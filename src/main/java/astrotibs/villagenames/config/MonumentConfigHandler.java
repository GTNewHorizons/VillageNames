package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import net.minecraftforge.common.config.Configuration;

public class MonumentConfigHandler {
	
	public static Configuration config;
	
	// Monument name pieces
	public static String[] monument_prefix = NamePieces.monument_prefix_default;
	public static String[] monument_suffix = NamePieces.monument_suffix_default;
	
	public static String[] monument_oneSylBegin = NamePieces.monument_oneSylBegin_default;
	public static String[] monument_oneSylEnd = NamePieces.monument_oneSylEnd_default;
	public static String[] monument_syl1Trans = NamePieces.monument_syl1Trans_default;
	
	public static String[] monument_syl2Term = NamePieces.monument_syl2Term_default;
	public static String[] monument_syl2Trans = NamePieces.monument_syl2Trans_default;
	
	public static String[] monument_syl3Term = NamePieces.monument_syl3Term_default;
	public static String[] monument_syl3Trans = NamePieces.monument_syl3Trans_default;
	
	public static String[] monument_syl4Term = NamePieces.monument_syl4Term_default;
	public static String[] monument_syl4Trans = NamePieces.monument_syl4Trans_default;
	
	public static String[] monument_syl5Term = NamePieces.monument_syl5Term_default;
	public static String[] monument_syl5Trans = NamePieces.monument_syl5Trans_default;
	
	public static String[] monument_syl6Term = NamePieces.monument_syl6Term_default;
	public static String[] monument_syl6Trans = NamePieces.monument_syl6Trans_default;
	
	public static String[] monument_syl7Term = NamePieces.monument_syl7Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	protected static void loadConfiguration() {
		
		//Java is stupid and I need to construct the default for the syllable lists INSIDE A METHOD, hurr durr
		// Here are all the string elements
		monument_prefix = config.getStringList("Name Prefix", "Monument Syllable Pool", monument_prefix, "Prefixes that can occur before name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		monument_suffix = config.getStringList("Name Suffix", "Monument Syllable Pool", monument_suffix, "Suffixes that can occur after name");
		
		monument_oneSylBegin = config.getStringList("One-Syllable Begin", "Monument Syllable Pool", monument_oneSylBegin, "First half of one-syllable name");
		monument_oneSylEnd = config.getStringList("One-Syllable End", "Monument Syllable Pool", monument_oneSylEnd, "Second half of one-syllable name");
		monument_syl1Trans = config.getStringList("Syllable 1 Transitional", "Monument Syllable Pool", monument_syl1Trans, "Transitional first syllable");
		
		monument_syl2Term = config.getStringList("Syllable 2 Terminal", "Monument Syllable Pool", monument_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		monument_syl2Trans = config.getStringList("Syllable 2 Transitional", "Monument Syllable Pool", monument_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		
		monument_syl3Term = config.getStringList("Syllable 3 Terminal", "Monument Syllable Pool", monument_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		monument_syl3Trans = config.getStringList("Syllable 3 Transitional", "Monument Syllable Pool", monument_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		
		monument_syl4Term = config.getStringList("Syllable 4 Terminal", "Monument Syllable Pool", monument_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		monument_syl4Trans = config.getStringList("Syllable 4 Transitional", "Monument Syllable Pool", monument_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		
		monument_syl5Term = config.getStringList("Syllable 5 Terminal", "Monument Syllable Pool", monument_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		monument_syl5Trans = config.getStringList("Syllable 5 Transitional", "Monument Syllable Pool", monument_syl5Trans, "Transitional fifth syllable (use ^ for leading/ending spaces)");
		
		monument_syl6Term = config.getStringList("Syllable 6 Terminal", "Monument Syllable Pool", monument_syl6Term, "Terminating sixth syllable (use ^ for leading/ending spaces)");
		monument_syl6Trans = config.getStringList("Syllable 6 Transitional", "Monument Syllable Pool", monument_syl6Trans, "Transitional sixth syllable (use ^ for leading/ending spaces)");
		
		monument_syl7Term = config.getStringList("Syllable 7 Terminal", "Monument Syllable Pool", monument_syl7Term, "Terminating seventh syllable (use ^ for leading/ending spaces)");
		
		if (config.hasChanged()) config.save();
	}
	
}
