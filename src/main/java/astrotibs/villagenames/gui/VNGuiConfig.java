package astrotibs.villagenames.gui;

import java.util.ArrayList;
import java.util.List;

import astrotibs.villagenames.VillageNames;
import astrotibs.villagenames.config.AlienConfigHandler;
import astrotibs.villagenames.config.AlienVillageConfigHandler;
import astrotibs.villagenames.config.AngelConfigHandler;
import astrotibs.villagenames.config.ConfigReloader;
import astrotibs.villagenames.config.CustomConfigHandler;
import astrotibs.villagenames.config.DemonConfigHandler;
import astrotibs.villagenames.config.DragonConfigHandler;
import astrotibs.villagenames.config.EndCityConfigHandler;
import astrotibs.villagenames.config.FortressConfigHandler;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.config.GoblinConfigHandler;
import astrotibs.villagenames.config.GolemConfigHandler;
import astrotibs.villagenames.config.MansionConfigHandler;
import astrotibs.villagenames.config.MineshaftConfigHandler;
import astrotibs.villagenames.config.MonumentConfigHandler;
import astrotibs.villagenames.config.StrongholdConfigHandler;
import astrotibs.villagenames.config.TempleConfigHandler;
import astrotibs.villagenames.config.VillageConfigHandler;
import astrotibs.villagenames.config.VillagerConfigHandler;
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
		
		cc = GeneralConfig.config.getCategory("general");
		cc.setComment("Change things like well decorations, name formats, and Codex/book settings");
		subCats.add( new ConfigElement(cc) );
		
		cc = GeneralConfig.config.getCategory("well kill switch");
		cc.setComment("Disable well decorations outright"); 
		subCats.add( new ConfigElement(cc) );
		
		cc = GeneralConfig.config.getCategory("entity names");
		cc.setComment("Name generation for vanilla and modded entities");
		subCats.add( new ConfigElement(cc) );
		
		cc = GeneralConfig.config.getCategory("world of color");
		cc.setComment("Add Concrete and Glazed Terracotta from 1.12");
		cc.setRequiresWorldRestart(true); // This category can't be edited while a world is running
		cc.setRequiresMcRestart(true); // This category needs Minecraft to be restarted to take effect
		subCats.add( new ConfigElement(cc) );
		/*
		cc = GeneralConfigHandler.config.getCategory("ocean monuments");
		cc.setComment("Add Prismarine, Sponges, Ocean Monuments, and Guardians from 1.8");
		cc.setRequiresWorldRestart(true); // This category can't be edited while a world is running
		cc.setRequiresMcRestart(true); // This category needs Minecraft to be restarted to take effect
		subCats.add( new ConfigElement(cc) );
		*/
		cc = GeneralConfig.config.getCategory("sounds");
		cc.setComment("Add in sound effects backported from various Minecraft versions");
		//cc.setRequiresMcRestart(true);
		subCats.add( new ConfigElement(cc) );
		
		cc = GeneralConfig.config.getCategory("mod integration");
		cc.setComment("Interaction with modded structures, entities, blocks, items, etc.");
		subCats.add( new ConfigElement(cc) );
		
		cc = GeneralConfig.config.getCategory("villager professions");
		cc.setComment("Profession and career assignment");
		subCats.add( new ConfigElement(cc) );
		cc.setRequiresMcRestart(true); // This category needs Minecraft to be restarted to take effect
		
		cc = GeneralConfig.config.getCategory("miscellaneous");
		cc.setComment("Activate debug messages here");
		subCats.add( new ConfigElement(cc) );
		
		topCats.add(new DummyCategoryElement(
				//EnumChatFormatting.GREEN +
				"General Settings",
				"config.villagenames.global",
				subCats
				));
		
		// Syllable pools
		subCats = new ArrayList<IConfigElement>();
		
		// Villager
		cc = VillagerConfigHandler.config.getCategory("villager syllable pool");
		cc.setComment("Syllables for naming villagers");
		subCats.add( new ConfigElement(cc) );
		
		// Village
		cc = VillageConfigHandler.config.getCategory("village syllable pool");
		cc.setComment("Syllables for naming villages");
		subCats.add( new ConfigElement(cc) );
		
		// Temple
		cc = TempleConfigHandler.config.getCategory("temple syllable pool");
		cc.setComment("Syllables for naming temples, pyramids, swamp huts, and igloos");
		subCats.add( new ConfigElement(cc) );
		
		// Mineshaft
		cc = MineshaftConfigHandler.config.getCategory("mineshaft syllable pool");
		cc.setComment("Syllables for naming mineshafts");
		subCats.add( new ConfigElement(cc) );
		
		// Fortress
		cc = FortressConfigHandler.config.getCategory("fortress syllable pool");
		cc.setComment("Syllables for naming Nether fortresses");
		subCats.add( new ConfigElement(cc) );
		
		// Stronghold
		cc = StrongholdConfigHandler.config.getCategory("stronghold syllable pool");
		cc.setComment("Syllables for naming strongholds");
		subCats.add( new ConfigElement(cc) );
		
		// Monument
		cc = MonumentConfigHandler.config.getCategory("monument syllable pool");
		cc.setComment("Syllables for naming ocean monuments");
		subCats.add( new ConfigElement(cc) );
		
		// End City
		cc = EndCityConfigHandler.config.getCategory("end city syllable pool");
		cc.setComment("Syllables for naming End cities");
		subCats.add( new ConfigElement(cc) );
		
		// Mansion
		cc = MansionConfigHandler.config.getCategory("mansion syllable pool");
		cc.setComment("Syllables for naming woodland mansions");
		subCats.add( new ConfigElement(cc) );
		
		// Golem
		cc = GolemConfigHandler.config.getCategory("golem syllable pool");
		cc.setComment("Syllables for naming golems");
		subCats.add( new ConfigElement(cc) );
		
		// Dragon
		cc = DragonConfigHandler.config.getCategory("dragon syllable pool");
		cc.setComment("Syllables for naming dragons");
		subCats.add( new ConfigElement(cc) );
		
		// Angel
		cc = AngelConfigHandler.config.getCategory("angel syllable pool");
		cc.setComment("Syllables to generate angel names");
		subCats.add( new ConfigElement(cc) );
		
		// Demon
		cc = DemonConfigHandler.config.getCategory("demon syllable pool");
		cc.setComment("Syllables to generate demon names");
		subCats.add( new ConfigElement(cc) );
		
		// Goblin
		cc = GoblinConfigHandler.config.getCategory("goblin syllable pool");
		cc.setComment("Syllables to generate fairy/goblin names");
		subCats.add( new ConfigElement(cc) );
		
		// Alien Villager
		cc = AlienConfigHandler.config.getCategory("alien syllable pool");
		cc.setComment("Syllables to generate alien names");
		subCats.add( new ConfigElement(cc) );
		
		// Alien Village
		cc = AlienVillageConfigHandler.config.getCategory("alien village syllable pool");
		cc.setComment("Syllables to generate alien village names");
		subCats.add( new ConfigElement(cc) );
		
		// Custom
		cc = CustomConfigHandler.config.getCategory("custom syllable pool");
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