package astrotibs.villagenames.name;

import java.util.Random;

import astrotibs.villagenames.config.EndCityConfigHandler;
import astrotibs.villagenames.config.FortressConfigHandler;
import astrotibs.villagenames.config.GeneralConfigHandler;
import astrotibs.villagenames.config.MansionConfigHandler;
import astrotibs.villagenames.config.MineshaftConfigHandler;
import astrotibs.villagenames.config.MonumentConfigHandler;
import astrotibs.villagenames.config.OtherModConfigHandler;
import astrotibs.villagenames.config.StrongholdConfigHandler;
import astrotibs.villagenames.config.TempleConfigHandler;
import astrotibs.villagenames.config.VillageConfigHandler;
import astrotibs.villagenames.config.VillagerConfigHandler;
import astrotibs.villagenames.handler.ItemEventHandler;

//The whole point of this thing is to be a separate class that generates the names.

public class NameGenerator {
	
	// The constructor
	public NameGenerator() {
	}
	
	public static int[] otherModIDs = OtherModConfigHandler.otherModIDs;//.config.get("Mapping Professions", "otherModIDs", new int[]{-1}, "").getIntList();
	public static String[] otherModProfessions = OtherModConfigHandler.otherModProfessions;//.config.getStringList("otherModProfessions", "Mapping Professions", new String[]{"Villager"}, "");

