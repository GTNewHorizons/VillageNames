package astrotibs.villagenames.config.pieces;

import java.io.File;

import astrotibs.villagenames.config.ConfigurationVN;
import astrotibs.villagenames.name.NamePieces;
import astrotibs.villagenames.utility.Reference;

public class MonumentConfigHandler
{
	public static ConfigurationVN config;
	
	
	public static String[] monument_prefix;
	public static String[] monument_root_initial;
	public static String[] monument_root_syllables;
	public static String[] monument_root_terminal;
	public static String[] monument_suffix;

	public static float prefix_chance;
	public static float suffix_chance;
	public static int[] syllable_count_weighting;
	public static int[] terminal_blank_counts;
	
	
	public static void init(File configFile)
	{
		if (config == null)
		{
			config = new ConfigurationVN(configFile);
			loadConfiguration();
		}
	}
	
	public static void loadConfiguration()
	{
		monument_prefix = config.getStringWithoutDefaultsInComment(Reference.NAME_PREFIXES, Reference.CATEGORY_MONUMENT_SYLLABLE_POOL, NamePieces.MONUMENT_PREFIX_DEFAULT,
				Reference.DESCRIPTION_PREFIXES).trim().split("\\s*,\\s*");
		
		monument_root_initial = config.getStringWithoutDefaultsInComment(Reference.NAME_ROOT_INITIAL, Reference.CATEGORY_MONUMENT_SYLLABLE_POOL, NamePieces.MONUMENT_ROOT_INITIAL_DEFAULT,
				Reference.DESCRIPTION_ROOT_INITIAL).trim().split("\\s*,\\s*");
		
		monument_root_syllables = config.getStringWithoutDefaultsInComment(Reference.NAME_ROOT_SYLLABLES, Reference.CATEGORY_MONUMENT_SYLLABLE_POOL, NamePieces.MONUMENT_ROOT_SYL_BEGIN_DEFAULT,
				Reference.DESCRIPTION_ROOT_SYLLABLES).trim().split("\\s*,\\s*");
		
		monument_root_terminal = config.getStringWithoutDefaultsInComment(Reference.NAME_ROOT_TERMINAL, Reference.CATEGORY_MONUMENT_SYLLABLE_POOL, NamePieces.MONUMENT_ROOT_TERMINAL_DEFAULT,
				Reference.DESCRIPTION_ROOT_TERMINAL).trim().split("\\s*,\\s*");
		
		monument_suffix = config.getStringWithoutDefaultsInComment("Suffixes", Reference.CATEGORY_MONUMENT_SYLLABLE_POOL, NamePieces.MONUMENT_SUFFIX_DEFAULT,
				Reference.DESCRIPTION_SUFFIXES).trim().split("\\s*,\\s*");

		
		
		syllable_count_weighting = config.get(Reference.CATEGORY_MONUMENT_SYLLABLE_POOL, Reference.NAME_SYLLABLE_COUNT_WEIGHTING, NamePieces.MONUMENT_SYLLABLE_COUNT_WEIGHTS,
				Reference.DESCRIPTION_SYLLABLE_COUNT_WEIGHTING).getIntList();

		terminal_blank_counts = config.get(Reference.CATEGORY_MONUMENT_SYLLABLE_POOL, Reference.NAME_TERMINAL_BLANK_COUNTS, NamePieces.MONUMENT_BLANK_TERMINAL_COUNTS,
				Reference.DESCRIPTION_TERMINAL_BLANK_COUNTS).getIntList();
		
		prefix_chance = config.getFloat(Reference.NAME_PREFIX_CHANCE, Reference.CATEGORY_MONUMENT_SYLLABLE_POOL,
				(monument_root_initial.length-1) <= 0 ? 0 : ((float)monument_prefix.length-1)/(monument_root_initial.length-1), 0.0F, 1.0F,
				Reference.DESCRIPTION_PREFIX_CHANCE);
		
		suffix_chance = config.getFloat(Reference.NAME_SUFFIX_CHANCE, Reference.CATEGORY_MONUMENT_SYLLABLE_POOL,
				(monument_root_initial.length-1) <= 0 ? 0 : ((float)monument_suffix.length-1)/(monument_root_initial.length-1), 0.0F, 1.0F,
				Reference.DESCRIPTION_SUFFIX_CHANCE);
		
		
		
		if (config.hasChanged()) config.save();
	}
	
}
