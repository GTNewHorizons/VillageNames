package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import net.minecraftforge.common.config.Configuration;

public class StrongholdConfigHandler {
	public static Configuration config;
	
	// Stronghold name pieces
	public static String[] stronghold_prefix = NamePieces.stronghold_prefix_default;
	public static String[] stronghold_suffix = NamePieces.stronghold_suffix_default;
	
	public static String[] stronghold_oneSylBegin = NamePieces.stronghold_oneSylBegin_default;
	public static String[] stronghold_oneSylEnd = NamePieces.stronghold_oneSylEnd_default;
	public static String[] stronghold_syl1Trans = NamePieces.stronghold_syl1Trans_default;
	
	public static String[] stronghold_syl2Term = NamePieces.stronghold_syl2Term_default;
	public static String[] stronghold_syl2Trans = NamePieces.stronghold_syl2Trans_default;
	
	public static String[] stronghold_syl3Term = NamePieces.stronghold_syl3Term_default;
	public static String[] stronghold_syl3Trans = NamePieces.stronghold_syl3Trans_default;
	
	public static String[] stronghold_syl4Term = NamePieces.stronghold_syl4Term_default;
	public static String[] stronghold_syl4Trans = NamePieces.stronghold_syl4Trans_default;
	
	public static String[] stronghold_syl5Term = NamePieces.stronghold_syl5Term_default;
	public static String[] stronghold_syl5Trans = NamePieces.stronghold_syl5Trans_default;
	
	public static String[] stronghold_syl6Term = NamePieces.stronghold_syl6Term_default;
	public static String[] stronghold_syl6Trans = NamePieces.stronghold_syl6Trans_default;
	
	public static String[] stronghold_syl7Term = NamePieces.stronghold_syl7Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	protected static void loadConfiguration() {
		
		//Java is stupid and I need to construct the default for the syllable lists INSIDE A METHOD, hurr durr
		// Here are all the string elements
		stronghold_prefix = config.getStringList("Name Prefix", "Stronghold Syllable Pool", stronghold_prefix, "Prefixes that can occur before name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		stronghold_suffix = config.getStringList("Name Suffix", "Stronghold Syllable Pool", stronghold_suffix, "Suffixes that can occur after name");
		
		stronghold_oneSylBegin = config.getStringList("One-Syllable Begin", "Stronghold Syllable Pool", stronghold_oneSylBegin, "First half of one-syllable name");
		stronghold_oneSylEnd = config.getStringList("One-Syllable End", "Stronghold Syllable Pool", stronghold_oneSylEnd, "Second half of one-syllable name");
		stronghold_syl1Trans = config.getStringList("Syllable 1 Transitional", "Stronghold Syllable Pool", stronghold_syl1Trans, "Transitional first syllable");
		
		stronghold_syl2Term = config.getStringList("Syllable 2 Terminal", "Stronghold Syllable Pool", stronghold_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		stronghold_syl2Trans = config.getStringList("Syllable 2 Transitional", "Stronghold Syllable Pool", stronghold_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		
		stronghold_syl3Term = config.getStringList("Syllable 3 Terminal", "Stronghold Syllable Pool", stronghold_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		stronghold_syl3Trans = config.getStringList("Syllable 3 Transitional", "Stronghold Syllable Pool", stronghold_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		
		stronghold_syl4Term = config.getStringList("Syllable 4 Terminal", "Stronghold Syllable Pool", stronghold_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		stronghold_syl4Trans = config.getStringList("Syllable 4 Transitional", "Stronghold Syllable Pool", stronghold_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		
		stronghold_syl5Term = config.getStringList("Syllable 5 Terminal", "Stronghold Syllable Pool", stronghold_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		stronghold_syl5Trans = config.getStringList("Syllable 5 Transitional", "Stronghold Syllable Pool", stronghold_syl5Trans, "Transitional fifth syllable (use ^ for leading/ending spaces)");
		
		stronghold_syl6Term = config.getStringList("Syllable 6 Terminal", "Stronghold Syllable Pool", stronghold_syl6Term, "Terminating sixth syllable (use ^ for leading/ending spaces)");
		stronghold_syl6Trans = config.getStringList("Syllable 6 Transitional", "Stronghold Syllable Pool", stronghold_syl6Trans, "Transitional sixth syllable (use ^ for leading/ending spaces)");
		
		stronghold_syl7Term = config.getStringList("Syllable 7 Terminal", "Stronghold Syllable Pool", stronghold_syl7Term, "Terminating seventh syllable (use ^ for leading/ending spaces)");
		
		if (config.hasChanged()) config.save();
	}
	
}
