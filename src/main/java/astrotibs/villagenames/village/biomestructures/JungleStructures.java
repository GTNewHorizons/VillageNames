package astrotibs.villagenames.village.biomestructures;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import astrotibs.villagenames.banner.TileEntityBanner;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.config.village.VillageGeneratorConfigHandler;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.name.NameGenerator;
import astrotibs.villagenames.utility.BlockPos;
import astrotibs.villagenames.utility.FunctionsVN;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.FunctionsVN.MaterialType;
import astrotibs.villagenames.village.StructureVillageVN;
import astrotibs.villagenames.village.StructureVillageVN.StartVN;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
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

public class JungleStructures
{
	// -------------------- //
    // --- Start Pieces --- //
	// -------------------- //
	
	// --- Jungle Statue --- //
	// designed by AstroTibs
    
    public static class JungleStatue extends StartVN
    {
        // Make foundation with blanks as empty air, F as foundation spaces, and P as path
        private static final String[] foundationPattern = new String[]{
            	"  PPFFPPF  ",
            	" PPPPPPPPP ",
            	" PFPPPPPPF ",
            	"PPPPFFFFPPP",
            	"PPPFFFFFPPP",
            	"PPPFFFFFPPP",
            	" PPPFFFFPPF",
            	" FPPPPPPPP ",
            	"  FPPPPPP  ",
            	"    PPPFF  ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 10;
    	// Values for lining things up
    	public static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1 + 2 + 8; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
	    public JungleStatue() {}
		
		public JungleStatue(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, float villageSize)
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
			if (this.coordBaseMode!=2) {StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode==1 ? 3 : 4), this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());}
			// Eastward
			if (this.coordBaseMode!=3) {StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode==2 ? 3 : 4), 3, this.getComponentType());}
			// Southward
			if (this.coordBaseMode!=0) {StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode==1 ? 3 : 4), this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());}
			// Westward
			if (this.coordBaseMode!=1) {StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode==2 ? 3 : 4), 1, this.getComponentType());}
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
        	
			
        	// Set Grass blocks
        	for (int[] grass_uw : new int[][]{
        		{1, 2}, 
        		{2, 1}, {2, 7}, 
        		{3, 4}, {3, 5}, 
        		{4, 9}, 
        		{5, 9}, 
        		{7, 0}, 
        		{8, 0}, {8, 9}, 
        		{9, 7}, 
        		{10, 3}, 
        	})
        	{
        		this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, grass_uw[0], 0, grass_uw[1], structureBB);
        	}
			
        	// Set unkempt grass
            for (int[] uvwg : new int[][]{ // g is grass type
            	{8,GROUND_LEVEL,0, 0},  
            	{10,GROUND_LEVEL,3, 0},
            	{7,GROUND_LEVEL,0, 1},
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
        	
            
            // Statue
            
            // Terracotta foundation
        	for (int[] uuvvww : new int[][]{
        		{4,0,3, 7,2,6}, 
        		})
            {
        		// Orange
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor5 : 1),
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor5 : 1), 
        				false);
            }
            
        	// Polished diorite stairs
        	Block biomePolishedDioriteStairsBlock = ModObjects.chooseModPolishedDioriteStairsBlock(); 
        	if (biomePolishedDioriteStairsBlock==null) // Try regular diorite stairs
        	{
        		biomePolishedDioriteStairsBlock = ModObjects.chooseModDioriteStairsBlock();
        		if (biomePolishedDioriteStairsBlock==null) {biomePolishedDioriteStairsBlock = Blocks.stone_brick_stairs;} // Set to stone brick stairs
        	}
        	biomePolishedDioriteStairsBlock = (Block) StructureVillageVN.getBiomeSpecificBlockObject(biomePolishedDioriteStairsBlock, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
        		// Base
        		{4,3,3, 3}, {5,3,3, 3}, {6,3,3, 3}, 
        		{7,3,3, 1}, {7,3,4, 1}, {7,3,5, 1}, 
        		{7,3,6, 2}, {6,3,6, 2}, {5,3,6, 2}, 
        		{4,3,6, 0}, {4,3,5, 0}, {4,3,4, 0}, 
        		// Boots
        		{5,4,4, 3}, {6,4,5, 3}, 
        		// Hat
        		{5,9,4, 3}, {6,9,4, 1}, {6,9,5, 2}, {5,9,5, 0}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomePolishedDioriteStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
            }
        	
        	// Polished diorite blocks
        	blockObject = ModObjects.chooseModPolishedDioriteObject();
        	if (blockObject==null) // Try regular diorite block
        	{
        		blockObject = ModObjects.chooseModDioriteObject();
        		if (blockObject==null) {blockObject = new Object[]{Blocks.stonebrick, 0};} // Set to stone brick
        	}; 
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs); Block biomePolishedDioriteBlock = (Block)blockObject[0]; int biomePolishedDioriteMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		// Base
        		{5,3,4, 6,3,5}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomePolishedDioriteBlock, biomePolishedDioriteMeta,
        				biomePolishedDioriteBlock, biomePolishedDioriteMeta, 
        				false);	
            }
            
        	// Regular diorite stairs
        	Block biomeDioriteStairsBlock = ModObjects.chooseModDioriteStairsBlock(); 
        	if (biomeDioriteStairsBlock==null) {biomeDioriteStairsBlock = Blocks.stone_stairs;} // Set to cobblestone stairs
        	biomeDioriteStairsBlock = (Block) StructureVillageVN.getBiomeSpecificBlockObject(biomeDioriteStairsBlock, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
        		// Leg
        		{5,5,5, 2+4}, {5,5,4, 3+4}, {5,6,4, 3},
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeDioriteStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
            }
        	
        	// Diorite blocks
        	blockObject = ModObjects.chooseModDioriteObject();
        	if (blockObject==null) {blockObject = new Object[]{Blocks.cobblestone, 0};} // Set to stone brick
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs); Block biomeDioriteBlock = (Block)blockObject[0]; int biomeDioriteMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		// Torso
        		{5,6,5, 6,7,5}, 
        		// Leg
        		{6,5,5, 6,5,5},
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeDioriteBlock, biomeDioriteMeta,
        				biomeDioriteBlock, biomeDioriteMeta, 
        				false);	
            }
        	
        	// Diorite wall
        	blockObject = ModObjects.chooseModDioriteWallObject();
        	if (blockObject==null) {blockObject = new Object[]{Blocks.cobblestone_wall, 0};}
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs); Block biomeDioriteWallBlock = (Block)blockObject[0]; int biomeDioriteWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		// Staff arm
        		{4,6,4, 4,6,5}, {4,7,5, 4,7,5}, 
        		// Flower arm
        		{7,7,3, 7,7,5}, 
        		// Head
        		{5,8,4, 6,8,5}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeDioriteWallBlock, biomeDioriteWallMeta,
        				biomeDioriteWallBlock, biomeDioriteWallMeta, 
        				false);	
            }
            
            // Fences
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
            for (int[] uuvvww : new int[][]{
            	// Staff
            	{4,4,4, 4,5,4}, {4,7,4, 4,7,4}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
        	
            
            // Campfire
        	blockObject = ModObjects.chooseModCampfireBlock(random.nextInt(4), this.coordBaseMode); Block campfireBlock = (Block) blockObject[0]; int campfireMeta = (Integer) blockObject[1];
            for(int[] uvw : new int[][]{
            	{4,8,4}, 
            	})
            {
        		this.placeBlockAtCurrentPosition(world, campfireBlock, campfireMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            // Flower Pot
    		
            int u=7; int v=7; int w=3;
            int x = this.getXWithOffset(u, w);
            int y = this.getYWithOffset(v+GROUND_LEVEL);
            int z = this.getZWithOffset(u, w);
            
            Object[] cornflowerObject = ModObjects.chooseModCornflower(); Object[] lilyOfTheValleyObject = ModObjects.chooseModLilyOfTheValley();
    		int randomPottedPlant = random.nextInt(10 + (cornflowerObject!=null && lilyOfTheValleyObject!=null ? 2:0))-1;
    		if (randomPottedPlant==-1) {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.yellow_flower, 0);} // Dandelion specifically
    		else if (randomPottedPlant==9 && cornflowerObject!=null) {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, (Block) cornflowerObject[0], (Integer) cornflowerObject[1]);}
    		else if (randomPottedPlant==10 && lilyOfTheValleyObject!=null) {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, (Block) lilyOfTheValleyObject[0], (Integer) lilyOfTheValleyObject[1]);}
    		else {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.red_flower, randomPottedPlant);}          // Every other type of flower
        	
            
            // Decor
            int[][] decorUVW = new int[][]{
            	{0,1,0}, 
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
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wall_sign, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWallSignBlock = (Block)blockObject[0];
    		if (GeneralConfig.nameSign)
            {
    			// Village sign
    			
            	int signU = 6;
    			int signV = 2;
    			int signW = 2;
    			int signO = 2;
                int signX = this.getXWithOffset(signU, signW);
                int signY = this.getYWithOffset(signV);
                int signZ = this.getZWithOffset(signU, signW);
                boolean hanging=true;
                
        		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);
        		
    			world.setBlock(signX, signY, signZ, biomeWallSignBlock, StructureVillageVN.getSignRotationMeta(signO, this.coordBaseMode, hanging), 2); // 2 is "send change to clients without block update notification"
        		world.setTileEntity(signX, signY, signZ, signContents);
        		        		
        		
        		
        		// "Founder" sign
        		
        		signU = 5;
        		signX = this.getXWithOffset(signU, signW);
                signZ = this.getZWithOffset(signU, signW);
        		                
                signContents = new TileEntitySign();
        		
            	String topLine = "Founder:";
        		topLine = topLine.trim();
        		
        		String[] founderName = NameGenerator.newRandomName("villager", random);
        		
        		String founderPrefix = founderName[1];
        		String founderRoot = founderName[2];
        		String founderSuffix = founderName[3];
        		
        		if ( (founderPrefix.length() + 1 + founderRoot.length()) > 15 )
        		{
        			// Prefix+Root is too long, so move prefix to line 1
        			signContents.signText[0] = GeneralConfig.headerTags.trim() + topLine.trim();
        			signContents.signText[1] = founderPrefix.trim();
        			if ( (founderRoot.length() + 1 + founderSuffix.length()) > 15 )
        			{
        				// Root+Suffix is too long, so move suffix to line 3
        				signContents.signText[2] = founderRoot.trim();
        				signContents.signText[3] = founderSuffix.trim();
        			}
        			else
        			{
        				// Fit Root+Suffix onto line 2
        				signContents.signText[2] = (founderRoot+" "+founderSuffix).trim();
        			}
        		}
        		else if ( (founderPrefix.length() + 1 + founderRoot.length() + 1 + founderSuffix.length()) <= 15 )
        		{
        			// Whole name fits on one line! Put it all on line 2.
        			signContents.signText[1] = GeneralConfig.headerTags.trim() + topLine;
        			signContents.signText[2] = (founderPrefix+" "+founderRoot+" "+founderSuffix).trim();
        		}
        		else
        		{
        			// Only Prefix and Root can fit together on line 2.
        			signContents.signText[1] = GeneralConfig.headerTags.trim() + topLine.trim();
        			signContents.signText[2] = (founderPrefix+" "+founderRoot).trim();
        			signContents.signText[3] = founderSuffix.trim();
        		}
        		// If top line is blank, roll everything up one line:
        		if (topLine.equals(""))
        		{
        			for (int isign=0; isign <3; isign++)
        			{
        				signContents.signText[isign] = signContents.signText[isign+1];	
        			}
        			signContents.signText[3] = "";
        		}
                
    			world.setBlock(signX, signY, signZ, biomeWallSignBlock, StructureVillageVN.getSignRotationMeta(signO, this.coordBaseMode, hanging), 2); // 2 is "send change to clients without block update notification"
        		world.setTileEntity(signX, signY, signZ, signContents);
            }
        	
    		
			// Banner    		
    		if (GeneralConfig.villageBanners)
    		{
    			Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
    			
        		if (testForBanner!=null)
    			{
        			for(int capeU : new int[]{5, 6})
                    {
                        int bannerU = capeU;
            			int bannerV = 6;
            			int bannerW = 6;
            			int bannerO = 0; // Facing away from you
            			boolean hanging=true;
            			
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
    		}
    		
    		// Villagers
            if (!this.villagersGenerated)
            {
            	this.villagersGenerated=true;
            	
            	if (VillageGeneratorConfigHandler.spawnVillagersInTownCenters)
            	{
	        		for (int[] ia : new int[][]{
	        			{2, 1, 3, -1, 0},
	        			{2, 1, 3, -1, 0},
	        			{5, 1, 8, -1, 0},
	        			{9, 1, 5, -1, 0},
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
	
    
	// --- Jungle Tree --- //
	// designed by Roadhog360
    
    public static class JungleTree extends StartVN
    {
        // Make foundation with blanks as empty air, F as foundation spaces, and P as path
        private static final String[] foundationPattern = new String[]{
            	"    PPP    ",
            	"  PPPPPPP  ",
            	" PPFFFFFPP ",
            	" PFFFFFFFP ",
            	"PPFFFFFFFPP",
            	"PPFFFFFFFPP",
            	"PPFFFFFFFPP",
            	" PFFFFFFFP ",
            	" PPFFFFFPP ",
            	" PPPPPPPPP ",
            	"    PPP    ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 11;
    	// Values for lining things up
    	public static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1 + 2 + 4 + 8; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
	    public JungleTree() {}
		
		public JungleTree(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, float villageSize)
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
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 4, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
			// Eastward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 4, 3, this.getComponentType());
			// Southward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 4, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
			// Westward
			StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 4, 1, this.getComponentType());
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
        	
            
            // Cobblestone stairs
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
        	for (int[] uuvvwwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
        		{4,1,2, 6,1,2, 3}, 
        		{7,1,2, 7,1,3, 1}, 
        		{8,1,3, 8,1,3, 3}, 
        		
        		{8,1,4, 8,1,6, 1}, 
        		{7,1,7, 8,1,7, 2}, 
        		{7,1,8, 7,1,8, 1}, 
        		
        		{4,1,8, 6,1,8, 2}, 
        		{3,1,7, 3,1,8, 0}, 
        		{2,1,7, 2,1,7, 2}, 
        		
        		{2,1,4, 2,1,6, 0}, 
        		{2,1,3, 3,1,3, 3}, 
        		{3,1,2, 3,1,2, 0}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], uuvvwwo[3], uuvvwwo[4], uuvvwwo[5], biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uuvvwwo[6]%4)+(uuvvwwo[6]/4)*4, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uuvvwwo[6]%4)+(uuvvwwo[6]/4)*4, false);	
            }
        	
			
        	// Grass blocks
        	for (int[] grass_uw : new int[][]{
        		{3, 1, 4}, {3, 1, 5}, 
        		{4, 1, 4}, {4, 1, 6}, {4, 1, 7}, 
        		{5, 1, 3}, {5, 1, 5}, {5, 1, 7}, 
        		{6, 1, 3}, {6, 1, 6}, 
        		{7, 1, 5}, 
        	})
        	{
        		this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, grass_uw[0], grass_uw[1], grass_uw[2], structureBB);
        	}
        	
        	
        	// Podzol
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 2, this.materialType, this.biome, this.disallowModSubs); Block biomePodzolBlock = (Block)blockObject[0]; int biomePodzolMeta = (Integer)blockObject[1];
        	for (int[] grass_uw : new int[][]{
        		{3, 1, 6}, 
        		{4, 1, 3}, {4, 1, 5}, 
        		{5, 1, 4}, {5, 1, 6}, 
        		{6, 1, 4}, {6, 1, 5}, {6, 1, 7}, 
        		{7, 1, 4}, {7, 1, 6}, 
        	})
        	{
        		this.placeBlockAtCurrentPosition(world, biomePodzolBlock, biomePodzolMeta, grass_uw[0], grass_uw[1], grass_uw[2], structureBB);
        	}
        	
        	
        	// Vertical jungle logs
            for (int[] uw : new int[][]{
            	{5,2,5, 5,9,5}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uw[0], uw[1], uw[2], uw[3], uw[4], uw[5], Blocks.log, 3, Blocks.log, 3, false);
            }
            
            
            // Jungle Leaves
        	for (int[] uuvvww : new int[][]{
        		// At the base
        		{3,2,4, 3,2,4}, 
        		{3,2,6, 3,2,6}, 
        		{4,2,5, 4,2,7}, {4,3,6, 4,3,6}, 
        		{5,2,3, 5,2,4}, {5,3,4, 5,3,4}, {5,2,6, 5,2,6}, 
        		{6,2,4, 6,2,5}, 
        		{7,2,5, 7,2,5}, 
        		// As part of the tree itself
        		{3,7,3, 4,8,7}, 
        		{5,7,3, 5,8,4}, {5,7,6, 5,8,7},
        		{6,7,3, 6,8,3}, {6,7,4, 7,8,7},
        		{4,9,5, 4,10,5}, {5,9,4, 5,10,4}, {6,9,5, 6,10,5}, {5,9,6, 5,10,6},
        		{5,10,5, 5,10,5}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.leaves, 3,
        				Blocks.leaves, 3, 
        				false);
            }
            
        	
        	// Vines
        	if (this.materialType!=FunctionsVN.MaterialType.SNOW)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward, 3:leftward
            		// Left side
        			{4,3,5, 3}, 
        			// Back side
        			{5,3,6, 0}, {5,5,6, 0}, {5,6,6, 0}, 
        			// Right side
        			{6,5,5, 1},
        			// Front side
        			{5,5,4, 2}, {5,6,4, 2}, 
            		})
                {
            		this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);	
                }
        	}
            
        	
        	// Flowers
        	for (int[] uvw : new int[][]{
    			{3,2,5},
    			{5,2,7}, 
    			{6,2,6}, 
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
        	
        	
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, materialType, biome, disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	
            // Sign
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.standing_sign, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStandingSignBlock = (Block)blockObject[0];
            if (GeneralConfig.nameSign)
            {
            	int signU = 2;
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
                    int bannerU = 8;
        			int bannerV = 1;
        			int bannerW = 2;
        			int bannerO = 8; // Facing toward you
        			boolean hanging=false;
        			
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
	        			{1, 1, 5, -1, 0},
	        			{8, 1, 8, -1, 0},
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
    
    
	// --- Jungle Garden --- //
    // designed by AstroTibs
    
    public static class JungleGarden extends StartVN
    {
        // Make foundation with blanks as empty air, F as foundation spaces, and P as path
        private static final String[] foundationPattern = new String[]{
            	"F     F     F     ",
            	" FFFFFFFFFFFFFFFFF",
            	" FFPFPFPFPFPFPFPPF",
            	" FPPPPPPPPPPPPPPPF",
            	" FFPFPFPFPFPFPFPPF",
            	"FFPPPPPPPPPPPPPPPF",
            	" FFPFPFFFFFFFFFPPP",
            	"PPPPPPFFFFFFFFFPPP",
            	"PPPPPPFFFFFFFFFPPP",
            	"PPPPPPFFFFFFFFFPFF",
            	"FFFFPPFFFFFFFFFPF ",
            	"   FPPFFFFFFFFFPF ",
            	" F FPPFFFFFFFFFPF ",
            	"   FPPFFFFFFFFFPF ",
            	"   FPPFFFFFFFFFPF ",
            	"   FPPPPPPPPPPPPF ",
            	"   FPPPFFFFFFPPPF ",
            	"   FPPPFFFFFFPPPF ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 5;
    	// Values for lining things up
    	public static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1 + 2 + 8; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;

    	
	    public JungleGarden() {}
		
		public JungleGarden(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, float villageSize)
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
			if (this.coordBaseMode!=2)
			{
				StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode==0 ? 4 : this.coordBaseMode==1 ? 7 : this.coordBaseMode==3 ? 8 : 0), this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
				// Add a second road
				if (this.coordBaseMode==0) {StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 13, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());}
			}
			// Eastward
			if (this.coordBaseMode!=3)
			{
				StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode==0 ? 9 : this.coordBaseMode==1 ? 4 : this.coordBaseMode==2 ? 6 : 0), 3, this.getComponentType());
				// Add a second road
				if (this.coordBaseMode==1) {StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 13, 3, this.getComponentType());}
			}
			// Southward
			if (this.coordBaseMode!=0)
			{
				StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode==1 ? 6 : this.coordBaseMode==2 ? 4 : this.coordBaseMode==3 ? 9 : 0), this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
				// Add a second road
				if (this.coordBaseMode==2) {StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 13, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());}
			}
			// Westward
			if (this.coordBaseMode!=1)
			{
				StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode==0 ? 8 : this.coordBaseMode==2 ? 7 : this.coordBaseMode==3 ? 4 : 0), 1, this.getComponentType());
				// Add a second road
				if (this.coordBaseMode==3) {StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 13, 1, this.getComponentType());}
			}
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
        	
			
        	// Set Grass blocks
        	for (int[] grass_uw : new int[][]{
        		{0, 7}, {0, 12}, {0, 17}, 
        		{1, 7}, {1, 11}, {1, 12}, {1, 13}, {1, 14}, {1, 15}, {1, 16}, 
        		{2, 7}, {2, 11}, {2, 13}, {2, 15}, {2, 16}, 
        		{3, 0}, {3, 1}, {3, 2}, {3, 3}, {3, 4}, {3, 5}, {3, 6}, {3, 7}, {3, 16}, 
        		{4, 11}, {4, 13}, {4, 15}, {4, 16}, 
        		{5, 16}, 
        		{6, 13}, {6, 15}, {6, 16}, {6, 17}, 
        		{7, 0}, {7, 1}, {7, 16}, 
        		{8, 0}, {8, 1}, {8, 13}, {8, 15}, {8, 16}, 
        		{9, 0}, {9, 1}, {9, 16}, 
        		{10, 0}, {10, 1}, {10, 13}, {10, 15}, {10, 16}, 
        		{11, 0}, {11, 1}, {11, 16}, 
        		{12, 0}, {12, 1}, {12, 13}, {12, 15}, {12, 16}, {12, 17}, 
        		{13, 16}, 
        		{14, 13}, {14, 15}, {14, 16}, 
        		{15, 16}, 
        		{16, 0}, {16, 1}, {16, 2}, {16, 3}, {16, 4}, {16, 5}, {16, 6}, {16, 7}, {16, 8}, {16, 16}, 
        		{17, 8}, {17, 12}, {17, 13}, {17, 14}, {17, 15}, {17, 16}, 
        	})
        	{
        		this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, grass_uw[0], 0, grass_uw[1], structureBB);
        	}
        	
        	
        	// Leaves
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.leaves, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLeafBlock = (Block)blockObject[0]; int biomeLeafMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		// Front left shrubs
        		{2,1,7, 3,1,7}, {3,1,1, 3,1,6}, 
        		// Front shrubs
        		{7,1,1, 12,1,1},
        		// Front right
        		{16,1,1, 16,1,8}, {16,1,1, 17,1,1}, 
        		// Back bushes
        		{1,1,12, 1,1,15}, 
        		{2,1,16, 16,1,16}, 
        		{17,1,12, 17,1,15}, 
        		// Bushes around the fountain
        		{6,1,3, 6,1,3}, {6,1,11, 6,1,11}, {14,1,3, 14,1,3}, {14,1,11, 14,1,11}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeLeafBlock, biomeLeafMeta,
        				biomeLeafBlock, biomeLeafMeta, 
        				false);
            }
        	
        	
        	// Polished diorite blocks
        	blockObject = ModObjects.chooseModPolishedDioriteObject();
        	if (blockObject==null) // Try regular diorite block
        	{
        		blockObject = ModObjects.chooseModDioriteObject();
        		if (blockObject==null) {blockObject = new Object[]{Blocks.stonebrick, 0};} // Set to stone brick
        	}; 
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs); Block biomePolishedDioriteBlock = (Block)blockObject[0]; int biomePolishedDioriteMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		// Base
        		{6,0,3, 6,0,3}, {6,0,11, 6,0,11}, {14,0,3, 14,0,3}, {14,0,11, 14,0,11}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomePolishedDioriteBlock, biomePolishedDioriteMeta,
        				biomePolishedDioriteBlock, biomePolishedDioriteMeta, 
        				false);	
            }
            
        	
        	// Diorite wall
        	blockObject = ModObjects.chooseModDioriteWallObject();
        	if (blockObject==null) {blockObject = new Object[]{Blocks.cobblestone_wall, 0};}; // Try cobblestone wall
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs); Block biomeDioriteWallBlock = (Block)blockObject[0]; int biomeDioriteWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		// Pavilion posts
        		{2,1,15, 2,2,15}, {4,1,15, 4,2,15}, {6,1,15, 6,2,15}, {8,1,15, 8,2,15}, {10,1,15, 10,2,15}, {12,1,15, 12,2,15}, {14,1,15, 14,2,15}, 
        		{2,1,13, 2,2,13}, {4,1,13, 4,2,13}, {6,1,13, 6,2,13}, {8,1,13, 8,2,13}, {10,1,13, 10,2,13}, {12,1,13, 12,2,13}, {14,1,13, 14,2,13}, 
        		{2,1,11, 2,2,11}, {4,1,11, 4,2,11}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeDioriteWallBlock, biomeDioriteWallMeta,
        				biomeDioriteWallBlock, biomeDioriteWallMeta, 
        				false);	
            }
        	
        	
        	// Clear out basin
        	this.fillWithAir(world, structureBB, 7,0,4, 13,0,10);
        	
        	
        	// Diorite blocks
        	blockObject = ModObjects.chooseModDioriteObject();
        	if (blockObject==null) {blockObject = new Object[]{Blocks.cobblestone, 0};} // Set to stone brick
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs); Block biomeDioriteBlock = (Block)blockObject[0]; int biomeDioriteMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		// Pavilion toppers
        		{2,3,15, 2,3,15}, {4,3,15, 4,3,15}, {6,3,15, 6,3,15}, {8,3,15, 8,3,15}, {10,3,15, 10,3,15}, {12,3,15, 12,3,15}, {14,3,15, 14,3,15}, 
        		{2,3,13, 2,3,13}, {4,3,13, 4,3,13}, {6,3,13, 6,3,13}, {8,3,13, 8,3,13}, {10,3,13, 10,3,13}, {12,3,13, 12,3,13}, {14,3,13, 14,3,13}, 
        		{2,3,11, 2,3,11}, {4,3,11, 4,3,11}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeDioriteBlock, biomeDioriteMeta,
        				biomeDioriteBlock, biomeDioriteMeta, 
        				false);	
            }
        	
        	
        	// Diorite slabs upper
        	blockObject = ModObjects.chooseModDioriteSlabBlock(true);
        	if (ModObjects.chooseModDioriteSlabBlock(true)==null) {blockObject = new Object[]{Blocks.stone_slab, 11};} // Set to cobblestone slab
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs); Block biomeDioriteSlabUpperBlock = (Block)blockObject[0]; int biomeDioriteSlabUpperMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		{2,3,12}, {2,3,14}, 
        		{3,3,11}, {3,3,12}, {3,3,13}, {3,3,14}, {3,3,15}, 
        		{4,3,12}, {4,3,14}, 
        		{5,3,13}, {5,3,14}, {5,3,15}, 
        		{6,3,14}, 
        		{7,3,13}, {7,3,14}, {7,3,15}, 
        		{8,3,14}, 
        		{9,3,13}, {9,3,14}, {9,3,15}, 
        		{10,3,14}, 
        		{11,3,13}, {11,3,14}, {11,3,15}, 
        		{12,3,14}, 
        		{13,3,13}, {13,3,14}, {13,3,15}, 
        		{14,3,14}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeDioriteSlabUpperBlock, biomeDioriteSlabUpperMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Polished diorite slabs lower
        	blockObject = ModObjects.chooseModPolishedDioriteSlabBlock(false);
        	if (blockObject==null) // Set to regular diorite slab
        	{
        		blockObject = ModObjects.chooseModDioriteSlabBlock(true);
            	if (ModObjects.chooseModDioriteSlabBlock(true)==null) {blockObject = new Object[]{Blocks.stone_slab, 0};} // Set to polished stone slab
        	}
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs); Block biomePolishedDioriteSlabLowerBlock = (Block)blockObject[0]; int biomePolishedDioriteSlabLowerMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
            	{2,4,11, 4,4,12}, {2,4,13, 14,4,15}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePolishedDioriteSlabLowerBlock, biomePolishedDioriteSlabLowerMeta, biomePolishedDioriteSlabLowerBlock, biomePolishedDioriteSlabLowerMeta, false);
            }
        	
        	
        	// Regular diorite stairs
        	Block biomeDioriteStairsBlock = ModObjects.chooseModDioriteStairsBlock(); 
        	if (biomeDioriteStairsBlock==null) {biomeDioriteStairsBlock = Blocks.stone_stairs;} // Set to cobblestone stairs
        	biomeDioriteStairsBlock = (Block) StructureVillageVN.getBiomeSpecificBlockObject(biomeDioriteStairsBlock, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
        		// Fountain outer lining
        		{7,0,3, 2}, {8,0,3, 2}, {9,0,3, 2}, {10,0,3, 2}, {11,0,3, 2}, {12,0,3, 2}, {13,0,3, 2}, 
        		{6,0,4, 1}, {6,0,5, 1}, {6,0,6, 1}, {6,0,7, 1}, {6,0,8, 1}, {6,0,9, 1}, {6,0,10, 1}, 
        		{7,0,11, 3}, {8,0,11, 3}, {9,0,11, 3}, {10,0,11, 3}, {11,0,11, 3}, {12,0,11, 3}, {13,0,11, 3}, 
        		{14,0,4, 0}, {14,0,5, 0}, {14,0,6, 0}, {14,0,7, 0}, {14,0,8, 0}, {14,0,9, 0}, {14,0,10, 0}, 
        		// Fountain inner lining
        		{8,0,5, 3}, {9,0,5, 3}, {10,0,5, 3}, {11,0,5, 3}, 
        		{12,0,5, 1}, {12,0,6, 1}, {12,0,7, 1}, {12,0,8, 1}, 
        		{9,0,9, 2}, {10,0,9, 2}, {11,0,9, 2}, {12,0,9, 2}, 
        		{8,0,6, 0}, {8,0,7, 0}, {8,0,8, 0}, {8,0,9, 0}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeDioriteStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
            }
            
        	
            // Sand
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.sand, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
            for (int[] uuvvww : new int[][]{
            	{7,-1,4, 13,-1,4}, {7,-1,5, 7,-1,9}, {7,-1,10, 13,-1,10}, {13,-1,5, 13,-1,9}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
            
        	
            // Stone brick
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickBlock = (Block)blockObject[0]; int biomeStoneBrickMeta = (Integer)blockObject[1];
            for (int[] uuvvww : new int[][]{
            	// Basin bottom
            	{8,-1,5, 12,-1,9}, 
            	// Fountain
            	{10,0,7, 10,0,7}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickBlock, biomeStoneBrickMeta, biomeStoneBrickBlock, biomeStoneBrickMeta, false);
            }
            
            
            // Chiseled Stone brick
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeChiseledStoneBrickBlock = (Block)blockObject[0]; int biomeChiseledStoneBrickMeta = (Integer)blockObject[1];
            for (int[] uuvvww : new int[][]{
            	// Fountain head
            	{10,1,7, 10,1,7}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeChiseledStoneBrickBlock, biomeChiseledStoneBrickMeta, biomeChiseledStoneBrickBlock, biomeChiseledStoneBrickMeta, false);
            }
            
            
            // Water
            this.placeBlockAtCurrentPosition(world, Blocks.flowing_water, 0, 10, 2, 7, structureBB);
            
            
        	// Trees
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.sapling, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeSaplingBlock = (Block)blockObject[0]; int biomeSaplingMeta = (Integer)blockObject[1];
        	for (int[] uvwss : new int[][]{ // u,v,w, ushift,wshift: Which adjacent spaces to use if this is a Dark Oak sapling
        		// Around pavilion
        		{0,1,12, -1,1},
        		{0,1,17, -1,1},
        		{6,1,17, -1,1},
        		{12,1,17, -1,1},
        		// In front of fountain
        		{8,1,0, -1,-1},
        		{11,1,0, 1,-1},
        		})
            {
        		Block dirtblock = world.getBlock(this.getXWithOffset(uvwss[0], uvwss[2]), this.getYWithOffset(uvwss[1]-1), this.getZWithOffset(uvwss[0], uvwss[2]));
        		Block saplingblock = world.getBlock(this.getXWithOffset(uvwss[0], uvwss[2]), this.getYWithOffset(uvwss[1]), this.getZWithOffset(uvwss[0], uvwss[2]));
        		
        		// Don't place if there's no dirt beneath to grow
        		if (dirtblock==null || (dirtblock != Blocks.dirt && dirtblock != Blocks.grass)) {continue;}
        		// Don't place if the sapling can't see the sky
        		if (!world.canBlockSeeTheSky(this.getXWithOffset(uvwss[0], uvwss[2]), this.getYWithOffset(uvwss[1]), this.getZWithOffset(uvwss[0], uvwss[2]))) {continue;}
        		// Dark oak version of the above
        		if (biomeSaplingMeta==5)
        		{
        			Block dirtblock1 = world.getBlock(this.getXWithOffset(uvwss[0]+uvwss[3], uvwss[2]), this.getYWithOffset(uvwss[1]-1), this.getZWithOffset(uvwss[0]+uvwss[3], uvwss[2]));
        			Block dirtblock2 = world.getBlock(this.getXWithOffset(uvwss[0], uvwss[2]+uvwss[4]), this.getYWithOffset(uvwss[1]-1), this.getZWithOffset(uvwss[0], uvwss[2]+uvwss[4]));
        			Block dirtblock3 = world.getBlock(this.getXWithOffset(uvwss[0]+uvwss[3], uvwss[2]+uvwss[4]), this.getYWithOffset(uvwss[1]-1), this.getZWithOffset(uvwss[0]+uvwss[3], uvwss[2]+uvwss[4]));
        			
        			if (dirtblock1==null || dirtblock2==null || dirtblock3==null || // Foundation blocks are null
        					// Foundation blocks can't support growing a tree
        					(dirtblock1 != Blocks.dirt && dirtblock1 != Blocks.grass)
        					|| (dirtblock2 != Blocks.dirt && dirtblock2 != Blocks.grass)
        					|| (dirtblock3 != Blocks.dirt && dirtblock3 != Blocks.grass)
        					// Foundation blocks can't see the sky
        					|| !world.canBlockSeeTheSky(this.getXWithOffset(uvwss[0]+uvwss[3], uvwss[2]), this.getYWithOffset(uvwss[1]), this.getZWithOffset(uvwss[0]+uvwss[3], uvwss[2]))
        					|| !world.canBlockSeeTheSky(this.getXWithOffset(uvwss[0], uvwss[2]+uvwss[4]), this.getYWithOffset(uvwss[1]), this.getZWithOffset(uvwss[0], uvwss[2]+uvwss[4]))
        					|| !world.canBlockSeeTheSky(this.getXWithOffset(uvwss[0]+uvwss[3], uvwss[2]+uvwss[4]), this.getYWithOffset(uvwss[1]), this.getZWithOffset(uvwss[0]+uvwss[3], uvwss[2]+uvwss[4]))
        					)
        			{
        				continue;
        			}
        		}
        		
        		
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
        	
        	
        	// Vines
        	if (this.materialType!=FunctionsVN.MaterialType.SNOW)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1: rightward, 2:backward, 3: leftward
            		// Left side
        			{1,2,13, 3}, {1,3,13, 3}, 
        			// Back side
        			{4,2,16, 0}, {4,3,16, 0}, 
        			{6,2,16, 0}, {6,3,16, 0}, 
        			{8,2,16, 0}, {8,3,16, 0}, 
        			{12,3,16, 0}, 
        			{12,3,16, 0},
            		})
                {
            		this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);	
                }
        	}
            
            
            // Decor
            int[][] decorUVW = new int[][]{
            	{1,1,5}, 
            	{3,1,18}, 
            	{9,1,18}, 
            	{15,1,18}, 
            	{18,1,5}, 
            	// Within the front face
            	{10, 1, -1},
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
            	
            	//LogHelper.info("Decor with horizIndex "+this.coordBaseMode+" spawned at: " + this.getXWithOffset(uvw[0], uvw[2]) + " " + (decorHeightY + this.boundingBox.minY) + " " + this.getZWithOffset(uvw[0], uvw[2]));
            	
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
            	int signU = 1;
    			int signV = 1;
    			int signW = 11;
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
                    int bannerU = 1;
        			int bannerV = 1;
        			int bannerW = 7;
        			int bannerO = 0;
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
    		}
			
    		
    		// Villagers
            if (!this.villagersGenerated)
            {
            	this.villagersGenerated=true;
            	
            	if (VillageGeneratorConfigHandler.spawnVillagersInTownCenters)
            	{
	        		for (int[] ia : new int[][]{
	        			{5, 1, 2, -1, 0}, 
	        			{7, 1, 14, -1, 0}, 
	        			{11, 1, 4, -1, 0}, 
	        			{15, 1, 10, -1, 0}, 
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
    
    
	// --- Jungle Villa --- //
    // designed by AstroTibs
    
    public static class JungleVilla extends StartVN
    {
        // Make foundation with blanks as empty air, F as foundation spaces, and P as path
        private static final String[] foundationPattern = new String[]{
            	"                ",
            	"FFFFFFFFFFFFFFF ",
            	"FFFFFFFFFFFFFFF ",
            	"FFFFFFFFFFFFFFF ",
            	"FFFFFFFFFFFFFFF ",
            	"FFFFFFFFFFFFFFF ",
            	"FFFFFFFFFFFFFFF ",
            	"FFFFFFFFFFFFFFF ",
            	"FFFFFFFFFFFFFFF ",
            	"FFFFFFFFFFFFFFF ",
            	"FFFFFFFFFFFFFFF ",
            	"PPPPFFFFFFFFFFF ",
            	"PPPPPFFFFFFFFFF ",
            	"PPPPPPPFFFFFFFF ",
            	"  PPPPPFFFFFFFF ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 10;
    	// Values for lining things up
    	public static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1 + 2; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 8;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 10;

    	
	    public JungleVilla() {}
		
		public JungleVilla(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, float villageSize)
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
			if (this.coordBaseMode!=2) {StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (this.coordBaseMode==0 ? 3 : this.coordBaseMode==1 ? 11 : 1), this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());}
			// Eastward
			if (this.coordBaseMode==1) {StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + (3), 3, this.getComponentType());}
			// Southward
			if (this.coordBaseMode==2) {StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + (3), this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());}
			// Westward
			if (this.coordBaseMode!=1) {StructureVillageVN.generateAndAddRoadPiece((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + (this.coordBaseMode==0 ? 1 : this.coordBaseMode==2 ? 11 : 3), 1, this.getComponentType());}
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
        	
			
        	// Set Grass blocks
        	for (int[] grass_uw : new int[][]{
        		{0, 4}, {1, 4}, {2, 4}, {3, 4}, 
        	})
        	{
        		this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, grass_uw[0], 0, grass_uw[1], structureBB);
        	}
        	
        	
        	// Cobblestone
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, materialType, biome, disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
            	// Ground-level foundation
            	{0,1,5, 4,1,13}, 
            	{5,1,4, 5,1,13}, 
            	{6,1,3, 6,1,13}, 
            	{7,1,0, 7,1,13}, 
            	{8,1,0, 14,1,6}, 
            	{8,1,9, 12,1,9}, 
            	{8,1,12, 12,1,13}, 
            	{10,1,7, 10,1,8}, 
            	{10,1,10, 10,1,11}, 
            	{13,1,0, 14,1,13}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);
            }
        	
            
        	// Cobbestone stairs
            blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneStairsBlock = (Block)blockObject[0];
        	for (int[] uuvvwwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward; +4:inverted
        		// Front stairs
        		{4,1,4, 0},
        		{4,1,3, 3}, {5,1,3, 3}, 
        		{5,1,2, 0},
        		{6,1,2, 3}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeStoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uuvvwwo[3]%4)+(uuvvwwo[3]/4)*4, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], structureBB);
            }
        	
        	
        	// Cobblestone wall
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
            	{0,2,5, 4,2,5}, 
            	{0,2,6, 0,2,13}, 
            	{1,2,13, 1,2,13}, 
            	{14,2,0, 14,2,2}, 
            	{7,2,0, 13,2,0}, 
            	{7,2,1, 7,2,2}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, false);
            }
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uuvvww : new int[][]{
            	{10,2,1, 10,3,1}, 
            	{12,2,1, 12,3,1}, 
            	{14,3,1, 14,3,1}, 
            	// Tables
            	{13,5,12, 13,5,12}, {13,5,6, 13,5,6}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
        	
        	
            // Glazed terracotta
        	Object[] tryGlazedTerracotta;
        	for (int[] uvwoc : new int[][]{ // u,v,w, orientation, dye color
        		// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
        		
        		// Front left
        		{8,1,7, 1, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{9,1,7, 0, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{8,1,8, 2, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{9,1,8, 3, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		
        		// Front right
        		{11,1,7, 0, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{12,1,7, 3, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{11,1,8, 1, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{12,1,8, 2, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		
        		// Back left
        		{8,1,10, 2, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{9,1,10, 1, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{8,1,11, 0, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{9,1,11, 3, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		
        		// Back right
        		{11,1,10, 3, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{12,1,10, 2, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{11,1,11, 0, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{12,1,11, 1, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
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
        	
        	
            // Terracotta
        	for (int[] uuvvww : new int[][]{
        		// Lower floor
        		{2,2,9, 2,2,13}, 
        		{2,2,9, 2,3,9}, {2,2,11, 2,3,11}, {2,2,13, 2,3,13}, 
        		{2,4,9, 2,4,13}, 
        		{3,4,9, 6,4,9}, {4,2,9, 4,3,9}, {6,2,9, 6,3,9}, 
        		{7,4,6, 7,4,8}, {7,2,8, 7,3,8}, {7,2,6, 7,3,6}, 
        		{8,4,6, 9,4,6}, {9,2,6, 9,3,6}, 
        		{10,4,1, 10,4,5}, {10,2,5, 10,3,5}, {10,2,4, 10,2,4}, {10,2,3, 10,3,3}, 
        		{10,4,1, 14,4,3}, {11,2,3, 11,3,3}, {13,2,3, 14,3,3}, 
        		{14,2,4, 14,2,13}, {14,3,5, 14,3,5}, {14,3,7, 14,3,7}, {14,3,9, 14,3,9}, {14,3,11, 14,3,11}, {14,3,13, 14,3,13}, {14,4,4, 14,4,13}, 
        		{3,2,13, 13,2,13}, {4,3,13, 4,3,13}, {6,3,13, 6,3,13}, {8,3,13, 8,3,13}, {10,3,13, 10,3,13}, {12,3,13, 12,3,13}, {3,4,13, 13,4,13},
        		// Upper floor
        		{2,6,9, 2,6,9}, {2,6,11, 2,6,11}, {2,6,13, 2,6,13}, {2,7,9, 2,7,13}, 
        		{4,6,9, 4,6,9}, {6,6,9, 6,6,9}, {8,6,9, 10,6,9}, {3,7,9, 10,7,9}, 
        		{10,6,5, 10,7,8}, {10,7,4, 10,7,4}, {10,6,3, 10,7,3}, 
        		{11,7,3, 11,7,3}, {12,6,3, 12,8,3}, {13,7,3, 13,7,3}, {14,6,3, 14,7,3}, 
        		{14,6,5, 14,7,5}, {14,6,7, 14,7,7}, {14,6,9, 14,7,9}, {14,6,11, 14,7,11}, {14,6,13, 14,7,13}, 
        		{14,7,4, 14,7,4}, {14,7,6, 14,7,6}, {14,7,8, 14,7,8}, {14,7,10, 14,7,10}, {14,7,12, 14,7,12}, 
        		{12,6,13, 12,7,13}, {10,6,13, 10,7,13}, {8,6,13, 8,7,13}, {6,6,13, 6,7,13}, {4,6,13, 4,7,13}, 
        		{13,7,13, 13,7,13}, {11,7,13, 11,7,13}, {9,7,13, 9,7,13}, {7,7,13, 7,7,13}, {5,7,13, 5,7,13}, {3,7,13, 3,7,13}, 
        		})
            {
        		// White
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor : 0),
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor : 0), 
        				false);
            }
            
        	
        	// Vertical logs
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, materialType, biome, disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		// Downstairs supports
        		{7,2,9, 7,4,9}, {10,2,6, 10,4,6}, 
        		// Floor separator
        		{2,5,9, 10,5,9}, {10,5,3, 10,5,8}, {12,5,3, 14,5,3}, {14,5,4, 14,5,13}, {2,5,13, 13,5,13}, {2,5,10, 2,5,12}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeLogVertBlock, biomeLogVertMeta,
        				biomeLogVertBlock, biomeLogVertMeta, 
        				false);
            }
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	// Front awning
            	{7,5,6, 7,5,8}, {8,5,6, 9,5,6}, {9,6,8, 9,6,8}, 
            	// Roof
            	{11,8,3, 11,8,3}, {13,8,3, 13,8,3}, 
            	{11,8,5, 13,8,5}, {11,8,7, 13,8,7}, {11,8,9, 13,8,9}, {12,8,11, 13,8,11}, {12,8,12, 12,8,12}, 
            	{10,8,10, 10,8,12}, {8,8,10, 8,8,12}, {6,8,9, 6,8,13}, {4,8,10, 4,8,12}, 
            	// Downstairs
            	{3,2,12, 3,2,12}, // Desk with flower pot
            	{8,2,12, 8,2,12}, // Under the stairs
            	{10,2,12, 10,2,12}, {13,2,9, 13,2,9}, // Couch ends
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
        	
        	
            // Wood stairs
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
        	for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
        		// Couch
        		{11,2,12, 3}, {12,2,12, 3}, {13,2,12, 3}, {13,2,11, 0}, {13,2,10, 0}, 
        		// Stairwell
        		{7,2,12, 0}, {8,3,12, 0}, {9,4,12, 0}, 
        		// Table
        		{13,5,10, 3+4}, {13,5,8, 2+4}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
            }
            
            
            // Wooden slabs (Top)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	// Roof trim
            	{9,7,2, 9,7,8}, {1,7,8, 8,7,8}, {1,7,9, 1,7,14}, {2,7,14, 15,7,14}, {15,7,2, 15,7,13}, 
            	// Roof panels
            	{11,8,2, 11,8,2}, {13,8,2, 13,8,2}, 
            	{11,8,4, 13,8,4}, {11,8,6, 13,8,6}, {11,8,8, 13,8,8}, {11,8,10, 13,8,10}, {11,8,11, 11,8,12}, {13,8,12, 13,8,12}, 
            	{9,8,10, 9,8,12}, {7,8,9, 7,8,13}, {5,8,9, 5,8,13}, {3,8,10, 3,8,12},
            	// floor separator
            	{3,4,10, 4,4,12}, {5,4,10, 9,4,11}, {8,4,9, 9,4,9}, {10,4,7, 10,4,12}, {10,4,7, 10,4,12}, {11,4,4, 13,4,12}, 
            	// Table
            	{13,5,9, 13,5,9}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
            }
            
            
            // Wooden slabs (Bottom)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	// Awning
            	{6,5,5, 6,5,8}, {7,5,5, 9,5,5}, {8,6,7, 8,6,8}, {9,6,7, 9,6,7}, 
            	// Roof
            	{10,8,2, 10,8,9}, {8,8,9, 9,8,9}, {2,8,9, 4,8,9}, {2,8,10, 2,8,13}, {3,8,13, 4,8,13}, {8,8,13, 14,8,13}, {14,8,2, 14,8,12}, 
            	{12,9,2, 12,9,11}, {4,9,11, 11,9,11}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
            }
        	
        	
            // Wooden pressure plate
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_pressure_plate, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodPressurePlateBlock = (Block)blockObject[0]; int biomeWoodPressurePlateMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		{13,6,12}, 
        		{13,6,6}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeWoodPressurePlateBlock, biomeWoodPressurePlateMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Bookshelves
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.bookshelf, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeBookshelfBlock = (Block)blockObject[0]; int biomeBookshelfMeta = (Integer)blockObject[1];
            for (int[] uuvvww : new int[][]{
        		{10,2,9, 10,4,9}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeBookshelfBlock, biomeBookshelfMeta, biomeBookshelfBlock, biomeBookshelfMeta, false);
            }
        	
            
            // Iron bars
            for(int[] uuvvww : new int[][]{
            	// Interior railing
            	{4,5,11, 4,5,12}, {4,5,11, 9,5,11}, 
            	// Exterior railing
            	{10,5,1, 10,5,2}, {11,5,1, 13,5,1}, {14,5,1, 14,5,2}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.iron_bars, 0, Blocks.iron_bars, 0, false);
            }
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
        		// Downstairs
        		{2,3,10}, {2,3,12}, 
        		{3,3,13}, {5,3,13}, {7,3,13}, {9,3,13}, {11,3,13}, {13,3,13}, 
        		{14,3,12}, {14,3,10}, {14,3,8}, {14,3,6}, {14,3,4}, 
        		{10,3,4}, 
        		// Upstairs
        		{2,6,10}, {2,6,12}, 
        		{3,6,13}, {5,6,13}, {7,6,13}, {9,6,13}, {11,6,13}, {13,6,13}, 
        		{14,6,12}, {14,6,10}, {14,6,8}, {14,6,6}, {14,6,4}, {13,6,3}, 
        		{10,6,4}, 
        		{3,6,9}, {5,6,9}, {7,6,9}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Torches
            for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	// Interior
            	{4,3,5, -1}, {0,3,5, -1}, {0,3,13, -1}, {14,3,0, -1}, {7,3,0, -1}, {7,3,2, -1}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	// Interior
            	{4,7,11}, {12,7,11}, {12,7,5}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Sitting Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(false); Block biomeSittingLanternBlock = (Block)blockObject[0]; int biomeSittingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	// On the couch ends
            	{10,3,12}, {13,3,9}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeSittingLanternBlock, biomeSittingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
        	
            // Potted Cactus
            this.placeBlockAtCurrentPosition(world, Blocks.flower_pot, 9, 13,6,9, structureBB); // 9 is cactus
        	
            
            // Potted random flower
            int u=3; int v=3; int w=12;
            int x = this.getXWithOffset(u, w);
            int y = this.getYWithOffset(v);
            int z = this.getZWithOffset(u, w);
            
            Object[] cornflowerObject = ModObjects.chooseModCornflower(); Object[] lilyOfTheValleyObject = ModObjects.chooseModLilyOfTheValley();
    		int randomPottedPlant = random.nextInt(10 + (cornflowerObject!=null && lilyOfTheValleyObject!=null ? 2:0))-1;
    		if (randomPottedPlant==-1) {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.yellow_flower, 0);} // Dandelion specifically
    		else if (randomPottedPlant==9 && cornflowerObject!=null) {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, (Block) cornflowerObject[0], (Integer) cornflowerObject[1]);}
    		else if (randomPottedPlant==10 && lilyOfTheValleyObject!=null) {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, (Block) lilyOfTheValleyObject[0], (Integer) lilyOfTheValleyObject[1]);}
    		else {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.red_flower, randomPottedPlant);}          // Every other type of flower
        	
        	
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 3;
        	int chestV = 5;
        	int chestW = 12;
        	int chestO = 2; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo("vn_jungle_house");
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
        	
            
        	// Trees
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.sapling, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeSaplingBlock = (Block)blockObject[0]; int biomeSaplingMeta = (Integer)blockObject[1];
        	for (int[] uvwss : new int[][]{ // u,v,w, ushift,wshift: Which adjacent spaces to use if this is a Dark Oak sapling
        		{-2,1,6, -1,1},
        		})
            {
        		Block dirtblock = world.getBlock(this.getXWithOffset(uvwss[0], uvwss[2]), this.getYWithOffset(uvwss[1]-1), this.getZWithOffset(uvwss[0], uvwss[2]));
        		Block saplingblock = world.getBlock(this.getXWithOffset(uvwss[0], uvwss[2]), this.getYWithOffset(uvwss[1]), this.getZWithOffset(uvwss[0], uvwss[2]));
        		
        		// Don't place if there's no dirt beneath to grow
        		if (dirtblock==null || (dirtblock != Blocks.dirt && dirtblock != Blocks.grass)) {continue;}
        		// Don't place if the sapling can't see the sky
        		if (!world.canBlockSeeTheSky(this.getXWithOffset(uvwss[0], uvwss[2]), this.getYWithOffset(uvwss[1]), this.getZWithOffset(uvwss[0], uvwss[2]))) {continue;}
        		// Dark oak version of the above
        		if (biomeSaplingMeta==5)
        		{
        			Block dirtblock1 = world.getBlock(this.getXWithOffset(uvwss[0]+uvwss[3], uvwss[2]), this.getYWithOffset(uvwss[1]-1), this.getZWithOffset(uvwss[0]+uvwss[3], uvwss[2]));
        			Block dirtblock2 = world.getBlock(this.getXWithOffset(uvwss[0], uvwss[2]+uvwss[4]), this.getYWithOffset(uvwss[1]-1), this.getZWithOffset(uvwss[0], uvwss[2]+uvwss[4]));
        			Block dirtblock3 = world.getBlock(this.getXWithOffset(uvwss[0]+uvwss[3], uvwss[2]+uvwss[4]), this.getYWithOffset(uvwss[1]-1), this.getZWithOffset(uvwss[0]+uvwss[3], uvwss[2]+uvwss[4]));
        			
        			if (dirtblock1==null || dirtblock2==null || dirtblock3==null || // Foundation blocks are null
        					// Foundation blocks can't support growing a tree
        					(dirtblock1 != Blocks.dirt && dirtblock1 != Blocks.grass)
        					|| (dirtblock2 != Blocks.dirt && dirtblock2 != Blocks.grass)
        					|| (dirtblock3 != Blocks.dirt && dirtblock3 != Blocks.grass)
        					// Foundation blocks can't see the sky
        					|| !world.canBlockSeeTheSky(this.getXWithOffset(uvwss[0]+uvwss[3], uvwss[2]), this.getYWithOffset(uvwss[1]), this.getZWithOffset(uvwss[0]+uvwss[3], uvwss[2]))
        					|| !world.canBlockSeeTheSky(this.getXWithOffset(uvwss[0], uvwss[2]+uvwss[4]), this.getYWithOffset(uvwss[1]), this.getZWithOffset(uvwss[0], uvwss[2]+uvwss[4]))
        					|| !world.canBlockSeeTheSky(this.getXWithOffset(uvwss[0]+uvwss[3], uvwss[2]+uvwss[4]), this.getYWithOffset(uvwss[1]), this.getZWithOffset(uvwss[0]+uvwss[3], uvwss[2]+uvwss[4]))
        					)
        			{
        				continue;
        			}
        		}
        		
        		
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
        	
            
            // Sign
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.standing_sign, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStandingSignBlock = (Block)blockObject[0];
            if (GeneralConfig.nameSign)
            {
            	int signU = 1;
    			int signV = 1;
    			int signW = 4;
    			int signO = 8;
                int signX = this.getXWithOffset(signU, signW);
                int signY = this.getYWithOffset(signV);
                int signZ = this.getZWithOffset(signU, signW);
                boolean hanging=false;
                
                // Generate sign contents manually so that you can substitute the "Village of..." stuff
        		TileEntitySign signContents = new TileEntitySign();
        		
            	String topLine = "Villa at";
        		topLine = topLine.trim();
        		
        		if ( (namePrefix.length() + 1 + nameRoot.length()) > 15 )
        		{
        			// Prefix+Root is too long, so move prefix to line 1
        			signContents.signText[0] = GeneralConfig.headerTags.trim() + topLine.trim();
        			signContents.signText[1] = namePrefix.trim();
        			if ( (nameRoot.length() + 1 + nameSuffix.length()) > 15 )
        			{
        				// Root+Suffix is too long, so move suffix to line 3
        				signContents.signText[2] = nameRoot.trim();
        				signContents.signText[3] = nameSuffix.trim();
        			}
        			else
        			{
        				// Fit Root+Suffix onto line 2
        				signContents.signText[2] = (nameRoot+" "+nameSuffix).trim();
        			}
        		}
        		else if ( (namePrefix.length() + 1 + nameRoot.length() + 1 + nameSuffix.length()) <= 15 )
        		{
        			// Whole name fits on one line! Put it all on line 2.
        			signContents.signText[1] = GeneralConfig.headerTags.trim() + topLine;
        			signContents.signText[2] = (namePrefix+" "+nameRoot+" "+nameSuffix).trim();
        		}
        		else
        		{
        			// Only Prefix and Root can fit together on line 2.
        			signContents.signText[1] = GeneralConfig.headerTags.trim() + topLine.trim();
        			signContents.signText[2] = (namePrefix+" "+nameRoot).trim();
        			signContents.signText[3] = nameSuffix.trim();
        		}
        		// If top line is blank, roll everything up one line:
        		if (topLine.equals(""))
        		{
        			for (int isign=0; isign <3; isign++)
        			{
        				signContents.signText[isign] = signContents.signText[isign+1];	
        			}
        			signContents.signText[3] = "";
        		}
        		
        		
    			world.setBlock(signX, signY, signZ, biomeStandingSignBlock, StructureVillageVN.getSignRotationMeta(signO, this.coordBaseMode, hanging), 2); // 2 is "send change to clients without block update notification"
        		world.setTileEntity(signX, signY, signZ, signContents);
            }
        	
            
            // Banner    		
    		if (GeneralConfig.villageBanners)
    		{
        		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
        		if (testForBanner!=null)
    			{
                    int bannerU = 9;
        			int bannerV = 4;
        			int bannerW = 1;
        			int bannerO = 3;
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
	        			{12, 5, 2, -1, 0}, // On the balcony 
	        			{6, 2, 4, -1, 0}, // At the entrance 
	        			{12, 2, 11, -1, 0}, // On the couch 
	        			{3, 5, 11, -1, 0}, // In front of the chest 
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
	public static ArrayList<BlueprintData> getRandomJungleDecorBlueprint(MaterialType materialType, boolean disallowModSubs, BiomeGenBase biome, int horizIndex, Random random)
	{
		int decorCount = 8;
		return getJungleDecorBlueprint(random.nextInt(decorCount), materialType, disallowModSubs, biome, horizIndex, random);
	}
	public static ArrayList<BlueprintData> getJungleDecorBlueprint(int decorType, MaterialType materialType, boolean disallowModSubs, BiomeGenBase biome, int horizIndex, Random random)
	{
		ArrayList<BlueprintData> blueprint = new ArrayList(); // The blueprint to export
		
		// Generate per-material blocks
		
		Object[] blockObject;
    	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, materialType, biome, disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, materialType, biome, disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 3, materialType, biome, disallowModSubs); Block biomeChiseledStoneBlock = (Block)blockObject[0]; int biomeChiseledStoneMeta = (Integer)blockObject[1];
    	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
    	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
    	blockObject = ModObjects.chooseModLanternBlock(false); Block biomeSittingLanternBlock = (Block)blockObject[0]; int biomeSittingLanternMeta = (Integer)blockObject[1];
    	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, materialType, biome, disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
    	
		boolean genericBoolean=false;
    	int genericInt=0;
		
        switch (decorType)
        {
    	case 0: // Hay bales by Roadhog360 and AstroTibs
    		
    		Block hayBlock = Blocks.hay_block; // Meta: 0 for vertical, 4 for E/W, 8 for N/S
    		
    		BlueprintData.addPlaceBlock(blueprint, 0, 1, 0, hayBlock, 0);
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, biomeDirtBlock, biomeDirtMeta); // Foundation
    		
    		switch (random.nextInt(4))
    		{
    		case 0: // Facing you
    			// Bale in center
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, 0, hayBlock, horizIndex%2==0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, hayBlock, biomeDirtMeta); // Foundation
    			// Bale in front
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, -1, hayBlock, horizIndex%2==0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, -1, hayBlock, biomeDirtMeta); // Foundation
    			// Back-left corner
    			BlueprintData.addPlaceBlock(blueprint, -1, 0, 1, hayBlock, 0);
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, 1, hayBlock, biomeDirtMeta); // Foundation
    			// Back-right corner
    			BlueprintData.addPlaceBlock(blueprint, 1, 0, 1, hayBlock, 0);
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, 1, hayBlock, biomeDirtMeta); // Foundation
    			// Right side
    			BlueprintData.addPlaceBlock(blueprint, 1, 0, 0, hayBlock, horizIndex%2==0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, 0, hayBlock, biomeDirtMeta); // Foundation
    			break;
    		case 1: // Facing left
    			// Bale in center
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, 0, hayBlock, horizIndex%2==0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, hayBlock, biomeDirtMeta); // Foundation
    			// Bale in front
    			BlueprintData.addPlaceBlock(blueprint, -1, 0, 0, hayBlock, horizIndex%2==0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, 0, hayBlock, biomeDirtMeta); // Foundation
    			// Back-left corner
    			BlueprintData.addPlaceBlock(blueprint, 1, 0, 1, hayBlock, 0);
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, 1, hayBlock, biomeDirtMeta); // Foundation
    			// Back-right corner
    			BlueprintData.addPlaceBlock(blueprint, -1, 0, 1, hayBlock, 0);
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, 1, hayBlock, biomeDirtMeta); // Foundation
    			// Right side
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, -1, hayBlock, horizIndex%2==0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, -1, hayBlock, biomeDirtMeta); // Foundation
    			break;
    		case 2: // Facing away
    			// Bale in center
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, 0, hayBlock, horizIndex%2==0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, biomeDirtBlock, biomeDirtMeta); // Foundation
    			// Bale in front
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, 1, hayBlock, horizIndex%2==0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, 1, biomeDirtBlock, biomeDirtMeta); // Foundation
    			// Back-left corner
    			BlueprintData.addPlaceBlock(blueprint, 1, 0, -1, hayBlock, 0);
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, -1, biomeDirtBlock, biomeDirtMeta); // Foundation
    			// Back-right corner
    			BlueprintData.addPlaceBlock(blueprint, -1, 0, -1, hayBlock, 0);
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, -1, biomeDirtBlock, biomeDirtMeta); // Foundation
    			// Right side
    			BlueprintData.addPlaceBlock(blueprint, -1, 0, 0, hayBlock, horizIndex%2==0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, 0, biomeDirtBlock, biomeDirtMeta); // Foundation
    			break;
    		case 3: // Facing right
    			// Bale in center
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, 0, hayBlock, horizIndex%2==0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, hayBlock, biomeDirtMeta); // Foundation
    			// Bale in front
    			BlueprintData.addPlaceBlock(blueprint, 1, 0, 0, hayBlock, horizIndex%2==0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, 0, hayBlock, biomeDirtMeta); // Foundation
    			// Back-left corner
    			BlueprintData.addPlaceBlock(blueprint, -1, 0, -1, hayBlock, 0);
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, -1, hayBlock, biomeDirtMeta); // Foundation
    			// Back-right corner
    			BlueprintData.addPlaceBlock(blueprint, 1, 0, -1, hayBlock, 0);
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, -1, hayBlock, biomeDirtMeta); // Foundation
    			// Right side
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, 1, hayBlock, horizIndex%2==0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, 1, hayBlock, biomeDirtMeta); // Foundation
    			break;
    		}
    		break;

    	case 1: // Campfire on cobblestone by jss2a98aj
        	blockObject = ModObjects.chooseModCampfireBlock(random.nextInt(4), horizIndex); Block campfireBlock = (Block) blockObject[0]; int campfireMeta = (Integer) blockObject[1];
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, biomeDirtBlock, biomeDirtMeta); // Foundation
    		BlueprintData.addFillBelowTo(blueprint, 1, -1, 0, biomeDirtBlock, biomeDirtMeta); // Foundation
    		BlueprintData.addFillBelowTo(blueprint, -1, -1, 0, biomeDirtBlock, biomeDirtMeta); // Foundation
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 1, biomeDirtBlock, biomeDirtMeta); // Foundation
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, -1, biomeDirtBlock, biomeDirtMeta); // Foundation
    		BlueprintData.addPlaceBlock(blueprint, 0, 0, 0, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, 1, 0, 0, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, -1, 0, 0, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, 0, 0, 1, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, 0, 0, -1, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, 0, 1, 0, campfireBlock, campfireMeta);
    		break;
    		
    	case 2: // Fence post hanging lantern on stone brick foundation by jss2a98aj
    		// Central column
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, biomeDirtBlock, biomeDirtMeta); // Foundation
    		BlueprintData.addPlaceBlock(blueprint, 0, 0, 0, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		BlueprintData.addPlaceBlock(blueprint, 0, 1, 0, biomeChiseledStoneBlock, biomeChiseledStoneMeta);
    		BlueprintData.addPlaceBlock(blueprint, 0, 2, 0, biomeFenceBlock);
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, 0, 3, 0, biomeFenceBlock);
    		
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, 0, 3, -1, biomeFenceBlock); // Fence post
    		BlueprintData.addPlaceBlock(blueprint, 0, 2, -1, biomeHangingLanternBlock, biomeHangingLanternMeta);
    		
    		// Vines
        	if (materialType!=FunctionsVN.MaterialType.SNOW)
        	{ // 0 and 2 are ok
        		for (int[] xyzo : new int[][]{ // Orientation - 1:north, 2:east, 4:south, 8:west
        			{0,0,-1, new int[]{1,2,4,8}[horizIndex]}, // Front
        			{1,0,0, new int[]{2,4,2,4}[horizIndex]}, // Right
        			{-1,0,0, new int[]{8,1,8,1}[horizIndex]}, // Left
            		})
                {
        			BlueprintData.addPlaceBlock(blueprint, xyzo[0], xyzo[1], xyzo[2], Blocks.vine, xyzo[3]);
                }
        	}
    		break;
    		
    	case 3: // Lantern on stone brick post by jss2a98aj
    		// Central column
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, biomeDirtBlock, biomeDirtMeta); // Foundation
    		BlueprintData.addPlaceBlock(blueprint, 0, 0, 0, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		BlueprintData.addPlaceBlock(blueprint, 0, 1, 0, biomeChiseledStoneBlock, biomeChiseledStoneMeta);
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, 0, 2, 0, biomeSittingLanternBlock, biomeSittingLanternMeta);
    		
    		// Vines
    		switch (random.nextInt(4))
    		{
    		case 0: // Facing you
        		if (materialType!=FunctionsVN.MaterialType.SNOW)
            	{
            		for (int[] xyzo : new int[][]{ // Orientation - 1:north, 2:east, 4:south, 8:west
            			{0,0,-1, new int[]{1,2,4,8}[horizIndex]}, {0,1,-1, new int[]{1,2,4,8}[horizIndex]}, // Front
            			{1,0,0, new int[]{2,4,2,4}[horizIndex]}, // Right
            			{0,0,1, new int[]{4,8,1,2}[horizIndex]}, // Back
            			{-1,1,0, new int[]{8,1,8,1}[horizIndex]}, // Left
                		})
                    {
            			BlueprintData.addPlaceBlock(blueprint, xyzo[0], xyzo[1], xyzo[2], Blocks.vine, xyzo[3]);
                    }
            	}
        		break;
        		
    		case 1: // Facing left
    			if (materialType!=FunctionsVN.MaterialType.SNOW)
            	{
            		for (int[] xyzo : new int[][]{ // Orientation - 1:north, 2:east, 4:south, 8:west
            			{-1,0,0, new int[]{8,1,8,1}[horizIndex]}, {-1,1,0, new int[]{8,1,8,1}[horizIndex]}, // Front
            			{0,0,-1, new int[]{1,2,4,8}[horizIndex]}, // Right
            			{1,0,0, new int[]{2,4,2,4}[horizIndex]}, // Back
            			{0,1,1, new int[]{4,8,1,2}[horizIndex]}, // Left
                		})
                    {
            			BlueprintData.addPlaceBlock(blueprint, xyzo[0], xyzo[1], xyzo[2], Blocks.vine, xyzo[3]);
                    }
            	}
            	break;
            	
    		case 2: // Facing away
    			if (materialType!=FunctionsVN.MaterialType.SNOW)
            	{
            		for (int[] xyzo : new int[][]{ // Orientation - 1:north, 2:east, 4:south, 8:west
            			{0,0,1, new int[]{4,8,1,2}[horizIndex]}, {0,1,1, new int[]{4,8,1,2}[horizIndex]}, // Front
            			{-1,0,0, new int[]{8,1,8,1}[horizIndex]}, // Right
            			{0,0,-1, new int[]{1,2,4,8}[horizIndex]}, // Back
            			{1,1,0, new int[]{2,4,2,4}[horizIndex]}, // Left
                		})
                    {
            			BlueprintData.addPlaceBlock(blueprint, xyzo[0], xyzo[1], xyzo[2], Blocks.vine, xyzo[3]);
                    }
            	}
    			break;
    			
    		case 3: // Facing right
    			if (materialType!=FunctionsVN.MaterialType.SNOW)
            	{
            		for (int[] xyzo : new int[][]{ // Orientation - 1:north, 2:east, 4:south, 8:west
            			{1,0,0, new int[]{2,4,2,4}[horizIndex]}, {1,1,0, new int[]{2,4,2,4}[horizIndex]}, // Front
            			{0,0,1, new int[]{4,8,1,2}[horizIndex]}, // Right
            			{-1,0,0, new int[]{8,1,8,1}[horizIndex]}, // Back
            			{0,1,-1, new int[]{1,2,4,8}[horizIndex]}, // Left
                		})
                    {
            			BlueprintData.addPlaceBlock(blueprint, xyzo[0], xyzo[1], xyzo[2], Blocks.vine, xyzo[3]);
                    }
            	}
    			break;
    		}
    		break;
    		
    	case 4: // Stone post with lanterns hanging from walls by jss2a98aj
    		// Central column
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, biomeDirtBlock, biomeDirtMeta); // Foundation
    		BlueprintData.addFillWithBlocks(blueprint, 0,0,0, 0,2,0, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, 0, 3, 0, biomeChiseledStoneBlock, biomeChiseledStoneMeta);
    		
    		// Belt of vines
    		if (materialType!=FunctionsVN.MaterialType.SNOW)
        	{
        		BlueprintData.addPlaceBlock(blueprint, 0, 1, 1, Blocks.vine, new int[]{4,8,1,2}[horizIndex]);
        		BlueprintData.addPlaceBlock(blueprint, 1, 1, 0, Blocks.vine, new int[]{2,4,2,4}[horizIndex]);
        		BlueprintData.addPlaceBlock(blueprint, 0, 1, -1, Blocks.vine, new int[]{1,2,4,8}[horizIndex]);
        		BlueprintData.addPlaceBlock(blueprint, -1, 1, 0, Blocks.vine, new int[]{8,1,8,1}[horizIndex]);
        	}
    		
    		genericInt = random.nextInt(4);
    		
    		// Hanging lanterns
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, materialType, biome, disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
        	
			BlueprintData.addPlaceBlockAndClearAbove(blueprint, genericInt%2==0?-1:0, 3, genericInt%2==0?0:-1, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta);
			BlueprintData.addPlaceBlockAndClearAbove(blueprint, genericInt%2==0?1:0, 3, genericInt%2==0?0:1, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta);
			BlueprintData.addPlaceBlock(blueprint, genericInt%2==0?-1:0, 2, genericInt%2==0?0:-1, biomeHangingLanternBlock, biomeHangingLanternMeta);
			BlueprintData.addPlaceBlock(blueprint, genericInt%2==0?1:0, 2, genericInt%2==0?0:1, biomeHangingLanternBlock, biomeHangingLanternMeta);
    		
			// Vines
    		if (materialType!=FunctionsVN.MaterialType.SNOW)
        	{
        		BlueprintData.addPlaceBlock(blueprint, genericInt%2!=0?-1:0, 2, genericInt%2!=0?0:-1, Blocks.vine, genericInt%2!=0?new int[]{8,1,8,1}[horizIndex]:new int[]{1,2,4,8}[horizIndex]);
        		BlueprintData.addPlaceBlock(blueprint, genericInt%2!=0?1:0, 2, genericInt%2!=0?0:1, Blocks.vine, genericInt%2!=0?new int[]{2,4,2,4}[horizIndex]:new int[]{4,8,1,2}[horizIndex]);
        		
        		BlueprintData.addPlaceBlock(blueprint, genericInt==1?-1:genericInt==3?1:0, 3, genericInt==0?-1:genericInt==2?1:0, Blocks.vine, genericInt==0?new int[]{1,2,4,8}[horizIndex]:genericInt==1?new int[]{8,1,8,1}[horizIndex]:genericInt==2?new int[]{4,8,1,2}[horizIndex]:new int[]{2,4,2,4}[horizIndex]);
        	}
    		
    		break;
    		
    	case 5: // Lantern on a stump by jss2a98aj
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, biomeDirtBlock, biomeDirtMeta); // Foundation
    		BlueprintData.addPlaceBlock(blueprint, 0, 0, 0, biomeLogVertBlock, biomeLogVertMeta);
    		BlueprintData.addPlaceBlock(blueprint, 0, 1, 0, biomeSittingLanternBlock, biomeSittingLanternMeta);
			
    		break;
    		
    	case 6: // Lantern on a log by jss2a98aj
    		
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, horizIndex%2==0?8:4, materialType, biome, disallowModSubs); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1]; // Perpendicular to you
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, horizIndex%2==0?4:8, materialType, biome, disallowModSubs); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1]; // Toward you
    		
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, biomeDirtBlock, biomeDirtMeta); // Foundation
    		BlueprintData.addPlaceBlock(blueprint, 0, 1, 0, biomeSittingLanternBlock, biomeSittingLanternMeta);
    		
    		switch (random.nextInt(4))
    		{
    		case 0: // Facing you
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, 0, biomeDirtBlock, biomeDirtMeta); // Foundation
    			BlueprintData.addFillWithBlocks(blueprint, 0,0,0, 1,0,0, biomeLogHorAlongBlock, biomeLogHorAlongMeta);
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, 1, biomeDirtBlock, biomeDirtMeta); // Foundation
    			BlueprintData.addPlaceBlock(blueprint, -1, 0, 1, biomeLogVertBlock, biomeLogVertMeta);
    			// Vines
    			if (materialType!=FunctionsVN.MaterialType.SNOW) // Orientation - 1:north, 2:east, 4:south, 8:west
            	{
            		BlueprintData.addPlaceBlock(blueprint, -1, 0, 0, Blocks.vine, new int[]{1,2,4,8}[horizIndex]); // Front
            		BlueprintData.addPlaceBlock(blueprint, 0, 0, 1, Blocks.vine, new int[]{2,4,2,4}[horizIndex]); // Right
            	}
    			break;
    			
    		case 1: // Facing left
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, -1, biomeDirtBlock, biomeDirtMeta); // Foundation
    			BlueprintData.addFillWithBlocks(blueprint, 0,0,-1, 0,0,0, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta);
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, 1, biomeDirtBlock, biomeDirtMeta); // Foundation
    			BlueprintData.addPlaceBlock(blueprint, 1, 0, 1, biomeLogVertBlock, biomeLogVertMeta);
    			// Vines
    			if (materialType!=FunctionsVN.MaterialType.SNOW) // Orientation - 1:north, 2:east, 4:south, 8:west
            	{
            		BlueprintData.addPlaceBlock(blueprint, 0, 0, 1, Blocks.vine, new int[]{8,1,8,1}[horizIndex]); // Front
            		BlueprintData.addPlaceBlock(blueprint, 1, 0, 0, Blocks.vine, new int[]{1,2,4,8}[horizIndex]); // Right
            	}
    			break;
    			
    		case 2: // Facing away
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, 0, biomeDirtBlock, biomeDirtMeta); // Foundation
    			BlueprintData.addFillWithBlocks(blueprint, -1,0,0, 0,0,0, biomeLogHorAlongBlock, biomeLogHorAlongMeta);
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, -1, biomeDirtBlock, biomeDirtMeta); // Foundation
    			BlueprintData.addPlaceBlock(blueprint, 1, 0, -1, biomeLogVertBlock, biomeLogVertMeta);
    			// Vines
    			if (materialType!=FunctionsVN.MaterialType.SNOW) // Orientation - 1:north, 2:east, 4:south, 8:west
            	{
            		BlueprintData.addPlaceBlock(blueprint, 1, 0, 0, Blocks.vine, new int[]{4,8,1,2}[horizIndex]); // Front
            		BlueprintData.addPlaceBlock(blueprint, 0, 0, -1, Blocks.vine, new int[]{8,1,8,1}[horizIndex]); // Right
            	}
    			break;
    			
    		case 3: // Facing right
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, 1, biomeDirtBlock, biomeDirtMeta); // Foundation
    			BlueprintData.addFillWithBlocks(blueprint, 0,0,0, 0,0,1, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta);
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, -1, biomeDirtBlock, biomeDirtMeta); // Foundation
    			BlueprintData.addPlaceBlock(blueprint, -1, 0, -1, biomeLogVertBlock, biomeLogVertMeta);
    			// Vines
    			if (materialType!=FunctionsVN.MaterialType.SNOW) // Orientation - 1:north, 2:east, 4:south, 8:west
            	{
            		BlueprintData.addPlaceBlock(blueprint, 0, 0, -1, Blocks.vine, new int[]{2,4,2,4}[horizIndex]); // Front
            		BlueprintData.addPlaceBlock(blueprint, -1, 0, 0, Blocks.vine, new int[]{4,8,1,2}[horizIndex]); // Right
            	}
    			break;
    		}
    		
    		break;
    		
    	case 7: // Lantern between two posts by jss2a98aj
    		
    		int vertOffset = -1;
    		
    		BlueprintData.addFillBelowTo(blueprint, 0, -1+vertOffset, 0, biomeDirtBlock, biomeDirtMeta); // Foundation
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, 0, 0+vertOffset, 0, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		
    		genericBoolean = random.nextBoolean();
    		
    		// Foundations
    		BlueprintData.addFillBelowTo(blueprint, genericBoolean?-1:0, -1+vertOffset, genericBoolean?0:-1, biomeDirtBlock, biomeDirtMeta);
    		BlueprintData.addFillBelowTo(blueprint, genericBoolean?1:0, -1+vertOffset, genericBoolean?0:1, biomeDirtBlock, biomeDirtMeta);
    		// Cobblestone
    		BlueprintData.addPlaceBlock(blueprint, genericBoolean?-1:0, 0+vertOffset, genericBoolean?0:-1, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		BlueprintData.addPlaceBlock(blueprint, genericBoolean?1:0, 0+vertOffset, genericBoolean?0:1, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		// Fence posts
    		BlueprintData.addFillWithBlocks(blueprint, genericBoolean?-1:0,1+vertOffset,genericBoolean?0:-1, genericBoolean?-1:0,3+vertOffset,genericBoolean?0:-1, biomeFenceBlock, 0);
    		BlueprintData.addFillWithBlocks(blueprint, genericBoolean?1:0,1+vertOffset,genericBoolean?0:1, genericBoolean?1:0,3+vertOffset,genericBoolean?0:1, biomeFenceBlock, 0);
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, genericBoolean?-1:0, 3+vertOffset, genericBoolean?0:-1, biomeFenceBlock, 0);
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, genericBoolean?1:0, 3+vertOffset, genericBoolean?0:1, biomeFenceBlock, 0);
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, 0, 3+vertOffset, 0, biomeFenceBlock, 0);
    		// Lantern
    		BlueprintData.addPlaceBlock(blueprint, 0, 2+vertOffset, 0, biomeHangingLanternBlock, biomeHangingLanternMeta);
    		break;
        }
        
        // Return the decor blueprint
        return blueprint;
	}
}
