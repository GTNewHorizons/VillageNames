package astrotibs.villagenames.spawnegg;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

/**
 * Adapted from Better Spawn Eggs:
 * https://github.com/DavidGoldman/BetterSpawnEggs/blob/master/com/mcf/davidee/spawneggs/eggs/DispenserBehaviorSpawnEgg.java
 * 
 * @author AstroTibs
 */

public final class DispenserBehavior extends BehaviorDefaultDispenseItem {

    public ItemStack dispenseStack(IBlockSource blockSource, ItemStack stack) {
        EnumFacing enumfacing = BlockDispenser.func_149937_b(blockSource.getBlockMetadata());
        double x = blockSource.getX() + (double) enumfacing.getFrontOffsetX();
        double y = (double) ((float) blockSource.getYInt() + 0.2F);
        double z = blockSource.getZ() + (double) enumfacing.getFrontOffsetZ();
        Entity entity = ItemSpawnEggVN.spawnCreature(blockSource.getWorld(), stack, x, y, z);
        if (entity instanceof EntityLiving && stack.hasDisplayName()) {
            ((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());
        }
        stack.splitStack(1);

        return stack;
    }
}
