package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import astrotibs.villagenames.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TempleConfigHandler {
	public static Configuration config;
	
	// Temple name pieces
	public static String[] temple_prefix = NamePieces.temple_prefix_default;
	public static String[] temple_suffix = NamePieces.temple_suffix_default;
	public static String[] temple_oneSylBegin = NamePieces.temple_oneSylBegin_default;
	public static String[] temple_oneSylEnd = NamePieces.temple_oneSylEnd_default;
	public static String[] temple_syl1Trans = NamePieces.temple_syl1Trans_default;
	public static String[] temple_syl2Term = NamePieces.temple_syl2Term_default;
	public static String[] temple_syl2Trans = NamePieces.temple_syl2Trans_default;
	public static String[] temple_syl3Term = NamePieces.temple_syl3Term_default;
	public static String[] temple_syl3Trans = NamePieces.temple_syl3Trans_default;
	public static String[] temple_syl4Term = NamePieces.temple_syl4Term_default;
	public static String[] temple_syl4Trans = NamePieces.temple_syl4Trans_default;
	public static String[] temple_syl5Term = NamePieces.temple_syl5Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		
		//Java is stupid and I need to construct the default for the syllable lists INSIDE A METHOD, hurr durr
		// Here are all the string elements
		temple_prefix = config.getStringList("temple_prefix", "Prefix/Suffix", temple_prefix, "Prefixes that can occur before name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		temple_suffix = config.getStringList("temple_suffix", "Prefix/Suffix", temple_suffix, "Suffixes that can occur after name");
		
		temple_oneSylBegin = config.getStringList("temple_oneSylBegin", "Temple Syllable 1", temple_oneSylBegin, "First half of one-syllable town name");
		temple_oneSylEnd = config.getStringList("temple_oneSylEnd", "Temple Syllable 1", temple_oneSylEnd, "Second half of one-syllable town name");
		temple_syl1Trans = config.getStringList("temple_syl1Trans", "Temple Syllable 1", temple_syl1Trans, "Transitional first syllable");
		
		temple_syl2Trans = config.getStringList("temple_syl2Trans", "Temple Syllable 2", temple_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		temple_syl2Term = config.getStringList("temple_syl2Term", "Temple Syllable 2", temple_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		
		temple_syl3Trans = config.getStringList("temple_syl3Trans", "Temple Syllable 3", temple_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		temple_syl3Term = config.getStringList("temple_syl3Term", "Temple Syllable 3", temple_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		
		temple_syl4Trans = config.getStringList("temple_syl4Trans", "Temple Syllable 4", temple_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		temple_syl4Term = config.getStringList("temple_syl4Term", "Temple Syllable 4", temple_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		
		temple_syl5Term = config.getStringList("temple_syl5Term", "Temple Syllable 5", temple_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		
	    if (config.hasChanged()) config.save();
	}
	
	@SubscribeEvent
	public void onConfigurationChangedEvent(cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
}