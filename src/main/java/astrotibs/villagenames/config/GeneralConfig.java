package astrotibs.villagenames.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import astrotibs.villagenames.utility.LogHelper;
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
	// Added in v3.1banner
	public static boolean villageBanners;
	public static int signYaw;
	
	public static boolean addConcrete;
	public static boolean concreteWell;
	public static boolean addOceanMonuments;
	public static boolean alternateGuardianNamespace; // v3.2.3
	public static boolean addIgloos;
	public static boolean biomedictIgloos; // Added in v3.1
	
	public static boolean codexChestLoot;
	public static boolean versionChecker;
	
	public static boolean wellBoundary;
	public static boolean wellDecorations;
	public static boolean debugMessages;
	public static boolean nameGolems;
	
	public static String[] modNameMappingAutomatic;
	public static String[] modNameMappingClickable;
	
	public static String[] modProfessionMapping;
	
	public static int PMMerchantProfessionMap;
	public static int PMLostMinerProfessionMap;
	
	public static String[] modStructureNames;
	
	public static boolean allowInGameConfig;
	
	public static boolean modernVillagerSkins; // Changed in v3.1
	public static boolean modernZombieSkins; // Added in v3.2.3
	public static boolean moddedVillagerHeadwear; // Added in v3.1.1
	public static boolean removeMobArmor; // v3.2.3
	
	// Added in v3.2
	public static String[] moddedVillagerHeadwearGraylist;
	public static ArrayList<Integer> moddedVillagerHeadwearWhitelist = new ArrayList<Integer>();
	public static ArrayList<Integer> moddedVillagerHeadwearBlacklist = new ArrayList<Integer>();
	
	// Added in v3.2
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
	
	// Added in v3.1trades
	public static String[] modBountifulStone;
	public static String[] modBanner;
	public static String[] modBeetroot;
	public static String[] modIronNugget;
	public static String[] modMutton;
	public static String[] modKelp;
	public static String[] modLantern;
	public static String[] modGrassPath;
	
	// Added in v3.2
	public static String[] modButton;
	public static String[] modConcrete;
	public static String[] modGlazedTerracotta;
	public static String[] modDye;
	public static String[] modFence;
	public static String[] modPressurePlate;
	public static String[] modFenceGate;
	public static String[] modDoor;
	public static String[] modWoodenTrapdoor;
	public static String[] modRedSandstone;
	public static String[] modStrippedLog;
	public static String[] modWall;
	
    public static boolean villagerSkinTones;
    public static float villagerSkinToneVarianceAnnealing;
    public static float villagerSkinToneVarianceScale;
	
    
    // --- Villages --- //
	public static boolean newVillageGenerator;
	public static int newVillageSize;
	public static int newVillageSpacingMedian;
	public static int newVillageSpacingSpread;
	public static String[] spawnBiomesNames;
	// Legacy Village buildings
	public static String componentLegacyHouse4Garden_string; public static ArrayList<Double> componentLegacyHouse4Garden_vals;
	public static String componentLegacyChurch_string; public static ArrayList<Double> componentLegacyChurch_vals;
	public static String componentLegacyHouse1_string; public static ArrayList<Double> componentLegacyHouse1_vals;
	public static String componentLegacyWoodHut_string; public static ArrayList<Double> componentLegacyWoodHut_vals;
	public static String componentLegacyHall_string; public static ArrayList<Double> componentLegacyHall_vals;
	public static String componentLegacyField1_string; public static ArrayList<Double> componentLegacyField1_vals;
	public static String componentLegacyField2_string; public static ArrayList<Double> componentLegacyField2_vals;
	public static String componentLegacyHouse2_string; public static ArrayList<Double> componentLegacyHouse2_vals;
	public static String componentLegacyHouse3_string; public static ArrayList<Double> componentLegacyHouse3_vals;
	// Modern Village buildings
	public static String componentModernPlainsAccessory1_string; public static ArrayList<Double> componentModernPlainsAccessory1_vals;
	public static String componentModernPlainsAnimalPen1_string; public static ArrayList<Double> componentModernPlainsAnimalPen1_vals;
	public static String componentModernPlainsAnimalPen2_string; public static ArrayList<Double> componentModernPlainsAnimalPen2_vals;
	public static String componentModernPlainsAnimalPen3_string; public static ArrayList<Double> componentModernPlainsAnimalPen3_vals;
	public static String componentModernPlainsArmorerHouse1_string; public static ArrayList<Double> componentModernPlainsArmorerHouse1_vals;
	public static String componentModernPlainsBigHouse1_string; public static ArrayList<Double> componentModernPlainsBigHouse1_vals;
	public static String componentModernPlainsButcherShop1_string; public static ArrayList<Double> componentModernPlainsButcherShop1_vals;
	public static String componentModernPlainsButcherShop2_string; public static ArrayList<Double> componentModernPlainsButcherShop2_vals;
	public static String componentModernPlainsCartographer1_string; public static ArrayList<Double> componentModernPlainsCartographer1_vals;
	public static String componentModernPlainsFisherCottage1_string; public static ArrayList<Double> componentModernPlainsFisherCottage1_vals;
	public static String componentModernPlainsFletcherHouse1_string; public static ArrayList<Double> componentModernPlainsFletcherHouse1_vals;
	public static String componentModernPlainsLargeFarm1_string; public static ArrayList<Double> componentModernPlainsLargeFarm1_vals;
	public static String componentModernPlainsLibrary1_string; public static ArrayList<Double> componentModernPlainsLibrary1_vals;
	public static String componentModernPlainsLibrary2_string; public static ArrayList<Double> componentModernPlainsLibrary2_vals;
	public static String componentModernPlainsMasonsHouse1_string; public static ArrayList<Double> componentModernPlainsMasonsHouse1_vals;
	public static String componentModernPlainsMediumHouse1_string; public static ArrayList<Double> componentModernPlainsMediumHouse1_vals;
	public static String componentModernPlainsMediumHouse2_string; public static ArrayList<Double> componentModernPlainsMediumHouse2_vals;
	public static String componentModernPlainsMeetingPoint4_string; public static ArrayList<Double> componentModernPlainsMeetingPoint4_vals;
	public static String componentModernPlainsMeetingPoint5_string; public static ArrayList<Double> componentModernPlainsMeetingPoint5_vals;
	public static String componentModernPlainsShepherdsHouse1_string; public static ArrayList<Double> componentModernPlainsShepherdsHouse1_vals;
	public static String componentModernPlainsSmallFarm1_string; public static ArrayList<Double> componentModernPlainsSmallFarm1_vals;
	public static String componentModernPlainsSmallHouse1_string; public static ArrayList<Double> componentModernPlainsSmallHouse1_vals;
	public static String componentModernPlainsSmallHouse2_string; public static ArrayList<Double> componentModernPlainsSmallHouse2_vals;
	public static String componentModernPlainsSmallHouse3_string; public static ArrayList<Double> componentModernPlainsSmallHouse3_vals;
	public static String componentModernPlainsSmallHouse4_string; public static ArrayList<Double> componentModernPlainsSmallHouse4_vals;
	public static String componentModernPlainsSmallHouse5_string; public static ArrayList<Double> componentModernPlainsSmallHouse5_vals;
	public static String componentModernPlainsSmallHouse6_string; public static ArrayList<Double> componentModernPlainsSmallHouse6_vals;
	public static String componentModernPlainsSmallHouse7_string; public static ArrayList<Double> componentModernPlainsSmallHouse7_vals;
	public static String componentModernPlainsSmallHouse8_string; public static ArrayList<Double> componentModernPlainsSmallHouse8_vals;
	public static String componentModernPlainsStable1_string; public static ArrayList<Double> componentModernPlainsStable1_vals;
	public static String componentModernPlainsStable2_string; public static ArrayList<Double> componentModernPlainsStable2_vals;
	public static String componentModernPlainsTannery1_string; public static ArrayList<Double> componentModernPlainsTannery1_vals;
	public static String componentModernPlainsTemple3_string; public static ArrayList<Double> componentModernPlainsTemple3_vals;
	public static String componentModernPlainsTemple4_string; public static ArrayList<Double> componentModernPlainsTemple4_vals;
	public static String componentModernPlainsToolSmith1_string; public static ArrayList<Double> componentModernPlainsToolSmith1_vals;
	public static String componentModernPlainsWeaponsmith1_string; public static ArrayList<Double> componentModernPlainsWeaponsmith1_vals;
	public static String componentModernPlainsStreetSubstitute1_string; public static ArrayList<Double> componentModernPlainsStreetSubstitute1_vals;
	
	public static String componentModernDesertAnimalPen1_string; public static ArrayList<Double> componentModernDesertAnimalPen1_vals;
	public static String componentModernDesertAnimalPen2_string; public static ArrayList<Double> componentModernDesertAnimalPen2_vals;
	public static String componentModernDesertArmorer1_string; public static ArrayList<Double> componentModernDesertArmorer1_vals;
	public static String componentModernDesertButcherShop1_string; public static ArrayList<Double> componentModernDesertButcherShop1_vals;
	public static String componentModernDesertCartographerHouse1_string; public static ArrayList<Double> componentModernDesertCartographerHouse1_vals;
	public static String componentModernDesertFarm1_string; public static ArrayList<Double> componentModernDesertFarm1_vals;
	public static String componentModernDesertFarm2_string; public static ArrayList<Double> componentModernDesertFarm2_vals;
	public static String componentModernDesertFisher1_string; public static ArrayList<Double> componentModernDesertFisher1_vals;
	public static String componentModernDesertFletcherHouse1_string; public static ArrayList<Double> componentModernDesertFletcherHouse1_vals;
	public static String componentModernDesertLargeFarm1_string; public static ArrayList<Double> componentModernDesertLargeFarm1_vals;
	public static String componentModernDesertLibrary1_string; public static ArrayList<Double> componentModernDesertLibrary1_vals;
	public static String componentModernDesertMason1_string; public static ArrayList<Double> componentModernDesertMason1_vals;
	public static String componentModernDesertMediumHouse1_string; public static ArrayList<Double> componentModernDesertMediumHouse1_vals;
	public static String componentModernDesertMediumHouse2_string; public static ArrayList<Double> componentModernDesertMediumHouse2_vals;
	public static String componentModernDesertShepherdHouse1_string; public static ArrayList<Double> componentModernDesertShepherdHouse1_vals;
	public static String componentModernDesertSmallHouse1_string; public static ArrayList<Double> componentModernDesertSmallHouse1_vals;
	public static String componentModernDesertSmallHouse2_string; public static ArrayList<Double> componentModernDesertSmallHouse2_vals;
	public static String componentModernDesertSmallHouse3_string; public static ArrayList<Double> componentModernDesertSmallHouse3_vals;
	public static String componentModernDesertSmallHouse4_string; public static ArrayList<Double> componentModernDesertSmallHouse4_vals;
	public static String componentModernDesertSmallHouse5_string; public static ArrayList<Double> componentModernDesertSmallHouse5_vals;
	public static String componentModernDesertSmallHouse6_string; public static ArrayList<Double> componentModernDesertSmallHouse6_vals;
	public static String componentModernDesertSmallHouse7_string; public static ArrayList<Double> componentModernDesertSmallHouse7_vals;
	public static String componentModernDesertSmallHouse8_string; public static ArrayList<Double> componentModernDesertSmallHouse8_vals;
	public static String componentModernDesertTannery1_string; public static ArrayList<Double> componentModernDesertTannery1_vals;
	public static String componentModernDesertTemple1_string; public static ArrayList<Double> componentModernDesertTemple1_vals;
	public static String componentModernDesertTemple2_string; public static ArrayList<Double> componentModernDesertTemple2_vals;
	public static String componentModernDesertToolSmith1_string; public static ArrayList<Double> componentModernDesertToolSmith1_vals;
	public static String componentModernDesertWeaponsmith1_string; public static ArrayList<Double> componentModernDesertWeaponsmith1_vals;
	public static String componentModernDesertStreetSubstitute1_string; public static ArrayList<Double> componentModernDesertStreetSubstitute1_vals;
	public static String componentModernDesertStreetSubstitute2_string; public static ArrayList<Double> componentModernDesertStreetSubstitute2_vals;
	public static String componentModernDesertStreetSubstitute3_string; public static ArrayList<Double> componentModernDesertStreetSubstitute3_vals;
	public static String componentModernDesertStreetSubstitute4_string; public static ArrayList<Double> componentModernDesertStreetSubstitute4_vals;
	
	public static String componentModernTaigaAnimalPen1_string; public static ArrayList<Double> componentModernTaigaAnimalPen1_vals;
	public static String componentModernTaigaArmorer2_string; public static ArrayList<Double> componentModernTaigaArmorer2_vals;
	public static String componentModernTaigaArmorerHouse1_string; public static ArrayList<Double> componentModernTaigaArmorerHouse1_vals;
	public static String componentModernTaigaButcherShop1_string; public static ArrayList<Double> componentModernTaigaButcherShop1_vals;
	public static String componentModernTaigaCartographerHouse1_string; public static ArrayList<Double> componentModernTaigaCartographerHouse1_vals;
	public static String componentModernTaigaFisherCottage1_string; public static ArrayList<Double> componentModernTaigaFisherCottage1_vals;
	public static String componentModernTaigaFletcherHouse1_string; public static ArrayList<Double> componentModernTaigaFletcherHouse1_vals;
	public static String componentModernTaigaLargeFarm1_string; public static ArrayList<Double> componentModernTaigaLargeFarm1_vals;
	public static String componentModernTaigaMediumFarm1_string; public static ArrayList<Double> componentModernTaigaMediumFarm1_vals;
	public static String componentModernTaigaLibrary1_string; public static ArrayList<Double> componentModernTaigaLibrary1_vals;
	public static String componentModernTaigaMasonsHouse1_string; public static ArrayList<Double> componentModernTaigaMasonsHouse1_vals;
	public static String componentModernTaigaMediumHouse1_string; public static ArrayList<Double> componentModernTaigaMediumHouse1_vals;
	public static String componentModernTaigaMediumHouse2_string; public static ArrayList<Double> componentModernTaigaMediumHouse2_vals;
	public static String componentModernTaigaMediumHouse3_string; public static ArrayList<Double> componentModernTaigaMediumHouse3_vals;
	public static String componentModernTaigaMediumHouse4_string; public static ArrayList<Double> componentModernTaigaMediumHouse4_vals;
	public static String componentModernTaigaShepherdsHouse1_string; public static ArrayList<Double> componentModernTaigaShepherdsHouse1_vals;
	public static String componentModernTaigaSmallFarm1_string; public static ArrayList<Double> componentModernTaigaSmallFarm1_vals;
	public static String componentModernTaigaSmallHouse1_string; public static ArrayList<Double> componentModernTaigaSmallHouse1_vals;
	public static String componentModernTaigaSmallHouse2_string; public static ArrayList<Double> componentModernTaigaSmallHouse2_vals;
	public static String componentModernTaigaSmallHouse3_string; public static ArrayList<Double> componentModernTaigaSmallHouse3_vals;
	public static String componentModernTaigaSmallHouse4_string; public static ArrayList<Double> componentModernTaigaSmallHouse4_vals;
	public static String componentModernTaigaSmallHouse5_string; public static ArrayList<Double> componentModernTaigaSmallHouse5_vals;
	public static String componentModernTaigaTannery1_string; public static ArrayList<Double> componentModernTaigaTannery1_vals;
	public static String componentModernTaigaTemple1_string; public static ArrayList<Double> componentModernTaigaTemple1_vals;
	public static String componentModernTaigaToolSmith1_string; public static ArrayList<Double> componentModernTaigaToolSmith1_vals;
	public static String componentModernTaigaWeaponsmith1_string; public static ArrayList<Double> componentModernTaigaWeaponsmith1_vals;
	public static String componentModernTaigaWeaponsmith2_string; public static ArrayList<Double> componentModernTaigaWeaponsmith2_vals;
	public static String componentModernTaigaStreetSubstitute1_string; public static ArrayList<Double> componentModernTaigaStreetSubstitute1_vals;

	public static String componentModernSavannaAnimalPen1_string; public static ArrayList<Double> componentModernSavannaAnimalPen1_vals;
	public static String componentModernSavannaAnimalPen2_string; public static ArrayList<Double> componentModernSavannaAnimalPen2_vals;
	public static String componentModernSavannaAnimalPen3_string; public static ArrayList<Double> componentModernSavannaAnimalPen3_vals;
	public static String componentModernSavannaArmorer1_string; public static ArrayList<Double> componentModernSavannaArmorer1_vals;
	public static String componentModernSavannaButchersShop1_string; public static ArrayList<Double> componentModernSavannaButchersShop1_vals;
	public static String componentModernSavannaButchersShop2_string; public static ArrayList<Double> componentModernSavannaButchersShop2_vals;
	public static String componentModernSavannaCartographer1_string; public static ArrayList<Double> componentModernSavannaCartographer1_vals;
	public static String componentModernSavannaFisherCottage1_string; public static ArrayList<Double> componentModernSavannaFisherCottage1_vals;
	public static String componentModernSavannaFletcherHouse1_string; public static ArrayList<Double> componentModernSavannaFletcherHouse1_vals;
	public static String componentModernSavannaLargeFarm1_string; public static ArrayList<Double> componentModernSavannaLargeFarm1_vals;
	public static String componentModernSavannaLargeFarm2_string; public static ArrayList<Double> componentModernSavannaLargeFarm2_vals;
	public static String componentModernSavannaLibrary1_string; public static ArrayList<Double> componentModernSavannaLibrary1_vals;
	public static String componentModernSavannaMason1_string; public static ArrayList<Double> componentModernSavannaMason1_vals;
	public static String componentModernSavannaMediumHouse1_string; public static ArrayList<Double> componentModernSavannaMediumHouse1_vals;
	public static String componentModernSavannaMediumHouse2_string; public static ArrayList<Double> componentModernSavannaMediumHouse2_vals;
	public static String componentModernSavannaShepherd1_string; public static ArrayList<Double> componentModernSavannaShepherd1_vals;
	public static String componentModernSavannaSmallFarm_string; public static ArrayList<Double> componentModernSavannaSmallFarm_vals;
	public static String componentModernSavannaSmallHouse1_string; public static ArrayList<Double> componentModernSavannaSmallHouse1_vals;
	public static String componentModernSavannaSmallHouse2_string; public static ArrayList<Double> componentModernSavannaSmallHouse2_vals;
	public static String componentModernSavannaSmallHouse3_string; public static ArrayList<Double> componentModernSavannaSmallHouse3_vals;
	public static String componentModernSavannaSmallHouse4_string; public static ArrayList<Double> componentModernSavannaSmallHouse4_vals;
	public static String componentModernSavannaSmallHouse5_string; public static ArrayList<Double> componentModernSavannaSmallHouse5_vals;
	public static String componentModernSavannaSmallHouse6_string; public static ArrayList<Double> componentModernSavannaSmallHouse6_vals;
	public static String componentModernSavannaSmallHouse7_string; public static ArrayList<Double> componentModernSavannaSmallHouse7_vals;
	public static String componentModernSavannaSmallHouse8_string; public static ArrayList<Double> componentModernSavannaSmallHouse8_vals;

	// Misc new village stuff
	public static boolean useModdedWoodenDoors;
	public static boolean spawnModdedVillagers;
	public static boolean useVillageColors;
	public static boolean spawnVillagersInResidences;
	public static boolean spawnVillagersInTownCenters;
	public static float harvestcraftCropFarmRate;
	public static float dragonQuestCropFarmRate;
	//public static boolean farmPumpkins;
	
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
		// --- New Villages --- //
		newVillageGenerator = config.getBoolean("Activate New Village Generator", "Village Generator", true, "Use replacement village generation system. You may need to deactivate village generation from other mods. All other settings in this section require this to be true.");
		newVillageSize = config.getInt("Village Size", "Village Generator", 1, 1, 10, "How large villages are. Vanilla is 1.");
		newVillageSpacingMedian = config.getInt("Village Spacing: Median", "Village Generator", 20, 1, 100, "Median distance between villages. Vanilla is 20.");
		newVillageSpacingSpread = config.getInt("Village Spacing: Range", "Village Generator", 12, 1, 100, "Variation in distances between villages. Must be lower than Median value. Vanilla is 12.");
		//farmPumpkins  = config.getBoolean("Pumpkin and Melon Crops", "Village Generator", true, "Farms can have pumpkins and melons generate in them");
		
		
		ArrayList<Double> ald; // For setting default values as integer lists
		
		// Legacy Village components
		ald = new ArrayList<Double>(Arrays.asList(0D,1D,2D,2D,4D));
		componentLegacyHouse4Garden_string = config.getString("Component: Legacy Small House", "Village Generator", convertDoubleArrayToString(ald), "Generation stats for this component in all villages. Vanilla weight is 4.0");
		componentLegacyHouse4Garden_vals = parseDoubleArray(componentLegacyHouse4Garden_string, ald);
		
		ald = new ArrayList<Double>(Arrays.asList(0D,1D,0D,1D,1D));
		componentLegacyChurch_string = config.getString("Component: Legacy Church", "Village Generator", convertDoubleArrayToString(ald), "Generation stats for this component in all villages. Vanilla weight is 20.0");
		componentLegacyChurch_vals = parseDoubleArray(componentLegacyChurch_string, ald);

		ald = new ArrayList<Double>(Arrays.asList(0D,1D,0D,1D,2D));
		componentLegacyHouse1_string = config.getString("Component: Legacy Library", "Village Generator", convertDoubleArrayToString(ald), "Generation stats for this component in all villages. Vanilla weight is 20.0");
		componentLegacyHouse1_vals = parseDoubleArray(componentLegacyHouse1_string, ald);

		ald = new ArrayList<Double>(Arrays.asList(0D,1D,2D,3D,5D));
		componentLegacyWoodHut_string = config.getString("Component: Legacy Hut", "Village Generator", convertDoubleArrayToString(ald), "Generation stats for this component in all villages. Vanilla weight is 3.0");
		componentLegacyWoodHut_vals = parseDoubleArray(componentLegacyWoodHut_string, ald);

		ald = new ArrayList<Double>(Arrays.asList(0D,1D,0D,1D,2D));
		componentLegacyHall_string = config.getString("Component: Legacy Butcher Shop", "Village Generator", convertDoubleArrayToString(ald), "Generation stats for this component in all villages. Vanilla weight is 15.0");
		componentLegacyHall_vals = parseDoubleArray(componentLegacyHall_string, ald);

		ald = new ArrayList<Double>(Arrays.asList(0D,1D,1D,1D,4D));
		componentLegacyField1_string = config.getString("Component: Legacy Large Farm", "Village Generator", convertDoubleArrayToString(ald), "Generation stats for this component in all villages. Vanilla weight is 3.0");
		componentLegacyField1_vals = parseDoubleArray(componentLegacyField1_string, ald);

		ald = new ArrayList<Double>(Arrays.asList(0D,1D,2D,2D,4D));
		componentLegacyField2_string = config.getString("Component: Legacy Small Farm", "Village Generator", convertDoubleArrayToString(ald), "Generation stats for this component in all villages. Vanilla weight is 3.0");
		componentLegacyField2_vals = parseDoubleArray(componentLegacyField2_string, ald);

		ald = new ArrayList<Double>(Arrays.asList(0D,0D,0D,1D,1D));
		componentLegacyHouse2_string = config.getString("Component: Legacy Smithy", "Village Generator", convertDoubleArrayToString(ald), "Generation stats for this component in all villages. Vanilla weight is 15.0");
		componentLegacyHouse2_vals = parseDoubleArray(componentLegacyHouse2_string, ald);

		ald = new ArrayList<Double>(Arrays.asList(0D,1D,0D,2D,3D));
		componentLegacyHouse3_string = config.getString("Component: Legacy Large House", "Village Generator", convertDoubleArrayToString(ald), "Generation stats for this component in all villages. Vanilla weight is 8.0");
		componentLegacyHouse3_vals = parseDoubleArray(componentLegacyHouse3_string, ald);
		
		// Modern Village components
		/*
		ArrayList<Double> modernDefaults = new ArrayList<Double>(Arrays.asList(
				(91D/9), //= 10.11111111111111
				((76D/91) * 9D/(152D/5)), //=  0.24725274725274726
				((23D/91) * 9D/(152D/5)), //= 0.07482648930017351
				((112D/91) * 9D/(152D/5)), //= 0.3643724696356275
				((184D/91) * 9D/(152D/5)) //= 0.5986119144013881
				)); // All modern buildings will default to these values
				*/
		ArrayList<Double> modernDefaults = new ArrayList<Double>(Arrays.asList(10D, 1D, 1D, 2D, 3D)); // Placeholder to ensure spawn
		ArrayList<Double> modifiedDefaults = new ArrayList<Double>(Arrays.asList(0D, 0D, 0D, 0D, 0D));
		int plainsHouses = 36;
		int desertHouses = 28;
		int taigaHouses = 27;
		double plainsDecorToHouseRatio = 19D/13D;
		double desertDecorToHouseRatio = 12D/17D;
		double desertStreetToHouseRatio = 15D/17D; // 17 house attachments and 28 street attachments across 11 streets and 2 street endcaps = (28-(11+2))/17 = 15/17
		double desertCrossroadsDecorStreetFraction = 1D/(13D); // 11 streets and 2 endcaps		modifiedDefaults.set(0, modernDefaults.get(0)*9D * desertDecorToHouseRatio); 
		double taigaDecorToHouseRatio = 30D/18D;
		
		// Plains components
		componentModernPlainsAccessory1_string = config.getString("Component: Modern Plains Flower Planter", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsAccessory1_vals = parseDoubleArray(componentModernPlainsAccessory1_string, modernDefaults);
		
		componentModernPlainsAnimalPen1_string = config.getString("Component: Modern Plains Small Animal Pen", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsAnimalPen1_vals = parseDoubleArray(componentModernPlainsAnimalPen1_string, modernDefaults);

		componentModernPlainsAnimalPen2_string = config.getString("Component: Modern Plains Large Animal Pen", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsAnimalPen2_vals = parseDoubleArray(componentModernPlainsAnimalPen2_string, modernDefaults);

		componentModernPlainsAnimalPen3_string = config.getString("Component: Modern Plains Decorated Animal Pen", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsAnimalPen3_vals = parseDoubleArray(componentModernPlainsAnimalPen3_string, modernDefaults);

		componentModernPlainsArmorerHouse1_string = config.getString("Component: Modern Plains Armorer House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsArmorerHouse1_vals = parseDoubleArray(componentModernPlainsArmorerHouse1_string, modernDefaults);
		
		componentModernPlainsBigHouse1_string = config.getString("Component: Modern Plains Large House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsBigHouse1_vals = parseDoubleArray(componentModernPlainsBigHouse1_string, modernDefaults);

		componentModernPlainsButcherShop1_string = config.getString("Component: Modern Plains Small Butcher Shop", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsButcherShop1_vals = parseDoubleArray(componentModernPlainsButcherShop1_string, modernDefaults);
		
		componentModernPlainsButcherShop2_string = config.getString("Component: Modern Plains Large Butcher Shop", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsButcherShop2_vals = parseDoubleArray(componentModernPlainsButcherShop2_string, modernDefaults);
		
		componentModernPlainsCartographer1_string = config.getString("Component: Modern Plains Cartographer House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsCartographer1_vals = parseDoubleArray(componentModernPlainsCartographer1_string, modernDefaults);
		
		componentModernPlainsFisherCottage1_string = config.getString("Component: Modern Plains Fisher Cottage", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsFisherCottage1_vals = parseDoubleArray(componentModernPlainsFisherCottage1_string, modernDefaults);
		
		componentModernPlainsFletcherHouse1_string = config.getString("Component: Modern Plains Fletcher House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsFletcherHouse1_vals = parseDoubleArray(componentModernPlainsFletcherHouse1_string, modernDefaults);
		
		componentModernPlainsLargeFarm1_string = config.getString("Component: Modern Plains Large Farm", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsLargeFarm1_vals = parseDoubleArray(componentModernPlainsLargeFarm1_string, modernDefaults);
		
		componentModernPlainsLibrary1_string = config.getString("Component: Modern Plains Large Library", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsLibrary1_vals = parseDoubleArray(componentModernPlainsLibrary1_string, modernDefaults);
		
		componentModernPlainsLibrary2_string = config.getString("Component: Modern Plains Small Library", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsLibrary2_vals = parseDoubleArray(componentModernPlainsLibrary2_string, modernDefaults);
		
		componentModernPlainsMasonsHouse1_string = config.getString("Component: Modern Plains Mason House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsMasonsHouse1_vals = parseDoubleArray(componentModernPlainsMasonsHouse1_string, modernDefaults);
		
		componentModernPlainsMediumHouse1_string = config.getString("Component: Modern Plains Medium House 1", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsMediumHouse1_vals = parseDoubleArray(componentModernPlainsMediumHouse1_string, modernDefaults);
		
		componentModernPlainsMediumHouse2_string = config.getString("Component: Modern Plains Medium House 2", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsMediumHouse2_vals = parseDoubleArray(componentModernPlainsMediumHouse2_string, modernDefaults);
		
		componentModernPlainsMeetingPoint4_string = config.getString("Component: Modern Plains Large Market", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsMeetingPoint4_vals = parseDoubleArray(componentModernPlainsMeetingPoint4_string, modernDefaults);
		
		componentModernPlainsMeetingPoint5_string = config.getString("Component: Modern Plains Small Market", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsMeetingPoint5_vals = parseDoubleArray(componentModernPlainsMeetingPoint5_string, modernDefaults);
		
		componentModernPlainsShepherdsHouse1_string = config.getString("Component: Modern Plains Shepherd House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsShepherdsHouse1_vals = parseDoubleArray(componentModernPlainsShepherdsHouse1_string, modernDefaults);
		
		componentModernPlainsSmallFarm1_string = config.getString("Component: Modern Plains Small Farm", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsSmallFarm1_vals = parseDoubleArray(componentModernPlainsSmallFarm1_string, modernDefaults);
		
		componentModernPlainsSmallHouse1_string = config.getString("Component: Modern Plains Small House 1", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsSmallHouse1_vals = parseDoubleArray(componentModernPlainsSmallHouse1_string, modernDefaults);
		
		componentModernPlainsSmallHouse2_string = config.getString("Component: Modern Plains Small House 2", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsSmallHouse2_vals = parseDoubleArray(componentModernPlainsSmallHouse2_string, modernDefaults);
		
		componentModernPlainsSmallHouse3_string = config.getString("Component: Modern Plains Small House 3", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsSmallHouse3_vals = parseDoubleArray(componentModernPlainsSmallHouse3_string, modernDefaults);
		
		componentModernPlainsSmallHouse4_string = config.getString("Component: Modern Plains Small House 4", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsSmallHouse4_vals = parseDoubleArray(componentModernPlainsSmallHouse4_string, modernDefaults);
		
		componentModernPlainsSmallHouse5_string = config.getString("Component: Modern Plains Small House 5", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsSmallHouse5_vals = parseDoubleArray(componentModernPlainsSmallHouse5_string, modernDefaults);
		
		componentModernPlainsSmallHouse6_string = config.getString("Component: Modern Plains Small House 6", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsSmallHouse6_vals = parseDoubleArray(componentModernPlainsSmallHouse6_string, modernDefaults);
		
		componentModernPlainsSmallHouse7_string = config.getString("Component: Modern Plains Small House 7", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsSmallHouse7_vals = parseDoubleArray(componentModernPlainsSmallHouse7_string, modernDefaults);
		
		componentModernPlainsSmallHouse8_string = config.getString("Component: Modern Plains Small House 8", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsSmallHouse8_vals = parseDoubleArray(componentModernPlainsSmallHouse8_string, modernDefaults);
		
		componentModernPlainsStable1_string = config.getString("Component: Modern Plains Cobblestone Stable", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsStable1_vals = parseDoubleArray(componentModernPlainsStable1_string, modernDefaults);
		
		componentModernPlainsStable2_string = config.getString("Component: Modern Plains Terracotta Stable", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsStable2_vals = parseDoubleArray(componentModernPlainsStable2_string, modernDefaults);
		
		componentModernPlainsTannery1_string = config.getString("Component: Modern Plains Tannery", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsTannery1_vals = parseDoubleArray(componentModernPlainsTannery1_string, modernDefaults);
		
		componentModernPlainsTemple3_string = config.getString("Component: Modern Plains Terracotta Temple", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsTemple3_vals = parseDoubleArray(componentModernPlainsTemple3_string, modernDefaults);
		
		componentModernPlainsTemple4_string = config.getString("Component: Modern Plains Cobblestone Temple", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsTemple4_vals = parseDoubleArray(componentModernPlainsTemple4_string, modernDefaults);
		
		componentModernPlainsToolSmith1_string = config.getString("Component: Modern Plains Tool Smithy", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsToolSmith1_vals = parseDoubleArray(componentModernPlainsToolSmith1_string, modernDefaults);
		
		componentModernPlainsWeaponsmith1_string = config.getString("Component: Modern Plains Weapon Smithy", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsWeaponsmith1_vals = parseDoubleArray(componentModernPlainsWeaponsmith1_string, modernDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * plainsDecorToHouseRatio); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*plainsHouses * plainsDecorToHouseRatio);}
		componentModernPlainsStreetSubstitute1_string = config.getString("Component: Modern Plains Road Decor", "Village Generator", convertDoubleArrayToString(modifiedDefaults), "Generation stats for this component in plains villages");
		componentModernPlainsStreetSubstitute1_vals = parseDoubleArray(componentModernPlainsStreetSubstitute1_string, modifiedDefaults);
		
		// Desert components
		componentModernDesertAnimalPen1_string = config.getString("Component: Modern Desert Small Animal Pen", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertAnimalPen1_vals = parseDoubleArray(componentModernDesertAnimalPen1_string, modernDefaults);
		
		componentModernDesertAnimalPen2_string = config.getString("Component: Modern Desert Covered Animal Pen", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertAnimalPen2_vals = parseDoubleArray(componentModernDesertAnimalPen2_string, modernDefaults);
		
		componentModernDesertArmorer1_string = config.getString("Component: Modern Desert Armorer House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertArmorer1_vals = parseDoubleArray(componentModernDesertArmorer1_string, modernDefaults);
		
		componentModernDesertButcherShop1_string = config.getString("Component: Modern Desert Butcher Shop", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertButcherShop1_vals = parseDoubleArray(componentModernDesertButcherShop1_string, modernDefaults);
		
		componentModernDesertCartographerHouse1_string = config.getString("Component: Modern Desert Cartographer House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertCartographerHouse1_vals = parseDoubleArray(componentModernDesertCartographerHouse1_string, modernDefaults);
		
		componentModernDesertFarm1_string = config.getString("Component: Modern Desert Small Farm", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertFarm1_vals = parseDoubleArray(componentModernDesertFarm1_string, modernDefaults);
		
		componentModernDesertFarm2_string = config.getString("Component: Modern Desert Medium Farm", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertFarm2_vals = parseDoubleArray(componentModernDesertFarm2_string, modernDefaults);
		
		componentModernDesertFisher1_string = config.getString("Component: Modern Desert Fisher Cottage", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertFisher1_vals = parseDoubleArray(componentModernDesertFisher1_string, modernDefaults);
		
		componentModernDesertFletcherHouse1_string = config.getString("Component: Modern Desert Fletcher House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertFletcherHouse1_vals = parseDoubleArray(componentModernDesertFletcherHouse1_string, modernDefaults);
		
		componentModernDesertLargeFarm1_string = config.getString("Component: Modern Desert Large Farm", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertLargeFarm1_vals = parseDoubleArray(componentModernDesertLargeFarm1_string, modernDefaults);
		
		componentModernDesertLibrary1_string = config.getString("Component: Modern Desert Library", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertLibrary1_vals = parseDoubleArray(componentModernDesertLibrary1_string, modernDefaults);
		
		componentModernDesertMason1_string = config.getString("Component: Modern Desert Mason House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertMason1_vals = parseDoubleArray(componentModernDesertMason1_string, modernDefaults);
		
		componentModernDesertMediumHouse1_string = config.getString("Component: Modern Desert Medium House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertMediumHouse1_vals = parseDoubleArray(componentModernDesertMediumHouse1_string, modernDefaults);
		
		componentModernDesertMediumHouse2_string = config.getString("Component: Modern Desert Large House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertMediumHouse2_vals = parseDoubleArray(componentModernDesertMediumHouse2_string, modernDefaults);
		
		componentModernDesertShepherdHouse1_string = config.getString("Component: Modern Desert Shepherd House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertShepherdHouse1_vals = parseDoubleArray(componentModernDesertShepherdHouse1_string, modernDefaults);
		
		componentModernDesertSmallHouse1_string = config.getString("Component: Modern Desert Small House 1", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertSmallHouse1_vals = parseDoubleArray(componentModernDesertSmallHouse1_string, modernDefaults);
		
		componentModernDesertSmallHouse2_string = config.getString("Component: Modern Desert Small House 2", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertSmallHouse2_vals = parseDoubleArray(componentModernDesertSmallHouse2_string, modernDefaults);
		
		componentModernDesertSmallHouse3_string = config.getString("Component: Modern Desert Small House 3", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertSmallHouse3_vals = parseDoubleArray(componentModernDesertSmallHouse3_string, modernDefaults);
		
		componentModernDesertSmallHouse4_string = config.getString("Component: Modern Desert Small House 4", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertSmallHouse4_vals = parseDoubleArray(componentModernDesertSmallHouse4_string, modernDefaults);
		
		componentModernDesertSmallHouse5_string = config.getString("Component: Modern Desert Small House 5", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertSmallHouse5_vals = parseDoubleArray(componentModernDesertSmallHouse5_string, modernDefaults);
		
		componentModernDesertSmallHouse6_string = config.getString("Component: Modern Desert Small House 6", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertSmallHouse6_vals = parseDoubleArray(componentModernDesertSmallHouse6_string, modernDefaults);
		
		componentModernDesertSmallHouse7_string = config.getString("Component: Modern Desert Small House 7", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertSmallHouse7_vals = parseDoubleArray(componentModernDesertSmallHouse7_string, modernDefaults);
		
		componentModernDesertSmallHouse8_string = config.getString("Component: Modern Desert Small House 8", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertSmallHouse8_vals = parseDoubleArray(componentModernDesertSmallHouse8_string, modernDefaults);
		
		componentModernDesertTannery1_string = config.getString("Component: Modern Desert Tannery", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertTannery1_vals = parseDoubleArray(componentModernDesertTannery1_string, modernDefaults);
		
		componentModernDesertTemple1_string = config.getString("Component: Modern Desert Temple 1", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertTemple1_vals = parseDoubleArray(componentModernDesertTemple1_string, modernDefaults);
		
		componentModernDesertTemple2_string = config.getString("Component: Modern Desert Temple 2", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertTemple2_vals = parseDoubleArray(componentModernDesertTemple2_string, modernDefaults);
		
		componentModernDesertToolSmith1_string = config.getString("Component: Modern Desert Tool Smithy", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertToolSmith1_vals = parseDoubleArray(componentModernDesertToolSmith1_string, modernDefaults);
		
		componentModernDesertWeaponsmith1_string = config.getString("Component: Modern Desert Weapon Smithy", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in desert villages");
		componentModernDesertWeaponsmith1_vals = parseDoubleArray(componentModernDesertWeaponsmith1_string, modernDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * desertDecorToHouseRatio); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*desertHouses * desertDecorToHouseRatio);}
		componentModernDesertStreetSubstitute1_string = config.getString("Component: Modern Desert Road Decor 1", "Village Generator", convertDoubleArrayToString(modifiedDefaults), "Generation stats for this component in desert villages");
		componentModernDesertStreetSubstitute1_vals = parseDoubleArray(componentModernDesertStreetSubstitute1_string, modifiedDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * desertStreetToHouseRatio * desertCrossroadsDecorStreetFraction); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*desertHouses * desertStreetToHouseRatio * desertCrossroadsDecorStreetFraction);}
		componentModernDesertStreetSubstitute2_string = config.getString("Component: Modern Desert Road Terracotta Accent 1", "Village Generator", convertDoubleArrayToString(modifiedDefaults), "Generation stats for this component in desert villages");
		componentModernDesertStreetSubstitute2_vals = parseDoubleArray(componentModernDesertStreetSubstitute2_string, modifiedDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * desertStreetToHouseRatio * desertCrossroadsDecorStreetFraction); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*desertHouses * desertStreetToHouseRatio * desertCrossroadsDecorStreetFraction);}
		componentModernDesertStreetSubstitute3_string = config.getString("Component: Modern Desert Road Terracotta Accent 2", "Village Generator", convertDoubleArrayToString(modifiedDefaults), "Generation stats for this component in desert villages");
		componentModernDesertStreetSubstitute3_vals = parseDoubleArray(componentModernDesertStreetSubstitute3_string, modifiedDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * desertStreetToHouseRatio * desertCrossroadsDecorStreetFraction); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*desertHouses * desertStreetToHouseRatio * desertCrossroadsDecorStreetFraction);}
		componentModernDesertStreetSubstitute4_string = config.getString("Component: Modern Desert Road Decor 2", "Village Generator", convertDoubleArrayToString(modifiedDefaults), "Generation stats for this component in desert villages");
		componentModernDesertStreetSubstitute4_vals = parseDoubleArray(componentModernDesertStreetSubstitute4_string, modifiedDefaults);
		
		// Taiga components
		componentModernTaigaAnimalPen1_string = config.getString("Component: Modern Taiga Animal Pen", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaAnimalPen1_vals = parseDoubleArray(componentModernTaigaAnimalPen1_string, modernDefaults);
		
		componentModernTaigaArmorer2_string = config.getString("Component: Modern Taiga Armorer Station", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaArmorer2_vals = parseDoubleArray(componentModernTaigaArmorer2_string, modernDefaults);
		
		componentModernTaigaArmorerHouse1_string = config.getString("Component: Modern Taiga Armorer House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaArmorerHouse1_vals = parseDoubleArray(componentModernTaigaArmorerHouse1_string, modernDefaults);
		
		componentModernTaigaButcherShop1_string = config.getString("Component: Modern Taiga Butcher Shop", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaButcherShop1_vals = parseDoubleArray(componentModernTaigaButcherShop1_string, modernDefaults);
		
		componentModernTaigaCartographerHouse1_string = config.getString("Component: Modern Taiga Cartographer House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaCartographerHouse1_vals = parseDoubleArray(componentModernTaigaCartographerHouse1_string, modernDefaults);
		
		componentModernTaigaFisherCottage1_string = config.getString("Component: Modern Taiga Fisher Cottage", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaFisherCottage1_vals = parseDoubleArray(componentModernTaigaFisherCottage1_string, modernDefaults);
		
		componentModernTaigaFletcherHouse1_string = config.getString("Component: Modern Taiga Fletcher House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaFletcherHouse1_vals = parseDoubleArray(componentModernTaigaFletcherHouse1_string, modernDefaults);
		
		componentModernTaigaLargeFarm1_string = config.getString("Component: Modern Taiga Large Farm", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaLargeFarm1_vals = parseDoubleArray(componentModernTaigaLargeFarm1_string, modernDefaults);
		
		componentModernTaigaMediumFarm1_string = config.getString("Component: Modern Taiga Medium Farm", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaMediumFarm1_vals = parseDoubleArray(componentModernTaigaMediumFarm1_string, modernDefaults);
		
		componentModernTaigaLibrary1_string = config.getString("Component: Modern Taiga Library", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaLibrary1_vals = parseDoubleArray(componentModernTaigaLibrary1_string, modernDefaults);
		
		componentModernTaigaMasonsHouse1_string = config.getString("Component: Modern Taiga Mason House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaMasonsHouse1_vals = parseDoubleArray(componentModernTaigaMasonsHouse1_string, modernDefaults);
		
		componentModernTaigaMediumHouse1_string = config.getString("Component: Modern Taiga Medium House 1", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaMediumHouse1_vals = parseDoubleArray(componentModernTaigaMediumHouse1_string, modernDefaults);
		
		componentModernTaigaMediumHouse2_string = config.getString("Component: Modern Taiga Medium House 2", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaMediumHouse2_vals = parseDoubleArray(componentModernTaigaMediumHouse2_string, modernDefaults);
		
		componentModernTaigaMediumHouse3_string = config.getString("Component: Modern Taiga Medium House 3", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaMediumHouse3_vals = parseDoubleArray(componentModernTaigaMediumHouse3_string, modernDefaults);
		
		componentModernTaigaMediumHouse4_string = config.getString("Component: Modern Taiga Medium House 4", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaMediumHouse4_vals = parseDoubleArray(componentModernTaigaMediumHouse4_string, modernDefaults);
		
		componentModernTaigaShepherdsHouse1_string = config.getString("Component: Modern Taiga Shepherd House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaShepherdsHouse1_vals = parseDoubleArray(componentModernTaigaShepherdsHouse1_string, modernDefaults);
		
		componentModernTaigaSmallFarm1_string = config.getString("Component: Modern Taiga Small Farm", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaSmallFarm1_vals = parseDoubleArray(componentModernTaigaSmallFarm1_string, modernDefaults);
		
		componentModernTaigaSmallHouse1_string = config.getString("Component: Modern Taiga Small House 1", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaSmallHouse1_vals = parseDoubleArray(componentModernTaigaSmallHouse1_string, modernDefaults);
		
		componentModernTaigaSmallHouse2_string = config.getString("Component: Modern Taiga Small House 2", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaSmallHouse2_vals = parseDoubleArray(componentModernTaigaSmallHouse2_string, modernDefaults);
		
		componentModernTaigaSmallHouse3_string = config.getString("Component: Modern Taiga Small House 3", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaSmallHouse3_vals = parseDoubleArray(componentModernTaigaSmallHouse3_string, modernDefaults);
		
		componentModernTaigaSmallHouse4_string = config.getString("Component: Modern Taiga Small House 4", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaSmallHouse4_vals = parseDoubleArray(componentModernTaigaSmallHouse4_string, modernDefaults);
		
		componentModernTaigaSmallHouse5_string = config.getString("Component: Modern Taiga Small House 5", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaSmallHouse5_vals = parseDoubleArray(componentModernTaigaSmallHouse5_string, modernDefaults);
		
		componentModernTaigaTannery1_string = config.getString("Component: Modern Taiga Tannery", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaTannery1_vals = parseDoubleArray(componentModernTaigaTannery1_string, modernDefaults);
		
		componentModernTaigaTemple1_string = config.getString("Component: Modern Taiga Temple", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaTemple1_vals = parseDoubleArray(componentModernTaigaTemple1_string, modernDefaults);
		
		componentModernTaigaToolSmith1_string = config.getString("Component: Modern Taiga Tool Smithy", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaToolSmith1_vals = parseDoubleArray(componentModernTaigaToolSmith1_string, modernDefaults);
		
		componentModernTaigaWeaponsmith1_string = config.getString("Component: Modern Taiga Weapon Smith House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaWeaponsmith1_vals = parseDoubleArray(componentModernTaigaWeaponsmith1_string, modernDefaults);
		
		componentModernTaigaWeaponsmith2_string = config.getString("Component: Modern Taiga Weapon Smith Station", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaWeaponsmith2_vals = parseDoubleArray(componentModernTaigaWeaponsmith2_string, modernDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * taigaDecorToHouseRatio); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*taigaHouses * taigaDecorToHouseRatio);}
		componentModernTaigaStreetSubstitute1_string = config.getString("Component: Modern Taiga Road Decor", "Village Generator", convertDoubleArrayToString(modifiedDefaults), "Generation stats for this component in taiga villages");
		componentModernTaigaStreetSubstitute1_vals = parseDoubleArray(componentModernTaigaStreetSubstitute1_string, modifiedDefaults);
		
		// Savanna components
		componentModernSavannaAnimalPen1_string = config.getString("Component: Modern Savanna Covered Animal Pen", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaAnimalPen1_vals = parseDoubleArray(componentModernSavannaAnimalPen1_string, modernDefaults);
		
		componentModernSavannaAnimalPen2_string = config.getString("Component: Modern Savanna Large Animal Pen", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaAnimalPen2_vals = parseDoubleArray(componentModernSavannaAnimalPen2_string, modernDefaults);
		
		componentModernSavannaAnimalPen3_string = config.getString("Component: Modern Savanna Medium Animal Pen", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaAnimalPen3_vals = parseDoubleArray(componentModernSavannaAnimalPen3_string, modernDefaults);
		
		componentModernSavannaArmorer1_string = config.getString("Component: Modern Savanna Armorer House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaArmorer1_vals = parseDoubleArray(componentModernSavannaArmorer1_string, modernDefaults);
		
		componentModernSavannaButchersShop1_string = config.getString("Component: Modern Savanna Butcher Shop 1", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaButchersShop1_vals = parseDoubleArray(componentModernSavannaButchersShop1_string, modernDefaults);
		
		componentModernSavannaButchersShop2_string = config.getString("Component: Modern Savanna Butcher Shop 2", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaButchersShop2_vals = parseDoubleArray(componentModernSavannaButchersShop2_string, modernDefaults);
		
		componentModernSavannaCartographer1_string = config.getString("Component: Modern Savanna Cartographer House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaCartographer1_vals = parseDoubleArray(componentModernSavannaCartographer1_string, modernDefaults);
		
		componentModernSavannaFisherCottage1_string = config.getString("Component: Modern Savanna Fisher Cottage", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaFisherCottage1_vals = parseDoubleArray(componentModernSavannaFisherCottage1_string, modernDefaults);
		
		componentModernSavannaFletcherHouse1_string = config.getString("Component: Modern Savanna Fletcher House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaFletcherHouse1_vals = parseDoubleArray(componentModernSavannaFletcherHouse1_string, modernDefaults);
		
		componentModernSavannaLargeFarm1_string = config.getString("Component: Modern Savanna Methodical Farm", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaLargeFarm1_vals = parseDoubleArray(componentModernSavannaLargeFarm1_string, modernDefaults);
		
		componentModernSavannaLargeFarm2_string = config.getString("Component: Modern Savanna Haphazard Farm", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaLargeFarm2_vals = parseDoubleArray(componentModernSavannaLargeFarm2_string, modernDefaults);
		
		componentModernSavannaLibrary1_string = config.getString("Component: Modern Savanna Library", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaLibrary1_vals = parseDoubleArray(componentModernSavannaLibrary1_string, modernDefaults);
		
		componentModernSavannaMason1_string = config.getString("Component: Modern Savanna Mason House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaMason1_vals = parseDoubleArray(componentModernSavannaMason1_string, modernDefaults);
		
		componentModernSavannaMediumHouse1_string = config.getString("Component: Modern Savanna Medium House 1", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaMediumHouse1_vals = parseDoubleArray(componentModernSavannaMediumHouse1_string, modernDefaults);
		
		componentModernSavannaMediumHouse2_string = config.getString("Component: Modern Savanna Medium House 2", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaMediumHouse2_vals = parseDoubleArray(componentModernSavannaMediumHouse2_string, modernDefaults);
		
		componentModernSavannaShepherd1_string = config.getString("Component: Modern Savanna Shepherd House", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaShepherd1_vals = parseDoubleArray(componentModernSavannaShepherd1_string, modernDefaults);
		
		componentModernSavannaSmallFarm_string = config.getString("Component: Modern Savanna Small Farm", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaSmallFarm_vals = parseDoubleArray(componentModernSavannaSmallFarm_string, modernDefaults);
		
		componentModernSavannaSmallHouse1_string = config.getString("Component: Modern Savanna Small House 1", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaSmallHouse1_vals = parseDoubleArray(componentModernSavannaSmallHouse1_string, modernDefaults);
		
		componentModernSavannaSmallHouse2_string = config.getString("Component: Modern Savanna Small House 2", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaSmallHouse2_vals = parseDoubleArray(componentModernSavannaSmallHouse2_string, modernDefaults);
		
		componentModernSavannaSmallHouse3_string = config.getString("Component: Modern Savanna Small House 3", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaSmallHouse3_vals = parseDoubleArray(componentModernSavannaSmallHouse3_string, modernDefaults);
		
		componentModernSavannaSmallHouse4_string = config.getString("Component: Modern Savanna Small House 4", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaSmallHouse4_vals = parseDoubleArray(componentModernSavannaSmallHouse4_string, modernDefaults);
		
		componentModernSavannaSmallHouse5_string = config.getString("Component: Modern Savanna Small House 5", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaSmallHouse5_vals = parseDoubleArray(componentModernSavannaSmallHouse5_string, modernDefaults);
		
		componentModernSavannaSmallHouse6_string = config.getString("Component: Modern Savanna Small House 6", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaSmallHouse6_vals = parseDoubleArray(componentModernSavannaSmallHouse6_string, modernDefaults);
		
		componentModernSavannaSmallHouse7_string = config.getString("Component: Modern Savanna Small House 7", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaSmallHouse7_vals = parseDoubleArray(componentModernSavannaSmallHouse7_string, modernDefaults);
		
		componentModernSavannaSmallHouse8_string = config.getString("Component: Modern Savanna Small House 8", "Village Generator", convertDoubleArrayToString(modernDefaults), "Generation stats for this component in savanna villages");
		componentModernSavannaSmallHouse8_vals = parseDoubleArray(componentModernSavannaSmallHouse8_string, modernDefaults);
		
		
		// Misc
		useModdedWoodenDoors = config.getBoolean("Use modded wooden doors in mod structures", "Village Generator", true, "Set this to false to use the vanilla 1.7 wooden doors, even if supported mod doors are found. Some villagers have trouble opening some modded doors.");
		spawnModdedVillagers = config.getBoolean("Allow mod villagers in new structures", "Village Generator", false, "When modern structures spawn random villagers on generation, set this to true to allow non-vanilla professions.");
		useVillageColors = config.getBoolean("Use village colors", "Village Generator", true, "Whether to apply the village's colors to concrete, terracotta, carpet, etc.");
		spawnVillagersInResidences = config.getBoolean("Spawn Villagers in Residences", "Village Generator", false, "Spawn villagers with random professions and ages in non-job-specific residential houses.");
		spawnVillagersInTownCenters = config.getBoolean("Spawn Villagers in Town Centers", "Village Generator", true, "Spawn villagers with random professions and ages in the town center.");
		
		spawnBiomesNames = config.getStringList("Spawn Biome Names", "Village Generator",
				new String[] {
						// Vanilla
						"Plains",
						"Desert",
						"Extreme Hills",
						"Forest",
						"Taiga",
						"Swampland",
						"Ice Plains",
						"MushroomIsland",
						"ForestHills",
						"TaigaHills",
						"Jungle",
						"JungleHills",
						"Birch Forest",
						"Birch Forest Hills",
						"Roofed Forest",
						"Cold Taiga",
						"Mega Taiga",
						"Mega Taiga Hills",
						"Savanna",
						"Mesa",
						"Sunflower Plains",
						"Flower Forest",
						"Mega Spruce Taiga",
						"Mega Spruce Taiga Hill",
						// Biomes o' Plenty
						"Bamboo Forest",
						"Bayou",
						"Bog",
						"Boreal Forest",
						"Canyon",
						"Chaparral",
						"Cherry Blossom Grove",
						"Coniferous Forest",
						"Snowy Coniferous Forest",
						"Deciduous Forest",
						"Dense Forest",
						"Eucalyptus Forest",
						"Flower Field",
						"Frost Forest",
						"Fungi Forest",
						"Garden",
						"Grassland",
						"Grove",
						"Heathland",
						"Highland",
						"Lavender Fields",
						"Lush Swamp",
						"Maple Woods",
						"Meadow",
						"Mountain",
						"Mystic Grove",
						"Orchard",
						"Outback",
						"Prairie",
						"Rainforest",
						"Redwood Forest",
						"Sacred Springs",
						"Seasonal Forest",
						"Shield",
						"Shrubland",
						"Steppe",
						"Temperate Rainforest",
						"Tropical Rainforest",
						"Tundra",
						"Wetland",
						"Woodland",
						"Xeric Shrubland",
						"Meadow Forest",
						"Oasis",
						"Scrubland",
						"Seasonal Forest Clearing",
						"Spruce Woods",
						// ATG
						"Rocky Steppe",
						// ExtrabiomeXL
						"Autumn Woods",
						"Forested Hills",
						"Green Hills",
						"Mini Jungle",
						"Mountain Taiga",
						"Pine Forest",
						"Redwood Lush",
						"Snowy Forest",
						"Snowy Rainforest",
						"Woodlands",
						// Highlands
						"Autumn Forest",
						"Badlands",
						"Birch Hills",
						"Highlands",
						"Lowlands",
						"Outback",
						"Pinelands",
						"Sahel",
						"Tall Pine Forest",
						"Tropics",
						},
				"Names of biomes which can spawn villages. Only used with Village Generator, and only applies to Overworld. Note that this list is EXCLUSIVE: other mod configs won't override this. You have to paste all biome names here.");
		
		// --- General --- //
	    nameSign = config.getBoolean("Name Sign", Configuration.CATEGORY_GENERAL, true, "Town centers display their name on one or more signs.");
		wellBoundary = config.getBoolean("Well boundary", Configuration.CATEGORY_GENERAL, true, "Whether to surround the well with colored blocks");
	    wellSlabs = config.getBoolean("Well slabs", Configuration.CATEGORY_GENERAL, true, "Replace the cobblestone rims of wells with stone slabs, making it easier for players and villagers to escape if they fall in.");
	    villageBanners = config.getBoolean("Village Banner", Configuration.CATEGORY_GENERAL, true, "The town banner pattern is displayed at the town center. You must be using Et Futurum or Gany's Surface with banners enabled.");
	    signYaw = config.getInt("Sign Yaw", Configuration.CATEGORY_GENERAL, 3, 0, 4, "If Village Banner is enabled: Degree to which well signs and banners should face inward. At 0 they face directly outward away from the well; at 4 they face each other.");
	    
		
		recordStructureCoords = config.getBoolean("Record Structure Coords", Configuration.CATEGORY_GENERAL, true, "Books generated by villagers or the Codex record the structure's coordinates.");
	    villagerDropBook = config.getBoolean("Villager drops book", Configuration.CATEGORY_GENERAL, false, "Village books are dropped by the villager rather than going directly into your inventory.");
	    villagerSellsCodex = config.getBoolean("Villager makes codex", Configuration.CATEGORY_GENERAL, true, "Librarian villagers will give you a codex if you right-click them while holding emerald, iron ingots, and/or gold ingots.");
	    
	    
	    
	    wellDecorations = config.getBoolean("Allow well decorations", "Well Kill Switch", true, "Set this to false to override-disable all well decoration: sign, slabs, terracotta, concrete.");
	    
	    addConcrete = config.getBoolean("1.12 Blocks", "World of Color", true, "Whether to add 1.12 style Concrete, Concrete Powder, and Glazed Terracotta");
	    concreteWell = config.getBoolean("Concrete Well", "World of Color", true, "Whether to decorate wells with Concrete and Glazed Terracotta instead of stained clay");
	    
	    
	    
	    //---------------Professions-----------------//
	    villagerCareers = config.getBoolean("Villager Careers", "villager professions", true, "Assign 1.8+ Career subdivisions to vanilla Professions. Trading is still 1.7 style, but merchant offers will be more progression-based. "
	    		+ "\nWARNING: This will permanently modify already-generated vanilla Villager trade offers.");
	    treasureTrades = config.getBoolean("Treasure Trades", "villager professions", true, "High-level Librarians and Cartographers will offer enchanted books and treasures in exchange for " + Reference.MOD_NAME + " items."
	    		+ " This only applies if Villager Careers is true.");
	    //villagerLegacyTrades = config.getBoolean("Villager Legacy Trades", "villager professions", true, "When using Villager Careers: some trades that were removed in 1.8 can still be unlocked.");
	    
	    // Changed in 3.1
	    modernVillagerSkins = config.getBoolean("Modern Villager Profession Skins", "villager professions", true, "Use the composite 1.14 Villager skins");
	    // Added in v3.2.3
	    modernZombieSkins = config.getBoolean("Modern Zombie Profession Skins", "villager professions", true, "Use the composite 1.14 Zombie skins");
	    // Added in v3.1
	    modernVillagerTrades = config.getBoolean("Modern Villager Trades", "villager professions", true, "Use JE 1.14 / BE 1.12 trade offerings and add the Mason villager");
	    enableNitwit = config.getBoolean("Nitwit Villager", "villager professions", true, "Enable 1.11 NitWit Villagers");
	    
	    // Added in v3.1.1
	    moddedVillagerHeadwear = config.getBoolean("Modded Villager Headwear", "villager professions", false, "If modern skins are enabled: renders the headwear layer for non-vanilla villager professions, if one exists.");
	    
	    removeMobArmor = config.getBoolean("Remove Armor for Modern Skins", "villager professions", true, "If modern skins are enabled: automatically removes armor from villagers and zombies to avoid a rendering bug.");
	    
	    // Added in v3.2
	    moddedVillagerHeadwearGraylist = config.getStringList("Modded Villager Headwear Graylist", "villager professions", new String[]{
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
	    
	    // Added in v3.2
	    moddedVillagerModularSkins = config.getStringList("Modded Villager Modular Skins", "villager professions", new String[]{
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
	    

	    villagerSkinTones = config.getBoolean("Display Skin Tones", "villager skin tones", true, "Display Gaussian-distributed random skin tones assigned to villagers");
	    villagerSkinToneVarianceAnnealing = config.getFloat("Skin Tone Variance Annealing", "villager skin tones", 8F/3, 0, Float.MAX_VALUE,
	    		"Statistical variance in skin tone for a population decreases as the number of skin-tone-affecting biome tags increases.\n"
	    		+ "Setting this value to zero eliminates that effect, making skin tone vary equally everywhere (aside from culling to the darkest/lightest tones).\n"
	    		+ "Increasing this value makes skin tone variation less likely in qualifying biomes.");
	    villagerSkinToneVarianceScale = config.getFloat("Skin Tone Variance Scale", "villager skin tones", 1F, 0, Float.MAX_VALUE,
	    		"Proportionality constant for variance everywhere, irrespective of biome. Set this to zero for absolutely no variation for a given biome.\n"
	    		+ "Skin tones are culled to the darkest and lightest values, so setting this arbitrarily high will result in ONLY the darkest or lightest villagers.\n"
	    		+ "I estimate that the distribution is flattest, and thus population variance is maximized, around a value of about 2.6.");
	    
	    zombieCureCatalysts = config.getStringList("Zombie Cure Catalysts", "villager professions", new String[]{
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
	    
	    zombieCureGroups = config.getStringList("Zombie Cure Groups", "villager professions", new String[]{
 				"vanilla|0.3|14"
 				},
 				"When curing a zombie villager, all blocks of the same named group will use these stats. "
 				+ "Format is: group|speedup|limit\n"
 				+ "group is the group name assigned in Zombie Cure Catalysts above.\n"
 				+ "speedup is the per-block percentage point boost in conversion speed. That is: a value of 1.0 increases the conversion by about 1 percentage point per group block found. "
 				+ "negative values will likewise reduce the conversion speed, making conversion take longer.\n"
 				+ "limit is the maximum number of blocks in this group that will apply the group speedup effect."
 				);
 		
 		
    	//--------------Miscellaneous-----------------//
	    
	    versionChecker = config.getBoolean("Version Checker", "miscellaneous", false, "Displays a client-side chat message on login if there's an update available. If the URL pinged by the checker happens to be down, your game will freeze for a while on login. Turn this on at your own risk.");
	    codexChestLoot = config.getBoolean("Codex Chest Loot", "miscellaneous", true, "The Codex can appear as rare chest loot.");
	    allowInGameConfig = config.getBoolean("Allow in-game config access", "miscellaneous", true, "Set this to false to deacactivate the in-game config GUI, in case it conflicts with other mods.");
	    debugMessages = config.getBoolean("Debug messages", "miscellaneous", false, "Print debug messages to the console, print the class paths of entities and blocks you right-click.");
	    pyramidTerracotta = config.getBoolean("Pyramid Terracotta", "miscellaneous", true, "Replace the wool blocks in desert pyramids with terracotta as in 1.8+");
	    addOceanMonuments = config.getBoolean("Add Monuments", "miscellaneous", true, "Generate Ocean Monuments, Prismarine, Guardians, and absorbent Sponges");
	    alternateGuardianNamespace = config.getBoolean("Alternate Guardian Namespace", "miscellaneous", false, "WARNING: TOGGLING THIS VALUE WILL REMOVE OR REPLACE ALL CURRENTLY-SPAWNED GUARDIANS FROM YOUR WORLD, INCLUDING ELDERS!\n"
	    		+"Set this to true if you have fatal conflicts loading another mod using the entity name minecraft:Guardian by instead using the name minecraft:Guardian_VN."); // v3.2.3
	    addIgloos = config.getBoolean("Add Igloos", "miscellaneous", true, "Generate Igloos from 1.9+");
	    // Added in v3.1
	    biomedictIgloos = config.getBoolean("Allow Igloos in modded biomes", "miscellaneous", false, "Igloos can generate in mods' snowy plains biomes, rather than just vanilla's Ice Plains and Cold Taiga");
	    
	    
	    //--------------Names-----------------//
	    
	    nameEntities = config.getBoolean("Entity names", "Naming", true, "Entities reveal their names when you right-click them, or automatically if so assigned.");
	    addJobToName = config.getBoolean("Entity professions", "Naming", false, "An entity's name also includes its profession/title. You may need to right-click the entity to update its name plate.");
	    nameGolems = config.getBoolean("Golem names", "Naming", true, "Right-click village Golems to learn their name.");
	    
	    nitwitProfession = config.getString("Nitwit Profession", "Naming", "", "The career displayed for a Nitwit");
	    
		// Automatic Names
		
		modNameMappingAutomatic = config.getStringList("Automatic Names", "Naming", new String[]{
				
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
				"alien-golem|Elder Guardian|"+Reference.elderGuardianClass+"|add", // NOT AN ACTUAL CLASSPATH: hard-coded in EntityInteractHandler.java
				
				
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
				"demon|Lord of Torment|com.emoniph.witchery.entity.EntityLordOfTorment|add"

				},
				"List of entities that will generate a name automatically when they appear. Useful for aggressive or boss mobs.\n"
				+ "Format is: nameType|profession|classPath|addOrRemove\n"
				+ "nameType is the name pool for the entity, or a hyphenated series of pools like \"angel-golem\".\n"
				+ "profession is displayed if that config flag is enabled. It can be left blank for no profession.\n"
				+ "classPath is the mod's address to the entity class.\n"
								+ "nameType options:\n"
								+ "villager, dragon, golem, alien, angel, demon, goblin, custom\n"
				+ "addOrRemove - type \"add\" to automatically add names tags to ALL COPIES of this entity upon spawning, or \"remove\" to automatically remove.\n"
				+ "Be VERY CAUTIOUS about what entities you choose to add to this list!"
								);
		
	    

		// Clickable Names
	    
		modNameMappingClickable = config.getStringList("Clickable Names", "Naming", new String[]{
				
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
				"villager-goblin||imc.entities.EntityPigman"
				
				},
				"List of entities that can generate a name when right-clicked. Format is: nameType|profession|classPath\n"
				+ "nameType is the name pool for the entity, or a hyphenated series of pools like \"angel-golem\".\n"
				+ "profession is displayed if that config flag is enabled. It can be left blank for no profession.\n"
				+ "classPath is mod's address to the entity class.\n"
								+ "nameType options:\n"
								+ "villager, dragon, golem, alien, angel, demon, goblin, custom\n"
								);
		
		
		//--------------Mod Integration-----------------//
		
		harvestcraftCropFarmRate = config.getFloat("Crop rate: Harvestcraft", "Mod Integration", 0.25F, 0F, 1F, "Generate Harvestcraft crops in farms. Only used with Village Generator. Set to 0 for no HC crops.");
		dragonQuestCropFarmRate = config.getFloat("Crop rate: DQ Respect", "Mod Integration", 0.25F, 0F, 1F, "Generate Dragon Quest Respect crops in farms. Only used with Village Generator. Set to 0 for no DQR crops.");
		
		modButton = config.getStringList("Mod Priority: Button", "Mod Integration", new String[]{
	    		"uptodate",
	    		"etfuturum",
 				"ganyssurface",
 				},
 				"Priority order for referencing wood buttons for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modConcrete = config.getStringList("Mod Priority: Concrete", "Mod Integration", new String[]{
 				"villagenames",
	    		"uptodate",
 				"etfuturum",
 				},
 				"Priority order for referencing concrete for well decorations; essentially, if you still want these features but want to disable "+ Reference.MOD_NAME+"\'s versions. The version highest on the list and registered in your game will be used."
 				);
		
	    modDye = config.getStringList("Mod Priority: Dye", "Mod Integration", new String[]{
	    		"uptodate",
 				"biomesoplenty",
 				"mariculture",
 				},
 				"Priority order for referencing dye for villager trade offers. The version highest on the list and registered in your game will be used."
 				);
	    
	    modFence = config.getStringList("Mod Priority: Fence", "Mod Integration", new String[]{
	    		"uptodate",
 				"etfuturum",
 				"ganyssurface",
 				},
 				"Priority order for referencing Fence blocks for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modPressurePlate = config.getStringList("Mod Priority: Pressure Plate", "Mod Integration", new String[]{
	    		"uptodate",
 				"etfuturum",
 				"ganyssurface",
 				},
 				"Priority order for referencing Fence blocks for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modFenceGate = config.getStringList("Mod Priority: Fence Gate", "Mod Integration", new String[]{
 				"etfuturum",
 				"ganyssurface",
	    		"uptodate",
 				},
 				"Priority order for referencing Fence Gate blocks for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modDoor = config.getStringList("Mod Priority: Door", "Mod Integration", new String[]{
	    		"uptodate",
	    		"etfuturum",
 				"ganyssurface",
 				},
 				"Priority order for referencing Doors for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modWoodenTrapdoor = config.getStringList("Mod Priority: Trapdoor", "Mod Integration", new String[]{
	    		"uptodate",
 				"ganyssurface",
 				},
 				"Priority order for referencing Wooden Trapdoors for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modRedSandstone = config.getStringList("Mod Priority: Red Sandstone", "Mod Integration", new String[]{
	    		"uptodate",
	    		"etfuturum",
 				"ganyssurface",
 				},
 				"Priority order for referencing Red Sandstone and its variants for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modStrippedLog = config.getStringList("Mod Priority: Stripped Log", "Mod Integration", new String[]{
	    		"uptodate",
 				"etfuturum",
 				},
 				"Priority order for referencing Stripped Logs for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modGlazedTerracotta = config.getStringList("Mod Priority: Glazed Terracotta", "Mod Integration", new String[]{
 				"villagenames",
	    		"uptodate",
 				},
 				"Priority order for referencing Glazed Terracotta for villager trade offers and well decorations; essentially, if you still want these features but want to disable "+ Reference.MOD_NAME+"\'s versions. The version highest on the list and registered in your game will be used."
 				);
		
		modBanner = config.getStringList("Mod Priority: Banner", "Mod Integration", new String[]{
 				"etfuturum",
 				"ganyssurface",
 				},
 				"Priority order for referencing Banners for e.g. villager trade offers. The version highest on the list and registered in your game will be used."
 				);
		
	    modBountifulStone = config.getStringList("Mod Priority: Bountiful Stone", "Mod Integration", new String[]{
	    		"chisel",
	    		"uptodate",
 				"etfuturum",
 				"ganyssurface",
 				"botania",
 				},
 				"Priority order for referencing Granite, Diorite, and Andesite for e.g. villager trade offers. The version highest on the list and registered in your game will be used."
 				);
		
	    modGrassPath = config.getStringList("Mod Priority: Grass Path", "Mod Integration", new String[]{
 				"etfuturum",
	    		"uptodate",
 				},
 				"Priority order for referencing Grass Path blocks for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
	    modBeetroot = config.getStringList("Mod Priority: Beetroot", "Mod Integration", new String[]{
 				"etfuturum",
 				"ganyssurface",
 				},
 				"Priority order for referencing Beetroot for e.g. villager trade offers. The version highest on the list and registered in your game will be used."
 				);
		
	    modIronNugget = config.getStringList("Mod Priority: Iron Nugget", "Mod Integration", new String[]{
	    		"uptodate",
 				"tinkersconstruct",
 				"thermalfoundation",
 				"railcraft",
 				"mariculture",
 				},
 				"Priority order for referencing Iron Nuggets for e.g. village chest loot. The version highest on the list and registered in your game will be used."
 				);
	    
	    modMutton = config.getStringList("Mod Priority: Mutton", "Mod Integration", new String[]{
	    		"uptodate",
 				"etfuturum",
 				"ganyssurface",
 				"harvestcraft",
 				},
 				"Priority order for referencing Mutton for e.g. villager trade offers. The version highest on the list and registered in your game will be used."
 				);
	    
	    modKelp = config.getStringList("Mod Priority: Kelp", "Mod Integration", new String[]{
 				"mariculture",
	    		"biomesoplenty",
 				},
 				"Priority order for referencing Kelp for e.g. villager trade offers. The version highest on the list and registered in your game will be used."
 				);
		
	    modLantern = config.getStringList("Mod Priority: Lantern", "Mod Integration", new String[]{
 				"uptodate",
 				"netherlicious",
	    		"enviromine",
 				},
 				"Priority order for referencing Lanterns for e.g. village generation and villager trade offers. The version highest on the list and registered in your game will be used."
 				);
	    
	    modWall = config.getStringList("Mod Priority: Wall", "Mod Integration", new String[]{
	    		"uptodate",
 				"railcraft",
 				},
 				"Priority order for referencing Wall for village generation. The version highest on the list and registered in your game will be used."
 				);
	    
		// Mapping for modded structures, and the creatures that can name them
		modStructureNames = config.getStringList("Mod Structures", "Mod Integration", new String[]{
				
				// Galacticraft
				"alienvillage|MoonVillage|Moon Village|Moon|moonvillage|micdoodle8.mods.galacticraft.core.entities.EntityAlienVillager",
				"alienvillage|GC_AbandonedBase|Abandoned Base|Asteroid Belt|abandonedbase|", // 1.10
				
				// More Planets
				"alienvillage|FronosVillage|Fronos Village|Fronos|fronosvillage|stevekung.mods.moreplanets.planets.fronos.entities.EntityFronosVillager",
				"alienvillage|FronosVillage|Fronos Village|Fronos|fronosvillage|stevekung.mods.moreplanets.module.planets.fronos.entities.EntityFronosVillager",
				"alienvillage|KoentusVillage|Koentus Village|Koentus|koentusvillage|stevekung.mods.moreplanets.moons.koentus.entities.EntityKoentusianVillager",
				"alienvillage|KoentusVillage|Koentus Village|Koentus|koentusvillage|stevekung.mods.moreplanets.module.moons.koentus.entities.EntityKoentusianVillager",
				"alienvillage|NibiruVillage|Nibiru Village|The Nether|nibiruvillage|stevekung.mods.moreplanets.planets.nibiru.entity.EntityNibiruVillager",
				"alienvillage|NibiruVillage|Nibiru Village|The Nether|nibiruvillage|stevekung.mods.moreplanets.module.planets.nibiru.entity.EntityNibiruVillager",
				
				// Hardcore Ender Expansion
				"endcity|hardcoreenderdragon_EndTower|Dungeon Tower|The End|endcity|",
				"endcity|hardcoreenderdragon_EndIsland|Laboratory|The End|endcity|"
				},
				"List of mod structures that can be named with a Codex, or by right-clicking an entity in that structure (optional)."
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
		modProfessionMapping = config.getStringList("Mod Professions", "Mod Integration", new String[]{
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
	    PMMerchantProfessionMap = config.getInt("PM Traveling Merchant Profession ID", "Mod Integration", 0, 0, 5,
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
	    PMLostMinerProfessionMap = config.getInt("PM Lost Miner Profession ID", "Mod Integration", 3, 0, 5,
	    		"Which vanilla archetype the traveling merchant emulates in order to generate hint pages.\n"
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
	
	/**
	 * Used to convert the comma-separated-integer string in the config value into an array of integers
	 * Returns the given default array if the user screws up.
	 */
	public static ArrayList<Double> parseDoubleArray(String configvalue, ArrayList<Double> defaultValues)
	{
		try
		{
			String[] sMPA1_stringarray = configvalue.split(",");
			ArrayList<Double> doubleArrayListToReturn = new ArrayList<Double>();
			
			for (int i=0; i<sMPA1_stringarray.length; i++)
			{
				doubleArrayListToReturn.add(Double.parseDouble(sMPA1_stringarray[i].trim()));
			}

			// HALL OF SHAME
			
			// User entered wrong number of parameters
			if (sMPA1_stringarray.length!=5)
			{
				LogHelper.error("Config entry " + configvalue + " requires five values, not " + sMPA1_stringarray.length + ". Using default values " + convertDoubleArrayToString(defaultValues) + " until this is fixed.");
				return defaultValues;
			}
			
			// User entered a negative component weight
			if (doubleArrayListToReturn.get(0) < 0)
			{
				doubleArrayListToReturn.set(0, 0D);
				LogHelper.error("The first value of config entry " + configvalue + " is a weight and must not be less than zero. It will be set to 0 until this is fixed.");
			}
			
			// User's lower bound for number of structures is negative
			if ((doubleArrayListToReturn.get(1) * GeneralConfig.newVillageSize + doubleArrayListToReturn.get(2)) < 0)
			{
				LogHelper.error("Values two and three of config entry " + configvalue + " can result in fewer than zero of this structure component. Using default values " + convertDoubleArrayToString(defaultValues) + " until this is fixed.");
				return defaultValues;
			}
			
			// User's upper bound for number of structures is negative
			if ((doubleArrayListToReturn.get(3) * GeneralConfig.newVillageSize + doubleArrayListToReturn.get(4)) < 0)
			{
				LogHelper.error("Values four and five of config entry " + configvalue + " will result in fewer than zero of this structure component. Using default values " + convertDoubleArrayToString(defaultValues) + " until this is fixed.");
				return defaultValues;
			}
			
			// User's lower bound for number of structures is greater than their upper bound
			if ((doubleArrayListToReturn.get(1) * GeneralConfig.newVillageSize + doubleArrayListToReturn.get(2)) > (doubleArrayListToReturn.get(3) * GeneralConfig.newVillageSize + doubleArrayListToReturn.get(4)))
			{
				LogHelper.error("Values two through five of config entry " + configvalue + " result in a higher upper bound than a lower bound for this structure component. Using default values " + convertDoubleArrayToString(defaultValues) + " until this is fixed.");
				return defaultValues;
			}
			
			// This only happens if the user didn't cock up royally
			return doubleArrayListToReturn;
		}
		catch (Exception e) // Config entry was malformed
		{
			LogHelper.error("Config entry " + configvalue + " was malformed. Check that it is five comma-separated integers. Using default values " + convertDoubleArrayToString(defaultValues) + " until this is fixed.");
			return defaultValues;
		}
	}
	
	/**
	 * Converts a double arraylist back into a comma-separated string
	 */
	public static String convertDoubleArrayToString(ArrayList<Double> arraylist, int nDecimals)
	{
		//String s=arraylist.get(0).toString();
		String s=String.format("%1."+nDecimals+"f", arraylist.get(0));
		
		for (int i=1; i<arraylist.size(); i++) 
		{
			//s+=", "+arraylist.get(i).toString();
			s+=", "+String.format("%1."+nDecimals+"f", arraylist.get(i));
		}
		return s;
	}
	public static String convertDoubleArrayToString(ArrayList<Double> arraylist)
	{
		return convertDoubleArrayToString(arraylist, 4);
	}
}
