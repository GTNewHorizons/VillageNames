package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import net.minecraftforge.common.config.Configuration;

public class EndCityConfigHandler {
	
	public static Configuration config;
	
	// Mansion name pieces
	public static String[] endcity_prefix = NamePieces.endcity_prefix_default;
	public static String[] endcity_suffix = NamePieces.endcity_suffix_default;
	
	public static String[] endcity_oneSylBegin = NamePieces.endcity_oneSylBegin_default;
	public static String[] endcity_oneSylEnd = NamePieces.endcity_oneSylEnd_default;
	public static String[] endcity_syl1Trans = NamePieces.endcity_syl1Trans_default;
	
	public static String[] endcity_syl2Term = NamePieces.endcity_syl2Term_default;
	public static String[] endcity_syl2Trans = NamePieces.endcity_syl2Trans_default;
	
	public static String[] endcity_syl3Term = NamePieces.endcity_syl3Term_default;
	public static String[] endcity_syl3Trans = NamePieces.endcity_syl3Trans_default;
	
	public static String[] endcity_syl4Term = NamePieces.endcity_syl4Term_default;
	public static String[] endcity_syl4Trans = NamePieces.endcity_syl4Trans_default;
	
	public static String[] endcity_syl5Term = NamePieces.endcity_syl5Term_default;
	public static String[] endcity_syl5Trans = NamePieces.endcity_syl5Trans_default;
	
	public static String[] endcity_syl6Term = NamePieces.endcity_syl6Term_default;
	public static String[] endcity_syl6Trans = NamePieces.endcity_syl6Trans_default;
	
	public static String[] endcity_syl7Term = NamePieces.endcity_syl7Term_default;
	
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	protected static void loadConfiguration() {
		
		//Java is stupid and I need to construct the default for the syllable lists INSIDE A METHOD, hurr durr
		// Here are all the string elements
		endcity_prefix = config.getStringList("Name Prefix", "End City Syllable Pool", endcity_prefix, "Prefixes that can occur before name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		endcity_suffix = config.getStringList("Name Suffix", "End City Syllable Pool", endcity_suffix, "Suffixes that can occur after name");
		
		endcity_oneSylBegin = config.getStringList("One-Syllable Begin", "End City Syllable Pool", endcity_oneSylBegin, "First half of one-syllable name");
		endcity_oneSylEnd = config.getStringList("One-Syllable End", "End City Syllable Pool", endcity_oneSylEnd, "Second half of one-syllable name");
		endcity_syl1Trans = config.getStringList("Syllable 1 Transitional", "End City Syllable Pool", endcity_syl1Trans, "Transitional first syllable");
		
		endcity_syl2Term = config.getStringList("Syllable 2 Terminal", "End City Syllable Pool", endcity_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		endcity_syl2Trans = config.getStringList("Syllable 2 Transitional", "End City Syllable Pool", endcity_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		
		endcity_syl3Term = config.getStringList("Syllable 3 Terminal", "End City Syllable Pool", endcity_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		endcity_syl3Trans = config.getStringList("Syllable 3 Transitional", "End City Syllable Pool", endcity_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		
		endcity_syl4Term = config.getStringList("Syllable 4 Terminal", "End City Syllable Pool", endcity_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		endcity_syl4Trans = config.getStringList("Syllable 4 Transitional", "End City Syllable Pool", endcity_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		
		endcity_syl5Term = config.getStringList("Syllable 5 Terminal", "End City Syllable Pool", endcity_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		endcity_syl5Trans = config.getStringList("Syllable 5 Transitional", "End City Syllable Pool", endcity_syl5Trans, "Transitional fifth syllable (use ^ for leading/ending spaces)");
		
		endcity_syl6Term = config.getStringList("Syllable 6 Terminal", "End City Syllable Pool", endcity_syl6Term, "Terminating sixth syllable (use ^ for leading/ending spaces)");
		endcity_syl6Trans = config.getStringList("Syllable 6 Transitional", "End City Syllable Pool", endcity_syl6Trans, "Transitional sixth syllable (use ^ for leading/ending spaces)");
		
		endcity_syl7Term = config.getStringList("Syllable 7 Terminal", "End City Syllable Pool", endcity_syl7Term, "Terminating seventh syllable (use ^ for leading/ending spaces)");
		
	    if (config.hasChanged()) config.save();
	}
	
}
