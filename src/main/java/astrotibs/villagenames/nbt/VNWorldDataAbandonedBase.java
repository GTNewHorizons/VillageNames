package astrotibs.villagenames.nbt;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.World;

public class VNWorldDataAbandonedBase extends WorldSavedData implements VNWorldData {
    private NBTTagCompound data = new NBTTagCompound();
    
    final static String key = "villagenames_gcab"; // .dat file being saved to
    final static String toptag = "AbandonedBases"; // top-level tag, under "data"
    
    public VNWorldDataAbandonedBase(String tagName) {
        super(tagName);
    }
    
    
	public static VNWorldDataAbandonedBase forWorld(World world) {
		// Retrieves the data instance for the given world, creating it if necessary
		MapStorage storage = world.perWorldStorage;
		VNWorldDataAbandonedBase result = (VNWorldDataAbandonedBase)storage.loadData(VNWorldDataAbandonedBase.class, key);
		if (result == null) {
			result = new VNWorldDataAbandonedBase(key);
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
