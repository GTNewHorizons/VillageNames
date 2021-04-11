package astrotibs.villagenames.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import astrotibs.villagenames.VillageNames;
import astrotibs.villagenames.banner.BannerGenerator;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.ieep.ExtendedVillager;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.item.ModItems;
import astrotibs.villagenames.name.NameGenerator;
import astrotibs.villagenames.utility.FunctionsVN;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.MathHelper;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;




/**
 * Different versions of a trade
 */

//The following examples are from coolAlias
//https://github.com/coolAlias/Forge_Tutorials/blob/master/AddingCustomVillagerTrades.java


//standard trade
//FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(new ItemStack(Item.emerald, 2), new ItemStack(YourMod.youritem, 1)));


//use metadata in either case
//FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(new ItemStack(Item.dye, 4, 15), // dye of metadata 15 is bonemeal, so we need 4 bonemeals
//	new ItemStack(YourMod.youritem, 1, 6))); // to buy 1 mod item of metadata value 6


//use the vanilla Item method to easily construct an ItemStack containing an enchanted book of any level
//	FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(new ItemStack(Item.diamond, 1), Item.enchantedBook.getEnchantedItemStack(new EnchantmentData(Enchantment.flame, 1))));


//trading two itemstacks for one itemstack in return
//	FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(new ItemStack(Item.emerald, 6), new ItemStack(YourMod.youritem1, 2), new ItemStack(YourMod.youritem2, 2)));


//using the passed in Random to randomize amounts; nextInt(value) returns an int between 0 and value (non-inclusive)
//	FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(new ItemStack(Item.emerald, 6 + random.nextInt(6)), new ItemStack(YourMod.youritem1, 5 + random.nextInt(4)), new ItemStack(YourMod.youritem2, 1)));


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



/**
 * I'm using this class both to seed trades that are used by 1.8+,
 * and to evaluate trades that should be re-generated on a per-career basis.
 * @author AstroTibs
 */
public class VillagerTradeHandler implements IVillageTradeHandler {
	
