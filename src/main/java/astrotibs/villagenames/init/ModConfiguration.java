package astrotibs.villagenames.init;

import java.io.File;

import astrotibs.villagenames.config.EndCityConfigHandler;
import astrotibs.villagenames.config.FortressConfigHandler;
import astrotibs.villagenames.config.GeneralConfigHandler;
import astrotibs.villagenames.config.MansionConfigHandler;
import astrotibs.villagenames.config.MineshaftConfigHandler;
import astrotibs.villagenames.config.MonumentConfigHandler;
import astrotibs.villagenames.config.OtherModConfigHandler;
import astrotibs.villagenames.config.StrongholdConfigHandler;
import astrotibs.villagenames.config.TempleConfigHandler;
import astrotibs.villagenames.config.VillageConfigHandler;
import astrotibs.villagenames.config.VillagerConfigHandler;
import net.minecraftforge.common.MinecraftForge;

public class ModConfiguration {
	public static void init(File configDirectory) {
		
		GeneralConfigHandler.init(new File(configDirectory, "vn_general.cfg"));
		MinecraftForge.EVENT_BUS.register(new GeneralConfigHandler());
		
		VillageConfigHandler.init(new File(configDirectory, "vn_village.cfg"));
		MinecraftForge.EVENT_BUS.register(new VillageConfigHandler());
		
		VillagerConfigHandler.init(new File(configDirectory, "vn_villager.cfg"));
		MinecraftForge.EVENT_BUS.register(new VillagerConfigHandler());
		
		MineshaftConfigHandler.init(new File(configDirectory, "vn_mineshaft.cfg"));
		MinecraftForge.EVENT_BUS.register(new MineshaftConfigHandler());
		
		StrongholdConfigHandler.init(new File(configDirectory, "vn_stronghold.cfg"));
		MinecraftForge.EVENT_BUS.register(new StrongholdConfigHandler());
		
		TempleConfigHandler.init(new File(configDirectory, "vn_temple.cfg"));
		MinecraftForge.EVENT_BUS.register(new TempleConfigHandler());
		
		FortressConfigHandler.init(new File(configDirectory, "vn_fortress.cfg"));
		MinecraftForge.EVENT_BUS.register(new FortressConfigHandler());
		
		MonumentConfigHandler.init(new File(configDirectory, "vn_monument.cfg"));
		MinecraftForge.EVENT_BUS.register(new MonumentConfigHandler());
		
		EndCityConfigHandler.init(new File(configDirectory, "vn_endcity.cfg"));
		MinecraftForge.EVENT_BUS.register(new EndCityConfigHandler());
		
		MansionConfigHandler.init(new File(configDirectory, "vn_mansion.cfg"));
		MinecraftForge.EVENT_BUS.register(new MansionConfigHandler());
		
		OtherModConfigHandler.init(new File(configDirectory, "vn_othermods.cfg"));
		MinecraftForge.EVENT_BUS.register(new OtherModConfigHandler());
		
	}
}
