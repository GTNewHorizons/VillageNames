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
import astrotibs.villagenames.utility.FunctionsVN.VillageType;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.Reference;
import astrotibs.villagenames.village.StructureVillageVN;
import astrotibs.villagenames.village.StructureVillageVN.StartVN;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
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
    
    public static class JungleStatue extends StructureVillageVN.StartVN
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
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	// Establish top and filler blocks, substituting Grass and Dirt if they're null
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
        	Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
            
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
            
        	// Try to establish diorite blocks used for the statue. If any kind doesn't exist, use default for all.
        	Block biomePolishedDioriteStairsBlock;
        	Block biomePolishedDioriteBlock; int biomePolishedDioriteMeta;
        	Block biomeDioriteStairsBlock;
        	Block biomeDioriteBlock; int biomeDioriteMeta;
        	Block biomeDioriteWallBlock; int biomeDioriteWallMeta;
        	
    		boolean useOnlyStone = false; // This flag will indicate to use stone instead of diorite, should we need to.
        	while (true)
        	{
            	// Polished diorite stairs
            	if (useOnlyStone) {biomePolishedDioriteStairsBlock = Blocks.stone_brick_stairs;} // Set to stone brick stairs
            	else
            	{
            		biomePolishedDioriteStairsBlock = ModObjects.chooseModPolishedDioriteStairsBlock(); 
                	
                	if (biomePolishedDioriteStairsBlock==null) // Try regular diorite stairs
                	{
                		biomePolishedDioriteStairsBlock = ModObjects.chooseModDioriteStairsBlock();
                		if (biomePolishedDioriteStairsBlock==null) {useOnlyStone=true; continue;} // Trigger flag and reset
                	}
            	}
            	biomePolishedDioriteStairsBlock = (Block) StructureVillageVN.getBiomeSpecificBlockObject(biomePolishedDioriteStairsBlock, 0, this.materialType, this.biome, this.disallowModSubs)[0];
            	
            	
            	// Polished diorite blocks
            	if (useOnlyStone) {blockObject = new Object[]{Blocks.stonebrick, 0};} // Set to stone brick
            	else
            	{
            		blockObject = ModObjects.chooseModPolishedDioriteObject();
                	if (blockObject==null) // Try regular diorite block
                	{
                		blockObject = ModObjects.chooseModDioriteObject();
                		if (blockObject==null) {useOnlyStone=true; continue;} // Trigger flag and reset
                	}; 
            	}
            	blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs); biomePolishedDioriteBlock = (Block)blockObject[0]; biomePolishedDioriteMeta = (Integer)blockObject[1];
            	
                
            	// Regular diorite stairs
            	if (useOnlyStone) {biomeDioriteStairsBlock = Blocks.stone_stairs;} // Set to cobblestone stairs
            	else
            	{
            		biomeDioriteStairsBlock = ModObjects.chooseModDioriteStairsBlock(); 
                	if (biomeDioriteStairsBlock==null) {useOnlyStone=true; continue;} // Trigger flag and reset
            	}
            	biomeDioriteStairsBlock = (Block) StructureVillageVN.getBiomeSpecificBlockObject(biomeDioriteStairsBlock, 0, this.materialType, this.biome, this.disallowModSubs)[0];
            	
            	
            	// Diorite blocks
            	if (useOnlyStone) {blockObject = new Object[]{Blocks.cobblestone, 0};} // Set to cobblestone
            	else
            	{
            		blockObject = ModObjects.chooseModDioriteObject();
                	if (blockObject==null) {useOnlyStone=true; continue;} // Trigger flag and reset
            	}
            	blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs); biomeDioriteBlock = (Block)blockObject[0]; biomeDioriteMeta = (Integer)blockObject[1];
            	
            	
            	// Diorite wall
            	if (useOnlyStone) {blockObject = new Object[]{Blocks.cobblestone_wall, 0};} // Set to cobblestone wall
            	else
            	{
            		blockObject = ModObjects.chooseModDioriteWallObject();
                	if (blockObject==null) {useOnlyStone=true; continue;} // Trigger flag and reset
            	}
            	blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs); biomeDioriteWallBlock = (Block)blockObject[0]; biomeDioriteWallMeta = (Integer)blockObject[1];
            	
            	
            	// If you make it here, all blocks are either diorite-type or stone-type.
            	break;
            }
        	
        	// Now, construct the statue with either all diorite blocks, or all stone
        	
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
        	for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
        		// Leg
        		{5,5,5, 2+4}, {5,5,4, 3+4}, {5,6,4, 3},
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeDioriteStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
            }
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
            int u=7; int v=8; int w=3;
            int x = this.getXWithOffset(u, w);
            int y = this.getYWithOffset(v);
            int z = this.getZWithOffset(u, w);
            
            Object[] cornflowerObject = ModObjects.chooseModCornflower(); Object[] lilyOfTheValleyObject = ModObjects.chooseModLilyOfTheValley();
    		int randomPottedPlant = random.nextInt(10)-1;
    		if (randomPottedPlant==-1) {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.yellow_flower, 0);} // Dandelion specifically
    		else {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.red_flower, randomPottedPlant);}          // Every other type of flower
            
    		
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
                boolean isHanging=true;
                
        		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);
        		
    			world.setBlock(signX, signY, signZ, biomeWallSignBlock, StructureVillageVN.getSignRotationMeta(signO, this.coordBaseMode, isHanging), 2); // 2 is "send change to clients without block update notification"
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
                
    			world.setBlock(signX, signY, signZ, biomeWallSignBlock, StructureVillageVN.getSignRotationMeta(signO, this.coordBaseMode, isHanging), 2); // 2 is "send change to clients without block update notification"
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
            			int bannerV = 7;
            			int bannerW = 6;
            			int bannerO = 0; // Facing away from you
            			boolean isHanging=true;
            			
            			int bannerX = this.getXWithOffset(bannerU, bannerW);
            			int bannerY = this.getYWithOffset(bannerV);
                        int bannerZ = this.getZWithOffset(bannerU, bannerW);
                        
                    	// Set the banner and its orientation
        				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
        				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(bannerO, this.coordBaseMode, isHanging), 2);
        				
        				// Set the tile entity
        				TileEntity bannerTileEntity = world.getTileEntity(bannerX, bannerY, bannerZ);
    				if (bannerTileEntity==null) {bannerTileEntity = new TileEntityBanner();}
        				NBTTagCompound bannerNBTTagCompound = new NBTTagCompound();
        				bannerTileEntity.writeToNBT(bannerNBTTagCompound);
        				bannerNBTTagCompound.setBoolean("IsStanding", !isHanging);
        				
        				ItemStack bannerItemStack = ModObjects.chooseModBannerItem();
        				bannerItemStack.setTagInfo("BlockEntityTag", villageNBTtag.getCompoundTag("BlockEntityTag"));
        				
        				bannerNBTTagCompound = FunctionsVN.setItemValues(bannerItemStack, bannerNBTTagCompound);
        				bannerTileEntity.readFromNBT(bannerNBTTagCompound);
        				
        				world.setTileEntity(bannerX, bannerY, bannerZ, bannerTileEntity);
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
	
    
	// --- Cocoa Tree --- //
	// designed by Roadhog360
    
    public static class JungleCocoaTree extends StructureVillageVN.StartVN
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
    	
	    public JungleCocoaTree() {}
		
		public JungleCocoaTree(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, float villageSize)
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
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	// Establish top and filler blocks, substituting Grass and Dirt if they're null
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
        	Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
            
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
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
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
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
        			}
                }
        	}
        	
        	
        	// Cocoa pods - random number and positions
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int i=0; i<5; i++)
                {
        			// Choose a random side for the pod
        			int side = random.nextInt(4);
        			
        			int u,v,w;
        			
        			switch (side)
        			{
        			case 0: // Forward facing
        				u=5; w=6; v=4+(random.nextBoolean()?1:0)+(random.nextBoolean()?1:0);
        				break;
        			case 1: // Right facing
        				u=6; w=5; v=4+(random.nextBoolean()?1:0)+(random.nextBoolean()?1:0);
        				break;
        			default:
        			case 2: // Back facing
        				u=5; w=4; v=4+(random.nextBoolean()?1:0)+(random.nextBoolean()?1:0);
        				break;
        			case 3: // Left facing
        				u=4; w=5; v=4+(random.nextBoolean()?1:0)+(random.nextBoolean()?1:0);
        				break;
        			}
        			
            		this.placeBlockAtCurrentPosition(world, Blocks.cocoa, StructureVillageVN.getCocoaPodOrientationMeta(side, this.coordBaseMode, random.nextInt(3)), u, v, w, structureBB);
            		
        			// Randomly stop placing pods
        			if (random.nextInt(4)==0) {break;}
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
                boolean isHanging=false;
                
                // Cobblestone foundation
    			this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, signU, signV-1, signW, structureBB);
    			
        		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);

    			world.setBlock(signX, signY, signZ, biomeStandingSignBlock, StructureVillageVN.getSignRotationMeta(signO, this.coordBaseMode, isHanging), 2); // 2 is "send change to clients without block update notification"
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
        			boolean isHanging=false;
        			
                    // Cobblestone foundation
        			this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, bannerU, bannerV-1, bannerW, structureBB);
        			
        			
        			int bannerX = this.getXWithOffset(bannerU, bannerW);
        			int bannerY = this.getYWithOffset(bannerV);
                    int bannerZ = this.getZWithOffset(bannerU, bannerW);
                    
                	// Set the banner and its orientation
    				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
    				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(bannerO, this.coordBaseMode, isHanging), 2);
    				
    				// Set the tile entity
    				TileEntity bannerTileEntity = world.getTileEntity(bannerX, bannerY, bannerZ);
    				if (bannerTileEntity==null) {bannerTileEntity = new TileEntityBanner();}
    				NBTTagCompound bannerNBTTagCompound = new NBTTagCompound();
    				bannerTileEntity.writeToNBT(bannerNBTTagCompound);
    				bannerNBTTagCompound.setBoolean("IsStanding", !isHanging);
    				
    				ItemStack bannerItemStack = ModObjects.chooseModBannerItem();
    				bannerItemStack.setTagInfo("BlockEntityTag", villageNBTtag.getCompoundTag("BlockEntityTag"));
    				
    				bannerNBTTagCompound = FunctionsVN.setItemValues(bannerItemStack, bannerNBTTagCompound);
    				bannerTileEntity.readFromNBT(bannerNBTTagCompound);
    				
    				world.setTileEntity(bannerX, bannerY, bannerZ, bannerTileEntity);
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
    
    public static class JungleGarden extends StructureVillageVN.StartVN
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
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	// Establish top and filler blocks, substituting Grass and Dirt if they're null
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
        	Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
            
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
        		blockObject = ModObjects.chooseModDioriteSlabBlock(false);
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
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.sand, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeSandBlock = (Block)blockObject[0]; int biomeSandMeta = (Integer)blockObject[1];
            for (int[] uuvvww : new int[][]{
            	{7,-1,4, 13,-1,4}, {7,-1,5, 7,-1,9}, {7,-1,10, 13,-1,10}, {13,-1,5, 13,-1,9}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeSandBlock, biomeSandMeta, biomeSandBlock, biomeSandMeta, false);
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
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
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
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
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
                boolean isHanging=false;
                
        		TileEntitySign signContents = StructureVillageVN.generateSignContents(namePrefix, nameRoot, nameSuffix);

    			world.setBlock(signX, signY, signZ, biomeStandingSignBlock, StructureVillageVN.getSignRotationMeta(signO, this.coordBaseMode, isHanging), 2); // 2 is "send change to clients without block update notification"
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
        			boolean isHanging=false;
        			
        			int bannerX = this.getXWithOffset(bannerU, bannerW);
        			int bannerY = this.getYWithOffset(bannerV);
                    int bannerZ = this.getZWithOffset(bannerU, bannerW);
                    
                	// Set the banner and its orientation
    				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
    				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(bannerO, this.coordBaseMode, isHanging), 2);
    				
    				// Set the tile entity
    				TileEntity bannerTileEntity = world.getTileEntity(bannerX, bannerY, bannerZ);
    				if (bannerTileEntity==null) {bannerTileEntity = new TileEntityBanner();}
    				NBTTagCompound bannerNBTTagCompound = new NBTTagCompound();
    				bannerTileEntity.writeToNBT(bannerNBTTagCompound);
    				bannerNBTTagCompound.setBoolean("IsStanding", !isHanging);
    				
    				ItemStack bannerItemStack = ModObjects.chooseModBannerItem();
    				bannerItemStack.setTagInfo("BlockEntityTag", villageNBTtag.getCompoundTag("BlockEntityTag"));
    				
    				bannerNBTTagCompound = FunctionsVN.setItemValues(bannerItemStack, bannerNBTTagCompound);
    				bannerTileEntity.readFromNBT(bannerNBTTagCompound);
    				
    				world.setTileEntity(bannerX, bannerY, bannerZ, bannerTileEntity);
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
    
    public static class JungleVilla extends StructureVillageVN.StartVN
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
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	// Establish top and filler blocks, substituting Grass and Dirt if they're null
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
        	Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
            
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
        	
            
        	// Cobblestone stairs
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
        		{8,1,11, 3, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{9,1,11, 0, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		
        		// Back right
        		{11,1,10, 3, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{12,1,10, 2, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{11,1,11, 0, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{12,1,11, 1, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
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
            	{11,8,11, 11,8,11}, {12,8,10, 12,8,10},
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
            	{11,8,4, 13,8,4}, {11,8,6, 13,8,6}, {11,8,8, 13,8,8}, {11,8,10, 11,8,10}, {13,8,10, 13,8,10}, {11,8,12, 11,8,12}, {13,8,12, 13,8,12}, 
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
        	
            
            // Table
            for (int[] uuvvwwo : new int[][]{
        		{13,5,12, 1}, 
        		{13,5,6, 1}, 
        		})
            {
            	Object[][] tableComponentObjects = ModObjects.chooseModWoodenTable(biomePlankBlock==Blocks.planks ? biomePlankMeta : 0, uuvvwwo[3], this.coordBaseMode);
        		for (int i=1; i>=0; i--)
        		{
        			this.placeBlockAtCurrentPosition(world, (Block)tableComponentObjects[i][0], (Integer)tableComponentObjects[i][1], uuvvwwo[0], uuvvwwo[1]+1-i, uuvvwwo[2], structureBB);
        		}
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
        	for (int[] uvw : new int[][]{
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
    		int randomPottedPlant = random.nextInt(10)-1;
    		if (randomPottedPlant==-1) {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.yellow_flower, 0);} // Dandelion specifically
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
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo(ChestLootHandler.getGenericLootForVillageType(this.villageType));
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
                boolean isHanging=false;
                
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
        		
        		
    			world.setBlock(signX, signY, signZ, biomeStandingSignBlock, StructureVillageVN.getSignRotationMeta(signO, this.coordBaseMode, isHanging), 2); // 2 is "send change to clients without block update notification"
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
        			boolean isHanging=true;
        			
        			int bannerX = this.getXWithOffset(bannerU, bannerW);
        			int bannerY = this.getYWithOffset(bannerV);
                    int bannerZ = this.getZWithOffset(bannerU, bannerW);
                    
                	// Set the banner and its orientation
    				world.setBlock(bannerX, bannerY, bannerZ, testForBanner);
    				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(bannerO, this.coordBaseMode, isHanging), 2);
    				
    				// Set the tile entity
    				TileEntity bannerTileEntity = world.getTileEntity(bannerX, bannerY, bannerZ);
    				if (bannerTileEntity==null) {bannerTileEntity = new TileEntityBanner();}
    				NBTTagCompound bannerNBTTagCompound = new NBTTagCompound();
    				bannerTileEntity.writeToNBT(bannerNBTTagCompound);
    				bannerNBTTagCompound.setBoolean("IsStanding", !isHanging);
    				
    				ItemStack bannerItemStack = ModObjects.chooseModBannerItem();
    				bannerItemStack.setTagInfo("BlockEntityTag", villageNBTtag.getCompoundTag("BlockEntityTag"));
    				
    				bannerNBTTagCompound = FunctionsVN.setItemValues(bannerItemStack, bannerNBTTagCompound);
    				bannerTileEntity.readFromNBT(bannerNBTTagCompound);
    				
    				world.setTileEntity(bannerX, bannerY, bannerZ, bannerTileEntity);
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
    
    
    
    // ------------------ //
    // --- Components --- //
    // ------------------ //
    
    
    // --- Armorer House --- //
    // designed by jss2a98aj
    
    public static class JungleArmorerHouse extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"  FFFFF  ", 
    			" FFFFFFF ", 
    			"FFFFFFFFF", 
    			"FFFFFFFFF", 
    			"FFFFFFFFF", 
    			"FFFFFFFFF", 
    			" FFFFFFF ", 
    			"  FPPPF  ", 
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 7;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 2;
    	private static final int DECREASE_MAX_U = 2;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleArmorerHouse() {}

        public JungleArmorerHouse(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleArmorerHouse buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleArmorerHouse(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			// Floor
    			{0,0,3, 0,0,5}, 
    			{1,0,2, 1,0,6}, 
    			{2,0,1, 2,0,7}, 
    			{3,0,1, 3,0,2}, {3,0,5, 3,0,7}, 
    			{4,0,1, 4,0,2}, {4,0,6, 4,0,7}, 
    			{5,0,1, 5,0,3}, {5,0,6, 5,0,7}, 
    			{6,0,1, 6,0,7}, 
    			{7,0,2, 7,0,6}, 
    			{8,0,3, 8,0,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
            // Terracotta
        	for (int[] uuvvww : new int[][]{
        		// Front wall
        		{2,1,1, 2,2,1}, {4,1,1, 4,2,1}, {6,1,1, 6,2,1}, {2,3,1, 6,3,1}, 
        		// Left wall 
        		{0,1,3, 0,3,3}, {0,1,5, 0,3,5}, {0,1,4, 0,1,4}, {0,3,4, 0,3,4}, 
        		// Right wall 
        		{8,1,3, 8,3,3}, {8,1,5, 8,3,5}, {8,1,4, 8,1,4}, {8,3,4, 8,3,4}, 
        		// Back wall 
        		{2,1,7, 2,3,7}, {6,1,7, 6,3,7}, 
        		})
            {
        		// White
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor : 0),
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor : 0), 
        				false);
            }
    		
    		
    		// For stripped logs specifically
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
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
    		for (int[] uuvvww : new int[][]{
    			// Corner pillars
    			{1,1,6, 1,3,6}, {7,1,6, 7,3,6}, 
    			{1,1,2, 1,3,2}, {7,1,2, 7,3,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
    		
    		
    		// Logs (Across)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Ceiling support
    			{2,4,6, 6,4,6}, 
    			{2,4,2, 6,4,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, false);	
    		}
    		
    		
    		// Logs (Along)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Ceiling support
    			{1,4,3, 1,4,5}, 
    			{4,4,3, 4,4,5}, 
    			{7,4,3, 7,4,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAlongBlock, biomeLogHorAlongMeta, biomeLogHorAlongBlock, biomeLogHorAlongMeta, false);	
    		}
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Front lantern mount
    			{4,4,1, 4,4,1}, 
    			// Roof
    			{3,6,4, 5,6,4}, 
    			// Abutting the chimney
    			{4,5,6, 4,5,6}, 
    			// Top of stripped log columns
    			{1,4,6, 1,4,6}, {7,4,6, 7,4,6}, 
    			{1,4,2, 1,4,2}, {7,4,2, 7,4,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Exterior
    			// Front
    			{1,4,1, 3}, {2,4,1, 3}, {3,4,1, 3}, {5,4,1, 3}, {6,4,1, 3}, {7,4,1, 3}, 
    			{1,5,2, 3}, {2,5,2, 3}, {3,5,2, 3}, {4,5,2, 3}, {5,5,2, 3}, {6,5,2, 3}, {7,5,2, 3}, 
    			// Left
    			{0,4,2, 0}, {0,4,3, 0}, {0,4,4, 0}, {0,4,5, 0}, {0,4,6, 0}, 
    			{1,5,3, 0}, {1,5,4, 0}, {1,5,5, 0}, 
    			// Right
    			{8,4,2, 1}, {8,4,3, 1}, {8,4,4, 1}, {8,4,5, 1}, {8,4,6, 1}, 
    			{7,5,3, 1}, {7,5,4, 1}, {7,5,5, 1}, 
    			// Back
    			{1,4,7, 2}, {2,4,7, 2}, {3,4,7, 2}, {5,4,7, 2}, {6,4,7, 2}, {7,4,7, 2}, 
    			{1,5,6, 2}, {2,5,6, 2}, {3,5,6, 2}, {5,5,6, 2}, {6,5,6, 2}, {7,5,6, 2}, 
    			// Interior
    			{2,5,3, 1+4}, {2,5,4, 1+4}, {2,5,5, 1+4}, 
    			{6,5,3, 0+4}, {6,5,4, 0+4}, {6,5,5, 0+4}, 
    			// Desks
    			{1,1,3, 2+4}, {1,1,5, 3+4}, 
    			{7,1,3, 2+4}, {7,1,5, 3+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{2,6,5, 6,6,5}, 
    			{2,6,4, 2,6,4}, {6,6,4, 6,6,4}, 
    			{2,6,3, 6,6,3}, 
    			// Tables
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Desks
    			{1,1,4, 1,1,4}, {7,1,4, 7,1,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uuvvww : new int[][]{
        		{4,4,0, 4,4,0}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
    		
    		
    		// Stone Brick
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickBlock = (Block)blockObject[0]; int biomeStoneBrickMeta = (Integer)blockObject[1];
    		for (int[] uuvvww : new int[][]{
    			// Furnace
    			{3,1,6, 3,1,6}, {4,2,6, 4,2,6}, {5,1,6, 5,1,6}, {3,1,7, 5,3,7}, 
    			// Chimney
    			{4,4,7, 4,6,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickBlock, biomeStoneBrickMeta, biomeStoneBrickBlock, biomeStoneBrickMeta, false);
    		}
    		
    		
    		// Stone Brick stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_brick_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Furnace
    			{3,2,6, 0}, {4,3,6, 3}, {5,2,6, 1}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeStoneBrickStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
            
            
            // Trapdoor (Top Horizontal)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapdoor, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	// Shelves
            	{2,2,6, 2}, {6,2,6, 2}, 
            	{2,1,6, 2}, {6,1,6, 2}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, true, false), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		// Front door
        		{4,3,0}, 
        		// Interior
        		{4,3,4}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
        		// Left wall
        		{0,2,4}, 
        		// Right wall
        		{8,2,4}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
            // Glazed terracotta
        	Object[] tryGlazedTerracotta;
        	for (int[] uvwoc : new int[][]{ // u,v,w, orientation, dye color
        		// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
        		
        		// Floor
        		{3,0,4, 0, GeneralConfig.useVillageColors ? this.townColor5 : 1}, // Orange
        		{5,0,4, 0, GeneralConfig.useVillageColors ? this.townColor5 : 1}, // Orange
        		{4,0,4, 1, GeneralConfig.useVillageColors ? this.townColor5 : 1}, // Orange
        		{4,0,3, 2, GeneralConfig.useVillageColors ? this.townColor5 : 1}, // Orange
        		{4,0,5, 2, GeneralConfig.useVillageColors ? this.townColor5 : 1}, // Orange
        		{3,0,3, 3, GeneralConfig.useVillageColors ? this.townColor5 : 1}, // Orange
        		{5,0,5, 3, GeneralConfig.useVillageColors ? this.townColor5 : 1}, // Orange
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
            
            
            // Blast Furnace - this is a TileEntity and needs to have its meta assigned manually
        	for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
        		{4,1,6, 2}, 
        		})
            {
        		blockObject = ModObjects.chooseModBlastFurnaceBlock(uvw[3], this.coordBaseMode); Block blastFurnaceBlock = (Block) blockObject[0]; int blastFurnaceMeta = (Integer) blockObject[1];
                this.placeBlockAtCurrentPosition(world, blastFurnaceBlock, 0, uvw[0], uvw[1], uvw[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvw[0], uvw[2]), this.getYWithOffset(uvw[1]), this.getZWithOffset(uvw[0], uvw[2]), blastFurnaceMeta, 2);
            }
    		
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{3,1,1, 2, 1, 1}, 
    			{5,1,1, 2, 1, 0}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
        	
        	
			// Solid color banners
    		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
    		if (testForBanner!=null)
			{
    			for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color
    				// 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    				
    				{2,4,4, 1, GeneralConfig.useVillageColors ? this.townColor5 : 1}, // Orange
    				{6,4,4, 3, GeneralConfig.useVillageColors ? this.townColor5 : 1}, // Orange
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
    				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(uvwoc[3], this.coordBaseMode, true), 2);
    				
    				// Set the tile entity
//    				TileEntity tilebanner = new TileEntityBanner();
//    				NBTTagCompound modifystanding = new NBTTagCompound();
//    				tilebanner.writeToNBT(modifystanding);
//    				modifystanding.setBoolean("IsStanding", false);
//    				modifystanding.setInteger("Base", uvwoc[4]);
//    				tilebanner.readFromNBT(modifystanding);
//    				
//            		world.setTileEntity(bannerX, bannerY, bannerZ, tilebanner);
    				
    				// Set the tile entity
    				TileEntity bannerTileEntity = world.getTileEntity(bannerX, bannerY, bannerZ);
    				if (bannerTileEntity==null) {bannerTileEntity = new TileEntityBanner();}
    				NBTTagCompound bannerNBTTagCompound = new NBTTagCompound();
    				bannerTileEntity.writeToNBT(bannerNBTTagCompound);
    				bannerNBTTagCompound.setBoolean("IsStanding", !isHanging);
    				bannerNBTTagCompound.setInteger("Base", uvwoc[4]);
    				bannerTileEntity.readFromNBT(bannerNBTTagCompound);
    				
    				world.setTileEntity(bannerX, bannerY, bannerZ, bannerTileEntity);
    			}
			}
        	
        	
        	// Leaves
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.leaves, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLeafBlock = (Block)blockObject[0]; int biomeLeafMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		// Front shrubs
        		{0,1,2, 0,1,2}, {1,1,1, 1,1,1}, {2,1,0, 2,1,0}, 
        		{8,1,2, 8,1,2}, {7,1,1, 7,1,1}, {6,1,0, 6,1,0}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeLeafBlock, biomeLeafMeta,
        				biomeLeafBlock, biomeLeafMeta, 
        				false);
            }
        	
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward, 3:leftward
            		// Forward
        			{2,1,8, 0}, 
        			{3,3,8, 0}, {3,2,8, 0}, {3,1,8, 0}, 
        			{4,4,8, 0}, {4,3,8, 0}, 
        			{7,2,7, 0}, {7,1,7, 0}, 
        			// Rightward
        			{8,3,6, 1}, {8,2,6, 1}, {8,1,6, 1}, 
        			// Backward
        			{0,3,2, 2}, {7,3,1, 2}, {7,2,1, 2}, 
        			// Leftward
        			{1,2,1, 3}, 
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
    			int u = 2+random.nextInt(5);
    			int v = 1;
    			int w = 2+random.nextInt(4);
    			
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
    // designed by Lonemind
    
    public static class JungleButcherShop extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"FFFFFFFFFF", 
    			"FFFFFFFFFF", 
    			"FFPFFFFFFF", 
    			"FFFFPPFFFF", 
    			"FFFFFFFPFF", 
    			"FFFPFFFFFF", 
    			"FFPFFFFFF ", 
    			"FFFFFFFFF ", 
    			"FFFFFFFFF ", 
    			"FFFFFFFFF ", 
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 11;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 4;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleButcherShop() {}

        public JungleButcherShop(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleButcherShop buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleButcherShop(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
        	
        	// Cobblestone wall
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
            	// Pen
        		{2,1,1, 4,1,1}, 
        		{1,1,2, 1,1,7}, 
        		{2,1,8, 7,1,8}, 
        		{8,1,5, 8,1,7}, 
        		{6,1,4, 7,1,4}, 
        		// Smoker chimney
        		{2,6,7, 2,7,7}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, false);
            }
    		
    		
    		// Cobblestone stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Chimney
    			{2,9,6, 3}, {1,9,7, 0}, {2,9,8, 2}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uuvvww : new int[][]{
        		// Torch posts
        		{4,1,5, 4,1,5}, {5,1,0, 5,1,0}, {8,1,0, 8,1,0}, 
        		// Windows
        		{2,6,1, 4,6,1}, 
        		{1,6,3, 1,6,6}, 
        		{3,6,8, 6,6,8}, 
        		{5,6,2, 5,6,3}, 
        		{8,6,6, 8,6,6}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
            
            
            // Smooth Stone Double Slab
            for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	{2,5,2}, {3,5,2}, {4,5,2}, 
            	{4,5,3}, {4,5,4}, {4,5,6}, {4,5,7}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
    		
    		
    		// Logs (Vertical) Part 1
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{5,1,4, 5,7,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);	
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// On fence posts
    			{4,2,5, -1}, {5,2,0, -1}, {8,2,0, -1}, 
    			// On counter
    			{4,6,4, 3}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			// Foundation
    			{5,1,2, 5,1,3}, 
    			// Chimney
    			{1,8,7, 1,8,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Logs (Vertical) Part 2
    		for(int[] uuvvww : new int[][]{
    			// Corner beams
    			{1,1,8, 1,7,8}, {8,1,8, 8,7,8}, 
    			{8,1,4, 8,7,4}, 
    			{1,1,1, 1,7,1}, {5,1,1, 5,7,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);	
    		}
    		
    		
    		// Logs (Across)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// House support
    			{2,4,8, 7,4,8}, 
    			{2,4,1, 4,4,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, false);	
    		}
    		
    		
    		// Logs (Along)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Ceiling support
    			{1,4,2, 1,4,7}, 
    			{5,4,2, 5,4,3}, {8,4,5, 8,4,7}, 
    			// Ceiling singletons
    			{3,8,1, 3,8,1}, 
    			{3,8,8, 3,8,8}, {6,8,8, 6,8,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAlongBlock, biomeLogHorAlongMeta, biomeLogHorAlongBlock, biomeLogHorAlongMeta, false);	
    		}
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Under the house
    			{5,2,2, 5,3,3}, 
    			// Floor
    			{2,4,2, 4,4,7}, {5,4,5, 7,4,7}, 
    			// Window rim
    			{1,5,2, 1,5,7}, {1,6,2, 1,6,2}, {1,6,7, 1,6,7}, {1,7,2, 1,7,7}, 
    			{2,5,1, 4,5,1}, {2,8,1, 2,8,1}, {4,8,1, 4,8,1}, {2,7,1, 4,7,1}, 
    			{5,5,2, 5,5,3}, {5,7,2, 5,7,3}, 
    			{8,5,5, 8,5,7}, {8,6,5, 8,6,5}, {8,6,7, 8,6,7}, {8,7,5, 8,7,7}, 
    			{2,5,8, 7,5,8}, {2,6,8, 2,6,8}, {7,6,8, 7,6,8}, {2,7,8, 7,7,8}, {2,8,8, 2,8,8}, {4,8,8, 5,8,8}, {7,8,8, 7,8,8}, 
    			// Ceiling
    			{2,8,2, 4,8,6}, {2,8,8, 2,8,8}, {5,8,5, 7,8,7}, {3,8,7, 4,8,7}, 
    			// Roof
    			{3,9,0, 3,9,5}, {3,9,6, 6,9,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Entry
    			{6,4,4, 3}, {7,4,4, 3}, 
    			{6,3,3, 3}, {7,3,3, 3}, 
    			{6,2,2, 3}, {7,2,2, 3}, 
    			{6,1,1, 3}, {7,1,1, 3}, 
    			// Underneath entry
    			{6,1,2, 2+4}, {7,1,2, 2+4}, 
    			{6,2,3, 2+4}, {7,2,3, 2+4}, 
    			{6,3,4, 2+4}, {7,3,4, 2+4}, 
    			// Interior ceiling trim
    			{2,7,2, 2+4}, {3,7,2, 2+4}, {4,7,2, 2+4}, 
    			{4,7,3, 0+4}, {4,7,4, 0+4}, 
    			{4,7,5, 2+4}, {5,7,5, 2+4}, {6,7,5, 2+4}, 
    			{7,7,6, 0+4}, {7,7,5, 0+4}, 
    			{3,7,7, 3+4}, {4,7,7, 3+4}, {5,7,7, 3+4}, {6,7,7, 3+4}, {7,7,7, 3+4}, 
    			{2,7,6, 1+4}, {2,7,5, 1+4}, {2,7,4, 1+4}, {2,7,3, 1+4}, 
    			// Roof
    			{0,7,0, 0}, {0,7,1, 0}, {0,7,2, 0}, {0,7,3, 0}, {0,7,4, 0}, {0,7,5, 0}, {0,7,6, 0}, {0,7,7, 0}, {0,7,8, 0}, {0,7,9, 0}, 
    			{1,8,0, 0}, {1,8,1, 0}, {1,8,2, 0}, {1,8,3, 0}, {1,8,4, 0}, {1,8,5, 0}, {1,8,6, 0}, {1,8,8, 0}, {1,8,9, 0}, 
    			{6,7,0, 1}, {6,7,1, 1}, {6,7,2, 1}, {6,7,3, 1}, 
    			{5,8,0, 1}, {5,8,1, 1}, {5,8,2, 1}, {5,8,3, 1}, {5,8,4, 1}, 
    			{7,7,3, 3}, {8,7,3, 3}, {9,7,3, 3}, 
    			{6,8,4, 3}, {7,8,4, 3}, {8,8,4, 3}, 
    			{9,7,4, 1}, {9,7,5, 1}, {9,7,6, 1}, {9,7,7, 1}, {9,7,8, 1}, {9,7,9, 1}, 
    			{8,8,5, 1}, {8,8,6, 1}, {8,8,7, 1}, {8,8,8, 1}, {8,8,9, 1}, 
    			// Roof trim
    			{1,7,0, 1+4}, {2,8,0, 1+4}, {4,8,0, 0+4}, {5,7,0, 0+4}, // Front
    			{1,7,9, 1+4}, {2,8,9, 1+4}, {7,8,9, 0+4}, {8,7,9, 0+4}, // Rear
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{2,9,0, 2,9,5}, 
    			{2,9,9, 7,9,9}, 
    			{7,9,5, 7,9,8}, 
    			{4,9,5, 6,9,5}, 
    			{4,9,0, 4,9,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Put in place so the butcher can't jump the counter and run away
    			{4,7,6, 4,7,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
            
            
            // Trapdoor (Top Horizontal)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapdoor, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	// Counter
            	{4,5,5, 2}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, true, false), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
            
            // Trapdoor (Bottom Vertical)
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	// Chimney
            	{2,10,6, 2}, 
            	{3,10,7, 1}, 
            	{2,10,8, 0}, 
            	{1,10,7, 3}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, false, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
                    	
            // Table
            for (int[] uuvvwwo : new int[][]{
        		{2,5,5, 1}, 
        		})
            {
            	Object[][] tableComponentObjects = ModObjects.chooseModWoodenTable(biomePlankBlock==Blocks.planks ? biomePlankMeta : 0, uuvvwwo[3], this.coordBaseMode);
        		for (int i=1; i>=0; i--)
        		{
        			this.placeBlockAtCurrentPosition(world, (Block)tableComponentObjects[i][0], (Integer)tableComponentObjects[i][1], uuvvwwo[0], uuvvwwo[1]+1-i, uuvvwwo[2], structureBB);
        		}
            }
            
            
            // Smoker
        	blockObject = ModObjects.chooseModSmokerBlock(3, this.coordBaseMode); Block smokerBlock = (Block) blockObject[0];
            for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{2,5,7, 2}
            	})
            {
                this.placeBlockAtCurrentPosition(world, smokerBlock, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2]), StructureVillageVN.chooseFurnaceMeta(uvwo[3], this.coordBaseMode), 2);
            }
    		
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{6,5,5, 2, 1, 1}, 
    			{7,5,5, 2, 1, 0}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
        	
        	
        	// Leaves
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.leaves, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLeafBlock = (Block)blockObject[0]; int biomeLeafMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		// Animal pen
        		{4,1,3, 4,1,4}, 
        		{4,2,4, 4,2,4}, 
        		{5,1,5, 5,1,5}, 
        		// Shrub
        		{8,1,1, 8,1,3}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeLeafBlock, biomeLeafMeta,
        				biomeLeafBlock, biomeLeafMeta, 
        				false);
            }
        	
        	
        	// Dirt
        	for (int[] uuvvww : new int[][]{
        		{2,0,5, 2,0,5}, 
        		{3,0,6, 3,0,6}, 
        		{6,0,5, 6,0,5}, {6,0,7, 6,0,7}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeDirtBlock, biomeDirtMeta,
        				biomeDirtBlock, biomeDirtMeta, 
        				false);
            }
            
            
            // Moist Farmland
            for(int[] uuvvww : new int[][]{
            	{2,0,6, 2,0,6}, 
        		{3,0,7, 4,0,7}, 
        		{7,0,6, 7,0,6}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.farmland, 7, Blocks.farmland, 7, false);	
            }
        	
            
        	// Crops - carrot
            for(int[] uuvvww : new int[][]{
        		{7,1,6, 7,1,6}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.carrots, 7, Blocks.carrots, 7, false);
            }
            
            
        	// Water
            for (int[] uvw : new int[][]{
        		{6,0,6}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.flowing_water, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Campfires
    		blockObject = ModObjects.chooseModCampfireBlock(random.nextInt(4), this.coordBaseMode); Block campfireBlock = (Block) blockObject[0]; int campfireMeta = (Integer) blockObject[1];
    		for(int[] uvw : new int[][]{
    			{2,8,7}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, campfireBlock, campfireMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
        	
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1: rightward, 2:backward, 3: leftward
            		// Front side
        			{1,3,0, 2}, {1,4,0, 2}, {1,5,0, 2}, {1,6,0, 2}, 
        			{2,2,0, 2}, {2,3,0, 2}, {2,4,0, 2}, 
        			{4,5,0, 2}, {4,6,0, 2}, {4,7,0, 2}, 
        			{5,3,0, 2}, {5,4,0, 2}, {5,5,0, 2}, 
        			{8,4,3, 2}, {8,5,3, 2}, {8,6,3, 2}, 
        			// Left side
        			{0,5,1, 3}, {0,6,1, 3}, 
        			{0,4,2, 3}, {0,5,2, 3}, {0,6,2, 3}, 
        			{0,2,3, 3}, {0,3,3, 3}, {0,4,3, 3}, {0,5,3, 3}, 
        			{0,3,6, 3}, {0,4,6, 3}, {0,5,6, 3}, 
        			{0,5,7, 3}, {0,6,7, 3}, 
        			// Back side
        			{2,3,9, 0}, {2,4,9, 0}, {2,5,9, 0}, {2,6,9, 0}, 
        			{3,5,9, 0}, {3,6,9, 0}, {3,7,9, 0}, {3,8,9, 0}, 
        			{6,2,9, 0}, {6,3,9, 0}, {6,4,9, 0}, 
        			{7,5,9, 0}, 
        			{8,5,9, 0}, {8,6,9, 0}, 
        			// Right side
        			{6,2,1, 1}, {6,3,1, 1}, 
        			{6,4,2, 1}, {6,5,2, 1}, 
        			{9,3,5, 1}, {9,4,5, 1}, {9,5,5, 1}, 
        			{9,3,8, 1}, {9,4,8, 1}, {9,5,8, 1}, 
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
    			
    			// Villager
    			int u = 2;
    			int v = 5;
    			int w = 5;
    			
    			while (u==2 && (w==5 || w==7))
    			{
    				u=2+random.nextInt(2); w=3+random.nextInt(5);
    			}
    			
    			EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 4, 1, 0); // Butcher
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
    			world.spawnEntityInWorld(entityvillager);
    			
            	if (VillageGeneratorConfigHandler.villageAnimalRestrictionLevel<1)
            	{
	                // Pigs in the yard
	            	for (int[] uvw : new int[][]{
	        			{3,1,5},
	        			{5,1,6},
	        			})
	        		{
	            		EntityLiving animal = new EntityPig(world);
	            		IEntityLivingData ientitylivingdata = animal.onSpawnWithEgg(null); // To give the animal random spawning properties (horse pattern, sheep color, etc)
	            		
	                    animal.setLocationAndAngles((double)this.getXWithOffset(uvw[0], uvw[2]) + 0.5D, (double)this.getYWithOffset(uvw[1]) + 0.5D, (double)this.getZWithOffset(uvw[0], uvw[2]) + 0.5D, random.nextFloat()*360F, 0.0F);
	                    world.spawnEntityInWorld(animal);
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
        protected int getVillagerType (int number) {return 4;}
    }
    
    
    // --- Cartographer House 1 --- //
    // designed by jss2a98aj
    
    public static class JungleCartographerHouse1 extends StructureVillageVN.VNComponent
    {
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
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 7;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1 + 2; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 1;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 1;
    	
        public JungleCartographerHouse1() {}

        public JungleCartographerHouse1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleCartographerHouse1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleCartographerHouse1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			// Feet
    			{0,0,5, 1,0,5}, {1,0,6, 1,0,6},   {5,0,5, 6,0,5}, {5,0,6, 5,0,6}, 
    			{0,0,1, 1,0,1}, {1,0,0, 1,0,0},   {5,0,1, 6,0,1}, {5,0,0, 5,0,0}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		// Foot foundation
    		for(int[] uvw : new int[][]{
    			// Feet
    			{0,-1,5}, {0,-1,6}, {1,-1,5}, {1,-1,6},   {5,-1,5}, {5,-1,6}, {6,-1,5}, {6,-1,6}, 
    			{0,-1,0}, {0,-1,1}, {1,-1,0}, {1,-1,1},   {0,-1,5}, {0,-1,6}, {1,-1,5}, {1,-1,6}, 
    			})
    		{
    			this.func_151554_b(world, biomeCobblestoneBlock, biomeCobblestoneMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Cobblestone Slab (lower)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabLowerBlock = (Block)blockObject[0]; int biomeCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Feet
    			{0,0,6, 0,0,6}, {6,0,6, 6,0,6}, 
    			{0,0,0, 0,0,0}, {6,0,0, 6,0,0}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, false);	
    		}
    		
    		
    		// Logs (Vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Top of front columns
    			{5,1,1, 5,1,1}, 
    			{1,1,5, 1,1,5}, 
    			{5,1,5, 5,1,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);	
    		}
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Floor
    			{2,1,2, 4,1,4}, 
    			// Platform
    			{3,2,6, 6,2,6}, {5,2,5, 5,2,5}, {6,2,3, 6,2,5}, 
    			// Front wall
    			{3,2,1, 3,4,1}, {4,2,1, 4,2,1}, {4,4,1, 4,4,1}, {5,2,1, 5,4,1}, 
    			// Front-right corner
    			{6,2,2, 6,5,2}, 
    			// Right wall
    			{7,3,3, 7,5,3}, {7,3,4, 7,3,6}, {7,5,4, 7,5,6}, {7,3,7, 7,5,7}, 
    			// Back wall
    			{3,3,7, 3,5,7}, {4,3,7, 6,3,7}, {4,5,7, 6,5,7},
    			// Left-back corner
    			{2,2,6, 2,5,6}, 
    			// Left wall
    			{1,2,3, 1,4,3}, {1,2,4, 1,2,4}, {1,4,4, 1,4,4}, {1,2,5, 1,4,5}, 
    			// Roof
    			{2,5,2, 5,5,2}, {2,5,3, 2,5,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Above entryway
    			{1,4,2, 0+4}, {2,4,1, 3+4},  
    			// Interior split-level stairs
    			{4,2,4, 3}, {5,2,4, 3}, {4,2,5, 0}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Entry steps
    			{1,1,1, 1,1,1}, 
    			// Roof trim
    			{1,5,1, 5,5,1}, {1,5,2, 1,5,5}, 
    			{3,6,3, 7,6,3}, {3,6,4, 3,6,6}, {7,6,4, 7,6,6}, {3,6,7, 7,6,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Above doors
    			{1,4,1, 1,4,1}, 
    			// Outside trimming
    			{2,1,1, 4,1,1}, {2,1,5, 4,1,5}, 
    			{1,1,2, 1,1,4}, {5,1,2, 5,1,4}, 
    			{7,2,3, 7,2,7}, {3,2,7, 6,2,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	{3,5,3}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
        		// Front wall
        		{4,3,1},  
        		// Back wall
        		{4,4,7}, {5,4,7}, {6,4,7}, 
        		// Left wall
        		{1,3,4},  
        		// Right wall
        		{7,4,4}, {7,4,5}, {7,4,6}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Glass blocks
        	for (int[] uuvvww : new int[][]{
        		// Sunroof
        		{4,6,4, 6,6,6}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.glass, 0, Blocks.glass, 0, false);
            }
            
        	
        	// Cartography Table
        	blockObject = ModObjects.chooseModCartographyTable(biomePlankMeta); Block cartographyTableBlock = (Block) blockObject[0]; int cartographyTableMeta = (Integer) blockObject[1];
            for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
        		{5,3,5}, 
           		})
        	{
            	this.placeBlockAtCurrentPosition(world, cartographyTableBlock, cartographyTableMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{2,2,1, 0, 1, 1}, 
    			{1,2,2, 1, 1, 0}, 
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
        		{3,3,6, 3,3,6}, {6,3,3, 6,3,3}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeBookshelfBlock, biomeBookshelfMeta, biomeBookshelfBlock, biomeBookshelfMeta, false);
            }
            
            
        	// Carpet
        	for(int[] uvwm : new int[][]{
        		// Lower
        		{3,2,3, (GeneralConfig.useVillageColors ? this.townColor4 : 9)}, // 9 is cyan
        		{4,2,3, (GeneralConfig.useVillageColors ? this.townColor : 0)}, // 0 is white
        		{5,2,3, (GeneralConfig.useVillageColors ? this.townColor4 : 9)}, // 9 is cyan
        		{3,2,4, (GeneralConfig.useVillageColors ? this.townColor : 0)}, // 0 is white
        		{3,2,5, (GeneralConfig.useVillageColors ? this.townColor4 : 9)}, // 9 is cyan
        		// Upper
        		{4,3,6, (GeneralConfig.useVillageColors ? this.townColor4 : 9)}, // 9 is cyan
        		{5,3,6, (GeneralConfig.useVillageColors ? this.townColor : 0)}, // 0 is white
        		{6,3,6, (GeneralConfig.useVillageColors ? this.townColor4 : 9)}, // 9 is cyan
        		{6,3,5, (GeneralConfig.useVillageColors ? this.townColor : 0)}, // 0 is white
        		{6,3,4, (GeneralConfig.useVillageColors ? this.townColor4 : 9)}, // 9 is cyan
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.carpet, uvwm[3], uvwm[0], uvwm[1], uvwm[2], structureBB); 
            }
        	
        	
        	// Crafting Table
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.crafting_table, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCraftingTableBlock = (Block)blockObject[0]; int biomeCraftingTableMeta = (Integer)blockObject[1];
            for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
        		{5,2,2}, 
           		})
        	{
            	this.placeBlockAtCurrentPosition(world, biomeCraftingTableBlock, biomeCraftingTableMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
    		
            // Flower Pot
            for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
        		{3,4,6}, 
        		{6,4,3}, 
           		})
        	{
                int x = this.getXWithOffset(uvw[0], uvw[2]);
                int y = this.getYWithOffset(uvw[1]);
                int z = this.getZWithOffset(uvw[0], uvw[2]);
                
                Object[] cornflowerObject = ModObjects.chooseModCornflower(); Object[] lilyOfTheValleyObject = ModObjects.chooseModLilyOfTheValley();
        		int randomPottedPlant = random.nextInt(10)-1;
        		if (randomPottedPlant==-1) {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.yellow_flower, 0);} // Dandelion specifically
        		else {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.red_flower, randomPottedPlant);}          // Every other type of flower
            }
        	
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward, 3:leftward
            		// Left side
        			{0,2,4, 3}, {0,1,4, 3}, 
        			// Back side
        			{6,3,8, 0}, {6,2,8, 0}, {6,1,8, 0}, 
        			// Corner
        			{6,4,1, 2}, {6,3,1, 2}, {6,2,1, 2}, 
        			{7,5,2, 1}, {7,4,2, 1}, {7,3,2, 1}, {7,2,2, 1}, {7,1,2, 1}, 
            		})
                {
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
        			}
                }
        	}
            
            
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 2;
        	int chestV = 2;
        	int chestW = 5;
        	int chestO = 1; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo(Reference.VN_CARTOGRAPHER);
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
        	
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			// Villager
    			int s = random.nextInt(10);
    			
    			int u = s<3 ? 3 : s<5 ? s+1 : s<8 ? 6 : 13-s;
    			int v = s<5 ? 2 : 3;
    			int w = s<3 ? 5-s : s<5 ? 3 : s<8 ? s-1 : 6;
    			
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
    
    
    // --- Cartographer House 2 --- //
    // designed by AstroTibs
    
    public static class JungleCartographerHouse2 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"FFFFFFFFFF",
    			"FFFFFFFFFF",
    			"FFFFFPPPFF",
    			"FFFFFFFPFF",
    			"FFFFFFFPFF",
    			"FFFFFFFFFF",
    			"FFFFFFFFFF",
    			"FFPPPFFFFF",
    			"FFPFFFFFFF",
    			"FFPFFFFFFF",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 6;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 5;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleCartographerHouse2() {}

        public JungleCartographerHouse2(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleCartographerHouse2 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleCartographerHouse2(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Grass
    		for(int[] uuvvww : new int[][]{
    			// Front garden
    			{0,0,0, 1,0,4}, 
    			{2,0,3, 4,0,4}, 
    			{3,0,0, 4,0,1}, 
    			// Back garden
    			{5,0,8, 9,0,9}, 
    			{5,0,5, 6,0,6}, 
    			{8,0,5, 9,0,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);	
    		}
    		
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			{5,0,0, 5,0,4}, {6,0,0, 7,0,1}, {6,0,4, 7,0,4}, {8,0,0, 9,0,4}, 
    			{0,0,5, 0,0,9}, {1,0,5, 3,0,5}, {1,0,9, 3,0,9}, {4,0,5, 4,0,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Stripped logs (Vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
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
    		for (int[] uuvvww : new int[][]{
    			// Front-right
    			{5,1,4, 5,3,4}, {9,1,4, 9,3,4}, 
    			{5,1,0, 5,3,0}, {9,1,0, 9,3,0}, 
    			// Back-left
    			{0,1,9, 0,3,9}, {4,1,9, 4,3,9}, 
    			{0,1,5, 0,3,5}, {4,1,5, 4,3,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
    		
    		
    		// Concrete
    		Object[] tryConcrete = ModObjects.chooseModConcrete(GeneralConfig.useVillageColors ? townColor : 0); // White
        	Block concreteBlock = Blocks.stained_hardened_clay; int concreteMeta = GeneralConfig.useVillageColors ? townColor : 0; // White
        	if (tryConcrete != null) {concreteBlock = (Block) tryConcrete[0]; concreteMeta = (Integer) tryConcrete[1];}
    		for(int[] uuvvww : new int[][]{
    			// Left-back
    			{4,1,6, 4,3,6}, {4,3,7, 4,3,7}, {4,1,8, 4,3,8}, 
    			{1,1,9, 3,1,9}, {1,3,9, 3,3,9}, 
    			{0,1,6, 0,1,8}, {0,3,6, 0,3,8}, 
    			{1,1,5, 3,1,5}, {1,3,5, 3,3,5}, 
    			// Front-right
    			{6,1,4, 6,3,4}, {7,3,4, 7,3,4}, {8,1,4, 8,3,4}, 
    			{5,1,1, 5,3,1}, {5,3,2, 5,3,2}, {5,1,3, 5,3,3}, 
    			{9,1,1, 9,1,3}, {9,3,1, 9,3,3}, 
    			{6,1,0, 8,1,0}, {6,3,0, 8,3,0}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], concreteBlock, concreteMeta, concreteBlock, concreteMeta, false);	
    		}
    		
        	
        	// Terracotta
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.hardened_clay, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTerracottaBlock = (Block)blockObject[0]; int biomeTerracottaMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{0,4,5, 4,4,5}, {0,4,5, 0,4,9}, {0,4,9, 4,4,9}, {4,4,5, 4,4,9}, 
        		{5,4,0, 9,4,0}, {5,4,0, 5,4,4}, {5,4,4, 9,4,4}, {9,4,0, 9,4,4}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeTerracottaBlock, biomeTerracottaMeta, biomeTerracottaBlock, biomeTerracottaMeta, false);
            }
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			{4,3,2, 3}, 
    			{6,4,2, 1}, 
    			{5,3,7, 1}, 
    			{3,4,7, 3}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Counter
    			{6,1,1, 8,1,1}, {8,1,2, 8,1,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Back-left
    			{0,5,9, 2}, {1,5,9, 2}, {2,5,9, 2}, {3,5,9, 2}, {4,5,9, 2}, 
    			{0,5,8, 0}, {4,5,8, 1}, 
    			{0,5,7, 0}, {4,5,7, 1}, 
    			{0,5,6, 0}, {4,5,6, 1}, 
    			{0,5,5, 3}, {1,5,5, 3}, {2,5,5, 3}, {3,5,5, 3}, {4,5,5, 3}, 
    			// Front-right
    			{5,5,4, 2}, {6,5,4, 2}, {7,5,4, 2}, {8,5,4, 2}, {9,5,4, 2}, 
    			{5,5,3, 0}, {9,5,3, 1}, 
    			{5,5,2, 0}, {9,5,2, 1}, 
    			{5,5,1, 0}, {9,5,1, 1}, 
    			{5,5,0, 3}, {6,5,0, 3}, {7,5,0, 3}, {8,5,0, 3}, {9,5,0, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
            
            
            // Glass Panes
        	for (int[] uuvvww : new int[][]{
        		// Front-right
        		{9,2,1, 9,2,3}, 
        		{6,2,0, 8,2,0}, 
        		// Back-left
        		{1,2,9, 3,2,9}, 
        		{0,2,6, 0,2,8}, 
        		{1,2,5, 3,2,5}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.glass_pane, 0, Blocks.glass_pane, 0, false);
            }
            
            
            // Glass blocks
        	for (int[] uvw : new int[][]{
        		// Back-left
        		{1,5,8, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{2,5,8, GeneralConfig.useVillageColors ? this.townColor4 : 9}, // Cyan
        		{3,5,8, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{1,5,7, GeneralConfig.useVillageColors ? this.townColor4 : 9}, // Cyan
        		{2,5,7, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{3,5,7, GeneralConfig.useVillageColors ? this.townColor4 : 9}, // Cyan
        		{1,5,6, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{2,5,6, GeneralConfig.useVillageColors ? this.townColor4 : 9}, // Cyan
        		{3,5,6, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		// Front-right
        		{6,5,3, GeneralConfig.useVillageColors ? this.townColor5 : 1}, // Orange
        		{7,5,3, GeneralConfig.useVillageColors ? this.townColor3 : 14}, // Red
        		{8,5,3, GeneralConfig.useVillageColors ? this.townColor5 : 1}, // Orange
        		{6,5,2, GeneralConfig.useVillageColors ? this.townColor3 : 14}, // Red
        		{7,5,2, GeneralConfig.useVillageColors ? this.townColor5 : 1}, // Orange
        		{8,5,2, GeneralConfig.useVillageColors ? this.townColor3 : 14}, // Red
        		{6,5,1, GeneralConfig.useVillageColors ? this.townColor5 : 1}, // Orange
        		{7,5,1, GeneralConfig.useVillageColors ? this.townColor3 : 14}, // Red
        		{8,5,1, GeneralConfig.useVillageColors ? this.townColor5 : 1}, // Orange
        		})
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.stained_glass, uvw[3], uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
        	// Wool - carpet prevented villagers from going through the door
        	for(int[] uvwm : new int[][]{
        		// Back-left
        		{1,0,8, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{2,0,8, GeneralConfig.useVillageColors ? this.townColor4 : 9}, // Cyan
        		{3,0,8, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{1,0,7, GeneralConfig.useVillageColors ? this.townColor4 : 9}, // Cyan
        		{2,0,7, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{3,0,7, GeneralConfig.useVillageColors ? this.townColor4 : 9}, // Cyan
        		{1,0,6, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{2,0,6, GeneralConfig.useVillageColors ? this.townColor4 : 9}, // Cyan
        		{3,0,6, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		// Front-right
        		{6,0,3, GeneralConfig.useVillageColors ? this.townColor5 : 1}, // Orange
        		{7,0,3, GeneralConfig.useVillageColors ? this.townColor3 : 14}, // Red
        		{6,0,2, GeneralConfig.useVillageColors ? this.townColor3 : 14}, // Red
        		{7,0,2, GeneralConfig.useVillageColors ? this.townColor5 : 1}, // Orange
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.wool, uvwm[3], uvwm[0], uvwm[1], uvwm[2], structureBB); 
            }
        	
        	
    		// Potted sapling
        	for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
        		{8,2,1}, 
           		})
        	{
                int potted_sapling_x = this.getXWithOffset(uvw[0], uvw[2]);
                int potted_sapling_y = this.getYWithOffset(uvw[1]);
                int potted_sapling_z = this.getZWithOffset(uvw[0], uvw[2]);
                StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, potted_sapling_x, potted_sapling_y, potted_sapling_z, Blocks.sapling, biomePlankMeta);
        	}
        	
        	
        	// Cartography Table
        	blockObject = ModObjects.chooseModCartographyTable(biomePlankMeta); Block cartographyTableBlock = (Block) blockObject[0]; int cartographyTableMeta = (Integer) blockObject[1];
            for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
        		{1,1,7}, 
           		})
        	{
            	this.placeBlockAtCurrentPosition(world, cartographyTableBlock, cartographyTableMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{5,1,2, 3, 1, 0}, 
    			{7,1,4, 0, 1, 1}, 
    			{4,1,7, 1, 1, 0}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uuvvww : new int[][]{
        		{5,1,9, 9,1,9}, 
        		{9,1,5, 9,1,8}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
    		
        	
        	// Leaves
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.leaves, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLeafBlock = (Block)blockObject[0]; int biomeLeafMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		// Front shrubs
        		{1,1,4, 3,1,4}, 
        		// Back shrubs
        		{5,2,9, 9,2,9}, 
        		{9,2,5, 9,2,8}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeLeafBlock, biomeLeafMeta,
        				biomeLeafBlock, biomeLeafMeta, 
        				false);
            }
    		
    		
            // Poppies
            for (int[] uvw : new int[][]{
            	{5,1,8}, {7,1,8}, {8,1,7}, {8,1,5}, 
            	{5,1,6}, {6,1,5}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.red_flower, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Tall Grass
    		for (int[] uvwg : new int[][]{ // g is grass type
    			// Back garden
            	{6,1,8, 0}, {8,1,8, 0}, {8,1,6, 0}, 
            	{6,1,6, 0}, {5,1,5, 0}, 
            	// Front garden
            	{0,1,4, 0}, {4,1,4, 0}, 
            	{0,1,3, 0}, {1,1,3, 0}, {2,1,3, 0}, {3,1,3, 0}, {4,1,3, 0}, 
            	{0,1,2, 0}, {1,1,2, 0}, 
            	{0,1,1, 0}, {1,1,1, 0}, {3,1,1, 0}, {4,1,1, 0}, 
            	{0,1,0, 0}, {1,1,0, 0}, {3,1,0, 0}, {4,1,0, 0}, 
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
        	
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			// Villager
    			int s = random.nextInt(28);
    			
    			int u = s<=2 ? s+1 : s<=4 ? s-1 : s<=7 ? s-4 : s<=11 ? s-3 : s<=15 ? s-7 : s<=19 ? s-11 : s<=23 ? s-15 : s<=25 ? s-18 : s-20;
    			int v = 1;
    			int w = s<=2 ? 8 : s<=4 ? 7 : s<=7 ? 6 : s<=11 ? 8 : s<=15 ? 7 : s<=19 ? 6 : s<=23 ? 5 : s<=25 ? 3 : 2;
    			
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
    
    
    // --- Fisher Cottage --- //
    // designed by jss2a98aj
    
    public static class JungleFisherCottage extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"FFFFFFFFFF",
    			"FFFFFFFFFF",
    			"FFFFFFFFFF",
    			"FFFFFFFFFF",
    			"FFFFFFFFFF",
    			"FFFFFFFFF ",
    			"    FFFFP ",
    			"    FFFFP ",
    			"    FFPPP ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 5;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1 + 8; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 4;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 5;
    	
        public JungleFisherCottage() {}

        public JungleFisherCottage(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleFisherCottage buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleFisherCottage(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			// Floor
    			{0,0,3, 5,0,8}, 
    			// Front steps
    			{4,0,0, 5,0,2}, {6,0,1, 7,0,4}, {8,0,3, 8,0,5}, 
    			// Pool rim
    			{9,0,4, 9,0,8}, {6,0,8, 8,0,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Water
    		for(int[] uuvvww : new int[][]{
    			// Floor
    			{6,0,5, 7,0,7}, {8,0,6, 8,0,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);	
    		}
    		
    		
            // Terracotta
        	for (int[] uuvvww : new int[][]{
        		// Front wall
        		{1,1,3, 1,3,3}, {2,1,3, 2,1,3}, {2,3,3, 2,3,3}, {3,1,3, 3,3,3}, 
        		// Left wall
        		{0,1,4, 0,3,4}, {0,1,5, 0,1,6}, {0,3,5, 0,3,6}, {0,1,7, 0,3,7}, 
        		// Right wall
        		{5,1,5, 5,3,5}, {5,1,6, 5,1,6}, {5,3,6, 5,3,6}, {5,1,7, 5,3,7}, 
        		// Back wall
        		{1,1,8, 1,3,8}, {2,1,8, 3,1,8}, {2,3,8, 3,3,8}, {4,1,8, 4,3,8}, 
        		})
            {
        		// Cyan
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor4 : 9),
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor4 : 9), 
        				false);
            }
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{1,4,7, 1,4,7}, {4,4,7, 4,4,7}, 
    			{1,4,4, 1,4,4}, {4,4,4, 4,4,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
            
        	
        	// Vertical logs
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, materialType, biome, disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{0,1,3, 0,3,3}, 
        		{0,1,8, 0,3,8}, 
        		{5,1,8, 5,3,8}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeLogVertBlock, biomeLogVertMeta,
        				biomeLogVertBlock, biomeLogVertMeta, 
        				false);
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	{4,3,4}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Sitting Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(false); Block biomeSittingLanternBlock = (Block)blockObject[0]; int biomeSittingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	// Outside
            	{9,1,4}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeSittingLanternBlock, biomeSittingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uuvvww : new int[][]{
        		// Awning supports
        		{4,1,0, 4,2,0}, 
        		{7,1,1, 7,2,1}, 
        		{8,1,4, 8,2,4}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Above front door
    			{4,3,3, 3+4}, {5,3,4, 1+4}, 
    			// Table
    			{1,1,5, 2+4}, {1,1,7, 3+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Awning
    			{4,3,0, 5,3,0}, 
    			{6,3,1, 7,3,1}, {7,3,2, 7,3,2}, {8,3,3, 8,3,4}, 
    			// Roof trim
    			{0,4,3, 1,4,3}, {0,4,4, 0,4,4}, 
    			{2,4,4, 3,4,4}, 
    			{4,4,3, 5,4,3}, {5,4,4, 5,4,4}, 
    			{4,4,5, 4,4,6}, 
    			{5,4,7, 5,4,8}, {4,4,8, 4,4,8}, 
    			{2,4,7, 3,4,7}, 
    			{0,4,7, 0,4,8}, {1,4,8, 1,4,8}, 
    			{1,4,5, 1,4,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Awning
    			{4,3,1, 5,3,2}, 
    			{5,3,3, 5,3,3}, 
    			{6,3,2, 6,3,2}, 
    			{6,3,3, 7,3,4}, 
    			// Table
    			{1,1,6, 1,1,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
        		// Front wall
        		{2,2,3}, 
        		// Back wall
        		{2,2,8}, {3,2,8}, 
        		// Left wall
        		{0,2,5}, {0,2,6}, 
        		// Right wall
        		{5,2,6}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Glass blocks
        	for (int[] uuvvww : new int[][]{
        		// Sunroof
        		{2,4,5, 3,4,6}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.glass, 0, Blocks.glass, 0, false);
            }
        	
        	
        	// Barrels
    		Block barrelBlock = ModObjects.chooseModBarrelBlock();
    		boolean isChestType=(barrelBlock==null);
    		for (int[] uvwoo : new int[][]{
    			// u, v, w, orientationIfChest, orientationIfUTDBarrel
    			// orientationIfChest:  0=foreward (away from you),  1=rightward,  2=backward (toward you),  3=leftward
    			// orientationIfUTDBarrel: -1=vertical,  0=forward,  1=rightward,  2=backward (toward you),  3=leftward
            	
    			// Front porch
    			{7,1,3, 0,-1}, 
    			// By the pool
    			{6,1,8, 0,1}, {7,1,8, 0,-1}, 
    			{6,2,8, 1,2}, 
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
    			{4,1,3, 0, 1, 0}, 
    			{5,1,4, 3, 1, 1}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
            
    		
            // Flower Pot
    		int flower_u=1; int flower_v=2; int flower_w=5;
            int x = this.getXWithOffset(flower_u, flower_w);
            int y = this.getYWithOffset(flower_v);
            int z = this.getZWithOffset(flower_u, flower_w);
            
            Object[] cornflowerObject = ModObjects.chooseModCornflower(); Object[] lilyOfTheValleyObject = ModObjects.chooseModLilyOfTheValley();
    		int randomPottedPlant = random.nextInt(10)-1;
    		if (randomPottedPlant==-1) {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.yellow_flower, 0);} // Dandelion specifically
    		else {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.red_flower, randomPottedPlant);}          // Every other type of flower
        	
        	
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 4;
        	int chestV = 1;
        	int chestW = 7;
        	int chestO = 2; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo(Reference.VN_FISHER);
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
        	
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward, 3:leftward
            		// Left side
        			{-1,1,7, 3}, {-1,2,7, 3}, {-1,3,7, 3},
        			{-1,1,8, 3}, {-1,2,8, 3}, 
        			// Back side
        			{3,1,9, 0}, {3,2,9, 0}, {3,3,9, 0}, 
        			// Front side
        			{1,1,2, 2}, {1,2,2, 2}, 
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
    			
    			int u=random.nextInt(3);
				int v=1;
				int w=random.nextInt(3);
    			
				if (random.nextBoolean())
				{
					// Inside the cottage
					u+=2; w+=4;
				}
				else
				{
					// Outside the cottage
					u+=4; w+=1;
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
    
    
    // --- Fletcher House 1 --- //
    // designed by AstroTibs
    
    public static class JungleFletcherHouse1 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"         ",
    			" FFFFFF  ",
    			" FFFFFF  ",
    			" FFFFFF  ",
    			" FFFFFF  ",
    			" FFFFFFF ",
    			" FFFFFFF ",
    			"  FPFFFF ",
    			"   P FFF ",
    			"   P     ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 7;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 1;
    	private static final int DECREASE_MAX_U = 4;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleFletcherHouse1() {}

        public JungleFletcherHouse1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleFletcherHouse1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleFletcherHouse1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			// Floor
    			{3,0,3, 3,0,3}, 
    			{1,0,4, 5,0,7}, 
    			{6,0,2, 6,0,4}, {5,0,3, 5,0,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Dirt
    		for(int[] uuvvww : new int[][]{
    			// Floor
    			{1,1,3, 1,1,8}, 
    			{2,1,3, 2,1,3}, 
    			{2,1,8, 5,1,8}, 
    			{6,1,5, 6,1,8}, 
    			{7,1,1, 7,1,4}, 
    			{5,1,1, 6,1,1}, 
    			{5,1,2, 5,1,2}, 
    			{4,1,3, 4,1,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeDirtBlock, biomeDirtMeta, biomeDirtBlock, biomeDirtMeta, false);	
    		}
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Back wall
    			{2,2,8, 2,3,8}, {3,3,8, 4,3,8}, {5,2,8, 5,3,8}, 
    			// Left wall
    			{1,2,4, 1,3,4}, {1,3,5, 1,3,6}, {1,2,7, 1,3,7}, 
    			// Front wall
    			{2,2,3, 2,3,3}, {4,2,3, 4,3,3}, {6,3,1, 6,3,1}, {5,2,2, 5,3,2}, 
    			// Right wall
    			{7,3,2, 7,3,3}, {6,3,5, 6,3,7}, {6,2,5, 6,2,5}, {6,2,7, 6,2,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
            
        	
        	// Vertical logs
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, materialType, biome, disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{1,2,3, 1,4,3}, 
        		{5,2,1, 5,4,1}, {7,2,1, 7,4,1}, 
        		{1,2,8, 1,4,8}, {6,2,8, 6,4,8}, {7,2,4, 7,4,4}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeLogVertBlock, biomeLogVertMeta,
        				biomeLogVertBlock, biomeLogVertMeta, 
        				false);
            }
    		
    		
    		// Stripped logs (Vertical)
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
    		for (int[] uuvvww : new int[][]{
    			{6,1,2, 6,1,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Entry desk
    			{2,1,4, 2+4}, 
    			// Above door
    			{3,3,3, 3+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			{3,3,4, 0}, 
    			{3,3,7, 2}, 
    			{5,3,6, 3}, 
    			{6,3,3, 3}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uuvvww : new int[][]{
        		// Windows
        		{1,2,5, 1,2,6}, 
        		{3,2,8, 4,2,8}, 
        		{6,2,6, 6,2,6}, 
        		{7,2,2, 7,2,3}, 
        		{6,2,1, 6,2,1}, 
        		{5,2,2, 5,2,2}, 
        		// Ceiling supports
        		{1,4,4, 1,4,7}, 
        		{2,4,8, 5,4,8}, 
        		{6,4,5, 6,4,7}, 
        		{7,4,2, 7,4,3}, 
        		{6,4,1, 6,4,1}, 
        		{5,4,2, 5,4,2}, 
        		{2,4,3, 4,4,3}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uvwo : new int[][]{
    			// Roof trim
    			{0,4,3}, {0,4,5}, {0,4,7}, {0,4,9}, 
    			{2,4,9}, {4,4,9}, {6,4,9}, 
    			{7,4,8}, {7,4,6}, 
    			{8,4,5}, {8,4,3}, {8,4,1}, 
    			{7,4,0}, {5,4,0}, 
    			{4,4,1}, 
    			{3,4,2}, {1,4,2}, 
    			// Ceiling
    			{1,5,3}, {1,5,4}, {1,5,5}, {1,5,6}, {1,5,7}, {1,5,8}, 
    			{2,5,8}, {3,5,8}, {4,5,8}, {5,5,8}, {6,5,8},
    			{6,5,7}, {6,5,6}, {6,5,5}, {6,5,4}, 
    			{7,5,4}, {7,5,3}, {7,5,2}, {7,5,1}, 
    			{6,5,1}, {5,5,1}, 
    			{5,5,2}, {5,5,3}, 
    			{4,5,3}, {3,5,3}, {2,5,3}, 
    			{3,6,5}, {4,6,5}, {3,6,6}, {4,6,6}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uvwo : new int[][]{
    			// Roof trim
    			{0,4,2}, {0,4,4}, {0,4,6}, {0,4,8}, 
    			{1,4,9}, {3,4,9}, {5,4,9}, {7,4,9}, 
    			{7,4,9}, {7,4,7}, {7,4,5}, 
    			{8,4,4}, {8,4,2}, {8,4,0}, 
    			{6,4,0}, {4,4,0}, 
    			{2,4,2}, {4,4,2}, 
    			// Ceiling
    			{2,5,4}, {2,5,5}, {2,5,6}, {2,5,7}, 
    			{3,5,7}, {4,5,7}, {5,5,7}, 
    			{5,5,6}, {5,5,5}, {5,5,4}, 
    			{4,5,4}, {3,5,4}, 
    			{6,5,3}, {6,5,2}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}

            
            // Fletching Table
        	blockObject = ModObjects.chooseModFletchingTable(biomePlankMeta); Block fletchingTableBlock = (Block) blockObject[0]; int fletchingTableMeta = (Integer) blockObject[1];
        	this.placeBlockAtCurrentPosition(world, fletchingTableBlock, fletchingTableMeta, 2, 1, 7, structureBB);
    		
    		
            // Table
        	for (int[] uuvvwwo : new int[][]{
        		{5,1,7, 0},
        		})
            {
        		for (int i=1; i>=0; i--)
        		{
        			Object[][] tableComponentObjects = ModObjects.chooseModWoodenTable(biomePlankBlock==Blocks.planks ? biomePlankMeta : 0, uuvvwwo[3], this.coordBaseMode);
        			this.placeBlockAtCurrentPosition(world, (Block)tableComponentObjects[i][0], (Integer)tableComponentObjects[i][1], uuvvwwo[0], uuvvwwo[1]+1-i, uuvvwwo[2], structureBB);
        		}
            }
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{3,1,3, 0, 1, 1}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
        	
            
            // Potted random flower
            for (int[] uvw : new int[][]{
        		{6,2,2}, 
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
    		
    		
    		// Tall Grass
    		for (int[] uvwg : new int[][]{ // g is grass type
    			{2,1,2, 1}, {4,1,2, 1}, 
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
            
            
        	// Carpet
        	for(int[] uuvvww : new int[][]{
        		// Lower
        		{3,1,6, 4,1,6, (GeneralConfig.useVillageColors ? this.townColor : 0)}, // White
        		{3,1,5, 3,1,5, (GeneralConfig.useVillageColors ? this.townColor : 0)}, // White
        		{4,1,5, 4,1,5, (GeneralConfig.useVillageColors ? this.townColor4 : 9)}, // Cyan
        		{5,1,5, 5,1,5, (GeneralConfig.useVillageColors ? this.townColor : 0)}, // White
        		{4,1,4, 5,1,4, (GeneralConfig.useVillageColors ? this.townColor : 0)}, // White
        		})
            {
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.carpet, uuvvww[6], Blocks.carpet, uuvvww[6], false);	
            }
        	
        	
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			int s=random.nextInt(16);
    			
    			int u = s<=1 ? 2 : s<=5 ? 3 : s<=9 ? 4 : s<=13 ? 5 : 6;
				int v=1;
				int w = s<=1 ? s-5 : s<=5 ? s+2 : s<=9 ? s-2 : s<=13 ? s-7 : s-9;
    			
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
    
    
    // --- Fletcher House 2 --- //
    // designed by AstroTibs
    
    public static class JungleFletcherHouse2 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"FFFFFFFFFFFFFFF",
    			"FFFFFFFFFFFFFFF",
    			"FFFFFPPPPPFFFFF",
    			"FFFFFFFPFFFFFFF",
    			"FFFFFFFPFFFFFFF",
    			"  FFFFFPFFFFF  ",
    			"  FFFFFPFFFFF  ",
    			"  FFFFFPFFFFF  ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 7;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 5;
    	private static final int DECREASE_MAX_U = 5;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleFletcherHouse2() {}

        public JungleFletcherHouse2(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleFletcherHouse2 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleFletcherHouse2(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			{0,0,3, 4,0,7}, {10,0,3, 14,0,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uuvvww : new int[][]{
        		// Outside
        		{5,1,7, 9,1,7}, 
        		{5,1,3, 6,1,3}, {8,1,3, 9,1,3}, 
        		// Left roof
        		{1,6,6, 1,6,6}, {3,6,6, 3,6,6}, 
        		{1,6,4, 1,6,4}, {3,6,4, 3,6,4}, 
        		// Right roof
        		{11,6,6, 11,6,6}, {13,6,6, 13,6,6}, 
        		{11,6,4, 11,6,4}, {13,6,4, 13,6,4}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
            
            
        	// Fence Gate (Across)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence_gate, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceGateBlock = (Block)blockObject[0]; int biomeFenceGateMeta = (Integer)blockObject[1];
        	for(int[] uvw : new int[][]{
            	{7,1,3}, 
            	})
            {
        		this.placeBlockAtCurrentPosition(world, biomeFenceGateBlock, StructureVillageVN.getMetadataWithOffset(biomeFenceGateBlock, biomeFenceGateMeta, this.coordBaseMode), uvw[0], uvw[1], uvw[2], structureBB);
            }
            
        	
        	// Vertical logs
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, materialType, biome, disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		// Left wing
        		// Windows
        		{2,1,7, 2,1,7}, {2,3,7, 2,3,7}, 
        		{0,1,5, 0,1,5}, {0,3,5, 0,3,5}, 
        		{4,3,5, 4,3,5}, 
        		{2,1,3, 2,1,3}, {2,3,3, 2,3,3}, 
        		// Roof
        		{1,5,6, 1,5,6}, {2,6,6, 2,6,6}, {3,5,6, 3,5,6}, 
        		{1,6,5, 1,6,5}, {3,6,5, 3,6,5}, 
        		{1,5,4, 1,5,4}, {2,6,4, 2,6,4}, {3,5,4, 3,5,4}, 
        		
        		// Right wing
        		// Windows
        		{12,1,7, 12,1,7}, {12,3,7, 12,3,7}, 
        		{10,3,5, 10,3,5}, 
        		{14,1,5, 14,1,5}, {14,3,5, 14,3,5}, 
        		{12,1,3, 12,1,3}, {12,3,3, 12,3,3}, 
        		// Roof
        		{11,5,6, 11,5,6}, {12,6,6, 12,6,6}, {13,5,6, 13,5,6}, 
        		{11,6,5, 11,6,5}, {13,6,5, 13,6,5}, 
        		{11,5,4, 11,5,4}, {12,6,4, 12,6,4}, {13,5,4, 13,5,4}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeLogVertBlock, biomeLogVertMeta,
        				biomeLogVertBlock, biomeLogVertMeta, 
        				false);
            }
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// Outside
    			{5,2,7, -1}, {9,2,7, -1}, 
    			{5,2,3, -1}, {9,2,3, -1}, 
    			// Inside
    			{1,3,5, 1}, {13,3,5, 3}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		
            // Stone Brick
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickBlock = (Block)blockObject[0]; int biomeStoneBrickMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Left wing
    			{0,1,7, 0,4,7}, {4,1,7, 4,4,7}, 
    			{0,1,3, 0,4,3}, {4,1,3, 4,4,3}, 
    			{1,4,3, 3,4,3}, {0,4,4, 0,4,6}, {1,4,7, 3,4,7}, {4,4,4, 4,4,6}, 
    			// Right wing
    			{10,1,7, 10,4,7}, {14,1,7, 14,4,7}, 
    			{10,1,3, 10,4,3}, {14,1,3, 14,4,3}, 
    			{11,4,3, 13,4,3}, {10,4,4, 10,4,6}, {11,4,7, 13,4,7}, {14,4,4, 14,4,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickBlock, biomeStoneBrickMeta, biomeStoneBrickBlock, biomeStoneBrickMeta, false);	
    		}
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Left wing
    			{1,1,3, 1,3,3}, {3,1,3, 3,3,3}, 
    			{0,1,4, 0,3,4}, {0,1,6, 0,3,6}, {4,1,6, 4,3,6}, {4,1,4, 4,3,4}, 
    			{1,1,7, 1,3,7}, {3,1,7, 3,3,7}, 
    			{2,5,7, 2,5,7}, 
    			{0,5,5, 0,5,5}, {4,5,5, 4,5,5}, 
    			{2,5,3, 2,5,3}, 
    			// Right wing
    			{11,1,3, 11,3,3}, {13,1,3, 13,3,3}, 
    			{10,1,4, 10,3,4}, {10,1,6, 10,3,6}, {14,1,6, 14,3,6}, {14,1,4, 14,3,4}, 
    			{11,1,7, 11,3,7}, {13,1,7, 13,3,7}, 
    			{12,5,7, 12,5,7}, 
    			{10,5,5, 10,5,5}, {14,5,5, 14,5,5}, 
    			{12,5,3, 12,5,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Left wing
    			{1,5,7, 0}, {3,5,7, 1}, 
    			{0,5,6, 2}, {4,5,6, 2}, 
    			{0,5,4, 3}, {4,5,4, 3}, 
    			{1,5,3, 0}, {3,5,3, 1}, 
    			// Right wing
    			{11,5,7, 0}, {13,5,7, 1}, 
    			{10,5,6, 2}, {14,5,6, 2}, 
    			{10,5,4, 3}, {14,5,4, 3}, 
    			{11,5,3, 0}, {13,5,3, 1}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uvwo : new int[][]{
    			// Left wing
    			{0,5,7}, {4,5,7}, 
    			{0,5,3}, {4,5,3}, 
    			// Right wing
    			{10,5,7}, {14,5,7}, 
    			{10,5,3}, {14,5,3}, 
    			// Connecting awning
    			{5,4,6}, {6,4,6}, {7,4,6}, {8,4,6}, {9,4,6}, 
    			{5,4,4}, {6,4,4}, {7,4,4}, {8,4,4}, {9,4,4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uvwo : new int[][]{
    			// Connecting awning
    			{5,3,7}, {6,3,7}, {7,3,7}, {8,3,7}, {9,3,7}, 
    			{5,4,5}, {6,4,5}, {7,4,5}, {8,4,5}, {9,4,5}, 
    			{5,3,3}, {6,3,3}, {7,3,3}, {8,3,3}, {9,3,3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
        		// Left wing
        		{2,2,7}, 
        		{0,2,5}, 
        		{2,2,3}, 
        		// Right wing
        		{12,2,7}, 
        		{14,2,5}, 
        		{12,2,3}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Glass blocks
        	for (int[] uvw : new int[][]{
        		{2,6,5}, {12,6,5}, 
        		})
            {
    			this.placeBlockAtCurrentPosition(world, Blocks.glass, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }

            
            // Fletching Table
        	blockObject = ModObjects.chooseModFletchingTable(biomePlankMeta); Block fletchingTableBlock = (Block) blockObject[0]; int fletchingTableMeta = (Integer) blockObject[1];
        	this.placeBlockAtCurrentPosition(world, fletchingTableBlock, fletchingTableMeta, 13,1,6, structureBB);
    		
    		
    		// Grass - NOT biome-swapped
    		for (int[] uuvvww : new int[][]{
        		{1,1,6, 1,1,6}, 
        		{2,0,0, 6,0,2}, {8,0,0, 11,0,2}, 
        		})
            {
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.grass, 0, Blocks.grass, 0, false);	
            }
            
        	
        	// Random Flower
        	for (int[] uvw : new int[][]{
    			{1,2,6}, 
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
            
            
            // Trapdoor (Bottom Vertical)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapdoor, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{1,1,5, 2}, 
            	{2,1,6, 1}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, false, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
        	
        	
            // Crafting Table
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.crafting_table, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCraftingTableBlock = (Block)blockObject[0]; int biomeCraftingTableMeta = (Integer)blockObject[1];
            for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
        		{1,1,4}, 
           		})
        	{
            	this.placeBlockAtCurrentPosition(world, biomeCraftingTableBlock, biomeCraftingTableMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 13;
        	int chestV = 1;
        	int chestW = 4;
        	int chestO = 3; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo(Reference.VN_FLETCHER);
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
    		
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{4,1,5, 1, 1, 1}, {10,1,5, 3, 1, 0}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
    		
        	
        	// Leaves
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.leaves, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLeafBlock = (Block)blockObject[0]; int biomeLeafMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		// Front shrubs
        		{5,1,0, 5,1,2}, {9,1,0, 9,1,2}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeLeafBlock, biomeLeafMeta,
        				biomeLeafBlock, biomeLeafMeta, 
        				false);
            }
    		
    		
    		// Tall Grass
    		for (int[] uvwg : new int[][]{ // g is grass type
    			// Walkway
    			{7,1,6, 0}, 
    			{5,1,6, 1}, {9,1,6, 1}, 
    			{6,1,6, 0}, {8,1,6, 0}, 
    			{5,1,4, 0}, {9,1,4, 0}, 
    			// Front lawn
    			{2,1,2, 0}, {3,1,2, 0}, {4,1,2, 0}, {6,1,2, 0}, {8,1,2, 0}, {10,1,2, 0}, {11,1,2, 0}, {12,1,2, 0}, 
    			{2,1,1, 0}, {3,1,1, 0}, {4,1,1, 0}, {6,1,1, 0}, {8,1,1, 0}, {10,1,1, 0}, {11,1,1, 0}, {12,1,1, 0}, 
    			{2,1,0, 0}, {3,1,0, 0}, {4,1,0, 0}, {6,1,0, 0}, {8,1,0, 0}, {10,1,0, 0}, {11,1,0, 0}, {12,1,0, 0}, 
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
        	
        	
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			int s=random.nextInt(29);
    			
    			int u = s<=0 ? 1 : s<=6 ? 2+(s-1)/3 : s<=21 ? 3+(s-1)/3 : s<=27 ? 4+(s-1)/3 : 13;
				int v=1;
				int w = s<=0 ? 5 : s<=27 ? 4+(s-1)%3 : 5;
    			
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
    
    
    // --- Large House --- //
    // designed by AstroTibs and jss2a98aj
    
    public static class JungleLargeHouse extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"                      ",
    			"       FFFFFFFFF      ",
    			"       FFFFFFFFF      ",
    			"FFF    FFFFFFFFF      ",
    			"FFF    FFFFFFFFF      ",
    			"FFF    FFFFFFFFF      ",
    			"FFF    FFFFFFFFFFFFFF ",
    			"PPP    FFFFFFFFFFFFFF ",
    			"PPPP   FFFFFFFFFFFFFF ",
    			" PPP   FFFFFFFFFFFFFF ",
    			" PPPP  FFFFFFFFFFFFFF ",
    			"  PPP  FFFFFFFFFFFFFF ",
    			"  PPPP FFFFFFFFFFFFFF ",
    			"   PPP FFFFFFFFF      ",
    			"   PPP                ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 12;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 2;
    	private static final int DECREASE_MAX_U = 15;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleLargeHouse() {}

        public JungleLargeHouse(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleLargeHouse buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleLargeHouse(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			// Foundation
    			{7,0,1, 15,0,13}, {16,0,2, 20,0,8}, 
    			// Entry staircase
    			{0,1,9, 2,1,11}, {0,2,10, 2,2,11}, 
    			// Cobblestone underneath balcony walkway
    			{6,3,9, 6,3,11}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
        	
            
            // Cobblestone stairs
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
        	for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
        		// Entryway
        		{0,1,8, 1}, {1,1,8, 3}, {2,1,8, 0}, 
        		{0,2,9, 1}, {1,2,9, 3}, {2,2,9, 0}, 
        		{2,3,10, 0}, {3,3,9, 1+4}, {3,3,10, 1+4}, {3,3,11, 1+4}, 
        		{3,4,10, 0}, {4,4,9, 1+4}, {4,4,10, 1+4}, {4,4,11, 1+4}, 
        		{5,4,9, 0+4}, {5,4,10, 0+4}, {5,4,11, 0+4}, 
        		{6,2,9, 0+4}, {6,2,10, 0+4}, {6,2,11, 0+4}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
    		
        	
        	// Cobblestone wall
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
            	{0,2,8, 0,2,8}, 
            	{0,3,8, 0,3,11}, {1,3,11, 2,3,11}, {2,4,11, 3,4,11}, {3,5,11, 4,5,11}, 
            	{2,2,8, 2,2,8}, 
            	{2,3,8, 2,3,9}, {2,4,9, 3,4,9}, {3,5,9, 4,5,9}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, false);
            }
    		
    		
    		// Stripped logs (Vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
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
    		for (int[] uuvvww : new int[][]{
    			// Two-floor beams
    			{15,1,2, 15,8,2}, {15,1,8, 15,8,8}, 
    			// Top-floor beam
    			{11,5,8, 11,7,8}, 
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
            	// Top
            	// Front wall
            	{7,8,1, 15,8,1}, {16,8,2, 20,8,2}, 
            	// Back wall
            	{7,8,13, 15,8,13}, {16,8,8, 20,8,8}, 
            	
            	// Bottom
            	// Front wall
            	{7,4,1, 15,4,1}, {16,4,2, 20,4,2}, 
            	// Back wall
            	{7,4,13, 15,4,13}, {16,4,8, 20,4,8}, 
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
            	// Left wall
            	{7,4,2, 7,4,12}, 
            	{7,8,2, 7,8,12}, 
            	// Right wall 
            	{20,4,3, 20,4,7}, {15,4,9, 15,4,12}, 
            	{20,8,3, 20,8,7}, {15,8,9, 15,8,12}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogHorizAlongBlock, biomeStrippedLogHorizAlongMeta, biomeStrippedLogHorizAlongBlock, biomeStrippedLogHorizAlongMeta, false);	
            }
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Stairs
    			{8,4,3, 8,4,3}, 
    			// Downstairs lamp
    			{11,4,5, 13,4,5}, {12,4,4, 12,4,4}, {12,4,6, 12,4,6}, 
    			// Downstairs couch
    			{8,1,12, 8,1,12}, {14,1,12, 14,1,12}, 
    			// Beneath beds
    			{18,5,3, 19,5,7}, 
    			// Top of walls
    			{8,9,1, 8,9,1}, {10,10,1, 10,10,1}, {12,10,1, 12,10,1}, {14,9,1, 14,9,1}, 
    			{20,9,3, 20,9,3}, {20,10,5, 20,10,5}, {20,9,7, 20,9,7}, 
    			{8,9,13, 8,9,13}, {10,10,13, 10,10,13}, {12,10,13, 12,10,13}, {14,9,13, 14,9,13}, 
    			// Ceiling
    			{17,10,5, 17,10,5}, 
    			// Under main entrance
    			{6,4,9, 6,4,11}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uuvvww : new int[][]{
        		// Balcony
        		{6,5,1, 6,5,1}, 
        		{5,5,1, 5,5,9}, {5,5,11, 5,5,13}, 
        		{6,5,13, 6,5,13}, 
        		{5,6,1, 5,7,1}, {5,6,3, 5,7,3}, {5,6,5, 5,7,5}, {5,6,7, 5,7,7}, {5,6,9, 5,7,9}, {5,6,11, 5,7,11}, {5,6,13, 5,7,13}, 
        		// Interior ceiling trims
        		{11,8,8, 11,8,12}, {12,8,8, 14,8,8}, 
        		{15,8,3, 15,8,7}, 
        		// Stairwell railing
        		{9,5,3, 13,5,3}, {13,5,2, 13,5,2}, 
        		// Light fixtures
        		{11,10,5, 11,10,5}, {10,9,5, 12,9,5}, {11,8,4, 11,8,6}, 
        		{17,9,5, 17,9,5}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// Entry stairwell
    			{0,4,8, -1}, {0,4,11, -1}, {4,6,11, -1}, 
    			{2,4,8, -1}, {4,6,9, -1}, 
    			// Balcony
    			{6,6,1, -1}, {6,6,13, -1}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		
            // White Terracotta
        	for (int[] uuvvww : new int[][]{
        		
        		// Downstairs
        		// Front wall
        		{7,1,1, 7,3,1}, {8,1,1, 8,1,1}, {9,1,1, 9,3,1}, {10,1,1, 10,1,1}, {11,1,1, 11,3,1}, {12,1,1, 12,1,1}, {13,1,1, 13,3,1}, {14,1,1, 14,1,1}, {15,1,1, 15,3,1}, 
        		{16,1,2, 16,3,2}, {17,1,2, 17,1,2}, {18,1,2, 18,3,2}, {19,1,2, 19,1,2}, {20,1,2, 20,3,2}, 
        		// Right wall 
        		{20,1,3, 20,1,3}, {20,1,4, 20,3,4}, {20,1,5, 20,1,5}, {20,1,6, 20,3,6}, {20,1,7, 20,1,7}, {20,1,8, 20,3,8}, 
        		{15,1,9, 15,3,9}, {15,1,10, 15,1,10}, {15,1,11, 15,3,11}, {15,1,12, 15,1,12}, 
        		// Left wall 
        		{7,1,2, 7,1,2}, {7,1,3, 7,3,3}, {7,1,4, 7,1,4}, {7,1,5, 7,3,5}, {7,1,6, 7,1,6}, {7,1,7, 7,3,13}, 
        		// Back wall 
        		{19,1,8, 19,1,8}, {18,1,8, 18,3,8}, {17,1,8, 17,1,8}, {16,1,8, 16,3,8}, 
        		{15,1,13, 15,3,13}, {14,1,13, 14,1,13}, {13,1,13, 13,3,13}, {12,1,13, 12,1,13}, {11,1,13, 11,3,13}, {10,1,13, 10,1,13}, {9,1,13, 9,3,13}, {8,1,13, 8,1,13}, 
        		
        		// Upstairs
        		// Front wall
        		{7,5,1, 7,7,1}, {8,5,1, 8,5,1}, {9,5,1, 9,7,1}, {10,5,1, 10,5,1}, {11,5,1, 11,7,1}, {12,5,1, 12,5,1}, {13,5,1, 13,7,1}, {14,5,1, 14,5,1}, {15,5,1, 15,7,1}, 
        		{16,5,2, 16,7,2}, {17,5,2, 17,5,2}, {18,5,2, 18,7,2}, {19,5,2, 19,5,2}, {20,5,2, 20,7,2}, 
        		// Right wall 
        		{20,5,3, 20,5,3}, {20,5,4, 20,7,4}, {20,5,5, 20,5,5}, {20,5,6, 20,7,6}, {20,5,7, 20,5,7}, {20,5,8, 20,7,8}, 
        		{15,5,9, 15,7,9}, {15,5,10, 15,5,10}, {15,5,11, 15,7,11}, {15,5,12, 15,5,12}, 
        		// Left wall 
        		{7,5,2, 7,5,2}, {7,5,3, 7,7,3}, {7,5,4, 7,5,4}, {7,5,5, 7,7,5}, {7,5,6, 7,5,6}, {7,5,7, 7,7,7}, {7,5,8, 7,5,8}, {7,5,9, 7,7,9}, {7,5,11, 7,7,11}, {7,5,12, 7,5,12}, {7,5,13, 7,7,13}, 
        		// Back wall 
        		{19,5,8, 19,5,8}, {18,5,8, 18,7,8}, {17,5,8, 17,5,8}, {16,5,8, 16,7,8}, 
        		{15,5,13, 15,7,13}, {14,5,13, 14,5,13}, {13,5,13, 13,7,13}, {12,5,13, 12,5,13}, {11,5,13, 11,7,13}, {10,5,13, 10,5,13}, {9,5,13, 9,7,13}, {8,5,13, 8,5,13}, 
        		
        		// Roof
        		{9,9,1, 10,9,1}, {11,10,1, 11,10,1}, {12,9,1, 13,9,1}, 
        		{20,9,4, 20,9,4}, {20,9,6, 20,9,6}, 
        		{9,9,13, 10,9,13}, {11,10,13, 11,10,13}, {12,9,13, 13,9,13}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor : 0),
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor : 0), 
        				false);
            }
    		
    		
            // Orange Terracotta
        	for (int[] uuvvww : new int[][]{
        		{14,5,12, 14,5,12}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor5 : 1),
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor5 : 1), 
        				false);
            }
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{5,8,0, 5,8,14}, {7,9,0, 7,9,14}, {9,10,0, 9,10,14}, {11,11,0, 11,11,14}, 
    			{13,10,0, 13,10,4}, {13,10,6, 13,10,14}, {14,10,4, 21,10,4}, {14,10,6, 21,10,6}, 
    			{15,9,0, 15,9,2}, {15,9,8, 15,9,14}, {16,9,2, 21,9,2}, {16,9,8, 21,9,8}, 
    			// In front of beds
    			{17,5,3, 17,5,7}, 
    			// Inside stairs
    			{8,4,2, 8,4,2}, {10,3,2, 10,3,2}, {12,2,2, 12,2,2}, {14,1,2, 14,1,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{6,8,0, 6,8,14}, {8,9,0, 8,9,0}, {8,9,2, 8,9,12}, {8,9,14, 8,9,14}, {10,10,0, 10,10,0}, {10,10,2, 10,10,12}, {10,10,14, 10,10,14}, 
    			{12,10,0, 12,10,0}, {12,10,2, 12,10,12}, {12,10,14, 12,10,14}, {13,10,5, 16,10,5}, {18,10,5, 19,10,5}, {21,10,5, 21,10,5}, 
    			{14,9,0, 14,9,0}, {14,9,2, 14,9,3}, {14,9,7, 14,9,12}, {14,9,14, 14,9,14}, {15,9,3, 19,9,3}, {21,9,3, 21,9,3}, {15,9,7, 19,9,7}, {21,9,7, 21,9,7}, 
    			{16,8,0, 16,8,1}, {16,8,9, 16,8,14}, {17,8,1, 21,8,1}, {17,8,9, 21,8,9}, 
    			// Balcony
    			{5,4,1, 6,4,8}, {5,4,12, 6,4,13}, 
    			// Dividing floor
    			{8,4,4, 8,4,12}, {9,4,3, 10,4,12}, {11,4,3, 11,4,4}, {11,4,6, 11,4,12}, {12,4,3, 12,4,3}, {12,4,7, 12,4,12}, {13,4,2, 13,4,4}, {13,4,6, 13,4,12}, {14,4,2, 14,4,12}, {14,4,3, 19,4,7}, 
    			// Inside stairs
    			{9,3,2, 9,3,2}, {11,2,2, 11,2,2}, {13,1,2, 13,1,2}, 
    			// Cake table
    			{11,1,9, 12,1,10}, 
    			// Shelves
    			{19,1,4, 19,2,4}, {19,1,6, 19,2,6}, 
    			// Second floor arches
    			{11,5,9, 11,5,9}, {11,5,11, 11,5,11}, 
    			{11,6,10, 11,6,10}, 
    			{11,7,9, 11,7,9}, {11,7,11, 11,7,11}, 
    			{13,7,8, 13,7,8}, 
    			{15,7,5, 15,7,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Room separator
    			{11,7,12, 3+4}, {11,7,10, 3+4}, 
    			{11,6,12, 3+4}, {11,6,11, 3+4}, {11,6,9, 3+4}, 
    			{11,5,12, 3+4}, {11,5,10, 3+4}, 
    			{12,5,8, 0+4}, {12,6,8, 0+4}, {12,7,8, 0+4}, 
    			{14,5,8, 1+4}, {14,6,8, 1+4}, {14,7,8, 1+4}, 
    			{15,5,7, 2+4}, {15,6,7, 2+4}, {15,7,7, 2+4}, {15,7,6, 3+4}, 
    			{15,5,3, 3+4}, {15,6,3, 3+4}, {15,7,3, 3+4}, {15,7,4, 2+4}, 
    			// Cake table
    			{10,1,10, 1+4}, {13,1,10, 0+4}, 
    			{10,1,9, 1+4}, {13,1,9, 0+4}, 
    			// Couch
    			{9,1,12, 3}, {10,1,12, 3}, {11,1,12, 3}, {12,1,12, 3}, {13,1,12, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	// Interior
            	{11,7,4}, {11,7,6}, 
            	{10,8,5}, {12,8,5}, 
            	{17,8,5}, 
            	{8,3,3}, {12,3,5}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Sitting Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(false); Block biomeSittingLanternBlock = (Block)blockObject[0]; int biomeSittingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	{14,6,12}, 
            	{8,2,12}, 
            	{19,3,4}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeSittingLanternBlock, biomeSittingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
        		// Upstairs
        		{8,6,1}, {8,7,1}, {10,6,1}, {10,7,1}, {12,6,1}, {12,7,1}, {14,6,1}, {14,7,1}, 
        		{17,6,2}, {17,7,2}, {19,6,2}, {19,7,2}, 
        		{20,6,3}, {20,7,3}, {20,6,5}, {20,7,5}, {20,6,7}, {20,7,7}, 
        		{19,6,8}, {19,7,8}, {17,6,8}, {17,7,8}, 
        		{15,6,10}, {15,7,10}, {15,6,12}, {15,7,12}, 
        		{14,6,13}, {14,7,13}, {12,6,13}, {12,7,13}, {10,6,13}, {10,7,13}, {8,6,13}, {8,7,13}, 
        		{7,6,12}, {7,7,12}, {7,6,8}, {7,7,8}, {7,6,6}, {7,7,6}, {7,6,4}, {7,7,4}, {7,6,2}, {7,7,2}, 
        		// Downstairs
        		{8,2,1}, {8,3,1}, {10,2,1}, {10,3,1}, {12,2,1}, {12,3,1}, {14,2,1}, {14,3,1}, 
        		{17,2,2}, {17,3,2}, {19,2,2}, {19,3,2}, 
        		{20,2,3}, {20,3,3}, {20,2,5}, {20,3,5}, {20,2,7}, {20,3,7}, 
        		{19,2,8}, {19,3,8}, {17,2,8}, {17,3,8}, 
        		{15,2,10}, {15,3,10}, {15,2,12}, {15,3,12}, 
        		{14,2,13}, {14,3,13}, {12,2,13}, {12,3,13}, {10,2,13}, {10,3,13}, {8,2,13}, {8,3,13}, 
        		{7,2,6}, {7,3,6}, {7,2,4}, {7,3,4}, {7,2,2}, {7,3,2}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Trapdoor (Top Vertical)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapdoor, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	// Ceiling vents
            	{11,9,0, 2}, {11,9,2, 0}, 
            	{19,9,5, 3}, {21,9,5, 1}, 
            	{11,9,12, 2}, {11,9,14, 0}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, true, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
            
        	// Orange Carpet
            int carpetMeta = (GeneralConfig.useVillageColors ? this.townColor5 : 1);
        	for(int[] uvwm : new int[][]{
        		// Entrance
        		{8,5,12, 8,5,12}, {10,5,12, 10,5,12}, 
        		{9,5,11, 9,5,11}, 
        		{8,5,10, 8,5,10}, {10,5,10, 10,5,10}, 
        		{9,5,9, 9,5,9}, 
        		{8,5,8, 8,5,8}, {10,5,8, 10,5,8}, 
        		// Downstairs rug
        		{9,1,4, 9,1,6}, {10,1,6, 10,1,7}, {11,1,7, 13,1,7}, {14,1,6, 14,1,7}, {15,1,4, 15,1,6}, 
        		{10,1,3, 10,1,4}, {11,1,3, 13,1,3}, {14,1,3, 14,1,4}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uvwm[0], uvwm[1], uvwm[2], uvwm[3], uvwm[4], uvwm[5], Blocks.carpet, carpetMeta, Blocks.carpet, carpetMeta, false);
            }
            
            
        	// Yellow Carpet
            carpetMeta = (GeneralConfig.useVillageColors ? this.townColor2 : 4);
        	for(int[] uvwm : new int[][]{
        		// Entrance
        		{9,5,12, 9,5,12}, 
        		{8,5,11, 8,5,11}, {10,5,11, 10,5,11}, 
        		{9,5,10, 9,5,10}, 
        		{8,5,9, 8,5,9}, {10,5,9, 10,5,9}, 
        		{9,5,8, 9,5,8}, 
        		// Downstairs rug
        		{11,1,6, 11,1,6}, {13,1,6, 13,1,6}, 
        		{10,1,5, 10,1,5}, {12,1,5, 12,1,5}, {14,1,5, 14,1,5}, 
        		{11,1,4, 11,1,4}, {13,1,4, 13,1,4}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uvwm[0], uvwm[1], uvwm[2], uvwm[3], uvwm[4], uvwm[5], Blocks.carpet, carpetMeta, Blocks.carpet, carpetMeta, false);
            }
            
            
        	// White Carpet
            carpetMeta = (GeneralConfig.useVillageColors ? this.townColor : 0);
        	for(int[] uvwm : new int[][]{
        		// Downstairs rug
        		{12,1,6, 12,1,6}, 
        		{11,1,5, 11,1,5}, {13,1,5, 13,1,5}, 
        		{12,1,4, 12,1,4}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uvwm[0], uvwm[1], uvwm[2], uvwm[3], uvwm[4], uvwm[5], Blocks.carpet, carpetMeta, Blocks.carpet, carpetMeta, false);
            }
            
            
            // Stone pressure plate
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_pressure_plate, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStonePressurePlateBlock = (Block)blockObject[0]; int biomeStonePressurePlateMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		{11,2,10}, {12,2,10}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeStonePressurePlateBlock, biomeStonePressurePlateMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Furnaces
            for (int[] uvwo : new int[][]{ // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
            	{17,1,3, 0}, 
            	})
            {
                this.placeBlockAtCurrentPosition(world, Blocks.furnace, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2]), StructureVillageVN.chooseFurnaceMeta(uvwo[3], this.coordBaseMode), 2);
            }
        	
        	
            // Crafting Table
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.crafting_table, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCraftingTableBlock = (Block)blockObject[0]; int biomeCraftingTableMeta = (Integer)blockObject[1];
            for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
        		{17,1,7}, 
           		})
        	{
            	this.placeBlockAtCurrentPosition(world, biomeCraftingTableBlock, biomeCraftingTableMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
            
            // Bookshelves
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.bookshelf, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeBookshelfBlock = (Block)blockObject[0]; int biomeBookshelfMeta = (Integer)blockObject[1];
            for (int[] uuvvww : new int[][]{
        		{8,1,9, 8,3,10}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeBookshelfBlock, biomeBookshelfMeta, biomeBookshelfBlock, biomeBookshelfMeta, false);
            }
        	
        	
            // Cake
            for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
        		{12,2,9}, 
           		})
        	{
            	this.placeBlockAtCurrentPosition(world, Blocks.cake, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
            
            // Potted random flower
            for (int[] uvw : new int[][]{
        		{19,3,6}, 
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
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{18,6,4, 3, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
            	{18,6,6, 3, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
            	{13,5,11, 2, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
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
	                	{18,6,3, -1, 0}, 
	                	{18,6,7, -1, 0}, 
	                	{13,5,10, -1, 0}, 
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
    // designed by AstroTibs
    
    public static class JungleLibrary extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"           ",
    			" FFFFFFFFF ",
    			" FFFFFFFFF ",
    			" FFFFFFFFF ",
    			" FFFFFFFFF ",
    			" FFFFFFFFF ",
    			" FFFFFFFFF ",
    			" FFFFFFFFF ",
    			" FFFFFFFFF ",
    			" FFFFFFFFF ",
    			" FFFFFFFFF ",
    			"    FPF    ",
    			"    PPP    ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 14;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 1;
    	private static final int DECREASE_MAX_U = 1;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleLibrary() {}

        public JungleLibrary(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleLibrary buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleLibrary(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			// Floor
    			{2,0,11, 8,0,11}, 
    			{1,0,2, 1,0,11}, {9,0,2, 9,0,11}, 
    			{2,0,2, 8,0,2}, 
    			// Wall foundation
    			{1,1,2, 4,1,2}, {6,1,2, 9,1,2}, 
    			{1,1,3, 1,1,10}, {9,1,3, 9,1,10},
    			{1,1,11, 9,1,11},
    			// Column bottoms
    			{4,0,1, 4,1,1}, {6,0,1, 6,1,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{1,6,2, 9,6,2}, 
    			{1,7,4, 9,7,4}, 
    			{1,8,6, 3,8,7}, {7,8,6, 9,8,7}, 
    			{1,7,9, 9,7,9}, 
    			{1,6,11, 9,6,11}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
    		
    		
    		// Logs (Across)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Ceiling support
    			{1,7,8, 9,7,8}, 
    			{1,7,5, 9,7,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, false);	
    		}
    		
    		
    		// Concrete
    		Object[] tryConcrete = ModObjects.chooseModConcrete(GeneralConfig.useVillageColors ? townColor : 0); // White
        	Block concreteBlock = Blocks.stained_hardened_clay; int concreteMeta = GeneralConfig.useVillageColors ? townColor : 0; // White
        	if (tryConcrete != null) {concreteBlock = (Block) tryConcrete[0]; concreteMeta = (Integer) tryConcrete[1];}
    		for(int[] uuvvww : new int[][]{
    			// Front wall
    			{1,2,2, 2,3,2}, {4,2,2, 4,3,2}, 
    			{5,3,2, 5,3,2}, 
    			{8,2,2, 9,3,2}, {6,2,2, 6,3,2}, 
    			{1,4,2, 9,5,2}, 
    			// Left wall
    			{1,2,3, 1,3,3}, {1,2,5, 1,3,8}, {1,2,10, 1,3,10}, 
    			{1,4,3, 1,6,10}, 
    			// Left window frame
    			{0,7,5, 0,7,5}, {0,7,8, 0,7,8}, 
    			// Right wall
    			{9,2,3, 9,3,3}, {9,2,5, 9,3,8}, {9,2,10, 9,3,10}, 
    			{9,4,3, 9,6,10}, 
    			// Right window frame
    			{10,7,5, 10,7,5}, {10,7,8, 10,7,8}, 
    			// Back wall
    			{1,2,11, 2,3,11}, {4,2,11, 4,3,11}, 
    			{8,2,11, 9,3,11}, {6,2,11, 6,3,11}, 
    			{1,4,11, 9,5,11}, 
    			// Steeple front
    			{4,8,5, 6,9,5}, 
    			// Steeple back
    			{4,8,8, 6,9,8}, 
    			// Steeple left
    			{4,8,6, 4,9,7}, 
    			// Steeple right
    			{6,8,6, 6,9,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], concreteBlock, concreteMeta, concreteBlock, concreteMeta, false);	
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// Front entrance
    			{5,3,1, 2},  
    			// Interior
    			{5,4,3, 0}, // Front wall
    			{5,4,10, 2}, // Back wall
    			{2,4,4, 1}, {2,4,9, 1}, // Left wall
    			{8,4,4, 3}, {8,4,9, 3}, // Right wall
    			// Steeple
    			{5,8,6, 0},
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		
    		// Logs (Vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Top of front columns
    			{4,5,1, 4,5,1}, {6,5,1, 6,5,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);	
    		}
    		
    		
    		// For stripped logs specifically
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
    		for (int[] uuvvww : new int[][]{
    			// Front pillars
    			{4,2,1, 4,4,1}, {6,2,1, 6,4,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Window awnings
    			{3,4,1, 3}, {5,4,1, 3}, {7,4,1, 3}, // Front wall
    			{3,4,12, 2}, {5,4,12, 2}, {7,4,12, 2}, // Back wall
    			{0,4,3, 3}, {0,4,4, 0}, {0,4,5, 2}, {0,4,8, 3}, {0,4,9, 0}, {0,4,10, 2}, // Left wall
    			{10,4,3, 3}, {10,4,4, 1}, {10,4,5, 2}, {10,4,8, 3}, {10,4,9, 1}, {10,4,10, 2}, // Right wall
    			// Roof trim
    			{0,6,2, 0+4}, {0,7,4, 0+4}, {0,8,6, 0+4}, {10,6,2, 1+4}, {10,7,4, 1+4}, {10,8,6, 1+4}, 
    			{0,6,11, 0+4}, {0,7,9, 0+4}, {0,8,7, 0+4}, {10,6,11, 1+4}, {10,7,9, 1+4}, {10,8,7, 1+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{0,6,1, 10,6,1}, {0,7,3, 10,7,3}, {0,8,5, 3,8,5}, {7,8,5, 10,8,5}, 
    			{0,6,12, 10,6,12}, {0,7,10, 10,7,10}, {0,8,8, 3,8,8}, {7,8,8, 10,8,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Ceiling
    			{2,6,3, 8,6,3}, {2,6,10, 8,6,10}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
        	
        	// Cobblestone wall
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
            	{4,8,4, 4,8,4}, {6,8,4, 6,8,4}, 
            	{4,8,9, 4,8,9}, {6,8,9, 6,8,9}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, false);
            }
        	
            
            // Cobblestone stairs
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
        	for (int[] uuvvwwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
        		// Roof trim
        		{0,6,3, 0,6,10, 0+4}, {10,6,3, 10,6,10, 1+4}, 
        		// Steeple
        		// Lower rim
        		{4,10,5, 6,10,5, 3}, 
        		{4,10,8, 6,10,8, 2}, 
        		{4,10,6, 4,10,7, 0}, 
        		{6,10,6, 6,10,7, 1}, 
        		// Upper rim
        		{4,12,5, 6,12,5, 3}, 
        		{4,12,8, 6,12,8, 2}, 
        		{4,12,6, 4,12,7, 0}, 
        		{6,12,6, 6,12,7, 1}, 
        		// Topmost
        		{5,13,6, 5,13,6, 3}, 
        		{5,13,7, 5,13,7, 2}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], uuvvwwo[3], uuvvwwo[4], uuvvwwo[5], biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uuvvwwo[6]%4)+(uuvvwwo[6]/4)*4, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uuvvwwo[6]%4)+(uuvvwwo[6]/4)*4, false);	
            }
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
        		// Front wall
        		{3,2,2}, {3,3,2}, {7,2,2}, {7,3,2}, 
        		// Back wall
        		{3,2,11}, {3,3,11}, {5,2,11}, {5,3,11}, {7,2,11}, {7,3,11}, 
        		// Left wall
        		{1,2,4}, {1,3,4}, {1,2,9}, {1,3,9}, {0,7,6}, {0,7,7}, 
        		// Right wall
        		{9,2,4}, {9,3,4}, {9,2,9}, {9,3,9}, {10,7,6}, {10,7,7}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
            
            // Iron bars
            for(int[] uuvvww : new int[][]{
            	// Interior railing
            	{4,11,5, 6,11,5}, 
            	{4,11,6, 4,11,7}, {6,11,6, 6,11,7}, 
            	{4,11,8, 6,11,8}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.iron_bars, 0, Blocks.iron_bars, 0, false);
            }
    		
        	
            // Lectern
            for (int[] uvwo : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{5,1,10, 2},
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
    		
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{5,1,2, 0, 1, 1}, 
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
        		{2,1,3, 2,4,3}, {2,1,6, 2,4,7}, {2,1,10, 2,4,10}, 
        		{8,1,3, 8,4,3}, {8,1,6, 8,4,7}, {8,1,10, 8,4,10}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeBookshelfBlock, biomeBookshelfMeta, biomeBookshelfBlock, biomeBookshelfMeta, false);
            }
            
            
            // Wool - carpet in front of the door prevents villagers from passing through
            int woolMeta = (GeneralConfig.useVillageColors ? this.townColor3 : 14); // Red
        	for(int[] uvwm : new int[][]{
        		{2,0,3, 8,0,10}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uvwm[0], uvwm[1], uvwm[2], uvwm[3], uvwm[4], uvwm[5], Blocks.wool, woolMeta, Blocks.wool, woolMeta, false);
            }
    		
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			// Villager
				int u = 3+random.nextInt(5);
				int v = 1;
				int w = 3+random.nextInt(7);
    			
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
    // designed by Lonemind
    
    public static class JungleMasonHouse extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"FFFFFFFFFFF  ",
    			"FFFFFFFFFFF  ",
    			"FFFFFFFFFFF  ",
    			"FFFFFFFFFFF  ",
    			"FFFFFFFFFFF  ",
    			"FFFFFFFFFFF  ",
    			"FFFFFFFFFFFF ",
    			"FFPFPFFFFFFFF",
    			"FFPPFPFFFFFFF",
    			"FFFPPFFFFFFFF",
    			"FFFFPFFFFFFF ",
    			"    FFFFFFF  ",
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
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleMasonHouse() {}

        public JungleMasonHouse(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleMasonHouse buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleMasonHouse(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
            // Stone Brick
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickBlock = (Block)blockObject[0]; int biomeStoneBrickMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Floor
    			{1,1,6, 1,1,8}, 
    			{2,1,6, 2,1,6}, {2,1,8, 2,1,10}, 
    			{3,1,6, 3,1,6}, {3,1,8, 3,1,8}, {3,1,10, 3,1,10}, 
    			{4,1,6, 4,1,6}, {4,1,9, 4,1,9}, 
    			{5,1,9, 5,1,9}, 
    			{6,1,7, 6,1,7}, {6,1,9, 6,1,10}, 
    			{7,1,7, 7,1,7}, {7,1,9, 7,1,9}, 
    			{8,1,6, 8,1,6}, {8,1,8, 8,1,9}, 
    			{9,1,7, 9,1,8}, {9,1,10, 9,1,10}, 
    			{10,1,7, 10,1,7}, {10,1,9, 10,1,10}, 
    			// Frame
    			{1,3,6, 1,3,6}, 
    			{1,2,10, 1,2,10}, 
    			{5,3,10, 5,3,10}, 
    			// Roof
    			{1,5,7, 1,5,7}, {2,5,10, 3,5,10}, {3,5,6, 3,5,6}, {5,5,9, 5,5,9}, 
    			{3,6,8, 3,6,8}, 
    			// Pile
    			{6,1,1, 7,1,1}, {9,1,1, 9,1,1}, 
    			{5,1,2, 11,1,2}, 
    			{7,2,2, 9,2,2}, 
    			{7,1,3, 9,1,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickBlock, biomeStoneBrickMeta, biomeStoneBrickBlock, biomeStoneBrickMeta, false);	
    		}
    		
    		
    		// Mossy Stone Brick
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 1, this.materialType, this.biome, this.disallowModSubs); Block biomeMossyStoneBrickBlock = (Block)blockObject[0]; int biomeMossyStoneBrickMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Floor
    			{1,1,9, 1,1,10}, 
    			{2,1,7, 2,1,7}, 
    			{3,1,7, 3,1,7}, {3,1,9, 3,1,9}, 
    			{4,1,7, 4,1,8}, {4,1,10, 4,1,10}, 
    			{5,1,6, 5,1,8}, {5,1,10, 5,1,10}, 
    			{6,1,6, 7,1,6}, {6,1,8, 7,1,8}, 
    			{7,1,10, 7,1,10}, 
    			{8,1,7, 8,1,7}, {8,1,10, 8,1,10}, 
    			{9,1,6, 9,1,6}, {9,1,9, 9,1,9}, 
    			{10,1,6, 10,1,6}, {10,1,8, 10,1,8}, 
    			// Frame
    			{1,2,6, 1,2,6}, 
    			{1,3,10, 1,3,10}, 
    			{5,1,6, 5,3,6}, 
    			{5,2,10, 5,2,10}, 
    			// Roof
    			{1,5,8, 1,5,9}, {2,5,6, 3,5,6}, {4,5,6, 4,5,6}, {4,5,10, 4,5,10}, {5,5,7, 5,5,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeMossyStoneBrickBlock, biomeMossyStoneBrickMeta, biomeMossyStoneBrickBlock, biomeMossyStoneBrickMeta, false);	
    		}
    		
    		
    		// Chiseled Stone Brick
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeChiseledStoneBrickBlock = (Block)blockObject[0]; int biomeChiseledStoneBrickMeta = (Integer)blockObject[1];
    		for (int[] uvw : new int[][]{
    			// Frame
    			{1,4,10}, {5,4,10}, 
    			{1,4,6}, {5,4,6}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeChiseledStoneBrickBlock, biomeChiseledStoneBrickMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Stone Brick stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_brick_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Front Steps
    			{2,1,5, 0}, {3,1,5, 3}, {4,1,5, 1}, 
    			// Roof
    			{2,6,9, 0}, {3,6,9, 2}, {4,6,9, 1}, 
    			{2,6,8, 0}, {4,6,8, 1}, 
    			{2,6,7, 0}, {3,6,7, 3}, {4,6,7, 1}, 
    			// Workspace
    			{9,2,7, 3}, 
    			// Pile
    			{6,1,3, 0}, {8,1,1, 0}, {8,1,4, 2}, {10,1,1, 3}, {10,2,2, 2}, {10,1,3, 1}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeStoneBrickStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Stone Brick Slab (lower)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 5, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickSlabLowerBlock = (Block)blockObject[0]; int biomeStoneBrickSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uvwo : new int[][]{
    			{7,2,9}, {8,2,3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeStoneBrickSlabLowerBlock, biomeStoneBrickSlabLowerMeta, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
            
            
            // Smooth Stone Double Slab
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.double_stone_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePolishedStoneSlabDoubleBlock = (Block)blockObject[0]; int biomePolishedStoneSlabDoubleMeta = (Integer)blockObject[1];
            for (int[] uvwo : new int[][]{
            	{0,1,1}, {0,1,3}, {1,1,4}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomePolishedStoneSlabDoubleBlock, biomePolishedStoneSlabDoubleMeta, uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
    		
    		
    		// Smooth Stone Slab (lower)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePolishedStoneSlabLowerBlock = (Block)blockObject[0]; int biomePolishedStoneSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uvwo : new int[][]{
    			{0,2,3}, 
    			{1,1,2}, 
    			{6,2,2}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomePolishedStoneSlabLowerBlock, biomePolishedStoneSlabLowerMeta, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
        	
        	// Stone Brick wall
    		Object[] tryStoneBrickWall = ModObjects.chooseModStoneBrickWallBlock();
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(tryStoneBrickWall!=null?(Block)tryStoneBrickWall[0]:Blocks.cobblestone_wall, tryStoneBrickWall!=null?(Integer)tryStoneBrickWall[1]:0, this.materialType, this.biome, this.disallowModSubs);
        	Block biomeStoneBrickWallBlock = (Block)blockObject[0]; int biomeStoneBrickWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
            	{6,2,6, 10,2,6}, {10,2,7, 10,2,9}, {6,2,10, 10,2,10}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickWallBlock, biomeStoneBrickWallMeta, biomeStoneBrickWallBlock, biomeStoneBrickWallMeta, false);
            }
    		
    		
            // Terracotta
        	for (int[] uuvvww : new int[][]{
        		// Front wall
        		{2,2,6, 2,4,6}, {3,4,6, 3,4,6}, {4,2,6, 4,4,6}, 
        		// Right wall
        		{5,2,7, 5,4,7}, {5,4,8, 5,4,8}, {5,2,9, 5,4,9}, 
        		// Left wall
        		{1,2,7, 1,4,7}, {1,2,8, 1,2,8}, {1,4,8, 1,4,8}, {1,2,9, 1,4,9}, 
        		// Back wall
        		{2,2,10, 2,4,10}, {3,2,10, 3,2,10}, {3,4,10, 3,4,10}, {4,2,10, 4,4,10}, 
        		})
            {
        		// Cyan
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor4 : 9),
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor4 : 9), 
        				false);
            }
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// Front wall
    			{2,3,5, 2}, {4,3,5, 2}, 
        		// Right wall
    			{6,3,7, 1}, {6,3,9, 1}, 
        		// Left wall
    			{0,3,7, 3}, {0,3,9, 3}, 
        		// Back wall
    			{2,3,11, 0}, {4,3,11, 0}, 
    			// Interior
    			{3,4,9, 2}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Window awnings
    			{2,2,9, 1}, {4,2,9, 0}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
        		// Front wall
        		{1,3,8}, 
        		// Back wall
        		{3,3,10}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Rails
    		for(int[] uvwo : new int[][]{ // Orientation where track increases in elevation: 0=right-facing; 1=back-facing (toward you); 2=left-facing; 3=fore-facing (away from you)
    			// Left rail
    			{7,1,4, StructureVillageVN.chooseFurnaceMeta(1, this.coordBaseMode)},
    			{7,2,3, StructureVillageVN.chooseFurnaceMeta(1, this.coordBaseMode)},
    			{7,3,2, this.coordBaseMode%2==0? 1:0}, // Along
    			{7,2,1, StructureVillageVN.chooseFurnaceMeta(3, this.coordBaseMode)},
    			{7,1,0, StructureVillageVN.chooseFurnaceMeta(3, this.coordBaseMode)},
    			// Right rail
    			{9,1,4, StructureVillageVN.chooseFurnaceMeta(1, this.coordBaseMode)},
    			{9,2,3, StructureVillageVN.chooseFurnaceMeta(1, this.coordBaseMode)},
    			{9,3,2, this.coordBaseMode%2==0? 1:0}, // Along
    			{9,2,1, StructureVillageVN.chooseFurnaceMeta(3, this.coordBaseMode)},
    			{9,1,0, StructureVillageVN.chooseFurnaceMeta(3, this.coordBaseMode)},
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, Blocks.rail, uvwo[3], uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
        	
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{3,2,6, 2, 1, 0}, 
    			{5,2,8, 1, 1, 1}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
        	
        	
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		// Table
        	for (int[] uuvvwwo : new int[][]{
        		{3,2,9, -2}, 
        		})
            {
        		Object[][] tableComponentObjects = ModObjects.chooseModWoodenTable(biomePlankBlock==Blocks.planks ? biomePlankMeta : 0, uuvvwwo[3], this.coordBaseMode);
        		for (int i=1; i>=0; i--)
        		{
        			this.placeBlockAtCurrentPosition(world, (Block)tableComponentObjects[i][0], (Integer)tableComponentObjects[i][1], uuvvwwo[0], uuvvwwo[1]+1-i, uuvvwwo[2], structureBB);
        		}
            }
    		
        	
            // Stone Cutter
        	// Orientation:0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	blockObject = ModObjects.chooseModStonecutter(3, this.coordBaseMode, biomePlankMeta); Block stonecutterBlock = (Block) blockObject[0]; int stonecutterMeta = (Integer) blockObject[1];
            this.placeBlockAtCurrentPosition(world, stonecutterBlock, stonecutterMeta, 8, 2, 8, structureBB);
    		
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			int s = random.nextInt(30);
    			
    			// Villager
				int u = s<=3? 2 : s<=7? 3 : s<=11? 4 : s<=13? 2 : s<=15? 3 : s<=17? 4 : s<=20? 6 : s<=22? 7 : s<=24? 8 : 9;
				int v = s<=11? 1 : 2;
				int w = s<=3? s+1 : s<=7? s-3 : s<=11? s-10 : s<=13? s-5 : s<=15? s-7 : s<=17? s-9 : s<=20? s-11 : s<=22? s-14 : s<=23? s-16 : s<=24? s-15 : s-17;
    			
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
    // designed by AstroTibs, DelirusCrux, and THASSELHOFF
    
    public static class JungleMediumHouse1 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"FFFFFFFFFFFF",
    			"FFFFFFFFFFFF",
    			"FFFFFFFFFFFF",
    			"FFFFFFFFFFFF",
    			"FFFFFFFFFFFF",
    			"FFFFFFFFFFFF",
    			"FFFFFFFFFFFF",
    			"FFFFFFFFFFFF",
    			"FFFFFFFFFFFF",
    			"FFFFFFFFFFFF",
    			"FFFFFFFFFFFF",
    			"FFFFPPFFFFFF",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 13;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 1;
    	private static final int DECREASE_MAX_U = 2;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleMediumHouse1() {}

        public JungleMediumHouse1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleMediumHouse1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleMediumHouse1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			// Next to entry path
    			{3,0,0, 3,0,0}, {6,0,0, 6,0,0}, 
    			// Lower level garden foundation
    			{0,1,0, 3,1,0}, {6,1,0, 11,1,0}, 
    			{0,1,1, 0,1,2}, {11,1,1, 11,1,10}, 
    			{0,1,11, 11,1,11}, 
    			// Front stairs
    			{4,1,2, 5,1,2}, 
    			// Back stairs
    			{0,1,10, 1,2,10}, {0,1,11, 2,2,11}, {2,1,10, 2,1,10}, 
    			// Foundation and floor
    			{0,1,3, 9,2,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
        	
            
            // Cobblestone stairs
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
        	for (int[] uuvvwwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
        		// Entryway
        		{4,1,1, 5,1,1, 3}, {4,2,2, 5,2,2, 3}, 
        		// Back
        		{2,2,10, 2,2,10, 1}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], uuvvwwo[3], uuvvwwo[4], uuvvwwo[5], biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uuvvwwo[6]%4)+(uuvvwwo[6]/4)*4, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uuvvwwo[6]%4)+(uuvvwwo[6]/4)*4, false);	
            }
        	
        	
        	// Cobblestone wall
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		// Lower wall
        		{0,2,1, 0,2,2}, 
            	{0,2,0, 3,2,0}, {6,2,0, 11,2,0}, 
            	{11,2,1, 11,2,10}, 
            	{3,2,11, 11,2,11}, 
            	// Higher wall
            	{0,3,3, 3,3,3}, {6,3,3, 9,3,3}, {9,3,4, 9,3,4}, 
            	{0,3,11, 2,3,11}, {0,3,4, 0,3,11}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, false);
            }
    		
    		
    		// Logs (Vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Vertical
    			{3,3,5, 3,9,5}, {9,3,5, 9,9,5}, 
    			{3,3,9, 3,9,9}, {9,3,9, 9,9,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);	
    		}
    		
    		
            // Terracotta
        	for (int[] uuvvww : new int[][]{
        		// Front wall
        		{4,3,5, 4,7,5}, {4,9,5, 4,9,5}, 
        		{5,3,5, 5,3,5}, {5,5,5, 5,9,5}, 
        		{6,3,5, 6,6,5}, {6,9,5, 6,9,5}, 
        		{7,5,5, 7,9,5}, 
        		{8,3,5, 8,7,5}, {8,9,5, 8,9,5}, 
        		// Right wall
        		{3,3,6, 3,9,6}, 
        		{3,3,7, 3,3,7}, {3,5,7, 3,7,7}, {3,9,7, 3,9,7}, 
        		{3,3,8, 3,9,8}, 
        		// Left wall
        		{9,3,6, 9,9,6}, 
        		{9,3,7, 9,3,7}, {9,5,7, 9,7,7}, {9,9,7, 9,9,7}, 
        		{9,3,8, 9,9,8}, 
        		// Back wall
        		{4,3,9, 4,4,9}, {4,6,9, 4,7,9}, {4,9,9, 4,9,9}, 
        		{5,3,9, 5,9,9}, 
        		{6,3,9, 6,5,9}, {6,8,9, 6,9,9}, 
        		{7,3,9, 7,9,9}, 
        		{8,3,9, 8,4,9}, {8,6,9, 8,7,9}, {8,9,9, 8,9,9}, 
        		})
            {
        		// White
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor : 0),
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor : 0), 
        				false);
            }
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Tops of support posts
    			{3,6,3, 3,6,3}, {6,6,3, 6,6,3}, {9,6,3, 9,6,3}, 
    			// Roof
    			{3,10,3, 9,10,9}, {5,11,5, 7,11,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Side awning
    			{1,5,3, 1,5,10}, 
    			// Upper balcony
    			{4,6,3, 5,6,3}, {7,6,3, 8,6,3}, {3,6,4, 9,6,4}, 
    			// Interior floors
    			{4,6,6, 8,6,7}, {4,6,8, 4,6,8}, 
    			// Table
    			{8,3,8, 8,3,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Side awning
    			{0,5,3, 0,5,11}, {2,6,3, 2,6,9}, 
    			// Roof
    			{2,10,2, 10,10,2}, {2,10,10, 10,10,10}, {2,10,3, 2,10,9}, {10,10,3, 10,10,9}, 
    			{4,11,4, 8,11,4}, {4,11,8, 8,11,8}, {4,11,5, 4,11,7}, {8,11,5, 8,11,7}, 
    			{6,12,6, 6,12,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uuvvww : new int[][]{
        		// Awning
        		{0,4,3, 0,4,3}, {0,4,11, 0,4,11}, 
        		// Balcony railing
        		{3,7,3, 3,7,4}, {4,7,3, 8,7,3}, {9,7,3, 9,7,4}, 
        		// Supports
        		{3,4,3, 3,5,3}, {6,4,3, 6,5,3}, {9,4,3, 9,5,3}, 
        		{3,8,3, 3,9,3}, {9,8,3, 9,9,3}, 
        		// Interior
        		{5,9,8, 5,9,8}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Interior staircase
    			{5,3,8, 0}, {6,4,8, 0}, {7,5,8, 0}, {8,6,8, 0}, 
    			{6,3,8, 1+4}, {7,4,8, 1+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// Exterior fence
    			{0,3,0, -1}, {3,3,0, -1}, {6,3,0, -1}, {11,3,0, -1}, {2,4,11, -1}, {11,3,0, -1}, {11,3,11, -1}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
        	
        	
        	// Sitting Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(false); Block biomeSittingLanternBlock = (Block)blockObject[0]; int biomeSittingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	// On the table
            	{8,4,8}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeSittingLanternBlock, biomeSittingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	// Interior
            	{5,8,8}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
        		// Front wall
        		{5,4,5}, 
        		{4,8,5}, {8,8,5}, 
        		// Back wall
        		{4,5,9}, {8,5,9}, 
        		{6,6,9}, {6,7,9}, 
        		{4,8,9}, {8,8,9}, 
        		// Right wall
        		{3,4,7}, {3,8,7}, 
        		// Left wall
        		{9,4,7}, {9,8,7}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Trapdoor (Bottom Vertical)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapdoor, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	// Table
            	{8,3,7, 2}, 
            	{7,3,8, 3}, 
            	// Upstairs railing
            	{5,7,7, 2}, {6,7,7, 2}, {7,7,7, 2}, {5,7,8, 1}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, false, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
    		
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{7,3,5, 2, 1, 0}, 
    			{6,7,5, 2, 1, 1}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
    		
    		
    		// Grass - NOT biome-swapped
    		for (int[] uuvvww : new int[][]{
        		// Front shrubs
        		{1,1,1, 3,1,2}, {6,1,1, 10,1,2}, {10,1,3, 10,1,10}, {3,1,10, 9,1,10}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.grass, 0,
        				Blocks.grass, 0, 
        				false);
            }
    		
    		
    		// Tall Grass
    		for (int[] uvwg : new int[][]{ // g is grass type
    			{10,2,4, 0}, 
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
    		
        	
        	// Leaves
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.leaves, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLeafBlock = (Block)blockObject[0]; int biomeLeafMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		// Front shrubs
        		{1,2,1, 1,2,1}, {10,2,1, 10,2,1}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeLeafBlock, biomeLeafMeta,
        				biomeLeafBlock, biomeLeafMeta, 
        				false);
            }
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{4,7,7, 2, GeneralConfig.useVillageColors ? this.townColor3 : 0}, // Red
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
        	
        	
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1: rightward, 2:backward, 3: leftward
            		// Front side
        			{3,2,2, 2}, {3,3,2, 2}, {3,4,2, 2}, {3,5,2, 2}, {3,6,2, 2}, 
        			{9,2,2, 2}, {9,3,2, 2}, {9,4,2, 2}, {9,5,2, 2}, {9,6,2, 2}, 
        			// Right side
        			{10,8,6, 1}, {10,9,6, 1}, 
        			{10,9,7, 1}, 
        			// Back side
        			{5,9,10, 0}, 
        			{5,9,10, 0}, 
        			{5,3,10, 0}, {5,4,10, 0}, {5,5,10, 0}, {5,6,10, 0}, 
        			{6,6,10, 0}, {6,7,10, 0}, {6,8,10, 0}, {6,9,10, 0}, {6,10,10, 0}, 
        			{7,2,10, 0}, {7,3,10, 0}, {7,4,10, 0}, {7,5,10, 0}, {7,6,10, 0}, {7,7,10, 0}, {7,8,10, 0}, {7,9,10, 0}, 
        			{8,6,10, 0}, {8,7,10, 0}, 
        			{8,9,10, 0}, 
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
	        			{4,7,6, -1, 0},
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
    // designed by AstroTibs, DelirusCrux, and THASSELHOFF
    
    public static class JungleMediumHouse2 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"             ",
    			" FFFFFFFFFFF ",
    			" FFFFFFFFFFF ",
    			" FFFFFFFFFFF ",
    			" FFFFFFFFFFF ",
    			" FFFFFFFFFFF ",
    			" FFFFFFFFFFF ",
    			" FFFFFFFFFFF ",
    			" FFFFFFFFFFF ",
    			" FFFFFFFFFFF ",
    			"    F PP F   ",
    			"      PP     ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 12;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 3;
    	private static final int DECREASE_MAX_U = 4;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleMediumHouse2() {}

        public JungleMediumHouse2(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleMediumHouse2 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleMediumHouse2(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			// Lined foundation
    			{1,1,2, 5,1,2}, {8,1,2, 11,1,2}, 
    			{1,1,3, 1,1,9}, {11,1,3, 11,1,9}, 
    			{1,1,10, 11,1,10}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Logs (Vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Vertical
    			{1,5,2, 11,5,2}, {1,5,3, 1,5,9}, {11,5,3, 11,5,9}, {1,5,10, 11,5,10}, 
    			{1,8,2, 11,8,2}, {1,8,3, 1,8,9}, {11,8,3, 11,8,9}, {1,8,10, 11,8,10}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);	
    		}
    		
    		
    		// Stripped logs (Vertical)
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
    		for (int[] uuvvww : new int[][]{
    			{1,2,2, 1,4,2}, {11,2,2, 11,4,2}, {1,2,10, 1,4,10}, {11,2,10, 11,4,10}, 
    			{1,6,2, 1,7,2}, {11,6,2, 11,7,2}, {1,6,10, 1,7,10}, {11,6,10, 11,7,10}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
    		
    		
            // Terracotta
        	for (int[] uuvvww : new int[][]{
        		// Front wall
        		{2,4,2, 10,4,2}, 
        		{2,3,2, 2,3,2}, {4,3,2, 5,3,2}, {8,3,2, 8,3,2}, {10,3,2, 10,3,2}, 
        		{2,2,2, 5,2,2}, {8,2,2, 10,2,2}, 
        		{2,6,2, 2,7,2}, {4,6,2, 4,7,2}, {6,6,2, 6,7,2}, {8,6,2, 8,7,2}, {10,6,2, 10,7,2}, 
        		// Right wall 
        		{1,4,3, 1,4,9}, 
        		{1,3,3, 1,3,3}, {1,3,5, 1,3,5}, {1,3,7, 1,3,7}, {1,3,9, 1,3,9}, 
        		{1,2,3, 1,2,9}, 
        		{1,6,3, 1,7,3}, {1,6,5, 1,7,5}, {1,6,7, 1,7,7}, {1,6,9, 1,7,9}, 
        		{1,9,4, 1,9,5}, {1,10,6, 1,10,6},  {1,9,7, 1,9,8}, 
        		// Left wall 
        		{11,4,3, 11,4,9}, 
        		{11,3,3, 11,3,3}, {11,3,5, 11,3,5}, {11,3,7, 11,3,7}, {11,3,9, 11,3,9}, 
        		{11,2,3, 11,2,9}, 
        		{11,6,3, 11,7,3}, {11,6,5, 11,7,5}, {11,6,7, 11,7,7}, {11,6,9, 11,7,9}, 
        		{11,9,4, 11,9,5}, {11,10,6, 11,10,6}, {11,9,7, 11,9,8}, 
        		// Back wall 
        		{2,4,10, 10,4,10}, 
        		{2,3,10, 2,3,10}, {4,3,10, 4,3,10}, {6,3,10, 6,3,10}, {8,3,10, 8,3,10}, {10,3,10, 10,3,10}, 
        		{2,2,10, 10,2,10}, 
        		{2,6,10, 2,7,10}, {4,6,10, 4,7,10}, {6,6,10, 6,7,10}, {8,6,10, 8,7,10}, {10,6,10, 10,7,10}, 
        		})
            {
        		// White
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor : 0),
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor : 0), 
        				false);
            }
        	
        	
            // Glazed terracotta
        	Object[] tryGlazedTerracotta;
        	for (int[] uvwoc : new int[][]{ // u,v,w, orientation, dye color
        		// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
        		
        		// Above entryway
        		{6,3,2, 1, GeneralConfig.useVillageColors ? this.townColor : 0}, // White
        		{7,3,2, 2, GeneralConfig.useVillageColors ? this.townColor : 0}, // White
        		// Entry floor
        		{7,0,2, 0, GeneralConfig.useVillageColors ? this.townColor : 0}, // White
        		{6,0,2, 1, GeneralConfig.useVillageColors ? this.townColor : 0}, // White
        		{6,0,3, 2, GeneralConfig.useVillageColors ? this.townColor : 0}, // White
        		{7,0,3, 3, GeneralConfig.useVillageColors ? this.townColor : 0}, // White
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
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Tops of walls to connect to roof
    			{1,9,3, 1,9,3}, {1,10,5, 1,10,5}, {1,10,7, 1,10,7}, {1,9,9, 1,9,9}, 
    			{11,9,3, 11,9,3}, {11,10,5, 11,10,5}, {11,10,7, 11,10,7}, {11,9,9, 11,9,9}, 
    			// Floor
    			{2,1,3, 5,1,9}, {6,1,5, 7,1,9}, {8,1,3, 8,1,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{0,8,1, 12,8,1}, 
    			{0,9,3, 0,9,3}, {2,9,3, 10,9,3}, {12,9,3, 12,9,3}, 
    			{0,10,5, 0,10,5}, {2,10,5, 10,10,5}, {12,10,5, 12,10,5}, 
    			{0,10,7, 0,10,7}, {2,10,7, 10,10,7}, {12,10,7, 12,10,7}, 
    			{0,9,9, 0,9,9}, {2,9,9, 10,9,9}, {12,9,9, 12,9,9}, 
    			{0,8,11, 12,8,11}, 
    			// Upstairs floor
    			{2,4,3, 2,4,8}, 
    			{3,4,3, 5,4,9}, 
    			{6,4,3, 7,4,4}, {6,4,9, 7,4,9}, 
    			{8,4,3, 10,4,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{0,9,2, 12,9,2}, 
    			{0,10,4, 12,10,4}, 
    			{0,11,6, 12,11,6}, 
    			{0,10,8, 12,10,8}, 
    			{0,9,10, 12,9,10}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uuvvww : new int[][]{
        		// Ceiling lantern supports
        		{4,9,6, 4,10,6}, {8,9,6, 8,10,6}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Front entrance
    			{6,1,4, 3}, {7,1,4, 3}, 
    			// Chairs
    			{2,2,3, 2}, {2,2,5, 3}, 
    			// Upstairs
    			{6,2,6, 3}, {7,2,6, 3}, {6,2,7, 2+4}, {7,2,7, 2+4}, 
    			{6,3,7, 3}, {7,3,7, 3}, {6,3,8, 2+4}, {7,3,8, 2+4}, 
    			{6,4,8, 3}, {7,4,8, 3}, {6,4,9, 2+4}, {7,4,9, 2+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// First floor
    			{4,3,3, 0}, 
    			{4,3,9, 2}, {8,3,9, 2}, 
    			{10,3,5, 3}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	// Hanging from ceiling
            	{4,8,6}, {8,8,6},  
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
        		// Front wall
        		{3,3,2}, {9,3,2}, 
        		{3,6,2}, {3,7,2}, {5,6,2}, {5,7,2}, {7,6,2}, {7,7,2}, {9,6,2}, {9,7,2}, 
        		// Back wall
        		{3,3,10}, {5,3,10}, {7,3,10}, {9,3,10}, 
        		{3,6,10}, {3,7,10}, {5,6,10}, {5,7,10}, {7,6,10}, {7,7,10}, {9,6,10}, {9,7,10}, 
        		// Right wall
        		{1,3,4}, {1,3,6}, {1,3,8}, {1,6,4}, {1,7,4}, {1,6,6}, {1,7,6}, {1,6,8}, {1,7,8}, 
        		// Left wall
        		{11,3,4}, {11,3,6}, {11,3,8}, {11,6,4}, {11,7,4}, {11,6,6}, {11,7,6}, {11,6,8}, {11,7,8}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Trapdoor (Top Vertical)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapdoor, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	// Front planter
            	{3,1,1, 3}, {4,1,0, 2}, {5,1,1, 1}, {8,1,1, 3}, {9,1,0, 2}, {10,1,1, 1}, 
            	// Stairs
            	{5,2,7, 3}, {5,3,8, 3}, 
            	{8,2,7, 1}, {8,3,8, 1}, 
            	// Left attic vent
            	{0,9,6, 3}, {2,9,6, 1}, 
            	// Right attic vent
            	{10,9,6, 3}, {12,9,6, 1}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, false, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
            
            // Table
            for (int[] uuvvwwo : new int[][]{
        		{2,2,4, -2}, 
        		{10,2,4, 1}, 
        		{10,2,6, 1}, 
        		})
            {
            	Object[][] tableComponentObjects = ModObjects.chooseModWoodenTable(biomePlankBlock==Blocks.planks ? biomePlankMeta : 0, uuvvwwo[3], this.coordBaseMode);
        		for (int i=1; i>=0; i--)
        		{
        			this.placeBlockAtCurrentPosition(world, (Block)tableComponentObjects[i][0], (Integer)tableComponentObjects[i][1], uuvvwwo[0], uuvvwwo[1]+1-i, uuvvwwo[2], structureBB);
        		}
            }
            
        	
        	// Hanging Sign (Blank)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wall_sign, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWallSignBlock = (Block)blockObject[0];
        	for (int[] uvwo : new int[][]{ // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        		// Chair arm rests
        		{3,2,3, 1}, {3,2,5, 1}, 
        		})
            {
                int signX = this.getXWithOffset(uvwo[0], uvwo[2]);
                int signY = this.getYWithOffset(uvwo[1]);
                int signZ = this.getZWithOffset(uvwo[0], uvwo[2]);
                
            	world.setBlock(signX, signY, signZ, biomeWallSignBlock, StructureVillageVN.getSignRotationMeta(uvwo[3], this.coordBaseMode, true), 2); // Facing away from you
            }
            
        	
        	// Brick furnace chimney
        	
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
    			{2,3,9, 2,3,9}, {2,5,9, 2,8,9}, {2,10,9, 2,10,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeBrickWallBlock, biomeBrickWallMeta, biomeBrickWallBlock, biomeBrickWallMeta, false);	
    		}
        	
            // Brick Blocks
        	for(int[] uuvvww : new int[][]{
    			// Roof plugs
    			{2,4,9, 2,4,9}, {2,9,9, 2,9,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeBrickBlock, biomeBrickMeta, biomeBrickBlock, biomeBrickMeta, false);	
    		}
        	
        	
        	// Furnaces
            for (int[] uvwo : new int[][]{ // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
            	{2,2,9, 2}, 
            	})
            {
                this.placeBlockAtCurrentPosition(world, Blocks.furnace, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2]), StructureVillageVN.chooseFurnaceMeta(uvwo[3], this.coordBaseMode), 2);
            }
            
                    	
            // Polished Granite
            blockObject = ModObjects.chooseModPolishedGraniteBlockObject();
            if (blockObject==null) {blockObject = ModObjects.chooseModSmoothStoneBlockObject();} // Guarantees a vanilla stone if this fails
            Block polishedGraniteBlock = (Block) blockObject[0]; int polishedGraniteMeta = (Integer) blockObject[1];
        	for (int[] uvw : new int[][]{
        		{3,2,9}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, polishedGraniteBlock, polishedGraniteMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
            // Crafting Table
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.crafting_table, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCraftingTableBlock = (Block)blockObject[0]; int biomeCraftingTableMeta = (Integer)blockObject[1];
            for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
        		{4,2,9}, 
           		})
        	{
            	this.placeBlockAtCurrentPosition(world, biomeCraftingTableBlock, biomeCraftingTableMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
            
            // Bookshelves
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.bookshelf, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeBookshelfBlock = (Block)blockObject[0]; int biomeBookshelfMeta = (Integer)blockObject[1];
            for (int[] uuvvww : new int[][]{
        		{9,2,9, 9,2,9}, 
        		{10,2,9, 10,3,9}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeBookshelfBlock, biomeBookshelfMeta, biomeBookshelfBlock, biomeBookshelfMeta, false);
            }
            
            
            // Wool
            Block woolBlock = Blocks.wool; int woolMeta = (GeneralConfig.useVillageColors ? this.townColor : 0); // White
            for (int[] uuvvww : new int[][]{
            	{9,1,3, 10,1,9}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], woolBlock, woolMeta, woolBlock, woolMeta, false);
            }
    		
    		
        	// Patterned banners
    		Block testForBanner = ModObjects.chooseModBannerBlock(); // Checks to see if supported mod banners are available. Will be null if there aren't any.
			if (testForBanner!=null)
			{
    			for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color
    				// 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    				
    				{5,3,1, 2, 0}, // White
    				{8,3,1, 2, 0}, // White
    				{5,3,3, 0, 0}, // White
    				{8,3,3, 0, 0}, // White
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
    				world.setBlockMetadataWithNotify(bannerX, bannerY, bannerZ, StructureVillageVN.getSignRotationMeta(uvwoc[3], this.coordBaseMode, true), 2);
    				
    				// Set the tile entity
    				TileEntity bannerTileEntity = world.getTileEntity(bannerX, bannerY, bannerZ);
    				if (bannerTileEntity==null) {bannerTileEntity = new TileEntityBanner();}
    				NBTTagCompound bannerNBTTagCompound = new NBTTagCompound();
    				bannerTileEntity.writeToNBT(bannerNBTTagCompound);
    				bannerNBTTagCompound.setBoolean("IsStanding", !isHanging);
    				
    				if (GeneralConfig.useVillageColors)
    				{
    					NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, 
    	            			(this.boundingBox.minX+this.boundingBox.maxX)/2,
    	            			(this.boundingBox.minY+this.boundingBox.maxY)/2,
    	            			(this.boundingBox.minZ+this.boundingBox.maxZ)/2);
    					
    					ItemStack bannerItemStack = ModObjects.chooseModBannerItem();
        				bannerItemStack.setTagInfo("BlockEntityTag", villageNBTtag.getCompoundTag("BlockEntityTag"));
        				
        				bannerNBTTagCompound = FunctionsVN.setItemValues(bannerItemStack, bannerNBTTagCompound);
    				}
    				else
    				{
    					bannerNBTTagCompound.setInteger("Base", uvwoc[4]);
    				}
    				bannerTileEntity.readFromNBT(bannerNBTTagCompound);
    				
    				world.setTileEntity(bannerX, bannerY, bannerZ, bannerTileEntity);
    			}
			}
    		
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{6,1,2, 2, 1, 1}, 
    			{7,1,2, 2, 1, 0}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
    		
    		
    		// Grass - NOT biome-swapped
    		for (int[] uuvvww : new int[][]{
        		// Front planters
        		{4,1,1, 4,1,1}, {9,1,1, 9,1,1}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.grass, 0,
        				Blocks.grass, 0, 
        				false);
            }
        	

            // Ferns
            for (int[] uwg : new int[][]{ // g is grass type
            	{4,2,1, 0},
            	{9,2,1, 0},
            })
            {
    			if (uwg[3]==0) // Fern
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.tallgrass, 2, uwg[0], uwg[1], uwg[2], structureBB);
    			}
    			else // Large Fern
    			{
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 3, uwg[0], uwg[1], uwg[2], structureBB);
    				this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 11, uwg[0], uwg[1]+1, uwg[2], structureBB);
    			}
            }
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{3,5,8, 2, GeneralConfig.useVillageColors ? this.townColor : 0}, // White
            	{9,5,8, 2, GeneralConfig.useVillageColors ? this.townColor : 0}, // White
            	{3,5,4, 0, GeneralConfig.useVillageColors ? this.townColor : 0}, // White
            	{9,5,4, 0, GeneralConfig.useVillageColors ? this.townColor : 0}, // White
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
	        			{4,5,8, -1, 0}, {8,5,8, -1, 0}, 
	        			{4,5,4, -1, 0}, {8,5,4, -1, 0}, 
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
    
    
    // --- Medium House 3 --- //
    // designed by AstroTibs
    
    public static class JungleMediumHouse3 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"    FFF    ",
    			"   FPPPF   ",
    			"  FPPPPPF  ",
    			" FPPPPPPPF ",
    			"FFPPFPFPPFF",
    			"FFPPPPPPPFF",
    			"FFPPFPFPPFF",
    			" FPPPPPPPF ",
    			"  FPPPPPF  ",
    			"   FPPPF   ",
    			"    FFF    ",
    			"    PPP    ",
    			"  FFPPPFF  ",
    			"  FPPPPPF  ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 8;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1+4; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 2;
    	private static final int DECREASE_MAX_U = 3;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleMediumHouse3() {}

        public JungleMediumHouse3(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleMediumHouse3 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleMediumHouse3(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Hay bales (vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.hay_block, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeHayBaleVertBlock = (Block)blockObject[0]; int biomeHayBaleVertMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{4,1,3, 4,2,3}, {6,1,3, 6,2,3}, 
    			{3,1,4, 3,2,4}, {7,1,4, 7,2,4}, 
    			{2,1,5, 2,2,5}, {8,1,5, 8,2,5}, 
    			{1,1,6, 1,2,6}, {9,1,6, 9,2,6}, 
    			{0,1,7, 0,2,9}, {10,1,7, 10,2,9}, 
    			{1,1,10, 1,2,10}, {9,1,10, 9,2,10}, 
    			{2,1,11, 2,2,11}, {8,1,11, 8,2,11}, 
    			{3,1,12, 3,2,12}, {7,1,12, 7,2,12}, 
    			{4,1,13, 4,2,13}, {6,1,13, 6,2,13}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeHayBaleVertBlock, biomeHayBaleVertMeta, biomeHayBaleVertBlock, biomeHayBaleVertMeta, false);	
    		}
    		
    		
    		// Logs (Vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Front garden
    			{2,1,0, 2,1,0}, {3,1,1, 3,1,1}, 
    			{8,1,0, 8,1,0}, {7,1,1, 7,1,1}, 
    			// Wall toppers
    			{1,3,6, 1,3,6}, {2,3,5, 2,3,5}, {8,3,5, 8,3,5}, {9,3,6, 9,3,6}, 
    			{1,3,10, 1,3,10}, {2,3,11, 2,3,11}, {8,3,11, 8,3,11}, {9,3,10, 9,3,10}, 
    			{1,3,6, 1,3,6}, {2,3,5, 2,3,5}, {8,3,5, 8,3,5}, {9,3,6, 9,3,6}, 
    			// Central spire
    			{4,5,9, 4,6,9}, {6,5,9, 6,6,9}, 
    			{4,5,7, 4,6,7}, {6,5,7, 6,6,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);	
    		}
    		
    		
    		// Logs (Across)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{3,3,4, 7,3,4}, 
    			{3,3,12, 7,3,12}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, false);	
    		}
    		
    		
    		// Logs (Along)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{0,3,7, 0,3,9}, {10,3,7, 10,3,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAlongBlock, biomeLogHorAlongMeta, biomeLogHorAlongBlock, biomeLogHorAlongMeta, false);	
    		}
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{3,4,11, 7,4,11}, 
    			{2,4,10, 3,4,10}, {7,4,10, 8,4,10}, 
    			{1,4,8, 1,4,8}, {2,4,7, 2,4,9}, {8,4,7, 8,4,9}, {9,4,8, 9,4,8}, 
    			{2,4,6, 3,4,6}, {7,4,6, 8,4,6}, 
    			{3,4,5, 7,4,5}, 
    			
    			{5,5,10, 5,5,10}, 
    			{3,5,8, 3,5,8}, {7,5,8, 7,5,8}, 
    			{5,5,6, 5,5,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{5,7,8, 5,7,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{4,7,9, 4,7,9}, {6,7,9, 6,7,9}, 
    			{4,7,7, 4,7,7}, {6,7,7, 6,7,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uuvvww : new int[][]{
    			{4,1,9, 4,4,9}, {6,1,9, 6,4,9}, 
    			{4,1,7, 4,4,7}, {6,1,7, 6,4,7}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
        	
        	
        	// Dirt
        	for (int[] uuvvww : new int[][]{
    			{4,0,9, 3,0,9}, {6,0,9, 5,0,9}, 
    			{4,0,7, 3,0,7}, {6,0,7, 5,0,7}, 
            	{1,1,7, 1,1,9}, 
            	{9,1,7, 9,1,9}, 
            	// Under doors
            	{5,0,13, 5,0,13}, 
            	{5,0,3, 5,0,3}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeFillerBlock, biomeFillerMeta,
        				biomeFillerBlock, biomeFillerMeta, 
        				false);
            }
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Over back entrance
    			{4,3,13, 2}, {5,3,13, 2}, {6,3,13, 2}, 
    			// Roof
    			{3,4,12, 2}, {4,4,12, 2}, {5,4,12, 2}, {6,4,12, 2}, {7,4,12, 2}, 
    			{2,4,11, 2}, {3,4,11, 0}, {7,4,11, 1}, {8,4,11, 2}, 
    			{1,4,10, 2}, {2,4,10, 0}, {8,4,10, 1}, {9,4,10, 2}, 
    			{0,4,9, 2}, {1,4,9, 0}, {9,4,9, 1}, {10,4,9, 2}, 
    			{0,4,8, 0}, {10,4,8, 1}, 
    			{0,4,7, 3}, {1,4,7, 0}, {9,4,7, 1}, {10,4,7, 3}, 
    			{1,4,6, 3}, {2,4,6, 0}, {8,4,6, 1}, {9,4,6, 3}, 
    			{2,4,5, 3}, {3,4,5, 0}, {7,4,5, 1}, {8,4,5, 3}, 
    			{3,4,4, 3}, {4,4,4, 3}, {5,4,4, 3}, {6,4,4, 3}, {7,4,4, 3}, 
    			
    			{3,5,9, 0}, {7,5,9, 1}, 
    			{3,5,7, 0}, {7,5,7, 1}, 
    			
    			{4,5,11, 2}, {5,5,11, 2}, {6,5,11, 2}, 
    			{3,5,10, 2}, {4,5,10, 0}, {6,5,10, 1}, {7,5,10, 2}, 
    			{2,5,9, 2}, {8,5,9, 2}, 
    			{2,5,8, 0}, {8,5,8, 1}, 
    			{2,5,7, 3}, {8,5,7, 3}, 
    			{3,5,6, 3}, {4,5,6, 0}, {6,5,6, 1}, {7,5,6, 3}, 
    			{4,5,5, 3}, {5,5,5, 3}, {6,5,5, 3}, 
    			
    			{5,7,9, 2}, 
    			{4,7,8, 0}, {6,7,8, 1}, 
    			{5,7,7, 3}, 
    			
    			// Over front entrance
    			{4,3,3, 3}, {5,3,3, 3}, {6,3,3, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// First floor
    			{5,4,10, 2}, 
    			{1,3,8, 1}, {9,3,8, 3}, 
    			{5,4,6, 0}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
        	
        	
            // Crafting Table
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.crafting_table, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCraftingTableBlock = (Block)blockObject[0]; int biomeCraftingTableMeta = (Integer)blockObject[1];
            for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
        		{1,1,9}, 
           		})
        	{
            	this.placeBlockAtCurrentPosition(world, biomeCraftingTableBlock, biomeCraftingTableMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 9;
        	int chestV = 1;
        	int chestW = 9;
        	int chestO = 3; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo(ChestLootHandler.getGenericLootForVillageType(this.villageType));
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{1,1,7, 2, GeneralConfig.useVillageColors ? this.townColor3 : 14}, // Red
            	{9,1,7, 2, GeneralConfig.useVillageColors ? this.townColor3 : 14}, // Red
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
    		
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{5,1,3, 2, 1, 1}, 
    			{5,1,13, 0, 1, 0}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
            
        	
            // Tall Grass
            for (int[] uvwg : new int[][]{ // g is grass type
            	{2,1,1, 1}, {8,1,1, 1}, 
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
        	
        	
    		// Villagers
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	if (VillageGeneratorConfigHandler.spawnVillagersInResidences)
            	{
	            	int[][] villagerPositions = new int[][]{
	        			{2,1,7, -1, 0}, {8,1,7, -1, 0}, 
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
    
    
    // --- Medium House 4 --- //
    // designed by jss2a98aj
    
    public static class JungleMediumHouse4 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"  FFFFF  ",
    			" FFFFFFF ",
    			" FFFFFFF ",
    			" FFFFFFF ",
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
    	public static final int STRUCTURE_HEIGHT = 7;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1+4; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 2;
    	private static final int DECREASE_MAX_U = 2;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleMediumHouse4() {}

        public JungleMediumHouse4(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleMediumHouse4 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleMediumHouse4(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			{0,1,2, 8,1,6}, 
    			// Back porch
    			{1,0,7, 1,0,9}, {2,0,9, 2,0,10}, {4,0,7, 4,0,7}, {6,0,9, 6,0,10}, {7,0,7, 7,0,9}, 
    			{3,0,8, 5,0,10}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Cobblestone stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			{3,1,1, 1}, {4,1,1, 3}, {5,1,1, 0}, 
    			{4,1,7, 2}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Grass
    		for(int[] uuvvww : new int[][]{
    			{1,1,8, 1,1,8}, {7,1,8, 7,1,8}, 
    			{1,1,7, 2,1,7}, {6,1,7, 7,1,7}, 
    			{1,1,1, 2,1,1}, {6,1,1, 7,1,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.grass, 0, Blocks.grass, 0, false);	
    		}
    		
    		
    		// Logs (Vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Front garden
    			{0,1,0, 0,1,0}, {2,1,0, 2,1,0}, {6,1,0, 6,1,0}, {8,1,0, 8,1,0}, 
    			// House frames
    			{0,2,6, 0,4,6}, {3,2,6, 3,4,6}, {5,2,6, 5,4,6}, {8,2,6, 8,4,6}, 
    			{0,2,2, 0,4,2}, {3,2,2, 3,4,2}, {5,2,2, 5,4,2}, {8,2,2, 8,4,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);	
    		}
    		
    		
    		// Logs (Along)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{3,5,2, 3,5,6}, {5,5,2, 5,5,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAlongBlock, biomeLogHorAlongMeta, biomeLogHorAlongBlock, biomeLogHorAlongMeta, false);	
    		}
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Walls
    			{1,4,6, 2,4,6}, {6,4,6, 7,4,6}, {1,2,6, 2,2,6}, {6,2,6, 7,2,6}, 
    			{1,4,2, 2,4,2}, {6,4,2, 7,4,2}, {1,2,2, 2,2,2}, {6,2,2, 7,2,2},
    			{0,2,3, 0,4,5}, {8,2,3, 8,4,5}, 
    			// Roof
    			{2,5,2, 2,5,6}, {6,5,2, 6,5,6}, 
    			// Above doorways
    			{4,4,6, 4,4,6}, 
    			{4,4,2, 4,4,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
            
            
            // Glass blocks
        	for (int[] uvw : new int[][]{
        		{1,3,2}, {2,3,2}, {6,3,2}, {7,3,2}, 
        		{1,3,6}, {2,3,6}, {6,3,6}, {7,3,6}, 
        		})
            {
    			this.placeBlockAtCurrentPosition(world, Blocks.glass, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{0,5,2, 0,5,6}, {3,6,2, 5,6,6}, {8,5,2, 8,5,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Under front fence
    			{0,1,1, 0+4}, {1,1,0, 3+4}, {7,1,0, 3+4}, {8,1,1, 1+4}, 
    			// Roof
    			{1,5,2, 0}, {1,5,3, 0}, {1,5,4, 0}, {1,5,5, 0}, {1,5,6, 0}, 
    			{7,5,2, 1}, {7,5,3, 1}, {7,5,4, 1}, {7,5,5, 1}, {7,5,6, 1},
    			// Table
    			{7,2,3, 2+4}, {7,2,5, 3+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uuvvww : new int[][]{
    			{0,2,0, 0,2,1}, {1,2,0, 2,2,0}, {6,2,0, 7,2,0}, {8,2,0, 8,2,1}, 
    			{4,5,1, 4,5,2}, {4,5,4, 4,5,4}, {4,5,6, 4,5,7}, 
    			// Back
    			{1,1,7, 1,1,9}, {2,1,9, 2,1,10}, {3,1,10, 3,1,10}, 
    			{7,1,7, 7,1,9}, {6,1,9, 6,1,10}, {5,1,10, 5,1,10}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
            
            
        	// Fence Gate (Across)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence_gate, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceGateBlock = (Block)blockObject[0]; int biomeFenceGateMeta = (Integer)blockObject[1];
        	for(int[] uvw : new int[][]{
            	{4,1,10}, 
            	})
            {
        		this.placeBlockAtCurrentPosition(world, biomeFenceGateBlock, StructureVillageVN.getMetadataWithOffset(biomeFenceGateBlock, biomeFenceGateMeta, this.coordBaseMode), uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	{4,4,1}, {4,4,4}, {4,4,7}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
            // Crafting Table
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.crafting_table, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCraftingTableBlock = (Block)blockObject[0]; int biomeCraftingTableMeta = (Integer)blockObject[1];
            for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
        		{7,2,4}, 
           		})
        	{
            	this.placeBlockAtCurrentPosition(world, biomeCraftingTableBlock, biomeCraftingTableMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
        	// Carpet
        	for(int[] uuvvww : new int[][]{
        		// Lower
        		{4,2,5, 4,2,5, (GeneralConfig.useVillageColors ? this.townColor5 : 1)}, // Orange
        		{3,2,4, 5,2,4, (GeneralConfig.useVillageColors ? this.townColor5 : 1)}, // Orange
        		{4,2,3, 4,2,3, (GeneralConfig.useVillageColors ? this.townColor5 : 1)}, // Orange
        		})
            {
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.carpet, uuvvww[6], Blocks.carpet, uuvvww[6], false);	
            }
            
            
            // Trapdoor (Bottom Horizontal)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapdoor, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{6,2,4, 3}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, false, true), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{2,2,5, 1, GeneralConfig.useVillageColors ? this.townColor3 : 14}, // Red
            	{2,2,3, 1, GeneralConfig.useVillageColors ? this.townColor3 : 14}, // Red
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
    		
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{4,2,2, 2, 1, 0}, 
    			{4,2,6, 0, 1, 1}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
    		
    		
            // Poppies
            for (int[] uvw : new int[][]{
            	{2,1,8}, {3,1,7}, {5,1,7}, {6,1,8}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.red_flower, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
            // Peony
            for (int[] uvw : new int[][]{
        		{1,2,1}, {2,2,1}, {6,2,1}, {7,2,1}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 5, uvw[0], uvw[1], uvw[2], structureBB);
        		this.placeBlockAtCurrentPosition(world, Blocks.double_plant, 11, uvw[0], uvw[1]+1, uvw[2], structureBB);
            }
            
            
            // Leaves
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.leaves, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeLeafBlock = (Block)blockObject[0]; int biomeLeafMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
        		{2,1,7}, {6,1,7}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeLeafBlock, biomeLeafMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward, 3:leftward
            		// Leftward
        			{-1,4,4, 3}, {-1,4,5, 3}, {-1,3,5, 3}, {-1,3,6, 3}, {-1,2,6, 3}, {-1,1,6, 3}, 
        			// Rightward
        			{9,4,2, 1}, {9,3,2, 1}, {9,2,2, 1}, {9,1,2, 1}, {9,4,3, 1}, {9,3,3, 1}, {9,4,5, 1}, {9,4,6, 1}, {9,3,6, 1}, 
        			// Forward
        			{5,2,7, 0}, {5,3,7, 0}, {5,4,7, 0}, {8,4,7, 0}, 
        			// Backward
        			{1,4,1, 2}, {2,4,1, 2}, {2,5,1, 2}, {3,5,1, 2}, {8,3,1, 2}, 
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
            	
            	// Villagers
            	if (VillageGeneratorConfigHandler.spawnVillagersInResidences)
            	{
	            	int[][] villagerPositions = new int[][]{
	        			{3,2,5, -1, 0}, 
	        			{3,2,3, -1, 0}, 
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
            	
                
            	// Painting
                for(int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
                	{0,3,4, 1}, 
                	})
                {
                	int x = this.getXWithOffset(uvwo[0], uvwo[2]);
                	int y = this.getYWithOffset(uvwo[1]);
                	int z = this.getZWithOffset(uvwo[0], uvwo[2]);
                	EntityPainting painting = new EntityPainting(world, x, y, z, StructureVillageVN.chooseHangingMeta(uvwo[3], this.coordBaseMode));
                	
                	if (painting.onValidSurface())
                	{
                    	// Set art
                		EntityPainting.EnumArt[] a_1x1_paintings = new EntityPainting.EnumArt[]{
                    			EntityPainting.EnumArt.Kebab,
                    			EntityPainting.EnumArt.Aztec,
                    			EntityPainting.EnumArt.Alban,
                    			EntityPainting.EnumArt.Aztec2,
                    			EntityPainting.EnumArt.Bomb,
                    			EntityPainting.EnumArt.Plant,
                    			EntityPainting.EnumArt.Wasteland,
                    	};
                    	painting.art = a_1x1_paintings[random.nextInt(a_1x1_paintings.length)];
                    	
                    	world.spawnEntityInWorld(painting);
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
    
    
    // --- Shepherd House --- //
    // designed by AstroTibs
    
    public static class JungleShepherdHouse extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
        private static final String[] foundationPattern = new String[]{
    			"  FFFFFF ",
    			" FFFFFFF ",
    			"FFFFFFFF ",
    			"FFFFFFFFF",
    			"FFFFFFFFF",
    			"FFFFFFFFF",
    			"FFFFFFFFF",
    			"FFFFFFFFF",
    			"   FFFFFF",
    			"      P  ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 7;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 4;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	public JungleShepherdHouse() {}

    	public JungleShepherdHouse(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
    	}
    	
    	public static JungleShepherdHouse buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new JungleShepherdHouse(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			{4,1,6, 7,1,6}, 
    			{3,1,2, 3,1,3}, {3,1,5, 3,1,5}, {8,1,2, 8,1,5}, 
    			{4,1,1, 5,1,1}, {7,1,1, 7,1,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Floor
    			{3,0,2, 3,0,5}, {4,0,1, 7,0,6}, {8,0,2, 8,0,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
    		
    		
    		// Wood stairs 1
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Table
    			{4,1,2, 1+4}, {4,1,3, 3+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uuvvww : new int[][]{
        		// Interior
        		{4,1,5, 4,4,5}, {7,4,5, 7,4,5}, 
        		{4,4,2, 4,4,2}, {7,4,2, 7,4,2}, 
        		// Exterior
        		{3,5,6, 3,5,6}, {8,5,6, 8,5,6}, 
        		{3,5,1, 3,5,1}, {8,5,1, 8,5,1}, 
        		{3,3,0, 3,3,0}, {8,3,0, 8,3,0}, 
        		// Sheep pen
        		{3,1,9, 7,1,9}, {7,1,7, 7,1,8}, 
        		{2,1,8, 2,1,9}, 
        		{1,1,7, 1,1,8}, 
        		{0,1,3, 0,1,7}, 
        		{0,1,2, 2,1,2}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
        	
    		
    		// Unkempt Grass
    		for (int[] uvwg : new int[][]{
    			{1,1,3, 0}, {2,1,3, 0}, 
    			{1,1,4, 0}, {2,1,4, 0}, 
    			{1,1,5, 0}, {2,1,5, 0}, 
    			{1,1,6, 0}, {2,1,6, 0}, 
    			{2,1,7, 0}, {3,1,7, 0}, {4,1,7, 0}, {5,1,7, 0}, {6,1,7, 0}, 
    			{3,1,8, 0}, {4,1,8, 0}, {5,1,8, 0}, {6,1,8, 0}, 
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
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// Inside
    			{4,2,3, -1}, 
    			// Sheep pen
    			{0,2,2, -1}, 
    			{0,2,7, -1}, {2,2,9, -1}, {7,2,9, -1}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		
    		// Logs (Across)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Shed
    			{4,4,6, 7,4,6}, 
    			{4,4,1, 7,4,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, false);	
    		}
    		
    		
    		// Logs (Along)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Longer log
    			{3,4,2, 3,4,5}, {8,4,2, 8,4,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAlongBlock, biomeLogHorAlongMeta, biomeLogHorAlongBlock, biomeLogHorAlongMeta, false);	
    		}
    		
    		
    		// For stripped logs specifically
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, materialType, biome, disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
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
    		for (int[] uuvvww : new int[][]{
    			{3,0,6, 3,4,6}, {8,0,6, 8,4,6}, 
    			{3,0,1, 3,4,1}, {8,0,1, 8,4,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
    		
    		
    		// Wood stairs 2
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Front face
    			{4,3,1, 3+4}, {5,3,1, 3}, {6,3,1, 3+4}, {7,3,1, 3}, 
    			{4,2,1, 3+4}, {5,2,1, 3}, {7,2,1, 3}, 
    			// Back face
    			{4,3,6, 2}, {5,3,6, 2+4}, {6,3,6, 2}, {7,3,6, 2+4}, 
    			{4,2,6, 2}, {5,2,6, 2+4}, {6,2,6, 2}, {7,2,6, 2+4}, 
    			// Left wall
    			{3,3,2, 0}, {3,3,3, 0+4}, {3,3,4, 0}, {3,3,5, 0+4}, 
    			{3,2,2, 0}, {3,2,3, 0+4}, {3,2,5, 0+4}, 
    			// Right wall
    			{8,3,2, 1+4}, {8,3,3, 1}, {8,3,4, 1+4}, {8,3,5, 1}, 
    			{8,2,2, 1+4}, {8,2,3, 1}, {8,2,4, 1+4}, {8,2,5, 1}, 
    			// Roof
    			{4,5,6, 1+4}, {7,5,6, 0+4}, 
    			{3,5,5, 3+4}, {8,5,5, 3+4}, 
    			{3,5,2, 2+4}, {8,5,2, 2+4}, 
    			{4,5,1, 1+4}, {7,5,1, 0+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{5,5,6, 6,5,6}, 
    			{3,5,3, 3,5,4}, {8,5,3, 8,5,4}, 
    			{5,5,1, 6,5,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{4,6,2, 7,6,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{6,1,1, 0, 1, 0}, 
    			{3,1,4, 1, 1, 0}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	{3,2,0}, {8,2,0}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
        	// Carpet
        	for(int[] uvwm : new int[][]{
        		// Lower
        		{5,1,3, (GeneralConfig.useVillageColors ? this.townColor3 : 14)}, // Red
        		{6,1,3, (GeneralConfig.useVillageColors ? this.townColor : 0)}, // White
        		{6,1,4, (GeneralConfig.useVillageColors ? this.townColor3 : 14)}, // Red
        		{5,1,4, (GeneralConfig.useVillageColors ? this.townColor : 0)}, // White
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.carpet, uvwm[3], uvwm[0], uvwm[1], uvwm[2], structureBB); 
            }
        	
        	
            // Loom
        	blockObject = ModObjects.chooseModLoom(2, this.coordBaseMode, biomePlankMeta); Block loomBlock = (Block) blockObject[0]; int loomMeta = (Integer) blockObject[1];
            for(int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	// Back Shutters
            	{7,1,5}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, loomBlock, loomMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 4;
        	int chestV = 2;
        	int chestW = 2;
        	int chestO = 1; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo(Reference.VN_SHEPHERD);
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
    		
    		
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward, 3:leftward
            		// Leftward
        			{2,4,1, 3}, {2,3,1, 3}, 
        			// Rightward
        			{9,4,3, 1}, {9,3,3, 1}, {9,2,3, 1}, {9,1,3, 1}, {9,0,3, 1}, 
        			{9,4,4, 1}, {9,3,4, 1}, 
        			// Forward
        			{4,4,7, 0}, {5,4,7, 0}, 
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
    			
    			// Villager
    			int u = 7;
    			int v = 1;
    			int w = 5;
    			
    			while (u==7 && w==5)
    			{
    				u = 5+random.nextInt(3);
    				w = 2+random.nextInt(4);
    			}
    			
    			EntityVillager entityvillager = StructureVillageVN.makeVillagerWithProfession(world, random, 0, 3, 0); // Shepherd
    			
    			entityvillager.setLocationAndAngles((double)this.getXWithOffset(u, w) + 0.5D, (double)this.getYWithOffset(v) + 0.5D, (double)this.getZWithOffset(u, w) + 0.5D, random.nextFloat()*360F, 0.0F);
    			world.spawnEntityInWorld(entityvillager);
    			
            	if (VillageGeneratorConfigHandler.villageAnimalRestrictionLevel<1)
            	{
	                // Sheep in the yard
	            	for (int[] uvw : new int[][]{
	        			{5, 1, 7},
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
    
    public static class JungleSmallHouse1 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"            ",
    			"FFFFFFFFFFFF",
    			"FFFFFFFFFFFF",
    			"FFFFFFFFFFFF",
    			"FFFFFFFFFFFF",
    			"FFFFFFFFFFFF",
    			"  FFFFFFFFFF",
    			"  FFFFFFFFFF",
    			"  FFF  FFFFF",
    			"  FFF  FFFFF",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 9;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 2;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleSmallHouse1() {}

        public JungleSmallHouse1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleSmallHouse1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleSmallHouse1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
            // Terracotta
        	for (int[] uuvvww : new int[][]{
        		// Side porch
        		{0,0,4, 1,0,8}, 
        		{2,0,1, 2,0,8}, 
        		// Front porch
        		{4,0,1, 4,0,1}, 
        		{3,0,2, 8,0,4}, 
        		{7,0,0, 8,0,1}, 
        		{9,0,1, 9,0,2}, 
        		{10,0,0, 11,0,2}, 
        		// Front wall
        		{2,1,4, 2,6,4}, 
        		{3,3,4, 3,3,4}, {3,6,4, 3,6,4}, 
        		{4,1,4, 4,6,4}, 
        		{5,1,4, 6,1,4}, {5,3,4, 6,4,4}, {5,6,4, 6,6,4},
        		{7,1,4, 8,6,4}, 
        		{8,1,2, 8,3,3}, {8,4,2, 8,5,2}, 
        		{10,1,2, 11,3,2}, {10,4,2, 10,5,2}, 
        		{9,3,2, 9,3,2}, 
        		{8,6,2, 10,6,2}, 
        		// Left wall
        		{2,1,5, 2,6,5}, {2,3,6, 2,6,7}, {2,1,6, 2,6,8}, 
        		// Right wall
        		{11,0,3, 11,3,3}, 
        		{11,0,4, 11,1,4}, {11,3,4, 11,3,4}, 
        		{11,0,5, 11,3,5}, 
        		{11,0,6, 11,1,6}, {11,3,6, 11,3,6}, 
        		{11,0,7, 11,3,8}, 
        		// Upstairs right wall
        		{10,4,4, 10,4,4}, {10,4,6, 10,4,6}, 
        		{10,4,3, 10,6,3}, 
        		{10,4,4, 10,4,4}, {10,5,4, 10,6,4}, 
        		{10,4,5, 10,6,5}, 
        		{10,4,6, 10,4,6}, {10,5,6, 10,6,6}, 
        		{10,4,7, 10,6,8}, 
        		// Back wall
        		{3,0,8, 3,1,8}, {3,3,8, 3,4,8}, {3,6,8, 3,6,8}, 
        		{4,0,8, 4,6,8}, 
        		{5,0,8, 6,1,8}, {5,3,8, 6,4,8}, {5,6,8, 6,6,8},
        		{7,0,8, 8,6,8}, 
        		{9,0,8, 9,1,8}, {9,3,8, 9,4,8}, {9,6,8, 9,6,8},
        		{10,0,8, 10,3,8}, 
        		// Upper floor balcony
        		// Front
        		{2,3,2, 7,3,3}, 
        		{7,3,0, 11,3,1}, 
        		})
            {
        		// Yellow
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor2 : 4),
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor2 : 4), 
        				false);
            }
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Floor
    			{3,0,5, 10,0,7}, 
    			{9,0,3, 10,0,4}, 
    			// Roof
    			{4,8,6, 8,8,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// Interior
    			// First floor
    			{4,2,7, 2}, {10,2,5, 3}, 
    			// Second floor
    			{3,6,6, 1}, {9,6,6, 3}, {9,6,3, 0}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uuvvww : new int[][]{
        		// First floor posts
        		{0,1,4, 0,2,4}, {0,1,8, 0,2,8}, 
        		{2,1,2, 2,2,2}, {4,1,2, 4,2,2}, {6,1,2, 6,2,2}, {7,1,0, 7,2,0}, {11,1,0, 11,2,0}, 
        		// Railing
        		{2,4,2, 2,4,3}, {3,4,2, 7,4,2}, {7,4,0, 7,4,1}, {8,4,0, 11,4,0}, {11,4,0, 11,4,8}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Left awning
    			{0,3,4, 3}, {1,3,4, 3}, 
    			{0,3,5, 0}, {0,3,6, 0}, {0,3,7, 0}, 
    			{0,3,8, 2}, {1,3,8, 2}, 
    			{1,4,5, 3}, {1,4,6, 0}, {1,4,7, 2}, 
    			// Roof
    			{1,6,3, 3}, {2,6,3, 3}, {3,6,3, 3}, {4,6,3, 3}, {5,6,3, 3}, {6,6,3, 3}, {7,6,3, 3}, 
    			{7,6,2, 0},
    			{7,6,1, 3}, {8,6,1, 3}, {9,6,1, 3}, {10,6,1, 3}, {11,6,1, 3}, 
    			{11,6,2, 1}, {11,6,2, 1}, {11,6,3, 1}, {11,6,4, 1}, {11,6,5, 1}, {11,6,6, 1}, {11,6,7, 1}, {11,6,8, 1}, 
    			{11,6,9, 2}, {10,6,9, 2}, {9,6,9, 2}, {8,6,9, 2}, {7,6,9, 2}, {6,6,9, 2}, {5,6,9, 2}, {4,6,9, 2}, {3,6,9, 2}, {2,6,9, 2}, {1,6,9, 2}, 
    			{1,6,8, 0}, {1,6,7, 0}, {1,6,6, 0}, {1,6,5, 0}, {1,6,4, 0}, {1,6,3, 0},
    			
    			{2,7,4, 3}, {3,7,4, 3}, {4,7,4, 3}, {5,7,4, 3}, {6,7,4, 3}, {7,7,4, 3}, {8,7,4, 3}, 
    			{8,7,3, 0}, 
    			{8,7,2, 3}, {9,7,2, 3}, {10,7,2, 3}, 
    			{10,7,3, 1}, {10,7,4, 1}, {10,7,5, 1}, {10,7,6, 1}, {10,7,7, 1}, 
    			{10,7,8, 2}, {9,7,8, 2}, {8,7,8, 2}, {7,7,8, 2}, {6,7,8, 2}, {5,7,8, 2}, {4,7,8, 2}, {3,7,8, 2}, {2,7,8, 2}, 
    			{2,7,7, 0}, {2,7,6, 0}, {2,7,5, 0}, 
    			
    			{3,8,5, 3}, {4,8,5, 3}, {5,8,5, 3}, {6,8,5, 3}, {7,8,5, 3}, {8,8,5, 3}, {9,8,5, 3}, 
    			{9,8,6, 1}, 
    			{9,8,7, 2}, {8,8,7, 2}, {7,8,7, 2}, {6,8,7, 2}, {5,8,7, 2}, {4,8,7, 2}, {3,8,7, 2}, 
    			{3,8,6, 0}, 
    			
    			// Stairs
    			{6,1,7, 0}, {7,2,7, 0}, {8,3,7, 0}, 
    			{7,1,7, 1+4}, {8,2,7, 1+4}, {9,3,7, 1+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Ceiling
    			{3,3,7, 4,3,7}, {3,3,5, 9,3,6}, {9,3,3, 9,3,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Ceiling
    			{9,8,3, 9,8,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
        		// Front wall
        		{5,2,4}, {6,2,4}, 
        		{5,5,4}, {6,5,4}, 
        		// Back wall
        		{3,2,8}, {5,2,8}, {6,2,8}, {9,2,8}, 
        		{3,5,8}, {5,5,8}, {6,5,8}, {9,5,8}, 
        		// Right wall
        		{11,2,4}, {11,2,6}, {10,5,4}, {10,5,6}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{3,1,4, 0, 1, 0}, 
    			{2,1,6, 3, 1, 0}, 
    			{9,1,2, 0, 1, 1}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
        	
            
            // Cobblestone stairs
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
        	for (int[] uuvvwwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
        		// Entryways
        		{3,0,1, 3,0,1, 3}, {9,0,1, 9,0,1, 3}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], uuvvwwo[3], uuvvwwo[4], uuvvwwo[5], biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uuvvwwo[6]%4)+(uuvvwwo[6]/4)*4, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uuvvwwo[6]%4)+(uuvvwwo[6]/4)*4, false);	
            }
        	
        	
            // Table
            for (int[] uuvvwwo : new int[][]{
        		{10,1,7, 2}, 
        		})
            {
            	Object[][] tableComponentObjects = ModObjects.chooseModWoodenTable(biomePlankBlock==Blocks.planks ? biomePlankMeta : 0, uuvvwwo[3], this.coordBaseMode);
        		for (int i=1; i>=0; i--)
        		{
        			this.placeBlockAtCurrentPosition(world, (Block)tableComponentObjects[i][0], (Integer)tableComponentObjects[i][1], uuvvwwo[0], uuvvwwo[1]+1-i, uuvvwwo[2], structureBB);
        		}
            }
        	
        	
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 4;
        	int chestV = 4;
        	int chestW = 7;
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
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{3,4,6, 2, GeneralConfig.useVillageColors ? this.townColor : 0}, // White
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
	        			{4,4,5, -1, 0},
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
    
    public static class JungleSmallHouse2 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"          ",
    			" FFFFFFFF ",
    			" FFFFFFFF ",
    			" FFFFFFFF ",
    			" FFFFFFFF ",
    			" FPFFFFFF ",
    			" FPFFFFFF ",
    			" FPFFFFFF ",
    			" FPFFFFFF ",
    			" FPPPPPPP ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 9;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 3;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleSmallHouse2() {}

        public JungleSmallHouse2(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleSmallHouse2 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleSmallHouse2(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Dirt
    		for (int[] uuvvww : new int[][]{
        		{1,0,5, 3,0,8}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeFillerBlock, biomeFillerMeta,
        				biomeFillerBlock, biomeFillerMeta, 
        				false);
            }
    		
    		
            // White Terracotta
        	for (int[] uuvvww : new int[][]{
        		// Front wall
        		{5,2,2, 5,4,2}, {6,4,2, 6,4,2}, {7,2,2, 7,4,2},
        		// Back wall
        		{5,2,8, 5,4,8}, {6,2,8, 6,2,8}, {6,4,8, 6,4,8}, {7,2,8, 7,4,8}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor : 0),
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor : 0), 
        				false);
            }
    		
    		
            // Yellow Terracotta
        	for (int[] uuvvww : new int[][]{
        		// Floor
        		{4,1,2, 8,1,8}, 
        		// Front wall
        		{5,6,2, 5,6,2}, {6,7,2, 6,7,2}, {7,6,2, 7,6,2}, 
        		// Left wall
        		{4,2,2, 4,2,8}, 
        		{4,3,2, 4,3,2}, {4,3,4, 4,3,8}, 
        		{4,4,2, 4,4,8}, {4,5,3, 4,5,7}, 
        		// Right wall
        		{8,2,2, 8,2,8}, 
        		{8,3,2, 8,3,2}, {8,3,4, 8,3,6}, {8,3,8, 8,3,8}, 
        		{8,4,2, 8,4,8}, {8,5,3, 8,5,7}, 
        		// Back wall
        		{5,6,8, 5,6,8}, {6,7,8, 6,7,8}, {7,6,8, 7,6,8}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor2 : 4),
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor2 : 4), 
        				false);
            }
    		
    		
            // Orange Terracotta
        	for (int[] uvw : new int[][]{
        		// Desk
        		{7,2,3, 7,2,3}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor5 : 1), uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Logs (Across)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Shed
    			{1,3,5, 3,3,5}, 
    			{1,3,8, 3,3,8}, 
    			// Main house
    			{4,5,2, 8,5,2}, 
    			{4,5,8, 8,5,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, false);	
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// Shed interior
    			{2,3,6, 0}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		
    		// Logs (Along)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Longer log
    			{1,3,6, 1,3,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAlongBlock, biomeLogHorAlongMeta, biomeLogHorAlongBlock, biomeLogHorAlongMeta, false);	
    		}
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Front wall
    			{3,1,5, 3,2,5}, {3,4,5, 3,4,5}, 
    			// Side wall
    			{1,1,5, 1,2,8}, 
    			// Back wall
    			{2,1,8, 3,2,8}, {3,4,8, 3,4,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uvwo : new int[][]{
        		{5,5,5}, 
        		})
            {
    			this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);	
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	{5,4,5}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Front entrance
    			{5,1,1, 3}, {6,1,1, 3}, {7,1,1, 3}, 
    			// Roof
    			{3,5,1, 0}, {3,5,2, 0}, {3,5,3, 0}, {3,5,4, 0}, {3,5,5, 0}, {3,5,6, 0}, {3,5,7, 0}, {3,5,8, 0}, {3,5,9, 0}, 
    			{4,6,1, 0}, {4,6,2, 0}, {4,6,3, 0}, {4,6,4, 0}, {4,6,5, 0}, {4,6,6, 0}, {4,6,7, 0}, {4,6,8, 0}, {4,6,9, 0}, 
    			{5,7,1, 0}, {5,7,2, 0}, {5,7,3, 0}, {5,7,4, 0}, {5,7,5, 0}, {5,7,6, 0}, {5,7,7, 0}, {5,7,8, 0}, {5,7,9, 0}, 
    			{7,7,1, 1}, {7,7,2, 1}, {7,7,3, 1}, {7,7,4, 1}, {7,7,5, 1}, {7,7,6, 1}, {7,7,7, 1}, {7,7,8, 1}, {7,7,9, 1}, 
    			{8,6,1, 1}, {8,6,2, 1}, {8,6,3, 1}, {8,6,4, 1}, {8,6,5, 1}, {8,6,6, 1}, {8,6,7, 1}, {8,6,8, 1}, {8,6,9, 1}, 
    			{9,5,1, 1}, {9,5,2, 1}, {9,5,3, 1}, {9,5,4, 1}, {9,5,5, 1}, {9,5,6, 1}, {9,5,7, 1}, {9,5,8, 1}, {9,5,9, 1}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Shed roof
    			{0,3,5, 0,3,8}, 
    			{2,4,5, 2,4,8}, 
    			// Shelves
    			{3,1,6, 3,1,6}, {3,2,7, 3,2,7}, {3,3,6, 3,3,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Shed roof
    			{1,4,5, 1,4,8}, 
    			// House roof
    			{6,8,1, 6,8,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
        		// Front wall
        		{6,6,2}, 
        		// Left wall
        		{4,3,3}, 
        		// Back wall
        		{6,3,8}, {6,6,8}, 
        		// Left wall
        		{8,3,3}, {8,3,7}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
        	// Carpet
        	for(int[] uuvvww : new int[][]{
        		// Lower
        		{5,2,4, 7,2,5, (GeneralConfig.useVillageColors ? this.townColor : 0)}, // White
        		})
            {
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.carpet, uuvvww[6], Blocks.carpet, uuvvww[6], false);	
            }
        	
            
            // Potted Ferns
        	for (int[] uvwm : new int[][]{ // 11:fern
        		{4,1,1, 11}, {8,1,1, 11}, 
           		})
        	{
            	this.placeBlockAtCurrentPosition(world, Blocks.flower_pot, uvwm[3], uvwm[0], uvwm[1], uvwm[2], structureBB);
            }
        	
            
            // Potted random flower
            for (int[] uvw : new int[][]{
        		{7,3,3}, 
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
    			// Shed door
    			{2,1,5, 2, 1, 0}, 
    			// House door
    			{6,2,2, 2, 1, 0}, 
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
            int chestU = 3;
        	int chestV = 1;
        	int chestW = 7;
        	int chestO = 3; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo(ChestLootHandler.getGenericLootForVillageType(this.villageType));
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{6,2,6, 2, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
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
	        			{5,2,5, -1, 0},
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
    // designed by AstroTibs
    
    public static class JungleSmallHouse3 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"       ",
    			" FFFFF ",
    			" FFFFF ",
    			" FFFFF ",
    			" FFFFF ",
    			" FFFFF ",
    			" FPPPF ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 8;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleSmallHouse3() {}

        public JungleSmallHouse3(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleSmallHouse3 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleSmallHouse3(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			{4,6,4, 4,6,4}, 
    			{1,0,1, 5,0,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
        	
        	
        	// Cobblestone wall
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
            	// Chimney
        		{4,2,4, 4,5,4}, {4,7,4, 4,7,4}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, false);
            }
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uvwo : new int[][]{
        		// Windows
        		{1, 2, 3}, {5, 2, 3}, 
        		{3, 5, 1}, 
        		{3, 5, 5}, {3, 2, 5}, 
        		})
            {
    			this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);	
            }
            
        	
        	// Vertical logs
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, materialType, biome, disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{1,1,5, 1,4,5}, {5,1,5, 5,4,5}, 
        		{1,1,1, 1,4,1}, {5,1,1, 5,4,1}, 
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
    			// Back wall
    			{2,1,5, 2,3,5}, {3,1,5, 3,1,5}, {3,3,5, 3,3,5}, {4,1,5, 4,3,5}, 
    			{2,5,5, 2,5,5}, {4,5,5, 4,5,5}, 
    			// Left wall
    			{1,1,2, 1,3,2}, {1,1,3, 1,1,3}, {1,3,3, 1,3,3}, {1,1,4, 1,3,4}, 
    			// Right wall
    			{5,1,2, 5,3,2}, {5,1,3, 5,1,3}, {5,3,3, 5,3,3}, {5,1,4, 5,3,4}, 
    			// Front wall
    			{2,1,1, 2,3,1}, {3,1,1, 3,1,1}, {3,3,1, 3,3,1}, {4,1,1, 4,3,1}, 
    			// Ceiling
    			{3,5,1, 3,5,1}, {3,5,5, 3,5,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
        		{2,3,3, 1}, {4,3,3, 3}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Roof
    			{1,5,0, 0}, {1,5,1, 0}, {1,5,2, 0}, {1,5,3, 0}, {1,5,4, 0}, {1,5,5, 0}, {1,5,6, 0}, 
    			{2,6,0, 0}, {2,6,1, 0}, {2,6,2, 0}, {2,6,3, 0}, {2,6,4, 0}, {2,6,5, 0}, {2,6,6, 0}, 
    			{4,6,0, 1}, {4,6,1, 1}, {4,6,2, 1}, {4,6,3, 1}, {4,6,5, 1}, {4,6,6, 1}, 
    			{5,5,0, 1}, {5,5,1, 1}, {5,5,2, 1}, {5,5,3, 1}, {5,5,4, 1}, {5,5,5, 1}, {5,5,6, 1}, 
    			// Roof trim
    			{2,5,0, 1+4}, {3,6,0, 3+4}, {4,5,0, 0+4}, 
    			{2,5,6, 1+4}, {3,6,6, 2+4}, {4,5,6, 0+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{3,7,0, 3,7,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{0,4,0, 0,4,6}, {6,4,0, 6,4,6}, 
    			// Wall trim
    			{1,4,2, 1,4,4}, {2,4,1, 4,4,1}, {2,4,5, 4,4,5}, {5,4,2, 5,4,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
        	
            
            // Potted random flower
        	Object[] cornflowerObject = ModObjects.chooseModCornflower(); Object[] lilyOfTheValleyObject = ModObjects.chooseModLilyOfTheValley();
    		int randomPottedPlant = random.nextInt(10)-1;
            for (int[] uvw : new int[][]{
        		{1,1,0}, {5,1,0}, 
        		})
            {
        		int u=uvw[0]; int v=uvw[1]; int w=uvw[2];
                int x = this.getXWithOffset(u, w);
                int y = this.getYWithOffset(v);
                int z = this.getZWithOffset(u, w);
            	
        		if (randomPottedPlant==-1) {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.yellow_flower, 0);} // Dandelion specifically
        		else {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.red_flower, randomPottedPlant);}          // Every other type of flower
            }
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{3,1,1, 2, 1, 1}, 
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
            	{4,1,4, 2}, 
            	})
            {
                this.placeBlockAtCurrentPosition(world, Blocks.furnace, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2]), StructureVillageVN.chooseFurnaceMeta(uvwo[3], this.coordBaseMode), 2);
            }
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{2,1,3, 2, GeneralConfig.useVillageColors ? this.townColor4 : 14}, // Red
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
	        			{2,1,2, -1, 0},
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
    // designed by AstroTibs
    
    public static class JungleSmallHouse4 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"        ",
    			" FFFFFF ",
    			" FFFFFFF",
    			" FFFFFFF",
    			" FFFFFFF",
    			" FFFFFF ",
    			"F PPP F ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 8;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 1;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleSmallHouse4() {}

        public JungleSmallHouse4(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleSmallHouse4 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleSmallHouse4(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			{1,0,1, 5,0,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
        	
        	
        	// Cobblestone wall
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
            	{0,1,0, 0,1,0}, {6,1,0, 6,1,0}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, false);
            }
    		
    		
            // Cyan Terracotta
        	for (int[] uuvvww : new int[][]{
        		// Front wall
        		{1,1,1, 1,3,2}, {2,1,1, 2,1,1}, {2,3,1, 2,3,1}, {3,1,1, 3,3,1}, {4,3,1, 4,3,1}, 
        		// Right wall
        		{5,1,1, 5,3,5}, 
        		// Left wall
        		{1,1,3, 1,1,3}, {1,3,3, 1,3,3}, {1,1,4, 1,3,4}, 
        		// Back wall
        		{1,1,5, 2,3,5}, {3,1,5, 3,2,5}, {4,1,5, 5,3,5}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor4 : 9),
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor4 : 9), 
        				false);
            }
    		
    		
    		// Hay bales (vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.hay_block, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeHayBaleVertBlock = (Block)blockObject[0]; int biomeHayBaleVertMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{1,5,1, 5,5,1}, {1,5,2, 1,5,4}, {5,5,2, 5,5,4}, {1,5,5, 5,5,5}, 
    			{2,6,2, 4,6,2}, {2,6,3, 2,6,3}, {4,6,3, 4,6,3}, {2,6,4, 4,6,4}, 
    			{0,4,0, 6,4,0}, {0,4,1, 0,4,5}, {6,4,1, 6,4,5}, {0,4,6, 6,4,6}, 
    			{3,7,3, 3,7,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeHayBaleVertBlock, biomeHayBaleVertMeta, biomeHayBaleVertBlock, biomeHayBaleVertMeta, false);	
    		}
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uvwo : new int[][]{
        		// Windows
        		{2, 2, 1}, 
        		{1, 2, 3}, 
        		{3, 3, 5}, 
        		// Ceiling support
        		{2, 5, 4}, {3, 5, 4}, {4, 5, 4}, 
        		{2, 5, 3}, {3, 6, 3}, {4, 5, 3}, 
        		{2, 5, 2}, {3, 5, 2}, {4, 5, 2}, 
        		})
            {
    			this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);	
            }
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
        		{0,2,0, -1}, {6,2,0, -1}, 
        		{4,3,3, 3}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Upper wall
    			{1,4,1, 2}, {2,4,1, 2}, {3,4,1, 2}, {4,4,1, 2}, {5,4,1, 2}, 
    			{1,4,2, 1}, {1,4,3, 1}, {1,4,4, 1}, 
    			{5,4,2, 0}, {5,4,3, 0}, {5,4,4, 0}, 
    			{1,4,5, 3}, {2,4,5, 3}, {3,4,5, 3}, {4,4,5, 3}, {5,4,5, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
            
            
        	// Carpet
        	for(int[] uuvvww : new int[][]{
        		// Carpet in front of the door prevents villagers from passing through
        		{3,1,3, 3,1,3, (GeneralConfig.useVillageColors ? this.townColor : 0)}, // White 
        		})
            {
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.carpet, uuvvww[6], Blocks.carpet, uuvvww[6], false);	
            }
        	
            
            // Potted random flower
            for (int[] uvw : new int[][]{
        		{3,1,4}, 
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
    			{4,1,1, 2, 1, 1}, 
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
            int chestU = 4;
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
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{2,1,3, 0, GeneralConfig.useVillageColors ? this.townColor4 : 9}, // Cyan
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
    		
            
            // Moist Farmland with crop above
            for(int[] uvwmcp : new int[][]{
            	// u,v,w, farmland moisture (0:dry, 7:moist), crop progress
            	{6,0,1, 7,0}, 
            	{7,0,2, 7,0}, {7,0,3, 7,0}, {7,0,4, 7,0}, 
            	{6,0,5, 7,0}, 
            	})
            {
            	this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, uvwmcp[0], uvwmcp[1]-1, uvwmcp[2], structureBB);
            	//this.clearCurrentPositionBlocksUpwards(world, uvwpmc[0], uvwpmc[1]+1, uvwpmc[2], structureBB);
            	this.placeBlockAtCurrentPosition(world, Blocks.wheat, uvwmcp[4], uvwmcp[0], uvwmcp[1]+1, uvwmcp[2], structureBB); 
            	this.placeBlockAtCurrentPosition(world, Blocks.farmland, uvwmcp[3], uvwmcp[0], uvwmcp[1], uvwmcp[2], structureBB); // 7 is moist
            }
            
            
    		// Water
    		for(int[] uuvvww : new int[][]{ 
    			{6,0,2, 6,0,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);	
    		}
        	
        	
    		// Villagers
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	if (VillageGeneratorConfigHandler.spawnVillagersInResidences)
            	{
	            	int[][] villagerPositions = new int[][]{
	        			{3,1,3, -1, 0},
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
    // designed by AstroTibs
    
    public static class JungleSmallHouse5 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"  F F  ",
    			" FFFFF ",
    			"FFFFFFF",
    			" FFFFF ",
    			"FFFFFFF",
    			" FFFFF ",
    			" FFPFF ",
    			" FFPFF ",
    			"  PPP  ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 6;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 1;
    	private static final int DECREASE_MAX_U = 1;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleSmallHouse5() {}

        public JungleSmallHouse5(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleSmallHouse5 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleSmallHouse5(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
            // Stone Brick
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickBlock = (Block)blockObject[0]; int biomeStoneBrickMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Entryway
    			{3,0,3, 3,0,3}, 
    			{2,0,3, 2,2,3}, {4,0,3, 4,2,3}, 
    			{2,0,2, 2,1,2}, {4,0,2, 4,1,2}, 
    			{2,0,1, 2,2,1}, {4,0,1, 4,2,1}, 
    			{2,3,1, 2,3,3}, {3,3,1, 3,3,1}, {3,3,3, 3,3,3}, {4,3,1, 4,3,3}, 
    			// Left wall
    			{1,0,6, 1,3,6}, {5,0,6, 5,3,6}, 
    			{1,0,5, 1,1,5}, {5,0,5, 5,1,5}, {1,3,5, 1,3,5}, {5,3,5, 5,3,5}, 
    			{1,0,4, 1,3,4}, {5,0,4, 5,3,4}, 
    			// Back wall
    			{1,0,7, 5,3,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickBlock, biomeStoneBrickMeta, biomeStoneBrickBlock, biomeStoneBrickMeta, false);	
    		}
    		
    		
    		// Stone Brick Slab (upper)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 13, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickSlabUpperBlock = (Block)blockObject[0]; int biomeStoneBrickSlabUpperMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{3,3,2, 3,3,2}
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickSlabUpperBlock, biomeStoneBrickSlabUpperMeta, biomeStoneBrickSlabUpperBlock, biomeStoneBrickSlabUpperMeta, false);	
    		}
    		
    		
    		// Stone Brick stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_brick_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Roof
    			{2,4,3, 3}, {3,4,3, 3}, {4,4,3, 3}, 
    			{1,4,4, 0}, {1,4,5, 0}, {1,4,6, 0}, 
    			{2,4,7, 2}, {3,4,7, 2}, {4,4,7, 2}, 
    			{5,4,4, 1}, {5,4,5, 1}, {5,4,6, 1}, 
    			// Top of roof
    			{2,5,6, 2}, {3,5,6, 2}, {4,5,6, 2}, 
    			{2,5,5, 0}, {4,5,5, 1}, 
    			{2,5,4, 3}, {3,5,4, 3}, {4,5,4, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeStoneBrickStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			{2,0,4, 4,0,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
        	
        	// Stone Brick wall
    		Object[] tryStoneBrickWall = ModObjects.chooseModStoneBrickWallBlock();
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(tryStoneBrickWall!=null?(Block)tryStoneBrickWall[0]:Blocks.cobblestone_wall, tryStoneBrickWall!=null?(Integer)tryStoneBrickWall[1]:0, this.materialType, this.biome, this.disallowModSubs);
        	Block biomeStoneBrickWallBlock = (Block)blockObject[0]; int biomeStoneBrickWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{3,4,6, 3,4,6}, //{3,5,5, 3,5,5}, 
            	{2,1,8, 2,3,8}, {4,1,8, 4,3,8}, 
            	{0,1,6, 0,3,6}, {6,1,6, 6,3,6}, 
            	{0,1,4, 0,3,4}, {6,1,4, 6,3,4}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickWallBlock, biomeStoneBrickWallMeta, biomeStoneBrickWallBlock, biomeStoneBrickWallMeta, false);
            }
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
        		{1,2,5}, {5,2,5}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
            
            // Iron bars
            for(int[] uuvvww : new int[][]{
            	{3,5,5, 3,5,5}
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.iron_bars, 0, Blocks.iron_bars, 0, false);
            }
            
            
        	// Carpet
        	for(int[] uuvvww : new int[][]{
        		// Lower
        		{3,1,5, 3,1,5, (GeneralConfig.useVillageColors ? this.townColor4 : 9)}, // Cyan
        		})
            {
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.carpet, uuvvww[6], Blocks.carpet, uuvvww[6], false);	
            }
        	
        	
        	// Furnaces
            for (int[] uvwo : new int[][]{ // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
            	{2,1,6, 1}, 
            	})
            {
                this.placeBlockAtCurrentPosition(world, Blocks.furnace, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2]), StructureVillageVN.chooseFurnaceMeta(uvwo[3], this.coordBaseMode), 2);
            }
        	
            
            // Potted random flower
            for (int[] uvw : new int[][]{
        		{2,2,6}, 
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
    			{3,1,3, 0, 1, 0}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	{3,3,6}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{4,1,5, 2, GeneralConfig.useVillageColors ? this.townColor3 : 14}, // Red
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
	        			{3,1,5, -1, 0},
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
    
    
    // --- Small House 6 --- //
    // designed by jss2a98aj
    
    public static class JungleSmallHouse6 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			" FFFF ",
    			"FFFFFF",
    			"FFFFFF",
    			"FFFFFF",
    			"FFFFFF",
    			" FFFFF",
    			" PPP  ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 6;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 1;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleSmallHouse6() {}

        public JungleSmallHouse6(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleSmallHouse6 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleSmallHouse6(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
        		{1,0,6, 4,0,6}, 
        		{0,0,3, 5,0,5}, 
        		{0,0,2, 4,0,2}, 
        		{1,0,1, 3,0,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
            // Stone Brick
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickBlock = (Block)blockObject[0]; int biomeStoneBrickMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Front wall
    			{1,1,1, 1,2,1}, {2,3,1, 2,3,1}, {3,1,1, 3,2,1}, 
    			// Left wall
    			{0,1,2, 0,2,2}, {0,1,3, 0,1,4}, {0,3,3, 0,3,4}, {0,1,5, 0,2,5}, 
    			// Back wall
    			{1,1,6, 1,2,6}, {2,1,6, 3,1,6}, {2,3,6, 3,3,6}, {4,1,6, 4,2,6}, 
    			// Right wall
    			{5,1,3, 5,2,5}, {5,3,4, 5,3,4}, {4,1,2, 4,1,2}, {4,3,2, 4,3,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickBlock, biomeStoneBrickMeta, biomeStoneBrickBlock, biomeStoneBrickMeta, false);	
    		}
    		
    		
    		// Chiseled Stone Brick
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeChiseledStoneBrickBlock = (Block)blockObject[0]; int biomeChiseledStoneBrickMeta = (Integer)blockObject[1];
    		for (int[] uvw : new int[][]{
    			// Front wall
    			{1,3,1}, {3,3,1}, 
    			// Left wall
    			{0,3,2}, {0,3,5}, 
    			// Back wall
    			{1,3,6}, {4,3,6}, 
    			// Right wall
    			{5,3,5}, {5,3,3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeChiseledStoneBrickBlock, biomeChiseledStoneBrickMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
            
            
            // Glass blocks
        	for (int[] uvw : new int[][]{
        		{0,2,3}, {0,2,4}, 
        		{2,2,6}, {3,2,6}, 
        		{4,2,2}, 
        		})
            {
    			this.placeBlockAtCurrentPosition(world, Blocks.glass, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Furnaces
            for (int[] uvwo : new int[][]{ // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
            	{4,1,4, 3}, 
            	})
            {
                this.placeBlockAtCurrentPosition(world, Blocks.furnace, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2]), StructureVillageVN.chooseFurnaceMeta(uvwo[3], this.coordBaseMode), 2);
            }
    		
    		
    		// Brick Slab (upper)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 12, this.materialType, this.biome, this.disallowModSubs); Block biomeBrickSlabUpperBlock = (Block)blockObject[0]; int biomeBrickSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uvwo : new int[][]{
    			{4,1,3}, {4,1,5}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeBrickSlabUpperBlock, biomeBrickSlabLowerMeta, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Web
    		for(int[] uvwo : new int[][]{
    			{4,4,4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, Blocks.web, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
        	
            
            // Potted flower
            for (int[] uvwf : new int[][]{
        		{4,2,5, 8}, // 8 is a brown mushroom
        		})
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.flower_pot, uvwf[3], uvwf[0], uvwf[1], uvwf[2], structureBB);
            }
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Roof
    			{2,4,1, 3}, 
    			{0,4,3, 0}, {0,4,4, 0}, 
    			{2,4,6, 2}, {3,4,6, 2}, 
    			{5,4,4, 1}, 
    			// Shelves
    			{4,3,3, 2+4}, {4,3,5, 3+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{1,4,1, 1,4,1}, {3,4,1, 3,4,1}, 
    			{0,4,2, 0,4,2}, {4,4,2, 4,4,2}, 
    			{5,4,3, 5,4,3}, 
    			{0,4,5, 0,4,5}, {5,4,5, 5,4,5}, 
    			{1,4,6, 1,4,6}, {4,4,6, 4,4,6}, 
    			{2,5,2, 2,5,2}, 
    			{1,5,3, 3,5,3}, 
    			{1,5,4, 4,5,4}, 
    			{2,5,5, 3,5,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{1,4,2, 1,4,2}, {3,4,2, 3,4,2}, 
    			{4,4,3, 4,4,3}, 
    			{1,4,5, 1,4,5}, {4,4,5, 4,4,5}, 
    			// Shelf
    			{4,3,4, 4,3,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uvw : new int[][]{
        		{2,4,4}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// On fence posts
    			{1,2,0, 2}, {3,2,0, 2}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	{2,3,4}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{1,1,4, 2, GeneralConfig.useVillageColors ? this.townColor3 : 14}, // Red
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
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{2,1,1, 2, 1, 1}, 
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
        		{4,1,1, 5,1,1}, {5,1,2, 5,1,2}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeLeafBlock, biomeLeafMeta,
        				biomeLeafBlock, biomeLeafMeta, 
        				false);
            }
    		
    		
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward, 3:leftward
            		// Leftward
        			{-1,1,2, 3}, {-1,1,5, 3}, {-1,2,5, 3}, {-1,3,5, 3}, 
        			// Forward
        			{0,1,6, 0}, {0,2,6, 0}, {3,1,7, 0}, {4,1,7, 0}, {4,2,7, 0}, 
        			// Rightward
        			{6,2,3, 1}, 
        			{6,1,3, 1}, {6,1,4, 1}, 
        			{6,1,5, 1}, {6,2,5, 1}, {6,3,5, 1}, 
        			// Backward
        			{0,1,1, 2}, {0,2,1, 2}, {0,3,1, 2}, 
        			{3,1,0, 2}, {5,2,2, 2}, 
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
	        			{2,1,4, -1, 0},
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
    
    
    // --- Small House 7 --- //
    // designed by AstroTibs
    
    public static class JungleSmallHouse7 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"  FFFF  ",
    			" FFFFFF ",
    			"FFFFFFFF",
    			"FFFFFFFF",
    			"FFFFFFFF",
    			"FFFFFFFF",
    			" FFFFFF ",
    			"  FPPF  ",
    			"   PP   ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 7;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 1;
    	private static final int DECREASE_MAX_U = 1;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleSmallHouse7() {}

        public JungleSmallHouse7(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleSmallHouse7 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleSmallHouse7(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
            // Stone Brick
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickBlock = (Block)blockObject[0]; int biomeStoneBrickMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Front
    			{2,1,1, 2,1,1}, {2,1,2, 2,2,2}, 
    			{5,1,1, 5,1,1}, {5,1,2, 5,2,2}, 
    			// Back
    			{2,1,8, 2,1,8}, {2,1,7, 2,2,7}, 
    			{3,1,7, 4,1,7}, 
    			{5,1,8, 5,1,8}, {5,1,7, 5,2,7}, 
    			// Left
    			{0,1,3, 0,1,3}, {1,1,3, 1,2,3},
    			{1,1,4, 1,1,5}, 
    			{0,1,6, 0,1,6}, {1,1,6, 1,2,6}, 
    			// Right
    			{7,1,3, 7,1,3}, {6,1,3, 6,2,3}, 
    			{6,1,4, 6,1,5}, 
    			{7,1,6, 7,1,6}, {6,1,6, 6,2,6}, 
    			
    			// Floor
    			{2,1,6, 5,1,6}, 
    			{2,1,4, 2,1,5}, {5,1,4, 5,1,5}, 
    			{2,1,3, 5,1,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickBlock, biomeStoneBrickMeta, biomeStoneBrickBlock, biomeStoneBrickMeta, false);	
    		}
        	
        	
            // Glazed terracotta
        	Object[] tryGlazedTerracotta;
        	for (int[] uvwoc : new int[][]{ // u,v,w, orientation, dye color
        		// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
        		
        		{3,1,5, 1, GeneralConfig.useVillageColors ? this.townColor3 : 14}, // Red
        		{4,1,5, 2, GeneralConfig.useVillageColors ? this.townColor3 : 14}, // Red
        		{3,1,4, 0, GeneralConfig.useVillageColors ? this.townColor3 : 14}, // Red
        		{4,1,4, 3, GeneralConfig.useVillageColors ? this.townColor3 : 14}, // Red
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
    		
        	
        	// Terracotta part 1
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.hardened_clay, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTerracottaBlock = (Block)blockObject[0]; int biomeTerracottaMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{2,2,6, 2,4,6}, {5,2,6, 5,4,6}, 
        		{2,2,3, 2,4,3}, {5,2,3, 5,4,3}, 
        		{3,4,2, 4,4,2}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeTerracottaBlock, biomeTerracottaMeta, biomeTerracottaBlock, biomeTerracottaMeta, false);
            }
    		
        	
        	// Stone Brick wall
    		Object[] tryStoneBrickWall = ModObjects.chooseModStoneBrickWallBlock();
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(tryStoneBrickWall!=null?(Block)tryStoneBrickWall[0]:Blocks.cobblestone_wall, tryStoneBrickWall!=null?(Integer)tryStoneBrickWall[1]:0, this.materialType, this.biome, this.disallowModSubs);
        	Block biomeStoneBrickWallBlock = (Block)blockObject[0]; int biomeStoneBrickWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{1,1,7, 1,2,7}, {2,3,7, 2,4,7}, {5,3,7, 5,4,7}, {6,1,7, 6,2,7}, 
        		{1,3,6, 1,4,6}, {6,3,6, 6,4,6}, 
        		{1,3,3, 1,4,3}, {6,3,3, 6,4,3}, 
        		{1,1,2, 1,2,2}, {2,3,2, 2,4,2}, {5,3,2, 5,4,2}, {6,1,2, 6,2,2}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickWallBlock, biomeStoneBrickWallMeta, biomeStoneBrickWallBlock, biomeStoneBrickWallMeta, false);
            }
    		
    		
    		// Stone Brick stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_brick_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Back
    			{3,1,8, 2}, {4,1,8, 2}, 
    			// Left
    			{0,1,4, 0}, {0,1,5, 0}, 
    			// Right
    			{7,1,4, 1}, {7,1,5, 1}, 
    			// Front
    			{3,1,2, 3}, {4,1,2, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeStoneBrickStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// Exterior
    			{1,3,7, -1}, {6,3,7, -1}, 
    			{1,3,2, -1}, {6,3,2, -1}, 
    			// Interior
    			{2,4,5, 2}, {5,4,4, 0}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
        	
        	// Terracotta part 2
        	for (int[] uuvvww : new int[][]{
        		{3,2,7, 4,4,7}, 
        		{1,2,4, 1,4,5}, {6,2,4, 6,4,5}, 
        		{3,4,2, 4,4,2}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeTerracottaBlock, biomeTerracottaMeta, biomeTerracottaBlock, biomeTerracottaMeta, false);
            }
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Roof
    			{2,5,7, 2}, {5,5,7, 2}, 
    			{1,5,6, 0}, {6,5,6, 1}, 
    			{1,5,3, 0}, {6,5,3, 1}, 
    			{2,5,2, 3}, {5,5,2, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{3,5,7, 4,5,7}, 
    			{1,5,4, 1,5,5}, {6,5,4, 6,5,5}, 
    			{3,5,2, 4,5,2}, 
    			
    			{2,6,6, 5,6,6}, 
    			{2,6,4, 2,6,5}, {5,6,4, 5,6,5}, 
    			{2,6,3, 5,6,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uvw : new int[][]{
        		{2,5,6}, {5,5,6}, 
        		{2,5,3}, {5,5,3}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Table
    			{5,2,5, 5,2,5}, 
    			// Roof
    			{3,6,4, 4,6,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
        	
            
            // Potted random flower
            int flower_u=5; int flower_v=3; int flower_w=5;
            int x = this.getXWithOffset(flower_u, flower_w);
            int y = this.getYWithOffset(flower_v);
            int z = this.getZWithOffset(flower_u, flower_w);
            
            Object[] cornflowerObject = ModObjects.chooseModCornflower(); Object[] lilyOfTheValleyObject = ModObjects.chooseModLilyOfTheValley();
    		int randomPottedPlant = random.nextInt(10)-1;
    		if (randomPottedPlant==-1) {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.yellow_flower, 0);} // Dandelion specifically
    		else {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.red_flower, randomPottedPlant);}          // Every other type of flower
        	
        	
        	// Crafting Table
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.crafting_table, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCraftingTableBlock = (Block)blockObject[0]; int biomeCraftingTableMeta = (Integer)blockObject[1];
            for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
        		{4,2,6}, 
           		})
        	{
            	this.placeBlockAtCurrentPosition(world, biomeCraftingTableBlock, biomeCraftingTableMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{2,2,4, 2, GeneralConfig.useVillageColors ? this.townColor : 0}, // White
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
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{3,2,3, 2, 1, 1}, {4,2,3, 2, 1, 0}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
    		
    		
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward, 3:leftward
            		// Leftward
        			{0,2,3, 3}, 
        			{0,4,4, 3}, {0,3,4, 3}, {0,2,4, 3}, 
        			// Forward
        			// Rightward
        			{7,4,5, 1}, {7,3,5, 1}, 
        			// Backward
        			{3,4,8, 0}, {4,4,8, 0}, {4,3,8, 0}, 
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
	        			{3,2,4, -1, 0},
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
    
    
    // --- Small House 8 --- //
    // designed by AstroTibs
    
    public static class JungleSmallHouse8 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"       ",
    			"       ",
    			"       ",
    			"       ",
    			"       ",
    			"       ",
    			"  PF   ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 10;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 1;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleSmallHouse8() {}

        public JungleSmallHouse8(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleSmallHouse8 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleSmallHouse8(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uvw : new int[][]{
        		{1,1,5}, {1,2,5}, {5,1,5}, {5,2,5}, 
        		{1,1,1}, {1,2,1}, {5,1,1}, {5,2,1}, 
        		})
            {
            	this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		// Foot foundation
    		for(int[] uvw : new int[][]{
    			// Feet
        		{1,0,5}, {5,0,5}, 
        		{1,0,1}, {5,0,1}, 
    			})
    		{
    			this.func_151554_b(world, biomeCobblestoneBlock, biomeCobblestoneMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Stripped Logs (Vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
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
    		for (int[] uuvvww : new int[][]{
    			{1,3,5, 1,8,5}, {5,3,5, 5,8,5}, 
    			{1,3,1, 1,8,1}, {5,3,1, 5,8,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Entry
    			{6,3,0, 6,3,2}, {5,3,2, 5,3,2}, 
    			// Walls
    			{2,3,5, 4,4,5}, {3,5,5, 3,5,5}, {2,6,5, 4,6,5}, 
    			{1,3,2, 1,4,4}, {1,5,3, 1,5,3}, {1,6,2, 1,6,4}, 
    			{5,3,3, 5,4,4}, {5,5,3, 5,5,3}, {5,6,2, 5,6,4}, 
    			{2,3,1, 4,4,1}, {3,5,1, 3,5,1}, {2,6,1, 4,6,1}, 
    			// Floor
    			{2,3,2, 4,3,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// Exterior
    			{3,6,6, 0}, 
    			{0,6,3, 3}, {6,6,3, 1}, 
    			{3,6,0, 2}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Entry
    			{3,1,0, 0}, {4,2,0, 0}, {5,3,0, 0}, 
    			{4,1,0, 1+4}, {5,2,0, 1+4}, 
    			// Top of walls
    			{1,7,2, 2+4}, {1,7,4, 3+4}, 
    			{2,7,5, 1+4}, {4,7,5, 0+4}, 
    			{5,7,2, 2+4}, {5,7,4, 3+4}, 
    			{2,7,1, 1+4}, {4,7,1, 0+4}, 
    			// Lower roof
    			{1,8,2, 0}, {1,8,4, 0}, 
    			{2,8,5, 2}, {4,8,5, 2}, 
    			{5,8,2, 1}, {5,8,4, 1}, 
    			{2,8,1, 3}, {4,8,1, 3}, 
    			// Top-center roof
    			{2,9,4, 2}, {3,9,4, 2}, {4,9,4, 2}, 
    			{2,9,3, 0}, {4,9,3, 1}, 
    			{2,9,2, 3}, {3,9,2, 3}, {4,9,2, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{0,8,6, 6,8,6}, 
    			{0,8,1, 0,8,5}, {6,8,1, 6,8,5}, 
    			{0,8,0, 6,8,0}, 
    			{3,8,1, 3,8,1}, {3,8,5, 3,8,5}, {1,8,3, 1,8,3}, {5,8,3, 5,8,3}, 
    			
    			{1,9,5, 5,9,5}, 
    			{1,9,2, 1,9,4}, {5,9,2, 5,9,4}, 
    			{1,9,1, 5,9,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{3,9,3, 3,9,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
        		{2,5,5}, {4,5,5}, 
        		{1,5,2}, {1,5,4}, 
        		{5,5,4}, 
        		{2,5,1}, {4,5,1}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
            // Terracotta
        	for (int[] uuvvww : new int[][]{
        		// Desk
        		{2,4,2, 2,4,2}, 
        		})
            {
        		// White
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor : 0),
        				Blocks.stained_hardened_clay, (GeneralConfig.useVillageColors ? this.townColor : 0), 
        				false);
            }
        	
            
            // Potted random flower
            int flower_u=2; int flower_v=5; int flower_w=2;
            int x = this.getXWithOffset(flower_u, flower_w);
            int y = this.getYWithOffset(flower_v);
            int z = this.getZWithOffset(flower_u, flower_w);
            
            Object[] cornflowerObject = ModObjects.chooseModCornflower(); Object[] lilyOfTheValleyObject = ModObjects.chooseModLilyOfTheValley();
    		int randomPottedPlant = random.nextInt(10)-1;
    		if (randomPottedPlant==-1) {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.yellow_flower, 0);} // Dandelion specifically
    		else {StructureVillageVN.generateStructureFlowerPot(world, structureBB, random, x, y, z, Blocks.red_flower, randomPottedPlant);}          // Every other type of flower
        	
            
            // Beds
            for (int[] uvwoc : new int[][]{ // u, v, w, orientation, color meta
            	// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{3,4,4, 3, GeneralConfig.useVillageColors ? this.townColor4 : 9}, // Cyan
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
            int chestU = 2;
        	int chestV = 4;
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
            
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{5,4,2, 1, 1, 0}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
    		
    		
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward, 3:leftward
            		// Leftward
        			{0,7,5, 3}, {0,6,5, 3}, 
        			// Forward
        			{1,7,1, 0}, {1,6,1, 0}, {1,5,1, 0}, {1,4,1, 0}, {1,3,1, 0}, {1,2,1, 0}, {1,1,1, 0}, 
        			{5,7,1, 0}, {5,6,1, 0}, 
        			// Rightward
        			{6,7,1, 1}, {6,6,1, 1}, {6,7,5, 1}, 
        			// Backward
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
	        			{3,4,3, -1, 0},
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
    // designed by AstroTibs
    
    public static class JungleStable extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"FFFFF ",
    			"FFFFF ",
    			"FFFFF ",
    			"FFFFF ",
    			"FFFFF ",
    			"FFFFF ",
    			"FFFFFF",
    			"FFFFF ",
    			"  P   ",
    			"F P F ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 6;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleStable() {}

        public JungleStable(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleStable buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleStable(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			{2,1,9, 2,2,9}, 
    			{0,1,9, 0,2,9}, {4,1,9, 4,2,9}, 
    			{0,1,7, 0,2,7}, {4,1,7, 4,2,7}, 
    			{0,1,5, 0,2,5}, {4,1,5, 4,2,5}, 
    			{0,1,3, 0,2,3}, {4,1,3, 4,2,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			{2,2,8, 2}, 
    			{0,3,3, -1}, {4,3,3, -1}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
            
        	
        	// Vertical logs
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, materialType, biome, disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{1,1,9, 1,1,9}, {3,1,9, 3,1,9}, 
        		{0,1,8, 0,1,8}, {4,1,8, 4,1,8}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeLogVertBlock, biomeLogVertMeta,
        				biomeLogVertBlock, biomeLogVertMeta, 
        				false);
            }
    		
    		
    		// Logs (Across)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 0:4), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Ceiling support
    			{1,1,7, 3,1,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta, false);	
    		}
			
            
			// Fence
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
			for (int[] uuvvww : new int[][]{
				{1,2,9, 1,2,9}, {3,2,9, 3,2,9}, 
				{0,1,0, 0,4,0}, {0,1,2, 0,3,2}, {0,1,4, 0,3,4}, {0,1,6, 0,2,6}, {0,2,8, 0,2,8}, 
				{4,1,0, 4,4,0}, {4,1,2, 4,3,2}, {4,1,4, 4,3,4}, {4,1,6, 4,2,6}, {4,2,8, 4,2,8}, 
				{1,1,2, 1,1,2}, {3,1,2, 3,1,2}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
			}
            
            
        	// Fence Gate (Across)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence_gate, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceGateBlock = (Block)blockObject[0]; int biomeFenceGateMeta = (Integer)blockObject[1];
        	for(int[] uvw : new int[][]{
            	{2,1,2}, 
            	})
            {
        		this.placeBlockAtCurrentPosition(world, biomeFenceGateBlock, StructureVillageVN.getMetadataWithOffset(biomeFenceGateBlock, biomeFenceGateMeta, this.coordBaseMode), uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	{0,4,2, 0,4,2}, {4,4,2, 4,4,2}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
            
            
            // Wooden slabs (Bottom)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	// Roof
            	{0,3,8, 4,3,9},
            	{0,4,4, 4,4,5},
            	{0,5,0, 4,5,1},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
            }
            
            
            // Wooden slabs (Top)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	// Roof
            	{0,3,6, 4,3,7},
            	{0,4,3, 4,4,3}, {1,4,2, 3,4,2},
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
            }
    		
        	
        	// Grass blocks with grass patches atop
        	for (int[] uuvvww : new int[][]{
				{1,0,3, 3,0,6}, 
        	})
        	{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeTopBlock, biomeTopMeta, biomeTopBlock, biomeTopMeta, false);	
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1]+1, uuvvww[2], uuvvww[3], uuvvww[4]+1, uuvvww[5], Blocks.tallgrass, 1, Blocks.tallgrass, 1, false);	
        	}
    		
        	
        	// Grass blocks under the fence
        	for (int[] uuvvww : new int[][]{
				{0,0,3, 0,0,9}, {4,0,3, 4,0,9}, 
				{0,0,2, 4,0,2}, {1,0,7, 3,0,9}, 
        	})
        	{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeTopBlock, biomeTopMeta, biomeTopBlock, biomeTopMeta, false);	
        	}
			
			
			// Water
			for(int[] uuvvww : new int[][]{
				{1,1,8, 3,1,8}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);	
			}
    		
    		
    		// Hay bales (vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.hay_block, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeHayBaleVertBlock = (Block)blockObject[0]; int biomeHayBaleVertMeta = (Integer)blockObject[1];
    		for(int[] uvw : new int[][]{
    			{5,1,3}, 
    			})
    		{
        		this.placeBlockAtCurrentPosition(world, biomeHayBaleVertBlock, StructureVillageVN.getMetadataWithOffset(biomeHayBaleVertBlock, biomeHayBaleVertMeta, this.coordBaseMode), uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward, 3:leftward
            		// Leftward
        			{-1,2,9, 3}, 
        			// Forward
        			{2,2,10, 0}, {0,2,10, 0}, {0,1,10, 0}, 
        			// Rightward
        			// Backward
            		})
                {
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
        			}
                }
        	}
            
            
            // Animals
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	if (VillageGeneratorConfigHandler.villageAnimalRestrictionLevel<1)
            	{
	            	// Animals
	            	for (int[] uvw : new int[][]{
	        			{2,1,4}, 
	        			})
	        		{
	                	EntityLiving animal = StructureVillageVN.getVillageAnimal(world, random, true, this.materialType==MaterialType.MUSHROOM);
	                	if (VillageGeneratorConfigHandler.nameVillageHorses && GeneralConfig.nameEntities && animal instanceof EntityHorse)
	                	{
	                		String[] petname_a = NameGenerator.newRandomName("pet", random);
	                		animal.setCustomNameTag((petname_a[1]+" "+petname_a[2]+" "+petname_a[3]).trim());
	                	}
	                    animal.setLocationAndAngles((double)this.getXWithOffset(uvw[0], uvw[2]) + 0.5D, (double)this.getYWithOffset(uvw[1]) + 0.5D, (double)this.getZWithOffset(uvw[0], uvw[2]) + 0.5D, random.nextFloat()*360F, 0.0F);
	                    world.spawnEntityInWorld(animal);
	                    
	                    // Dirt block underneath
	                    //this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, uvw[0], uvw[1]-1, uvw[2], structureBB);
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
    
    
    // --- Stepped Farm --- //
    // designed by Lonemind
    
    public static class JungleSteppedFarm extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			" FFFFF         ",
    			"FFFFFFFF   FFF ",
    			"FFFFFFFF  FFFFF",
    			"FFFFFFF   FFFFF",
    			"FFFFFFF   FF  F",
    			"FFFFFFF    FFF ",
    			" FFFFF         ",
    			"        FFFFF  ",
    			"       FFFFFFF ",
    			"       FFFFFFF ",
    			"  FFF  FFFFFFF ",
    			" FFF F FFFFFFF ",
    			" FFF F FFFFFFF ",
    			" FFFFF FFFFFF  ",
    			"  FFF   F      ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 6;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1+2+4+8; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleSteppedFarm() {}

        public JungleSteppedFarm(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleSteppedFarm buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleSteppedFarm(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
        	
        	// Dirt
        	for (int[] uuvvww : new int[][]{
        		// Back left
        		{1,0,9, 5,2,13}, 
        		// Front right 
        		{8,0,2, 12,1,6}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeDirtBlock, biomeDirtMeta,
        				biomeDirtBlock, biomeDirtMeta, 
        				false);
            }
    		
    		
            // Stone Brick
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickBlock = (Block)blockObject[0]; int biomeStoneBrickMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Back left 
    			{0,1,9, 0,1,10}, {0,2,10, 0,2,10}, {0,0,11, 0,0,11}, {0,2,12, 0,2,12}, {0,1,13, 0,1,13}, // Left wall
    			{1,0,14, 1,0,14}, {2,1,14, 2,2,14}, {3,2,14, 3,2,14}, {4,1,14, 4,2,14}, {5,0,14, 5,0,14}, // Back wall
    			{6,0,13, 6,1,13}, {6,0,11, 6,0,11}, {6,2,9, 6,2,11}, {6,3,11, 6,3,11}, {6,1,10, 6,1,10}, {6,3,9, 6,3,9}, {6,0,9, 6,0,9}, // Right wall
    			{1,1,8, 1,1,8}, {2,2,8, 4,2,8}, {4,1,8, 5,1,8}, {5,0,8, 5,0,8}, // Front wall
    			// Spout
    			{3,3,11, 3,3,11}, 
    			// Back right post
    			{10,0,10, 10,1,10}, 
    			// Front right
    			{7,0,2, 7,0,3}, {7,2,2, 7,2,2}, {7,2,4, 7,2,4}, {7,1,4, 7,1,5}, {7,0,6, 7,0,6}, // Left wall
    			{8,0,7, 9,0,7}, {9,1,7, 9,1,7}, {10,2,7, 10,2,7}, {11,1,7, 11,1,7}, {12,0,7, 12,0,7}, // Back wall
    			{13,0,5, 13,0,5}, {13,1,3, 13,1,4}, {13,0,3, 13,0,3}, // Right wall
    			{8,0,1, 9,0,1}, {10,1,1, 11,1,1}, {12,0,1, 12,0,1}, // Front wall
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickBlock, biomeStoneBrickMeta, biomeStoneBrickBlock, biomeStoneBrickMeta, false);	
    		}
    		
    		
    		// Chiseled Stone Brick
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeChiseledStoneBrickBlock = (Block)blockObject[0]; int biomeChiseledStoneBrickMeta = (Integer)blockObject[1];
    		for (int[] uvw : new int[][]{
    			// Back left 
    			{0,2,9}, {0,0,10}, {0,1,11}, {0,0,12}, {0,2,13}, // Left wall
    			{1,2,14}, {2,0,14}, {3,1,14}, {4,0,14}, {5,2,14}, // Back wall
    			{6,2,13}, {6,0,12}, {6,1,11}, {6,0,10}, {6,2,9}, // Right wall
    			{1,2,8}, {2,0,8}, {3,1,8}, {4,0,8}, {5,2,8}, // Front wall
    			// Spout
    			{3,4,11}, 
    			// Back right post
    			{10,2,10}, 
    			// Front right
    			{7,1,2}, {7,0,4}, {7,1,6}, // Left wall
    			{8,1,7}, {10,0,7}, {12,1,7}, // Back wall
    			{13,1,2}, {13,0,4}, {13,1,6}, // Right wall
    			{8,1,1}, {10,0,1}, {12,1,1}, // Front wall
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeChiseledStoneBrickBlock, biomeChiseledStoneBrickMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Cobblestone
    		for(int[] uvw : new int[][]{
    			// Back left 
    			{0,0,9}, {0,2,11}, {0,1,12}, {0,0,13}, // Left wall
    			{1,1,14}, {3,0,14}, {5,1,14}, // Back wall
    			{6,1,9}, {6,1,12}, {6,2,12}, // Right wall
    			{1,0,8}, {2,1,8}, {3,0,8}, // Front wall
    			// Front right
    			{7,1,3}, {7,0,5}, // Left wall
    			{10,1,7}, {11,0,7}, // Back wall
    			{13,0,2}, {13,1,5}, {13,0,6}, // Right wall
    			{9,1,1}, {11,0,1}, // Front wall
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Cobblestone stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Back left
    			{0,3,9, 0}, {0,3,12, 0},  // Left wall
    			{1,3,14, 2}, {4,3,14, 2},  // Back wall
    			{6,3,13, 1},  // Right wall
    			{1,3,8, 3}, {3,3,8, 3}, {4,3,8, 3},  // Front wall
    			// Back chute
    			{7,3,11, 2+4}, {9,3,11, 2+4}, {10,3,11, 2+4}, {11,3,11, 2+4}, {11,3,7, 1+4},  // Outside Rim
    			{7,3,9, 3+4},  // Inside Rim
    			// Front right
    			{7,2,6, 0},  // Left wall
    			{12,2,7, 2},  // Back wall
    			{13,2,6, 1}, {13,2,5, 1}, {13,2,3, 1},  // Right wall
    			{9,2,1, 3}, {11,2,1, 3}, {12,2,1, 3},  // Front wall
    			// Front chute
    			{6,2,4, 2+4},  // Back Rim
    			{5,2,2, 3+4},  // Front Rim
    			// Front left
    			{1,0,1, 0},  // Left wall
    			{2,0,4, 2}, {3,0,4, 2},  // Back wall
	   			 // Right wall
    			{3,0,0, 3},  // Front wall
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Stone Brick stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_brick_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Back left
    			{0,3,10, 0}, {0,3,11, 0}, {0,3,13, 0},  // Left wall
    			{2,3,14, 2}, {3,3,14, 2}, {5,3,14, 2},  // Back wall
    			{6,3,12, 1},  // Right wall
    			{2,3,8, 3}, {5,3,8, 3},  // Front wall
	   			// Back chute
    			{8,3,11, 2+4}, {11,3,9, 3+4}, {11,3,8, 1+4},  // Outside Rim
    			{8,3,9, 3+4}, {9,3,9, 3+4}, {9,3,8, 0+4}, {9,3,7, 0+4},  // Inside Rim
    			{11,2,10, 1+4}, // Spout
	   			// Back right
    			{10,0,11, 0}, {10,0,12, 0},  // Left wall
    			{11,0,13, 2}, {12,0,13, 2}, {13,0,13, 2},  // Back wall
    			{14,0,12, 1}, {14,0,11, 1}, {14,0,10, 1},  // Right wall
    			{11,0,9, 3}, {12,0,9, 3}, {13,0,9, 3},  // Front wall
	   			// Front right
    			{7,2,5, 0},  // Left wall
    			{8,2,7, 2}, {9,2,7, 2}, {11,2,7, 2},  // Back wall
    			{13,2,4, 1}, {13,2,2, 1},  // Right wall
    			{8,2,1, 3}, {10,2,1, 3},  // Front wall
	   			// Front chute
    			{6,2,2, 3+4},  // Front Rim
    			{5,2,4, 2+4},  // Back Rim
    			{5,1,3, 1+4},  // Bottom lip
	   			// Front left
    			{1,0,2, 0}, {1,0,3, 0},  // Left wall
    			{4,0,4, 2},  // Back wall
    			{5,0,3, 1}, {5,0,2, 1}, {5,0,1, 1},  // Right wall
    			{2,0,0, 3}, {4,0,0, 3},  // Front wall
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeStoneBrickStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Stone Brick Slab (upper)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 13, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickSlabLowerBlock = (Block)blockObject[0]; int biomeStoneBrickSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Back chute
    			{7,2,10, 9,2,10}, {10,2,8, 10,2,9}, 
    			// Front chute
    			{6,1,3, 6,1,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickSlabLowerBlock, biomeStoneBrickSlabLowerMeta, biomeStoneBrickSlabLowerBlock, biomeStoneBrickSlabLowerMeta, false);	
    		}
    		
            
            // Moist Farmland with crop above
        	Block[] cropBlocksTemp1 = StructureVillageVN.chooseCropPair(random);
        	Block[] cropBlocksTemp2 = StructureVillageVN.chooseCropPair(random);
        	Block[] cropBlocks = new Block[]{cropBlocksTemp1[0],cropBlocksTemp1[1],cropBlocksTemp2[0],cropBlocksTemp2[1]};
            for(int[] uvwmcp : new int[][]{
            	// u,v,w, farmland moisture (0:dry, 7:moist), crop (0-2), crop progress
            	// Back left
            	{1,3,13, 7,0,0}, {2,3,13, 7,0,0}, {3,3,13, 7,0,0}, {4,3,13, 7,0,0}, {5,3,13, 7,0,0}, 
            	{1,3,12, 7,0,0}, {2,3,12, 7,0,0}, {4,3,12, 7,0,0}, {5,3,12, 7,0,0}, 
            	{1,3,11, 7,0,0}, {5,3,11, 7,0,0}, 
            	{1,3,10, 7,0,0}, {2,3,10, 7,0,0}, 
            	{1,3,9, 7,0,0}, {2,3,9, 7,0,0}, {3,3,9, 7,0,0}, {4,3,9, 7,0,0}, {5,3,9, 7,0,0}, 
            	// Back right
            	{11,0,12, 7,1,0}, {12,0,12, 7,1,0}, {13,0,12, 7,1,0}, 
            	{11,0,11, 7,1,0}, {12,0,11, 7,1,0}, {13,0,11, 7,1,0}, 
            	{11,0,10, 7,1,0}, 
            	// Front right
            	{11,2,6, 7,2,0}, {12,2,6, 7,2,0}, 
            	{9,2,5, 7,2,0}, {10,2,5, 7,2,0}, {11,2,5, 7,2,0}, {12,2,5, 7,2,0}, 
            	{9,2,4, 7,2,0}, {10,2,4, 7,2,0}, {11,2,4, 7,2,0}, {12,2,4, 7,2,0}, 
            	{9,2,3, 7,2,0}, {10,2,3, 7,2,0}, {11,2,3, 7,2,0}, {12,2,3, 7,2,0}, 
            	{8,2,2, 7,2,0}, {9,2,2, 7,2,0}, {10,2,2, 7,2,0}, {11,2,2, 7,2,0}, {12,2,2, 7,2,0}, 
            	// Front left
            	{2,0,3, 7,3,0}, {3,0,3, 7,3,0}, 
            	{2,0,2, 7,3,0}, {3,0,2, 7,3,0}, 
            	{2,0,1, 7,3,0}, {3,0,1, 7,3,0}, {4,0,1, 7,3,0}, 
            	})
            {
            	this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, uvwmcp[0], uvwmcp[1]-1, uvwmcp[2], structureBB);
            	//this.clearCurrentPositionBlocksUpwards(world, uvwpmc[0], uvwpmc[1]+1, uvwpmc[2], structureBB);
            	this.placeBlockAtCurrentPosition(world, cropBlocks[uvwmcp[4]], uvwmcp[5], uvwmcp[0], uvwmcp[1]+1, uvwmcp[2], structureBB); 
            	this.placeBlockAtCurrentPosition(world, Blocks.farmland, uvwmcp[3], uvwmcp[0], uvwmcp[1], uvwmcp[2], structureBB); // 7 is moist
            }
            
            
    		// Water
    		for(int[] uuvvww : new int[][]{ 
    			// Top of back-left fountain
    			{3,5,11, 3,5,11}, 
    			// Back-left fountain and chute
    			{2,3,11, 2,3,11}, {3,3,12, 3,3,12}, {4,3,11, 4,3,11}, {3,3,10, 7,3,10}, 
    			// Back-right fountain
    			{12,0,10, 12,0,10}, 
    			// Front-right fountain
    			{8,2,6, 10,2,6}, 
    			// Front-left fountain
    			{4,0,2, 4,0,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);	
    		}
    		// Lower-leveled water
    		for(int[] uvw : new int[][]{ 
    			// Top of back-left fountain
    			{8,3,10, 1}, 
    			{9,3,10, 2}, 
    			{10,3,10, 3}, 
    			{10,3,9, 4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, Blocks.flowing_water, uvw[3], uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Attempt to add GardenCore Compost Bins. If this fails, do nothing
            Block compostBin = ModObjects.chooseModComposterBlock();
            if (compostBin != null)
            {
            	for(int[] uvw : new int[][]{
        			{7,0,1}, 
        			{7,0,12}, 
        			})
        		{
        			this.placeBlockAtCurrentPosition(world, compostBin, 0, uvw[0], uvw[1], uvw[2], structureBB);	
        			this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, uvw[0], uvw[1]-1, uvw[2], structureBB);	
        		}
            }
            
            
            // Ladder
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.ladder, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLadderBlock = (Block)blockObject[0];
        	for (int[] uuvvwwo : new int[][]{ // Orientation - 3:leftward, 1:rightward, 2:backward, 0:forward
        		{8,0,0, 8,1,0, 2},  
        		{7,0,13, 7,2,13, 1},  
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], uuvvwwo[3], uuvvwwo[4], uuvvwwo[5], biomeLadderBlock, StructureVillageVN.chooseLadderMeta(uuvvwwo[6], this.coordBaseMode), biomeLadderBlock, StructureVillageVN.chooseLadderMeta(uuvvwwo[6], this.coordBaseMode), false);
            }
    		
    		
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward, 3:leftward
            		// Leftward
        			{-1,0,9, 3}, {-1,1,9, 3}, {-1,1,10, 3}, {-1,2,10, 3}, {-1,1,13, 3}, {-1,2,13, 3}, // Back-left
        			{6,1,5, 3}, // Front-right
        			// Forward
        			{0,0,14, 0}, {0,1,14, 0}, {0,2,14, 0}, {2,0,15, 0}, {2,1,15, 0}, {3,1,15, 0}, {3,2,15, 0}, // Back-left
        			{8,1,8, 0}, // Front-right
        			// Rightward
        			{7,2,11, 1}, {7,1,11, 1},  // Back-left
        			{13,0,1, 1}, {13,1,1, 1}, {14,0,3, 1}, {14,1,3, 1}, {14,1,4, 1}, // Front-right
        			// Backward
        			{1,1,7, 2}, {1,2,7, 2}, {4,1,7, 2}, {4,2,7, 2}, {5,1,7, 2},  // Back-left
        			{9,0,0, 2}, {10,0,0, 2}, {10,1,0, 2}, // Front-right
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
    			
    			int u = 2+random.nextInt(2);
				int v=1;
    			int w = 1+random.nextInt(3);
				
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
    
    
    // --- Stone Animal Pen --- //
    // designed by AstroTibs
    
    public static class JungleStoneAnimalPen extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"    FFF    ",
    			"FFFFFFFFFFF",
    			"FFFFFFFFFFF",
    			"FFFFFPFFFFF",
    			"FFFFFPFFFFF",
    			"FFFFFPFFFFF",
    			"FFFFFPFFFFF",
    			"FFFFFPFFFFF",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 4;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 1;
    	private static final int DECREASE_MAX_U = 1;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleStoneAnimalPen() {}

        public JungleStoneAnimalPen(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleStoneAnimalPen buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleStoneAnimalPen(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Cobblestone
    		for (int[] uvw : new int[][]{
    			{0,0,0}, {0,0,2}, {0,0,3}, {0,0,5}, 
    			{1,0,6}, {4,0,7}, {5,1,7}, {8,0,6}, {10,0,1}, {10,0,2}, {10,0,6}, 
    			{2,0,0}, {4,1,0}, {6,0,0}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Stone Brick
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickBlock = (Block)blockObject[0]; int biomeStoneBrickMeta = (Integer)blockObject[1];
    		for (int[] uvw : new int[][]{
    			{0,0,1}, 
    			{0,0,4}, 
    			{0,0,6}, 
    			{2,0,6}, {3,0,6}, {4,0,6}, {4,1,6}, {6,0,6}, {6,1,6}, {7,0,6}, {9,0,6}, {10,0,5}, {10,0,4}, {10,0,3}, 
    			{4,1,7}, {5,0,7}, {6,0,7}, {6,1,7}, 
    			{10,0,0}, {9,0,0}, {8,0,0}, {7,0,0}, {6,1,0}, {4,0,0}, {3,0,0}, {1,0,0}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeStoneBrickBlock, biomeStoneBrickMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Chiseled Stone Brick
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeChiseledStoneBrickBlock = (Block)blockObject[0]; int biomeChiseledStoneBrickMeta = (Integer)blockObject[1];
    		for (int[] uvw : new int[][]{
    			{4,2,6}, {6,2,6}, 
    			{4,2,0}, {6,2,0}, 
    			{5,0,6}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeChiseledStoneBrickBlock, biomeChiseledStoneBrickMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
			
			
			// Cobblestone wall
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
			for (int[] uuvvww : new int[][]{
				{1,1,6, 3,1,6}, {7,1,6, 9,1,6}, 
				{0,1,1, 0,1,6}, {10,1,1, 10,1,6}, 
				{0,1,0, 3,1,0}, {7,1,0, 10,1,0}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, false);
			}
			
            
			// Fence
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
			for (int[] uuvvww : new int[][]{
				{4,1,1, 4,1,1}, {4,1,3, 4,1,5}, 
				{6,1,1, 6,1,1}, {6,1,3, 6,1,5}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
			}
            
            
        	// Fence Gate (Along)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence_gate, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceGateBlock = (Block)blockObject[0]; int biomeFenceGateMeta = (Integer)blockObject[1];
        	for(int[] uvw : new int[][]{
            	{4,1,2}, {6,1,2}, 
            	})
            {
        		this.placeBlockAtCurrentPosition(world, biomeFenceGateBlock, StructureVillageVN.getMetadataWithOffset(biomeFenceGateBlock, (biomeFenceGateMeta+1)%8, this.coordBaseMode), uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			{4,3,6, -1}, {6,3,6, -1}, 
    			{4,3,0, -1}, {6,3,0, -1}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
        	
        	// Grass blocks with grass patches atop
        	for (int[] uuvvww : new int[][]{
				{1,0,1, 3,0,4}, {7,0,1, 9,0,4}, 
        	})
        	{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeTopBlock, biomeTopMeta, biomeTopBlock, biomeTopMeta, false);	
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1]+1, uuvvww[2], uuvvww[3], uuvvww[4]+1, uuvvww[5], Blocks.tallgrass, 1, Blocks.tallgrass, 1, false);	
        	}
    		
        	
        	// Grass blocks under the fence
        	for (int[] uuvvww : new int[][]{
				{4,0,1, 4,0,4}, {6,0,1, 6,0,4}, 
        	})
        	{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeTopBlock, biomeTopMeta, biomeTopBlock, biomeTopMeta, false);	
        	}
			
			
			// Water
			for(int[] uuvvww : new int[][]{
				{1,0,5, 9,0,5}, {5,1,6, 5,1,6}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);	
			}
            
            
            // Animals
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	if (VillageGeneratorConfigHandler.villageAnimalRestrictionLevel<1)
            	{
	            	// Animals
	            	for (int[] uvw : new int[][]{
	        			{2,1,2}, {8,1,3}, 
	        			})
	        		{
	                	EntityLiving animal = StructureVillageVN.getVillageAnimal(world, random, false, this.materialType==MaterialType.MUSHROOM); // Because horses can escape the pen
	                    animal.setLocationAndAngles((double)this.getXWithOffset(uvw[0], uvw[2]) + 0.5D, (double)this.getYWithOffset(uvw[1]) + 0.5D, (double)this.getZWithOffset(uvw[0], uvw[2]) + 0.5D, random.nextFloat()*360F, 0.0F);
	                    world.spawnEntityInWorld(animal);
	                    
	                    // Dirt block underneath
	                    //this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, uvw[0], uvw[1]-1, uvw[2], structureBB);
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
    
    
    // --- Tamed Farm --- //
    // designed by Lonemind
    
    public static class JungleTamedFarm extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"    FFFF     ",
    			"    FFFF     ",
    			" FFFFFFFFFF  ",
    			" FFFFFFFFFF  ",
    			" FFFFFFFFFF  ",
    			" FFFFFFFFFF  ",
    			" FFFFFFFFFF  ",
    			" FFFFFFFFFFFF",
    			"FFFFFFFFFFFFF",
    			"FFFFFFFFFFFFF",
    			"FFFFFFFFFFFFF",
    			"FFFFFFFFFFFFF",
    			"   FFFFFF    ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 8;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 3;
    	private static final int DECREASE_MAX_U = 4;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleTamedFarm() {}

        public JungleTamedFarm(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleTamedFarm buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleTamedFarm(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
        	
        	// Vertical logs
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, materialType, biome, disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{0,0,1, 0,0,1}, 
        		{0,0,4, 0,0,4}, 
        		{1,0,10, 1,0,10}, 
        		{3,0,0, 3,0,1}, 
        		{4,0,10, 4,0,10}, 
        		{4,0,12, 4,0,12}, 
        		{7,0,10, 7,0,10}, 
        		{7,0,12, 7,0,12}, 
        		{8,0,0, 8,0,1}, 
        		{10,0,5, 10,0,5}, 
        		{10,0,10, 10,0,10}, 
        		{12,0,1, 12,0,1}, 
        		{12,0,5, 12,0,5}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeLogVertBlock, biomeLogVertMeta,
        				biomeLogVertBlock, biomeLogVertMeta, 
        				false);
            }
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Front
    			{1,0,1, 3}, {2,0,1, 3}, {4,0,0, 3}, {5,0,0, 3}, {6,0,0, 3}, {7,0,0, 3}, {9,0,1, 3}, {10,0,1, 3}, {11,0,1, 3}, 
    			// Right
    			{12,0,2, 1}, {12,0,3, 1}, {12,0,4, 1}, {11,0,5, 2}, {10,0,6, 1}, {10,0,7, 1}, {10,0,8, 1}, {10,0,9, 1}, 
    			// Back
    			{2,0,10, 2}, {3,0,10, 2}, {4,0,11, 0}, {5,0,12, 2}, {6,0,12, 2}, {7,0,11, 1}, {8,0,10, 2}, {9,0,10, 2}, 
    			// Left
    			{0,0,2, 0}, {0,0,3, 0}, {1,0,4, 2}, {1,0,5, 0}, {1,0,6, 0}, {1,0,7, 0}, {1,0,8, 0}, {1,0,9, 0}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Grass
    		for(int[] uuvvww : new int[][]{
    			{1,0,2, 1,0,3}, 
    			{2,0,8, 3,0,9}, 
    			{4,0,1, 4,0,6}, {4,0,9, 4,0,9}, 
    			{5,0,8, 5,0,8}, 
    			{5,0,1, 6,0,1}, {5,0,11, 6,0,11}, {5,0,10, 5,0,10}, 
    			{7,0,1, 7,0,6}, {7,0,8, 7,0,8}, 
    			{8,0,7, 9,0,9}, 
    			{10,0,2, 11,0,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeGrassBlock, biomeGrassMeta, biomeGrassBlock, biomeGrassMeta, false);	
    		}
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uuvvww : new int[][]{
        		{1,1,3, 1,1,3}, 
        		{3,1,9, 3,1,9}, 
        		{4,1,6, 4,1,6}, 
        		{7,1,1, 7,1,1}, 
        		{8,1,7, 8,1,7}, 
        		{10,1,3, 10,1,3}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// On fence posts
    			{1,2,3, -1}, 
        		{3,2,9, -1}, 
        		{4,2,6, -1}, 
        		{7,2,1, -1}, 
        		{8,2,7, -1}, 
        		{10,2,3, -1}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
        	
            
            // Moist Farmland with crop above
        	Block[] cropBlocksTemp1 = StructureVillageVN.chooseCropPair(random);
        	Block[] cropBlocksTemp2 = StructureVillageVN.chooseCropPair(random);
        	Block[] cropBlocks = new Block[]{cropBlocksTemp1[0],cropBlocksTemp1[1],cropBlocksTemp2[0]};
            for(int[] uvwmcp : new int[][]{
            	// u,v,w, farmland moisture (0:dry, 7:moist), crop (0-2), crop progress
            	// Left row
            	{2,0,2, 7,0,0}, {2,0,3, 7,0,0}, {2,0,4, 7,0,0}, {2,0,5, 7,0,0}, {2,0,6, 7,0,0}, {2,0,7, 7,0,0}, 
            	{3,0,2, 7,0,0}, {3,0,3, 7,0,0}, {3,0,4, 7,0,0}, {3,0,5, 7,0,0}, {3,0,6, 7,0,0}, {3,0,7, 7,0,0}, 
            	// Center row
            	{5,0,2, 7,1,0}, {5,0,3, 7,1,0}, {5,0,4, 7,1,0}, {5,0,5, 7,1,0}, 
            	{6,0,2, 7,1,0}, {6,0,3, 7,1,0}, {6,0,4, 7,1,0}, {6,0,5, 7,1,0}, 
            	// Right row
            	{8,0,2, 7,2,0}, {8,0,3, 7,2,0}, {8,0,4, 7,2,0}, {8,0,5, 7,2,0}, {8,0,6, 7,2,0}, 
            	{9,0,2, 7,2,0}, {9,0,3, 7,2,0}, {9,0,4, 7,2,0}, {9,0,5, 7,2,0}, {9,0,6, 7,2,0}, 
            	})
            {
            	this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, uvwmcp[0], uvwmcp[1]-1, uvwmcp[2], structureBB);
            	//this.clearCurrentPositionBlocksUpwards(world, uvwpmc[0], uvwpmc[1]+1, uvwpmc[2], structureBB);
            	this.placeBlockAtCurrentPosition(world, cropBlocks[uvwmcp[4]], uvwmcp[5], uvwmcp[0], uvwmcp[1]+1, uvwmcp[2], structureBB); 
            	this.placeBlockAtCurrentPosition(world, Blocks.farmland, uvwmcp[3], uvwmcp[0], uvwmcp[1], uvwmcp[2], structureBB); // 7 is moist
            }
    		
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			{5,1,8, 5,2,9}, {5,2,8, 5,3,9}, 
    			{6,3,9, 6,3,9}, {6,5,9, 6,5,9}, 
    			{6,2,10, 6,3,10}, {6,5,10, 6,5,10}, 
    			{7,1,8, 7,1,9}, {7,4,9, 7,5,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
            // Stone Brick
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickBlock = (Block)blockObject[0]; int biomeStoneBrickMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{5,1,9, 5,1,9}, {5,4,9, 5,5,9}, 
    			{6,1,9, 6,2,9}, {6,4,9, 6,4,9}, 
    			{6,1,10, 6,1,10}, {6,4,10, 6,4,10}, 
    			{7,2,8, 7,3,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickBlock, biomeStoneBrickMeta, biomeStoneBrickBlock, biomeStoneBrickMeta, false);	
    		}
    		
    		
    		// Cobblestone stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			{5,3,8, 3}, 
    			{6,6,10, 2}, 
    			{7,6,9, 1}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Stone Brick stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_brick_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			{5,6,9, 0}, 
    			{7,4,8, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeStoneBrickStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Cobblestone Slab (lower)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneSlabLowerBlock = (Block)blockObject[0]; int biomeCobblestoneSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uvw : new int[][]{
    			{6,7,9}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeCobblestoneSlabLowerBlock, biomeCobblestoneSlabLowerMeta, uvw[0], uvw[1], uvw[2], structureBB);	
    		}
            
            
    		// Water
    		for(int[] uuvvww : new int[][]{
    			{4,0,7, 4,0,8}, 
    			{5,0,6, 6,0,7}, 
    			{6,0,8, 6,0,8}, 
    			{7,0,7, 7,0,7}, 
    			// Top of fountain
    			{6,6,9, 6,6,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);	
    		}
    		
    		
    		// Attempt to add GardenCore Compost Bins. If this fails, do nothing
            Block compostBin = ModObjects.chooseModComposterBlock();
            if (compostBin != null)
            {
            	for(int[] uvw : new int[][]{
        			{4,1,1}, 
        			})
        		{
        			this.placeBlockAtCurrentPosition(world, compostBin, 0, uvw[0], uvw[1], uvw[2], structureBB);	
        			this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, uvw[0], uvw[1]-1, uvw[2], structureBB);	
        		}
            }
            
            
    		// Bamboo
    		blockObject = ModObjects.chooseModBambooStalk(0);
			if (blockObject!=null)
    		{
				for (int[] uuvvwws : new int[][]{
					{2,1,8, 2,3,8, 3}, {2,4,8, 2,4,8, 4}, {2,5,8, 2,6,8, 5}, 
					{2,1,9, 2,1,9, 3}, {2,2,9, 2,3,9, 4}, {2,4,9, 2,4,9, 5}, 
					
					{4,1,9, 4,1,9, 0}, {4,2,9, 4,3,9, 1},
					
					{8,1,9, 8,1,9, 0}, {8,2,9, 8,2,9, 1}, 
					{9,1,8, 9,2,8, 3}, {9,3,8, 9,3,8, 4}, {9,4,8, 9,5,8, 5}, 
					
					{10,1,2, 10,1,2, 3}, {10,2,2, 10,3,2, 4}, {10,4,2, 10,4,2, 5}, 
            		})
                {
					blockObject = ModObjects.chooseModBambooStalk(uuvvwws[6]);
					this.fillWithMetadataBlocks(world, structureBB, uuvvwws[0], uuvvwws[1], uuvvwws[2], uuvvwws[3], uuvvwws[4], uuvvwws[5],
							(Block)blockObject[0], (Integer)blockObject[1], (Block)blockObject[0], (Integer)blockObject[1], false);	
                }
				
				// Leaf toppers
				blockObject = ModObjects.chooseModBambooLeaves();
				if (blockObject!=null)
	    		{
					Block bambooLeavesBlock = (Block)blockObject[0]; int bambooLeavesMeta = (Integer)blockObject[1];
					
					for (int[] uuvvww : new int[][]{
						{2,7,8}, 
						{2,5,9}, 
						{9,6,8}, 
						{10,5,2}, 
	            		})
	                {
						this.placeBlockAtCurrentPosition(world, bambooLeavesBlock, bambooLeavesMeta, uuvvww[0], uuvvww[1], uuvvww[2], structureBB);	
	                }
	    		}
    		}
			else // As sugarcane
			{
				Block bambooBlock = Blocks.reeds; int bambooMeta = 0;

				// Add water to support the sugarcane
				for(int[] uvw : new int[][]{
	    			{5,0,9}, 
	    			{7,0,9}, 
	    			})
	    		{
	    			this.placeBlockAtCurrentPosition(world, Blocks.flowing_water, 0, uvw[0], uvw[1], uvw[2], structureBB);
	    		}
				
				for (int[] uuvvww : new int[][]{
					{4,1,9, 4,2,9}, 
					{8,1,9, 8,1,9}, 
            		})
                {
					this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], bambooBlock, bambooMeta, bambooBlock, bambooMeta, false);	
                }
			}
			
            
    		// Bamboo Shoots
    		ItemStack modItemStack = ModObjects.chooseModBambooShoot();
			if (modItemStack!=null)
    		{
				Block bambooShootBlock = Block.getBlockFromItem(modItemStack.getItem()); int bambooShootMeta = modItemStack.getItemDamage();
				
				if (bambooShootBlock!=null)
	    		{
					for (int[] uvw : new int[][]{
						{8,1,8}, 
						{9,1,7}, 
	            		})
	                {
						this.placeBlockAtCurrentPosition(world, bambooShootBlock, bambooShootMeta, uvw[0], uvw[1], uvw[2], structureBB);	
	                }
	    		}
    		}
    		
    		
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward, 3:leftward
            		// Leftward
        			{4,1,8, 3}, {4,2,8, 3}, 
        			{4,4,9, 3}, {4,5,9, 3}, 
        			// Backward
        			{7,1,7, 2}, {7,2,7, 2}, {7,3,7, 2}, 
        			// Forward
        			{5,3,10, 0}, {5,4,10, 0}, 
        			{6,1,11, 0}, {6,2,11, 0}, {6,3,11, 0}, 
        			// Rightward
        			{8,2,8, 1}, {8,3,8, 1}, 
        			{8,3,9, 1}, {8,4,9, 1}, {8,5,9, 1}, 
        			{7,1,10, 1}, {7,2,10, 1}, {7,3,10, 1}, {7,4,10, 1}, 
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
    			
    			int u = 2+random.nextInt(8);
				int v=1;
    			int w = 2+random.nextInt(4);
				
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
    
    
    // --- Tannery 1 --- //
    // designed by AstroTibs
    
    public static class JungleTannery1 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"   F  F ",
    			"FFFFFFFF",
    			"FFFFFFFF",
    			"FFFFFFFF",
    			"FFFFFFFF",
    			"FFFFFFFF",
    			"FFFFFFFF",
    			"FFFFFFFF",
    			" FFFPPFF",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 7;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 2;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleTannery1() {}

        public JungleTannery1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleTannery1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleTannery1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			// Counter
    			{3,0,1, 6,0,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Stripped Logs (Vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
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
    		for (int[] uuvvww : new int[][]{
    			{3,1,6, 3,1,6}, {6,1,6, 6,1,6}, 
    			{3,1,4, 3,1,4}, {6,1,4, 6,1,4}, 
    			{3,1,2, 3,1,2}, {6,1,2, 6,1,2}, 
    			
    			{3,4,6, 3,4,6}, {6,4,6, 6,4,6}, 
    			{3,4,4, 3,4,4}, {6,4,4, 6,4,4}, 
    			{3,4,2, 3,4,2}, {6,4,2, 6,4,2}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
    		
        	
        	// Stone Brick wall
    		Object[] tryStoneBrickWall = ModObjects.chooseModStoneBrickWallBlock();
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(tryStoneBrickWall!=null?(Block)tryStoneBrickWall[0]:Blocks.cobblestone_wall, tryStoneBrickWall!=null?(Integer)tryStoneBrickWall[1]:0, this.materialType, this.biome, this.disallowModSubs);
        	Block biomeStoneBrickWallBlock = (Block)blockObject[0]; int biomeStoneBrickWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
            	{3,1,8, 3,1,8}, {6,1,8, 6,1,8}, 
            	{2,1,7, 2,1,7}, {7,1,7, 7,1,7}, 
            	{2,1,1, 2,1,1}, {7,1,1, 7,1,1}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickWallBlock, biomeStoneBrickWallMeta, biomeStoneBrickWallBlock, biomeStoneBrickWallMeta, false);
            }
    		
    		
    		// Torches part 1
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	{3,2,8, -1}, {6,2,8, -1}, 
            	{2,2,7, -1}, {7,2,7, -1}, 
            	{2,2,1, -1}, {7,2,1, -1}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
            
        	
            // Stone brick
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickBlock = (Block)blockObject[0]; int biomeStoneBrickMeta = (Integer)blockObject[1];
            for (int[] uuvvww : new int[][]{
            	{3,1,7, 6,4,7}, 
            	{3,1,5, 3,4,5}, {6,1,5, 6,4,5}, 
            	{3,1,3, 3,4,3}, {6,1,3, 6,4,3}, 
            	{3,1,1, 3,4,1}, {4,4,1, 5,4,1}, {6,1,1, 6,4,1}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickBlock, biomeStoneBrickMeta, biomeStoneBrickBlock, biomeStoneBrickMeta, false);
            }
    		
    		
    		// Torches part 2
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			{4,3,5, 1}, {5,3,5, 3}, 
    			{4,3,3, 1}, {5,3,3, 3}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
    			{3,2,6}, {6,2,6}, 
    			{3,2,4}, {6,2,4}, 
    			{3,2,2}, {6,2,2}, 
    			{3,3,6}, {6,3,6}, 
    			{3,3,4}, {6,3,4}, 
    			{3,3,2}, {6,3,2}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Fences
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
    		for (int[] uuvvww : new int[][]{
    			// Awning scaffold
    			{0,1,7, 0,2,7}, 
    			{0,1,4, 0,2,4}, 
    			{0,1,1, 0,2,1}, 
    			// Roof
    			{4,5,1, 5,5,1}, {4,5,7, 5,5,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Front awning
    			{4,3,0, 3}, {5,3,0, 3}, 
    			// Roof
    			{3,5,7, 0}, {6,5,7, 1}, 
    			{3,5,6, 0}, {6,5,6, 1}, 
    			{3,5,5, 0}, {6,5,5, 1}, 
    			{3,5,4, 0}, {6,5,4, 1}, 
    			{3,5,3, 0}, {6,5,3, 1}, 
    			{3,5,2, 0}, {6,5,2, 1}, 
    			{3,5,1, 0}, {6,5,1, 1}, 
    			{4,6,7, 0}, {5,6,7, 1}, 
    			{4,6,6, 0}, {5,6,6, 1}, 
    			{4,6,5, 0}, {5,6,5, 1}, 
    			{4,6,4, 0}, {5,6,4, 1}, 
    			{4,6,3, 0}, {5,6,3, 1}, 
    			{4,6,2, 0}, {5,6,2, 1}, 
    			{4,6,1, 0}, {5,6,1, 1}, 
    			// Desk
    			{5,1,4, 0+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{0,3,1, 0,3,7}, {2,4,1, 2,4,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{1,3,1, 1,3,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
            
        	
            // Cauldron
        	for (int[] uvw : new int[][]{
        		{2,1,6}, 
        		{2,1,5}, 
        		{2,1,4}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.cauldron, 3, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Smooth Stone Block
        	blockObject = ModObjects.chooseModSmoothStoneBlockObject(); Block smoothStoneBlock = (Block)blockObject[0]; int smoothStoneMeta = (Integer)blockObject[1];
            for (int[] uuvvww : new int[][]{
            	// Counter
            	{4,1,6, 5,1,6}, {5,1,5, 5,1,5}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], smoothStoneBlock, smoothStoneMeta, smoothStoneBlock, smoothStoneMeta, false);
            }
    		
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{4,1,1, 2, 1, 1}, {5,1,1, 2, 1, 0}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
    		
    		
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward, 3:leftward
        			// Forward
        			{4,4,8, 0}, {5,4,8, 0}, {5,3,8, 0}, 
        			// Rightward
        			{7,4,4, 1}, 
        			{7,4,5, 1}, {7,3,5, 1}, {7,2,5, 1}, {7,1,5, 1}, 
        			{7,4,6, 1}, {7,3,6, 1}, {7,2,6, 1}, 
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
    			
    			int s = random.nextInt(13);
    			
    			int u = s<=4 ? 1 : s<=6 ? 2 : s<=10 ? 4 : 5;
    			int v = 1;
    			int w = s<=4 ? s+2 : s<=6 ? s-3 : s<=10 ? s-5 : s-9;
    			
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
    
    
    // --- Tannery 2 --- //
    // designed by AstroTibs
    
    public static class JungleTannery2 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"  F  F  ",
    			" FFFFFF ",
    			"FFFFFFFF",
    			" FFFFFF ",
    			" FFFFFF ",
    			"FFFFFFFF",
    			" FFFFFF ",
    			" F  PPF ",
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
    	
        public JungleTannery2() {}

        public JungleTannery2(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleTannery2 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleTannery2(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
        	// Dirt
        	for (int[] uuvvww : new int[][]{
        		{2,2,3, 5,2,5}, 
        		{2,1,2, 5,1,5}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeFillerBlock, biomeFillerMeta,
        				biomeFillerBlock, biomeFillerMeta, 
        				false);
            }
    		
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			// Counter
    			{2,3,5, 5,3,5}, 
    			{4,3,4, 5,3,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
        	
        	
            // Glazed terracotta
        	Object[] tryGlazedTerracotta;
        	for (int[] uvwoc : new int[][]{ // u,v,w, orientation, dye color
        		// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
        		{3,3,6, 1, GeneralConfig.useVillageColors ? this.townColor : 0}, // White
        		{4,3,6, 0, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
        		{4,2,6, 3, GeneralConfig.useVillageColors ? this.townColor : 0}, // White
        		{3,2,6, 2, GeneralConfig.useVillageColors ? this.townColor2 : 4}, // Yellow
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
    		
        	
        	// Stone Brick wall
    		Object[] tryStoneBrickWall = ModObjects.chooseModStoneBrickWallBlock();
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(tryStoneBrickWall!=null?(Block)tryStoneBrickWall[0]:Blocks.cobblestone_wall, tryStoneBrickWall!=null?(Integer)tryStoneBrickWall[1]:0, this.materialType, this.biome, this.disallowModSubs);
        	Block biomeStoneBrickWallBlock = (Block)blockObject[0]; int biomeStoneBrickWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		// Left wall
            	{1,1,0, 1,1,0}, {1,1,1, 1,2,1}, {1,1,2, 1,3,2}, {1,1,3, 1,4,3}, 
            	// Right wall
            	{6,1,0, 6,1,0}, {6,2,1, 6,2,1}, {6,3,2, 6,3,2}, {6,4,3, 6,4,3}, 
            	// Outside posts
            	{0,1,2, 0,2,2}, {0,1,5, 0,2,5}, {2,1,7, 2,2,7}, 
            	{7,1,2, 7,2,2}, {7,1,5, 7,2,5}, {5,1,7, 5,2,7}, 
            	// Roof supports
            	{1,5,4, 1,5,4}, {1,5,6, 1,5,6}, 
            	{6,5,4, 6,5,4}, {6,5,6, 6,5,6}, 
            	{3,4,4, 3,5,4}, {6,4,4, 6,5,4}, {3,5,6, 4,5,6}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickWallBlock, biomeStoneBrickWallMeta, biomeStoneBrickWallBlock, biomeStoneBrickWallMeta, false);
            }
    		
    		
    		// Logs (Along)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Longer log
    			{1,6,5, 1,6,5}, {6,6,5, 6,6,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAlongBlock, biomeLogHorAlongMeta, biomeLogHorAlongBlock, biomeLogHorAlongMeta, false);	
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	{0,3,2, -1}, {0,3,5, -1}, {2,3,7, -1}, 
            	{7,3,2, -1}, {7,3,5, -1}, {5,3,7, -1}, 
            	{2,6,5, 1}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
            
        	
            // Stone brick
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickBlock = (Block)blockObject[0]; int biomeStoneBrickMeta = (Integer)blockObject[1];
            for (int[] uuvvww : new int[][]{
            	// Left wall
            	{1,1,4, 1,4,6}, 
            	// Right wall
            	{6,1,5, 6,4,6}, {6,1,3, 6,3,4}, {6,1,2, 6,2,2}, {6,1,1, 6,1,1}, 
            	// Front steps
            	{2,4,4, 2,4,4}, 
            	{2,3,3, 3,3,4}, 
            	{2,2,2, 3,2,2}, 
            	{2,1,1, 3,1,1}, 
            	// Back wall
            	{2,1,6, 2,4,6}, {5,1,6, 5,4,6}, 
            	{3,1,6, 4,1,6}, {3,4,6, 4,4,6}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickBlock, biomeStoneBrickMeta, biomeStoneBrickBlock, biomeStoneBrickMeta, false);
            }
    		
    		
    		// Fences
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
    		for (int[] uuvvww : new int[][]{
    			// Roof support
    			{2,5,4, 2,5,4}, {1,5,5, 1,5,5}, 
    			{2,5,6, 2,5,6}, {5,5,6, 5,5,6}, 
    			{6,5,5, 6,5,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
    		}
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{1,6,4, 6,6,4}, 
    			{1,7,5, 6,7,5}, 
    			{1,6,6, 6,6,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
            
        	
            // Cauldron
        	for (int[] uvw : new int[][]{
        		{2,4,3}, 
        		{2,3,2}, 
        		{2,2,1}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.cauldron, 3, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Cobblestone stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Stairs
    			{4,3,3, 3}, {5,3,3, 3}, 
    			{4,2,2, 3}, {5,2,2, 3}, 
    			{4,1,1, 3}, {5,1,1, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		

    		// Chest
    		// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
    		int chestU = 2;
    		int chestV = 4;
    		int chestW = 5;
    		int chestO = 1; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    		Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
    		this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
    		world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
    		TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
    		if (te instanceof IInventory)
    		{
    			ChestGenHooks chestGenHook = ChestGenHooks.getInfo(Reference.VN_TANNERY);
    			WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
    		}
    		
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			int s = random.nextInt(14);
    			
    			int u = s<=3 ? 3 : s<=8 ? 4 : 5;
    			int v = s<=0 ? 2 : s<=1 ? 3 : s<=3 ? 4 : s<=4 ? 2 : s<=5 ? 3 : s<=8 ? 4 : s<=9 ? 2 : s<=10 ? 3 : 4;
    			int w = s<=0 ? 1 : s<=1 ? 2 : s<=2 ? 3 : s<=3 ? 5 : s<=8 ? s-3 : s-8;
    			
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
    // designed by Lonemind
    
    public static class JungleTemple extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
        private static final String[] foundationPattern = new String[]{
    			"    FFFF    ",
    			"   FFFFFF   ",
    			"  FFFFFFFF  ",
    			" FFFFFFFFFF ",
    			"FFFFFFFFFFFF",
    			"FFFFFFFFFFFF",
    			"FFFFFFFFFFFF",
    			"FFFFFFFFFFFF",
    			" FFFFFFFFFF ",
    			"  FFFFFFFF  ",
    			"   FFFFFF   ",
    			"    FFFF    ",
        };
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 9;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1 + 2 + 4 + 8; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
    	public JungleTemple() {}

    	public JungleTemple(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
    	{
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
    	}
    	
    	public static JungleTemple buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    	{
    		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
    		
    		return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null) ? new JungleTemple(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Cobblestone
    		for (int[] uvw : new int[][]{
    			// Stairs
    			{5,1,1}, {5,2,3}, {6,1,3}, {6,2,3}, {6,2,2}, // Front
    			{3,2,5}, {3,3,6}, // Left
    			{5,1,9}, {5,2,8}, {6,3,8}, {6,1,9}, // Back
    			{8,1,5}, {10,1,5}, {8,2,6}, {10,1,6}, // Right
    			// Spires
    			{3,2,3}, 
    			{3,1,8}, 
    			{8,2,3}, {9,1,2}, 
    			{9,1,9}, 
    			// Set into grass
    			{2,0,4}, {4,0,4}, {6,0,4}, 
    			{5,0,7}, {6,0,7}, {7,0,9}, 
    			{5,0,5}, {5,0,5}, {6,0,6}, 
    			// Top spire
    			{5,4,4}, {5,4,6}, 
    			{4,6,4}, {7,5,4}, {7,5,7},  {4,5,7}, {4,6,7}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Stone Brick
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickBlock = (Block)blockObject[0]; int biomeStoneBrickMeta = (Integer)blockObject[1];
    		for (int[] uvw : new int[][]{
    			// Stairs
    			{5,1,2}, {5,1,3}, {5,2,2}, {5,3,3}, {6,1,1}, {6,1,2}, {6,3,3}, // Front
    			{1,1,5}, {2,1,5}, {2,2,5}, {3,1,5}, {3,3,5}, {1,1,6}, {2,1,6}, {3,1,6}, {2,2,6}, {3,2,6}, // Left
    			{5,1,8}, {5,1,10}, {5,3,8}, {5,2,9}, {6,1,8}, {6,2,8}, {6,2,9}, {6,1,10}, // Back
    			{8,2,5}, {8,3,5}, {8,1,6}, {8,3,6}, {9,1,5}, {9,2,5}, {9,1,6}, {9,2,6}, // Right
    			// Spires
    			{2,1,2}, {3,1,3}, 
    			{8,1,3}, 
    			{2,1,9}, {3,2,8}, 
    			{8,1,8}, {8,2,8}, 
    			// Set into grass 
    			{3,0,2}, 
    			{4,0,5}, {4,0,7}, 
    			{5,0,4}, 
    			{7,0,4}, {7,0,6}, {7,0,7}, 
    			{8,0,2}, 
    			{9,0,7}, 
    			// Top spire 
    			{4,4,4}, {4,4,5}, {4,4,6}, {4,4,7}, 
    			{5,4,5}, {5,4,7}, 
    			{6,4,4}, {6,4,5}, {6,4,6}, {6,4,7}, 
    			{7,4,4}, {7,4,5}, {7,4,6}, {7,4,7}, 
    			{4,5,4}, 
    			{7,6,4}, {7,6,7}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeStoneBrickBlock, biomeStoneBrickMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Chiseled Stone Brick
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeChiseledStoneBrickBlock = (Block)blockObject[0]; int biomeChiseledStoneBrickMeta = (Integer)blockObject[1];
    		for (int[] uvw : new int[][]{
    			// Outer spires
    			{3,3,8}, {8,3,8}, 
    			{3,3,3}, {8,3,3}, 
    			// Awning corners
    			{4,7,7}, {7,7,7}, 
    			{4,7,4}, {7,7,4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeChiseledStoneBrickBlock, biomeChiseledStoneBrickMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Polished Andesite
    		blockObject = ModObjects.chooseModPolishedAndesiteObject();
    		if (blockObject==null) // Try polished stone
    		{
    			blockObject = ModObjects.chooseModSmoothStoneBlockObject(); // Guarantees a vanilla stone if this fails
    		};
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs); Block biomePolishedAndesiteBlock = (Block)blockObject[0]; int biomePolishedAndesiteMeta = (Integer)blockObject[1];
    		for (int[] uvw : new int[][]{
    			// Central platform
    			{5,1,6}, {6,1,6}, 
    			{5,1,5}, {6,1,5}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomePolishedAndesiteBlock, biomePolishedAndesiteMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Polished Andesite Slab (Lower)
    		blockObject = ModObjects.chooseModPolishedAndesiteSlabObject(false);
    		if (blockObject==null) // Use basic polished stone slab
    		{
    			blockObject = new Object[]{Blocks.stone_slab, 0};
    		};
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs); Block biomePolishedAndesiteSlabBottomBlock = (Block)blockObject[0]; int biomePolishedAndesiteSlabBottomMeta = (Integer)blockObject[1];
    		for (int[] uvw : new int[][]{
    			{2,2,2}, {3,4,3}, {9,2,2}, {8,4,3}, 
    			{2,2,9}, {3,4,8}, {9,2,9}, {8,4,8}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomePolishedAndesiteSlabBottomBlock, biomePolishedAndesiteSlabBottomMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Cobblestone stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Stairs
    			{6,1,0, 3}, {5,2,1, 3}, {6,3,2, 3}, // Front
    			{0,1,6, 0}, {1,2,5, 0}, {2,3,5, 0}, {3,4,6, 0}, // Left
    			{5,1,11, 2}, {6,2,10, 2}, {5,3,9, 2}, {6,3,9, 2}, {5,4,8, 2}, // Back
    			{11,1,6, 1}, {10,2,5, 1}, {10,2,6, 1}, {9,3,5, 1}, {8,4,6, 1}, // Right
    			// Lower roof rim
    			{5,7,7, 3+4}, {7,7,6, 0+4}, 
    			// Upper roof rim
    			{4,8,5, 0}, {4,8,7, 2}, {6,8,7, 2}, {7,8,7, 2}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeCobblestoneStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Stone Brick stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_brick_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Stairs
    			{5,1,0, 3}, {6,2,1, 3}, {5,3,2, 3}, {5,4,3, 3}, {6,4,3, 3}, // Front
    			{0,1,5, 0}, {1,2,6, 0}, {2,3,6, 0}, {3,4,5, 0}, // Left
    			{6,1,11, 2}, {5,2,10, 2}, {6,4,8, 2}, // Back
    			{11,1,5, 1}, {9,3,6, 1}, {8,4,5, 1}, // Right
    			// Lower roof rim
    			{5,7,4, 2+4}, {6,7,4, 2+4}, {4,7,5, 1+4}, {4,7,6, 1+4}, {7,7,5, 0+4}, {6,7,7, 3+4}, 
    			// Upper roof rim
    			{4,8,4, 3}, {5,8,4, 3}, {6,8,4, 3}, {7,8,4, 3}, 
    			{4,8,6, 0}, {7,8,5, 1}, {7,8,6, 1}, 
    			{5,8,7, 2}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeStoneBrickStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Fences
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
    		for (int[] uvw : new int[][]{
    			{0,1,4}, {0,1,7}, 
    			{4,1,0}, {7,1,0}, 
    			{11,1,4}, {11,1,7}, 
    			{4,1,11}, {7,1,11}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeFenceBlock, 0, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// On fence posts
    			{0,2,4, -1}, {0,2,7, -1}, 
    			{4,2,0, -1}, {7,2,0, -1}, 
    			{11,2,4, -1}, {11,2,7, -1}, 
    			{4,2,11, -1}, {7,2,11, -1},
    			// On andesite ritual altar
    			{5,2,6, -1},
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		
    		// Campfires
    		blockObject = ModObjects.chooseModCampfireBlock(random.nextInt(4), this.coordBaseMode); Block campfireBlock = (Block) blockObject[0]; int campfireMeta = (Integer) blockObject[1];
    		for(int[] uvw : new int[][]{
    			{5,5,6}, {6,5,6}, 
    			{5,5,5}, {6,5,5}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, campfireBlock, campfireMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    					
    		// Brewing stand
    		for (int[] uvw : new int[][]{
    			{6,2,5}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, ModObjects.chooseModBrewingStandBlock(), 0, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			// Villager
    			
    			int s = random.nextInt(12);
    			
    			int u = s<4 ? s+4 : s<6 ? 7 : s<10 ? 13-s : 4;
    			int v = 1;
    			int w = s<4 ? 4 : s<6 ? s+1 : s<10 ? 7 : 16-s;
    			
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
    
    
    // --- Tool Smithy 1 --- //
    // designed by AstroTibs
    
    public static class JungleToolSmithy1 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"       ",
    			"FFFFFF ",
    			"FFFFFFF",
    			"FFFFFFF",
    			"FFFFFFF",
    			"FFFPPFF",
    			"FFFPPFF",
    			"FFFPPFF",
    			"FFFPPFF",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 6;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 1;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleToolSmithy1() {}

        public JungleToolSmithy1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleToolSmithy1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleToolSmithy1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
    		
    		// Logs (Vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Vertical
    			{1,1,0, 1,1,0}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);	
    		}
    		
    		
    		// Logs (Along)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 4+(this.coordBaseMode%2==0? 4:0), this.materialType, this.biome, this.disallowModSubs); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Longer log
    			{2,1,0, 2,1,1}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogHorAlongBlock, biomeLogHorAlongMeta, biomeLogHorAlongBlock, biomeLogHorAlongMeta, false);	
    		}
    		
    		
    		// For stripped logs specifically
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
    		for (int[] uuvvww : new int[][]{
    			{0,1,7, 0,2,7}, {5,1,7, 5,2,7}, 
    			{0,1,4, 0,4,4}, {5,1,4, 5,4,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, biomeStrippedLogVerticBlock, biomeStrippedLogVerticMeta, false);
    		}
    		
    		
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Front wall
    			{1,1,4, 3,4,4}, {4,3,4, 4,4,4}, 
    			// Right wall
    			{5,1,5, 5,3,5}, {5,1,6, 5,2,6}, 
    			// Left wall
    			{0,1,5, 0,3,5}, {0,1,6, 0,2,6}, 
    			// Back wall
    			{1,1,7, 4,2,7}, {0,3,7, 5,3,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
    		
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			// Counter
    			{0,0,4, 5,0,7}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// Exterior
    			{4,3,3, 2}, 
    			// Interior
    			{2,3,5, 0}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		
    		// Fences
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
    		for (int[] uuvvww : new int[][]{
    			// Front posts
    			{0,1,1, 0,2,1}, {5,1,1, 5,2,1}, 
    			// Awning scaffold
    			{0,3,2, 0,3,3}, {0,4,3, 0,4,3}, 
    			{5,3,2, 5,3,3}, {5,4,3, 5,4,3}, 
    			// Back posts
    			{0,2,8, 0,2,8}, {5,2,8, 5,2,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Front roof
    			{0,3,1, 3}, {1,3,1, 3}, {2,3,1, 3}, {3,3,1, 3}, {4,3,1, 3}, {5,3,1, 3}, 
    			{0,4,2, 3}, {1,4,2, 3}, {2,4,2, 3}, {3,4,2, 3}, {4,4,2, 3}, {5,4,2, 3}, 
    			{0,5,3, 3}, {1,5,3, 3}, {2,5,3, 3}, {3,5,3, 3}, {4,5,3, 3}, {5,5,3, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Bottom)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{0,5,4, 5,5,4}, 
    			{0,4,6, 5,4,6}, {0,3,6, 0,3,6}, {5,3,6, 5,3,6}, 
    			{0,3,8, 5,3,8}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Roof
    			{0,4,5, 5,4,5}, 
    			// Shelves
    			{1,1,5, 1,2,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
    		
    		
    		// Smithing table
    		blockObject = ModObjects.chooseModSmithingTable(biomePlankMeta); Block smithingTableBlock = (Block) blockObject[0]; int smithingTableMeta = (Integer) blockObject[1];
    		for (int[] uvw : new int[][]{
    			{2,1,5}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, smithingTableBlock, smithingTableMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{4,1,4, 0, 1, 1}, 
    		})
    		{
    			for (int height=0; height<=1; height++)
    			{
    				this.placeBlockAtCurrentPosition(world, biomeWoodDoorBlock, StructureVillageVN.getDoorMetas(uvwoor[3], this.coordBaseMode, uvwoor[4]==1, uvwoor[5]==1)[height],
    						uvwoor[0], uvwoor[1]+height, uvwoor[2], structureBB);
    			}
    		}
    		
    		
    		// Coarse Dirt
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 1, this.materialType, this.biome, this.disallowModSubs); Block biomeCoarseDirtBlock = (Block)blockObject[0]; int biomeCoarseDirtMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{0,0,1, 2,0,3}, 
    			{5,0,1, 6,0,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCoarseDirtBlock, biomeCoarseDirtMeta, biomeCoarseDirtBlock, biomeCoarseDirtMeta, false);	
    		}
    		
    		
    		// Gravel - NOT biome-modified
    		for (int[] uvw : new int[][]{
    			{6,1,0}, 
    			{6,1,4}, {6,1,5}, {6,1,6}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, Blocks.gravel, 0, uvw[0], uvw[1], uvw[2], structureBB);	
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

    		// Chest
    		// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
    		int chestU = 0;
    		int chestV = 1;
    		int chestW = 3;
    		int chestO = 2; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    		Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
    		this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
    		world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
    		TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
    		if (te instanceof IInventory)
    		{
    			ChestGenHooks chestGenHook = ChestGenHooks.getInfo(Reference.VN_TOOLSMITH);
    			WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
    		}
    		
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			int s = random.nextInt(3);
    			
    			// Villager
    			int u;
    			int v;
    			int w;
    			
    			if (s==0)
    			{
    				u = 2+random.nextInt(2);
    				v = 1;
    				w = 5+random.nextInt(2);
    			}
    			else
    			{
    				u = 2+random.nextInt(4);
    				v = 1;
    				w = 2+random.nextInt(2);
    			}
    			
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
    
    
    // --- Tool Smithy 2 --- //
    // designed by AstroTibs
    
    public static class JungleToolSmithy2 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"    FFFF    ",
    			"    FFFF    ",
    			"    FFFF    ",
    			"   FFFFFF   ",
    			"FFFFFFFFFFFF",
    			"FFFFFFFFFFFF",
    			"FFFFFFFFFFFF ",
    			"FFFFFFFFFFFF",
    			"   FFFFFF   ",
    			"    FFFF    ",
    			"    FFFF    ",
    			"    FFFF    ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 10;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 3; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1+2+4+8; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleToolSmithy2() {}

        public JungleToolSmithy2(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleToolSmithy2 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleToolSmithy2(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
        	
            // Stone brick part 1
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickBlock = (Block)blockObject[0]; int biomeStoneBrickMeta = (Integer)blockObject[1];
            for (int[] uuvvww : new int[][]{
            	// Under steps
            	{5,3,9, 6,3,9}, 
            	{2,3,5, 2,3,6}, {9,3,5, 9,3,6}, 
            	{5,3,2, 6,3,2}, 
            	// Basement
            	{3,0,3, 4,0,8}, {5,0,3, 6,0,4}, {5,0,7, 6,0,8}, {7,0,3, 8,0,8}, 
            	{3,1,3, 3,4,8}, {4,1,3, 7,4,3}, {4,1,8, 7,4,8}, {8,1,3, 8,4,8}, 
            	// Temple
            	{3,5,4, 3,7,4}, {3,7,5, 3,7,6}, {3,5,7, 3,7,7}, 
            	{8,5,4, 8,7,4}, {8,7,5, 8,7,6}, {8,5,7, 8,7,7}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickBlock, biomeStoneBrickMeta, biomeStoneBrickBlock, biomeStoneBrickMeta, false);
            }
    		
        	
        	// Clear out basin
            for(int[] uuvvww : new int[][]{
    			{4,1,4, 7,3,7}, 
    			})
    		{
            	this.fillWithAir(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5]);	
    		}
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			// Upstairs
    			{4,6,7, 1}, 
    			// Downstairs
    			{6,3,7, 2}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		
            // Stone brick part 2
            for (int[] uuvvww : new int[][]{
            	// Temple
            	{4,5,8, 4,7,8}, {5,7,8, 6,7,8}, {7,5,8, 7,7,8}, 
            	{4,5,3, 4,7,3}, {5,7,3, 6,7,3}, {7,5,3, 7,7,3}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickBlock, biomeStoneBrickMeta, biomeStoneBrickBlock, biomeStoneBrickMeta, false);
            }
        	
            
    		// Planks
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Floor
    			{7,4,5, 7,4,7}, 
    			{5,4,5, 6,4,6}, 
    			{4,4,4, 6,4,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
    		}
    		
    		
    		// Cobblestone
    		for(int[] uuvvww : new int[][]{
    			// Furnace plugs
    			{7,4,4, 7,4,4}, {7,8,4, 7,8,4}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
    		}
			
			
			// Cobblestone wall
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
			for (int[] uuvvww : new int[][]{
				{7,9,4, 7,9,4}, 
				{7,5,4, 7,7,4}, 
				{7,2,4, 7,3,4}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, false);
			}
    		
        	
        	// Stone Brick wall
    		Object[] tryStoneBrickWall = ModObjects.chooseModStoneBrickWallBlock();
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(tryStoneBrickWall!=null?(Block)tryStoneBrickWall[0]:Blocks.cobblestone_wall, tryStoneBrickWall!=null?(Integer)tryStoneBrickWall[1]:0, this.materialType, this.biome, this.disallowModSubs);
        	Block biomeStoneBrickWallBlock = (Block)blockObject[0]; int biomeStoneBrickWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		// Outside decoration
            	{3,5,8, 3,5,8}, {8,5,8, 8,5,8}, 
            	{3,5,3, 3,5,3}, {8,5,3, 8,5,3}, 
            	{5,8,8, 6,8,8}, 
            	{3,8,5, 3,8,6}, {8,8,5, 8,8,6}, 
            	{5,8,3, 6,8,3}, 
            	// Windows
            	{3,6,5, 3,6,5}, {3,5,6, 3,5,6}, 
            	{6,5,8, 6,5,8}, {5,6,8, 5,6,8}, 
            	{8,6,6, 8,6,6}, {8,5,5, 8,5,5}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickWallBlock, biomeStoneBrickWallMeta, biomeStoneBrickWallBlock, biomeStoneBrickWallMeta, false);
            }
        	
            
            // Iron bars
            for(int[] uuvvww : new int[][]{
            	// Windows
            	{3,5,5, 3,5,5}, {3,6,6, 3,6,6}, 
            	{6,6,8, 6,6,8}, {5,5,8, 5,5,8}, 
            	{8,5,6, 8,5,6}, {8,6,5, 8,6,5}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.iron_bars, 0, Blocks.iron_bars, 0, false);
            }
    		
    		
    		// Stone Brick stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_brick_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Side steps
    			{5,3,1, 3}, {6,3,1, 3}, {5,4,2, 3}, {6,4,2, 3}, 
    			{1,3,5, 0}, {1,3,6, 0}, {2,4,5, 0}, {2,4,6, 0}, 
    			{10,3,5, 1}, {10,3,6, 1}, {9,4,5, 1}, {9,4,6, 1}, 
    			{5,3,10, 2}, {6,3,10, 2}, {5,4,9, 2}, {6,4,9, 2}, 
    			// Roof
    			{4,8,8, 2}, {7,8,8, 2}, 
    			{3,8,4, 0}, {3,8,7, 0}, 
    			{4,8,3, 3}, {7,8,3, 3}, 
    			{8,8,4, 1}, {8,8,7, 1}, 
    			// Downstairs
    			{6,4,7, 0}, {5,3,7, 0}, 
    			{4,2,6, 3}, {4,1,5, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeStoneBrickStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Stone Brick Slab (upper)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 13, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickSlabUpperBlock = (Block)blockObject[0]; int biomeStoneBrickSlabUpperMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{4,8,4, 6,8,4},
    			{4,8,5, 4,8,7},
    			{5,8,7, 7,8,7},
    			{7,8,5, 7,8,6},
    			{4,2,7, 4,2,7},
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickSlabUpperBlock, biomeStoneBrickSlabUpperMeta, biomeStoneBrickSlabUpperBlock, biomeStoneBrickSlabUpperMeta, false);	
    		}
    		
    		
    		// Stone Brick Slab (lower)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_slab, 5, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickSlabLowerBlock = (Block)blockObject[0]; int biomeStoneBrickSlabLowerMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{5,9,5, 6,9,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickSlabLowerBlock, biomeStoneBrickSlabLowerMeta, biomeStoneBrickSlabLowerBlock, biomeStoneBrickSlabLowerMeta, false);	
    		}
    		
    		
    		// Smithing table
    		blockObject = ModObjects.chooseModSmithingTable(biomePlankMeta); Block smithingTableBlock = (Block) blockObject[0]; int smithingTableMeta = (Integer) blockObject[1];
    		for (int[] uvw : new int[][]{
    			{7,1,7}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, smithingTableBlock, smithingTableMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Doors
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_door, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodDoorBlock = (Block)blockObject[0];
    		for (int[] uvwoor : new int[][]{ // u, v, w, orientation, isShut (1/0 for true/false), isRightHanded (1/0 for true/false)
    			// orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
    			{5,5,3, 0, 1, 0}, {6,5,3, 0, 1, 1}, 
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
            	{7,1,4, 0}, 
            	})
            {
                this.placeBlockAtCurrentPosition(world, Blocks.furnace, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2]), StructureVillageVN.chooseFurnaceMeta(uvwo[3], this.coordBaseMode), 2);
            }
        	
        	
            // Glazed terracotta
        	Object[] tryGlazedTerracotta;
        	for (int[] uvwoc : new int[][]{ // u,v,w, orientation, dye color
        		// Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
        		
        		{5,0,5, 2, GeneralConfig.useVillageColors ? this.townColor4 : 9}, // Cyan
        		{6,0,5, 1, GeneralConfig.useVillageColors ? this.townColor4 : 9}, // Cyan
        		{6,0,6, 0, GeneralConfig.useVillageColors ? this.townColor4 : 9}, // Cyan
        		{5,0,6, 3, GeneralConfig.useVillageColors ? this.townColor4 : 9}, // Cyan
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
    		
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			int s = random.nextInt(11);
    			
    			// Villager
    			int u = s<=0 ? 4 : s<=4 ? 5 : s<=8 ? 6 : 7;
    			int v = 1;
    			int w = s<=0 ? 4 : s<=4 ? s+3 : s<=8 ? s-1 : s-4;
    			
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
    // designed by Lonemind
    
    public static class JungleWeaponSmithy extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			" FFFFFFF",
    			" FFFFFFF",
    			" FFFPFFF",
    			"FFPFFFFF",
    			"FFFFPFFF",
    			"FPPPFPFF",
    			"FFFPFPFF",
    			"FFPFFFFF",
    			"FFFFFFFF",
    			" FFFFFFF",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 10;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 1;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleWeaponSmithy() {}

        public JungleWeaponSmithy(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleWeaponSmithy buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleWeaponSmithy(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
        	
            // Stone brick
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickBlock = (Block)blockObject[0]; int biomeStoneBrickMeta = (Integer)blockObject[1];
            for (int[] uuvvww : new int[][]{
            	// Frame
            	{1,1,9, 1,2,9}, {5,1,9, 5,2,9}, 
            	{1,1,5, 1,2,5}, {5,1,5, 5,2,5}, 
            	{1,1,1, 1,2,1}, {5,1,1, 5,2,1}, 
            	// Upstairs
            	{1,5,9, 1,6,9}, {5,5,9, 5,6,9}, 
            	{1,5,5, 1,6,5}, {5,5,5, 5,6,5}, 
            	// Level divider
            	{2,4,1, 4,4,1}, 
            	{1,4,2, 5,4,7}, 
            	{1,4,8, 1,4,8}, {3,4,8, 5,4,8}, 
            	{1,4,9, 5,4,9}, 
            	// Roof
            	{2,8,5, 4,8,5}, 
            	{1,8,6, 1,8,8}, 
            	{2,8,9, 4,8,9}, 
            	{5,8,6, 5,8,8}, 
            	{3,9,7, 3,9,7}, 
            	// Basins
            	{6,0,6, 6,0,8}, {6,1,7, 6,1,7}, 
            	{7,1,5, 7,1,5}, {7,1,9, 7,1,9}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickBlock, biomeStoneBrickMeta, biomeStoneBrickBlock, biomeStoneBrickMeta, false);
            }
    		
    		
    		// Stone Brick stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone_brick_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBrickStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Basin
    			{6,1,5, 3}, 
    			{5,1,6, 0}, {5,1,7, 0}, {5,1,8, 0}, 
    			{7,1,6, 1}, {7,1,7, 1}, {7,1,8, 1}, 
    			{6,1,9, 2}, 
    			// Roof
    			{2,9,8, 2}, {3,9,8, 2}, {4,9,8, 2}, 
    			{2,9,7, 0}, {4,9,7, 1}, 
    			{2,9,6, 3}, {3,9,6, 3}, {4,9,6, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeStoneBrickStairsBlock, this.getMetadataWithOffset(Blocks.stone_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
            
        	
            // Stone
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeStoneBlock = (Block)blockObject[0]; int biomeStoneMeta = (Integer)blockObject[1];
            for (int[] uuvvww : new int[][]{
            	// Building top
            	{2,5,5, 4,5,5}, {2,6,5, 2,6,5}, {4,6,5, 4,6,5}, {2,7,5, 4,7,5}, // Front window
            	{1,5,6, 1,5,8}, {1,6,6, 1,6,6}, {1,6,8, 1,6,8}, {1,7,6, 1,7,8}, // Left window
            	{2,5,9, 4,5,9}, {2,6,9, 2,6,9}, {4,6,9, 4,6,9}, {2,7,9, 4,7,9}, // Back window
            	{5,5,6, 5,5,8}, {5,6,6, 5,6,6}, {5,6,8, 5,6,8}, {5,7,6, 5,7,8}, // Left window
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBlock, biomeStoneMeta, biomeStoneBlock, biomeStoneMeta, false);
            }
            
        	
            // Chiseled stone brick
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.stonebrick, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeChiseledStoneBrickBlock = (Block)blockObject[0]; int biomeChiseledStoneBrickMeta = (Integer)blockObject[1];
            for (int[] uvwo : new int[][]{
            	// Front entrance
            	{1,3,9}, {5,3,9}, 
            	{1,3,5}, {5,3,5}, 
            	{1,3,1}, {5,3,1}, 
            	// Building top
            	{1,7,9}, {5,7,9}, 
            	{1,7,5}, {5,7,5}, 
        		})
            {
    			this.placeBlockAtCurrentPosition(world, biomeChiseledStoneBrickBlock, biomeChiseledStoneBrickMeta, uvwo[0], uvwo[1], uvwo[2], structureBB);
            }
    		
    		
    		// Logs (Vertical)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			{1,1,6, 1,3,8}, 
    			{2,1,9, 4,3,9}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeLogVertBlock, biomeLogVertMeta, biomeLogVertBlock, biomeLogVertMeta, false);	
    		}
    		
    		
    		// Cobblestone
    		for(int[] uvwo : new int[][]{
    			// Counter
    			{3,0,8}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeCobblestoneBlock, biomeCobblestoneMeta, uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Outside table
    			{7,1,1, 2+4}, {7,1,4, 3+4}, 
    			// Inside table
    			{4,5,8, 1+4}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
    		
    		
    		// Wooden slabs (Top)
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
    		for(int[] uuvvww : new int[][]{
    			// Table
    			{6,1,1, 6,1,1}, {7,1,2, 7,1,3}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
    		}
            
            
            // Grindstone
        	for (int[] uvwoh : new int[][]{ // u,v,w, orientation, isHanging
        		// Orientation: 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        		{3,1,8, 2, 0}, 
        		})
            {
        		// Generate the blockObject here so that we have the correct meta on hand
        		blockObject = ModObjects.chooseModGrindstone(uvwoh[3], this.coordBaseMode, uvwoh[4]==1); Block biomeGrindstoneBlock = (Block)blockObject[0]; int biomeGrindstoneMeta = (Integer)blockObject[1];
            	
        		this.placeBlockAtCurrentPosition(world, biomeGrindstoneBlock, biomeGrindstoneMeta, uvwoh[0], uvwoh[1], uvwoh[2], structureBB);
            }
        	
        	
        	// Furnaces
            for (int[] uvwo : new int[][]{ // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
            	{0,1,2, 1}, 
            	{0,1,3, 1}, 
            	{0,1,4, 1}, 
            	{0,2,3, 1}, 
            	})
            {
                this.placeBlockAtCurrentPosition(world, Blocks.furnace, 0, uvwo[0], uvwo[1], uvwo[2], structureBB);
                world.setBlockMetadataWithNotify(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2]), StructureVillageVN.chooseFurnaceMeta(uvwo[3], this.coordBaseMode), 2);
            }
    		
    		
    		// Water
    		for(int[] uvw : new int[][]{
    			// Floor
    			{6,1,6},
    			})
    		{
                this.placeBlockAtCurrentPosition(world, Blocks.flowing_water, 0, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Lava
    		for(int[] uvw : new int[][]{
    			// Floor
    			{6,1,8},
    			})
    		{
                this.placeBlockAtCurrentPosition(world, Blocks.lava, 0, uvw[0], uvw[1], uvw[2], structureBB);
    		}
            
            
            // Ladder
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.ladder, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLadderBlock = (Block)blockObject[0];
        	for (int[] uuvvwwo : new int[][]{ // Orientation - 3:leftward, 1:rightward, 2:backward, 0:forward
        		{2,1,8, 2,4,8, 2}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], uuvvwwo[3], uuvvwwo[4], uuvvwwo[5], biomeLadderBlock, StructureVillageVN.chooseLadderMeta(uuvvwwo[6], this.coordBaseMode), biomeLadderBlock, StructureVillageVN.chooseLadderMeta(uuvvwwo[6], this.coordBaseMode), false);
            }
            
            
        	// Carpet
        	for(int[] uvwm : new int[][]{
        		// Lower
        		{3,5,6, (GeneralConfig.useVillageColors ? this.townColor3 : 14)}, // Red
        		{4,5,6, (GeneralConfig.useVillageColors ? this.townColor : 0)}, // White
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.carpet, uvwm[3], uvwm[0], uvwm[1], uvwm[2], structureBB); 
            }
    		
        	
        	// Stone Brick wall
    		Object[] tryStoneBrickWall = ModObjects.chooseModStoneBrickWallBlock();
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(tryStoneBrickWall!=null?(Block)tryStoneBrickWall[0]:Blocks.cobblestone_wall, tryStoneBrickWall!=null?(Integer)tryStoneBrickWall[1]:0, this.materialType, this.biome, this.disallowModSubs);
        	Block biomeStoneBrickWallBlock = (Block)blockObject[0]; int biomeStoneBrickWallMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
            	{1,3,0, 1,3,0}, {5,3,0, 5,3,0}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeStoneBrickWallBlock, biomeStoneBrickWallMeta, biomeStoneBrickWallBlock, biomeStoneBrickWallMeta, false);
            }
        	
        	
    		// Polished Andesite Slab (Lower)
    		blockObject = ModObjects.chooseModPolishedAndesiteSlabObject(false);
    		if (blockObject==null) // Use basic polished stone slab
    		{
    			blockObject = new Object[]{Blocks.stone_slab, 0};
    		};
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject((Block)blockObject[0], (Integer)blockObject[1], this.materialType, this.biome, this.disallowModSubs); Block biomePolishedAndesiteSlabBottomBlock = (Block)blockObject[0]; int biomePolishedAndesiteSlabBottomMeta = (Integer)blockObject[1];
    		for (int[] uvw : new int[][]{
    			{7,2,5}, {7,2,9}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomePolishedAndesiteSlabBottomBlock, biomePolishedAndesiteSlabBottomMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Coarse Dirt
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 1, this.materialType, this.biome, this.disallowModSubs); Block biomeCoarseDirtBlock = (Block)blockObject[0]; int biomeCoarseDirtMeta = (Integer)blockObject[1];
    		for(int[] uvw : new int[][]{
    			{2,0,8}, {4,0,8}, 
    			{2,0,7}, {3,0,7}, 
    			{3,0,6}, {4,0,6}, 
    			{2,0,5}, {3,0,5}, 
    			{4,0,4}, 
    			{2,0,3}, 
    			{4,0,2}, 
    			{3,0,1}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeCoarseDirtBlock, biomeCoarseDirtMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
            
            
            // Glass Panes
        	for (int[] uvw : new int[][]{
        		{1,6,7}, 
        		{3,6,5}, 
        		{5,6,7}, 
        		{3,6,9}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
            
            
            // Hanging Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(true); Block biomeHangingLanternBlock = (Block)blockObject[0]; int biomeHangingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{
            	{1,2,0}, 
            	{5,2,0}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeHangingLanternBlock, biomeHangingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
        	
        	// Sitting Lanterns
        	blockObject = ModObjects.chooseModLanternBlock(false); Block biomeSittingLanternBlock = (Block)blockObject[0]; int biomeSittingLanternMeta = (Integer)blockObject[1];
        	for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
            	//{7,2,1}, 
            	{4,6,8}, 
            	}) {
            	this.placeBlockAtCurrentPosition(world, biomeSittingLanternBlock, biomeSittingLanternMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Villagers
    		if (!this.entitiesGenerated)
    		{
    			this.entitiesGenerated=true;
    			
    			int s = random.nextInt(3);
    			
    			// Villager
    			int u = 2 + random.nextInt(3);
    			int v = 1;
    			int w = 1 + random.nextInt(7);
    			    			
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
    // designed by Lonemind
    
    public static class JungleWildFarm extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"   FFF       ",
    			"  FFFFFF     ",
    			" FFFFFFFFF   ",
    			" FFFFFFFFF   ",
    			" FFFFFFFFFFF ",
    			" FFFFFFFFFFFF",
    			" FFFFFFFFFFFF",
    			" FFFFFFFFFFFF",
    			"FFFFFFFFFFFF ",
    			"FFFFFFFFFFF  ",
    			"FFFFFFFFFFF  ",
    			"      FFF    ",
    			"       FF    ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 9;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1 + 4 + 8; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleWildFarm() {}

        public JungleWildFarm(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleWildFarm buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleWildFarm(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
        	
        	// Vertical logs
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, materialType, biome, disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		{1,1,2, 1,2,2}, 
        		{1,1,3, 1,2,3}, 
        		{1,1,4, 1,3,4}, 
        		{2,1,5, 2,2,5}, 
        		{2,1,6, 2,1,6}, 
        		{2,1,7, 2,3,7}, 
        		{3,1,8, 3,1,8}, 
        		{2,1,9, 2,2,9}, 
        		{2,1,10, 2,1,10}, 
        		{3,1,11, 3,3,11}, 
        		{4,1,11, 4,1,11}, 
        		{7,1,10, 7,3,10}, 
        		{8,1,9, 8,2,9}, 
        		{9,1,9, 9,2,9}, 
        		{10,1,8, 10,1,8}, 
        		{11,1,7, 11,2,7}, 
        		{11,1,6, 11,1,6}, 
        		{11,1,5, 11,3,5}, 
        		{9,1,2, 9,3,2}, 
        		{8,1,1, 8,2,1}, 
        		{7,1,1, 7,3,1}, 
        		{6,1,1, 6,1,1}, 
        		{5,1,2, 5,2,2}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeLogVertBlock, biomeLogVertMeta,
        				biomeLogVertBlock, biomeLogVertMeta, 
        				false);
            }
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uuvvww : new int[][]{
        		{1,3,2, 1,3,2}, 
        		{1,3,3, 1,3,3}, 
        		{1,4,4, 1,4,4}, 
        		{2,3,5, 2,3,5}, 
        		{2,2,6, 2,2,6}, 
        		{2,4,7, 2,4,7}, 
        		{3,2,8, 3,2,8}, 
        		{2,3,9, 2,3,9}, 
        		{2,2,10, 2,2,10}, 
        		{3,4,11, 3,4,11}, 
        		{4,2,11, 4,2,11}, 
        		{7,4,10, 7,4,10}, 
        		{8,3,9, 8,3,9}, 
        		{10,2,8, 10,2,8}, 
        		{11,3,7, 11,3,7}, 
        		{11,2,6, 11,2,6}, 
        		{11,4,5, 11,4,5}, 
        		{9,4,2, 9,4,2}, 
        		{8,3,1, 8,3,1}, 
        		{7,4,1, 7,4,1}, 
        		{6,2,1, 6,2,1}, 
        		{5,3,2, 5,3,2}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
        	
        	// Grass
        	for (int[] uuvvww : new int[][]{
        		{8,1,8, 8,1,8}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeGrassBlock, biomeGrassMeta,
        				biomeGrassBlock, biomeGrassMeta, 
        				false);
            }
        	
        	// Dirt
        	for (int[] uuvvww : new int[][]{
        		{6,0,2, 8,0,4}, {7,0,5, 7,0,5}, 
        		{8,0,8, 9,0,8}, {10,0,7, 10,0,7}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeDirtBlock, biomeDirtMeta,
        				biomeDirtBlock, biomeDirtMeta, 
        				false);
            }
            
        	// Coarse Dirt
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 1, this.materialType, this.biome, this.disallowModSubs); Block biomeCoarseDirtBlock = (Block)blockObject[0]; int biomeCoarseDirtMeta = (Integer)blockObject[1];
    		for (int[] uuvvww : new int[][]{
        		{6,1,2, 6,1,4}, 
        		{8,1,3, 8,1,4}, {9,1,3, 9,1,3}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeCoarseDirtBlock, biomeCoarseDirtMeta,
        				biomeCoarseDirtBlock, biomeCoarseDirtMeta, 
        				false);
            }
            
            // Melon 
            for(int[] uvw : new int[][]{
            	{2,1,2}, {2,1,3}, 
            	{7,1,9}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, Blocks.melon_block, 0, uvw[0], uvw[1], uvw[2], structureBB); // Random pumpkin orientation
            }
            
            // Moist Farmland with crop above
            for(int[] uvwfcp : new int[][]{
            	// u,v,w, farmland moisture (0:dry, 7:moist), crop (0:wheat, 1:potato, 2:carrot, 3:melon), crop progress
            	// Front left
            	{3,0,2, 7,3,7}, 
            	{3,0,3, 7,3,7}, 
            	{4,0,3, 7,0,4}, 
            	{4,0,4, 7,0,0}, 
            	{4,0,5, 7,0,7},
            	{5,0,4, 7,0,7},
            	// Back
            	{7,0,8, 7,3,7}, 
            	{7,0,7, 7,0,0}, 
            	{8,0,7, 7,0,5}, 
            	{9,1,8, 7,1,0}, 
            	{10,1,7, 7,1,0},
            	// Front right
            	{10,0,2, 7,2,4}, 
            	{10,0,3, 7,2,7}, 
            	{11,0,4, 7,2,0}, 
            	})
            {
            	this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, uvwfcp[0], uvwfcp[1]-1, uvwfcp[2], structureBB);
            	//this.clearCurrentPositionBlocksUpwards(world, uvwpmc[0], uvwpmc[1]+1, uvwpmc[2], structureBB);
            	this.placeBlockAtCurrentPosition(world, uvwfcp[4]==0?Blocks.wheat:uvwfcp[4]==1?Blocks.potatoes:uvwfcp[4]==2?Blocks.carrots:Blocks.melon_stem, uvwfcp[5], uvwfcp[0], uvwfcp[1]+1, uvwfcp[2], structureBB); 
            	this.placeBlockAtCurrentPosition(world, Blocks.farmland, uvwfcp[3], uvwfcp[0], uvwfcp[1], uvwfcp[2], structureBB); // 7 is moist
            }
    		
            
    		// Water
    		for(int[] uuvvww : new int[][]{
    			// Hidden under fence posts
    			{3,0,8, 3,0,8}, 
    			{4,0,11, 4,0,11},
    			// Fountain source
    			{8,1,2, 8,1,2}, 
    			// Fountain pool
    			{6,0,5, 8,0,6}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);	
    		}
    		
    		
    		// Attempt to add GardenCore Compost Bins. If this fails, do nothing
            Block compostBin = ModObjects.chooseModComposterBlock();
            if (compostBin != null)
            {
            	for(int[] uvw : new int[][]{
        			{6,1,6}, 
        			})
        		{
        			this.placeBlockAtCurrentPosition(world, compostBin, 0, uvw[0], uvw[1], uvw[2], structureBB);	
        			this.placeBlockAtCurrentPosition(world, biomeDirtBlock, biomeDirtMeta, uvw[0], uvw[1]-1, uvw[2], structureBB);	
        		}
            }
            
            
            // Sugarcane
    		for(int[] uuvvww : new int[][]{
    			// Back
    			{3,1,7, 3,1,7}, 
    			{3,1,9, 3,3,9}, 
    			{4,1,10, 4,1,10}, 
    			{5,1,11, 5,2,11}, 
    			// Front
    			{5,1,5, 5,1,5}, 
    			// On the coarse dirt
    			{6,2,2, 6,3,2}, 
    			{6,2,3, 6,2,3}, 
    			{8,2,3, 8,3,3}, 
    			{8,2,4, 8,2,4}, 
    			// Right
    			{9,1,5, 9,1,5}, 
    			})
    		{
    			this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.reeds, 0, Blocks.reeds, 0, false);	
    		}
            
    		// Bamboo
    		blockObject = ModObjects.chooseModBambooStalk(0);
			if (blockObject!=null)
    		{
				for (int[] uuvvwws : new int[][]{
					{3,1,10, 3,5,10, 3}, {3,6,10, 3,6,10, 4}, {3,7,10, 3,8,10, 5}, 
        			{4,1,8, 4,1,8, 3}, {4,2,8, 4,3,8, 4}, {4,4,8, 4,4,8, 5}, 
        			{5,1,9, 5,1,9, 0}, {5,2,9, 5,2,9, 1},  
            		})
                {
					blockObject = ModObjects.chooseModBambooStalk(uuvvwws[6]);
					this.fillWithMetadataBlocks(world, structureBB, uuvvwws[0], uuvvwws[1], uuvvwws[2], uuvvwws[3], uuvvwws[4], uuvvwws[5],
							(Block)blockObject[0], (Integer)blockObject[1], (Block)blockObject[0], (Integer)blockObject[1], false);	
                }
				
				// Leaf toppers
				blockObject = ModObjects.chooseModBambooLeaves();
				if (blockObject!=null)
	    		{
					Block bambooLeavesBlock = (Block)blockObject[0]; int bambooLeavesMeta = (Integer)blockObject[1];
					
					for (int[] uuvvww : new int[][]{
						{3,9,10}, 
	        			{4,5,8}, 
	            		})
	                {
						this.placeBlockAtCurrentPosition(world, bambooLeavesBlock, bambooLeavesMeta, uuvvww[0], uuvvww[1], uuvvww[2], structureBB);	
	                }
	    		}
    		}
			else // As sugarcane
			{
				Block bambooBlock = Blocks.reeds; int bambooMeta = 0;

				// Add water to support the sugarcane
				for(int[] uvw : new int[][]{
	    			{3,0,11}, 
	    			})
	    		{
	    			this.placeBlockAtCurrentPosition(world, Blocks.flowing_water, 0, uvw[0], uvw[1], uvw[2], structureBB);
	    		}
				
				for (int[] uuvvww : new int[][]{
					{3,1,10, 3,4,10}, 
					{4,1,8, 4,2,8}, 
            		})
                {
					this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], bambooBlock, bambooMeta, bambooBlock, bambooMeta, false);	
                }
			}
    		
    		
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward, 3:leftward
            		// Leftward
        			{0,1,2, 3}, {0,2,2, 3}, 
        			{0,1,4, 3}, {0,2,4, 3}, {0,3,4, 3}, 
        			{1,1,5, 3}, 
        			{1,1,7, 3}, {1,2,7, 3}, {1,3,7, 3}, 
        			{1,1,9, 3}, {1,2,9, 3}, 
        			{2,2,11, 3}, {2,3,11, 3}, 
        			{6,1,10, 3}, {6,2,10, 3}, {6,3,10, 3}, 
        			// Backward
        			{7,1,0, 2}, {7,2,0, 2}, {7,3,0, 2}, 
        			{8,1,0, 2}, {8,2,0, 2}, 
        			{9,1,1, 2}, {9,2,1, 2}, 
        			{11,2,4, 2}, {11,3,4, 2}, 
        			// Forward
        			{3,1,12, 0}, {3,2,12, 0}, 
        			{7,1,11, 0}, {7,2,11, 0}, 
        			{8,2,10, 0}, 
        			{9,1,10, 0}, {9,2,10, 0}, 
        			// Rightward
        			{10,2,2, 1}, {10,3,2, 1}, 
        			{11,1,5, 1}, {11,2,5, 1}, 
        			{11,1,7, 1}, {11,2,7, 1}, 
        			{10,1,8, 1}, 
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
    			
    			int s=random.nextInt(11);
				
    			int u = s<3 ? 3 : s<5 ? 4 : s<8 ? 5 : 6;
				int v=1;
    			int w = s<3 ? s+4 : s<5 ? s+3 : s<8 ? s+1 : s-1;
				
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
    
    
    // --- Wood Animal Pen --- //
    // designed by AstroTibs
    
    public static class JungleWoodAnimalPen extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
    			"     FFF   ",
    			"   FFFFFF  ",
    			"  FFFFFFFF ",
    			" FFFFFFFFF ",
    			" FFFFFFFFF ",
    			"FFFFFFFFFFF",
    			"FFFFFFFFFFF",
    			"FFFFFFFFFFF",
    			"FFFFFFFFFF ",
    			"   FFFFF   ",
    			"    FFF    ",
    			"     F     ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 5;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	public static final byte MEDIAN_BORDERS = 1; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 3;
    	private static final int DECREASE_MAX_U = 3;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleWoodAnimalPen() {}

        public JungleWoodAnimalPen(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
    		super();
    		this.coordBaseMode = coordBaseMode;
    		this.boundingBox = boundingBox;
    		// Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleWoodAnimalPen buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleWoodAnimalPen(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
			
			// Vertical logs
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, materialType, biome, disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
			for (int[] uuvvww : new int[][]{
				{0,0,3, 0,1,3}, 
				{1,0,3, 1,0,3}, 
				{2,0,3, 2,1,3}, 
				{3,0,2, 3,1,2}, 
				{4,0,1, 6,1,1}, 
				{7,0,2, 7,1,2}, 
				{8,0,3, 8,0,3}, 
				{9,0,3, 9,1,3}, 
				{10,0,4, 10,1,4}, 
				{10,0,5, 10,0,5}, 
				{10,0,6, 10,1,6}, 
				{9,0,7, 9,1,7}, 
				{9,0,8, 9,0,8}, 
				{9,0,9, 9,1,9}, 
				{8,0,10, 8,1,10}, 
				{7,0,11, 7,1,11}, 
				{6,0,11, 6,0,11}, 
				{5,0,11, 5,1,11}, 
				{4,0,10, 4,1,10}, 
				{3,0,10, 3,0,10}, 
				{2,0,9, 2,1,9}, 
				{1,0,8, 1,1,8},  
				{1,0,7, 1,0,7}, 
				{0,0,6, 0,1,6}, 
				{0,0,4, 0,0,5}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
						biomeLogVertBlock, biomeLogVertMeta,
						biomeLogVertBlock, biomeLogVertMeta, 
						false);
			}
    		
    		
    		// Wood stairs
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
    		for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
    			// Roof
    			{4,3,1, 0}, {6,3,1, 1}, //{3,4,1, 0}, {7,4,1, 1}, 
    			{5,0,0, 3}, 
    			})
    		{
    			this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
    		}
			
            
			// Fence
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
			for (int[] uuvvww : new int[][]{
				{0,2,3, 0,2,7}, 
				{0,1,4, 0,1,5}, 
				{1,1,3, 1,1,3}, 
				{1,1,7, 1,1,7}, 
				{1,2,7, 1,2,9}, 
				{2,2,9, 2,2,10}, 
				{3,2,10, 4,2,10}, 
				{3,1,10, 3,1,10}, 
				{4,2,11, 8,2,11}, 
				{6,1,11, 6,1,11}, 
				{8,2,10, 9,2,10}, 
				{9,2,7, 9,2,9}, 
				{9,1,8, 9,1,8}, 
				{10,2,3, 10,2,7}, 
				{10,1,5, 10,1,5}, 
				{8,2,3, 9,2,3}, 
				{8,1,3, 8,1,3}, 
				{7,2,2, 8,2,2}, 
				{6,1,1, 6,1,1}, 
				{4,1,1, 4,1,1}, 
				{3,2,1, 4,2,1}, 
				{6,2,1, 7,2,1}, 
				{2,2,2, 3,2,2},
				{1,2,3, 2,2,3}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
			}
			
			
            // Wooden slabs (Top)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	// "Walls"
            	{5,3,1, 5,3,1}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
            }
            
            
        	// Fence Gate
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence_gate, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeFenceGateBlock = (Block)blockObject[0]; int biomeFenceGateMeta = (Integer)blockObject[1];
        	for(int[] uvw : new int[][]{
            	{5,1,1}, 
            	})
            {
        		this.placeBlockAtCurrentPosition(world, biomeFenceGateBlock, StructureVillageVN.getMetadataWithOffset(biomeFenceGateBlock, biomeFenceGateMeta, this.coordBaseMode), uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
    		
    		// Torches
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			{2,3,3, -1}, {1,3,7, -1}, {4,3,10, -1}, {8,3,10, -1}, {9,3,7, -1}, {8,3,3, -1}, 
    			// Over entrance
    			{5,4,1, -1}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
    		
        	
        	// Grass blocks with grass patches atop
        	for (int[] uuvvww : new int[][]{
				{1,0,6, 1,0,6}, 
				{2,0,6, 2,0,8}, 
				{3,0,3, 7,0,9}, 
				{4,0,2, 6,0,2}, 
				{5,0,10, 7,0,10}, 
				{8,0,4, 8,0,9}, 
				{9,0,4, 9,0,6}, 
        	})
        	{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeTopBlock, biomeTopMeta, biomeTopBlock, biomeTopMeta, false);	
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1]+1, uuvvww[2], uuvvww[3], uuvvww[4]+1, uuvvww[5], Blocks.tallgrass, 1, Blocks.tallgrass, 1, false);	
        	}
			
			
			// Water
			for(int[] uuvvww : new int[][]{
				{1,0,4, 2,0,5}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);	
			}
			
			
        	// Set unkempt grass
            for (int[] uvwg : new int[][]{ // g is grass type
            	{6,1,10, 1}, {9,1,6, 1},
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
            
            
            // Animals
            if (!this.entitiesGenerated)
            {
            	this.entitiesGenerated=true;
            	
            	if (VillageGeneratorConfigHandler.villageAnimalRestrictionLevel<1)
            	{
	            	// Animals
	            	for (int[] uvw : new int[][]{
	        			{3,1,6}, {7,1,4}, {7,1,8}, 
	        			})
	        		{
	                	EntityLiving animal = StructureVillageVN.getVillageAnimal(world, random, false, this.materialType==MaterialType.MUSHROOM); // Because horses can escape the pen
	                    animal.setLocationAndAngles((double)this.getXWithOffset(uvw[0], uvw[2]) + 0.5D, (double)this.getYWithOffset(uvw[1]) + 0.5D, (double)this.getZWithOffset(uvw[0], uvw[2]) + 0.5D, random.nextFloat()*360F, 0.0F);
	                    world.spawnEntityInWorld(animal);
	                    
	                    // Dirt block underneath
	                    //this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, uvw[0], uvw[1]-1, uvw[2], structureBB);
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
    
    
    
    // ------------------ //
    // --- Road Decor --- //
    // ------------------ //

    
    // --- Road Decor --- //
    
    public static class JungleStreetDecor extends StructureVillageVN.VNComponent
    {
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = 3;
    	public static final int STRUCTURE_DEPTH = 3;
    	public static final int STRUCTURE_HEIGHT = 4;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 0; // Spaces above the bottom of the structure considered to be "ground level"
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleStreetDecor() {}

        public JungleStreetDecor(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super();
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            // Additional stuff to be used in the construction
            this.ascertainVillageStatsFromStartPiece(start);
        }

        public static JungleStreetDecor buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, STRUCTURE_WIDTH, STRUCTURE_HEIGHT, STRUCTURE_DEPTH, coordBaseMode);
            
            return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new JungleStreetDecor(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
            Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
        	// Establish top and filler blocks, substituting Grass and Dirt if they're null
        	Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
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
            			new    int[]{-2,-1,0,1,2,3}, // Values
            			new double[]{ 1, 2,3,8,4,2}, // Weights
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
            	
            	this.func_151554_b(world, biomeFillerBlock, biomeFillerMeta, uvw[0], decorHeightY-2, uvw[2], structureBB);
            	this.placeBlockAtCurrentPosition(world, biomeTopBlock, biomeTopMeta, uvw[0], decorHeightY-1, uvw[2], structureBB);
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
    
    
    // --- Road Well --- //
    // designed by AstroTibs
	
	public static class JungleRoadAccent1 extends StructureVillageVN.VNComponent
	{
		// Make foundation with blanks as empty air and F as foundation spaces
		private static final String[] foundationPattern = new String[]{
    			"PPPPP",
    			"PFFFP",
    			"PFFFP",
    			"PFFFP",
    			"PPPPP",
		};
		// Here are values to assign to the bounding box
		public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
		public static final int STRUCTURE_DEPTH = foundationPattern.length;
		public static final int STRUCTURE_HEIGHT = 5;
		// Values for lining things up
		private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
		private static final int W_OFFSET = -4; // How much to shift the well to ensure it is positioned onto the road
		public static final byte MEDIAN_BORDERS = 2 + 8; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
		private static final int INCREASE_MIN_U = 0;
		private static final int DECREASE_MAX_U = 0;
		private static final int INCREASE_MIN_W = 0;
		private static final int DECREASE_MAX_W = 0;
		
		public JungleRoadAccent1() {}
	
		public JungleRoadAccent1(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
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
            this.ascertainVillageStatsFromStartPiece(start);
		}
		
		public static JungleRoadAccent1 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
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
					? new JungleRoadAccent1(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
			Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
			// Establish top and filler blocks, substituting Grass and Dirt if they're null
			Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
			Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
			
			// Decor - placed outside Bounding Box for simplicity's sake
			int[][] decorUVW = new int[][]{
				{-1,1,5}, 
				// Within the front face
				{5,1,-1}, 
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
			
			
			// Cobblestone
			for(int[] uuvvww : new int[][]{
				{1,0,3, 3,0,3}, 
				{1,0,2, 1,0,2}, {3,0,2, 3,0,2}, 
				{1,0,1, 3,0,1}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneBlock, biomeCobblestoneMeta, biomeCobblestoneBlock, biomeCobblestoneMeta, false);	
			}
			
			
			// Vertical logs
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, materialType, biome, disallowModSubs); Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
			for (int[] uuvvww : new int[][]{
				{2,1,1, 2,1,1}, 
				{2,1,3, 2,1,3}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
						biomeLogVertBlock, biomeLogVertMeta,
						biomeLogVertBlock, biomeLogVertMeta, 
						false);
			}
			
			
			// Cobblestone wall
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
			for (int[] uuvvww : new int[][]{
				{1,1,1, 1,1,3}, {3,1,1, 3,1,3}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta, false);
			}
			
			
			// Fence
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
			for (int[] uuvvww : new int[][]{
				{2,2,1, 2,3,1}, 
				{2,2,3, 2,3,3},  
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
			}
			
			
			// Wood stairs
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
			for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
				{1,3,3, 0}, {3,3,3, 1}, 
				{1,3,2, 0}, {3,3,2, 1}, 
				{1,3,1, 0}, {3,3,1, 1}, 
				})
			{
				this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
			}
			
			
			// Wooden slabs (Bottom)
			blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
			for(int[] uuvvww : new int[][]{
				{2,4,1, 2,4,3}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
			}
			
			
			// Water
			for(int[] uuvvww : new int[][]{
				{2,0,2, 2,0,2}, 
				})
			{
				this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], Blocks.flowing_water, 0, Blocks.flowing_water, 0, false);	
			}
			
			
			// Villagers - treated here like a town center
			if (!this.entitiesGenerated)
			{
				this.entitiesGenerated=true;
				
				if (VillageGeneratorConfigHandler.spawnVillagersInTownCenters)
				{
					for (int[] ia : new int[][]{
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
		
		/**
		 * Returns the villager type to spawn in this component, based on the number
		 * of villagers already spawned.
		 */
		@Override
		protected int getVillagerType (int number) {return 0;}
	}
    
    
    // --- Treehouse --- //
    // designed by AstroTibs and js2a98aj
    
    public static class JungleRoadAccent2 extends StructureVillageVN.VNComponent
    {
    	// Make foundation with blanks as empty air and F as foundation spaces
    	private static final String[] foundationPattern = new String[]{
				"       ",
				"       ",
				"PPPPPPP",
				"PPPPPPP",
				"PPPPPPP",
				"  F    ",
				" FF    ",
    	};
    	// Here are values to assign to the bounding box
    	public static final int STRUCTURE_WIDTH = foundationPattern[0].length();
    	public static final int STRUCTURE_DEPTH = foundationPattern.length;
    	public static final int STRUCTURE_HEIGHT = 13;
    	// Values for lining things up
    	private static final int GROUND_LEVEL = 1; // Spaces above the bottom of the structure considered to be "ground level"
    	private static final int W_OFFSET = -5; // How much to shift the well to ensure it is positioned onto the road
    	public static final byte MEDIAN_BORDERS = 2 + 8; // Sides of the bounding box to count toward ground level median. +1: front; +2: left; +4: back; +8: right;
    	private static final int INCREASE_MIN_U = 0;
    	private static final int DECREASE_MAX_U = 0;
    	private static final int INCREASE_MIN_W = 0;
    	private static final int DECREASE_MAX_W = 0;
    	
        public JungleRoadAccent2() {}

        public JungleRoadAccent2(StartVN start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
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
            this.ascertainVillageStatsFromStartPiece(start);
        }
        
        public static JungleRoadAccent2 buildComponent(StartVN villagePiece, List pieces, Random random, int x, int y, int z, int coordBaseMode, int componentType)
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
            		? new JungleRoadAccent2(villagePiece, componentType, random, structureboundingbox, coordBaseMode) : null;
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
        	this.populateVillageFields(world);
        	
    		Object[] blockObject;
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    		// Establish top and filler blocks, substituting Grass and Dirt if they're null
    		Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (this.biome!=null && this.biome.topBlock!=null) {biomeTopBlock=this.biome.topBlock; biomeTopMeta=0;}
    		Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (this.biome!=null && this.biome.fillerBlock!=null) {biomeFillerBlock=this.biome.fillerBlock; biomeFillerMeta=0;}
        	
        	// Clear space above
        	this.clearSpaceAbove(world, structureBB, STRUCTURE_WIDTH, STRUCTURE_DEPTH, GROUND_LEVEL);
            
            // Follow the blueprint to set up the starting foundation
            this.establishFoundation(world, structureBB, foundationPattern, GROUND_LEVEL, this.materialType, this.disallowModSubs, this.biome,
       				FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeTopBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeTopMeta,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneBlock : biomeFillerBlock,
					FunctionsVN.shouldUseCobblestoneFoundation(this.biome) ? biomeCobblestoneMeta : biomeFillerMeta);
            
        	
        	// Fence
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.fence, 0, materialType, biome, disallowModSubs); Block biomeFenceBlock = (Block)blockObject[0];
        	for (int[] uuvvww : new int[][]{
        		// Roof support
        		{1,6,5, 1,6,5}, 
        		{1,6,0, 1,6,0}, {5,6,0, 5,6,0}, 
        		// Underneath logs
        		{1,3,5, 1,3,5}, {5,3,0, 5,3,0}, 
        		// Torch posts
        		{0,5,1, 0,5,1}, {6,5,5, 6,5,5}, {5,5,6, 5,5,6}, 
        		})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeFenceBlock, 0, biomeFenceBlock, 0, false);
            }
    		
    		
    		// Torches on posts
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			{0,6,1, -1}, {6,6,5, -1}, {5,6,6, -1}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
            
        	
        	// Vertical logs - can not be sandstone
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, 0, materialType, biome, disallowModSubs);
        	Block biomeLogVertBlock = (Block)blockObject[0]; int biomeLogVertMeta = (Integer)blockObject[1];
        	// Make sure this is a log (or mushroom stem)
        	biomeLogVertMeta = biomeLogVertBlock==Blocks.sandstone ? 3 : biomeLogVertMeta; // Force to be jungle log in a desert
        	biomeLogVertBlock = biomeLogVertBlock==Blocks.sandstone ? Blocks.log : biomeLogVertBlock; // Force to be jungle log in a desert
        	for (int[] uuvvww : new int[][]{
        		{1,4,5, 1,5,5}, {5,1,5, 5,11,5}, 
        		{1,1,1, 1,8,1}, {5,4,0, 5,5,0}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeLogVertBlock, biomeLogVertMeta,
        				biomeLogVertBlock, biomeLogVertMeta, 
        				false);
            }
    		// Foot foundation
    		for(int[] uvw : new int[][]{
    			// Feet
    			{5,0,5}, 
    			{1,0,1}, 
    			})
    		{
    			this.func_151554_b(world, biomeLogVertBlock, biomeLogVertMeta, uvw[0], uvw[1], uvw[2], structureBB);
    		}
    		
    		
    		// Torches on logs
    		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
    			{5,2,4, 2}, 
    			}) {
    			this.placeBlockAtCurrentPosition(world, Blocks.torch, StructureVillageVN.getTorchRotationMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
    		}
            
            
            // Leaves
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.leaves, 3, this.materialType, this.biome, this.disallowModSubs); Block biomeLeafBlock = (Block)blockObject[0]; int biomeLeafMeta = (Integer)blockObject[1];
        	for (int[] uuvvww : new int[][]{
        		// Front tree
        		{0,8,-1, 2,9,3}, {-1,8,0, -1,9,2}, {3,8,0, 3,9,2}, 
        		{1,10,0, 1,11,2}, {0,10,1, 0,11,1}, {2,10,1, 2,11,1}, {2,10,2, 2,10,2}, 
        		// Back tree
        		{4,9,3, 6,10,7}, {3,9,4, 3,10,6}, {7,9,3, 7,10,6}, 
        		{5,11,4, 5,12,6}, {4,11,5, 4,12,5}, {6,11,5, 6,12,5}, {6,11,4, 6,11,4}, 
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], 
        				biomeLeafBlock, biomeLeafMeta,
        				biomeLeafBlock, biomeLeafMeta, 
        				false);
            }
            
            
            // Planks
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.planks, 0, this.materialType, this.biome, this.disallowModSubs); Block biomePlankBlock = (Block)blockObject[0]; int biomePlankMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	{1,4,0, 1,5,0}, 
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomePlankBlock, biomePlankMeta, biomePlankBlock, biomePlankMeta, false);	
            }
        	
        	
            // Wood stairs
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.oak_stairs, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodStairsBlock = (Block)blockObject[0];
        	for (int[] uvwo : new int[][]{ // Orientation - 0: leftward, 1: rightward, 3:backward, 2:forward
        		// Back roof trim
        		{2,7,6, 2}, {4,7,6, 2}, 
        		// Left roof trim
        		{0,7,2, 0}, {0,7,4, 0}, 
        		// Right roof trim
        		{6,7,1, 1}, {6,7,3, 1}, 
        		// Front roof trim
        		{2,7,-1, 3}, {4,7,-1, 3}, 
        		})
            {
        		this.placeBlockAtCurrentPosition(world, biomeWoodStairsBlock, this.getMetadataWithOffset(Blocks.oak_stairs, uvwo[3]%4)+(uvwo[3]/4)*4, uvwo[0], uvwo[1], uvwo[2], structureBB);	
            }
            
            
            // Wooden slabs (Bottom)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabBottomBlock = (Block)blockObject[0]; int biomeWoodSlabBottomMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	// "Walls"
            	{2,4,0, 4,4,0}, // Front
            	{2,4,5, 4,4,5}, // Back
            	{1,4,2, 1,4,4}, // Left
            	{5,4,1, 5,4,4}, // Right
            	// Roof
            	{1,7,0, 5,7,0}, // Front
            	{1,7,5, 4,7,5}, // Back
            	{1,7,2, 1,7,4}, // Left
            	{5,7,1, 5,7,4}, // Right
            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, biomeWoodSlabBottomBlock, biomeWoodSlabBottomMeta, false);	
            }
            
            
            // Wooden slabs (Top)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.wooden_slab, 8, this.materialType, this.biome, this.disallowModSubs); Block biomeWoodSlabTopBlock = (Block)blockObject[0]; int biomeWoodSlabTopMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{
            	// "Walls"
            	{2,5,0, 4,5,0}, // Front
            	{2,5,5, 4,5,5}, // Back
            	{1,5,2, 1,5,4}, // Left
            	{5,5,1, 5,5,4}, // Right
            	// Roof
            	{2,7,1, 4,7,4}, 
            	// Floor
            	{2,3,2, 4,3,4}, 
            	{3,3,1, 4,3,1}, 

            	})
            {
            	this.fillWithMetadataBlocks(world, structureBB, uuvvww[0], uuvvww[1], uuvvww[2], uuvvww[3], uuvvww[4], uuvvww[5], biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, biomeWoodSlabTopBlock, biomeWoodSlabTopMeta, false);	
            }
            
            
            // Ladder
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.ladder, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeLadderBlock = (Block)blockObject[0];
        	for (int[] uuvvwwo : new int[][]{ // Orientation - 3:leftward, 1:rightward, 2:backward, 0:forward
        		{2,1,1, 2,3,1, 1},    
        		})
            {
        		this.fillWithMetadataBlocks(world, structureBB, uuvvwwo[0], uuvvwwo[1], uuvvwwo[2], uuvvwwo[3], uuvvwwo[4], uuvvwwo[5], biomeLadderBlock, StructureVillageVN.chooseLadderMeta(uuvvwwo[6], this.coordBaseMode), biomeLadderBlock, StructureVillageVN.chooseLadderMeta(uuvvwwo[6], this.coordBaseMode), false);
            }
            
            
            // Trapdoor (Bottom Horizontal)
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.trapdoor, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeTrapdoorBlock = (Block)blockObject[0]; int biomeTrapdoorMeta = (Integer)blockObject[1];
            for(int[] uuvvww : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward
            	{2,4,1, 3}, 
            	})
            {
            	this.placeBlockAtCurrentPosition(world, biomeTrapdoorBlock, StructureVillageVN.getTrapdoorMeta(uuvvww[3], this.coordBaseMode, false, false), uuvvww[0], uuvvww[1], uuvvww[2], structureBB);
            }
        	
        	
        	// Crafting Table
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.crafting_table, 0, this.materialType, this.biome, this.disallowModSubs); Block biomeCraftingTableBlock = (Block)blockObject[0]; int biomeCraftingTableMeta = (Integer)blockObject[1];
            for (int[] uvw : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward (toward you), 3:leftward, -1:upright;
        		{4,4,4}, 
           		})
        	{
            	this.placeBlockAtCurrentPosition(world, biomeCraftingTableBlock, biomeCraftingTableMeta, uvw[0], uvw[1], uvw[2], structureBB);
            }
        	
            
            // Chest
        	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
            int chestU = 4;
        	int chestV = 4;
        	int chestW = 3;
        	int chestO = 3; // 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
        	Block biomeChestBlock = (Block)StructureVillageVN.getBiomeSpecificBlockObject(Blocks.chest, 0, this.materialType, this.biome, this.disallowModSubs)[0];
        	this.placeBlockAtCurrentPosition(world, biomeChestBlock, 0, chestU, chestV, chestW, structureBB);
            world.setBlockMetadataWithNotify(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW), StructureVillageVN.chooseFurnaceMeta(chestO, this.coordBaseMode), 2);
        	TileEntity te = world.getTileEntity(this.getXWithOffset(chestU, chestW), this.getYWithOffset(chestV), this.getZWithOffset(chestU, chestW));
        	if (te instanceof IInventory)
        	{
            	ChestGenHooks chestGenHook = ChestGenHooks.getInfo(ChestLootHandler.getGenericLootForVillageType(this.villageType));
            	WeightedRandomChestContent.generateChestContents(random, chestGenHook.getItems(random), (TileEntityChest)te, chestGenHook.getCount(random));
        	}
        	
			
        	// Grass blocks
        	for (int[] grass_uw : new int[][]{
        		{2,0,1}, 
        		{1,0,0}, {2,0,0}, 
        	})
        	{
        		this.placeBlockAtCurrentPosition(world, biomeGrassBlock, biomeGrassMeta, grass_uw[0], grass_uw[1], grass_uw[2], structureBB);
        	}
        	
        	
        	// Flower
            for (int[] uvw : new int[][]{
            	{1,1,0}, 
            })
            {
            	this.placeBlockAtCurrentPosition(world, random.nextBoolean()?Blocks.red_flower:Blocks.yellow_flower, 0, uvw[0], uvw[1], uvw[2], structureBB);
            }
    		
        	// Vines
        	if (this.villageType==FunctionsVN.VillageType.JUNGLE || this.villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		for (int[] uvwo : new int[][]{ // Orientation - 0:forward, 1:rightward, 2:backward, 3:leftward
            		// Leftward
        			{0,1,1, 3}, {0,2,1, 3}, {0,3,1, 3}, {0,4,1, 3}, {0,7,1, 3}, 
        			// Backward
        			{1,4,-1, 2}, {1,5,-1, 2}, 
        			// Forward
        			{5,8,6, 0}, 
            		})
                {
        			// Replace only when air to prevent overwriting stuff outside the bb
        			if (world.isAirBlock(this.getXWithOffset(uvwo[0], uvwo[2]), this.getYWithOffset(uvwo[1]), this.getZWithOffset(uvwo[0], uvwo[2])))
        			{
        				this.placeBlockAtCurrentPosition(world, Blocks.vine, StructureVillageVN.chooseVineMeta(uvwo[3], this.coordBaseMode), uvwo[0], uvwo[1], uvwo[2], structureBB);
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
    
    
	
    // ------------------- //
    // --- Biome Decor --- //
    // ------------------- //
    
	/**
	 * Returns a list of blocks and coordinates used to construct a decor piece
	 */
	public static ArrayList<BlueprintData> getRandomJungleDecorBlueprint(VillageType villageType, MaterialType materialType, boolean disallowModSubs, BiomeGenBase biome, int horizIndex, Random random)
	{
		int decorCount = 8;
		return getJungleDecorBlueprint(random.nextInt(decorCount), villageType, materialType, disallowModSubs, biome, horizIndex, random);
	}
	public static ArrayList<BlueprintData> getJungleDecorBlueprint(int decorType, VillageType villageType, MaterialType materialType, boolean disallowModSubs, BiomeGenBase biome, int horizIndex, Random random)
	{
		ArrayList<BlueprintData> blueprint = new ArrayList(); // The blueprint to export
		
		// Generate per-material blocks
		
		Object[] blockObject;
    	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone, 0, materialType, biome, disallowModSubs); Block biomeCobblestoneBlock = (Block)blockObject[0]; int biomeCobblestoneMeta = (Integer)blockObject[1];
		
    	// Establish top and filler blocks, substituting Grass and Dirt if they're null
    	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.dirt, 0, materialType, biome, disallowModSubs); Block biomeDirtBlock = (Block)blockObject[0]; int biomeDirtMeta = (Integer)blockObject[1];
    	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.grass, 0, materialType, biome, disallowModSubs); Block biomeGrassBlock = (Block)blockObject[0]; int biomeGrassMeta = (Integer)blockObject[1];
    	Block biomeTopBlock=biomeGrassBlock; int biomeTopMeta=biomeGrassMeta; if (biome!=null && biome.topBlock!=null) {biomeTopBlock=biome.topBlock; biomeTopMeta=0;}
    	Block biomeFillerBlock=biomeDirtBlock; int biomeFillerMeta=biomeDirtMeta; if (biome!=null && biome.fillerBlock!=null) {biomeFillerBlock=biome.fillerBlock; biomeFillerMeta=0;}
		
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
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    		
    		switch (random.nextInt(4))
    		{
    		case 0: // Facing you
    			// Bale in center
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, 0, hayBlock, horizIndex%2==0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			// Bale in front
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, -1, hayBlock, horizIndex%2==0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, -1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			// Back-left corner
    			BlueprintData.addPlaceBlock(blueprint, -1, 0, 1, hayBlock, 0);
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, 1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			// Back-right corner
    			BlueprintData.addPlaceBlock(blueprint, 1, 0, 1, hayBlock, 0);
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, 1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			// Right side
    			BlueprintData.addPlaceBlock(blueprint, 1, 0, 0, hayBlock, horizIndex%2==0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			break;
    		case 1: // Facing left
    			// Bale in center
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, 0, hayBlock, horizIndex%2!=0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			// Bale in front
    			BlueprintData.addPlaceBlock(blueprint, -1, 0, 0, hayBlock, horizIndex%2!=0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			// Back-left corner
    			BlueprintData.addPlaceBlock(blueprint, 1, 0, 1, hayBlock, 0);
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, 1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			// Back-right corner
    			BlueprintData.addPlaceBlock(blueprint, 1, 0, -1, hayBlock, 0);
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, -1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			// Right side
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, -1, hayBlock, horizIndex%2!=0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, -1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			break;
    		case 2: // Facing away
    			// Bale in center
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, 0, hayBlock, horizIndex%2==0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			// Bale in front
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, 1, hayBlock, horizIndex%2==0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, 1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			// Back-left corner
    			BlueprintData.addPlaceBlock(blueprint, 1, 0, -1, hayBlock, 0);
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, -1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			// Back-right corner
    			BlueprintData.addPlaceBlock(blueprint, -1, 0, -1, hayBlock, 0);
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, -1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			// Right side
    			BlueprintData.addPlaceBlock(blueprint, -1, 0, 0, hayBlock, horizIndex%2==0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			break;
    		case 3: // Facing right
    			// Bale in center
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, 0, hayBlock, horizIndex%2!=0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			// Bale in front
    			BlueprintData.addPlaceBlock(blueprint, 1, 0, 0, hayBlock, horizIndex%2!=0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			// Back-left corner
    			BlueprintData.addPlaceBlock(blueprint, -1, 0, -1, hayBlock, 0);
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, -1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			// Back-right corner
    			BlueprintData.addPlaceBlock(blueprint, -1, 0, 1, hayBlock, 0);
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, 1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			// Right side
    			BlueprintData.addPlaceBlock(blueprint, 0, 0, 1, hayBlock, horizIndex%2!=0?8:4);
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, 1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			break;
    		}
    		break;

    	case 1: // Campfire on cobblestone by jss2a98aj
        	blockObject = ModObjects.chooseModCampfireBlock(random.nextInt(4), horizIndex); Block campfireBlock = (Block) blockObject[0]; int campfireMeta = (Integer) blockObject[1];
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    		BlueprintData.addFillBelowTo(blueprint, 1, -1, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    		BlueprintData.addFillBelowTo(blueprint, -1, -1, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, -1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    		BlueprintData.addPlaceBlock(blueprint, 0, 0, 0, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, 1, 0, 0, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, -1, 0, 0, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, 0, 0, 1, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, 0, 0, -1, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, 0, 1, 0, campfireBlock, campfireMeta);
    		break;
    		
    	case 2: // Fence post hanging lantern on stone brick foundation by jss2a98aj
    		// Central column
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    		BlueprintData.addPlaceBlock(blueprint, 0, 0, 0, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		BlueprintData.addPlaceBlock(blueprint, 0, 1, 0, biomeChiseledStoneBlock, biomeChiseledStoneMeta);
    		BlueprintData.addPlaceBlock(blueprint, 0, 2, 0, biomeFenceBlock);
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, 0, 3, 0, biomeFenceBlock);
    		
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, 0, 3, -1, biomeFenceBlock); // Fence post
    		BlueprintData.addPlaceBlock(blueprint, 0, 2, -1, biomeHangingLanternBlock, biomeHangingLanternMeta);
    		
    		// Vines
        	if (villageType==FunctionsVN.VillageType.JUNGLE || villageType==FunctionsVN.VillageType.SWAMP)
        	{ // 0 and 2 are ok
        		for (int[] xyzo : new int[][]{ // Orientation - 1:north, 2:east, 4:south, 8:west
        			{0,0,-1, StructureVillageVN.chooseVineMeta(2, horizIndex)}, // Front
        			{1,0,0, StructureVillageVN.chooseVineMeta(1, horizIndex)}, // Right
        			{-1,0,0, StructureVillageVN.chooseVineMeta(3, horizIndex)}, // Left
            		})
                {
        			BlueprintData.addPlaceBlock(blueprint, xyzo[0], xyzo[1], xyzo[2], Blocks.vine, xyzo[3]);
                }
        	}
    		break;
    		
    	case 3: // Lantern on stone brick post by jss2a98aj
    		// Central column
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    		BlueprintData.addPlaceBlock(blueprint, 0, 0, 0, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		BlueprintData.addPlaceBlock(blueprint, 0, 1, 0, biomeChiseledStoneBlock, biomeChiseledStoneMeta);
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, 0, 2, 0, biomeSittingLanternBlock, biomeSittingLanternMeta);
    		
    		// Vines
    		switch (random.nextInt(4))
    		{
    		case 0: // Facing you
        		if (villageType==FunctionsVN.VillageType.JUNGLE || villageType==FunctionsVN.VillageType.SWAMP)
            	{
            		for (int[] xyzo : new int[][]{ // Orientation - 1:north, 2:east, 4:south, 8:west
            			{0,0,-1, StructureVillageVN.chooseVineMeta(2, horizIndex)}, {0,1,-1, StructureVillageVN.chooseVineMeta(2, horizIndex)}, // Front
            			{1,0,0, StructureVillageVN.chooseVineMeta(1, horizIndex)}, // Right
            			{0,0,1, StructureVillageVN.chooseVineMeta(0, horizIndex)}, // Back
            			{-1,1,0, StructureVillageVN.chooseVineMeta(3, horizIndex)}, // Left
                		})
                    {
            			BlueprintData.addPlaceBlock(blueprint, xyzo[0], xyzo[1], xyzo[2], Blocks.vine, xyzo[3]);
                    }
            	}
        		break;
        		
    		case 1: // Facing left
    			if (villageType==FunctionsVN.VillageType.JUNGLE || villageType==FunctionsVN.VillageType.SWAMP)
            	{
            		for (int[] xyzo : new int[][]{ // Orientation - 1:north, 2:east, 4:south, 8:west
            			{-1,0,0, StructureVillageVN.chooseVineMeta(3, horizIndex)}, {-1,1,0, StructureVillageVN.chooseVineMeta(3, horizIndex)}, // Front
            			{0,0,-1, StructureVillageVN.chooseVineMeta(2, horizIndex)}, // Right
            			{1,0,0, StructureVillageVN.chooseVineMeta(1, horizIndex)}, // Back
            			{0,1,1, StructureVillageVN.chooseVineMeta(0, horizIndex)}, // Left
                		})
                    {
            			BlueprintData.addPlaceBlock(blueprint, xyzo[0], xyzo[1], xyzo[2], Blocks.vine, xyzo[3]);
                    }
            	}
            	break;
            	
    		case 2: // Facing away
    			if (villageType==FunctionsVN.VillageType.JUNGLE || villageType==FunctionsVN.VillageType.SWAMP)
            	{
            		for (int[] xyzo : new int[][]{ // Orientation - 1:north, 2:east, 4:south, 8:west
            			{0,0,1, StructureVillageVN.chooseVineMeta(0, horizIndex)}, {0,1,1, StructureVillageVN.chooseVineMeta(0, horizIndex)}, // Front
            			{-1,0,0, StructureVillageVN.chooseVineMeta(3, horizIndex)}, // Right
            			{0,0,-1, StructureVillageVN.chooseVineMeta(2, horizIndex)}, // Back
            			{1,1,0, StructureVillageVN.chooseVineMeta(1, horizIndex)}, // Left
                		})
                    {
            			BlueprintData.addPlaceBlock(blueprint, xyzo[0], xyzo[1], xyzo[2], Blocks.vine, xyzo[3]);
                    }
            	}
    			break;
    			
    		case 3: // Facing right
    			if (villageType==FunctionsVN.VillageType.JUNGLE || villageType==FunctionsVN.VillageType.SWAMP)
            	{
            		for (int[] xyzo : new int[][]{ // Orientation - 1:north, 2:east, 4:south, 8:west
            			{1,0,0, StructureVillageVN.chooseVineMeta(1, horizIndex)}, {1,1,0, StructureVillageVN.chooseVineMeta(1, horizIndex)}, // Front
            			{0,0,1, StructureVillageVN.chooseVineMeta(0, horizIndex)}, // Right
            			{-1,0,0, StructureVillageVN.chooseVineMeta(3, horizIndex)}, // Back
            			{0,1,-1, StructureVillageVN.chooseVineMeta(2, horizIndex)}, // Left
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
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    		BlueprintData.addFillWithBlocks(blueprint, 0,0,0, 0,2,0, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, 0, 3, 0, biomeChiseledStoneBlock, biomeChiseledStoneMeta);
    		
    		// Belt of vines
    		if (villageType==FunctionsVN.VillageType.JUNGLE || villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		BlueprintData.addPlaceBlock(blueprint, 0, 1, 1, Blocks.vine, StructureVillageVN.chooseVineMeta(0, horizIndex));
        		BlueprintData.addPlaceBlock(blueprint, 1, 1, 0, Blocks.vine, StructureVillageVN.chooseVineMeta(1, horizIndex));
        		BlueprintData.addPlaceBlock(blueprint, 0, 1, -1, Blocks.vine, StructureVillageVN.chooseVineMeta(2, horizIndex));
        		BlueprintData.addPlaceBlock(blueprint, -1, 1, 0, Blocks.vine, StructureVillageVN.chooseVineMeta(3, horizIndex));
        	}
    		
    		genericInt = random.nextInt(4);
    		
    		// Hanging lanterns
        	blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.cobblestone_wall, 0, materialType, biome, disallowModSubs); Block biomeCobblestoneWallBlock = (Block)blockObject[0]; int biomeCobblestoneWallMeta = (Integer)blockObject[1];
        	
			BlueprintData.addPlaceBlockAndClearAbove(blueprint, genericInt%2==0?-1:0, 3, genericInt%2==0?0:-1, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta);
			BlueprintData.addPlaceBlockAndClearAbove(blueprint, genericInt%2==0?1:0, 3, genericInt%2==0?0:1, biomeCobblestoneWallBlock, biomeCobblestoneWallMeta);
			BlueprintData.addPlaceBlock(blueprint, genericInt%2==0?-1:0, 2, genericInt%2==0?0:-1, biomeHangingLanternBlock, biomeHangingLanternMeta);
			BlueprintData.addPlaceBlock(blueprint, genericInt%2==0?1:0, 2, genericInt%2==0?0:1, biomeHangingLanternBlock, biomeHangingLanternMeta);
    		
			// Vines
    		if (villageType==FunctionsVN.VillageType.JUNGLE || villageType==FunctionsVN.VillageType.SWAMP)
        	{
        		BlueprintData.addPlaceBlock(blueprint, genericInt%2!=0?-1:0, 2, genericInt%2!=0?0:-1, Blocks.vine, genericInt%2!=0?StructureVillageVN.chooseVineMeta(3, horizIndex):StructureVillageVN.chooseVineMeta(2, horizIndex));
        		BlueprintData.addPlaceBlock(blueprint, genericInt%2!=0?1:0, 2, genericInt%2!=0?0:1, Blocks.vine, genericInt%2!=0?StructureVillageVN.chooseVineMeta(1, horizIndex):StructureVillageVN.chooseVineMeta(0, horizIndex));
        		
        		BlueprintData.addPlaceBlock(blueprint, genericInt==1?-1:genericInt==3?1:0, 3, genericInt==0?-1:genericInt==2?1:0, Blocks.vine, genericInt==0?StructureVillageVN.chooseVineMeta(2, horizIndex):genericInt==1?StructureVillageVN.chooseVineMeta(3, horizIndex):genericInt==2?StructureVillageVN.chooseVineMeta(0, horizIndex):StructureVillageVN.chooseVineMeta(1, horizIndex));
        	}
    		
    		break;
    		
    	case 5: // Lantern on a stump by jss2a98aj
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    		BlueprintData.addPlaceBlock(blueprint, 0, 0, 0, biomeLogVertBlock, biomeLogVertMeta);
    		BlueprintData.addPlaceBlock(blueprint, 0, 1, 0, biomeSittingLanternBlock, biomeSittingLanternMeta);
			
    		break;
    		
    	case 6: // Lantern on a log by jss2a98aj
    		
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, horizIndex%2==0?8:4, materialType, biome, disallowModSubs); Block biomeLogHorAcrossBlock = (Block)blockObject[0]; int biomeLogHorAcrossMeta = (Integer)blockObject[1]; // Perpendicular to you
    		blockObject = StructureVillageVN.getBiomeSpecificBlockObject(Blocks.log, horizIndex%2==0?4:8, materialType, biome, disallowModSubs); Block biomeLogHorAlongBlock = (Block)blockObject[0]; int biomeLogHorAlongMeta = (Integer)blockObject[1]; // Toward you
    		
    		BlueprintData.addFillBelowTo(blueprint, 0, -1, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    		
    		switch (random.nextInt(4))
    		{
    		case 0: // Facing you
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			BlueprintData.addFillWithBlocks(blueprint, 0,0,0, 1,0,0, biomeLogHorAlongBlock, biomeLogHorAlongMeta);
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, 1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			BlueprintData.addPlaceBlock(blueprint, -1, 0, 1, biomeLogVertBlock, biomeLogVertMeta);
    			// Vines
    			if (villageType==FunctionsVN.VillageType.JUNGLE || villageType==FunctionsVN.VillageType.SWAMP) // Orientation - 1:north, 2:east, 4:south, 8:west
            	{
            		BlueprintData.addPlaceBlock(blueprint, -1, 0, 0, Blocks.vine, StructureVillageVN.chooseVineMeta(2, horizIndex)); // Front
            		BlueprintData.addPlaceBlock(blueprint, 0, 0, 1, Blocks.vine, StructureVillageVN.chooseVineMeta(1, horizIndex)); // Right
            	}
    			break;
    			
    		case 1: // Facing left
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, -1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			BlueprintData.addFillWithBlocks(blueprint, 0,0,-1, 0,0,0, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta);
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, 1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			BlueprintData.addPlaceBlock(blueprint, 1, 0, 1, biomeLogVertBlock, biomeLogVertMeta);
    			// Vines
    			if (villageType==FunctionsVN.VillageType.JUNGLE || villageType==FunctionsVN.VillageType.SWAMP) // Orientation - 1:north, 2:east, 4:south, 8:west
            	{
            		BlueprintData.addPlaceBlock(blueprint, 0, 0, 1, Blocks.vine, StructureVillageVN.chooseVineMeta(3, horizIndex)); // Front
            		BlueprintData.addPlaceBlock(blueprint, 1, 0, 0, Blocks.vine, StructureVillageVN.chooseVineMeta(2, horizIndex)); // Right
            	}
    			break;
    			
    		case 2: // Facing away
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			BlueprintData.addFillWithBlocks(blueprint, -1,0,0, 0,0,0, biomeLogHorAlongBlock, biomeLogHorAlongMeta);
    			BlueprintData.addFillBelowTo(blueprint, 1, -1, -1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			BlueprintData.addPlaceBlock(blueprint, 1, 0, -1, biomeLogVertBlock, biomeLogVertMeta);
    			// Vines
    			if (villageType==FunctionsVN.VillageType.JUNGLE || villageType==FunctionsVN.VillageType.SWAMP) // Orientation - 1:north, 2:east, 4:south, 8:west
            	{
            		BlueprintData.addPlaceBlock(blueprint, 1, 0, 0, Blocks.vine, StructureVillageVN.chooseVineMeta(0, horizIndex)); // Front
            		BlueprintData.addPlaceBlock(blueprint, 0, 0, -1, Blocks.vine, StructureVillageVN.chooseVineMeta(3, horizIndex)); // Right
            	}
    			break;
    			
    		case 3: // Facing right
    			BlueprintData.addFillBelowTo(blueprint, 0, -1, 1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			BlueprintData.addFillWithBlocks(blueprint, 0,0,0, 0,0,1, biomeLogHorAcrossBlock, biomeLogHorAcrossMeta);
    			BlueprintData.addFillBelowTo(blueprint, -1, -1, -1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    			BlueprintData.addPlaceBlock(blueprint, -1, 0, -1, biomeLogVertBlock, biomeLogVertMeta);
    			// Vines
    			if (villageType==FunctionsVN.VillageType.JUNGLE || villageType==FunctionsVN.VillageType.SWAMP) // Orientation - 1:north, 2:east, 4:south, 8:west
            	{
            		BlueprintData.addPlaceBlock(blueprint, 0, 0, -1, Blocks.vine, StructureVillageVN.chooseVineMeta(1, horizIndex)); // Front
            		BlueprintData.addPlaceBlock(blueprint, -1, 0, 0, Blocks.vine, StructureVillageVN.chooseVineMeta(0, horizIndex)); // Right
            	}
    			break;
    		}
    		
    		// Lantern
    		BlueprintData.addPlaceBlock(blueprint, 0, 1, 0, biomeSittingLanternBlock, biomeSittingLanternMeta);
    		
    		break;
    		
    	case 7: // Lantern between two posts by jss2a98aj
    		
    		int vertOffset = -1;
    		
    		BlueprintData.addFillBelowTo(blueprint, 0, -1+vertOffset, 0, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta); // Foundation
    		BlueprintData.addPlaceBlockAndClearAbove(blueprint, 0, 0+vertOffset, 0, biomeCobblestoneBlock, biomeCobblestoneMeta);
    		
    		genericBoolean = random.nextBoolean();
    		
    		// Foundations
    		BlueprintData.addFillBelowTo(blueprint, genericBoolean?-1:0, -1+vertOffset, genericBoolean?0:-1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta);
    		BlueprintData.addFillBelowTo(blueprint, genericBoolean?1:0, -1+vertOffset, genericBoolean?0:1, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneBlock : biomeFillerBlock, FunctionsVN.shouldUseCobblestoneFoundation(biome) ? biomeCobblestoneMeta : biomeFillerMeta);
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
