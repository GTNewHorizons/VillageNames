package astrotibs.villagenames.nbt;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class VNWorldDataStructure extends WorldSavedData implements VNWorldData {
    
	private NBTTagCompound data = new NBTTagCompound();
    
    //final static String key = "villagenames"; // .dat file being saved to
    public static String toptag = ""; // top-level tag, under "data"
    
    // I tried generalizing this so that I could throw out redundant NBT classes.
    // Here is the list of previously-used values so that I can reference them from older versions:
    /* 
     * STRUCTURE		key					toptag
     * 
     * Villages:		villagenames		Villages
     * Temples:			villagenames_te		Temples
     * Mineshafts:		villagenames_mi		Mineshafts
     * Strongholds:		villagenames_st		Strongholds
     * Monuments:		villagenames_mo		Monuments
     * Mansions:		villagenames_ma		Mansions
     * Fortress:		villagenames_fo		Fortresses
     * End City:		villagenames_ec		EndCities
     * 
     * Abandoned Base:	villagenames_gcab	AbandonedBases
     * End Islands:		villagenames_heei	hardcoreenderdragon_EndIsland
     * End Towers:		villagenames_heet	hardcoreenderdragon_EndTower
     * Fronos Villages:	villagenames_mpfv	FronosVillages
     * Koentus Vill:	villagenames_mpkv	KoentusVillages
     * Moon Villages:	villagenames_gcmv	MoonVillages
     * Nibiru Villages:	villagenames_mpnv	NibiruVillages
     */
    
    public VNWorldDataStructure(String tagName) {
        super(tagName);
    }
    
    /**
     * Accesses the NBT machinery for Village Names structure names and info
     * @param world
     * @param key - The name of the nbt file
     * @param toptagIn - the topmost tag of the NBT file.
     * @return
     */
	public static VNWorldDataStructure forWorld(World world, String key, String toptagIn) {
		toptag = toptagIn;
		// Retrieves the data instance for the given world, creating it if necessary
		MapStorage storage = world.perWorldStorage;
		VNWorldDataStructure result = (VNWorldDataStructure)storage.loadData(VNWorldDataStructure.class, key);
		if (result == null) {
			result = new VNWorldDataStructure(key);
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
