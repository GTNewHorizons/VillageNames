package astrotibs.villagenames.village.biomestructures;

import java.util.List;
import java.util.Random;

import astrotibs.villagenames.banner.TileEntityBanner;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.utility.BlockPos;
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

public class DesertStructures
{
	// -------------------- //
    // --- Start Pieces --- //
	// -------------------- //
	
	// --- Fountain with building --- //
	
	public static class DesertMeetingPoint1 extends StartVN
    {
    	public DesertMeetingPoint1() {}
    	
    	public DesertMeetingPoint1(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, int terrainType)
    	{
    		super(chunkManager, componentType, random, posX, posZ, components, terrainType);

    		int width = 9;
    		int depth = 8;
    		int height = 5;
    		
		    // Establish orientation
            this.coordBaseMode = random.nextInt(4);
            switch (this.coordBaseMode)
            {
	            case 0: // North
	            case 2: // South
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + width, 64+height, posZ + depth);
                    break;
                default: // 1: East; 3: West
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
			if (this.coordBaseMode%2!=0) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 3, 3, this.getComponentType());}
			// Southward
			if (this.coordBaseMode%2==0) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 3, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());}
			// Westward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 3, 1, this.getComponentType());
			
			// Attach a non-road structure
			int strucX=0; int strucZ=0; int coordBaseMode=0;
			
			if (this.coordBaseMode%2==0)
			{
				strucX=this.boundingBox.maxX + 1; strucZ=this.boundingBox.minZ + random.nextInt(3)+1; coordBaseMode=3;
			}
			else 
			{
				strucX=this.boundingBox.minX + random.nextInt(3)+1; strucZ=this.boundingBox.maxZ + 1; coordBaseMode=0;
			}
			
			StructureVillageVN.getNextVillageStructureComponent((StructureVillagePieces.Start)start, components, random, strucX, this.boundingBox.minY, strucZ, coordBaseMode, this.getComponentType());
		}
    	
		/*
		 * Construct the structure
		 */
    	@Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.standing_sign, 0, this.materialType, this.biome); Block biomeStandingSignBlock = (Block)blockObject[0];
        	
        	if (this.field_143015_k < 0)
            {
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				new StructureBoundingBox(
        						this.boundingBox.minX+1, this.boundingBox.minZ+1,
        						this.boundingBox.maxX-1, this.boundingBox.maxZ-1), // Set the bounding box version as this bounding box but with Y going from 0 to 512
        				true);
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.minY -1, 0);
            }
            
        	// Generate or otherwise obtain village name and banner and colors
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world,
        			this.getXWithOffset(6, 4),
        			this.getYWithOffset(2),
        			this.getZWithOffset(6, 4));
        	int townColor = villageNBTtag.getInteger("townColor");
        	int townColor2 = villageNBTtag.getInteger("townColor2");
        	
        	// Set sandstone ground and clear area above
        	this.fillWithBlocks(world, structureBB, 3, 0, 0, 9, 0, 8, Blocks.sandstone, Blocks.sandstone, false);
        	for (int x = 3; x <= 9; ++x) {for (int z = 0; z <= 8; ++z)
        	{
        		this.func_151554_b(world, Blocks.sandstone, 0, x, -1, z, structureBB); // Foundation
        		this.clearCurrentPositionBlocksUpwards(world, x, 1, z, structureBB);
        	}}
        	this.fillWithBlocks(world, structureBB, 1, 0, 1, 2, 0, 7, Blocks.sandstone, Blocks.sandstone, false);
        	for (int x = 1; x <= 2; ++x) {for (int z = 1; z <= 7; ++z)
        	{
        		this.func_151554_b(world, Blocks.sandstone, 0, x, -1, z, structureBB); // Foundation
        		this.clearCurrentPositionBlocksUpwards(world, x, 1, z, structureBB);
        	}}
        	this.fillWithBlocks(world, structureBB, 0, 0, 3, 0, 0, 5, Blocks.sandstone, Blocks.sandstone, false);
        	for (int x = 0; x <= 0; ++x) {for (int z = 3; z <= 5; ++z)
        	{
        		this.func_151554_b(world, Blocks.sandstone, 0, x, -1, z, structureBB); // Foundation
        		this.clearCurrentPositionBlocksUpwards(world, x, 1, z, structureBB);
        	}}
        	
        	// Set well rim
        	if (GeneralConfig.decorateVillageCenter)
        	{
        		Object[] tryConcrete = ModObjects.chooseModConcrete(townColor);
            	Block concreteBlock = Blocks.stained_hardened_clay; int concreteMeta = townColor;
            	if (tryConcrete != null) {concreteBlock = (Block) tryConcrete[0]; concreteMeta = (Integer) tryConcrete[1];}
            	
            	this.fillWithMetadataBlocks(world, structureBB, 2, 1, 2, 6, 1, 6, concreteBlock, concreteMeta, concreteBlock, concreteMeta, false);
        	}
        	else
        	{
        		this.fillWithMetadataBlocks(world, structureBB, 2, 1, 2, 6, 1, 6, Blocks.sandstone, 2, Blocks.sandstone, 2, false);
        	}
        	// Air in the corners
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 2, 1, 2, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 2, 1, 6, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 6, 1, 6, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 6, 1, 2, structureBB);
            
            // Sand underneath the rim
            this.fillWithBlocks(world, structureBB, 3, 0, 2, 5, 0, 2, Blocks.sand, Blocks.sand, false);
            this.fillWithBlocks(world, structureBB, 3, 0, 6, 5, 0, 6, Blocks.sand, Blocks.sand, false);
            this.fillWithBlocks(world, structureBB, 2, 0, 3, 2, 0, 5, Blocks.sand, Blocks.sand, false);
            this.fillWithBlocks(world, structureBB, 6, 0, 3, 6, 0, 5, Blocks.sand, Blocks.sand, false);
            
            // Water in the fountain
            this.fillWithBlocks(world, structureBB, 3, 1, 3, 5, 1, 5, Blocks.flowing_water, Blocks.flowing_water, false);
            
            // Spout
            this.fillWithMetadataBlocks(world, structureBB, 4, 1, 4, 4, 3, 4, Blocks.sandstone, 2, Blocks.sandstone, 2, false);
            if (GeneralConfig.decorateVillageCenter)
        	{
        		/*Object[] tryConcrete = ModObjects.chooseModConcrete(townColor2);
            	Block concreteBlock = Blocks.stained_hardened_clay; int concreteMeta = townColor2;
            	if (tryConcrete != null) {concreteBlock = (Block) tryConcrete[0]; concreteMeta = (Integer) tryConcrete[1];}
            	
            	this.placeBlockAtCurrentPosition(world, concreteBlock, concreteMeta, 4, 4, 4, structureBB);*/
            	this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, townColor2, 4, 4, 4, structureBB);
        	}
        	else
        	{
        		this.placeBlockAtCurrentPosition(world, Blocks.hardened_clay, 0, 4, 4, 4, structureBB);
        	}
            // Just the tip
            for (int[] uvwm : new int[][]{
            	{4, 5, 4, 0},
            })
            {
            	world.setBlock(this.getXWithOffset(uvwm[0], uvwm[2]), this.getYWithOffset(uvwm[1]), this.getZWithOffset(uvwm[0], uvwm[2]), Blocks.torch, uvwm[3], 2);
            }
            
            // Cactus
            this.placeBlockAtCurrentPosition(world, Blocks.flower_pot, 9, 5, 2, 2, structureBB); // 9 is cactus
            
            
        	// Sign
            int signXBB = 6;
			int signYBB = 2;
			int signZBB = 4;
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
        		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
        		if (testForBanner!=null)
    			{
        			int bannerXBB = 7;
        			int bannerZBB = 1;
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
                    
                    // Place a foundation
                    this.fillWithMetadataBlocks(world, structureBB, bannerXBB, bannerYBB-2, bannerZBB, bannerXBB, bannerYBB-1, bannerZBB, Blocks.sandstone, 0, Blocks.sandstone, 0, false);
                    this.func_151554_b(world, Blocks.sandstone, 0, bannerXBB, bannerYBB-3, bannerZBB, structureBB);
                    // Clear space upward
                    this.clearCurrentPositionBlocksUpwards(world, bannerXBB, bannerYBB, bannerZBB, structureBB);
                    
                	// Set the banner and its orientation
    				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
    				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(8, this.coordBaseMode, false), 2);
    				
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
        			{1, 1, 1, -1, 0},
        			{5, 1, 0, -1, 0},
        			{1, 1, 7, -1, 0},
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
	
	
	// --- Desert Well --- //
	
	public static class DesertMeetingPoint2 extends StartVN
    {
    	public DesertMeetingPoint2() {}
    	
    	public DesertMeetingPoint2(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, int terrainType)
    	{
    		super(chunkManager, componentType, random, posX, posZ, components, terrainType);

    		int width = 11;
    		int depth = 11;
    		int height = 5;
    		
		    // Establish orientation
            this.coordBaseMode = random.nextInt(4);
            switch (this.coordBaseMode)
            {
	            case 0: // North
	            case 2: // South
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + width, 64+height, posZ + depth);
                    break;
                default: // 1: East; 3: West
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
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode>=2 ? 5 : 4), this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode>=2 ? 5 : 4), 3, this.getComponentType());
			// Southward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode<=1 ? 5 : 4), this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode<=1 ? 5 : 4), 1, this.getComponentType());
		}
    	
		/*
		 * Construct the structure
		 */
    	@Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.standing_sign, 0, this.materialType, this.biome); Block biomeStandingSignBlock = (Block)blockObject[0];
        	
        	if (this.field_143015_k < 0)
            {
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				new StructureBoundingBox(
        						this.boundingBox.minX+1, this.boundingBox.minZ+1,
        						this.boundingBox.maxX-1, this.boundingBox.maxZ-1), // Set the bounding box version as this bounding box but with Y going from 0 to 512
        				true);
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.minY -1, 0);
            }
            
        	// Generate or otherwise obtain village name and banner and colors
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world,
        			this.getXWithOffset(8, 1),
        			this.getYWithOffset(1),
        			this.getZWithOffset(8, 1));
        	int townColor = villageNBTtag.getInteger("townColor");
        	int townColor2 = villageNBTtag.getInteger("townColor2");
        	
        	// Set sandstone ground and clear area above
        	this.fillWithBlocks(world, structureBB, 1, 0, 1, 10, 0, 10, Blocks.sandstone, Blocks.sandstone, false);
        	
        	// Fill foundation
        	for (int x = 1; x <= 10; ++x)
            {
	        	for (int z = 1; z <= 10; ++z)
	            {
	                this.func_151554_b(world, Blocks.sandstone, 0, x, -1, z, structureBB); // Foundation
	                this.clearCurrentPositionBlocksUpwards(world, x, 1, z, structureBB);
                }
            }
        	
        	// Path hitches at the ends
        	this.fillWithBlocks(world, structureBB, 0, 0, 5, 0, 0, 7, Blocks.sandstone, Blocks.sandstone, false);
        	for (int w = 5; w <= 7; ++w)
        	{
        		this.func_151554_b(world, Blocks.sandstone, 0, 0, -1, w, structureBB); // Foundation
        		this.clearCurrentPositionBlocksUpwards(world, 0, 1, w, structureBB);
        	}
        	this.fillWithBlocks(world, structureBB, 11, 0, 4, 11, 0, 6, Blocks.sandstone, Blocks.sandstone, false);
        	for (int w = 4; w <= 6; ++w)
        	{
        		this.func_151554_b(world, Blocks.sandstone, 0, 11, -1, w, structureBB); // Foundation
        		this.clearCurrentPositionBlocksUpwards(world, 11, 1, w, structureBB);
        	}
        	this.fillWithBlocks(world, structureBB, 4, 0, 0, 6, 0, 0, Blocks.sandstone, Blocks.sandstone, false);
        	for (int w = 4; w <= 6; ++w)
        	{
        		this.func_151554_b(world, Blocks.sandstone, 0, w, -1, 0, structureBB); // Foundation
        		this.clearCurrentPositionBlocksUpwards(world, w, 1, 0, structureBB);
        	}
        	this.fillWithBlocks(world, structureBB, 5, 0, 11, 7, 0, 11, Blocks.sandstone, Blocks.sandstone, false);
        	for (int w = 5; w <= 7; ++w)
        	{
        		this.func_151554_b(world, Blocks.sandstone, 0, w, -1, 11, structureBB); // Foundation
        		this.clearCurrentPositionBlocksUpwards(world, w, 1, 11, structureBB);
        	}
        	
        	// Set sand underneath the fountain
        	this.fillWithBlocks(world, structureBB, 3, 0, 3, 8, 0, 8, Blocks.sandstone, Blocks.sandstone, false);
        	
        	// Set well rim
        	if (GeneralConfig.decorateVillageCenter)
        	{
        		Object[] tryConcrete = ModObjects.chooseModConcrete(townColor);
            	Block concreteBlock = Blocks.stained_hardened_clay; int concreteMeta = townColor;
            	if (tryConcrete != null) {concreteBlock = (Block) tryConcrete[0]; concreteMeta = (Integer) tryConcrete[1];}
            	
            	this.fillWithMetadataBlocks(world, structureBB, 3, 1, 3, 8, 1, 8, concreteBlock, concreteMeta, concreteBlock, concreteMeta, false);
        	}
        	else
        	{
        		this.fillWithMetadataBlocks(world, structureBB, 3, 1, 3, 8, 1, 8, Blocks.sandstone, 0, Blocks.sandstone, 0, false);
        	}
        	
        	// Water in the fountain
            this.fillWithBlocks(world, structureBB, 4, 1, 4, 7, 1, 7, Blocks.flowing_water, Blocks.flowing_water, false);
            
            // Sandstone slab roof
            this.fillWithMetadataBlocks(world, structureBB, 4, 4, 4, 7, 4, 7, Blocks.stone_slab, 1, Blocks.stone_slab, 1, false);
            
        	// Columns
            this.fillWithMetadataBlocks(world, structureBB, 4, 1, 4, 4, 4, 4, Blocks.sandstone, 2, Blocks.sandstone, 2, false);
            this.fillWithMetadataBlocks(world, structureBB, 4, 1, 7, 4, 4, 7, Blocks.sandstone, 2, Blocks.sandstone, 2, false);
            this.fillWithMetadataBlocks(world, structureBB, 7, 1, 7, 7, 4, 7, Blocks.sandstone, 2, Blocks.sandstone, 2, false);
            this.fillWithMetadataBlocks(world, structureBB, 7, 1, 4, 7, 4, 4, Blocks.sandstone, 2, Blocks.sandstone, 2, false);
            
            // Torches
            for (int[] uvwm : new int[][]{
            	{4, 5, 4, 0},
            	{4, 5, 7, 0},
            	{7, 5, 7, 0},
            	{7, 5, 4, 0},
            })
            {
            	world.setBlock(this.getXWithOffset(uvwm[0], uvwm[2]), this.getYWithOffset(uvwm[1]), this.getZWithOffset(uvwm[0], uvwm[2]), Blocks.torch, uvwm[3], 2);
            }
            
            
            // Roof of the well
            if (GeneralConfig.decorateVillageCenter)
            {
            	int metaBase = ((int)world.getSeed()%4+this.coordBaseMode)%4; // Procedural based on world seed and base mode
            	
            	BlockPos uvw = new BlockPos(5, 4, 5); // Starting position of the block cluster. Use lowest X, Z.
            	int metaCycle = (metaBase+Math.abs(this.getXWithOffset(uvw.getX(), uvw.getZ())%2 - (this.getZWithOffset(uvw.getX(), uvw.getZ())%2)*3) + uvw.getY())%4; // Procedural based on block X, Y, Z 
            	Object[] tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(townColor2, metaCycle);
            	
            	if (tryGlazedTerracotta != null)
            	{
            		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvw.getX(), uvw.getY(), uvw.getZ(), structureBB);
            		
            		uvw = uvw.south(); metaCycle = (metaCycle+1)%4;
            		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(townColor2, metaCycle);
            		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvw.getX(), uvw.getY(), uvw.getZ(), structureBB);
            		
            		uvw = uvw.west(); metaCycle = (metaCycle+1)%4;
            		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(townColor2, metaCycle);
            		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvw.getX(), uvw.getY(), uvw.getZ(), structureBB);
            		
            		uvw = uvw.north(); metaCycle = (metaCycle+1)%4;
            		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(townColor2, metaCycle);
            		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvw.getX(), uvw.getY(), uvw.getZ(), structureBB);
            	}
            	else
            	{
            		this.fillWithMetadataBlocks(world, structureBB, 5, 4, 5, 6, 4, 6, Blocks.stained_hardened_clay, townColor2, Blocks.stained_hardened_clay, townColor2, false);
            	}
            }
            else
            {
            	this.fillWithBlocks(world, structureBB, 5, 4, 5, 6, 4, 6, Blocks.sandstone, Blocks.sandstone, false);
            }
            
            
        	// Sign
            int signXBB = 8;
			int signYBB = 1;
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
    		
    		
			// Banner
    		if (GeneralConfig.decorateVillageCenter)
    		{
        		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
        		if (testForBanner!=null)
    			{
        			int bannerXBB = 10;
        			int bannerZBB = 10;
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
                    
                    // Place a foundation
                    this.fillWithMetadataBlocks(world, structureBB, bannerXBB, bannerYBB-2, bannerZBB, bannerXBB, bannerYBB-1, bannerZBB, Blocks.sandstone, 0, Blocks.sandstone, 0, false);
                    this.func_151554_b(world, Blocks.sandstone, 0, bannerXBB, bannerYBB-3, bannerZBB, structureBB);
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
        	
    		
    		// Villagers
            if (!this.villagersGenerated)
            {
            	this.villagersGenerated=true;
            	
        		for (int[] ia : new int[][]{
        			{10, 1, 8, -1, 0},
        			{1, 1, 10, -1, 0},
        			{7, 1, 10, -1, 0},
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
	
	
	// --- Desert Market --- //

	public static class DesertMeetingPoint3 extends StartVN
    {
    	public DesertMeetingPoint3() {}
    	
    	public DesertMeetingPoint3(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, int terrainType)
    	{
    		super(chunkManager, componentType, random, posX, posZ, components, terrainType);

    		int width = 14;
    		int depth = 14;
    		int height = 5;
    		
		    // Establish orientation
            this.coordBaseMode = random.nextInt(4);
            switch (this.coordBaseMode)
            {
	            case 0: // North
	            case 2: // South
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + width, 64+height, posZ + depth);
                    break;
                default: // 1: East; 3: West
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
    		if (this.coordBaseMode!=0) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + new int[]{1,5,1,7}[this.coordBaseMode], this.boundingBox.maxY - 5, this.boundingBox.minZ - 1, 2, this.getComponentType());}
        	// Eastward
        	if (this.coordBaseMode!=1) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.maxY - 5, this.boundingBox.minZ + new int[]{6,4,6,1}[this.coordBaseMode], 3, this.getComponentType());}
			// Southward
        	if (this.coordBaseMode!=2) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + new int[]{1,6,4,6}[this.coordBaseMode], this.boundingBox.maxY - 5, this.boundingBox.maxZ + 1, 0, this.getComponentType());}
			// Westward
			if (this.coordBaseMode!=3) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.maxY - 5, this.boundingBox.minZ + new int[]{7,1,5,4}[this.coordBaseMode], 1, this.getComponentType());}
		}
    	
		/*
		 * Construct the structure
		 */
    	@Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.standing_sign, 0, this.materialType, this.biome); Block biomeStandingSignBlock = (Block)blockObject[0];
        	Block biomeSandstoneWall = Block.getBlockFromName(ModObjects.sandstoneWallUTD); int biomeSandstoneMeta = 0;
        	if (biomeSandstoneWall==null) {blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, this.materialType, this.biome); biomeSandstoneWall = (Block)blockObject[0]; biomeSandstoneMeta = (Integer)blockObject[1];}
        	
        	if (this.field_143015_k < 0)
            {
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				new StructureBoundingBox(
        						this.boundingBox.minX+2, this.boundingBox.minZ+2,
        						this.boundingBox.maxX-2, this.boundingBox.maxZ-2), // Set the bounding box version as this bounding box but with Y going from 0 to 512
        				true);
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.minY -1, 0);
            }
            
        	// Generate or otherwise obtain village name and banner and colors
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world,
        			this.getXWithOffset(8, 2),
        			this.getYWithOffset(3),
        			this.getZWithOffset(8, 2));
        	int townColor = villageNBTtag.getInteger("townColor");
        	int townColor2 = villageNBTtag.getInteger("townColor2");
        	
        	
        	// Set ground and clear area above
        	int fillXmin; int fillZmin; int fillXmax; int fillZmax; int clearToHeight = 5;
        	
        	for (Object[] o : new Object[][]{
        		// minX, maxX, minZ, maxZ, groundY, structureHeight, surfaceBlock, surfaceMeta, subsurfaceBlock, subsurfaceMeta 
        		{0, 0, 7, 12, 0, 5, Blocks.sand, 0, Blocks.sandstone, 0},
        		{1, 2, 4, 12, 0, 5, Blocks.sand, 0, Blocks.sandstone, 0},
        		{1, 2, 14, 14, 0, 5, Blocks.sand, 0, Blocks.sandstone, 0},
        		{0, 2, 13, 13, -1, 5, Blocks.sandstone, 0, Blocks.sandstone, 0},
        		{2, 2, 3, 3, -1, 5, Blocks.sandstone, 0, Blocks.sandstone, 0},
        		{3, 3, 14, 14, -1, 5, Blocks.sandstone, 0, Blocks.sandstone, 0},
        		{3, 3, 1, 13, 0, 5, Blocks.sand, 0, Blocks.sandstone, 0},
        		{3, 3, 0, 0, -1, 5, Blocks.sandstone, 0, Blocks.sandstone, 0},
        		{4, 4, 2, 14, 0, 5, Blocks.sand, 0, Blocks.sandstone, 0},
        		{4, 4, 0, 1, -1, 5, Blocks.sandstone, 0, Blocks.sandstone, 0},
        		{5, 9, 0, 14, 0, 5, Blocks.sand, 0, Blocks.sandstone, 0},
        		{10, 11, 0, 13, 0, 5, Blocks.sand, 0, Blocks.sandstone, 0},
        		{12, 12, 1, 13, 0, 5, Blocks.sand, 0, Blocks.sandstone, 0},
        		{12, 12, 0, 0, -1, 5, Blocks.sandstone, 0, Blocks.sandstone, 0},
        		{13, 13, 0, 12, 0, 5, Blocks.sand, 0, Blocks.sandstone, 0},
        		{14, 14, 3, 11, 0, 5, Blocks.sand, 0, Blocks.sandstone, 0},
        	})
        	{
        		this.fillWithMetadataBlocks(world, structureBB, (Integer)o[0], (Integer)o[4], (Integer)o[2], (Integer)o[1], (Integer)o[4], (Integer)o[3], (Block)o[6], (Integer)o[7], (Block)o[6], (Integer)o[7], false);
            	for (int x = (Integer)o[0]; x <= (Integer)o[1]; ++x) {for (int z = (Integer)o[2]; z <= (Integer)o[3]; ++z)
            	{
            		this.func_151554_b(world, (Block)o[8], (Integer)o[9], x, (Integer)o[4]-1, z, structureBB); // Foundation
            		this.clearCurrentPositionBlocksUpwards(world, (Integer)o[0], Math.max((Integer)o[4], 0)+1, (Integer)o[2], structureBB);
            	}}
        	}
        	// Set sandstone in certain places
        	this.fillWithBlocks(world, structureBB, 7, 0, 0, 7, 0, 4, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithBlocks(world, structureBB, 10, 0, 0, 10, 0, 3, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithBlocks(world, structureBB, 8, 0, 1, 9, 0, 1, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithBlocks(world, structureBB, 8, 0, 3, 9, 0, 3, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithBlocks(world, structureBB, 8, 0, 4, 13, 0, 4, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithBlocks(world, structureBB, 8, 0, 9, 13, 0, 9, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithBlocks(world, structureBB, 8, 0, 5, 8, 0, 8, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithBlocks(world, structureBB, 13, 0, 5, 13, 0, 8, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithBlocks(world, structureBB, 10, 0, 6, 11, 0, 7, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithBlocks(world, structureBB, 14, 0, 6, 14, 0, 8, Blocks.sandstone, Blocks.sandstone, false);
        	
        	
        	// Fountain
        	
        	// Rim
        	if (GeneralConfig.decorateVillageCenter)
        	{
        		Object[] tryConcrete = ModObjects.chooseModConcrete(townColor2);
            	Block concreteBlock = Blocks.stained_hardened_clay; int concreteMeta = townColor2;
            	if (tryConcrete != null) {concreteBlock = (Block) tryConcrete[0]; concreteMeta = (Integer) tryConcrete[1];}
            	
            	this.fillWithMetadataBlocks(world, structureBB, 9, 1, 5, 12, 1, 8, concreteBlock, concreteMeta, concreteBlock, concreteMeta, false);
            	
            	/*
            	tryConcrete = ModObjects.chooseModConcrete(townColor2);
            	concreteBlock = Blocks.stained_hardened_clay; concreteMeta = townColor2;
            	if (tryConcrete != null) {concreteBlock = (Block) tryConcrete[0]; concreteMeta = (Integer) tryConcrete[1];}
            	
            	this.fillWithMetadataBlocks(world, structureBB, 8, 0, 4, 8, 0, 9, concreteBlock, concreteMeta, concreteBlock, concreteMeta, false);
            	this.fillWithMetadataBlocks(world, structureBB, 13, 0, 4, 13, 0, 9, concreteBlock, concreteMeta, concreteBlock, concreteMeta, false);
            	this.fillWithMetadataBlocks(world, structureBB, 9, 0, 4, 12, 0, 4, concreteBlock, concreteMeta, concreteBlock, concreteMeta, false);
            	this.fillWithMetadataBlocks(world, structureBB, 9, 0, 9, 12, 0, 9, concreteBlock, concreteMeta, concreteBlock, concreteMeta, false);
            	*/
            	//this.fillWithMetadataBlocks(world, structureBB, 9, 1, 7, 10, 1, 8, concreteBlock, concreteMeta, concreteBlock, concreteMeta, false);
        	}
        	else
        	{
        		this.fillWithMetadataBlocks(world, structureBB, 9, 1, 5, 12, 1, 8, Blocks.sandstone, 0, Blocks.sandstone, 0, false);
        	}
        	
        	
        	// Corner posts
    		this.placeBlockAtCurrentPosition(world, Blocks.sandstone, 2, 9, 1, 5, structureBB);
    		this.placeBlockAtCurrentPosition(world, Blocks.sandstone, 2, 9, 1, 8, structureBB);
    		this.placeBlockAtCurrentPosition(world, Blocks.sandstone, 2, 12, 1, 8, structureBB);
    		this.placeBlockAtCurrentPosition(world, Blocks.sandstone, 2, 12, 1, 5, structureBB);
        	// Top the corners
    		this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 1, 9, 2, 5, structureBB);
    		this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 1, 9, 2, 8, structureBB);
    		this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 1, 12, 2, 8, structureBB);
    		this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 1, 12, 2, 5, structureBB);
        	
    		// Fill with water
    		this.fillWithBlocks(world, structureBB, 10, 1, 6, 11, 1, 7, Blocks.flowing_water, Blocks.flowing_water, false);
    		
    		
    		// Market stalls
    		
    		// Frames
    		this.fillWithMetadataBlocks(world, structureBB, 7, 1, 0, 7, 3, 0, biomeSandstoneWall, biomeSandstoneMeta, biomeSandstoneWall, biomeSandstoneMeta, false);
    		this.fillWithMetadataBlocks(world, structureBB, 10, 1, 0, 10, 3, 0, biomeSandstoneWall, biomeSandstoneMeta, biomeSandstoneWall, biomeSandstoneMeta, false);
    		this.fillWithMetadataBlocks(world, structureBB, 10, 1, 2, 10, 3, 2, biomeSandstoneWall, biomeSandstoneMeta, biomeSandstoneWall, biomeSandstoneMeta, false);
    		this.fillWithMetadataBlocks(world, structureBB, 7, 1, 2, 7, 3, 2, biomeSandstoneWall, biomeSandstoneMeta, biomeSandstoneWall, biomeSandstoneMeta, false);
    		this.fillWithMetadataBlocks(world, structureBB, 7, 4, 0, 10, 4, 2, Blocks.stone_slab, 1, Blocks.stone_slab, 1, false);
    		this.fillWithAir(world, structureBB, 8, 4, 1, 9, 4, 1);
    		
    		this.fillWithMetadataBlocks(world, structureBB, 1, 1, 5, 1, 4, 5, biomeSandstoneWall, biomeSandstoneMeta, biomeSandstoneWall, biomeSandstoneMeta, false);
    		this.fillWithMetadataBlocks(world, structureBB, 5, 1, 5, 5, 4, 5, biomeSandstoneWall, biomeSandstoneMeta, biomeSandstoneWall, biomeSandstoneMeta, false);
    		this.fillWithMetadataBlocks(world, structureBB, 5, 1, 7, 5, 4, 7, biomeSandstoneWall, biomeSandstoneMeta, biomeSandstoneWall, biomeSandstoneMeta, false);
    		this.fillWithMetadataBlocks(world, structureBB, 1, 1, 7, 1, 4, 7, biomeSandstoneWall, biomeSandstoneMeta, biomeSandstoneWall, biomeSandstoneMeta, false);
    		this.fillWithMetadataBlocks(world, structureBB, 1, 5, 5, 5, 5, 7, Blocks.stone_slab, 1, Blocks.stone_slab, 1, false);
    		this.fillWithAir(world, structureBB, 2, 5, 6, 4, 5, 6);
    		
    		this.fillWithMetadataBlocks(world, structureBB, 4, 1, 11, 4, 3, 11, biomeSandstoneWall, biomeSandstoneMeta, biomeSandstoneWall, biomeSandstoneMeta, false);
    		this.fillWithMetadataBlocks(world, structureBB, 7, 1, 11, 7, 3, 11, biomeSandstoneWall, biomeSandstoneMeta, biomeSandstoneWall, biomeSandstoneMeta, false);
    		this.fillWithMetadataBlocks(world, structureBB, 7, 1, 14, 7, 3, 14, biomeSandstoneWall, biomeSandstoneMeta, biomeSandstoneWall, biomeSandstoneMeta, false);
    		this.fillWithMetadataBlocks(world, structureBB, 4, 1, 14, 4, 3, 14, biomeSandstoneWall, biomeSandstoneMeta, biomeSandstoneWall, biomeSandstoneMeta, false);
    		this.fillWithMetadataBlocks(world, structureBB, 4, 4, 11, 7, 4, 14, Blocks.stone_slab, 1, Blocks.stone_slab, 1, false);
    		this.fillWithAir(world, structureBB, 5, 4, 12, 6, 4, 13);
    		
    		// Stall contents
    		
    		// Glazed terracotta
    		Object[] tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(GeneralConfig.decorateVillageCenter ? townColor : 0, (0 + this.coordBaseMode + (this.coordBaseMode < 2 ? 1 : 0))%4);
        	
        	if (tryGlazedTerracotta != null)
        	{
        		// Square under square awning
        		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], 5, 1, 13, structureBB);
        		
        		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(GeneralConfig.decorateVillageCenter ? townColor : 0, (1 + this.coordBaseMode + (this.coordBaseMode < 2 ? 3 : 0))%4);
        		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], 6, 1, 13, structureBB);
        		
        		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(GeneralConfig.decorateVillageCenter ? townColor : 0, (2 + this.coordBaseMode + (this.coordBaseMode < 2 ? 1 : 0))%4);
        		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], 6, 1, 12, structureBB);
        		
        		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(GeneralConfig.decorateVillageCenter ? townColor : 0, (3 + this.coordBaseMode + (this.coordBaseMode < 2 ? 3 : 0))%4);
        		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], 5, 1, 12, structureBB);
        		
        		// Halved square under strip awning
        		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(GeneralConfig.decorateVillageCenter ? townColor2 : 0, (0 + this.coordBaseMode + (this.coordBaseMode < 2 ? 1 : 0))%4);
        		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], 8, 1, 2, structureBB);
        		
        		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(GeneralConfig.decorateVillageCenter ? townColor2 : 0, (1 + this.coordBaseMode + (this.coordBaseMode < 2 ? 3 : 0))%4);
        		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], 9, 1, 2, structureBB);
        		
        		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(GeneralConfig.decorateVillageCenter ? townColor2 : 0, (2 + this.coordBaseMode + (this.coordBaseMode < 2 ? 1 : 0))%4);
        		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], 9, 1, 0, structureBB);
        		
        		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(GeneralConfig.decorateVillageCenter ? townColor2 : 0, (3 + this.coordBaseMode + (this.coordBaseMode < 2 ? 3 : 0))%4);
        		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], 8, 1, 0, structureBB);
        	}
        	else
        	{
        		// Square under awning
        		this.fillWithMetadataBlocks(world, structureBB, 5, 1, 12, 6, 1, 13, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor : 0, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor : 0, false);
        		
        		// Halved square under strip awning
        		this.fillWithMetadataBlocks(world, structureBB, 8, 1, 0, 9, 1, 0, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor2 : 0, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor2 : 0, false);
        		this.fillWithMetadataBlocks(world, structureBB, 8, 1, 2, 9, 1, 2, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor2 : 0, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter ? townColor2 : 0, false);
        	}
    		
        	// Cut stone and stairs
        	this.fillWithMetadataBlocks(world, structureBB, 2, 1, 6, 4, 1, 6, Blocks.sandstone, 2, Blocks.sandstone, 2, false);
        	this.placeBlockAtCurrentPosition(world, Blocks.sandstone_stairs, this.getMetadataWithOffset(Blocks.sandstone_stairs, 0), 1, 1, 6, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.sandstone_stairs, this.getMetadataWithOffset(Blocks.sandstone_stairs, 1), 5, 1, 6, structureBB);
        	
        	// Various decorations
        	this.placeBlockAtCurrentPosition(world, Blocks.hay_block, 0, 5, 1, 0, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.hay_block, this.coordBaseMode%2==1 ? 8 : 4, 3, 1, 2, structureBB);
        	
    		// Flower pots
        	// 1: red flower; 2: yellow flower
        	// 3-6: oak, spruce, birch, jungle saplings
        	// 7-8: red/brown mushrooms
        	// 9: cactus; 10: dead bush; 11: fern
        	// 12-13: acacia, dark oak saplings
        	this.placeBlockAtCurrentPosition(world, Blocks.flower_pot, 9, 0, 1, 7, structureBB); // cactus
        	this.placeBlockAtCurrentPosition(world, Blocks.flower_pot, 9, 3, 1, 7, structureBB); // cactus
        	this.placeBlockAtCurrentPosition(world, Blocks.flower_pot, 9, 2, 2, 6, structureBB); // cactus
        	this.placeBlockAtCurrentPosition(world, Blocks.flower_pot, 9, 4, 2, 6, structureBB); // cactus
        	this.placeBlockAtCurrentPosition(world, Blocks.flower_pot, 9, 9, 2, 2, structureBB); // cactus
        	this.placeBlockAtCurrentPosition(world, Blocks.flower_pot, 10, 1, 1, 4, structureBB); // dead bush
        	this.placeBlockAtCurrentPosition(world, Blocks.flower_pot, 10, 2, 1, 5, structureBB); // dead bush
        	this.placeBlockAtCurrentPosition(world, Blocks.flower_pot, 10, 2, 1, 7, structureBB); // dead bush
        	this.placeBlockAtCurrentPosition(world, Blocks.flower_pot, 10, 3, 2, 6, structureBB); // dead bush
        	this.placeBlockAtCurrentPosition(world, Blocks.flower_pot, 10, 4, 1, 5, structureBB); // dead bush
        	
        	// Lantern decor
        	for (int[] uvw : new int[][]{
    			{1, 1, 11},
    			{12, 1, 12},
    			{13, 1, 0},
    			{14, 1, 3},
        	})
        	{
        		// Base
        		this.fillWithMetadataBlocks(world, structureBB, uvw[0], uvw[1], uvw[2], uvw[0], uvw[1]+1, uvw[2], Blocks.sandstone, 2, Blocks.sandstone, 2, false);
        		// Tip
                if (GeneralConfig.decorateVillageCenter) {this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, townColor, uvw[0], uvw[1]+2, uvw[2], structureBB);}
            	else {this.placeBlockAtCurrentPosition(world, Blocks.hardened_clay, 0, uvw[0], uvw[1]+2, uvw[2], structureBB);}
        		// Torch
            	world.setBlock(this.getXWithOffset(uvw[0], uvw[2]), this.getYWithOffset(uvw[1]+3), this.getZWithOffset(uvw[0], uvw[2]), Blocks.torch, 0, 2);
        	}
        	
        	// Sign
            int signXBB = 8;
			int signYBB = 2;
			int signZBB = 2;
            int signX = this.getXWithOffset(signXBB, signZBB);
            int signY = this.getYWithOffset(signYBB);
            int signZ = this.getZWithOffset(signXBB, signZBB);
    		
    		String namePrefix = villageNBTtag.getString("namePrefix");
    		String nameRoot = villageNBTtag.getString("nameRoot");
    		String nameSuffix = villageNBTtag.getString("nameSuffix");
    		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);
    		
			world.setBlock(signX, signY, signZ, biomeStandingSignBlock, StructureVillageVN.getSignRotationMeta(0, this.coordBaseMode, false), 2); // 2 is "send change to clients without block update notification"
    		world.setTileEntity(signX, signY, signZ, signContents);
    		
        	
			// Banner
    		if (GeneralConfig.decorateVillageCenter)
    		{
        		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
        		if (testForBanner!=null)
    			{
        			int bannerXBB = 10;
        			int bannerZBB = 11;
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
                    
                    // Place a foundation
                    this.fillWithMetadataBlocks(world, structureBB, bannerXBB, bannerYBB-2, bannerZBB, bannerXBB, bannerYBB-1, bannerZBB, Blocks.sandstone, 0, Blocks.sandstone, 0, false);
                    this.func_151554_b(world, Blocks.sandstone, 0, bannerXBB, bannerYBB-3, bannerZBB, structureBB);
                    // Clear space upward
                    this.clearCurrentPositionBlocksUpwards(world, bannerXBB, bannerYBB, bannerZBB, structureBB);
                    
                	// Set the banner and its orientation
    				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
    				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(8, this.coordBaseMode, false), 2);
    				
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
        			{6, 1, 5, -1, 0},
        			{8, 1, 10, -1, 0},
        			{11, 1, 10, -1, 0},
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
}
