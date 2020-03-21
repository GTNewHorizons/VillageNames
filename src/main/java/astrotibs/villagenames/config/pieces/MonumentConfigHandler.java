package astrotibs.villagenames.config.pieces;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import net.minecraftforge.common.config.Configuration;

public class MonumentConfigHandler
{
	public static Configuration config;
	
	
	public static String[] monument_prefix;
	public static String[] monument_root_initial;
	public static String[] monument_root_syllables;
	public static String[] monument_root_terminal;
	public static String[] monument_suffix;

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
		monument_prefix = config.getString("Prefixes", "Monument Syllable Pool", NamePieces.monument_prefix_default,
				"Prefixes that can occur before the core name.").trim().split("\\s*,\\s*");
		
		monument_root_initial = config.getString("Root: Initial", "Monument Syllable Pool", NamePieces.monument_root_initial_default,
				"Core names begin with one of these half-syllables. Use _ to represent a space.").trim().split("\\s*,\\s*");
		
		monument_root_syllables = config.getString("Root: Syllables", "Monument Syllable Pool", NamePieces.monument_root_sylBegin_default,
				"Core names insert zero or more of these elements to build to their target lengths. Use _ to represent a space, and ^ for a blank entry.").trim().split("\\s*,\\s*");
		
		monument_root_terminal = config.getString("Root: Terminal", "Monument Syllable Pool", NamePieces.monument_root_terminal_default,
				"Core names end with one of these half-syllables. Use _ to represent a space, and ^ for a blank entry.").trim().split("\\s*,\\s*");
		
		monument_suffix = config.getString("Suffixes", "Monument Syllable Pool", NamePieces.monument_suffix_default,
				"Suffixes that can occur after the core name.").trim().split("\\s*,\\s*");

		
		
		syllable_count_weighting = config.get("Monument Syllable Pool", "Syllable Count Weighting", NamePieces.monument_syllable_count_weights,
				"How often core names of various lengths are generated. The number in the Nth row is the weighting for N-syllable names.").getIntList();
		
		prefix_chance = config.getFloat("Prefix Chance", "Monument Syllable Pool",
				(monument_root_initial.length-1) <= 0 ? 0 : ((float)monument_prefix.length-1)/(monument_root_initial.length-1), 0.0F, 1.0F,
				"The fraction of names that include a prefix.");
		
		suffix_chance = config.getFloat("Suffix Chance", "Monument Syllable Pool",
				(monument_root_initial.length-1) <= 0 ? 0 : ((float)monument_suffix.length-1)/(monument_root_initial.length-1), 0.0F, 1.0F,
				"The fraction of names that include a suffix.");
		
		
		
		if (config.hasChanged()) config.save();
	}
	
}
