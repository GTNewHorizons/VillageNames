package astrotibs.villagenames.village.biomestructures;

import java.util.List;
import java.util.Random;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.utility.FunctionsVN;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.village.StructureVillageVN;
import astrotibs.villagenames.village.StructureVillageVN.StartVN;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;

public class PlainsStructures
{
	// -------------------- //
    // --- Start Pieces --- //
	// -------------------- //
	
	// --- Fountain --- //
	
    public static class PlainsFountain01 extends StartVN
    {
    	public PlainsFountain01() {}
    	
    	public PlainsFountain01(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, int terrainType)
    	{
    		super(chunkManager, componentType, random, posX, posZ, components, terrainType);
    		
		    // Establish orientation
            this.coordBaseMode = random.nextInt(4);
            switch (this.coordBaseMode)
            {
	            case 0: // North
	            case 2: // South
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + 10, 68, posZ + 10);
                    break;
                default: // 1: East; 3: West
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + 10, 68, posZ + 10);
            }

            //StructureVillageVN.establishBiomeBlocks(this, posX, posZ);
    	}

		/*
		 * Add the paths that lead outward from this structure
		 */
		public void buildComponent(StructureComponent start, List components, Random random)
		{
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 4, 1, this.getComponentType());
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 4, 3, this.getComponentType());
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 4, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 4, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
		}
    	
		/*
		 * Construct the structure
		 */
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, this); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.standing_sign, 0, this); Block biomeSignBlock = (Block)blockObject[0];
        	
        	if (this.field_143015_k < 0)
            {
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				new StructureBoundingBox(
        						this.boundingBox.minX, this.boundingBox.minZ,
        						this.boundingBox.maxX, this.boundingBox.maxZ), // Set the bounding box version as this bounding box but with Y going from 0 to 512
        				true);
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.minY -1, 0);
            }
            
        	// Clear out area
        	this.fillWithAir(world, structureBB, 2, 1, 2, 6, 4, 6);
        	
            // Basin bottom
            this.fillWithMetadataBlocks(world, structureBB, 2, 0, 2, 6, 0, 6, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            
            // Torches at the corners
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 2, 1, 2, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 2, 1, 6, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 6, 1, 2, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 6, 1, 6, structureBB);
            
            // Basin rim
            this.fillWithMetadataBlocks(world, structureBB, 2, 1, 3, 6, 1, 5, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            this.fillWithMetadataBlocks(world, structureBB, 3, 1, 2, 5, 1, 6, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            this.fillWithAir(world, structureBB, 3, 1, 3, 5, 1, 5);
            
            // Spout
            this.fillWithMetadataBlocks(world, structureBB, 4, 1, 4, 4, 2, 4, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            this.placeBlockAtCurrentPosition(world, Blocks.flowing_water, 0, 4, 3, 4, structureBB);
            
            // Encircle the well with path
            Block pathBlock = ModObjects.chooseModPathBlock();
        	StructureVillagePieces.Start startPiece_reflected = ReflectionHelper.getPrivateValue(StructureVillagePieces.Village.class, this, new String[]{"startPiece"});
        	for (int i = 1; i <= 7; ++i)
            {
                for (int j = 1; j <= 7; ++j)
                {
                    if (j == 1 || j == 7 || i == 1 || i == 7)
                    {
                    	// Gets ground level, so long as it's not leaves or other foliage
                    	int k = world.getTopSolidOrLiquidBlock(this.getXWithOffset(i, j), this.getZWithOffset(i, j)) - 1;
                        if (k > -1)
                        {
                        	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getBoundingBox().minX+i, k, this.getBoundingBox().minZ+j);
                        	this.clearCurrentPositionBlocksUpwards(world, i, k+1, j, structureBB);
                       	}
                    }
                }
            }
            // Add path nodules at the end
            for (int i : new int[]{1,2,3,4,5,6,7})
            {
            	for (int j : new int[]{0,8})
            	{
            		int k = world.getTopSolidOrLiquidBlock(this.getXWithOffset(i, j), this.getZWithOffset(i, j)) - 1;
                    if (k > -1)
                    {
                    	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(i, j), k, this.getZWithOffset(i, j));
                    	this.clearCurrentPositionBlocksUpwards(world, i, k+1, j, structureBB);
                   	}
                    
                    k = world.getTopSolidOrLiquidBlock(this.getXWithOffset(j, i), this.getZWithOffset(j, i)) - 1;
                    if (k > -1)
                    {
                    	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(j, i), k, this.getZWithOffset(j, i));
                    	this.clearCurrentPositionBlocksUpwards(world, j, k+1, i, structureBB);
                   	}
            	}
            }

            // Sign
            int signX = this.getXWithOffset(4, 2);
            int signY = this.getYWithOffset(2);
            int signZ = this.getZWithOffset(4, 2);
    		NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, random, signX, signY, signZ);
    		String namePrefix = villageNBTtag.getString("namePrefix");
    		String nameRoot = villageNBTtag.getString("nameRoot");
    		String nameSuffix = villageNBTtag.getString("nameSuffix");
    		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);
    		
    		// Do the below if you want to make the sign stand up
    		NBTTagCompound signCompound = new NBTTagCompound();
    		signContents.writeToNBT(signCompound);
    		// Add the standing tag as per Gany's Surface
    		signCompound.setBoolean("IsStanding", true);
    		signContents.readFromNBT(signCompound);
    		
    		world.setTileEntity(signX, signY, signZ, signContents);
    		int signFacing = 2; // 0=forward-facing; 1=leftward-facing; 2=backward-facing (toward you); 3=rightward-facing,  
    		world.setBlock(signX, signY, signZ, biomeSignBlock, ((signFacing + this.coordBaseMode)*4)%16, 2); // 2 is "send change to clients without block update notification"
    		
            return true;
        }
    }
    
    
    
    // --- Well --- //
    
    public static class PlainsMeetingPoint1 extends StartVN
    {
    	int xoffset = 2; int zoffset = 2; // Offsets from vanilla

	    int wellHeight = 4+1;
	    int wellTop = 78+1; 
	    int wellDepthDecrease=7;
	    
		public PlainsMeetingPoint1() {}
		
		public PlainsMeetingPoint1(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, int terrainType)
		{
		    super(chunkManager, componentType, random, posX, posZ, components, terrainType);
		    
		    // Establish orientation
            this.coordBaseMode = random.nextInt(4);
            switch (this.coordBaseMode)
            {
                case 0:
                case 2:
                    this.boundingBox = new StructureBoundingBox(posX, 64+wellDepthDecrease, posZ, posX + 6 - 1 + 4, wellTop, posZ + 6 - 1 + 4);
                    break;
                default:
                    this.boundingBox = new StructureBoundingBox(posX, 64+wellDepthDecrease, posZ, posX + 6 - 1 + 4, wellTop, posZ + 6 - 1 + 4);
            }
            
            //StructureVillageVN.establishBiomeBlocks(this, posX, posZ);
		}
		
		/*
		 * Add the paths that lead outward from this structure
		 */
		public void buildComponent(StructureComponent start, List components, Random random)
		{
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.maxY - wellHeight, this.boundingBox.minZ + 4, 1, this.getComponentType());
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.maxY - wellHeight, this.boundingBox.minZ + 4, 3, this.getComponentType());
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 4, this.boundingBox.maxY - wellHeight, this.boundingBox.minZ - 1, 2, this.getComponentType());
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 4, this.boundingBox.maxY - wellHeight, this.boundingBox.maxZ + 1, 0, this.getComponentType());
		}
		
		/*
		 * Construct the structure
		 */
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, this); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, this); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.standing_sign, 0, this); Block biomeSignBlock = (Block)blockObject[0];
        	
        	if (this.field_143015_k < 0)
            {
                //this.field_143015_k = StructureVillagePiecesVN.getMedianGroundLevel(world, structureBB, true);//this.getAverageGroundLevel(world, structureBoundingBox);
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				new StructureBoundingBox(
        						this.boundingBox.minX, this.boundingBox.minZ,
        						this.boundingBox.maxX, this.boundingBox.maxZ), // Set the bounding box version as this bounding box but with Y going from 0 to 512
        				true);
        		
                //if (GeneralConfig.debugMessages) {LogHelper.info("Average ground level for well: " + this.field_143015_k + " at " + (this.boundingBox.minX+this.boundingBox.maxX)/2 + " " + (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
                
                if (this.field_143015_k < 0) {return true;} // Do not construct a well in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + (wellHeight-1) - wellDepthDecrease, 0);
            }
            
            // The well gets filled completely with water first
            //this.fillWithBlocks(world, structureBoundingBox, 1+xoffset, 0+wellDepthDecrease, 1+zoffset, 4+xoffset, 12, 4+zoffset, this.biomeCobblestoneBlock, Blocks.flowing_water, false);
            this.fillWithMetadataBlocks(world, structureBB, 1+xoffset, 0+wellDepthDecrease, 1+zoffset, 4+xoffset, 12, 4+zoffset, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            this.fillWithBlocks(world, structureBB, 2+xoffset, 1+wellDepthDecrease, 2+zoffset, 3+xoffset, 12, 3+zoffset, Blocks.flowing_water, Blocks.flowing_water, false); // Water
            
            // I believe this replaces the top water level with air
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 2+xoffset, 12, 2+zoffset, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 3+xoffset, 12, 2+zoffset, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 2+xoffset, 12, 3+zoffset, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 3+xoffset, 12, 3+zoffset, structureBB);
            
            // Well support posts
            for (int i : new int[]{1, 4})
            {
                for (int j : new int[]{1, 4})
                {
                	this.fillWithBlocks(world, structureBB, i+xoffset, 13, j+zoffset, i+xoffset, 14, j+zoffset, biomeFenceBlock, biomeFenceBlock, false);
                }
            }
            
            // Roof of the well
            this.fillWithMetadataBlocks(world, structureBB, 1+xoffset, 15, 1+zoffset, 4+xoffset, 15, 4+zoffset, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            
            // Line the well with cobblestone and ensure the spaces above are clear
            for (int i = 0; i <= 5; ++i)
            {
                for (int j = 0; j <= 5; ++j)
                {
                    if (j == 0 || j == 5 || i == 0 || i == 5)
                    {
                    	this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, j+xoffset, 11, i+zoffset, structureBB);
                    	this.fillWithMetadataBlocks(world, structureBB, j+xoffset, 0+wellDepthDecrease, i+zoffset, j+xoffset, 11, i+zoffset, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
                        this.clearCurrentPositionBlocksUpwards(world, j+xoffset, 12, i+zoffset, structureBB);
                    }
                }
            }
            
            // How to place mod doors
            /*
            for (int i : new int[]{0,1})
            {
            	this.placeBlockAtCurrentPosition(world, ModObjects.chooseModDoor(2), this.getMetadataWithOffset(Blocks.wooden_door, 0) + 8*i, 5+xoffset, 12+i, 2+zoffset, structureBB);
            	this.placeBlockAtCurrentPosition(world, ModObjects.chooseModDoor(3), this.getMetadataWithOffset(Blocks.wooden_door, 1) + 8*i, 2+xoffset, 12+i, 0+zoffset, structureBB);
            	this.placeBlockAtCurrentPosition(world, ModObjects.chooseModDoor(4), this.getMetadataWithOffset(Blocks.wooden_door, 2) + 8*i, 0+xoffset, 12+i, 2+zoffset, structureBB);
            	this.placeBlockAtCurrentPosition(world, ModObjects.chooseModDoor(5), this.getMetadataWithOffset(Blocks.wooden_door, 3) + 8*i, 2+xoffset, 12+i, 5+zoffset, structureBB);
            }
            */
            
            // Over-lid torches
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 1+xoffset, 16, 1+zoffset, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 1+xoffset, 16, 4+zoffset, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 4+xoffset, 16, 1+zoffset, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 4+xoffset, 16, 4+zoffset, structureBB);
            
            
            // Encircle the well with path
            Block pathBlock = ModObjects.chooseModPathBlock();
        	StructureVillagePieces.Start startPiece_reflected = ReflectionHelper.getPrivateValue(StructureVillagePieces.Village.class, this, new String[]{"startPiece"});
        	for (int i = 1; i <= 8; ++i)
            {
                for (int j = 1; j <= 8; ++j)
                {
                    if (j == 1 || j == 8 || i == 1 || i == 8)
                    {
                    	// Gets ground level, so long as it's not leaves or other foliage
                        //int k = world.getTopSolidOrLiquidBlock(this.getBoundingBox().minX+i, this.getBoundingBox().minZ+j) - 1;
                    	int k = world.getTopSolidOrLiquidBlock(this.getXWithOffset(i, j), this.getZWithOffset(i, j)) - 1;
                        if (k > -1)
                        {
                        	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getBoundingBox().minX+i, k, this.getBoundingBox().minZ+j);
                        	this.clearCurrentPositionBlocksUpwards(world, i, k+1, j, structureBB);
                       	}
                    }
                }
            }
            // Add path nodules at the end
            for (int i : new int[]{3,4,5,6})
            {
            	for (int j : new int[]{0,9})
            	{
                    //int k = world.getTopSolidOrLiquidBlock(this.getBoundingBox().minX+i, this.getBoundingBox().minZ+j) - 1;
            		int k = world.getTopSolidOrLiquidBlock(this.getXWithOffset(i, j), this.getZWithOffset(i, j)) - 1;
                    if (k > -1)
                    {
                    	//StructureVillagePiecesVN.setPathSpecificBlock(world, this, 0, this.getBoundingBox().minX+i, k, this.getBoundingBox().minZ+j);
                    	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(i, j), k, this.getZWithOffset(i, j));
                    	this.clearCurrentPositionBlocksUpwards(world, i, k+1, j, structureBB);
                   	}
                    
                    //k = world.getTopSolidOrLiquidBlock(this.getBoundingBox().minX+j, this.getBoundingBox().minZ+i) - 1;
                    k = world.getTopSolidOrLiquidBlock(this.getXWithOffset(j, i), this.getZWithOffset(j, i)) - 1;
                    if (k > -1)
                    {
                    	//StructureVillagePiecesVN.setPathSpecificBlock(world, this, 0, this.getBoundingBox().minX+j, k, this.getBoundingBox().minZ+i);
                    	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(j, i), k, this.getZWithOffset(j, i));
                    	this.clearCurrentPositionBlocksUpwards(world, j, k+1, i, structureBB);
                   	}
            	}
            }
            

            // Sign
            int signX = this.getXWithOffset(6, 7);
            int signY = this.getYWithOffset(12);
            int signZ = this.getZWithOffset(6, 7);
    		NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, random, signX, signY, signZ);
    		String namePrefix = villageNBTtag.getString("namePrefix");
    		String nameRoot = villageNBTtag.getString("nameRoot");
    		String nameSuffix = villageNBTtag.getString("nameSuffix");
    		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);
    		
    		// Do the below if you want to make the sign stand up
    		NBTTagCompound signCompound = new NBTTagCompound();
    		signContents.writeToNBT(signCompound);
    		// Add the standing tag as per Gany's Surface
    		signCompound.setBoolean("IsStanding", true);
    		signContents.readFromNBT(signCompound);
    		
    		world.setTileEntity(signX, signY, signZ, signContents);
    		int signFacing = 0; // 0=forward-facing; 1=leftward-facing; 2=backward-facing (toward you); 3=rightward-facing,  
    		world.setBlock(signX, signY, signZ, biomeSignBlock, ((signFacing + this.coordBaseMode)*4)%16, 2); // 2 is "send change to clients without block update notification"
    		
            return true;
        }
    }
}
