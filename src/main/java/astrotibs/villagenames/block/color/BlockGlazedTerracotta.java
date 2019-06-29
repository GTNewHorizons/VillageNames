package astrotibs.villagenames.block.color;

import java.util.List;

import astrotibs.villagenames.proxy.ClientProxy;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

// Adapted from KillerMapper
public class BlockGlazedTerracotta extends Block
{
	// These are used to reference texture names
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
    // These are used to reference unlocalized names
    public static String[] subBlockUnloc = {
    		"White", //0
    		"Orange", //1
    		"Magenta", //2
    		"LightBlue", //3
    		"Yellow", //4
    		"Lime", //5
    		"Pink", //6
    		"Gray", //7
    		"Silver", //8
    		"Cyan", //9
    		"Purple", //10
    		"Blue", //11
    		"Brown", //12
    		"Green", //13
    		"Red", //14
    		"Black" //15
    		};
    private IIcon[] blockIcons = new IIcon[32];
    private int metaSubStart; // This value iterates through the color strings because you can only register 4 (since meta is limited to 16 values)
    
    public BlockGlazedTerracotta(int metaSubStart)
    {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.metaSubStart = metaSubStart;
        this.setHardness(1.4F);
        this.setHarvestLevel("pickaxe", 0);
        this.setStepSound(soundTypeStone);
    }

    @Override
	@SideOnly(Side.CLIENT)
    public int getRenderType()
    {
        return ClientProxy.renderGlazedTerracotta;
    }

    @Override
	public int damageDropped(int metadata)
    {
        return metadata%4+metaSubStart;
    }

    @Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
    {
        for(int i = metaSubStart; i < metaSubStart+4; i++)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
	public void registerBlockIcons(IIconRegister iicon)
    {
    	
        for (int subs = 0; subs < 4; subs ++) {
        	/* VN addresses are kept here because there needs to be two icons per block,
        	 * which is different from the vanilla-style one per block.
        	 * If someone were to use a resource pack to reference the vanilla addresses,
        	 * it would only replace some of the block faces.
        	 */
	        this.blockIcons[subs] = iicon.registerIcon(Reference.MOD_ID + ":glazed_terracotta_"+subBlock[metaSubStart+subs]);
	        this.blockIcons[subs+16] = iicon.registerIcon(Reference.MOD_ID + ":glazed_terracotta_"+subBlock[metaSubStart+subs]+"_i");
	    }
    }
    
    @Override
	public IIcon getIcon(int side, int metadata)
    {
    	IIcon sideIcon = this.blockIcons[metadata%4]; // The ordinary orientation
    	// Everything below are combinations of side/orientations that need to be mirrored
        if(side == 0) {sideIcon = this.blockIcons[metadata%4+16];} // Bottom
        if(side == 2 && ((metadata/4)%2==0)) {sideIcon = this.blockIcons[metadata%4+16];} // North
        if(side == 5 && ((metadata/4)%2==1)) {sideIcon = this.blockIcons[metadata%4+16];} // East
        return sideIcon;
    }

    @Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
    {
        int meta = 0;
        int direction = MathHelper.floor_double(living.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
        
        meta = (direction*4) + (stack.getItemDamage())%4;
        world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    }
    
    @Override
	public MapColor getMapColor(int meta)
    {
    	MapColor mapColor = MapColor.airColor;
    	switch ( metaSubStart+(meta%4) ) {
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