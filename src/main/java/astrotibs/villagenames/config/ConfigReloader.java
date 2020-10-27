package astrotibs.villagenames.config;

import astrotibs.villagenames.config.pieces.AlienConfigHandler;
import astrotibs.villagenames.config.pieces.AlienVillageConfigHandler;
import astrotibs.villagenames.config.pieces.AngelConfigHandler;
import astrotibs.villagenames.config.pieces.CustomConfigHandler;
import astrotibs.villagenames.config.pieces.DemonConfigHandler;
import astrotibs.villagenames.config.pieces.DragonConfigHandler;
import astrotibs.villagenames.config.pieces.EndCityConfigHandler;
import astrotibs.villagenames.config.pieces.FortressConfigHandler;
import astrotibs.villagenames.config.pieces.GoblinConfigHandler;
import astrotibs.villagenames.config.pieces.GolemConfigHandler;
import astrotibs.villagenames.config.pieces.MansionConfigHandler;
import astrotibs.villagenames.config.pieces.MineshaftConfigHandler;
import astrotibs.villagenames.config.pieces.MonumentConfigHandler;
import astrotibs.villagenames.config.pieces.PetConfigHandler;
import astrotibs.villagenames.config.pieces.StrongholdConfigHandler;
import astrotibs.villagenames.config.pieces.TempleConfigHandler;
import astrotibs.villagenames.config.pieces.VillageConfigHandler;
import astrotibs.villagenames.config.pieces.VillagerConfigHandler;
import astrotibs.villagenames.config.village.VillageGeneratorConfigHandler;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigReloader {
	
	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID))
		{
			this.reloadConfigs();
		}
	}
		
	public static void reloadConfigs()
	{
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
		
		PetConfigHandler.loadConfiguration();
		
		CustomConfigHandler.loadConfiguration();
		
		VillageGeneratorConfigHandler.loadConfiguration();
	}
	
}
