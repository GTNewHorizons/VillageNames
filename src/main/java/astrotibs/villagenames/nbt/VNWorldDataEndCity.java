package astrotibs.villagenames.nbt;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.World;

public class VNWorldDataEndCity extends WorldSavedData implements VNWorldData {
    private NBTTagCompound data = new NBTTagCompound();
    
    final static String key = "villagenames_ec"; // .dat file being saved to
    final static String toptag = "EndCities"; // top-level tag, under "data"
    
    public VNWorldDataEndCity(String tagName) {
        super(tagName);
    }
    
    
	public static VNWorldDataEndCity forWorld(World world) {
		// Retrieves the data instance for the given world, creating it if necessary
		MapStorage storage = world.perWorldStorage;
		VNWorldDataEndCity result = (VNWorldDataEndCity)storage.loadData(VNWorldDataEndCity.class, key);
		if (result == null) {
			result = new VNWorldDataEndCity(key);
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