	static Random random = new Random();
	
	
	// Generate all the name pieces necessary for a village sign
	public static String[] newVillageName() {
		
		String[] prefix = VillageConfigHandler.prefix;
		String[] suffix = VillageConfigHandler.suffix;
		String[] oneSylBegin = VillageConfigHandler.oneSylBegin;
		String[] oneSylEnd = VillageConfigHandler.oneSylEnd;
		String[] syl1Trans = VillageConfigHandler.syl1Trans;
		String[] syl2Trans = VillageConfigHandler.syl2Trans;
		String[] syl2Term = VillageConfigHandler.syl2Term;
		String[] syl3Trans = VillageConfigHandler.syl3Trans;
		String[] syl3Term = VillageConfigHandler.syl3Term;
		String[] syl4Trans = VillageConfigHandler.syl4Trans;
		String[] syl4Term = VillageConfigHandler.syl4Term;
		String[] syl5Trans = VillageConfigHandler.syl5Trans;
		String[] syl5Term = VillageConfigHandler.syl5Term;
		String[] syl6Trans = VillageConfigHandler.syl6Trans;
		String[] syl6Term = VillageConfigHandler.syl6Term;
		String[] syl7Term = VillageConfigHandler.syl7Term;
		String headerTags = GeneralConfigHandler.headerTags;
		
		// Step 1: generate prefix.
		// Step 1a: get number of all possible name bases. This should be length of all one-syl words and first syllables.
		int numChoices = 0;
		int indx;
		
		numChoices = oneSylBegin.length + syl1Trans.length; // Number of possible name bases
		indx = random.nextInt(numChoices);
		String namePrefix = "";
		if (indx < prefix.length) {
			// Add a prefix!
			namePrefix = prefix[indx];
		}
		
		String nameRoot; // I need to declare it here in order to place it onto the sign
		
		while (true) {
			
    		// Step 2: Generate a proper (root) name.
    		// Step 2.1: Generate the first syllable
			nameRoot = "";
			numChoices = oneSylBegin.length + syl1Trans.length; // Number of possible name bases
    		indx = random.nextInt(numChoices);
    		if (indx < syl1Trans.length) {
    			// This will be a multi-syllable compound name
    			nameRoot = syl1Trans[indx];
    			
    			// Step 2.2: Add second syllable
    			numChoices = syl2Trans.length + syl2Term.length; // Number of possible second syllables
    			indx = random.nextInt(numChoices);
    			if (indx < syl2Trans.length) {
    				nameRoot += syl2Trans[indx];
    				
    				// Step 2.3: Add a third syllable
        			numChoices = syl3Trans.length + syl3Term.length; // Number of possible third syllables
        			indx = random.nextInt(numChoices);
        			if (indx < syl3Trans.length) {
        				nameRoot += syl3Trans[indx];
        				
        				// Step 2.4: Add a fourth syllable
            			numChoices = syl4Trans.length + syl4Term.length; // Number of possible fourth syllables
            			indx = random.nextInt(numChoices);
            			if (indx < syl4Trans.length) {
            				nameRoot += syl4Trans[indx];
            				
            				// Step 2.5: Add a fifth syllable
            				numChoices = syl5Trans.length + syl5Term.length; // Number of possible fifth syllables
                			indx = random.nextInt(numChoices);
                			if (indx < syl5Trans.length) {
                				nameRoot += syl5Trans[indx];
                				
                				// Step 2.6: Add a sixth syllable
                				numChoices = syl6Trans.length + syl6Term.length; // Number of possible sixth syllables
                    			indx = random.nextInt(numChoices);
                    			if (indx < syl6Trans.length) {
                    				nameRoot += syl6Trans[indx];
                    				
                    				// Step 2.7: Add the seventh and final syllable.
                    				indx = random.nextInt(syl7Term.length);
                    				nameRoot += syl7Term[indx];
                    			}
                    			else {
                    				// End the word with six syllables.
                    				nameRoot += syl6Term[indx-syl6Trans.length];
                    			}
                			}
                			else {
                				// End the word with five syllables.
                				nameRoot += syl5Term[indx-syl5Trans.length];
                			}
            			}
            			else {
            				// End the word with four syllables.
            				nameRoot += syl4Term[indx-syl4Trans.length];
            			}
        			}
        			else {
        				// End the word with three syllables.
        				nameRoot += syl3Term[indx-syl3Trans.length];
        			}
    			}
    			else {
    				// End the word with two syllables.
    				nameRoot += syl2Term[indx-syl2Trans.length];
    			}
    		}
    		else {
    			// This will be a one-syllable name.
    			nameRoot = oneSylBegin[indx-syl1Trans.length];
    			indx = random.nextInt(oneSylBegin.length);
    			if (indx < oneSylEnd.length) {
    				// Add a second half to the syllable
    				nameRoot += oneSylEnd[indx];
    			}
    		}
    		
    		// I have to reject this (root) name if it's above 15 characters or below 2.
    		// Also I should ensure the last three characters are not all the same
    		if (
    				nameRoot.length() <= 15 && 
    				nameRoot.length() >= 3
    			) {
    			// Now, make sure the same characters don't appear in the name three times in a row
    			char[] nameRootArray  = nameRoot.toLowerCase().toCharArray();
    			int consecutives = 0;
    			for(int ci = 0; ci < nameRootArray.length-2; ci++) {
    				if (nameRootArray[ci] == nameRootArray[ci+1] && nameRootArray[ci] == nameRootArray[ci+2]) {
    					consecutives++;
    				}
    			}
    			if (consecutives == 0) {
					// Accept the name!
        			break;
				}
    		}
    		// Now ensure that a two-letter name isn't the same letter twice.
    		else if (nameRoot.length() == 2) {
    			if ( nameRoot.toLowerCase().charAt(0) != nameRoot.toLowerCase().charAt(1) ) {
    				// Accept the name!
        			break;
    			}
    		}
    		//LogHelper.info("Gotta go round again...");
		}
    		
		// Step 3: generate suffix.
		numChoices = oneSylBegin.length + syl1Trans.length; // Number of possible name bases
		indx = random.nextInt(numChoices);
		String nameSuffix = "";
		if (indx < suffix.length) {
			// Add a suffix!
			nameSuffix = suffix[indx];
		}
		
		//Should probably replace the carots here
		headerTags = headerTags.trim(); // Just in case some idiot added spaces
		
		namePrefix = namePrefix.replaceAll("\\^", " ");
		nameRoot = nameRoot.replaceAll("\\^", " ");
		nameSuffix = nameSuffix.replaceAll("\\^", " ");
		
		String[] nameStringArray = {headerTags, namePrefix, nameRoot, nameSuffix};
		
		return nameStringArray;
	}
	
	
	// This generates a name for a villager
	public static String newVillagerName() {
		
		String[] villager_oneSylBegin = VillagerConfigHandler.villager_oneSylBegin;
		String[] villager_oneSylEnd = VillagerConfigHandler.villager_oneSylEnd;
		String[] villager_syl1Trans = VillagerConfigHandler.villager_syl1Trans;
		String[] villager_syl2Trans = VillagerConfigHandler.villager_syl2Trans;
		String[] villager_syl2Term = VillagerConfigHandler.villager_syl2Term;
		String[] villager_syl3Trans = VillagerConfigHandler.villager_syl3Trans;
		String[] villager_syl3Term = VillagerConfigHandler.villager_syl3Term;
		String[] villager_syl4Trans = VillagerConfigHandler.villager_syl4Trans;
		String[] villager_syl4Term = VillagerConfigHandler.villager_syl4Term;
		String[] villager_syl5Term = VillagerConfigHandler.villager_syl5Term;
		
		int i;
		String nameRoot; // I need to declare it here in order to place it onto the sign
		
		while (true) {
			
    		// Step 2: Generate a proper (root) name.
    		// Step 2.1: Generate the first syllable
			nameRoot = "";
			
			int numChoices = villager_oneSylBegin.length + villager_syl1Trans.length; // Number of possible name bases
    		i = random.nextInt(numChoices);
    		if (i < villager_syl1Trans.length) {
    			// This will be a multi-syllable compound name
    			nameRoot = villager_syl1Trans[i];
    			
    			// Step 2.2: Add second syllable
    			numChoices = villager_syl2Trans.length + villager_syl2Term.length; // Number of possible second syllables
    			i = random.nextInt(numChoices);
    			if (i < villager_syl2Trans.length) {
    				nameRoot += villager_syl2Trans[i];
    				
    				// Step 2.3: Add a third syllable
        			numChoices = villager_syl3Trans.length + villager_syl3Term.length; // Number of possible third syllables
        			i = random.nextInt(numChoices);
        			if (i < villager_syl3Trans.length) {
        				nameRoot += villager_syl3Trans[i];
        				
        				// Step 2.4: Add a fourth syllable
            			numChoices = villager_syl4Trans.length + villager_syl4Term.length; // Number of possible fourth syllables
            			i = random.nextInt(numChoices);
            			if (i < villager_syl4Trans.length) {
            				nameRoot += villager_syl4Trans[i];
            				
            				// Step 2.5: Add the fifth and final syllable
                			i = random.nextInt(villager_syl5Term.length);
                			nameRoot += villager_syl5Term[i];
            			}
            			else {
            				// End the word with four syllables.
            				nameRoot += villager_syl4Term[i-villager_syl4Trans.length];
            			}
        			}
        			else {
        				// End the word with three syllables.
        				nameRoot += villager_syl3Term[i-villager_syl3Trans.length];
        			}
    			}
    			else {
    				// End the word with two syllables.
    				nameRoot += villager_syl2Term[i-villager_syl2Trans.length];
    			}
    		}
    		else {
    			// This will be a one-syllable name.
    			nameRoot = villager_oneSylBegin[i-villager_syl1Trans.length];
    			i = random.nextInt(villager_oneSylBegin.length);
    			if (i < villager_oneSylEnd.length) {
    				// Add a second half to the syllable
    				nameRoot += villager_oneSylEnd[i];
    			}
    		}
    		
    		nameRoot = nameRoot.replaceAll("\\^", " ");
    		
    		// I have to reject this (root) name if it's above 15 characters or below 2.
    		// Also I should ensure the last three characters are not all the same
    		if (
    				nameRoot.length() <= 15 && 
    				nameRoot.length() >= 3
    			) {
    			// Now, make sure the same characters don't appear in the name three times in a row
    			char[] nameRootArray  = nameRoot.toLowerCase().toCharArray();
    			int consecutives = 0;
    			for(int ci = 0; ci < nameRootArray.length-2; ci++) {
    				if (nameRootArray[ci] == nameRootArray[ci+1] && nameRootArray[ci] == nameRootArray[ci+2]) {
    					consecutives++;
    				}
    			}
    			if (consecutives == 0) {
					// Accept the name!
        			break;
				}
    		}
    		// Now ensure that a two-letter name isn't the same letter twice.
    		else if (nameRoot.length() == 2) {
    			if ( nameRoot.toLowerCase().charAt(0) != nameRoot.toLowerCase().charAt(1) ) {
    				// Accept the name!
        			break;
    			}
    		}
		}
		return nameRoot;
	}
	
	
	// Generate all the name pieces necessary for a mineshaft
	public static String[] newMineshaftName() {
		
		String[] mineshaft_prefix = MineshaftConfigHandler.mineshaft_prefix;
		String[] mineshaft_suffix = MineshaftConfigHandler.mineshaft_suffix;
		String[] mineshaft_oneSylBegin = MineshaftConfigHandler.mineshaft_oneSylBegin;
		String[] mineshaft_oneSylEnd = MineshaftConfigHandler.mineshaft_oneSylEnd;
		String[] mineshaft_syl1Trans = MineshaftConfigHandler.mineshaft_syl1Trans;
		String[] mineshaft_syl2Trans = MineshaftConfigHandler.mineshaft_syl2Trans;
		String[] mineshaft_syl2Term = MineshaftConfigHandler.mineshaft_syl2Term;
		String[] mineshaft_syl3Trans = MineshaftConfigHandler.mineshaft_syl3Trans;
		String[] mineshaft_syl3Term = MineshaftConfigHandler.mineshaft_syl3Term;
		String[] mineshaft_syl4Trans = MineshaftConfigHandler.mineshaft_syl4Trans;
		String[] mineshaft_syl4Term = MineshaftConfigHandler.mineshaft_syl4Term;
		String[] mineshaft_syl5Term = MineshaftConfigHandler.mineshaft_syl5Term;
		
		// Step 1: generate prefix.
		// Step 1a: get number of all possible name bases. This should be length of all one-syl words and first syllables.
		int numChoices = 0;
		int indx;
		
		numChoices = mineshaft_oneSylBegin.length + mineshaft_syl1Trans.length; // Number of possible name bases
		indx = random.nextInt(numChoices);
		String namePrefix = "";
		if (indx < mineshaft_prefix.length) {
			// Add a prefix!
			namePrefix = mineshaft_prefix[indx];
		}
		
		String nameRoot; // I need to declare it here in order to place it onto the sign
		
		while (true) {
			
			// Step 2: Generate a proper (root) name.
			// Step 2.1: Generate the first syllable
			nameRoot = "";
			numChoices = mineshaft_oneSylBegin.length + mineshaft_syl1Trans.length; // Number of possible name bases
			indx = random.nextInt(numChoices);
			if (indx < mineshaft_syl1Trans.length) {
				// This will be a multi-syllable compound name
				nameRoot = mineshaft_syl1Trans[indx];
				
				// Step 2.2: Add second syllable
				numChoices = mineshaft_syl2Trans.length + mineshaft_syl2Term.length; // Number of possible second syllables
				indx = random.nextInt(numChoices);
				if (indx < mineshaft_syl2Trans.length) {
					nameRoot += mineshaft_syl2Trans[indx];
					
					// Step 2.3: Add a third syllable
					numChoices = mineshaft_syl3Trans.length + mineshaft_syl3Term.length; // Number of possible third syllables
					indx = random.nextInt(numChoices);
					if (indx < mineshaft_syl3Trans.length) {
						nameRoot += mineshaft_syl3Trans[indx];
						
						// Step 2.4: Add a fourth syllable
						numChoices = mineshaft_syl4Trans.length + mineshaft_syl4Term.length; // Number of possible fourth syllables
						indx = random.nextInt(numChoices);
						if (indx < mineshaft_syl4Trans.length) {
							nameRoot += mineshaft_syl4Trans[indx];
							
							// Step 2.5: Add the fifth and final syllable
							indx = random.nextInt(mineshaft_syl5Term.length);
							nameRoot += mineshaft_syl5Term[indx];
						}
						else {
							// End the word with four syllables.
							nameRoot += mineshaft_syl4Term[indx-mineshaft_syl4Trans.length];
						}
					}
					else {
						// End the word with three syllables.
						nameRoot += mineshaft_syl3Term[indx-mineshaft_syl3Trans.length];
					}
				}
				else {
					// End the word with two syllables.
					nameRoot += mineshaft_syl2Term[indx-mineshaft_syl2Trans.length];
				}
			}
			else {
				// This will be a one-syllable name.
				nameRoot = mineshaft_oneSylBegin[indx-mineshaft_syl1Trans.length];
				indx = random.nextInt(mineshaft_oneSylBegin.length);
				if (indx < mineshaft_oneSylEnd.length) {
					// Add a second half to the syllable
					nameRoot += mineshaft_oneSylEnd[indx];
				}
			}
			
			// I have to reject this (root) name if it's above 15 characters or below 2.
			// Also I should ensure the last three characters are not all the same
			if (
					nameRoot.length() <= 15 && 
					nameRoot.length() >= 3
				) {
				// Now, make sure the same characters don't appear in the name three times in a row
				char[] nameRootArray  = nameRoot.toLowerCase().toCharArray();
				int consecutives = 0;
				for(int ci = 0; ci < nameRootArray.length-2; ci++) {
					if (nameRootArray[ci] == nameRootArray[ci+1] && nameRootArray[ci] == nameRootArray[ci+2]) {
						consecutives++;
					}
				}
				if (consecutives == 0) {
					// Accept the name!
					break;
				}
			}
			// Now ensure that a two-letter name isn't the same letter twice.
			else if (nameRoot.length() == 2) {
				if ( nameRoot.toLowerCase().charAt(0) != nameRoot.toLowerCase().charAt(1) ) {
					// Accept the name!
					break;
				}
			}
		}
			
		// Step 3: generate suffix.
		numChoices = mineshaft_oneSylBegin.length + mineshaft_syl1Trans.length; // Number of possible name bases
		indx = random.nextInt(numChoices);
		String nameSuffix = "";
		if (indx < mineshaft_suffix.length) {
			// Add a suffix!
			nameSuffix = mineshaft_suffix[indx];
		}
		
		//Should probably replace the carots here
		//mineshaft_headerTags = mineshaft_headerTags.trim(); // Just in case some idiot added spaces
		
		namePrefix = namePrefix.replaceAll("\\^", " ");
		nameRoot = nameRoot.replaceAll("\\^", " ");
		nameSuffix = nameSuffix.replaceAll("\\^", " ");
		
		String[] nameStringArray = {"", namePrefix, nameRoot, nameSuffix};
		
		return nameStringArray;
	}



