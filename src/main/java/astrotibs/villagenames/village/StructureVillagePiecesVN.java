package astrotibs.villagenames.village;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.utility.FunctionsVN;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.BiomeEvent;

/**
 * This class gives me better control over deactivating legacy buildings
 * @author AstroTibs
 */
public class StructureVillagePiecesVN
{
    public static List getStructureVillageWeightedPieceList(Random random, int villageSize)
    {
        ArrayList arraylist = new ArrayList();
        
        if (GeneralConfig.structureLegacySmallHouse) {arraylist.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.House4Garden.class, 4, MathHelper.getRandomIntegerInRange(random, 2 + villageSize, 4 + villageSize * 2)));}
        if (GeneralConfig.structureLegacyChurch) {arraylist.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.Church.class, 20, MathHelper.getRandomIntegerInRange(random, 0 + villageSize, 1 + villageSize)));}
        if (GeneralConfig.structureLegacyLibrary) {arraylist.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.House1.class, 20, MathHelper.getRandomIntegerInRange(random, 0 + villageSize, 2 + villageSize)));}
        if (GeneralConfig.structureLegacyHut) {arraylist.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.WoodHut.class, 3, MathHelper.getRandomIntegerInRange(random, 2 + villageSize, 5 + villageSize * 3)));}
        if (GeneralConfig.structureLegacyButcherShop) {arraylist.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.Hall.class, 15, MathHelper.getRandomIntegerInRange(random, 0 + villageSize, 2 + villageSize)));}
        if (GeneralConfig.structureLegacyLargeFarm) {arraylist.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.Field1.class, 3, MathHelper.getRandomIntegerInRange(random, 1 + villageSize, 4 + villageSize)));}
        if (GeneralConfig.structureLegacySmallFarm) {arraylist.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.Field2.class, 3, MathHelper.getRandomIntegerInRange(random, 2 + villageSize, 4 + villageSize * 2)));}
        if (GeneralConfig.structureLegacySmithy) {arraylist.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.House2.class, 15, MathHelper.getRandomIntegerInRange(random, 0, 1 + villageSize)));}
        if (GeneralConfig.structureLegacyLargeHouse) {arraylist.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.House3.class, 8, MathHelper.getRandomIntegerInRange(random, 0 + villageSize, 3 + villageSize * 2)));}
        
        VillagerRegistry.addExtraVillageComponents(arraylist, random, villageSize);

        Iterator iterator = arraylist.iterator();

        while (iterator.hasNext())
        {
            if (((StructureVillagePieces.PieceWeight)iterator.next()).villagePiecesLimit == 0)
            {
                iterator.remove();
            }
        }

        return arraylist;
    }
    
    // Pasted in from StructureVillagePieces so that I can access it
    // This prepares a new path component to build upon
    private static StructureComponent getNextComponentVillagePath(StructureVillagePieces.Start start, List components, Random random, int x, int y, int z, int coordBaseMode, int componentType)
    {
        if (componentType > 3 + start.terrainType)
        {
            return null;
        }
        else if (Math.abs(x - start.getBoundingBox().minX) <= 112 && Math.abs(z - start.getBoundingBox().minZ) <= 112)
        {
            StructureBoundingBox structureboundingbox = StructureVillagePieces.Path.func_74933_a(start, components, random, x, y, z, coordBaseMode);

            if (structureboundingbox != null && structureboundingbox.minY > 10)
            {
                StructureVillagePiecesVN.PathVN path = new StructureVillagePiecesVN.PathVN(start, componentType, random, structureboundingbox, coordBaseMode);
                int medianX = (path.getBoundingBox().minX + path.getBoundingBox().maxX) / 2;
                int medianZ = (path.getBoundingBox().minZ + path.getBoundingBox().maxZ) / 2;
                int rangeX = path.getBoundingBox().maxX - path.getBoundingBox().minX;
                int rangeZ = path.getBoundingBox().maxZ - path.getBoundingBox().minZ;
                int bboxWidth = rangeX > rangeZ ? rangeX : rangeZ;
                
                // Replaces the ordinary "areBiomesViable" method with one that uses the VN biome config list
                BiomeGenBase biome = start.getWorldChunkManager().getBiomeGenAt(medianX, medianZ);
            	
            	if (GeneralConfig.spawnBiomesNames != null) // Biome list is not empty
        		{
        			for (int i = 0; i < GeneralConfig.spawnBiomesNames.length; i++)
        			{
        				if (GeneralConfig.spawnBiomesNames[i].equals(biome.biomeName))
        				{
        					BiomeManager.addVillageBiome(biome, true); // Set biome to be able to spawn villages
        					
        					components.add(path);
                            start.field_74930_j.add(path);
                            return path;
        				}
        			}
        		}
            	
            }
            
            return null;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * Discover the y coordinate that will serve as the ground level of the supplied BoundingBox.
     * (An ACTUAL median of all the levels in the BB's horizontal rectangle).
     */
    protected int getMedianGroundLevelBody(World world, StructureBoundingBox boundingBox)
    {
    	ArrayList<Integer> i = new ArrayList<Integer>();
    	
        for (int k = boundingBox.minZ; k <= boundingBox.maxZ; ++k)
        {
            for (int l = boundingBox.minX; l <= boundingBox.maxX; ++l)
            {
                if (boundingBox.isVecInside(l, 64, k))
                {
                    i.add(Math.max(world.getTopSolidOrLiquidBlock(l, k), world.provider.getAverageGroundLevel()));
                }
            }
        }
        
        return FunctionsVN.medianIntArray(i, true);
    }
    
    /**
     * Biome-specific block replacement
     */
    protected static Block getBiomeSpecificBlock(Block block, int meta, StructureVillagePieces.Start startPiece)
    {
    	// Post Forge event
        BiomeEvent.GetVillageBlockID event = new BiomeEvent.GetVillageBlockID(startPiece == null ? null : startPiece.biome, block, meta);
        MinecraftForge.TERRAIN_GEN_BUS.post(event);
        if (event.getResult() == Result.DENY) return event.replacement;
        
        // Replace the block if this is a deserty boi
        if (startPiece.inDesert)
        {
            if (block == Blocks.log || block == Blocks.log2) {return Blocks.sandstone;}
            if (block == Blocks.cobblestone)                 {return Blocks.sandstone;}
            if (block == Blocks.planks)                      {return Blocks.sandstone;}
            if (block == Blocks.oak_stairs)                  {return Blocks.sandstone_stairs;}
            if (block == Blocks.stone_stairs)                {return Blocks.sandstone_stairs;}
            if (block == Blocks.gravel)                      {return Blocks.sandstone;}
        }

        return block;
    }
    
    /**
     * Used to determine what path block to place into the world
     */
    protected static void setPathSpecificBlock(World world, StructureVillagePieces.Start startPiece, int meta, int posX, int posY, int posZ)
    {
    	int seaLevel = 63; //TODO - actually call sea level in later versions
    	
    	Block blockToReplace = world.getBlock(posX, posY, posZ);
    	
    	Block grassPath = getBiomeSpecificBlock(ModObjects.chooseModPathBlock(), 0, startPiece);
    	Block planks = getBiomeSpecificBlock(Blocks.planks, 0, startPiece);
    	Block gravel = getBiomeSpecificBlock(Blocks.gravel, 0, startPiece);
    	Block cobblestone = getBiomeSpecificBlock(Blocks.cobblestone, 0, startPiece);
    	
    	if (posY < seaLevel) {posY = seaLevel-1;}
    	
    	while (posY >= seaLevel-1)
    	{
    		Block surfaceBlock = world.getBlock(posX, posY, posZ);
    		
    		// Replace grass with grass path
    		if (surfaceBlock instanceof BlockGrass && world.isAirBlock(posX, posY+1, posZ))
    		{
    			world.setBlock(posX, posY, posZ, grassPath, 0, 2);
    			break;
    		}
    		
    		// Replace liquid with planks. Let's hope this isn't liquid you dumb banana
    		if (surfaceBlock.getMaterial().isLiquid())
    		{
    			world.setBlock(posX, posY, posZ, planks, 0, 2);
    			break;
    		}
    		
    		// Replace sand or standstone with reinforced gravel
    		if (surfaceBlock instanceof BlockSand || surfaceBlock instanceof BlockSandStone)
    		{
    			world.setBlock(posX, posY, posZ, gravel, 0, 2);
    			world.setBlock(posX, posY-1, posZ, cobblestone, 0, 2);
    			break;
    		}
    		
    		posY -=1;
    	}
    }
    
    
    // --------------------------------- //    
    // --- Structure components here --- //
    // --------------------------------- //
    
    // --- General --- //
    
    public static class PathVN extends StructureVillagePieces.Road
    {
        private int averageGroundLevel;
        
        public PathVN() {}

        public PathVN(StructureVillagePieces.Start start, int componentType, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
        {
            super(start, componentType);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            this.averageGroundLevel = Math.max(boundingBox.getXSize(), boundingBox.getZSize());
        }

        protected void func_143012_a(NBTTagCompound p_143012_1_)
        {
            super.func_143012_a(p_143012_1_);
            p_143012_1_.setInteger("Length", this.averageGroundLevel);
        }

        protected void func_143011_b(NBTTagCompound p_143011_1_)
        {
            super.func_143011_b(p_143011_1_);
            this.averageGroundLevel = p_143011_1_.getInteger("Length");
        }

        /**
         * Initiates construction of the Structure Component picked, at the current Location of StructGen
         */
        public void buildComponent(StructureComponent start, List components, Random random)
        {
            boolean flag = false;
            int i;
            StructureComponent structurecomponent1;

            for (i = random.nextInt(5); i < this.averageGroundLevel - 8; i += 2 + random.nextInt(5))
            {
                structurecomponent1 = this.getNextComponentNN((StructureVillagePieces.Start)start, components, random, 0, i);

                if (structurecomponent1 != null)
                {
                    i += Math.max(structurecomponent1.getBoundingBox().getXSize(), structurecomponent1.getBoundingBox().getZSize());
                    flag = true;
                }
            }

            for (i = random.nextInt(5); i < this.averageGroundLevel - 8; i += 2 + random.nextInt(5))
            {
                structurecomponent1 = this.getNextComponentPP((StructureVillagePieces.Start)start, components, random, 0, i);

                if (structurecomponent1 != null)
                {
                    i += Math.max(structurecomponent1.getBoundingBox().getXSize(), structurecomponent1.getBoundingBox().getZSize());
                    flag = true;
                }
            }

            if (flag && random.nextInt(3) > 0)
            {
                switch (this.coordBaseMode)
                {
                    case 0:
                        getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, 1, this.getComponentType());
                        break;
                    case 1:
                        getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
                        break;
                    case 2:
                        getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, 1, this.getComponentType());
                        break;
                    case 3:
                        getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
                }
            }

            if (flag && random.nextInt(3) > 0)
            {
                switch (this.coordBaseMode)
                {
                    case 0:
                        getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, 3, this.getComponentType());
                        break;
                    case 1:
                        getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                        break;
                    case 2:
                        getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, 3, this.getComponentType());
                        break;
                    case 3:
                        getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                }
            }
        }

        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
         * Mineshafts at the end, it adds Fences...
         */
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
            //Block block = this.func_151558_b(Blocks.emerald_block, 0);
        	StructureVillagePieces.Start startPiece_reflected = ReflectionHelper.getPrivateValue(StructureVillagePieces.Village.class, this, new String[]{"startPiece"});
        	
        	// Scans X, Z inside bounding box and finds the ground layer
            for (int i = this.boundingBox.minX; i <= this.boundingBox.maxX; ++i)
            {
                for (int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; ++j)
                {
                    if (structureBB.isVecInside(i, 64, j))
                    {
                    	// Gets ground level, so long as it's not leaves or other foliage
                        int k = world.getTopSolidOrLiquidBlock(i, j) - 1;
                        
                        // TODO - specific block here
                        LogHelper.info("Path block to be replaced: " + world.getBlock(i, k, j) + " at " + i + " " + k + " " + j);
                        
                        setPathSpecificBlock(world, startPiece_reflected, 0, i, k, j);
                    }
                }
            }

            return true;
        }
    }
    
    
    // --- Plains --- //
    
    // Well
    public static class PlainsMeetingPoint1 extends StructureVillagePieces.Start
    {
    	int xoffset = 2; int zoffset = 2; // Offsets from vanilla

	    int wellHeight = 4+1;
	    int wellTop = 78+1; 
	    int wellDepthDecrease=7;
	    
		public PlainsMeetingPoint1() {}
		
		public PlainsMeetingPoint1(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List p_i2104_6_, int p_i2104_7_)
		{
		    super(chunkManager, componentType, random, posX, posZ, p_i2104_6_, p_i2104_7_);
		    
		    // Establish orientation
            this.coordBaseMode = random.nextInt(4);
            switch (this.coordBaseMode)
            {
                case 0:
                case 2:
                    this.boundingBox = new StructureBoundingBox(posX, 64+wellDepthDecrease, posZ, posX + 6 - 1 + 4, wellTop, posZ + 6 - 1 + 4);
                    break;
                default:
                    this.boundingBox = new StructureBoundingBox(posX, 64+wellDepthDecrease, posZ, posX + 6 - 1 + 4, wellTop, posZ + 6 - 1 + 4);
            }
		}
		
       /*
        * Add the paths that lead outward from this structure
        */
       public void buildComponent(StructureComponent start, List components, Random random)
       {
           getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX - 1, this.boundingBox.maxY - wellHeight, this.boundingBox.minZ + 4, 1, this.getComponentType());
           getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.maxX + 1, this.boundingBox.maxY - wellHeight, this.boundingBox.minZ + 4, 3, this.getComponentType());
           getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 4, this.boundingBox.maxY - wellHeight, this.boundingBox.minZ - 1, 2, this.getComponentType());
           getNextComponentVillagePath((StructureVillagePieces.Start)start, components, random, this.boundingBox.minX + 4, this.boundingBox.maxY - wellHeight, this.boundingBox.maxZ + 1, 0, this.getComponentType());
       }
       
        /**
         * Construct the well
         */
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox)
        {
        	
            if (this.field_143015_k < 0)
            {
                this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);

                if (this.field_143015_k < 0) {return true;} // Do not construct a well in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + (wellHeight-1) - wellDepthDecrease, 0);
            }
            
            // The well gets filled completely with water first
            this.fillWithBlocks(world, structureBoundingBox, 1+xoffset, 0+wellDepthDecrease, 1+zoffset, 4+xoffset, 12, 4+zoffset, Blocks.cobblestone, Blocks.flowing_water, false);
            
            // I believe this replaces the top water level with air
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 2+xoffset, 12, 2+zoffset, structureBoundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 3+xoffset, 12, 2+zoffset, structureBoundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 2+xoffset, 12, 3+zoffset, structureBoundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 3+xoffset, 12, 3+zoffset, structureBoundingBox);
            
            // Well support posts
            this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 1+xoffset, 13, 1+zoffset, structureBoundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 1+xoffset, 14, 1+zoffset, structureBoundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 4+xoffset, 13, 1+zoffset, structureBoundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 4+xoffset, 14, 1+zoffset, structureBoundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 1+xoffset, 13, 4+zoffset, structureBoundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 1+xoffset, 14, 4+zoffset, structureBoundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 4+xoffset, 13, 4+zoffset, structureBoundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.fence, 0, 4+xoffset, 14, 4+zoffset, structureBoundingBox);
            
            // Roof of the well
            this.fillWithBlocks(world, structureBoundingBox, 1+xoffset, 15, 1+zoffset, 4+xoffset, 15, 4+zoffset, Blocks.cobblestone, Blocks.cobblestone, false);
            
            // Line the well with cobblestone and ensure the spaces above are clear
            for (int i = 0; i <= 5; ++i)
            {
                for (int j = 0; j <= 5; ++j)
                {
                    if (j == 0 || j == 5 || i == 0 || i == 5)
                    {
                        //this.placeBlockAtCurrentPosition(world, Blocks.dirt, 0, j+xoffset, 10+yoffset, i+zoffset, structureBoundingBox);
                        this.placeBlockAtCurrentPosition(world, Blocks.cobblestone, 0, j+xoffset, 11, i+zoffset, structureBoundingBox);
                        this.clearCurrentPositionBlocksUpwards(world, j+xoffset, 12, i+zoffset, structureBoundingBox);
                    }
                }
            }
            
            // Over-lid torches
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 1+xoffset, 16, 1+zoffset, structureBoundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 1+xoffset, 16, 4+zoffset, structureBoundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 4+xoffset, 16, 1+zoffset, structureBoundingBox);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 4+xoffset, 16, 4+zoffset, structureBoundingBox);
            
            // Encircle the well with path
            Block pathBlock = ModObjects.chooseModPathBlock();
            
            for (int i = 1; i <= 8; ++i)
            {
                for (int j = 1; j <= 8; ++j)
                {
                    if (j == 1 || j == 8 || i == 1 || i == 8)
                    {
                        this.placeBlockAtCurrentPosition(world, pathBlock, 0, j, 10, i, structureBoundingBox);
                        this.clearCurrentPositionBlocksUpwards(world, j, 11, i, structureBoundingBox);
                    }
                }
            }
            // Add path nodules at the end
            for (int i : new int[]{3,4,5,6})
            {
            	this.placeBlockAtCurrentPosition(world, pathBlock, 0, 0, 10, i, structureBoundingBox);
                this.clearCurrentPositionBlocksUpwards(world,         0, 11, i, structureBoundingBox);
                this.placeBlockAtCurrentPosition(world, pathBlock, 0, 9, 10, i, structureBoundingBox);
                this.clearCurrentPositionBlocksUpwards(world,         9, 11, i, structureBoundingBox);
                this.placeBlockAtCurrentPosition(world, pathBlock, 0, i, 10, 0, structureBoundingBox);
                this.clearCurrentPositionBlocksUpwards(world,         i, 11, 0, structureBoundingBox);
                this.placeBlockAtCurrentPosition(world, pathBlock, 0, i, 10, 9, structureBoundingBox);
                this.clearCurrentPositionBlocksUpwards(world,         i, 11, 9, structureBoundingBox);
            }
            
            return true;
        }
    }
    
}
