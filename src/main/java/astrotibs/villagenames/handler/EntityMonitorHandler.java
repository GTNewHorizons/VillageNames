package astrotibs.villagenames.handler;

import astrotibs.villagenames.VillageNames;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.ieep.ExtendedVillageGuard;
import astrotibs.villagenames.ieep.ExtendedVillager;
import astrotibs.villagenames.ieep.ExtendedZombieVillager;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.mixins.early.AccessorEntityVillager;
import astrotibs.villagenames.mixins.early.AccessorEntityZombie;
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
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import static astrotibs.villagenames.VillageNames.configDirectory;
import static astrotibs.villagenames.VillageNames.isWitcheryLoaded;

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
    	if (!(event.entity instanceof  EntityVillager) || !FunctionsVN.isVanillaZombie(event.source.getEntity())) return;

        // A villager was killed by a zombie and may be zombified. Adds to the tracker for future check.
        final EntityVillager villager = (EntityVillager) event.entity;
        ServerInfoTracker.add(villager);

        if (GeneralConfig.debugMessages) {

            Vec3i pos = new Vec3i(villager.posX, villager.posY + 0.5D, villager.posZ);
            String tag = ( villager.getCustomNameTag() == null || villager.getCustomNameTag().isEmpty() ? "(None)" : villager.getCustomNameTag() );
            int entityId = villager.getEntityId();
            int profession = villager.getProfession();

            LogHelper.info(String.format(
                    "EntityMonitorHandler > A zombie just killed villager %s [%d] at [%s], profession [%d]",
                    tag, entityId, pos, profession
            ));
        }
    }
    
	
    @SubscribeEvent
    public void onPlayerStartTracking(PlayerEvent.StartTracking event) {
    	
    	if (event.entity.worldObj.isRemote) return;

        if (event.target instanceof EntityVillager && GeneralConfig.villagerCareers) {
            final EntityVillager villager = (EntityVillager) event.target;
            final ExtendedVillager properties = ExtendedVillager.get(villager);
            NetworkHelper.sendModernVillagerSkinMessage(villager.getEntityId(), properties, event.entityPlayer);
        }

        // Check if the player started tracking a zombie villager (happens on server-side).
        else if (FunctionsVN.isVanillaZombie(event.target)) {
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
        else if (WitcheryHelper.isWitcheryGuard(event.entity)) {
            final EntityLiving guard = (EntityLiving) event.target;

            // Check if the guard has special properties
            final ExtendedVillageGuard properties = ExtendedVillageGuard.get(guard);
            if (properties != null) {
                NetworkHelper.sendVillageGuardMessage(guard.getEntityId(), properties, event.entityPlayer);
            }

        }
    }
    

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {

    	// summon Zombie ~ ~ ~ {IsVillager:1}
    	// New entity is a Zombie. Check to see if it came into being via a killed Villager.
        if (FunctionsVN.isVanillaZombie(event.entity) && ((EntityZombie)event.entity).isVillager()) {
            final EntityZombie zombie = (EntityZombie) event.entity;
            
            ExtendedZombieVillager ezv = ExtendedZombieVillager.get(zombie);
            
            if (event.world.isRemote) {
                // Looks for info sent by the server that should be applied to the zombie (e.g. villager profession)
                ClientInfoTracker.SyncZombieMessage(zombie);
                return;
            }

            // Try to assign a biome number if this villager has none.
            if (ezv.getBiomeType() <0) {
                ezv.setBiomeType(FunctionsVN.returnBiomeTypeForEntityLocation(zombie));
            }

            if (ezv.getSkinTone() == -99) {
                ezv.setSkinTone(FunctionsVN.returnSkinToneForEntityLocation(zombie));
            }

            // Looks on the event tracker for a villager that just died
            final EventTracker tracked = ServerInfoTracker.seek(EventType.VILLAGER,
                    new Vec3i(zombie.posX, zombie.posY + 0.5D, zombie.posZ));

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
                LogHelper.info(String.format("EntityMonitorHandler > Custom name [%s]", zombie.getCustomNameTag()));
                LogHelper.info(String.format("EntityMonitorHandler > Profession [%d]", ezv.getProfession()));
                if (GeneralConfig.villagerCareers){
                    LogHelper.info(String.format("EntityMonitorHandler > Career [%d]", ezv.getCareer()));
                }
            }

        }

        // New entity is a villager. Check to see if it came into being via a cured villager-zombie.
        if (!(event.entity instanceof EntityVillager villager)) {
            return;
        }

		if (GeneralConfig.modernVillagerTrades) {
            FunctionsVN.monitorVillagerTrades(villager);
        }

        ExtendedVillager ev = ExtendedVillager.get(villager);

        if (event.world.isRemote) {
            // Looks for info sent by the server that should be applied to the zombie (e.g. villager profession)
            ClientInfoTracker.syncModernVillagerMessage(villager);
            return;
        }

        // Looks on the event tracker for a zombie that was cured
        final EventTracker tracked = ServerInfoTracker.seek(EventType.ZOMBIE,
                new Vec3i(villager.posX, villager.posY + 0.5D, villager.posZ));

        if (tracked == null) {
            return;
        }

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

    private void treatVanillaZombie(LivingUpdateEvent event){
        final EntityZombie zombie = (EntityZombie) event.entity;

        if (zombie.worldObj.isRemote) return;

        // Based on the [onUpdate] event from zombies
        if (zombie.isConverting()) {
            int zombieX = (int) zombie.posX;
            int zombieY = (int) zombie.posY;
            int zombieZ = (int) zombie.posZ;

            double checkfactor = 10; // This determines how (many times) frequently to check as compared to vanilla

            //summon Zombie ~ ~ ~ {IsVillager:1}

            // Check the spaces around the zombie, and speed up or slow down the conversion process based on keyed blocks
            int vanillaRollbackTicks = 0;
            // First, undo the official vanilla entries
            if (zombie.worldObj.rand.nextFloat() < (0.01F*checkfactor) ) {

                int countedBedsOrBars = 0;

                for (int x = zombieX - 4; x < zombieX + 4 && countedBedsOrBars < 14; ++x) {
                    for (int y = zombieY - 4; y < zombieY + 4 && countedBedsOrBars < 14; ++y) {
                        for (int z = zombieZ - 4; z < zombieZ + 4 && countedBedsOrBars < 14; ++z) {
                            Block block = zombie.worldObj.getBlock(x, y, z);

                            if (block != Blocks.iron_bars && block != Blocks.bed) continue;

                            if (zombie.worldObj.rand.nextFloat() < (0.3F/checkfactor) ) {
                                --vanillaRollbackTicks;
                            }

                            ++countedBedsOrBars;
                        }
                    }
                }
            }

            // Finally, update the conversion value. Do this once every ten ticks I suppose.

            int modTickAdjustment = 0;

            if (zombie.worldObj.rand.nextFloat() < (0.01F*checkfactor) ) {
                // Go through all the groups in GeneralConfig.zombieCureGroups_map
                // todo: refactor this performance nightmare into something a tad more fast
                for ( int groupi=0 ; groupi < GeneralConfig.zombieCureGroups_map.get("Groups").size(); groupi++ ) {
                    String group = (String) GeneralConfig.zombieCureGroups_map.get("Groups").get(groupi);
                    int groupLimit = (Integer) GeneralConfig.zombieCureGroups_map.get("Limits").get(groupi);
                    double groupSpeedup = ((Double) GeneralConfig.zombieCureGroups_map.get("Speedups").get(groupi)) / checkfactor;

                    // Extract sign and apply it later
                    int speedupSign = groupSpeedup<0?-1:1;
                    groupSpeedup = Math.abs(groupSpeedup);

                    int countedGroupBlocks = 0;

                    for (int x = zombieX - 4; x < zombieX + 4 && countedGroupBlocks < groupLimit; ++x) {
                        for (int y = zombieY - 4; y < zombieY + 4 && countedGroupBlocks < groupLimit; ++y) {
                            for (int z = zombieZ - 4; z < zombieZ + 4 && countedGroupBlocks < groupLimit; ++z) {

                                Block block = zombie.worldObj.getBlock(x, y, z);
                                int blockmeta = zombie.worldObj.getBlockMetadata(x, y, z);
                                String blockClassPath = block.getClass().toString().substring(6);
                                String blockUnlocName = block.getUnlocalizedName();

                                // Go through all the custom block entries
                                for (int blocki = 0 ; blocki < GeneralConfig.zombieCureCatalysts_map.get("Groups").size(); blocki++ ) {
                                    String catalystGroup = (String) GeneralConfig.zombieCureCatalysts_map.get("Groups").get(blocki);
                                    String catalystClassPath = (String) GeneralConfig.zombieCureCatalysts_map.get("ClassPaths").get(blocki);
                                    String catalystUnlocName = (String) GeneralConfig.zombieCureCatalysts_map.get("UnlocNames").get(blocki);
                                    int catalystMeta = (Integer) GeneralConfig.zombieCureCatalysts_map.get("Metas").get(blocki);

                                    if (!catalystGroup.equals(group)) continue;
                                    if (!catalystClassPath.equals(blockClassPath)) continue;
                                    if (!catalystUnlocName.isEmpty() && !catalystUnlocName.equals(blockUnlocName)) continue;
                                    if (catalystMeta != 1 && blockmeta!=catalystMeta) continue;

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
            }

            var accessor = (AccessorEntityZombie) zombie;

            int conversionTime = accessor.getConversionTime();
            // Increment conversion time
            conversionTime -= (vanillaRollbackTicks + modTickAdjustment);
            // Cap at 5 minutes
            conversionTime = MathHelper.clamp_int(conversionTime, 1, 6000);
            // Set the conversion value to this modified value
            accessor.setConversionTime(conversionTime);

            int getConversionTimeBoost = accessor.invokeGetConversionTimeBoost();

            final int nextConversionTime = conversionTime - getConversionTimeBoost;

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

        final ExtendedZombieVillager ezv = ExtendedZombieVillager.get(zombie);
        if (ezv.getBiomeType()==-1) {ezv.setBiomeType(FunctionsVN.returnBiomeTypeForEntityLocation(zombie));}
        if (ezv.getSkinTone()==-1) {ezv.setSkinTone(FunctionsVN.returnSkinToneForEntityLocation(zombie));}

        // Ticks intermittently, modulated so villagers don't deliberately sync.
        if ((zombie.ticksExisted + zombie.getEntityId())%5 != 0) return;

        // Only strip armor if modern skins are on
        if (GeneralConfig.modernZombieSkins && GeneralConfig.removeMobArmor)
        {
            // Turn off gear pickup to prevent goofball rendering
            if (zombie.canPickUpLoot()) {zombie.setCanPickUpLoot(false);}

            // Strip armor
            for (int slot=1; slot <=4; slot++) {
                if (zombie.getEquipmentInSlot(slot) != null) {
                    zombie.setCurrentItemOrArmor(slot, null);
                }
            }
        }

        // Sends a ping to everyone within 80 blocks
        NetworkRegistry.TargetPoint targetPoint = new NetworkRegistry.TargetPoint(zombie.dimension, zombie.lastTickPosX,
                zombie.lastTickPosY, zombie.lastTickPosZ, 16*5);

        VillageNames.VNNetworkWrapper.sendToAllAround(
                new MessageZombieVillagerProfession(zombie.getEntityId(), ezv.getProfession(), ezv.getCareer(),
                        ezv.getBiomeType(), ezv.getProfessionLevel(), ezv.getSkinTone()), targetPoint);


    }

    private void treatWitcheryGuard(LivingUpdateEvent event){
        if ((EventType.GUARD).getTracker().isEmpty()) return;

        final EntityLiving guard = (EntityLiving) event.entity;

        if (guard.worldObj.isRemote) {
            // Looks for info sent by the server that should be applied to the zombie (e.g. villager profession)
            ClientInfoTracker.SyncGuardMessage(guard);
            return;
        }

        // Looks on the event tracker for a villager that just died
        final EventTracker tracked = ServerInfoTracker.seek(EventType.GUARD,
                new Vec3i(guard.posX, guard.posY + 0.5D, guard.posZ)
        );

        final ExtendedVillageGuard properties = ExtendedVillageGuard.get(guard);

        if (tracked == null) return;

        if (GeneralConfig.debugMessages) {
            LogHelper.info("EntityMonitorHandler > Found villager info on the tracker--must copy to guard");
        }

        // If found, copy the data from the villager
        tracked.updateGuard(event, properties);
    }

    private void treatVillager(LivingUpdateEvent event){
        if (event.entity.worldObj.isRemote) return;

        EntityVillager villager = (EntityVillager)event.entity;
        ExtendedVillager ev = ExtendedVillager.get(villager);

        if (GeneralConfig.modernVillagerSkins) {
            // Initialize buying list in order to provoke the villager to choose a career
            villager.getRecipes(null);
            FunctionsVN.monitorVillagerTrades(villager);
        }

        int professionLevel = ev.getProfessionLevel();
        if (professionLevel < 0) {ev.setProfessionLevel(ExtendedVillager.determineProfessionLevel(villager));}

        // Update biome and skin tone values
        if (ev.getBiomeType()==-1) {ev.setBiomeType(FunctionsVN.returnBiomeTypeForEntityLocation(villager));}
        if (ev.getSkinTone()==-99) {ev.setSkinTone(FunctionsVN.returnSkinToneForEntityLocation(villager));}

        // Ticks intermittently, modulated so villagers don't deliberately sync.
        if ((villager.ticksExisted + villager.getEntityId())%5 == 0) return;

        if (ev.getProfession() < 0) return;

        // This villager ID is specified in the configs
        if(ev.getProfession() > 5 && !GeneralConfig.professionID_a.contains(ev.getProfession())) return;

        // Only strip armor if modern villager skins are on
        if (GeneralConfig.modernVillagerSkins && GeneralConfig.removeMobArmor) {
            // Turn off gear pickup to prevent goofball rendering
            if (villager.canPickUpLoot()) {villager.setCanPickUpLoot(false);}

            // Strip armor
            for (int slot=1; slot <=4; slot++) {if (villager.getEquipmentInSlot(slot) != null) {villager.setCurrentItemOrArmor(slot, null);}}
        }

        // Initialize the buying list so that the badge displays
        var buyingList = ((AccessorEntityVillager) villager).getBuyingList();
        if (buyingList == null) ((AccessorEntityVillager) villager).invokeAddDefaultEquipmentAndRecipies(1);

        ev.setProfessionLevel(ExtendedVillager.determineProfessionLevel(villager));
        // Sends a ping to everyone within 80 blocks
        NetworkRegistry.TargetPoint targetPoint = new NetworkRegistry.TargetPoint(villager.dimension,
                villager.lastTickPosX, villager.lastTickPosY, villager.lastTickPosZ, 16*5);
        VillageNames.VNNetworkWrapper.sendToAllAround(
                new MessageModernVillagerSkin(villager.getEntityId(), ev.getProfession(), ev.getCareer(),
                        ev.getBiomeType(), professionLevel, ev.getSkinTone()), targetPoint);

    }

    private void treatPlayer(LivingUpdateEvent event){
        // Only applies to the Overworld
        if (event.entity.dimension != 0) return;

        // Only check every few seconds so as not to bog down the server with Village.dat scans
        if (event.entity.ticksExisted % (tickRate) != 0) return;

        EntityPlayerMP player = (EntityPlayerMP) event.entity;
        World world = player.worldObj;

        if (event.entity.worldObj.isRemote) return;

        try {

            String villageTopTagPlayerIsIn = ReputationHandler.getVillageTagPlayerIsIn(player);

            Village villageObjPlayerIsIn = world.villageCollectionObj.findNearestVillage(
                    (int)player.posX, (int)player.posY, (int)player.posZ, EntityInteractHandler.villageRadiusBuffer);

            // Player is not in a valid village.dat village.
            if (villageTopTagPlayerIsIn.equals("none") &&  villageObjPlayerIsIn==null){
                tickRate = 100; // Slow down the checker when you're not in a village.
                return;
            }

            // Player is in a valid Village.dat village
            int playerRep = ReputationHandler.getVNReputationForPlayer(player, villageTopTagPlayerIsIn,
                    villageObjPlayerIsIn);

            // ---- Maximum Rep Achievement ---- //
            // - Must also be checked onEntity - //
            // Town rep is minimum
            if (playerRep <=-30 && !player.func_147099_x().hasAchievementUnlocked(VillageNames.minrep)) {
                // Copied over from EntityPlayerMP
                player.triggerAchievement(VillageNames.minrep);
                AchievementReward.allFiveAchievements(player);
            }

            // --- Maximum Rep Achievement --- //
            // Town rep is maximum
            else if (playerRep >=10 && !player.func_147099_x().hasAchievementUnlocked(VillageNames.maxrep)) {
                // Copied over from EntityPlayerMP
                player.triggerAchievement(VillageNames.maxrep);
                AchievementReward.allFiveAchievements(player);
            }

            if (tickRate < 50) tickRate+=2;
            else if (tickRate > 50) tickRate=50;

        }
        catch (Exception e) {
            LogHelper.error(e); // Could not verify village status
        }

    }

    @SubscribeEvent
    public void onLivingUpdateEvent(LivingUpdateEvent event) {
    	
        // Check if a zombie is about to convert to villager
        if (FunctionsVN.isVanillaZombie(event.entity)) {
            treatVanillaZombie(event);
        }
        
        // New entity is a village guard. Check to see if it came into being via a player's recruitment.
        else if ( WitcheryHelper.isWitcheryGuard(event.entity)) {
        	treatWitcheryGuard(event);
        }

        // --- Initialize villager trades and sync skin with client --- //

        // Explicit vanilla villager class
        else if ( event.entity.getClass() == EntityVillager.class) {
        	treatVillager(event);
        }

        // Monitor the player for purposes of the village reputations achievements
        else if (event.entity instanceof EntityPlayerMP){
        	treatPlayer(event);
        }
    }

    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event) {
        
    	// Adds the Extended Properties to zombies
        if (FunctionsVN.isVanillaZombie(event.entity) && ExtendedZombieVillager.get((EntityZombie) event.entity) == null) {
            ExtendedZombieVillager.register((EntityZombie) event.entity);
        }
        
        // summon Villager ~ ~ ~ {Profession:0}
        
        else if (event.entity instanceof EntityVillager) {
            final EntityVillager villager = (EntityVillager)event.entity;
            
            // Adds the extended properties to villagers
            if (ExtendedVillager.get(villager) == null) {
            	ExtendedVillager.register(villager);
            }
        }
        
        else if (WitcheryHelper.isWitcheryGuard(event.entity)) {
            EntityLiving guard = (EntityLiving)event.entity;
            
            // Adds the extended properties to guards
            if (ExtendedVillageGuard.get(guard) == null) {
            	ExtendedVillageGuard.register(guard);
            }
        }
    }
}
