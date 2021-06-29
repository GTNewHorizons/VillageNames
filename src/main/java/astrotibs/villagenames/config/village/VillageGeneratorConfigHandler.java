package astrotibs.villagenames.config.village;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import astrotibs.villagenames.utility.FunctionsVN;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.Reference;
import net.minecraftforge.common.config.Configuration;

public class VillageGeneratorConfigHandler
{
	public static Configuration config;
	
	// --- Villages --- //
	public static boolean newVillageGenerator;
	
	public static float newVillageSizeMaximum;
	public static float newVillageSizeMinimum;
	public static float newVillageSizeMode;
	public static int newVillageSizeNormalOrder;
	
	public static int newVillageSpacingMedian;
	public static int newVillageSpacingSpread;
	public static String[] spawnBiomesNames;
	public static int noVillagesRadius;
	
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
	
	// Town centers
	public static int 
	componentModernPlainsFountain, componentModernPlainsWell, componentModernPlainsMarket, componentModernPlainsOakTree,
	componentModernDesertFountain, componentModernDesertWell, componentModernDesertMarket, 
	componentModernTaigaSquare, componentModernTaigaWell,
	componentModernSavannaMarket, componentModernSavannaFountain, componentModernSavannaDoubleWell, componentModernSavannaWell, 
	componentModernSnowyIceSpire, componentModernSnowyFountain, componentModernSnowyPavilion, 
	componentModernJungleStatue, componentModernJungleCocoaTree, componentModernJungleGarden, componentModernJungleVilla, 
	componentModernSwampWillow, componentModernSwampStatue, componentModernSwampPavilion, componentModernSwampMonolith; 
	
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
	public static String componentModernTaigaLargeFarm2_string; public static ArrayList<Double> componentModernTaigaLargeFarm2_vals;
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
	
	public static String componentModernJungleArmorerHouse_string; public static ArrayList<Double> componentModernJungleArmorerHouse_vals;
	public static String componentModernJungleButcherShop_string; public static ArrayList<Double> componentModernJungleButcherShop_vals;
	public static String componentModernJungleCartographerHouse1_string; public static ArrayList<Double> componentModernJungleCartographerHouse1_vals;
	public static String componentModernJungleCartographerHouse2_string; public static ArrayList<Double> componentModernJungleCartographerHouse2_vals;
	public static String componentModernJungleFisherCottage_string; public static ArrayList<Double> componentModernJungleFisherCottage_vals;
	public static String componentModernJungleFletcherHouse1_string; public static ArrayList<Double> componentModernJungleFletcherHouse1_vals;
	public static String componentModernJungleFletcherHouse2_string; public static ArrayList<Double> componentModernJungleFletcherHouse2_vals;
	public static String componentModernJungleLargeHouse_string; public static ArrayList<Double> componentModernJungleLargeHouse_vals;
	public static String componentModernJungleLibrary_string; public static ArrayList<Double> componentModernJungleLibrary_vals;
	public static String componentModernJungleMasonHouse_string; public static ArrayList<Double> componentModernJungleMasonHouse_vals;
	public static String componentModernJungleMediumHouse1_string; public static ArrayList<Double> componentModernJungleMediumHouse1_vals;
	public static String componentModernJungleMediumHouse2_string; public static ArrayList<Double> componentModernJungleMediumHouse2_vals;
	public static String componentModernJungleMediumHouse3_string; public static ArrayList<Double> componentModernJungleMediumHouse3_vals;
	public static String componentModernJungleMediumHouse4_string; public static ArrayList<Double> componentModernJungleMediumHouse4_vals;
	public static String componentModernJungleSmallHouse1_string; public static ArrayList<Double> componentModernJungleSmallHouse1_vals;
	public static String componentModernJungleSmallHouse2_string; public static ArrayList<Double> componentModernJungleSmallHouse2_vals;
	public static String componentModernJungleSmallHouse3_string; public static ArrayList<Double> componentModernJungleSmallHouse3_vals;
	public static String componentModernJungleSmallHouse4_string; public static ArrayList<Double> componentModernJungleSmallHouse4_vals;
	public static String componentModernJungleSmallHouse5_string; public static ArrayList<Double> componentModernJungleSmallHouse5_vals;
	public static String componentModernJungleSmallHouse6_string; public static ArrayList<Double> componentModernJungleSmallHouse6_vals;
	public static String componentModernJungleSmallHouse7_string; public static ArrayList<Double> componentModernJungleSmallHouse7_vals;
	public static String componentModernJungleSmallHouse8_string; public static ArrayList<Double> componentModernJungleSmallHouse8_vals;
	public static String componentModernJungleShepherdHouse_string; public static ArrayList<Double> componentModernJungleShepherdHouse_vals;
	public static String componentModernJungleStable_string; public static ArrayList<Double> componentModernJungleStable_vals;
	public static String componentModernJungleSteppedFarm_string; public static ArrayList<Double> componentModernJungleSteppedFarm_vals;
	public static String componentModernJungleStoneAnimalPen_string; public static ArrayList<Double> componentModernJungleStoneAnimalPen_vals;
	public static String componentModernJungleTamedFarm_string; public static ArrayList<Double> componentModernJungleTamedFarm_vals;
	public static String componentModernJungleTannery1_string; public static ArrayList<Double> componentModernJungleTannery1_vals;
	public static String componentModernJungleTannery2_string; public static ArrayList<Double> componentModernJungleTannery2_vals;
	public static String componentModernJungleTemple_string; public static ArrayList<Double> componentModernJungleTemple_vals;
	public static String componentModernJungleToolSmithy1_string; public static ArrayList<Double> componentModernJungleToolSmithy1_vals;
	public static String componentModernJungleToolSmithy2_string; public static ArrayList<Double> componentModernJungleToolSmithy2_vals;
	public static String componentModernJungleWeaponSmithy_string; public static ArrayList<Double> componentModernJungleWeaponSmithy_vals;
	public static String componentModernJungleWildFarm_string; public static ArrayList<Double> componentModernJungleWildFarm_vals;
	public static String componentModernJungleWoodAnimalPen_string; public static ArrayList<Double> componentModernJungleWoodAnimalPen_vals;
	public static String componentModernJungleStreetDecor_string; public static ArrayList<Double> componentModernJungleStreetDecor_vals;
	public static String componentModernJungleRoadAccent1_string; public static ArrayList<Double> componentModernJungleRoadAccent1_vals;
	public static String componentModernJungleRoadAccent2_string; public static ArrayList<Double> componentModernJungleRoadAccent2_vals;
	
