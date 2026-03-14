package astrotibs.villagenames.prismarine.monument;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureStart;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import astrotibs.villagenames.prismarine.guardian.entity.monster.EntityGuardian;
import astrotibs.villagenames.prismarine.minecraft.EnumFacing189;

public class StructureOceanMonument extends MapGenStructure {

    // Added static to allow access
    private static int field_175800_f;
    private static int field_175801_g;
    public static final List<BiomeGenBase> field_175802_d = Arrays.<BiomeGenBase>asList(
        new BiomeGenBase[] { BiomeGenBase.ocean, BiomeGenBase.deepOcean, BiomeGenBase.river, BiomeGenBase.frozenOcean,
            BiomeGenBase.frozenRiver });
    // Here I've made an integer array version of field_175802_d for use in my own areBiomesViableVN method
    public static final int[] waterBiomesInt = new int[] { BiomeGenBase.ocean.biomeID, BiomeGenBase.deepOcean.biomeID,
        BiomeGenBase.river.biomeID, BiomeGenBase.frozenOcean.biomeID, BiomeGenBase.frozenRiver.biomeID };

    private static final List<BiomeGenBase.SpawnListEntry> MONUMENT_ENEMIES = Lists.<BiomeGenBase.SpawnListEntry>newArrayList(); // Used
                                                                                                                                 // to
                                                                                                                                 // be
                                                                                                                                 // named
                                                                                                                                 // field_175803_h

    public StructureOceanMonument() {
        field_175800_f = 32; // spacing
        field_175801_g = 5; // separation
    }

    public StructureOceanMonument(Map<String, String> p_i45608_1_) {
        this();

        for (Entry<String, String> entry : p_i45608_1_.entrySet()) {
            if (entry.getKey()
                .equals("spacing")) {
                field_175800_f = MathHelper
                    .parseIntWithDefaultAndMax(entry.getValue(), StructureOceanMonument.field_175800_f, 1);
            } else if (entry.getKey()
                .equals("separation")) {
                    field_175801_g = MathHelper
                        .parseIntWithDefaultAndMax(entry.getValue(), StructureOceanMonument.field_175801_g, 1);
                }
        }
    }

    // public String getStructureName()
    @Override
    public String func_143025_a() {
        return "Monument";
    }

    // I added static to allow this to be accessed, good god
    protected static boolean canSpawnStructureAtCoords(World worldIn, int chunkX, int chunkZ) {
        // This method fires irrespective of chunk generator

        int i = chunkX;
        int j = chunkZ;

        // I think these functions ensure that structured spawn symmetrically
        // likely across the world Cartesian axes
        if (chunkX < 0) {
            chunkX -= field_175800_f - 1;
        }

        if (chunkZ < 0) {
            chunkZ -= field_175800_f - 1;
        }

        // Removed "this." from fields
        // k and l "magnetize" to the nearest chunk that would contain the center of the monument.
        // By way of field_175800_f=32, they ensure that monuments are at least 32 chunks apart from one another.
        int k = chunkX / field_175800_f;
        int l = chunkZ / field_175800_f;
        Random random = worldIn.setRandomSeed(k, l, 10387313);
        k = k * field_175800_f;
        l = l * field_175800_f;
        // Add a little random offset to ensure they're not all aligned to a 32x32-chunk grid
        k = k + (random.nextInt(field_175800_f - field_175801_g) + random.nextInt(field_175800_f - field_175801_g)) / 2;
        l = l + (random.nextInt(field_175800_f - field_175801_g) + random.nextInt(field_175800_f - field_175801_g)) / 2;
        String biomeString;

        if (i == k && j == l) // The inputted x and z chunks are the same as the one selected for the monument!
        {

            /*
             * Determines whether or not the "center" block of the monument is in a Deep Ocean biome.
             * "Center" means the south-east block out of the four central ones.
             * In other words: the north-west-most block of the south-east quadrant of the monument.
             */

            if (!areBiomesViableVN(i * 16 + 8, j * 16 + 8, 16, new String[] { "deepocean", "deep ocean" }, worldIn)) // Changed
                                                                                                                     // from
                                                                                                                     // 29
            {
                return false;
            }

            /*
             * This appears to be the holdover condition that may not fire with non-vanilla chunk providers
             */
            // Determines whether EVERY BLOCK within +/-15 is in a "legal" biome for a monument (field_175802_d)
            boolean flag = areBiomesViableVN(i * 16 + 8, j * 16 + 8, 29, new String[] { "ocean", "river" }, worldIn); // "ocean",
                                                                                                                      // "river"
            if (flag) {
                return true;
            }

        }

        return false;
    }

