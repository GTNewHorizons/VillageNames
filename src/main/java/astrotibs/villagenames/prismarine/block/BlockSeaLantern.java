package astrotibs.villagenames.prismarine.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSeaLantern extends Block {

    public BlockSeaLantern() {
        super(Material.glass);
        setHardness(0.3F);
        setLightLevel(1.0F);
        setStepSound(soundTypeGlass);
        setBlockTextureName("sea_lantern");
        setBlockName(Reference.MOD_ID + "." + "sea_lantern");
        setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public int quantityDropped(Random random) {
        return 2 + random.nextInt(2);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        return MathHelper.clamp_int(this.quantityDropped(random) + random.nextInt(fortune + 1), 1, 5);
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int fortune) {

        ItemStack droppedPrismarine = ModObjects.chooseModPrismarineCrystalsItemStack();

        return droppedPrismarine == null ? null : droppedPrismarine.getItem();
    }

    @Override
    public MapColor getMapColor(int meta) {
        return MapColor.quartzColor;
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("tile.%s", "seaLantern");

    }

    // Texture
    @Override
    @SideOnly(Side.CLIENT) // This method only exists on the client side. There is one for the server, too.
    public void registerBlockIcons(IIconRegister iconRegister) {
        // blockIcon = iconRegister.registerIcon(String.format("%s",
        // getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        blockIcon = iconRegister.registerIcon("minecraft:sea_lantern");
    }

    // Block name?
    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.lastIndexOf(".") + 1);
    }

}