	public static String componentModernSwampAnimalPen1_string; public static ArrayList<Double> componentModernSwampAnimalPen1_vals;
	public static String componentModernSwampAnimalPen2_string; public static ArrayList<Double> componentModernSwampAnimalPen2_vals;
	public static String componentModernSwampArmorerHouse_string; public static ArrayList<Double> componentModernSwampArmorerHouse_vals;
	public static String componentModernSwampButcherShop_string; public static ArrayList<Double> componentModernSwampButcherShop_vals;
	public static String componentModernSwampCartographerHouse_string; public static ArrayList<Double> componentModernSwampCartographerHouse_vals;
	public static String componentModernSwampFisherCottage1_string; public static ArrayList<Double> componentModernSwampFisherCottage1_vals;
	public static String componentModernSwampFisherCottage2_string; public static ArrayList<Double> componentModernSwampFisherCottage2_vals;
	public static String componentModernSwampFletcherHouse_string; public static ArrayList<Double> componentModernSwampFletcherHouse_vals;
	public static String componentModernSwampHutFarm_string; public static ArrayList<Double> componentModernSwampHutFarm_vals;
	public static String componentModernSwampLargeHouse_string; public static ArrayList<Double> componentModernSwampLargeHouse_vals;
	public static String componentModernSwampHorribleSecret_string; public static ArrayList<Double> componentModernSwampHorribleSecret_vals;
	public static String componentModernSwampLibrary_string; public static ArrayList<Double> componentModernSwampLibrary_vals;
	public static String componentModernSwampMasonHouse_string; public static ArrayList<Double> componentModernSwampMasonHouse_vals;
	public static String componentModernSwampMediumHouse1_string; public static ArrayList<Double> componentModernSwampMediumHouse1_vals;
	public static String componentModernSwampMediumHouse2_string; public static ArrayList<Double> componentModernSwampMediumHouse2_vals;
	public static String componentModernSwampShepherdHouse1_string; public static ArrayList<Double> componentModernSwampShepherdHouse1_vals;
	public static String componentModernSwampShepherdHouse2_string; public static ArrayList<Double> componentModernSwampShepherdHouse2_vals;
	public static String componentModernSwampSmallHouse1_string; public static ArrayList<Double> componentModernSwampSmallHouse1_vals;
	public static String componentModernSwampSmallHouse2_string; public static ArrayList<Double> componentModernSwampSmallHouse2_vals;
	public static String componentModernSwampSmallHouse3_string; public static ArrayList<Double> componentModernSwampSmallHouse3_vals;
	public static String componentModernSwampSmallHouse4_string; public static ArrayList<Double> componentModernSwampSmallHouse4_vals;
	public static String componentModernSwampSmallHouse5_string; public static ArrayList<Double> componentModernSwampSmallHouse5_vals;
	public static String componentModernSwampStable_string; public static ArrayList<Double> componentModernSwampStable_vals;
	public static String componentModernSwampTannery_string; public static ArrayList<Double> componentModernSwampTannery_vals;
	public static String componentModernSwampTemple_string; public static ArrayList<Double> componentModernSwampTemple_vals;
	public static String componentModernSwampToolSmithy_string; public static ArrayList<Double> componentModernSwampToolSmithy_vals;
	public static String componentModernSwampWeaponSmithy_string; public static ArrayList<Double> componentModernSwampWeaponSmithy_vals;
	public static String componentModernSwampWildFarm_string; public static ArrayList<Double> componentModernSwampWildFarm_vals;
	public static String componentModernSwampStreetDecor_string; public static ArrayList<Double> componentModernSwampStreetDecor_vals;
	public static String componentModernSwampRoadAccent_string; public static ArrayList<Double> componentModernSwampRoadAccent_vals;
	
	// Default values used to restrict modern vanilla components to specific village biome types
	public static final String[] MODERN_VANILLA_COMPONENT_VILLAGE_TYPE_DEFAULTS = new String[] {
			// Village Names 1.14 buildings
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "Accessory1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "AnimalPen1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "AnimalPen2|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "AnimalPen3|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "ArmorerHouse1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "BigHouse1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "ButcherShop1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "ButcherShop2|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "Cartographer1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "FisherCottage1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "FletcherHouse1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "LargeFarm1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "Library1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "Library2|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "MasonsHouse1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "MediumHouse1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "MediumHouse2|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "MeetingPoint4|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "MeetingPoint5|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "ShepherdsHouse1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "SmallFarm1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "SmallHouse1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "SmallHouse2|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "SmallHouse3|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "SmallHouse4|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "SmallHouse5|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "SmallHouse6|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "SmallHouse7|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "SmallHouse8|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "Stable1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "Stable2|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "Tannery1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "Temple3|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "Temple4|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "ToolSmith1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "Weaponsmith1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.PLAINS_BUILDING_STUB + "StreetDecor1|plains",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "AnimalPen1|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "AnimalPen2|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "Armorer1|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "ButcherShop1|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "CartographerHouse1|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "Farm1|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "Farm2|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "Fisher1|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "FletcherHouse1|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "LargeFarm1|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "Library1|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "Mason1|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "MediumHouse1|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "MediumHouse2|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "ShepherdHouse1|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "SmallHouse1|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "SmallHouse2|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "SmallHouse3|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "SmallHouse4|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "SmallHouse5|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "SmallHouse6|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "SmallHouse7|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "SmallHouse8|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "Tannery1|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "Temple1|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "Temple2|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "ToolSmith1|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "Weaponsmith1|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "StreetDecor1|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "StreetSubstitute1|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "StreetSubstitute2|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.DESERT_BUILDING_STUB + "StreetSubstitute3|desert",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "AnimalPen1|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "Armorer2|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "ArmorerHouse1|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "ButcherShop1|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "CartographerHouse1|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "FisherCottage1|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "FletcherHouse1|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "LargeFarm1|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "LargeFarm2|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "Library1|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "MasonsHouse1|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "MediumHouse1|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "MediumHouse2|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "MediumHouse3|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "MediumHouse4|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "ShepherdsHouse1|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "SmallFarm1|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "SmallHouse1|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "SmallHouse2|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "SmallHouse3|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "SmallHouse4|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "SmallHouse5|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "Tannery1|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "Temple1|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "ToolSmith1|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "Weaponsmith1|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "Weaponsmith2|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.TAIGA_BUILDING_STUB + "StreetDecor1|taiga",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "AnimalPen1|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "AnimalPen2|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "AnimalPen3|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "Armorer1|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "ButchersShop1|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "ButchersShop2|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "Cartographer1|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "FisherCottage1|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "FletcherHouse1|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "LargeFarm1|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "LargeFarm2|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "Library1|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "Mason1|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "MediumHouse1|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "MediumHouse2|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "Shepherd1|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "SmallFarm|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "SmallHouse1|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "SmallHouse2|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "SmallHouse3|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "SmallHouse4|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "SmallHouse5|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "SmallHouse6|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "SmallHouse7|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "SmallHouse8|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "Tannery1|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "Temple1|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "Temple2|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "ToolSmith1|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "Weaponsmith1|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "Weaponsmith2|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "StreetDecor1|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "StreetSubstitute1|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "StreetSubstitute2|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "StreetSubstitute3|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SAVANNA_BUILDING_STUB + "StreetSubstitute4|savanna",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "AnimalPen1|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "AnimalPen2|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "ArmorerHouse1|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "ArmorerHouse2|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "ButchersShop1|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "ButchersShop2|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "CartographerHouse1|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "Farm1|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "Farm2|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "FisherCottage|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "FletcherHouse1|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "Library1|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "MasonsHouse1|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "MasonsHouse2|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "MediumHouse1|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "MediumHouse2|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "MediumHouse3|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "ShepherdsHouse1|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "SmallHouse1|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "SmallHouse2|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "SmallHouse3|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "SmallHouse4|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "SmallHouse5|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "SmallHouse6|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "SmallHouse7|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "SmallHouse8|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "Tannery1|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "Temple1|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "ToolSmith1|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "WeaponSmith1|snowy",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SNOWY_BUILDING_STUB + "StreetDecor1|snowy",
			// Custom buildings
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "ArmorerHouse|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "ButcherShop|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "CartographerHouse1|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "CartographerHouse2|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "FisherCottage|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "FletcherHouse1|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "FletcherHouse2|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "LargeHouse|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "Library|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "MasonHouse|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "MediumHouse1|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "MediumHouse2|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "MediumHouse3|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "MediumHouse4|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "ShepherdHouse|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "SmallHouse1|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "SmallHouse2|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "SmallHouse3|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "SmallHouse4|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "SmallHouse5|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "SmallHouse6|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "SmallHouse7|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "SmallHouse8|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "Stable|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "SteppedFarm|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "StoneAnimalPen|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "TamedFarm|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "Tannery1|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "Tannery2|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "Temple|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "ToolSmithy1|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "ToolSmithy2|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "WeaponSmithy|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "WildFarm|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "WoodAnimalPen|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "StreetDecor|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "RoadAccent1|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.JUNGLE_BUILDING_STUB + "RoadAccent2|jungle",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "AnimalPen1|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "AnimalPen2|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "ArmorerHouse|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "ButcherShop|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "CartographerHouse|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "FisherCottage1|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "FisherCottage2|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "FletcherHouse|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "HorribleSecret|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "HutFarm|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "LargeHouse|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "Library|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "MasonHouse|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "MediumHouse1|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "MediumHouse2|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "ShepherdHouse1|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "ShepherdHouse2|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "SmallHouse1|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "SmallHouse2|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "SmallHouse3|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "SmallHouse4|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "SmallHouse5|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "Stable|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "Tannery|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "Temple|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "ToolSmithy|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "WeaponSmithy|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "WildFarm|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "StreetDecor|swamp",
			Reference.VN_BUILDING_CLASSPATH_STUB + Reference.SWAMP_BUILDING_STUB + "RoadAccent|swamp",
			};
	
