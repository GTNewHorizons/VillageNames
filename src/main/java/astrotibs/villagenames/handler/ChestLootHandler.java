package astrotibs.villagenames.handler;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.config.village.VillageGeneratorConfigHandler;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.item.ModItems;
import astrotibs.villagenames.utility.FunctionsVN;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class ChestLootHandler {
	
	// These values are defaults for loot population: they are assumed when not explicitly listed in the 1.14+ json loot tables.
	private static final int
	DEFAULT_LOOT_STACK_MINIMUM = 1,
	DEFAULT_LOOT_STACK_MAXIMUM = 1,
	DEFAULT_LOOT_STACK_WEIGHT = 1;

	private static final Object[][]
	ARMORER_CHEST_LOOT_ARRAY = new Object[][]{
		{new ItemStack(Items.iron_ingot), 1, 3, 2},
		{new ItemStack(Items.bread), 1, 4, 4},
		{new ItemStack(Items.iron_helmet), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
		{new ItemStack(Items.emerald), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
	},
	TOOLSMITHY_CHEST_LOOT_ARRAY = new Object[][]{
		{new ItemStack(Items.diamond), 1, 3, DEFAULT_LOOT_STACK_WEIGHT},
		{new ItemStack(Items.iron_ingot), 1, 5, 5},
		{new ItemStack(Items.gold_ingot), 1, 3, DEFAULT_LOOT_STACK_WEIGHT},
		{new ItemStack(Items.bread), 1, 3, 15},
		{new ItemStack(Items.iron_pickaxe), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 5},
		{new ItemStack(Items.coal), 1, 3, DEFAULT_LOOT_STACK_WEIGHT},
		{new ItemStack(Items.stick), 1, 3, 20},
		{new ItemStack(Items.iron_shovel), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 5},
//	},
//	WEAPONSMITH_CHEST_LOOT_ARRAY = new Object[][]{
//		{new ItemStack(Items.diamond), 1, 3, 3},
//		{new ItemStack(Items.iron_ingot), 1, 5, 10},
//		{new ItemStack(Items.gold_ingot), 1, 3, 5},
//		{new ItemStack(Items.bread), 1, 3, 15},
//		{new ItemStack(Items.apple), 1, 3, 15},
//		{new ItemStack(Items.iron_pickaxe), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 5},
//		{new ItemStack(Items.iron_sword), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 5},
//		{new ItemStack(Items.iron_chestplate), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 5},
//		{new ItemStack(Items.iron_helmet), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 5},
//		{new ItemStack(Items.iron_leggings), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 5},
//		{new ItemStack(Items.iron_boots), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 5},
//		{new ItemStack(Blocks.obsidian), 3, 7, 5},
//		{new ItemStack(Blocks.sapling, 1, 0), 3, 7, 5}, // Oak Sapling
//		{new ItemStack(Items.saddle), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 3},
//		{new ItemStack(Items.iron_horse_armor), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
//		{new ItemStack(Items.golden_horse_armor), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
//		{new ItemStack(Items.diamond_horse_armor), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
	};
	public static void init()
	{
		// Bonus chest
		ChestGenHooks.addItem("bonusChest", new WeightedRandomChestContent(new ItemStack(ModItems.codex), 1, 1, 1));			
		
		// Village blacksmiths
		ChestGenHooks.addItem("villageBlacksmith", new WeightedRandomChestContent(new ItemStack(ModItems.codex), 1, 1, 3));
		
		// Mineshafts
		ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(new ItemStack(ModItems.codex), 1, 1, 2));
		
		// Temples
		ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(new ItemStack(ModItems.codex), 1, 1, 12));
		ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(new ItemStack(ModItems.codex), 1, 1, 10));
		
		// Strongholds
		ChestGenHooks.addItem("strongholdCorridor", new WeightedRandomChestContent(new ItemStack(ModItems.codex), 1, 1, 4));
		ChestGenHooks.addItem("strongholdCrossing", new WeightedRandomChestContent(new ItemStack(ModItems.codex), 1, 1, 15));
		ChestGenHooks.addItem("strongholdLibrary", new WeightedRandomChestContent(new ItemStack(ModItems.codex), 1, 3, 20));
		
		// Other mods
		if ( Loader.isModLoaded("Mariculture") ) ChestGenHooks.addItem("oceanFloorChest", new WeightedRandomChestContent(new ItemStack(ModItems.codex), 1, 1, 20)); // Mariculture
		if ( Loader.isModLoaded("Artifacts") )   ChestGenHooks.addItem("A_WIZARD_DID_IT", new WeightedRandomChestContent(new ItemStack(ModItems.codex), 1, 4, 20)); // Dragon Artifacts
		
		// Modern village buildings
		if (VillageGeneratorConfigHandler.newVillageGenerator)
		{
			ChestGenHooks.addItem(Reference.VN_CARTOGRAPHER, new WeightedRandomChestContent(new ItemStack(ModItems.codex), 1, 2, 4));
			ChestGenHooks.addItem(Reference.VN_LIBRARY, new WeightedRandomChestContent(new ItemStack(ModItems.codex), 1, 2, 4));
			ChestGenHooks.addItem(Reference.VN_MASON, new WeightedRandomChestContent(new ItemStack(ModItems.codex), 1, 1, 1));
			ChestGenHooks.addItem(Reference.VN_TOOLSMITH, new WeightedRandomChestContent(new ItemStack(ModItems.codex), 1, 1, 3));
		}
	}
	
	
	public static void iglooChest()
	{
		// From https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431724-forge-custom-chest-loot-generation
		
		ChestGenHooks iglooChestContents = ChestGenHooks.getInfo("iglooChest"); // create registered ChestGenHooks
		iglooChestContents.addItem(new WeightedRandomChestContent(new ItemStack(Items.apple), 1, 3, 15));
		iglooChestContents.addItem(new WeightedRandomChestContent(new ItemStack(Items.coal), 1, 4, 15));
		iglooChestContents.addItem(new WeightedRandomChestContent(new ItemStack(Items.gold_nugget), 1, 3, 10));
		iglooChestContents.addItem(new WeightedRandomChestContent(new ItemStack(Items.stone_axe), 1, 1, 2));
		iglooChestContents.addItem(new WeightedRandomChestContent(new ItemStack(Items.rotten_flesh), 1, 1, 10));
		iglooChestContents.addItem(new WeightedRandomChestContent(new ItemStack(Items.emerald), 1, 1, 1));
		iglooChestContents.addItem(new WeightedRandomChestContent(new ItemStack(Items.wheat), 2, 3, 10));
		if (GeneralConfig.codexChestLoot) iglooChestContents.addItem(new WeightedRandomChestContent(new ItemStack(ModItems.codex), 1, 1, 8));
		
		iglooChestContents.setMin(2); // inclusive
		iglooChestContents.setMax(9); // exclusive
		
		
		ChestGenHooks iglooRareChestContents = ChestGenHooks.getInfo("iglooChestGoldapple"); // create registered ChestGenHooks
		iglooRareChestContents.addItem(new WeightedRandomChestContent(new ItemStack(Items.golden_apple), 1, 1, 1));
		
		iglooRareChestContents.setMin(1); // inclusive
		iglooRareChestContents.setMax(1); // exclusive
	}
	
	
	public static void modernVillageChests()
	{
		// Changed with each chest entry
		ChestGenHooks chestGenHooks; int stacks_min; int stacks_max;
		
		
		
		// ------------------------------------- //
		// --- General biome-specific chests --- //
		// ------------------------------------- //
		
		
		
		// --- Desert House --- //
		
		chestGenHooks = ChestGenHooks.getInfo(Reference.VN_DESERT_HOUSE);
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.clay_ball), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.dye, 1, 2), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Blocks.cactus), 1, 4, 10},
			{new ItemStack(Items.wheat), 1, 7, 10},
			{new ItemStack(Items.bread), 1, 4, 10},
			{new ItemStack(Items.book), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Blocks.deadbush), 1, 3, 2},
			{new ItemStack(Items.emerald), 1, 3, DEFAULT_LOOT_STACK_WEIGHT},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Plains House --- //
		
		chestGenHooks = ChestGenHooks.getInfo(Reference.VN_PLAINS_HOUSE);
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.gold_nugget), 1, 3, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Blocks.yellow_flower), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 2},
			{new ItemStack(Blocks.red_flower), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.potato), 1, 7, 10},
			{new ItemStack(Items.bread), 1, 4, 10},
			{new ItemStack(Items.apple), 1, 5, 10},
			{new ItemStack(Items.book), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.feather), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.emerald), 1, 4, 2},
			{new ItemStack(Blocks.sapling), 1, 2, 5}, // Oak sapling
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Savanna House --- //
		
		chestGenHooks = ChestGenHooks.getInfo(Reference.VN_SAVANNA_HOUSE);
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.gold_nugget), 1, 3, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Blocks.grass), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 5},
			{new ItemStack(Blocks.double_plant, 1, 2), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 5}, // Tall grass
			{new ItemStack(Items.bread), 1, 4, 10},
			{new ItemStack(Items.wheat_seeds), 1, 5, 10},
			{new ItemStack(Items.emerald), 1, 4, 2},
			{new ItemStack(Blocks.sapling, 1, 4), 1, 2, 10}, // Acacia
			{new ItemStack(Items.saddle), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Blocks.torch), 1, 2, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.bucket), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Snowy House --- //
		
		chestGenHooks = ChestGenHooks.getInfo(Reference.VN_SNOWY_HOUSE);
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			//{new ItemStack(Blocks.BLUE_ICE), def_min, def_max, def_weight}, // TODO - Blue Ice
			{new ItemStack(Blocks.snow), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 4},
			{new ItemStack(Items.potato), 1, 7, 10},
			{new ItemStack(Items.bread), 1, 4, 10},
			{ModObjects.chooseModBeetrootSeeds(), 1, 5, 10},
			{ModObjects.chooseModBeetrootSoup(), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Blocks.furnace), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.emerald), 1, 4, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.snowball), 1, 7, 10},
			{new ItemStack(Items.coal), 1, 4, 5},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Taiga House --- //
		
		chestGenHooks = ChestGenHooks.getInfo(Reference.VN_TAIGA_HOUSE);
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{ModObjects.chooseModIronNugget(), 1, 5, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Blocks.tallgrass, 1, 2), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 2}, // Fern
			{new ItemStack(Blocks.double_plant, 1, 3), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 2}, // Large Fern
			{new ItemStack(Items.potato), 1, 7, 10},
			{new ItemStack (ModObjects.chooseModSweetBerriesItem()), 1, 7, 5},
			{new ItemStack(Items.bread), 1, 4, 10},
			{new ItemStack(Items.pumpkin_seeds), 1, 5, 5},
			{new ItemStack(Items.pumpkin_pie), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.emerald), 1, 4, 2},
			{new ItemStack(Blocks.sapling, 1, 1), 1, 5, 5}, // Spruce Sapling
			{ModObjects.chooseModWoodenSignItem(1), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT}, // Spruce Sign
			{new ItemStack(Blocks.log, 1, 1), 1, 5, 10}, // Spruce Log
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Jungle House --- //
		
		chestGenHooks = ChestGenHooks.getInfo(Reference.VN_JUNGLE_HOUSE);
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.bread), DEFAULT_LOOT_STACK_MINIMUM, 4, 10},
			{new ItemStack(Items.emerald), DEFAULT_LOOT_STACK_MINIMUM, 4, 2},
			{ModObjects.chooseModIronNugget(), DEFAULT_LOOT_STACK_MINIMUM, 5, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Blocks.sapling, 1, 3), DEFAULT_LOOT_STACK_MINIMUM, 5, 3}, // Jungle Sapling
			{new ItemStack(Blocks.log, 1, 3), DEFAULT_LOOT_STACK_MINIMUM, 5, 10}, // Jungle Log
			{ModObjects.chooseModWoodenSignItem(3), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT}, // Jungle Sign
			{new ItemStack(Blocks.vine), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 5},
			{new ItemStack(Blocks.torch), DEFAULT_LOOT_STACK_MINIMUM, 2, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.feather), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.chicken), DEFAULT_LOOT_STACK_MINIMUM, 2, 3},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Swamp House --- //
		
		chestGenHooks = ChestGenHooks.getInfo(Reference.VN_SWAMP_HOUSE);
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.bread), DEFAULT_LOOT_STACK_MINIMUM, 4, 10},
			{new ItemStack(Items.emerald), DEFAULT_LOOT_STACK_MINIMUM, 4, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.book), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Blocks.vine), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 5},
			{new ItemStack(Items.water_bucket), DEFAULT_LOOT_STACK_MINIMUM, 3, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.coal), DEFAULT_LOOT_STACK_MINIMUM, 4, 5},
			{new ItemStack(Items.fish, 1, 0), DEFAULT_LOOT_STACK_MINIMUM, 2, DEFAULT_LOOT_STACK_WEIGHT}, // Raw Cod
			{new ItemStack(Blocks.fence), DEFAULT_LOOT_STACK_MINIMUM, 4, 2}, // Oak Fence
			{new ItemStack(Items.boat), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT}, // Oak Boat
			{ModObjects.chooseModPrismarineShardItemStack(), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		
		// ------------------------------ //
		// --- Specific career chests --- //
		// ------------------------------ //
		
		
		
		// --- Armorer --- //
		
		chestGenHooks = ChestGenHooks.getInfo(Reference.VN_ARMORER);
		
		// Number of stacks in a chest
		stacks_min=1;
		stacks_max=5;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : ARMORER_CHEST_LOOT_ARRAY)
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Butcher --- //
		
		chestGenHooks = ChestGenHooks.getInfo(Reference.VN_BUTCHER);
		
		// Number of stacks in a chest
		stacks_min=1;
		stacks_max=5;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.emerald), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.porkchop), 1, 3, 6},
			{new ItemStack(Items.wheat), 1, 3, 6},
			{new ItemStack(Items.beef), 1, 3, 6},
			{ModObjects.chooseModRawMutton(), 1, 3, 6},
			{new ItemStack(Items.coal), 1, 3, 3},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Cartographer --- //
		
		chestGenHooks = ChestGenHooks.getInfo(Reference.VN_CARTOGRAPHER);
		
		// Number of stacks in a chest
		stacks_min=1;
		stacks_max=5;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.map), 1, 3, 10},
			{new ItemStack(Items.paper), 1, 5, 15},
			{new ItemStack(Items.compass), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 5},
			{new ItemStack(Items.bread), 1, 4, 15},
			{new ItemStack(Items.stick), 1, 2, 5},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Farm --- //
		// Custom by AstroTibs
		chestGenHooks = ChestGenHooks.getInfo(Reference.VN_FARM);
		
		// Number of stacks in a chest
		stacks_min=1;
		stacks_max=5;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.emerald), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.wheat_seeds), DEFAULT_LOOT_STACK_MINIMUM, 5, 5},
			{new ItemStack(Items.potato), DEFAULT_LOOT_STACK_MINIMUM, 5, 2},
			{new ItemStack(Items.carrot), DEFAULT_LOOT_STACK_MINIMUM, 5, 2},
			{ModObjects.chooseModBeetrootSeeds(), DEFAULT_LOOT_STACK_MINIMUM, 5, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.pumpkin_seeds), DEFAULT_LOOT_STACK_MINIMUM, 5, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.reeds), DEFAULT_LOOT_STACK_MINIMUM, 5, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.bucket), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Fisher Cottage --- //
		
		chestGenHooks = ChestGenHooks.getInfo(Reference.VN_FISHER);
		
		// Number of stacks in a chest
		stacks_min=1;
		stacks_max=5;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.emerald), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.fish), 1, 3, 2}, // Cod
			{new ItemStack(Items.fish, 1, 1), 1, 3, DEFAULT_LOOT_STACK_WEIGHT}, // Salmon
			{new ItemStack(Items.water_bucket), 1, 3, DEFAULT_LOOT_STACK_WEIGHT},
			{ModObjects.chooseModBarrelItem(), 1, 3, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.clay_ball), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.wheat_seeds), 1, 3, 3},
			{new ItemStack(Items.coal), 1, 3, 2},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Fletcher --- //
		
		chestGenHooks = ChestGenHooks.getInfo(Reference.VN_FLETCHER);
		
		// Number of stacks in a chest
		stacks_min=1;
		stacks_max=5;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.emerald), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.arrow), 1, 3, 2},
			{new ItemStack(Items.feather), 1, 3, 6},
			{new ItemStack(Items.egg), 1, 3, 2},
			{new ItemStack(Items.flint), 1, 3, 6},
			{new ItemStack(Items.stick), 1, 3, 6},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Library --- //
		// Custom by AstroTibs
		chestGenHooks = ChestGenHooks.getInfo(Reference.VN_LIBRARY);
		
		// Number of stacks in a chest
		stacks_min=1;
		stacks_max=5;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.paper), DEFAULT_LOOT_STACK_MINIMUM, 3, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.dye, 1, 0), DEFAULT_LOOT_STACK_MINIMUM, 3, 6}, // Ink sac
			{new ItemStack(Items.feather), DEFAULT_LOOT_STACK_MINIMUM, 3, 6},
			{new ItemStack(Items.writable_book), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.book), DEFAULT_LOOT_STACK_MINIMUM, 3, 3},
			{new ItemStack(Items.apple), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 15},
			{new ItemStack(Items.emerald), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{ModObjects.dustyBook_LB==null ? null : new ItemStack(FunctionsVN.getItemFromName(ModObjects.dustyBook_LB)), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 2}, // Lost Book
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Mason --- //
		
		chestGenHooks = ChestGenHooks.getInfo(Reference.VN_MASON);
		
		// Number of stacks in a chest
		stacks_min=1;
		stacks_max=5;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.clay_ball), 1, 3, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.flower_pot), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Blocks.stone), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 2},
			{new ItemStack(Blocks.stonebrick), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 2},
			{new ItemStack(Items.bread), 1, 4, 4},
			{new ItemStack(Items.dye, 1, 11), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT}, // Dandelion Yellow
			{new ItemStack((Block) ModObjects.chooseModSmoothStoneBlockObject()[0], 1, (Integer) ModObjects.chooseModSmoothStoneBlockObject()[1]), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.emerald), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		// --- Shepherd --- //
		
		chestGenHooks = ChestGenHooks.getInfo(Reference.VN_SHEPHERD);
		
		// Number of stacks in a chest
		stacks_min=1;
		stacks_max=5;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Blocks.wool, 1, 0), 1, 8, 6}, // White
			{new ItemStack(Blocks.wool, 1, 15), 1, 3, 3}, // Black
			{new ItemStack(Blocks.wool, 1, 7), 1, 3, 2}, // Gray
			{new ItemStack(Blocks.wool, 1, 12), 1, 3, 2}, // Brown
			{new ItemStack(Blocks.wool, 1, 8), 1, 3, 2}, // Light Gray
			{new ItemStack(Items.emerald), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.shears), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.wheat), 1, 6, 6},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Tannery --- //
		
		chestGenHooks = ChestGenHooks.getInfo(Reference.VN_TANNERY);
		
		// Number of stacks in a chest
		stacks_min=1;
		stacks_max=5;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.leather), 1, 3, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.leather_chestplate), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 2},
			{new ItemStack(Items.leather_boots), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 2},
			{new ItemStack(Items.leather_helmet), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 2},
			{new ItemStack(Items.bread), 1, 4, 5},
			{new ItemStack(Items.leather_leggings), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, 2},
			{new ItemStack(Items.saddle), DEFAULT_LOOT_STACK_MINIMUM, DEFAULT_LOOT_STACK_MAXIMUM, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.emerald), 1, 4, DEFAULT_LOOT_STACK_WEIGHT},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Temple --- //
		
		chestGenHooks = ChestGenHooks.getInfo(Reference.VN_TEMPLE);
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.redstone), 1, 4, 2},
			{new ItemStack(Items.bread), 1, 4, 7},
			{new ItemStack(Items.rotten_flesh), 1, 4, 7},
			{new ItemStack(Items.dye), 1, 11, DEFAULT_LOOT_STACK_WEIGHT}, // Lapis Lazuli
			{new ItemStack(Items.gold_ingot), 1, 4, DEFAULT_LOOT_STACK_WEIGHT},
			{new ItemStack(Items.emerald), 1, 4, DEFAULT_LOOT_STACK_WEIGHT},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Toolsmith --- //
		
		chestGenHooks = ChestGenHooks.getInfo(Reference.VN_TOOLSMITH);
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : TOOLSMITHY_CHEST_LOOT_ARRAY)
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Weaponsmith --- //
		// Contents are identical to vanilla blacksmith chest!
		
		chestGenHooks = ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH);
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
	}
	
	public static String getGenericLootForVillageType(FunctionsVN.VillageType villageType)
	{
		switch (villageType)
		{
		default:
		case PLAINS:  return Reference.VN_PLAINS_HOUSE;
		case DESERT:  return Reference.VN_DESERT_HOUSE;
		case TAIGA:   return Reference.VN_TAIGA_HOUSE;
		case SAVANNA: return Reference.VN_SAVANNA_HOUSE;
		case SNOWY:   return Reference.VN_SNOWY_HOUSE;
		case JUNGLE:  return Reference.VN_JUNGLE_HOUSE;
		case SWAMP:   return Reference.VN_SWAMP_HOUSE;
		}
	}
}
