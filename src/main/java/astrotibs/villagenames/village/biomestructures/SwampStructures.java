package astrotibs.villagenames.village.biomestructures;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import astrotibs.villagenames.banner.TileEntityBanner;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.config.village.VillageGeneratorConfigHandler;
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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;

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
            	"P PPPPP P",
            	" PPFFFPP ",
            	"PPFFFFFPP",
            	"PFFFFFFFP",
            	"PFFFFFFFP",
            	"PFFFFFFFP",
            	"PPFFFFFPP",
            	" PPFFFPP ",
            	"P PPPPP P",
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
    		
    		// No roads behind the structure
    		
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
        	if (this.materialType!=FunctionsVN.MaterialType.SNOW)
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
            		this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);	
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
                
                // Cobblestone foundation
    			this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, signU, signV-1, signW, structureBB);
    			
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
        			
                    // Cobblestone foundation
        			this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, bannerU, bannerV-1, bannerW, structureBB);
        			
        			
        			int bannerX = this.getXWithOffset(bannerU, bannerW);
        			int bannerY = this.getYWithOffset(bannerV);
                    int bannerZ = this.getZWithOffset(bannerU, bannerW);
                    
                    // Sign 1
                    
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
        
    
	
    // ------------------- //
    // --- Biome Decor --- //
    // ------------------- //
    
	/**
	 * Returns a list of blocks and coordinates used to construct a decor piece
	 */
	public static ArrayList<BlueprintData> getRandomSwampDecorBlueprint(MaterialType materialType, boolean disallowModSubs, BiomeGenBase biome, int horizIndex, Random random)
	{
		int decorCount = 4;
		return getSwampDecorBlueprint(random.nextInt(decorCount), materialType, disallowModSubs, biome, horizIndex, random);
	}
	public static ArrayList<BlueprintData> getSwampDecorBlueprint(int decorType, MaterialType materialType, boolean disallowModSubs, BiomeGenBase biome, int horizIndex, Random random)
	{
		ArrayList<BlueprintData> blueprint = new ArrayList(); // The blueprint to export
		
		// Generate per-material blocks
		
		Object[] blockObject;
    	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, materialType, biome, disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, materialType, biome, disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
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
    		postheight+=1;
    	case 1: // Medium lantern post by AstroTibs
    		postheight+=1;
    	case 2: // Short lantern post by AstroTibs
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
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, biomeDirtBlock, biomeDirtMeta); // Foundation
    		BlueprintData.addFillWithBlocks(blueprint, 0,0,0, 0,postheight-1,0, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta);
    		break;
    		
    	case 3: // Toppled lantern post by AstroTibs
    		
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, biomeDirtBlock, biomeDirtMeta); // Center foundation
    		
    		switch (random.nextInt(4))
    		{
    		case 0: // Facing you
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, 0, biomeDirtBlock, biomeDirtMeta); // Foundation
    			BlueprintData.addFillWithBlocks(blueprint, 0,0,0, 1,0,0, biomeStrippedLogHorizAcrossBlock, biomeStrippedLogHorizAcrossMeta);
    			BlueprintData.addPlaceBlock(blueprint, -1, 0, 0, biomeFenceBlock, 0); // Fence post
    			BlueprintData.addFillBelowTo(blueprint, -1, -2, 1, biomeDirtBlock, biomeDirtMeta); // Foundation for lantern
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, 1, biomeGrassBlock, biomeGrassMeta); // Foundation for lantern
        		BlueprintData.addPlaceBlock(blueprint, -1, 0, 1, biomeSittingLanternBlock, biomeSittingLanternMeta);
        		break;
    			
    		case 1: // Facing left
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, -1, biomeDirtBlock, biomeDirtMeta); // Foundation
    			BlueprintData.addFillWithBlocks(blueprint, 0,0,-1, 0,0,0, biomeStrippedLogHorizAlongBlock, biomeStrippedLogHorizAlongMeta);
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, 1, biomeFenceBlock, 0); // Fence post
    			BlueprintData.addFillBelowTo(blueprint, 1, -2, 1, biomeDirtBlock, biomeDirtMeta); // Foundation for lantern
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, 1, biomeGrassBlock, biomeGrassMeta); // Foundation for lantern
        		BlueprintData.addPlaceBlock(blueprint, 1, 0, 1, biomeSittingLanternBlock, biomeSittingLanternMeta);
    			break;
    			
    		case 2: // Facing away
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, 0, biomeDirtBlock, biomeDirtMeta); // Foundation
    			BlueprintData.addFillWithBlocks(blueprint, -1,0,0, 0,0,0, biomeStrippedLogHorizAcrossBlock, biomeStrippedLogHorizAcrossMeta);
    			BlueprintData.addPlaceBlock(blueprint, 1, 0, 0, biomeFenceBlock, 0); // Fence post
    			BlueprintData.addFillBelowTo(blueprint, 1, -2, -1, biomeDirtBlock, biomeDirtMeta); // Foundation for lantern
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, -1, biomeGrassBlock, biomeGrassMeta); // Foundation for lantern
        		BlueprintData.addPlaceBlock(blueprint, 1, 0, -1, biomeSittingLanternBlock, biomeSittingLanternMeta);
    			break;
    			
    		case 3: // Facing right
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, 1, biomeDirtBlock, biomeDirtMeta); // Foundation
    			BlueprintData.addFillWithBlocks(blueprint, 0,0,0, 0,0,1, biomeStrippedLogHorizAlongBlock, biomeStrippedLogHorizAlongMeta);
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, -1, biomeFenceBlock, 0); // Fence post
    			BlueprintData.addFillBelowTo(blueprint, -1, -2, -1, biomeDirtBlock, biomeDirtMeta); // Foundation for lantern
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, -1, biomeGrassBlock, biomeGrassMeta); // Foundation for lantern
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