	// Generate all the name pieces necessary for a stronghold
	public static String[] newStrongholdName() {
		
		String[] stronghold_prefix = StrongholdConfigHandler.stronghold_prefix;
		String[] stronghold_suffix = StrongholdConfigHandler.stronghold_suffix;
		String[] stronghold_oneSylBegin = StrongholdConfigHandler.stronghold_oneSylBegin;
		String[] stronghold_oneSylEnd = StrongholdConfigHandler.stronghold_oneSylEnd;
		String[] stronghold_syl1Trans = StrongholdConfigHandler.stronghold_syl1Trans;
		String[] stronghold_syl2Trans = StrongholdConfigHandler.stronghold_syl2Trans;
		String[] stronghold_syl2Term = StrongholdConfigHandler.stronghold_syl2Term;
		String[] stronghold_syl3Trans = StrongholdConfigHandler.stronghold_syl3Trans;
		String[] stronghold_syl3Term = StrongholdConfigHandler.stronghold_syl3Term;
		String[] stronghold_syl4Trans = StrongholdConfigHandler.stronghold_syl4Trans;
		String[] stronghold_syl4Term = StrongholdConfigHandler.stronghold_syl4Term;
		String[] stronghold_syl5Term = StrongholdConfigHandler.stronghold_syl5Term;
		
		// Step 1: generate prefix.
		// Step 1a: get number of all possible name bases. This should be length of all one-syl words and first syllables.
		int numChoices = 0;
		int indx;
		
		numChoices = stronghold_oneSylBegin.length + stronghold_syl1Trans.length; // Number of possible name bases
		indx = random.nextInt(numChoices);
		String namePrefix = "";
		if (indx < stronghold_prefix.length) {
			// Add a prefix!
			namePrefix = stronghold_prefix[indx];
		}
		
		String nameRoot; // I need to declare it here in order to place it onto the sign
		
		while (true) {
			
			// Step 2: Generate a proper (root) name.
			// Step 2.1: Generate the first syllable
			nameRoot = "";
			numChoices = stronghold_oneSylBegin.length + stronghold_syl1Trans.length; // Number of possible name bases
			indx = random.nextInt(numChoices);
			if (indx < stronghold_syl1Trans.length) {
				// This will be a multi-syllable compound name
				nameRoot = stronghold_syl1Trans[indx];
				
				// Step 2.2: Add second syllable
				numChoices = stronghold_syl2Trans.length + stronghold_syl2Term.length; // Number of possible second syllables
				indx = random.nextInt(numChoices);
				if (indx < stronghold_syl2Trans.length) {
					nameRoot += stronghold_syl2Trans[indx];
					
					// Step 2.3: Add a third syllable
					numChoices = stronghold_syl3Trans.length + stronghold_syl3Term.length; // Number of possible third syllables
					indx = random.nextInt(numChoices);
					if (indx < stronghold_syl3Trans.length) {
						nameRoot += stronghold_syl3Trans[indx];
						
						// Step 2.4: Add a fourth syllable
						numChoices = stronghold_syl4Trans.length + stronghold_syl4Term.length; // Number of possible fourth syllables
						indx = random.nextInt(numChoices);
						if (indx < stronghold_syl4Trans.length) {
							nameRoot += stronghold_syl4Trans[indx];
							
							// Step 2.5: Add the fifth and final syllable
							indx = random.nextInt(stronghold_syl5Term.length);
							nameRoot += stronghold_syl5Term[indx];
						}
						else {
							// End the word with four syllables.
							nameRoot += stronghold_syl4Term[indx-stronghold_syl4Trans.length];
						}
					}
					else {
						// End the word with three syllables.
						nameRoot += stronghold_syl3Term[indx-stronghold_syl3Trans.length];
					}
				}
				else {
					// End the word with two syllables.
					nameRoot += stronghold_syl2Term[indx-stronghold_syl2Trans.length];
				}
			}
			else {
				// This will be a one-syllable name.
				nameRoot = stronghold_oneSylBegin[indx-stronghold_syl1Trans.length];
				indx = random.nextInt(stronghold_oneSylBegin.length);
				if (indx < stronghold_oneSylEnd.length) {
					// Add a second half to the syllable
					nameRoot += stronghold_oneSylEnd[indx];
				}
			}
			
			// I have to reject this (root) name if it's above 15 characters or below 2.
			// Also I should ensure the last three characters are not all the same
			if (
					nameRoot.length() <= 15 && 
					nameRoot.length() >= 3
				) {
				// Now, make sure the same characters don't appear in the name three times in a row
				char[] nameRootArray  = nameRoot.toLowerCase().toCharArray();
				int consecutives = 0;
				for(int ci = 0; ci < nameRootArray.length-2; ci++) {
					if (nameRootArray[ci] == nameRootArray[ci+1] && nameRootArray[ci] == nameRootArray[ci+2]) {
						consecutives++;
					}
				}
				if (consecutives == 0) {
					// Accept the name!
					break;
				}
			}
			// Now ensure that a two-letter name isn't the same letter twice.
			else if (nameRoot.length() == 2) {
				if ( nameRoot.toLowerCase().charAt(0) != nameRoot.toLowerCase().charAt(1) ) {
					// Accept the name!
					break;
				}
			}
		}
			
		// Step 3: generate suffix.
		numChoices = stronghold_oneSylBegin.length + stronghold_syl1Trans.length; // Number of possible name bases
		indx = random.nextInt(numChoices);
		String nameSuffix = "";
		if (indx < stronghold_suffix.length) {
			// Add a suffix!
			nameSuffix = stronghold_suffix[indx];
		}
		
		//Should probably replace the carots here
		//stronghold_headerTags = stronghold_headerTags.trim(); // Just in case some idiot added spaces
		
		namePrefix = namePrefix.replaceAll("\\^", " ");
		nameRoot = nameRoot.replaceAll("\\^", " ");
		nameSuffix = nameSuffix.replaceAll("\\^", " ");
		
		String[] nameStringArray = {"", namePrefix, nameRoot, nameSuffix};
		
		return nameStringArray;
	}



