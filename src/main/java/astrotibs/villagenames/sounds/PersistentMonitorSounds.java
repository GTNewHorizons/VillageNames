package astrotibs.villagenames.sounds;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;


/**
 * Randomly applies the 1.9+ crackle sound to lit furnaces within a radius of 15 around the player.
 * For efficiency, each tick it chooses a fraction (1%) of possible blocks to check, and fires the sound
 * 100% of the time it finds a lit furnace. This is much more efficient than searching every block (16k)
 * every tick, and at each lit furnace, choosing to play the sound only 1% of the time.
 * 
 * @author AstroTibs
 *
 */

public class PersistentMonitorSounds {
	
    // Only a fraction of blocks produce the crackle every tick, so only search one every N. I'm hard-coding this to minimize computation footprint.
    final int everyNblocks = 10*10; // The furnace is ticked for every 10 world ticks, and then only plays the sound 10% of the time.
    final int soundRange = 15; // Search range, in blocks, that this event looks for active furnaces
    
    // The way I'm going to do this is to randomly generate a fraction of positions from the search cube.
    // This equates to generating (2*soundRange+1)^3 / everyNblocks unique numbers.
    // Then I de-convolve those numbers to 3D positions, and if there's a furnace at that position, I play the crackle noise.
    // The actual calculation takes a bit more nuance.
    
    
    // Step 1: figure out how many total blocks will be searched.
    final int totalBlocks = ((2*soundRange)+1)*((2*soundRange)+1)*((2*soundRange)+1); // Total number of blocks in the search cube
    // Step 2: find out how many positions we'll check, rounded up.
    final int numPosToCheck = (totalBlocks+everyNblocks-1)/everyNblocks;
    
    
    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
    	
    	if (GeneralConfig.furnaceSounds) {
    		
        	final Minecraft minecraft = FMLClientHandler.instance().getClient();
            //final WorldClient world = minecraft.theWorld;
            final EntityClientPlayerMP player = minecraft.thePlayer;
        	
        	if (
        			player != null
        			&& !minecraft.isGamePaused()
        			) {
        		
        		// Generating a list and going through it takes more computational power (thus FPS) than generating multiple random values.
        		// The downside is that rarely, a position can be double-ticked. I barely care.
            	for (int p = 0; p < numPosToCheck ; p++) { 
            		int codedPosition = player.worldObj.rand.nextInt(numPosToCheck*everyNblocks-1);
            		
            		if (codedPosition <= (totalBlocks-1) ) { // Occasionally, a position will be outside the cube, and that's fine. Just reject it.
            			
            			// Step 5: decode the integer to an x, y, z position
            			
            			// These range from -15 to +15 in x, y, z
            			int sx = (codedPosition / ( ((2*soundRange)+1)*((2*soundRange)+1) )) - soundRange; // X position is unpacked
            			int sy = codedPosition % ( ((2*soundRange)+1)*((2*soundRange)+1) ); // this is Y and Z convolved
            			int sz = (sy % ((2*soundRange)+1)) - soundRange; // Z position is unpacked
            			sy = (sy / ((2*soundRange)+1)) - soundRange; // Y position is unpacked
            			
            			// Step 6: add the player's position as offset.
            			int fx = sx + MathHelper.floor_double(player.posX);
            			int fy = sy + MathHelper.floor_double(player.posY + player.eyeHeight);
            			int fz = sz + MathHelper.floor_double(player.posZ);
            			
            			// Step 7: only continue if the position is inside the sphere AND inside the world:
            			if (
            					fy >= 0 // Greater than the void height
            					&& fy <= player.worldObj.getHeight() // Less than the sky height
            					&& (sx*sx + sy*sy + sz*sz) <= soundRange*soundRange // Within the search sphere -- cuts the list into about half
            					&& player.worldObj.getBlock(fx, fy, fz) == Blocks.lit_furnace // Is a lit furnace
            					) {
            				player.worldObj.playSound(
            						  fx + 0.5
            						, fy + 0.5
            						, fz + 0.5
            						, Reference.MOD_ID+":block.furnace.fire_crackle", 1.0F, 1.0F, false);
            			}
            		}
            	}
        	}
    	}
    }
    
    
    
    
}
