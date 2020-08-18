package astrotibs.villagenames.village.biomestructures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import astrotibs.villagenames.banner.TileEntityBanner;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.utility.FunctionsVN;
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
        	this.townColor = villageNBTtag.getInteger("townColor");
        	this.townColor2 = villageNBTtag.getInteger("townColor2");
        	// Generate additional colors to be used in the town
        	if (this.townColorA==-1) {this.townColorA = StructureVillageVN.generateUnusedColor(new int[]{this.townColor, this.townColor2}, random, false);}
        	if (this.townColorB==-1) {this.townColorB = StructureVillageVN.generateUnusedColor(new int[]{this.townColor, this.townColor2, this.townColorA}, random, false);}
        	if (this.townColorC==-1) {this.townColorC = StructureVillageVN.generateUnusedColor(new int[]{this.townColor, this.townColor2, this.townColorA, this.townColorB}, random, false);}
        	
    		this.namePrefix = villageNBTtag.getString("namePrefix");
    		this.nameRoot = villageNBTtag.getString("nameRoot");
    		this.nameSuffix = villageNBTtag.getString("nameSuffix");
        	
        	// Top layer is grass path
        	for (int u=0; u<=11; u++) {for (int w=0; w<=7; w++) {
        		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, -1, w, structureBB); // Foundation
        		
        		StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(u, w), this.getYWithOffset(0), this.getZWithOffset(u, w)); // Path
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
        	this.placeBlockAtCurrentPosition(world, Blocks.snow_layer, 0, 0, 1, 7, structureBB);
        	this.fillWithMetadataBlocks(world, structureBB, 0, 1, 1, 0, 1, 3, Blocks.snow_layer, 0, Blocks.snow_layer, 0, false);
        	this.fillWithMetadataBlocks(world, structureBB, 0, 1, 0, 5, 1, 0, Blocks.snow_layer, 0, Blocks.snow_layer, 0, false);
        	this.placeBlockAtCurrentPosition(world, Blocks.snow_layer, 0, 8, 1, 7, structureBB);
        	this.fillWithMetadataBlocks(world, structureBB, 10, 1, 7, 11, 1, 7, Blocks.snow_layer, 0, Blocks.snow_layer, 0, false);
        	this.fillWithMetadataBlocks(world, structureBB, 11, 1, 0, 11, 1, 3, Blocks.snow_layer, 0, Blocks.snow_layer, 0, false);
        	this.fillWithMetadataBlocks(world, structureBB, 5, 2, 2, 8, 2, 5, Blocks.snow_layer, 0, Blocks.snow_layer, 0, false);
        	
        	// Ice spire
        	this.fillWithBlocks(world, structureBB, 6, 1, 3, 7, 2, 4, Blocks.packed_ice, Blocks.packed_ice, false);
        	this.fillWithBlocks(world, structureBB, 6, 3, 4, 6, 7, 4, Blocks.packed_ice, Blocks.packed_ice, false);
        	this.fillWithBlocks(world, structureBB, 7, 3, 3, 7, 5, 3, Blocks.packed_ice, Blocks.packed_ice, false);
        	
        	
        	// Place the sign base. Concrete if requested/allowed, ice otherwise
        	if (GeneralConfig.decorateVillageCenter)
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
            int signXBB = 2;
			int signYBB = 2;
			int signZBB = 5;
            int signX = this.getXWithOffset(signXBB, signZBB);
            int signY = this.getYWithOffset(signYBB);
            int signZ = this.getZWithOffset(signXBB, signZBB);
    		
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
            	ArrayList<BlueprintData> decorBlueprint = getRandomSnowyDecorBlueprint(this, this.coordBaseMode, randomFromXYZ);
            	
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
        	this.townColor = villageNBTtag.getInteger("townColor");
        	this.townColor2 = villageNBTtag.getInteger("townColor2");
        	// Generate additional colors to be used in the town
        	if (this.townColorA==-1) {this.townColorA = StructureVillageVN.generateUnusedColor(new int[]{this.townColor, this.townColor2}, random, false);}
        	if (this.townColorB==-1) {this.townColorB = StructureVillageVN.generateUnusedColor(new int[]{this.townColor, this.townColor2, this.townColorA}, random, false);}
        	if (this.townColorC==-1) {this.townColorC = StructureVillageVN.generateUnusedColor(new int[]{this.townColor, this.townColor2, this.townColorA, this.townColorB}, random, false);}
        	
    		this.namePrefix = villageNBTtag.getString("namePrefix");
    		this.nameRoot = villageNBTtag.getString("nameRoot");
    		this.nameSuffix = villageNBTtag.getString("nameSuffix");
        	
        	// Top layer is grass path
        	for (int u=1; u<=9; u++) {for (int w=1; w<=7; w++) {
        		
        		if (!(u==1&&w==1) && !(u==1&&w==7) && !(u==9&&w==7) && !(u==9&&w==1)) // Not in the corners
        		{
        			this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, -1, w, structureBB); // Foundation
            		
            		StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(u, w), this.getYWithOffset(0), this.getZWithOffset(u, w)); // Path
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
                    	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(uw[0], uw[1]), k, this.getZWithOffset(uw[0], uw[1]));
                   	}
        		}
            }
        	
        	
        	// Place the sign base. Concrete if requested/allowed, ice otherwise
        	if (GeneralConfig.decorateVillageCenter)
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
            int signXBB = 9;
			int signYBB = 1;
			int signZBB = 4;
            int signX = this.getXWithOffset(signXBB, signZBB);
            int signY = this.getYWithOffset(signYBB);
            int signZ = this.getZWithOffset(signXBB, signZBB);
    		
    		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);
    		
			world.setBlock(signX, signY, signZ, biomeStandingSignBlock, StructureVillageVN.getSignRotationMeta(4, this.coordBaseMode, false), 2); // 2 is "send change to clients without block update notification"
    		world.setTileEntity(signX, signY, signZ, signContents);
    		
    		
			// Banner
    		if (GeneralConfig.decorateVillageCenter)
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
        	this.townColor = villageNBTtag.getInteger("townColor");
        	this.townColor2 = villageNBTtag.getInteger("townColor2");
        	// Generate additional colors to be used in the town
        	if (this.townColorA==-1) {this.townColorA = StructureVillageVN.generateUnusedColor(new int[]{this.townColor, this.townColor2}, random, false);}
        	if (this.townColorB==-1) {this.townColorB = StructureVillageVN.generateUnusedColor(new int[]{this.townColor, this.townColor2, this.townColorA}, random, false);}
        	if (this.townColorC==-1) {this.townColorC = StructureVillageVN.generateUnusedColor(new int[]{this.townColor, this.townColor2, this.townColorA, this.townColorB}, random, false);}
        	
    		this.namePrefix = villageNBTtag.getString("namePrefix");
    		this.nameRoot = villageNBTtag.getString("nameRoot");
    		this.nameSuffix = villageNBTtag.getString("nameSuffix");
        	
        	// Top layer is grass path
        	for (int u=1; u<=5; u++) {for (int w=1; w<=5; w++) {
        		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, -1, w, structureBB); // Foundation
        		this.clearCurrentPositionBlocksUpwards(world, u, 1, w, structureBB); // Clear above
        		StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(u, w), this.getYWithOffset(0), this.getZWithOffset(u, w)); // Path
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
                    	StructureVillageVN.setPathSpecificBlock(world, this, 0, this.getXWithOffset(uw[0], uw[1]), k, this.getZWithOffset(uw[0], uw[1]));
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
        	if (GeneralConfig.decorateVillageCenter)
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
    		
    		
    		
			// Banner
    		if (GeneralConfig.decorateVillageCenter)
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
            
            return true;
        }
    }
    
    
	
	/**
	 * Returns a list of blocks and coordinates used to construct a decor piece
	 */
    public static ArrayList<BlueprintData> getRandomSnowyDecorBlueprint(StartVN startVN, int coordBaseMode, Random random)
	{
		int decorCount = 3;
		return getSnowyDecorBlueprint(random.nextInt(decorCount), startVN, coordBaseMode, random);
	}
	public static ArrayList<BlueprintData> getSnowyDecorBlueprint(int decorType, StartVN startVN, int coordBaseMode, Random random)
	{
		ArrayList<BlueprintData> blueprint = new ArrayList(); // The blueprint to export
		
		
		// Generate per-material blocks
		
		Object[] blockObject;
    	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.fence, 0, startVN.materialType, startVN.biome); Block biomeFenceBlock = (Block)blockObject[0];
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
