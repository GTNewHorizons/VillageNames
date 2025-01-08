package astrotibs.villagenames.prismarine.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

import astrotibs.villagenames.config.GeneralConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPrismarine extends BlockGeneric implements IConfigurable {

    public BlockPrismarine() {
        super(Material.rock, "rough", "bricks", "dark");
        setHardness(1.5F);
        setResistance(10.0F);
        setBlockTextureName("prismarine");
        setBlockName("prismarine"); // setBlockName(Reference.MOD_ID + "." + "prismarine_block");
        setCreativeTab(CreativeTabs.tabBlock);
    }

    @SideOnly(Side.CLIENT)
    public void setIcon(int index, IIcon icon) {
        if (icons == null) icons = new IIcon[types.length];

        icons[index] = icon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        if (icons == null) icons = new IIcon[types.length];

        for (int i = 1; i < types.length; i++) if ("".equals(types[i])) {
            icons[i] = reg.registerIcon("minecraft" + ":" + getTextureName());
        }

        else {
            icons[i] = reg.registerIcon("minecraft" + ":" + getTextureName() + "_" + types[i]); // The underscore only
                                                                                                // applies to textures,
                                                                                                // not to name
        }
    }

    @Override
    public String getUnlocalizedName() {
        // return String.format("tile.%s%s", Reference.MOD_ID.toLowerCase() + ":",
        // getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        return String.format("tile.%s", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    // Block name?
    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.lastIndexOf(".") + 1);
    }

    @Override
    public boolean isEnabled() {
        return GeneralConfig.addPrismarine;
    }

    @Override
    public MapColor getMapColor(int meta) {
        return types[meta].equalsIgnoreCase("dark") ? MapColor.cyanColor : MapColor.diamondColor;
        // This is the official mapping: "rough" is the darker texture?
        // return types[meta].equalsIgnoreCase("rough") ? MapColor.cyanColor : MapColor.diamondColor;
    }

}
