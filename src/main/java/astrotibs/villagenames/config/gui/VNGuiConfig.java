package astrotibs.villagenames.config.gui;

import java.util.ArrayList;
import java.util.List;

import astrotibs.villagenames.VillageNames;
import astrotibs.villagenames.config.ConfigReloader;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.config.pieces.AlienConfigHandler;
import astrotibs.villagenames.config.pieces.AlienVillageConfigHandler;
import astrotibs.villagenames.config.pieces.AngelConfigHandler;
import astrotibs.villagenames.config.pieces.CustomConfigHandler;
import astrotibs.villagenames.config.pieces.DemonConfigHandler;
import astrotibs.villagenames.config.pieces.DragonConfigHandler;
import astrotibs.villagenames.config.pieces.EndCityConfigHandler;
import astrotibs.villagenames.config.pieces.FortressConfigHandler;
import astrotibs.villagenames.config.pieces.GoblinConfigHandler;
import astrotibs.villagenames.config.pieces.GolemConfigHandler;
import astrotibs.villagenames.config.pieces.MansionConfigHandler;
import astrotibs.villagenames.config.pieces.MineshaftConfigHandler;
import astrotibs.villagenames.config.pieces.MonumentConfigHandler;
import astrotibs.villagenames.config.pieces.PetConfigHandler;
import astrotibs.villagenames.config.pieces.StrongholdConfigHandler;
import astrotibs.villagenames.config.pieces.TempleConfigHandler;
import astrotibs.villagenames.config.pieces.VillageConfigHandler;
import astrotibs.villagenames.config.pieces.VillagerConfigHandler;
import astrotibs.villagenames.config.village.VillageGeneratorConfigHandler;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.client.config.DummyConfigElement.DummyCategoryElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiMessageDialog;
import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.client.event.ConfigChangedEvent.PostConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;

/**
 * @author AstroTibs
 * Adapted from Jabelar's Magic Beans:
 * https://github.com/jabelar/MagicBeans-1.7.10/blob/52dc91bfa2e515dcd6ebe116453dc98951f03dcb/src/main/java/com/blogspot/jabelarminecraft/magicbeans/gui/GuiConfig.java
 * and FunWayGuy's EnviroMine:
 * https://github.com/EnviroMine/EnviroMine-1.7/blob/1652062539adba36563450caefa1879127ccb950/src/main/java/enviromine/client/gui/menu/config/EM_ConfigMenu.java
 */
public class VNGuiConfig extends GuiConfig 
{
	
	public VNGuiConfig(GuiScreen guiScreen)
	{
		super(
				guiScreen,         // parentScreen: the parent GuiScreen object
				getElements(),     // configElements: a List of IConfigProperty objects
                Reference.MOD_ID,  // modID: the mod ID for the mod whose config settings will be edited
				false,             // allRequireWorldRestart: send true if all configElements on this screen require a world restart
				false,             // allRequireMcRestart: send true if all configElements on this screen require MC to be restarted
				getHeader()        // title: the desired title for this screen. For consistency it is recommended that you pass the path of the config file being edited.
				);
	}
	
