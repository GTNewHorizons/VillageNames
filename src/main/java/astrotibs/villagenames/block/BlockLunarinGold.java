package astrotibs.villagenames.block;

import astrotibs.villagenames.material.ModMaterial;
import net.minecraft.creativetab.CreativeTabs;

public class BlockLunarinGold extends BlockVN {
	public BlockLunarinGold() {
		super(ModMaterial.gold);
		this.setBlockName("lunarinGoldBrick"); // This one alone seems to work I guess?
		this.setBlockTextureName("lunarinGoldBrick"); // But Pahimar did this too for some reason
		// Stuff I pulled out of Block.class for the gold block :P
		this.setHardness(2.0F); // Gold block is fully 3.0
		this.setResistance(6.0F); // Gold block is fully 10.0F
		this.setStepSound(soundTypeMetal);
		this.setHarvestLevel("pickaxe", 2);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
}