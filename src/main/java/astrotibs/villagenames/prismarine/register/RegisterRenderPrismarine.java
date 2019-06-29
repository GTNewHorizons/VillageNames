package astrotibs.villagenames.prismarine.register;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.prismarine.block.BlockPrismarine;
import astrotibs.villagenames.prismarine.render.PrismarineIcon;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.client.event.TextureStitchEvent;

public class RegisterRenderPrismarine {
	
	
	public static final RegisterRenderPrismarine INSTANCE = new RegisterRenderPrismarine();

	private RegisterRenderPrismarine() {
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void loadTextures(TextureStitchEvent.Pre event) {
		if (GeneralConfig.addOceanMonuments)
			if (event.map.getTextureType() == 0) {
				TextureAtlasSprite icon = new PrismarineIcon("prismarine_rough");
				if (event.map.setTextureEntry("prismarine_rough", icon)) {
					((BlockPrismarine) ModBlocksPrismarine.blockPrismarine).setIcon(0, icon);
				}
				else {
					((BlockPrismarine) ModBlocksPrismarine.blockPrismarine).setIcon(0, event.map.registerIcon("prismarine_rough"));
				}
					
			}
	}
	
}