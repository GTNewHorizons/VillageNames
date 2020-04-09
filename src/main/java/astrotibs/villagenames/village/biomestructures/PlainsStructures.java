package astrotibs.villagenames.village.biomestructures;

import java.util.List;
import java.util.Random;

import astrotibs.villagenames.banner.TileEntityBanner;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.integration.tools.TileEntityWoodSign;
import astrotibs.villagenames.utility.BlockPos;
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

    		int width = 8;
    		int depth = 8;
    		int height = 3;
    		
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

            //StructureVillageVN.establishBiomeBlocks(this, posX, posZ);
    	}

		/*
		 * Add the paths that lead outward from this structure
		 */
		public void buildComponent(StructureComponent start, List components, Random random)
		{
			// Southward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 3, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 3, 1, this.getComponentType());
			// Northward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 3, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 3, 3, this.getComponentType());
		}
    	
		/*
		 * Construct the structure
		 */
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;	
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, this); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.standing_sign, 0, this); Block biomeStandingSignBlock = (Block)blockObject[0];
        	
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
            
        	// Generate or otherwise obtain village name and banner and colors
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, random,
        			this.getXWithOffset(4, 4),
        			this.getYWithOffset(2),
        			this.getZWithOffset(4, 4));
        	int townColor = villageNBTtag.getInteger("townColor");
        	int townColor2 = villageNBTtag.getInteger("townColor2");
        	
        	
        	// Clear out area
        	this.fillWithAir(world, structureBB, 2, 1, 2, 6, 4, 6);
        	
            // Basin bottom
            this.fillWithMetadataBlocks(world, structureBB, 2, -1, 2, 6, 0, 6, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            
            // Torches at the corners
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 2, 1, 2, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 2, 1, 6, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 6, 1, 2, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 6, 1, 6, structureBB);
            
            if (GeneralConfig.decorateVillageCenter)
            {
            	// Basin rim
            	Object[] tryConcrete = ModObjects.chooseModConcrete(townColor);
            	Block concreteBlock = Blocks.stained_hardened_clay; int concreteMeta = townColor;
            	if (tryConcrete != null) {concreteBlock = (Block) tryConcrete[0]; concreteMeta = (Integer) tryConcrete[1];}
            	
            	this.fillWithMetadataBlocks(world, structureBB, 2, 1, 3, 6, 1, 5, concreteBlock, concreteMeta, concreteBlock, concreteMeta, false);
                this.fillWithMetadataBlocks(world, structureBB, 3, 1, 2, 5, 1, 6, concreteBlock, concreteMeta, concreteBlock, concreteMeta, false);
                
                // Under-torch GT
            	int metaBase = ((int)world.getSeed()%4+this.coordBaseMode)%4; // Procedural based on world seed and base mode
            	
            	BlockPos uvw = new BlockPos(2, 0, 2); // Starting position of the block cluster. Use lowest X, Z.
            	int metaCycle = (metaBase+Math.abs(this.getXWithOffset(uvw.getX(), uvw.getZ())%2 - (this.getZWithOffset(uvw.getX(), uvw.getZ())%2)*3) + uvw.getY())%4; // Procedural based on block X, Y, Z 
            	Object[] tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(townColor2, metaCycle);
            	
            	if (tryGlazedTerracotta != null)
            	{
            		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvw.getX(), uvw.getY(), uvw.getZ(), structureBB);
            		
            		uvw = uvw.south(4); metaCycle = (metaCycle+1)%4;
            		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(townColor2, metaCycle);
            		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvw.getX(), uvw.getY(), uvw.getZ(), structureBB);
            		
            		uvw = uvw.west(4); metaCycle = (metaCycle+1)%4;
            		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(townColor2, metaCycle);
            		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvw.getX(), uvw.getY(), uvw.getZ(), structureBB);
            		
            		uvw = uvw.north(4); metaCycle = (metaCycle+1)%4;
            		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(townColor2, metaCycle);
            		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvw.getX(), uvw.getY(), uvw.getZ(), structureBB);
            	}
                else
                {
                    this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, townColor2, 2, 0, 2, structureBB);
                    this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, townColor2, 2, 0, 6, structureBB);
                    this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, townColor2, 6, 0, 2, structureBB);
                    this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, townColor2, 6, 0, 6, structureBB);
                }
            }
            else
            {
            	// Basin rim
                this.fillWithMetadataBlocks(world, structureBB, 2, 1, 3, 6, 1, 5, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
                this.fillWithMetadataBlocks(world, structureBB, 3, 1, 2, 5, 1, 6, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            }
            
            this.fillWithAir(world, structureBB, 3, 1, 3, 5, 1, 5);
            
            // Spout
            if (GeneralConfig.decorateVillageCenter)
            {
            	Object[] tryConcrete = ModObjects.chooseModConcrete(townColor2);
            	Block concreteBlock = Blocks.stained_hardened_clay; int concreteMeta = townColor2;
            	if (tryConcrete != null) {concreteBlock = (Block) tryConcrete[0]; concreteMeta = (Integer) tryConcrete[1];}
            	
            	this.fillWithMetadataBlocks(world, structureBB, 4, 1, 4, 4, 2, 4, concreteBlock, concreteMeta, concreteBlock, concreteMeta, false);
            }
            else
            {
            	this.fillWithMetadataBlocks(world, structureBB, 4, 1, 4, 4, 2, 4, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            }
            this.placeBlockAtCurrentPosition(world, Blocks.flowing_water, 0, 4, 3, 4, structureBB);
            
            // Encircle the fountain with path
            Block pathBlock = ModObjects.chooseModPathBlock();
        	StructureVillagePieces.Start startPiece_reflected = ReflectionHelper.getPrivateValue(StructureVillagePieces.Village.class, this, new String[]{"startPiece"});
        	for (int i = 1; i <= 7; ++i)
            {
                for (int j = 1; j <= 7; ++j)
                {
                    if (j == 1 || j == 7 || i == 1 || i == 7)
                    {
                    	// Gets ground level, so long as it's not leaves or other foliage
                    	int k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(i, j), this.getZWithOffset(i, j)) - 1;
                        if (k > -1)
                        {
                        	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getBoundingBox().minX+i, k, this.getBoundingBox().minZ+j);
                        	this.clearCurrentPositionBlocksUpwards(world, i, k+1-this.boundingBox.minY, j, structureBB);
                       	}
                    }
                }
            }
        	
            // Add path nodules at the end
            for (int i : new int[]{1,2,3,4,5,6,7})
            {
            	for (int j : new int[]{0,8})
            	{
            		int k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(i, j), this.getZWithOffset(i, j)) - 1;
                    if (k > -1)
                    {
                    	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(i, j), k, this.getZWithOffset(i, j));
                    	this.clearCurrentPositionBlocksUpwards(world, i, k+1-this.boundingBox.minY, j, structureBB);
                   	}
                    
                    k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(j, i), this.getZWithOffset(j, i)) - 1;
                    if (k > -1)
                    {
                    	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(j, i), k, this.getZWithOffset(j, i));
                    	this.clearCurrentPositionBlocksUpwards(world, j, k+1-this.boundingBox.minY, i, structureBB);
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
    		
    		String namePrefix = villageNBTtag.getString("namePrefix");
    		String nameRoot = villageNBTtag.getString("nameRoot");
    		String nameSuffix = villageNBTtag.getString("nameSuffix");
    		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);
    		
			world.setBlock(signX, signY, signZ, biomeStandingSignBlock, StructureVillageVN.getSignRotationMeta(8, this.coordBaseMode, false), 2); // 2 is "send change to clients without block update notification"
    		world.setTileEntity(signX, signY, signZ, signContents);
    		
    		
			// Banner    		
    		if (GeneralConfig.decorateVillageCenter)
    		{
        		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
        		if (testForBanner!=null)
    			{
        			int bannerXBB = 8;
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
                    
                    // Place a cobblestone foundation
                    this.fillWithMetadataBlocks(world, structureBB, bannerXBB, bannerYBB-2, bannerZBB, bannerXBB, bannerYBB-1, bannerZBB, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
                    this.func_151554_b(world, biomeCobblestoneBlock, biomeCobblestoneMeta, bannerXBB, bannerYBB-3, bannerZBB, structureBB);
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
        			{6, 1, 1, -1, 0},
        			{1, 1, 2, -1, 0},
        			{1, 1, 7, -1, 0},
        			})
        		{
        			EntityVillager entityvillager = new EntityVillager(world);
        			
        			// Nitwits more often than not
        			if (GeneralConfig.enableNitwit && random.nextInt(3)==0) {entityvillager.setProfession(5);}
        			else {entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], -random.nextInt(24001));}
        			
        			int k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(ia[0], ia[2]), this.getZWithOffset(ia[0], ia[2]));
        			
        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)k + 0.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
                    		random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(entityvillager);
        		}
            }
            
            return true;
        }
    }
    
    
    
    // --- Well --- //
    
    public static class PlainsMeetingPoint1 extends StartVN
    {
	    int wellDepthDecrease=7;
	    
		public PlainsMeetingPoint1() {}
		
		public PlainsMeetingPoint1(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, int terrainType)
		{
		    super(chunkManager, componentType, random, posX, posZ, components, terrainType);

    		int width = 9;
    		int depth = 9;
    		
		    // Establish orientation
            this.coordBaseMode = random.nextInt(4);
            switch (this.coordBaseMode)
            {
                case 0:
                case 2:
                    this.boundingBox = new StructureBoundingBox(posX, 64+wellDepthDecrease, posZ, posX + width, 79, posZ + depth);
                    break;
                default:
                    this.boundingBox = new StructureBoundingBox(posX, 64+wellDepthDecrease, posZ, posX + depth, 79, posZ + width);
            }
            
            //StructureVillageVN.establishBiomeBlocks(this, posX, posZ);
		}
		
		/*
		 * Add the paths that lead outward from this structure
		 */
		public void buildComponent(StructureComponent start, List components, Random random)
		{
			// Southward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 4, this.boundingBox.maxY - 5, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.maxY - 5, this.boundingBox.minZ + 4, 1, this.getComponentType());
        	// Northward
        	StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 4, this.boundingBox.maxY - 5, this.boundingBox.minZ - 1, 2, this.getComponentType());
        	// Eastward
        	StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.maxY - 5, this.boundingBox.minZ + 4, 3, this.getComponentType());
		}
		
		/*
		 * Construct the structure
		 */
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, this); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.stone_slab, 3, this); Block biomeCobblestoneSlabBlock = (Block)blockObject[0]; int biomeCobblestoneSlabMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, this); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.standing_sign, 0, this); Block biomeStandingSignBlock = (Block)blockObject[0];
        	
        	if (this.field_143015_k < 0)
            {
                //this.field_143015_k = StructureVillagePiecesVN.getMedianGroundLevel(world, structureBB, true);//this.getAverageGroundLevel(world, structureBoundingBox);
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				new StructureBoundingBox(
        						this.boundingBox.minX+1, this.boundingBox.minZ+1,
        						this.boundingBox.maxX-1, this.boundingBox.maxZ-1), // Set the bounding box version as this bounding box but with Y going from 0 to 512
        				true);
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct a well in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + (5-1) - wellDepthDecrease, 0);
            }
        	
        	
        	// Generate or otherwise obtain village name and banner and colors
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, random,
        			this.getXWithOffset(4, 4),
        			this.getYWithOffset(12),
        			this.getZWithOffset(4, 4));
        	int townColor = villageNBTtag.getInteger("townColor");
        	int townColor2 = villageNBTtag.getInteger("townColor2");
        	
        	
            // The well gets filled completely with water first
            //this.fillWithBlocks(world, structureBoundingBox, 3, 0+wellDepthDecrease, 3, 6, 12, 6, this.biomeCobblestoneBlock, Blocks.flowing_water, false);
            this.fillWithMetadataBlocks(world, structureBB, 3, 0+wellDepthDecrease, 3, 6, 12, 6, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            this.fillWithBlocks(world, structureBB, 4, 1+wellDepthDecrease, 4, 5, 12, 5, Blocks.flowing_water, Blocks.flowing_water, false); // Water
            
            // Well rim
            if (GeneralConfig.decorateVillageCenter)
            {
            	this.fillWithMetadataBlocks(world, structureBB, 4, 12, 3, 5, 12, 3, biomeCobblestoneSlabBlock, biomeCobblestoneSlabMeta, biomeCobblestoneSlabBlock, biomeCobblestoneSlabMeta, false);
            	this.fillWithMetadataBlocks(world, structureBB, 4, 12, 6, 5, 12, 6, biomeCobblestoneSlabBlock, biomeCobblestoneSlabMeta, biomeCobblestoneSlabBlock, biomeCobblestoneSlabMeta, false);
            	this.fillWithMetadataBlocks(world, structureBB, 3, 12, 4, 3, 12, 5, biomeCobblestoneSlabBlock, biomeCobblestoneSlabMeta, biomeCobblestoneSlabBlock, biomeCobblestoneSlabMeta, false);
            	this.fillWithMetadataBlocks(world, structureBB, 6, 12, 4, 6, 12, 5, biomeCobblestoneSlabBlock, biomeCobblestoneSlabMeta, biomeCobblestoneSlabBlock, biomeCobblestoneSlabMeta, false);
            }
            
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
            
            if (GeneralConfig.decorateVillageCenter)
            {
            	int metaBase = ((int)world.getSeed()%4+this.coordBaseMode)%4; // Procedural based on world seed and base mode
            	
            	BlockPos uvw = new BlockPos(4, 15, 4); // Starting position of the block cluster. Use lowest X, Z.
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
            		this.fillWithMetadataBlocks(world, structureBB, 4, 15, 4, 5, 15, 5, Blocks.stained_hardened_clay, townColor2, Blocks.stained_hardened_clay, townColor2, false);
            	}
            }
            
            // Line the well with cobblestone and ensure the spaces above are clear
            for (int i = 2; i <= 7; ++i)
            {
                for (int j = 2; j <= 7; ++j)
                {
                    if (j == 2 || j == 7 || i == 2 || i == 7)
                    {
                    	if (GeneralConfig.decorateVillageCenter)
                    	{
                    		Object[] tryConcrete = ModObjects.chooseModConcrete(townColor);
                    		Block concreteBlock = Blocks.stained_hardened_clay; int concreteMeta = townColor;
                    		if (tryConcrete != null) {concreteBlock = (Block) tryConcrete[0]; concreteMeta = (Integer) tryConcrete[1];}
                    		
                    		this.fillWithMetadataBlocks(world, structureBB, j, 0+wellDepthDecrease, i, j, 11, i, concreteBlock, concreteMeta, concreteBlock, concreteMeta, false);
                    	}
                    	else
                    	{
                    		this.fillWithMetadataBlocks(world, structureBB, j, 0+wellDepthDecrease, i, j, 11, i, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
                    	}
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
                    	int k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(i, j), this.getZWithOffset(i, j)) - 1;
                        if (k > -1)
                        {
                            StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getBoundingBox().minX+i, k, this.getBoundingBox().minZ+j);
                        	this.clearCurrentPositionBlocksUpwards(world, i, k+1-this.boundingBox.minY, j, structureBB);
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
            		int k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(i, j), this.getZWithOffset(i, j)) - 1;
                    if (k > -1)
                    {
                    	//StructureVillagePiecesVN.setPathSpecificBlock(world, this, 0, this.getBoundingBox().minX+i, k, this.getBoundingBox().minZ+j);
                    	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(i, j), k, this.getZWithOffset(i, j));
                    	this.clearCurrentPositionBlocksUpwards(world, i, k+1-this.boundingBox.minY, j, structureBB);
                   	}
                    
                    //k = world.getTopSolidOrLiquidBlock(this.getBoundingBox().minX+j, this.getBoundingBox().minZ+i) - 1;
                    k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(j, i), this.getZWithOffset(j, i)) - 1;
                    if (k > -1)
                    {
                    	//StructureVillagePiecesVN.setPathSpecificBlock(world, this, 0, this.getBoundingBox().minX+j, k, this.getBoundingBox().minZ+i);
                    	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(j, i), k, this.getZWithOffset(j, i));
                    	this.clearCurrentPositionBlocksUpwards(world, j, k+1-this.boundingBox.minY, i, structureBB);
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
                    int bannerXBB = 8;
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
                    
                    // Place a cobblestone foundation
                    this.fillWithMetadataBlocks(world, structureBB, bannerXBB, bannerYBB-2, bannerZBB, bannerXBB, bannerYBB-1, bannerZBB, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
                    this.func_151554_b(world, biomeCobblestoneBlock, biomeCobblestoneMeta, bannerXBB, bannerYBB-3, bannerZBB, structureBB);
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
        			{1, 11, 8, -1, 0},
        			{8, 11, 8, -1, 0},
        			})
        		{
        			EntityVillager entityvillager = new EntityVillager(world);
        			
        			// Nitwits more often than not
        			if (GeneralConfig.enableNitwit && random.nextInt(3)==0) {entityvillager.setProfession(5);}
        			else {entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], -random.nextInt(24001));}
        			
        			int k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(ia[0], ia[2]), this.getZWithOffset(ia[0], ia[2]));
        			
        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)k + 0.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
                    		random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(entityvillager);
        		}
            }
            
            return true;
        }
    }
    
    

    // --- Market --- //
    
    public static class PlainsMeetingPoint2 extends StartVN
    {
	    public PlainsMeetingPoint2() {}
		
		public PlainsMeetingPoint2(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, int terrainType)
		{
		    super(chunkManager, componentType, random, posX, posZ, components, terrainType);
		    
    		int width = 7;
    		int depth = 14;
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
			// Southward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode==0 ? 1 : this.coordBaseMode==1 ? 6 : this.coordBaseMode==2 ? 1 : 6), this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode==0 ? 2 : this.coordBaseMode==1 ? 1 : this.coordBaseMode==2 ? 10 : 1), 1, this.getComponentType());
			// Northward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode==0 ? 1 : this.coordBaseMode==1 ? 10 : this.coordBaseMode==2 ? 1 : 2), this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode==0 ? 6 : this.coordBaseMode==1 ? 1 : this.coordBaseMode==2 ? 6 : 1), 3, this.getComponentType());
		}
		
		/*
		 * Construct the structure
		 */
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, this); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, this); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, this); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_slab, 0, this); Block biomeWoodSlabBlock = (Block)blockObject[0]; int biomeWoodSlabMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, this); Block biomeFenceBlock = (Block)blockObject[0];
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
        			this.getXWithOffset(4, 4),
        			this.getYWithOffset(12),
        			this.getZWithOffset(4, 4));
        	int townColor = villageNBTtag.getInteger("townColor");
        	int townColor2 = villageNBTtag.getInteger("townColor2");
        	
        	// Top layer is grass
        	this.fillWithMetadataBlocks(world, structureBB, 0, 0, 0, 7, 0, 14, biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);
        	// Clear above
        	for (int i=0; i<=7; i++)
        	{
        		for (int j=0; j<=14; j++)
            	{
        			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, i, -1, j, structureBB); // Foundation
        			this.clearCurrentPositionBlocksUpwards(world, i, 1, j, structureBB);
            	}
        	}
        	
        	// Set grass paths
        	for (int[] offset_xy : new int[][]{
        		{0, 2}, {0, 3}, 
        		{1, 0}, {1, 2}, {1, 3}, {1, 4}, {1, 13}, {1, 14}, 
        		{2, 0}, {2, 1}, {2, 2}, {2, 3}, {2, 12}, {2, 13}, {2, 14}, 
        		{3, 0}, {3, 1}, {3, 2}, {3, 4}, {3, 7}, {3, 9}, {3, 10}, {3, 11}, {3, 12}, {3, 13}, 
        		{4, 3}, {4, 4}, {4, 5}, {4, 6}, {4, 7}, {4, 9}, 
        		{5, 2}, {5, 3}, {5, 4}, {5, 5}, {5, 7}, {5, 8}, {5, 10}, {5, 12}, 
        		{6, 2}, {6, 3}, {6, 4}, {6, 5}, {6, 6}, {6, 7}, {6, 8}, {6, 11}, {6, 12},
        		{7, 3}, {7, 4}, {7, 5}, {7, 6}, {7, 7}, {7, 8}, {7, 9}, {7, 11}, 
        	})
        	{
        		StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(offset_xy[0], offset_xy[1]), this.getYWithOffset(0), this.getZWithOffset(offset_xy[0], offset_xy[1]));
        	}
        	
        	// Unkempt grass
        	this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 0, 0, 1, 8, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 0, 1, 1, 7, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 0, 1, 1, 12, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 0, 4, 1, 10, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 0, 4, 1, 11, structureBB);
        	
        	// Stalls
        	this.fillWithMetadataBlocks(world, structureBB, 4, 1, 1, 7, 1, 1, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 4, 0, 1, 7, 0, 1, biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);
        	this.fillWithBlocks(world, structureBB, 4, 1, 0, 4, 3, 0, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 4, 1, 2, 4, 3, 2, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 7, 1, 2, 7, 3, 2, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 7, 1, 0, 7, 3, 0, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithMetadataBlocks(world, structureBB, 4, 4, 0, 7, 4, 2, biomeWoodSlabBlock, biomeWoodSlabMeta, biomeWoodSlabBlock, biomeWoodSlabMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 5, 4, 0, 6, 4, 2, Blocks.wool, GeneralConfig.decorateVillageCenter ? townColor : 4, Blocks.wool, GeneralConfig.decorateVillageCenter ? townColor : 4, false);
        	this.placeBlockAtCurrentPosition(world, Blocks.wool, GeneralConfig.decorateVillageCenter ? townColor2 : 0, 5, 4, 0, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.wool, GeneralConfig.decorateVillageCenter ? townColor2 : 0, 6, 4, 1, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.wool, GeneralConfig.decorateVillageCenter ? townColor2 : 0, 5, 4, 2, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 4, 2, 1, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 7, 2, 1, structureBB);
        	
        	this.fillWithMetadataBlocks(world, structureBB, 2, 1, 5, 2, 1, 8, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 2, 0, 5, 2, 0, 8, biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);
        	this.fillWithBlocks(world, structureBB, 1, 1, 5, 1, 3, 5, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 1, 1, 8, 1, 3, 8, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 3, 1, 8, 3, 3, 8, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 3, 1, 5, 3, 3, 5, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithMetadataBlocks(world, structureBB, 1, 4, 5, 3, 4, 8, biomeWoodSlabBlock, biomeWoodSlabMeta, biomeWoodSlabBlock, biomeWoodSlabMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 1, 4, 6, 3, 4, 7, Blocks.wool, GeneralConfig.decorateVillageCenter ? townColor : 4, Blocks.wool, GeneralConfig.decorateVillageCenter ? townColor : 4, false);
        	this.placeBlockAtCurrentPosition(world, Blocks.wool, GeneralConfig.decorateVillageCenter ? townColor2 : 0, 1, 4, 7, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.wool, GeneralConfig.decorateVillageCenter ? townColor2 : 0, 2, 4, 6, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.wool, GeneralConfig.decorateVillageCenter ? townColor2 : 0, 3, 4, 7, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 2, 2, 5, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 2, 2, 8, structureBB);
        	
        	this.fillWithMetadataBlocks(world, structureBB, 4, 1, 13, 7, 1, 13, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 4, 0, 13, 7, 0, 13, biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);
        	this.fillWithBlocks(world, structureBB, 4, 1, 12, 4, 3, 12, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 4, 1, 14, 4, 3, 14, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 7, 1, 14, 7, 3, 14, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 7, 1, 12, 7, 3, 12, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithMetadataBlocks(world, structureBB, 4, 4, 12, 7, 4, 14, biomeWoodSlabBlock, biomeWoodSlabMeta, biomeWoodSlabBlock, biomeWoodSlabMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 5, 4, 12, 6, 4, 14, Blocks.wool, GeneralConfig.decorateVillageCenter ? townColor : 4, Blocks.wool, GeneralConfig.decorateVillageCenter ? townColor : 4, false);
        	this.placeBlockAtCurrentPosition(world, Blocks.wool, GeneralConfig.decorateVillageCenter ? townColor2 : 0, 5, 4, 12, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.wool, GeneralConfig.decorateVillageCenter ? townColor2 : 0, 6, 4, 13, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.wool, GeneralConfig.decorateVillageCenter ? townColor2 : 0, 5, 4, 14, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 4, 2, 13, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 7, 2, 13, structureBB);
        	
        	        	        	
            // Sign
            int signXBB = 2;
			int signYBB = 2;
			int signZBB = 10;
            int signX = this.getXWithOffset(signXBB, signZBB);
            int signY = this.getYWithOffset(signYBB);
            int signZ = this.getZWithOffset(signXBB, signZBB);
    		
    		String namePrefix = villageNBTtag.getString("namePrefix");
    		String nameRoot = villageNBTtag.getString("nameRoot");
    		String nameSuffix = villageNBTtag.getString("nameSuffix");
    		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);
    		
    		this.placeBlockAtCurrentPosition(world, biomePlankBlock, biomePlankMeta, signXBB, signYBB-1, signZBB, structureBB);
    		this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, signXBB, signYBB-2, signZBB, structureBB);
        	
			world.setBlock(signX, signY, signZ, biomeStandingSignBlock, StructureVillageVN.getSignRotationMeta(12, this.coordBaseMode, false), 2); // 2 is "send change to clients without block update notification"
    		world.setTileEntity(signX, signY, signZ, signContents);
    		
    		
			// Banner
    		if (GeneralConfig.decorateVillageCenter)
    		{
        		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
        		if (testForBanner!=null)
    			{
                    int bannerXBB = 6;
        			int bannerZBB = 4;
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
                    
                    // Place a cobblestone foundation
                    this.fillWithMetadataBlocks(world, structureBB, bannerXBB, bannerYBB-2, bannerZBB, bannerXBB, bannerYBB-1, bannerZBB, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
                    this.func_151554_b(world, biomeCobblestoneBlock, biomeCobblestoneMeta, bannerXBB, bannerYBB-3, bannerZBB, structureBB);
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
        			{6, 1, 6, -1, 0},
        			{5, 1, 8, -1, 0},
        			{5, 1, 10, -1, 0},
        			})
        		{
        			EntityVillager entityvillager = new EntityVillager(world);
        			
        			// Nitwits more often than not
        			if (GeneralConfig.enableNitwit && random.nextInt(3)==0) {entityvillager.setProfession(5);}
        			else {entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], -random.nextInt(24001));}
        			
        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)this.getYWithOffset(ia[1]) + 0.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
                    		random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(entityvillager);
        		}
            }
            
            return true;
        }
    }
    
    
	// --- Tree --- //
	
    public static class PlainsMeetingPoint3 extends StartVN
    {
    	public PlainsMeetingPoint3() {}
    	
    	public PlainsMeetingPoint3(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, int terrainType)
    	{
    		super(chunkManager, componentType, random, posX, posZ, components, terrainType);
    		
    		int width = 10;
    		int depth = 10;
    		int height = 8;
    		
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

            //StructureVillageVN.establishBiomeBlocks(this, posX, posZ);
    	}

		/*
		 * Add the paths that lead outward from this structure
		 */
		public void buildComponent(StructureComponent start, List components, Random random)
		{
			//LogHelper.info("coordBaseMode: " + this.coordBaseMode);
			// Southward
			if (this.coordBaseMode!=2) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 4, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());}
			// Westward
			if (this.coordBaseMode!=3) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 4, 1, this.getComponentType());}
			// Northward
			if (this.coordBaseMode!=0) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 4, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());}
			// Eastward
			if (this.coordBaseMode!=1) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 4, 3, this.getComponentType());}
		}
    	
		/*
		 * Construct the structure
		 */
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;	
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, this); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, this); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, this); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wall_sign, 0, this); Block biomeWallSignBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, this); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.stone_stairs, 0, this); Block biomeStoneStairsBlock = (Block)blockObject[0];
        	
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
            
        	// Generate or otherwise obtain village name and banner and colors
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, random,
        			this.getXWithOffset(5, 5),
        			this.getYWithOffset(2),
        			this.getZWithOffset(5, 5));
        	int townColor = villageNBTtag.getInteger("townColor");
        	int townColor2 = villageNBTtag.getInteger("townColor2");
        	
        	
        	// Level the ground with grass and then insert grass paths
        	
        	// Top layer is grass
        	this.fillWithMetadataBlocks(world, structureBB, 0, 0, 0, 10, 0, 10, biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);
        	// Clear above
        	for (int i=0; i<=10; i++)
        	{
        		for (int j=0; j<=10; j++)
            	{
        			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, i, -1, j, structureBB); // Foundation
        			this.clearCurrentPositionBlocksUpwards(world, i, 1, j, structureBB);
            	}
        	}
        	
        	// Set grass paths
        	for (int[] offset_xy : new int[][]{
        		{0, 2}, {0, 5}, {0, 8}, {0, 9}, 
        		{1, 0}, {1, 1}, {1, 3}, {1, 4}, {1, 7}, 
        		{2, 0}, {2, 2}, {2, 6}, {2, 8}, {2, 9}, 
        		{3, 0}, {3, 2}, {3, 5}, {3, 8}, 
        		{4, 7}, {4, 10}, 
        		{5, 0}, {5, 3}, {5, 7}, {5, 8}, {5, 9}, 
        		{6, 0}, 
        		{7, 4}, {7, 6}, {7, 7}, {7, 10}, 
        		{8, 1}, {8, 6}, {8, 9}, 
        		{9, 2}, {9, 3}, {9, 5}, {9, 8}, 
        		{10, 1}, {10, 4}, {10, 5}, 
        	})
        	{
        		StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(offset_xy[0], offset_xy[1]), this.getYWithOffset(0), this.getZWithOffset(offset_xy[0], offset_xy[1]));
        	}

        	// Set cobblestone
        	for (int[] offset_xy : new int[][]{
        		{0, 1}, {0, 4}, 
        		{1, 5}, {1, 8}, 
        		{2, 1}, {2, 4}, {2, 5}, {2, 7}, {2, 10}, 
        		{3, 3}, {3, 6}, {3, 9}, 
        		{4, 3}, {4, 8}, 
        		{5, 1}, {5, 2}, {5, 10}, 
        		{6, 2}, {6, 3}, {6, 9}, 
        		{7, 1}, {7, 3}, {7, 5}, {7, 8}, 
        		{8, 2}, {8, 4}, 
        		{9, 6}, {9, 9}, 
        		{10, 7}, 
        	})
        	{
        		this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, offset_xy[0], 0, offset_xy[1], structureBB);
        	}
        	
        	// Unkempt grass
        	for (int[] offset_xy : new int[][]{
        		{0, 3}, 
        		{1, 2}, {1, 6}, 
        		{3, 4}, {3, 10}, 
        		{4, 2}, 
        		{6, 7}, {6, 8}, 
        		{7, 0}, 
        		{8, 0}, {8, 5}, {8, 8},
        		{10, 0}, {10, 3}, 
        	})
        	{
        		this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 0, offset_xy[0], 1, offset_xy[1], structureBB);
        	}
        	this.placeBlockAtCurrentPosition(world, Blocks.yellow_flower, 0, 3, 1, 1, structureBB);
        	
        	
        	// Tree
        	this.placeBlockAtCurrentPosition(world, Blocks.dirt, 0, 5, 0, 5, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 3), 4, 1, 4, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 3), 5, 1, 4, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 1), 6, 1, 4, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 1), 6, 1, 5, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 2), 6, 1, 6, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 2), 5, 1, 6, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 0), 4, 1, 6, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 0), 4, 1, 5, structureBB);
        	this.fillWithMetadataBlocks(world, structureBB, 3, 5, 4, 7, 6, 6, Blocks.leaves, 0, Blocks.leaves, 0, false);
        	this.fillWithMetadataBlocks(world, structureBB, 4, 5, 3, 6, 6, 7, Blocks.leaves, 0, Blocks.leaves, 0, false);
        	this.fillWithMetadataBlocks(world, structureBB, 4, 7, 4, 6, 8, 6, Blocks.leaves, 0, Blocks.leaves, 0, false);
        	this.fillWithMetadataBlocks(world, structureBB, 5, 1, 5, 5, 7, 5, Blocks.log, 0, Blocks.log, 0, false);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 4, 5, 3, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 4, 5, 7, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 6, 5, 7, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 5, 6, 7, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 4, 8, 4, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 6, 8, 4, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 6, 8, 6, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 4, 8, 6, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 5, 3, 4, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 4, 3, 5, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 5, 3, 6, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 6, 3, 5, structureBB);
        	
                    	        	
            // Posts
        	this.fillWithBlocks(world, structureBB, 4, 1, 1, 4, 4, 1, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 6, 1, 1, 6, 4, 1, biomeFenceBlock, biomeFenceBlock, false);
        	if (GeneralConfig.decorateVillageCenter)
        	{
        		Object[] tryConcrete = ModObjects.chooseModConcrete(townColor);
            	Block concreteBlock = Blocks.stained_hardened_clay; int concreteMeta = townColor;
            	if (tryConcrete != null) {concreteBlock = (Block) tryConcrete[0]; concreteMeta = (Integer) tryConcrete[1];}
        		
        		this.placeBlockAtCurrentPosition(world, concreteBlock, concreteMeta, 5, 4, 1, structureBB);
        	}
        	else
        	{
        		this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, 5, 4, 1, structureBB);
        	}
        	
        	this.fillWithBlocks(world, structureBB, 4, 1, 9, 4, 4, 9, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithBlocks(world, structureBB, 6, 1, 9, 6, 4, 9, biomeFenceBlock, biomeFenceBlock, false);
        	if (GeneralConfig.decorateVillageCenter)
        	{
        		Object[] tryConcrete = ModObjects.chooseModConcrete(townColor2);
            	Block concreteBlock = Blocks.stained_hardened_clay; int concreteMeta = townColor2;
            	if (tryConcrete != null) {concreteBlock = (Block) tryConcrete[0]; concreteMeta = (Integer) tryConcrete[1];}
        		
        		this.placeBlockAtCurrentPosition(world, concreteBlock, concreteMeta, 5, 4, 9, structureBB);
        	}
        	else
        	{
        		this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, 5, 4, 9, structureBB);
        	}
        	
        	// Signs
            int signXBB = 5;
			int signYBB = 4;
			int signZBB = 0;
			int signZBB2 = 10;
            int signX = this.getXWithOffset(signXBB, signZBB);
            int signX2 = this.getXWithOffset(signXBB, signZBB2);
            int signY = this.getYWithOffset(signYBB);
            int signZ = this.getZWithOffset(signXBB, signZBB);
            int signZ2 = this.getZWithOffset(signXBB, signZBB2);
    		
    		String namePrefix = villageNBTtag.getString("namePrefix");
    		String nameRoot = villageNBTtag.getString("nameRoot");
    		String nameSuffix = villageNBTtag.getString("nameSuffix");
    		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);
    		
			world.setBlock(signX, signY, signZ, biomeWallSignBlock, StructureVillageVN.getSignRotationMeta(2, this.coordBaseMode, true), 2); // 2 is "send change to clients without block update notification"
			world.setTileEntity(signX, signY, signZ, signContents);
    		
            // I need to make a duplicate TileEntity because the first one gets consumed when applied to the first sign
    		TileEntitySign signContents2 = new TileEntitySign();
    		for (int i=0; i<4; i++) {signContents2.signText[i] = signContents.signText[i];}
    		
			world.setBlock(signX2, signY, signZ2, biomeWallSignBlock, StructureVillageVN.getSignRotationMeta(0, this.coordBaseMode, true), 2); // 2 is "send change to clients without block update notification"
    		world.setTileEntity(signX2, signY, signZ2, signContents2);

    		
			// Banner    		
    		if (GeneralConfig.decorateVillageCenter)
    		{
        		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
        		if (testForBanner!=null)
    			{
                    int bannerXBB = 7;
        			int bannerZBB = 8;
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
                    
                    // Place a cobblestone foundation
                    this.fillWithMetadataBlocks(world, structureBB, bannerXBB, bannerYBB-2, bannerZBB, bannerXBB, bannerYBB-1, bannerZBB, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
                    this.func_151554_b(world, biomeCobblestoneBlock, biomeCobblestoneMeta, bannerXBB, bannerYBB-3, bannerZBB, structureBB);
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
        			{8, 1, 6, -1, 0},
        			{9, 1, 2, -1, 0},
        			})
        		{
        			EntityVillager entityvillager = new EntityVillager(world);
        			
        			// Nitwits more often than not
        			if (GeneralConfig.enableNitwit && random.nextInt(3)==0) {entityvillager.setProfession(5);}
        			else {entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], -random.nextInt(24001));}
        			
        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)this.getYWithOffset(ia[1]) + 0.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
                    		random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(entityvillager);
        		}
            }
            
            return true;
        }
    }
}
