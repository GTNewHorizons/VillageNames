package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePiecesEntities;
import net.minecraftforge.common.config.Configuration;

public class DemonConfigHandler {
	public static Configuration config;
	
	// Villager names
	public static String[] demon_prefix = NamePiecesEntities.demon_prefix_default;
	public static String[] demon_suffix = NamePiecesEntities.demon_suffix_default;
	
	public static String[] demon_oneSylBegin = NamePiecesEntities.demon_oneSylBegin_default;
	public static String[] demon_oneSylEnd = NamePiecesEntities.demon_oneSylEnd_default;
	public static String[] demon_syl1Trans = NamePiecesEntities.demon_syl1Trans_default;
	
	public static String[] demon_syl2Term = NamePiecesEntities.demon_syl2Term_default;
	public static String[] demon_syl2Trans = NamePiecesEntities.demon_syl2Trans_default;
	
	public static String[] demon_syl3Term = NamePiecesEntities.demon_syl3Term_default;
	public static String[] demon_syl3Trans = NamePiecesEntities.demon_syl3Trans_default;
	
	public static String[] demon_syl4Term = NamePiecesEntities.demon_syl4Term_default;
	public static String[] demon_syl4Trans = NamePiecesEntities.demon_syl4Trans_default;
	
	public static String[] demon_syl5Term = NamePiecesEntities.demon_syl5Term_default;
	public static String[] demon_syl5Trans = NamePiecesEntities.demon_syl5Trans_default;
	
	public static String[] demon_syl6Term = NamePiecesEntities.demon_syl6Term_default;
	public static String[] demon_syl6Trans = NamePiecesEntities.demon_syl6Trans_default;
	
	public static String[] demon_syl7Term = NamePiecesEntities.demon_syl7Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	protected static void loadConfiguration() {
		
		// Here are the elements for the villager names
		demon_prefix = config.getStringList("Name Prefix", "Demon Syllable Pool", demon_prefix, "Prefixes that can occur before name");
		demon_suffix = config.getStringList("Name Suffix", "Demon Syllable Pool", demon_suffix, "Suffixes that can occur after name");
		
		demon_oneSylBegin = config.getStringList("One-Syllable Begin", "Demon Syllable Pool", demon_oneSylBegin, "First half of one-syllable name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		demon_oneSylEnd = config.getStringList("One-Syllable End", "Demon Syllable Pool", demon_oneSylEnd, "Second half of one-syllable name");
		demon_syl1Trans = config.getStringList("Syllable 1 Transitional", "Demon Syllable Pool", demon_syl1Trans, "Transitional first syllable");
		
		demon_syl2Term = config.getStringList("Syllable 2 Terminal", "Demon Syllable Pool", demon_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		demon_syl2Trans = config.getStringList("Syllable 2 Transitional", "Demon Syllable Pool", demon_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		
		demon_syl3Term = config.getStringList("Syllable 3 Terminal", "Demon Syllable Pool", demon_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		demon_syl3Trans = config.getStringList("Syllable 3 Transitional", "Demon Syllable Pool", demon_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		
		demon_syl4Term = config.getStringList("Syllable 4 Terminal", "Demon Syllable Pool", demon_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		demon_syl4Trans = config.getStringList("Syllable 4 Transitional", "Demon Syllable Pool", demon_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		
		demon_syl5Term = config.getStringList("Syllable 5 Terminal", "Demon Syllable Pool", demon_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		demon_syl5Trans = config.getStringList("Syllable 5 Transitional", "Demon Syllable Pool", demon_syl5Trans, "Transitional fifth syllable (use ^ for leading/ending spaces)");
		
		demon_syl6Term = config.getStringList("Syllable 6 Terminal", "Demon Syllable Pool", demon_syl6Term, "Terminating sixth syllable (use ^ for leading/ending spaces)");
		demon_syl6Trans = config.getStringList("Syllable 6 Transitional", "Demon Syllable Pool", demon_syl6Trans, "Transitional sixth syllable (use ^ for leading/ending spaces)");
		
		demon_syl7Term = config.getStringList("Syllable 7 Terminal", "Demon Syllable Pool", demon_syl7Term, "Terminating seventh syllable (use ^ for leading/ending spaces)");
		
		if (config.hasChanged()) config.save();
	}
	
}
