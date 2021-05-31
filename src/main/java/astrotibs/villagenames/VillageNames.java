package astrotibs.villagenames;

import java.io.File;

import astrotibs.villagenames.banner.TileEntityBanner;
import astrotibs.villagenames.block.ModBlocksVN;
import astrotibs.villagenames.command.CommandBanner;
import astrotibs.villagenames.command.CommandName;
import astrotibs.villagenames.config.ConfigInit;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.config.village.VillageGeneratorConfigHandler;
import astrotibs.villagenames.handler.ChestLootHandler;
import astrotibs.villagenames.handler.DevVersionWarning;
import astrotibs.villagenames.handler.EntityMonitorHandler;
import astrotibs.villagenames.handler.ServerCleanExpired;
import astrotibs.villagenames.handler.ServerTrackerStarter;
import astrotibs.villagenames.handler.VersionChecker;
import astrotibs.villagenames.handler.VillagerTradeHandler;
import astrotibs.villagenames.igloo.IglooGeneratorIWG;
import astrotibs.villagenames.igloo.VNComponentIglooPieces;
import astrotibs.villagenames.igloo.VNMapGenIgloo;
import astrotibs.villagenames.init.Recipes;
import astrotibs.villagenames.integration.antiqueatlas.VillageWatcherAA;
import astrotibs.villagenames.integration.ganyssurface.TileEntityWoodSign;
import astrotibs.villagenames.item.ModItems;
import astrotibs.villagenames.nbt.NBTUpdater;
import astrotibs.villagenames.network.MessageModernVillagerSkin;
import astrotibs.villagenames.network.MessageVillageGuard;
import astrotibs.villagenames.network.MessageZombieVillagerProfession;
import astrotibs.villagenames.network.NetworkHelper;
import astrotibs.villagenames.prismarine.guardian.entity.monster.EntityGuardian;
import astrotibs.villagenames.prismarine.guardian.particle.PacketHandlerClient;
import astrotibs.villagenames.prismarine.guardian.particle.SToCMessage;
import astrotibs.villagenames.prismarine.guardian.spawning.SpawnEventListener;
import astrotibs.villagenames.prismarine.monument.MonumentGeneratorIWG;
import astrotibs.villagenames.prismarine.monument.StructureOceanMonument;
import astrotibs.villagenames.prismarine.monument.StructureOceanMonumentPieces;
import astrotibs.villagenames.prismarine.register.ModBlocksPrismarine;
import astrotibs.villagenames.prismarine.register.ModItemsPrismarine;
import astrotibs.villagenames.proxy.CommonProxy;
import astrotibs.villagenames.spawnegg.DispenserBehavior;
import astrotibs.villagenames.spawnegg.ItemSpawnEggVN;
import astrotibs.villagenames.spawnegg.SpawnEggRegistry;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.Reference;
import astrotibs.villagenames.village.MapGenVillageVN;
import astrotibs.villagenames.village.StructureCreationHandlers;
import astrotibs.villagenames.village.StructureVillageVN;
import astrotibs.villagenames.village.biomestructures.DesertStructures;
import astrotibs.villagenames.village.biomestructures.JungleStructures;
import astrotibs.villagenames.village.biomestructures.PlainsStructures;
import astrotibs.villagenames.village.biomestructures.SavannaStructures;
import astrotibs.villagenames.village.biomestructures.SnowyStructures;
import astrotibs.villagenames.village.biomestructures.SwampStructures;
import astrotibs.villagenames.village.biomestructures.TaigaStructures;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageCreationHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.init.Blocks;
import net.minecraft.stats.Achievement;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;

/*
 * Testing sword:
 * give @p golden_sword 1 0 {display:{Name:"Un-Instantiator"}, ench:[{id:16,lvl:1000},{id:34,lvl:99}]}
 * Loot level 3: {id:21,lvl:3}
 */

@Mod(	
		modid = Reference.MOD_ID,
		name = Reference.MOD_NAME,
		version = Reference.VERSION,
		guiFactory = Reference.GUI_FACTORY
		)
public final class VillageNames
{
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static CommonProxy PROXY;
	
	public static SimpleNetworkWrapper VNNetworkWrapper; //Added from Dragon Artifacts
	
	public static File configDirectory;
	
	@Instance(Reference.MOD_ID)
	public static VillageNames instance;
	
	public static String currentConfigFolder = "VillageNames4";
	public static String[] oldConfigFolders = new String[]{"VillageNames3", "VillageNames"};
	
    // instantiate achievements
	public static Achievement maxrep;
	public static Achievement minrep;
	public static Achievement ghosttown;
	public static Achievement archaeologist;
	public static Achievement laputa;
	
	// Version checking instance
	public static VersionChecker versionChecker = new VersionChecker();
	public static boolean haveWarnedVersionOutOfDate = false;
	public static boolean devVersionWarned = false;
	
	// Disallow trades that discriminate meta values sold from player unless this mod is loaded
	public static boolean canVillagerTradesDistinguishMeta = false;
	
	/*
	 * The number of structures you need to use the Codex on to trigger the achievement.
	 * If the player does not use any mods that add valid searchable structures,
	 * AND they're using the 1.7 version of Village Names,
	 * AND they're not using its optional Monument or Igloo generation,
	 * then there are seven structures they can identify, so they have to identify them all.
	 * The structures are:
	 * 
	 * Village
	 * Desert Pyramid
	 * Jungle Pyramid
	 * Swamp Hut
	 * Mineshaft
	 * Stronghold
	 * Nether Fortress
	 */
	public static int numberStructuresArchaeologist = 7;
    
	public static ItemSpawnEggVN spawnEgg;
	
	// PRE-INIT
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		configDirectory = new File(event.getModConfigurationDirectory(), currentConfigFolder);
		ConfigInit.init(configDirectory);
		
