package astrotibs.villagenames.village.biomestructures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import astrotibs.villagenames.banner.TileEntityBanner;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.utility.FunctionsVN;
import astrotibs.villagenames.utility.FunctionsVN.MaterialType;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.village.StructureVillageVN;
import astrotibs.villagenames.village.StructureVillageVN.StartVN;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.common.ChestGenHooks;

public class SnowyStructures
{
	// -------------------- //
    // --- Start Pieces --- //
	// -------------------- //
	
	// --- Ice Spire --- //
    
    public static class SnowyMeetingPoint1 extends StartVN
    {
	    public SnowyMeetingPoint1() {}
		
		public SnowyMeetingPoint1(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, int terrainType)
		{
		    super(chunkManager, componentType, random, posX, posZ, components, terrainType);
		    
    		int width = 11;
    		int depth = 7;
    		int height = 7;
    		
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
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (new int[]{8,1,2,4})[this.coordBaseMode], this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + (new int[]{4,8,1,2})[this.coordBaseMode], 3, this.getComponentType());
			// Southward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (new int[]{2,1,8,4})[this.coordBaseMode], this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + (new int[]{4,2,1,8})[this.coordBaseMode], 1, this.getComponentType());
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
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, this.materialType, this.biome); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), this.materialType, this.biome); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1]; // Toward you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), this.materialType, this.biome); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1]; // Perpendicular to you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.standing_sign, 0, this.materialType, this.biome); Block biomeStandingSignBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, this.materialType, this.biome); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, this.materialType, this.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeLanternMeta = (Integer)blockObject[1];
        	
        	// TODO - stripped wood
        	// For stripped wood specifically
        	Block biomeStrippedWoodOrLogOrLogVerticBlock = biomeLogVertBlock; int biomeStrippedWoodOrLogOrLogVerticMeta = biomeLogVertMeta;
        	Block biomeStrippedWoodOrLogOrLogHorAlongBlock = biomeLogHorAlongBlock; int biomeStrippedWoodOrLogOrLogHorAlongMeta = biomeLogHorAlongMeta;
        	Block biomeStrippedWoodOrLogOrLogHorAcrossBlock = biomeLogHorAcrossBlock; int biomeStrippedWoodOrLogOrLogHorAcrossMeta = biomeLogHorAcrossMeta;
        	
        	// Try to see if stripped wood exists
        	if (biomeLogVertBlock == Blocks.log)
        	{
        		blockObject = ModObjects.chooseModStrippedWood(biomeLogVertMeta);
        		biomeStrippedWoodOrLogOrLogVerticBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogVerticMeta = (Integer)blockObject[1];
        		biomeStrippedWoodOrLogOrLogHorAlongBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogHorAlongMeta = (Integer)blockObject[1];
        		biomeStrippedWoodOrLogOrLogHorAcrossBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogHorAcrossMeta = (Integer)blockObject[1];
        	}
        	else if (biomeLogVertBlock == Blocks.log2)
        	{
        		blockObject = ModObjects.chooseModStrippedWood(biomeLogVertMeta+4);
        		biomeStrippedWoodOrLogOrLogVerticBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogVerticMeta = (Integer)blockObject[1];
        		biomeStrippedWoodOrLogOrLogHorAlongBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogHorAlongMeta = (Integer)blockObject[1];
        		biomeStrippedWoodOrLogOrLogHorAcrossBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogHorAcrossMeta = (Integer)blockObject[1];
        	}
        	// If it doesn't exist, try stripped logs
        	if (biomeStrippedWoodOrLogOrLogVerticBlock==Blocks.log || biomeStrippedWoodOrLogOrLogVerticBlock==Blocks.log2)
        	{
            	if (biomeLogVertBlock == Blocks.log)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedWoodOrLogOrLogVerticBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogVerticMeta = (Integer)blockObject[1];
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 1+(this.coordBaseMode%2==0? 1:0)); biomeStrippedWoodOrLogOrLogHorAlongBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogHorAlongMeta = (Integer)blockObject[1];
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 1+(this.coordBaseMode%2==0? 0:1)); biomeStrippedWoodOrLogOrLogHorAcrossBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogHorAcrossMeta = (Integer)blockObject[1];
            	}
            	else if (biomeLogVertBlock == Blocks.log2)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 0); biomeStrippedWoodOrLogOrLogVerticBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogVerticMeta = (Integer)blockObject[1];
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 1+(this.coordBaseMode%2==0? 1:0)); biomeStrippedWoodOrLogOrLogHorAlongBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogHorAlongMeta = (Integer)blockObject[1];
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 1+(this.coordBaseMode%2==0? 0:1)); biomeStrippedWoodOrLogOrLogHorAcrossBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogHorAcrossMeta = (Integer)blockObject[1];
            	}
        	}
        	
        	
        	
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
        			this.getXWithOffset(2, 5),
        			this.getYWithOffset(2),
        			this.getZWithOffset(2, 5));
        	
        	// Load the values of interest into memory
        	if (this.townColor==-1) {this.townColor = villageNBTtag.getInteger("townColor");}
        	if (this.townColor2==-1) {this.townColor2 = villageNBTtag.getInteger("townColor2");}
        	if (this.townColor3==-1) {this.townColor3 = villageNBTtag.getInteger("townColor3");}
        	if (this.townColor4==-1) {this.townColor4 = villageNBTtag.getInteger("townColor4");}
        	if (this.townColor5==-1) {this.townColor5 = villageNBTtag.getInteger("townColor5");}
        	if (this.townColor6==-1) {this.townColor6 = villageNBTtag.getInteger("townColor6");}
        	if (this.townColor7==-1) {this.townColor7 = villageNBTtag.getInteger("townColor7");}
        	if (this.namePrefix.equals("")) {this.namePrefix = villageNBTtag.getString("namePrefix");}
        	if (this.nameRoot.equals("")) {this.nameRoot = villageNBTtag.getString("nameRoot");}
        	if (this.nameSuffix.equals("")) {this.nameSuffix = villageNBTtag.getString("nameSuffix");}
        	if (this.villageType==null) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(villageNBTtag.getString("villageType"), FunctionsVN.VillageType.getVillageTypeFromBiome(world, (this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2));}
        	if (this.materialType==null) {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(villageNBTtag.getString("materialType"), FunctionsVN.MaterialType.getMaterialTemplateForBiome(world, (this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2));}
        	
        	// Top layer is grass path
        	for (int u=0; u<=11; u++) {for (int w=0; w<=7; w++) {
        		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, -1, w, structureBB); // Foundation
        		
        		StructureVillageVN.setPathSpecificBlock(world, this.materialType, this.biome, 0, this.getXWithOffset(u, w), this.getYWithOffset(0), this.getZWithOffset(u, w)); // Path
        		this.clearCurrentPositionBlocksUpwards(world, u, 1, w, structureBB); // Clear above
        	}}
        	
        	// Set grass
        	this.fillWithMetadataBlocks(world, structureBB, 0, 0, 7, 1, 0, 7, biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 0, 0, 0, 0, 0, 3, biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 1, 0, 0, 1, 0, 2, biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 2, 0, 0, 2, 0, 1, biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 3, 0, 0, 7, 0, 0, biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 5, 0, 7, 11, 0, 7, biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 11, 0, 0, 11, 0, 3, biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);
        	// Set dirt
        	this.fillWithMetadataBlocks(world, structureBB, 5, 0, 2, 8, 0, 5, biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);
        	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, 2, 0, 5, structureBB);
        	
        	// Place the "stripped wood" rim around the "art"
        	this.fillWithMetadataBlocks(world, structureBB, 5, 1, 2, 7, 1, 2, biomeStrippedWoodOrLogOrLogHorAcrossBlock, biomeStrippedWoodOrLogOrLogHorAcrossMeta, biomeStrippedWoodOrLogOrLogHorAcrossBlock, biomeStrippedWoodOrLogOrLogHorAcrossMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 5, 1, 3, 5, 1, 5, biomeStrippedWoodOrLogOrLogHorAlongBlock, biomeStrippedWoodOrLogOrLogHorAlongMeta, biomeStrippedWoodOrLogOrLogHorAlongBlock, biomeStrippedWoodOrLogOrLogHorAlongMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 6, 1, 5, 8, 1, 5, biomeStrippedWoodOrLogOrLogHorAcrossBlock, biomeStrippedWoodOrLogOrLogHorAcrossMeta, biomeStrippedWoodOrLogOrLogHorAcrossBlock, biomeStrippedWoodOrLogOrLogHorAcrossMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 8, 1, 2, 8, 1, 4, biomeStrippedWoodOrLogOrLogHorAlongBlock, biomeStrippedWoodOrLogOrLogHorAlongMeta, biomeStrippedWoodOrLogOrLogHorAlongBlock, biomeStrippedWoodOrLogOrLogHorAlongMeta, false);
        	
        	// Set snow layer
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.snow_layer, 0, this.materialType, this.biome); Block biomeSnowLayerBlock = (Block)blockObject[0];
        	this.placeBlockAtCurrentPosition(world, biomeSnowLayerBlock, 0, 0, 1, 7, structureBB);
        	this.fillWithMetadataBlocks(world, structureBB, 0, 1, 1, 0, 1, 3, biomeSnowLayerBlock, 0, biomeSnowLayerBlock, 0, false);
        	this.fillWithMetadataBlocks(world, structureBB, 0, 1, 0, 5, 1, 0, biomeSnowLayerBlock, 0, biomeSnowLayerBlock, 0, false);
        	this.placeBlockAtCurrentPosition(world, biomeSnowLayerBlock, 0, 8, 1, 7, structureBB);
        	this.fillWithMetadataBlocks(world, structureBB, 10, 1, 7, 11, 1, 7, biomeSnowLayerBlock, 0, biomeSnowLayerBlock, 0, false);
        	this.fillWithMetadataBlocks(world, structureBB, 11, 1, 0, 11, 1, 3, biomeSnowLayerBlock, 0, biomeSnowLayerBlock, 0, false);
        	this.fillWithMetadataBlocks(world, structureBB, 5, 2, 2, 8, 2, 5, biomeSnowLayerBlock, 0, biomeSnowLayerBlock, 0, false);
        	
        	// Ice spire
        	this.fillWithBlocks(world, structureBB, 6, 1, 3, 7, 2, 4, Blocks.packed_ice, Blocks.packed_ice, false);
        	this.fillWithBlocks(world, structureBB, 6, 3, 4, 6, 7, 4, Blocks.packed_ice, Blocks.packed_ice, false);
        	this.fillWithBlocks(world, structureBB, 7, 3, 3, 7, 5, 3, Blocks.packed_ice, Blocks.packed_ice, false);
        	
        	
        	// Place the sign base. Concrete if requested/allowed, ice otherwise
        	if (GeneralConfig.useVillageColors)
        	{
        		Object[] tryConcrete = ModObjects.chooseModConcrete(townColor);
            	Block concreteBlock = Blocks.stained_hardened_clay; int concreteMeta = townColor;
            	if (tryConcrete != null) {concreteBlock = (Block) tryConcrete[0]; concreteMeta = (Integer) tryConcrete[1];}
            	
            	this.placeBlockAtCurrentPosition(world, concreteBlock, concreteMeta, 2, 1, 5, structureBB);
        	}
        	else
        	{
        		this.placeBlockAtCurrentPosition(world, Blocks.packed_ice, 0, 2, 1, 5, structureBB);
        	}
        	
        	
            // Sign
            if (GeneralConfig.nameSign)
            {
            	int signXBB = 2;
    			int signYBB = 2;
    			int signZBB = 5;
                int signX = this.getXWithOffset(signXBB, signZBB);
                int signY = this.getYWithOffset(signYBB);
                int signZ = this.getZWithOffset(signXBB, signZBB);
        		
        		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);
        		
    			world.setBlock(signX, signY, signZ, biomeStandingSignBlock, StructureVillageVN.getSignRotationMeta(0, this.coordBaseMode, false), 2); // 2 is "send change to clients without block update notification"
        		world.setTileEntity(signX, signY, signZ, signContents);
            }
            
    		
    		
			// Banner    		
    		if (GeneralConfig.villageBanners)
    		{
    			Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
        		if (testForBanner!=null)
    			{
                    int bannerXBB = 10;
        			int bannerZBB = 2;
        			int bannerYBB = 1;
        			
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
    		
    		
            // Decor
            int[][] decorUVW = new int[][]{
            	{1, 1, 1},
            	{9, 1, 7},
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
            	ArrayList<BlueprintData> decorBlueprint = getRandomSnowyDecorBlueprint(this.materialType, this.biome, this.coordBaseMode, randomFromXYZ);
            	
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
        	
    		
    		
    		// Villagers
            if (!this.villagersGenerated)
            {
            	this.villagersGenerated=true;
            	
            	if (GeneralConfig.spawnVillagersInTownCenters)
            	{
	        		for (int[] ia : new int[][]{
	        			{3, 1, 3, -1, 0},
	        			{10, 1, 4, -1, 0},
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
            }
            
            return true;
        }
    }
    
    
    
	// --- Frozen Fountain --- //
    
    public static class SnowyMeetingPoint2 extends StartVN
    {
	    public SnowyMeetingPoint2() {}
		
		public SnowyMeetingPoint2(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, int terrainType)
		{
		    super(chunkManager, componentType, random, posX, posZ, components, terrainType);
		    
    		int width = 10;
    		int depth = 8;
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
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode%2==0?4:3), this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode%2==0?3:4), 3, this.getComponentType());
			// Southward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode%2==0?4:3), this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode%2==0?3:4), 1, this.getComponentType());
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
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, this.materialType, this.biome); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.oak_stairs, 0, this.materialType, this.biome); Block biomeWoodenStairsBlock = (Block)blockObject[0];
        	
        	if (this.field_143015_k < 0)
            {
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				new StructureBoundingBox(
        						this.boundingBox.minX+1, this.boundingBox.minZ+1,
        						this.boundingBox.maxX-1, this.boundingBox.maxZ-1), // Set the bounding box version as this bounding box but with Y going from 0 to 512
        				true, (byte)15, this.coordBaseMode);
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct a well in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.minY -1, 0);
            }
        	
        	
        	// Generate or otherwise obtain village name and banner and colors
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world,
        			this.getXWithOffset(9, 4),
        			this.getYWithOffset(1),
        			this.getZWithOffset(9, 4));
        	
        	// Load the values of interest into memory
        	if (this.townColor==-1) {this.townColor = villageNBTtag.getInteger("townColor");}
        	if (this.townColor2==-1) {this.townColor2 = villageNBTtag.getInteger("townColor2");}
        	if (this.townColor3==-1) {this.townColor3 = villageNBTtag.getInteger("townColor3");}
        	if (this.townColor4==-1) {this.townColor4 = villageNBTtag.getInteger("townColor4");}
        	if (this.townColor5==-1) {this.townColor5 = villageNBTtag.getInteger("townColor5");}
        	if (this.townColor6==-1) {this.townColor6 = villageNBTtag.getInteger("townColor6");}
        	if (this.townColor7==-1) {this.townColor7 = villageNBTtag.getInteger("townColor7");}
        	if (this.namePrefix.equals("")) {this.namePrefix = villageNBTtag.getString("namePrefix");}
        	if (this.nameRoot.equals("")) {this.nameRoot = villageNBTtag.getString("nameRoot");}
        	if (this.nameSuffix.equals("")) {this.nameSuffix = villageNBTtag.getString("nameSuffix");}
        	if (this.villageType==null) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(villageNBTtag.getString("villageType"), FunctionsVN.VillageType.getVillageTypeFromBiome(world, (this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2));}
        	if (this.materialType==null) {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(villageNBTtag.getString("materialType"), FunctionsVN.MaterialType.getMaterialTemplateForBiome(world, (this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2));}
        	
        	// Top layer is grass path
        	for (int u=1; u<=9; u++) {for (int w=1; w<=7; w++) {
        		
        		if (!(u==1&&w==1) && !(u==1&&w==7) && !(u==9&&w==7) && !(u==9&&w==1)) // Not in the corners
        		{
        			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, -1, w, structureBB); // Foundation
            		
            		StructureVillageVN.setPathSpecificBlock(world, this.materialType, this.biome, 0, this.getXWithOffset(u, w), this.getYWithOffset(0), this.getZWithOffset(u, w)); // Path
            		this.clearCurrentPositionBlocksUpwards(world, u, 1, w, structureBB); // Clear above
        		}
        	}}
        	
        	// Set grass
        	this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, 8, 0, 0, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, 10, 0, 2, structureBB);
        	this.fillWithMetadataBlocks(world, structureBB, 4, 0, 2, 6, 0, 6, biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 3, 0, 3, 3, 0, 5, biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 7, 0, 3, 7, 0, 5, biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);
        	// Set dirt
        	this.fillWithMetadataBlocks(world, structureBB, 5, 0, 2, 8, 0, 5, biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);
        	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, 2, 0, 5, structureBB);
        	this.fillWithMetadataBlocks(world, structureBB, 5, 0, 3, 5, 0, 5, biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);
        	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, 4, 0, 4, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, 6, 0, 4, structureBB);
        	// Stone brick for some reason
        	this.placeBlockAtCurrentPosition(world, Blocks.stonebrick, 0, 6, 0, 3, structureBB);
        	
        	// Ice fountain
        	
        	// Ice
        	this.fillWithBlocks(world, structureBB, 5, 1, 3, 5, 2, 5, Blocks.packed_ice, Blocks.packed_ice, false);
        	this.fillWithBlocks(world, structureBB, 4, 1, 4, 4, 2, 4, Blocks.packed_ice, Blocks.packed_ice, false);
        	this.fillWithBlocks(world, structureBB, 6, 1, 4, 6, 2, 4, Blocks.packed_ice, Blocks.packed_ice, false);
        	this.placeBlockAtCurrentPosition(world, Blocks.packed_ice, 0, 5, 3, 4, structureBB);
        	// Torch
        	world.setBlock(this.getXWithOffset(5, 4), this.getYWithOffset(4), this.getZWithOffset(5, 4), Blocks.torch, 0, 2);
        	
        	// Rim
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, (new int[]{2,1,3,0})[this.coordBaseMode], 6, 1, 2, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, (new int[]{2,1,3,0})[this.coordBaseMode], 5, 1, 2, structureBB); // Front-center
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, this.coordBaseMode%2==0?0:2,              4, 1, 2, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, (new int[]{2,1,3,0})[this.coordBaseMode], 4, 1, 3, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, this.coordBaseMode%2==0?0:2,              3, 1, 3, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, this.coordBaseMode%2==0?0:2,              3, 1, 4, structureBB); // Left-center
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, (new int[]{3,0,2,1})[this.coordBaseMode], 3, 1, 5, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, this.coordBaseMode%2==0?0:2,              4, 1, 5, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, (new int[]{3,0,2,1})[this.coordBaseMode], 4, 1, 6, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, (new int[]{3,0,2,1})[this.coordBaseMode], 5, 1, 6, structureBB); // Back-center
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, this.coordBaseMode%2==0?1:3,              6, 1, 6, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, (new int[]{3,0,2,1})[this.coordBaseMode], 6, 1, 5, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, this.coordBaseMode%2==0?1:3,              7, 1, 5, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, this.coordBaseMode%2==0?1:3,              7, 1, 4, structureBB); // Right-center
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, (new int[]{2,1,3,0})[this.coordBaseMode], 7, 1, 3, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeWoodenStairsBlock, this.coordBaseMode%2==0?1:3,              6, 1, 3, structureBB);
        	
        	
            // Add path nodules at the end
            for (int i=0; i<3; i++)
            {
        		for (int[] uw: new int[][]{
        			{i+4, 0},
        			{i+4, 8},
        			{0, i+3},
        			{10, i+3},
        		})
        		{
        			int k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(uw[0], uw[1]), this.getZWithOffset(uw[0], uw[1])) - 1;
            		
                    if (k > -1)
                    {
                    	this.clearCurrentPositionBlocksUpwards(world, uw[0], k+2-this.boundingBox.minY, uw[1], structureBB);
                    	StructureVillageVN.setPathSpecificBlock(world, this.materialType, this.biome, 0, this.getXWithOffset(uw[0], uw[1]), k, this.getZWithOffset(uw[0], uw[1]));
                   	}
        		}
            }
        	
        	
        	// Place the sign base. Concrete if requested/allowed, ice otherwise
        	if (GeneralConfig.useVillageColors)
        	{
        		Object[] tryConcrete = ModObjects.chooseModConcrete(townColor);
            	Block concreteBlock = Blocks.stained_hardened_clay; int concreteMeta = townColor;
            	if (tryConcrete != null) {concreteBlock = (Block) tryConcrete[0]; concreteMeta = (Integer) tryConcrete[1];}
            	
            	this.placeBlockAtCurrentPosition(world, concreteBlock, concreteMeta, 9, 0, 4, structureBB);
        	}
        	else
        	{
        		this.placeBlockAtCurrentPosition(world, biomePlankBlock, biomePlankMeta, 9, 0, 4, structureBB);
        	}
        	
            // Sign
            if (GeneralConfig.nameSign)
            {
            	int signXBB = 9;
    			int signYBB = 1;
    			int signZBB = 4;
                int signX = this.getXWithOffset(signXBB, signZBB);
                int signY = this.getYWithOffset(signYBB);
                int signZ = this.getZWithOffset(signXBB, signZBB);
        		
        		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);
        		
    			world.setBlock(signX, signY, signZ, biomeStandingSignBlock, StructureVillageVN.getSignRotationMeta(4, this.coordBaseMode, false), 2); // 2 is "send change to clients without block update notification"
        		world.setTileEntity(signX, signY, signZ, signContents);
            }
            
    		
    		
			// Banner    		
    		if (GeneralConfig.villageBanners)
    		{
    			Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
        		if (testForBanner!=null)
    			{
                    int bannerXBB = 8;
        			int bannerZBB = 7;
        			int bannerYBB = 1;
        			
        			int bannerX = this.getXWithOffset(bannerXBB, bannerZBB);
        			int bannerY = this.getYWithOffset(bannerYBB);
                    int bannerZ = this.getZWithOffset(bannerXBB, bannerZBB);
                    
                    // Place a plank foundation
                    this.placeBlockAtCurrentPosition(world, biomePlankBlock, biomePlankMeta, bannerXBB, bannerYBB-1, bannerZBB, structureBB);
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
            	
            	if (GeneralConfig.spawnVillagersInTownCenters)
            	{
	        		for (int[] ia : new int[][]{
	        			{7, 1, 1, -1, 0},
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
            }
            
            return true;
        }
    }
    
    
	// --- Snowy Pavilion --- //
    
    public static class SnowyMeetingPoint3 extends StartVN
    {
	    public SnowyMeetingPoint3() {}
		
		public SnowyMeetingPoint3(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, int terrainType)
		{
		    super(chunkManager, componentType, random, posX, posZ, components, terrainType);
		    
    		int width = 6;
    		int depth = 6;
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
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 2, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 2, 3, this.getComponentType());
			// Southward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 2, 1, this.getComponentType());
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
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, this.materialType, this.biome); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), this.materialType, this.biome); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1]; // Toward you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), this.materialType, this.biome); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1]; // Perpendicular to you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wall_sign, 0, this.materialType, this.biome); Block biomeWallSignBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, this.materialType, this.biome); Block biomeFenceBlock = (Block)blockObject[0];
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeLanternMeta = (Integer)blockObject[1];
        	
        	// TODO - stripped wood 
        	// For stripped wood specifically
        	Block biomeStrippedWoodOrLogOrLogVerticBlock = biomeLogVertBlock; int biomeStrippedWoodOrLogOrLogVerticMeta = biomeLogVertMeta;
        	Block biomeStrippedWoodOrLogOrLogHorAlongBlock = biomeLogHorAlongBlock; int biomeStrippedWoodOrLogOrLogHorAlongMeta = biomeLogHorAlongMeta;
        	Block biomeStrippedWoodOrLogOrLogHorAcrossBlock = biomeLogHorAcrossBlock; int biomeStrippedWoodOrLogOrLogHorAcrossMeta = biomeLogHorAcrossMeta;
        	
        	// Try to see if stripped wood exists
        	if (biomeLogVertBlock == Blocks.log)
        	{
        		blockObject = ModObjects.chooseModStrippedWood(biomeLogVertMeta);
        		biomeStrippedWoodOrLogOrLogVerticBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogVerticMeta = (Integer)blockObject[1];
        		biomeStrippedWoodOrLogOrLogHorAlongBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogHorAlongMeta = (Integer)blockObject[1];
        		biomeStrippedWoodOrLogOrLogHorAcrossBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogHorAcrossMeta = (Integer)blockObject[1];
        	}
        	else if (biomeLogVertBlock == Blocks.log2)
        	{
        		blockObject = ModObjects.chooseModStrippedWood(biomeLogVertMeta+4);
        		biomeStrippedWoodOrLogOrLogVerticBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogVerticMeta = (Integer)blockObject[1];
        		biomeStrippedWoodOrLogOrLogHorAlongBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogHorAlongMeta = (Integer)blockObject[1];
        		biomeStrippedWoodOrLogOrLogHorAcrossBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogHorAcrossMeta = (Integer)blockObject[1];
        	}
        	// If it doesn't exist, try stripped logs
        	if (biomeStrippedWoodOrLogOrLogVerticBlock==Blocks.log || biomeStrippedWoodOrLogOrLogVerticBlock==Blocks.log2)
        	{
            	if (biomeLogVertBlock == Blocks.log)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedWoodOrLogOrLogVerticBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogVerticMeta = (Integer)blockObject[1];
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 1+(this.coordBaseMode%2==0? 1:0)); biomeStrippedWoodOrLogOrLogHorAlongBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogHorAlongMeta = (Integer)blockObject[1];
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 1+(this.coordBaseMode%2==0? 0:1)); biomeStrippedWoodOrLogOrLogHorAcrossBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogHorAcrossMeta = (Integer)blockObject[1];
            	}
            	else if (biomeLogVertBlock == Blocks.log2)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 0); biomeStrippedWoodOrLogOrLogVerticBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogVerticMeta = (Integer)blockObject[1];
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 1+(this.coordBaseMode%2==0? 1:0)); biomeStrippedWoodOrLogOrLogHorAlongBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogHorAlongMeta = (Integer)blockObject[1];
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 1+(this.coordBaseMode%2==0? 0:1)); biomeStrippedWoodOrLogOrLogHorAcrossBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogHorAcrossMeta = (Integer)blockObject[1];
            	}
        	}
        	// If THAT doesn't exist, set to biome variant
        	if (biomeStrippedWoodOrLogOrLogVerticBlock==Blocks.log || biomeStrippedWoodOrLogOrLogVerticBlock==Blocks.log2)
        	{
        		biomeStrippedWoodOrLogOrLogVerticBlock = biomeLogVertBlock; biomeStrippedWoodOrLogOrLogVerticMeta = (Integer)blockObject[1];
        		biomeStrippedWoodOrLogOrLogHorAlongBlock = biomeLogHorAlongBlock; biomeStrippedWoodOrLogOrLogHorAlongMeta = biomeLogHorAlongMeta;
        		biomeStrippedWoodOrLogOrLogHorAcrossBlock = biomeLogHorAcrossBlock; biomeStrippedWoodOrLogOrLogHorAcrossMeta = biomeLogHorAcrossMeta;
        	}
        	
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
        			this.getXWithOffset(3, 3),
        			this.getYWithOffset(3),
        			this.getZWithOffset(3, 3));
        	
        	// Load the values of interest into memory
        	if (this.townColor==-1) {this.townColor = villageNBTtag.getInteger("townColor");}
        	if (this.townColor2==-1) {this.townColor2 = villageNBTtag.getInteger("townColor2");}
        	if (this.townColor3==-1) {this.townColor3 = villageNBTtag.getInteger("townColor3");}
        	if (this.townColor4==-1) {this.townColor4 = villageNBTtag.getInteger("townColor4");}
        	if (this.townColor5==-1) {this.townColor5 = villageNBTtag.getInteger("townColor5");}
        	if (this.townColor6==-1) {this.townColor6 = villageNBTtag.getInteger("townColor6");}
        	if (this.townColor7==-1) {this.townColor7 = villageNBTtag.getInteger("townColor7");}
        	if (this.namePrefix.equals("")) {this.namePrefix = villageNBTtag.getString("namePrefix");}
        	if (this.nameRoot.equals("")) {this.nameRoot = villageNBTtag.getString("nameRoot");}
        	if (this.nameSuffix.equals("")) {this.nameSuffix = villageNBTtag.getString("nameSuffix");}
        	if (this.villageType==null) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(villageNBTtag.getString("villageType"), FunctionsVN.VillageType.getVillageTypeFromBiome(world, (this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2));}
        	if (this.materialType==null) {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(villageNBTtag.getString("materialType"), FunctionsVN.MaterialType.getMaterialTemplateForBiome(world, (this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2));}
        	
        	// Top layer is grass path
        	for (int u=1; u<=5; u++) {for (int w=1; w<=5; w++) {
        		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, -1, w, structureBB); // Foundation
        		this.clearCurrentPositionBlocksUpwards(world, u, 1, w, structureBB); // Clear above
        		StructureVillageVN.setPathSpecificBlock(world, this.materialType, this.biome, 0, this.getXWithOffset(u, w), this.getYWithOffset(0), this.getZWithOffset(u, w)); // Path
        	}}
        	
        	// Set grass
        	this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, 2, 0, 2, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, 2, 0, 4, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, 4, 0, 4, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, 4, 0, 2, structureBB);
        	// Set these grass blocks into ground level
    		for (int[] uw: new int[][]{
    			{0, 6},
    			{5, 0},
    			{6, 1},
    		})
    		{
    			int k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(uw[0], uw[1]), this.getZWithOffset(uw[0], uw[1])) - 1;
        		
                if (k > -1) {this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, uw[0], k+1-this.boundingBox.minY, uw[1], structureBB);}
    		}
        	
            // Add path nodules at the end
            for (int i=0; i<3; i++)
            {
        		for (int[] uw: new int[][]{
        			{i+2, 0},
        			{i+2, 6},
        			{0, i+2},
        			{6, i+2},
        		})
        		{
        			int k = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(uw[0], uw[1]), this.getZWithOffset(uw[0], uw[1])) - 1;
            		
                    if (k > -1)
                    {
                    	this.clearCurrentPositionBlocksUpwards(world, uw[0], k+2-this.boundingBox.minY, uw[1], structureBB);
                    	StructureVillageVN.setPathSpecificBlock(world, this.materialType, this.biome, 0, this.getXWithOffset(uw[0], uw[1]), k, this.getZWithOffset(uw[0], uw[1]));
                   	}
        		}
            }
        	
            // Pavilion
            
            // Posts
            this.fillWithMetadataBlocks(world, structureBB, 2, 1, 2, 2, 3, 2, biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            this.fillWithMetadataBlocks(world, structureBB, 2, 1, 4, 2, 3, 4, biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            this.fillWithMetadataBlocks(world, structureBB, 4, 1, 4, 4, 3, 4, biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            this.fillWithMetadataBlocks(world, structureBB, 4, 1, 2, 4, 3, 2, biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            
        	// Place the "stripped wood" roof
        	this.fillWithMetadataBlocks(world, structureBB, 2, 4, 2, 3, 4, 2, biomeStrippedWoodOrLogOrLogHorAcrossBlock, biomeStrippedWoodOrLogOrLogHorAcrossMeta, biomeStrippedWoodOrLogOrLogHorAcrossBlock, biomeStrippedWoodOrLogOrLogHorAcrossMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 2, 4, 3, 2, 4, 4, biomeStrippedWoodOrLogOrLogHorAlongBlock, biomeStrippedWoodOrLogOrLogHorAlongMeta, biomeStrippedWoodOrLogOrLogHorAlongBlock, biomeStrippedWoodOrLogOrLogHorAlongMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 3, 4, 4, 4, 4, 4, biomeStrippedWoodOrLogOrLogHorAcrossBlock, biomeStrippedWoodOrLogOrLogHorAcrossMeta, biomeStrippedWoodOrLogOrLogHorAcrossBlock, biomeStrippedWoodOrLogOrLogHorAcrossMeta, false);
        	this.fillWithMetadataBlocks(world, structureBB, 4, 4, 2, 4, 4, 3, biomeStrippedWoodOrLogOrLogHorAlongBlock, biomeStrippedWoodOrLogOrLogHorAlongMeta, biomeStrippedWoodOrLogOrLogHorAlongBlock, biomeStrippedWoodOrLogOrLogHorAlongMeta, false);
        	
        	// Add torches
        	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, 1, 4, 3, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeLanternMeta, 1, 3, 3, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, 5, 4, 3, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeLanternMeta, 5, 3, 3, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, 3, 4, 1, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeLanternMeta, 3, 3, 1, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, 3, 4, 5, structureBB);
        	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeLanternMeta, 3, 3, 5, structureBB);
        	
        	
        	// Place the sign base. Concrete if requested/allowed
        	if (GeneralConfig.useVillageColors)
        	{
        		Object[] tryConcrete = ModObjects.chooseModConcrete(townColor);
            	Block concreteBlock = Blocks.stained_hardened_clay; int concreteMeta = townColor;
            	if (tryConcrete != null) {concreteBlock = (Block) tryConcrete[0]; concreteMeta = (Integer) tryConcrete[1];}
            	
            	this.fillWithMetadataBlocks(world, structureBB, 3, 3, 3, 3, 5, 3, concreteBlock, concreteMeta, concreteBlock, concreteMeta, false);
        	}
        	else
        	{
        		this.fillWithMetadataBlocks(world, structureBB, 3, 3, 3, 3, 5, 3, biomeStrippedWoodOrLogOrLogVerticBlock, biomeStrippedWoodOrLogOrLogVerticMeta, biomeStrippedWoodOrLogOrLogVerticBlock, biomeStrippedWoodOrLogOrLogVerticMeta, false);
        	}
        	
        	
            // Sign
            if (GeneralConfig.nameSign)
            {
            	int signXBB = 2;
    			int signYBB = 3;
    			int signZBB = 3;
                int signX = this.getXWithOffset(signXBB, signZBB);
                int signY = this.getYWithOffset(signYBB);
                int signZ = this.getZWithOffset(signXBB, signZBB);
        		
        		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);
        		
    			world.setBlock(signX, signY, signZ, biomeWallSignBlock, StructureVillageVN.getSignRotationMeta(3, this.coordBaseMode, true), 2); // 2 is "send change to clients without block update notification"
        		world.setTileEntity(signX, signY, signZ, signContents);
        		
                int signXBB2 = 4;
                int signX2 = this.getXWithOffset(signXBB2, signZBB);
                int signZ2 = this.getZWithOffset(signXBB2, signZBB);
                
                // I need to make a duplicate TileEntity because the first one gets consumed when applied to the first sign
        		TileEntitySign signContents2 = new TileEntitySign();
        		for (int i=0; i<4; i++) {signContents2.signText[i] = signContents.signText[i];}
                
    			world.setBlock(signX2, signY, signZ2, biomeWallSignBlock, StructureVillageVN.getSignRotationMeta(1, this.coordBaseMode, true), 2); // 2 is "send change to clients without block update notification"
        		world.setTileEntity(signX2, signY, signZ2, signContents2);
            }
            
    		
    		
    		
			// Banner    		
    		if (GeneralConfig.villageBanners)
    		{
    			Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
        		if (testForBanner!=null)
    			{
                    int bannerXBB = 5;
        			int bannerZBB = 0;
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
            	
            	if (GeneralConfig.spawnVillagersInTownCenters)
            	{
	        		for (int[] ia : new int[][]{
	        			{5, 1, 1, -1, 0},
	        			{5, 1, 5, -1, 0},
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
            }
            
            return true;
        }
    }
    
    
    
    // ------------------ //
    // --- Components --- //
    // ------------------ //
    
    
    // --- Animal Pen 1 --- //
    
    public static class SnowyAnimalPen1 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public int townColor=-1;
    	public int townColor2=-1;
    	public int townColor3=-1;
    	public int townColor4=-1;
    	public int townColor5=-1;
    	public int townColor6=-1;
    	public int townColor7=-1;
    	public String namePrefix="";
    	public String nameRoot="";
    	public String nameSuffix="";
    	public BiomeGenBase biome=null;
    	
    	// Make foundation with blanks as empty air and F as foundation spaces
        private static final String[] foundationPattern = new String[]{
        		"FFFFFFFFF",
        		"FFFFFFFFF",
        		"FFFFFFFFF",
        		"FFFFFFFFF",
        		"FFFFFFFFF",
        		"FFFFFFFFF",
        		"FFFFFFFFF",
        		"FFFFFFFF ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 7;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	private static final int INCREASE_MIN_U = 2;
    	private static final int DECREASE_MAX_U = 0;
    	
    	private int averageGroundLevel = -1;
    	
        public SnowyAnimalPen1() {}

        public SnowyAnimalPen1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            if (start!=null)
            {
            	this.villageType=start.villageType;
            	this.materialType=start.materialType;
            	this.townColor=start.townColor;
            	this.townColor2=start.townColor2;
            	this.townColor3=start.townColor3;
            	this.townColor4=start.townColor4;
            	this.townColor5=start.townColor5;
            	this.townColor6=start.townColor6;
            	this.townColor7=start.townColor7;
            	this.namePrefix=start.namePrefix;
            	this.nameRoot=start.nameRoot;
            	this.nameSuffix=start.nameSuffix;
            	this.biome=start.biome;
            }
        }
        
        public static SnowyAnimalPen1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new SnowyAnimalPen1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
            	if (this.averageGroundLevel < 0)
                {
            		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
            				// Set the bounding box version as this bounding box but with Y going from 0 to 512
            				new StructureBoundingBox(
            						// Modified to center onto front of house
            						this.boundingBox.minX+(this.coordBaseMode%2==0?INCREASE_MIN_U:0), this.boundingBox.minZ+(this.coordBaseMode%2==0?0:INCREASE_MIN_U),
            						this.boundingBox.maxX-(this.coordBaseMode%2==0?DECREASE_MAX_U:0), this.boundingBox.maxZ-(this.coordBaseMode%2==0?0:DECREASE_MAX_U)),
            				true, (byte)1, this.coordBaseMode);
            		
                    if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
                    
                    this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
                }
            }
        	
        	// In the event that this village construction is resuming after being unloaded
        	// you may need to reestablish the village name/color/type info
            if (
                	this.villageType==null
                	|| this.materialType==null
                	|| this.townColor==-1
                	|| this.townColor2==-1
                	|| this.townColor3==-1
                	|| this.townColor4==-1
                	|| this.townColor5==-1
                	|| this.townColor6==-1
                	|| this.townColor7==-1
                	|| this.nameRoot.equals("")
            		)
            {
            	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, 
            			(this.boundingBox.minX+this.boundingBox.maxX)/2,
            			(this.boundingBox.minY+this.boundingBox.maxY)/2,
            			(this.boundingBox.minZ+this.boundingBox.maxZ)/2);
            	
            	// Load the values of interest into memory
            	this.townColor = villageNBTtag.getInteger("townColor");
            	this.townColor2 = villageNBTtag.getInteger("townColor2");
            	this.townColor3 = villageNBTtag.getInteger("townColor3");
            	this.townColor4 = villageNBTtag.getInteger("townColor4");
            	this.townColor5 = villageNBTtag.getInteger("townColor5");
            	this.townColor6 = villageNBTtag.getInteger("townColor6");
            	this.townColor7 = villageNBTtag.getInteger("townColor7");
            	this.namePrefix = villageNBTtag.getString("namePrefix");
            	this.nameRoot = villageNBTtag.getString("nameRoot");
            	this.nameSuffix = villageNBTtag.getString("nameSuffix");
            	this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(villageNBTtag.getString("villageType"), FunctionsVN.VillageType.getVillageTypeFromBiome(world, (this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2));
            	this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(villageNBTtag.getString("materialType"), FunctionsVN.MaterialType.getMaterialTemplateForBiome(world, (this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2));
            }
        	// Reestablish biome if start was null or something
            if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
        	
            Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, this.materialType, this.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, this.materialType, this.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	
        	// Clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            	this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
            	// Make dirt foundation
            	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
            }}
            
            // Make foundation with blanks as empty air and F as foundation spaces
            
        	for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
        		
            		if (foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase().equals("F"))
            		{
            			// If marked with F: fill with dirt foundation
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-1, w, structureBB);
                    	// top with grass
                    	this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
            		}
            		else if (world.getBlock(this.getXWithOffset(u, w), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(u, w))==biomeDirtBlock)
            		{
            			// Otherwise, if dirt, add dirt foundation and then cap with grass:
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
            		}
                }
            }
            
            
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, this.materialType, this.biome); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	
        	// TODO - stripped wood
        	// Stripped Log (Vertical)
        	Block biomeStrippedLogVerticBlock = biomeLogVertBlock; int biomeStrippedLogVerticMeta = biomeLogVertMeta;
        	// Try to see if stripped logs exist
        	if (biomeStrippedLogVerticBlock==Blocks.log || biomeStrippedLogVerticBlock==Blocks.log2)
        	{
            	if (biomeLogVertBlock == Blocks.log)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
            	}
            	else if (biomeLogVertBlock == Blocks.log2)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
            	}
        	}
            for(int[] uuvvww : new int[][]{
            	{2,1,1, 8,1,1}, 
            	{2,1,7, 8,1,7}, 
            	{2,1,2, 2,1,6}, 
            	{8,1,2, 8,1,6}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);	
            }

            
        	// Grass
        	for(int[] uuvvww : new int[][]{
            	{3,1,2, 7,1,6},  
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.grass, 0, Blocks.grass, 0, false);	
            }
            
            
            // Fences
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, this.materialType, this.biome); Block biomeFenceBlock = (Block)blockObject[0];
            for (int[] uuvvww : new int[][]{
            	// Surrounding
            	// Front
            	{2,2,1, 3,2,1}, {7,2,1, 8,2,1}, 
            	// Back
            	{2,2,7, 8,2,7}, 
            	// Left
            	{2,2,2, 2,2,6}, 
            	// Right
            	{8,2,2, 8,2,6}, 
            	// Torch posts
            	{4,2,1, 4,3,1}, {6,2,1, 6,3,1}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
            
            
        	// Fence Gate
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence_gate, 0, this.materialType, this.biome); Block biomeFenceGateBlock = (Block)blockObject[0]; int biomeFenceGateMeta = (Integer)blockObject[1];
        	for(int[] uvw : new int[][]{
            	{5,2,1}, 
            	})
            {
        		this.placeBlockAtCurrentPosition(world, biomeFenceGateBlock, StructureVillageVN.getMetadataWithOffset(biomeFenceGateBlock, biomeFenceGateMeta, this.coordBaseMode), uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
            
            // Torches
            for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	{4,4,1, -1}, {6,4,1, -1}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, this.materialType, this.biome); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	// Trough
            	{4,2,3, 6,2,3}, 
            	{4,2,4, 4,2,4}, 
            	{4,2,5, 6,2,5}, 
            	{6,2,4, 6,2,4}, 
            	// Trough Bottom
            	{5,1,4, 5,1,4}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
            
            
            // Water
            for(int[] uvw : new int[][]{
            	{5,2,4}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.flowing_water, 0, uvw[0], uvw[1], uvw[2], structureBB); 
            }
        	
        	
            // Wood stairs
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.oak_stairs, 0, this.materialType, this.biome); Block biomeWoodStairsBlock = (Block)blockObject[0];
        	for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
        		// Front
        		{4,1,0, 0}, {5,1,0, 3}, {6,1,0, 1}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
            }
            
            
            // Snow Layer
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.snow_layer, 0, this.materialType, this.biome); Block biomeSnowLayerBlock = (Block)blockObject[0];
            for(int[] uvwm : new int[][]{
            	// Outside of pen
            	{0,1,0, 0}, {0,1,1, 0}, {0,1,2, 0}, {0,1,3, 5}, {0,1,5, 3}, {0,1,6, 0}, {0,1,7, 0}, 
            	{1,1,0, 0}, {1,1,1, 0}, {1,1,2, 4}, {1,2,5, 2}, {1,1,6, 4}, {1,1,7, 0}, 
            	{2,1,0, 0}, 
            	{3,1,0, 4}, 
            	{7,1,0, 4}, 
            	// Inside of pen
            	{3,2,2, 0}, {3,2,3, 0}, {3,2,4, 0}, {3,2,5, 0}, {3,2,6, 0}, 
            	{4,2,2, 0}, {4,2,6, 0}, 
            	{5,2,6, 0}, 
            	{6,2,2, 0}, {6,2,6, 0}, 
            	{7,2,2, 0}, {7,2,3, 0}, {7,2,4, 0}, {7,2,5, 0}, {7,2,6, 0}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeSnowLayerBlock, biomeSnowLayerBlock==Blocks.snow_layer ? uvwm[3] : 0, uvwm[0], uvwm[1], uvwm[2], structureBB); 
            }
            
            
            // Snow Block
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.snow, 0, this.materialType, this.biome); Block biomeSnowBlock = (Block)blockObject[0]; int biomeSnowMeta = (Integer)blockObject[1];
            for(int[] uvwm : new int[][]{
            	{0,1,4}, 
            	{1,1,3}, {1,1,4}, {1,2,4}, {1,1,5}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeSnowBlock, biomeSnowMeta, uvwm[0], uvwm[1], uvwm[2], structureBB); 
            }
    		
            
            // Clear path for easier entry
            for (int[] uvw : new int[][]{
        		{4, GROUND_LEVEL, -1}, 
        		{5, GROUND_LEVEL, -1}, 
        		{6, GROUND_LEVEL, -1}, 
           		})
        	{
            	int pathU = uvw[0];
                int pathV = uvw[1];
                int pathW = uvw[2];
                
                this.clearCurrentPositionBlocksUpwards(world, pathU, pathV, pathW, structureBB);
            	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, pathU, pathV-1, pathW, structureBB);
            	/*// Place dirt if the block to be set as path is empty
            	if (world.isAirBlock(this.getXWithOffset(pathU, pathW), this.getYWithOffset(pathV-1), this.getZWithOffset(pathU, pathW)))
            	{
                	this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, pathU, pathV-1, pathW, structureBB);
            	}*/
            	
            	StructureVillageVN.setPathSpecificBlock(world, this.materialType, this.biome, 0, this.getXWithOffset(pathU, pathW), this.getYWithOffset(pathV-1), this.getZWithOffset(pathU, pathW));
        	}

        	
            // Animals
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	// Animals
            	for (int[] uvw : new int[][]{
        			{5,2,2}, 
        			})
        		{
                	EntityLiving animal = StructureVillageVN.getVillageAnimal(world, random, false, this.materialType==MaterialType.MUSHROOM); // Because horses can escape the pen
                    animal.setLocationAndAngles((double)this.getXWithOffset(uvw[0], uvw[2]) + 0.5D, (double)this.getYWithOffset(uvw[1]) + 0.5D, (double)this.getZWithOffset(uvw[0], uvw[2]) + 0.5D, random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(animal);
                    
                    // Dirt block underneath
                    //this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, uvw[0], uvw[1]-1, uvw[2], structureBB);
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
    
    
    // --- Animal Pen 2 --- //
    
    public static class SnowyAnimalPen2 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public int townColor=-1;
    	public int townColor2=-1;
    	public int townColor3=-1;
    	public int townColor4=-1;
    	public int townColor5=-1;
    	public int townColor6=-1;
    	public int townColor7=-1;
    	public String namePrefix="";
    	public String nameRoot="";
    	public String nameSuffix="";
    	public BiomeGenBase biome=null;
    	
    	// Make foundation with blanks as empty air and F as foundation spaces
        private static final String[] foundationPattern = new String[]{
        		"FFFFFFFFF",
        		"FFFFFFFFF",
        		"FFFFFFFFF",
        		"FFFFFFFFF",
        		"FFFFFFFFF",
        		" FFFFFFFF",
        		" FFFF    ",
        		" FFFF    ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 6;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 3;
    	
    	private int averageGroundLevel = -1;
    	
        public SnowyAnimalPen2() {}

        public SnowyAnimalPen2(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            if (start!=null)
            {
            	this.villageType=start.villageType;
            	this.materialType=start.materialType;
            	this.townColor=start.townColor;
            	this.townColor2=start.townColor2;
            	this.townColor3=start.townColor3;
            	this.townColor4=start.townColor4;
            	this.townColor5=start.townColor5;
            	this.townColor6=start.townColor6;
            	this.townColor7=start.townColor7;
            	this.namePrefix=start.namePrefix;
            	this.nameRoot=start.nameRoot;
            	this.nameSuffix=start.nameSuffix;
            	this.biome=start.biome;
            }
        }
        
        public static SnowyAnimalPen2 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new SnowyAnimalPen2(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
            	if (this.averageGroundLevel < 0)
                {
            		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
            				// Set the bounding box version as this bounding box but with Y going from 0 to 512
            				new StructureBoundingBox(
            						// Modified to center onto front of house
            						this.boundingBox.minX+(this.coordBaseMode%2==0?INCREASE_MIN_U:0), this.boundingBox.minZ+(this.coordBaseMode%2==0?0:INCREASE_MIN_U),
            						this.boundingBox.maxX-(this.coordBaseMode%2==0?DECREASE_MAX_U:0), this.boundingBox.maxZ-(this.coordBaseMode%2==0?0:DECREASE_MAX_U)),
            				true, (byte)1, this.coordBaseMode);
            		
                    if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
                    
                    this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
                }
            }
        	
        	// In the event that this village construction is resuming after being unloaded
        	// you may need to reestablish the village name/color/type info
            if (
                	this.villageType==null
                	|| this.materialType==null
                	|| this.townColor==-1
                	|| this.townColor2==-1
                	|| this.townColor3==-1
                	|| this.townColor4==-1
                	|| this.townColor5==-1
                	|| this.townColor6==-1
                	|| this.townColor7==-1
                	|| this.nameRoot.equals("")
            		)
            {
            	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, 
            			(this.boundingBox.minX+this.boundingBox.maxX)/2,
            			(this.boundingBox.minY+this.boundingBox.maxY)/2,
            			(this.boundingBox.minZ+this.boundingBox.maxZ)/2);
            	
            	// Load the values of interest into memory
            	this.townColor = villageNBTtag.getInteger("townColor");
            	this.townColor2 = villageNBTtag.getInteger("townColor2");
            	this.townColor3 = villageNBTtag.getInteger("townColor3");
            	this.townColor4 = villageNBTtag.getInteger("townColor4");
            	this.townColor5 = villageNBTtag.getInteger("townColor5");
            	this.townColor6 = villageNBTtag.getInteger("townColor6");
            	this.townColor7 = villageNBTtag.getInteger("townColor7");
            	this.namePrefix = villageNBTtag.getString("namePrefix");
            	this.nameRoot = villageNBTtag.getString("nameRoot");
            	this.nameSuffix = villageNBTtag.getString("nameSuffix");
            	this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(villageNBTtag.getString("villageType"), FunctionsVN.VillageType.getVillageTypeFromBiome(world, (this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2));
            	this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(villageNBTtag.getString("materialType"), FunctionsVN.MaterialType.getMaterialTemplateForBiome(world, (this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2));
            }
        	// Reestablish biome if start was null or something
            if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
        	
            Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, this.materialType, this.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, this.materialType, this.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	
        	// Clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            	this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
            	// Make dirt foundation
            	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
            }}
            
            // Make foundation with blanks as empty air and F as foundation spaces
            
        	for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
        		
            		if (foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase().equals("F"))
            		{
            			// If marked with F: fill with dirt foundation
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-1, w, structureBB);
                    	// top with grass
                    	this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
            		}
            		else if (world.getBlock(this.getXWithOffset(u, w), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(u, w))==biomeDirtBlock)
            		{
            			// Otherwise, if dirt, add dirt foundation and then cap with grass:
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
            		}
                }
            }
            
            
            // Fences
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, this.materialType, this.biome); Block biomeFenceBlock = (Block)blockObject[0];
            for (int[] uuvvww : new int[][]{
            	// Surrounding
            	{4,1,0, 4,1,2}, 
            	{5,1,2, 8,1,2}, 
            	{8,1,3, 8,1,7}, 
            	{0,1,7, 7,1,7}, 
            	{0,1,3, 0,1,6}, 
            	{1,1,0, 1,1,3}, 
            	{2,1,0, 2,1,0}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
            
            
        	// Fence Gate
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence_gate, 0, this.materialType, this.biome); Block biomeFenceGateBlock = (Block)blockObject[0]; int biomeFenceGateMeta = (Integer)blockObject[1];
        	for(int[] uvw : new int[][]{
            	{3,1,0}, 
            	})
            {
        		this.placeBlockAtCurrentPosition(world, biomeFenceGateBlock, StructureVillageVN.getMetadataWithOffset(biomeFenceGateBlock, biomeFenceGateMeta, this.coordBaseMode), uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
            
            // Lantern
        	blockObject = ModObjects.chooseModLanternBlock(false); Block biomeSittingLanternBlock = (Block)blockObject[0]; int biomeSittingLanternMeta = (Integer)blockObject[1];
            for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	{0,2,7}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeSittingLanternBlock, biomeSittingLanternMeta, uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
            
            
            // Water
            for(int[] uvw : new int[][]{
            	{1,0,5}, {1,0,6}, {2,0,6}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.flowing_water, 0, uvw[0], uvw[1], uvw[2], structureBB); 
            }

            
        	// Grass pit in the ground
        	for(int[] uvw : new int[][]{
            	{2,0,3},  
            	})
            {
            	this.clearCurrentPositionBlocksUpwards(world, uvw[0], uvw[1], uvw[2], structureBB); 
            	this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, uvw[0], uvw[1]-1, uvw[2], structureBB); 
            }
            
            
            // Snow Layer
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.snow_layer, 0, this.materialType, this.biome); Block biomeSnowLayerBlock = (Block)blockObject[0];
            for(int[] uvwm : new int[][]{
            	{2,1,4, 0}, 
            	{3,1,2, 0}, {3,1,3, 0}, {3,1,4, 0}, {3,1,5, 3}, 
            	{4,1,3, 0}, {4,1,6, 0}, 
            	{5,1,3, 0}, {5,1,4, 2}, {5,1,6, 0}, 
            	{6,1,3, 0}, {6,1,5, 0}, {6,1,6, 0}, 
            	{7,1,4, 0}, {7,1,5, 0}, {7,1,6, 0}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeSnowLayerBlock, biomeSnowLayerBlock==Blocks.snow_layer ? uvwm[3] : 0, uvwm[0], uvwm[1], uvwm[2], structureBB); 
            }
            
            
            // Snow Block
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.snow, 0, this.materialType, this.biome); Block biomeSnowBlock = (Block)blockObject[0]; int biomeSnowMeta = (Integer)blockObject[1];
            for(int[] uvwm : new int[][]{
            	{4,1,4}, 
            	{4,1,5}, 
            	{4,2,5}, 
            	{5,1,5}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeSnowBlock, biomeSnowMeta, uvwm[0], uvwm[1], uvwm[2], structureBB); 
            }
    		
            
            // Clear path for easier entry
            for (int[] uvw : new int[][]{
        		{2, GROUND_LEVEL, -1}, 
        		{3, GROUND_LEVEL, -1}, 
           		})
        	{
            	int pathU = uvw[0];
                int pathV = uvw[1];
                int pathW = uvw[2];
                
                this.clearCurrentPositionBlocksUpwards(world, pathU, pathV, pathW, structureBB);
            	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, pathU, pathV-1, pathW, structureBB);
            	/*// Place dirt if the block to be set as path is empty
            	if (world.isAirBlock(this.getXWithOffset(pathU, pathW), this.getYWithOffset(pathV-1), this.getZWithOffset(pathU, pathW)))
            	{
                	this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, pathU, pathV-1, pathW, structureBB);
            	}*/
            	
            	StructureVillageVN.setPathSpecificBlock(world, this.materialType, this.biome, 0, this.getXWithOffset(pathU, pathW), this.getYWithOffset(pathV-1), this.getZWithOffset(pathU, pathW));
        	}

        	
            // Animals
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	// Animals
            	for (int[] uvw : new int[][]{
        			{2,0,3}, 
        			{6,1,4}, 
        			})
        		{
                	EntityLiving animal = StructureVillageVN.getVillageAnimal(world, random, false, this.materialType==MaterialType.MUSHROOM); // Because horses can escape the pen
                    animal.setLocationAndAngles((double)this.getXWithOffset(uvw[0], uvw[2]) + 0.5D, (double)this.getYWithOffset(uvw[1]) + 0.5D, (double)this.getZWithOffset(uvw[0], uvw[2]) + 0.5D, random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(animal);
                    
                    // Dirt block underneath
                    //this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, uvw[0], uvw[1]-1, uvw[2], structureBB);
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
    
    
    // --- Armorer House 1 --- //
    
    public static class SnowyArmorerHouse1 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public int townColor=-1;
    	public int townColor2=-1;
    	public int townColor3=-1;
    	public int townColor4=-1;
    	public int townColor5=-1;
    	public int townColor6=-1;
    	public int townColor7=-1;
    	public String namePrefix="";
    	public String nameRoot="";
    	public String nameSuffix="";
    	public BiomeGenBase biome=null;
    	
    	// Make foundation with blanks as empty air and F as foundation spaces
        private static final String[] foundationPattern = new String[]{
        		"FFFFFFF",
        		"FFFFFFF",
        		"FFFFFFF",
        		"FFFFFFF",
        		"FFFFFFF",
        		"FFFFFFF",
        		"FFFFFFF",
        		"FFFFFFF",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 9;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	
    	private int averageGroundLevel = -1;
    	
        public SnowyArmorerHouse1() {}

        public SnowyArmorerHouse1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            if (start!=null)
            {
            	this.villageType=start.villageType;
            	this.materialType=start.materialType;
            	this.townColor=start.townColor;
            	this.townColor2=start.townColor2;
            	this.townColor3=start.townColor3;
            	this.townColor4=start.townColor4;
            	this.townColor5=start.townColor5;
            	this.townColor6=start.townColor6;
            	this.townColor7=start.townColor7;
            	this.namePrefix=start.namePrefix;
            	this.nameRoot=start.nameRoot;
            	this.nameSuffix=start.nameSuffix;
            	this.biome=start.biome;
            }
        }
        
        public static SnowyArmorerHouse1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new SnowyArmorerHouse1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
            	if (this.averageGroundLevel < 0)
                {
            		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
            				// Set the bounding box version as this bounding box but with Y going from 0 to 512
            				new StructureBoundingBox(
            						// Modified to center onto front of house
            						this.boundingBox.minX+(this.coordBaseMode%2==0?INCREASE_MIN_U:0), this.boundingBox.minZ+(this.coordBaseMode%2==0?0:INCREASE_MIN_U),
            						this.boundingBox.maxX-(this.coordBaseMode%2==0?DECREASE_MAX_U:0), this.boundingBox.maxZ-(this.coordBaseMode%2==0?0:DECREASE_MAX_U)),
            				true, (byte)1, this.coordBaseMode);
            		
                    if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
                    
                    this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
                }
            }
        	
        	// In the event that this village construction is resuming after being unloaded
        	// you may need to reestablish the village name/color/type info
            if (
                	this.villageType==null
                	|| this.materialType==null
                	|| this.townColor==-1
                	|| this.townColor2==-1
                	|| this.townColor3==-1
                	|| this.townColor4==-1
                	|| this.townColor5==-1
                	|| this.townColor6==-1
                	|| this.townColor7==-1
                	|| this.nameRoot.equals("")
            		)
            {
            	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, 
            			(this.boundingBox.minX+this.boundingBox.maxX)/2,
            			(this.boundingBox.minY+this.boundingBox.maxY)/2,
            			(this.boundingBox.minZ+this.boundingBox.maxZ)/2);
            	
            	// Load the values of interest into memory
            	this.townColor = villageNBTtag.getInteger("townColor");
            	this.townColor2 = villageNBTtag.getInteger("townColor2");
            	this.townColor3 = villageNBTtag.getInteger("townColor3");
            	this.townColor4 = villageNBTtag.getInteger("townColor4");
            	this.townColor5 = villageNBTtag.getInteger("townColor5");
            	this.townColor6 = villageNBTtag.getInteger("townColor6");
            	this.townColor7 = villageNBTtag.getInteger("townColor7");
            	this.namePrefix = villageNBTtag.getString("namePrefix");
            	this.nameRoot = villageNBTtag.getString("nameRoot");
            	this.nameSuffix = villageNBTtag.getString("nameSuffix");
            	this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(villageNBTtag.getString("villageType"), FunctionsVN.VillageType.getVillageTypeFromBiome(world, (this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2));
            	this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(villageNBTtag.getString("materialType"), FunctionsVN.MaterialType.getMaterialTemplateForBiome(world, (this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2));
            }
        	// Reestablish biome if start was null or something
            if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
        	
            Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, this.materialType, this.biome); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, this.materialType, this.biome); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	
        	// Clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            	this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
            	// Make dirt foundation
            	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
            }}
            
            // Make foundation with blanks as empty air and F as foundation spaces
            
        	for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
        		
            		if (foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase().equals("F"))
            		{
            			// If marked with F: fill with dirt foundation
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-1, w, structureBB);
                    	// top with grass
                    	this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
            		}
            		else if (world.getBlock(this.getXWithOffset(u, w), this.getYWithOffset(GROUND_LEVEL-1), this.getZWithOffset(u, w))==biomeDirtBlock)
            		{
            			// Otherwise, if dirt, add dirt foundation and then cap with grass:
            			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, GROUND_LEVEL-2, w, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, u, GROUND_LEVEL-1, w, structureBB);
            		}
                }
            }
            
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, this.materialType, this.biome); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
            
        	// TODO - stripped wood
        	// Stripped Log (Vertical)
        	Block biomeStrippedLogVerticBlock = biomeLogVertBlock; int biomeStrippedLogVerticMeta = biomeLogVertMeta;
        	// Try to see if stripped logs exist
        	if (biomeStrippedLogVerticBlock==Blocks.log || biomeStrippedLogVerticBlock==Blocks.log2)
        	{
            	if (biomeLogVertBlock == Blocks.log)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
            	}
            	else if (biomeLogVertBlock == Blocks.log2)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
            	}
        	}
            for(int[] uuvvww : new int[][]{
            	// Floor
            	{1,1,1, 5,1,3}, 
            	{1,1,4, 1,1,5}, {5,1,4, 5,1,5}, 
            	// Front wall
            	{1,2,1, 1,3,1}, {5,2,1, 5,3,1}, 
            	{2,4,1, 2,5,1}, {4,4,1, 4,5,1}, 
            	{3,5,1, 3,6,1}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);	
            }

        	            
            // Torches, part 2
            for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	{1,2,5, -1}, {5,2,5, -1}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
            
        	
        	Object[] dioriteBlockObject = ModObjects.chooseModDioriteBlock();
        	Block dioriteStairsBlock = ModObjects.chooseModDioriteStairsBlock();
        	Object[] dioriteWallObject = ModObjects.chooseModDioriteWallBlock();
        	if (dioriteBlockObject==null || dioriteStairsBlock==null || dioriteWallObject==null)
        	{
            	dioriteBlockObject = new Object[]{Blocks.cobblestone, 0};
            	dioriteStairsBlock = Blocks.stone_stairs;
            	dioriteWallObject = new Object[]{Blocks.cobblestone_wall, 0};
        	}
        	Block dioriteBlock=(Block)dioriteBlockObject[0]; int dioriteMeta=(Integer)dioriteBlockObject[1];
        	Block dioriteWallBlock=(Block)dioriteWallObject[0]; int dioriteWallMeta=(Integer)dioriteWallObject[1];
        	
        	// Diorite block
        	for(int[] uuvvww : new int[][]{
            	// Furnace base
        		{2,1,4, 4,1,5}, 
        		// Body
        		{2,2,5, 2,2,5}, {4,2,5, 4,2,5}, 
        		// Chimney
        		{3,3,5, 3,7,5}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], dioriteBlock, dioriteMeta, dioriteBlock, dioriteMeta, false);	
            }
        	// Diorite stairs
        	for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
        		{2,3,5, 0}, {4,3,5, 1}, 
        		})
            {
                this.placeBlockAtCurrentPosition(world, dioriteStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);
           }
        	// Diorite wall
        	for(int[] uuvvww : new int[][]{
            	{3,8,5, 3,8,5}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], dioriteWallBlock, dioriteWallMeta, dioriteWallBlock, dioriteWallMeta, false);	
            }
            
            
            // Blast Furnace - this is a TileEntity and needs to have its meta assigned manually
        	for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
        		{3,2,5, 2}, 
        		})
            {
        		blockObject = ModObjects.chooseModBlastFurnaceBlock(uvw[3], this.coordBaseMode); Block blastFurnaceBlock = (Block) blockObject[0]; int blastFurnaceMeta = (Integer) blockObject[1];
                this.placeBlockAtCurrentPosition(world, blastFurnaceBlock, 0, uvw[0], uvw[1], uvw[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvw[0], uvw[2]), this.getYWithOffset(uvw[1]), this.getZWithOffset(uvw[0], uvw[2]), blastFurnaceMeta, 2);
            }

        	            
            // Torches, part 2
            for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	{3,5,4, 2}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
            
            
            // Individual vertical stripped log placements easier for the back wall
            for(int[] uvw : new int[][]{
            	{1,2,6}, 
            	{2,1,6}, {2,3,6}, {2,5,6}, 
            	{3,2,6}, {3,4,6}, {3,6,6}, 
            	{4,1,6}, {4,3,6}, {4,5,6}, 
            	{5,2,6}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, uvw[0], uvw[1], uvw[2], structureBB); 
            }
            
            
        	// Stripped Log (Along)
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), this.materialType, this.biome); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1];
        	Block biomeStrippedLogHorizAlongBlock = biomeLogHorAlongBlock; int biomeStrippedLogHorizAlongMeta = biomeLogHorAlongMeta;
        	// Try to see if stripped logs exist
        	if (biomeStrippedLogHorizAlongBlock==Blocks.log || biomeStrippedLogHorizAlongBlock==Blocks.log2)
        	{
            	if (biomeLogVertBlock == Blocks.log)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 1+(this.coordBaseMode%2==0? 1:0)); biomeStrippedLogHorizAlongBlock = (Block)blockObject[0]; biomeStrippedLogHorizAlongMeta = (Integer)blockObject[1];
            	}
            	else if (biomeLogVertBlock == Blocks.log2)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 1+(this.coordBaseMode%2==0? 1:0)); biomeStrippedLogHorizAlongBlock = (Block)blockObject[0]; biomeStrippedLogHorizAlongMeta = (Integer)blockObject[1];
            	}
        	}
            for(int[] uvw : new int[][]{
            	// Front wall
            	{2,2,1}, {2,3,1}, 
            	{3,4,1}, 
            	{4,2,1}, {4,3,1}, 
            	// Back wall
            	{1,1,6}, {1,3,6}, 
            	{2,2,6}, {2,4,6}, 
            	{3,1,6}, {3,3,6}, {3,5,6}, 
            	{4,2,6}, {4,4,6}, 
            	{5,1,6}, {5,3,6}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeStrippedLogHorizAlongBlock, biomeStrippedLogHorizAlongMeta, uvw[0], uvw[1], uvw[2], structureBB); 
            }
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.planks, 0, this.materialType, this.biome); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	// Roof
            	{0,2,0, 0,3,7}, 
            	{1,4,0, 1,5,7}, 
            	{2,6,0, 2,6,7}, 
            	{3,7,0, 3,7,4}, {3,7,6, 3,7,7}, 
            	{4,6,0, 4,6,7}, 
            	{5,4,0, 5,5,7}, 
            	{6,2,0, 6,3,7}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
            
            
            // Fences
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, this.materialType, this.biome); Block biomeFenceBlock = (Block)blockObject[0];
            for (int[] uuvvww : new int[][]{
            	// Lantern post
            	{3,5,0, 3,6,0}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
        	
            
            // Lantern
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
            for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	{3,4,0}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
            
        	
            // Tall Grass
            for (int[] uvwg : new int[][]{ // g is grass type
            	{5,1,0, 0}, 
            })
            {
    			if (uvwg[3]==0) // Short grass
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 1, uvwg[0], uvwg[1], uvwg[2], structureBB);
    			}
    			else // Tall grass
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 2, uvwg[0], uvwg[1], uvwg[2], structureBB);
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 11, uvwg[0], uvwg[1]+1, uvwg[2], structureBB);
    			}
            }
            
            
            // Snow Layer
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.snow_layer, 0, this.materialType, this.biome); Block biomeSnowLayerBlock = (Block)blockObject[0];
            for(int[] uvwm : new int[][]{
            	{0,1,0, 0}, {0,1,1, 0}, {0,1,3, 0}, {0,1,4, 0}, {0,1,5, 0}, {0,1,7, 0}, 
            	{1,1,0, 0}, {1,1,7, 0}, 
            	{2,1,0, 0}, {2,1,7, 0}, 
            	{3,1,7, 0}, 
            	{4,1,0, 0}, {4,1,7, 0}, 
            	{5,1,7, 0}, 
            	{6,1,0, 0}, {6,1,1, 0}, {6,1,2, 0}, {6,1,3, 0}, {6,1,4, 0}, {6,1,5, 0}, {6,1,6, 0}, {6,1,7, 0}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeSnowLayerBlock, biomeSnowLayerBlock==Blocks.snow_layer ? uvwm[3] : 0, uvwm[0], uvwm[1], uvwm[2], structureBB); 
            }
        	
        	
            // Wood stairs
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.oak_stairs, 0, this.materialType, this.biome); Block biomeWoodStairsBlock = (Block)blockObject[0];
        	for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
        		// Front entry
        		{3,1,0, 3}, 
        		// Surrounding the chimney
        		{2,7,4, 3}, {4,7,4, 3}, 
        		{2,7,5, 0}, 
        		{2,7,6, 2}, {4,7,6, 2}, 
        		{4,7,5, 1}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
            }
        	
        	
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 5;
        	int chestV = 2;
        	int chestW = 2;
        	int chestO = 3; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	this.placeBlockAtCurrentPosition(world, Blocks.chest, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo("vn_armorer");
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
            
            
            // Doors
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.wooden_door, 0, this.materialType, this.biome); Block biomeWoodDoorBlock = (Block)blockObject[0];
            for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
            	// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
            	{3,2,1, 2, 1, 0}, 
            })
            {
            	for (int height=0; height<=1; height++)
            	{
            		this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
            				uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
            	}
            }
    		
            
            // Clear path for easier entry
            for (int[] uvw : new int[][]{
        		{1, GROUND_LEVEL, -1}, 
        		{2, GROUND_LEVEL, -1}, 
        		{3, GROUND_LEVEL, -1}, 
        		{4, GROUND_LEVEL, -1}, 
        		{5, GROUND_LEVEL, -1}, 
           		})
        	{
            	int pathU = uvw[0];
                int pathV = uvw[1];
                int pathW = uvw[2];
                
                this.clearCurrentPositionBlocksUpwards(world, pathU, pathV, pathW, structureBB);
            	this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, pathU, pathV-1, pathW, structureBB);
            	/*// Place dirt if the block to be set as path is empty
            	if (world.isAirBlock(this.getXWithOffset(pathU, pathW), this.getYWithOffset(pathV-1), this.getZWithOffset(pathU, pathW)))
            	{
                	this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, pathU, pathV-1, pathW, structureBB);
            	}*/
            	
            	StructureVillageVN.setPathSpecificBlock(world, this.materialType, this.biome, 0, this.getXWithOffset(pathU, pathW), this.getYWithOffset(pathV-1), this.getZWithOffset(pathU, pathW));
        	}
            
            
    		// Entities
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	// Villager
            	int u = 5;
            	int v = 2;
            	int w = 2;
            	
            	while (u==5 && w==2)
            	{
                	u = 1+random.nextInt(5);
                	w = 2+random.nextInt(3);
            	}
            	
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
    
    
    
    // ------------------ //
    // --- Road Decor --- //
    // ------------------ //
    

    
    
    
    // ------------------- //
    // --- Biome Decor --- //
    // ------------------- //
    
	/**
	 * Returns a list of blocks and coordinates used to construct a decor piece
	 */
    public static ArrayList<BlueprintData> getRandomSnowyDecorBlueprint(MaterialType materialType, BiomeGenBase biome, int coordBaseMode, Random random)
	{
		int decorCount = 3;
		return getSnowyDecorBlueprint(random.nextInt(decorCount), materialType, biome, coordBaseMode, random);
	}
	public static ArrayList<BlueprintData> getSnowyDecorBlueprint(int decorType, MaterialType materialType, BiomeGenBase biome, int coordBaseMode, Random random)
	{
		ArrayList<BlueprintData> blueprint = new ArrayList(); // The blueprint to export
		
		
		// Generate per-material blocks
		
		Object[] blockObject;
    	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, materialType, biome); Block biomeFenceBlock = (Block)blockObject[0];
    	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeLanternMeta = (Integer)blockObject[1];
    	
    	boolean genericBoolean=false;
    	
    	int lanternX; int lanternY; int lanternZ;
    	
    	int decorOrientation = random.nextInt(4);
    	
    	// Make lantern base
    	switch (decorType)
    	{
    	case 2: // Lateral lanterns
    		lanternX =  decorOrientation==3 ? -1 : decorOrientation==1 ? 1 : 0;
    		lanternZ =  decorOrientation==0 ? -1 : decorOrientation==2 ? 1 : 0;
    		BlueprintData.addPlaceBlock(blueprint, lanternX, 3, lanternZ, biomeFenceBlock);
    		BlueprintData.addPlaceBlock(blueprint, lanternX, 2, lanternZ, biomeHangingLanternBlock, biomeLanternMeta);
    		
    		lanternX =  decorOrientation==3 ? 1 : decorOrientation==1 ? -1 : 0;
    		lanternZ =  decorOrientation==0 ? 1 : decorOrientation==2 ? -1 : 0;
    		BlueprintData.addPlaceBlock(blueprint, lanternX, 3, lanternZ, biomeFenceBlock);
    		BlueprintData.addPlaceBlock(blueprint, lanternX, 2, lanternZ, biomeHangingLanternBlock, biomeLanternMeta);
    		
    	case 1: // Second lantern opposite
    		lanternX =  decorOrientation==0 ? -1 : decorOrientation==2 ? 1 : 0;
    		lanternZ =  decorOrientation==3 ? -1 : decorOrientation==1 ? 1 : 0;
    		BlueprintData.addPlaceBlock(blueprint, lanternX, 3, lanternZ, biomeFenceBlock);
    		BlueprintData.addPlaceBlock(blueprint, lanternX, 2, lanternZ, biomeHangingLanternBlock, biomeLanternMeta);
    		
    	case 0: // Single lantern
    		lanternX =  decorOrientation==0 ? 1 : decorOrientation==2 ? -1 : 0;
    		lanternZ =  decorOrientation==3 ? 1 : decorOrientation==1 ? -1 : 0;
    		BlueprintData.addPlaceBlock(blueprint, lanternX, 3, lanternZ, biomeFenceBlock);
    		BlueprintData.addPlaceBlock(blueprint, lanternX, 2, lanternZ, biomeHangingLanternBlock, biomeLanternMeta);
    		
    		// Base post
    		BlueprintData.addFillWithBlocks(blueprint, 0, 0, 0, 0, 3, 0, biomeFenceBlock);
    	}
    	
        
        // Return the decor blueprint
        return blueprint;
	}
}
