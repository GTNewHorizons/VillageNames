package astrotibs.villagenames.prismarine.render;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.client.event.TextureStitchEvent;

import astrotibs.villagenames.block.ModBlocksVN;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.prismarine.block.BlockPrismarine;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RegisterRenderPrismarine {

    public static final RegisterRenderPrismarine INSTANCE = new RegisterRenderPrismarine();

    private RegisterRenderPrismarine() {}

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void loadTextures(TextureStitchEvent.Pre event) {
        if (GeneralConfig.addPrismarine) if (event.map.getTextureType() == 0) {
            TextureAtlasSprite icon = new PrismarineIcon("prismarine_rough");
            if (event.map.setTextureEntry("prismarine_rough", icon)) {
                ((BlockPrismarine) ModBlocksVN.blockPrismarine).setIcon(0, icon);
            } else {
                ((BlockPrismarine) ModBlocksVN.blockPrismarine).setIcon(0, event.map.registerIcon("prismarine_rough"));
            }

        }
    }

}
