package astrotibs.villagenames.config;

import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigReloader {
	
	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			this.reloadConfigs();
			
		}
	}
		
	public static void reloadConfigs() {
		
		GeneralConfig.loadConfiguration();
		
		EndCityConfigHandler.loadConfiguration();
		FortressConfigHandler.loadConfiguration();
		MansionConfigHandler.loadConfiguration();
		MineshaftConfigHandler.loadConfiguration();
		MonumentConfigHandler.loadConfiguration();
		StrongholdConfigHandler.loadConfiguration();
		TempleConfigHandler.loadConfiguration();
		VillageConfigHandler.loadConfiguration();
		VillagerConfigHandler.loadConfiguration();
		
		AngelConfigHandler.loadConfiguration();
		DemonConfigHandler.loadConfiguration();
		DragonConfigHandler.loadConfiguration();
		GolemConfigHandler.loadConfiguration();
		
		AlienVillageConfigHandler.loadConfiguration();
		AlienConfigHandler.loadConfiguration();
		GoblinConfigHandler.loadConfiguration();
		
		CustomConfigHandler.loadConfiguration();
		
	}
	
}