	// Generate all the name pieces necessary for a temple
	public static String[] newTempleName() {
		
		String[] temple_prefix = TempleConfigHandler.temple_prefix;
		String[] temple_suffix = TempleConfigHandler.temple_suffix;
		String[] temple_oneSylBegin = TempleConfigHandler.temple_oneSylBegin;
		String[] temple_oneSylEnd = TempleConfigHandler.temple_oneSylEnd;
		String[] temple_syl1Trans = TempleConfigHandler.temple_syl1Trans;
		String[] temple_syl2Trans = TempleConfigHandler.temple_syl2Trans;
		String[] temple_syl2Term = TempleConfigHandler.temple_syl2Term;
		String[] temple_syl3Trans = TempleConfigHandler.temple_syl3Trans;
		String[] temple_syl3Term = TempleConfigHandler.temple_syl3Term;
		String[] temple_syl4Trans = TempleConfigHandler.temple_syl4Trans;
		String[] temple_syl4Term = TempleConfigHandler.temple_syl4Term;
		String[] temple_syl5Term = TempleConfigHandler.temple_syl5Term;
		
		// Step 1: generate prefix.
		// Step 1a: get number of all possible name bases. This should be length of all one-syl words and first syllables.
		int numChoices = 0;
		int indx;
		
		numChoices = temple_oneSylBegin.length + temple_syl1Trans.length; // Number of possible name bases
		indx = random.nextInt(numChoices);
		String namePrefix = "";
		if (indx < temple_prefix.length) {
			// Add a prefix!
			namePrefix = temple_prefix[indx];
		}
		
		String nameRoot; // I need to declare it here in order to place it onto the sign
		
		while (true) {
			
			// Step 2: Generate a proper (root) name.
			// Step 2.1: Generate the first syllable
			nameRoot = "";
			numChoices = temple_oneSylBegin.length + temple_syl1Trans.length; // Number of possible name bases
			indx = random.nextInt(numChoices);
			if (indx < temple_syl1Trans.length) {
				// This will be a multi-syllable compound name
				nameRoot = temple_syl1Trans[indx];
				
				// Step 2.2: Add second syllable
				numChoices = temple_syl2Trans.length + temple_syl2Term.length; // Number of possible second syllables
				indx = random.nextInt(numChoices);
				if (indx < temple_syl2Trans.length) {
					nameRoot += temple_syl2Trans[indx];
					
					// Step 2.3: Add a third syllable
					numChoices = temple_syl3Trans.length + temple_syl3Term.length; // Number of possible third syllables
					indx = random.nextInt(numChoices);
					if (indx < temple_syl3Trans.length) {
						nameRoot += temple_syl3Trans[indx];
						
						// Step 2.4: Add a fourth syllable
						numChoices = temple_syl4Trans.length + temple_syl4Term.length; // Number of possible fourth syllables
						indx = random.nextInt(numChoices);
						if (indx < temple_syl4Trans.length) {
							nameRoot += temple_syl4Trans[indx];
							
							// Step 2.5: Add the fifth and final syllable
							indx = random.nextInt(temple_syl5Term.length);
							nameRoot += temple_syl5Term[indx];
						}
						else {
							// End the word with four syllables.
							nameRoot += temple_syl4Term[indx-temple_syl4Trans.length];
						}
					}
					else {
						// End the word with three syllables.
						nameRoot += temple_syl3Term[indx-temple_syl3Trans.length];
					}
				}
				else {
					// End the word with two syllables.
					nameRoot += temple_syl2Term[indx-temple_syl2Trans.length];
				}
			}
			else {
				// This will be a one-syllable name.
				nameRoot = temple_oneSylBegin[indx-temple_syl1Trans.length];
				indx = random.nextInt(temple_oneSylBegin.length);
				if (indx < temple_oneSylEnd.length) {
					// Add a second half to the syllable
					nameRoot += temple_oneSylEnd[indx];
				}
			}
			
			// I have to reject this (root) name if it's above 15 characters or below 2.
			// Also I should ensure the last three characters are not all the same
			if (
					nameRoot.length() <= 15 && 
					nameRoot.length() >= 3
				) {
				// Now, make sure the same characters don't appear in the name three times in a row
				char[] nameRootArray  = nameRoot.toLowerCase().toCharArray();
				int consecutives = 0;
				for(int ci = 0; ci < nameRootArray.length-2; ci++) {
					if (nameRootArray[ci] == nameRootArray[ci+1] && nameRootArray[ci] == nameRootArray[ci+2]) {
						consecutives++;
					}
				}
				if (consecutives == 0) {
					// Accept the name!
					break;
				}
			}
			// Now ensure that a two-letter name isn't the same letter twice.
			else if (nameRoot.length() == 2) {
				if ( nameRoot.toLowerCase().charAt(0) != nameRoot.toLowerCase().charAt(1) ) {
					// Accept the name!
					break;
				}
			}
		}
			
		// Step 3: generate suffix.
		numChoices = temple_oneSylBegin.length + temple_syl1Trans.length; // Number of possible name bases
		indx = random.nextInt(numChoices);
		String nameSuffix = "";
		if (indx < temple_suffix.length) {
			// Add a suffix!
			nameSuffix = temple_suffix[indx];
		}
		
		//Should probably replace the carots here
		//temple_headerTags = temple_headerTags.trim(); // Just in case some idiot added spaces
		
		namePrefix = namePrefix.replaceAll("\\^", " ");
		nameRoot = nameRoot.replaceAll("\\^", " ");
		nameSuffix = nameSuffix.replaceAll("\\^", " ");
		
		String[] nameStringArray = {"", namePrefix, nameRoot, nameSuffix};
		
		return nameStringArray;
	}
	
	
	// Generate all the name pieces necessary for a fortress
	public static String[] newFortressName() {
		
		String[] fortress_prefix = FortressConfigHandler.fortress_prefix;
		String[] fortress_suffix = FortressConfigHandler.fortress_suffix;
		String[] fortress_oneSylBegin = FortressConfigHandler.fortress_oneSylBegin;
		String[] fortress_oneSylEnd = FortressConfigHandler.fortress_oneSylEnd;
		String[] fortress_syl1Trans = FortressConfigHandler.fortress_syl1Trans;
		String[] fortress_syl2Trans = FortressConfigHandler.fortress_syl2Trans;
		String[] fortress_syl2Term = FortressConfigHandler.fortress_syl2Term;
		String[] fortress_syl3Trans = FortressConfigHandler.fortress_syl3Trans;
		String[] fortress_syl3Term = FortressConfigHandler.fortress_syl3Term;
		String[] fortress_syl4Trans = FortressConfigHandler.fortress_syl4Trans;
		String[] fortress_syl4Term = FortressConfigHandler.fortress_syl4Term;
		String[] fortress_syl5Term = FortressConfigHandler.fortress_syl5Term;
		
		// Step 1: generate prefix.
		// Step 1a: get number of all possible name bases. This should be length of all one-syl words and first syllables.
		int numChoices = 0;
		int indx;
		
		numChoices = fortress_oneSylBegin.length + fortress_syl1Trans.length; // Number of possible name bases
		indx = random.nextInt(numChoices);
		String namePrefix = "";
		if (indx < fortress_prefix.length) {
			// Add a prefix!
			namePrefix = fortress_prefix[indx];
		}
		
		String nameRoot; // I need to declare it here in order to place it onto the sign
		
		while (true) {
			
			// Step 2: Generate a proper (root) name.
			// Step 2.1: Generate the first syllable
			nameRoot = "";
			numChoices = fortress_oneSylBegin.length + fortress_syl1Trans.length; // Number of possible name bases
			indx = random.nextInt(numChoices);
			if (indx < fortress_syl1Trans.length) {
				// This will be a multi-syllable compound name
				nameRoot = fortress_syl1Trans[indx];
				
				// Step 2.2: Add second syllable
				numChoices = fortress_syl2Trans.length + fortress_syl2Term.length; // Number of possible second syllables
				indx = random.nextInt(numChoices);
				if (indx < fortress_syl2Trans.length) {
					nameRoot += fortress_syl2Trans[indx];
					
					// Step 2.3: Add a third syllable
					numChoices = fortress_syl3Trans.length + fortress_syl3Term.length; // Number of possible third syllables
					indx = random.nextInt(numChoices);
					if (indx < fortress_syl3Trans.length) {
						nameRoot += fortress_syl3Trans[indx];
						
						// Step 2.4: Add a fourth syllable
						numChoices = fortress_syl4Trans.length + fortress_syl4Term.length; // Number of possible fourth syllables
						indx = random.nextInt(numChoices);
						if (indx < fortress_syl4Trans.length) {
							nameRoot += fortress_syl4Trans[indx];
							
							// Step 2.5: Add the fifth and final syllable
							indx = random.nextInt(fortress_syl5Term.length);
							nameRoot += fortress_syl5Term[indx];
						}
						else {
							// End the word with four syllables.
							nameRoot += fortress_syl4Term[indx-fortress_syl4Trans.length];
						}
					}
					else {
						// End the word with three syllables.
						nameRoot += fortress_syl3Term[indx-fortress_syl3Trans.length];
					}
				}
				else {
					// End the word with two syllables.
					nameRoot += fortress_syl2Term[indx-fortress_syl2Trans.length];
				}
			}
			else {
				// This will be a one-syllable name.
				nameRoot = fortress_oneSylBegin[indx-fortress_syl1Trans.length];
				indx = random.nextInt(fortress_oneSylBegin.length);
				if (indx < fortress_oneSylEnd.length) {
					// Add a second half to the syllable
					nameRoot += fortress_oneSylEnd[indx];
				}
			}
			
			// I have to reject this (root) name if it's above 15 characters or below 2.
			// Also I should ensure the last three characters are not all the same
			if (
					nameRoot.length() <= 15 && 
					nameRoot.length() >= 3
				) {
				// Now, make sure the same characters don't appear in the name three times in a row
				char[] nameRootArray  = nameRoot.toLowerCase().toCharArray();
				int consecutives = 0;
				for(int ci = 0; ci < nameRootArray.length-2; ci++) {
					if (nameRootArray[ci] == nameRootArray[ci+1] && nameRootArray[ci] == nameRootArray[ci+2]) {
						consecutives++;
					}
				}
				if (consecutives == 0) {
					// Accept the name!
					break;
				}
			}
			// Now ensure that a two-letter name isn't the same letter twice.
			else if (nameRoot.length() == 2) {
				if ( nameRoot.toLowerCase().charAt(0) != nameRoot.toLowerCase().charAt(1) ) {
					// Accept the name!
					break;
				}
			}
		}
			
		// Step 3: generate suffix.
		numChoices = fortress_oneSylBegin.length + fortress_syl1Trans.length; // Number of possible name bases
		indx = random.nextInt(numChoices);
		String nameSuffix = "";
		if (indx < fortress_suffix.length) {
			// Add a suffix!
			nameSuffix = fortress_suffix[indx];
		}
		
		//Should probably replace the carots here
		//fortress_headerTags = fortress_headerTags.trim(); // Just in case some idiot added spaces
		
		namePrefix = namePrefix.replaceAll("\\^", " ");
		nameRoot = nameRoot.replaceAll("\\^", " ");
		nameSuffix = nameSuffix.replaceAll("\\^", " ");
		
		String[] nameStringArray = {"", namePrefix, nameRoot, nameSuffix};
		
		return nameStringArray;
	}
	
	
	// Generate all the name pieces necessary for a monument
	public static String[] newMonumentName() {
		
		String[] monument_prefix = MonumentConfigHandler.monument_prefix;
		String[] monument_suffix = MonumentConfigHandler.monument_suffix;
		String[] monument_oneSylBegin = MonumentConfigHandler.monument_oneSylBegin;
		String[] monument_oneSylEnd = MonumentConfigHandler.monument_oneSylEnd;
		String[] monument_syl1Trans = MonumentConfigHandler.monument_syl1Trans;
		String[] monument_syl2Trans = MonumentConfigHandler.monument_syl2Trans;
		String[] monument_syl2Term = MonumentConfigHandler.monument_syl2Term;
		String[] monument_syl3Trans = MonumentConfigHandler.monument_syl3Trans;
		String[] monument_syl3Term = MonumentConfigHandler.monument_syl3Term;
		String[] monument_syl4Trans = MonumentConfigHandler.monument_syl4Trans;
		String[] monument_syl4Term = MonumentConfigHandler.monument_syl4Term;
		String[] monument_syl5Trans = MonumentConfigHandler.monument_syl5Trans;
		String[] monument_syl5Term = MonumentConfigHandler.monument_syl5Term;
		String[] monument_syl6Term = MonumentConfigHandler.monument_syl6Term;
		
		// Step 1: generate prefix.
		// Step 1a: get number of all possible name bases. This should be length of all one-syl words and first syllables.
		int numChoices = 0;
		int indx;
		
		numChoices = monument_oneSylBegin.length + monument_syl1Trans.length; // Number of possible name bases
		indx = random.nextInt(numChoices);
		String namePrefix = "";
		if (indx < monument_prefix.length) {
			// Add a prefix!
			namePrefix = monument_prefix[indx];
		}
		
		String nameRoot; // I need to declare it here in order to place it onto the sign
		
		while (true) {
			
			// Step 2: Generate a proper (root) name.
			// Step 2.1: Generate the first syllable
			nameRoot = "";
			numChoices = monument_oneSylBegin.length + monument_syl1Trans.length; // Number of possible name bases
			indx = random.nextInt(numChoices);
			if (indx < monument_syl1Trans.length) {
				// This will be a multi-syllable compound name
				nameRoot = monument_syl1Trans[indx];
				
				// Step 2.2: Add second syllable
				numChoices = monument_syl2Trans.length + monument_syl2Term.length; // Number of possible second syllables
				indx = random.nextInt(numChoices);
				if (indx < monument_syl2Trans.length) {
					nameRoot += monument_syl2Trans[indx];
					
					// Step 2.3: Add a third syllable
					numChoices = monument_syl3Trans.length + monument_syl3Term.length; // Number of possible third syllables
					indx = random.nextInt(numChoices);
					if (indx < monument_syl3Trans.length) {
						nameRoot += monument_syl3Trans[indx];
						
						// Step 2.4: Add a fourth syllable
						numChoices = monument_syl4Trans.length + monument_syl4Term.length; // Number of possible fourth syllables
						indx = random.nextInt(numChoices);
						if (indx < monument_syl4Trans.length) {
							nameRoot += monument_syl4Trans[indx];
							
							// Step 2.5: Add a fifth syllable
							numChoices = monument_syl5Trans.length + monument_syl5Term.length; // Number of possible fifth syllables
							indx = random.nextInt(numChoices);
							if (indx < monument_syl5Trans.length) {
								nameRoot += monument_syl5Trans[indx];
								
								// Step 2.6: Add the sixth and final syllable
								indx = random.nextInt(monument_syl6Term.length);
								nameRoot += monument_syl6Term[indx];
							}
						}
						else {
							// End the word with four syllables.
							nameRoot += monument_syl4Term[indx-monument_syl4Trans.length];
						}
					}
					else {
						// End the word with three syllables.
						nameRoot += monument_syl3Term[indx-monument_syl3Trans.length];
					}
				}
				else {
					// End the word with two syllables.
					nameRoot += monument_syl2Term[indx-monument_syl2Trans.length];
				}
			}
			else {
				// This will be a one-syllable name.
				nameRoot = monument_oneSylBegin[indx-monument_syl1Trans.length];
				indx = random.nextInt(monument_oneSylBegin.length);
				if (indx < monument_oneSylEnd.length) {
					// Add a second half to the syllable
					nameRoot += monument_oneSylEnd[indx];
				}
			}
			
			// I have to reject this (root) name if it's above 15 characters or below 2.
			// Also I should ensure the last three characters are not all the same
			if (
					nameRoot.length() <= 15 && 
					nameRoot.length() >= 3
				) {
				// Now, make sure the same characters don't appear in the name three times in a row
				char[] nameRootArray  = nameRoot.toLowerCase().toCharArray();
				int consecutives = 0;
				for(int ci = 0; ci < nameRootArray.length-2; ci++) {
					if (nameRootArray[ci] == nameRootArray[ci+1] && nameRootArray[ci] == nameRootArray[ci+2]) {
						consecutives++;
					}
				}
				if (consecutives == 0) {
					// Accept the name!
					break;
				}
			}
			// Now ensure that a two-letter name isn't the same letter twice.
			else if (nameRoot.length() == 2) {
				if ( nameRoot.toLowerCase().charAt(0) != nameRoot.toLowerCase().charAt(1) ) {
					// Accept the name!
					break;
				}
			}
		}
			
		// Step 3: generate suffix.
		numChoices = monument_oneSylBegin.length + monument_syl1Trans.length; // Number of possible name bases
		indx = random.nextInt(numChoices);
		String nameSuffix = "";
		if (indx < monument_suffix.length) {
			// Add a suffix!
			nameSuffix = monument_suffix[indx];
		}
		
		//Should probably replace the carots here
		//monument_headerTags = monument_headerTags.trim(); // Just in case some idiot added spaces
		
		namePrefix = namePrefix.replaceAll("\\^", " ");
		nameRoot = nameRoot.replaceAll("\\^", " ");
		nameSuffix = nameSuffix.replaceAll("\\^", " ");
		
		String[] nameStringArray = {"", namePrefix, nameRoot, nameSuffix};
		
		return nameStringArray;
	}
	
	
	
