package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import net.minecraftforge.common.config.Configuration;

public class AlienVillageConfigHandler {
	
	public static Configuration config;
	
	public static String[] alienVillage_prefix = NamePieces.alienVillage_prefix_default;
	public static String[] alienVillage_suffix = NamePieces.alienVillage_suffix_default;
	
	public static String[] alienVillage_oneSylBegin = NamePieces.alienVillage_oneSylBegin_default;
	public static String[] alienVillage_oneSylEnd = NamePieces.alienVillage_oneSylEnd_default;
	public static String[] alienVillage_syl1Trans = NamePieces.alienVillage_syl1Trans_default;
	public static String[] alienVillage_syl2Term = NamePieces.alienVillage_syl2Term_default;
	public static String[] alienVillage_syl2Trans = NamePieces.alienVillage_syl2Trans_default;
	public static String[] alienVillage_syl3Term = NamePieces.alienVillage_syl3Term_default;
	public static String[] alienVillage_syl3Trans = NamePieces.alienVillage_syl3Trans_default;
	public static String[] alienVillage_syl4Term = NamePieces.alienVillage_syl4Term_default;
	public static String[] alienVillage_syl4Trans = NamePieces.alienVillage_syl4Trans_default;
	public static String[] alienVillage_syl5Term = NamePieces.alienVillage_syl5Term_default;
	public static String[] alienVillage_syl5Trans = NamePieces.alienVillage_syl5Trans_default;
	public static String[] alienVillage_syl6Term = NamePieces.alienVillage_syl6Term_default;
	public static String[] alienVillage_syl6Trans = NamePieces.alienVillage_syl6Trans_default;
	public static String[] alienVillage_syl7Term = NamePieces.alienVillage_syl7Term_default;
	
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	public static void loadConfiguration() {
		
		/*
	     * Alien Village
	     */
		alienVillage_prefix = config.getStringList("Name Prefix", "Alien Village Syllable Pool", alienVillage_prefix, "Prefixes that can occur before name");
		alienVillage_suffix = config.getStringList("Name Suffix", "Alien Village Syllable Pool", alienVillage_suffix, "Suffixes that can occur after name");
		
		alienVillage_oneSylBegin = config.getStringList("One-Syllable Begin", "Alien Village Syllable Pool", alienVillage_oneSylBegin, "Alien village: First half of one-syllable name");
		alienVillage_oneSylEnd = config.getStringList("One-Syllable End", "Alien Village Syllable Pool", alienVillage_oneSylEnd, "Alien village: Second half of one-syllable name");
		alienVillage_syl1Trans = config.getStringList("Syllable 1 Transitional", "Alien Village Syllable Pool", alienVillage_syl1Trans, "Alien village: Transitional first syllable");
		
		alienVillage_syl2Term = config.getStringList("Syllable 2 Terminal", "Alien Village Syllable Pool", alienVillage_syl2Term, "Alien village: Terminating second syllable (use ^ for leading/ending spaces)");
		alienVillage_syl2Trans = config.getStringList("Syllable 2 Transitional", "Alien Village Syllable Pool", alienVillage_syl2Trans, "Alien village: Transitional second syllable (use ^ for leading/ending spaces)");
		
		alienVillage_syl3Term = config.getStringList("Syllable 3 Terminal", "Alien Village Syllable Pool", alienVillage_syl3Term, "Alien village: Terminating third syllable (use ^ for leading/ending spaces)");
		alienVillage_syl3Trans = config.getStringList("Syllable 3 Transitional", "Alien Village Syllable Pool", alienVillage_syl3Trans, "Alien village: Transitional third syllable (use ^ for leading/ending spaces)");
		
		alienVillage_syl4Term = config.getStringList("Syllable 4 Terminal", "Alien Village Syllable Pool", alienVillage_syl4Term, "Alien village: Terminating fourth syllable (use ^ for leading/ending spaces)");
		alienVillage_syl4Trans = config.getStringList("Syllable 4 Transitional", "Alien Village Syllable Pool", alienVillage_syl4Trans, "Alien village: Transitional fourth syllable (use ^ for leading/ending spaces)");
		
		alienVillage_syl5Term = config.getStringList("Syllable 5 Terminal", "Alien Village Syllable Pool", alienVillage_syl5Term, "Alien village: Terminating fifth syllable (use ^ for leading/ending spaces)");
		alienVillage_syl5Trans = config.getStringList("Syllable 5 Transitional", "Alien Village Syllable Pool", alienVillage_syl5Trans, "Alien village: Transitional fifth syllable (use ^ for leading/ending spaces)");
		
		alienVillage_syl6Term = config.getStringList("Syllable 6 Terminal", "Alien Village Syllable Pool", alienVillage_syl6Term, "Alien village: Terminating sixth syllable (use ^ for leading/ending spaces)");
		alienVillage_syl6Trans = config.getStringList("Syllable 6 Transitional", "Alien Village Syllable Pool", alienVillage_syl6Trans, "Alien village: Transitional sixth syllable (use ^ for leading/ending spaces)");
		
		alienVillage_syl7Term = config.getStringList("Syllable 7 Terminal", "Alien Village Syllable Pool", alienVillage_syl7Term, "Alien village: Terminating seventh syllable (use ^ for leading/ending spaces)");

	    if (config.hasChanged()) config.save();
	    
	}
}
