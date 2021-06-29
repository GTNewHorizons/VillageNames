package astrotibs.villagenames.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import astrotibs.villagenames.utility.Reference;
import net.minecraftforge.common.config.Configuration;

public class GeneralConfig {
	public static Configuration config;
	
	//public static String[] blackList;
	public static boolean wellSlabs;
	public static boolean nameSign;
	public static boolean nameEntities;
	public static String headerTags = "\u00a78\u00a7o";
	public static boolean villagerDropBook;
	public static boolean villagerSellsCodex;
    public static boolean recordStructureCoords; 
	public static boolean addJobToName;
	public static String nitwitProfession;
	
	public static boolean villageBanners;
	public static int signYaw;
	
	public static boolean addConcrete;
	public static boolean concreteWell;
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
	public static String[] modNameMappingClickable;
	
	public static String[] modProfessionMapping;
	
	public static int PMMerchantProfessionMap;
	public static int PMLostMinerProfessionMap;
	
	public static String[] modStructureNames;
	
	public static boolean allowInGameConfig;
	
	public static boolean modernVillagerSkins;
	public static boolean modernZombieSkins;
	public static boolean moddedVillagerHeadwear;
	public static boolean removeMobArmor;
	
	public static String[] moddedVillagerHeadwearGraylist;
	public static ArrayList<Integer> moddedVillagerHeadwearWhitelist = new ArrayList<Integer>();
	public static ArrayList<Integer> moddedVillagerHeadwearBlacklist = new ArrayList<Integer>();
	
	public static String[] moddedVillagerModularSkins;
	public static Map<String, ArrayList> moddedVillagerCareerSkins;
	public static ArrayList<String> careerAsset_a;
	public static ArrayList<String> zombieCareerAsset_a;
	public static ArrayList<Integer> professionID_a;
	
	public static boolean villagerCareers;
	public static boolean treasureTrades;
	public static boolean LEGACYTRADESFALSE = false; // I don't want to allow these old trades.
	public static boolean modernVillagerTrades;
	public static boolean enableNitwit;
	public static String[] zombieCureCatalysts;
	public static String[] zombieCureGroups;
	
	public static boolean pyramidTerracotta;
	
	public static String[] modBamboo;
	public static String[] modBanner;
	public static String[] modBarrel;
	public static String[] modBeetroot;
	public static String[] modBookshelf;
	public static String[] modBountifulStone;
	public static String[] modButton;
	public static String[] modChest;
	public static String[] modConcrete;
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
	public static String[] modMossyStone;
	public static String[] modMutton;
	public static String[] modPressurePlate;
	public static String[] modPrismarine;
	public static String[] modRedSandstone;
	public static String[] modSign;
	public static String[] modSmoothSandstone;
	public static String[] modSmoothStone;
	public static String[] modSponge;
	public static String[] modSuspiciousStew;
	public static String[] modStrippedLog;
	public static String[] modSweetBerries;
	public static String[] modWall;
	public static String[] modWoodenTrapdoor;
	

	public static float harvestcraftCropFarmRate;
	public static float dragonQuestCropFarmRate;
	public static boolean antiqueAtlasMarkerNames;
	
