package astrotibs.villagenames.proxy;

import astrotibs.villagenames.VillageNames;
import astrotibs.villagenames.config.ConfigReloader;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.config.gui.ConfigInGame;
import astrotibs.villagenames.handler.EntityInteractHandler;
import astrotibs.villagenames.handler.SpawnNamingHandler;
import astrotibs.villagenames.handler.WellDecorateEvent;
import astrotibs.villagenames.item.ModItems;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.JsonSerializableSet;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

public class CommonProxy {
	
	protected Configuration config;
	
	public void preInit(FMLPreInitializationEvent e) {
		
    }
	
	public void init(FMLInitializationEvent e)
	{
		// Event listeners
		MinecraftForge.EVENT_BUS.register( new EntityInteractHandler() );
		MinecraftForge.EVENT_BUS.register( new WellDecorateEvent() );
		MinecraftForge.EVENT_BUS.register( new ConfigReloader() );
		MinecraftForge.EVENT_BUS.register( new SpawnNamingHandler() );
		if (GeneralConfig.allowInGameConfig) MinecraftForge.EVENT_BUS.register(new ConfigInGame());
		
		// Achievements
		registerAchievements();
	}
	
	public void postInit(FMLPostInitializationEvent e) {}
	
	protected void registerAchievements() {
    	
		// Achievement parameters: CommandRegisterName  LangReference  pageX  pageY  Icon  RequiredAchievement
		
		/*
		 * Name explanation from https://ghibli.fandom.com/wiki/Laputa:_Castle_in_the_Sky
		 * 
		 * There are three decipher the morse code in the film, which was never decoded fully. In the Japanese show Tsukai! Akashiya TV
		 * a former soldier reveals its hidden meaning. The first code can be heard in the first chapter of the film. Muska take a call,
		 * before he got knocked out by Sheeta hitting with an empty bottle.
		 * 
		 * In the past, fans have dismissed this message as jibberish, recording it as a repetition of a series of dots and dashes […_ …_ …_],
		 * which translates to nothing more than V V V. However, Sakai and his soldier mates, who have experience in deciphering codes with
		 * no definite beginning and end, discovered that the message contained the code [.._. .. _.. . ._.. .. _ _._ _ ], which spells out the word fidelity.
		 */
		
		VillageNames.laputa = new Achievement(Reference.MOD_ID.toLowerCase()+".achievement.laputa", "laputa",
    			1, -2, Blocks.red_flower, (Achievement)null).initIndependentStat().registerStat();//.setSpecial();
		
		VillageNames.maxrep = new Achievement(Reference.MOD_ID.toLowerCase()+".achievement.maxrep", "maxrep",
    			3, 0, Items.emerald, (Achievement)null).initIndependentStat().registerStat();
    	
    	VillageNames.archaeologist = new Achievement(Reference.MOD_ID.toLowerCase()+".achievement.archaeologist", "archaeologist",
    			-1, 0, ModItems.codex, (Achievement)null).func_150953_b(JsonSerializableSet.class).registerStat();
    	
    	VillageNames.ghosttown = new Achievement(Reference.MOD_ID.toLowerCase()+".achievement.ghosttown", "ghosttown",
    			0, 2, ModItems.villageBook, (Achievement)null).initIndependentStat().registerStat();
    	
    	VillageNames.minrep = new Achievement(Reference.MOD_ID.toLowerCase()+".achievement.minrep", "minrep",
    			2, 2, new ItemStack(Items.skull, 1, 1), (Achievement)null).initIndependentStat().registerStat();
    	
    	
    	// Need to register the stats so that the achievements will be saved
    	//ghosttown.registerStat();
    	
    	// My Little Achievement Page
    	AchievementPage.registerAchievementPage(new AchievementPage(Reference.MOD_NAME, new Achievement[] {
    			VillageNames.maxrep,
    			VillageNames.archaeologist,
    			VillageNames.minrep,
    			VillageNames.ghosttown,
    			VillageNames.laputa
    			}));
    	
    }
	
	public void registerRender() {
	}

	public void registerEvents() {
		
	}
	
}
