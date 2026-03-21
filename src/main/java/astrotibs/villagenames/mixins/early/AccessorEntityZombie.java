package astrotibs.villagenames.mixins.early;

import net.minecraft.entity.monster.EntityZombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityZombie.class)
public interface AccessorEntityZombie {

    @Accessor("conversionTime")
    int getConversionTime();

    @Accessor("conversionTime")
    void setConversionTime(int conversionTime);

    @Invoker("getConversionTimeBoost")
    int invokeGetConversionTimeBoost();
}