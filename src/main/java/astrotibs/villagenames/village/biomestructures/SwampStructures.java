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
import astrotibs.villagenames.utility.BlockPos;
import astrotibs.villagenames.utility.FunctionsVN;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.FunctionsVN.MaterialType;
import astrotibs.villagenames.village.StructureVillageVN;
import astrotibs.villagenames.village.StructureVillageVN.StartVN;
import net.minecraft.block.Block;
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
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
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
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
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
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
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
        		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(uvwoc[4], (uvwoc[3] + this.coordBaseMode + (this.coordBaseMode < 2 ? 1 : 0))%4);
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
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + new int[]{3,5,5,4}[this.coordBaseMode], this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
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
        			// If the space is blank and the block itself is dirt, add dirt foundation and then cap with grass:
        			this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, u, GROUND_LEVEL-1, w, structureBB);
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
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 2;
    	private static final int DECREASE_MAX_U = 2;
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
            	{7,3,3, 8,3,3}, 
            	{2,3,4, 4,3,4}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogHorizAcrossBlock, biomeStrippedLogHorizAcrossMeta, biomeStrippedLogHorizAcrossBlock, biomeStrippedLogHorizAcrossMeta, false);	
            }
            
            
        	// Stripped Log (Along)
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
    		
    		
    		// Unkept Grass
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
        		this.fillWithMetadataBlocks(world, structureBB, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], uuvvwwo[3], uuvvwwo[4], uuvvwwo[5], biomeLadderBlock, StructureVillageVN.chooseFurnaceMeta(uuvvwwo[6], this.coordBaseMode), biomeLadderBlock, StructureVillageVN.chooseFurnaceMeta(uuvvwwo[6], this.coordBaseMode), false);
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
            for(int[] uuvvww : new int[][]{ // Orientation - 2:forward, 3:rightward, 0:backward (toward you), 1:leftward
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
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.leaves, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeLeafBlock = (Block)blockObject[0]; int biomeLeafMeta = (Integer)blockObject[1];
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
    			"  F          ",
    			"             ",
    			"  F F   F    ",
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
            for(int[] uwuwvs : new int[][]{ // u/w box corners, v heigh, and the side that is to be evaluated.
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
            	case 0: w_offset=1; break;// forward
            	case 1: u_offset=1; break;// forward
            	case 2: w_offset=-1; break;// forward
            	case 3: u_offset=-1; break;// forward
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
            	{6,2,2, 6,2,5}, {6,2,7, 6,2,7}, {6,3,2, 6,4,2}, {5,6,2, 7,6,2}, 
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
        		tryGlazedTerracotta = ModObjects.chooseModGlazedTerracotta(uvwoc[4], (uvwoc[3] + this.coordBaseMode + (this.coordBaseMode < 2 ? 1 : 0))%4);
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
            			biomePlankMeta);
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
    			"     F    ",
    			" F       F",
    			"          ",
    			"          ",
    			" F   F   F",
    			"          ",
    			"          ",
    			"          ",
    			" F   F   F",
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
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
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
    			{1,0,3, 5,0,10}, 
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
            
            
        	// Carpet
        	for(int[] uvwc : new int[][]{
        		// Lower
        		{2,1,7, (GeneralConfig.useVillageColors ? this.townColor3 : 10)}, // Purple
        		{3,1,6, (GeneralConfig.useVillageColors ? this.townColor3 : 10)}, // Purple
        		{4,1,7, (GeneralConfig.useVillageColors ? this.townColor3 : 10)}, // Purple
        		{2,1,6, (GeneralConfig.useVillageColors ? this.townColor2 : 13)}, // Green
        		{3,1,7, (GeneralConfig.useVillageColors ? this.townColor2 : 13)}, // Green
        		{4,1,6, (GeneralConfig.useVillageColors ? this.townColor2 : 13)}, // Green
        		})
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.carpet, uvwc[3], uvwc[0], uvwc[1], uvwc[2], structureBB);
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
            	{7,3,3, 8,3,3}, 
            	{2,3,4, 4,3,4}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogHorizAcrossBlock, biomeStrippedLogHorizAcrossMeta, biomeStrippedLogHorizAcrossBlock, biomeStrippedLogHorizAcrossMeta, false);	
            }
            
            
        	// Stripped Log (Along)
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
    		
    		
    		// Unkept Grass
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
        		// Lower
        		{3,1,2, 4,1,7, (GeneralConfig.useVillageColors ? this.townColor3 : 10)}, // Purple
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
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.leaves, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeLeafBlock = (Block)blockObject[0]; int biomeLeafMeta = (Integer)blockObject[1];
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
    				BlueprintData.addFillWithBlocks(blueprint, 0,lantern2height,0, 1,lantern2height+1,0, biomeFenceBlock, 0);
    				BlueprintData.addPlaceBlock(blueprint, 0,lantern2height,1, biomeHangingLanternBlock, biomeHangingLanternMeta);
    				break;
    			}
    		}
    		
    		// Post
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, biomeFillerBlock, biomeFillerMeta); // Foundation
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, biomeFillerBlock, biomeFillerMeta); // Foundation
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
	
	
	// Stripped Wood
//	Block biomeStrippedWoodOrLogOrLogVerticBlock = biomeLogVertBlock; int biomeStrippedWoodOrLogOrLogVerticMeta = biomeLogVertMeta;
//	
//	// Try to see if stripped wood exists
//	if (biomeLogVertBlock == Blocks.log)
//	{
//		blockObject = ModObjects.chooseModStrippedWood(biomeLogVertMeta);
//		biomeStrippedWoodOrLogOrLogVerticBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogVerticMeta = (Integer)blockObject[1];
//	}
//	else if (biomeLogVertBlock == Blocks.log2)
//	{
//		blockObject = ModObjects.chooseModStrippedWood(biomeLogVertMeta+4);
//		biomeStrippedWoodOrLogOrLogVerticBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogVerticMeta = (Integer)blockObject[1];
//	}
//	// If it doesn't exist, try stripped logs
//	if (biomeStrippedWoodOrLogOrLogVerticBlock==Blocks.log || biomeStrippedWoodOrLogOrLogVerticBlock==Blocks.log2)
//	{
//    	if (biomeLogVertBlock == Blocks.log)
//    	{
//    		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta, 0); biomeStrippedWoodOrLogOrLogVerticBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogVerticMeta = (Integer)blockObject[1];
//    	}
//    	else if (biomeLogVertBlock == Blocks.log2)
//    	{
//    		blockObject = ModObjects.chooseModStrippedLog(biomeLogVertMeta+4, 0); biomeStrippedWoodOrLogOrLogVerticBlock = (Block)blockObject[0]; biomeStrippedWoodOrLogOrLogVerticMeta = (Integer)blockObject[1];
//    	}
//	}
	
}
