package astrotibs.villagenames.handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import astrotibs.villagenames.VillageNames;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.ieep.ExtendedVillageGuard;
import astrotibs.villagenames.ieep.ExtendedVillager;
import astrotibs.villagenames.ieep.ExtendedZombieVillager;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.network.MessageModernVillagerSkin;
import astrotibs.villagenames.network.MessageZombieVillagerProfession;
import astrotibs.villagenames.network.NetworkHelper;
import astrotibs.villagenames.prismarine.minecraft.Vec3i;
import astrotibs.villagenames.tracker.ClientInfoTracker;
import astrotibs.villagenames.tracker.EventTracker;
import astrotibs.villagenames.tracker.ServerInfoTracker;
import astrotibs.villagenames.tracker.ServerInfoTracker.EventType;
import astrotibs.villagenames.utility.FunctionsVN;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * Adapted from Villager Tweaks by sidben:
 * https://github.com/sidben/VillagerTweaks/blob/master/src/main/java/sidben/villagertweaks/handler/EntityMonitorHandler.java
 * @author AstroTibs
 */
public class EntityMonitorHandler
{
	protected static int tickRate = 50; // Number of ticks between monitoring
	
    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
    	
        if (event.entity instanceof EntityVillager) {
            if (FunctionsVN.isVanillaZombie(event.source.getEntity())) {

                // A villager was killed by a zombie and may be zombified. Adds to the tracker for future check.
                final EntityVillager villager = (EntityVillager) event.entity;
                ServerInfoTracker.add(villager);

                if (GeneralConfig.debugMessages) {
                    LogHelper.info("EntityMonitorHandler > A zombie just killed villager " 
                    		+ ( villager.getCustomNameTag().equals("")||villager.getCustomNameTag().equals(null) ? "(None)" : villager.getCustomNameTag() ) 
                    		+ " [" + villager.getEntityId() + "] "
                    		+ "at [" + 
                    		//villager.getPosition(1.0F)
                    		//Vec3.createVectorHelper(villager.posX, villager.posY, villager.posZ) // Changed because of server crash
                    		new Vec3i(villager.posX, villager.posY + 0.5D, villager.posZ)
                    		+ "], profession [" + villager.getProfession() + "]");
                }
            }
        }
        
    }
    
	
    @SubscribeEvent
    public void onPlayerStartTracking(PlayerEvent.StartTracking event) {
    	
    	if (!event.entity.worldObj.isRemote) // Encased in notremote if - v3.1
    	{
        	// Added in v3.1
        	if (
        			event.target instanceof EntityVillager
        			&& GeneralConfig.villagerCareers // Removed not-remote condition - v3.1
        			)
        	{
        		final EntityVillager villager = (EntityVillager) event.target;
        		final ExtendedVillager properties = ExtendedVillager.get(villager);
        		NetworkHelper.sendModernVillagerSkinMessage(villager.getEntityId(), properties, event.entityPlayer);
        	}
        	
            // Check if the player started tracking a zombie villager (happens on server-side).
        	else if (FunctionsVN.isVanillaZombie(event.target)) { // Removed not-remote condition - v3.1
                final EntityZombie zombie = (EntityZombie) event.target;

                if (zombie.isVillager()) {
                    // Check if the zombie has special properties
                    final ExtendedZombieVillager properties = ExtendedZombieVillager.get(zombie);
                    if (properties != null) {
                        NetworkHelper.sendZombieVillagerProfessionMessage(zombie.getEntityId(), properties, event.entityPlayer);
                    }
                }
            }
            
            // Check if the player started tracking a village guard
        	else if (event.entity.getClass().toString().substring(6).equals(ModObjects.WitcheryGuardClass)) { // Removed not-remote condition and added ELSE - v3.1
                //final EntityZombie zombie = (EntityZombie) event.target;
            	final EntityLiving guard = (EntityLiving) event.target;


                // Check if the guard has special properties
                final ExtendedVillageGuard properties = ExtendedVillageGuard.get(guard);
                if (properties != null) {
                    NetworkHelper.sendVillageGuardMessage(guard.getEntityId(), properties, event.entityPlayer);
                }

            }
    		
    	}
    	
    }
    

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {

    	// Renovated in v3.1
    	
    	// summon Zombie ~ ~ ~ {IsVillager:1}
    	// New entity is a Zombie. Check to see if it came into being via a killed Villager.
        if (
        		FunctionsVN.isVanillaZombie(event.entity)
        		&& ((EntityZombie)event.entity).isVillager()
        		)
        {
            final EntityZombie zombie = (EntityZombie) event.entity;
            
            ExtendedZombieVillager ezv = ExtendedZombieVillager.get(zombie);
            
            // Renovated in v3.1
            if (event.world.isRemote) {
                // Looks for info sent by the server that should be applied to the zombie (e.g. villager profession)
                ClientInfoTracker.SyncZombieMessage(zombie);
            }
            else {
            	
            	// Moved into server-side world in v3.2
        		// Try to assign a biome number if this villager has none.
                if (ezv.getBiomeType() <0)
                {
                	ezv.setBiomeType(FunctionsVN.returnBiomeTypeForEntityLocation(zombie));
                }
                
                // Added in v3.2
                if (ezv.getSkinTone() == -99)
                {ezv.setSkinTone(FunctionsVN.returnSkinToneForEntityLocation(zombie));}
                
            	
                // Looks on the event tracker for a villager that just died
                //final EventTracker tracked = ServerInfoTracker.seek(EventType.VILLAGER, zombie.getPosition(1.0F));//zombie.getPosition());
            	final EventTracker tracked = ServerInfoTracker.seek(EventType.VILLAGER,
            			//Vec3.createVectorHelper(zombie.posX, zombie.posY, zombie.posZ)
            			new Vec3i(zombie.posX, zombie.posY + 0.5D, zombie.posZ)
            			); // Replaced because of mp server-side crash
            	
                if (tracked != null) {
                    if (GeneralConfig.debugMessages) {
                        LogHelper.info("EntityMonitorHandler > Found info on the tracker--must copy to zombie");
                    }

                    // If found, copy the data from the villager
                    tracked.updateZombie(event, ezv);
                }
                else if (ezv.getProfession() == -1) {
                    if (GeneralConfig.debugMessages) {
                        LogHelper.info("EntityMonitorHandler > No info on the tracker--assigning a random profession");
                    }

                    // If not, assign a random profession
                    if (GeneralConfig.villagerCareers) {
                    	ezv.pickRandomProfessionAndCareer();
                    }
                    else {
                    	ezv.pickRandomProfession();
                    }
                }
                
                if (GeneralConfig.debugMessages) {
                    LogHelper.info("EntityMonitorHandler > Custom name [" + zombie.getCustomNameTag() + "]");
                    LogHelper.info("EntityMonitorHandler > Profession [" + ezv.getProfession() + "]");
                    if (GeneralConfig.villagerCareers) LogHelper.info("EntityMonitorHandler > Career [" + ezv.getCareer() + "]");
                }

            }

        }
        
        
        // New entity is a villager. Check to see if it came into being via a cured villager-zombie.
        else if (event.entity instanceof EntityVillager) {
        	
        	EntityVillager villager = (EntityVillager) event.entity;
            
            if (GeneralConfig.modernVillagerTrades) {FunctionsVN.monitorVillagerTrades(villager);}
            
            
        	// Added in v3.1
            ExtendedVillager ev = ExtendedVillager.get(villager);
            
            // Renovated in v3.1
            if (event.world.isRemote)
            {
                // Looks for info sent by the server that should be applied to the zombie (e.g. villager profession)
                ClientInfoTracker.syncModernVillagerMessage(villager);
            }
            else
            {
                // Looks on the event tracker for a zombie that was cured
                final EventTracker tracked = ServerInfoTracker.seek(
                		EventType.ZOMBIE,
                		//Vec3.createVectorHelper(villager.posX, villager.posY, villager.posZ) // Replaced because of mp server-side crash
                		new Vec3i(villager.posX, villager.posY + 0.5D, villager.posZ)
                		);//.getPosition());

                if (tracked != null) {
                	// This is a cured Villager Zombie.
                	
                    if (GeneralConfig.debugMessages) {
                        LogHelper.info("EntityMonitorHandler > Found info on the tracker--must copy to villager");
                    }

                    // If found, copy the data from the zombie
                    tracked.updateVillager(villager, ev);

                    // Sends info to the special track list
                    ServerInfoTracker.endedCuringZombie(tracked.getEntityID(), villager.getEntityId());

                    ServerInfoTracker.removeCuredZombiesFromTracker(event.world, tracked.getEntityID());

                }
                /*
                // Moved inside server-side check in v3.2
        		// Try to assign a biome number if this villager has none.
                if (
                		ev != null
                		//&& villager.getProfession() >=0
                		//&& villager.getProfession() <=5
                		&& (ExtendedVillager.get(villager)).getBiomeType()<0
                		)
                {
                	(ExtendedVillager.get(villager)).setBiomeType(FunctionsVN.returnBiomeTypeForEntityLocation(villager));
                }
                
                // v3.2 Try to assign a skin tone number if this villager has none.
                if (
                		ev != null
                		&& (ExtendedVillager.get(villager)).getSkinTone()==-99
                		)
                {
                	(ExtendedVillager.get(villager)).setSkinTone(FunctionsVN.returnSkinToneForEntityLocation(villager));
                }
                */
            }
            
            
        }
        
    }
    


    @SubscribeEvent
    public void onLivingUpdateEvent(LivingUpdateEvent event) {
    	
        // Check if a zombie is about to convert to villager
        if (FunctionsVN.isVanillaZombie(event.entity)) {
            final EntityZombie zombie = (EntityZombie) event.entity;

            // Based on the [onUpdate] event from zombies
            if (!zombie.worldObj.isRemote && zombie.isConverting()) {
            	
            	double checkfactor = 10; // This determines how (many times) frequently to check as compared to vanilla
            	
        		//summon Zombie ~ ~ ~ {IsVillager:1}
            	
            	// Check the spaces around the zombie, and speed up or slow down the conversion process based on keyed blocks
                int vanillaRollbackTicks = 0;
            	// First, undo the official vanilla entries
            	if (zombie.worldObj.rand.nextFloat() < (0.01F*checkfactor) ) {
                	
                    int countedBedsOrBars = 0;

                    for (int k = (int)zombie.posX - 4; k < (int)zombie.posX + 4 && countedBedsOrBars < 14; ++k)
                    {
                        for (int l = (int)zombie.posY - 4; l < (int)zombie.posY + 4 && countedBedsOrBars < 14; ++l)
                        {
                            for (int i1 = (int)zombie.posZ - 4; i1 < (int)zombie.posZ + 4 && countedBedsOrBars < 14; ++i1)
                            {
                                Block block = zombie.worldObj.getBlock(k, l, i1);

                                if (block == Blocks.iron_bars || block == Blocks.bed)
                                {
                                    if (zombie.worldObj.rand.nextFloat() < (0.3F/checkfactor) ) {
                                        --vanillaRollbackTicks;
                                    }
                                    
                                    ++countedBedsOrBars;
                                }
                            }
                        }
                    }
                }
            	//if (vanillaRollbackTicks!=0 && GeneralConfigHandler.debugMessages) {LogHelper.info("Counteracting vanilla effects resulting in a " + vanillaRollbackTicks + " tick adjustment");}
            	
            	// Next, apply the values as per the config entries
            	Map<String, ArrayList> zombieCureCatalysts = GeneralConfig.unpackZombieCureCatalysts(GeneralConfig.zombieCureCatalysts);
            	Map<String, ArrayList> zombieCureGroups = GeneralConfig.unpackZombieCureGroups(GeneralConfig.zombieCureGroups);
            	
            	// Finally, update the conversion value. Do this once every ten ticks I suppose.
            	
            	int modTickAdjustment = 0;
            	
            	if (zombie.worldObj.rand.nextFloat() < (0.01F*checkfactor) ) {
            		
                	for ( int groupi=0 ; groupi < zombieCureGroups.get("Groups").size(); groupi++ ) { // Go through all the groups in zombieCureGroups
                		
                		String group = (String) zombieCureGroups.get("Groups").get(groupi);
                		int groupLimit = (Integer) zombieCureGroups.get("Limits").get(groupi);
                		double groupSpeedup = ((Double) zombieCureGroups.get("Speedups").get(groupi))/checkfactor;
                		
                		// Extract sign and apply it later
                		int speedupSign = groupSpeedup<0?-1:1;
                		groupSpeedup = Math.abs(groupSpeedup); 
                		
                        int countedGroupBlocks = 0;

                        for (int k = (int)zombie.posX - 4; k < (int)zombie.posX + 4 && countedGroupBlocks < groupLimit; ++k) {
                            for (int l = (int)zombie.posY - 4; l < (int)zombie.posY + 4 && countedGroupBlocks < groupLimit; ++l) {
                                for (int i1 = (int)zombie.posZ - 4; i1 < (int)zombie.posZ + 4 && countedGroupBlocks < groupLimit; ++i1) {
                                    
                                	Block block = zombie.worldObj.getBlock(k, l, i1);
                        			int blockmeta = zombie.worldObj.getBlockMetadata(k, l, i1);
                                    String blockClassPath = block.getClass().toString().substring(6);
                                    String blockUnlocName = block.getUnlocalizedName();
                                    
                                    for ( int blocki=0 ; blocki < zombieCureCatalysts.get("Groups").size(); blocki++ ) { // Go through all the custom block entries
                                    	
                                    	String catalystGroup = (String) zombieCureCatalysts.get("Groups").get(blocki);
                                    	String catalystClassPath = (String) zombieCureCatalysts.get("ClassPaths").get(blocki);
                                    	String catalystUnlocName = (String) zombieCureCatalysts.get("UnlocNames").get(blocki);
                                    	int catalystMeta = (Integer) zombieCureCatalysts.get("Metas").get(blocki);
                                    	
                                    	if (
                                    			catalystGroup.equals(group)
                                    			&& catalystClassPath.equals(blockClassPath)
                                    			&& (catalystUnlocName.equals("") || catalystUnlocName.equals(blockUnlocName))
                                    			&& (catalystMeta==-1 || blockmeta==catalystMeta)
                                    			) {
                                    		
                                    		//if (GeneralConfigHandler.debugMessages) {
                                        	//	LogHelper.info("Ticked match at " + k + " " + l + " " + i1);
                                        	//	}
                                    		
                                    		for (int i=1; i<groupSpeedup; i++) {
                                        		// Increment time jump
                                        		modTickAdjustment += speedupSign; 
                                        	}
                                        	// Then, deal with the fractional leftover
                                            if (zombie.worldObj.rand.nextFloat() < groupSpeedup % 1) {
                                            	modTickAdjustment += speedupSign; 
                                            }
                                            
                                            ++countedGroupBlocks;
                                            break;
                                    	}
                                    }
                                }
                            }
                        }
                        //if (countedGroupBlocks!=0 &&
                        //		GeneralConfigHandler.debugMessages) {LogHelper.info("Incrementing conversion as per " + countedGroupBlocks + " blocks from " + group + " group.");}
                	}
            	}
            	
            	//if (GeneralConfigHandler.debugMessages && modTickAdjustment != 0) {
            	//	LogHelper.info("Zombie conversion advanced by " + modTickAdjustment + " ticks from custom blocks.");
            	//	}
            	//if (GeneralConfigHandler.debugMessages && (vanillaRollbackTicks != 0 || modTickAdjustment != 0) ) {
            	//	LogHelper.info("Total tick adjustment: " + (vanillaRollbackTicks+modTickAdjustment));
            	//	}
            	//this.accumulatedticks += (vanillaRollbackTicks+modTickAdjustment);
            	//if (GeneralConfigHandler.debugMessages && (vanillaRollbackTicks != 0 || modTickAdjustment != 0) ) {
            	//	LogHelper.info("Cumulative advanced ticks: "+accumulatedticks);
            	//	}
            	
            	
            	int conversionTime=0;
            	
            	try{
            		conversionTime = ReflectionHelper.getPrivateValue(EntityZombie.class, (EntityZombie)event.entity, new String[]{"conversionTime", "field_82234_d"}); // The MCP mapping for this field name
            		// Increment conversion time
            		conversionTime -= (vanillaRollbackTicks+modTickAdjustment);
            		// Cap at 5 minutes
            		conversionTime = MathHelper.clamp_int(conversionTime, 1, 6000);
            		// Set the conversion value to this modified value
            		ReflectionHelper.setPrivateValue(EntityZombie.class, (EntityZombie)event.entity, conversionTime, new String[]{"conversionTime", "field_82234_d"});
            		//if (GeneralConfigHandler.debugMessages) {LogHelper.warn("Setting conversion timer to "+conversionTime);}
    			}
            	catch (Exception e) {
    				//if (GeneralConfigHandler.debugMessages) LogHelper.warn("EntityMonitorHandler > Could not reflect/modify conversionTime field");
    			}
            	
            	Method getConversionTimeBoost_m = ReflectionHelper.findMethod(EntityZombie.class, (EntityZombie)event.entity, new String[]{"getConversionTimeBoost", "func_82233_q"}); // The MCP mapping for this method name
            	
            	int getConversionTimeBoost=0;
            	
            	try {
            		getConversionTimeBoost = (Integer)getConversionTimeBoost_m.invoke((EntityZombie)event.entity);
            	}
            	catch (Exception e) {
            		//if (GeneralConfigHandler.debugMessages) {LogHelper.warn("EntityMonitorHandler > Could not reflect EntityZombie.getConversionTimeBoost");}
            	}
            	
                final int nextConversionTime = conversionTime - getConversionTimeBoost;//zombie.conversionTime - zombie.getConversionTimeBoost();
                
                if (GeneralConfig.debugMessages 
                		&& nextConversionTime <= 500 // Starts counting down 25 seconds before conversion
                		&& nextConversionTime % 20 == 0 // Confirmation message every second
                		) { // Counts down 25 seconds until a zombie villager is cured
                	LogHelper.info("EntityMonitorHandler > Zombie [" + zombie.getEntityId() + "] being cured in " + conversionTime + " ticks");
                }

                // NOTE: if [conversionTime] is zero, the zombie already converted and it's too late to track
                if (nextConversionTime <= 0 && conversionTime > 0) {
                    if (GeneralConfig.debugMessages) {
                        LogHelper.info("EntityMonitorHandler > Zombie " + zombie.toString() + " is about to be cured in tick " + MinecraftServer.getServer().getTickCounter());
                    }
                    ServerInfoTracker.add(zombie);
                }
            }
            
            // Added in v3.1
            if (!zombie.worldObj.isRemote)
            {
            	final ExtendedZombieVillager ezv = ExtendedZombieVillager.get(zombie);
            	if (ezv.getBiomeType()==-1) {ezv.setBiomeType(FunctionsVN.returnBiomeTypeForEntityLocation(zombie));}
            	if (ezv.getSkinTone()==-1) {ezv.setSkinTone(FunctionsVN.returnSkinToneForEntityLocation(zombie));} // Added in v3.2
            	
    			if ((zombie.ticksExisted + zombie.getEntityId())%5 == 0) // Ticks intermittently, modulated so villagers don't deliberately sync.
    			{
    				// Only strip armor if modern skins are on
    		    	if (GeneralConfig.modernZombieSkins && GeneralConfig.removeMobArmor)
    		    	{
    		    		// Turn off gear pickup to prevent goofball rendering
    		    		if (zombie.canPickUpLoot()) {zombie.setCanPickUpLoot(false);}
    		    		
    			    	// Strip armor
    		    		for (int slot=1; slot <=4; slot++) {if (zombie.getEquipmentInSlot(slot) != null) {zombie.setCurrentItemOrArmor(slot, null);}}
    		    	}
    				
    				//if (ezv.getBiomeType() < 0) {ezv.setBiomeType(FunctionsVN.returnBiomeTypeForEntityLocation(zombie));}
    				//(ExtendedZombieVillager.get( zombie )).setProfessionLevel(ExtendedVillager.determineProfessionLevel(zombie));
    				// Sends a ping to everyone within 80 blocks
    				NetworkRegistry.TargetPoint targetPoint = new NetworkRegistry.TargetPoint(zombie.dimension, zombie.lastTickPosX, zombie.lastTickPosY, zombie.lastTickPosZ, 16*5);
    				VillageNames.VNNetworkWrapper.sendToAllAround(
    						new MessageZombieVillagerProfession(zombie.getEntityId(), ezv.getProfession(), ezv.getCareer(), ezv.getBiomeType(), ezv.getProfessionLevel(), ezv.getSkinTone()), // v3.2
    						targetPoint);
    			}
            }
            
        }
        
        
        
        
        // New entity is a village guard. Check to see if it came into being via a player's recruitment.
        else if (
        		Loader.isModLoaded("witchery")
        		&& event.entity instanceof EntityLiving
        		&& event.entity.getClass().toString().substring(6).equals(ModObjects.WitcheryGuardClass)
        		&& (EventType.GUARD).getTracker().size() > 0
        		) {
        	
            final EntityLiving guard = (EntityLiving) event.entity;
            
            if (event.entity.worldObj.isRemote) {
                // Looks for info sent by the server that should be applied to the zombie (e.g. villager profession)
                ClientInfoTracker.SyncGuardMessage(guard);
            }
            else {
                // Looks on the event tracker for a villager that just died
                //final EventTracker tracked = ServerInfoTracker.seek(EventType.VILLAGER, zombie.getPosition(1.0F));//zombie.getPosition());
            	final EventTracker tracked = ServerInfoTracker.seek(EventType.GUARD,
            			//Vec3.createVectorHelper(zombie.posX, zombie.posY, zombie.posZ)
            			new Vec3i(guard.posX, guard.posY + 0.5D, guard.posZ)
            			); // Replaced because of mp server-side crash
            	
            	final ExtendedVillageGuard properties = ExtendedVillageGuard.get(guard);

                if (tracked != null) {
                    if (GeneralConfig.debugMessages) {
                        LogHelper.info("EntityMonitorHandler > Found villager info on the tracker--must copy to guard");
                    }

                    // If found, copy the data from the villager
                    tracked.updateGuard(event, properties);
                }
                /*
                else {
                    if (GeneralConfigHandler.debugMessages) {
                        LogHelper.info("EntityMonitorHandler > No villager info on the tracker--can't assign to guard!");
                    }
                    
                }

                if (GeneralConfigHandler.debugMessages) {
                    LogHelper.info("EntityMonitorHandler > Custom name [" + guard.getCustomNameTag() + "]");
                }
            	*/
            }
        	
        }
        
        
        
        // --- Initialize villager trades and sync skin with client --- //
        
        else if ( event.entity.getClass().toString().substring(6).equals(Reference.VILLAGER_CLASS) // Explicit vanilla villager class
				&& !event.entity.worldObj.isRemote)
        {
        	EntityVillager villager = (EntityVillager)event.entity;
        	ExtendedVillager ev = ExtendedVillager.get(villager);

        	if (GeneralConfig.modernVillagerSkins)
        	{
            	// Initialize buying list in order to provoke the villager to choose a career
            	villager.getRecipes(null);
            	FunctionsVN.monitorVillagerTrades(villager);
        	}
        	
        	int professionLevel = ev.getProfessionLevel();
    		if (professionLevel < 0) {ev.setProfessionLevel(ExtendedVillager.determineProfessionLevel(villager));}
        	
    		if (
    				(villager.ticksExisted + villager.getEntityId())%5 == 0 // Ticks intermittently, modulated so villagers don't deliberately sync.
    				&& ev.getProfession() >= 0 && (ev.getProfession() <=5 || GeneralConfig.professionID_a.indexOf(ev.getProfession())>-1) // This villager ID is specified in the configs
    				)
    		{
    			// Only strip armor if modern villager skins are on
		    	if (GeneralConfig.modernVillagerSkins && GeneralConfig.removeMobArmor)
		    	{
		    		// Turn off gear pickup to prevent goofball rendering
		    		if (villager.canPickUpLoot()) {villager.setCanPickUpLoot(false);}
		    		
			    	// Strip armor
		    		for (int slot=1; slot <=4; slot++) {if (villager.getEquipmentInSlot(slot) != null) {villager.setCurrentItemOrArmor(slot, null);}}
		    	}
				
    			// Initialize the buying list so that the badge displays
    			MerchantRecipeList buyingList = ReflectionHelper.getPrivateValue( EntityVillager.class, villager, new String[]{"buyingList", "field_70963_i"} );
    			if (buyingList == null)
    			{
    				try {
    					Method addDefaultEquipmentAndRecipies_m = ReflectionHelper.findMethod(
    							EntityVillager.class, villager, new String[]{"addDefaultEquipmentAndRecipies", "func_70950_c"},
    							Integer.TYPE
    							);
    					addDefaultEquipmentAndRecipies_m.invoke(villager, 1);
    					}
            		catch (Exception e) {if (GeneralConfig.debugMessages) LogHelper.warn("Could not invoke EntityVillager.addDefaultEquipmentAndRecipies method");}
    			}
    			
    			ev.setProfessionLevel(ExtendedVillager.determineProfessionLevel(villager));
    			// Sends a ping to everyone within 80 blocks
    			NetworkRegistry.TargetPoint targetPoint = new NetworkRegistry.TargetPoint(villager.dimension, villager.lastTickPosX, villager.lastTickPosY, villager.lastTickPosZ, 16*5);
    			VillageNames.VNNetworkWrapper.sendToAllAround(
    					new MessageModernVillagerSkin(villager.getEntityId(), ev.getProfession(), ev.getCareer(), ev.getBiomeType(), professionLevel, ev.getSkinTone()),
    					targetPoint);
    		}
        	
        }
        
        // Monitor the player for purposes of the village reputations achievements
        else if (event.entity instanceof EntityPlayerMP
        		&& !event.entity.worldObj.isRemote
        		&& event.entity.dimension == 0 // Only applies to the Overworld
        		&& event.entity.ticksExisted % (tickRate) == 0) { // Only check every few seconds so as not to bog down the server with Village.dat scans
        	
        	EntityPlayerMP player = (EntityPlayerMP)event.entity;
        	World world = player.worldObj;
        	
        	try {
        		
            	String villageTopTagPlayerIsIn = ReputationHandler.getVillageTagPlayerIsIn(player);
            	
        		Village villageObjPlayerIsIn = world.villageCollectionObj.findNearestVillage((int)player.posX, (int)player.posY, (int)player.posZ, EntityInteractHandler.villageRadiusBuffer);
            	
            	if (
            			!villageTopTagPlayerIsIn.equals("none")
            			|| villageObjPlayerIsIn!=null
            			) { // Player is in a valid Village.dat village.
            		
                	
                	int playerRep = ReputationHandler.getVNReputationForPlayer(player, villageTopTagPlayerIsIn, villageObjPlayerIsIn);
                	
                	// ---- Maximum Rep Achievement ---- //
                	// - Must also be checked onEntity - //
                	if (
                			playerRep <=-30 // Town rep is minimum
                			&& !player.func_147099_x().hasAchievementUnlocked(VillageNames.minrep) // Copied over from EntityPlayerMP
                			) {
                		player.triggerAchievement(VillageNames.minrep);
                		AchievementReward.allFiveAchievements(player);
                	}
                	
                	// --- Maximum Rep Achievement --- //
                	
                	else if (
                			playerRep >=10 // Town rep is maximum
                			&& !player.func_147099_x().hasAchievementUnlocked(VillageNames.maxrep) // Copied over from EntityPlayerMP
                			) {
                		player.triggerAchievement(VillageNames.maxrep);
                		AchievementReward.allFiveAchievements(player);
                	}
                	
                	if (tickRate < 50) tickRate+=2;
                	else if (tickRate > 50) tickRate=50;
                	
            	}
            	else { // Player is not in a valid village.dat village.
            		tickRate = 100; // Slow down the checker when you're not in a village.
            	}
        		
        	}
        	catch (Exception e) {} // Could not verify village status
        	
        }
    }
    
    
    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event) {
        
    	// Adds the Extended Properties to zombies
        if (FunctionsVN.isVanillaZombie(event.entity)
        		&& ExtendedZombieVillager.get((EntityZombie) event.entity) == null) {
            ExtendedZombieVillager.register((EntityZombie) event.entity);
        }
        
        
        // summon Villager ~ ~ ~ {Profession:0}
        
        else if (
        		event.entity instanceof EntityVillager
        		) {
        	
            final EntityVillager villager = (EntityVillager)event.entity;
            
            // Adds the extended properties to villagers
            if (
            		ExtendedVillager.get(villager) == null // Removed careers condition for v3.1 so that villagers always render
            		) {

            	ExtendedVillager.register(villager);
            	
            }
            
        }
        
        else if (
        		Loader.isModLoaded("witchery") &&
        		event.entity.getClass().toString().substring(6).equals(ModObjects.WitcheryGuardClass)
        		) {
        	
            EntityLiving guard = (EntityLiving)event.entity;
            
            // Adds the extended properties to guards
            if (ExtendedVillageGuard.get(guard) == null) {
            	
            	ExtendedVillageGuard.register(guard);
            	
            }
            
        }
        
    }
    
}