	// Generate all the name pieces necessary for an end city
	public static String[] newEndCityName() {
		
		String[] endcity_prefix = EndCityConfigHandler.endcity_prefix;
		String[] endcity_suffix = EndCityConfigHandler.endcity_suffix;
		String[] endcity_oneSylBegin = EndCityConfigHandler.endcity_oneSylBegin;
		String[] endcity_oneSylEnd = EndCityConfigHandler.endcity_oneSylEnd;
		String[] endcity_syl1Trans = EndCityConfigHandler.endcity_syl1Trans;
		String[] endcity_syl2Trans = EndCityConfigHandler.endcity_syl2Trans;
		String[] endcity_syl2Term = EndCityConfigHandler.endcity_syl2Term;
		String[] endcity_syl3Trans = EndCityConfigHandler.endcity_syl3Trans;
		String[] endcity_syl3Term = EndCityConfigHandler.endcity_syl3Term;
		String[] endcity_syl4Trans = EndCityConfigHandler.endcity_syl4Trans;
		String[] endcity_syl4Term = EndCityConfigHandler.endcity_syl4Term;
		String[] endcity_syl5Term = EndCityConfigHandler.endcity_syl5Term;
		
		// Step 1: generate prefix.
		// Step 1a: get number of all possible name bases. This should be length of all one-syl words and first syllables.
		int numChoices = 0;
		int indx;
		
		numChoices = endcity_oneSylBegin.length + endcity_syl1Trans.length; // Number of possible name bases
		indx = random.nextInt(numChoices);
		String namePrefix = "";
		if (indx < endcity_prefix.length) {
			// Add a prefix!
			namePrefix = endcity_prefix[indx];
		}
		
		String nameRoot; // I need to declare it here in order to place it onto the sign
		
		while (true) {
			
			// Step 2: Generate a proper (root) name.
			// Step 2.1: Generate the first syllable
			nameRoot = "";
			numChoices = endcity_oneSylBegin.length + endcity_syl1Trans.length; // Number of possible name bases
			indx = random.nextInt(numChoices);
			if (indx < endcity_syl1Trans.length) {
				// This will be a multi-syllable compound name
				nameRoot = endcity_syl1Trans[indx];
				
				// Step 2.2: Add second syllable
				numChoices = endcity_syl2Trans.length + endcity_syl2Term.length; // Number of possible second syllables
				indx = random.nextInt(numChoices);
				if (indx < endcity_syl2Trans.length) {
					nameRoot += endcity_syl2Trans[indx];
					
					// Step 2.3: Add a third syllable
					numChoices = endcity_syl3Trans.length + endcity_syl3Term.length; // Number of possible third syllables
					indx = random.nextInt(numChoices);
					if (indx < endcity_syl3Trans.length) {
						nameRoot += endcity_syl3Trans[indx];
						
						// Step 2.4: Add a fourth syllable
						numChoices = endcity_syl4Trans.length + endcity_syl4Term.length; // Number of possible fourth syllables
						indx = random.nextInt(numChoices);
						if (indx < endcity_syl4Trans.length) {
							nameRoot += endcity_syl4Trans[indx];
							
							// Step 2.5: Add the fifth and final syllable
							indx = random.nextInt(endcity_syl5Term.length);
							nameRoot += endcity_syl5Term[indx];
						}
						else {
							// End the word with four syllables.
							nameRoot += endcity_syl4Term[indx-endcity_syl4Trans.length];
						}
					}
					else {
						// End the word with three syllables.
						nameRoot += endcity_syl3Term[indx-endcity_syl3Trans.length];
					}
				}
				else {
					// End the word with two syllables.
					nameRoot += endcity_syl2Term[indx-endcity_syl2Trans.length];
				}
			}
			else {
				// This will be a one-syllable name.
				nameRoot = endcity_oneSylBegin[indx-endcity_syl1Trans.length];
				indx = random.nextInt(endcity_oneSylBegin.length);
				if (indx < endcity_oneSylEnd.length) {
					// Add a second half to the syllable
					nameRoot += endcity_oneSylEnd[indx];
				}
			}
			
			// I have to reject this (root) name if it's above 15 characters or below 2.
			// Also I should ensure the last three characters are not all the same
			if (
					nameRoot.length() <= 15 && 
					nameRoot.length() >= 3
				) {
				// Now, make sure the same characters don't appear in the name three times in a row
				char[] nameRootArray  = nameRoot.toLowerCase().toCharArray();
				int consecutives = 0;
				for(int ci = 0; ci < nameRootArray.length-2; ci++) {
					if (nameRootArray[ci] == nameRootArray[ci+1] && nameRootArray[ci] == nameRootArray[ci+2]) {
						consecutives++;
					}
				}
				if (consecutives == 0) {
					// Accept the name!
					break;
				}
			}
			// Now ensure that a two-letter name isn't the same letter twice.
			else if (nameRoot.length() == 2) {
				if ( nameRoot.toLowerCase().charAt(0) != nameRoot.toLowerCase().charAt(1) ) {
					// Accept the name!
					break;
				}
			}
		}
			
		// Step 3: generate suffix.
		numChoices = endcity_oneSylBegin.length + endcity_syl1Trans.length; // Number of possible name bases
		indx = random.nextInt(numChoices);
		String nameSuffix = "";
		if (indx < endcity_suffix.length) {
			// Add a suffix!
			nameSuffix = endcity_suffix[indx];
		}
		
		//Should probably replace the carots here
		//fortress_headerTags = fortress_headerTags.trim(); // Just in case some idiot added spaces
		
		namePrefix = namePrefix.replaceAll("\\^", " ");
		nameRoot = nameRoot.replaceAll("\\^", " ");
		nameSuffix = nameSuffix.replaceAll("\\^", " ");
		
		String[] nameStringArray = {"", namePrefix, nameRoot, nameSuffix};
		
		return nameStringArray;
	}
	
	
	
