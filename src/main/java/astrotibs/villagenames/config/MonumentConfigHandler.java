package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import astrotibs.villagenames.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MonumentConfigHandler {
	
	public static Configuration config;
	
	// Monument name pieces
	public static String[] monument_prefix = NamePieces.monument_prefix_default;
	public static String[] monument_suffix = NamePieces.monument_suffix_default;
	public static String[] monument_oneSylBegin = NamePieces.monument_oneSylBegin_default;
	public static String[] monument_oneSylEnd = NamePieces.monument_oneSylEnd_default;
	public static String[] monument_syl1Trans = NamePieces.monument_syl1Trans_default;
	public static String[] monument_syl2Term = NamePieces.monument_syl2Term_default;
	public static String[] monument_syl2Trans = NamePieces.monument_syl2Trans_default;
	public static String[] monument_syl3Term = NamePieces.monument_syl3Term_default;
	public static String[] monument_syl3Trans = NamePieces.monument_syl3Trans_default;
	public static String[] monument_syl4Term = NamePieces.monument_syl4Term_default;
	public static String[] monument_syl4Trans = NamePieces.monument_syl4Trans_default;
	public static String[] monument_syl5Term = NamePieces.monument_syl5Term_default;
	public static String[] monument_syl5Trans = NamePieces.monument_syl5Trans_default;
	public static String[] monument_syl6Term = NamePieces.monument_syl6Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		
		//Java is stupid and I need to construct the default for the syllable lists INSIDE A METHOD, hurr durr
		// Here are all the string elements
		monument_prefix = config.getStringList("prefix", "Prefix/Suffix", monument_prefix, "Prefixes that can occur before name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		monument_suffix = config.getStringList("suffix", "Prefix/Suffix", monument_suffix, "Suffixes that can occur after name");
		
		monument_oneSylBegin = config.getStringList("oneSylBegin", "Monument Syllable 1", monument_oneSylBegin, "First half of one-syllable town name");
		monument_oneSylEnd = config.getStringList("oneSylEnd", "Monument Syllable 1", monument_oneSylEnd, "Second half of one-syllable town name");
		monument_syl1Trans = config.getStringList("syl1Trans", "Monument Syllable 1", monument_syl1Trans, "Transitional first syllable");
		
		monument_syl2Trans = config.getStringList("syl2Trans", "Monument Syllable 2", monument_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		monument_syl2Term = config.getStringList("syl2Term", "Monument Syllable 2", monument_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		
		monument_syl3Trans = config.getStringList("syl3Trans", "Monument Syllable 3", monument_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		monument_syl3Term = config.getStringList("syl3Term", "Monument Syllable 3", monument_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		
		monument_syl4Trans = config.getStringList("syl4Trans", "Monument Syllable 4", monument_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		monument_syl4Term = config.getStringList("syl4Term", "Monument Syllable 4", monument_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		
		monument_syl5Trans = config.getStringList("syl5Trans", "Monument Syllable 5", monument_syl5Trans, "Transitional fifth syllable (use ^ for leading/ending spaces)");
		monument_syl5Term = config.getStringList("syl5Term", "Monument Syllable 5", monument_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		
		monument_syl6Term = config.getStringList("syl6Term", "Monument Syllable 6", monument_syl6Term, "Terminating sixth syllable (use ^ for leading/ending spaces)");
		
	    if (config.hasChanged()) config.save();
	}
	
	@SubscribeEvent
	public void onConfigurationChangedEvent(cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
	
}
