package astrotibs.villagenames.handler;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.item.ModItems;
import cpw.mods.fml.common.Loader;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class ChestLootHandler {
	
	public static void init() {
		
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
	}
	
	
	
	public static void iglooChest() {
		
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
	
}
