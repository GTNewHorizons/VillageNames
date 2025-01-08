package astrotibs.villagenames.name;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.config.pieces.AlienConfigHandler;
import astrotibs.villagenames.config.pieces.AlienVillageConfigHandler;
import astrotibs.villagenames.config.pieces.AngelConfigHandler;
import astrotibs.villagenames.config.pieces.CustomConfigHandler;
import astrotibs.villagenames.config.pieces.DemonConfigHandler;
import astrotibs.villagenames.config.pieces.DragonConfigHandler;
import astrotibs.villagenames.config.pieces.EndCityConfigHandler;
import astrotibs.villagenames.config.pieces.FortressConfigHandler;
import astrotibs.villagenames.config.pieces.GoblinConfigHandler;
import astrotibs.villagenames.config.pieces.GolemConfigHandler;
import astrotibs.villagenames.config.pieces.MansionConfigHandler;
import astrotibs.villagenames.config.pieces.MineshaftConfigHandler;
import astrotibs.villagenames.config.pieces.MonumentConfigHandler;
import astrotibs.villagenames.config.pieces.PetConfigHandler;
import astrotibs.villagenames.config.pieces.StrongholdConfigHandler;
import astrotibs.villagenames.config.pieces.TempleConfigHandler;
import astrotibs.villagenames.config.pieces.VillageConfigHandler;
import astrotibs.villagenames.config.pieces.VillagerConfigHandler;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.Reference;

// The whole point of this thing is to be a separate class that generates the names.

public class NameGenerator {

    /**
     * Enter in a name type to generate (e.g. "village"), and this will return a string list containing: [0] a header
     * tag (for village colors; unused so far) [1] prefix [2] root name -- this is the CORE NAME of interest [3] suffix
     */
    private static final String CAROT = "^";

