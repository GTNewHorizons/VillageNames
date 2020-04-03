package astrotibs.villagenames.village.biomestructures;

import java.util.List;
import java.util.Random;

import astrotibs.villagenames.banner.TileEntityBanner;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.integration.tools.TileEntityWoodSign;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.village.StructureVillageVN;
import astrotibs.villagenames.village.StructureVillageVN.StartVN;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
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
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + 8, 68, posZ + 8);
                    break;
                default: // 1: East; 3: West
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + 8, 68, posZ + 8);
            }

            //StructureVillageVN.establishBiomeBlocks(this, posX, posZ);
    	}

		/*
		 * Add the paths that lead outward from this structure
		 */
		public void buildComponent(StructureComponent start, List components, Random random)
		{
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 3, 1, this.getComponentType());
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 3, 3, this.getComponentType());
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 3, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 3, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
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
            int signXBB = 4;
			int signYBB = 2;
			int signZBB = 2;
            int signX = this.getXWithOffset(signXBB, signZBB);
            int signY = this.getYWithOffset(signYBB);
            int signZ = this.getZWithOffset(signXBB, signZBB);
    		NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, random, signX, signY, signZ);
    		String namePrefix = villageNBTtag.getString("namePrefix");
    		String nameRoot = villageNBTtag.getString("nameRoot");
    		String nameSuffix = villageNBTtag.getString("nameSuffix");
    		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);
    		
    		int signFacing = 2; // 0=forward-facing; 1=leftward-facing; 2=backward-facing (toward you); 3=rightward-facing,  

    		// Clear the spaces around the sign
            /*
    		for (int i=0; i < (signFacing%2==0 ? 3 : 2); i++)
            {
            	for (int j=0; j < (signFacing%2==0 ? 2 : 3); j++)
            	{
            		this.clearCurrentPositionBlocksUpwards(world, signXBB+i+(signFacing==3 ? 0 : -1), signYBB, signZBB+j+(signFacing==2 ? 0 : -1), structureBB);
            	}
            }
    		*/
    		if (biomeSignBlock.getUnlocalizedName().toLowerCase().contains("ganyssurface"))
    		{
    			// Set the sign and its orientation
				world.setBlock(signX, signY, signZ, biomeSignBlock);
				world.setBlockMetadataWithNotify(signX, signY, signZ, ((signFacing + this.coordBaseMode + (this.coordBaseMode==0 || this.coordBaseMode==1 ? 2: 0))*4)%16, 3);
				
				// Set the tile entity
				TileEntity tileModSign = new TileEntityWoodSign();
				NBTTagCompound modifystanding = new NBTTagCompound();
				tileModSign.writeToNBT(modifystanding);
				modifystanding.setBoolean("IsStanding", true);
				tileModSign.readFromNBT(modifystanding);
				
        		world.setTileEntity(signX, signY, signZ, tileModSign);
    		}
    		else
    		{
    			world.setBlock(signX, signY, signZ, biomeSignBlock, ((signFacing + this.coordBaseMode)*4)%16, 3); // 2 is "send change to clients without block update notification"
        		world.setTileEntity(signX, signY, signZ, signContents);
    		}
    		
    		
        	// Banner
    		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
    		if (testForBanner!=null)
			{
    			int bannerXBB = 8;
    			int bannerYBB = 1;
    			int bannerZBB = 6;
    			int bannerX = this.getXWithOffset(bannerXBB, bannerZBB);
    			int bannerY = this.getYWithOffset(bannerYBB);
                int bannerZ = this.getZWithOffset(bannerXBB, bannerZBB);
                int bannerFacing = 2; // 0=backward-facing (toward you); 1=rightward-facing; 2=forward-facing; 3=leftward-facing;  
                
                // Clear the spaces around the banner
                /*
                for (int i=0; i < (bannerFacing%2==0 ? 3 : 2); i++)
                {
                	for (int j=0; j < (bannerFacing%2==0 ? 2 : 3); j++)
                	{
                		this.clearCurrentPositionBlocksUpwards(world, bannerXBB+i+(bannerFacing==1 ? 0 : -1), bannerYBB, bannerZBB+j+(bannerFacing==0 ? 0 : -1), structureBB);
                	}
                }
                 */
                // Place a cobblestone foundation
                this.fillWithMetadataBlocks(world, structureBB, bannerXBB, bannerYBB-3, bannerZBB, bannerXBB, bannerYBB-1, bannerZBB, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
                
            	// Set the banner and its orientation
				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, ((bannerFacing + this.coordBaseMode + (this.coordBaseMode==0 || this.coordBaseMode==1 ? 2: 0))*4)%16, 2);
				
				// Set the tile entity
				TileEntity tilebanner = new TileEntityBanner();
				NBTTagCompound modifystanding = new NBTTagCompound();
				tilebanner.writeToNBT(modifystanding);
				modifystanding.setBoolean("IsStanding", true);
				tilebanner.readFromNBT(modifystanding);
				ItemStack villageBanner = ModObjects.chooseModBannerItem();
				villageBanner.setTagInfo("BlockEntityTag", villageNBTtag.getCompoundTag("BlockEntityTag"));
				
    			((TileEntityBanner) tilebanner).setItemValues(villageBanner);
        		
        		world.setTileEntity(bannerX, bannerY, bannerZ, tilebanner);
			}
    		
    		
    		// Villagers
            if (!this.villagersGenerated)
            {
            	this.villagersGenerated=true;
            	            	
        		for (int[] ia : new int[][]{
        			{6, 1, 1, -1, 0},
        			{1, 1, 2, -1, 0},
        			{1, 1, 7, -1, 0},
        			})
        		{
        			EntityVillager entityvillager = new EntityVillager(world);
        			
        			// Nitwits more often than not
        			if (GeneralConfig.enableNitwit && random.nextInt(3)==0) {entityvillager.setProfession(5);}
        			else {entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], -random.nextInt(24001));}
        			
        			int villagerY = world.getTopSolidOrLiquidBlock(this.getXWithOffset(ia[0], ia[2]), this.getZWithOffset(ia[0], ia[2]));
        			
        			if (villagerY > -1)
        			{
        				entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, villagerY, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
                        		random.nextFloat()*360F, 0.0F);
                        world.spawnEntityInWorld(entityvillager);
        			}
        		}
            }
            
            return true;
        }
    }
    
    
    
    // --- Well --- //
    
    public static class PlainsMeetingPoint1 extends StartVN
    {
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
                    this.boundingBox = new StructureBoundingBox(posX, 64+wellDepthDecrease, posZ, posX + 9, wellTop, posZ + 9);
                    break;
                default:
                    this.boundingBox = new StructureBoundingBox(posX, 64+wellDepthDecrease, posZ, posX + 9, wellTop, posZ + 9);
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
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct a well in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + (wellHeight-1) - wellDepthDecrease, 0);
            }
        	
            // The well gets filled completely with water first
            //this.fillWithBlocks(world, structureBoundingBox, 3, 0+wellDepthDecrease, 3, 6, 12, 6, this.biomeCobblestoneBlock, Blocks.flowing_water, false);
            this.fillWithMetadataBlocks(world, structureBB, 3, 0+wellDepthDecrease, 3, 6, 12, 6, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            this.fillWithBlocks(world, structureBB, 4, 1+wellDepthDecrease, 4, 5, 12, 5, Blocks.flowing_water, Blocks.flowing_water, false); // Water
            
            // I believe this replaces the top water level with air
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 4, 12, 4, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 5, 12, 4, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 4, 12, 5, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 5, 12, 5, structureBB);
            
            // Well support posts
            for (int i : new int[]{3, 6})
            {
                for (int j : new int[]{3, 6})
                {
                	this.fillWithBlocks(world, structureBB, i, 13, j, i, 14, j, biomeFenceBlock, biomeFenceBlock, false);
                }
            }
            
            // Roof of the well
            this.fillWithMetadataBlocks(world, structureBB, 3, 15, 3, 6, 15, 6, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            
            // Line the well with cobblestone and ensure the spaces above are clear
            for (int i = 2; i <= 7; ++i)
            {
                for (int j = 2; j <= 7; ++j)
                {
                    if (j == 2 || j == 7 || i == 2 || i == 7)
                    {
                    	//this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, j, 11, i, structureBB);
                    	this.fillWithMetadataBlocks(world, structureBB, j, 0+wellDepthDecrease, i, j, 11, i, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
                        this.clearCurrentPositionBlocksUpwards(world, j, 12, i, structureBB);
                    }
                }
            }
            
            // How to place mod doors
            /*
            for (int i : new int[]{0,1})
            {
            	this.placeBlockAtCurrentPosition(world, ModObjects.chooseModDoor(2), this.getMetadataWithOffset(Blocks.wooden_door, 0) + 8*i, 7, 12+i, 4, structureBB);
            	this.placeBlockAtCurrentPosition(world, ModObjects.chooseModDoor(3), this.getMetadataWithOffset(Blocks.wooden_door, 1) + 8*i, 4, 12+i, 2, structureBB);
            	this.placeBlockAtCurrentPosition(world, ModObjects.chooseModDoor(4), this.getMetadataWithOffset(Blocks.wooden_door, 2) + 8*i, 2, 12+i, 4, structureBB);
            	this.placeBlockAtCurrentPosition(world, ModObjects.chooseModDoor(5), this.getMetadataWithOffset(Blocks.wooden_door, 3) + 8*i, 4, 12+i, 7, structureBB);
            }
            */
            
            // Over-lid torches
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 3, 16, 3, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 3, 16, 6, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 6, 16, 3, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 6, 16, 6, structureBB);
            
            
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
            int signXBB = 6;
			int signYBB = 12;
			int signZBB = 7;
            int signX = this.getXWithOffset(signXBB, signZBB);
            int signY = this.getYWithOffset(signYBB);
            int signZ = this.getZWithOffset(signXBB, signZBB);
    		NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, random, signX, signY, signZ);
    		String namePrefix = villageNBTtag.getString("namePrefix");
    		String nameRoot = villageNBTtag.getString("nameRoot");
    		String nameSuffix = villageNBTtag.getString("nameSuffix");
    		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);
    		
    		int signFacing = 0; // 0=forward-facing; 1=leftward-facing; 2=backward-facing (toward you); 3=rightward-facing,  

    		// Clear the spaces around the sign
    		/*
            for (int i=0; i < (signFacing%2==0 ? 3 : 2); i++)
            {
            	for (int j=0; j < (signFacing%2==0 ? 2 : 3); j++)
            	{
            		this.clearCurrentPositionBlocksUpwards(world, signXBB+i+(signFacing==3 ? 0 : -1), signYBB, signZBB+j+(signFacing==2 ? 0 : -1), structureBB);
            	}
            }
    		*/
    		if (biomeSignBlock.getUnlocalizedName().toLowerCase().contains("ganyssurface"))
    		{
    			// Set the sign and its orientation
				world.setBlock(signX, signY, signZ, biomeSignBlock);
				world.setBlockMetadataWithNotify(signX, signY, signZ, ((signFacing + this.coordBaseMode + (this.coordBaseMode==0 || this.coordBaseMode==1 ? 2: 0))*4)%16, 3);
				
				// Set the tile entity
				TileEntity tileModSign = new TileEntityWoodSign();
				NBTTagCompound modifystanding = new NBTTagCompound();
				tileModSign.writeToNBT(modifystanding);
				modifystanding.setBoolean("IsStanding", true);
				tileModSign.readFromNBT(modifystanding);
				
        		world.setTileEntity(signX, signY, signZ, tileModSign);
    		}
    		else
    		{
    			world.setBlock(signX, signY, signZ, biomeSignBlock, ((signFacing + this.coordBaseMode)*4)%16, 3); // 2 is "send change to clients without block update notification"
        		world.setTileEntity(signX, signY, signZ, signContents);
    		}
    		
    		
    		// Banner
    		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
    		if (testForBanner!=null)
			{
                int bannerXBB = 8;
    			int bannerYBB = 11;
    			int bannerZBB = 6;
    			int bannerX = this.getXWithOffset(bannerXBB, bannerZBB);
    			int bannerY = this.getYWithOffset(bannerYBB);
                int bannerZ = this.getZWithOffset(bannerXBB, bannerZBB);
                int bannerFacing = 1; // 0=backward-facing (toward you); 1=rightward-facing; 2=forward-facing; 3=leftward-facing;  
                
                // Clear the spaces around the banner
                /*
                for (int i=0; i < (bannerFacing%2==0 ? 3 : 2); i++)
                {
                	for (int j=0; j < (bannerFacing%2==0 ? 2 : 3); j++)
                	{
                		this.clearCurrentPositionBlocksUpwards(world, bannerXBB+i+(bannerFacing==1 ? 0 : -1), bannerYBB, bannerZBB+j+(bannerFacing==0 ? 0 : -1), structureBB);
                	}
                }*/
                
                // Place a cobblestone foundation
                this.fillWithMetadataBlocks(world, structureBB, bannerXBB, bannerYBB-3, bannerZBB, bannerXBB, bannerYBB-1, bannerZBB, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
                
            	// Set the banner and its orientation
				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, ((bannerFacing + this.coordBaseMode + (this.coordBaseMode==0 || this.coordBaseMode==1 ? 2: 0))*4)%16, 2);
				
				// Set the tile entity
				TileEntity tilebanner = new TileEntityBanner();
				NBTTagCompound modifystanding = new NBTTagCompound();
				tilebanner.writeToNBT(modifystanding);
				modifystanding.setBoolean("IsStanding", true);
				tilebanner.readFromNBT(modifystanding);
				ItemStack villageBanner = ModObjects.chooseModBannerItem();
				villageBanner.setTagInfo("BlockEntityTag", villageNBTtag.getCompoundTag("BlockEntityTag"));
				
    			((TileEntityBanner) tilebanner).setItemValues(villageBanner);
        		
        		world.setTileEntity(bannerX, bannerY, bannerZ, tilebanner);
			}
    		
    		
    		// Villagers
            if (!this.villagersGenerated)
            {
            	this.villagersGenerated=true;
            	
        		for (int[] ia : new int[][]{
        			{1, 11, 8, -1, 0},
        			{8, 11, 8, -1, 0},
        			})
        		{
        			EntityVillager entityvillager = new EntityVillager(world);
        			
        			// Nitwits more often than not
        			if (GeneralConfig.enableNitwit && random.nextInt(3)==0) {entityvillager.setProfession(5);}
        			else {entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], -random.nextInt(24001));}
        			
        			int villagerY = world.getTopSolidOrLiquidBlock(this.getXWithOffset(ia[0], ia[2]), this.getZWithOffset(ia[0], ia[2]));
        			
        			if (villagerY > -1)
        			{
        				entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, villagerY, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
                        		random.nextFloat()*360F, 0.0F);
                        world.spawnEntityInWorld(entityvillager);
        			}
        		}
            }
            
            return true;
        }
    }
}
