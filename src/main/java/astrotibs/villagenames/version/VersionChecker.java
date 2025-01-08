package astrotibs.villagenames.version;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.ForgeHooks;

import org.apache.commons.io.IOUtils;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

/**
 * Adapted from Jabelar's tutorials http://jabelarminecraft.blogspot.com/p/minecraft-forge-1721710-making-mod.html
 * Parallel threading provided by Roadhog360
 * 
 * @author AstroTibs
 */
public class VersionChecker extends Thread {

    public static VersionChecker instance = new VersionChecker();

    private static boolean isLatestVersion = false;
    private static boolean warnaboutfailure = false;
    private static String latestVersion = "";
    private static boolean isUpdateCheckFinished = false;
    private static boolean quitChecking = false;
    private static boolean hasThreadStarted = false;

    private static final String CHECK_FOR_VERSIONS_AT_URL = "You can check for new versions at " + Reference.URL;

    @Override
    public void run() {
        InputStream in = null;

        try {
            URL url = new URL(Reference.VERSION_CHECKER_URL);
            in = url.openStream();
        } catch (Exception e) {
            if (!warnaboutfailure) {
                LogHelper.error("Could not connect with server to compare " + Reference.MOD_NAME + " version");
                LogHelper.error(CHECK_FOR_VERSIONS_AT_URL);
                warnaboutfailure = true;
            }
        }

        try {
            latestVersion = IOUtils.readLines(in, Charset.defaultCharset())
                .get(0);
        } catch (Exception e) {
            if (!warnaboutfailure) {
                LogHelper.error("Failed to compare " + Reference.MOD_NAME + " version");
                LogHelper.error(CHECK_FOR_VERSIONS_AT_URL);
                warnaboutfailure = true;
            }
        } finally {
            IOUtils.closeQuietly(in);
        }

        isLatestVersion = Reference.VERSION.equals(latestVersion);

        if (!this.isLatestVersion() && !latestVersion.equals("") && !latestVersion.equals(null)) {
            LogHelper.info(
                "This version of " + Reference.MOD_NAME_COLORIZED
                    + " ("
                    + Reference.VERSION
                    + ") differs from the latest version: "
                    + latestVersion);
        }

        isUpdateCheckFinished = true;
    }

    public boolean isLatestVersion() {
        return isLatestVersion;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    /**
     * PlayerTickEvent is going to be used for version checking.
     * 
     * @param event
     */

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onPlayerTickEvent(PlayerTickEvent event) {
        // Used to repeat the version check
        if ((latestVersion.equals(null) || latestVersion.equals("")) && !warnaboutfailure // Skip the "run" if a failure
                                                                                          // was detected
            && !hasThreadStarted) {
            start();
            hasThreadStarted = true;
        }

        if (event.player.ticksExisted >= 200 && !quitChecking && isUpdateCheckFinished) {
            LogHelper.error(Reference.MOD_NAME + " version check failed.");
            LogHelper.error(CHECK_FOR_VERSIONS_AT_URL);
            quitChecking = true;
        }

        if (event.player.worldObj.isRemote && event.phase == Phase.END // Stops doubling the checks unnecessarily
            && event.player.ticksExisted >= 30
            && isUpdateCheckFinished
            && !quitChecking) {
            // Ordinary version checker
            if (GeneralConfig.versionChecker && !instance.isLatestVersion()
                && !latestVersion.equals(null)
                && !latestVersion.equals("")
                && !(Reference.VERSION).contains("DEV")) {
                quitChecking = true;

                event.player.addChatComponentMessage(
                    new ChatComponentText(
                        Reference.MOD_NAME_COLORIZED + EnumChatFormatting.RESET
                            + " version "
                            + EnumChatFormatting.YELLOW
                            + this.getLatestVersion()
                            + EnumChatFormatting.RESET
                            + " is available! Get it at:"));
                event.player.addChatComponentMessage(ForgeHooks.newChatWithLinks(Reference.URL));
            }
        }

        if (quitChecking) {
            FMLCommonHandler.instance()
                .bus()
                .unregister(instance);
            return;
        }
    }
}
