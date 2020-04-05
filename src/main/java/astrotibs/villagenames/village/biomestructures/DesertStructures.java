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
		public void buildComponent(StructureComponent start, List components, Random random)
		{
			//LogHelper.info("coordBaseMode: " + this.coordBaseMode);
			// Southward
			if (this.coordBaseMode%2==0) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 3, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());}
			// Westward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 3, 1, this.getComponentType());
			// Northward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 3, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			if (this.coordBaseMode%2!=0) {StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 3, 3, this.getComponentType());}
			
			// Attach a non-road structure
			int strucX=0; int strucZ=0; int coordBaseMode=0;
			
			if (this.coordBaseMode%2==0)
			{
				strucX=this.boundingBox.maxX + 1; strucZ=this.boundingBox.minZ + random.nextInt(3)+1; coordBaseMode=3;
				//strucX=this.boundingBox.maxX + 1; strucZ=this.boundingBox.minZ + 0; coordBaseMode=3;
			}
			else 
			{
				strucX=this.boundingBox.minX + random.nextInt(3)+1; strucZ=this.boundingBox.maxZ + 1; coordBaseMode=0;
				//strucX=this.boundingBox.minX + 1; strucZ=this.boundingBox.maxZ + 1; coordBaseMode=0;
			}
			
			StructureVillageVN.getNextVillageStructureComponent((StructureVillagePieces.Start)start, components, random, strucX, this.boundingBox.minY, strucZ, coordBaseMode, this.getComponentType());
		}
    	
		/*
		 * Construct the structure
		 */
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;
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
            
        	// Generate or otherwise obtain village name and banner and colors
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, random,
        			this.getXWithOffset(6, 4),
        			this.getYWithOffset(2),
        			this.getZWithOffset(6, 4));
        	int townColor = villageNBTtag.getInteger("townColor");
        	int townColor2 = villageNBTtag.getInteger("townColor2");
        	
        	// Set sandstone ground and clear area above
        	this.fillWithBlocks(world, structureBB, 3, 0, 0, 9, 0, 8, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithAir(world, structureBB, 3, 1, 0, 9, 5, 8);
        	for (int x = 3; x <= 9; ++x) {for (int z = 0; z <= 8; ++z) {this.func_151554_b(world, Blocks.sandstone, 0, x, -1, z, structureBB);}}
        	this.fillWithBlocks(world, structureBB, 1, 0, 1, 2, 0, 7, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithAir(world, structureBB, 1, 1, 1, 2, 5, 7);
        	for (int x = 1; x <= 2; ++x) {for (int z = 1; z <= 7; ++z) {this.func_151554_b(world, Blocks.sandstone, 0, x, -1, z, structureBB);}}
        	this.fillWithBlocks(world, structureBB, 0, 0, 3, 0, 0, 5, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithAir(world, structureBB, 0, 1, 3, 0, 5, 5);
        	for (int x = 0; x <= 0; ++x) {for (int z = 3; z <= 5; ++z) {this.func_151554_b(world, Blocks.sandstone, 0, x, -1, z, structureBB);}}
        	
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
        		Object[] tryConcrete = ModObjects.chooseModConcrete(townColor2);
            	Block concreteBlock = Blocks.stained_hardened_clay; int concreteMeta = townColor2;
            	if (tryConcrete != null) {concreteBlock = (Block) tryConcrete[0]; concreteMeta = (Integer) tryConcrete[1];}
            	
            	this.placeBlockAtCurrentPosition(world, concreteBlock, concreteMeta, 4, 4, 4, structureBB);
        	}
        	else
        	{
        		this.placeBlockAtCurrentPosition(world, Blocks.hardened_clay, 0, 4, 4, 4, structureBB);
        	}
            // Just the tip
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 4, 5, 4, structureBB);
            
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
    		
    		int signFacing = 3; // 0=forward-facing; 1=leftward-facing; 2=backward-facing (toward you); 3=rightward-facing,  
    		
    		if (biomeSignBlock.getUnlocalizedName().toLowerCase().contains("ganyssurface"))
    		{
    			// Set the sign and its orientation
				world.setBlock(signX, signY, signZ, biomeSignBlock);
				world.setBlockMetadataWithNotify(signX, signY, signZ, ((signFacing + this.coordBaseMode + (this.coordBaseMode==0 || this.coordBaseMode==1 ? 2: 0))*4)%16, 2);
				
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
    			world.setBlock(signX, signY, signZ, biomeSignBlock, ((signFacing + this.coordBaseMode%2)*4)%16, 2); // 2 is "send change to clients without block update notification"
        		world.setTileEntity(signX, signY, signZ, signContents);
    		}
    		
    		
    		if (GeneralConfig.decorateVillageCenter)
    		{
    			// Banner
        		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
        		if (testForBanner!=null)
    			{
        			int bannerXBB = 7;
        			int bannerYBB = 1;
        			int bannerZBB = 1;
        			int bannerX = this.getXWithOffset(bannerXBB, bannerZBB);
        			int bannerY = this.getYWithOffset(bannerYBB);
                    int bannerZ = this.getZWithOffset(bannerXBB, bannerZBB);
                    int bannerFacing = 0; // 0=backward-facing (toward you); 1=rightward-facing; 2=forward-facing; 3=leftward-facing;  
                    
                    // Place a foundation
                    this.fillWithMetadataBlocks(world, structureBB, bannerXBB, bannerYBB-3, bannerZBB, bannerXBB, bannerYBB-1, bannerZBB, Blocks.sandstone, 0, Blocks.sandstone, 0, false);
                    
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
        			{1, 1, 1, -1, 0},
        			{5, 1, 0, -1, 0},
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
		public void buildComponent(StructureComponent start, List components, Random random)
		{
			//LogHelper.info("coordBaseMode: " + this.coordBaseMode);
			// Southward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode<=1 ? 5 : 4), this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode<=1 ? 5 : 4), 1, this.getComponentType());
			// Northward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode>=2 ? 5 : 4), this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode>=2 ? 5 : 4), 3, this.getComponentType());
		}
    	
		/*
		 * Construct the structure
		 */
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;
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
            
        	// Generate or otherwise obtain village name and banner and colors
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, random,
        			this.getXWithOffset(8, 1),
        			this.getYWithOffset(1),
        			this.getZWithOffset(8, 1));
        	int townColor = villageNBTtag.getInteger("townColor");
        	int townColor2 = villageNBTtag.getInteger("townColor2");
        	
        	// Set sandstone ground and clear area above
        	this.fillWithBlocks(world, structureBB, 1, 0, 1, 10, 0, 10, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithAir(world, structureBB, 1, 1, 1, 10, 5, 10);
        	
        	// Fill foundation
        	for (int x = 1; x <= 10; ++x)
            {
	        	for (int z = 1; z <= 10; ++z)
	            {
	                this.func_151554_b(world, Blocks.sandstone, 0, x, -1, z, structureBB);
                }
            }
        	
        	// Path hitches at the ends
        	this.fillWithBlocks(world, structureBB, 0, 0, 5, 0, 0, 7, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithAir(world, structureBB, 0, 1, 5, 0, 5, 7);
        	for (int w = 5; w <= 7; ++w) {this.func_151554_b(world, Blocks.sandstone, 0, 0, -1, w, structureBB);}
        	this.fillWithBlocks(world, structureBB, 11, 0, 4, 11, 0, 6, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithAir(world, structureBB, 11, 1, 4, 11, 5, 6);
        	for (int w = 4; w <= 6; ++w) {this.func_151554_b(world, Blocks.sandstone, 0, 11, -1, w, structureBB);}
        	this.fillWithBlocks(world, structureBB, 4, 0, 0, 6, 0, 0, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithAir(world, structureBB, 4, 1, 0, 6, 5, 0);
        	for (int w = 4; w <= 6; ++w) {this.func_151554_b(world, Blocks.sandstone, 0, w, -1, 0, structureBB);}
        	this.fillWithBlocks(world, structureBB, 5, 0, 11, 7, 0, 11, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithAir(world, structureBB, 5, 1, 11, 7, 5, 11);
        	for (int w = 5; w <= 7; ++w) {this.func_151554_b(world, Blocks.sandstone, 0, w, -1, 11, structureBB);}
        	
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
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 4, 5, 4, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 4, 5, 7, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 7, 5, 7, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 7, 5, 4, structureBB);
            
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
            		
            		uvw = uvw.south(); metaCycle = (metaBase+Math.abs(this.getXWithOffset(uvw.getX(), uvw.getZ())%2 - (this.getZWithOffset(uvw.getX(), uvw.getZ())%2)*3) + uvw.getY())%4;
            		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(townColor2, metaCycle);
            		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvw.getX(), uvw.getY(), uvw.getZ(), structureBB);
            		
            		uvw = uvw.west(); metaCycle = (metaBase+Math.abs(this.getXWithOffset(uvw.getX(), uvw.getZ())%2 - (this.getZWithOffset(uvw.getX(), uvw.getZ())%2)*3) + uvw.getY())%4;
            		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(townColor2, metaCycle);
            		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvw.getX(), uvw.getY(), uvw.getZ(), structureBB);
            		
            		uvw = uvw.north(); metaCycle = (metaBase+Math.abs(this.getXWithOffset(uvw.getX(), uvw.getZ())%2 - (this.getZWithOffset(uvw.getX(), uvw.getZ())%2)*3) + uvw.getY())%4;
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
    		
    		int signFacing = 0; // 0=forward-facing; 1=leftward-facing; 2=backward-facing (toward you); 3=rightward-facing,  
    		
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
        			int bannerXBB = 10;
        			int bannerYBB = 1;
        			int bannerZBB = 10;
        			int bannerX = this.getXWithOffset(bannerXBB, bannerZBB);
        			int bannerY = this.getYWithOffset(bannerYBB);
                    int bannerZ = this.getZWithOffset(bannerXBB, bannerZBB);
                    int bannerFacing = 3; // 0=backward-facing (toward you); 1=rightward-facing; 2=forward-facing; 3=leftward-facing;  
                    
                    // Place a foundation
                    this.fillWithMetadataBlocks(world, structureBB, bannerXBB, bannerYBB-3, bannerZBB, bannerXBB, bannerYBB-1, bannerZBB, Blocks.sandstone, 0, Blocks.sandstone, 0, false);
                    
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
        			{10, 1, 8, -1, 0},
        			{1, 1, 10, -1, 0},
        			{1, 7, 10, -1, 0},
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
		public void buildComponent(StructureComponent start, List components, Random random)
		{
			//LogHelper.info("coordBaseMode: " + this.coordBaseMode);
			// Southward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode<=1 ? 5 : 4), this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode<=1 ? 5 : 4), 1, this.getComponentType());
			// Northward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode>=2 ? 5 : 4), this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode>=2 ? 5 : 4), 3, this.getComponentType());
		}
    	
		/*
		 * Construct the structure
		 */
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.standing_sign, 0, this); Block biomeSignBlock = (Block)blockObject[0];
        	Block biomeSandstoneWall = Block.getBlockFromName(ModObjects.sandstoneWallUTD); int biomeSandstoneMeta = 0;
        	if (biomeSandstoneWall==null) {blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, this); biomeSandstoneWall = (Block)blockObject[0]; biomeSandstoneMeta = (Integer)blockObject[1];}
        	
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
        			this.getXWithOffset(6, 4),
        			this.getYWithOffset(2),
        			this.getZWithOffset(6, 4));
        	int townColor = villageNBTtag.getInteger("townColor");
        	int townColor2 = villageNBTtag.getInteger("townColor2");
        	
        	// Set sandstone ground and clear area above
        	this.fillWithBlocks(world, structureBB, 1, 0, 1, 10, 0, 10, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithAir(world, structureBB, 1, 1, 1, 10, 5, 10);
        	
        	// Fill foundation
        	for (int x = 1; x <= 10; ++x)
            {
	        	for (int z = 1; z <= 10; ++z)
	            {
	                this.func_151554_b(world, Blocks.sandstone, 0, x, -1, z, structureBB);
                }
            }
        	
        	// Path hitches at the ends
        	this.fillWithBlocks(world, structureBB, 0, 0, 5, 0, 0, 7, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithAir(world, structureBB, 0, 1, 5, 0, 5, 7);
        	for (int w = 5; w <= 7; ++w) {this.func_151554_b(world, Blocks.sandstone, 0, 0, -1, w, structureBB);}
        	this.fillWithBlocks(world, structureBB, 11, 0, 4, 11, 0, 6, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithAir(world, structureBB, 11, 1, 4, 11, 5, 6);
        	for (int w = 4; w <= 6; ++w) {this.func_151554_b(world, Blocks.sandstone, 0, 11, -1, w, structureBB);}
        	this.fillWithBlocks(world, structureBB, 4, 0, 0, 6, 0, 0, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithAir(world, structureBB, 4, 1, 0, 6, 5, 0);
        	for (int w = 4; w <= 6; ++w) {this.func_151554_b(world, Blocks.sandstone, 0, w, -1, 0, structureBB);}
        	this.fillWithBlocks(world, structureBB, 5, 0, 11, 7, 0, 11, Blocks.sandstone, Blocks.sandstone, false);
        	this.fillWithAir(world, structureBB, 5, 1, 11, 7, 5, 11);
        	for (int w = 5; w <= 7; ++w) {this.func_151554_b(world, Blocks.sandstone, 0, w, -1, 11, structureBB);}
        	
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
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 4, 5, 4, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 4, 5, 7, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 7, 5, 7, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 7, 5, 4, structureBB);
            
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
            		
            		uvw = uvw.north(); metaCycle = (metaBase+Math.abs(this.getXWithOffset(uvw.getX(), uvw.getZ())%2 - (this.getZWithOffset(uvw.getX(), uvw.getZ())%2)*3) + uvw.getY())%4;
            		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(townColor2, metaCycle);
            		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvw.getX(), uvw.getY(), uvw.getZ(), structureBB);
            		
            		uvw = uvw.east(); metaCycle = (metaBase+Math.abs(this.getXWithOffset(uvw.getX(), uvw.getZ())%2 - (this.getZWithOffset(uvw.getX(), uvw.getZ())%2)*3) + uvw.getY())%4;
            		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(townColor2, metaCycle);
            		this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvw.getX(), uvw.getY(), uvw.getZ(), structureBB);
            		
            		uvw = uvw.south(); metaCycle = (metaBase+Math.abs(this.getXWithOffset(uvw.getX(), uvw.getZ())%2 - (this.getZWithOffset(uvw.getX(), uvw.getZ())%2)*3) + uvw.getY())%4;
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
    		
    		int signFacing = 0; // 0=forward-facing; 1=leftward-facing; 2=backward-facing (toward you); 3=rightward-facing,  
    		
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
        			int bannerXBB = 10;
        			int bannerYBB = 1;
        			int bannerZBB = 10;
        			int bannerX = this.getXWithOffset(bannerXBB, bannerZBB);
        			int bannerY = this.getYWithOffset(bannerYBB);
                    int bannerZ = this.getZWithOffset(bannerXBB, bannerZBB);
                    int bannerFacing = 3; // 0=backward-facing (toward you); 1=rightward-facing; 2=forward-facing; 3=leftward-facing;  
                    
                    // Place a foundation
                    this.fillWithMetadataBlocks(world, structureBB, bannerXBB, bannerYBB-3, bannerZBB, bannerXBB, bannerYBB-1, bannerZBB, Blocks.sandstone, 0, Blocks.sandstone, 0, false);
                    
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
        			{10, 1, 8, -1, 0},
        			{1, 1, 10, -1, 0},
        			{1, 7, 10, -1, 0},
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
