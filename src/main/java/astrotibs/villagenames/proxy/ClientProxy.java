package astrotibs.villagenames.proxy;

import astrotibs.villagenames.VillageNames;
import astrotibs.villagenames.block.color.RenderGlazedTerracotta;
import astrotibs.villagenames.prismarine.register.RegisterRenderPrismarine;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
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
	}
	
	@Override
	public void registerRender() {
		renderGlazedTerracotta = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(renderGlazedTerracotta, new RenderGlazedTerracotta());
	}
	
	@Override
	public void registerEvents() {
		super.registerEvents();
		if (VillageNames.addOceanMonuments) {
			FMLCommonHandler.instance().bus().register(RegisterRenderPrismarine.INSTANCE);
			MinecraftForge.EVENT_BUS.register(RegisterRenderPrismarine.INSTANCE);
		}
	}
	
}
