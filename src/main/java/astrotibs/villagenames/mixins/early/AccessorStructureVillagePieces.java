package astrotibs.villagenames.mixins.early;

import net.minecraft.world.gen.structure.StructureVillagePieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.Random;

@Mixin(StructureVillagePieces.class)
public interface AccessorStructureVillagePieces {

    @Invoker("getNextVillageComponent")
    static StructureVillagePieces.Village invokeGetNextVillageComponent(
            StructureVillagePieces.Start start,
            List components,
            Random random,
            int x,
            int y,
            int z,
            int coordBaseMode,
            int componentType
    ) {
        throw new AssertionError();
    }
}