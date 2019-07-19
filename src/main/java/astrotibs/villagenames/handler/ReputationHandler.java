package astrotibs.villagenames.handler;

import java.util.Iterator;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.utility.LogHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.village.Village;
import net.minecraft.world.gen.structure.MapGenStructureData;

public class ReputationHandler {
	
	private static final String collectedKey = "VNCollected";
	private static final String repKey = "VNRep";
	
	
	
    /**
     * Return the village reputation for a player
     * player: the EntityPlayerMP entity to check
     * villageTopTag: the village to check as given by the topmost entry in Village.dat (e.g. "[12,10]")
     * Village: the village that will be checked for a reputation value via villageCollectionObj
     */
    public static int getVNReputationForPlayer(EntityPlayerMP player, String villageTopTag, Village village)
    {
    	
    	// Updated in v3.2.1 to allow for Open Terrain Generation compatibility
		
		MapGenStructureData structureData = null;
		NBTTagCompound nbttagcompound = null;
		
		try
		{
			structureData = (MapGenStructureData)player.worldObj.perWorldStorage.loadData(MapGenStructureData.class, "Village");
			nbttagcompound = structureData.func_143041_a();
		}
		catch (Exception e) // Village.dat does not exist
		{
			try
    		{
    			structureData = (MapGenStructureData)player.worldObj.perWorldStorage.loadData(MapGenStructureData.class, "OTGVillage");
    			nbttagcompound = structureData.func_143041_a();
    		}
    		catch (Exception e1) {} // OTGVillage.dat does not exist
		}
    	
		// v3.2.1 - Put it all in a try/catch thanks to OTG
		NBTTagCompound villageTag = null;
		
		try
		{
			NBTBase nbtbase = nbttagcompound.getTag( villageTopTag ); // Retrieve the specific village as indicated by villageTopTag
	    	villageTag = (NBTTagCompound)nbtbase; // Cast this as a tag compound
		}
		catch (Exception e) {}
		
    	NBTTagCompound playerReps = new NBTTagCompound();
    	int VNPlayerRep = 0; // Defaults to zero
    	boolean returnDefaultRep = false; // This is flagged as "true" if we're forced to return default reputation.
    	
    	// ------------------------------------------------- //
    	// --- VN reps recorded for at least one player! --- //
    	// ------------------------------------------------- //
    	
    	if (villageTag!=null) { // Otherwise, the game crashes when in an artificial village
    		
    		
        	if (villageTag.hasKey(repKey)) {
        		playerReps = (NBTTagCompound)(villageTag.getTag(repKey)); // Retrieve the entry that has all the player reps and cast it as a tag compound
        		
        		// -------------------------------------------------- //
        		// --- We found an entry for the player's VN rep! --- //
        		// -------------------------------------------------- //
        		if (playerReps.hasKey(player.getUniqueID().toString())) {
        			
        			int PrevVNPlayerRep = playerReps.getInteger(player.getUniqueID().toString());
        			
        			if (village==null) { // There is no village in the collectionObj--presumably, it was annihilated. Return the VN rep.
        				VNPlayerRep = PrevVNPlayerRep;
        				//if (GeneralConfigHandler.debugMessages) LogHelper.info("This village was previously annihilated. Your reputation is now " + VNPlayerRep);
        			}
        			else { // There is a village in the collectionObj. Compare the VN reputation with its reputation.
        				int collectionRep = village.getReputationForPlayer(player.getDisplayName());
        				if (PrevVNPlayerRep == collectionRep) { // They match.
        					// If it's zero, it's safe to assume that it's supposed to be zero.
        					VNPlayerRep = PrevVNPlayerRep; 
        					//if (GeneralConfigHandler.debugMessages) LogHelper.info("Your collection and VN reputation match: " + VNPlayerRep);
        				}
        				else { // They do not match.
    						if (villageTag.getBoolean(collectedKey)) { // This village was previously reported as "collected"
    							// Chances are, the VN version needs to be updated to the new collection version.
    							VNPlayerRep = collectionRep; 
    							if (GeneralConfig.debugMessages) {LogHelper.info("There was a reputation mismatch. We assume villageCollectionObj was correct: " + VNPlayerRep);}
    						}
    						else { // This village was previously reported as annihilated
    							// The village is no longer annihilated, so its reputation is going to return to zero.
    							// Set its reputation to whatever the VN version currently is.
    							
    							// 3.0.1 update: setReputationForPlayer INCREMENTS reputation; it doesn't set the absolute value!!
    							village.setReputationForPlayer(player.getDisplayName(), PrevVNPlayerRep-collectionRep);
    							VNPlayerRep = PrevVNPlayerRep; 
    							if (GeneralConfig.debugMessages) {LogHelper.info("There was a reputation mismatch. We assume VN's was correct: " + VNPlayerRep);}
    						}
        				}
        			}
        		}
        		else {
            		returnDefaultRep = true; // Player's VN rep does not exist
        		}
        	}
        	else {
        		returnDefaultRep = true; // Nobody has VN rep in this village
        	}
    		
    		// ---------------------------------------------------------- //
    		// --- There is no VN reputation recorded for this player --- //
        	// --------- OR No VN reps recorded for any player! --------- //
    		// ---------------------------------------------------------- //
    		if (returnDefaultRep) {
    			// Check it against villageCollectionObj
    			if (village!=null) {
    				// Either someone else visited this village when it wasn't annihilated, 
    				// or this village has been brought back from annihilation,
    				// or it was not visited since VN3.
    				// In any case, the player has no recorded rep. There's nothing further we can do.
    				// Just take it from the collection.
    				VNPlayerRep = village.getReputationForPlayer(player.getDisplayName());
    				if (GeneralConfig.debugMessages) LogHelper.info("You have no recorded VN rep in this town. It is now set to " + VNPlayerRep);
    			}
    		}
        	
        	villageTag.setTag(repKey, playerReps);
        	villageTag.setBoolean(collectedKey, !(village==null));
    		playerReps.setInteger(player.getUniqueID().toString(), VNPlayerRep);
    		structureData.markDirty(); // You changed a value.
        	
    		return VNPlayerRep;
    		
    	}
    	
    	else { // villageTag is null, so return 0 by default
    		
    		// It's possible you're dealing with an artificial village. Try getting the player rep.
    		
    		int nullVillageTagRep = 0;
    		
    		try {
    			nullVillageTagRep = village.getReputationForPlayer(player.getDisplayName());
    		}
    		catch (Exception e) {} // Something went wrong. Probably because the village is null. Just leave it at zero.
    		
    		return nullVillageTagRep;
    	}
    	

    }

