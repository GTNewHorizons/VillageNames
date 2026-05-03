package astrotibs.villagenames.mixins;

import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder;

import javax.annotation.Nonnull;

public enum Mixins implements IMixins {

    MINECRAFT(Side.COMMON, "AccessorChunkProviderFlat", "AccessorChunkProviderGenerate", "AccessorChunkProviderHell",
            "AccessorEntityVillager", "AccessorEntityZombie", "AccessorStructureVillagePieces", "AccessorStructureVillagePiecesVillage",
            "AccessorTextureAtlasSprite", "MixinChunkProviderFlat"),
    MINECRAFT_CLIENT(Side.CLIENT, "MixinGuiMerchant")

    ;

    private final MixinBuilder builder;

    Mixins(Side side, String... mixins) {
        this.builder = new MixinBuilder().addSidedMixins(side, mixins)
                .setPhase(Phase.EARLY);
    }

    @Nonnull
    @Override
    public MixinBuilder getBuilder() {
        return builder;
    }
}
