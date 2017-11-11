package astrotibs.villagenames.nbt;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.World;

public class VNWorldDataFronosVillage extends WorldSavedData implements VNWorldData {
    private NBTTagCompound data = new NBTTagCompound();
    
    final static String key = "villagenames_mpfv"; // .dat file being saved to
    final static String toptag = "FronosVillages"; // top-level tag, under "data"
    
    public VNWorldDataFronosVillage(String tagName) {
        super(tagName);
    }
    
    
	public static VNWorldDataFronosVillage forWorld(World world) {
		// Retrieves the data instance for the given world, creating it if necessary
		MapStorage storage = world.perWorldStorage;
		VNWorldDataFronosVillage result = (VNWorldDataFronosVillage)storage.loadData(VNWorldDataFronosVillage.class, key);
		if (result == null) {
			result = new VNWorldDataFronosVillage(key);
			storage.setData(key, result);
		}
	return result;
	}
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
    	data = compound.getCompoundTag(toptag);
    }
    
    // Here's the default one it wants so badly
    @Override
    public void writeToNBT(NBTTagCompound compound) {
        compound.setTag(toptag, data);
    }
    
    @Override
	public NBTTagCompound getData() {
        return data;
    }
}
