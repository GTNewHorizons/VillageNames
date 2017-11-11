package astrotibs.villagenames.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockLunarinIron extends BlockVN {
	public BlockLunarinIron() {
		super(Material.iron);
		this.setBlockName("lunarinIronBrick"); // This one alone seems to work I guess?
		this.setBlockTextureName("lunarinIronBrick"); // But Pahimar did this too for some reason
		// Stuff I pulled out of Block.class for the gold block :P
		this.setHardness(3.3F); // Gold block is fully 3.0
		this.setResistance(6.0F); // Gold block is fully 10.0F
		this.setStepSound(soundTypeMetal);
		this.setHarvestLevel("pickaxe", 1);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
}