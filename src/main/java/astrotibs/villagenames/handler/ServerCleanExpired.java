package astrotibs.villagenames.handler;

import astrotibs.villagenames.tracker.ServerInfoTracker;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import net.minecraft.server.MinecraftServer;

/**
 * Adapted from Villager Tweaks by sidben:
 * https://github.com/sidben/VillagerTweaks/blob/master/src/main/java/sidben/villagertweaks/handler/TickEventHandler.java
 * @author AstroTibs
 */
public class ServerCleanExpired
{

    private static final int tickFactor = 300;  // Frequency of update


    @SubscribeEvent
    public void onPostServerTick(TickEvent.ServerTickEvent event)
    {

        if (event.phase == Phase.END) {

            final MinecraftServer server = MinecraftServer.getServer();
            if (server.getTickCounter() % tickFactor == 0) {

                // Cleanup SpecialEventsTracker expired content
                ServerInfoTracker.cleanExpired();
            }
        }
    }

}