    /*
     * Maybe I need my own areBiomesViable function?
     */

    /**
     * checks given Chunk's Biomes against List of allowed ones
     */
    public static boolean areBiomesViableVN(int xChunkCenter, int zChunkCenter, int radius, String[] allowedBiomes,
        World worldIn) {
        IntCache.resetIntCache();
        // Do I need to put these in parentheses to enforce order of operations!?
        int l = xChunkCenter - (radius >> 2); // ~7 blocks west of monument center
        int i1 = zChunkCenter - (radius >> 2); // ~7 blocks north of monument center
        int j1 = xChunkCenter + (radius >> 2); // ~7 blocks east of monument center
        int k1 = zChunkCenter + (radius >> 2); // ~7 bocks south of monument center
        int l1 = j1 - l + 1;
        int i2 = k1 - i1 + 1;
        int biomeInt;
        int strNumber = 0;
        String biomeStr = "";
        int breakFlag = -1;
        int biomeSearchCompare;

        try {
            for (int ind = 0; ind < l1 * i2; ind++) {
                // Searches l1-by-i2 block around center block and checks its biomeID
                breakFlag = -1;
                biomeStr = worldIn.getBiomeGenForCoords(l + (ind / l1), i1 + (ind % i2)).biomeName.toLowerCase();
                for (strNumber = 0; strNumber < allowedBiomes.length; strNumber++) {
                    biomeSearchCompare = Math.max(biomeStr.indexOf(allowedBiomes[strNumber]), breakFlag);
                    if (breakFlag >= 0) {
                        break;
                    } // This block is in one of the allowed biomes; move to the next block
                    else {
                        breakFlag = biomeSearchCompare;
                    }
                }
                if (breakFlag < 0) {
                    return false;
                } // The block just checked was not in ANY of the allowed biomes.
            }
            return true; // The entire 15x15 patch around the monument center is a combination of river/ocean/deep ocean
                         // etc
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Layer");
            crashreportcategory.addCrashSection("x", Integer.valueOf(xChunkCenter));
            crashreportcategory.addCrashSection("z", Integer.valueOf(zChunkCenter));
            crashreportcategory.addCrashSection("radius", Integer.valueOf(radius));
            throw new ReportedException(crashreport);
        }

    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        return new StructureOceanMonument.StartMonument(this.worldObj, this.rand, chunkX, chunkZ);
    }

    public static List<BiomeGenBase.SpawnListEntry> getMonsters()// 1.8 is named func_175799_b()
    {
        return MONUMENT_ENEMIES;
    }

    static {
        // Inserted to allow Guardian generation!
        MONUMENT_ENEMIES.add(new BiomeGenBase.SpawnListEntry(EntityGuardian.class, 1, 2, 4));
    }

    public static class StartMonument extends StructureStart {

        private Set<ChunkCoordIntPair> field_175791_c = Sets.<ChunkCoordIntPair>newHashSet(); // processed (1.9.4)
        private boolean field_175790_d; // wasCreated (1.9.4)
        private boolean func_175788_a_toggle = true; // Exists so that I can mimic 1.8's
                                                     // MapGenStructure.generateStructure requirement:
                                                     // structurestart.func_175788_a(chunkCoord)

        public StartMonument() {}

        public StartMonument(World worldIn, Random random, int chunkX, int chunkZ) {
            super(chunkX, chunkZ);
            this.func_175789_b(worldIn, random, chunkX, chunkZ);
        }

        // Only called by Monument stuff
        private void func_175789_b(World worldIn, Random random, int chunkX, int chunkZ) {
            random.setSeed(worldIn.getSeed());
            long i = random.nextLong();
            long j = random.nextLong();
            long k = chunkX * i;
            long l = chunkZ * j;
            random.setSeed(k ^ l ^ worldIn.getSeed());
            int i1 = chunkX * 16 + 8 - 29;
            int j1 = chunkZ * 16 + 8 - 29;
            EnumFacing189 enumfacing = EnumFacing189.Plane.HORIZONTAL.random(random);
            this.components.add(new StructureOceanMonumentPieces.MonumentBuilding(random, i1, j1, enumfacing));
            this.updateBoundingBox();
            this.field_175790_d = true;
        }

