package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import astrotibs.villagenames.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FortressConfigHandler {
	public static Configuration config;
	
	// Fortress name pieces
	public static String[] fortress_prefix = NamePieces.fortress_prefix_default;
	public static String[] fortress_suffix = NamePieces.fortress_suffix_default;
	public static String[] fortress_oneSylBegin = NamePieces.fortress_oneSylBegin_default;
	public static String[] fortress_oneSylEnd = NamePieces.fortress_oneSylEnd_default;
	public static String[] fortress_syl1Trans = NamePieces.fortress_syl1Trans_default;
	public static String[] fortress_syl2Term = NamePieces.fortress_syl2Term_default;
	public static String[] fortress_syl2Trans = NamePieces.fortress_syl2Trans_default;
	public static String[] fortress_syl3Term = NamePieces.fortress_syl3Term_default;
	public static String[] fortress_syl3Trans = NamePieces.fortress_syl3Trans_default;
	public static String[] fortress_syl4Term = NamePieces.fortress_syl4Term_default;
	public static String[] fortress_syl4Trans = NamePieces.fortress_syl4Trans_default;
	public static String[] fortress_syl5Term = NamePieces.fortress_syl5Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		
		//Java is stupid and I need to construct the default for the syllable lists INSIDE A METHOD, hurr durr
		// Here are all the string elements
		fortress_prefix = config.getStringList("fortress_prefix", "Prefix/Suffix", fortress_prefix, "Prefixes that can occur before name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		fortress_suffix = config.getStringList("fortress_suffix", "Prefix/Suffix", fortress_suffix, "Suffixes that can occur after name");
		
		fortress_oneSylBegin = config.getStringList("fortress_oneSylBegin", "Fortress Syllable 1", fortress_oneSylBegin, "First half of one-syllable town name");
		fortress_oneSylEnd = config.getStringList("fortress_oneSylEnd", "Fortress Syllable 1", fortress_oneSylEnd, "Second half of one-syllable town name");
		fortress_syl1Trans = config.getStringList("fortress_syl1Trans", "Fortress Syllable 1", fortress_syl1Trans, "Transitional first syllable");
		
		fortress_syl2Trans = config.getStringList("fortress_syl2Trans", "Fortress Syllable 2", fortress_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		fortress_syl2Term = config.getStringList("fortress_syl2Term", "Fortress Syllable 2", fortress_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		
		fortress_syl3Trans = config.getStringList("fortress_syl3Trans", "Fortress Syllable 3", fortress_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		fortress_syl3Term = config.getStringList("fortress_syl3Term", "Fortress Syllable 3", fortress_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		
		fortress_syl4Trans = config.getStringList("fortress_syl4Trans", "Fortress Syllable 4", fortress_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		fortress_syl4Term = config.getStringList("fortress_syl4Term", "Fortress Syllable 4", fortress_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		
		fortress_syl5Term = config.getStringList("fortress_syl5Term", "Fortress Syllable 5", fortress_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		
	    if (config.hasChanged()) config.save();
	}
	
	@SubscribeEvent
	public void onConfigurationChangedEvent(cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
}