    public static String[] newRandomName(String nameType, Random random) {
        // Unpack nameType into multiple possible name pools

        // Split input string by hyphen
        String[] nameType_raw = nameType.trim().split("\\s*-\\s*"); // Using regular expression \s* to optional remove
                                                                    // leading and tailing spaces

        // Cast all elements as lowercase for easier comparison
        String[] nameType_a = new String[nameType_raw.length];
        for (int input_i = 0; input_i < nameType_raw.length; input_i++) {
            nameType_a[input_i] = nameType_raw[input_i].toLowerCase().trim();
        }

        // Step 0: initialize empty syllable pools, into which will be added specific source pools
        String[] prefix = new String[] {};
        String[] root_initial = new String[] {};
        String[] root_sylBegin = new String[] {};
        String[] root_terminal = new String[] {};
        String[] suffix = new String[] {};

        float prefix_chance = 0F;
        float suffix_chance = 0F;
        int numnames;
        int normalization = 0;
        ArrayList<Integer> pooled_length_weights = new ArrayList<Integer>();
        ArrayList<Integer> pooled_terminal_blank_counts = new ArrayList<Integer>();

        // Load in syllable pieces

        if (Arrays.asList(nameType_a).contains("village")) {
            prefix = ArrayUtils.addAll(prefix, VillageConfigHandler.village_prefix);
            root_initial = ArrayUtils.addAll(root_initial, VillageConfigHandler.village_root_initial);
            root_sylBegin = ArrayUtils.addAll(root_sylBegin, VillageConfigHandler.village_root_syllables);
            root_terminal = ArrayUtils.addAll(root_terminal, VillageConfigHandler.village_root_terminal);
            suffix = ArrayUtils.addAll(suffix, VillageConfigHandler.village_suffix);

            numnames = VillageConfigHandler.village_root_initial.length;
            prefix_chance += (VillageConfigHandler.prefix_chance * numnames);
            suffix_chance += (VillageConfigHandler.suffix_chance * numnames);
            normalization += numnames;

            // Update the distribution of syllables
            for (int i = 0; i < VillageConfigHandler.syllable_count_weighting.length; i++) {
                if (pooled_length_weights.size() > i) // Add weights of this name length to the master weight array
                {
                    pooled_length_weights
                            .set(i, pooled_length_weights.get(i) + VillageConfigHandler.syllable_count_weighting[i]);
                } else // Weights of this name length don't exist in the master array. Expand the array.
                {
                    pooled_length_weights.add(VillageConfigHandler.syllable_count_weighting[i]);
                }
            }
            // Update the distribution of blank terminal entries
            for (int i = 0; i < VillageConfigHandler.terminal_blank_counts.length; i++) {
                if (pooled_terminal_blank_counts.size() > i) // Add blanks terminal entries for this name length to the
                                                             // master weight array
                {
                    pooled_terminal_blank_counts.set(
                            i,
                            pooled_terminal_blank_counts.get(i) + VillageConfigHandler.terminal_blank_counts[i]);
                } else // Blank terminal entries for this name length don't exist in the master array. Expand the array.
                {
                    pooled_terminal_blank_counts.add(VillageConfigHandler.terminal_blank_counts[i]);
                }
            }
        }
        if (Arrays.asList(nameType_a).contains("temple")) {
            prefix = ArrayUtils.addAll(prefix, TempleConfigHandler.temple_prefix);
            root_initial = ArrayUtils.addAll(root_initial, TempleConfigHandler.temple_root_initial);
            root_sylBegin = ArrayUtils.addAll(root_sylBegin, TempleConfigHandler.temple_root_syllables);
            root_terminal = ArrayUtils.addAll(root_terminal, TempleConfigHandler.temple_root_terminal);
            suffix = ArrayUtils.addAll(suffix, TempleConfigHandler.temple_suffix);

            numnames = TempleConfigHandler.temple_root_initial.length;
            prefix_chance += (TempleConfigHandler.prefix_chance * numnames);
            suffix_chance += (TempleConfigHandler.suffix_chance * numnames);
            normalization += numnames;

            // Update the distribution of syllables
            for (int i = 0; i < TempleConfigHandler.syllable_count_weighting.length; i++) {
                if (pooled_length_weights.size() > i) // Add weights of this name length to the master weight array
                {
                    pooled_length_weights
                            .set(i, pooled_length_weights.get(i) + TempleConfigHandler.syllable_count_weighting[i]);
                } else // Weights of this name length don't exist in the master array. Expand the array.
                {
                    pooled_length_weights.add(TempleConfigHandler.syllable_count_weighting[i]);
                }
            }
            // Update the distribution of blank terminal entries
            for (int i = 0; i < TempleConfigHandler.terminal_blank_counts.length; i++) {
                if (pooled_terminal_blank_counts.size() > i) // Add blanks terminal entries for this name length to the
                                                             // master weight array
                {
                    pooled_terminal_blank_counts
                            .set(i, pooled_terminal_blank_counts.get(i) + TempleConfigHandler.terminal_blank_counts[i]);
                } else // Blank terminal entries for this name length don't exist in the master array. Expand the array.
                {
                    pooled_terminal_blank_counts.add(TempleConfigHandler.terminal_blank_counts[i]);
                }
            }
        }
        if (Arrays.asList(nameType_a).contains("mineshaft")) {
            prefix = ArrayUtils.addAll(prefix, MineshaftConfigHandler.mineshaft_prefix);
            root_initial = ArrayUtils.addAll(root_initial, MineshaftConfigHandler.mineshaft_root_initial);
            root_sylBegin = ArrayUtils.addAll(root_sylBegin, MineshaftConfigHandler.mineshaft_root_syllables);
            root_terminal = ArrayUtils.addAll(root_terminal, MineshaftConfigHandler.mineshaft_root_terminal);
            suffix = ArrayUtils.addAll(suffix, MineshaftConfigHandler.mineshaft_suffix);

            numnames = MineshaftConfigHandler.mineshaft_root_initial.length;
            prefix_chance += (MineshaftConfigHandler.prefix_chance * numnames);
            suffix_chance += (MineshaftConfigHandler.suffix_chance * numnames);
            normalization += numnames;

            // Update the distribution of syllables
            for (int i = 0; i < MineshaftConfigHandler.syllable_count_weighting.length; i++) {
                if (pooled_length_weights.size() > i) // Add weights of this name length to the master weight array
                {
                    pooled_length_weights
                            .set(i, pooled_length_weights.get(i) + MineshaftConfigHandler.syllable_count_weighting[i]);
                } else // Weights of this name length don't exist in the master array. Expand the array.
                {
                    pooled_length_weights.add(MineshaftConfigHandler.syllable_count_weighting[i]);
                }
            }
            // Update the distribution of blank terminal entries
            for (int i = 0; i < MineshaftConfigHandler.terminal_blank_counts.length; i++) {
                if (pooled_terminal_blank_counts.size() > i) // Add blanks terminal entries for this name length to the
                                                             // master weight array
                {
                    pooled_terminal_blank_counts.set(
                            i,
                            pooled_terminal_blank_counts.get(i) + MineshaftConfigHandler.terminal_blank_counts[i]);
                } else // Blank terminal entries for this name length don't exist in the master array. Expand the array.
                {
                    pooled_terminal_blank_counts.add(MineshaftConfigHandler.terminal_blank_counts[i]);
                }
            }
        }
        if (Arrays.asList(nameType_a).contains("fortress")) {
            prefix = ArrayUtils.addAll(prefix, FortressConfigHandler.fortress_prefix);
            root_initial = ArrayUtils.addAll(root_initial, FortressConfigHandler.fortress_root_initial);
            root_sylBegin = ArrayUtils.addAll(root_sylBegin, FortressConfigHandler.fortress_root_syllables);
            root_terminal = ArrayUtils.addAll(root_terminal, FortressConfigHandler.fortress_root_terminal);
            suffix = ArrayUtils.addAll(suffix, FortressConfigHandler.fortress_suffix);

            numnames = FortressConfigHandler.fortress_root_initial.length;
            prefix_chance += (FortressConfigHandler.prefix_chance * numnames);
            suffix_chance += (FortressConfigHandler.suffix_chance * numnames);
            normalization += numnames;

            // Update the distribution of syllables
            for (int i = 0; i < FortressConfigHandler.syllable_count_weighting.length; i++) {
                if (pooled_length_weights.size() > i) // Add weights of this name length to the master weight array
                {
                    pooled_length_weights
                            .set(i, pooled_length_weights.get(i) + FortressConfigHandler.syllable_count_weighting[i]);
                } else // Weights of this name length don't exist in the master array. Expand the array.
                {
                    pooled_length_weights.add(FortressConfigHandler.syllable_count_weighting[i]);
                }
            }
            // Update the distribution of blank terminal entries
            for (int i = 0; i < FortressConfigHandler.terminal_blank_counts.length; i++) {
                if (pooled_terminal_blank_counts.size() > i) // Add blanks terminal entries for this name length to the
                                                             // master weight array
                {
                    pooled_terminal_blank_counts.set(
                            i,
                            pooled_terminal_blank_counts.get(i) + FortressConfigHandler.terminal_blank_counts[i]);
                } else // Blank terminal entries for this name length don't exist in the master array. Expand the array.
                {
                    pooled_terminal_blank_counts.add(FortressConfigHandler.terminal_blank_counts[i]);
                }
            }
        }
        if (Arrays.asList(nameType_a).contains("stronghold")) {
            prefix = ArrayUtils.addAll(prefix, StrongholdConfigHandler.stronghold_prefix);
            root_initial = ArrayUtils.addAll(root_initial, StrongholdConfigHandler.stronghold_root_initial);
            root_sylBegin = ArrayUtils.addAll(root_sylBegin, StrongholdConfigHandler.stronghold_root_syllables);
            root_terminal = ArrayUtils.addAll(root_terminal, StrongholdConfigHandler.stronghold_root_terminal);
            suffix = ArrayUtils.addAll(suffix, StrongholdConfigHandler.stronghold_suffix);

            numnames = StrongholdConfigHandler.stronghold_root_initial.length;
            prefix_chance += (StrongholdConfigHandler.prefix_chance * numnames);
            suffix_chance += (StrongholdConfigHandler.suffix_chance * numnames);
            normalization += numnames;

            // Update the distribution of syllables
            for (int i = 0; i < StrongholdConfigHandler.syllable_count_weighting.length; i++) {
                if (pooled_length_weights.size() > i) // Add weights of this name length to the master weight array
                {
                    pooled_length_weights
                            .set(i, pooled_length_weights.get(i) + StrongholdConfigHandler.syllable_count_weighting[i]);
                } else // Weights of this name length don't exist in the master array. Expand the array.
                {
                    pooled_length_weights.add(StrongholdConfigHandler.syllable_count_weighting[i]);
                }
            }
            // Update the distribution of blank terminal entries
            for (int i = 0; i < StrongholdConfigHandler.terminal_blank_counts.length; i++) {
                if (pooled_terminal_blank_counts.size() > i) // Add blanks terminal entries for this name length to the
                                                             // master weight array
                {
                    pooled_terminal_blank_counts.set(
                            i,
                            pooled_terminal_blank_counts.get(i) + StrongholdConfigHandler.terminal_blank_counts[i]);
                } else // Blank terminal entries for this name length don't exist in the master array. Expand the array.
                {
                    pooled_terminal_blank_counts.add(StrongholdConfigHandler.terminal_blank_counts[i]);
                }
            }
        }
        if (Arrays.asList(nameType_a).contains("monument")) {
            prefix = ArrayUtils.addAll(prefix, MonumentConfigHandler.monument_prefix);
            root_initial = ArrayUtils.addAll(root_initial, MonumentConfigHandler.monument_root_initial);
            root_sylBegin = ArrayUtils.addAll(root_sylBegin, MonumentConfigHandler.monument_root_syllables);
            root_terminal = ArrayUtils.addAll(root_terminal, MonumentConfigHandler.monument_root_terminal);
            suffix = ArrayUtils.addAll(suffix, MonumentConfigHandler.monument_suffix);

            numnames = MonumentConfigHandler.monument_root_initial.length;
            prefix_chance += (MonumentConfigHandler.prefix_chance * numnames);
            suffix_chance += (MonumentConfigHandler.suffix_chance * numnames);
            normalization += numnames;

            // Update the distribution of syllables
            for (int i = 0; i < MonumentConfigHandler.syllable_count_weighting.length; i++) {
                if (pooled_length_weights.size() > i) // Add weights of this name length to the master weight array
                {
                    pooled_length_weights
                            .set(i, pooled_length_weights.get(i) + MonumentConfigHandler.syllable_count_weighting[i]);
                } else // Weights of this name length don't exist in the master array. Expand the array.
                {
                    pooled_length_weights.add(MonumentConfigHandler.syllable_count_weighting[i]);
                }
            }
            // Update the distribution of blank terminal entries
            for (int i = 0; i < MonumentConfigHandler.terminal_blank_counts.length; i++) {
                if (pooled_terminal_blank_counts.size() > i) // Add blanks terminal entries for this name length to the
                                                             // master weight array
                {
                    pooled_terminal_blank_counts.set(
                            i,
                            pooled_terminal_blank_counts.get(i) + MonumentConfigHandler.terminal_blank_counts[i]);
                } else // Blank terminal entries for this name length don't exist in the master array. Expand the array.
                {
                    pooled_terminal_blank_counts.add(MonumentConfigHandler.terminal_blank_counts[i]);
                }
            }
        }
        if (Arrays.asList(nameType_a).contains("endcity")) {
            prefix = ArrayUtils.addAll(prefix, EndCityConfigHandler.endcity_prefix);
            root_initial = ArrayUtils.addAll(root_initial, EndCityConfigHandler.endcity_root_initial);
            root_sylBegin = ArrayUtils.addAll(root_sylBegin, EndCityConfigHandler.endcity_root_syllables);
            root_terminal = ArrayUtils.addAll(root_terminal, EndCityConfigHandler.endcity_root_terminal);
            suffix = ArrayUtils.addAll(suffix, EndCityConfigHandler.endcity_suffix);

            numnames = EndCityConfigHandler.endcity_root_initial.length;
            prefix_chance += (EndCityConfigHandler.prefix_chance * numnames);
            suffix_chance += (EndCityConfigHandler.suffix_chance * numnames);
            normalization += numnames;

            // Update the distribution of syllables
            for (int i = 0; i < EndCityConfigHandler.syllable_count_weighting.length; i++) {
                if (pooled_length_weights.size() > i) // Add weights of this name length to the master weight array
                {
                    pooled_length_weights
                            .set(i, pooled_length_weights.get(i) + EndCityConfigHandler.syllable_count_weighting[i]);
                } else // Weights of this name length don't exist in the master array. Expand the array.
                {
                    pooled_length_weights.add(EndCityConfigHandler.syllable_count_weighting[i]);
                }
            }
            // Update the distribution of blank terminal entries
            for (int i = 0; i < EndCityConfigHandler.terminal_blank_counts.length; i++) {
                if (pooled_terminal_blank_counts.size() > i) // Add blanks terminal entries for this name length to the
                                                             // master weight array
                {
                    pooled_terminal_blank_counts.set(
                            i,
                            pooled_terminal_blank_counts.get(i) + EndCityConfigHandler.terminal_blank_counts[i]);
                } else // Blank terminal entries for this name length don't exist in the master array. Expand the array.
                {
                    pooled_terminal_blank_counts.add(EndCityConfigHandler.terminal_blank_counts[i]);
                }
            }
        }
        if (Arrays.asList(nameType_a).contains("mansion")) {
            prefix = ArrayUtils.addAll(prefix, MansionConfigHandler.mansion_prefix);
            root_initial = ArrayUtils.addAll(root_initial, MansionConfigHandler.mansion_root_initial);
            root_sylBegin = ArrayUtils.addAll(root_sylBegin, MansionConfigHandler.mansion_root_syllables);
            root_terminal = ArrayUtils.addAll(root_terminal, MansionConfigHandler.mansion_root_terminal);
            suffix = ArrayUtils.addAll(suffix, MansionConfigHandler.mansion_suffix);

            numnames = MansionConfigHandler.mansion_root_initial.length;
            prefix_chance += (MansionConfigHandler.prefix_chance * numnames);
            suffix_chance += (MansionConfigHandler.suffix_chance * numnames);
            normalization += numnames;

            // Update the distribution of syllables
            for (int i = 0; i < MansionConfigHandler.syllable_count_weighting.length; i++) {
                if (pooled_length_weights.size() > i) // Add weights of this name length to the master weight array
                {
                    pooled_length_weights
                            .set(i, pooled_length_weights.get(i) + MansionConfigHandler.syllable_count_weighting[i]);
                } else // Weights of this name length don't exist in the master array. Expand the array.
                {
                    pooled_length_weights.add(MansionConfigHandler.syllable_count_weighting[i]);
                }
            }
            // Update the distribution of blank terminal entries
            for (int i = 0; i < MansionConfigHandler.terminal_blank_counts.length; i++) {
                if (pooled_terminal_blank_counts.size() > i) // Add blanks terminal entries for this name length to the
                                                             // master weight array
                {
                    pooled_terminal_blank_counts.set(
                            i,
                            pooled_terminal_blank_counts.get(i) + MansionConfigHandler.terminal_blank_counts[i]);
                } else // Blank terminal entries for this name length don't exist in the master array. Expand the array.
                {
                    pooled_terminal_blank_counts.add(MansionConfigHandler.terminal_blank_counts[i]);
                }
            }
        }
        if (Arrays.asList(nameType_a).contains("alien")) {
            prefix = ArrayUtils.addAll(prefix, AlienConfigHandler.alien_prefix);
            root_initial = ArrayUtils.addAll(root_initial, AlienConfigHandler.alien_root_initial);
            root_sylBegin = ArrayUtils.addAll(root_sylBegin, AlienConfigHandler.alien_root_syllables);
            root_terminal = ArrayUtils.addAll(root_terminal, AlienConfigHandler.alien_root_terminal);
            suffix = ArrayUtils.addAll(suffix, AlienConfigHandler.alien_suffix);

            numnames = AlienConfigHandler.alien_root_initial.length;
            prefix_chance += (AlienConfigHandler.prefix_chance * numnames);
            suffix_chance += (AlienConfigHandler.suffix_chance * numnames);
            normalization += numnames;

            // Update the distribution of syllables
            for (int i = 0; i < AlienConfigHandler.syllable_count_weighting.length; i++) {
                if (pooled_length_weights.size() > i) // Add weights of this name length to the master weight array
                {
                    pooled_length_weights
                            .set(i, pooled_length_weights.get(i) + AlienConfigHandler.syllable_count_weighting[i]);
                } else // Weights of this name length don't exist in the master array. Expand the array.
                {
                    pooled_length_weights.add(AlienConfigHandler.syllable_count_weighting[i]);
                }
            }
            // Update the distribution of blank terminal entries
            for (int i = 0; i < AlienConfigHandler.terminal_blank_counts.length; i++) {
                if (pooled_terminal_blank_counts.size() > i) // Add blanks terminal entries for this name length to the
                                                             // master weight array
                {
                    pooled_terminal_blank_counts
                            .set(i, pooled_terminal_blank_counts.get(i) + AlienConfigHandler.terminal_blank_counts[i]);
                } else // Blank terminal entries for this name length don't exist in the master array. Expand the array.
                {
                    pooled_terminal_blank_counts.add(AlienConfigHandler.terminal_blank_counts[i]);
                }
            }
        }
        if (Arrays.asList(nameType_a).contains("alienvillage")) {
            prefix = ArrayUtils.addAll(prefix, AlienVillageConfigHandler.alienvillage_prefix);
            root_initial = ArrayUtils.addAll(root_initial, AlienVillageConfigHandler.alienvillage_root_initial);
            root_sylBegin = ArrayUtils.addAll(root_sylBegin, AlienVillageConfigHandler.alienvillage_root_syllables);
            root_terminal = ArrayUtils.addAll(root_terminal, AlienVillageConfigHandler.alienvillage_root_terminal);
            suffix = ArrayUtils.addAll(suffix, AlienVillageConfigHandler.alienvillage_suffix);

            numnames = AlienVillageConfigHandler.alienvillage_root_initial.length;
            prefix_chance += (AlienVillageConfigHandler.prefix_chance * numnames);
            suffix_chance += (AlienVillageConfigHandler.suffix_chance * numnames);
            normalization += numnames;

            // Update the distribution of syllables
            for (int i = 0; i < AlienVillageConfigHandler.syllable_count_weighting.length; i++) {
                if (pooled_length_weights.size() > i) // Add weights of this name length to the master weight array
                {
                    pooled_length_weights.set(
                            i,
                            pooled_length_weights.get(i) + AlienVillageConfigHandler.syllable_count_weighting[i]);
                } else // Weights of this name length don't exist in the master array. Expand the array.
                {
                    pooled_length_weights.add(AlienVillageConfigHandler.syllable_count_weighting[i]);
                }
            }
            // Update the distribution of blank terminal entries
            for (int i = 0; i < AlienVillageConfigHandler.terminal_blank_counts.length; i++) {
                if (pooled_terminal_blank_counts.size() > i) // Add blanks terminal entries for this name length to the
                                                             // master weight array
                {
                    pooled_terminal_blank_counts.set(
                            i,
                            pooled_terminal_blank_counts.get(i) + AlienVillageConfigHandler.terminal_blank_counts[i]);
                } else // Blank terminal entries for this name length don't exist in the master array. Expand the array.
                {
                    pooled_terminal_blank_counts.add(AlienVillageConfigHandler.terminal_blank_counts[i]);
                }
            }
        }
        if (Arrays.asList(nameType_a).contains("goblin")) {
            prefix = ArrayUtils.addAll(prefix, GoblinConfigHandler.goblin_prefix);
            root_initial = ArrayUtils.addAll(root_initial, GoblinConfigHandler.goblin_root_initial);
            root_sylBegin = ArrayUtils.addAll(root_sylBegin, GoblinConfigHandler.goblin_root_syllables);
            root_terminal = ArrayUtils.addAll(root_terminal, GoblinConfigHandler.goblin_root_terminal);
            suffix = ArrayUtils.addAll(suffix, GoblinConfigHandler.goblin_suffix);

            numnames = GoblinConfigHandler.goblin_root_initial.length;
            prefix_chance += (GoblinConfigHandler.prefix_chance * numnames);
            suffix_chance += (GoblinConfigHandler.suffix_chance * numnames);
            normalization += numnames;

            // Update the distribution of syllables
            for (int i = 0; i < GoblinConfigHandler.syllable_count_weighting.length; i++) {
                if (pooled_length_weights.size() > i) // Add weights of this name length to the master weight array
                {
                    pooled_length_weights
                            .set(i, pooled_length_weights.get(i) + GoblinConfigHandler.syllable_count_weighting[i]);
                } else // Weights of this name length don't exist in the master array. Expand the array.
                {
                    pooled_length_weights.add(GoblinConfigHandler.syllable_count_weighting[i]);
                }
            }
            // Update the distribution of blank terminal entries
            for (int i = 0; i < GoblinConfigHandler.terminal_blank_counts.length; i++) {
                if (pooled_terminal_blank_counts.size() > i) // Add blanks terminal entries for this name length to the
                                                             // master weight array
                {
                    pooled_terminal_blank_counts
                            .set(i, pooled_terminal_blank_counts.get(i) + GoblinConfigHandler.terminal_blank_counts[i]);
                } else // Blank terminal entries for this name length don't exist in the master array. Expand the array.
                {
                    pooled_terminal_blank_counts.add(GoblinConfigHandler.terminal_blank_counts[i]);
                }
            }
        }
        if (Arrays.asList(nameType_a).contains("golem")) {
            prefix = ArrayUtils.addAll(prefix, GolemConfigHandler.golem_prefix);
            root_initial = ArrayUtils.addAll(root_initial, GolemConfigHandler.golem_root_initial);
            root_sylBegin = ArrayUtils.addAll(root_sylBegin, GolemConfigHandler.golem_root_syllables);
            root_terminal = ArrayUtils.addAll(root_terminal, GolemConfigHandler.golem_root_terminal);
            suffix = ArrayUtils.addAll(suffix, GolemConfigHandler.golem_suffix);

            numnames = GolemConfigHandler.golem_root_initial.length;
            prefix_chance += (GolemConfigHandler.prefix_chance * numnames);
            suffix_chance += (GolemConfigHandler.suffix_chance * numnames);
            normalization += numnames;

            // Update the distribution of syllables
            for (int i = 0; i < GolemConfigHandler.syllable_count_weighting.length; i++) {
                if (pooled_length_weights.size() > i) // Add weights of this name length to the master weight array
                {
                    pooled_length_weights
                            .set(i, pooled_length_weights.get(i) + GolemConfigHandler.syllable_count_weighting[i]);
                } else // Weights of this name length don't exist in the master array. Expand the array.
                {
                    pooled_length_weights.add(GolemConfigHandler.syllable_count_weighting[i]);
                }
            }
            // Update the distribution of blank terminal entries
            for (int i = 0; i < GolemConfigHandler.terminal_blank_counts.length; i++) {
                if (pooled_terminal_blank_counts.size() > i) // Add blanks terminal entries for this name length to the
                                                             // master weight array
                {
                    pooled_terminal_blank_counts
                            .set(i, pooled_terminal_blank_counts.get(i) + GolemConfigHandler.terminal_blank_counts[i]);
                } else // Blank terminal entries for this name length don't exist in the master array. Expand the array.
                {
                    pooled_terminal_blank_counts.add(GolemConfigHandler.terminal_blank_counts[i]);
                }
            }
        }
        if (Arrays.asList(nameType_a).contains("demon")) {
            prefix = ArrayUtils.addAll(prefix, DemonConfigHandler.demon_prefix);
            root_initial = ArrayUtils.addAll(root_initial, DemonConfigHandler.demon_root_initial);
            root_sylBegin = ArrayUtils.addAll(root_sylBegin, DemonConfigHandler.demon_root_syllables);
            root_terminal = ArrayUtils.addAll(root_terminal, DemonConfigHandler.demon_root_terminal);
            suffix = ArrayUtils.addAll(suffix, DemonConfigHandler.demon_suffix);

            numnames = DemonConfigHandler.demon_root_initial.length;
            prefix_chance += (DemonConfigHandler.prefix_chance * numnames);
            suffix_chance += (DemonConfigHandler.suffix_chance * numnames);
            normalization += numnames;

            // Update the distribution of syllables
            for (int i = 0; i < DemonConfigHandler.syllable_count_weighting.length; i++) {
                if (pooled_length_weights.size() > i) // Add weights of this name length to the master weight array
                {
                    pooled_length_weights
                            .set(i, pooled_length_weights.get(i) + DemonConfigHandler.syllable_count_weighting[i]);
                } else // Weights of this name length don't exist in the master array. Expand the array.
                {
                    pooled_length_weights.add(DemonConfigHandler.syllable_count_weighting[i]);
                }
            }
            // Update the distribution of blank terminal entries
            for (int i = 0; i < DemonConfigHandler.terminal_blank_counts.length; i++) {
                if (pooled_terminal_blank_counts.size() > i) // Add blanks terminal entries for this name length to the
                                                             // master weight array
                {
                    pooled_terminal_blank_counts
                            .set(i, pooled_terminal_blank_counts.get(i) + DemonConfigHandler.terminal_blank_counts[i]);
                } else // Blank terminal entries for this name length don't exist in the master array. Expand the array.
                {
                    pooled_terminal_blank_counts.add(DemonConfigHandler.terminal_blank_counts[i]);
                }
            }
        }
        if (Arrays.asList(nameType_a).contains("angel")) {
            prefix = ArrayUtils.addAll(prefix, AngelConfigHandler.angel_prefix);
            root_initial = ArrayUtils.addAll(root_initial, AngelConfigHandler.angel_root_initial);
            root_sylBegin = ArrayUtils.addAll(root_sylBegin, AngelConfigHandler.angel_root_syllables);
            root_terminal = ArrayUtils.addAll(root_terminal, AngelConfigHandler.angel_root_terminal);
            suffix = ArrayUtils.addAll(suffix, AngelConfigHandler.angel_suffix);

            numnames = AngelConfigHandler.angel_root_initial.length;
            prefix_chance += (AngelConfigHandler.prefix_chance * numnames);
            suffix_chance += (AngelConfigHandler.suffix_chance * numnames);
            normalization += numnames;

            // Update the distribution of syllables
            for (int i = 0; i < AngelConfigHandler.syllable_count_weighting.length; i++) {
                if (pooled_length_weights.size() > i) // Add weights of this name length to the master weight array
                {
                    pooled_length_weights
                            .set(i, pooled_length_weights.get(i) + AngelConfigHandler.syllable_count_weighting[i]);
                } else // Weights of this name length don't exist in the master array. Expand the array.
                {
                    pooled_length_weights.add(AngelConfigHandler.syllable_count_weighting[i]);
                }
            }
            // Update the distribution of blank terminal entries
            for (int i = 0; i < AngelConfigHandler.terminal_blank_counts.length; i++) {
                if (pooled_terminal_blank_counts.size() > i) // Add blanks terminal entries for this name length to the
                                                             // master weight array
                {
                    pooled_terminal_blank_counts
                            .set(i, pooled_terminal_blank_counts.get(i) + AngelConfigHandler.terminal_blank_counts[i]);
                } else // Blank terminal entries for this name length don't exist in the master array. Expand the array.
                {
                    pooled_terminal_blank_counts.add(AngelConfigHandler.terminal_blank_counts[i]);
                }
            }
        }
        if (Arrays.asList(nameType_a).contains("dragon")) {
            prefix = ArrayUtils.addAll(prefix, DragonConfigHandler.dragon_prefix);
            root_initial = ArrayUtils.addAll(root_initial, DragonConfigHandler.dragon_root_initial);
            root_sylBegin = ArrayUtils.addAll(root_sylBegin, DragonConfigHandler.dragon_root_syllables);
            root_terminal = ArrayUtils.addAll(root_terminal, DragonConfigHandler.dragon_root_terminal);
            suffix = ArrayUtils.addAll(suffix, DragonConfigHandler.dragon_suffix);

            numnames = DragonConfigHandler.dragon_root_initial.length;
            prefix_chance += (DragonConfigHandler.prefix_chance * numnames);
            suffix_chance += (DragonConfigHandler.suffix_chance * numnames);
            normalization += numnames;

            // Update the distribution of syllables
            for (int i = 0; i < DragonConfigHandler.syllable_count_weighting.length; i++) {
                if (pooled_length_weights.size() > i) // Add weights of this name length to the master weight array
                {
                    pooled_length_weights
                            .set(i, pooled_length_weights.get(i) + DragonConfigHandler.syllable_count_weighting[i]);
                } else // Weights of this name length don't exist in the master array. Expand the array.
                {
                    pooled_length_weights.add(DragonConfigHandler.syllable_count_weighting[i]);
                }
            }
            // Update the distribution of blank terminal entries
            for (int i = 0; i < DragonConfigHandler.terminal_blank_counts.length; i++) {
                if (pooled_terminal_blank_counts.size() > i) // Add blanks terminal entries for this name length to the
                                                             // master weight array
                {
                    pooled_terminal_blank_counts
                            .set(i, pooled_terminal_blank_counts.get(i) + DragonConfigHandler.terminal_blank_counts[i]);
                } else // Blank terminal entries for this name length don't exist in the master array. Expand the array.
                {
                    pooled_terminal_blank_counts.add(DragonConfigHandler.terminal_blank_counts[i]);
                }
            }
        }
        if (Arrays.asList(nameType_a).contains("pet")) {
            prefix = ArrayUtils.addAll(prefix, PetConfigHandler.pet_prefix);
            root_initial = ArrayUtils.addAll(root_initial, PetConfigHandler.pet_root_initial);
            root_sylBegin = ArrayUtils.addAll(root_sylBegin, PetConfigHandler.pet_root_syllables);
            root_terminal = ArrayUtils.addAll(root_terminal, PetConfigHandler.pet_root_terminal);
            suffix = ArrayUtils.addAll(suffix, PetConfigHandler.pet_suffix);

            numnames = PetConfigHandler.pet_root_initial.length;
            prefix_chance += (PetConfigHandler.prefix_chance * numnames);
            suffix_chance += (PetConfigHandler.suffix_chance * numnames);
            normalization += numnames;

            // Update the distribution of syllables
            for (int i = 0; i < PetConfigHandler.syllable_count_weighting.length; i++) {
                if (pooled_length_weights.size() > i) // Add weights of this name length to the master weight array
                {
                    pooled_length_weights
                            .set(i, pooled_length_weights.get(i) + PetConfigHandler.syllable_count_weighting[i]);
                } else // Weights of this name length don't exist in the master array. Expand the array.
                {
                    pooled_length_weights.add(PetConfigHandler.syllable_count_weighting[i]);
                }
            }
            // Update the distribution of blank terminal entries
            for (int i = 0; i < PetConfigHandler.terminal_blank_counts.length; i++) {
                if (pooled_terminal_blank_counts.size() > i) // Add blanks terminal entries for this name length to the
                                                             // master weight array
                {
                    pooled_terminal_blank_counts
                            .set(i, pooled_terminal_blank_counts.get(i) + PetConfigHandler.terminal_blank_counts[i]);
                } else // Blank terminal entries for this name length don't exist in the master array. Expand the array.
                {
                    pooled_terminal_blank_counts.add(PetConfigHandler.terminal_blank_counts[i]);
                }
            }
        }
        if (Arrays.asList(nameType_a).contains("custom")) {
            prefix = ArrayUtils.addAll(prefix, CustomConfigHandler.custom_prefix);
            root_initial = ArrayUtils.addAll(root_initial, CustomConfigHandler.custom_root_initial);
            root_sylBegin = ArrayUtils.addAll(root_sylBegin, CustomConfigHandler.custom_root_syllables);
            root_terminal = ArrayUtils.addAll(root_terminal, CustomConfigHandler.custom_root_terminal);
            suffix = ArrayUtils.addAll(suffix, CustomConfigHandler.custom_suffix);

            numnames = CustomConfigHandler.custom_root_initial.length;
            prefix_chance += (CustomConfigHandler.prefix_chance * numnames);
            suffix_chance += (CustomConfigHandler.suffix_chance * numnames);
            normalization += numnames;

            // Update the distribution of syllables
            for (int i = 0; i < CustomConfigHandler.syllable_count_weighting.length; i++) {
                if (pooled_length_weights.size() > i) // Add weights of this name length to the master weight array
                {
                    pooled_length_weights
                            .set(i, pooled_length_weights.get(i) + CustomConfigHandler.syllable_count_weighting[i]);
                } else // Weights of this name length don't exist in the master array. Expand the array.
                {
                    pooled_length_weights.add(CustomConfigHandler.syllable_count_weighting[i]);
                }
            }
            // Update the distribution of blank terminal entries
            for (int i = 0; i < CustomConfigHandler.terminal_blank_counts.length; i++) {
                if (pooled_terminal_blank_counts.size() > i) // Add blanks terminal entries for this name length to the
                                                             // master weight array
                {
                    pooled_terminal_blank_counts
                            .set(i, pooled_terminal_blank_counts.get(i) + CustomConfigHandler.terminal_blank_counts[i]);
                } else // Blank terminal entries for this name length don't exist in the master array. Expand the array.
                {
                    pooled_terminal_blank_counts.add(CustomConfigHandler.terminal_blank_counts[i]);
                }
            }
        }
        // It's possible the player made a mistake and no name pieces were correctly entered. If so, default to
        // "villager"
        if (Arrays.asList(nameType_a).contains("villager") // OR, the user deliberately chose "villager"
                || root_initial.length <= 0 // No previous entries were chosen
        ) {
            if (!Arrays.asList(nameType_a).contains("villager") && (root_initial.length <= 0)) {
                if (GeneralConfig.debugMessages)
                    LogHelper.error("Submitted nameType contained no valid entries! Defaulting to Villager name pool.");
            }

            prefix = ArrayUtils.addAll(prefix, VillagerConfigHandler.villager_prefix);
            root_initial = ArrayUtils.addAll(root_initial, VillagerConfigHandler.villager_root_initial);
            root_sylBegin = ArrayUtils.addAll(root_sylBegin, VillagerConfigHandler.villager_root_syllables);
            root_terminal = ArrayUtils.addAll(root_terminal, VillagerConfigHandler.villager_root_terminal);
            suffix = ArrayUtils.addAll(suffix, VillagerConfigHandler.villager_suffix);

            numnames = VillagerConfigHandler.villager_root_initial.length;
            prefix_chance += (VillagerConfigHandler.prefix_chance * numnames);
            suffix_chance += (VillagerConfigHandler.suffix_chance * numnames);
            normalization += numnames;

            // Update the distribution of syllables
            for (int i = 0; i < VillagerConfigHandler.syllable_count_weighting.length; i++) {
                if (pooled_length_weights.size() > i) // Add weights of this name length to the master weight array
                {
                    pooled_length_weights
                            .set(i, pooled_length_weights.get(i) + VillagerConfigHandler.syllable_count_weighting[i]);
                } else // Weights of this name length don't exist in the master array. Expand the array.
                {
                    pooled_length_weights.add(VillagerConfigHandler.syllable_count_weighting[i]);
                }
            }
            // Update the distribution of blank terminal entries
            for (int i = 0; i < VillagerConfigHandler.terminal_blank_counts.length; i++) {
                if (pooled_terminal_blank_counts.size() > i) // Add blanks terminal entries for this name length to the
                                                             // master weight array
                {
                    pooled_terminal_blank_counts.set(
                            i,
                            pooled_terminal_blank_counts.get(i) + VillagerConfigHandler.terminal_blank_counts[i]);
                } else // Blank terminal entries for this name length don't exist in the master array. Expand the array.
                {
                    pooled_terminal_blank_counts.add(VillagerConfigHandler.terminal_blank_counts[i]);
                }
            }
        }
        // Normalize prefix/suffix probabilities based on the pools provided
        if (normalization > 0) {
            prefix_chance /= normalization;
            suffix_chance /= normalization;
        }

        // The three pieces of interest
        String r_prefix = "";
        String r_suffix = "";
        String rootName = "";
        StringBuilder sb = new StringBuilder();

        int rootname_syllable_inserts = 1; // How many syllables are inserted between the name's starting and ending
                                           // half-syllables

        // These integers will get iterated over every time a root generation fails.
        // An exception is thrown if one gets to 50--pretty generous, if I say so.
        // Someone has to have REALLY bungled up syllable pools to have done this.
        int tooManyFailures = 50;
        int blankRoot = 0;
        int sizeOverflow = 0;
        int sizeUnderflow = 0;
        int repeatedChar = 0;
        int filterFail = 0;
        int prefixsuffixFail = 0;

        while (true) {
            // Step 1: Generate a prefix.
            r_prefix = "";
            if (random.nextFloat() < prefix_chance && normalization > 0 && prefix.length > 0) {
                r_prefix = (prefix[random.nextInt(prefix.length)]).trim();
            }

            // Step 3: Generate a suffix.
            r_suffix = "";
            if (random.nextFloat() < suffix_chance && normalization > 0 && suffix.length > 0) {
                r_suffix = (suffix[random.nextInt(suffix.length)]).trim();
            }

            if (r_prefix.equals("") || r_suffix.equals("") || !r_prefix.equals(r_suffix)) {
                break;
            } else {
                if (++prefixsuffixFail >= tooManyFailures) {
                    String errorMessage = "Name type " + nameType
                            + " Matched too many prefixes and suffixes! Check your syllable configs.";
                    LogHelper.fatal(errorMessage);
                    throw new RuntimeException(errorMessage);
                }
            }
        }

        // Step 2: Generate a proper (root) name.

        // The while loop continues until a valid name is generated or an exception is thrown
        while (true) {
            // Determine how long this name will be

            // Compute the total weight of all items together
            int totalWeight = 0;

            for (int i = 0; i < pooled_length_weights.size(); i++) {
                totalWeight += pooled_length_weights.get(i);
            }

            if (totalWeight <= 0) {
                String errorMessage = "Name type " + nameType
                        + " total syllable weighting was non-positive! Check the weighting values in your configs.";
                LogHelper.fatal(errorMessage);
                throw new RuntimeException(errorMessage);
            }

            // Now choose a random index
            int randomObject = random.nextInt(totalWeight);
            for (int i = 0; i < pooled_length_weights.size(); ++i) {
                randomObject -= pooled_length_weights.get(i);
                if (randomObject <= 0) {
                    rootname_syllable_inserts = i; // We've determined the length of the name.
                    break;
                }
            }

            // Step 2.1: Generate the beginning sound of the name
            if (root_initial.length <= 0) {
                String errorMessage = "Name type " + nameType
                        + " has no root-initial entries! No name can be constructed!";
                LogHelper.fatal(errorMessage);
                throw new RuntimeException(errorMessage);
            } else {
                sb.delete(0, sb.length());
                sb.append(root_initial[random.nextInt(root_initial.length)]);
            }

            // Step 2.2: Generate some number of inserted syllables
            for (int i = 0; i < rootname_syllable_inserts; i++) {
                if (root_sylBegin.length <= 0) {
                    String errorMessage = "Name type " + nameType
                            + " has no root-syllable entries! You need at least one, even if it's the blank entry character: ^";
                    LogHelper.error(errorMessage);
                    sb.append(CAROT);
                } else {
                    sb.append(root_sylBegin[random.nextInt(root_sylBegin.length)]);
                }
            }

            // Step 2.3: Generate the ending sound of the name, if any
            if (root_terminal.length <= 0) {
                String errorMessage = "Name type " + nameType
                        + " has no root-terminal entries! You need at least one, even if it's the blank entry character: ^";
                LogHelper.error(errorMessage);
                sb.append(CAROT);
            } else {
                String random_root_terminal = root_terminal[random.nextInt(root_terminal.length)];
                String error_using_carot_as_terminal_weighting = "Using the raw number of ^ characters to determine the chances of a blank root terminal character instead.";
                boolean terminal_character_accepted = false;

                // Determine if the end sound is supposed to be blank or not
                if (pooled_terminal_blank_counts.size() < rootname_syllable_inserts + 1) {
                    LogHelper.error(
                            "You're missing the the corresponding value in your \""
                                    + Reference.NAME_TERMINAL_BLANK_COUNTS
                                    + "\" config entry. Did you change something?");
                } else {
                    // Determine the chance that the terminal character will be blank
                    int name_weight = pooled_length_weights.get(rootname_syllable_inserts);
                    int terminal_blank_weight = pooled_terminal_blank_counts.get(rootname_syllable_inserts);

                    if (name_weight < terminal_blank_weight) {
                        LogHelper.error(
                                "Your \"" + Reference.NAME_TERMINAL_BLANK_COUNTS
                                        + "\" config entry is higher than the corresponding \""
                                        + Reference.NAME_SYLLABLE_COUNT_WEIGHTING
                                        + "\", implying there are more blank endings than names. Please check your configs.");
                    } else if (name_weight < 0) {
                        LogHelper.error(
                                "Your \"" + Reference.NAME_SYLLABLE_COUNT_WEIGHTING
                                        + "\" is less than zero. Please check your configs.");
                    } else if (terminal_blank_weight < 0) {
                        LogHelper.error(
                                "Your \"" + Reference.NAME_TERMINAL_BLANK_COUNTS
                                        + "\" is less than zero. Please check your configs.");
                    } else {
                        // Determine at this stage whether to use a blank terminal character or not
                        boolean root_terminal_is_blank = random.nextInt(name_weight) < terminal_blank_weight;

                        if (root_terminal_is_blank) {
                            terminal_character_accepted = true;
                            sb.append(CAROT);
                        } else {
                            // Try for some number of times to generate an end character
                            for (int i = 0; i < tooManyFailures * 2; i++) {
                                String try_terminal_character = root_terminal[random.nextInt(root_terminal.length)];
                                if ((try_terminal_character.trim().equals(CAROT) && root_terminal_is_blank)
                                        || (!try_terminal_character.trim().equals(CAROT) && !root_terminal_is_blank)) {
                                    // Terminal character agrees with our choice (blank vs non-blank)
                                    terminal_character_accepted = true;
                                    sb.append(try_terminal_character);
                                    break;
                                }
                            }
                        }

                        if (!terminal_character_accepted) {
                            LogHelper.error("Failed to generate an appropriate terminal character.");
                        }
                    }
                }

                if (!terminal_character_accepted) {
                    LogHelper.error(error_using_carot_as_terminal_weighting);
                    sb.append(random_root_terminal);
                }
            }

            // Step 2V: clean up for validation

            rootName = sb.toString().trim();

            // Remove any spaces
            rootName = rootName.replace(" ", "");
            // Replace underscores here with INTENTIONAL spaces.
            rootName = rootName.replaceAll("\\_", " ");
            // Replace carots with nothing, because they were used as blank placeholders
            rootName = rootName.replace(CAROT, "");

            // I have to reject this (root) name if it's not within the allotted size threshold.
            // Also I should ensure the last three characters are not all the same.

            if (rootName.length() <= 15) {
                // Make sure a six-letter name isn't the same two letters three times
                if (rootName.length() >= 6) {
                    // Now, make sure the same characters don't appear in the name three times in a row
                    char[] nameRootArray = rootName.toLowerCase().toCharArray();
                    int consecutives = 0;
                    for (int ci = 0; ci <= nameRootArray.length - 6; ci++) {
                        if (nameRootArray[ci] == nameRootArray[ci + 2] && nameRootArray[ci] == nameRootArray[ci + 4]
                                && nameRootArray[ci + 1] == nameRootArray[ci + 3]
                                && nameRootArray[ci + 1] == nameRootArray[ci + 5]) {
                            consecutives++;
                            break;
                        }
                    }
                    if (consecutives > 0) {
                        repeatedChar++; // Detected three of the same letter in a row.
                    }
                }

                if (rootName.length() >= 3) {
                    // Now, make sure the same characters don't appear in the name three times in a row
                    char[] nameRootArray = rootName.toLowerCase().toCharArray();
                    int consecutives = 0;
                    for (int ci = 0; ci < nameRootArray.length - 2; ci++) {
                        if (nameRootArray[ci] == nameRootArray[ci + 1] && nameRootArray[ci] == nameRootArray[ci + 2]) {
                            consecutives++;
                        }
                    }
                    if (consecutives == 0) {
                        // Do a content scan
                        if (!contentScan(rootName)) {
                            // Passes all the checks! Accept the name!
                            break;
                        }
                        // Something caught the attention of the filter
                        filterFail++;
                    } else {
                        repeatedChar++; // Detected three of the same letter in a row.
                    }

                }
                // Now ensure that a two-letter name isn't the same letter twice.
                else if (rootName.length() == 2) {
                    if (rootName.toLowerCase().charAt(0) != rootName.toLowerCase().charAt(1)) {
                        // Do a content scan
                        if (!contentScan(rootName)) {
                            // Passes all the checks! Accept the name!
                            break;
                        }
                        // Something caught the attention of the filter
                        filterFail++;
                    }
                } else if (rootName.length() > 0) {
                    sizeUnderflow++; // Root name is too short.
                } else {
                    blankRoot++; // Root name is blank.
                }
            } else { // Root name is too long.
                sizeOverflow++;
            }

            // Step 2X: The Graveyard
            // If we counted too many invalid name attempts, throw an exception
            if (sizeOverflow >= tooManyFailures) {
                String errorMessage = "Name type " + nameType + " names are too long! Check your syllable lengths.";
                LogHelper.fatal(errorMessage);
                throw new RuntimeException(errorMessage);
            }
            if (sizeUnderflow >= tooManyFailures) {
                String errorMessage = "Name type " + nameType + " names are too short! Check your syllables configs.";
                LogHelper.fatal(errorMessage);
                throw new RuntimeException(errorMessage);
            }
            if (blankRoot >= tooManyFailures) {
                String errorMessage = "Name type " + nameType + " Produced blank names! Check your syllable configs.";
                LogHelper.fatal(errorMessage);
                throw new RuntimeException(errorMessage);
            }
            if (repeatedChar >= tooManyFailures) {
                String errorMessage = "Name type " + nameType
                        + " has too many consecutive repeated letters or pairs of letters! Check your syllable configs.";
                LogHelper.fatal(errorMessage);
                throw new RuntimeException(errorMessage);
            }
            if (filterFail >= tooManyFailures) {
                String errorMessage = "Name type " + nameType
                        + " has tripped the content filter too many times. Are you being naughty?";
                LogHelper.fatal(errorMessage);
                throw new RuntimeException(errorMessage);
            }
        }

        // Step 4: Grab a header tag.
        String headerTags = GeneralConfig.headerTags.trim(); // Just in case some idiot added spaces

        String[] nameStringArray = { headerTags, r_prefix, rootName, r_suffix };

        return nameStringArray;
    }

