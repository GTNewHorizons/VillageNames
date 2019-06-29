package astrotibs.villagenames.config;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;

public class ConfigInit {
	public static void init(File configDirectory) {
		
		GeneralConfig.init(new File(configDirectory, "general.cfg"));
		MinecraftForge.EVENT_BUS.register(new GeneralConfig());
		
		VillageConfigHandler.init(new File(configDirectory, "namepieces_village.cfg"));
		MinecraftForge.EVENT_BUS.register(new VillageConfigHandler());
		
		VillagerConfigHandler.init(new File(configDirectory, "namepieces_villager.cfg"));
		MinecraftForge.EVENT_BUS.register(new VillagerConfigHandler());
		
		MineshaftConfigHandler.init(new File(configDirectory, "namepieces_mineshaft.cfg"));
		MinecraftForge.EVENT_BUS.register(new MineshaftConfigHandler());
		
		StrongholdConfigHandler.init(new File(configDirectory, "namepieces_stronghold.cfg"));
		MinecraftForge.EVENT_BUS.register(new StrongholdConfigHandler());
		
		TempleConfigHandler.init(new File(configDirectory, "namepieces_temple.cfg"));
		MinecraftForge.EVENT_BUS.register(new TempleConfigHandler());
		
		FortressConfigHandler.init(new File(configDirectory, "namepieces_fortress.cfg"));
		MinecraftForge.EVENT_BUS.register(new FortressConfigHandler());
		
		MonumentConfigHandler.init(new File(configDirectory, "namepieces_monument.cfg"));
		MinecraftForge.EVENT_BUS.register(new MonumentConfigHandler());
		
		EndCityConfigHandler.init(new File(configDirectory, "namepieces_endcity.cfg"));
		MinecraftForge.EVENT_BUS.register(new EndCityConfigHandler());
		
		MansionConfigHandler.init(new File(configDirectory, "namepieces_mansion.cfg"));
		MinecraftForge.EVENT_BUS.register(new MansionConfigHandler());
		
		
		// Syllable pools designed for niche vanilla purposes
		AngelConfigHandler.init(new File(configDirectory, "namepieces_angel.cfg"));
		MinecraftForge.EVENT_BUS.register(new AngelConfigHandler());
		
		DemonConfigHandler.init(new File(configDirectory, "namepieces_demon.cfg"));
		MinecraftForge.EVENT_BUS.register(new DemonConfigHandler());
		
		DragonConfigHandler.init(new File(configDirectory, "namepieces_dragon.cfg"));
		MinecraftForge.EVENT_BUS.register(new DragonConfigHandler());
		
		GolemConfigHandler.init(new File(configDirectory, "namepieces_golem.cfg"));
		MinecraftForge.EVENT_BUS.register(new GolemConfigHandler());
		
		
		// Syllable pools designed specifically for mod things
		AlienVillageConfigHandler.init(new File(configDirectory, "namepieces_alienvillage.cfg"));
		MinecraftForge.EVENT_BUS.register(new AlienVillageConfigHandler());
		
		AlienConfigHandler.init(new File(configDirectory, "namepieces_alien.cfg"));
		MinecraftForge.EVENT_BUS.register(new AlienConfigHandler());
		
		GoblinConfigHandler.init(new File(configDirectory, "namepieces_goblin.cfg"));
		MinecraftForge.EVENT_BUS.register(new GoblinConfigHandler());
		
		
		// Syllable pools designed to give the player customizability
		CustomConfigHandler.init(new File(configDirectory, "namepieces_custom.cfg"));
		MinecraftForge.EVENT_BUS.register(new CustomConfigHandler());
		
	}
}