    public static boolean villagerSkinTones;
    public static float villagerSkinToneVarianceAnnealing;
    public static float villagerSkinToneVarianceScale;
	
    
	public static void init(File configFile) 
	{
		if (config == null)
		{
			config = new Configuration(configFile);
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
	    villagerDropBook = config.getBoolean("Villager drops book", Reference.CATEGORY_GENERAL, false, "Village books are dropped by the villager rather than going directly into your inventory.");
	    villagerSellsCodex = config.getBoolean("Villager makes codex", Reference.CATEGORY_GENERAL, true, "Librarian villagers will give you a codex if you right-click them while holding emerald, iron ingots, and/or gold ingots.");
		useVillageColors = config.getBoolean("Use village colors", Reference.CATEGORY_GENERAL, true, "Whether to apply the village's colors to concrete, terracotta, carpet, etc.");
		
	    
	    
	    wellDecorations = config.getBoolean("Allow well decorations", Reference.CATEGORY_WELL_KILL_SWITCH, true, "Set this to false to override-disable all well decoration: sign, slabs, terracotta, concrete.");
	    
	    addConcrete = config.getBoolean("1.12 Blocks", Reference.CATEGORY_WORLD_OF_COLOR, true, "Whether to add 1.12 style Concrete, Concrete Powder, and Glazed Terracotta");
	    concreteWell = config.getBoolean("Concrete Well", Reference.CATEGORY_WORLD_OF_COLOR, true, "Whether to decorate wells with Concrete and Glazed Terracotta instead of stained clay");
	    
	    
	    
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
	    
	    moddedVillagerHeadwearGraylist = config.getStringList("Modded Villager Headwear Graylist", Reference.CATEGORY_VILLAGER_PROFESSIONS, new String[]{
				"14", // Growthcraft Apiarist
				"-15", // Apple & Milk & Tea's Cafe Master -- turned off because I enjoy his sexy hair v3.2.3
				"44", // Village Taverns's Shepherdess -- turned on to better differentiate from the vanilla Shepherd v3.2.3
				"80", // Forestry Apiarist
				"-190", // Thaumcraft Wizard -- turned off because of hat brim rendering issues
				"-191", // Thaumcraft Banker -- turned off because of hat brim rendering issues
				"-6156", // Open Blocks Music Merchant
				"7766", // Growthcraft Community Edition Apiarist
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
	    
	    moddedVillagerModularSkins = config.getStringList("Modded Villager Modular Skins", Reference.CATEGORY_VILLAGER_PROFESSIONS, new String[]{
				"gc_brewer||10", // Growthcraft
				"gc_apiarist||14", // Growthcraft
		        "amt_cafemaster||15", // Apple & Milk & Tea --v3.2.3
		        "amt_warehousemanager||16", // Apple & Milk & Tea --v3.2.3
		        "vt_barwench||42", // Village Taverns --v3.2.3
		        "vt_hostler||43", // Village Taverns --v3.2.3
		        "vt_shepherdess||44", // Village Taverns --v3.2.3
		        "vt_baker||45", // Village Taverns --v3.2.3
				"msm_swordsmith||66", // More Swords mod version 2
				"for_apiarist|for_apiarist|80", // Forestry
				"for_arborist|for_arborist|81", // Forestry
				"psy_dealer||87", // Psychedelicraft
				"thc_wizard||190", // Thaumcraft
				"thc_banker||191", // Thaumcraft
				"fa_archaeologist||303", // Fossils and Archaeology
				"rc_engineer|rc_engineer|456", // Railcraft
				"wit_apothecary||2435", // Witchery
				"ob_musicmerchant||6156", // Open Blocks
				"gc_brewer||6677", // Growthcraft Community Edition
				"gc_apiarist||7766", // Growthcraft Community Edition
				"mus_clerk||52798", // Musica
				"tc_tinkerer||78943", // Tinkers Construct
				"ccp_stablehand||19940402", // ChocoCraft Plus
				"myc_archivist||1210950779", // Mystcraft
				// Actually Additions
				"aa_jam|aa_jam|493827",
	    		},
	    		"(If modern skins are enabled) List of profession IDs for other mods' villagers to render in the modular skin style. Format is: careerAsset|zombieCareerAsset|professionID\n"+
	    		"careerAsset: career skin png to be overlaid onto the villager, located in assets\\"+Reference.MOD_ID.toLowerCase()+"\\textures\\entity\\villager\\profession\n"+
	    				"The default values are all available in "+Reference.MOD_NAME+". You can access custom values with a resourcepack.\n"
	    						+ "zombieCareerAsset: a zombie career png, located in the corresponding zombie_villager directory. You may leave this value blank, in which case it will use the non-zombie career overlay.\n"
	    						+ "professionID: the ID associated with the mod profession.");
	    
	    // Assign the map now and immediately extract it into arrays for faster lookup
	    moddedVillagerCareerSkins = GeneralConfig.unpackModVillagerSkins(GeneralConfig.moddedVillagerModularSkins);
	    careerAsset_a = (ArrayList<String>)moddedVillagerCareerSkins.get("careerAsset");
	    zombieCareerAsset_a = (ArrayList<String>)moddedVillagerCareerSkins.get("zombieCareerAsset");
	    professionID_a = (ArrayList<Integer>)moddedVillagerCareerSkins.get("professionID");
	    

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
	    
	    zombieCureCatalysts = config.getStringList("Zombie Cure Catalysts", Reference.CATEGORY_GENERAL, new String[]{
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
	    
	    zombieCureGroups = config.getStringList("Zombie Cure Groups", Reference.CATEGORY_GENERAL, new String[]{
 				"vanilla|0.3|14"
 				},
 				"When curing a zombie villager, all blocks of the same named group will use these stats. "
 				+ "Format is: group|speedup|limit\n"
 				+ "group is the group name assigned in Zombie Cure Catalysts above.\n"
 				+ "speedup is the per-block percentage point boost in conversion speed. That is: a value of 1.0 increases the conversion by about 1 percentage point per group block found. "
 				+ "negative values will likewise reduce the conversion speed, making conversion take longer.\n"
 				+ "limit is the maximum number of blocks in this group that will apply the group speedup effect."
 				);
 		
 	    versionChecker = config.getBoolean("Version Checker", Reference.CATEGORY_MISCELLANEOUS, false, "Displays a client-side chat message on login if there's an update available. If the URL pinged by the checker happens to be down, your game will freeze for a while on login. Turn this on at your own risk.");
	    codexChestLoot = config.getBoolean("Codex Chest Loot", Reference.CATEGORY_MISCELLANEOUS, true, "The Codex can appear as rare chest loot.");
	    allowInGameConfig = config.getBoolean("Allow in-game config access", Reference.CATEGORY_MISCELLANEOUS, true, "Set this to false to deacactivate the in-game config GUI, in case it conflicts with other mods.");
	    debugMessages = config.getBoolean("Debug messages", Reference.CATEGORY_MISCELLANEOUS, false, "Print debug messages to the console, print the class paths of entities and blocks you right-click.");
	    pyramidTerracotta = config.getBoolean("Pyramid Terracotta", Reference.CATEGORY_MISCELLANEOUS, true, "Replace the wool blocks in desert pyramids with terracotta as in 1.8+");
	    addOceanMonuments = config.getBoolean("Add Monuments", Reference.CATEGORY_MISCELLANEOUS, true, "Generate Ocean Monuments, Prismarine, Guardians, and absorbent Sponges");
	    //alternateGuardianNamespace = config.getBoolean("Alternate Guardian Namespace", Reference.CATEGORY_MISCELLANEOUS, false, "WARNING: TOGGLING THIS VALUE WILL REMOVE OR REPLACE ALL CURRENTLY-SPAWNED GUARDIANS FROM YOUR WORLD, INCLUDING ELDERS!\n"+"Set this to true if you have fatal conflicts loading another mod using the entity name minecraft:Guardian by instead using the name minecraft:Guardian_VN."); // v3.2.3
	    addIgloos = config.getBoolean("Add Igloos", Reference.CATEGORY_MISCELLANEOUS, true, "Generate Igloos from 1.9+");
	    // Added in v3.1
	    biomedictIgloos = config.getBoolean("Allow Igloos in modded biomes", Reference.CATEGORY_MISCELLANEOUS, false, "Igloos can generate in mods' snowy plains biomes, rather than just vanilla's Ice Plains and Cold Taiga");
	    
	    
	    //--------------Names-----------------//
	    
	    nameEntities = config.getBoolean("Entity names", Reference.CATEGORY_NAMING, true, "Entities reveal their names when you right-click them, or automatically if so assigned.");
	    addJobToName = config.getBoolean("Entity professions", Reference.CATEGORY_NAMING, false, "An entity's name also includes its profession/title. You may need to right-click the entity to update its name plate.");
	    nameGolems = config.getBoolean("Golem names", Reference.CATEGORY_NAMING, true, "Right-click village Golems to learn their name.");
	    
	    nitwitProfession = config.getString("Nitwit Profession", Reference.CATEGORY_NAMING, "", "The career displayed for a Nitwit");
	    
		// Automatic Names
		
		modNameMappingAutomatic = config.getStringList("Automatic Names", Reference.CATEGORY_NAMING, new String[]{
				
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
				
				// Primitive Mobs
				"villager|Summoner|net.daveyx0.primitivemobs.entity.monster.EntityDSummoner|add",
				
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
				"demon|Lord of Torment|com.emoniph.witchery.entity.EntityLordOfTorment|add",
				
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
		
	    

		// Clickable Names
	    
		modNameMappingClickable = config.getStringList("Clickable Names", Reference.CATEGORY_NAMING, new String[]{
				
				// Galacticraft
				"alien||micdoodle8.mods.galacticraft.core.entities.EntityAlienVillager",
				
				// More Planets
				"alien||stevekung.mods.moreplanets.moons.koentus.entities.EntityKoentusianVillager", // 1.7
				"alien||stevekung.mods.moreplanets.module.moons.koentus.entities.EntityKoentusianVillager", // 1.10
				"alien-villager-goblin||stevekung.mods.moreplanets.planets.fronos.entities.EntityFronosVillager", // 1.7
				"alien-villager-goblin||stevekung.mods.moreplanets.module.planets.fronos.entities.EntityFronosVillager", // 1.10
				"alien-villager-angel||stevekung.mods.moreplanets.planets.nibiru.entity.EntityNibiruVillager", // 1.7
				"alien-villager-angel||stevekung.mods.moreplanets.module.planets.nibiru.entity.EntityNibiruVillager", // 1.10
				
				// Natura
				"goblin-demon||mods.natura.entity.ImpEntity",
				
				// Thaumcraft
				"goblin||thaumcraft.common.entities.monster.EntityPech",
				
				// Twilight Forest
				"angel-goblin|Questing Ram|twilightforest.entity.passive.EntityTFQuestRam",
				
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
				"villager-goblin||DelirusCrux.Netherlicious.Common.Entities.Passive.EntityPiglin"
				
				},
				"List of entities that can generate a name when right-clicked. Format is: nameType|profession|classPath\n"
				+ "nameType is the name pool for the entity, or a hyphenated series of pools like \"angel-golem\".\n"
				+ "profession is displayed if that config flag is enabled. It can be left blank for no profession.\n"
				+ "classPath is mod's address to the entity class.\n"
								+ "nameType options:\n"
								+ "villager, dragon, golem, alien, angel, demon, goblin, pet, custom\n"
								);
		
		
		//--------------Mod Integration-----------------//
		
		harvestcraftCropFarmRate = config.getFloat("Crop rate: Harvestcraft", Reference.CATEGORY_MOD_INTEGRATION, 0.25F, 0F, 1F, "Generate Harvestcraft crops in farms. Only used with Village Generator. Set to 0 for no HC crops.");
		dragonQuestCropFarmRate = config.getFloat("Crop rate: DQ Respect", Reference.CATEGORY_MOD_INTEGRATION, 0.25F, 0F, 1F, "Generate Dragon Quest Respect crops in farms. Only used with Village Generator. Set to 0 for no DQR crops.");
		antiqueAtlasMarkerNames = config.getBoolean("Antique Atlas: Village Marker Names", Reference.CATEGORY_MOD_INTEGRATION, true, "Label a new village marker with the village's name in your Antique Atlases.");

		modBamboo = config.getStringList("Mod Priority: Bamboo", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"growthcraft",
 				"biomesoplenty",
 				},
 				"Priority order for referencing Bamboo for village generation. The version highest on the list and registered in your game will be used."
 				);
		
		modBanner = config.getStringList("Mod Priority: Banner", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
 				"ganyssurface",
 				},
 				"Priority order for referencing Banners for e.g. villager trade offers. The version highest on the list and registered in your game will be used."
 				);
		
		modBarrel = config.getStringList("Mod Priority: Barrel", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
 				"uptodate",
 				},
 				"Priority order for referencing Barrels for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modBeetroot = config.getStringList("Mod Priority: Beetroot", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
 				"ganyssurface",
 				},
 				"Priority order for referencing Beetroot for e.g. villager trade offers. The version highest on the list and registered in your game will be used."
 				);
	    
	    modBookshelf = config.getStringList("Mod Priority: Bookshelf", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"woodstuff",
 				"ganyssurface",
 				},
 				"Priority order for referencing Bookshelves for village generation. The version highest on the list and registered in your game will be used."
 				);
		
	    modBountifulStone = config.getStringList("Mod Priority: Bountiful Stone", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"chisel",
 				"etfuturum",
	    		"uptodate",
 				"ganyssurface",
 				"botania",
 				},
 				"Priority order for referencing Granite, Diorite, and Andesite for e.g. villager trade offers. The version highest on the list and registered in your game will be used."
 				);
	    
		modButton = config.getStringList("Mod Priority: Button", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"uptodate",
	    		"woodstuff",
	    		"etfuturum",
 				"ganyssurface",
 				},
 				"Priority order for referencing wood buttons for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modChest = config.getStringList("Mod Priority: Chest", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"woodstuff",
 				"ganyssurface",
 				},
 				"Priority order for referencing Wooden Chests for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modConcrete = config.getStringList("Mod Priority: Concrete", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"villagenames",
	    		"uptodate",
 				"etfuturum",
 				},
 				"Priority order for referencing concrete for well decorations; essentially, if you still want these features but want to disable "+ Reference.MOD_NAME+"\'s versions. The version highest on the list and registered in your game will be used."
 				);
	    
	    modDoor = config.getStringList("Mod Priority: Door", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"uptodate",
	    		"etfuturum",
 				"ganyssurface",
 				"malisisdoors",
 				},
 				"Priority order for referencing Doors for village generation. The version highest on the list and registered in your game will be used."
 				);
		
	    modDye = config.getStringList("Mod Priority: Dye", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"uptodate",
 				"biomesoplenty",
 				"mariculture",
 				},
 				"Priority order for referencing dye for villager trade offers. The version highest on the list and registered in your game will be used."
 				);
	    
	    modFence = config.getStringList("Mod Priority: Fence", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"uptodate",
	    		"woodstuff",
 				"etfuturum",
 				"ganyssurface",
 				},
 				"Priority order for referencing Fence blocks for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modFenceGate = config.getStringList("Mod Priority: Fence Gate", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
 				"ganyssurface",
	    		"uptodate",
	    		"woodstuff",
	    		"malisisdoors",
 				},
 				"Priority order for referencing Fence Gate blocks for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modFlower = config.getStringList("Mod Priority: Flower", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
	    		"uptodate",
 				},
 				"Priority order for referencing flowers for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modGlazedTerracotta = config.getStringList("Mod Priority: Glazed Terracotta", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"villagenames",
 				"etfuturum",
	    		"uptodate",
 				},
 				"Priority order for referencing Glazed Terracotta for villager trade offers and well decorations; essentially, if you still want these features but want to disable "+ Reference.MOD_NAME+"\'s versions. The version highest on the list and registered in your game will be used."
 				);
		
	    modGrassPath = config.getStringList("Mod Priority: Grass Path", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
	    		"uptodate",
 				},
 				"Priority order for referencing Grass Path blocks for village generation. The version highest on the list and registered in your game will be used."
 				);
		
	    modIronNugget = config.getStringList("Mod Priority: Iron Nugget", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"etfuturum",
	    		"uptodate",
 				"tinkersconstruct",
 				"thermalfoundation",
 				"railcraft",
 				"mariculture",
 				"netherlicious",
 				"ganysnether",
 				},
 				"Priority order for referencing Iron Nuggets for e.g. village chest loot. The version highest on the list and registered in your game will be used."
 				);
	    
	    modKelp = config.getStringList("Mod Priority: Kelp", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"mariculture",
	    		"biomesoplenty",
 				},
 				"Priority order for referencing Kelp for e.g. villager trade offers. The version highest on the list and registered in your game will be used."
 				);
		
	    modLantern = config.getStringList("Mod Priority: Lantern", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
 				"netherlicious",
 				"uptodate",
	    		"enviromine",
 				},
 				"Priority order for referencing Lanterns for e.g. village generation and villager trade offers. The version highest on the list and registered in your game will be used."
 				);
	    
	    modMossyStone = config.getStringList("Mod Priority: Mossy Stone", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
	    		"uptodate",
 				},
 				"Priority order for referencing mossy stone blocks for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modMutton = config.getStringList("Mod Priority: Mutton", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"uptodate",
 				"etfuturum",
 				"ganyssurface",
 				"harvestcraft",
 				},
 				"Priority order for referencing Mutton for e.g. villager trade offers. The version highest on the list and registered in your game will be used."
 				);
	    
	    modPressurePlate = config.getStringList("Mod Priority: Pressure Plate", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"uptodate",
	    		"woodstuff",
 				"etfuturum",
 				"ganyssurface",
 				},
 				"Priority order for referencing Fence blocks for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modPrismarine = config.getStringList("Mod Priority: Prismarine", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"villagenames",
 				"botania",
 				"etfuturum",
	    		"uptodate",
 				},
 				"Priority order for referencing Prismarine blocks and items for monument and village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modRedSandstone = config.getStringList("Mod Priority: Red Sandstone", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"uptodate",
 				"ganyssurface",
 				},
 				"Priority order for referencing Red Sandstone and its variants for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modSign = config.getStringList("Mod Priority: Sign", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"ganyssurface",
 				"etfuturum",
 				},
 				"Priority order for referencing Signs for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modSmoothStone = config.getStringList("Mod Priority: Smooth Stone", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
	    		"uptodate",
 				},
 				"Priority order for referencing Smooth Stone for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modSmoothSandstone = config.getStringList("Mod Priority: Smooth Sandstone", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
	    		"uptodate",
 				},
 				"Priority order for referencing Smooth Sandstone for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modSponge = config.getStringList("Mod Priority: Sponge", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"villagenames",
 				"etfuturum",
	    		"uptodate",
 				},
 				"Priority order for referencing Sponge blocks for monument generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modStrippedLog = config.getStringList("Mod Priority: Stripped Log", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"uptodate",
 				"etfuturum",
 				},
 				"Priority order for referencing Stripped Logs/Wood for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modSuspiciousStew = config.getStringList("Mod Priority: Suspicious Stew", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
	    		"uptodate",
 				},
 				"Priority order for referencing Suspicious Stew for villager trades. The version highest on the list and registered in your game will be used."
 				);

	    modSweetBerries = config.getStringList("Mod Priority: Sweet Berries", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
 				"etfuturum",
	    		"uptodate",
 				},
 				"Priority order for referencing sweet berries for villager trade offers. The version highest on the list and registered in your game will be used."
 				);
	    
	    modWall = config.getStringList("Mod Priority: Wall", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"uptodate",
 				"railcraft",
 				},
 				"Priority order for referencing walls for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modWoodenTrapdoor = config.getStringList("Mod Priority: Trapdoor", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
	    		"uptodate",
	    		"etfuturum",
 				"ganyssurface",
 				},
 				"Priority order for referencing Wooden Trapdoors for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    
		// Mapping for modded structures, and the creatures that can name them
		modStructureNames = config.getStringList("Mod Structures", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
				
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
		
		// New mod profession mapping
		modProfessionMapping = config.getStringList("Mod Professions", Reference.CATEGORY_MOD_INTEGRATION, new String[]{
				"Brewer|10|0", // Growthcraft
		        "Cafe Master|15|0", // Apple & Milk & Tea --v3.2.3
		        "Warehouse Manager|16|0", // Apple & Milk & Tea --v3.2.3
		        "Bar Wench|42|0", // Village Taverns --v3.2.3
		        "Hostler|43|0", // Village Taverns --v3.2.3
		        "Shepherd|44|0", // Village Taverns --v3.2.3
		        "Baker|45|0", // Village Taverns --v3.2.3
				"Apiarist|14|4", // Growthcraft
				"Swordsmith|66|5", // More Swords mod version 2
				"Apiarist|80|4", // Forestry
				"Arborist|81|0", // Forestry
				"Dealer|87|0", // Psychedelicraft
				"Wizard|190|2", // Thaumcraft
				"Banker|191|0", // Thaumcraft
				"Archaeologist|303|2", // Fossils and Archaeology
				"Engineer|456|3", // Railcraft
				"Apothecary|2435|2", // Witchery
				"Music Merchant|6156|5", // Open Blocks
				"Brewer|6677|0", // Growthcraft Community Edition
				"Apiarist|7766|4", // Growthcraft Community Edition
				"Clerk|52798|0", // Musica
				"Tinkerer|78943|5", // Tinkers Construct
				"Enchanter|935153|2", 
				"Stablehand|19940402|0", // ChocoCraft Plus
				"Archivist|1210950779|1", // Mystcraft
				// Actually Additions
				"Jam Guy|493827|0",
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
	public static Map<String, ArrayList> unpackMappedProfessions(String[] inputList) {
		ArrayList<String>  otherModProfessions = new ArrayList<String>();
		ArrayList<Integer> otherModIDs = new ArrayList<Integer>();
		ArrayList<Integer> vanillaProfMaps = new ArrayList<Integer>();
		
		for (String entry : inputList) {
			// Remove parentheses
			entry.replaceAll("\\)", "");
			entry.replaceAll("\\(", "");
			// Split by pipe
			String[] splitEntry = entry.split("\\|");
			
			// Initialize temp fields
			String otherModProfession="";
			int otherModID=-1;
			int vanillaProfMap=-1;
			
			// Place entries into variables
			try {otherModProfession = splitEntry[0].trim();}               catch (Exception e) {otherModProfession="";}
			try {otherModID = Integer.parseInt(splitEntry[1].trim());}     catch (Exception e) {otherModID=-1;}
			try {vanillaProfMap = Integer.parseInt(splitEntry[2].trim());} catch (Exception e) {vanillaProfMap=-1;}
			
			if( !otherModProfession.equals("") && otherModID!=-1 ) { // Something was actually assigned in the try block
				otherModProfessions.add(otherModProfession);
				otherModIDs.add(otherModID);
				vanillaProfMaps.add(vanillaProfMap);
			}
		}
		
		Map<String,ArrayList> map =new HashMap();
		map.put("Professions",otherModProfessions);
		map.put("IDs",otherModIDs);
		map.put("VanillaProfMaps",vanillaProfMaps);
		
		return map;
	}
	
	

	/**
	 * Loads the (nameType|structureType|structureTitle|dimensionName|bookType|entityClassPath) string lists from othermods.cfg > Mod Structures
	 * and assigns them to this instance's variables.
	 */
	public static Map<String, ArrayList> unpackModStructures(String[] inputList) {
		
		ArrayList<String> otherModNameTypes = new ArrayList<String>();
		ArrayList<String> otherModStructureTypes = new ArrayList<String>();
		ArrayList<String> otherModStructureTitles = new ArrayList<String>();
		ArrayList<String> otherModDimensionNames = new ArrayList<String>();
		ArrayList<String> otherModBookTypes = new ArrayList<String>();
		ArrayList<String> otherModClassPaths = new ArrayList<String>();
		
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

		Map<String,ArrayList> map =new HashMap();
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
	public static Map<String, ArrayList> unpackMappedNames(String[] inputList) {
		
		ArrayList<String> otherModNameTypes = new ArrayList<String>();
		ArrayList<String> otherModProfessions = new ArrayList<String>();
		ArrayList<String> otherModClassPaths = new ArrayList<String>();
		ArrayList<String> addOrRemoveA = new ArrayList<String>();
		
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

		Map<String,ArrayList> map =new HashMap();
		map.put("NameTypes",otherModNameTypes);
		map.put("Professions",otherModProfessions);
		map.put("ClassPaths",otherModClassPaths);
		map.put("AddOrRemove",addOrRemoveA);
		
		return map;
	}
	
	
	/**
	 * Loads the (group|classPath|unlocName|meta) string lists and assigns them to this instance's variables.
	 */
	public static Map<String, ArrayList> unpackZombieCureCatalysts(String[] inputList) {
		ArrayList<String>  zombieCureCatalystGroups = new ArrayList<String>();
		ArrayList<String> zombieCureCatalystClassPaths = new ArrayList<String>();
		ArrayList<String> zombieCureCatalystUnlocNames = new ArrayList<String>();
		ArrayList<Integer> zombieCureCatalystMetas = new ArrayList<Integer>();
		
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
		
		Map<String,ArrayList> map = new HashMap();
		map.put("Groups",zombieCureCatalystGroups);
		map.put("ClassPaths",zombieCureCatalystClassPaths);
		map.put("UnlocNames",zombieCureCatalystUnlocNames);
		map.put("Metas",zombieCureCatalystMetas);
		
		return map;
	}
	
	/**
	 * Loads the (group|speedup|limit) string lists and assigns them to this instance's variables.
	 */
	public static Map<String, ArrayList> unpackZombieCureGroups(String[] inputList) {
		ArrayList<String>  zombieCureGroupGroups = new ArrayList<String>();
		ArrayList<Double> zombieCureGroupSpeedups = new ArrayList<Double>();
		ArrayList<Integer> zombieCureGroupLimits = new ArrayList<Integer>();
		
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
		
		Map<String,ArrayList> map = new HashMap();
		map.put("Groups",zombieCureGroupGroups);
		map.put("Speedups",zombieCureGroupSpeedups);
		map.put("Limits",zombieCureGroupLimits);
		
		return map;
	}
	
	// Added in v3.2
	/**
	 * Loads the (careerAsset|zombieCareerAsset|professionID) string lists and assigns them to this instance's variables.
	 */
	public static Map<String, ArrayList> unpackModVillagerSkins(String[] inputList) {
		ArrayList<String>  careerAsset_a = new ArrayList<String>();
		ArrayList<String> zombieCareerAsset_a = new ArrayList<String>();
		ArrayList<Integer> professionID_a = new ArrayList<Integer>();
		
		for (String entry : inputList) {
			// Remove slashes and double dots to prevent address abuse
			entry.replaceAll("/", ""); // Forward slashses don't need to be escaped
			entry.replaceAll("\\\\", ""); // \ is BOTH String and regex; needs to be double-escaped. See https://stackoverflow.com/questions/1701839/string-replaceall-single-backslashes-with-double-backslashes
			entry.replaceAll("..", "");
			// Split by pipe
			String[] splitEntry = entry.split("\\|");
			
			// Initialize temp fields
			String careerAsset="";
			String zombieCareerAsset="";
			int professionID=-1;
			
			// Place entries into variables
			try {careerAsset = splitEntry[0].trim();}                       catch (Exception e) {}
			try {zombieCareerAsset = splitEntry[1].trim();} 				catch (Exception e) {}
			try {professionID = Integer.parseInt(splitEntry[2].trim());}    catch (Exception e) {}
			
			if(!careerAsset.equals("")) { // Something was actually assigned in the try block
				careerAsset_a.add(careerAsset);
				zombieCareerAsset_a.add(zombieCareerAsset);
				professionID_a.add(professionID);
			}
		}
		
		Map<String,ArrayList> map = new HashMap();
		map.put("careerAsset",careerAsset_a);
		map.put("zombieCareerAsset",zombieCareerAsset_a);
		map.put("professionID",professionID_a);
		
		return map;
	}
	
}
