package astrotibs.villagenames.igloo;

import java.util.Random;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.ieep.ExtendedZombieVillager;
import astrotibs.villagenames.igloo.utils.Rotation;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.utility.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraftforge.common.ChestGenHooks;

public class VNComponentIglooPieces
{
    public static void registerScatteredFeaturePieces()
    {
        MapGenStructureIO.func_143031_a(VNComponentIglooPieces.Igloo.class, "Iglu");
    }
    
    
    abstract static class Feature extends StructureComponent
        {
            /** The size of the bounding box for this feature in the X axis */
            protected int scatteredFeatureSizeX;
            /** The size of the bounding box for this feature in the Y axis */
            protected int scatteredFeatureSizeY;
            /** The size of the bounding box for this feature in the Z axis */
            protected int scatteredFeatureSizeZ;
            protected int field_74936_d = -1;

            public Feature() {}

            protected Feature(Random rand, int x, int y, int z, int sizeX, int sizeY, int sizeZ)
            {
                super(0);
                this.scatteredFeatureSizeX = sizeX;
                this.scatteredFeatureSizeY = sizeY;
                this.scatteredFeatureSizeZ = sizeZ;
                this.coordBaseMode = (rand.nextInt(4)+2+  
                		
                		1 // This value will guarantee a particular coordbasemode for the test
                		
                		)%4; // In 1.9 is: this.setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(rand));

                switch (this.coordBaseMode) // In 1.9 is: (this.getCoordBaseMode().getAxis() == EnumFacing.Axis.Z)
                {
                    case 0: // North
                    case 2: // South
                        this.boundingBox = new StructureBoundingBox(x, y, z, x + sizeX - 3, y + sizeY - 1, z + sizeZ - 3);
                        break;
                    default: // 1: East; 3: West
                        this.boundingBox = new StructureBoundingBox(x, y, z, x + sizeZ - 3, y + sizeY - 1, z + sizeX - 3);
                }
            }

            protected void func_143012_a(NBTTagCompound tagCompound) // 1.9's writeStructureToNBT
            {
                tagCompound.setInteger("Width", this.scatteredFeatureSizeX);
                tagCompound.setInteger("Height", this.scatteredFeatureSizeY);
                tagCompound.setInteger("Depth", this.scatteredFeatureSizeZ);
                tagCompound.setInteger("HPos", this.field_74936_d);
            }

            protected void func_143011_b(NBTTagCompound tagCompound) // 1.9's readStructureFromNBT
            {
                this.scatteredFeatureSizeX = tagCompound.getInteger("Width");
                this.scatteredFeatureSizeY = tagCompound.getInteger("Height");
                this.scatteredFeatureSizeZ = tagCompound.getInteger("Depth");
                this.field_74936_d = tagCompound.getInteger("HPos");
            }

            protected boolean func_74935_a(World worldIn, StructureBoundingBox structurebb, int yOffset) // In 1.9: offsetToAverageGroundLevel()
            {
                if (this.field_74936_d >= 0)
                {
                    return true;
                }
                else
                {
                    int j = 0;
                    int k = 0;
                    
                    // Below is essentially the same, except uses BlockPos
                    
                    for (int l = this.boundingBox.minZ; l <= this.boundingBox.maxZ; ++l)
                    {
                        for (int i1 = this.boundingBox.minX; i1 <= this.boundingBox.maxX; ++i1)
                        {
                            if (structurebb.isVecInside(i1, 64, l))
                            {
                                j += Math.max(worldIn.getTopSolidOrLiquidBlock(i1, l), worldIn.provider.getAverageGroundLevel());
                                ++k;
                            }
                        }
                    }

                    if (k == 0)
                    {
                        return false;
                    }
                    else
                    {
                        this.field_74936_d = j / k;
                        this.boundingBox.offset(0, this.field_74936_d - this.boundingBox.minY + yOffset, 0);
                        return true;
                    }
                }
            }
        }

    public static class Igloo extends VNComponentIglooPieces.Feature
        {
           
            // From 1.9
            //private static final ResourceLocation IGLOO_TOP_ID = new ResourceLocation("igloo/igloo_top");
            //private static final ResourceLocation IGLOO_MIDDLE_ID = new ResourceLocation("igloo/igloo_middle");
            //private static final ResourceLocation IGLOO_BOTTOM_ID = new ResourceLocation("igloo/igloo_bottom");
            