		// Log a warning to the user if an old config folder is detected
		for (String oldConfigFolder : oldConfigFolders)
		{
			if (new File(event.getModConfigurationDirectory(), oldConfigFolder).exists())
			{
				LogHelper.warn(
						"ATTENTION! The old configuration folder " + oldConfigFolder + " will NOT BE USED in this version of "+Reference.MOD_NAME+"! "
								+ "A new " + currentConfigFolder + " folder has been created. Old config values HAVE NOT BEEN COPIED OVER.");
				LogHelper.warn("Remove the "+ oldConfigFolder + " folder (save a backup!) to prevent this message in the future.");
				break;
			}
		}
		
		canVillagerTradesDistinguishMeta = Loader.isModLoaded("VillagerMetaFix");
		
		
		// Moved down here to make sure config fires first!?
		ModItems.init();
		ModBlocksVN.init();
		
		
		if (GeneralConfig.codexChestLoot) { // Chest loot
			ChestLootHandler.init();
		}
		
		if (GeneralConfig.addOceanMonuments)
		{
			// Monuments, Prismarine, Guardians, Sponges
			// Register Prismarine stuff here
			ModBlocksPrismarine.init();
			ModItemsPrismarine.init();
			
			// Register Ocean Monument stuff here
			GameRegistry.registerWorldGenerator(new MonumentGeneratorIWG(), 0);
			MapGenStructureIO.registerStructure(StructureOceanMonument.StartMonument.class, "Monument");
			StructureOceanMonumentPieces.registerOceanMonumentPieces();
			
			// Guardian registry has been moved to init()
			
			// Spawn egg stuff
			spawnEgg = new ItemSpawnEggVN();
			GameRegistry.registerItem(spawnEgg, spawnEgg.getUnlocalizedName());
			BlockDispenser.dispenseBehaviorRegistry.putObject(spawnEgg, new DispenserBehavior());
			
			MinecraftForge.EVENT_BUS.register(new SpawnEventListener());
			LogHelper.info("Ocean Monuments, Prismarine, Guardians, and Sponges registered");
			
		}
		
		if (GeneralConfig.addIgloos)
		{
			GameRegistry.registerWorldGenerator(new IglooGeneratorIWG(), 0);
			MapGenStructureIO.registerStructure(VNMapGenIgloo.Start.class, "Temple");
			VNComponentIglooPieces.registerScatteredFeaturePieces();
			ChestLootHandler.iglooChest();
			LogHelper.info("Registered Igloo generation");
		}
		
