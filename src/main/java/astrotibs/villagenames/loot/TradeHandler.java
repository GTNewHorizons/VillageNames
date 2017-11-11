package astrotibs.villagenames.loot;
/*
import java.util.Random;

import astrotibs.villagenames.item.ModItems;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
*/
/**
 * CAN'T USE THIS IN 1.8 AND UP HERP DERP
 */

// The following examples are from coolAlias
// https://github.com/coolAlias/Forge_Tutorials/blob/master/AddingCustomVillagerTrades.java
/*
public class TradeHandler implements IVillageTradeHandler {
	
	@Override
	public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random) {
		
		switch(villager.getProfession()) {
		case 0: // FARMER
			break;
		case 1: // LIBRARIAN
			
			// Trading one itemstack for the codex
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 3), 
					new ItemStack(ModItems.codex, 1)));
			// Trading two itemstacks for the codex
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 2), new ItemStack(Items.gold_ingot, 2 + random.nextInt(2+1) ),
					new ItemStack(ModItems.codex, 1)));
			// Trading two itemstacks for the codex
			recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 2), new ItemStack(Items.iron_ingot, 4 + random.nextInt(4+1) ),
					new ItemStack(ModItems.codex, 1)));
			
			break;
		case 2: // PRIEST
			break;
		case 3: // BLACKSMITH
			break;
		case 4: // BUTCHER
			break;
		default:
			break;
		}
		
	}
	
}
*/
/**
 * Different versions of a trade
 */

//standard trade
//recipeList.add(new MerchantRecipe(new ItemStack(Item.emerald, 2), new ItemStack(YourMod.youritem, 1)));


//use metadata in either case
//recipeList.add(new MerchantRecipe(new ItemStack(Item.dye, 4, 15), // dye of metadata 15 is bonemeal, so we need 4 bonemeals
//	new ItemStack(YourMod.youritem, 1, 6))); // to buy 1 mod item of metadata value 6


//use the vanilla Item method to easily construct an ItemStack containing an enchanted book of any level
//	recipeList.add(new MerchantRecipe(new ItemStack(Item.diamond, 1), Item.enchantedBook.getEnchantedItemStack(new EnchantmentData(Enchantment.flame, 1))));


//trading two itemstacks for one itemstack in return
//	recipeList.add(new MerchantRecipe(new ItemStack(Item.emerald, 6), new ItemStack(YourMod.youritem1, 2), new ItemStack(YourMod.youritem2, 2)));


//using the passed in Random to randomize amounts; nextInt(value) returns an int between 0 and value (non-inclusive)
//	recipeList.add(new MerchantRecipe(new ItemStack(Item.emerald, 6 + random.nextInt(6)), new ItemStack(YourMod.youritem1, 5 + random.nextInt(4)), new ItemStack(YourMod.youritem2, 1)));


//You can also add directly to the villager with 2 different methods:

// Method 1: takes the list, an item ID that may be bought OR sold, rand, and a float value that
// determines how common the trade is. The price of the item is determined in the HashMap
// blacksmithSellingList, which we'll add our custom Item to first:
//villager.blacksmithSellingList.put(Integer.valueOf(YourMod.yourItem.itemID), new Tuple(Integer.valueOf(4), Integer.valueOf(8)));
// Then add the trade, which will buy or sell for between 4 and 8 emeralds
//villager.addBlacksmithItem(recipeList, ItemToTrade.itemID, random, 0.5F);

// Method 2: Basically the same as above, but only for selling items and at a fixed price of 1 emerald
// However, the stack sold will have a variable size determined by the HashMap villagerStockList,
// to which we first need to add our custom Item:
//villager.villagerStockList.put(Integer.valueOf(YourMod.YourItem.itemID), new Tuple(Integer.valueOf(16), Integer.valueOf(24)));
// Then add the trade, which will sell between 16 and 24 of our Item for 1 emerald
//villager.addMerchantItem(recipeList, ItemToSell.itemID, random, 0.5F);
