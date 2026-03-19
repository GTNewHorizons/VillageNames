package astrotibs.villagenames.prismarine.guardian.spawning;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.prismarine.guardian.entity.monster.EntityGuardian;
import astrotibs.villagenames.prismarine.monument.StructureOceanMonument;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import gnu.trove.set.hash.TLongHashSet;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructureData;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.world.WorldEvent;

import java.util.ArrayList;
import java.util.List;

public class SpawnEventListener {

	/**
	 * WOW. Literal weeks of wracking my brain over how to add Guardian spawns, and I accidentally stumbled on this thread:
	 * http://www.minecraftforge.net/forum/topic/17547-spawning-a-custom-entity/
	 * which brings EnumHelper to the fore. And to think I was about to abandon that thread as useless.
	 */
	// Here I called the monster max number explicitly (70)
	public static final EnumCreatureType waterMonster = EnumHelper.addCreatureType("waterMonster", IMob.class, EnumCreatureType.monster.getMaxNumberOfCreature(), Material.water, false, false);

	private static final int REFRESH_INTERVAL = 2 * 20; // 2 seconds of ticks
	private static final int MONUMENT_REFRESH_INTERVAL = 5 * 60 * 20; // 5 minutes of ticks
	private boolean overMonsterCap = false;
	private int[][] monumentBBs = new int[0][];
	private final TLongHashSet eligibleChunks = new TLongHashSet();


	@SubscribeEvent
	public void onPostServerTick(TickEvent.ServerTickEvent event) {
		if (event.phase != Phase.END) return;

		final MinecraftServer server = MinecraftServer.getServer();
		if (server == null) return;
		final int tick = server.getTickCounter();

		if (tick % REFRESH_INTERVAL == 0) {
			final WorldServer world = server.worldServerForDimension(0);
			if (world != null) {
				final int countMonster = world.countEntities(EnumCreatureType.monster, true);
				final int countWaterMonster = world.countEntities(waterMonster, true);
				final int totalMonsters = countMonster + countWaterMonster;
				final int maxMonsters = EnumCreatureType.monster.getMaxNumberOfCreature();
				final int playerCount = world.playerEntities.size();

				final int lowerBoundChunks = 17 * 17; // Single player's 17x17 spawn area
				final int upperBoundChunks = playerCount * lowerBoundChunks;

				if (playerCount == 0 || totalMonsters <= maxMonsters * lowerBoundChunks / 256) {
					// No players, or under cap even with minimum eligible chunks
					overMonsterCap = false;
				} else if (totalMonsters > maxMonsters * upperBoundChunks / 256) {
					// Over cap even with maximum eligible chunks (no player overlap)
					overMonsterCap = true;
				} else {
					// Ambiguous — compute exact chunk count
					eligibleChunks.clear();
					for (int i = 0; i < playerCount; i++) {
						final EntityPlayer player = world.playerEntities.get(i);
						final int pcx = MathHelper.floor_double(player.posX / 16.0D);
						final int pcz = MathHelper.floor_double(player.posZ / 16.0D);
						for (int dx = -8; dx <= 8; dx++) {
							for (int dz = -8; dz <= 8; dz++) {
								eligibleChunks.add(chunkKey(dx + pcx, dz + pcz));
							}
						}
					}
					final int chunkCount = eligibleChunks.size();
					overMonsterCap = totalMonsters > maxMonsters * chunkCount / 256;
				}
			}
		}

		if (tick % MONUMENT_REFRESH_INTERVAL == MONUMENT_REFRESH_INTERVAL / 2) {
			final WorldServer world0 = server.worldServerForDimension(0);
			if (world0 != null) {
				final MapGenStructureData structureData = (MapGenStructureData) world0.perWorldStorage.loadData(MapGenStructureData.class, "Monument");
				this.monumentBBs = structureData != null ? readMonumentBBs(structureData.func_143041_a()) : new int[0][];
			}
		}
	}

