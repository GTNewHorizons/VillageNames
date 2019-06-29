package astrotibs.villagenames.name;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

import astrotibs.villagenames.config.AlienConfigHandler;
import astrotibs.villagenames.config.AlienVillageConfigHandler;
import astrotibs.villagenames.config.AngelConfigHandler;
import astrotibs.villagenames.config.CustomConfigHandler;
import astrotibs.villagenames.config.DemonConfigHandler;
import astrotibs.villagenames.config.DragonConfigHandler;
import astrotibs.villagenames.config.EndCityConfigHandler;
import astrotibs.villagenames.config.FortressConfigHandler;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.config.GoblinConfigHandler;
import astrotibs.villagenames.config.GolemConfigHandler;
import astrotibs.villagenames.config.MansionConfigHandler;
import astrotibs.villagenames.config.MineshaftConfigHandler;
import astrotibs.villagenames.config.MonumentConfigHandler;
import astrotibs.villagenames.config.StrongholdConfigHandler;
import astrotibs.villagenames.config.TempleConfigHandler;
import astrotibs.villagenames.config.VillageConfigHandler;
import astrotibs.villagenames.config.VillagerConfigHandler;
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
	public static String[] newRandomName(String nameType) {
		
		Random random = new Random(); // Overwrites world random because of simultaneity issues
		
		// Unpack nameType into multiple possible name pools
		
		// Split input string by hyphen
		String[] nameType_raw = nameType.trim().split("\\s*-\\s*"); // Using regular expression \s* to optional remove leading and tailing spaces
		
		// Cast all elements as lowercase for easier comparison
		String[] nameType_a = new String[nameType_raw.length];
		for (int input_i=0; input_i < nameType_raw.length; input_i++) {nameType_a[input_i] = nameType_raw[input_i].toLowerCase().trim();}
		
		// Step 0: initialize empty syllable pools, into which will be added specific source pools
		String[] prefix = new String[]{};
		String[] suffix = new String[]{};
		
		String[] syl1begin = new String[]{};  String[] syl1end = new String[]{};
						                      String[] syl1trans = new String[]{};
		String[] syl2term = new String[]{};   String[] syl2trans = new String[]{};
		String[] syl3term = new String[]{};   String[] syl3trans = new String[]{};
		String[] syl4term = new String[]{};   String[] syl4trans = new String[]{};
		String[] syl5term = new String[]{};   String[] syl5trans = new String[]{};
		String[] syl6term = new String[]{};   String[] syl6trans = new String[]{};
						                      String[] syl7term = new String[]{};
		
	    // Load in syllable pieces
		
		//if (nameType.toLowerCase().trim().equals("village")) {
		if ( Arrays.asList(nameType_a).contains("village") ) {
			prefix =    ArrayUtils.addAll(prefix, VillageConfigHandler.village_prefix);
			suffix =    ArrayUtils.addAll(suffix, VillageConfigHandler.village_suffix);
			syl1begin = ArrayUtils.addAll(syl1begin, VillageConfigHandler.village_oneSylBegin);
			syl1end =   ArrayUtils.addAll(syl1end, VillageConfigHandler.village_oneSylEnd);
			syl1trans = ArrayUtils.addAll(syl1trans, VillageConfigHandler.village_syl1Trans);
			syl2term =  ArrayUtils.addAll(syl2term, VillageConfigHandler.village_syl2Term);
			syl2trans = ArrayUtils.addAll(syl2trans, VillageConfigHandler.village_syl2Trans);
			syl3term =  ArrayUtils.addAll(syl3term, VillageConfigHandler.village_syl3Term);
			syl3trans = ArrayUtils.addAll(syl3trans, VillageConfigHandler.village_syl3Trans);
			syl4term =  ArrayUtils.addAll(syl4term, VillageConfigHandler.village_syl4Term);
			syl4trans = ArrayUtils.addAll(syl4trans, VillageConfigHandler.village_syl4Trans);
			syl5term =  ArrayUtils.addAll(syl5term, VillageConfigHandler.village_syl5Term);
			syl5trans = ArrayUtils.addAll(syl5trans, VillageConfigHandler.village_syl5Trans);
			syl6term =  ArrayUtils.addAll(syl6term, VillageConfigHandler.village_syl6Term);
			syl6trans = ArrayUtils.addAll(syl6trans, VillageConfigHandler.village_syl6Trans);
			syl7term =  ArrayUtils.addAll(syl7term, VillageConfigHandler.village_syl7Term);
		}
		//else if (nameType.toLowerCase().trim().equals("temple")) {
		if ( Arrays.asList(nameType_a).contains("temple") ) {
			prefix =    ArrayUtils.addAll(prefix, TempleConfigHandler.temple_prefix);
			suffix =    ArrayUtils.addAll(suffix, TempleConfigHandler.temple_suffix);
			syl1begin = ArrayUtils.addAll(syl1begin, TempleConfigHandler.temple_oneSylBegin);
			syl1end =   ArrayUtils.addAll(syl1end, TempleConfigHandler.temple_oneSylEnd);
			syl1trans = ArrayUtils.addAll(syl1trans, TempleConfigHandler.temple_syl1Trans);
			syl2term =  ArrayUtils.addAll(syl2term, TempleConfigHandler.temple_syl2Term);
			syl2trans = ArrayUtils.addAll(syl2trans, TempleConfigHandler.temple_syl2Trans);
			syl3term =  ArrayUtils.addAll(syl3term, TempleConfigHandler.temple_syl3Term);
			syl3trans = ArrayUtils.addAll(syl3trans, TempleConfigHandler.temple_syl3Trans);
			syl4term =  ArrayUtils.addAll(syl4term, TempleConfigHandler.temple_syl4Term);
			syl4trans = ArrayUtils.addAll(syl4trans, TempleConfigHandler.temple_syl4Trans);
			syl5term =  ArrayUtils.addAll(syl5term, TempleConfigHandler.temple_syl5Term);
			syl5trans = ArrayUtils.addAll(syl5trans, TempleConfigHandler.temple_syl5Trans);
			syl6term =  ArrayUtils.addAll(syl6term, TempleConfigHandler.temple_syl6Term);
			syl6trans = ArrayUtils.addAll(syl6trans, TempleConfigHandler.temple_syl6Trans);
			syl7term =  ArrayUtils.addAll(syl7term, TempleConfigHandler.temple_syl7Term);
		}
		//else if (nameType.toLowerCase().trim().equals("mineshaft")) {
		if ( Arrays.asList(nameType_a).contains("mineshaft") ) {
			prefix =    ArrayUtils.addAll(prefix, MineshaftConfigHandler.mineshaft_prefix);
			suffix =    ArrayUtils.addAll(suffix, MineshaftConfigHandler.mineshaft_suffix);
			syl1begin = ArrayUtils.addAll(syl1begin, MineshaftConfigHandler.mineshaft_oneSylBegin);
			syl1end =   ArrayUtils.addAll(syl1end, MineshaftConfigHandler.mineshaft_oneSylEnd);
			syl1trans = ArrayUtils.addAll(syl1trans, MineshaftConfigHandler.mineshaft_syl1Trans);
			syl2term =  ArrayUtils.addAll(syl2term, MineshaftConfigHandler.mineshaft_syl2Term);
			syl2trans = ArrayUtils.addAll(syl2trans, MineshaftConfigHandler.mineshaft_syl2Trans);
			syl3term =  ArrayUtils.addAll(syl3term, MineshaftConfigHandler.mineshaft_syl3Term);
			syl3trans = ArrayUtils.addAll(syl3trans, MineshaftConfigHandler.mineshaft_syl3Trans);
			syl4term =  ArrayUtils.addAll(syl4term, MineshaftConfigHandler.mineshaft_syl4Term);
			syl4trans = ArrayUtils.addAll(syl4trans, MineshaftConfigHandler.mineshaft_syl4Trans);
			syl5term =  ArrayUtils.addAll(syl5term, MineshaftConfigHandler.mineshaft_syl5Term);
			syl5trans = ArrayUtils.addAll(syl5trans, MineshaftConfigHandler.mineshaft_syl5Trans);
			syl6term =  ArrayUtils.addAll(syl6term, MineshaftConfigHandler.mineshaft_syl6Term);
			syl6trans = ArrayUtils.addAll(syl6trans, MineshaftConfigHandler.mineshaft_syl6Trans);
			syl7term =  ArrayUtils.addAll(syl7term, MineshaftConfigHandler.mineshaft_syl7Term);
		}
		//else if (nameType.toLowerCase().trim().equals("fortress")) {
		if ( Arrays.asList(nameType_a).contains("fortress") ) {
			prefix =    ArrayUtils.addAll(prefix, FortressConfigHandler.fortress_prefix);
			suffix =    ArrayUtils.addAll(suffix, FortressConfigHandler.fortress_suffix);
			syl1begin = ArrayUtils.addAll(syl1begin, FortressConfigHandler.fortress_oneSylBegin);
			syl1end =   ArrayUtils.addAll(syl1end, FortressConfigHandler.fortress_oneSylEnd);
			syl1trans = ArrayUtils.addAll(syl1trans, FortressConfigHandler.fortress_syl1Trans);
			syl2term =  ArrayUtils.addAll(syl2term, FortressConfigHandler.fortress_syl2Term);
			syl2trans = ArrayUtils.addAll(syl2trans, FortressConfigHandler.fortress_syl2Trans);
			syl3term =  ArrayUtils.addAll(syl3term, FortressConfigHandler.fortress_syl3Term);
			syl3trans = ArrayUtils.addAll(syl3trans, FortressConfigHandler.fortress_syl3Trans);
			syl4term =  ArrayUtils.addAll(syl4term, FortressConfigHandler.fortress_syl4Term);
			syl4trans = ArrayUtils.addAll(syl4trans, FortressConfigHandler.fortress_syl4Trans);
			syl5term =  ArrayUtils.addAll(syl5term, FortressConfigHandler.fortress_syl5Term);
			syl5trans = ArrayUtils.addAll(syl5trans, FortressConfigHandler.fortress_syl5Trans);
			syl6term =  ArrayUtils.addAll(syl6term, FortressConfigHandler.fortress_syl6Term);
			syl6trans = ArrayUtils.addAll(syl6trans, FortressConfigHandler.fortress_syl6Trans);
			syl7term =  ArrayUtils.addAll(syl7term, FortressConfigHandler.fortress_syl7Term);
		}
		//else if (nameType.toLowerCase().trim().equals("stronghold")) {
		if ( Arrays.asList(nameType_a).contains("stronghold") ) {
			prefix =    ArrayUtils.addAll(prefix, StrongholdConfigHandler.stronghold_prefix);
			suffix =    ArrayUtils.addAll(suffix, StrongholdConfigHandler.stronghold_suffix);
			syl1begin = ArrayUtils.addAll(syl1begin, StrongholdConfigHandler.stronghold_oneSylBegin);
			syl1end =   ArrayUtils.addAll(syl1end, StrongholdConfigHandler.stronghold_oneSylEnd);
			syl1trans = ArrayUtils.addAll(syl1trans, StrongholdConfigHandler.stronghold_syl1Trans);
			syl2term =  ArrayUtils.addAll(syl2term, StrongholdConfigHandler.stronghold_syl2Term);
			syl2trans = ArrayUtils.addAll(syl2trans, StrongholdConfigHandler.stronghold_syl2Trans);
			syl3term =  ArrayUtils.addAll(syl3term, StrongholdConfigHandler.stronghold_syl3Term);
			syl3trans = ArrayUtils.addAll(syl3trans, StrongholdConfigHandler.stronghold_syl3Trans);
			syl4term =  ArrayUtils.addAll(syl4term, StrongholdConfigHandler.stronghold_syl4Term);
			syl4trans = ArrayUtils.addAll(syl4trans, StrongholdConfigHandler.stronghold_syl4Trans);
			syl5term =  ArrayUtils.addAll(syl5term, StrongholdConfigHandler.stronghold_syl5Term);
			syl5trans = ArrayUtils.addAll(syl5trans, StrongholdConfigHandler.stronghold_syl5Trans);
			syl6term =  ArrayUtils.addAll(syl6term, StrongholdConfigHandler.stronghold_syl6Term);
			syl6trans = ArrayUtils.addAll(syl6trans, StrongholdConfigHandler.stronghold_syl6Trans);
			syl7term =  ArrayUtils.addAll(syl7term, StrongholdConfigHandler.stronghold_syl7Term);
		}
		//else if (nameType.toLowerCase().trim().equals("monument")) {
		if ( Arrays.asList(nameType_a).contains("monument") ) {
			prefix =    ArrayUtils.addAll(prefix, MonumentConfigHandler.monument_prefix);
			suffix =    ArrayUtils.addAll(suffix, MonumentConfigHandler.monument_suffix);
			syl1begin = ArrayUtils.addAll(syl1begin, MonumentConfigHandler.monument_oneSylBegin);
			syl1end =   ArrayUtils.addAll(syl1end, MonumentConfigHandler.monument_oneSylEnd);
			syl1trans = ArrayUtils.addAll(syl1trans, MonumentConfigHandler.monument_syl1Trans);
			syl2term =  ArrayUtils.addAll(syl2term, MonumentConfigHandler.monument_syl2Term);
			syl2trans = ArrayUtils.addAll(syl2trans, MonumentConfigHandler.monument_syl2Trans);
			syl3term =  ArrayUtils.addAll(syl3term, MonumentConfigHandler.monument_syl3Term);
			syl3trans = ArrayUtils.addAll(syl3trans, MonumentConfigHandler.monument_syl3Trans);
			syl4term =  ArrayUtils.addAll(syl4term, MonumentConfigHandler.monument_syl4Term);
			syl4trans = ArrayUtils.addAll(syl4trans, MonumentConfigHandler.monument_syl4Trans);
			syl5term =  ArrayUtils.addAll(syl5term, MonumentConfigHandler.monument_syl5Term);
			syl5trans = ArrayUtils.addAll(syl5trans, MonumentConfigHandler.monument_syl5Trans);
			syl6term =  ArrayUtils.addAll(syl6term, MonumentConfigHandler.monument_syl6Term);
			syl6trans = ArrayUtils.addAll(syl6trans, MonumentConfigHandler.monument_syl6Trans);
			syl7term =  ArrayUtils.addAll(syl7term, MonumentConfigHandler.monument_syl7Term);
		}
		//else if (nameType.toLowerCase().trim().equals("endcity")) {
		if ( Arrays.asList(nameType_a).contains("endcity") ) {
			prefix =    ArrayUtils.addAll(prefix, EndCityConfigHandler.endcity_prefix);
			suffix =    ArrayUtils.addAll(suffix, EndCityConfigHandler.endcity_suffix);
			syl1begin = ArrayUtils.addAll(syl1begin, EndCityConfigHandler.endcity_oneSylBegin);
			syl1end =   ArrayUtils.addAll(syl1end, EndCityConfigHandler.endcity_oneSylEnd);
			syl1trans = ArrayUtils.addAll(syl1trans, EndCityConfigHandler.endcity_syl1Trans);
			syl2term =  ArrayUtils.addAll(syl2term, EndCityConfigHandler.endcity_syl2Term);
			syl2trans = ArrayUtils.addAll(syl2trans, EndCityConfigHandler.endcity_syl2Trans);
			syl3term =  ArrayUtils.addAll(syl3term, EndCityConfigHandler.endcity_syl3Term);
			syl3trans = ArrayUtils.addAll(syl3trans, EndCityConfigHandler.endcity_syl3Trans);
			syl4term =  ArrayUtils.addAll(syl4term, EndCityConfigHandler.endcity_syl4Term);
			syl4trans = ArrayUtils.addAll(syl4trans, EndCityConfigHandler.endcity_syl4Trans);
			syl5term =  ArrayUtils.addAll(syl5term, EndCityConfigHandler.endcity_syl5Term);
			syl5trans = ArrayUtils.addAll(syl5trans, EndCityConfigHandler.endcity_syl5Trans);
			syl6term =  ArrayUtils.addAll(syl6term, EndCityConfigHandler.endcity_syl6Term);
			syl6trans = ArrayUtils.addAll(syl6trans, EndCityConfigHandler.endcity_syl6Trans);
			syl7term =  ArrayUtils.addAll(syl7term, EndCityConfigHandler.endcity_syl7Term);
		}
		//else if (nameType.toLowerCase().trim().equals("mansion")) {
		if ( Arrays.asList(nameType_a).contains("mansion") ) {
			prefix =    ArrayUtils.addAll(prefix, MansionConfigHandler.mansion_prefix);
			suffix =    ArrayUtils.addAll(suffix, MansionConfigHandler.mansion_suffix);
			syl1begin = ArrayUtils.addAll(syl1begin, MansionConfigHandler.mansion_oneSylBegin);
			syl1end =   ArrayUtils.addAll(syl1end, MansionConfigHandler.mansion_oneSylEnd);
			syl1trans = ArrayUtils.addAll(syl1trans, MansionConfigHandler.mansion_syl1Trans);
			syl2term =  ArrayUtils.addAll(syl2term, MansionConfigHandler.mansion_syl2Term);
			syl2trans = ArrayUtils.addAll(syl2trans, MansionConfigHandler.mansion_syl2Trans);
			syl3term =  ArrayUtils.addAll(syl3term, MansionConfigHandler.mansion_syl3Term);
			syl3trans = ArrayUtils.addAll(syl3trans, MansionConfigHandler.mansion_syl3Trans);
			syl4term =  ArrayUtils.addAll(syl4term, MansionConfigHandler.mansion_syl4Term);
			syl4trans = ArrayUtils.addAll(syl4trans, MansionConfigHandler.mansion_syl4Trans);
			syl5term =  ArrayUtils.addAll(syl5term, MansionConfigHandler.mansion_syl5Term);
			syl5trans = ArrayUtils.addAll(syl5trans, MansionConfigHandler.mansion_syl5Trans);
			syl6term =  ArrayUtils.addAll(syl6term, MansionConfigHandler.mansion_syl6Term);
			syl6trans = ArrayUtils.addAll(syl6trans, MansionConfigHandler.mansion_syl6Trans);
			syl7term =  ArrayUtils.addAll(syl7term, MansionConfigHandler.mansion_syl7Term);
		}
		//else if (nameType.toLowerCase().trim().equals("alienvillager")) {
		if ( Arrays.asList(nameType_a).contains("alien") ) {
			prefix =    ArrayUtils.addAll(prefix, AlienConfigHandler.alien_prefix);
			suffix =    ArrayUtils.addAll(suffix, AlienConfigHandler.alien_suffix);
			syl1begin = ArrayUtils.addAll(syl1begin, AlienConfigHandler.alien_oneSylBegin);
			syl1end =   ArrayUtils.addAll(syl1end, AlienConfigHandler.alien_oneSylEnd);
			syl1trans = ArrayUtils.addAll(syl1trans, AlienConfigHandler.alien_syl1Trans);
			syl2term =  ArrayUtils.addAll(syl2term, AlienConfigHandler.alien_syl2Term);
			syl2trans = ArrayUtils.addAll(syl2trans, AlienConfigHandler.alien_syl2Trans);
			syl3term =  ArrayUtils.addAll(syl3term, AlienConfigHandler.alien_syl3Term);
			syl3trans = ArrayUtils.addAll(syl3trans, AlienConfigHandler.alien_syl3Trans);
			syl4term =  ArrayUtils.addAll(syl4term, AlienConfigHandler.alien_syl4Term);
			syl4trans = ArrayUtils.addAll(syl4trans, AlienConfigHandler.alien_syl4Trans);
			syl5term =  ArrayUtils.addAll(syl5term, AlienConfigHandler.alien_syl5Term);
			syl5trans = ArrayUtils.addAll(syl5trans, AlienConfigHandler.alien_syl5Trans);
			syl6term =  ArrayUtils.addAll(syl6term, AlienConfigHandler.alien_syl6Term);
			syl6trans = ArrayUtils.addAll(syl6trans, AlienConfigHandler.alien_syl6Trans);
			syl7term =  ArrayUtils.addAll(syl7term, AlienConfigHandler.alien_syl7Term);
		}
		//else if (nameType.toLowerCase().trim().equals("alienvillage")) {
		if ( Arrays.asList(nameType_a).contains("alienvillage") ) {
			prefix =    ArrayUtils.addAll(prefix, AlienVillageConfigHandler.alienVillage_prefix);
			suffix =    ArrayUtils.addAll(suffix, AlienVillageConfigHandler.alienVillage_suffix);
			syl1begin = ArrayUtils.addAll(syl1begin, AlienVillageConfigHandler.alienVillage_oneSylBegin);
			syl1end =   ArrayUtils.addAll(syl1end, AlienVillageConfigHandler.alienVillage_oneSylEnd);
			syl1trans = ArrayUtils.addAll(syl1trans, AlienVillageConfigHandler.alienVillage_syl1Trans);
			syl2term =  ArrayUtils.addAll(syl2term, AlienVillageConfigHandler.alienVillage_syl2Term);
			syl2trans = ArrayUtils.addAll(syl2trans, AlienVillageConfigHandler.alienVillage_syl2Trans);
			syl3term =  ArrayUtils.addAll(syl3term, AlienVillageConfigHandler.alienVillage_syl3Term);
			syl3trans = ArrayUtils.addAll(syl3trans, AlienVillageConfigHandler.alienVillage_syl3Trans);
			syl4term =  ArrayUtils.addAll(syl4term, AlienVillageConfigHandler.alienVillage_syl4Term);
			syl4trans = ArrayUtils.addAll(syl4trans, AlienVillageConfigHandler.alienVillage_syl4Trans);
			syl5term =  ArrayUtils.addAll(syl5term, AlienVillageConfigHandler.alienVillage_syl5Term);
			syl5trans = ArrayUtils.addAll(syl5trans, AlienVillageConfigHandler.alienVillage_syl5Trans);
			syl6term =  ArrayUtils.addAll(syl6term, AlienVillageConfigHandler.alienVillage_syl6Term);
			syl6trans = ArrayUtils.addAll(syl6trans, AlienVillageConfigHandler.alienVillage_syl6Trans);
			syl7term =  ArrayUtils.addAll(syl7term, AlienVillageConfigHandler.alienVillage_syl7Term);
		}
		//else if (nameType.toLowerCase().trim().equals("goblin")) {
		if ( Arrays.asList(nameType_a).contains("goblin") ) {
			prefix =    ArrayUtils.addAll(prefix, GoblinConfigHandler.goblin_prefix);
			suffix =    ArrayUtils.addAll(suffix, GoblinConfigHandler.goblin_suffix);
			syl1begin = ArrayUtils.addAll(syl1begin, GoblinConfigHandler.goblin_oneSylBegin);
			syl1end =   ArrayUtils.addAll(syl1end, GoblinConfigHandler.goblin_oneSylEnd);
			syl1trans = ArrayUtils.addAll(syl1trans, GoblinConfigHandler.goblin_syl1Trans);
			syl2term =  ArrayUtils.addAll(syl2term, GoblinConfigHandler.goblin_syl2Term);
			syl2trans = ArrayUtils.addAll(syl2trans, GoblinConfigHandler.goblin_syl2Trans);
			syl3term =  ArrayUtils.addAll(syl3term, GoblinConfigHandler.goblin_syl3Term);
			syl3trans = ArrayUtils.addAll(syl3trans, GoblinConfigHandler.goblin_syl3Trans);
			syl4term =  ArrayUtils.addAll(syl4term, GoblinConfigHandler.goblin_syl4Term);
			syl4trans = ArrayUtils.addAll(syl4trans, GoblinConfigHandler.goblin_syl4Trans);
			syl5term =  ArrayUtils.addAll(syl5term, GoblinConfigHandler.goblin_syl5Term);
			syl5trans = ArrayUtils.addAll(syl5trans, GoblinConfigHandler.goblin_syl5Trans);
			syl6term =  ArrayUtils.addAll(syl6term, GoblinConfigHandler.goblin_syl6Term);
			syl6trans = ArrayUtils.addAll(syl6trans, GoblinConfigHandler.goblin_syl6Trans);
			syl7term =  ArrayUtils.addAll(syl7term, GoblinConfigHandler.goblin_syl7Term);
		}
		//else if (nameType.toLowerCase().trim().equals("golem")) {
		if ( Arrays.asList(nameType_a).contains("golem") ) {
			prefix =    ArrayUtils.addAll(prefix, GolemConfigHandler.golem_prefix);
			suffix =    ArrayUtils.addAll(suffix, GolemConfigHandler.golem_suffix);
			syl1begin = ArrayUtils.addAll(syl1begin, GolemConfigHandler.golem_oneSylBegin);
			syl1end =   ArrayUtils.addAll(syl1end, GolemConfigHandler.golem_oneSylEnd);
			syl1trans = ArrayUtils.addAll(syl1trans, GolemConfigHandler.golem_syl1Trans);
			syl2term =  ArrayUtils.addAll(syl2term, GolemConfigHandler.golem_syl2Term);
			syl2trans = ArrayUtils.addAll(syl2trans, GolemConfigHandler.golem_syl2Trans);
			syl3term =  ArrayUtils.addAll(syl3term, GolemConfigHandler.golem_syl3Term);
			syl3trans = ArrayUtils.addAll(syl3trans, GolemConfigHandler.golem_syl3Trans);
			syl4term =  ArrayUtils.addAll(syl4term, GolemConfigHandler.golem_syl4Term);
			syl4trans = ArrayUtils.addAll(syl4trans, GolemConfigHandler.golem_syl4Trans);
			syl5term =  ArrayUtils.addAll(syl5term, GolemConfigHandler.golem_syl5Term);
			syl5trans = ArrayUtils.addAll(syl5trans, GolemConfigHandler.golem_syl5Trans);
			syl6term =  ArrayUtils.addAll(syl6term, GolemConfigHandler.golem_syl6Term);
			syl6trans = ArrayUtils.addAll(syl6trans, GolemConfigHandler.golem_syl6Trans);
			syl7term =  ArrayUtils.addAll(syl7term, GolemConfigHandler.golem_syl7Term);
		}
		//else if (nameType.toLowerCase().trim().equals("demon")) {
		if ( Arrays.asList(nameType_a).contains("demon") ) {
			prefix =    ArrayUtils.addAll(prefix, DemonConfigHandler.demon_prefix);
			suffix =    ArrayUtils.addAll(suffix, DemonConfigHandler.demon_suffix);
			syl1begin = ArrayUtils.addAll(syl1begin, DemonConfigHandler.demon_oneSylBegin);
			syl1end =   ArrayUtils.addAll(syl1end, DemonConfigHandler.demon_oneSylEnd);
			syl1trans = ArrayUtils.addAll(syl1trans, DemonConfigHandler.demon_syl1Trans);
			syl2term =  ArrayUtils.addAll(syl2term, DemonConfigHandler.demon_syl2Term);
			syl2trans = ArrayUtils.addAll(syl2trans, DemonConfigHandler.demon_syl2Trans);
			syl3term =  ArrayUtils.addAll(syl3term, DemonConfigHandler.demon_syl3Term);
			syl3trans = ArrayUtils.addAll(syl3trans, DemonConfigHandler.demon_syl3Trans);
			syl4term =  ArrayUtils.addAll(syl4term, DemonConfigHandler.demon_syl4Term);
			syl4trans = ArrayUtils.addAll(syl4trans, DemonConfigHandler.demon_syl4Trans);
			syl5term =  ArrayUtils.addAll(syl5term, DemonConfigHandler.demon_syl5Term);
			syl5trans = ArrayUtils.addAll(syl5trans, DemonConfigHandler.demon_syl5Trans);
			syl6term =  ArrayUtils.addAll(syl6term, DemonConfigHandler.demon_syl6Term);
			syl6trans = ArrayUtils.addAll(syl6trans, DemonConfigHandler.demon_syl6Trans);
			syl7term =  ArrayUtils.addAll(syl7term, DemonConfigHandler.demon_syl7Term);
		}
		//else if (nameType.toLowerCase().trim().equals("angel")) {
		if ( Arrays.asList(nameType_a).contains("angel") ) {
			prefix =    ArrayUtils.addAll(prefix, AngelConfigHandler.angel_prefix);
			suffix =    ArrayUtils.addAll(suffix, AngelConfigHandler.angel_suffix);
			syl1begin = ArrayUtils.addAll(syl1begin, AngelConfigHandler.angel_oneSylBegin);
			syl1end =   ArrayUtils.addAll(syl1end, AngelConfigHandler.angel_oneSylEnd);
			syl1trans = ArrayUtils.addAll(syl1trans, AngelConfigHandler.angel_syl1Trans);
			syl2term =  ArrayUtils.addAll(syl2term, AngelConfigHandler.angel_syl2Term);
			syl2trans = ArrayUtils.addAll(syl2trans, AngelConfigHandler.angel_syl2Trans);
			syl3term =  ArrayUtils.addAll(syl3term, AngelConfigHandler.angel_syl3Term);
			syl3trans = ArrayUtils.addAll(syl3trans, AngelConfigHandler.angel_syl3Trans);
			syl4term =  ArrayUtils.addAll(syl4term, AngelConfigHandler.angel_syl4Term);
			syl4trans = ArrayUtils.addAll(syl4trans, AngelConfigHandler.angel_syl4Trans);
			syl5term =  ArrayUtils.addAll(syl5term, AngelConfigHandler.angel_syl5Term);
			syl5trans = ArrayUtils.addAll(syl5trans, AngelConfigHandler.angel_syl5Trans);
			syl6term =  ArrayUtils.addAll(syl6term, AngelConfigHandler.angel_syl6Term);
			syl6trans = ArrayUtils.addAll(syl6trans, AngelConfigHandler.angel_syl6Trans);
			syl7term =  ArrayUtils.addAll(syl7term, AngelConfigHandler.angel_syl7Term);
		}
		//else if (nameType.toLowerCase().trim().equals("dragon")) {
		if ( Arrays.asList(nameType_a).contains("dragon") ) {
			prefix =    ArrayUtils.addAll(prefix, DragonConfigHandler.dragon_prefix);
			suffix =    ArrayUtils.addAll(suffix, DragonConfigHandler.dragon_suffix);
			syl1begin = ArrayUtils.addAll(syl1begin, DragonConfigHandler.dragon_oneSylBegin);
			syl1end =   ArrayUtils.addAll(syl1end, DragonConfigHandler.dragon_oneSylEnd);
			syl1trans = ArrayUtils.addAll(syl1trans, DragonConfigHandler.dragon_syl1Trans);
			syl2term =  ArrayUtils.addAll(syl2term, DragonConfigHandler.dragon_syl2Term);
			syl2trans = ArrayUtils.addAll(syl2trans, DragonConfigHandler.dragon_syl2Trans);
			syl3term =  ArrayUtils.addAll(syl3term, DragonConfigHandler.dragon_syl3Term);
			syl3trans = ArrayUtils.addAll(syl3trans, DragonConfigHandler.dragon_syl3Trans);
			syl4term =  ArrayUtils.addAll(syl4term, DragonConfigHandler.dragon_syl4Term);
			syl4trans = ArrayUtils.addAll(syl4trans, DragonConfigHandler.dragon_syl4Trans);
			syl5term =  ArrayUtils.addAll(syl5term, DragonConfigHandler.dragon_syl5Term);
			syl5trans = ArrayUtils.addAll(syl5trans, DragonConfigHandler.dragon_syl5Trans);
			syl6term =  ArrayUtils.addAll(syl6term, DragonConfigHandler.dragon_syl6Term);
			syl6trans = ArrayUtils.addAll(syl6trans, DragonConfigHandler.dragon_syl6Trans);
			syl7term =  ArrayUtils.addAll(syl7term, DragonConfigHandler.dragon_syl7Term);
		}
		//else if (nameType.toLowerCase().trim().equals("custom")) {
		if ( Arrays.asList(nameType_a).contains("custom") ) {
			prefix =    ArrayUtils.addAll(prefix, CustomConfigHandler.custom_prefix);
			suffix =    ArrayUtils.addAll(suffix, CustomConfigHandler.custom_suffix);
			syl1begin = ArrayUtils.addAll(syl1begin, CustomConfigHandler.custom_oneSylBegin);
			syl1end =   ArrayUtils.addAll(syl1end, CustomConfigHandler.custom_oneSylEnd);
			syl1trans = ArrayUtils.addAll(syl1trans, CustomConfigHandler.custom_syl1Trans);
			syl2term =  ArrayUtils.addAll(syl2term, CustomConfigHandler.custom_syl2Term);
			syl2trans = ArrayUtils.addAll(syl2trans, CustomConfigHandler.custom_syl2Trans);
			syl3term =  ArrayUtils.addAll(syl3term, CustomConfigHandler.custom_syl3Term);
			syl3trans = ArrayUtils.addAll(syl3trans, CustomConfigHandler.custom_syl3Trans);
			syl4term =  ArrayUtils.addAll(syl4term, CustomConfigHandler.custom_syl4Term);
			syl4trans = ArrayUtils.addAll(syl4trans, CustomConfigHandler.custom_syl4Trans);
			syl5term =  ArrayUtils.addAll(syl5term, CustomConfigHandler.custom_syl5Term);
			syl5trans = ArrayUtils.addAll(syl5trans, CustomConfigHandler.custom_syl5Trans);
			syl6term =  ArrayUtils.addAll(syl6term, CustomConfigHandler.custom_syl6Term);
			syl6trans = ArrayUtils.addAll(syl6trans, CustomConfigHandler.custom_syl6Trans);
			syl7term =  ArrayUtils.addAll(syl7term, CustomConfigHandler.custom_syl7Term);
		}
		// It's possible the player made a mistake and no name pieces were correctly entered. If so, default to "villager"
		if ( 
				Arrays.asList(nameType_a).contains("villager") // User deliberately chose "villager"
				|| (syl1begin.length + syl1trans.length) <= 0  // No previous entries were chosen
				) {
			if ( !Arrays.asList(nameType_a).contains("villager") && (syl1begin.length + syl1trans.length) <= 0 )
				{ if (GeneralConfig.debugMessages) LogHelper.error("Submitted nameType contained no valid entries! Defaulting to Villager name pool."); }
			prefix =    ArrayUtils.addAll(prefix, VillagerConfigHandler.villager_prefix);
			suffix =    ArrayUtils.addAll(suffix, VillagerConfigHandler.villager_suffix);
			syl1begin = ArrayUtils.addAll(syl1begin, VillagerConfigHandler.villager_oneSylBegin);
			syl1end =   ArrayUtils.addAll(syl1end, VillagerConfigHandler.villager_oneSylEnd);
			syl1trans = ArrayUtils.addAll(syl1trans, VillagerConfigHandler.villager_syl1Trans);
			syl2term =  ArrayUtils.addAll(syl2term, VillagerConfigHandler.villager_syl2Term);
			syl2trans = ArrayUtils.addAll(syl2trans, VillagerConfigHandler.villager_syl2Trans);
			syl3term =  ArrayUtils.addAll(syl3term, VillagerConfigHandler.villager_syl3Term);
			syl3trans = ArrayUtils.addAll(syl3trans, VillagerConfigHandler.villager_syl3Trans);
			syl4term =  ArrayUtils.addAll(syl4term, VillagerConfigHandler.villager_syl4Term);
			syl4trans = ArrayUtils.addAll(syl4trans, VillagerConfigHandler.villager_syl4Trans);
			syl5term =  ArrayUtils.addAll(syl5term, VillagerConfigHandler.villager_syl5Term);
			syl5trans = ArrayUtils.addAll(syl5trans, VillagerConfigHandler.villager_syl5Trans);
			syl6term =  ArrayUtils.addAll(syl6term, VillagerConfigHandler.villager_syl6Term);
			syl6trans = ArrayUtils.addAll(syl6trans, VillagerConfigHandler.villager_syl6Trans);
			syl7term =  ArrayUtils.addAll(syl7term, VillagerConfigHandler.villager_syl7Term);
		}
		
		// The three pieces of interest
		String r_prefix = "";
		String r_suffix = "";
		String rootName = "";
		
		// These integers will get iterated over every time a root generation fails.
		// An exception is thrown if one gets to 50--pretty generous, if I say so.
		// Someone has to have REALLY bungled up syllable pools to have done this.
		int tooManyFailures = 50;
		int blankRoot = 0;
		int sizeOverflow = 0;
		int sizeUnderflow = 0;
		int repeatedChar = 0;
		int filterFail = 0;
		
		int r; // Integer used for randomizing
		
		// Step 1: Generate a prefix.
		if ( (syl1begin.length + syl1trans.length) > 0) { // There are starting syllables.
			
			if ( (syl1begin.length + syl1trans.length) > prefix.length) { // There are more starting syllables than prefixes, as should be the case.
				r = random.nextInt(syl1begin.length + syl1trans.length);
				if (r < prefix.length) r_prefix=prefix[r]; // Prefix generated
				if (prefix.length >= (syl1begin.length + syl1trans.length) && GeneralConfig.debugMessages) LogHelper.warn(nameType + " has more prefixes than first syllables!");
			}
			else { // There are fewer starting syllables than prefixes, which is abnormal. Just pick a random prefix.
				r_prefix = (prefix.length>0) ? prefix[random.nextInt(prefix.length)] : "";
			}
		}
		else LogHelper.error(nameType + " has no entries for the first syllable!");
		
		
		// Step 3: Generate a suffix.
		//r_suffix = (suffix.length>0) ? suffix[random.nextInt(suffix.length)] : "";
		if ( (syl1begin.length + syl1trans.length) > 0) { // There are starting syllables.
			
			if ( (syl1begin.length + syl1trans.length) > suffix.length) { // There are more starting syllables than suffixes, as should be the case.
				r = random.nextInt(syl1begin.length + syl1trans.length);
				if (r < suffix.length) r_suffix=suffix[r]; // Suffix generated
				if (suffix.length >= (syl1begin.length + syl1trans.length) && GeneralConfig.debugMessages) LogHelper.warn(nameType + " has more suffixes than first syllables!");
			}
			else { // There are fewer starting syllables than suffixes, which is abnormal. Just pick a random suffix.
				r_suffix = (suffix.length>0) ? suffix[random.nextInt(suffix.length)] : "";
			}
		}
		
		// Step 2: Generate a proper (root) name.
		
		// The while loop continues until a valid name is generated or an exception is thrown
		while (true) {
			
			// Step 2.1: Determine whether or not this will be a one-syllable name
			if ( (syl1begin.length + syl1trans.length) <= 0 ) { // There is no first syllable to choose from!
				
				String errorMessage = "Name type " + nameType + " has no syllable 1 entries! No name can be constructed!";
				LogHelper.fatal(errorMessage);
				throw new RuntimeException(errorMessage);
			}
			else {
				r = random.nextInt(syl1begin.length + syl1trans.length);
				if (r < syl1begin.length) { // This will be a monosyllabic name.
					rootName = syl1begin[r];
					// Now try to generate an end cap
					r = random.nextInt(syl1begin.length);
					if (r < syl1end.length) { // Add an ending part to the syllable
						rootName += syl1end[r];
					}
					if ( syl1end.length > syl1begin.length && GeneralConfig.debugMessages) LogHelper.warn("Name type " + nameType + " has more one-syllable ending pieces than beginning pieces!");

					// The root name was created. It is one syllable long.
				}
				else { // This will be a polysyllabic name.
					// Start with a 1-syl transitional name
					rootName = syl1trans[r-syl1begin.length];
					
					// Step 2.2: Determine whether to stop at syllable 2 or continue to a third
					if ( (syl2term.length + syl2trans.length) <= 0 ) { // There is no second syllable to choose from!
						if (GeneralConfig.debugMessages) LogHelper.error("Name type " + nameType + " has transitional syllable 1 entries, but no syllable 2 entries!");
						// The root name was created. It is one syllable long, but it has ended on a transitional syllable.
					}
					else {
						r = random.nextInt(syl2term.length + syl2trans.length);
						if (r < syl2term.length) { // This name will terminate at syllable 2.
							rootName += syl2term[r];
						}
						else { // This name will go on to a third syllable.
							rootName += syl2trans[r-syl2term.length];
							
							// Step 2.3: Determine whether to stop at syllable 3 or continue to a fourth
							if ( (syl3term.length + syl3trans.length) <= 0 ) { // There is no third syllable to choose from!
								if (GeneralConfig.debugMessages) LogHelper.error("Name type " + nameType + " has transitional syllable 2 entries, but no syllable 3 entries!");
								// The root name was created. It is two syllables long, but it has ended on a transitional syllable.
							}
							else {
								r = random.nextInt(syl3term.length + syl3trans.length);
								if (r < syl3term.length) { // This name will terminate at syllable 3.
									rootName += syl3term[r];
								}
								else { // This name will go on to a fourth syllable.
									rootName += syl3trans[r-syl3term.length];
									
									// Step 2.4: Determine whether to stop at syllable 4 or continue to a fifth
									if ( (syl4term.length + syl4trans.length) <= 0 ) { // There is no fourth syllable to choose from!
										if (GeneralConfig.debugMessages) LogHelper.error("Name type " + nameType + " has transitional syllable 3 entries, but no syllable 4 entries!");
										// The root name was created. It is three syllables long, but it has ended on a transitional syllable.
									}
									else {
										r = random.nextInt(syl4term.length + syl4trans.length);
										if (r < syl4term.length) { // This name will terminate at syllable 4.
											rootName += syl4term[r];
										}
										else { // This name will go on to a fifth syllable.
											rootName += syl4trans[r-syl4term.length];
											
											// Step 2.5: Determine whether to stop at syllable 5 or continue to a sixth
											if ( (syl5term.length + syl5trans.length) <= 0 ) { // There is no fifth syllable to choose from!
												if (GeneralConfig.debugMessages) LogHelper.error("Name type " + nameType + " has transitional syllable 4 entries, but no syllable 5 entries!");
												// The root name was created. It is four syllables long, but it has ended on a transitional syllable.
											}
											else {
												r = random.nextInt(syl5term.length + syl5trans.length);
												if (r < syl5term.length) { // This name will terminate at syllable 5.
													rootName += syl5term[r];
												}
												else { // This name will go on to a sixth syllable.
													rootName += syl5trans[r-syl5term.length];
													
													// Step 2.6: Determine whether to stop at syllable 6 or continue to a seventh
													if ( (syl6term.length + syl6trans.length) <= 0 ) { // There is no sixth syllable to choose from!
														if (GeneralConfig.debugMessages) LogHelper.error("Name type " + nameType + " has transitional syllable 5 entries, but no syllable 6 entries!");
														// The root name was created. It is five syllables long, but it has ended on a transitional syllable.
													}
													else {
														r = random.nextInt(syl6term.length + syl6trans.length);
														if (r < syl6term.length) { // This name will terminate at syllable 6.
															rootName += syl6term[r];
														}
														else { // This name will go on to a seventh syllable.
															rootName += syl6trans[r-syl6term.length];
															
															// Step 2.7: Generate a seventh syllable
															if ( syl7term.length <= 0 ) { // There is no seventh syllable to choose from!
																if (GeneralConfig.debugMessages) LogHelper.error("Name type " + nameType + " has transitional syllable 6 entries, but no syllable 7 entries!");
																// The root name was created. It is six syllables long, but it has ended on a transitional syllable.
															}
															else {
																r = random.nextInt(syl7term.length);
																rootName += syl7term[r];
																// Ended on syllable 7.
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
			// Step 2V: clean up for validation
			
			rootName = rootName.trim();
						
			// Replace carots here with INTENTIONAL spaces.
			rootName = rootName.replaceAll("\\^", " ");
			
			// I have to reject this (root) name if it's not within the allotted size threshold.
			// Also I should ensure the last three characters are not all the same.
			
			if ( rootName.length() <= 15 ) {
				if ( rootName.length() >= 3 ) {
					// Now, make sure the same characters don't appear in the name three times in a row
					char[] nameRootArray = rootName.toLowerCase().toCharArray();
					int consecutives = 0;
					for(int ci = 0; ci < nameRootArray.length-2; ci++) {
						if (nameRootArray[ci] == nameRootArray[ci+1] && nameRootArray[ci] == nameRootArray[ci+2]) {
							consecutives++; 
						}
					}
					if (consecutives == 0) {
						// Do a content scan
						if ( !contentScan(rootName) ) {
							// Passes all the checks! Accept the name!
							break;
						}
						// Something caught the attention of the filter 
						filterFail++;
					}
					else {
						repeatedChar++; // Detected three of the same letter in a row.
					}
					
				}
				// Now ensure that a two-letter name isn't the same letter twice.
				else if (rootName.length() == 2) {
					if ( rootName.toLowerCase().charAt(0) != rootName.toLowerCase().charAt(1) ) {
						// Passes all the checks! Accept the name!
		    			break;
					}
				}
				else if (rootName.length() > 0) {
					sizeUnderflow++; // Root name is too short.
				}
				else blankRoot++; // Root name is blank.
			}
			else { // Root name is too long.
				sizeOverflow++;
			}
			
			// Step 2X
			// If we counted too many invalid name attempts, throw an exception
			if (sizeOverflow>=tooManyFailures) {
				String errorMessage = "Name type " + nameType +" names are too long! Check your syllable lengths.";
				LogHelper.fatal(errorMessage);
				throw new RuntimeException(errorMessage);
				//r_prefix = rootName = r_suffix = "";
				//break;
				}
			if (sizeUnderflow>=tooManyFailures) {
				String errorMessage = "Name type " + nameType +" names are too short! Check your syllables configs.";
				LogHelper.fatal(errorMessage);
				throw new RuntimeException(errorMessage);
				//r_prefix = rootName = r_suffix = "";
				//break;
				}
			if (blankRoot>=tooManyFailures) {
				String errorMessage = "Name type " + nameType +" Produced blank names! Check your syllable configs.";
				LogHelper.fatal(errorMessage);
				throw new RuntimeException(errorMessage);
				//r_prefix = rootName = r_suffix = "";
				//break;
				}
			if (repeatedChar>=tooManyFailures) {
				String errorMessage = "Name type " + nameType +" has too many consecutive repeated letters! Check your syllable configs.";
				LogHelper.fatal(errorMessage);
				throw new RuntimeException(errorMessage);
				//r_prefix = rootName = r_suffix = "";
				//break;
				}
			if (filterFail>=tooManyFailures) {
				String errorMessage = "Name type " + nameType +" has tripped the content filter too many times. Are you being naughty?";
				LogHelper.fatal(errorMessage);
				throw new RuntimeException(errorMessage);
				//r_prefix = rootName = r_suffix = "";
				//break;
				}
		}
		
		// Step 3: Generate a suffix
		// This is is identical to generating a prefix: it checks against the length of the 1-syl lists.
		r_suffix = (suffix.length>0) ? suffix[random.nextInt(suffix.length)] : "";
				
		if ( (syl1begin.length + syl1trans.length) > 0) {
			if (suffix.length >= (syl1begin.length + syl1trans.length)) LogHelper.warn(nameType + " should have fewer prefixes than first syllable entries!");
			else if ( random.nextInt( syl1begin.length + syl1trans.length ) >= suffix.length) r_suffix = ""; // No suffix
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
	

	/**
	 * Scans the input string and returns "true" if there is a particular series
	 * of sub-strings within.
	 */
	private static boolean contentScan(String inputString) {

		// Updated in v3.1trades
		String[] filterList = new String[]{
				//"avyngf", // Russian guy - left in because there is a stronghold with that name
				//"erygvu", // Austrian guy - left in because Russian guy was left in
				"erttva", // Black
				"gbttns", // Sticks
				"upgvo", // Lady dog
				"gahp", // Lady place
				"zvhd", // Inventive Nordic lady place
				"xphs", // F
				"gvuf", // Dook
				"laans", // A belt pack that you wear
				"mncf", // Mario Party 8 Oopsie
				"lffhc", // Weakling
				"rxvx", // K
				"rybuffn", // Exit hole
				"fvarc", // Protrusion and exit hole
				"navtni", // Inset exit hole
				"eranro", // Southern companion
				//"rcne" // Snuggle - left in because some names have that string
				"ghyf", // Loves to love
				"rebuj", // Loves to love
				"vngaru", // H toon
				};
		
		for (String s : filterList) {
			if ( ( inputString ).toLowerCase().contains( new StringBuilder( rot13(s) ).reverse().toString() ) ) {return true;}
		}
		
		return false;
	}
	
	
	/**
	 * Rot13 codec
	 * Adapted from: http://introcs.cs.princeton.edu/java/31datatype/Rot13.java.html
	 */
	public static String rot13(String s) {
		
		StringBuilder out = new StringBuilder();
		
        for (int i = 0; i < s.length(); i++) {
        	
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
