package astrotibs.villagenames.mixins.early;

import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChunkProviderHell.class)
public interface AccessorChunkProviderHell {

    @Accessor("genNetherBridge")
    MapGenNetherBridge getGenNetherBridge();
}