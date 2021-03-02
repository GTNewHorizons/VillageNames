package astrotibs.villagenames.handler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import astrotibs.villagenames.VillageNames;
import astrotibs.villagenames.banner.BannerGenerator;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.ieep.ExtendedVillager;
import astrotibs.villagenames.ieep.ExtendedZombieVillager;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.item.ModItems;
import astrotibs.villagenames.name.NameGenerator;
import astrotibs.villagenames.nbt.VNWorldDataStructure;
import astrotibs.villagenames.prismarine.guardian.entity.monster.EntityGuardian;
import astrotibs.villagenames.prismarine.minecraft.Vec3i;
import astrotibs.villagenames.tracker.ServerInfoTracker;
import astrotibs.villagenames.utility.FunctionsVN;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureData;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class EntityInteractHandler {
	
	//Random random = new Random();
	
	public static int villageRadiusBuffer = 16;
	
	// This will only be used for getting the class path to a block
	@SubscribeEvent
	//@SideOnly(Side.CLIENT)
	public void onPlayerInteractEvent(PlayerInteractEvent event)
	{
		if ( 
				!event.entityPlayer.worldObj.isRemote
				&& event.action == Action.RIGHT_CLICK_BLOCK
				&& GeneralConfig.debugMessages
				)
		{
			Block targetBlock = event.world.getBlock(event.x, event.y, event.z);
			int targetBlockMeta = event.world.getBlockMetadata(event.x, event.y, event.z);
			
			String targetBlockUnlocName = targetBlock.getUnlocalizedName();
			event.entityPlayer.addChatComponentMessage(new ChatComponentText( "Class path of this block: " + targetBlock.getClass().toString().substring(6) ));
			event.entityPlayer.addChatComponentMessage(new ChatComponentText( "Unlocalized name: " + targetBlockUnlocName ));
			event.entityPlayer.addChatComponentMessage(new ChatComponentText( "Meta value: " + targetBlockMeta ));
			event.entityPlayer.addChatComponentMessage(new ChatComponentText( "" ));
			
			//LogHelper.info("ArrayList<Integer> " + GeneralConfig.structureModernPlainsAccessory1_vals);
		}
		
	}
	
	
	@SubscribeEvent(receiveCanceled=true)
	public void onEntityInteract(EntityInteractEvent event)
	{
		// This was used to verify server-client syncing of Careers
		/*
		if (GeneralConfig.debugMessages && event.target instanceof EntityVillager)
		{
			EntityVillager villager = (EntityVillager)event.target;
			//int career = ReflectionHelper.getPrivateValue(EntityVillager.class, villager, new String[]{"careerId", "field_175563_bv"});
			
			// v3.1.1 - Placed into null check to prevent crash
			
			ExtendedVillager ev = ExtendedVillager.get(villager);
			
			if (ev != null)
			{
				LogHelper.info(
						"SYNC CHECKING Profession: " + villager.getProfession()
						+ ", Career: " + (ExtendedVillager.get(villager)).getCareer()
						+ ", BiomeType: " + (ExtendedVillager.get(villager)).getBiomeType()
						+ ", ProfessionLevel: " + (ExtendedVillager.get(villager)).getProfessionLevel()
						+ ", SkinTone: " + (ExtendedVillager.get(villager)).getSkinTone()
						);
			}
		}
		if (event.target instanceof EntityZombie)
		{
			EntityZombie zombie = (EntityZombie)event.target;
			ExtendedZombieVillager ezv = ExtendedZombieVillager.get(zombie);
			
			// v3.1.1 - Placed into null check to prevent crash
			if (ezv != null)
			{
				int career = ezv.getCareer();
				int profLevel = ezv.getProfessionLevel();
				LogHelper.info("SYNC CHECKING Profession: " + ezv.getProfession() + ", Career: " + career + ", BiomeType: " + ezv.getBiomeType() + ", ProfessionLevel: " + profLevel
						+ ", SkinTone: " + ezv.getSkinTone() //v3.2
						);
			}
		}
		*/
		
		// summon Zombie ~ ~ ~ {IsVillager:1}
		
		// This is the placeholder for creating Witchery Village Guards
		if (
				Loader.isModLoaded("witchery")
				&& event.entity instanceof EntityPlayer
				&& event.target instanceof EntityVillager
				//&& event.target.getClass().toString().substring(6).equals(Reference.WitcheryGuardClass)
				&& event.entity.isSneaking()
				&& ((EntityPlayer)event.entity).inventory.getCurrentItem() != null
				&& ((EntityPlayer)event.entity).inventory.getCurrentItem().getItem() == Items.leather_chestplate
				&& event.entity.worldObj.villageCollectionObj.findNearestVillage((int)event.entity.posX, (int)event.entity.posY, (int)event.entity.posZ, villageRadiusBuffer) !=null
				) {
			
            // A player attempted to convert a villager to a witchery guard. Adds to the tracker for future check.
            final EntityVillager villager = (EntityVillager) event.target;
            
            ServerInfoTracker.add((EntityLiving)villager);

            if (GeneralConfig.debugMessages) {
                LogHelper.info("EntityMonitorHandler > A player just attempted to recruit villager " 
                		+ ( villager.getCustomNameTag().equals("")||villager.getCustomNameTag().equals(null) ? "(None)" : villager.getCustomNameTag() ) 
                		+ " [" + villager.getEntityId() + "] "
                		+ "at [" + 
                		//villager.getPosition(1.0F)
                		//Vec3.createVectorHelper(villager.posX, villager.posY, villager.posZ) // Changed because of server crash
                		new Vec3i(villager.posX, villager.posY + 0.5D, villager.posZ)
                		+ "]");
            }
		}
		
		else if (
				event.entity instanceof EntityPlayer		// IF A PLAYER INITIALIZES THIS INTERACTION
				&& !event.entityPlayer.worldObj.isRemote	// WORLD MUST NOT BE CLIENT
				&& event.target instanceof EntityLiving		// AND THE TARGET IS A LIVING THING
				&& !(event.target instanceof EntityPlayer)	// BUT NOT A PLAYER
				) {
			
			Random random = event.entity.worldObj.rand;
			
			EntityPlayer player = (EntityPlayer) event.entity; 							// The player
			ItemStack itemstack = player.inventory.getCurrentItem();					// What the player is holding
			EntityLiving target = (EntityLiving)event.target;							// The target
			String targetClassPath = event.target.getClass().toString().substring(6);	// The classpath string of the target
			World world = player.worldObj;												// Reference to the world object
			
			// Hard-code workaround to allow reference to the Elder Guardian in the configs
			if (target instanceof EntityGuardian) {
				if ( ((EntityGuardian) target).isElder() ) { // Reference "Elder" guardians using the below string. Reference ordinary guardians as EntityGuardian
					targetClassPath = Reference.ELDER_GUARDIAN_CLASS;
				}
			}
			
			
			// The coordinates of the target
			double targetX = target.posX;
    		double targetY = target.posY;
    		double targetZ = target.posZ;
			
			String customName = "";
			int targetAge = 0;
			int targetProfession = 0;
			int targetCareer = 0;
			boolean targetPlayerCreated=false; // Specifically to determine whether an iron golem was made by the player or not
			int villagerMappedProfession = -1; // Should be assigned below
			
			// Nearest village to the target entity
			Village villageNearTarget = world.villageCollectionObj.findNearestVillage((int)targetX, (int)targetY, (int)targetZ, villageRadiusBuffer);
			
			
			// Player rep evaluated near the start - v3.2.2
			int playerRep = 0;
			try{playerRep = ReputationHandler.getVNReputationForPlayer((EntityPlayerMP) player, ReputationHandler.getVillageTagPlayerIsIn((EntityPlayerMP) player), villageNearTarget);}
			catch (Exception e) {}
			
			
			// keys: "NameTypes", "Professions", "ClassPaths"
			Map<String, ArrayList> mappedNamesAutomatic = GeneralConfig.unpackMappedNames(GeneralConfig.modNameMappingAutomatic);
			Map<String, ArrayList> mappedNamesClickable = GeneralConfig.unpackMappedNames(GeneralConfig.modNameMappingClickable);
			
			//if (!world.isRemote) { // Since messages get sent on both sides
				
			// Read the target's NBT data
			NBTTagCompound compound = new NBTTagCompound();
			target.writeEntityToNBT(compound);
			targetProfession = compound.getInteger("Profession");
			
			// Used to ascertain career for 1.7 mod version
			if (target instanceof EntityVillager && GeneralConfig.villagerCareers) {
				targetCareer = (ExtendedVillager.get((EntityVillager)target)).getCareer();
			 }
			else {
				targetCareer = compound.getInteger("Career");
			}
			
			customName = compound.getString("CustomName");
			targetAge = compound.getInteger("Age");
			targetPlayerCreated = compound.getBoolean("PlayerCreated");
			
			
			// Convert a non-vanilla profession into a vanilla one for the purposes of generating a hint page
			Map<String, ArrayList> mappedProfessions = GeneralConfig.unpackMappedProfessions(GeneralConfig.modProfessionMapping);
	    	 // If the below fails, do none
			
			if (target instanceof EntityVillager) { // Put this into if block -v3.2.3
		    	try {
		    		villagerMappedProfession =  
		    				(Integer) ((targetProfession >= 0 && targetProfession <= 5)
		    				? targetProfession : ((mappedProfessions.get("VanillaProfMaps")).get( mappedProfessions.get("IDs").indexOf(targetProfession) )));
		    		}
		    	catch (Exception e) {
		    		if(!event.entityLiving.worldObj.isRemote) LogHelper.error("Error evaluating mod profession ID. Check your formatting!");
		    		}
			} // v3.2.3
			
	    	// Primitive Mobs hard coding for career detection
	    	if (targetClassPath.equals( ModObjects.PMTravelingMerchantClass ) )
					{villagerMappedProfession = GeneralConfig.PMMerchantProfessionMap;}
	    	else if (targetClassPath.equals( ModObjects.PMLostMinerClass ) )
					{villagerMappedProfession = GeneralConfig.PMLostMinerProfessionMap;}
	    	else if (targetClassPath.equals( ModObjects.PMSheepmanSmithClass ) )
					{villagerMappedProfession = 3;}
							
			if (GeneralConfig.debugMessages) {
				player.addChatComponentMessage(new ChatComponentText("Class path of this entity: " + targetClassPath));
				player.addChatComponentMessage(new ChatComponentText(""));
				
				
				if (target instanceof EntityVillager) {
					try {
						LogHelper.info("Profession: " + targetProfession 
								+ (GeneralConfig.villagerCareers ? ", Career: " + (ExtendedVillager.get((EntityVillager) target)).getCareer()
										: "")
								+ (GeneralConfig.modernVillagerSkins ? ", BiomeType: " + (ExtendedVillager.get((EntityVillager) target)).getBiomeType() // Added in v3.1
										: "")
								+ (GeneralConfig.modernVillagerSkins ? ", Profession Level: " + (ExtendedVillager.get((EntityVillager) target)).getProfessionLevel() // Added in v3.1
										: "")
								+ ", Mapped profession: " + villagerMappedProfession
								+ (GeneralConfig.villagerSkinTones ? ", Skin Tone: " + (ExtendedVillager.get((EntityVillager) target)).getSkinTone() // Added in v3.2
										: "")
								);
					}
					catch (Exception e) {} // This is some kind of non-standard zombie
					
					}
				else if (target instanceof EntityZombie && !(target instanceof EntityPigZombie)) {
						try {
						LogHelper.info(
								  (GeneralConfig.modernZombieSkins ? "Zombie Profession: " + (ExtendedZombieVillager.get((EntityZombie) target)).getProfession()
										: "") 
								+ ((GeneralConfig.villagerCareers && GeneralConfig.modernZombieSkins) ? ", Career: " + (ExtendedZombieVillager.get((EntityZombie) target)).getCareer()
										: "")
								+ (GeneralConfig.modernZombieSkins ? ", BiomeType: " + (ExtendedZombieVillager.get((EntityZombie) target)).getBiomeType() // Added in v3.1
										: "")
								+ (GeneralConfig.modernZombieSkins ? ", Profession Level: " + (ExtendedZombieVillager.get((EntityZombie) target)).getProfessionLevel() // Added in v3.1
										: "")
								+ (GeneralConfig.villagerSkinTones ? ", Skin Tone: " + (ExtendedZombieVillager.get((EntityZombie) target)).getSkinTone() // Added in v3.2
										: "")
								);
						}
						catch (Exception e) {} // This is some kind of non-standard zombie
					}
				
				LogHelper.info("Entity CustomName: " + (customName.equals("")||customName.equals(null) ? "(None)" : customName) + ", Age: " + targetAge +
						(target instanceof EntityIronGolem ? ", PlayerCreated: " + targetPlayerCreated : ""));
				
				try {
					LogHelper.info(player.getDisplayName() + " reputation in this village: "
							+ playerRep // v3.2.2
							);
				}
				catch (Exception e) {}
			}
			//}
			
			// If you're talking to a nitwit, cancel the trade gui
			if (
					GeneralConfig.enableNitwit
					&& target instanceof EntityVillager
					&& targetProfession==5
					) {
				// summon Villager ~ ~ ~ {Profession:5}
				if (!target.worldObj.isRemote) {
					// Blank out the trade
					MerchantRecipeList buyingList = ReflectionHelper.getPrivateValue(EntityVillager.class, (EntityVillager)target, new String[]{"buyingList", "field_70963_i"});
					//buyingList.clear();
					if (	
							buyingList!=null
							) {
						Iterator iterator = buyingList.iterator();
						while (iterator.hasNext()) {
							buyingList.remove(0);
						}
					}
				}
				event.setCanceled(true);
			}
			
			
			
			//----------------------------------//
			//------------ Name Tag ------------//
			//----------------------------------//
			
			// If you're holding a name tag, 
			if (
					itemstack != null
					&& itemstack.getItem() == Items.name_tag
					)
			{
				// Randomly name an unnamed pet you own using a blank name tag
				
				/*
				 * give AstroTibs minecraft:name_tag 64 0 {display:{Name:"Larongom"}}
				 * summon Wolf ~ ~ ~ {Owner:"AstroTibs"}
				 */
				try
				{
					if (
							(
								(target instanceof EntityTameable
								&& ((EntityTameable)target).isTamed()
								&& ((EntityTameable)target).func_152114_e(player))
								||
								(target instanceof EntityHorse
								&& ((EntityHorse)target).func_152119_ch().equals(player.getUniqueID().toString()))
							)
							
							&& !target.hasCustomNameTag()
							&& (!itemstack.hasDisplayName() || (itemstack.hasDisplayName() && itemstack.getDisplayName().equals("")))
							)
					{
						event.setCanceled(true);
						
						// Apply the name here
						String[] petname_a = NameGenerator.newRandomName("pet", random);
						target.setCustomNameTag((petname_a[1]+" "+petname_a[2]+" "+petname_a[3]).trim());
						
						// Consume the blank name tag if relevant
						if (!player.capabilities.isCreativeMode) {itemstack.stackSize--;}
						
						return;
					}
				}
				catch (Exception e) {LogHelper.error("Caught exception when naming a pet: " + e);}
				
				// Cancel naming an entity that has special name registration
				if (
						itemstack.hasDisplayName()
						&& !itemstack.getDisplayName().equals(customName)
						&& !player.capabilities.isCreativeMode
						) {
					
					//check to see if the target is a Villager or an entry from the other mod config list.
					if (
							(target instanceof EntityVillager && GeneralConfig.nameEntities)
							|| (target instanceof EntityIronGolem && GeneralConfig.nameGolems && !targetPlayerCreated)
							|| mappedNamesAutomatic.get("ClassPaths").contains(targetClassPath)
							|| mappedNamesClickable.get("ClassPaths").contains(targetClassPath)
							) {
						// If so, you should be prevented from naming the entity.
						event.setCanceled(true);
						if (!world.isRemote) player.addChatComponentMessage(new ChatComponentText("That is not its name!"));
						//target.setCustomNameTag(customName);
					}
				}
			}
			
			
			
			//-------------------------------//
			//------------ Poppy ------------//
			//-------------------------------//
			
			else if (
					!world.isRemote
					&& event.entity.dimension == 0 // Only applies to the Overworld
					&& itemstack != null
					&& itemstack.getItem() == Item.getItemFromBlock(Blocks.red_flower) // You present a poppy
					&& itemstack.getItemDamage() == 0
					&& target instanceof EntityIronGolem // to an Iron Golem
					&& !targetPlayerCreated // The Golem wasn't created by you
					) {
				
				int population = -1;
				// reputationRemoved in v3.2.2
				
				try {
					population = villageNearTarget.getNumVillagers();
				}
				catch (Exception e) {
					// Failed to evaluate village, or there is none.
				}
				
				// Check if the player is inside a village bounding box
				boolean playerIsInVillage = false;
				
				try{
					// Load in the vanilla structure file
					
					// Updated in v3.2.1 to allow for Open Terrain Generation compatibility

					MapGenStructureData structureData;
		    		NBTTagCompound nbttagcompound = null;
		    		
		    		try
		    		{
		    			structureData = (MapGenStructureData)world.perWorldStorage.loadData(MapGenStructureData.class, "Village");
		    			nbttagcompound = structureData.func_143041_a();
		    		}
		    		catch (Exception e) // Village.dat does not exist
		    		{
		    			try
		        		{
		        			structureData = (MapGenStructureData)world.perWorldStorage.loadData(MapGenStructureData.class, "OTGVillage");
		        			nbttagcompound = structureData.func_143041_a();
		        		}
		        		catch (Exception e1) {} // OTGVillage.dat does not exist
		    		}
					
    				// Iterate through the entries
    				Iterator itr = nbttagcompound.func_150296_c().iterator();
    				
    				while (itr.hasNext()) {
    					Object element = itr.next();
    					
    					NBTBase nbtbase = nbttagcompound.getTag(element.toString());
    					
    					if (nbtbase.getId() == 10) {
    						NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbtbase;
    						
    						try {
    							int[] boundingBox = nbttagcompound2.getIntArray("BB");
    							// Now check to see if the player is inside the feature
    							if (
									   player.posX >= boundingBox[0]
									&& player.posY >= boundingBox[1]
									&& player.posZ >= boundingBox[2]
									&& player.posX <= boundingBox[3]
									&& player.posY <= boundingBox[4]
									&& player.posZ <= boundingBox[5]
    								) {
    								playerIsInVillage = true;
    							}
    						}
    						catch (Exception e) {
    							// There's a valid entry, but there's no bounding box tag. This is an error.
    						}
    					}
    				}
    				
				}
				catch (Exception e) {} // Not inside a valid Village BB, so playerIsInVillage will remain as false
				
				
				if (   
						(population == 0 || (playerIsInVillage && population == -1)) // No Villagers in the village
						&& playerRep >= -15 // This may be redundant in the event of an empty village. Changed to playerRep in v3.2.2
						) {
						EntityIronGolem ironGolemTarget = (EntityIronGolem) target;
						
						// Play a confirmation sound effect
						
						// Consume the poppy
						if (!player.capabilities.isCreativeMode) {player.inventory.consumeInventoryItem( Item.getItemFromBlock(Blocks.red_flower) );}
						
						// Give the golem the poppy
						ironGolemTarget.setHoldingRose(true);
						
						// Spawn a heart particle
						
						// Switch him over to your side
						ironGolemTarget.setPlayerCreated(true);
						
						// Trigger the achievement
						if (!( (EntityPlayerMP)player ).func_147099_x().hasAchievementUnlocked(VillageNames.laputa))
						{
							player.triggerAchievement(VillageNames.laputa);
							AchievementReward.allFiveAchievements( (EntityPlayerMP)player );
						}
					}
				}
			
			
			//-------------------------------//
			//------------ Codex ------------//
			//-------------------------------//
			
			// If you're holding an emerald or iron/gold ingot,
			else if (
					GeneralConfig.villagerSellsCodex &&
					itemstack != null
					&& ( itemstack.getItem() == Items.emerald
					  || itemstack.getItem() == Items.gold_ingot
					  || itemstack.getItem() == Items.iron_ingot )
					&& target instanceof EntityVillager
					&& !player.isSneaking() // I'm disallowing you from making a Codex when crouching because it doesn't visually update your itemstacks correctly
					&& targetProfession==1 //check to see if the target is a Villager with Profession 1 (Librarian).
					&& villagerMappedProfession==1 // To prevent things like Traveling Merchant from selling you the Codex
					//&& !targetClassPath.equals(Reference.PMTravelingMerchantClass)
					//&& !targetClassPath.equals(Reference.PMLostMinerClass)
					//&& !targetClassPath.equals(Reference.PMSheepmanClass)
					//&& !targetClassPath.equals(Reference.PMSheepmanSmithClass)
					) {
				event.setCanceled(true);
				// If so, you can do the Codex interaction.
				if ( targetAge < 0 ) {
					// Villager is a baby, so can't make you a codex.
					if (!event.entityPlayer.worldObj.isRemote) { // Messages get sent on both sides.
						babyCantHelpString("codex");
					}
				}
				else { // Villager is an adult. Proceed.
					
					// Finds the nearest village to this target, but only if the villager's coordinates are within its bounding radius plus the buffer
					
					if (villageNearTarget != null) { // The Villager is inside/near a village
						
						// Removed playerRep because it's calculated up top - v3.2.2
						
						if (playerRep < 0) { // Your reputation is too low.
							if (!world.isRemote) {player.addChatComponentMessage(new ChatComponentText( "The villager does not trust you." ) );}
						}
						else {
							// The villager trusts you.
            				// Now we construct a codex based on materials you're offering.
							
            				// Search the player's inventory, and sum up how many resources they have.
							int emeralds = 0;
            				int ironIngots = 0;
            				int goldIngots = 0;
            				for (int slot = 0; slot < player.inventory.getSizeInventory(); slot++)
            				{
            					ItemStack Stack = player.inventory.getStackInSlot(slot);

            					if (Stack != null && Stack.getItem().equals(Items.emerald)) {emeralds += Stack.stackSize;}
            					if (Stack != null && Stack.getItem().equals(Items.iron_ingot)) {ironIngots += Stack.stackSize;}
            					if (Stack != null && Stack.getItem().equals(Items.gold_ingot)) {goldIngots += Stack.stackSize;}
            				}
            				
            				// We have the totals for emeralds and iron/gold ingots, and the player's rep.
            				// Now let's actually do the exchange.
            				
            				// Adjust the material requirements based on your reputation
            				int emeraldRequired = 4 - (playerRep+2)/5;
            				int ironRequired = 8 - ((playerRep+1)*5)/12;
            				int goldRequired = 4 - (playerRep+2)/5;
            				
            				if (GeneralConfig.debugMessages && !world.isRemote) {LogHelper.info("Your reputation: "+playerRep+", Emeralds req: "+emeraldRequired+", Iron req: "+ironRequired+", Gold req: "+goldRequired);}
            				
            				// If the item in your hand is an emerald, trade with just emeralds.
            				if (itemstack.getItem() == Items.emerald) {
            					
            					if (emeralds >= emeraldRequired+1
            							|| player.capabilities.isCreativeMode) { // Allow Creative players to get Codexes regardless of emerald count
            						
            						if (!player.capabilities.isCreativeMode) { // Consume the emeralds only if you're in survival
            							for (int i = 0; i < emeraldRequired+1; i++) {player.inventory.consumeInventoryItem(Items.emerald);}
            						}
            						
            						EntityItem eitem = (GeneralConfig.villagerDropBook ? target : player).entityDropItem(new ItemStack(ModItems.codex), 1);
	            			        eitem.delayBeforeCanPickup = 0; // No delay: directly into the inventory!
            					}
            					else if (!world.isRemote) {player.addChatComponentMessage(new ChatComponentText( "The Villager wants more emeralds from you." ) );}
            				}
            				// If the item in your hand is a gold ingot, trade both gold ingots and emeralds.
            				else if (itemstack.getItem() == Items.gold_ingot) {
            					
            					if ( (emeralds >= emeraldRequired && goldIngots >= goldRequired)
            							|| player.capabilities.isCreativeMode) { // Allow Creative players to get Codexes regardless of emerald/gold count
            						
            						if (!player.capabilities.isCreativeMode) { // Consume the items only if you're in survival
            							for (int i = 0; i < emeraldRequired; i++) {player.inventory.consumeInventoryItem(Items.emerald);}
    	            					for (int i = 0; i < goldRequired; i++) {player.inventory.consumeInventoryItem(Items.gold_ingot);}
            						}
            						
            						EntityItem eitem = (GeneralConfig.villagerDropBook ? target : player).entityDropItem(new ItemStack(ModItems.codex), 1);
	            			        eitem.delayBeforeCanPickup = 0; // No delay: directly into the inventory!
        							
            					}
            					else if (!world.isRemote) { // Messages send to both sides
            						if (emeralds < emeraldRequired && goldIngots < goldRequired) {player.addChatComponentMessage(new ChatComponentText( "The Villager wants more emeralds and gold from you." ) );}
            						else if (emeralds < emeraldRequired) {player.addChatComponentMessage(new ChatComponentText( "The Villager wants more emeralds from you." ) );}
            						else if (goldIngots < goldRequired) {player.addChatComponentMessage(new ChatComponentText( "The Villager wants more gold from you." ) );}
            					}
            					
            				}
            				// If the item in your hand is an iron ingot, trade both iron ingots and emeralds.
            				else if (
            						itemstack.getItem() == Items.iron_ingot
            						) {
            					
            					if ( (emeralds >= emeraldRequired && ironIngots >= ironRequired)
            							|| player.capabilities.isCreativeMode) { // Allow Creative players to get Codexes regardless of emerald/iron count
            						
            						if (!player.capabilities.isCreativeMode) { // Consume the items only if you're in survival
            							for (int i = 0; i < emeraldRequired; i++) player.inventory.consumeInventoryItem(Items.emerald);
    	            					for (int i = 0; i < ironRequired; i++) player.inventory.consumeInventoryItem(Items.iron_ingot);
            						}
            						
            						EntityItem eitem = (GeneralConfig.villagerDropBook ? target : player).entityDropItem(new ItemStack(ModItems.codex), 1);
	            			        eitem.delayBeforeCanPickup = 0; // No delay: directly into the inventory!
        							
            					}
            					else if (!world.isRemote) { // Messages send to both sides
            						if (emeralds < emeraldRequired && ironIngots < ironRequired) {player.addChatComponentMessage(new ChatComponentText( "The Villager wants more emeralds and iron from you." ) );}
            						else if (emeralds < emeraldRequired) {player.addChatComponentMessage(new ChatComponentText( "The Villager wants more emeralds from you." ) );}
            						else if (ironIngots < ironRequired) {player.addChatComponentMessage(new ChatComponentText( "The Villager wants more iron from you." ) );}
            					}
            				}
						}
					}
					else {// No nearby villages found: let the user know that s/he can't get a Codex.
						villagerConfused(player);
					}
				}
			}
			
			
			//------------------------------//
			//------------ Book ------------//
			//------------------------------//
			
			// If you're holding a book...
			else if (
					itemstack != null
					&& itemstack.getItem() == Items.book
					) {
				
				// The target is a Villager
				if (target instanceof EntityVillager) {
					event.setCanceled(true);
					EntityVillager villager = (EntityVillager)target;
					if ( targetAge >= 0 ) { // Villager is an adult.
						
						if ( villageNearTarget == null) { // Remove dimension limitation - v3.2.2
							if (!world.isRemote) {villagerConfused(player);}
						}
						else { // There is a town.
							
							// Removed player rep assertion - v3.2.2
							
							if (playerRep < 0) { // Your reputation is too low.
								if (!world.isRemote) {player.addChatComponentMessage(new ChatComponentText( "The villager does not trust you." ) );}
	            			}
							else { // The Villager trusts you.
								int villageRadius = villageNearTarget.getVillageRadius();
								int popSize = villageNearTarget.getNumVillagers();
								int centerX = villageNearTarget.getCenter().posX; // Village X position
		            			int centerY = villageNearTarget.getCenter().posY; // Village Y position
		            			int centerZ = villageNearTarget.getCenter().posZ; // Village Z position
								
		            			// Let's see if we can find a sign near that located village center!
		            			VNWorldDataStructure data = VNWorldDataStructure.forWorld(world, "villagenames3_Village", "NamedStructures");
		            			// .getTagList() will return all the entries under the specific village name.
		    					NBTTagCompound tagCompound = data.getData();
		    					Set tagmapKeyset = tagCompound.func_150296_c(); //Gets the town key list: "coordinates"
		    					
		    					Iterator itr = tagmapKeyset.iterator();
		    			        String townSignEntry;
		    			        
		    			        //Placeholders for villagenames.dat tags
		    			        boolean signLocated = false; //Use this to record whether or not a sign was found
		    			        boolean isColony = false; //Use this to record whether or not the village was naturally generated
		    			        
		    			        while(itr.hasNext()) { // Going through the list of VN villages
		    			        	Object element = itr.next();
		    			        	townSignEntry = element.toString(); //Text name of village header (e.g. "x535y80z39")
		    			        	//The only index that has data is 0:
		    			            NBTTagCompound tagList = tagCompound.getTagList(townSignEntry, tagCompound.getId()).getCompoundTagAt(0);
		    			            // Retrieve the "sign" coordinates
		    			            int townX = tagList.getInteger("signX");
		    			            int townY = tagList.getInteger("signY");
		    			            int townZ = tagList.getInteger("signZ");
		    			            String namePrefix = tagList.getString("namePrefix");
		    			            String nameRoot = tagList.getString("nameRoot");
		    			            String nameSuffix = tagList.getString("nameSuffix");
		    			            // Now find the nearest Village to that sign's coordinate, within villageRadiusBuffer blocks outside the radius.
		    			            Village villageNearSign = world.villageCollectionObj.findNearestVillage(townX, townY, townZ, villageRadiusBuffer);
		    			            
		    			            // Player rep evaluated here before writing book - v3.2.2
		    						int playerRepInVillageNearSign = 0;
		    						try{playerRepInVillageNearSign = ReputationHandler.getVNReputationForPlayer((EntityPlayerMP) player, ReputationHandler.getVillageTagPlayerIsIn((EntityPlayerMP) player), villageNearSign);}
		    						catch (Exception e) {}
		    			            
		    			            isColony = tagList.getBoolean("isColony");
		    			            
		    			            if (villageNearSign == villageNearTarget) { // There is a match between the nearest village to this villager and the nearest village to the sign
		    			            	signLocated = true;
		    			            	if (!world.isRemote) {
		    			            		// Generate a new Village Book with all the dressings!
		    			            		WriteBookHandler.targetWriteNewVillageBook(
		    			            				"village", target.getCustomNameTag(),
		    			            				townX, townY, townZ,
		    			            				isColony ? "Colony" : "Village",
		    			            				// Explicit book dimension below - v3.2.2
		    			            				player.dimension==0 ? "" : player.dimension==-1 ? "The Nether" : player.dimension==1 ? "The End": "Dimension "+player.dimension,
		    			            				namePrefix, nameRoot, nameSuffix,
		    			            				true, villageNearSign, event,
		    			            				targetProfession, targetCareer, 
		    			            				( villager.getRecipes(player) ).size(), //villageNearSign.getReputationForPlayer(player.getDisplayName()),
		    			            				playerRepInVillageNearSign, // v3.2.2
		    			            				player, target
		    			            				);
		    			            		}
		    			            	break; // No need to keep comparing villages.
		    			            }
		    			        }
		    			        if (!signLocated) {
		    			        	// No well sign was found that matched the villager's village.
		    			        	// We can assume this is a village WITHOUT a sign. So let's at least give it a name!
		    			        	// Name type based on dimension - v3.2.2
		    			        	Random deterministic = new Random(); deterministic.setSeed(world.getSeed() + FunctionsVN.getUniqueLongForXYZ(centerX, centerY, centerZ));
		    			        	String[] newVillageName = NameGenerator.newRandomName(player.dimension==-1 ? "Village-Fortress" : player.dimension==1 ? "Village-EndCity" : "Village", deterministic);
		    			        	String headerTags = newVillageName[0];
	                        		String namePrefix = newVillageName[1];
	                        		String nameRoot = newVillageName[2];
	                        		String nameSuffix = newVillageName[3];
	                        		
	                        		// Changed color block in v3.1banner
                        			// Generate banner info, regardless of if we make a banner.
                            		Object[] newRandomBanner = BannerGenerator.randomBannerArrays(deterministic, -1, -1);
                    				ArrayList<String> patternArray = (ArrayList<String>) newRandomBanner[0];
                    				ArrayList<Integer> colorArray = (ArrayList<Integer>) newRandomBanner[1];
                    				ItemStack villageBanner = BannerGenerator.makeBanner(patternArray, colorArray);
                            		int townColorMeta = 15-colorArray.get(0);
                            		int townColorMeta2 = colorArray.size()==1 ? townColorMeta : 15-colorArray.get(1);
                            		
                            		// Make the data bundle to save to NBT
	                        		NBTTagList nbttaglist = new NBTTagList();
	                        		
	                        		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
	                                nbttagcompound1.setInteger("signX", centerX);
	                                nbttagcompound1.setInteger("signY", centerY);
	                                nbttagcompound1.setInteger("signZ", centerZ);
	                                nbttagcompound1.setInteger("townColor", townColorMeta); //In case we want to make clay, carpet, wool, glass, etc
	                                nbttagcompound1.setInteger("townColor2", townColorMeta2);
	                                nbttagcompound1.setString("namePrefix", namePrefix);
	                                nbttagcompound1.setString("nameRoot", nameRoot);
	                                nbttagcompound1.setString("nameSuffix", nameSuffix);
	                                nbttagcompound1.setBoolean("fromEntity", true); // Record whether this name was generated from interaction with an entity
	                                
	                                if (
	                                		player.dimension == 0 && // Added to avoid a crash by loading a null list of villages - v3.2.2
	                                		!ReputationHandler.getVillageTagPlayerIsIn((EntityPlayerMP)player).equals("none")
	                                		) {
		                                nbttagcompound1.setBoolean("preVN", true); // No Village Names entry was discovered, so presumably this village was generated before including VN
                                	}
	                                else {
		                                nbttagcompound1.setBoolean("isColony", true); // The village is player-created
		                                isColony = true;
	                                }

	                                // Added in v3.1banner
                                    // Form and append banner info
	                                // If you don't have a mod banner, this will not be added. It will be generated once you do.
                                    if (villageBanner!=null) {nbttagcompound1.setTag("BlockEntityTag", BannerGenerator.getNBTFromBanner(villageBanner));}
	                                
	                                nbttaglist.appendTag(nbttagcompound1);
	                                
	                                // Save the data under a "Villages" entry with unique name based on sign coords
	                                data.getData().setTag((namePrefix + " " + nameRoot + " " + nameSuffix).trim() + ", x" + centerX + " y" + centerY + " z" + centerZ, nbttaglist);
	                                
	                                data.markDirty();
	                        		
	                                signLocated = false;
	                                
	                                //event refers to the interaction with the villager
				            		//tagList is the villagename data
				            		//villageNearVillager is the corresponding vanilla data
	                                if (!world.isRemote) {
	                                	WriteBookHandler.targetWriteNewVillageBook(
	    			            				"village", target.getCustomNameTag(),
	    			            				centerX, centerY, centerZ,
	    			            				isColony ? "Colony" : "Village",
			            						// Explicit book dimension below - v3.2.2
	    			            				player.dimension==0 ? "" : player.dimension==-1 ? "The Nether" : player.dimension==1 ? "The End": "Dimension "+player.dimension,
	    			            				namePrefix, nameRoot, nameSuffix,
	    			            				true, villageNearTarget, event,
	    			            				targetProfession, targetCareer, 
	    			            				( villager.getRecipes(player) ).size(), //villageNearTarget.getReputationForPlayer(player.getDisplayName()),
	    			            				playerRep, // v3.2.2
	    			            				player, target
	    			            				);
	                                	}
		    			        }
							}
						}
					}
					else { // The Villager is a baby and so can't help you.
						if (!world.isRemote) {player.addChatComponentMessage(new ChatComponentText( babyCantHelpString("book") ) );}
					}
				}
				
				else { // The target is a non-vanilla type that has a supported village and book
					
				    /* 
				     * STRUCTURE		NBTname			key					toptag
				     * 
				     * Villages:						villagenames		Villages
				     * Temples:							villagenames_te		Temples
				     * Mineshafts:						villagenames_mi		Mineshafts
				     * Strongholds:						villagenames_st		Strongholds
				     * Monuments:						villagenames_mo		Monuments
				     * Mansions:						villagenames_ma		Mansions
				     * Fortress:						villagenames_fo		Fortresses
				     * End City:						villagenames_ec		EndCities
				     * 
				     * Abandoned Base:					villagenames_gcab	AbandonedBases
				     * End Islands:						villagenames_heei	hardcoreenderdragon_EndIsland
				     * End Towers:						villagenames_heet	hardcoreenderdragon_EndTower
				     * Fronos Villages:	FronosVillage	villagenames_mpfv	FronosVillages
				     * Koentus Vill:	KoentusVillage	villagenames_mpkv	KoentusVillages
				     * Moon Villages:	MoonVillage		villagenames_gcmv	MoonVillages
				     * Nibiru Villages:	NibiruVillage	villagenames_mpnv	NibiruVillages
				     */
					
					// keys: "NameTypes", "StructureTypes", "StructureTitles", "DimensionNames", "BookTypes", "ClassPaths"
					Map<String, ArrayList> mappedModStructureNames = GeneralConfig.unpackModStructures(GeneralConfig.modStructureNames);
					
					// What kind of name to be generated
					
					String nameType = "alienvillage";
					try {nameType = (String) (mappedModStructureNames.get("NameTypes")).get( mappedModStructureNames.get("ClassPaths").indexOf(targetClassPath) );}
					catch (Exception e) {}
					
					// The name of the NBT structure file generated by the mod (e.g. "FronosVillage")
					String structureType = "";
					try {structureType = (String) (mappedModStructureNames.get("StructureTypes")).get( mappedModStructureNames.get("ClassPaths").indexOf(targetClassPath) );}
					catch (Exception e) {structureType = "";}
					
					// The string name of the structure (e.g. "Fronos Village")
					String structureTitle = "";
					try {structureTitle = (String) (mappedModStructureNames.get("StructureTitles")).get( mappedModStructureNames.get("ClassPaths").indexOf(targetClassPath) );}
					catch (Exception e) {}
					
					// The string name of the dimension the structure is in (e.g. "Fronos")
					String dimensionName = "";
					try {dimensionName = (String) (mappedModStructureNames.get("DimensionNames")).get( mappedModStructureNames.get("ClassPaths").indexOf(targetClassPath) );}
					catch (Exception e) {}
					
					// The type of book to create: (e.g. "fronosvillage")
					String bookType = "village";
					try {bookType = (String) (mappedModStructureNames.get("BookTypes")).get( mappedModStructureNames.get("ClassPaths").indexOf(targetClassPath) );}
					catch (Exception e) {}
					
					// Now that the relevant info has been set, go through the motions
					if ( targetAge < 0 ) {
						// Target is a baby so can't write you a book.
						if (!world.isRemote) {player.addChatComponentMessage(new ChatComponentText( babyCantHelpString("book") ) );}
					}
					else { // Target is an adult
						
						// Prep the data stuff
						MapGenStructureData structureData;
		    			int[] BB = new int[6];
		    			boolean targetIsInsideAlienVillage=false;
		    			
		    			try {
		    				structureData = (MapGenStructureData)world.perWorldStorage.loadData(MapGenStructureData.class, structureType);
		    				// Load in entries from the structure class
		    				NBTTagCompound nbttagcompound = structureData.func_143041_a();
		    				
							Iterator itr = nbttagcompound.func_150296_c().iterator();
							
							// Go through the entries
							while (itr.hasNext()) {
								Object element = itr.next();
								
								NBTBase nbtbase = nbttagcompound.getTag(element.toString());
								
								if (nbtbase.getId() == 10) { //10 is "compound tag" I think?
									NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbtbase;
									
									try{
										int[] boundingBox = nbttagcompound2.getIntArray("BB");
										// Now check to see if the target is inside the feature
										if (
												   targetX >= boundingBox[0]
												&& targetY >= boundingBox[1]
												&& targetZ >= boundingBox[2]
												&& targetX <= boundingBox[3]
												&& targetY <= boundingBox[4]
												&& targetZ <= boundingBox[5]
												) { // Target is inside bounding box.
											
											targetIsInsideAlienVillage = true;
											
											String structureName;
											
											int ChunkX = nbttagcompound2.getInteger("ChunkX");
											int ChunkZ = nbttagcompound2.getInteger("ChunkZ");
											
											String[] structureInfoArray = WriteBookHandler.tryGetStructureInfo(structureType, boundingBox, world);

											String namePrefix = structureInfoArray[0];
											String nameRoot = structureInfoArray[1];
											String nameSuffix = structureInfoArray[2];

											int signX; int signY; int signZ;

											// If none is found, these strings are "null" which parseInt does not like very much
											try {
												signX = Integer.parseInt(structureInfoArray[3]);
												signY = Integer.parseInt(structureInfoArray[4]);
												signZ = Integer.parseInt(structureInfoArray[5]);
													}
											catch (Exception e) {}
											
											// If a name was NOT returned, then we need to generate a new one, as is done below:

											int[] structureCoords = new int[] {
    												(boundingBox[0]+boundingBox[3])/2,
    												(boundingBox[1]+boundingBox[4])/2,
    												(boundingBox[2]+boundingBox[5])/2,
    												};
											
											VNWorldDataStructure data = VNWorldDataStructure.forWorld(world, "villagenames3_"+structureType, "NamedStructures");
											NBTTagCompound tagCompound = data.getData();
											
											if (structureInfoArray[0]==null && structureInfoArray[1]==null && structureInfoArray[2]==null) {
												
												//Structure has no name. Generate it here.
												signX = structureCoords[0];
												signY = structureCoords[1];
												signZ = structureCoords[2];
												
												Random deterministic = new Random(); deterministic.setSeed(world.getSeed() + FunctionsVN.getUniqueLongForXYZ(signX, signY, signZ));
												structureInfoArray = NameGenerator.newRandomName(nameType, deterministic); // Generates name based on table above
												
												// Gotta copy this thing to each IF condition I think
												String headerTags = structureInfoArray[0];
												namePrefix = structureInfoArray[1];
												nameRoot = structureInfoArray[2];
												nameSuffix = structureInfoArray[3];
												int townColorMeta = 15; int townColorMeta2 = 0;
												
												// Make the data bundle to save to NBT
												NBTTagList nbttaglist = new NBTTagList();
												
												NBTTagCompound nbttagcompound1 = new NBTTagCompound();
												nbttagcompound1.setInteger("signX", signX);
												nbttagcompound1.setInteger("signY", signY);
												nbttagcompound1.setInteger("signZ", signZ);
												nbttagcompound1.setInteger("townColor", townColorMeta); //In case we want to make clay, carpet, wool, glass, etc
												nbttagcompound1.setInteger("townColor2", townColorMeta2);
												nbttagcompound1.setString("namePrefix", namePrefix);
												nbttagcompound1.setString("nameRoot", nameRoot);
												nbttagcompound1.setString("nameSuffix", nameSuffix);
												nbttagcompound1.setBoolean("fromEntity", true); // Record whether this name was generated from interaction with an entity
												nbttaglist.appendTag(nbttagcompound1);
												
												// .getTagList() will return all the entries under the specific village name.
												
												tagCompound.setTag((namePrefix + " " + nameRoot + " " + nameSuffix).trim() + ", x" + signX + " y" + signY + " z" + signZ, nbttaglist);
												data.markDirty();
												
											}
											// Either the structure name already existed, or it was just generated right above.
											
											structureName = structureInfoArray[0]+" "+structureInfoArray[1]+" "+structureInfoArray[2];
											structureName = structureName.trim();
											
											signX = structureCoords[0];
											signY = structureCoords[1];
											signZ = structureCoords[2];
											
							    			// Actually form the book contents and write the book
							    			
							    			//Here are the contents of the book up front
							    			String bookContents = "\n\u00a7l";
							    			
							    			// I don't care if the structure has a sign. We have to cut the name up into arbitrary sign strings for the book.
							    			
							    			String sign0 = new String();
							    			String sign1 = new String();
							    			String sign2 = new String();
							    			String sign3 = new String();
							    			
							    			String headerTags = GeneralConfig.headerTags;
							    			
							    			if (!world.isRemote) {
			                                	WriteBookHandler.targetWriteNewVillageBook(
			                                			bookType, target.getCustomNameTag(),
			                                			signX, signY, signZ,
			                                			structureTitle, dimensionName,
			    			            				namePrefix, nameRoot, nameSuffix,
			    			            				structureType.equals("Village"), null, event, 
			    			            				targetProfession, targetCareer, // Also unused but there's no harm in leaving them
			    			            				-1, -1, // Trade size and player rep are unused outside of vanilla villages
			    			            				player, target
			    			            				);
			                                	}
										}
										// Target is not inside bounding box of the particular entry in question
									}
									catch (Exception e) {
										// There's a tag like [23,-3] (chunk location) but there's no bounding box tag. Not a good sign, bruv.
										if (GeneralConfig.debugMessages && !world.isRemote) {LogHelper.error("No bounding box detected!");}
									}
								}
							}
		    			}
		    			catch (Exception e) {
		    				if (!world.isRemote && GeneralConfig.debugMessages) {LogHelper.error("Failed to make a village book.");}
		    			}
					}
					
				}
				
			}
			
			
			//--------------------------------------//
			//------------ Village Book ------------//
			//--------------------------------------//
			
			// If you're holding a village book, right clicking a villager will return your village reputation. 
			else if (
					itemstack != null
					&& itemstack.getItem() == ModItems.villageBook
					&& target instanceof EntityVillager
					&& !world.isRemote
					) {
				event.setCanceled(true);
				
				// Revised village-book-reputation to only work in dimension 0 - v3.2.2
				if (villageNearTarget != null && player.dimension==0) // Villager is in a town, so get the rep message
				{
					try
					{
						player.addChatComponentMessage(new ChatComponentText( villagerAssessReputation(playerRep) ) );
						return;
					}
					catch (Exception e) {} // Could not assess reputation -- this might not be a valid village world.
				}
				villagerConfused(player);
				
			}
			
			
			
			//------------------------------//
			//------------ Name ------------//
			//------------------------------//
			
			// If you're holding anything else (or nothing), check to see if the target is a Villager, Village Golem, or entry from the config list.
			else if (!world.isRemote)
			{
				// Update villager trades on interaction
				if (event.target instanceof EntityVillager && GeneralConfig.modernVillagerTrades) {FunctionsVN.monitorVillagerTrades((EntityVillager) event.target);}
				
				// Entity is a custom clickable config entry.
				if ( mappedNamesClickable.get("ClassPaths").contains(targetClassPath) ) {
					
					String PMTMUnloc = "Traveling Merchant";
					String PMTMUnlocModern = "Traveling Merchant";
					String PMShUnloc = "Sheepman";
					String PMShUnlocModern = "Sheepman";
					String PMSSUnloc = "Sheepman Smith";
					
					// Unfortunately, I18n are client-side only and will crash the server. So we're going to just have to deal with the English versions.
					if ( (customName.trim()).equals("") || customName.equals(null)
						||
						 (targetClassPath.equals("net.daveyx0.primitivemobs.entity.passive.EntityTravelingMerchant")
								 //&& customName.equals("Traveling Merchant")
								 && 
								 ( customName.equals( PMTMUnloc )
									|| customName.equals( PMTMUnlocModern ) )// Contingency in there specifically for PM's Traveling Merchants
								 )
						 ||
						 (targetClassPath.equals("net.daveyx0.primitivemobs.entity.passive.EntitySheepman")
								 //&& customName.equals("Sheepman")
								 &&
								 ( customName.equals( PMShUnloc )
									|| customName.equals( PMShUnlocModern ) ) // Contingency in there specifically for PM's Sheepman
								 ) // Contingency in there specifically for PM's Sheepmen
						 ||
						 (targetClassPath.equals("net.daveyx0.primitivemobs.entity.passive.EntitySheepmanSmith")
								 &&
								 ( customName.equals( PMSSUnloc ) ) // Contingency in there specifically for PM's Sheepman Smith
								 ) // Contingency in there specifically for PM's Sheepmen Smith
							) {
						
						// Generate a name type that's defined in the config entry
						
						String mappedNameType = "villager";
						try {
							// Get the index number where targetClassPath is located, and then pull the NameTypes of that same index
							mappedNameType = (String) ((mappedNamesClickable.get("NameTypes")).get( mappedNamesClickable.get("ClassPaths").indexOf(targetClassPath) ));
						}
						catch (Exception e) { // Your config file was poorly formatted
							if(!world.isRemote) LogHelper.error("Your othermods.cfg > Clickable Names entries returned an error. Check to make sure they're formatted properly!");
						}
						
						String[] newNameList = NameGenerator.newRandomName(mappedNameType, new Random());
						String newCustomName = ( newNameList[1].trim() + " " + newNameList[2].trim() + " " + newNameList[3].trim() ).trim(); // Generate new name
						// Generate profession tag
						if (
								GeneralConfig.addJobToName
								&& ( !(target instanceof EntityVillager) || targetAge>=0 )
								) {
							newCustomName += " " + NameGenerator.getCareerTag(targetClassPath, targetProfession, targetCareer);
						}
						// Apply the name
						target.setCustomNameTag( newCustomName.trim() );
					}
					// Clickable Entity already has a name. You may want to add (or remove) a career tag.
					else if (
							customName.indexOf("(")==-1 && GeneralConfig.addJobToName
							&& ( !(target instanceof EntityVillager) || targetAge>=0 )
							) { // Target is named but does not have job tag: add one!
						String newCustomName = customName + " " + NameGenerator.getCareerTag(targetClassPath, targetProfession, targetCareer);
						// Apply the name
						target.setCustomNameTag( newCustomName.trim() );
					}
					else if (customName.indexOf("(")!=-1 && !GeneralConfig.addJobToName) { // Target has a job tag: remove it...
						target.setCustomNameTag(customName.substring(0, customName.indexOf("(")).trim());
					}
				}
				// Entity is a custom automatic config entry.
				else if ( mappedNamesAutomatic.get("ClassPaths").contains(targetClassPath) ) {
					if ( ((customName.trim()).equals("") || customName.equals(null))
							&&
							!((String) ((mappedNamesAutomatic.get("AddOrRemove")).get( mappedNamesAutomatic.get("ClassPaths").indexOf(targetClassPath) ))).trim().equals("remove")
							) {
						
						// Generate a name type that's defined in the config entry
						
						String mappedNameType = "villager";
						try {
							// Get the index number where targetClassPath is located, and then pull the NameTypes of that same index
							mappedNameType = (String) ((mappedNamesAutomatic.get("NameTypes")).get( mappedNamesAutomatic.get("ClassPaths").indexOf(targetClassPath) ));
						}
						catch (Exception e) { // Your config file was poorly formatted
							if(!world.isRemote) LogHelper.error("Your othermods.cfg > Automatic Names entries returned an error. Check to make sure they're formatted properly!");
						}
						
						// Generate a name type that's defined in the config entry
						String[] newNameList = NameGenerator.newRandomName(mappedNameType, new Random());
						String newCustomName = ( newNameList[1].trim() + " " + newNameList[2].trim() + " " + newNameList[3].trim() ).trim(); // Generate new name
						// Generate profession tag
						if (
								GeneralConfig.addJobToName
								&& ( !(target instanceof EntityVillager) || targetAge>=0 )
								) {
							newCustomName += " " + NameGenerator.getCareerTag(targetClassPath, targetProfession, targetCareer);
						}
						// Apply the name
						target.setCustomNameTag( newCustomName.trim() );
					}
					// Automatic Entity already has a name. You may want to add (or remove) a career tag.
					if (
							customName.indexOf("(")==-1 && GeneralConfig.addJobToName
							&& ( !(target instanceof EntityVillager) || targetAge>=0 )
							) { // Target is named but does not have job tag: add one!
						String newCustomName = customName + " " + NameGenerator.getCareerTag(targetClassPath, targetProfession, targetCareer);
						// Apply the name
						target.setCustomNameTag( newCustomName.trim() );
					}
					else if (customName.indexOf("(")!=-1 && !GeneralConfig.addJobToName) { // Target has a job tag: remove it...
						target.setCustomNameTag(customName.substring(0, customName.indexOf("(")).trim());
					}
				}
				// Entity is a Villager
				else if (target instanceof EntityVillager) {
					EntityMonitorHandler.tickRate = 20; // Abruptly speed up the checker to help sync for achievements.
					if (GeneralConfig.nameEntities) {
						// This is a Villager. If it does not have a custom name, add one.
						if ( (customName.trim()).equals("") || customName.equals(null) ) {
							// Generate root name
							String[] newNameList = NameGenerator.newRandomName("Villager", new Random());
							String newCustomName = ( newNameList[1].trim() + " " + newNameList[2].trim() + " " + newNameList[3].trim() ).trim(); // Generate new name
							// Generate profession tag
							String careerTag = "";
							if (
									GeneralConfig.addJobToName
									&& ( targetAge>=0 )
									) {
								newCustomName += " " + NameGenerator.getCareerTag(targetClassPath, targetProfession, targetCareer);
							}
							// Apply the name
							target.setCustomNameTag( newCustomName.trim() );
						}
						// Villager already has a name. You may want to add (or remove) a career tag.
						else if (
								customName.indexOf("(")==-1 && GeneralConfig.addJobToName
								&& ( targetAge>=0 )
								) { // Villager is named but does not have job tag: add one!
							String newCustomName = customName + " " + NameGenerator.getCareerTag(targetClassPath, targetProfession, targetCareer);
							// Apply the name
							target.setCustomNameTag( newCustomName.trim() );
						}
						else if (customName.indexOf("(")!=-1 && !GeneralConfig.addJobToName) { // Villager has a job tag: remove it...
							target.setCustomNameTag(customName.substring(0, customName.indexOf("(")).trim());
						}
						
					}
				}
				// Entity is a Village Golem
				else if (target instanceof EntityIronGolem && GeneralConfig.nameGolems //&& !targetPlayerCreated
						&& ( (customName.trim()).equals("") || customName.equals(null)) ) {
					// This is a village Golem without a name. Determine whether it is player controlled.
					if (targetPlayerCreated) {
						// Send a message stating that it does not (yet) have a name.
						if(!world.isRemote) player.addChatComponentMessage(new ChatComponentText( "You can give this golem a name tag!" ) );
					}
					else {
						//Determine whether it's in/near a populated village.
						Village villageNearGolem = world.villageCollectionObj.findNearestVillage((int)targetX, (int)targetY, (int)targetZ, villageRadiusBuffer);
						if (villageNearGolem != null) { // The golem is in/near a village
							if ( (customName.trim()).equals("") || customName.equals(null) ) {
								if (villageNearGolem.getNumVillagers() > 0) {
									// and there is at least one resident
									//If it does not have a custom name, add one.
									String[] newNameList = NameGenerator.newRandomName("Golem", new Random());
									String newCustomName = ( newNameList[1].trim() + " " + newNameList[2].trim() + " " + newNameList[3].trim() ).trim(); // Generate new name
									// Does not get a profession tag
									// Apply the name
									target.setCustomNameTag( newCustomName.trim() );
								}
								else if(!world.isRemote) {player.addChatComponentMessage(new ChatComponentText( "There is nobody left to tell you its name." ) );}
							}
							// Golem already has a name.
						}
						else {
							// Golem is not in a town
							if(!world.isRemote) {player.addChatComponentMessage(new ChatComponentText( "There is nobody around to tell you its name." ) );}
						}
					}
				}

				// Entity is some other type
			}
			
		}
		
		// Execute healing procedure with Zombie Villager 
		else if (FunctionsVN.isVanillaZombie(event.target) ) {
            final EntityZombie zombie = (EntityZombie) event.target;

            if (!zombie.worldObj.isRemote) {

                // Check if the player is holding a regular Golden Apple
                final ItemStack item = event.entityPlayer.inventory.getCurrentItem();
                if (item != null && item.getItem() == Items.golden_apple && item.getItemDamage() == 0) {//item.getMetadata() == 0) {

                    // Check if the target is a zombie villager with weakness potion active
                    // Also check if the zombie isn't converting, I only want to track the
                    // player that started the conversion.
                    if (zombie.isVillager() && zombie.isPotionActive(Potion.weakness) && !zombie.isConverting()) {
                    	
                        // Sends info to the special track list
                        ServerInfoTracker.startedCuringZombie(event.entityPlayer.getEntityId(), zombie.getEntityId());
                    }
                }
            }
        }
	}
	

    // Used to check against the Mininum Rep achievement.
    // Will also be used to monitor player rep in 1.9+
    @SubscribeEvent
    public void onPlayerAttackEntity(AttackEntityEvent event) {
    	
    	if (!event.target.worldObj.isRemote
    		&& event.entity.dimension == 0 // Only applies to the Overworld
    		&& event.target instanceof EntityLiving ) {
    		
    		EntityLiving target = (EntityLiving) event.target;
        	
        	if (event.target instanceof EntityVillager
        			|| event.target instanceof EntityIronGolem
        			|| event.target.getClass().getClass().toString().substring(6).equals(ModObjects.WitcheryGuardClass)
        			) {
        		EntityMonitorHandler.tickRate = 10; // Abruptly speed up the checker to help sync for achievements.
        		Village villageNearTarget = target.worldObj.villageCollectionObj.findNearestVillage(
        				MathHelper.floor_double(target.posX), MathHelper.floor_double(target.posY), MathHelper.floor_double(target.posZ), villageRadiusBuffer);
        		EntityPlayerMP playerMP = (EntityPlayerMP) event.entityPlayer;
        		try {
        			// Get the attacker's reputation
        			int playerRep = ReputationHandler.getVNReputationForPlayer(playerMP, ReputationHandler.getVillageTagPlayerIsIn(playerMP), villageNearTarget);
        			
        			if (
        					playerRep <=-30 // Town rep is minimum
                			&& !playerMP.func_147099_x().hasAchievementUnlocked(VillageNames.minrep) // Copied over from EntityPlayerMP
                			) {
        				playerMP.triggerAchievement(VillageNames.minrep);
        				AchievementReward.allFiveAchievements(playerMP);
                	}
        		}
        		catch (Exception e) {}
        	}
    	}
    	
    }
	
	
	
	/**
	 * Generates a "baby villager can't help" string from a pool of possibilities.
	 * Setting forType to "book" or "codex" will add possibilities unique to that type. 
	 */
	public static String babyCantHelpString(String forType) {
		Random random = new Random();
		List<String> babyCantHelpArray = new ArrayList<String>();
		
		// Add in universal messages
		babyCantHelpArray.add("This villager is too young to help you.");
		babyCantHelpArray.add("The child looks uncomfortable with you.");
		babyCantHelpArray.add("You should probably ask an adult.");
		babyCantHelpArray.add("This child just wants to play!");
		babyCantHelpArray.add("This child just wants to frolick!");
		babyCantHelpArray.add("The child looks around nervously.");
		babyCantHelpArray.add("This child is still developing language.");
		babyCantHelpArray.add("Why would you ask a child for such information? That's a bit odd.");
		babyCantHelpArray.add("The child reaches out with dirtied hands. Perhaps you should find another villager.");
		babyCantHelpArray.add("Stop bothering children with this.");
		babyCantHelpArray.add("The child looks away sheepishly.");
		babyCantHelpArray.add("The child sticks out its tongue. This is not productive.");
		// Add in specialized messages
		if ((forType.toLowerCase()).equals("book")) {
			babyCantHelpArray.add("This child is not interested in busywork.");
			babyCantHelpArray.add("Who wants to do homework? Not this kid.");
		}
		else if ((forType.toLowerCase()).equals("codex")) {
			babyCantHelpArray.add("Bookkeeping work is something to grow into.");
			babyCantHelpArray.add("This child is not a full-fledged librarian yet.");
		}
		
		return babyCantHelpArray.get( random.nextInt(babyCantHelpArray.size()) );
	}
	
    /**
     * This method is called when you have to generate a random confusion message from a villager.
     */
    public static void villagerConfused(EntityPlayer player) {
    	boolean messagetype = new Random().nextInt(2) == 0;
    	
    	List<String> cantHelpAdjective = new ArrayList<String>();
    	cantHelpAdjective.add("baffled");
    	cantHelpAdjective.add("befuddled");
    	cantHelpAdjective.add("bewildered");
    	cantHelpAdjective.add("clueless");
    	cantHelpAdjective.add("confused");
    	cantHelpAdjective.add("dumbfounded");
    	cantHelpAdjective.add("mystified");
    	cantHelpAdjective.add("nonplussed");
    	cantHelpAdjective.add("perplexed");
    	cantHelpAdjective.add("puzzled");
    	cantHelpAdjective.add("disoriented");
    	if (messagetype) {
    		cantHelpAdjective.add("lost");
    	}
    	else{
    		cantHelpAdjective.add("helpless");
    	}
    	
		if (!player.worldObj.isRemote) player.addChatComponentMessage(new ChatComponentText(
				messagetype ?
				"The villager seems " + cantHelpAdjective.get(new Random().nextInt(cantHelpAdjective.size())) + ", and glances around." :
				"The villager gives you a " + cantHelpAdjective.get(new Random().nextInt(cantHelpAdjective.size())) + " look."
				) );
    }
    
    /**
     * This method generates a random flavor text depending on the player's village reputation.
     * @return String
     */
    public static String villagerAssessReputation(int playerRep) {
    	// Default strings so that we can have something in case there is an error
    	String[] villagerAssessmentPool = new String[]{
				"You are unable to gauge the villager's opinion of you.",
				"You are unsure how the village feels about you.",
				"This villager isn't interested in gossip.",
				"This villager doesn't know what the others think of you."
			};
    	
    	if (playerRep >= 7) {
    		// Extremely positive reputation
    		villagerAssessmentPool = new String[]{
    				"You have brought great prosperity to this town.",
    				"You are very well regarded in this town!",
    				"You are notably reputable in this village!",
    				"The town views you very favorably!"
    			};
    	}
    	else if (playerRep >= 4) {
    		// Very positive reputation
    		villagerAssessmentPool = new String[]{
    				"The villagers like having you around!",
    				"This villager has heard very good things about you!",
    				"The villagers here would like to get to know you!",
    				"You are well regarded by this town."
    			};
    	}
    	else if (playerRep >= 2) {
    		// Moderately positive reputation
    		villagerAssessmentPool = new String[]{
    				"This villager has heard good things about you!",
    				"There is a general air of cautious trust regarding you.",
    				"You are well regarded by a couple villagers.",
    				"Your presence gives the town some optimism!"
    			};
    	}
    	else if (playerRep >= 1) {
    		// Slightly positive reputation
    		villagerAssessmentPool = new String[]{
    				"The villagers don't mind you being around.",
    				"The villagers are cautious about you, but somewhat interested.",
    				"This villager seems wary, but curious about you.",
    				"You are a stranger, but the villagers seem interested."
    			};
    	}
    	else if (playerRep >= 0) {
    		// Neutral reputation
    		villagerAssessmentPool = new String[]{
    				"The villagers are ambivalent to you.",
    				"The villagers don't know what to make of you.",
    				"Nobody knows what to make of you.",
    				"The villagers are indifferent to you."
    			};
    	}
    	else if (playerRep >= -3) {
    		// Slightly negative reputation
    		villagerAssessmentPool = new String[]{
    				"There are unsavory rumors around town about you.",
    				"This villager seems unhappy with you.",
    				"The village doesn't trust you.",
    				"This villager seems worried about you."
    			};
    	}
    	else if (playerRep >= -9) {
    		// Moderately negative reputation
    		villagerAssessmentPool = new String[]{
    				"You are viewed as a danger.",
    				"Your misdeeds are known throughout the town.",
    				"This villager is visibly afraid of you.",
    				"The village sees you as a menace."
    			};
    	}
    	else if (playerRep >= -18) {
    		// Very negative reputation
    		villagerAssessmentPool = new String[]{
    				"The villagers are all afraid of you.",
    				"Your cruel acts are known to everyone.",
    				"The villagers regard you as evil.",
    				"No villager will sleep soundly until you're gone."
    			};
    	}
    	else {
    		// Extremely negative reputation
    		villagerAssessmentPool = new String[]{
    				"Your vile acts have brought tragedy to this town.",
    				"You have brought devastation to this village.",
    				"You are the stuff of nightmares.",
    				"You are unquestionably wicked."
    			};
    	}
    	return villagerAssessmentPool[new Random().nextInt(villagerAssessmentPool.length)];
    }
    
}

