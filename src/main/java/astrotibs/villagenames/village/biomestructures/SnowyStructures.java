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
		public void buildComponent(StructureComponent start, List components, Random random)
		{
			LogHelper.info("coordBaseMode: " + this.coordBaseMode);
			// Southward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (0), this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + (0), 1, this.getComponentType());
			// Northward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (0), this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			StructureVillageVN.getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + (0), 3, this.getComponentType());
		}
		
		/*
		 * Construct the structure
		 */
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.grass, 0, this); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.dirt, 0, this); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 0, this); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 4, this); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1]; // Toward you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.log, 8, this); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1]; // Perpendicular to you
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.standing_sign, 0, this); Block biomeStandingSignBlock = (Block)blockObject[0];
        	blockObject = StructureVillageVN.getBiomeSpecificBlock(Blocks.cobblestone, 0, this); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	
        	// For stripped wood specifically
        	Block biomeStrippedWoodOrLogOrLogVerticBlock = null; int biomeStrippedWoodOrLogOrLogVerticMeta = 0;
        	Block biomeStrippedWoodOrLogOrLogHorAlongBlock = null; int biomeStrippedWoodOrLogOrLogHorAlongMeta = 0;
        	Block biomeStrippedWoodOrLogOrLogHorAcrossBlock = null; int biomeStrippedWoodOrLogOrLogHorAcrossMeta = 0;
        	
        	// First, try to get UTD's Stripped Log. If it exists, we can use meta 12 to turn it into Stripped Wood.
        	Block tryStrippedWood = Block.getBlockFromName(ModObjects.strippedLogOakUTD);
        	if (tryStrippedWood != null)
        	{
        		blockObject = StructureVillageVN.getBiomeSpecificBlock(tryStrippedWood, 12, this);
        		// Set the blocks as stripped logs and the metas as 12
        		biomeStrippedWoodOrLogOrLogVerticBlock = biomeStrippedWoodOrLogOrLogHorAlongBlock = biomeStrippedWoodOrLogOrLogHorAcrossBlock = (Block)blockObject[0];
        		biomeStrippedWoodOrLogOrLogVerticMeta = (Integer)blockObject[1];
        		biomeStrippedWoodOrLogOrLogHorAlongMeta = biomeStrippedWoodOrLogOrLogVerticMeta + 8 + (this.coordBaseMode%2==0 ? 4 : 0);
        		biomeStrippedWoodOrLogOrLogHorAcrossMeta = biomeStrippedWoodOrLogOrLogVerticMeta + 4 - (this.coordBaseMode%2==0 ? 4 : 0);
        	}
        	else
        	{
        		// Next, try to see if you can use a mod that has just stripped logs, but not stripped wood (EF)
        		Block tryStrippedLogs = Block.getBlockFromName(ModObjects.strippedLog1EF);
        		
        		if (tryStrippedLogs != null)
            	{
        			blockObject = StructureVillageVN.getBiomeSpecificBlock(tryStrippedLogs, 0, this);
        			biomeStrippedWoodOrLogOrLogVerticBlock = biomeStrippedWoodOrLogOrLogHorAlongBlock = biomeStrippedWoodOrLogOrLogHorAcrossBlock = (Block)blockObject[0];
        			biomeStrippedWoodOrLogOrLogVerticMeta = (Integer)blockObject[1];
        			biomeStrippedWoodOrLogOrLogHorAlongMeta = biomeStrippedWoodOrLogOrLogVerticMeta + 8 + (this.coordBaseMode%2==0 ? 4 : 0);
        			biomeStrippedWoodOrLogOrLogHorAcrossMeta = biomeStrippedWoodOrLogOrLogVerticMeta + 4 - (this.coordBaseMode%2==0 ? 4 : 0);
            	}
        		else
        		{
        			// If those two fail, return the vanilla logs
            		biomeStrippedWoodOrLogOrLogVerticBlock = biomeLogVertBlock;
            		biomeStrippedWoodOrLogOrLogHorAlongBlock = biomeLogHorAlongBlock;
            		biomeStrippedWoodOrLogOrLogHorAcrossBlock = biomeLogHorAcrossBlock;
            		biomeStrippedWoodOrLogOrLogVerticMeta = biomeLogVertMeta;
            		biomeStrippedWoodOrLogOrLogHorAlongMeta = biomeLogHorAlongMeta + (this.coordBaseMode%2==0 ? 4 : 0);
            		biomeStrippedWoodOrLogOrLogHorAcrossMeta = biomeLogHorAcrossMeta - (this.coordBaseMode%2==0 ? 4 : 0);
        		}
        	}
        	
        	
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
        			this.getXWithOffset(2, 5),
        			this.getYWithOffset(2),
        			this.getZWithOffset(2, 5));
        	int townColor = villageNBTtag.getInteger("townColor");
        	int townColor2 = villageNBTtag.getInteger("townColor2");
        	
        	// Top layer is grass path
        	for (int u=0; u<=11; u++) {for (int w=0; w<=7; w++) {
        		this.func_151554_b(world, biomeDirtBlock, biomeDirtMeta, u, -1, w, structureBB); // Foundation
        		// TODO - change StructureVillageVN.setPathSpecificBlock for all starters that NEED to be at that ground level
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
        		testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
        		if (testForBanner!=null)
    			{
                    int bannerXBB = 10;
        			int bannerZBB = 2;
        			int bannerYBB = 1;
        			
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
        			{3, 1, 3, -1, 0},
        			{10, 1, 4, -1, 0},
        			{8, 1, 4, -1, 0},
        			})
        		{
        			EntityVillager entityvillager = new EntityVillager(world);
        			
        			// Nitwits more often than not
        			if (GeneralConfig.enableNitwit && random.nextInt(3)==0) {entityvillager.setProfession(5);}
        			else {entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], -random.nextInt(24001));}
        			
        			// TODO - fix villager heights
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
