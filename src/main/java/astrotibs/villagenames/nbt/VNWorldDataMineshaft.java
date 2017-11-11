package astrotibs.villagenames.nbt;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.World;

public class VNWorldDataMineshaft extends WorldSavedData implements VNWorldData {
    private NBTTagCompound data = new NBTTagCompound();
    
    final static String key = "villagenames_mi"; // .dat file being saved to
    final static String toptag = "Mineshafts"; // top-level tag, under "data"
    
    public VNWorldDataMineshaft(String tagName) {
        super(tagName);
    }
    
    
	public static VNWorldDataMineshaft forWorld(World world) {
		// Retrieves the data instance for the given world, creating it if necessary
		MapStorage storage = world.perWorldStorage;
		VNWorldDataMineshaft result = (VNWorldDataMineshaft)storage.loadData(VNWorldDataMineshaft.class, key);
		if (result == null) {
			result = new VNWorldDataMineshaft(key);
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
