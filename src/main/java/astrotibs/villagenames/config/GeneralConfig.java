package astrotibs.villagenames.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import astrotibs.villagenames.utility.Reference;

public class GeneralConfig {
	public static ConfigurationVN config;
	
	//public static String[] blackList;
	public static boolean wellSlabs;
	public static boolean nameSign;
	public static boolean nameEntities;
	public static String headerTags = "\u00a78\u00a7o";
	public static boolean villagerMakesBook;
	public static boolean villagerDropBook;
	public static boolean villagersOfferCodexTrade;
	public static boolean villagerMakesCodex;
    public static boolean recordStructureCoords; 
	public static boolean addJobToName;
	public static String nitwitProfession;
	
	public static boolean villageBanners;
	public static int signYaw;
	
	public static boolean addLunarinBlocks;
	public static boolean addConcrete;
	public static boolean concreteWell;
	public static boolean addPrismarine;
	public static boolean addSponges;
	public static boolean addOceanMonuments;
	//public static boolean alternateGuardianNamespace;
	public static boolean addIgloos;
	public static boolean biomedictIgloos;
	
	public static boolean codexChestLoot;
	public static boolean versionChecker;
	
	public static boolean wellBoundary;
	public static boolean wellDecorations;
	public static boolean useVillageColors;
	public static boolean debugMessages;
	public static boolean nameGolems;
	
	public static String[] modNameMappingAutomatic;
	public static Map<String, List> modNameMappingAutomatic_map = new HashMap<String, List>();
	public static String[] modNameMappingClickable;
	public static Map<String, List> modNameMappingClickable_map = new HashMap<String, List>();
	public static String[] entitiesNameableLikePets;
	public static Set<String> entitiesNameableLikePets_set = new HashSet<String>();
	
	public static String[] modProfessionMapping;
	public static Map<String, List> modProfessionMapping_map = new HashMap<String, List>();
	
	public static int PMMerchantProfessionMap;
	public static int PMLostMinerProfessionMap;
	
	public static String[] modStructureNames;
	public static Map<String, List> modStructureNames_map = new HashMap<String, List>();
	
	public static boolean allowInGameConfig;
	
	public static boolean modernVillagerSkins;
	public static boolean modernZombieSkins;
	public static boolean moddedVillagerHeadwear;
	public static boolean removeMobArmor;
	
	public static String[] moddedVillagerHeadwearGraylist;
	public static ArrayList<Integer> moddedVillagerHeadwearWhitelist = new ArrayList<Integer>();
	public static ArrayList<Integer> moddedVillagerHeadwearBlacklist = new ArrayList<Integer>();
	
	public static String[] moddedVillagerModularSkins;
	public static Map<String, List> moddedVillagerCareerSkins_map = new HashMap<String, List>();
	public static ArrayList<String> careerAsset_a;
	public static ArrayList<String> zombieCareerAsset_a;
	public static ArrayList<Integer> professionID_a;
	
	public static boolean villagerCareers;
	public static boolean treasureTrades;
	public static boolean LEGACYTRADESFALSE = false; // I don't want to allow these old trades.
	public static boolean modernVillagerTrades;
	public static boolean enableNitwit;
	public static String[] zombieCureCatalysts;
	public static Map<String, List> zombieCureCatalysts_map = new HashMap<String, List>();
	public static String[] zombieCureGroups;
	public static Map<String, List> zombieCureGroups_map = new HashMap<String, List>();
	
	public static boolean pyramidTerracotta;
	
	public static String[] modBamboo;
	public static String[] modBanner;
	public static String[] modBarrel;
	public static String[] modBed;
	public static String[] modBeetroot;
	public static String[] modBookshelf;
	public static String[] modBountifulStone;
	public static String[] modButton;
	public static String[] modCampfire;
	public static String[] modChest;
	public static String[] modComposter;
	public static String[] modConcrete;
	public static String[] modCraftingTable;
	public static String[] modDoor;
	public static String[] modDye;
	public static String[] modFence;
	public static String[] modFenceGate;
	public static String[] modFlower;
	public static String[] modGlazedTerracotta;
	public static String[] modGrassPath;
	public static String[] modIronNugget;
	public static String[] modKelp;
	public static String[] modLantern;
	public static String[] modLectern;
	public static String[] modMossyStone;
	public static String[] modMutton;
	public static String[] modPressurePlate;
	public static String[] modPrismarine;
	public static String[] modRedSandstone;
	public static String[] modSign;
	public static String[] modSmithingTable;
	public static String[] modSmoothSandstone;
	public static String[] modSmoothStone;
	public static String[] modSponge;
	public static String[] modSuspiciousStew;
	public static String[] modStrippedLog;
	public static String[] modSweetBerries;
	public static String[] modWall;
	public static String[] modWoodenTable;
	public static String[] modWoodenTrapdoor;
	

	public static float harvestcraftCropFarmRate;
	public static float dragonQuestCropFarmRate;
	public static boolean antiqueAtlasMarkerNames;
	public static boolean antiqueAtlasPlaceSignPosts;
	public static String antiqueAtlasSignPostLabel;
	public static boolean manaMetalCartographersSellMapFragments;
	
    public static boolean villagerSkinTones;
    public static float villagerSkinToneVarianceAnnealing;
    public static float villagerSkinToneVarianceScale;
	
    
	public static void init(File configFile) 
	{
		if (config == null)
		{
			config = new ConfigurationVN(configFile);
			loadConfiguration();
		}
	}
	
