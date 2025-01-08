package astrotibs.villagenames.handler;

import net.minecraftforge.event.world.WorldEvent;

import astrotibs.villagenames.tracker.ServerInfoTracker;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Adapted from Villager Tweaks by sidben:
 * https://github.com/sidben/VillagerTweaks/blob/master/src/main/java/sidben/villagertweaks/handler/WorldEventHandler.java
 * 
 * @author AstroTibs
 */
public class ServerTrackerStarter {

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (!event.world.isRemote) {
            ServerInfoTracker.startTracking();
        }
    }

}
