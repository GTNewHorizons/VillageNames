package astrotibs.villagenames.mixins.early;

import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.StatCollector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(GuiMerchant.class)
public abstract class MixinGuiMerchant {

    @Shadow
    public abstract IMerchant func_147035_g();

    @ModifyArg(
            method = "drawGuiContainerForegroundLayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I",
                    ordinal = 0
            ),
            index = 0
    )
    private String villagenames$replaceMerchantGuiTitle(String originalTitle) {
        final IMerchant merchant = this.func_147035_g();
        String merchantName = originalTitle;

        if (merchant instanceof EntityLiving) {
            final EntityLiving living = (EntityLiving) merchant;
            if (living.hasCustomNameTag()) {
                merchantName = living.getCustomNameTag();
            } else {
                merchantName = living.getCommandSenderName();
            }
        } else if (merchant instanceof Entity) {
            merchantName = ((Entity) merchant).getCommandSenderName();
        }

        return StatCollector.translateToLocalFormatted("gui.Villager.name", merchantName);
    }
}