	// Generate all the name pieces necessary for a mansion
	public static String[] newMansionName() {
		
		String[] mansion_prefix = MansionConfigHandler.mansion_prefix;
		String[] mansion_suffix = MansionConfigHandler.mansion_suffix;
		String[] mansion_oneSylBegin = MansionConfigHandler.mansion_oneSylBegin;
		String[] mansion_oneSylEnd = MansionConfigHandler.mansion_oneSylEnd;
		String[] mansion_syl1Trans = MansionConfigHandler.mansion_syl1Trans;
		String[] mansion_syl2Trans = MansionConfigHandler.mansion_syl2Trans;
		String[] mansion_syl2Term = MansionConfigHandler.mansion_syl2Term;
		String[] mansion_syl3Trans = MansionConfigHandler.mansion_syl3Trans;
		String[] mansion_syl3Term = MansionConfigHandler.mansion_syl3Term;
		String[] mansion_syl4Trans = MansionConfigHandler.mansion_syl4Trans;
		String[] mansion_syl4Term = MansionConfigHandler.mansion_syl4Term;
		String[] mansion_syl5Term = MansionConfigHandler.mansion_syl5Term;
		
		// Step 1: generate prefix.
		// Step 1a: get number of all possible name bases. This should be length of all one-syl words and first syllables.
		int numChoices = 0;
		int indx;
		
		numChoices = mansion_oneSylBegin.length + mansion_syl1Trans.length; // Number of possible name bases
		indx = random.nextInt(numChoices);
		String namePrefix = "";
		if (indx < mansion_prefix.length) {
			// Add a prefix!
			namePrefix = mansion_prefix[indx];
		}
		
		String nameRoot; // I need to declare it here in order to place it onto the sign
		
		while (true) {
			
			// Step 2: Generate a proper (root) name.
			// Step 2.1: Generate the first syllable
			nameRoot = "";
			numChoices = mansion_oneSylBegin.length + mansion_syl1Trans.length; // Number of possible name bases
			indx = random.nextInt(numChoices);
			if (indx < mansion_syl1Trans.length) {
				// This will be a multi-syllable compound name
				nameRoot = mansion_syl1Trans[indx];
				
				// Step 2.2: Add second syllable
				numChoices = mansion_syl2Trans.length + mansion_syl2Term.length; // Number of possible second syllables
				indx = random.nextInt(numChoices);
				if (indx < mansion_syl2Trans.length) {
					nameRoot += mansion_syl2Trans[indx];
					
					// Step 2.3: Add a third syllable
					numChoices = mansion_syl3Trans.length + mansion_syl3Term.length; // Number of possible third syllables
					indx = random.nextInt(numChoices);
					if (indx < mansion_syl3Trans.length) {
						nameRoot += mansion_syl3Trans[indx];
						
						// Step 2.4: Add a fourth syllable
						numChoices = mansion_syl4Trans.length + mansion_syl4Term.length; // Number of possible fourth syllables
						indx = random.nextInt(numChoices);
						if (indx < mansion_syl4Trans.length) {
							nameRoot += mansion_syl4Trans[indx];
							
							// Step 2.5: Add the fifth and final syllable
							indx = random.nextInt(mansion_syl5Term.length);
							nameRoot += mansion_syl5Term[indx];
						}
						else {
							// End the word with four syllables.
							nameRoot += mansion_syl4Term[indx-mansion_syl4Trans.length];
						}
					}
					else {
						// End the word with three syllables.
						nameRoot += mansion_syl3Term[indx-mansion_syl3Trans.length];
					}
				}
				else {
					// End the word with two syllables.
					nameRoot += mansion_syl2Term[indx-mansion_syl2Trans.length];
				}
			}
			else {
				// This will be a one-syllable name.
				nameRoot = mansion_oneSylBegin[indx-mansion_syl1Trans.length];
				indx = random.nextInt(mansion_oneSylBegin.length);
				if (indx < mansion_oneSylEnd.length) {
					// Add a second half to the syllable
					nameRoot += mansion_oneSylEnd[indx];
				}
			}
			
			// I have to reject this (root) name if it's above 15 characters or below 2.
			// Also I should ensure the last three characters are not all the same
			if (
					nameRoot.length() <= 15 && 
					nameRoot.length() >= 3
				) {
				// Now, make sure the same characters don't appear in the name three times in a row
				char[] nameRootArray  = nameRoot.toLowerCase().toCharArray();
				int consecutives = 0;
				for(int ci = 0; ci < nameRootArray.length-2; ci++) {
					if (nameRootArray[ci] == nameRootArray[ci+1] && nameRootArray[ci] == nameRootArray[ci+2]) {
						consecutives++;
					}
				}
				if (consecutives == 0) {
					// Accept the name!
					break;
				}
			}
			// Now ensure that a two-letter name isn't the same letter twice.
			else if (nameRoot.length() == 2) {
				if ( nameRoot.toLowerCase().charAt(0) != nameRoot.toLowerCase().charAt(1) ) {
					// Accept the name!
					break;
				}
			}
		}
			
		// Step 3: generate suffix.
		numChoices = mansion_oneSylBegin.length + mansion_syl1Trans.length; // Number of possible name bases
		indx = random.nextInt(numChoices);
		String nameSuffix = "";
		if (indx < mansion_suffix.length) {
			// Add a suffix!
			nameSuffix = mansion_suffix[indx];
		}
		
		//Should probably replace the carots here
		//mansion_headerTags = mansion_headerTags.trim(); // Just in case some idiot added spaces
		
		namePrefix = namePrefix.replaceAll("\\^", " ");
		nameRoot = nameRoot.replaceAll("\\^", " ");
		nameSuffix = nameSuffix.replaceAll("\\^", " ");
		
		String[] nameStringArray = {"", namePrefix, nameRoot, nameSuffix};
		
		return nameStringArray;
	}
	
	
	// This generates a name for an alien villager
	public static String newAlienVillagerName() {
		
		String[] alienVillager_oneSylBegin = OtherModConfigHandler.alienVillager_oneSylBegin;
		String[] alienVillager_oneSylEnd = OtherModConfigHandler.alienVillager_oneSylEnd;
		String[] alienVillager_syl1Trans = OtherModConfigHandler.alienVillager_syl1Trans;
		String[] alienVillager_syl2Trans = OtherModConfigHandler.alienVillager_syl2Trans;
		String[] alienVillager_syl2Term = OtherModConfigHandler.alienVillager_syl2Term;
		String[] alienVillager_syl3Trans = OtherModConfigHandler.alienVillager_syl3Trans;
		String[] alienVillager_syl3Term = OtherModConfigHandler.alienVillager_syl3Term;
		String[] alienVillager_syl4Trans = OtherModConfigHandler.alienVillager_syl4Trans;
		String[] alienVillager_syl4Term = OtherModConfigHandler.alienVillager_syl4Term;
		String[] alienVillager_syl5Trans = OtherModConfigHandler.alienVillager_syl5Trans;
		String[] alienVillager_syl5Term = OtherModConfigHandler.alienVillager_syl5Term;
		String[] alienVillager_syl6Trans = OtherModConfigHandler.alienVillager_syl6Trans;
		String[] alienVillager_syl6Term = OtherModConfigHandler.alienVillager_syl6Term;
		String[] alienVillager_syl7Term = OtherModConfigHandler.alienVillager_syl7Term;
		
		// Step 1: generate prefix.
		// Step 1a: get number of all possible name bases. This should be length of all one-syl words and first syllables.
		int numChoices = 0;
		int indx;
		
		numChoices = alienVillager_oneSylBegin.length + alienVillager_syl1Trans.length; // Number of possible name bases
		indx = random.nextInt(numChoices);
		
		String nameRoot; // I need to declare it here in order to place it onto the sign
		
		while (true) {
			
    		// Step 2: Generate a proper (root) name.
    		// Step 2.1: Generate the first syllable
			nameRoot = "";
			numChoices = alienVillager_oneSylBegin.length + alienVillager_syl1Trans.length; // Number of possible name bases
    		indx = random.nextInt(numChoices);
    		if (indx < alienVillager_syl1Trans.length) {
    			// This will be a multi-syllable compound name
    			nameRoot = alienVillager_syl1Trans[indx];
    			
    			// Step 2.2: Add second syllable
    			numChoices = alienVillager_syl2Trans.length + alienVillager_syl2Term.length; // Number of possible second syllables
    			indx = random.nextInt(numChoices);
    			if (indx < alienVillager_syl2Trans.length) {
    				nameRoot += alienVillager_syl2Trans[indx];
    				
    				// Step 2.3: Add a third syllable
        			numChoices = alienVillager_syl3Trans.length + alienVillager_syl3Term.length; // Number of possible third syllables
        			indx = random.nextInt(numChoices);
        			if (indx < alienVillager_syl3Trans.length) {
        				nameRoot += alienVillager_syl3Trans[indx];
        				
        				// Step 2.4: Add a fourth syllable
            			numChoices = alienVillager_syl4Trans.length + alienVillager_syl4Term.length; // Number of possible fourth syllables
            			indx = random.nextInt(numChoices);
            			if (indx < alienVillager_syl4Trans.length) {
            				nameRoot += alienVillager_syl4Trans[indx];
            				
            				// Step 2.5: Add a fifth syllable
            				numChoices = alienVillager_syl5Trans.length + alienVillager_syl5Term.length; // Number of possible fifth syllables
                			indx = random.nextInt(numChoices);
                			if (indx < alienVillager_syl5Trans.length) {
                				nameRoot += alienVillager_syl5Trans[indx];
                				
                				// Step 2.6: Add a sixth syllable
                				numChoices = alienVillager_syl6Trans.length + alienVillager_syl6Term.length; // Number of possible sixth syllables
                    			indx = random.nextInt(numChoices);
                    			if (indx < alienVillager_syl6Trans.length) {
                    				nameRoot += alienVillager_syl6Trans[indx];
                    				
                    				// Step 2.7: Add the seventh and final syllable.
                    				indx = random.nextInt(alienVillager_syl7Term.length);
                    				nameRoot += alienVillager_syl7Term[indx];
                    			}
                    			else {
                    				// End the word with six syllables.
                    				nameRoot += alienVillager_syl6Term[indx-alienVillager_syl6Trans.length];
                    			}
                			}
                			else {
                				// End the word with five syllables.
                				nameRoot += alienVillager_syl5Term[indx-alienVillager_syl5Trans.length];
                			}
            			}
            			else {
            				// End the word with four syllables.
            				nameRoot += alienVillager_syl4Term[indx-alienVillager_syl4Trans.length];
            			}
        			}
        			else {
        				// End the word with three syllables.
        				nameRoot += alienVillager_syl3Term[indx-alienVillager_syl3Trans.length];
        			}
    			}
    			else {
    				// End the word with two syllables.
    				nameRoot += alienVillager_syl2Term[indx-alienVillager_syl2Trans.length];
    			}
    		}
    		else {
    			// This will be a one-syllable name.
    			nameRoot = alienVillager_oneSylBegin[indx-alienVillager_syl1Trans.length];
    			indx = random.nextInt(alienVillager_oneSylBegin.length);
    			if (indx < alienVillager_oneSylEnd.length) {
    				// Add a second half to the syllable
    				nameRoot += alienVillager_oneSylEnd[indx];
    			}
    		}
    		
    		// I have to reject this (root) name if it's above 15 characters or below 2.
    		// Also I should ensure the last three characters are not all the same
    		if (
    				nameRoot.length() <= 15 && 
    				nameRoot.length() >= 4
    			) {
    			
    			// Now, make sure the same characters don't appear in the name four times in a row
    			char[] nameRootArray  = nameRoot.toLowerCase().toCharArray();
    			int consecutives = 0;
    			for(int ci = 0; ci < nameRootArray.length-3; ci++) {
    				if (nameRootArray[ci] == nameRootArray[ci+1]
    						&& nameRootArray[ci] == nameRootArray[ci+2]
    						&& nameRootArray[ci] == nameRootArray[ci+3]) {
    					consecutives++;
    				}
    			}
    			if (consecutives == 0) {
					// Accept the name!
        			break;
				}
				
    			break;
    		}
    		// Now ensure that a three-letter name isn't the same letter thrice.
    		else if (nameRoot.length() == 3) {
    			if ( !(
    				 (nameRoot.toLowerCase().charAt(0) == nameRoot.toLowerCase().charAt(1)) &&
    				 (nameRoot.toLowerCase().charAt(0) == nameRoot.toLowerCase().charAt(2))
    				 ) ) {
    				// Accept the name!
        			break;
    			}
    		}
    		// Now ensure that a two-letter name isn't the same letter twice.
    		else if (nameRoot.length() == 2) {
    			if ( nameRoot.toLowerCase().charAt(0) != nameRoot.toLowerCase().charAt(1) ) {
    				// Accept the name!
        			break;
    			}
    		}
		}
		//Should probably replace the carots here
		nameRoot = nameRoot.replaceAll("\\^", " ");
		
		return nameRoot;
	}
	
	
	
