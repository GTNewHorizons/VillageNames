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
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, this); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.trapdoor, 0, this); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.standing_sign, 0, this); Block biomeSignBlock = (Block)blockObject[0];
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
        			this.placeBlockAtCurrentPosition(world, Blocks.grass, 0, i, 0, j, structureBB);
        			this.func_151554_b(world, Blocks.dirt, 0, i, -1, j, structureBB); // Foundation
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
        		this.placeBlockAtCurrentPosition(world, Blocks.grass, 0, offset_xy[0], 0, offset_xy[1], structureBB);
        	}
        	
        	// Nodules at the end
        	for (int i=4; i<=6; i++) {for (int j=0; j<=0; j++) {
        			this.placeBlockAtCurrentPosition(world, Blocks.grass, 0, i, 0, j, structureBB);
        			this.func_151554_b(world, Blocks.dirt, 0, i, -1, j, structureBB); // Foundation
        			this.clearCurrentPositionBlocksUpwards(world, i, 1, j, structureBB);
        			StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(i, j), this.getYWithOffset(0), this.getZWithOffset(i, j));
            }}
        	for (int i=11; i<=11; i++) {for (int j=2; j<=4; j++) {
        			this.placeBlockAtCurrentPosition(world, Blocks.grass, 0, i, 0, j, structureBB);
        			this.func_151554_b(world, Blocks.dirt, 0, i, -1, j, structureBB); // Foundation
        			this.clearCurrentPositionBlocksUpwards(world, i, 1, j, structureBB);
        			StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(i, j), this.getYWithOffset(0), this.getZWithOffset(i, j));
            }}
        	
        	// Single wood platform for the "bell"
        	this.placeBlockAtCurrentPosition(world, Blocks.dirt, 0, 2, 0, 3, structureBB);
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
        	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, this.coordBaseMode==0 ? 4 : this.coordBaseMode==1 ? 7 : this.coordBaseMode==2 ? 5 : 6, 2, 1, 2, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, this.coordBaseMode==0 ? 5 : this.coordBaseMode==1 ? 6 : this.coordBaseMode==2 ? 4 : 7, 2, 1, 4, structureBB);
        	
        	
        	// Signs
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
    		TileEntitySign signContents2 = new TileEntitySign();
    		for (int i=0; i<4; i++) {signContents2.signText[i] = signContents.signText[i];}

    		int signFacing = 2; // 0=forward-facing; 1=leftward-facing; 2=backward-facing (toward you); 3=rightward-facing,  
    		
    		if (biomeSignBlock.getUnlocalizedName().toLowerCase().contains("ganyssurface"))
    		{
    			// Set the sign and its orientation
				world.setBlock(signX, signY, signZ, biomeSignBlock);
				world.setBlockMetadataWithNotify(signX, signY, signZ, ((signFacing + this.coordBaseMode + (this.coordBaseMode==4 ? 2 : 0))*4)%16, 2);
				
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
    			world.setBlock(signX, signY, signZ, biomeSignBlock, ((signFacing + this.coordBaseMode)*4)%16, 2); // 2 is "send change to clients without block update notification"
        		world.setTileEntity(signX, signY, signZ, signContents);
    		}
    		
    		
    		if (GeneralConfig.decorateVillageCenter)
    		{
    			// Banner
        		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
        		if (testForBanner!=null)
    			{
                    int bannerXBB = 5;
        			int bannerYBB = 1;
        			int bannerZBB = 4;
        			int bannerX = this.getXWithOffset(bannerXBB, bannerZBB);
        			int bannerY = this.getYWithOffset(bannerYBB);
                    int bannerZ = this.getZWithOffset(bannerXBB, bannerZBB);
                    int bannerFacing = 1; // 0=backward-facing (toward you); 1=rightward-facing; 2=forward-facing; 3=leftward-facing;  
                    
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
        			
        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)this.getYWithOffset(ia[1]) + 0.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
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
			LogHelper.info("coordBaseMode: " + this.coordBaseMode);
			// Southward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode<=1 ? 5 : 4), this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode<=1 ? 5 : 4), 1, this.getComponentType());
			// Northward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode>=2 ? 5 : 4), this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode>=2 ? 5 : 4), 3, this.getComponentType());
		}

    }
}
