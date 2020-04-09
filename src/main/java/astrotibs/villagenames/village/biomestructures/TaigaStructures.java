package astrotibs.villagenames.village.biomestructures;

import java.lang.reflect.Method;
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

public class TaigaStructures
{
	// -------------------- //
    // --- Start Pieces --- //
	// -------------------- //
	
	// --- Simple grass patch with two structures --- //
	
    public static class TaigaMeetingPoint1 extends StartVN
    {
    	public TaigaMeetingPoint1() {}
    	
    	public TaigaMeetingPoint1(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, int terrainType)
    	{
    		super(chunkManager, componentType, random, posX, posZ, components, terrainType);
    		
    		int width = 11;
    		int depth = 6;
    		int height = 2;
    		
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
			if (this.coordBaseMode!=0) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode%2==1 ? 2 : 4), this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());}
			// Westward
			if (this.coordBaseMode==3) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 4, 1, this.getComponentType());}
			// Northward
			if (this.coordBaseMode==0) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 4, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());}
			// Eastward
			if (this.coordBaseMode!=3) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode%2==0 ? 2 : 4), 3, this.getComponentType());}
			
			
			// Attach non-road structures
			
			// Structure 1, left-hand side, near the bell
			if (this.coordBaseMode==0) {StructureVillageVN.getNextVillageStructureComponent((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX+(-1), this.boundingBox.minY, this.boundingBox.minZ+(random.nextInt(4)-1), 1, this.getComponentType());}
			if (this.coordBaseMode==1) {StructureVillageVN.getNextVillageStructureComponent((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX+(random.nextInt(4)+1), this.boundingBox.minY, this.boundingBox.minZ+(-1), 2, this.getComponentType());}
			if (this.coordBaseMode==2) {StructureVillageVN.getNextVillageStructureComponent((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX+(-1), this.boundingBox.minY, this.boundingBox.minZ+(random.nextInt(4)-1), 1, this.getComponentType());}
			if (this.coordBaseMode==3) {StructureVillageVN.getNextVillageStructureComponent((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX+(random.nextInt(4)), this.boundingBox.minY, this.boundingBox.minZ+(-1), 2, this.getComponentType());}
			
			// Structure 2, back side, along the longer side
			if (this.coordBaseMode==0) {StructureVillageVN.getNextVillageStructureComponent((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX+(random.nextInt(6)+3), this.boundingBox.minY, this.boundingBox.maxZ+(1), 0, this.getComponentType());}
			if (this.coordBaseMode==1) {StructureVillageVN.getNextVillageStructureComponent((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX+(-1), this.boundingBox.minY, this.boundingBox.minZ+(random.nextInt(6)+3), 1, this.getComponentType());}
			if (this.coordBaseMode==2) {StructureVillageVN.getNextVillageStructureComponent((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX+(-1), this.boundingBox.minY, this.boundingBox.minZ+(random.nextInt(6)+3), 2, this.getComponentType());}
			if (this.coordBaseMode==3) {StructureVillageVN.getNextVillageStructureComponent((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX+(1), this.boundingBox.minY, this.boundingBox.minZ+(random.nextInt(6)+3), 3, this.getComponentType());}
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
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.trapdoor, 0, this); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.standing_sign, 0, this); Block biomeStandingSignBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, this); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	
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
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, random,
        			this.getXWithOffset(2, 3),
        			this.getYWithOffset(2),
        			this.getZWithOffset(2, 3));
        	int townColor = villageNBTtag.getInteger("townColor");
        	int townColor2 = villageNBTtag.getInteger("townColor2");
        	
        	// Top layer is grass path
        	for (int i=0; i<=10; i++)
        	{
        		for (int j=1; j<=6; j++)
            	{
        			this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, i, 0, j, structureBB);
        			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, i, -1, j, structureBB); // Foundation
        			this.clearCurrentPositionBlocksUpwards(world, i, 1, j, structureBB);
        			// Set grass path after fill so that the area is level
        			StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(i, j), this.getYWithOffset(0), this.getZWithOffset(i, j));
            	}
        	}
            
        	// Set grass
        	for (int[] offset_xy : new int[][]{
        		{0, 2}, 
        		{1, 3}, 
        		{2, 1}, {2, 2}, {2, 4}, {2, 6},
        		{3, 1}, {3, 3}, {3, 5}, 
        		{5, 2}, {5, 5}, 
        		{6, 3}, 
        		{8, 1}, {8, 3}, {8, 5}, 
        		{9, 2}, {9, 4}, 
        		{10, 3}, {10, 5}, {10, 6}, 
        		{10, 3}, 
        	})
        	{
        		this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, offset_xy[0], 0, offset_xy[1], structureBB);
        	}
        	
        	// Nodules at the end
        	for (int i=4; i<=6; i++) {for (int j=0; j<=0; j++) {
        			this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, i, 0, j, structureBB);
        			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, i, -1, j, structureBB); // Foundation
        			this.clearCurrentPositionBlocksUpwards(world, i, 1, j, structureBB);
        			StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(i, j), this.getYWithOffset(0), this.getZWithOffset(i, j));
            }}
        	for (int i=11; i<=11; i++) {for (int j=2; j<=4; j++) {
        			this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, i, 0, j, structureBB);
        			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, i, -1, j, structureBB); // Foundation
        			this.clearCurrentPositionBlocksUpwards(world, i, 1, j, structureBB);
        			StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(i, j), this.getYWithOffset(0), this.getZWithOffset(i, j));
            }}
        	
        	// Single wood platform for the "bell"
        	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, 2, 0, 3, structureBB);
        	if (GeneralConfig.decorateVillageCenter)
        	{
        		Object[] tryConcrete = ModObjects.chooseModConcrete(townColor);
            	Block concreteBlock = Blocks.stained_hardened_clay; int concreteMeta = townColor;
            	if (tryConcrete != null) {concreteBlock = (Block) tryConcrete[0]; concreteMeta = (Integer) tryConcrete[1];}
            	
            	this.placeBlockAtCurrentPosition(world, concreteBlock, concreteMeta, 2, 1, 3, structureBB);
        	}
        	else
        	{
        		this.placeBlockAtCurrentPosition(world, biomePlankBlock, biomePlankMeta, 2, 1, 3, structureBB);
        	}
        	// Wood trapdoor hatching
        	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, this.coordBaseMode%2==0 ? 6 : 4, 1, 1, 3, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, this.coordBaseMode%2==0 ? 7 : 5, 3, 1, 3, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, (new int[]{4, 7, 5, 6})[this.coordBaseMode], 2, 1, 2, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, (new int[]{5, 6, 4, 7})[this.coordBaseMode], 2, 1, 4, structureBB);
        	
        	// Sign
            int signXBB = 2;
			int signYBB = 2;
			int signZBB = 3;
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
                    int bannerXBB = 5;
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
        			{2, 1, 5, -1, 0},
        			{4, 1, 2, -1, 0},
        			{9, 1, 4, -1, 0},
        			})
        		{
        			EntityVillager entityvillager = new EntityVillager(world);
        			
        			// Nitwits more often than not
        			if (GeneralConfig.enableNitwit && random.nextInt(3)==0) {entityvillager.setProfession(5);}
        			else {entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], -random.nextInt(24001));}
        			
        			int villagerY = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(ia[0], ia[2]), this.getZWithOffset(ia[0], ia[2]));
        			
        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)villagerY + 0.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
                    		random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(entityvillager);
        		}
            }
            
            return true;
        }
    }
    
    
    
	// --- Taiga Well --- //
	
	public static class TaigaMeetingPoint2 extends StartVN
    {
    	public TaigaMeetingPoint2() {}
    	
    	public TaigaMeetingPoint2(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, int terrainType)
    	{
    		super(chunkManager, componentType, random, posX, posZ, components, terrainType);

    		int width = 8;
    		int depth = 8;
    		int height = 6;
    		
		    // Establish orientation
            this.coordBaseMode = random.nextInt(4);
            switch (this.coordBaseMode)
            {
	            case 0: // North
	            case 2: // South
                    this.boundingBox = new StructureBoundingBox(posX, 64+1, posZ, posX + width, 64+1+height, posZ + depth);
                    break;
                default: // 1: East; 3: West
                    this.boundingBox = new StructureBoundingBox(posX, 64+1, posZ, posX + depth, 64+1+height, posZ + width);
            }
    	}
    	
		/*
		 * Add the paths that lead outward from this structure
		 */
		public void buildComponent(StructureComponent start, List components, Random random)
		{
			//LogHelper.info("coordBaseMode: " + this.coordBaseMode);
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
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, this); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, this); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.mossy_cobblestone, 0, this); Block biomeMossyCobblestoneBlock = (Block)blockObject[0]; int biomeMossyCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.stone_slab, 3, this); Block biomeCobblestoneSlabBlock = (Block)blockObject[0]; int biomeCobblestoneSlabMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.stone_stairs, 0, this); Block biomeStoneStairsBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone_wall, 0, this); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, this); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wall_sign, 0, this); Block biomeWallSignBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4, this); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, this); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, this); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.trapdoor, 0, this); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
        	
        	if (this.field_143015_k < 0)
            {
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				new StructureBoundingBox(
        						this.boundingBox.minX+1, this.boundingBox.minZ+1,
        						this.boundingBox.maxX-1, this.boundingBox.maxZ-1), // Set the bounding box version as this bounding box but with Y going from 0 to 512
        				true);
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.minY -1 -1, 0);
            }
            
        	// Generate or otherwise obtain village name and banner and colors
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, random,
        			this.getXWithOffset(8, 1),
        			this.getYWithOffset(1),
        			this.getZWithOffset(8, 1));
        	int townColor = villageNBTtag.getInteger("townColor");
        	int townColor2 = villageNBTtag.getInteger("townColor2");
        	
        	
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
            for (int i : new int[]{3,4,5})
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
        	
            // Add odds and ends into the ground
            for (int[] uw : new int[][]{
            	{0, -1, 5},
            	{6, -1, 1},
            	{7, -1, 1},
            	{7, -1, 6},
            	{6, -1, 7},
            	{6, 0, 0},
            	{8, 0, 6},
            	{3, -1, 1},
            	{7, -1, 5},
            })
            {
            	int k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(uw[0], uw[2]), this.getZWithOffset(uw[0], uw[2])) + uw[1] - 1;
            	if (k > -1)
                {
                	this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, uw[0], k - this.boundingBox.minY, uw[2], structureBB);
               	}
            }
            
            // One block of water - probably a mistake but whatever
            int kw = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(4, 7), this.getZWithOffset(4, 7)) - 2;
            if (kw > -1)
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.flowing_water, 0, 4, kw - this.boundingBox.minY, 7, structureBB);
            }
            
            
            
            // Decor
            int[][] decorUVW = new int[][]{
            	{0, 2, 7},
            	{8, 2, 0},
            };  
            
            for (int j=0; j<decorUVW.length; j++)
            {
            	// Get coordinates
            	int[] uvw = decorUVW[j];
            	
            	// Set random seed
            	Random randomDecor = new Random();
            	randomDecor.setSeed(
        					world.getSeed() +
        					this.coordBaseMode +
        					this.getXWithOffset(uvw[0], uvw[2]) +
        					this.getYWithOffset(uvw[1]) +
        					this.getXWithOffset(uvw[0], uvw[2])
            			);
            	
            	int decorHeightY;
            	
            	// Get ground level
            	if (this.decorHeightY.size()<(j+1))
            	{
            		// There are fewer stored ground levels than this decor number, so this is being generated for the first time.
            		// Add new ground level
            		decorHeightY = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(uvw[0], uvw[2]), this.getZWithOffset(uvw[0], uvw[2]))-this.boundingBox.minY;
            		this.decorHeightY.add(decorHeightY);
            	}
            	else
            	{
            		// There is already (presumably) a value for this ground level, so this decor is being multiply generated.
            		// Retrieve ground level
            		decorHeightY = this.decorHeightY.get(j);
            	}
            	
            	//LogHelper.info("Decor spawned at: " + this.getXWithOffset(uvw[0], uvw[2]) + " " + (groundLevelY+this.boundingBox.minY) + " " + this.getZWithOffset(uvw[0], uvw[2]));
            	
            	boolean genericBoolean=false;
            	
	            switch (randomDecor.nextInt(7))
	            {
            	case 0: // Wood trough
            		boolean shift=randomDecor.nextBoolean();
            		switch (randomDecor.nextInt(2))
            		{
            		case 0:
            			// Base
            			this.fillWithMetadataBlocks(world, structureBB, uvw[0]+0, decorHeightY-1, uvw[2]-2+(shift?1:0), uvw[0]+0, decorHeightY-1, uvw[2]+1+(shift?1:0), biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
            			// Foundation
            			for (int i=-2 ; i<=1; i++) {this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]+0, decorHeightY-2, uvw[2]+i+(shift?1:0), structureBB);}
            			// Left
            			this.fillWithMetadataBlocks(world, structureBB, uvw[0]-1, decorHeightY+0, uvw[2]-2+(shift?1:0), uvw[0]-1, decorHeightY+0, uvw[2]+1+(shift?1:0), biomeTrapdoorBlock, this.coordBaseMode%2==0 ? 6 : 4, biomeTrapdoorBlock, this.coordBaseMode%2==0 ? 6 : 4, false);
            			// Right
            			this.fillWithMetadataBlocks(world, structureBB, uvw[0]+1, decorHeightY+0, uvw[2]-2+(shift?1:0), uvw[0]+1, decorHeightY+0, uvw[2]+1+(shift?1:0), biomeTrapdoorBlock, this.coordBaseMode%2==0 ? 7 : 5, biomeTrapdoorBlock, this.coordBaseMode%2==0 ? 7 : 5, false);
            			// Front
            			this.fillWithMetadataBlocks(world, structureBB, uvw[0]+0, decorHeightY+0, uvw[2]-3+(shift?1:0), uvw[0]+0, decorHeightY+0, uvw[2]-3+(shift?1:0), biomeTrapdoorBlock, (new int[]{4, 7, 5, 6})[this.coordBaseMode], biomeTrapdoorBlock, (new int[]{4, 7, 5, 6})[this.coordBaseMode], false);
            			// Back
            			this.fillWithMetadataBlocks(world, structureBB, uvw[0]+0, decorHeightY+0, uvw[2]+2+(shift?1:0), uvw[0]+0, decorHeightY+0, uvw[2]+2+(shift?1:0), biomeTrapdoorBlock, (new int[]{5, 6, 4, 7})[this.coordBaseMode], biomeTrapdoorBlock, (new int[]{5, 6, 4, 7})[this.coordBaseMode], false);
            			break;
            			
            		case 1:
            			// Base
            			this.fillWithMetadataBlocks(world, structureBB, uvw[0]-2+(shift?1:0), decorHeightY-1, uvw[2]+0, uvw[0]+1+(shift?1:0), decorHeightY-1, uvw[2]+0, biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
            			// Foundation
            			for (int i=-2 ; i<=1; i++) {this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]+i+(shift?1:0), decorHeightY-2, uvw[2]+0, structureBB);}
            			// Left
            			this.fillWithMetadataBlocks(world, structureBB, uvw[0]-3+(shift?1:0), decorHeightY+0, uvw[2]+0, uvw[0]-3+(shift?1:0), decorHeightY+0, uvw[2]+0, biomeTrapdoorBlock, this.coordBaseMode%2==0 ? 6 : 4, biomeTrapdoorBlock, this.coordBaseMode%2==0 ? 6 : 4, false);
            			// Right
            			this.fillWithMetadataBlocks(world, structureBB, uvw[0]+2+(shift?1:0), decorHeightY+0, uvw[2]+0, uvw[0]+2+(shift?1:0), decorHeightY+0, uvw[2]+0, biomeTrapdoorBlock, this.coordBaseMode%2==0 ? 7 : 5, biomeTrapdoorBlock, this.coordBaseMode%2==0 ? 7 : 5, false);
            			// Front
            			this.fillWithMetadataBlocks(world, structureBB, uvw[0]-2+(shift?1:0), decorHeightY+0, uvw[2]-1, uvw[0]+1+(shift?1:0), decorHeightY+0, uvw[2]-1, biomeTrapdoorBlock, (new int[]{4, 7, 5, 6})[this.coordBaseMode], biomeTrapdoorBlock, (new int[]{4, 7, 5, 6})[this.coordBaseMode], false);
            			// Back
            			this.fillWithMetadataBlocks(world, structureBB, uvw[0]-2+(shift?1:0), decorHeightY+0, uvw[2]+1, uvw[0]+1+(shift?1:0), decorHeightY+0, uvw[2]+1, biomeTrapdoorBlock, (new int[]{5, 6, 4, 7})[this.coordBaseMode], biomeTrapdoorBlock, (new int[]{5, 6, 4, 7})[this.coordBaseMode], false);
            			break;
            		}
            		break;
            		
            	case 1: // Large boulder
            		// Central boulder is in the same place
            		this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, uvw[0]+0, decorHeightY+0, uvw[2]+0, structureBB);
            		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]+0, decorHeightY-1, uvw[2]+0, structureBB); // Foundation
            		
            		switch (randomDecor.nextInt(4))
            		{
            		case 0: // Facing you
            			this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, uvw[0]+0, decorHeightY+0, uvw[2]+1, structureBB);
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]+0, decorHeightY-1, uvw[2]+1, structureBB); // Foundation
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 3), uvw[0]+0, decorHeightY+0, uvw[2]-1, structureBB);
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]+0, decorHeightY-1, uvw[2]-1, structureBB); // Foundation
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 3), uvw[0]+0, decorHeightY+1, uvw[2]+0, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 2), uvw[0]+0, decorHeightY+1, uvw[2]+1, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 1), uvw[0]+1, decorHeightY+0, uvw[2]+1, structureBB);
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]+1, decorHeightY-1, uvw[2]+1, structureBB); // Foundation
            			break;
            		case 1: // Facing left
            			this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, uvw[0]+1, decorHeightY+0, uvw[2]+0, structureBB);
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]+1, decorHeightY-1, uvw[2]+0, structureBB); // Foundation
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 0), uvw[0]-1, decorHeightY+0, uvw[2]+0, structureBB);
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]-1, decorHeightY-1, uvw[2]+0, structureBB); // Foundation
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 0), uvw[0]+0, decorHeightY+1, uvw[2]+0, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 1), uvw[0]+1, decorHeightY+1, uvw[2]+0, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 3), uvw[0]+1, decorHeightY+0, uvw[2]-1, structureBB);
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]+1, decorHeightY-1, uvw[2]-1, structureBB); // Foundation
            			break;
            		case 2: // Facing away
            			this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, uvw[0]+0, decorHeightY+0, uvw[2]-1, structureBB);
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]+0, decorHeightY-1, uvw[2]-1, structureBB); // Foundation
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 2), uvw[0]+0, decorHeightY+0, uvw[2]+1, structureBB);
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]+0, decorHeightY-1, uvw[2]+1, structureBB); // Foundation
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 2), uvw[0]+0, decorHeightY+1, uvw[2]+0, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 3), uvw[0]+0, decorHeightY+1, uvw[2]-1, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 0), uvw[0]-1, decorHeightY+0, uvw[2]-1, structureBB);
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]-1, decorHeightY-1, uvw[2]+1, structureBB); // Foundation
            			break;
            		case 3: // Facing right
            			this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, uvw[0]-1, decorHeightY+0, uvw[2]+0, structureBB);
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]-1, decorHeightY-1, uvw[2]+0, structureBB); // Foundation
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 1), uvw[0]+1, decorHeightY+0, uvw[2]+0, structureBB);
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]+1, decorHeightY-1, uvw[2]+0, structureBB); // Foundation
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 1), uvw[0]+0, decorHeightY+1, uvw[2]+0, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 0), uvw[0]-1, decorHeightY+1, uvw[2]+0, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 2), uvw[0]-1, decorHeightY+0, uvw[2]+1, structureBB);
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]-1, decorHeightY-1, uvw[2]+1, structureBB); // Foundation
            			break;
            		}
            		break;
            	
            	case 2: // Small boulder with spike
            		genericBoolean=true;
            	case 3: // Small boulder without spike
            		// Central boulder is in the same place
            		this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, uvw[0]+0, decorHeightY+0, uvw[2]+0, structureBB);
            		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]+0, decorHeightY-1, uvw[2]+0, structureBB); // Foundation
            		
            		Block boulderTopperBlock; int boulderTopperMeta;
            		int boulderOrientation = random.nextInt(4);
            		
            		if (genericBoolean)
            		{
            			// Put a spike on top of the boulder
            			
            			boulderTopperBlock = Blocks.cobblestone_wall; boulderTopperMeta = 0;
                		
                		// Test the spike here by seeing if cobblestone remains as cobblestone.
                		Object[] blockObjectTest = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, this); Block biomeTestBlock = (Block)blockObjectTest[0]; int biomeTestMeta = (Integer)blockObjectTest[1];
                		if (biomeTestBlock==Blocks.mossy_cobblestone)
                		{
                			// Try to make mossy cobblestone wall
                			boulderTopperMeta = 1;
                		}
                		else if (biomeTestBlock==Blocks.sandstone)
                		{
                			// Try a sandstone wall--use a slab otherwise
                			boulderTopperBlock = Block.getBlockFromName(ModObjects.sandstoneWallUTD);
                			if (boulderTopperBlock==null) {boulderTopperBlock = Blocks.sandstone;}
                		}
                		else if (biomeTestBlock!=Blocks.cobblestone)
                		{
                			boulderTopperBlock = biomeTestBlock;
                		}
            		}
            		else
            		{
            			// Put stairs on top of the boulder
            			boulderTopperBlock = biomeStoneStairsBlock;
            			boulderTopperMeta = this.getMetadataWithOffset(boulderTopperBlock, (new int[]{3,0,2,1})[boulderOrientation]);
            		}
            		
            		this.placeBlockAtCurrentPosition(world, boulderTopperBlock, boulderTopperMeta, uvw[0]+0, decorHeightY+1, uvw[2]+0, structureBB);
            		
            		switch(boulderOrientation)
            		{
            		case 0:
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 2), uvw[0]+0, decorHeightY+0, uvw[2]+1, structureBB);
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]+0, decorHeightY-1, uvw[2]+1, structureBB); // Foundation
            			break;
            		case 1:
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 1), uvw[0]+1, decorHeightY+0, uvw[2]+0, structureBB);
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]+1, decorHeightY-1, uvw[2]+0, structureBB); // Foundation
            			break;
            		case 2:
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 3), uvw[0]+0, decorHeightY+0, uvw[2]-1, structureBB);
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]+0, decorHeightY-1, uvw[2]-1, structureBB); // Foundation
            			break;
            		case 3:
            			this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(biomeStoneStairsBlock, 0), uvw[0]-1, decorHeightY+0, uvw[2]+0, structureBB);
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]-1, decorHeightY-1, uvw[2]+0, structureBB); // Foundation
            			break;
            		}
            		break;
            		
            	case 4: // Campfire
            		
            		/*
            		for (int i=-1; i<=1; i++) {for (int l=-1; l<=1; l++) {
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]+i, decorHeightY-2, uvw[2]+l, structureBB); // Foundation
            			this.placeBlockAtCurrentPosition(world, Blocks.gravel, 0, uvw[0]+i, decorHeightY-1, uvw[2]+l, structureBB); // Gravel
            			this.clearCurrentPositionBlocksUpwards(world, uvw[0]+i, decorHeightY+0, uvw[2]+l, structureBB); // Clear above
            			// Add slabs around the edge
            			if (i==0 || l==0) {this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 3, uvw[0]+i, decorHeightY+0, uvw[2]+l, structureBB);} // Cobblestone slab
            			
            		}}
            		// Actually set the campfire
            		this.placeBlockAtCurrentPosition(world, Blocks.netherrack, 0, uvw[0]+0, decorHeightY-1, uvw[2]+0, structureBB);
            		this.placeBlockAtCurrentPosition(world, Blocks.fire, 0, uvw[0]+0, decorHeightY+0, uvw[2]+0, structureBB);
            		*/
            		
            		// Substitute with a log with a torch
            		this.placeBlockAtCurrentPosition(world, biomeLogVertBlock, biomeLogVertMeta, uvw[0]+0, decorHeightY+0, uvw[2]+0, structureBB);
                	this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, uvw[0]+0, decorHeightY+1, uvw[2]+0, structureBB);
                	
            		break;
            		
            	case 5: // Campfire over hay in bin
            		
            		// Foundation
            		for (int i=-1 ; i<=1; i++) {for (int l=-1 ; l<=1; l++) {if (i==0 || j==0) {
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, uvw[0]+i, decorHeightY-1, uvw[2]+j, structureBB);
            			this.clearCurrentPositionBlocksUpwards(world, uvw[0]+i, decorHeightY+0, uvw[2]+j, structureBB); // Clear above
            		}}}
        			// Left
        			this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, this.coordBaseMode%2==0 ? 6 : 4, uvw[0]-1, decorHeightY+0, uvw[2]+0, structureBB);
        			// Right
        			this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, this.coordBaseMode%2==0 ? 7 : 5, uvw[0]+1, decorHeightY+0, uvw[2]+0, structureBB);
        			// Front
        			this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, (new int[]{4, 7, 5, 6})[this.coordBaseMode], uvw[0]+0, decorHeightY+0, uvw[2]-1, structureBB);
        			// Back
        			this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, (new int[]{5, 6, 4, 7})[this.coordBaseMode], uvw[0]+0, decorHeightY+0, uvw[2]+1, structureBB);
            		
        			this.placeBlockAtCurrentPosition(world, Blocks.hay_block, 0, uvw[0]+0, decorHeightY+0, uvw[2]+0, structureBB);
        			
        			// This block is supposed to be campfire but ya know
            		this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, uvw[0]+0, decorHeightY+1, uvw[2]+0, structureBB);
                	
            		break;
            		
            	case 6: // Torch on a cobblestone wall
            		
            		this.placeBlockAtCurrentPosition(world, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, uvw[0]+0, decorHeightY+0, uvw[2]+0, structureBB);
            		this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, uvw[0]+0, decorHeightY+1, uvw[2]+0, structureBB);
            		
            		break;
	            }
	            
            }
            
        	
        	// Create well
        	this.fillWithMetadataBlocks(world, structureBB, 2, 1, 2, 6, 2, 6, biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
        	// Bottom is dirt with inset mossy cobblestone blocks
        	this.fillWithMetadataBlocks(world, structureBB, 2, 0, 2, 6, 0, 6, biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 3, 0, 3, 5, 0, 5, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, false);
        	
        	// Clear above and set foundation
        	for (int u=2; u<=6; u++) {for (int w=2; w<=6; w++) {
        			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, -1, w, structureBB); // Foundation
        			this.clearCurrentPositionBlocksUpwards(world, u, 3, w, structureBB);
            }}
        	
        	// Fill basin with water
        	this.fillWithBlocks(world, structureBB, 3, 1, 3, 5, 2, 5, Blocks.flowing_water, Blocks.flowing_water, false);
        	
        	// Place a couple specific stone/cobblestone blocks in the well
        	this.placeBlockAtCurrentPosition(world, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, 3, 2, 2, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, 2, 2, 4, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, 3, 0, 3, structureBB);
        	
        	// Add fence posts
        	this.fillWithBlocks(world, structureBB, 2, 3, 2, 6, 5, 6, biomeFenceBlock, biomeFenceBlock, false);
        	this.fillWithAir(world, structureBB, 2, 3, 3, 6, 4, 5);
        	this.fillWithAir(world, structureBB, 3, 3, 2, 5, 4, 6);
        	this.fillWithAir(world, structureBB, 3, 5, 3, 5, 5, 5);
        	// Add log roof
        	this.fillWithMetadataBlocks(world, structureBB, 3, 6, 2, 5, 6, 6, biomeLogHorAlongBlock, biomeLogHorAlongMeta + (this.coordBaseMode%2==0 ? 4 : 0), biomeLogHorAlongBlock, biomeLogHorAlongMeta + (this.coordBaseMode%2==0 ? 4 : 0), false);
        	this.fillWithMetadataBlocks(world, structureBB, 2, 5, 2, 2, 5, 6, biomeLogHorAlongBlock, biomeLogHorAlongMeta + (this.coordBaseMode%2==0 ? 4 : 0), biomeLogHorAlongBlock, biomeLogHorAlongMeta + (this.coordBaseMode%2==0 ? 4 : 0), false);
        	this.fillWithMetadataBlocks(world, structureBB, 6, 5, 2, 6, 5, 6, biomeLogHorAlongBlock, biomeLogHorAlongMeta + (this.coordBaseMode%2==0 ? 4 : 0), biomeLogHorAlongBlock, biomeLogHorAlongMeta + (this.coordBaseMode%2==0 ? 4 : 0), false);
        	// Add torches
        	this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 2, 5, 1, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 2, 5, 7, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 6, 5, 1, structureBB);
        	this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 6, 5, 7, structureBB);
            
            
            // Colored block where bell used to be
            if (GeneralConfig.decorateVillageCenter)
            {
            	int metaBase = ((int)world.getSeed()%4+this.coordBaseMode)%4; // Procedural based on world seed and base mode
            	
            	BlockPos uvw = new BlockPos(4, 5, 4); // Starting position of the block cluster. Use lowest X, Z.
            	int metaCycle = (metaBase+Math.abs(this.getXWithOffset(uvw.getX(), uvw.getZ())%2 - (this.getZWithOffset(uvw.getX(), uvw.getZ())%2)*3) + uvw.getY())%4; // Procedural based on block X, Y, Z 
            	Object[] tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(townColor, metaCycle);
            	
            	if (tryGlazedTerracotta != null)
            	{
            		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvw.getX(), uvw.getY(), uvw.getZ(), structureBB);
            	}
            	else
            	{
            		this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, townColor, 4, 5, 4, structureBB);
            	}
            }
            else
            {
            	this.placeBlockAtCurrentPosition(world, biomePlankBlock, biomePlankMeta, 4, 5, 4, structureBB);
            }
            
        	// Signs
            int signXBB = 4;
			int signYBB = 5;
			int signZBB = 5;
			int signZBB2 = 3;
            int signX = this.getXWithOffset(signXBB, signZBB);
            int signX2 = this.getXWithOffset(signXBB, signZBB2);
            int signY = this.getYWithOffset(signYBB);
            int signZ = this.getZWithOffset(signXBB, signZBB);
            int signZ2 = this.getZWithOffset(signXBB, signZBB2);
    		
    		String namePrefix = villageNBTtag.getString("namePrefix");
    		String nameRoot = villageNBTtag.getString("nameRoot");
    		String nameSuffix = villageNBTtag.getString("nameSuffix");
    		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);
    		
			world.setBlock(signX, signY, signZ, biomeWallSignBlock, StructureVillageVN.getSignRotationMeta(0, this.coordBaseMode, true), 2); // Facing away from you
			world.setTileEntity(signX, signY, signZ, signContents);
    		
            // I need to make a duplicate TileEntity because the first one gets consumed when applied to the first sign
    		TileEntitySign signContents2 = new TileEntitySign();
    		for (int i=0; i<4; i++) {signContents2.signText[i] = signContents.signText[i];}
			
			world.setBlock(signX2, signY, signZ2, biomeWallSignBlock, StructureVillageVN.getSignRotationMeta(2, this.coordBaseMode, true), 2); // Facing toward you
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
                    
                    // Place a foundation
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
    		else
    		{
				int k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(7, 8), this.getZWithOffset(7, 8)) - 1;
				if (k > -1)
				{
					this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, 7, k-1-this.boundingBox.minY, 8, structureBB);
				    this.clearCurrentPositionBlocksUpwards(world, 7, k-this.boundingBox.minY, 8, structureBB);
				}
    		}
    		
    		// Villagers
            if (!this.villagersGenerated)
            {
            	this.villagersGenerated=true;
            	
        		for (int[] ia : new int[][]{
        			{7, 2, 1, -1, 0},
        			{8, 2, 3, -1, 0},
        			{0, 2, 5, -1, 0},
        			})
        		{
        			EntityVillager entityvillager = new EntityVillager(world);
        			
        			// Nitwits more often than not
        			if (GeneralConfig.enableNitwit && random.nextInt(3)==0) {entityvillager.setProfession(5);}
        			else {entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], -random.nextInt(24001));}
        			
        			int villagerY = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(ia[0], ia[2]), this.getZWithOffset(ia[0], ia[2]));
        			
        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)villagerY + 0.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
                    		random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(entityvillager);
        		}
            }
            
            return true;
        }
        
    }
}
