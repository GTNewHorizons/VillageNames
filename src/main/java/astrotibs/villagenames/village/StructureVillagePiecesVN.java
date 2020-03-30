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
     * Use outlineOnly if you'd like to tally only the boundary values.
     */
    protected static int getMedianGroundLevel(World world, StructureBoundingBox boundingBox, boolean outlineOnly)
    {
    	ArrayList<Integer> i = new ArrayList<Integer>();
    	
        for (int k = boundingBox.minZ; k <= boundingBox.maxZ; ++k)
        {
            for (int l = boundingBox.minX; l <= boundingBox.maxX; ++l)
            {
                if (boundingBox.isVecInside(l, 64, k))
                {
                	if (!outlineOnly || (outlineOnly && (k==boundingBox.minZ || k==boundingBox.maxZ || l==boundingBox.minX || l==boundingBox.maxX)))
                	{
                		//i.add(Math.max(world.getTopSolidOrLiquidBlock(l, k), world.provider.getAverageGroundLevel())); // getAverageGroundLevel returns 64
                		//LogHelper.info("height " + world.getTopSolidOrLiquidBlock(l, k) + " at " + l + " " + k);
                		i.add(world.getTopSolidOrLiquidBlock(l, k));
                	}
                    
                }
            }
        }
        
        return FunctionsVN.medianIntArray(i, true);
    }
    
    /**
     * Biome-specific block replacement
     */
    protected static Object[] getBiomeSpecificBlock(Block block, int meta, StructureVillagePiecesVN.StartVN startPiece)
    {
    	// TODO - use vanilla fences and gates in 1.8
    	
    	if (startPiece.materialType == FunctionsVN.MaterialType.SPRUCE)
        {
        	if (block == Blocks.log || block == Blocks.log2)   {return new Object[]{Blocks.log, (meta/4)*4 + 1};}
        	if (block == Blocks.planks)                        {return new Object[]{Blocks.planks, 1};}
        	if (block == Blocks.fence)                         {return new Object[]{ModObjects.chooseModFence(1), 0};}
        	if (block == Blocks.fence_gate)                    {return new Object[]{ModObjects.chooseModFenceGate(1), 0};}
        	if (block == Blocks.oak_stairs)                    {return new Object[]{Blocks.spruce_stairs, meta};}
        	if (block == Blocks.wooden_slab)                   {return new Object[]{Blocks.wooden_slab, meta==0? 0 +1: meta==8? 8 +1 : meta};}
        	if (block == Blocks.double_wooden_slab)            {return new Object[]{Blocks.double_wooden_slab, 1};}
        }
        if (startPiece.materialType == FunctionsVN.MaterialType.BIRCH)
        {
        	if (block == Blocks.log || block == Blocks.log2)   {return new Object[]{Blocks.log, (meta/4)*4 + 2};}
        	if (block == Blocks.planks)                        {return new Object[]{Blocks.planks, 2};}
        	if (block == Blocks.fence)                         {return new Object[]{ModObjects.chooseModFence(2), 0};}
        	if (block == Blocks.fence_gate)                    {return new Object[]{ModObjects.chooseModFenceGate(2), 0};}
        	if (block == Blocks.oak_stairs)                    {return new Object[]{Blocks.birch_stairs, meta};}
        	if (block == Blocks.wooden_slab)                   {return new Object[]{Blocks.wooden_slab, meta==0? 0 +2: meta==8? 8 +2 : meta};}
        	if (block == Blocks.double_wooden_slab)            {return new Object[]{Blocks.double_wooden_slab, 2};}
        }
        if (startPiece.materialType == FunctionsVN.MaterialType.JUNGLE)
        {
        	if (block == Blocks.log || block == Blocks.log2)   {return new Object[]{Blocks.log, (meta/4)*4 + 3};}
        	if (block == Blocks.planks)                        {return new Object[]{Blocks.planks, 3};}
        	if (block == Blocks.fence)                         {return new Object[]{ModObjects.chooseModFence(3), 0};}
        	if (block == Blocks.fence_gate)                    {return new Object[]{ModObjects.chooseModFenceGate(3), 0};}
        	if (block == Blocks.oak_stairs)                    {return new Object[]{Blocks.jungle_stairs, meta};}
        	if (block == Blocks.wooden_slab)                   {return new Object[]{Blocks.wooden_slab, meta==0? 0 +3: meta==8? 8 +3 : meta};}
        	if (block == Blocks.double_wooden_slab)            {return new Object[]{Blocks.double_wooden_slab, 3};}
        }
        if (startPiece.materialType == FunctionsVN.MaterialType.ACACIA)
        {
        	if (block == Blocks.log || block == Blocks.log2)   {return new Object[]{Blocks.log2, (meta/4)*4 + 0};}
        	if (block == Blocks.planks)                        {return new Object[]{Blocks.planks, 4};}
        	if (block == Blocks.fence)                         {return new Object[]{ModObjects.chooseModFence(4), 0};}
        	if (block == Blocks.fence_gate)                    {return new Object[]{ModObjects.chooseModFenceGate(4), 0};}
        	if (block == Blocks.oak_stairs)                    {return new Object[]{Blocks.acacia_stairs, meta};}
        	if (block == Blocks.wooden_slab)                   {return new Object[]{Blocks.wooden_slab, meta==0? 0 +4: meta==8? 8 +4 : meta};}
        	if (block == Blocks.double_wooden_slab)            {return new Object[]{Blocks.double_wooden_slab, 4};}
        }
        if (startPiece.materialType == FunctionsVN.MaterialType.DARK_OAK)
        {
        	if (block == Blocks.log || block == Blocks.log2)   {return new Object[]{Blocks.log2, (meta/4)*4 + 1};}
        	if (block == Blocks.planks)                        {return new Object[]{Blocks.planks, 5};}
        	if (block == Blocks.fence)                         {return new Object[]{ModObjects.chooseModFence(5), 0};}
        	if (block == Blocks.fence_gate)                    {return new Object[]{ModObjects.chooseModFenceGate(5), 0};}
        	if (block == Blocks.oak_stairs)                    {return new Object[]{Blocks.dark_oak_stairs, meta};}
        	if (block == Blocks.wooden_slab)                   {return new Object[]{Blocks.wooden_slab, meta==0? 0 +5: meta==8? 8 +5 : meta};}
        	if (block == Blocks.double_wooden_slab)            {return new Object[]{Blocks.double_wooden_slab, 5};}
        }
        if (startPiece.materialType == FunctionsVN.MaterialType.SAND)
        {
        	if (block == Blocks.log || block == Blocks.log2)   {return new Object[]{Blocks.sandstone, 2};} // Cut sandstone
        	if (block == Blocks.cobblestone)                   {return new Object[]{Blocks.sandstone, 0};} // Regular sandstone
        	if (block == Blocks.planks)                        {return new Object[]{Blocks.planks, 3};} // Jungle planks
        	if (block == Blocks.fence)                         {return new Object[]{ModObjects.chooseModFence(3), 0};} // Jungle fence
        	if (block == Blocks.fence_gate)                    {return new Object[]{ModObjects.chooseModFenceGate(3), 0};} // Jungle fence gate
        	if (block == Blocks.oak_stairs)                    {return new Object[]{Blocks.jungle_stairs, meta};}
        	if (block == Blocks.stone_stairs)                  {return new Object[]{Blocks.sandstone_stairs, meta};}
        	if (block == Blocks.gravel)                        {return new Object[]{Blocks.sandstone, 0};}
        	if (block == Blocks.dirt)                          {return new Object[]{Blocks.sand, 0};}
        	if (block == Blocks.wooden_slab)                   {return new Object[]{Blocks.wooden_slab, meta==0? 0 +3: meta==8? 8 +3 : meta};} // Jungle slab
        	if (block == Blocks.double_wooden_slab)            {return new Object[]{Blocks.double_wooden_slab, 3};} // Jungle double slab
        }
        if (startPiece.materialType == FunctionsVN.MaterialType.MESA)
        {
        	//if (block == Blocks.log || block == Blocks.log2)   {return new Object[]{Blocks.hardened_clay, 0};} // No log change
        	if (block == Blocks.cobblestone)                   {return new Object[]{Blocks.hardened_clay, 0};} // TODO - change stain color with biome
        	if (block == Blocks.stone_stairs)                  {return new Object[]{Blocks.brick_stairs, meta};}
        	if (block == Blocks.gravel)                        {return new Object[]{Blocks.hardened_clay, 0};}
        	if (block == Blocks.dirt)                          {return new Object[]{Blocks.clay, 0};}
        }
        if (startPiece.materialType == FunctionsVN.MaterialType.SNOW)
        {
        	if (block == Blocks.log || block == Blocks.log2)   {return new Object[]{Blocks.packed_ice, 0};}
        	if (block == Blocks.cobblestone)                   {return new Object[]{Blocks.packed_ice, 0};}
        	if (block == Blocks.planks)                        {return new Object[]{Blocks.snow, 0};}
        	if (block == Blocks.fence)                         {return new Object[]{ModObjects.chooseModFence(1), 0};} // Spruce fence
        	if (block == Blocks.fence_gate)                    {return new Object[]{ModObjects.chooseModFenceGate(1), 0};} // Spruce fence
        	if (block == Blocks.oak_stairs)                    {return new Object[]{Blocks.spruce_stairs, meta};}
        	if (block == Blocks.gravel)                        {return new Object[]{Blocks.packed_ice, 0};}
        	if (block == Blocks.dirt)                          {return new Object[]{Blocks.snow, 0};}
        	if (block == Blocks.wooden_slab)                   {return new Object[]{Blocks.wooden_slab, meta==0? 0 +1: meta==8? 8 +1 : meta};} // Spruce slab
        	if (block == Blocks.double_wooden_slab)            {return new Object[]{Blocks.double_wooden_slab, 1};} // Spruce double slab
        }
        if (startPiece.materialType == FunctionsVN.MaterialType.MUSHROOM)
        {
        	if (block == Blocks.log || block == Blocks.log2)   {return new Object[]{Blocks.brown_mushroom_block, 15};} // Stem on all six sides
        	if (block == Blocks.cobblestone)                   {return new Object[]{Blocks.brown_mushroom_block, 15};} // Cap on all six sides
        	if (block == Blocks.planks)                        {return new Object[]{Blocks.brown_mushroom_block, 0};} // Pores on all six sides
        }
        
        // Post Forge event
        BiomeEvent.GetVillageBlockID event = new BiomeEvent.GetVillageBlockID(startPiece == null ? null : startPiece.biome, block, meta);
        MinecraftForge.TERRAIN_GEN_BUS.post(event);
        if (event.getResult() == Result.DENY) return new Object[]{event.replacement, meta};
        
        return new Object[]{block, meta};
    }
    
    /**
     * Used to determine what path block to place into the world
     * Returns the height at which the block was placed
     */
    protected static int setPathSpecificBlock(World world, StructureVillagePiecesVN.StartVN startPiece, int meta, int posX, int posY, int posZ)
    {
    	int seaLevel = 63; //TODO - actually call sea level in later versions
    	
    	Block blockToReplace = world.getBlock(posX, posY, posZ);
    	
    	Object[] grassPath = getBiomeSpecificBlock(ModObjects.chooseModPathBlock(), 0, startPiece);
    	Object[] planks = getBiomeSpecificBlock(Blocks.planks, 0, startPiece);
    	Object[] gravel = getBiomeSpecificBlock(Blocks.gravel, 0, startPiece);
    	Object[] cobblestone = getBiomeSpecificBlock(Blocks.cobblestone, 0, startPiece);
    	
    	if (posY < seaLevel) {posY = seaLevel-1;}
    	
    	while (posY >= seaLevel-1)
    	{
    		Block surfaceBlock = world.getBlock(posX, posY, posZ);
    		
    		// Replace grass with grass path
    		if (surfaceBlock instanceof BlockGrass && world.isAirBlock(posX, posY+1, posZ))
    		{
    			world.setBlock(posX, posY, posZ, (Block)grassPath[0], (Integer)grassPath[1], 2);
    			return posY;
    		}
    		
    		// Replace liquid with planks. Let's hope this isn't liquid you dumb banana
    		if (surfaceBlock.getMaterial().isLiquid())
    		{
    			world.setBlock(posX, posY, posZ, (Block)planks[0], (Integer)planks[1], 2);
    			return posY;
    		}
    		
    		// Replace sand or standstone with reinforced gravel
    		if (surfaceBlock instanceof BlockSand || surfaceBlock instanceof BlockSandStone)
    		{
    			world.setBlock(posX, posY, posZ, (Block)gravel[0], (Integer)gravel[1], 2);
    			world.setBlock(posX, posY-1, posZ, (Block)cobblestone[0], (Integer)cobblestone[1], 2);
    			return posY;
    		}
    		
    		posY -=1;
    	}
		return -1;
    }
    
    /**
     * Contracts bounding box by amount specified in X, Y, Z
     */
    public static StructureBoundingBox contractBB(StructureBoundingBox structureBoundingBox, int xAmount, int yAmount, int zAmount)
    {
    	return new StructureBoundingBox(
    			structureBoundingBox.minX+MathHelper.abs_int(xAmount),
    			structureBoundingBox.minY+MathHelper.abs_int(yAmount),
    			structureBoundingBox.minZ+MathHelper.abs_int(zAmount),
    			structureBoundingBox.maxX-MathHelper.abs_int(xAmount),
    			structureBoundingBox.maxY-MathHelper.abs_int(yAmount),
    			structureBoundingBox.maxZ-MathHelper.abs_int(zAmount)
    			);
    }
    
    /**
     * Contracts bounding box by specified X, Z amount
     */
    public static StructureBoundingBox contractBB(StructureBoundingBox structureBoundingBox, int xAmount, int zAmount)
    {
    	return contractBB(structureBoundingBox, xAmount, 0, zAmount);
    }
    
    /**
     * Contracts bounding box by specified amount in all dimensions
     */
    public static StructureBoundingBox contractBB(StructureBoundingBox structureBoundingBox, int amount)
    {
    	return contractBB(structureBoundingBox, amount, amount, amount);
    }
    
    
    
    // --------------------------------- //    
    // --- Structure components here --- //
    // --------------------------------- //
    
    // --- General --- //
    
    // Start
    public static class StartVN extends StructureVillagePieces.Start
    {
    	// Set them to defaults here
    	FunctionsVN.VillageType villageType = FunctionsVN.VillageType.NULL;
    	FunctionsVN.MaterialType materialType = FunctionsVN.MaterialType.NULL;
    	Block biomeCobblestoneBlock; int biomeCobblestoneMeta;
    	Block biomePlankBlock; int biomePlankMeta;
    	Block biomeGravelBlock; int biomeGravelMeta;
    	Block biomeFenceBlock; Block biomeFenceGateBlock;
    	
        public StartVN() {}

        public StartVN(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, int terrainType)
        {
            super(chunkManager, componentType, random, posX, posZ, components, terrainType);
            
            // Set biome tags
		    this.villageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, posX, posZ);
	    	this.materialType = FunctionsVN.MaterialType.getMaterialTemplateForBiome(chunkManager, posX, posZ);
		    
            // Establish generic building materials
        	Object[] blockObject;
        	blockObject = getBiomeSpecificBlock(Blocks.cobblestone, 0, this); this.biomeCobblestoneBlock = (Block)blockObject[0]; this.biomeCobblestoneMeta = (Integer)blockObject[1];
        	blockObject = getBiomeSpecificBlock(Blocks.planks, 0, this); this.biomePlankBlock = (Block)blockObject[0]; this.biomePlankMeta = (Integer)blockObject[1];
        	blockObject = getBiomeSpecificBlock(Blocks.gravel, 0, this); this.biomeGravelBlock = (Block)blockObject[0]; this.biomeGravelMeta = (Integer)blockObject[1];
        	blockObject = getBiomeSpecificBlock(Blocks.fence, 0, this); this.biomeFenceBlock = (Block)blockObject[0];
        	blockObject = getBiomeSpecificBlock(Blocks.fence_gate, 0, this); this.biomeFenceGateBlock = (Block)blockObject[0];
        }
    }
    
    // Path
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
                        
                        setPathSpecificBlock(world, (StartVN)startPiece_reflected, 0, i, k, j);
                    }
                }
            }

            return true;
        }
    }
    
    
    
    
    // --- Plains --- //
    
    // Well
    public static class PlainsMeetingPoint1 extends StartVN
    {
    	int xoffset = 2; int zoffset = 2; // Offsets from vanilla

	    int wellHeight = 4+1;
	    int wellTop = 78+1; 
	    int wellDepthDecrease=7;
	    
		public PlainsMeetingPoint1() {}
		
		public PlainsMeetingPoint1(WorldChunkManager chunkManager, int componentType, Random random, int posX, int posZ, List components, int terrainType)
		{
		    super(chunkManager, componentType, random, posX, posZ, components, terrainType);
		    
	    	if (GeneralConfig.debugMessages) {LogHelper.info("Village type: " + this.villageType + ", material type: " + this.materialType);}
	    	
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
        public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBB)
        {
        	if (this.field_143015_k < 0)
            {
                //this.field_143015_k = getMedianGroundLevel(world, structureBB, true);//this.getAverageGroundLevel(world, structureBoundingBox);
        		this.field_143015_k = getMedianGroundLevel(world,
        				new StructureBoundingBox(
        						this.boundingBox.minX, this.boundingBox.minZ,
        						this.boundingBox.maxX, this.boundingBox.maxZ), // Set the bounding box version as this bounding box but with Y going from 0 to 512
        				true);
        		
                if (GeneralConfig.debugMessages)
                {
                	LogHelper.info("Average ground level for well: " + this.field_143015_k + " at " + (this.boundingBox.minX+this.boundingBox.maxX)/2 + " " + (this.boundingBox.minZ+this.boundingBox.maxZ)/2);
                }
                
                if (this.field_143015_k < 0) {return true;} // Do not construct a well in a void

                this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + (wellHeight-1) - wellDepthDecrease, 0);
            }
            
            // The well gets filled completely with water first
            //this.fillWithBlocks(world, structureBoundingBox, 1+xoffset, 0+wellDepthDecrease, 1+zoffset, 4+xoffset, 12, 4+zoffset, this.biomeCobblestoneBlock, Blocks.flowing_water, false);
            this.fillWithMetadataBlocks(world, structureBB, 1+xoffset, 0+wellDepthDecrease, 1+zoffset, 4+xoffset, 12, 4+zoffset, this.biomeCobblestoneBlock, this.biomeCobblestoneMeta, this.biomeCobblestoneBlock, this.biomeCobblestoneMeta, false);
            this.fillWithBlocks(world, structureBB, 2+xoffset, 1+wellDepthDecrease, 2+zoffset, 3+xoffset, 12, 3+zoffset, Blocks.flowing_water, Blocks.flowing_water, false); // Water
            
            // I believe this replaces the top water level with air
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 2+xoffset, 12, 2+zoffset, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 3+xoffset, 12, 2+zoffset, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 2+xoffset, 12, 3+zoffset, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.air, 0, 3+xoffset, 12, 3+zoffset, structureBB);
            
            // Well support posts
            for (int i : new int[]{1, 4})
            {
                for (int j : new int[]{1, 4})
                {
                	this.fillWithBlocks(world, structureBB, i+xoffset, 13, j+zoffset, i+xoffset, 14, j+zoffset, this.biomeFenceBlock, this.biomeFenceBlock, false);
                }
            }
            
            // Roof of the well
            this.fillWithMetadataBlocks(world, structureBB, 1+xoffset, 15, 1+zoffset, 4+xoffset, 15, 4+zoffset, this.biomeCobblestoneBlock, this.biomeCobblestoneMeta, this.biomeCobblestoneBlock, this.biomeCobblestoneMeta, false);
            
            // Line the well with cobblestone and ensure the spaces above are clear
            for (int i = 0; i <= 5; ++i)
            {
                for (int j = 0; j <= 5; ++j)
                {
                    if (j == 0 || j == 5 || i == 0 || i == 5)
                    {
                    	this.placeBlockAtCurrentPosition(world, this.biomeCobblestoneBlock, this.biomeCobblestoneMeta, j+xoffset, 11, i+zoffset, structureBB);
                    	this.fillWithMetadataBlocks(world, structureBB, j+xoffset, 0+wellDepthDecrease, i+zoffset, j+xoffset, 11, i+zoffset, this.biomeCobblestoneBlock, this.biomeCobblestoneMeta, this.biomeCobblestoneBlock, this.biomeCobblestoneMeta, false);
                        this.clearCurrentPositionBlocksUpwards(world, j+xoffset, 12, i+zoffset, structureBB);
                    }
                }
            }
            
            // Over-lid torches
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 1+xoffset, 16, 1+zoffset, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 1+xoffset, 16, 4+zoffset, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 4+xoffset, 16, 1+zoffset, structureBB);
            this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 4+xoffset, 16, 4+zoffset, structureBB);
            
            // Encircle the well with path
            Block pathBlock = ModObjects.chooseModPathBlock();
        	StructureVillagePieces.Start startPiece_reflected = ReflectionHelper.getPrivateValue(StructureVillagePieces.Village.class, this, new String[]{"startPiece"});
        	for (int i = 1; i <= 8; ++i)
            {
                for (int j = 1; j <= 8; ++j)
                {
                    if (j == 1 || j == 8 || i == 1 || i == 8)
                    {
                    	// Gets ground level, so long as it's not leaves or other foliage
                        //int k = world.getTopSolidOrLiquidBlock(this.getBoundingBox().minX+i, this.getBoundingBox().minZ+j) - 1;
                    	int k = world.getTopSolidOrLiquidBlock(this.getXWithOffset(i, j), this.getZWithOffset(i, j)) - 1;
                        if (k > -1)
                        {
                        	setPathSpecificBlock(world, this, 0, this.getBoundingBox().minX+i, k, this.getBoundingBox().minZ+j);
                        	this.clearCurrentPositionBlocksUpwards(world, i, k+1, j, structureBB);
                       	}
                    }
                }
            }
            // Add path nodules at the end
            for (int i : new int[]{3,4,5,6})
            {
            	for (int j : new int[]{0,9})
            	{
                    //int k = world.getTopSolidOrLiquidBlock(this.getBoundingBox().minX+i, this.getBoundingBox().minZ+j) - 1;
            		int k = world.getTopSolidOrLiquidBlock(this.getXWithOffset(i, j), this.getZWithOffset(i, j)) - 1;
                    if (k > -1)
                    {
                    	//setPathSpecificBlock(world, this, 0, this.getBoundingBox().minX+i, k, this.getBoundingBox().minZ+j);
                    	setPathSpecificBlock(world, this, 0, this.getXWithOffset(i, j), k, this.getZWithOffset(i, j));
                    	this.clearCurrentPositionBlocksUpwards(world, i, k+1, j, structureBB);
                   	}
                    
                    //k = world.getTopSolidOrLiquidBlock(this.getBoundingBox().minX+j, this.getBoundingBox().minZ+i) - 1;
                    k = world.getTopSolidOrLiquidBlock(this.getXWithOffset(j, i), this.getZWithOffset(j, i)) - 1;
                    if (k > -1)
                    {
                    	//setPathSpecificBlock(world, this, 0, this.getBoundingBox().minX+j, k, this.getBoundingBox().minZ+i);
                    	setPathSpecificBlock(world, this, 0, this.getXWithOffset(j, i), k, this.getZWithOffset(j, i));
                    	this.clearCurrentPositionBlocksUpwards(world, j, k+1, i, structureBB);
                   	}
            	}
            }
            
            return true;
        }
    }
    
}
