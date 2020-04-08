package astrotibs.villagenames.village.biomestructures;

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
import net.minecraft.init.Items;
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
		public void buildComponent(StructureComponent start, List components, Random random)
		{
			LogHelper.info("coordBaseMode: " + this.coordBaseMode);
			// Southward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode==1 || this.coordBaseMode==2 ? 5 : 4), this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			if (this.coordBaseMode%2!=0) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode==1 ? 4 : 5), 1, this.getComponentType());}
			// Northward
			if (this.coordBaseMode%2!=1) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode==0 ? 5 : this.coordBaseMode==2 ? 4 : 0), this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());}
			// Eastward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode==1 || this.coordBaseMode==2 ? 5 : 4), 3, this.getComponentType());
		}
		
		/*
		 * Construct the structure
		 */
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, this); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, this); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_slab, 0, this); Block biomeWoodSlabBlock = (Block)blockObject[0]; int biomeWoodSlabMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.oak_stairs, 0, this); Block biomeWoodenStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, this); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.standing_sign, 0, this); Block biomeStandingSignBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, this); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	
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
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, random,
        			this.getXWithOffset(6, 7),
        			this.getYWithOffset(1),
        			this.getZWithOffset(6, 7));
        	int townColor = villageNBTtag.getInteger("townColor");
        	int townColor2 = villageNBTtag.getInteger("townColor2");
        	
        	// Top layer is grass
        	this.fillWithMetadataBlocks(world, structureBB, 0, 0, 0, 13, 0, 11, biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);
        	
        	// Clear above
        	for (int u=0; u<=13; u++) {for (int w=0; w<=11; w++) {
        			this.func_151554_b(world, biomeGrassBlock, biomeGrassMeta, u, -1, w, structureBB); // Foundation
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
        	this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 2, 2, 10, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, 8, 1, 0, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 8, 2, 0, structureBB);
        	
        	
        	
			// Banners on the market stalls
    		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
    		if (testForBanner!=null)
			{
    			for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color
    				{0, 4, 6, 1, townColor},
    				{0, 4, 5, 1, townColor2},
    				{10, 4, 0, 0, townColor},
    				{11, 4, 0, 0, townColor2},
    				{11, 4, 11, 2, townColor},
    				{10, 4, 11, 2, townColor2},
    			})
    			{
        			int bannerXBB = uvwoc[0];
        			int bannerYBB = uvwoc[1];
        			int bannerZBB = uvwoc[2];
        			
        			int bannerX = this.getXWithOffset(bannerXBB, bannerZBB);
        			int bannerY = this.getYWithOffset(bannerYBB);
                    int bannerZ = this.getZWithOffset(bannerXBB, bannerZBB);
                    
                    int bannerFacing = uvwoc[3]; // 0=backward-facing (toward you); 1=rightward-facing; 2=forward-facing; 3=leftward-facing;  
                    
                	// Set the banner and its orientation
    				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);

    				switch (bannerFacing)
    				{
    				case 0: // Facing you
    					world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, new int[]{2,5,3,4}[this.coordBaseMode], 2); break;
    				case 1: // Facing left
    					world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, new int[]{4,2,4,2}[this.coordBaseMode], 2); break;
    				case 2: // Facing away
    					world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, new int[]{3,4,2,5}[this.coordBaseMode], 2); break;
    				case 3: // Facing right
    					world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, new int[]{5,3,5,3}[this.coordBaseMode], 2); break;
    				}
    				
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
    		TileEntitySign signContents2 = new TileEntitySign();
    		for (int i=0; i<4; i++) {signContents2.signText[i] = signContents.signText[i];}

    		int signFacing = 3; // 0=forward-facing; 1=leftward-facing; 2=backward-facing (toward you); 3=rightward-facing,  
    		
			world.setBlock(signX, signY, signZ, biomeStandingSignBlock, ((signFacing + this.coordBaseMode + (this.coordBaseMode >=2 ? 2 : 0))*4)%16, 2); // 2 is "send change to clients without block update notification"
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
                    int bannerFacing = 1; // 0=backward-facing (toward you); 1=rightward-facing; 2=forward-facing; 3=leftward-facing;  
                    
                    // Place a cobblestone foundation // TODO - fill in spaces below with dat cob
                    this.fillWithMetadataBlocks(world, structureBB, bannerXBB, bannerYBB-2, bannerZBB, bannerXBB, bannerYBB-1, bannerZBB, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
                    // Clear space upward
                    this.clearCurrentPositionBlocksUpwards(world, bannerXBB, bannerYBB, bannerZBB, structureBB);
                    
                	// Set the banner and its orientation
    				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
    				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, ((bannerFacing + this.coordBaseMode + (this.coordBaseMode <=1 ? 2: 0))*4)%16, 2);
    				
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
        			else {entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], -random.nextInt(24001));}
        			
        			int villagerY = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(ia[0], ia[2]), this.getZWithOffset(ia[0], ia[2]));
        			
        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)this.getYWithOffset(ia[1]) + 0.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
                    		random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(entityvillager);
        		}
            }
            
            return true;
        }
    }
}
