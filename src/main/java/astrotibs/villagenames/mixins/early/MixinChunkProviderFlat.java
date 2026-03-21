package astrotibs.villagenames.mixins.early;

import astrotibs.villagenames.config.village.VillageGeneratorConfigHandler;
import astrotibs.villagenames.village.MapGenVillageVN;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.structure.MapGenVillage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

@Mixin(ChunkProviderFlat.class)
public class MixinChunkProviderFlat {

    @Redirect(
            method = "<init>",
            at = @At(
                    value = "NEW",
                    target = "(Ljava/util/Map;)Lnet/minecraft/world/gen/structure/MapGenVillage;"
            )
    )
    private MapGenVillage villagenames$replaceFlatVillageGenerator(Map map) {
        if (VillageGeneratorConfigHandler.newVillageGenerator) {
            return new MapGenVillageVN();
        }
        return new MapGenVillage(map);
    }
}