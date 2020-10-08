package astrotibs.villagenames.config.village;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.Reference;
import net.minecraftforge.common.config.Configuration;

public class VillageGeneratorConfigHandler
{
	public static Configuration config;
	
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
	public static String componentModernPlainsStreetDecor1_string; public static ArrayList<Double> componentModernPlainsStreetDecor1_vals;
	
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
	public static String componentModernDesertStreetDecor1_string; public static ArrayList<Double> componentModernDesertStreetDecor1_vals;
	public static String componentModernDesertStreetSubstitute1_string; public static ArrayList<Double> componentModernDesertStreetSubstitute1_vals;
	public static String componentModernDesertStreetSubstitute2_string; public static ArrayList<Double> componentModernDesertStreetSubstitute2_vals;
	public static String componentModernDesertStreetSubstitute3_string; public static ArrayList<Double> componentModernDesertStreetSubstitute3_vals;
	
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
	public static String componentModernTaigaStreetDecor1_string; public static ArrayList<Double> componentModernTaigaStreetDecor1_vals;

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
	public static String componentModernSavannaTannery1_string; public static ArrayList<Double> componentModernSavannaTannery1_vals;
	public static String componentModernSavannaTemple1_string; public static ArrayList<Double> componentModernSavannaTemple1_vals;
	public static String componentModernSavannaTemple2_string; public static ArrayList<Double> componentModernSavannaTemple2_vals;
	public static String componentModernSavannaToolSmith1_string; public static ArrayList<Double> componentModernSavannaToolSmith1_vals;
	public static String componentModernSavannaWeaponsmith1_string; public static ArrayList<Double> componentModernSavannaWeaponsmith1_vals;
	public static String componentModernSavannaWeaponsmith2_string; public static ArrayList<Double> componentModernSavannaWeaponsmith2_vals;
	public static String componentModernSavannaStreetDecor1_string; public static ArrayList<Double> componentModernSavannaStreetDecor1_vals;
	public static String componentModernSavannaStreetSubstitute1_string; public static ArrayList<Double> componentModernSavannaStreetSubstitute1_vals;
	public static String componentModernSavannaStreetSubstitute2_string; public static ArrayList<Double> componentModernSavannaStreetSubstitute2_vals;
	public static String componentModernSavannaStreetSubstitute3_string; public static ArrayList<Double> componentModernSavannaStreetSubstitute3_vals;
	public static String componentModernSavannaStreetSubstitute4_string; public static ArrayList<Double> componentModernSavannaStreetSubstitute4_vals;
	
	public static String componentModernSnowyAnimalPen1_string; public static ArrayList<Double> componentModernSnowyAnimalPen1_vals;
	public static String componentModernSnowyAnimalPen2_string; public static ArrayList<Double> componentModernSnowyAnimalPen2_vals;
	public static String componentModernSnowyArmorerHouse1_string; public static ArrayList<Double> componentModernSnowyArmorerHouse1_vals;
	public static String componentModernSnowyArmorerHouse2_string; public static ArrayList<Double> componentModernSnowyArmorerHouse2_vals;
	public static String componentModernSnowyButchersShop1_string; public static ArrayList<Double> componentModernSnowyButchersShop1_vals;
	public static String componentModernSnowyButchersShop2_string; public static ArrayList<Double> componentModernSnowyButchersShop2_vals;
	public static String componentModernSnowyCartographerHouse1_string; public static ArrayList<Double> componentModernSnowyCartographerHouse1_vals;
	public static String componentModernSnowyFarm1_string; public static ArrayList<Double> componentModernSnowyFarm1_vals;
	public static String componentModernSnowyFarm2_string; public static ArrayList<Double> componentModernSnowyFarm2_vals;
	public static String componentModernSnowyFisherCottage_string; public static ArrayList<Double> componentModernSnowyFisherCottage_vals;
	public static String componentModernSnowyFletcherHouse1_string; public static ArrayList<Double> componentModernSnowyFletcherHouse1_vals;
	public static String componentModernSnowyLibrary1_string; public static ArrayList<Double> componentModernSnowyLibrary1_vals;
	public static String componentModernSnowyMasonsHouse1_string; public static ArrayList<Double> componentModernSnowyMasonsHouse1_vals;
	public static String componentModernSnowyMasonsHouse2_string; public static ArrayList<Double> componentModernSnowyMasonsHouse2_vals;
	public static String componentModernSnowyMediumHouse1_string; public static ArrayList<Double> componentModernSnowyMediumHouse1_vals;
	public static String componentModernSnowyMediumHouse2_string; public static ArrayList<Double> componentModernSnowyMediumHouse2_vals;
	public static String componentModernSnowyMediumHouse3_string; public static ArrayList<Double> componentModernSnowyMediumHouse3_vals;
	public static String componentModernSnowyShepherdsHouse1_string; public static ArrayList<Double> componentModernSnowyShepherdsHouse1_vals;
	public static String componentModernSnowySmallHouse1_string; public static ArrayList<Double> componentModernSnowySmallHouse1_vals;
	public static String componentModernSnowySmallHouse2_string; public static ArrayList<Double> componentModernSnowySmallHouse2_vals;
	public static String componentModernSnowySmallHouse3_string; public static ArrayList<Double> componentModernSnowySmallHouse3_vals;
	public static String componentModernSnowySmallHouse4_string; public static ArrayList<Double> componentModernSnowySmallHouse4_vals;
	public static String componentModernSnowySmallHouse5_string; public static ArrayList<Double> componentModernSnowySmallHouse5_vals;
	public static String componentModernSnowySmallHouse6_string; public static ArrayList<Double> componentModernSnowySmallHouse6_vals;
	public static String componentModernSnowySmallHouse7_string; public static ArrayList<Double> componentModernSnowySmallHouse7_vals;
	public static String componentModernSnowySmallHouse8_string; public static ArrayList<Double> componentModernSnowySmallHouse8_vals;
	public static String componentModernSnowyTannery1_string; public static ArrayList<Double> componentModernSnowyTannery1_vals;
	public static String componentModernSnowyTemple1_string; public static ArrayList<Double> componentModernSnowyTemple1_vals;
	public static String componentModernSnowyToolSmith1_string; public static ArrayList<Double> componentModernSnowyToolSmith1_vals;
	public static String componentModernSnowyWeaponSmith1_string; public static ArrayList<Double> componentModernSnowyWeaponSmith1_vals;
	public static String componentModernSnowyStreetDecor1_string; public static ArrayList<Double> componentModernSnowyStreetDecor1_vals;

	// Misc new village stuff
	public static String[] componentVillageTypes;
	public static boolean useModdedWoodenDoors;
	public static boolean spawnModdedVillagers;
	public static boolean spawnVillagersInResidences;
	public static boolean spawnVillagersInTownCenters;
	//public static boolean farmPumpkins;
	
