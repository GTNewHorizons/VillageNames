package astrotibs.villagenames.spawnegg;

import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import astrotibs.villagenames.prismarine.guardian.entity.monster.EntityGuardian;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Adapted from Better Spawn Eggs:
 * https://github.com/DavidGoldman/BetterSpawnEggs/blob/master/com/mcf/davidee/spawneggs/eggs/ItemSpawnEgg.java
 * 
 * @author AstroTibs
 */

public class ItemSpawnEggVN extends Item {

    private IIcon icon;

    public ItemSpawnEggVN() {
        setHasSubtypes(true);
        setCreativeTab(CreativeTabs.tabMisc);
        setUnlocalizedName("monsterPlacer");
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String name = ("" + StatCollector.translateToLocal("item.monsterPlacer.name")).trim();
        SpawnEggInfo info = SpawnEggRegistry.getEggInfo((short) stack.getItemDamage());

        if (info == null) {
            return name;
        }

        String mobID = info.mobID;
        String displayName = info.displayName;

        if (stack.hasTagCompound()) {
            NBTTagCompound compound = stack.getTagCompound();
            if (compound.hasKey("mobID")) {
                mobID = compound.getString("mobID");
            }
            if (compound.hasKey("displayName")) {
                displayName = compound.getString("displayName");
            }
        }

        if (displayName == null) {
            name += " " + attemptToTranslate("entity." + mobID + ".name", mobID);
        } else {
            name += " "
                + ("" + StatCollector.translateToLocal("eggdisplay." + Reference.MOD_ID + "." + displayName + ".name"))
                    .trim();
        }

        return name;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
        SpawnEggInfo info = SpawnEggRegistry.getEggInfo((short) stack.getItemDamage());

        if (info == null) {
            return renderPass == 0 ? 0xdb9c3e : 0x423b32;
        } // Calico colors

        int color = (renderPass == 0) ? info.primaryColor : info.secondaryColor;

        if (stack.hasTagCompound()) {
            NBTTagCompound compound = stack.getTagCompound();

            if (renderPass == 0 && compound.hasKey("primaryColor")) {
                color = compound.getInteger("primaryColor");
            }
            if (renderPass != 0 && compound.hasKey("secondaryColor")) {
                color = compound.getInteger("secondaryColor");
            }
        }

        return color;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int par7,
        float par8, float par9, float par10) {

        if (world.isRemote) {
            return true;
        } else {
            Block block = world.getBlock(x, y, z);
            x += Facing.offsetsXForSide[par7];
            y += Facing.offsetsYForSide[par7];
            z += Facing.offsetsZForSide[par7];
            double d0 = 0.0D;

            if (par7 == 1 && block.getRenderType() == 11) {
                d0 = Reference.SPAWN_BLOCK_OFFSET;
            }

            Entity entity = spawnCreature(
                world,
                itemStack,
                (double) x + Reference.SPAWN_BLOCK_OFFSET,
                (double) y + d0,
                (double) z + Reference.SPAWN_BLOCK_OFFSET);

            if (entity != null) {
                if (entity instanceof EntityLiving && itemStack.hasDisplayName()) {
                    ((EntityLiving) entity).setCustomNameTag(itemStack.getDisplayName());
                }

                if (!player.capabilities.isCreativeMode) {
                    --itemStack.stackSize;
                }
            }

            return true;
        }
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

        if (world.isRemote) {
            return stack;
        } else {
            MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, player, true);

            if (movingobjectposition == null) {
                return stack;
            }

            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int x = movingobjectposition.blockX;
                int y = movingobjectposition.blockY;
                int z = movingobjectposition.blockZ;

                if (!world.canMineBlock(player, x, y, z)) {
                    return stack;
                }

                if (!player.canPlayerEdit(x, y, z, movingobjectposition.sideHit, stack)) {
                    return stack;
                }

                if (world.getBlock(x, y, z) instanceof BlockLiquid) {
                    Entity entity = spawnCreature(world, stack, (double) x, (double) y, (double) z);

                    if (entity != null) {
                        if (entity instanceof EntityLiving && stack.hasDisplayName()) {
                            ((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());
                        }
                        if (!player.capabilities.isCreativeMode) {
                            --stack.stackSize;
                        }
                    }
                }
            }

            return stack;
        }

    }

    /**
     * Spawns the creature specified by the egg's type in the location specified by the last three parameters.
     * Parameters: world, entityID, x, y, z.
     */
    public static Entity spawnCreature(World world, ItemStack stack, double x, double y, double z) {
        int stackDamage = stack.getItemDamage();

        SpawnEggInfo info = SpawnEggRegistry.getEggInfo((short) stackDamage);

        if (info == null) {
            return null;
        }

        String mobID = info.mobID;
        String displayName = info.displayName;
        NBTTagCompound spawnData = info.spawnData;

        if (stack.hasTagCompound()) {
            NBTTagCompound compound = stack.getTagCompound();

            if (compound.hasKey("mobID")) {
                mobID = compound.getString("mobID");
            }
            if (compound.hasKey("displayName")) {
                displayName = compound.getString("displayName");
            }
            if (compound.hasKey("spawnData")) {
                spawnData = compound.getCompoundTag("spawnData");
            }
        }

        Entity entity = null;

        entity = EntityList.createEntityByName(mobID, world);

        if (entity != null && entity instanceof EntityLiving) {
            EntityLiving entityliving = (EntityLiving) entity;
            entity
                .setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
            entityliving.rotationYawHead = entityliving.rotationYaw;
            entityliving.renderYawOffset = entityliving.rotationYaw;
            entityliving.onSpawnWithEgg(null);

            if (!spawnData.hasNoTags()) {
                addNBTData(entity, spawnData);
            }

            if (entityliving instanceof EntityGuardian && info != null && info.spawnData.getBoolean("Elder")) {
                ((EntityGuardian) entityliving).setElder(true);
                ((EntityGuardian) entityliving).heal(((EntityGuardian) entityliving).getMaxHealth());
            }

            world.spawnEntityInWorld(entity);
            entityliving.playLivingSound();
        }

        return entity;

    }

    @SuppressWarnings("unchecked")
    private static void addNBTData(Entity entity, NBTTagCompound spawnData) {
        NBTTagCompound newTag = new NBTTagCompound();
        entity.writeToNBTOptional(newTag);

        for (String name : (Set<String>) spawnData.func_150296_c()) // getKeySet() in 1.8
        {
            newTag.setTag(
                name,
                spawnData.getTag(name)
                    .copy());
        }

        entity.readFromNBT(newTag);
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
        return pass > 0 ? icon : super.getIconFromDamageForRenderPass(meta, pass);
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list) {
        for (SpawnEggInfo info : SpawnEggRegistry.getEggInfoList()) {
            list.add(new ItemStack(par1, 1, info.eggID));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("spawn_egg");
        icon = iconRegister.registerIcon("spawn_egg_overlay");
    }

    public static String attemptToTranslate(String key, String _default) {
        String result = StatCollector.translateToLocal(key);
        return (result.equals(key)) ? _default : result;
    }
}