    /**
     * Generate a profession tag to append to their name
     * 
     * @param villagerProfession: integer to represent profession (0 to 5)
     * @param villagerCareer:     integer to represent career (0 before 1.8; 1+ otherwise)
     * @param nitwitProfession:   name to assign to a nitwit (profession 5)
     * @return
     */
    public static String getCareerTag(String entityClasspath, int villagerProfession, int villagerCareer) {
        StringBuilder careerTag = (new StringBuilder()).append("(");

        // The entity is identified in the "clickable" or "automatic" config entry
        if (GeneralConfig.modNameMappingClickable_map.get("ClassPaths").contains(entityClasspath)) {
            careerTag.append(
                    ((String) ((GeneralConfig.modNameMappingClickable_map.get("Professions"))
                            .get(GeneralConfig.modNameMappingClickable_map.get("ClassPaths").indexOf(entityClasspath))))
                                    .trim());
        } else if (GeneralConfig.modNameMappingAutomatic_map.get("ClassPaths").contains(entityClasspath)) {
            careerTag.append(
                    ((String) ((GeneralConfig.modNameMappingAutomatic_map.get("Professions"))
                            .get(GeneralConfig.modNameMappingAutomatic_map.get("ClassPaths").indexOf(entityClasspath))))
                                    .trim());
        }

        // Handle More Planets's Nibiru Villager
        else if (entityClasspath.equals(ModObjects.MPNibiruVillagerClass) // 1.7 version
                || entityClasspath.equals(ModObjects.MPNibiruVillagerClassModern) // 1.10 version
        ) {
            switch (villagerProfession % 3) {
                case 0: // Farmer profession
                    careerTag.append("Farmer");
                    break;
                case 1: // Librarian profession
                    careerTag.append("Librarian");
                    break;
                case 2: // Priest profession
                    careerTag.append("Medic");
                    break;
            }
        }

        // Unfortunately, the I18n is client-side only, and this method is only called server-side.
        // I have to plug in the English versions for BOTH sides.

        else { // Ordinary vanilla-style villager, even it's using non-vanilla profession and career IDs

            switch (villagerProfession) {
                case 0: // Farmer profession
                    switch (villagerCareer) {
                        case 1:
                            careerTag.append("Farmer");
                            break;
                        case 2:
                            careerTag.append("Fisherman");
                            break;
                        case 3:
                            careerTag.append("Shepherd");
                            break;
                        case 4:
                            careerTag.append("Fletcher");
                            break;
                        default:
                            careerTag.append("Farmer");
                            break;
                    }
                    break;
                case 1: // Librarian profession
                    switch (villagerCareer) {
                        case 1:
                            careerTag.append("Librarian");
                            break;
                        case 2:
                            careerTag.append("Cartographer");
                            break;
                        default:
                            careerTag.append("Librarian");
                            break;
                    }
                    break;
                case 2: // Priest profession
                    switch (villagerCareer) {
                        case 1:
                            careerTag.append("Cleric");
                            break;
                        default:
                            careerTag.append("Priest");
                            break;
                    }
                    break;
                case 3: // Blacksmith profession
                    switch (villagerCareer) {
                        case 1:
                            careerTag.append("Armorer");
                            break;
                        case 2:
                            careerTag.append("Weaponsmith");
                            break;
                        case 3:
                            careerTag.append("Toolsmith");
                            break;
                        case 4:
                            careerTag.append("Mason");
                            break;
                        default:
                            careerTag.append("Blacksmith");
                            break;
                    }
                    break;
                case 4: // Butcher profession
                    switch (villagerCareer) {
                        case 1:
                            careerTag.append("Butcher");
                            break;
                        case 2:
                            careerTag.append("Leatherworker");
                            break;
                        default:
                            careerTag.append("Butcher");
                            break;
                    }
                    break;
                case 5: // Nitwit profession
                    if (GeneralConfig.enableNitwit) {
                        break;
                    }
                    String nitwitCareer = ((GeneralConfig.nitwitProfession.trim()).equals("")
                            || (GeneralConfig.nitwitProfession.toLowerCase().trim()).equals("null")) ? ""
                                    : GeneralConfig.nitwitProfession;
                    switch (villagerCareer) {
                        case 1:
                            careerTag.append(nitwitCareer);
                            break;
                        default:
                            careerTag.append(nitwitCareer);
                            break;
                    }
                    break;
            }

            if (!(villagerProfession >= 0 && villagerProfession <= (GeneralConfig.enableNitwit ? 5 : 4))) // This is a
                                                                                                          // modded
                                                                                                          // profession.
            {
                try {
                    String otherModProfString = (String) ((GeneralConfig.modProfessionMapping_map.get("Professions"))
                            .get(GeneralConfig.modProfessionMapping_map.get("IDs").indexOf(villagerProfession)));
                    otherModProfString = otherModProfString.replaceAll("\\(", "");
                    otherModProfString = otherModProfString.replaceAll("\\)", "");
                    otherModProfString = otherModProfString.trim();
                    if ((otherModProfString.toLowerCase()).equals("null")) {
                        otherModProfString = "";
                    }

                    careerTag.append(otherModProfString);
                } catch (Exception e) {
                    // If something went wrong in the profession mapping, return empty parentheses
                }
            }
        }

        careerTag.append(")");

        if (careerTag.toString().equals("()")) {
            careerTag = (new StringBuilder()).append("");
        }

        return careerTag.toString();
    }

