package astrotibs.villagenames.config.pieces;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import astrotibs.villagenames.utility.Reference;
import net.minecraftforge.common.config.Configuration;

public class AlienVillageConfigHandler
{
	public static Configuration config;
	
	
	public static String[] alienvillage_prefix;
	public static String[] alienvillage_root_initial;
	public static String[] alienvillage_root_syllables;
	public static String[] alienvillage_root_terminal;
	public static String[] alienvillage_suffix;

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
		alienvillage_prefix = config.getString(Reference.NAME_PREFIXES, Reference.CATEGORY_ALIEN_VILLAGE_SYLLABLE_POOL, NamePieces.ALIEN_VILLAGE_PREFIX_DEFAULT,
				Reference.DESCRIPTION_PREFIXES).trim().split("\\s*,\\s*");
		
		alienvillage_root_initial = config.getString(Reference.NAME_ROOT_INITIAL, Reference.CATEGORY_ALIEN_VILLAGE_SYLLABLE_POOL, NamePieces.ALIEN_VILLAGE_ROOT_INITIAL_DEFAULT,
				Reference.DESCRIPTION_ROOT_INITIAL).trim().split("\\s*,\\s*");
		
		alienvillage_root_syllables = config.getString(Reference.NAME_ROOT_SYLLABLES, Reference.CATEGORY_ALIEN_VILLAGE_SYLLABLE_POOL, NamePieces.ALIEN_vILLAGE_ROOT_SYL_BEGIN_DEFAULT,
				Reference.DESCRIPTION_ROOT_SYLLABLES).trim().split("\\s*,\\s*");
		
		alienvillage_root_terminal = config.getString(Reference.NAME_ROOT_TERMINAL, Reference.CATEGORY_ALIEN_VILLAGE_SYLLABLE_POOL, NamePieces.ALIEN_VILLAGE_ROOT_TERMINAL_DEFAULT,
				Reference.DESCRIPTION_ROOT_TERMINAL).trim().split("\\s*,\\s*");
		
		alienvillage_suffix = config.getString("Suffixes", Reference.CATEGORY_ALIEN_VILLAGE_SYLLABLE_POOL, NamePieces.ALIEN_VILLAGE_SUFFIX_DEFAULT,
				Reference.DESCRIPTION_SUFFIXES).trim().split("\\s*,\\s*");
		
		
		
		syllable_count_weighting = config.get(Reference.CATEGORY_ALIEN_VILLAGE_SYLLABLE_POOL, Reference.NAME_SYLLABLE_COUNT_WEIGHTING, NamePieces.ALIEN_VILLAGE_SYLLABLE_COUNT_WEIGHTS,
				Reference.DESCRIPTION_SYLLABLE_COUNT_WEIGHTING).getIntList();

		terminal_blank_counts = config.get(Reference.CATEGORY_ALIEN_VILLAGE_SYLLABLE_POOL, Reference.NAME_TERMINAL_BLANK_COUNTS, NamePieces.ALIEN_VILLAGE_BLANK_TERMINAL_COUNTS,
				Reference.DESCRIPTION_TERMINAL_BLANK_COUNTS).getIntList();
		
		prefix_chance = config.getFloat(Reference.NAME_PREFIX_CHANCE, Reference.CATEGORY_ALIEN_VILLAGE_SYLLABLE_POOL,
				(alienvillage_root_initial.length-1) <= 0 ? 0 : ((float)alienvillage_prefix.length-1)/(alienvillage_root_initial.length-1), 0.0F, 1.0F,
				Reference.DESCRIPTION_PREFIX_CHANCE);
		
		suffix_chance = config.getFloat(Reference.NAME_SUFFIX_CHANCE, Reference.CATEGORY_ALIEN_VILLAGE_SYLLABLE_POOL,
				(alienvillage_root_initial.length-1) <= 0 ? 0 : ((float)alienvillage_suffix.length-1)/(alienvillage_root_initial.length-1), 0.0F, 1.0F,
				Reference.DESCRIPTION_SUFFIX_CHANCE);
		
		
		
		if (config.hasChanged()) config.save();
	}
	
}
