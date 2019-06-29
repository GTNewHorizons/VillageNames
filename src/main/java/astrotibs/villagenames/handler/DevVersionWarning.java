package astrotibs.villagenames.handler;

import astrotibs.villagenames.VillageNames;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class DevVersionWarning {
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onPlayerTickEvent(PlayerTickEvent event) {
		
		if ( 
				event.player.worldObj.isRemote 
				&& !VillageNames.devVersionWarned
				) {
    		event.player.addChatComponentMessage(
            		new ChatComponentText(
            				"You're using a "
            				+ EnumChatFormatting.RED + "development version"  + EnumChatFormatting.RESET + " of " + Reference.MOD_NAME + "."
            		 ));
    	
    		event.player.addChatComponentMessage(
            		new ChatComponentText(
            				EnumChatFormatting.RED + "This version is not meant for public use."
            		 ));
    		VillageNames.devVersionWarned = true;
    	}
		
	}
	
}
