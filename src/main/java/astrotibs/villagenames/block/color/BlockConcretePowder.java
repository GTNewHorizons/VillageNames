package astrotibs.villagenames.block.color;

import java.util.List;
import java.util.Random;

import astrotibs.villagenames.block.ModBlocksVN;
import astrotibs.villagenames.utility.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockConcretePowder extends BlockFalling
{
	
	//@SideOnly(Side.CLIENT)
	private IIcon[] blockIcons = new IIcon[16];
	public static String[] subBlock = {
    		"white", //0
    		"orange", //1
    		"magenta", //2
    		"light_blue", //3
    		"yellow", //4
    		"lime", //5
    		"pink", //6
    		"gray", //7
    		"silver", //8
    		"cyan", //9
    		"purple", //10
    		"blue", //11
    		"brown", //12
    		"green", //13
    		"red", //14
    		"black" //15
    		};
	
	
    public BlockConcretePowder(String unlocalizedName)
    {
        super(Material.sand);
        this.setBlockName(
        		String.format("%s%s", Reference.MOD_ID.toLowerCase() + ":", unlocalizedName.substring(unlocalizedName.lastIndexOf(".") + 1)
        				));
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(0.5F);
        this.setHarvestLevel("shovel", 0);
        this.setStepSound(soundTypeSand);
    }
    
    @Override
	public int damageDropped(int metadata)
    {
        return metadata;
    }
    
    @Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
    {
        for(int i = 0; i < 16; i++)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    @Override
	public void registerBlockIcons(IIconRegister iicon)
    {
        for (int subs = 0; subs < 16; subs ++) {
	        //this.blockIcons[subs] = iicon.registerIcon(Reference.MOD_ID + ":concrete_powder_"+subBlock[subs]);
        	this.blockIcons[subs] = iicon.registerIcon("minecraft:concrete_powder_"+subBlock[subs]);
	    }
    }
    
    @Override
	public IIcon getIcon(int side, int metadata)
    {
    	IIcon sideIcon = this.blockIcons[metadata];
        return sideIcon;
    }
    
    @Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        int meta = stack.getItemDamage();
        world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    }
    
    /*
     * All the stuff below is related to conversion to solid concrete
     */
    
    @Override
	public void updateTick(World world, int x, int y, int z, Random random)
    {
    	// This fires when you place the concrete powder block
        if (!world.isRemote)
        {
            this.func_149830_m(world, x, y, z);
        }
    }
    
    public void func_149830_m(World world, int x, int y, int z)
    {
        if (func_149831_e(world, x, y - 1, z) && y >= 0) // True if block underneath is air, fire, water, or lava
        {
            byte b0 = 32;
            
            if (!fallInstantly && world.checkChunksExist(x - b0, y - b0, z - b0, x + b0, y + b0, z + b0)) // Checks if surrounding chunks exist
            { // idk what fallInstantly actually does but apparently it's always off for this object
            	
                if (!world.isRemote)
                {
                	EntityFallingBlockCP entityfallingblock = new EntityFallingBlockCP(world, x + 0.5F, y + 0.5F, z + 0.5F, this, world.getBlockMetadata(x, y, z));
                    this.func_149829_a(entityfallingblock);
                    world.spawnEntityInWorld(entityfallingblock);
                }
            }
            else
            {
            	// Does not fire apparently
                world.setBlockToAir(x, y, z);

                while (func_149831_e(world, x, y - 1, z) && y > 0)
                {
                    --y;
                }

                if (y > 0)
                {
                    world.setBlock(x, y, z, this);
                }
            }
        }
    }
    
    protected boolean tryTouchWater(World worldIn, int x, int y, int z)//, Block block)
    {
    	int xo = x; int yo = y; int zo = z;
        boolean flag = false;
        
        for (int side = 1; side < 6; side++)
        {
        	xo = x; yo = y; zo = z;
        	switch (side) {
        	//case 0: yo--; break;
        	case 1: yo++; break;
        	case 2: zo--; break;
        	case 3: zo++; break;
        	case 4: xo--; break;
        	case 5: xo++; break;
        	}
        	
            if (worldIn.getBlock(xo, yo, zo).getMaterial() == Material.water)
            {
                flag = true;
                break;
            }
        }

        if (flag)
        {
        	int meta = worldIn.getBlockMetadata(x, y, z);
        	BlockConcrete concreteOut = ModBlocksVN.blockConcrete;
        	worldIn.setBlock(x, y, z, concreteOut, meta, 3);
        }

        return flag;
    }
    
    
    /** 1.7.10
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    @Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
    	if (!this.tryTouchWater(world, x, y, z)) {
    		super.onNeighborBlockChange(world, x, y, z, block);
    	}
    }
    
    /** 1.7.10
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    @Override
	public void onBlockAdded(World world, int x, int y, int z)
    {
    	if (!this.tryTouchWater(world, x, y, z)) {
    		super.onBlockAdded(world, x, y, z);
    	}
    }
    
    @Override
	public MapColor getMapColor(int meta)
    {
    	MapColor mapColor = MapColor.airColor;
    	switch ( meta ) {
    	case 0: mapColor =  MapColor.snowColor; break;
    	case 1: mapColor =  MapColor.adobeColor; break;
    	case 2: mapColor =  MapColor.magentaColor; break;
    	case 3: mapColor =  MapColor.lightBlueColor; break;
    	case 4: mapColor =  MapColor.yellowColor; break;
    	case 5: mapColor =  MapColor.limeColor; break;
    	case 6: mapColor =  MapColor.pinkColor; break;
    	case 7: mapColor =  MapColor.grayColor; break;
    	case 8: mapColor =  MapColor.silverColor; break;
    	case 9: mapColor =  MapColor.cyanColor; break;
    	case 10: mapColor =  MapColor.purpleColor; break;
    	case 11: mapColor =  MapColor.blueColor; break;
    	case 12: mapColor =  MapColor.brownColor; break;
    	case 13: mapColor =  MapColor.greenColor; break;
    	case 14: mapColor =  MapColor.redColor; break;
    	case 15: mapColor =  MapColor.blackColor; break;
    	}
    	return mapColor;
    }
    
}