    /**
     * Set the village reputation for a player.
     */
    
	public static String getVillageTagPlayerIsIn(EntityPlayerMP player) {
		
		// First, check to see if the player is in a village bounding box as defined in Village.dat
		// If so, check to see if the player is also in a village as defined in villages.dat

		// Updated in v3.2.1 to allow for Open Terrain Generation compatibility

		MapGenStructureData structureData;
		NBTTagCompound nbttagcompound = null;

		try
		{
			structureData = (MapGenStructureData)player.worldObj.perWorldStorage.loadData(MapGenStructureData.class, "Village");
			nbttagcompound = structureData.func_143041_a();
		}
		catch (Exception e) // Village.dat does not exist
		{
			try
    		{
    			structureData = (MapGenStructureData)player.worldObj.perWorldStorage.loadData(MapGenStructureData.class, "OTGVillage");
    			nbttagcompound = structureData.func_143041_a();
    		}
    		catch (Exception e1) {} // OTGVillage.dat does not exist
		}
		
		Iterator itr = nbttagcompound.func_150296_c().iterator();

		while (itr.hasNext()) {
			Object entry = itr.next();
			
			NBTBase nbtbase = nbttagcompound.getTag(entry.toString());
			
			if (nbtbase.getId() == 10) {
				NBTTagCompound villageTag = (NBTTagCompound)nbtbase;
				
				if (villageTag.getBoolean("Valid")) { // Was not generated as a junk entry
					int[] boundingBox = villageTag.getIntArray("BB");
					
					int posX = (int) player.posX;
					int posY = (int) player.posY;
					int posZ = (int) player.posZ;
					
					if (
							(
							   posX >= (boundingBox[0])
							&& posY >= (boundingBox[1])
							&& posZ >= (boundingBox[2])
							&& posX <= (boundingBox[3])
							&& posY <= (boundingBox[4])
							&& posZ <= (boundingBox[5])
							)) {
						// Player is inside the bounding box of this village
						
						return entry.toString();
					}
				}
			}
		}
		
		return "none"; // Player is not inside a village as defined by Village.dat
	}
	
}
