package astrotibs.villagenames.village.biomestructures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import astrotibs.villagenames.banner.TileEntityBanner;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.utility.BlockPos;
import astrotibs.villagenames.utility.FunctionsVN;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.village.StructureVillageVN;
import astrotibs.villagenames.village.StructureVillageVN.StartVN;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.common.ChestGenHooks;

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
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, this.materialType, this.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.standing_sign, 0, this.materialType, this.biome); Block biomeStandingSignBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, this.materialType, this.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, this.materialType, this.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	
        	if (this.field_143015_k < 0)
            {
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				new StructureBoundingBox(
        						this.boundingBox.minX, this.boundingBox.minZ,
        						this.boundingBox.maxX, this.boundingBox.maxZ), // Set the bounding box version as this bounding box but with Y going from 0 to 512
        				true, (byte)15, this.coordBaseMode);
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct in a void
                
                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.minY -1, 0);
            }
            
        	// Generate or otherwise obtain village name and banner and colors
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, 
        			this.getXWithOffset(4, 4),
        			this.getYWithOffset(2),
        			this.getZWithOffset(4, 4));
        	this.townColor = villageNBTtag.getInteger("townColor");
        	this.townColor2 = villageNBTtag.getInteger("townColor2");
    		this.namePrefix = villageNBTtag.getString("namePrefix");
    		this.nameRoot = villageNBTtag.getString("nameRoot");
    		this.nameSuffix = villageNBTtag.getString("nameSuffix");
        	
            // Basin bottom
            this.fillWithMetadataBlocks(world, structureBB, 2, -1, 2, 6, 0, 6, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            
            // Torches with the correct metas
            for (int[] uvwm : new int[][]{
            	{2, 1, 2, 0},
            	{2, 1, 6, 0},
            	{6, 1, 2, 0},
            	{6, 1, 6, 0},
            })
            {
            	world.setBlock(this.getXWithOffset(uvwm[0], uvwm[2]), this.getYWithOffset(uvwm[1]), this.getZWithOffset(uvwm[0], uvwm[2]), Blocks.torch, uvwm[3], 2);
            }
            
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
                        	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(i, j), k, this.getZWithOffset(i, j));
                        	this.clearCurrentPositionBlocksUpwards(world, i, k+2-this.boundingBox.minY, j, structureBB);
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
                    	this.clearCurrentPositionBlocksUpwards(world, i, k+2-this.boundingBox.minY, j, structureBB);
                    	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(i, j), k, this.getZWithOffset(i, j));
                   	}
                    
                    k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(j, i), this.getZWithOffset(j, i)) - 1;
                    if (k > -1)
                    {
                    	this.clearCurrentPositionBlocksUpwards(world, j, k+2-this.boundingBox.minY, i, structureBB);
                    	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(j, i), k, this.getZWithOffset(j, i));
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
        			if (this.bannerY==0)
        			{
        				this.bannerY = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(bannerXBB, bannerZBB), this.getZWithOffset(bannerXBB, bannerZBB))-this.boundingBox.minY +1;
        				bannerYBB = this.bannerY;
        			}
        			else {bannerYBB = this.bannerY;}
        			
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
        	StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode<=1? 3 : 4), this.boundingBox.maxY - 5, this.boundingBox.minZ - 1, 2, this.getComponentType());
        	// Eastward
        	StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.maxY - 5, this.boundingBox.minZ + (this.coordBaseMode<=1? 3 : 4), 3, this.getComponentType());
			// Southward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode<=1? 4 : 3), this.boundingBox.maxY - 5, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.maxY - 5, this.boundingBox.minZ + (this.coordBaseMode<=1? 4 : 3), 1, this.getComponentType());
		}
		
		/*
		 * Construct the structure
		 */
		@Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, this.materialType, this.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.stone_slab, 3, this.materialType, this.biome); Block biomeCobblestoneSlabBlock = (Block)blockObject[0]; int biomeCobblestoneSlabMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, this.materialType, this.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.standing_sign, 0, this.materialType, this.biome); Block biomeStandingSignBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, this.materialType, this.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	
        	if (this.field_143015_k < 0)
            {
                //this.field_143015_k = StructureVillagePiecesVN.getMedianGroundLevel(world, structureBB, true);//this.getAverageGroundLevel(world, structureBoundingBox);
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				new StructureBoundingBox(
        						this.boundingBox.minX+1, this.boundingBox.minZ+1,
        						this.boundingBox.maxX-1, this.boundingBox.maxZ-1), // Set the bounding box version as this bounding box but with Y going from 0 to 512
        				true, (byte)15, this.coordBaseMode);
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct a well in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + (5-1) - wellDepthDecrease, 0);
            }
        	
        	
        	// Generate or otherwise obtain village name and banner and colors
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, 
        			this.getXWithOffset(4, 4),
        			this.getYWithOffset(12),
        			this.getZWithOffset(4, 4));
        	this.townColor = villageNBTtag.getInteger("townColor");
        	this.townColor2 = villageNBTtag.getInteger("townColor2");
    		this.namePrefix = villageNBTtag.getString("namePrefix");
    		this.nameRoot = villageNBTtag.getString("nameRoot");
    		this.nameSuffix = villageNBTtag.getString("nameSuffix");
        	
        	
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
            for (int[] uvwm : new int[][]{
            	{3, 16, 3, 0},
            	{3, 16, 6, 0},
            	{6, 16, 3, 0},
            	{6, 16, 6, 0},
            })
            {
            	world.setBlock(this.getXWithOffset(uvwm[0], uvwm[2]), this.getYWithOffset(uvwm[1]), this.getZWithOffset(uvwm[0], uvwm[2]), Blocks.torch, uvwm[3], 2);
            }
            
            
            // Encircle the well with path
        	StructureVillagePieces.Start startPiece_reflected = ReflectionHelper.getPrivateValue(StructureVillagePieces.Village.class, this, new String[]{"startPiece"});
        	for (int i = 1; i <= 8; ++i)
            {
                for (int j = 1; j <= 8; ++j)
                {
                    if (j == 1 || j == 8 || i == 1 || i == 8)
                    {
                    	// Gets ground level, so long as it's not leaves or other foliage
                    	int k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(i, j), this.getZWithOffset(i, j)) - 1;
                        if (k > -1)
                        {
                            StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(i, j), k, this.getZWithOffset(i, j));
                        	this.clearCurrentPositionBlocksUpwards(world, i, k+2-this.boundingBox.minY, j, structureBB);
                       	}
                    }
                }
            }
            // Add path nodules at the end
            for (int i : new int[]{3,4,5})
            {
            	int k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(0, i+1), this.getZWithOffset(0, i+1)) - 1;
                if (k > -1)
                {
                	this.clearCurrentPositionBlocksUpwards(world, 0, k+2-this.boundingBox.minY, i+1, structureBB);
                	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(0, i+1), k, this.getZWithOffset(0, i+1));
               	}
                
                k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(9, i), this.getZWithOffset(9, i)) - 1;
                if (k > -1)
                {
                	this.clearCurrentPositionBlocksUpwards(world, 9, k+2-this.boundingBox.minY, i, structureBB);
                	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(9, i), k, this.getZWithOffset(9, i));
               	}

                k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(i, 0), this.getZWithOffset(i, 0)) - 1;
                if (k > -1)
                {
                	this.clearCurrentPositionBlocksUpwards(world, i, k+2-this.boundingBox.minY, 0, structureBB);
                	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(i, 0), k, this.getZWithOffset(i, 0));
               	}
                
                k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(i+1, 9), this.getZWithOffset(i+1, 9)) - 1;
                if (k > -1)
                {
                	this.clearCurrentPositionBlocksUpwards(world, i+2, k+2-this.boundingBox.minY, 9, structureBB);
                	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(i+1, 9), k, this.getZWithOffset(i+1, 9));
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
        			if (this.bannerY==0)
        			{
        				this.bannerY = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(bannerXBB, bannerZBB), this.getZWithOffset(bannerXBB, bannerZBB))-this.boundingBox.minY +1;
        				bannerYBB = this.bannerY;
        			}
        			else {bannerYBB = this.bannerY;}
        			
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
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode==0 ? 1 : this.coordBaseMode==1 ? 10 : this.coordBaseMode==2 ? 1 : 2), this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode==0 ? 6 : this.coordBaseMode==1 ? 1 : this.coordBaseMode==2 ? 6 : 1), 3, this.getComponentType());
			// Southward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode==0 ? 1 : this.coordBaseMode==1 ? 6 : this.coordBaseMode==2 ? 1 : 6), this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode==0 ? 2 : this.coordBaseMode==1 ? 1 : this.coordBaseMode==2 ? 10 : 1), 1, this.getComponentType());
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
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, this.materialType, this.biome); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_slab, 0, this.materialType, this.biome); Block biomeWoodSlabBlock = (Block)blockObject[0]; int biomeWoodSlabMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, this.materialType, this.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.standing_sign, 0, this.materialType, this.biome); Block biomeStandingSignBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, this.materialType, this.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	
        	if (this.field_143015_k < 0)
            {
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				new StructureBoundingBox(
        						this.boundingBox.minX, this.boundingBox.minZ,
        						this.boundingBox.maxX, this.boundingBox.maxZ), // Set the bounding box version as this bounding box but with Y going from 0 to 512
        				true, (byte)15, this.coordBaseMode);
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct a well in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.minY -1, 0);
            }
        	
        	
        	// Generate or otherwise obtain village name and banner and colors
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, 
        			this.getXWithOffset(4, 4),
        			this.getYWithOffset(12),
        			this.getZWithOffset(4, 4));
        	this.townColor = villageNBTtag.getInteger("townColor");
        	this.townColor2 = villageNBTtag.getInteger("townColor2");
    		this.namePrefix = villageNBTtag.getString("namePrefix");
    		this.nameRoot = villageNBTtag.getString("nameRoot");
    		this.nameSuffix = villageNBTtag.getString("nameSuffix");
        	
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
            
        	// Torches
            for (int[] uvwm : new int[][]{
            	{4, 2, 1, 0},
            	{7, 2, 1, 0},
            })
            {
            	world.setBlock(this.getXWithOffset(uvwm[0], uvwm[2]), this.getYWithOffset(uvwm[1]), this.getZWithOffset(uvwm[0], uvwm[2]), Blocks.torch, uvwm[3], 2);
            }
            
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

            // Torches
            for (int[] uvwm : new int[][]{
            	{2, 2, 5, 0},
            	{2, 2, 8, 0},
            })
            {
            	world.setBlock(this.getXWithOffset(uvwm[0], uvwm[2]), this.getYWithOffset(uvwm[1]), this.getZWithOffset(uvwm[0], uvwm[2]), Blocks.torch, uvwm[3], 2);
            }
        	
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
        	
            // Torches
            for (int[] uvwm : new int[][]{
            	{4, 2, 13, 0},
            	{7, 2, 13, 0},
            })
            {
            	world.setBlock(this.getXWithOffset(uvwm[0], uvwm[2]), this.getYWithOffset(uvwm[1]), this.getZWithOffset(uvwm[0], uvwm[2]), Blocks.torch, uvwm[3], 2);
            }
        	
        	        	        	
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
        			if (this.bannerY==0)
        			{
        				this.bannerY = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(bannerXBB, bannerZBB), this.getZWithOffset(bannerXBB, bannerZBB))-this.boundingBox.minY +1;
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
        			{6, 1, 6, -1, 0},
        			{5, 1, 8, -1, 0},
        			{5, 1, 10, -1, 0},
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
			if (this.coordBaseMode!=0) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 4, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());}
			// Eastward
			if (this.coordBaseMode!=1) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 4, 3, this.getComponentType());}
			// Southward
			if (this.coordBaseMode!=2) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 4, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());}
			// Westward
			if (this.coordBaseMode!=3) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 4, 1, this.getComponentType());}
		}
    	
		/*
		 * Construct the structure
		 */
    	@Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;	
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, this.materialType, this.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, this.materialType, this.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, this.materialType, this.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wall_sign, 0, this.materialType, this.biome); Block biomeWallSignBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, this.materialType, this.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.stone_stairs, 0, this.materialType, this.biome); Block biomeStoneStairsBlock = (Block)blockObject[0];
        	
        	if (this.field_143015_k < 0)
            {
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				new StructureBoundingBox(
        						this.boundingBox.minX, this.boundingBox.minZ,
        						this.boundingBox.maxX, this.boundingBox.maxZ), // Set the bounding box version as this bounding box but with Y going from 0 to 512
        				true, (byte)15, this.coordBaseMode);
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.minY -1, 0);
            }
            
        	// Generate or otherwise obtain village name and banner and colors
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, 
        			this.getXWithOffset(5, 5),
        			this.getYWithOffset(2),
        			this.getZWithOffset(5, 5));
        	this.townColor = villageNBTtag.getInteger("townColor");
        	this.townColor2 = villageNBTtag.getInteger("townColor2");
    		this.namePrefix = villageNBTtag.getString("namePrefix");
    		this.nameRoot = villageNBTtag.getString("nameRoot");
    		this.nameSuffix = villageNBTtag.getString("nameSuffix");
        	
        	
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
        	for (int uvwm[] : new int[][]{
        		{4, 1, 4, 3}, // Corner
        		{5, 1, 4, 3},
        		{6, 1, 4, 1}, // Corner
        		{6, 1, 5, 1},
        		{6, 1, 6, 2}, // Corner
        		{5, 1, 6, 2},
        		{4, 1, 6, 0}, // Corner
        		{4, 1, 5, 0},
        	})
        	{
        		this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwm[3]), uvwm[0], uvwm[1], uvwm[2], structureBB);
        	}
        	
        	
        	// Dirt block
        	world.setBlock(this.getXWithOffset(5, 5), this.getYWithOffset(0), this.getZWithOffset(5, 5), Blocks.dirt, 0, 2);
        	
        	// Leaves placed into world
        	for (int u=3; u<=7; u++) {for (int v=5; v<=6; v++) {for (int w=4; w<=6; w++) {world.setBlock(this.getXWithOffset(u, w), this.getYWithOffset(v), this.getZWithOffset(u, w), Blocks.leaves, 0, 2);}}}
        	for (int u=4; u<=6; u++) {for (int v=5; v<=6; v++) {for (int w=3; w<=7; w++) {world.setBlock(this.getXWithOffset(u, w), this.getYWithOffset(v), this.getZWithOffset(u, w), Blocks.leaves, 0, 2);}}}
        	for (int u=4; u<=6; u++) {for (int v=7; v<=8; v++) {for (int w=4; w<=6; w++) {world.setBlock(this.getXWithOffset(u, w), this.getYWithOffset(v), this.getZWithOffset(u, w), Blocks.leaves, 0, 2);}}}

        	// Logs need to be set in world so as not to be replaced with sandstone
        	for (int v=1; v<=7; v++) {world.setBlock(this.getXWithOffset(5, 5), this.getYWithOffset(v), this.getZWithOffset(5, 5), Blocks.log, 0, 2);}
        	
        	// Carve out chunks of leaves using air
            for (int[] uvw : new int[][]{
            	{4, 5, 3},
            	{4, 5, 7},
            	{6, 5, 7},
            	{5, 6, 7},
            	{4, 8, 4},
            	{6, 8, 4},
            	{6, 8, 6},
            	{4, 8, 6},
            })
            {
            	world.setBlock(this.getXWithOffset(uvw[0], uvw[2]), this.getYWithOffset(uvw[1]), this.getZWithOffset(uvw[0], uvw[2]), Blocks.air, 0, 2);
            }
            
            // Torches
            for (int[] uvwm : new int[][]{
            	{5, 3, 4, StructureVillageVN.getTorchRotationMeta(2, this.coordBaseMode)},
            	{4, 3, 5, StructureVillageVN.getTorchRotationMeta(3, this.coordBaseMode)},
            	{5, 3, 6, StructureVillageVN.getTorchRotationMeta(0, this.coordBaseMode)},
            	{6, 3, 5, StructureVillageVN.getTorchRotationMeta(1, this.coordBaseMode)},
            })
            {
            	world.setBlock(this.getXWithOffset(uvwm[0], uvwm[2]), this.getYWithOffset(uvwm[1]), this.getZWithOffset(uvwm[0], uvwm[2]), Blocks.torch, uvwm[3], 2);
            }
        	
                    	        	
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
        			if (this.bannerY==0)
        			{
        				this.bannerY = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(bannerXBB, bannerZBB), this.getZWithOffset(bannerXBB, bannerZBB))-this.boundingBox.minY +1;
        				bannerYBB = this.bannerY;
        			}
        			else {bannerYBB = this.bannerY;}
        			
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
    
    
    
    // ------------------ //
    // --- Structures --- //
    // ------------------ //
    
    
    // --- Flower planter ---//
    
    public static class PlainsAccessory1 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public StartVN start;
    	
    	// Here are values to assign to the bounding box
    	private static final int STRUCTURE_WIDTH = 5;
    	private static final int STRUCTURE_HEIGHT = 2;
    	private static final int STRUCTURE_DEPTH = 3;
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	
    	private int averageGroundLevel = -1;
    	
        public PlainsAccessory1() {}

        public PlainsAccessory1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            this.start = start;
        }

        public static PlainsAccessory1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new PlainsAccessory1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
        		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
        				// Set the bounding box version as this bounding box but with Y going from 0 to 512
        				new StructureBoundingBox(
        						this.boundingBox.minX, this.boundingBox.minZ,
        						this.boundingBox.maxX, this.boundingBox.maxZ),
        				true, (byte)15, this.coordBaseMode);
        		
                if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
            }
        	
            Object[] blockObject;	
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, start.materialType, start.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, start.materialType, start.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.trapdoor, 0, start.materialType, start.biome); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
            
        	// make foundation and clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
            		this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
                    this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
            }}
        	
            // Sod filling
            this.fillWithBlocks(world, structureBB, 1, 1, 1, 3, 1, 1, Blocks.grass, Blocks.grass, false);
            // Trapdoor rim
        	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, this.coordBaseMode%2==0 ? 6 : 4, 0, 1, 1, structureBB); // Left
        	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, this.coordBaseMode%2==0 ? 7 : 5, 4, 1, 1, structureBB); // Right
        	this.fillWithMetadataBlocks(world, structureBB, 1, 1, 0, 3, 1, 0, biomeTrapdoorBlock, (new int[]{4, 7, 5, 6})[this.coordBaseMode], biomeTrapdoorBlock, (new int[]{4, 7, 5, 6})[this.coordBaseMode], false); // Front
        	this.fillWithMetadataBlocks(world, structureBB, 1, 1, 2, 3, 1, 2, biomeTrapdoorBlock, (new int[]{5, 6, 4, 7})[this.coordBaseMode], biomeTrapdoorBlock, (new int[]{5, 6, 4, 7})[this.coordBaseMode], false); // Back
        	// Flowers on top
        	for (int f=0; f<3; f++)
        	{
        		int flowerindex = random.nextInt(10 + (Block.getBlockFromName(ModObjects.flowerUTD)==null ? 0 : 2));
        		// 0-8 is "red" flower
        		// 9 is a basic yellow flower
        		// 10-11 are the flowers from UpToDateMod
        		Block flowerblock = flowerindex == 9 ? Blocks.yellow_flower : flowerindex > 9 ? Block.getBlockFromName(ModObjects.flowerUTD) : Blocks.red_flower;
        		int flowermeta = new int[]{0,1,2,3,4,5,6,7,8,0,0,1}[flowerindex];
        		
        		this.placeBlockAtCurrentPosition(world, flowerblock, flowermeta, 1+f, 2, 1, structureBB);
        		// Upper half of double flower block
        		//if (flowerindex>9) {this.placeBlockAtCurrentPosition(world, flowerblock, 11, 1+f, 3, 1, structureBB);} // Meta is always 11?
        	}
        	
            return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    

    // --- Small Animal Pen --- //
    
    public static class PlainsAnimalPen1 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public StartVN start;
    	
    	// Here are values to assign to the bounding box
    	private static final int STRUCTURE_WIDTH = 6;
    	private static final int STRUCTURE_HEIGHT = 8;
    	private static final int STRUCTURE_DEPTH = 5;
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	
    	private int averageGroundLevel = -1;
    	
        public PlainsAnimalPen1() {}

        public PlainsAnimalPen1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            this.start = start;
        }

        public static PlainsAnimalPen1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new PlainsAnimalPen1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
        		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
        				// Set the bounding box version as this bounding box but with Y going from 0 to 512
        				new StructureBoundingBox(
        						this.boundingBox.minX, this.boundingBox.minZ,
        						this.boundingBox.maxX, this.boundingBox.maxZ),
        				true, (byte)1, this.coordBaseMode);
        		
                if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
            }
        	
            Object[] blockObject;	
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, start.materialType, start.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, start.materialType, start.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, start.materialType, start.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence_gate, 0, start.materialType, start.biome); Block biomeFenceGateBlock = (Block)blockObject[0]; int biomeFenceGateMeta = (Integer)blockObject[1];
        	
        	// Make foundation and clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
            		this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
                    this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
                    
                    // Add a fence
                    if (u==0 || u==STRUCTURE_WIDTH-1 || w==0 || w==STRUCTURE_DEPTH-1)
                    {
                    	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, u, 1, w, structureBB);
                    }
            }}
        	
            // Fence gate
            this.placeBlockAtCurrentPosition(world, biomeFenceGateBlock, StructureVillageVN.getMetadataWithOffset(biomeFenceGateBlock, biomeFenceGateMeta, this.coordBaseMode), 2, 1, 0, structureBB);
            
            // Grass and flower in random places
            ArrayList<Integer> weedpositions = new ArrayList<Integer>();
            
            while (weedpositions.size()<3)
            {
            	while (true)
            	{
            		int candidatevalue = random.nextInt((STRUCTURE_WIDTH-2)*(STRUCTURE_DEPTH-2));
            		if (!weedpositions.contains(candidatevalue))
            		{
            			weedpositions.add(candidatevalue); break;
            		}
            	}
            }
            for (int i=0; i<weedpositions.size(); i++)
            {
            	if (i==0) // Random flower
            	{
            		int flowerindex = random.nextInt(10 + (Block.getBlockFromName(ModObjects.flowerUTD)==null ? 0 : 2));
            		// 0-8 is "red" flower
            		// 9 is a basic yellow flower
            		// 10-11 are the flowers from UpToDateMod
            		Block flowerblock = flowerindex == 9 ? Blocks.yellow_flower : flowerindex > 9 ? Block.getBlockFromName(ModObjects.flowerUTD) : Blocks.red_flower;
            		int flowermeta = new int[]{0,1,2,3,4,5,6,7,8,0,0,1}[flowerindex];
            		
            		this.placeBlockAtCurrentPosition(world, flowerblock, flowermeta, weedpositions.get(i)%(STRUCTURE_WIDTH-2)+1, 1, weedpositions.get(i)/(STRUCTURE_WIDTH-2)+1, structureBB);
            	}
            	else // Tall grass
            	{
            		this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 1, weedpositions.get(i)%(STRUCTURE_WIDTH-2)+1, 1, weedpositions.get(i)/(STRUCTURE_WIDTH-2)+1, structureBB);
            	}
            }
            
            // Animal in the pen
            if (!this.entitiesGenerated)
            {
            	entitiesGenerated = true;
            	
            	EntityLiving animal = StructureVillageVN.getVillageAnimal(world, random, true);
                animal.setLocationAndAngles((double)this.getXWithOffset(1, 2) + 0.5D, (double)this.getYWithOffset(1) + 0.5D, (double)this.getZWithOffset(1, 2) + 0.5D, random.nextFloat()*360F, 0.0F);
                world.spawnEntityInWorld(animal);
                
                // Dirt block underneath
                //this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, 1, 0, 2, structureBB);
            }
            
            return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    
    
    // --- Large Animal Pen --- //
    
    public static class PlainsAnimalPen2 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public StartVN start;
    	
    	// Here are values to assign to the bounding box
    	private static final int STRUCTURE_WIDTH = 11;
    	private static final int STRUCTURE_HEIGHT = 7;
    	private static final int STRUCTURE_DEPTH = 7;
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	
    	private int averageGroundLevel = -1;
    	
        public PlainsAnimalPen2() {}

        public PlainsAnimalPen2(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            this.start = start;
        }

        public static PlainsAnimalPen2 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new PlainsAnimalPen2(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
        		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
        				// Set the bounding box version as this bounding box but with Y going from 0 to 512
        				// ONLY USING PART OF THE FRONT for the butcher's shop
        				new StructureBoundingBox(
        						this.boundingBox.minX+0, this.boundingBox.minZ+0,
        						this.boundingBox.maxX-0, this.boundingBox.maxZ-0),
        				true, (byte)1, this.coordBaseMode);
        		
                if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
            }
        	
            Object[] blockObject;	
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, start.materialType, start.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, start.materialType, start.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, start.materialType, start.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence_gate, 0, start.materialType, start.biome); Block biomeFenceGateBlock = (Block)blockObject[0]; int biomeFenceGateMeta = (Integer)blockObject[1];
        	
        	// Make foundation and clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
            		this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
                    this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
                    
                    // Add a fence
                    if (u==0 || u==STRUCTURE_WIDTH-1 || w==0 || w==STRUCTURE_DEPTH-1)
                    {
                    	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, u, 1, w, structureBB);
                    }
            }}
        	
            // Fence gate
            this.placeBlockAtCurrentPosition(world, biomeFenceGateBlock, StructureVillageVN.getMetadataWithOffset(biomeFenceGateBlock, biomeFenceGateMeta, this.coordBaseMode), 5, 1, 0, structureBB);
            
            // Hay block
            this.placeBlockAtCurrentPosition(world, Blocks.hay_block, 0, 7, 1, 3, structureBB);
            
            // Grass and flower in random places
            ArrayList<Integer> weedpositions = new ArrayList<Integer>();
            
            while (weedpositions.size()<14)
            {
            	while (true)
            	{
            		int candidatevalue = random.nextInt((STRUCTURE_WIDTH-2)*(STRUCTURE_DEPTH-2));
            		if (!weedpositions.contains(candidatevalue))
            		{
            			weedpositions.add(candidatevalue); break;
            		}
            	}
            }
            for (int i=0; i<weedpositions.size(); i++)
            {
            	if (i==0) // Random flower
            	{
            		int flowerindex = random.nextInt(10 + (Block.getBlockFromName(ModObjects.flowerUTD)==null ? 0 : 2));
            		// 0-8 is "red" flower
            		// 9 is a basic yellow flower
            		// 10-11 are the flowers from UpToDateMod
            		Block flowerblock = flowerindex == 9 ? Blocks.yellow_flower : flowerindex > 9 ? Block.getBlockFromName(ModObjects.flowerUTD) : Blocks.red_flower;
            		int flowermeta = new int[]{0,1,2,3,4,5,6,7,8,0,0,1}[flowerindex];
            		
            		this.placeBlockAtCurrentPosition(world, flowerblock, flowermeta, weedpositions.get(i)%(STRUCTURE_WIDTH-2)+1, 1, weedpositions.get(i)/(STRUCTURE_WIDTH-2)+1, structureBB);
            	}
            	else // Tall grass if < 11 or double-tall grass otherwise 
            	{
            		this.placeBlockAtCurrentPosition(world, i<11 ? Blocks.tallgrass : Blocks.double_plant, i<11 ? 1 : 2, weedpositions.get(i)%(STRUCTURE_WIDTH-2)+1, 1, weedpositions.get(i)/(STRUCTURE_WIDTH-2)+1, structureBB);
            		// Double-tall topper
            		if (i >=11) {this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 11, weedpositions.get(i)%(STRUCTURE_WIDTH-2)+1, 2, weedpositions.get(i)/(STRUCTURE_WIDTH-2)+1, structureBB);}
            	}
            }
            
            // Animal in the pen
            if (!this.entitiesGenerated)
            {
            	entitiesGenerated = true;
            	
            	for (int[] uvw : new int[][]{
        			{3, 1, 2},
        			{7, 1, 4},
        			})
        		{

                	EntityLiving animal = StructureVillageVN.getVillageAnimal(world, random, true);
                    animal.setLocationAndAngles((double)this.getXWithOffset(uvw[0], uvw[2]) + 0.5D, (double)this.getYWithOffset(uvw[1]) + 0.5D, (double)this.getZWithOffset(uvw[0], uvw[2]) + 0.5D, random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(animal);
                    
                    // Dirt block underneath
                    //this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, uvw[0], uvw[1]-1, uvw[2], structureBB);
        		}
            }
            
            return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    
    
    // --- Decorated Animal Pen --- //
    
    public static class PlainsAnimalPen3 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public StartVN start;
    	
    	// Here are values to assign to the bounding box
    	private static final int STRUCTURE_WIDTH = 11;
    	private static final int STRUCTURE_HEIGHT = 6;
    	private static final int STRUCTURE_DEPTH = 8;
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	
    	private int averageGroundLevel = -1;
    	
        public PlainsAnimalPen3() {}

        public PlainsAnimalPen3(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            this.start = start;
        }

        public static PlainsAnimalPen3 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new PlainsAnimalPen3(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
        		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
        				// Set the bounding box version as this bounding box but with Y going from 0 to 512
        				new StructureBoundingBox(
        						this.boundingBox.minX, this.boundingBox.minZ,
        						this.boundingBox.maxX, this.boundingBox.maxZ),
        				true, (byte)1, this.coordBaseMode);
        		
                if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
            }
        	
            Object[] blockObject;	
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, start.materialType, start.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, start.materialType, start.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, start.materialType, start.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence_gate, 0, start.materialType, start.biome); Block biomeFenceGateBlock = (Block)blockObject[0]; int biomeFenceGateMeta = (Integer)blockObject[1];
        	
        	// Make foundation and clear space above
            for (int w = 0; w < STRUCTURE_DEPTH; ++w) {for (int u = new int[]{2,2,0,0,0,0,0,3}[w]; u <= new int[]{8,8,8,10,10,10,10,10}[w]; ++u) {
            		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
            		this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
                    this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
                    
                    // Add a fence
                    if (u==new int[]{2,2,0,0,0,0,0,3}[w] || u==new int[]{8,8,8,10,10,10,10,10}[w] || w==0 || w==STRUCTURE_DEPTH-1)
                    {
                    	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, u, 1, w, structureBB);
                    }
            }}
            // Add fences missed by above algorithm
            for (int uw[] : new int[][]{
            	{1,2}, 
            	{2,2},
            	{9,3},
            	{8,3},
            	{1,6},
            	{2,6},
            	{3,6},
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uw[0], 1, uw[1], structureBB);
            }
            // Add decor foundations
            for (int uw[] : new int[][]{
            	{0,0}, 
            	{10,1},
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, uw[0], 0, uw[1], structureBB);
        		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uw[0], GROUND_LEVEL-2, uw[1], structureBB);
            }
            
            // Fence gate
            this.placeBlockAtCurrentPosition(world, biomeFenceGateBlock, StructureVillageVN.getMetadataWithOffset(biomeFenceGateBlock, biomeFenceGateMeta, this.coordBaseMode), 5, 1, 0, structureBB);
            
            // Torches
            for (int i : new int[]{0,2})
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 4+i, 2, 0, structureBB);
            }
            
            // Cross-shaped watering hole
            for (int u=0; u<3; u++) {for (int w=0; w<3; w++) {
            	if (u==1 || w==1) {this.placeBlockAtCurrentPosition(world, Blocks.flowing_water, 0, 6+u, 0, 4+w, structureBB);}
            }}
            
            
            // Animal in the pen
            if (!this.entitiesGenerated)
            {
            	entitiesGenerated = true;
            	
            	for (int[] uvw : new int[][]{
        			{1, 1, 4},
        			{5, 1, 2},
        			{5, 1, 5},
        			})
        		{
                	EntityLiving animal = StructureVillageVN.getVillageAnimal(world, random, true);
                    animal.setLocationAndAngles((double)this.getXWithOffset(uvw[0], uvw[2]) + 0.5D, (double)this.getYWithOffset(uvw[1]) + 0.5D, (double)this.getZWithOffset(uvw[0], uvw[2]) + 0.5D, random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(animal);
                    
                    // Dirt block underneath
                    //this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, uvw[0], uvw[1]-1, uvw[2], structureBB);
        		}
            }
            
            
            // Decor
            int[][] decorUVW = new int[][]{
            	{0, 1, 0},
            	{3, 1, 4},
            	{7, 1, 3},
            	{10, 1, 1},
            };  
            
            for (int j=0; j<decorUVW.length; j++)
            {
            	// Get coordinates
            	int[] uvw = decorUVW[j];
            	
            	// Set random seed
            	Random randomFromXYZ = new Random();
            	randomFromXYZ.setSeed(
    					world.getSeed() +
    					FunctionsVN.getUniqueLongForXYZ(
    							this.getXWithOffset(uvw[0], uvw[2]),
    							this.getYWithOffset(uvw[1]),
    							this.getZWithOffset(uvw[0], uvw[2])
    							)
            			);
            	
            	int decorHeightY = uvw[1];
            	
            	// Get ground level
            	if (this.decorHeightY.size()<(j+1))
            	{
            		// There are fewer stored ground levels than this decor number, so this is being generated for the first time.
            		// Add new ground level
            		//decorHeightY = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(uvw[0], uvw[2]), this.getZWithOffset(uvw[0], uvw[2]))-this.boundingBox.minY;
            		this.decorHeightY.add(decorHeightY);
            	}
            	else
            	{
            		// There is already (presumably) a value for this ground level, so this decor is being multiply generated.
            		// Retrieve ground level
            		decorHeightY = this.decorHeightY.get(j);
            	}
            	
            	
            	// Generate decor
            	ArrayList<BlueprintData> decorBlueprint = getRandomPlainsDecorBlueprint(this.start, this.coordBaseMode, randomFromXYZ);
            	
            	for (BlueprintData b : decorBlueprint)
            	{
            		// Place block indicated by blueprint
            		this.placeBlockAtCurrentPosition(world, b.getBlock(), b.getMeta(), uvw[0]+b.getUPos(), decorHeightY+b.getVPos(), uvw[2]+b.getWPos(), structureBB);
            		
            		// Fill below if flagged
            		if ((b.getfillFlag()&1)!=0)
            		{
            			this.func_151554_b(world, b.getBlock(), b.getMeta(), uvw[0]+b.getUPos(), decorHeightY+b.getVPos()-1, uvw[2]+b.getWPos(), structureBB);
            		}
            		
            		// Clear above if flagged
            		if ((b.getfillFlag()&2)!=0)
            		{
            			this.clearCurrentPositionBlocksUpwards(world, uvw[0]+b.getUPos(), decorHeightY+b.getVPos()+1, uvw[2]+b.getWPos(), structureBB);
            		}            		
            	}
            }
            
            return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    
    // --- Armorer House --- //
    
    public static class PlainsArmorerHouse1 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public StartVN start;
    	
    	// Here are values to assign to the bounding box
    	private static final int STRUCTURE_WIDTH = 8;
    	private static final int STRUCTURE_HEIGHT = 8;
    	private static final int STRUCTURE_DEPTH = 9;
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	
    	private int averageGroundLevel = -1;
    	
        public PlainsArmorerHouse1() {}

        public PlainsArmorerHouse1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            this.start = start;
        }

        public static PlainsArmorerHouse1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new PlainsArmorerHouse1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
        		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
        				// Set the bounding box version as this bounding box but with Y going from 0 to 512
        				new StructureBoundingBox(
        						this.boundingBox.minX, this.boundingBox.minZ,
        						this.boundingBox.maxX, this.boundingBox.maxZ),
        				true, (byte)1, this.coordBaseMode);
        		
                if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
            }
        	
            Object[] blockObject;
            blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, start.materialType, start.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, start.materialType, start.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, start.materialType, start.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone_wall, 0, start.materialType, start.biome); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.stone_stairs, 0, start.materialType, start.biome); Block biomeStoneStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.brick_block, 0, start.materialType, start.biome); Block biomeBrickBlock = (Block)blockObject[0]; int biomeBrickMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.oak_stairs, 0, start.materialType, start.biome); Block biomeWoodStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_slab, 8, start.materialType, start.biome); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_door, 0, start.materialType, start.biome); Block biomeWoodDoorBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, start.materialType, start.biome); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), start.materialType, start.biome); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1]; // Toward you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), start.materialType, start.biome); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1]; // Perpendicular to you
        	blockObject = ModObjects.chooseModSmoothStoneBlock(); Block smoothStoneBlock = (Block)blockObject[0]; int smoothStoneMeta = (Integer)blockObject[1];
        	blockObject = ModObjects.chooseModBlastFurnaceBlock(3, this.coordBaseMode); Block blastFurnaceBlock = (Block) blockObject[0]; int blastFurnaceMeta = (Integer) blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(ModObjects.chooseModPathBlock(), 0, start.materialType, start.biome); Block grassPathBlock = (Block) blockObject[0]; int grassPathMeta = (Integer) blockObject[1]; 
        	
        	// Make foundation and clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            		
            		this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL+1, w, structureBB);
            		
            		// Only add foundation to interior
            		if (!(u==0 || u==STRUCTURE_WIDTH-1 || w==0 || w==STRUCTURE_DEPTH-1)) {
            			this.func_151554_b(world, biomeCobblestoneBlock, biomeCobblestoneMeta, u, GROUND_LEVEL-1, w, structureBB);
                		this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, u, GROUND_LEVEL, w, structureBB);
            		}
            		else if (world.getBlock(this.getXWithOffset(u, w), this.getYWithOffset(0), this.getZWithOffset(u, w))==Blocks.dirt)
            		{
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-1, w, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL, w, structureBB);
            		}
            		
            }}
            
            // Log corners
            for(int[] uw : new int[][]{{1,1},{1,7},{6,1},{6,7}})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uw[0], 0, uw[1], uw[0], 3, uw[1], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            }            
            
            // Stone front wall
            this.fillWithMetadataBlocks(world, structureBB, 2, 0, 1, 2, 4, 1, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            this.fillWithMetadataBlocks(world, structureBB, 5, 0, 1, 5, 4, 1, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            this.fillWithMetadataBlocks(world, structureBB, 3, 3, 1, 4, 5, 1, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            
            // Stone back wall
            this.fillWithMetadataBlocks(world, structureBB, 2, 1, 7, 5, 4, 7, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            this.fillWithMetadataBlocks(world, structureBB, 3, 5, 7, 4, 5, 7, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            
            // Stone left and right walls
            for (int i : new int[]{1,6}) {
            	this.fillWithMetadataBlocks(world, structureBB, i, 1, 2, i, 3, 6, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            }
            
            // Left windows
            this.fillWithMetadataBlocks(world, structureBB, 1, 2, 2, 1, 2, 6, biomeLogHorAlongBlock, biomeLogHorAlongMeta, biomeLogHorAlongBlock, biomeLogHorAlongMeta, false);
            for (int i : new int[]{3,5}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 1, 2, i, structureBB);
            }
            
            // Back windows
            this.fillWithMetadataBlocks(world, structureBB, 2, 2, 7, 5, 2, 7, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, false);
            this.fillWithMetadataBlocks(world, structureBB, 3, 2, 7, 4, 2, 7, Blocks.glass_pane, 0, Blocks.glass_pane, 0, false);
            
            // Roof
            for (int i=0; i<4; i++) {
            	// Left side meta 0
            	this.fillWithMetadataBlocks(world, structureBB, i, 3+i, 0, i, 3+i, 8, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 0), biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 0), false);
            	// Right side meta 1
            	this.fillWithMetadataBlocks(world, structureBB, 7-i, 3+i, 0, 7-i, 3+i, 8, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 1), biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 1), false);
            }
            // Roof trim
            for (int i=0; i<3; i++) {for (int j : new int[]{0,8}){
            	this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 1)+4, 1+i, 3+i, j, structureBB);
            	this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 0)+4, 6-i, 3+i, j, structureBB);
            }}
            // Trim on the side of the house where the furnace is
            for (int i=0; i<2; i++) {
            	this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, new int[]{2,3}[i])+4, 7, 3, new int[]{2,6}[i], structureBB);
            }
            this.fillWithMetadataBlocks(world, structureBB, 7, 3, 3, 7, 3, 5, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);
            
            // Torches
            for (int i : new int[]{2,5}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(2, this.coordBaseMode), i, 2, 0, structureBB);
            }
            this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(1, this.coordBaseMode), 2, 2, 4, structureBB);
            for (int i : new int[]{2,6}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(3, this.coordBaseMode), 5, 4, i, structureBB);
            }
            
            // Counter
            for (int[] uuww : new int[][]{
            	{2,2,2,5},
            	{2,5,6,6}
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuww[0], 1, uuww[2], uuww[1], 1, uuww[3], smoothStoneBlock, smoothStoneMeta, smoothStoneBlock, smoothStoneMeta, false);
            }
            
            // Fireplace
            for (int i : new int[]{3,5}) {
            	this.fillWithMetadataBlocks(world, structureBB, 5, 1, i, 5, 2, i, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            }
            for (int i=3; i<=5; i++) {
            	this.fillWithMetadataBlocks(world, structureBB, 7, 0, i, 7, 1, i, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            	this.func_151554_b(world, biomeCobblestoneBlock, biomeCobblestoneMeta, 7, GROUND_LEVEL-1, i, structureBB);
            }
            
            this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, 5, 3, 4, structureBB);
            // Stone Stairs - Fireplace and stairs out front
            for (int[] uvwm : new int[][]{
            	// Front Stairs
            	{3,0,0,3},
            	{4,0,0,3},
            	// Furnace exterior
            	{7,2,3,3},
            	{7,2,4,1},
            	{7,2,5,2},
            	// Furnace interior
            	{5,3,3,3},
            	{5,3,5,2},
            	{5,4,4,0},
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwm[3]), uvwm[0], uvwm[1], uvwm[2], structureBB);
            }
            // Chimney
            this.fillWithMetadataBlocks(world, structureBB, 6, 3, 4, 6, 6, 4, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneWallBlock, biomeCobblestoneMeta, false);
            this.placeBlockAtCurrentPosition(world, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, 6, 7, 4, structureBB);
            // Brick
            this.placeBlockAtCurrentPosition(world, biomeBrickBlock, biomeBrickMeta, 5, 0, 4, structureBB);
            this.placeBlockAtCurrentPosition(world, biomeBrickBlock, biomeBrickMeta, 6, 2, 4, structureBB);
            // Furnace - this is a TileEntity and needs to have its meta assigned manually
            for (int[] uvw : new int[][]{{6,1,4}})
            {
                this.placeBlockAtCurrentPosition(world, blastFurnaceBlock, 0, uvw[0], uvw[1], uvw[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvw[0], uvw[2]), this.getYWithOffset(uvw[1]), this.getZWithOffset(uvw[0], uvw[2]), blastFurnaceMeta, 2);
            }
            
            // Clear path for easier entry
            for (int i : new int[]{3, 4})
            {
            	this.clearCurrentPositionBlocksUpwards(world, i, GROUND_LEVEL, -1, structureBB);
            	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, i, GROUND_LEVEL-2, -1, structureBB);
            	// Place dirt if the block to be set as path is empty
            	if (world.isAirBlock(this.getXWithOffset(i, -1), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(i, -1)))
            	{
                	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, i, GROUND_LEVEL-1, -1, structureBB);
            	}
            	StructureVillageVN.setPathSpecificBlock(world, this.start, 0, this.getXWithOffset(i, -1), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(i, -1));
            }
            
            // Doors
            for (int height=0; height<2; height++) {for (int leftright=0; leftright<2; leftright++) {
            	this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(2, this.coordBaseMode, true, leftright==0)[height], 3+leftright, 1+height, 1, structureBB);
            }}
            
            
			//LogHelper.info("armorer spawned at " + this.getXWithOffset(4, 0) + " " + this.getYWithOffset(1) + " " + this.getZWithOffset(4, 0) + " with horizIndex " + this.coordBaseMode);
            
            
    		// Villagers
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	int u = random.nextInt(2)+3;
            	int v = 1;
            	int w = random.nextInt(4)+2;
            	
            	EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 3, 1, 0);
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
                world.spawnEntityInWorld(entityvillager);
            }
            
            return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 3;}
    }
    
    
    
    // --- Big House --- //
    
    public static class PlainsBigHouse1 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public StartVN start;
    	
    	// Here are values to assign to the bounding box
    	private static final int STRUCTURE_WIDTH = 11;
    	private static final int STRUCTURE_HEIGHT = 11;
    	private static final int STRUCTURE_DEPTH = 7;
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	
    	private int averageGroundLevel = -1;
    	
        public PlainsBigHouse1() {}

        public PlainsBigHouse1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            this.start = start;
        }

        public static PlainsBigHouse1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new PlainsBigHouse1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
        		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
        				// Set the bounding box version as this bounding box but with Y going from 0 to 512
        				new StructureBoundingBox(
        						this.boundingBox.minX, this.boundingBox.minZ,
        						this.boundingBox.maxX, this.boundingBox.maxZ),
        				true, (byte)1, this.coordBaseMode);
        		
                if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
            }
        	
            Object[] blockObject;
            blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, start.materialType, start.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, start.materialType, start.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, start.materialType, start.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.stone_stairs, 0, start.materialType, start.biome); Block biomeStoneStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, start.materialType, start.biome); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), start.materialType, start.biome); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1]; // Toward you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), start.materialType, start.biome); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1]; // Perpendicular to you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, start.materialType, start.biome); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_door, 0, start.materialType, start.biome); Block biomeWoodDoorBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(ModObjects.chooseModPathBlock(), 0, start.materialType, start.biome); Block grassPathBlock = (Block) blockObject[0]; int grassPathMeta = (Integer) blockObject[1]; 
        	
        	// Make foundation and clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            		
            		this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
            		
            		// Only add foundation to interior
            		if (!(u==0 || u==STRUCTURE_WIDTH-1 || w==0 || w==STRUCTURE_DEPTH-1)) {
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
                		this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-1, w, structureBB);
            		}
            		else if (world.getBlock(this.getXWithOffset(u, w), this.getYWithOffset(0), this.getZWithOffset(u, w))==Blocks.dirt)
            		{
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
            		}
            		
            }}
            
            // Log frame
            for(int[] uw : new int[][]{{1,1},{1,5},{9,1},{9,5}})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uw[0], 1, uw[1], uw[0], 7, uw[1], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            }            
            this.fillWithMetadataBlocks(world, structureBB, 2, 4, 1, 8, 4, 1, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, false);
            this.fillWithMetadataBlocks(world, structureBB, 2, 4, 5, 8, 4, 5, biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            this.fillWithMetadataBlocks(world, structureBB, 5, 1, 5, 5, 3, 5, biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            for (int i : new int[]{1,5}) {
            	this.fillWithMetadataBlocks(world, structureBB, 5, 5, i, 5, 7, i, biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            }
            for (int i : new int[]{1,9}) {
            	this.fillWithMetadataBlocks(world, structureBB, i, 4, 2, i, 4, 4, biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            }
            
            // Front and back walls
            this.fillWithMetadataBlocks(world, structureBB, 2, 1, 1, 8, 3, 1, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            this.fillWithMetadataBlocks(world, structureBB, 2, 1, 5, 4, 3, 5, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            this.fillWithMetadataBlocks(world, structureBB, 6, 1, 5, 8, 3, 5, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            for (int i : new int[]{1,5}) {
            	this.fillWithMetadataBlocks(world, structureBB, 2, 5, i, 4, 7, i, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
                this.fillWithMetadataBlocks(world, structureBB, 6, 5, i, 8, 7, i, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
            }
                        
            // Left and right walls
            for (int i : new int[]{1,9}) {
            	this.fillWithMetadataBlocks(world, structureBB, i, 1, 2, i, 3, 4, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            	this.fillWithMetadataBlocks(world, structureBB, i, 5, 2, i, 8, 4, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
            	this.placeBlockAtCurrentPosition(world, biomePlankBlock, biomePlankMeta, i, 9, 3, structureBB);
            }
            
            // Windows
            for (int v : new int[]{2, 6})
            {
            	for (int[] uw : new int[][]{
            		{1, 3}, {9, 3},
            		{3, 1}, {7, 1}, {3, 5}, {7, 5},
            		})
                {
            		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uw[0], v, uw[1], structureBB);
                }
            }
            
            // Roof
            for (int i=0; i<=3; i++) {
            	for (int j : i==0 ? new int[]{i} : new int[]{-i, i})
            	{
            		this.fillWithMetadataBlocks(world, structureBB, 0, 10-i, 3+j, 10, 10-i, 3+j, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
            	}
            }
            
            
            // Front door
            for (int height=0; height<=1; height++) {
            	this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(2, this.coordBaseMode, true, true)[height], 5, 1+height, 1, structureBB);
            }
            this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, 5, 0, 1, structureBB);
            
            // Clear path for easier entry
        	this.clearCurrentPositionBlocksUpwards(world, 5, GROUND_LEVEL, 0, structureBB);
        	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, 5, GROUND_LEVEL-2, 0, structureBB);
        	this.placeBlockAtCurrentPosition(world, grassPathBlock, grassPathMeta, 5, GROUND_LEVEL-1, 0, structureBB);
        	// Place dirt if the block to be set as path is empty
        	if (world.isAirBlock(this.getXWithOffset(5, 0), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(5, 0)))
        	{
            	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, 5, GROUND_LEVEL-1, 0, structureBB);
        	}
        	StructureVillageVN.setPathSpecificBlock(world, this.start, 0, this.getXWithOffset(5, 0), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(5, 0));
        	
        	
            // --- Interior --- //
            
            // Stone floor
            this.fillWithMetadataBlocks(world, structureBB, 2, 0, 2, 8, 0, 4, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            
            // Stone stairway
            this.fillWithMetadataBlocks(world, structureBB, 4, 1, 4, 5, 2, 4, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            this.fillWithMetadataBlocks(world, structureBB, 2, 3, 4, 3, 3, 4, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            for (int i=0; i<4; i++) {
            	this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, 1), 3+i, 4-i, 4, structureBB);
            }
            
            // Wood floor
            this.fillWithMetadataBlocks(world, structureBB, 2, 4, 2, 8, 4, 3, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
            this.fillWithMetadataBlocks(world, structureBB, 7, 4, 4, 8, 4, 4, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
            this.placeBlockAtCurrentPosition(world, biomePlankBlock, biomePlankMeta, 2, 4, 4, structureBB);
            
            // Torches
            for (int[] uvwm : new int[][]{
            	{2,3,3,1},
            	{8,3,3,3},
            	{2,7,3,1},
            	{8,7,3,3},
            	{5,6,4,2},
            })
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwm[3], this.coordBaseMode), uvwm[0], uvwm[1], uvwm[2], structureBB);
            }
            
            
            // Beds
            for (int[] uvwm : new int[][]{
            	{2,1,3,2},
            	{7,1,2,3},
            	{3,5,2,1},
            	{7,5,2,3},
            })
            {
            	for (boolean isHead : new boolean[]{false, true})
            	{
            		int orientation = uvwm[3];
            		int u = uvwm[0] + (isHead?(new int[]{0,-1,0,1}[orientation]):0);
            		int v = uvwm[1];
            		int w = uvwm[2] + (isHead?(new int[]{-1,0,1,0}[orientation]):0);
                	ModObjects.setModBedBlock(world,
                			this.getXWithOffset(u, w),
                			this.getYWithOffset(v),
                			this.getZWithOffset(u, w),
                			StructureVillageVN.getBedOrientationMeta(orientation, this.coordBaseMode, isHead),
                			GeneralConfig.decorateVillageCenter ? this.start.townColor2 : 0); // Goes by wool meta
            	}
            }
            
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 8;
        	int chestV = 5;
        	int chestW = 4;
        	int chestO = 3;
        	this.placeBlockAtCurrentPosition(world, Blocks.chest, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo("vn_plains_house");
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
            
    		// Villagers
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
        		for (int[] ia : new int[][]{
        			{3, 1, 3, -1, 0},
        			{8, 1, 3, -1, 0},
        			{2, 5, 3, -1, 0},
        			{8, 5, 3, -1, 0},
        			})
        		{
        			EntityVillager entityvillager = new EntityVillager(world);
        			entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], Math.min(random.nextInt(24001)-12000,0));
        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)this.getYWithOffset(ia[1]) + 1.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
                    		random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(entityvillager);
        		}
            }
            
            return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    
    // --- Small Butcher Shop --- //
    
    public static class PlainsButcherShop1 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public StartVN start;
    	
    	// Here are values to assign to the bounding box
    	private static final int STRUCTURE_WIDTH = 12;
    	private static final int STRUCTURE_HEIGHT = 8;
    	private static final int STRUCTURE_DEPTH = 11;
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	
    	private int averageGroundLevel = -1;
    	
        public PlainsButcherShop1() {}

        public PlainsButcherShop1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            this.start = start;
        }

        public static PlainsButcherShop1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new PlainsButcherShop1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
        		// Use these to re-center the side when determining ground level
            	int expandMinU = 0;
            	int shrinkMaxU = 4;
            	
            	if (this.averageGroundLevel < 0)
                {
            		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
            				// Set the bounding box version as this bounding box but with Y going from 0 to 512
            				new StructureBoundingBox(
            						// Modified to center onto front of house
            						this.boundingBox.minX+(this.coordBaseMode%2==0?expandMinU:0), this.boundingBox.minZ+(this.coordBaseMode%2==0?0:expandMinU),
            						this.boundingBox.maxX-(this.coordBaseMode%2==0?shrinkMaxU:0), this.boundingBox.maxZ-(this.coordBaseMode%2==0?0:shrinkMaxU)),
            				true, (byte)1, this.coordBaseMode);
            		
                    if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void

                    this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
                }
            }
        	
            
            Object[] blockObject;
            blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, start.materialType, start.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, start.materialType, start.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, start.materialType, start.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.stone_stairs, 0, start.materialType, start.biome); Block biomeStoneStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, start.materialType, start.biome); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.oak_stairs, 0, start.materialType, start.biome); Block biomeWoodStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_door, 0, start.materialType, start.biome); Block biomeWoodDoorBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_pressure_plate, 0, start.materialType, start.biome); Block biomeWoodPressurePlateBlock = (Block)blockObject[0]; int biomeWoodPressurePlateMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, start.materialType, start.biome); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), start.materialType, start.biome); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1]; // Toward you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, start.materialType, start.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = ModObjects.chooseModSmokerBlock(3, this.coordBaseMode); Block smokerBlock = (Block) blockObject[0];
        	
        	
        	// Make foundation and clear space above
        	// House proper
            for (int u = 0; u < STRUCTURE_WIDTH-4; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            		
            		this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL+1, w, structureBB);
            		
            		// Only add foundation to interior
            		if (u>=1 && u<=6 && w>=1 && w<=9) {
            			this.func_151554_b(world, biomeCobblestoneBlock, biomeCobblestoneMeta, u, GROUND_LEVEL-1, w, structureBB);
                		this.placeBlockAtCurrentPosition(world, biomePlankBlock, biomePlankMeta, u, GROUND_LEVEL, w, structureBB);
            		}
            		else if (world.getBlock(this.getXWithOffset(u, w), this.getYWithOffset(0), this.getZWithOffset(u, w))==Blocks.dirt)
            		{
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-1, w, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL, w, structureBB);
            		}
            }}
            // Outside field
            for (int u = 7; u <= 11; ++u) {for (int w = 3; w <= 9; ++w) {
    			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-1, w, structureBB);
        		this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL, w, structureBB);
        		this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL+1, w, structureBB);
            }}
            
            // Log frame
            for (int u : new int[]{1,6}) {for (int w : new int[]{1,9}) {
            	this.fillWithMetadataBlocks(world, structureBB, u, 0, w, u, 3, w, biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            }}
            this.fillWithMetadataBlocks(world, structureBB, 1, 0, 5, 1, 2, 5, biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            for (int[] uw : new int[][]{
            	{2,1}, {5,1}, // Front wall
            	{2,9}, {5,9}, // Back wall
            	{1,2}, {1,4}, {1,6}, {1,8}, // Left wall
            })
            {
            	this.placeBlockAtCurrentPosition(world, biomeLogVertBlock, biomeLogVertMeta, uw[0], 2, uw[1], structureBB);
            }
            for (int w : new int[]{2,5,6}) {this.placeBlockAtCurrentPosition(world, biomeLogHorAlongBlock, biomeLogHorAlongMeta, 6, 2, w, structureBB);}
            
            // Stone front and back walls
            for (int w : new int[]{1,9})
            {
            	this.fillWithMetadataBlocks(world, structureBB, 2, 0, w, 5, 1, w, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
                this.fillWithMetadataBlocks(world, structureBB, 2, 3, w, 5, 4, w, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
                this.fillWithMetadataBlocks(world, structureBB, 3, 5, w, 4, 5, w, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            }
            // Stone left wall
            this.fillWithMetadataBlocks(world, structureBB, 1, 0, 2, 1, 1, 4, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            this.fillWithMetadataBlocks(world, structureBB, 1, 0, 6, 1, 1, 8, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            // Stone right wall
            this.fillWithMetadataBlocks(world, structureBB, 6, 0, 2, 6, 1, 6, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            this.fillWithMetadataBlocks(world, structureBB, 6, 0, 8, 6, 2, 8, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            this.fillWithMetadataBlocks(world, structureBB, 6, 0, 7, 7, 0, 7, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            
            // Windows
        	for (int[] uw : new int[][]{
        		{1, 3}, {1, 7}, // Left wall
        		{6, 3}, {6, 4}, // Right wall, facing yard
        		{3, 9}, {4, 9}, // Back wall
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uw[0], 2, uw[1], structureBB);
            }
            
            // Roof
            for (int i=0; i<4; i++) {
            	// Left side meta 0
            	this.fillWithMetadataBlocks(world, structureBB, i, 3+i, 0, i, 3+i, STRUCTURE_DEPTH-1, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 0), biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 0), false);
            	// Right side meta 1
            	this.fillWithMetadataBlocks(world, structureBB, 7-i, 3+i, 0, 7-i, 3+i, STRUCTURE_DEPTH-1, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 1), biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 1), false);
            }
            // Interior roof
            for (int i=0; i<3; i++) {
            	// Left side meta 0
            	this.fillWithMetadataBlocks(world, structureBB, 1+i, 3+i, 2, 1+i, 3+i, STRUCTURE_DEPTH-3, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
            	// Right side meta 1
            	this.fillWithMetadataBlocks(world, structureBB, 6-i, 3+i, 2, 6-i, 3+i, STRUCTURE_DEPTH-3, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
            }
            // Roof trim
            for (int i=0; i<3; i++) {for (int j : new int[]{0,STRUCTURE_DEPTH-1}){
            	this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 1)+4, 1+i, 3+i, j, structureBB);
            	this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 0)+4, 6-i, 3+i, j, structureBB);
            }}
            
            
            // Yard sard
            
            // Pen rim is vertical logs with fences above
            Block[] penrimBlock = new Block[]{biomeLogVertBlock, biomeFenceBlock};
            int[] penrimMeta = new int[]{biomeLogVertMeta, 0};
            for (int i=0; i<=1; i++)
            {
            	for (int w : new int[]{3,9}) {
                	this.fillWithMetadataBlocks(world, structureBB, 7, i, w, 10, i, w, penrimBlock[i], penrimMeta[i], penrimBlock[i], penrimMeta[i], false);
                }
                this.fillWithMetadataBlocks(world, structureBB, 11, i, 3, 11, i, 9, penrimBlock[i], penrimMeta[i], penrimBlock[i], penrimMeta[i], false);
            }
            
            
            // Hay bales
            this.fillWithMetadataBlocks(world, structureBB, 7, 1, 5, 7, 1, 6, Blocks.hay_block, 0, Blocks.hay_block, 0, false);
            
            
            // Torches
            for (int[] uvwm : new int[][]{
            	{2,2,0,2}, {5,2,0,2}, {0,2,5,3}, // House exterior
            	{2,3,3,1}, {5,3,7,3}, // House interior
            	{11,2,3,-1}, {11,2,9,-1}, // Animal pen
            })
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwm[3], this.coordBaseMode), uvwm[0], uvwm[1], uvwm[2], structureBB);
            }
            
            
            // Counter
            this.fillWithMetadataBlocks(world, structureBB, 2, 0, 6, 4, 0, 8, Blocks.double_stone_slab, 0, Blocks.double_stone_slab, 0, false);
            this.fillWithMetadataBlocks(world, structureBB, 2, 1, 7, 3, 1, 7, Blocks.double_stone_slab, 0, Blocks.double_stone_slab, 0, false);
            
            
            // Fireplace
            // Furnace - this is a TileEntity and needs to have its meta assigned manually
            for (int[] uvwo : new int[][]{{5,1,8,2}})
            {
                this.placeBlockAtCurrentPosition(world, smokerBlock, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2]), StructureVillageVN.chooseFurnaceMeta(uvwo[3], this.coordBaseMode), 2);
            }
            // Chimney
            this.placeBlockAtCurrentPosition(world, Blocks.cobblestone, 0, 5, 5, 8, structureBB);
            this.fillWithMetadataBlocks(world, structureBB, 5, 2, 8, 5, 4, 8, Blocks.cobblestone_wall, 0, Blocks.cobblestone_wall, 0, false);
            this.fillWithMetadataBlocks(world, structureBB, 5, 6, 8, 5, 7, 8, Blocks.cobblestone_wall, 0, Blocks.cobblestone_wall, 0, false);
            
            // Table
            this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, 2, 1, 3, structureBB);
            this.placeBlockAtCurrentPosition(world, biomeWoodPressurePlateBlock, biomeWoodPressurePlateMeta, 2, 2, 3, structureBB);
            
            // 0: nuffin
            // 1: poppy
            // 2: dandelion
            int flowernumber = random.nextInt(2)+1;
            
            // Flower pot on table
            this.placeBlockAtCurrentPosition(world, biomePlankBlock, biomePlankMeta, 5, 1, 2, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.flower_pot, flowernumber, 5, 2, 2, structureBB);
        	
            // Chairs
            for (int wo[]: new int[][]{
            		{2,2},
            		{4,3},
            		}) {
            	this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, wo[1]), 2, 1, wo[0], structureBB);
            }
            
            
            // Front stairs
            this.fillWithMetadataBlocks(world, structureBB, 3, 0, 0, 4, 0, 0, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 3), biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 3), false);
            
            
            // Doors
            for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
            	{3, 1, 1, 2, 1, 1},
            	{4, 1, 1, 2, 1, 0},
            	{6, 1, 7, 1, 1, 1},
            })
            {
            	for (int height=0; height<=1; height++)
            	{
            		this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
            				uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
            	}
            }
            
            
            // Clear path for easier entry
            for (int i : new int[]{3, 4})
            {
            	this.clearCurrentPositionBlocksUpwards(world, i, GROUND_LEVEL, -1, structureBB);
            	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, i, GROUND_LEVEL-2, -1, structureBB);
            	// Place dirt if the block to be set as path is empty
            	if (world.isAirBlock(this.getXWithOffset(i, -1), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(i, -1)))
            	{
                	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, i, GROUND_LEVEL-1, -1, structureBB);
            	}
            	StructureVillageVN.setPathSpecificBlock(world, this.start, 0, this.getXWithOffset(i, -1), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(i, -1));
            }
            
            //LogHelper.info("butcher spawned at " + this.getXWithOffset(4, 0) + " " + this.getYWithOffset(1) + " " + this.getZWithOffset(4, 0) + " with horizIndex " + this.coordBaseMode);
            
    		// Entities
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	// Animals
            	for (int[] uvw : new int[][]{
        			{9, 1, 6},
        			})
        		{
                	EntityLiving animal = StructureVillageVN.getVillageAnimal(world, random, false);
                    animal.setLocationAndAngles((double)this.getXWithOffset(uvw[0], uvw[2]) + 0.5D, (double)this.getYWithOffset(uvw[1]) + 0.5D, (double)this.getZWithOffset(uvw[0], uvw[2]) + 0.5D, random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(animal);
                    
                    // Dirt block underneath
                    this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, uvw[0], uvw[1]-1, uvw[2], structureBB);
        		}
            	
            	// Villager
            	int u = random.nextInt(3)+3;
            	int v = 1;
            	int w = random.nextInt(4)+3;
            	
            	EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 4, 1, 0);
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
                world.spawnEntityInWorld(entityvillager);
            }
            
            return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 4;}
    }
    
    
    
    // --- Large Butcher Shop --- //
    
    public static class PlainsButcherShop2 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public StartVN start;
    	
    	// Here are values to assign to the bounding box
    	private static final int STRUCTURE_WIDTH = 7;
    	private static final int STRUCTURE_HEIGHT = 12;
    	private static final int STRUCTURE_DEPTH = 15;
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	
    	private int averageGroundLevel = -1;
    	
        public PlainsButcherShop2() {}

        public PlainsButcherShop2(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            this.start = start;
        }

        public static PlainsButcherShop2 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new PlainsButcherShop2(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
        		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
        				// Set the bounding box version as this bounding box but with Y going from 0 to 512
        				new StructureBoundingBox(
        						this.boundingBox.minX, this.boundingBox.minZ,
        						this.boundingBox.maxX, this.boundingBox.maxZ),
        				true, (byte)1, this.coordBaseMode);
        		
                if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
            }
        	
            Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, start.materialType, start.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, start.materialType, start.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, start.materialType, start.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.stone_stairs, 0, start.materialType, start.biome); Block biomeStoneStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, start.materialType, start.biome); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.oak_stairs, 0, start.materialType, start.biome); Block biomeWoodStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_door, 0, start.materialType, start.biome); Block biomeWoodDoorBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, start.materialType, start.biome); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), start.materialType, start.biome); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1]; // Toward you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), start.materialType, start.biome); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1]; // Perpendicular to you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, start.materialType, start.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = ModObjects.chooseModSmokerBlock(3, this.coordBaseMode); Block smokerBlock = (Block) blockObject[0];
        	
        	// Make foundation and clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            		
        		this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
        		
        		// Only add foundation to interior
        		if (!(u==0 || u==STRUCTURE_WIDTH-1 || w==0 || w==STRUCTURE_DEPTH)) {
        			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
            		this.placeBlockAtCurrentPosition(world, w<=8?biomeDirtBlock:biomeGrassBlock, w<=8?biomeDirtMeta:biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
        		else if (world.getBlock(this.getXWithOffset(u, w), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(u, w))==Blocks.dirt)
        		{
        			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
        			this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
            }}
            
            
            // Decor
            for (int decorUVW[] : new int[][]{
            	{6, 0, 5, 0},
            	{0, 0, 14, 1},
            	})
            {
                // Add foundations
            	this.placeBlockAtCurrentPosition(world, decorUVW[3]==1 ? biomeGrassBlock : biomeDirtBlock, decorUVW[3]==1 ? biomeGrassMeta : biomeDirtMeta, decorUVW[0], decorUVW[1], decorUVW[2], structureBB);
        		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, decorUVW[0], decorUVW[1]-1, decorUVW[2], structureBB);
        		
                // The actual decor
        		for (int j=0; j<decorUVW.length; j++)
                {
        			int decorHeightY = decorUVW[1]+1;
        			
        			// Set random seed
                	Random randomFromXYZ = new Random();
                	randomFromXYZ.setSeed(
        					world.getSeed() +
        					FunctionsVN.getUniqueLongForXYZ(
        							this.getXWithOffset(decorUVW[0], decorUVW[2]),
        							this.getYWithOffset(decorHeightY),
        							this.getZWithOffset(decorUVW[0], decorUVW[2])
        							)
                			);
                	
                	// Get ground level
                	if (this.decorHeightY.size()<(j+1))
                	{
                		// There are fewer stored ground levels than this decor number, so this is being generated for the first time.
                		// Add new ground level
                		//decorHeightY = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(uvw[0], uvw[2]), this.getZWithOffset(uvw[0], uvw[2]))-this.boundingBox.minY;
                		this.decorHeightY.add(decorHeightY);
                	}
                	else
                	{
                		// There is already (presumably) a value for this ground level, so this decor is being multiply generated.
                		// Retrieve ground level
                		decorHeightY = this.decorHeightY.get(j);
                	}
                	
                	
                	// Generate decor
                	ArrayList<BlueprintData> decorBlueprint = getRandomPlainsDecorBlueprint(this.start, this.coordBaseMode, randomFromXYZ);
                	
                	for (BlueprintData b : decorBlueprint)
                	{
                		// Place block indicated by blueprint
                		this.placeBlockAtCurrentPosition(world, b.getBlock(), b.getMeta(), decorUVW[0]+b.getUPos(), decorHeightY+b.getVPos(), decorUVW[2]+b.getWPos(), structureBB);
                		
                		// Fill below if flagged
                		if ((b.getfillFlag()&1)!=0)
                		{
                			this.func_151554_b(world, b.getBlock(), b.getMeta(), decorUVW[0]+b.getUPos(), decorHeightY+b.getVPos()-1, decorUVW[2]+b.getWPos(), structureBB);
                		}
                		
                		// Clear above if flagged
                		if ((b.getfillFlag()&2)!=0)
                		{
                			this.clearCurrentPositionBlocksUpwards(world, decorUVW[0]+b.getUPos(), decorHeightY+b.getVPos()+1, decorUVW[2]+b.getWPos(), structureBB);
                		}            		
                	}
                }
            }
            
            // Log corners
            for (int u : new int[]{1,5})
            {
            	this.fillWithMetadataBlocks(world, structureBB, u, 1, 1, u, 4, 1, biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            	this.fillWithMetadataBlocks(world, structureBB, u, 1, 8, u, 8, 8, biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            	this.fillWithMetadataBlocks(world, structureBB, u, 5, 4, u, 8, 4, biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            }
            
            // Log along
            for (int u : new int[]{1,5}) {for (int v : new int[]{0,2}) {
                	this.fillWithMetadataBlocks(world, structureBB, u, v+2, 2, u, v+2, 7, biomeLogHorAlongBlock, biomeLogHorAlongMeta, biomeLogHorAlongBlock, biomeLogHorAlongMeta, false);
                	this.fillWithMetadataBlocks(world, structureBB, u, v+6, 5, u, v+6, 7, biomeLogHorAlongBlock, biomeLogHorAlongMeta, biomeLogHorAlongBlock, biomeLogHorAlongMeta, false);
            }}
            
            // Log across
            for (int[] vw : new int[][]{
            	{4,1},
            	{4,8},
            	{6,4},
            	{8,4},
            	{6,8},
            	{8,8},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, 2, vw[0], vw[1], 4, vw[0], vw[1], biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, false);
            }
            
            // Floors
            // Stone first story
            this.fillWithMetadataBlocks(world, structureBB, 2, 0, 2, 4, 0, 8, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            this.fillWithMetadataBlocks(world, structureBB, 2, 0, 4, 2, 0, 5, biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);
            // Wood second story
            this.fillWithMetadataBlocks(world, structureBB, 3, 4, 5, 4, 4, 7, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
            
            
            // Stone front wall
            this.fillWithMetadataBlocks(world, structureBB, 2, 1, 1, 4, 3, 1, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            
            // Stone back wall
            this.fillWithMetadataBlocks(world, structureBB, 2, 1, 8, 4, 3, 8, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            
            // Left and right walls
            for (int u : new int[]{1,5}) {for (int v : new int[]{1,3}) {
            	this.fillWithMetadataBlocks(world, structureBB, u, v, 2, u, v, 7, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            }}
            
            // Second floor
            for (int u : new int[]{1,5}) {for (int v : new int[]{5,7}) {
            	this.fillWithMetadataBlocks(world, structureBB, u, v, 5, u, v, 7, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            }}
            for (int w : new int[]{4,8}) {for (int v : new int[]{5,7,9}) {
            	this.fillWithMetadataBlocks(world, structureBB, 2, v, w, 4, v, w, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            }}
            
            
            // Windows
        	for (int[] uvw : new int[][]{
        		{1, 2, 3}, {1, 2, 6}, {5, 2, 3}, {5, 2, 6}, // First floor
        		{1, 6, 6}, {5, 6, 6}, {3, 6, 4}, {3, 6, 8}, // Second floor
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            // Roof
        	// First floor
        	this.fillWithMetadataBlocks(world, structureBB, 2, 5, 0, 4, 5, 3, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 1, 5, 0, 1, 5, 3, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 0), biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 0), false);
        	this.fillWithMetadataBlocks(world, structureBB, 5, 5, 0, 5, 5, 3, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 1), biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 1), false);
        	this.fillWithMetadataBlocks(world, structureBB, 0, 4, 0, 0, 4, 9, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 0), biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 0), false);
        	this.fillWithMetadataBlocks(world, structureBB, 6, 4, 0, 6, 4, 9, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 1), biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 1), false);
        	this.fillWithMetadataBlocks(world, structureBB, 1, 4, 9, 5, 4, 9, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 2), biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 1), false);
        	// Second floor
        	this.fillWithMetadataBlocks(world, structureBB, 3, 10, 3, 3, 10, 9, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
        	for (int i=0; i<=2; i++) {
        		this.fillWithMetadataBlocks(world, structureBB, 2-i, 10-i, 3, 2-i, 10-i, 9, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 0), biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 0), false);
        		this.fillWithMetadataBlocks(world, structureBB, 4+i, 10-i, 3, 4+i, 10-i, 9, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 1), biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 1), false);
        	}
        	
        	// Various wooden stairs
        	for (int[] uvwo : new int[][]{
        		{1, 4, 0, 5}, {5, 4, 0, 4}, 
        		{1, 8, 3, 5}, {5, 8, 3, 4}, {2, 9, 3, 5}, {4, 9, 3, 4},
        		{1, 8, 9, 5}, {5, 8, 9, 4}, {2, 9, 9, 5}, {4, 9, 9, 4},
        		{3, 4, 4, 7}, {4, 4, 4, 7}, // Ceiling trim
        		{2, 1, 6, 1}, {2, 1, 7, 1}, // Bench
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
            }
        	
        	
        	// Fences
        	for (int u : new int[]{1,5})
        	{
        		this.fillWithMetadataBlocks(world, structureBB, u, 1, 9, u, 1, 13, biomeFenceBlock, 0, biomeFenceBlock, 0, false);
        	}
        	this.fillWithMetadataBlocks(world, structureBB, 1, 1, 14, 5, 1, 14, biomeFenceBlock, 0, biomeFenceBlock, 0, false);
        	
            // Torches
        	for (int u : new int[]{1,5})
        	{
        		this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, u, 2, 14, structureBB);
        	}
            for (int[] uvwo : new int[][]{
            	{3,3,0,2},
            	{3,3,9,0},
            	{3,8,3,2},
            	{3,3,2,0},
            	{3,3,7,2},
            	{3,7,7,2},
            	{3,7,5,0},
            	}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
            
            // Counter
            this.fillWithMetadataBlocks(world, structureBB, 3, 0, 3, 4, 0, 3, Blocks.double_stone_slab, 0, Blocks.double_stone_slab, 0, false);
            this.placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 3, 0, 4, structureBB);
            this.fillWithMetadataBlocks(world, structureBB, 3, 0, 5, 4, 0, 5, Blocks.double_stone_slab, 0, Blocks.double_stone_slab, 0, false);
            this.placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 4, 1, 4, structureBB);
            
            // Chimney
            this.fillWithMetadataBlocks(world, structureBB, 4, 6, 5, 4, 11, 5, Blocks.cobblestone_wall, 0, Blocks.cobblestone_wall, 0, false);
            // Furnace - this is a TileEntity and needs to have its meta assigned manually
            for (int[] uvwo : new int[][]{{4,5,5,0}})
            {
                this.placeBlockAtCurrentPosition(world, smokerBlock, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2]), StructureVillageVN.chooseFurnaceMeta(uvwo[3], this.coordBaseMode), 2);
            }
            // Roof plug
            this.placeBlockAtCurrentPosition(world, Blocks.cobblestone, 0, 4, 10, 5, structureBB);
            
            // Wooden stairway
            for (int[] vw : new int[][]{
            	{1,5},
            	{3,7},
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomePlankBlock, biomePlankMeta, 2, vw[0], vw[1], structureBB);
            }
            for (int i=0; i<=3; i++) {
            	this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 3), 2, 1+i, 4+i, structureBB);
            }
            
            
            this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, 3, 0, 1, structureBB);
            
            // Doors
            for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
            	{3, 1, 1, 2, 1, 0},
            	{3, 1, 8, 0, 1, 0},
            })
            {
            	for (int height=0; height<=1; height++)
            	{
            		this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
            				uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
            	}
            }
            
            // Clear path for easier entry
            this.clearCurrentPositionBlocksUpwards(world, 3, 1, 0, structureBB);
        	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, 3, GROUND_LEVEL-2, 0, structureBB);
        	// Place dirt if the block to be set as path is empty
        	if (world.isAirBlock(this.getXWithOffset(3, 0), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(3, 0)))
        	{
            	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, 3, GROUND_LEVEL-1, 0, structureBB);
        	}
        	StructureVillageVN.setPathSpecificBlock(world, this.start, 0, this.getXWithOffset(3, 0), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(3, 0));
            
			//LogHelper.info("butcher 2 spawned at " + this.getXWithOffset(4, 0) + " " + this.getYWithOffset(1) + " " + this.getZWithOffset(4, 0) + " with horizIndex " + this.coordBaseMode);
            
    		// Entities
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	// Animals
            	for (int[] uvw : new int[][]{
        			{3, 1, 12},
        			})
        		{
                	EntityLiving animal = StructureVillageVN.getVillageAnimal(world, random, false);
                    animal.setLocationAndAngles((double)this.getXWithOffset(uvw[0], uvw[2]) + 0.5D, (double)this.getYWithOffset(uvw[1]) + 0.5D, (double)this.getZWithOffset(uvw[0], uvw[2]) + 0.5D, random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(animal);
                    
                    // Dirt block underneath
                    this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, uvw[0], uvw[1]-1, uvw[2], structureBB);
        		}
            	
            	// Villager
            	boolean levelflag = random.nextBoolean();
            	int u = levelflag ? random.nextInt(2)+3 : 3;
            	int v = levelflag ? 5 : 1;
            	int w = levelflag ? random.nextInt(2)+6 : random.nextInt(6)+2;
            	
            	EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 4, 1, 0);
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
                world.spawnEntityInWorld(entityvillager);
            }
            
            return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 4;}
    }
    
    
    
    // --- Cartographer House --- //
    
    public static class PlainsCartographer1 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public StartVN start;
    	
    	// Here are values to assign to the bounding box
    	private static final int STRUCTURE_WIDTH = 7;
    	private static final int STRUCTURE_HEIGHT = 8;
    	private static final int STRUCTURE_DEPTH = 10;
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	
    	private int averageGroundLevel = -1;
    	
        public PlainsCartographer1() {}

        public PlainsCartographer1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            this.start = start;
        }

        public static PlainsCartographer1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new PlainsCartographer1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
        		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
        				// Set the bounding box version as this bounding box but with Y going from 0 to 512
        				new StructureBoundingBox(
        						this.boundingBox.minX, this.boundingBox.minZ,
        						this.boundingBox.maxX, this.boundingBox.maxZ),
        				true, (byte)1, this.coordBaseMode);
        		
                if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
            }
        	
            Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, start.materialType, start.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, start.materialType, start.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, start.materialType, start.biome); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, start.materialType, start.biome); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, start.materialType, start.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.oak_stairs, 0, start.materialType, start.biome); Block biomeWoodStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_slab, 0, start.materialType, start.biome); Block biomeWoodSlabBlock = (Block)blockObject[0]; int biomeWoodSlabMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_door, 0, start.materialType, start.biome); Block biomeWoodDoorBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(ModObjects.chooseModPathBlock(), 0, start.materialType, start.biome); Block grassPathBlock = (Block) blockObject[0]; int grassPathMeta = (Integer) blockObject[1]; 
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_pressure_plate, 0, start.materialType, start.biome); Block biomeWoodPressurePlateBlock = (Block)blockObject[0]; int biomeWoodPressurePlateMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, start.materialType, start.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = ModObjects.chooseModCartographyTable(); Block cartographyTableBlock = (Block) blockObject[0]; int cartographyTableMeta = (Integer) blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.trapdoor, 0, start.materialType, start.biome); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
        	
        	// Make foundation and clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            		
        		this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
        		
        		// Only add foundation to interior
        		if (!(u==0 || u==STRUCTURE_WIDTH-1 || w==0 || w==STRUCTURE_DEPTH-1)) {
        			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
            		this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
        		else if (world.getBlock(this.getXWithOffset(u, w), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(u, w))==Blocks.dirt)
        		{
        			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
        			this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
            }}
            
            // Log Frame
            // Corners
            for (int[] uw : new int[][]{
            	{1,2}, {5,2},
            	{1,8}, {5,8},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uw[0], 1, uw[1], uw[0], 4, uw[1], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            }
            for (int u : new int[]{1, 5})
            {
            	this.fillWithMetadataBlocks(world, structureBB, u, 4, 3, u, 4, 7, biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            }
            for (int w : new int[]{2, 8})
            {
            	this.fillWithMetadataBlocks(world, structureBB, 2, 4, w, 4, 4, w, biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            }
            
            // Floor
            this.fillWithMetadataBlocks(world, structureBB, 2, 0, 3, 4, 0, 7, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
            this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, 3, 0, 2, structureBB);
            
            // Left and right walls
            for (int u : new int[]{1,5}) {
            	this.fillWithMetadataBlocks(world, structureBB, u, 1, 3, u, 3, 7, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            }
            // Front and back walls
            for (int w : new int[]{2,8}) {
            	this.fillWithMetadataBlocks(world, structureBB, 2, 1, w, 4, 3, w, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            	this.fillWithMetadataBlocks(world, structureBB, 2, 5, w, 4, 5, w, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            }
            
            
            // Windows
        	for (int[] uvw : new int[][]{
        		{1, 2, 4}, {1, 2, 6}, // Left wall
        		{5, 2, 4}, {5, 2, 6}, // Right wall
        		{3, 2, 8}, // Back wall
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            // Roof
        	// First floor
        	for (int i=0; i<=2; i++) {
        		this.fillWithMetadataBlocks(world, structureBB, 2-i, 6-i, 1, 2-i, 6-i, 9, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 0), biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 0), false);
        		this.fillWithMetadataBlocks(world, structureBB, 4+i, 6-i, 1, 4+i, 6-i, 9, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 1), biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 1), false);
        	}
        	this.fillWithMetadataBlocks(world, structureBB, 3, 6, 1, 3, 6, 9, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 3, 7, 1, 3, 7, 9, biomeWoodSlabBlock, biomeWoodSlabMeta, biomeWoodSlabBlock, biomeWoodSlabMeta, false);
        	
        	// Various wooden stairs
        	for (int[] uvwo : new int[][]{
        		{1, 4, 1, 5}, {5, 4, 1, 4}, {2, 5, 1, 5}, {4, 5, 1, 4}, // Front trim
        		{1, 4, 9, 5}, {5, 4, 9, 4}, {2, 5, 9, 5}, {4, 5, 9, 4}, // Rear trim
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
            }
        	
            // Torches
        	for (int u : new int[]{1,5})
        	{
        		this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, u, 2, 14, structureBB);
        	}
            for (int[] uvwo : new int[][]{
            	{3,4,1,2}, {3,4,3,0}, // Front wall
            	{3,4,7,2}, // Back wall
            	}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
            
            
            // Doors
            for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
            	{3, 1, 2, 2, 1, 1},
            })
            {
            	for (int height=0; height<=1; height++)
            	{
            		this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
            				uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
            	}
            }
            
            
            // --- Interior --- //
            
            // Table
            this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, 2, 1, 3, structureBB);
            this.placeBlockAtCurrentPosition(world, biomeWoodPressurePlateBlock, biomeWoodPressurePlateMeta, 2, 2, 3, structureBB);
            // Cartography Table
            this.placeBlockAtCurrentPosition(world, cartographyTableBlock, cartographyTableMeta, 3, 1, 6, structureBB);
            // Carpet
            int[] carpetU = new int[]{2,2,2,3,4,4,4,3};
            int[] carpetW = new int[]{5,6,7,7,7,6,5,5};
            for (int i=0; i<=7; i++)
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.carpet, i%2==0?
            			(GeneralConfig.decorateVillageCenter ? this.start.townColor : 4):(GeneralConfig.decorateVillageCenter ? this.start.townColor2 : 0),
            			carpetU[i], 1, carpetW[i], structureBB);
            }
            
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 4;
        	int chestV = 1;
        	int chestW = 3;
        	int chestO = 3;
        	this.placeBlockAtCurrentPosition(world, Blocks.chest, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo("vn_cartographer");
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
            
        	
        	// Flower planter out front
        	this.placeBlockAtCurrentPosition(world, grassPathBlock, grassPathMeta, 3, 0, 1, structureBB);
        	
            // Trapdoor rim
        	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, this.coordBaseMode%2==0 ? 6 : 4, 0, 1, 1, structureBB); // Left
        	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, this.coordBaseMode%2==0 ? 7 : 5, 6, 1, 1, structureBB); // Right
        	for (int u : new int[]{1,2,4,5})
        	{
        		this.placeBlockAtCurrentPosition(world, Blocks.grass, 0, u, 1, 1, structureBB); // Sod
        		this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, (new int[]{4, 7, 5, 6})[this.coordBaseMode], u, 1, 0, structureBB); // Front face
        	}
        	
        	// Flowers on top
        	for (int f : new int[]{1,2,4,5})
        	{
        		int flowerindex = random.nextInt(10 + (Block.getBlockFromName(ModObjects.flowerUTD)==null ? 0 : 2));
        		// 0-8 is "red" flower
        		// 9 is a basic yellow flower
        		// 10-11 are the flowers from UpToDateMod
        		Block flowerblock = flowerindex == 9 ? Blocks.yellow_flower : flowerindex > 9 ? Block.getBlockFromName(ModObjects.flowerUTD) : Blocks.red_flower;
        		int flowermeta = new int[]{0,1,2,3,4,5,6,7,8,0,0,1}[flowerindex];
        		
        		this.placeBlockAtCurrentPosition(world, flowerblock, flowermeta, f, 2, 1, structureBB);
        	}
        	
            
            // Clear path for easier entry
            this.clearCurrentPositionBlocksUpwards(world, 3, GROUND_LEVEL, 0, structureBB);
        	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, 3, GROUND_LEVEL-2, 0, structureBB);
        	// Place dirt if the block to be set as path is empty
        	if (world.isAirBlock(this.getXWithOffset(3, 0), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(3, 0)))
        	{
            	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, 3, GROUND_LEVEL-1, 0, structureBB);
        	}
        	StructureVillageVN.setPathSpecificBlock(world, this.start, 0, this.getXWithOffset(3, 0), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(3, 0));
            
			//LogHelper.info("Cartographer house spawned at " + this.getXWithOffset(4, 0) + " " + this.getYWithOffset(1) + " " + this.getZWithOffset(4, 0) + " with horizIndex " + this.coordBaseMode);
            
    		// Entities
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	// Villager
            	int u = random.nextInt(3)+2;
            	int v =  1;
            	int w = random.nextInt(2)+4;
            	
            	EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 1, 2, 0);
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
                world.spawnEntityInWorld(entityvillager);
            }
            
            return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 1;}
    }
    
    
    
    // --- Fisher Cottage --- //
    
    public static class PlainsFisherCottage1 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public StartVN start;
    	
    	// Here are values to assign to the bounding box
    	private static final int STRUCTURE_WIDTH = 10;
    	private static final int STRUCTURE_HEIGHT = 9;
    	private static final int STRUCTURE_DEPTH = 11;
    	private static final int GROUND_LEVEL = 2; // Spaces above the bottom of the structure considered to be "ground level"
    	
    	private int averageGroundLevel = -1;
    	
        public PlainsFisherCottage1() {}

        public PlainsFisherCottage1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            this.start = start;
        }

        public static PlainsFisherCottage1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new PlainsFisherCottage1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
        		// Use these to re-center the side when determining ground level
            	int expandMinU = 2;
            	int shrinkMaxU = 1;
            	
            	if (this.averageGroundLevel < 0)
                {
            		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
            				// Set the bounding box version as this bounding box but with Y going from 0 to 512
            				new StructureBoundingBox(
            						// Modified to center onto front of house
            						this.boundingBox.minX+(this.coordBaseMode%2==0?expandMinU:0), this.boundingBox.minZ+(this.coordBaseMode%2==0?0:expandMinU),
            						this.boundingBox.maxX-(this.coordBaseMode%2==0?shrinkMaxU:0), this.boundingBox.maxZ-(this.coordBaseMode%2==0?0:shrinkMaxU)),
            				true, (byte)1, this.coordBaseMode);
            		
                    if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void

                    this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
                }
            }
        	
            Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, start.materialType, start.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, start.materialType, start.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.oak_stairs, 0, start.materialType, start.biome); Block biomeWoodStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, start.materialType, start.biome); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), start.materialType, start.biome); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1]; // Toward you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), start.materialType, start.biome); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1]; // Perpendicular to you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, start.materialType, start.biome); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_slab, 0, start.materialType, start.biome); Block biomeWoodSlabBlock = (Block)blockObject[0]; int biomeWoodSlabMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_door, 0, start.materialType, start.biome); Block biomeWoodDoorBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.trapdoor, 0, start.materialType, start.biome); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, start.materialType, start.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, start.materialType, start.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.stone_stairs, 0, start.materialType, start.biome); Block biomeStoneStairsBlock = (Block)blockObject[0];
        	
        	// Clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            	this.clearCurrentPositionBlocksUpwards(world, u, 2, w, structureBB);
            }}
            
            // Make foundation
        	int[][] grassWidths = new int[][]{
    			{4,7},
    			{3,7},
    			{2,7},
    			{1,8},
    			{0,9},
    			{0,9},
    			{0,9},
    			{1,9},
    			{2,9},
    			{4,7},
    			{4,7},
        	};
        	for (int w=0; w < STRUCTURE_DEPTH; w++)
            {
        		for (int u=grassWidths[w][0]; u<=grassWidths[w][1]; u++)
        		{
        			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
        			this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
            }
            
            // Log Frame
            // Corners
            for (int[] uw : new int[][]{
            	{3,1}, {7,1},
            	{3,5}, {7,5},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uw[0], 2, uw[1], uw[0], 6, uw[1], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            }
            for (int u : new int[]{3, 7})
            {
            	this.fillWithMetadataBlocks(world, structureBB, u, 6, 2, u, 6, 4, biomeLogHorAlongBlock, biomeLogHorAlongMeta, biomeLogHorAlongBlock, biomeLogHorAlongMeta, false);
            }
            for (int w : new int[]{1, 5})
            {
            	this.fillWithMetadataBlocks(world, structureBB, 4, 6, w, 6, 6, w, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, false);
            }
            
            // Floor
            this.fillWithMetadataBlocks(world, structureBB, 3, 1, 1, 7, 1, 5, biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);
            this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, 3, 0, 2, structureBB);
            this.fillWithMetadataBlocks(world, structureBB, 5, 2, 2, 6, 2, 4, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
            this.fillWithMetadataBlocks(world, structureBB, 4, 2, 2, 4, 2, 4, Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);
            this.fillWithMetadataBlocks(world, structureBB, 4, 1, 2, 4, 1, 4, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            
            // Left and right walls
            for (int u : new int[]{3,7}) {
            	this.fillWithMetadataBlocks(world, structureBB, u, 2, 2, u, 5, 4, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            }
            // Front and back walls
            for (int w : new int[]{1,5}) {
            	this.fillWithMetadataBlocks(world, structureBB, 4, 2, w, 6, 5, w, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            	this.fillWithMetadataBlocks(world, structureBB, 4, 7, w, 6, 7, w, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            }
            
            // Windows
        	for (int[] uvw : new int[][]{
        		{3, 4, 3}, // Left wall
        		{7, 4, 3}, // Right wall
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            // Roof
        	// First floor
        	for (int i=0; i<=2; i++) {
        		this.fillWithMetadataBlocks(world, structureBB, 4-i, 8-i, 0, 4-i, 8-i, 6, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 0), biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 0), false);
        		this.fillWithMetadataBlocks(world, structureBB, 6+i, 8-i, 0, 6+i, 8-i, 6, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 1), biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, 1), false);
        	}
        	this.fillWithMetadataBlocks(world, structureBB, 5, 8, 0, 5, 8, 6, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
        	
        	// Various wooden stairs
        	for (int[] uvwo : new int[][]{
        		{3, 6, 0, 5}, {4, 7, 0, 5}, {6, 7, 0, 4}, {7, 6, 0, 4}, // Front trim
        		{3, 6, 6, 5}, {4, 7, 6, 5}, {6, 7, 6, 4}, {7, 6, 6, 4}, // Rear trim
        		{5, 2, 6, 2}, // Back platform
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
            }
        	
            // Torches
            for (int[] uvwo : new int[][]{
            	{5,5,0,2},
            	{5,5,6,0},
            	}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
            
            // Doors
            for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
            	{5, 3, 1, 2, 1, 1},
            	{5, 3, 5, 0, 1, 0},
            })
            {
            	for (int height=0; height<=1; height++)
            	{
            		this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
            				uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
            	}
            }
            
            
            // --- Interior --- //
            
            // Trapdoor gate
    		this.fillWithMetadataBlocks(world, structureBB, 4, 3, 2, 4, 3, 4, biomeTrapdoorBlock, this.coordBaseMode%2==0?6:4, biomeTrapdoorBlock, this.coordBaseMode%2==0?6:4, false);
    		
    		//this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, this.coordBaseMode%2==0 ? 6 : 4, 0, 1, 1, structureBB); // Left
        	//this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, this.coordBaseMode%2==0 ? 7 : 5, 6, 1, 1, structureBB); // Right
    		
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 6;
        	int chestV = 3;
        	int chestW = 4;
        	int chestO = 2;
        	this.placeBlockAtCurrentPosition(world, Blocks.chest, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo("vn_fisher");
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
            
        	
        	// --- Backyard --- //
        	
        	// Pond
        	int[][] waterDepths = new int[][]{
    			{4,6},
    			{3,7},
    			{6,7},
    			{6,8},
    			{6,9},
    			{6,9},
    			{6,8},
    			{4,7},
        	};
        	for (int u=0; u<=7; u++)
            {
        		this.fillWithMetadataBlocks(world, structureBB, u+1, 1, waterDepths[u][0], u+1, 1, waterDepths[u][1], Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);
            }
        	
        	// Balcony
        	this.placeBlockAtCurrentPosition(world, biomeWoodSlabBlock, biomeWoodSlabMeta, 5, 2, 7, structureBB);
    		this.fillWithMetadataBlocks(world, structureBB, 5, 1, 6, 5, 1, 7, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false); // Instead of fences b/c waterlogging
    		
    		// Barrels
    		Block barrelBlock = ModObjects.chooseModBarrelBlock();
    		for (int[] uvwoo : new int[][]{
    			// u, v, w, orientationIfChest, orientationIfUTDBarrel
    			// TODO - use different barrel meta for different mods
            	{2, 2, 2, 3, 1},
            	{7, 2, 9, 1, 1},
            })
            {
    			// Set the barrel, or a chest if it's not supported
    			if (barrelBlock==null) {barrelBlock = Blocks.chest;}
    			this.placeBlockAtCurrentPosition(world, barrelBlock, 0, uvwoo[0], uvwoo[1], uvwoo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwoo[0], uvwoo[2]), this.getYWithOffset(uvwoo[1]), this.getZWithOffset(uvwoo[0], uvwoo[2]), barrelBlock==Blocks.chest?StructureVillageVN.chooseFurnaceMeta(uvwoo[3], this.coordBaseMode):uvwoo[4], 2);
                // Dirt beneath
                this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, uvwoo[0], uvwoo[1]-1, uvwoo[2], structureBB);
            }
    		
    		
    		// Front steps
        	for (int uvwm[] : new int[][]{
        		{4, 2, 0, 0}, // Corner
        		{5, 2, 0, 3},
        		{6, 2, 0, 1}, // Corner
        	})
        	{
        		this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwm[3]), uvwm[0], uvwm[1], uvwm[2], structureBB);
        	}
        	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, 7, 1, 0, structureBB);
        	
            // Clear path for easier entry
        	for (int u=4; u<=6; u++)
        	{
        		this.clearCurrentPositionBlocksUpwards(world, u, 2, -1, structureBB);
            	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, -1, structureBB);
            	// Place dirt if the block to be set as path is empty
            	if (world.isAirBlock(this.getXWithOffset(u, -1), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(u, -1)))
            	{
                	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-1, -1, structureBB);
            	}
            	StructureVillageVN.setPathSpecificBlock(world, this.start, 0, this.getXWithOffset(u, -1), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(u, -1));
        	}
        	
			//LogHelper.info("Fisher Cottage spawned at " + this.getXWithOffset(0, 0) + " " + this.getYWithOffset(0) + " " + this.getZWithOffset(0, 0) + " with horizIndex " + this.coordBaseMode);
            
    		// Entities
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	// Villager
            	int u = random.nextInt(2)+5;
            	int v =  3;
            	int w = random.nextInt(2)+2;
            	
            	EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 0, 2, 0);
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
                world.spawnEntityInWorld(entityvillager);
            }
            
            return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    
    // --- Fletcher House --- //
    
    public static class PlainsFletcherHouse1 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public StartVN start;
    	
    	// Here are values to assign to the bounding box
    	private static final int STRUCTURE_WIDTH = 11;
    	private static final int STRUCTURE_HEIGHT = 7;
    	private static final int STRUCTURE_DEPTH = 9;
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	
    	private int averageGroundLevel = -1;
    	
        public PlainsFletcherHouse1() {}

        public PlainsFletcherHouse1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            this.start = start;
        }

        public static PlainsFletcherHouse1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new PlainsFletcherHouse1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
        		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
        				// Set the bounding box version as this bounding box but with Y going from 0 to 512
        				new StructureBoundingBox(
        						this.boundingBox.minX, this.boundingBox.minZ,
        						this.boundingBox.maxX, this.boundingBox.maxZ),
        				true, (byte)1, this.coordBaseMode);
        		
                if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
            }
        	
            Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, start.materialType, start.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, start.materialType, start.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, start.materialType, start.biome); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), start.materialType, start.biome); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1]; // Toward you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), start.materialType, start.biome); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1]; // Perpendicular to you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, start.materialType, start.biome); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, start.materialType, start.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.oak_stairs, 0, start.materialType, start.biome); Block biomeWoodStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_slab, 0, start.materialType, start.biome); Block biomeWoodSlabBlock = (Block)blockObject[0]; int biomeWoodSlabMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_door, 0, start.materialType, start.biome); Block biomeWoodDoorBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(ModObjects.chooseModPathBlock(), 0, start.materialType, start.biome); Block grassPathBlock = (Block) blockObject[0]; int grassPathMeta = (Integer) blockObject[1]; 
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, start.materialType, start.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = ModObjects.chooseModFletchingTable(); Block fletchingTableBlock = (Block) blockObject[0]; int fletchingTableMeta = (Integer) blockObject[1];
        	
        	// Clear above and make foundation
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            		
        		this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
        		
        		if (world.getBlock(this.getXWithOffset(u, w), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(u, w))==Blocks.dirt)
        		{
        			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
        			this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
            }}
            // Set foundation where the house specifically is
            int[][] rowstrips = new int[][]{
            	{4,6},
            	{5,5},
            	{4,6},
            	{3,7},
            	{1,9},
            	{1,9},
            	{1,9},
            	{1,9},
            };
            for (int i=0; i<=7; i++)
            {
            	for (int u=rowstrips[i][0]; u<=rowstrips[i][1]; u++)
            	{
            		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-1, i, structureBB);
            	}
            }
            // The grass patch in front
            for (int[] uwg : new int[][]{ // g is grass type
            	{8,0,0},
            	{9,0,1},
            	{10,0,1},
            	{8,1,0},
            	{10,1,0},
            })
            {
            	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uwg[0], GROUND_LEVEL-2, uwg[1], structureBB);
    			this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, uwg[0], GROUND_LEVEL-1, uwg[1], structureBB);
    			if (uwg[2]==0) // Short grass
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 1, uwg[0], GROUND_LEVEL, uwg[1], structureBB);
    			}
    			else // Tall grass
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 2, uwg[0], GROUND_LEVEL, uwg[1], structureBB);
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 11, uwg[0], GROUND_LEVEL+1, uwg[1], structureBB);
    			}
            }
            
            // Log Frame
            // Corners
            for (int[] uw : new int[][]{
            	{1,4}, {9,4},
            	{1,7}, {9,7},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uw[0], 1, uw[1], uw[0], 4, uw[1], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            }
            // Crossbeams
            for (int u : new int[]{1,9})
            {
            	this.fillWithMetadataBlocks(world, structureBB, u, 4, 5, u, 4, 6, biomeLogHorAlongBlock, biomeLogHorAlongMeta, biomeLogHorAlongBlock, biomeLogHorAlongMeta, false);
            }
            this.fillWithMetadataBlocks(world, structureBB, 2, 4, 7, 8, 4, 7, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, false);
            
            // Floor
            this.fillWithMetadataBlocks(world, structureBB, 4, 0, 3, 6, 0, 3, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
            this.fillWithMetadataBlocks(world, structureBB, 3, 0, 4, 7, 0, 4, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
            this.fillWithMetadataBlocks(world, structureBB, 2, 0, 5, 8, 0, 6, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
            this.placeBlockAtCurrentPosition(world, biomePlankBlock, biomePlankMeta, 5, 0, 2, structureBB);
            
            // Walls
            for (int u : new int[]{2,8}) {
            	this.fillWithMetadataBlocks(world, structureBB, u, 1, 4, u, 4, 4, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            }
            for (int u : new int[]{1,9}) {
            	this.fillWithMetadataBlocks(world, structureBB, u, 1, 5, u, 3, 6, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            	this.fillWithMetadataBlocks(world, structureBB, u, 5, 5, u, 5, 6, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            }
            this.fillWithMetadataBlocks(world, structureBB, 2, 1, 7, 8, 3, 7, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            this.fillWithMetadataBlocks(world, structureBB, 4, 5, 7, 6, 5, 7, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            // Front wall
            this.fillWithMetadataBlocks(world, structureBB, 4, 1, 2, 6, 4, 2, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
            
            // Windows
        	for (int[] uvw : new int[][]{
        		{1, 2, 5}, {1, 2, 6}, // Left wall
        		{9, 2, 5}, {9, 2, 6}, // Right wall
        		{3, 2, 7}, {5, 2, 7}, {7, 2, 7}, // Back wall
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
        	// Various wooden stairs
        	for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
        		
        		// Front awning
        		{3, 4, 2, 0}, {4, 5, 2, 0}, {6, 5, 2, 1}, {7, 4, 2, 1},
        		// Front roof
        		{2, 4, 3, 0}, {3, 5, 3, 0}, {4, 6, 3, 0}, {6, 6, 3, 1}, {7, 5, 3, 1}, {8, 4, 3, 1},
        		{3, 5, 4, 3}, {4, 6, 4, 0}, {6, 6, 4, 1}, {7, 5, 4, 3},
        		{4, 6, 5, 3}, {6, 6, 5, 3},
        		// Back roof
        		{4, 6, 6, 2}, {6, 6, 6, 2},
        		{3, 5, 7, 2}, {4, 6, 7, 0}, {6, 6, 7, 1}, {7, 5, 7, 2},
        		{3, 5, 8, 0}, {4, 6, 8, 0}, {6, 6, 8, 1}, {7, 5, 8, 1},
        		// Left roof
        		{0, 4, 3, 3}, {0, 5, 4, 3}, {0, 6, 5, 3}, {0, 6, 6, 2}, {0, 5, 7, 2}, {0, 4, 8, 2},
        		{1, 4, 3, 3}, {1, 5, 4, 3}, {1, 6, 5, 3}, {1, 6, 6, 2}, {1, 5, 7, 2}, {1, 4, 8, 2},
        		{2, 5, 4, 3}, {2, 6, 5, 3}, {2, 6, 6, 2}, {2, 5, 7, 2}, {2, 4, 8, 2},
        		{3, 6, 5, 3}, {3, 6, 6, 2},
        		// Right roof
        		{7, 6, 5, 3}, {7, 6, 6, 2},
        		{8, 5, 4, 3}, {8, 6, 5, 3}, {8, 6, 6, 2}, {8, 5, 7, 2}, {8, 4, 8, 2},
        		{9, 4, 3, 3}, {9, 5, 4, 3}, {9, 6, 5, 3}, {9, 6, 6, 2}, {9, 5, 7, 2}, {9, 4, 8, 2},
        		{10, 4, 3, 3}, {10, 5, 4, 3}, {10, 6, 5, 3}, {10, 6, 6, 2}, {10, 5, 7, 2}, {10, 4, 8, 2},
        		
        		
        		// Left roof trim
        		{0, 4, 4, 6}, {0, 5, 5, 6}, {0, 5, 6, 7}, {0, 4, 7, 7},
        		// Right roof trim
        		{10, 4, 4, 6}, {10, 5, 5, 6}, {10, 5, 6, 7}, {10, 4, 7, 7},
        		// Back roof trim
        		{3, 4, 8, 5}, {4, 5, 8, 5}, {6, 5, 8, 4}, {7, 4, 8, 4},
        		
        		
        		// Interior supports
        		{3, 5, 5, 0}, {3, 5, 6, 0},
        		{7, 5, 5, 1}, {7, 5, 6, 1},
        		
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
            }
        	this.fillWithMetadataBlocks(world, structureBB, 5, 6, 3, 5, 6, 8, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
        	
        	// Torches
            for (int[] uvwo : new int[][]{
            	{5,3,1,2}, {5,3,3,0}, // Front door
            	{4,4,6,2}, {6,4,6,2}, {4,2,8,0}, {6,2,8,0}, // Back wall
            	}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
            
            // Doors
            for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
            	{5, 1, 2, 2, 1, 1},
            })
            {
            	for (int height=0; height<=1; height++)
            	{
            		this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
            				uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
            	}
            }
            
            
            // --- Interior --- //
            
            // Plank framework
            for (int u : new int[]{3,7})
            {
            	this.fillWithMetadataBlocks(world, structureBB, u, 1, 3, u, 4, 3, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
            }
            for (int u : new int[]{4,6})
            {
            	this.placeBlockAtCurrentPosition(world, biomePlankBlock, biomePlankMeta, u, 5, 3, structureBB);
            }
            this.placeBlockAtCurrentPosition(world, biomePlankBlock, biomePlankMeta, 5, 5, 2, structureBB);
            
            // Table
            this.fillWithMetadataBlocks(world, structureBB, 8, 1, 5, 8, 1, 6, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
            
            // 0: nuffin
            // 1: poppy
            // 2: dandelion
            int flowernumber = random.nextInt(2)+1;
            
            // Flower pot on table
        	this.placeBlockAtCurrentPosition(world, Blocks.flower_pot, flowernumber, 8, 2, 6, structureBB);
        	
            // Fletching Table
            this.placeBlockAtCurrentPosition(world, fletchingTableBlock, fletchingTableMeta, 2, 1, 6, structureBB);
            
            // Carpet
            this.fillWithMetadataBlocks(world, structureBB, 4, 1, 5, 6, 1, 5, Blocks.carpet, (GeneralConfig.decorateVillageCenter ? this.start.townColor : 4), Blocks.carpet, (GeneralConfig.decorateVillageCenter ? this.start.townColor : 4), false);
            
            
            // --- Front awning --- //
            for (int u : new int[]{4,6})
            {
            	this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, 0, 0, structureBB);
                this.fillWithMetadataBlocks(world, structureBB, u, 1, 0, u, 3, 0, biomeFenceBlock, 0, biomeFenceBlock, 0, false);
                this.fillWithMetadataBlocks(world, structureBB, u, 4, 0, u, 4, 1, biomeWoodSlabBlock, biomeWoodSlabMeta, biomeWoodSlabBlock, biomeWoodSlabMeta, false);
            }
            // Wool
            this.placeBlockAtCurrentPosition(world, Blocks.wool, (GeneralConfig.decorateVillageCenter ? this.start.townColor : 4), 5, 4, 0, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.wool, (GeneralConfig.decorateVillageCenter ? this.start.townColor2 : 0), 5, 4, 1, structureBB);
            // Grass Path
            this.fillWithMetadataBlocks(world, structureBB, 5, 0, 0, 5, 0, 1, grassPathBlock, grassPathMeta, grassPathBlock, grassPathMeta, false);
            
            // Clear path for easier entry
            this.clearCurrentPositionBlocksUpwards(world, 5, GROUND_LEVEL, -1, structureBB);
        	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, 5, GROUND_LEVEL-2, -1, structureBB);
        	// Place dirt if the block to be set as path is empty
        	if (world.isAirBlock(this.getXWithOffset(5, -1), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(5, -1)))
        	{
            	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, 5, GROUND_LEVEL-1, -1, structureBB);
        	}
        	StructureVillageVN.setPathSpecificBlock(world, this.start, 0, this.getXWithOffset(5, -1), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(5, -1));
            
            // Decor
            for (int decorUVW[] : new int[][]{
            	{1, 1, 1},
            	})
            {
                // Add foundations
            	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, decorUVW[0], decorUVW[1]-1, decorUVW[2], structureBB);
        		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, decorUVW[0], decorUVW[1]-2, decorUVW[2], structureBB);
        		
                // The actual decor
        		for (int j=0; j<decorUVW.length; j++)
                {
        			int decorHeightY = decorUVW[1];
        			
        			// Set random seed
                	Random randomFromXYZ = new Random();
                	randomFromXYZ.setSeed(
        					world.getSeed() +
        					FunctionsVN.getUniqueLongForXYZ(
        							this.getXWithOffset(decorUVW[0], decorUVW[2]),
        							this.getYWithOffset(decorHeightY),
        							this.getZWithOffset(decorUVW[0], decorUVW[2])
        							)
                			);
                	
                	// Get ground level
                	if (this.decorHeightY.size()<(j+1))
                	{
                		// There are fewer stored ground levels than this decor number, so this is being generated for the first time.
                		// Add new ground level
                		//decorHeightY = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(uvw[0], uvw[2]), this.getZWithOffset(uvw[0], uvw[2]))-this.boundingBox.minY;
                		this.decorHeightY.add(decorHeightY);
                	}
                	else
                	{
                		// There is already (presumably) a value for this ground level, so this decor is being multiply generated.
                		// Retrieve ground level
                		decorHeightY = this.decorHeightY.get(j);
                	}
                	
                	
                	// Generate decor
                	ArrayList<BlueprintData> decorBlueprint = getRandomPlainsDecorBlueprint(this.start, this.coordBaseMode, randomFromXYZ);
                	
                	for (BlueprintData b : decorBlueprint)
                	{
                		// Place block indicated by blueprint
                		this.placeBlockAtCurrentPosition(world, b.getBlock(), b.getMeta(), decorUVW[0]+b.getUPos(), decorHeightY+b.getVPos(), decorUVW[2]+b.getWPos(), structureBB);
                		
                		// Fill below if flagged
                		if ((b.getfillFlag()&1)!=0)
                		{
                			this.func_151554_b(world, b.getBlock(), b.getMeta(), decorUVW[0]+b.getUPos(), decorHeightY+b.getVPos()-1, decorUVW[2]+b.getWPos(), structureBB);
                		}
                		
                		// Clear above if flagged
                		if ((b.getfillFlag()&2)!=0)
                		{
                			this.clearCurrentPositionBlocksUpwards(world, decorUVW[0]+b.getUPos(), decorHeightY+b.getVPos()+1, decorUVW[2]+b.getWPos(), structureBB);
                		}            		
                	}
                }
            }
            
			//LogHelper.info("Fletcher house spawned at " + this.getXWithOffset(4, 0) + " " + this.getYWithOffset(1) + " " + this.getZWithOffset(4, 0) + " with horizIndex " + this.coordBaseMode);
            
    		// Entities
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	// Villager
            	int u = random.nextInt(5)+3;
            	int v =  1;
            	int w = random.nextInt(3)+4;
            	
            	EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 0, 4, 0);
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
                world.spawnEntityInWorld(entityvillager);
            }
            
            return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    
    // --- Large Farm --- //
    
    public static class PlainsLargeFarm1 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public StartVN start;
    	
    	// Here are values to assign to the bounding box
    	private static final int STRUCTURE_WIDTH = 9;
    	private static final int STRUCTURE_HEIGHT = 6;
    	private static final int STRUCTURE_DEPTH = 13;
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	
    	private int averageGroundLevel = -1;
    	
        public PlainsLargeFarm1() {}

        public PlainsLargeFarm1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            this.start = start;
        }

        public static PlainsLargeFarm1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new PlainsLargeFarm1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
        		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
        				// Set the bounding box version as this bounding box but with Y going from 0 to 512
        				new StructureBoundingBox(
        						this.boundingBox.minX, this.boundingBox.minZ,
        						this.boundingBox.maxX, this.boundingBox.maxZ),
        				true, (byte)1, this.coordBaseMode);
        		
                if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
            }
        	
            Object[] blockObject;
            blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, start.materialType, start.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, start.materialType, start.biome); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(ModObjects.chooseModPathBlock(), 0, start.materialType, start.biome); Block grassPathBlock = (Block) blockObject[0]; int grassPathMeta = (Integer) blockObject[1]; 
        	
        	// Clear above and make foundation
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            		
        		this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL+1, w, structureBB); // Clear above
    			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-1, w, structureBB); // Foundation
    			// Ground-level blocks
    			if(u==0 || u==(STRUCTURE_WIDTH-1) || w==0 || w==(STRUCTURE_DEPTH-1) )
    			{
    				this.placeBlockAtCurrentPosition(world, biomeLogVertBlock, biomeLogVertMeta, u, GROUND_LEVEL, w, structureBB);
    			}
    			else
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.farmland, 7, u, GROUND_LEVEL, w, structureBB);
    			}
            }}
            
            // Log Frame interior
            this.fillWithMetadataBlocks(world, structureBB, 1, 0, 6, STRUCTURE_WIDTH-2, 0, 6, biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            // Water
            for (int w : new int[]{3,9}) {
            	this.fillWithMetadataBlocks(world, structureBB, 1, 0, w, STRUCTURE_WIDTH-2, 0, w, Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);
            }
            
            // Crops here
            Block[] cropBlocks;
            
            for (int w=0; w<4; w++)
            {
            	cropBlocks = StructureVillageVN.chooseCropPair(random);
            	
            	for (int i=0; i<2; i++)
            	{
            		this.fillWithMetadataBlocks(world, structureBB, 1, 1, 3*w +i +1, STRUCTURE_WIDTH-2, 1, 3*w +i +1, cropBlocks[i], 0, cropBlocks[i], 0, false);
            	}
            }
            
            // Attempt to add GardenCore Compost Bins. If this fails, do nothing
            Block compostBin = Block.getBlockFromName(ModObjects.compostBinGC);
            if (compostBin != null)
            {
            	this.placeBlockAtCurrentPosition(world, compostBin, 0, 1, 1, 1, structureBB); this.placeBlockAtCurrentPosition(world, Blocks.dirt, 0, 1, 0, 1, structureBB);
            	this.placeBlockAtCurrentPosition(world, compostBin, 0, 1, 1, 11, structureBB); this.placeBlockAtCurrentPosition(world, Blocks.dirt, 0, 1, 0, 1, structureBB);
            }
            		
            
    		// Entities
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	// Villager
            	int u=0;
            	int w=3;
            	int v= 1;
            	
            	while (w==3 || w==9)
            	{
            		u = random.nextInt(STRUCTURE_WIDTH);
                	w = 1+random.nextInt(STRUCTURE_DEPTH-2);
            	}
            	EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 0, 1, 0);
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
                world.spawnEntityInWorld(entityvillager);
            }
            
            return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    
    // --- Plains Large Library --- //
    
    public static class PlainsLibrary1 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public StartVN start;
    	
    	// Here are values to assign to the bounding box
    	private static final int STRUCTURE_WIDTH = 17;
    	private static final int STRUCTURE_HEIGHT = 9;
    	private static final int STRUCTURE_DEPTH = 10;
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	
    	private int averageGroundLevel = -1;
    	
        public PlainsLibrary1() {}
        
        public PlainsLibrary1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            this.start = start;
        }
        
        public static PlainsLibrary1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new PlainsLibrary1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
        		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
        				// Set the bounding box version as this bounding box but with Y going from 0 to 512
        				new StructureBoundingBox(
        						this.boundingBox.minX, this.boundingBox.minZ,
        						this.boundingBox.maxX, this.boundingBox.maxZ),
        				true, (byte)1, this.coordBaseMode);
        		
                if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
            }
        	
            Object[] blockObject;
            blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, start.materialType, start.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, start.materialType, start.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, start.materialType, start.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.stone_stairs, 0, start.materialType, start.biome); Block biomeStoneStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.oak_stairs, 0, start.materialType, start.biome); Block biomeWoodStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, start.materialType, start.biome); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), start.materialType, start.biome); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1]; // Toward you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), start.materialType, start.biome); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1]; // Perpendicular to you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, start.materialType, start.biome); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_door, 0, start.materialType, start.biome); Block biomeWoodDoorBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(ModObjects.chooseModPathBlock(), 0, start.materialType, start.biome); Block grassPathBlock = (Block) blockObject[0]; int grassPathMeta = (Integer) blockObject[1]; 
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, start.materialType, start.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = ModObjects.chooseModLectern(); Block lecternBlock = (Block) blockObject[0]; int lecternMeta = (Integer) blockObject[1];
        	
        	// Clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            	this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL+2, w, structureBB);
            }}
            
            // Make foundation
        	int[][] grassWidths = new int[][]{
    			{-1,-1}, // Force this row to be grass-style
    			{7,9},
    			{6,10},
    			{1,15}, {1,15}, {1,15}, {1,15}, {1,15}, {1,15}, {1,15},
    			{-1,-1}, // Force this row to be grass-style
        	};
        	for (int w=0; w < STRUCTURE_DEPTH; w++)
            {
            	for (int u=0; u < STRUCTURE_WIDTH; u++)
                {
            		if (u>=grassWidths[w][0] && u<=grassWidths[w][1])
            		{
            			// Grass foundation
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-1, w, structureBB);
            		}
            		else if (world.getBlock(this.getXWithOffset(u, w), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(u, w))==Blocks.dirt)
            		{
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
            		}
                }
            }
            
            
            // Stone
            for(int[] uuvvww : new int[][]{
            	{1,0,3, 5,0,9},
            	{11,0,3, 15,0,9},
            	// Front door
            	{6,0,2, 10,0,9},
            	{7,0,1, 9,0,1},
            	{6,1,2, 6,3,2}, {10,1,2, 10,3,2},
            	{7,1,1, 7,3,1}, {9,1,1, 9,3,1},
            	// Interior stairs
            	{5,1,5, 5,2,5}, {11,1,5, 11,2,5},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
            }
            this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, 8, 3, 1, structureBB);
            this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, 4, 1, 5, structureBB);
            this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, 12, 1, 5, structureBB);

            // Planks
            for(int[] uuvvww : new int[][]{
            	// Left and right walls
            	{1,1,4, 1,6,8},
            	{15,1,4, 15,6,8},
            	// Front walls
            	{2,1,3, 4,5,3}, {12,1,3, 14,5,3}, {6,4,3, 10,6,3}, {7,8,3, 9,8,3},
            	// Back wall
            	{2,1,9, 14,5,9}, 
            	// Platform
            	{7,3,2, 9,3,2}, {6,3,3, 10,3,5},
            	// Interior plank beams
            	{2,6,4, 5,6,4}, {11,6,4, 14,6,4}, {2,6,8, 14,6,8},
            	{2,7,4, 6,7,5}, {10,7,4, 14,7,5}, {2,7,7, 14,7,7}, {0,8,6, 16,8,6},
            	{8,9,2, 8,9,5}, {7,8,4, 7,8,5}, {9,8,4, 9,8,5},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
            
            // Log frame
            // Exterior corners
            for(int[] uw : new int[][]{{1,3},{1,9},{15,3},{15,9}})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uw[0], 0, uw[1], uw[0], 5, uw[1], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            }
            // Interior corners
            for(int[] uw : new int[][]{{5,3},{11,3}})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uw[0], 0, uw[1], uw[0], 6, uw[1], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            }
            // Across beams
            for(int[] uuvvww : new int[][]{
            	{2,4,3, 4,4,3},
            	{12,4,3, 14,4,3},
            	{6,7,3, 10,7,3},
            	{2,4,9, 14,4,9},
            })
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, false);
            }
            for(int u : new int[]{3,5,7,9,11,13}){
            	this.placeBlockAtCurrentPosition(world, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, u, 2, 9, structureBB);
            }
            // Along beams
            for(int[] uuvvww : new int[][]{
            	{1,4,4, 1,4,8},
            	{1,7,5, 1,7,7},
            	{15,4,4, 15,4,8},
            	{15,7,5, 15,7,7},
            })
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAlongBlock, biomeLogHorAlongMeta, biomeLogHorAlongBlock, biomeLogHorAlongMeta, false);
            }
            for (int u : new int[]{1,15}) {for (int w : new int[]{5,7}) {
            	this.placeBlockAtCurrentPosition(world, biomeLogHorAlongBlock, biomeLogHorAlongMeta, u, 2, w, structureBB);
            }}
            
            // Windows
        	for (int[] uw : new int[][]{
        		{3, 3}, {13, 3}, // Front wall
        		{1, 6}, {15, 6}, // Left and right walls
        		{4, 9}, {8, 9}, {12, 9}, // Back wall
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uw[0], 2, uw[1], structureBB);
            }
            
        	// Various wooden stairs
        	for (int[] uuvvwwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward; +4:inverted
        		
        		// Left roof
        		{0,5,2, 4,5,2, 3}, {0,6,3, 4,6,3, 3}, {0,7,4, 4,7,4, 3}, {0,8,5, 5,8,5, 3},
        		// Right roof
        		{12,5,2, 16,5,2, 3}, {12,6,3, 16,6,3, 3}, {12,7,4, 16,7,4, 3}, {11,8,5, 16,8,5, 3},
        		// Back roof
        		{0,5,10, 16,5,10, 2}, {0,6,9, 16,6,9, 2}, {0,7,8, 16,7,8, 2}, {0,8,7, 16,8,7, 2}, {7,9,6, 9,9,6, 2},
        		// Front roof
        		{5,7,2, 5,7,4, 0}, {11,7,2, 11,7,4, 1},
        		{6,8,2, 6,8,5, 0}, {10,8,2, 10,8,5, 1},
        		{7,9,2, 7,9,5, 0}, {9,9,2, 9,9,5, 1},
        		// Trim
        		{6,7,2, 6,7,2, 1+4}, {10,7,2, 10,7,2, 0+4}, {7,8,2, 7,8,2, 1+4}, {9,8,2, 9,8,2, 0+4},
        		{0,5,3, 0,5,3, 2+4}, {0,5,9, 0,5,9, 3+4}, {0,6,4, 0,6,4, 2+4}, {0,6,8, 0,6,8, 3+4}, {0,7,5, 0,7,5, 2+4}, {0,7,7, 0,7,7, 3+4},
        		{16,5,3, 16,5,3, 2+4}, {16,5,9, 16,5,9, 3+4}, {16,6,4, 16,6,4, 2+4}, {16,6,8, 16,6,8, 3+4}, {16,7,5, 16,7,5, 2+4}, {16,7,7, 16,7,7, 3+4},
        		// Interior between doors
        		{8,4,4, 8,4,4, 2},
        		// Benches
        		{2,1,7, 2,1,8, 1}, {3,1,8, 4,1,8, 3},
        		{14,1,7, 14,1,8, 0}, {12,1,8, 13,1,8, 3},
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], uuvvwwo[3], uuvvwwo[4], uuvvwwo[5], biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uuvvwwo[6]%4)+(uuvvwwo[6]/4)*4, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uuvvwwo[6]%4)+(uuvvwwo[6]/4)*4, false);
            }
        	
        	// Torches
            for (int[] uvwo : new int[][]{
            	{1,2,2,2}, {15,2,2,2}, {7,2,0,2}, {9,2,0,2}, // Front
            	{6,2,10,0}, {10,2,10,0}, // Back
            	{6,3,6,0}, {10,3,6,0}, // Floor separation
            	{2,5,6,1}, {14,5,6,3}, // Upper interior walls
            	}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
        	
        	// Interior
        	// Various stone stairs
        	for (int[] uuvvwwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward; +4:inverted
        		// Left
        		{3,1,5, 0}, {4,2,5, 0}, {5,3,5, 0},
        		// Right
        		{13,1,5, 1}, {12,2,5, 1}, {11,3,5, 1},
        		// Front door
        		{8,0,0, 3},
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uuvvwwo[3]%4)+(uuvvwwo[3]/4)*4, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], structureBB);
            }
            
            // Bookshelves
            for(int[] uuvvww : new int[][]{
            	{5,1,8, 7,1,8}, {6,2,8, 6,2,8},
            	{9,1,8, 11,1,8}, {10,2,8, 10,2,8},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.bookshelf, 0, Blocks.bookshelf, 0, false);	
            }
        	
            // Lecterns
            for (int u : new int[]{6, 10})
            {
            	this.placeBlockAtCurrentPosition(world, lecternBlock, lecternMeta, u, 1, 5, structureBB);
            }
            
            // Doors
            for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
            	{8, 1, 1, 2, 1, 0},
            	{7, 4, 3, 2, 1, 0},
            	{9, 4, 3, 2, 1, 1},
            })
            {
            	for (int height=0; height<=1; height++)
            	{
            		this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
            				uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
            	}
            }
        	
            // Fences
            this.fillWithMetadataBlocks(world, structureBB, 7, 4, 1, 9, 4, 1, biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            for (int u : new int[]{6,10}) {
            	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, u, 4, 2, structureBB);
            }
            
            
            // Clear path for easier entry
        	this.clearCurrentPositionBlocksUpwards(world, 8, GROUND_LEVEL, -1, structureBB);
        	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, 8, GROUND_LEVEL-2, -1, structureBB);
        	// Place dirt if the block to be set as path is empty
        	if (world.isAirBlock(this.getXWithOffset(8, -1), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(8, -1)))
        	{
            	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, 8, GROUND_LEVEL-1, -1, structureBB);
        	}
        	StructureVillageVN.setPathSpecificBlock(world, this.start, 0, this.getXWithOffset(8, -1), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(8, -1));
        	
            
    		// Villagers
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	// Villager
            	int u;
            	int w;
            	int v;
            	
            	if (random.nextBoolean())
            	{
            		// Upstairs
                	u=6+random.nextInt(5);
                	v=4;
                	w=5;
            	}
            	else
            	{
            		// Downstairs
                	u=3+random.nextInt(11);
                	v=1;
                	w=6+random.nextInt(2);
            	}
            	
            	EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 1, 1, 0);
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
                world.spawnEntityInWorld(entityvillager);
            }
            
            return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 1;}
    }
    
    
    
    // --- Plains Small Library --- //
    
    public static class PlainsLibrary2 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public StartVN start;
    	
    	// Here are values to assign to the bounding box
    	private static final int STRUCTURE_WIDTH = 9;
    	private static final int STRUCTURE_HEIGHT = 10;
    	private static final int STRUCTURE_DEPTH = 8;
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	
    	private int averageGroundLevel = -1;
    	
        public PlainsLibrary2() {}
        
        public PlainsLibrary2(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            this.start = start;
        }
        
        public static PlainsLibrary2 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new PlainsLibrary2(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
        		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
        				// Set the bounding box version as this bounding box but with Y going from 0 to 512
        				new StructureBoundingBox(
        						this.boundingBox.minX, this.boundingBox.minZ,
        						this.boundingBox.maxX, this.boundingBox.maxZ),
        				true, (byte)1, this.coordBaseMode);
        		
                if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
            }
        	
            Object[] blockObject;
            blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, start.materialType, start.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, start.materialType, start.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, start.materialType, start.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.oak_stairs, 0, start.materialType, start.biome); Block biomeWoodStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, start.materialType, start.biome); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), start.materialType, start.biome); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1]; // Toward you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), start.materialType, start.biome); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1]; // Perpendicular to you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, start.materialType, start.biome); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_door, 0, start.materialType, start.biome); Block biomeWoodDoorBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(ModObjects.chooseModPathBlock(), 0, start.materialType, start.biome); Block grassPathBlock = (Block) blockObject[0]; int grassPathMeta = (Integer) blockObject[1]; 
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, start.materialType, start.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = ModObjects.chooseModLectern(); Block lecternBlock = (Block) blockObject[0]; int lecternMeta = (Integer) blockObject[1];
        	
        	// Clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            	this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
            }}
            
            // Make foundation with blanks as empty air and F as foundation spaces
            String[] foundationPattern = new String[]{
            	"         ",
            	" FFFFFFF ",
            	" FFFFFFF ",
            	" FFFFFFF ",
            	" FFFFFFF ",
            	" FFFFFFF ",
            	"   F F   ",
            	"  FFFFF  ",
            };
        	for (int w=0; w < STRUCTURE_DEPTH; w++) {for (int u=0; u < STRUCTURE_WIDTH; u++) {
        		
            		if (foundationPattern[STRUCTURE_DEPTH-1-w].substring(u, u+1).toUpperCase().equals("F"))
            		{
            			// If marked with F: fill with dirt foundation
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-1, w, structureBB);
            		}
            		else if (world.getBlock(this.getXWithOffset(u, w), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(u, w))==biomeDirtBlock)
            		{
            			// Otherwise, if dirt, add dirt foundation and then cap with grass:
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
            		}
                }
            }
            
            
            // Stone
            for(int[] uuvvww : new int[][]{
            	// Floor
            	{2,0,3, 6,0,4},
            	// Front
            	{2,1,2, 6,3,2},
            	// Back
            	{2,1,6, 6,3,6},
            	// Left
            	{1,1,3, 1,3,5},
            	// Right
            	{7,1,3, 7,3,5},
            	// Under bookshelves
            	{2,0,5, 3,0,5},
            	// Foot of stairs
            	{6,0,5, 6,0,5},
            	// Under doors
            	{3,0,2, 3,0,2},
            	{5,0,2, 5,0,2},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
            }
            
            // Planks
            for(int[] uuvvww : new int[][]{
            	// Second floor
            	
            	// Floor
            	{2,4,3, 6,4,4},
            	// Front
            	{2,5,2, 6,7,2},
            	// Back
            	{2,5,6, 6,7,6},
            	// Left
            	{1,5,3, 1,7,5},
            	// Right
            	{7,5,3, 7,7,5},
            	// Balcony
            	{2,4,1, 6,4,1},
            	// Corner of second floor
            	{6,4,5, 6,4,5},
            	// Stairs
            	{4,1,5, 4,1,5},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
            
            // Logs
            // Vertical beams
            for(int[] uuvvww : new int[][]{
            	{1,1,2, 1,3,2}, {1,5,2, 1,6,2}, 
            	{1,1,6, 1,3,6}, {1,5,6, 1,6,6},
            	{7,1,2, 7,3,2}, {7,5,2, 7,6,2}, 
            	{7,1,6, 7,3,6}, {7,5,6, 7,6,6}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            }
            // Across beams
            for(int[] uuvvww : new int[][]{
            	{2,4,0, 6,4,0},
            	{1,4,2, 7,4,2},
            	{3,8,2, 5,8,2},
            	{1,4,6, 7,4,6},
            	{3,8,6, 5,8,6},
            })
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, false);
            }
            
            // Along beams
            for(int[] uuvvww : new int[][]{
            	{1,4,3, 1,4,5},
            	{7,4,3, 7,4,5},
            	{4,9,1, 4,9,7},
            })
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAlongBlock, biomeLogHorAlongMeta, biomeLogHorAlongBlock, biomeLogHorAlongMeta, false);
            }
            for (int u : new int[]{1,7}) {
            	this.placeBlockAtCurrentPosition(world, biomeLogHorAlongBlock, biomeLogHorAlongMeta, u, 4, 1, structureBB);
            }
            
            // Windows
        	for (int[] uvw : new int[][]{
        			{1,2,4}, {1,6,4},
        			{7,2,4}, {7,6,4},
        			{3,2,6}, {5,2,6}, {3,6,6}, {5,6	,6},
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
        	// Various wooden stairs
        	for (int[] uuvvwwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward; +4:inverted
        		
        		// Roof
        		{0,6,1, 0,6,2, 0}, {8,6,1, 8,6,2, 1},
        		{0,6,6, 0,6,7, 0}, {8,6,6, 8,6,7, 1}, 
        		{1,7,1, 1,7,2, 0}, {7,7,1, 7,7,2, 1}, 
        		{1,7,6, 1,7,7, 0}, {7,7,6, 7,7,7, 1}, 
        		{2,8,1, 2,8,7, 0}, {6,8,1, 6,8,7, 1}, 
        		{3,9,1, 3,9,7, 0}, {5,9,1, 5,9,7, 1}, 
        		// Side awning
        		{0,7,3, 0,7,5, 0}, {8,7,3, 8,7,5, 1}, 
        		// Roof trim
        		{1,6,1, 1,6,1, 5}, {7,6,1, 7,6,1, 4}, 
        		{2,7,1, 2,7,1, 5}, {6,7,1, 6,7,1, 4}, 
        		{3,8,1, 3,8,1, 5}, {5,8,1, 5,8,1, 4}, 
        		{1,6,7, 1,6,7, 5}, {7,6,7, 7,6,7, 4}, 
        		{2,7,7, 2,7,7, 5}, {6,7,7, 6,7,7, 4}, 
        		{3,8,7, 3,8,7, 5}, {5,8,7, 5,8,7, 4}, 
        		// Roof awning trims
        		{0,6,3, 0,6,3, 6}, {0,6,5, 0,6,5, 7},
        		{8,6,3, 8,6,3, 6}, {8,6,5, 8,6,5, 7}, 
        		// Inside stairs
        		{5,1,5, 5,1,5, 1}, 
        		{4,2,5, 4,2,5, 1},
        		{3,3,5, 3,3,5, 1},
        		{2,4,5, 2,4,5, 1}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], uuvvwwo[3], uuvvwwo[4], uuvvwwo[5], biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uuvvwwo[6]%4)+(uuvvwwo[6]/4)*4, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uuvvwwo[6]%4)+(uuvvwwo[6]/4)*4, false);
            }
        	
        	// Torches
            for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright; 
            	// Front
            	{3,3,1,2}, {5,3,1,2}, {3,7,1,2}, {5,7,1,2},
            	// Back
            	{4,2,7,0}, {4,6,7,0}, 
            	// Left
            	{0,4,4,3}, 
            	// Right
            	{8,4,4,1},
            	
            	// Interior
            	{4,2,3,0}, {4,4,5,2}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
        	
        	// Interior
        	
            // Bookshelves
            for(int[] uuvvww : new int[][]{
            	{3,1,5, 3,1,5}, 
            	{2,1,5, 2,3,5}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.bookshelf, 0, Blocks.bookshelf, 0, false);	
            }
        	
            // Lectern
            this.placeBlockAtCurrentPosition(world, lecternBlock, lecternMeta, 2, 1, 3, structureBB);
            
            // Doors
            for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
            	{3, 1, 2, 2, 1, 1},
            	{5, 1, 2, 2, 1, 0},
            	{3, 5, 2, 2, 1, 1},
            	{5, 5, 2, 2, 1, 0},
            })
            {
            	for (int height=0; height<=1; height++)
            	{
            		this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
            				uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
            	}
            }
        	
            // Fences
            for(int[] uuvvww : new int[][]{
            	{2,1,0, 2,2,0}, {4,1,0, 4,2,0}, {6,1,0, 6,2,0}, 
            	{2,3,0, 6,3,0}, {2,5,0, 6,5,0},
            	{1,5,1, 1,5,1}, {7,5,1, 7,5,1}, 
            })
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
            
            // Front path
            for (int u : new int[]{2,4,6})
            {
            	this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, 0, structureBB);
            }
            for (int u : new int[]{3,5}) {for (int w=0; w<2; w++) {
            	StructureVillageVN.setPathSpecificBlock(world, this.start, 0, this.getXWithOffset(u, w), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(u, w));
            }}
            
            
            // Clear path for easier entry
            for (int u=3; u<=5; u++)
            {
            	this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, -1, structureBB);
            	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, -1, structureBB);
            	// Place dirt if the block to be set as path is empty
            	if (world.isAirBlock(this.getXWithOffset(u, -1), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(u, -1)))
            	{
                	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-1, -1, structureBB);
            	}
            	StructureVillageVN.setPathSpecificBlock(world, this.start, 0, this.getXWithOffset(u, -1), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(u, -1));
            }
        	
            
    		// Villagers
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	// Villager
            	int u = 3+random.nextInt(4);
            	int v = random.nextBoolean() ? 5 : 1;
            	int w = 3+random.nextInt(2);
            	            	
            	EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 1, 1, 0);
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
                world.spawnEntityInWorld(entityvillager);
            }
            
            return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 1;}
    }
    
    
    
    // --- Plains Mason's House --- //
    
    public static class PlainsMasonsHouse1 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public StartVN start;
    	
    	// Here are values to assign to the bounding box
    	private static final int STRUCTURE_WIDTH = 9;
    	private static final int STRUCTURE_HEIGHT = 7;
    	private static final int STRUCTURE_DEPTH = 8;
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	
    	private int averageGroundLevel = -1;
    	
        public PlainsMasonsHouse1() {}
        
        public PlainsMasonsHouse1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            this.start = start;
        }
        
        public static PlainsMasonsHouse1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new PlainsMasonsHouse1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
        		// Use these to re-center the side when determining ground level
            	int expandMinU = 0;
            	int shrinkMaxU = 2;
            	
            	if (this.averageGroundLevel < 0)
                {
            		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
            				// Set the bounding box version as this bounding box but with Y going from 0 to 512
            				new StructureBoundingBox(
            						// Modified to center onto front of house
            						this.boundingBox.minX+(this.coordBaseMode%2==0?expandMinU:0), this.boundingBox.minZ+(this.coordBaseMode%2==0?0:expandMinU),
            						this.boundingBox.maxX-(this.coordBaseMode%2==0?shrinkMaxU:0), this.boundingBox.maxZ-(this.coordBaseMode%2==0?0:shrinkMaxU)),
            				true, (byte)1, this.coordBaseMode);
            		
                    if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void

                    this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
                }
            }
        	
            Object[] blockObject;
            blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, start.materialType, start.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, start.materialType, start.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, start.materialType, start.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.stone_stairs, 0, start.materialType, start.biome); Block biomeStoneStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.oak_stairs, 0, start.materialType, start.biome); Block biomeWoodStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, start.materialType, start.biome); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), start.materialType, start.biome); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1]; // Perpendicular to you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, start.materialType, start.biome); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_door, 0, start.materialType, start.biome); Block biomeWoodDoorBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, start.materialType, start.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.trapdoor, 0, start.materialType, start.biome); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
        	blockObject = ModObjects.chooseModStonecutter(); Block stonecutterBlock = (Block) blockObject[0]; int stonecutterMeta = (Integer) blockObject[1];
        	
        	// Clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            	this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
            }}
            
            // Make foundation with blanks as empty air and F as foundation spaces
            String[] foundationPattern = new String[]{
	            	"         ",
	            	" FFFFFFF ",
	            	" FFFFFFF ",
	            	" FFFFFFF ",
	            	" FFFFFFF ",
	            	" FFFFFFF ",
	            	"  FFFFFF ",
	            	"   FFFFF ",
            };
        	for (int w=0; w < STRUCTURE_DEPTH; w++) {for (int u=0; u < STRUCTURE_WIDTH; u++) {
        		
            		if (foundationPattern[STRUCTURE_DEPTH-1-w].substring(u, u+1).toUpperCase().equals("F"))
            		{
            			// If marked with F: fill with dirt foundation
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-1, w, structureBB);
            		}
            		else if (world.getBlock(this.getXWithOffset(u, w), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(u, w))==biomeDirtBlock)
            		{
            			// Otherwise, if dirt, add dirt foundation and then cap with grass:
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
            		}
                }
            }
            
            
            // Stone
            for(int[] uuvvww : new int[][]{
            	// Floor
            	{1,0,2, 7,0,6},
            	{3,0,1, 7,0,1},
            	{4,0,0, 7,0,0},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
            }
            
            // Planks
            for(int[] uuvvww : new int[][]{
            	// Embedded into the floor
            	{3,0,4, 5,0,4},
            	// Roof
            	{4,6,1, 4,6,7},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }

            
            // Stained terracotta
            for(int[] uuvvww : new int[][]{
            	// Front
            	{2,1,2, 6,3,2}, {3,5,2, 5,5,2},
            	// Back
            	{2,1,6, 6,3,6}, {3,5,6, 5,5,6},
            	// Left
            	{1,1,3, 1,3,5},
            	// Right
            	{7,1,3, 7,3,5},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter? this.start.townColor2:0, Blocks.stained_hardened_clay, GeneralConfig.decorateVillageCenter? this.start.townColor2:0, false);	
            }
            
            
            // Logs
            // Vertical beams
            for(int[] uuvvww : new int[][]{
            	{1,1,2, 1,3,2}, 
            	{1,1,6, 1,3,6}, 
            	{7,1,2, 7,3,2}, 
            	{7,1,6, 7,3,6}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);
            }
            // Across beams
            for(int[] uuvvww : new int[][]{
            	{2,4,2, 6,4,2},
            	{2,4,6, 6,4,6},
            })
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, false);
            }
            
            // Windows
        	for (int[] uvw : new int[][]{
        			{5,2,2},
        			{5,2,6},
        			{3,2,6},
        			{1,2,4},
        			{7,2,4},
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
        	// Various wooden stairs
        	for (int[] uuvvwwo : new int[][]{ // Orientation - 0:leftward, 1:rightward, 3:backward, 2:forward; +4:inverted
        		
        		// Roof
        		{0,3,1, 0,3,7, 0}, {8,3,1, 8,3,7, 1},
        		{1,4,1, 1,4,7, 0}, {7,4,1, 7,4,7, 1}, 
        		{2,5,1, 2,5,7, 0}, {6,5,1, 6,5,7, 1}, 
        		{3,6,1, 3,6,7, 0}, {5,6,1, 5,6,7, 1},
        		// Trim
        		{1,3,1, 1,3,1, 5}, {7,3,1, 7,3,1, 4},
        		{2,4,1, 2,4,1, 5}, {6,4,1, 6,4,1, 4},
        		{3,5,1, 3,5,1, 5}, {5,5,1, 5,5,1, 4},
        		{1,3,7, 1,3,7, 5}, {7,3,7, 7,3,7, 4},
        		{2,4,7, 2,4,7, 5}, {6,4,7, 6,4,7, 4},
        		{3,5,7, 3,5,7, 5}, {5,5,7, 5,5,7, 4},
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], uuvvwwo[3], uuvvwwo[4], uuvvwwo[5], biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uuvvwwo[6]%4)+(uuvvwwo[6]/4)*4, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uuvvwwo[6]%4)+(uuvvwwo[6]/4)*4, false);
            }

            // Fences
            for(int[] uuvvww : new int[][]{
            	{4,1,0, 7,1,0}, {7,1,1, 7,1,1}, 
            })
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
            
        	// Torches
            for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright; 
            	// Porch
            	{4,2,0,-1}, {7,2,0,-1},
            	{4,4,3,0}, {4,4,5,2}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
        	
        	// Interior
        	
            // Clay
            for(int[] uvw : new int[][]{
            	{2,1,5}, 
            	{3,1,5}, 
            	{2,2,5}, 
            	{2,1,4}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.clay, 0, uvw[0], uvw[1], uvw[2], structureBB);	
            }
            // Terracotta
            for(int[] uvw : new int[][]{
            	{4,1,5}, 
            	{2,3,5}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.hardened_clay, 0, uvw[0], uvw[1], uvw[2], structureBB);	
            }
        	
            // Stone Cutter
            this.placeBlockAtCurrentPosition(world, stonecutterBlock, stonecutterMeta, 6, 1, 4, structureBB);
            
            // Doors
            for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
            	{3, 1, 2, 2, 1, 1},
            })
            {
            	for (int height=0; height<=1; height++)
            	{
            		this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
            				uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
            	}
            }
        	
            // Front stairs
        	for (int[] uuvvwwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward; +4:inverted
        		{3,0,0, 3}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uuvvwwo[3]%4)+(uuvvwwo[3]/4)*4, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], structureBB);
            }
            
            
        	// Flower planter out front
        	this.placeBlockAtCurrentPosition(world, Blocks.grass, 0, 2, 0, 1, structureBB); // Sod
        	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, this.coordBaseMode%2==0 ? 6 : 4, 1, 0, 1, structureBB); // Left
        	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, (new int[]{4, 7, 5, 6})[this.coordBaseMode], 2, 0, 0, structureBB); // Front
        	
        	// Flowers on top
    		int flowerindex = random.nextInt(10 + (Block.getBlockFromName(ModObjects.flowerUTD)==null ? 0 : 2));
    		// 0-8 is "red" flower
    		// 9 is a basic yellow flower
    		// 10-11 are the flowers from UpToDateMod
    		Block flowerblock = flowerindex == 9 ? Blocks.yellow_flower : flowerindex > 9 ? Block.getBlockFromName(ModObjects.flowerUTD) : Blocks.red_flower;
    		int flowermeta = new int[]{0,1,2,3,4,5,6,7,8,0,0,1}[flowerindex];
    		
    		this.placeBlockAtCurrentPosition(world, flowerblock, flowermeta, 2, 1, 1, structureBB);
        	
    		
        	
            // Clear path for easier entry
        	this.clearCurrentPositionBlocksUpwards(world, 3, GROUND_LEVEL, -1, structureBB);
        	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, 3, GROUND_LEVEL-2, -1, structureBB);
        	// Place dirt if the block to be set as path is empty
        	if (world.isAirBlock(this.getXWithOffset(3, -1), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(3, -1)))
        	{
            	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, 3, GROUND_LEVEL-1, -1, structureBB);
        	}
        	StructureVillageVN.setPathSpecificBlock(world, this.start, 0, this.getXWithOffset(3, -1), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(3, -1));
        	
            
    		// Villagers
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	// Villager
            	int u = 3+random.nextInt(3);
            	int v = 1;
            	int w = 3+random.nextInt(2);
            	            	
            	EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 3, 4, 0);
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
                world.spawnEntityInWorld(entityvillager);
            }
            
            return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 3;}
    }
    
    
    
    // --- Plains Medium House 1 --- //
    
    public static class PlainsMediumHouse1 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public StartVN start;
    	
    	// Here are values to assign to the bounding box
    	private static final int STRUCTURE_WIDTH = 11;
    	private static final int STRUCTURE_HEIGHT = 8;
    	private static final int STRUCTURE_DEPTH = 13;
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	
    	private int averageGroundLevel = -1;
    	
        public PlainsMediumHouse1() {}
        
        public PlainsMediumHouse1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            this.start = start;
        }
        
        public static PlainsMediumHouse1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new PlainsMediumHouse1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
        		// Use these to re-center the side when determining ground level
            	int expandMinU = 0;
            	int shrinkMaxU = 4;
            	
            	if (this.averageGroundLevel < 0)
                {
            		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
            				// Set the bounding box version as this bounding box but with Y going from 0 to 512
            				new StructureBoundingBox(
            						// Modified to center onto front of house
            						this.boundingBox.minX+(this.coordBaseMode%2==0?expandMinU:0), this.boundingBox.minZ+(this.coordBaseMode%2==0?0:expandMinU),
            						this.boundingBox.maxX-(this.coordBaseMode%2==0?shrinkMaxU:0), this.boundingBox.maxZ-(this.coordBaseMode%2==0?0:shrinkMaxU)),
            				true, (byte)1, this.coordBaseMode);
            		
                    if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void

                    this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
                }
            }
        	
        	
            Object[] blockObject;
            blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, start.materialType, start.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, start.materialType, start.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, start.materialType, start.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, start.materialType, start.biome); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_door, 0, start.materialType, start.biome); Block biomeWoodDoorBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, start.materialType, start.biome); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), start.materialType, start.biome); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1]; // Toward you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), start.materialType, start.biome); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1]; // Perpendicular to you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.oak_stairs, 0, start.materialType, start.biome); Block biomeWoodStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, start.materialType, start.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_pressure_plate, 0, start.materialType, start.biome); Block biomeWoodPressurePlateBlock = (Block)blockObject[0]; int biomeWoodPressurePlateMeta = (Integer)blockObject[1];
        	
        	// TODO - stripped wood
        	// For stripped logs specifically
        	Block biomeStrippedLogVerticBlock = biomeLogVertBlock; int biomeStrippedLogVerticMeta = biomeLogVertMeta;
        	Block biomeStrippedLogHorAlongBlock = biomeLogHorAlongBlock; int biomeStrippedLogHorAlongMeta = biomeLogHorAlongMeta;
        	Block biomeStrippedLogHorAcrossBlock = biomeLogHorAcrossBlock; int biomeStrippedLogHorAcrossMeta = biomeLogHorAcrossMeta;
        	
        	// Try to see if stripped logs exist
        	if (biomeStrippedLogVerticBlock==Blocks.log || biomeStrippedLogVerticBlock==Blocks.log2)
        	{
            	if (biomeLogVertBlock == Blocks.log)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 1+(this.coordBaseMode%2==0? 0:1)); biomeStrippedLogHorAlongBlock = (Block)blockObject[0]; biomeStrippedLogHorAlongMeta = (Integer)blockObject[1];
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 1+(this.coordBaseMode%2==0? 1:0)); biomeStrippedLogHorAcrossBlock = (Block)blockObject[0]; biomeStrippedLogHorAcrossMeta = (Integer)blockObject[1];
            	}
            	else if (biomeLogVertBlock == Blocks.log2)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 1+(this.coordBaseMode%2==0? 0:1)); biomeStrippedLogHorAlongBlock = (Block)blockObject[0]; biomeStrippedLogHorAlongMeta = (Integer)blockObject[1];
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 1+(this.coordBaseMode%2==0? 1:0)); biomeStrippedLogHorAcrossBlock = (Block)blockObject[0]; biomeStrippedLogHorAcrossMeta = (Integer)blockObject[1];
            	}
        	}
        	
        	// Clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            	this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
            }}
            
            // Make foundation with blanks as empty air and F as foundation spaces
            String[] foundationPattern = new String[]{
            		"           ",
            		"   FFFFFFF ",
            		"   FFFFFFF ",
            		"   FFFFFFF ",
            		"   FFFFFFF ",
            		"   FFFFFFF ",
            		" FFFFFFFFF ",
            		" FFFFFFFFF ",
            		" FFFFFFFFF ",
            		" FFFFFFFFF ",
            		" FFFFFFFFF ",
            		" FFFFFFFFF ",
            		"   FF      ",
            };
        	for (int w=0; w < STRUCTURE_DEPTH; w++) {for (int u=0; u < STRUCTURE_WIDTH; u++) {
        		
            		if (foundationPattern[STRUCTURE_DEPTH-1-w].substring(u, u+1).toUpperCase().equals("F"))
            		{
            			// If marked with F: fill with dirt foundation
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-1, w, structureBB);
            		}
            		else if (world.getBlock(this.getXWithOffset(u, w), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(u, w))==biomeDirtBlock)
            		{
            			// Otherwise, if dirt, add dirt foundation and then cap with grass:
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
            		}
                }
            }
            
            
            // Stone
            for(int[] uuvvww : new int[][]{
            	// Front wall
            	{2,1,1, 8,2,1},
            	// Left wall
            	{1,1,2, 1,2,5},
            	{1,4,2, 1,4,5},
            	{1,5,3, 1,5,4},
            	{1,1,6, 3,2,6},
            	{3,1,7, 3,2,10},
            	{3,4,7, 3,4,10},
            	// Back wall
            	{4,1,11, 8,4,11},
            	{6,6,11, 6,6,11},
            	// Right wall
            	{9,1,2, 9,2,10},
            	{9,4,2, 9,4,10},
            	{9,5,3, 9,5,4},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
            }
            
            
            // Planks
            for(int[] uuvvww : new int[][]{
            	// Embedded into the floor
            	{2,1,2, 8,1,5},
            	{4,1,6, 8,1,10},
            	// Wall
            	{1,3,6, 3,4,6},
            	// Roof
            	{0,4,1, 10,4,1},
            	{0,5,2, 10,5,2},
            	{0,6,3, 10,6,3},
            	{0,6,4, 10,6,4},
            	{0,5,5, 4,5,5},
            	{4,5,5, 4,5,12},
            	{5,6,5, 5,6,12},
            	{6,7,5, 6,7,12},
            	{7,6,5, 7,6,12},
            	{8,5,3, 8,5,12},
            	
            	// Roof touchup
            	// Back
            	{3,4,12, 3,4,12},
            	{9,4,12, 9,4,12},
            	// Top
            	{5,7,4, 7,7,4},
            	{4,6,5, 4,6,5},
            	{3,5,6, 3,5,6},
            	{2,4,7, 2,4,7},
            	{8,6,5, 8,6,5},
            	// Left
            	{0,4,6, 0,4,6},
            	{10,4,6, 10,4,6},
            	// Right
            	{9,5,5, 10,5,5},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
            
            
            // Stripped logs
            // Vertical
            for(int[] uuvvww : new int[][]{
            	{1,1,1, 1,4,1},
            	{9,1,1, 9,4,1},
            	{1,1,6, 1,4,6},
            	{3,1,11, 3,4,11},
            	{9,1,11, 9,4,11},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);	
            }
            // Stripped log across
        	for (int[] uvw : new int[][]{
    			{9,3,6},
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeStrippedLogHorAcrossBlock, biomeStrippedLogHorAcrossMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            // Stripped log along
        	for (int[] uvw : new int[][]{
    			{2,3,1},
    			{4,3,1},
    			{8,3,1},
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeStrippedLogHorAlongBlock, biomeStrippedLogHorAlongMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Logs
            // Window frames
        	for (int[] uvw : new int[][]{
    			{1,3,2}, {1,3,5},
    			{9,3,2}, {9,3,5},
    			{9,3,7}, {9,3,10},
    			{3,3,7}, {3,3,10},
    			{5,3,1}, {7,3,1},
    			{5,5,11}, {7,5,11},
    			})
	        {
	    		this.placeBlockAtCurrentPosition(world, biomeLogVertBlock, biomeLogVertMeta, uvw[0], uvw[1], uvw[2], structureBB);
	        }
            
            
            // Windows
        	for (int[] uvw : new int[][]{
    			{1,3,3}, {1,3,4},
    			{9,3,3}, {9,3,4},
    			{9,3,8}, {9,3,9},
    			{3,3,8}, {3,3,9},
    			{6,3,1},
    			{6,5,11},
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
        	
        	// Various wooden stairs
        	for (int[] uuvvwwo : new int[][]{ // Orientation - 0:leftward, 1:rightward, 3:backward, 2:forward; +4:inverted
        		
        		// Roof
        		// Front
        		{0,4,0, 10,4,0, 3},
        		{0,5,1, 10,5,1, 3},
        		{0,6,2, 10,6,2, 3},
        		{0,7,3, 10,7,3, 3},
        		//Back
        		{0,4,7, 1,4,7, 2},
        		{0,5,6, 2,5,6, 2},
        		{0,6,5, 3,6,5, 2},
        		{0,7,4, 4,7,4, 2},
        		{10,4,7, 10,4,7, 2},
        		{9,5,6, 10,5,6, 2},
        		{9,6,5, 10,6,5, 2},
        		{8,7,4, 10,7,4, 2},
        		// Left
        		{2,4,8, 2,4,12, 0},
        		{3,5,7, 3,5,12, 0},
        		{4,6,6, 4,6,12, 0},
        		{5,7,5, 5,7,12, 0},
        		{10,4,8, 10,4,12, 1},
        		{9,5,7, 9,5,12, 1},
        		{8,6,6, 8,6,12, 1},
        		{7,7,5, 7,7,12, 1},
            	// Benches
            	{5,2,2, 5,2,2, 1},
            	{7,2,2, 7,2,2, 0},
            	// Front steps
            	{3,1,0, 3,1,0, 3},
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], uuvvwwo[3], uuvvwwo[4], uuvvwwo[5], biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uuvvwwo[6]%4)+(uuvvwwo[6]/4)*4, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uuvvwwo[6]%4)+(uuvvwwo[6]/4)*4, false);
            }
        	
        	
            // Tables: fence with pressure plate
        	for (int[] uvw : new int[][]{
    			{6,2,2},
    			{8,2,6},
    			{8,2,10},
    			{4,2,10},
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uvw[0], uvw[1], uvw[2], structureBB);
        		this.placeBlockAtCurrentPosition(world, biomeWoodPressurePlateBlock, biomeWoodPressurePlateMeta, uvw[0], uvw[1]+1, uvw[2], structureBB);
            }
            
            
        	// Torches
            for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright; 
            	// Front
            	{2,3,0,2}, {4,3,0,2},
            	// Back
            	{6,3,12,0},
            	// Interior
            	{3,4,2,0}, {8,4,6,3}, {6,3,10,2},
            	}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
        	
        	// Interior
            
            // Beds
            for (int[] uvwm : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{5,2,9,2},
            	{7,2,9,2},
            })
            {
            	for (boolean isHead : new boolean[]{false, true})
            	{
            		int orientation = uvwm[3];
            		int u = uvwm[0] + (isHead?(new int[]{0,-1,0,1}[orientation]):0);
            		int v = uvwm[1];
            		int w = uvwm[2] + (isHead?(new int[]{-1,0,1,0}[orientation]):0);
                	ModObjects.setModBedBlock(world,
                			this.getXWithOffset(u, w),
                			this.getYWithOffset(v),
                			this.getZWithOffset(u, w),
                			StructureVillageVN.getBedOrientationMeta(orientation, this.coordBaseMode, isHead),
                			GeneralConfig.decorateVillageCenter ? this.start.townColor2 : 0); // Goes by wool meta	
            	}
            }
            
            // Doors
            for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
            	{3, 2, 1, 2, 1, 1},
            })
            {
            	for (int height=0; height<=1; height++)
            	{
            		this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
            				uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
            	}
            }
        	
            // Grass block is supposed to be under front stairs, but that's underneath stairs so I'm moving it to the side
            for (int[] uvw : new int[][]{
    			{4,0,0},
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
        	
            // Clear path for easier entry
            int pathU = 3;
            int pathV = GROUND_LEVEL-1;
            int pathW = -1;
            
            this.clearCurrentPositionBlocksUpwards(world, pathU, pathV+1, pathW, structureBB);
        	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, pathU, pathV-1, pathW, structureBB);
        	// Place dirt if the block to be set as path is empty
        	if (world.isAirBlock(this.getXWithOffset(pathU, pathW), this.getYWithOffset(pathV), this.getZWithOffset(pathU, pathW)))
        	{
            	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, pathU, pathV, pathW, structureBB);
        	}
        	StructureVillageVN.setPathSpecificBlock(world, this.start, 0, this.getXWithOffset(pathU, pathW), this.getYWithOffset(pathV), this.getZWithOffset(pathU, pathW));
        	
        	

            // Decor
            for (int decorUVW[] : new int[][]{
            	{0, GROUND_LEVEL, 10},
            	})
            {
                // Add foundations
            	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, decorUVW[0], decorUVW[1]-1, decorUVW[2], structureBB);
        		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, decorUVW[0], decorUVW[1]-2, decorUVW[2], structureBB);
        		
                // The actual decor
        		for (int j=0; j<decorUVW.length; j++)
                {
        			int decorHeightY = decorUVW[1];
        			
        			// Set random seed
                	Random randomFromXYZ = new Random();
                	randomFromXYZ.setSeed(
        					world.getSeed() +
        					FunctionsVN.getUniqueLongForXYZ(
        							this.getXWithOffset(decorUVW[0], decorUVW[2]),
        							this.getYWithOffset(decorHeightY),
        							this.getZWithOffset(decorUVW[0], decorUVW[2])
        							)
                			);
                	
                	// Get ground level
                	if (this.decorHeightY.size()<(j+1))
                	{
                		// There are fewer stored ground levels than this decor number, so this is being generated for the first time.
                		// Add new ground level
                		//decorHeightY = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(uvw[0], uvw[2]), this.getZWithOffset(uvw[0], uvw[2]))-this.boundingBox.minY;
                		this.decorHeightY.add(decorHeightY);
                	}
                	else
                	{
                		// There is already (presumably) a value for this ground level, so this decor is being multiply generated.
                		// Retrieve ground level
                		decorHeightY = this.decorHeightY.get(j);
                	}
                	
                	
                	// Generate decor
                	ArrayList<BlueprintData> decorBlueprint = getRandomPlainsDecorBlueprint(this.start, this.coordBaseMode, randomFromXYZ);
                	
                	for (BlueprintData b : decorBlueprint)
                	{
                		// Place block indicated by blueprint
                		this.placeBlockAtCurrentPosition(world, b.getBlock(), b.getMeta(), decorUVW[0]+b.getUPos(), decorHeightY+b.getVPos(), decorUVW[2]+b.getWPos(), structureBB);
                		
                		// Fill below if flagged
                		if ((b.getfillFlag()&1)!=0)
                		{
                			this.func_151554_b(world, b.getBlock(), b.getMeta(), decorUVW[0]+b.getUPos(), decorHeightY+b.getVPos()-1, decorUVW[2]+b.getWPos(), structureBB);
                		}
                		
                		// Clear above if flagged
                		if ((b.getfillFlag()&2)!=0)
                		{
                			this.clearCurrentPositionBlocksUpwards(world, decorUVW[0]+b.getUPos(), decorHeightY+b.getVPos()+1, decorUVW[2]+b.getWPos(), structureBB);
                		}            		
                	}
                }
            }
            
            
    		// Villagers
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
        		for (int[] ia : new int[][]{
        			{7, 2, 4, -1, 0},
        			{6, 2, 6, -1, 0},
        			})
        		{
        			EntityVillager entityvillager = new EntityVillager(world);
        			entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], Math.min(random.nextInt(24001)-12000,0));
        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)this.getYWithOffset(ia[1]) + 1.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
                    		random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(entityvillager);
        		}
            }
            
            
            return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    
    // --- Plains Medium House 2 --- //
    
    public static class PlainsMediumHouse2 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public StartVN start;
    	
    	// Here are values to assign to the bounding box
    	private static final int STRUCTURE_WIDTH = 13;
    	private static final int STRUCTURE_HEIGHT = 6;
    	private static final int STRUCTURE_DEPTH = 7;
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	
    	private int averageGroundLevel = -1;
    	
        public PlainsMediumHouse2() {}
        
        public PlainsMediumHouse2(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            this.start = start;
        }
        
        public static PlainsMediumHouse2 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new PlainsMediumHouse2(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
        		// Use these to re-center the side when determining ground level
            	int expandMinU = 0;
            	int shrinkMaxU = 0;
            	
            	if (this.averageGroundLevel < 0)
                {
            		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
            				// Set the bounding box version as this bounding box but with Y going from 0 to 512
            				new StructureBoundingBox(
            						// Modified to center onto front of house
            						this.boundingBox.minX+(this.coordBaseMode%2==0?expandMinU:0), this.boundingBox.minZ+(this.coordBaseMode%2==0?0:expandMinU),
            						this.boundingBox.maxX-(this.coordBaseMode%2==0?shrinkMaxU:0), this.boundingBox.maxZ-(this.coordBaseMode%2==0?0:shrinkMaxU)),
            				true, (byte)1, this.coordBaseMode);
            		
                    if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void

                    this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
                }
            }
        	
        	
            Object[] blockObject;
            blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, start.materialType, start.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, start.materialType, start.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, start.materialType, start.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, start.materialType, start.biome); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_door, 0, start.materialType, start.biome); Block biomeWoodDoorBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, start.materialType, start.biome); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), start.materialType, start.biome); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1]; // Toward you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), start.materialType, start.biome); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1]; // Perpendicular to you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.oak_stairs, 0, start.materialType, start.biome); Block biomeWoodStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, start.materialType, start.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_pressure_plate, 0, start.materialType, start.biome); Block biomeWoodPressurePlateBlock = (Block)blockObject[0]; int biomeWoodPressurePlateMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.stone_stairs, 0, start.materialType, start.biome); Block biomeStoneStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.double_wooden_slab, 0, start.materialType, start.biome); Block biomeWoodDoubleSlabBlock = (Block)blockObject[0]; int biomeWoodDoubleSlabMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_slab, 8, start.materialType, start.biome); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];

        	// Clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            	this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
            }}
            
            // Make foundation with blanks as empty air and F as foundation spaces
            String[] foundationPattern = new String[]{
            		"             ",
            		" FFFFFFFFFFF ",
            		" FFFFFFFFFFF ",
            		" FFFFFFFFFFF ",
            		" FFFFFFFFFFF ",
            		" FFFFFFFFFFF ",
            		"             ",
            };
        	for (int w=0; w < STRUCTURE_DEPTH; w++) {for (int u=0; u < STRUCTURE_WIDTH; u++) {
        		
            		if (foundationPattern[STRUCTURE_DEPTH-1-w].substring(u, u+1).toUpperCase().equals("F"))
            		{
            			// If marked with F: fill with dirt foundation
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-1, w, structureBB);
            		}
            		else if (world.getBlock(this.getXWithOffset(u, w), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(u, w))==biomeDirtBlock)
            		{
            			// Otherwise, if dirt, add dirt foundation and then cap with grass:
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
            		}
                }
            }
            
            
            // Stone
            for(int[] uuvvww : new int[][]{
            	// Front wall
            	{2,0,1, 4,3,1}, {8,0,1, 10,3,1}, {6,3,1, 6,3,1},
            	// Back wall
            	{2,0,5, 4,3,5}, {6,0,5, 6,3,5}, {8,0,5, 10,3,5},
            	// Left
            	{1,0,2, 1,3,4}, 
            	// Right
            	{11,0,2, 11,3,4},
            	// Floor
            	{6,0,1, 6,0,4}, {3,0,3, 4,0,3}, {8,0,3, 9,0,3},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
            }
            
            
            // Planks
            for(int[] uuvvww : new int[][]{
            	// Embedded into the floor
            	{2,0,2, 2,0,4}, {3,0,2, 4,0,2}, {3,0,4, 4,0,4}, {5,0,2, 5,0,4},
            	{7,0,2, 7,0,4}, {8,0,2, 9,0,2}, {8,0,4, 9,0,4}, {10,0,2, 10,0,4},
            	// Double roof
            	{3,5,0, 3,5,6}, {9,5,0, 9,5,6},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
            
            // Wooden slabs and double slabs
            this.fillWithMetadataBlocks(world, structureBB, 6, 4, 0, 6, 4, 6, biomeWoodDoubleSlabBlock, biomeWoodDoubleSlabMeta, biomeWoodDoubleSlabBlock, biomeWoodDoubleSlabMeta, false);
        	for (int[] uvw : new int[][]{
    			{6,3,0},
    			{6,3,6},
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Logs
        	// Vertical
            for(int[] uuvvww : new int[][]{
            	{1,0,1, 1,3,1}, {5,0,1, 5,3,1}, {7,0,1, 7,3,1}, {11,0,1, 11,3,1},
            	{1,0,5, 1,3,5}, {5,0,5, 5,3,5}, {7,0,5, 7,3,5}, {11,0,5, 11,3,5},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);	
            }
        	// Across
            for(int[] uuvvww : new int[][]{
            	{2,4,1, 4,4,1}, {8,4,1, 10,4,1},
            	{2,4,5, 4,4,5}, {8,4,5, 10,4,5},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, false);	
            }
            
        	
        	// Various wooden stairs
        	for (int[] uuvvwwo : new int[][]{ // Orientation - 0:leftward, 1:rightward, 3:backward, 2:forward; +4:inverted
        		
        		// Roof
        		{0,3,0, 0,3,6, 0},
        		{1,4,0, 1,4,6, 0},
        		{2,5,0, 2,5,6, 0},
        		
        		{5,4,0, 5,4,6, 1},
        		{4,5,0, 4,5,6, 1},
        		
        		{7,4,0, 7,4,6, 0},
        		{8,5,0, 8,5,6, 0},
        		
        		{12,3,0, 12,3,6, 1},
        		{11,4,0, 11,4,6, 1},
        		{10,5,0, 10,5,6, 1},
        		
        		// Trim
        		{1,3,0, 1,3,0, 5},
        		{2,4,0, 2,4,0, 5},
        		{4,4,0, 4,4,0, 4},
        		{8,4,0, 8,4,0, 5},
        		{10,4,0, 10,4,0, 4},
        		{11,3,0, 11,3,0, 4},
        		{1,3,6, 1,3,6, 5},
        		{2,4,6, 2,4,6, 5},
        		{4,4,6, 4,4,6, 4},
        		{8,4,6, 8,4,6, 5},
        		{10,4,6, 10,4,6, 4},
        		{11,3,6, 11,3,6, 4},
        		
        		// A single bench
        		{2,1,4, 2,1,4, 1},
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], uuvvwwo[3], uuvvwwo[4], uuvvwwo[5], biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uuvvwwo[6]%4)+(uuvvwwo[6]/4)*4, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uuvvwwo[6]%4)+(uuvvwwo[6]/4)*4, false);
            }
        	
            
            // Windows
        	for (int[] uvw : new int[][]{
    			{3,2,1}, {9,2,1},
    			{3,2,5}, {9,2,5},
    			{1,2,3}, {11,2,3},
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
        	
            // Tables: fence with pressure plate
        	for (int[] uvw : new int[][]{
    			{3,1,4},
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uvw[0], uvw[1], uvw[2], structureBB);
        		this.placeBlockAtCurrentPosition(world, biomeWoodPressurePlateBlock, biomeWoodPressurePlateMeta, uvw[0], uvw[1]+1, uvw[2], structureBB);
            }
            
            
        	// Torches
            for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright; 
            	// Front
            	{5,2,0,2}, {7,2,0,2},
            	// Back
            	{6,2,6,0},
            	// Interior
            	{5,2,4,2}, {7,2,4,2},
            	}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
        	
        	// Interior
            
            // Beds
            for (int[] uvwm : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{3,1,2,1},
            	{9,1,4,3},
            })
            {
            	for (boolean isHead : new boolean[]{false, true})
            	{
            		int orientation = uvwm[3];
            		int u = uvwm[0] + (isHead?(new int[]{0,-1,0,1}[orientation]):0);
            		int v = uvwm[1];
            		int w = uvwm[2] + (isHead?(new int[]{-1,0,1,0}[orientation]):0);
                	ModObjects.setModBedBlock(world,
                			this.getXWithOffset(u, w),
                			this.getYWithOffset(v),
                			this.getZWithOffset(u, w),
                			StructureVillageVN.getBedOrientationMeta(orientation, this.coordBaseMode, isHead),
                			GeneralConfig.decorateVillageCenter ? this.start.townColor : 4); // Goes by wool meta	
            	}
            }
            
            
            // Doors
            for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
            	{6, 1, 1, 2, 1, 0},
            })
            {
            	for (int height=0; height<=1; height++)
            	{
            		this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
            				uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
            	}
            }
        	
            
            // Front stairs
        	for (int[] uuvvwwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward; +4:inverted
        		// Left
        		{6,0,0, 3}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uuvvwwo[3]%4)+(uuvvwwo[3]/4)*4, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], structureBB);
            }
        	
            
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 10;
        	int chestV = 1;
        	int chestW = 2;
        	int chestO = 3; // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
        	this.placeBlockAtCurrentPosition(world, Blocks.chest, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo("vn_plains_house");
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
        	
        	
            // Clear path for easier entry
            int pathU = 6;
            int pathV = GROUND_LEVEL;
            int pathW = -1;
            
            this.clearCurrentPositionBlocksUpwards(world, pathU, pathV, pathW, structureBB);
        	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, pathU, pathV-1, pathW, structureBB);
        	// Place dirt if the block to be set as path is empty
        	if (world.isAirBlock(this.getXWithOffset(pathU, pathW), this.getYWithOffset(pathV-1), this.getZWithOffset(pathU, pathW)))
        	{
            	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, pathU, pathV-1, pathW, structureBB);
        	}
        	StructureVillageVN.setPathSpecificBlock(world, this.start, 0, this.getXWithOffset(pathU, pathW), this.getYWithOffset(pathV-1), this.getZWithOffset(pathU, pathW));
        	
        	
    		// Villagers
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
        		for (int[] ia : new int[][]{
        			{4, 1, 3, -1, 0},
        			{8, 1, 3, -1, 0},
        			})
        		{
        			EntityVillager entityvillager = new EntityVillager(world);
        			entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], Math.min(random.nextInt(24001)-12000,0));
        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)this.getYWithOffset(ia[1]) + 1.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
                    		random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(entityvillager);
        		}
            }
            
            
            return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    
    // --- Plains Market 1 --- //
    
    public static class PlainsMeetingPoint4 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public StartVN start;
    	
    	// Here are values to assign to the bounding box
    	private static final int STRUCTURE_WIDTH = 16;
    	private static final int STRUCTURE_HEIGHT = 7;
    	private static final int STRUCTURE_DEPTH = 10;
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	
    	private int averageGroundLevel = -1;
    	
        public PlainsMeetingPoint4() {}
        
        public PlainsMeetingPoint4(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            this.start = start;
        }
        
        public static PlainsMeetingPoint4 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new PlainsMeetingPoint4(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
        		// Use these to re-center the side when determining ground level
            	int expandMinU = 0;
            	int shrinkMaxU = 0;
            	
            	if (this.averageGroundLevel < 0)
                {
            		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
            				// Set the bounding box version as this bounding box but with Y going from 0 to 512
            				new StructureBoundingBox(
            						// Modified to center onto front of house
            						this.boundingBox.minX+(this.coordBaseMode%2==0?expandMinU:0), this.boundingBox.minZ+(this.coordBaseMode%2==0?0:expandMinU),
            						this.boundingBox.maxX-(this.coordBaseMode%2==0?shrinkMaxU:0), this.boundingBox.maxZ-(this.coordBaseMode%2==0?0:shrinkMaxU)),
            				true, (byte)1, this.coordBaseMode);
            		
                    if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void

                    this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
                }
            }
        	
        	
            Object[] blockObject;
            blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, start.materialType, start.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, start.materialType, start.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, start.materialType, start.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.mossy_cobblestone, 0, start.materialType, start.biome); Block biomeMossyCobblestoneBlock = (Block)blockObject[0]; int biomeMossyCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, start.materialType, start.biome); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, start.materialType, start.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_slab, 0, start.materialType, start.biome); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(ModObjects.chooseModPathBlock(), 0, start.materialType, start.biome); Block grassPathBlock = (Block) blockObject[0]; int grassPathMeta = (Integer) blockObject[1]; 
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.standing_sign, 0, start.materialType, start.biome); Block biomeStandingSignBlock = (Block)blockObject[0];
        	
        	
        	// Clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            	this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
            	// Make dirt foundation
            	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
            	// top with grass
            	this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
            }}
            
            
            // Stone
            for(int[] uw : new int[][]{
            	{3, 3}, {3, 6}, 
            	{4, 1}, {4, 4}, 
            	{5, 4},
            	{6, 6},
            	{7, 2},
            	{8, 0}, {8, 4}, {8, 5},
            	{10, 2}, {10, 4}, {10, 7},
            	{11, 3},
            	{12, 4}, {12, 6},
            	{13, 6},
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, uw[0], GROUND_LEVEL-1, uw[1], structureBB);	
            }
            // Mossy cobblestone
            this.placeBlockAtCurrentPosition(world, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, 13, GROUND_LEVEL-1, 1, structureBB);
            // Grass path
            for(int[] uw : new int[][]{
            	{1, 0}, 
            	{2, 1}, {2, 7}, 
            	{3, 4}, {3, 7},
            	{4, 2}, {4, 3}, {4, 5}, {4, 7}, 
            	{5, 0}, {5, 2}, {5, 5}, {5, 6}, {5, 8},
            	{6, 1}, {6, 2}, {6, 3}, {6, 4}, {6, 5}, 
            	{7, 0}, {7, 3}, {7, 5}, {7, 8}, 
            	{8, 1}, {8, 3}, {8, 6},
            	{9, 2}, {9, 5}, 
            	{10, 0}, {10, 1}, {10, 6}, {10, 8},
            	{11, 0}, {11, 2}, {11, 5}, 
            	{12, 1}, {12, 3}, {12, 7},
            	{13, 6}, {12, 8},
            	{14, 3}, {14, 6}, 
            	{15, 7}, 
            	})
            {
            	StructureVillageVN.setPathSpecificBlock(world, this.start, 0, this.getXWithOffset(uw[0], uw[1]), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(uw[0], uw[1]));	
            }
            // Dirt
            for(int[] uuww : new int[][]{
            	{2,0,2, 2,0,5}, 
            	{13,0,2, 13,0,5}, 
            	})
            {
                this.fillWithMetadataBlocks(world, structureBB, uuww[0], uuww[1], uuww[2], uuww[3], uuww[4], uuww[5], biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);
            }
            
            
            // Tall grass
            for (int[] uw : new int[][]{
            	{0,0}, {0,2}, {0,5}, {0,7}, 
            	{1,3}, {1,4}, {1,9}, 
            	{3,0}, {3,9}, 
            	{4,6}, {4,8}, {4,9}, 
            	{5,7}, 
            	{6,8}, {6,9}, 
            	{7,9}, 
            	{8,7}, {8,8}, 
            	{11,8}, 
            	{12,0}, {12,9}, 
            	{13,0}, 
            	{14,1}, {14,4}, {14,9}, 
            	{15,2}, {15,3}, {15,9}, 
            })
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 1, uw[0], GROUND_LEVEL, uw[1], structureBB);
            }
            
            // Double-tall grass
            for (int[] uw : new int[][]{
            	{0,8}, 
            	{2,6}, 
            	{5,9}, 
            	{15,0}, {15,1}, 
            })
            {
				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 2, uw[0], GROUND_LEVEL, uw[1], structureBB);
				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 11, uw[0], GROUND_LEVEL+1, uw[1], structureBB);
            }
            
            
            // Yellow flowers
            for (int[] uw : new int[][]{
            	{7,6}, {14,0}, 
            })
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.yellow_flower, 0, uw[0], GROUND_LEVEL, uw[1], structureBB);
            }
            
            
            // Trees
            // Nothing yet...
        	
            
            // Decor
            for (int decorUVW[] : new int[][]{
            	{1, GROUND_LEVEL, 1},
            	{2, GROUND_LEVEL, 8},
            	{13, GROUND_LEVEL, 7},
            	})
            {
                // Add foundations
            	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, decorUVW[0], decorUVW[1]-1, decorUVW[2], structureBB);
        		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, decorUVW[0], decorUVW[1]-2, decorUVW[2], structureBB);
        		
                // The actual decor
        		for (int j=0; j<decorUVW.length; j++)
                {
        			int decorHeightY = decorUVW[1];
        			
        			// Set random seed
                	Random randomFromXYZ = new Random();
                	randomFromXYZ.setSeed(
        					world.getSeed() +
        					FunctionsVN.getUniqueLongForXYZ(
        							this.getXWithOffset(decorUVW[0], decorUVW[2]),
        							this.getYWithOffset(decorHeightY),
        							this.getZWithOffset(decorUVW[0], decorUVW[2])
        							)
                			);
                	
                	// Get ground level
                	if (this.decorHeightY.size()<(j+1))
                	{
                		// There are fewer stored ground levels than this decor number, so this is being generated for the first time.
                		// Add new ground level
                		//decorHeightY = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(uvw[0], uvw[2]), this.getZWithOffset(uvw[0], uvw[2]))-this.boundingBox.minY;
                		this.decorHeightY.add(decorHeightY);
                	}
                	else
                	{
                		// There is already (presumably) a value for this ground level, so this decor is being multiply generated.
                		// Retrieve ground level
                		decorHeightY = this.decorHeightY.get(j);
                	}
                	
                	
                	// Generate decor
                	ArrayList<BlueprintData> decorBlueprint = getRandomPlainsDecorBlueprint(this.start, this.coordBaseMode, randomFromXYZ);
                	
                	for (BlueprintData b : decorBlueprint)
                	{
                		// Place block indicated by blueprint
                		this.placeBlockAtCurrentPosition(world, b.getBlock(), b.getMeta(), decorUVW[0]+b.getUPos(), decorHeightY+b.getVPos(), decorUVW[2]+b.getWPos(), structureBB);
                		
                		// Fill below if flagged
                		if ((b.getfillFlag()&1)!=0)
                		{
                			this.func_151554_b(world, b.getBlock(), b.getMeta(), decorUVW[0]+b.getUPos(), decorHeightY+b.getVPos()-1, decorUVW[2]+b.getWPos(), structureBB);
                		}
                		
                		// Clear above if flagged
                		if ((b.getfillFlag()&2)!=0)
                		{
                			this.clearCurrentPositionBlocksUpwards(world, decorUVW[0]+b.getUPos(), decorHeightY+b.getVPos()+1, decorUVW[2]+b.getWPos(), structureBB);
                		}            		
                	}
                }
            }

            
            // Fences
            for(int[] uuvvww : new int[][]{
            	// Stalls
            	{1,1,2, 1,3,2}, {3,1,2, 3,3,2}, {1,1,5, 1,3,5}, {3,1,5, 3,3,5}, 
            	{12,1,2, 12,3,2}, {14,1,2, 14,3,2}, {12,1,5, 12,3,5}, {14,1,5, 14,3,5}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);	
            }
            
            
            // Planks
            for(int[] uuvvww : new int[][]{
            	// Stalls
            	{2,1,2, 2,1,5}, 
            	{13,1,2, 13,1,5},
            	// Bell post
            	{6,1,7, 6,1,7},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
            
            // Wooden slabs
            for(int[] uuvvww : new int[][]{
            	// Left stall
            	{1,4,2, 3,4,2}, {1,4,5, 3,4,5},
            	// Right stall
            	{12,4,2, 14,4,2}, {12,4,5, 14,4,5},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
            }
            
            
            // Sign
            int signU = 6;
			int signV = 2;
			int signW = 7;
            int signX = this.getXWithOffset(signU, signW);
            int signY = this.getYWithOffset(signV);
            int signZ = this.getZWithOffset(signU, signW);
    		
            TileEntitySign signContents;
            
            if (this.start.namePrefix.equals("") && this.start.nameRoot.equals("") && this.start.nameSuffix.equals(""))
            {
            	// Something has gone wrong and we can't obtain the name. Substitute nonsense instead.
            	signContents = new TileEntitySign();
            	signContents.signText[1] = "Market";
            }
            else
            {
            	signContents = StructureVillageVN.generateSignContents(this.start.namePrefix, this.start.nameRoot, this.start.nameSuffix);
            }
            
			world.setBlock(signX, signY, signZ, biomeStandingSignBlock, StructureVillageVN.getSignRotationMeta(8, this.coordBaseMode, false), 2); // 2 is "send change to clients without block update notification"
    		world.setTileEntity(signX, signY, signZ, signContents);
            
            
        	// Torches
            for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright; 
            	// Left stall
            	{2,2,2,-1}, {2,2,5,-1}, 
            	// Right stall
            	{13,2,2,-1}, {13,2,5,-1}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
        	

            // Wool Awnings
            // Yellow
            for (int[] uvw : new int[][]{
            	{1,4,4}, {2,4,3}, {3,4,4}, 
            	{12,4,3}, {13,4,4}, {14,4,3}, 
            })
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.wool, GeneralConfig.decorateVillageCenter ? this.start.townColor : 4, uvw[0], uvw[1], uvw[2], structureBB);
            }
            // White
            for (int[] uvw : new int[][]{
            	{1,4,3}, {2,4,4}, {3,4,3}, 
            	{12,4,4}, {13,4,3}, {14,4,4}, 
            })
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.wool, GeneralConfig.decorateVillageCenter ? this.start.townColor2 : 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
        	
            // Clear path for easier entry
            int pathU = 6;
            int pathV = GROUND_LEVEL;
            int pathW = -1;
            
            this.clearCurrentPositionBlocksUpwards(world, pathU, pathV, pathW, structureBB);
        	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, pathU, pathV-1, pathW, structureBB);
        	// Place dirt if the block to be set as path is empty
        	if (world.isAirBlock(this.getXWithOffset(pathU, pathW), this.getYWithOffset(pathV-1), this.getZWithOffset(pathU, pathW)))
        	{
            	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, pathU, pathV-1, pathW, structureBB);
        	}
        	StructureVillageVN.setPathSpecificBlock(world, this.start, 0, this.getXWithOffset(pathU, pathW), this.getYWithOffset(pathV-1), this.getZWithOffset(pathU, pathW));
        	
            
            return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    
	/**
	 * Returns a list of blocks and coordinates used to construct a decor piece
	 */
	protected static ArrayList<BlueprintData> getRandomPlainsDecorBlueprint(StartVN startVN, int coordBaseMode, Random random)
	{
		int decorCount = 1;
		return getPlainsDecorBlueprint(random.nextInt(decorCount), startVN, coordBaseMode, random);
	}
	protected static ArrayList<BlueprintData> getPlainsDecorBlueprint(int decorType, StartVN startVN, int coordBaseMode, Random random)
	{
		ArrayList<BlueprintData> blueprint = new ArrayList(); // The blueprint to export
		
		
		// Generate per-material blocks
		
		Object[] blockObject;
    	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, startVN.materialType, startVN.biome); Block biomeFenceBlock = (Block)blockObject[0];
    	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, startVN.materialType, startVN.biome); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
    	
    	
    	// For stripped wood specifically
    	Block biomeStrippedWoodOrLogOrLogVerticBlock = biomeLogVertBlock; int biomeStrippedWoodOrLogOrLogVerticMeta = biomeLogVertMeta;
    	
    	// Try to see if stripped wood exists
    	if (biomeLogVertBlock == Blocks.log)
    	{
    		blockObject = ModObjects.chooseModStrippedWood(biomeLogVertMeta);
    		biomeStrippedWoodOrLogOrLogVerticBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogVerticMeta = (Integer)blockObject[1];
    	}
    	else if (biomeLogVertBlock == Blocks.log2)
    	{
    		blockObject = ModObjects.chooseModStrippedWood(biomeLogVertMeta+4);
    		biomeStrippedWoodOrLogOrLogVerticBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogVerticMeta = (Integer)blockObject[1];
    	}
    	// If it doesn't exist, try stripped logs
    	if (biomeStrippedWoodOrLogOrLogVerticBlock==Blocks.log || biomeStrippedWoodOrLogOrLogVerticBlock==Blocks.log2)
    	{
        	if (biomeLogVertBlock == Blocks.log)
        	{
        		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedWoodOrLogOrLogVerticBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogVerticMeta = (Integer)blockObject[1];
        	}
        	else if (biomeLogVertBlock == Blocks.log2)
        	{
        		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 0); biomeStrippedWoodOrLogOrLogVerticBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogVerticMeta = (Integer)blockObject[1];
        	}
    	}
    	
    	
        switch (decorType)
        {
    	case 0: // Plains Lamp 1
    		
    		BlueprintData.addFillWithBlocks(blueprint, 0, 0, 0, 0, 2, 0, biomeFenceBlock);
    		BlueprintData.addPlaceBlock(blueprint, 0, 3, 0, biomeStrippedWoodOrLogOrLogVerticBlock, biomeStrippedWoodOrLogOrLogVerticMeta);
    		for (int[] lamp_uwm : new int[][]{
    			{-1,0,3},
    			{1,0,1},
    			{0,-1,2},
    			{0,1,0}
    			}) {
    			BlueprintData.addPlaceBlock(blueprint, lamp_uwm[0], 3, lamp_uwm[1], Blocks.torch, StructureVillageVN.getTorchRotationMeta(lamp_uwm[2], coordBaseMode));
    		}
    		
    		break;
        }
        
        // Return the decor blueprint
        return blueprint;
	}
	
}
