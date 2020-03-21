package astrotibs.villagenames.config.pieces;

import java.io.File;

import astrotibs.villagenames.name.NamePiecesEntities;
import net.minecraftforge.common.config.Configuration;

public class DemonConfigHandler
{
	public static Configuration config;
	
	
	public static String[] demon_prefix;
	public static String[] demon_root_initial;
	public static String[] demon_root_syllables;
	public static String[] demon_root_terminal;
	public static String[] demon_suffix;

	public static float prefix_chance;
	public static float suffix_chance;
	public static int[] syllable_count_weighting; 
	
	
	public static void init(File configFile)
	{
		if (config == null)
		{
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	public static void loadConfiguration()
	{
		demon_prefix = config.getString("Prefixes", "Demon Syllable Pool", NamePiecesEntities.demon_prefix_default,
				"Prefixes that can occur before the core name.").trim().split("\\s*,\\s*");
		
		demon_root_initial = config.getString("Root: Initial", "Demon Syllable Pool", NamePiecesEntities.demon_root_initial_default,
				"Core names begin with one of these half-syllables. Use _ to represent a space.").trim().split("\\s*,\\s*");
		
		demon_root_syllables = config.getString("Root: Syllables", "Demon Syllable Pool", NamePiecesEntities.demon_root_sylBegin_default,
				"Core names insert zero or more of these elements to build to their target lengths. Use _ to represent a space, and ^ for a blank entry.").trim().split("\\s*,\\s*");
		
		demon_root_terminal = config.getString("Root: Terminal", "Demon Syllable Pool", NamePiecesEntities.demon_root_terminal_default,
				"Core names end with one of these half-syllables. Use _ to represent a space, and ^ for a blank entry.").trim().split("\\s*,\\s*");
		
		demon_suffix = config.getString("Suffixes", "Demon Syllable Pool", NamePiecesEntities.demon_suffix_default,
				"Suffixes that can occur after the core name.").trim().split("\\s*,\\s*");

		
		
		syllable_count_weighting = config.get("Demon Syllable Pool", "Syllable Count Weighting", NamePiecesEntities.demon_syllable_count_weights,
				"How often core names of various lengths are generated. The number in the Nth row is the weighting for N-syllable names.").getIntList();
		
		prefix_chance = config.getFloat("Prefix Chance", "Demon Syllable Pool",
				(demon_root_initial.length-1) <= 0 ? 0 : ((float)demon_prefix.length-1)/(demon_root_initial.length-1), 0.0F, 1.0F,
				"The fraction of names that include a prefix.");
		
		suffix_chance = config.getFloat("Suffix Chance", "Demon Syllable Pool",
				(demon_root_initial.length-1) <= 0 ? 0 : ((float)demon_suffix.length-1)/(demon_root_initial.length-1), 0.0F, 1.0F,
				"The fraction of names that include a suffix.");
		
		
		
		if (config.hasChanged()) config.save();
	}
	
}
