package astrotibs.villagenames.prismarine.block;

import java.util.List;

import astrotibs.villagenames.block.ModBlocksVN.ISubBlocksBlock;
import astrotibs.villagenames.prismarine.item.ItemBlockGeneric;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockGeneric extends Block implements ISubBlocksBlock {

	@SideOnly(Side.CLIENT)
	protected IIcon[] icons;
	protected final String[] types;

	protected int startMeta = 0;

	public BlockGeneric(Material material, String... types) {
		super(material);
		this.types = types;
	}

	public String getNameFor(int meta) {
		return types[Math.max(Math.min(meta, types.length - 1), 0)];
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = startMeta; i < types.length; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icons[Math.max(Math.min(meta, types.length - 1), startMeta)];
	}
	
	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockGeneric.class;
	}
}