	// Generate all the name pieces necessary for an alien village
	public static String[] newAlienVillageName() {
		
		String[] alienVillage_oneSylBegin = OtherModConfigHandler.alienVillage_oneSylBegin;
		String[] alienVillage_oneSylEnd = OtherModConfigHandler.alienVillage_oneSylEnd;
		String[] alienVillage_syl1Trans = OtherModConfigHandler.alienVillage_syl1Trans;
		String[] alienVillage_syl2Trans = OtherModConfigHandler.alienVillage_syl2Trans;
		String[] alienVillage_syl2Term = OtherModConfigHandler.alienVillage_syl2Term;
		String[] alienVillage_syl3Trans = OtherModConfigHandler.alienVillage_syl3Trans;
		String[] alienVillage_syl3Term = OtherModConfigHandler.alienVillage_syl3Term;
		String[] alienVillage_syl4Trans = OtherModConfigHandler.alienVillage_syl4Trans;
		String[] alienVillage_syl4Term = OtherModConfigHandler.alienVillage_syl4Term;
		String[] alienVillage_syl5Trans = OtherModConfigHandler.alienVillage_syl5Trans;
		String[] alienVillage_syl5Term = OtherModConfigHandler.alienVillage_syl5Term;
		String[] alienVillage_syl6Term = OtherModConfigHandler.alienVillage_syl6Term;
		
		// Step 1: generate prefix.
		// Step 1a: get number of all possible name bases. This should be length of all one-syl words and first syllables.
		int numChoices = 0;
		int indx;
		
		numChoices = alienVillage_oneSylBegin.length + alienVillage_syl1Trans.length; // Number of possible name bases
		indx = random.nextInt(numChoices);
		String namePrefix = "";
		
		String nameRoot; // I need to declare it here in order to place it onto the sign
		
		while (true) {
			
			// Step 2: Generate a proper (root) name.
			// Step 2.1: Generate the first syllable
			nameRoot = "";
			numChoices = alienVillage_oneSylBegin.length + alienVillage_syl1Trans.length; // Number of possible name bases
			indx = random.nextInt(numChoices);
			if (indx < alienVillage_syl1Trans.length) {
				// This will be a multi-syllable compound name
				nameRoot = alienVillage_syl1Trans[indx];
				
				// Step 2.2: Add second syllable
				numChoices = alienVillage_syl2Trans.length + alienVillage_syl2Term.length; // Number of possible second syllables
				indx = random.nextInt(numChoices);
				if (indx < alienVillage_syl2Trans.length) {
					nameRoot += alienVillage_syl2Trans[indx];
					
					// Step 2.3: Add a third syllable
					numChoices = alienVillage_syl3Trans.length + alienVillage_syl3Term.length; // Number of possible third syllables
					indx = random.nextInt(numChoices);
					if (indx < alienVillage_syl3Trans.length) {
						nameRoot += alienVillage_syl3Trans[indx];
						
						// Step 2.4: Add a fourth syllable
						numChoices = alienVillage_syl4Trans.length + alienVillage_syl4Term.length; // Number of possible fourth syllables
						indx = random.nextInt(numChoices);
						if (indx < alienVillage_syl4Trans.length) {
							nameRoot += alienVillage_syl4Trans[indx];
							
							// Step 2.5: Add a fifth syllable
							numChoices = alienVillage_syl5Trans.length + alienVillage_syl5Term.length; // Number of possible fifth syllables
							indx = random.nextInt(numChoices);
							if (indx < alienVillage_syl5Trans.length) {
								nameRoot += alienVillage_syl5Trans[indx];
								
								// Step 2.6: Add the sixth and final syllable
								indx = random.nextInt(alienVillage_syl6Term.length);
								nameRoot += alienVillage_syl6Term[indx];
							}
						}
						else {
							// End the word with four syllables.
							nameRoot += alienVillage_syl4Term[indx-alienVillage_syl4Trans.length];
						}
					}
					else {
						// End the word with three syllables.
						nameRoot += alienVillage_syl3Term[indx-alienVillage_syl3Trans.length];
					}
				}
				else {
					// End the word with two syllables.
					nameRoot += alienVillage_syl2Term[indx-alienVillage_syl2Trans.length];
				}
			}
			else {
				// This will be a one-syllable name.
				nameRoot = alienVillage_oneSylBegin[indx-alienVillage_syl1Trans.length];
				indx = random.nextInt(alienVillage_oneSylBegin.length);
				if (indx < alienVillage_oneSylEnd.length) {
					// Add a second half to the syllable
					nameRoot += alienVillage_oneSylEnd[indx];
				}
			}
			
			// I have to reject this (root) name if it's above 15 characters or below 2.
			// Also I should ensure the last three characters are not all the same
			if (
					nameRoot.length() <= 15 && 
					nameRoot.length() >= 3
				) {
				// Now, make sure the same characters don't appear in the name three times in a row
				char[] nameRootArray  = nameRoot.toLowerCase().toCharArray();
				int consecutives = 0;
				for(int ci = 0; ci < nameRootArray.length-2; ci++) {
					if (nameRootArray[ci] == nameRootArray[ci+1] && nameRootArray[ci] == nameRootArray[ci+2]) {
						consecutives++;
					}
				}
				if (consecutives == 0) {
					// Accept the name!
					break;
				}
			}
			// Now ensure that a two-letter name isn't the same letter twice.
			else if (nameRoot.length() == 2) {
				if ( nameRoot.toLowerCase().charAt(0) != nameRoot.toLowerCase().charAt(1) ) {
					// Accept the name!
					break;
				}
			}
		}
			
		// Step 3: generate suffix.
		String nameSuffix = "";
		
		//Should probably replace the carots here
		//monument_headerTags = monument_headerTags.trim(); // Just in case some idiot added spaces
		
		namePrefix = namePrefix.replaceAll("\\^", " ");
		nameRoot = nameRoot.replaceAll("\\^", " ");
		nameSuffix = nameSuffix.replaceAll("\\^", " ");
		
		String[] nameStringArray = {"", namePrefix, nameRoot, nameSuffix};
		
		return nameStringArray;
	}
	
	
	
