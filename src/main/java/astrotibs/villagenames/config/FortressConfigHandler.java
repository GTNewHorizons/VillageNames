package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import net.minecraftforge.common.config.Configuration;

public class FortressConfigHandler {
	public static Configuration config;
	
	// Fortress name pieces
	public static String[] fortress_prefix = NamePieces.fortress_prefix_default;
	public static String[] fortress_suffix = NamePieces.fortress_suffix_default;
	
	public static String[] fortress_oneSylBegin = NamePieces.fortress_oneSylBegin_default;
	public static String[] fortress_oneSylEnd = NamePieces.fortress_oneSylEnd_default;
	public static String[] fortress_syl1Trans = NamePieces.fortress_syl1Trans_default;
	
	public static String[] fortress_syl2Term = NamePieces.fortress_syl2Term_default;
	public static String[] fortress_syl2Trans = NamePieces.fortress_syl2Trans_default;
	
	public static String[] fortress_syl3Term = NamePieces.fortress_syl3Term_default;
	public static String[] fortress_syl3Trans = NamePieces.fortress_syl3Trans_default;
	
	public static String[] fortress_syl4Term = NamePieces.fortress_syl4Term_default;
	public static String[] fortress_syl4Trans = NamePieces.fortress_syl4Trans_default;
	
	public static String[] fortress_syl5Term = NamePieces.fortress_syl5Term_default;
	public static String[] fortress_syl5Trans = NamePieces.fortress_syl5Trans_default;
	
	public static String[] fortress_syl6Term = NamePieces.fortress_syl6Term_default;
	public static String[] fortress_syl6Trans = NamePieces.fortress_syl6Trans_default;
	
	public static String[] fortress_syl7Term = NamePieces.fortress_syl7Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	protected static void loadConfiguration() {
		
		fortress_prefix = config.getStringList("Name Prefix", "Fortress Syllable Pool", fortress_prefix, "Prefixes that can occur before name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		fortress_suffix = config.getStringList("Name Suffix", "Fortress Syllable Pool", fortress_suffix, "Suffixes that can occur after name");
		
		fortress_oneSylBegin = config.getStringList("One-Syllable Begin", "Fortress Syllable Pool", fortress_oneSylBegin, "First half of one-syllable name");
		fortress_oneSylEnd = config.getStringList("One-Syllable End", "Fortress Syllable Pool", fortress_oneSylEnd, "Second half of one-syllable name");
		fortress_syl1Trans = config.getStringList("Syllable 1 Transitional", "Fortress Syllable Pool", fortress_syl1Trans, "Transitional first syllable");
		
		fortress_syl2Term = config.getStringList("Syllable 2 Terminal", "Fortress Syllable Pool", fortress_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		fortress_syl2Trans = config.getStringList("Syllable 2 Transitional", "Fortress Syllable Pool", fortress_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		
		fortress_syl3Term = config.getStringList("Syllable 3 Terminal", "Fortress Syllable Pool", fortress_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		fortress_syl3Trans = config.getStringList("Syllable 3 Transitional", "Fortress Syllable Pool", fortress_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		
		fortress_syl4Term = config.getStringList("Syllable 4 Terminal", "Fortress Syllable Pool", fortress_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		fortress_syl4Trans = config.getStringList("Syllable 4 Transitional", "Fortress Syllable Pool", fortress_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		
		fortress_syl5Term = config.getStringList("Syllable 5 Terminal", "Fortress Syllable Pool", fortress_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		fortress_syl5Trans = config.getStringList("Syllable 5 Transitional", "Fortress Syllable Pool", fortress_syl5Trans, "Transitional fifth syllable (use ^ for leading/ending spaces)");
		
		fortress_syl6Term = config.getStringList("Syllable 6 Terminal", "Fortress Syllable Pool", fortress_syl6Term, "Terminating sixth syllable (use ^ for leading/ending spaces)");
		fortress_syl6Trans = config.getStringList("Syllable 6 Transitional", "Fortress Syllable Pool", fortress_syl6Trans, "Transitional sixth syllable (use ^ for leading/ending spaces)");
		
		fortress_syl7Term = config.getStringList("Syllable 7 Terminal", "Fortress Syllable Pool", fortress_syl7Term, "Terminating seventh syllable (use ^ for leading/ending spaces)");
		
	    if (config.hasChanged()) config.save();
	}
	
}
