package astrotibs.villagenames.village.biomestructures;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import astrotibs.villagenames.banner.TileEntityBanner;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.config.village.VillageGeneratorConfigHandler;
import astrotibs.villagenames.handler.ChestLootHandler;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.name.NameGenerator;
import astrotibs.villagenames.utility.BlockPos;
import astrotibs.villagenames.utility.FunctionsVN;
import astrotibs.villagenames.utility.FunctionsVN.MaterialType;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.Reference;
import astrotibs.villagenames.village.StructureVillageVN;
import astrotibs.villagenames.village.StructureVillageVN.StartVN;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.common.ChestGenHooks;

public class SwampStructures
{
	// -------------------- //
    // --- Start Pieces --- //
	// -------------------- //
	
	// --- Swamp Willow --- //
	// designed by AstroTibs
    
    public static class SwampWillow extends StartVN
    {
        // Make foundation with blanks as empty air, F as foundation spaces, and P as path
        private static final String[] foundationPattern = new String[]{
            	"F PPPPP F",
            	" PPFFFPP ",
            	"PPFFFFFPP",
            	"PFFFFFFFP",
            	"PFFFFFFFP",
            	"PFFFFFFFP",
            	"PPFFFFFPP",
            	" PPFFFPP ",
            	"F PPPPP F",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 13;
    	// Values for lining things up
    	public static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1 + 2 + 4 + 8; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
	    public SwampWillow() {}
		
		public SwampWillow(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, float villageSize)
		{
		    super(chunkManager, componentType, random, posX, posZ, components, villageSize);
    		
		    // Establish orientation and bounding box
            this.coordBaseMode = random.nextInt(4);
            switch (this.coordBaseMode)
            {
	            case 0: // North
	            case 2: // South
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + STRUCTURE_WIDTH-1, 64 + STRUCTURE_HEIGHT-1, posZ + STRUCTURE_DEPTH-1);
                    break;
                default: // 1: East; 3: West
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + STRUCTURE_DEPTH-1, 64 + STRUCTURE_HEIGHT-1, posZ + STRUCTURE_WIDTH-1);
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
    					+ " at x=" + (this.boundingBox.minX+this.boundingBox.maxX)/2 + ", y=" + (this.boundingBox.minY+this.boundingBox.maxY)/2 + ", z=" + (this.boundingBox.minZ+this.boundingBox.maxZ)/2
    					+ " with town center: " + start.getClass().toString().substring(start.getClass().toString().indexOf("$")+1) + " and coordBaseMode: " + this.coordBaseMode
    					);
    		}
    		
			// Northward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 3, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 3, 3, this.getComponentType());
			// Southward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 3, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 3, 1, this.getComponentType());
		}
		
		/*
		 * Construct the structure
		 */
		@Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
			if (this.field_143015_k < 0)
            {
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				// Set the bounding box version as this bounding box but with Y going from 0 to 512
        				new StructureBoundingBox(
        						this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
        						this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
        				true, MEDIAN_BORDERS, this.coordBaseMode);
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.minY - GROUND_LEVEL, 0);
            }
        	
        	Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	// Establish top and filler blocks, substituting Grass and Dirt if they're null
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
        	Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            	this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
            }}
            
        	// Follow the blueprint to set up the starting foundation
        	for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
        		
        		String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
        		if (unitLetter.equals("F"))
        		{
        			// If marked with F: fill with dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
        		else if (unitLetter.equals("P"))
        		{
        			// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
        			StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
        		}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
            }}
        	
            
        	// Generate or otherwise obtain village name and banner and colors
        	BlockPos signpos = new BlockPos(6,2,2);
        	
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world,
        			this.getXWithOffset(signpos.getX(), signpos.getZ()),
        			this.getYWithOffset(signpos.getY()),
        			this.getZWithOffset(signpos.getX(), signpos.getZ()));
        	
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

        	WorldChunkManager chunkManager= world.getWorldChunkManager();
        	int posX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int posZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
            BiomeGenBase biome = chunkManager.getBiomeGenAt(posX, posZ);
			Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
            
			if (this.villageType==null || this.materialType==null)
			{
				try {
	            	String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
	            	if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, posX, posZ);}
	            	else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
	            	}
				catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, posX, posZ);}
				
				try {
	            	String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
	            	if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, posX, posZ);}
	            	else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
	            	}
				catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, posX, posZ);}
				
				try {
	            	String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
	            	if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
	            	else {this.disallowModSubs = false;}
	            	}
				catch (Exception e) {this.disallowModSubs = false;}
			}
        	
        	
        	// Cobblestone
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, materialType, biome, disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
            	{1,0,3, 1,0,5}, 
            	{2,0,2, 2,0,3}, {2,0,5, 2,0,5}, 
            	{3,0,1, 5,0,2}, {3,0,7, 5,0,7}, 
            	{6,0,2, 6,0,3}, {6,0,6, 6,0,6}, 
            	{7,0,3, 7,0,5}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            }
        	
        	
        	// Mossy Cobblestone
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.mossy_cobblestone, 0, materialType, biome, disallowModSubs); Block biomeMossyCobblestoneBlock = (Block)blockObject[0]; int biomeMossyCobblestoneMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
            	{2,0,4, 2,0,4}, {2,0,6, 5,0,6}, {6,0,4, 6,0,5}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, false);
            }
        	
        	
        	// For stripped logs specifically
        	Block biomeLogVertBlock = Blocks.log; int biomeLogVertMeta = 0;
        	Block biomeStrippedLogVerticBlock = biomeLogVertBlock; int biomeStrippedLogVerticMeta = biomeLogVertMeta;
        	// Try to see if stripped logs exist
        	blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{3,0,5, 3,1,5}, {4,0,5, 4,2,5}, {5,0,5, 5,0,5}, 
        		{3,0,4, 3,3,4}, {4,0,4, 4,9,4}, {5,0,4, 5,2,4}, 
        		{3,0,3, 3,1,3},                 {5,0,3, 5,1,3}, 
        		// Everything above the base
        		{4,10,5, 4,10,5}, {3,11,4, 3,11,4}, 
        		{6,7,4, 6,8,4}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
            }
        	
        	
        	// Stripped Wood
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
            for(int[] uuvvww : new int[][]{
            	{3,2,5}, {4,3,5}, {5,1,5}, 
            	{3,4,4},          {5,3,4}, 
            	{3,2,3}, {4,0,3}, {4,2,3}, {4,3,3}, {5,2,3}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeStrippedWoodOrLogOrLogVerticBlock, biomeStrippedWoodOrLogOrLogVerticMeta, uuvvww[0], uuvvww[1], uuvvww[2], structureBB);	
            }
            
            
        	// Stripped Log (Across)
        	Block biomeLogHorAcrossBlock = Blocks.log; int biomeLogHorAcrossMeta = 4+(this.coordBaseMode%2!=0? 4:0);
        	Block biomeStrippedLogHorizAcrossBlock = biomeLogHorAcrossBlock; int biomeStrippedLogHorizAcrossMeta = biomeLogHorAcrossMeta;
        	// Try to see if stripped logs exist
        	blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 1+(this.coordBaseMode%2!=0? 1:0)); biomeStrippedLogHorizAcrossBlock = (Block)blockObject[0]; biomeStrippedLogHorizAcrossMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	{2,7,4, 3,7,4}, 
            	{5,6,4, 5,6,4}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogHorizAcrossBlock, biomeStrippedLogHorizAcrossMeta, biomeStrippedLogHorizAcrossBlock, biomeStrippedLogHorizAcrossMeta, false);	
            }
            
            
            // Oak Leaves
        	for (int[] uuvvww : new int[][]{
        		{1,5,3, 1,7,3}, 
        		{1,7,4, 1,7,4}, {1,11,4, 1,11,4}, 
        		{1,6,5, 1,8,5}, 
        		
        		{2,7,3, 2,7,3}, {2,11,3, 2,11,3}, 
        		{2,8,4, 2,8,4}, {2,10,4, 2,12,4}, 
        		{2,7,5, 2,8,5}, {2,11,5, 2,11,5}, 
        		{2,4,6, 2,7,6}, 
        		
        		{3,4,2, 3,5,2}, {3,8,2, 3,10,2}, 
        		{3,5,3, 3,6,3}, {3,9,3, 3,11,3}, 
        		{3,10,4, 3,10,4}, {3,12,4, 3,12,4}, 
        		{3,6,5, 3,6,5}, {3,10,5, 3,12,5}, 
        		{3,9,6, 3,10,6}, 
        		
        		{4,8,3, 4,9,3}, {4,11,3, 4,11,3}, 
        		{4,10,4, 4,11,4}, 
        		{4,6,5, 4,7,5}, {4,9,5, 4,9,5}, {4,11,5, 4,11,5}, 
        		{4,5,6, 4,11,6}, 
        		
        		{5,5,2, 5,9,2}, 
        		{5,6,3, 5,6,3}, {5,9,3, 5,10,3}, 
        		{5,9,4, 5,9,4}, 
        		{5,6,5, 5,6,5}, {5,10,5, 5,11,5}, 
        		{5,8,6, 5,10,6}, 
        		{5,4,7, 5,9,7}, 
        		
        		{6,3,1, 6,7,1}, 
        		{6,6,2, 6,9,2}, 
        		{6,4,3, 6,10,3}, 
        		{6,6,4, 6,6,4}, {6,9,4, 6,9,4}, 
        		{6,3,5, 6,7,5}, 
        		
        		{7,7,2, 7,8,2}, 
        		{7,2,3, 7,9,3}, 
        		{7,5,4, 7,7,4}, 
        		{7,7,5, 7,7,5}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.leaves, 4,
        				Blocks.leaves, 4, 
        				false);
            }
            
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward, 3:leftward
            		// Left side
        			{3,5,4, 3}, {3,6,4, 3}, {3,8,4, 3}, {3,9,4, 3}, 
        			// Back side
        			{4,4,5, 0}, {4,5,5, 0}, {4,8,5, 0}, {6,8,5, 0}, 
        			// Right side
        			{5,5,4, 1}, {5,7,4, 1}, {5,8,4, 1}, {7,8,4, 1}, 
        			// Front side
        			{4,4,3, 2}, {4,5,3, 2}, {4,6,3, 2}, {4,7,3, 2}, {6,4,2, 2}, {6,5,2, 2}, 
            		})
                {
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);	
        			}
                }
        	}
            
        	
        	
            // Decor
            int[][] decorUVW = new int[][]{
            	{0,1,0}, 
            	{8,1,0}, 
            	{0,1,0}, 
            	{8,1,0}, 
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
            	
            	// Generate decor
            	ArrayList<BlueprintData> decorBlueprint = StructureVillageVN.getRandomDecorBlueprint(this.villageType, this.materialType, this.disallowModSubs, this.biome, this.coordBaseMode, randomFromXYZ, VillageGeneratorConfigHandler.allowTaigaTroughs && !VillageGeneratorConfigHandler.restrictTaigaTroughs);
            	
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
        	
        	
            // Sign
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.standing_sign, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStandingSignBlock = (Block)blockObject[0];
            if (GeneralConfig.nameSign)
            {
            	int signU = 3;
    			int signV = 1;
    			int signW = 2;
    			int signO = 8;
                int signX = this.getXWithOffset(signU, signW);
                int signY = this.getYWithOffset(signV);
                int signZ = this.getZWithOffset(signU, signW);
                boolean hanging=false;
                
        		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);

    			world.setBlock(signX, signY, signZ, biomeStandingSignBlock, StructureVillageVN.getSignRotationMeta(signO, this.coordBaseMode, hanging), 2); // 2 is "send change to clients without block update notification"
        		world.setTileEntity(signX, signY, signZ, signContents);
            }
        	
    		
			// Banner    		
    		if (GeneralConfig.villageBanners)
    		{
    			Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
    			
        		if (testForBanner!=null)
    			{
                    int bannerU = 4;
        			int bannerV = 3;
        			int bannerW = 2;
        			int bannerO = 2; // Facing toward you
        			boolean hanging=true;
        			
        			int bannerX = this.getXWithOffset(bannerU, bannerW);
        			int bannerY = this.getYWithOffset(bannerV);
                    int bannerZ = this.getZWithOffset(bannerU, bannerW);
                    
                	// Set the banner and its orientation
    				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
    				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(bannerO, this.coordBaseMode, hanging), 2);
    				
    				// Set the tile entity
    				TileEntity tilebanner = new TileEntityBanner();
    				NBTTagCompound modifystanding = new NBTTagCompound();
    				tilebanner.writeToNBT(modifystanding);
    				modifystanding.setBoolean("IsStanding", !hanging);
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
            	
            	if (VillageGeneratorConfigHandler.spawnVillagersInTownCenters)
            	{
	        		for (int[] ia : new int[][]{
	        			{1, 1, 3, -1, 0}, 
	        			{2, 1, 8, -1, 0}, 
	        			{7, 1, 7, -1, 0}, 
	        			})
	        		{
	        			EntityVillager entityvillager = new EntityVillager(world);
	        			
	        			// Nitwits more often than not
	        			if (GeneralConfig.enableNitwit && random.nextInt(3)==0) {entityvillager.setProfession(5);}
	        			else {entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], -12000-random.nextInt(12001));}
	        			
	        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)this.getYWithOffset(ia[1]) + 0.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
	                    		random.nextFloat()*360F, 0.0F);
	                    world.spawnEntityInWorld(entityvillager);
	        		}
            	}
            }
            
            // Clean items
            if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
            return true;
        }
    }
    
    
    
	// --- Swamp Statue --- //
	// designed by AstroTibs
    
    public static class SwampStatue extends StartVN
    {
        // Make foundation with blanks as empty air, F as foundation spaces, and P as path
        private static final String[] foundationPattern = new String[]{
            	" F  PFF   ",
            	"    FPP  F",
            	"   PPPP   ",
            	"F PFFFFPF ",
            	"PPPFFFFPP ",
            	"PPPFFFFFPP",
            	"PPFFFFFFPP",
            	"  FFPPPPPP",
            	"F FFFPPF  ",
            	"    PPP  F",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 8;
    	// Values for lining things up
    	public static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1 + 2 + 4 + 8; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
	    public SwampStatue() {}
		
		public SwampStatue(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, float villageSize)
		{
		    super(chunkManager, componentType, random, posX, posZ, components, villageSize);
    		
		    // Establish orientation and bounding box
            this.coordBaseMode = random.nextInt(4);
            switch (this.coordBaseMode)
            {
	            case 0: // North
	            case 2: // South
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + STRUCTURE_WIDTH-1, 64 + STRUCTURE_HEIGHT-1, posZ + STRUCTURE_DEPTH-1);
                    break;
                default: // 1: East; 3: West
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + STRUCTURE_DEPTH-1, 64 + STRUCTURE_HEIGHT-1, posZ + STRUCTURE_WIDTH-1);
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
    					+ " at x=" + (this.boundingBox.minX+this.boundingBox.maxX)/2 + ", y=" + (this.boundingBox.minY+this.boundingBox.maxY)/2 + ", z=" + (this.boundingBox.minZ+this.boundingBox.maxZ)/2
    					+ " with town center: " + start.getClass().toString().substring(start.getClass().toString().indexOf("$")+1) + " and coordBaseMode: " + this.coordBaseMode
    					);
    		}

			// Northward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + new int[]{4,4,4,3}[this.coordBaseMode], this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + new int[]{2,4,5,4}[this.coordBaseMode], 3, this.getComponentType());
			// Southward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + new int[]{4,5,4,2}[this.coordBaseMode], this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + new int[]{3,4,4,4}[this.coordBaseMode], 1, this.getComponentType());
		}
		
		/*
		 * Construct the structure
		 */
		@Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
			if (this.field_143015_k < 0)
            {
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				// Set the bounding box version as this bounding box but with Y going from 0 to 512
        				new StructureBoundingBox(
        						this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
        						this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
        				true, MEDIAN_BORDERS, this.coordBaseMode);
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.minY - GROUND_LEVEL, 0);
            }
        	
        	Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	// Establish top and filler blocks, substituting Grass and Dirt if they're null
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
        	Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            	this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
            }}
            
        	// Follow the blueprint to set up the starting foundation
        	for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
        		
        		String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
        		if (unitLetter.equals("F"))
        		{
        			// If marked with F: fill with dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
        		else if (unitLetter.equals("P"))
        		{
        			// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
        			StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
        		}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
            }}
        	
            
        	// Generate or otherwise obtain village name and banner and colors
        	BlockPos signpos = new BlockPos(6,2,2);
        	
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world,
        			this.getXWithOffset(signpos.getX(), signpos.getZ()),
        			this.getYWithOffset(signpos.getY()),
        			this.getZWithOffset(signpos.getX(), signpos.getZ()));
        	
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

        	WorldChunkManager chunkManager= world.getWorldChunkManager();
        	int posX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int posZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
            BiomeGenBase biome = chunkManager.getBiomeGenAt(posX, posZ);
			Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
            
			if (this.villageType==null || this.materialType==null)
			{
				try {
	            	String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
	            	if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, posX, posZ);}
	            	else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
	            	}
				catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, posX, posZ);}
				
				try {
	            	String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
	            	if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, posX, posZ);}
	            	else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
	            	}
				catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, posX, posZ);}
				
				try {
	            	String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
	            	if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
	            	else {this.disallowModSubs = false;}
	            	}
				catch (Exception e) {this.disallowModSubs = false;}
			}
        	
        	
        	// Dark Prismarine Base
			blockObject = ModObjects.chooseModDarkPrismarineObject(); Block darkPrismarineBlock; int darkPrismarineMeta;
			if (blockObject==null) {darkPrismarineBlock = Blocks.stonebrick; darkPrismarineMeta = 0;}
			else {darkPrismarineBlock = (Block)blockObject[0]; darkPrismarineMeta = (Integer)blockObject[1];}
        	for (int[] uuvvww : new int[][]{
            	{3,1,3, 6,2,6}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], darkPrismarineBlock, darkPrismarineMeta, darkPrismarineBlock, darkPrismarineMeta, false);
            }
        	
        	
        	// --- Statue proper --- //
            
        	// Try to establish prismarine blocks used for the statue. If any kind doesn't exist, use default for all.
        	Block biomePrismarineStairsBlock;
        	Block biomePrismarineBlock; int biomePrismarineMeta;
        	Block biomePrismarineSlabUpperBlock; int biomePrismarineSlabUpperMeta;
        	Block biomePrismarineSlabLowerBlock; int biomePrismarineSlabLowerMeta;
        	
        	Block biomePrismarineWallBlock;
        	
    		boolean useOnlyStone = false; // This flag will indicate to use stone instead of diorite, should we need to.
        	while (true)
        	{
            	// Prismarine Stairs
            	if (useOnlyStone) {biomePrismarineStairsBlock = Blocks.stone_stairs;} // Set to cobblestone stairs
            	else
            	{
            		biomePrismarineStairsBlock = ModObjects.chooseModPrismarineStairsBlock();
            		if (biomePrismarineStairsBlock==null) {useOnlyStone=true; continue;} // Trigger flag and reset
            	}
            	biomePrismarineStairsBlock = (Block) StructureVillageVN.getBiomeSpecificBlockObject(biomePrismarineStairsBlock, 0, this.materialType, this.biome, this.disallowModSubs)[0];
            	
            	
            	// Prismarine blocks
            	if (useOnlyStone) {blockObject = new Object[]{Blocks.cobblestone, 0};} // Set to cobblestone
            	else
            	{
            		blockObject = ModObjects.chooseModPrismarineBlockObject();
                	if (blockObject==null) {useOnlyStone=true; continue;} // Trigger flag and reset
            	}
            	blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs); biomePrismarineBlock = (Block)blockObject[0]; biomePrismarineMeta = (Integer)blockObject[1];
            	
            	
            	// Prismarine Slabs lower
            	if (useOnlyStone) {blockObject = new Object[]{Blocks.stone_slab, 3};} // Set to cobblestone slab
            	else
            	{
            		blockObject = ModObjects.chooseModPrismarineSlab(false); 
                	if (blockObject==null) {useOnlyStone=true; continue;} // Trigger flag and reset
            	}
            	blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs); biomePrismarineSlabLowerBlock = (Block)blockObject[0]; biomePrismarineSlabLowerMeta = (Integer)blockObject[1];
            	
                
            	// Prismarine Slabs upper
            	if (useOnlyStone) {blockObject = new Object[]{Blocks.stone_slab, 11};} // Set to cobblestone slab
            	else
            	{
            		blockObject = ModObjects.chooseModPrismarineSlab(true); 
                	if (blockObject==null) {useOnlyStone=true; continue;} // Trigger flag and reset
            	}
            	blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs); biomePrismarineSlabUpperBlock = (Block)blockObject[0]; biomePrismarineSlabUpperMeta = (Integer)blockObject[1];
            	
            	
            	// Prismarine wall
            	if (useOnlyStone) {blockObject = new Object[]{Blocks.cobblestone_wall, 0};} // Set to cobblestone wall
            	else
            	{
            		biomePrismarineWallBlock = ModObjects.chooseModPrismarineWallBlock();
                	if (biomePrismarineWallBlock==null) {useOnlyStone=true; continue;} // Trigger flag and reset
            	}
            	biomePrismarineWallBlock = (Block) StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs)[0];
            	
            	
            	// If you make it here, all blocks are either prismarine-type or stone-type.
            	break;
            }
        	
        	// Now, construct the statue with either all diorite blocks, or all stone
        	
        	for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
        		// Feet
        		{4,3,3, 3}, {5,3,3, 3}, 
        		// Butt
        		{4,3,5, 3+4}, {5,3,5, 3+4}, 
        		{4,3,6, 2+4}, {5,3,6, 2+4},
        		// Hands
        		{3,5,4, 3}, {6,5,4, 3}, 
        		// Lips
        		{4,6,3, 3}, {5,6,3, 3},
        		// Neck
        		{4,6,6, 2}, {5,6,6, 2},
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomePrismarineStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
            }
        	for (int[] uuvvww : new int[][]{
        		// Knees
        		{4,4,4, 5,4,4}, 
        		// Back
        		{4,4,6, 5,5,6}, 
        		// Head
        		{4,6,4, 5,7,5}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomePrismarineBlock, biomePrismarineMeta,
        				biomePrismarineBlock, biomePrismarineMeta, 
        				false);	
            }
        	for (int[] uuvvww : new int[][]{
        		// Thighs
        		{4,4,5, 5,4,5}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomePrismarineSlabLowerBlock, biomePrismarineSlabLowerMeta,
        				biomePrismarineSlabLowerBlock, biomePrismarineSlabLowerMeta, 
        				false);	
            }
        	for (int[] uuvvww : new int[][]{
        		// Meaty fists
        		{3,4,4, 3,4,4}, {6,4,4, 6,4,4}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomePrismarineSlabUpperBlock, biomePrismarineSlabUpperMeta,
        				biomePrismarineSlabUpperBlock, biomePrismarineSlabUpperMeta, 
        				false);	
            }
        	for (int[] uuvvww : new int[][]{
        		// Right arm
        		{3,5,5, 3,5,6}, 
        		// Left arm
        		{6,5,5, 6,5,6}, 
        		// Wings
        		{4,5,7, 5,6,7}, 
        		// Jowl
        		{4,5,4, 5,5,4}, 
        		// Calves
        		{4,3,4, 5,3,4}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomePrismarineWallBlock, 0,
        				biomePrismarineWallBlock, 0, 
        				false);	
            }
        	
        	
            // Iron bars
            for(int[] uuvvww : new int[][]{
            	{4,5,3, 5,5,3}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.iron_bars, 0, Blocks.iron_bars, 0, false);
            }
        	
        	
        	// Polished Blackstone Buttons
        	Block polishedBlackstoneButtonBlock = ModObjects.chooseModPolishedBlackstoneButton(); 
        	if (polishedBlackstoneButtonBlock==null) {polishedBlackstoneButtonBlock = Blocks.stone_button;} // Stone button if not found
        	for(int[] uuvvwwo : new int[][]{
        		// Rim
        		// Front
        		{3,1,2, 6,1,2, 2},  
        		// Left
        		{2,1,3, 2,1,6, 3}, 
        		// Right
        		{7,1,3, 7,1,6, 1}, 
        		// Back
        		{3,1,7, 6,1,7, 0}, 
        		
        		// Eyes
        		{4,7,3, 5,7,3, 2}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], uuvvwwo[3], uuvvwwo[4], uuvvwwo[5], polishedBlackstoneButtonBlock, StructureVillageVN.chooseButtonMeta(uuvvwwo[6], this.coordBaseMode), polishedBlackstoneButtonBlock, StructureVillageVN.chooseButtonMeta(uuvvwwo[6], this.coordBaseMode), false);	
            }
        	
        	
        	// Terracotta
        	for(int[] uuvvww : new int[][]{
            	{2,0,1}, {7,0,1}, {0,0,6}, {8,0,6}, {5,0,9}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world,
            			Blocks.stained_hardened_clay,
            			(GeneralConfig.useVillageColors ? this.townColor : 15), // Black
            			uuvvww[0], uuvvww[1], uuvvww[2], structureBB);	
            }
        	
        	            
            // Fences
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
            for (int[] uuvvww : new int[][]{
            	// Staff
            	{2,1,1}, {7,1,1}, {0,1,6}, {8,1,6}, {5,1,9}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
            
        	// Torches
            for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	{0,2,6, -1}, {8,2,6, -1}, {5,2,9, -1}, 
        	})
        	{
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
        	}
        	
        	
            // Decor
            int[][] decorUVW = new int[][]{
            	{0,1,2}, 
            	{9,1,0}, 
            	{1,1,9}, 
            	{9,1,8}, 
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
            	
            	// Generate decor
            	ArrayList<BlueprintData> decorBlueprint = StructureVillageVN.getRandomDecorBlueprint(this.villageType, this.materialType, this.disallowModSubs, this.biome, this.coordBaseMode, randomFromXYZ, VillageGeneratorConfigHandler.allowTaigaTroughs && !VillageGeneratorConfigHandler.restrictTaigaTroughs);
            	
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
        	
        	
            // Sign
        	int signU = 2;
			int signV = 2;
			int signW = 1;
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.standing_sign, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStandingSignBlock = (Block)blockObject[0];
            if (GeneralConfig.nameSign)
            {

    			int signO = 4;
                int signX = this.getXWithOffset(signU, signW);
                int signY = this.getYWithOffset(signV);
                int signZ = this.getZWithOffset(signU, signW);
                boolean hanging=false;
                
        		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);

    			world.setBlock(signX, signY, signZ, biomeStandingSignBlock, StructureVillageVN.getSignRotationMeta(signO, this.coordBaseMode, hanging), 2); // 2 is "send change to clients without block update notification"
        		world.setTileEntity(signX, signY, signZ, signContents);
            }
            else {this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(-1, this.coordBaseMode), signU, signV, signW, structureBB);} // Substitute a torch if signs are disabled
        	
    		
			// Banner    		
            int bannerU = 7;
			int bannerV = 2;
			int bannerW = 1;
    		if (GeneralConfig.villageBanners)
    		{
    			Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
    			
        		if (testForBanner!=null)
    			{
        			int bannerO = 12; // Facing toward you
        			boolean hanging=false;
        			
        			int bannerX = this.getXWithOffset(bannerU, bannerW);
        			int bannerY = this.getYWithOffset(bannerV);
                    int bannerZ = this.getZWithOffset(bannerU, bannerW);
                    
                	// Set the banner and its orientation
    				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
    				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(bannerO, this.coordBaseMode, hanging), 2);
    				
    				// Set the tile entity
    				TileEntity tilebanner = new TileEntityBanner();
    				NBTTagCompound modifystanding = new NBTTagCompound();
    				tilebanner.writeToNBT(modifystanding);
    				modifystanding.setBoolean("IsStanding", !hanging);
    				tilebanner.readFromNBT(modifystanding);
    				ItemStack villageBanner = ModObjects.chooseModBannerItem();
    				villageBanner.setTagInfo("BlockEntityTag", villageNBTtag.getCompoundTag("BlockEntityTag"));
    				
        			((TileEntityBanner) tilebanner).setItemValues(villageBanner);
            		
            		world.setTileEntity(bannerX, bannerY, bannerZ, tilebanner);
    			}
        		else {this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(-1, this.coordBaseMode), bannerU, bannerV, bannerW, structureBB);} // Substitute a torch if banners are unavailable
    		}
    		else {this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(-1, this.coordBaseMode), bannerU, bannerV, bannerW, structureBB);} // Substitute a torch if banners are disabled
    		
    		// Villagers
            if (!this.villagersGenerated)
            {
            	this.villagersGenerated=true;
            	
            	if (VillageGeneratorConfigHandler.spawnVillagersInTownCenters)
            	{
	        		for (int[] ia : new int[][]{
	        			{6, 1, 1, -1, 0}, 
	        			{8, 1, 4, -1, 0}, 
	        			{4, 1, 8, -1, 0}, 
	        			})
	        		{
	        			EntityVillager entityvillager = new EntityVillager(world);
	        			
	        			// Nitwits more often than not
	        			if (GeneralConfig.enableNitwit && random.nextInt(3)==0) {entityvillager.setProfession(5);}
	        			else {entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], -12000-random.nextInt(12001));}
	        			
	        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)this.getYWithOffset(ia[1]) + 0.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
	                    		random.nextFloat()*360F, 0.0F);
	                    world.spawnEntityInWorld(entityvillager);
	        		}
            	}
            }
            
            // Clean items
            if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
            return true;
        }
    }
    
    
    
	// --- Swamp Pavilion --- //
	// designed by AstroTibs
    
    public static class SwampPavilion extends StartVN
    {
        // Make foundation with blanks as empty air, F as foundation spaces, and P as path
        private static final String[] foundationPattern = new String[]{
            	"   PPF  ",
            	" PPPPPP ",
            	"PPFPPFP ",
            	"PFPFPPPP",
            	"PPPPPFFP",
            	" PFPPFPP",
            	" PPPPPP ",
            	"  FPP   ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 6;
    	// Values for lining things up
    	public static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1 + 2 + 4 + 8; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
	    public SwampPavilion() {}
		
		public SwampPavilion(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, float villageSize)
		{
		    super(chunkManager, componentType, random, posX, posZ, components, villageSize);
    		
		    // Establish orientation and bounding box
            this.coordBaseMode = random.nextInt(4);
            switch (this.coordBaseMode)
            {
	            case 0: // North
	            case 2: // South
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + STRUCTURE_WIDTH-1, 64 + STRUCTURE_HEIGHT-1, posZ + STRUCTURE_DEPTH-1);
                    break;
                default: // 1: East; 3: West
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + STRUCTURE_DEPTH-1, 64 + STRUCTURE_HEIGHT-1, posZ + STRUCTURE_WIDTH-1);
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
    					+ " at x=" + (this.boundingBox.minX+this.boundingBox.maxX)/2 + ", y=" + (this.boundingBox.minY+this.boundingBox.maxY)/2 + ", z=" + (this.boundingBox.minZ+this.boundingBox.maxZ)/2
    					+ " with town center: " + start.getClass().toString().substring(start.getClass().toString().indexOf("$")+1) + " and coordBaseMode: " + this.coordBaseMode
    					);
    		}

			// Northward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode<2 ? 2:3), this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode<2 ? 2:3), 3, this.getComponentType());
			// Southward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode<2 ? 3:2), this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode<2 ? 3:2), 1, this.getComponentType());
		}
		
		/*
		 * Construct the structure
		 */
		@Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
			if (this.field_143015_k < 0)
            {
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				// Set the bounding box version as this bounding box but with Y going from 0 to 512
        				new StructureBoundingBox(
        						this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
        						this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
        				true, MEDIAN_BORDERS, this.coordBaseMode);
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.minY - GROUND_LEVEL, 0);
            }
        	
        	Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	// Establish top and filler blocks, substituting Grass and Dirt if they're null
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
        	Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            	this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
            }}
            
        	// Follow the blueprint to set up the starting foundation
        	for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
        		
        		String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
        		if (unitLetter.equals("F"))
        		{
        			// If marked with F: fill with dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
        		else if (unitLetter.equals("P"))
        		{
        			// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
        			StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
        		}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
            }}
        	
            
        	// Generate or otherwise obtain village name and banner and colors
        	BlockPos signpos = new BlockPos(6,2,2);
        	
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world,
        			this.getXWithOffset(signpos.getX(), signpos.getZ()),
        			this.getYWithOffset(signpos.getY()),
        			this.getZWithOffset(signpos.getX(), signpos.getZ()));
        	
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

        	WorldChunkManager chunkManager= world.getWorldChunkManager();
        	int posX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int posZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
            BiomeGenBase biome = chunkManager.getBiomeGenAt(posX, posZ);
			Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
            
			if (this.villageType==null || this.materialType==null)
			{
				try {
	            	String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
	            	if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, posX, posZ);}
	            	else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
	            	}
				catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, posX, posZ);}
				
				try {
	            	String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
	            	if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, posX, posZ);}
	            	else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
	            	}
				catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, posX, posZ);}
				
				try {
	            	String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
	            	if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
	            	else {this.disallowModSubs = false;}
	            	}
				catch (Exception e) {this.disallowModSubs = false;}
			}
			
			
        	// For stripped logs specifically
        	Block biomeLogVertBlock = Blocks.log; int biomeLogVertMeta = 0;
        	Block biomeStrippedLogVerticBlock = biomeLogVertBlock; int biomeStrippedLogVerticMeta = biomeLogVertMeta;
        	// Try to see if stripped logs exist
        	blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{2,1,5, 2,3,5}, {5,1,5, 5,3,5}, 
        		{2,1,2, 2,3,2}, {5,1,2, 5,3,2}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
            }
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	{1,4,1, 6,4,6}, {2,5,2, 5,5,5}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	{1,3,6}, {6,3,6}, 
            	{1,3,1}, {6,3,1}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
            // Glazed terracotta
        	Object[] tryGlazedTerracotta;
        	for (int[] uvwoc : new int[][]{ // u,v,w, orientation, dye color
        		// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
        		{3,4,3, 0, GeneralConfig.useVillageColors ? this.townColor3 : 10}, // Purple
        		{3,4,4, 1, GeneralConfig.useVillageColors ? this.townColor3 : 10}, // Purple
        		{4,4,3, 3, GeneralConfig.useVillageColors ? this.townColor3 : 10}, // Purple
        		{4,4,4, 2, GeneralConfig.useVillageColors ? this.townColor3 : 10}, // Purple
           		})
        	{
        		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(uvwoc[4], StructureVillageVN.chooseGlazedTerracottaMeta(uvwoc[3], this.coordBaseMode));
        		if (tryGlazedTerracotta != null)
            	{
        			this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvwoc[0], uvwoc[1], uvwoc[2], structureBB);
            	}
        		else
        		{
        			this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, uvwoc[4], uvwoc[0], uvwoc[1], uvwoc[2], structureBB);
        		}
            }
        	
        	
            // Sign
        	int signU = 2;
			int signV = 3;
			int signW = 1;
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wall_sign, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStandingSignBlock = (Block)blockObject[0];
            if (GeneralConfig.nameSign)
            {

    			int signO = 2;
                int signX = this.getXWithOffset(signU, signW);
                int signY = this.getYWithOffset(signV);
                int signZ = this.getZWithOffset(signU, signW);
                boolean hanging=true;
                
        		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);

    			world.setBlock(signX, signY, signZ, biomeStandingSignBlock, StructureVillageVN.getSignRotationMeta(signO, this.coordBaseMode, hanging), 2); // 2 is "send change to clients without block update notification"
        		world.setTileEntity(signX, signY, signZ, signContents);
            }
            else {this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(-1, this.coordBaseMode), signU, signV, signW, structureBB);} // Substitute a torch if signs are disabled
            
    		
    		// Banner - patterend or solid depending on configs
            Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
			if (testForBanner!=null)
			{
    			for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color
    				// 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    				
    				{2,2,1, 2, 10}, // Purple
    				{5,2,1, 2, 10}, // Purple
    			})
    			{
        			int bannerU = uvwoc[0];
        			int bannerV = uvwoc[1];
        			int bannerW = uvwoc[2];
        			boolean hanging=true;
        			
        			int bannerX = this.getXWithOffset(bannerU, bannerW);
        			int bannerY = this.getYWithOffset(bannerV);
                    int bannerZ = this.getZWithOffset(bannerU, bannerW);
                    
                	// Set the banner and its orientation
    				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
    				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(uvwoc[3], this.coordBaseMode, hanging), 2);
    				
    				// Set the tile entity
    				TileEntity tilebanner = new TileEntityBanner();
    				NBTTagCompound modifystanding = new NBTTagCompound();
    				tilebanner.writeToNBT(modifystanding);
    				modifystanding.setBoolean("IsStanding", !hanging);
    				
    				if (GeneralConfig.useVillageColors)
    				{
    	            	tilebanner.readFromNBT(modifystanding);
        				ItemStack villageBanner = ModObjects.chooseModBannerItem();
        				villageBanner.setTagInfo("BlockEntityTag", villageNBTtag.getCompoundTag("BlockEntityTag"));
        				
            			((TileEntityBanner) tilebanner).setItemValues(villageBanner);
    				}
    				else
    				{
    					modifystanding.setInteger("Base", uvwoc[4]);
        				tilebanner.readFromNBT(modifystanding);
    				}
    				
            		world.setTileEntity(bannerX, bannerY, bannerZ, tilebanner);
    			}
			}
            
    		
    		// Villagers
            if (!this.villagersGenerated)
            {
            	this.villagersGenerated=true;
            	
            	if (VillageGeneratorConfigHandler.spawnVillagersInTownCenters)
            	{
	        		for (int[] ia : new int[][]{
	        			{3, 1, 3, -1, 0}, 
	        			{1, 1, 2, -1, 0}, 
	        			{0, 1, 3, -1, 0}, 
	        			})
	        		{
	        			EntityVillager entityvillager = new EntityVillager(world);
	        			
	        			// Nitwits more often than not
	        			if (GeneralConfig.enableNitwit && random.nextInt(3)==0) {entityvillager.setProfession(5);}
	        			else {entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], -12000-random.nextInt(12001));}
	        			
	        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)this.getYWithOffset(ia[1]) + 0.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
	                    		random.nextFloat()*360F, 0.0F);
	                    world.spawnEntityInWorld(entityvillager);
	        		}
            	}
            }
            
            // Clean items
            if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
            return true;
        }
    }
    
    
    
	// --- Swamp Monolith --- //
	// designed by AstroTibs
    
    public static class SwampMonolith extends StartVN
    {
        // Make foundation with blanks as empty air, F as foundation spaces, and P as path
        private static final String[] foundationPattern = new String[]{
            	"F   PPP    F",
            	"  PPPPPFP   ",
            	" PFPPFPPPP  ",
            	" PPPFFFFPPP ",
            	"PPPFFFFFFPF ",
            	"PPPFFFFFFPPP",
            	"FPPFFFFFFPPP",
            	" PPFFFFFFFPP",
            	" FPPFFFFPPP ",
            	"  PPPPPPPPP ",
            	"   PPPPPPP  ",
            	"F    PPP   F",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 7;
    	// Values for lining things up
    	public static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1 + 2 + 4 + 8; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
	    public SwampMonolith() {}
		
		public SwampMonolith(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, float villageSize)
		{
		    super(chunkManager, componentType, random, posX, posZ, components, villageSize);
    		
		    // Establish orientation and bounding box
            this.coordBaseMode = random.nextInt(4);
            switch (this.coordBaseMode)
            {
	            case 0: // North
	            case 2: // South
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + STRUCTURE_WIDTH-1, 64 + STRUCTURE_HEIGHT-1, posZ + STRUCTURE_DEPTH-1);
                    break;
                default: // 1: East; 3: West
                    this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + STRUCTURE_DEPTH-1, 64 + STRUCTURE_HEIGHT-1, posZ + STRUCTURE_WIDTH-1);
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
    					+ " at x=" + (this.boundingBox.minX+this.boundingBox.maxX)/2 + ", y=" + (this.boundingBox.minY+this.boundingBox.maxY)/2 + ", z=" + (this.boundingBox.minZ+this.boundingBox.maxZ)/2
    					+ " with town center: " + start.getClass().toString().substring(start.getClass().toString().indexOf("$")+1) + " and coordBaseMode: " + this.coordBaseMode
    					);
    		}

			// Northward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + new int[]{5,4,4,5}[this.coordBaseMode], this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + new int[]{4,5,5,4}[this.coordBaseMode], 3, this.getComponentType());
			// Southward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + new int[]{4,5,5,4}[this.coordBaseMode], this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + new int[]{5,4,4,5}[this.coordBaseMode], 1, this.getComponentType());
		}
		
		/*
		 * Construct the structure
		 */
		@Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
			if (this.field_143015_k < 0)
            {
        		this.field_143015_k = StructureVillageVN.getMedianGroundLevel(world,
        				// Set the bounding box version as this bounding box but with Y going from 0 to 512
        				new StructureBoundingBox(
        						this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
        						this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
        				true, MEDIAN_BORDERS, this.coordBaseMode);
        		
                if (this.field_143015_k < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.minY - GROUND_LEVEL, 0);
            }
        	
        	Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	// Establish top and filler blocks, substituting Grass and Dirt if they're null
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
        	Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
            for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
            	this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
            }}
            
        	// Follow the blueprint to set up the starting foundation
        	for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
        		
        		String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
        		if (unitLetter.equals("F"))
        		{
        			// If marked with F: fill with dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
        		else if (unitLetter.equals("P"))
        		{
        			// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
        			StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
        		}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
            }}
        	
            
        	// Generate or otherwise obtain village name and banner and colors
        	BlockPos signpos = new BlockPos(6,2,2);
        	
        	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world,
        			this.getXWithOffset(signpos.getX(), signpos.getZ()),
        			this.getYWithOffset(signpos.getY()),
        			this.getZWithOffset(signpos.getX(), signpos.getZ()));
        	
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

        	WorldChunkManager chunkManager= world.getWorldChunkManager();
        	int posX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int posZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
            BiomeGenBase biome = chunkManager.getBiomeGenAt(posX, posZ);
			Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
            
			if (this.villageType==null || this.materialType==null)
			{
				try {
	            	String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
	            	if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, posX, posZ);}
	            	else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
	            	}
				catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, posX, posZ);}
				
				try {
	            	String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
	            	if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, posX, posZ);}
	            	else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
	            	}
				catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, posX, posZ);}
				
				try {
	            	String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
	            	if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
	            	else {this.disallowModSubs = false;}
	            	}
				catch (Exception e) {this.disallowModSubs = false;}
			}
        	
        	
        	// Dark Prismarine Monolith that becomes green terracotta otherwise
			blockObject = ModObjects.chooseModDarkPrismarineObject(); Block darkPrismarineBlock; int darkPrismarineMeta;
			if (blockObject==null) {darkPrismarineBlock = Blocks.stained_hardened_clay; darkPrismarineMeta = 13;}
			else {darkPrismarineBlock = (Block)blockObject[0]; darkPrismarineMeta = (Integer)blockObject[1];}
        	for (int[] uuvvww : new int[][]{
            	{5,1,5, 6,6,6}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], darkPrismarineBlock, darkPrismarineMeta, darkPrismarineBlock, darkPrismarineMeta, false);
            }
            
            
            // Grass
            for(int[] uuvvww : new int[][]{
            	{4,1,7, 7,1,7}, 
            	{4,1,5, 4,1,6}, {7,1,5, 7,1,6}, 
            	{4,1,4, 7,1,4}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeTopBlock, biomeTopMeta, biomeTopBlock, biomeTopMeta, false);	
            }
            
            
        	// Torches
            for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	{4,2,7, -1}, {7,2,7, -1}, 
            	{4,2,4, -1}, {7,2,4, -1}, 
        	})
        	{
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
        	}
        	
        	
            // Decor
            int[][] decorUVW = new int[][]{
            	{0,1,11}, {11,1,11}, 
            	{0,1,0}, {11,1,0}, 
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
            	
            	// Generate decor
            	ArrayList<BlueprintData> decorBlueprint = StructureVillageVN.getRandomDecorBlueprint(this.villageType, this.materialType, this.disallowModSubs, this.biome, this.coordBaseMode, randomFromXYZ, VillageGeneratorConfigHandler.allowTaigaTroughs && !VillageGeneratorConfigHandler.restrictTaigaTroughs);
            	
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
        	
        	
            // Sign
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wall_sign, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStandingSignBlock = (Block)blockObject[0];
            if (GeneralConfig.nameSign)
            {
            	int signU = 6;
    			int signV = 1;
    			int signW = 3;
    			int signO = 2;
                int signX = this.getXWithOffset(signU, signW);
                int signY = this.getYWithOffset(signV);
                int signZ = this.getZWithOffset(signU, signW);
                boolean hanging=true;
                
        		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);

    			world.setBlock(signX, signY, signZ, biomeStandingSignBlock, StructureVillageVN.getSignRotationMeta(signO, this.coordBaseMode, hanging), 2); // 2 is "send change to clients without block update notification"
        		world.setTileEntity(signX, signY, signZ, signContents);
            }
            
    		
    		// Banners
            if (GeneralConfig.villageBanners)
    		{
            	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, materialType, biome, disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
            	
            	Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
    			if (testForBanner!=null)
    			{
        			for (int[] uvwo : new int[][]{ // u, v, w, orientation
        				// 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        				
        				{4,1,1, 4}, 
        				{8,1,1, 12}, 
        			})
        			{
            			int bannerU = uvwo[0];
            			int bannerV = uvwo[1];
            			int bannerW = uvwo[2];
            			boolean hanging=false;
            			
            			int bannerX = this.getXWithOffset(bannerU, bannerW);
            			int bannerY = this.getYWithOffset(bannerV);
                        int bannerZ = this.getZWithOffset(bannerU, bannerW);
                        
                        // Cobblestone foundation
            			this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, uvwo[0], uvwo[1]-1, uvwo[2], structureBB);
                        
                    	// Set the banner and its orientation
        				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
        				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(uvwo[3], this.coordBaseMode, hanging), 2);
        				
        				// Set the tile entity
        				TileEntity tilebanner = new TileEntityBanner();
        				NBTTagCompound modifystanding = new NBTTagCompound();
        				tilebanner.writeToNBT(modifystanding);
        				modifystanding.setBoolean("IsStanding", !hanging);
        				
    	            	tilebanner.readFromNBT(modifystanding);
        				ItemStack villageBanner = ModObjects.chooseModBannerItem();
        				villageBanner.setTagInfo("BlockEntityTag", villageNBTtag.getCompoundTag("BlockEntityTag"));
        				
            			((TileEntityBanner) tilebanner).setItemValues(villageBanner);
        				
                		world.setTileEntity(bannerX, bannerY, bannerZ, tilebanner);
        			}
    			}
    		}
            
            
    		// Villagers
            if (!this.villagersGenerated)
            {
            	this.villagersGenerated=true;
            	
            	if (VillageGeneratorConfigHandler.spawnVillagersInTownCenters)
            	{
	        		for (int[] ia : new int[][]{
	        			{6, 1, 2, -1, 0}, 
	        			{1, 1, 4, -1, 0}, 
	        			{4, 1, 9, -1, 0}, 
	        			{10, 1, 5, -1, 0}, 
	        			})
	        		{
	        			EntityVillager entityvillager = new EntityVillager(world);
	        			
	        			// Nitwits more often than not
	        			if (GeneralConfig.enableNitwit && random.nextInt(3)==0) {entityvillager.setProfession(5);}
	        			else {entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], -12000-random.nextInt(12001));}
	        			
	        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)this.getYWithOffset(ia[1]) + 0.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
	                    		random.nextFloat()*360F, 0.0F);
	                    world.spawnEntityInWorld(entityvillager);
	        		}
            	}
            }
            
            // Clean items
            if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
            return true;
        }
    }
    
    
    // --- Animal Pen 1 --- //
    // designed by THASSELHOFF
    
    public static class SwampAnimalPen1 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"        ",
    			" F    F ",
    			"        ",
    			" F    F ",
    			"  F     ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 4;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 3;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampAnimalPen1() {}

    	public SwampAnimalPen1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampAnimalPen1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampAnimalPen1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
    		
    		
    		// Stripped logs (Vertical)
        	Block biomeLogVertBlock = Blocks.log; int biomeLogVertMeta = 0;
    		Block biomeStrippedLogVerticBlock = biomeLogVertBlock; int biomeStrippedLogVerticMeta = biomeLogVertMeta;
    		// Try to see if stripped logs exist
    		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
    		for (int[] uuvvww : new int[][]{
        		// Front Awning
        		{1,0,3, 1,1,3}, {6,0,3, 6,1,3}, 
        		{1,0,1, 1,1,1}, {6,0,1, 6,1,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	{1,1,2, 1,1,2}, {2,1,3, 5,1,3}, {6,1,2, 6,1,2}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{2,0,0, 2,0,0}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{2,0,3, 5,0,3}, 
    			{1,0,2, 6,0,2}, 
    			{2,0,1, 5,0,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
    		
    		// Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{1,2,1, 1,2,2}, {2,2,1, 5,2,1}, {6,2,1, 6,2,3}, {2,2,3, 5,2,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.mossy_cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneBlock = (Block)blockObject[0]; int biomeMossyCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{1,2,3, 1,2,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, false);	
    		}
    		
    		
    		// Cobblestone Slab (lower)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabLowerBlock = (Block)blockObject[0]; int biomeCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{0,2,1, 0,2,4}, 
    			{1,2,4, 2,2,4}, {5,2,4, 7,2,4}, 
    			{7,2,0, 7,2,4}, 
    			{2,2,0, 6,2,0}, 
    			{2,3,2, 3,3,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone Slab (lower)
    		Block biomeMossyCobblestoneSlabLowerBlock = biomeCobblestoneSlabLowerBlock; int biomeMossyCobblestoneSlabLowerMeta = biomeCobblestoneSlabLowerMeta;
    		blockObject = ModObjects.chooseModMossyCobblestoneSlabBlock(false);
    		if (blockObject != null)
    		{
    			blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs);
    			biomeMossyCobblestoneSlabLowerBlock = (Block)blockObject[0]; biomeMossyCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		}
    		for(int[] uuvvww : new int[][]{
    			{3,2,4, 4,2,4}, 
    			{4,3,2, 5,3,2}, 
    			{0,2,0, 1,2,0}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Trapdoor (Top Vertical)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapdoor, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{2,1,1, 2}, {3,1,1, 2}, {4,1,1, 2}, {5,1,1, 2}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, true, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		{0,1,4}, {7,1,4}, 
        		{0,1,0}, {7,1,0}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
    		
    		// Entities
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
            	// Chickens
            	for (int[] uvw : new int[][]{
        			{3, 1, 2},
        			{5, 1, 2},
        			})
        		{
    				EntityChicken entityChicken = new EntityChicken(world);
    				IEntityLivingData ientitylivingdata = entityChicken.onSpawnWithEgg(null);
    				entityChicken.setLocationAndAngles(getXWithOffset(uvw[0], uvw[2]) + 0.5D, getYWithOffset(uvw[1]) + 0.5D, getZWithOffset(uvw[0], uvw[2]) + 0.5D, random.nextFloat() * 360.0F, 0.0F);
    				world.spawnEntityInWorld((Entity)entityChicken);
        		}
    		}
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
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
    // designed by THASSELHOFF
    
    public static class SwampAnimalPen2 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"FFFFFFFFFFFFFF",
    			"FFFFFFFFFFFFFF",
    			"FFFFFFFFFFFFFF",
    			"FFFFFFFFFFFFFF",
    			"FFFFFFFFFFFFFF",
    			"FFFFFFFFFFFFFF",
    			"FFFFFFFFFFFFFF",
    			"FFFFFFFFFFFFFF",
    			"FFFFFFFFFFFFFF",
    			"FFFFFFFFFFFFFF",
    			"FFFFFFFFFFFFFF",
    			"FFFFFFFFFFFFFF",
    			"FFFFFFFFFFFFFF",
    			"FFFFFFFFFFFFFF",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 6;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1+2+4+8; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampAnimalPen2() {}

    	public SwampAnimalPen2(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampAnimalPen2 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampAnimalPen2(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
            
            
            // Grass
            for(int[] uuvvww : new int[][]{
    			{0,0,0, 0,0,13}, 
    			{1,0,0, 1,0,3}, {1,0,7, 3,0,13}, 
    			{2,0,0, 3,0,2}, 
    			{4,0,0, 4,0,1}, {4,0,6, 4,0,7}, {4,0,10, 4,0,13}, 
    			{5,0,0, 6,0,0}, {5,0,5, 5,0,6}, {5,0,11, 5,0,13}, 
    			{6,0,4, 6,0,7}, {6,0,12, 8,0,13}, 
    			{7,0,0, 7,0,4}, {7,0,6, 7,0,9}, 
    			{8,0,0, 8,0,3}, {8,0,7, 8,0,10}, 
    			{9,0,0, 10,0,2}, {9,0,8, 9,0,13}, 
    			{10,0,10, 10,0,13}, 
    			{11,0,0, 11,0,3}, {11,0,10, 11,0,13}, 
    			{12,0,0, 12,0,4}, {12,0,8, 12,0,13}, 
    			{13,0,0, 13,0,13}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);	
            }
    		
    		
    		// Stripped logs (Vertical)
        	Block biomeLogVertBlock = Blocks.log; int biomeLogVertMeta = 0;
    		Block biomeStrippedLogVerticBlock = biomeLogVertBlock; int biomeStrippedLogVerticMeta = biomeLogVertMeta;
    		// Try to see if stripped logs exist
    		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
    		for (int[] uuvvww : new int[][]{
        		// Front Awning
        		{0,1,13, 0,4,13}, {13,1,13, 13,5,13}, 
        		{0,1,0, 0,4,0}, {13,1,0, 13,5,0}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
        	
        	            
            // Fences
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
            for (int[] uuvvww : new int[][]{
            	// Lantern posts
            	{0,4,12}, {1,5,13}, {0,5,13}, {12,5,13}, {13,5,12}, 
            	{0,5,0}, {0,5,1}, {1,4,0}, {12,5,0}, {13,4,1}, 
            	// Fence
            	{1,1,13}, {2,1,13}, {3,1,13}, {4,1,13}, {5,1,13}, {6,1,13}, {9,1,13}, {10,1,13}, {11,1,13}, {12,1,13}, 
            	{0,1,1}, {0,1,2}, {0,1,3}, {0,1,6}, {0,1,7}, {0,1,8}, {0,1,9}, {0,1,10}, {0,1,11}, {0,1,12}, 
            	{13,1,1}, {13,1,2}, {13,1,5}, {13,1,6}, {13,1,7}, {13,1,8}, {13,1,9}, {13,1,10}, {13,1,11}, {13,1,12}, 
            	{1,1,0}, {2,1,0}, {3,1,0}, {4,1,0}, {5,1,0}, {6,1,0}, {7,1,0}, {8,1,0}, {9,1,0}, {12,1,0}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
            
        	// Fence Gate (Across)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence_gate, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceGateBlock = (Block)blockObject[0]; int biomeFenceGateMeta = (Integer)blockObject[1];
        	for(int[] uvw : new int[][]{
        		{7,1,13}, {8,1,13}, 
        		
        		{10,1,0}, {11,1,0}, 
            	})
            {
        		this.placeBlockAtCurrentPosition(world, biomeFenceGateBlock, StructureVillageVN.getMetadataWithOffset(biomeFenceGateBlock, biomeFenceGateMeta, this.coordBaseMode), uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
        	// Fence Gate (Along)
        	for(int[] uvw : new int[][]{
        		{0,1,4}, {0,1,5}, {13,1,3}, {13,1,4}, 
            	})
            {
        		this.placeBlockAtCurrentPosition(world, biomeFenceGateBlock, StructureVillageVN.getMetadataWithOffset(biomeFenceGateBlock, (biomeFenceGateMeta+1)%8, this.coordBaseMode), uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		{0,3,12}, {1,4,13}, 
        		{12,4,13}, {13,4,12}, 
        		{12,4,0}, {13,3,1}, 
        		{1,3,0}, {0,4,1}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	            
    		// Water
    		for(int[] uuvvww : new int[][]{
    			// Back left
    			{1,0,7, 1,0,8}, {2,0,7, 2,0,9}, {3,0,7, 3,0,11}, {4,0,8, 4,0,12}, {5,0,9, 5,0,12}, {6,0,10, 6,0,12}, 
    			// Back right
    			{7,0,8, 7,0,8}, {8,0,7, 9,0,9}, {10,0,6, 10,0,8}, {11,0,5, 11,0,7}, 
    			// Front pond
    			{3,0,3, 3,0,4}, {4,0,2, 4,0,5}, {5,0,1, 5,0,6}, {6,0,1, 6,0,5}, {7,0,1, 7,0,4}, {8,0,2, 9,0,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);	
    		}
    		
    		
    		// Lilypad
    		for (int[] uvw : new int[][]{
    			{2,1,8}, 
    			{3,1,10}, {3,1,11}, 
    			{4,1,5}, {4,1,10}, 
    			{5,1,3}, {5,1,9}, 
    			{7,1,3}, 
    			{9,1,7}, {9,1,9}, 
    			{11,1,6}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.waterlily, 0, uvw[0], uvw[1], uvw[2], structureBB);
    		}
            
            
            // Hay bales (vertical)
        	for (int[] uvw : new int[][]{
        		{9,1,10}, {10,1,10}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.hay_block, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Hay bales (along)
        	for (int[] uvw : new int[][]{
        		{10,1,9}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.hay_block, 4+(this.coordBaseMode%2!=0? 0:4), uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Unkempt Grass
    		for (int[] uvwg : new int[][]{ // g is grass type
    			{1,1,5, 0}, {1,1,11, 2}, {1,1,12, 1}, 
    			{2,1,4, 0}, {2,1,12, 1}, 
    			{7,1,11, 0}, 
    			{9,1,5, 1}, 
    			{10,1,1, 2}, {10,1,3, 0}, {10,1,4, 0}, 
    			{11,1,1, 1}, {11,1,4, 0}, 
    			{12,1,1, 2}, 
    		})
    		{
    			if (uvwg[3]==0) // Short grass
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 1, uvwg[0], uvwg[1], uvwg[2], structureBB);
    			}
    			else if (uvwg[3]==1) // Tall grass
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 2, uvwg[0], uvwg[1], uvwg[2], structureBB);
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 11, uvwg[0], uvwg[1]+1, uvwg[2], structureBB);
    			}
    			else if (uvwg[3]==2) // Fern
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 2, uvwg[0], uvwg[1], uvwg[2], structureBB);
    			}
    			else // Large Fern
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 3, uvwg[0], uvwg[1], uvwg[2], structureBB);
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 11, uvwg[0], uvwg[1]+1, uvwg[2], structureBB);
    			}
    		}
        	
    		
    		// Entities
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			// Livestock
            	for (int[] uvw : new int[][]{
        			{2, 1, 10}, 
        			{8, 1, 5}, 
        			})
        		{
                	EntityLiving animal = StructureVillageVN.getVillageAnimal(world, random, false, this.materialType==MaterialType.MUSHROOM);
                    animal.setLocationAndAngles((double)this.getXWithOffset(uvw[0], uvw[2]) + 0.5D, (double)this.getYWithOffset(uvw[1]) + 0.5D, (double)this.getZWithOffset(uvw[0], uvw[2]) + 0.5D, random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(animal);
                    
                    // Dirt block underneath
                    //this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, uvw[0], uvw[1]-1, uvw[2], structureBB);
        		}
            	
    			// Horse
            	for (int[] uvw : new int[][]{
            		{2, 1, 2}, 
        			})
        		{
    				EntityHorse entityHorse = new EntityHorse(world);
    				IEntityLivingData ientitylivingdata = entityHorse.onSpawnWithEgg(null);
    				
                	if (VillageGeneratorConfigHandler.nameVillageHorses && GeneralConfig.nameEntities)
                	{
                		String[] petname_a = NameGenerator.newRandomName("pet", random);
                		entityHorse.setCustomNameTag((petname_a[1]+" "+petname_a[2]+" "+petname_a[3]).trim());
                	}
                	entityHorse.setLocationAndAngles((double)this.getXWithOffset(uvw[0], uvw[2]) + 0.5D, (double)this.getYWithOffset(uvw[1]) + 0.5D, (double)this.getZWithOffset(uvw[0], uvw[2]) + 0.5D, random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(entityHorse);
                    
                    // Dirt block underneath
                    //this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, uvw[0], uvw[1]-1, uvw[2], structureBB);
        		}
    		}
			
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
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
    // designed by AstroTibs
    
    public static class SwampArmorerHouse extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"         ",
    			"FFFFFFFFF",
    			"FFFFFFFFF",
    			"FFFFFFFFF",
    			"FFFFFFFFF",
    			"FFFFFFFFF",
    			"FFFFFFFFF",
    			"FFFFFFFF ",
    			"FFFFFFFF ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 6;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = -1;
    	private static final int DECREASE_MAX_U = 5;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampArmorerHouse() {}

    	public SwampArmorerHouse(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampArmorerHouse buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampArmorerHouse(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
    		
    		
    		// Brick Blocks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.brick_block, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeBrickBlock = (Block)blockObject[0]; int biomeBrickMeta = (Integer)blockObject[1];
        	for(int[] uuvvww : new int[][]{
        		{0,0,3, 7,0,3}, 
        		{0,0,0, 0,0,2}, 
        		// Furnace
        		{7,0,2, 8,1,2}, {8,0,3, 8,1,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeBrickBlock, biomeBrickMeta, biomeBrickBlock, biomeBrickMeta, false);	
    		}
            
            
            // Brick Walls
        	Block biomeBrickWallBlock = null; int biomeBrickWallMeta = 0;
        	
        	// First, attempt to obtain modded brick wall
        	blockObject = ModObjects.chooseModBrickWall();
        	if (blockObject==null)
        	{
        		// Use cobblestone
        		biomeBrickWallBlock = Blocks.cobblestone_wall; biomeBrickWallMeta = 0;
        	}
        	else
        	{
        		biomeBrickWallBlock = (Block) blockObject[0]; biomeBrickWallMeta = (Integer) blockObject[1];
        	}
        	// Convert to biome-specific versions
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(biomeBrickWallBlock, biomeBrickWallMeta, this.materialType, this.biome, this.disallowModSubs);
        	biomeBrickWallBlock = (Block)blockObject[0]; biomeBrickWallMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(biomeBrickBlock, biomeBrickMeta, this.materialType, this.biome, this.disallowModSubs);
        	for(int[] uuvvww : new int[][]{
    			// Furnace chimney
    			{7,2,3, 7,3,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeBrickWallBlock, biomeBrickWallMeta, biomeBrickWallBlock, biomeBrickWallMeta, false);	
    		}
            
            
            // Blast Furnace - this is a TileEntity and needs to have its meta assigned manually
        	for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
        		{7,1,3, 3}, 
        		})
            {
        		blockObject = ModObjects.chooseModBlastFurnaceBlock(uvw[3], this.coordBaseMode); Block blastFurnaceBlock = (Block) blockObject[0]; int blastFurnaceMeta = (Integer) blockObject[1];
                this.placeBlockAtCurrentPosition(world, blastFurnaceBlock, 0, uvw[0], uvw[1], uvw[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvw[0], uvw[2]), this.getYWithOffset(uvw[1]), this.getZWithOffset(uvw[0], uvw[2]), blastFurnaceMeta, 2);
            }
    		
			
    		// Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Floor
    			{0,0,4, 8,0,4}, 
    			{0,0,5, 0,0,6}, {8,0,5, 8,0,6}, 
    			{0,0,7, 8,0,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
        	
        	
        	// Terracotta (Green)
        	for(int[] uuvvww : new int[][]{
        		{0,1,7, 8,1,7}, 
        		{0,1,5, 0,1,6}, {8,1,5, 8,1,6}, 
        		{0,1,4, 3,1,4}, {5,1,4, 8,1,4}, 
            	})
            {
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
    					Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor2 : 13), 
    					Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor2 : 13), 
    					false);	
            }
        	
        	
        	// Terracotta (Black)
        	for(int[] uuvvww : new int[][]{
        		{0,3,4, 8,3,4}, 
        		{0,2,4, 1,2,4}, {7,2,4, 8,2,4}, 
        		{0,3,7, 8,3,7}, 
        		{0,2,7, 1,2,7}, {3,2,7, 3,2,7}, {5,2,7, 5,2,7}, {7,2,7, 8,2,7}, 
        		{0,2,5, 0,3,6}, {8,2,5, 8,3,6}, 
            	})
            {
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
    					Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor : 15), 
    					Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor : 15), 
    					false);	
            }
        	
        	
            // Glazed terracotta
        	Object[] tryGlazedTerracotta;
        	for (int[] uvwoc : new int[][]{ // u,v,w, orientation, dye color
        		// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
        		{3,2,4, 2, GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
        		{5,2,4, 0, GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
           		})
        	{
        		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(uvwoc[4], StructureVillageVN.chooseGlazedTerracottaMeta(uvwoc[3], this.coordBaseMode));
        		if (tryGlazedTerracotta != null)
            	{
        			this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvwoc[0], uvwoc[1], uvwoc[2], structureBB);
            	}
        		else
        		{
        			this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, uvwoc[4], uvwoc[0], uvwoc[1], uvwoc[2], structureBB);
        		}
            }
    		
    		
    		// Stripped logs (Vertical)
        	Block biomeLogVertBlock = Blocks.log; int biomeLogVertMeta = 0;
    		Block biomeStrippedLogVerticBlock = biomeLogVertBlock; int biomeStrippedLogVerticMeta = biomeLogVertMeta;
    		// Try to see if stripped logs exist
    		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
    		for (int[] uuvvww : new int[][]{
        		// Front Awning
        		{1,0,1, 1,2,1}, {3,0,1, 3,2,1}, {5,0,1, 5,2,1}, {7,0,1, 7,2,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
            
            
            // Water boundary
    		// This puts up a dirt/grass wall to keep in the water if it's not level with the surrounding ground
            for(int[] uwuwvs : new int[][]{ // u/w box corners, v height, and the side that is to be evaluated.
            	// Side - 0:forward (away from you), 1:rightward, 2:backward (toward you), 3: leftward
            	
    			{7,0, 7,0, 0, 1}, // Right side
    			{0,0, 8,0, 0, 2}, // Back side
    			})
    		{
            	int u_offset=0, w_offset=0;
            	int v = uwuwvs[4];
            	
            	switch(uwuwvs[5])
            	{
            	case 0: w_offset=1; break; // forward
            	case 1: u_offset=1; break; // rightward
            	case 2: w_offset=-1; break; // backward
            	case 3: u_offset=-1; break; // leftward
            	default:
            	}
            	
            	// Scan boundary and add containment if necessary
            	for (int u=uwuwvs[0]; u<=uwuwvs[2]; u++) {for (int w=uwuwvs[1]; w<=uwuwvs[3]; w++)
            	{
            		int x = this.getXWithOffset(u+u_offset, w+w_offset);
            		int y = this.getYWithOffset(v);
            		int z = this.getZWithOffset(u+u_offset, w+w_offset);
            		
            		// If space above bordering block is liquid, fill below with filler and cap with topper
            		if (world.getBlock(x, y+1, z).getMaterial().isLiquid())
            		{
            			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u+u_offset, v, w+w_offset, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u+u_offset, v+1, w+w_offset, structureBB);
            		}
            		// If bordering block is air, fill below with filler and cap with topper
            		else if (world.isAirBlock(x, y, z))
            		{
            			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u+u_offset, v-1, w+w_offset, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u+u_offset, v, w+w_offset, structureBB);
            		}
            	}}
    		}
            
            
    		// Water
    		for(int[] uuvvww : new int[][]{
    			{1,0,2, 6,0,2}, 
    			{2,0,1, 2,0,1}, {4,0,1, 4,0,1}, {6,0,1, 6,0,1}, 
    			{1,0,0, 7,0,0}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);	
    		}
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	// Roof
            	{0,4,7, 8,4,7}, 
            	{0,4,5, 0,4,6}, {8,4,5, 8,4,6}, 
            	// Desk
            	{1,1,6, 1,1,6}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{0,4,8, 8,4,8}, 
    			{0,4,4, 8,4,4}, 
    			{0,3,1, 8,3,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{0,3,3, 6,3,3}, {8,3,3, 8,3,3}, 
    			// Ceiling
    			{1,4,5, 7,4,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
    		
    		// Brick Slab (lower)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 4, this.materialType, this.biome, this.disallowModSubs); Block biomeBrickSlabLowerBlock = (Block)blockObject[0]; int biomeBrickSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{0,5,6, 8,5,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeBrickSlabLowerBlock, biomeBrickSlabLowerMeta, biomeBrickSlabLowerBlock, biomeBrickSlabLowerMeta, false);	
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Chair
    			{7,1,5, 2}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
        	
        	            
            // Fences
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
            for (int[] uuvvww : new int[][]{
            	{7,1,6}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
        	
        	
            // Wooden pressure plate
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_pressure_plate, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodPressurePlateBlock = (Block)blockObject[0]; int biomeWoodPressurePlateMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		{7,2,6}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeWoodPressurePlateBlock, biomeWoodPressurePlateMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Trapdoor (Top Vertical)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapdoor, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{2,2,7, 2}, {4,2,7, 2}, {6,2,7, 2}, 
            	{2,2,4, 0}, {6,2,4, 0}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, true, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{4,1,4, 2, 1, 1}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
            
            
            // Bookshelves
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.bookshelf, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeBookshelfBlock = (Block)blockObject[0]; int biomeBookshelfMeta = (Integer)blockObject[1];
            for (int[] uuvvww : new int[][]{
        		{1,1,5, 1,2,5}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeBookshelfBlock, biomeBookshelfMeta, biomeBookshelfBlock, biomeBookshelfMeta, false);
            }
            
            
        	// Wool - carpet in front of the door prevents villagers from passing through
        	for(int[] uuvvww : new int[][]{
        		{2,0,5, 6,0,6, (GeneralConfig.useVillageColors ? this.townColor3 : 10)}, // Purple
        		})
            {
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.wool, uuvvww[6], Blocks.wool, uuvvww[6], false);	
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	{1,2,2}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Sitting Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(false); Block biomeSittingLanternBlock = (Block)blockObject[0]; int biomeSittingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
        		{7,2,2}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeSittingLanternBlock, biomeSittingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			{4,3,6, 2}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
        	
        	
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 1;
        	int chestV = 2;
        	int chestW = 6;
        	int chestO = 1; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo("vn_armorer");
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
        	
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1: rightward, 2:backward, 3: leftward
        			// Left side
        			{-1,4,5, 3}, {-1,3,5, 3}, {-1,2,5, 3}, 
        			// Right side
        			{9,4,6, 1}, {9,3,6, 1}, {9,2,6, 1}, {9,1,6, 1}, 
        			{9,1,7, 1}, 
        			// Away-facing vines
        			{1,3,8, 0}, {1,2,8, 0}, 
        			{3,3,8, 0}, {3,2,8, 0}, {3,1,8, 0}, 
        			{4,3,8, 0}, 
        			// Player-facing side
        			{1,2,0, 2}, {1,1,0, 2}, 
        			{7,2,0, 2}, 
            		})
                {
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
        			}
                }
        	}
    		
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			// Villager
    			int s = random.nextInt(10+3);
    			
    			int u;
    			int v = 3;
    			int w;
    			
				u = s<=9? 2+(s/2) : s-6;
				w = s<=9? 5+(s%2) : 3;
    			
    			
				EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 3, 1, 0); // Armorer
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
    			world.spawnEntityInWorld(entityvillager);
    		}
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 3;}
    }
    
    
    // --- Butcher Shop --- //
    // designed by AstroTibs and THASSELHOFF
    
    public static class SwampButcherShop extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"     FFFF    ",
    			"    FFFFFF   ",
    			"  FFFFFFFFF  ",
    			" FFFFFFFFFFF ",
    			"FFFFFFFFFFFF ",
    			"FFFFFFFFFFFFF",
    			" FFFFFFFFFFFF",
    			"  FFFFFFFFFFF",
    			"  FFFFFFFFFFF",
    			"  FFFFFFFFFFF",
    			"  FFFFFFFFFFF",
    			"   FFFFFFFFFF",
    			"   FFFFFFFFF ",
    			"   FFFFFFFF  ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 8;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 2; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 3;
    	private static final int DECREASE_MAX_U = 3;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampButcherShop() {}

    	public SwampButcherShop(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampButcherShop buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampButcherShop(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
    		
    		
    		// Grass Path
        	for (int[] uvw : new int[][]{
        		{5,1,2}, {6,1,2}, {7,1,2}, 
        		{5,1,1}, {6,1,1}, {7,1,1}, 
        		{5,1,0}, {6,1,0}, {7,1,0}, 
            	}) {
        		int posX = this.getXWithOffset(uvw[0], uvw[2]);
    			int posY = this.getYWithOffset(uvw[1]);
    			int posZ = this.getZWithOffset(uvw[0], uvw[2]);
        		StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
            }
    		
			
    		// Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{3,0,3, 8,0,9}, 
    			{4,4,4, 4,4,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
    			// Front wall
    			{3,4,2, 5,4,2}, {6,4,3, 7,4,3}, 
    			{3,1,3, 8,1,3}, {3,2,3, 5,3,3}, {7,2,3, 8,3,3}, 
    			// Left wall
    			{3,1,4, 3,2,9}, {3,4,3, 3,4,8}, {3,3,4, 3,3,4}, {3,3,7, 3,3,9}, 
    			// Right wall
    			{8,1,4, 8,4,9}, 
    			// Back wall
    			{4,1,9, 7,4,9}, 
    			// Ceiling
    			{5,4,4, 7,4,4}, {4,4,5, 7,4,8}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
            
            
            // Dirt
            for(int[] uuvvww : new int[][]{
    			// Front entrance
            	{4,1,0, 4,1,2}, 
            	// Left hill
            	{1,2,8, 1,2,9}, 
            	{2,2,7, 2,3,9}, {2,2,10, 2,2,10}, 
            	{3,2,10, 4,3,10}, 
            	{4,2,11, 4,2,11}, 
            	{5,2,10, 8,3,11}, {8,4,11, 8,4,11}, {6,2,12, 7,2,12}, 
            	{8,4,2, 8,4,2}, {8,2,1, 8,2,1}, 
            	{9,2,1, 9,2,1}, {9,2,2, 9,3,2}, {9,2,3, 9,3,10}, {9,4,3, 9,4,7}, 
            	{10,2,0, 10,2,1}, {10,2,2, 10,3,7}, {10,2,8, 10,2,9}, 
            	{11,2,2, 11,2,7}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);
            }
            
            
            // Grass
            for(int[] uuvvww : new int[][]{
    			{3,5,6, 3,5,8}, {4,5,5, 8,5,9}, {5,5,4, 8,5,4}, {6,5,3, 8,5,3}, {9,5,3, 9,5,7}, {8,5,2, 8,5,2}, 
    			{2,4,7, 2,4,9}, {3,4,9, 3,4,9}, {3,4,10, 8,4,10}, {5,4,11, 7,4,11}, {9,4,2, 9,4,2}, {9,4,8, 9,4,9}, {10,4,2, 10,4,7}, {4,4,3, 5,4,3}, {6,4,2, 7,4,2}, 
    			{1,3,8, 1,3,9}, {2,3,10, 2,3,10}, {4,3,11, 4,3,11}, {6,3,12, 7,3,12}, {9,3,10, 9,3,10}, {10,3,8, 10,3,9}, {11,3,2, 11,3,7}, {10,3,0, 10,3,1}, {8,3,1, 9,3,1}, 
    			{1,2,7, 1,2,7}, {0,2,8, 0,2,9}, {1,2,10, 1,2,10}, {2,2,11, 3,2,11}, {4,2,12, 5,2,12}, {5,2,13, 8,2,13}, {8,2,12, 9,2,12}, {9,2,0, 9,2,0}, {9,2,11, 10,2,11}, {10,2,10, 11,2,10}, 
    			{11,2,8, 11,2,9}, {12,2,2, 12,2,8}, {11,2,1, 11,2,1}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);	
            }
    		
    		
    		// Stripped logs (Vertical)
        	Block biomeLogVertBlock = Blocks.log; int biomeLogVertMeta = 0;
    		Block biomeStrippedLogVerticBlock = biomeLogVertBlock; int biomeStrippedLogVerticMeta = biomeLogVertMeta;
    		// Try to see if stripped logs exist
    		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
    		for (int[] uuvvww : new int[][]{
        		{4,2,2, 4,3,2}, {8,2,2, 8,3,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
    		
        	
        	// Clear out basement
            for(int[] uuvvww : new int[][]{
    			{4,1,4, 7,3,8}, 
    			})
    		{
            	this.fillWithAir(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5]);	
    		}
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		{6,3,7}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Smooth Stone Block
        	blockObject = ModObjects.chooseModSmoothStoneBlockObject(); Block smoothStoneBlock = (Block)blockObject[0]; int smoothStoneMeta = (Integer)blockObject[1];
            for (int[] uuvvww : new int[][]{
            	// Counter
            	{5,1,7, 6,1,7}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], smoothStoneBlock, smoothStoneMeta, smoothStoneBlock, smoothStoneMeta, false);
            }
        	
        	
        	// Cobblestone wall
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{4,5,4, 4,7,4}, {4,2,4, 4,3,4}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, false);
            }
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Stairs up to animal pen
    			{8,2,0, 0}, {9,3,0, 0}, {10,4,1, 3}, {9,5,2, 1}, 
    			// Basement
    			{6,1,4, 2}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Fences
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
    		for (int[] uuvvww : new int[][]{
    			{3,6,6, 3,6,8}, {4,6,8, 4,6,9}, {5,6,9, 8,6,9}, 
    			{8,6,7, 8,6,8}, {9,6,3, 9,6,7}, 
    			{4,6,5, 4,6,6}, {5,6,4, 5,6,5}, {6,6,3, 6,6,4}, {7,6,3, 7,6,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			{3,7,6, -1}, 
    			{6,7,3, -1}, 
    			{9,7,7, -1}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
            
            
        	// Fence Gate (Across)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence_gate, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceGateBlock = (Block)blockObject[0]; int biomeFenceGateMeta = (Integer)blockObject[1];
        	for(int[] uvw : new int[][]{
            	{8,6,3}, 
            	})
            {
        		this.placeBlockAtCurrentPosition(world, biomeFenceGateBlock, StructureVillageVN.getMetadataWithOffset(biomeFenceGateBlock, biomeFenceGateMeta, this.coordBaseMode), uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Smoker
        	blockObject = ModObjects.chooseModSmokerBlock(3, this.coordBaseMode); Block smokerBlock = (Block) blockObject[0];
            for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{4,1,4, 1}
            	})
            {
                this.placeBlockAtCurrentPosition(world, smokerBlock, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2]), StructureVillageVN.chooseFurnaceMeta(uvwo[3], this.coordBaseMode), 2);
            }
    		
    		
    		// Trapdoor (Top Vertical)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapdoor, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{3,3,5, 1}, {3,3,6, 1}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, true, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{6,2,3, 2, 1, 0}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
    		
    		
    		// Unkempt Grass
    		for (int[] uvwg : new int[][]{ // g is grass type
    			{1,3,7, 1}, {1,4,8, 0}, 
    			{2,4,10, 2}, 
    			{3,3,11, 0}, {3,5,9, 0}, 
    			{5,3,13, 0}, 
    			{6,5,10, 1}, 
    			{7,5,11, 0}, {7,6,8, 0}, {7,6,4, 0}, 
    			{8,3,12, 0}, 
    			{9,3,11, 1}, {9,5,8, 0}, 
    			{10,4,8, 0}, {10,5,4, 0}, 
    			{11,4,3, 0}, {11,4,5, 0}, 
    			{12,3,5, 2}, {12,3,6, 1}, 
    		})
    		{
    			if (uvwg[3]==0) // Short grass
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 1, uvwg[0], uvwg[1], uvwg[2], structureBB);
    			}
    			else if (uvwg[3]==1) // Tall grass
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 2, uvwg[0], uvwg[1], uvwg[2], structureBB);
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 11, uvwg[0], uvwg[1]+1, uvwg[2], structureBB);
    			}
    			else if (uvwg[3]==2) // Fern
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 2, uvwg[0], uvwg[1], uvwg[2], structureBB);
    			}
    			else // Large Fern
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 3, uvwg[0], uvwg[1], uvwg[2], structureBB);
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 11, uvwg[0], uvwg[1]+1, uvwg[2], structureBB);
    			}
    		}
        	
        	
            // Decor
            int[][] decorUVW = new int[][]{
            	{8,5,11}, 
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
            	
            	// Generate decor
            	ArrayList<BlueprintData> decorBlueprint = StructureVillageVN.getRandomDecorBlueprint(this.villageType, this.materialType, this.disallowModSubs, this.biome, this.coordBaseMode, randomFromXYZ, VillageGeneratorConfigHandler.allowTaigaTroughs && !VillageGeneratorConfigHandler.restrictTaigaTroughs);
            	
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
        	
    		
    		// Entities
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			// Villager
    			int s = random.nextInt(16);
    			
    			int u = s<=3 ? 4 : s<=7 ? 5 : s<=10 ? 6 : 7;
    			int v = 1;
    			int w = s<=3 ? s+5 : s<=6 ? s : s<=8 ? 8 : s<=10 ? s-4 : s-7;
    			
    			EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 4, 1, 0); // Butcher
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
    			world.spawnEntityInWorld(entityvillager);
    			
    			
            	// Animals
            	for (int[] uvw : new int[][]{
        			{6, 6, 6},
        			})
        		{
                	EntityLiving animal = StructureVillageVN.getVillageAnimal(world, random, false, this.materialType==MaterialType.MUSHROOM);
                    animal.setLocationAndAngles((double)this.getXWithOffset(uvw[0], uvw[2]) + 0.5D, (double)this.getYWithOffset(uvw[1]) + 0.5D, (double)this.getZWithOffset(uvw[0], uvw[2]) + 0.5D, random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(animal);
                    
                    // Dirt block underneath
                    //this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, uvw[0], uvw[1]-1, uvw[2], structureBB);
        		}
    		}
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
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
    // designed by AstroTibs
    
    public static class SwampCartographerHouse extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"          ",
    			"          ",
    			"          ",
    			"          ",
    			"          ",
    			"          ",
    			"          ",
    			"         F",
    			"         P",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 9;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 6;
    	private static final int DECREASE_MAX_U = -1;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampCartographerHouse() {}

    	public SwampCartographerHouse(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampCartographerHouse buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampCartographerHouse(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
        	
        	
        	// Dark Prismarine Base
			blockObject = ModObjects.chooseModDarkPrismarineObject(); Block darkPrismarineBlock; int darkPrismarineMeta;
			if (blockObject==null) {darkPrismarineBlock = Blocks.stonebrick; darkPrismarineMeta = 0;}
			else {darkPrismarineBlock = (Block)blockObject[0]; darkPrismarineMeta = (Integer)blockObject[1];}
        	for (int[] uuvvww : new int[][]{
            	{1,1,7, 1,2,7}, {7,1,7, 7,2,7}, 
            	{3,1,5, 3,8,5}, {5,1,5, 5,2,5}, 
            	{3,1,3, 3,2,3}, {5,1,3, 5,2,3}, 
            	{1,1,1, 1,2,1}, {7,1,1, 7,2,1}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], darkPrismarineBlock, darkPrismarineMeta, darkPrismarineBlock, darkPrismarineMeta, false);
            }
    		// Foot foundation
    		for(int[] uvw : new int[][]{
            	{1,0,7}, {7,0,7}, 
            	{3,0,5}, {5,0,5}, 
            	{3,0,3}, {5,0,3}, 
            	{1,0,1}, {7,0,1}, 
    			})
    		{
    			this.func_151554_b(world, darkPrismarineBlock, darkPrismarineMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
            // Purple Terracotta
        	for (int[] uuvvww : new int[][]{
        		// Floor
        		{1,3,1, 2,3,8}, 
        		{3,3,1, 3,3,4}, {3,3,6, 3,3,8}, 
        		{4,3,1, 5,3,8}, 
        		{6,3,1, 7,3,7}, 
        		{8,3,4, 8,3,4}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor3 : 10),
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor3 : 10), 
        				false);
            }
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	// Porch
            	{0,3,0, 0,3,1}, {1,3,0, 8,3,0}, {8,3,1, 8,3,3}, {8,3,5, 8,3,8}, {9,3,4, 9,3,5}, {6,3,8, 7,3,8}, 
            	// Left wall
            	{0,3,2, 0,5,8}, 
            	// Right wall
            	{6,4,2, 6,5,4}, {6,4,6, 6,5,8}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
    		
    		
    		// Fences
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
    		for (int[] uuvvww : new int[][]{
            	// Porch
            	{0,4,0, 0,4,1}, {1,4,0, 8,4,0}, {8,4,1, 8,4,3}, {8,4,5, 9,4,5}, {8,4,6, 8,4,8}, {7,4,8, 7,4,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
    		}
    		
    		
    		// Cobblestone stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Roof
    			{0,6,8, 0}, {1,7,8, 0}, {2,8,8, 0}, {4,8,8, 1}, 
    			{1,7,7, 0}, {2,8,7, 0}, {4,8,7, 1}, 
    			{0,6,6, 0}, {1,7,6, 0}, {2,8,6, 0}, {4,8,6, 1}, {5,7,6, 1}, 
    			{1,7,5, 0}, {2,8,5, 0}, {6,6,5, 1}, 
    			{5,7,4, 1}, {6,6,4, 1}, 
    			{2,8,3, 0}, {4,8,3, 1}, {5,7,3, 1}, 
    			{0,6,2, 0}, {1,7,2, 0}, {2,8,2, 0}, {4,8,2, 1}, {5,7,2, 1}, {6,6,2, 1}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
        	
        	
    		// Mossy Cobblestone stairs
    		Block modblock = ModObjects.chooseModMossyCobblestoneStairsBlock();
    		if (modblock==null) {modblock = Blocks.stone_stairs;}
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(modblock, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			{5,7,8, 1}, {6,6,8, 1}, 
    			{0,6,7, 0}, {5,7,7, 1}, {6,6,7, 1}, 
    			{6,6,6, 1}, 
    			{0,6,5, 0}, {4,8,5, 1}, {5,7,5, 1}, 
    			{0,6,4, 0}, {1,7,4, 0}, {2,8,4, 0}, {4,8,4, 1}, 
    			{0,6,3, 0}, {1,7,3, 0}, {6,6,3, 1}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeMossyCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Cobblestone Slab (lower)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabLowerBlock = (Block)blockObject[0]; int biomeCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{3,9,2, 3,9,3}, {3,9,5, 3,9,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone Slab (lower)
    		Block biomeMossyCobblestoneSlabLowerBlock = biomeCobblestoneSlabLowerBlock; int biomeMossyCobblestoneSlabLowerMeta = biomeCobblestoneSlabLowerMeta;
    		blockObject = ModObjects.chooseModMossyCobblestoneSlabBlock(false);
    		if (blockObject != null)
    		{
    			blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs);
    			biomeMossyCobblestoneSlabLowerBlock = (Block)blockObject[0]; biomeMossyCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		}
    		for(int[] uuvvww : new int[][]{
    			{3,9,4, 3,9,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Seat
    			{1,4,3, 1}, {1,4,4, 1}, 
    			// Entryway
    			{9,1,1, 3}, 
    			{9,2,2, 3}, 
    			{9,3,3, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{9,1,2, 9,1,2}, 
    			{9,2,3, 9,2,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
    		
    		// Trapdoor (Bottom Vertical)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapdoor, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{1,4,5, 0}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, false, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
        	
        	
            // Glazed terracotta
        	Object[] tryGlazedTerracotta;
        	for (int[] uvwoc : new int[][]{ // u,v,w, orientation, dye color
        		// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
        		{5,4,7, 2, GeneralConfig.useVillageColors ? this.townColor3 : 10}, // Purple
           		})
        	{
        		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(uvwoc[4], StructureVillageVN.chooseGlazedTerracottaMeta(uvwoc[3], this.coordBaseMode));
        		if (tryGlazedTerracotta != null)
            	{
        			this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvwoc[0], uvwoc[1], uvwoc[2], structureBB);
            	}
        		else
        		{
        			this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, uvwoc[4], uvwoc[0], uvwoc[1], uvwoc[2], structureBB);
        		}
            }
        	
        	
        	// Potted plant
        	for (int[] uvws : new int[][]{ // u,v,w, sapling
        		{5,5,7, random.nextInt(6)},
           		})
        	{
            	StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, 
            			this.getXWithOffset(uvws[0], uvws[2]), 
            			this.getYWithOffset(uvws[1]), 
            			this.getZWithOffset(uvws[0], uvws[2]), 
            			Blocks.sapling, uvws[3]);
        	}
            
        	
        	// Cartography Table
        	blockObject = ModObjects.chooseModCartographyTable(biomePlankMeta); Block cartographyTableBlock = (Block) blockObject[0]; int cartographyTableMeta = (Integer) blockObject[1];
            for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
        		{1,4,7}, 
           		})
        	{
            	this.placeBlockAtCurrentPosition(world, cartographyTableBlock, cartographyTableMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Bookshelves
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.bookshelf, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeBookshelfBlock = (Block)blockObject[0]; int biomeBookshelfMeta = (Integer)blockObject[1];
            for (int[] uuvvww : new int[][]{
        		{5,6,7, 5,6,7}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeBookshelfBlock, biomeBookshelfMeta, biomeBookshelfBlock, biomeBookshelfMeta, false);
            }
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{6,4,5, 3, 1, 1}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
            
            
            // Glass blocks
        	for (int[] uuvvww : new int[][]{
        		{1,4,8, 5,6,8}, {2,7,8, 4,7,8}, {3,8,8, 3,8,8}, 
        		{1,4,2, 5,6,2}, {2,7,2, 4,7,2}, {3,8,2, 3,8,2}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.glass, 0, Blocks.glass, 0, false);
            }
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			{3,6,6, 0}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
        	
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1: rightward, 2:backward, 3: leftward
        			// Right facing
        			{7,5,7, 1}, 
        			// Away-facing
        			{5,6,9, 0}, {5,5,9, 0}, {5,4,9, 0}, 
        			{6,5,9, 0}, {6,4,9, 0}, {6,3,9, 0}, {6,2,9, 0}, {6,1,9, 0}, 
            		})
                {
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
        			}
                }
        	}
    		
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			// Villager
    			int s = random.nextInt(20);
    			
    			int u = s<=1? 1 : s<=6? 2 : s<=8? 3 : s<=10? 3 : s<=15? 4 : 5;
    			int v = 4;
    			int w = s<=1? s+5 : s<=6? s+1 : s<=8? s-4 : s<=10? s-3 : s<=15? s-8 : s-13;
    			
    			EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 1, 2, 0); // Cartographer
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
    			world.spawnEntityInWorld(entityvillager);
    		}
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 1;}
    }
    
    
    // --- Fisher Cottage 1 --- //
    // designed by jss2a98aj
    
    public static class SwampFisherCottage1 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"FFF   FFF ",
    			"FFFFFFFFF ",
    			"FFFFFFFFF ",
    			" FFFFFFF  ",
    			" FFFFFFF  ",
    			" FFFFFFF  ",
    			"FFFFFFFFF ",
    			"FFFFFFFFF ",
    			"FFF   FFFF",
    			"      PPPP",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 8;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 6;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampFisherCottage1() {}

    	public SwampFisherCottage1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampFisherCottage1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampFisherCottage1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
    		
    		
    		// Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{6,0,9, 8,0,9}, {8,0,8, 8,0,8}, 
    			{0,0,7, 0,0,9}, {1,0,7, 1,0,7}, {1,0,9, 2,0,9}, 
    			{0,0,1, 0,0,2}, {1,0,1, 1,0,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.mossy_cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneBlock = (Block)blockObject[0]; int biomeMossyCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Bases
    			{6,0,7, 7,0,8}, {8,0,7, 8,0,7}, 
    			{1,0,8, 2,0,8}, {2,0,7, 2,0,7}, 
    			{0,0,3, 2,0,3}, {1,0,2, 2,0,2}, {2,0,1, 2,0,1}, 
    			// Rims
    			{1,0,4, 1,0,6}, 
    			{3,0,2, 5,0,2}, {3,0,8, 5,0,8}, 
    			{7,0,4, 7,0,6}, 
    			{6,0,1, 8,0,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, false);	
    		}
    		
            
    		// Water
    		for(int[] uuvvww : new int[][]{
    			{2,0,4, 2,0,6}, {3,0,3, 5,0,7}, {6,0,4, 6,0,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);	
    		}
    		
    		
    		// Lilypad
    		for (int[] uvw : new int[][]{
    			{4,1,3}, {5,1,5}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.waterlily, 0, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Logs (Vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Wall corners
    			{7,4,5, 7,6,5}, 
    			{8,4,6, 8,6,6}, 
    			{5,4,9, 5,6,9}, {8,4,9, 8,6,9}, 
    			{4,4,8, 4,6,8}, 
    			{1,4,5, 1,6,5}, 
    			{0,4,4, 0,6,4}, 
    			{0,4,1, 0,6,1}, {3,4,1, 3,6,1}, {4,4,2, 4,6,2}, 
    			// Bases
    			{1,1,8, 1,6,8}, {7,1,8, 7,2,8},
    			{1,1,2, 1,2,2}, {7,1,2, 7,6,2},
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);	
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			{7,2,3, 0}, {1,2,7, 2}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	{6,3,7, 7,3,8}, 
            	{1,3,7, 1,3,7}, {2,3,8, 2,3,8}, 
    			{1,3,2, 2,3,3}, {6,3,2, 6,3,3}, {7,3,3, 7,3,3}, 
    			// Roof
    			{2,7,7, 2,7,7}, {6,7,7, 7,7,8}, 
    			{2,7,5, 3,7,5}, {5,7,5, 6,7,5}, {4,7,3, 4,7,4}, {4,7,6, 4,7,7}, 
    			{1,7,2, 2,7,3}, {6,7,3, 6,7,3}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Bottom trim
    			// Back-right post
    			{5,3,9, 2+4}, {6,3,9, 2+4}, {7,3,9, 2+4}, {8,3,9, 2+4}, 
    			{5,3,7, 0+4}, {5,3,8, 0+4}, {8,3,7, 1+4}, {8,3,8, 1+4}, 
    			{5,3,6, 3+4}, {6,3,6, 3+4}, {7,3,6, 3+4}, {8,3,6, 3+4}, 
    			// Front-left post
    			{0,3,4, 2+4}, {1,3,4, 2+4}, {2,3,4, 2+4}, {3,3,4, 2+4}, 
    			{0,3,2, 0+4}, {0,3,3, 0+4}, {3,3,2, 1+4}, {3,3,3, 1+4}, 
    			{0,3,1, 3+4}, {1,3,1, 3+4}, {2,3,1, 3+4}, {3,3,1, 3+4}, 
    			// Front-right post
    			{5,3,2, 0+4}, {5,3,3, 0+4}, {5,3,4, 0+4}, {6,3,4, 2+4}, {7,3,4, 2+4}, {8,3,4, 2+4}, 
    			// Back-left post
    			{1,3,6, 3+4}, {3,3,8, 1+4}, 
    			// Front steps
    			{7,1,1, 0}, {8,1,1, 3}, {8,2,2, 3}, {8,3,3, 3}, 
    			{8,1,2, 2+4}, {8,2,3, 2+4}, 
    			// Table
    			{1,4,2, 1+4}, {3,4,2, 0+4}, 
    			// Roof trim
    			{0,7,4, 0}, {0,7,3, 0}, {0,7,2, 0}, 
    			{1,7,7, 0}, {1,7,6, 0}, {1,7,5, 0}, {1,7,4, 2}, 
    			{5,7,8, 2}, {4,7,8, 2}, {3,7,8, 2}, {2,7,8, 2}, {1,7,8, 2}, 
    			{7,7,9, 2}, {6,7,9, 2}, {5,7,9, 0}, 
    			{8,7,7, 1}, {8,7,8, 1}, {8,7,9, 1}, 
    			{7,7,3, 1}, {7,7,4, 1}, {7,7,5, 1}, {7,7,6, 1}, {8,7,6, 3}, 
    			{3,7,2, 1}, {4,7,2, 3}, {5,7,2, 3}, {6,7,2, 3}, {7,7,2, 3}, 
    			{0,7,1, 3}, {1,7,1, 3}, {2,7,1, 3}, {3,7,1, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{4,3,2, 4,3,5}, {4,3,8, 4,3,8}, 
    			{5,3,5, 8,3,5}, 
    			{1,3,5, 1,3,5}, 
    			// Table
    			{2,4,2, 2,4,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
    		
            // Black Terracotta
        	for (int[] uuvvww : new int[][]{
        		// Windows
        		{8,6,7, 8,6,8}, {6,6,9, 7,6,9}, {2,6,8, 3,6,8}, {1,6,6, 1,6,7}, {0,6,2, 0,6,3}, {1,6,1, 2,6,1}, {5,6,2, 6,6,2}, {8,6,7, 8,6,8}, 
        		{8,4,7, 8,4,8}, {6,4,9, 7,4,9}, {2,4,8, 3,4,8}, {1,4,6, 1,4,7}, {0,4,2, 0,4,3}, {1,4,1, 2,4,1}, {5,4,2, 6,4,2}, {8,4,7, 8,4,8}, 
        		// Door
        		{7,4,3, 7,6,3}, {7,6,4, 7,6,4}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor : 15),
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor : 15), 
        				false);
            }
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
        		{8,5,7}, {8,5,8}, {6,5,9}, {7,5,9}, {2,5,8}, {3,5,8}, {1,5,6}, {1,5,7}, {0,5,2}, {0,5,3}, {1,5,1}, {2,5,1}, {5,5,2}, {6,5,2}, {8,5,7}, {8,5,8}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Glass blocks
        	for (int[] uvw : new int[][]{
        		{3,7,7}, {5,7,7}, 
        		{2,7,6}, {3,7,6}, {5,7,6}, {6,7,6}, 
        		{2,7,4}, {3,7,4}, {5,7,4}, {6,7,4}, 
        		{3,7,3}, {5,7,3}, 
        		})
            {
    			this.placeBlockAtCurrentPosition(world, Blocks.glass, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	            
            // Fences
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
            for (int[] uuvvww : new int[][]{
            	{2,6,5}, {3,6,5}, {4,6,6}, {4,6,7}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
            
            // Brick Walls
        	Block biomeBrickWallBlock = null; int biomeBrickWallMeta = 0;
        	Block biomeBrickBlock = null; int biomeBrickMeta = 0;
        	
        	// First, attempt to obtain modded brick wall
        	blockObject = ModObjects.chooseModBrickWall();
        	if (blockObject==null)
        	{
        		// Use cobblestone
        		biomeBrickWallBlock = Blocks.cobblestone_wall; biomeBrickWallMeta = 0;
        		biomeBrickBlock = Blocks.cobblestone; biomeBrickMeta = 0;
        	}
        	else
        	{
        		biomeBrickWallBlock = (Block) blockObject[0]; biomeBrickWallMeta = (Integer) blockObject[1];
        		biomeBrickBlock = Blocks.brick_block; biomeBrickMeta = 0;
        	}
        	// Convert to biome-specific versions
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(biomeBrickWallBlock, biomeBrickWallMeta, this.materialType, this.biome, this.disallowModSubs);
        	biomeBrickWallBlock = (Block)blockObject[0]; biomeBrickWallMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(biomeBrickBlock, biomeBrickMeta, this.materialType, this.biome, this.disallowModSubs);
        	biomeBrickBlock = (Block)blockObject[0]; biomeBrickMeta = (Integer)blockObject[1];
        	for(int[] uuvvww : new int[][]{
    			// Furnace chimney
    			{4,5,5, 4,6,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeBrickWallBlock, biomeBrickWallMeta, biomeBrickWallBlock, biomeBrickWallMeta, false);	
    		}
            // Brick Blocks
        	for(int[] uuvvww : new int[][]{
    			// Roof plug
    			{4,7,5, 4,7,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeBrickBlock, biomeBrickMeta, biomeBrickBlock, biomeBrickMeta, false);	
    		}
    		
    		
    		// Trapdoor (Bottom Vertical)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapdoor, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{  // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{4,4,6, 3}, {4,4,7, 3}, 
            	{2,4,5, 0}, {3,4,5, 0}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, false, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	{2,6,3}, 
            	{6,6,7}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Furnaces
            for (int[] uvwo : new int[][]{ // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
            	{4,4,5, 2}, 
            	})
            {
                this.placeBlockAtCurrentPosition(world, Blocks.furnace, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2]), StructureVillageVN.chooseFurnaceMeta(uvwo[3], this.coordBaseMode), 2);
            }
        	
        	
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 7;
        	int chestV = 4;
        	int chestW = 7;
        	int chestO = 3; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo("vn_fisher");
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
        	
            
            // Potted random flower
            for (int[] uvw : new int[][]{
        		{1,5,2}, 
        		})
            {
        		int u=uvw[0]; int v=uvw[1]; int w=uvw[2];
                int x = this.getXWithOffset(u, w);
                int y = this.getYWithOffset(v);
                int z = this.getZWithOffset(u, w);
            	
            	Object[] cornflowerObject = ModObjects.chooseModCornflower(); Object[] lilyOfTheValleyObject = ModObjects.chooseModLilyOfTheValley();
        		int randomPottedPlant = random.nextInt(10)-1;
        		if (randomPottedPlant==-1) {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.yellow_flower, 0);} // Dandelion specifically
        		else {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.red_flower, randomPottedPlant);}          // Every other type of flower
            }
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{7,4,4, 1, 1, 1}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
    		
            
            // Leaves
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.leaves, 4, this.materialType, this.biome, this.disallowModSubs); Block biomeLeafBlock = (Block)blockObject[0]; int biomeLeafMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{9,1,1, 9,1,1}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeLeafBlock, biomeLeafMeta,
        				biomeLeafBlock, biomeLeafMeta, 
        				false);
            }
        	
        	
        	// Barrels
    		Block barrelBlock = ModObjects.chooseModBarrelBlock();
    		boolean isChestType=(barrelBlock==null);
    		for (int[] uvwoo : new int[][]{
    			// u, v, w, orientationIfChest, orientationIfUTDBarrel
    			// orientationIfChest:  0=foreward (away from you),  1=rightward,  2=backward (toward you),  3=leftward
    			// orientationIfUTDBarrel: -1=vertical,  0=forward,  1=rightward,  2=backward (toward you),  3=leftward
            	
    			// Underneath
    			{1,1,7, 2,-1}, 
    			{2,2,8, 2,2}, 
    			{2,1,8, 2,1}, {3,1,8, 2,-1}, 
    			{7,1,3, 3,-1}, 
    			// At entrance
    			{8,4,5, 2,-1}, 
            })
            {
    			// Set the barrel, or a chest if it's not supported
    			if (isChestType) {barrelBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];}
    			this.placeBlockAtCurrentPosition(world, barrelBlock, 0, uvwoo[0], uvwoo[1], uvwoo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwoo[0], uvwoo[2]), this.getYWithOffset(uvwoo[1]), this.getZWithOffset(uvwoo[0], uvwoo[2]), isChestType?StructureVillageVN.chooseFurnaceMeta(uvwoo[3], this.coordBaseMode):StructureVillageVN.chooseFurnaceMeta(uvwoo[4], this.coordBaseMode), 2);
            }
    		
    		
        	// Patterned banners
    		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
			if (testForBanner!=null)
			{
    			for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color
    				// 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    				
    				{9,3,5, 1, 15}, // Black
    			})
    			{
        			int bannerU = uvwoc[0];
        			int bannerV = uvwoc[1];
        			int bannerW = uvwoc[2];
        			
        			int bannerX = this.getXWithOffset(bannerU, bannerW);
        			int bannerY = this.getYWithOffset(bannerV);
                    int bannerZ = this.getZWithOffset(bannerU, bannerW);

                    boolean isHanging = true;
                    
                	// Set the banner and its orientation
    				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
    				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(uvwoc[3], this.coordBaseMode, isHanging), 2);
    				
    				// Set the tile entity
    				TileEntity tilebanner = new TileEntityBanner();
    				NBTTagCompound modifystanding = new NBTTagCompound();
    				tilebanner.writeToNBT(modifystanding);
    				modifystanding.setBoolean("IsStanding", !isHanging);
    				
    				if (GeneralConfig.useVillageColors)
    				{
    	            	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, 
    	            			(this.boundingBox.minX+this.boundingBox.maxX)/2,
    	            			(this.boundingBox.minY+this.boundingBox.maxY)/2,
    	            			(this.boundingBox.minZ+this.boundingBox.maxZ)/2);
    					
        				tilebanner.readFromNBT(modifystanding);
        				ItemStack villageBanner = ModObjects.chooseModBannerItem();
        				villageBanner.setTagInfo("BlockEntityTag", villageNBTtag.getCompoundTag("BlockEntityTag"));
        				
            			((TileEntityBanner) tilebanner).setItemValues(villageBanner);
    				}
    				else
    				{
    					modifystanding.setInteger("Base", uvwoc[4]);
        				tilebanner.readFromNBT(modifystanding);
    				}
    				
            		world.setTileEntity(bannerX, bannerY, bannerZ, tilebanner);
    			}
			}
        	
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1: rightward, 2:backward, 3: leftward
        			// Away-facing vines
        			{4,4,9, 0}, {4,3,9, 0}, {4,2,9, 0}, {4,1,9, 0}, 
        			// Player-facing side
        			{4,4,1, 2}, {4,3,1, 2}, 
            		})
                {
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
        			}
                }
        	}
    		
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			// Villager
    			int s = random.nextInt(20);
    			
    			int u;
    			int v = 4;
    			int w;
    			
    			if (s<12) // On the front portion of the building (near the table)
    			{
    				u = 1+(s%6);
    				w = 3+(s/6);
    			}
    			else // On the right portion of the building (near the chest)
    			{
    				u = 5+((s-12)/4);
    				w = 5+((s-12)%4);
    			}
    			
    			
    			EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 0, 2, 0); // Fisherman
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
    			world.spawnEntityInWorld(entityvillager);
    		}
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    // --- Fisher Cottage 2 --- //
    // designed by AstroTibs
    
    public static class SwampFisherCottage2 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"             ",
    			" FFFFFFFFFFF ",
    			" FFFFFFFFFFF ",
    			" FFFFFFFFFFF ",
    			" FFFFFFFFFFF ",
    			" FFFFFFFFFFF ",
    			" FFFFFFFFFFF ",
    			"             ",
    			"             ",
    			"             ",
    			"             ",
    			"  FF         ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 9;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 1;
    	private static final int DECREASE_MAX_U = 7;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampFisherCottage2() {}

    	public SwampFisherCottage2(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampFisherCottage2 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampFisherCottage2(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
    		
    		
    		// Stripped logs (Vertical)
        	Block biomeLogVertBlock = Blocks.log; int biomeLogVertMeta = 0;
    		Block biomeStrippedLogVerticBlock = biomeLogVertBlock; int biomeStrippedLogVerticMeta = biomeLogVertMeta;
    		// Try to see if stripped logs exist
    		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
    		for (int[] uuvvww : new int[][]{
        		// Pier
        		{2,0,8, 2,2,8}, {4,0,8, 4,2,8}, {6,0,8, 6,3,8}, {8,0,8, 8,2,8}, {10,0,8, 10,2,8}, 
        		{2,0,6, 2,2,6}, {4,0,6, 4,5,6}, {6,0,6, 6,7,6}, {8,0,6, 8,5,6}, {10,0,6, 10,2,6}, 
        		{2,1,4, 2,2,4}, 
        		{2,1,2, 2,2,2}, {4,1,2, 4,5,2}, {8,1,2, 8,5,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
    		// Foot foundation
    		for(int[] uvw : new int[][]{
    			// Feet
        		{2,0,4}, 
        		{2,0,2}, {4,0,2}, {8,0,2}, 
    			})
    		{
    			this.func_151554_b(world, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
            
        	// Stripped Log (Across)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2!=0? 4:0), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1];
        	Block biomeStrippedLogHorizAcrossBlock = biomeLogHorAcrossBlock; int biomeStrippedLogHorizAcrossMeta = biomeLogHorAcrossMeta;
        	// Try to see if stripped logs exist
        	if (biomeStrippedLogHorizAcrossBlock==Blocks.log || biomeStrippedLogHorizAcrossBlock==Blocks.log2)
        	{
            	if (biomeLogVertBlock == Blocks.log)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 1+(this.coordBaseMode%2!=0? 1:0)); biomeStrippedLogHorizAcrossBlock = (Block)blockObject[0]; biomeStrippedLogHorizAcrossMeta = (Integer)blockObject[1];
            	}
            	else if (biomeLogVertBlock == Blocks.log2)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 1+(this.coordBaseMode%2!=0? 1:0)); biomeStrippedLogHorizAcrossBlock = (Block)blockObject[0]; biomeStrippedLogHorizAcrossMeta = (Integer)blockObject[1];
            	}
        	}
            for(int[] uuvvww : new int[][]{
            	{5,5,6, 5,5,6}, {7,5,6, 7,5,6}, 
            	{5,5,2, 7,5,2}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogHorizAcrossBlock, biomeStrippedLogHorizAcrossMeta, biomeStrippedLogHorizAcrossBlock, biomeStrippedLogHorizAcrossMeta, false);	
            }
            
            
            // Water boundary
    		// This puts up a dirt/grass wall to keep in the water if it's not level with the surrounding ground
            for(int[] uwuwvs : new int[][]{ // u/w box corners, v height, and the side that is to be evaluated.
            	// Side - 0:forward (away from you), 1:rightward, 2:backward (toward you), 3: leftward
    			
            	{1,10, 11,10, 0, 0}, // Forward side
    			{11,4, 11,11, 0, 1}, // Right side
    			{1,5, 11,5, 0, 2}, // Back side
    			{1,4, 1,11, 0, 3}, // Left side
    			})
    		{
            	int u_offset=0, w_offset=0;
            	int v = uwuwvs[4];
            	
            	switch(uwuwvs[5])
            	{
            	case 0: w_offset=1; break; // forward
            	case 1: u_offset=1; break; // rightward
            	case 2: w_offset=-1; break; // backward
            	case 3: u_offset=-1; break; // leftward
            	default:
            	}
            	
            	// Scan boundary and add containment if necessary
            	for (int u=uwuwvs[0]; u<=uwuwvs[2]; u++) {for (int w=uwuwvs[1]; w<=uwuwvs[3]; w++)
            	{
            		int x = this.getXWithOffset(u+u_offset, w+w_offset);
            		int y = this.getYWithOffset(v);
            		int z = this.getZWithOffset(u+u_offset, w+w_offset);
            		
            		// If space above bordering block is liquid, fill below with filler and cap with topper
            		if (world.getBlock(x, y+1, z).getMaterial().isLiquid())
            		{
            			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u+u_offset, v, w+w_offset, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u+u_offset, v+1, w+w_offset, structureBB);
            		}
            		// If bordering block is air, fill below with filler and cap with topper
            		else if (world.isAirBlock(x, y, z))
            		{
            			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u+u_offset, v-1, w+w_offset, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u+u_offset, v, w+w_offset, structureBB);
            		}
            	}}
    		}
            
            
    		// Water
    		for(int[] uuvvww : new int[][]{
    			{1,0,9, 11,0,10}, 
    			{1,0,8, 1,0,8}, {3,0,8, 3,0,8}, {5,0,8, 5,0,8}, {7,0,8, 7,0,8}, {9,0,8, 9,0,8}, {11,0,8, 11,0,8}, 
    			{1,0,7, 11,0,7}, 
    			{1,0,6, 1,0,6}, {3,0,6, 3,0,6}, {5,0,6, 5,0,6}, {7,0,6, 7,0,6}, {9,0,6, 9,0,6}, {11,0,6, 11,0,6}, 
    			{1,0,5, 11,0,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);	
    		}
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	{2,2,3, 2,2,3}, {2,2,5, 2,2,5}, {2,2,7, 2,2,7}, 
            	{3,2,2, 3,2,8}, 
            	{4,2,3, 4,3,5}, {4,2,7, 4,2,7}, {4,4,3, 4,4,3}, {4,4,5, 4,4,5}, {4,5,3, 4,5,5}, 
            	{5,2,2, 5,2,8}, {5,3,2, 5,3,2}, {5,6,6, 5,6,6}, 
            	{6,2,2, 6,2,5}, {6,2,7, 6,2,7}, {6,3,2, 6,4,2}, {5,6,2, 7,6,2}, {6,7,2, 6,7,2}, 
            	{7,2,2, 7,2,8}, {7,3,2, 7,3,2}, {7,3,6, 7,3,6}, {7,6,6, 7,6,6}, 
            	{8,2,3, 8,3,5}, {8,4,3, 8,4,3}, {8,4,5, 8,4,5}, {8,5,3, 8,5,5}, {8,2,7, 8,2,7}, 
            	{9,2,6, 9,2,8}, 
            	{10,2,7, 10,2,7}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Entrance
    			{2,2,1, 3}, {3,2,1, 3}, 
    			{2,1,0, 3}, {3,1,0, 3}, 
    			// Roof
    			{8,6,2, 1}, {8,6,3, 1}, {8,6,4, 1}, {8,6,5, 1}, {8,6,6, 1}, 
    			{7,7,2, 1}, {7,7,3, 1}, {7,7,4, 1}, {7,7,5, 1}, {7,7,6, 1}, 
    			{5,7,2, 0}, {5,7,3, 0}, {5,7,4, 0}, {5,7,5, 0}, {5,7,6, 0}, 
    			{4,6,2, 0}, {4,6,3, 0}, {4,6,4, 0}, {4,6,5, 0}, {4,6,6, 0}, 
    			// Porch
    			{9,3,7, 2}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Under stairs
    			{2,1,1, 3,1,1}, 
    			// Table
    			{6,3,3, 6,3,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{6,8,2, 6,8,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
        		{5,4,2}, {7,4,2}, 
        		{4,4,4}, {8,4,4}, 
        		{7,4,6}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	            
            // Fences
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
            for (int[] uuvvww : new int[][]{
            	{6,7,4}, {6,6,4}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	{6,5,4}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Sitting Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(false); Block biomeSittingLanternBlock = (Block)blockObject[0]; int biomeSittingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	{6,4,8}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeSittingLanternBlock, biomeSittingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
            // Glazed terracotta
        	Object[] tryGlazedTerracotta;
        	for (int[] uvwoc : new int[][]{ // u,v,w, orientation, dye color
        		// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
        		{5,3,3, 1, GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
        		{7,3,3, 3, GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
           		})
        	{
        		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(uvwoc[4], StructureVillageVN.chooseGlazedTerracottaMeta(uvwoc[3], this.coordBaseMode));
        		if (tryGlazedTerracotta != null)
            	{
        			this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvwoc[0], uvwoc[1], uvwoc[2], structureBB);
            	}
        		else
        		{
        			this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, uvwoc[4], uvwoc[0], uvwoc[1], uvwoc[2], structureBB);
        		}
            }
        	
        	
        	// Potted plant
        	for (int[] uvws : new int[][]{ // u,v,w, sapling
        		{5,4,3, 5}, // Dark Oak
        		{7,4,3, 0}, // Oak
           		})
        	{
            	StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, 
            			this.getXWithOffset(uvws[0], uvws[2]), 
            			this.getYWithOffset(uvws[1]), 
            			this.getZWithOffset(uvws[0], uvws[2]), 
            			Blocks.sapling, uvws[3]);
        	}
            
            
        	// Carpet
        	for(int[] uvwc : new int[][]{
        		// Lower
        		{6,3,4, (GeneralConfig.useVillageColors ? this.townColor4 : 14)}, // Red
        		})
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.carpet, uvwc[3], uvwc[0], uvwc[1], uvwc[2], structureBB);
            }
        	
        	
        	// Barrels
    		Block barrelBlock = ModObjects.chooseModBarrelBlock();
    		boolean isChestType=(barrelBlock==null);
    		for (int[] uvwoo : new int[][]{
    			// u, v, w, orientationIfChest, orientationIfUTDBarrel
    			// orientationIfChest:  0=foreward (away from you),  1=rightward,  2=backward (toward you),  3=leftward
    			// orientationIfUTDBarrel: -1=vertical,  0=forward,  1=rightward,  2=backward (toward you),  3=leftward
            	
    			{8,3,7, 0,-1}, 
            })
            {
    			// Set the barrel, or a chest if it's not supported
    			if (isChestType) {barrelBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];}
    			this.placeBlockAtCurrentPosition(world, barrelBlock, 0, uvwoo[0], uvwoo[1], uvwoo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwoo[0], uvwoo[2]), this.getYWithOffset(uvwoo[1]), this.getZWithOffset(uvwoo[0], uvwoo[2]), isChestType?StructureVillageVN.chooseFurnaceMeta(uvwoo[3], this.coordBaseMode):StructureVillageVN.chooseFurnaceMeta(uvwoo[4], this.coordBaseMode), 2);
            }
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{5,3,6, 0, 1, 0}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
    		
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			// Villager
    			int s = random.nextInt(19);
    			
    			int u;
    			int v = 3;
    			int w;
    			
				u = s<=5? 5+(s%3) : s<=9? 4+((s-6)%2) : s<=10? 6 : s<=12? 7 : s<=15? s-5 : s<=17? 10 : 9;
				w = s<=5? 4+(s/3) : s<=9? 7+((s-6)/2) : s<=10? 7 : s<=12? s-4 : s<=15? 8 : s<=17? s-10 : 6;
    			
    			
    			EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 0, 2, 0); // Fisherman
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
    			world.spawnEntityInWorld(entityvillager);
    		}
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
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
    // designed by THASSELHOFF
    
    public static class SwampFletcherHouse extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"            ",
    			"            ",
    			"            ",
    			"            ",
    			"            ",
    			"            ",
    			"            ",
    			"            ",
    			" F          ",
    			"FF          ",
    			"PFFF        ",
    			"PPF         ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 9;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 8;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 8;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampFletcherHouse() {}

    	public SwampFletcherHouse(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampFletcherHouse buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampFletcherHouse(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
    		
    		
    		// Stripped logs (Vertical)
        	Block biomeLogVertBlock = Blocks.log; int biomeLogVertMeta = 0;
    		Block biomeStrippedLogVerticBlock = biomeLogVertBlock; int biomeStrippedLogVerticMeta = biomeLogVertMeta;
    		// Try to see if stripped logs exist
    		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
    		for (int[] uuvvww : new int[][]{
        		// Entrance
        		{0,1,2, 0,1,2}, {2,1,0, 2,1,0}, 
        		{1,1,3, 1,2,3}, {3,1,1, 3,2,1}, 
        		{2,3,4, 2,3,4}, {4,3,2, 4,3,2}, 
        		{3,4,5, 3,6,5}, {5,4,3, 5,6,3}, 
        		// Supports
        		{6,1,10, 6,2,10}, 
        		{3,1,9, 3,2,9}, {9,1,9, 9,2,9}, 
        		{6,1,7, 6,2,7},
        		{2,1,6, 2,2,6}, {5,1,6, 5,2,6}, {6,1,6, 6,1,6}, {7,1,6, 7,2,6}, {10,1,6, 10,2,6}, 
        		{6,1,5, 6,2,5}, 
        		{3,1,3, 3,2,3}, {9,1,3, 9,2,3}, 
        		{6,1,2, 6,2,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
    		// Foot foundation
    		for(int[] uvw : new int[][]{
        		{6,0,10}, 
        		{3,0,9}, {9,0,9}, 
        		{6,0,7},
        		{2,0,6}, {5,0,6}, {6,0,6}, {7,0,6}, {10,0,6}, 
        		{6,0,5}, 
        		{3,0,3}, {9,0,3}, 
        		{6,0,2}, 
    			})
    		{
    			this.func_151554_b(world, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	{1,3,5, 1,3,7}, 
            	{2,3,8, 2,3,9}, {3,3,9, 3,3,10}, {4,3,10, 4,3,10}, 
            	{5,3,11, 7,3,11}, 
            	{10,3,8, 10,3,9}, {9,3,9, 9,3,10}, {8,3,10, 8,3,10}, 
            	{11,3,5, 11,3,7}, 
            	{8,3,2, 9,3,2}, {9,3,3, 10,3,3}, {10,3,4, 10,3,4}, 
            	{5,3,1, 7,3,1}, 
            	// House supports
            	{7,4,9, 7,6,9}, {9,4,7, 9,6,7}, 
            	// Floor
            	{3,3,5, 3,3,7}, 
            	{4,3,4, 4,3,8}, 
            	{5,3,3, 5,3,9}, 
            	{6,3,3, 6,3,5}, {6,3,7, 6,3,9}, 
            	{7,3,3, 7,3,9}, 
            	{8,3,4, 8,3,8}, 
            	{9,3,5, 9,3,7}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Entryway
    			{2,1,1, 2,1,1}, {1,1,2, 1,1,2}, 
    			// Under main area
    			{1,2,5, 1,2,5}, {1,2,7, 1,2,7}, 
    			{2,2,3, 2,2,5}, {2,2,7, 2,2,9}, 
    			{3,2,2, 3,2,2}, {3,2,4, 3,2,8}, {3,2,10, 3,2,10}, 
    			{4,2,2, 4,2,10}, 
    			{5,2,1, 5,2,5}, {5,2,7, 5,2,11}, 
    			{6,2,3, 6,2,4}, {6,2,8, 6,2,9}, 
    			{7,2,1, 7,2,5}, {7,2,7, 7,2,11}, 
    			{8,2,2, 8,2,10}, 
    			{9,2,2, 9,2,2}, {9,2,4, 9,2,8}, {9,2,10, 9,2,10}, 
    			{10,2,3, 10,2,5}, {10,2,7, 2,2,9}, 
    			{11,2,5, 11,2,5}, {11,2,7, 11,2,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Entryway
    			{1,1,1, 1,1,1}, {2,2,2, 2,2,2}, {3,3,3, 3,3,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Cobblestone Slab (upper)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3+8, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabUpperBlock = (Block)blockObject[0]; int biomeCobblestoneSlabUpperMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{1,6,5, 1,6,6}, 
    			{2,6,4, 2,6,7}, 
    			{3,6,4, 3,6,4}, 
    			{3,6,9, 4,6,10}, 
    			{5,6,10, 5,6,11}, 
    			{6,6,11, 7,6,11}, 
    			{8,6,10, 9,6,10}, 
    			{9,6,9, 9,6,9}, 
    			
    			{4,6,2, 4,6,2}, 
    			{5,6,1, 6,6,2}, {7,6,1, 7,6,1}, 
    			{8,6,2, 9,6,2}, 
    			{9,6,3, 9,6,3}, 
    			{10,6,3, 10,6,7}, {11,6,5, 11,6,7}, 
    			
    			{5,7,7, 5,7,7}, {7,7,7, 7,7,7}, 
    			{5,7,5, 5,7,5}, {7,7,5, 7,7,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneSlabUpperBlock, biomeCobblestoneSlabUpperMeta, biomeCobblestoneSlabUpperBlock, biomeCobblestoneSlabUpperMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone Slab (upper)
    		Block biomeMossyCobblestoneSlabUpperBlock = biomeCobblestoneSlabUpperBlock; int biomeMossyCobblestoneSlabUpperMeta = biomeCobblestoneSlabUpperMeta;
    		blockObject = ModObjects.chooseModMossyCobblestoneSlabBlock(true);
    		if (blockObject != null)
    		{
    			blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs);
    			biomeMossyCobblestoneSlabUpperBlock = (Block)blockObject[0]; biomeMossyCobblestoneSlabUpperMeta = (Integer)blockObject[1];
    		}
    		for(int[] uuvvww : new int[][]{
    			{1,6,7, 1,6,7}, 
    			{2,6,3, 2,6,3}, {2,6,8, 2,6,9}, 
    			{3,6,2, 3,6,3}, {3,6,8, 3,6,8}, 
    			{7,6,2, 7,6,2}, 
    			{6,6,10, 7,6,10}, 
    			{10,6,8, 10,6,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneSlabUpperBlock, biomeMossyCobblestoneSlabUpperMeta, biomeMossyCobblestoneSlabUpperBlock, biomeMossyCobblestoneSlabUpperMeta, false);	
    		}
    		
    		
    		// Cobblestone Slab (lower)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabLowerBlock = (Block)blockObject[0]; int biomeCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{3,7,5, 3,7,8}, 
    			{4,7,3, 4,7,4}, {4,7,8, 4,7,9}, 
    			{5,7,3, 6,7,3}, {5,7,9, 8,7,9}, 
    			{8,7,4, 8,7,4}, {9,7,4, 9,7,8}, {8,7,8, 8,7,8}, 
    			
    			{6,8,5, 6,8,5}, {5,8,6, 7,8,6}, {6,8,7, 6,8,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone Slab (lower)
    		Block biomeMossyCobblestoneSlabLowerBlock = biomeCobblestoneSlabLowerBlock; int biomeMossyCobblestoneSlabLowerMeta = biomeCobblestoneSlabLowerMeta;
    		blockObject = ModObjects.chooseModMossyCobblestoneSlabBlock(false);
    		if (blockObject != null)
    		{
    			blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs);
    			biomeMossyCobblestoneSlabLowerBlock = (Block)blockObject[0]; biomeMossyCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		}
    		for(int[] uuvvww : new int[][]{
    			{3,7,4, 3,7,4}, 
    			{7,7,3, 8,7,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{4,7,7, 5,7,7}, 
    			{5,7,8, 5,7,8}, 
    			{7,7,7, 7,7,8}, {7,7,4, 7,7,5}, 
    			{8,7,5, 8,7,7}, 
    			{5,7,4, 5,7,5}, {6,7,4, 6,7,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.mossy_cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneBlock = (Block)blockObject[0]; int biomeMossyCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{4,7,5, 4,7,6}, 
    			{6,7,8, 6,7,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, false);	
    		}
        	
        	
        	// Cobblestone wall
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{6,7,6, 6,7,6}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, false);
            }
            
            
            // Glass blocks
        	for (int[] uuvvww : new int[][]{
        		{3,4,6, 3,6,7}, {4,4,8, 4,6,8}, {5,4,9, 6,6,9}, 
        		{6,4,3, 7,6,3}, {8,4,4, 8,6,4}, {9,4,5, 9,6,6}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.glass, 0, Blocks.glass, 0, false);
            }
    		
    		
    		// Trapdoor (Bottom Vertical)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapdoor, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{8,4,9, 0}, {8,5,9, 0}, 
            	{9,4,8, 1}, {9,5,8, 1}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, false, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
    		
    		
    		// Trapdoor (Top Vertical)
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{8,6,9, 0}, 
            	{9,6,8, 1}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, true, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	{6,6,6}, 
            	{6,2,1}, 
            	{1,2,6}, {11,2,6}, 
            	{6,2,11}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Sitting Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(false); Block biomeSittingLanternBlock = (Block)blockObject[0]; int biomeSittingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	{1,3,3}, {3,3,1}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeSittingLanternBlock, biomeSittingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
        	// Carpet
        	for(int[] uuvvww : new int[][]{
        		{4,4,5, 4,4,7, (GeneralConfig.useVillageColors ? this.townColor4 : 14)}, // Red
        		{5,4,4, 7,4,4, (GeneralConfig.useVillageColors ? this.townColor4 : 14)}, // Red
        		{8,4,5, 8,4,7, (GeneralConfig.useVillageColors ? this.townColor4 : 14)}, // Red
        		{5,4,8, 7,4,8, (GeneralConfig.useVillageColors ? this.townColor4 : 14)}, // Red
        		})
            {
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.carpet, uuvvww[6], Blocks.carpet, uuvvww[6], false);	
            }
        	
        	
            // Glazed terracotta
        	Object[] tryGlazedTerracotta;
        	for (int[] uvwoc : new int[][]{ // u,v,w, orientation, dye color
        		// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
        		{5,3,2, 0, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
        		{6,3,2, 1, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
        		{7,3,2, 2, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
        		{8,3,3, 3, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
        		{9,3,4, 0, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
        		{10,3,5, 1, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
        		{10,3,6, 2, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
        		{10,3,7, 3, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
        		{9,3,8, 0, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
        		{8,3,9, 1, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
        		{7,3,10, 2, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
        		{6,3,10, 3, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
        		{5,3,10, 0, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
        		{4,3,9, 1, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
        		{3,3,8, 2, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
        		{2,3,7, 3, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
        		{2,3,6, 0, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
        		{2,3,5, 1, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
        		{3,3,4, 2, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
        		{4,3,3, 3, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
           		})
        	{
        		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(uvwoc[4], StructureVillageVN.chooseGlazedTerracottaMeta(uvwoc[3], this.coordBaseMode));
        		if (tryGlazedTerracotta != null)
            	{
        			this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvwoc[0], uvwoc[1], uvwoc[2], structureBB);
            	}
        		else
        		{
        			this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, uvwoc[4], uvwoc[0], uvwoc[1], uvwoc[2], structureBB);
        		}
            }

            
            // Fletching Table
        	blockObject = ModObjects.chooseModFletchingTable(biomePlankMeta); Block fletchingTableBlock = (Block) blockObject[0]; int fletchingTableMeta = (Integer) blockObject[1];
        	this.placeBlockAtCurrentPosition(world, fletchingTableBlock, fletchingTableMeta, 8, 4, 8, structureBB);
    		
            
        	// Carpet
        	for(int[] uvwc : new int[][]{
        		// Lower
        		{6,3,4, (GeneralConfig.useVillageColors ? this.townColor4 : 14)}, // Red
        		})
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.carpet, uvwc[3], uvwc[0], uvwc[1], uvwc[2], structureBB);
            }
        	
        	
        	// Water
        	for (int[] uvw : new int[][]{
            	{6,2,6}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.flowing_water, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// TNT
        	for (int[] uvw : new int[][]{
            	{6,3,6}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.tnt, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 6;
        	int chestV = 4;
        	int chestW = 6;
        	int chestO = 0; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapped_chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo("vn_fletcher");
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
        	
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1: rightward, 2:backward, 3: leftward
        			// Right side
        			{10,1,3, 1}, {11,3,3, 1}, {11,3,8, 1}, {11,3,9, 1}, 
        			// Player-facing side
        			{9,3,1, 2}, 
            		})
                {
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
        			}
                }
        	}
    		
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			// Villager
    			int s = random.nextInt(20);
    			
    			int u;
    			int v = 4;
    			int w;
    			
				u = s<=2? 4 : s<=7? 5 : s<=11? 6 : s<=16? 7 : 8;
				w = s<=2? s+5 : s<=7? s+1 : s<=9? s-4 : s<=11? s-3 : s<=16? s-8 : s-12;
    			
    			
    			EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 0, 4, 0); // Fletcher
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
    			world.spawnEntityInWorld(entityvillager);
    		}
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    // --- Horrible Secret --- //
    // designed by AstroTibs
    
    public static class SwampHorribleSecret extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    	
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = 8;//foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = 8;//foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 28;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 22; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 1;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampHorribleSecret() {}

    	public SwampHorribleSecret(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampHorribleSecret buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampHorribleSecret(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel <= this.STRUCTURE_HEIGHT+5) {return true;} // Do not construct if ground level is too low
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
            
            // Grass
            for(int[] uuvvww : new int[][]{
    			{3,21,6, 5,21,6}, 
    			{2,21,5, 6,21,5}, 
    			{1,21,3, 7,21,4}, 
    			{1,21,2, 2,21,2}, {6,21,2, 7,21,2}, 
    			{2,21,1, 6,21,1}, 
    			{3,21,0, 5,21,0}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);	
            }

    		        	
        	// Clear out underground area
            for(int[] uuvvww : new int[][]{
    			{3,1,0, 5,20,0}, 
    			{0,1,1, 6,9,7}, 
    			{7,1,2, 7,20,4}, 
    			{2,10,1, 6,20,1}, 
    			{1,10,2, 7,20,4}, 
    			{2,10,5, 6,20,5}, 
    			{3,10,6, 5,20,6}, 
    			})
    		{
            	this.fillWithAir(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5]);	
    		}
            
            
            // Coarse Dirt
            for(int[] uuvvww : new int[][]{
            	// Covered up hole in the ground
            	{3,21,2, 5,21,2}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.dirt, 1, Blocks.dirt, 1, false);	
            }
            
            
            // Dirt
            for(int[] uuvvww : new int[][]{
            	// Dirt top
    			{3,19,6, 5,20,6}, 
    			{2,19,5, 6,20,5}, 
    			{1,19,2, 2,20,4}, {6,19,2, 7,20,4}, 
    			{2,19,1, 6,20,1}, 
    			{3,19,0, 5,20,0}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);	
            }
    		
    		
    		// Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Central column
    			{3,0,0, 5,18,0}, // Front wall
    			{2,10,1, 6,18,1}, 
    			{1,10,2, 1,18,4}, {2,10,2, 2,18,5}, 
    			{7,0,2, 7,18,4}, {6,10,2, 6,18,5}, 
    			{3,10,5, 5,18,6}, 
    			{0,0,2, 2,9,2}, 
    			{0,0,1, 0,9,7}, 
    			{1,0,3, 1,9,3}, 
    			{1,0,4, 1,1,7}, {1,2,5, 1,2,7}, {1,3,4, 1,5,7}, 
    			{3,0,2, 6,0,2}, 
    			{4,1,2, 6,2,2}, 
    			{3,3,2, 6,6,2},
    			{2,7,3, 2,9,3}, 
    			{6,7,2, 6,9,7}, {6,6,3, 6,6,3}, {6,6,5, 6,6,7}, 
    			{6,0,3, 6,5,7}, 
    			{1,6,7, 5,9,7}, 
    			{1,9,4, 2,9,6}, {3,9,5, 5,9,6}, {5,9,4, 5,9,4}, 
    			{3,7,2, 3,7,2}, 
    			{3,8,4, 3,8,4}, {5,10,2, 5,10,2}, 
    			{3,11,2, 3,11,2}, {3,12,4, 3,12,4}, {5,13,4, 5,13,4}, 
    			{5,14,2, 5,14,2}, {3,15,2, 3,15,2}, 
    			{3,16,4, 3,16,4}, {5,17,4, 5,17,4}, {5,18,2, 5,18,2}, 
    			// Stairwell center
    			{4,7,3, 4,20,3}, 
    			// Basement chamber
    			{1,0,1, 6,9,1}, 
    			{2,0,7, 5,5,7}, 
    			{2,0,6, 2,5,6}, {3,0,6, 3,2,6}, {3,4,6, 3,5,6}, {4,0,6, 5,5,6}, 
    			{5,0,3, 5,5,3}, {5,0,4, 5,3,4}, {5,5,4, 5,5,4}, {5,0,5, 5,5,5}, 
    			{2,0,3, 4,0,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone
    		// Replaces regular stone because I lost my mind trying to set the coordinates
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.mossy_cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneBlock = (Block)blockObject[0]; int biomeMossyCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uvw : new int[][]{
    			// Tower
    			{3,18,6}, {4,18,6}, {5,18,6}, 
    			{2,18,5}, {3,18,5}, {4,18,5}, {5,18,5}, {6,18,5}, 
    			{1,18,4}, {2,18,4}, {6,18,4}, {7,18,4}, 
    			{1,18,3}, {2,18,3}, {6,18,3}, {7,18,3}, 
    			{1,18,2}, {2,18,2}, {6,18,2}, {7,18,2}, 
    			{2,18,1}, {3,18,1}, {4,18,1}, {5,18,1}, {6,18,1}, 
    			{3,18,0}, {4,18,0}, {5,18,0}, 
    			
    			{3,17,6}, {4,17,6}, {5,17,6}, 
    			{2,17,5}, {3,17,5}, {4,17,5}, {5,17,5}, {6,17,5}, 
    			{1,17,4}, {2,17,4}, {6,17,4}, {7,17,4}, 
    			{1,17,2}, {2,17,2}, {6,17,2}, {7,17,2}, 
    			{2,17,1}, {3,17,1}, {4,17,1}, {5,17,1}, {6,17,1}, 
    			{3,17,0}, {4,17,0}, {5,17,0}, 
    			
    			{3,16,6}, {5,16,6}, 
    			{2,16,5}, {3,16,5}, {5,16,5}, {6,16,5}, 
    			{1,16,4}, {2,16,4}, 
    			{1,16,3}, {2,16,3}, {6,16,3}, {7,16,3}, 
    			{6,16,2}, {7,16,2}, 
    			{2,16,1}, {3,16,1}, {4,16,1}, {6,16,1}, 
    			{3,16,0}, {4,16,0}, 
    			
    			{3,18,6}, {4,15,6}, 
    			{2,15,5}, {3,15,5}, {4,15,5}, {6,15,5}, 
    			{6,15,4}, {7,15,4}, 
    			{1,15,3}, {2,15,3}, {6,15,3}, {7,15,3}, 
    			{1,15,2}, {2,15,2}, {6,15,2}, {7,15,2}, 
    			{3,15,1}, {4,15,1}, {5,15,1}, 
    			{3,15,0}, {4,15,0}, {5,15,0}, 
    			
    			{4,14,6}, {5,14,6}, 
    			{2,14,5}, {4,14,5}, {5,14,5}, 
    			{6,14,4}, {7,14,4}, 
    			{1,14,3}, {2,14,3}, 
    			{6,14,2}, {7,14,2}, 
    			{2,14,1}, {4,14,1}, {6,14,1}, 
    			{4,14,0}, 
    			
    			{5,13,6}, 
    			{2,13,5}, {5,13,5}, 
    			{1,13,4}, {2,13,4}, {6,13,4}, {7,13,4}, 
    			{1,13,2}, {2,13,2}, {6,13,2}, {7,13,2}, 
    			{3,13,1}, {6,13,1}, 
    			{3,13,0}, 
    			
    			{3,12,6}, 
    			{3,12,5}, {6,12,5}, 
    			{1,12,4}, {2,12,4}, 
    			{6,12,2}, {7,12,2}, 
    			{2,12,1}, {5,12,1}, 
    			{5,12,0}, 
    			
    			{3,11,6}, 
    			{3,11,5}, {6,11,5}, 
    			{1,11,3}, {2,11,3}, 
    			{6,11,2}, {7,11,2}, 
    			{3,11,1}, 
    			{3,11,0}, 
    			
    			// Chamber
    			
    			{4,10,6}, 
    			{4,10,5}, 
    			{1,10,4}, {2,10,4}, {6,10,4}, {7,10,4}, 
    			{3,10,1}, {5,10,1}, 
    			{3,10,0}, {5,10,0}, 
    			
    			{6,9,2}, {7,9,2}, 
    			{2,8,7}, {4,8,7}, {6,8,6}, 
    			{1,6,3}, {0,6,6}, {1,6,7}, {5,6,7}, 
    			{1,5,6}, {2,5,6}, 
    			
    			{2,0,3}, {4,0,3}, 
    			
    			// Center column
    			{4,18,3}, 
    			{4,16,3}, 
    			{4,12,3}, 
    			{4,7,3}, 
    			})
    		{
            	this.placeBlockAtCurrentPosition(world, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
            
            // Cobblestone stairs
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
        	for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			{3,20,2, 1}, 
    			{4,19,2, 1}, 
    			{5,18,3, 2}, 
    			{4,17,4, 0}, 
    			{3,16,3, 3}, 
    			{4,15,2, 1},  
    			{5,14,3, 2}, 
    			{4,13,4, 0}, 
    			{3,12,3, 3}, 
    			{4,11,2, 1},  
    			{5,10,3, 2}, 
    			{4,9,4, 0}, 
    			{3,8,3, 3}, 
    			{4,7,2, 1}, 
    			{5,6,3, 2}, 
    			
    			{4,16,4, 1+4}, 
    			{4,14,2, 0+4},  
    			{5,13,3, 3+4}, 
    			{3,11,3, 2+4}, 
    			{4,10,2, 0+4},  
    			{5,9,3, 3+4}, 
    			{4,8,4, 1+4}, 
    			{3,7,3, 2+4}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
        	
        	
    		// Mossy Cobblestone stairs
    		Block modblock = ModObjects.chooseModMossyCobblestoneStairsBlock();
    		if (modblock==null) {modblock = Blocks.stone_stairs;}
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(modblock, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			{3,19,2, 0+4}, 
    			{4,18,2, 0+4}, 
    			{5,17,3, 3+4}, 
    			{3,15,3, 2+4}, 
    			{4,12,4, 1+4},
    			
    			{2,8,4, 0+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeMossyCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// On the central column
    			{4,19,4, 0}, 
    			{4,17,2, 2}, 
    			{4,15,4, 0}, 
    			{4,13,2, 2}, 
    			{4,11,4, 0}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		
    		// Redstone Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// Walls of the chamber
    			{1,7,5, 1}, 
    			{3,7,6, 2}, 
    			{5,7,5, 3}, 
    			// Walls of the pit
    			{5,3,4, 3}, 
    			{3,3,6, 2}, 
    			{1,2,4, 1}, 
    			{3,1,2, 0}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.redstone_torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
        	
        	
            // Iron bars
            for(int[] uuvvww : new int[][]{
            	{2,1,5, 4,5,5}, 
            	{2,1,4, 2,5,4}, {4,1,4, 4,5,4}, 
            	{2,1,3, 4,5,3}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.iron_bars, 0, Blocks.iron_bars, 0, false);
            }
        	
        	
        	// Dispenser
            for (int[] uvwo : new int[][]{ // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
            	{6,6,4, 3}, 
            	})
            {
            	int x = this.getXWithOffset(uvwo[0], uvwo[2]);
            	int y = this.getYWithOffset(uvwo[1]);
            	int z = this.getZWithOffset(uvwo[0], uvwo[2]);
            	
                // Set contents
                if (structureBB.isVecInside(x, y, z) && world.getBlock(x, y, z) != Blocks.dispenser)
                {
                	this.placeBlockAtCurrentPosition(world, Blocks.dispenser, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);
                	world.setBlockMetadataWithNotify(x, y, z, StructureVillageVN.chooseFurnaceMeta(uvwo[3], this.coordBaseMode), 2);
                	
                    TileEntityDispenser tileentitydispenser = (TileEntityDispenser)world.getTileEntity(x, y, z);

                    if (tileentitydispenser != null)
                    {
                    	
                    	// Potion IDs taken from https://www.minecraftinfo.com/IDList.htm
                    	int potionID;

                    	int number_of_potions = 2; // How many attempts to add potions to a slot. Sometimes there's slot overlap
                    	for (int i=0; i<number_of_potions; i++)
                    	{
                        	switch(random.nextInt(5))
                        	{
                        	default:
                        	case 0: potionID = 16385; break; // Splash potion of Regeneration (33 sec)
                        	case 1: potionID = 16389; break; // Splash potion of Healing
                        	case 2: potionID = 16392; break; // Splash potion of Weakness (1m07s)
                        	case 3: potionID = 16394; break; // Splash potion of Slowness (1m07s)
                        	case 4: potionID = 16396; break; // Splash potion of Harming
                        	}
                        	
                        	ItemStack dispenserItem = new ItemStack(Items.potionitem, 1, potionID);
                        	
                        	tileentitydispenser.setInventorySlotContents(random.nextInt(tileentitydispenser.getSizeInventory()), dispenserItem);
                    	}
                    }
                }
            }
            
                    	
            // Stone pressure plate
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_pressure_plate, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodPressurePlateBlock = (Block)blockObject[0]; int biomeWoodPressurePlateMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		{5,6,4, 5,6,4}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeWoodPressurePlateBlock, biomeWoodPressurePlateMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
            // Decor
            int[][] decorUVW = new int[][]{
            	{4,22,1}, 
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
            	
            	// Generate decor
            	ArrayList<BlueprintData> decorBlueprint = StructureVillageVN.getRandomDecorBlueprint(this.villageType, this.materialType, this.disallowModSubs, this.biome, this.coordBaseMode, randomFromXYZ, VillageGeneratorConfigHandler.allowTaigaTroughs && !VillageGeneratorConfigHandler.restrictTaigaTroughs);
            	
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
        	
        	
    		// Entities
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	// Spawn Zombie
                int u = 3;
                int v = 1;
                int w = 4;
                int x = this.getXWithOffset(u, w);
                int y = this.getYWithOffset(v);
                int z = this.getZWithOffset(u, w);
            	
                EntityZombie zombie = new EntityZombie(world);
                zombie.heal(zombie.getMaxHealth());
                zombie.setLocationAndAngles(
                		(double)x + 0.5D,
                		(double)y + 0.5D,
                		(double)z + 0.5D,
                		random.nextFloat()*360, 0.0F);
                zombie.func_110163_bv(); //.enablePersistence() in 1.8
                zombie.setVillager(true);
                
                if (GeneralConfig.nameEntities)
            	{
            		String[] villager_name_a = NameGenerator.newRandomName("villager", random);
            		zombie.setCustomNameTag((villager_name_a[1]+" "+villager_name_a[2]+" "+villager_name_a[3]).trim());
            	}
                
                //if(GeneralConfig.modernZombieSkins) (ExtendedZombieVillager.get( zombie )).setProfession(2); // v3.2.3
                                                
                world.spawnEntityInWorld(zombie);
            }
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    // --- Hut Farm --- //
    // designed by THASSELHOFF
    
    public static class SwampHutFarm extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"     FFFFFFFF",
    			"     FFFFFFFF",
    			" FFFFFFFFFFFF",
    			" FFFFFFFFFFFF",
    			" FFFFFFFFFFFF",
    			" FFFFFFFFFFFF",
    			" FFFFFFFFFFFF",
    			"   F FFFFFFFF",
    			"   F FFFFFFFF",
    			"   F         ",
    			"   F         ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 8;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 7;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampHutFarm() {}

    	public SwampHutFarm(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampHutFarm buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampHutFarm(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
    		
    		
    		// Stripped logs (Vertical)
        	Block biomeLogVertBlock = Blocks.log; int biomeLogVertMeta = 0;
    		Block biomeStrippedLogVerticBlock = biomeLogVertBlock; int biomeStrippedLogVerticMeta = biomeLogVertMeta;
    		// Try to see if stripped logs exist
    		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
    		for (int[] uuvvww : new int[][]{
        		{1,1,8, 1,5,8}, {5,1,8, 5,5,8}, 
        		{1,1,4, 1,5,4}, {5,1,4, 5,5,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	// Back wall
            	{2,4,8, 2,5,8}, {3,4,8, 3,4,8}, {4,4,8, 4,5,8}, 
            	// Left wall
            	{1,4,5, 1,5,5}, {1,4,6, 1,4,6}, {1,4,7, 1,5,7}, 
            	// Right wall
            	{5,4,5, 5,5,5}, {5,4,6, 5,4,6}, {5,4,7, 5,5,7}, 
            	// Front
            	{2,4,4, 2,5,4}, {4,4,4, 4,5,4}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Entrance
    			{3,1,1, 3}, {3,2,2, 3}, {3,3,3, 3}, 
    			{3,1,2, 2+4}, {3,2,3, 2+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{2,3,8, 2,3,8}, {4,3,8, 4,3,8}, 
    			{1,3,7, 5,3,7}, 
    			{2,3,6, 4,3,6}, 
    			{1,3,5, 5,3,5}, 
    			{2,3,4, 4,3,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
        	
        	            
            // Fences
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
            for (int[] uuvvww : new int[][]{
            	{3,5,8}, 
            	{1,5,6}, {5,5,6}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
    		
    		
    		// Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{1,0,4, 1,0,8}, 
    			{2,0,4, 2,0,4}, {2,0,8, 2,0,8}, 
    			{3,0,0, 3,0,3}, 
    			{5,0,2, 5,0,4}, {4,0,9, 5,0,10}, 
    			{6,0,8, 6,0,8}, 
    			{7,0,5, 7,0,5}, {7,0,7, 7,0,7}, 
    			{9,0,5, 9,0,5}, 
    			{11,0,3, 11,0,3}, {11,0,9, 11,0,9}, 
    			{9,0,10, 10,0,10}, 
    			{8,0,2, 12,0,2}, 
    			{12,0,2, 12,0,2}, {12,0,6, 12,0,7}, 
    			// Hut roof
    			{1,6,8, 4,6,8}, 
    			{1,6,6, 1,6,7}, {5,6,5, 5,6,7}, 
    			{2,6,4, 4,6,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.mossy_cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneBlock = (Block)blockObject[0]; int biomeMossyCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{3,0,4, 4,0,4}, {3,0,8, 4,0,8}, 
    			{6,0,2, 7,0,2}, {6,0,4, 6,0,4}, {6,0,10, 8,0,10}, 
    			{7,0,7, 7,0,7}, {8,0,8, 8,0,8}, {8,0,4, 8,0,4}, 
    			{9,0,7, 9,0,7}, 
    			{10,0,3, 10,0,5}, {10,0,8, 10,0,10}, 
    			{11,0,10, 11,0,10}, 
    			{12,0,3, 12,0,5}, {12,0,8, 12,0,10}, 
    			// Hut roof
    			{5,6,4, 5,6,4}, {5,6,8, 5,6,8}, 
    			{1,6,4, 1,6,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, false);	
    		}
    		
    		
    		// Cobblestone Slab (lower)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabLowerBlock = (Block)blockObject[0]; int biomeCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{0,6,9, 4,6,9}, 
    			{0,6,6, 0,6,8}, 
    			{6,6,4, 6,6,4}, {6,6,7, 6,6,8}, 
    			{1,6,3, 6,6,3}, 
    			{3,7,5, 4,7,5}, {4,7,6, 4,7,6}, {2,7,7, 4,7,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone Slab (lower)
    		Block biomeMossyCobblestoneSlabLowerBlock = biomeCobblestoneSlabLowerBlock; int biomeMossyCobblestoneSlabLowerMeta = biomeCobblestoneSlabLowerMeta;
    		blockObject = ModObjects.chooseModMossyCobblestoneSlabBlock(false);
    		if (blockObject != null)
    		{
    			blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs);
    			biomeMossyCobblestoneSlabLowerBlock = (Block)blockObject[0]; biomeMossyCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		}
    		for(int[] uuvvww : new int[][]{
    			{5,6,9, 6,6,9}, 
    			{0,6,3, 0,6,5}, {6,6,5, 6,6,6}, 
    			
    			{2,7,5, 2,7,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone Slab (upper)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3+8, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabUpperBlock = (Block)blockObject[0]; int biomeCobblestoneSlabUpperMeta = (Integer)blockObject[1];
    		Block biomeMossyCobblestoneSlabUpperBlock = biomeCobblestoneSlabUpperBlock; int biomeMossyCobblestoneSlabUpperMeta = biomeCobblestoneSlabUpperMeta;
    		blockObject = ModObjects.chooseModMossyCobblestoneSlabBlock(true);
    		if (blockObject != null)
    		{
    			blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs);
    			biomeMossyCobblestoneSlabUpperBlock = (Block)blockObject[0]; biomeMossyCobblestoneSlabUpperMeta = (Integer)blockObject[1];
    		}
    		for(int[] uuvvww : new int[][]{
    			{3,7,6, 3,7,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneSlabUpperBlock, biomeMossyCobblestoneSlabUpperMeta, biomeMossyCobblestoneSlabUpperBlock, biomeMossyCobblestoneSlabUpperMeta, false);	
    		}
    		
            
    		// Water
    		for(int[] uuvvww : new int[][]{
    			// Under hut
    			{3,0,5, 3,0,7}, 
    			// Center of patch
    			{8,0,6, 8,0,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);	
    		}
    		
            
            // Moist Farmland with crop above
            Block[] cropPair1 = StructureVillageVN.chooseCropPair(random);
        	Block[] cropPair2 = StructureVillageVN.chooseCropPair(random);
        	for(int[] uvwfcp : new int[][]{
            	// u,v,w, farmland moisture (0:dry, 7:moist), crop, crop progress
            	// Left patch
            	{6,0,5, 7,0,random.nextInt(3)}, {6,0,6, 7,0,random.nextInt(3)}, {6,0,7, 7,0,random.nextInt(3)}, 
            	{7,0,6, 7,0,random.nextInt(3)}, 
            	// Right patch
            	{9,0,6, 7,1,random.nextInt(3)}, 
            	{10,0,5, 7,1,random.nextInt(3)}, {10,0,6, 7,1,random.nextInt(3)}, {10,0,7, 7,1,random.nextInt(3)}, 
            	{11,0,4, 7,1,random.nextInt(3)}, {11,0,5, 7,1,random.nextInt(3)}, {11,0,6, 7,1,random.nextInt(3)}, {11,0,7, 7,1,random.nextInt(3)}, {11,0,8, 7,1,random.nextInt(3)}, 
            	// Front patch
            	{8,0,5, 7,2,random.nextInt(3)}, 
            	{7,0,4, 7,2,random.nextInt(3)}, {8,0,4, 7,2,random.nextInt(3)}, {9,0,4, 7,2,random.nextInt(3)}, 
            	{6,0,3, 7,2,random.nextInt(3)}, {7,0,3, 7,2,random.nextInt(3)}, {8,0,3, 7,2,random.nextInt(3)}, {9,0,3, 7,2,random.nextInt(3)}, {10,0,3, 7,2,random.nextInt(3)}, 
            	// Back patch
            	{8,0,7, 7,3,random.nextInt(3)}, 
            	{7,0,8, 7,3,random.nextInt(3)}, {8,0,8, 7,3,random.nextInt(3)}, {9,0,8, 7,3,random.nextInt(3)}, 
            	{6,0,9, 7,3,random.nextInt(3)}, {7,0,9, 7,3,random.nextInt(3)}, {8,0,9, 7,3,random.nextInt(3)}, {9,0,9, 7,3,random.nextInt(3)}, {10,0,9, 7,3,random.nextInt(3)}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.farmland, uvwfcp[3], uvwfcp[0], uvwfcp[1], uvwfcp[2], structureBB);
            	this.placeBlockAtCurrentPosition(world, (uvwfcp[4]/2==0?cropPair1:cropPair2)[uvwfcp[4]%2], uvwfcp[5], uvwfcp[0], uvwfcp[1]+1, uvwfcp[2], structureBB);
            }
    		
    		
    		// Lilypad
    		for (int[] uvw : new int[][]{
    			// Under hut
    			{3,1,5}, {3,1,7}, 
    			// Center of patch
    			{8,1,6}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.waterlily, 0, uvw[0], uvw[1], uvw[2], structureBB);
    		}
            
            
            // Dirt
            for(int[] uuvvww : new int[][]{
    			{2,0,5, 2,0,7}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);	
            }
            
            
            // Sugarcane
    		for(int[] uuvvww : new int[][]{
    			{2,1,5, 2,1,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.reeds, 0, Blocks.reeds, 0, false);	
    		}
            
            
            // Pumpkin with dirt underneath
            for(int[] uvw : new int[][]{
            	{4,1,5}, {4,1,6}, {4,1,7}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.pumpkin, random.nextInt(3), uvw[0], uvw[1], uvw[2], structureBB); // Random pumpkin orientation
            	this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, uvw[0], uvw[1]-1, uvw[2], structureBB); 
            }
            
            
            // Pumpkin stem with farmland underneath
            for(int[] uvw : new int[][]{
            	{5,1,5}, {5,1,6}, {5,1,7}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.farmland, 7, uvw[0], uvw[1]-1, uvw[2], structureBB);
            	this.placeBlockAtCurrentPosition(world, Blocks.pumpkin_stem, 7, uvw[0], uvw[1], uvw[2], structureBB); // Random pumpkin orientation
            }
    		
    		
    		// Attempt to add GardenCore Compost Bins. If this fails, nothing
            Block compostBin = ModObjects.chooseModComposterBlock();
            if (compostBin!=null)
            {
            	for(int[] uvw : new int[][]{
        			{5,1,2}, 
        			})
        		{
        			this.placeBlockAtCurrentPosition(world, compostBin, 0, uvw[0], uvw[1], uvw[2], structureBB);	
        		}
            }
            
            
            // Hay bales (vertical)
        	for (int[] uvw : new int[][]{
        		{2,4,6}, {2,4,7}, {2,5,7}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.hay_block, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Hay bales (along)
        	for (int[] uvw : new int[][]{
        		{3,4,7}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.hay_block, 4+(this.coordBaseMode%2==0? 0:4), uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			{3,6,5, 0}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		{3,3,8}, 
        		{1,3,6}, {5,3,6}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 4;
        	int chestV = 4;
        	int chestW = 5;
        	int chestO = 3; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo("vn_farm");
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
        	
        	
            // Decor
            int[][] decorUVW = new int[][]{
            	{4,1,10}, {12,1,10}, 
            	{12,1,2}, 
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
            	
            	// Generate decor
            	ArrayList<BlueprintData> decorBlueprint = StructureVillageVN.getRandomDecorBlueprint(this.villageType, this.materialType, this.disallowModSubs, this.biome, this.coordBaseMode, randomFromXYZ, VillageGeneratorConfigHandler.allowTaigaTroughs && !VillageGeneratorConfigHandler.restrictTaigaTroughs);
            	
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
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			int u = 8;
    			int v = 1;
    			int w = 6;
    			
    			while (
    					(u==8 && w==6)
    					|| (u>=11 && w>=9)
    					|| (u>=11 && w<=3)
    					)
    			{
    				u = 6+random.nextInt(7);
    				w = 2+random.nextInt(9);
    			}
    			
    			EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 0, 1, 0); // Farmer
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
    			world.spawnEntityInWorld(entityvillager);
    		}
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    // --- Large House --- //
    // designed by AstroTibs
    
    public static class SwampLargeHouse extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"         ",
    			" FF    FF",
    			"  F   FF ",
    			"   FFF   ",
    			"   FFF   ",
    			"   FFF   ",
    			"  FFFFFF ",
    			" FFFFFF F",
    			" F FFFF  ",
    			"F FFFFFF ",
    			"   FFFF  ",
    			"    FF   ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 14;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 2;
    	private static final int DECREASE_MAX_U = 1;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampLargeHouse() {}

    	public SwampLargeHouse(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampLargeHouse buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampLargeHouse(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
    		
    		
    		// Logs (Vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Outside roots
    			{2,0,9, 2,1,9}, {6,0,9, 6,1,9}, {7,0,9, 7,0,9}, 
    			{2,0,5, 2,1,5}, {6,0,5, 6,1,5}, 
    			// Main trunk
    			{3,0,6, 3,5,8}, {4,0,6, 4,2,7}, {4,0,8, 4,8,8}, {5,0,6, 5,5,8}, 
    			{4,0,8, 4,2,8}, {4,5,6, 4,5,6}, 
    			// Ceiling
    			{3,13,6, 5,13,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);	
    		}
    		
    		
    		// Logs (Across)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Outside roots
    			{1,0,10, 1,0,10}, {8,0,10, 8,0,10}, 
    			{7,0,5, 7,0,5}, {8,0,4, 8,0,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, false);	
    		}
    		
    		
    		// Logs (Along)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Longer log
    			{2,0,10, 2,0,10}, {7,0,10, 7,0,10}, 
    			{0,0,2, 0,0,2}, {1,0,3, 1,0,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAlongBlock, biomeLogHorAlongMeta, biomeLogHorAlongBlock, biomeLogHorAlongMeta, false);	
    		}
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	// Front wall
            	{3,6,5, 3,11,5}, {4,8,5, 4,8,5}, {4,11,5, 4,11,5}, {5,6,5, 5,11,5}, 
            	// Left wall
            	{2,6,6, 2,11,6}, {2,6,7, 2,6,7}, {2,9,7, 2,9,7}, {2,11,7, 2,11,7}, {2,6,8, 2,11,8}, 
            	// Right wall
            	{6,6,6, 6,11,6}, {6,6,7, 6,6,7}, {6,9,7, 6,9,7}, {6,11,7, 6,11,7}, {6,6,8, 6,11,8}, 
            	// Back wall
            	{3,6,9, 5,9,9}, {3,10,9, 3,11,9}, {4,11,9, 4,11,9}, {5,10,9, 5,11,9}, 
            	// Lantern support
            	{3,8,8, 3,8,8}, 
            	// Roof
            	{2,12,5, 6,12,5}, {2,12,6, 2,12,8}, {6,12,6, 6,12,8}, {2,12,9, 6,12,9}, 
            	// Supports
            	{0,11,5, 0,11,5}, {2,11,3, 2,11,3}, {6,11,3, 6,11,3}, {8,11,5, 8,11,5}, {0,11,9, 0,11,9}, {2,11,11, 2,11,11}, {6,11,11, 6,11,11}, {8,11,9, 8,11,9}, 
            	{0,8,5, 0,8,5}, {2,8,3, 2,8,3}, {6,8,3, 6,8,3}, {8,8,5, 8,8,5}, {0,8,9, 0,8,9}, {2,8,11, 2,8,11}, {6,8,11, 6,8,11}, {8,8,9, 8,8,9}, 
            	{0,5,9, 1,5,9}, {2,4,9, 2,4,9}, {6,4,9, 6,4,9}, {7,5,9, 8,5,9}, 
            	{0,5,5, 1,5,5}, {2,4,5, 2,4,5}, {6,4,5, 6,4,5}, {7,5,5, 8,5,5}, 
            	// Entry
            	{3,0,1, 5,0,5}, {5,2,4, 5,2,4}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Pews
    			{5,0,0, 3}, {5,1,2, 3}, {5,2,3, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Entrance
    			{3,2,5, 5,2,5}, {4,2,4, 4,2,4}, 
    			{5,1,3, 5,1,3}, 
    			// First balcony
    			{0,5,6, 0,5,8}, 
    			{1,5,4, 1,5,4}, {1,5,6, 1,5,8}, {1,5,10, 1,5,10}, 
    			{2,5,3, 2,5,11}, 
    			{3,5,3, 5,5,5}, {3,5,9, 5,5,11}, 
    			{6,5,3, 6,5,11}, 
    			{7,5,4, 7,5,4}, {7,5,6, 7,5,8}, {7,5,10, 7,5,10}, 
    			{8,5,6, 8,5,8}, 
    			// Second balcony
    			{0,8,6, 0,8,8}, 
    			{1,8,4, 1,8,10}, 
    			{2,8,4, 2,8,5}, {2,8,9, 2,8,10}, 
    			{3,8,3, 5,8,4}, {3,8,10, 5,8,11}, 
    			{6,8,4, 6,8,5}, {6,8,9, 6,8,10}, 
    			{7,8,4, 7,8,10}, 
    			{8,8,6, 8,8,8}, 
    			// Second floor
    			{3,8,6, 3,8,7}, {4,8,6, 4,8,6}, {5,8,6, 5,8,8}, 
    			// Roof
    			{1,11,10, 7,11,10}, 
    			{1,11,5, 1,11,9}, {7,11,5, 7,11,9}, 
    			{1,11,4, 7,11,4}, 
    			{2,11,5, 2,11,5}, {6,11,5, 6,11,5}, {2,11,9, 2,11,9}, {6,11,9, 6,11,9}, 
    			{3,11,3, 5,11,3}, {0,11,6, 0,11,8}, {8,11,6, 8,11,8}, {3,11,11, 5,11,11}, 
    			// Front supports
    			{1,4,9, 1,4,9}, {7,4,9, 7,4,9}, 
    			{1,4,5, 1,4,5}, {7,4,5, 7,4,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{2,12,10, 6,12,10}, 
    			{1,12,5, 1,12,9}, {7,12,5, 7,12,9}, 
    			{2,12,4, 6,12,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
        	
        	            
            // Fences
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
            for (int[] uuvvww : new int[][]{
            	// Front deck
            	{2,0,4}, {6,0,4}, 
            	{2,0,2}, {6,0,2}, 
            	// Front railing
            	{3,1,4}, {3,2,4}, {3,3,4}, {4,3,4}, {3,3,5}, 
            	// First balcony railing
            	{0,6,4}, {0,6,5}, {0,6,6}, {0,6,7}, {0,6,8}, {0,6,9}, {0,6,10}, 
            	{1,6,10}, {1,6,11}, {2,6,11}, {3,6,11}, {4,6,11}, {5,6,11}, {6,6,11}, {7,6,11}, 
            	{7,6,10}, {8,6,10}, {8,6,9}, {8,6,8}, {8,6,7}, {8,6,6}, {8,6,5}, {8,6,4}, 
            	{7,6,4}, {7,6,3}, {6,6,3}, {5,6,3}, {4,6,3}, {3,6,3}, {2,6,3}, {1,6,3}, {1,6,4}, 
            	{0,7,5}, {0,7,9}, 
            	{2,7,3}, {6,7,3}, {2,7,11}, {6,7,11}, 
            	{8,7,5}, {8,7,9}, 
            	// Second balcony railing
            	{0,9,4}, {0,9,5}, {0,9,6}, {0,9,7}, {0,9,8}, {0,9,9}, {0,9,10}, 
            	{1,9,10}, {1,9,11}, {2,9,11}, {3,9,11}, {4,9,11}, {5,9,11}, {6,9,11}, {7,9,11}, 
            	{7,9,10}, {8,9,10}, {8,9,9}, {8,9,8}, {8,9,7}, {8,9,6}, {8,9,5}, {8,9,4}, 
            	{7,9,4}, {7,9,3}, {6,9,3}, {5,9,3}, {4,9,3}, {3,9,3}, {2,9,3}, {1,9,3}, {1,9,4}, 
            	{0,10,5}, {0,10,9}, 
            	{2,10,3}, {6,10,3}, {2,10,11}, {6,10,11}, 
            	{8,10,5}, {8,10,9}, 
            	// Hanging lantern
            	{4,12,7}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		{4,11,7}, 
        		{3,7,8}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Sitting Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(false); Block biomeSittingLanternBlock = (Block)blockObject[0]; int biomeSittingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	{2,2,9}, {6,2,5}, 
            	{1,7,10}, {7,7,4}, 
            	{7,10,10}, {1,10,4}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeSittingLanternBlock, biomeSittingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{4,9,5, 2, 1, 0}, 
    			{4,6,5, 2, 1, 0}, 
    			{4,3,6, 2, 1, 0}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
    		
    		
    		// Trapdoor (Bottom Vertical)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapdoor, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{2,10,7, 1}, {4,10,9, 2}, {6,10,7, 3}, 
            	{2,7,7, 1}, {6,7,7, 3}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, false, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
    		
    		
    		// Trapdoor (Top Vertical)
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{2,11,7, 1}, {4,11,9, 2}, {6,11,7, 3}, 
            	{2,8,7, 1}, {6,8,7, 3}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, true, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
            
            // Ladder
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.ladder, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLadderBlock = (Block)blockObject[0];
        	for (int[] uuvvwwo : new int[][]{ // Orientation - 3:leftward, 1:rightward, 2:backward, 0:forward
        		{4,3,7, 4,8,7, 2}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], uuvvwwo[3], uuvvwwo[4], uuvvwwo[5], biomeLadderBlock, StructureVillageVN.chooseLadderMeta(uuvvwwo[6], this.coordBaseMode), biomeLadderBlock, StructureVillageVN.chooseLadderMeta(uuvvwwo[6], this.coordBaseMode), false);
            }
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{5,6,7, 2, GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            	{3,9,7, 2, GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            })
            {
            	for (boolean isHead : new boolean[]{false, true})
            	{
            		int orientation = uvwoc[3];
            		int u = uvwoc[0] + (isHead?(new int[]{0,-1,0,1}[orientation]):0);
            		int v = uvwoc[1];
            		int w = uvwoc[2] + (isHead?(new int[]{-1,0,1,0}[orientation]):0);
            		ModObjects.setModBedBlock(world,
                			this.getXWithOffset(u, w),
                			this.getYWithOffset(v),
                			this.getZWithOffset(u, w),
                			StructureVillageVN.getBedOrientationMeta(orientation, this.coordBaseMode, isHead),
                			uvwoc[4]);
            	}
            }
        	
        	
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 3;
        	int chestV = 6;
        	int chestW = 8;
        	int chestO = 2; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo(ChestLootHandler.getGenericLootForVillageType(this.villageType));
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
        	
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1: rightward, 2:backward, 3: leftward
        			// Left side
        			{2,13,6, 3}, 
        			{-1,11,9, 3}, {-1,10,9, 3}, {-1,9,9, 3}, 
        			{2,10,9, 3}, {2,9,9, 3}, {2,7,9, 3}, 
        			// Right side
        			{6,13,6, 1}, 
        			{7,7,6, 1}, {7,6,6, 1}, 
        			{6,4,6, 1}, {6,3,6, 1}, 
        			{6,3,7, 1}, {6,2,7, 1}, 
        			{6,3,8, 1}, {6,2,8, 1}, 
        			// Away-facing vines
        			{6,11,12, 0}, {6,10,12, 0}, {6,9,12, 0}, {6,8,12, 0}, {6,7,12, 0}, 
        			{3,7,10, 0}, {4,7,10, 0}, {4,6,10, 0}, 
        			{5,2,9, 0}, {5,1,9, 0}, 
        			{4,2,9, 0}, {4,1,9, 0}, 
        			{3,1,9, 0}, {3,0,9, 0}, 
        			// Player-facing side
        			{3,13,5, 2}, {5,13,5, 2}, 
        			{2,10,5, 2}, 
        			{2,8,2, 2}, {2,7,2, 2}, {2,6,2, 2}, 
        			{6,11,2, 2}, {6,10,2, 2}, {6,9,2, 2}, {6,8,2, 2}, {6,7,2, 2}, {6,6,2, 2}, 
        			{6,7,5, 2}, {6,6,5, 2}, 
        			{5,4,5, 2}, {5,3,5, 2}, 
            		})
                {
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
        			}
                }
        	}
        	
        	
    		// Villagers
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	if (VillageGeneratorConfigHandler.spawnVillagersInResidences)
            	{
	            	int[][] villagerPositions = new int[][]{
	                	{5,6,6, -1, 0}, 
	                	{3,9,6, -1, 0}, 
	        			};
	        		int countdownToAdult = 1+random.nextInt(villagerPositions.length); // One of the villagers here is an adult
	            	
	        		for (int[] ia :villagerPositions)
	        		{
	        			EntityVillager entityvillager = new EntityVillager(world);
	        			entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], (--countdownToAdult)==0?0:Math.min(random.nextInt(24001)-12000,0));
	        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)this.getYWithOffset(ia[1]) + 1.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
	                    		random.nextFloat()*360F, 0.0F);
	                    world.spawnEntityInWorld(entityvillager);
	        		}
            	}
            }
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    // --- Library --- //
    // designed by Overjay
    
    public static class SwampLibrary extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"FFFFFFFFF",
    			"FFFFFFFFF",
    			"FFFFFFFFF",
    			"FFFFFFFFF",
    			"FFFFFFFFF",
    			"FFFPPPFFF",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 8;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 1;
    	private static final int DECREASE_MAX_U = 1;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampLibrary() {}

    	public SwampLibrary(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampLibrary buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampLibrary(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
            
                    	
            // Polished Granite
            blockObject = ModObjects.chooseModPolishedGraniteBlockObject();
            if (blockObject==null) {blockObject = ModObjects.chooseModSmoothStoneBlockObject();} // Guarantees a vanilla stone if this fails
            Block polishedGraniteBlock = (Block) blockObject[0]; int polishedGraniteMeta = (Integer) blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{2,1,3, 6,1,10}, 
        		})
            {
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], polishedGraniteBlock, polishedGraniteMeta, polishedGraniteBlock, polishedGraniteMeta, false);	
            }
            
            
            // Bookshelves
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.bookshelf, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeBookshelfBlock = (Block)blockObject[0]; int biomeBookshelfMeta = (Integer)blockObject[1];
            for (int[] uuvvww : new int[][]{
        		{2,2,6, 3,3,6}, {3,2,8, 3,3,9}, {3,4,9, 3,5,9}, 
        		{6,2,3, 6,3,3}, {6,2,5, 6,2,6}, {6,2,8, 6,2,8}, {6,2,10, 6,2,10}, {6,4,10, 6,4,10}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeBookshelfBlock, biomeBookshelfMeta, biomeBookshelfBlock, biomeBookshelfMeta, false);
            }
    		
    		
    		// Redstone Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			{6,3,5, -1}, {6,3,8, -1}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.redstone_torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		
    		// Stone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBlock = (Block)blockObject[0]; int biomeStoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Left wall
    			{1,2,3, 1,2,3}, {1,4,3, 1,4,3}, 
    			{1,3,8, 1,4,8}, 
    			// Right wall
    			{7,2,5, 7,3,5}, 
    			{7,3,8, 7,4,8}, 
    			// Front wall
    			{1,3,2, 1,4,2}, {3,5,2, 3,5,2}, {5,5,2, 5,5,2}, {7,3,2, 7,4,2}, 
    			// Back wall
    			{3,2,11, 3,2,11}, {3,4,11, 3,5,11}, 
    			{5,2,11, 5,3,11}, {6,4,11, 6,4,11}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBlock, biomeStoneMeta, biomeStoneBlock, biomeStoneMeta, false);	
    		}
    		
    		
    		// Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Left wall
    			{1,1,3, 1,1,3}, {1,3,3, 1,3,3}, 
    			{1,2,5, 1,3,5}, 
    			{1,4,6, 1,4,6}, 
    			{1,1,7, 1,1,7}, {1,3,7, 1,4,7}, 
    			{1,2,8, 1,2,8}, 
    			{1,1,9, 1,1,9}, 
    			{1,2,10, 1,4,10}, 
    			// Right wall
    			{7,1,3, 7,4,3}, 
    			{7,1,5, 7,1,5}, {7,4,5, 7,4,5}, 
    			{7,3,6, 7,4,6}, 
    			{7,1,7, 7,2,7}, 
    			{7,1,8, 7,2,8}, 
    			{7,2,10, 7,4,10}, 
    			// Front wall
    			{1,1,2, 1,2,2}, 
    			{2,2,2, 2,5,2}, 
    			{3,1,2, 3,3,2}, 
    			{4,1,2, 4,1,2}, {4,4,2, 4,5,2}, 
    			{5,2,2, 5,3,2}, 
    			{6,2,2, 6,5,2}, 
    			{7,1,2, 7,2,2}, 
    			// Back wall
    			{1,1,11, 1,4,11}, 
    			{2,1,11, 2,1,11}, {2,5,11, 2,5,11}, 
    			{3,3,11, 3,3,11}, 
    			{5,1,11, 5,1,11}, {5,4,11, 5,5,11}, 
    			{7,1,11, 7,4,11}, 
    			// Ceiling
    			{2,5,5, 2,5,5}, {2,5,8, 2,5,8}, 
    			{6,5,5, 6,5,5}, {6,5,8, 6,5,8}, 
    			// Exterior posts
    			{0,1,2, 0,1,2}, {0,1,11, 0,1,11}, 
    			{4,1,12, 4,1,12}, {7,1,12, 7,1,12}, 
    			{7,1,1, 7,1,1}, 
    			{8,1,2, 8,1,2}, {8,1,11, 8,1,11}, 
    			// Ceiling
    			{3,6,5, 5,6,5}, {3,6,8, 5,6,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.mossy_cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneBlock = (Block)blockObject[0]; int biomeMossyCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Left wall
    			{1,1,4, 1,1,5}, {1,4,5, 1,4,5}, 
    			{1,1,6, 1,3,6}, 
    			{1,2,7, 1,2,7}, 
    			{1,1,8, 1,1,8}, 
    			{1,1,10, 1,1,10}, 
    			{1,1,10, 1,1,10}, 
    			// Right wall 
    			{7,1,4, 7,1,4}, 
    			{7,1,6, 7,2,6}, 
    			{7,3,7, 7,4,7}, 
    			{7,1,9, 7,1,10}, 
    			// Front wall 
    			{2,1,2, 2,1,2}, 
    			{4,6,2, 4,6,2}, 
    			{6,1,2, 6,1,2}, 
    			// Back wall 
    			{3,1,11, 4,1,11}, 
    			{4,6,11, 4,6,11}, 
    			{6,1,11, 6,1,11}, {6,5,11, 6,5,11}, 
    			// Exterior posts
    			{1,1,1, 1,1,1}, 
    			{0,1,5, 0,1,5}, 
    			{0,1,8, 0,1,8}, 
    			{1,1,12, 1,1,12}, 
    			{8,1,5, 8,1,5}, 
    			{8,1,8, 8,1,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, false);	
    		}
            
            
            // Grass
            for(int[] uuvvww : new int[][]{
            	{0,0,1, 8,0,12}, 
            	{0,0,0, 2,0,0}, {6,0,0, 8,0,0}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeTopBlock, biomeTopMeta, biomeTopBlock, biomeTopMeta, false);	
            }
    		
    		
    		// Cobblestone stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Roof
    			{2,6,11, 0}, {3,6,11, 1}, {3,7,11, 2}, {4,7,12, 2+4}, {4,7,11, 3}, {5,7,11, 2}, {5,6,11, 0}, {6,6,11, 1}, 
    			{2,6,8, 0}, {6,6,8, 1}, 
    			{2,6,5, 0}, {6,6,5, 1}, 
    			{2,6,2, 0}, {3,6,2, 1}, {3,7,2, 3}, {4,7,1, 3+4}, {4,7,2, 2}, {5,7,2, 3}, {5,6,2, 0}, {6,6,2, 1}, 
    			{1,5,2, 0}, {1,5,3, 0}, {1,5,4, 0}, {1,5,5, 0}, {1,5,6, 0}, {1,5,7, 0}, {1,5,8, 0}, {1,5,9, 0}, {1,5,10, 0}, {1,5,11, 0}, 
    			{7,5,2, 1}, {7,5,3, 1}, {7,5,4, 1}, {7,5,5, 1}, {7,5,6, 1}, {7,5,7, 1}, {7,5,8, 1}, {7,5,9, 1}, {7,5,10, 1}, {7,5,11, 1}, 
    			// Ceiling
    			{2,4,8, 1+4}, {3,5,8, 1+4}, {5,5,8, 0+4}, {6,4,8, 0+4}, 
    			{2,4,5, 1+4}, {3,5,5, 1+4}, {5,5,5, 0+4}, {6,4,5, 0+4},
    			// Windows
    			{7,2,4, 0}, {7,4,4, 0+4}, {7,2,9, 0}, {7,4,9, 0+4}, 
    			{1,2,4, 1}, {1,4,4, 1+4}, {1,2,9, 1}, {1,4,9, 1+4}, 
    			{2,2,11, 3}, {2,4,11, 3+4}, {4,2,11, 3}, {4,5,11, 3+4}, {6,2,11, 3}, {6,4,11, 3+4}, 
    			// Posts
    			{0,2,11, 0+4}, {0,3,11, 0}, 
    			{0,2,8, 0+4}, {0,3,8, 0}, 
    			{0,2,5, 0+4}, {0,3,5, 0}, 
    			{0,2,2, 0+4}, {0,3,2, 0}, 
    			{1,2,1, 3+4}, {1,3,1, 3}, {7,2,1, 3+4}, {7,3,1, 3}, 
    			{8,2,11, 1+4}, {8,3,11, 1}, 
    			{8,2,8, 1+4}, {8,3,8, 1}, 
    			{8,2,5, 1+4}, {8,3,5, 1}, 
    			{8,2,2, 1+4}, {8,3,2, 1}, 
    			{1,2,12, 2+4}, {1,3,12, 2}, {4,2,12, 2+4}, {4,3,12, 2}, {7,2,12, 2+4}, {7,3,12, 2}, 
    			// Front steps
    			{3,1,1, 3+4}, {4,1,1, 3}, {5,1,1, 3+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Cobblestone Slab (lower)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabLowerBlock = (Block)blockObject[0]; int biomeCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{3,7,8, 5,7,8}, 
    			{3,7,5, 5,7,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Front entrance
    			{4,4,1, 3}, 
    			// Ceiling trim
    			{2,5,3, 1+4}, {2,5,4, 1+4}, {2,5,6, 1+4}, {2,5,7, 1+4}, {2,5,9, 1+4}, {2,5,10, 1+4}, 
    			{6,5,3, 0+4}, {6,5,4, 0+4}, {6,5,6, 0+4}, {6,5,7, 0+4}, {6,5,9, 0+4}, {6,5,10, 0+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{4,6,3, 4,6,4}, {4,6,6, 4,6,7}, {4,6,9, 4,6,10}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{3,6,3, 3,6,4}, {3,6,6, 3,6,7}, {3,6,9, 3,6,10}, 
    			{5,6,3, 5,6,4}, {5,6,6, 5,6,7}, {5,6,9, 5,6,10}, 
    			{3,4,1, 3,4,1}, {5,4,1, 5,4,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
        	
        	            
            // Fences
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
            for (int[] uuvvww : new int[][]{
            	// Front posts
            	{3,2,1}, {3,3,1}, {5,2,1}, {5,3,1}, 
            	// Front desk
            	{3,2,3}, {3,2,4}, 
            	// Interior shelf posts
            	{3,4,6}, {3,5,6}, {6,3,6}, {6,4,6}, 
            	{3,4,8}, {3,5,10}, 
            	{6,3,10}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
            
        	// Fence Gate (Along)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence_gate, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceGateBlock = (Block)blockObject[0]; int biomeFenceGateMeta = (Integer)blockObject[1];
        	for(int[] uvw : new int[][]{
            	{3,2,5}, 
            	})
            {
        		this.placeBlockAtCurrentPosition(world, biomeFenceGateBlock, StructureVillageVN.getMetadataWithOffset(biomeFenceGateBlock, (biomeFenceGateMeta+1)%8, this.coordBaseMode), uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	{4,5,5}, {4,5,8}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
        	// Carpet
        	for(int[] uuvvww : new int[][]{
        		// Lower
        		{2,3,3, 3,3,3, (GeneralConfig.useVillageColors ? this.townColor2 : 13)}, // Green
        		{3,3,4, 3,3,4, (GeneralConfig.useVillageColors ? this.townColor2 : 13)}, // Green
        		})
            {
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.carpet, uuvvww[6], Blocks.carpet, uuvvww[6], false);	
            }
            
            
            // Stained Glass Windows
            for (int[] uvwc : new int[][]{
            	// Player-facing wall
            	{3,4,2, GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
            	{5,4,2, GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
            	// Left wall
            	{1,3,4, GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            	{1,3,9, GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            	// Right wall
            	{7,3,4, GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            	{7,3,9, GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            	// Back wall
            	{2,3,11, GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            	{4,3,11, GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            	{4,4,11, GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            	{6,3,11, GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
        		})
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.stained_glass_pane, uvwc[3], uvwc[0], uvwc[1], uvwc[2], structureBB);
            }
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{4,2,2, 0, 1, 0}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
    		
        	
            // Lectern
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for (int[] uvwo : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{6,2,4, 3}, {6,2,7, 3}, 
            })
            {
        		ModObjects.setModLecternBlock(world,
            			this.getXWithOffset(uvwo[0], uvwo[2]),
            			this.getYWithOffset(uvwo[1]),
            			this.getZWithOffset(uvwo[0], uvwo[2]),
            			uvwo[3],
            			this.coordBaseMode,
            			biomePlankMeta,
            			-1 // Carpet color
        				);
            }
        	
        	
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 2;
        	int chestV = 2;
        	int chestW = 3;
        	int chestO = 0; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo("vn_library");
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
        	
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1: rightward, 2:backward, 3: leftward
        			// Left side
        			{0,4,3, 3}, {0,3,3, 3}, {0,2,3, 3}, {0,1,3, 3}, 
        			{0,4,9, 3}, {0,3,8, 3}, {0,2,8, 3}, {0,1,8, 3}, 
        			// Right side
        			{8,4,2, 1}, {8,4,3, 1}, {8,3,3, 1}, {8,2,3, 1}, {8,1,3, 1}, {8,2,4, 1}, 
        			{8,4,6, 1}, {8,3,6, 1}, {8,2,6, 1}, {8,1,6, 1}, 
        			{8,3,7, 1}, {8,2,7, 1}, 
        			{8,4,8, 1}, 
        			{8,1,9, 1}, 
        			{8,4,10, 1}, {8,3,10, 1}, {8,2,10, 1}, {8,1,10, 1}, 
        			// Away-facing vines
        			{2,5,12, 0}, {2,4,12, 0}, 
        			{5,4,12, 0}, {5,3,12, 0}, {5,2,12, 0}, 
        			{6,5,12, 0}, {6,4,12, 0}, {6,2,12, 0}, {6,1,12, 0}, 
        			// Player-facing side
        			{1,4,1, 2}, 
        			{2,4,1, 2}, {2,3,1, 2}, {2,2,1, 2}, {2,1,1, 2}, 
        			{6,4,1, 2}, {6,3,1, 2}, {6,2,1, 2}, {6,1,1, 2}, 
        			{7,4,1, 2}, 
            		})
                {
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
        			}
                }
        	}
    		
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			// Villager
    			int s = random.nextInt(25);
    			
    			int u = s<=5? 2 : s<=7? 3 : s<=15? 4 : s<=23? 5 : 6;
    			int v = 2;
    			int w = s<=1? s+4 : s<=5? s+5 : s<=6? 7 : s<=7? 10 : s<=15? s-5 : s<=23? s-13 : 9;
    			
    			EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 1, 1, 0); // Librarian
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
    			world.spawnEntityInWorld(entityvillager);
    		}
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
    	}
    	
    	/**
    	 * Returns the villager type to spawn in this component, based on the number
    	 * of villagers already spawned.
    	 */
    	@Override
    	protected int getVillagerType (int number) {return 1;}
    }
    
    
    // --- Mason House --- //
    // designed by AstroTibs
    
    public static class SwampMasonHouse extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"FFFFFFF",
    			"FFFFFFF",
    			"FFFFFFF",
    			"FFFFFFF",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 7;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampMasonHouse() {}

    	public SwampMasonHouse(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampMasonHouse buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampMasonHouse(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
    		
    		
    		// Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Outline
    			{0,0,0, 0,0,1}, {0,0,4, 0,0,5}, {0,0,9, 0,0,11}, 
    			{5,0,11, 6,0,11}, 
    			{6,0,8, 6,0,8}, {6,0,0, 6,0,1}, 
    			{4,0,0, 4,0,0}, 
    			// Floor
    			{1,0,1, 5,0,2}, 
    			{1,0,3, 2,0,6}, {4,0,3, 5,0,6}, 
    			{1,0,7, 4,0,9}, {1,0,10, 1,0,10}, 
    			{4,0,10, 5,0,10}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.mossy_cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneBlock = (Block)blockObject[0]; int biomeMossyCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Outline
    			{0,0,2, 0,0,3}, {0,0,6, 0,0,8}, 
    			{1,0,11, 4,0,11}, {2,0,10, 3,0,10}, 
    			{6,0,9, 6,0,10}, {6,0,2, 6,0,7}, {5,0,8, 5,0,9}, 
    			{1,0,0, 2,0,0}, {5,0,0, 5,0,0}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, false);	
    		}
        	
        	
            // Glazed terracotta
        	Object[] tryGlazedTerracotta;
        	for (int[] uvwoc : new int[][]{ // u,v,w, orientation, dye color
        		// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
        		{3,0,3, 1, GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
        		{3,0,4, 0, GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
        		{3,0,5, 3, GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
        		{3,0,6, 2, GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
           		})
        	{
        		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(uvwoc[4], StructureVillageVN.chooseGlazedTerracottaMeta(uvwoc[3], this.coordBaseMode));
        		if (tryGlazedTerracotta != null)
            	{
        			this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvwoc[0], uvwoc[1], uvwoc[2], structureBB);
            	}
        		else
        		{
        			this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, uvwoc[4], uvwoc[0], uvwoc[1], uvwoc[2], structureBB);
        		}
            }
    		
    		
    		// Cobblestone stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Roof
    			{3,0,0, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
        	
        	
        	// Cobblestone wall
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{0,1,0, 0,1,1}, {0,1,4, 0,1,5}, {0,1,8, 0,1,11}, 
        		{1,1,11, 1,1,11}, {3,1,11, 6,1,11}, {6,1,10, 6,1,10}, {6,1,7, 6,1,8}, {6,1,3, 6,1,3}, {6,1,0, 6,1,0}, {4,1,0, 4,1,0}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, false);
            }
        	
        	
        	// Mossy Cobblestone wall
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 1, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneWallBlock = (Block)blockObject[0]; int biomeMossyCobblestoneWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{0,1,2, 0,1,3}, {0,1,6, 0,1,7}, 
        		{2,1,11, 2,1,11}, 
        		{6,1,9, 6,1,9}, {6,1,4, 6,1,6}, {6,1,1, 6,1,2}, 
        		{5,1,0, 5,1,0}, {1,1,0, 2,1,0}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneWallBlock, biomeMossyCobblestoneWallMeta, biomeMossyCobblestoneWallBlock, biomeMossyCobblestoneWallMeta, false);
            }
    		
    		
    		// Cobblestone Slab (lower) - not biome adjusted
    		for(int[] uuvvww : new int[][]{
    			{1,2,8, 1,2,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.stone_slab, 3, Blocks.stone_slab, 3, false);	
    		}
    		
    		
    		// Clay - not biome adjusted
    		for(int[] uuvvww : new int[][]{
    			{1,1,8, 1,1,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.clay, 0, Blocks.clay, 0, false);	
    		}
    		
    		
    		// Stone Brick Stairs - not biome adjusted
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Central platform
    			{4,1,2, 0},  
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, Blocks.stone_brick_stairs, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		
    		// Polished Andesite - not biome adjusted
    		blockObject = ModObjects.chooseModPolishedAndesiteObject();
    		if (blockObject==null) // Try polished stone
    		{
    			blockObject = ModObjects.chooseModSmoothStoneBlockObject(); // Guarantees a vanilla stone if this fails
    		};
    		Block biomePolishedAndesiteBlock = (Block)blockObject[0]; int biomePolishedAndesiteMeta = (Integer)blockObject[1];
    		for (int[] uvw : new int[][]{
    			// Central platform
    			{4,1,3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomePolishedAndesiteBlock, biomePolishedAndesiteMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Andesite - not biome adjusted
    		blockObject = ModObjects.chooseModAndesiteObject();
    		if (blockObject==null) // Try stone
    		{
    			blockObject = new Object[]{Blocks.stone, 0}; // Guarantees a vanilla stone if this fails
    		};
    		Block biomeAndesiteBlock = (Block)blockObject[0]; int biomeAndesiteMeta = (Integer)blockObject[1];
    		for (int[] uvw : new int[][]{
    			// Central platform
    			{1,1,9},  
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeAndesiteBlock, biomeAndesiteMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Stone Brick Slab (lower) - not biome adjusted
    		Block biomeStoneBrickSlabLowerBlock = Blocks.stone_slab; int biomeStoneBrickSlabLowerMeta = 5;
    		for(int[] uuvvww : new int[][]{
    			{2,1,5, 2,1,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickSlabLowerBlock, biomeStoneBrickSlabLowerMeta, biomeStoneBrickSlabLowerBlock, biomeStoneBrickSlabLowerMeta, false);	
    		}
    		
    		
    		// Brick block - not biome adjusted
    		for (int[] uvw : new int[][]{
    			// Central platform
    			{2,1,6}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, Blocks.brick_block, 0, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Polished Diorite - not biome adjusted
    		blockObject = ModObjects.chooseModPolishedDioriteObject();
        	if (blockObject==null) // Try regular diorite block
        	{
        		blockObject = ModObjects.chooseModDioriteObject();
        		if (blockObject==null)
        		{
        			blockObject = ModObjects.chooseModSmoothStoneBlockObject();
        		}
        	}; 
        	Block polishedDioriteBlock = (Block)blockObject[0]; int polishedDioriteMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
    			// Central platform
    			{2,1,7}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, polishedDioriteBlock, polishedDioriteMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Concrete
    		Object[] tryConcrete = ModObjects.chooseModConcrete(GeneralConfig.useVillageColors ? townColor : 15); // Black
        	Block concreteBlock = Blocks.stained_hardened_clay; int concreteMeta = GeneralConfig.useVillageColors ? townColor : 15; // Black
        	if (tryConcrete != null) {concreteBlock = (Block) tryConcrete[0]; concreteMeta = (Integer) tryConcrete[1];}
    		for(int[] uuvvww : new int[][]{
    			// Front wall
    			{1,1,1, 2,4,1}, {3,4,1, 3,4,1}, {4,1,1, 5,4,1}, 
    			// Left wall
    			{1,1,2, 1,1,7}, {1,2,3, 1,3,3}, {1,2,5, 1,3,5}, {1,2,7, 1,3,7}, {1,4,2, 1,4,7}, 
    			// Right wall
    			{5,1,2, 5,1,7}, {5,2,3, 5,3,3}, {5,2,5, 5,3,5}, {5,2,7, 5,3,7}, {5,4,2, 5,4,7}, 
    			// Back section
    			{2,1,8, 2,3,9}, {3,1,9, 4,3,9}, {4,3,8, 4,3,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], concreteBlock, concreteMeta, concreteBlock, concreteMeta, false);	
    		}
    		
    		
    		// Trapdoor (Bottom Vertical)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapdoor, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{1,2,2, 1}, {1,2,4, 1}, {1,2,6, 1},
            	{5,2,2, 3}, {5,2,4, 3}, {5,2,6, 3}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, false, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
    		
    		
    		// Trapdoor (Top Vertical)
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{1,3,2, 1}, {1,3,4, 1}, {1,3,6, 1},
            	{5,3,2, 3}, {5,3,4, 3}, {5,3,6, 3}, 
            	{3,3,1, 0}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, true, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
        	
        	
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
    			// Roof
    			{2,5,2, 4,5,6}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
            
        	
        	// Stone Cutter
        	// Orientation:0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	blockObject = ModObjects.chooseModStonecutter(2, biomePlankMeta); Block stonecutterBlock = (Block) blockObject[0]; int stonecutterMeta = (Integer) blockObject[1];
            this.placeBlockAtCurrentPosition(world, stonecutterBlock, stonecutterMeta, 3, 1, 8, structureBB);
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{2,4,7, 4,4,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{1,5,7, 5,5,7}, 
    			{1,5,2, 1,5,6}, {5,5,2, 5,5,6}, 
    			{1,5,1, 5,5,1}, 
    			{3,6,3, 3,6,5}, 
    			{2,4,8, 4,4,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	{3,4,3}, {3,3,8} 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{3,1,1, 2, 1, 1}, 
    			{4,1,8, 1, 1, 0}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
        	
        	
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 1;
        	int chestV = 1;
        	int chestW = 10;
        	int chestO = 1; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo("vn_mason");
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
        	
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1: rightward, 2:backward, 3: leftward
        			// Left side
        			{0,4,2, 3}, 
        			{0,4,3, 3}, {0,3,3, 3}, {0,2,3, 3}, 
        			{0,4,5, 3}, 
        			{0,3,7, 3}, {0,2,7, 3}, 
        			{1,3,9, 3}, {1,2,9, 3}, 
        			// Right side
        			{6,3,1, 1}, {6,2,1, 1}, 
        			{6,4,3, 1}, {6,3,3, 1}, {6,2,3, 1}, 
        			{6,4,5, 1}, {6,3,5, 1}, {6,2,5, 1}, 
        			{6,4,6, 1}, 
        			{6,3,7, 1}, 
        			{5,3,8, 1}, 
        			{5,3,9, 1}, {5,2,9, 1}, {5,1,9, 1}, 
        			// Away-facing vines
        			{1,4,8, 0}, {1,3,8, 0}, 
        			{2,3,10, 0}, {2,2,10, 0}, {2,1,10, 0}, 
        			{3,3,10, 0}, 
        			// Player-facing side
        			{1,4,0, 2}, {1,3,0, 2}, {1,2,0, 2}, 
        			{2,3,0, 2}, {2,2,0, 2}, 
        			{5,4,0, 2}, {5,3,0, 2}, {5,2,0, 2}, 
            		})
                {
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
        			}
                }
        	}
    		
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			// Villager
    			int s = random.nextInt(20);
    			
    			int u = s<=2? 2 : s<=8? 3 : s<=13? 4 : s<=16? 5 : s-15;
    			int v = 2;
    			int w = s<=2? s-2 : s<=8? s-1 : s<=13? s-5 : s<=16? s-6 : 10;
    			
    			EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 3, 4, 0); // Mason
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
    			world.spawnEntityInWorld(entityvillager);
    		}
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 3;}
    }
    
    
    // --- Medium House 1 --- //
    // designed by AstroTibs
    
    public static class SwampMediumHouse1 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"          ",
    			"          ",
    			"          ",
    			"          ",
    			"          ",
    			"          ",
    			"          ",
    			"          ",
    			"          ",
    			"F         ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 7;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 5;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampMediumHouse1() {}

    	public SwampMediumHouse1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampMediumHouse1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampMediumHouse1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
        	
        	            
            // Fences
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
            for (int[] uuvvww : new int[][]{
            	// Under posts
            	{1,0,8}, {5,0,9}, {9,0,8}, 
            	{1,0,5}, {5,0,5}, {9,0,5}, 
            	{1,0,1}, {5,0,1}, {9,0,1}, 
            	// Awning posts
            	{1,2,1}, {1,2,2}, {1,3,1}, {5,2,1}, {5,3,1}, {9,2,1}, {9,3,1}, 
            	// Porch table
            	{7,2,2}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
    		// Foot foundation
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uvw : new int[][]{
            	{1,-1,8}, {5,-1,9}, {9,-1,8}, 
            	{1,-1,5}, {5,-1,5}, {9,-1,5}, 
            	{1,-1,1}, {5,-1,1}, {9,-1,1}, 
    			})
    		{
    			this.func_151554_b(world, biomeCobblestoneBlock, biomeCobblestoneMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
        	
        	// For stripped logs specifically
        	Block biomeLogVertBlock = Blocks.log; int biomeLogVertMeta = 0;
        	Block biomeStrippedLogVerticBlock = biomeLogVertBlock; int biomeStrippedLogVerticMeta = biomeLogVertMeta;
        	// Try to see if stripped logs exist
        	blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{7,2,7, 7,2,7}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
            }
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
    			// Entry
    			{0,1,2, 0,1,3}, 
    			// Floor
    			{1,1,1, 9,1,8}, {4,1,9, 6,1,9}, 
    			// Front wall
    			{1,2,4, 1,3,4}, {2,2,4, 3,2,4}, {4,2,4, 4,3,4}, 
    			{6,2,4, 7,3,4}, {8,2,4, 8,2,4}, {9,2,4, 9,3,4}, 
    			{3,4,4, 7,4,4}, {5,5,4, 5,5,4}, 
    			// Back wall
    			{1,2,8, 3,3,8}, {3,4,8, 3,4,8}, {7,4,8, 7,4,8}, {7,2,8, 9,3,8}, 
    			// Left wall
    			{1,2,5, 1,3,7}, 
    			// Right wall
    			{9,2,5, 9,3,7}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
    		
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{2,4,8, 2,4,8}, {4,5,8, 6,5,8}, {6,5,4, 6,5,4}, {8,4,8, 8,4,8}, 
    			// Furnace
    			{4,2,8, 4,2,8}, {6,2,8, 6,2,8}, 
    			{4,2,9, 6,3,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.mossy_cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneBlock = (Block)blockObject[0]; int biomeMossyCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{2,4,4, 2,4,4}, {4,5,4, 4,5,4}, {8,4,4, 8,4,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, false);	
    		}
    		
    		
    		// Cobblestone Slab (lower)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabLowerBlock = (Block)blockObject[0]; int biomeCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Awning
    			{1,4,1, 4,4,2}, {2,4,3, 3,4,3}, {6,4,1, 7,4,3}, {8,4,1, 8,4,2}, 
    			{1,4,5, 1,4,7}, {9,4,8, 9,4,8}, 
    			// Roof
    			{3,5,6, 3,5,8}, {5,6,4, 5,6,7}, {7,5,4, 7,5,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone Slab (lower)
    		Block biomeMossyCobblestoneSlabLowerBlock = biomeCobblestoneSlabLowerBlock; int biomeMossyCobblestoneSlabLowerMeta = biomeCobblestoneSlabLowerMeta;
    		blockObject = ModObjects.chooseModMossyCobblestoneSlabBlock(false);
    		if (blockObject != null)
    		{
    			blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs);
    			biomeMossyCobblestoneSlabLowerBlock = (Block)blockObject[0]; biomeMossyCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		}
    		for(int[] uuvvww : new int[][]{
    			// Awning
    			{1,4,3, 1,4,4}, {1,4,8, 1,4,8}, 
    			{4,4,3, 4,4,3}, {5,4,1, 5,4,3}, 
    			{8,4,3, 8,4,3}, {9,4,1, 9,4,7}, 
    			// Roof
    			{3,5,4, 3,5,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Cobblestone Slab (upper)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3+8, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabUpperBlock = (Block)blockObject[0]; int biomeCobblestoneSlabUpperMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{2,4,5, 2,4,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneSlabUpperBlock, biomeCobblestoneSlabUpperMeta, biomeCobblestoneSlabUpperBlock, biomeCobblestoneSlabUpperMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone Slab (upper)
    		Block biomeMossyCobblestoneSlabUpperBlock = biomeCobblestoneSlabUpperBlock; int biomeMossyCobblestoneSlabUpperMeta = biomeCobblestoneSlabUpperMeta;
    		blockObject = ModObjects.chooseModMossyCobblestoneSlabBlock(true);
    		if (blockObject != null)
    		{
    			blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs);
    			biomeMossyCobblestoneSlabUpperBlock = (Block)blockObject[0]; biomeMossyCobblestoneSlabUpperMeta = (Integer)blockObject[1];
    		}
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{2,4,6, 2,4,7}, {4,5,5, 4,5,7}, {6,5,5, 6,5,7}, {8,4,5, 8,4,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneSlabUpperBlock, biomeMossyCobblestoneSlabUpperMeta, biomeMossyCobblestoneSlabUpperBlock, biomeMossyCobblestoneSlabUpperMeta, false);	
    		}
    		
            
            // Cobblestone stairs
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
        	for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Furnace
        		{4,4,8, 1+4}, {5,4,8, 3+4}, {6,4,8, 0+4}, 
        		{4,3,8, 1+4}, {6,3,8, 0+4}, 
        		// Furnace outside
        		{4,4,9, 2}, {5,4,9, 2}, {6,4,9, 2}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
    		
    		
            // Stone Brick
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickBlock = (Block)blockObject[0]; int biomeStoneBrickMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Under the furnace
    			{5,2,8, 5,2,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickBlock, biomeStoneBrickMeta, biomeStoneBrickBlock, biomeStoneBrickMeta, false);	
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Entry
    			{0,0,0, 3}, {0,1,1, 3}, 
    			// Porch furniture
    			{2,2,2, 3}, {3,2,2, 3}, {6,2,2, 1}, {8,2,2, 0}, 
    			// Interior seat
    			{2,2,5, 2}, {3,2,5, 2}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
        	
        	
            // Wooden pressure plate
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_pressure_plate, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodPressurePlateBlock = (Block)blockObject[0]; int biomeWoodPressurePlateMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		{7,3,2},  
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeWoodPressurePlateBlock, biomeWoodPressurePlateMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		// Porch
        		{7,3,1}, 
        		// Interior
        		{5,5,7}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Sitting Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(false); Block biomeSittingLanternBlock = (Block)blockObject[0]; int biomeSittingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	{7,3,7}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeSittingLanternBlock, biomeSittingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
        	// Carpet
        	for(int[] uuvvww : new int[][]{
        		// Lower
        		{2,2,6, 3,2,6, (GeneralConfig.useVillageColors ? this.townColor2 : 13)}, // Green
        		{3,2,7, 3,2,7, (GeneralConfig.useVillageColors ? this.townColor2 : 13)}, // Green
        		})
            {
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.carpet, uuvvww[6], Blocks.carpet, uuvvww[6], false);	
            }
            
            
            // Bookshelves
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.bookshelf, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeBookshelfBlock = (Block)blockObject[0]; int biomeBookshelfMeta = (Integer)blockObject[1];
            for (int[] uuvvww : new int[][]{
        		{2,2,7, 2,3,7}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeBookshelfBlock, biomeBookshelfMeta, biomeBookshelfBlock, biomeBookshelfMeta, false);
            }
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
        		{2,3,4}, {3,3,4}, {8,3,4}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{5,2,4, 2, 1, 1}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
        	
        	
        	// Furnaces
            for (int[] uvwo : new int[][]{ // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
            	{5,3,8, 2}, 
            	})
            {
                this.placeBlockAtCurrentPosition(world, Blocks.furnace, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2]), StructureVillageVN.chooseFurnaceMeta(uvwo[3], this.coordBaseMode), 2);
            }
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{6,2,6, 2, GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            	{8,2,6, 2, GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            })
            {
            	for (boolean isHead : new boolean[]{false, true})
            	{
            		int orientation = uvwoc[3];
            		int u = uvwoc[0] + (isHead?(new int[]{0,-1,0,1}[orientation]):0);
            		int v = uvwoc[1];
            		int w = uvwoc[2] + (isHead?(new int[]{-1,0,1,0}[orientation]):0);
            		ModObjects.setModBedBlock(world,
                			this.getXWithOffset(u, w),
                			this.getYWithOffset(v),
                			this.getZWithOffset(u, w),
                			StructureVillageVN.getBedOrientationMeta(orientation, this.coordBaseMode, isHead),
                			uvwoc[4]);
            	}
            }
    		
    		
        	// Patterned banners
    		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
			if (testForBanner!=null)
			{
    			for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color
    				// 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    				
    				{7,3,3, 2, 14}, // Red
    			})
    			{
        			int bannerU = uvwoc[0];
        			int bannerV = uvwoc[1];
        			int bannerW = uvwoc[2];
        			
        			int bannerX = this.getXWithOffset(bannerU, bannerW);
        			int bannerY = this.getYWithOffset(bannerV);
                    int bannerZ = this.getZWithOffset(bannerU, bannerW);
                    
                    boolean isHanging = true;
                    
                	// Set the banner and its orientation
    				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
    				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(uvwoc[3], this.coordBaseMode, isHanging), 2);
    				
    				// Set the tile entity
    				TileEntity tilebanner = new TileEntityBanner();
    				NBTTagCompound modifystanding = new NBTTagCompound();
    				tilebanner.writeToNBT(modifystanding);
    				modifystanding.setBoolean("IsStanding", !isHanging);
    				
    				if (GeneralConfig.useVillageColors)
    				{
    	            	NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, 
    	            			(this.boundingBox.minX+this.boundingBox.maxX)/2,
    	            			(this.boundingBox.minY+this.boundingBox.maxY)/2,
    	            			(this.boundingBox.minZ+this.boundingBox.maxZ)/2);
    					
        				tilebanner.readFromNBT(modifystanding);
        				ItemStack villageBanner = ModObjects.chooseModBannerItem();
        				villageBanner.setTagInfo("BlockEntityTag", villageNBTtag.getCompoundTag("BlockEntityTag"));
        				
            			((TileEntityBanner) tilebanner).setItemValues(villageBanner);
    				}
    				else
    				{
    					modifystanding.setInteger("Base", uvwoc[4]);
        				tilebanner.readFromNBT(modifystanding);
    				}
    				
            		world.setTileEntity(bannerX, bannerY, bannerZ, tilebanner);
    			}
			}
        	
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1: rightward, 2:backward, 3: leftward
        			// Left side
        			{0,3,4, 3}, {0,2,4, 3}, {0,1,4, 3}, 
        			// Right side
        			{10,3,4, 1}, {10,3,5, 1}, {10,2,5, 1}, {10,1,5, 1}, {10,3,6, 1}, {10,2,6, 1}, 
        			// Away-facing vines
        			{1,3,9, 0}, 
            		})
                {
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
        			}
                }
        	}
        	
        	
    		// Villagers
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	if (VillageGeneratorConfigHandler.spawnVillagersInResidences)
            	{
	            	int[][] villagerPositions = new int[][]{
	                	{6,2,5, -1, 0}, 
	                	{8,2,5, -1, 0}, 
	        			};
	        		int countdownToAdult = 1+random.nextInt(villagerPositions.length); // One of the villagers here is an adult
	            	
	        		for (int[] ia :villagerPositions)
	        		{
	        			EntityVillager entityvillager = new EntityVillager(world);
	        			entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], (--countdownToAdult)==0?0:Math.min(random.nextInt(24001)-12000,0));
	        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)this.getYWithOffset(ia[1]) + 1.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
	                    		random.nextFloat()*360F, 0.0F);
	                    world.spawnEntityInWorld(entityvillager);
	        		}
            	}
            }
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    // --- Medium House 2 --- //
    // designed by AstroTibs
    
    public static class SwampMediumHouse2 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"            ",
    			"            ",
    			"            ",
    			"            ",
    			"            ",
    			"            ",
    			"            ",
    			"         FF ",
    			"         F  ",
    			"         FF ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 7;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1+8; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 8;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 6;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampMediumHouse2() {}

    	public SwampMediumHouse2(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampMediumHouse2 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampMediumHouse2(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
    		
    		
    		// Stripped logs (Vertical)
        	Block biomeLogVertBlock = Blocks.log; int biomeLogVertMeta = 0;
    		Block biomeStrippedLogVerticBlock = biomeLogVertBlock; int biomeStrippedLogVerticMeta = biomeLogVertMeta;
    		// Try to see if stripped logs exist
    		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
    		for (int[] uuvvww : new int[][]{
        		{1,1,9, 1,4,9}, {7,1,9, 7,4,9}, 
        		{1,1,5, 1,4,5}, {7,1,5, 7,4,5}, 
        		{2,1,2, 2,4,2}, {6,1,2, 6,4,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
            
            
        	// Stripped Logs (Across)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2!=0? 4:0), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1];
        	Block biomeStrippedLogHorizAcrossBlock = biomeLogHorAcrossBlock; int biomeStrippedLogHorizAcrossMeta = biomeLogHorAcrossMeta;
        	// Try to see if stripped logs exist
        	if (biomeStrippedLogHorizAcrossBlock==Blocks.log || biomeStrippedLogHorizAcrossBlock==Blocks.log2)
        	{
            	if (biomeLogVertBlock == Blocks.log)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 1+(this.coordBaseMode%2!=0? 1:0)); biomeStrippedLogHorizAcrossBlock = (Block)blockObject[0]; biomeStrippedLogHorizAcrossMeta = (Integer)blockObject[1];
            	}
            	else if (biomeLogVertBlock == Blocks.log2)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 1+(this.coordBaseMode%2!=0? 1:0)); biomeStrippedLogHorizAcrossBlock = (Block)blockObject[0]; biomeStrippedLogHorizAcrossMeta = (Integer)blockObject[1];
            	}
        	}
            for(int[] uuvvww : new int[][]{
            	{2,1,0, 6,1,0}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogHorizAcrossBlock, biomeStrippedLogHorizAcrossMeta, biomeStrippedLogHorizAcrossBlock, biomeStrippedLogHorizAcrossMeta, false);	
            }
    		
    		
    		// Fences
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
    		for (int[] uuvvww : new int[][]{
    			// Support posts
    			{1,0,9, 1,0,9}, {7,0,9, 7,0,9}, 
    			{1,0,5, 1,0,5}, {7,0,5, 7,0,5}, 
    			{2,0,2, 2,0,2}, {6,0,2, 6,0,2}, 
    			{2,0,0, 2,0,0}, {4,0,0, 4,0,0}, {6,0,0, 6,0,0}, 
    			// Front hatching
    			{2,4,0, 6,4,0}, 
    			{7,3,2, 8,3,2}, {8,2,2, 10,2,2}, {7,3,0, 8,3,0}, {8,2,0, 10,2,0}, 
    			{2,4,1, 2,4,1}, {6,4,1, 6,4,1}, 
    			{8,1,2, 8,2,2}, {10,0,2, 10,1,2}, 
    			{2,2,0, 2,3,0}, {4,2,0, 4,3,0}, {6,2,0, 6,3,0}, {8,1,0, 8,2,0}, {10,0,0, 10,1,0}, 
    			// Windows
    			{1,3,7, 1,3,7}, {7,3,7, 7,3,7}, 
    			{2,3,3, 2,3,3}, {6,3,3, 6,3,4}, 
    			{3,3,2, 4,3,2}, {4,5,2, 4,5,2}, 
    			{3,5,5, 5,5,5}, {3,5,9, 5,5,9}, 
    			{4,5,6, 4,5,8}, 
    			{3,3,5, 3,3,5}, {5,3,5, 5,3,5}, 
    			// Tables
    			{6,2,6, 6,2,6},
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
    		}
    		// Foot foundation
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uvw : new int[][]{
    			{1,-1,9}, {7,-1,9}, 
    			{1,-1,5}, {7,-1,5}, 
    			{2,-1,2}, {6,-1,2}, 
    			{2,-1,0}, {4,-1,0}, {6,-1,0}, 
    			})
    		{
    			this.func_151554_b(world, biomeCobblestoneBlock, biomeCobblestoneMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
    			// Front wall
            	{3,2,2, 4,2,2}, {3,4,2, 5,4,2}, 
            	// Left wall
            	{2,1,3, 2,2,3}, {2,1,4, 2,1,4}, {2,4,3, 2,4,3}, 
            	{1,1,6, 1,2,8}, {1,3,6, 1,3,6}, {1,3,8, 1,3,8}, {1,4,6, 1,4,8}, 
            	// Right wall
            	{7,1,6, 7,2,8}, {7,3,6, 7,3,6}, {7,3,8, 7,3,8}, {7,4,6, 7,4,8}, 
            	{6,4,3, 6,4,4}, {6,1,3, 6,2,4}, 
            	// Back wall
            	{2,1,9, 6,4,9}, 
            	// Separator wall
            	{2,4,5, 6,4,5}, 
            	{2,3,5, 2,3,5}, {6,3,5, 6,3,5}, 
            	{2,2,5, 3,2,5}, {5,2,5, 6,2,5}, 
            	// Floor
            	{2,1,5, 6,1,8}, 
            	{3,1,2, 5,1,4}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{1,5,5, 1,5,9}, {3,6,5, 3,6,9}, {5,6,5, 5,6,9}, {7,5,5, 7,5,9}, 
    			{2,5,0, 2,5,3}, {4,6,0, 4,6,4}, {6,5,0, 6,5,4}, 
    			// Entry steps
    			{7,1,0, 7,1,2}, {9,0,0, 9,0,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{0,4,5, 0,4,9}, {2,5,5, 2,5,9}, {4,6,5, 4,6,9}, {6,5,5, 6,5,9}, {8,4,5, 8,4,9}, 
    			{1,4,0, 1,4,4}, {3,5,0, 3,5,4}, {5,5,0, 5,5,4}, {7,4,0, 7,4,4},
    			// Front porch
    			{2,1,1, 6,1,1},  
            	// Entry steps
    			{8,0,0, 8,0,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
        	
        	
            // Wooden pressure plate
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_pressure_plate, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodPressurePlateBlock = (Block)blockObject[0]; int biomeWoodPressurePlateMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		{6,3,6}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeWoodPressurePlateBlock, biomeWoodPressurePlateMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		// Porch
        		{3,3,0}, 
        		// Interior
        		{4,4,7}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
        	// Carpet (Black)
        	for(int[] uvwm : new int[][]{
        		// Carpet in front of the door prevents villagers from passing through
        		{3,2,4}, {5,2,4}, 
        		{4,2,3}, 
        		})
            {
    			this.placeBlockAtCurrentPosition(world, Blocks.carpet, (GeneralConfig.useVillageColors ? this.townColor : 15), uvwm[0], uvwm[1], uvwm[2], structureBB);
            }
        	
        	
            // Glazed terracotta
        	Object[] tryGlazedTerracotta;
        	for (int[] uvwoc : new int[][]{ // u,v,w, orientation, dye color
        		// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
        		{4,5,0, 2, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
           		})
        	{
        		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(uvwoc[4], StructureVillageVN.chooseGlazedTerracottaMeta(uvwoc[3], this.coordBaseMode));
        		if (tryGlazedTerracotta != null)
            	{
        			this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvwoc[0], uvwoc[1], uvwoc[2], structureBB);
            	}
        		else
        		{
        			this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, uvwoc[4], uvwoc[0], uvwoc[1], uvwoc[2], structureBB);
        		}
            }
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{5,2,2, 2, 1, 0}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
        	
        	
        	// Crafting Table
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.crafting_table, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCraftingTableBlock = (Block)blockObject[0]; int biomeCraftingTableMeta = (Integer)blockObject[1];
            for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
        		{2,2,6}, 
           		})
        	{
            	this.placeBlockAtCurrentPosition(world, biomeCraftingTableBlock, biomeCraftingTableMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Furnaces
            for (int[] uvwo : new int[][]{ // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
            	{2,2,4, 1}, 
            	})
            {
                this.placeBlockAtCurrentPosition(world, Blocks.furnace, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2]), StructureVillageVN.chooseFurnaceMeta(uvwo[3], this.coordBaseMode), 2);
            }
        	
        	
        	// Cobblestone wall
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{2,3,4, 2,4,4}, {2,6,4, 2,6,4}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, false);
            }
    		
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			{2,5,4, 2,5,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{3,2,8, 1, GeneralConfig.useVillageColors ? this.townColor3 : 10}, // Purple
            	{5,2,8, 3, GeneralConfig.useVillageColors ? this.townColor3 : 10}, // Purple
            })
            {
            	for (boolean isHead : new boolean[]{false, true})
            	{
            		int orientation = uvwoc[3];
            		int u = uvwoc[0] + (isHead?(new int[]{0,-1,0,1}[orientation]):0);
            		int v = uvwoc[1];
            		int w = uvwoc[2] + (isHead?(new int[]{-1,0,1,0}[orientation]):0);
            		ModObjects.setModBedBlock(world,
                			this.getXWithOffset(u, w),
                			this.getYWithOffset(v),
                			this.getZWithOffset(u, w),
                			StructureVillageVN.getBedOrientationMeta(orientation, this.coordBaseMode, isHead),
                			uvwoc[4]);
            	}
            }
        	
        	
    		// Villagers
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	if (VillageGeneratorConfigHandler.spawnVillagersInResidences)
            	{
	            	int[][] villagerPositions = new int[][]{
	                	{3,2,7, -1, 0}, 
	                	{5,2,7, -1, 0}, 
	        			};
	        		int countdownToAdult = 1+random.nextInt(villagerPositions.length); // One of the villagers here is an adult
	            	
	        		for (int[] ia :villagerPositions)
	        		{
	        			EntityVillager entityvillager = new EntityVillager(world);
	        			entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], (--countdownToAdult)==0?0:Math.min(random.nextInt(24001)-12000,0));
	        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)this.getYWithOffset(ia[1]) + 1.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
	                    		random.nextFloat()*360F, 0.0F);
	                    world.spawnEntityInWorld(entityvillager);
	        		}
            	}
            }
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    // --- Shepherd House 1 --- //
    // designed by AstroTibs
    
    public static class SwampShepherdHouse1 extends StructureVillagePieces.Village
    {
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList<Integer>();
    	public FunctionsVN.VillageType villageType = null;
    	public FunctionsVN.MaterialType materialType = null;
    	public boolean disallowModSubs = false;
    	public int townColor = -1;
    	public int townColor2 = -1;
    	public int townColor3 = -1;
    	public int townColor4 = -1;
    	public int townColor5 = -1;
    	public int townColor6 = -1;
    	public int townColor7 = -1;
    	public String namePrefix = "";
    	public String nameRoot = "";
    	public String nameSuffix = "";
    	public BiomeGenBase biome = null;
    	
    	private static final String[] foundationPattern = new String[]{
				"         ", 
				" FFFFFFF ", 
				" FFFFFFF ", 
				" FFFFFFF ", 
				" FFFFFFF ", 
				" FFFFFFF ", 
				" FFFFFFF ", 
				" FFFFFFFF", 
				"        P"
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 8;
		// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1+8; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 7;
    	private static final int DECREASE_MAX_U = -1;
    	private static final int INCREASE_MIN_W = -1;
    	private static final int DECREASE_MAX_W = 6;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampShepherdHouse1() {}
    	
    	public SwampShepherdHouse1(StructureVillageVN.StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start != null)
    		{
    			this.villageType = start.villageType;
    			this.materialType = start.materialType;
    			this.disallowModSubs = start.disallowModSubs;
    			this.townColor = start.townColor;
    			this.townColor2 = start.townColor2;
    			this.townColor3 = start.townColor3;
    			this.townColor4 = start.townColor4;
    			this.townColor5 = start.townColor5;
    			this.townColor6 = start.townColor6;
    			this.townColor7 = start.townColor7;
    			this.namePrefix = start.namePrefix;
    			this.nameRoot = start.nameRoot;
    			this.nameSuffix = start.nameSuffix;
    			this.biome = start.biome;
    		} 
    	}
    	
    	public static SwampShepherdHouse1 buildComponent(StructureVillageVN.StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, 8, STRUCTURE_DEPTH, coordBaseMode);
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampShepherdHouse1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
			// In the event that this village construction is resuming after being unloaded
			// you may need to reestablish the village name/color/type info
			if (
					this.townColor==-1
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
			}
			
			WorldChunkManager chunkManager= world.getWorldChunkManager();
			int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
			BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
			Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
			if (this.villageType==null)
			{
				try {
					String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
					if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
					else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
					}
				catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
			}
			
			if (this.materialType==null)
			{
				try {
					String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
					if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
					else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
					}
				catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
			}
			
			if (!this.disallowModSubs)
			{
				try {
					String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
					if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
					else {this.disallowModSubs = false;}
					}
				catch (Exception e) {this.disallowModSubs = false;}
			}
			// Reestablish biome if start was null or something
			if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
			Object[] blockObject;
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
			// Establish top and filler blocks, substituting Grass and Dirt if they're null
			Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
			Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
			
			// Clear space above
			for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
				this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
			}}
			
			// Follow the blueprint to set up the starting foundation
			for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
				
				String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
				int posX = this.getXWithOffset(u, w);
				int posY = this.getYWithOffset(GROUND_LEVEL-1);
				int posZ = this.getZWithOffset(u, w);
						
				if (unitLetter.equals("F"))
				{
					// If marked with F: fill with dirt foundation
					this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
				}
				else if (unitLetter.equals("P"))
				{
					// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
					this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
					StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
				}
				else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
				{
					// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
					this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
					this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
				}
			}}
			
			// Yeah so I screwed up and accidentally deleted this, so had to recover it from decompiling :|
			// Sorry if this is illegible
			
			// Grass
    		for (int[] uuvvww : new int[][] { { 1, 0, 1, 7, 0, 7 } })
    		{
    			fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);
    		}
    		
    		// Logs (Vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs);
    		Block biomeLogVertBlock = (Block)blockObject[0];
    		int biomeLogVertMeta = ((Integer)blockObject[1]).intValue();
    		for (int[] uuvvww : new int[][] { { 1, 1, 7, 1, 5, 7 }, { 7, 1, 7, 7, 5, 7 }, { 1, 1, 1, 1, 5, 1 }, { 7, 1, 1, 7, 5, 1 } })
    		{
    			fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false); 
    		}
    		
    		// Logs (Along)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4 + ((this.coordBaseMode % 2 == 0) ? 4 : 0), this.materialType, this.biome, this.disallowModSubs);
    		Block biomeLogHorAlongBlock = (Block)blockObject[0];
    		int biomeLogHorAlongMeta = ((Integer)blockObject[1]).intValue();
    		for (int[] uuvvww : new int[][] { { 1, 6, 1, 1, 6, 7 }, { 7, 6, 1, 7, 6, 7 } })
    		{
    			fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAlongBlock, biomeLogHorAlongMeta, biomeLogHorAlongBlock, biomeLogHorAlongMeta, false); 
    		}
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs);
    		Block biomePlankBlock = (Block)blockObject[0];
    		int biomePlankMeta = ((Integer)blockObject[1]).intValue();
    		for (int[] uuvvww : new int[][] { 
    				{ 2, 3, 7, 5, 4, 7 }, { 4, 5, 7, 5, 5, 7 }, { 6, 3, 7, 6, 3, 7 }, { 1, 3, 2, 1, 4, 6 }, { 1, 5, 2, 1, 5, 2 }, { 1, 5, 6, 1, 5, 6 }, { 7, 3, 2, 7, 4, 6 }, { 7, 5, 2, 7, 5, 2 }, { 7, 5, 6, 7, 5, 6 }, { 2, 3, 1, 6, 4, 1 }, 
    				{ 4, 5, 1, 4, 5, 1 }, { 2, 3, 2, 3, 3, 6 }, { 4, 3, 6, 4, 3, 6 }, { 5, 3, 2, 6, 3, 6 }, { 8, 0, 1, 8, 0, 1 }, { 1, 7, 4, 7, 7, 4 } })
    		{
    			fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);
    		}
    		
    		// Fence
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs);
    		Block biomeFenceBlock = (Block)blockObject[0];
    		for (int[] uuvvww : new int[][] { { 4, 3, 8, 4, 4, 8 }, { 0, 3, 4, 0, 4, 4 }, { 4, 3, 0, 4, 4, 0 }, { 2, 1, 7, 6, 1, 7 }, { 1, 1, 2, 1, 1, 6 }, { 7, 1, 2, 7, 1, 6 }, { 2, 1, 1, 6, 1, 1 }, { 4, 1, 4, 4, 1, 4 }, { 4, 1, 5, 4, 2, 5 } })
    		{
    			fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
    		}
    		
    		// Wooden Stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs);
    		Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][] { { 4, 3, 5, 3 }, { 4, 2, 4, 3 }, { 4, 1, 3, 3 } })
    		{
    			placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, getMetadataWithOffset(Blocks.oak_stairs, uvwo[3] % 4) + uvwo[3] / 4 * 4, uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		// Wooden Slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs);
    		Block biomeWoodSlabBottomBlock = (Block)blockObject[0];
    		int biomeWoodSlabBottomMeta = ((Integer)blockObject[1]).intValue();
    		for (int[] uuvvww : new int[][] { { 2, 6, 7, 6, 6, 7 }, { 1, 7, 5, 7, 7, 5 }, { 1, 7, 3, 7, 7, 3 }, { 2, 6, 1, 6, 6, 1 }, { 8, 1, 2, 8, 1, 2 }, { 8, 2, 4, 8, 2, 4 }, { 8, 3, 6, 8, 3, 6 } })
    		{
    			fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);
    		}
    		
    		// Wooden Slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs);
    		Block biomeWoodSlabTopBlock = (Block)blockObject[0];
    		int biomeWoodSlabTopMeta = ((Integer)blockObject[1]).intValue();
    		for (int[] uuvvww : new int[][] { { 2, 6, 6, 6, 6, 6 }, { 2, 6, 2, 6, 6, 2 }, { 8, 1, 3, 8, 1, 3 }, { 8, 2, 5, 8, 2, 5 }, { 8, 3, 7, 8, 3, 8 }, { 5, 3, 8, 7, 3, 8 } })
    		{
    			fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);
    		}
    		
    		// Hanging Lanterns
    		blockObject = ModObjects.chooseModLanternBlock(true);
    		Block biomeHangingLanternBlock = (Block)blockObject[0];
    		int biomeHangingLanternMeta = ((Integer)blockObject[1]).intValue();
    		for (int[] uvw : new int[][] { { 4, 2, 8 }, { 0, 2, 4 }, { 4, 2, 0 } })
    		{
    			placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		// Sitting Lanterns
    		blockObject = ModObjects.chooseModLanternBlock(false);
    		Block biomeSittingLanternBlock = (Block)blockObject[0];
    		int biomeSittingLanternMeta = ((Integer)blockObject[1]).intValue();
    		for (int[] uvw : new int[][] { { 7, 2, 4 } })
    		{
    			placeBlockAtCurrentPosition(world, biomeSittingLanternBlock, biomeSittingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		// Torches
    		for (int[] uvwo : new int[][] { { 2, 6, 4, 1 }, { 6, 6, 4, 3 } })
    		{
    			placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		// Trapdoors (Top Horizontal)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapdoor, 0, this.materialType, this.biome, this.disallowModSubs);
    		Block biomeTrapdoorBlock = (Block)blockObject[0];
    		int biomeTrapdoorMeta = ((Integer)blockObject[1]).intValue();
    		for (int[] uuvvww : new int[][] { { 4, 3, 4, 3 }, { 4, 3, 3, 3 }, { 4, 3, 2, 3 } })
    		{
    			placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, true, false), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
    		}
    		
    		// Wooden Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs);
    		Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][] { { 6, 4, 7, 0, 1, 1 } }) {
    			for (int height = 0; height <= 1; height++)
    			{
    				placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, (uvwoor[4] == 1), (uvwoor[5] == 1))[height], uvwoor[0], uvwoor[1] + height, uvwoor[2], structureBB);
    			}
    		}
    		
    		// Glass Panes
    		for (int[] uuvvww : new int[][] { { 2, 5, 7, 3, 5, 7 }, { 1, 5, 3, 1, 5, 5 }, { 7, 5, 3, 7, 5, 5 }, { 2, 5, 1, 3, 5, 1 }, { 5, 5, 1, 6, 5, 1 } })
    		{
    			fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.glass_pane, 0, Blocks.glass_pane, 0, false);
    		}
    		
    		// Polished Diorite Blocks
    		blockObject = ModObjects.chooseModPolishedDioriteObject();
    		if (blockObject == null) {
    			blockObject = ModObjects.chooseModDioriteObject();
    			if (blockObject == null)
    				blockObject = ModObjects.chooseModSmoothStoneBlockObject(); 
    		} 
    		Block polishedDioriteBlock = (Block)blockObject[0];
    		int polishedDioriteMeta = ((Integer)blockObject[1]).intValue();
    		for (int[] uvw : new int[][] { { 6, 4, 2 } })
    		{
    			placeBlockAtCurrentPosition(world, polishedDioriteBlock, polishedDioriteMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		// Potted Random Flower
    		for (int[] uvw : new int[][] { { 6, 5, 2 } }) {
    			int i = uvw[0], v = uvw[1], j = uvw[2];
    			int x = getXWithOffset(i, j);
    			int y = getYWithOffset(v);
    			int z = getZWithOffset(i, j);
    			Object[] cornflowerObject = ModObjects.chooseModCornflower(), lilyOfTheValleyObject = ModObjects.chooseModLilyOfTheValley();
    			int randomPottedPlant = random.nextInt(10) - 1;
    			if (randomPottedPlant == -1) {
    				StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, (Block)Blocks.yellow_flower, 0);
    			} else {
    				StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, (Block)Blocks.red_flower, randomPottedPlant);
    			} 
    		}
    		
    		// Loom
    		blockObject = ModObjects.chooseModLoom(biomePlankMeta);
    		Block loomBlock = (Block)blockObject[0];
    		int loomMeta = ((Integer)blockObject[1]).intValue();
    		for (int[] uvw : new int[][] { { 2, 4, 2, 0 } })
    		{
    			placeBlockAtCurrentPosition(world, loomBlock, loomMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		// Wool
    		for (int[] uvwo : new int[][] { { 2, 4, 6, GeneralConfig.useVillageColors ? this.townColor2 : 13 }, { 2, 4, 5, GeneralConfig.useVillageColors ? this.townColor3 : 10 }, { 2, 5, 6, GeneralConfig.useVillageColors ? this.townColor4 : 14 } })
    		{
    			placeBlockAtCurrentPosition(world, Blocks.wool, uvwo[3], uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		// Carpet
    		for (int[] uuvvww : new int[][]
    		{
    			// Carpet in front of the door prevents villagers from passing through
    			{6,4,3, 6,4,5, GeneralConfig.useVillageColors ? this.townColor4 : 14 }, 
    			{5,4,2, 5,4,5, GeneralConfig.useVillageColors ? this.townColor4 : 14 }
    		})
    		{
    			fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.carpet, uuvvww[6], Blocks.carpet, uuvvww[6], false);
    		}
    		
    		// Unkempt Grass
    		for (int[] uvwg : new int[][]{
    			{2,1,5, 0}, 
    			{3,1,2, 0}, 
    			{5,1,5, 0}, 
    			{6,1,6, 0} 
    			})
    		{
    			if (uvwg[3] == 0) // Tall grass
    			{
    				placeBlockAtCurrentPosition(world, (Block)Blocks.tallgrass, 1, uvwg[0], uvwg[1], uvwg[2], structureBB);
    			}
    			else if (uvwg[3] == 1) // Double-tall grass
    			{
    				placeBlockAtCurrentPosition(world, (Block)Blocks.double_plant, 2, uvwg[0], uvwg[1], uvwg[2], structureBB);
    				placeBlockAtCurrentPosition(world, (Block)Blocks.double_plant, 11, uvwg[0], uvwg[1] + 1, uvwg[2], structureBB);
    			}
    			else if (uvwg[3] == 2) // Fern
    			{
    				placeBlockAtCurrentPosition(world, (Block)Blocks.tallgrass, 2, uvwg[0], uvwg[1], uvwg[2], structureBB);
    			}
    			else // Tall fern
    			{
    				placeBlockAtCurrentPosition(world, (Block)Blocks.double_plant, 3, uvwg[0], uvwg[1], uvwg[2], structureBB);
    				placeBlockAtCurrentPosition(world, (Block)Blocks.double_plant, 11, uvwg[0], uvwg[1] + 1, uvwg[2], structureBB);
    			} 
    		}
    		
    		// Entities
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated = true;
    			
    			int s = random.nextInt(21);
    			
    			int i = (s <= 1) ? 2 : ((s <= 16) ? ((s - 2) / 5 + 3) : 6);
    			int v = 4;
    			int j = (s <= 1) ? (s - 3) : ((s <= 16) ? ((s - 2) % 5 + 2) : (s - 14));
    			
    			EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 0, 3, 0); // Shepherd
    			entityvillager.setLocationAndAngles(getXWithOffset(i, j) + 0.5D, getYWithOffset(v) + 0.5D, getZWithOffset(i, j) + 0.5D, random.nextFloat() * 360.0F, 0.0F);
    			
    			world.spawnEntityInWorld((Entity)entityvillager);
    			
    			// Sheep
    			for (int[] uvw : new int[][] { { 3, 1, 6 }, { 6, 1, 4 } }) {
    				EntitySheep entitySheep = new EntitySheep(world);
    				IEntityLivingData ientitylivingdata = entitySheep.onSpawnWithEgg(null);
    				entitySheep.setLocationAndAngles(getXWithOffset(uvw[0], uvw[2]) + 0.5D, getYWithOffset(uvw[1]) + 0.5D, getZWithOffset(uvw[0], uvw[2]) + 0.5D, random.nextFloat() * 360.0F, 0.0F);
    				world.spawnEntityInWorld((Entity)entitySheep);
    			} 
    		}
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems)
    		{
    			StructureVillageVN.cleanEntityItems(world, this.boundingBox);
    		}
    		
    		return true;
    	}
    	
		/**
		 * Returns the villager type to spawn in this component, based on the number
		 * of villagers already spawned.
		 */
    	protected int getVillagerType(int number) {return 0;}
    }
    
    
	// --- Shepherd House 2 --- //
	// designed by AstroTibs

	public static class SwampShepherdHouse2 extends StructureVillagePieces.Village
	{
		// Stuff to be used in the construction
		public boolean entitiesGenerated = false;
		public ArrayList<Integer> decorHeightY = new ArrayList();
		public FunctionsVN.VillageType villageType=null;
		public FunctionsVN.MaterialType materialType=null;
		public boolean disallowModSubs=false;
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
				" FFFFF  ",
				"FFFFFFF ",
				"FFFFFFF ",
				"FFFFFFF ",
				"FFFFFFFF",
				"FFFFFFFF",
				"FFFFFFFF",
				" FFFFFFF",
				" FFFFF P",
				"  FFF  P",
				"   PPPPP",
		};
		// Here are values to assign to the bounding box
		public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
		public static final int STRUCTURE_DEPTH = foundationPattern.length;
		public static final int STRUCTURE_HEIGHT = 8;
		// Values for lining things up
		private static final int GROUND_LEVEL = 4; // Spaces above the bottom of the structure considered to be "ground level"
		public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
		private static final int INCREASE_MIN_U = 3;
		private static final int DECREASE_MAX_U = 0;
		private static final int INCREASE_MIN_W = 0;
		private static final int DECREASE_MAX_W = 0;
		
		private int averageGroundLevel = -1;
		
		public SwampShepherdHouse2() {}

		public SwampShepherdHouse2(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
		{
			super();
			this.coordBaseMode = coordBaseMode;
			this.boundingBox = boundingBox;
			// Additional stuff to be used in the construction
			if (start!=null)
			{
				this.villageType=start.villageType;
				this.materialType=start.materialType;
				this.disallowModSubs=start.disallowModSubs;
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
		
		public static SwampShepherdHouse2 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
			
			return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampShepherdHouse2(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
									this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
									this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
							true, MEDIAN_BORDERS, this.coordBaseMode);
					
					if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
					
					this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
				}
			}
			
			// In the event that this village construction is resuming after being unloaded
			// you may need to reestablish the village name/color/type info
			if (
					this.townColor==-1
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
			}
			
			WorldChunkManager chunkManager= world.getWorldChunkManager();
			int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
			BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
			Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
			if (this.villageType==null)
			{
				try {
					String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
					if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
					else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
					}
				catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
			}
			
			if (this.materialType==null)
			{
				try {
					String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
					if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
					else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
					}
				catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
			}
			
			if (!this.disallowModSubs)
			{
				try {
					String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
					if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
					else {this.disallowModSubs = false;}
					}
				catch (Exception e) {this.disallowModSubs = false;}
			}
			// Reestablish biome if start was null or something
			if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
			Object[] blockObject;
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
			// Establish top and filler blocks, substituting Grass and Dirt if they're null
			Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
			Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
			
			// Clear space above
			for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
				this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
			}}
			
			// Follow the blueprint to set up the starting foundation
			for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
				
				String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
				int posX = this.getXWithOffset(u, w);
				int posY = this.getYWithOffset(GROUND_LEVEL-1);
				int posZ = this.getZWithOffset(u, w);
						
				if (unitLetter.equals("F"))
				{
					// If marked with F: fill with dirt foundation
					this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
				}
				else if (unitLetter.equals("P"))
				{
					// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
					this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
					StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
				}
				else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
				{
					// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
					this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
					this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
				}
			}}
			
			
			// Dirt
			for(int[] uuvvww : new int[][]{
				{1,4,4, 1,4,6}, 
				{2,4,3, 2,4,5}, {2,4,8, 2,4,9}, 
				{3,4,5, 3,4,5}, {3,4,9, 3,4,9}, 
				{4,4,3, 4,4,9}, {4,4,8, 4,4,9}, 
				{5,4,3, 5,4,5}, {5,4,8, 5,4,9}, 
				{6,4,6, 6,4,6}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);	
			}
			
			
			// Grass
			for(int[] uuvvww : new int[][]{
				{0,4,4, 0,4,9}, 
				{1,4,2, 1,4,3}, {1,4,7, 1,4,10}, 
				{2,4,1, 2,4,2}, {2,4,10, 5,4,10}, 
				{4,4,1, 4,4,2}, 
				{5,4,2, 5,4,2}, {5,4,9, 5,4,9}, 
				{6,4,3, 6,4,4}, {6,4,7, 6,4,9}, 
				{7,4,4, 7,4,6}, 
				// Top of hill
				{1,5,4, 1,5,6}, 
				{2,5,3, 4,5,9}, 
				{5,5,3, 5,5,8}, 
				{6,5,6, 6,5,6}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);	
			}
    		
    		
    		// Grass Path
        	for (int[] uvw : new int[][]{
        		{7,4,4}, {7,4,5}, 
            	}) {
        		int posX = this.getXWithOffset(uvw[0], uvw[2]);
    			int posY = this.getYWithOffset(uvw[1]);
    			int posZ = this.getZWithOffset(uvw[0], uvw[2]);
        		StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
            }

    		        	
        	// Clear out leaves to allow player to access the walkway
            for(int[] uuvvww : new int[][]{
    			// Basement
            	{1,1,6, 5,3,9}, 
            	// Entrance
            	{3,1,4, 3,4,4}, 
            	{3,2,3, 3,4,3}, 
            	{3,3,2, 3,4,2}, 
            	{3,4,1, 3,4,1}, 
    			})
    		{
            	this.fillWithAir(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5]);	
    		}
			
			
			// Cobblestone part 1
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
			for(int[] uuvvww : new int[][]{
				// Entrance
				{3,3,5, 3,3,5}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
			}
			
			
			// Fences
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
			for (int[] uuvvww : new int[][]{
				{1,6,4, 1,6,6}, 
				{2,6,6, 2,6,9}, 
				{3,6,9, 4,6,9}, 
				{4,6,8, 5,6,8}, 
				{5,6,3, 5,6,4}, {5,6,6, 5,6,7}, 
				{2,6,3, 4,6,3}, 
				{2,6,4, 2,6,4}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
			}
			
			
			// Torches
			for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
				// Entry
				{3,3,4, 2}, 
				// Animal pen
				{2,7,6, -1}, 
				{5,7,3, -1}, 
				{5,7,7, -1}, 
				}) {
				this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
			}
			
			
			// Cobblestone part 2
			for(int[] uuvvww : new int[][]{
				// Walls
				{0,0,5, 0,3,9}, 
				{1,0,9, 1,3,9}, 
				{1,0,10, 5,3,10}, 
				{5,0,9, 5,3,9}, 
				{6,0,5, 6,3,9}, 
				{1,0,5, 2,3,5}, {4,0,5, 5,3,5}, 
				// Floor
				{1,0,6, 1,0,8}, {5,0,6, 5,0,8}, 
				{3,0,4, 3,0,5}, 
				// Left entrance wall
				{2,1,3, 2,3,4}, {4,1,3, 4,3,4}, 
				{2,2,2, 2,3,2}, {4,2,2, 4,3,2}, 
				{2,3,1, 2,3,1}, {4,3,1, 4,3,1}, 
				// Ceiling
				{2,4,6, 4,4,7}, {3,4,8, 3,4,8}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
			}
			
			
			// Cobblestone stairs
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
			for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
				// Entry
				{3,3,1, 2}, 
				{3,2,2, 2}, 
				{3,1,3, 2}, 
				// Ceiling trim
				{1,3,6, 1+4}, {1,3,7, 1+4}, {1,3,8, 1+4}, 
				{2,3,8, 3+4}, 
				{2,3,9, 1+4}, 
				{3,3,9, 3+4}, 
				{4,3,9, 0+4}, 
				{4,3,8, 3+4}, 
				{5,3,6, 0+4}, {5,3,7, 0+4}, {5,3,8, 0+4}, 
				})
			{
				this.placeBlockAtCurrentPosition(world, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);
			}
            
            
        	// Fence Gate (Along)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence_gate, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceGateBlock = (Block)blockObject[0]; int biomeFenceGateMeta = (Integer)blockObject[1];
        	for(int[] uvw : new int[][]{
            	{5,6,5}, 
            	})
            {
        		this.placeBlockAtCurrentPosition(world, biomeFenceGateBlock, StructureVillageVN.getMetadataWithOffset(biomeFenceGateBlock, (biomeFenceGateMeta+1)%8, this.coordBaseMode), uvw[0], uvw[1], uvw[2], structureBB);
            }
			
			
			// Wood stairs
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
			for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
				{7,4,3, 3}, 
				{6,5,5, 1}, 
				// Table
				{5,1,8, 3+4}, 
				{5,1,6, 2+4}, 
				})
			{
				this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
			}
			
			
			// Wooden slabs (Top)
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
			for(int[] uuvvww : new int[][]{
				// Table
				{5,1,7, 5,1,7}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
			}
            
        	
            // Potted Cactus
            this.placeBlockAtCurrentPosition(world, Blocks.flower_pot, 9, 5,2,7, structureBB); // 9 is cactus
			
			
			// Hanging Lanterns
			blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
			for (int[] uvw : new int[][]{
				{3,3,8}, 
				}) {
				this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
			}
			
			
			// Doors
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
			for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
				// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
				{3,1,5, 0, 1, 1}, 
			})
			{
				for (int height=0; height<=1; height++)
				{
					this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
							uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
				}
			}
			
			
			// Loom
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
			blockObject = ModObjects.chooseModLoom(biomePlankMeta); Block loomBlock = (Block) blockObject[0]; int loomMeta = (Integer) blockObject[1];
			for(int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
				{1,1,7, 1}, 
				})
			{
				this.placeBlockAtCurrentPosition(world, loomBlock, loomMeta, uvw[0], uvw[1], uvw[2], structureBB);
			}
			
			
			// Wool - carpet in front of the door prevents villagers from passing through
			for (int[] uvwo : new int[][]{
				{1,1,6, (GeneralConfig.useVillageColors ? this.townColor4 : 14)}, // Red
				
        		// Floor carpeting
        		{2,0,9, (GeneralConfig.useVillageColors ? this.townColor4 : 14)}, // Red
        		{3,0,9, (GeneralConfig.useVillageColors ? this.townColor : 15)}, // Black
        		{4,0,9, (GeneralConfig.useVillageColors ? this.townColor4 : 14)}, // Red
        		
        		{2,0,8, (GeneralConfig.useVillageColors ? this.townColor : 15)}, // Black
        		{3,0,8, (GeneralConfig.useVillageColors ? this.townColor4 : 14)}, // Red
        		{4,0,8, (GeneralConfig.useVillageColors ? this.townColor : 15)}, // Black
        		
        		{2,0,7, (GeneralConfig.useVillageColors ? this.townColor4 : 14)}, // Red
        		{3,0,7, (GeneralConfig.useVillageColors ? this.townColor : 15)}, // Black
        		{4,0,7, (GeneralConfig.useVillageColors ? this.townColor4 : 14)}, // Red
        		
        		{2,0,6, (GeneralConfig.useVillageColors ? this.townColor : 15)}, // Black
        		{3,0,6, (GeneralConfig.useVillageColors ? this.townColor4 : 14)}, // Red
        		{4,0,6, (GeneralConfig.useVillageColors ? this.townColor : 15)}, // Black
			})
			{
				this.placeBlockAtCurrentPosition(world, Blocks.wool, uvwo[3], uvwo[0], uvwo[1], uvwo[2], structureBB);
			}
        	
        	
			// Solid color banners
    		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
    		if (testForBanner!=null)
			{
    			for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color
    				// 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    				
    				{2,2,9, 2, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
    				{3,2,9, 2, GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
    				{4,2,9, 2, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
    			})
    			{
        			int bannerXBB = uvwoc[0];
        			int bannerYBB = uvwoc[1];
        			int bannerZBB = uvwoc[2];
        			
        			int bannerX = this.getXWithOffset(bannerXBB, bannerZBB);
        			int bannerY = this.getYWithOffset(bannerYBB);
                    int bannerZ = this.getZWithOffset(bannerXBB, bannerZBB);
                    
                    boolean isHanging = true;
                    
                	// Set the banner and its orientation
    				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
    				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(uvwoc[3], this.coordBaseMode, isHanging), 2);
    				
    				// Set the tile entity
    				TileEntity tilebanner = new TileEntityBanner();
    				NBTTagCompound modifystanding = new NBTTagCompound();
    				tilebanner.writeToNBT(modifystanding);
    				modifystanding.setBoolean("IsStanding", !isHanging);
    				modifystanding.setInteger("Base", uvwoc[4]);
    				tilebanner.readFromNBT(modifystanding);
    				
            		world.setTileEntity(bannerX, bannerY, bannerZ, tilebanner);
    			}
			}
			
			
			// Unkempt Grass
			for (int[] uvwg : new int[][]{ // g is grass type
				{1,5,8, 0}, 
				{3,6,8, 0}, 
				{2,5,10, 0}, 
				{5,5,9, 0}, 
				{6,5,7, 0}, 
			})
			{
				if (uvwg[3]==0) // Short grass
				{
					this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 1, uvwg[0], uvwg[1], uvwg[2], structureBB);
				}
				else if (uvwg[3]==1) // Tall grass
				{
					this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 2, uvwg[0], uvwg[1], uvwg[2], structureBB);
					this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 11, uvwg[0], uvwg[1]+1, uvwg[2], structureBB);
				}
				else if (uvwg[3]==2) // Fern
				{
					this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 2, uvwg[0], uvwg[1], uvwg[2], structureBB);
				}
				else // Large Fern
				{
					this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 3, uvwg[0], uvwg[1], uvwg[2], structureBB);
					this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 11, uvwg[0], uvwg[1]+1, uvwg[2], structureBB);
				}
			}
        	
        	
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 1;
        	int chestV = 1;
        	int chestW = 8;
        	int chestO = 1; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo("vn_shepherd");
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
			
			
			// Entities
			if (!this.entitiesGenerated)
			{
				this.entitiesGenerated=true;
				
				// Villager
				int u = 2+random.nextInt(3);
				int v = 1;
				int w = 6+random.nextInt(5);
				
				EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 0, 3, 0); // Shepherd
				
				entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
				world.spawnEntityInWorld(entityvillager);
				
				
				// Sheep in the yard
				for (int[] uvw : new int[][]{
					{3,6,6}, 
					})
				{
					EntityLiving animal = new EntitySheep(world);
					IEntityLivingData ientitylivingdata = animal.onSpawnWithEgg(null); // To give the animal random spawning properties (horse pattern, sheep color, etc)
					
					animal.setLocationAndAngles((double)this.getXWithOffset(uvw[0], uvw[2]) + 0.5D, (double)this.getYWithOffset(uvw[1]) + 0.5D, (double)this.getZWithOffset(uvw[0], uvw[2]) + 0.5D, random.nextFloat()*360F, 0.0F);
					world.spawnEntityInWorld(animal);
					
					// Dirt block underneath
					//this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, uvw[0], uvw[1]-1, uvw[2], structureBB);
				}
			}
			
			// Clean items
			if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
			return true;
		}
		
		/**
		 * Returns the villager type to spawn in this component, based on the number
		 * of villagers already spawned.
		 */
		@Override
		protected int getVillagerType (int number) {return 0;}
	}
    
    
    // --- Small House 1 --- //
    // designed by AstroTibs
    
    public static class SwampSmallHouse1 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"  FFF",
    			"FFFFF",
    			"FFFFF",
    			"FFFFF",
    			"FFFFF",
    			"FFFFF",
    			"FFFFF",
    			"FFFFF",
    			"FFFFF",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 6;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 2;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampSmallHouse1() {}

    	public SwampSmallHouse1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampSmallHouse1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampSmallHouse1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
    		
    		
    		// Logs (Vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{4,1,0, 4,1,0},
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);	
    		}
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
    			// Entry/floor
    			{1,0,0, 1,0,1}, 
    			{0,0,2, 4,0,7}, 
    			// Front wall
    			{0,1,4, 0,3,4}, {1,3,4, 1,3,4}, {2,1,4, 2,3,4}, {3,1,4, 3,1,4}, {3,3,4, 3,3,4}, {4,1,4, 4,3,4}, 
    			// Left wall
    			{0,1,5, 0,1,5}, {0,3,5, 0,3,5}, {0,1,6, 0,3,6}, 
    			// Right wall
    			{4,1,5, 4,1,5}, {4,3,5, 4,3,5}, {4,1,6, 4,3,6}, 
    			// Back wall
    			{0,0,7, 1,3,7}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
        	
        	            
            // Fences
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
            for (int[] uuvvww : new int[][]{
            	// Front deck
            	{0,1,0}, {2,1,0}, 
            	// Awning support
            	{0,1,2}, {0,2,2}, {4,1,2}, {4,2,2}, 
            	// Windows
            	{0,2,5}, {3,2,4}, {4,2,5}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
    		
    		
    		// Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{0,4,5, 0,4,6}, {4,4,5, 4,4,6}, 
    			// Chimney
    			{2,0,8, 4,3,8}, {2,0,7, 2,0,7}, {4,0,7, 4,0,7}, {2,1,7, 4,3,7}, 
    			{3,4,7, 3,4,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
        	
        	
        	// Cobblestone wall
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{3,5,7, 3,5,7}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, false);
            }
    		
    		
    		// Cobblestone Slab (lower)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabLowerBlock = (Block)blockObject[0]; int biomeCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{0,3,2, 1,3,2}, {3,3,2, 4,3,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Cobblestone Slab (upper)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3+8, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabUpperBlock = (Block)blockObject[0]; int biomeCobblestoneSlabUpperMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{0,3,3, 0,3,3}, {4,3,3, 4,3,3}, 
    			// Ceiling
    			{1,4,5, 3,4,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneSlabUpperBlock, biomeCobblestoneSlabUpperMeta, biomeCobblestoneSlabUpperBlock, biomeCobblestoneSlabUpperMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone Slab (lower)
    		Block biomeMossyCobblestoneSlabLowerBlock = biomeCobblestoneSlabLowerBlock; int biomeMossyCobblestoneSlabLowerMeta = biomeCobblestoneSlabLowerMeta;
    		blockObject = ModObjects.chooseModMossyCobblestoneSlabBlock(false);
    		if (blockObject != null)
    		{
    			blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs);
    			biomeMossyCobblestoneSlabLowerBlock = (Block)blockObject[0]; biomeMossyCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		}
    		for(int[] uuvvww : new int[][]{
    			{2,3,2, 2,3,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone Slab (upper)
    		Block biomeMossyCobblestoneSlabUpperBlock = biomeCobblestoneSlabUpperBlock; int biomeMossyCobblestoneSlabUpperMeta = biomeCobblestoneSlabUpperMeta;
    		blockObject = ModObjects.chooseModMossyCobblestoneSlabBlock(true);
    		if (blockObject != null)
    		{
    			blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs);
    			biomeMossyCobblestoneSlabUpperBlock = (Block)blockObject[0]; biomeMossyCobblestoneSlabUpperMeta = (Integer)blockObject[1];
    		}
    		for(int[] uuvvww : new int[][]{
    			{1,3,3, 3,3,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneSlabUpperBlock, biomeMossyCobblestoneSlabUpperMeta, biomeMossyCobblestoneSlabUpperBlock, biomeMossyCobblestoneSlabUpperMeta, false);	
    		}
    		
            
            // Cobblestone stairs
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
        	for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			{0,4,7, 2}, {1,4,7, 2}, 
    			{0,5,6, 2}, {1,5,6, 2}, {2,5,6, 2}, {3,5,6, 2}, 
    			{0,5,5, 3}, {1,5,5, 3}, {3,5,5, 3}, 
    			{0,4,4, 3}, {3,4,4, 3}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
        	
        	
    		// Mossy Cobblestone stairs
    		Block modblock = ModObjects.chooseModMossyCobblestoneStairsBlock();
    		if (modblock==null) {modblock = Blocks.stone_stairs;}
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(modblock, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			{2,4,7, 2}, {4,4,7, 2}, 
    			{4,5,6, 2}, 
    			{2,5,5, 3}, {4,5,5, 3}, 
    			{1,4,4, 3}, {2,4,4, 3}, {4,4,4, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeMossyCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		{3,2,2}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{1,1,4, 2, 1, 0}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
        	
        	
        	// Furnaces
            for (int[] uvwo : new int[][]{ // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
            	{3,1,7, 2}, 
            	})
            {
                this.placeBlockAtCurrentPosition(world, Blocks.furnace, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2]), StructureVillageVN.chooseFurnaceMeta(uvwo[3], this.coordBaseMode), 2);
            }
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{2,1,5, 3, GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
            })
            {
            	for (boolean isHead : new boolean[]{false, true})
            	{
            		int orientation = uvwoc[3];
            		int u = uvwoc[0] + (isHead?(new int[]{0,-1,0,1}[orientation]):0);
            		int v = uvwoc[1];
            		int w = uvwoc[2] + (isHead?(new int[]{-1,0,1,0}[orientation]):0);
            		ModObjects.setModBedBlock(world,
                			this.getXWithOffset(u, w),
                			this.getYWithOffset(v),
                			this.getZWithOffset(u, w),
                			StructureVillageVN.getBedOrientationMeta(orientation, this.coordBaseMode, isHead),
                			uvwoc[4]);
            	}
            }
        	
        	
    		// Villagers
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	if (VillageGeneratorConfigHandler.spawnVillagersInResidences)
            	{
	            	int[][] villagerPositions = new int[][]{
	                	{3,1,6, -1, 0}, 
	        			};
	        		int countdownToAdult = 1+random.nextInt(villagerPositions.length); // One of the villagers here is an adult
	            	
	        		for (int[] ia :villagerPositions)
	        		{
	        			EntityVillager entityvillager = new EntityVillager(world);
	        			entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], (--countdownToAdult)==0?0:Math.min(random.nextInt(24001)-12000,0));
	        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)this.getYWithOffset(ia[1]) + 1.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
	                    		random.nextFloat()*360F, 0.0F);
	                    world.spawnEntityInWorld(entityvillager);
	        		}
            	}
            }
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    // --- Small House 2 --- //
    // designed by AstroTibs
    
    public static class SwampSmallHouse2 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			" FFFFF ",
    			" FFFFF ",
    			" FFFFF ",
    			" FFFFF ",
    			" FFFFF ",
    			" FFFFF ",
    			" FFFFF ",
    			" FFFFF ",
    			"   FF  ",
    			"   FF  ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 6;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 1;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampSmallHouse2() {}

    	public SwampSmallHouse2(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampSmallHouse2 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampSmallHouse2(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
    		
    		
    		// Logs (Across)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Side
    			{0,3,10, 0,3,10}, {6,3,10, 6,3,10}, 
    			{0,3,8, 0,3,8}, {6,3,8, 6,3,8}, 
    			{0,3,6, 0,3,6}, {6,3,6, 6,3,6}, 
    			// Front awning
    			{1,3,3, 5,3,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, false);	
    		}
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
    			// Entry/floor
            	{1,0,3, 5,0,5}, {1,0,6, 1,0,7}, {5,0,6, 5,0,7}, {1,0,8, 5,0,10}, 
    			// Front wall
    			{1,1,5, 2,3,5}, {3,3,5, 3,3,5}, {4,1,5, 5,3,5}, {2,4,6, 4,4,6},
    			// Left wall
    			{1,0,6, 1,3,6}, {1,0,7, 1,1,7}, {1,3,7, 1,3,7}, {1,0,8, 1,3,8}, {1,0,9, 1,1,9}, {1,3,9, 1,3,9}, 
    			// Right wall
    			{5,0,6, 5,3,6}, {5,0,7, 5,1,7}, {5,3,7, 5,3,7}, {5,0,8, 5,3,8}, {5,0,9, 5,1,9}, {5,3,9, 5,3,9}, 
    			// Back wall
    			{1,0,10, 5,3,10}, {2,4,10, 2,4,10}, {4,4,10, 4,4,10}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{2,0,0, 3,0,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{1,3,4, 5,3,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
        	
        	            
            // Fences
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
            for (int[] uuvvww : new int[][]{
            	// Front deck
            	{1,1,3}, {1,2,3}, {5,1,3}, {5,2,3}, 
            	// Windows
            	{3,4,10}, 
            	{1,2,9}, {5,2,9}, 
            	{1,2,7}, {5,2,7}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
    		
    		
    		// Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{1,4,6, 1,4,8}, {3,5,6, 3,5,6}, {3,5,8, 3,5,8}, {3,5,10, 3,5,10}, {5,4,6, 5,4,6}, {5,4,10, 5,4,10}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.mossy_cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneBlock = (Block)blockObject[0]; int biomeMossyCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{1,4,9, 1,4,10}, {5,4,7, 5,4,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, false);	
    		}
    		
    		
    		// Cobblestone Slab (lower)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabLowerBlock = (Block)blockObject[0]; int biomeCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{0,4,6, 0,4,7}, 
    			{2,5,6, 2,5,8}, {2,5,10, 2,5,10}, 
    			{4,5,6, 4,5,7}, {4,5,10, 4,5,10}, 
    			{6,4,6, 6,4,10}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone Slab (lower)
    		Block biomeMossyCobblestoneSlabLowerBlock = biomeCobblestoneSlabLowerBlock; int biomeMossyCobblestoneSlabLowerMeta = biomeCobblestoneSlabLowerMeta;
    		blockObject = ModObjects.chooseModMossyCobblestoneSlabBlock(false);
    		if (blockObject != null)
    		{
    			blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs);
    			biomeMossyCobblestoneSlabLowerBlock = (Block)blockObject[0]; biomeMossyCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		}
    		for(int[] uuvvww : new int[][]{
    			{0,4,8, 0,4,10}, 
    			{2,5,9, 2,5,9}, 
    			{4,5,8, 4,5,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Cobblestone Slab (upper)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3+8, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabUpperBlock = (Block)blockObject[0]; int biomeCobblestoneSlabUpperMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{3,5,7, 3,5,7}, {3,5,9, 3,5,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneSlabUpperBlock, biomeCobblestoneSlabUpperMeta, biomeCobblestoneSlabUpperBlock, biomeCobblestoneSlabUpperMeta, false);	
    		}
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		{3,4,8}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
        	// Wool - carpet in front of the door prevents villagers from passing through
        	for(int[] uvwc : new int[][]{
        		// Lower
        		{2,0,7, (GeneralConfig.useVillageColors ? this.townColor3 : 10)}, // Purple
        		{3,0,6, (GeneralConfig.useVillageColors ? this.townColor3 : 10)}, // Purple
        		{4,0,7, (GeneralConfig.useVillageColors ? this.townColor3 : 10)}, // Purple
        		{2,0,6, (GeneralConfig.useVillageColors ? this.townColor2 : 13)}, // Green
        		{3,0,7, (GeneralConfig.useVillageColors ? this.townColor2 : 13)}, // Green
        		{4,0,6, (GeneralConfig.useVillageColors ? this.townColor2 : 13)}, // Green
        		})
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.wool, uvwc[3], uvwc[0], uvwc[1], uvwc[2], structureBB);
            }
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{3,1,5, 2, 1, 1}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// Interior
    			{4,2,4, 2}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{3,1,8, 2, GeneralConfig.useVillageColors ? this.townColor3 : 10}, // Purple
            })
            {
            	for (boolean isHead : new boolean[]{false, true})
            	{
            		int orientation = uvwoc[3];
            		int u = uvwoc[0] + (isHead?(new int[]{0,-1,0,1}[orientation]):0);
            		int v = uvwoc[1];
            		int w = uvwoc[2] + (isHead?(new int[]{-1,0,1,0}[orientation]):0);
            		ModObjects.setModBedBlock(world,
                			this.getXWithOffset(u, w),
                			this.getYWithOffset(v),
                			this.getZWithOffset(u, w),
                			StructureVillageVN.getBedOrientationMeta(orientation, this.coordBaseMode, isHead),
                			uvwoc[4]);
            	}
            }
        	
            
            // Potted random flower
            for (int[] uvw : new int[][]{
        		{4,1,4}, 
        		})
            {
        		int u=uvw[0]; int v=uvw[1]; int w=uvw[2];
                int x = this.getXWithOffset(u, w);
                int y = this.getYWithOffset(v);
                int z = this.getZWithOffset(u, w);
            	
            	Object[] cornflowerObject = ModObjects.chooseModCornflower(); Object[] lilyOfTheValleyObject = ModObjects.chooseModLilyOfTheValley();
        		int randomPottedPlant = random.nextInt(10)-1;
        		if (randomPottedPlant==-1) {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.yellow_flower, 0);} // Dandelion specifically
        		else {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.red_flower, randomPottedPlant);}          // Every other type of flower
            }
        	
        	
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 1;
        	int chestV = 1;
        	int chestW = 4;
        	int chestO = 2; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo(ChestLootHandler.getGenericLootForVillageType(this.villageType));
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
        	
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1: rightward, 2:backward, 3: leftward
        			// Right side
        			{6,3,5, 1}, {6,2,5, 1}, {6,1,5, 1}, {6,0,5, 1}, 
        			// Away-facing vines
        			{1,3,11, 0}, 
        			{4,4,11, 0}, {4,3,11, 0}, {4,2,11, 0}, 
            		})
                {
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
        			}
                }
        	}
        	
        	
    		// Villagers
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	if (VillageGeneratorConfigHandler.spawnVillagersInResidences)
            	{
	            	int[][] villagerPositions = new int[][]{
	                	{2,1,7, -1, 0}, 
	        			};
	        		int countdownToAdult = 1+random.nextInt(villagerPositions.length); // One of the villagers here is an adult
	            	
	        		for (int[] ia :villagerPositions)
	        		{
	        			EntityVillager entityvillager = new EntityVillager(world);
	        			entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], (--countdownToAdult)==0?0:Math.min(random.nextInt(24001)-12000,0));
	        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)this.getYWithOffset(ia[1]) + 1.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
	                    		random.nextFloat()*360F, 0.0F);
	                    world.spawnEntityInWorld(entityvillager);
	        		}
            	}
            }
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    
    // --- Small House 3 --- //
    // Shrek's House
    // designed by AstroTibs
    
    public static class SwampSmallHouse3 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			" FFFFFF  ",
    			"FFFFFFFF ",
    			"FFFFFFFF ",
    			"FFFFFFFF ",
    			"FFFFFFF  ",
    			"FFFFFFF  ",
    			"FFFFFFFF ",
    			"FFFFFFFFF",
    			"  FFFFFFF",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 8;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 2;
    	private static final int DECREASE_MAX_U = 2;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampSmallHouse3() {}

    	public SwampSmallHouse3(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampSmallHouse3 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampSmallHouse3(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
    			// Entry walkway
    			{4,0,0, 4,0,2}, 
    			// Front wall
    			{2,1,3, 3,3,3}, {4,3,3, 4,3,3}, {5,1,3, 5,3,3}, 
    			// Left wall
    			{2,1,4, 2,2,7}, {2,3,5, 2,3,6}, {1,1,7, 1,2,7}, 
    			// Back wall
    			{3,1,7, 6,3,7}, 
    			// Right wall
    			{7,1,5, 7,3,6}, {6,1,4, 6,3,4}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
    		
    		
    		// Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{2,0,3, 5,0,7}, {6,0,4, 6,0,7}, {7,0,5, 7,0,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
            
            
            // Dirt
            for(int[] uuvvww : new int[][]{
    			{0,1,3, 0,1,3}, {0,1,4, 0,2,5}, {0,1,6, 0,1,7}, 
    			{1,1,3, 1,2,3}, {1,1,4, 1,3,5}, {1,1,6, 1,2,6}, 
    			{1,1,8, 2,1,8}, 
    			{3,1,8, 6,2,8}, 
    			{4,4,3, 5,4,3}, 
    			{6,1,3, 6,3,3}, 
    			{7,1,7, 7,2,7}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);	
            }
            
            
            // Grass
            for(int[] uuvvww : new int[][]{
    			{1,4,3, 3,4,5}, {2,4,6, 7,4,6}, {2,4,7, 6,4,7}, {2,4,3, 6,4,5}, 
    			{0,2,4, 0,2,5}, {1,3,6, 1,3,7}, {2,3,7, 2,3,7}, {3,3,8, 6,3,8}, {7,3,7, 7,3,7}, 
    			{0,2,3, 0,2,3}, {0,2,6, 0,2,7}, {1,2,8, 2,2,8}, 
    			{0,1,2, 2,1,2}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);	
            }
    		
    		
    		// Stripped logs (Vertical)
        	Block biomeLogVertBlock = Blocks.log; int biomeLogVertMeta = 0;
    		Block biomeStrippedLogVerticBlock = biomeLogVertBlock; int biomeStrippedLogVerticMeta = biomeLogVertMeta;
    		// Try to see if stripped logs exist
    		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
    		for (int[] uuvvww : new int[][]{
        		{4,4,4, 5,6,5}, {4,7,4, 4,7,4}, 
        		{6,3,2, 6,4,2}, {7,1,2, 7,2,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
            
            
        	// Stripped Logs (Across)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2!=0? 4:0), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1];
        	Block biomeStrippedLogHorizAcrossBlock = biomeLogHorAcrossBlock; int biomeStrippedLogHorizAcrossMeta = biomeLogHorAcrossMeta;
        	// Try to see if stripped logs exist
        	if (biomeStrippedLogHorizAcrossBlock==Blocks.log || biomeStrippedLogHorizAcrossBlock==Blocks.log2)
        	{
            	if (biomeLogVertBlock == Blocks.log)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 1+(this.coordBaseMode%2!=0? 1:0)); biomeStrippedLogHorizAcrossBlock = (Block)blockObject[0]; biomeStrippedLogHorizAcrossMeta = (Integer)blockObject[1];
            	}
            	else if (biomeLogVertBlock == Blocks.log2)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 1+(this.coordBaseMode%2!=0? 1:0)); biomeStrippedLogHorizAcrossBlock = (Block)blockObject[0]; biomeStrippedLogHorizAcrossMeta = (Integer)blockObject[1];
            	}
        	}
            for(int[] uuvvww : new int[][]{
            	{7,3,3, 8,3,3}, 
            	{2,3,4, 4,3,4}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogHorizAcrossBlock, biomeStrippedLogHorizAcrossMeta, biomeStrippedLogHorizAcrossBlock, biomeStrippedLogHorizAcrossMeta, false);	
            }
            
            
        	// Stripped Logs (Along)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1];
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
            for(int[] uuvvww : new int[][]{
            	{1,3,3, 1,3,3}, 
            	{5,5,2, 5,5,3}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogHorizAlongBlock, biomeStrippedLogHorizAlongMeta, biomeStrippedLogHorizAlongBlock, biomeStrippedLogHorizAlongMeta, false);	
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		{5,3,4}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Bookshelves
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.bookshelf, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeBookshelfBlock = (Block)blockObject[0]; int biomeBookshelfMeta = (Integer)blockObject[1];
            for (int[] uuvvww : new int[][]{
        		{3,3,5, 3,3,6}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeBookshelfBlock, biomeBookshelfMeta, biomeBookshelfBlock, biomeBookshelfMeta, false);
            }
            
                    	
            // Polished Granite
            blockObject = ModObjects.chooseModPolishedGraniteBlockObject();
            if (blockObject==null) {blockObject = ModObjects.chooseModSmoothStoneBlockObject();} // Guarantees a vanilla stone if this fails
            Block polishedGraniteBlock = (Block) blockObject[0]; int polishedGraniteMeta = (Integer) blockObject[1];
        	for (int[] uvw : new int[][]{
        		{3,1,6}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, polishedGraniteBlock, polishedGraniteMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
            
            // Potted random flower
            for (int[] uvw : new int[][]{
        		{3,2,6}, 
        		})
            {
        		int u=uvw[0]; int v=uvw[1]; int w=uvw[2];
                int x = this.getXWithOffset(u, w);
                int y = this.getYWithOffset(v);
                int z = this.getZWithOffset(u, w);
            	
            	Object[] cornflowerObject = ModObjects.chooseModCornflower(); Object[] lilyOfTheValleyObject = ModObjects.chooseModLilyOfTheValley();
        		int randomPottedPlant = random.nextInt(10)-1;
        		if (randomPottedPlant==-1) {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.yellow_flower, 0);} // Dandelion specifically
        		else {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.red_flower, randomPottedPlant);}          // Every other type of flower
            }
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{4,1,3, 2, 1, 0}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
    		
    		
    		// Unkempt Grass
    		for (int[] uvwg : new int[][]{ // g is grass type
    			{0,4,5, 0}, 
    			{1,2,2, 0}, {1,5,4, 0}, {1,4,7, 2}, 
    			{2,5,3, 0}, {2,5,5, 0}, 
    			{3,4,8, 0}, 
    			{5,5,7, 2}, 
    			{6,5,3, 2}, 
    			{7,5,4, 0}, {7,5,6, 0}, 
    		})
    		{
    			if (uvwg[3]==0) // Short grass
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 1, uvwg[0], uvwg[1], uvwg[2], structureBB);
    			}
    			else if (uvwg[3]==1)// Tall grass
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 2, uvwg[0], uvwg[1], uvwg[2], structureBB);
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 11, uvwg[0], uvwg[1]+1, uvwg[2], structureBB);
    			}
    			else if (uvwg[3]==2) // Fern
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 2, uvwg[0], uvwg[1], uvwg[2], structureBB);
    			}
    			else // Large Fern
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 3, uvwg[0], uvwg[1], uvwg[2], structureBB);
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 11, uvwg[0], uvwg[1]+1, uvwg[2], structureBB);
    			}
    		}
            
            
        	// Blue Orchid
        	for (int[] uvw : new int[][]{
        		// Back window
        		{2,2,2}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.red_flower, 1, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{6,1,5, 2, GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            })
            {
            	for (boolean isHead : new boolean[]{false, true})
            	{
            		int orientation = uvwoc[3];
            		int u = uvwoc[0] + (isHead?(new int[]{0,-1,0,1}[orientation]):0);
            		int v = uvwoc[1];
            		int w = uvwoc[2] + (isHead?(new int[]{-1,0,1,0}[orientation]):0);
            		ModObjects.setModBedBlock(world,
                			this.getXWithOffset(u, w),
                			this.getYWithOffset(v),
                			this.getZWithOffset(u, w),
                			StructureVillageVN.getBedOrientationMeta(orientation, this.coordBaseMode, isHead),
                			uvwoc[4]);
            	}
            }
        	
        	
            // Decor
            int[][] decorUVW = new int[][]{
            	{2,1,0}, {8,1,0}, 
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
            	
            	// Generate decor
            	ArrayList<BlueprintData> decorBlueprint = StructureVillageVN.getRandomDecorBlueprint(this.villageType, this.materialType, this.disallowModSubs, this.biome, this.coordBaseMode, randomFromXYZ, VillageGeneratorConfigHandler.allowTaigaTroughs && !VillageGeneratorConfigHandler.restrictTaigaTroughs);
            	
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
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	if (VillageGeneratorConfigHandler.spawnVillagersInResidences)
            	{
	            	int[][] villagerPositions = new int[][]{
	                	{5,1,5, -1, 0}, 
	        			};
	        		int countdownToAdult = 1+random.nextInt(villagerPositions.length); // One of the villagers here is an adult
	            	
	        		for (int[] ia :villagerPositions)
	        		{
	        			EntityVillager entityvillager = new EntityVillager(world);
	        			entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], (--countdownToAdult)==0?0:Math.min(random.nextInt(24001)-12000,0));
	        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)this.getYWithOffset(ia[1]) + 1.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
	                    		random.nextFloat()*360F, 0.0F);
	                    world.spawnEntityInWorld(entityvillager);
	        		}
            	}
            }
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    // --- Small House 4 --- //
    // designed by THASSELHOFF
    
    public static class SwampSmallHouse4 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"         ",
    			"         ",
    			"         ",
    			"         ",
    			"         ",
    			"         ",
    			"         ",
    			"         ",
    			"  F      ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 7;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 4;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampSmallHouse4() {}

    	public SwampSmallHouse4(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampSmallHouse4 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampSmallHouse4(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
    		
    		
    		// Stripped logs (Vertical)
        	Block biomeLogVertBlock = Blocks.log; int biomeLogVertMeta = 0;
    		Block biomeStrippedLogVerticBlock = biomeLogVertBlock; int biomeStrippedLogVerticMeta = biomeLogVertMeta;
    		// Try to see if stripped logs exist
    		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
    		for (int[] uuvvww : new int[][]{
        		{2,0,6, 2,5,6}, {6,0,6, 6,5,6}, 
        		{2,0,2, 2,5,2}, {6,0,2, 6,5,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
    		// Foot foundation
    		for(int[] uvw : new int[][]{
    			// Feet
        		{2,-1,6}, {6,-1,6}, 
        		{2,-1,2}, {6,-1,2}, 
    			})
    		{
    			this.func_151554_b(world, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
    			// Front wall
    			{3,3,2, 4,3,2}, {4,4,2, 4,4,2}, {3,5,2, 5,5,2}, 
    			// Left wall
    			{2,3,3, 2,3,5}, {2,4,5, 2,4,5}, {2,5,3, 2,5,5}, 
    			// Right wall
    			{6,3,3, 6,3,5}, {6,4,3, 6,4,3}, {6,5,3, 6,5,5}, 
    			// Back wall
    			{3,3,6, 5,3,6}, {3,5,6, 5,5,6}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Balcony
    			{2,2,1, 6,2,1}, 
    			// Floor
    			{2,2,3, 2,2,3}, {2,2,5, 2,2,5},
    			{6,2,3, 6,2,3}, {6,2,5, 6,2,5}, 
    			{3,2,6, 3,2,6}, {5,2,6, 5,2,6}, 
    			{3,2,2, 5,2,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
			
			
			// Wood stairs
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
			for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
				// Entrance stairs
				{2,0,0, 0}, {3,1,0, 0}, {4,2,0, 0}, 
				{3,0,0, 1+4}, {4,1,0, 1+4}, {5,2,0, 1+4}, 
				// Chairs
				{3,3,3, 2}, {3,3,5, 3}, 
				})
			{
				this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
			}
        	
        	            
            // Fences
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
            for (int[] uuvvww : new int[][]{
            	// Posts
            	{2,3,1}, {6,3,1},
            	// Table
            	{3,3,4}, 
            	// Window
            	{3,4,2}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
        	
        	
            // Wooden pressure plate
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_pressure_plate, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodPressurePlateBlock = (Block)blockObject[0]; int biomeWoodPressurePlateMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		{3,4,4}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeWoodPressurePlateBlock, biomeWoodPressurePlateMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	{2,2,4}, {6,2,4}, 
            	{4,2,6}, 
            	{3,5,4}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Sitting Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(false); Block biomeSittingLanternBlock = (Block)blockObject[0]; int biomeSittingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
        		{2,4,1}, {6,4,1}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeSittingLanternBlock, biomeSittingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{5,3,4, 2, GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            })
            {
            	for (boolean isHead : new boolean[]{false, true})
            	{
            		int orientation = uvwoc[3];
            		int u = uvwoc[0] + (isHead?(new int[]{0,-1,0,1}[orientation]):0);
            		int v = uvwoc[1];
            		int w = uvwoc[2] + (isHead?(new int[]{-1,0,1,0}[orientation]):0);
            		ModObjects.setModBedBlock(world,
                			this.getXWithOffset(u, w),
                			this.getYWithOffset(v),
                			this.getZWithOffset(u, w),
                			StructureVillageVN.getBedOrientationMeta(orientation, this.coordBaseMode, isHead),
                			uvwoc[4]);
            	}
            }
    		
    		
    		// Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{2,6,2, 2,6,3}, {2,6,5, 2,6,6}, 
    			{3,6,2, 4,6,5}, 
    			{5,6,4, 5,6,6}, 
    			{6,6,3, 6,6,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.mossy_cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneBlock = (Block)blockObject[0]; int biomeMossyCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{2,6,4, 2,6,4}, {3,6,6, 4,6,6}, {5,6,2, 6,6,2}, {5,6,3, 5,6,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, false);	
    		}
    		
    		
    		// Cobblestone Slab (lower)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabLowerBlock = (Block)blockObject[0]; int biomeCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{1,6,1, 1,6,2}, {1,6,5, 1,6,7}, 
    			{4,6,7, 7,6,7}, 
    			{7,6,3, 7,6,6}, 
    			{2,6,1, 5,6,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone Slab (lower)
    		Block biomeMossyCobblestoneSlabLowerBlock = biomeCobblestoneSlabLowerBlock; int biomeMossyCobblestoneSlabLowerMeta = biomeCobblestoneSlabLowerMeta;
    		blockObject = ModObjects.chooseModMossyCobblestoneSlabBlock(false);
    		if (blockObject != null)
    		{
    			blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs);
    			biomeMossyCobblestoneSlabLowerBlock = (Block)blockObject[0]; biomeMossyCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		}
    		for(int[] uuvvww : new int[][]{
    			{1,6,3, 1,6,4}, 
    			{2,6,7, 3,6,7}, 
    			{6,6,1, 7,6,1}, 
    			{7,6,2, 7,6,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, false);	
    		}
            
            
            // Grass - not biome adjusted
            for(int[] uuvvww : new int[][]{
    			{1,3,3, 1,3,4}, 
    			{3,3,7, 5,3,7}, 
    			{7,3,4, 7,3,5}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.grass, 0, Blocks.grass, 0, false);	
            }
    		
    		
    		// Trapdoor (Top Vertical)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapdoor, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	// Windows
            	{2,4,3, 1}, {2,4,4, 1}, 
            	{3,4,6, 2}, {4,4,6, 2}, {5,4,6, 2}, 
            	{6,4,4, 3}, {6,4,5, 3}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, true, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
    		
    		
    		// Trapdoor (Bottom Vertical)
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	// Planters
            	{1,3,2, 2}, {0,3,3, 3}, {0,3,4, 3}, {1,3,5, 0}, 
            	{2,3,7, 3}, {3,3,8, 0}, {4,3,8, 0}, {5,3,8, 0}, {6,3,7, 1}, 
            	{7,3,3, 2}, {8,3,4, 1}, {8,3,5, 1}, {7,3,6, 0}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, false, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
    		
    		
    		// Trapdoor (Top Horizontal)
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	// Planters
            	{1,2,3, 3}, {1,2,4, 3}, 
            	{3,2,7, 0}, {4,2,7, 0}, {5,2,7, 0}, 
            	{7,2,4, 1}, {7,2,5, 1}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, true, false), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
        	
        	// Random Flowers in pairs
            Object[] cornflowerObject = ModObjects.chooseModCornflower(); Object[] lilyOfTheValleyObject = ModObjects.chooseModLilyOfTheValley();
        	int flowerindex = random.nextInt(10 + (cornflowerObject!=null && lilyOfTheValleyObject!=null ? 2:0));
        	int flowerindex2 = flowerindex;
        	int flowerindex3 = flowerindex;
        	while (flowerindex2==flowerindex) {flowerindex2 = random.nextInt(10 + (cornflowerObject!=null && lilyOfTheValleyObject!=null ? 2:0));}
        	while (flowerindex3==flowerindex || flowerindex3==flowerindex2) {flowerindex3 = random.nextInt(10 + (cornflowerObject!=null && lilyOfTheValleyObject!=null ? 2:0));}
        	
        	for (int[] uvwf : new int[][]{
        		{1,4,3, flowerindex}, {1,4,4, flowerindex}, 
        		{3,4,7, flowerindex2}, {4,4,7, flowerindex2}, {5,4,7, flowerindex2}, 
        		{7,4,4, flowerindex3}, {7,4,5, flowerindex3}, 
        		})
            {
        		// 0-8 is "red" flower
        		// 9 is a basic yellow flower
        		// 10 is cornflower, 11 is lily of the valley
            	Block flowerblock; int flowermeta;
            	if (uvwf[3]==10 && cornflowerObject!=null) {flowerblock=(Block) cornflowerObject[0]; flowermeta=(Integer) cornflowerObject[1];}
            	else if (uvwf[3]==11 && lilyOfTheValleyObject!=null) {flowerblock=(Block) lilyOfTheValleyObject[0]; flowermeta=(Integer) lilyOfTheValleyObject[1];}
            	else {flowerblock = uvwf[3]==9 ? Blocks.yellow_flower:Blocks.red_flower; flowermeta = new int[]{0,1,2,3,4,5,6,7,8,0}[uvwf[3]];}
        		
        		this.placeBlockAtCurrentPosition(world, flowerblock, flowermeta, uvwf[0], uvwf[1], uvwf[2], structureBB);	
            }
            
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward, 3:leftward
            		// Left side
        			{1,5,3, 3}, {1,5,4, 3}, 
        			// Back side
        			{2,5,7, 0}, {2,4,7, 0}, {3,5,7, 0}, 
        			// Right side
        			{7,5,2, 1}, {7,4,2, 1}, {7,3,2, 1}, {7,2,2, 1}, 
        			// Front side
        			{6,5,1, 2}, 
            		})
                {
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);	
        			}
                }
        	}
            
        	
    		// Villagers
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	if (VillageGeneratorConfigHandler.spawnVillagersInResidences)
            	{
	            	int[][] villagerPositions = new int[][]{
	                	{4,3,4, -1, 0}, 
	        			};
	        		int countdownToAdult = 1+random.nextInt(villagerPositions.length); // One of the villagers here is an adult
	            	
	        		for (int[] ia :villagerPositions)
	        		{
	        			EntityVillager entityvillager = new EntityVillager(world);
	        			entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], (--countdownToAdult)==0?0:Math.min(random.nextInt(24001)-12000,0));
	        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)this.getYWithOffset(ia[1]) + 1.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
	                    		random.nextFloat()*360F, 0.0F);
	                    world.spawnEntityInWorld(entityvillager);
	        		}
            	}
            }
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    // --- Small House 5 --- //
    // designed by THASSELHOFF
    
    public static class SwampSmallHouse5 extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"     ",
    			"     ",
    			"  FF ",
    			"     ",
    			" F   ",
    			"     ",
    			"F    ",
    			"F    ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 10;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = -1;
    	private static final int DECREASE_MAX_U = 3;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampSmallHouse5() {}

    	public SwampSmallHouse5(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampSmallHouse5 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampSmallHouse5(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
    		
    		
    		// Stripped logs (Vertical)
        	Block biomeLogVertBlock = Blocks.log; int biomeLogVertMeta = 0;
    		Block biomeStrippedLogVerticBlock = biomeLogVertBlock; int biomeStrippedLogVerticMeta = biomeLogVertMeta;
    		// Try to see if stripped logs exist
    		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
    		for (int[] uuvvww : new int[][]{
        		{0,1,6, 0,6,6}, {4,1,6, 4,6,6}, 
        		{0,1,2, 0,6,2}, {4,1,2, 4,6,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
    		// Foot foundation
    		for(int[] uvw : new int[][]{
    			// Feet
        		{0,0,6}, {4,0,6}, 
        		{0,0,2}, {4,0,2}, 
    			})
    		{
    			this.func_151554_b(world, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
    			// Entrance
            	{0,1,1, 0,1,1}, 
            	// Front wall
    			{1,4,2, 2,4,2}, {2,5,2, 2,5,2}, {1,6,2, 3,6,2}, 
    			// Left wall
    			{0,4,3, 0,4,5}, {0,5,5, 0,5,5}, {0,6,3, 0,6,5}, 
    			// Right wall
    			{4,4,3, 4,4,5}, {4,5,5, 4,5,5}, {4,6,3, 4,6,5}, 
    			// Back wall
    			{1,4,6, 3,4,6}, {2,5,6, 2,5,6}, {1,6,6, 3,6,6}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Balcony
    			{4,3,1, 4,3,1}, 
    			// Floor
    			{0,3,3, 0,3,3}, {0,3,5, 0,3,5}, 
    			{4,3,3, 4,3,3}, {4,3,5, 4,3,5}, 
    			{1,3,6, 1,3,6}, {3,3,6, 3,3,6}, 
    			{1,3,2, 3,3,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
			
			
			// Wood stairs
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
			for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
				// Entrance stairs
				{0,1,0, 3}, 
				{1,2,1, 0}, {2,3,1, 0}, 
				{1,1,1, 1+4}, {2,2,1, 1+4}, {3,3,1, 1+4}, 
				})
			{
				this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
			}
        	
        	            
            // Fences
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
            for (int[] uuvvww : new int[][]{
            	{4,4,1}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	// Front
        		{0,6,1}, {4,6,1}, 
        		// Interior
            	{1,6,3}, 
            	// Under-farm
            	{2,3,6}, 
            	{0,3,4}, {4,3,4}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{1,4,4, 2, GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
            })
            {
            	for (boolean isHead : new boolean[]{false, true})
            	{
            		int orientation = uvwoc[3];
            		int u = uvwoc[0] + (isHead?(new int[]{0,-1,0,1}[orientation]):0);
            		int v = uvwoc[1];
            		int w = uvwoc[2] + (isHead?(new int[]{-1,0,1,0}[orientation]):0);
            		ModObjects.setModBedBlock(world,
                			this.getXWithOffset(u, w),
                			this.getYWithOffset(v),
                			this.getZWithOffset(u, w),
                			StructureVillageVN.getBedOrientationMeta(orientation, this.coordBaseMode, isHead),
                			uvwoc[4]);
            	}
            }
    		
    		
    		// Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{0,7,6, 4,7,6}, {4,7,2, 4,7,5}, 
    			{0,7,4, 0,7,4}, 
    			{0,7,2, 2,7,2}, 
    			{0,8,4, 2,8,4}, {2,8,5, 2,8,5}, {4,8,4, 4,8,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.mossy_cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneBlock = (Block)blockObject[0]; int biomeMossyCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{0,7,5, 1,7,5}, 
    			{0,7,3, 0,7,3}, {0,7,3, 0,7,3}, 
    			{3,7,2, 3,7,2}, 
    			{3,8,4, 3,8,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, false);	
    		}
    		
    		
    		// Cobblestone Slab (lower)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabLowerBlock = (Block)blockObject[0]; int biomeCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{0,7,1, 2,7,1}, 
    			{0,7,7, 0,7,7}, {3,7,7, 4,7,7}, 
    			{1,8,3, 4,8,3}, 
    			{3,8,5, 4,8,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone Slab (lower)
    		Block biomeMossyCobblestoneSlabLowerBlock = biomeCobblestoneSlabLowerBlock; int biomeMossyCobblestoneSlabLowerMeta = biomeCobblestoneSlabLowerMeta;
    		blockObject = ModObjects.chooseModMossyCobblestoneSlabBlock(false);
    		if (blockObject != null)
    		{
    			blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs);
    			biomeMossyCobblestoneSlabLowerBlock = (Block)blockObject[0]; biomeMossyCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		}
    		for(int[] uuvvww : new int[][]{
    			{3,7,1, 4,7,1}, 
    			{1,7,7, 2,7,7}, 
    			{0,8,3, 0,8,3}, 
    			{0,8,5, 1,8,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, false);	
    		}
        	
        	
        	// Cobblestone wall
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{2,5,5, 2,6,5}, {2,9,5, 2,9,5}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, false);
            }
        	
        	
        	// Furnaces
            for (int[] uvwo : new int[][]{ // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
            	{2,4,5, 2}, 
            	})
            {
                this.placeBlockAtCurrentPosition(world, Blocks.furnace, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2]), StructureVillageVN.chooseFurnaceMeta(uvwo[3], this.coordBaseMode), 2);
            }
			
			
			// Wool
			for (int[] uvwo : new int[][]{
				{1,7,3, (GeneralConfig.useVillageColors ? this.townColor2 : 13)}, // Green
				{2,7,3, (GeneralConfig.useVillageColors ? this.townColor : 15)}, // Black
				{3,7,3, (GeneralConfig.useVillageColors ? this.townColor2 : 13)}, // Green
				
				{1,7,4, (GeneralConfig.useVillageColors ? this.townColor : 15)}, // Black
				{2,7,4, (GeneralConfig.useVillageColors ? this.townColor2 : 13)}, // Green
				{3,7,4, (GeneralConfig.useVillageColors ? this.townColor : 15)}, // Black
				
				{1,7,5, (GeneralConfig.useVillageColors ? this.townColor2 : 13)}, // Green
				{2,7,5, (GeneralConfig.useVillageColors ? this.townColor : 15)}, // Black
				{3,7,5, (GeneralConfig.useVillageColors ? this.townColor2 : 13)}, // Green
			})
			{
				this.placeBlockAtCurrentPosition(world, Blocks.wool, uvwo[3], uvwo[0], uvwo[1], uvwo[2], structureBB);
			}
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
        		{1,5,6}, {3,5,6}, 
        		{0,5,3}, {0,5,4}, 
        		{4,5,3}, {4,5,4}, 
        		{1,5,2}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            // Moist Farmland with crop above
            for(int[] uvwfcp : new int[][]{
            	// u,v,w, farmland moisture (0:dry, 7:moist), crop (0:wheat, 1:potato, 2:carrot, 3:melon, 4:pumpkin), crop progress
            	// Front left
            	{3,0,5, 7, 4, 7}, 
            	{1,0,3, 7, 2, random.nextInt(5)}, 
            	})
            {
            	this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, uvwfcp[0], uvwfcp[1]-1, uvwfcp[2], structureBB);
            	//this.clearCurrentPositionBlocksUpwards(world, uvwpmc[0], uvwpmc[1]+1, uvwpmc[2], structureBB);
            	this.placeBlockAtCurrentPosition(world, uvwfcp[4]==0?Blocks.wheat:uvwfcp[4]==1?Blocks.potatoes:uvwfcp[4]==2?Blocks.carrots:uvwfcp[4]==3?Blocks.melon_stem:Blocks.pumpkin_stem, uvwfcp[5], uvwfcp[0], uvwfcp[1]+1, uvwfcp[2], structureBB); 
            	this.placeBlockAtCurrentPosition(world, Blocks.farmland, uvwfcp[3], uvwfcp[0], uvwfcp[1], uvwfcp[2], structureBB); // 7 is moist
            }
            
            // Pumpkin
        	for (int[] uvw : new int[][]{
        		{2,1,5}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.pumpkin, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Water boundary
    		// This puts up a dirt/grass wall to keep in the water if it's not level with the surrounding ground
            for(int[] uwuwvs : new int[][]{ // u/w box corners, v height, and the side that is to be evaluated.
            	// Side - 0:forward (away from you), 1:rightward, 2:backward (toward you), 3: leftward
            	// Right water space
    			{3,2, 3,4, 0, 1}, // Right side
    			{3,3, 3,3, 0, 0}, // Far side
    			{3,3, 3,3, 0, 2}, // Close side
    			{3,2, 3,3, 0, 3}, // Left side
    			// Left water space
    			{2,4, 2,5, 0, 3}, // Left side
    			})
    		{
            	int u_offset=0, w_offset=0;
            	int v = uwuwvs[4];
            	
            	switch(uwuwvs[5])
            	{
            	case 0: w_offset=1; break; // forward
            	case 1: u_offset=1; break; // rightward
            	case 2: w_offset=-1; break; // backward
            	case 3: u_offset=-1; break; // leftward
            	default:
            	}
            	
            	// Scan boundary and add containment if necessary
            	for (int u=uwuwvs[0]; u<=uwuwvs[2]; u++) {for (int w=uwuwvs[1]; w<=uwuwvs[3]; w++)
            	{
            		int x = this.getXWithOffset(u+u_offset, w+w_offset);
            		int y = this.getYWithOffset(v);
            		int z = this.getZWithOffset(u+u_offset, w+w_offset);
            		
            		// If space above bordering block is liquid, fill below with filler and cap with topper
            		if (world.getBlock(x, y+1, z).getMaterial().isLiquid())
            		{
            			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u+u_offset, v, w+w_offset, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u+u_offset, v+1, w+w_offset, structureBB);
            		}
            		// If bordering block is air, fill below with filler and cap with topper
            		else if (world.isAirBlock(x, y, z))
            		{
            			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u+u_offset, v-1, w+w_offset, structureBB);
            			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u+u_offset, v, w+w_offset, structureBB);
            		}
            	}}
    		}
            
            
    		// Water
    		for(int[] uuvvww : new int[][]{
    			{3,0,3, 3,0,3}, 
    			{2,0,4, 2,0,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);	
    		}
            
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward, 3:leftward
            		// Left side
        			{-1,6,4, 3}, {-1,6,3, 3}, {-1,5,3, 3}, 
        			// Back side
        			{0,6,7, 0}, {0,5,7, 0}, 
        			{1,6,7, 0}, {1,5,7, 0}, {1,4,7, 0}, {1,3,7, 0}, 
        			{2,6,7, 0}, {2,5,7, 0}, 
        			// Right side
        			{5,6,2, 1}, {5,5,2, 1}, {5,4,2, 1}, {5,3,2, 1}, 
        			{5,7,3, 1}, {5,6,3, 1}, {5,5,3, 1}, 
        			// Front side
        			{1,6,1, 2}, {1,5,1, 2}, 
        			{2,6,1, 2}, 
            		})
                {
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);	
        			}
                }
        	}
            
        	
    		// Villagers
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	if (VillageGeneratorConfigHandler.spawnVillagersInResidences)
            	{
	            	int[][] villagerPositions = new int[][]{
	                	{2,4,4, -1, 0}, 
	        			};
	        		int countdownToAdult = 1+random.nextInt(villagerPositions.length); // One of the villagers here is an adult
	            	
	        		for (int[] ia :villagerPositions)
	        		{
	        			EntityVillager entityvillager = new EntityVillager(world);
	        			entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, ia[3], ia[4], (--countdownToAdult)==0?0:Math.min(random.nextInt(24001)-12000,0));
	        			entityvillager.setLocationAndAngles((double)this.getXWithOffset(ia[0], ia[2]) + 0.5D, (double)this.getYWithOffset(ia[1]) + 1.5D, (double)this.getZWithOffset(ia[0], ia[2]) + 0.5D,
	                    		random.nextFloat()*360F, 0.0F);
	                    world.spawnEntityInWorld(entityvillager);
	        		}
            	}
            }
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    // --- Stable --- //
    // designed by THASSELHOFF
    
    public static class SwampStable extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"            FFFF  ",
    			"         FFFFFFFF ",
    			"        FFFFFFFFFF",
    			"        FFFFFFFFFF",
    			"        FFFFFFFFFF",
    			"        FFFFFFFFFF",
    			"        FFFFFFFFFF",
    			"         FFFFFFFF ",
    			"            FFFF  ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 9;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 13;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampStable() {}

    	public SwampStable(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampStable buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampStable(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
            
            
            // Grass
            for(int[] uuvvww : new int[][]{
    			{8,0,2, 8,0,6}, 
    			{9,0,1, 11,0,7}, 
    			{12,0,0, 12,0,8}, 
    			{13,0,0, 13,0,6}, {13,0,8, 14,0,8}, 
    			{14,0,0, 14,0,5}, 
    			{15,0,1, 16,0,3}, {15,0,7, 15,0,8}, {16,0,6, 16,0,7}, 
    			{17,0,2, 17,0,6}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);	
            }
    		
    		
    		// Unkempt Grass
    		for (int[] uvwg : new int[][]{ // g is grass type
    			{13,1,1, 1}, 
    		})
    		{
    			if (uvwg[3]==0) // Short grass
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 1, uvwg[0], uvwg[1], uvwg[2], structureBB);
    			}
    			else if (uvwg[3]==1)// Tall grass
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 2, uvwg[0], uvwg[1], uvwg[2], structureBB);
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 11, uvwg[0], uvwg[1]+1, uvwg[2], structureBB);
    			}
    			else if (uvwg[3]==2) // Fern
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 2, uvwg[0], uvwg[1], uvwg[2], structureBB);
    			}
    			else // Large Fern
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 3, uvwg[0], uvwg[1], uvwg[2], structureBB);
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 11, uvwg[0], uvwg[1]+1, uvwg[2], structureBB);
    			}
    		}
    		
    		
    		// Stripped logs (Vertical)
        	Block biomeLogVertBlock = Blocks.log; int biomeLogVertMeta = 0;
    		Block biomeStrippedLogVerticBlock = biomeLogVertBlock; int biomeStrippedLogVerticMeta = biomeLogVertMeta;
    		// Try to see if stripped logs exist
    		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedLogVerticBlock = (Block)blockObject[0]; biomeStrippedLogVerticMeta = (Integer)blockObject[1];
    		for (int[] uuvvww : new int[][]{
        		{1,1,7, 1,5,7}, {8,1,7, 8,5,7}, 
        		{1,1,1, 1,5,1}, {8,1,1, 8,5,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
    		// Foot foundation
    		for(int[] uvw : new int[][]{
    			// Feet
        		{1,0,7}, {8,0,7}, 
        		{1,0,1}, {8,0,1}, 
    			})
    		{
    			this.func_151554_b(world, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	// Back wall
            	{2,3,7, 7,3,7}, {2,4,7, 2,4,7}, {4,4,7, 5,4,7}, {7,4,7, 7,4,7}, {2,5,7, 7,5,7},
            	// Left wall
            	{1,3,2, 1,3,6}, {1,4,2, 1,4,2}, {1,4,4, 1,4,4}, {1,4,6, 1,4,6}, {1,5,2, 1,5,6},
            	// Right wall
            	{8,3,2, 8,5,2}, {8,3,6, 8,5,6}, 
            	// Front wall
            	{2,3,1, 7,3,1}, {2,4,1, 2,4,1}, {4,4,1, 5,4,1}, {7,4,1, 7,4,1}, {2,5,1, 7,5,1}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{11,1,3, 11,1,5}, {9,2,3, 9,2,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Floor main
    			{2,2,2, 8,2,6}, 
    			// Floor fringe
    			{2,2,7, 2,2,7}, {4,2,7, 5,2,7}, {7,2,7, 7,2,7}, 
    			{1,2,2, 1,2,3}, {1,2,5, 1,2,6}, 
    			{2,2,1, 2,2,1}, {4,2,1, 5,2,1}, {7,2,1, 7,2,1}, 
    			// Ramp
    			{10,1,3, 10,1,5}, 
    			// Entrance lip
    			{8,5,3, 8,5,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
    		
    		// Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{1,6,1, 1,6,7}, {2,6,7, 2,6,7}, {4,6,7, 7,6,7}, 
    			{8,6,1, 8,6,3}, {8,6,5, 8,6,7}, 
    			{4,6,1, 5,6,1}, 
    			{3,7,3, 6,7,3}, {3,7,4, 3,7,4}, {6,7,4, 6,7,4}, {3,7,5, 6,7,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.mossy_cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneBlock = (Block)blockObject[0]; int biomeMossyCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{3,6,7, 4,6,7}, {8,6,4, 8,6,4}, 
    			{2,6,1, 3,6,1}, {6,6,1, 7,6,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, false);	
    		}
    		
    		
    		// Cobblestone Slab (lower)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabLowerBlock = (Block)blockObject[0]; int biomeCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{0,6,1, 0,6,8}, 
    			{1,6,8, 6,6,8}, 
    			{9,6,0, 9,6,2}, {9,6,5, 9,6,6}, 
    			{2,6,0, 5,6,0}, {7,6,0, 8,6,0}, 
    			{2,7,2, 2,7,6}, {3,7,2, 6,7,2}, {3,7,6, 6,7,6}, {7,7,2, 7,7,6}, 
    			{4,8,4, 4,8,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone Slab (lower)
    		Block biomeMossyCobblestoneSlabLowerBlock = biomeCobblestoneSlabLowerBlock; int biomeMossyCobblestoneSlabLowerMeta = biomeCobblestoneSlabLowerMeta;
    		blockObject = ModObjects.chooseModMossyCobblestoneSlabBlock(false);
    		if (blockObject != null)
    		{
    			blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs);
    			biomeMossyCobblestoneSlabLowerBlock = (Block)blockObject[0]; biomeMossyCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		}
    		for(int[] uuvvww : new int[][]{
    			{0,6,0, 1,6,0}, {6,6,0, 6,6,0}, {7,6,8, 9,6,8}, 
    			{9,6,3, 9,6,4}, {9,6,7, 9,6,7}, 
    			{5,8,4, 5,8,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Trapdoor (Bottom Vertical)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapdoor, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{6,3,2, 3}, 
            	{7,3,3, 0}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, false, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
    		
            
    		// Water
    		for(int[] uuvvww : new int[][]{
    			// Trough
    			{7,3,2, 7,3,2}, 
    			// Pond
    			{13,0,7, 13,0,7}, 
    			{14,0,6, 14,0,7}, 
    			{15,0,4, 15,0,6}, 
    			{16,0,4, 16,0,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);	
    		}
    		
    		
    		// Lilypad
    		for (int[] uvw : new int[][]{
    			{14,1,7}, 
    			{15,1,5}, 
    			{16,1,4}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.waterlily, 0, uvw[0], uvw[1], uvw[2], structureBB);
    		}
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		// In hut
        		{2,6,4}, {7,6,4}, 
        		// Under house
        		{3,2,7}, {6,2,7}, 
        		{1,2,4}, 
        		{3,2,1}, {6,2,1}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Fences
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
    		for (int[] uuvvww : new int[][]{
    			// Pen
    			{9,1,1, 12,1,1}, {12,1,0, 14,1,0}, {14,1,1, 14,1,1}, 
    			{9,3,1, 10,3,1}, {9,2,1, 12,2,1}, 
    			{16,1,1, 16,1,2}, {17,1,2, 17,1,6}, {16,1,6, 16,1,7}, {15,1,7, 15,1,8}, {12,1,8, 14,1,8}, 
    			{9,1,7, 12,1,7}, 
    			{9,3,7, 10,3,7}, {9,2,7, 12,2,7}, 
    			{8,1,2, 8,1,6}, 
    			// Windows
    			{3,4,7, 3,4,7}, {6,4,7, 6,4,7}, 
    			{1,4,3, 1,4,3}, {1,4,5, 1,4,5}, 
    			{3,4,1, 3,4,1}, {6,4,1, 6,4,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
    		}
            
            
        	// Fence Gate (Across)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence_gate, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceGateBlock = (Block)blockObject[0]; int biomeFenceGateMeta = (Integer)blockObject[1];
        	for(int[] uvw : new int[][]{
        		{15,1,1}, 
            	})
            {
        		this.placeBlockAtCurrentPosition(world, biomeFenceGateBlock, StructureVillageVN.getMetadataWithOffset(biomeFenceGateBlock, biomeFenceGateMeta, this.coordBaseMode), uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Hay bales (vertical)
        	for (int[] uvw : new int[][]{
        		{2,3,6}, {2,4,6}, 
        		{4,3,3}, {4,4,2}, {5,3,2}, 
        		{6,3,6}, {8,3,5}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.hay_block, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Hay bales (along)
        	for (int[] uvw : new int[][]{
        		{2,3,5}, 
        		{3,3,2}, {4,3,2}, 
        		{4,3,2}, {7,3,5}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.hay_block, 4+(this.coordBaseMode%2!=0? 0:4), uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Hay bales (across)
        	for (int[] uvw : new int[][]{
        		{3,3,6}, 
        		{7,3,6}, {7,4,6}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.hay_block, 4+(this.coordBaseMode%2==0? 0:4), uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1: rightward, 2:backward, 3: leftward
        			// Back-facing vines
        			{1,5,0, 2}, {1,4,0, 2}, 
            		})
                {
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
        			}
                }
        	}
        	
    		
    		// Entities
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			// Cow
            	for (int[] uvw : new int[][]{
        			{15, 1, 3}, 
        			})
        		{
            		EntityCow entityCow = new EntityCow(world);
    				IEntityLivingData ientitylivingdata = entityCow.onSpawnWithEgg(null);
    				
                    entityCow.setLocationAndAngles((double)this.getXWithOffset(uvw[0], uvw[2]) + 0.5D, (double)this.getYWithOffset(uvw[1]) + 0.5D, (double)this.getZWithOffset(uvw[0], uvw[2]) + 0.5D, random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(entityCow);
                    
                    // Dirt block underneath
                    //this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, uvw[0], uvw[1]-1, uvw[2], structureBB);
        		}
            	
    			// Horse
            	for (int[] uvw : new int[][]{
            		{13, 1, 4}, 
        			})
        		{
    				EntityHorse entityHorse = new EntityHorse(world);
    				IEntityLivingData ientitylivingdata = entityHorse.onSpawnWithEgg(null);
    				
                	if (VillageGeneratorConfigHandler.nameVillageHorses && GeneralConfig.nameEntities)
                	{
                		String[] petname_a = NameGenerator.newRandomName("pet", random);
                		entityHorse.setCustomNameTag((petname_a[1]+" "+petname_a[2]+" "+petname_a[3]).trim());
                	}
                	entityHorse.setLocationAndAngles((double)this.getXWithOffset(uvw[0], uvw[2]) + 0.5D, (double)this.getYWithOffset(uvw[1]) + 0.5D, (double)this.getZWithOffset(uvw[0], uvw[2]) + 0.5D, random.nextFloat()*360F, 0.0F);
                    world.spawnEntityInWorld(entityHorse);
                    
                    // Dirt block underneath
                    //this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, uvw[0], uvw[1]-1, uvw[2], structureBB);
        		}
    		}
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    // --- Tannery --- //
    // designed by AstroTibs
    
    public static class SwampTannery extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"    FFFFFFFF",
    			" FFFFFFFFFFF",
    			"FFF FFFFFFFF",
    			"FFF FFFFFFFF",
    			"FFF FFFFFFFF",
    			"    FFF     ",
    			"    FFF     ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 7;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 5;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 1;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampTannery() {}

    	public SwampTannery(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampTannery buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampTannery(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	// Lookout
            	{0,0,2, 0,2,4}, 
            	{1,0,2, 1,0,4}, {1,1,2, 1,2,2}, 
            	{2,0,2, 2,2,4}, 
            	// Main house
            	// Front porch
            	{4,0,0, 6,0,1}, 
            	// Floor
            	{4,0,2, 4,0,6}, 
            	{5,0,2, 6,0,2}, 
            	{5,0,6, 6,0,6}, 
            	{7,0,2, 11,0,6}, 
            	// Left wall
            	{4,1,2, 4,3,4}, {4,3,5, 4,3,5}, {4,1,6, 4,3,6}, 
            	// Back wall
            	{5,1,6, 6,3,6}, 
            	// Outside rim
            	{7,1,2, 11,1,2}, 
            	{11,1,3, 11,1,5}, 
            	{7,1,6, 11,1,6}, 
            	// Front wall
            	{5,3,2, 5,3,2}, {6,1,2, 6,3,2}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Lookout
    			{0,3,4, 2+4}, {1,3,4, 2+4}, {2,3,4, 2+4}, 
    			{0,3,3, 0+4}, {2,3,3, 1+4}, 
    			{0,3,2, 3+4}, {1,3,2, 3+4}, {2,3,2, 3+4}, 
    			// House roof
    			{3,3,6, 0}, {4,4,6, 0}, {6,4,6, 1}, {7,3,6, 1}, 
    			{3,3,5, 0}, {4,4,5, 0}, {6,4,5, 1}, {7,3,5, 1}, 
    			{3,3,4, 0}, {4,4,4, 0}, {6,4,4, 1}, {7,3,4, 1}, 
    			{3,3,3, 0}, {4,4,3, 0}, {6,4,3, 1}, {7,3,3, 1}, 
    			{3,3,2, 0}, {4,4,2, 0}, {6,4,2, 1}, {7,3,2, 1}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Fences
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
    		for (int[] uuvvww : new int[][]{
    			// Lookout
    			{0,4,4, 2,4,4}, 
    			{0,4,3, 0,4,3}, {2,4,3, 2,4,3}, 
    			{0,4,2, 2,4,2}, 
    			{1,5,2, 1,6,2}, {1,6,1, 1,6,1}, 
    			// Front porch
    			{4,1,0, 6,1,0}, {4,2,0, 4,2,0}, {6,2,0, 6,2,0}, 
    			// Awning
    			{9,2,6, 9,2,6}, {11,2,6, 11,2,6}, 
    			{9,2,2, 9,2,2}, {11,2,2, 11,2,2}, 
    			// Top windows
    			{5,4,2, 5,4,2}, {5,4,6, 5,4,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Front entrance
    			{4,3,0, 6,3,1}, 
    			// Awning
    			{8,3,2, 11,3,6}, 
    			// Roof
    			{5,5,2, 5,5,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
            
            
            // Ladder
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.ladder, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLadderBlock = (Block)blockObject[0];
        	for (int[] uuvvwwo : new int[][]{ // Orientation - 3:leftward, 1:rightward, 2:backward, 0:forward
        		{1,1,3, 1,3,3, 0}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], uuvvwwo[3], uuvvwwo[4], uuvvwwo[5], biomeLadderBlock, StructureVillageVN.chooseLadderMeta(uuvvwwo[6], this.coordBaseMode), biomeLadderBlock, StructureVillageVN.chooseLadderMeta(uuvvwwo[6], this.coordBaseMode), false);
            }
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{5,1,2, 2, 1, 0}, 
    			{4,1,5, 3, 1, 1}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
            
        	
            // Cauldron
        	for (int[] uvw : new int[][]{
        		{10,1,5}, 
        		{10,1,3}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.cauldron, 3, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Potted plant
        	for (int[] uvws : new int[][]{ // u,v,w, sapling
        		{11,2,4, 0}, // Oak
           		})
        	{
            	StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, 
            			this.getXWithOffset(uvws[0], uvws[2]), 
            			this.getYWithOffset(uvws[1]), 
            			this.getZWithOffset(uvws[0], uvws[2]), 
            			Blocks.sapling, uvws[3]);
        	}
        	
        	
            // Glazed terracotta
        	Object[] tryGlazedTerracotta;
        	for (int[] uvwoc : new int[][]{ // u,v,w, orientation, dye color
        		// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
        		{5,0,5, 2, GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
        		{6,0,5, 3, GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
        		{5,0,4, 1, GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
        		{6,0,4, 0, GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
        		{5,0,3, 2, GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
        		{6,0,3, 3, GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
           		})
        	{
        		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(uvwoc[4], StructureVillageVN.chooseGlazedTerracottaMeta(uvwoc[3], this.coordBaseMode));
        		if (tryGlazedTerracotta != null)
            	{
        			this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvwoc[0], uvwoc[1], uvwoc[2], structureBB);
            	}
        		else
        		{
        			this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, uvwoc[4], uvwoc[0], uvwoc[1], uvwoc[2], structureBB);
        		}
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		// In house
        		{6,3,4}, 
        		// Hanging
        		{1,5,1}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1: rightward, 2:backward, 3: leftward
        			// Left side
        			{0,2,4, 3}, 
        			// Right side
        			{12,1,5, 1}, 
        			// Forward-facing vines
        			{0,2,5, 0}, {0,1,5, 0}, 
        			{5,3,7, 0}, {5,2,7, 0}, {6,3,7, 0}, 
        			{9,1,7, 0}, {10,1,7, 0}, 
        			// Back-facing vines
        			{0,2,1, 2}, 
        			{1,2,1, 2}, {1,1,1, 2}, 
        			{9,1,1, 2}, {10,1,1, 2}, 
            		})
                {
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
        			}
                }
        	}
    		
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			int s = random.nextInt(16);
    			
    			int u = s==15 ? 10 : 5+(s/3);
    			int v = 1;
    			int w = s==15 ? 4 : 3+(s%3);
    			
    			EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 4, 2, 0); // Leatherworker
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
    			world.spawnEntityInWorld(entityvillager);
    		}
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 4;}
    }
    
    
    // --- Temple --- //
    // designed by AstroTibs
    
    public static class SwampTemple extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			" FFFFFFFFFF",
    			" FPPPPPPPFF",
    			" FFFFFFFPFF",
    			" FFFFFFFPFF",
    			" FFFFFFFPFF",
    			" FFFFFFFPFF",
    			" FFFFFFFPFF",
    			" FFFFFFFFFF",
    			" FFFFFFFFFF",
    			" FFFFFFFFFF",
    			" FFFFFFFFFF",
    			" FFPPFF    ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 10;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 4;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampTemple() {}

    	public SwampTemple(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampTemple buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampTemple(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
    		
    		
    		// Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Left wall
    			{1,1,8, 1,1,8}, {1,4,8, 1,4,8}, 
    			{1,1,7, 1,4,7}, 
    			{1,1,6, 1,1,6}, {1,4,6, 1,4,6}, 
    			{1,1,5, 1,4,5}, 
    			{1,1,4, 1,1,4}, {1,4,4, 1,4,4}, 
    			{1,1,3, 1,4,3}, 
    			{1,4,2, 1,4,2}, 
    			// Right wall
    			{6,1,8, 6,1,8}, {6,4,8, 6,4,8}, 
    			{6,1,7, 6,4,7}, 
    			{6,1,6, 6,1,6}, {6,4,6, 6,4,6}, 
    			{6,1,5, 6,3,5}, 
    			{6,1,4, 6,1,4}, {6,4,4, 6,4,4}, 
    			{6,1,3, 6,3,3}, 
    			{6,1,2, 6,1,2}, 
    			// Front wall
    			{1,2,1, 1,5,1}, 
    			{2,4,1, 2,5,1}, 
    			{3,7,1, 4,8,1}, 
    			{5,4,1, 5,6,1}, 
    			{6,2,1, 6,4,1}, 
    			// Back wall
    			{3,1,9, 3,6,9}, 
    			{4,3,9, 4,6,9}, 
    			{5,1,9, 5,5,9}, 
    			{6,1,9, 6,1,9}, 
    			// Floor
    			{2,0,1, 6,0,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.mossy_cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneBlock = (Block)blockObject[0]; int biomeMossyCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Left wall
    			{1,1,2, 1,1,2}, 
    			// Right wall
    			{6,4,2, 6,4,3}, 
    			{6,4,5, 6,4,5}, 
    			// Front wall
    			{1,1,1, 1,1,1}, 
    			{2,6,1, 2,6,1}, 
    			{3,4,1, 4,4,1}, 
    			{6,1,1, 6,1,1}, {6,5,1, 6,5,1}, 
    			// Back wall
    			{1,1,9, 1,4,9}, 
    			{2,1,9, 2,5,9}, 
    			{6,2,9, 6,4,9}, 
    			// Floor
    			{1,0,1, 1,0,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, false);	
    		}
            
            
            // Grass
            for(int[] uuvvww : new int[][]{
            	{7,0,2, 9,0,4}, 
            	{7,0,5, 7,0,9}, {9,0,5, 9,0,10}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeTopBlock, biomeTopMeta, biomeTopBlock, biomeTopMeta, false);	
            }
    		
    		
    		// Cobblestone stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Stairs
    			{3,9,1, 0}, {4,9,1, 1}, 
    			{5,7,1, 1}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Pews
    			{2,1,3, 2}, {2,1,5, 2}, 
    			{5,1,3, 2}, {5,1,5, 2}, 
    			// Roof
    			{2,6,2, 0}, {2,6,3, 0}, {2,6,4, 0}, {2,6,5, 0}, {2,6,6, 0}, {2,6,7, 0}, {2,6,8, 0}, {2,6,9, 0}, 
    			{3,7,2, 0}, {3,7,3, 0}, {3,7,4, 0}, {3,7,5, 0}, {3,7,6, 0}, {3,7,7, 0}, {3,7,8, 0}, {3,7,9, 0}, 
    			{4,7,2, 1}, {4,7,3, 1}, {4,7,4, 1}, {4,7,5, 1}, {4,7,6, 1}, {4,7,7, 1}, {4,7,8, 1}, {4,7,9, 1}, 
    			{5,6,2, 1}, {5,6,3, 1}, {5,6,4, 1}, {5,6,5, 1}, {5,6,6, 1}, {5,6,7, 1}, {5,6,8, 1}, {5,6,9, 1}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	{1,5,2, 1,5,9}, {6,5,2, 6,5,9}, 
            	{3,1,8, 3,1,8}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{0,5,2, 0,5,9}, {7,5,2, 7,5,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone stairs
    		Block modblock = ModObjects.chooseModMossyCobblestoneStairsBlock();
    		if (modblock==null) {modblock = Blocks.stone_stairs;}
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(modblock, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Stairs
    			{2,7,1, 0}, 
    			{2,3,0, 3}, {3,3,0, 3}, {4,3,0, 3}, {5,3,0, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeMossyCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Logs (Vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Vertical
    			{2,1,1, 2,2,1}, {5,1,1, 5,2,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);	
    		}
    		
    		
    		// Logs (Across)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{2,3,1, 5,3,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, false);	
    		}
        	
        	
        	// Cobblestone wall
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{8,1,1, 9,1,1}, 
        		{10,1,4, 10,1,10}, 
        		{2,1,11, 7,1,11}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, false);
            }
        	
        	
        	// Mossy Cobblestone wall
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 1, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneWallBlock = (Block)blockObject[0]; int biomeMossyCobblestoneWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{7,1,1, 7,1,1}, 
        		{10,1,1, 10,1,3}, 
        		{8,1,11, 10,1,11}, 
        		{1,1,10, 1,1,11}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneWallBlock, biomeMossyCobblestoneWallMeta, biomeMossyCobblestoneWallBlock, biomeMossyCobblestoneWallMeta, false);
            }
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// Interior
    			{2,4,3, 1}, {2,4,7, 1}, 
    			{5,4,3, 3}, {5,4,7, 3}, 
    			// On Outside wall
    			{10,2,1, -1}, {10,2,11, -1}, {1,2,11, -1}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
            
            
        	// Carpet
        	for(int[] uuvvww : new int[][]{
        		// Carpet in front of the door prevents villagers from passing through
        		{3,1,3, 4,1,7, (GeneralConfig.useVillageColors ? this.townColor3 : 10)}, // Purple
        		})
            {
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.carpet, uuvvww[6], Blocks.carpet, uuvvww[6], false);	
            }
            
            
            // Stained Glass Windows
            for (int[] uvwc : new int[][]{
            	// Front wall
            	{3,6,1 , GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
            	{4,5,1 , GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
            	{3,5,1 , GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            	{4,6,1 , GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            	// Left wall
            	{1,3,2 , GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
            	{1,3,4 , GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
            	{1,3,6 , GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
            	{1,3,8 , GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
            	{1,2,2 , GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            	{1,2,4 , GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            	{1,2,6 , GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            	{1,2,8 , GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            	// Right wall
            	{6,3,2 , GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
            	{6,3,4 , GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
            	{6,3,6 , GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
            	{6,3,8 , GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
            	{6,2,2 , GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            	{6,2,4 , GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            	{6,2,6 , GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
            	{6,2,8 , GeneralConfig.useVillageColors ? this.townColor2 : 13}, // Green
        		})
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.stained_glass_pane, uvwc[3], uvwc[0], uvwc[1], uvwc[2], structureBB);
            }
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{3,1,1, 0, 1, 0}, {4,1,1, 0, 1, 1}, 
    			{4,1,9, 0, 1, 1}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
    		
    					
    		// Brewing stand
    		for (int[] uvw : new int[][]{
    			{3,2,8}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, ModObjects.chooseModBrewingStandBlock(), 0, uvw[0], uvw[1], uvw[2], structureBB);
    		}
            
            
            // Leaves
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.leaves, 4, this.materialType, this.biome, this.disallowModSubs); Block biomeLeafBlock = (Block)blockObject[0]; int biomeLeafMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{7,1,2, 7,1,9}, 
        		{8,1,2, 8,1,2}, 
        		{9,1,2, 9,1,10}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeLeafBlock, biomeLeafMeta,
        				biomeLeafBlock, biomeLeafMeta, 
        				false);
            }
            
        	
        	// Random Flower
        	for (int[] uvw : new int[][]{
        		{8,1,4}, 
        		})
            {
        		Object[] cornflowerObject = ModObjects.chooseModCornflower(); Object[] lilyOfTheValleyObject = ModObjects.chooseModLilyOfTheValley();
            	int flowerindex = random.nextInt(10 + (cornflowerObject!=null && lilyOfTheValleyObject!=null ? 2:0));
        		// 0-8 is "red" flower
        		// 9 is a basic yellow flower
        		// 10 is cornflower, 11 is lily of the valley
            	Block flowerblock; int flowermeta;
            	if (flowerindex==10 && cornflowerObject!=null) {flowerblock=(Block) cornflowerObject[0]; flowermeta=(Integer) cornflowerObject[1];}
            	else if (flowerindex==11 && lilyOfTheValleyObject!=null) {flowerblock=(Block) lilyOfTheValleyObject[0]; flowermeta=(Integer) lilyOfTheValleyObject[1];}
            	else {flowerblock = flowerindex==9 ? Blocks.yellow_flower:Blocks.red_flower; flowermeta = new int[]{0,1,2,3,4,5,6,7,8,0}[flowerindex];}
        		
        		this.placeBlockAtCurrentPosition(world, flowerblock, flowermeta, uvw[0], uvw[1], uvw[2], structureBB);	
            }
        	
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1: rightward, 2:backward, 3: leftward
        			// Left side
        			{0,5,1, 3}, {0,4,1, 3}, {0,3,1, 3}, {0,2,1, 3}, 
        			{0,4,3, 3}, {0,3,3, 3}, {0,2,3, 3}, {0,1,3, 3}, {0,0,3, 3}, 
        			{0,4,9, 3}, 
        			// Right side
        			{7,4,3, 1}, {7,3,3, 1}, 
        			// Away-facing vines
        			{1,4,10, 0}, {1,3,10, 0}, 
        			{1,5,10, 0}, {1,4,10, 0}, {1,3,10, 0}, {1,2,10, 0}, {1,1,10, 0}, 
            		})
                {
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
        			}
                }
        	}
    		
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			// Villager
    			
    			int u = 3+random.nextInt(2);
    			int v = 1;
    			int w = 2+random.nextInt(6);
    			
    			EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 2, 1, 0); // Cleric
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
    			world.spawnEntityInWorld(entityvillager);
    		}
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
    	}
    	
    	/**
    	 * Returns the villager type to spawn in this component, based on the number
    	 * of villagers already spawned.
    	 */
    	@Override
    	protected int getVillagerType (int number) {return 2;}
    }
    
    
    // --- Tool Smithy --- //
    // designed by AstroTibs
    
    public static class SwampToolSmithy extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"          ",
    			"          ",
    			"          ",
    			"          ",
    			"FFFF      ",
    			"FFF       ",
    			"FFF       ",
    			"FFF       ",
    			"FFF       ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 10;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 6;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampToolSmithy() {}

    	public SwampToolSmithy(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampToolSmithy buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampToolSmithy(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
            
            
        	// Trees
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.sapling, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeSaplingBlock = (Block)blockObject[0]; int biomeSaplingMeta = (Integer)blockObject[1];
        	for (int[] uvwss : new int[][]{ // u,v,w, ushift,wshift: Which adjacent spaces to use if this is a Dark Oak sapling
        		{1,1,1, -1,1},
        		})
            {
        		Block dirtblock = world.getBlock(this.getXWithOffset(uvwss[0], uvwss[2]), this.getYWithOffset(uvwss[1]-1), this.getZWithOffset(uvwss[0], uvwss[2]));
        		Block saplingblock = world.getBlock(this.getXWithOffset(uvwss[0], uvwss[2]), this.getYWithOffset(uvwss[1]), this.getZWithOffset(uvwss[0], uvwss[2]));
        		
        		// Don't place if there's no dirt beneath to grow
        		if (dirtblock==null) {continue;}
        		// Don't place if the sapling can't see the sky
        		if (!world.canBlockSeeTheSky(this.getXWithOffset(uvwss[0], uvwss[2]), this.getYWithOffset(uvwss[1]), this.getZWithOffset(uvwss[0], uvwss[2]))) {continue;}
        		// Dark oak version of the above
        		if (biomeSaplingMeta==5)
        		{
        			Block dirtblock1 = world.getBlock(this.getXWithOffset(uvwss[0]+uvwss[3], uvwss[2]), this.getYWithOffset(uvwss[1]-1), this.getZWithOffset(uvwss[0]+uvwss[3], uvwss[2]));
        			Block dirtblock2 = world.getBlock(this.getXWithOffset(uvwss[0], uvwss[2]+uvwss[4]), this.getYWithOffset(uvwss[1]-1), this.getZWithOffset(uvwss[0], uvwss[2]+uvwss[4]));
        			Block dirtblock3 = world.getBlock(this.getXWithOffset(uvwss[0]+uvwss[3], uvwss[2]+uvwss[4]), this.getYWithOffset(uvwss[1]-1), this.getZWithOffset(uvwss[0]+uvwss[3], uvwss[2]+uvwss[4]));
        			
        			if (
        					// Foundation blocks can't see the sky
        					   !world.canBlockSeeTheSky(this.getXWithOffset(uvwss[0]+uvwss[3], uvwss[2]), this.getYWithOffset(uvwss[1]), this.getZWithOffset(uvwss[0]+uvwss[3], uvwss[2]))
        					|| !world.canBlockSeeTheSky(this.getXWithOffset(uvwss[0], uvwss[2]+uvwss[4]), this.getYWithOffset(uvwss[1]), this.getZWithOffset(uvwss[0], uvwss[2]+uvwss[4]))
        					|| !world.canBlockSeeTheSky(this.getXWithOffset(uvwss[0]+uvwss[3], uvwss[2]+uvwss[4]), this.getYWithOffset(uvwss[1]), this.getZWithOffset(uvwss[0]+uvwss[3], uvwss[2]+uvwss[4]))
        					)
        			{
        				continue;
        			}
        			else
        			{
        				// Otherwise, plant grass to allow the Dark Oak sapling
        				this.placeBlockAtCurrentPosition(world, Blocks.grass, 0, uvwss[0]+uvwss[3], uvwss[1]-1, uvwss[2], structureBB);
        				this.placeBlockAtCurrentPosition(world, Blocks.grass, 0, uvwss[0], uvwss[1]-1, uvwss[2]+uvwss[4], structureBB);
        				this.placeBlockAtCurrentPosition(world, Blocks.grass, 0, uvwss[0]+uvwss[3], uvwss[1]-1, uvwss[2]+uvwss[4], structureBB);
        			}
        		}
        		
        		this.placeBlockAtCurrentPosition(world, Blocks.grass, 0, uvwss[0], uvwss[1]-1, uvwss[2], structureBB);
        		
        		// Place the sapling
        		this.placeBlockAtCurrentPosition(world, biomeSaplingBlock, biomeSaplingMeta, uvwss[0], uvwss[1], uvwss[2], structureBB);
        		
        		// Grow it into a tree
        		if (biomeSaplingBlock instanceof BlockSapling)
                {
        			if (biomeSaplingMeta==5) // This is a dark oak. You need four to grow.
        			{
        				this.placeBlockAtCurrentPosition(world, biomeSaplingBlock, biomeSaplingMeta, uvwss[0]+uvwss[3], uvwss[1], uvwss[2], structureBB);
        				this.placeBlockAtCurrentPosition(world, biomeSaplingBlock, biomeSaplingMeta, uvwss[0], uvwss[1], uvwss[2]+uvwss[4], structureBB);
        				this.placeBlockAtCurrentPosition(world, biomeSaplingBlock, biomeSaplingMeta, uvwss[0]+uvwss[3], uvwss[1], uvwss[2]+uvwss[4], structureBB);
        			}
        			
        			((BlockSapling)biomeSaplingBlock).func_149878_d(world, this.getXWithOffset(uvwss[0], uvwss[2]), this.getYWithOffset(uvwss[1]), this.getZWithOffset(uvwss[0], uvwss[2]), world.rand);
                }
            }

    		        	
        	// Clear out leaves to allow player to access the walkway
            for(int[] uuvvww : new int[][]{
    			{3,2,4, 3,3,4}, 
    			{3,2,3, 3,3,3}, 
    			{3,3,1, 4,4,2}, 
    			// Next to the tree
    			{2,1,-1, 2,2,4}, 
    			{3,1,-1, 3,2,-1}, 
    			})
    		{
            	this.fillWithAir(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5]);	
    		}
        	
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	// Entry
            	{0,0,0, 0,0,4}, {1,0,0, 1,0,0}, {1,0,2, 1,0,4}, {2,0,0, 2,0,4}, {3,0,4, 3,0,4}, 
            	// Left wall
            	{3,4,4, 3,5,8}, {3,5,3, 3,5,3}, 
            	// Right wall
            	{9,4,3, 9,5,8}, 
            	// Front wall
            	{4,4,3, 4,6,3}, {5,4,3, 5,7,3}, {6,6,3, 6,8,3}, {7,4,3, 7,7,3}, {8,4,3, 8,6,3}, 
            	// Back wall
            	{4,4,8, 4,6,8}, {5,6,8, 5,7,8}, {6,7,8, 6,8,8}, {7,6,8, 7,7,8}, {8,4,8, 8,6,8}, {5,4,8, 7,4,8}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
            
            
            
        	// Stripped Log (Along)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1];
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
            for(int[] uuvvww : new int[][]{
            	{4,2,3, 4,2,7}, {8,2,3, 8,2,7}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogHorizAlongBlock, biomeStrippedLogHorizAlongMeta, biomeStrippedLogHorizAlongBlock, biomeStrippedLogHorizAlongMeta, false);	
            }
            
            
        	// Stripped Log (Across)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2!=0? 4:0), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1];
        	Block biomeStrippedLogHorizAcrossBlock = biomeLogHorAcrossBlock; int biomeStrippedLogHorizAcrossMeta = biomeLogHorAcrossMeta;
        	// Try to see if stripped logs exist
        	if (biomeStrippedLogHorizAcrossBlock==Blocks.log || biomeStrippedLogHorizAcrossBlock==Blocks.log2)
        	{
            	if (biomeLogVertBlock == Blocks.log)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 1+(this.coordBaseMode%2!=0? 1:0)); biomeStrippedLogHorizAcrossBlock = (Block)blockObject[0]; biomeStrippedLogHorizAcrossMeta = (Integer)blockObject[1];
            	}
            	else if (biomeLogVertBlock == Blocks.log2)
            	{
            		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 1+(this.coordBaseMode%2!=0? 1:0)); biomeStrippedLogHorizAcrossBlock = (Block)blockObject[0]; biomeStrippedLogHorizAcrossMeta = (Integer)blockObject[1];
            	}
        	}
            for(int[] uuvvww : new int[][]{
            	{3,2,0, 9,2,0}, {5,2,3, 7,2,3}, {3,2,8, 9,2,8}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogHorizAcrossBlock, biomeStrippedLogHorizAcrossMeta, biomeStrippedLogHorizAcrossBlock, biomeStrippedLogHorizAcrossMeta, false);	
            }
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			{5,3,2, 0}, {6,3,2, 3}, {7,3,2, 1}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{3,1,4, 3,1,4}, {3,2,2, 3,2,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Entryway
    			{3,1,3, 3,1,3}, 
    			{3,2,1, 9,2,1}, {4,2,2, 9,2,2}, 
    			{3,4,3, 3,4,3}, 
    			// Under floor
    			{3,2,5, 9,2,7}, 
    			{5,2,4, 7,2,7}, 
    			{9,2,3, 9,2,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
    		
    		// Fences
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
    		for (int[] uuvvww : new int[][]{
    			// Support posts
    			{3,1,8, 3,1,8}, {5,1,8, 5,1,8}, {7,1,8, 7,1,8}, {9,1,8, 9,1,8}, 
    			{4,1,3, 4,1,3}, {8,1,3, 8,1,3}, 
    			{3,1,0, 3,1,0}, {5,1,0, 5,1,0}, {7,1,0, 7,1,0}, {9,1,0, 9,1,0}, 
    			{3,3,0, 3,5,0}, {5,3,0, 5,7,0}, {7,3,0, 7,7,0}, {9,3,0, 9,5,0}, 
    			// Front hatching
    			{4,5,0, 4,6,0}, {6,5,0, 6,8,0}, {8,5,0, 8,6,0}, 
    			{6,7,1, 6,7,2}, 
    			// Windows
    			{6,6,8, 6,6,8}, {5,5,8, 7,5,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
    		}
    		// Foot foundation
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uvw : new int[][]{
    			{3,0,8}, {5,0,8}, {7,0,8}, {9,0,8}, 
    			{4,0,3}, {8,0,3}, 
    			{3,0,0}, {5,0,0}, {7,0,0}, {9,0,0}, 
    			})
    		{
    			this.func_151554_b(world, biomeCobblestoneBlock, biomeCobblestoneMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Concrete
    		Object[] tryConcrete = ModObjects.chooseModConcrete(GeneralConfig.useVillageColors ? townColor : 15); // Black
        	Block concreteBlock = Blocks.stained_hardened_clay; int concreteMeta = GeneralConfig.useVillageColors ? townColor : 15; // Black
        	if (tryConcrete != null) {concreteBlock = (Block) tryConcrete[0]; concreteMeta = (Integer) tryConcrete[1];}
    		for(int[] uuvvww : new int[][]{
    			{3,3,5, 3,3,8}, {4,3,3, 9,3,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], concreteBlock, concreteMeta, concreteBlock, concreteMeta, false);	
    		}
    		
    		
    		// Cobblestone stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			{4,7,8, 0}, {5,8,8, 0}, {7,8,8, 1}, {9,6,8, 1}, 
    			{3,6,7, 0}, {9,6,7, 1}, 
    			{3,6,6, 0}, {5,8,6, 0}, {7,8,6, 1}, {8,7,6, 1}, {9,6,6, 1}, 
    			{4,7,5, 0}, {5,8,5, 0}, {7,8,5, 1}, {8,7,5, 1}, {9,6,5, 1}, 
    			{4,7,4, 0}, {5,8,4, 0}, {7,8,4, 1}, {8,7,4, 1}, {9,6,4, 1}, 
    			{3,6,3, 0}, {4,7,3, 0}, {5,8,3, 0}, {7,8,3, 1}, {8,7,3, 1}, {9,6,3, 1}, 
    			{3,6,2, 0}, {4,7,2, 0}, {5,8,2, 0}, {8,7,2, 1}, {9,6,2, 1}, 
    			{3,6,1, 0}, {5,8,1, 0}, {9,6,1, 1}, 
    			{5,8,0, 0}, {7,8,0, 1}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Mossy Cobblestone stairs
    		Block modblock = ModObjects.chooseModMossyCobblestoneStairsBlock();
    		if (modblock==null) {modblock = Blocks.stone_stairs;}
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(modblock, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			{3,6,8, 0}, {8,7,8, 1}, 
    			{4,7,7, 0}, {5,8,7, 0}, {7,8,7, 1}, {8,7,7, 1}, 
    			{4,7,6, 0}, 
    			{3,6,5, 0}, 
    			{3,6,4, 0}, 
    			{7,8,2, 1}, 
    			{4,7,1, 0}, {7,8,1, 1}, {8,7,1, 1}, 
    			{3,6,0, 0}, {4,7,0, 0}, {8,7,0, 1}, {9,6,0, 1}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeMossyCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Cobblestone Slab (lower)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabLowerBlock = (Block)blockObject[0]; int biomeCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{6,9,1, 6,9,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Mossy Cobblestone Slab (lower)
    		Block biomeMossyCobblestoneSlabLowerBlock = biomeCobblestoneSlabLowerBlock; int biomeMossyCobblestoneSlabLowerMeta = biomeCobblestoneSlabLowerMeta;
    		blockObject = ModObjects.chooseModMossyCobblestoneSlabBlock(false);
    		if (blockObject != null)
    		{
    			blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs);
    			biomeMossyCobblestoneSlabLowerBlock = (Block)blockObject[0]; biomeMossyCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		}
    		for(int[] uuvvww : new int[][]{
    			{6,9,0, 6,9,0}, {6,9,4, 6,9,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, biomeMossyCobblestoneSlabLowerBlock, biomeMossyCobblestoneSlabLowerMeta, false);	
    		}
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{6,4,3, 2, 1, 1}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
    		
    		
    		// Smithing table
    		blockObject = ModObjects.chooseModSmithingTable(biomePlankMeta); Block smithingTableBlock = (Block) blockObject[0]; int smithingTableMeta = (Integer) blockObject[1];
    		for (int[] uvw : new int[][]{
    			{4,4,7}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, smithingTableBlock, smithingTableMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
            
            
            // Bookshelves
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.bookshelf, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeBookshelfBlock = (Block)blockObject[0]; int biomeBookshelfMeta = (Integer)blockObject[1];
            for (int[] uuvvww : new int[][]{
        		{4,6,4, 4,6,5}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeBookshelfBlock, biomeBookshelfMeta, biomeBookshelfBlock, biomeBookshelfMeta, false);
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	// Exterior
        		{6,6,1}, 
        		// Interior
            	{4,6,7}, {8,6,5}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Polished Andesite table
        	Object[] tryPolishedAndesiteSlabUpper;
        	Block tryPolishedAndesiteStairs;
        	
        	Block polishedAndesiteSlabUpperBlock; int polishedAndesiteSlabUpperMeta;
        	Block polishedAndesiteStairsBlock;
        	
        	// First try polished Andesite
        	tryPolishedAndesiteSlabUpper = ModObjects.chooseModPolishedAndesiteSlabObject(true);
        	tryPolishedAndesiteStairs = ModObjects.chooseModPolishedAndesiteStairsBlock();
        	if (tryPolishedAndesiteSlabUpper!=null && tryPolishedAndesiteStairs!=null)
        	{
        		// Both exist
        		polishedAndesiteSlabUpperBlock = (Block)tryPolishedAndesiteSlabUpper[0]; polishedAndesiteSlabUpperMeta = (Integer)tryPolishedAndesiteSlabUpper[1];
        		polishedAndesiteStairsBlock = tryPolishedAndesiteStairs;
        	}
        	else // Try regular Andesite
        	{
        		polishedAndesiteSlabUpperBlock = ModObjects.chooseModAndesiteSlabBlock();
            	tryPolishedAndesiteStairs = ModObjects.chooseModAndesiteStairsBlock();
            	if (polishedAndesiteSlabUpperBlock!=null && tryPolishedAndesiteStairs!=null)
            	{
            		polishedAndesiteStairsBlock = tryPolishedAndesiteStairs;
            		polishedAndesiteSlabUpperMeta = 8;
            	}
            	else // Just use stone brick
            	{
            		polishedAndesiteSlabUpperBlock=Blocks.stone_slab; polishedAndesiteSlabUpperMeta=5+8;
                	polishedAndesiteStairsBlock=Blocks.stone_brick_stairs;
            	}
        	}
        	
    		// Polished Andesite Slab (Upper)
    		for (int[] uvw : new int[][]{
    			{4,4,5}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, polishedAndesiteSlabUpperBlock, polishedAndesiteSlabUpperMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
        	
    		// Polished Andesite Stairs
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			{4,4,4, 2+4}, {4,4,6, 3+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, polishedAndesiteStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		
    		// Polished Andesite
    		blockObject = ModObjects.chooseModPolishedAndesiteObject();
    		if (blockObject==null) // Try polished stone
    		{
    			blockObject = ModObjects.chooseModSmoothStoneBlockObject(); // Guarantees a vanilla stone if this fails
    		};
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs); Block biomePolishedAndesiteBlock = (Block)blockObject[0]; int biomePolishedAndesiteMeta = (Integer)blockObject[1];
    		for (int[] uvw : new int[][]{
    			// Counter
    			{7,4,5}, {8,4,5}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomePolishedAndesiteBlock, biomePolishedAndesiteMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
        	
        	
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 8;
        	int chestV = 4;
        	int chestW = 7;
        	int chestO = 3; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo("vn_toolsmith");
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
            
            
            // Grass
            for(int[] uuvvww : new int[][]{
            	{1,0,1, 1,0,1}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeTopBlock, biomeTopMeta, biomeTopBlock, biomeTopMeta, false);	
            }
        	
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1: rightward, 2:backward, 3: leftward
        			// Left side
        			{2,5,5, 3}, {2,4,5, 3}, {2,3,5, 3}, {2,2,5, 3}, 
        			{2,5,8, 3}, {2,4,8, 3}, {2,3,8, 3}, {2,2,8, 3}, {2,1,8, 3}, 
        			// Away-facing vines
        			{3,5,9, 0}, {3,4,9, 0}, 
        			{8,6,9, 0}, {8,5,9, 0}, {8,4,9, 0}, 
            		})
                {
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
        			}
                }
        	}
    		
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			// Villager
    			int s = random.nextInt(13); 
    			
    			int u = s <=3 ? s+5 : s <=5 ? s+1 : s <=9 ? s-1 : s-5;
    			int v = 4;
    			int w = s <=3 ? 4 : s <=5 ? 5 : s <=9 ? 6 : 7;
    			
    			EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 3, 3, 0); // Tool Smith
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
    			world.spawnEntityInWorld(entityvillager);
    		}
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 3;}
    }
    
    
    // --- Weapon Smithy --- //
    // designed by AstroTibs
    
    public static class SwampWeaponSmithy extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			" F    F ",
    			"  FFFF  ",
    			"  FFFF  ",
    			" FFFFFF ",
    			"FFFFFFFF",
    			" FFFFFF ",
    			"  FFFF  ",
    			"   PP   ",
    			"  FPPF  ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 7;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 2; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 2;
    	private static final int DECREASE_MAX_U = 2;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampWeaponSmithy() {}

    	public SwampWeaponSmithy(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampWeaponSmithy buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampWeaponSmithy(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
        	
        	
        	// Cobblestone wall
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{0,2,4, 0,2,4}, 
        		{1,2,8, 1,2,8}, 
        		{7,2,4, 7,2,4}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, false);
            }
        	
        	
        	// Mossy Cobblestone wall
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 1, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyCobblestoneWallBlock = (Block)blockObject[0]; int biomeMossyCobblestoneWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{6,2,8, 6,2,8}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneWallBlock, biomeMossyCobblestoneWallMeta, biomeMossyCobblestoneWallBlock, biomeMossyCobblestoneWallMeta, false);
            }
            
            
        	// Torches
            for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	{0,3,4, -1}, 
        		{1,3,8, -1}, {6,3,8, -1}, 
        		{7,3,4, -1}, 
        	})
        	{
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
        	}
        	
        	
        	// Cobblestone
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, materialType, biome, disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
            	{0,1,4, 0,1,4}, 
            	{1,1,3, 1,4,3}, {1,1,4, 1,3,4}, {1,1,5, 1,4,5}, 
            	{2,1,2, 2,4,2}, {2,5,4, 2,5,5}, {2,4,5, 2,4,5}, {2,1,6, 2,7,6}, {2,1,7, 2,3,7}, 
            	{3,1,7, 4,7,7}, {3,5,5, 4,7,5}, 
            	{5,1,2, 5,4,2}, {5,5,4, 5,5,5}, {5,4,5, 5,4,5}, {5,1,6, 5,7,6}, {5,1,7, 5,3,7}, 
            	{6,1,3, 6,4,3}, {6,1,4, 6,3,4}, {6,1,5, 6,4,5}, 
            	{7,1,4, 7,1,4}, 
            	// Upper entry lip
            	{3,4,2, 4,4,2}, 
            	// Lip in front of basin
            	{2,1,5, 5,1,5}, 
            	// Bottom of basin
            	{2,0,5, 5,0,7}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            }
        	
        	
        	// Mossy Cobblestone
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.mossy_cobblestone, 0, materialType, biome, disallowModSubs); Block biomeMossyCobblestoneBlock = (Block)blockObject[0]; int biomeMossyCobblestoneMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
            	{1,1,8, 1,1,8}, {6,1,8, 6,1,8}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, biomeMossyCobblestoneBlock, biomeMossyCobblestoneMeta, false);
            }
    		
    		
    		// Cobblestone stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Roof
    			{1,5,5, 0}, {1,5,4, 0}, {1,5,3, 3}, 
    			{2,5,3, 0}, 
    			{2,5,2, 3}, {3,5,2, 3}, {4,5,2, 3}, {5,5,2, 3}, 
    			{5,5,3, 1}, 
    			{6,5,5, 1}, {6,5,4, 1}, {6,5,3, 3}, 
    			// Ceiling
    			{3,4,5, 3+4}, {4,4,5, 3+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Cobblestone Slab (lower)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabLowerBlock = (Block)blockObject[0]; int biomeCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{1,4,4, 1,4,4}, 
    			{6,4,4, 6,4,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, false);	
    		}
        	
        	
            // Glazed terracotta
        	Object[] tryGlazedTerracotta;
        	for (int[] uvwoc : new int[][]{ // u,v,w, orientation, dye color
        		// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
        		{3,5,3, 2, GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
        		{3,5,4, 3, GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
        		{4,5,3, 1, GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
        		{4,5,4, 0, GeneralConfig.useVillageColors ? this.townColor : 15}, // Black
           		})
        	{
        		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(uvwoc[4], StructureVillageVN.chooseGlazedTerracottaMeta(uvwoc[3], this.coordBaseMode));
        		if (tryGlazedTerracotta != null)
            	{
        			this.placeBlockAtCurrentPosition(world, (Block)tryGlazedTerracotta[0], (Integer)tryGlazedTerracotta[1], uvwoc[0], uvwoc[1], uvwoc[2], structureBB);
            	}
        		else
        		{
        			this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, uvwoc[4], uvwoc[0], uvwoc[1], uvwoc[2], structureBB);
        		}
            }
    		
    		
            // Gravel
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.gravel, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGravelBlock = (Block)blockObject[0]; int biomeGravelMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
    			{2,1,3, 5,1,4}, 
    			{3,1,2, 4,1,2}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeGravelBlock, biomeGravelMeta, biomeGravelBlock, biomeGravelMeta, false);	
            }
    		
    		
    		// Fences
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
    		for (int[] uuvvww : new int[][]{
    			// Support posts
    			{2,2,0, 2,3,0}, {5,2,0, 5,3,0}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
    		}
			
			
			// Wool
			for (int[] uvwo : new int[][]{
				{2,4,0, (GeneralConfig.useVillageColors ? this.townColor3 : 10)}, // Purple
				{3,4,0, (GeneralConfig.useVillageColors ? this.townColor : 15)}, // Black
				{4,4,0, (GeneralConfig.useVillageColors ? this.townColor3 : 10)}, // Purple
				{5,4,0, (GeneralConfig.useVillageColors ? this.townColor : 15)}, // Black
				{2,4,1, (GeneralConfig.useVillageColors ? this.townColor : 15)}, // Black
				{3,4,1, (GeneralConfig.useVillageColors ? this.townColor3 : 10)}, // Purple
				{4,4,1, (GeneralConfig.useVillageColors ? this.townColor : 15)}, // Black
				{5,4,1, (GeneralConfig.useVillageColors ? this.townColor3 : 10)}, // Purple
			})
			{
				this.placeBlockAtCurrentPosition(world, Blocks.wool, uvwo[3], uvwo[0], uvwo[1], uvwo[2], structureBB);
			}
            
            
            // Grindstone
        	for (int[] uvwoh : new int[][]{ // u,v,w, orientation, isHanging
        		// Orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        		{2,2,5, 1, 1}, 
        		})
            {
        		// Generate the blockObject here so that we have the correct meta on hand
        		blockObject = ModObjects.chooseModGrindstone(uvwoh[3], this.coordBaseMode, uvwoh[4]==1); Block biomeGrindstoneBlock = (Block)blockObject[0]; int biomeGrindstoneMeta = (Integer)blockObject[1];
            	
        		this.placeBlockAtCurrentPosition(world, biomeGrindstoneBlock, biomeGrindstoneMeta, uvwoh[0], uvwoh[1], uvwoh[2], structureBB);
            }
        	
        	
        	// Furnaces
            for (int[] uvwo : new int[][]{ // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
            	{5,3,5, 2}, 
            	{5,2,5, 2}, 
            	})
            {
                this.placeBlockAtCurrentPosition(world, Blocks.furnace, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2]), StructureVillageVN.chooseFurnaceMeta(uvwo[3], this.coordBaseMode), 2);
            }
        	
        	
            // Iron bars
            for(int[] uuvvww : new int[][]{
            	{3,7,6, 4,7,6}, 
            	{3,2,6, 4,2,6}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.iron_bars, 0, Blocks.iron_bars, 0, false);
            }
    		
    		
    		// Lava
    		for(int[] uvw : new int[][]{
    			// Floor
    			{3,1,6}, {4,1,6}, 
    			})
    		{
                this.placeBlockAtCurrentPosition(world, Blocks.lava, 0, uvw[0], uvw[1], uvw[2], structureBB);
    		}
        	
        	
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 2;
        	int chestV = 2;
        	int chestW = 3;
        	int chestO = 0; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo("vn_weaponsmith");
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
    		
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			// Villager
    			int s = random.nextInt(9); 
    			
    			int u = s <=0 ? 2 : s <=3 ? 3 : s <=6 ? 4 : 5;
    			int v = 2;
    			int w = s <=0 ? 4 : s <=6 ? 3+((s-1)%3) : s-4;
    			
    			EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 3, 2, 0); // Weapon Smith
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
    			world.spawnEntityInWorld(entityvillager);
    		}
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 3;}
    }
    
    
    // --- Wild Farm --- //
    // designed by THASSELHOFF
    
    public static class SwampWildFarm extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    			"      FF       ",
    			"     FFFFFF    ",
    			"   FFFFFFFFF   ",
    			"  FFFFFFFFFFFF ",
    			" FFFFFFFFFFFFFF",
    			"FFFFFFFFFFFFFFF",
    			"FFFFFFFFFFFFFFF",
    			" FFFFFFFFFFFFF ",
    			"  FFFFFFFFFFFF ",
    			"  FFFFFFFFFFFF ",
    			"   FFFFFFFFFF  ",
    			"   FFFFFFFFFF  ",
    			"    FFFFFFFFF  ",
    			"     FFFFF     ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 5;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 2; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1+2+4+8; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
    	public SwampWildFarm() {}

    	public SwampWildFarm(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
    		if (start!=null)
    		{
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
    	
    	public static SwampWildFarm buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new SwampWildFarm(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
    	}
    	
    	
    	@Override
    	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
    	{
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
            
            
            // Grass
            for(int[] uuvvww : new int[][]{
            	{0,1,7, 0,1,8}, 
            	{1,1,6, 1,1,6}, {1,1,9, 1,1,9}, 
            	{2,1,4, 2,1,4}, {2,1,10, 2,1,10}, 
            	{3,1,2, 3,1,3}, {3,1,10, 3,1,11}, 
            	{4,1,1, 4,1,2}, 
            	{5,1,0, 5,1,1}, {5,1,12, 5,1,12}, 
            	{6,1,0, 6,1,0}, {6,1,13, 7,1,13}, 
            	{8,1,12, 10,1,12}, 
            	{10,1,11, 11,1,11}, 
            	{12,1,10, 13,1,10}, 
            	{13,1,9, 14,1,9}, {13,1,4, 13,1,6}, 
            	{14,1,7, 14,1,8}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeTopBlock, biomeTopMeta, biomeTopBlock, biomeTopMeta, false);	
            }
    		
            
    		// Water
    		for(int[] uuvvww : new int[][]{
    			{1,1,7, 2,1,8}, 
    			{2,1,9, 2,1,9}, 
    			{3,1,8, 4,1,9}, {4,1,10, 4,1,10}, 
    			{5,1,9, 6,1,11}, {6,1,12, 6,1,12}, {7,1,9, 7,1,9}, {7,1,11, 7,1,12}, 
    			{3,1,4, 3,1,4}, {4,1,3, 4,1,4}, {5,1,2, 5,1,5}, {6,1,1, 6,1,6}, 
    			{7,1,5, 7,1,5}, 
    			{7,1,1, 8,1,2}, {8,1,4, 8,1,5}, {8,1,8, 8,1,8}, 
    			{9,1,2, 9,1,8}, 
    			{10,1,2, 10,1,3}, {10,1,5, 10,1,6}, 
    			{11,1,2, 11,1,5}, 
    			{12,1,5, 12,1,5}, 
    			{10,1,10, 10,1,10}, {11,1,8, 11,1,10}, {12,1,7, 12,1,9}, {13,1,7, 13,1,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);	
    		}
    		
            
            // Moist Farmland with crop above
            Block[] cropPair1 = StructureVillageVN.chooseCropPair(random);
        	Block[] cropPair2 = StructureVillageVN.chooseCropPair(random);
        	for(int[] uvwfcp : new int[][]{
            	// u,v,w, farmland moisture (0:dry, 7:moist), crop, crop progress
            	// Large left-hand blob
            	{2,1,5, 7,0,random.nextInt(6)}, {2,1,6, 7,0,random.nextInt(6)}, 
            	{3,1,5, 7,0,random.nextInt(6)}, {3,1,6, 7,0,random.nextInt(6)}, {3,1,7, 7,0,random.nextInt(6)}, 
            	{4,1,5, 7,0,random.nextInt(6)}, {4,1,6, 7,0,random.nextInt(6)}, {4,1,7, 7,0,random.nextInt(6)}, 
            	{5,1,6, 7,0,random.nextInt(6)}, {5,1,7, 7,0,random.nextInt(6)}, {5,1,8, 7,0,random.nextInt(6)}, 
            	{6,1,7, 7,0,random.nextInt(6)}, {6,1,8, 7,0,random.nextInt(6)}, 
            	{7,1,6, 7,0,random.nextInt(6)}, {7,1,7, 7,0,random.nextInt(6)}, {7,1,8, 7,0,random.nextInt(6)}, 
            	{8,1,6, 7,0,random.nextInt(6)}, {8,1,7, 7,0,random.nextInt(6)}, 
            	// Small front-left blob
            	{7,1,3, 7,1,random.nextInt(6)}, {7,1,4, 7,1,random.nextInt(6)}, 
            	{8,1,3, 7,1,random.nextInt(6)}, 
            	// Single patch in the back left
            	{4,1,11, 7,1,random.nextInt(6)}, 
            	// Back-right strip
            	{7,1,10, 7,2,random.nextInt(6)}, 
            	{8,1,9, 7,2,random.nextInt(6)}, {8,1,10, 7,2,random.nextInt(6)}, {8,1,11, 7,2,random.nextInt(6)}, 
            	{9,1,9, 7,2,random.nextInt(6)}, {9,1,10, 7,2,random.nextInt(6)}, {9,1,11, 7,2,random.nextInt(6)}, 
            	{10,1,7, 7,2,random.nextInt(6)}, {10,1,8, 7,2,random.nextInt(6)}, {10,1,9, 7,2,random.nextInt(6)}, 
            	{11,1,6, 7,2,random.nextInt(6)}, {11,1,7, 7,2,random.nextInt(6)}, 
            	{12,1,6, 7,2,random.nextInt(6)}, 
            	// Front-right strip
            	{7,1,0, 7,3,random.nextInt(6)}, 
            	{8,1,0, 7,3,random.nextInt(6)}, 
            	{9,1,0, 7,3,random.nextInt(6)}, {9,1,1, 7,3,random.nextInt(6)}, 
            	{10,1,1, 7,3,random.nextInt(6)}, 
            	{11,1,1, 7,3,random.nextInt(6)}, 
            	{12,1,1, 7,3,random.nextInt(6)}, {12,1,2, 7,3,random.nextInt(6)}, {12,1,3, 7,3,random.nextInt(6)}, {12,1,4, 7,3,random.nextInt(6)}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.farmland, uvwfcp[3], uvwfcp[0], uvwfcp[1], uvwfcp[2], structureBB);
            	this.placeBlockAtCurrentPosition(world, (uvwfcp[4]/2==0?cropPair1:cropPair2)[uvwfcp[4]%2], uvwfcp[5], uvwfcp[0], uvwfcp[1]+1, uvwfcp[2], structureBB);
            }
    		
    		
    		// Lilypad
    		for (int[] uvw : new int[][]{
    			{2,2,9}, 
    			{3,2,8}, 
    			{5,2,3}, 
    			{6,2,2}, {6,2,5}, {6,2,11}, 
    			{9,2,3}, {9,2,7}, 
    			{11,2,4}, 
    			{12,2,9}, 
    			{13,2,7}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.waterlily, 0, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
            // Gravel
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.gravel, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGravelBlock = (Block)blockObject[0]; int biomeGravelMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	{7,0,0, 8,0,0}, 
            	{6,0,3, 6,0,4}, {7,0,2, 7,0,3}, 
            	{10,0,2, 10,0,3}, {11,0,2, 11,0,2}, 
            	{0,0,8, 2,0,8}, {1,0,9, 3,0,9}, 
            	{8,0,7, 9,0,8}, {10,0,8, 10,0,8}, 
            	{7,0,11, 8,0,11}, {7,0,12, 7,0,12}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeGravelBlock, biomeGravelMeta, biomeGravelBlock, biomeGravelMeta, false);	
            }
            
            
            // Clay
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.clay, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeClayBlock = (Block)blockObject[0]; int biomeClayMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	{5,0,1, 6,0,1}, {6,0,2, 7,0,2}, {7,0,3, 7,0,3}, 
            	{2,0,4, 2,0,4}, {3,0,3, 4,0,4}, {5,0,4, 5,0,5}, 
            	{9,0,6, 10,0,6}, {10,0,7, 11,0,7}, {11,0,8, 11,0,8}, {11,0,9, 13,0,9}, 
            	{4,0,9, 4,0,9}, {3,0,10, 6,0,10}, {3,0,11, 4,0,11}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeClayBlock, biomeClayMeta, biomeClayBlock, biomeClayMeta, false);	
            }
    		
    		
    		// Attempt to add GardenCore Compost Bins. If this fails, add farmland with a crop instead
            Block compostBin = ModObjects.chooseModComposterBlock();
            for(int[] uvw : new int[][]{
    			{10,2,4}, 
    			})
    		{
            	if (compostBin!=null)
            	{
        			this.placeBlockAtCurrentPosition(world, compostBin, 0, uvw[0], uvw[1], uvw[2], structureBB);	
        			this.placeBlockAtCurrentPosition(world, biomeFillerBlock, biomeDirtMeta, uvw[0], uvw[1]-1, uvw[2], structureBB);	
            	}
            	else
            	{
                	this.placeBlockAtCurrentPosition(world, Blocks.farmland, 7, uvw[0], uvw[1]-1, uvw[2], structureBB);
                	this.placeBlockAtCurrentPosition(world, cropPair1[1], random.nextInt(6), uvw[0], uvw[1], uvw[2], structureBB);
            	}
    		}
            
            
            // Sugarcane
    		for(int[] uuvvww : new int[][]{
    			// Back
    			{5,2,12, 5,3,12}, 
    			{6,2,13, 6,4,13}, 
    			{7,2,13, 7,2,13}, 
    			{8,2,12, 8,3,12}, 
    			{10,2,11, 10,2,11}, 
    			{14,2,8, 14,2,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.reeds, 0, Blocks.reeds, 0, false);	
    		}
    		
    		
    		// Unkempt Grass
    		for (int[] uvwg : new int[][]{ // g is grass type
    			{0,2,7, 2}, 
    			{1,2,6, 2}, 
    			{2,2,10, 0}, 
    			{3,2,10, 0}, 
    			{4,2,2, 0}, 
    			{5,2,1, 1}, 
    			{12,2,10, 0}, 
    			{13,2,5, 1}, {13,2,9, 0}, {13,2,10, 1}, 
    		})
    		{
    			if (uvwg[3]==0) // Short grass
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 1, uvwg[0], uvwg[1], uvwg[2], structureBB);
    			}
    			else if (uvwg[3]==1) // Tall grass
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 2, uvwg[0], uvwg[1], uvwg[2], structureBB);
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 11, uvwg[0], uvwg[1]+1, uvwg[2], structureBB);
    			}
    			else if (uvwg[3]==2) // Fern
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 2, uvwg[0], uvwg[1], uvwg[2], structureBB);
    			}
    			else // Large Fern
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 3, uvwg[0], uvwg[1], uvwg[2], structureBB);
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 11, uvwg[0], uvwg[1]+1, uvwg[2], structureBB);
    			}
    		}
    		
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			// Villager
    			int[][] farmerPositions = new int[][]{
                	// On farmland
                	// Large left-hand blob
                	{2,1,5}, 
                	{3,1,5}, 
                	{4,1,5}, 
                	{5,1,6}, 
                	{6,1,7}, 
                	{7,1,6}, 
                	{8,1,6}, 
                	// Small front-left blob
                	{7,1,3}, 
                	{8,1,3}, 
                	// Single patch in the back left
                	{4,1,11}, 
                	// Back-right strip
                	{7,1,10}, 
                	{8,1,9}, 
                	{9,1,9}, 
                	{10,1,7}, 
                	{11,1,6}, 
                	{12,1,6}, 
                	// Front-right strip
                	{7,1,0}, 
                	{8,1,0}, 
                	{9,1,0}, 
                	{10,1,1}, 
                	{11,1,1}, 
                	{12,1,1}, 
                	// On grass
                	{0,1,7}, {0,1,8}, 
                	{1,1,6}, {1,1,9}, 
                	{2,1,4}, {2,1,10}, 
                	{3,1,2}, {3,1,3}, {3,1,10}, {3,1,11}, 
                	{4,1,1}, {4,1,2}, 
                	{5,1,0}, {5,1,1}, {5,1,12}, 
                	{6,1,0}, {6,1,13}, {7,1,13}, 
                	{8,1,12}, {10,1,12}, 
                	{10,1,11}, {11,1,11}, 
                	{12,1,10}, {13,1,10}, 
                	{13,1,9}, {14,1,9}, {13,1,4}, {13,1,6}, 
                	{14,1,7}, {14,1,8}, 
                	};
    			int[] farmerPosition = farmerPositions[random.nextInt(farmerPositions.length)];
    			
    			int u = farmerPosition[0];
    			int v = farmerPosition[1]+1;
    			int w = farmerPosition[2];

    			EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 0, 1, 0); // Farmer
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
    			world.spawnEntityInWorld(entityvillager);
    		}
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    
    // ------------------ //
    // --- Road Decor --- //
    // ------------------ //

    
    // --- Road Decor --- //
    
    public static class SwampStreetDecor extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
    	
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = 3;
    	public static final int STRUCTURE_DEPTH = 3;
    	public static final int STRUCTURE_HEIGHT = 5;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
        public SwampStreetDecor() {}

        public SwampStreetDecor(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            if (start!=null)
            {
            	this.villageType=start.villageType;
            	this.materialType=start.materialType;
            	this.disallowModSubs=start.disallowModSubs;
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

        public static SwampStreetDecor buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new SwampStreetDecor(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.averageGroundLevel < 0)
            {
        		this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
        				// Set the bounding box version as this bounding box but with Y going from 0 to 512
        				new StructureBoundingBox(
        						this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
        						this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
        				true, (byte)1, this.coordBaseMode);
        		
                if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void

                this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
            }
        	
        	// In the event that this village construction is resuming after being unloaded
        	// you may need to reestablish the village name/color/type info
            if (
                	this.townColor==-1
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
            }
            
        	WorldChunkManager chunkManager= world.getWorldChunkManager();
        	int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
            BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
			Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
            
			if (this.villageType==null)
			{
    			try {
                	String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
                	if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
                	else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
                	}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
			}
			
			if (this.materialType==null)
			{
    			try {
                	String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
                	if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
                	else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
                	}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
			}
			
			if (!this.disallowModSubs)
			{
    			try {
                	String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
                	if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
                	else {this.disallowModSubs = false;}
                	}
    			catch (Exception e) {this.disallowModSubs = false;}
			}
        	// Reestablish biome if start was null or something
            if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
        	
            Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	// Establish top and filler blocks, substituting Grass and Dirt if they're null
        	Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
            
            // Decor
        			
            int[][] decorUVW = new int[][]{
            	{1, 0, 1},
            };  
            
            for (int j=0; j<decorUVW.length; j++)
            {
            	// Get coordinates
            	int[] uvw = decorUVW[j];
            	
            	// Select a random distance from the path
            	// Set random seed
            	Random randomFromXYZ = new Random();
            	randomFromXYZ.setSeed(
    					world.getSeed() +
    					FunctionsVN.getUniqueLongForXYZ(
    							this.getXWithOffset(uvw[0], uvw[2]),
    							this.getYWithOffset(uvw[1]),
    							this.getZWithOffset(uvw[0], uvw[2]))
    							);
            	int decorDepth = (Integer) FunctionsVN.weightedRandom(
            			new    int[]{-1,0,1}, // Values
            			new double[]{ 1,9,5}, // Weights
            			randomFromXYZ);
            	
            	uvw[2] = decorDepth;
            	
            	int decorHeightY = StructureVillageVN.getAboveTopmostSolidOrLiquidBlockVN(world, this.getXWithOffset(uvw[0], uvw[2]), this.getZWithOffset(uvw[0], uvw[2]))-this.getYWithOffset(0);

            	// If the decor is ON the road, do a surround check to make sure it isn't sunken into the ground
            	if (decorDepth<0)
            	{
	            	int nonairSurrounding = 0;
	            	int decorY = this.getYWithOffset(decorHeightY);
	            	for (int i=0; i<8; i++)
	            	{
	            		int[][] surroundpos = new int[][]{
	            			{0,0},
	            			{0,1},
	            			{0,2},
	            			{1,2},
	            			{2,2},
	            			{2,1},
	            			{2,0},
	            			{1,0},
	            		};
	            		int u = surroundpos[i][0]; int w = surroundpos[i][0];
	            		int x = this.getXWithOffset(u, w);
	            		int z = this.getZWithOffset(u, w);
	            		if (world.getBlock(x, decorY, z)!=Blocks.air)
	            		{
	            			if (++nonairSurrounding >=4) {decorHeightY++; break;}
	            		}
	            	}
            	}
            	
            	//this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, uvw[0], decorHeightY-1, uvw[2], structureBB);
            	this.clearCurrentPositionBlocksUpwards(world, uvw[0], decorHeightY+1, uvw[2], structureBB);
            	
            	// Get ground level
            	if (this.decorHeightY.size()<(j+1))
            	{
            		// There are fewer stored ground levels than this decor number, so this is being generated for the first time.
            		// Add new ground level
            		this.decorHeightY.add(decorHeightY);
            	}
            	else
            	{
            		// There is already (presumably) a value for this ground level, so this decor is being multiply generated.
            		// Retrieve ground level
            		decorHeightY = this.decorHeightY.get(j);
            	}
            	
            	
            	// Generate decor
            	ArrayList<BlueprintData> decorBlueprint = StructureVillageVN.getRandomDecorBlueprint(this.villageType, this.materialType, this.disallowModSubs, this.biome, this.coordBaseMode, randomFromXYZ, VillageGeneratorConfigHandler.allowTaigaTroughs && !VillageGeneratorConfigHandler.restrictTaigaTroughs);
            	
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
            	
            	// Grass base
            	if (!world.getBlock(
            			this.getXWithOffset(uvw[0], uvw[2]),
            			this.getYWithOffset(decorHeightY-1),
            			this.getZWithOffset(uvw[0], uvw[2])
            			).isNormalCube()
            			|| decorDepth < 0 // If it's in the center of the road, make sure the base is grass so it doesn't become path -> dirt
            			) {
            		this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, uvw[0], decorHeightY-1, uvw[2], structureBB);
            	}
            }
            
            // Clean items
            if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
            return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    // --- Straddling Lantern --- //
    // designed by AstroTibs
    
    public static class SwampRoadAccent extends StructureVillagePieces.Village
    {
    	// Stuff to be used in the construction
    	public boolean entitiesGenerated = false;
    	public ArrayList<Integer> decorHeightY = new ArrayList();
    	public FunctionsVN.VillageType villageType=null;
    	public FunctionsVN.MaterialType materialType=null;
    	public boolean disallowModSubs=false;
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
				"   ",
				"PPP",
				"PPP",
				"PPP",
				"   ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 5;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	private static final int W_OFFSET = -4; // How much to shift the well to ensure it is positioned onto the road
    	public static final byte MEDIAN_BORDERS = 1 + 4; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	private int averageGroundLevel = -1;
    	
        public SwampRoadAccent() {}

        public SwampRoadAccent(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		
    		// Offset the bounding box to position it onto the street
    		this.boundingBox.offset(
    				this.coordBaseMode==1 ? -W_OFFSET : this.coordBaseMode==3 ? W_OFFSET : 0,
    				0,
    				this.coordBaseMode==0 ? W_OFFSET : this.coordBaseMode==2 ? -W_OFFSET: 0);
    		
    		// Additional stuff to be used in the construction
            if (start!=null)
            {
    			this.villageType=start.villageType;
    			this.materialType=start.materialType;
    			this.disallowModSubs=start.disallowModSubs;
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
        
        public static SwampRoadAccent buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 
					0, 0, 0, 
					STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH+W_OFFSET, 
					coordBaseMode);
			
			// Bounding box on the other side of the road
			StructureBoundingBox structureBBOtherSide = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 
					0, -16, -Reference.STREET_WIDTH-1-STRUCTURE_DEPTH-W_OFFSET, 
					STRUCTURE_WIDTH, STRUCTURE_HEIGHT+16, -Reference.STREET_WIDTH-1, 
					coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox)
            		&& StructureComponent.findIntersecting(pieces, structureboundingbox) == null
            		&& StructureComponent.findIntersecting(pieces, structureBBOtherSide) == null
            		? new SwampRoadAccent(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
        }
        
        
        @Override
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
			if (this.averageGroundLevel < 0)
			{
				this.averageGroundLevel = StructureVillageVN.getMedianGroundLevel(world,
						// Set the bounding box version as this bounding box but with Y going from 0 to 512
						new StructureBoundingBox(
								this.boundingBox.minX+(new int[]{INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U,INCREASE_MIN_W}[this.coordBaseMode]), this.boundingBox.minZ+(new int[]{INCREASE_MIN_W,INCREASE_MIN_U,DECREASE_MAX_W,INCREASE_MIN_U}[this.coordBaseMode]),
								this.boundingBox.maxX-(new int[]{DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U,DECREASE_MAX_W}[this.coordBaseMode]), this.boundingBox.maxZ-(new int[]{DECREASE_MAX_W,DECREASE_MAX_U,INCREASE_MIN_W,DECREASE_MAX_U}[this.coordBaseMode])),
						true, MEDIAN_BORDERS, this.coordBaseMode);
				
				if (this.averageGroundLevel < 0) {return true;} // Do not construct in a void
				
				this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.minY - GROUND_LEVEL, 0);
			}
    		
    		// In the event that this village construction is resuming after being unloaded
    		// you may need to reestablish the village name/color/type info
    		if (
    				this.townColor==-1
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
    		}
    		
    		WorldChunkManager chunkManager= world.getWorldChunkManager();
    		int bbCenterX = (this.boundingBox.minX+this.boundingBox.maxX)/2; int bbCenterZ = (this.boundingBox.minZ+this.boundingBox.maxZ)/2;
    		BiomeGenBase biome = chunkManager.getBiomeGenAt(bbCenterX, bbCenterZ);
    		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
    		if (this.villageType==null)
    		{
    			try {
    				String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedVillageType.equals("")) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.villageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
    				}
    			catch (Exception e) {this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (this.materialType==null)
    		{
    			try {
    				String mappedMaterialType = (String) (mappedBiomes.get("MaterialTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedMaterialType.equals("")) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    				else {this.materialType = FunctionsVN.MaterialType.getMaterialTypeFromName(mappedMaterialType, FunctionsVN.MaterialType.OAK);}
    				}
    			catch (Exception e) {this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, bbCenterX, bbCenterZ);}
    		}
    		
    		if (!this.disallowModSubs)
    		{
    			try {
    				String mappedBlockModSubs = (String) (mappedBiomes.get("DisallowModSubs")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
    				if (mappedBlockModSubs.toLowerCase().trim().equals("nosub")) {this.disallowModSubs = true;}
    				else {this.disallowModSubs = false;}
    				}
    			catch (Exception e) {this.disallowModSubs = false;}
    		}
    		// Reestablish biome if start was null or something
    		if (this.biome==null) {this.biome = world.getBiomeGenForCoords((this.boundingBox.minX+this.boundingBox.maxX)/2, (this.boundingBox.minZ+this.boundingBox.maxZ)/2);}
    		Object[] blockObject;
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
    		
    		// Clear space above
    		for (int u = 0; u < STRUCTURE_WIDTH; ++u) {for (int w = 0; w < STRUCTURE_DEPTH; ++w) {
    			this.clearCurrentPositionBlocksUpwards(world, u, GROUND_LEVEL, w, structureBB);
    		}}
    		
    		// Follow the blueprint to set up the starting foundation
    		for (int w=0; w < foundationPattern.length; w++) {for (int u=0; u < foundationPattern[0].length(); u++) {
    			
    			String unitLetter = foundationPattern[foundationPattern.length-1-w].substring(u, u+1).toUpperCase();
    			int posX = this.getXWithOffset(u, w);
    			int posY = this.getYWithOffset(GROUND_LEVEL-1);
    			int posZ = this.getZWithOffset(u, w);
    					
    			if (unitLetter.equals("F"))
    			{
    				// If marked with F: fill with dirt foundation
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
    			}
    			else if (unitLetter.equals("P"))
    			{
    				// If marked with P: fill with dirt foundation and top with block-and-biome-appropriate path
    				this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1+(world.getBlock(posX, posY, posZ).isNormalCube()?-1:0), w, structureBB);
    				StructureVillageVN.setPathSpecificBlock(world, materialType, biome, disallowModSubs, posX, posY, posZ, false);
    			}
        		else if (world.getBlock(posX, posY, posZ)==biomeFillerBlock)
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-2, w, structureBB);
        		}
        		
        		// Then, if the top is dirt with a non-full cube above it, make it grass
        		if (world.getBlock(posX, posY, posZ)==biomeFillerBlock && !world.getBlock(posX, posY+1, posZ).isNormalCube())
        		{
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, u, GROUND_LEVEL-1, w, structureBB);
        		}
    		}}
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uuvvww : new int[][]{
        		{1,1,4, 1,3,4}, 
        		{1,4,2, 1,4,2}, 
        		{1,1,0, 1,3,0}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
    		// Foot foundation
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		for(int[] uvw : new int[][]{
        		{1,0,4}, 
        		{1,0,0}, 
    			})
    		{
    			this.func_151554_b(world, biomeCobblestoneBlock, biomeCobblestoneMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	{1,3,2}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	{1,4,3, 1,4,3}, {1,4,1, 1,4,1}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
            
            
            // Wooden slabs (Bottom)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	{1,4,4, 1,4,4}, {1,4,0, 1,4,0}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
            }
            
    		
    		// Clean items
    		if (VillageGeneratorConfigHandler.cleanDroppedItems) {StructureVillageVN.cleanEntityItems(world, this.boundingBox);}
    		return true;
        }
        
        /**
         * Returns the villager type to spawn in this component, based on the number
         * of villagers already spawned.
         */
        @Override
        protected int getVillagerType (int number) {return 0;}
    }
    
    
    
    
	
    // ------------------- //
    // --- Biome Decor --- //
    // ------------------- //
    
	/**
	 * Returns a list of blocks and coordinates used to construct a decor piece
	 */
	public static ArrayList<BlueprintData> getRandomSwampDecorBlueprint(MaterialType materialType, boolean disallowModSubs, BiomeGenBase biome, int horizIndex, Random random)
	{
		int decorCount = 7;
		return getSwampDecorBlueprint(random.nextInt(decorCount), materialType, disallowModSubs, biome, horizIndex, random);
	}
	public static ArrayList<BlueprintData> getSwampDecorBlueprint(int decorType, MaterialType materialType, boolean disallowModSubs, BiomeGenBase biome, int horizIndex, Random random)
	{
		ArrayList<BlueprintData> blueprint = new ArrayList(); // The blueprint to export
		
		// Generate per-material blocks
		
		Object[] blockObject;

    	// Establish top and filler blocks, substituting Grass and Dirt if they're null
    	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, materialType, biome, disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, materialType, biome, disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    	Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (biome!=null && biome.topBlock!=null) {biomeTopBlock=biome.topBlock; biomeTopMeta=0;}
    	Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (biome!=null && biome.fillerBlock!=null) {biomeFillerBlock=biome.fillerBlock; biomeFillerMeta=0;}
		
    	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
    	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
    	blockObject = ModObjects.chooseModLanternBlock(false); Block biomeSittingLanternBlock = (Block)blockObject[0]; int biomeSittingLanternMeta = (Integer)blockObject[1];
    	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, materialType, biome, disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
    	
    	
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
        // Stripped Log (Across)
    	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(horizIndex%2!=0? 4:0), materialType, biome, disallowModSubs); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1];
    	Block biomeStrippedLogHorizAcrossBlock = biomeLogHorAcrossBlock; int biomeStrippedLogHorizAcrossMeta = biomeLogHorAcrossMeta;
    	// Try to see if stripped logs exist
    	if (biomeStrippedLogHorizAcrossBlock==Blocks.log || biomeStrippedLogHorizAcrossBlock==Blocks.log2)
    	{
        	if (biomeLogVertBlock == Blocks.log)
        	{
        		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 1+(horizIndex%2!=0? 1:0)); biomeStrippedLogHorizAcrossBlock = (Block)blockObject[0]; biomeStrippedLogHorizAcrossMeta = (Integer)blockObject[1];
        	}
        	else if (biomeLogVertBlock == Blocks.log2)
        	{
        		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 1+(horizIndex%2!=0? 1:0)); biomeStrippedLogHorizAcrossBlock = (Block)blockObject[0]; biomeStrippedLogHorizAcrossMeta = (Integer)blockObject[1];
        	}
    	}
    	// Stripped Log (Along)
    	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(horizIndex%2==0? 4:0), materialType, biome, disallowModSubs); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1];
    	Block biomeStrippedLogHorizAlongBlock = biomeLogHorAlongBlock; int biomeStrippedLogHorizAlongMeta = biomeLogHorAlongMeta;
    	// Try to see if stripped logs exist
    	if (biomeStrippedLogHorizAlongBlock==Blocks.log || biomeStrippedLogHorizAlongBlock==Blocks.log2)
    	{
        	if (biomeLogVertBlock == Blocks.log)
        	{
        		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 1+(horizIndex%2==0? 1:0)); biomeStrippedLogHorizAlongBlock = (Block)blockObject[0]; biomeStrippedLogHorizAlongMeta = (Integer)blockObject[1];
        	}
        	else if (biomeLogVertBlock == Blocks.log2)
        	{
        		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 1+(horizIndex%2==0? 1:0)); biomeStrippedLogHorizAlongBlock = (Block)blockObject[0]; biomeStrippedLogHorizAlongMeta = (Integer)blockObject[1];
        	}
    	}
    	
    	
    	
		boolean genericBoolean=false;
    	int genericInt=0;
		int postheight=2;
    	int lanternheight;
		
        switch (decorType)
        {
    	case 0: // Tall lantern post by AstroTibs
    	case 1:
    		postheight+=1;
    	case 2: // Medium lantern post by AstroTibs
    	case 3:
    		postheight+=1;
    	case 4: // Short lantern post by AstroTibs
    	case 5:
    		postheight+=1;
    		
    		lanternheight = 2+(random.nextBoolean()?1:0); // Either 2 or 3
    		
    		// Add lantern in front
    		BlueprintData.addFillWithBlocks(blueprint, 0,lanternheight,-1, 0,lanternheight+1,0, biomeFenceBlock, 0);
    		BlueprintData.addPlaceBlock(blueprint, 0,lanternheight,-1, biomeHangingLanternBlock, biomeHangingLanternMeta);
    		
    		// Add possible second lantern
    		if (random.nextBoolean())
    		{
    			int lantern2height = 2+(random.nextBoolean()?1:0); // Either 2 or 3
    			
    			switch (random.nextInt(5))
    			{
    			// Lantern on right
    			case 0:
    			case 1:
    				BlueprintData.addFillWithBlocks(blueprint, 0,lantern2height,0, 1,lantern2height+1,0, biomeFenceBlock, 0);
    				BlueprintData.addPlaceBlock(blueprint, 1,lantern2height,0, biomeHangingLanternBlock, biomeHangingLanternMeta);
    				break;
    			// Lantern on left
    			case 2:
    			case 3:
    				BlueprintData.addFillWithBlocks(blueprint, -1,lantern2height,0, 0,lantern2height+1,0, biomeFenceBlock, 0);
    				BlueprintData.addPlaceBlock(blueprint, -1,lantern2height,0, biomeHangingLanternBlock, biomeHangingLanternMeta);
    				break;
    			// Lantern behind
    			case 4:
    				BlueprintData.addFillWithBlocks(blueprint, 0,lantern2height,0, 0,lantern2height+1,1, biomeFenceBlock, 0);
    				BlueprintData.addPlaceBlock(blueprint, 0,lantern2height,1, biomeHangingLanternBlock, biomeHangingLanternMeta);
    				break;
    			}
    		}
    		
    		// Post
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta); // Foundation
    		BlueprintData.addFillWithBlocks(blueprint, 0,0,0, 0,postheight-1,0, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta);
    		break;
    		
    	case 6: // Toppled lantern post by AstroTibs
    		
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, biomeFillerBlock, biomeFillerMeta); // Center foundation
    		
    		switch (random.nextInt(4))
    		{
    		case 0: // Facing you
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, 0, biomeFillerBlock, biomeFillerMeta); // Foundation
    			BlueprintData.addFillWithBlocks(blueprint, 0,0,0, 1,0,0, biomeStrippedLogHorizAcrossBlock, biomeStrippedLogHorizAcrossMeta);
    			BlueprintData.addPlaceBlock(blueprint, -1, 0, 0, biomeFenceBlock, 0); // Fence post
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, 1, biomeTopBlock, biomeTopMeta); // Foundation for lantern
        		BlueprintData.addPlaceBlock(blueprint, -1, 0, 1, biomeSittingLanternBlock, biomeSittingLanternMeta);
        		break;
    			
    		case 1: // Facing left
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, -1, biomeFillerBlock, biomeFillerMeta); // Foundation
    			BlueprintData.addFillWithBlocks(blueprint, 0,0,-1, 0,0,0, biomeStrippedLogHorizAlongBlock, biomeStrippedLogHorizAlongMeta);
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, 1, biomeFenceBlock, 0); // Fence post
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, 1, biomeTopBlock, biomeTopMeta); // Foundation for lantern
        		BlueprintData.addPlaceBlock(blueprint, 1, 0, 1, biomeSittingLanternBlock, biomeSittingLanternMeta);
    			break;
    			
    		case 2: // Facing away
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, 0, biomeFillerBlock, biomeFillerMeta); // Foundation
    			BlueprintData.addFillWithBlocks(blueprint, -1,0,0, 0,0,0, biomeStrippedLogHorizAcrossBlock, biomeStrippedLogHorizAcrossMeta);
    			BlueprintData.addPlaceBlock(blueprint, 1, 0, 0, biomeFenceBlock, 0); // Fence post
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, -1, biomeTopBlock, biomeTopMeta); // Foundation for lantern
        		BlueprintData.addPlaceBlock(blueprint, 1, 0, -1, biomeSittingLanternBlock, biomeSittingLanternMeta);
    			break;
    			
    		case 3: // Facing right
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, 1, biomeFillerBlock, biomeFillerMeta); // Foundation
    			BlueprintData.addFillWithBlocks(blueprint, 0,0,0, 0,0,1, biomeStrippedLogHorizAlongBlock, biomeStrippedLogHorizAlongMeta);
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, -1, biomeFenceBlock, 0); // Fence post
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, -1, biomeTopBlock, biomeTopMeta); // Foundation for lantern
        		BlueprintData.addPlaceBlock(blueprint, -1, 0, -1, biomeSittingLanternBlock, biomeSittingLanternMeta);
    			break;
    		}
    		
    		break;
        }
        
        // Return the decor blueprint
        return blueprint;
	}
}
