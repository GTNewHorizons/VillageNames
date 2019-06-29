package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePiecesEntities;
import net.minecraftforge.common.config.Configuration;

public class AlienConfigHandler {
	
	public static Configuration config;
	
	public static String[] alien_prefix = NamePiecesEntities.alien_prefix_default;
	public static String[] alien_suffix = NamePiecesEntities.alien_suffix_default;
	
	public static String[] alien_oneSylBegin = NamePiecesEntities.alien_oneSylBegin_default;
	public static String[] alien_oneSylEnd = NamePiecesEntities.alien_oneSylEnd_default;
	public static String[] alien_syl1Trans = NamePiecesEntities.alien_syl1Trans_default;
	public static String[] alien_syl2Term = NamePiecesEntities.alien_syl2Term_default;
	public static String[] alien_syl2Trans = NamePiecesEntities.alien_syl2Trans_default;
	public static String[] alien_syl3Term = NamePiecesEntities.alien_syl3Term_default;
	public static String[] alien_syl3Trans = NamePiecesEntities.alien_syl3Trans_default;
	public static String[] alien_syl4Term = NamePiecesEntities.alien_syl4Term_default;
	public static String[] alien_syl4Trans = NamePiecesEntities.alien_syl4Trans_default;
	public static String[] alien_syl5Term = NamePiecesEntities.alien_syl5Term_default;
	public static String[] alien_syl5Trans = NamePiecesEntities.alien_syl5Trans_default;
	public static String[] alien_syl6Term = NamePiecesEntities.alien_syl6Term_default;
	public static String[] alien_syl6Trans = NamePiecesEntities.alien_syl6Trans_default;
	public static String[] alien_syl7Term = NamePiecesEntities.alien_syl7Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	public static void loadConfiguration() {

	    /*
	     * Alien
	     */
		alien_prefix = config.getStringList("Name Prefix", "Alien Syllable Pool", alien_prefix, "Prefixes that can occur before name");
		alien_suffix = config.getStringList("Name Suffix", "Alien Syllable Pool", alien_suffix, "Suffixes that can occur after name");
		
		alien_oneSylBegin = config.getStringList("One-Syllable Begin", "Alien Syllable Pool", alien_oneSylBegin, "Alien: First half of one-syllable name");
		alien_oneSylEnd = config.getStringList("One-Syllable End", "Alien Syllable Pool", alien_oneSylEnd, "Alien: Second half of one-syllable name");
		alien_syl1Trans = config.getStringList("Syllable 1 Transitional", "Alien Syllable Pool", alien_syl1Trans, "Alien: Transitional first syllable");
		
		alien_syl2Term = config.getStringList("Syllable 2 Terminal", "Alien Syllable Pool", alien_syl2Term, "Alien: Terminating second syllable (use ^ for leading/ending spaces)");
		alien_syl2Trans = config.getStringList("Syllable 2 Transitional", "Alien Syllable Pool", alien_syl2Trans, "Alien: Transitional second syllable (use ^ for leading/ending spaces)");
		
		alien_syl3Term = config.getStringList("Syllable 3 Terminal", "Alien Syllable Pool", alien_syl3Term, "Alien: Terminating third syllable (use ^ for leading/ending spaces)");
		alien_syl3Trans = config.getStringList("Syllable 3 Transitional", "Alien Syllable Pool", alien_syl3Trans, "Alien: Transitional third syllable (use ^ for leading/ending spaces)");
		
		alien_syl4Term = config.getStringList("Syllable 4 Terminal", "Alien Syllable Pool", alien_syl4Term, "Alien: Terminating fourth syllable (use ^ for leading/ending spaces)");
		alien_syl4Trans = config.getStringList("Syllable 4 Transitional", "Alien Syllable Pool", alien_syl4Trans, "Alien: Transitional fourth syllable (use ^ for leading/ending spaces)");
		
		alien_syl5Term = config.getStringList("Syllable 5 Terminal", "Alien Syllable Pool", alien_syl5Term, "Alien: Terminating fifth syllable (use ^ for leading/ending spaces)");
		alien_syl5Trans = config.getStringList("Syllable 5 Transitional", "Alien Syllable Pool", alien_syl5Trans, "Alien: Transitional fifth syllable (use ^ for leading/ending spaces)");
		
		alien_syl6Term = config.getStringList("Syllable 6 Terminal", "Alien Syllable Pool", alien_syl6Term, "Alien: Terminating sixth syllable (use ^ for leading/ending spaces)");
		alien_syl6Trans = config.getStringList("Syllable 6 Transitional", "Alien Syllable Pool", alien_syl6Trans, "Alien: Transitional sixth syllable (use ^ for leading/ending spaces)");
		
		alien_syl7Term = config.getStringList("Syllable 7 Terminal", "Alien Syllable Pool", alien_syl7Term, "Alien: Terminating seventh syllable (use ^ for leading/ending spaces)");
		

	    if (config.hasChanged()) config.save();
	    
	}
}
