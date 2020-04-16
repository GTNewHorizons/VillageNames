package astrotibs.villagenames.handler;

import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import astrotibs.villagenames.VillageNames;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.ForgeHooks;

/**
 * Adapted from Jabelar's tutorials"
 * http://jabelarminecraft.blogspot.com/p/minecraft-forge-1721710-making-mod.html
 * @author AstroTibs
 */
public class VersionChecker implements Runnable {
	
	private static boolean isLatestVersion = false;
	private static boolean warnaboutfailure = false; // Added in v3.1.1
    private static String latestVersion = "";
    
	@Override
	public void run() {
		
        InputStream in = null;
        
        try {
        	URL url = new URL(Reference.VERSION_CHECKER_URL);
        	LogHelper.info("Query: " + url.getQuery());
            in = url.openStream();
        } 
        catch 
        (Exception e)  {LogHelper.error("Could not connect with server to compare " + Reference.MOD_NAME + " version");} // Blanked in v3.1.1
        
        try {
            latestVersion = IOUtils.readLines(in).get(0);
        }
        catch (Exception e) {
        	
        	if (!warnaboutfailure) {
        		// Added in v3.1.1
        		LogHelper.error("Failed to compare " + Reference.MOD_NAME + " version");
        		LogHelper.error("You can check for new versions at "+Reference.URL);
        		warnaboutfailure=true;
        	}
        }
        finally {
            IOUtils.closeQuietly(in);
        }
        
        isLatestVersion = Reference.VERSION.equals(latestVersion);
        
        if ( !this.isLatestVersion() && !latestVersion.equals("") && !latestVersion.equals(null) ) {
        	LogHelper.info("This version of "+Reference.MOD_NAME+" (" + Reference.VERSION + ") differs from the latest version: " + latestVersion);
        }
    }
	
    public boolean isLatestVersion()
    {
    	return isLatestVersion;
    }
    
    public String getLatestVersion()
    {
    	return latestVersion;
    }
	
    /**
     * PlayerTickEvent is going to be used for version checking.
     * @param event
     */
    
    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onPlayerTickEvent(PlayerTickEvent event) {
    	
        if (
        		event.player.worldObj.isRemote
        		&& event.phase == Phase.END // Stops doubling the checks unnecessarily -- v3.2.4
            	&& event.player.ticksExisted==30
        		)
        {
        	// V3.0.1: Used to repeat the version check
        	if (
        			(latestVersion.equals(null) || latestVersion.equals(""))
        			&& !warnaboutfailure // Skip the "run" if a failure was detected
        			)
        	{
        		run();
        	}
        	// Ordinary version checker
        	if (
        			!VillageNames.haveWarnedVersionOutOfDate
            		&& GeneralConfig.versionChecker
            		&& !VillageNames.versionChecker.isLatestVersion()
            		&& !latestVersion.equals(null)
            		&& !latestVersion.equals("")
            		&& !(Reference.VERSION).contains("DEV")
        			)
        	{
        		
                event.player.addChatComponentMessage(
                		new ChatComponentText(
                				EnumChatFormatting.GOLD + Reference.MOD_NAME + 
                				EnumChatFormatting.RESET + " version " + EnumChatFormatting.YELLOW + this.getLatestVersion() + EnumChatFormatting.RESET +
                				" is available! Get it at:"
                		 ));
                event.player.addChatComponentMessage(
                		ForgeHooks.newChatWithLinks(Reference.URL // Made clickable in v3.1.1
                				//EnumChatFormatting.GRAY + Reference.URL + EnumChatFormatting.RESET
                		 ));
                // V3.0.1: Moved inside the "if" condition so that it will only stop version checking when it's confirmed
                VillageNames.haveWarnedVersionOutOfDate = true;
        	}
        	
        }
    	
    }
    
}