        /**
         * Keeps iterating Structure Pieces and spawning them until the checks tell it to stop
         * I THINK this is the correct method in 1.7.10...
         */
        @Override
        public void generateStructure(World worldIn, Random rand, StructureBoundingBox structurebb) {
            // Addeed this to reverse-engineer 1.8's generateStructure(World, Random, ChunkCoordIntPair)
            ChunkCoordIntPair chunkCoord = new ChunkCoordIntPair(
                (structurebb.minX - 8) >> 4,
                (structurebb.minZ - 8) >> 4);

            if (!this.field_175790_d) {
                this.components.clear();
                this.func_175789_b(worldIn, rand, this.func_143019_e(), this.func_143018_f());
            }

            // I'm calling the below functions explicitly here
            this.func_175787_b(chunkCoord);
            // this.func_175788_a_toggle = this.func_175788_a(chunkCoord);

            super.generateStructure(worldIn, rand, structurebb);
        }

        // I'm adding this in to mimic 1.8's MapGenStructure.generateStructure requirement:
        // structurestart.func_175788_a(chunkCoord)
        @Override
        public boolean isSizeableStructure() {
            return this.func_175788_a_toggle;
        }

        /*
         * This function is only in 1.8.9. Returns "true".
         * It is fired during MapGenStructure.generateStructuresInChunk()
         * if (structurestart.isSizeableStructure() && structurestart.func_175788_a(chunkCoord) &&
         * structurestart.getBoundingBox().intersectsWith(i, j, i + 15, j + 15))
         * The above if condition should be FALSE whenever return this.field_175791_c.contains(pair) and TRUE otherwise
         * I believe this is used to prevent chunks from spawning in places they should not? Not sure.
         */
        public boolean func_175788_a(ChunkCoordIntPair pair) {
            return this.field_175791_c.contains(pair) ? false : true;// super.func_175788_a(pair);
        }

        /*
         * This function is only in 1.8.9. Returns nothing.
         * It is fired during MapGenStructure.generateStructuresInChunk()
         * when (structurestart.isSizeableStructure() && structurestart.getBoundingBox().intersectsWith(k, l, k + 15, l
         * + 15)) == true
         * after structurestart.generateStructure(p_75051_1_, p_75051_2_, new StructureBoundingBox(k, l, k + 15, l +
         * 15));
         * It is used to compile the chunk coordinates for the 16 chunks involved in the Monument.
         */
        public void func_175787_b(ChunkCoordIntPair pair) {
            // super.func_175787_b(pair)
            this.field_175791_c.add(pair);
        }

        // public void writeToNBT(NBTTagCompound tagCompound)
        @Override
        public void func_143022_a(NBTTagCompound tagCompound) {
            // super.writeToNBT(tagCompound);
            super.func_143022_a(tagCompound);
            NBTTagList nbttaglist = new NBTTagList();

            for (ChunkCoordIntPair chunkcoordintpair : this.field_175791_c) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setInteger("X", chunkcoordintpair.chunkXPos);
                nbttagcompound.setInteger("Z", chunkcoordintpair.chunkZPos);
                nbttaglist.appendTag(nbttagcompound);
            }

            tagCompound.setTag("Processed", nbttaglist);

            tagCompound.setBoolean("ElderGen", true); // Added this so I can retrogen Elder Guardians into
                                                      // previously-generated Monuments
        }

        // public void readFromNBT(NBTTagCompound tagCompound)
        @Override
        public void func_143017_b(NBTTagCompound tagCompound) {
            // super.readFromNBT(tagCompound);
            super.func_143017_b(tagCompound);

            if (tagCompound.hasKey("Processed", 9)) {
                NBTTagList nbttaglist = tagCompound.getTagList("Processed", 10);

                for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                    NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
                    this.field_175791_c
                        .add(new ChunkCoordIntPair(nbttagcompound.getInteger("X"), nbttagcompound.getInteger("Z")));
                }
            }
        }
    }

    @Override
    public boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
        return canSpawnStructureAtCoords(this.worldObj, chunkX, chunkZ);
    }

}
