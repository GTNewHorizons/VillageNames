package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import astrotibs.villagenames.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class StrongholdConfigHandler {
	public static Configuration config;
	
	// Stronghold name pieces
	public static String[] stronghold_prefix = NamePieces.stronghold_prefix_default;
	public static String[] stronghold_suffix = NamePieces.stronghold_suffix_default;
	public static String[] stronghold_oneSylBegin = NamePieces.stronghold_oneSylBegin_default;
	public static String[] stronghold_oneSylEnd = NamePieces.stronghold_oneSylEnd_default;
	public static String[] stronghold_syl1Trans = NamePieces.stronghold_syl1Trans_default;
	public static String[] stronghold_syl2Term = NamePieces.stronghold_syl2Term_default;
	public static String[] stronghold_syl2Trans = NamePieces.stronghold_syl2Trans_default;
	public static String[] stronghold_syl3Term = NamePieces.stronghold_syl3Term_default;
	public static String[] stronghold_syl3Trans = NamePieces.stronghold_syl3Trans_default;
	public static String[] stronghold_syl4Term = NamePieces.stronghold_syl4Term_default;
	public static String[] stronghold_syl4Trans = NamePieces.stronghold_syl4Trans_default;
	public static String[] stronghold_syl5Term = NamePieces.stronghold_syl5Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		
		//Java is stupid and I need to construct the default for the syllable lists INSIDE A METHOD, hurr durr
		// Here are all the string elements
		stronghold_prefix = config.getStringList("stronghold_prefix", "Prefix/Suffix", stronghold_prefix, "Prefixes that can occur before name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		stronghold_suffix = config.getStringList("stronghold_suffix", "Prefix/Suffix", stronghold_suffix, "Suffixes that can occur after name");
		
		stronghold_oneSylBegin = config.getStringList("stronghold_oneSylBegin", "Stronghold Syllable 1", stronghold_oneSylBegin, "First half of one-syllable town name");
		stronghold_oneSylEnd = config.getStringList("stronghold_oneSylEnd", "Stronghold Syllable 1", stronghold_oneSylEnd, "Second half of one-syllable town name");
		stronghold_syl1Trans = config.getStringList("stronghold_syl1Trans", "Stronghold Syllable 1", stronghold_syl1Trans, "Transitional first syllable");
		
		stronghold_syl2Trans = config.getStringList("stronghold_syl2Trans", "Stronghold Syllable 2", stronghold_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		stronghold_syl2Term = config.getStringList("stronghold_syl2Term", "Stronghold Syllable 2", stronghold_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		
		stronghold_syl3Trans = config.getStringList("stronghold_syl3Trans", "Stronghold Syllable 3", stronghold_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		stronghold_syl3Term = config.getStringList("stronghold_syl3Term", "Stronghold Syllable 3", stronghold_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		
		stronghold_syl4Trans = config.getStringList("stronghold_syl4Trans", "Stronghold Syllable 4", stronghold_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		stronghold_syl4Term = config.getStringList("stronghold_syl4Term", "Stronghold Syllable 4", stronghold_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		
		stronghold_syl5Term = config.getStringList("stronghold_syl5Term", "Stronghold Syllable 5", stronghold_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		
	    if (config.hasChanged()) config.save();
	}
	
	@SubscribeEvent
	public void onConfigurationChangedEvent(cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
}
