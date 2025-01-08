package astrotibs.villagenames.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import astrotibs.villagenames.block.ModBlocksVN;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.item.ModItems;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes {

    static String[] oreDyeNames = new String[] { "dyeWhite", "dyeOrange", "dyeMagenta", "dyeLightBlue", "dyeYellow",
        "dyeLime", "dyePink", "dyeGray", "dyeLightGray", "dyeCyan", "dyePurple", "dyeBlue", "dyeBrown", "dyeGreen",
        "dyeRed", "dyeBlack" };

    public static void init() {
        // This is just a method inside this class that will register all the recipes.

        if (GeneralConfig.addLunarinBlocks) {
            // Shapeless oreDict recipe
            GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                    new ItemStack(ModBlocksVN.lunarinGoldBlock),
                    "ingotGold",
                    "ingotGold",
                    "ingotGold",
                    "ingotGold",
                    "ingotGold",
                    "ingotGold")); // Much better
            // Turn Lunarin block back to ingots
            GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                    new ItemStack(Items.gold_ingot, 6),
                    new ItemStack(ModBlocksVN.lunarinGoldBlock)));

            // Shapeless oreDict recipe
            GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                    new ItemStack(ModBlocksVN.lunarinIronBlock),
                    "ingotIron",
                    "ingotIron",
                    "ingotIron",
                    "ingotIron",
                    "ingotIron",
                    "ingotIron")); // Much better
            // Turn Lunarin block back to ingots
            GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                    new ItemStack(Items.iron_ingot, 6),
                    new ItemStack(ModBlocksVN.lunarinIronBlock)));
        }

        if (GeneralConfig.addConcrete) {
            // Add recipes for Glazed Terracotta
            for (int i = 0; i < 4; i++) {
                GameRegistry.addSmelting(
                    new ItemStack(Blocks.stained_hardened_clay, 1, i),
                    new ItemStack(ModBlocksVN.glazedTerracotta, 1, i),
                    0.1F);
                GameRegistry.addSmelting(
                    new ItemStack(Blocks.stained_hardened_clay, 1, i + 4),
                    new ItemStack(ModBlocksVN.glazedTerracotta2, 1, i + 4),
                    0.1F);
                GameRegistry.addSmelting(
                    new ItemStack(Blocks.stained_hardened_clay, 1, i + 8),
                    new ItemStack(ModBlocksVN.glazedTerracotta3, 1, i + 8),
                    0.1F);
                GameRegistry.addSmelting(
                    new ItemStack(Blocks.stained_hardened_clay, 1, i + 12),
                    new ItemStack(ModBlocksVN.glazedTerracotta4, 1, i + 12),
                    0.1F);
            }
            // Add recipes for Concrete Powder
            for (int i = 0; i < 16; i++) {
                GameRegistry.addRecipe(
                    new ShapelessOreRecipe(
                        new ItemStack(ModBlocksVN.blockConcretePowder, 8, i),
                        oreDyeNames[i],
                        new ItemStack(Blocks.sand, 1, 0),
                        new ItemStack(Blocks.sand, 1, 0),
                        new ItemStack(Blocks.sand, 1, 0),
                        new ItemStack(Blocks.sand, 1, 0),
                        new ItemStack(Blocks.gravel, 1, 0),
                        new ItemStack(Blocks.gravel, 1, 0),
                        new ItemStack(Blocks.gravel, 1, 0),
                        new ItemStack(Blocks.gravel, 1, 0)));
            }
        }

        if (GeneralConfig.addPrismarine) {
            OreDictionary.registerOre("shardPrismarine", new ItemStack(ModItems.prismarine_shard));
            OreDictionary.registerOre("crystalPrismarine", new ItemStack(ModItems.prismarine_crystals));
            OreDictionary.registerOre(
                "blockPrismarine",
                new ItemStack(ModBlocksVN.blockPrismarine, 1, OreDictionary.WILDCARD_VALUE));

            int rough = 0;
            int bricks = 1;
            int dark = 2;

            GameRegistry.addRecipe(
                new ShapedOreRecipe(
                    new ItemStack(ModBlocksVN.blockPrismarine, 1, dark),
                    "xxx",
                    "xyx",
                    "xxx",
                    'x',
                    "shardPrismarine",
                    'y',
                    "dyeBlack"));
            GameRegistry.addRecipe(
                new ShapedOreRecipe(
                    new ItemStack(ModBlocksVN.blockPrismarine, 1, rough),
                    "xx",
                    "xx",
                    'x',
                    "shardPrismarine"));
            GameRegistry.addRecipe(
                new ShapedOreRecipe(
                    new ItemStack(ModBlocksVN.blockPrismarine, 1, bricks),
                    "xxx",
                    "xxx",
                    "xxx",
                    'x',
                    "shardPrismarine"));
            GameRegistry.addRecipe(
                new ShapedOreRecipe(
                    new ItemStack(ModBlocksVN.blockSeaLantern),
                    "xyx",
                    "yyy",
                    "xyx",
                    'x',
                    "shardPrismarine",
                    'y',
                    "crystalPrismarine"));
        }

        if (GeneralConfig.addSponges) {
            // Sponge stuff
            GameRegistry.addSmelting(
                new ItemStack(ModBlocksVN.blockSpongeVN, 1, 1),
                new ItemStack(ModBlocksVN.blockSpongeVN, 1, 0),
                0.0F);
            // Allow vanilla sponge to be crafted into functional sponge and vice-versa
            GameRegistry.addRecipe(
                new ShapelessOreRecipe(new ItemStack(Blocks.sponge), new ItemStack(ModBlocksVN.blockSpongeVN, 1, 0)));
            GameRegistry.addRecipe(
                new ShapelessOreRecipe(new ItemStack(ModBlocksVN.blockSpongeVN, 1, 0), new ItemStack(Blocks.sponge)));
        }
    }
}
