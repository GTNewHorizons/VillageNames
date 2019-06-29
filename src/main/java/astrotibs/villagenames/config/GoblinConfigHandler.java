package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePiecesEntities;
import net.minecraftforge.common.config.Configuration;

public class GoblinConfigHandler {
	
	public static Configuration config;

	public static String[] goblin_prefix = NamePiecesEntities.goblin_prefix_default;
	public static String[] goblin_suffix = NamePiecesEntities.goblin_suffix_default;
	
	public static String[] goblin_oneSylBegin = NamePiecesEntities.goblin_oneSylBegin_default;
	public static String[] goblin_oneSylEnd = NamePiecesEntities.goblin_oneSylEnd_default;
	public static String[] goblin_syl1Trans = NamePiecesEntities.goblin_syl1Trans_default;
	public static String[] goblin_syl2Term = NamePiecesEntities.goblin_syl2Term_default;
	public static String[] goblin_syl2Trans = NamePiecesEntities.goblin_syl2Trans_default;
	public static String[] goblin_syl3Term = NamePiecesEntities.goblin_syl3Term_default;
	public static String[] goblin_syl3Trans = NamePiecesEntities.goblin_syl3Trans_default;
	public static String[] goblin_syl4Term = NamePiecesEntities.goblin_syl4Term_default;
	public static String[] goblin_syl4Trans = NamePiecesEntities.goblin_syl4Trans_default;
	public static String[] goblin_syl5Term = NamePiecesEntities.goblin_syl5Term_default;
	public static String[] goblin_syl5Trans = NamePiecesEntities.goblin_syl5Trans_default;
	public static String[] goblin_syl6Term = NamePiecesEntities.goblin_syl6Term_default;
	public static String[] goblin_syl6Trans = NamePiecesEntities.goblin_syl6Trans_default;
	public static String[] goblin_syl7Term = NamePiecesEntities.goblin_syl7Term_default;
	

	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	public static void loadConfiguration() {

		/*
	     * Goblin
	     */
		goblin_prefix = config.getStringList("Name Prefix", "Goblin Syllable Pool", goblin_prefix, "Prefixes that can occur before name");
		goblin_suffix = config.getStringList("Name Suffix", "Goblin Syllable Pool", goblin_suffix, "Suffixes that can occur after name");
		
		goblin_oneSylBegin = config.getStringList("One-Syllable Begin", "Goblin Syllable Pool", goblin_oneSylBegin, "Goblin: First half of one-syllable name");
		goblin_oneSylEnd = config.getStringList("One-Syllable End", "Goblin Syllable Pool", goblin_oneSylEnd, "Goblin: Second half of one-syllable name");
		goblin_syl1Trans = config.getStringList("Syllable 1 Transitional", "Goblin Syllable Pool", goblin_syl1Trans, "Goblin: Transitional first syllable");
		
		goblin_syl2Term = config.getStringList("Syllable 2 Terminal", "Goblin Syllable Pool", goblin_syl2Term, "Goblin: Terminating second syllable (use ^ for leading/ending spaces)");
		goblin_syl2Trans = config.getStringList("Syllable 2 Transitional", "Goblin Syllable Pool", goblin_syl2Trans, "Goblin: Transitional second syllable (use ^ for leading/ending spaces)");
		
		goblin_syl3Term = config.getStringList("Syllable 3 Terminal", "Goblin Syllable Pool", goblin_syl3Term, "Goblin: Terminating third syllable (use ^ for leading/ending spaces)");
		goblin_syl3Trans = config.getStringList("Syllable 3 Transitional", "Goblin Syllable Pool", goblin_syl3Trans, "Goblin: Transitional third syllable (use ^ for leading/ending spaces)");
		
		goblin_syl4Term = config.getStringList("Syllable 4 Terminal", "Goblin Syllable Pool", goblin_syl4Term, "Goblin: Terminating fourth syllable (use ^ for leading/ending spaces)");
		goblin_syl4Trans = config.getStringList("Syllable 4 Transitional", "Goblin Syllable Pool", goblin_syl4Trans, "Goblin: Transitional fourth syllable (use ^ for leading/ending spaces)");
		
		goblin_syl5Term = config.getStringList("Syllable 5 Terminal", "Goblin Syllable Pool", goblin_syl5Term, "Goblin: Terminating fifth syllable (use ^ for leading/ending spaces)");
		goblin_syl5Trans = config.getStringList("Syllable 5 Transitional", "Goblin Syllable Pool", goblin_syl5Trans, "Goblin: Transitional fifth syllable (use ^ for leading/ending spaces)");
		
		goblin_syl6Term = config.getStringList("Syllable 6 Terminal", "Goblin Syllable Pool", goblin_syl6Term, "Goblin: Terminating sixth syllable (use ^ for leading/ending spaces)");
		goblin_syl6Trans = config.getStringList("Syllable 6 Transitional", "Goblin Syllable Pool", goblin_syl6Trans, "Goblin: Transitional sixth syllable (use ^ for leading/ending spaces)");
		
		goblin_syl7Term = config.getStringList("Syllable 7 Terminal", "Goblin Syllable Pool", goblin_syl7Term, "Goblin: Terminating seventh syllable (use ^ for leading/ending spaces)");

	    if (config.hasChanged()) config.save();
	    
	}
}
