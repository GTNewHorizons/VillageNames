package astrotibs.villagenames.prismarine.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PrismarineCrystals extends Item {

    public PrismarineCrystals() {
        super();
        setTextureName("prismarine_crystals");
        // setUnlocalizedName(Reference.MOD_ID.toLowerCase() + ":" + "prismarine_crystals");
        // setUnlocalizedName("minecraft:" + "prismarine_crystals");
        setUnlocalizedName("prismarineCrystals");
        setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        // itemIcon =
        // iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
        itemIcon = iconRegister.registerIcon("minecraft:prismarine_crystals");
    }

}
