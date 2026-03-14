package astrotibs.villagenames.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockVN extends Block {

    // Specific type declaration
    public BlockVN(Material material) {
        super(material);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format(
            "tile.%s%s",
            Reference.MOD_ID.toLowerCase() + ":",
            getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    // Texture
    @Override
    @SideOnly(Side.CLIENT) // This method only exists on the client side. There is one for the server, too.
    public void registerBlockIcons(IIconRegister iconRegister) {
        blockIcon = iconRegister
            .registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
    }

    // Block name?
    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.lastIndexOf(".") + 1);
    }

}
