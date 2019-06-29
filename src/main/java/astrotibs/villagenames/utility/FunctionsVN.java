package astrotibs.villagenames.utility;

import java.lang.reflect.Array;
import java.util.Random;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.integration.ModObjects;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

// Added in v3.1
public class FunctionsVN {
	
	/**
	 * Determine the biometype of the biome the entity is currently in
	 */
	public static int returnBiomeTypeForEntityLocation(EntityLiving entity)
	{
		BiomeGenBase entityBiome = entity.worldObj.getBiomeGenForCoords((int)entity.posX, (int)entity.posZ);
		return biomeToSkinType(entityBiome);
	}
	
	/**
	 * Inputs a biome and returns the skin type it translates to
	 */
	public static int biomeToSkinType(BiomeGenBase biome)
	{
		// Get a list of tags for this biome
		BiomeDictionary.Type[] typeTags = BiomeDictionary.getTypesForBiome(biome);
		// Bytes used to count conditions
		byte b = 0; byte b1 = 0;
		
		// Now check the type list against a series of conditions to determine which biome skin int to return.
		// These are arranged in priority order.
		
		// Nether type (13)
		for (BiomeDictionary.Type type : typeTags)
		{
			if (type==BiomeDictionary.Type.NETHER)
			{
				return 13;
			}
		}
		
		// End type (12)
		for (BiomeDictionary.Type type : typeTags)
		{
			if (type==BiomeDictionary.Type.END)
			{
				return 12;
			}
		}
		
		// Snow type (11)
		for (BiomeDictionary.Type type : typeTags)
		{
			if (type==BiomeDictionary.Type.SNOWY)
			{
				return 11;
			}
		}
		
		// Mushroom type (10)
		for (BiomeDictionary.Type type : typeTags)
		{
			if (type==BiomeDictionary.Type.MUSHROOM)
			{
				return 10;
			}
		}
		
		// Savanna type (9)
		b = 0;
		for (BiomeDictionary.Type type : typeTags)
		{
			if (type==BiomeDictionary.Type.HOT) {b |= 1;}
			if (type==BiomeDictionary.Type.SAVANNA) {b |= 2;}
			if (b==3)
			{
				return 9;
			}
		}
		
		// Desert type (8)
		b = 0; b1 = 0;
		for (BiomeDictionary.Type type : typeTags)
		{
			if (type==BiomeDictionary.Type.SANDY) {b |= 1; b1 |= 1;}
			if (type==BiomeDictionary.Type.HOT) {b |= 2;}
			if (type==BiomeDictionary.Type.DRY) {b |= 4;}
			if (type==BiomeDictionary.Type.MESA) {b1 |= 2;}
			if (b==7 || b1==3)
			{
				return 8;
			}
		}
		
		// Taiga type (7)
		for (BiomeDictionary.Type type : typeTags)
		{
			if (type==BiomeDictionary.Type.CONIFEROUS)
			{
				return 7;
			}
		}
		
		// Swamp type (6)
		for (BiomeDictionary.Type type : typeTags)
		{
			if (type==BiomeDictionary.Type.SWAMP)
			{
				return 6;
			}
		}
		
		// Jungle type (5)
		b = 0;
		for (BiomeDictionary.Type type : typeTags)
		{
			if (type==BiomeDictionary.Type.COLD || type==BiomeDictionary.Type.SPARSE) {b = 0; break;}
			if (type==BiomeDictionary.Type.JUNGLE) {b |= 1;}
			if (type==BiomeDictionary.Type.WET) {b |= 2;}
		}
		if (b==3)
		{
			return 5;
		}
		
		// Aquatic type (4)
		for (BiomeDictionary.Type type : typeTags)
		{
			if (type==BiomeDictionary.Type.OCEAN || type==BiomeDictionary.Type.RIVER || type==BiomeDictionary.Type.BEACH)
			{
				return 4;
			}
		}
		
		// Forest type (3)
		b = 0;
		for (BiomeDictionary.Type type : typeTags)
		{
			if (type==BiomeDictionary.Type.SPARSE) {b = 0; break;}
			if (type==BiomeDictionary.Type.FOREST) {b |= 1;}
		}
		if (b==1)
		{
			return 3;
		}
		
		// Highland type (2)
		for (BiomeDictionary.Type type : typeTags)
		{
			if (type==BiomeDictionary.Type.MOUNTAIN || type==BiomeDictionary.Type.HILLS)
			{
				return 2;
			}
		}
		
		// Magical type (1)
		for (BiomeDictionary.Type type : typeTags)
		{
			if (type==BiomeDictionary.Type.MAGICAL)
			{
				return 1;
			}
		}
		
		// Plains type (0)
		for (BiomeDictionary.Type type : typeTags)
		{
			if (type==BiomeDictionary.Type.PLAINS)
			{
				return 0;
			}
		}
		
		// In case none of these ticked off, return -1.
		// This will cause the 
		return -1;
	}
	
	
    /**
     * Check if the given entity is a original Zombie (normal, baby or villager), 
     * and not a inherited class (like Zombie Pigman).
     * Adapted from Villager Tweaks by sidben:
     * https://github.com/sidben/VillagerTweaks/blob/master/src/main/java/sidben/villagertweaks/helper/GenericHelper.java
     */
    public static boolean isVanillaZombie(Entity entity) {
    	
    	if (entity == null) return false;
    	else if (entity instanceof EntityPigZombie) return false;
    	else return entity instanceof EntityZombie;
    }
    
    
    
