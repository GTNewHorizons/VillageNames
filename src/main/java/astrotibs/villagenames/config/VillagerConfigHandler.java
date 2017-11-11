package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.name.NamePieces;
import astrotibs.villagenames.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class VillagerConfigHandler {
	public static Configuration config;
	
	// Villager names
	public static String[] villager_oneSylBegin = NamePieces.villager_oneSylBegin_default;
	public static String[] villager_oneSylEnd = NamePieces.villager_oneSylEnd_default;
	public static String[] villager_syl1Trans = NamePieces.villager_syl1Trans_default;
	public static String[] villager_syl2Term = NamePieces.villager_syl2Term_default;
	public static String[] villager_syl2Trans = NamePieces.villager_syl2Trans_default;
	public static String[] villager_syl3Term = NamePieces.villager_syl3Term_default;
	public static String[] villager_syl3Trans = NamePieces.villager_syl3Trans_default;
	public static String[] villager_syl4Term = NamePieces.villager_syl4Term_default;
	public static String[] villager_syl4Trans = NamePieces.villager_syl4Trans_default;
	public static String[] villager_syl5Term = NamePieces.villager_syl5Term_default;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		
		// Here are the elements for the villager names
		villager_oneSylBegin = config.getStringList("villager_oneSylBegin", "Villager Syllable 1", villager_oneSylBegin, "First half of one-syllable villager name\n"
				+ "Check out https://en.wikipedia.org/wiki/List_of_Unicode_characters\n"
				+ "for unicode keys.\n"
				+ "");
		villager_oneSylEnd = config.getStringList("villager_oneSylEnd", "Villager Syllable 1", villager_oneSylEnd, "Second half of one-syllable villager name");
		villager_syl1Trans = config.getStringList("villager_syl1Trans", "Villager Syllable 1", villager_syl1Trans, "Transitional first syllable");
		
		villager_syl2Trans = config.getStringList("villager_syl2Trans", "Villager Syllable 2", villager_syl2Trans, "Transitional second syllable (use ^ for leading/ending spaces)");
		villager_syl2Term = config.getStringList("villager_syl2Term", "Villager Syllable 2", villager_syl2Term, "Terminating second syllable (use ^ for leading/ending spaces)");
		
		villager_syl3Trans = config.getStringList("villager_syl3Trans", "Villager Syllable 3", villager_syl3Trans, "Transitional third syllable (use ^ for leading/ending spaces)");
		villager_syl3Term = config.getStringList("villager_syl3Term", "Villager Syllable 3", villager_syl3Term, "Terminating third syllable (use ^ for leading/ending spaces)");
		
		villager_syl4Trans = config.getStringList("villager_syl4Trans", "Villager Syllable 4", villager_syl4Trans, "Transitional fourth syllable (use ^ for leading/ending spaces)");
		villager_syl4Term = config.getStringList("villager_syl4Term", "Villager Syllable 4", villager_syl4Term, "Terminating fourth syllable (use ^ for leading/ending spaces)");
		
		villager_syl5Term = config.getStringList("villager_syl5Term", "Villager Syllable 5", villager_syl5Term, "Terminating fifth syllable (use ^ for leading/ending spaces)");
		
	    if (config.hasChanged()) config.save();
	}
	
	@SubscribeEvent
	public void onConfigurationChangedEvent(cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
}
