package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import net.minecraftforge.common.config.Configuration;

public class VillageConfigHandler {
	public static Configuration config;
	
	// Village name pieces
	public static String[] village_prefix = NamePieces.village_prefix_default;
	public static String[] village_suffix = NamePieces.village_suffix_default;
	
	public static String[] village_oneSylBegin = NamePieces.village_oneSylBegin_default;
	public static String[] village_oneSylEnd = NamePieces.village_oneSylEnd_default;
	public static String[] village_syl1Trans = NamePieces.village_syl1Trans_default;
	
	public static String[] village_syl2Term = NamePieces.village_syl2Term_default;
	public static String[] village_syl2Trans = NamePieces.village_syl2Trans_default;
	
	public static String[] village_syl3Term = NamePieces.village_syl3Term_default;
	public static String[] village_syl3Trans = NamePieces.village_syl3Trans_default;
	
	public static String[] village_syl4Term = NamePieces.village_syl4Term_default;
	public static String[] village_syl4Trans = NamePieces.village_syl4Trans_default;
	
	public static String[] village_syl5Term = NamePieces.village_syl5Term_default;
	public static String[] village_syl5Trans = NamePieces.village_syl5Trans_default;
	
	public static String[] village_syl6Term = NamePieces.village_syl6Term_default;
	public static String[] village_syl6Trans = NamePieces.village_syl6Trans_default;
	
	public static String[] village_syl7Term = NamePieces.village_syl7Term_default;
	
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	protected static void loadConfiguration() {
		
		//Java is stupid and I need to construct the default for the syllable lists INSIDE A METHOD, hurr durr
		// Here are all the string elements
		village_prefix = config.getStringList("Name Prefix", "Village Syllable Pool", village_prefix, "Prefixes that can occur before name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		village_suffix = config.getStringList("Name Suffix", "Village Syllable Pool", village_suffix, "Suffixes that can occur after name");
		
		village_oneSylBegin = config.getStringList("One-Syllable Begin", "Village Syllable Pool", village_oneSylBegin, "First half of one-syllable name");
		village_oneSylEnd = config.getStringList("One-Syllable End", "Village Syllable Pool", village_oneSylEnd, "Second half of one-syllable name");
		village_syl1Trans = config.getStringList("Syllable 1 Transitional", "Village Syllable Pool", village_syl1Trans, "Transitional first syllable");
		
		village_syl2Term = config.getStringList("Syllable 2 Terminal", "Village Syllable Pool", village_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		village_syl2Trans = config.getStringList("Syllable 2 Transitional", "Village Syllable Pool", village_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		
		village_syl3Term = config.getStringList("Syllable 3 Terminal", "Village Syllable Pool", village_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		village_syl3Trans = config.getStringList("Syllable 3 Transitional", "Village Syllable Pool", village_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		
		village_syl4Term = config.getStringList("Syllable 4 Terminal", "Village Syllable Pool", village_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		village_syl4Trans = config.getStringList("Syllable 4 Transitional", "Village Syllable Pool", village_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		
		village_syl5Term = config.getStringList("Syllable 5 Terminal", "Village Syllable Pool", village_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		village_syl5Trans = config.getStringList("Syllable 5 Transitional", "Village Syllable Pool", village_syl5Trans, "Transitional fifth syllable (use ^ for leading/ending spaces)");
		
		village_syl6Term = config.getStringList("Syllable 6 Terminal", "Village Syllable Pool", village_syl6Term, "Terminating sixth syllable (use ^ for leading/ending spaces)");
		village_syl6Trans = config.getStringList("Syllable 6 Transitional", "Village Syllable Pool", village_syl6Trans, "Transitional sixth syllable (use ^ for leading/ending spaces)");
		
		village_syl7Term = config.getStringList("Syllable 7 Terminal", "Village Syllable Pool", village_syl7Term, "Terminating seventh syllable (use ^ for leading/ending spaces)");
		
	    if (config.hasChanged()) config.save();
	}
	
}