	// I was going to use this to warn the player if they had an old config folder but I kind of don't care
	private static String getHeader() {
		return EnumChatFormatting.YELLOW 
				+ VillageNames.configDirectory.getAbsolutePath();
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	private static List<IConfigElement> getElements()
	{
		List<IConfigElement> topCats = new ArrayList<IConfigElement>();
		List<IConfigElement> subCats;
		ConfigCategory cc;
		
		// General config
		subCats = new ArrayList<IConfigElement>();
		
		cc = GeneralConfig.config.getCategory(Reference.CATEGORY_GENERAL);
		cc.setComment("Change things like well decorations, name formats, and Codex/book settings");
		subCats.add( new ConfigElement(cc) );
		
		cc = GeneralConfig.config.getCategory(Reference.CATEGORY_WELL_KILL_SWITCH);
		cc.setComment("Disable well decorations outright"); 
		subCats.add( new ConfigElement(cc) );
		
		cc = GeneralConfig.config.getCategory(Reference.CATEGORY_NAMING);
		cc.setComment("Name generation for vanilla and modded entities");
		subCats.add( new ConfigElement(cc) );
		
		cc = GeneralConfig.config.getCategory(Reference.CATEGORY_WORLD_OF_COLOR);
		cc.setComment("Add Concrete and Glazed Terracotta from 1.12");
		cc.setRequiresWorldRestart(true); // This category can't be edited while a world is running
		cc.setRequiresMcRestart(true); // This category needs Minecraft to be restarted to take effect
		subCats.add( new ConfigElement(cc) );
		
		cc = GeneralConfig.config.getCategory(Reference.CATEGORY_MOD_INTEGRATION);
		cc.setComment("Interaction with modded structures, entities, blocks, items, etc.");
		subCats.add( new ConfigElement(cc) );
		
		cc = GeneralConfig.config.getCategory(Reference.CATEGORY_VILLAGER_PROFESSIONS);
		cc.setComment("Profession and career assignment");
		subCats.add( new ConfigElement(cc) );
		cc.setRequiresMcRestart(true); // This category needs Minecraft to be restarted to take effect
		
		cc = GeneralConfig.config.getCategory(Reference.CATEGORY_VILLAGER_SKIN_TONES);
		cc.setComment("Show biome-based skin tone variation (requires Modern Villager Profession Skins to be true)");
		subCats.add( new ConfigElement(cc) );
		
		cc = GeneralConfig.config.getCategory(Reference.CATEGORY_MISCELLANEOUS);
		cc.setComment("Activate debug messages here");
		subCats.add( new ConfigElement(cc) );
		
		topCats.add(new DummyCategoryElement(
				//EnumChatFormatting.GREEN +
				"General Settings",
				"config.villagenames.global",
				subCats
				));
		
		
		
		// Village generator
		subCats = new ArrayList<IConfigElement>();
		
		cc = VillageGeneratorConfigHandler.config.getCategory(Reference.CATEGORY_VILLAGE_GENERATOR.toLowerCase());
		cc.setComment("Village buildings and generation system");
		cc.setRequiresWorldRestart(true); // This category can't be edited while a world is running
		cc.setRequiresMcRestart(true); // This category needs Minecraft to be restarted to take effect
		subCats.add( new ConfigElement(cc) );
		
		topCats.add(new DummyCategoryElement(
				//EnumChatFormatting.GREEN +
				Reference.CATEGORY_VILLAGE_GENERATOR,
				"config.villagenames.villagegenerator",
				subCats
				));
		
		
		
		// Syllable pools
		subCats = new ArrayList<IConfigElement>();
		
		// Villager
		cc = VillagerConfigHandler.config.getCategory(Reference.CATEGORY_VILLAGER_SYLLABLE_POOL);
		cc.setComment("Syllables for naming villagers");
		subCats.add( new ConfigElement(cc) );
		
		// Village
		cc = VillageConfigHandler.config.getCategory(Reference.CATEGORY_VILLAGE_SYLLABLE_POOL);
		cc.setComment("Syllables for naming villages");
		subCats.add( new ConfigElement(cc) );
		
		// Temple
		cc = TempleConfigHandler.config.getCategory(Reference.CATEGORY_TEMPLE_SYLLABLE_POOL);
		cc.setComment("Syllables for naming temples, pyramids, swamp huts, and igloos");
		subCats.add( new ConfigElement(cc) );
		
		// Mineshaft
		cc = MineshaftConfigHandler.config.getCategory(Reference.CATEGORY_MINESHAFT_SYLLABLE_POOL);
		cc.setComment("Syllables for naming mineshafts");
		subCats.add( new ConfigElement(cc) );
		
		// Fortress
		cc = FortressConfigHandler.config.getCategory(Reference.CATEGORY_FORTRESS_SYLLABLE_POOL);
		cc.setComment("Syllables for naming Nether fortresses");
		subCats.add( new ConfigElement(cc) );
		
		// Stronghold
		cc = StrongholdConfigHandler.config.getCategory(Reference.CATEGORY_STRONGHOLD_SYLLABLE_POOL);
		cc.setComment("Syllables for naming strongholds");
		subCats.add( new ConfigElement(cc) );
		
		// Monument
		cc = MonumentConfigHandler.config.getCategory(Reference.CATEGORY_MONUMENT_SYLLABLE_POOL);
		cc.setComment("Syllables for naming ocean monuments");
		subCats.add( new ConfigElement(cc) );
		
		// End City
		cc = EndCityConfigHandler.config.getCategory(Reference.CATEGORY_END_CITY_SYLLABLE_POOL);
		cc.setComment("Syllables for naming End cities");
		subCats.add( new ConfigElement(cc) );
		
		// Mansion
		cc = MansionConfigHandler.config.getCategory(Reference.CATEGORY_MANSION_SYLLABLE_POOL);
		cc.setComment("Syllables for naming woodland mansions");
		subCats.add( new ConfigElement(cc) );
		
		// Golem
		cc = GolemConfigHandler.config.getCategory(Reference.CATEGORY_GOLEM_SYLLABLE_POOL);
		cc.setComment("Syllables for naming golems");
		subCats.add( new ConfigElement(cc) );
		
		// Pet
		cc = PetConfigHandler.config.getCategory(Reference.CATEGORY_PET_SYLLABLE_POOL);
		cc.setComment("Syllables to generate names for pets");
		subCats.add( new ConfigElement(cc) );
		
		// Dragon
		cc = DragonConfigHandler.config.getCategory(Reference.CATEGORY_DRAGON_SYLLABLE_POOL);
		cc.setComment("Syllables for naming dragons");
		subCats.add( new ConfigElement(cc) );
		
		// Angel
		cc = AngelConfigHandler.config.getCategory(Reference.CATEGORY_ANGEL_SYLLABLE_POOL);
		cc.setComment("Syllables to generate angel names");
		subCats.add( new ConfigElement(cc) );
		
		// Demon
		cc = DemonConfigHandler.config.getCategory(Reference.CATEGORY_DEMON_SYLLABLE_POOL);
		cc.setComment("Syllables to generate demon names");
		subCats.add( new ConfigElement(cc) );
		
		// Goblin
		cc = GoblinConfigHandler.config.getCategory(Reference.CATEGORY_GOBLIN_SYLLABLE_POOL);
		cc.setComment("Syllables to generate fairy/goblin names");
		subCats.add( new ConfigElement(cc) );
		
		// Alien Villager
		cc = AlienConfigHandler.config.getCategory(Reference.CATEGORY_ALIEN_SYLLABLE_POOL);
		cc.setComment("Syllables to generate alien names");
		subCats.add( new ConfigElement(cc) );
		
		// Alien Village
		cc = AlienVillageConfigHandler.config.getCategory(Reference.CATEGORY_ALIEN_VILLAGE_SYLLABLE_POOL);
		cc.setComment("Syllables to generate alien village names");
		subCats.add( new ConfigElement(cc) );
		
		// Custom
		cc = CustomConfigHandler.config.getCategory(Reference.CATEGORY_CUSTOM_SYLLABLE_POOL);
		cc.setComment("Dedicated section for players to assign names to using their own syllable pools");
		subCats.add( new ConfigElement(cc) );
		
		topCats.add(new DummyCategoryElement(
				//EnumChatFormatting.GREEN +
				"Syllable Pools",
				"config.villagenames.syllables",
				subCats
				));
		
		
		return topCats;
	}
	
	@Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 2000) // The topmost "Done" button
        {
            boolean flag = true;
            
            try
            {
                if ((configID != null || this.parentScreen == null || !(this.parentScreen instanceof VNGuiConfig)) 
                        && (this.entryList.hasChangedEntry(true)))
                {
                    boolean requiresMcRestart = this.entryList.saveConfigElements();
                    
                    if (Loader.isModLoaded(modID))
                    {
                        ConfigChangedEvent event = new OnConfigChangedEvent(modID, configID, isWorldRunning, requiresMcRestart);
                        FMLCommonHandler.instance().bus().post(event);
                        
                        if (!event.getResult().equals(Result.DENY))
                            FMLCommonHandler.instance().bus().post(new PostConfigChangedEvent(modID, configID, isWorldRunning, requiresMcRestart));
                        	ConfigReloader.reloadConfigs(); // To force-sync the config options
                        if (requiresMcRestart)
                        {
                            flag = false;
                            mc.displayGuiScreen(new GuiMessageDialog(parentScreen, "fml.configgui.gameRestartTitle", new ChatComponentText(I18n.format("fml.configgui.gameRestartRequired")), "fml.configgui.confirmRestartMessage"));
                        }
                        
                        if (this.parentScreen instanceof VNGuiConfig)
                            ((VNGuiConfig) this.parentScreen).needsRefresh = true;
                    }
                }
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
            
            if (flag)
            	
                this.mc.displayGuiScreen(this.parentScreen);
        }
    }
	
}