package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import net.minecraftforge.common.config.Configuration;

public class CustomConfigHandler {
	public static Configuration config;
	
	// Custom names
	public static String[] custom_prefix = NamePieces.custom_prefix_default;
	public static String[] custom_suffix = NamePieces.custom_suffix_default;
	
	public static String[] custom_oneSylBegin = NamePieces.custom_oneSylBegin_default;
	public static String[] custom_oneSylEnd = NamePieces.custom_oneSylEnd_default;
	public static String[] custom_syl1Trans = NamePieces.custom_syl1Trans_default;
	
	public static String[] custom_syl2Term = NamePieces.custom_syl2Term_default;
	public static String[] custom_syl2Trans = NamePieces.custom_syl2Trans_default;
	
	public static String[] custom_syl3Term = NamePieces.custom_syl3Term_default;
	public static String[] custom_syl3Trans = NamePieces.custom_syl3Trans_default;
	
	public static String[] custom_syl4Term = NamePieces.custom_syl4Term_default;
	public static String[] custom_syl4Trans = NamePieces.custom_syl4Trans_default;
	
	public static String[] custom_syl5Term = NamePieces.custom_syl5Term_default;
	public static String[] custom_syl5Trans = NamePieces.custom_syl5Trans_default;
	
	public static String[] custom_syl6Term = NamePieces.custom_syl6Term_default;
	public static String[] custom_syl6Trans = NamePieces.custom_syl6Trans_default;
	
	public static String[] custom_syl7Term = NamePieces.custom_syl7Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	protected static void loadConfiguration() {
		
		custom_prefix = config.getStringList("Name Prefix", "Custom Syllable Pool", custom_prefix, "Prefixes that can occur before name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		custom_suffix = config.getStringList("Name Suffix", "Custom Syllable Pool", custom_suffix, "Suffixes that can occur after name");
		
		custom_oneSylBegin = config.getStringList("One-Syllable Begin", "Custom Syllable Pool", custom_oneSylBegin, "First half of one-syllable name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		custom_oneSylEnd = config.getStringList("One-Syllable End", "Custom Syllable Pool", custom_oneSylEnd, "Second half of one-syllable name");
		custom_syl1Trans = config.getStringList("Syllable 1 Transitional", "Custom Syllable Pool", custom_syl1Trans, "Transitional first syllable");
		
		custom_syl2Term = config.getStringList("Syllable 2 Terminal", "Custom Syllable Pool", custom_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		custom_syl2Trans = config.getStringList("Syllable 2 Transitional", "Custom Syllable Pool", custom_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		
		custom_syl3Term = config.getStringList("Syllable 3 Terminal", "Custom Syllable Pool", custom_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		custom_syl3Trans = config.getStringList("Syllable 3 Transitional", "Custom Syllable Pool", custom_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		
		custom_syl4Term = config.getStringList("Syllable 4 Terminal", "Custom Syllable Pool", custom_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		custom_syl4Trans = config.getStringList("Syllable 4 Transitional", "Custom Syllable Pool", custom_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		
		custom_syl5Term = config.getStringList("Syllable 5 Terminal", "Custom Syllable Pool", custom_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		custom_syl5Trans = config.getStringList("Syllable 5 Transitional", "Custom Syllable Pool", custom_syl5Trans, "Transitional fifth syllable (use ^ for leading/ending spaces)");
		
		custom_syl6Term = config.getStringList("Syllable 6 Terminal", "Custom Syllable Pool", custom_syl6Term, "Terminating sixth syllable (use ^ for leading/ending spaces)");
		custom_syl6Trans = config.getStringList("Syllable 6 Transitional", "Custom Syllable Pool", custom_syl6Trans, "Transitional sixth syllable (use ^ for leading/ending spaces)");
		
		custom_syl7Term = config.getStringList("Syllable 7 Terminal", "Custom Syllable Pool", custom_syl7Term, "Terminating seventh syllable (use ^ for leading/ending spaces)");
		
		if (config.hasChanged()) config.save();
	}
	
}
