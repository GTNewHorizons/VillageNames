package astrotibs.villagenames.config;

import java.io.File;

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

public class ConfigInit
{
	public static void init(File configDirectory)
	{
		GeneralConfig.init(new File(configDirectory, "general.cfg"));
		VillageConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/village.cfg"));
		VillagerConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/villager.cfg"));
		MineshaftConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/mineshaft.cfg"));
		StrongholdConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/stronghold.cfg"));
		TempleConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/temple.cfg"));
		FortressConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/fortress.cfg"));
		MonumentConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/monument.cfg"));
		EndCityConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/endcity.cfg"));
		MansionConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/mansion.cfg"));
		// Syllable pools designed for niche vanilla purposes
		AngelConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/angel.cfg"));
		DemonConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/demon.cfg"));
		DragonConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/dragon.cfg"));
		GolemConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/golem.cfg"));
		// Syllable pools designed specifically for mod things
		AlienVillageConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/alienvillage.cfg"));
		AlienConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/alien.cfg"));
		GoblinConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/goblin.cfg"));
		PetConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/pet.cfg"));
		// Syllable pools designed to give the player customizability
		CustomConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/custom.cfg"));
		// New village stuff
		VillageGeneratorConfigHandler.init(new File(configDirectory, Reference.FOLDER_NEWVILLAGES+"/village_gen.cfg"));

	}
}
