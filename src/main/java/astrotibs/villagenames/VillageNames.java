package astrotibs.villagenames;

import java.io.File;

import astrotibs.villagenames.banner.TileEntityBanner;
import astrotibs.villagenames.block.ModBlocksVN;
import astrotibs.villagenames.command.CommandBanner;
import astrotibs.villagenames.command.CommandName;
import astrotibs.villagenames.config.ConfigInit;
import astrotibs.villagenames.config.GeneralConfig;
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
import astrotibs.villagenames.integration.tools.TileEntityWoodSign;
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
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.Reference;
import astrotibs.villagenames.village.MapGenVillageVN;
import astrotibs.villagenames.village.StructureVillageVN;
import astrotibs.villagenames.village.biomestructures.PlainsStructures;
import cpw.mods.fml.common.FMLCommonHandler;
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
import cpw.mods.fml.relauncher.Side;
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
		
		
		
		// Moved down here to make sure config fires first!?
		ModItems.init();
		ModBlocksVN.init();
		
		
		if (GeneralConfig.codexChestLoot) { // Chest loot
			ChestLootHandler.init();
		}
		
		if (GeneralConfig.addOceanMonuments)
		{ // Monuments, Prismarine, Guardians, Sponges
			// Register Prismarine stuff here
			ModBlocksPrismarine.init();
			ModItemsPrismarine.init();
			
			// Register Ocean Monument stuff here
			GameRegistry.registerWorldGenerator(new MonumentGeneratorIWG(), 0);
			MapGenStructureIO.registerStructure(StructureOceanMonument.StartMonument.class, "Monument");
			StructureOceanMonumentPieces.registerOceanMonumentPieces();
						
			// Register Guardian stuff here
			EntityRegistry.registerGlobalEntityID(EntityGuardian.class, GeneralConfig.alternateGuardianNamespace ? "Guardian_VN" : "Guardian", EntityRegistry.findGlobalUniqueEntityId(), 0x5A7A6C, 0xE57E3E);
			//RenderingRegistry.registerEntityRenderingHandler(EntityGuardian.class, new RenderGuardian());
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
		if (GeneralConfig.newVillageGenerator)
		{
			// New village generator
			MapGenStructureIO.registerStructure(MapGenVillageVN.Start.class, "MapGenVillageVN");
			
			// Structure components
	        MapGenStructureIO.func_143031_a(StructureVillageVN.PathVN.class, "VNPath"); // Path
	        MapGenStructureIO.func_143031_a(PlainsStructures.PlainsFountain01.class, "VNPlF01"); // Fountain
	        MapGenStructureIO.func_143031_a(PlainsStructures.PlainsMeetingPoint1.class, "VNPlMP1"); // Well
	        
	        // Listener that interrupts old village generation with the new one
			MinecraftForge.TERRAIN_GEN_BUS.register( new MapGenVillageVN() );
			
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
		
		
		// Reworked in v3.1: new network channel stuff
		
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
	public void load(FMLInitializationEvent event) {
		
		// register crafting recipes
		Recipes.init();
		
		// package registering
		
		// general event handlers
		
		// Renderers
		PROXY.init(event);
		PROXY.registerRender();
		PROXY.registerEvents();
		// Added in v3.1banner
		GameRegistry.registerTileEntity(TileEntityBanner.class, Reference.MOD_ID + ".banner");
		GameRegistry.registerTileEntity(TileEntityWoodSign.class, Reference.MOD_ID + ".sign"); // VillageNames.sign // ganyssurface.wood_sign
		// key handling
		
	}
	
	
	// POST-INIT
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		PROXY.postInit(event);
		// cover your ass here
		// e.g. get list of all blocks added into game from other mods
	}
	
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
	    // register server commands
		event.registerServerCommand(new CommandName());
		event.registerServerCommand(new CommandBanner()); // Added in v3.1.1
	}
	
	/*
	// Way to convert from color meta int into string formatting (for e.g. signs)
	public static String mapColorMetaToStringFormat(int colorMeta) {
		HashMap<Integer, String> signColorToFormat = new HashMap<Integer, String>();//new HashMap();
		// This hashmap translates the town's name color on the sign to a color meta value.
		// This meta should be universal through e.g. wool, clay, etc
		signColorToFormat.put(0, "\u00a7f"); //white
		signColorToFormat.put(1, "\u00a76"); //gold
		signColorToFormat.put(2, "\u00a7d"); //light_purple
		signColorToFormat.put(3, "\u00a79"); //blue
		signColorToFormat.put(4, "\u00a7e"); //yellow
		signColorToFormat.put(5, "\u00a7a"); //green
		signColorToFormat.put(6, "\u00a7c"); //red
		signColorToFormat.put(7, "\u00a78"); //dark_gray
		signColorToFormat.put(8, "\u00a77"); //gray
		//signColorToFormat.put(9, "\u00a7b"); //aqua
		signColorToFormat.put(9, "\u00a73"); //dark_aqua
		signColorToFormat.put(10, "\u00a75"); //dark_purple
		signColorToFormat.put(11, "\u00a71"); //dark_blue
		signColorToFormat.put(12, "\u00a70"); //black
		signColorToFormat.put(13, "\u00a72"); //dark_green
		signColorToFormat.put(14, "\u00a74"); //dark_red
		signColorToFormat.put(15, "\u00a70"); //black
		
		// Return a "town color" string
		return signColorToFormat.get(colorMeta);
	}
	*/
}
