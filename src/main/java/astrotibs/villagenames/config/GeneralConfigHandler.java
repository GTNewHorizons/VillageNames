package astrotibs.villagenames.config;

import java.io.File;

import astrotibs.villagenames.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GeneralConfigHandler {
	public static Configuration config;
	
	public static String[] blackList;
	public static boolean wellSlabs;
	public static boolean nameSign;
	public static boolean nameVillagers;
	public static String headerTags = "\u00a78\u00a7o";
	public static boolean villagerDropBook;
	public static boolean addJobToName;
	public static String nitwitProfession;
	public static boolean addConcrete;
	public static boolean concreteWell;
	public static boolean addOceanMonuments;
	//public static boolean moreATGMonuments;
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		
		blackList = config.getString("Dimension blacklist", Configuration.CATEGORY_GENERAL, "-1,1", "Prevent well decorations on a per-world basis, by dimension ids. Use [id1;id2] to add a range of id, prefix with - to exclude.").split(",");
	    nameSign = config.getBoolean("Name sign", Configuration.CATEGORY_GENERAL, true, "Villages display their name on a sign near the well. You can still discover the name by right-clicking a book onto a villager.");
	    nameVillagers = config.getBoolean("Name villagers", Configuration.CATEGORY_GENERAL, true, "Villagers reveal their names when you right-click them. If this is false, you can name villagers with name tags.");
	    wellSlabs = config.getBoolean("Well slabs", Configuration.CATEGORY_GENERAL, true, "Replace the cobblestone rims of wells with stone slabs, making it easier for players and villagers to escape if they fall in.");
	    villagerDropBook = config.getBoolean("Villager drops book", Configuration.CATEGORY_GENERAL, false, "Village books are dropped by the villager rather than going directly into your inventory.");
	    addJobToName = config.getBoolean("addJobToName", "Profession/Career", false, "A villager's name also includes its profession. Turn this on/off and talk to a villager to add/remove the career tag to their name.");
	    //String nitwitProfession = config.getString("nitwitProfession", "Profession/Career", "", "The career displayed for a Nitwit");
	    addConcrete = config.getBoolean("addConcrete", "Concrete", true, "Whether to add 1.12 style Concrete, Concrete Powder, and Glazed Terracotta");
	    concreteWell = config.getBoolean("concreteWell", "Concrete", true, "Whether to decorate wells with Concrete and Glazed Terracotta instead of stained clay (addConcrete must be true)");
	    addOceanMonuments = config.getBoolean("addOceanMonuments", "Ocean Monuments", true, "Whether to generate Ocean Monuments, Prismarine, and absorbent Sponges");
	    //moreATGMonuments = config.getBoolean("moreATGMonuments", "Ocean Monuments", false, "Deep Ocean biomes are rare in the ATG world type. Set this to true to require the monument center to be in a Deep/Frozen/regular Ocean biome instead of specifically a Deep Ocean. Monuments may carve themselves into the sea bed.");
	    
	    if (config.hasChanged()) config.save();
	}
	
	@SubscribeEvent
	public void onConfigurationChangedEvent(cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
}
