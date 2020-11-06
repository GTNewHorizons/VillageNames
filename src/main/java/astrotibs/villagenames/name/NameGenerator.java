package astrotibs.villagenames.name;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
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

//The whole point of this thing is to be a separate class that generates the names.

public class NameGenerator {
	/*
	// The constructor
	public NameGenerator() {
		// Not called. YOLO
	}
	*/
	//static Random random = new Random();
	
	/**
	 * Enter in a name type to generate (e.g. "village"), and this will return a string list containing:
	 * [0] a header tag (for village colors; unused so far)
	 * [1] prefix
	 * [2] root name -- this is the CORE NAME of interest
	 * [3] suffix
	 */
	public static String[] newRandomName(String nameType, Random random)
	{
		// Unpack nameType into multiple possible name pools
		
		// Split input string by hyphen
		String[] nameType_raw = nameType.trim().split("\\s*-\\s*"); // Using regular expression \s* to optional remove leading and tailing spaces
		
		// Cast all elements as lowercase for easier comparison
		String[] nameType_a = new String[nameType_raw.length];
		for (int input_i=0; input_i < nameType_raw.length; input_i++) {nameType_a[input_i] = nameType_raw[input_i].toLowerCase().trim();}
		
		// Step 0: initialize empty syllable pools, into which will be added specific source pools
		String[] prefix = new String[]{};
		String[] root_initial = new String[]{};
		String[] root_sylBegin = new String[]{};
		String[] root_terminal = new String[]{};
		String[] suffix = new String[]{};
		
		float prefix_chance = 0F;
		float suffix_chance = 0F;
		int numnames;
		int normalization = 0;
		ArrayList<Integer> pooled_length_weights = new ArrayList<Integer>();
		
		
	    // Load in syllable pieces
		
		if ( Arrays.asList(nameType_a).contains("village") )
		{
			prefix =        ArrayUtils.addAll(prefix, VillageConfigHandler.village_prefix);
			root_initial =  ArrayUtils.addAll(root_initial, VillageConfigHandler.village_root_initial);
			root_sylBegin = ArrayUtils.addAll(root_sylBegin, VillageConfigHandler.village_root_syllables);
			root_terminal = ArrayUtils.addAll(root_terminal, VillageConfigHandler.village_root_terminal);
			suffix =        ArrayUtils.addAll(suffix, VillageConfigHandler.village_suffix);
			
			numnames = NamePieces.village_root_initial_default.length();
			prefix_chance += (VillageConfigHandler.prefix_chance * numnames);
			suffix_chance += (VillageConfigHandler.suffix_chance * numnames);
			normalization += numnames;
			
			for (int i=0; i<VillageConfigHandler.syllable_count_weighting.length; i++)
			{
				if (pooled_length_weights.size() > i) // Add weights of this name length for this syllable pool to the master weight array
				{pooled_length_weights.set(i, pooled_length_weights.get(i)+VillageConfigHandler.syllable_count_weighting[i]);}
				else // Weights of this name length don't exist in the master array. Expand the array.
				{pooled_length_weights.add(VillageConfigHandler.syllable_count_weighting[i]);}
			}
		}
		if ( Arrays.asList(nameType_a).contains("temple") )
		{
			prefix =        ArrayUtils.addAll(prefix, TempleConfigHandler.temple_prefix);
			root_initial =  ArrayUtils.addAll(root_initial, TempleConfigHandler.temple_root_initial);
			root_sylBegin = ArrayUtils.addAll(root_sylBegin, TempleConfigHandler.temple_root_syllables);
			root_terminal = ArrayUtils.addAll(root_terminal, TempleConfigHandler.temple_root_terminal);
			suffix =        ArrayUtils.addAll(suffix, TempleConfigHandler.temple_suffix);
			
			numnames = NamePieces.temple_root_initial_default.length();
			prefix_chance += (TempleConfigHandler.prefix_chance * numnames);
			suffix_chance += (TempleConfigHandler.suffix_chance * numnames);
			normalization += numnames;
			
			for (int i=0; i<TempleConfigHandler.syllable_count_weighting.length; i++)
			{
				if (pooled_length_weights.size() > i) // Add weights of this name length for this syllable pool to the master weight array
				{pooled_length_weights.set(i, pooled_length_weights.get(i)+TempleConfigHandler.syllable_count_weighting[i]);}
				else // Weights of this name length don't exist in the master array. Expand the array.
				{pooled_length_weights.add(TempleConfigHandler.syllable_count_weighting[i]);}
			}
		}
		if ( Arrays.asList(nameType_a).contains("mineshaft") )
		{
			prefix =        ArrayUtils.addAll(prefix, MineshaftConfigHandler.mineshaft_prefix);
			root_initial =  ArrayUtils.addAll(root_initial, MineshaftConfigHandler.mineshaft_root_initial);
			root_sylBegin = ArrayUtils.addAll(root_sylBegin, MineshaftConfigHandler.mineshaft_root_syllables);
			root_terminal = ArrayUtils.addAll(root_terminal, MineshaftConfigHandler.mineshaft_root_terminal);
			suffix =        ArrayUtils.addAll(suffix, MineshaftConfigHandler.mineshaft_suffix);
			
			numnames = NamePieces.mineshaft_root_initial_default.length();
			prefix_chance += (MineshaftConfigHandler.prefix_chance * numnames);
			suffix_chance += (MineshaftConfigHandler.suffix_chance * numnames);
			normalization += numnames;
			
			for (int i=0; i<MineshaftConfigHandler.syllable_count_weighting.length; i++)
			{
				if (pooled_length_weights.size() > i) // Add weights of this name length for this syllable pool to the master weight array
				{pooled_length_weights.set(i, pooled_length_weights.get(i)+MineshaftConfigHandler.syllable_count_weighting[i]);}
				else // Weights of this name length don't exist in the master array. Expand the array.
				{pooled_length_weights.add(MineshaftConfigHandler.syllable_count_weighting[i]);}
			}
		}
		if ( Arrays.asList(nameType_a).contains("fortress") )
		{
			prefix =        ArrayUtils.addAll(prefix, FortressConfigHandler.fortress_prefix);
			root_initial =  ArrayUtils.addAll(root_initial, FortressConfigHandler.fortress_root_initial);
			root_sylBegin = ArrayUtils.addAll(root_sylBegin, FortressConfigHandler.fortress_root_syllables);
			root_terminal = ArrayUtils.addAll(root_terminal, FortressConfigHandler.fortress_root_terminal);
			suffix =        ArrayUtils.addAll(suffix, FortressConfigHandler.fortress_suffix);
			
			numnames = NamePieces.fortress_root_initial_default.length();
			prefix_chance += (FortressConfigHandler.prefix_chance * numnames);
			suffix_chance += (FortressConfigHandler.suffix_chance * numnames);
			normalization += numnames;
			
			for (int i=0; i<FortressConfigHandler.syllable_count_weighting.length; i++)
			{
				if (pooled_length_weights.size() > i) // Add weights of this name length for this syllable pool to the master weight array
				{pooled_length_weights.set(i, pooled_length_weights.get(i)+FortressConfigHandler.syllable_count_weighting[i]);}
				else // Weights of this name length don't exist in the master array. Expand the array.
				{pooled_length_weights.add(FortressConfigHandler.syllable_count_weighting[i]);}
			}
		}
		if ( Arrays.asList(nameType_a).contains("stronghold") )
		{
			prefix =        ArrayUtils.addAll(prefix, StrongholdConfigHandler.stronghold_prefix);
			root_initial =  ArrayUtils.addAll(root_initial, StrongholdConfigHandler.stronghold_root_initial);
			root_sylBegin = ArrayUtils.addAll(root_sylBegin, StrongholdConfigHandler.stronghold_root_syllables);
			root_terminal = ArrayUtils.addAll(root_terminal, StrongholdConfigHandler.stronghold_root_terminal);
			suffix =        ArrayUtils.addAll(suffix, StrongholdConfigHandler.stronghold_suffix);
			
			numnames = NamePieces.stronghold_root_initial_default.length();
			prefix_chance += (StrongholdConfigHandler.prefix_chance * numnames);
			suffix_chance += (StrongholdConfigHandler.suffix_chance * numnames);
			normalization += numnames;
			
			for (int i=0; i<StrongholdConfigHandler.syllable_count_weighting.length; i++)
			{
				if (pooled_length_weights.size() > i) // Add weights of this name length for this syllable pool to the master weight array
				{pooled_length_weights.set(i, pooled_length_weights.get(i)+StrongholdConfigHandler.syllable_count_weighting[i]);}
				else // Weights of this name length don't exist in the master array. Expand the array.
				{pooled_length_weights.add(StrongholdConfigHandler.syllable_count_weighting[i]);}
			}
		}
		if ( Arrays.asList(nameType_a).contains("monument") )
		{
			prefix =        ArrayUtils.addAll(prefix, MonumentConfigHandler.monument_prefix);
			root_initial =  ArrayUtils.addAll(root_initial, MonumentConfigHandler.monument_root_initial);
			root_sylBegin = ArrayUtils.addAll(root_sylBegin, MonumentConfigHandler.monument_root_syllables);
			root_terminal = ArrayUtils.addAll(root_terminal, MonumentConfigHandler.monument_root_terminal);
			suffix =        ArrayUtils.addAll(suffix, MonumentConfigHandler.monument_suffix);
			
			numnames = NamePieces.monument_root_initial_default.length();
			prefix_chance += (MonumentConfigHandler.prefix_chance * numnames);
			suffix_chance += (MonumentConfigHandler.suffix_chance * numnames);
			normalization += numnames;
			
			for (int i=0; i<MonumentConfigHandler.syllable_count_weighting.length; i++)
			{
				if (pooled_length_weights.size() > i) // Add weights of this name length for this syllable pool to the master weight array
				{pooled_length_weights.set(i, pooled_length_weights.get(i)+MonumentConfigHandler.syllable_count_weighting[i]);}
				else // Weights of this name length don't exist in the master array. Expand the array.
				{pooled_length_weights.add(MonumentConfigHandler.syllable_count_weighting[i]);}
			}
		}
		if ( Arrays.asList(nameType_a).contains("endcity") )
		{
			prefix =        ArrayUtils.addAll(prefix, EndCityConfigHandler.endcity_prefix);
			root_initial =  ArrayUtils.addAll(root_initial, EndCityConfigHandler.endcity_root_initial);
			root_sylBegin = ArrayUtils.addAll(root_sylBegin, EndCityConfigHandler.endcity_root_syllables);
			root_terminal = ArrayUtils.addAll(root_terminal, EndCityConfigHandler.endcity_root_terminal);
			suffix =        ArrayUtils.addAll(suffix, EndCityConfigHandler.endcity_suffix);
			
			numnames = NamePieces.endcity_root_initial_default.length();
			prefix_chance += (EndCityConfigHandler.prefix_chance * numnames);
			suffix_chance += (EndCityConfigHandler.suffix_chance * numnames);
			normalization += numnames;
			
			for (int i=0; i<EndCityConfigHandler.syllable_count_weighting.length; i++)
			{
				if (pooled_length_weights.size() > i) // Add weights of this name length for this syllable pool to the master weight array
				{pooled_length_weights.set(i, pooled_length_weights.get(i)+EndCityConfigHandler.syllable_count_weighting[i]);}
				else // Weights of this name length don't exist in the master array. Expand the array.
				{pooled_length_weights.add(EndCityConfigHandler.syllable_count_weighting[i]);}
			}
		}
		if ( Arrays.asList(nameType_a).contains("mansion") )
		{
			prefix =        ArrayUtils.addAll(prefix, MansionConfigHandler.mansion_prefix);
			root_initial =  ArrayUtils.addAll(root_initial, MansionConfigHandler.mansion_root_initial);
			root_sylBegin = ArrayUtils.addAll(root_sylBegin, MansionConfigHandler.mansion_root_syllables);
			root_terminal = ArrayUtils.addAll(root_terminal, MansionConfigHandler.mansion_root_terminal);
			suffix =        ArrayUtils.addAll(suffix, MansionConfigHandler.mansion_suffix);
			
			numnames = NamePieces.mansion_root_initial_default.length();
			prefix_chance += (MansionConfigHandler.prefix_chance * numnames);
			suffix_chance += (MansionConfigHandler.suffix_chance * numnames);
			normalization += numnames;
			
			for (int i=0; i<MansionConfigHandler.syllable_count_weighting.length; i++)
			{
				if (pooled_length_weights.size() > i) // Add weights of this name length for this syllable pool to the master weight array
				{pooled_length_weights.set(i, pooled_length_weights.get(i)+MansionConfigHandler.syllable_count_weighting[i]);}
				else // Weights of this name length don't exist in the master array. Expand the array.
				{pooled_length_weights.add(MansionConfigHandler.syllable_count_weighting[i]);}
			}
		}
		if ( Arrays.asList(nameType_a).contains("alien") )
		{
			prefix =        ArrayUtils.addAll(prefix, AlienConfigHandler.alien_prefix);
			root_initial =  ArrayUtils.addAll(root_initial, AlienConfigHandler.alien_root_initial);
			root_sylBegin = ArrayUtils.addAll(root_sylBegin, AlienConfigHandler.alien_root_syllables);
			root_terminal = ArrayUtils.addAll(root_terminal, AlienConfigHandler.alien_root_terminal);
			suffix =        ArrayUtils.addAll(suffix, AlienConfigHandler.alien_suffix);
			
			numnames = NamePiecesEntities.alien_root_initial_default.length();
			prefix_chance += (AlienConfigHandler.prefix_chance * numnames);
			suffix_chance += (AlienConfigHandler.suffix_chance * numnames);
			normalization += numnames;
			
			for (int i=0; i<AlienConfigHandler.syllable_count_weighting.length; i++)
			{
				if (pooled_length_weights.size() > i) // Add weights of this name length for this syllable pool to the master weight array
				{pooled_length_weights.set(i, pooled_length_weights.get(i)+AlienConfigHandler.syllable_count_weighting[i]);}
				else // Weights of this name length don't exist in the master array. Expand the array.
				{pooled_length_weights.add(AlienConfigHandler.syllable_count_weighting[i]);}
			}
		}
		if ( Arrays.asList(nameType_a).contains("alienvillage") )
		{
			prefix =        ArrayUtils.addAll(prefix, AlienVillageConfigHandler.alienvillage_prefix);
			root_initial =  ArrayUtils.addAll(root_initial, AlienVillageConfigHandler.alienvillage_root_initial);
			root_sylBegin = ArrayUtils.addAll(root_sylBegin, AlienVillageConfigHandler.alienvillage_root_syllables);
			root_terminal = ArrayUtils.addAll(root_terminal, AlienVillageConfigHandler.alienvillage_root_terminal);
			suffix =        ArrayUtils.addAll(suffix, AlienVillageConfigHandler.alienvillage_suffix);
			
			numnames = NamePieces.alienvillage_root_initial_default.length();
			prefix_chance += (AlienVillageConfigHandler.prefix_chance * numnames);
			suffix_chance += (AlienVillageConfigHandler.suffix_chance * numnames);
			normalization += numnames;
			
			for (int i=0; i<AlienVillageConfigHandler.syllable_count_weighting.length; i++)
			{
				if (pooled_length_weights.size() > i) // Add weights of this name length for this syllable pool to the master weight array
				{pooled_length_weights.set(i, pooled_length_weights.get(i)+AlienVillageConfigHandler.syllable_count_weighting[i]);}
				else // Weights of this name length don't exist in the master array. Expand the array.
				{pooled_length_weights.add(AlienVillageConfigHandler.syllable_count_weighting[i]);}
			}
		}
		if ( Arrays.asList(nameType_a).contains("goblin") )
		{
			prefix =        ArrayUtils.addAll(prefix, GoblinConfigHandler.goblin_prefix);
			root_initial =  ArrayUtils.addAll(root_initial, GoblinConfigHandler.goblin_root_initial);
			root_sylBegin = ArrayUtils.addAll(root_sylBegin, GoblinConfigHandler.goblin_root_syllables);
			root_terminal = ArrayUtils.addAll(root_terminal, GoblinConfigHandler.goblin_root_terminal);
			suffix =        ArrayUtils.addAll(suffix, GoblinConfigHandler.goblin_suffix);
			
			numnames = NamePiecesEntities.goblin_root_initial_default.length();
			prefix_chance += (GoblinConfigHandler.prefix_chance * numnames);
			suffix_chance += (GoblinConfigHandler.suffix_chance * numnames);
			normalization += numnames;
			
			for (int i=0; i<GoblinConfigHandler.syllable_count_weighting.length; i++)
			{
				if (pooled_length_weights.size() > i) // Add weights of this name length for this syllable pool to the master weight array
				{pooled_length_weights.set(i, pooled_length_weights.get(i)+GoblinConfigHandler.syllable_count_weighting[i]);}
				else // Weights of this name length don't exist in the master array. Expand the array.
				{pooled_length_weights.add(GoblinConfigHandler.syllable_count_weighting[i]);}
			}
		}
		if ( Arrays.asList(nameType_a).contains("golem") )
		{
			prefix =        ArrayUtils.addAll(prefix, GolemConfigHandler.golem_prefix);
			root_initial =  ArrayUtils.addAll(root_initial, GolemConfigHandler.golem_root_initial);
			root_sylBegin = ArrayUtils.addAll(root_sylBegin, GolemConfigHandler.golem_root_syllables);
			root_terminal = ArrayUtils.addAll(root_terminal, GolemConfigHandler.golem_root_terminal);
			suffix =        ArrayUtils.addAll(suffix, GolemConfigHandler.golem_suffix);
			
			numnames = NamePiecesEntities.golem_root_initial_default.length();
			prefix_chance += (GolemConfigHandler.prefix_chance * numnames);
			suffix_chance += (GolemConfigHandler.suffix_chance * numnames);
			normalization += numnames;
			
			for (int i=0; i<GolemConfigHandler.syllable_count_weighting.length; i++)
			{
				if (pooled_length_weights.size() > i) // Add weights of this name length for this syllable pool to the master weight array
				{pooled_length_weights.set(i, pooled_length_weights.get(i)+GolemConfigHandler.syllable_count_weighting[i]);}
				else // Weights of this name length don't exist in the master array. Expand the array.
				{pooled_length_weights.add(GolemConfigHandler.syllable_count_weighting[i]);}
			}
		}
		if ( Arrays.asList(nameType_a).contains("demon") )
		{
			prefix =        ArrayUtils.addAll(prefix, DemonConfigHandler.demon_prefix);
			root_initial =  ArrayUtils.addAll(root_initial, DemonConfigHandler.demon_root_initial);
			root_sylBegin = ArrayUtils.addAll(root_sylBegin, DemonConfigHandler.demon_root_syllables);
			root_terminal = ArrayUtils.addAll(root_terminal, DemonConfigHandler.demon_root_terminal);
			suffix =        ArrayUtils.addAll(suffix, DemonConfigHandler.demon_suffix);
			
			numnames = NamePiecesEntities.demon_root_initial_default.length();
			prefix_chance += (DemonConfigHandler.prefix_chance * numnames);
			suffix_chance += (DemonConfigHandler.suffix_chance * numnames);
			normalization += numnames;
			
			for (int i=0; i<DemonConfigHandler.syllable_count_weighting.length; i++)
			{
				if (pooled_length_weights.size() > i) // Add weights of this name length for this syllable pool to the master weight array
				{pooled_length_weights.set(i, pooled_length_weights.get(i)+DemonConfigHandler.syllable_count_weighting[i]);}
				else // Weights of this name length don't exist in the master array. Expand the array.
				{pooled_length_weights.add(DemonConfigHandler.syllable_count_weighting[i]);}
			}
		}
		if ( Arrays.asList(nameType_a).contains("angel") )
		{
			prefix =        ArrayUtils.addAll(prefix, AngelConfigHandler.angel_prefix);
			root_initial =  ArrayUtils.addAll(root_initial, AngelConfigHandler.angel_root_initial);
			root_sylBegin = ArrayUtils.addAll(root_sylBegin, AngelConfigHandler.angel_root_syllables);
			root_terminal = ArrayUtils.addAll(root_terminal, AngelConfigHandler.angel_root_terminal);
			suffix =        ArrayUtils.addAll(suffix, AngelConfigHandler.angel_suffix);
			
			numnames = NamePiecesEntities.angel_root_initial_default.length();
			prefix_chance += (AngelConfigHandler.prefix_chance * numnames);
			suffix_chance += (AngelConfigHandler.suffix_chance * numnames);
			normalization += numnames;
			
			for (int i=0; i<AngelConfigHandler.syllable_count_weighting.length; i++)
			{
				if (pooled_length_weights.size() > i) // Add weights of this name length for this syllable pool to the master weight array
				{pooled_length_weights.set(i, pooled_length_weights.get(i)+AngelConfigHandler.syllable_count_weighting[i]);}
				else // Weights of this name length don't exist in the master array. Expand the array.
				{pooled_length_weights.add(AngelConfigHandler.syllable_count_weighting[i]);}
			}
		}
		if ( Arrays.asList(nameType_a).contains("dragon") )
		{
			prefix =        ArrayUtils.addAll(prefix, DragonConfigHandler.dragon_prefix);
			root_initial =  ArrayUtils.addAll(root_initial, DragonConfigHandler.dragon_root_initial);
			root_sylBegin = ArrayUtils.addAll(root_sylBegin, DragonConfigHandler.dragon_root_syllables);
			root_terminal = ArrayUtils.addAll(root_terminal, DragonConfigHandler.dragon_root_terminal);
			suffix =        ArrayUtils.addAll(suffix, DragonConfigHandler.dragon_suffix);
			
			numnames = NamePiecesEntities.dragon_root_initial_default.length();
			prefix_chance += (DragonConfigHandler.prefix_chance * numnames);
			suffix_chance += (DragonConfigHandler.suffix_chance * numnames);
			normalization += numnames;
			
			for (int i=0; i<DragonConfigHandler.syllable_count_weighting.length; i++)
			{
				if (pooled_length_weights.size() > i) // Add weights of this name length for this syllable pool to the master weight array
				{pooled_length_weights.set(i, pooled_length_weights.get(i)+DragonConfigHandler.syllable_count_weighting[i]);}
				else // Weights of this name length don't exist in the master array. Expand the array.
				{pooled_length_weights.add(DragonConfigHandler.syllable_count_weighting[i]);}
			}
		}
		if ( Arrays.asList(nameType_a).contains("pet") )
		{
			prefix =        ArrayUtils.addAll(prefix, PetConfigHandler.pet_prefix);
			root_initial =  ArrayUtils.addAll(root_initial, PetConfigHandler.pet_root_initial);
			root_sylBegin = ArrayUtils.addAll(root_sylBegin, PetConfigHandler.pet_root_syllables);
			root_terminal = ArrayUtils.addAll(root_terminal, PetConfigHandler.pet_root_terminal);
			suffix =        ArrayUtils.addAll(suffix, PetConfigHandler.pet_suffix);
			
			numnames = NamePiecesEntities.pet_root_initial_default.length();
			prefix_chance += (PetConfigHandler.prefix_chance * numnames);
			suffix_chance += (PetConfigHandler.suffix_chance * numnames);
			normalization += numnames;
			
			for (int i=0; i<PetConfigHandler.syllable_count_weighting.length; i++)
			{
				if (pooled_length_weights.size() > i) // Add weights of this name length for this syllable pool to the master weight array
				{pooled_length_weights.set(i, pooled_length_weights.get(i)+PetConfigHandler.syllable_count_weighting[i]);}
				else // Weights of this name length don't exist in the master array. Expand the array.
				{pooled_length_weights.add(PetConfigHandler.syllable_count_weighting[i]);}
			}
		}
		if ( Arrays.asList(nameType_a).contains("custom") )
		{
			prefix =        ArrayUtils.addAll(prefix, CustomConfigHandler.custom_prefix);
			root_initial =  ArrayUtils.addAll(root_initial, CustomConfigHandler.custom_root_initial);
			root_sylBegin = ArrayUtils.addAll(root_sylBegin, CustomConfigHandler.custom_root_syllables);
			root_terminal = ArrayUtils.addAll(root_terminal, CustomConfigHandler.custom_root_terminal);
			suffix =        ArrayUtils.addAll(suffix, CustomConfigHandler.custom_suffix);
			
			numnames = NamePieces.custom_root_initial_default.length();
			prefix_chance += (CustomConfigHandler.prefix_chance * numnames);
			suffix_chance += (CustomConfigHandler.suffix_chance * numnames);
			normalization += numnames;
			
			for (int i=0; i<CustomConfigHandler.syllable_count_weighting.length; i++)
			{
				if (pooled_length_weights.size() > i) // Add weights of this name length for this syllable pool to the master weight array
				{pooled_length_weights.set(i, pooled_length_weights.get(i)+CustomConfigHandler.syllable_count_weighting[i]);}
				else // Weights of this name length don't exist in the master array. Expand the array.
				{pooled_length_weights.add(CustomConfigHandler.syllable_count_weighting[i]);}
			}
		}
		// It's possible the player made a mistake and no name pieces were correctly entered. If so, default to "villager"
		if ( 
				Arrays.asList(nameType_a).contains("villager") // OR, the user deliberately chose "villager"
				|| root_initial.length <= 0  // No previous entries were chosen
				)
		{
			if (!Arrays.asList(nameType_a).contains("villager") && (root_initial.length <= 0))
			{if (GeneralConfig.debugMessages) LogHelper.error("Submitted nameType contained no valid entries! Defaulting to Villager name pool.");}
			
			prefix =        ArrayUtils.addAll(prefix, VillagerConfigHandler.villager_prefix);
			root_initial =  ArrayUtils.addAll(root_initial, VillagerConfigHandler.villager_root_initial);
			root_sylBegin = ArrayUtils.addAll(root_sylBegin, VillagerConfigHandler.villager_root_syllables);
			root_terminal = ArrayUtils.addAll(root_terminal, VillagerConfigHandler.villager_root_terminal);
			suffix =        ArrayUtils.addAll(suffix, VillagerConfigHandler.villager_suffix);
			
			numnames = NamePiecesEntities.villager_root_initial_default.length();
			prefix_chance += (VillagerConfigHandler.prefix_chance * numnames);
			suffix_chance += (VillagerConfigHandler.suffix_chance * numnames);
			normalization += numnames;
			
			for (int i=0; i<VillagerConfigHandler.syllable_count_weighting.length; i++)
			{
				if (pooled_length_weights.size() > i) // Add weights of this name length for this syllable pool to the master weight array
				{pooled_length_weights.set(i, pooled_length_weights.get(i)+VillagerConfigHandler.syllable_count_weighting[i]);}
				else // Weights of this name length don't exist in the master array. Expand the array.
				{pooled_length_weights.add(VillagerConfigHandler.syllable_count_weighting[i]);}
			}
		}
		// Normalize prefix/suffix probabilities based on the pools provided
		if (normalization > 0)
		{
			prefix_chance /= normalization;
			suffix_chance /= normalization;
		}
		
		// The three pieces of interest
		String r_prefix = "";
		String r_suffix = "";
		String rootName = "";
		
		int rootname_syllable_inserts = 1; // How many syllables are inserted between the name's starting and ending half-syllables
		
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
		
		while (true)
		{
			// Step 1: Generate a prefix.
			r_prefix="";
			if (
					random.nextFloat() < prefix_chance
					&& normalization > 0
					&& prefix.length > 0
					)
			{
				r_prefix = (prefix[random.nextInt(prefix.length)]).trim();
			}
			
			// Step 3: Generate a suffix.
			r_suffix="";
			if (
					random.nextFloat() < suffix_chance
					&& normalization > 0
					&& suffix.length > 0
					)
			{
				r_suffix = (suffix[random.nextInt(suffix.length)]).trim();
			}
			
			if (
					r_prefix.equals("")
					|| r_suffix.equals("")
					|| !r_prefix.equals(r_suffix)
					)
			{break;}
			else
			{
				if (++prefixsuffixFail>=tooManyFailures)
				{
					String errorMessage = "Name type " + nameType +" Matched too many prefixes and suffixes! Check your syllable configs.";
					LogHelper.fatal(errorMessage);
					throw new RuntimeException(errorMessage);
				}
			}
		}
		
		
		// Step 2: Generate a proper (root) name.
		
		// The while loop continues until a valid name is generated or an exception is thrown
		while (true)
		{
			// Determine how long this name will be
			
    		// Compute the total weight of all items together
    		int totalWeight = 0;
    		
    		for (int i=0; i<pooled_length_weights.size(); i++) {totalWeight += pooled_length_weights.get(i);}
    		
    		if (totalWeight <= 0)
			{
				String errorMessage = "Name type " + nameType +" total syllable weighting was non-positive! Check the weighting values in your configs.";
				LogHelper.fatal(errorMessage);
				throw new RuntimeException(errorMessage);
			}
    		
    		// Now choose a random index
    		int randomObject = random.nextInt(totalWeight);
    		for (int i = 0; i < pooled_length_weights.size(); ++i)
    		{
    			randomObject -= pooled_length_weights.get(i);
    		    if (randomObject <= 0)
    		    {
    		    	rootname_syllable_inserts = i; // We've determined the length of the name.
    		        break;
    		    }
    		}
    		
    		// Step 2.1: Generate the beginning sound of the name
			if (root_initial.length <= 0)
			{
				String errorMessage = "Name type " + nameType + " has no root-initial entries! No name can be constructed!";
				LogHelper.fatal(errorMessage);
				throw new RuntimeException(errorMessage);
			}
			else
			{
				rootName = root_initial[random.nextInt(root_initial.length)];
			}
			
			// Step 2.2: Generate some number of inserted syllables
			for (int i=0; i<rootname_syllable_inserts; i++)
			{
				if (root_sylBegin.length <= 0)
				{
					String errorMessage = "Name type " + nameType + " has no root-syllable entries! You need at least one, even if it's the blank entry character: ~";
					LogHelper.error(errorMessage);
					rootName += "~";
				}
				else
				{
					rootName += root_sylBegin[random.nextInt(root_sylBegin.length)];
				}
			}
			
			// Step 2.3: Generate the ending sound of the name, if any
			if (root_terminal.length <= 0)
			{
				String errorMessage = "Name type " + nameType + " has no root-terminal entries! You need at least one, even if it's the blank entry character: ~";
				LogHelper.error(errorMessage);
				rootName += "~";
			}
			else
			{
				rootName += root_terminal[random.nextInt(root_terminal.length)];
			}
			
			
			// Step 2V: clean up for validation
			
			rootName = rootName.trim();
						
			// Remove any spaces
			rootName = rootName.replace(" ", "");
			// Replace underscores here with INTENTIONAL spaces.
			rootName = rootName.replaceAll("\\_", " ");
			// Replace carots with nothing, because they were used as blank placeholders
			rootName = rootName.replace("^", "");
			
			// I have to reject this (root) name if it's not within the allotted size threshold.
			// Also I should ensure the last three characters are not all the same.
			
			if ( rootName.length() <= 15 )
			{
				if ( rootName.length() >= 3 )
				{
					// Now, make sure the same characters don't appear in the name three times in a row
					char[] nameRootArray = rootName.toLowerCase().toCharArray();
					int consecutives = 0;
					for(int ci = 0; ci < nameRootArray.length-2; ci++)
					{
						if (nameRootArray[ci] == nameRootArray[ci+1] && nameRootArray[ci] == nameRootArray[ci+2])
						{
							consecutives++; 
						}
					}
					if (consecutives == 0)
					{
						// Do a content scan
						if ( !contentScan(rootName) )
						{
							// Passes all the checks! Accept the name!
							break;
						}
						// Something caught the attention of the filter 
						filterFail++;
					}
					else
					{
						repeatedChar++; // Detected three of the same letter in a row.
					}
					
				}
				// Now ensure that a two-letter name isn't the same letter twice.
				else if (rootName.length() == 2)
				{
					if ( rootName.toLowerCase().charAt(0) != rootName.toLowerCase().charAt(1) )
					{
						// Do a content scan
						if ( !contentScan(rootName) )
						{
							// Passes all the checks! Accept the name!
							break;
						}
						// Something caught the attention of the filter 
						filterFail++;
					}
				}
				else if (rootName.length() > 0)
				{
					sizeUnderflow++; // Root name is too short.
				}
				else blankRoot++; // Root name is blank.
			}
			else
			{ // Root name is too long.
				sizeOverflow++;
			}
			
			// Step 2X: The Graveyard
			// If we counted too many invalid name attempts, throw an exception
			if (sizeOverflow>=tooManyFailures)
			{
				String errorMessage = "Name type " + nameType +" names are too long! Check your syllable lengths.";
				LogHelper.fatal(errorMessage);
				throw new RuntimeException(errorMessage);
				//r_prefix = rootName = r_suffix = "";
				//break;
			}
			if (sizeUnderflow>=tooManyFailures)
			{
				String errorMessage = "Name type " + nameType +" names are too short! Check your syllables configs.";
				LogHelper.fatal(errorMessage);
				throw new RuntimeException(errorMessage);
				//r_prefix = rootName = r_suffix = "";
				//break;
			}
			if (blankRoot>=tooManyFailures)
			{
				String errorMessage = "Name type " + nameType +" Produced blank names! Check your syllable configs.";
				LogHelper.fatal(errorMessage);
				throw new RuntimeException(errorMessage);
				//r_prefix = rootName = r_suffix = "";
				//break;
			}
			if (repeatedChar>=tooManyFailures)
			{
				String errorMessage = "Name type " + nameType +" has too many consecutive repeated letters! Check your syllable configs.";
				LogHelper.fatal(errorMessage);
				throw new RuntimeException(errorMessage);
				//r_prefix = rootName = r_suffix = "";
				//break;
			}
			if (filterFail>=tooManyFailures)
			{
				String errorMessage = "Name type " + nameType +" has tripped the content filter too many times. Are you being naughty?";
				LogHelper.fatal(errorMessage);
				throw new RuntimeException(errorMessage);
				//r_prefix = rootName = r_suffix = "";
				//break;
			}
		}
		
		// Step 4: Grab a header tag.
		String headerTags = GeneralConfig.headerTags.trim(); // Just in case some idiot added spaces
		
		String[] nameStringArray = {headerTags, r_prefix, rootName, r_suffix};
		
		return nameStringArray;
	}
	
	
	/**
	 * Generate a profession tag to append to their name
	 * @param villagerProfession: integer to represent profession (0 to 5)
	 * @param villagerCareer: integer to represent career (0 before 1.8; 1+ otherwise)
	 * @param nitwitProfession: name to assign to a nitwit (profession 5)
	 * @return
	 */
	public static String getCareerTag(String entityClasspath, int villagerProfession, int villagerCareer) {
		
		// keys: "NameTypes", "Professions", "ClassPaths", "AddOrRemove"
		Map<String, ArrayList> mappedNamesAutomatic = GeneralConfig.unpackMappedNames(GeneralConfig.modNameMappingAutomatic);
		Map<String, ArrayList> mappedNamesClickable = GeneralConfig.unpackMappedNames(GeneralConfig.modNameMappingClickable);
		// keys: "Professions", "IDs", "VanillaProfMaps"
		Map<String, ArrayList> mappedProfessions = GeneralConfig.unpackMappedProfessions(GeneralConfig.modProfessionMapping);
		
		String careerTag = "(";
		
		// The entity is identified in the "clickable" or "automatic" config entry
		if ( mappedNamesClickable.get("ClassPaths").contains(entityClasspath) ) {
			careerTag += (String) ((mappedNamesClickable.get("Professions")).get( mappedNamesClickable.get("ClassPaths").indexOf(entityClasspath) ));
			careerTag = careerTag.trim();
		}
		else if ( mappedNamesAutomatic.get("ClassPaths").contains(entityClasspath) ) {
			careerTag += (String) ((mappedNamesAutomatic.get("Professions")).get( mappedNamesAutomatic.get("ClassPaths").indexOf(entityClasspath) ));
			careerTag = careerTag.trim();
		}
		
		// Handle More Planets's Nibiru Villager
		else if (entityClasspath.equals(ModObjects.MPNibiruVillagerClass) // 1.7 version
				|| entityClasspath.equals(ModObjects.MPNibiruVillagerClassModern) // 1.10 version
				) {
			switch (villagerProfession%3) {
			case 0: // Farmer profession
				careerTag += "Farmer";
				break;
			case 1: // Librarian profession
				careerTag += "Librarian";
				break;
			case 2: // Priest profession
				careerTag += "Medic";
				break;
			}
		}
		
		// Unfortunately, the I18n is client-side only, and this method is only called server-side.
		// I have to plug in the English versions for BOTH sides.
		
		else { // Ordinary vanilla-style villager, even it's using non-vanilla profession and career IDs
			
			switch (villagerProfession) {
			case 0: // Farmer profession
				switch(villagerCareer) {
				case 1:
					//try {careerTag += I18n.format("entity.Villager.farmer");}
					//catch (Exception e) {careerTag += "Farmer";}
					careerTag += "Farmer";
					break;
				case 2:
					//try {careerTag += I18n.format("entity.Villager.fisherman");}
					//catch (Exception e) {careerTag += "Fisherman";}
					careerTag += "Fisherman";
					break;
				case 3:
					//try {careerTag += I18n.format("entity.Villager.shepherd");}
					//catch (Exception e) {careerTag += "Shepherd";}
					careerTag += "Shepherd";
					break;
				case 4:
					//try {careerTag += I18n.format("entity.Villager.fletcher");}
					//catch (Exception e) {careerTag += "Fletcher";}
					careerTag += "Fletcher";
					break;
				default:
					//try {careerTag += I18n.format("entity.Villager.farmer");}
					//catch (Exception e) {careerTag += "Farmer";}
					careerTag += "Farmer";
					break;
				}
				break;
			case 1: // Librarian profession
				switch(villagerCareer) {
				case 1:
					//try {careerTag += I18n.format("entity.Villager.librarian");}
					//catch (Exception e) {careerTag += "Librarian";}
					careerTag += "Librarian";
					break;
				case 2:
					//try {careerTag += I18n.format("entity.Villager.cartographer");}
					//catch (Exception e) {careerTag += "Cartographer";}
					careerTag += "Cartographer";
					break;
				default:
					//try {careerTag += I18n.format("entity.Villager.librarian");}
					//catch (Exception e) {careerTag += "Librarian";}
					careerTag += "Librarian";
					break;
				}
				break;
			case 2: // Priest profession
				switch(villagerCareer) {
				case 1:
					//try {careerTag += I18n.format("entity.Villager.cleric");}
					//catch (Exception e) {careerTag += "Cleric";}
					careerTag += "Cleric";
					break;
				default:
					//try {careerTag += I18n.format("entity.Villager.priest");}
					//catch (Exception e) {careerTag += "Priest";}
					careerTag += "Priest";
					break;
				}
				break;
			case 3: // Blacksmith profession
				switch(villagerCareer) {
				case 1:
					//try {careerTag += I18n.format("entity.Villager.armor");}
					//catch (Exception e) {careerTag += "Armorer";}
					careerTag += "Armorer";
					break;
				case 2:
					//try {careerTag += I18n.format("entity.Villager.weapon");}
					//catch (Exception e) {careerTag += "Weapon Smith";}
					careerTag += "Weaponsmith"; // Changed in v3.1
					break;
				case 3:
					//try {careerTag += I18n.format("entity.Villager.tool");}
					//catch (Exception e) {careerTag += "Tool Smith";}
					careerTag += "Toolsmith"; // Changed in v3.1
					break;
				case 4:
					careerTag += "Mason"; // Added in v3.1
					break;
				default:
					//try {careerTag += I18n.format("entity.Villager.blacksmith");}
					//catch (Exception e) {careerTag += "Blacksmith";}
					careerTag += "Blacksmith";
					break;
				}
				break;
			case 4: // Butcher profession
				switch(villagerCareer) {
				case 1:
					//try {careerTag += I18n.format("entity.Villager.butcher");}
					//catch (Exception e) {careerTag += "Butcher";}
					careerTag += "Butcher";
					break;
				case 2:
					//try {careerTag += I18n.format("entity.Villager.leather");}
					//catch (Exception e) {careerTag += "Leatherworker";}
					careerTag += "Leatherworker";
					break;
				default:
					//try {careerTag += I18n.format("entity.Villager.butcher");}
					//catch (Exception e) {careerTag += "Butcher";}
					careerTag += "Butcher";
					break;
				}
				break;
			case 5: // Nitwit profession
				String nitwitCareer = (
						(GeneralConfig.nitwitProfession.trim()).equals("")
						|| (GeneralConfig.nitwitProfession.toLowerCase().trim()).equals("null")
						) ? "" :  GeneralConfig.nitwitProfession;
				switch(villagerCareer) {
				case 1:
					careerTag += nitwitCareer;
					break;
				default:
					careerTag += nitwitCareer;
					break;
				}
				break;
			}
			if (!(villagerProfession >= 0 && villagerProfession <= 5)) { // This is a modded profession.
				try {
					String otherModProfString = (String) ((mappedProfessions.get("Professions")).get( mappedProfessions.get("IDs").indexOf(villagerProfession) ));
					otherModProfString = otherModProfString.replaceAll("\\(", "");
					otherModProfString = otherModProfString.replaceAll("\\)", "");
					otherModProfString = otherModProfString.trim();
					if ((otherModProfString.toLowerCase()).equals("null")) {otherModProfString = "";}
					
					careerTag += otherModProfString;
					}
				catch (Exception e){
					//If something went wrong in the profession mapping, return empty parentheses
					}
			}
		}
		
		careerTag += ")";
		
		if (careerTag.equals("()")) careerTag = "";
		
		return careerTag;
	}
	
	
	private static final String[] filterIfAnywhere = new String[]
	{
		"erttva", // Blck
		"gbttns", // Stx
		"upgvo", // Ldy arfr
		"xphs", // F
		"gvuf", // S
		"laans", // Belt
		"mncf", // Mario Party 8
		"lffhc", // Flower
		// Exits
		"rybuffn", "fvarc", "navtni",
		"eranro", // Beyond border
		"ghyf", "rebuj", "gfvcne", // Lvs
		"vngaru", // H
		"qybxphp", // watch and let
	};
	
