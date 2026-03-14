package astrotibs.villagenames.spawnegg;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;

import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.Reference;

/**
 * Adapted from Better Spawn Eggs:
 * https://github.com/DavidGoldman/BetterSpawnEggs/blob/master/com/mcf/davidee/spawneggs/SpawnEggRegistry.java
 * https://github.com/DavidGoldman/BetterSpawnEggs/blob/master/com/mcf/davidee/spawneggs/DefaultFunctionality.java
 * https://github.com/DavidGoldman/BetterSpawnEggs/blob/master/com/mcf/davidee/spawneggs/CustomTags.java
 * 
 * @author AstroTibs
 */

public class SpawnEggRegistry {

    private static final Map<Short, SpawnEggInfo> eggs = new LinkedHashMap<Short, SpawnEggInfo>();
    private static final int MOD_MOBS_IDS_START = 1;

    public static final Object[][] spawnEggData = new Object[][] { { "guardian", guardian(false), 0x5A8272, 0xF17D30 }, // Guardian
        { "guardian.elder", guardian(true), 0xCECCBA, 0x747693 }, // Elder Guardian
    };

    public static void registerSpawnEgg(SpawnEggInfo info) throws IllegalArgumentException {
        if (info == null) {
            throw new IllegalArgumentException("SpawnEggInfo cannot be null");
        }

        if (!isValidSpawnEggID(info.eggID)) {
            throw new IllegalArgumentException("Duplicate spawn egg with id " + info.eggID);
        }

        eggs.put(info.eggID, info);
    }

    public static boolean isValidSpawnEggID(short id) {
        return !eggs.containsKey(id);
    }

    public static SpawnEggInfo getEggInfo(short id) {
        return eggs.get(id);
    }

    public static Collection<SpawnEggInfo> getEggInfoList() {
        return Collections.unmodifiableCollection(eggs.values());
    }

    public static void addAllSpawnEggs()// throws RuntimeException
    {
        // Use this to index the mob strings
        int currentID;

        for (int i = 0; i < spawnEggData.length; i++) {
            currentID = i + MOD_MOBS_IDS_START;

            try {
                SpawnEggRegistry.registerSpawnEgg(
                    new SpawnEggInfo(
                        (short) currentID, // Meta ID that's going to try to load
                        Reference.MOD_ID + "." + Reference.MOB_GUARDIAN_VN, // Mob's internal name (e.g. Ocelot_NEC)
                        (String) (spawnEggData[i][0]), // The name to be displayed on the spawn egg item
                        (NBTTagCompound) (spawnEggData[i][1]), // Extra data to be applied to the mob
                        (Integer) (spawnEggData[i][2]),
                        (Integer) (spawnEggData[i][3]))); // The base and speckle colors of the spawn egg
            } catch (IllegalArgumentException e) {
                LogHelper.warn(
                    "Could not register spawn egg for entity " + (String) (spawnEggData[i][0])
                        + " into ID "
                        + currentID);
            }
        }
    }

    /*
     * These methods attack custom NBT data to allow subtypes with the spawn egg
     * I mean, otherwise, there's no point in having the eggs.
     */

    public static NBTTagCompound guardian(boolean isElder) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("Elder", isElder ? 1 : 0);
        return tag;
    }
}