	// This generates a name for a hobgoblin
	public static String newHobgoblinName() {
		
		String[] hobgoblin_oneSylBegin = OtherModConfigHandler.hobgoblin_oneSylBegin;
		String[] hobgoblin_oneSylEnd = OtherModConfigHandler.hobgoblin_oneSylEnd;
		String[] hobgoblin_syl1Trans = OtherModConfigHandler.hobgoblin_syl1Trans;
		String[] hobgoblin_syl2Trans = OtherModConfigHandler.hobgoblin_syl2Trans;
		String[] hobgoblin_syl2Term = OtherModConfigHandler.hobgoblin_syl2Term;
		String[] hobgoblin_syl3Trans = OtherModConfigHandler.hobgoblin_syl3Trans;
		String[] hobgoblin_syl3Term = OtherModConfigHandler.hobgoblin_syl3Term;
		String[] hobgoblin_syl4Trans = OtherModConfigHandler.hobgoblin_syl4Trans;
		String[] hobgoblin_syl4Term = OtherModConfigHandler.hobgoblin_syl4Term;
		String[] hobgoblin_syl5Term = OtherModConfigHandler.hobgoblin_syl5Term;
		
		int i;
		String nameRoot; // I need to declare it here in order to place it onto the sign
		
		while (true) {
			
    		// Step 2: Generate a proper (root) name.
    		// Step 2.1: Generate the first syllable
			nameRoot = "";
			
			int numChoices = hobgoblin_oneSylBegin.length + hobgoblin_syl1Trans.length; // Number of possible name bases
    		i = random.nextInt(numChoices);
    		if (i < hobgoblin_syl1Trans.length) {
    			// This will be a multi-syllable compound name
    			nameRoot = hobgoblin_syl1Trans[i];
    			
    			// Step 2.2: Add second syllable
    			numChoices = hobgoblin_syl2Trans.length + hobgoblin_syl2Term.length; // Number of possible second syllables
    			i = random.nextInt(numChoices);
    			if (i < hobgoblin_syl2Trans.length) {
    				nameRoot += hobgoblin_syl2Trans[i];
    				
    				// Step 2.3: Add a third syllable
        			numChoices = hobgoblin_syl3Trans.length + hobgoblin_syl3Term.length; // Number of possible third syllables
        			i = random.nextInt(numChoices);
        			if (i < hobgoblin_syl3Trans.length) {
        				nameRoot += hobgoblin_syl3Trans[i];
        				
        				// Step 2.4: Add a fourth syllable
            			numChoices = hobgoblin_syl4Trans.length + hobgoblin_syl4Term.length; // Number of possible fourth syllables
            			i = random.nextInt(numChoices);
            			if (i < hobgoblin_syl4Trans.length) {
            				nameRoot += hobgoblin_syl4Trans[i];
            				
            				// Step 2.5: Add the fifth and final syllable
                			i = random.nextInt(hobgoblin_syl5Term.length);
                			nameRoot += hobgoblin_syl5Term[i];
            			}
            			else {
            				// End the word with four syllables.
            				nameRoot += hobgoblin_syl4Term[i-hobgoblin_syl4Trans.length];
            			}
        			}
        			else {
        				// End the word with three syllables.
        				nameRoot += hobgoblin_syl3Term[i-hobgoblin_syl3Trans.length];
        			}
    			}
    			else {
    				// End the word with two syllables.
    				nameRoot += hobgoblin_syl2Term[i-hobgoblin_syl2Trans.length];
    			}
    		}
    		else {
    			// This will be a one-syllable name.
    			nameRoot = hobgoblin_oneSylBegin[i-hobgoblin_syl1Trans.length];
    			i = random.nextInt(hobgoblin_oneSylBegin.length);
    			if (i < hobgoblin_oneSylEnd.length) {
    				// Add a second half to the syllable
    				nameRoot += hobgoblin_oneSylEnd[i];
    			}
    		}
    		
    		nameRoot = nameRoot.replaceAll("\\^", " ");
    		
    		// I have to reject this (root) name if it's above 15 characters or below 2.
    		// Also I should ensure the last three characters are not all the same
    		if (
    				nameRoot.length() <= 15 && 
    				nameRoot.length() >= 3
    			) {
    			// Now, make sure the same characters don't appear in the name three times in a row
    			char[] nameRootArray  = nameRoot.toLowerCase().toCharArray();
    			int consecutives = 0;
    			for(int ci = 0; ci < nameRootArray.length-2; ci++) {
    				if (nameRootArray[ci] == nameRootArray[ci+1] && nameRootArray[ci] == nameRootArray[ci+2]) {
    					consecutives++;
    				}
    			}
    			if (consecutives == 0) {
					// Accept the name!
        			break;
				}
    		}
    		// Now ensure that a two-letter name isn't the same letter twice.
    		else if (nameRoot.length() == 2) {
    			if ( nameRoot.toLowerCase().charAt(0) != nameRoot.toLowerCase().charAt(1) ) {
    				// Accept the name!
        			break;
    			}
    		}
		}
		return nameRoot;
	}
	
	
	
	/**
	 * Generate a profession tag to append to their name
	 * @param villagerProfession: integer to represent profession (0 to 5)
	 * @param villagerCareer: integer to represent career (0 before 1.8; 1+ otherwise)
	 * @param nitwitProfession: name to assign to a nitwit (profession 5)
	 * @return
	 */
	public static String getCareerTag(int villagerProfession, int villagerCareer, String nitwitProfession) {
		
		String careerTag = "(";
		
		switch (villagerProfession) {
		case 0: // Farmer profession
			switch(villagerCareer) {
			case 1:
				careerTag += "Farmer";
				break;
			case 2:
				careerTag += "Fisherman";
				break;
			case 3:
				careerTag += "Shepherd";
				break;
			case 4:
				careerTag += "Fletcher";
				break;
			default:
				careerTag += "Farmer";
				break;
			}
			break;
		case 1: // Librarian profession
			switch(villagerCareer) {
			case 1:
				careerTag += "Librarian";
				break;
			case 2:
				careerTag += "Cartographer";
				break;
			default:
				careerTag += "Librarian";
				break;
			}
			break;
		case 2: // Priest profession
			switch(villagerCareer) {
			case 1:
				careerTag += "Cleric";
				break;
			default:
				careerTag += "Cleric";
				break;
			}
			break;
		case 3: // Blacksmith profession
			switch(villagerCareer) {
			case 1:
				careerTag += "Armorer";
				break;
			case 2:
				careerTag += "Weapon Smith";
				break;
			case 3:
				careerTag += "Tool Smith";
				break;
			default:
				careerTag += "Blacksmith";
				break;
			}
			break;
		case 4: // Butcher profession
			switch(villagerCareer) {
			case 1:
				careerTag += "Butcher";
				break;
			case 2:
				careerTag += "Leatherworker";
				break;
			default:
				careerTag += "Butcher";
				break;
			}
			break;
		case 5: // Nitwit profession
			switch(villagerCareer) {
			case 1:
				careerTag += nitwitProfession;
				break;
			default:
				careerTag += nitwitProfession;
				break;
			}
			break;
		}
		if (!(villagerProfession >= 0 && villagerProfession <= 5)) {
			try {
				String otherModProfString = otherModProfessions[ItemEventHandler.indexOfIntArr(otherModIDs, villagerProfession)];
				otherModProfString = otherModProfString.replaceAll("\\(", "");
				otherModProfString = otherModProfString.replaceAll("\\)", "");
				otherModProfString = otherModProfString.trim();
				
				careerTag += otherModProfString;
				}
			catch (Exception e){
				//If something went wrong in the profession mapping, return empty parentheses
					}
		}
		
		careerTag += ")";
		
		return careerTag;
	}


	/**
	 * Generate a profession tag to append to their name
	 * @param villagerProfession: integer to represent profession (0 to 5)
	 * @param villagerCareer: integer to represent career (0 before 1.8; 1+ otherwise)
	 * @return
	 */
	public static String getCareerTag(int villagerProfession, int villagerCareer) {
		return getCareerTag(villagerProfession, villagerCareer, "");
	}
	
	/**
	 * Generate a profession tag to append to their name
	 * @param villagerProfession: integer to represent profession (0 to 5)
	 * @param villagerCareer: integer to represent career (0 before 1.8; 1+ otherwise)
	 * @return
	 */
	public static String getNibiruCareerTag(int villagerProfession, int villagerCareer) {
		
		String careerTag = "(";
		
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
		careerTag += ")";
		
		return careerTag;
	}
	
}
