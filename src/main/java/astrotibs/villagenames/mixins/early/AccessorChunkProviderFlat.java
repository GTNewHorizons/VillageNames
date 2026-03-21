package astrotibs.villagenames.mixins.early;

import java.util.List;

import net.minecraft.world.gen.ChunkProviderFlat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChunkProviderFlat.class)
public interface AccessorChunkProviderFlat {

    @Accessor("structureGenerators")
    List getStructureGenerators();
}