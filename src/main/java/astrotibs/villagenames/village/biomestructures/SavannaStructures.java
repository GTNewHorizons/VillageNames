package astrotibs.villagenames.village.biomestructures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import astrotibs.villagenames.banner.TileEntityBanner;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.village.StructureVillageVN;
import astrotibs.villagenames.village.StructureVillageVN.StartVN;
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

public class SavannaStructures
{
	// -------------------- //
    // --- Start Pieces --- //
	// -------------------- //
	
	// --- Savanna Market --- //
    
    public static class SavannaMeetingPoint1 extends StartVN
    {
	    public SavannaMeetingPoint1() {}
		
		public SavannaMeetingPoint1(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, int terrainType)
		{
		    super(chunkManager, componentType, random, posX, posZ, components, terrainType);
		    
    		int width = 13;
    		int depth = 11;
    		int height = 4;
    		
		    // Establish orientation
            this.coordBaseMode = random.nextInt(4);
            switch (this.coordBaseMode)
            {
                case 0:
                case 2:
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + width, 64+height, posZ + depth);
                    break;
                default:
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + depth, 64+height, posZ + width);
            }
		}
		
		/*
		 * Add the paths that lead outward from this structure
		 */
		@Override
		public void buildComponent(StructureComponent start, List components, Random random)
		{
    		if (GeneralConfig.debugMessages)
    		{
    			LogHelper.info(
    					this.materialType + " " +  this.villageType + " village generated in "
    					+ this.worldChunkMngr.getBiomeGenAt((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2).biomeName
    					+ " at x=" + (this.boundingBox.minX+this.boundingBox.maxX)/2 + ", z=" + (this.boundingBox.minZ+this.boundingBox.maxZ)/2
    					+ " with town center: " + start.getClass().toString().substring(start.getClass().toString().indexOf("$")+1) + " and coordBaseMode: " + this.coordBaseMode
    					);
    		}
    		
			// Northward
			if (this.coordBaseMode%2!=1) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode==0 ? 5 : this.coordBaseMode==2 ? 4 : 0), this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());}
			// Eastward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode==1 || this.coordBaseMode==2 ? 5 : 4), 3, this.getComponentType());
			// Southward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode==1 || this.coordBaseMode==2 ? 5 : 4), this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			if (this.coordBaseMode%2!=0) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode==1 ? 4 : 5), 1, this.getComponentType());}
		}
		
		/*
		 * Construct the structure
		 */
		@Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, this.materialType, this.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, this.materialType, this.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, this.materialType, this.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_slab, 0, this.materialType, this.biome); Block biomeWoodSlabBlock = (Block)blockObject[0]; int biomeWoodSlabMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.oak_stairs, 0, this.materialType, this.biome); Block biomeWoodenStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, this.materialType, this.biome); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.standing_sign, 0, this.materialType, this.biome); Block biomeStandingSignBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, this.materialType, this.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	
        	if (this.field_143015_k < 0)
            {
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				new StructureBoundingBox(
        						this.boundingBox.minX, this.boundingBox.minZ,
        						this.boundingBox.maxX, this.boundingBox.maxZ), // Set the bounding box version as this bounding box but with Y going from 0 to 512
        				true);
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct a well in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.minY -1, 0);
            }
        	
        	
        	// Generate or otherwise obtain village name and banner and colors
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world,
        			this.getXWithOffset(6, 7),
        			this.getYWithOffset(1),
        			this.getZWithOffset(6, 7));
        	int townColor = villageNBTtag.getInteger("townColor");
        	int townColor2 = villageNBTtag.getInteger("townColor2");
        	
        	// Top layer is grass
        	this.fillWithMetadataBlocks(world, structureBB, 0, 0, 0, 13, 0, 11, biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);
        	
        	// Clear above
        	for (int u=0; u<=13; u++) {for (int w=0; w<=11; w++) {
        			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, -1, w, structureBB); // Foundation
        			this.clearCurrentPositionBlocksUpwards(world, u, 1, w, structureBB);
            }}
        	
        	// Set grass paths
        	for (int[] grass_uw : new int[][]{
        		{1, 1}, {1, 2}, {1, 3}, 
        		{2, 2}, {2, 7}, {2, 9}, 
        		{3, 0}, {3, 1}, {3, 2}, {3, 3}, {3, 5}, {3, 6}, {3, 8}, {3, 10}, 
        		{4, 2}, {4, 3}, {4, 5}, {4, 6}, {4, 7}, {4, 8}, {4, 9}, {4, 10}, {4, 11},
        		{5, 0}, {5, 1}, {5, 2}, {5, 3}, {5, 4}, {5, 6}, {5, 7}, {5, 8}, {5, 10}, {5, 11}, 
        		{6, 0}, {6, 2}, {6, 4}, {6, 5}, {6, 7}, {6, 9}, {6, 10},
        		{7, 0}, {7, 1}, {7, 3}, {7, 7}, {7, 7}, {7, 9}, {7, 11}, 
        		{8, 2}, {8, 4}, {8, 5}, {8, 7}, {8, 8}, {8, 9}, {8, 10}, {8, 11}, 
        		{9, 0}, {9, 4}, {9, 5}, {9, 6}, {9, 7}, 
        		{10, 0}, {10, 3}, {10, 4}, {10, 5}, {10, 6}, {10, 7}, {10, 8}, {10, 10}, 
        		{11, 0}, {11, 3}, {11, 4}, {11, 6}, {11, 7}, {11, 8}, {11, 10}, {11, 11}, 
        		{12, 0}, {12, 2}, {12, 4}, {12, 5}, {12, 6}, {12, 7}, {12, 11}, 
        		{13, 0}, {13, 2}, {13, 3}, {13, 4}, {13, 5}, {13, 6}, {13, 8}, {13, 10},
        	})
        	{
        		StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(grass_uw[0], grass_uw[1]), this.getYWithOffset(0), this.getZWithOffset(grass_uw[0], grass_uw[1]));
        	}
        	
        	// Set unkempt grass
        	for (int[] grass_uw : new int[][]{
        		{2, 0}, {2, 1},
        		{3, 9},
        		{4, 0}, {4, 1}, {4, 4}, 
        		{5, 5}, 
        		{6, 6}, {6, 8}, 
        		{7, 0}, {7, 8}, {7, 10},
        		{8, 1}, {8, 3}, {8, 6}, 
        		{9, 2}, {9, 9}, {9, 11}, 
        		{10, 11}, 
        		{11, 1}, {11, 5}, 
        		{12, 9}, 
        		{13, 1}, {13, 7}, {13, 9}, 
        	})
        	{
        		this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 0, grass_uw[0], 1, grass_uw[1], structureBB);
        	}
        	// Tall Grass
        	this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 2, 0, 1, 5, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 8, 0, 2, 5, structureBB);
        	
        	// Stalls
        	
        	// Posts
        	this.fillWithBlocks(world, structureBB, 1, 1, 4, 1, 3, 4, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 3, 1, 4, 3, 3, 4, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 3, 1, 7, 3, 3, 7, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 1, 1, 7, 1, 3, 7, biomeFenceBlock, biomeFenceBlock, false);
        	// Awning
        	this.fillWithMetadataBlocks(world, structureBB, 1, 4, 4, 3, 4, 7, biomeWoodSlabBlock, biomeWoodSlabMeta, biomeWoodSlabBlock, biomeWoodSlabMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 1, 4, 5, 3, 4, 5, biomeWoodenStairsBlock, (new int[]{2,1,3,0})[this.coordBaseMode], biomeWoodenStairsBlock, (new int[]{2,1,3,0})[this.coordBaseMode], false);
        	this.fillWithMetadataBlocks(world, structureBB, 1, 4, 6, 3, 4, 6, biomeWoodenStairsBlock, (new int[]{3,0,2,1})[this.coordBaseMode], biomeWoodenStairsBlock, (new int[]{3,0,2,1})[this.coordBaseMode], false);
        	// Logs
        	this.fillWithMetadataBlocks(world, structureBB, 2, 1, 5, 2, 1, 6, biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
        	
        	this.fillWithBlocks(world, structureBB, 9, 1, 1, 9, 3, 1, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 9, 1, 3, 9, 3, 3, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 12, 1, 3, 12, 3, 3, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 12, 1, 1, 12, 3, 1, biomeFenceBlock, biomeFenceBlock, false);
        	// Awning
        	this.fillWithMetadataBlocks(world, structureBB, 9, 4, 1, 12, 4, 3, biomeWoodSlabBlock, biomeWoodSlabMeta, biomeWoodSlabBlock, biomeWoodSlabMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 10, 4, 1, 10, 4, 3, biomeWoodenStairsBlock, this.coordBaseMode%2==0 ? 0 : 2, biomeWoodenStairsBlock, this.coordBaseMode%2==0 ? 0 : 2, false);
        	this.fillWithMetadataBlocks(world, structureBB, 11, 4, 1, 11, 4, 3, biomeWoodenStairsBlock, this.coordBaseMode%2==0 ? 1 : 3, biomeWoodenStairsBlock, this.coordBaseMode%2==0 ? 1 : 3, false);
        	// Logs
        	this.fillWithMetadataBlocks(world, structureBB, 10, 1, 2, 11, 1, 2, biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
        	
        	this.fillWithBlocks(world, structureBB, 9, 1, 8, 9, 3, 8, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 9, 1, 10, 9, 3, 10, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 12, 1, 10, 12, 3, 10, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 12, 1, 8, 12, 3, 8, biomeFenceBlock, biomeFenceBlock, false);        	
        	// Awning
        	this.fillWithMetadataBlocks(world, structureBB, 9, 4, 8, 12, 4, 10, biomeWoodSlabBlock, biomeWoodSlabMeta, biomeWoodSlabBlock, biomeWoodSlabMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 10, 4, 8, 10, 4, 10, biomeWoodenStairsBlock, this.coordBaseMode%2==0 ? 0 : 2, biomeWoodenStairsBlock, this.coordBaseMode%2==0 ? 0 : 2, false);
        	this.fillWithMetadataBlocks(world, structureBB, 11, 4, 8, 11, 4, 10, biomeWoodenStairsBlock, this.coordBaseMode%2==0 ? 1 : 3, biomeWoodenStairsBlock, this.coordBaseMode%2==0 ? 1 : 3, false);
        	// Logs
        	this.fillWithMetadataBlocks(world, structureBB, 10, 1, 9, 11, 1, 9, biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
        	
        	// Fences with torches
        	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, 2, 1, 10, structureBB);
        	world.setBlock(this.getXWithOffset(2, 10), this.getYWithOffset(2), this.getZWithOffset(2, 10), Blocks.torch, 0, 2);
        	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, 8, 1, 0, structureBB);
        	world.setBlock(this.getXWithOffset(8, 0), this.getYWithOffset(2), this.getZWithOffset(8, 0), Blocks.torch, 0, 2);        	
        	
        	
			// Banners on the market stalls
    		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
    		if (testForBanner!=null)
			{
    			for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color
    				{0, 4, 6, 3, townColor},
    				{0, 4, 5, 3, townColor2},
    				{10, 4, 0, 2, townColor},
    				{11, 4, 0, 2, townColor2},
    				{11, 4, 11, 0, townColor},
    				{10, 4, 11, 0, townColor2},
    			})
    			{
        			int bannerXBB = uvwoc[0];
        			int bannerYBB = uvwoc[1];
        			int bannerZBB = uvwoc[2];
        			
        			int bannerX = this.getXWithOffset(bannerXBB, bannerZBB);
        			int bannerY = this.getYWithOffset(bannerYBB);
                    int bannerZ = this.getZWithOffset(bannerXBB, bannerZBB);
                    
                	// Set the banner and its orientation
    				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
    				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(uvwoc[3], this.coordBaseMode, true), 2);
    				
    				// Set the tile entity
    				TileEntity tilebanner = new TileEntityBanner();
    				NBTTagCompound modifystanding = new NBTTagCompound();
    				tilebanner.writeToNBT(modifystanding);
    				modifystanding.setBoolean("IsStanding", false);
    				modifystanding.setInteger("Base", GeneralConfig.decorateVillageCenter ? uvwoc[4] : 12);
    				tilebanner.readFromNBT(modifystanding);
    				
            		world.setTileEntity(bannerX, bannerY, bannerZ, tilebanner);
    			}
			}
        	
        	
        	// Sign
            int signXBB = 7;
			int signYBB = 1;
			int signZBB = 6;
            int signX = this.getXWithOffset(signXBB, signZBB);
            int signY = this.getYWithOffset(signYBB);
            int signZ = this.getZWithOffset(signXBB, signZBB);
    		
    		String namePrefix = villageNBTtag.getString("namePrefix");
    		String nameRoot = villageNBTtag.getString("nameRoot");
    		String nameSuffix = villageNBTtag.getString("nameSuffix");
    		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);
    		
			world.setBlock(signX, signY, signZ, biomeStandingSignBlock, StructureVillageVN.getSignRotationMeta(4, this.coordBaseMode, false), 2); // 2 is "send change to clients without block update notification"
    		world.setTileEntity(signX, signY, signZ, signContents);
    		
    		
			// Banner
    		if (GeneralConfig.decorateVillageCenter)
    		{
        		testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
        		if (testForBanner!=null)
    			{
                    int bannerXBB = 11;
        			int bannerZBB = 6;
        			int bannerYBB = -1;
        			if (this.bannerY==-1)
        			{
        				this.bannerY = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(bannerXBB, bannerZBB), this.getZWithOffset(bannerXBB, bannerZBB))-this.boundingBox.minY;
        				bannerYBB = this.bannerY;
        			}
        			else {bannerYBB = this.bannerY;}
        			
        			int bannerX = this.getXWithOffset(bannerXBB, bannerZBB);
        			int bannerY = this.getYWithOffset(bannerYBB);
                    int bannerZ = this.getZWithOffset(bannerXBB, bannerZBB);
                    
                    // Place a grass foundation
                    this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, bannerXBB, bannerYBB-1, bannerZBB, structureBB);
                    this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, bannerXBB, bannerYBB-2, bannerZBB, structureBB);
                    // Clear space upward
                    this.clearCurrentPositionBlocksUpwards(world, bannerXBB, bannerYBB, bannerZBB, structureBB);
                    
                	// Set the banner and its orientation
    				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
    				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(4, this.coordBaseMode, false), 2);
    				
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
    		}

    		// Villagers
            if (!this.villagersGenerated)
            {
            	this.villagersGenerated=true;
            	
        		for (int[] ia : new int[][]{
        			{3, 1, 2, -1, 0},
        			{5, 1, 4, -1, 0},
        			{8, 1, 4, -1, 0},
        			})
        		{
        			EntityVillager entityvillager = new EntityVillager(world);
        			
        			// Nitwits more often than not
        			if (GeneralConfig.enableNitwit && random.nextInt(3)==0) {entityvillager.setProfession(5);}
        			else {entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], -12000-random.nextInt(12001));}
        			
        			int villagerY = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(ia[0], ia[2]), this.getZWithOffset(ia[0], ia[2]));
        			
        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)villagerY + 1.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
                    		random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(entityvillager);
        		}
            }
            
            return true;
        }
    }
    

	// --- Savanna Fountain --- //
    
    public static class SavannaMeetingPoint2 extends StartVN
    {
	    public SavannaMeetingPoint2() {}
		
		public SavannaMeetingPoint2(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, int terrainType)
		{
		    super(chunkManager, componentType, random, posX, posZ, components, terrainType);
		    
    		int width = 10;
    		int depth = 10;
    		int height = 5;
    		
		    // Establish orientation
            this.coordBaseMode = random.nextInt(4);
            switch (this.coordBaseMode)
            {
                case 0:
                case 2:
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + width, 64+height, posZ + depth);
                    break;
                default:
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + depth, 64+height, posZ + width);
            }
		}
		
		/*
		 * Add the paths that lead outward from this structure
		 */
		@Override
		public void buildComponent(StructureComponent start, List components, Random random)
		{
    		if (GeneralConfig.debugMessages)
    		{
    			LogHelper.info(
    					this.materialType + " " +  this.villageType + " village generated in "
    					+ this.worldChunkMngr.getBiomeGenAt((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2).biomeName
    					+ " at x=" + (this.boundingBox.minX+this.boundingBox.maxX)/2 + ", z=" + (this.boundingBox.minZ+this.boundingBox.maxZ)/2
    					+ " with town center: " + start.getClass().toString().substring(start.getClass().toString().indexOf("$")+1) + " and coordBaseMode: " + this.coordBaseMode
    					);
    		}
    		
			// Northward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 4, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 4, 3, this.getComponentType());
			// Southward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 4, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 4, 1, this.getComponentType());
		}
		
		/*
		 * Construct the structure
		 */
		@Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, this.materialType, this.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, this.materialType, this.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.standing_sign, 0, this.materialType, this.biome); Block biomeStandingSignBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, this.materialType, this.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	
        	if (this.field_143015_k < 0)
            {
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				new StructureBoundingBox(
        						this.boundingBox.minX, this.boundingBox.minZ,
        						this.boundingBox.maxX, this.boundingBox.maxZ), // Set the bounding box version as this bounding box but with Y going from 0 to 512
        				true);
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct a well in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.minY -1, 0);
            }
        	
        	
        	// Generate or otherwise obtain village name and banner and colors
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world,
        			this.getXWithOffset(9, 1),
        			this.getYWithOffset(2),
        			this.getZWithOffset(9, 1));
        	int townColor = villageNBTtag.getInteger("townColor");
        	int townColor2 = villageNBTtag.getInteger("townColor2");
        	

        	// Top layer 
        	
        	// Set grass paths
        	for (int[] grass_uw : new int[][]{
        		{0, 5}, {0, 6}, {0, 7}, 
        		{1, 5}, {1, 6}, {1, 7}, 
        		{2, 4}, {2, 5}, {2, 6}, {2, 7}, {2, 8},
        		{3, 3}, {3, 4}, {3, 8}, {3, 9},  
        		{4, 0}, {4, 1}, {4, 2}, {4, 8}, {4, 9}, {4, 10}, 
        		{5, 0}, {5, 1}, {5, 2}, {5, 8}, {5, 9}, {5, 10}, 
        		{6, 0}, {6, 1}, {6, 2}, {6, 8}, {6, 9}, {6, 10},
        		{7, 3}, {7, 4}, {7, 8}, {7, 9},  
        		{8, 4}, {8, 5}, {8, 6}, {8, 7}, {8, 8},
        		{9, 5}, {9, 6}, {9, 7}, 
        		{10, 5}, {10, 6}, {10, 7}, 
        	})
        	{
        		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, grass_uw[0], -1, grass_uw[1], structureBB); // Foundation
        		StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(grass_uw[0], grass_uw[1]), this.getYWithOffset(0), this.getZWithOffset(grass_uw[0], grass_uw[1])); // Path
        		this.clearCurrentPositionBlocksUpwards(world, grass_uw[0], 1, grass_uw[1], structureBB); // Clear above
        	}
        	// Set Grass blocks
        	for (int[] grass_uw : new int[][]{
        		{0, 3}, {0, 7},
        		{1, 1}, {1, 2}, {1, 3}, {1, 7}, {1, 8}, {1, 9}, 
        		{2, 1}, {2, 2}, {2, 8}, {2, 9}, 
        		{3, 1}, {3, 9}, {3, 10}, 
        		{4, 4}, {4, 5}, {4, 6},
        		{5, 4}, {5, 5}, {5, 6}, 
        		{6, 4}, {6, 5}, {6, 6}, 
        		{7, 9}, {7, 10}, 
        		{8, 8}, {8, 9}, 
        		{9, 1}, {9, 3}, {9, 7}, {9, 8}, {9, 9}, 
        		{10, 7}, 
        	})
        	{
        		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, grass_uw[0], -1, grass_uw[1], structureBB); // Foundation
        		this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, grass_uw[0], 0, grass_uw[1], structureBB);
        		this.clearCurrentPositionBlocksUpwards(world, grass_uw[0], 1, grass_uw[1], structureBB); // Clear above
        	}
        	// Set dirt
        	for (int[] uw : new int[][]{
        		{1, 1}, {1, 9}, 
        		{3, 4}, {3, 5}, {3, 6},
        		{4, 3}, {4, 7}, 
        		{5, 3}, {5, 5}, {5, 7},
        		{6, 3}, {6, 7},
        		{7, 4}, {7, 5}, {7, 6},
        		{9, 1}, {9, 9},
        	})
        	{
        		this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, uw[0], 0, uw[1], structureBB);
        	}
        	
        	// Set unkempt grass
        	for (int[] grass_uw : new int[][]{
        		{7, 10},
        		{8, 8}, {8, 9}, 
        		{9, 7}, 
        		{10, 7},
        	})
        	{
        		this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 0, grass_uw[0], 1, grass_uw[1], structureBB);
        	}
        	
        	
        	// Fountain
        	
        	// Set rim
        	this.fillWithMetadataBlocks(world, structureBB, 3, 1, 4, 7, 1, 6, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor2 : 1, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor2 : 1, false);
        	this.fillWithMetadataBlocks(world, structureBB, 4, 1, 3, 6, 1, 7, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor2 : 1, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor2 : 1, false);
        	// Set water
        	this.fillWithBlocks(world, structureBB, 4, 1, 4, 6, 1, 6, Blocks.flowing_water, Blocks.flowing_water, false);
        	// Set spire
        	this.fillWithMetadataBlocks(world, structureBB, 5, 1, 5, 5, 5, 5, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor : 4, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor : 4, false);
        	// Place individual clay blocks here and there
        	this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor : 4, 1, 1, 1, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor : 4, 9, 1, 1, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor : 4, 9, 1, 9, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor : 4, 1, 1, 9, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor : 4, 5, 1, 3, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor : 4, 5, 1, 7, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor : 4, 3, 1, 5, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor : 4, 7, 1, 5, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor2 : 1, 5, 4, 5, structureBB);
        	
        	// Torches
        	world.setBlock(this.getXWithOffset(1, 1), this.getYWithOffset(2), this.getZWithOffset(1, 1), Blocks.torch, 0, 2);     
        	world.setBlock(this.getXWithOffset(9, 9), this.getYWithOffset(2), this.getZWithOffset(9, 9), Blocks.torch, 0, 2);     
        	
        	// Banners
    		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
			if (testForBanner!=null)
			{
    			for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color
    				{5, 4, 6, 0, 12},
    				{6, 4, 5, 1, 12},
    				{5, 4, 4, 2, 12},
    				{4, 4, 5, 3, 12},
    			})
    			{
        			int bannerXBB = uvwoc[0];
        			int bannerYBB = uvwoc[1];
        			int bannerZBB = uvwoc[2];
        			
        			int bannerX = this.getXWithOffset(bannerXBB, bannerZBB);
        			int bannerY = this.getYWithOffset(bannerYBB);
                    int bannerZ = this.getZWithOffset(bannerXBB, bannerZBB);
                    
                	// Set the banner and its orientation
    				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
    				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(uvwoc[3], this.coordBaseMode, true), 2);
    				
    				// Set the tile entity
    				TileEntity tilebanner = new TileEntityBanner();
    				NBTTagCompound modifystanding = new NBTTagCompound();
    				tilebanner.writeToNBT(modifystanding);
    				modifystanding.setBoolean("IsStanding", false);
    				
    				if (GeneralConfig.decorateVillageCenter)
    				{
        				tilebanner.readFromNBT(modifystanding);
        				ItemStack villageBanner = ModObjects.chooseModBannerItem();
        				villageBanner.setTagInfo("BlockEntityTag", villageNBTtag.getCompoundTag("BlockEntityTag"));
        				
            			((TileEntityBanner) tilebanner).setItemValues(villageBanner);
    				}
    				else
    				{
    					modifystanding.setInteger("Base", uvwoc[4]);
        				tilebanner.readFromNBT(modifystanding);
    				}
    				
            		world.setTileEntity(bannerX, bannerY, bannerZ, tilebanner);
    			}
			}
        	
    		
        	// Sign
            int signXBB = 9;
			int signYBB = 2;
			int signZBB = 1;
            int signX = this.getXWithOffset(signXBB, signZBB);
            int signY = this.getYWithOffset(signYBB);
            int signZ = this.getZWithOffset(signXBB, signZBB);
    		
    		String namePrefix = villageNBTtag.getString("namePrefix");
    		String nameRoot = villageNBTtag.getString("nameRoot");
    		String nameSuffix = villageNBTtag.getString("nameSuffix");
    		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);

			world.setBlock(signX, signY, signZ, biomeStandingSignBlock, StructureVillageVN.getSignRotationMeta(12, this.coordBaseMode, false), 2); // 2 is "send change to clients without block update notification"
    		world.setTileEntity(signX, signY, signZ, signContents);
    		
            int signXBB2 = 1;
			int signZBB2 = 9;
            int signX2 = this.getXWithOffset(signXBB2, signZBB2);
            int signZ2 = this.getZWithOffset(signXBB2, signZBB2);
            
            // I need to make a duplicate TileEntity because the first one gets consumed when applied to the first sign
    		TileEntitySign signContents2 = new TileEntitySign();
    		for (int i=0; i<4; i++) {signContents2.signText[i] = signContents.signText[i];}
            
			world.setBlock(signX2, signY, signZ2, biomeStandingSignBlock, StructureVillageVN.getSignRotationMeta(4, this.coordBaseMode, false), 2); // 2 is "send change to clients without block update notification"
    		world.setTileEntity(signX2, signY, signZ2, signContents2);
    		
    		
    		// Villagers
            if (!this.villagersGenerated)
            {
            	this.villagersGenerated=true;
            	
        		for (int[] ia : new int[][]{
        			{3, 1, 8, -1, 0},
        			{6, 1, 9, -1, 0},
        			{9, 1, 6, -1, 0},
        			})
        		{
        			EntityVillager entityvillager = new EntityVillager(world);
        			
        			// Nitwits more often than not
        			if (GeneralConfig.enableNitwit && random.nextInt(3)==0) {entityvillager.setProfession(5);}
        			else {entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], -12000-random.nextInt(12001));}
        			
        			int villagerY = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(ia[0], ia[2]), this.getZWithOffset(ia[0], ia[2]));
        			
        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)villagerY + 1.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
                    		random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(entityvillager);
        		}
            }
            
            return true;
        }
    }
    
    
	// --- Savanna Double Well --- //
    
    public static class SavannaMeetingPoint3 extends StartVN
    {
	    public SavannaMeetingPoint3() {}
		
		public SavannaMeetingPoint3(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, int terrainType)
		{
		    super(chunkManager, componentType, random, posX, posZ, components, terrainType);
		    
    		int width = 8;
    		int depth = 10;
    		int height = 5;
    		
		    // Establish orientation
            this.coordBaseMode = random.nextInt(4);
            switch (this.coordBaseMode)
            {
                case 0:
                case 2:
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + width, 64+height, posZ + depth);
                    break;
                default:
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + depth, 64+height, posZ + width);
            }
		}
		
		/*
		 * Add the paths that lead outward from this structure
		 */
		@Override
		public void buildComponent(StructureComponent start, List components, Random random)
		{
    		if (GeneralConfig.debugMessages)
    		{
    			LogHelper.info(
    					this.materialType + " " +  this.villageType + " village generated in "
    					+ this.worldChunkMngr.getBiomeGenAt((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2).biomeName
    					+ " at x=" + (this.boundingBox.minX+this.boundingBox.maxX)/2 + ", z=" + (this.boundingBox.minZ+this.boundingBox.maxZ)/2
    					+ " with town center: " + start.getClass().toString().substring(start.getClass().toString().indexOf("$")+1) + " and coordBaseMode: " + this.coordBaseMode
    					);
    		}
    		
			// Northward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode%2==0 ? 3 : 4), this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode%2==0 ? 4 : 3), 3, this.getComponentType());
			// Southward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode%2==0 ? 3 : 4), this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode%2==0 ? 4 : 3), 1, this.getComponentType());
		}
		
		/*
		 * Construct the structure
		 */
		@Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, this.materialType, this.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, this.materialType, this.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wall_sign, 0, this.materialType, this.biome); Block biomeWallSignBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, this.materialType, this.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, this.materialType, this.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_slab, 0, this.materialType, this.biome); Block biomeWoodSlabBlock = (Block)blockObject[0]; int biomeWoodSlabMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, this.materialType, this.biome); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4, this.materialType, this.biome); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1]; // Toward you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 8, this.materialType, this.biome); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1]; // Perpendicular to you
        	
        	// For bark specifically
        	Block biomeBarkOrLogHorAlongBlock = null; int biomeBarkOrLogHorAlongMeta = 0;
        	Block biomeBarkOrLogHorAcrossBlock = null; int biomeBarkOrLogHorAcrossMeta = 0;
        	
        	Block tryBark = Block.getBlockFromName(ModObjects.barkEF);
        	if (tryBark == null)
        	{
        		biomeBarkOrLogHorAlongBlock = biomeLogHorAlongBlock; biomeBarkOrLogHorAlongMeta = biomeLogHorAlongMeta%4+12;// + (this.coordBaseMode%2==0 ? 4 : 0);
        		biomeBarkOrLogHorAcrossBlock = biomeLogHorAcrossBlock; biomeBarkOrLogHorAcrossMeta = biomeLogHorAcrossMeta%4+12;// - (this.coordBaseMode%2==0 ? 4 : 0);
        	}
        	else
        	{
        		blockObject = StructureVillageVN.getBiomeSpecificBlock(Block.getBlockFromName(ModObjects.barkEF), 0, this.materialType, this.biome); biomeBarkOrLogHorAlongBlock = (Block)blockObject[0]; biomeBarkOrLogHorAlongMeta = (Integer)blockObject[1];
        		blockObject = StructureVillageVN.getBiomeSpecificBlock(Block.getBlockFromName(ModObjects.barkEF), 0, this.materialType, this.biome); biomeBarkOrLogHorAcrossBlock = (Block)blockObject[0]; biomeBarkOrLogHorAcrossMeta = (Integer)blockObject[1];
        	}
        	
        	
        	if (this.field_143015_k < 0)
            {
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				new StructureBoundingBox(
        						this.boundingBox.minX+1, this.boundingBox.minZ+1,
        						this.boundingBox.maxX-1, this.boundingBox.maxZ-1), // Set the bounding box version as this bounding box but with Y going from 0 to 512
        				true);
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct a well in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.minY -1, 0);
            }
        	
        	
        	// Generate or otherwise obtain village name and banner and colors
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world,
        			this.getXWithOffset(4, 5),
        			this.getYWithOffset(3),
        			this.getZWithOffset(4, 5));
        	int townColor = villageNBTtag.getInteger("townColor");
        	int townColor2 = villageNBTtag.getInteger("townColor2");
        	
        	// Top grass path 
        	for (int u=2; u<=6; u++) {for (int w=1; w<=9; w++) {
        		if (!(u==4 && w==3) && !(u==4 && w==7)) // To prevent path overwriting the water in the wells
        		{
        			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, -1, w, structureBB); // Foundation
            		StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(u, w), this.getYWithOffset(0), this.getZWithOffset(u, w)); // Path
            		this.clearCurrentPositionBlocksUpwards(world, u, 1, w, structureBB); // Clear above	
        		}
        	}}
        	
        	// Set grass paths
        	for (int[] uw : new int[][]{
        		{1, 3}, {1, 4}, {1, 5}, {1, 6}, {1, 7}, 
        		{7, 3}, {7, 4}, {7, 5}, {7, 6}, {7, 7}, 
        	})
        	{
        		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uw[0], -1, uw[1], structureBB); // Foundation
        		StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(uw[0], uw[1]), this.getYWithOffset(0), this.getZWithOffset(uw[0], uw[1])); // Path
        		this.clearCurrentPositionBlocksUpwards(world, uw[0], 1, uw[1], structureBB); // Clear above
        	}
        	// Set Grass blocks
        	for (int[] uw : new int[][]{
        		{0, 9},
        		{1, 1},
        		{9, 2}, 
        	})
        	{
        		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uw[0], -1, uw[1], structureBB); // Foundation
        		this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, uw[0], 0, uw[1], structureBB);
        		this.clearCurrentPositionBlocksUpwards(world, uw[0], 1, uw[1], structureBB); // Clear above
        	}
        	
        	
            // Add path nodules at the end
            for (int i=0; i<3; i++)
            {
        		for (int[] uw: new int[][]{
        			{i+3, 0},
        			{i+3, 10},
        			{0, i+4},
        			{8, i+4},
        		})
        		{
        			int k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(uw[0], uw[1]), this.getZWithOffset(uw[0], uw[1])) - 1;
            		
                    if (k > -1)
                    {
                    	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(uw[0], uw[1]), k, this.getZWithOffset(uw[0], uw[1]));
                    	this.clearCurrentPositionBlocksUpwards(world, uw[0], k+1-this.boundingBox.minY, uw[1], structureBB);
                   	}
        		}
            }
        	
        	
        	// Establish wells
        	
        	// Dirt
        	this.fillWithMetadataBlocks(world, structureBB, 3, 0, 2, 5, 0, 4, biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 3, 0, 6, 5, 0, 8, biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);
        	
        	// Basins
        	this.fillWithMetadataBlocks(world, structureBB, 3, 1, 2, 3, 1, 4, biomeBarkOrLogHorAlongBlock, biomeBarkOrLogHorAlongMeta, biomeBarkOrLogHorAlongBlock, biomeBarkOrLogHorAlongMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 5, 1, 2, 5, 1, 4, biomeBarkOrLogHorAlongBlock, biomeBarkOrLogHorAlongMeta, biomeBarkOrLogHorAlongBlock, biomeBarkOrLogHorAlongMeta, false);
        	this.placeBlockAtCurrentPosition(world, biomeBarkOrLogHorAcrossBlock, biomeBarkOrLogHorAcrossMeta, 4, 1, 2, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeBarkOrLogHorAcrossBlock, biomeBarkOrLogHorAcrossMeta, 4, 1, 4, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeBarkOrLogHorAlongBlock, biomeBarkOrLogHorAlongMeta, 4, 0, 3, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.flowing_water, 0, 4, 1, 3, structureBB);
        	
        	this.fillWithMetadataBlocks(world, structureBB, 3, 1, 6, 3, 1, 8, biomeBarkOrLogHorAlongBlock, biomeBarkOrLogHorAlongMeta, biomeBarkOrLogHorAlongBlock, biomeBarkOrLogHorAlongMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 5, 1, 6, 5, 1, 8, biomeBarkOrLogHorAlongBlock, biomeBarkOrLogHorAlongMeta, biomeBarkOrLogHorAlongBlock, biomeBarkOrLogHorAlongMeta, false);
        	this.placeBlockAtCurrentPosition(world, biomeBarkOrLogHorAcrossBlock, biomeBarkOrLogHorAcrossMeta, 4, 1, 6, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeBarkOrLogHorAcrossBlock, biomeBarkOrLogHorAcrossMeta, 4, 1, 8, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeBarkOrLogHorAcrossBlock, biomeBarkOrLogHorAcrossMeta, 4, 0, 7, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.flowing_water, 0, 4, 1, 7, structureBB);
        	
        	// Torches on the corners
        	for (int[] uvwm : new int[][]{
        		{3, 2, 2, 0},
        		{5, 2, 2, 0},
        		{3, 2, 8, 0},
        		{5, 2, 8, 0},
        	})
        	{
        		world.setBlock(this.getXWithOffset(uvwm[0], uvwm[2]), this.getYWithOffset(uvwm[1]), this.getZWithOffset(uvwm[0], uvwm[2]), Blocks.torch, uvwm[3], 2);
        	}
        	
        	// Pavilion
        	this.fillWithBlocks(world, structureBB, 3, 2, 4, 5, 3, 6, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithAir(world, structureBB, 4, 2, 4, 4, 3, 6);
        	this.fillWithAir(world, structureBB, 3, 2, 5, 5, 3, 5);
        	this.fillWithMetadataBlocks(world, structureBB, 3, 4, 4, 5, 4, 6, biomeWoodSlabBlock, biomeWoodSlabMeta, biomeWoodSlabBlock, biomeWoodSlabMeta, false);
        	this.placeBlockAtCurrentPosition(world, biomePlankBlock, biomePlankMeta, 4, 4, 5, structureBB);
        	// Torch
        	for (int[] uvwm : new int[][]{
        		{4, 5, 5, 0},
        	})
        	{
        		world.setBlock(this.getXWithOffset(uvwm[0], uvwm[2]), this.getYWithOffset(uvwm[1]), this.getZWithOffset(uvwm[0], uvwm[2]), Blocks.torch, uvwm[3], 2);
        	}
        	
			// Banner
    		if (GeneralConfig.decorateVillageCenter)
    		{
        		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
        		if (testForBanner!=null)
    			{
                    int bannerXBB = 7;
        			int bannerZBB = 7;
        			int bannerYBB = 1;
        			
        			int bannerX = this.getXWithOffset(bannerXBB, bannerZBB);
        			int bannerY = this.getYWithOffset(bannerYBB);
                    int bannerZ = this.getZWithOffset(bannerXBB, bannerZBB);
                    
                    // Place a cobblestone foundation
                    this.fillWithMetadataBlocks(world, structureBB, bannerXBB, bannerYBB-2, bannerZBB, bannerXBB, bannerYBB-1, bannerZBB, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
                    this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, bannerXBB, bannerYBB-3, bannerZBB, structureBB);
                    // Clear space upward
                    this.clearCurrentPositionBlocksUpwards(world, bannerXBB, bannerYBB, bannerZBB, structureBB);
                    
                	// Set the banner and its orientation
    				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
    				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(4, this.coordBaseMode, false), 2);
    				
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
    		}

        	// Sign support
        	this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor : 4, 4, 3, 5, structureBB);
        	
        	// Sign
            int signXBB = 3;
			int signYBB = 3;
			int signZBB = 5;
            int signX = this.getXWithOffset(signXBB, signZBB);
            int signY = this.getYWithOffset(signYBB);
            int signZ = this.getZWithOffset(signXBB, signZBB);
    		
    		String namePrefix = villageNBTtag.getString("namePrefix");
    		String nameRoot = villageNBTtag.getString("nameRoot");
    		String nameSuffix = villageNBTtag.getString("nameSuffix");
    		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);
    		
			world.setBlock(signX, signY, signZ, biomeWallSignBlock, StructureVillageVN.getSignRotationMeta(3, this.coordBaseMode, true), 2); // 2 is "send change to clients without block update notification"
    		world.setTileEntity(signX, signY, signZ, signContents);
    		
            int signXBB2 = 5;
            int signX2 = this.getXWithOffset(signXBB2, signZBB);
            int signZ2 = this.getZWithOffset(signXBB2, signZBB);
            
            // I need to make a duplicate TileEntity because the first one gets consumed when applied to the first sign
    		TileEntitySign signContents2 = new TileEntitySign();
    		for (int i=0; i<4; i++) {signContents2.signText[i] = signContents.signText[i];}
            
			world.setBlock(signX2, signY, signZ2, biomeWallSignBlock, StructureVillageVN.getSignRotationMeta(1, this.coordBaseMode, true), 2); // 2 is "send change to clients without block update notification"
    		world.setTileEntity(signX2, signY, signZ2, signContents2);
    		
    		
    		// Villagers
            if (!this.villagersGenerated)
            {
            	this.villagersGenerated=true;
            	
        		for (int[] ia : new int[][]{
        			{6, 1, 5, -1, 0},
        			{7, 1, 3, -1, 0},
        			{9, 1, 6, -1, 0},
        			})
        		{
        			EntityVillager entityvillager = new EntityVillager(world);
        			
        			// Nitwits more often than not
        			if (GeneralConfig.enableNitwit && random.nextInt(3)==0) {entityvillager.setProfession(5);}
        			else {entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], -12000-random.nextInt(12001));}
        			
        			int villagerY = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(ia[0], ia[2]), this.getZWithOffset(ia[0], ia[2]));
        			
        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)villagerY + 1.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
                    		random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(entityvillager);
        		}
            }
            
            return true;
        }
    }
    

	// --- Savanna Single Well --- //
    
    public static class SavannaMeetingPoint4 extends StartVN
    {
	    public SavannaMeetingPoint4() {}
		
		public SavannaMeetingPoint4(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, int terrainType)
		{
		    super(chunkManager, componentType, random, posX, posZ, components, terrainType);
		    
    		int width = 8;
    		int depth = 8;
    		int height = 5;
    		
		    // Establish orientation
            this.coordBaseMode = random.nextInt(4);
            switch (this.coordBaseMode)
            {
                case 0:
                case 2:
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + width, 64+height, posZ + depth);
                    break;
                default:
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + depth, 64+height, posZ + width);
            }
		}
		
		/*
		 * Add the paths that lead outward from this structure
		 */
		@Override
		public void buildComponent(StructureComponent start, List components, Random random)
		{
    		if (GeneralConfig.debugMessages)
    		{
    			LogHelper.info(
    					this.materialType + " " +  this.villageType + " village generated in "
    					+ this.worldChunkMngr.getBiomeGenAt((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2).biomeName
    					+ " at x=" + (this.boundingBox.minX+this.boundingBox.maxX)/2 + ", z=" + (this.boundingBox.minZ+this.boundingBox.maxZ)/2
    					+ " with town center: " + start.getClass().toString().substring(start.getClass().toString().indexOf("$")+1) + " and coordBaseMode: " + this.coordBaseMode
    					);
    		}
    		
			// Northward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 3, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 3, 3, this.getComponentType());
			// Southward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 3, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 3, 1, this.getComponentType());
		}
		
		/*
		 * Construct the structure
		 */
		@Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, this.materialType, this.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, this.materialType, this.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wall_sign, 0, this.materialType, this.biome); Block biomeWallSignBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, this.materialType, this.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, this.materialType, this.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, this.materialType, this.biome); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4, this.materialType, this.biome); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1]; // Toward you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.oak_stairs, 0, this.materialType, this.biome); Block biomeWoodenStairsBlock = (Block)blockObject[0];
        	
        	// For bark specifically
        	Block biomeBarkOrLogHorAlongBlock = null; int biomeBarkOrLogHorAlongMeta = 0;
        	
        	Block tryBark = Block.getBlockFromName(ModObjects.barkEF);
        	if (tryBark == null)
        	{
        		biomeBarkOrLogHorAlongBlock = biomeLogHorAlongBlock; biomeBarkOrLogHorAlongMeta = biomeLogHorAlongMeta%4+12;// + (this.coordBaseMode%2==0 ? 4 : 0);
        	}
        	else
        	{
        		blockObject = StructureVillageVN.getBiomeSpecificBlock(Block.getBlockFromName(ModObjects.barkEF), 0, this.materialType, this.biome); biomeBarkOrLogHorAlongBlock = (Block)blockObject[0]; biomeBarkOrLogHorAlongMeta = (Integer)blockObject[1];
        	}
        	
        	
        	if (this.field_143015_k < 0)
            {
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				new StructureBoundingBox(
        						this.boundingBox.minX+1, this.boundingBox.minZ+1,
        						this.boundingBox.maxX-1, this.boundingBox.maxZ-1), // Set the bounding box version as this bounding box but with Y going from 0 to 512
        				true);
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct a well in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.minY -1, 0);
            }
        	
        	
        	// Generate or otherwise obtain village name and banner and colors
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world,
        			this.getXWithOffset(4, 4),
        			this.getYWithOffset(3),
        			this.getZWithOffset(4, 4));
        	int townColor = villageNBTtag.getInteger("townColor");
        	int townColor2 = villageNBTtag.getInteger("townColor2");
        	
        	// Set grass paths
        	for (int u=1; u<=7; u++) {for (int w=1; w<=7; w++) {
        		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, -1, w, structureBB); // Foundation
        		if (u<3 || u>5 || w<3 || w>5) {StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(u, w), this.getYWithOffset(0), this.getZWithOffset(u, w));} // Path
        		else {this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, u, 0, w, structureBB);}
        		this.clearCurrentPositionBlocksUpwards(world, u, 1, w, structureBB); // Clear above
        	}}
        	// Set Grass blocks
        	for (int[] uw : new int[][]{
        		{0, 0},
        		{1, 10},
        	})
        	{
        		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uw[0], -1, uw[1], structureBB); // Foundation
        		this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, uw[0], 0, uw[1], structureBB);
        		this.clearCurrentPositionBlocksUpwards(world, uw[0], 1, uw[1], structureBB); // Clear above
        	}
        	
        	
            // Add path nodules at the end
            for (int i=0; i<3; i++)
            {
        		for (int[] uw: new int[][]{
        			{i+3, 0},
        			{i+3, 8},
        			{0, i+3},
        			{8, i+3},
        		})
        		{
        			int k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(uw[0], uw[1]), this.getZWithOffset(uw[0], uw[1])) - 1;
            		
                    if (k > -1)
                    {
                    	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(uw[0], uw[1]), k, this.getZWithOffset(uw[0], uw[1]));
                    	this.clearCurrentPositionBlocksUpwards(world, uw[0], k+1-this.boundingBox.minY, uw[1], structureBB);
                   	}
        		}
            }
        	
        	
        	// Establish wells
        	
        	// Dirt
        	this.fillWithMetadataBlocks(world, structureBB, 3, 0, 2, 5, 0, 6, biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 2, 0, 3, 5, 0, 5, biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);
        	
        	// Basins
        	this.fillWithMetadataBlocks(world, structureBB, 3, 1, 2, 5, 1, 6, biomeBarkOrLogHorAlongBlock, biomeBarkOrLogHorAlongMeta, biomeBarkOrLogHorAlongBlock, biomeBarkOrLogHorAlongMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 2, 1, 3, 6, 1, 5, biomeBarkOrLogHorAlongBlock, biomeBarkOrLogHorAlongMeta, biomeBarkOrLogHorAlongBlock, biomeBarkOrLogHorAlongMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 4, 0, 3, 4, 0, 5, biomeBarkOrLogHorAlongBlock, biomeBarkOrLogHorAlongMeta, biomeBarkOrLogHorAlongBlock, biomeBarkOrLogHorAlongMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 3, 0, 4, 5, 0, 4, biomeBarkOrLogHorAlongBlock, biomeBarkOrLogHorAlongMeta, biomeBarkOrLogHorAlongBlock, biomeBarkOrLogHorAlongMeta, false);
        	this.fillWithBlocks(world, structureBB, 4, 1, 3, 4, 1, 5, Blocks.flowing_water, Blocks.flowing_water, false);
        	this.fillWithBlocks(world, structureBB, 3, 1, 4, 5, 1, 4, Blocks.flowing_water, Blocks.flowing_water, false);
        	
        	// Torches on the corners
        	for (int[] uvwm : new int[][]{
        		{2, 2, 4, 0},
        		{4, 2, 2, 0},
        		{4, 2, 6, 0},
        		{6, 2, 4, 0},
        	})
        	{
        		world.setBlock(this.getXWithOffset(uvwm[0], uvwm[2]), this.getYWithOffset(uvwm[1]), this.getZWithOffset(uvwm[0], uvwm[2]), Blocks.torch, uvwm[3], 2);
        	}
        	
        	// Pavilion
        	// Supports
        	this.fillWithBlocks(world, structureBB, 3, 2, 3, 5, 3, 5, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithAir(world, structureBB, 4, 2, 3, 4, 3, 5);
        	this.fillWithAir(world, structureBB, 3, 2, 4, 5, 3, 4);
        	// Roof
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, (new int[]{2,1,3,0})[this.coordBaseMode], 3, 4, 3, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, (new int[]{2,1,3,0})[this.coordBaseMode], 4, 4, 3, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, this.coordBaseMode%2==0 ? 1 : 3, 5, 4, 3, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, this.coordBaseMode%2==0 ? 1 : 3, 5, 4, 4, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, (new int[]{3,0,2,1})[this.coordBaseMode], 5, 4, 5, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, (new int[]{3,0,2,1})[this.coordBaseMode], 4, 4, 5, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, this.coordBaseMode%2==0 ? 0 : 2, 3, 4, 5, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, this.coordBaseMode%2==0 ? 0 : 2, 3, 4, 4, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomePlankBlock, biomePlankMeta, 4, 4, 4, structureBB);
        	
			// Banner
    		if (GeneralConfig.decorateVillageCenter)
    		{
        		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
        		if (testForBanner!=null)
    			{
                    int bannerXBB = 7;
        			int bannerZBB = 7;
        			int bannerYBB = 1;
        			
        			int bannerX = this.getXWithOffset(bannerXBB, bannerZBB);
        			int bannerY = this.getYWithOffset(bannerYBB);
                    int bannerZ = this.getZWithOffset(bannerXBB, bannerZBB);
                    
                    // Place a cobblestone foundation
                    this.fillWithMetadataBlocks(world, structureBB, bannerXBB, bannerYBB-2, bannerZBB, bannerXBB, bannerYBB-1, bannerZBB, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
                    this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, bannerXBB, bannerYBB-3, bannerZBB, structureBB);
                    // Clear space upward
                    this.clearCurrentPositionBlocksUpwards(world, bannerXBB, bannerYBB, bannerZBB, structureBB);
                    
                	// Set the banner and its orientation
    				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
    				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(12, this.coordBaseMode, false), 2);
    				
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
    		}
    		
    		
        	// Sign support
        	this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor : 4, 4, 3, 4, structureBB);
        	
        	
        	// Sign
            int signXBB = 4;
			int signYBB = 3;
			int signZBB = 3;
            int signX = this.getXWithOffset(signXBB, signZBB);
            int signY = this.getYWithOffset(signYBB);
            int signZ = this.getZWithOffset(signXBB, signZBB);
    		
    		String namePrefix = villageNBTtag.getString("namePrefix");
    		String nameRoot = villageNBTtag.getString("nameRoot");
    		String nameSuffix = villageNBTtag.getString("nameSuffix");
    		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);
    		
			world.setBlock(signX, signY, signZ, biomeWallSignBlock, StructureVillageVN.getSignRotationMeta(2, this.coordBaseMode, true), 2); // 2 is "send change to clients without block update notification"
    		world.setTileEntity(signX, signY, signZ, signContents);
    		
            int signZBB2 = 5;
            int signX2 = this.getXWithOffset(signXBB, signZBB2);
            int signZ2 = this.getZWithOffset(signXBB, signZBB2);
            
            // I need to make a duplicate TileEntity because the first one gets consumed when applied to the first sign
    		TileEntitySign signContents2 = new TileEntitySign();
    		for (int i=0; i<4; i++) {signContents2.signText[i] = signContents.signText[i];}
            
			world.setBlock(signX2, signY, signZ2, biomeWallSignBlock, StructureVillageVN.getSignRotationMeta(0, this.coordBaseMode, true), 2); // 2 is "send change to clients without block update notification"
    		world.setTileEntity(signX2, signY, signZ2, signContents2);
    		
    		
    		// Villagers
            if (!this.villagersGenerated)
            {
            	this.villagersGenerated=true;
            	
        		for (int[] ia : new int[][]{
        			{7, 1, 1, -1, 0},
        			{8, 1, 3, -1, 0},
        			})
        		{
        			EntityVillager entityvillager = new EntityVillager(world);
        			
        			// Nitwits more often than not
        			if (GeneralConfig.enableNitwit && random.nextInt(3)==0) {entityvillager.setProfession(5);}
        			else {entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], -12000-random.nextInt(12001));}
        			
        			int villagerY = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(ia[0], ia[2]), this.getZWithOffset(ia[0], ia[2]));
        			
        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)villagerY + 1.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
                    		random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(entityvillager);
        		}
            }
            
            return true;
        }
    }
    
    
	/**
	 * Returns a list of blocks and coordinates used to construct a decor piece
	 */
	protected static ArrayList<BlueprintData> getRandomSavannaDecorBlueprint(StartVN startVN, int coordBaseMode, Random random)
	{
		int decorCount = 1;
		return getSavannaDecorBlueprint(random.nextInt(decorCount), startVN, coordBaseMode, random);
	}
	protected static ArrayList<BlueprintData> getSavannaDecorBlueprint(int decorType, StartVN startVN, int coordBaseMode, Random random)
	{
		ArrayList<BlueprintData> blueprint = new ArrayList(); // The blueprint to export
		
		
		// Generate per-material blocks
		
		Object[] blockObject;
    	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, startVN.materialType, startVN.biome); Block biomeFenceBlock = (Block)blockObject[0];
    	
        switch (decorType)
        {
    	case 0: // Torch on fence
    		
    		BlueprintData.addPlaceBlock(blueprint, 0, 0, 0, biomeFenceBlock);
    		BlueprintData.addPlaceBlock(blueprint, 0, 1, 0, Blocks.torch, 0);
    		
    		break;
        }
        
        // Return the decor blueprint
        return blueprint;
	}
}
