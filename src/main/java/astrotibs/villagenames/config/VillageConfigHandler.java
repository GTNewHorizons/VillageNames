package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import astrotibs.villagenames.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class VillageConfigHandler {
	public static Configuration config;
	
	// Village name pieces
	public static String[] prefix = NamePieces.prefix_default;
	public static String[] suffix = NamePieces.suffix_default;
	public static String[] oneSylBegin = NamePieces.oneSylBegin_default;
	public static String[] oneSylEnd = NamePieces.oneSylEnd_default;
	public static String[] syl1Trans = NamePieces.syl1Trans_default;
	public static String[] syl2Term = NamePieces.syl2Term_default;
	public static String[] syl2Trans = NamePieces.syl2Trans_default;
	public static String[] syl3Term = NamePieces.syl3Term_default;
	public static String[] syl3Trans = NamePieces.syl3Trans_default;
	public static String[] syl4Term = NamePieces.syl4Term_default;
	public static String[] syl4Trans = NamePieces.syl4Trans_default;
	public static String[] syl5Term = NamePieces.syl5Term_default;
	public static String[] syl5Trans = NamePieces.syl5Trans_default;
	public static String[] syl6Term = NamePieces.syl6Term_default;
	public static String[] syl6Trans = NamePieces.syl6Trans_default;
	public static String[] syl7Term = NamePieces.syl7Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		
		//Java is stupid and I need to construct the default for the syllable lists INSIDE A METHOD, hurr durr
		// Here are all the string elements
		prefix = config.getStringList("prefix", "Prefix/Suffix", prefix, "Prefixes that can occur before name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		suffix = config.getStringList("suffix", "Prefix/Suffix", suffix, "Suffixes that can occur after name");
		
		oneSylBegin = config.getStringList("oneSylBegin", "Town Syllable 1", oneSylBegin, "First half of one-syllable town name");
		oneSylEnd = config.getStringList("oneSylEnd", "Town Syllable 1", oneSylEnd, "Second half of one-syllable town name");
		syl1Trans = config.getStringList("syl1Trans", "Town Syllable 1", syl1Trans, "Transitional first syllable");
		
		syl2Trans = config.getStringList("syl2Trans", "Town Syllable 2", syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		syl2Term = config.getStringList("syl2Term", "Town Syllable 2", syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		
		syl3Trans = config.getStringList("syl3Trans", "Town Syllable 3", syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		syl3Term = config.getStringList("syl3Term", "Town Syllable 3", syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		
		syl4Trans = config.getStringList("syl4Trans", "Town Syllable 4", syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		syl4Term = config.getStringList("syl4Term", "Town Syllable 4", syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		
		syl5Trans = config.getStringList("syl5Trans", "Town Syllable 5", syl5Trans, "Transitional fifth syllable (use ^ for leading/ending spaces)");
		syl5Term = config.getStringList("syl5Term", "Town Syllable 5", syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		
		syl6Trans = config.getStringList("syl6Trans", "Town Syllable 6", syl6Trans, "Transitional sixth syllable (use ^ for leading/ending spaces)");
		syl6Term = config.getStringList("syl6Term", "Town Syllable 6", syl6Term, "Terminating sixth syllable (use ^ for leading/ending spaces)");
		
		syl7Term = config.getStringList("syl7Term", "Town Syllable 7", syl7Term, "Terminating seventh syllable (use ^ for leading/ending spaces)");
		
	    if (config.hasChanged()) config.save();
	}
	
	@SubscribeEvent
	public void onConfigurationChangedEvent(cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
}