            public Igloo() {}

            public Igloo(Random rand, int x, int z)
            {
                super(rand, x, 64, z, 7, 5, 8); // Random, x, y, z, sizeX, sizeY, sizeZ
            }
            
            
            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             * Author: Searge
             * Translated to 1.7/1.8 by: AstroTibs
             */
            public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
            {
                if (!this.func_74935_a(worldIn, structureBoundingBoxIn, -1)) //offsetToAverageGroundLevel()
                {
                    return false;
                }
                else
                {
                	StructureBoundingBox structureboundingbox = this.getBoundingBox();
                	BlockPos blockpos = new BlockPos(structureboundingbox.minX, structureboundingbox.minY, structureboundingbox.minZ);
                	Rotation[] arotation = Rotation.values();
                	
                	
                	// Bounding box stuff to help locate furnace
                	int minX = structureBoundingBoxIn.minX;
                	int minY = structureBoundingBoxIn.minY;
                	int minZ = structureBoundingBoxIn.minZ;
                	int maxX = structureBoundingBoxIn.maxX;
                	int maxY = structureBoundingBoxIn.maxY;
                	int maxZ = structureBoundingBoxIn.maxZ;
                    
                    
                	int xOffset = -1;
                    int zOffset = 0;
                    
                	
                	// Always add the top (above-ground) part
                	
                	// Bottom
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2+xOffset,0,2, 6+xOffset,0,6, Blocks.snow, Blocks.snow, false); // Floor of igloo
                    // Walls
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3+xOffset,0,1, 5+xOffset,2,1, Blocks.snow, Blocks.snow, false); // North wall
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3+xOffset,0,7, 5+xOffset,2,7, Blocks.snow, Blocks.snow, false); // South wall
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 7+xOffset,0,3, 7+xOffset,2,5, Blocks.snow, Blocks.snow, false); // East wall
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1+xOffset,0,3, 1+xOffset,2,5, Blocks.snow, Blocks.snow, false); // West wall
                    // Corners
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2+xOffset,0,2, 2+xOffset,2,2, Blocks.snow, Blocks.snow, false); // NW corner
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6+xOffset,0,2, 6+xOffset,2,2, Blocks.snow, Blocks.snow, false); // NE corner
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6+xOffset,0,6, 6+xOffset,2,6, Blocks.snow, Blocks.snow, false); // SE corner
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2+xOffset,0,6, 2+xOffset,2,6, Blocks.snow, Blocks.snow, false); // SW corner
                    // Ceiling "supports"
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3+xOffset,3,2, 5+xOffset,3,2, Blocks.snow, Blocks.snow, false); // North beam
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3+xOffset,3,6, 5+xOffset,3,6, Blocks.snow, Blocks.snow, false); // South beam
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6+xOffset,3,3, 6+xOffset,3,5, Blocks.snow, Blocks.snow, false); // East beam
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2+xOffset,3,3, 2+xOffset,3,5, Blocks.snow, Blocks.snow, false); // West beam
                    // Ceiling proper
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3+xOffset,4,3, 5+xOffset,4,5, Blocks.snow, Blocks.snow, false); // Floor of igloo
                    
                    // Hollow out the center
                    this.fillWithAir(worldIn, structureBoundingBoxIn, 3+xOffset,1,2, 5+xOffset,2,6);
                    this.fillWithAir(worldIn, structureBoundingBoxIn, 2+xOffset,1,3, 2+xOffset,2,5);
                    this.fillWithAir(worldIn, structureBoundingBoxIn, 6+xOffset,1,3, 6+xOffset,2,5);
                    this.fillWithAir(worldIn, structureBoundingBoxIn, 3+xOffset,3,3, 5+xOffset,3,5);
                    
                    // Add interior
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3+xOffset,1,3, 5+xOffset,1,5, Blocks.carpet, Blocks.carpet, false); // white carpet
                    
                    // Door
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3+xOffset,0,0, 5+xOffset,2,0, Blocks.snow, Blocks.snow, false);
                	this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4+xOffset,3,0, 4+xOffset,3,1, Blocks.snow, Blocks.snow, false);
                	this.fillWithAir   (worldIn, structureBoundingBoxIn, 4+xOffset,1,0, 4+xOffset,2,1);
                	// carpet head
                	this.fillWithMetadataBlocks(worldIn, structureBoundingBoxIn, 3+xOffset,1,6, 5+xOffset,1,6, Blocks.carpet, 8, Blocks.carpet, 8, false);
                	// Bed
                	this.placeBlockAtCurrentPosition(worldIn, Blocks.bed, this.coordBaseMode+8, (this.coordBaseMode<2?6:2)+xOffset, 1, 5, structureBoundingBoxIn); // Head
                	this.placeBlockAtCurrentPosition(worldIn, Blocks.bed, this.coordBaseMode,   (this.coordBaseMode<2?6:2)+xOffset, 1, 4, structureBoundingBoxIn); // Foot
                	// Wall items
                	this.placeBlockAtCurrentPosition(worldIn, Blocks.crafting_table, 0, (this.coordBaseMode<2?2:6)+xOffset, 1, 5, structureBoundingBoxIn);
                	this.placeBlockAtCurrentPosition(worldIn, Blocks.redstone_torch, 5, (this.coordBaseMode<2?2:6)+xOffset, 1, 4, structureBoundingBoxIn);
                	this.placeBlockAtCurrentPosition(worldIn, Blocks.furnace, (new int[]{5,3,4,2})[this.coordBaseMode], (this.coordBaseMode<2?2:6)+xOffset, 1, 3, structureBoundingBoxIn);
                	// Have to manually set metadata to get it to stick
                    worldIn.setBlockMetadataWithNotify(this.getXWithOffset((this.coordBaseMode<2?2:6)+xOffset, 3), this.getYWithOffset(1), this.getZWithOffset((this.coordBaseMode<2?2:6)+xOffset, 3), (new int[]{5,3,4,2})[this.coordBaseMode], 2);
                	
                	// Blank out the space next to the furnace to ensure it will face the right direction
                	//this.placeBlockAtCurrentPosition(worldIn, Blocks.furnace, 0, (this.coordBaseMode<2?2:6)+xOffset, 1, 3, structureBoundingBoxIn);
                	//this.placeBlockAtCurrentPosition(worldIn, Blocks.air, 0, (this.coordBaseMode<2?2:6)+xOffset, 1, 4, structureBoundingBoxIn);
                	
                	//this.block
                	// Ice windows
                	this.placeBlockAtCurrentPosition(worldIn, Blocks.ice, 0, 1+xOffset, 1, 4, structureBoundingBoxIn);
                	this.placeBlockAtCurrentPosition(worldIn, Blocks.ice, 0, 7+xOffset, 1, 4, structureBoundingBoxIn);
                    
                	
                	Block blocktoscan;
                	int   metatoscan  = -1;
                	
                    if (randomIn.nextDouble() < 0.5D) // Add an underground part!
                    {
                    	
                    	// Hatch
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.trapdoor, (new int[]{8,11,9,10})[this.coordBaseMode], 4+xOffset, 0, 5, structureBoundingBoxIn);
                    	
                    	// How deep should the column be
                    	int basementCeilingY= -2 - ((((randomIn.nextInt(8) + 4)+9)%12)*3);
                    	
                    	
                    	// ---------------- //
                    	// --- BASEMENT --- //
                    	// ---------------- //
                    	
                    	
                    	// Hollow out basement
                    	this.fillWithAir(worldIn, structureBoundingBoxIn,
                    			2+xOffset,basementCeilingY-3,1,
                    			6+xOffset,basementCeilingY-1,5
                    			);
                    	
                    	// Basement ceiling
                    	this.fillWithBlocks(worldIn, structureBoundingBoxIn,
                    			2+xOffset,basementCeilingY,1,
                    			6+xOffset,basementCeilingY,5,
                    			Blocks.stonebrick, Blocks.stonebrick, false);
                    	
                    	// Portion behind ladder column
                    	this.fillWithBlocks(worldIn, structureBoundingBoxIn,
                    			3+xOffset,basementCeilingY,0,
                    			5+xOffset,basementCeilingY,0,
                    			Blocks.stonebrick, Blocks.stonebrick, false);
                    	
                    	// Front wall
                    	this.fillWithBlocks(worldIn, structureBoundingBoxIn,
                    			2+xOffset,basementCeilingY-2,6,
                    			6+xOffset,basementCeilingY-1,6,
                    			Blocks.stonebrick, Blocks.stonebrick, false);
                    	// Front wall moss bottom
                    	this.fillWithMetadataBlocks(worldIn, structureBoundingBoxIn,
                    			2+xOffset,basementCeilingY-3,6,
                    			6+xOffset,basementCeilingY-3,6,
                    			Blocks.stonebrick, 1, Blocks.stonebrick, 1, false);
                    	
                    	// Left wall
                    	this.fillWithBlocks(worldIn, structureBoundingBoxIn,
                    			1+xOffset,basementCeilingY-2,1,
                    			1+xOffset,basementCeilingY-1,5,
                    			Blocks.stonebrick, Blocks.stonebrick, false);
                    	// Left wall moss bottom
                    	this.fillWithMetadataBlocks(worldIn, structureBoundingBoxIn,
                    			1+xOffset,basementCeilingY-3,1,
                    			1+xOffset,basementCeilingY-3,5,
                    			Blocks.stonebrick, 1, Blocks.stonebrick, 1, false);
                    	
                    	// Right wall
                    	this.fillWithBlocks(worldIn, structureBoundingBoxIn,
                    			7+xOffset,basementCeilingY-2,1,
                    			7+xOffset,basementCeilingY-1,5,
                    			Blocks.stonebrick, Blocks.stonebrick, false);
                    	// Right wall moss bottom
                    	this.fillWithMetadataBlocks(worldIn, structureBoundingBoxIn,
                    			7+xOffset,basementCeilingY-3,1,
                    			7+xOffset,basementCeilingY-3,5,
                    			Blocks.stonebrick, 1, Blocks.stonebrick, 1, false);
                    	
                    	// Basement floor
                    	this.fillWithBlocks(worldIn, structureBoundingBoxIn,
                    			2+xOffset,basementCeilingY-4,1,
                    			6+xOffset,basementCeilingY-4,5,
                    			Blocks.stonebrick, Blocks.stonebrick, false);
                    	
                    	
                    	
                    	
                    	// ------------ //
                    	// --- CELL --- //
                    	// ------------ //
                    	
                    	// Cell ceiling
                    	this.fillWithBlocks(worldIn, structureBoundingBoxIn,
                    			3+xOffset,basementCeilingY,-1,
                    			5+xOffset,basementCeilingY,0,
                    			Blocks.stonebrick, Blocks.stonebrick, false);
                    	
                    	// Right cell wall
                    	this.fillWithBlocks(worldIn, structureBoundingBoxIn,
                    			6+xOffset,basementCeilingY-3,-2,
                    			6+xOffset,basementCeilingY-1,0,
                    			Blocks.stonebrick, Blocks.stonebrick, false);
                    	
                    	// Left cell wall
                    	this.fillWithBlocks(worldIn, structureBoundingBoxIn,
                    			2+xOffset,basementCeilingY-3,-2,
                    			2+xOffset,basementCeilingY-1,0,
                    			Blocks.stonebrick, Blocks.stonebrick, false);
                    	
                    	// Back cell wall
                    	this.fillWithBlocks(worldIn, structureBoundingBoxIn,
                    			3+xOffset,basementCeilingY-3,-2,
                    			5+xOffset,basementCeilingY-1,-2,
                    			Blocks.stonebrick, Blocks.stonebrick, false);
                    	
                    	// Hollow out cell
                    	this.fillWithAir(worldIn, structureBoundingBoxIn,
                    			3+xOffset,basementCeilingY-3,-1,
                    			5+xOffset,basementCeilingY-1,0
                    			);
                    	
                    	// Cell top lip
                    	this.fillWithBlocks(worldIn, structureBoundingBoxIn,
                    			3+xOffset,basementCeilingY-1,0,
                    			5+xOffset,basementCeilingY-1,0,
                    			Blocks.stonebrick, Blocks.stonebrick, false);
                    	
                    	// Cell floor
                    	this.fillWithBlocks(worldIn, structureBoundingBoxIn,
                    			3+xOffset,basementCeilingY-4,-1,
                    			5+xOffset,basementCeilingY-4,0,
                    			Blocks.stonebrick, Blocks.stonebrick, false);
                    	
                    	// Cell moss bottom
                    	this.fillWithMetadataBlocks(worldIn, structureBoundingBoxIn,
                    			2+xOffset,basementCeilingY-3,0,
                    			6+xOffset,basementCeilingY-3,0,
                    			Blocks.stonebrick, 1, Blocks.stonebrick, 1, false);
                    	
                    	// Bars
                    	this.fillWithBlocks(worldIn, structureBoundingBoxIn,
                    			3+xOffset,basementCeilingY-3,0,
                    			5+xOffset,basementCeilingY-2,0,
                    			Blocks.iron_bars, Blocks.iron_bars, false);
                    	
                    	// Cell divider
                    	this.fillWithBlocks(worldIn, structureBoundingBoxIn,
                    			4+xOffset,basementCeilingY-3,-1,
                    			4+xOffset,basementCeilingY-1,-1,
                    			Blocks.stonebrick, Blocks.stonebrick, false);
                    	// Mossy divider bottom
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.stonebrick, 1, 4+xOffset,basementCeilingY-3,0, structureBoundingBoxIn); // Left of brewing stand
                    	
                    	
                    	
                    	// ---------------- //
                    	// --- INTERIOR --- //
                    	// -- DECORATIONS --//
                    	// ---------------- //

                    	// Torches beside the ladder
                    	for (int i: new int[]{-1,1}) {
                    		this.placeBlockAtCurrentPosition(worldIn, Blocks.torch, (new int[]{4,1,3,2})[this.coordBaseMode], 4+i+xOffset,basementCeilingY-1,5, structureBoundingBoxIn);
                    	}
                    	// Torch between the cells
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.torch, (new int[]{3,2,4,1})[this.coordBaseMode], 4+xOffset,basementCeilingY-1,1, structureBoundingBoxIn);
                    	
                    	// Cobweb
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.web, (new int[]{3,2,4,1})[this.coordBaseMode],
                    			(this.coordBaseMode<2?2:6)+xOffset,basementCeilingY-1,1, structureBoundingBoxIn);
                    	
                    	// Sign
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.wall_sign, (new int[]{3,4,2,5})[this.coordBaseMode], 4+xOffset,basementCeilingY-2,1, structureBoundingBoxIn);
                    	
                    	
                    	// Alchemy Table
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.spruce_stairs, (new int[]{6,5,7,4})[this.coordBaseMode], (this.coordBaseMode<2?6:2)+xOffset,basementCeilingY-3,5, structureBoundingBoxIn);
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.wooden_slab, 9, (this.coordBaseMode<2?6:2)+xOffset,basementCeilingY-3,4, structureBoundingBoxIn);
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.spruce_stairs, (new int[]{7,4,6,5})[this.coordBaseMode], (this.coordBaseMode<2?6:2)+xOffset,basementCeilingY-3,3, structureBoundingBoxIn);
                    	// Brewing stand
                    	this.placeBlockAtCurrentPosition(worldIn, ModObjects.chooseModBrewingStandBlock(), 0, (this.coordBaseMode<2?6:2)+xOffset,basementCeilingY-2,4, structureBoundingBoxIn);
                    	
                    	// clay pot
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.flower_pot, 9, (this.coordBaseMode<2?6:2)+xOffset,basementCeilingY-2,5, structureBoundingBoxIn); // 9 is "cactus"
                    	
                    	// Cauldron
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.cauldron, 2, (this.coordBaseMode<2?6:2)+xOffset,basementCeilingY-3,2, structureBoundingBoxIn);
                    	
                    	// Chest
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.chest, (new int[]{5,3,4,2})[this.coordBaseMode], (this.coordBaseMode<2?2:6)+xOffset,basementCeilingY-3,4, structureBoundingBoxIn);
                    	
                    	// Floor mat
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.carpet, 14, (this.coordBaseMode<2?2:6)+xOffset,basementCeilingY-3,2, structureBoundingBoxIn);
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.carpet, 14, (this.coordBaseMode<2?2:6)+xOffset,basementCeilingY-3,1, structureBoundingBoxIn);
                    	
                    	
                    	
                    	// ------------------ //
                    	// ----- STONE ------ //
                    	// -- REPLACEMENTS -- //
                    	// ------------------ //
                    	
                    	// The nine chiseled stone bricks in the floor
                    	for (int chiseledX: new int[]{2,4,6}) {
                    		for (int chiseledZ: new int[]{5, 3, 1}) {
                    			this.placeBlockAtCurrentPosition(worldIn, Blocks.stonebrick, 3, chiseledX+xOffset,basementCeilingY-4,chiseledZ, structureBoundingBoxIn);
                    		}
                    	}
                    	
                    	// Cracked stone brick positions
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.stonebrick, 2, 4+xOffset,basementCeilingY-2,0, structureBoundingBoxIn); // Behind the sign
                    	for (int i : new int[]{3,5}) {
                    		this.placeBlockAtCurrentPosition(worldIn, Blocks.stonebrick, 2, i+xOffset,basementCeilingY-4,-1, structureBoundingBoxIn); // Floor of a cell
                    	}
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.stonebrick, 2, (this.coordBaseMode<2?1:7)+xOffset,basementCeilingY-2,1, structureBoundingBoxIn); // Above the mat
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.stonebrick, 2, (this.coordBaseMode<2?1:7)+xOffset,basementCeilingY-2,4, structureBoundingBoxIn); // Above the chest
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.stonebrick, 2, 4+xOffset,basementCeilingY-2,6, structureBoundingBoxIn); // Behind the chest
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.stonebrick, 2, (this.coordBaseMode<2?6:2)+xOffset,basementCeilingY-2,6, structureBoundingBoxIn); // Right of brewing stand
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.stonebrick, 2, (this.coordBaseMode<2?7:1)+xOffset,basementCeilingY-2,3, structureBoundingBoxIn); // Left of brewing stand
                    	
                    	// Smooth "Andesite" under the mat
                    	Block andesitePlaceholder = Blocks.double_stone_slab;
                    	int andesitePlaceholderMeta = 0;
                    	
                    	Object[] tryAndesite = ModObjects.chooseModPolishedAndesiteObject();
                    	
                    	if (tryAndesite != null)
                    	{
                    		andesitePlaceholder = (Block) tryAndesite[0];
                    		andesitePlaceholderMeta = (Integer) tryAndesite[1];
                    	}
                    	
                    	
                    	this.placeBlockAtCurrentPosition(worldIn, andesitePlaceholder, andesitePlaceholderMeta, (this.coordBaseMode<2?2:6)+xOffset,basementCeilingY-4,1, structureBoundingBoxIn);
                    	
                    	
                    	// Silverfish eggs
                    	
                    	// Chiseled: center ceiling
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.monster_egg, 5, 4+xOffset,basementCeilingY,3, structureBoundingBoxIn);
                    	// Chiseled: across from the andesite
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.monster_egg, 5, (this.coordBaseMode<2?6:2)+xOffset,basementCeilingY-4,1, structureBoundingBoxIn);
                    	// Mossy: between the ladder and the chest
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.monster_egg, 3, (this.coordBaseMode<2?3:5)+xOffset,basementCeilingY-3,6, structureBoundingBoxIn);
                    	// Brick: in ceiling
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.monster_egg, 2, (this.coordBaseMode<2?3:5)+xOffset,basementCeilingY,4, structureBoundingBoxIn);
                    	// Brick: between the ladder and the chest
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.monster_egg, 2, (this.coordBaseMode<2?2:6)+xOffset,basementCeilingY-2,6, structureBoundingBoxIn);
                    	// Brick: behind the brewing stand
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.monster_egg, 2, (this.coordBaseMode<2?7:1)+xOffset,basementCeilingY-2,4, structureBoundingBoxIn);
                    	// Brick: beneath the brewing stand
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.monster_egg, 2, (this.coordBaseMode<2?6:2)+xOffset,basementCeilingY-4,4, structureBoundingBoxIn);
                    	// Brick: above mat
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.monster_egg, 2, (this.coordBaseMode<2?1:7)+xOffset,basementCeilingY-1,2, structureBoundingBoxIn);
                    	// Brick: next to the mat
                    	this.placeBlockAtCurrentPosition(worldIn, Blocks.monster_egg, 2, (this.coordBaseMode<2?3:5)+xOffset,basementCeilingY-4,2, structureBoundingBoxIn);
                    	
                    	
                    	
                    	// ---------------- //
                    	// --- ENTRANCE --- //
                    	// ---------------- //
                    	
                    	
                    	// Stone column
                    	for (int i: new int[]{0,1,2,3}) {
                   			this.fillWithBlocks(worldIn, structureBoundingBoxIn,
                   					4+xOffset+(new int[]{-1,0,0,1})[i],basementCeilingY,5+(new int[]{0,-1,1,0})[i],
                   					4+xOffset+(new int[]{-1,0,0,1})[i],-1              ,5+(new int[]{0,-1,1,0})[i],
                   					Blocks.stonebrick, Blocks.stonebrick, false);
                    	}
                    	// Ladder
                    	this.fillWithMetadataBlocks(worldIn, structureBoundingBoxIn, 4+xOffset,basementCeilingY-2,5, 4+xOffset,-1,5,
                    			Blocks.ladder, (new int[]{2,5,3,4})[this.coordBaseMode],
                    			Blocks.ladder, (new int[]{2,5,3,4})[this.coordBaseMode],
                    			false);
                    	
                    	
                    	
                    	
                    	// ---------------- //
                    	// --- BASEMENT --- //
                    	// --- CLEANUP ---- //
                    	// ---------------- //
                    	
                    	
                    	this.basementTouchup(worldIn, structureBoundingBoxIn, randomIn, minX, maxX, minZ, maxZ, this.coordBaseMode);
                    	
                    }
                    
                	
                	// ----------------- //
                	// --- TOP-LEVEL --- //
                    // ---- CLEANUP ---- //
                	// ----------------- //
                    
                    // Fix furnace TODO - not sure if this can be done easily...
                    // 1.7.10 does something stupid where the furnace will be automatically re-oriented.
                    
                    /*
                    for (int y = 55; y <= worldIn.getActualHeight(); y++) { // Reasonable span to check for the furnace
                    	for (int x = minX; x <= maxX; x++) {
                    		for (int z = minZ; z <= maxZ; z++) {
                    			if (worldIn.getBlock(x, y, z) == Blocks.crafting_table) {
                    				
                    				// Detected a crafting table. Place the furnace as necessary.
                    				
                    				// Clear out blocks that would have surrounded the furnace
                    				for (int w=0; w <=3; w++) {
                    					worldIn.setBlock(
                        						x + (new int[]{0, 2, 0, -2})[this.coordBaseMode] + (new int[]{-1,0,1,0}[w]),
                        						y,
                        						z + (new int[]{-2, 0, 2, 0})[this.coordBaseMode] + (new int[]{0,1,0,-1}[w]),
                        						//Blocks.furnace, (new int[]{2,5,3,4})[this.coordBaseMode], 2);
                        						Blocks.air, (new int[]{3,4,2,5})[this.coordBaseMode], 2);
                    				}
                    				
                    				// Place the furnace
                    				worldIn.setBlock(
                    						x + (new int[]{0, 2, 0, -2})[this.coordBaseMode],
                    						y,
                    						z + (new int[]{-2, 0, 2, 0})[this.coordBaseMode],
                    						//Blocks.furnace, (new int[]{2,5,3,4})[this.coordBaseMode], 2);
                    						Blocks.furnace, (new int[]{3,4,2,5})[this.coordBaseMode], 2);
                    						
                    			}
                    		}
                    	}
                    }
                    */
                    
                    return true;
                	
                	
                	
                	
                	// Placing the furnace is tricky
                	/*
                	//Block blocktofix;
                	for (int i=minX; i<=maxX; i++) {
                		for (int k=minZ; k<=maxZ; k++) {
                			for (int j=48; j<=71; j++) {
                				if (worldIn.getBlock(i, j, k) == Blocks.furnace) {
                					worldIn.setBlock(i, j, k, Blocks.furnace, this.coordBaseMode==0?3:this.coordBaseMode==3?0:(this.coordBaseMode+2), 2);
                					LogHelper.info("Furnace meta changed to " + (this.coordBaseMode==0?5:this.coordBaseMode==3?2:(this.coordBaseMode+2)) );
                				}
                			}
                		}
                	}
                	*/
                	
                }
            }
            
            
            /**
             * Called to summon the villager and zombie in the igloo basement ,
             * to set the sign text, and to put the potion in the brewing stand.
             */
            protected void basementTouchup(World worldIn, StructureBoundingBox structureBoundingBoxIn, Random randomIn, int minX, int maxX, int minZ, int maxZ, int coordBaseMode) {
            	
            	for (int y = 1; y <= worldIn.getActualHeight(); y++) {
            		for (int x = minX; x <= maxX; x++) {
                		for (int z = minZ; z <= maxZ; z++) {
                			if (worldIn.getBlock(x, y, z) == ModObjects.chooseModBrewingStandBlock()) {
                				
                				
                				// Spawn priest
                				
                                EntityVillager villager = new EntityVillager(worldIn, 2); // 2 is a Priest
                                
                                villager.heal(villager.getMaxHealth());
                                villager.setLocationAndAngles(
                                		(double)(x + (new int[]{-3, 5, 3, -5})[coordBaseMode]) + 0.5D,
                                		(double)y,
                                		(double)(z + (new int[]{-5, -3, 5, 3})[coordBaseMode]) + 0.5D,
                                		0.0F, 0.0F);
                                villager.func_110163_bv(); //.enablePersistence() in 1.8
                                worldIn.spawnEntityInWorld(villager);
                                
                                
                                // Spawn Zombie
                                                                
                                EntityZombie zombie = new EntityZombie(worldIn);
                                zombie.heal(zombie.getMaxHealth());
                                zombie.setLocationAndAngles(
                                		(double)(x + (new int[]{-1, 5, 1, -5})[coordBaseMode]) + 0.5D,
                                		(double)y,
                                		(double)(z + (new int[]{-5, -1, 5, 1})[coordBaseMode]) + 0.5D,
                                		90.0F*this.coordBaseMode, 0.0F);
                                zombie.func_110163_bv(); //.enablePersistence() in 1.8
                                zombie.setVillager(true);
                                
                                // MIGHT HAVE FORGOTTEN TO SAVE THE BELOW CHANGE TO ZOMBIE CONFIG VALUE BEFORE PUBLISHING v3.2.3
                                if(GeneralConfig.modernZombieSkins) (ExtendedZombieVillager.get( zombie )).setProfession(2); // v3.2.3
                                                                
                                worldIn.spawnEntityInWorld(zombie);
                				
                				
                                // Set sign contents
                                
                            	TileEntitySign signContents = new TileEntitySign();
                            	signContents.signText[1] = "<----";
                            	signContents.signText[2] = "---->";
                            	worldIn.setTileEntity(
                                		(x + (new int[]{-2, 3, 2, -3})[coordBaseMode]),
                                		y,
                                		(z + (new int[]{-3, -2, 3, 2})[coordBaseMode]),
                            			signContents);
                                
                            	
                            	// Set brewing stand contents
                            	TileEntity brewingStandTileEntity = worldIn.getTileEntity(x, y, z);
                            	if (brewingStandTileEntity instanceof ISidedInventory)
                            	{
                            		((ISidedInventory) brewingStandTileEntity).setInventorySlotContents(1, new ItemStack( Items.potionitem, 1, 16392));
                            		worldIn.setTileEntity(x, y, z, brewingStandTileEntity);
                            	}
                            	
                            	//TileEntityBrewingStand brewingStandContents = new TileEntityBrewingStand();
                            	//brewingStandContents.setInventorySlotContents(1, new ItemStack( Items.potionitem, 1, 16392));
                            	//worldIn.setTileEntity(x, y, z, brewingStandContents);
                            	
                            	
                            	
                            	// Chest stuff
                            	// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
                            	
                            	TileEntity te = worldIn.getTileEntity(
                            			(x + (new int[]{-4, 0, 4, 0})[coordBaseMode]),
                                		y-1,
                                		(z + (new int[]{0, -4, 0, 4})[coordBaseMode])
                            			);
                            	if (te instanceof IInventory)
                            	{
	                            	ChestGenHooks iglooChestGoldapple = ChestGenHooks.getInfo("iglooChestGoldapple");
	                            	WeightedRandomChestContent.generateChestContents(randomIn, iglooChestGoldapple.getItems(randomIn), (TileEntityChest)te, iglooChestGoldapple.getCount(randomIn));
                            		
	                            	ChestGenHooks iglooChest = ChestGenHooks.getInfo("iglooChest");
	                            	WeightedRandomChestContent.generateChestContents(randomIn, iglooChest.getItems(randomIn), (TileEntityChest)te, iglooChest.getCount(randomIn));
                            	}
                            	
                				return;
                			}
                		}
                	}
                }
            }
            
            
        }


}