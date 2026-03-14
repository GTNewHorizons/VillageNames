package astrotibs.villagenames.igloo;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.CUSTOM;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.event.terraingen.TerrainGen;

import cpw.mods.fml.common.IWorldGenerator;

public class IglooGeneratorIWG implements IWorldGenerator {

    private static VNMapGenIgloo iglooGenerator = new VNMapGenIgloo();

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
        IChunkProvider chunkProvider) {

        Block[] ablock = new Block[65536];

        if ((world.provider.dimensionId == 0) && (world.getWorldInfo()
            .isMapFeaturesEnabled())) {
            // THIS BLOCK RUNS REGARDLESS OF WHETHER GENERATION TYPE IS VANILLA, ALTERNATE, REALISTIC
            // iglooGenerator = (VNMapGenIgloo) TerrainGen.getModdedMapGen(new VNMapGenIgloo(),
            // EventType.SCATTERED_FEATURE);
            iglooGenerator = (VNMapGenIgloo) TerrainGen.getModdedMapGen(new VNMapGenIgloo(), CUSTOM); // Fixed for
                                                                                                      // version 3.0.2

            // This block only seems to fire with a vanilla world generator :(
            if (VNMapGenIgloo.canSpawnStructureAtCoords(world, chunkX, chunkZ))// .canSpawnStructureAtCoords(world,
                                                                               // chunkX, chunkZ))
            {
                int chunkOffset = 2;
                for (int OM_x = -chunkOffset; OM_x <= chunkOffset - 1; OM_x++) {
                    for (int OM_z = -chunkOffset; OM_z <= chunkOffset - 1; OM_z++) {

                        this.iglooGenerator.func_151539_a(world.getChunkProvider(), world, chunkX, chunkZ, ablock); // THIS
                                                                                                                    // needed
                                                                                                                    // for
                                                                                                                    // chunk
                                                                                                                    // generation

                        try {
                            boolean generateInChunk = this.iglooGenerator
                                .generateStructuresInChunk(world, rand, chunkX + OM_x, chunkZ + OM_z); // THIS needed
                                                                                                       // for chunk
                                                                                                       // generation
                        } catch (Exception e) {}

                    }
                }

            }
        }

    }

    public static VNMapGenIgloo getIglooGenerator() {
        return iglooGenerator;
    }

}
