package astrotibs.villagenames.prismarine.register;

import astrotibs.villagenames.prismarine.block.BlockPrismarine;
import astrotibs.villagenames.prismarine.block.BlockSeaLantern;
import astrotibs.villagenames.prismarine.block.BlockSpongeVN;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModBlocksPrismarine {

	
	public static final Block blockPrismarine = new BlockPrismarine();
	public static final Block blockSeaLantern = new BlockSeaLantern();
	
	public static final Block blockSpongeVN = new BlockSpongeVN();
	
	public static void init() {
		
		GameRegistry.registerBlock(blockSeaLantern, "sea_lantern");
		GameRegistry.registerBlock(blockSpongeVN, ((ISubBlocksBlock) blockSpongeVN).getItemBlockClass(), "sponge");
		GameRegistry.registerBlock(blockPrismarine, ((ISubBlocksBlock) blockPrismarine).getItemBlockClass(), "prismarine");
		
		// Oredict registries
		OreDictionary.registerOre("blockPrismarine", new ItemStack(blockPrismarine, 1, 0));
		OreDictionary.registerOre("blockPrismarineBrick", new ItemStack(blockPrismarine, 1, 1));
		OreDictionary.registerOre("blockPrismarineDark", new ItemStack(blockPrismarine, 1, 2));
	}
	
	public static interface ISubBlocksBlock {

		Class<? extends ItemBlock> getItemBlockClass();
	}
	
}