package astrotibs.villagenames.prismarine.item;

import astrotibs.villagenames.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class PrismarineCrystals extends Item {

	public PrismarineCrystals() {
		super();
		setTextureName("prismarine_crystals");
		setUnlocalizedName(Reference.MOD_ID.toLowerCase() + ":" + "prismarine_crystals");
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }
	
}