	public static void init(File configFile)
	{
		if (config == null)
		{
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	public static void loadConfiguration()
	{
		// --- New Villages --- //
		String componentLegacy = "Component: Legacy ";
		String componentModern = "Component: Modern ";
		String generationStatsForL = "Generation stats for this component in all villages. Vanilla weight is ";
		String generationStatsForM = "Generation stats for this component in ";
		String plainsVillages = "plains villages";
		String desertVillages = "desert villages";
		String taigaVillages = "taiga villages";
		String savannaVillages = "savanna villages";
		String snowyVillages = "snowy villages";
		
		newVillageGenerator = config.getBoolean("Activate New Village Generator", Reference.CATEGORY_VILLAGE_GENERATOR, true, "Use replacement village generation system. You may need to deactivate village generation from other mods. All other settings in this section require this to be true.");
		newVillageSize = config.getInt("Village Size", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 1, 10, "How large villages are. Vanilla is 1.");
		newVillageSpacingMedian = config.getInt("Village Spacing: Median", Reference.CATEGORY_VILLAGE_GENERATOR, 20, 1, 100, "Median distance between villages. Vanilla is 20.");
		newVillageSpacingSpread = config.getInt("Village Spacing: Range", Reference.CATEGORY_VILLAGE_GENERATOR, 12, 1, 100, "Variation in distances between villages. Must be lower than Median value. Vanilla is 12.");
		//farmPumpkins  = config.getBoolean("Pumpkin and Melon Crops", Reference.CATEGORY_VILLAGE_GENERATOR, true, "Farms can have pumpkins and melons generate in them");
		
		
		ArrayList<Double> ald; // For setting default values as integer lists
		
		// Legacy Village components
		ald = new ArrayList<Double>(Arrays.asList(0D,1D,2D,2D,4D));
		componentLegacyHouse4Garden_string = config.getString(componentLegacy+"Small House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(ald), generationStatsForL+"4.0");
		componentLegacyHouse4Garden_vals = parseDoubleArray(componentLegacyHouse4Garden_string, ald);
		
		ald = new ArrayList<Double>(Arrays.asList(0D,1D,0D,1D,1D));
		componentLegacyChurch_string = config.getString(componentLegacy+"Church", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(ald), generationStatsForL+"20.0");
		componentLegacyChurch_vals = parseDoubleArray(componentLegacyChurch_string, ald);

		ald = new ArrayList<Double>(Arrays.asList(0D,1D,0D,1D,2D));
		componentLegacyHouse1_string = config.getString(componentLegacy+"Library", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(ald), generationStatsForL+"20.0");
		componentLegacyHouse1_vals = parseDoubleArray(componentLegacyHouse1_string, ald);

		ald = new ArrayList<Double>(Arrays.asList(0D,1D,2D,3D,5D));
		componentLegacyWoodHut_string = config.getString(componentLegacy+"Hut", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(ald), generationStatsForL+"3.0");
		componentLegacyWoodHut_vals = parseDoubleArray(componentLegacyWoodHut_string, ald);

		ald = new ArrayList<Double>(Arrays.asList(0D,1D,0D,1D,2D));
		componentLegacyHall_string = config.getString(componentLegacy+"Butcher Shop", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(ald), generationStatsForL+"15.0");
		componentLegacyHall_vals = parseDoubleArray(componentLegacyHall_string, ald);

		ald = new ArrayList<Double>(Arrays.asList(0D,1D,1D,1D,4D));
		componentLegacyField1_string = config.getString(componentLegacy+"Large Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(ald), generationStatsForL+"3.0");
		componentLegacyField1_vals = parseDoubleArray(componentLegacyField1_string, ald);

		ald = new ArrayList<Double>(Arrays.asList(0D,1D,2D,2D,4D));
		componentLegacyField2_string = config.getString(componentLegacy+"Small Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(ald), generationStatsForL+"3.0");
		componentLegacyField2_vals = parseDoubleArray(componentLegacyField2_string, ald);

		ald = new ArrayList<Double>(Arrays.asList(0D,0D,0D,1D,1D));
		componentLegacyHouse2_string = config.getString(componentLegacy+"Smithy", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(ald), generationStatsForL+"15.0");
		componentLegacyHouse2_vals = parseDoubleArray(componentLegacyHouse2_string, ald);

		ald = new ArrayList<Double>(Arrays.asList(0D,1D,0D,2D,3D));
		componentLegacyHouse3_string = config.getString(componentLegacy+"Large House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(ald), generationStatsForL+"8.0");
		componentLegacyHouse3_vals = parseDoubleArray(componentLegacyHouse3_string, ald);
		
		// Modern Village components
		
		ArrayList<Double> modernDefaults = new ArrayList<Double>(Arrays.asList(
				(91D/9), //= 10.11111111111111
				((76D/91) * 9D/(152D/5)), //=  0.24725274725274726
				((23D/91) * 9D/(152D/5)), //= 0.07482648930017351
				((112D/91) * 9D/(152D/5)), //= 0.3643724696356275
				((184D/91) * 9D/(152D/5)) //= 0.5986119144013881
				)); // All modern buildings will default to these values
		
		//ArrayList<Double> modernDefaults = new ArrayList<Double>(Arrays.asList(10D, 1D, 1D, 2D, 3D)); // Placeholder to ensure spawn
		
		ArrayList<Double> modifiedDefaults = new ArrayList<Double>(Arrays.asList(0D, 0D, 0D, 0D, 0D));
		int plainsHouses = 36;
		int desertHouses = 28;
		int taigaHouses = 27;
		int savannaHouses = 31;
		int snowyHouses = 30;
		
		int desertStreetsAndEndcaps = 11+2;
		int savannaStreetsAndEndcaps = 19+1;
		
		double plainsDecorToHouseRatio = 19D/13D;
		
		double desertDecorToHouseRatio = 12D/17D;
		double desertStreetToHouseRatio = 15D/17D; // 17 house attachments and 28 street attachments across 11 streets and 2 street endcaps = (28-(11+2))/17 = 15/17
		
		double taigaDecorToHouseRatio = 30D/18D;
		
		double savannaDecorToHouseRatio = 1D;
		double savannaStreetToHouseRatio = 31D/17D; // 17 house attachments and 51 street attachments across 19 streets and 1 street endcaps = (51-(19+1))/17 = 31/17
		
		double snowyDecorToHouseRatio = 28D/24D; 
		
		
		// Plains components
		componentModernPlainsAccessory1_string = config.getString(componentModern+"Plains Flower Planter", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsAccessory1_vals = parseDoubleArray(componentModernPlainsAccessory1_string, modernDefaults);
		
		componentModernPlainsAnimalPen1_string = config.getString(componentModern+"Plains Small Animal Pen", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsAnimalPen1_vals = parseDoubleArray(componentModernPlainsAnimalPen1_string, modernDefaults);

		componentModernPlainsAnimalPen2_string = config.getString(componentModern+"Plains Large Animal Pen", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsAnimalPen2_vals = parseDoubleArray(componentModernPlainsAnimalPen2_string, modernDefaults);

		componentModernPlainsAnimalPen3_string = config.getString(componentModern+"Plains Decorated Animal Pen", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsAnimalPen3_vals = parseDoubleArray(componentModernPlainsAnimalPen3_string, modernDefaults);

		componentModernPlainsArmorerHouse1_string = config.getString(componentModern+"Plains Armorer House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsArmorerHouse1_vals = parseDoubleArray(componentModernPlainsArmorerHouse1_string, modernDefaults);
		
		componentModernPlainsBigHouse1_string = config.getString(componentModern+"Plains Large House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsBigHouse1_vals = parseDoubleArray(componentModernPlainsBigHouse1_string, modernDefaults);

		componentModernPlainsButcherShop1_string = config.getString(componentModern+"Plains Small Butcher Shop", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsButcherShop1_vals = parseDoubleArray(componentModernPlainsButcherShop1_string, modernDefaults);
		
		componentModernPlainsButcherShop2_string = config.getString(componentModern+"Plains Large Butcher Shop", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsButcherShop2_vals = parseDoubleArray(componentModernPlainsButcherShop2_string, modernDefaults);
		
		componentModernPlainsCartographer1_string = config.getString(componentModern+"Plains Cartographer House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsCartographer1_vals = parseDoubleArray(componentModernPlainsCartographer1_string, modernDefaults);
		
		componentModernPlainsFisherCottage1_string = config.getString(componentModern+"Plains Fisher Cottage", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsFisherCottage1_vals = parseDoubleArray(componentModernPlainsFisherCottage1_string, modernDefaults);
		
		componentModernPlainsFletcherHouse1_string = config.getString(componentModern+"Plains Fletcher House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsFletcherHouse1_vals = parseDoubleArray(componentModernPlainsFletcherHouse1_string, modernDefaults);
		
		componentModernPlainsLargeFarm1_string = config.getString(componentModern+"Plains Large Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsLargeFarm1_vals = parseDoubleArray(componentModernPlainsLargeFarm1_string, modernDefaults);
		
		componentModernPlainsLibrary1_string = config.getString(componentModern+"Plains Large Library", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsLibrary1_vals = parseDoubleArray(componentModernPlainsLibrary1_string, modernDefaults);
		
		componentModernPlainsLibrary2_string = config.getString(componentModern+"Plains Small Library", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsLibrary2_vals = parseDoubleArray(componentModernPlainsLibrary2_string, modernDefaults);
		
		componentModernPlainsMasonsHouse1_string = config.getString(componentModern+"Plains Mason House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsMasonsHouse1_vals = parseDoubleArray(componentModernPlainsMasonsHouse1_string, modernDefaults);
		
		componentModernPlainsMediumHouse1_string = config.getString(componentModern+"Plains Medium House 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsMediumHouse1_vals = parseDoubleArray(componentModernPlainsMediumHouse1_string, modernDefaults);
		
		componentModernPlainsMediumHouse2_string = config.getString(componentModern+"Plains Medium House 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsMediumHouse2_vals = parseDoubleArray(componentModernPlainsMediumHouse2_string, modernDefaults);
		
		componentModernPlainsMeetingPoint4_string = config.getString(componentModern+"Plains Large Market", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsMeetingPoint4_vals = parseDoubleArray(componentModernPlainsMeetingPoint4_string, modernDefaults);
		
		componentModernPlainsMeetingPoint5_string = config.getString(componentModern+"Plains Small Market", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsMeetingPoint5_vals = parseDoubleArray(componentModernPlainsMeetingPoint5_string, modernDefaults);
		
		componentModernPlainsShepherdsHouse1_string = config.getString(componentModern+"Plains Shepherd House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsShepherdsHouse1_vals = parseDoubleArray(componentModernPlainsShepherdsHouse1_string, modernDefaults);
		
		componentModernPlainsSmallFarm1_string = config.getString(componentModern+"Plains Small Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsSmallFarm1_vals = parseDoubleArray(componentModernPlainsSmallFarm1_string, modernDefaults);
		
		componentModernPlainsSmallHouse1_string = config.getString(componentModern+"Plains Small House 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsSmallHouse1_vals = parseDoubleArray(componentModernPlainsSmallHouse1_string, modernDefaults);
		
		componentModernPlainsSmallHouse2_string = config.getString(componentModern+"Plains Small House 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsSmallHouse2_vals = parseDoubleArray(componentModernPlainsSmallHouse2_string, modernDefaults);
		
		componentModernPlainsSmallHouse3_string = config.getString(componentModern+"Plains Small House 3", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsSmallHouse3_vals = parseDoubleArray(componentModernPlainsSmallHouse3_string, modernDefaults);
		
		componentModernPlainsSmallHouse4_string = config.getString(componentModern+"Plains Small House 4", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsSmallHouse4_vals = parseDoubleArray(componentModernPlainsSmallHouse4_string, modernDefaults);
		
		componentModernPlainsSmallHouse5_string = config.getString(componentModern+"Plains Small House 5", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsSmallHouse5_vals = parseDoubleArray(componentModernPlainsSmallHouse5_string, modernDefaults);
		
		componentModernPlainsSmallHouse6_string = config.getString(componentModern+"Plains Small House 6", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsSmallHouse6_vals = parseDoubleArray(componentModernPlainsSmallHouse6_string, modernDefaults);
		
		componentModernPlainsSmallHouse7_string = config.getString(componentModern+"Plains Small House 7", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsSmallHouse7_vals = parseDoubleArray(componentModernPlainsSmallHouse7_string, modernDefaults);
		
		componentModernPlainsSmallHouse8_string = config.getString(componentModern+"Plains Small House 8", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsSmallHouse8_vals = parseDoubleArray(componentModernPlainsSmallHouse8_string, modernDefaults);
		
		componentModernPlainsStable1_string = config.getString(componentModern+"Plains Cobblestone Stable", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsStable1_vals = parseDoubleArray(componentModernPlainsStable1_string, modernDefaults);
		
		componentModernPlainsStable2_string = config.getString(componentModern+"Plains Terracotta Stable", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsStable2_vals = parseDoubleArray(componentModernPlainsStable2_string, modernDefaults);
		
		componentModernPlainsTannery1_string = config.getString(componentModern+"Plains Tannery", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsTannery1_vals = parseDoubleArray(componentModernPlainsTannery1_string, modernDefaults);
		
		componentModernPlainsTemple3_string = config.getString(componentModern+"Plains Terracotta Temple", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsTemple3_vals = parseDoubleArray(componentModernPlainsTemple3_string, modernDefaults);
		
		componentModernPlainsTemple4_string = config.getString(componentModern+"Plains Cobblestone Temple", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsTemple4_vals = parseDoubleArray(componentModernPlainsTemple4_string, modernDefaults);
		
		componentModernPlainsToolSmith1_string = config.getString(componentModern+"Plains Tool Smithy", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsToolSmith1_vals = parseDoubleArray(componentModernPlainsToolSmith1_string, modernDefaults);
		
		componentModernPlainsWeaponsmith1_string = config.getString(componentModern+"Plains Weapon Smithy", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsWeaponsmith1_vals = parseDoubleArray(componentModernPlainsWeaponsmith1_string, modernDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * plainsDecorToHouseRatio); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*plainsHouses * plainsDecorToHouseRatio);}
		componentModernPlainsStreetDecor1_string = config.getString(componentModern+"Plains Road Decor", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modifiedDefaults), generationStatsForM+plainsVillages);
		componentModernPlainsStreetDecor1_vals = parseDoubleArray(componentModernPlainsStreetDecor1_string, modifiedDefaults);
		
		// Desert components
		componentModernDesertAnimalPen1_string = config.getString(componentModern+"Desert Small Animal Pen", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertAnimalPen1_vals = parseDoubleArray(componentModernDesertAnimalPen1_string, modernDefaults);
		
		componentModernDesertAnimalPen2_string = config.getString(componentModern+"Desert Covered Animal Pen", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertAnimalPen2_vals = parseDoubleArray(componentModernDesertAnimalPen2_string, modernDefaults);
		
		componentModernDesertArmorer1_string = config.getString(componentModern+"Desert Armorer House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertArmorer1_vals = parseDoubleArray(componentModernDesertArmorer1_string, modernDefaults);
		
		componentModernDesertButcherShop1_string = config.getString(componentModern+"Desert Butcher Shop", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertButcherShop1_vals = parseDoubleArray(componentModernDesertButcherShop1_string, modernDefaults);
		
		componentModernDesertCartographerHouse1_string = config.getString(componentModern+"Desert Cartographer House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertCartographerHouse1_vals = parseDoubleArray(componentModernDesertCartographerHouse1_string, modernDefaults);
		
		componentModernDesertFarm1_string = config.getString(componentModern+"Desert Small Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertFarm1_vals = parseDoubleArray(componentModernDesertFarm1_string, modernDefaults);
		
		componentModernDesertFarm2_string = config.getString(componentModern+"Desert Medium Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertFarm2_vals = parseDoubleArray(componentModernDesertFarm2_string, modernDefaults);
		
		componentModernDesertFisher1_string = config.getString(componentModern+"Desert Fisher Cottage", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertFisher1_vals = parseDoubleArray(componentModernDesertFisher1_string, modernDefaults);
		
		componentModernDesertFletcherHouse1_string = config.getString(componentModern+"Desert Fletcher House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertFletcherHouse1_vals = parseDoubleArray(componentModernDesertFletcherHouse1_string, modernDefaults);
		
		componentModernDesertLargeFarm1_string = config.getString(componentModern+"Desert Large Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertLargeFarm1_vals = parseDoubleArray(componentModernDesertLargeFarm1_string, modernDefaults);
		
		componentModernDesertLibrary1_string = config.getString(componentModern+"Desert Library", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertLibrary1_vals = parseDoubleArray(componentModernDesertLibrary1_string, modernDefaults);
		
		componentModernDesertMason1_string = config.getString(componentModern+"Desert Mason House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertMason1_vals = parseDoubleArray(componentModernDesertMason1_string, modernDefaults);
		
		componentModernDesertMediumHouse1_string = config.getString(componentModern+"Desert Medium House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertMediumHouse1_vals = parseDoubleArray(componentModernDesertMediumHouse1_string, modernDefaults);
		
		componentModernDesertMediumHouse2_string = config.getString(componentModern+"Desert Large House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertMediumHouse2_vals = parseDoubleArray(componentModernDesertMediumHouse2_string, modernDefaults);
		
		componentModernDesertShepherdHouse1_string = config.getString(componentModern+"Desert Shepherd House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertShepherdHouse1_vals = parseDoubleArray(componentModernDesertShepherdHouse1_string, modernDefaults);
		
		componentModernDesertSmallHouse1_string = config.getString(componentModern+"Desert Small House 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertSmallHouse1_vals = parseDoubleArray(componentModernDesertSmallHouse1_string, modernDefaults);
		
		componentModernDesertSmallHouse2_string = config.getString(componentModern+"Desert Small House 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertSmallHouse2_vals = parseDoubleArray(componentModernDesertSmallHouse2_string, modernDefaults);
		
		componentModernDesertSmallHouse3_string = config.getString(componentModern+"Desert Small House 3", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertSmallHouse3_vals = parseDoubleArray(componentModernDesertSmallHouse3_string, modernDefaults);
		
		componentModernDesertSmallHouse4_string = config.getString(componentModern+"Desert Small House 4", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertSmallHouse4_vals = parseDoubleArray(componentModernDesertSmallHouse4_string, modernDefaults);
		
		componentModernDesertSmallHouse5_string = config.getString(componentModern+"Desert Small House 5", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertSmallHouse5_vals = parseDoubleArray(componentModernDesertSmallHouse5_string, modernDefaults);
		
		componentModernDesertSmallHouse6_string = config.getString(componentModern+"Desert Small House 6", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertSmallHouse6_vals = parseDoubleArray(componentModernDesertSmallHouse6_string, modernDefaults);
		
		componentModernDesertSmallHouse7_string = config.getString(componentModern+"Desert Small House 7", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertSmallHouse7_vals = parseDoubleArray(componentModernDesertSmallHouse7_string, modernDefaults);
		
		componentModernDesertSmallHouse8_string = config.getString(componentModern+"Desert Small House 8", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertSmallHouse8_vals = parseDoubleArray(componentModernDesertSmallHouse8_string, modernDefaults);
		
		componentModernDesertTannery1_string = config.getString(componentModern+"Desert Tannery", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertTannery1_vals = parseDoubleArray(componentModernDesertTannery1_string, modernDefaults);
		
		componentModernDesertTemple1_string = config.getString(componentModern+"Desert Temple 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertTemple1_vals = parseDoubleArray(componentModernDesertTemple1_string, modernDefaults);
		
		componentModernDesertTemple2_string = config.getString(componentModern+"Desert Temple 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertTemple2_vals = parseDoubleArray(componentModernDesertTemple2_string, modernDefaults);
		
		componentModernDesertToolSmith1_string = config.getString(componentModern+"Desert Tool Smithy", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertToolSmith1_vals = parseDoubleArray(componentModernDesertToolSmith1_string, modernDefaults);
		
		componentModernDesertWeaponsmith1_string = config.getString(componentModern+"Desert Weapon Smithy", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+desertVillages);
		componentModernDesertWeaponsmith1_vals = parseDoubleArray(componentModernDesertWeaponsmith1_string, modernDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * desertDecorToHouseRatio); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*desertHouses * desertDecorToHouseRatio);}
		componentModernDesertStreetDecor1_string = config.getString(componentModern+"Desert Road Decor 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modifiedDefaults), generationStatsForM+desertVillages);
		componentModernDesertStreetDecor1_vals = parseDoubleArray(componentModernDesertStreetDecor1_string, modifiedDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * desertStreetToHouseRatio/desertStreetsAndEndcaps ); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*desertHouses * desertStreetToHouseRatio/desertStreetsAndEndcaps);}
		componentModernDesertStreetSubstitute1_string = config.getString(componentModern+"Desert Road Terracotta Accent 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modifiedDefaults), generationStatsForM+desertVillages);
		componentModernDesertStreetSubstitute1_vals = parseDoubleArray(componentModernDesertStreetSubstitute1_string, modifiedDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * desertStreetToHouseRatio/desertStreetsAndEndcaps); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*desertHouses * desertStreetToHouseRatio/desertStreetsAndEndcaps);}
		componentModernDesertStreetSubstitute2_string = config.getString(componentModern+"Desert Road Terracotta Accent 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modifiedDefaults), generationStatsForM+desertVillages);
		componentModernDesertStreetSubstitute2_vals = parseDoubleArray(componentModernDesertStreetSubstitute2_string, modifiedDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * desertStreetToHouseRatio/desertStreetsAndEndcaps); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*desertHouses * desertStreetToHouseRatio/desertStreetsAndEndcaps);}
		componentModernDesertStreetSubstitute3_string = config.getString(componentModern+"Desert Road Decor 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modifiedDefaults), generationStatsForM+desertVillages);
		componentModernDesertStreetSubstitute3_vals = parseDoubleArray(componentModernDesertStreetSubstitute3_string, modifiedDefaults);
		
		// Taiga components
		componentModernTaigaAnimalPen1_string = config.getString(componentModern+"Taiga Animal Pen", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaAnimalPen1_vals = parseDoubleArray(componentModernTaigaAnimalPen1_string, modernDefaults);
		
		componentModernTaigaArmorer2_string = config.getString(componentModern+"Taiga Armorer Station", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaArmorer2_vals = parseDoubleArray(componentModernTaigaArmorer2_string, modernDefaults);
		
		componentModernTaigaArmorerHouse1_string = config.getString(componentModern+"Taiga Armorer House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaArmorerHouse1_vals = parseDoubleArray(componentModernTaigaArmorerHouse1_string, modernDefaults);
		
		componentModernTaigaButcherShop1_string = config.getString(componentModern+"Taiga Butcher Shop", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaButcherShop1_vals = parseDoubleArray(componentModernTaigaButcherShop1_string, modernDefaults);
		
		componentModernTaigaCartographerHouse1_string = config.getString(componentModern+"Taiga Cartographer House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaCartographerHouse1_vals = parseDoubleArray(componentModernTaigaCartographerHouse1_string, modernDefaults);
		
		componentModernTaigaFisherCottage1_string = config.getString(componentModern+"Taiga Fisher Cottage", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaFisherCottage1_vals = parseDoubleArray(componentModernTaigaFisherCottage1_string, modernDefaults);
		
		componentModernTaigaFletcherHouse1_string = config.getString(componentModern+"Taiga Fletcher House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaFletcherHouse1_vals = parseDoubleArray(componentModernTaigaFletcherHouse1_string, modernDefaults);
		
		componentModernTaigaLargeFarm1_string = config.getString(componentModern+"Taiga Large Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaLargeFarm1_vals = parseDoubleArray(componentModernTaigaLargeFarm1_string, modernDefaults);
		
		componentModernTaigaMediumFarm1_string = config.getString(componentModern+"Taiga Medium Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaMediumFarm1_vals = parseDoubleArray(componentModernTaigaMediumFarm1_string, modernDefaults);
		
		componentModernTaigaLibrary1_string = config.getString(componentModern+"Taiga Library", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaLibrary1_vals = parseDoubleArray(componentModernTaigaLibrary1_string, modernDefaults);
		
		componentModernTaigaMasonsHouse1_string = config.getString(componentModern+"Taiga Mason House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaMasonsHouse1_vals = parseDoubleArray(componentModernTaigaMasonsHouse1_string, modernDefaults);
		
		componentModernTaigaMediumHouse1_string = config.getString(componentModern+"Taiga Medium House 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaMediumHouse1_vals = parseDoubleArray(componentModernTaigaMediumHouse1_string, modernDefaults);
		
		componentModernTaigaMediumHouse2_string = config.getString(componentModern+"Taiga Medium House 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaMediumHouse2_vals = parseDoubleArray(componentModernTaigaMediumHouse2_string, modernDefaults);
		
		componentModernTaigaMediumHouse3_string = config.getString(componentModern+"Taiga Medium House 3", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaMediumHouse3_vals = parseDoubleArray(componentModernTaigaMediumHouse3_string, modernDefaults);
		
		componentModernTaigaMediumHouse4_string = config.getString(componentModern+"Taiga Medium House 4", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaMediumHouse4_vals = parseDoubleArray(componentModernTaigaMediumHouse4_string, modernDefaults);
		
		componentModernTaigaShepherdsHouse1_string = config.getString(componentModern+"Taiga Shepherd House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaShepherdsHouse1_vals = parseDoubleArray(componentModernTaigaShepherdsHouse1_string, modernDefaults);
		
		componentModernTaigaSmallFarm1_string = config.getString(componentModern+"Taiga Small Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaSmallFarm1_vals = parseDoubleArray(componentModernTaigaSmallFarm1_string, modernDefaults);
		
		componentModernTaigaSmallHouse1_string = config.getString(componentModern+"Taiga Small House 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaSmallHouse1_vals = parseDoubleArray(componentModernTaigaSmallHouse1_string, modernDefaults);
		
		componentModernTaigaSmallHouse2_string = config.getString(componentModern+"Taiga Small House 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaSmallHouse2_vals = parseDoubleArray(componentModernTaigaSmallHouse2_string, modernDefaults);
		
		componentModernTaigaSmallHouse3_string = config.getString(componentModern+"Taiga Small House 3", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaSmallHouse3_vals = parseDoubleArray(componentModernTaigaSmallHouse3_string, modernDefaults);
		
		componentModernTaigaSmallHouse4_string = config.getString(componentModern+"Taiga Small House 4", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaSmallHouse4_vals = parseDoubleArray(componentModernTaigaSmallHouse4_string, modernDefaults);
		
		componentModernTaigaSmallHouse5_string = config.getString(componentModern+"Taiga Small House 5", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaSmallHouse5_vals = parseDoubleArray(componentModernTaigaSmallHouse5_string, modernDefaults);
		
		componentModernTaigaTannery1_string = config.getString(componentModern+"Taiga Tannery", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaTannery1_vals = parseDoubleArray(componentModernTaigaTannery1_string, modernDefaults);
		
		componentModernTaigaTemple1_string = config.getString(componentModern+"Taiga Temple", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaTemple1_vals = parseDoubleArray(componentModernTaigaTemple1_string, modernDefaults);
		
		componentModernTaigaToolSmith1_string = config.getString(componentModern+"Taiga Tool Smithy", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaToolSmith1_vals = parseDoubleArray(componentModernTaigaToolSmith1_string, modernDefaults);
		
		componentModernTaigaWeaponsmith1_string = config.getString(componentModern+"Taiga Weapon Smith House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaWeaponsmith1_vals = parseDoubleArray(componentModernTaigaWeaponsmith1_string, modernDefaults);
		
		componentModernTaigaWeaponsmith2_string = config.getString(componentModern+"Taiga Weapon Smith Station", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaWeaponsmith2_vals = parseDoubleArray(componentModernTaigaWeaponsmith2_string, modernDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * taigaDecorToHouseRatio); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*taigaHouses * taigaDecorToHouseRatio);}
		componentModernTaigaStreetDecor1_string = config.getString(componentModern+"Taiga Road Decor", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modifiedDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaStreetDecor1_vals = parseDoubleArray(componentModernTaigaStreetDecor1_string, modifiedDefaults);
		
		// Savanna components
		componentModernSavannaAnimalPen1_string = config.getString(componentModern+"Savanna Covered Animal Pen", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaAnimalPen1_vals = parseDoubleArray(componentModernSavannaAnimalPen1_string, modernDefaults);
		
		componentModernSavannaAnimalPen2_string = config.getString(componentModern+"Savanna Large Animal Pen", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaAnimalPen2_vals = parseDoubleArray(componentModernSavannaAnimalPen2_string, modernDefaults);
		
		componentModernSavannaAnimalPen3_string = config.getString(componentModern+"Savanna Medium Animal Pen", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaAnimalPen3_vals = parseDoubleArray(componentModernSavannaAnimalPen3_string, modernDefaults);
		
		componentModernSavannaArmorer1_string = config.getString(componentModern+"Savanna Armorer House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaArmorer1_vals = parseDoubleArray(componentModernSavannaArmorer1_string, modernDefaults);
		
		componentModernSavannaButchersShop1_string = config.getString(componentModern+"Savanna Butcher Shop 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaButchersShop1_vals = parseDoubleArray(componentModernSavannaButchersShop1_string, modernDefaults);
		
		componentModernSavannaButchersShop2_string = config.getString(componentModern+"Savanna Butcher Shop 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaButchersShop2_vals = parseDoubleArray(componentModernSavannaButchersShop2_string, modernDefaults);
		
		componentModernSavannaCartographer1_string = config.getString(componentModern+"Savanna Cartographer House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaCartographer1_vals = parseDoubleArray(componentModernSavannaCartographer1_string, modernDefaults);
		
		componentModernSavannaFisherCottage1_string = config.getString(componentModern+"Savanna Fisher Cottage", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaFisherCottage1_vals = parseDoubleArray(componentModernSavannaFisherCottage1_string, modernDefaults);
		
		componentModernSavannaFletcherHouse1_string = config.getString(componentModern+"Savanna Fletcher House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaFletcherHouse1_vals = parseDoubleArray(componentModernSavannaFletcherHouse1_string, modernDefaults);
		
		componentModernSavannaLargeFarm1_string = config.getString(componentModern+"Savanna Methodical Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaLargeFarm1_vals = parseDoubleArray(componentModernSavannaLargeFarm1_string, modernDefaults);
		
		componentModernSavannaLargeFarm2_string = config.getString(componentModern+"Savanna Haphazard Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaLargeFarm2_vals = parseDoubleArray(componentModernSavannaLargeFarm2_string, modernDefaults);
		
		componentModernSavannaLibrary1_string = config.getString(componentModern+"Savanna Library", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaLibrary1_vals = parseDoubleArray(componentModernSavannaLibrary1_string, modernDefaults);
		
		componentModernSavannaMason1_string = config.getString(componentModern+"Savanna Mason House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaMason1_vals = parseDoubleArray(componentModernSavannaMason1_string, modernDefaults);
		
		componentModernSavannaMediumHouse1_string = config.getString(componentModern+"Savanna Medium House 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaMediumHouse1_vals = parseDoubleArray(componentModernSavannaMediumHouse1_string, modernDefaults);
		
		componentModernSavannaMediumHouse2_string = config.getString(componentModern+"Savanna Medium House 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaMediumHouse2_vals = parseDoubleArray(componentModernSavannaMediumHouse2_string, modernDefaults);
		
		componentModernSavannaShepherd1_string = config.getString(componentModern+"Savanna Shepherd House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaShepherd1_vals = parseDoubleArray(componentModernSavannaShepherd1_string, modernDefaults);
		
		componentModernSavannaSmallFarm_string = config.getString(componentModern+"Savanna Small Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaSmallFarm_vals = parseDoubleArray(componentModernSavannaSmallFarm_string, modernDefaults);
		
		componentModernSavannaSmallHouse1_string = config.getString(componentModern+"Savanna Small House 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaSmallHouse1_vals = parseDoubleArray(componentModernSavannaSmallHouse1_string, modernDefaults);
		
		componentModernSavannaSmallHouse2_string = config.getString(componentModern+"Savanna Small House 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaSmallHouse2_vals = parseDoubleArray(componentModernSavannaSmallHouse2_string, modernDefaults);
		
		componentModernSavannaSmallHouse3_string = config.getString(componentModern+"Savanna Small House 3", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaSmallHouse3_vals = parseDoubleArray(componentModernSavannaSmallHouse3_string, modernDefaults);
		
		componentModernSavannaSmallHouse4_string = config.getString(componentModern+"Savanna Small House 4", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaSmallHouse4_vals = parseDoubleArray(componentModernSavannaSmallHouse4_string, modernDefaults);
		
		componentModernSavannaSmallHouse5_string = config.getString(componentModern+"Savanna Small House 5", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaSmallHouse5_vals = parseDoubleArray(componentModernSavannaSmallHouse5_string, modernDefaults);
		
		componentModernSavannaSmallHouse6_string = config.getString(componentModern+"Savanna Small House 6", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaSmallHouse6_vals = parseDoubleArray(componentModernSavannaSmallHouse6_string, modernDefaults);
		
		componentModernSavannaSmallHouse7_string = config.getString(componentModern+"Savanna Small House 7", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaSmallHouse7_vals = parseDoubleArray(componentModernSavannaSmallHouse7_string, modernDefaults);
		
		componentModernSavannaSmallHouse8_string = config.getString(componentModern+"Savanna Small House 8", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaSmallHouse8_vals = parseDoubleArray(componentModernSavannaSmallHouse8_string, modernDefaults);
		
		componentModernSavannaTannery1_string = config.getString(componentModern+"Savanna Tannery 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaTannery1_vals = parseDoubleArray(componentModernSavannaTannery1_string, modernDefaults);
		
		componentModernSavannaTemple1_string = config.getString(componentModern+"Savanna Temple 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaTemple1_vals = parseDoubleArray(componentModernSavannaTemple1_string, modernDefaults);
		
		componentModernSavannaTemple2_string = config.getString(componentModern+"Savanna Temple 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaTemple2_vals = parseDoubleArray(componentModernSavannaTemple2_string, modernDefaults);
		
		componentModernSavannaToolSmith1_string = config.getString(componentModern+"Savanna Tool Smithy", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaToolSmith1_vals = parseDoubleArray(componentModernSavannaToolSmith1_string, modernDefaults);
		
		componentModernSavannaWeaponsmith1_string = config.getString(componentModern+"Savanna Small Weapon Smithy", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaWeaponsmith1_vals = parseDoubleArray(componentModernSavannaWeaponsmith1_string, modernDefaults);
		
		componentModernSavannaWeaponsmith2_string = config.getString(componentModern+"Savanna Large Weapon Smithy", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaWeaponsmith2_vals = parseDoubleArray(componentModernSavannaWeaponsmith2_string, modernDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * savannaDecorToHouseRatio); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*savannaHouses * savannaDecorToHouseRatio);}
		componentModernSavannaStreetDecor1_string = config.getString(componentModern+"Savanna Road Decor", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modifiedDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaStreetDecor1_vals = parseDoubleArray(componentModernSavannaStreetDecor1_string, modifiedDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * savannaStreetToHouseRatio/savannaStreetsAndEndcaps ); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*savannaHouses * savannaStreetToHouseRatio/savannaStreetsAndEndcaps);}
		componentModernSavannaStreetSubstitute1_string = config.getString(componentModern+"Savanna Roadside Farm 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modifiedDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaStreetSubstitute1_vals = parseDoubleArray(componentModernSavannaStreetSubstitute1_string, modifiedDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * savannaStreetToHouseRatio/savannaStreetsAndEndcaps ); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*savannaHouses * savannaStreetToHouseRatio/savannaStreetsAndEndcaps);}
		componentModernSavannaStreetSubstitute2_string = config.getString(componentModern+"Savanna Roadside Farm 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modifiedDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaStreetSubstitute2_vals = parseDoubleArray(componentModernSavannaStreetSubstitute2_string, modifiedDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * savannaStreetToHouseRatio/savannaStreetsAndEndcaps ); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*savannaHouses * savannaStreetToHouseRatio/savannaStreetsAndEndcaps);}
		componentModernSavannaStreetSubstitute3_string = config.getString(componentModern+"Savanna Roadside Farm 3", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modifiedDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaStreetSubstitute3_vals = parseDoubleArray(componentModernSavannaStreetSubstitute3_string, modifiedDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * savannaStreetToHouseRatio/savannaStreetsAndEndcaps ); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*savannaHouses * savannaStreetToHouseRatio/savannaStreetsAndEndcaps);}
		componentModernSavannaStreetSubstitute4_string = config.getString(componentModern+"Savanna Roadside Farm 4", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modifiedDefaults), generationStatsForM+savannaVillages);
		componentModernSavannaStreetSubstitute4_vals = parseDoubleArray(componentModernSavannaStreetSubstitute4_string, modifiedDefaults);
		
		componentModernSnowyAnimalPen1_string = config.getString(componentModern+"Snowy Animal Pen 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyAnimalPen1_vals = parseDoubleArray(componentModernSnowyAnimalPen1_string, modernDefaults);
		
		componentModernSnowyAnimalPen2_string = config.getString(componentModern+"Snowy Animal Pen 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyAnimalPen2_vals = parseDoubleArray(componentModernSnowyAnimalPen2_string, modernDefaults);
		
		componentModernSnowyArmorerHouse1_string = config.getString(componentModern+"Snowy Armorer House 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyArmorerHouse1_vals = parseDoubleArray(componentModernSnowyArmorerHouse1_string, modernDefaults);
		
		componentModernSnowyArmorerHouse2_string = config.getString(componentModern+"Snowy Armorer House 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyArmorerHouse2_vals = parseDoubleArray(componentModernSnowyArmorerHouse2_string, modernDefaults);
		
		componentModernSnowyButchersShop1_string = config.getString(componentModern+"Snowy Butcher House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyButchersShop1_vals = parseDoubleArray(componentModernSnowyButchersShop1_string, modernDefaults);
		
		componentModernSnowyButchersShop2_string = config.getString(componentModern+"Snowy Butcher Igloo", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyButchersShop2_vals = parseDoubleArray(componentModernSnowyButchersShop2_string, modernDefaults);
		
		componentModernSnowyCartographerHouse1_string = config.getString(componentModern+"Snowy Cartographer House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyCartographerHouse1_vals = parseDoubleArray(componentModernSnowyCartographerHouse1_string, modernDefaults);
		
		componentModernSnowyFarm1_string = config.getString(componentModern+"Snowy Square Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyFarm1_vals = parseDoubleArray(componentModernSnowyFarm1_string, modernDefaults);
		
		componentModernSnowyFarm2_string = config.getString(componentModern+"Snowy Patch Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyFarm2_vals = parseDoubleArray(componentModernSnowyFarm2_string, modernDefaults);
		
		componentModernSnowyFisherCottage_string = config.getString(componentModern+"Snowy Fisher Cottage", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyFisherCottage_vals = parseDoubleArray(componentModernSnowyFisherCottage_string, modernDefaults);
		
		componentModernSnowyFletcherHouse1_string = config.getString(componentModern+"Snowy Fletcher House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyFletcherHouse1_vals = parseDoubleArray(componentModernSnowyFletcherHouse1_string, modernDefaults);
		
		componentModernSnowyLibrary1_string = config.getString(componentModern+"Snowy Library", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyLibrary1_vals = parseDoubleArray(componentModernSnowyLibrary1_string, modernDefaults);
		
		componentModernSnowyMasonsHouse1_string = config.getString(componentModern+"Snowy Mason House 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyMasonsHouse1_vals = parseDoubleArray(componentModernSnowyMasonsHouse1_string, modernDefaults);
		
		componentModernSnowyMasonsHouse2_string = config.getString(componentModern+"Snowy Mason House 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyMasonsHouse2_vals = parseDoubleArray(componentModernSnowyMasonsHouse2_string, modernDefaults);
		
		componentModernSnowyMediumHouse1_string = config.getString(componentModern+"Snowy Medium House 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyMediumHouse1_vals = parseDoubleArray(componentModernSnowyMediumHouse1_string, modernDefaults);
		
		componentModernSnowyMediumHouse2_string = config.getString(componentModern+"Snowy Medium House 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyMediumHouse2_vals = parseDoubleArray(componentModernSnowyMediumHouse2_string, modernDefaults);
		
		componentModernSnowyMediumHouse3_string = config.getString(componentModern+"Snowy Medium House 3", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyMediumHouse3_vals = parseDoubleArray(componentModernSnowyMediumHouse3_string, modernDefaults);
		
		componentModernSnowyShepherdsHouse1_string = config.getString(componentModern+"Snowy Shepherd House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyShepherdsHouse1_vals = parseDoubleArray(componentModernSnowyShepherdsHouse1_string, modernDefaults);
		
		componentModernSnowySmallHouse1_string = config.getString(componentModern+"Snowy Small House 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowySmallHouse1_vals = parseDoubleArray(componentModernSnowySmallHouse1_string, modernDefaults);
		
		componentModernSnowySmallHouse2_string = config.getString(componentModern+"Snowy Small House 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowySmallHouse2_vals = parseDoubleArray(componentModernSnowySmallHouse2_string, modernDefaults);
		
		componentModernSnowySmallHouse3_string = config.getString(componentModern+"Snowy Small House 3", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowySmallHouse3_vals = parseDoubleArray(componentModernSnowySmallHouse3_string, modernDefaults);
		
		componentModernSnowySmallHouse4_string = config.getString(componentModern+"Snowy Small House 4", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowySmallHouse4_vals = parseDoubleArray(componentModernSnowySmallHouse4_string, modernDefaults);
		
		componentModernSnowySmallHouse5_string = config.getString(componentModern+"Snowy Small House 5", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowySmallHouse5_vals = parseDoubleArray(componentModernSnowySmallHouse5_string, modernDefaults);
		
		componentModernSnowySmallHouse6_string = config.getString(componentModern+"Snowy Small House 6", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowySmallHouse6_vals = parseDoubleArray(componentModernSnowySmallHouse6_string, modernDefaults);
		
		componentModernSnowySmallHouse7_string = config.getString(componentModern+"Snowy Small House 7", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowySmallHouse7_vals = parseDoubleArray(componentModernSnowySmallHouse7_string, modernDefaults);
		
		componentModernSnowySmallHouse8_string = config.getString(componentModern+"Snowy Small House 8", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowySmallHouse8_vals = parseDoubleArray(componentModernSnowySmallHouse8_string, modernDefaults);
		
		componentModernSnowyTannery1_string = config.getString(componentModern+"Snowy Tannery", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyTannery1_vals = parseDoubleArray(componentModernSnowyTannery1_string, modernDefaults);
		
		componentModernSnowyTemple1_string = config.getString(componentModern+"Snowy Temple", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyTemple1_vals = parseDoubleArray(componentModernSnowyTemple1_string, modernDefaults);
		
		componentModernSnowyToolSmith1_string = config.getString(componentModern+"Snowy Tool Smithy", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyToolSmith1_vals = parseDoubleArray(componentModernSnowyToolSmith1_string, modernDefaults);
		
		componentModernSnowyWeaponSmith1_string = config.getString(componentModern+"Snowy Weapon Smithy", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyWeaponSmith1_vals = parseDoubleArray(componentModernSnowyWeaponSmith1_string, modernDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * snowyDecorToHouseRatio); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*snowyHouses * snowyDecorToHouseRatio);}
		componentModernSnowyStreetDecor1_string = config.getString(componentModern+"Snowy Road Decor", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modifiedDefaults), generationStatsForM+snowyVillages);
		componentModernSnowyStreetDecor1_vals = parseDoubleArray(componentModernSnowyStreetDecor1_string, modifiedDefaults);
		
		
		componentVillageTypes = config.getStringList("Component Village Types", Reference.CATEGORY_VILLAGE_GENERATOR,
				new String[] {
						// Village Names 1.14 buildings
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsAccessory1|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsAnimalPen1|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsAnimalPen2|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsAnimalPen3|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsArmorerHouse1|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsBigHouse1|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsButcherShop1|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsButcherShop2|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsCartographer1|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsFisherCottage1|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsFletcherHouse1|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsLargeFarm1|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsLibrary1|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsLibrary2|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsMasonsHouse1|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsMediumHouse1|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsMediumHouse2|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsMeetingPoint4|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsMeetingPoint5|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsShepherdsHouse1|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsSmallFarm1|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsSmallHouse1|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsSmallHouse2|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsSmallHouse3|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsSmallHouse4|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsSmallHouse5|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsSmallHouse6|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsSmallHouse7|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsSmallHouse8|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsStable1|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsStable2|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsTannery1|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsTemple3|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsTemple4|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsToolSmith1|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsWeaponsmith1|plains",
						"astrotibs.villagenames.village.biomestructures.PlainsStructures$PlainsStreetDecor1|plains",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertAnimalPen1|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertAnimalPen2|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertArmorer1|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertButcherShop1|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertCartographerHouse1|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertFarm1|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertFarm2|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertFisher1|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertFletcherHouse1|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertLargeFarm1|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertLibrary1|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertMason1|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertMediumHouse1|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertMediumHouse2|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertShepherdHouse1|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertSmallHouse1|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertSmallHouse2|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertSmallHouse3|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertSmallHouse4|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertSmallHouse5|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertSmallHouse6|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertSmallHouse7|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertSmallHouse8|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertTannery1|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertTemple1|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertTemple2|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertToolSmith1|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertWeaponsmith1|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertStreetDecor1|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertStreetSubstitute1|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertStreetSubstitute2|desert",
						"astrotibs.villagenames.village.biomestructures.DesertStructures$DesertStreetSubstitute3|desert",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaAnimalPen1|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaArmorer2|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaArmorerHouse1|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaButcherShop1|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaCartographerHouse1|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaFisherCottage1|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaFletcherHouse1|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaLargeFarm1|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaMediumFarm1|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaLibrary1|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaMasonsHouse1|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaMediumHouse1|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaMediumHouse2|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaMediumHouse3|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaMediumHouse4|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaShepherdsHouse1|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaSmallFarm1|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaSmallHouse1|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaSmallHouse2|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaSmallHouse3|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaSmallHouse4|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaSmallHouse5|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaTannery1|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaTemple1|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaToolSmith1|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaWeaponsmith1|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaWeaponsmith2|taiga",
						"astrotibs.villagenames.village.biomestructures.TaigaStructures$TaigaStreetDecor1|taiga",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaAnimalPen1|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaAnimalPen2|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaAnimalPen3|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaArmorer1|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaButchersShop1|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaButchersShop2|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaCartographer1|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaFisherCottage1|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaFletcherHouse1|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaLargeFarm1|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaLargeFarm2|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaLibrary1|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaMason1|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaMediumHouse1|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaMediumHouse2|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaShepherd1|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaSmallFarm|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaSmallHouse1|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaSmallHouse2|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaSmallHouse3|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaSmallHouse4|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaSmallHouse5|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaSmallHouse6|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaSmallHouse7|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaSmallHouse8|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaTannery1|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaTemple1|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaTemple2|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaToolSmith1|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaWeaponsmith1|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaWeaponsmith2|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaStreetDecor1|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaStreetSubstitute1|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaStreetSubstitute2|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaStreetSubstitute3|savanna",
						"astrotibs.villagenames.village.biomestructures.SavannaStructures$SavannaStreetSubstitute4|savanna",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyAnimalPen1|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyAnimalPen2|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyArmorerHouse1|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyArmorerHouse2|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyButchersShop1|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyButchersShop2|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyCartographerHouse1|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyFarm1|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyFarm2|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyFisherCottage|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyFletcherHouse1|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyLibrary1|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyMasonsHouse1|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyMasonsHouse2|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyMediumHouse1|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyMediumHouse2|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyMediumHouse3|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyShepherdsHouse1|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowySmallHouse1|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowySmallHouse2|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowySmallHouse3|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowySmallHouse4|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowySmallHouse5|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowySmallHouse6|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowySmallHouse7|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowySmallHouse8|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyTannery1|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyTemple1|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyToolSmith1|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyWeaponSmith1|snowy",
						"astrotibs.villagenames.village.biomestructures.SnowyStructures$SnowyStreetDecor1|snowy",
						"growthcraft.apples.common.village.ComponentVillageAppleFarm|plains taiga snowy",
						"growthcraft.bamboo.common.village.ComponentVillageBambooYard|plains taiga savanna snowy",
						"growthcraft.bees.common.village.ComponentVillageApiarist|plains savanna",
						"growthcraft.grapes.common.village.ComponentVillageGrapeVineyard|plains taiga snowy",
						"growthcraft.hops.common.village.ComponentVillageHopVineyard|plains taiga snowy",
						"growthcraft.rice.common.village.ComponentVillageRiceField|plains taiga savanna snowy",
						},
				"List of village components that only appear in certain village types. Format is: classPaths|villageTypes\n"
				+ "classPaths: The class address to the specific structure component. If debugMessages is true, every time a village generates, a list of village components not yet specified on this list will be printed to the console.\n"
   				+ "villageTypes: list of the types this component can appear in. Multiple types can be separator with a delimiter of your choice, aside from | (pipe)"
				);
		
		
		
		// Misc
		useModdedWoodenDoors = config.getBoolean("Use modded wooden doors in mod structures", Reference.CATEGORY_VILLAGE_GENERATOR, true, "Set this to false to use the vanilla 1.7 wooden doors, even if supported mod doors are found. Some villagers have trouble opening some modded doors.");
		spawnModdedVillagers = config.getBoolean("Spawn Extra Villagers with mod professions", Reference.CATEGORY_VILLAGE_GENERATOR, false, "Villagers spawned in town centers or residential houses can have non-vanilla professions.");
		spawnVillagersInResidences = config.getBoolean("Spawn Extra Villagers in Residences", Reference.CATEGORY_VILLAGE_GENERATOR, false, "Spawn villagers with random professions and ages in non-job-specific residential houses.");
		spawnVillagersInTownCenters = config.getBoolean("Spawn Extra Villagers in Town Centers", Reference.CATEGORY_VILLAGE_GENERATOR, true, "Spawn villagers with random professions and ages in the town center.");
		
		spawnBiomesNames = config.getStringList("Spawn Biome Names", Reference.CATEGORY_VILLAGE_GENERATOR,
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
		
		
		
		if (config.hasChanged()) config.save();
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
			if ((doubleArrayListToReturn.get(1) * newVillageSize + doubleArrayListToReturn.get(2)) < 0)
			{
				LogHelper.error("Values two and three of config entry " + configvalue + " can result in fewer than zero of this structure component. Using default values " + convertDoubleArrayToString(defaultValues) + " until this is fixed.");
				return defaultValues;
			}
			
			// User's upper bound for number of structures is negative
			if ((doubleArrayListToReturn.get(3) * newVillageSize + doubleArrayListToReturn.get(4)) < 0)
			{
				LogHelper.error("Values four and five of config entry " + configvalue + " will result in fewer than zero of this structure component. Using default values " + convertDoubleArrayToString(defaultValues) + " until this is fixed.");
				return defaultValues;
			}
			
			// User's lower bound for number of structures is greater than their upper bound
			if ((doubleArrayListToReturn.get(1) * newVillageSize + doubleArrayListToReturn.get(2)) > (doubleArrayListToReturn.get(3) * newVillageSize + doubleArrayListToReturn.get(4)))
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
	
	

	/**
	 * Loads the (classPath|villageType) string lists from general.cfg
	 * and assigns them to this instance's variables.
	 */
	public static Map<String, ArrayList> unpackComponentVillageTypes(String[] inputList) {
		
		ArrayList<String> componentClassPaths = new ArrayList<String>();
		ArrayList<String> componentVillageTypes = new ArrayList<String>();
		
		for (String entry : inputList) {
			// Remove parentheses
			entry.replaceAll("\\)", "");
			entry.replaceAll("\\(", "");
			// Split by pipe
			String[] splitEntry = entry.split("\\|");
			
			// Initialize temp fields
			String componentClassPath="";
			String componentVillageType="";
			
			// Place entries into variables
			try {componentClassPath = splitEntry[0].trim();}       catch (Exception e) {componentClassPath="";}
			try {componentVillageType = splitEntry[1].trim();}  catch (Exception e) {componentVillageType="FAILSAFE";}
			
			if( !componentClassPath.equals("") && !componentVillageType.equals("") ) { // Something was actually assigned in the try block
				componentClassPaths.add(componentClassPath);
				componentVillageTypes.add(componentVillageType);
				}
		}

		Map<String,ArrayList> map =new HashMap();
		map.put("ClassPaths",componentClassPaths);
		map.put("VillageTypes",componentVillageTypes);
		
		return map;
	}
}
