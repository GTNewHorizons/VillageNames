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
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

/**
 * Adapted from Jabelar's tutorials"
 * http://jabelarminecraft.blogspot.com/p/minecraft-forge-1721710-making-mod.html
 * @author AstroTibs
 */
public class VersionChecker implements Runnable {
	
	private static boolean isLatestVersion = false;
    private static String latestVersion = "";
    
	@Override
	public void run() {
        InputStream in = null;
        try {
            in = new URL(Reference.VERSION_CHECKER_URL).openStream();
        } 
        catch
        // Exception generalized for v 3.0.3
        (Exception e)  {
            e.printStackTrace();
        } 
        
        try {
            latestVersion = IOUtils.readLines(in).get(0);
        }
        // Exception generalized for v 3.0.3
        catch (Exception e)  {
            e.printStackTrace();
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
                
        		) {
        	// V3.0.1: Used to repeat the version check
        	if (
        			event.player.ticksExisted<=50
        			&& event.player.ticksExisted%10==0
        			&& latestVersion.equals("")
        			) {
        		run();
        	}
        	// Ordinary version checker
        	if (
        			!VillageNames.haveWarnedVersionOutOfDate
            		&& GeneralConfig.versionChecker
            		&& !VillageNames.versionChecker.isLatestVersion()
            		&& !latestVersion.equals("")
            		&& !latestVersion.equals(null)
            		&& !(Reference.VERSION).contains("DEV")
            		&& event.player.ticksExisted<=50 // Version 3.0.1: to make sure this goes through
        			) {
        		
                event.player.addChatComponentMessage(
                		new ChatComponentText(
                				EnumChatFormatting.GOLD + Reference.MOD_NAME + 
                				EnumChatFormatting.RESET + " version " + EnumChatFormatting.YELLOW + this.getLatestVersion() + EnumChatFormatting.RESET +
                				" is available! Get it at:"
                		 ));
                event.player.addChatComponentMessage(
                		new ChatComponentText(
                				EnumChatFormatting.GRAY + Reference.URL + EnumChatFormatting.RESET
                		 ));
                // V3.0.1: Moved inside the "if" condition so that it will only stop version checking when it's confirmed
                VillageNames.haveWarnedVersionOutOfDate = true;
        	}
        	
          }
    	
    }
    
}
