package astrotibs.villagenames.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.common.MinecraftForge;

import astrotibs.villagenames.block.color.RenderGlazedTerracotta;
import astrotibs.villagenames.client.renderer.entity.RenderVillagerModern;
import astrotibs.villagenames.client.renderer.entity.RenderZombieVillagerModern;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.integration.antiqueatlas.VillageWatcherAA;
import astrotibs.villagenames.prismarine.guardian.audio.GuardianSound;
import astrotibs.villagenames.prismarine.guardian.entity.monster.EntityGuardian;
import astrotibs.villagenames.prismarine.guardian.renderer.RenderGuardian;
import astrotibs.villagenames.prismarine.render.RegisterRenderPrismarine;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    public static int renderGlazedTerracotta;

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);

        if (Loader.isModLoaded(Reference.ANTIQUE_ATLAS_MODID)) {
            VillageWatcherAA.registerTextures();
        }
    }

    @Override
    public void registerRender() {
        if (GeneralConfig.addConcrete) {
            renderGlazedTerracotta = RenderingRegistry.getNextAvailableRenderId();
            RenderingRegistry.registerBlockHandler(renderGlazedTerracotta, new RenderGlazedTerracotta());
        }

        if (GeneralConfig.modernVillagerSkins) {
            // RenderingRegistry.registerEntityRenderingHandler(EntityZombie.class, new VNRenderZombie()); // Villagers
            // with multiple professions
            RenderingRegistry.registerEntityRenderingHandler(EntityVillager.class, new RenderVillagerModern()); // Modern
                                                                                                                // modular
                                                                                                                // villagers
        }
        if (GeneralConfig.modernZombieSkins) {
            RenderingRegistry.registerEntityRenderingHandler(EntityZombie.class, new RenderZombieVillagerModern()); // Modern
                                                                                                                    // modular
                                                                                                                    // zombie
                                                                                                                    // villagers
        }

        if (GeneralConfig.addOceanMonuments) {
            RenderingRegistry.registerEntityRenderingHandler(EntityGuardian.class, new RenderGuardian());
        }
    }

    @Override
    public void registerEvents() {
        super.registerEvents();
        if (GeneralConfig.addOceanMonuments) {
            FMLCommonHandler.instance()
                .bus()
                .register(RegisterRenderPrismarine.INSTANCE);
            MinecraftForge.EVENT_BUS.register(RegisterRenderPrismarine.INSTANCE);
            // RenderingRegistry.registerEntityRenderingHandler(EntityGuardian.class, new RenderGuardian());
        }
    }

    public static void handleHealthUpdate(EntityGuardian guardian) {
        Minecraft.getMinecraft()
            .getSoundHandler()
            .playSound(new GuardianSound(guardian));
    }
    /*
     * public static void playEndPortalSound() { //Minecraft.getMinecraft().getSoundHandler().playSound(new
     * EndPortalActivateSound() ); Minecraft.getMinecraft().getSoundHandler().playSound(
     * PositionedSoundRecord.func_147673_a(new ResourceLocation("VillageNames:block.end_portal.endportal")) ); }
     */
}