	private static final String[] filterIfEntire = new String[]
	{
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
		"rxvx",	"frxvx",
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
		"rz", "lz", "ub", "eb", "ro", "jb", "zh", "rj", "jr", "jn", "bl", "hu", "fh", "ch", "bg",
		"anz", "arz", "lbo", "flbo", "anzbj", "arzbj", "yevt", "fyevt", "ru", "ur",
		"cnep", "fcnep", "lccnep", "qrccnep",
		"aznq", "faznq", "ynan", "fhan", "frfhan",
		"zhp", "fzhp", "lzzhp", "trzf", "mmvw", "zfvw", "zbz", "jbj",
		"rrc",
		"ffvc", "lffvc", "qrffvc", "erffvc", "frffvc",
		// any haha
		"lan", "nunu",
		"rynz", "rynzrs",
		"rvq",
	};
	
	/**
	 * Scans the input string and returns "true" if there is a particular series
	 * of sub-strings within.
	 */
	private static boolean contentScan(String inputString)
	{
		// Scan string for match anywhere 
		for (String s : filterIfAnywhere)
		{
			if ((inputString).trim().toLowerCase().contains((new StringBuilder(rot13(s))).reverse().toString())) {return true;}
		}
		// Return true if entire string matches
		for (String s : filterIfEntire)
		{
			if ((inputString).trim().toLowerCase().equals((new StringBuilder(rot13(s))).reverse().toString())) {return true;}
		}
		
		// No matches : return false
		return false;
	}
	
	
	/**
	 * Rot13 codec
	 * Adapted from: http://introcs.cs.princeton.edu/java/31datatype/Rot13.java.html
	 */
	public static String rot13(String s)
	{
		StringBuilder out = new StringBuilder();
		
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if       (c >= 'a' && c <= 'm') c += 13;
            else if  (c >= 'A' && c <= 'M') c += 13;
            else if  (c >= 'n' && c <= 'z') c -= 13;
            else if  (c >= 'N' && c <= 'Z') c -= 13;
            
            out.append(c);
        }
        return out.toString();
    }
	
	
}
