package astrotibs.villagenames.block.color;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

//Adapted from KillerMapper
public class ItemBlockConcretePowder extends ItemBlock
{

    public ItemBlockConcretePowder(Block block)
    {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
	public int getMetadata(int metadata)
    {
        return metadata;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int metadata)
    {
        return this.field_150939_a.getIcon(2, metadata);
    }

    @Override
	public String getUnlocalizedName(ItemStack stack)
    {
        int metadata = stack.getItemDamage() % (BlockConcrete.subBlock.length);
        String itemName = super.getUnlocalizedName().substring(super.getUnlocalizedName().lastIndexOf(".") + 1);
        //return String.format("%s%s", "tile."+ Reference.MOD_ID.toLowerCase() + ":", "concretePowder" + "." + BlockConcrete.subBlock[metadata]);
        return String.format("%s%s", "tile.", "concrete_powder" + "." + BlockConcrete.subBlock[metadata]);
    }
    
}
