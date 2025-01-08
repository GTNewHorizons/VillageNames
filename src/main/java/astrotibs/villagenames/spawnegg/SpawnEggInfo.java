package astrotibs.villagenames.spawnegg;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Adapted from Better Spawn Eggs:
 * https://github.com/DavidGoldman/BetterSpawnEggs/blob/master/com/mcf/davidee/spawneggs/eggs/SpawnEggInfo.java
 * 
 * @author AstroTibs
 */

public class SpawnEggInfo {

    public final short eggID;
    public final String mobID;
    public final String displayName;
    public final NBTTagCompound spawnData;
    public final int primaryColor;
    public final int secondaryColor;

    public SpawnEggInfo(short eggID, String mobID, String displayName, NBTTagCompound spawnData, int primaryColor,
        int secondaryColor) {
        this.eggID = eggID;
        this.mobID = mobID;
        this.displayName = displayName;
        this.spawnData = spawnData;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    public SpawnEggInfo(short eggID, String mobID, NBTTagCompound compound, int primaryColor, int secondaryColor) {
        this(eggID, mobID, null, compound, primaryColor, secondaryColor);
    }

}