	// Decor
	public static boolean allowTaigaTroughs;
	public static boolean restrictTaigaTroughs;
/*	public static boolean decorPlainsLamp1; // Four torches on elevated stripped wood
	
	public static boolean decorDesertLamp1; // Torch on terracotta atop cut sandstone column
	
	public static boolean decorTaigaDecoration1; // Trough
	public static boolean decorTaigaDecoration2; // Large Boulder
	public static boolean decorTaigaDecoration3; // Small Boulder
	public static boolean decorTaigaDecoration4; // Medium Boulder
	public static boolean decorTaigaDecoration5; // Campfire
	public static boolean decorTaigaDecoration6; // Campfire over hay bin
	public static boolean decorTaigaDecoration7; // Torch on cobblestone wall
	
	public static boolean decorSavannaLampPost01; // Torch on a fence
	
	public static boolean decorSnowyLampPost1; // Two lanterns
	public static boolean decorSnowyLampPost02; // One lantern
	public static boolean decorSnowyLampPost03; // Four lanterns*/	
	
	// Misc new village stuff
	public static String[] componentVillageTypes;
	public static boolean useModdedWoodenDoors;
	public static boolean spawnModdedVillagers;
	public static boolean spawnVillagersInResidences;
	public static boolean spawnVillagersInTownCenters;
	public static boolean nameVillageHorses;
	public static boolean cleanDroppedItems;
	
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
		String townCenter = "Town Center - ";
/*		String decor = "Decor: ";
		String allowForThisDecorTypeIn = "Allow this decor type in ";*/
		String generationStatsForL = "Generation stats for this component in all villages. Vanilla weight is ";
		String generationStatsForM = "Generation stats for this component in ";
		String plainsVillages = "plains villages";
		String desertVillages = "desert villages";
		String taigaVillages = "taiga villages";
		String savannaVillages = "savanna villages";
		String snowyVillages = "snowy villages";
		String jungleVillages = "jungle villages";
		String swampVillages = "swamp villages";
		
		newVillageGenerator = config.getBoolean("Activate New Village Generator", Reference.CATEGORY_VILLAGE_GENERATOR, true, "Use replacement village generation system. You may need to deactivate village generation from other mods. All other settings in this section require this to be true.");
		
		// --- Village Size stuff --- //
		newVillageSizeMinimum = config.getFloat("Village Size: Minimum", Reference.CATEGORY_VILLAGE_GENERATOR, 1.0F, 1, 10, "Lower limit for the randomly-selected size of a village. Vanilla is 1.");
		newVillageSizeMaximum = config.getFloat("Village Size: Maximum", Reference.CATEGORY_VILLAGE_GENERATOR, 1.0F, 1, 10, "Upper limit for the randomly-selected size of a village. Vanilla is 1.");
		
		// If someone was an idiot and made Minimum greater than Maximum, fix that
		if (newVillageSizeMinimum > newVillageSizeMaximum)
		{
			LogHelper.error("Minimum village size can't be higher than maximum! Swapping values.");
			float hanoi = newVillageSizeMinimum;
			newVillageSizeMinimum = newVillageSizeMaximum;
			newVillageSizeMaximum = hanoi;
		}
		
		newVillageSizeNormalOrder = config.getInt("Village Size: Normal Order", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 1, 10, "Village size is drawn from a convolution of this many uniform distributions."
				+ " \nA value of 1 is uniform: all values in the range are equally likely. A value of 2 is triangular."
				+ " \nIncreasing this more narrowly centers the distribution around the value entered for Mode.");
		newVillageSizeMode = config.getFloat("Village Size: Mode", Reference.CATEGORY_VILLAGE_GENERATOR, 1.0F, 1, 10,
				"Peak of the distribution for the randomly-selected size of a village: the most probable village size. "
				+ "Irrelevant for Normal Order of 1 or when Maximum and Minimum are equal. "
				+ "Must be in the range of [Minimum, Maximum].");
		
		// If someone put Mode outside of the range, let them know
		if (newVillageSizeMode < newVillageSizeMinimum || newVillageSizeMode > newVillageSizeMaximum)
		{
			LogHelper.error("Village Size: Mode can't be outside of the range [minimum, maximum]. Setting to the midpoint between these.");
			newVillageSizeMode = (newVillageSizeMinimum + newVillageSizeMaximum)/2F;
		}
		