	/**
	 * Returns "true" if a particular trade was flagged for removal from the villager's Offers
	 */
    // Relocated to "Functions" for v3.1
	public static boolean isTradeInappropriate(MerchantRecipe merchantrecipe, int profession, int career, int slotPosition) {
		
		
		switch (profession) {
		
		// summon Villager ~ ~ ~ {Profession:0}
		case 0:	// Is a Farmer
			
			// --------------------------------------------- //
			// --- Profession = 0: Farmer trade checking --- //
			// --------------------------------------------- //
			
			// Career = 1: Farmer
				
			     if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.wheat, 1, 0), new ItemStack(Items.emerald, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==1 || slotPosition > 5)) : (career==1 && (slotPosition==1 || slotPosition > 4)) ) return false; else return true; }
			
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.bread, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==1 || slotPosition > 5)) : (career==1 && (slotPosition==1 || slotPosition > 4)) ) return false; else return true; }
			
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.apple, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==2 || slotPosition > 5)) : (career==1 && (slotPosition==3 || slotPosition > 4)) ) return false; else return true; }
			     
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.cookie, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==3 || slotPosition > 5)) : (career==1 && (slotPosition>=4)) ) return false; else return true; }
			
			// Legacy trade
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.melon, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (false) : (GeneralConfig.LEGACYTRADESFALSE && career==1 && (slotPosition>=5)) ) return false; else return true; }
			
			/*
			// Career = 2: Fisherman
			else if ( hasSameIDsAndMetasAs(merchantrecipe, new MerchantRecipe(new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.fish, 1, 0), new ItemStack(Items.cooked_fished, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades && (career==2 && (slotPosition==1 || slotPosition > 5) )) return false; else return true; }
			
			else if ( hasSameIDsAndMetasAs(merchantrecipe, new MerchantRecipe(new ItemStack(Items.fish, 1, 0), new ItemStack(Items.emerald, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades && (career==2 && (slotPosition==2 || slotPosition > 5) )) return false; else return true; }
			
			else if ( hasSameIDsAndMetasAs(merchantrecipe, new MerchantRecipe(new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.fish, 1, 1), new ItemStack(Items.cooked_fished, 1, 1))) ) {
				 if ( GeneralConfig.modernVillagerTrades && (career==2 && (slotPosition==2 || slotPosition > 5) )) return false; else return true; }
			
			else if ( hasSameIDsAndMetasAs(merchantrecipe, new MerchantRecipe(new ItemStack(Items.fish, 1, 1), new ItemStack(Items.emerald, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades && (career==2 && (slotPosition==3 || slotPosition > 5) )) return false; else return true; }
			
			else if ( hasSameIDsAndMetasAs(merchantrecipe, new MerchantRecipe(new ItemStack(Items.fish, 1, 2), new ItemStack(Items.emerald, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades && (career==2 && (slotPosition==4 || slotPosition > 5) )) return false; else return true; }
			
			else if ( hasSameIDsAndMetasAs(merchantrecipe, new MerchantRecipe(new ItemStack(Items.fish, 1, 3), new ItemStack(Items.emerald, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades && (career==2 && (slotPosition==5 || slotPosition > 5) )) return false; else return true; }
			*/
			     
			     
			// Career = 3: Shepherd
			     
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0), new ItemStack(Items.emerald, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==3 && (slotPosition==1 || slotPosition > 5)) : (career==3 && (slotPosition==1 || slotPosition > 2)) ) return false; else return true; }
			     
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.shears, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==3 && (slotPosition==1 || slotPosition > 5)) : (career==3 && (slotPosition==1 || slotPosition > 2)) ) return false; else return true; }
			     
			     
			// Career = 4: Fletcher
			     
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.arrow, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==4 && (slotPosition==1 || slotPosition > 5)) : (career==4 && (slotPosition==1 || slotPosition > 2)) ) return false; else return true; }
			     
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Item.getItemFromBlock(Blocks.gravel), 1, 0), new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.flint, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (false) : (career==4 && (slotPosition>=2)) ) return false; else return true; }
			
			
			     
			// Obsoleted generic trades		     
			else
			{
				if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.cooked_fished, 1, 0), new ItemStack(Items.emerald, 1, 0))) ) return true;
				//if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.melon, 1, 0))) ) return true;
				if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.flint_and_steel, 1, 0))) ) return true;
				if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.chicken, 1, 0), new ItemStack(Items.emerald, 1, 0))) ) return true;
				if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.cooked_chicken, 1, 0))) ) return true;
			}
			
			
			break;
		
		
		case 1:	// summon Villager ~ ~ ~ {Profession:1}
			
			// ------------------------------------------------ //
			// --- Profession = 1: Librarian trade checking --- //
			// ------------------------------------------------ //
			
			// Common trades
			
			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.paper, 1, 0), new ItemStack(Items.emerald, 1, 0))) ) {
				 if ( 
						    (career==1 && (slotPosition==1 || slotPosition > (GeneralConfig.modernVillagerTrades ? 5 : 6) ))
						 || (career==2 && (slotPosition==1 || slotPosition > (GeneralConfig.modernVillagerTrades ? 5 : 4) )) 
						 ) return false; else return true; }
			
			     
			// Career = 1: Librarian
			
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.book, 1, 0), new ItemStack(Items.emerald, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==2 || slotPosition > 5)) : (career==1 && (slotPosition==2 || slotPosition > 6)) ) return false; else return true; }
			
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.compass, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==4 || slotPosition > 5)) : (career==1 && (slotPosition==2 || slotPosition > 6)) ) return false; else return true; }
			
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Item.getItemFromBlock(Blocks.bookshelf), 1, 0) )) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==1 || slotPosition > 5)) : (career==1 && (slotPosition==2 || slotPosition > 6)) ) return false; else return true; }
			
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.written_book, 1, 0), new ItemStack(Items.emerald, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (false) : (career==1 && (slotPosition==3 || slotPosition > 6)) ) return false; else return true; }
			
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.clock, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==4 || slotPosition > 5)) : (career==1 && (slotPosition==3 || slotPosition > 6)) ) return false; else return true; }
			
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Item.getItemFromBlock(Blocks.glass), 1, 0) )) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==3 || slotPosition > 5)) : (career==1 && (slotPosition==3 || slotPosition > 6)) ) return false; else return true; }
			
			
			// Career = 2: Cartographer
			     
			
		    // Obsoleted generic trades
			     
	  		else {
	  			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.book, 1), new ItemStack(Items.emerald, 1), new ItemStack(Items.enchanted_book, 1) )) ) {
					 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition!=5)) : ( (career==1 && (slotPosition==2 || slotPosition==3 || slotPosition==6)) || career==2 ) ) return false; else return true; }
	  		}
			
			break;
			
		
		case 2:	// summon Villager ~ ~ ~ {Profession:2}
			
			// --------------------------------------------- //
			// --- Profession = 2: Priest trade checking --- //
			// --------------------------------------------- //
			
			// Career = 1: Cleric
			     
	  		if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.redstone, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==1 || slotPosition > 5)) : (career==1 && (slotPosition==2 || slotPosition > 4)) ) return false; else return true; }
			     
	  		else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Item.getItemFromBlock(Blocks.glowstone), 1, 0) )) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==3 || slotPosition > 5)) : (career==1 && (slotPosition==3 || slotPosition > 4)) ) return false; else return true; }
			     
	  		else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.experience_bottle, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition >= 5)) : (career==1 && (slotPosition>=4)) ) return false; else return true; }
			
	  		// Legacy trade
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.ender_eye, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (false) : GeneralConfig.LEGACYTRADESFALSE && career==1 && (slotPosition>=5) ) return false; else return true; }
			
			     
			// Obsoleted generic trades
			     
	  		else {
	  			//if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.ender_eye, 1, 0))) ) return true;
	  			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.iron_sword, 1, 0), new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.iron_sword, 1, 0))) ) return true;
	  			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.diamond_sword, 1, 0), new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.diamond_sword, 1, 0))) ) return true;
	  			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.iron_chestplate, 1, 0), new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.iron_chestplate, 1, 0))) ) return true;
	  			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.diamond_chestplate, 1, 0), new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.diamond_chestplate, 1, 0))) ) return true;
	  			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.iron_axe, 1, 0), new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.iron_axe, 1, 0))) ) return true;
	  			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.diamond_axe, 1, 0), new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.diamond_axe, 1, 0))) ) return true;
	  			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.iron_pickaxe, 1, 0), new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.iron_pickaxe, 1, 0))) ) return true;
	  			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.diamond_pickaxe, 1, 0), new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.diamond_pickaxe, 1, 0))) ) return true;
	  		}
			
			break;
			
		
		case 3:	// summon Villager ~ ~ ~ {Profession:3}
			
			// ------------------------------------------------- //
			// --- Profession = 3: Blacksmith trade checking --- //
			// ------------------------------------------------- //
			
			// Common trades
			
			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.coal, 1, 0), new ItemStack(Items.emerald, 1, 0))) ) {
				 if (
						    (career==1 && (slotPosition==1 || slotPosition > (GeneralConfig.modernVillagerTrades ? 5 : 4)))
						 || (career==2 && (slotPosition==1 || slotPosition > (GeneralConfig.modernVillagerTrades ? 5 : 3)))
						 || (career==3 && (slotPosition==1 || slotPosition > (GeneralConfig.modernVillagerTrades ? 5 : 3)))
					  ) return false; else return true; }
			
			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.iron_ingot, 1, 0), new ItemStack(Items.emerald, 1, 0))) ) {
				 if ( 
						    (career==1 && (slotPosition==2 || slotPosition > (GeneralConfig.modernVillagerTrades ? 5 : 4)))
						 || (career==2 && (slotPosition==2 || slotPosition > (GeneralConfig.modernVillagerTrades ? 5 : 3)))
						 || (career==3 && (slotPosition==2 || slotPosition > (GeneralConfig.modernVillagerTrades ? 5 : 3)))
					  ) return false; else return true; }
			
			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.diamond, 1, 0), new ItemStack(Items.emerald, 1, 0))) ) {
				if (career==4) {return true;}
				else if ( 
						    ((career>=1 || career <=3) && (GeneralConfig.modernVillagerTrades ? (slotPosition==4 || slotPosition > 5) : (slotPosition==3 || slotPosition > 4)))
					  ) return false; else return true; }
			
	  		
			
			// Career = 1: Armorer
			
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.iron_helmet, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==1)) : (career==1 && (slotPosition==1 || slotPosition > 4)) ) return false; else return true; }
			     
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.iron_chestplate, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==1)) : (career==1 && (slotPosition==2 || slotPosition > 4)) ) return false; else return true; }
			     
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.diamond_chestplate, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition >= 5)) : (career==1 && (slotPosition==3 || slotPosition > 4)) ) return false; else return true; }
			     
	  		else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.chainmail_boots, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==2)) : (career==1 && (slotPosition>=4)) ) return false; else return true; }
			     
	  		else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.chainmail_leggings, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==2)) : (career==1 && (slotPosition>=4)) ) return false; else return true; }
			     
	  		else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.chainmail_helmet, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==3)) : (career==1 && (slotPosition>=4)) ) return false; else return true; }
			     
	  		else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.chainmail_chestplate, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==3)) : (career==1 && (slotPosition>=4)) ) return false; else return true; }
			     
			
			// Career = 2: Weapon Smith
			     
	  		else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.iron_axe, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (
						 (career==2 && (slotPosition==1)) || (career==3 && (slotPosition==3 || slotPosition > 5))
						 ) : (career==2 && (slotPosition==1 || slotPosition > 3)) ) return false; else return true; }
			     
	  		else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.iron_sword, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==2 && (slotPosition==2)) : (career==2 && (slotPosition==2 || slotPosition > 3)) ) return false; else return true; }
			     
	  		else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.diamond_sword, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==2 && (slotPosition==5)) : (career==2 && (slotPosition>=3)) ) return false; else return true; }
			     
	  		else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.diamond_axe, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (
						 (career==2 && (slotPosition==4)) || (career==3 && (slotPosition==4 || slotPosition > 5))
						 ) : (career==2 && (slotPosition>=3)) ) return false; else return true; }
			     
			     
			// Career = 3: Tool Smith
			
	  		else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.iron_shovel, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==3 && (slotPosition==3)) : (career==3 && (slotPosition==1 || slotPosition > 3)) ) return false; else return true; }
			     
	  		else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.iron_pickaxe, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==3 && (slotPosition==3)) : (career==3 && (slotPosition==2 || slotPosition > 3)) ) return false; else return true; }
			     
	  		else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.diamond_pickaxe, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==3 && (slotPosition==5)) : (career==3 && (slotPosition>=3)) ) return false; else return true; }
			     
	  		
	 		
	  	    // Legacy trade
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.gold_ingot, 1, 0), new ItemStack(Items.emerald, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (false) : GeneralConfig.LEGACYTRADESFALSE && 
						 (
							    (career==1 && (slotPosition>=5))
							 || (career==2 && (slotPosition>=4))
							 || (career==3 && (slotPosition>=4))
							 )
						 ) return false; else return true; }
			
			
			// Obsoleted generic trades
			     
	  		else {
	  			//if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.gold_ingot, 1, 0), new ItemStack(Items.emerald, 1, 0))) ) return true;				
	  			//if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.iron_sword, 1, 0))) ) return true;				
	  			//if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.diamond_sword, 1, 0))) ) return true;				
	  			//if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.diamond_axe, 1, 0))) ) return true;				
	  			//if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.iron_pickaxe, 1, 0))) ) return true;				
	  			//if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.diamond_pickaxe, 1, 0))) ) return true;				
	  			//if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.iron_shovel, 1, 0))) ) return true;				
	  			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.diamond_shovel, 1, 0))) ) {
	  				if ( GeneralConfig.modernVillagerTrades && (career==3 && (slotPosition==4))) return false; else return true;}				
	  			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.iron_hoe, 1, 0))) ) return true;				
	  			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.diamond_hoe, 1, 0))) ) {
	  				if ( GeneralConfig.modernVillagerTrades && (career==3 && (slotPosition==3 || slotPosition > 5))) return false; else return true;}				
	  			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.iron_boots, 1, 0))) ) {
	  				if ( GeneralConfig.modernVillagerTrades && (career==1 && (slotPosition==1))) return false; else return true;}				
	  			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.diamond_boots, 1, 0))) ) {
	  				if ( GeneralConfig.modernVillagerTrades && (career==1 && (slotPosition==4))) return false; else return true;}				
	  			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.diamond_helmet, 1, 0))) ) {
	  				if ( GeneralConfig.modernVillagerTrades && (career==1 && (slotPosition==5))) return false; else return true;}				
	  			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.diamond_chestplate, 1, 0))) ) {
	  				if ( GeneralConfig.modernVillagerTrades && (career==1 && (slotPosition==5))) return false; else return true;}				
	  			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.iron_leggings, 1, 0))) ) {
	  				if ( GeneralConfig.modernVillagerTrades && (career==1 && (slotPosition==1))) return false; else return true;}				
	  			if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.diamond_leggings, 1, 0))) ) {
	  				if ( GeneralConfig.modernVillagerTrades && (career==1 && (slotPosition==4))) return false; else return true;}	
	  		}
			
			break;
			
		
		case 4:	// summon Villager ~ ~ ~ {Profession:4}
			
			// ---------------------------------------------- //
			// --- Profession = 4: Butcher trade checking --- //
			// ---------------------------------------------- //
			
			// Career = 1: Butcher
			
	  		if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.porkchop, 1, 0), new ItemStack(Items.emerald, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==1 || slotPosition > 3)) : (career==1 && (slotPosition==1 || slotPosition > 2)) ) return false; else return true; }
			     
	  		else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.coal, 1, 0), new ItemStack(Items.emerald, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==2 || slotPosition > 3)) : (career==1 && (slotPosition>=2)) ) return false; else return true; }
			     
	  		else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.cooked_porkchop, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==2 || slotPosition > 3)) : (career==1 && (slotPosition>=2)) ) return false; else return true; }
			
	  		// Legacy trade
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.beef, 1, 0), new ItemStack(Items.emerald, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition >= 3)) : (GeneralConfig.LEGACYTRADESFALSE && career==1 && (slotPosition>=3)) ) return false; else return true; }
	  		
	  		// Legacy trade
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.cooked_beef, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==1 && (slotPosition==3 || slotPosition > 5)) : (GeneralConfig.LEGACYTRADESFALSE && career==1 && (slotPosition>=3)) ) return false; else return true; }
	  		
			
			// Career = 2: Leatherworker
			
	  		else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.leather_leggings, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==2 && (slotPosition==1 || slotPosition == 4)) : (career==2 && (slotPosition==1 || slotPosition > 3)) ) return false; else return true; }
			     
	  		else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.leather_chestplate, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==2 && (slotPosition==1 || slotPosition == 3 || slotPosition == 4)) : (career==2 && (slotPosition==2 || slotPosition > 3)) ) return false; else return true; }
	  		  		
	  		else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.saddle, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==2 && (slotPosition >= 5)) : (career==2 && (slotPosition>=3)) ) return false; else return true; }
			
			// Legacy trade
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.leather_boots, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==2 && (slotPosition==2 || slotPosition == 4 || slotPosition > 5)) : (GeneralConfig.LEGACYTRADESFALSE && career==2 && (slotPosition>=4)) ) return false; else return true; }
	  		  		
	  		// Legacy trade
			else if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.leather_helmet, 1, 0))) ) {
				 if ( GeneralConfig.modernVillagerTrades ? (career==2 && (slotPosition==2 || slotPosition == 4 || slotPosition == 5)) : (GeneralConfig.LEGACYTRADESFALSE && career==2 && (slotPosition>=4)) ) return false; else return true; }
	  		
	  				     
			// Obsoleted generic trades
			/*     
	  		else {
	  			//if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.beef, 1, 0), new ItemStack(Items.emerald, 1, 0))) ) return true;				
	  			//if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.leather_chestplate, 1, 0))) ) return true;				
	  			//if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.leather_boots, 1, 0))) ) return true;				
	  			//if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.leather_helmet, 1, 0))) ) return true;				
	  			//if ( merchantrecipe.hasSameIDsAs(new MerchantRecipe( new ItemStack(Items.emerald, 1, 0), new ItemStack(Items.cooked_beef, 1, 0))) ) return true;	
	  		}
			*/
			break;
		
		
		case 5:	// summon Villager ~ ~ ~ {Profession:5}
			
			// --------------------------------------------- //
			// --- Profession = 5: Nitwit trade checking --- //
			// --------------------------------------------- //
			
			break;
			
		}
		
		return false;
	}
    
    
    
    
    /**
     * Item equivalent of Block.getBlockFromName(String)
     */
    public static Item getItemFromName(String itemName)
    {
        if (Item.itemRegistry.containsKey(itemName))
        {
            return (Item)Item.itemRegistry.getObject(itemName);
        }
        else
        {
            try
            {
                return (Item)Item.itemRegistry.getObjectById(Integer.parseInt(itemName));
            }
            catch (NumberFormatException numberformatexception)
            {
                return null;
            }
        }
    }
    
    
    /**
     * Copied from MerchantRecipeList.addToListWithCheck
     * Checks if there is a recipe for the same ingredients already on the list, and replaces it. Otherwise, adds it.
     * If discriminateMetas is "true", this counts MerchantRecipes with different meta values as being distinct.
     * Otherwise, this trade replaces the first on the list with matching items irrespective of meta (just like vanilla method). 
     */
    public static void addToListWithCheckMeta(MerchantRecipeList recipeList, MerchantRecipe recipe)
    {
    	addToListWithCheckMeta(recipeList, recipe, true);
    }
    public static void addToListWithCheckMeta(MerchantRecipeList recipeList, MerchantRecipe newRecipe, boolean discriminateMetas)
    {
        for (int i = 0; i < recipeList.size(); ++i)
        {
            MerchantRecipe existingRecipe = (MerchantRecipe)recipeList.get(i);
            
            if (
            		(discriminateMetas && hasSameIDsAndMetasAs(newRecipe, existingRecipe))
            		|| (!discriminateMetas && newRecipe.hasSameIDsAs(existingRecipe))
            		)
            {
            	//LogHelper.info("New recipe " + newRecipe + " has same IDs and Metas as existing recipe " + existingRecipe);
                if (hasSameItemsAs(newRecipe, existingRecipe)) // If this version is cheaper for the player, or offers a better deal, replace the one that's there
                {
                	//LogHelper.info("New recipe " + newRecipe + " has same items as existing recipe " + existingRecipe);
                	recipeList.set(i, newRecipe);
                }
                return;
            }
        }
        recipeList.add(newRecipe);
    }
    
    /**
     * Checks first and second ItemToBuy ID's and count.
     * If the asking price is lower or the offer amount is higher, replace the trade with the better deal.
     */
    public static boolean hasSameItemsAs(MerchantRecipe newRecipe, MerchantRecipe existingRecipe)
    {
        if (newRecipe.hasSameIDsAs(existingRecipe)) // Both recipes are comparable
        {
	    	int buyitem1diff = newRecipe.getItemToBuy().stackSize - existingRecipe.getItemToBuy().stackSize; // Difference between old cost and new
	        int buyitem2diff = ((newRecipe.getSecondItemToBuy() != null) ? newRecipe.getSecondItemToBuy().stackSize : 0)
	        		- ((existingRecipe.getSecondItemToBuy() != null) ? existingRecipe.getSecondItemToBuy().stackSize : 0);
	        int sellitemdiff = newRecipe.getItemToSell().stackSize - existingRecipe.getItemToSell().stackSize;
	        
	        return (buyitem1diff + buyitem2diff - sellitemdiff) < 0;
        }
        else {return false;}
    }
    
    /**
     * Checks if both the first and second ItemToBuy IDs are the same
     */
    public static boolean hasSameIDsAndMetasAs(MerchantRecipe recipe1, MerchantRecipe recipe2)
    {
        return 
        		(
        			   (recipe1.getItemToBuy().getItem() == recipe2.getItemToBuy().getItem() && recipe1.getItemToSell().getItem() == recipe2.getItemToSell().getItem())
        			&& (recipe1.getItemToBuy().getItemDamage() == recipe2.getItemToBuy().getItemDamage() && recipe1.getItemToSell().getItemDamage() == recipe2.getItemToSell().getItemDamage())
        				)
        		? recipe1.getSecondItemToBuy() == null && recipe2.getSecondItemToBuy() == null
        		|| recipe1.getSecondItemToBuy() != null && recipe2.getSecondItemToBuy() != null && recipe1.getSecondItemToBuy().getItemDamage() == recipe2.getSecondItemToBuy().getItemDamage()
        		: false;
    }
    
    
    
    /**
     * Combines colors and determines the resulting integer-encoded color.
     * Colors are input as an int array of any length, where each element is a dye meta color (e.g. 2 is magenta).
     */
    public static int combineDyeColors(int[] metaStream)
    {
    	// Dye integers
    	final int[] colorInts = new int[]{
    			16777215, // White
    			14188339, // Orange
    			11685080, // Magenta
    			 6724056, // Light blue
    			15066419, // Yellow
    			 8375321, // Lime
    			15892389, // Pink
    			 5000268, // Gray
    			10066329, // Light gray
    			 5013401, // Cyan
    			 8339378, // Purple
    			 3361970, // Blue
    			 6704179, // Brown
    			 6717235, // Green
    			10040115, // Red
    			 1644825  // Black
    		};
    	
    	// Sum up r, g, b values
    	
    	// Initialize holder r, g, b
    	int r = 0; int g = 0; int b = 0;
    	
    	for (int i=0; i<metaStream.length; i++)
    	{
    		r += colorInts[metaStream[i]]/(256*256);
    		g += (colorInts[metaStream[i]]/256)%256;
    		b += colorInts[metaStream[i]]%256;
    	}
    	
    	// Divide r, g, b by number of combined dyes
    	r /= metaStream.length;
    	g /= metaStream.length;
    	b /= metaStream.length;
    	
    	// Re-encode r, g, b into final integer
    	return r*(256*256) + g*(256) + b;
    }
    
    
    /**
     * Colorizes an itemstack using an encoded color integer. This is used to give a random two-dye color to leather armor for the leatherworker. 
     */
    public static ItemStack colorizeItemstack(ItemStack colorizableitemstack, int colorInt)
    {
		// Get the itemstack's tag compound
        NBTTagCompound nbttagcompound = colorizableitemstack.getTagCompound();
        
        // If the itemstack has no tag compound, make one
        if (nbttagcompound == null)
        {
            nbttagcompound = new NBTTagCompound();
            colorizableitemstack.setTagCompound(nbttagcompound);
        }
        
        // Form the display tag and apply it to the compound if needed.
        NBTTagCompound tagcompounddisplay = nbttagcompound.getCompoundTag("display");
        if (!nbttagcompound.hasKey("display", 10)) {nbttagcompound.setTag("display", tagcompounddisplay);}
        
        // Apply the color
        tagcompounddisplay.setInteger("color", colorInt);
        
        return colorizableitemstack;
    }
    
    
    /**
     * Returns the cost of an item in a villager trade depending on the slot it's in and a priceMultiplier.
     * The "natural" cost is what it would be in slot 3 (Journeyman). The cost is always clamped between 1 and 64.
     * A priceMultiplier of N means that the item cost decreases by N for each slot after intendedSlot, and increases by N for each slot before intendedSlot.
     * A priceMultiplier of 0 thus means no change whatsoever.
     * The price changing effect of priceMultiplier is halved after slot 5.
     */
    public static int modernTradeCostBySlot(int defaultQuantity, int priceMultiplier, int slot, int intendedSlot)
    {
    	return MathHelper.clamp_int(defaultQuantity + priceMultiplier*(intendedSlot-Math.min(slot,5)) + (priceMultiplier*(5-Math.max(slot,5)))/2, 1, 64);
    }
    

    /**
     * Adds a random enchanted book to a villager's trade offers in the style of 1.14
     * Copied and modified from EntityVillager.ListEnchantedBookForEmeralds
     */
    public static MerchantRecipe modernEnchantedBookTrade(Random random)
    {
        Enchantment enchantment = Enchantment.enchantmentsBookList[random.nextInt(Enchantment.enchantmentsBookList.length)];
        int i = MathHelper.getRandomIntegerInRange(random, enchantment.getMinLevel(), enchantment.getMaxLevel());
        ItemStack itemstack = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, i));
        int emeraldprice = 2 + random.nextInt(5 + i * 10) + 3 * i;
        
        if (emeraldprice > 64)
        {
            emeraldprice = 64;
        }
        
        return (new MerchantRecipe(new ItemStack(Items.emerald, emeraldprice), new ItemStack(Items.book), itemstack));
    }
    
    
    /**
     * Generates an ItemStack with a random tipped arrow from EtFuturum 
     */
    public static ItemStack getRandomTippedArrow(int numberOfArrows, Random random)
    {
    	Item EFtippedarrow = FunctionsVN.getItemFromName(ModObjects.tippedArrowEF);
    	
    	if (EFtippedarrow==null) {return null;} // Et Futurum tipped arrows are not available.
    	
    	ItemStack tippedArrowItemStack = new ItemStack(EFtippedarrow, numberOfArrows);
    	
		// Get the itemstack's tag compound
        NBTTagCompound nbttagcompound = tippedArrowItemStack.getTagCompound();
        
        // If the itemstack has no tag compound, make one
        if (nbttagcompound == null)
        {
            nbttagcompound = new NBTTagCompound();
            tippedArrowItemStack.setTagCompound(nbttagcompound);
        }
        
        // Form the Potion tag and apply it to the compound if needed.
        NBTTagCompound tagcompoundPotion = nbttagcompound.getCompoundTag("Potion");
        if (!nbttagcompound.hasKey("Potion", 10)) {nbttagcompound.setTag("Potion", tagcompoundPotion);}
        
        // Here is the 2D array of Et Futurum potion effects
        // Columns are: Ambient (byte), Amplifier (byte), Id (byte), Duration (int)
        // Stacks loaded in from NEI have twice the duration value.
        int[][] etFuturumPotionValues = new int[][]{
        	
        	{0,0,10,225}, // Arrow of Regeneration
        	{0,0,10,112}, // Arrow of Regeneration
        	{0,0,10,600}, // Arrow of Regeneration
        	
        	{0,0,1,900}, // Arrow of Swiftness
        	{0,0,1,450}, // Arrow of Swiftness
        	{0,0,1,2400}, // Arrow of Swiftness
        	
        	{0,0,12,900}, // Arrow of Fire Resistance
        	{0,0,12,2400}, // Arrow of Fire Resistance
        	
        	{0,0,19,225}, // Arrow of Poison
        	{0,0,19,112}, // Arrow of Poison
        	{0,0,19,600}, // Arrow of Poison
        	
        	{0,0,6,1}, // Arrow of Healing
        	{0,0,6,1}, // Arrow of Healing
        	
        	{0,0,16,900}, // Arrow of Night Vision
        	{0,0,16,2400}, // Arrow of Night Vision
        	
        	{0,0,18,450}, // Arrow of Weakness
        	{0,0,18,1200}, // Arrow of Weakness
        	
        	{0,0,5,900}, // Arrow of Strength
        	{0,0,5,450}, // Arrow of Strength
        	{0,0,5,2400}, // Arrow of Strength
        	
        	{0,0,2,450}, // Arrow of Slowness
        	{0,0,2,1200}, // Arrow of Slowness
        	
        	{0,0,8,900}, // Arrow of Leaping
        	{0,0,8,450}, // Arrow of Leaping
        	
        	{0,0,7,1}, // Arrow of Harming
        	{0,0,7,1}, // Arrow of Harming
        	
        	{0,0,13,900}, // Arrow of Water Breathing
        	{0,0,13,2400}, // Arrow of Water Breathing
        	
        	{0,0,14,900}, // Arrow of Invisibility
        	{0,0,14,2400}, // Arrow of Invisibility
        };
        
        // Apply the color
        int selectedpotion = random.nextInt(etFuturumPotionValues.length); // Select a random row from the above array
        tagcompoundPotion.setByte("Ambient", (byte) etFuturumPotionValues[selectedpotion][0]);
        tagcompoundPotion.setByte("Amplifier", (byte) etFuturumPotionValues[selectedpotion][1]);
        tagcompoundPotion.setByte("Id", (byte) etFuturumPotionValues[selectedpotion][2]);
        tagcompoundPotion.setInteger("Duration", etFuturumPotionValues[selectedpotion][3]);
        
        return tippedArrowItemStack;
    }
    
    
    // Added in v3.1banner
    
    /**
     * Inputs an array of objects and a corresponding array of weights, and returns a randomly-selected element
     * with a probability proportional to its weight.
     * 
     * These inputs must be equal length. If they are not, you get back null.
     * Additionally, and this goes without saying: the individual weights must be non-negative and their sum must be positive.
     * 
     * Adapted from https://stackoverflow.com/questions/6737283/weighted-randomness-in-java
     */
    public static Object weightedRandom(Object elementArray, double[] weightArray, Random random)
    {
    	if (Array.getLength(elementArray) != weightArray.length) {return null;}
    	else
    	{
    		// Compute the total weight of all items together
    		double totalWeight = 0D;
    		for (int i=0; i<weightArray.length; i++ )
    		{
    		    totalWeight += weightArray[i];
    		}
    		
    		// Now choose a random item
    		int randomIndex = -1;
    		double randomObject = random.nextDouble() * totalWeight;
    		for (int i = 0; i < Array.getLength(elementArray); ++i)
    		{
    			randomObject -= weightArray[i];
    		    if (randomObject <= 0.0d)
    		    {
    		        randomIndex = i;
    		        break;
    		    }
    		}
    		return Array.get(elementArray, randomIndex);
    	}
    }
    
}
