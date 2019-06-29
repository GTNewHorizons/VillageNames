package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import net.minecraftforge.common.config.Configuration;

public class TempleConfigHandler {
	public static Configuration config;
	
	// Temple name pieces
	public static String[] temple_prefix = NamePieces.temple_prefix_default;
	public static String[] temple_suffix = NamePieces.temple_suffix_default;
	
	public static String[] temple_oneSylBegin = NamePieces.temple_oneSylBegin_default;
	public static String[] temple_oneSylEnd = NamePieces.temple_oneSylEnd_default;
	public static String[] temple_syl1Trans = NamePieces.temple_syl1Trans_default;
	
	public static String[] temple_syl2Term = NamePieces.temple_syl2Term_default;
	public static String[] temple_syl2Trans = NamePieces.temple_syl2Trans_default;
	
	public static String[] temple_syl3Term = NamePieces.temple_syl3Term_default;
	public static String[] temple_syl3Trans = NamePieces.temple_syl3Trans_default;
	
	public static String[] temple_syl4Term = NamePieces.temple_syl4Term_default;
	public static String[] temple_syl4Trans = NamePieces.temple_syl4Trans_default;
	
	public static String[] temple_syl5Term = NamePieces.temple_syl5Term_default;
	public static String[] temple_syl5Trans = NamePieces.temple_syl5Trans_default;
	
	public static String[] temple_syl6Term = NamePieces.temple_syl6Term_default;
	public static String[] temple_syl6Trans = NamePieces.temple_syl6Trans_default;
	
	public static String[] temple_syl7Term = NamePieces.temple_syl7Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	protected static void loadConfiguration() {
		
		//Java is stupid and I need to construct the default for the syllable lists INSIDE A METHOD, hurr durr
		// Here are all the string elements
		temple_prefix = config.getStringList("Name Prefix", "Temple Syllable Pool", temple_prefix, "Prefixes that can occur before name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		temple_suffix = config.getStringList("Name Suffix", "Temple Syllable Pool", temple_suffix, "Suffixes that can occur after name");
		
		temple_oneSylBegin = config.getStringList("One-Syllable Begin", "Temple Syllable Pool", temple_oneSylBegin, "First half of one-syllable name");
		temple_oneSylEnd = config.getStringList("One-Syllable End", "Temple Syllable Pool", temple_oneSylEnd, "Second half of one-syllable name");
		temple_syl1Trans = config.getStringList("Syllable 1 Transitional", "Temple Syllable Pool", temple_syl1Trans, "Transitional first syllable");
		
		temple_syl2Term = config.getStringList("Syllable 2 Terminal", "Temple Syllable Pool", temple_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		temple_syl2Trans = config.getStringList("Syllable 2 Transitional", "Temple Syllable Pool", temple_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		
		temple_syl3Term = config.getStringList("Syllable 3 Terminal", "Temple Syllable Pool", temple_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		temple_syl3Trans = config.getStringList("Syllable 3 Transitional", "Temple Syllable Pool", temple_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		
		temple_syl4Term = config.getStringList("Syllable 4 Terminal", "Temple Syllable Pool", temple_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		temple_syl4Trans = config.getStringList("Syllable 4 Transitional", "Temple Syllable Pool", temple_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		
		temple_syl5Term = config.getStringList("Syllable 5 Terminal", "Temple Syllable Pool", temple_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		temple_syl5Trans = config.getStringList("Syllable 5 Transitional", "Temple Syllable Pool", temple_syl5Trans, "Transitional fifth syllable (use ^ for leading/ending spaces)");
		
		temple_syl6Term = config.getStringList("Syllable 6 Terminal", "Temple Syllable Pool", temple_syl6Term, "Terminating sixth syllable (use ^ for leading/ending spaces)");
		temple_syl6Trans = config.getStringList("Syllable 6 Transitional", "Temple Syllable Pool", temple_syl6Trans, "Transitional sixth syllable (use ^ for leading/ending spaces)");
		
		temple_syl7Term = config.getStringList("Syllable 7 Terminal", "Temple Syllable Pool", temple_syl7Term, "Terminating seventh syllable (use ^ for leading/ending spaces)");
		
		if (config.hasChanged()) config.save();
	}
	
}