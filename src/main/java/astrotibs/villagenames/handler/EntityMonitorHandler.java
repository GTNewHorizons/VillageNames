package astrotibs.villagenames.handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

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
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.village.MerchantRecipe;
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
	protected final int failuresToForceAcceptance = 100; // How many invalid trade cycles are performed until we can assume this is an infinite loop
	
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
            
    		// Try to assign a biome number if this villager has none.
            if (ezv.getBiomeType() <0)
            {
            	ezv.setBiomeType(FunctionsVN.returnBiomeTypeForEntityLocation(zombie));
            }

            // Renovated in v3.1
            if (event.world.isRemote) {
                // Looks for info sent by the server that should be applied to the zombie (e.g. villager profession)
                ClientInfoTracker.SyncZombieMessage(zombie);
            }
            else {
            	
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
        	
            final EntityVillager villager = (EntityVillager) event.entity;
            
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
            }
            
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
            	
            	// Strip gear
            	zombie.setCanPickUpLoot(false);
                for (int slot=0; slot <=4; slot++) {zombie.setCurrentItemOrArmor(slot, null);}
            	
    			if ((zombie.ticksExisted + zombie.getEntityId())%5 == 0) // Ticks intermittently, modulated so villagers don't deliberately sync.
    					{
    				//if (ezv.getBiomeType() < 0) {ezv.setBiomeType(FunctionsVN.returnBiomeTypeForEntityLocation(zombie));}
    				//(ExtendedZombieVillager.get( zombie )).setProfessionLevel(ExtendedVillager.determineProfessionLevel(zombie));
    				// Sends a ping to everyone within 80 blocks
    				NetworkRegistry.TargetPoint targetPoint = new NetworkRegistry.TargetPoint(zombie.dimension, zombie.lastTickPosX, zombie.lastTickPosY, zombie.lastTickPosZ, 16*5);
    				VillageNames.VNNetworkWrapper.sendToAllAround(
    						new MessageZombieVillagerProfession(zombie.getEntityId(), ezv.getProfession(), ezv.getCareer(), ezv.getBiomeType(), ezv.getProfessionLevel()),
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
        
        
        
        
        
        // --- Monitoring villager trades --- //
        
        else if ( event.entity instanceof EntityVillager
        		&& !event.entity.worldObj.isRemote) {
        	
        	EntityVillager villager = (EntityVillager) event.entity;
        	ExtendedVillager ev = ExtendedVillager.get( villager );
            // Try modifying trades
			// summon Villager ~ ~ ~ {Profession:0}
			
        	// Added in v3.1
        	// Update IEEP stuff
        	int professionLevel = ev.getProfessionLevel(); // Added in v3.1
			if (professionLevel < 0)
			{
				ev.setProfessionLevel(ExtendedVillager.determineProfessionLevel(villager));
			}
			
        	
			// If you're talking to a vanilla Villager, check the trades list
			if (
					GeneralConfig.villagerCareers
					&& villager.getProfession() >= 0
					&& villager.getProfession() <= 5
					) {
				
				int profession = villager.getProfession();
				int career = ev.getCareer();
				
				
				// Get the current buying list
				MerchantRecipeList buyingList = ReflectionHelper.getPrivateValue( EntityVillager.class, villager, new String[]{"buyingList", "field_70963_i"} );
								
				
				// --- If the career and careerLevel are null, assign them values --- //
				
				// Random career based on profession
				if ( career<=0 ) {
					career = ExtendedVillager.pickRandomCareer(villager.worldObj.rand, villager.getProfession());
					ev.setCareer(career);
					}
				
				// Compare the villager's CareerLevel against the number of trades it has.
				
				// --- Career-based trade repopulator --- /// 
				
				// Flow plan:
				// 
				// 1. Check the number of trades.
				// 2. If the number of trades is less than (CareerLevel+1), add one trade.
				// 3. Then check all of the trades and remove "illegal" ones.
				// 4. Check the number of trades.
				// 6. Check the number of trades.
				// 7. If the number of trades equals CareerLevel+1, finish the algorithm.
				
				
				// 1. Check the number of trades.
				
				while ( 
						//(buyingList == null ? 0 : buyingList.size()) != (careerLevel+listSizeAdd) 
						//|| 
						//(buyingList == null ? 0 : buyingList.size()) <= 0
						true
						) {
					
					// EntityVillager.addDefaultEquipmentAndRecipies(n) adds n unique trades.
					Method addDefaultEquipmentAndRecipies_m = ReflectionHelper.findMethod(
							EntityVillager.class, villager, new String[]{"addDefaultEquipmentAndRecipies", "func_70950_c"},
							Integer.TYPE
							);
					
					// 2. If the number of trades is less than (CareerLevel+listSizeAdd), generate new trades until they're equal.
					
					int mulliganTrades = 0;
					
					while (
							//(buyingList == null ? 0 : buyingList.size()) < (careerLevel+listSizeAdd)
							//|| 
							(buyingList == null ? 0 : buyingList.size()) <= 0
							) {
						try {addDefaultEquipmentAndRecipies_m.invoke(villager, 1);}
	            		catch (Exception e) {if (GeneralConfig.debugMessages) LogHelper.warn("Could not invoke EntityVillager.addDefaultEquipmentAndRecipies method");}
						
						// Re-collect the trade list
						buyingList = ReflectionHelper.getPrivateValue( EntityVillager.class, villager, new String[]{"buyingList", "field_70963_i"} );
						
						mulliganTrades++;
						
						if (mulliganTrades >= failuresToForceAcceptance) {
							break;
						}
						
					}
					
					// 3. Then check all of the trades and remove "illegal" ones.
					// Either this is unchanged from before the while loop began, or it was just changed in step (2).
					//buyingList = ReflectionHelper.getPrivateValue( EntityVillager.class, villager, new String[]{"buyingList", "field_70963_i"} );
					
					
					// ---------------------------------------- //
					// --- Check for "inappropriate" trades --- //
					// ---------------------------------------- //
					
					int listSizeBeforeCulling = buyingList.size();
					
					mulliganTrades = 0; // To count how many invalid trades were removed
					
					while (true) {
						
						for (int i=buyingList.size()-1; i >= 0; i--) {
							
		                    //MerchantRecipe merchantrecipe = (MerchantRecipe)iterator.next();
							MerchantRecipe merchantrecipe = (MerchantRecipe)buyingList.get(i);
							
							int slot = i+1;
							
							// v3.1 First pass: check to see if metas have been replaced in certain cases
							if (GeneralConfig.modernVillagerTrades)
							{
								// Fisherman
								if (profession==0 && career==2)
								{
									if (( slot==1 ) // has cooked non-cod in slot 1 - set to cod
											&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.fish, 1), new ItemStack(Items.cooked_fished, 1) ))
											&& (merchantrecipe.getItemToSell().getItemDamage() != 0)
													)
									{buyingList.set(i, new MerchantRecipe(new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, slot, 1) ),
											new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(6, 0, slot, 1), 0 ),
											new ItemStack( Items.cooked_fished, 6, 0 ) ));}
									
									else if (( slot==2 ) // has cooked non-salmon in slot 2 - set to cod
											&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.fish, 1), new ItemStack(Items.cooked_fished, 1) ))
											&& (merchantrecipe.getItemToSell().getItemDamage() != 0)
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, slot, 2) ),
											new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(6, 0, slot, 2), 1 ),
											new ItemStack( Items.cooked_fished, 6, 1 ) ));}
									
									else if (( slot==2 ) // has non-cod in slot 2 - set to cod
											&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.fish, 1), new ItemStack(Items.emerald, 1) ))
											&& (merchantrecipe.getItemToBuy().getItemDamage() != 0)
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(15, 1, slot, 2), 0 ),
											new ItemStack( Items.emerald, 1 ) ));}
									
									else if (( slot==3 ) // has non-salmon in slot 3 - set to salmon
											&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.fish, 1), new ItemStack(Items.emerald, 1) ))
											&& (merchantrecipe.getItemToBuy().getItemDamage() != 1)
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(13, 1, slot, 3), 1 ),
											new ItemStack( Items.emerald, 1 ) ));}

									else if (( slot==4 ) // has non-clownfish in slot 4 - set to clownfish
											&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.fish, 1), new ItemStack(Items.emerald, 1) ))
											&& (merchantrecipe.getItemToBuy().getItemDamage() != 2)
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(6, 1, slot, 4), 2 ),
											new ItemStack( Items.emerald, 1 ) ));}

									else if (( slot==5 ) // has non-pufferfish in slot 5 - set to pufferfish
											&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.fish, 1), new ItemStack(Items.emerald, 1) ))
											&& (merchantrecipe.getItemToBuy().getItemDamage() != 3)
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(4, 1, slot, 5), 3 ),
											new ItemStack( Items.emerald, 1 ) ));}
									
									else if (// This is a differentiated pufferfish trade (Puff + emerald = emerald). Replace it with its appropriate trade.
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.fish, 1), new ItemStack(Items.emerald, 1), new ItemStack(Items.emerald, 1) )))
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(4, 1, slot, 5), 3 ),
											new ItemStack( Items.emerald, 1 ) ));}
								}
								
								// Shepherd
								else if (profession==0 && career==3)
								{
									// Dye in slot 3 is of the form (Dye + emerald = emerald).
									// Detect these trades and change them to sell for one emerald.
									if ( merchantrecipe.getItemToBuy() != null
											&& merchantrecipe.getSecondItemToBuy() != null && merchantrecipe.getSecondItemToBuy().getItem() == Items.emerald
											&& merchantrecipe.getItemToSell() != null && merchantrecipe.getItemToSell().getItem() == Items.emerald
											)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( merchantrecipe.getItemToBuy().getItem(), merchantrecipe.getItemToBuy().stackSize, merchantrecipe.getItemToBuy().getItemDamage() ),
											new ItemStack( Items.emerald, 1 ) ));}
									// Dye in slot 4 is of the form (Dye + diamond = diamond).
									// Detect these trades and change them to sell for one emerald.
									else if ( merchantrecipe.getItemToBuy() != null
											&& merchantrecipe.getSecondItemToBuy() != null && merchantrecipe.getSecondItemToBuy().getItem() == Items.diamond
											&& merchantrecipe.getItemToSell() != null && merchantrecipe.getItemToSell().getItem() == Items.diamond
											)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( merchantrecipe.getItemToBuy().getItem(), merchantrecipe.getItemToBuy().stackSize, merchantrecipe.getItemToBuy().getItemDamage() ),
											new ItemStack( Items.emerald, 1 ) ));}
								}
								
								// Fletcher
								else if (profession==0 && career==4)
								{
									// In order to distinguish bow from enchanted bow, the enchanted bow sells for two distinct emerald stacks
									if ( merchantrecipe.getItemToBuy() != null && merchantrecipe.getItemToBuy().getItem() == Items.emerald
											&& merchantrecipe.getSecondItemToBuy() != null && merchantrecipe.getSecondItemToBuy().getItem() == Items.emerald
											)
									{int enchantvalue = 5 + villager.worldObj.rand.nextInt(15);
									buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(2+villager.worldObj.rand.nextInt(2)), 1, slot, 4) ),
											EnchantmentHelper.addRandomEnchantment(villager.worldObj.rand, new ItemStack(Items.bow, 1, 0), enchantvalue ) ) );}
								}
								
								// Librarian
								else if (profession==1 && career==1)
								{
									if ( merchantrecipe.getItemToBuy() != null && merchantrecipe.getItemToBuy().getItem() == Items.emerald
											&& merchantrecipe.getSecondItemToBuy() != null && merchantrecipe.getSecondItemToBuy().getItem() == Items.book
											&& merchantrecipe.getItemToSell() != null && merchantrecipe.getItemToSell().getItem() == Items.emerald
											)
									{
										// This is a placeholder for an enchanted book, to allow multiples from the same librarian.
										{
											Enchantment enchantment = Enchantment.enchantmentsBookList[villager.worldObj.rand.nextInt(Enchantment.enchantmentsBookList.length)];
											int enchLevel = MathHelper.getRandomIntegerInRange(villager.worldObj.rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
											buyingList.set(i,
													FunctionsVN.modernEnchantedBookTrade(villager.worldObj.rand)
													/*new MerchantRecipe(
									        		new ItemStack(Items.emerald, FunctionsVN.modernTradeCostBySlot((villager.worldObj.rand.nextInt(5 + enchLevel * 10) + 3 * enchLevel), 1, slot, 3) ),
									        		new ItemStack(Items.book),
									        		Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, enchLevel))
									        		)*/
													);
										}
									}
									
								}
								
								
								// Leatherworker
								else if (profession==4 && career==2)
								{
									// Detect these trades and change them to sell for one emerald stack instead of two.
									if ( merchantrecipe.getItemToBuy() != null && merchantrecipe.getItemToBuy().getItem() == Items.emerald
											&& merchantrecipe.getSecondItemToBuy() != null && merchantrecipe.getSecondItemToBuy().getItem() == Items.emerald
											&& merchantrecipe.getItemToSell() != null
											)
									{buyingList.set(i, new MerchantRecipe(
											merchantrecipe.getItemToBuy(),
											merchantrecipe.getItemToSell() ));}
								}
								
								// This block is for enchanting equipment items that should be but aren't.
								if (
										merchantrecipe.getItemToBuy() != null && merchantrecipe.getItemToBuy().getItem() == Items.emerald
										//&& merchantrecipe.getSecondItemToBuy() == null
										&& merchantrecipe.getItemToSell() != null
										&&
										(
												 // Armorer
												   (profession==3 && career==1 && merchantrecipe.getItemToSell().getItem() == Items.diamond_leggings)
												|| (profession==3 && career==1 && merchantrecipe.getItemToSell().getItem() == Items.diamond_boots)
												|| (profession==3 && career==1 && merchantrecipe.getItemToSell().getItem() == Items.diamond_helmet)
												|| (profession==3 && career==1 && merchantrecipe.getItemToSell().getItem() == Items.diamond_chestplate)
												 // Weaponsmith
												|| (profession==3 && career==2 && merchantrecipe.getItemToSell().getItem() == Items.iron_sword)
												|| (profession==3 && career==2 && merchantrecipe.getItemToSell().getItem() == Items.diamond_axe)
												|| (profession==3 && career==2 && merchantrecipe.getItemToSell().getItem() == Items.diamond_sword)
												 // Toolsmith
												|| (profession==3 && career==3 && merchantrecipe.getItemToSell().getItem() == Items.iron_axe)
												|| (profession==3 && career==3 && merchantrecipe.getItemToSell().getItem() == Items.iron_shovel)
												|| (profession==3 && career==3 && merchantrecipe.getItemToSell().getItem() == Items.iron_pickaxe)
												|| (profession==3 && career==3 && merchantrecipe.getItemToSell().getItem() == Items.diamond_hoe)
												|| (profession==3 && career==3 && merchantrecipe.getItemToSell().getItem() == Items.diamond_axe)
												|| (profession==3 && career==3 && merchantrecipe.getItemToSell().getItem() == Items.diamond_shovel)
												|| (profession==3 && career==3 && merchantrecipe.getItemToSell().getItem() == Items.diamond_pickaxe)
												// Leatherworker
												|| (profession==4 && career==2 && merchantrecipe.getItemToSell().getItem() == Items.leather_chestplate && slot !=1)
												|| (profession==4 && career==2 && merchantrecipe.getItemToSell().getItem() == Items.leather_helmet && slot !=2)
												)
										&& !merchantrecipe.getItemToSell().isItemEnchanted()
										)
								{
									ItemStack itemToEnchant = merchantrecipe.getItemToSell();
									int enchantvalue = 5 + villager.worldObj.rand.nextInt(15);
									itemToEnchant = EnchantmentHelper.addRandomEnchantment(villager.worldObj.rand, itemToEnchant, enchantvalue );
									// Re-apply it to the list.
									buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(6+villager.worldObj.rand.nextInt(3)), 1, slot, 3) ),
											itemToEnchant ) );
								}
								
								
								// Change some prices to make them more fair
								
								// Farmer
								if (profession==0 && career==1)
								{
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.cookie, 1) ))
											&& (merchantrecipe.getItemToSell().stackSize < 18)
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 1, slot, 3) ),
											new ItemStack(Items.cookie, 18 ) ));}
									
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.bread, 1) ))
											&& (merchantrecipe.getItemToSell().stackSize < 6)
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 1, slot, 2) ),
											new ItemStack(Items.bread, 6) ));}
								}
								
								// Librarian
								if (profession==1 && career==1)
								{
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.book, 1), new ItemStack(Items.emerald, 1) ))
											&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(4, 1, slot, 2))
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.book, FunctionsVN.modernTradeCostBySlot(4, 1, slot, 2) ),
											new ItemStack(Items.emerald, 1) ));}
									
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Item.getItemFromBlock(Blocks.bookshelf), 1, 0) ))
											&& (merchantrecipe.getItemToSell().stackSize != 3)
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(6, 1, slot, 1) ),
											new ItemStack( Item.getItemFromBlock(Blocks.bookshelf), 3 ) ));}
									
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.compass, 1) ))
											&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(4, 1, slot, 4))
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(4, 1, slot, 4) ),
											new ItemStack(Items.compass, 1 ) ));}
									
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.clock, 1) ))
											&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(5, 1, slot, 4))
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(5, 1, slot, 4) ),
											new ItemStack(Items.clock, 1 ) ));}
								}
								
								// Blacksmith
								if (profession==3 && (career>=1 || career<=3))
								{
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.coal, 1), new ItemStack(Items.emerald, 1) ))
											&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(15, 1, slot, 1))
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.coal, FunctionsVN.modernTradeCostBySlot(15, 1, slot, 1) ),
											new ItemStack(Items.emerald, 1) ));}
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.iron_ingot, 1), new ItemStack(Items.emerald, 1) ))
											&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(4, 1, slot, 2))
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.iron_ingot, FunctionsVN.modernTradeCostBySlot(4, 1, slot, 2) ),
											new ItemStack(Items.emerald, 1) ));}

									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.diamond, 1), new ItemStack(Items.emerald, 1) ))
											&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(1, 1, slot, 4))
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.diamond, FunctionsVN.modernTradeCostBySlot(1, 1, slot, 4) ),
											new ItemStack(Items.emerald, 1) ));}
									
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.chainmail_helmet, 1) ))
											&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(1, 1, slot, 2))
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, slot, 2) ),
											new ItemStack(Items.chainmail_helmet, 1 ) ));}
									
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.chainmail_chestplate, 1) ))
											&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(1, 1, slot, 3))
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, slot, 3) ),
											new ItemStack(Items.chainmail_chestplate, 1 ) ));}
									
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.chainmail_leggings, 1) ))
											&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(3, 1, slot, 2))
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 1, slot, 2) ),
											new ItemStack(Items.chainmail_leggings, 1 ) ));}
									
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.chainmail_boots, 1) ))
											&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(1, 1, slot, 2))
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, slot, 2) ),
											new ItemStack(Items.chainmail_boots, 1 ) ));}
								}
								
								// Butcher
								if (profession==4 && career==1)
								{
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.chicken, 1), new ItemStack(Items.emerald, 1) ))
											&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(14, 1, slot, 1))
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.chicken, FunctionsVN.modernTradeCostBySlot(14, 1, slot, 1) ),
											new ItemStack(Items.emerald, 1) ));}
									
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.porkchop, 1), new ItemStack(Items.emerald, 1) ))
											&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(7, 1, slot, 1))
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.porkchop, FunctionsVN.modernTradeCostBySlot(7, 1, slot, 1) ),
											new ItemStack(Items.emerald, 1) ));}
									
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.beef, 1), new ItemStack(Items.emerald, 1) ))
											&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(10, 1, slot, 3))
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.beef, FunctionsVN.modernTradeCostBySlot(10, 1, slot, 3) ),
											new ItemStack(Items.emerald, 1) ));}
									
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.cooked_porkchop, 1) ))
											&& (merchantrecipe.getItemToSell().stackSize != 5)
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, slot, 2) ),
											new ItemStack(Items.cooked_porkchop, 5 ) ));}
									
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.cooked_chicken, 1) ))
											&& (merchantrecipe.getItemToSell().stackSize != 8)
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, slot, 2) ),
											new ItemStack(Items.cooked_chicken, 8 ) ));}
									
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.cooked_chicken, 1) ))
											&& (merchantrecipe.getItemToSell().stackSize != 8)
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, slot, 3) ),
											new ItemStack(Items.cooked_beef, 3 ) ));}
								}
								
								// Leatherworker
								if (profession==4 && career==2)
								{
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.leather_leggings, 1) ))
											&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(3, 2, slot, 1))
													)
									{
										ItemStack itemStackColorizable = new ItemStack(Items.leather_leggings);
										itemStackColorizable = FunctionsVN.colorizeItemstack(itemStackColorizable, FunctionsVN.combineDyeColors(new int[]{villager.worldObj.rand.nextInt(16), villager.worldObj.rand.nextInt(16)}));
										buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 2, slot, 1) ),
											itemStackColorizable ));}
									
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.leather_boots, 1) ))
											&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(4, 2, slot, 2))
													)
									{buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(4, 2, slot, 2) ),
											new ItemStack(Items.leather_boots, 1 ) ));}
									
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.leather_chestplate, 1) ))
											&& !merchantrecipe.getItemToSell().isItemEnchanted()
											&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(7, 2, slot, 1))
													)
									{
										ItemStack itemStackColorizable = new ItemStack(Items.leather_chestplate);
										itemStackColorizable = FunctionsVN.colorizeItemstack(itemStackColorizable, FunctionsVN.combineDyeColors(new int[]{villager.worldObj.rand.nextInt(16), villager.worldObj.rand.nextInt(16)}));
										buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(7, 2, slot, 1) ),
											itemStackColorizable ));}
									
									if (
											merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.leather_helmet, 1) ))
											&& !merchantrecipe.getItemToSell().isItemEnchanted()
											&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(5, 2, slot, 2))
													)
									{
										ItemStack itemStackColorizable = new ItemStack(Items.leather_helmet);
										itemStackColorizable = FunctionsVN.colorizeItemstack(itemStackColorizable, FunctionsVN.combineDyeColors(new int[]{villager.worldObj.rand.nextInt(16), villager.worldObj.rand.nextInt(16)}));
										buyingList.set(i, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(5, 2, slot, 2) ),
											itemStackColorizable ));}
								}
								
								
								
								
								/*
								if (
										(profession==1 && (career==1 || career==2))
										&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.paper, 1), new ItemStack(Items.emerald, 1) ))
										&& (merchantrecipe.getItemToBuy().stackSize < 22 || merchantrecipe.getItemToBuy().stackSize > 26)
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.paper, FunctionsVN.modernTradeCostBySlot(24, 1, slot) ),
										new ItemStack(Items.emerald, 1) ));}
								if (
										(profession==1 && career==1)
										&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.book, 1), new ItemStack(Items.emerald, 1) ))
										&& (merchantrecipe.getItemToBuy().stackSize < 4-2 || merchantrecipe.getItemToBuy().stackSize > 4+2)
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.book, FunctionsVN.modernTradeCostBySlot(4, 1, slot) ),
										new ItemStack(Items.emerald, 1) ));}
								if (
										(profession==2 && career==1)
										&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.experience_bottle, 1) ))
										&& (merchantrecipe.getItemToSell().stackSize != 1)
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack(Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 1, slot)),
										new ItemStack( Items.experience_bottle, 1 )
										));}
								if (
										(profession==2 && career==1)
										&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.redstone, 1) ))
										&& (merchantrecipe.getItemToSell().stackSize != 2)
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack(Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, slot)),
										new ItemStack( Items.redstone, 2 )
										));}
								/*
								if (
										(profession==4 && career==1)
										&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.coal, 1), new ItemStack(Items.emerald, 1) ))
										&& (merchantrecipe.getItemToBuy().stackSize < 13 || merchantrecipe.getItemToBuy().stackSize > 17)
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.coal, FunctionsVN.modernTradeCostBySlot(15, 1, slot) ),
										new ItemStack(Items.emerald, 1) ));}*/
								
								
							}
							
							// This is where I'll put the "illegal trade removal" method.
							// Right now this is a placeholder specifically to remove the paper/emerald Librarian trade
		                    if (
		                    		// Since we're equating careerLevel and trade list position, insert that instead of careerLevel for evaluation.
		                    		FunctionsVN.isTradeInappropriate(merchantrecipe, profession, career, i+1)//-listSizeAdd)
		                    		// Added in v3.1: to prevent crash-trades
		                    		|| merchantrecipe.getItemToBuy()==null || merchantrecipe.getItemToBuy().getItem()==Item.getItemFromBlock(Blocks.air)
									|| merchantrecipe.getItemToSell()==null || merchantrecipe.getItemToSell().getItem()==Item.getItemFromBlock(Blocks.air)
		                    		) {
		                    	// summon Villager ~ ~ ~ {Profession:1}
		        				
		                    	// Remove the offending trade
		                    	buyingList.remove(i);
		                    	
		                    }
		                }
						
                    	// If the buying list is the same size it was before culling, then it had no inappropriate trades removed. We can stop.
                    	if (buyingList.size() == listSizeBeforeCulling) {
                    		break;
                    	}
                    	else {
                    		// Apply the culled list to the villager
                        	ReflectionHelper.setPrivateValue(EntityVillager.class, villager, buyingList, new String[]{"buyingList", "field_70963_i"});
                    	}
                    	
                		
                    	// Accept that this is probably an infinite loop.
                    	if (mulliganTrades >= failuresToForceAcceptance) {
                    		
                    		int nextSlotToFill = buyingList.size()+1;
                    		
                    		if (GeneralConfig.debugMessages) LogHelper.info("Infinite loop suspected while generating villager trades. Stopping with "
                    				+ buyingList.size() + " trades"
                    						);
                    		
                    		// If the Villager is a Librarian who already has an enchanted book, they can't get another one,
    						// and that's probably why the search failed. So give them one manually.
    						if (
    								!(GeneralConfig.modernVillagerTrades) // Added in v3.1
    								&& profession== 1 // Profession: Librarian
    								&& career == 1 // Career: Librarian
    								&& buyingList!=null
    								&& (nextSlotToFill==1
    								|| nextSlotToFill==4
    								|| nextSlotToFill==5)
    								)
    						{
    							Enchantment enchantment = Enchantment.enchantmentsBookList[villager.worldObj.rand.nextInt(Enchantment.enchantmentsBookList.length)];
    					        int i1 = MathHelper.getRandomIntegerInRange(villager.worldObj.rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
    					        buyingList.add(new MerchantRecipe(
    					        		new ItemStack(Items.book),
    					        		new ItemStack(Items.emerald, Math.min((villager.worldObj.rand.nextInt(5 + i1 * 10) + 3 * i1), 64) ),
    					        		Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, i1))
    					        		));
    					        ReflectionHelper.setPrivateValue(EntityVillager.class, villager, buyingList, new String[]{"buyingList", "field_70963_i"});
    						}
    						// Modern trades cleanup
    						else if (
    								GeneralConfig.modernVillagerTrades

    								)
    						{
    							if (profession == 0 && career == 2) // Career: Fisherman
    							{
    								if (nextSlotToFill==4) // Stuck at 3 trades
    								{
    									// Add the clownfish trade
    	    							buyingList.add(new MerchantRecipe(
    											new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(6, 0, nextSlotToFill, 4), 2 ),
    											new ItemStack( Items.emerald, 1 ) ));
    								}
    							}
    							
    						}
                    		break;
                    	}
                    	
						// If the modified list is smaller than the list going in, then add new trades.
						if (buyingList.size() < listSizeBeforeCulling) {
							
							// Generate a replacement trade
	                    	try {addDefaultEquipmentAndRecipies_m.invoke(villager, 1);}
		            		catch (Exception e) {if (GeneralConfig.debugMessages) LogHelper.warn("Could not invoke EntityVillager.addDefaultEquipmentAndRecipies method");}
	                    	
	                    	mulliganTrades++;
	                    	
	                    	// Reload trade list
	                    	buyingList = ReflectionHelper.getPrivateValue( EntityVillager.class, villager, new String[]{"buyingList", "field_70963_i"} );
						}
						
					}
					
					// Should be good to escape.
					if (buyingList.size() == listSizeBeforeCulling) {
                		
						// But first, do some last-minute checks
						
						// If the dust has settled and the poor bastard has absolutely no trades, give him the ol' gold ingot.
						if ( (buyingList == null ? 0 : buyingList.size()) <= 0 ) {
							//LogHelper.info("Adding gold ingots");
							buyingList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.gold_ingot, 8 + villager.worldObj.rand.nextInt(3) ), new ItemStack(Items.emerald, 1 )) );
							ReflectionHelper.setPrivateValue(EntityVillager.class, villager, buyingList, new String[]{"buyingList", "field_70963_i"});
						}
						// Special handler: if this is a shepherd and the final slot is _selling wool_, then add all the colored varieties.
						else if (
								!(GeneralConfig.modernVillagerTrades) // Added in v3.1
								&& profession== 0 // Profession: Farmer
								&& career == 3 // Career: Shepherd
								&& buyingList!=null
								&& buyingList.size()<=16
								&& ((MerchantRecipe)buyingList.get(buyingList.size()-1)).hasSameIDsAs(
										new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack( Item.getItemFromBlock(Blocks.wool), 1))
										)
								) {
							buyingList.remove(buyingList.size()-1);
							
							final int[] woolRandOrder = shuffledIntArray(0, 15, villager.worldObj.rand);
							
							for (int i=0; i<16; i++) {
								buyingList.add(
									new MerchantRecipe(new ItemStack(Items.emerald, 1 + villager.worldObj.rand.nextInt(2) ), new ItemStack( Item.getItemFromBlock(Blocks.wool), 1, woolRandOrder[i]))
									);
								}
							ReflectionHelper.setPrivateValue(EntityVillager.class, villager, buyingList, new String[]{"buyingList", "field_70963_i"});
						}
						// Go through list of Blacksmith items, and enchant stuff that is not enchanted.
						else if (!(GeneralConfig.modernVillagerTrades) // Added in v3.1
								&& profession == 3) { // Profession: Blacksmith
							
							for (int i=buyingList.size()-1; i >= 0; i--) {
								
								MerchantRecipe merchantrecipe = (MerchantRecipe)buyingList.get(i);
								ItemStack itemToCheck = merchantrecipe.getItemToSell();
								if (!itemToCheck.isItemEnchanted()
										&& 
										(
											   (career==1 && itemToCheck.isItemEqual(new ItemStack(Items.diamond_chestplate)))
											|| (career==2 && itemToCheck.isItemEqual(new ItemStack(Items.iron_sword)) )
											|| (career==2 && itemToCheck.isItemEqual(new ItemStack(Items.diamond_sword)) )
											|| (career==2 && itemToCheck.isItemEqual(new ItemStack(Items.diamond_axe)) )
											|| (career==3 && itemToCheck.isItemEqual(new ItemStack(Items.iron_shovel)) )
											|| (career==3 && itemToCheck.isItemEqual(new ItemStack(Items.iron_pickaxe)) )
											|| (career==3 && itemToCheck.isItemEqual(new ItemStack(Items.diamond_pickaxe)) )
												)
										) {
									ItemStack emeraldPrice = merchantrecipe.getItemToBuy();
									// Enchant the item.
									itemToCheck = EnchantmentHelper.addRandomEnchantment(villager.worldObj.rand, itemToCheck, 5 + villager.worldObj.rand.nextInt(15) );
									// Re-apply it to the list.
									buyingList.set(i, new MerchantRecipe( emeraldPrice, itemToCheck ) );
								}
							}
						}
						// If it's a Leather Tunic, it might be enchanted.
						else if (!(GeneralConfig.modernVillagerTrades) // Added in v3.1
								&& profession == 4 && career == 2) { // Profession: Blacksmith
							
							// Iterate through all four types
							ArrayList<ItemStack> alis = new ArrayList();
							alis.add(new ItemStack(Items.leather_chestplate));
							alis.add(new ItemStack(Items.leather_boots));
							alis.add(new ItemStack(Items.leather_helmet));
							alis.add(new ItemStack(Items.leather_leggings));
							
							for (int j = 0; j <4; j++) {
								for (int i=buyingList.size()-1; i >= 0; i--) {
									
									MerchantRecipe merchantrecipe = (MerchantRecipe)buyingList.get(i);
									ItemStack itemToCheck = merchantrecipe.getItemToSell();
									if (!itemToCheck.isItemEnchanted()
											&& itemToCheck.isItemEqual( alis.get(j) )
											) {
										// Enchant the item.
										itemToCheck = EnchantmentHelper.addRandomEnchantment(villager.worldObj.rand, itemToCheck, 5 + villager.worldObj.rand.nextInt(15) );
										// Change the emerald cost
										ItemStack emeraldPrice = new ItemStack(Items.emerald, new int[]{7,4,5,5}[j] + villager.worldObj.rand.nextInt(new int[]{6,5,5,6}[j]));
										// Re-apply it to the list.
										buyingList.set(i, new MerchantRecipe( emeraldPrice, itemToCheck ) );
									}
								}
							}
						}
						
						if (ev.getBiomeType()==-1) {ev.setBiomeType(FunctionsVN.returnBiomeTypeForEntityLocation(villager));}
						
						// Added in v3.1
						if ((villager.ticksExisted + villager.getEntityId())%5 == 0) // Ticks intermittently, modulated so villagers don't deliberately sync.
								{
							ev.setProfessionLevel(ExtendedVillager.determineProfessionLevel(villager));
							// Sends a ping to everyone within 80 blocks
							NetworkRegistry.TargetPoint targetPoint = new NetworkRegistry.TargetPoint(villager.dimension, villager.lastTickPosX, villager.lastTickPosY, villager.lastTickPosZ, 16*5);
							VillageNames.VNNetworkWrapper.sendToAllAround(
									new MessageModernVillagerSkin(villager.getEntityId(), profession, career, ev.getBiomeType(), professionLevel),
									targetPoint);
								}
						
						break;
                	}
					
					// 4. Check the number of trades.
					// Should not be necessary because either it's unchanged from (3) or it was changed but then applied and re-read above.
					
					// 6. Check the number of trades.
					// Same situation as (4).
					
					// 7. If the number of trades equals CareerLevel+listSizeAdd, finish the algorithm.
				}
				
				
				//villager.func_146091_a(buyingList, Items.blaze_powder, new Random(), 1.0F);
				//buyingList = ReflectionHelper.getPrivateValue( EntityVillager.class, villager, new String[]{"buyingList", "field_70963_i"} );
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
    
    /**
     * Produces a shuffled array of integers from value a to value b.
     * Primarily used to randomize the colored wool variations granted to shepherds.
     */
    public static int[] shuffledIntArray(int a, int b, Random rgen){
		int size = b-a+1;
		int[] array = new int[size];
 
		for(int i=0; i< size; i++){
			array[i] = a+i;
		}
 
		for (int i=0; i<array.length; i++) {
		    int randomPosition = rgen.nextInt(array.length);
		    int temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
		}
		
		return array;
	}
    
}
