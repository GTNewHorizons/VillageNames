package astrotibs.villagenames.config.pieces;

import java.io.File;

import astrotibs.villagenames.config.ConfigurationVN;
import astrotibs.villagenames.name.NamePieces;
import astrotibs.villagenames.utility.Reference;

public class CustomConfigHandler {

    public static ConfigurationVN config;

    public static String[] custom_prefix;
    public static String[] custom_root_initial;
    public static String[] custom_root_syllables;
    public static String[] custom_root_terminal;
    public static String[] custom_suffix;

    public static float prefix_chance;
    public static float suffix_chance;
    public static int[] syllable_count_weighting;
    public static int[] terminal_blank_counts;

    public static void init(File configFile) {
        if (config == null) {
            config = new ConfigurationVN(configFile);
            loadConfiguration();
        }
    }

    public static void loadConfiguration() {
        custom_prefix = config.getStringWithoutDefaultsInComment(
                Reference.NAME_PREFIXES,
                Reference.CATEGORY_CUSTOM_SYLLABLE_POOL,
                NamePieces.CUSTOM_PREFIX_DEFAULT,
                Reference.DESCRIPTION_PREFIXES).trim().split("\\s*,\\s*");

        custom_root_initial = config.getStringWithoutDefaultsInComment(
                Reference.NAME_ROOT_INITIAL,
                Reference.CATEGORY_CUSTOM_SYLLABLE_POOL,
                NamePieces.CUSTOM_ROOT_INITIAL_DEFAULT,
                Reference.DESCRIPTION_ROOT_INITIAL).trim().split("\\s*,\\s*");

        custom_root_syllables = config.getStringWithoutDefaultsInComment(
                Reference.NAME_ROOT_SYLLABLES,
                Reference.CATEGORY_CUSTOM_SYLLABLE_POOL,
                NamePieces.CUSTOM_ROOT_SYL_BEGIN_DEFAULT,
                Reference.DESCRIPTION_ROOT_SYLLABLES).trim().split("\\s*,\\s*");

        custom_root_terminal = config.getStringWithoutDefaultsInComment(
                Reference.NAME_ROOT_TERMINAL,
                Reference.CATEGORY_CUSTOM_SYLLABLE_POOL,
                NamePieces.CUSTOM_ROOT_TERMINAL_DEFAULT,
                Reference.DESCRIPTION_ROOT_TERMINAL).trim().split("\\s*,\\s*");

        custom_suffix = config.getStringWithoutDefaultsInComment(
                "Suffixes",
                Reference.CATEGORY_CUSTOM_SYLLABLE_POOL,
                NamePieces.CUSTOM_SUFFIX_DEFAULT,
                Reference.DESCRIPTION_SUFFIXES).trim().split("\\s*,\\s*");

        syllable_count_weighting = config.get(
                Reference.CATEGORY_CUSTOM_SYLLABLE_POOL,
                Reference.NAME_SYLLABLE_COUNT_WEIGHTING,
                NamePieces.CUSTOM_SYLLABLE_COUNT_WEIGHTS,
                Reference.DESCRIPTION_SYLLABLE_COUNT_WEIGHTING).getIntList();

        terminal_blank_counts = config.get(
                Reference.CATEGORY_CUSTOM_SYLLABLE_POOL,
                Reference.NAME_TERMINAL_BLANK_COUNTS,
                NamePieces.CUSTOM_BLANK_TERMINAL_COUNTS,
                Reference.DESCRIPTION_TERMINAL_BLANK_COUNTS).getIntList();

        prefix_chance = config.getFloat(
                Reference.NAME_PREFIX_CHANCE,
                Reference.CATEGORY_CUSTOM_SYLLABLE_POOL,
                (custom_root_initial.length - 1) <= 0 ? 0
                        : ((float) custom_prefix.length - 1) / (custom_root_initial.length - 1),
                0.0F,
                1.0F,
                Reference.DESCRIPTION_PREFIX_CHANCE);

        suffix_chance = config.getFloat(
                Reference.NAME_SUFFIX_CHANCE,
                Reference.CATEGORY_CUSTOM_SYLLABLE_POOL,
                (custom_root_initial.length - 1) <= 0 ? 0
                        : ((float) custom_suffix.length - 1) / (custom_root_initial.length - 1),
                0.0F,
                1.0F,
                Reference.DESCRIPTION_SUFFIX_CHANCE);

        if (config.hasChanged()) config.save();
    }

}
