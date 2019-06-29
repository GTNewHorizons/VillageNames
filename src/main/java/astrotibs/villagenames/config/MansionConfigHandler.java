package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import net.minecraftforge.common.config.Configuration;

public class MansionConfigHandler {
	
	public static Configuration config;
	
	// Mansion name pieces
	public static String[] mansion_prefix = NamePieces.mansion_prefix_default;
	public static String[] mansion_suffix = NamePieces.mansion_suffix_default;
	
	public static String[] mansion_oneSylBegin = NamePieces.mansion_oneSylBegin_default;
	public static String[] mansion_oneSylEnd = NamePieces.mansion_oneSylEnd_default;
	public static String[] mansion_syl1Trans = NamePieces.mansion_syl1Trans_default;
	
	public static String[] mansion_syl2Term = NamePieces.mansion_syl2Term_default;
	public static String[] mansion_syl2Trans = NamePieces.mansion_syl2Trans_default;
	
	public static String[] mansion_syl3Term = NamePieces.mansion_syl3Term_default;
	public static String[] mansion_syl3Trans = NamePieces.mansion_syl3Trans_default;
	
	public static String[] mansion_syl4Term = NamePieces.mansion_syl4Term_default;
	public static String[] mansion_syl4Trans = NamePieces.mansion_syl4Trans_default;
	
	public static String[] mansion_syl5Term = NamePieces.mansion_syl5Term_default;
	public static String[] mansion_syl5Trans = NamePieces.mansion_syl5Trans_default;
	
	public static String[] mansion_syl6Term = NamePieces.mansion_syl6Term_default;
	public static String[] mansion_syl6Trans = NamePieces.mansion_syl6Trans_default;
	
	public static String[] mansion_syl7Term = NamePieces.mansion_syl7Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	protected static void loadConfiguration() {
		
		//Java is stupid and I need to construct the default for the syllable lists INSIDE A METHOD, hurr durr
		// Here are all the string elements
		mansion_prefix = config.getStringList("Name Prefix", "Mansion Syllable Pool", mansion_prefix, "Prefixes that can occur before name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		mansion_suffix = config.getStringList("Name Suffix", "Mansion Syllable Pool", mansion_suffix, "Suffixes that can occur after name");
		
		mansion_oneSylBegin = config.getStringList("One-Syllable Begin", "Mansion Syllable Pool", mansion_oneSylBegin, "First half of one-syllable name");
		mansion_oneSylEnd = config.getStringList("One-Syllable End", "Mansion Syllable Pool", mansion_oneSylEnd, "Second half of one-syllable name");
		mansion_syl1Trans = config.getStringList("Syllable 1 Transitional", "Mansion Syllable Pool", mansion_syl1Trans, "Transitional first syllable");
		
		mansion_syl2Term = config.getStringList("Syllable 2 Terminal", "Mansion Syllable Pool", mansion_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		mansion_syl2Trans = config.getStringList("Syllable 2 Transitional", "Mansion Syllable Pool", mansion_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		
		mansion_syl3Term = config.getStringList("Syllable 3 Terminal", "Mansion Syllable Pool", mansion_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		mansion_syl3Trans = config.getStringList("Syllable 3 Transitional", "Mansion Syllable Pool", mansion_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		
		mansion_syl4Term = config.getStringList("Syllable 4 Terminal", "Mansion Syllable Pool", mansion_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		mansion_syl4Trans = config.getStringList("Syllable 4 Transitional", "Mansion Syllable Pool", mansion_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		
		mansion_syl5Term = config.getStringList("Syllable 5 Terminal", "Mansion Syllable Pool", mansion_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		mansion_syl5Trans = config.getStringList("Syllable 5 Transitional", "Mansion Syllable Pool", mansion_syl5Trans, "Transitional fifth syllable (use ^ for leading/ending spaces)");
		
		mansion_syl6Term = config.getStringList("Syllable 6 Terminal", "Mansion Syllable Pool", mansion_syl6Term, "Terminating sixth syllable (use ^ for leading/ending spaces)");
		mansion_syl6Trans = config.getStringList("Syllable 6 Transitional", "Mansion Syllable Pool", mansion_syl6Trans, "Transitional sixth syllable (use ^ for leading/ending spaces)");
		
		mansion_syl7Term = config.getStringList("Syllable 7 Terminal", "Mansion Syllable Pool", mansion_syl7Term, "Terminating seventh syllable (use ^ for leading/ending spaces)");
		
		if (config.hasChanged()) config.save();
	}
	
}
