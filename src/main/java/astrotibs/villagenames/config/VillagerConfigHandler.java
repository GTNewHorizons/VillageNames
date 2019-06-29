package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePiecesEntities;
import net.minecraftforge.common.config.Configuration;

public class VillagerConfigHandler {
	public static Configuration config;
	
	// Villager names
	public static String[] villager_prefix = NamePiecesEntities.villager_prefix_default;
	public static String[] villager_suffix = NamePiecesEntities.villager_suffix_default;
	
	public static String[] villager_oneSylBegin = NamePiecesEntities.villager_oneSylBegin_default;
	public static String[] villager_oneSylEnd = NamePiecesEntities.villager_oneSylEnd_default;
	public static String[] villager_syl1Trans = NamePiecesEntities.villager_syl1Trans_default;
	
	public static String[] villager_syl2Term = NamePiecesEntities.villager_syl2Term_default;
	public static String[] villager_syl2Trans = NamePiecesEntities.villager_syl2Trans_default;
	
	public static String[] villager_syl3Term = NamePiecesEntities.villager_syl3Term_default;
	public static String[] villager_syl3Trans = NamePiecesEntities.villager_syl3Trans_default;
	
	public static String[] villager_syl4Term = NamePiecesEntities.villager_syl4Term_default;
	public static String[] villager_syl4Trans = NamePiecesEntities.villager_syl4Trans_default;
	
	public static String[] villager_syl5Term = NamePiecesEntities.villager_syl5Term_default;
	public static String[] villager_syl5Trans = NamePiecesEntities.villager_syl5Trans_default;
	
	public static String[] villager_syl6Term = NamePiecesEntities.villager_syl6Term_default;
	public static String[] villager_syl6Trans = NamePiecesEntities.villager_syl6Trans_default;
	
	public static String[] villager_syl7Term = NamePiecesEntities.villager_syl7Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	protected static void loadConfiguration() {
		
		// Here are the elements for the villager names
		villager_prefix = config.getStringList("Name Prefix", "Villager Syllable Pool", villager_prefix, "Prefixes that can occur before name");
		villager_suffix = config.getStringList("Name Suffix", "Villager Syllable Pool", villager_suffix, "Suffixes that can occur after name");
		
		villager_oneSylBegin = config.getStringList("One-Syllable Begin", "Villager Syllable Pool", villager_oneSylBegin, "First half of one-syllable name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		villager_oneSylEnd = config.getStringList("One-Syllable End", "Villager Syllable Pool", villager_oneSylEnd, "Second half of one-syllable name");
		villager_syl1Trans = config.getStringList("Syllable 1 Transitional", "Villager Syllable Pool", villager_syl1Trans, "Transitional first syllable");
		
		villager_syl2Term = config.getStringList("Syllable 2 Terminal", "Villager Syllable Pool", villager_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		villager_syl2Trans = config.getStringList("Syllable 2 Transitional", "Villager Syllable Pool", villager_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		
		villager_syl3Term = config.getStringList("Syllable 3 Terminal", "Villager Syllable Pool", villager_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		villager_syl3Trans = config.getStringList("Syllable 3 Transitional", "Villager Syllable Pool", villager_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		
		villager_syl4Term = config.getStringList("Syllable 4 Terminal", "Villager Syllable Pool", villager_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		villager_syl4Trans = config.getStringList("Syllable 4 Transitional", "Villager Syllable Pool", villager_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		
		villager_syl5Term = config.getStringList("Syllable 5 Terminal", "Villager Syllable Pool", villager_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		villager_syl5Trans = config.getStringList("Syllable 5 Transitional", "Villager Syllable Pool", villager_syl5Trans, "Transitional fifth syllable (use ^ for leading/ending spaces)");
		
		villager_syl6Term = config.getStringList("Syllable 6 Terminal", "Villager Syllable Pool", villager_syl6Term, "Terminating sixth syllable (use ^ for leading/ending spaces)");
		villager_syl6Trans = config.getStringList("Syllable 6 Transitional", "Villager Syllable Pool", villager_syl6Trans, "Transitional sixth syllable (use ^ for leading/ending spaces)");
		
		villager_syl7Term = config.getStringList("Syllable 7 Terminal", "Villager Syllable Pool", villager_syl7Term, "Terminating seventh syllable (use ^ for leading/ending spaces)");
		
		if (config.hasChanged()) config.save();
	}
	
}
