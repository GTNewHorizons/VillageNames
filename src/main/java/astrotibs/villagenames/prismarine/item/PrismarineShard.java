package astrotibs.villagenames.prismarine.item;

import astrotibs.villagenames.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class PrismarineShard extends Item{
	
	public PrismarineShard() {
		super();
		setTextureName("prismarine_shard");
		setUnlocalizedName(Reference.MOD_ID.toLowerCase() + ":" + "prismarine_shard");
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }
    
}