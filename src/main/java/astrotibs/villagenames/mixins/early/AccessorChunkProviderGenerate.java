package astrotibs.villagenames.mixins.early;

import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChunkProviderGenerate.class)
public interface AccessorChunkProviderGenerate {

    @Accessor("strongholdGenerator")
    MapGenStronghold getStrongholdGenerator();

    @Accessor("villageGenerator")
    MapGenVillage getVillageGenerator();

    @Accessor("mineshaftGenerator")
    MapGenMineshaft getMineshaftGenerator();

    @Accessor("scatteredFeatureGenerator")
    MapGenScatteredFeature getScatteredFeatureGenerator();
}