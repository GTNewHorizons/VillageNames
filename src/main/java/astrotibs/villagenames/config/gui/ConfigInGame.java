package astrotibs.villagenames.config.gui;

import astrotibs.villagenames.config.GeneralConfig;
import cpw.mods.fml.client.GuiIngameModOptions;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.GuiOpenEvent;

public class ConfigInGame {
	/**
	 * This is only used in 1.7, because in-game config menus
	 * were properly implemented in 1.8 and on.
	 */
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(GuiOpenEvent event)
	{
	    if (event.gui instanceof GuiIngameModOptions
	    		&& GeneralConfig.allowInGameConfig)
	    {
	        event.gui = new VNGuiConfig(null);        
	    }
	}
	
}