		newVillageSpacingMedian = config.getInt("Village Spacing: Median", Reference.CATEGORY_VILLAGE_GENERATOR, 20, 1, 100, "Median distance between villages. Vanilla is 20.");
		newVillageSpacingSpread = config.getInt("Village Spacing: Range", Reference.CATEGORY_VILLAGE_GENERATOR, 12, 1, 100, "Variation in distances between villages. Must be lower than Median value. Vanilla is 12.");
		noVillagesRadius = config.getInt("Village Spacing: Village-Free Radius", Reference.CATEGORY_VILLAGE_GENERATOR, 0, 0, 100000, "No villages will spawn less than this many chunks from world origin (0, 0).");
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
		
		// Town centers
		componentModernPlainsFountain = config.getInt(componentModern+"Plains "+townCenter+"Fountain", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Plains village's town center instead of other Plains town centers. Set to 0 to disable.");
		
		componentModernPlainsWell = config.getInt(componentModern+"Plains "+townCenter+"Well", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Plains village's town center instead of other Plains town centers. Set to 0 to disable.");
		
		componentModernPlainsMarket = config.getInt(componentModern+"Plains "+townCenter+"Market", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Plains village's town center instead of other Plains town centers. Set to 0 to disable.");
		
		componentModernPlainsOakTree = config.getInt(componentModern+"Plains "+townCenter+"Oak Tree", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Plains village's town center instead of other Plains town centers. Set to 0 to disable.");
		
		componentModernDesertFountain = config.getInt(componentModern+"Desert "+townCenter+"Fountain", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Desert village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		componentModernDesertWell = config.getInt(componentModern+"Desert "+townCenter+"Well", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Desert village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		componentModernDesertMarket = config.getInt(componentModern+"Desert "+townCenter+"Market", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Desert village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		componentModernTaigaSquare = config.getInt(componentModern+"Taiga "+townCenter+"Square", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Taiga village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		componentModernTaigaWell = config.getInt(componentModern+"Taiga "+townCenter+"Well", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Taiga village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		componentModernSavannaMarket = config.getInt(componentModern+"Savanna "+townCenter+"Market", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Savanna village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		componentModernSavannaFountain = config.getInt(componentModern+"Savanna "+townCenter+"Fountain", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Savanna village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		componentModernSavannaDoubleWell = config.getInt(componentModern+"Savanna "+townCenter+"Double Well", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Savanna village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		componentModernSavannaWell = config.getInt(componentModern+"Savanna "+townCenter+"Well", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Savanna village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		componentModernSnowyIceSpire = config.getInt(componentModern+"Snowy "+townCenter+"Ice Spire", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Snowy village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		componentModernSnowyFountain = config.getInt(componentModern+"Snowy "+townCenter+"Fountain", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Snowy village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		componentModernSnowyPavilion = config.getInt(componentModern+"Snowy "+townCenter+"Pavilion", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Snowy village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		componentModernJungleStatue = config.getInt(componentModern+"Jungle "+townCenter+"Statue", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Jungle village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		componentModernJungleCocoaTree = config.getInt(componentModern+"Jungle "+townCenter+"Cocoa Tree", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Jungle village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		componentModernJungleGarden = config.getInt(componentModern+"Jungle "+townCenter+"Garden", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Jungle village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		componentModernJungleVilla = config.getInt(componentModern+"Jungle "+townCenter+"Villa", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Jungle village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		componentModernSwampWillow = config.getInt(componentModern+"Swamp "+townCenter+"Willow", Reference.CATEGORY_VILLAGE_GENERATOR, 6, 0, 10000,
				"Weighted chance to select this component as a Swamp village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		componentModernSwampStatue = config.getInt(componentModern+"Swamp "+townCenter+"Statue", Reference.CATEGORY_VILLAGE_GENERATOR, 1, 0, 10000,
				"Weighted chance to select this component as a Swamp village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		componentModernSwampPavilion = config.getInt(componentModern+"Swamp "+townCenter+"Pavilion", Reference.CATEGORY_VILLAGE_GENERATOR, 6, 0, 10000,
				"Weighted chance to select this component as a Swamp village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		componentModernSwampMonolith = config.getInt(componentModern+"Swamp "+townCenter+"Monolith", Reference.CATEGORY_VILLAGE_GENERATOR, 3, 0, 10000,
				"Weighted chance to select this component as a Swamp village's town center instead of other Desert town centers. Set to 0 to disable.");
		
		
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
		int jungleHouses = 35;  
		int swampHouses = 28;  
		
		int desertStreetsAndEndcaps = 11+2;
		int savannaStreetsAndEndcaps = 19+1;
		int jungleStreetsAndEndcaps = 16; // Arbitrary
		int swampStreetsAndEndcaps = 17; // Arbitrary
		
		double plainsDecorToHouseRatio = 19D/13D;
		
		double desertDecorToHouseRatio = 12D/17D;
		double desertStreetToHouseRatio = 15D/17D; // 17 house attachments and 28 street attachments across 11 streets and 2 street endcaps = (28-(11+2))/17 = 15/17
		
		double taigaDecorToHouseRatio = 30D/18D;
		
		double savannaDecorToHouseRatio = 1D;
		double savannaStreetToHouseRatio = 31D/17D; // 17 house attachments and 51 street attachments across 19 streets and 1 street endcaps = (51-(19+1))/17 = 31/17
		
		double snowyDecorToHouseRatio = 28D/24D; 
		
		double jungleDecorToHouseRatio = 5D/3D; // Arbitrary
		double jungleStreetToHouseRatio = 20D/17D; // Arbitrary

		double swampDecorToHouseRatio = 4D/3D; // Arbitrary
		double swampStreetToHouseRatio = 26D/17D; // Arbitrary
		
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
		
		componentModernTaigaLargeFarm2_string = config.getString(componentModern+"Taiga Medium Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+taigaVillages);
		componentModernTaigaLargeFarm2_vals = parseDoubleArray(componentModernTaigaLargeFarm2_string, modernDefaults);
		
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
		
		componentModernJungleArmorerHouse_string = config.getString(componentModern+"Jungle Armorer House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleArmorerHouse_vals = parseDoubleArray(componentModernJungleArmorerHouse_string, modernDefaults);
		
		componentModernJungleButcherShop_string = config.getString(componentModern+"Jungle Butcher Shop", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleButcherShop_vals = parseDoubleArray(componentModernJungleButcherShop_string, modernDefaults);
		
		componentModernJungleCartographerHouse1_string = config.getString(componentModern+"Jungle Cartographer House 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleCartographerHouse1_vals = parseDoubleArray(componentModernJungleCartographerHouse1_string, modernDefaults);

		componentModernJungleCartographerHouse2_string = config.getString(componentModern+"Jungle Cartographer House 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleCartographerHouse2_vals = parseDoubleArray(componentModernJungleCartographerHouse2_string, modernDefaults);
		
		componentModernJungleFisherCottage_string = config.getString(componentModern+"Jungle Fisher Cottage", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleFisherCottage_vals = parseDoubleArray(componentModernJungleFisherCottage_string, modernDefaults);

		componentModernJungleFletcherHouse1_string = config.getString(componentModern+"Jungle Fletcher House 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleFletcherHouse1_vals = parseDoubleArray(componentModernJungleFletcherHouse1_string, modernDefaults);

		componentModernJungleFletcherHouse2_string = config.getString(componentModern+"Jungle Fletcher House 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleFletcherHouse2_vals = parseDoubleArray(componentModernJungleFletcherHouse2_string, modernDefaults);
		
		componentModernJungleLargeHouse_string = config.getString(componentModern+"Jungle Large House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleLargeHouse_vals = parseDoubleArray(componentModernJungleLargeHouse_string, modernDefaults);
		
		componentModernJungleLibrary_string = config.getString(componentModern+"Jungle Library", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleLibrary_vals = parseDoubleArray(componentModernJungleLibrary_string, modernDefaults);
		
		componentModernJungleMasonHouse_string = config.getString(componentModern+"Jungle Mason House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleMasonHouse_vals = parseDoubleArray(componentModernJungleMasonHouse_string, modernDefaults);
		
		componentModernJungleMediumHouse1_string = config.getString(componentModern+"Jungle Medium House 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleMediumHouse1_vals = parseDoubleArray(componentModernJungleMediumHouse1_string, modernDefaults);
		
		componentModernJungleMediumHouse2_string = config.getString(componentModern+"Jungle Medium House 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleMediumHouse2_vals = parseDoubleArray(componentModernJungleMediumHouse2_string, modernDefaults);

		componentModernJungleMediumHouse3_string = config.getString(componentModern+"Jungle Medium House 3", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleMediumHouse3_vals = parseDoubleArray(componentModernJungleMediumHouse3_string, modernDefaults);

		componentModernJungleMediumHouse4_string = config.getString(componentModern+"Jungle Medium House 4", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleMediumHouse4_vals = parseDoubleArray(componentModernJungleMediumHouse4_string, modernDefaults);

		componentModernJungleShepherdHouse_string = config.getString(componentModern+"Jungle Shepherd House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleShepherdHouse_vals = parseDoubleArray(componentModernJungleShepherdHouse_string, modernDefaults);
		
		componentModernJungleSmallHouse1_string = config.getString(componentModern+"Jungle Small House 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleSmallHouse1_vals = parseDoubleArray(componentModernJungleSmallHouse1_string, modernDefaults);

		componentModernJungleSmallHouse2_string = config.getString(componentModern+"Jungle Small House 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleSmallHouse2_vals = parseDoubleArray(componentModernJungleSmallHouse2_string, modernDefaults);

		componentModernJungleSmallHouse3_string = config.getString(componentModern+"Jungle Small House 3", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleSmallHouse3_vals = parseDoubleArray(componentModernJungleSmallHouse3_string, modernDefaults);

		componentModernJungleSmallHouse4_string = config.getString(componentModern+"Jungle Small House 4", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleSmallHouse4_vals = parseDoubleArray(componentModernJungleSmallHouse4_string, modernDefaults);
		
		componentModernJungleSmallHouse5_string = config.getString(componentModern+"Jungle Small House 5", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleSmallHouse5_vals = parseDoubleArray(componentModernJungleSmallHouse5_string, modernDefaults);
		
		componentModernJungleSmallHouse6_string = config.getString(componentModern+"Jungle Small House 6", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleSmallHouse6_vals = parseDoubleArray(componentModernJungleSmallHouse6_string, modernDefaults);
		
		componentModernJungleSmallHouse7_string = config.getString(componentModern+"Jungle Small House 7", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleSmallHouse7_vals = parseDoubleArray(componentModernJungleSmallHouse7_string, modernDefaults);
		
		componentModernJungleSmallHouse8_string = config.getString(componentModern+"Jungle Small House 8", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleSmallHouse8_vals = parseDoubleArray(componentModernJungleSmallHouse8_string, modernDefaults);

		componentModernJungleStable_string = config.getString(componentModern+"Jungle Stone Animal Pen", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleStable_vals = parseDoubleArray(componentModernJungleStable_string, modernDefaults);
		
		componentModernJungleSteppedFarm_string = config.getString(componentModern+"Jungle Stepped Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleSteppedFarm_vals = parseDoubleArray(componentModernJungleSteppedFarm_string, modernDefaults);

		componentModernJungleStoneAnimalPen_string = config.getString(componentModern+"Jungle Stone Animal Pen", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleStoneAnimalPen_vals = parseDoubleArray(componentModernJungleStoneAnimalPen_string, modernDefaults);
		
		componentModernJungleTamedFarm_string = config.getString(componentModern+"Jungle Tamed Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleTamedFarm_vals = parseDoubleArray(componentModernJungleTamedFarm_string, modernDefaults);

		componentModernJungleTannery1_string = config.getString(componentModern+"Jungle Tannery 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleTannery1_vals = parseDoubleArray(componentModernJungleTannery1_string, modernDefaults);

		componentModernJungleTannery2_string = config.getString(componentModern+"Jungle Tannery 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleTannery2_vals = parseDoubleArray(componentModernJungleTannery2_string, modernDefaults);
		
		componentModernJungleTemple_string = config.getString(componentModern+"Jungle Temple", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleTemple_vals = parseDoubleArray(componentModernJungleTemple_string, modernDefaults);
		
		componentModernJungleToolSmithy1_string = config.getString(componentModern+"Jungle Tool Smithy 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleToolSmithy1_vals = parseDoubleArray(componentModernJungleToolSmithy1_string, modernDefaults);
		
		componentModernJungleToolSmithy2_string = config.getString(componentModern+"Jungle Tool Smithy 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleToolSmithy2_vals = parseDoubleArray(componentModernJungleToolSmithy2_string, modernDefaults);

		componentModernJungleWeaponSmithy_string = config.getString(componentModern+"Jungle Weapon Smithy", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleWeaponSmithy_vals = parseDoubleArray(componentModernJungleWeaponSmithy_string, modernDefaults);
		
		componentModernJungleWildFarm_string = config.getString(componentModern+"Jungle Wild Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleWildFarm_vals = parseDoubleArray(componentModernJungleWildFarm_string, modernDefaults);

		componentModernJungleWoodAnimalPen_string = config.getString(componentModern+"Jungle Wood Animal Pen", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+jungleVillages);
		componentModernJungleWoodAnimalPen_vals = parseDoubleArray(componentModernJungleWoodAnimalPen_string, modernDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * jungleDecorToHouseRatio); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*jungleHouses * jungleDecorToHouseRatio);}
		componentModernJungleStreetDecor_string = config.getString(componentModern+"Jungle Road Decor", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modifiedDefaults), generationStatsForM+jungleVillages);
		componentModernJungleStreetDecor_vals = parseDoubleArray(componentModernJungleStreetDecor_string, modifiedDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * jungleStreetToHouseRatio/jungleStreetsAndEndcaps);
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*jungleHouses * jungleStreetToHouseRatio/jungleStreetsAndEndcaps);}
		componentModernJungleRoadAccent1_string = config.getString(componentModern+"Jungle Road Accent 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modifiedDefaults), generationStatsForM+savannaVillages);
		componentModernJungleRoadAccent1_vals = parseDoubleArray(componentModernJungleRoadAccent1_string, modifiedDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * jungleStreetToHouseRatio/jungleStreetsAndEndcaps);
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*jungleHouses * jungleStreetToHouseRatio/jungleStreetsAndEndcaps);}
		componentModernJungleRoadAccent2_string = config.getString(componentModern+"Jungle Road Accent 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modifiedDefaults), generationStatsForM+savannaVillages);
		componentModernJungleRoadAccent2_vals = parseDoubleArray(componentModernJungleRoadAccent2_string, modifiedDefaults);
		
		componentModernSwampAnimalPen1_string = config.getString(componentModern+"Swamp Animal Pen 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampAnimalPen1_vals = parseDoubleArray(componentModernSwampAnimalPen1_string, modernDefaults);
		
		componentModernSwampAnimalPen2_string = config.getString(componentModern+"Swamp Animal Pen 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampAnimalPen2_vals = parseDoubleArray(componentModernSwampAnimalPen2_string, modernDefaults);
		
		componentModernSwampArmorerHouse_string = config.getString(componentModern+"Swamp Armorer House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampArmorerHouse_vals = parseDoubleArray(componentModernSwampArmorerHouse_string, modernDefaults);
		
		componentModernSwampButcherShop_string = config.getString(componentModern+"Swamp Butcher Shop", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampButcherShop_vals = parseDoubleArray(componentModernSwampButcherShop_string, modernDefaults);
		
		componentModernSwampCartographerHouse_string = config.getString(componentModern+"Swamp Cartographer House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampCartographerHouse_vals = parseDoubleArray(componentModernSwampCartographerHouse_string, modernDefaults);

		componentModernSwampFisherCottage1_string = config.getString(componentModern+"Swamp Fisher Cottage 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampFisherCottage1_vals = parseDoubleArray(componentModernSwampFisherCottage1_string, modernDefaults);

		componentModernSwampFisherCottage2_string = config.getString(componentModern+"Swamp Fisher Cottage 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampFisherCottage2_vals = parseDoubleArray(componentModernSwampFisherCottage2_string, modernDefaults);

		componentModernSwampFletcherHouse_string = config.getString(componentModern+"Swamp Fletcher House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampFletcherHouse_vals = parseDoubleArray(componentModernSwampFletcherHouse_string, modernDefaults);
		
		componentModernSwampHutFarm_string = config.getString(componentModern+"Swamp Hut Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampHutFarm_vals = parseDoubleArray(componentModernSwampHutFarm_string, modernDefaults);
		
		componentModernSwampHorribleSecret_string = config.getString(componentModern+"Swamp Horrible Secret", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampHorribleSecret_vals = parseDoubleArray(componentModernSwampHorribleSecret_string, modernDefaults);
		
		componentModernSwampLargeHouse_string = config.getString(componentModern+"Swamp Large House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampLargeHouse_vals = parseDoubleArray(componentModernSwampLargeHouse_string, modernDefaults);
		
		componentModernSwampLibrary_string = config.getString(componentModern+"Swamp Library", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampLibrary_vals = parseDoubleArray(componentModernSwampLibrary_string, modernDefaults);
		
		componentModernSwampMasonHouse_string = config.getString(componentModern+"Swamp Mason House", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampMasonHouse_vals = parseDoubleArray(componentModernSwampMasonHouse_string, modernDefaults);
		
		componentModernSwampMediumHouse1_string = config.getString(componentModern+"Swamp Medium House 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampMediumHouse1_vals = parseDoubleArray(componentModernSwampMediumHouse1_string, modernDefaults);
		
		componentModernSwampMediumHouse2_string = config.getString(componentModern+"Swamp Medium House 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampMediumHouse2_vals = parseDoubleArray(componentModernSwampMediumHouse2_string, modernDefaults);
		
		componentModernSwampShepherdHouse1_string = config.getString(componentModern+"Swamp Shepherd House 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampShepherdHouse1_vals = parseDoubleArray(componentModernSwampShepherdHouse1_string, modernDefaults);
		
		componentModernSwampShepherdHouse2_string = config.getString(componentModern+"Swamp Shepherd House 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampShepherdHouse2_vals = parseDoubleArray(componentModernSwampShepherdHouse2_string, modernDefaults);
		
		componentModernSwampSmallHouse1_string = config.getString(componentModern+"Swamp Small House 1", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampSmallHouse1_vals = parseDoubleArray(componentModernSwampSmallHouse1_string, modernDefaults);
		
		componentModernSwampSmallHouse2_string = config.getString(componentModern+"Swamp Small House 2", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampSmallHouse2_vals = parseDoubleArray(componentModernSwampSmallHouse2_string, modernDefaults);
		
		componentModernSwampSmallHouse3_string = config.getString(componentModern+"Swamp Small House 3", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampSmallHouse3_vals = parseDoubleArray(componentModernSwampSmallHouse3_string, modernDefaults);
		
		componentModernSwampSmallHouse4_string = config.getString(componentModern+"Swamp Small House 4", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampSmallHouse4_vals = parseDoubleArray(componentModernSwampSmallHouse4_string, modernDefaults);
		
		componentModernSwampSmallHouse5_string = config.getString(componentModern+"Swamp Small House 5", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampSmallHouse5_vals = parseDoubleArray(componentModernSwampSmallHouse5_string, modernDefaults);
		
		componentModernSwampStable_string = config.getString(componentModern+"Swamp Stable", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampStable_vals = parseDoubleArray(componentModernSwampStable_string, modernDefaults);
		
		componentModernSwampTannery_string = config.getString(componentModern+"Swamp Tannery", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampTannery_vals = parseDoubleArray(componentModernSwampTannery_string, modernDefaults);
		
		componentModernSwampTemple_string = config.getString(componentModern+"Swamp Temple", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampTemple_vals = parseDoubleArray(componentModernSwampTemple_string, modernDefaults);
		
		componentModernSwampToolSmithy_string = config.getString(componentModern+"Swamp Tool Smithy", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampToolSmithy_vals = parseDoubleArray(componentModernSwampToolSmithy_string, modernDefaults);
		
		componentModernSwampWeaponSmithy_string = config.getString(componentModern+"Swamp Weapon Smithy", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampWeaponSmithy_vals = parseDoubleArray(componentModernSwampWeaponSmithy_string, modernDefaults);
		
		componentModernSwampWildFarm_string = config.getString(componentModern+"Swamp Wild Farm", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modernDefaults), generationStatsForM+swampVillages);
		componentModernSwampWildFarm_vals = parseDoubleArray(componentModernSwampWildFarm_string, modernDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * swampDecorToHouseRatio); 
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*swampHouses * swampDecorToHouseRatio);}
		componentModernSwampStreetDecor_string = config.getString(componentModern+"Swamp Road Decor", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modifiedDefaults), generationStatsForM+swampVillages);
		componentModernSwampStreetDecor_vals = parseDoubleArray(componentModernSwampStreetDecor_string, modifiedDefaults);
		
		modifiedDefaults.set(0, modernDefaults.get(0)*9D * swampStreetToHouseRatio/swampStreetsAndEndcaps);
		for (int i=1; i<modernDefaults.size(); i++) {modifiedDefaults.set(i, modernDefaults.get(i)*swampHouses * swampStreetToHouseRatio/swampStreetsAndEndcaps);}
		componentModernSwampRoadAccent_string = config.getString(componentModern+"Swamp Road Accent", Reference.CATEGORY_VILLAGE_GENERATOR, convertDoubleArrayToString(modifiedDefaults), generationStatsForM+savannaVillages);
		componentModernSwampRoadAccent_vals = parseDoubleArray(componentModernSwampRoadAccent_string, modifiedDefaults);
		
		
		// --- Decor --- //
		allowTaigaTroughs = config.getBoolean("Decor: Allow Taiga Troughs", Reference.CATEGORY_VILLAGE_GENERATOR, true, "Set to false to completely disallow the trough as decor in taiga villages");
		restrictTaigaTroughs = config.getBoolean("Decor: Restrict Taiga Troughs", Reference.CATEGORY_VILLAGE_GENERATOR, true, "Limit taiga troughs only to the well or as street decor. "
				+ "Setting this to false allows them in any flagged taiga decor location, at the risk of them cutting into the parent structure.");
		
/*		decorPlainsLamp1 = config.getBoolean(decor+"Plains Lamp", Reference.CATEGORY_VILLAGE_GENERATOR, true, allowForThisDecorTypeIn+plainsVillages);
		
		decorDesertLamp1 = config.getBoolean(decor+"Desert Lamp", Reference.CATEGORY_VILLAGE_GENERATOR, true, allowForThisDecorTypeIn+desertVillages);
		
		decorTaigaDecoration1 = config.getBoolean(decor+"Taiga Trough", Reference.CATEGORY_VILLAGE_GENERATOR, true, allowForThisDecorTypeIn+taigaVillages);
		decorTaigaDecoration2 = config.getBoolean(decor+"Taiga Large Boulder", Reference.CATEGORY_VILLAGE_GENERATOR, true, allowForThisDecorTypeIn+taigaVillages);
		decorTaigaDecoration3 = config.getBoolean(decor+"Taiga Small Boulder", Reference.CATEGORY_VILLAGE_GENERATOR, true, allowForThisDecorTypeIn+taigaVillages);
		decorTaigaDecoration4 = config.getBoolean(decor+"Taiga Medium Boulder", Reference.CATEGORY_VILLAGE_GENERATOR, true, allowForThisDecorTypeIn+taigaVillages);
		decorTaigaDecoration5 = config.getBoolean(decor+"Taiga Campfire", Reference.CATEGORY_VILLAGE_GENERATOR, true, allowForThisDecorTypeIn+taigaVillages);
		decorTaigaDecoration6 = config.getBoolean(decor+"Taiga Campfire Over Hay Bin", Reference.CATEGORY_VILLAGE_GENERATOR, true, allowForThisDecorTypeIn+taigaVillages);
		decorTaigaDecoration7 = config.getBoolean(decor+"Taiga Lamp", Reference.CATEGORY_VILLAGE_GENERATOR, true, allowForThisDecorTypeIn+taigaVillages);
		
		decorSavannaLampPost01 = config.getBoolean(decor+"Savanna Lamp", Reference.CATEGORY_VILLAGE_GENERATOR, true, allowForThisDecorTypeIn+savannaVillages);
		
		decorSnowyLampPost1 = config.getBoolean(decor+"Snowy Lamp (Two Lanterns)", Reference.CATEGORY_VILLAGE_GENERATOR, true, allowForThisDecorTypeIn+snowyVillages);
		decorSnowyLampPost02 = config.getBoolean(decor+"Snowy Lamp (One Lantern)", Reference.CATEGORY_VILLAGE_GENERATOR, true, allowForThisDecorTypeIn+snowyVillages);
		decorSnowyLampPost03 = config.getBoolean(decor+"Snowy Lamp (Four Lanterns)", Reference.CATEGORY_VILLAGE_GENERATOR, true, allowForThisDecorTypeIn+snowyVillages);*/
				
		
		componentVillageTypes = config.getStringList("Component Village Types", Reference.CATEGORY_VILLAGE_GENERATOR,
				FunctionsVN.joinTwoStringArrays(MODERN_VANILLA_COMPONENT_VILLAGE_TYPE_DEFAULTS, new String[] {
						// Mod buildings
						"forestry.apiculture.worldgen.ComponentVillageBeeHouse|plains taiga savanna jungle swamp",
						
						"growthcraft.apples.common.village.ComponentVillageAppleFarm|plains taiga snowy jungle swamp",
						"growthcraft.bamboo.common.village.ComponentVillageBambooYard|plains taiga savanna snowy jungle swamp",
						"growthcraft.bees.common.village.ComponentVillageApiarist|plains taiga savanna jungle swamp",
						"growthcraft.grapes.common.village.ComponentVillageGrapeVineyard|plains taiga snowy swamp",
						"growthcraft.hops.common.village.ComponentVillageHopVineyard|plains taiga snowy swamp",
						"growthcraft.rice.common.village.ComponentVillageRiceField|plains taiga savanna snowy jungle swamp",
						}),
				"List of village components that only appear in certain village types. Format is: classPaths|villageTypes\n"
				+ "classPaths: The class address to the specific structure component. If debugMessages is true, every time a village generates, a list of village components not yet specified on this list will be printed to the console.\n"
   				+ "villageTypes: list of the types this component can appear in. Multiple types can be separator with a delimiter of your choice, aside from | (pipe). Leave this blank, or enter something that doesn't include a village type, to prevent the component from spawning."
				);
		
		
		
		// Misc
		useModdedWoodenDoors = config.getBoolean("Use modded wooden doors in mod structures", Reference.CATEGORY_VILLAGE_GENERATOR, true, "Set this to false to use the vanilla 1.7 wooden doors, even if supported mod doors are found. Some villagers have trouble opening some modded doors.");
		spawnModdedVillagers = config.getBoolean("Spawn Extra Villagers with mod professions", Reference.CATEGORY_VILLAGE_GENERATOR, false, "Villagers spawned in town centers or residential houses can have non-vanilla professions.");
		spawnVillagersInResidences = config.getBoolean("Spawn Extra Villagers in Residences", Reference.CATEGORY_VILLAGE_GENERATOR, false, "Spawn villagers with random professions and ages in non-job-specific residential houses.");
		spawnVillagersInTownCenters = config.getBoolean("Spawn Extra Villagers in Town Centers", Reference.CATEGORY_VILLAGE_GENERATOR, true, "Spawn villagers with random professions and ages in the town center.");
		nameVillageHorses = config.getBoolean("Name Village Horses", Reference.CATEGORY_VILLAGE_GENERATOR, false, "Domesticated horses spawn with names generated from the \"pet\" syllable pool.");
		cleanDroppedItems = config.getBoolean("Clean Dropped Items", Reference.CATEGORY_VILLAGE_GENERATOR, true, "Deletes most blocks and items accidentally broken and dropped onto the ground by village generation, such as torches, doors, beds, and chest contents.");
	    
		spawnBiomesNames = config.getStringList("Spawn Biome Names", Reference.CATEGORY_VILLAGE_GENERATOR,
				new String[] {
						// Vanilla
						"Plains",
						"Desert",
						"Forest",
						"Taiga",
						"Swampland",
						"Ice Plains",
						"MushroomIsland",
						"Jungle",
						"Birch Forest",
						"Roofed Forest",
						"Cold Taiga",
						"Mega Taiga",
						"Savanna",
						"Mesa",
						"Sunflower Plains",
						"Flower Forest",
						"Mega Spruce Taiga",
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
						"Heathland|savanna|oak",
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
						"Highlands",
						"Lowlands",
						"Outback",
						"Pinelands",
						"Sahel",
						"Tall Pine Forest",
						"Tropics",
						},
				"Names of biomes which can spawn villages. Only used with Village Generator, and only applies to Overworld. Note that this list is EXCLUSIVE: other mod configs won't override this. You have to paste all biome names here."
					+ "Format is: biomeName|villageType|materialType|disallowModSub OR simply: biomeName \n"
					+ "biomeName: the registered string name of the biome\n"
					+ "villageType: the type of component structures. Leave this blank to automatically determine. Options: plains, desert, taiga, savanna, snowy\n"
					+ "materialType: the building materials used to make the components. Leave this blank to automatically determine. Set as oak if you want to allow mods to override materials. Options: oak, spruce, birch, jungle, acacia, dark_oak, sand, mesa, snow, mushroom\n"
					+ "disallowModSub: enter \"nosub\" in the last field if you want to disallow other mods from substituting blocks during village generation. Blank, or anything else, allows substitution. This only works for " + Reference.MOD_NAME + " buildings.\n"
					+ "Printing just the biome name with no pipes is equivalent to biomeName||, which will automatically determine both villageType and materialType."
				);
		
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
			LogHelper.info("VALUE IS: " + configvalue);
			
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
			if ((doubleArrayListToReturn.get(1) * newVillageSizeMinimum + doubleArrayListToReturn.get(2)) < 0)
			{
				LogHelper.error("Values two and three of config entry " + configvalue + " can result in fewer than zero of this structure component. Using default values " + convertDoubleArrayToString(defaultValues) + " until this is fixed.");
				return defaultValues;
			}
			
			// User's upper bound for number of structures is negative
			if ((doubleArrayListToReturn.get(3) * newVillageSizeMinimum + doubleArrayListToReturn.get(4)) < 0)
			{
				LogHelper.error("Values four and five of config entry " + configvalue + " will result in fewer than zero of this structure component. Using default values " + convertDoubleArrayToString(defaultValues) + " until this is fixed.");
				return defaultValues;
			}
			
			// User's lower bound for number of structures is greater than their upper bound
			if ((doubleArrayListToReturn.get(1) * (newVillageSizeMinimum+newVillageSizeMaximum)/2F + doubleArrayListToReturn.get(2)) > (doubleArrayListToReturn.get(3) * (newVillageSizeMinimum+newVillageSizeMaximum)/2F + doubleArrayListToReturn.get(4)))
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
	 * Loads the (classPath|villageType) string lists from newvillages/village_gen.cfg
	 * and assigns them to this instance's variables.
	 */
	public static Map<String, ArrayList> unpackComponentVillageTypes(String[] inputList)
	{
		ArrayList<String> componentClassPaths = new ArrayList<String>();
		ArrayList<String> componentVillageTypes = new ArrayList<String>();
		
		for (String entry : inputList)
		{
			// Remove parentheses
			entry.replaceAll("\\)", "");
			entry.replaceAll("\\(", "");
			// Split by pipe
			String[] splitEntry = entry.split("\\|");
			
			// Initialize temp fields
			String componentClassPath="";
			String componentVillageType="";
			
			// Place entries into variables
			try {componentClassPath = splitEntry[0].trim();}   catch (Exception e) {componentClassPath="FAILSAFE";}
			try {componentVillageType = splitEntry[1].trim();} catch (Exception e) {componentVillageType="none";} // If the second field is blank, that implies "don't spawn"
			
			if( !componentClassPath.equals("")) { // Something was actually assigned in the try block
				componentClassPaths.add(componentClassPath);
				componentVillageTypes.add(componentVillageType);
				}
		}

		Map<String,ArrayList> map =new HashMap();
		map.put("ClassPaths",componentClassPaths);
		map.put("VillageTypes",componentVillageTypes);
		
		return map;
	}
	

	/**
	 * Loads the (biomeName|villageType|materialType|modSubstitution) string lists from newvillages/village_gen.cfg
	 * and assigns them to this instance's variables.
	 */
	public static Map<String, ArrayList<String>> unpackBiomes(String[] inputList)
	{
		ArrayList<String> biomeNames = new ArrayList<String>();
		ArrayList<String> villageTypes = new ArrayList<String>();
		ArrayList<String> materialTypes = new ArrayList<String>();
		ArrayList<String> disallowModSubs = new ArrayList<String>();
		
		for (String entry : inputList)
		{
			// Remove parentheses
			entry.replaceAll("\\)", "");
			entry.replaceAll("\\(", "");
			// Split by pipe
			String[] splitEntry = entry.split("\\|");
			
			// Initialize temp fields
			String biomeName="";
			String villageType="";
			String materialType="";
			String disallowModSub="";
			
			// Place entries into variables
			try {biomeName = splitEntry[0].trim();}      catch (Exception e) {biomeName="";}
			try {villageType = splitEntry[1].trim();}    catch (Exception e) {villageType="";}
			try {materialType = splitEntry[2].trim();}   catch (Exception e) {materialType="";}
			try {disallowModSub = splitEntry[3].trim();} catch (Exception e) {disallowModSub="";}
			
			if( !biomeName.equals("")) { // Something was actually assigned in the try block
				biomeNames.add(biomeName);
				villageTypes.add(villageType);
				materialTypes.add(materialType);
				disallowModSubs.add(disallowModSub);
				}
		}

		Map<String,ArrayList<String>> map =new HashMap();
		map.put("BiomeNames",biomeNames);
		map.put("VillageTypes",villageTypes);
		map.put("MaterialTypes",materialTypes);
		map.put("DisallowModSubs",disallowModSubs);
		
		return map;
	}
}
