package astrotibs.villagenames.handler;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.config.village.VillageGeneratorConfigHandler;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.item.ModItems;
import astrotibs.villagenames.utility.FunctionsVN;
import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class ChestLootHandler {
	
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
			ChestGenHooks.addItem("vn_cartographer", new WeightedRandomChestContent(new ItemStack(ModItems.codex), 1, 2, 4));
			ChestGenHooks.addItem("vn_library", new WeightedRandomChestContent(new ItemStack(ModItems.codex), 1, 2, 4));
			ChestGenHooks.addItem("vn_mason", new WeightedRandomChestContent(new ItemStack(ModItems.codex), 1, 1, 1));
			ChestGenHooks.addItem("vn_toolsmith", new WeightedRandomChestContent(new ItemStack(ModItems.codex), 1, 1, 3));
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
		// These values are defaults for loot population: they are assumed when not explicitly listed in the 1.14+ json loot tables.
		int def_min = 1; int def_max = 1; int def_weight = 1;
		
		
		// ------------------------------------- //
		// --- General biome-specific chests --- //
		// ------------------------------------- //
		
		
		
		// --- Desert House --- //
		
		chestGenHooks = ChestGenHooks.getInfo("vn_desert_house");
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.clay_ball), def_min, def_max, def_weight},
			{new ItemStack(Items.dye, 1, 2), def_min, def_max, def_weight},
			{new ItemStack(Blocks.cactus), 1, 4, 10},
			{new ItemStack(Items.wheat), 1, 7, 10},
			{new ItemStack(Items.bread), 1, 4, 10},
			{new ItemStack(Items.book), def_min, def_max, def_weight},
			{new ItemStack(Blocks.deadbush), 1, 3, 2},
			{new ItemStack(Items.emerald), 1, 3, def_weight},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Plains House --- //
		
		chestGenHooks = ChestGenHooks.getInfo("vn_plains_house");
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.gold_nugget), 1, 3, def_weight},
			{new ItemStack(Blocks.yellow_flower), def_min, def_max, 2},
			{new ItemStack(Blocks.red_flower), def_min, def_max, def_weight},
			{new ItemStack(Items.potato), 1, 7, 10},
			{new ItemStack(Items.bread), 1, 4, 10},
			{new ItemStack(Items.apple), 1, 5, 10},
			{new ItemStack(Items.book), def_min, def_max, def_weight},
			{new ItemStack(Items.feather), def_min, def_max, def_weight},
			{new ItemStack(Items.emerald), 1, 4, 2},
			{new ItemStack(Blocks.sapling), 1, 2, 5}, // Oak sapling
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Savanna House --- //
		
		chestGenHooks = ChestGenHooks.getInfo("vn_savanna_house");
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.gold_nugget), 1, 3, def_weight},
			{new ItemStack(Blocks.grass), def_min, def_max, 5},
			{new ItemStack(Blocks.double_plant, 1, 2), def_min, def_max, 5}, // Tall grass
			{new ItemStack(Items.bread), 1, 4, 10},
			{new ItemStack(Items.wheat_seeds), 1, 5, 10},
			{new ItemStack(Items.emerald), 1, 4, 2},
			{new ItemStack(Blocks.sapling, 1, 4), 1, 2, 10}, // Acacia
			{new ItemStack(Items.saddle), def_min, def_max, def_weight},
			{new ItemStack(Blocks.torch), 1, 2, def_weight},
			{new ItemStack(Items.bucket), def_min, def_max, def_weight},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Snowy House --- //
		
		chestGenHooks = ChestGenHooks.getInfo("vn_snowy_house");
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			//{new ItemStack(Blocks.BLUE_ICE), def_min, def_max, def_weight}, // TODO - Blue Ice
			{new ItemStack(Blocks.snow), def_min, def_max, 4},
			{new ItemStack(Items.potato), 1, 7, 10},
			{new ItemStack(Items.bread), 1, 4, 10},
			{ModObjects.chooseModBeetrootSeeds(), 1, 5, 10},
			{ModObjects.chooseModBeetrootSoup(), def_min, def_max, def_weight},
			{new ItemStack(Blocks.furnace), def_min, def_max, def_weight},
			{new ItemStack(Items.emerald), 1, 4, def_weight},
			{new ItemStack(Items.snowball), 1, 7, 10},
			{new ItemStack(Items.coal), 1, 4, 5},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Taiga House --- //
		
		chestGenHooks = ChestGenHooks.getInfo("vn_taiga_house");
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{ModObjects.chooseModIronNugget(), 1, 5, def_weight},
			{new ItemStack(Blocks.tallgrass, 1, 2), def_min, def_max, 2}, // Fern
			{new ItemStack(Blocks.double_plant, 1, 3), def_min, def_max, 2}, // Large Fern
			{new ItemStack(Items.potato), 1, 7, 10},
			{new ItemStack (ModObjects.chooseModSweetBerriesItem()), 1, 7, 5},
			{new ItemStack(Items.bread), 1, 4, 10},
			{new ItemStack(Items.pumpkin_seeds), 1, 5, 5},
			{new ItemStack(Items.pumpkin_pie), def_min, def_max, def_weight},
			{new ItemStack(Items.emerald), 1, 4, 2},
			{new ItemStack(Blocks.sapling, 1, 1), 1, 5, 5}, // Spruce Sapling
			{ModObjects.chooseModWoodenSignItem(1), def_min, def_max, def_weight}, // Spruce Sign
			{new ItemStack(Blocks.log, 1, 1), 1, 5, 10}, // Spruce Log
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Jungle House --- //
		
		chestGenHooks = ChestGenHooks.getInfo("vn_jungle_house");
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.bread), def_min, 4, 10},
			{new ItemStack(Items.emerald), def_min, 4, 2},
			{ModObjects.chooseModIronNugget(), def_min, 5, def_weight},
			{new ItemStack(Blocks.sapling, 1, 3), def_min, 5, 3}, // Jungle Sapling
			{new ItemStack(Blocks.log, 1, 3), def_min, 5, 10}, // Jungle Log
			{ModObjects.chooseModWoodenSignItem(3), def_min, def_max, def_weight}, // Jungle Sign
			{new ItemStack(Blocks.vine), def_min, def_max, 5},
			{new ItemStack(Blocks.torch), def_min, 2, def_weight},
			{new ItemStack(Items.feather), def_min, def_max, def_weight},
			{new ItemStack(Items.chicken), def_min, 2, 3},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Swamp House --- //
		
		chestGenHooks = ChestGenHooks.getInfo("vn_swamp_house");
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.bread), def_min, 4, 10},
			{new ItemStack(Items.emerald), def_min, 4, def_weight},
			{new ItemStack(Items.book), def_min, def_max, def_weight},
			{new ItemStack(Blocks.vine), def_min, def_max, 5},
			{new ItemStack(Items.water_bucket), def_min, 3, def_weight},
			{new ItemStack(Items.coal), def_min, 4, 5},
			{new ItemStack(Items.fish, 1, 0), def_min, 2, def_weight}, // Raw Cod
			{new ItemStack(Blocks.fence), def_min, 4, 2}, // Oak Fence
			{new ItemStack(Items.boat), def_min, def_max, def_weight}, // Oak Boat
			{ModObjects.chooseModPrismarineShardItemStack(), def_min, def_max, def_weight},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		
		// ------------------------------ //
		// --- Specific career chests --- //
		// ------------------------------ //
		
		
		
		// --- Armorer --- //
		
		chestGenHooks = ChestGenHooks.getInfo("vn_armorer");
		
		// Number of stacks in a chest
		stacks_min=1;
		stacks_max=5;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.iron_ingot), 1, 3, 2},
			{new ItemStack(Items.bread), 1, 4, 4},
			{new ItemStack(Items.iron_helmet), def_min, def_max, def_weight},
			{new ItemStack(Items.emerald), def_min, def_max, def_weight},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Butcher --- //
		
		chestGenHooks = ChestGenHooks.getInfo("vn_butcher");
		
		// Number of stacks in a chest
		stacks_min=1;
		stacks_max=5;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.emerald), def_min, def_max, def_weight},
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
		
		chestGenHooks = ChestGenHooks.getInfo("vn_cartographer");
		
		// Number of stacks in a chest
		stacks_min=1;
		stacks_max=5;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.map), 1, 3, 10},
			{new ItemStack(Items.paper), 1, 5, 15},
			{new ItemStack(Items.compass), def_min, def_max, 5},
			{new ItemStack(Items.bread), 1, 4, 15},
			{new ItemStack(Items.stick), 1, 2, 5},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Farm --- //
		// Custom by AstroTibs
		chestGenHooks = ChestGenHooks.getInfo("vn_farm");
		
		// Number of stacks in a chest
		stacks_min=1;
		stacks_max=5;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.emerald), def_min, def_max, def_weight},
			{new ItemStack(Items.wheat_seeds), def_min, 5, 5},
			{new ItemStack(Items.potato), def_min, 5, 2},
			{new ItemStack(Items.carrot), def_min, 5, 2},
			{ModObjects.chooseModBeetrootSeeds(), def_min, 5, def_weight},
			{new ItemStack(Items.pumpkin_seeds), def_min, 5, def_weight},
			{new ItemStack(Items.reeds), def_min, 5, def_weight},
			{new ItemStack(Items.bucket), def_min, def_max, def_weight},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Fisher Cottage --- //
		
		chestGenHooks = ChestGenHooks.getInfo("vn_fisher");
		
		// Number of stacks in a chest
		stacks_min=1;
		stacks_max=5;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.emerald), def_min, def_max, def_weight},
			{new ItemStack(Items.fish), 1, 3, 2}, // Cod
			{new ItemStack(Items.fish, 1, 1), 1, 3, def_weight}, // Salmon
			{new ItemStack(Items.water_bucket), 1, 3, def_weight},
			{ModObjects.chooseModBarrelItem(), 1, 3, def_weight},
			{new ItemStack(Items.clay_ball), def_min, def_max, def_weight},
			{new ItemStack(Items.wheat_seeds), 1, 3, 3},
			{new ItemStack(Items.coal), 1, 3, 2},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Fletcher --- //
		
		chestGenHooks = ChestGenHooks.getInfo("vn_fletcher");
		
		// Number of stacks in a chest
		stacks_min=1;
		stacks_max=5;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.emerald), def_min, def_max, def_weight},
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
		chestGenHooks = ChestGenHooks.getInfo("vn_library");
		
		// Number of stacks in a chest
		stacks_min=1;
		stacks_max=5;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.paper), def_min, 3, def_weight},
			{new ItemStack(Items.dye, 1, 0), def_min, 3, 6}, // Ink sac
			{new ItemStack(Items.feather), def_min, 3, 6},
			{new ItemStack(Items.writable_book), def_min, def_max, def_weight},
			{new ItemStack(Items.book), def_min, 3, 3},
			{new ItemStack(Items.apple), def_min, def_max, 15},
			{new ItemStack(Items.emerald), def_min, def_max, def_weight},
			{new ItemStack(FunctionsVN.getItemFromName(ModObjects.dustyBook_LB)), def_min, def_max, 2}, // Lost Book
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Mason --- //
		
		chestGenHooks = ChestGenHooks.getInfo("vn_mason");
		
		// Number of stacks in a chest
		stacks_min=1;
		stacks_max=5;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.clay_ball), 1, 3, def_weight},
			{new ItemStack(Items.flower_pot), def_min, def_max, def_weight},
			{new ItemStack(Blocks.stone), def_min, def_max, 2},
			{new ItemStack(Blocks.stonebrick), def_min, def_max, 2},
			{new ItemStack(Items.bread), 1, 4, 4},
			{new ItemStack(Items.dye, 1, 11), def_min, def_max, def_weight}, // Dandelion Yellow
			{new ItemStack((Block) ModObjects.chooseModSmoothStoneBlockObject()[0], 1, (Integer) ModObjects.chooseModSmoothStoneBlockObject()[1]), def_min, def_max, def_weight},
			{new ItemStack(Items.emerald), def_min, def_max, def_weight},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		// --- Shepherd --- //
		
		chestGenHooks = ChestGenHooks.getInfo("vn_shepherd");
		
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
			{new ItemStack(Items.emerald), def_min, def_max, def_weight},
			{new ItemStack(Items.shears), def_min, def_max, def_weight},
			{new ItemStack(Items.wheat), 1, 6, 6},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Tannery --- //
		
		chestGenHooks = ChestGenHooks.getInfo("vn_tannery");
		
		// Number of stacks in a chest
		stacks_min=1;
		stacks_max=5;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.leather), 1, 3, def_weight},
			{new ItemStack(Items.leather_chestplate), def_min, def_max, 2},
			{new ItemStack(Items.leather_boots), def_min, def_max, 2},
			{new ItemStack(Items.leather_helmet), def_min, def_max, 2},
			{new ItemStack(Items.bread), 1, 4, 5},
			{new ItemStack(Items.leather_leggings), def_min, def_max, 2},
			{new ItemStack(Items.saddle), def_min, def_max, def_weight},
			{new ItemStack(Items.emerald), 1, 4, def_weight},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Temple --- //
		
		chestGenHooks = ChestGenHooks.getInfo("vn_temple");
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.redstone), 1, 4, 2},
			{new ItemStack(Items.bread), 1, 4, 7},
			{new ItemStack(Items.rotten_flesh), 1, 4, 7},
			{new ItemStack(Items.dye), 1, 11, def_weight}, // Lapis Lazuli
			{new ItemStack(Items.gold_ingot), 1, 4, def_weight},
			{new ItemStack(Items.emerald), 1, 4, def_weight},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Toolsmith --- //
		
		chestGenHooks = ChestGenHooks.getInfo("vn_toolsmith");
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.diamond), 1, 3, def_weight},
			{new ItemStack(Items.iron_ingot), 1, 5, 5},
			{new ItemStack(Items.gold_ingot), 1, 3, def_weight},
			{new ItemStack(Items.bread), 1, 3, 15},
			{new ItemStack(Items.iron_pickaxe), def_min, def_max, 5},
			{new ItemStack(Items.coal), 1, 3, def_weight},
			{new ItemStack(Items.stick), 1, 3, 20},
			{new ItemStack(Items.iron_shovel), def_min, def_max, 5},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
		
		
		// --- Weaponsmith --- //
		
		chestGenHooks = ChestGenHooks.getInfo("vn_weaponsmith");
		
		// Number of stacks in a chest
		stacks_min=3;
		stacks_max=8;
		
		chestGenHooks.setMin(stacks_min); chestGenHooks.setMax(stacks_max+1);
		
		// Register chest entries: ItemStack, stackMin, stackMax, weight
		for (Object[] chestItemObject : new Object[][]{
			{new ItemStack(Items.diamond), 1, 3, 3},
			{new ItemStack(Items.iron_ingot), 1, 5, 10},
			{new ItemStack(Items.gold_ingot), 1, 3, 5},
			{new ItemStack(Items.bread), 1, 3, 15},
			{new ItemStack(Items.apple), 1, 3, 15},
			{new ItemStack(Items.iron_pickaxe), def_min, def_max, 5},
			{new ItemStack(Items.iron_sword), def_min, def_max, 5},
			{new ItemStack(Items.iron_chestplate), def_min, def_max, 5},
			{new ItemStack(Items.iron_helmet), def_min, def_max, 5},
			{new ItemStack(Items.iron_leggings), def_min, def_max, 5},
			{new ItemStack(Items.iron_boots), def_min, def_max, 5},
			{new ItemStack(Blocks.obsidian), 3, 7, 5},
			{new ItemStack(Blocks.sapling, 1, 0), 3, 7, 5}, // Oak Sapling
			{new ItemStack(Items.saddle), def_min, def_max, 3},
			{new ItemStack(Items.iron_horse_armor), def_min, def_max, def_weight},
			{new ItemStack(Items.golden_horse_armor), def_min, def_max, def_weight},
			{new ItemStack(Items.diamond_horse_armor), def_min, def_max, def_weight},
		})
		{
			if (chestItemObject[0] != null) {chestGenHooks.addItem(new WeightedRandomChestContent((ItemStack)chestItemObject[0], (Integer)chestItemObject[1], (Integer)chestItemObject[2], (Integer)chestItemObject[3]));}
		}
		
	}
	
	public static String getGenericLootForVillageType(FunctionsVN.VillageType villageType)
	{
		switch (villageType)
		{
		default:
		case PLAINS:  return "vn_plains_house";
		case DESERT:  return "vn_desert_house";
		case TAIGA:   return "vn_taiga_house";
		case SAVANNA: return "vn_savanna_house";
		case SNOWY:   return "vn_snowy_house";
		case JUNGLE:  return "vn_jungle_house";
		case SWAMP:   return "vn_swamp_house";
		}
	}
}
