package astrotibs.villagenames.prismarine.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import astrotibs.villagenames.prismarine.block.BlockGeneric;

public class ItemBlockGeneric extends ItemBlock {

    public ItemBlockGeneric(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (field_150939_a instanceof BlockGeneric) {
            String name = ((BlockGeneric) field_150939_a).getNameFor(stack.getItemDamage());
            if ("".equals(name)) return getUnlocalizedName();
            else return getUnlocalizedName() + "." + name; // period used to be underscore
        }

        return getUnlocalizedName() + "." + stack.getItemDamage(); // period used to be underscore
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
}
