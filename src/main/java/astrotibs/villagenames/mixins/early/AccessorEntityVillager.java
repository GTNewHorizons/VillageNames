package astrotibs.villagenames.mixins.early;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.village.MerchantRecipeList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityVillager.class)
public interface AccessorEntityVillager {

    @Accessor("buyingList")
    MerchantRecipeList getBuyingList();

    @Accessor("buyingList")
    void setBuyingList(MerchantRecipeList buyingList);

    @Invoker("addDefaultEquipmentAndRecipies")
    void invokeAddDefaultEquipmentAndRecipies(int careerLevel);
}