		// New village generator
		if (VillageGeneratorConfigHandler.newVillageGenerator)
		{
			// New village generator
			MapGenStructureIO.registerStructure(MapGenVillageVN.Start.class, "MapGenVillageVN");
			
			// Village Misc
	        MapGenStructureIO.func_143031_a(StructureVillageVN.PathVN.class, "VNPath"); // Path
	        MapGenStructureIO.func_143031_a(StructureVillageVN.DecorTorch.class, "VNDecTor"); // Decor Torch
	        
			// Village Centers
	        MapGenStructureIO.func_143031_a(PlainsStructures.PlainsFountain01.class, "VNPlF01"); // Fountain
	        MapGenStructureIO.func_143031_a(PlainsStructures.PlainsMeetingPoint1.class, "VNPlMP1"); // Plains Well
	        MapGenStructureIO.func_143031_a(PlainsStructures.PlainsMeetingPoint2.class, "VNPlMP2"); // Plains Market
	        MapGenStructureIO.func_143031_a(PlainsStructures.PlainsMeetingPoint3.class, "VNPlMP3"); // Central Tree
	        MapGenStructureIO.func_143031_a(DesertStructures.DesertMeetingPoint1.class, "VNDeMP1"); // Fountain and Building
	        MapGenStructureIO.func_143031_a(DesertStructures.DesertMeetingPoint2.class, "VNDeMP2"); // Desert Well
	        MapGenStructureIO.func_143031_a(DesertStructures.DesertMeetingPoint3.class, "VNDeMP3"); // Desert Market
	        MapGenStructureIO.func_143031_a(TaigaStructures.TaigaMeetingPoint1.class, "VNTaMP1"); // Grass patch with two structures
	        MapGenStructureIO.func_143031_a(TaigaStructures.TaigaMeetingPoint2.class, "VNTaMP2"); // Taiga Well
	        MapGenStructureIO.func_143031_a(SavannaStructures.SavannaMeetingPoint1.class, "VNSaMP1"); // Savanna Market
	        MapGenStructureIO.func_143031_a(SavannaStructures.SavannaMeetingPoint2.class, "VNSaMP2"); // Savanna Fountain
	        MapGenStructureIO.func_143031_a(SavannaStructures.SavannaMeetingPoint3.class, "VNSaMP3"); // Savanna double well
	        MapGenStructureIO.func_143031_a(SavannaStructures.SavannaMeetingPoint4.class, "VNSaMP4"); // Savanna single well
	        MapGenStructureIO.func_143031_a(SnowyStructures.SnowyMeetingPoint1.class, "VNSnMP1"); // Snowy Ice Spire
	        MapGenStructureIO.func_143031_a(SnowyStructures.SnowyMeetingPoint2.class, "VNSnMP2"); // Frozen Fountain
	        MapGenStructureIO.func_143031_a(SnowyStructures.SnowyMeetingPoint3.class, "VNSnMP3"); // Snowy Pavilion
	        MapGenStructureIO.func_143031_a(JungleStructures.JungleStatue.class, "VNJuSta"); // Jungle Statue
	        MapGenStructureIO.func_143031_a(JungleStructures.JungleCocoaTree.class, "VNJuTre"); // Jungle Tree
	        MapGenStructureIO.func_143031_a(JungleStructures.JungleGarden.class, "VNJuGar"); // Jungle Garden
	        MapGenStructureIO.func_143031_a(JungleStructures.JungleVilla.class, "VNJuVil"); // Jungle Villa
	        MapGenStructureIO.func_143031_a(SwampStructures.SwampWillow.class, "VNSwWil"); // Swamp Willow
	        MapGenStructureIO.func_143031_a(SwampStructures.SwampStatue.class, "VNSwSta"); // Swamp Statue
	        MapGenStructureIO.func_143031_a(SwampStructures.SwampPavilion.class, "VNSwPav"); // Swamp Pavilion
	        MapGenStructureIO.func_143031_a(SwampStructures.SwampMonolith.class, "VNSwMon"); // Swamp Monolith
	        
	        // Village Structures
	        registerVillageComponentBuilding(PlainsStructures.PlainsAccessory1.class, "VNPlAcc1", new StructureCreationHandlers.PlainsAccessory1_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsAnimalPen1.class, "VNPlAnP1", new StructureCreationHandlers.PlainsAnimalPen1_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsAnimalPen2.class, "VNPlAnP2", new StructureCreationHandlers.PlainsAnimalPen2_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsAnimalPen3.class, "VNPlAnP3", new StructureCreationHandlers.PlainsAnimalPen3_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsArmorerHouse1.class, "VNPlArm1", new StructureCreationHandlers.PlainsArmorerHouse1_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsBigHouse1.class, "VNPlBiH1", new StructureCreationHandlers.PlainsBigHouse1_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsButcherShop1.class, "VNPlBut1", new StructureCreationHandlers.PlainsButcherShop1_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsButcherShop2.class, "VNPlBut2", new StructureCreationHandlers.PlainsButcherShop2_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsCartographer1.class, "VNPlCar1", new StructureCreationHandlers.PlainsCartographer1_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsFisherCottage1.class, "VNPlFis1", new StructureCreationHandlers.PlainsFisherCottage1_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsFletcherHouse1.class, "VNPlFle1", new StructureCreationHandlers.PlainsFletcherHouse1_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsLargeFarm1.class, "VNPlLFa1", new StructureCreationHandlers.PlainsLargeFarm1_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsLibrary1.class, "VNPlLib1", new StructureCreationHandlers.PlainsLibrary1_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsLibrary2.class, "VNPlLib2", new StructureCreationHandlers.PlainsLibrary2_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsMasonsHouse1.class, "VNPlMas1", new StructureCreationHandlers.PlainsMasonsHouse1_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsMediumHouse1.class, "VNPlMeH1", new StructureCreationHandlers.PlainsMediumHouse1_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsMediumHouse2.class, "VNPlMeH2", new StructureCreationHandlers.PlainsMediumHouse2_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsMeetingPoint4.class, "VNPlMeP4", new StructureCreationHandlers.PlainsMeetingPoint4_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsMeetingPoint5.class, "VNPlMeP5", new StructureCreationHandlers.PlainsMeetingPoint5_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsShepherdsHouse1.class, "VNPlShe1", new StructureCreationHandlers.PlainsShepherdsHouse1_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsSmallFarm1.class, "VNPlSFa1", new StructureCreationHandlers.PlainsSmallFarm1_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsSmallHouse1.class, "VNPlSmH1", new StructureCreationHandlers.PlainsSmallHouse1_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsSmallHouse2.class, "VNPlSmH2", new StructureCreationHandlers.PlainsSmallHouse2_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsSmallHouse3.class, "VNPlSmH3", new StructureCreationHandlers.PlainsSmallHouse3_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsSmallHouse4.class, "VNPlSmH4", new StructureCreationHandlers.PlainsSmallHouse4_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsSmallHouse5.class, "VNPlSmH5", new StructureCreationHandlers.PlainsSmallHouse5_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsSmallHouse6.class, "VNPlSmH6", new StructureCreationHandlers.PlainsSmallHouse6_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsSmallHouse7.class, "VNPlSmH7", new StructureCreationHandlers.PlainsSmallHouse7_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsSmallHouse8.class, "VNPlSmH8", new StructureCreationHandlers.PlainsSmallHouse8_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsStable1.class, "VNPlSta1", new StructureCreationHandlers.PlainsStable1_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsStable2.class, "VNPlSta2", new StructureCreationHandlers.PlainsStable2_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsTannery1.class, "VNPlTan2", new StructureCreationHandlers.PlainsTannery1_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsTemple3.class, "VNPlTem3", new StructureCreationHandlers.PlainsTemple3_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsTemple4.class, "VNPlTem4", new StructureCreationHandlers.PlainsTemple4_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsToolSmith1.class, "VNPlTSm1", new StructureCreationHandlers.PlainsToolSmith1_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsWeaponsmith1.class, "VNPlWSm1", new StructureCreationHandlers.PlainsWeaponsmith1_Handler());
	        registerVillageComponentBuilding(PlainsStructures.PlainsStreetDecor1.class, "VNPlStD1", new StructureCreationHandlers.PlainsStreetDecor1_Handler());
	        
	        registerVillageComponentBuilding(DesertStructures.DesertAnimalPen1.class, "VNDeAnP1", new StructureCreationHandlers.DesertAnimalPen1_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertAnimalPen2.class, "VNDeAnP2", new StructureCreationHandlers.DesertAnimalPen2_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertArmorer1.class, "VNDeArm1", new StructureCreationHandlers.DesertArmorer1_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertButcherShop1.class, "VNDeBut1", new StructureCreationHandlers.DesertButcherShop1_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertCartographerHouse1.class, "VNDeCar1", new StructureCreationHandlers.DesertCartographerHouse1_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertFarm1.class, "VNDeFar1", new StructureCreationHandlers.DesertFarm1_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertFarm2.class, "VNDeFar2", new StructureCreationHandlers.DesertFarm2_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertFisher1.class, "VNDeFis1", new StructureCreationHandlers.DesertFisher1_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertFletcherHouse1.class, "VNDeFle1", new StructureCreationHandlers.DesertFletcherHouse1_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertLargeFarm1.class, "VNDeLFa1", new StructureCreationHandlers.DesertLargeFarm1_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertLibrary1.class, "VNDeLib1", new StructureCreationHandlers.DesertLibrary1_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertMason1.class, "VNDeMas1", new StructureCreationHandlers.DesertMason1_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertMediumHouse1.class, "VNDeMeH1", new StructureCreationHandlers.DesertMediumHouse1_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertMediumHouse2.class, "VNDeMeH2", new StructureCreationHandlers.DesertMediumHouse2_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertShepherdHouse1.class, "VNDeShe1", new StructureCreationHandlers.DesertShepherdHouse1_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertSmallHouse1.class, "VNDeSmH1", new StructureCreationHandlers.DesertSmallHouse1_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertSmallHouse2.class, "VNDeSmH2", new StructureCreationHandlers.DesertSmallHouse2_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertSmallHouse3.class, "VNDeSmH3", new StructureCreationHandlers.DesertSmallHouse3_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertSmallHouse4.class, "VNDeSmH4", new StructureCreationHandlers.DesertSmallHouse4_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertSmallHouse5.class, "VNDeSmH5", new StructureCreationHandlers.DesertSmallHouse5_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertSmallHouse6.class, "VNDeSmH6", new StructureCreationHandlers.DesertSmallHouse6_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertSmallHouse7.class, "VNDeSmH7", new StructureCreationHandlers.DesertSmallHouse7_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertSmallHouse8.class, "VNDeSmH8", new StructureCreationHandlers.DesertSmallHouse8_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertTannery1.class, "VNDeTan1", new StructureCreationHandlers.DesertTannery1_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertTemple1.class, "VNDeTem1", new StructureCreationHandlers.DesertTemple1_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertTemple2.class, "VNDeTem2", new StructureCreationHandlers.DesertTemple2_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertToolSmith1.class, "VNDeTSm1", new StructureCreationHandlers.DesertToolSmith1_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertWeaponsmith1.class, "VNDeWSm1", new StructureCreationHandlers.DesertWeaponsmith1_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertStreetDecor1.class, "VNDeStD1", new StructureCreationHandlers.SavannaStreetDecor1_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertStreetSubstitute1.class, "VNDeStS1", new StructureCreationHandlers.DesertStreetSubstitute1_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertStreetSubstitute2.class, "VNDeStS2", new StructureCreationHandlers.DesertStreetSubstitute2_Handler());
	        registerVillageComponentBuilding(DesertStructures.DesertStreetSubstitute3.class, "VNDeStS3", new StructureCreationHandlers.DesertStreetSubstitute3_Handler());
	        
	        registerVillageComponentBuilding(TaigaStructures.TaigaAnimalPen1.class, "VNTaAnP1", new StructureCreationHandlers.TaigaAnimalPen1_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaArmorer2.class, "VNTaArm2", new StructureCreationHandlers.TaigaArmorer2_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaArmorerHouse1.class, "VNTaArm1", new StructureCreationHandlers.TaigaArmorerHouse1_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaButcherShop1.class, "VNTaBut1", new StructureCreationHandlers.TaigaButcherShop1_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaCartographerHouse1.class, "VNTaCar1", new StructureCreationHandlers.TaigaCartographerHouse1_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaFisherCottage1.class, "VNTaFis1", new StructureCreationHandlers.TaigaFisherCottage1_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaFletcherHouse1.class, "VNTaFle1", new StructureCreationHandlers.TaigaFletcherHouse1_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaLargeFarm1.class, "VNTaLFa1", new StructureCreationHandlers.TaigaLargeFarm1_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaLargeFarm2.class, "VNTaLFa2", new StructureCreationHandlers.TaigaLargeFarm2_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaLibrary1.class, "VNTaLib1", new StructureCreationHandlers.TaigaLibrary1_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaMasonsHouse1.class, "VNTaMas1", new StructureCreationHandlers.TaigaMasonsHouse1_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaMediumHouse1.class, "VNTaMeH1", new StructureCreationHandlers.TaigaMediumHouse1_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaMediumHouse2.class, "VNTaMeH2", new StructureCreationHandlers.TaigaMediumHouse2_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaMediumHouse3.class, "VNTaMeH3", new StructureCreationHandlers.TaigaMediumHouse3_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaMediumHouse4.class, "VNTaMeH4", new StructureCreationHandlers.TaigaMediumHouse4_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaShepherdsHouse1.class, "VNTaShe1", new StructureCreationHandlers.TaigaShepherdsHouse1_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaSmallFarm1.class, "VNTaSFa1", new StructureCreationHandlers.TaigaSmallFarm1_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaSmallHouse1.class, "VNTaSmH1", new StructureCreationHandlers.TaigaSmallHouse1_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaSmallHouse2.class, "VNTaSmH2", new StructureCreationHandlers.TaigaSmallHouse2_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaSmallHouse3.class, "VNTaSmH3", new StructureCreationHandlers.TaigaSmallHouse3_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaSmallHouse4.class, "VNTaSmH4", new StructureCreationHandlers.TaigaSmallHouse4_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaSmallHouse5.class, "VNTaSmH5", new StructureCreationHandlers.TaigaSmallHouse5_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaTannery1.class, "VNTaTan1", new StructureCreationHandlers.TaigaTannery1_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaTemple1.class, "VNTaTem1", new StructureCreationHandlers.TaigaTemple1_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaToolSmith1.class, "VNTaTSm1", new StructureCreationHandlers.TaigaToolSmith1_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaWeaponsmith1.class, "VNTaWSm1", new StructureCreationHandlers.TaigaWeaponsmith1_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaWeaponsmith2.class, "VNTaWSm2", new StructureCreationHandlers.TaigaWeaponsmith2_Handler());
	        registerVillageComponentBuilding(TaigaStructures.TaigaStreetDecor1.class, "VNTaStD1", new StructureCreationHandlers.TaigaStreetDecor1_Handler());
	        
	        registerVillageComponentBuilding(SavannaStructures.SavannaAnimalPen1.class, "VNSaAnP1", new StructureCreationHandlers.SavannaAnimalPen1_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaAnimalPen2.class, "VNSaAnP2", new StructureCreationHandlers.SavannaAnimalPen2_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaAnimalPen3.class, "VNSaAnP3", new StructureCreationHandlers.SavannaAnimalPen3_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaArmorer1.class, "VNSaArm1", new StructureCreationHandlers.SavannaArmorer1_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaButchersShop1.class, "VNSaBut1", new StructureCreationHandlers.SavannaButchersShop1_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaButchersShop2.class, "VNSaBut2", new StructureCreationHandlers.SavannaButchersShop2_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaCartographer1.class, "VNSaCar1", new StructureCreationHandlers.SavannaCartographer1_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaFisherCottage1.class, "VNSaFis1", new StructureCreationHandlers.SavannaFisherCottage1_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaFletcherHouse1.class, "VNSaFle1", new StructureCreationHandlers.SavannaFletcherHouse1_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaLargeFarm1.class, "VNSaLFa1", new StructureCreationHandlers.SavannaLargeFarm1_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaLargeFarm2.class, "VNSaLFa2", new StructureCreationHandlers.SavannaLargeFarm2_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaLibrary1.class, "VNSaLib1", new StructureCreationHandlers.SavannaLibrary1_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaMason1.class, "VNSaMas1", new StructureCreationHandlers.SavannaMason1_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaMediumHouse1.class, "VNSaMeH1", new StructureCreationHandlers.SavannaMediumHouse1_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaMediumHouse2.class, "VNSaMeH2", new StructureCreationHandlers.SavannaMediumHouse2_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaShepherd1.class, "VNSaShe1", new StructureCreationHandlers.SavannaShepherd1_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaSmallFarm.class, "VNSaSmFa", new StructureCreationHandlers.SavannaSmallFarm_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaSmallHouse1.class, "VNSaSmH1", new StructureCreationHandlers.SavannaSmallHouse1_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaSmallHouse2.class, "VNSaSmH2", new StructureCreationHandlers.SavannaSmallHouse2_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaSmallHouse3.class, "VNSaSmH3", new StructureCreationHandlers.SavannaSmallHouse3_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaSmallHouse4.class, "VNSaSmH4", new StructureCreationHandlers.SavannaSmallHouse4_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaSmallHouse5.class, "VNSaSmH5", new StructureCreationHandlers.SavannaSmallHouse5_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaSmallHouse6.class, "VNSaSmH6", new StructureCreationHandlers.SavannaSmallHouse6_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaSmallHouse7.class, "VNSaSmH7", new StructureCreationHandlers.SavannaSmallHouse7_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaSmallHouse8.class, "VNSaSmH8", new StructureCreationHandlers.SavannaSmallHouse8_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaTannery1.class, "VNSaTsn1", new StructureCreationHandlers.SavannaTannery1_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaTemple1.class, "VNSaTem1", new StructureCreationHandlers.SavannaTemple1_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaTemple2.class, "VNSaTem2", new StructureCreationHandlers.SavannaTemple2_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaToolSmith1.class, "VNSaTSm1", new StructureCreationHandlers.SavannaToolSmith1_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaWeaponsmith1.class, "VNSaWSm1", new StructureCreationHandlers.SavannaWeaponsmith1_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaWeaponsmith2.class, "VNSaWSm2", new StructureCreationHandlers.SavannaWeaponsmith2_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaStreetDecor1.class, "VNSaStD1", new StructureCreationHandlers.SavannaStreetDecor1_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaStreetSubstitute1.class, "VNSaStS1", new StructureCreationHandlers.SavannaStreetSubstitute1_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaStreetSubstitute2.class, "VNSaStS2", new StructureCreationHandlers.SavannaStreetSubstitute2_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaStreetSubstitute3.class, "VNSaStS3", new StructureCreationHandlers.SavannaStreetSubstitute3_Handler());
	        registerVillageComponentBuilding(SavannaStructures.SavannaStreetSubstitute4.class, "VNSaStS4", new StructureCreationHandlers.SavannaStreetSubstitute4_Handler());
	        
	        registerVillageComponentBuilding(SnowyStructures.SnowyAnimalPen1.class, "VNSnAnP1", new StructureCreationHandlers.SnowyAnimalPen1_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyAnimalPen2.class, "VNSnAnP2", new StructureCreationHandlers.SnowyAnimalPen2_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyArmorerHouse1.class, "VNSnArH1", new StructureCreationHandlers.SnowyArmorerHouse1_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyArmorerHouse2.class, "VNSnArH2", new StructureCreationHandlers.SnowyArmorerHouse2_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyButchersShop1.class, "VNSnBut1", new StructureCreationHandlers.SnowyButchersShop1_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyButchersShop2.class, "VNSnBut2", new StructureCreationHandlers.SnowyButchersShop2_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyCartographerHouse1.class, "VNSnCar1", new StructureCreationHandlers.SnowyCartographerHouse1_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyFarm1.class, "VNSnFar1", new StructureCreationHandlers.SnowyFarm1_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyFarm2.class, "VNSnFar2", new StructureCreationHandlers.SnowyFarm2_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyFisherCottage.class, "VNSnFisC", new StructureCreationHandlers.SnowyFisherCottage_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyFletcherHouse1.class, "VNSnFle1", new StructureCreationHandlers.SnowyFletcherHouse1_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyLibrary1.class, "VNSnLib1", new StructureCreationHandlers.SnowyLibrary1_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyMasonsHouse1.class, "VNSnMas1", new StructureCreationHandlers.SnowyMasonsHouse1_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyMasonsHouse2.class, "VNSnMas2", new StructureCreationHandlers.SnowyMasonsHouse2_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyMediumHouse1.class, "VNSnMeH1", new StructureCreationHandlers.SnowyMediumHouse1_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyMediumHouse2.class, "VNSnMeH2", new StructureCreationHandlers.SnowyMediumHouse2_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyMediumHouse3.class, "VNSnMeH3", new StructureCreationHandlers.SnowyMediumHouse3_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyShepherdsHouse1.class, "VNSnShe1", new StructureCreationHandlers.SnowyShepherdsHouse1_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowySmallHouse1.class, "VNSnSmH1", new StructureCreationHandlers.SnowySmallHouse1_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowySmallHouse2.class, "VNSnSmH2", new StructureCreationHandlers.SnowySmallHouse2_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowySmallHouse3.class, "VNSnSmH3", new StructureCreationHandlers.SnowySmallHouse3_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowySmallHouse4.class, "VNSnSmH4", new StructureCreationHandlers.SnowySmallHouse4_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowySmallHouse5.class, "VNSnSmH5", new StructureCreationHandlers.SnowySmallHouse5_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowySmallHouse6.class, "VNSnSmH6", new StructureCreationHandlers.SnowySmallHouse6_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowySmallHouse7.class, "VNSnSmH7", new StructureCreationHandlers.SnowySmallHouse7_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowySmallHouse8.class, "VNSnSmH8", new StructureCreationHandlers.SnowySmallHouse8_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyTannery1.class, "VNSnTan1", new StructureCreationHandlers.SnowyTannery1_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyTemple1.class, "VNSnTem1", new StructureCreationHandlers.SnowyTemple1_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyToolSmith1.class, "VNSnTSm1", new StructureCreationHandlers.SnowyToolSmith1_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyWeaponSmith1.class, "VNSnWSm1", new StructureCreationHandlers.SnowyWeaponSmith1_Handler());
	        registerVillageComponentBuilding(SnowyStructures.SnowyStreetDecor1.class, "VNSnStD1", new StructureCreationHandlers.SnowyStreetDecor1_Handler());
	        
	        registerVillageComponentBuilding(JungleStructures.JungleArmorerHouse.class, "VNJuArmH", new StructureCreationHandlers.JungleArmorerHouse_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleButcherShop.class, "VNJuButS", new StructureCreationHandlers.JungleButcherShop_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleCartographerHouse1.class, "VNJuCaH1", new StructureCreationHandlers.JungleCartographerHouse1_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleCartographerHouse2.class, "VNJuCaH2", new StructureCreationHandlers.JungleCartographerHouse2_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleFisherCottage.class, "VNJuFshC", new StructureCreationHandlers.JungleFisherCottage_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleFletcherHouse1.class, "VNJuFlH1", new StructureCreationHandlers.JungleFletcherHouse1_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleFletcherHouse2.class, "VNJuFlH2", new StructureCreationHandlers.JungleFletcherHouse2_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleLargeHouse.class, "VNJuLaHo", new StructureCreationHandlers.JungleLargeHouse_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleLibrary.class, "VNJuLibr", new StructureCreationHandlers.JungleLibrary_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleMasonHouse.class, "VNJuMasH", new StructureCreationHandlers.JungleMasonHouse_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleMediumHouse1.class, "VNJuMeH1", new StructureCreationHandlers.JungleMediumHouse1_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleMediumHouse2.class, "VNJuMeH2", new StructureCreationHandlers.JungleMediumHouse2_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleMediumHouse3.class, "VNJuMeH3", new StructureCreationHandlers.JungleMediumHouse3_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleMediumHouse4.class, "VNJuMeH4", new StructureCreationHandlers.JungleMediumHouse4_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleShepherdHouse.class, "VNJuShpH", new StructureCreationHandlers.JungleShepherdHouse_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleSmallHouse1.class, "VNJuSmH1", new StructureCreationHandlers.JungleSmallHouse1_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleSmallHouse2.class, "VNJuSmH2", new StructureCreationHandlers.JungleSmallHouse2_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleSmallHouse3.class, "VNJuSmH3", new StructureCreationHandlers.JungleSmallHouse3_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleSmallHouse4.class, "VNJuSmH4", new StructureCreationHandlers.JungleSmallHouse4_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleSmallHouse5.class, "VNJuSmH5", new StructureCreationHandlers.JungleSmallHouse5_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleSmallHouse6.class, "VNJuSmH6", new StructureCreationHandlers.JungleSmallHouse6_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleSmallHouse7.class, "VNJuSmH7", new StructureCreationHandlers.JungleSmallHouse7_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleSmallHouse8.class, "VNJuSmH8", new StructureCreationHandlers.JungleSmallHouse8_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleStable.class, "VNJuStbl", new StructureCreationHandlers.JungleStable_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleSteppedFarm.class, "VNJuStFa", new StructureCreationHandlers.JungleSteppedFarm_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleStoneAnimalPen.class, "VNJuStAP", new StructureCreationHandlers.JungleStoneAnimalPen_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleTamedFarm.class, "VNJuTaFa", new StructureCreationHandlers.JungleTamedFarm_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleTannery1.class, "VNJuTan1", new StructureCreationHandlers.JungleTannery1_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleTannery2.class, "VNJuTan2", new StructureCreationHandlers.JungleTannery2_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleTemple.class, "VNJuTemp", new StructureCreationHandlers.JungleTemple_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleToolSmithy1.class, "VNJuTSm1", new StructureCreationHandlers.JungleToolSmithy1_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleToolSmithy2.class, "VNJuTSm2", new StructureCreationHandlers.JungleToolSmithy2_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleWeaponSmithy.class, "VNJuWpSm", new StructureCreationHandlers.JungleWeaponSmithy_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleWildFarm.class, "VNJuWiFa", new StructureCreationHandlers.JungleWildFarm_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleWoodAnimalPen.class, "VNJuWdAP", new StructureCreationHandlers.JungleWoodAnimalPen_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleStreetDecor.class, "VNJuStDe", new StructureCreationHandlers.JungleStreetDecor_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleRoadAccent1.class, "VNJuRdA1", new StructureCreationHandlers.JungleRoadAccent1_Handler());
	        registerVillageComponentBuilding(JungleStructures.JungleRoadAccent2.class, "VNJuRdA2", new StructureCreationHandlers.JungleRoadAccent2_Handler());
	        
	        registerVillageComponentBuilding(SwampStructures.SwampAnimalPen1.class, "VNSwAnP1", new StructureCreationHandlers.SwampAnimalPen1_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampAnimalPen2.class, "VNSwAnP2", new StructureCreationHandlers.SwampAnimalPen2_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampArmorerHouse.class, "VNSwArHo", new StructureCreationHandlers.SwampArmorerHouse_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampButcherShop.class, "VNSwBuSh", new StructureCreationHandlers.SwampButcherShop_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampCartographerHouse.class, "VNSwCaHo", new StructureCreationHandlers.SwampCartographerHouse_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampFisherCottage1.class, "VNSwFiC1", new StructureCreationHandlers.SwampFisherCottage1_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampFisherCottage2.class, "VNSwFiC2", new StructureCreationHandlers.SwampFisherCottage2_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampFletcherHouse.class, "VNSwFlHo", new StructureCreationHandlers.SwampFletcherHouse_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampHutFarm.class, "VNSwHuFa", new StructureCreationHandlers.SwampHutFarm_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampHorribleSecret.class, "VNSwHoSe", new StructureCreationHandlers.SwampHorribleSecret_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampLargeHouse.class, "VNSwLaHo", new StructureCreationHandlers.SwampLargeHouse_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampLibrary.class, "VNSwLibr", new StructureCreationHandlers.SwampLibrary_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampMasonHouse.class, "VNSwMaHo", new StructureCreationHandlers.SwampMasonHouse_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampMediumHouse1.class, "VNSwSmH1", new StructureCreationHandlers.SwampMediumHouse1_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampMediumHouse1.class, "VNSwSmH1", new StructureCreationHandlers.SwampMediumHouse1_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampMediumHouse2.class, "VNSwSmH2", new StructureCreationHandlers.SwampMediumHouse2_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampShepherdHouse1.class, "VNSwShH1", new StructureCreationHandlers.SwampShepherdHouse1_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampShepherdHouse2.class, "VNSwShH2", new StructureCreationHandlers.SwampShepherdHouse2_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampSmallHouse1.class, "VNSwSmH1", new StructureCreationHandlers.SwampSmallHouse1_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampSmallHouse2.class, "VNSwSmH2", new StructureCreationHandlers.SwampSmallHouse2_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampSmallHouse3.class, "VNSwSmH3", new StructureCreationHandlers.SwampSmallHouse3_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampSmallHouse4.class, "VNSwSmH4", new StructureCreationHandlers.SwampSmallHouse4_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampSmallHouse5.class, "VNSwSmH5", new StructureCreationHandlers.SwampSmallHouse5_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampStable.class, "VNSwStbl", new StructureCreationHandlers.SwampStable_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampTannery.class, "VNSwTann", new StructureCreationHandlers.SwampTannery_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampTemple.class, "VNSwTemp", new StructureCreationHandlers.SwampTemple_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampToolSmithy.class, "VNSwToSm", new StructureCreationHandlers.SwampToolSmithy_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampWeaponSmithy.class, "VNSwWeSm", new StructureCreationHandlers.SwampWeaponSmithy_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampWildFarm.class, "VNSwWiFa", new StructureCreationHandlers.SwampWildFarm_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampStreetDecor.class, "VNSwStDe", new StructureCreationHandlers.SwampStreetDecor_Handler());
	        registerVillageComponentBuilding(SwampStructures.SwampRoadAccent.class, "VNSwRdAc", new StructureCreationHandlers.SwampRoadAccent_Handler());
	        
	        
	        // Listener that interrupts old village generation with the new one
			MinecraftForge.TERRAIN_GEN_BUS.register(new MapGenVillageVN());
			
			// Chest hooks
			ChestLootHandler.modernVillageChests();
			
			LogHelper.info("Registered new Village generator");
		}
		
		
		// Listener that will fire on world loading, to generate the new nbt files from your old ones.
		MinecraftForge.EVENT_BUS.register(new NBTUpdater());
        