	private static long chunkKey(int cx, int cz) {
		return ((long) cx << 32) | (cz & 0xFFFFFFFFL);
	}

	private static int[][] readMonumentBBs(NBTTagCompound nbttagcompound) {
		final ArrayList<int[]> bbList = new ArrayList<>();
		for (String key : nbttagcompound.func_150296_c()) {
			final NBTBase nbtbase = nbttagcompound.getTag(key);
			if (nbtbase.getId() == 10) {
				final int[] bb = ((NBTTagCompound) nbtbase).getIntArray("BB");
				if (bb.length == 6) {
					bbList.add(bb);
				}
			}
		}
		return bbList.toArray(new int[0][]);
	}

	private boolean isInMonumentCached(int x, int y, int z) {
		for (int[] bb : monumentBBs) {
			if (x >= bb[0] && y >= bb[1] && z >= bb[2] && x <= bb[3] && y <= bb[4] && z <= bb[5]) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Handles PotentialSpawns — caps monster+waterMonster collectively using cached state, and injects guardian spawns inside monument bounding boxes.
	 */
	@SubscribeEvent(receiveCanceled=true)
	public void guardianSpawnerAndCap(WorldEvent.PotentialSpawns event) {

		if (event.type != EnumCreatureType.monster && event.type != waterMonster) {
			return;
		}

		if (overMonsterCap) {
			return;
		}

		if (event.type == waterMonster && event.world.getWorldInfo().getVanillaDimension() == 0 && event.world.getWorldInfo().isMapFeaturesEnabled() && GeneralConfig.addOceanMonuments && isInMonumentCached(event.x, event.y, event.z)) {
			this.getPossibleMonumentCreatures(event);
		}
	}


	/**
	 * This method triggers when the Overworld is loaded. It then attempts to retrogen Elder Guardians
	 * into Ocean Monuments that were spawned before the 3.0 update.
	 * Also populates the cached monument bounding boxes.
	 *
     * WorldEvent.Load is fired when Minecraft loads a world.
     **/
	@SubscribeEvent(receiveCanceled=true)
	public void retroGenElders(WorldEvent.Load event) {

		if (event.world.provider.dimensionId==0) { // Player is in the Overworld
			final World world = event.world;

			final MapGenStructureData structureData = (MapGenStructureData) world.perWorldStorage.loadData(MapGenStructureData.class, "Monument");
			if (structureData == null) {
				this.monumentBBs = new int[0][];
				if (GeneralConfig.debugMessages) {LogHelper.warn("Failed to load Monument list, or none exists.");}
				return;
			}

			final NBTTagCompound nbttagcompound = structureData.func_143041_a();

			// Cache monument BBs immediately on load
			this.monumentBBs = readMonumentBBs(nbttagcompound);

			for (String key : nbttagcompound.func_150296_c()) {

				try {
					final NBTBase nbtbase = nbttagcompound.getTag(key);
					if (nbtbase.getId() == 10) { //10 is NBT tag compound, I think
						final NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbtbase;

						final int[] boundingBoxIA = nbttagcompound2.getIntArray("BB");
						final StructureBoundingBox boundingBox = new StructureBoundingBox(boundingBoxIA);

						// Orientation of the Monument's entrance
						// 0: NORTH
						// 1: EAST
						// 2: SOUTH
						// 3: WEST

						final boolean hasHadElders = nbttagcompound2.getBoolean(Reference.ELDER_GEN_VN4);

						if (!hasHadElders) { //This Monument was generated before the 2.1 update, so here's our chance to shove some Elders into it

							final int coordBaseMode = nbttagcompound2.getTagList("Children", 10).getCompoundTagAt(0).getInteger("O");

							final int chunkXPos = nbttagcompound2.getInteger("ChunkX");
							final int chunkZPos = nbttagcompound2.getInteger("ChunkZ");
							final int i = (chunkXPos << 4) + 8;
					        final int j = (chunkZPos << 4) + 8;

					        //Calculate the center position of the monument, which will be used to position the three elders
					        final double monumentXCenter = (boundingBox.maxX+boundingBox.minX)/2.0D;
					        final double monumentZCenter = (boundingBox.maxZ+boundingBox.minZ)/2.0D;

							// Spawn an Elder in the Penthouse. Accurate to within 1x1x0 block
							this.spawnElder(world, boundingBox, monumentXCenter, 53.0D, monumentZCenter);

							final int seedOffset = ((int) world.getSeed())%2; // This is to modulate the initial placements of the elders

							// Spawn an Elder in the Left wing. Accurate to within 3x3x3 block
							this.spawnElder(world, boundingBox,
									monumentXCenter + ( (coordBaseMode%2==0 ? 16.5D : 13.5D)*(coordBaseMode>1 ? -1.0D : 1.0D) ),
									seedOffset==0? 45.0D: 41.0D, // Supposed to be 42 OR 45. Floored to 41 because he hits his head lmao
									monumentZCenter + ( (coordBaseMode%2==0 ? 13.5D : 16.5D)*(Math.abs((2*coordBaseMode)-3) > 2 ? -1.0D : 1.0D) )
									);

							// Spawn an Elder in the Right wing. Accurate to within 3x3x3 block
							this.spawnElder(world, boundingBox,
									monumentXCenter + ( (coordBaseMode%2==0 ? 16.5D : 13.5D)*(Math.abs((2*coordBaseMode)-3) > 2 ? -1.0D : 1.0D) ),
									seedOffset==0? 41.0D: 45.0D, // Supposed to be 42 OR 45
									monumentZCenter + ( (coordBaseMode%2==0 ? 13.5D : 16.5D)*(coordBaseMode<2 ? -1.0D : 1.0D) )
									);

							// Set the tag to "true" so that we won't generate elders again
							nbttagcompound2.setBoolean(Reference.ELDER_GEN_VN4, true);
							structureData.setDirty(true);
						}
					}
				} catch (Exception e) {
					LogHelper.warn("Failed to evaluate Elder Guardian status of Monument");
				}

			}
		}
	}


    /**
     * Spawns an Elder Guardian at the specified x, y, z locations
     */
    protected boolean spawnElder(World worldIn, StructureBoundingBox bb, double x, double y, double z)
    {

        if (bb.isVecInside((int)x, (int)y, (int)z))
        {
            final EntityGuardian elderGuardian = new EntityGuardian(worldIn);
            elderGuardian.setElder(true);
            elderGuardian.heal(elderGuardian.getMaxHealth());
            elderGuardian.setLocationAndAngles(x + 0.5D, y, z + 0.5D, 0.0F, 0.0F);
            elderGuardian.onSpawnWithEgg( (IEntityLivingData)null ); // Hopefully this will still allow a guardian to spawn.

            final int i = MathHelper.floor_double(elderGuardian.posX / 16.0D);
            final int j = MathHelper.floor_double(elderGuardian.posZ / 16.0D);

            worldIn.getChunkFromChunkCoords(i, j).addEntity(elderGuardian);
            worldIn.loadedEntityList.add(elderGuardian);
            worldIn.onEntityAdded(elderGuardian);

            if (GeneralConfig.debugMessages) {LogHelper.info("Elder Guardian retroactively spawned at " + (x + 0.5D) + " " + y + " " + (z + 0.5D) );}
            return true;
        }
        else
        {
        	return false;
        }
    }


	/**
     * Populates the event's spawn list with Monument guardian entries.
     */
    public void getPossibleMonumentCreatures(WorldEvent.PotentialSpawns event)
    {
        final List<BiomeGenBase.SpawnListEntry> monumentMonsters = StructureOceanMonument.getMonsters();
		event.list.clear();
		event.list.addAll(monumentMonsters);
    }

}
