package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import astrotibs.villagenames.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EndCityConfigHandler {
	
	public static Configuration config;
	
	// Mansion name pieces
	public static String[] endcity_prefix = NamePieces.endcity_prefix_default;
	public static String[] endcity_suffix = NamePieces.endcity_suffix_default;
	public static String[] endcity_oneSylBegin = NamePieces.endcity_oneSylBegin_default;
	public static String[] endcity_oneSylEnd = NamePieces.endcity_oneSylEnd_default;
	public static String[] endcity_syl1Trans = NamePieces.endcity_syl1Trans_default;
	public static String[] endcity_syl2Term = NamePieces.endcity_syl2Term_default;
	public static String[] endcity_syl2Trans = NamePieces.endcity_syl2Trans_default;
	public static String[] endcity_syl3Term = NamePieces.endcity_syl3Term_default;
	public static String[] endcity_syl3Trans = NamePieces.endcity_syl3Trans_default;
	public static String[] endcity_syl4Term = NamePieces.endcity_syl4Term_default;
	public static String[] endcity_syl4Trans = NamePieces.endcity_syl4Trans_default;
	public static String[] endcity_syl5Term = NamePieces.endcity_syl5Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		
		//Java is stupid and I need to construct the default for the syllable lists INSIDE A METHOD, hurr durr
		// Here are all the string elements
		endcity_prefix = config.getStringList("endcity_prefix", "Prefix/Suffix", endcity_prefix, "Prefixes that can occur before name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		endcity_suffix = config.getStringList("endcity_suffix", "Prefix/Suffix", endcity_suffix, "Suffixes that can occur after name");
		
		endcity_oneSylBegin = config.getStringList("endcity_oneSylBegin", "End City Syllable 1", endcity_oneSylBegin, "First half of one-syllable town name");
		endcity_oneSylEnd = config.getStringList("endcity_oneSylEnd", "End City Syllable 1", endcity_oneSylEnd, "Second half of one-syllable town name");
		endcity_syl1Trans = config.getStringList("endcity_syl1Trans", "End City Syllable 1", endcity_syl1Trans, "Transitional first syllable");
		
		endcity_syl2Trans = config.getStringList("endcity_syl2Trans", "End City Syllable 2", endcity_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		endcity_syl2Term = config.getStringList("endcity_syl2Term", "End City Syllable 2", endcity_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		
		endcity_syl3Trans = config.getStringList("endcity_syl3Trans", "End City Syllable 3", endcity_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		endcity_syl3Term = config.getStringList("endcity_syl3Term", "End City Syllable 3", endcity_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		
		endcity_syl4Trans = config.getStringList("endcity_syl4Trans", "End City Syllable 4", endcity_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		endcity_syl4Term = config.getStringList("endcity_syl4Term", "End City Syllable 4", endcity_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		
		endcity_syl5Term = config.getStringList("endcity_syl5Term", "End City Syllable 5", endcity_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		
	    if (config.hasChanged()) config.save();
	}
	
	@SubscribeEvent
	public void onConfigurationChangedEvent(cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
	
}
