package astrotibs.villagenames.prismarine.register;

import astrotibs.villagenames.prismarine.item.PrismarineCrystals;
import astrotibs.villagenames.prismarine.item.PrismarineShard;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class ModItemsPrismarine {
	
	
	public static final Item prismarine_shard = new PrismarineShard();
	public static final Item prismarine_crystals = new PrismarineCrystals();
	
	public static void init () {
		GameRegistry.registerItem(prismarine_shard, "prismarine_shard");
		GameRegistry.registerItem(prismarine_crystals, "prismarine_crystals");
		
		// Oredict registries
		OreDictionary.registerOre("gemPrismarine", prismarine_shard);
		OreDictionary.registerOre("dustPrismarine", prismarine_crystals);
	}
	
}