package astrotibs.villagenames.prismarine.block;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.google.common.collect.Lists;

import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSpongeVN extends BlockGeneric {

    public BlockSpongeVN() {
        super(Material.sponge, "dry", "wet");
        setHardness(0.6F);
        setStepSound(soundTypeGrass);
        setBlockTextureName("sponge");
        setBlockName(Reference.MOD_ID + "." + "sponge");
        setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        if (world.provider.isHellWorld) // Dries when placed in the Nether
        {
            world.setBlockMetadataWithNotify(x, y, z, 0, 2);

            world.playSoundEffect(
                (double) ((float) x + 0.5F),
                (double) ((float) y + 0.5F),
                (double) ((float) z + 0.5F),
                "random.fizz",
                0.5F,
                2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

            // Smoke particles - doesn't seem to be working
            // for (int l = 0; l < 8; ++l)
            // {
            // world.spawnParticle("largesmoke", (double)x + Math.random(), (double)y + Math.random(), (double)z +
            // Math.random(), 0.0D, 0.0D, 0.0D);
            // }
        } else {
            tryAbsorb(world, x, y, z, world.getBlockMetadata(x, y, z) == 1);
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        tryAbsorb(world, x, y, z, world.getBlockMetadata(x, y, z) == 1);
        super.onNeighborBlockChange(world, x, y, z, neighborBlock);
    }

    protected void tryAbsorb(World worldIn, int x, int y, int z, boolean wet) {
        if (!wet && absorb(worldIn, x, y, z)) {
            worldIn.setBlockMetadataWithNotify(x, y, z, 1, 2);
            worldIn.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(Blocks.water));
        }
    }

    private boolean absorb(World world, int x, int y, int z) {
        LinkedList<Tuple> linkedlist = Lists.newLinkedList();
        ArrayList<WorldCoord> arraylist = Lists.newArrayList();
        linkedlist.add(new Tuple(new WorldCoord(x, y, z), 0));
        int i = 0;
        WorldCoord blockpos1;

        while (!linkedlist.isEmpty()) {
            Tuple tuple = linkedlist.poll();
            blockpos1 = (WorldCoord) tuple.getFirst();
            int j = (Integer) tuple.getSecond();

            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                WorldCoord blockpos2 = blockpos1.add(dir);

                if (world.getBlock(blockpos2.x, blockpos2.y, blockpos2.z)
                    .getMaterial() == Material.water) {
                    world.setBlockToAir(blockpos2.x, blockpos2.y, blockpos2.z);
                    arraylist.add(blockpos2);
                    i++;
                    if (j < 6) linkedlist.add(new Tuple(blockpos2, j + 1));
                }
            }

            if (i > 64) break;
        }

        Iterator<WorldCoord> iterator = arraylist.iterator();

        while (iterator.hasNext()) {
            blockpos1 = iterator.next();
            world.notifyBlockOfNeighborChange(blockpos1.x, blockpos1.y, blockpos1.z, Blocks.air);
        }

        return i > 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (world.getBlockMetadata(x, y, z) == 1) {
            ForgeDirection dir = getRandomDirection(rand);

            if (dir != ForgeDirection.UP
                && !World.doesBlockHaveSolidTopSurface(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) {
                double d0 = x;
                double d1 = y;
                double d2 = z;

                if (dir == ForgeDirection.DOWN) {
                    d1 -= 0.05D;
                    d0 += rand.nextDouble();
                    d2 += rand.nextDouble();
                } else {
                    d1 += rand.nextDouble() * 0.8D;

                    if (dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) {
                        d2 += rand.nextDouble();

                        if (dir == ForgeDirection.EAST) d0++;
                        else d0 += 0.05D;
                    } else {
                        d0 += rand.nextDouble();

                        if (dir == ForgeDirection.SOUTH) d2++;
                        else d2 += 0.05D;
                    }
                }

                world.spawnParticle("dripWater", d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    private ForgeDirection getRandomDirection(Random rand) {
        return ForgeDirection.VALID_DIRECTIONS[rand.nextInt(ForgeDirection.VALID_DIRECTIONS.length)];
    }

    @Override
    public String getUnlocalizedName() {
        // return String.format("tile.%s%s", Reference.MOD_ID.toLowerCase() + ":",
        // getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
        return String.format("tile.%s", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        icons = new IIcon[types.length];
        for (int i = 0; i < types.length; i++) if (types[i].equals("")) {
            icons[i] = reg.registerIcon("minecraft" + ":" + getTextureName()); // reg.registerIcon(Reference.MOD_ID +
                                                                               // ":" + getTextureName());
        } else if (types[i].equals("dry")) {
            icons[i] = reg.registerIcon("minecraft" + ":" + getTextureName()); // reg.registerIcon(Reference.MOD_ID +
                                                                               // ":" + getTextureName());
        } else {
            icons[i] = reg.registerIcon("minecraft" + ":" + getTextureName() + "_" + types[i]); // reg.registerIcon(Reference.MOD_ID
                                                                                                // + ":" +
                                                                                                // getTextureName() +
                                                                                                // "_" + types[i] );
        }

    }

    // Block name?
    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.lastIndexOf(".") + 1);
    }

    @Override
    public MapColor getMapColor(int meta) {
        return MapColor.yellowColor;
    }

}