		// Event Handlers
        MinecraftForge.EVENT_BUS.register(new ServerTrackerStarter());
        MinecraftForge.EVENT_BUS.register(new EntityMonitorHandler());
        FMLCommonHandler.instance().bus().register(new ServerCleanExpired());
        
        // Version check monitor
        if (GeneralConfig.versionChecker) {FMLCommonHandler.instance().bus().register(versionChecker);}
        if ((Reference.VERSION).contains("DEV")) {FMLCommonHandler.instance().bus().register(new DevVersionWarning());}
        
        
		PROXY.preInit(event);
		
		// Establish the channel
        VNNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_CHANNEL);
        
        // Register different messages here
        
        int messageID = 0;
		
        VNNetworkWrapper.registerMessage(NetworkHelper.ZombieVillagerProfessionHandler.class, MessageZombieVillagerProfession.class, messageID++, Side.CLIENT);
        VNNetworkWrapper.registerMessage(NetworkHelper.VillageGuardHandler.class, MessageVillageGuard.class, messageID++, Side.CLIENT);
        VNNetworkWrapper.registerMessage(PacketHandlerClient.class, SToCMessage.class, messageID++, Side.CLIENT);
        VNNetworkWrapper.registerMessage(NetworkHelper.ModernVillagerSkinHandler.class, MessageModernVillagerSkin.class, messageID++, Side.CLIENT);
		
        
		
		// The following overrides the mcmod.info file!
		// Adapted from Jabelar's Magic Beans:
		// https://github.com/jabelar/MagicBeans-1.7.10/blob/e48456397f9c6c27efce18e6b9ad34407e6bc7c7/src/main/java/com/blogspot/jabelarminecraft/magicbeans/MagicBeans.java
        event.getModMetadata().autogenerated = false ; // stops it from complaining about missing mcmod.info
        
        event.getModMetadata().name = 			// name 
        		EnumChatFormatting.GOLD + 
        		Reference.MOD_NAME;
        
        event.getModMetadata().version = 		// version 
        		EnumChatFormatting.YELLOW+
        		Reference.VERSION;
        
        event.getModMetadata().credits = 		// credits 
        		EnumChatFormatting.AQUA +
        		"Thanks: Pahimar, MineMaarten, whrrgarbl, Jabelar, Darian Stephens";
        
        event.getModMetadata().authorList.clear();
        event.getModMetadata().authorList.add(  // authorList - added as a list
        		EnumChatFormatting.BLUE +
        		"AstroTibs");
        
        event.getModMetadata().url = EnumChatFormatting.GRAY +
        		Reference.URL;
        
        event.getModMetadata().description = 	// description
	       		EnumChatFormatting.GREEN +
	       		"Generates random names for villages, villagers, and other structures and entities.";
        
        event.getModMetadata().logoFile = "assets/villagenames/vn_banner.png";
        
        
        
        // --- New Villager Profession/Career stuff --- //
        
        // Iterate through all the villager types and add their new trades.
        // This accounts only for the vanilla types, and that's A-OK.
        if (GeneralConfig.villagerCareers) {
        	for (int i = 0; i < 5; ++i) {
        		VillagerRegistry.instance().registerVillageTradeHandler(i, new VillagerTradeHandler());
        	}
        	LogHelper.info("Registered Villager careers and updated trade pools");
        }
	    
        // Register the Nitwit
        if (GeneralConfig.enableNitwit) {
        	VillagerRegistry.instance().registerVillagerId(5);
        	LogHelper.info("Registered Nitwit villager");
        	}
        
        // Register prof 6 for testing purposes
        //VillagerRegistry.instance().registerVillageTradeHandler(6, new VillagerTradeHandler());
        //VillagerRegistry.instance().registerVillagerId(6);
	}
        
    
	
	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		if (GeneralConfig.addOceanMonuments)
		{
			// Register Guardian stuff here
	    	int entityIDs = 0; // Increment this to make sure everything has its own model register
	    	//EntityRegistry.registerGlobalEntityID(EntityGuardian.class, GeneralConfig.alternateGuardianNamespace ? "Guardian_VN" : "Guardian", EntityRegistry.findGlobalUniqueEntityId(), 0x5A7A6C, 0xE57E3E);
	    	EntityRegistry.registerModEntity(EntityGuardian.class, Reference.MOB_GUARDIAN_VN, entityIDs++, this, 64, 3, true);
			//RenderingRegistry.registerEntityRenderingHandler(EntityGuardian.class, new RenderGuardian());
		}
		
		// register crafting recipes
		Recipes.init();
		
		// Turn off Trapdoor stinky validation yuck
		BlockTrapDoor.disableValidation = true;
		// To prevent torches from melting snow blocks
		Blocks.snow.setTickRandomly(false);
		
		// Renderers
		PROXY.init(event);
		PROXY.registerRender();
		PROXY.registerEvents();
		// Added in v3.1banner
		GameRegistry.registerTileEntity(TileEntityBanner.class, Reference.MOD_ID + ".banner");
		GameRegistry.registerTileEntity(TileEntityWoodSign.class, Reference.MOD_ID + ".sign"); // VillageNames.sign // ganyssurface.wood_sign
		
		// Other mod stuff
        if (Loader.isModLoaded(Reference.ANTIQUE_ATLAS_MODID))
        {
        	MinecraftForge.EVENT_BUS.register(new VillageWatcherAA()); // Antique Atlas map listener
        }
	}
	
	
	// POST-INIT
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		SpawnEggRegistry.addAllSpawnEggs();
	}
	
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
	    // register server commands
		event.registerServerCommand(new CommandName());
		event.registerServerCommand(new CommandBanner()); // Added in v3.1.1
	}

	/**
	 * For streamlining structure registry of component buildings (not village centers)
	 * @param structureClass - The class of the building
	 * @param structureShorthand - Abbreviated name for the structure, loaded into nbt structure files
	 * @param handlerClass - The creation handler class in StructureCreationHandlers
	 */
	public static void registerVillageComponentBuilding(Class structureClass, String structureShorthand, IVillageCreationHandler handlerClass)
	{
        VillagerRegistry.instance().registerVillageCreationHandler(handlerClass);
        MapGenStructureIO.func_143031_a(structureClass, structureShorthand);
	}
}