	protected static void loadConfiguration()
	{
		
		// --- General --- //
	    nameSign = config.getBoolean("Name Sign", Reference.CATEGORY_GENERAL, true, "Town centers display their name on one or more signs.");
		wellBoundary = config.getBoolean("Well boundary", Reference.CATEGORY_GENERAL, true, "Whether to surround the well with colored blocks");
	    wellSlabs = config.getBoolean("Well slabs", Reference.CATEGORY_GENERAL, true, "Replace the cobblestone rims of wells with stone slabs, making it easier for players and villagers to escape if they fall in.");
	    villageBanners = config.getBoolean("Village Banner", Reference.CATEGORY_GENERAL, true, "The town banner pattern is displayed at the town center. You must be using Et Futurum or Gany's Surface with banners enabled.");
	    signYaw = config.getInt("Sign Yaw", Reference.CATEGORY_GENERAL, 3, 0, 4, "If Village Banner is enabled: Degree to which well signs and banners should face inward. At 0 they face directly outward away from the well; at 4 they face each other.");
	    
		
		recordStructureCoords = config.getBoolean("Record Structure Coords", Reference.CATEGORY_GENERAL, true, "Books generated by villagers or the Codex record the structure's coordinates.");
	    villagerMakesBook = config.getBoolean("Villager makes book", Reference.CATEGORY_GENERAL, true, "Villagers record the village name, and other info, when you right-click them with a book.");
	    villagerDropBook = config.getBoolean("Villager drops book/codex", Reference.CATEGORY_GENERAL, false, "Village books and Codices are dropped by the villager rather than going directly into your inventory.");
	    villagersOfferCodexTrade = config.getBoolean("Villagers offer Codex trade", Reference.CATEGORY_GENERAL, true, "Librarian and Cartographer villagers can offer to sell you the Codex item via an ordinary trade.");
	    villagerMakesCodex = config.getBoolean("Villager makes codex", Reference.CATEGORY_GENERAL, false, "Librarian villagers will give you a codex if you right-click them while holding emerald, iron ingots, and/or gold ingots.");
		useVillageColors = config.getBoolean("Use village colors", Reference.CATEGORY_GENERAL, true, "Whether to apply the village's colors to concrete, terracotta, carpet, etc.");
		
	    
	    
	    wellDecorations = config.getBoolean("Allow well decorations", Reference.CATEGORY_WELL_KILL_SWITCH, true, "Set this to false to override-disable all well decoration: sign, slabs, terracotta, concrete.");
	    
	    addConcrete = config.getBoolean("1.12 Blocks", Reference.CATEGORY_WORLD_OF_COLOR, true, "Whether to add 1.12 style Concrete, Concrete Powder, and Glazed Terracotta");
	    concreteWell = config.getBoolean("Concrete Well", Reference.CATEGORY_WORLD_OF_COLOR, true, "Whether to decorate wells with Concrete and Glazed Terracotta instead of stained clay. This only applies to old-school wells, i.e. if \"Activate New Village Generator\" is false.");
	    
	    
	    
	    //---------------Professions-----------------//
	    villagerCareers = config.getBoolean("Villager Careers", Reference.CATEGORY_VILLAGER_PROFESSIONS, true, "Assign 1.8+ Career subdivisions to vanilla Professions. Trading is still 1.7 style, but merchant offers will be more progression-based. "
	    		+ "\nWARNING: This will permanently modify already-generated vanilla Villager trade offers.");
	    treasureTrades = config.getBoolean("Treasure Trades", Reference.CATEGORY_VILLAGER_PROFESSIONS, true, "High-level Librarians and Cartographers will offer enchanted books and treasures in exchange for " + Reference.MOD_NAME + " items."
	    		+ " This only applies if Villager Careers is true.");
	    //villagerLegacyTrades = config.getBoolean("Villager Legacy Trades", Reference.CATEGORY_VILLAGER_PROFESSIONS, true, "When using Villager Careers: some trades that were removed in 1.8 can still be unlocked.");
	    
	    modernVillagerSkins = config.getBoolean("Modern Villager Profession Skins", Reference.CATEGORY_VILLAGER_PROFESSIONS, true, "Use the composite 1.14 Villager skins");
	    modernZombieSkins = config.getBoolean("Modern Zombie Profession Skins", Reference.CATEGORY_VILLAGER_PROFESSIONS, true, "Use the composite 1.14 Zombie skins");
	    modernVillagerTrades = config.getBoolean("Modern Villager Trades", Reference.CATEGORY_VILLAGER_PROFESSIONS, true, "Use JE 1.14 / BE 1.12 trade offerings and add the Mason villager");
	    enableNitwit = config.getBoolean("Nitwit Villager", Reference.CATEGORY_VILLAGER_PROFESSIONS, true, "Enable 1.11 NitWit Villagers");
	    
	    moddedVillagerHeadwear = config.getBoolean("Modded Villager Headwear", Reference.CATEGORY_VILLAGER_PROFESSIONS, false, "If modern skins are enabled: renders the headwear layer for non-vanilla villager professions, if one exists.");
	    
	    removeMobArmor = config.getBoolean("Remove Armor for Modern Skins", Reference.CATEGORY_VILLAGER_PROFESSIONS, true, "If modern skins are enabled: automatically removes armor from villagers and zombies to avoid a rendering bug.");
	    
	    moddedVillagerHeadwearGraylist = config.getStringListWithoutDefaultsInComment("Modded Villager Headwear Graylist", Reference.CATEGORY_VILLAGER_PROFESSIONS, new String[]{
				"14", // Growthcraft Apiarist
				"-15", // Apple & Milk & Tea's Cafe Master -- turned off because I enjoy his sexy hair
				"44", // Village Taverns's Shepherdess -- turned on to better differentiate from the vanilla Shepherd
				"80", // Forestry Apiarist
				"-190", // Thaumcraft Wizard -- turned off because of hat brim rendering issues
				"-191", // Thaumcraft Banker -- turned off because of hat brim rendering issues
				"385", // GraveStone Mod's Undertaker
				"512", // Immersive Engineering Engineer
				"-6156", // Open Blocks Music Merchant
				"7117", // Mine Trading Cards's Card Master
				"7766", // Growthcraft Community Edition Apiarist
				"935153", // Enchanting Plus v4 Enchanter
	    		},
	    		"(If modern skins are enabled) List of profession IDs for other mods' villagers. A normal value will be whitelisted: it will display that villager's headwear layer even if Modded Villager Headwear is false. "
	    		+ "Adding a negative sign in front of the ID int will blacklist the profession so that its headwear layer never renders.");
	    
	    // Extract the values and populate the white and black lists
	    for (String prof_s : moddedVillagerHeadwearGraylist)
	    {
	    	try
	    	{
	    		int prof_i = Integer.parseInt(prof_s);
	    		
	    		if (prof_i > 0) {moddedVillagerHeadwearWhitelist.add(prof_i);}
	    		else if (prof_i < 0) {moddedVillagerHeadwearBlacklist.add(Math.abs(prof_i));}
	    	}
	    	catch (Exception e) {} // Failure to parse the string entry into an integer, so ignore it
	    }
	    
	    moddedVillagerModularSkins = config.getStringListWithoutDefaultsInComment("Modded Villager Modular Skins", Reference.CATEGORY_VILLAGER_PROFESSIONS, new String[]{
				// Actually Additions
				"aa_jam|aa_jam|493827",
				// Apple & Milk & Tea
		        "amt_cafemaster||15",
		        "amt_warehousemanager||16",
				// ChocoCraft Plus
				"ccp_stablehand||19940402",
				// Enchanting Plus v4 Enchanter
				"ep_enchanter||935153",
				// Forestry
				"for_apiarist|for_apiarist|80",
				"for_arborist|for_arborist|81",
				// Fossils and Archaeology
				"fa_archaeologist||303",
				// GraveStone Mod
				"gs_undertaker|gs_undertaker|385",
	    		// Growthcraft
	    		"gc_brewer||10",
				"gc_apiarist||14",
				// Growthcraft Community Edition
				"gc_brewer||6677",
				"gc_apiarist||7766",
				// Immersive Engineering
				"ie_engineer||512",
				// Mine Trading Cards
				"mtc_cardmaster||7117",
				"mtc_cardtrader||7118",
		        // More Swords mod version 2
				"msm_swordsmith||66",
				// Musica
				"mus_clerk||52798",
				// Mystcraft
				"myc_archivist||1210950779",
				// Open Blocks
				"ob_musicmerchant||6156",
				// PneumaticCraft
				"pc_mechanic||125",
				// Psychedelicraft
				"psy_dealer||87",
				// Railcraft
				"rc_engineer|rc_engineer|456",
				// Thaumcraft
				"thc_wizard||190",
				"thc_banker||191",
				// Tinkers Construct
				"tc_tinkerer||78943",
				// Traincraft
				"tc_stationchief||86", 
		        // Village Taverns
		        "vt_barwench||42",
		        "vt_hostler||43",
		        "vt_shepherdess||44",
		        "vt_baker||45",
				// Witchery
				"wit_apothecary||2435",
	    		},
	    		"(If modern skins are enabled) List of profession IDs for other mods' villagers to render in the modular skin style. Format is: careerAsset|zombieCareerAsset|professionID\n"+
	    		"careerAsset: career skin png to be overlaid onto the villager, located in assets\\"+Reference.MOD_ID.toLowerCase()+"\\textures\\entity\\villager\\profession\n"+
	    				"The default values are all available in "+Reference.MOD_NAME+". You can access custom values with a resourcepack.\n"
	    						+ "zombieCareerAsset: a zombie career png, located in the corresponding zombie_villager directory. You may leave this value blank, in which case it will use the non-zombie career overlay.\n"
	    						+ "professionID: the ID associated with the mod profession.");
	    
	    // Assign the map now and immediately extract it into arrays for faster lookup
	    moddedVillagerCareerSkins_map.clear();
	    moddedVillagerCareerSkins_map = GeneralConfig.unpackModVillagerSkins(GeneralConfig.moddedVillagerModularSkins);
	    careerAsset_a = (ArrayList<String>)moddedVillagerCareerSkins_map.get("careerAsset");
	    zombieCareerAsset_a = (ArrayList<String>)moddedVillagerCareerSkins_map.get("zombieCareerAsset");
	    professionID_a = (ArrayList<Integer>)moddedVillagerCareerSkins_map.get("professionID");
	    

	    villagerSkinTones = config.getBoolean("Display Skin Tones", Reference.CATEGORY_VILLAGER_SKIN_TONES, true, "Display Gaussian-distributed random skin tones assigned to villagers");
	    villagerSkinToneVarianceAnnealing = config.getFloat("Skin Tone Variance Annealing", Reference.CATEGORY_VILLAGER_SKIN_TONES, 8F/3, 0, Float.MAX_VALUE,
	    		"Statistical variance in skin tone for a population decreases as the number of skin-tone-affecting biome tags increases.\n"
	    		+ "Setting this value to zero eliminates that effect, making skin tone vary equally everywhere (aside from culling to the darkest/lightest tones).\n"
	    		+ "Increasing this value makes skin tone variation less likely in qualifying biomes.");
	    villagerSkinToneVarianceScale = config.getFloat("Skin Tone Variance Scale", Reference.CATEGORY_VILLAGER_SKIN_TONES, 1F, 0, Float.MAX_VALUE,
	    		"Proportionality constant for variance everywhere, irrespective of biome. Set this to zero for absolutely no variation for a given biome.\n"
	    		+ "Skin tones are culled to the darkest and lightest values, so setting this arbitrarily high will result in ONLY the darkest or lightest villagers.\n"
	    		+ "I estimate that the distribution is flattest, and thus population variance is maximized, around a value of about 2.6.");

	    
	    
		
    	//--------------Miscellaneous-----------------//
	    
	    zombieCureCatalysts = config.getStringListWithoutDefaultsInComment("Zombie Cure Catalysts", Reference.CATEGORY_GENERAL, new String[]{
 				"vanilla|net.minecraft.block.BlockBed|tile.bed|-1",
 				"vanilla|net.minecraft.block.BlockPane|tile.fenceIron|-1"
 				},
 				"When performing the ritual to convert a zombie villager into a villager, having these blocks nearby (within a taxicab distance of 4) will speed up the process. "
 				+ "Format is: group|classPath|unlocName|meta\n"
 				+ "group is an arbitrary group name to which the block belongs, referenced in Zombie Cure Groups below.\n"
 				+ "classPath is the mod's address to the entity class.\n"
 				+ "unlocName is the unlocalized name of the block. This is used as an extra discriminator in case class path and meta aren't enough. "
 				+ "You can leave this blank to ignore it.\n"
 				+ "meta is integer meta value of the block. Enter -1 to ignore meta and count all blocks with that class path."
 				);
		// Populate the hashmap
	    zombieCureCatalysts_map.clear();
	    zombieCureCatalysts_map = unpackZombieCureCatalysts(zombieCureCatalysts);
	    
	    zombieCureGroups = config.getStringListWithoutDefaultsInComment("Zombie Cure Groups", Reference.CATEGORY_GENERAL, new String[]{
 				"vanilla|0.3|14"
 				},
 				"When curing a zombie villager, all blocks of the same named group will use these stats. "
 				+ "Format is: group|speedup|limit\n"
 				+ "group is the group name assigned in Zombie Cure Catalysts above.\n"
 				+ "speedup is the per-block percentage point boost in conversion speed. That is: a value of 1.0 increases the conversion by about 1 percentage point per group block found. "
 				+ "negative values will likewise reduce the conversion speed, making conversion take longer.\n"
 				+ "limit is the maximum number of blocks in this group that will apply the group speedup effect."
 				);
		// Populate the hashmap
	    zombieCureGroups_map.clear();
	    zombieCureGroups_map = unpackZombieCureGroups(zombieCureGroups);
 		
 	    versionChecker = config.getBoolean("Version Checker", Reference.CATEGORY_MISCELLANEOUS, true, "Displays a client-side chat message on login if there's an update available.");
	    codexChestLoot = config.getBoolean("Codex Chest Loot", Reference.CATEGORY_MISCELLANEOUS, true, "The Codex can appear as rare chest loot.");
	    allowInGameConfig = config.getBoolean("Allow in-game config access", Reference.CATEGORY_MISCELLANEOUS, true, "Set this to false to deacactivate the in-game config GUI, in case it conflicts with other mods.");
	    debugMessages = config.getBoolean("Debug messages", Reference.CATEGORY_MISCELLANEOUS, false, "Print debug messages to the console, print the class paths of entities and blocks you right-click.");
	    pyramidTerracotta = config.getBoolean("Pyramid Terracotta", Reference.CATEGORY_MISCELLANEOUS, true, "Replace the wool blocks in desert pyramids with terracotta as in 1.8+");
	    addLunarinBlocks = config.getBoolean("Add Lunarin Blocks", Reference.CATEGORY_MISCELLANEOUS, true, "Adds an original brick-style block for iron and gold");
	    addPrismarine = config.getBoolean("Add Prismarine", Reference.CATEGORY_MISCELLANEOUS, true, "Adds Prismarine blocks and items. Very difficult to acquire if \"Add Monuments\" is false.");
	    addSponges = config.getBoolean("Add Sponges", Reference.CATEGORY_MISCELLANEOUS, true, "Adds absorbent Sponges");
	    addOceanMonuments = config.getBoolean("Add Monuments", Reference.CATEGORY_MISCELLANEOUS, true, "Generate Ocean Monuments and Guardians. Pairs well with \"Add Prismarine\" if you're not using another mod to backport prismarine.");
	    //alternateGuardianNamespace = config.getBoolean("Alternate Guardian Namespace", Reference.CATEGORY_MISCELLANEOUS, false, "WARNING: TOGGLING THIS VALUE WILL REMOVE OR REPLACE ALL CURRENTLY-SPAWNED GUARDIANS FROM YOUR WORLD, INCLUDING ELDERS!\n"+"Set this to true if you have fatal conflicts loading another mod using the entity name minecraft:Guardian by instead using the name minecraft:Guardian_VN.");
	    addIgloos = config.getBoolean("Add Igloos", Reference.CATEGORY_MISCELLANEOUS, true, "Generate Igloos from 1.9+");
	    biomedictIgloos = config.getBoolean("Allow Igloos in modded biomes", Reference.CATEGORY_MISCELLANEOUS, false, "Igloos can generate in mods' snowy plains biomes, rather than just vanilla's Ice Plains and Cold Taiga");
	    
	    
	    //--------------Names-----------------//
	    
	    nameEntities = config.getBoolean("Entity names", Reference.CATEGORY_NAMING, true, "Entities reveal their names when you right-click them, or automatically if so assigned.");
	    addJobToName = config.getBoolean("Entity professions", Reference.CATEGORY_NAMING, false, "An entity's name also includes its profession/title. You may need to right-click the entity to update its name plate.");
	    nameGolems = config.getBoolean("Golem names", Reference.CATEGORY_NAMING, true, "Right-click village Golems to learn their name.");
	    
	    nitwitProfession = config.getString("Nitwit Profession", Reference.CATEGORY_NAMING, "", "The career displayed for a Nitwit");
	    
		// Automatic Names
		
		modNameMappingAutomatic = config.getStringListWithoutDefaultsInComment("Automatic Names", Reference.CATEGORY_NAMING, new String[]{
				
				// Minecraft
				//"demon||net.minecraft.entity.boss.EntityWither|add",
				"villager-goblin|Witch|net.minecraft.entity.monster.EntityWitch|add",
				"dragon-angel|Ender Dragon|net.minecraft.entity.boss.EntityDragon|add",
				"villager-demon|Evoker|net.minecraft.entity.monster.EntityEvoker|add",
				"villager-demon|Vindicator|net.minecraft.entity.monster.EntityVindicator|add",
				"villager-demon|Illusioner|net.minecraft.entity.monster.EntityIllusionIllager|add",
				
				// Hardcore Ender Expansion
				"dragon-angel|Ender Dragon|chylex.hee.entity.boss.EntityBossDragon|add",
				"demon|Ender Demon|chylex.hee.entity.boss.EntityBossEnderDemon|add",
				
				// Et Futurum
				"dragon-angel|Ender Dragon|ganymedes01.etfuturum.entities.EntityRespawnedDragon|add",
				
				// Village Names
				"alien-golem|Elder Guardian|"+Reference.ELDER_GUARDIAN_CLASS+"|add", // NOT AN ACTUAL CLASSPATH: hard-coded in EntityInteractHandler.java
				
				
				// Galacticraft
				"alien-demon|Evolved Skeleton Boss|micdoodle8.mods.galacticraft.core.entities.EntitySkeletonBoss|add", // 1.7 and 1.10
				"alien-golem|Evolved Creeper Boss|micdoodle8.mods.galacticraft.planets.mars.entities.EntityCreeperBoss|add", // 1.7 and 1.10
				"alien-goblin||micdoodle8.mods.galacticraft.planets.venus.entities.EntitySpiderQueen|add", // 1.10
				
				// More Planets
				"alien-goblin|Evolved Witch|stevekung.mods.moreplanets.core.entities.EntityEvolvedWitch|add", // 1.7
				// Bosses
				"alien-golem|Diona Creeper Boss|stevekung.mods.moreplanets.planets.diona.entities.EntityDionaCreeperBoss|add", // 1.7
				"alien-golem|Fronos Creeper Boss|stevekung.mods.moreplanets.planets.fronos.entities.EntityFronosCreeperBossTemp|add", // 1.7
				"alien-golem|Kapteyn B Creeper Boss|stevekung.mods.moreplanets.planets.kapteynb.entities.EntityKapteynBCreeperBoss|add", // 1.7
				"alien-goblin|Evolved Infected Spider Boss|stevekung.mods.moreplanets.planets.nibiru.entities.EntityEvolvedInfectedSpiderBoss|add", // 1.7
				"alien-golem|Pluto Creeper Boss|stevekung.mods.moreplanets.planets.pluto.entities.EntityPlutoCreeperBoss|add", // 1.7
				"alien-angel|Cheese Cube Boss|stevekung.mods.moreplanets.planets.polongnius.entities.EntityCheeseCubeEyeBoss|add", // 1.7
				"alien-demon-golem|Evolved Sirius Blaze Boss|stevekung.mods.moreplanets.planets.siriusb.entities.EntityEvolvedSiriusBlazeBoss|add", // 1.7
				
				"alien-angel||stevekung.mods.moreplanets.module.planets.chalos.entity.EntityCheeseCubeEyeBoss|add", // 1.10
				
				// Galaxy Space
				"alien-demon-angel|Evolved Boss Ghast|galaxyspace.galaxies.milkyway.SolarSystem.moons.io.entities.EntityBossGhast|add",
				"alien-demon-golem|Evolved Boss Blaze|galaxyspace.galaxies.milkyway.SolarSystem.planets.ceres.entities.EntityBossBlaze|add",
				
				// Amun-Ra
				"alien-demon-golem|Undead Pharaoh|de.katzenpapst.amunra.mob.entity.EntityMummyBoss|add",
				
				// Primitive Mobs
				"villager|Summoner|net.daveyx0.primitivemobs.entity.monster.EntityDSummoner|add",
				
				// Special Mobs
				"villager-goblin|Witch of Domination|toast.specialMobs.entity.witch.EntityDominationWitch|add",
				"villager-goblin|Witch of Rage|toast.specialMobs.entity.witch.EntityRageWitch|add",
				"villager-goblin|Witch of Shadows|toast.specialMobs.entity.witch.EntityShadowsWitch|add",
				"villager-goblin|Witch of the Undead|toast.specialMobs.entity.witch.EntityUndeadWitch|add",
				"villager-goblin|Witch of the Wilds|toast.specialMobs.entity.witch.EntityWildsWitch|add",
				"villager-goblin|Witch of the Wind|toast.specialMobs.entity.witch.EntityWindWitch|add",
				
				// Twilight Forest
				"villager-golem||twilightforest.entity.EntityTFArmoredGiant|add",
				"villager-golem||twilightforest.entity.EntityTFGiantMiner|add",
				//Bosses
				"dragon|Naga|twilightforest.entity.boss.EntityTFNaga|add",
				"dragon|Hydra|twilightforest.entity.boss.EntityTFHydra|add",
				"demon-golem|Knight Phantom|twilightforest.entity.boss.EntityTFKnightPhantom|add",
				"demon|Twilight Lich|twilightforest.entity.boss.EntityTFLich|add",
				"goblin|Minoshroom|twilightforest.entity.boss.EntityTFMinoshroom|add",
				"angel|Snow Queen|twilightforest.entity.boss.EntityTFSnowQueen|add",
				"demon-angel|Ur-ghast|twilightforest.entity.boss.EntityTFUrGhast|add",
				"goblin-golem|Alpha Yeti|twilightforest.entity.boss.EntityTFYetiAlpha|add",
				
				// Thaumcraft
				
				
				// Witchery
				"villager-demon||com.emoniph.witchery.entity.EntityVampire|add",
				"villager|Witch Hunter|com.emoniph.witchery.entity.EntityWitchHunter|add",
				"demon|Horned Huntsman|com.emoniph.witchery.entity.EntityHornedHuntsman|add",
				
				},
				"List of entities that will generate a name automatically when they appear. Useful for aggressive or boss mobs.\n"
				+ "Format is: nameType|profession|classPath|addOrRemove\n"
				+ "nameType is the name pool for the entity, or a hyphenated series of pools like \"angel-golem\".\n"
				+ "profession is displayed if that config flag is enabled. It can be left blank for no profession.\n"
				+ "classPath is the mod's address to the entity class.\n"
								+ "nameType options:\n"
								+ "villager, dragon, golem, alien, angel, demon, goblin, pet, custom\n"
				+ "addOrRemove - type \"add\" to automatically add names tags to ALL COPIES of this entity upon spawning, or \"remove\" to automatically remove.\n"
				+ "Be VERY CAUTIOUS about what entities you choose to add to this list!"
								);
		// Populate the hashmap
		modNameMappingAutomatic_map.clear();
		modNameMappingAutomatic_map = unpackMappedNames(modNameMappingAutomatic);
	    

		// Clickable Names
	    
		modNameMappingClickable = config.getStringListWithoutDefaultsInComment("Clickable Names", Reference.CATEGORY_NAMING, new String[]{
				
				// Galacticraft
				"alien||micdoodle8.mods.galacticraft.core.entities.EntityAlienVillager",
				
				// More Planets
				"alien||stevekung.mods.moreplanets.moons.koentus.entities.EntityKoentusianVillager", // 1.7
				"alien||stevekung.mods.moreplanets.module.moons.koentus.entities.EntityKoentusianVillager", // 1.10
				"alien-villager-goblin||stevekung.mods.moreplanets.planets.fronos.entities.EntityFronosVillager", // 1.7
				"alien-villager-goblin||stevekung.mods.moreplanets.module.planets.fronos.entities.EntityFronosVillager", // 1.10
				"alien-villager-angel||stevekung.mods.moreplanets.planets.nibiru.entity.EntityNibiruVillager", // 1.7
				"alien-villager-angel||stevekung.mods.moreplanets.module.planets.nibiru.entity.EntityNibiruVillager", // 1.10
				
				// Amun-Ra
		        "alien-goblin||de.katzenpapst.amunra.mob.entity.EntityARVillager",
		        "alien-villager-golem||de.katzenpapst.amunra.mob.entity.EntityRobotVillager",
				
				// Natura
				"goblin-demon||mods.natura.entity.ImpEntity",
				
				// Thaumcraft
				"goblin||thaumcraft.common.entities.monster.EntityPech",
				
				// Twilight Forest
				"angel-golem-goblin|Questing Ram|twilightforest.entity.passive.EntityTFQuestRam",
				
				// Witchery
				"villager|Guard|com.emoniph.witchery.entity.EntityVillageGuard",
				"goblin||com.emoniph.witchery.entity.EntityGoblin",
				"goblin-demon||com.emoniph.witchery.entity.EntityImp",
				"demon||com.emoniph.witchery.entity.EntityDemon",
				
				// Primitive Mobs
				"villager|Traveling Merchant|net.daveyx0.primitivemobs.entity.passive.EntityTravelingMerchant",
				"villager|Miner|net.daveyx0.primitivemobs.entity.passive.EntityLostMiner",
				"villager||net.daveyx0.primitivemobs.entity.passive.EntitySheepman",
				"villager|Blacksmith|net.daveyx0.primitivemobs.entity.passive.EntitySheepmanSmith",
				
				// Improving Minecraft
				"villager-goblin||imc.entities.EntityPigman",
				
				// Netherlicious
				"villager-goblin||DelirusCrux.Netherlicious.Common.Entities.Passive.EntityPiglin",
				
				// DQ Respect
				"villager|MP Exchanger|dqr.entity.npcEntity.npc.DqmEntityNPCBankMP",
				"villager|Banker|dqr.entity.npcEntity.npc.DqmEntityNPCBank",
				"villager|Equipment Merchant|dqr.entity.npcEntity.npc.DqmEntityNPCBukiya",
				"villager|Soldier|dqr.entity.npcEntity.npc.DqmEntityNPCGuntai",
				"villager|Equipment Buyer|dqr.entity.npcEntity.npc.DqmEntityNPCKaitoriya",
				"villager|Blackjack Dealer|dqr.entity.npcEntity.npc.DqmEntityNPCKajinoBJ",
				"villager|Chinchirorin Dealer|dqr.entity.npcEntity.npc.DqmEntityNPCKajinoCCR",
				"villager|Token Exchange|dqr.entity.npcEntity.npc.DqmEntityNPCKajinoCoin",
				"villager|Prize Exchange|dqr.entity.npcEntity.npc.DqmEntityNPCKajinoEXC",
				"villager|Poker Dealer|dqr.entity.npcEntity.npc.DqmEntityNPCKajinoPK",
				"villager|Medal King|dqr.entity.npcEntity.npc.DqmEntityNPCMedalking",
				"villager|Job Priest|dqr.entity.npcEntity.npc.DqmEntityNPCSinkan",
				"villager|Skill Priest|dqr.entity.npcEntity.npc.DqmEntityNPCSinkan2",
				"villager|Weapon Priest|dqr.entity.npcEntity.npc.DqmEntityNPCSinkan3",
				"villager|Repair Smith|dqr.entity.npcEntity.npc.DqmEntityNPCSyuuri",
				
				},
				"List of entities that can generate a name when right-clicked. Format is: nameType|profession|classPath\n"
				+ "nameType is the name pool for the entity, or a hyphenated series of pools like \"angel-golem\".\n"
				+ "profession is displayed if that config flag is enabled. It can be left blank for no profession.\n"
				+ "classPath is mod's address to the entity class.\n"
								+ "nameType options:\n"
								+ "villager, dragon, golem, alien, angel, demon, goblin, pet, custom\n"
								);
		// Populate the hashmap
		modNameMappingClickable_map.clear();
		modNameMappingClickable_map = unpackMappedNames(modNameMappingClickable);

		
		// Forced pet names
		entitiesNameableLikePets = config.getStringListWithoutDefaultsInComment("Entities Nameable Like Pets", Reference.CATEGORY_NAMING, new String[]{
				},
				"List of class paths of entities that receive a random Pet name when right-clicked with a blank nametag, irrespective of if they're tamed or who tamed them.\n"
				+ "Use this for entities that can't receive a Pet name in the intended way (typically because owner ID is stored differently or not stored at all)."
								);
		// Populate hash set
		entitiesNameableLikePets_set.clear();
		for (int i=0; i< entitiesNameableLikePets.length; i++) {entitiesNameableLikePets_set.add(entitiesNameableLikePets[i]);}
		
		
		
		//--------------Mod Integration-----------------//
		
		harvestcraftCropFarmRate = config.getFloat("Crop rate: Harvestcraft", Reference.CATEGORY_MOD_INTEGRATION, 0.25F, 0F, 1F, "Generate Harvestcraft crops in farms. Only used with Village Generator. Set to 0 for no HC crops.");
		dragonQuestCropFarmRate = config.getFloat("Crop rate: DQ Respect", Reference.CATEGORY_MOD_INTEGRATION, 0.25F, 0F, 1F, "Generate Dragon Quest Respect crops in farms. Only used with Village Generator. Set to 0 for no DQR crops.");
		antiqueAtlasMarkerNames = config.getBoolean("Antique Atlas: Village Marker Names", Reference.CATEGORY_MOD_INTEGRATION, true, "Label a new village marker with the village's name in your Antique Atlases.");
		antiqueAtlasPlaceSignPosts = config.getBoolean("Antique Atlas: Place Sign-Posts", Reference.CATEGORY_MOD_INTEGRATION, true, "If using harceroi's SignPosts mod, new villages will generate with a registered Sign-Post somewhere");
		antiqueAtlasSignPostLabel = config.getString("Antique Atlas: Sign-Post Label", Reference.CATEGORY_MOD_INTEGRATION, "", "Text that will be displayed on every Antique Atlas village SignPost marker. Leave blank for the village name.");
		manaMetalCartographersSellMapFragments = config.getBoolean("ManaMetal: Cartographers Sell Map Fragments", Reference.CATEGORY_MOD_INTEGRATION, true, "Cartographer villagers can sell Map Fragments");
		
		
		modBamboo = config.getStringListWithoutDefaultsInComment("Mod Priority: Bamboo", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
				"manametal",
 				"growthcraft",
 				"biomesoplenty",
 				},
 				"Priority order for referencing Bamboo for village generation. The version highest on the list and registered in your game will be used."
 				);
		
