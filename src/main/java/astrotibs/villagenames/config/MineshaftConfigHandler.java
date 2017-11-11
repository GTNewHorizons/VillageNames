package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import astrotibs.villagenames.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MineshaftConfigHandler {
	public static Configuration config;
	
	// Mineshaft name pieces
	public static String[] mineshaft_prefix = NamePieces.mineshaft_prefix_default;
	public static String[] mineshaft_suffix = NamePieces.mineshaft_suffix_default;
	public static String[] mineshaft_oneSylBegin = NamePieces.mineshaft_oneSylBegin_default;
	public static String[] mineshaft_oneSylEnd = NamePieces.mineshaft_oneSylEnd_default;
	public static String[] mineshaft_syl1Trans = NamePieces.mineshaft_syl1Trans_default;
	public static String[] mineshaft_syl2Term = NamePieces.mineshaft_syl2Term_default;
	public static String[] mineshaft_syl2Trans = NamePieces.mineshaft_syl2Trans_default;
	public static String[] mineshaft_syl3Term = NamePieces.mineshaft_syl3Term_default;
	public static String[] mineshaft_syl3Trans = NamePieces.mineshaft_syl3Trans_default;
	public static String[] mineshaft_syl4Term = NamePieces.mineshaft_syl4Term_default;
	public static String[] mineshaft_syl4Trans = NamePieces.mineshaft_syl4Trans_default;
	public static String[] mineshaft_syl5Term = NamePieces.mineshaft_syl5Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		
		//Java is stupid and I need to construct the default for the syllable lists INSIDE A METHOD, hurr durr
		// Here are all the string elements
		mineshaft_prefix = config.getStringList("mineshaft_prefix", "Prefix/Suffix", mineshaft_prefix, "Prefixes that can occur before name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		mineshaft_suffix = config.getStringList("mineshaft_suffix", "Prefix/Suffix", mineshaft_suffix, "Suffixes that can occur after name");
		
		mineshaft_oneSylBegin = config.getStringList("mineshaft_oneSylBegin", "Mineshaft Syllable 1", mineshaft_oneSylBegin, "First half of one-syllable town name");
		mineshaft_oneSylEnd = config.getStringList("mineshaft_oneSylEnd", "Mineshaft Syllable 1", mineshaft_oneSylEnd, "Second half of one-syllable town name");
		mineshaft_syl1Trans = config.getStringList("mineshaft_syl1Trans", "Mineshaft Syllable 1", mineshaft_syl1Trans, "Transitional first syllable");
		
		mineshaft_syl2Trans = config.getStringList("mineshaft_syl2Trans", "Mineshaft Syllable 2", mineshaft_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		mineshaft_syl2Term = config.getStringList("mineshaft_syl2Term", "Mineshaft Syllable 2", mineshaft_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		
		mineshaft_syl3Trans = config.getStringList("mineshaft_syl3Trans", "Mineshaft Syllable 3", mineshaft_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		mineshaft_syl3Term = config.getStringList("mineshaft_syl3Term", "Mineshaft Syllable 3", mineshaft_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		
		mineshaft_syl4Trans = config.getStringList("mineshaft_syl4Trans", "Mineshaft Syllable 4", mineshaft_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		mineshaft_syl4Term = config.getStringList("mineshaft_syl4Term", "Mineshaft Syllable 4", mineshaft_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		
		mineshaft_syl5Term = config.getStringList("mineshaft_syl5Term", "Mineshaft Syllable 5", mineshaft_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		
	    if (config.hasChanged()) config.save();
	}
	
	@SubscribeEvent
	public void onConfigurationChangedEvent(cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
}
