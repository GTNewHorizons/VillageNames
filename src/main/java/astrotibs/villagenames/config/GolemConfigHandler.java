package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePiecesEntities;
import net.minecraftforge.common.config.Configuration;

public class GolemConfigHandler {
	public static Configuration config;
	
	// Villager names
	public static String[] golem_prefix = NamePiecesEntities.golem_prefix_default;
	public static String[] golem_suffix = NamePiecesEntities.golem_suffix_default;
	
	public static String[] golem_oneSylBegin = NamePiecesEntities.golem_oneSylBegin_default;
	public static String[] golem_oneSylEnd = NamePiecesEntities.golem_oneSylEnd_default;
	public static String[] golem_syl1Trans = NamePiecesEntities.golem_syl1Trans_default;
	
	public static String[] golem_syl2Term = NamePiecesEntities.golem_syl2Term_default;
	public static String[] golem_syl2Trans = NamePiecesEntities.golem_syl2Trans_default;
	
	public static String[] golem_syl3Term = NamePiecesEntities.golem_syl3Term_default;
	public static String[] golem_syl3Trans = NamePiecesEntities.golem_syl3Trans_default;
	
	public static String[] golem_syl4Term = NamePiecesEntities.golem_syl4Term_default;
	public static String[] golem_syl4Trans = NamePiecesEntities.golem_syl4Trans_default;
	
	public static String[] golem_syl5Term = NamePiecesEntities.golem_syl5Term_default;
	public static String[] golem_syl5Trans = NamePiecesEntities.golem_syl5Trans_default;
	
	public static String[] golem_syl6Term = NamePiecesEntities.golem_syl6Term_default;
	public static String[] golem_syl6Trans = NamePiecesEntities.golem_syl6Trans_default;
	
	public static String[] golem_syl7Term = NamePiecesEntities.golem_syl7Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	protected static void loadConfiguration() {
		
		// Here are the elements for the villager names
		golem_prefix = config.getStringList("Name Prefix", "Golem Syllable Pool", golem_prefix, "Prefixes that can occur before name");
		golem_suffix = config.getStringList("Name Suffix", "Golem Syllable Pool", golem_suffix, "Suffixes that can occur after name");
		
		golem_oneSylBegin = config.getStringList("One-Syllable Begin", "Golem Syllable Pool", golem_oneSylBegin, "First half of one-syllable name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		golem_oneSylEnd = config.getStringList("One-Syllable End", "Golem Syllable Pool", golem_oneSylEnd, "Second half of one-syllable name");
		golem_syl1Trans = config.getStringList("Syllable 1 Transitional", "Golem Syllable Pool", golem_syl1Trans, "Transitional first syllable");
		
		golem_syl2Term = config.getStringList("Syllable 2 Terminal", "Golem Syllable Pool", golem_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		golem_syl2Trans = config.getStringList("Syllable 2 Transitional", "Golem Syllable Pool", golem_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		
		golem_syl3Term = config.getStringList("Syllable 3 Terminal", "Golem Syllable Pool", golem_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		golem_syl3Trans = config.getStringList("Syllable 3 Transitional", "Golem Syllable Pool", golem_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		
		golem_syl4Term = config.getStringList("Syllable 4 Terminal", "Golem Syllable Pool", golem_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		golem_syl4Trans = config.getStringList("Syllable 4 Transitional", "Golem Syllable Pool", golem_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		
		golem_syl5Term = config.getStringList("Syllable 5 Terminal", "Golem Syllable Pool", golem_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		golem_syl5Trans = config.getStringList("Syllable 5 Transitional", "Golem Syllable Pool", golem_syl5Trans, "Transitional fifth syllable (use ^ for leading/ending spaces)");
		
		golem_syl6Term = config.getStringList("Syllable 6 Terminal", "Golem Syllable Pool", golem_syl6Term, "Terminating sixth syllable (use ^ for leading/ending spaces)");
		golem_syl6Trans = config.getStringList("Syllable 6 Transitional", "Golem Syllable Pool", golem_syl6Trans, "Transitional sixth syllable (use ^ for leading/ending spaces)");
		
		golem_syl7Term = config.getStringList("Syllable 7 Terminal", "Golem Syllable Pool", golem_syl7Term, "Terminating seventh syllable (use ^ for leading/ending spaces)");
		
		if (config.hasChanged()) config.save();
	}
	
}
