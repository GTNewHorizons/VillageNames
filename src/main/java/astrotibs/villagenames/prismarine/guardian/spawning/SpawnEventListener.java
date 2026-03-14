package astrotibs.villagenames.prismarine.guardian.spawning;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructureData;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.world.WorldEvent;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.prismarine.guardian.entity.monster.EntityGuardian;
import astrotibs.villagenames.prismarine.monument.StructureOceanMonument;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SpawnEventListener {

    /**
     * WOW. Literal weeks of wracking my brain over how to add Guardian spawns, and I accidentally stumbled on this
     * thread:
     * http://www.minecraftforge.net/forum/topic/17547-spawning-a-custom-entity/
     * which brings EnumHelper to the fore. And to think I was about to abandon that thread as useless.
     */
    // Here I called the monster max number explicitly (70)
    public static final EnumCreatureType waterMonster = EnumHelper.addCreatureType(
        "waterMonster",
        IMob.class,
        EnumCreatureType.monster.getMaxNumberOfCreature(),
        Material.water,
        false,
        false);

    /**
     * This method actively cancels creature types "monster" and "waterMonster" from spawning
     * if their collective sum is greater than the spawn cap for type "monster."
     * 
     * Called by WorldServer to gather a list of all possible entities that can spawn at the specified location.
     * If an entry is added to the list, it needs to be a globally unique instance.
     * The event is called in WorldServer#spawnRandomCreature(EnumCreatureType, int, int, int)
     * Canceling the event will result in a empty list, meaning no entity will be spawned.
     */
    @SubscribeEvent(receiveCanceled = true)
    public void guardianSpawnerAndCap(WorldEvent.PotentialSpawns event) {

        // Here I'm going to tally up all the MONSTER and WATERMONSTER types
        int countMonsterType = event.world.countEntities(EnumCreatureType.monster, true); // total monsters in world
        int countWaterMonsterType = event.world.countEntities(waterMonster, true); // total waterMonsters (Guardians) in
                                                                                   // world

        // Here I count the number of eligible spawning chunks
        if ((event.type == EnumCreatureType.monster || event.type == waterMonster)
            // && (countMonsterType + countWaterMonsterType) > EnumCreatureType.monster.getMaxNumberOfCreature() * 289 /
            // 256 // 289 is 17 (chunks) squared, centered on player
            && this.capMonsterSpawns(event.world, countMonsterType, countWaterMonsterType, true, true)) { // Cap the
                                                                                                          // number of
                                                                                                          // spawns per
                                                                                                          // chunk for
                                                                                                          // monster and
                                                                                                          // waterMonster,
                                                                                                          // collectively
            return;
        }
        // LogHelper.info("Monsters: " + countMonsterType + ", Water Monsters: " + countWaterMonsterType + "; Total: " +
        // (countMonsterType + countWaterMonsterType));

        if ( // If the attempted spawn is a waterMonster, check if the spawn is in water and inside the BB of a
             // Monument.
        event.world.getWorldInfo()
            .getVanillaDimension() == 0 && event.world.getWorldInfo()
                .isMapFeaturesEnabled()
            && event.type == waterMonster
            && GeneralConfig.addOceanMonuments
            && isIntersectingWithAnyMonument(event)) {
            this.getPossibleMonumentCreatures(event); // Populate the waterMonster spawn list with the Monument spawn
                                                      // list (a group of 2-4 Guardians)
        }
    }

    /**
     * This method triggers when the Overworld is loaded. It then attempts to retrogen Elder Guardians
     * into Ocean Monuments that were spawned before the 3.0 update.
     * 
     * WorldEvent.Load is fired when Minecraft loads a world.
     **/
    @SubscribeEvent(receiveCanceled = true)
    public void retroGenElders(WorldEvent.Load event) {

        if (event.world.provider.dimensionId == 0) { // Player is in the Overworld
            World world = event.world;

            // LogHelper.info("Dimension " + event.world.getWorldInfo().getVanillaDimension() + " loaded");
            MapGenStructureData structureData;
            try {
                structureData = (MapGenStructureData) event.world.perWorldStorage
                    .loadData(MapGenStructureData.class, "Monument");
                NBTTagCompound nbttagcompound = structureData.func_143041_a();

                Iterator itr = nbttagcompound.func_150296_c()
                    .iterator();

                while (itr.hasNext()) { // Go through list of already-generated Monuments
                    Object element = itr.next();

                    try {
                        NBTBase nbtbase = nbttagcompound.getTag(element.toString());
                        if (nbtbase.getId() == 10) { // 10 is NBT tag compound, I think
                            NBTTagCompound nbttagcompound2 = (NBTTagCompound) nbtbase;

                            int[] boundingBoxIA = nbttagcompound2.getIntArray("BB");
                            StructureBoundingBox boundingBox = new StructureBoundingBox(boundingBoxIA);

                            // Orientation of the Monument's entrance
                            // 0: NORTH
                            // 1: EAST
                            // 2: SOUTH
                            // 3: WEST

                            boolean hasHadElders = nbttagcompound2.getBoolean(Reference.ELDER_GEN_VN4);

                            if (!hasHadElders) { // This Monument was generated before the 2.1 update, so here's our
                                                 // chance to shove some Elders into it

                                int coordBaseMode = nbttagcompound2.getTagList("Children", 10)
                                    .getCompoundTagAt(0)
                                    .getInteger("O");

                                int chunkXPos = nbttagcompound2.getInteger("ChunkX");
                                int chunkZPos = nbttagcompound2.getInteger("ChunkZ");
                                int i = (chunkXPos << 4) + 8;
                                int j = (chunkZPos << 4) + 8;

                                // Calculate the center position of the monument, which will be used to position the
                                // three elders
                                double monumentXCenter = (boundingBox.maxX + boundingBox.minX) / 2.0D;
                                double monumentZCenter = (boundingBox.maxZ + boundingBox.minZ) / 2.0D;

                                // Spawn an Elder in the Penthouse. Accurate to within 1x1x0 block
                                this.spawnElder(event.world, boundingBox, monumentXCenter, 53.0D, monumentZCenter);

                                int seedOffset = ((int) world.getSeed()) % 2; // This is to modulate the initial
                                                                              // placements of the elders

                                // Spawn an Elder in the Left wing. Accurate to within 3x3x3 block
                                this.spawnElder(
                                    event.world,
                                    boundingBox,
                                    monumentXCenter + ((coordBaseMode % 2 == 0 ? 16.5D : 13.5D)
                                        * (coordBaseMode > 1 ? -1.0D : 1.0D)),
                                    seedOffset == 0 ? 45.0D : 41.0D, // Supposed to be 42 OR 45. Floored to 41 because
                                                                     // he hits his head lmao
                                    monumentZCenter + ((coordBaseMode % 2 == 0 ? 13.5D : 16.5D)
                                        * (Math.abs((2 * coordBaseMode) - 3) > 2 ? -1.0D : 1.0D)));

                                // Spawn an Elder in the Right wing. Accurate to within 3x3x3 block
                                this.spawnElder(
                                    event.world,
                                    boundingBox,
                                    monumentXCenter + ((coordBaseMode % 2 == 0 ? 16.5D : 13.5D)
                                        * (Math.abs((2 * coordBaseMode) - 3) > 2 ? -1.0D : 1.0D)),
                                    seedOffset == 0 ? 41.0D : 45.0D, // Supposed to be 42 OR 45
                                    monumentZCenter + ((coordBaseMode % 2 == 0 ? 13.5D : 16.5D)
                                        * (coordBaseMode < 2 ? -1.0D : 1.0D)));

                                // Set the tag to "true" so that we won't generate elders again
                                nbttagcompound2.setBoolean(Reference.ELDER_GEN_VN4, true);
                                structureData.setDirty(true);
                            }
                        }
                    } catch (Exception e) {
                        LogHelper.warn("Failed to evaluate Elder Guardian status of Monument");
                    }

                }
            } catch (Exception e) { // This fails when the Monument list is empty (i.e. none have been generated).
                if (GeneralConfig.debugMessages) {
                    LogHelper.warn("Failed to load Monument list, or none exists.");
                }
            }
        }
    }

    /**
     * Spawns an Elder Guardian at the specified x, y, z locations
     */
    protected boolean spawnElder(World worldIn, StructureBoundingBox bb, double x, double y, double z) {

        if (bb.isVecInside((int) x, (int) y, (int) z)) {
            EntityGuardian elderGuardian = new EntityGuardian(worldIn);
            elderGuardian.setElder(true);
            elderGuardian.heal(elderGuardian.getMaxHealth());
            elderGuardian.setLocationAndAngles(x + 0.5D, y, z + 0.5D, 0.0F, 0.0F);
            elderGuardian.onSpawnWithEgg((IEntityLivingData) null); // Hopefully this will still allow a guardian to
                                                                    // spawn.

            int i = MathHelper.floor_double(elderGuardian.posX / 16.0D);
            int j = MathHelper.floor_double(elderGuardian.posZ / 16.0D);
            boolean flag = elderGuardian.forceSpawn;

            worldIn.getChunkFromChunkCoords(i, j)
                .addEntity(elderGuardian);
            worldIn.loadedEntityList.add(elderGuardian);
            worldIn.onEntityAdded(elderGuardian);

            if (GeneralConfig.debugMessages) {
                LogHelper.info("Elder Guardian retroactively spawned at " + (x + 0.5D) + " " + y + " " + (z + 0.5D));
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Inputs WorldEvent.PotentialSpawns event, and determines whether the x, y, z in that event
     * falls within the bounding box of ANY generated Monument structure
     */
    public boolean isIntersectingWithAnyMonument(WorldEvent.PotentialSpawns event) {
        MapGenStructureData structureData;
        // LogHelper.info("dimension: " + event.world.provider.dimensionId);
        if (event.world.provider.dimensionId == 0) { // Player is in the Overworld
            try {
                structureData = (MapGenStructureData) event.world.perWorldStorage
                    .loadData(MapGenStructureData.class, "Monument");
                NBTTagCompound nbttagcompound = structureData.func_143041_a();

                Iterator itr = nbttagcompound.func_150296_c()
                    .iterator();
                // LogHelper.info("Monument list size: " + nbttagcompound.func_150296_c().size());
                while (itr.hasNext()) {
                    Object element = itr.next();
                    NBTBase nbtbase = nbttagcompound.getTag(element.toString());
                    if (nbtbase.getId() == 10) {
                        NBTTagCompound nbttagcompound2 = (NBTTagCompound) nbtbase;
                        try {
                            int[] boundingBox = nbttagcompound2.getIntArray("BB");

                            // Now check to see if the spawn entity is inside the monument
                            if (event.x >= boundingBox[0] && event.y >= boundingBox[1]
                                && event.z >= boundingBox[2]
                                && event.x <= boundingBox[3]
                                && event.y <= boundingBox[4]
                                && event.z <= boundingBox[5]) {
                                // Spawn event is inside bounding box.
                                return true;
                            }
                        } catch (Exception e) {
                            LogHelper.warn("Failed to evaluate Monument bounding box");
                        }
                    }
                }
            } catch (Exception e) { // This fails when the Monument list is empty (i.e. none have been generated).
                // LogHelper.warn("Failed to evaluate Monument list");
            }
        }
        return false;
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    public void getPossibleMonumentCreatures(WorldEvent.PotentialSpawns event) {
        // BiomeGenBase biomegenbase = event.world.getBiomeGenForCoords(event.x, event.z);
        List monumentMonsters = StructureOceanMonument.getMonsters();
        event.list.clear();
        for (int i = 0; i < monumentMonsters.size(); i++) {
            event.list.add((BiomeGenBase.SpawnListEntry) monumentMonsters.get(i));
        }
    }

    private HashMap eligibleChunksForSpawning = new HashMap();
    // findChunksForSpawning(this, this.spawnHostileMobs, this.spawnPeacefulMobs, this.worldInfo.getWorldTotalTime() %
    // 400L == 0L);

    /**
     * Made to tally the Guardians against the monster mobs
     * Copied pretty much wholesale from net.minecraft.world.SpawnerAnimals
     * adds all chunks within the spawn radius of the players to eligibleChunksForSpawning. pars: the world,
     * hostileCreatures, passiveCreatures. returns number of eligible chunks.
     */
    public boolean capMonsterSpawns(World worldIn, int typeMonsterCount, int typeWaterMonsterCount,
        boolean spawnHostileMobs, boolean spawnPeacefulMobs) {
        if (!spawnHostileMobs && !spawnPeacefulMobs) {
            return true; // Neither hostile nor peaceful mobs are allowed
        } else {
            this.eligibleChunksForSpawning.clear();
            int playerInd;
            int playerChunkZ;

            for (playerInd = 0; playerInd < worldIn.playerEntities.size(); ++playerInd) {
                EntityPlayer entityplayer = (EntityPlayer) worldIn.playerEntities.get(playerInd);
                int playerChunkX = MathHelper.floor_double(entityplayer.posX / 16.0D);
                playerChunkZ = MathHelper.floor_double(entityplayer.posZ / 16.0D);
                byte chunkRadius = 8;

                for (int searchCX = -chunkRadius; searchCX <= chunkRadius; ++searchCX) // l checks the chunks from -8 to
                                                                                       // +8 around the player in the X
                                                                                       // direction
                {
                    for (int searchCZ = -chunkRadius; searchCZ <= chunkRadius; ++searchCZ) // i1 checks the chunks from
                                                                                           // -8 to +8 around the player
                                                                                           // in the Z direction
                    {
                        boolean isOnSearchBoundary = searchCX == -chunkRadius || searchCX == chunkRadius
                            || searchCZ == -chunkRadius
                            || searchCZ == chunkRadius;
                        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(
                            searchCX + playerChunkX,
                            searchCZ + playerChunkZ);

                        if (!isOnSearchBoundary) {
                            this.eligibleChunksForSpawning.put(chunkcoordintpair, Boolean.valueOf(false));
                        } else if (!this.eligibleChunksForSpawning.containsKey(chunkcoordintpair)) {
                            this.eligibleChunksForSpawning.put(chunkcoordintpair, Boolean.valueOf(true));
                        }
                    }
                }
            }

            if (spawnHostileMobs && (typeMonsterCount + typeWaterMonsterCount)
                > EnumCreatureType.monster.getMaxNumberOfCreature() * this.eligibleChunksForSpawning.size() / 256) {
                return true; // "Monster" type cap is reached
            } else return false; // Monster cap is not yet reached
        }
    }

}
