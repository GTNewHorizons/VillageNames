package astrotibs.villagenames.handler;
import astrotibs.villagenames.config.GeneralConfigHandler;
import astrotibs.villagenames.config.OtherModConfigHandler;
import astrotibs.villagenames.item.ModItems;
import astrotibs.villagenames.name.NameGenerator;
import astrotibs.villagenames.name.NamePieces;
import astrotibs.villagenames.nbt.VNWorldData;
import astrotibs.villagenames.nbt.VNWorldDataAbandonedBase;
import astrotibs.villagenames.nbt.VNWorldDataEndCity;
import astrotibs.villagenames.nbt.VNWorldDataEndIsland;
import astrotibs.villagenames.nbt.VNWorldDataEndTower;
import astrotibs.villagenames.nbt.VNWorldDataFortress;
import astrotibs.villagenames.nbt.VNWorldDataFronosVillage;
import astrotibs.villagenames.nbt.VNWorldDataKoentusVillage;
import astrotibs.villagenames.nbt.VNWorldDataMansion;
import astrotibs.villagenames.nbt.VNWorldDataMineshaft;
import astrotibs.villagenames.nbt.VNWorldDataMonument;
import astrotibs.villagenames.nbt.VNWorldDataMoonVillage;
import astrotibs.villagenames.nbt.VNWorldDataNibiruVillage;
import astrotibs.villagenames.nbt.VNWorldDataStronghold;
import astrotibs.villagenames.nbt.VNWorldDataTemple;
import astrotibs.villagenames.nbt.VNWorldDataVillage;
import astrotibs.villagenames.reference.Reference;
import astrotibs.villagenames.structure.StructureRegistry;
import astrotibs.villagenames.utility.LogHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatComponentText;
import net.minecraft.village.Village;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.MapGenStructureData;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class ItemEventHandler {
	
	// All of this stuff is from sargeant / darthvader45 / whoever
	Random random = new Random();
	
	boolean nameVillagers = GeneralConfigHandler.nameVillagers;
	boolean villagerDropBook = GeneralConfigHandler.villagerDropBook;
	boolean addJobToName = GeneralConfigHandler.addJobToName;
	//String nitwitProfession = GeneralConfigHandler.config.getString("nitwitProfession", "Profession/Career", null, "The career displayed for a Nitwit");
	public static int[] otherModIDs = OtherModConfigHandler.otherModIDs;
	public static int[] vanillaProfMaps = OtherModConfigHandler.vanillaProfMaps;
	public static String entityNameString;
	
	// Galacticraft names
	boolean nameGCAlienVillagers = OtherModConfigHandler.nameGCAlienVillagers;
	
	// More Planets names
	boolean nameMPKoentusVillagers = OtherModConfigHandler.nameMPKoentusVillagers;
	boolean nameMPFronosVillagers = OtherModConfigHandler.nameMPFronosVillagers;	
	boolean nameMPNibiruVillagers = OtherModConfigHandler.nameMPNibiruVillagers;
	
	// Witchery names
	boolean nameWitcheryWitchHunter = OtherModConfigHandler.nameWitcheryWitchHunter;
	boolean nameWitcheryVampire = OtherModConfigHandler.nameWitcheryVampire;
	boolean nameWitcheryHobgoblins = OtherModConfigHandler.nameWitcheryHobgoblins;
	boolean nameWitcheryVillageGuards = OtherModConfigHandler.nameWitcheryVillageGuards;
	
	int villageRadiusBuffer = 32;
	
	@SubscribeEvent
	public void onEntityInteract(EntityInteractEvent event) {
		
		if (
			event.entity instanceof EntityPlayer
			&& !event.entityPlayer.worldObj.isRemote
			) { 
			EntityPlayer player = (EntityPlayer) event.entity;
			ItemStack itemstack = player.inventory.getCurrentItem();
			
			//Use the below to figure out what you're looking at :3
			entityNameString = event.target.getClass().toString();
			//entityNameString = entityNameString.substring(6, entityNameString.length());
			//player.addChatComponentMessage(new ChatComponentText( "This is a "+entityNameString+" right here." ) );
			//LogHelper.info("Interacted with a "+entityNameString);
			
			if (
					!event.entityPlayer.worldObj.isRemote &&
					event.target != null && event.target instanceof EntityVillager
					) {
				
				
				// cast the entity to type EntityVillager
				EntityVillager villager = (EntityVillager)event.target;
				
				int villagerProfession = villager.getProfession();
				int tradeCount = (villager.getRecipes(event.entityPlayer)).size();
				// and read its NBT data
				NBTTagCompound compound = new NBTTagCompound();
				villager.writeEntityToNBT(compound);
				String customName = compound.getString("CustomName");
				int villagerAge = compound.getInteger("Age");
				int villagerCareer = compound.getInteger("Career");
				
				
				/**
				 * 1.8 is stupid. I can't add trades. I have to add this bullshit here
				 * and allow you to create the Codex by clicking ingredients onto the villager.
				 * APPARENTLY 1.9 and on have solved this. We'll see.
				 */
				
				/* 
				 * Now if this target is a Librarian and you're holding
				 * Emerald or gold ingot or iron ingot, there will be a special event.
				 */
				
				if (
						itemstack != null
						&& ( itemstack.getItem() == Items.emerald || itemstack.getItem() == Items.gold_ingot || itemstack.getItem() == Items.iron_ingot )
						&& villagerProfession==1
						//&& villagerCareer==0
						) {
					
					
					if ( villagerAge < 0 ) {
						// Villager is a baby so can't make you a codex.
            			String[] babyCantHelpArray = new String[]{
            					"This villager is too young to help you.",
            					"The child looks uncomfortable with you.",
            					"Bookkeeping work is something to grow into.",
            					"You should probably ask an adult.",
            					"This child just wants to play!",
            					"This child just wants to frolick!",
            					"The child looks around nervously.",
            					"This child is not a full-fledged librarian yet.",
            					"This child is still developing language.",
            					"Why would you ask a child for such information? That's a bit odd.",
            					"The child reaches out with soiled hands. Perhaps you should find another villager.",
            					"Stop bothering children with this.",
            					"The child looks away sheepishly.",
            					"The child sticks out its tongue. This is not productive."
            				};
            			String babyCantHelpString = babyCantHelpArray[random.nextInt(babyCantHelpArray.length)];
            			player.addChatComponentMessage(new ChatComponentText( babyCantHelpString ) );
					}
					else {
						// Villager is an adult. Proceed.
						event.setCanceled(true);
						
						// These are the coordinates of the villager
	            		// used to determine which village center or name sign is closest
	            		double villagerX = event.target.posX;
	            		double villagerY = event.target.posY;
	            		double villagerZ = event.target.posZ;
						
						// Figure out how large the village list is
	            		int radius = 0;
	            		//double distToVillage = 0;
	            		int popSize;
	            		
	            			            		
	            		// Finds the nearest village, but only if the given coordinates are within its bounding radius plus the buffer
	            		Village villageNearVillager = event.entityPlayer.worldObj.villageCollectionObj.findNearestVillage((int)villagerX, (int)villagerY, (int)villagerZ, villageRadiusBuffer);//.findNearestVillage((int)villagerX, (int)villagerY, (int)villagerZ, villageRadiusBuffer);
	            		
	            		if (villageNearVillager != null && player.dimension==0) {
	            			int playerRep = villageNearVillager.getReputationForPlayer(event.entityPlayer.getDisplayName());
	            			if (playerRep < 0) {
	            				// Your reputation is too low.
	            				player.addChatComponentMessage(new ChatComponentText( "The villager does not trust you." ) );
	            			}
	            			else {
	            				// The villager trusts you.
	            				// Now we construct a codex based on material availability.
	            				
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
	            				
	            				
	            				/*
	            				 * We have the totals for emeralds and iron/gold ingots, and the player's rep.
	            				 * Now let's actually do the exchange.
	            				 */
	            				int emeraldReqiured = 4 - (playerRep+2)/5;
	            				int ironRequired = 8 - ((playerRep+1)*5)/12;
	            				int goldRequired = 4 - (playerRep+2)/5;
	            					            				
	            				if (itemstack.getItem() == Items.emerald) { // Trade with emeralds only
	            					if (emeralds >= emeraldReqiured+1) {
		            					for (int i = 0; i < emeraldReqiured+1; i++) {event.entityPlayer.inventory.consumeInventoryItem(Items.emerald);}
		            					EntityItem eitem = (villagerDropBook ? event.target : event.entityPlayer).entityDropItem(new ItemStack(ModItems.codex), 1);
		            			        eitem.delayBeforeCanPickup = 0; //No delay: directly into the inventory!
		            			        
		            			        // Increase the player's reputation for a successful trade
		            			        if(random.nextInt(2)==0) {villageNearVillager.setReputationForPlayer(event.entityPlayer.getDisplayName(), playerRep+1);}
	            					}
	            					else {
	            						player.addChatComponentMessage(new ChatComponentText( "Need more emeralds." ) );
	            					}
	            					
	            				}
	            				else if (itemstack.getItem() == Items.gold_ingot) { // Trade with gold and emeralds
	            					if (emeralds >= emeraldReqiured) {
	            						if (goldIngots >= goldRequired) {
			            					for (int i = 0; i < emeraldReqiured; i++) {event.entityPlayer.inventory.consumeInventoryItem(Items.emerald);}
			            					for (int i = 0; i < goldRequired; i++) {event.entityPlayer.inventory.consumeInventoryItem(Items.gold_ingot);}
			            					EntityItem eitem = (villagerDropBook ? event.target : event.entityPlayer).entityDropItem(new ItemStack(ModItems.codex), 1);
			            			        eitem.delayBeforeCanPickup = 0; //No delay: directly into the inventory!
			            			        
			            			        // Increase the player's reputation for a successful trade
			            			        if(random.nextInt(2)==0) {villageNearVillager.setReputationForPlayer(event.entityPlayer.getDisplayName(), playerRep+1);}
	            						}
	            						else {
	            							player.addChatComponentMessage(new ChatComponentText( "Need more gold ingots." ) );
	            						}
	            						
	            					}
	            					else {
	            						if (goldIngots >= goldRequired) {
	            							player.addChatComponentMessage(new ChatComponentText( "Need more emeralds." ) );
	            						}
	            						else {
	            							player.addChatComponentMessage(new ChatComponentText( "Need more emeralds and gold ingots." ) );
	            						}
	            					}
	            					
	            				}
	            				else if (itemstack.getItem() == Items.iron_ingot) { // Trade with iron and emeralds
	            					if (emeralds >= emeraldReqiured) {
	            						if (ironIngots >= ironRequired) {
			            					for (int i = 0; i < emeraldReqiured; i++) {event.entityPlayer.inventory.consumeInventoryItem(Items.emerald);}
			            					for (int i = 0; i < ironRequired; i++) {event.entityPlayer.inventory.consumeInventoryItem(Items.iron_ingot);}
			            					EntityItem eitem = (villagerDropBook ? event.target : event.entityPlayer).entityDropItem(new ItemStack(ModItems.codex), 1);
			            					eitem.delayBeforeCanPickup = 0; //No delay: directly into the inventory!
			            			        
			            			        // Increase the player's reputation for a successful trade
			            			        if(random.nextInt(2)==0) {villageNearVillager.setReputationForPlayer(event.entityPlayer.getDisplayName(), playerRep+1);}
	            						}
	            						else {
	            							player.addChatComponentMessage(new ChatComponentText( "Need more iron ingots." ) );
	            						}
	            						
	            					}
	            					else {
	            						if (ironIngots >= ironRequired) {
	            							player.addChatComponentMessage(new ChatComponentText( "Need more emeralds." ) );
	            						}
	            						else {
	            							player.addChatComponentMessage(new ChatComponentText( "Need more emeralds and iron ingots." ) );
	            						}
	            					}
	            					
	            				}
	            				
	            				
	            				
	            				
	           				}
	            			
	            		}
	            		else {
	            			// No nearby villages found: let the user know that s/he can't get a Codex.
	            			String[] cantHelpArray = new String[]{
	            					"baffled",
	            					"befuddled",
	            					"confused",
	            					"nonplussed",
	            					"perplexed",
	            					"puzzled",
	            					"disoriented",
	            					"lost",
	            					"helpless",
	            					"uncomfortable"
	            				};
	            			String cantHelpAdjective = cantHelpArray[random.nextInt(cantHelpArray.length)];
	            			player.addChatComponentMessage(new ChatComponentText( "The villager seems " + cantHelpAdjective + ", and glances around." ) );
	            		}
	            		
					}
					
				}
				
				/**
				 * End of trade circumvention bullshit. Thanks for playing.
				 */
				
				
				// I'm hoping what this does is prevent you from naming a villager with a name tag. We'll see.
				else if (
						nameVillagers && 
						itemstack != null
						&& itemstack.getItem() == Items.name_tag
						&& itemstack.hasDisplayName()
						&& !itemstack.getDisplayName().equals(customName)
						&& !player.capabilities.isCreativeMode
						) {
					player.addChatComponentMessage(new ChatComponentText("That's not this villager's name..."));
					event.setCanceled(true);
					villager.setCustomNameTag(customName);
				}
				// Now either create a Village Book if you're holding a normal book...
				else if (
						itemstack != null
						&& itemstack.getItem() == Items.book
						) {
					
					if ( villagerAge < 0 ) {
						// Villager is a baby so can't write you a book.
            			player.addChatComponentMessage(new ChatComponentText( babyCantHelpString() ) );
					}
					else {
						// Villager is an adult. Proceed.
						event.setCanceled(true);
						
						
						// Figure out how large the village list is
	            		int radius = 0;
	            		//double distToVillage = 0;
	            		int popSize;
	            		
	            		// These are the coordinates of the villager
	            		// used to determine which village center or name sign is closest
	            		double villagerX = event.target.posX;
	            		double villagerY = event.target.posY;
	            		double villagerZ = event.target.posZ;
	            		
	            		// Finds the nearest village, but only if the given coordinates are within its bounding radius plus the buffer
	            		Village villageNearVillager = event.entityPlayer.worldObj.villageCollectionObj.findNearestVillage((int)villagerX, (int)villagerY, (int)villagerZ, villageRadiusBuffer);
	            			            		
	            		if (villageNearVillager != null && player.dimension==0) {
	            			int playerRep = villageNearVillager.getReputationForPlayer(event.entityPlayer.getDisplayName());
	            			if (playerRep < 0) {
	            				// Your reputation is too low.
	            				player.addChatComponentMessage(new ChatComponentText( "The villager does not trust you." ) );
	            			}
	            			else {
	            				// The villager trusts you.
	            				radius = villageNearVillager.getVillageRadius();
		            			popSize = villageNearVillager.getNumVillagers();
		            			int CX = villageNearVillager.getCenter().posX; // Village X position
		            			int CY = villageNearVillager.getCenter().posY; // Village Y position
		            			int CZ = villageNearVillager.getCenter().posZ; // Village Z position
		            			
		            			// Now that the villager is determined to be within a most likely village,
		            			// Let's see if we can find a sign near that located village center!
		            			
		    					VNWorldDataVillage data = VNWorldDataVillage.forWorld(event.entity.worldObj);
		    					
		    					// .getTagList() will return all the entries under the specific village name.
		    					NBTTagCompound tagCompound = data.getData();
		    					
		    					Set tagmapKeyset = tagCompound.func_150296_c(); //Gets the town key list: "coordinates"
		    			        
		    					
		    			        Iterator itr = tagmapKeyset.iterator();
		    			        String townSignEntry;
		    			        
		    			        //Placeholders for villagenames.dat tags
		    			        boolean signLocated = false; //Use this to record whether or not a sign was found
		    			        
		    			        
		    			        
		    			        while(itr.hasNext()) {
		    			            Object element = itr.next();
		    			            
		    			            townSignEntry = element.toString(); //Text name of village header (e.g. "x535y80z39")
		    			            //The only index that has data is 0:
		    			            NBTTagCompound tagList = tagCompound.getTagList(townSignEntry, tagCompound.getId()).getCompoundTagAt(0);
		    			            
		    			            int townX = tagList.getInteger("signX");
		    			            int townY = tagList.getInteger("signY");
		    			            int townZ = tagList.getInteger("signZ");
		    			            
		    			            
		    			            Village villageNearSign = event.entityPlayer.worldObj.villageCollectionObj.findNearestVillage(townX, townY, townZ, villageRadiusBuffer);
		    			            
		    			            if (villageNearSign == villageNearVillager) {
		    			            	signLocated = true;
		    			            	if (!event.entityPlayer.worldObj.isRemote) {
		    			            		writeNewVillageBook(event, tagList, villageNearVillager, villagerProfession, villagerCareer);
		    			            		
		    			            		
		    			            	}
		    			            	break;
		    			            }
		    			            
		    			        }
		    			        if (!signLocated) {
		    			        	// No well sign was found that matched the villager's village.
		    			        	// We can assume this is a village WITHOUT a sign. So let's at least give it a name!
		    			        	
		    			        	String[] newVillageName = NameGenerator.newVillageName();
	                        		String headerTags = newVillageName[0];
	                        		String namePrefix = newVillageName[1];
	                        		String nameRoot = newVillageName[2];
	                        		String nameSuffix = newVillageName[3];
	                        		
                            		int[] codeColor_a = NamePieces.codeColors_default;
                            		int townColorMeta = codeColor_a[random.nextInt(codeColor_a.length)];
                            		// Allow a chance for uncommon colors to be used
                            		if (townColorMeta==-1) {
                            			int[] codeColors_remaining_a = NamePieces.codeColors_remaining;
                            			townColorMeta = codeColors_remaining_a[random.nextInt(codeColors_remaining_a.length)];
                            		}
	                        		
	                        		// Make the data bundle to save to NBT
	                        		NBTTagList nbttaglist = new NBTTagList();
	                        		
	                        		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
	                                nbttagcompound1.setInteger("signX", CX);
	                                nbttagcompound1.setInteger("signY", CY);
	                                nbttagcompound1.setInteger("signZ", CZ);
	                                nbttagcompound1.setInteger("townColor", townColorMeta); //In case we want to make clay, carpet, wool, glass, etc
	                                nbttagcompound1.setString("namePrefix", namePrefix);
	                                nbttagcompound1.setString("nameRoot", nameRoot);
	                                nbttagcompound1.setString("nameSuffix", nameSuffix);
	                                nbttaglist.appendTag(nbttagcompound1);
	                                // Save the data under a "Villages" entry with unique name based on sign coords
	                                data.getData().setTag("x"+CX+"y"+CY+"z"+CZ+"_nosign", nbttaglist);
	                                
	                                data.markDirty();
	                        		
	                                signLocated = false;
	                                
	                                //event refers to the interaction with the villager
				            		//tagList is the villagename data
				            		//villageNearVillager is the corresponding vanilla data
				            		writeNewVillageBook(event, nbttagcompound1, villageNearVillager, villagerProfession, villagerCareer);
		    			        	
		    			        }
	            			}
	            		}
	            		else {
	            			// No nearby villages found: let the user know that s/he can't get a Village Book.
	            			// Just to randomize the response the villager gives you if he can't locate a village ;)
	            			String[] cantHelpArray = new String[]{
	            					"baffled",
	            					"befuddled",
	            					"bewildered",
	            					"clueless",
	            					"confused",
	            					"dumbfounded",
	            					"mystified",
	            					"nonplussed",
	            					"perplexed",
	            					"puzzled",
	            				};
	            			String cantHelpAdjective = cantHelpArray[random.nextInt(cantHelpArray.length)];
	            			player.addChatComponentMessage(new ChatComponentText( "The villager gives you a " + cantHelpAdjective + " look." ) );
	            		}
	            		
					}
					
				}
				
				// ...or NAME the villager if he's not named. Open the GUI too.
				else if ( nameVillagers && !player.isSneaking() ) {
					
					if (customName == "") { // Empty name field is represented by "", and not: null
						// Name the villager
						String nameRoot = NameGenerator.newVillagerName();
						if (addJobToName) { // Add job tag in parentheses after the name
							String careerTag = NameGenerator.getCareerTag(villagerProfession, villagerCareer);
							if (careerTag.length()>2) { // If the parentheses aren't empty, display them
								nameRoot += " "+careerTag;
							}
						}
						villager.setCustomNameTag(nameRoot);
					}
					else { // Villager already has a name. You may want to add (or remove) a career tag.
						if (customName.indexOf("(")==-1 && addJobToName) { // Villager is named but does not have job tag: add one!
							String careerTag = NameGenerator.getCareerTag(villagerProfession, villagerCareer);

							if (careerTag.length()>2) { // If the parentheses aren't empty, display them
								String nameRoot = customName+" "+careerTag;
								villager.setCustomNameTag(nameRoot);
							}
						}
						else if (customName.indexOf("(")!=-1 && !addJobToName) { // Villager has a job tag: remove it...
							String nameRoot = customName.substring(0, customName.indexOf("(")-1);
							villager.setCustomNameTag(nameRoot);
						}
					}
				}
				
				
			}
			
			else if (event.target instanceof EntityLiving) {
				
				// cast the entity to type EntityLiving
				EntityLiving target = (EntityLiving)event.target;
				// and read its NBT data
				NBTTagCompound compound = new NBTTagCompound();
				target.writeEntityToNBT(compound);
				String customName = target.getCustomNameTag();
				int villagerAge = compound.getInteger("Age");
				int villagerProfession = compound.getInteger("Profession");
				int villagerCareer = compound.getInteger("Career");

				//LogHelper.info("Age: "+villagerAge+", Profession: "+villagerProfession+", Career: "+villagerCareer);
				
				// Disable name tags for these special entities
				if (
						itemstack != null
						&& itemstack.getItem() == Items.name_tag
						&& itemstack.hasDisplayName()
						//&& player.isSneaking()
						&& !player.capabilities.isCreativeMode
						&& (
							   (nameGCAlienVillagers && entityNameString.equals(Reference.GCAlienVillagerClass))
							   || (nameMPKoentusVillagers && (entityNameString.equals(Reference.MPKoentusVillagerClass) || entityNameString.equals(Reference.MPKoentusVillagerClassModern)) )
							   || (nameMPFronosVillagers && (entityNameString.equals(Reference.MPFronosVillagerClass) || entityNameString.equals(Reference.MPFronosVillagerClassModern)))
							   || (nameMPNibiruVillagers && (entityNameString.equals(Reference.MPNibiruVillagerClass) || entityNameString.equals(Reference.MPNibiruVillagerClassModern)))
							   || (nameWitcheryVillageGuards && entityNameString.equals(Reference.WitcheryGuardClass))
							   || (nameWitcheryVampire && entityNameString.equals(Reference.WitcheryVampireClass))
							   || (nameWitcheryHobgoblins && entityNameString.equals(Reference.WitcheryHobgoblinClass))
							   || (nameWitcheryWitchHunter && entityNameString.equals(Reference.WitcheryHunterClass))
							)
						&& !event.entityPlayer.worldObj.isRemote
						) {
					player.addChatComponentMessage(new ChatComponentText("That name is just an epithet."));
					target.setCustomNameTag(customName);
					event.setCanceled(true);
				}
				
				// Name entities that would draw from same pool (village guard, witch hunter, vampire)
				if (
						(
							(nameWitcheryVillageGuards && entityNameString.equals(Reference.WitcheryGuardClass))
							|| (nameWitcheryWitchHunter && entityNameString.equals(Reference.WitcheryHunterClass))
							|| (nameWitcheryVampire && entityNameString.equals(Reference.WitcheryVampireClass))
							)
						&& !player.isSneaking()
						) {
					
					
					if ( customName.equals("") ) {
						// Name the entity
						String nameRoot = NameGenerator.newVillagerName();
						if (addJobToName) { // Add job tag in parentheses after the name
							if (entityNameString.equals(Reference.WitcheryGuardClass)) {nameRoot += " (Guard)";}
							else if (entityNameString.equals(Reference.WitcheryHunterClass)) {nameRoot += " (Witch Hunter)";}
						}
						target.setCustomNameTag(nameRoot);
					}
					else { // Entity already has a name. You may want to add (or remove) a career tag.
						if (customName.indexOf("(")==-1 && addJobToName) { // Entity is named but does not have job tag: add one!
							String careerTag;
							if (entityNameString.equals(Reference.WitcheryGuardClass)) {
								String nameRoot = customName += " (Guard)";
								target.setCustomNameTag(nameRoot);
								}
							else if (entityNameString.equals(Reference.WitcheryHunterClass)) {
								String nameRoot = customName += " (Witch Hunter)";
								target.setCustomNameTag(nameRoot);
								}
						}
						else if (customName.indexOf("(")!=-1 && !addJobToName) { // Entity has a job tag: remove it...
							String nameRoot = customName.substring(0, customName.indexOf("(")-1);
							target.setCustomNameTag(nameRoot);
						}
					}
				}
				
				
				// Create Alien Village book
				else if (
							(
								entityNameString.equals(Reference.GCAlienVillagerClass)
								|| ( entityNameString.equals(Reference.MPKoentusVillagerClass) || entityNameString.equals(Reference.MPKoentusVillagerClassModern) )
								|| ( entityNameString.equals(Reference.MPFronosVillagerClass) || entityNameString.equals(Reference.MPFronosVillagerClassModern) )
								|| ( entityNameString.equals(Reference.MPNibiruVillagerClass) || entityNameString.equals(Reference.MPNibiruVillagerClassModern) )
							)
						&& itemstack != null
						&& itemstack.getItem() == Items.book
						) {
					if ( villagerAge < 0 ) {
						// Villager is a baby so can't write you a book.
						player.addChatComponentMessage(new ChatComponentText( babyCantHelpString() ) );
					}
					else {
						
						// Villager is an adult. Proceed.
						event.setCanceled(true);
						
						// Figure out how large the village list is
						int radius = 0;
						//double distToVillage = 0;
						int popSize;
						
						// These are the coordinates of the villager
						// used to determine which village center or name sign is closest
						double villagerX = event.target.posX;
						double villagerY = event.target.posY;
						double villagerZ = event.target.posZ;
						
						String s="";
						
						if (entityNameString.equals(Reference.GCAlienVillagerClass)) {s = "MoonVillage";}
						if (entityNameString.equals(Reference.MPKoentusVillagerClass) || entityNameString.equals(Reference.MPKoentusVillagerClassModern)) {s = "KoentusVillage";}
						if (entityNameString.equals(Reference.MPFronosVillagerClass) || entityNameString.equals(Reference.MPFronosVillagerClassModern)) {s = "FronosVillage";}
						if (entityNameString.equals(Reference.MPNibiruVillagerClass) || entityNameString.equals(Reference.MPNibiruVillagerClassModern)) {s = "NibiruVillage";}
						
						
						MapGenStructureData structureData;
		    			World worldIn = player.worldObj;
		    			int[ ] BB = new int[6];
		    			boolean targetIsInsideAlienVillage=false;
		    			
		    			try {
							structureData = (MapGenStructureData)worldIn.perWorldStorage.loadData(MapGenStructureData.class, s);
							NBTTagCompound nbttagcompound = structureData.func_143041_a();
							
							Iterator itr = nbttagcompound.func_150296_c().iterator();
							
							while (itr.hasNext()) {
								Object element = itr.next();
								
								NBTBase nbtbase = nbttagcompound.getTag(element.toString());
								
								if (nbtbase.getId() == 10) {
									NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbtbase;
									
									try {
										int[] boundingBox = nbttagcompound2.getIntArray("BB");
										// Now check to see if the target is inside the feature
										if (
											   villagerX >= boundingBox[0]
											&& villagerY >= boundingBox[1]
											&& villagerZ >= boundingBox[2]
											&& villagerX <= boundingBox[3]
											&& villagerY <= boundingBox[4]
											&& villagerZ <= boundingBox[5]
											) {
											
											// Target is inside bounding box.
											
											targetIsInsideAlienVillage = true;
											
											int ChunkX = nbttagcompound2.getInteger("ChunkX");
											int ChunkZ = nbttagcompound2.getInteger("ChunkZ");
											
											String structureName;
											String[] structureInfoArray = tryGetStructureInfo(s, boundingBox, worldIn);
											
											String namePrefix = structureInfoArray[0];
											String nameRoot = structureInfoArray[1];
											String nameSuffix = structureInfoArray[2];
											
											int signX; int signY; int signZ;
											
											// If none is found, these strings are "null" which parseInt does not like very much
											try {signX = Integer.parseInt(structureInfoArray[3]);} catch (Exception e) {}
											try {signY = Integer.parseInt(structureInfoArray[4]);} catch (Exception e) {}
											try {signZ = Integer.parseInt(structureInfoArray[5]);} catch (Exception e) {}
											
											// If a name was NOT returned, then we need to generate a new one, as is done below:
											
											int[] structureCoords = new int[] {
    												(boundingBox[0]+boundingBox[3])/2,
    												(boundingBox[1]+boundingBox[4])/2,
    												(boundingBox[2]+boundingBox[5])/2,
    												};
											
											if (structureInfoArray[0]==null && structureInfoArray[1]==null && structureInfoArray[2]==null) {
												//Structure has no name. Generate it here.
												
												if (s.equals("MoonVillage")) {
													VNWorldDataMoonVillage data = VNWorldDataMoonVillage.forWorld(target.worldObj);
													structureInfoArray = NameGenerator.newAlienVillageName();
													
													// Gotta copy this thing to each IF condition I think
													String headerTags = structureInfoArray[0];
													namePrefix = structureInfoArray[1];
													nameRoot = structureInfoArray[2];
													nameSuffix = structureInfoArray[3];
													int townColorMeta = 15;
													
													// Make the data bundle to save to NBT
													NBTTagList nbttaglist = new NBTTagList();
													
													NBTTagCompound nbttagcompound1 = new NBTTagCompound();
													signX = structureCoords[0];
													signY = structureCoords[1];
													signZ = structureCoords[2];
													nbttagcompound1.setInteger("signX", signX);
													nbttagcompound1.setInteger("signY", signY);
													nbttagcompound1.setInteger("signZ", signZ);
													nbttagcompound1.setInteger("townColor", townColorMeta); //In case we want to make clay, carpet, wool, glass, etc
													nbttagcompound1.setString("namePrefix", namePrefix);
													nbttagcompound1.setString("nameRoot", nameRoot);
													nbttagcompound1.setString("nameSuffix", nameSuffix);
													nbttaglist.appendTag(nbttagcompound1);
													
													// .getTagList() will return all the entries under the specific village name.
													NBTTagCompound tagCompound = data.getData();
													
													data.getData().setTag("x"+structureCoords[0]+"y"+structureCoords[1]+"z"+structureCoords[2]+"_fromvillager", nbttaglist);
													data.markDirty();
													
												}
												else if (s.equals("KoentusVillage")) {
													VNWorldDataKoentusVillage data = VNWorldDataKoentusVillage.forWorld(target.worldObj);
													structureInfoArray = NameGenerator.newAlienVillageName();
													
													// Gotta copy this thing to each IF condition I think
													String headerTags = structureInfoArray[0];
													namePrefix = structureInfoArray[1];
													nameRoot = structureInfoArray[2];
													nameSuffix = structureInfoArray[3];
													int townColorMeta = 15;
													
													// Make the data bundle to save to NBT
													NBTTagList nbttaglist = new NBTTagList();
													
													NBTTagCompound nbttagcompound1 = new NBTTagCompound();
													signX = structureCoords[0];
													signY = structureCoords[1];
													signZ = structureCoords[2];
													nbttagcompound1.setInteger("signX", signX);
													nbttagcompound1.setInteger("signY", signY);
													nbttagcompound1.setInteger("signZ", signZ);
													nbttagcompound1.setInteger("townColor", townColorMeta); //In case we want to make clay, carpet, wool, glass, etc
													nbttagcompound1.setString("namePrefix", namePrefix);
													nbttagcompound1.setString("nameRoot", nameRoot);
													nbttagcompound1.setString("nameSuffix", nameSuffix);
													nbttaglist.appendTag(nbttagcompound1);
													
													// .getTagList() will return all the entries under the specific village name.
													NBTTagCompound tagCompound = data.getData();
													
													data.getData().setTag("x"+structureCoords[0]+"y"+structureCoords[1]+"z"+structureCoords[2]+"_fromvillager", nbttaglist);
													data.markDirty();
												}
												else if (s.equals("FronosVillage")) {
													VNWorldDataFronosVillage data = VNWorldDataFronosVillage.forWorld(target.worldObj);
													structureInfoArray = NameGenerator.newAlienVillageName();
													
													// Gotta copy this thing to each IF condition I think
													String headerTags = structureInfoArray[0];
													namePrefix = structureInfoArray[1];
													nameRoot = structureInfoArray[2];
													nameSuffix = structureInfoArray[3];
													int townColorMeta = 15;
													
													// Make the data bundle to save to NBT
													NBTTagList nbttaglist = new NBTTagList();
													
													NBTTagCompound nbttagcompound1 = new NBTTagCompound();
													signX = structureCoords[0];
													signY = structureCoords[1];
													signZ = structureCoords[2];
													nbttagcompound1.setInteger("signX", signX);
													nbttagcompound1.setInteger("signY", signY);
													nbttagcompound1.setInteger("signZ", signZ);
													nbttagcompound1.setInteger("townColor", townColorMeta); //In case we want to make clay, carpet, wool, glass, etc
													nbttagcompound1.setString("namePrefix", namePrefix);
													nbttagcompound1.setString("nameRoot", nameRoot);
													nbttagcompound1.setString("nameSuffix", nameSuffix);
													nbttaglist.appendTag(nbttagcompound1);
													
													// .getTagList() will return all the entries under the specific village name.
													NBTTagCompound tagCompound = data.getData();
													
													data.getData().setTag("x"+structureCoords[0]+"y"+structureCoords[1]+"z"+structureCoords[2]+"_fromvillager", nbttaglist);
													data.markDirty();
													
												}
												else if (s.equals("NibiruVillage")) {
													VNWorldDataNibiruVillage data = VNWorldDataNibiruVillage.forWorld(target.worldObj);
													structureInfoArray = NameGenerator.newAlienVillageName();
													
													// Gotta copy this thing to each IF condition I think
													String headerTags = structureInfoArray[0];
													namePrefix = structureInfoArray[1];
													nameRoot = structureInfoArray[2];
													nameSuffix = structureInfoArray[3];
													int townColorMeta = 15;
													
													// Make the data bundle to save to NBT
													NBTTagList nbttaglist = new NBTTagList();
													
													NBTTagCompound nbttagcompound1 = new NBTTagCompound();
													signX = structureCoords[0];
													signY = structureCoords[1];
													signZ = structureCoords[2];
													nbttagcompound1.setInteger("signX", signX);
													nbttagcompound1.setInteger("signY", signY);
													nbttagcompound1.setInteger("signZ", signZ);
													nbttagcompound1.setInteger("townColor", townColorMeta); //In case we want to make clay, carpet, wool, glass, etc
													nbttagcompound1.setString("namePrefix", namePrefix);
													nbttagcompound1.setString("nameRoot", nameRoot);
													nbttagcompound1.setString("nameSuffix", nameSuffix);
													nbttaglist.appendTag(nbttagcompound1);
													
													// .getTagList() will return all the entries under the specific village name.
													NBTTagCompound tagCompound = data.getData();
													
													data.getData().setTag("x"+structureCoords[0]+"y"+structureCoords[1]+"z"+structureCoords[2]+"_fromvillager", nbttaglist);
													data.markDirty();
												}
												structureName = structureInfoArray[1]+" "+structureInfoArray[2]+" "+structureInfoArray[3];
												structureName = structureName.trim();
												
											}
											else {
												//Structure has a name. Unpack it here.
												structureName = structureInfoArray[0]+" "+structureInfoArray[1]+" "+structureInfoArray[2];
												structureName = structureName.trim();
											}
											
											signX = structureCoords[0];
											signY = structureCoords[1];
											signZ = structureCoords[2];
											
							    			// Actually form the book contents and write the book
							    			
							    			//Here are the contents of the book up front
							    			String bookContents = "\n\u00a7l";
							    			
											// I don't care if the structure has a sign. We have to cut the name up into arbitrary sign strings for the book.
							    			String topLine;
							    			String structureType = "";
							    			if (entityNameString.equals(Reference.GCAlienVillagerClass)) {structureType = "MoonVillage";}
							    			else if (entityNameString.equals(Reference.MPKoentusVillagerClass) || entityNameString.equals(Reference.MPKoentusVillagerClassModern)) {structureType = "KoentusVillage";}
							    			else if (entityNameString.equals(Reference.MPFronosVillagerClass) || entityNameString.equals(Reference.MPFronosVillagerClassModern)) {structureType = "FronosVillage";}
							    			else if (entityNameString.equals(Reference.MPNibiruVillagerClass) || entityNameString.equals(Reference.MPNibiruVillagerClassModern)) {structureType = "NibiruVillage";}
							    			
							    			// These lines split top lines into two words
							    			if (structureType.equals("MoonVillage")) {topLine = "Moon Village:";}
							    			else if (structureType.equals("KoentusVillage")) {topLine = "Koentus Village:";}
							    			else if (structureType.equals("FronosVillage")) {topLine = "Fronos Village:";}
							    			else if (structureType.equals("NibiruVillage")) {topLine = "Nibiru Village:";}
							    			else {topLine = structureType+":";}
							    			
							    			String sign0 = new String();
							    			String sign1 = new String();
							    			String sign2 = new String();
							    			String sign3 = new String();
							    			
							    			
							    			String headerTags = GeneralConfigHandler.headerTags;
							    			
							    			if ( (namePrefix.length() + 1 + nameRoot.length()) > 15 ) {
												// Prefix+Root is too long, so move prefix to line 1
												sign0 = headerTags+ topLine.trim();
												sign1 = namePrefix.trim();
												if ( (nameRoot.length() + 1 + nameSuffix.length()) > 15 ) {
													// Root+Suffix is too long, so move suffix to line 3
													sign2 = nameRoot.trim();
													sign3 = nameSuffix.trim();
												}
												else {
													// Fit Root+Suffix onto line 2
													sign2 = (nameRoot+" "+nameSuffix).trim();
												}
											}
											else if ( (namePrefix.length() + 1 + nameRoot.length() + 1 + nameSuffix.length()) <= 15 ) {
												// Whole name fits on one line! Put it all on line 2.
												sign1 = headerTags+ topLine;
												sign2 = (namePrefix+" "+nameRoot+" "+nameSuffix).trim();
											}
											else {
												// Only Prefix and Root can fit together on line 2.
												sign1 = headerTags+ topLine.trim();
												sign2 = (namePrefix+" "+nameRoot).trim();
												sign3 = nameSuffix.trim();
											}
										
							    			// Add name of town
							    			bookContents += "\u00a7r"+topLine;
							    			
							    			if (sign0.length()==0) {
							    				bookContents += "\n" + "\u00a7l"+sign2;
							    			}
							    			else {
							    				bookContents += "\u00a7l"+sign1 + "\n" + "\u00a7l"+sign2;
							    			}
							    			bookContents +=
							    					"\n" + "\u00a7l"+sign3 + 
							    					"\n\n" +
							    					"\u00a7rLocated at:\n"+
							    					"\u00a7rx = \u00a7l"+signX+"\u00a7r\ny = \u00a7l"+signY+"\u00a7r\nz = \u00a7l"+signZ;
							    			
							    			// These lines clarify when a feature is not on the Overworld
							    			if (player.dimension==-1) {
							    				bookContents +=
							    						"\n" +
							    						"\u00a7r(Nether)\n";
							    			}
							    			else if (player.dimension==1) {
							    				bookContents +=
							    						"\n" +
							    						"\u00a7r(End dimension)\n";
							    			}
							    			else if (structureType.equals("MoonVillage")) {
							    				bookContents +=
							    						"\n" +
							    						"\u00a7r(Moon)\n";
							    			}
							    			else if (structureType.equals("KoentusVillage")) {
							    				bookContents +=
							    						"\n" +
							    						"\u00a7r(Koentus)\n";
							    			}
							    			else if (structureType.equals("FronosVillage")) {
							    				bookContents +=
							    						"\n" +
							    						"\u00a7r(Fronos)\n";
							    			}
							    			else if (structureType.equals("NibiruVillage")) {
							    				bookContents +=
							    						"\n" +
							    						"\u00a7r(Nibiru)\n";
							    			}
							    			
							    			List<String> pages = new ArrayList<String>();
							    			
							    			ItemStack book = new ItemStack(ModItems.moonvillageBook);
							    			
							    			// Change the icon up depending on surroundings
							    			if (structureType.equals("MoonVillage")) {book = new ItemStack(ModItems.moonvillageBook);}
							    			else if (structureType.equals("KoentusVillage")) {book = new ItemStack(ModItems.koentusvillageBook);}
							    			else if (structureType.equals("FronosVillage")) {book = new ItemStack(ModItems.fronosvillageBook);}
							    			else if (structureType.equals("NibiruVillage")) {book = new ItemStack(ModItems.nibiruvillageBook);}
							    			
							    			if (book.stackTagCompound == null) {
							    				book.setTagCompound(new NBTTagCompound());
							    			}
							    			
							    			String nameCompound = namePrefix + " " +  nameRoot + " " + nameSuffix;
							    			String authorName = customName;
							    			
							    			// Set the title
							    			book.stackTagCompound.setString("title", nameCompound.trim() );
							    			// Set the author
							    			//book.stackTagCompound.setString("author", authorName );
							    			if (customName!=null && !customName.equals("")) {
							    	        	book.stackTagCompound.setString("author", customName.indexOf("(")!=-1 ? customName.substring(0, customName.indexOf("(")-1) : customName );
							    	        }
							    	        else {
							    	        	book.stackTagCompound.setString("author", "" );
							    	        }
							    			
							    			// Set the book's contents
							    			NBTTagList pagesTag = new NBTTagList();
							    			
							    			// Page 1, with the feature information
							    			pagesTag.appendTag(new NBTTagString(bookContents));
							    			
							    			book.stackTagCompound.setTag("pages", pagesTag);
							    			
							    			// Don't make a book if the sign coords are all -1.
							    			
							    			if (signX!=-1 && signY!=-1 && signZ!=-1) {
							    				// Consume Book
							        			player.inventory.consumeInventoryItem(Items.book);
							        			
							        			// Give the book to the player
							        	        EntityItem eitem = (player).entityDropItem(book, 1);
							        	        eitem.delayBeforeCanPickup = 0; //No delay: directly into the inventory!
							    			}
											
											
											
										}
									
									}
									catch (Exception e) {
										// There's a tag like [23,-3] (chunk location) but there's no bounding box tag.
									}
									
								}
								
							}
		    			}
		    			catch (Exception e) {
	            			LogHelper.info("Failed to make a village book");
	            		}
		    			
		    			
						
						if (!targetIsInsideAlienVillage) {
							// No nearby alien villages found: let the user know that s/he can't get a Village Book.
							// Just to randomize the response the villager gives you if he can't locate a village ;)
							String[] cantHelpArray = new String[]{
									"baffled",
									"befuddled",
									"bewildered",
									"clueless",
									"confused",
									"dumbfounded",
									"mystified",
									"nonplussed",
									"perplexed",
									"puzzled",
								};
							String cantHelpAdjective = cantHelpArray[random.nextInt(cantHelpArray.length)];
							player.addChatComponentMessage(new ChatComponentText( "The villager gives you a " + cantHelpAdjective + " look." ) );
						}
						
					}
				}
				// Alien Villager name pool
				else if (
							(
								(nameGCAlienVillagers && entityNameString.equals(Reference.GCAlienVillagerClass))
								|| (nameMPKoentusVillagers && (entityNameString.equals(Reference.MPKoentusVillagerClass) || entityNameString.equals(Reference.MPKoentusVillagerClassModern)) )
							)
						&& !player.isSneaking()
						) {
					if ( customName.equals("") ) {
						// Name the entity
						String nameRoot = NameGenerator.newAlienVillagerName();
						target.setCustomNameTag(nameRoot);
					}
				}
				// Hobgoblin name pool
				else if (
							(
								(nameMPFronosVillagers && (entityNameString.equals(Reference.MPFronosVillagerClass) || entityNameString.equals(Reference.MPFronosVillagerClassModern)) )
								|| (nameWitcheryHobgoblins && entityNameString.equals(Reference.WitcheryHobgoblinClass))
							)
						&& !player.isSneaking()
						) {
					if ( customName.equals("") ) {
						// Name the entity
						String nameRoot = NameGenerator.newHobgoblinName();
						target.setCustomNameTag(nameRoot);
					}
				}
				// Nibiru villagers can have a profession tag
				else if ( 
						(nameMPNibiruVillagers && (entityNameString.equals(Reference.MPNibiruVillagerClass) || entityNameString.equals(Reference.MPNibiruVillagerClassModern) ))
						&& !player.isSneaking()
						) {
					if (customName == "") { // Empty name field is represented by "", and not: null
						// Name the villager
						String nameRoot = NameGenerator.newHobgoblinName();
						if (addJobToName) { // Add job tag in parentheses after the name
							String careerTag = NameGenerator.getNibiruCareerTag(villagerProfession, villagerCareer);
							if (careerTag.length()>2) { // If the parentheses aren't empty, display them
								nameRoot += " "+careerTag;
							}
						}
						target.setCustomNameTag(nameRoot);
					}
					else { // Villager already has a name. You may want to add (or remove) a career tag.
						if (customName.indexOf("(")==-1 && addJobToName) { // Villager is named but does not have job tag: add one!
							String careerTag = NameGenerator.getNibiruCareerTag(villagerProfession, villagerCareer);
							if (careerTag.length()>2) { // If the parentheses aren't empty, display them
								String nameRoot = customName+" "+careerTag;
								target.setCustomNameTag(nameRoot);
							}
						}
						else if (customName.indexOf("(")!=-1 && !addJobToName) { // Villager has a job tag: remove it...
							String nameRoot = customName.substring(0, customName.indexOf("(")-1);
							target.setCustomNameTag(nameRoot);
						}
					}
				}
				
			}
			
		}
	}
	
	// This method consumes a player's book and gives them a village book.
	private void writeNewVillageBook(EntityInteractEvent event, NBTTagCompound tagList, Village villageNearVillager, int villagerProfession, int villagerCareer) {
		
		String sign0 = tagList.getString("sign0");
        String sign1 = tagList.getString("sign1");
        String sign2 = tagList.getString("sign2");
        String sign3 = tagList.getString("sign3");
        
        int radius = villageNearVillager.getVillageRadius();
		int popSize = villageNearVillager.getNumVillagers();
		int CX = villageNearVillager.getCenter().posX; // Village X position
		int CY = villageNearVillager.getCenter().posY; // Village Y position
		int CZ = villageNearVillager.getCenter().posZ; // Village Z position
		
		int townX = tagList.getInteger("signX");
        int townY = tagList.getInteger("signY");
        int townZ = tagList.getInteger("signZ");
        int townColor = tagList.getInteger("townColor");
        String namePrefix = tagList.getString("namePrefix");
        String nameRoot = tagList.getString("nameRoot");
        String nameSuffix = tagList.getString("nameSuffix");
        String headerTags = GeneralConfigHandler.headerTags;
        String topLine = "Welcome to";
        
        String nameCompound = namePrefix + " " +  nameRoot + " " + nameSuffix;
		
        // cast the entity to type EntityVillager
		EntityVillager villager = (EntityVillager)event.target;
		
		// and read its NBT data
		NBTTagCompound compound = new NBTTagCompound();
		villager.writeEntityToNBT(compound);
		String customName = compound.getString("CustomName");
		int villagerAge = compound.getInteger("Age");
        
		
		//Here are the contents of the book up front
		String bookContents = "\n\u00a7l";
        if ( (sign0+sign1+sign2+sign3).length()==0) {
        	
        	// This town has no sign. We have to cut the name up into arbitrary sign strings.
        	if ( (namePrefix.length() + 1 + nameRoot.length()) > 15 ) {
        		// Prefix+Root is too long, so move prefix to line 1
        		sign0 = headerTags+ topLine.trim();
        		sign1 = namePrefix.trim();
        		if ( (nameRoot.length() + 1 + nameSuffix.length()) > 15 ) {
        			// Root+Suffix is too long, so move suffix to line 3
        			sign2 = nameRoot.trim();
        			sign3 = nameSuffix.trim();
        		}
        		else {
        			// Fit Root+Suffix onto line 2
        			sign2 = (nameRoot+" "+nameSuffix).trim();
        		}
        	}
        	else if ( (namePrefix.length() + 1 + nameRoot.length() + 1 + nameSuffix.length()) <= 15 ) {
        		// Whole name fits on one line! Put it all on line 2.
        		sign1 = headerTags+ topLine;
        		sign2 = (namePrefix+" "+nameRoot+" "+nameSuffix).trim();
        	}
        	else {
        		// Only Prefix and Root can fit together on line 2.
        		sign1 = headerTags+ topLine.trim();
        		sign2 = (namePrefix+" "+nameRoot).trim();
        		sign3 = nameSuffix.trim();
        	}
        }
        
        // Add name of town, but avoid the "welcome to" line
        
        if (sign0.length()==0) {
        	bookContents += "\u00a7l"+sign2;
        }
        else {
        	bookContents += "\u00a7l"+sign1 + "\n" + "\u00a7l"+sign2;
        }
        bookContents +=
        		"\n" + "\u00a7l"+sign3 + 
        		"\n\n" +
        		"\u00a7rLocated at:\n"+
        		"\u00a7rx = \u00a7l"+CX+"\u00a7r\ny = \u00a7l"+CY+"\u00a7r\nz = \u00a7l"+CZ+
        		"\n\n" +
        		"\u00a7rPopulation: \u00a7l" + popSize + "\n" +
        		"\u00a7rRadius: \u00a7l" + radius;
        
        List<String> pages = new ArrayList<String>();
        
		ItemStack book = new ItemStack(ModItems.villageBook);
		
		if (book.stackTagCompound == null) {
            book.setTagCompound(new NBTTagCompound());
        }
        
        book.stackTagCompound.setString("title", nameCompound.trim() ); // Set the title
        // Set the author
        if (customName!=null && !customName.equals("")) {
        	book.stackTagCompound.setString("author", customName.indexOf("(")!=-1 ? customName.substring(0, customName.indexOf("(")-1) : customName );
        }
        else {
        	book.stackTagCompound.setString("author", "" );
        }
        
        // Set the book's contents
        NBTTagList pagesTag = new NBTTagList();
        
        // Page 1, with the town information
        pagesTag.appendTag(new NBTTagString(bookContents));
        
        
        /**
         *  All the machinery to make a second page should only work if the villager is named.
         *  That makes the villager more "personable."
         */
        
        if ( (customName != "" && customName != null) || !nameVillagers ) {
        	double radiusCoef = 64.0f; // Feature search radius is playerRep x tradeCount x radiusCoef
        	double strongholdCoefSquared = 0.5f; // Multiplier to reduce chances of locating a stronghold
        	double nitwitCoef = 2.5f; // Multiplier
            String structureFeature = "";
            int playerRep = villageNearVillager.getReputationForPlayer(event.entityPlayer.getDisplayName());
            int tradeCount = (villager.getRecipes(event.entityPlayer)).size();
            double maxStructureDistance = playerRep * Math.sqrt(tradeCount+1) * radiusCoef; // Maximum radius villager is allowed to report a structure
            //maxStructureDistance = Double.MAX_VALUE; // Guarantees search result
            // Page 2 will be information about nearby features
            double dx;
            double dy;
            double dz;
            double mineshaftDistSq = 0;
            double strongholdDistSq = 0;
            double templeDistSq = 0;
            double villageDistSq = 0;
            double monumentDistSq = 0;
        	double mansionDistSq = 0;
        	int[] nearestMineshaftXYZ;
        	int[] nearestStrongholdXYZ;
        	int[] nearestTempleXYZ;
        	int[] nearestVillageXYZ = new int[3];
        	int[] nearestMonumentXYZ;
        	int[] nearestMansionXYZ;
        	
        	List vlist = event.entityPlayer.worldObj.villageCollectionObj.getVillageList();
        	
        	// Go through list of villages and pick out the closest one that's not this one.
        	double vmaxr = Double.MAX_VALUE;
        	radius = villageNearVillager.getVillageRadius();
        	Iterator vitr = vlist.iterator();
        	double rsq;
        	while (vitr.hasNext()) {
        		Village element = (Village)vitr.next();
        		int vx = element.getCenter().posX;
        		int vy = element.getCenter().posY;
        		int vz = element.getCenter().posZ;
        		dx = vx - villageNearVillager.getCenter().posX;
        		dy = vy - villageNearVillager.getCenter().posY;
        		dz = vz - villageNearVillager.getCenter().posZ;
        		rsq = (dx*dx) + (dy*dy) + (dz*dz) ;
        		if ( rsq < vmaxr && rsq >= ((radius+villageRadiusBuffer)*(radius+villageRadiusBuffer)) ) {
        			vmaxr = rsq;
        			nearestVillageXYZ[0] = vx;
        			nearestVillageXYZ[1] = vy;
        			nearestVillageXYZ[2] = vz;
        		}
        		
        	}
        	        	
    		double[] villagerCoords = {event.target.posX, event.target.posY, event.target.posZ};
    		double[] villageCoords = {villageNearVillager.getCenter().posX, villageNearVillager.getCenter().posY, villageNearVillager.getCenter().posZ};
    		
        	String closestStructure = "";
        	int[] closestCoords = new int[3];
        	
        	// Convert a non-vanilla profession into a vanilla one for the purposes of generating a hint page
        	int villagerMappedProfession = 0; //On a failure, direct it to "Farmer"
        	try {villagerMappedProfession = (villagerProfession >= 0 && villagerProfession <= 5) ? villagerProfession : vanillaProfMaps[indexOfIntArr(otherModIDs, villagerProfession)];}
        	catch (Exception e){}
        	
            switch (villagerMappedProfession) {
    	        case 0: // Villager is a Farmer
    	        	if (villagerCareer == 2) {
    	        		// Villager is a fisherman. Find an ocean monument.
    	        		nearestMonumentXYZ = nearestStructureLoc("Monument", event);
    	        		dx = nearestMonumentXYZ[0] - villagerCoords[0];
    	        		dy = nearestMonumentXYZ[1] - villagerCoords[1];
    	        		dz = nearestMonumentXYZ[2] - villagerCoords[2];
    	        		if (
    	        				!(nearestMonumentXYZ[0] == 0 &&
    	        				nearestMonumentXYZ[1] == 0 &&
    	        				nearestMonumentXYZ[2] == 0) &&
    	        				(dx*dx) + (dy*dy) + (dz*dz) <= maxStructureDistance*maxStructureDistance
    	        				) {
    	        			// Monument found. Write a page about it.
    	        			closestStructure = "Monument";
                			closestCoords = nearestMonumentXYZ;
    	        		}
    	        	}
    	        	else {
    	        		// Villager is another kind of farmer. Find another village.
    	        		// FIND NEXT NEAREST VILLAGE: THIS WILL BE AWESOME ;____;
    	        		dx = nearestVillageXYZ[0] - villagerCoords[0];
    	        		dy = nearestVillageXYZ[1] - villagerCoords[1];
    	        		dz = nearestVillageXYZ[2] - villagerCoords[2];
    	        		if (
    	        				!(nearestVillageXYZ[0] == 0 &&
    	        					nearestVillageXYZ[1] == 0 &&
    	        					nearestVillageXYZ[2] == 0) &&
    	        				(dx*dx) + (dy*dy) + (dz*dz) <= maxStructureDistance*maxStructureDistance
    	        				) {
    	        			// Village found. Write a page about it.
    	        			closestStructure = "Village";
                			closestCoords = nearestVillageXYZ;
    	        		}
    	        	}
    	        	
    	        	break;
    	        	
    	        case 1: // Villager is a Librarian. Find a Stronghold or a Woodland Mansion.
    	        	nearestStrongholdXYZ = nearestStructureLoc("Stronghold", event);
    	        	dx = nearestStrongholdXYZ[0] - villagerCoords[0];
            		dy = nearestStrongholdXYZ[1] - villagerCoords[1];
            		dz = nearestStrongholdXYZ[2] - villagerCoords[2];
            		if (
            				!(
            						nearestStrongholdXYZ[0] == 0 &&
            						nearestStrongholdXYZ[1] == 0 &&
            						nearestStrongholdXYZ[2] == 0) &&
            				(dx*dx) + (dy*dy) + (dz*dz) <= maxStructureDistance*maxStructureDistance*strongholdCoefSquared
            				) {
            			// Stronghold found.
            			strongholdDistSq = (dx*dx) + (dy*dy) + (dz*dz);
            		}
    	        	nearestMansionXYZ = nearestStructureLoc("Mansion", event);
    	        	dx = nearestMansionXYZ[0] - villagerCoords[0];
            		dy = nearestMansionXYZ[1] - villagerCoords[1];
            		dz = nearestMansionXYZ[2] - villagerCoords[2];
            		if (
            				!(
            						nearestMansionXYZ[0] == 0 &&
            						nearestMansionXYZ[1] == 0 &&
            						nearestMansionXYZ[2] == 0) &&
            				(dx*dx) + (dy*dy) + (dz*dz) <= maxStructureDistance*maxStructureDistance
            				) {
            			// Mansion found.
            			mansionDistSq = (dx*dx) + (dy*dy) + (dz*dz);
            		}
            		
            		if (strongholdDistSq==0 && mansionDistSq ==0) {
            			// Nothing legal has been detected.
            		}
            		else if (strongholdDistSq==0 || (strongholdDistSq >= mansionDistSq && mansionDistSq!=0) ) {
            			// Only a Mansion has been legally detected. Report that.
            			closestStructure = "Mansion";
            			closestCoords = nearestMansionXYZ;
            		}
            		else if (mansionDistSq==0 || (strongholdDistSq < mansionDistSq && strongholdDistSq!=0) ) {
            			// Only a Stronghold has been legally detected. Report that.
            			closestStructure = "Stronghold";
            			closestCoords = nearestStrongholdXYZ;
            		}
            		else {
            			// I can't think of any other legal cases.
            		}
    	        	break;
    	        	
    	        case 2: // Villager is a Priest. Find a temple.
    	        	nearestTempleXYZ = nearestStructureLoc("Temple", event);
    	        	dx = nearestTempleXYZ[0] - villagerCoords[0];
            		dy = nearestTempleXYZ[1] - villagerCoords[1];
            		dz = nearestTempleXYZ[2] - villagerCoords[2];
            		if (
            				!(
            						nearestTempleXYZ[0] == 0 &&
            						nearestTempleXYZ[1] == 0 &&
            						nearestTempleXYZ[2] == 0) &&
            				(dx*dx) + (dy*dy) + (dz*dz) <= maxStructureDistance*maxStructureDistance
            				) {
            			// Temple found. Write a page about it.
            			closestStructure = "Temple";
            			closestCoords = nearestTempleXYZ;
            		}
    	        	break;
    	        	
    	        case 3: // Villager is a Blacksmith. Find a mineshaft.
    	        	nearestMineshaftXYZ = nearestStructureLoc("Mineshaft", event);
    	        	dx = nearestMineshaftXYZ[0] - villagerCoords[0];
            		dy = nearestMineshaftXYZ[1] - villagerCoords[1];
            		dz = nearestMineshaftXYZ[2] - villagerCoords[2];
            		if (
            				!(
            						nearestMineshaftXYZ[0] == 0 &&
            						nearestMineshaftXYZ[1] == 0 &&
            						nearestMineshaftXYZ[2] == 0) &&
            				(dx*dx) + (dy*dy) + (dz*dz) <= maxStructureDistance*maxStructureDistance
            				) {
            			// Mineshaft found. Write a page about it.
            			closestStructure = "Mineshaft";
            			closestCoords = nearestMineshaftXYZ;
            		}
    	        	break;
    	        	
    	        case 4: // Villager is a Butcher. Find a temple or a village.
    	        	nearestTempleXYZ = nearestStructureLoc("Temple", event);
    	        	dx = nearestTempleXYZ[0] - villagerCoords[0];
            		dy = nearestTempleXYZ[1] - villagerCoords[1];
            		dz = nearestTempleXYZ[2] - villagerCoords[2];
            		if (
            				!(
            						nearestTempleXYZ[0] == 0 &&
            						nearestTempleXYZ[1] == 0 &&
            						nearestTempleXYZ[2] == 0) &&
            				(dx*dx) + (dy*dy) + (dz*dz) <= maxStructureDistance*maxStructureDistance
            				) {
            			// Temple found.
            			templeDistSq = (dx*dx) + (dy*dy) + (dz*dz);
            		}
            		
            		dx = nearestVillageXYZ[0] - villagerCoords[0];
            		dy = nearestVillageXYZ[1] - villagerCoords[1];
            		dz = nearestVillageXYZ[2] - villagerCoords[2];
            		if (
            				!(nearestVillageXYZ[0] == 0 &&
            					nearestVillageXYZ[1] == 0 &&
            					nearestVillageXYZ[2] == 0) &&
            				(dx*dx) + (dy*dy) + (dz*dz) <= maxStructureDistance*maxStructureDistance
            				) {
            			// Village found.
            			villageDistSq = (dx*dx) + (dy*dy) + (dz*dz);
            		}
            		
            		if (templeDistSq==0 && villageDistSq ==0) {
            			// Nothing legal has been detected.
            		}
            		else if (templeDistSq==0 || (templeDistSq >= villageDistSq && villageDistSq!=0) ) {
            			// Only a Village has been legally detected. Report that.
            			closestStructure = "Village";
            			closestCoords = nearestVillageXYZ;
            		}
            		else if (villageDistSq==0 || (templeDistSq < villageDistSq && templeDistSq!=0) ) {
            			// Only a Temple has been legally detected. Report that.
            			closestStructure = "Temple";
            			closestCoords = nearestTempleXYZ;
            		}
            		else {
            			// I can't think of any other legal cases.
            		}
    	        	break;
    	        	
    	        case 5: // Villager is a Nitwit
    	        	// No structure planned at this time
    	        	double nitwitRadius = playerRep * radiusCoef * nitwitCoef;
    	        	//nitwitRadius = Double.MAX_VALUE; // Guarantees search result
    	        	double nitwitMax = Double.MAX_VALUE;
    	        	
    	        	//Check village radius
    	        	dx = nearestVillageXYZ[0] - villagerCoords[0];
            		dy = nearestVillageXYZ[1] - villagerCoords[1];
            		dz = nearestVillageXYZ[2] - villagerCoords[2];
            		if ( 
            				!(nearestVillageXYZ[0]==0 && nearestVillageXYZ[1]==0 && nearestVillageXYZ[2]==0)
            				&& ( (dx*dx)+(dy*dy)+(dz*dz) <= nitwitRadius*nitwitRadius )
            				&& ( (dx*dx)+(dy*dy)+(dz*dz) <= nitwitMax )
            				) {
            			// Village may be the closest structure.
            			nitwitMax = (dx*dx)+(dy*dy)+(dz*dz);
            			closestStructure = "Village";
            			closestCoords = nearestVillageXYZ;
            		}
            		
            		// Check Mineshaft radius
    	        	nearestMineshaftXYZ = nearestStructureLoc("Mineshaft", event);
    	        	dx = nearestMineshaftXYZ[0] - villagerCoords[0];
            		dy = nearestMineshaftXYZ[1] - villagerCoords[1];
            		dz = nearestMineshaftXYZ[2] - villagerCoords[2];
            		if ( 
            				!(nearestMineshaftXYZ[0]==0 && nearestMineshaftXYZ[1]==0 && nearestMineshaftXYZ[2]==0)
            				&& ( (dx*dx)+(dy*dy)+(dz*dz) <= nitwitRadius*nitwitRadius )
            				&& ( (dx*dx)+(dy*dy)+(dz*dz) <= nitwitMax )
            				) {
            			// Mineshaft may be the closest structure.
            			nitwitMax = (dx*dx)+(dy*dy)+(dz*dz);
            			closestStructure = "Mineshaft";
            			closestCoords = nearestMineshaftXYZ;
            		}
            		
    	        	// Check Stronghold radius
    	        	nearestStrongholdXYZ = nearestStructureLoc("Stronghold", event);
    	        	dx = nearestStrongholdXYZ[0] - villagerCoords[0];
            		dy = nearestStrongholdXYZ[1] - villagerCoords[1];
            		dz = nearestStrongholdXYZ[2] - villagerCoords[2];
            		if ( 
            				!(nearestStrongholdXYZ[0]==0 && nearestStrongholdXYZ[1]==0 && nearestStrongholdXYZ[2]==0)
            				&& ( (dx*dx)+(dy*dy)+(dz*dz) <= nitwitRadius*nitwitRadius*strongholdCoefSquared )
            				&& ( (dx*dx)+(dy*dy)+(dz*dz) <= nitwitMax )
            				) {
            			// Stronghold may be the closest structure.
            			nitwitMax = (dx*dx)+(dy*dy)+(dz*dz);
            			closestStructure = "Stronghold";
            			closestCoords = nearestStrongholdXYZ;
            		}
            		
    	        	// Check Temple radius
    	        	nearestTempleXYZ = nearestStructureLoc("Temple", event);
    	        	dx = nearestTempleXYZ[0] - villagerCoords[0];
            		dy = nearestTempleXYZ[1] - villagerCoords[1];
            		dz = nearestTempleXYZ[2] - villagerCoords[2];
            		if ( 
            				!(nearestTempleXYZ[0]==0 && nearestTempleXYZ[1]==0 && nearestTempleXYZ[2]==0)
            				&& ( (dx*dx)+(dy*dy)+(dz*dz) <= nitwitRadius*nitwitRadius )
            				&& ( (dx*dx)+(dy*dy)+(dz*dz) <= nitwitMax )
            				) {
            			// Temple may be the closest structure.
            			nitwitMax = (dx*dx)+(dy*dy)+(dz*dz);
            			closestStructure = "Temple";
            			closestCoords = nearestTempleXYZ;
            		}
    	        	
    	        	// Check Monument radius
    	        	nearestMonumentXYZ = nearestStructureLoc("Monument", event);
    	        	dx = nearestMonumentXYZ[0] - villagerCoords[0];
            		dy = nearestMonumentXYZ[1] - villagerCoords[1];
            		dz = nearestMonumentXYZ[2] - villagerCoords[2];
            		if ( 
            				!(nearestMonumentXYZ[0]==0 && nearestMonumentXYZ[1]==0 && nearestMonumentXYZ[2]==0)
            				&& ( (dx*dx)+(dy*dy)+(dz*dz) <= nitwitRadius*nitwitRadius )
            				&& ( (dx*dx)+(dy*dy)+(dz*dz) <= nitwitMax )
            				) {
            			// Monument may be the closest structure.
            			nitwitMax = (dx*dx)+(dy*dy)+(dz*dz);
            			closestStructure = "Monument";
            			closestCoords = nearestMonumentXYZ;
            		}
    	        	
    	        	// Check Mansion radius
    	        	nearestMansionXYZ = nearestStructureLoc("Mansion", event);
    	        	dx = nearestMansionXYZ[0] - villagerCoords[0];
            		dy = nearestMansionXYZ[1] - villagerCoords[1];
            		dz = nearestMansionXYZ[2] - villagerCoords[2];
            		if ( 
            				!(nearestMansionXYZ[0]==0 && nearestMansionXYZ[1]==0 && nearestMansionXYZ[2]==0)
            				&& ( (dx*dx)+(dy*dy)+(dz*dz) <= nitwitRadius*nitwitRadius )
            				&& ( (dx*dx)+(dy*dy)+(dz*dz) <= nitwitMax )
            				) {
            			// Mansion may be the closest structure.
            			nitwitMax = (dx*dx)+(dy*dy)+(dz*dz);
            			closestStructure = "Mansion";
            			closestCoords = nearestMansionXYZ;
            		}
    	        	break;
            }
            
            if (!closestStructure.equals("")) {
            	String structureHintPageText = "\n\n" + writeStructureHintPage(closestStructure, closestCoords, villagerProfession, villageCoords, radius, event );
                
                pagesTag.appendTag(new NBTTagString(structureHintPageText));
            }
            
            
            
        }
        //String testString = "lel LEL lel LEL ";
        //pagesTag.appendTag(new NBTTagString("La la la-la la-la herp derp derpadee "+testString+testString+testString+testString+testString));
        
        book.stackTagCompound.setTag("pages", pagesTag);
        
        
        // Consume the book held by the player
        event.entityPlayer.inventory.consumeInventoryItem(Items.book);
        // Give the book to the player
        EntityItem eitem = (villagerDropBook ? event.target : event.entityPlayer).entityDropItem(book, 1);
        eitem.delayBeforeCanPickup = 0; //No delay: directly into the inventory!
	}
	
	/**
	 * Used to find the nearest structure to the target entity by structure name. Returns [0,0,0] if none found.
	 * You can also enter an X and Z position offset for the search. Useful for villages.
	 */
	private int[] nearestStructureLoc(String structureName, EntityInteractEvent event, double xOffset, double zOffset){
        
		int[] structurePos = new int[3];
		
		Map<String, ChunkPosition> nearbyStructures = StructureRegistry.instance.getNearestStructures((WorldServer)event.entityPlayer.worldObj, (int)(event.target.posX + xOffset), (int)event.target.posY, (int)(event.target.posZ + zOffset) );
		
		double max = Double.MAX_VALUE;
		
		int CX = 0;
		//int CY = 0;
		int CZ = 0;
		int radius = 0;
		

		for (Map.Entry<String, ChunkPosition> e : nearbyStructures.entrySet()) {
			ChunkPosition pos = e.getValue();
			
			// First things first. If 
			
			double dx = pos.chunkPosX - event.target.posX;
			double dy = pos.chunkPosY - event.target.posY;
			double dz = pos.chunkPosZ - event.target.posZ;

			double distsq = (dx * dx) + (dy * dy) + (dz * dz);
			
			// Locate nearest component of specified type to specified position
			if (distsq < max && e.getKey().equals(structureName)) {
				// Return the nearest structure of interest
				max = distsq;
				structurePos[0] = pos.chunkPosX;
				structurePos[1] = pos.chunkPosY;
				structurePos[2] = pos.chunkPosZ;
			}
			
			
		}
		return structurePos;
	}
	
	/**
	 * Used to find the nearest structure to the target entity by structure name. Returns [0,0,0] if none found.
	 */
	private int[] nearestStructureLoc(String structureName, EntityInteractEvent event){
		return nearestStructureLoc(structureName, event, 0.0f, 0.0f);
	}
	
	/**
	 * This method generates the additional page about a nearby structure
	 */
	private String writeStructureHintPage(String nearbyStructure, int[] structureCoords, int villagerProfession, double[] villageCoords, int villageRadius, EntityInteractEvent event ) {
		String structureHintPage = "";
		
		// Determine the cardinal direction from the coordinates
		double dx = structureCoords[0] - villageCoords[0];
		double dy = structureCoords[1] - villageCoords[1];
		double dz = structureCoords[2] - villageCoords[2];
		// Distances across surface (2D) or absolute (3d)
		double featureDistance2D = Math.sqrt( (dx*dx)+(dz*dz) );
		double featureDistance3D = Math.sqrt( (dx*dx)+(dy*dy)+(dz*dz) );
		
		double thetaPolar;
		thetaPolar = Math.atan2(-dz, dx);
		
		//Convert angle into a cardinal direction
		String directionString;
		double directionDegrees = (thetaPolar*180/Math.PI);///11.25;
		while (directionDegrees<0) {
			directionDegrees += 360d;
		}
		// Flag for whether structure is under village
		boolean isInVillageBounds=false;
		if ( (dx*dx)+(dz*dz) <= villageRadius*villageRadius ) {
			isInVillageBounds=true;
		}
		
		boolean isBelowGround=false;
		if (nearbyStructure.equals("Mineshaft") || nearbyStructure.equals("Stronghold")){
			isBelowGround=true;
		}
		
		if (directionDegrees <= 11.25){
			directionString = "east";
		}
		else if (directionDegrees <= 33.75){
			directionString = "east-northeast";
		}
		else if (directionDegrees <= 56.25){
			directionString = "northeast";
		}
		else if (directionDegrees <= 78.75){
			directionString = "north-northeast";
		}
		else if (directionDegrees <= 101.25){
			directionString = "north";
		}
		else if (directionDegrees <= 123.75){
			directionString = "north-northwest";
		}
		else if (directionDegrees <= 146.25){
			directionString = "northwest";
		}
		else if (directionDegrees <= 168.75){
			directionString = "west-northwest";
		}
		else if (directionDegrees <= 191.25){
			directionString = "west";
		}
		else if (directionDegrees <= 213.75){
			directionString = "west-southwest";
		}
		else if (directionDegrees <= 236.25){
			directionString = "southwest";
		}
		else if (directionDegrees <= 258.75){
			directionString = "south-southwest";
		}
		else if (directionDegrees <= 281.25){
			directionString = "south";
		}
		else if (directionDegrees <= 303.75){
			directionString = "south-southeast";
		}
		else if (directionDegrees <= 326.25){
			directionString = "southeast";
		}
		else if (directionDegrees <= 348.75){
			directionString = "east-southeast";
		}
		else {
			directionString = "east";
		}
		
		/*
		 * Here's where we'll name the structure.
		 */
		String structureName;
		String[] structureNameArray = tryGetStructureName(nearbyStructure, structureCoords, event);
		
		if (structureNameArray[0]==null && structureNameArray[1]==null && structureNameArray[2]==null) {
			//Structure has no name. Generate it here.
			//VNWorldData data=null;
			
			if (nearbyStructure.equals("Village")) {
				VNWorldDataVillage data = VNWorldDataVillage.forWorld(event.entity.worldObj);
				structureNameArray = NameGenerator.newVillageName();
				
				// Gotta copy this thing to each IF condition I think
				String headerTags = structureNameArray[0];
	    		String namePrefix = structureNameArray[1];
	    		String nameRoot = structureNameArray[2];
	    		String nameSuffix = structureNameArray[3];
	    		int townColorMeta = 15;
	    		
	    		// Make the data bundle to save to NBT
	    		NBTTagList nbttaglist = new NBTTagList();
	    		
	    		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
	            nbttagcompound1.setInteger("signX", structureCoords[0]);
	            nbttagcompound1.setInteger("signY", structureCoords[1]);
	            nbttagcompound1.setInteger("signZ", structureCoords[2]);
	            nbttagcompound1.setInteger("townColor", townColorMeta); //In case we want to make clay, carpet, wool, glass, etc
	            nbttagcompound1.setString("namePrefix", namePrefix);
	            nbttagcompound1.setString("nameRoot", nameRoot);
	            nbttagcompound1.setString("nameSuffix", nameSuffix);
	            nbttaglist.appendTag(nbttagcompound1);
	    		
	    		// .getTagList() will return all the entries under the specific village name.
	    		NBTTagCompound tagCompound = data.getData();
	            
	            data.getData().setTag("x"+structureCoords[0]+"y"+structureCoords[1]+"z"+structureCoords[2]+"_fromvillager", nbttaglist);
	            data.markDirty();
				
			}
			else if (nearbyStructure.equals("Mineshaft")) {
				VNWorldDataMineshaft data = VNWorldDataMineshaft.forWorld(event.entity.worldObj);
				structureNameArray = NameGenerator.newMineshaftName();
				
				// Gotta copy this thing to each IF condition I think
				String headerTags = structureNameArray[0];
	    		String namePrefix = structureNameArray[1];
	    		String nameRoot = structureNameArray[2];
	    		String nameSuffix = structureNameArray[3];
	    		int townColorMeta = 15;
	    		
	    		// Make the data bundle to save to NBT
	    		NBTTagList nbttaglist = new NBTTagList();
	    		
	    		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
	            nbttagcompound1.setInteger("signX", structureCoords[0]);
	            nbttagcompound1.setInteger("signY", structureCoords[1]);
	            nbttagcompound1.setInteger("signZ", structureCoords[2]);
	            nbttagcompound1.setInteger("townColor", townColorMeta); //In case we want to make clay, carpet, wool, glass, etc
	            nbttagcompound1.setString("namePrefix", namePrefix);
	            nbttagcompound1.setString("nameRoot", nameRoot);
	            nbttagcompound1.setString("nameSuffix", nameSuffix);
	            nbttaglist.appendTag(nbttagcompound1);
	    		
	    		// .getTagList() will return all the entries under the specific village name.
	    		NBTTagCompound tagCompound = data.getData();
	            
	            data.getData().setTag("x"+structureCoords[0]+"y"+structureCoords[1]+"z"+structureCoords[2]+"_fromvillager", nbttaglist);
	            data.markDirty();
				
			}
			else if (nearbyStructure.equals("Stronghold")) {
				VNWorldDataStronghold data = VNWorldDataStronghold.forWorld(event.entity.worldObj);
				structureNameArray = NameGenerator.newStrongholdName();
				
				// Gotta copy this thing to each IF condition I think
				String headerTags = structureNameArray[0];
	    		String namePrefix = structureNameArray[1];
	    		String nameRoot = structureNameArray[2];
	    		String nameSuffix = structureNameArray[3];
	    		int townColorMeta = 15;
	    		
	    		// Make the data bundle to save to NBT
	    		NBTTagList nbttaglist = new NBTTagList();
	    		
	    		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
	            nbttagcompound1.setInteger("signX", structureCoords[0]);
	            nbttagcompound1.setInteger("signY", structureCoords[1]);
	            nbttagcompound1.setInteger("signZ", structureCoords[2]);
	            nbttagcompound1.setInteger("townColor", townColorMeta); //In case we want to make clay, carpet, wool, glass, etc
	            nbttagcompound1.setString("namePrefix", namePrefix);
	            nbttagcompound1.setString("nameRoot", nameRoot);
	            nbttagcompound1.setString("nameSuffix", nameSuffix);
	            nbttaglist.appendTag(nbttagcompound1);
	    		
	    		// .getTagList() will return all the entries under the specific village name.
	    		NBTTagCompound tagCompound = data.getData();
	            
	            data.getData().setTag("x"+structureCoords[0]+"y"+structureCoords[1]+"z"+structureCoords[2]+"_fromvillager", nbttaglist);
	            data.markDirty();
				
			}
			else if (nearbyStructure.equals("Temple")) {
				VNWorldDataTemple data = VNWorldDataTemple.forWorld(event.entity.worldObj);
				structureNameArray = NameGenerator.newTempleName();
				
				// Gotta copy this thing to each IF condition I think
				String headerTags = structureNameArray[0];
	    		String namePrefix = structureNameArray[1];
	    		String nameRoot = structureNameArray[2];
	    		String nameSuffix = structureNameArray[3];
	    		int townColorMeta = 15;
	    		
	    		// Make the data bundle to save to NBT
	    		NBTTagList nbttaglist = new NBTTagList();
	    		
	    		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
	            nbttagcompound1.setInteger("signX", structureCoords[0]);
	            nbttagcompound1.setInteger("signY", structureCoords[1]);
	            nbttagcompound1.setInteger("signZ", structureCoords[2]);
	            nbttagcompound1.setInteger("townColor", townColorMeta); //In case we want to make clay, carpet, wool, glass, etc
	            nbttagcompound1.setString("namePrefix", namePrefix);
	            nbttagcompound1.setString("nameRoot", nameRoot);
	            nbttagcompound1.setString("nameSuffix", nameSuffix);
	            nbttaglist.appendTag(nbttagcompound1);
	    		
	    		// .getTagList() will return all the entries under the specific village name.
	    		NBTTagCompound tagCompound = data.getData();
	            
	            data.getData().setTag("x"+structureCoords[0]+"y"+structureCoords[1]+"z"+structureCoords[2]+"_fromvillager", nbttaglist);
	            data.markDirty();
				
			}
			/*
			else if (nearbyStructure.equals("Monument")) {
				VNWorldDataMonument data = VNWorldDataMonument.forWorld(event.entity.worldObj);
				structureNameArray = NameGenerator.newMonumentName();
				
				// Gotta copy this thing to each IF condition I think
				String headerTags = structureNameArray[0];
	    		String namePrefix = structureNameArray[1];
	    		String nameRoot = structureNameArray[2];
	    		String nameSuffix = structureNameArray[3];
	    		int townColorMeta = 15;
	    		
	    		// Make the data bundle to save to NBT
	    		NBTTagList nbttaglist = new NBTTagList();
	    		
	    		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
	            nbttagcompound1.setInteger("signX", structureCoords[0]);
	            nbttagcompound1.setInteger("signY", structureCoords[1]);
	            nbttagcompound1.setInteger("signZ", structureCoords[2]);
	            nbttagcompound1.setInteger("townColor", townColorMeta); //In case we want to make clay, carpet, wool, glass, etc
	            nbttagcompound1.setString("namePrefix", namePrefix);
	            nbttagcompound1.setString("nameRoot", nameRoot);
	            nbttagcompound1.setString("nameSuffix", nameSuffix);
	            nbttaglist.appendTag(nbttagcompound1);
	    		
	    		// .getTagList() will return all the entries under the specific village name.
	    		NBTTagCompound tagCompound = data.getData();
	            
	            data.getData().setTag("x"+structureCoords[0]+"y"+structureCoords[1]+"z"+structureCoords[2]+"_fromvillager", nbttaglist);
	            data.markDirty();
				
			}
			*/
			/*
			else if (nearbyStructure.equals("Mansion")) {
				VNWorldDataMansion data = VNWorldDataMansion.forWorld(event.entity.worldObj);
				structureNameArray = NameGenerator.newMansionName();
				
				// Gotta copy this thing to each IF condition I think
				String headerTags = structureNameArray[0];
	    		String namePrefix = structureNameArray[1];
	    		String nameRoot = structureNameArray[2];
	    		String nameSuffix = structureNameArray[3];
	    		int townColorMeta = 15;
	    		
	    		// Make the data bundle to save to NBT
	    		NBTTagList nbttaglist = new NBTTagList();
	    		
	    		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
	            nbttagcompound1.setInteger("signX", structureCoords[0]);
	            nbttagcompound1.setInteger("signY", structureCoords[1]);
	            nbttagcompound1.setInteger("signZ", structureCoords[2]);
	            nbttagcompound1.setInteger("townColor", townColorMeta); //In case we want to make clay, carpet, wool, glass, etc
	            nbttagcompound1.setString("namePrefix", namePrefix);
	            nbttagcompound1.setString("nameRoot", nameRoot);
	            nbttagcompound1.setString("nameSuffix", nameSuffix);
	            nbttaglist.appendTag(nbttagcompound1);
	    		
	    		// .getTagList() will return all the entries under the specific village name.
	    		NBTTagCompound tagCompound = data.getData();
	            
	            data.getData().setTag("x"+structureCoords[0]+"y"+structureCoords[1]+"z"+structureCoords[2]+"_fromvillager", nbttaglist);
	            data.markDirty();
				
			}
    		*/
			structureName = structureNameArray[1]+" "+structureNameArray[2]+" "+structureNameArray[3];
			structureName = structureName.trim();
			
		}
		else {
			//Structure has a name. Unpack it here.
			structureName = structureNameArray[0]+" "+structureNameArray[1]+" "+structureNameArray[2];
			structureName = structureName.trim();
		}
		
		
		
		int approxDist2D = (int)Math.round(featureDistance2D/100)*100; //Approximates the distance to the structure
		
		String structureString="";
		
		if (nearbyStructure.equals("Village")) {
			String[] structureStringArray = new String[]{
					"We trade with " + structureName + ", ",
					"The villagers of this town trade with " + structureName + ", ",
					"Our trading partner, " + structureName + ", is ",
					"There's another settlement named " + structureName + " that we trade with, ",
					"The village of " + structureName + " is ",
					"A village named " + structureName + " is ",
					"There is a village, " + structureName + ", "
				};
			structureString = structureStringArray[random.nextInt(structureStringArray.length)];
		}
		else if (nearbyStructure.equals("Stronghold")) {
			String[] structureStringArray = new String[]{
					"We have records of a stronghold, " + structureName + ", ",
					"An underground fortress, " + structureName + ", is ",
					"Our records list a stronghold, " + structureName + ", ",
					"A mysterious labyrinth, " + structureName + ", is "
				};
			structureString = structureStringArray[random.nextInt(structureStringArray.length)];
		}
		else if (nearbyStructure.equals("Temple")) {
			String[] structureStringArray = new String[]{
					"An abandoned temple called " + structureName + " is ",
					"A former religious ritual site, " + structureName + ", is ",
					"Some bygone religious sect constructed a temple at the " + structureName + " site. It's ",
					"A previous civilization built the temple of " + structureName + ", "
				};
			structureString = structureStringArray[random.nextInt(structureStringArray.length)];
		}
		else if (nearbyStructure.equals("Mineshaft")) {
			String[] structureStringArray = new String[]{
					"An underground mining site, " + structureName + ", is ",
					"Previous settlers built an underground mining site, " + structureName + ", ",
					"There's a long-deserted mine at the old " + structureName + " site ",
					"Back at the old " + structureName + " site, there should be an abandoned mineshaft. It's ",
					"An abandoned mining structure known as " + structureName + " is "
				};
			structureString = structureStringArray[random.nextInt(structureStringArray.length)];
		}
		else if (nearbyStructure.equals("Monument")) {
			String[] structureStringArray = new String[]{
					"Rumors on the wind speak of a sunken temple, " + structureName + ", ",
					"There are rumors of " + structureName + ", under the sea, ",
					"The monument of " + structureName + "was said to have been dragged into the sea long ago. It's rumored to be ",
					"An old fisherman's tale mentions " + structureName + ", "
				};
			structureString = structureStringArray[random.nextInt(structureStringArray.length)];
		}
		else if (nearbyStructure.equals("Mansion")) {
			String[] structureStringArray = new String[]{
					"We have records of a cult mansion, " + structureName + ", ",
					"We have records of a cult operating at " + structureName + ", ",
					"Our records list a cult mansion, " + structureName + ", ",
					"A mysterious cult meets at a mansion called " + structureName + ", "
				};
			structureString = structureStringArray[random.nextInt(structureStringArray.length)];
		}
		else {
			//There's nothing left, man
		}
		
		if (villagerProfession==5) {
			// Special text for nitwit
			
			if (nearbyStructure.equals("Mineshaft")) {
				String[] structureStringArray = new String[]{
						"I hear there used to be a mine called " + structureName + ", ",
						"There used to be a mine over at the old " + structureName + " site, ",
						"If the wind is still over at the " + structureName + 
							" site, you can hear faint sounds coming from deep underground. You can go check it out if you want: it's "
					};
				structureString = structureStringArray[random.nextInt(structureStringArray.length)];
			}
			else if (nearbyStructure.equals("Stronghold")) {
				String[] structureStringArray = new String[]{
						"I've heard rumors about " + structureName + ", an underground stronghold ",
						"I get really weird supernatural sensations over at the old " + structureName + 
							" site. If you want to dig around under there, it's "
					};
				structureString = structureStringArray[random.nextInt(structureStringArray.length)];
			}
			else if (nearbyStructure.equals("Temple")) {
				String[] structureStringArray = new String[]{
						"In my wanderings I've stumbled upon an abandoned site called "	+ structureName + ", ",
						"I hear there's a temple or something like that over at the "	+ structureName + " religious site, "
					};
				structureString = structureStringArray[random.nextInt(structureStringArray.length)];
			}
			else if (nearbyStructure.equals("Village")) {
				String[] structureStringArray = new String[]{
						"There is another village named " + structureName + ", ",
						"Everyone knows about the town of " + structureName + ", ",
						"My buddy once lost a shoe while visiting " + structureName + ", "
					};
				structureString = structureStringArray[random.nextInt(structureStringArray.length)];
			}
			else if (nearbyStructure.equals("Monument")) {
				String[] structureStringArray = new String[]{
						"I've heard fishermen mention a sunken monument called " + structureName + ", ",
						"The darkest sea tales, whispered in hushed tones, mention " + structureName + 
							", a sunken temple filled with treasure. If you dare to look for it, rumor says it's "
					};
				structureString = structureStringArray[random.nextInt(structureStringArray.length)];
			}
			else if (nearbyStructure.equals("Mansion")) {
				String[] structureStringArray = new String[]{
						"I've heard a lot of very bad things about the cult that practices in "	+ structureName + ", ",
						"Others don't like to talk about it, but everyone here knows about the eerie cult that gathers at "
								+ structureName + ", deep in the dark wood. If you're foolish enough to look, it's "
					};
				structureString = structureStringArray[random.nextInt(structureStringArray.length)];
			}
		}
		
		structureHintPage += "" + structureString;
		
		
		
		/*
		 * Location of the feature
		 */
		
		String[] thisVillageArray = new String[]{
				"this village",
				"this very village"
			};
		String thisVillage = thisVillageArray[random.nextInt(thisVillageArray.length)];
		
		if (isInVillageBounds && isBelowGround){
			// Structure is under this very village.
			structureHintPage += "located underneath "+thisVillage+". ";
		}
		else if (isInVillageBounds && !isBelowGround) {
			// It's in this village and should be visible.
			structureHintPage += "located within "+thisVillage+". ";
		}
		else {
			// Is outside of village.
			
			if (approxDist2D>=100){
				
				String[] approxStringArray = new String[]{
						"approximately",
						"roughly",
						"about"
					};
				String approxString = approxStringArray[random.nextInt(approxStringArray.length)];
				
				structureHintPage += "located "+approxString;
				structureHintPage += " "+approxDist2D+" meters ";
			}
			else {
				String[] approxStringArray = new String[]{
						"less than",
						"under",
						"short of"
					};
				String approxString = approxStringArray[random.nextInt(approxStringArray.length)];
				
				structureHintPage += "located "+approxString;
				structureHintPage += " 100 meters ";
			}
			String[] wordArray = new String[]{
					"due",
					"to the"
				};
			String randomWord = wordArray[random.nextInt(wordArray.length)];
			structureHintPage += randomWord + " " + directionString + ". ";
			
		}
		return structureHintPage;
	}
	
	/**
	 * This method searches the feature you're interested in to see if a name already exists for it
	 * @return {namePrefix, nameRoot, nameSuffix} if something is found; {null, null, null} otherwise
	 */
	private String[] tryGetStructureName(String nearbyStructure, int[] structureCoords, EntityInteractEvent event) {
		
		// Load in names data
		VNWorldData data=null;
		if (nearbyStructure.equals("Village")) {
			data = VNWorldDataVillage.forWorld(event.entity.worldObj);
		}
		else if (nearbyStructure.equals("Mineshaft")) {
			data = VNWorldDataMineshaft.forWorld(event.entity.worldObj);
		}
		else if (nearbyStructure.equals("Stronghold")) {
			data = VNWorldDataStronghold.forWorld(event.entity.worldObj);
		}
		else if (nearbyStructure.equals("Temple")) {
			data = VNWorldDataTemple.forWorld(event.entity.worldObj);
		}
		else if (nearbyStructure.equals("Monument")) {
			data = VNWorldDataMonument.forWorld(event.entity.worldObj);
		}
		else if (nearbyStructure.equals("Mansion")) {
			data = VNWorldDataMansion.forWorld(event.entity.worldObj);
		}
		
		// .getTagList() will return all the entries under the specific village name.
		NBTTagCompound tagCompound = data.getData();
		
		Set tagmapKeyset = tagCompound.func_150296_c(); //Gets the town key list: "x535y80z39"
		
        Iterator itr = tagmapKeyset.iterator();
        String townSignEntry;
        
        String namePrefix=null;
        String nameRoot=null;
        String nameSuffix=null;
        while(itr.hasNext()) {
            Object element = itr.next();
            
            townSignEntry = element.toString(); //Text name of village header (e.g. "x535y80z39")
            //The only index that has data is 0:
            NBTTagCompound tagList = tagCompound.getTagList(townSignEntry, tagCompound.getId()).getCompoundTagAt(0);
            
            // Town's location
            int featureX = tagList.getInteger("signX");
            int featureY = tagList.getInteger("signY");
            int featureZ = tagList.getInteger("signZ");
            
            double sdx = featureX - structureCoords[0];
            double sdy = (featureY==64) ? 0.d : featureY - structureCoords[1]; // A signY of 64 likely means it was detected pre-generation.
            double sdz = featureZ - structureCoords[2];
            
            if ( (sdx*sdx)+(sdy*sdy)+(sdz*sdz) <= 100*100 ) {
            	//This entry has been correctly matched to the structure in question
                namePrefix = tagList.getString("namePrefix"); 
                nameRoot = tagList.getString("nameRoot"); 
                nameSuffix = tagList.getString("nameSuffix"); 
            }
            
        }
		
		return new String[] {namePrefix, nameRoot, nameSuffix};
	}
	
	/**
	 * Enter an int[] and a value, and this will return the first index matching that value, or -1 on a failure.
	 * 
	 */
	public static int indexOfIntArr(int[] intArray, int valToIndex) {
		for (int i=0; i<intArray.length; i++) {
			if(intArray[i]==valToIndex)
				return i;
		}
		return -1;
	}
	
	/**
	 * Generates a "baby villager can't write book" string.
	 * 
	 */
	public static String babyCantHelpString() {
		Random random = new Random();
		String[] babyCantHelpArray = new String[]{
				"This villager is too young to help you.",
				"The child looks uncomfortable with you.",
				"This child is not interested in busywork.",
				"You should probably ask an adult.",
				"This child just wants to play!",
				"This child just wants to frolick!",
				"The child looks around nervously.",
				"Who wants to do homework? Not this kid.",
				"This child is still developing language.",
				"Why would you ask a child for such information? That's a bit odd.",
				"The child reaches out with soiled hands. Perhaps you should find another villager.",
				"Stop bothering children with this.",
				"The child looks away sheepishly.",
				"The child sticks out its tongue. This is not productive."
			};
		return babyCantHelpArray[random.nextInt(babyCantHelpArray.length)];
	}
	
    /**
     * This method searches the feature you're interested in to see if a name already exists for it
     * @return {namePrefix, nameRoot, nameSuffix} if something is found; {null, null, null} otherwise
     */
    public static String[] tryGetStructureInfo(String structureType, int[] structureBB, World world) {
    	
    	// Load in names data
    	VNWorldData data=null;
    	if (structureType.equals("Village")) {
    		data = VNWorldDataVillage.forWorld(world);
    	}
    	else if (structureType.equals("Mineshaft")) {
    		data = VNWorldDataMineshaft.forWorld(world);
    	}
    	else if (structureType.equals("Stronghold")) {
    		data = VNWorldDataStronghold.forWorld(world);
    	}
    	else if (structureType.equals("Temple")) {
    		data = VNWorldDataTemple.forWorld(world);
    	}
    	else if (structureType.equals("Monument")) {
    		data = VNWorldDataMonument.forWorld(world);
    	}
    	else if (structureType.equals("Mansion")) {
    		data = VNWorldDataMansion.forWorld(world);
    	}
    	else if (structureType.equals("Fortress")) {
    		data = VNWorldDataFortress.forWorld(world);
    	}
    	else if (structureType.equals("EndCity")) {
    		data = VNWorldDataEndCity.forWorld(world);
    	}
    	else if (structureType.equals("MoonVillage")) {
    		data = VNWorldDataMoonVillage.forWorld(world);
    	}
    	else if (structureType.equals("KoentusVillage")) {
    		data = VNWorldDataKoentusVillage.forWorld(world);
    	}
    	else if (structureType.equals("hardcoreenderdragon_EndTower")) {
    		data = VNWorldDataEndTower.forWorld(world);
    	}
    	else if (structureType.equals("hardcoreenderdragon_EndIsland")) {
    		data = VNWorldDataEndIsland.forWorld(world);
    	}
    	else if (structureType.equals("FronosVillage")) {
    		data = VNWorldDataFronosVillage.forWorld(world);
    	}
    	else if (structureType.equals("NibiruVillage")) {
    		data = VNWorldDataNibiruVillage.forWorld(world);
    	}
    	else if (structureType.equals("GC_AbandonedBase")) {
    		data = VNWorldDataAbandonedBase.forWorld(world);
    	}
    	
    	// .getTagList() will return all the entries under the specific village name.
    	NBTTagCompound tagCompound = data.getData();
    	
    	Set tagmapKeyset = tagCompound.func_150296_c(); //Gets the town key list: "coordinates"
    	
    	Iterator itr = tagmapKeyset.iterator();
    	String featureSignLoc;
    	
    	String namePrefix=null;
    	String nameRoot=null;
    	String nameSuffix=null;
    	String signX=null;
    	String signY=null;
    	String signZ=null;
    	
    	while(itr.hasNext()) {
    		Object element = itr.next();
    		
    		featureSignLoc = element.toString(); //Text name of feature header (e.g. "x535y80z39")
    		//The only index that has data is 0:
    		NBTTagCompound tagList = tagCompound.getTagList(featureSignLoc, tagCompound.getId()).getCompoundTagAt(0);
    		
    		int[] structureCoords = new int[] {tagList.getInteger("signX"), tagList.getInteger("signY"), tagList.getInteger("signZ")};
    		
    		// A signY of 64 likely means it was detected pre-generation.
    		// The below code detects if you're in the 3D bounding box if and only if signY is not 64.
    		// If signY is 64, the code detects if you're in the 2D (x vs z) bounding box.
    		
    		if (
    			    structureCoords[0] >= structureBB[0]
				 && structureCoords[2] >= structureBB[2]
				 && structureCoords[0] <= structureBB[3]
				 && structureCoords[2] <= structureBB[5]
    			 && ( structureCoords[1] == 64  ||  (structureCoords[1] >= structureBB[1] && structureCoords[1] <= structureBB[4] ) )
				) {
    			//This entry has been correctly matched to the structure in question
    			namePrefix = tagList.getString("namePrefix"); 
    			nameRoot = tagList.getString("nameRoot"); 
    			nameSuffix = tagList.getString("nameSuffix"); 
    			signX = ""+structureCoords[0];
    			signY = ""+structureCoords[1];
    			signZ = ""+structureCoords[2];
    		}
    		
    	}
    	
    	return new String[] {namePrefix, nameRoot, nameSuffix, signX, signY, signZ};
    }
	
}