		modBanner = config.getStringListWithoutDefaultsInComment("Mod Priority: Banner", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
 				"ganyssurface",
 				},
 				"Priority order for referencing Banners for e.g. villager trade offers. The version highest on the list and registered in your game will be used."
 				);
		
		modBarrel = config.getStringListWithoutDefaultsInComment("Mod Priority: Barrel", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
 				"uptodate",
 				},
 				"Priority order for referencing Barrels for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modBed = config.getStringListWithoutDefaultsInComment("Mod Priority: Bed", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"etfuturum",
	    		"manametal",
 				"bettervanilla",
 				"carpentersblocks",
 				},
 				"Priority order for referencing Beds for village generation and villager trade offers. The version highest on the list and registered in your game will be used."
 				);
	    
	    modBeetroot = config.getStringListWithoutDefaultsInComment("Mod Priority: Beetroot", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
 				"ganyssurface",
 				},
 				"Priority order for referencing Beetroot for e.g. villager trade offers. The version highest on the list and registered in your game will be used."
 				);
	    
	    modBookshelf = config.getStringListWithoutDefaultsInComment("Mod Priority: Bookshelf", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"woodstuff",
 				"ganyssurface",
 				"cement",
 				},
 				"Priority order for referencing Bookshelves for village generation. The version highest on the list and registered in your game will be used."
 				);
		
	    modBountifulStone = config.getStringListWithoutDefaultsInComment("Mod Priority: Bountiful Stone", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"chisel",
 				"etfuturum",
	    		"uptodate",
 				"ganyssurface",
 				"botania",
 				"manametal",
 				},
 				"Priority order for referencing Granite, Diorite, and Andesite for e.g. villager trade offers. The version highest on the list and registered in your game will be used."
 				);
	    
		modButton = config.getStringListWithoutDefaultsInComment("Mod Priority: Button", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"etfuturum",
	    		"uptodate",
	    		"woodstuff",
 				"ganyssurface",
 				},
 				"Priority order for referencing wood buttons for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modCampfire = config.getStringListWithoutDefaultsInComment("Mod Priority: Campfire", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"campfirebackport",
 				"manametal",
 				},
 				"Priority order for referencing Campfires for village generation and villager trade offers. The version highest on the list and registered in your game will be used."
 				);
	    
	    modChest = config.getStringListWithoutDefaultsInComment("Mod Priority: Chest", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"woodstuff",
 				"ganyssurface",
 				},
 				"Priority order for referencing Wooden Chests for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modComposter = config.getStringListWithoutDefaultsInComment("Mod Priority: Composter", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
 				"gardenstuff",
 				},
 				"Priority order for referencing composters for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modConcrete = config.getStringListWithoutDefaultsInComment("Mod Priority: Concrete", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"villagenames",
 				"etfuturum",
	    		"uptodate",
 				"manametal",
 				},
 				"Priority order for referencing concrete for well decorations; essentially, if you still want these features but want to disable "+ Reference.MOD_NAME+"\'s versions. The version highest on the list and registered in your game will be used."
 				);
	    
	    modCraftingTable = config.getStringListWithoutDefaultsInComment("Mod Priority: Crafting Table", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"woodstuff",
 				"cement",
 				},
 				"Priority order for referencing crafting tables for village decorations. The version highest on the list and registered in your game will be used."
 				);
	    
	    modDoor = config.getStringListWithoutDefaultsInComment("Mod Priority: Door", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"etfuturum",
	    		"uptodate",
 				"ganyssurface",
 				"malisisdoors",
 				"manametal",
 				},
 				"Priority order for referencing Doors for village generation. The version highest on the list and registered in your game will be used."
 				);
		
	    modDye = config.getStringListWithoutDefaultsInComment("Mod Priority: Dye", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"etfuturum",
	    		"uptodate",
 				"biomesoplenty",
 				"mariculture",
 				},
 				"Priority order for referencing dye for villager trade offers. The version highest on the list and registered in your game will be used."
 				);
	    
	    modFence = config.getStringListWithoutDefaultsInComment("Mod Priority: Fence", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
	    		"uptodate",
	    		"woodstuff",
 				"ganyssurface",
 				"manametal",
 				},
 				"Priority order for referencing Fence blocks for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modFenceGate = config.getStringListWithoutDefaultsInComment("Mod Priority: Fence Gate", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
 				"ganyssurface",
	    		"uptodate",
	    		"woodstuff",
	    		"manametal",
	    		"malisisdoors",
 				},
 				"Priority order for referencing Fence Gate blocks for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modFlower = config.getStringListWithoutDefaultsInComment("Mod Priority: Flower", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
	    		"uptodate",
 				},
 				"Priority order for referencing flowers for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modGlazedTerracotta = config.getStringListWithoutDefaultsInComment("Mod Priority: Glazed Terracotta", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"villagenames",
 				"etfuturum",
	    		"uptodate",
	    		"manametal",
 				},
 				"Priority order for referencing Glazed Terracotta for villager trade offers and well decorations; essentially, if you still want these features but want to disable "+ Reference.MOD_NAME+"\'s versions. The version highest on the list and registered in your game will be used."
 				);
		
	    modGrassPath = config.getStringListWithoutDefaultsInComment("Mod Priority: Grass Path", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
 				"gregtech",
	    		"uptodate",
	    		"manametal",
 				},
 				"Priority order for referencing Grass Path blocks for village generation. The version highest on the list and registered in your game will be used."
 				);
		
	    modIronNugget = config.getStringListWithoutDefaultsInComment("Mod Priority: Iron Nugget", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"etfuturum",
	    		"uptodate",
 				"tinkersconstruct",
 				"thaumcraft",
 				"immersiveengineering",
 				"thermalfoundation",
 				"railcraft",
 				"mariculture",
 				"netherlicious",
 				"ganysnether",
 				"manametal",
 				},
 				"Priority order for referencing Iron Nuggets for e.g. village chest loot. The version highest on the list and registered in your game will be used."
 				);
	    
	    modKelp = config.getStringListWithoutDefaultsInComment("Mod Priority: Kelp", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"mariculture",
	    		"biomesoplenty",
 				},
 				"Priority order for referencing Kelp for e.g. villager trade offers. The version highest on the list and registered in your game will be used."
 				);
		
	    modLantern = config.getStringListWithoutDefaultsInComment("Mod Priority: Lantern", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
 				"netherlicious",
 				"uptodate",
	    		"enviromine",
	    		"manametal",
 				},
 				"Priority order for referencing Lanterns for e.g. village generation and villager trade offers. The version highest on the list and registered in your game will be used."
 				);
	    
	    modLectern = config.getStringListWithoutDefaultsInComment("Mod Priority: Lectern", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"manametal",
	    		"bibliocraft",
 				},
 				"Priority order for referencing Lecterns for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modMossyStone = config.getStringListWithoutDefaultsInComment("Mod Priority: Mossy Stone", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
	    		"uptodate",
 				},
 				"Priority order for referencing mossy stone blocks for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modMutton = config.getStringListWithoutDefaultsInComment("Mod Priority: Mutton", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
	    		"uptodate",
 				"ganyssurface",
 				"harvestcraft",
 				},
 				"Priority order for referencing Mutton for e.g. villager trade offers. The version highest on the list and registered in your game will be used."
 				);
	    
	    modPressurePlate = config.getStringListWithoutDefaultsInComment("Mod Priority: Pressure Plate", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
	    		"uptodate",
	    		"woodstuff",
 				"ganyssurface",
 				},
 				"Priority order for referencing Fence blocks for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modPrismarine = config.getStringListWithoutDefaultsInComment("Mod Priority: Prismarine", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"villagenames",
 				"etfuturum",
 				"botania",
	    		"uptodate",
	    		"manametal",
 				},
 				"Priority order for referencing Prismarine blocks and items for monument and village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modRedSandstone = config.getStringListWithoutDefaultsInComment("Mod Priority: Red Sandstone", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"etfuturum",
	    		"uptodate",
 				"ganyssurface",
 				},
 				"Priority order for referencing Red Sandstone and its variants for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modSign = config.getStringListWithoutDefaultsInComment("Mod Priority: Sign", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
 				"ganyssurface",
 				},
 				"Priority order for referencing Signs for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modSmoothStone = config.getStringListWithoutDefaultsInComment("Mod Priority: Smooth Stone", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
	    		"uptodate",
	    		"manametal",
 				},
 				"Priority order for referencing Smooth Stone for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modSmithingTable = config.getStringListWithoutDefaultsInComment("Mod Priority: Smithing Table", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
 				"smithinginthe90s",
 				"netheriteplus",
	    		"manametal",
 				},
 				"Priority order for referencing Smithing Table for tool smithy buildings. The version highest on the list and registered in your game will be used."
 				);
	    
	    modSmoothSandstone = config.getStringListWithoutDefaultsInComment("Mod Priority: Smooth Sandstone", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
	    		"uptodate",
 				},
 				"Priority order for referencing Smooth Sandstone for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modSponge = config.getStringListWithoutDefaultsInComment("Mod Priority: Sponge", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"villagenames",
 				"etfuturum",
	    		"uptodate",
 				},
 				"Priority order for referencing Sponge blocks for monument generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modStrippedLog = config.getStringListWithoutDefaultsInComment("Mod Priority: Stripped Log", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
	    		"uptodate",
	    		"manametal",
 				},
 				"Priority order for referencing Stripped Logs/Wood for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modSuspiciousStew = config.getStringListWithoutDefaultsInComment("Mod Priority: Suspicious Stew", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
	    		"uptodate",
 				},
 				"Priority order for referencing Suspicious Stew for villager trades. The version highest on the list and registered in your game will be used."
 				);

	    modSweetBerries = config.getStringListWithoutDefaultsInComment("Mod Priority: Sweet Berries", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
	    		"uptodate",
	    		"manametal",
 				},
 				"Priority order for referencing sweet berries for villager trade offers. The version highest on the list and registered in your game will be used."
 				);
	    
	    modWall = config.getStringListWithoutDefaultsInComment("Mod Priority: Wall", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"etfuturum",
	    		"uptodate",
 				"railcraft",
 				},
 				"Priority order for referencing walls for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modWoodenTable = config.getStringListWithoutDefaultsInComment("Mod Priority: Table", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"minecraft",
	    		"bibliocraft",
	    		"mrcrayfishsfurnituremod",
 				},
 				"Priority order for referencing Wooden Tables for village generation. The version highest on the list and registered in your game will be used. "
 				+ "The \"minecraft\" entry refers to the vanilla-style pressure plate atop a fence post."
 				);
	    
	    modWoodenTrapdoor = config.getStringListWithoutDefaultsInComment("Mod Priority: Trapdoor", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"etfuturum",
	    		"uptodate",
 				"ganyssurface",
 				},
 				"Priority order for referencing Wooden Trapdoors for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    
		// Mapping for modded structures, and the creatures that can name them
		modStructureNames = config.getStringListWithoutDefaultsInComment("Mod Structures", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
				
				// Galacticraft
				"alienvillage|MoonVillage|Moon Village|Moon|moonvillage|micdoodle8.mods.galacticraft.core.entities.EntityAlienVillager",
				"alienvillage|GC_AbandonedBase|Abandoned Base|Asteroid Belt|abandonedbase|", // 1.10
				
				// More Planets
				"alienvillage|FronosVillage|Fronos Village|Fronos|fronosvillage|stevekung.mods.moreplanets.planets.fronos.entities.EntityFronosVillager",
				"alienvillage|FronosVillage|Fronos Village|Fronos|fronosvillage|stevekung.mods.moreplanets.module.planets.fronos.entities.EntityFronosVillager",
				"alienvillage|KoentusVillage|Koentus Village|Koentus|koentusvillage|stevekung.mods.moreplanets.moons.koentus.entities.EntityKoentusianVillager",
				"alienvillage|KoentusVillage|Koentus Village|Koentus|koentusvillage|stevekung.mods.moreplanets.module.moons.koentus.entities.EntityKoentusianVillager",
				"alienvillage|NibiruVillage|Nibiru Village|Nibiru|nibiruvillage|stevekung.mods.moreplanets.planets.nibiru.entity.EntityNibiruVillager",
				"alienvillage|NibiruVillage|Nibiru Village|Nibiru|nibiruvillage|stevekung.mods.moreplanets.module.planets.nibiru.entity.EntityNibiruVillager",
				
				// Hardcore Ender Expansion
				"endcity|hardcoreenderdragon_EndTower|Dungeon Tower|The End|endcity|",
				"endcity|hardcoreenderdragon_EndIsland|Laboratory|The End|endcity|"
				},
				"List of mod structures that can be named with a Codex, or by right-clicking an entity in that structure (optional). "
				+ "Structures must have been generated in a manner similarly to vanilla (e.g. Galacticraft Moon Villages).\n"
				+ "Format is: nameType|structureType|structureTitle|dimensionName|bookType|entityClassPath\n"
				+ "nameType is your choice of name pool for the structure. Options: village, mineshaft, temple, stronghold, fortress, monument, endcity, mansion, alienvillage\n"
				+ "structureType how the mod saves the structure info--e.g. dimension/data/[structureType].dat\n"
				+ "structureTitle is the string type of the structure (e.g. \"Moon Village\"), which will be recorded into a book. It can be left blank.\n"
				+ "dimensionName is the name of the dimension that would be recorded into the book. It can be left blank.\n"
				+ "bookType is the kind of book that is generated. Options: village, mineshaft, temple, jungletemple, desertpyramid, swamphut, igloo, "
				+ "stronghold, fortress, monument, endcity, mansion, moonvillage, koentusvillage, fronosvillage, nibiruvillage, abandonedbase\n"
				+ "entityClassPath is the mod's address to the entity class that will generate this book (when inside the structure). "
					+ "It can be left blank, wherein the structure name can only be obtained via a Codex.\n");
		// Populate the hashmap
		modStructureNames_map.clear();
		modStructureNames_map = unpackModStructures(modStructureNames);
		
		// New mod profession mapping
		modProfessionMapping = config.getStringListWithoutDefaultsInComment("Mod Professions", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
				// Actually Additions
				"Jam Guy|493827|0",
				// Apple & Milk & Tea
		        "Cafe Master|15|0",
		        "Warehouse Manager|16|0",
				// ChocoCraft Plus
				"Stablehand|19940402|0",
				// Enchanting Plus v4
				"Enchanter|935153|2",
				// Forestry
				"Apiarist|80|4",
				"Arborist|81|0",
				// Fossils and Archaeology
				"Archaeologist|303|2",
				// GraveStone Mod
				"Undertaker|385|4",
				// Growthcraft
				"Brewer|10|0",
				"Apiarist|14|4",
				// Growthcraft Community Edition
				"Brewer|6677|0",
				"Apiarist|7766|4",
				// Immersive Engineering
				"Engineer|512|0",
				// Mine Trading Cards
				"Card Master|7117|2",
				"Card Trader|7118|0",
				// More Swords mod version 2
				"Swordsmith|66|5",
				// Musica
				"Clerk|52798|0",
				// Mystcraft
				"Archivist|1210950779|1",
				// Open Blocks
				"Music Merchant|6156|5",
				// PneumaticCraft
				"Mechanic|125|3",
				// Psychedelicraft
				"Dealer|87|0",
				// Railcraft
				"Engineer|456|3",
				// Thaumcraft
				"Wizard|190|2",
				"Banker|191|0",
				// Tinkers Construct
				"Tinkerer|78943|5",
				// Traincraft
				"Station Chief|86|0",
		        // Village Taverns
		        "Bar Wench|42|0",
		        "Hostler|43|0",
		        "Shepherd|44|0",
		        "Baker|45|0",
				// Witchery
				"Apothecary|2435|2",
				},
				"List of professions for other mods' villagers. Format is: Name|ID|pageType\n"
				+ "Name is your choice of name for the profession.\n"
				+ "ID is the ID associated with the mod profession.\n"
				+ "pageType is the vanilla archetype the villager emulates in order to generate hint pages.\n"
								+ "Use this reference:\n"
								+ "-1=None\n"
								+ "0=Farmer: finds Villages\n"
								+ "1=Librarian: finds Strongholds or Mansions\n"
								+ "2=Priest: finds Temples\n"
								+ "3=Blacksmith: finds Mineshafts\n"
								+ "4=Butcher: finds Temples or Villages\n"
								+ "5=Nitwit: finds any structure\n");
		// Populate the hashmap
		modProfessionMapping_map.clear();
		modProfessionMapping_map = unpackMappedProfessions(modProfessionMapping);
		
				
		
		// Primitive Mobs villager mapping
	    //PMMerchantProfession = config.getString("PMMerchantProfession", "Mapping Professions", "Merchant", "The career displayed for Primitive Mobs's Traveling Merchant. Blank this out to display no profession regardless of addJobToName.");
	    PMMerchantProfessionMap = config.getInt("PM Traveling Merchant Profession ID", Reference.CATEGORY_MOD_INTEGRATION, 0, 0, 5,
	    		"Which vanilla archetype the traveling merchant emulates in order to generate hint pages.\n"
				+ "Use this reference:\n"
				+ "-1=None\n"
				+ "0=Farmer\n"
				+ "1=Librarian\n"
				+ "2=Priest\n"
				+ "3=Blacksmith\n"
				+ "4=Butcher\n"
				+ "5=Nitwit\n");
		
	    //PMLostMinerProfession = config.getString("PMLostMinerProfession", "Mapping Professions", "Miner", "The career displayed for Primitive Mobs's Lost Miner. Blank this out to display no profession regardless of addJobToName.");
	    PMLostMinerProfessionMap = config.getInt("PM Lost Miner Profession ID", Reference.CATEGORY_MOD_INTEGRATION, 3, 0, 5,
	    		"Which vanilla archetype the lost miner emulates in order to generate hint pages.\n"
				+ "Use this reference:\n"
				+ "-1=None\n"
				+ "0=Farmer\n"
				+ "1=Librarian\n"
				+ "2=Priest\n"
				+ "3=Blacksmith\n"
				+ "4=Butcher\n"
				+ "5=Nitwit\n");
	    
	    
	    if (config.hasChanged()) config.save();
		
	}
	
	
	/**
	 * Inputs a (Profession|ID|vanillaType) String list and breaks it into three array lists
	 */
	public static Map<String, List> unpackMappedProfessions(String[] inputList) {
		List<String>  otherModProfessions = new ArrayList<String>();
		List<Integer> otherModIDs = new ArrayList<Integer>();
		List<Integer> vanillaProfMaps = new ArrayList<Integer>();
		
		for (String entry : inputList) {
			entry.replaceAll("/", ""); // Forward slashses don't need to be escaped
			entry.replaceAll("\\\\", ""); // \ is BOTH String and regex; needs to be double-escaped. See https://stackoverflow.com/questions/1701839/string-replaceall-single-backslashes-with-double-backslashes
			entry.replaceAll("..", "");
			// Split by pipe
			String[] splitEntry = entry.split("\\|");
			
			// Initialize temp fields
			String otherModProfession="";
			int otherModID=-1;
			int vanillaProfMap=-1;
			
			// Place entries into variables
			try {otherModProfession = splitEntry[0].trim();}               catch (Exception e) {otherModProfession="";}
			try {otherModID = Integer.parseInt(splitEntry[1].trim());}                       catch (Exception e) {otherModID=-1;}
			try {vanillaProfMap = Integer.parseInt(splitEntry[2].trim());} catch (Exception e) {vanillaProfMap=-1;}
			
			if( !otherModProfession.equals("") && otherModID!=-1 ) {
				otherModProfessions.add(otherModProfession);
				otherModIDs.add(otherModID);
				vanillaProfMaps.add(vanillaProfMap);
			}
		}
		
		Map<String, List> map = new HashMap();
		map.put("Professions",otherModProfessions);
		map.put("IDs",otherModIDs);
		map.put("VanillaProfMaps",vanillaProfMaps);
		
		return map;
	}
	
	

	/**
	 * Loads the (nameType|structureType|structureTitle|dimensionName|bookType|entityClassPath) string lists from othermods.cfg > Mod Structures
	 * and assigns them to this instance's variables.
	 */
	public static Map<String, List> unpackModStructures(String[] inputList) {
		
		List<String> otherModNameTypes = new ArrayList<String>();
		List<String> otherModStructureTypes = new ArrayList<String>();
		List<String> otherModStructureTitles = new ArrayList<String>();
		List<String> otherModDimensionNames = new ArrayList<String>();
		List<String> otherModBookTypes = new ArrayList<String>();
		List<String> otherModClassPaths = new ArrayList<String>();
		
		for (String entry : inputList) {
			// Remove parentheses
			entry.replaceAll("\\)", "");
			entry.replaceAll("\\(", "");
			// Split by pipe
			String[] splitEntry = entry.split("\\|");
			
			// Initialize temp fields
			String otherModNameType="";
			String otherModStructureType="FAILSAFE";
			String otherModStructureTitle="";
			String otherModDimensionName="";
			String otherModBookType="";
			String otherModClassPath="";
			
			// Place entries into variables
			try {otherModNameType = splitEntry[0].trim();}       catch (Exception e) {otherModNameType="";}
			try {otherModStructureType = splitEntry[1].trim();}  catch (Exception e) {otherModStructureType="FAILSAFE";}
			try {otherModStructureTitle = splitEntry[2].trim();} catch (Exception e) {otherModStructureTitle="";}
			try {otherModDimensionName = splitEntry[3].trim();}  catch (Exception e) {otherModDimensionName="";}
			try {otherModBookType = splitEntry[4].trim();}       catch (Exception e) {otherModBookType="";}
			try {otherModClassPath = splitEntry[5].trim();}      catch (Exception e) {otherModClassPath="";}
			
			if( !otherModNameType.equals("") && !otherModStructureType.equals("") && !otherModBookType.equals("") ) { // Something was actually assigned in the try block
				otherModNameTypes.add(otherModNameType);
				otherModStructureTypes.add(otherModStructureType);
				otherModStructureTitles.add(otherModStructureTitle);
				otherModDimensionNames.add(otherModDimensionName);
				otherModBookTypes.add(otherModBookType);
				otherModClassPaths.add(otherModClassPath);
				}
		}

		Map<String,List> map =new HashMap();
		map.put("NameTypes",otherModNameTypes);
		map.put("StructureTypes",otherModStructureTypes);
		map.put("StructureTitles",otherModStructureTitles);
		map.put("DimensionNames",otherModDimensionNames);
		map.put("BookTypes",otherModBookTypes);
		map.put("ClassPaths",otherModClassPaths);
		
		return map;
	}
	

	/**
	 * Loads the (nameType|profession|classPath|AddOrRemove) string lists from othermods.cfg > Automatic Names and othermods.cfg > Clickable Names
	 * and assigns them to this instance's variables.
	 */
	public static Map<String, List> unpackMappedNames(String[] inputList) {
		
		List<String> otherModNameTypes = new ArrayList<String>();
		List<String> otherModProfessions = new ArrayList<String>();
		List<String> otherModClassPaths = new ArrayList<String>();
		List<String> addOrRemoveA = new ArrayList<String>();
		
		for (String entry : inputList) {
			// Remove parentheses
			entry.replaceAll("\\)", "");
			entry.replaceAll("\\(", "");
			// Split by pipe
			String[] splitEntry = entry.split("\\|");
			
			// Initialize temp fields
			String otherModNameType="";
			String otherModProfession="";
			String otherModClassPath="";
			String addOrRemove="";
			
			// Place entries into variables
			try {otherModNameType = splitEntry[0].trim();}   catch (Exception e) {otherModNameType="";}
			try {otherModProfession = splitEntry[1].trim();} catch (Exception e) {otherModProfession="";}
			try {otherModClassPath = splitEntry[2].trim();}  catch (Exception e) {otherModClassPath="";}
			try {addOrRemove       = splitEntry[3].trim();}  catch (Exception e) {addOrRemove="";}
			
			if( !otherModClassPath.equals("") && !otherModNameType.equals("") ) { // Something was actually assigned in the try block
				
				otherModClassPaths.add(otherModClassPath);
				otherModNameTypes.add(otherModNameType);
				otherModProfessions.add(otherModProfession);
				addOrRemoveA.add(addOrRemove);
				
				}
		}

		Map<String, List> map =new HashMap();
		map.put("NameTypes",otherModNameTypes);
		map.put("Professions",otherModProfessions);
		map.put("ClassPaths",otherModClassPaths);
		map.put("AddOrRemove",addOrRemoveA);
		
		return map;
	}
	
	
	/**
	 * Loads the (group|classPath|unlocName|meta) string lists and assigns them to this instance's variables.
	 */
	public static Map<String, List> unpackZombieCureCatalysts(String[] inputList) {
		List<String>  zombieCureCatalystGroups = new ArrayList<String>();
		List<String> zombieCureCatalystClassPaths = new ArrayList<String>();
		List<String> zombieCureCatalystUnlocNames = new ArrayList<String>();
		List<Integer> zombieCureCatalystMetas = new ArrayList<Integer>();
		
		for (String entry : inputList) {
			// Remove parentheses
			entry.replaceAll("\\)", "");
			entry.replaceAll("\\(", "");
			// Split by pipe
			String[] splitEntry = entry.split("\\|");
			
			// Initialize temp fields
			String zombieCureCatalystGroup="";
			String zombieCureCatalystClassPath="";
			String zombieCureCatalystUnlocName="";
			int zombieCureCatalystMeta=-1;
			
			// Place entries into variables
			try {zombieCureCatalystGroup = splitEntry[0].trim();}                  catch (Exception e) {}
			try {zombieCureCatalystClassPath = splitEntry[1].trim();}              catch (Exception e) {}
			try {zombieCureCatalystUnlocName = splitEntry[2].trim();}              catch (Exception e) {}
			try {zombieCureCatalystMeta = Integer.parseInt(splitEntry[3].trim());} catch (Exception e) {}
			
			if(
					   !zombieCureCatalystGroup.equals("")
					&& !zombieCureCatalystClassPath.equals("")
					) { // Something was actually assigned in the try block
				zombieCureCatalystGroups.add(zombieCureCatalystGroup);
				zombieCureCatalystClassPaths.add(zombieCureCatalystClassPath);
				zombieCureCatalystUnlocNames.add(zombieCureCatalystUnlocName);
				zombieCureCatalystMetas.add(zombieCureCatalystMeta);
			}
		}
		
		Map<String, List> map = new HashMap();
		map.put("Groups",zombieCureCatalystGroups);
		map.put("ClassPaths",zombieCureCatalystClassPaths);
		map.put("UnlocNames",zombieCureCatalystUnlocNames);
		map.put("Metas",zombieCureCatalystMetas);
		
		return map;
	}
	
	
	/**
	 * Loads the (group|speedup|limit) string lists and assigns them to this instance's variables.
	 */
	public static Map<String, List> unpackZombieCureGroups(String[] inputList) {
		List<String>  zombieCureGroupGroups = new ArrayList<String>();
		List<Double> zombieCureGroupSpeedups = new ArrayList<Double>();
		List<Integer> zombieCureGroupLimits = new ArrayList<Integer>();
		
		for (String entry : inputList) {
			// Remove parentheses
			entry.replaceAll("\\)", "");
			entry.replaceAll("\\(", "");
			// Split by pipe
			String[] splitEntry = entry.split("\\|");
			
			// Initialize temp fields
			String zombieCureGroupGroup="";
			double zombieCureGroupSpeedup=0.0D;
			int zombieCureGroupLimit=-1;
			
			// Place entries into variables
			try {zombieCureGroupGroup = splitEntry[0].trim();}                       catch (Exception e) {}
			try {zombieCureGroupSpeedup = Double.parseDouble(splitEntry[1].trim());} catch (Exception e) {}
			try {zombieCureGroupLimit = Integer.parseInt(splitEntry[2].trim());}     catch (Exception e) {}
			
			if(!zombieCureGroupGroup.equals("")) { // Something was actually assigned in the try block
				zombieCureGroupGroups.add(zombieCureGroupGroup);
				zombieCureGroupSpeedups.add(zombieCureGroupSpeedup);
				zombieCureGroupLimits.add(zombieCureGroupLimit);
			}
		}
		
		Map<String, List> map = new HashMap();
		map.put("Groups",zombieCureGroupGroups);
		map.put("Speedups",zombieCureGroupSpeedups);
		map.put("Limits",zombieCureGroupLimits);
		
		return map;
	}
	
	
	/**
	 * Loads the (careerAsset|zombieCareerAsset|professionID) string lists and assigns them to this instance's variables.
	 */
	public static Map<String, List> unpackModVillagerSkins(String[] inputList)
	{
		List<String> careerAsset_a = new ArrayList<String>();
		List<String> zombieCareerAsset_a = new ArrayList<String>();
		List<Integer> professionID_a = new ArrayList<Integer>();
		
		for (String entry : inputList)
		{
			// Remove slashes and double dots to prevent address abuse
			entry.replaceAll("/", ""); // Forward slashses don't need to be escaped
			entry.replaceAll("\\\\", ""); // \ is BOTH String and regex; needs to be double-escaped. See https://stackoverflow.com/questions/1701839/string-replaceall-single-backslashes-with-double-backslashes
			entry.replaceAll("..", "");
			// Split by pipe
			String[] splitEntry = entry.split("\\|");
			
			// Initialize temp fields
			String careerAsset="";
			String zombieCareerAsset="";
			Integer professionID=-1;
			
			// Place entries into variables
			try {careerAsset = splitEntry[0].trim();}       			catch (Exception e) {careerAsset="";}
			try {zombieCareerAsset = splitEntry[1].trim();} 			catch (Exception e) {zombieCareerAsset="";}
			try {professionID = Integer.valueOf(splitEntry[2].trim());} catch (Exception e) {professionID=-1;}
			
			if(!careerAsset.equals(""))
			{ // Something was actually assigned in the try block
				careerAsset_a.add(careerAsset);
				zombieCareerAsset_a.add(zombieCareerAsset);
				professionID_a.add(professionID);
			}
		}
		
		Map<String, List> map = new HashMap();
		map.put("careerAsset", careerAsset_a);
		map.put("zombieCareerAsset", zombieCareerAsset_a);
		map.put("professionID", professionID_a);
		
		return map;
	}
	
}
