package astrotibs.villagenames.config.pieces;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import astrotibs.villagenames.utility.Reference;
import net.minecraftforge.common.config.Configuration;

public class EndCityConfigHandler
{
	public static Configuration config;
	
	
	public static String[] endcity_prefix;
	public static String[] endcity_root_initial;
	public static String[] endcity_root_syllables;
	public static String[] endcity_root_terminal;
	public static String[] endcity_suffix;

	public static float prefix_chance;
	public static float suffix_chance;
	public static int[] syllable_count_weighting;
	public static int[] terminal_blank_counts;
	
	
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
		endcity_prefix = config.getString(Reference.NAME_PREFIXES, Reference.CATEGORY_END_CITY_SYLLABLE_POOL, NamePieces.END_CITY_PREFIX_DEFAULT,
				Reference.DESCRIPTION_PREFIXES).trim().split("\\s*,\\s*");
		
		endcity_root_initial = config.getString(Reference.NAME_ROOT_INITIAL, Reference.CATEGORY_END_CITY_SYLLABLE_POOL, NamePieces.END_CITY_ROOT_INITIAL_DEFAULT,
				Reference.DESCRIPTION_ROOT_INITIAL).trim().split("\\s*,\\s*");
		
		endcity_root_syllables = config.getString(Reference.NAME_ROOT_SYLLABLES, Reference.CATEGORY_END_CITY_SYLLABLE_POOL, NamePieces.END_CITY_ROOT_SYL_BEGIN_DEFAULT,
				Reference.DESCRIPTION_ROOT_SYLLABLES).trim().split("\\s*,\\s*");
		
		endcity_root_terminal = config.getString(Reference.NAME_ROOT_TERMINAL, Reference.CATEGORY_END_CITY_SYLLABLE_POOL, NamePieces.END_CITY_ROOT_TERMINAL_DEFAULT,
				Reference.DESCRIPTION_ROOT_TERMINAL).trim().split("\\s*,\\s*");
		
		endcity_suffix = config.getString("Suffixes", Reference.CATEGORY_END_CITY_SYLLABLE_POOL, NamePieces.END_CITY_SUFFIX_DEFAULT,
				Reference.DESCRIPTION_SUFFIXES).trim().split("\\s*,\\s*");

		
		
		syllable_count_weighting = config.get(Reference.CATEGORY_END_CITY_SYLLABLE_POOL, Reference.NAME_SYLLABLE_COUNT_WEIGHTING, NamePieces.END_CITY_SYLLABLE_COUNT_WEIGHTS,
				Reference.DESCRIPTION_SYLLABLE_COUNT_WEIGHTING).getIntList();

		terminal_blank_counts = config.get(Reference.CATEGORY_END_CITY_SYLLABLE_POOL, Reference.NAME_TERMINAL_BLANK_COUNTS, NamePieces.END_CITY_BLANK_TERMINAL_COUNTS,
				Reference.DESCRIPTION_TERMINAL_BLANK_COUNTS).getIntList();
		
		prefix_chance = config.getFloat(Reference.NAME_PREFIX_CHANCE, Reference.CATEGORY_END_CITY_SYLLABLE_POOL,
				(endcity_root_initial.length-1) <= 0 ? 0 : ((float)endcity_prefix.length-1)/(endcity_root_initial.length-1), 0.0F, 1.0F,
				Reference.DESCRIPTION_PREFIX_CHANCE);
		
		suffix_chance = config.getFloat(Reference.NAME_SUFFIX_CHANCE, Reference.CATEGORY_END_CITY_SYLLABLE_POOL,
				(endcity_root_initial.length-1) <= 0 ? 0 : ((float)endcity_suffix.length-1)/(endcity_root_initial.length-1), 0.0F, 1.0F,
				Reference.DESCRIPTION_SUFFIX_CHANCE);
		
		
		
		if (config.hasChanged()) config.save();
	}
	
}
