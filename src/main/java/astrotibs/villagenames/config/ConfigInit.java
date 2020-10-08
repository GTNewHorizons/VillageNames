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
import astrotibs.villagenames.config.pieces.StrongholdConfigHandler;
import astrotibs.villagenames.config.pieces.TempleConfigHandler;
import astrotibs.villagenames.config.pieces.VillageConfigHandler;
import astrotibs.villagenames.config.pieces.VillagerConfigHandler;
import astrotibs.villagenames.config.village.VillageGeneratorConfigHandler;
import astrotibs.villagenames.utility.Reference;
import net.minecraftforge.common.MinecraftForge;

public class ConfigInit
{
	public static void init(File configDirectory)
	{
		GeneralConfig.init(new File(configDirectory, "general.cfg"));
		MinecraftForge.EVENT_BUS.register(new GeneralConfig());
		
		VillageConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/village.cfg"));
		MinecraftForge.EVENT_BUS.register(new VillageConfigHandler());
		
		VillagerConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/villager.cfg"));
		MinecraftForge.EVENT_BUS.register(new VillagerConfigHandler());
		
		MineshaftConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/mineshaft.cfg"));
		MinecraftForge.EVENT_BUS.register(new MineshaftConfigHandler());
		
		StrongholdConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/stronghold.cfg"));
		MinecraftForge.EVENT_BUS.register(new StrongholdConfigHandler());
		
		TempleConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/temple.cfg"));
		MinecraftForge.EVENT_BUS.register(new TempleConfigHandler());
		
		FortressConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/fortress.cfg"));
		MinecraftForge.EVENT_BUS.register(new FortressConfigHandler());
		
		MonumentConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/monument.cfg"));
		MinecraftForge.EVENT_BUS.register(new MonumentConfigHandler());
		
		EndCityConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/endcity.cfg"));
		MinecraftForge.EVENT_BUS.register(new EndCityConfigHandler());
		
		MansionConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/mansion.cfg"));
		MinecraftForge.EVENT_BUS.register(new MansionConfigHandler());
		
		
		// Syllable pools designed for niche vanilla purposes
		AngelConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/angel.cfg"));
		MinecraftForge.EVENT_BUS.register(new AngelConfigHandler());
		
		DemonConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/demon.cfg"));
		MinecraftForge.EVENT_BUS.register(new DemonConfigHandler());
		
		DragonConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/dragon.cfg"));
		MinecraftForge.EVENT_BUS.register(new DragonConfigHandler());
		
		GolemConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/golem.cfg"));
		MinecraftForge.EVENT_BUS.register(new GolemConfigHandler());
		
		
		// Syllable pools designed specifically for mod things
		AlienVillageConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/alienvillage.cfg"));
		MinecraftForge.EVENT_BUS.register(new AlienVillageConfigHandler());
		
		AlienConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/alien.cfg"));
		MinecraftForge.EVENT_BUS.register(new AlienConfigHandler());
		
		GoblinConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/goblin.cfg"));
		MinecraftForge.EVENT_BUS.register(new GoblinConfigHandler());
		
		
		// Syllable pools designed to give the player customizability
		CustomConfigHandler.init(new File(configDirectory, Reference.FOLDER_NAMEPIECES+"/custom.cfg"));
		MinecraftForge.EVENT_BUS.register(new CustomConfigHandler());
		
		
		// New village stuff
		VillageGeneratorConfigHandler.init(new File(configDirectory, Reference.FOLDER_NEWVILLAGES+"/village_gen.cfg"));
		MinecraftForge.EVENT_BUS.register(new VillageGeneratorConfigHandler());
		
	}
}
