package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import astrotibs.villagenames.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MansionConfigHandler {
	
	public static Configuration config;
	
	// Mansion name pieces
	public static String[] mansion_prefix = NamePieces.mansion_prefix_default;
	public static String[] mansion_suffix = NamePieces.mansion_suffix_default;
	public static String[] mansion_oneSylBegin = NamePieces.mansion_oneSylBegin_default;
	public static String[] mansion_oneSylEnd = NamePieces.mansion_oneSylEnd_default;
	public static String[] mansion_syl1Trans = NamePieces.mansion_syl1Trans_default;
	public static String[] mansion_syl2Term = NamePieces.mansion_syl2Term_default;
	public static String[] mansion_syl2Trans = NamePieces.mansion_syl2Trans_default;
	public static String[] mansion_syl3Term = NamePieces.mansion_syl3Term_default;
	public static String[] mansion_syl3Trans = NamePieces.mansion_syl3Trans_default;
	public static String[] mansion_syl4Term = NamePieces.mansion_syl4Term_default;
	public static String[] mansion_syl4Trans = NamePieces.mansion_syl4Trans_default;
	public static String[] mansion_syl5Term = NamePieces.mansion_syl5Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		
		//Java is stupid and I need to construct the default for the syllable lists INSIDE A METHOD, hurr durr
		// Here are all the string elements
		mansion_prefix = config.getStringList("mansion_prefix", "Prefix/Suffix", mansion_prefix, "Prefixes that can occur before name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		mansion_suffix = config.getStringList("mansion_suffix", "Prefix/Suffix", mansion_suffix, "Suffixes that can occur after name");
		
		mansion_oneSylBegin = config.getStringList("mansion_oneSylBegin", "Mansion Syllable 1", mansion_oneSylBegin, "First half of one-syllable town name");
		mansion_oneSylEnd = config.getStringList("mansion_oneSylEnd", "Mansion Syllable 1", mansion_oneSylEnd, "Second half of one-syllable town name");
		mansion_syl1Trans = config.getStringList("mansion_syl1Trans", "Mansion Syllable 1", mansion_syl1Trans, "Transitional first syllable");
		
		mansion_syl2Trans = config.getStringList("mansion_syl2Trans", "Mansion Syllable 2", mansion_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		mansion_syl2Term = config.getStringList("mansion_syl2Term", "Mansion Syllable 2", mansion_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		
		mansion_syl3Trans = config.getStringList("mansion_syl3Trans", "Mansion Syllable 3", mansion_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		mansion_syl3Term = config.getStringList("mansion_syl3Term", "Mansion Syllable 3", mansion_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		
		mansion_syl4Trans = config.getStringList("mansion_syl4Trans", "Mansion Syllable 4", mansion_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		mansion_syl4Term = config.getStringList("mansion_syl4Term", "Mansion Syllable 4", mansion_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		
		mansion_syl5Term = config.getStringList("mansion_syl5Term", "Mansion Syllable 5", mansion_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		
	    if (config.hasChanged()) config.save();
	}
	
	@SubscribeEvent
	public void onConfigurationChangedEvent(cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
	
}
