package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePiecesEntities;
import net.minecraftforge.common.config.Configuration;

public class AngelConfigHandler {
	public static Configuration config;
	
	// Villager names
	public static String[] angel_prefix = NamePiecesEntities.angel_prefix_default;
	public static String[] angel_suffix = NamePiecesEntities.angel_suffix_default;
	
	public static String[] angel_oneSylBegin = NamePiecesEntities.angel_oneSylBegin_default;
	public static String[] angel_oneSylEnd = NamePiecesEntities.angel_oneSylEnd_default;
	public static String[] angel_syl1Trans = NamePiecesEntities.angel_syl1Trans_default;
	
	public static String[] angel_syl2Term = NamePiecesEntities.angel_syl2Term_default;
	public static String[] angel_syl2Trans = NamePiecesEntities.angel_syl2Trans_default;
	
	public static String[] angel_syl3Term = NamePiecesEntities.angel_syl3Term_default;
	public static String[] angel_syl3Trans = NamePiecesEntities.angel_syl3Trans_default;
	
	public static String[] angel_syl4Term = NamePiecesEntities.angel_syl4Term_default;
	public static String[] angel_syl4Trans = NamePiecesEntities.angel_syl4Trans_default;
	
	public static String[] angel_syl5Term = NamePiecesEntities.angel_syl5Term_default;
	public static String[] angel_syl5Trans = NamePiecesEntities.angel_syl5Trans_default;
	
	public static String[] angel_syl6Term = NamePiecesEntities.angel_syl6Term_default;
	public static String[] angel_syl6Trans = NamePiecesEntities.angel_syl6Trans_default;
	
	public static String[] angel_syl7Term = NamePiecesEntities.angel_syl7Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	protected static void loadConfiguration() {
		
		// Here are the elements for the villager names
		angel_prefix = config.getStringList("Name Prefix", "Angel Syllable Pool", angel_prefix, "Prefixes that can occur before name");
		angel_suffix = config.getStringList("Name Suffix", "Angel Syllable Pool", angel_suffix, "Suffixes that can occur after name");
		
		angel_oneSylBegin = config.getStringList("One-Syllable Begin", "Angel Syllable Pool", angel_oneSylBegin, "First half of one-syllable name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		angel_oneSylEnd = config.getStringList("One-Syllable End", "Angel Syllable Pool", angel_oneSylEnd, "Second half of one-syllable name");
		angel_syl1Trans = config.getStringList("Syllable 1 Transitional", "Angel Syllable Pool", angel_syl1Trans, "Transitional first syllable");
		
		angel_syl2Term = config.getStringList("Syllable 2 Terminal", "Angel Syllable Pool", angel_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		angel_syl2Trans = config.getStringList("Syllable 2 Transitional", "Angel Syllable Pool", angel_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		
		angel_syl3Term = config.getStringList("Syllable 3 Terminal", "Angel Syllable Pool", angel_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		angel_syl3Trans = config.getStringList("Syllable 3 Transitional", "Angel Syllable Pool", angel_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		
		angel_syl4Term = config.getStringList("Syllable 4 Terminal", "Angel Syllable Pool", angel_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		angel_syl4Trans = config.getStringList("Syllable 4 Transitional", "Angel Syllable Pool", angel_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		
		angel_syl5Term = config.getStringList("Syllable 5 Terminal", "Angel Syllable Pool", angel_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		angel_syl5Trans = config.getStringList("Syllable 5 Transitional", "Angel Syllable Pool", angel_syl5Trans, "Transitional fifth syllable (use ^ for leading/ending spaces)");
		
		angel_syl6Term = config.getStringList("Syllable 6 Terminal", "Angel Syllable Pool", angel_syl6Term, "Terminating sixth syllable (use ^ for leading/ending spaces)");
		angel_syl6Trans = config.getStringList("Syllable 6 Transitional", "Angel Syllable Pool", angel_syl6Trans, "Transitional sixth syllable (use ^ for leading/ending spaces)");
		
		angel_syl7Term = config.getStringList("Syllable 7 Terminal", "Angel Syllable Pool", angel_syl7Term, "Terminating seventh syllable (use ^ for leading/ending spaces)");
		
		if (config.hasChanged()) config.save();
	}
	
}
