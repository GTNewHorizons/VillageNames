package astrotibs.villagenames.igloo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraftforge.common.BiomeDictionary;

import astrotibs.villagenames.config.GeneralConfig;

public class VNMapGenIgloo extends MapGenStructure {

    private static List biomelist = Arrays
        .asList(new BiomeGenBase[] { BiomeGenBase.icePlains, BiomeGenBase.coldTaiga });
    /** contains possible spawns for scattered features */
    private List scatteredFeatureSpawnList;
    /** the maximum distance between scattered features */
    private static int maxDistanceBetweenScatteredFeatures;
    /** the minimum distance between scattered features */
    private static int minDistanceBetweenScatteredFeatures;

    public VNMapGenIgloo() {
        this.scatteredFeatureSpawnList = new ArrayList();
        this.maxDistanceBetweenScatteredFeatures = 32;
        this.minDistanceBetweenScatteredFeatures = 8;
    }

    public VNMapGenIgloo(Map p_i2061_1_) {
        this();
        Iterator iterator = p_i2061_1_.entrySet()
            .iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();

            if (((String) entry.getKey()).equals("distance")) {
                this.maxDistanceBetweenScatteredFeatures = MathHelper.parseIntWithDefaultAndMax(
                    (String) entry.getValue(),
                    this.maxDistanceBetweenScatteredFeatures,
                    this.minDistanceBetweenScatteredFeatures + 1);
            }
        }
    }

    public String func_143025_a() {
        return "Temple";
    }

    @Override
    public boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
        return canSpawnStructureAtCoords(this.worldObj, chunkX, chunkZ);
    }

    protected static boolean canSpawnStructureAtCoords(World worldIn, int chunkX, int chunkZ) {
        int k = chunkX;
        int l = chunkZ;

        if (chunkX < 0) {
            // p_75047_1_ -= this.maxDistanceBetweenScatteredFeatures - 1;
            chunkX -= maxDistanceBetweenScatteredFeatures - 1;
        }

        if (chunkZ < 0) {
            // p_75047_2_ -= this.maxDistanceBetweenScatteredFeatures - 1;
            chunkZ -= maxDistanceBetweenScatteredFeatures - 1;
        }

        int i1 = chunkX / maxDistanceBetweenScatteredFeatures;// this.maxDistanceBetweenScatteredFeatures;
        int j1 = chunkZ / maxDistanceBetweenScatteredFeatures;// this.maxDistanceBetweenScatteredFeatures;
        Random random = worldIn.setRandomSeed(i1, j1, 14357617);// this.worldObj.setRandomSeed(i1, j1, 14357617);
        i1 *= maxDistanceBetweenScatteredFeatures;// this.maxDistanceBetweenScatteredFeatures;
        j1 *= maxDistanceBetweenScatteredFeatures;// this.maxDistanceBetweenScatteredFeatures;
        i1 += random.nextInt(maxDistanceBetweenScatteredFeatures - minDistanceBetweenScatteredFeatures);// random.nextInt(this.maxDistanceBetweenScatteredFeatures
                                                                                                        // -
                                                                                                        // this.minDistanceBetweenScatteredFeatures);
        j1 += random.nextInt(maxDistanceBetweenScatteredFeatures - minDistanceBetweenScatteredFeatures);// random.nextInt(this.maxDistanceBetweenScatteredFeatures
                                                                                                        // -
                                                                                                        // this.minDistanceBetweenScatteredFeatures);

        if (k == i1 && l == j1) {
            BiomeGenBase biomegenbase = worldIn.getWorldChunkManager()
                .getBiomeGenAt(k * 16 + 8, l * 16 + 8);// this.worldObj.getWorldChunkManager().getBiomeGenAt(k * 16 + 8,
                                                       // l * 16 + 8);
            Iterator iterator = biomelist.iterator();

            while (iterator.hasNext()) {
                BiomeGenBase biomegenbase1 = (BiomeGenBase) iterator.next();

                if (biomegenbase == biomegenbase1) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        return new VNMapGenIgloo.Start(this.worldObj, this.rand, chunkX, chunkZ);
    }

    public boolean func_143030_a(int p_143030_1_, int p_143030_2_, int p_143030_3_) {
        StructureStart structurestart = this.func_143028_c(p_143030_1_, p_143030_2_, p_143030_3_);

        if (structurestart != null && structurestart instanceof VNMapGenIgloo.Start
            && !structurestart.getComponents()
                .isEmpty()) {
            StructureComponent structurecomponent = (StructureComponent) structurestart.getComponents()
                .getFirst();
            return false;
        } else {
            return false;
        }
    }

    /**
     * returns possible spawns for scattered features
     */
    public List getScatteredFeatureSpawnList() {
        return this.scatteredFeatureSpawnList;
    }

    public static class Start extends StructureStart {

        public Start() {}

        public Start(World worldIn, Random rand, int chunkX, int chunkZ) {
            super(chunkX, chunkZ);
            BiomeGenBase biomegenbase = worldIn.getBiomeGenForCoords(chunkX * 16 + 8, chunkZ * 16 + 8);

            // Get a list of tags for this biome
            BiomeDictionary.Type[] typeTags = BiomeDictionary.getTypesForBiome(biomegenbase);

            boolean isSnowy = false;
            for (BiomeDictionary.Type type : typeTags) {
                if (type == BiomeDictionary.Type.SNOWY) {
                    isSnowy = true;
                }
                // Invalid biomes
                if (type == BiomeDictionary.Type.HILLS) {
                    isSnowy = false;
                    break;
                }
                if (type == BiomeDictionary.Type.MOUNTAIN) {
                    isSnowy = false;
                    break;
                }
                if (type == BiomeDictionary.Type.OCEAN) {
                    isSnowy = false;
                    break;
                }
                if (type == BiomeDictionary.Type.RIVER) {
                    isSnowy = false;
                    break;
                }
            }

            if ((biomegenbase == BiomeGenBase.icePlains || biomegenbase == BiomeGenBase.coldTaiga)
                || (GeneralConfig.biomedictIgloos && isSnowy)) {
                VNComponentIglooPieces.Igloo igloo = new VNComponentIglooPieces.Igloo(rand, chunkX * 16, chunkZ * 16);
                this.components.add(igloo);
            }

            this.updateBoundingBox();
        }
    }
}
