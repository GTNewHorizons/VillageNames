package astrotibs.villagenames.block;

import astrotibs.villagenames.block.color.BlockConcrete;
import astrotibs.villagenames.block.color.BlockConcretePowder;
import astrotibs.villagenames.block.color.BlockGlazedTerracotta;
import astrotibs.villagenames.block.color.ItemBlockConcrete;
import astrotibs.villagenames.block.color.ItemBlockConcretePowder;
import astrotibs.villagenames.block.color.ItemBlockGlazedTerracotta;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.prismarine.block.BlockPrismarine;
import astrotibs.villagenames.prismarine.block.BlockSeaLantern;
import astrotibs.villagenames.prismarine.block.BlockSpongeVN;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocksVN
{
	// Lunarin
	public static final BlockVN lunarinGoldBlock = new BlockLunarinGold();
	public static final BlockVN lunarinIronBlock = new BlockLunarinIron();
	
	// Prismarine
	public static final Block blockPrismarine = new BlockPrismarine();
	public static final Block blockSeaLantern = new BlockSeaLantern();
	
	// Sponge
	public static final Block blockSpongeVN = new BlockSpongeVN();
	
	// Glazed Terracotta
	public static final Block glazedTerracotta = new BlockGlazedTerracotta(0).setBlockName("glazedTerracotta1");
	public static final Block glazedTerracotta2 = new BlockGlazedTerracotta(4).setBlockName("glazedTerracotta2");
	public static final Block glazedTerracotta3 = new BlockGlazedTerracotta(8).setBlockName("glazedTerracotta3");
	public static final Block glazedTerracotta4 = new BlockGlazedTerracotta(12).setBlockName("glazedTerracotta4");
	
	// Concrete
	public static final BlockConcrete blockConcrete = new BlockConcrete("concrete");
	
	// Concrete Powder
	public static final BlockConcretePowder blockConcretePowder = new BlockConcretePowder("concretePowder");
	
	public static void init()
	{
		if (GeneralConfig.addLunarinBlocks)
		{
			GameRegistry.registerBlock(lunarinGoldBlock, "lunarinGoldBlock");
			GameRegistry.registerBlock(lunarinIronBlock, "lunarinIronBlock");
		}
		
		if (GeneralConfig.addPrismarine)
		{
			GameRegistry.registerBlock(blockSeaLantern, "sea_lantern");
			GameRegistry.registerBlock(blockPrismarine, ((ISubBlocksBlock) blockPrismarine).getItemBlockClass(), "prismarine");
			
			// Oredict registries
			OreDictionary.registerOre("blockPrismarine", new ItemStack(blockPrismarine, 1, 0));
			OreDictionary.registerOre("blockPrismarineBrick", new ItemStack(blockPrismarine, 1, 1));
			OreDictionary.registerOre("blockPrismarineDark", new ItemStack(blockPrismarine, 1, 2));
		}
		
		if (GeneralConfig.addSponges)
		{
			GameRegistry.registerBlock(blockSpongeVN, ((ISubBlocksBlock) blockSpongeVN).getItemBlockClass(), "sponge");
		}
		
		if (GeneralConfig.addConcrete)
		{
			// Terracotta
	    	GameRegistry.registerBlock(glazedTerracotta, ItemBlockGlazedTerracotta.class, "glazedTerracotta");
	    	GameRegistry.registerBlock(glazedTerracotta2, ItemBlockGlazedTerracotta.class, "glazedTerracotta2");
	    	GameRegistry.registerBlock(glazedTerracotta3, ItemBlockGlazedTerracotta.class, "glazedTerracotta3");
	    	GameRegistry.registerBlock(glazedTerracotta4, ItemBlockGlazedTerracotta.class, "glazedTerracotta4");
			
			// Concrete
	    	GameRegistry.registerBlock(blockConcrete, ItemBlockConcrete.class, "concrete");
	    	
			// Concrete Powder
	    	GameRegistry.registerBlock(blockConcretePowder, ItemBlockConcretePowder.class, "concretePowder");
		}
	}
	
	public static interface ISubBlocksBlock
	{
		Class<? extends ItemBlock> getItemBlockClass();
	}
}
