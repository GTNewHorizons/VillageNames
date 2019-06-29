package astrotibs.villagenames.proxy;

import astrotibs.villagenames.VillageNames;
import astrotibs.villagenames.block.color.RenderGlazedTerracotta;
import astrotibs.villagenames.client.renderer.entity.RenderVillagerModern;
import astrotibs.villagenames.client.renderer.entity.RenderZombieVillagerModern;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.prismarine.guardian.audio.GuardianSound;
import astrotibs.villagenames.prismarine.guardian.entity.monster.EntityGuardian;
import astrotibs.villagenames.prismarine.guardian.renderer.RenderGuardian;
import astrotibs.villagenames.prismarine.register.RegisterRenderPrismarine;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
	
	public static int renderGlazedTerracotta;
	
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
		//VillageNames.versionChecker = new VersionChecker();
		Thread versionCheckThread = new Thread(VillageNames.versionChecker, "Version Check");
		versionCheckThread.start();
	}
	
	@Override
	public void registerRender() {
		if (GeneralConfig.addConcrete) {
			renderGlazedTerracotta = RenderingRegistry.getNextAvailableRenderId();
			RenderingRegistry.registerBlockHandler(renderGlazedTerracotta, new RenderGlazedTerracotta());
		}
		
		// Changed in v3.1
        if (GeneralConfig.modernVillagerSkins)
        {
        	//RenderingRegistry.registerEntityRenderingHandler(EntityZombie.class, new VNRenderZombie()); // Villagers with multiple professions
        	RenderingRegistry.registerEntityRenderingHandler(EntityVillager.class, new RenderVillagerModern()); // Modern modular villagers
        	RenderingRegistry.registerEntityRenderingHandler(EntityZombie.class, new RenderZombieVillagerModern());  // Modern modular zombie villagers
        }
        	
	}
	
	@Override
	public void registerEvents() {
		super.registerEvents();
		if (GeneralConfig.addOceanMonuments) {
			FMLCommonHandler.instance().bus().register(RegisterRenderPrismarine.INSTANCE);
			MinecraftForge.EVENT_BUS.register(RegisterRenderPrismarine.INSTANCE);
			RenderingRegistry.registerEntityRenderingHandler(EntityGuardian.class, new RenderGuardian());
		}
	}
	
	public static void handleHealthUpdate(EntityGuardian guardian) {
		Minecraft.getMinecraft().getSoundHandler().playSound(new GuardianSound(guardian));
	}
	/*
	public static void playEndPortalSound() {
		//Minecraft.getMinecraft().getSoundHandler().playSound(new EndPortalActivateSound() );
		Minecraft.getMinecraft().getSoundHandler().playSound(
				PositionedSoundRecord.func_147673_a(new ResourceLocation("VillageNames:block.end_portal.endportal"))
				);
	}
	*/
}
