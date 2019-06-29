package astrotibs.villagenames.block.color;

import java.util.List;

import astrotibs.villagenames.utility.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockConcrete extends Block
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
	
    
    public BlockConcrete(String unlocalizedName)//, String textureName)//, String colorName)
    {
        super(Material.rock);
        this.setBlockName(
        		String.format("%s%s", Reference.MOD_ID.toLowerCase() + ":", unlocalizedName.substring(unlocalizedName.lastIndexOf(".") + 1)
        				));
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(1.8F);
        this.setHarvestLevel("pickaxe", 0);
        this.setStepSound(soundTypeStone);
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
	        //this.blockIcons[subs] = iicon.registerIcon(Reference.MOD_ID + ":concrete_"+subBlock[subs]);
        	this.blockIcons[subs] = iicon.registerIcon("minecraft:concrete_"+subBlock[subs]);
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