	@Override
	public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random) {
		
		int profession = villager.getProfession();
		int career = (ExtendedVillager.get(villager)).getCareer();
		
		MerchantRecipeList buyingList = ReflectionHelper.getPrivateValue( EntityVillager.class, villager, new String[]{"buyingList", "field_70963_i"} );
		int nextSlotToFill = (buyingList == null ? 0 : buyingList.size()) + 1;
		
		Item moditem; // Used as a placeholder to attempt to add modded trades to villagers
		ItemStack moditemstack; // Used as a placeholder when a generator function checks multiple mods for this item
		ItemStack itemStackColorizable; // Holder for itemstacks that can be dyed
		int enchantvalue; // Used as a holder for enchantment levels to help tweak enchanted item prices
		// These are used when a trade asks for "1 or 2" color variations.
		int color1;
		int color2;
		Enchantment enchantment; // This is used for books
		int enchLevel; // For selecting random enchantment levels
		
		switch(profession) {
			
			// summon Villager ~ ~ ~ {Profession:0}
			case 0: // FARMER
				
				switch(career) {
				
					case 1: // Farmer
						
						if (GeneralConfig.modernVillagerTrades)
						{
							// Level 1: Novice
							if (nextSlotToFill == 1 || nextSlotToFill > 5)
							{
								// Wheat to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.wheat, FunctionsVN.modernTradeCostBySlot(20, 1, nextSlotToFill, 1) ), 
										new ItemStack( Items.emerald, 1 ) ) );
								
								// Potato to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.potato, FunctionsVN.modernTradeCostBySlot(26, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.emerald, 1 ) ) );
								
								// Carrot to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.carrot, FunctionsVN.modernTradeCostBySlot(22, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.emerald, 1 ) ) );
								
								// Beetroot to Emerald
								moditemstack = ModObjects.chooseModBeetrootItem();
								if (moditemstack!=null)
								{
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( moditemstack.getItem(), FunctionsVN.modernTradeCostBySlot(15, 1, nextSlotToFill, 1) ),
											new ItemStack( Items.emerald, 1 ) ) );
								}
								
								// Emerald to Bread
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.bread, 6 ) ) );
							}
							
							// Level 2: Apprentice
							if (nextSlotToFill == 2 || nextSlotToFill > 5)
							{
								// Pumpkin to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Item.getItemFromBlock(Blocks.pumpkin), FunctionsVN.modernTradeCostBySlot(6, 1, nextSlotToFill, 2) ),
										new ItemStack( Items.emerald, 1 ) ) );
								// Emerald to Pumpkin Pie
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
										new ItemStack( Items.pumpkin_pie, 4 ) ) );
								// Emerald to Apple
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
										new ItemStack( Items.apple, 4 ) ) );
							}
							
							// Level 3: Journeyman
							if (nextSlotToFill == 3 || nextSlotToFill > 5)
							{
								// Emerald to Cookie
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 1, nextSlotToFill, 3) ),
										new ItemStack( Items.cookie, 18 ) ) );
								// Melon slice to Emerald (Melon BLOCK in Java)
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Item.getItemFromBlock(Blocks.melon_block), FunctionsVN.modernTradeCostBySlot(4, 1, nextSlotToFill, 3) ),
										new ItemStack( Items.emerald, 1 ) ) );
							}
							
							// Level 4: Expert
							if (nextSlotToFill == 4 || nextSlotToFill > 5)
							{
								// Emerald to Cake
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 4) ),
										new ItemStack( Items.cake, 1 ) ) );
								// Egg to Emerald in BE
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.egg, FunctionsVN.modernTradeCostBySlot(16, 0, nextSlotToFill, 4) ),
										new ItemStack( Items.emerald, 1 ) ) );
								// This is Suspicious Stew in Java
								moditemstack = ModObjects.chooseModSuspiciousStewRandom(random); // One of six types
								if (moditemstack != null) {
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, 1 ), moditemstack
											));}
							}
							
							// Level 5: Master
							if (nextSlotToFill >= 5)
							{
								// Emerald to Golden Carrot
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 1, nextSlotToFill, 5) ),
										new ItemStack( Items.golden_carrot, 3 ) ) );
								// Emerald to Glistering Melon
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(4, 1, nextSlotToFill, 5) ),
										new ItemStack( Items.speckled_melon, 1 ) ) );							}
						}
						else
						{
							if (nextSlotToFill == 1 || nextSlotToFill > 4) {
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.potato, 15 + random.nextInt(5) ), new ItemStack(Items.emerald, 1 )) );
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.carrot, 15 + random.nextInt(5) ), new ItemStack(Items.emerald, 1 )) );
							}
							if (nextSlotToFill == 2 || nextSlotToFill > 4) {
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Item.getItemFromBlock(Blocks.pumpkin), 8 + random.nextInt(6) ), new ItemStack(Items.emerald, 1 )) );
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.emerald, 1 ), new ItemStack(Items.pumpkin_pie, 2 + random.nextInt(2) )) );
							}
							if (nextSlotToFill == 3 || nextSlotToFill > 4) {
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Item.getItemFromBlock(Blocks.melon_block), 7 + random.nextInt(6) ), new ItemStack(Items.emerald, 1 )) );
							}
							if (nextSlotToFill >= 4) {
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack(Items.emerald, 1 ), new ItemStack( Items.cake, 1 )) );
							}
						}
						
						break;
						
					case 2: // Fisherman

						if (GeneralConfig.modernVillagerTrades)
						{
							// Level 1: Novice
							if (nextSlotToFill == 1 || nextSlotToFill > 5)
							{
								// String to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.string, FunctionsVN.modernTradeCostBySlot(20, 1, nextSlotToFill, 1) ), 
										new ItemStack( Items.emerald, 1 ) ) );
								// Coal to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.coal, FunctionsVN.modernTradeCostBySlot(10, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.emerald, 1 ) ) );
								// Emerald and raw Cod to cooked Cod
								if (VillageNames.canVillagerTradesDistinguishMeta)
								{
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 1) ),
											new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(6, 0, nextSlotToFill, 1), 0 ),
											new ItemStack( Items.cooked_fished, 6, 0 ) ) );
								}
								else
								{
									// SUBSTITUTE TRADE: Emerald for raw Cod
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 1) ),
											new ItemStack( Items.fish, 5, 0 ) ) );
								}
								// TODO - Emerald to Bucket of Cod
							}
							
							// Level 2: Apprentice
							if (nextSlotToFill == 2 || nextSlotToFill > 5)
							{
								// Raw Cod to Emerald
								if (VillageNames.canVillagerTradesDistinguishMeta)
								{
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(15, 1, nextSlotToFill, 2), 0 ),
											new ItemStack( Items.emerald, 1 ) ) );
								}
								else
								{
									// SUBSTITUTE TRADE: Emerald for cooked Cod
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
											new ItemStack( Items.cooked_fished, 6, 0 ) ) );
								}
								// Emerald and raw Salmon to cooked Salmon
								if (VillageNames.canVillagerTradesDistinguishMeta)
								{
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
											new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(6, 0, nextSlotToFill, 2), 1 ),
											new ItemStack( Items.cooked_fished, 6, 1 ) ) );
								}
								else
								{
									// SUBSTITUTE TRADE: Emerald for raw Salmon
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
											new ItemStack( Items.fish, 7, 1 ) ) );
								}
								
								// TODO - Emerald to Campfire
								moditemstack =  new ItemStack(Item.getItemFromBlock(Block.getBlockFromName(ModObjects.campfirebackport)));
								if (moditemstack != null)
								{
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(2, 1, nextSlotToFill, 2) ),
											moditemstack ) );
								}
							}
							
							// Level 3: Journeyman
							if (nextSlotToFill == 3)
							{
								// Emerald to enchanted Fishing Rod
								for (int i=0; i<2 ; i++) {
									enchantvalue = 5 + random.nextInt(15);
									recipeList.add(new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(2+random.nextInt(2)), 2, nextSlotToFill, 3) ),
											EnchantmentHelper.addRandomEnchantment(random, new ItemStack(Items.fishing_rod, 1), enchantvalue ) ) );
								}
								
							}
							if (nextSlotToFill == 3 || nextSlotToFill > 5)
							{
								// Raw Salmon to Emerald
								if (VillageNames.canVillagerTradesDistinguishMeta)
								{
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(13, 1, nextSlotToFill, 3), 1 ),
											new ItemStack( Items.emerald, 1 ) ) );
								}
								else
								{
									// SUBSTITUTE TRADE: Emerald for cooked Salmon
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 3) ),
											new ItemStack( Items.cooked_fished, 8, 1 ) ) );
								}
							}
							
							// Level 4: Expert
							if (nextSlotToFill == 4 || nextSlotToFill > 5)
							{
								// Raw Tropical Fish to Emerald
								if (VillageNames.canVillagerTradesDistinguishMeta)
								{
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(6, 0, nextSlotToFill, 4), 2 ),
											new ItemStack( Items.emerald, 1 ) ) );
								}
								else
								{
									// SUBSTITUTE TRADE: Emerald for Tropical Fish
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 4) ),
											new ItemStack( Items.fish, 9, 2 ) ) );
								}
							}
							
							// Level 5: Master
							if (nextSlotToFill == 5)
							{
								// Raw Pufferfish to Emerald
								if (VillageNames.canVillagerTradesDistinguishMeta)
								{
									for (int i=0; i<2 ; i++) {
										recipeList.add(new MerchantRecipe(
												new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(4, 1, nextSlotToFill, 5), 3 ),
												new ItemStack( Items.emerald, 1 ),
												new ItemStack( Items.emerald, 2 )) ); // Added a dud trade so that it will appear on the buying list
									}
								}
								else
								{
									// SUBSTITUTE TRADE: Emerald for Pufferfish
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 5) ),
											new ItemStack( Items.fish, 10, 3 ) ) );
								}
							}
							if (nextSlotToFill >= 5)
							{
								// Oak Boat to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										//new ItemStack( Items.boat, FunctionsVN.modernTradeCostBySlot(1, 0, nextSlotToFill, 5), 3 ),
										new ItemStack( FunctionsVN.returnRandomBoatTypeForVillager(villager), FunctionsVN.modernTradeCostBySlot(1, 0, nextSlotToFill, 5) ),
										new ItemStack( Items.emerald, 1 ) ) );
							}
						}
						else
						{
							if (nextSlotToFill == 1 || nextSlotToFill > 2) {
								// 
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.string, 15 + random.nextInt(6) ), new ItemStack(Items.emerald, 1 )) );
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.coal, 16 + random.nextInt(9) ), new ItemStack(Items.emerald, 1 )) );
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.fish, 6 ), new ItemStack(Items.emerald, 1 ), new ItemStack( Items.cooked_fished, 6 )) );
							}
							if (nextSlotToFill >= 2) {
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.emerald, 7 + random.nextInt(2) ), // Enchanted fishing rod 
										EnchantmentHelper.addRandomEnchantment(random, new ItemStack(Items.fishing_rod, 1), 5 + random.nextInt(15) ) ) );
							}
						}
						
						break;
						
					case 3: // Shepherd

						if (GeneralConfig.modernVillagerTrades)
						{
							// Level 1: Novice
							if (nextSlotToFill == 1 || nextSlotToFill > 5)
							{
								if (VillageNames.canVillagerTradesDistinguishMeta)
								{
									// White Wool to Emerald
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Item.getItemFromBlock(Blocks.wool), FunctionsVN.modernTradeCostBySlot(18, 1, nextSlotToFill, 1), 0 ),
											new ItemStack( Items.emerald, 1 ) ) );
									// Brown Wool to Emerald
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Item.getItemFromBlock(Blocks.wool), FunctionsVN.modernTradeCostBySlot(18, 1, nextSlotToFill, 1), 12 ),
											new ItemStack( Items.emerald, 1 ) ) );
									// Black Wool to Emerald
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Item.getItemFromBlock(Blocks.wool), FunctionsVN.modernTradeCostBySlot(18, 1, nextSlotToFill, 1), 15 ),
											new ItemStack( Items.emerald, 1 ) ) );
									// Gray Wool to Emerald
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Item.getItemFromBlock(Blocks.wool), FunctionsVN.modernTradeCostBySlot(18, 1, nextSlotToFill, 1), 7 ),
											new ItemStack( Items.emerald, 1 ) ) );
								}
								else
								{
									// SUBSTITUTION: Generic wool to Emerald
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Item.getItemFromBlock(Blocks.wool), FunctionsVN.modernTradeCostBySlot(18, 1, nextSlotToFill, 1) ),
											new ItemStack( Items.emerald, 1 ) ) );
								}
								// Emerald to Shears
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(2, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.shears, 1 ) ) );
							}
							
							// Level 2: Apprentice
							if (nextSlotToFill == 2)
							{
								if (VillageNames.canVillagerTradesDistinguishMeta)
								{
									// Gray Dye to Emerald
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.dye, FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 2), 8 ),
											new ItemStack( Items.emerald, 1 ) ) );
									// Lime Dye to Emerald
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.dye, FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 2), 10 ),
											new ItemStack( Items.emerald, 1 ) ) );
									// Light Blue Dye to Emerald
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.dye, FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 2), 12 ),
											new ItemStack( Items.emerald, 1 ) ) );
								}
								else
								{
									// SUBSTITUTION: Villages SELLS dye for emeralds
									// Emerald to Gray Dye
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
											new ItemStack( Items.dye, 12, 8 ) ) );
									// Emerald to Lime Dye
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
											new ItemStack( Items.dye, 12, 10 ) ) );
									// Emerald to Light Blue Dye
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
											new ItemStack( Items.dye, 12, 12 ) ) );
								}
								// Black Dye to Emerald
								while (true)
								{
									moditemstack = ModObjects.chooseModBlackDye();
									if (moditemstack!=null)
									{
										if (VillageNames.canVillagerTradesDistinguishMeta)
										{
											FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
													new ItemStack( moditemstack.getItem(), FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 2), moditemstack.getItemDamage()),
													new ItemStack( Items.emerald, 1 ) ) );
										}
										else
										{
											// SUBSTITUTION: sell dye if you can't distinguish meta
											FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
													new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
													new ItemStack( moditemstack.getItem(), 12, moditemstack.getItemDamage() ) ) );
										}
										break;
									}
									
									// Reverts to vanilla version if no modded version is found
									if (!VillageNames.canVillagerTradesDistinguishMeta) {break;}
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.dye, FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 2), 0 ),
											new ItemStack( Items.emerald, 1 ) ) );
									break;
								}
								// White Dye to Emerald
								while (true)
								{
									moditemstack = ModObjects.chooseModWhiteDye();
									if (moditemstack!=null)
									{
										if (VillageNames.canVillagerTradesDistinguishMeta)
										{
											FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
													new ItemStack( moditemstack.getItem(), FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 2), moditemstack.getItemDamage()),
													new ItemStack( Items.emerald, 1 ) ) );
										}
										else
										{
											// SUBSTITUTION: sell dye if you can't distinguish meta
											FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
													new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
													new ItemStack( moditemstack.getItem(), 12, moditemstack.getItemDamage() ) ) );
										}
										break;
									}
									
									// Reverts to vanilla version if no modded version is found
									if (!VillageNames.canVillagerTradesDistinguishMeta) {break;}
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.dye, FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 2), 15 ),
											new ItemStack( Items.emerald, 1 ) ) );
									break;
								}
							}
							if (nextSlotToFill == 2 || nextSlotToFill > 5)
							{
								// Emerald to Wool
								color1 = random.nextInt(16);
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
										new ItemStack( Item.getItemFromBlock(Blocks.wool), 1, color1 ) ) );
								// Possible second Emerald to Wool
								color2 = random.nextInt(16);
								if (color2 != color1) {
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
											new ItemStack( Item.getItemFromBlock(Blocks.wool), 1, color2 ) ) );
								}
								// Emerald to Carpet
								color1 = random.nextInt(16);
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
										new ItemStack( Item.getItemFromBlock(Blocks.carpet), 4, color1 ) ) );
								// Possible second Emerald to Carpet
								color2 = random.nextInt(16);
								if (color2 != color1) {
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
											new ItemStack( Item.getItemFromBlock(Blocks.carpet), 4, color2 ) ) );
								}
							}
							
							// Level 3: Journeyman
							if (nextSlotToFill == 3)
							{
								if (VillageNames.canVillagerTradesDistinguishMeta)
								{
									// Red Dye to Emerald
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.dye, FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 3), 1 ),
											new ItemStack( Items.emerald, 1 ),
											new ItemStack( Items.emerald, 2 ) ) );
									// Light Gray Dye to Emerald
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.dye, FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 3), 7 ),
											new ItemStack( Items.emerald, 1 ),
											new ItemStack( Items.emerald, 2 ) ) );
									// Pink Dye to Emerald
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.dye, FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 3), 9 ),
											new ItemStack( Items.emerald, 1 ),
											new ItemStack( Items.emerald, 2 ) ) );
									// Yellow Dye to Emerald
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.dye, FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 3), 11 ),
											new ItemStack( Items.emerald, 1 ),
											new ItemStack( Items.emerald, 2 ) ) );
									// Orange Dye to Emerald
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.dye, FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 3), 14 ),
											new ItemStack( Items.emerald, 1 ),
											new ItemStack( Items.emerald, 2 ) ) );
								}
								else
								{
									// SUBSTITUTION: Villages SELLS dye for emeralds
									// Emerald to Red Dye
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 3) ),
											new ItemStack( Items.dye, 12, 1 ) ) );
									// Emerald to Light Gray Dye
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 3) ),
											new ItemStack( Items.dye, 12, 7 ) ) );
									// Emerald to Pink Dye
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 3) ),
											new ItemStack( Items.dye, 12, 9 ) ) );
									// Emerald to Yellow Dye
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 3) ),
											new ItemStack( Items.dye, 12, 11 ) ) );
									// Emerald to Orange Dye
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 3) ),
											new ItemStack( Items.dye, 12, 14 ) ) );
								}
							}
							if (nextSlotToFill == 3 || nextSlotToFill > 5)
							{
								// Emerald to Bed
								while (true)
								{
									moditem = FunctionsVN.getItemFromName(ModObjects.coloredBedItemBV);
									if (moditem != null) {
										color1 = random.nextInt(16);
										FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
												new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 1, nextSlotToFill, 3) ),
												new ItemStack( moditem, 1, color1 ) ) );
										color2 = random.nextInt(16);
										if (color2 != color1) {
											FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
													new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 1, nextSlotToFill, 3) ),
													new ItemStack( moditem, 1, color2 ) ) );
											}
										break;
										}
									moditem = FunctionsVN.getItemFromName(ModObjects.bedCB);
									if (moditem != null) {
										for (int i=(random.nextInt(16)==0 ? 1:0); i<2 ; i++) {
											recipeList.add(new MerchantRecipe(
													new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 1, nextSlotToFill, 3) ),
													new ItemStack( moditem, 1 ) ) );
										}
										break;
										}
									for (int i=(random.nextInt(16)==0 ? 1:0); i<2 ; i++) {
									recipeList.add(new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 1, nextSlotToFill, 3) ),
											new ItemStack( Items.bed, 1 ) ) );
									}
									break;
								}
							}
							
							// Level 4: Expert
							if (nextSlotToFill == 4)
							{
								if (VillageNames.canVillagerTradesDistinguishMeta)
								{
									// Green Dye to Emerald
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.dye, FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 4), 2 ),
											new ItemStack( Items.diamond, 1 ),
											new ItemStack( Items.diamond, 2 ) ) );
									// Purple Dye to Emerald
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.dye, FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 4), 5 ),
											new ItemStack( Items.diamond, 1 ),
											new ItemStack( Items.diamond, 2 ) ) );
									// Cyan Dye to Emerald
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.dye, FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 4), 6 ),
											new ItemStack( Items.diamond, 1 ),
											new ItemStack( Items.diamond, 2 ) ) );
									// Magenta Dye to Emerald
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.dye, FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 4), 13 ),
											new ItemStack( Items.diamond, 1 ),
											new ItemStack( Items.diamond, 2 ) ) );
								}
								else
								{
									// SUBSTITUTION: Villages SELLS dye for emeralds
									// Emerald to Green Dye
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 4) ),
											new ItemStack( Items.dye, 12, 2 ) ) );
									// Emerald to Purple Dye
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 4) ),
											new ItemStack( Items.dye, 12, 5 ) ) );
									// Emerald to Cyan Dye
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 4) ),
											new ItemStack( Items.dye, 12, 6 ) ) );
									// Emerald to Magenta Dye
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 4) ),
											new ItemStack( Items.dye, 12, 13 ) ) );
								}
								// Brown Dye to Emerald
								while (true)
								{
									moditemstack = ModObjects.chooseModBrownDye();
									if (moditemstack!=null)
									{
										if (VillageNames.canVillagerTradesDistinguishMeta)
										{
											FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
													new ItemStack( moditemstack.getItem(), FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 4), moditemstack.getItemDamage()),
													new ItemStack( Items.diamond, 1 ),
													new ItemStack( Items.diamond, 2 ) ) );
										}
										else
										{
											// SUBSTITUTION: sell dye if you can't distinguish meta
											FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
													new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 4) ),
													new ItemStack( moditemstack.getItem(), 12, moditemstack.getItemDamage() ) ) );
										}
										break;
									}
									
									// Reverts to vanilla version if no modded version is found
									if (!VillageNames.canVillagerTradesDistinguishMeta) {break;}
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.dye, FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 4), 3 ),
											new ItemStack( Items.diamond, 1 ),
											new ItemStack( Items.diamond, 2 ) ) );
									break;
								}
								// Blue Dye to Emerald
								while (true)
								{
									moditemstack = ModObjects.chooseModBlueDye();
									if (moditemstack!=null)
									{
										if (VillageNames.canVillagerTradesDistinguishMeta)
										{
											FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
													new ItemStack( moditemstack.getItem(), FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 4), moditemstack.getItemDamage()),
													new ItemStack( Items.diamond, 1 ),
													new ItemStack( Items.diamond, 2 ) ) );
										}
										else
										{
											// SUBSTITUTION: sell dye if you can't distinguish meta
											FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
													new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 4) ),
													new ItemStack( moditemstack.getItem(), 12, moditemstack.getItemDamage() ) ) );
										}
										break;
									}
									
									// Reverts to vanilla version if no modded version is found
									if (!VillageNames.canVillagerTradesDistinguishMeta) {break;}
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.dye, FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 4), 4 ),
											new ItemStack( Items.diamond, 1 ),
											new ItemStack( Items.diamond, 2 ) ) );
									break;
								}
							}
							if (nextSlotToFill == 4 || nextSlotToFill > 5)
							{
								// Emerald to Banner
								moditemstack = ModObjects.chooseModBannerItem();
								if (moditemstack!=null)
								{
									color1 = random.nextInt(16);
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 1, nextSlotToFill, 4) ),
											new ItemStack( moditemstack.getItem(), 1, color1 ) ) );
									color2 = random.nextInt(16);
									if (color2 != color1) {
										FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
												new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 1, nextSlotToFill, 4) ),
												new ItemStack( moditemstack.getItem(), 1, color2 ) ) );
									}
								}
								
							}
							
							// Level 5: Master
							if (nextSlotToFill >= 5)
							{
								// Emerald to Painting
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(2, 1, nextSlotToFill, 5) ),
										new ItemStack( Items.painting, 3 ) ) );
							}
						}
						else
						{
							if (nextSlotToFill >= 2) {
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack(Items.emerald, 1 + random.nextInt(2) ), new ItemStack( Item.getItemFromBlock(Blocks.wool), 1, random.nextInt(16) )) );
							}
						}
						
						break;
						
					case 4: // Fletcher

						if (GeneralConfig.modernVillagerTrades)
						{
							// Level 1: Novice
							if (nextSlotToFill == 1 || nextSlotToFill > 5)
							{
								// Stick to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.stick, FunctionsVN.modernTradeCostBySlot(32, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.emerald, 1 ) ) );
								// Emerald to Arrow
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.arrow, 16 ) ) );
								// Emerald and Gravel to Flint
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 1) ),
										new ItemStack( Item.getItemFromBlock(Blocks.gravel), FunctionsVN.modernTradeCostBySlot(10, 0, nextSlotToFill, 1), 0 ),
										new ItemStack( Items.flint, 10, 0 ) ) );
							}
							
							// Level 2: Apprentice
							if (nextSlotToFill == 2)
							{
								for (int i=0; i<2 ; i++) {
									// Emerald to Bow
									recipeList.add(new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(2, 1, nextSlotToFill, 2) ),
											new ItemStack( Items.bow, 1 ) ) );
								}
								
							}
							if (nextSlotToFill == 2 || nextSlotToFill > 5)
							{
								// Flint to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.flint, FunctionsVN.modernTradeCostBySlot(26, 1, nextSlotToFill, 2) ),
										new ItemStack( Items.emerald, 1 ) ) );
							}
							
							// Level 3: Journeyman
							if (nextSlotToFill == 3 || nextSlotToFill > 5)
							{
								// String to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.string, FunctionsVN.modernTradeCostBySlot(14, 1, nextSlotToFill, 3) ),
										new ItemStack( Items.emerald, 1 ) ) );
								// TODO - Emerald to Crossbow
							}
							
							// Level 4: Expert
							if (nextSlotToFill == 4 || nextSlotToFill == 5)
							{
								// Emerald to Enchanted Bow
								enchantvalue = 5 + random.nextInt(15);
								FunctionsVN.addToListWithCheckMeta(recipeList,  new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(2+random.nextInt(2)), 1, nextSlotToFill, 4) ),
										new ItemStack( Items.emerald, 1 ),
										EnchantmentHelper.addRandomEnchantment(random, new ItemStack(Items.bow, 1), enchantvalue ) ) );
							}
							if (nextSlotToFill == 4 || nextSlotToFill > 5)
							{
								// Feather to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.feather, FunctionsVN.modernTradeCostBySlot(24, 1, nextSlotToFill, 4) ),
										new ItemStack( Items.emerald, 1 ) ) );
							}
							
							// Level 5: Master
							if (nextSlotToFill >= 5)
							{
								// Tripwire Hook to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Item.getItemFromBlock(Blocks.tripwire_hook), FunctionsVN.modernTradeCostBySlot(8, 1, nextSlotToFill, 5) ),
										new ItemStack( Items.emerald, 1 ) ) );
								
								// TODO - Enchanted crossbow
								
								// TODO - Tipped arrows
								// Despite Et Futurum having these, they aren't easy to discriminate
								ItemStack randomTippedArrowStack = FunctionsVN.getRandomTippedArrow(5, random);
								if (randomTippedArrowStack != null)
								{
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, 2 ),
											new ItemStack( Items.arrow, 5 ),
											randomTippedArrowStack
											));
								}
								
							}
						}
						else
						{
							if (nextSlotToFill == 1 || nextSlotToFill > 2) {
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.string, 15 + random.nextInt(6) ), new ItemStack(Items.emerald, 1 )) );
							}
							if (nextSlotToFill >= 2) {
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.emerald, 2 + random.nextInt(2) ), new ItemStack(Items.bow, 1 )) );
							}
						}
						
						break;
				}
				
				break;
			
			// summon Villager ~ ~ ~ {Profession:1}
			case 1: // LIBRARIAN
				
				switch(career) {
				
					case 1: // Librarian

						if (GeneralConfig.modernVillagerTrades)
						{
							// Level 1: Novice
							if (nextSlotToFill == 1)
							{
								for (int i=0; i<2 ; i++) {
									// Emerald and Book for Enchanted Book - placeholder
									recipeList.add(new MerchantRecipe(
							        		new ItemStack(Items.emerald, 1),
							        		new ItemStack(Items.book, 1),
							        		new ItemStack(Items.emerald, 1)
							        		));
								}
								
							}
							if (nextSlotToFill == 1 || nextSlotToFill > 5)
							{
								// Paper to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.paper, FunctionsVN.modernTradeCostBySlot(24, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.emerald, 1 ) ) );
								
								// Emerald to Bookshelf
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(6, 1, nextSlotToFill, 1) ),
										new ItemStack( Item.getItemFromBlock(Blocks.bookshelf), 3 ) ) );
							}
							
							// Level 2: Apprentice
							if (nextSlotToFill == 2)
							{
								for (int i=0; i<2 ; i++) {
									// Emerald and Book for Enchanted Book - placeholder
									recipeList.add(new MerchantRecipe(
							        		new ItemStack(Items.emerald, 1),
							        		new ItemStack(Items.book, 1),
							        		new ItemStack(Items.emerald, 1)
							        		));
								}

							}
							if (nextSlotToFill == 2 || nextSlotToFill > 5)
							{
								// Book to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.book, FunctionsVN.modernTradeCostBySlot(4, 1, nextSlotToFill, 2) ),
										new ItemStack( Items.emerald, 1 ) ) );
								
								moditemstack = ModObjects.chooseModLanternItem();
								if (moditemstack != null)
								{
									// TODO - Emerald to Lantern
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
											moditemstack ) );
								}
								
							}
							
							// Level 3: Journeyman
							if (nextSlotToFill == 3)
							{
								for (int i=0; i<2 ; i++) {
									// Emerald and Book for Enchanted Book - placeholder
									recipeList.add(new MerchantRecipe(
							        		new ItemStack(Items.emerald, 1),
							        		new ItemStack(Items.book, 1),
							        		new ItemStack(Items.emerald, 1)
							        		));
								}
							}
							if (nextSlotToFill == 3 || nextSlotToFill > 5)
							{
								// Ink Sac to Emerald
								if (VillageNames.canVillagerTradesDistinguishMeta)
								{
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.dye, FunctionsVN.modernTradeCostBySlot(5, 1, nextSlotToFill, 3), 0 ),
											new ItemStack( Items.emerald, 1 ) ) );
								}
								else
								{
									// SUBSTITUTION: sell ink instead
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 3) ),
											new ItemStack( Items.dye, 5, 0 ) ) );
								}
								
								// Emerald to Glass
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 0, nextSlotToFill, 3) ),
										new ItemStack( Item.getItemFromBlock(Blocks.glass), 4 ) ) );
							}
							
							// Level 4: Expert
							if (nextSlotToFill == 4)
							{
								for (int i=0; i<2 ; i++) {
									// Emerald and Book for Enchanted Book - placeholder
									recipeList.add(new MerchantRecipe(
							        		new ItemStack(Items.emerald, 1),
							        		new ItemStack(Items.book, 1),
							        		new ItemStack(Items.emerald, 1)
							        		));
								}
							}
							if (nextSlotToFill == 4 || nextSlotToFill > 5)
							{
								// Book and Quill to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.writable_book, 1 ),
										new ItemStack( Items.emerald, 1 ) ) );
								
								// Emerald to Clock
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(5, 1, nextSlotToFill, 4) ),
										new ItemStack( Items.clock, 1 ) ) );
								
								// Emerald to Compass
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(4, 1, nextSlotToFill, 4) ),
										new ItemStack( Items.compass, 1 ) ) );
							}
							
							// Level 5: Master
							if (nextSlotToFill >= 5)
							{
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(20, 1, nextSlotToFill, 5) ),
										new ItemStack( Items.name_tag, 1 ) ) );
							}
							
						}
						else
						{
							if (
									   nextSlotToFill == 1
									|| nextSlotToFill == 4
									|| nextSlotToFill == 5
									|| nextSlotToFill > 6
									) {
								//addRandomBookEnchantmentToMerchantList(recipeList, villager, random); // Enchanted book
								
								float adjustedProb = 0.07F + (buyingList==null ? 0.0F : MathHelper.sqrt_float((float)buyingList.size()) * 0.2F);
						        
						        // IDK why EntityVillager.adjustProbability does this
						        adjustedProb = adjustedProb > 0.9F ? 0.9F - (adjustedProb - 0.9F) : adjustedProb;
						        
						        if (villager.worldObj.rand.nextFloat() < adjustedProb) {
						        	enchantment = Enchantment.enchantmentsBookList[villager.worldObj.rand.nextInt(Enchantment.enchantmentsBookList.length)];
							        int i1 = MathHelper.getRandomIntegerInRange(villager.worldObj.rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
							        recipeList.addToListWithCheck(new MerchantRecipe(
							        		new ItemStack(Items.book),
							        		new ItemStack(Items.emerald, Math.min( (villager.worldObj.rand.nextInt(5 + i1 * 10) + 3 * i1), 64) ),
							        		Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, i1))
							        		));
						        }
						        
							}
							if (nextSlotToFill >= 6) {
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.emerald, 20 + random.nextInt(3) ), new ItemStack(Items.name_tag, 1 )) );
							}
							
							if (nextSlotToFill >= 7) {
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.emerald, 20 + random.nextInt(3) ), new ItemStack(Items.name_tag, 1 )) );
							}
						}
						
						// Treasure trades here, irrespective of if modern trades is on
						if (
								GeneralConfig.treasureTrades
								&& nextSlotToFill >= (GeneralConfig.modernVillagerTrades ? 6 : 8)
								) {
							
							// Specialty trades added to reward you for exploring
							
							int enchantLevel = 1; //used for level-based enchant books 
							
							
							
							// --- MINESHAFT --- //
							
							enchantLevel = 4 + villager.worldObj.rand.nextInt(2);
					        MerchantRecipe mineshaftForEnchantBook = new MerchantRecipe(
			        				new ItemStack(ModItems.mineshaftBook, 1),
			        				new ItemStack(Items.emerald, Math.min( (villager.worldObj.rand.nextInt(1 + enchantLevel * 9) + 3 * enchantLevel), 64) ),
			        				Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(Enchantment.efficiency, enchantLevel))
					        		);
							recipeList.add( mineshaftForEnchantBook );
					        
					        enchantLevel = 3;//2 + villager.worldObj.rand.nextInt(2);
					        MerchantRecipe mineshaftForFortuneBook = new MerchantRecipe(
			        				new ItemStack(ModItems.mineshaftBook, 1),
			        				new ItemStack(Items.emerald, Math.min( (villager.worldObj.rand.nextInt(1 + enchantLevel * 9) + 3 * enchantLevel), 64) ),
			        				Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(Enchantment.fortune, enchantLevel))
					        		);
							recipeList.add( mineshaftForFortuneBook );
					        
					        
					        
					        
					        // --- STRONGHOLD --- //
					        
							enchantLevel = 1;
					        MerchantRecipe strongholdForInfinity = new MerchantRecipe(
			        				new ItemStack(ModItems.strongholdBook, 1),
			        				new ItemStack(Items.emerald, Math.min( (villager.worldObj.rand.nextInt(1 + enchantLevel * 9) + 3 * enchantLevel), 64) ),
			        				Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(Enchantment.infinity, enchantLevel))
					        		);
							recipeList.add( strongholdForInfinity );
					        
					        
					        
					        // --- FORTRESS --- //
					        
							enchantLevel = 3 + villager.worldObj.rand.nextInt(2);
					        MerchantRecipe fortressForFeatherBook = new MerchantRecipe(
			        				new ItemStack(ModItems.fortressBook, 1),
			        				new ItemStack(Items.emerald, Math.min( (villager.worldObj.rand.nextInt(1 + enchantLevel * 9) + 3 * enchantLevel), 64) ),
			        				Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(Enchantment.featherFalling, enchantLevel))
					        		);
							recipeList.add( fortressForFeatherBook );
					        
					        
					        
					        // --- MONUMENT --- //
					        
					        if (GeneralConfig.addOceanMonuments) {
								enchantLevel = 1;
						        MerchantRecipe monumentForAquaBook = new MerchantRecipe(
				        				new ItemStack(ModItems.monumentBook, 1),
				        				new ItemStack(Items.emerald, Math.min( (villager.worldObj.rand.nextInt(1 + enchantLevel * 9) + 3 * enchantLevel), 64) ),
				        				Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(Enchantment.aquaAffinity, enchantLevel))
						        		);
								recipeList.add( monumentForAquaBook );
					        }
					        
					        
					        
					        // --- JUNGLE TEMPLE --- //
					        
							enchantLevel = 4 + villager.worldObj.rand.nextInt(2);
					        MerchantRecipe jungleTempleForBaneBook = new MerchantRecipe(
			        				new ItemStack(ModItems.jungletempleBook, 1),
			        				new ItemStack(Items.emerald, Math.min( (villager.worldObj.rand.nextInt(1 + enchantLevel * 9) + 3 * enchantLevel), 64) ),
			        				Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(Enchantment.baneOfArthropods, enchantLevel))
					        		);
							recipeList.add( jungleTempleForBaneBook );
							
					        
					        
					        // --- DESERT PYRAMID --- //
							
					        enchantLevel = 3 + villager.worldObj.rand.nextInt(2);
					        MerchantRecipe desertPyramidForBlastProtectionBook = new MerchantRecipe(
			        				new ItemStack(ModItems.desertpyramidBook, 1),
			        				new ItemStack(Items.emerald, Math.min( (villager.worldObj.rand.nextInt(1 + enchantLevel * 9) + 3 * enchantLevel), 64) ),
			        				Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(Enchantment.blastProtection, enchantLevel))
					        		);
							recipeList.add( desertPyramidForBlastProtectionBook );
							
					        enchantLevel = 4 + villager.worldObj.rand.nextInt(2);
					        MerchantRecipe desertPyramidForSmiteBook = new MerchantRecipe(
			        				new ItemStack(ModItems.desertpyramidBook, 1),
			        				new ItemStack(Items.emerald, Math.min( (villager.worldObj.rand.nextInt(1 + enchantLevel * 9) + 3 * enchantLevel), 64) ),
			        				Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(Enchantment.smite, enchantLevel))
					        		);
							recipeList.add( desertPyramidForSmiteBook );
							
					        
							
							// --- VILLAGE -- //
							
							// Book author
							String bookAuthor = villager.getCustomNameTag();
							
							// Book title
							String bookTitle = "Custom Structure Name";
							switch ( random.nextInt(16) ) {
							case 0:
								// limit:   "--------------------------------"
								//bookTitle = "Your Own Unique Location Name";
								bookTitle = "Your Very Own Unique Location Name"; // Too long
								break;
							case 1:
								// limit:   "--------------------------------"
								//bookTitle = "A Random Gobbledygook Name";
								bookTitle = "A Random Nonsense Gobbledygook Name"; // Too long
								break;
							case 2:
								// limit:   "--------------------------------"
								//bookTitle = "Name Things And Influence People";
								bookTitle = "How To Name Things And Influence People"; // Too long
								break;
							case 3:
								// limit:   "--------------------------------"
								//bookTitle = "Deed To A Non-Existent Place";
								bookTitle = "Deed To A Place That Doesn't Exist"; // Too long
								break;
							case 4:
								bookTitle = "A Brand-New Structure Name";
								break;
							case 5:
								bookTitle = "A New Structure Name For You!";
								break;
							case 6:
								bookTitle = "Naming Things For Dummies";
								break;
							case 7:
								bookTitle = "Naming Things And You";
								break;
							case 8:
								bookTitle = "Live, Laugh, Name, Love";
								break;
							case 9:
								bookTitle = "Markovian Name Generation";
								break;
							case 10:
								bookTitle = "A Tale Of One City Name";
								break;
							case 11:
								bookTitle = "The Case of the Un-Named Thing";
								break;
							case 12:
								bookTitle = "The Unnamed";
								bookAuthor = "H.P. Lovenames";
								break;
							case 13:
								bookTitle = "Custom Structure Name";
								break;
							case 14:
								bookTitle = "Name Inspiration";
								break;
							case 15:
								bookTitle = "A One-Of-A-Kind Title";
								break;
							}
							
							// Book contents
							String bookContents = ""; // Start on line 2
							switch (random.nextInt(8)) {
							case 0:
								bookContents = "If you've gone and built something grand, but don't know what name to give it--why not use this name:";
								break;
							case 1:
								bookContents = "Here's a custom-generated name for you to use, if you wish:";
								break;
							case 2:
								bookContents = "Coming up with names can be difficult. If you're drawing a blank, why not use this name:";
								break;
							case 3:
								bookContents = "Here's a unique name you can give to something if you need some inspiration:";
								break;
							case 4:
								bookContents = Reference.MOD_NAME+" uses a Markov chain to generate names for entities and structures."
										+ " If you've built something and you want to use VN to generate a new name for it, you can use this one:";
								bookAuthor = "AstroTibs";
								break;
							case 5:
								bookContents = "Feeling uninspired? Have writer's block? Feel free to use this customized location name:";
								break;
							case 6:
								bookContents = "Maybe you've just built or discovered something, and you're not sure what to name it. Why not name it this:";
								break;
							case 7:
								bookContents = "Coming up with a good, authentic location name can be hard. Well, this name might be neither good nor authentic, but maybe you'll use it anyway:";
								break;
							}
							
							// Generated name
							String[] locationName = NameGenerator.newRandomName("village-mineshaft-stronghold-temple-fortress-monument-endcity-mansion-alienvillage", new Random());
							bookContents += "\n\n" + (locationName[1]+" "+locationName[2]+" "+locationName[3]).trim();
							
							// Put it all together
							ItemStack bookWithName = new ItemStack(Items.written_book);
							if (bookWithName.stackTagCompound == null) {bookWithName.setTagCompound(new NBTTagCompound());} // Priming the book to receive information
							
							bookWithName.stackTagCompound.setString("title", bookTitle ); // Set the title
							
							if (bookAuthor!=null && !bookAuthor.equals("")) { // Set the author
								try { bookWithName.stackTagCompound.setString("author", bookAuthor.indexOf("(")!=-1 ? bookAuthor.substring(0, bookAuthor.indexOf("(")).trim() : bookAuthor ); }
								// If the target's name starts with a parenthesis for some reason, this will crash with an index OOB exception. In that case, add no author name.
								catch (Exception e) {}
							}
							else {bookWithName.getTagCompound().setString("author", "");}
							
							NBTTagList pagesTag = new NBTTagList();
							pagesTag.appendTag(new NBTTagString(bookContents));
							bookWithName.stackTagCompound.setTag("pages", pagesTag);
							
							// Add the trade
					        recipeList.add( new MerchantRecipe(
									new ItemStack(ModItems.villageBook, 1),
									bookWithName) );
							
						}
						
						break;
						
					case 2: // Cartographer
						// summon Villager ~ ~ ~ {Profession:1}
						if (GeneralConfig.modernVillagerTrades)
						{
							// Level 1: Novice
							if (nextSlotToFill == 1 || nextSlotToFill >= 5)
							{
								// Paper to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.paper, FunctionsVN.modernTradeCostBySlot(24, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.emerald, 1 ) ) );
								// Emerald to Map
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(7, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.map, 1 ) ) );
							}
							
							// Level 2: Apprentice
							if (nextSlotToFill == 2 || nextSlotToFill >= 5)
							{
								// Glass Pane to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Item.getItemFromBlock(Blocks.glass_pane), FunctionsVN.modernTradeCostBySlot(11, 1, nextSlotToFill, 2) ),
										new ItemStack( Items.emerald, 1 ) ) );
								// TODO - Emerald and Compass to Ocean Explorer map
							}
							
							// Level 3: Journeyman 
							if (nextSlotToFill == 3 || nextSlotToFill >= 5)
							{
								// Compass to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.compass, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 3) ),
										new ItemStack( Items.emerald, 1 ) ) );
								// TODO - Emerald and Compass to Woodland Explorer map
							}
							
							// Level 4: Expert 
							if (nextSlotToFill == 4 || nextSlotToFill >= 5)
							{
								// Emerald to Item Frame
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(7, 1, nextSlotToFill, 4) ),
										new ItemStack( Items.item_frame, 1 ) ) );
								
								// Emerald to Banner
								moditemstack = ModObjects.chooseModBannerItem();
								if (moditemstack!=null)
								{
									color1 = random.nextInt(16);
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, 3 ),
											new ItemStack( moditemstack.getItem(), 1, color1 ) ) );
									color2 = random.nextInt(16);
									if (color2 != color1) {
										FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
												new ItemStack( Items.emerald, 3 ),
												new ItemStack( moditemstack.getItem(), 1, color2 ) ) );
									}
								}
								
							}
							
							// Level 5: Master 
							// summon Villager ~ ~ ~ {Profession:1}
							if (nextSlotToFill >= 5)
							{
								// TODO - Globe pattern
								
								// Changed in v3.1banner
								// Instead, offer a banner with the village's pattern!
								
								ItemStack trialbannerstack = ModObjects.chooseModBannerItem();
								
								if (trialbannerstack != null)
								{
									Object[] villageBannerData = BannerGenerator.getVillageBannerData(villager);
		    						NBTTagCompound bannerNBT = new NBTTagCompound();
		    						String villageNameForBanner = "";
		    						if (villageBannerData!=null) {
		    							bannerNBT = (NBTTagCompound) villageBannerData[0];
		    							villageNameForBanner = (String) villageBannerData[1];}
		    						
		    						if (!(villageNameForBanner.equals("")))
		    						{
		    							recipeList.add(new MerchantRecipe(
		    									new ItemStack( Items.emerald, 2 ), (ItemStack)null, BannerGenerator.makeBanner(bannerNBT) ));
		    						}
		    						else // No banner was found or is available. INSTEAD, sell a new banner with a random design.
		    						{
		    							
		    							
		    							if (trialbannerstack != null)
		    							{
		    								Object[] newRandomBanner = BannerGenerator.randomBannerArrays(villager.worldObj.rand, -1, -1);
			    							ArrayList<String> patternArray = (ArrayList<String>) newRandomBanner[0];
			    							ArrayList<Integer> colorArray = (ArrayList<Integer>) newRandomBanner[1];
			    							
			    							recipeList.add(new MerchantRecipe(
			    									new ItemStack( Items.emerald, 3 ), (ItemStack)null, BannerGenerator.makeBanner(patternArray, colorArray) ));
		    							}
		    						}
								}
							}
						}
						else
						{
							if (nextSlotToFill == 2 || nextSlotToFill > 4) {
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.compass, 1 ), new ItemStack(Items.emerald, 1 )) );
							}
							if (nextSlotToFill == 3 || nextSlotToFill > 4) {
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.emerald, 7 + random.nextInt(5) ), new ItemStack(Items.map, 1 )) );
							}
						}
						
						if (
								GeneralConfig.treasureTrades
								&& nextSlotToFill >= (GeneralConfig.modernVillagerTrades ? 6 : 4)
								) {
							
							
							// Potion IDs taken from https://www.minecraftinfo.com/IDList.htm
							
					        
							
							// --- STRONGHOLD --- //
					        MerchantRecipe strongholdForEnderEye = new MerchantRecipe(new ItemStack(ModItems.strongholdBook, 1), new ItemStack(Items.ender_eye, 2));
							recipeList.add( strongholdForEnderEye ); // Ender Eye
							
					        // --- FORTRESS --- //
					        MerchantRecipe fortressForFireResistance = new MerchantRecipe( new ItemStack(ModItems.fortressBook, 1), new ItemStack(Items.potionitem, 1, 8259));
							recipeList.add( fortressForFireResistance ); // Fire Resistance (8:00)
							
							// --- SWAMP HUT --- //
							MerchantRecipe swampHutForHarmingPotion = new MerchantRecipe(new ItemStack(ModItems.swamphutBook, 1), new ItemStack(Items.potionitem, 1, 16428) );
							recipeList.add( swampHutForHarmingPotion ); // Splash Harming II
							
							MerchantRecipe swampHutForHealingPotion = new MerchantRecipe(new ItemStack(ModItems.swamphutBook, 1), new ItemStack(Items.potionitem, 1, 8229) );
							recipeList.add( swampHutForHealingPotion ); // Healing II
							
							// --- MONUMENT --- //
							if (GeneralConfig.addOceanMonuments) {
								MerchantRecipe monumentForWaterBreathing = new MerchantRecipe(new ItemStack(ModItems.monumentBook, 1), new ItemStack(Items.potionitem, 1, 8269));
								recipeList.add( monumentForWaterBreathing ); // Water Breathing (8:00)
							}
							
							// --- JUNGLE TEMPLE --- //
							MerchantRecipe jungleTempleForStrength = new MerchantRecipe(new ItemStack(ModItems.jungletempleBook, 1), new ItemStack(Items.potionitem, 1, 8233));
							recipeList.add( jungleTempleForStrength ); // Strength II (1:30)
							
							// --- IGLOO --- //
							if (GeneralConfig.addIgloos) {
								MerchantRecipe iglooForGoldenApple = new MerchantRecipe(new ItemStack(ModItems.igloobook, 1), new ItemStack(Items.golden_apple, 1));
								//iglooForGoldenApple.func_82783_a(-6);
								recipeList.add( iglooForGoldenApple );
								
								MerchantRecipe iglooForSplashWeakness = new MerchantRecipe(new ItemStack(ModItems.igloobook, 1), new ItemStack(Items.potionitem, 1, 16456));
								//iglooForSplashWeakness.func_82783_a(-6);
								recipeList.add( iglooForSplashWeakness ); // Splash Weakness (3:00)
							}
							
							// --- VILLAGE -- //
							String[] entityName = NameGenerator.newRandomName("villager-angel-demon-dragon-goblin-golem-pet", new Random());
					        ItemStack tagWithName = new ItemStack(Items.name_tag, 1).setStackDisplayName( (entityName[1]+" "+entityName[2]+" "+entityName[3]).trim() );
							tagWithName.setRepairCost(99);
							recipeList.add( new MerchantRecipe(
									new ItemStack(ModItems.villageBook, 1),
									tagWithName) );
					        
						}
						break;
				}
				
				break;
			
			// summon Villager ~ ~ ~ {Profession:2}
			case 2: // PRIEST
				
				switch(career) {
					case 1: // Cleric

						if (GeneralConfig.modernVillagerTrades)
						{
							// Level 1: Novice
							if (nextSlotToFill == 1 || nextSlotToFill > 5)
							{
								// Rotten Flesh to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.rotten_flesh, FunctionsVN.modernTradeCostBySlot(32, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.emerald, 1 ) ) );
								// Emerald to Redstone Dust
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.redstone, 4 ) ) ); // BE: 4 dusts
							}
							
							// Level 2: Apprentice
							if (nextSlotToFill == 2 || nextSlotToFill > 5)
							{
								// Gold Ingot to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.gold_ingot, FunctionsVN.modernTradeCostBySlot(3, 1, nextSlotToFill, 2) ),
										new ItemStack( Items.emerald, 1 ) ) );
								// Emerald to Lapis
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
										new ItemStack( Items.dye, 1, 4 ) ) );
							}
							
							// Level 3: Journeyman
							if (nextSlotToFill == 3 || nextSlotToFill > 5)
							{
								// Rabbit's Foot to Emerald
								while (true)
								{
									moditem = FunctionsVN.getItemFromName(ModObjects.rabbitFootEF);
									if (moditem != null) {
										FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
												new ItemStack( moditem, FunctionsVN.modernTradeCostBySlot(2, 1, nextSlotToFill, 3) ),
												new ItemStack( Items.emerald, 1 ) ) ); break;}
									break;
								}
								// Emerald to Glowstone
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(4, 1, nextSlotToFill, 3) ),
										new ItemStack( Item.getItemFromBlock(Blocks.glowstone), 1 ) ) );
							}
							
							// Level 4: Expert
							if (nextSlotToFill == 4 || nextSlotToFill > 5)
							{
								// TODO - Scute to Emerald
								// Glass Bottle to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.glass_bottle, FunctionsVN.modernTradeCostBySlot(9, 1, nextSlotToFill, 4) ),
										new ItemStack( Items.emerald, 1 ) ) );
								// Emerald to Ender Pearl
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(5, 1, nextSlotToFill, 4) ),
										new ItemStack( Items.ender_pearl, 1 ) ) );
							}
							// Level 5: Master
							if (nextSlotToFill >=5 )
							{
								// Nether Wart to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.nether_wart, FunctionsVN.modernTradeCostBySlot(22, 1, nextSlotToFill, 5) ),
										new ItemStack( Items.emerald, 1 ) ) );
								// Emerald to Experience Bottle
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 1, nextSlotToFill, 5) ),
										new ItemStack( Items.experience_bottle, 1 ) ) );
							}
						}
						else
						{
							if (nextSlotToFill == 1 || nextSlotToFill > 4) {
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.rotten_flesh, 36 + random.nextInt(5) ), new ItemStack(Items.emerald, 1 )) );
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.gold_ingot, 8 + random.nextInt(3) ), new ItemStack(Items.emerald, 1 )) );
							}
							if (nextSlotToFill == 2 || nextSlotToFill > 4) {
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack(Items.emerald, 1 ), new ItemStack( Items.dye, 1 + random.nextInt(2), 4)) ); // Lapis Lazuli
							}
							if (nextSlotToFill == 3 || nextSlotToFill > 4) {
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack(Items.emerald, 1 ), new ItemStack( Items.ender_pearl, 4 + random.nextInt(4) )) ); // Lapis Lazuli
							}
						}
						
						
						break;
				}
				
				break;
			
			// summon Villager ~ ~ ~ {Profession:3}
			case 3: // BLACKSMITH
				
				// v3.1 LOL NAH MATE, adding Stone Mason trades.
				switch(career) {
					case 1: // Armorer
						
						if (GeneralConfig.modernVillagerTrades)
						{
							// Level 1: Novice
							if (nextSlotToFill == 1)
							{
								// Emerald to Iron Boots
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(4, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.iron_boots, 1 ) ) );
								// Emerald to Iron Leggings
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(7, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.iron_leggings, 1 ) ) );
								// Emerald to Iron Helmet
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(5, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.iron_helmet, 1 ) ) );
								// Emerald to Iron Chestplate
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(9, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.iron_chestplate, 1 ) ) );
							}
							if (nextSlotToFill == 1 || nextSlotToFill > 5)
							{
								// Coal to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.coal, FunctionsVN.modernTradeCostBySlot(15, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.emerald, 1 ) ) );
							}
							
							// Level 2: Apprentice
							if (nextSlotToFill == 2)
							{
								// TODO - Bell
								// Emerald to Chainmail Boots
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 2, nextSlotToFill, 2) ),
										new ItemStack( Items.chainmail_boots, 1 ) ) );
								// Emerald to Chainmail Leggings
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 2, nextSlotToFill, 2) ),
										new ItemStack( Items.chainmail_leggings, 1 ) ) );
							}
							if (nextSlotToFill == 2 || nextSlotToFill > 5)
							{
								// Iron Ingot to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.iron_ingot, FunctionsVN.modernTradeCostBySlot(4, 1, nextSlotToFill, 2) ),
										new ItemStack( Items.emerald, 1 ) ) );
							}
							
							// Level 3: Journeyman
							if (nextSlotToFill == 3)
							{
								// Emerald to Chainmail Helmet
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 2, nextSlotToFill, 3) ),
										new ItemStack( Items.chainmail_helmet, 1 ) ) );
								// Emerald to Chainmail Chestplate
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(4, 2, nextSlotToFill, 3) ),
										new ItemStack( Items.chainmail_chestplate, 1 ) ) );
							}
							if (nextSlotToFill == 3 || nextSlotToFill > 5)
							{
								// Lava Bucket to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.lava_bucket, FunctionsVN.modernTradeCostBySlot(1, 0, nextSlotToFill, 3) ),
										new ItemStack( Items.emerald, 1 ) ) );
								// Diamond to Emerald in JE
								
								// TODO - Shield
							}
							
							// Level 4: Expert
							if (nextSlotToFill == 4)
							{
								// Emerald to Enchanted Diamond Leggings
								enchantvalue = 5 + random.nextInt(15);
								FunctionsVN.addToListWithCheckMeta(recipeList,  new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(13+random.nextInt(4)), 1, nextSlotToFill, 4) ),
										EnchantmentHelper.addRandomEnchantment(random, new ItemStack(Items.diamond_leggings, 1), enchantvalue ) ) );
								// Emerald to Enchanted Diamond Boots
								enchantvalue = 5 + random.nextInt(15);
								FunctionsVN.addToListWithCheckMeta(recipeList,  new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(6+random.nextInt(3)), 1, nextSlotToFill, 4) ),
										EnchantmentHelper.addRandomEnchantment(random, new ItemStack(Items.diamond_boots, 1), enchantvalue ) ) );
							}
							if (nextSlotToFill == 4 || nextSlotToFill > 5)
							{
								// Diamond to Emerald in BE
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.diamond, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 4) ),
										new ItemStack( Items.emerald, 1 ) ) );
							}
							
							// Level 5: Master
							if (nextSlotToFill == 5)
							{
								// Emerald to Enchanted Diamond Helmet
								enchantvalue = 5 + random.nextInt(15);
								FunctionsVN.addToListWithCheckMeta(recipeList,  new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(6+random.nextInt(3)), 1, nextSlotToFill, 5) ),
										EnchantmentHelper.addRandomEnchantment(random, new ItemStack(Items.diamond_helmet, 1), enchantvalue ) ) );
								// Emerald to Enchanted Diamond Chestplate
								enchantvalue = 5 + random.nextInt(15);
								FunctionsVN.addToListWithCheckMeta(recipeList,  new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(13+random.nextInt(4)), 2, nextSlotToFill, 5) ),
										EnchantmentHelper.addRandomEnchantment(random, new ItemStack(Items.diamond_chestplate, 1), enchantvalue ) ) );
							}
						}
						
						break;
								
						
					case 2: // Weaponsmith
						
						if (GeneralConfig.modernVillagerTrades)
						{
							// Level 1: Novice
							if (nextSlotToFill == 1)
							{
								for (int i=0; i<2 ; i++) {
									// Emerald to Iron Axe
									recipeList.add(new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 1, nextSlotToFill, 1) ),
											new ItemStack( Items.iron_axe, 1 ) ) );
								}
							}
							if (nextSlotToFill == 1 || nextSlotToFill > 5)
							{
								// Coal to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.coal, FunctionsVN.modernTradeCostBySlot(15, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.emerald, 1 ) ) );
							}
							
							// Level 2: Apprentice
							if (nextSlotToFill == 2)
							{
								// Emerald to Enchanted Iron Sword
								enchantvalue = 5 + random.nextInt(15);
								recipeList.add(  new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(2+random.nextInt(2)), 1, nextSlotToFill, 2) ),
										EnchantmentHelper.addRandomEnchantment(random, new ItemStack(Items.iron_sword, 1), enchantvalue ) ) );
							}
							if (nextSlotToFill == 2 || nextSlotToFill > 5)
							{
								// Iron Ingot to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.iron_ingot, FunctionsVN.modernTradeCostBySlot(4, 1, nextSlotToFill, 2) ),
										new ItemStack( Items.emerald, 1 ) ) );
								// TODO - Bell
							}
							
							// Level 3: Journeyman
							if (nextSlotToFill == 3 || nextSlotToFill > 5)
							{
								// Flint to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.flint, FunctionsVN.modernTradeCostBySlot(24, 1, nextSlotToFill, 3) ),
										new ItemStack( Items.emerald, 1 ) ) );
							}
							
							// Level 4: Expert
							if (nextSlotToFill == 4)
							{
								// Emerald to Enchanted Diamond Axe
								for (int i=0; i<2 ; i++) {
									enchantvalue = 5 + random.nextInt(15);
									recipeList.add(  new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(13+random.nextInt(4)), 1, nextSlotToFill, 4) ),
											EnchantmentHelper.addRandomEnchantment(random, new ItemStack(Items.diamond_axe, 1), enchantvalue ) ) );
								}
								
							}
							if (nextSlotToFill == 4 || nextSlotToFill > 5)
							{
								// Diamond to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.diamond, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 4) ),
										new ItemStack( Items.emerald, 1 ) ) );
							}
							
							// Level 5: Master
							if (nextSlotToFill == 5)
							{
								// Emerald to Enchanted Diamond Sword
								enchantvalue = 5 + random.nextInt(15);
								FunctionsVN.addToListWithCheckMeta(recipeList,  new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(6+random.nextInt(3)), 1, nextSlotToFill, 5) ),
										EnchantmentHelper.addRandomEnchantment(random, new ItemStack(Items.diamond_sword, 1), enchantvalue ) ) );
							}
						}
						
						break;
						
					case 3: // Toolsmith
						
						if (GeneralConfig.modernVillagerTrades)
						{
							// Level 1: Novice
							if (nextSlotToFill == 1)
							{
								// Emerald to Stone Axe
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 2, nextSlotToFill, 1) ),
										new ItemStack( Items.stone_axe, 1 ) ) );
								// Emerald to Stone Shovel
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 2, nextSlotToFill, 1) ),
										new ItemStack( Items.stone_shovel, 1 ) ) );
								// Emerald to Stone Pick
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 2, nextSlotToFill, 1) ),
										new ItemStack( Items.stone_pickaxe, 1 ) ) );
								// Emerald to Stone Hoe
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 2, nextSlotToFill, 1) ),
										new ItemStack( Items.stone_hoe, 1 ) ) );
							}
							if (nextSlotToFill == 1 || nextSlotToFill > 5)
							{
								// Coal to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.coal, FunctionsVN.modernTradeCostBySlot(15, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.emerald, 1 ) ) );
							}
							
							// Level 2: Apprentice
							if (nextSlotToFill == 2 || nextSlotToFill > 5)
							{
								// Iron Ingot to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.iron_ingot, FunctionsVN.modernTradeCostBySlot(4, 1, nextSlotToFill, 2) ),
										new ItemStack( Items.emerald, 1 ) ) );
								// TODO - Bell
							}
							
							// Level 3: Journeyman
							if (nextSlotToFill == 3)
							{
								for (int i=0; i<2 ; i++) {
									// Emerald to Enchanted Iron Axe
									enchantvalue = 5 + random.nextInt(15);
									recipeList.add(new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(2+random.nextInt(2)), 1, nextSlotToFill, 3) ),
											EnchantmentHelper.addRandomEnchantment(random, new ItemStack(Items.iron_axe, 1), enchantvalue ) ) );
									// Emerald to Enchanted Iron Shovel
									enchantvalue = 5 + random.nextInt(15);
									recipeList.add(new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(2+random.nextInt(2)), 2, nextSlotToFill, 3) ),
											EnchantmentHelper.addRandomEnchantment(random, new ItemStack(Items.iron_shovel, 1), enchantvalue ) ) );
									// Emerald to Enchanted Iron Pickaxe
									enchantvalue = 5 + random.nextInt(15);
									recipeList.add(new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(2+random.nextInt(2)), 2, nextSlotToFill, 3) ),
											EnchantmentHelper.addRandomEnchantment(random, new ItemStack(Items.iron_pickaxe, 1), enchantvalue ) ) );
									// Emerald to Enchanted Diamond Hoe
									/*
									enchantvalue = 5 + random.nextInt(15);
									recipeList.add(new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(6+random.nextInt(3)), 2, nextSlotToFill, 3) ),
											EnchantmentHelper.addRandomEnchantment(random, new ItemStack(Items.diamond_hoe, 1), enchantvalue ) ) );
									*/
								}
								
							}
							if (nextSlotToFill == 3 || nextSlotToFill > 5)
							{
								// Flint to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.flint, FunctionsVN.modernTradeCostBySlot(30, 1, nextSlotToFill, 3) ),
										new ItemStack( Items.emerald, 1 ) ) );
								
								// Emerald to Enchanted Diamond Hoe
								// 1.7.10 can't enchant hoes. I'm using the BE emerald price.
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(4, 2, nextSlotToFill, 3) ),
										new ItemStack( Items.diamond_hoe, 1 ) ) );
							}
							
							// Level 4: Expert
							if (nextSlotToFill == 4 || nextSlotToFill > 5)
							{
								// Emerald to Enchanted Diamond Axe
								enchantvalue = 5 + random.nextInt(15);
								FunctionsVN.addToListWithCheckMeta(recipeList,  new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(13+random.nextInt(4)), 2, nextSlotToFill, 4) ),
										EnchantmentHelper.addRandomEnchantment(random, new ItemStack(Items.diamond_axe, 1), enchantvalue ) ) );
								// Emerald to Enchanted Diamond Shovel
								enchantvalue = 5 + random.nextInt(15);
								FunctionsVN.addToListWithCheckMeta(recipeList,  new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(6+random.nextInt(3)), 2, nextSlotToFill, 4) ),
										EnchantmentHelper.addRandomEnchantment(random, new ItemStack(Items.diamond_shovel, 1), enchantvalue ) ) );
							}
							if (nextSlotToFill == 4 || nextSlotToFill > 5)
							{
								// Flint to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.diamond, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 4) ),
										new ItemStack( Items.emerald, 1 ) ) );
							}
							
							// Level 5: Master
							if (nextSlotToFill == 5)
							{
								// Emerald to Enchanted Diamond Pickaxe
								enchantvalue = 5 + random.nextInt(15);
								FunctionsVN.addToListWithCheckMeta(recipeList,  new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(13+random.nextInt(4)), 0, nextSlotToFill, 5) ),
										EnchantmentHelper.addRandomEnchantment(random, new ItemStack(Items.diamond_pickaxe, 1), enchantvalue ) ) );
							}
						}
						
						break;
						
					case 4: // Mason
						
						// Level 1: Novice
						if (nextSlotToFill == 1 || nextSlotToFill > 5)
						{
							// Clay to Emerald
							FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
									new ItemStack( Items.clay_ball, FunctionsVN.modernTradeCostBySlot(10, 1, nextSlotToFill, 1) ),
									new ItemStack( Items.emerald, 1 ) ) );
							// Emerald to Brick
							FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
									new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(4, 1, nextSlotToFill, 1) ),
									new ItemStack( Items.brick, 10 ) ) );
						}
						
						// Level 2: Apprentice
						if (nextSlotToFill == 2 || nextSlotToFill > 5)
						{
							// Stone to Emerald
							FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
									new ItemStack( Item.getItemFromBlock(Blocks.stone), FunctionsVN.modernTradeCostBySlot(20, 1, nextSlotToFill, 2), 0 ),
									new ItemStack( Items.emerald, 1 ) ) );
							// Emerald to Chiseled Stone Brick
							FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
									new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
									new ItemStack( Item.getItemFromBlock(Blocks.stonebrick), 4, 3 ) ) );
						}
						
						// Level 3: Journeyman
						if (nextSlotToFill == 3 || nextSlotToFill > 5)
						{
							int modrocks = 0; // Use this to count how many modded rocks available. If none, use a different trade.
							
							// Granite to Emerald
							moditemstack = ModObjects.chooseModGraniteItem();
							if (moditemstack!=null)
							{
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( moditemstack.getItem(), FunctionsVN.modernTradeCostBySlot(16, 1, nextSlotToFill, 3), moditemstack.getItemDamage() ),
										new ItemStack( Items.emerald, 1 ) ) );
								modrocks++;
							}
							
							// Andesite to Emerald
							moditemstack = ModObjects.chooseModAndesiteItem();
							if (moditemstack!=null)
							{
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( moditemstack.getItem(), FunctionsVN.modernTradeCostBySlot(16, 1, nextSlotToFill, 3), moditemstack.getItemDamage() ),
										new ItemStack( Items.emerald, 1 ) ) );
								modrocks++;
							}
							
							// Diorite to Emerald
							moditemstack = ModObjects.chooseModDioriteItem();
							if (moditemstack!=null)
							{
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( moditemstack.getItem(), FunctionsVN.modernTradeCostBySlot(16, 1, nextSlotToFill, 3), moditemstack.getItemDamage() ),
										new ItemStack( Items.emerald, 1 ) ) );
								modrocks++;
							}
							
							// Emerald to Polished Andesite
							moditemstack = ModObjects.chooseModPolishedAndesiteItem();
							if (moditemstack!=null)
							{
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 3) ),
										new ItemStack( moditemstack.getItem(), 1, moditemstack.getItemDamage() ) ) );
								modrocks++;
							}
							
							// Emerald to Polished Granite
							moditemstack = ModObjects.chooseModPolishedGraniteItem();
							if (moditemstack!=null)
							{
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 3) ),
										new ItemStack( moditemstack.getItem(), 1, moditemstack.getItemDamage() ) ) );
								modrocks++;
							}
							
							// Emerald to Polished Diorite
							moditemstack = ModObjects.chooseModPolishedDioriteItem();
							if (moditemstack!=null)
							{
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 3) ),
										new ItemStack( moditemstack.getItem(), 1, moditemstack.getItemDamage() ) ) );
								modrocks++;
							}
							
							// If you're not using any of these modded rocks, you can't fill slot 3.
							// Should that be the case, fill it instead with an unused Slot 1 or 2 trade.
							if (modrocks == 0)
							{
								// Clay to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.clay_ball, FunctionsVN.modernTradeCostBySlot(10, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.emerald, 1 ) ) );
								// Emerald to Brick
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 1) ),
										new ItemStack( Items.brick, 10 ) ) );
								// Stone to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Item.getItemFromBlock(Blocks.stone), FunctionsVN.modernTradeCostBySlot(20, 1, nextSlotToFill, 2), 0 ),
										new ItemStack( Items.emerald, 1 ) ) );
								// Emerald to Chiseled Stone Brick
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
										new ItemStack( Item.getItemFromBlock(Blocks.stonebrick), 4, 3 ) ) );
							}
							
						}
						// Level 4: Expert
						if (nextSlotToFill == 4)
						{
							
							// Emerald to Glazed Terracotta
							for (int i=0; i<2 ; i++) {
								
								color1 = random.nextInt(16);
								Object[] glazedTerracottaObject = ModObjects.chooseModGlazedTerracotta(color1, 0);
								
								if (glazedTerracottaObject[0] != null)
								{
									recipeList.add(new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 4) ),
											new ItemStack( Item.getItemFromBlock(
													(Block)glazedTerracottaObject[0]
													), 1, (Integer)glazedTerracottaObject[1] ) ) );
								}
								
								
								// Possible second Emerald to Terracotta
								color2 = random.nextInt(16);
								glazedTerracottaObject = ModObjects.chooseModGlazedTerracotta(color2, 0);
								
								if (color2 != color1 && glazedTerracottaObject[0] != null) {
									recipeList.add(new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 4) ),
											new ItemStack( Item.getItemFromBlock(
													(Block)glazedTerracottaObject[0]
													), 1, (Integer)glazedTerracottaObject[1] ) ) );
								}
							}

						}
						if (nextSlotToFill == 4 || nextSlotToFill > 5)
						{
							// Quartz to Emerald
							FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
									new ItemStack( Items.quartz, FunctionsVN.modernTradeCostBySlot(12, 1, nextSlotToFill, 4) ),
									new ItemStack( Items.emerald, 1 ) ) );
							// Emerald to Terracotta
							color1 = random.nextInt(16);
							FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
									new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 4) ),
									new ItemStack( Item.getItemFromBlock(Blocks.stained_hardened_clay), 1, color1 ) ) );
							// Possible second Emerald to Terracotta
							color2 = random.nextInt(16);
							if (color2 != color1) {
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 4) ),
										new ItemStack( Item.getItemFromBlock(Blocks.stained_hardened_clay), 1, color2 ) ) );
							}
						}
						
						// Level 5: Master
						if (nextSlotToFill >= 5)
						{
							// Emerald to Quartz Pillar
							FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
									new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 5) ),
									new ItemStack( Item.getItemFromBlock(Blocks.quartz_block), 1, 2 ) ) );
							// Emerald to Quartz Block
							FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
									new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 5) ),
									new ItemStack( Item.getItemFromBlock(Blocks.quartz_block), 1, 0 ) ) );
						}
						break;
				}
				
				break;
			
			// summon Villager ~ ~ ~ {Profession:4}
			case 4: // BUTCHER
				
				switch(career) {
					case 1: // Butcher

						if (GeneralConfig.modernVillagerTrades)
						{
							// Level 1: Novice
							if (nextSlotToFill == 1 || nextSlotToFill > 3)
							{
								// Raw Chicken to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.chicken, FunctionsVN.modernTradeCostBySlot(14, 1, nextSlotToFill, 1) ), 
										new ItemStack( Items.emerald, 1 ) ) );
								// Raw Porkchop to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.porkchop, FunctionsVN.modernTradeCostBySlot(7, 1, nextSlotToFill, 1) ), 
										new ItemStack( Items.emerald, 1 ) ) );
							}
							if (nextSlotToFill == 1 || nextSlotToFill > 5)
							{

								// Raw Rabbit to Emerald
								while (true)
								{
									moditem = FunctionsVN.getItemFromName(ModObjects.rabbitRawEF);
									if (moditem != null) {
										FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
												new ItemStack( moditem, FunctionsVN.modernTradeCostBySlot(4, 1, nextSlotToFill, 1) ),
												new ItemStack( Items.emerald, 1 ) ) ); break;}
									break;
								}
								// Emerald to Rabbit Stew
								while (true)
								{
									moditem = FunctionsVN.getItemFromName(ModObjects.rabbitStewEF);
									if (moditem != null) {
										FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
												new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 1) ),
												new ItemStack( moditem, 1 ) ) ); break;}
									break;
								}
							}
							
							// Level 2: Apprentice
							if (nextSlotToFill == 2 || nextSlotToFill > 3)
							{
								// Coal to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.coal, FunctionsVN.modernTradeCostBySlot(15, 1, nextSlotToFill, 2) ), 
										new ItemStack( Items.emerald, 1 ) ) );
								// Emerald to Cooked Porkchop
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
										new ItemStack( Items.cooked_porkchop, 5 ) ) );
								// Emerald to Cooked Chicken
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 2) ),
										new ItemStack( Items.cooked_chicken, 8 ) ) );
								
								// BE: Emerald to Cooked Rabbit
								while (true)
								{
									moditem = FunctionsVN.getItemFromName(ModObjects.rabbitCookedEF);
									if (moditem != null) {
										FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
												new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(5, 1, nextSlotToFill, 2) ),
												new ItemStack( moditem, 1 ) ) ); break;}
									break;
								}
								
								// BE: Emerald to Cooked Mutton
								moditemstack = ModObjects.chooseModCookedMutton();
								if (moditemstack!=null)
								{
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( Items.emerald, 1 ),
											new ItemStack( moditemstack.getItem(), FunctionsVN.modernTradeCostBySlot(4, 1, nextSlotToFill, 2) ) ) );
								}
								
							}
							
							// Level 3: Journeyman
							if (nextSlotToFill >= 3)
							{
								// Raw Beef to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.beef, FunctionsVN.modernTradeCostBySlot(10, 1, nextSlotToFill, 3) ), 
										new ItemStack( Items.emerald, 1 ) ) );
							}
							if (nextSlotToFill == 3 || nextSlotToFill > 5)
							{
								// Raw Mutton to Emerald
								moditemstack = ModObjects.chooseModRawMutton();
								if (moditemstack!=null)
								{
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack( moditemstack.getItem(), FunctionsVN.modernTradeCostBySlot(8, 1, nextSlotToFill, 3) ),
											new ItemStack( Items.emerald, 1 ) ) );
								}
								
								
								// BE: Emerald to Steak
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 0, nextSlotToFill, 3) ),
										new ItemStack( Items.cooked_beef, 5 ) ) ); // VN price
							}
							// Level 4: Expert
							if (nextSlotToFill == 4 || nextSlotToFill > 5)
							{
								// Dried Kelp Block to Emerald
								moditemstack = ModObjects.chooseModKelpBlock();
								if (moditemstack!=null)
								{
									FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
											new ItemStack(
													moditemstack.getItem(),
													// Could be one (kelp wrap) or nine (individual kelp)
													MathHelper.clamp_int(FunctionsVN.modernTradeCostBySlot(10, 1, nextSlotToFill, 4)*moditemstack.stackSize, 1, 64),
													moditemstack.getItemDamage() ),
											new ItemStack( Items.emerald, 1 ) ) );
									break;
								}
							}
							// Level 5: Master
							if (nextSlotToFill >= 5)
							{
								// Sweet Berries to Emerald
								while (true)
								{
									moditem = ModObjects.chooseModSweetBerriesItem();
									if (moditem != null) {
										FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
												new ItemStack( moditem, FunctionsVN.modernTradeCostBySlot(10, 1, nextSlotToFill, 5) ),
												new ItemStack( Items.emerald, 1 ) ) ); break;}
									break;
								}
							}
						}
						else
						{
							if (nextSlotToFill == 1 || nextSlotToFill > 2) {
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.chicken, 14 + random.nextInt(5) ), new ItemStack(Items.emerald, 1 )) );
							}
							if (nextSlotToFill >= 2) {
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.emerald, 1 ), new ItemStack( Items.cooked_chicken, 6 + random.nextInt(3) )) ); // Lapis Lazuli
							}
						}
						
						break;
					
					// summon Villager ~ ~ ~ {Profession:4}
					case 2: // Leatherworker
						
						if (GeneralConfig.modernVillagerTrades)
						{
							// Level 1: Novice
							if (nextSlotToFill == 1)
							{
								for (int i=0; i<2 ; i++) {
									// Emerald to Dyed Leather Tunic
									itemStackColorizable = new ItemStack(Items.leather_chestplate);
									itemStackColorizable = FunctionsVN.colorizeItemstack(itemStackColorizable, FunctionsVN.combineDyeColors(new int[]{random.nextInt(16), random.nextInt(16)}));
									recipeList.add( new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(7, 2, nextSlotToFill, 1) ),
											itemStackColorizable ) );
								}
							}
							if (nextSlotToFill == 1 || nextSlotToFill == 4)
							{
								// Emerald to Dyed Leather Pants
								itemStackColorizable = new ItemStack(Items.leather_leggings);
								itemStackColorizable = FunctionsVN.colorizeItemstack(itemStackColorizable, FunctionsVN.combineDyeColors(new int[]{random.nextInt(16), random.nextInt(16)}));
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 2, nextSlotToFill, 1) ),
										itemStackColorizable ) );
							}
							if (nextSlotToFill == 1 || nextSlotToFill == 4 || nextSlotToFill > 5)
							{
								// Leather to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.leather, FunctionsVN.modernTradeCostBySlot(6, 1, nextSlotToFill, 1) ), 
										new ItemStack( Items.emerald, 1 ) ) );
							}
							
							// Level 2: Apprentice
							if (nextSlotToFill == 2 || nextSlotToFill == 4)
							{
								for (int i=0; i<2 ; i++) {
									// Emerald to Dyed Leather Cap
									itemStackColorizable = new ItemStack(Items.leather_helmet);
									itemStackColorizable = FunctionsVN.colorizeItemstack(itemStackColorizable, FunctionsVN.combineDyeColors(new int[]{random.nextInt(16), random.nextInt(16)}));
									recipeList.add( new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(5, 2, nextSlotToFill, 2) ),
											itemStackColorizable ) );
								}
								
							}
							if (nextSlotToFill == 2 || nextSlotToFill == 4 || nextSlotToFill > 5)
							{
								// Flint to Emerald
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.flint, FunctionsVN.modernTradeCostBySlot(26, 1, nextSlotToFill, 2) ), 
										new ItemStack( Items.emerald, 1 ) ) );
								
								// Emerald to Leather Boots
								// The wiki implies that they are not dyed, but I will dye them because why not
								itemStackColorizable = new ItemStack(Items.leather_boots);
								itemStackColorizable = FunctionsVN.colorizeItemstack(itemStackColorizable, FunctionsVN.combineDyeColors(new int[]{random.nextInt(16), random.nextInt(16)}));
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(4, 2, nextSlotToFill, 2) ),
										itemStackColorizable ) );
							}
							
							// Level 3: Journeyman
							if (nextSlotToFill == 3 || nextSlotToFill == 4)
							{
								for (int i=0; i<2 ; i++) {
									// Emerald to Enchanted Dyed Leather Tunic
									itemStackColorizable = new ItemStack(Items.leather_chestplate);
									itemStackColorizable = FunctionsVN.colorizeItemstack(itemStackColorizable, FunctionsVN.combineDyeColors(new int[]{random.nextInt(16), random.nextInt(16)}));
									enchantvalue = 5 + random.nextInt(15);
									recipeList.add(  new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(7, 2, nextSlotToFill, 3) ),
											new ItemStack( Items.emerald, 1 ), // Second emerald to distinguish this from the other tunic trade
											EnchantmentHelper.addRandomEnchantment(random, itemStackColorizable, enchantvalue ) ) );
								}
								
							}
							if (nextSlotToFill == 3 || nextSlotToFill == 4 || nextSlotToFill > 5)
							{
								// Rabbit Hide to Emerald
								while (true)
								{
									moditem = FunctionsVN.getItemFromName(ModObjects.rabbitHideEF);
									if (moditem != null) {
										FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
												new ItemStack( moditem, FunctionsVN.modernTradeCostBySlot(9, 1, nextSlotToFill, 3) ),
												new ItemStack( Items.emerald, 1 ) ) ); break;}
									break;
								}
							}
							
							// Level 4: Expert
							if (nextSlotToFill == 4 || nextSlotToFill > 5)
							{
								// TODO - Scute for Emerald
								// TODO - Emerald to Leather Horse Armor
							}
							
							// Level 5: Master
							if (nextSlotToFill == 5)
							{
								for (int i=0; i<2 ; i++) {
									// Emerald to Enchanted Dyed Leather Cap
									itemStackColorizable = new ItemStack(Items.leather_helmet);
									itemStackColorizable = FunctionsVN.colorizeItemstack(itemStackColorizable, FunctionsVN.combineDyeColors(new int[]{random.nextInt(16), random.nextInt(16)}));
									enchantvalue = 5 + random.nextInt(15);
									recipeList.add(  new MerchantRecipe(
											new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(5, 2, nextSlotToFill, 5) ),
											new ItemStack( Items.emerald, 1 ), // Second emerald to distinguish this from the other cap trade
											EnchantmentHelper.addRandomEnchantment(random, itemStackColorizable, enchantvalue ) ) );
								}
								
							}
							if (nextSlotToFill >= 5)
							{
								// Emerald to Saddle
								FunctionsVN.addToListWithCheckMeta(recipeList, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(6, 2, nextSlotToFill, 5) ),
										new ItemStack( Items.saddle, 1 ) ) );
							}
						}
						else
						{
							if (nextSlotToFill == 1 || nextSlotToFill > 3) {
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.leather, 9 + random.nextInt(4) ), new ItemStack(Items.emerald, 1 )) );
							}
							if (nextSlotToFill == 2 || nextSlotToFill > 3) {
								recipeList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.emerald, 7 + random.nextInt(2) ), // Enchanted leather tunic
										EnchantmentHelper.addRandomEnchantment(random, new ItemStack(Items.leather_chestplate, 1), 7 + random.nextInt(6) ) ) );
							}
						}
						
						break;
				}
				
				break;
			
			// summon Villager ~ ~ ~ {Profession:5}
			case 5: // NITWIT
				break;

		}
		
		// Added in v3.1 because I think I need it
		Collections.shuffle(recipeList);
	}
	
}

