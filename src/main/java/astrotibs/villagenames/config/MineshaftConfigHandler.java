package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import net.minecraftforge.common.config.Configuration;

public class MineshaftConfigHandler {
	public static Configuration config;
	
	// Mineshaft name pieces
	public static String[] mineshaft_prefix = NamePieces.mineshaft_prefix_default;
	public static String[] mineshaft_suffix = NamePieces.mineshaft_suffix_default;
	
	public static String[] mineshaft_oneSylBegin = NamePieces.mineshaft_oneSylBegin_default;
	public static String[] mineshaft_oneSylEnd = NamePieces.mineshaft_oneSylEnd_default;
	public static String[] mineshaft_syl1Trans = NamePieces.mineshaft_syl1Trans_default;
	
	public static String[] mineshaft_syl2Term = NamePieces.mineshaft_syl2Term_default;
	public static String[] mineshaft_syl2Trans = NamePieces.mineshaft_syl2Trans_default;
	
	public static String[] mineshaft_syl3Term = NamePieces.mineshaft_syl3Term_default;
	public static String[] mineshaft_syl3Trans = NamePieces.mineshaft_syl3Trans_default;
	
	public static String[] mineshaft_syl4Term = NamePieces.mineshaft_syl4Term_default;
	public static String[] mineshaft_syl4Trans = NamePieces.mineshaft_syl4Trans_default;
	
	public static String[] mineshaft_syl5Term = NamePieces.mineshaft_syl5Term_default;
	public static String[] mineshaft_syl5Trans = NamePieces.mineshaft_syl5Trans_default;
	
	public static String[] mineshaft_syl6Term = NamePieces.mineshaft_syl6Term_default;
	public static String[] mineshaft_syl6Trans = NamePieces.mineshaft_syl6Trans_default;
	
	public static String[] mineshaft_syl7Term = NamePieces.mineshaft_syl7Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	protected static void loadConfiguration() {
		
		//Java is stupid and I need to construct the default for the syllable lists INSIDE A METHOD, hurr durr
		// Here are all the string elements
		mineshaft_prefix = config.getStringList("Name Prefix", "Mineshaft Syllable Pool", mineshaft_prefix, "Prefixes that can occur before name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		mineshaft_suffix = config.getStringList("Name Suffix", "Mineshaft Syllable Pool", mineshaft_suffix, "Suffixes that can occur after name");
		
		mineshaft_oneSylBegin = config.getStringList("One-Syllable Begin", "Mineshaft Syllable Pool", mineshaft_oneSylBegin, "First half of one-syllable name");
		mineshaft_oneSylEnd = config.getStringList("One-Syllable End", "Mineshaft Syllable Pool", mineshaft_oneSylEnd, "Second half of one-syllable name");
		mineshaft_syl1Trans = config.getStringList("Syllable 1 Transitional", "Mineshaft Syllable Pool", mineshaft_syl1Trans, "Transitional first syllable");
		
		mineshaft_syl2Term = config.getStringList("Syllable 2 Terminal", "Mineshaft Syllable Pool", mineshaft_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		mineshaft_syl2Trans = config.getStringList("Syllable 2 Transitional", "Mineshaft Syllable Pool", mineshaft_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		
		mineshaft_syl3Term = config.getStringList("Syllable 3 Terminal", "Mineshaft Syllable Pool", mineshaft_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		mineshaft_syl3Trans = config.getStringList("Syllable 3 Transitional", "Mineshaft Syllable Pool", mineshaft_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		
		mineshaft_syl4Term = config.getStringList("Syllable 4 Terminal", "Mineshaft Syllable Pool", mineshaft_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		mineshaft_syl4Trans = config.getStringList("Syllable 4 Transitional", "Mineshaft Syllable Pool", mineshaft_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		
		mineshaft_syl5Term = config.getStringList("Syllable 5 Terminal", "Mineshaft Syllable Pool", mineshaft_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		mineshaft_syl5Trans = config.getStringList("Syllable 5 Transitional", "Mineshaft Syllable Pool", mineshaft_syl5Trans, "Transitional fifth syllable (use ^ for leading/ending spaces)");
		
		mineshaft_syl6Term = config.getStringList("Syllable 6 Terminal", "Mineshaft Syllable Pool", mineshaft_syl6Term, "Terminating sixth syllable (use ^ for leading/ending spaces)");
		mineshaft_syl6Trans = config.getStringList("Syllable 6 Transitional", "Mineshaft Syllable Pool", mineshaft_syl6Trans, "Transitional sixth syllable (use ^ for leading/ending spaces)");
		
		mineshaft_syl7Term = config.getStringList("Syllable 7 Terminal", "Mineshaft Syllable Pool", mineshaft_syl7Term, "Terminating seventh syllable (use ^ for leading/ending spaces)");
		
		if (config.hasChanged()) config.save();
	}
	
}
