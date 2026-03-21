package astrotibs.villagenames.mixins.early;

import net.minecraft.world.gen.structure.StructureVillagePieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StructureVillagePieces.Village.class)
public interface AccessorStructureVillagePiecesVillage {

    @Accessor(value = "startPiece", remap = false)
    StructureVillagePieces.Start getStartPiece();
}
