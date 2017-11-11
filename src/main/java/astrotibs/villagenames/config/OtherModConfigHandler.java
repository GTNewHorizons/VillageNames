package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePiecesMods;
import astrotibs.villagenames.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class OtherModConfigHandler {
	public static Configuration config;
	
	public static String[] otherModProfessions;
	public static int[] otherModIDs;
	public static int[] vanillaProfMaps;
	
	public static boolean nameWitcheryWitchHunter;
	public static boolean nameWitcheryVampire;
	public static boolean nameWitcheryHobgoblins;
	public static boolean nameWitcheryVillageGuards;
	
	public static boolean nameGCAlienVillagers;
	
	public static boolean nameMPKoentusVillagers;
	public static boolean nameMPFronosVillagers;
	public static boolean nameMPNibiruVillagers;
	
	public static String[] alienVillager_oneSylBegin = NamePiecesMods.alienVillager_oneSylBegin_default;
	public static String[] alienVillager_oneSylEnd = NamePiecesMods.alienVillager_oneSylEnd_default;
	public static String[] alienVillager_syl1Trans = NamePiecesMods.alienVillager_syl1Trans_default;
	public static String[] alienVillager_syl2Term = NamePiecesMods.alienVillager_syl2Term_default;
	public static String[] alienVillager_syl2Trans = NamePiecesMods.alienVillager_syl2Trans_default;
	public static String[] alienVillager_syl3Term = NamePiecesMods.alienVillager_syl3Term_default;
	public static String[] alienVillager_syl3Trans = NamePiecesMods.alienVillager_syl3Trans_default;
	public static String[] alienVillager_syl4Term = NamePiecesMods.alienVillager_syl4Term_default;
	public static String[] alienVillager_syl4Trans = NamePiecesMods.alienVillager_syl4Trans_default;
	public static String[] alienVillager_syl5Term = NamePiecesMods.alienVillager_syl5Term_default;
	public static String[] alienVillager_syl5Trans = NamePiecesMods.alienVillager_syl5Trans_default;
	public static String[] alienVillager_syl6Term = NamePiecesMods.alienVillager_syl6Term_default;
	public static String[] alienVillager_syl6Trans = NamePiecesMods.alienVillager_syl6Trans_default;
	public static String[] alienVillager_syl7Term = NamePiecesMods.alienVillager_syl7Term_default;
	
	public static String[] alienVillage_oneSylBegin = NamePiecesMods.alienVillage_oneSylBegin_default;
	public static String[] alienVillage_oneSylEnd = NamePiecesMods.alienVillage_oneSylEnd_default;
	public static String[] alienVillage_syl1Trans = NamePiecesMods.alienVillage_syl1Trans_default;
	public static String[] alienVillage_syl2Term = NamePiecesMods.alienVillage_syl2Term_default;
	public static String[] alienVillage_syl2Trans = NamePiecesMods.alienVillage_syl2Trans_default;
	public static String[] alienVillage_syl3Term = NamePiecesMods.alienVillage_syl3Term_default;
	public static String[] alienVillage_syl3Trans = NamePiecesMods.alienVillage_syl3Trans_default;
	public static String[] alienVillage_syl4Term = NamePiecesMods.alienVillage_syl4Term_default;
	public static String[] alienVillage_syl4Trans = NamePiecesMods.alienVillage_syl4Trans_default;
	public static String[] alienVillage_syl5Term = NamePiecesMods.alienVillage_syl5Term_default;
	public static String[] alienVillage_syl5Trans = NamePiecesMods.alienVillage_syl5Trans_default;
	public static String[] alienVillage_syl6Term = NamePiecesMods.alienVillage_syl6Term_default;
	
	public static String[] hobgoblin_oneSylBegin = NamePiecesMods.hobgoblin_oneSylBegin_default;
	public static String[] hobgoblin_oneSylEnd = NamePiecesMods.hobgoblin_oneSylEnd_default;
	public static String[] hobgoblin_syl1Trans = NamePiecesMods.hobgoblin_syl1Trans_default;
	public static String[] hobgoblin_syl2Term = NamePiecesMods.hobgoblin_syl2Term_default;
	public static String[] hobgoblin_syl2Trans = NamePiecesMods.hobgoblin_syl2Trans_default;
	public static String[] hobgoblin_syl3Term = NamePiecesMods.hobgoblin_syl3Term_default;
	public static String[] hobgoblin_syl3Trans = NamePiecesMods.hobgoblin_syl3Trans_default;
	public static String[] hobgoblin_syl4Term = NamePiecesMods.hobgoblin_syl4Term_default;
	public static String[] hobgoblin_syl4Trans = NamePiecesMods.hobgoblin_syl4Trans_default;
	public static String[] hobgoblin_syl5Term = NamePiecesMods.hobgoblin_syl5Term_default;
	
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		
		// Galacticraft names
		nameGCAlienVillagers = config.getBoolean("nameGCAlienVillagers", Configuration.CATEGORY_GENERAL, true,
				"Generates names for Galacticraft Alien Villagers");
		
		// More Planets names
		nameMPKoentusVillagers = config.getBoolean("nameMPKoentusVillagers", Configuration.CATEGORY_GENERAL, true,
				"Generates names for More Planets Koentus Villagers");
		nameMPFronosVillagers = config.getBoolean("nameMPFronosVillagers", Configuration.CATEGORY_GENERAL, true,
				"Generates names for More Planets Fronos Villagers");	
		nameMPNibiruVillagers = config.getBoolean("nameMPNibiruVillagers", Configuration.CATEGORY_GENERAL, true,
				"Generates names for More Planets Nibiru Villagers");	
		
		// Witchery names
		nameWitcheryWitchHunter = config.getBoolean("nameWitcheryWitchHunter", Configuration.CATEGORY_GENERAL, true,
				"Generates names for Witchery Witch Hunters");
		nameWitcheryVampire = config.getBoolean("nameWitcheryVampire", Configuration.CATEGORY_GENERAL, true,
				"Generates names for Witchery Vampires");
		nameWitcheryHobgoblins = config.getBoolean("nameWitcheryHobgoblins", Configuration.CATEGORY_GENERAL, true,
				"Generates names for Witchery Hobgoblins");
		nameWitcheryVillageGuards = config.getBoolean("nameWitcheryVillageGuards", Configuration.CATEGORY_GENERAL, true,
				"Generates names for Witchery Village Guards");
		
		// Mod profession mappings
		otherModProfessions = config.getStringList("otherModProfessions", "Mapping Professions",
				new String[]{
						"Brewer",
						"Apiarist",
						"Swordsmith",
						"Archaeologist",
						"Engineer",
						"Apothecary",
						"Music Merchant",
						"Tinkerer",
						"Enchanter"
						}, "Ordered list of mod villager Professions");
		otherModIDs = config.get("Mapping professions", "otherModIDs",
				new int[]{
						10,
						14,
						66,
						303,
						456,
						2435,
						6156,
						78943,
						935153
						}, "Ordered list of mod villager profession IDs").getIntList();
		vanillaProfMaps = config.get("Mapping professions", "vanillaProfMaps",
				new int[]{
						0,
						4,
						5,
						2,
						3,
						2,
						5,
						5,
						2
						}, "Ordered list of mod professions: this determines which vanilla archetype the villager emulates in order to generate hint pages."
								+ "Use this reference:\n"
								+ "0=Farmer\n"
								+ "1=Librarian\n"
								+ "2=Priest\n"
								+ "3=Blacksmith\n"
								+ "4=Butcher\n"
								+ "5=Nitwit\n").getIntList();
		
		//Java is stupid and I need to construct the default for the syllable lists INSIDE A METHOD, hurr durr
		// Here are all the string elements
		
		alienVillager_oneSylBegin = config.getStringList("alienVillager_oneSylBegin", "Syllable Pools", alienVillager_oneSylBegin, "Alien villager: First half of one-syllable name");
		alienVillager_oneSylEnd = config.getStringList("alienVillager_oneSylEnd", "Syllable Pools", alienVillager_oneSylEnd, "Alien villager: Second half of one-syllable name");
		alienVillager_syl1Trans = config.getStringList("alienVillager_syl1Trans", "Syllable Pools", alienVillager_syl1Trans, "Alien villager: Transitional first syllable");
		
		alienVillager_syl2Trans = config.getStringList("alienVillager_syl2Trans", "Syllable Pools", alienVillager_syl2Trans, "Alien villager: Transitional second syllable (use ^ for leading/ending spaces)");
		alienVillager_syl2Term = config.getStringList("alienVillager_syl2Term", "Syllable Pools", alienVillager_syl2Term, "Alien villager: Terminating second syllable (use ^ for leading/ending spaces)");
		
		alienVillager_syl3Trans = config.getStringList("alienVillager_syl3Trans", "Syllable Pools", alienVillager_syl3Trans, "Alien villager: Transitional third syllable (use ^ for leading/ending spaces)");
		alienVillager_syl3Term = config.getStringList("alienVillager_syl3Term", "Syllable Pools", alienVillager_syl3Term, "Alien villager: Terminating third syllable (use ^ for leading/ending spaces)");
		
		alienVillager_syl4Trans = config.getStringList("alienVillager_syl4Trans", "Syllable Pools", alienVillager_syl4Trans, "Alien villager: Transitional fourth syllable (use ^ for leading/ending spaces)");
		alienVillager_syl4Term = config.getStringList("alienVillager_syl4Term", "Syllable Pools", alienVillager_syl4Term, "Alien villager: Terminating fourth syllable (use ^ for leading/ending spaces)");
		
		alienVillager_syl5Trans = config.getStringList("alienVillager_syl5Trans", "Syllable Pools", alienVillager_syl5Trans, "Alien villager: Transitional fifth syllable (use ^ for leading/ending spaces)");
		alienVillager_syl5Term = config.getStringList("alienVillager_syl5Term", "Syllable Pools", alienVillager_syl5Term, "Alien villager: Terminating fifth syllable (use ^ for leading/ending spaces)");
		
		alienVillager_syl6Trans = config.getStringList("alienVillager_syl6Trans", "Syllable Pools", alienVillager_syl6Trans, "Alien villager: Transitional sixth syllable (use ^ for leading/ending spaces)");
		alienVillager_syl6Term = config.getStringList("alienVillager_syl6Term", "Syllable Pools", alienVillager_syl6Term, "Alien villager: Terminating sixth syllable (use ^ for leading/ending spaces)");
		
		alienVillager_syl7Term = config.getStringList("alienVillager_syl7Term", "Syllable Pools", alienVillager_syl7Term, "Alien villager: Terminating seventh syllable (use ^ for leading/ending spaces)");
		
		
		alienVillage_oneSylBegin = config.getStringList("alienVillage_oneSylBegin", "Syllable Pools", alienVillage_oneSylBegin, "Alien village: First half of one-syllable name");
		alienVillage_oneSylEnd = config.getStringList("alienVillage_oneSylEnd", "Syllable Pools", alienVillage_oneSylEnd, "Alien village: Second half of one-syllable name");
		alienVillage_syl1Trans = config.getStringList("alienVillage_syl1Trans", "Syllable Pools", alienVillage_syl1Trans, "Alien village: Transitional first syllable");
		
		alienVillage_syl2Trans = config.getStringList("alienVillage_syl2Trans", "Syllable Pools", alienVillage_syl2Trans, "Alien village: Transitional second syllable (use ^ for leading/ending spaces)");
		alienVillage_syl2Term = config.getStringList("alienVillage_syl2Term", "Syllable Pools", alienVillage_syl2Term, "Alien village: Terminating second syllable (use ^ for leading/ending spaces)");
		
		alienVillage_syl3Trans = config.getStringList("alienVillage_syl3Trans", "Syllable Pools", alienVillage_syl3Trans, "Alien village: Transitional third syllable (use ^ for leading/ending spaces)");
		alienVillage_syl3Term = config.getStringList("alienVillage_syl3Term", "Syllable Pools", alienVillage_syl3Term, "Alien village: Terminating third syllable (use ^ for leading/ending spaces)");
		
		alienVillage_syl4Trans = config.getStringList("alienVillage_syl4Trans", "Syllable Pools", alienVillage_syl4Trans, "Alien village: Transitional fourth syllable (use ^ for leading/ending spaces)");
		alienVillage_syl4Term = config.getStringList("alienVillage_syl4Term", "Syllable Pools", alienVillage_syl4Term, "Alien village: Terminating fourth syllable (use ^ for leading/ending spaces)");
		
		alienVillage_syl5Trans = config.getStringList("alienVillage_syl5Trans", "Syllable Pools", alienVillage_syl5Trans, "Alien village: Transitional fifth syllable (use ^ for leading/ending spaces)");
		alienVillage_syl5Term = config.getStringList("alienVillage_syl5Term", "Syllable Pools", alienVillage_syl5Term, "Alien village: Terminating fifth syllable (use ^ for leading/ending spaces)");
		
		alienVillage_syl6Term = config.getStringList("alienVillage_syl6Term", "Syllable Pools", alienVillage_syl6Term, "Alien village: Terminating sixth syllable (use ^ for leading/ending spaces)");
		
		
		hobgoblin_oneSylBegin = config.getStringList("hobgoblin_oneSylBegin", "Syllable Pools", hobgoblin_oneSylBegin, "Hobgoblin: First half of one-syllable name");
		hobgoblin_oneSylEnd = config.getStringList("hobgoblin_oneSylEnd", "Syllable Pools", hobgoblin_oneSylEnd, "Hobgoblin: Second half of one-syllable name");
		hobgoblin_syl1Trans = config.getStringList("hobgoblin_syl1Trans", "Syllable Pools", hobgoblin_syl1Trans, "Hobgoblin: Transitional first syllable");
		
		hobgoblin_syl2Trans = config.getStringList("hobgoblin_syl2Trans", "Syllable Pools", hobgoblin_syl2Trans, "Hobgoblin: Transitional second syllable (use ^ for leading/ending spaces)");
		hobgoblin_syl2Term = config.getStringList("hobgoblin_syl2Term", "Syllable Pools", hobgoblin_syl2Term, "Hobgoblin: Terminating second syllable (use ^ for leading/ending spaces)");
		
		hobgoblin_syl3Trans = config.getStringList("hobgoblin_syl3Trans", "Syllable Pools", hobgoblin_syl3Trans, "Hobgoblin: Transitional third syllable (use ^ for leading/ending spaces)");
		hobgoblin_syl3Term = config.getStringList("hobgoblin_syl3Term", "Syllable Pools", hobgoblin_syl3Term, "Hobgoblin: Terminating third syllable (use ^ for leading/ending spaces)");
		
		hobgoblin_syl4Trans = config.getStringList("hobgoblin_syl4Trans", "Syllable Pools", hobgoblin_syl4Trans, "Hobgoblin: Transitional fourth syllable (use ^ for leading/ending spaces)");
		hobgoblin_syl4Term = config.getStringList("hobgoblin_syl4Term", "Syllable Pools", hobgoblin_syl4Term, "Hobgoblin: Terminating fourth syllable (use ^ for leading/ending spaces)");
		
		hobgoblin_syl5Term = config.getStringList("hobgoblin_syl5Term", "Syllable Pools", hobgoblin_syl5Term, "Hobgoblin: Terminating fifth syllable (use ^ for leading/ending spaces)");
		
		
		
	    if (config.hasChanged()) config.save();
	}
	
	@SubscribeEvent
	public void onConfigurationChangedEvent(cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
}