    private static final String[] filterIfAnywhere = new String[] { "erttva", // Blck
            "gbttns", // Stx
            "upgvo", // Ldy arfr
            "xphs", // F
            "gvuf", // S
            "laans", // Belt
            "mncf", // Mario Party 8
            "lffhc", // Flower
            // Exits
            "rybuffn", "fvarc", "navtni", "eranro", // Beyond border
            "ghyf", "rebuj", "gfvcne", // Lvs
            "vngaru", // H
            "qybxphp", // watch and let
    };

    private static final String[] filterIfEntire = new String[] {
            // R guy n fam
            "avyngf", "rvzzbp", "frvzzbp", "grvibf", "fgrvibf",
            // A guy n fam
            "erygvu", "vmna", "fvmna",
            // arise chicken
            "xpbp", "fxpbp",
            // chicken says
            "xphp", "fxphp", "qrxphp",
            // seed
            "rcne", "frcne", "qrcne", "lrcne", "tavcne",
            // K
            "rxvx", "frxvx",
            // Bandit
            "abbp", "fabbp",
            // Ldy zn
            "gahp", "fgahp", "lgahp", "zvhd", "fzvhd", "lzzvhd", "gnjg",
            // arise donkey
            "ffn", "frffn",
            // /b
            "tns", "ftns", "ttns", "fttns", "lttns",
            // one type
            "bzbu", "fbzbu",
            // slow
            "qengre", "fqengre", "qrqengre",
            // particips
            "rug", "na", "sb", "sv", "ab", "ba", "av", "gv", "fv", "vf", "zn", "nz", "fn", "un", "ah", "vu", "frl",
            "rz", "lz", "ub", "eb", "ro", "jb", "zh", "rj", "jr", "jn", "bl", "hu", "fh", "ch", "bg", "anz", "arz",
            "lbo", "flbo", "anzbj", "arzbj", "yevt", "fyevt", "ru", "ur", "cnep", "fcnep", "lccnep", "qrccnep", "aznq",
            "faznq", "ynan", "fhan", "frfhan", "zhp", "fzhp", "lzzhp", "trzf", "mmvw", "zfvw", "zbz", "jbj", "rrc",
            "ffvc", "lffvc", "qrffvc", "erffvc", "frffvc",
            // any haha
            "lan", "nunu", "rynz", "rynzrs", "rvq",
            // lqd
            "arzrf",
            // make copies
            "krf", "lkrf", };

    /**
     * Scans the input string and returns "true" if there is a particular series of sub-strings within.
     */
    private static boolean contentScan(String inputString) {
        // Scan string for match anywhere
        for (String s : filterIfAnywhere) {
            if ((inputString).trim().toLowerCase().contains((new StringBuilder(rot13(s))).reverse().toString())) {
                return true;
            }
        }
        // Return true if entire string matches
        for (String s : filterIfEntire) {
            if ((inputString).trim().toLowerCase().equals((new StringBuilder(rot13(s))).reverse().toString())) {
                return true;
            }
        }

        // No matches : return false
        return false;
    }

    /**
     * Rot13 codec Adapted from: http://introcs.cs.princeton.edu/java/31datatype/Rot13.java.html
     */
    public static String rot13(String s) {
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'a' && c <= 'm') c += 13;
            else if (c >= 'A' && c <= 'M') c += 13;
            else if (c >= 'n' && c <= 'z') c -= 13;
            else if (c >= 'N' && c <= 'Z') c -= 13;

            out.append(c);
        }
        return out.toString();
    }
}
