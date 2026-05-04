package astrotibs.villagenames.mixins.early;

import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.util.StatCollector;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(GuiMerchant.class)
public abstract class MixinGuiMerchant {
    @ModifyArg(
        method = "drawGuiContainerForegroundLayer",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I",
            ordinal = 0
        )
    )
    private String villagenames$replaceTitleText(String original) {
        return StatCollector.translateToLocalFormatted("gui.Villager.name", original);
    }
}
