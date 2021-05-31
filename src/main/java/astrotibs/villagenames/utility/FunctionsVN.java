package astrotibs.villagenames.utility;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import astrotibs.villagenames.VillageNames;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.ieep.ExtendedVillager;
import astrotibs.villagenames.integration.ModObjects;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraftforge.common.BiomeDictionary;

// Added in v3.1
public class FunctionsVN
{
	// Represents the 1.14+ village types
	public static enum VillageType
	{
		PLAINS, DESERT, TAIGA, SAVANNA, SNOWY, JUNGLE, SWAMP;
		
		/**
		 * Determine the biometype to generate village buildings
		 */
		public static VillageType getVillageTypeFromBiome(World world, int posX, int posZ)
		{
			BiomeGenBase biome = world.getBiomeGenForCoords(posX, posZ);
			return getVillageTypeFromBiome(biome);
		}
		public static VillageType getVillageTypeFromBiome(WorldChunkManager worldChunkManager, int posX, int posZ)
		{
			BiomeGenBase biome = worldChunkManager.getBiomeGenAt(posX, posZ);
			return getVillageTypeFromBiome(biome);
		}
		public static VillageType getVillageTypeFromBiome(BiomeGenBase biome)
		{
			BiomeDictionary.Type[] typeTags = BiomeDictionary.getTypesForBiome(biome);
			
			// Ordered by personal priority. The first of these to be fulfilled gets returned
			if (biome.biomeName.toLowerCase().contains("taiga")) {return TAIGA;}
			for (BiomeDictionary.Type type : typeTags) {if (type==BiomeDictionary.Type.CONIFEROUS) {return TAIGA;}}
			if (biome.biomeName.toLowerCase().contains("savanna")) {return SAVANNA;}
			for (BiomeDictionary.Type type : typeTags) {if (type==BiomeDictionary.Type.SAVANNA) {return SAVANNA;}}
			for (BiomeDictionary.Type type : typeTags) {if (type==BiomeDictionary.Type.SNOWY) {return SNOWY;}}
			if (biome.biomeName.toLowerCase().contains("desert")) {return DESERT;}
			for (BiomeDictionary.Type type : typeTags) {if (type==BiomeDictionary.Type.SANDY) {return DESERT;}}
			if (biome.biomeName.toLowerCase().contains("jungle")) {return JUNGLE;}
			for (BiomeDictionary.Type type : typeTags) {if (type==BiomeDictionary.Type.JUNGLE) {return JUNGLE;}}
			if (biome.biomeName.toLowerCase().contains("swamp")) {return SWAMP;}
			for (BiomeDictionary.Type type : typeTags) {if (type==BiomeDictionary.Type.SWAMP) {return SWAMP;}}
			
			// If none apply, send back Plains
			return PLAINS;
		}
		public static VillageType getVillageTypeFromName(String name, VillageType defaultType)
		{
			if (name.toUpperCase().equals("PLAINS"))       {return PLAINS;}
			else if (name.toUpperCase().equals("DESERT"))  {return DESERT;}
			else if (name.toUpperCase().equals("TAIGA"))   {return TAIGA;}
			else if (name.toUpperCase().equals("SAVANNA")) {return SAVANNA;}
			else if (name.toUpperCase().equals("SNOWY"))   {return SNOWY;}
			else if (name.toUpperCase().equals("JUNGLE"))  {return JUNGLE;}
			else if (name.toUpperCase().equals("SWAMP"))   {return SWAMP;}
			return defaultType;
		}
	}
	
	public static enum MaterialType
	{
		OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK,
		SAND, MESA, SNOW, MUSHROOM; // Added three more for special non-wood cases
		
		/**
		 * Determine the wood type to return for a given biome
		 */
		public static MaterialType getMaterialTemplateForBiome(World world, int posX, int posZ)
		{
			BiomeGenBase biome = world.getBiomeGenForCoords(posX, posZ);
			return getMaterialTemplateForBiome(biome);
		}
		public static MaterialType getMaterialTemplateForBiome(WorldChunkManager worldChunkManager, int posX, int posZ)
		{
			BiomeGenBase biome = worldChunkManager.getBiomeGenAt(posX, posZ);
			return getMaterialTemplateForBiome(biome);
		}
		public static MaterialType getMaterialTemplateForBiome(BiomeGenBase biome)
		{
			if (biome.biomeName.toLowerCase().contains("birch")) {return BIRCH;}
			if (biome.biomeName.toLowerCase().contains("roofed forest")) {return DARK_OAK;}
			
			BiomeDictionary.Type[] typeTags = BiomeDictionary.getTypesForBiome(biome);
			if (biome.biomeName.toLowerCase().contains("taiga")) {return SPRUCE;}
			for (BiomeDictionary.Type type : typeTags) {if (type==BiomeDictionary.Type.CONIFEROUS) {return SPRUCE;}}
			for (BiomeDictionary.Type type : typeTags) {if (type==BiomeDictionary.Type.JUNGLE) {return JUNGLE;}}
			if (biome.biomeName.toLowerCase().contains("savanna")) {return ACACIA;}
			for (BiomeDictionary.Type type : typeTags) {if (type==BiomeDictionary.Type.SAVANNA) {return ACACIA;}}
			for (BiomeDictionary.Type type : typeTags) {if (type==BiomeDictionary.Type.MESA) {return MESA;}}
			for (BiomeDictionary.Type type : typeTags) {if (type==BiomeDictionary.Type.MUSHROOM) {return MUSHROOM;}}
			
			// Snow is only returned if there are no trees and it's a wasteland:
			boolean isSnowy = false;
			boolean isWasteland = false;
			for (BiomeDictionary.Type type : typeTags)
			{
				if (type==BiomeDictionary.Type.SNOWY) {isSnowy=true;}
				if (type==BiomeDictionary.Type.WASTELAND) {isWasteland=true;}
				if (type==BiomeDictionary.Type.DENSE || type==BiomeDictionary.Type.FOREST || type==BiomeDictionary.Type.LUSH || type==BiomeDictionary.Type.SPARSE) {isSnowy=false; isWasteland=false; break;}
			}
			if (isSnowy && isWasteland) {return SNOW;}
			
			for (BiomeDictionary.Type type : typeTags) {if (type==BiomeDictionary.Type.SANDY) {return SAND;}}
			
			// If none apply, send back Oak
			return OAK;
		}
		public static MaterialType getMaterialTypeFromName(String name, MaterialType defaultType)
		{
			if (name.toUpperCase().equals("OAK"))           {return OAK;}
			else if (name.toUpperCase().equals("SPRUCE"))   {return SPRUCE;}
			else if (name.toUpperCase().equals("BIRCH"))    {return BIRCH;}
			else if (name.toUpperCase().equals("JUNGLE"))   {return JUNGLE;}
			else if (name.toUpperCase().equals("ACACIA"))   {return ACACIA;}
			else if (name.toUpperCase().equals("DARK_OAK")) {return DARK_OAK;}
			else if (name.toUpperCase().equals("SAND"))     {return SAND;}
			else if (name.toUpperCase().equals("MESA"))     {return MESA;}
			else if (name.toUpperCase().equals("SNOW"))     {return SNOW;}
			else if (name.toUpperCase().equals("MUSHROOM")) {return MUSHROOM;}
			return defaultType;
		}
	}
	
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
			if (type==BiomeDictionary.Type.COLD || type==BiomeDictionary.Type.SPARSE || type==BiomeDictionary.Type.DEAD) {b = 0; break;}
			if (type==BiomeDictionary.Type.JUNGLE) {b |= 1;}
			if (type==BiomeDictionary.Type.WET || type==BiomeDictionary.Type.LUSH || type==BiomeDictionary.Type.DENSE) {b |= 2;}
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
		
		// Magical type (1)
		b = 0;
		for (BiomeDictionary.Type type : typeTags)
		{
			if (type==BiomeDictionary.Type.DEAD || type==BiomeDictionary.Type.SPOOKY) {b = 0; break;}
			if (type==BiomeDictionary.Type.MAGICAL) {b |= 1;}
		}
		if (b==1)
		{
			return 1;
		}
		
		// Highland type (2)
		for (BiomeDictionary.Type type : typeTags)
		{
			if (type==BiomeDictionary.Type.MOUNTAIN)
			{
				return 2;
			}
		}
				
		// Forest type (3)
		b = 0; b1 = 0;
		for (BiomeDictionary.Type type : typeTags)
		{
			if (type==BiomeDictionary.Type.DEAD) {b = 0; break;}
			if (type==BiomeDictionary.Type.SPARSE) {b1--;}
			if (type==BiomeDictionary.Type.DENSE) {b1++;}
			if (type==BiomeDictionary.Type.FOREST) {b |= 1;}
		}
		if (b==1 && b1!=-1)
		{
			return 3;
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
		// This will cause the mod to check again
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
    
    // Added in v3.2
	/**
	 * Determine the skinTone of the biome the entity is currently in
	 */
	public static int returnSkinToneForEntityLocation(EntityLiving entity)
	{
		BiomeGenBase entityBiome = entity.worldObj.getBiomeGenForCoords((int)entity.posX, (int)entity.posZ);
		int chosenSkin = biomeToSkinTone(entityBiome, new Random());
		//entity.setCustomNameTag("Skin: " + chosenSkin);
		return chosenSkin;
	}
	
	/**
	 * Inputs a biome and returns a randomized skin tone value.
	 * Values are drawn from a Gaussian distribution and then rounded to the nearest integer.
	 * The mean value of the distribution is the sum of values associated with each biome tag. Most are 0 but some increase or decrease.
	 * The StDev value decreases as the Poisson error for number of biome tags that have non-zero value.
	 * Config values determine the overall StDev scale, and how quickly the StDev shrinks with number of biome tags.
	 * Possible output values range from -4 (darkest) to 3 (lightest) with 0 being the standard Minecraft villager.
	 */
	public static int biomeToSkinTone(BiomeGenBase biome, Random random)
	{
		// Get a list of tags for this biome
		BiomeDictionary.Type[] typeTags = BiomeDictionary.getTypesForBiome(biome);
		
		// Now check the type list, modifying the median for particular biomes.
		int skin_mu = 0; // Center of the Gaussian distribution; start as 0 (default villager)
		int skin_tags = 0; // How many skin-altering tags were counted
		
		for (BiomeDictionary.Type type : typeTags)
		{
			// Cold, snowy, high-latitude values lighten skin:
			if (type==BiomeDictionary.Type.COLD)
			{
				skin_mu += 1;
				skin_tags++;
				continue;
			}
			if (type==BiomeDictionary.Type.WET)
			{
				skin_mu += 1;
				skin_tags++;
				continue;
			}
			if (type==BiomeDictionary.Type.CONIFEROUS)
			{
				skin_mu += 2;
				skin_tags++;
				continue;
			}
			if (type==BiomeDictionary.Type.NETHER)
			{
				skin_mu += 2;
				skin_tags++;
				continue;
			}
			if (type==BiomeDictionary.Type.END)
			{
				skin_mu += 2;
				skin_tags++;
				continue;
			}
			if (type==BiomeDictionary.Type.MOUNTAIN)
			{
				skin_mu += 1;
				skin_tags++;
				continue;
			}
			if (type==BiomeDictionary.Type.SNOWY)
			{
				skin_mu += 2;
				skin_tags++;
				continue;
			}
			
			// Hot, dry, low-latitude values darken skin:
			if (type==BiomeDictionary.Type.HOT)
			{
				skin_mu -= 1;
				skin_tags++;
				continue;
			}
			if (type==BiomeDictionary.Type.DRY)
			{
				skin_mu -= 1;
				skin_tags++;
				continue;
			}
			if (type==BiomeDictionary.Type.SAVANNA)
			{
				skin_mu -= 2;
				skin_tags++;
				continue;
			}
			if (type==BiomeDictionary.Type.JUNGLE)
			{
				skin_mu -= 1;
				skin_tags++;
				continue;
			}
			if (type==BiomeDictionary.Type.MESA)
			{
				skin_mu -= 1;
				skin_tags++;
				continue;
			}
		}
		
		// Now, draw a Gaussian-distributed random value centered on skin_mu:
		
		// Standard deviation, reduced by number of skin tags found and influenced by user's config values
		float sigma = MathHelper.sqrt_double( 1D/(skin_tags*GeneralConfig.villagerSkinToneVarianceAnnealing + 1D) ) * GeneralConfig.villagerSkinToneVarianceScale; 
		double skin_r = random.nextGaussian()*sigma + skin_mu;
		int chosen_skin = MathHelper.clamp_int((int) Math.round(skin_r), -4, 3);
		
		//chosen_skin = 3;
		//LogHelper.info("skin_mu: " + skin_mu + ", skin_tags: " + skin_tags + ", chosen_skin: " + chosen_skin);
		
		// Return this value clamped to the darkest and lightest values
		return chosen_skin;
	}
    

    /**
     * Produces a shuffled array of integers from value a to value b.
     * Primarily used to randomize the colored wool variations granted to shepherds.
     */
    public static int[] shuffledIntArray(int a, int b, Random rgen)
    {
		int size = b-a+1;
		int[] array = new int[size];
 
		for(int i=0; i< size; i++) {array[i] = a+i;}
 
		for (int i=0; i<array.length; i++)
		{
		    int randomPosition = rgen.nextInt(array.length);
		    int temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
		}
		
		return array;
	}
    
	
	/**
	 * Use this to adjust the villagers' trade when you talk to them.
	 * It essentially scans for and removes all vanilla trades that aren't allowed by the new trade system.
	 */
	public static void monitorVillagerTrades(EntityVillager villager)
	{
		if (villager.getProfession()<0 || villager.getProfession()>5) {return;} // Don't modify non-vanilla villager trades
    	
    	ExtendedVillager ev = ExtendedVillager.get(villager);
        // Try modifying trades
		// summon Villager ~ ~ ~ {Profession:0}
		
    	// Added in v3.1
    	// Update IEEP stuff
    	int professionLevel = ev.getProfessionLevel(); // Added in v3.1
		if (professionLevel < 0) {ev.setProfessionLevel(ExtendedVillager.determineProfessionLevel(villager));}
		
    	
		// If you're talking to a vanilla Villager, check the trades list
		if (
				GeneralConfig.villagerCareers
				&& villager.getProfession() >= 0
				&& villager.getProfession() <= 5
				)
		{
			final int failuresToForceAcceptance = 100; // How many invalid trade cycles are performed until we can assume this is an infinite loop
			
			int profession = villager.getProfession();
			int career = ev.getCareer();
			
			
			// Get the current buying list
			MerchantRecipeList buyingList = ReflectionHelper.getPrivateValue(EntityVillager.class, villager, new String[]{"buyingList", "field_70963_i"});
							
			
			// --- If the career and careerLevel are null, assign them values --- //
			
			// Random career based on profession
			if ( career<=0 ) {
				career = ExtendedVillager.pickRandomCareer(villager.worldObj.rand, villager.getProfession());
				ev.setCareer(career);
				}
			
			// Compare the villager's CareerLevel against the number of trades it has.
			
			// --- Career-based trade repopulator --- /// 
			
			// Flow plan:
			// 
			// 1. Check the number of trades.
			// 2. If the number of trades is less than (CareerLevel+1), add one trade.
			// 3. Then check all of the trades and remove "illegal" ones.
			// 4. Check the number of trades.
			// 6. Check the number of trades.
			// 7. If the number of trades equals CareerLevel+1, finish the algorithm.
			
			
			// 1. Check the number of trades.
			
			while ( 
					//(buyingList == null ? 0 : buyingList.size()) != (careerLevel+listSizeAdd) 
					//|| 
					//(buyingList == null ? 0 : buyingList.size()) <= 0
					true
					) {
				
				// EntityVillager.addDefaultEquipmentAndRecipies(n) adds n unique trades.
				Method addDefaultEquipmentAndRecipies_m = ReflectionHelper.findMethod(
						EntityVillager.class, villager, new String[]{"addDefaultEquipmentAndRecipies", "func_70950_c"},
						Integer.TYPE
						);
				
				// 2. If the number of trades is less than (CareerLevel+listSizeAdd), generate new trades until they're equal.
				
				int mulliganTrades = 0;
				
				while (
						//(buyingList == null ? 0 : buyingList.size()) < (careerLevel+listSizeAdd)
						//|| 
						(buyingList == null ? 0 : buyingList.size()) <= 0
						) {
					try {addDefaultEquipmentAndRecipies_m.invoke(villager, 1);}
            		catch (Exception e) {if (GeneralConfig.debugMessages) LogHelper.warn("Could not invoke EntityVillager.addDefaultEquipmentAndRecipies method");}
					
					// Re-collect the trade list
					buyingList = ReflectionHelper.getPrivateValue( EntityVillager.class, villager, new String[]{"buyingList", "field_70963_i"} );
					
					mulliganTrades++;
					
					if (mulliganTrades >= failuresToForceAcceptance) {
						break;
					}
					
				}
				
				// 3. Then check all of the trades and remove "illegal" ones.
				// Either this is unchanged from before the while loop began, or it was just changed in step (2).
				//buyingList = ReflectionHelper.getPrivateValue( EntityVillager.class, villager, new String[]{"buyingList", "field_70963_i"} );
				
				
				// ---------------------------------------- //
				// --- Check for "inappropriate" trades --- //
				// ---------------------------------------- //
				
				int listSizeBeforeCulling = buyingList.size();
				
				mulliganTrades = 0; // To count how many invalid trades were removed
				
				while (true) {
					
					for (int i=buyingList.size()-1; i >= 0; i--) {
						
	                    //MerchantRecipe merchantrecipe = (MerchantRecipe)iterator.next();
						MerchantRecipe merchantrecipe = (MerchantRecipe)buyingList.get(i);
						
						int slot = i+1;
						
						// v3.1 First pass: check to see if metas have been replaced in certain cases
						if (GeneralConfig.modernVillagerTrades)
						{
							// Fisherman
							if (profession==0 && career==2)
							{
								if (( slot==1 & VillageNames.canVillagerTradesDistinguishMeta ) // has cooked non-cod in slot 1 - set to cod
										&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.fish, 1), new ItemStack(Items.cooked_fished, 1) ))
										&& (merchantrecipe.getItemToSell().getItemDamage() != 0)
												)
								{buyingList.set(i, new MerchantRecipe(new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, slot, 1) ),
										new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(6, 0, slot, 1), 0 ),
										new ItemStack( Items.cooked_fished, 6, 0 ) ));}
								
								else if (( slot==2 & VillageNames.canVillagerTradesDistinguishMeta ) // has cooked non-salmon in slot 2 - set to cod
										&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.fish, 1), new ItemStack(Items.cooked_fished, 1) ))
										&& (merchantrecipe.getItemToSell().getItemDamage() != 0)
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, slot, 2) ),
										new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(6, 0, slot, 2), 1 ),
										new ItemStack( Items.cooked_fished, 6, 1 ) ));}
								
								else if (( slot==2 & VillageNames.canVillagerTradesDistinguishMeta ) // has non-cod in slot 2 - set to cod
										&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.fish, 1), new ItemStack(Items.emerald, 1) ))
										&& (merchantrecipe.getItemToBuy().getItemDamage() != 0)
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(15, 1, slot, 2), 0 ),
										new ItemStack( Items.emerald, 1 ) ));}
								
								else if (( slot==3 & VillageNames.canVillagerTradesDistinguishMeta ) // has non-salmon in slot 3 - set to salmon
										&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.fish, 1), new ItemStack(Items.emerald, 1) ))
										&& (merchantrecipe.getItemToBuy().getItemDamage() != 1)
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(13, 1, slot, 3), 1 ),
										new ItemStack( Items.emerald, 1 ) ));}

								else if (( slot==4 & VillageNames.canVillagerTradesDistinguishMeta ) // has non-clownfish in slot 4 - set to clownfish
										&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.fish, 1), new ItemStack(Items.emerald, 1) ))
										&& (merchantrecipe.getItemToBuy().getItemDamage() != 2)
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(6, 1, slot, 4), 2 ),
										new ItemStack( Items.emerald, 1 ) ));}

								else if (( slot==5 & VillageNames.canVillagerTradesDistinguishMeta ) // has non-pufferfish in slot 5 - set to pufferfish
										&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.fish, 1), new ItemStack(Items.emerald, 1) ))
										&& (merchantrecipe.getItemToBuy().getItemDamage() != 3)
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(4, 1, slot, 5), 3 ),
										new ItemStack( Items.emerald, 1 ) ));}
								
								else if (// This is a differentiated pufferfish trade (Puff + emerald = emerald). Replace it with its appropriate trade.
										VillageNames.canVillagerTradesDistinguishMeta &&
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.fish, 1), new ItemStack(Items.emerald, 1), new ItemStack(Items.emerald, 1) )))
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(4, 1, slot, 5), 3 ),
										new ItemStack( Items.emerald, 1 ) ));}
							}
							
							// Shepherd
							else if (profession==0 && career==3)
							{
								// Dye in slot 3 is of the form (Dye + emerald = emerald).
								// Detect these trades and change them to sell for one emerald.
								if ( merchantrecipe.getItemToBuy() != null
										&& merchantrecipe.getSecondItemToBuy() != null && merchantrecipe.getSecondItemToBuy().getItem() == Items.emerald
										&& merchantrecipe.getItemToSell() != null && merchantrecipe.getItemToSell().getItem() == Items.emerald
										)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( merchantrecipe.getItemToBuy().getItem(), merchantrecipe.getItemToBuy().stackSize, merchantrecipe.getItemToBuy().getItemDamage() ),
										new ItemStack( Items.emerald, 1 ) ));}
								// Dye in slot 4 is of the form (Dye + diamond = diamond).
								// Detect these trades and change them to sell for one emerald.
								else if ( merchantrecipe.getItemToBuy() != null
										&& merchantrecipe.getSecondItemToBuy() != null && merchantrecipe.getSecondItemToBuy().getItem() == Items.diamond
										&& merchantrecipe.getItemToSell() != null && merchantrecipe.getItemToSell().getItem() == Items.diamond
										)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( merchantrecipe.getItemToBuy().getItem(), merchantrecipe.getItemToBuy().stackSize, merchantrecipe.getItemToBuy().getItemDamage() ),
										new ItemStack( Items.emerald, 1 ) ));}
							}
							
							// Fletcher
							else if (profession==0 && career==4)
							{
								// In order to distinguish bow from enchanted bow, the enchanted bow sells for two distinct emerald stacks
								if ( merchantrecipe.getItemToBuy() != null && merchantrecipe.getItemToBuy().getItem() == Items.emerald
										&& merchantrecipe.getSecondItemToBuy() != null && merchantrecipe.getSecondItemToBuy().getItem() == Items.emerald
										)
								{int enchantvalue = 5 + villager.worldObj.rand.nextInt(15);
								buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(2+villager.worldObj.rand.nextInt(2)), 1, slot, 4) ),
										EnchantmentHelper.addRandomEnchantment(villager.worldObj.rand, new ItemStack(Items.bow, 1, 0), enchantvalue ) ) );}
							}
							
							// Librarian
							else if (profession==1 && career==1)
							{
								if ( merchantrecipe.getItemToBuy() != null && merchantrecipe.getItemToBuy().getItem() == Items.emerald
										&& merchantrecipe.getSecondItemToBuy() != null && merchantrecipe.getSecondItemToBuy().getItem() == Items.book
										&& merchantrecipe.getItemToSell() != null && merchantrecipe.getItemToSell().getItem() == Items.emerald
										)
								{
									// This is a placeholder for an enchanted book, to allow multiples from the same librarian.
									{
										Enchantment enchantment = Enchantment.enchantmentsBookList[villager.worldObj.rand.nextInt(Enchantment.enchantmentsBookList.length)];
										int enchLevel = MathHelper.getRandomIntegerInRange(villager.worldObj.rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
										buyingList.set(i,
												FunctionsVN.modernEnchantedBookTrade(villager.worldObj.rand)
												/*new MerchantRecipe(
								        		new ItemStack(Items.emerald, FunctionsVN.modernTradeCostBySlot((villager.worldObj.rand.nextInt(5 + enchLevel * 10) + 3 * enchLevel), 1, slot, 3) ),
								        		new ItemStack(Items.book),
								        		Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, enchLevel))
								        		)*/
												);
									}
								}
								
							}
							
							
							// Leatherworker
							else if (profession==4 && career==2)
							{
								// Detect these trades and change them to sell for one emerald stack instead of two.
								if ( merchantrecipe.getItemToBuy() != null && merchantrecipe.getItemToBuy().getItem() == Items.emerald
										&& merchantrecipe.getSecondItemToBuy() != null && merchantrecipe.getSecondItemToBuy().getItem() == Items.emerald
										&& merchantrecipe.getItemToSell() != null
										)
								{buyingList.set(i, new MerchantRecipe(
										merchantrecipe.getItemToBuy(),
										merchantrecipe.getItemToSell() ));}
							}
							
							// This block is for enchanting equipment items that should be but aren't.
							if (
									merchantrecipe.getItemToBuy() != null && merchantrecipe.getItemToBuy().getItem() == Items.emerald
									//&& merchantrecipe.getSecondItemToBuy() == null
									&& merchantrecipe.getItemToSell() != null
									&&
									(
											 // Armorer
											   (profession==3 && career==1 && merchantrecipe.getItemToSell().getItem() == Items.diamond_leggings)
											|| (profession==3 && career==1 && merchantrecipe.getItemToSell().getItem() == Items.diamond_boots)
											|| (profession==3 && career==1 && merchantrecipe.getItemToSell().getItem() == Items.diamond_helmet)
											|| (profession==3 && career==1 && merchantrecipe.getItemToSell().getItem() == Items.diamond_chestplate)
											 // Weaponsmith
											|| (profession==3 && career==2 && merchantrecipe.getItemToSell().getItem() == Items.iron_sword)
											|| (profession==3 && career==2 && merchantrecipe.getItemToSell().getItem() == Items.diamond_axe)
											|| (profession==3 && career==2 && merchantrecipe.getItemToSell().getItem() == Items.diamond_sword)
											 // Toolsmith
											|| (profession==3 && career==3 && merchantrecipe.getItemToSell().getItem() == Items.iron_axe)
											|| (profession==3 && career==3 && merchantrecipe.getItemToSell().getItem() == Items.iron_shovel)
											|| (profession==3 && career==3 && merchantrecipe.getItemToSell().getItem() == Items.iron_pickaxe)
											|| (profession==3 && career==3 && merchantrecipe.getItemToSell().getItem() == Items.diamond_hoe)
											|| (profession==3 && career==3 && merchantrecipe.getItemToSell().getItem() == Items.diamond_axe)
											|| (profession==3 && career==3 && merchantrecipe.getItemToSell().getItem() == Items.diamond_shovel)
											|| (profession==3 && career==3 && merchantrecipe.getItemToSell().getItem() == Items.diamond_pickaxe)
											// Leatherworker
											|| (profession==4 && career==2 && merchantrecipe.getItemToSell().getItem() == Items.leather_chestplate && slot !=1)
											|| (profession==4 && career==2 && merchantrecipe.getItemToSell().getItem() == Items.leather_helmet && slot !=2)
											)
									&& !merchantrecipe.getItemToSell().isItemEnchanted()
									)
							{
								ItemStack itemToEnchant = merchantrecipe.getItemToSell();
								int enchantvalue = 5 + villager.worldObj.rand.nextInt(15);
								itemToEnchant = EnchantmentHelper.addRandomEnchantment(villager.worldObj.rand, itemToEnchant, enchantvalue );
								// Re-apply it to the list.
								buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(enchantvalue+(6+villager.worldObj.rand.nextInt(3)), 1, slot, 3) ),
										itemToEnchant ) );
							}
							
							
							// Change some prices to make them more fair
							
							// Farmer
							if (profession==0 && career==1)
							{
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.cookie, 1) ))
										&& (merchantrecipe.getItemToSell().stackSize < 18)
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 1, slot, 3) ),
										new ItemStack(Items.cookie, 18 ) ));}
								
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.bread, 1) ))
										&& (merchantrecipe.getItemToSell().stackSize < 6)
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 1, slot, 2) ),
										new ItemStack(Items.bread, 6) ));}
							}
							
							// Librarian
							if (profession==1 && career==1)
							{
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.book, 1), new ItemStack(Items.emerald, 1) ))
										&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(4, 1, slot, 2))
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.book, FunctionsVN.modernTradeCostBySlot(4, 1, slot, 2) ),
										new ItemStack(Items.emerald, 1) ));}
								
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Item.getItemFromBlock(Blocks.bookshelf), 1, 0) ))
										&& (merchantrecipe.getItemToSell().stackSize != 3)
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(6, 1, slot, 1) ),
										new ItemStack( Item.getItemFromBlock(Blocks.bookshelf), 3 ) ));}
								
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.compass, 1) ))
										&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(4, 1, slot, 4))
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(4, 1, slot, 4) ),
										new ItemStack(Items.compass, 1 ) ));}
								
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.clock, 1) ))
										&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(5, 1, slot, 4))
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(5, 1, slot, 4) ),
										new ItemStack(Items.clock, 1 ) ));}
							}
							
							// Blacksmith
							if (profession==3 && (career>=1 || career<=3))
							{
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.coal, 1), new ItemStack(Items.emerald, 1) ))
										&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(15, 1, slot, 1))
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.coal, FunctionsVN.modernTradeCostBySlot(15, 1, slot, 1) ),
										new ItemStack(Items.emerald, 1) ));}
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.iron_ingot, 1), new ItemStack(Items.emerald, 1) ))
										&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(4, 1, slot, 2))
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.iron_ingot, FunctionsVN.modernTradeCostBySlot(4, 1, slot, 2) ),
										new ItemStack(Items.emerald, 1) ));}

								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.diamond, 1), new ItemStack(Items.emerald, 1) ))
										&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(1, 1, slot, 4))
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.diamond, FunctionsVN.modernTradeCostBySlot(1, 1, slot, 4) ),
										new ItemStack(Items.emerald, 1) ));}
								
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.chainmail_helmet, 1) ))
										&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(1, 1, slot, 2))
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, slot, 2) ),
										new ItemStack(Items.chainmail_helmet, 1 ) ));}
								
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.chainmail_chestplate, 1) ))
										&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(1, 1, slot, 3))
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, slot, 3) ),
										new ItemStack(Items.chainmail_chestplate, 1 ) ));}
								
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.chainmail_leggings, 1) ))
										&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(3, 1, slot, 2))
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 1, slot, 2) ),
										new ItemStack(Items.chainmail_leggings, 1 ) ));}
								
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.chainmail_boots, 1) ))
										&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(1, 1, slot, 2))
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, slot, 2) ),
										new ItemStack(Items.chainmail_boots, 1 ) ));}
							}
							
							// Butcher
							if (profession==4 && career==1)
							{
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.chicken, 1), new ItemStack(Items.emerald, 1) ))
										&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(14, 1, slot, 1))
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.chicken, FunctionsVN.modernTradeCostBySlot(14, 1, slot, 1) ),
										new ItemStack(Items.emerald, 1) ));}
								
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.porkchop, 1), new ItemStack(Items.emerald, 1) ))
										&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(7, 1, slot, 1))
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.porkchop, FunctionsVN.modernTradeCostBySlot(7, 1, slot, 1) ),
										new ItemStack(Items.emerald, 1) ));}
								
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.beef, 1), new ItemStack(Items.emerald, 1) ))
										&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(10, 1, slot, 3))
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.beef, FunctionsVN.modernTradeCostBySlot(10, 1, slot, 3) ),
										new ItemStack(Items.emerald, 1) ));}
								
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.cooked_porkchop, 1) ))
										&& (merchantrecipe.getItemToSell().stackSize != 5)
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, slot, 2) ),
										new ItemStack(Items.cooked_porkchop, 5 ) ));}
								
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.cooked_chicken, 1) ))
										&& (merchantrecipe.getItemToSell().stackSize != 8)
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, slot, 2) ),
										new ItemStack(Items.cooked_chicken, 8 ) ));}
								
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.cooked_chicken, 1) ))
										&& (merchantrecipe.getItemToSell().stackSize != 8)
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, slot, 3) ),
										new ItemStack(Items.cooked_beef, 3 ) ));}
							}
							
							// Leatherworker
							if (profession==4 && career==2)
							{
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.leather_leggings, 1) ))
										&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(3, 2, slot, 1))
												)
								{
									ItemStack itemStackColorizable = new ItemStack(Items.leather_leggings);
									itemStackColorizable = FunctionsVN.colorizeItemstack(itemStackColorizable, FunctionsVN.combineDyeColors(new int[]{villager.worldObj.rand.nextInt(16), villager.worldObj.rand.nextInt(16)}));
									buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 2, slot, 1) ),
										itemStackColorizable ));}
								
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.leather_boots, 1) ))
										&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(4, 2, slot, 2))
												)
								{buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(4, 2, slot, 2) ),
										new ItemStack(Items.leather_boots, 1 ) ));}
								
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.leather_chestplate, 1) ))
										&& !merchantrecipe.getItemToSell().isItemEnchanted()
										&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(7, 2, slot, 1))
												)
								{
									ItemStack itemStackColorizable = new ItemStack(Items.leather_chestplate);
									itemStackColorizable = FunctionsVN.colorizeItemstack(itemStackColorizable, FunctionsVN.combineDyeColors(new int[]{villager.worldObj.rand.nextInt(16), villager.worldObj.rand.nextInt(16)}));
									buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(7, 2, slot, 1) ),
										itemStackColorizable ));}
								
								if (
										merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.leather_helmet, 1) ))
										&& !merchantrecipe.getItemToSell().isItemEnchanted()
										&& (merchantrecipe.getItemToBuy().stackSize != FunctionsVN.modernTradeCostBySlot(5, 2, slot, 2))
												)
								{
									ItemStack itemStackColorizable = new ItemStack(Items.leather_helmet);
									itemStackColorizable = FunctionsVN.colorizeItemstack(itemStackColorizable, FunctionsVN.combineDyeColors(new int[]{villager.worldObj.rand.nextInt(16), villager.worldObj.rand.nextInt(16)}));
									buyingList.set(i, new MerchantRecipe(
										new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(5, 2, slot, 2) ),
										itemStackColorizable ));}
							}
							
							
							
							
							/*
							if (
									(profession==1 && (career==1 || career==2))
									&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.paper, 1), new ItemStack(Items.emerald, 1) ))
									&& (merchantrecipe.getItemToBuy().stackSize < 22 || merchantrecipe.getItemToBuy().stackSize > 26)
											)
							{buyingList.set(i, new MerchantRecipe(
									new ItemStack( Items.paper, FunctionsVN.modernTradeCostBySlot(24, 1, slot) ),
									new ItemStack(Items.emerald, 1) ));}
							if (
									(profession==1 && career==1)
									&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.book, 1), new ItemStack(Items.emerald, 1) ))
									&& (merchantrecipe.getItemToBuy().stackSize < 4-2 || merchantrecipe.getItemToBuy().stackSize > 4+2)
											)
							{buyingList.set(i, new MerchantRecipe(
									new ItemStack( Items.book, FunctionsVN.modernTradeCostBySlot(4, 1, slot) ),
									new ItemStack(Items.emerald, 1) ));}
							if (
									(profession==2 && career==1)
									&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.experience_bottle, 1) ))
									&& (merchantrecipe.getItemToSell().stackSize != 1)
											)
							{buyingList.set(i, new MerchantRecipe(
									new ItemStack(Items.emerald, FunctionsVN.modernTradeCostBySlot(3, 1, slot)),
									new ItemStack( Items.experience_bottle, 1 )
									));}
							if (
									(profession==2 && career==1)
									&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack(Items.redstone, 1) ))
									&& (merchantrecipe.getItemToSell().stackSize != 2)
											)
							{buyingList.set(i, new MerchantRecipe(
									new ItemStack(Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, slot)),
									new ItemStack( Items.redstone, 2 )
									));}
							/*
							if (
									(profession==4 && career==1)
									&& merchantrecipe.hasSameIDsAs(new MerchantRecipe(new ItemStack(Items.coal, 1), new ItemStack(Items.emerald, 1) ))
									&& (merchantrecipe.getItemToBuy().stackSize < 13 || merchantrecipe.getItemToBuy().stackSize > 17)
											)
							{buyingList.set(i, new MerchantRecipe(
									new ItemStack( Items.coal, FunctionsVN.modernTradeCostBySlot(15, 1, slot) ),
									new ItemStack(Items.emerald, 1) ));}*/
							
							
						}
						
						// This is where I'll put the "illegal trade removal" method.
						// Right now this is a placeholder specifically to remove the paper/emerald Librarian trade
	                    if (
	                    		merchantrecipe==null
	                    		|| merchantrecipe.getItemToBuy()==null || merchantrecipe.getItemToBuy().getItem()==Item.getItemFromBlock(Blocks.air)
								|| merchantrecipe.getItemToSell()==null || merchantrecipe.getItemToSell().getItem()==Item.getItemFromBlock(Blocks.air)
	                    		// Since we're equating careerLevel and trade list position, insert that instead of careerLevel for evaluation.
	                    		|| FunctionsVN.isTradeInappropriate(merchantrecipe, profession, career, i+1)//-listSizeAdd)
	                    		) {
	                    	// summon Villager ~ ~ ~ {Profession:1}
	        				
	                    	// Remove the offending trade
	                    	buyingList.remove(i);
	                    	
	                    }
	                }
					
                	// If the buying list is the same size it was before culling, then it had no inappropriate trades removed. We can stop.
                	if (buyingList.size() == listSizeBeforeCulling) {
                		break;
                	}
                	else {
                		// Apply the culled list to the villager
                    	ReflectionHelper.setPrivateValue(EntityVillager.class, villager, buyingList, new String[]{"buyingList", "field_70963_i"});
                	}
                	
            		
                	// Accept that this is probably an infinite loop.
                	if (mulliganTrades >= failuresToForceAcceptance) {
                		
                		int nextSlotToFill = buyingList.size()+1;
                		
                		if (GeneralConfig.debugMessages) LogHelper.info("Infinite loop suspected while generating villager trades. Stopping with "
                				+ buyingList.size() + " trades"
                						);
                		
                		// If the Villager is a Librarian who already has an enchanted book, they can't get another one,
						// and that's probably why the search failed. So give them one manually.
						if (
								!(GeneralConfig.modernVillagerTrades) // Added in v3.1
								&& profession== 1 // Profession: Librarian
								&& career == 1 // Career: Librarian
								&& buyingList!=null
								&& (nextSlotToFill==1
								|| nextSlotToFill==4
								|| nextSlotToFill==5)
								)
						{
							Enchantment enchantment = Enchantment.enchantmentsBookList[villager.worldObj.rand.nextInt(Enchantment.enchantmentsBookList.length)];
					        int i1 = MathHelper.getRandomIntegerInRange(villager.worldObj.rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
					        buyingList.add(new MerchantRecipe(
					        		new ItemStack(Items.book),
					        		new ItemStack(Items.emerald, Math.min((villager.worldObj.rand.nextInt(5 + i1 * 10) + 3 * i1), 64) ),
					        		Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, i1))
					        		));
					        ReflectionHelper.setPrivateValue(EntityVillager.class, villager, buyingList, new String[]{"buyingList", "field_70963_i"});
						}
						// Modern trades cleanup
						else if (
								GeneralConfig.modernVillagerTrades

								)
						{
							if (profession == 0 && career == 2) // Career: Fisherman
							{
								if (nextSlotToFill==4) // Stuck at 3 trades
								{
									if (VillageNames.canVillagerTradesDistinguishMeta)
									{
										// Add the clownfish trade
		    							buyingList.add(new MerchantRecipe(
												new ItemStack( Items.fish, FunctionsVN.modernTradeCostBySlot(6, 0, nextSlotToFill, 4), 2 ),
												new ItemStack( Items.emerald, 1 ) ));
									}
									else
									{
										// Reverse trade because vanilla 1.7 can't distinguish emeralds
		    							buyingList.add(new MerchantRecipe(
												new ItemStack( Items.emerald, FunctionsVN.modernTradeCostBySlot(1, 1, nextSlotToFill, 4) ),
												new ItemStack( Items.fish, 9, 2 ) ) );
									}
								}
							}
							
						}
                		break;
                	}
                	
					// If the modified list is smaller than the list going in, then add new trades.
					if (buyingList.size() < listSizeBeforeCulling) {
						
						// Generate a replacement trade
                    	try {addDefaultEquipmentAndRecipies_m.invoke(villager, 1);}
	            		catch (Exception e) {if (GeneralConfig.debugMessages) LogHelper.warn("Could not invoke EntityVillager.addDefaultEquipmentAndRecipies method");}
                    	
                    	mulliganTrades++;
                    	
                    	// Reload trade list
                    	buyingList = ReflectionHelper.getPrivateValue( EntityVillager.class, villager, new String[]{"buyingList", "field_70963_i"} );
					}
					
				}
				
				// Should be good to escape.
				if (buyingList.size() == listSizeBeforeCulling) {
            		
					// But first, do some last-minute checks
					
					// If the dust has settled and the poor bastard has absolutely no trades, give him the ol' gold ingot.
					if ( (buyingList == null ? 0 : buyingList.size()) <= 0 ) {
						//LogHelper.info("Adding gold ingots");
						buyingList.addToListWithCheck( new MerchantRecipe(new ItemStack( Items.gold_ingot, 8 + villager.worldObj.rand.nextInt(3) ), new ItemStack(Items.emerald, 1 )) );
						ReflectionHelper.setPrivateValue(EntityVillager.class, villager, buyingList, new String[]{"buyingList", "field_70963_i"});
					}
					// Special handler: if this is a shepherd and the final slot is _selling wool_, then add all the colored varieties.
					else if (
							!(GeneralConfig.modernVillagerTrades) // Added in v3.1
							&& profession== 0 // Profession: Farmer
							&& career == 3 // Career: Shepherd
							&& buyingList!=null
							&& buyingList.size()<=16
							&& ((MerchantRecipe)buyingList.get(buyingList.size()-1)).hasSameIDsAs(
									new MerchantRecipe(new ItemStack(Items.emerald, 1), new ItemStack( Item.getItemFromBlock(Blocks.wool), 1))
									)
							) {
						buyingList.remove(buyingList.size()-1);
						
						final int[] woolRandOrder = shuffledIntArray(0, 15, villager.worldObj.rand);
						
						for (int i=0; i<16; i++) {
							buyingList.add(
								new MerchantRecipe(new ItemStack(Items.emerald, 1 + villager.worldObj.rand.nextInt(2) ), new ItemStack( Item.getItemFromBlock(Blocks.wool), 1, woolRandOrder[i]))
								);
							}
						ReflectionHelper.setPrivateValue(EntityVillager.class, villager, buyingList, new String[]{"buyingList", "field_70963_i"});
					}
					// Go through list of Blacksmith items, and enchant stuff that is not enchanted.
					else if (!(GeneralConfig.modernVillagerTrades) // Added in v3.1
							&& profession == 3) { // Profession: Blacksmith
						
						for (int i=buyingList.size()-1; i >= 0; i--) {
							
							MerchantRecipe merchantrecipe = (MerchantRecipe)buyingList.get(i);
							ItemStack itemToCheck = merchantrecipe.getItemToSell();
							if (!itemToCheck.isItemEnchanted()
									&& 
									(
										   (career==1 && itemToCheck.isItemEqual(new ItemStack(Items.diamond_chestplate)))
										|| (career==2 && itemToCheck.isItemEqual(new ItemStack(Items.iron_sword)) )
										|| (career==2 && itemToCheck.isItemEqual(new ItemStack(Items.diamond_sword)) )
										|| (career==2 && itemToCheck.isItemEqual(new ItemStack(Items.diamond_axe)) )
										|| (career==3 && itemToCheck.isItemEqual(new ItemStack(Items.iron_shovel)) )
										|| (career==3 && itemToCheck.isItemEqual(new ItemStack(Items.iron_pickaxe)) )
										|| (career==3 && itemToCheck.isItemEqual(new ItemStack(Items.diamond_pickaxe)) )
											)
									) {
								ItemStack emeraldPrice = merchantrecipe.getItemToBuy();
								// Enchant the item.
								itemToCheck = EnchantmentHelper.addRandomEnchantment(villager.worldObj.rand, itemToCheck, 5 + villager.worldObj.rand.nextInt(15) );
								// Re-apply it to the list.
								buyingList.set(i, new MerchantRecipe( emeraldPrice, itemToCheck ) );
							}
						}
					}
					// If it's a Leather Tunic, it might be enchanted.
					else if (!(GeneralConfig.modernVillagerTrades) // Added in v3.1
							&& profession == 4 && career == 2)
					{ // Profession: Blacksmith
						
						// Iterate through all four types
						ArrayList<ItemStack> alis = new ArrayList();
						alis.add(new ItemStack(Items.leather_chestplate));
						alis.add(new ItemStack(Items.leather_boots));
						alis.add(new ItemStack(Items.leather_helmet));
						alis.add(new ItemStack(Items.leather_leggings));
						
						for (int j = 0; j <4; j++)
						{
							for (int i=buyingList.size()-1; i >= 0; i--)
							{
								MerchantRecipe merchantrecipe = (MerchantRecipe)buyingList.get(i);
								ItemStack itemToCheck = merchantrecipe.getItemToSell();
								if (!itemToCheck.isItemEnchanted()
										&& itemToCheck.isItemEqual( alis.get(j) )
										) {
									// Enchant the item.
									itemToCheck = EnchantmentHelper.addRandomEnchantment(villager.worldObj.rand, itemToCheck, 5 + villager.worldObj.rand.nextInt(15) );
									// Change the emerald cost
									ItemStack emeraldPrice = new ItemStack(Items.emerald, new int[]{7,4,5,5}[j] + villager.worldObj.rand.nextInt(new int[]{6,5,5,6}[j]));
									// Re-apply it to the list.
									buyingList.set(i, new MerchantRecipe( emeraldPrice, itemToCheck ) );
								}
							}
						}
					}
					
					break;
            	}
				
				// 4. Check the number of trades.
				// Should not be necessary because either it's unchanged from (3) or it was changed but then applied and re-read above.
				
				// 6. Check the number of trades.
				// Same situation as (4).
				
				// 7. If the number of trades equals CareerLevel+listSizeAdd, finish the algorithm.
			}
			
			
			//villager.func_146091_a(buyingList, Items.blaze_powder, new Random(), 1.0F);
			//buyingList = ReflectionHelper.getPrivateValue( EntityVillager.class, villager, new String[]{"buyingList", "field_70963_i"} );
		}
    	
		// v3.2 moved outside trade monitor so that non-vanilla can be sync-checked
		if (ev.getBiomeType()==-1) {ev.setBiomeType(FunctionsVN.returnBiomeTypeForEntityLocation(villager));}
		if (ev.getSkinTone()==-99) {ev.setSkinTone(FunctionsVN.returnSkinToneForEntityLocation(villager));} // v3.2
		
	}
	
	
	/**
	 * Inputs a villager (ideally, a fisherman) and returns a RANDOM boat type to buy.
	 */
	public static Item returnRandomBoatTypeForVillager(EntityVillager villager)
	{
		Item[] boats = new Item[]{
				FunctionsVN.getItemFromName(ModObjects.boatAcaciaUTD),
				FunctionsVN.getItemFromName(ModObjects.boatBirchUTD),
				FunctionsVN.getItemFromName(ModObjects.boatDarkOakUTD),
				FunctionsVN.getItemFromName(ModObjects.boatJungleUTD),
				Items.boat,
				FunctionsVN.getItemFromName(ModObjects.boatSpruceUTD)
		};
		
		int[] randomOrder = shuffledIntArray(0, 5, villager.getRNG());
		for (int i=0; i<6 ; i++) {if (boats[randomOrder[i]]!=null) {return boats[randomOrder[i]];}}
		
		// Failsafe, return ordinary boat.
		return Items.boat;
	}
	

	/**
	 * Inputs an entity and returns a wood type for that entity based on its location.
	 * This is just in case we need to base type on where that entity is. Useful for
	 * if a villager wants to sell an item made of a particular kind of wood.
	 * 
	 * If you are in a biome with certain specific tags or words in the name, it will
	 * return a specific wood. Otherwise, if you are in a biome with the "forest" tag
	 * it will return "oak." Failing all of that, it returns a random wood type.
	 */
	public static String chooseRandomWoodTypeFromLocation(EntityLiving entity)
	{
		BiomeGenBase biome = entity.worldObj.getBiomeGenForCoords((int)entity.posX, (int)entity.posZ);
		
		BiomeDictionary.Type[] typeTags = BiomeDictionary.getTypesForBiome(biome);
		
		ArrayList<String> boatTypes = new ArrayList<String>();
		
		// Add wood types to pool based on name
		if (biome.biomeName.toLowerCase().contains("birch")) {boatTypes.add("birch");}
		if (biome.biomeName.toLowerCase().contains("roofed")) {boatTypes.add("darkoak");}
		
		boolean isForest = false;
		
		// Add wood types to pool based on biome tags
		for (BiomeDictionary.Type type : typeTags)
		{
			if (type==BiomeDictionary.Type.CONIFEROUS) {boatTypes.add("spruce"); continue;}
			if (type==BiomeDictionary.Type.JUNGLE) {boatTypes.add("jungle"); continue;}
			if (type==BiomeDictionary.Type.SAVANNA) {boatTypes.add("acacia"); continue;}
			if (type==BiomeDictionary.Type.FOREST) {isForest=true; continue;}
		}
		
		// Now, pick a boat type from the tags available
		if (boatTypes.size() > 0) {return boatTypes.get(entity.getRNG().nextInt(boatTypes.size()));}
		
		// If none of the above applied, and the "isForest" tag is true, return oak.
		if (isForest) {return "oak";}
		
		return (new String[]{"acacia", "birch", "darkoak", "jungle", "oak", "spruce"})[entity.getRNG().nextInt(6)];
	}
	
	/**
	 * Returns the median value of an int array.
	 * If the returned value is a halfway point, round up or down depending on if the average value is higher or lower than the median.
	 * If it's the same, specify based on roundup parameter.
	 */
	public static int medianIntArray(ArrayList<Integer> array, boolean roundup)
	{
		if (array.size() <=0) return -1;
		
		Collections.sort(array);
		
		//if (GeneralConfig.debugMessages) {LogHelper.info("array: " + array);}
		
		if (array.size() % 2 == 0)
		{
			// Array is even-length. Find average of the middle two values.
			int totalElements = array.size();
			int sumOfMiddleTwo = array.get(totalElements / 2) + array.get(totalElements / 2 - 1);
			
			if (sumOfMiddleTwo%2==0)
			{
				// Average of middle two values is integer
				//LogHelper.info("Median chosen type A: " + sumOfMiddleTwo/2);
				return sumOfMiddleTwo/2;
			}
			else
			{
				// Average of middle two is half-integer.
				// Round this based on whether the average is higher.
				double median = (double)sumOfMiddleTwo/2;
				
				double average = 0;
				for (int i : array) {average += i;}
				average /= array.size();
				
				if (average < median)
				{
					//LogHelper.info("Median chosen type B: " + MathHelper.floor_double(median) );
					return MathHelper.floor_double(median);
				}
				else if (average > median)
				{
					//LogHelper.info("Median chosen type C: " + MathHelper.ceiling_double_int(median) );
					return MathHelper.ceiling_double_int(median);
				}
				else
				{
					//LogHelper.info("Median chosen type D: " + (roundup ? MathHelper.ceiling_double_int(median) : MathHelper.floor_double(median)));
					return roundup ? MathHelper.ceiling_double_int(median) : MathHelper.floor_double(median);
				}
			}
		}
		else
		{
			// Array is odd-length. Take the middle value.
			//LogHelper.info("Median chosen type E: " + array.get(array.size()/2));
			return array.get(array.size()/2);
		}
	}
	
	/**
	 * This method inputs three integers and returns a unique long value based on them.
	 * The purpose of this is to be used as Random.setSeed(unique + worldseed) to ensure that
	 * randomized values for e.g. village names are deterministic based on their coordinates
	 * so that they can be regenerated as necessary.
	 */
	public static long getUniqueLongForXYZ(int x, int y, int z)
	{
		// Find out which of x and/or z are negative in order to discriminate "quadrant"
		boolean xIsNegative = x<0; boolean zIsNegative = z<0;
		// set the inputs to non-negative
		x = Math.abs(x); y = Math.abs(y); z = Math.abs(z);
		
		return ((x+y+z)*(x+y+z+1)*(x+y+z+2)/6 + (y+z)*(y+z+1)/2 + y + (zIsNegative? 1:0)) * (xIsNegative? -2:2);
	}
	
	/**
	 * Inputs two object arrays and joins them, sequentially placing array2 after array1
	 * Adapted from https://www.geeksforgeeks.org/java-program-to-merge-two-arrays/
	 */
	public static String[] joinTwoStringArrays(String[] array1, String[] array2)
	{
        // determines length of firstArray
        int a1len = array1.length;
        
        // determines length of secondArray
        int a2len = array2.length;
        
        // resultant array size
        int aolen = a1len + a2len;
  
        // create the resultant array
        String[] out_array = new String[aolen];
  
        // using the pre-defined function arraycopy
        System.arraycopy(array1, 0, out_array, 0, a1len);
        System.arraycopy(array2, 0, out_array, a1len, a2len);
        
        return out_array;
	}
	
	/*
	// Way to convert from color meta int into string formatting (for e.g. signs)
	public static String mapColorMetaToStringFormat(int colorMeta) {
		HashMap<Integer, String> signColorToFormat = new HashMap<Integer, String>();//new HashMap();
		// This hashmap translates the town's name color on the sign to a color meta value.
		// This meta should be universal through e.g. wool, clay, etc
		signColorToFormat.put(0, "\u00a7f"); //white
		signColorToFormat.put(1, "\u00a76"); //gold
		signColorToFormat.put(2, "\u00a7d"); //light_purple
		signColorToFormat.put(3, "\u00a79"); //blue
		signColorToFormat.put(4, "\u00a7e"); //yellow
		signColorToFormat.put(5, "\u00a7a"); //green
		signColorToFormat.put(6, "\u00a7c"); //red
		signColorToFormat.put(7, "\u00a78"); //dark_gray
		signColorToFormat.put(8, "\u00a77"); //gray
		//signColorToFormat.put(9, "\u00a7b"); //aqua
		signColorToFormat.put(9, "\u00a73"); //dark_aqua
		signColorToFormat.put(10, "\u00a75"); //dark_purple
		signColorToFormat.put(11, "\u00a71"); //dark_blue
		signColorToFormat.put(12, "\u00a70"); //black
		signColorToFormat.put(13, "\u00a72"); //dark_green
		signColorToFormat.put(14, "\u00a74"); //dark_red
		signColorToFormat.put(15, "\u00a70"); //black
		
		// Return a "town color" string
		return signColorToFormat.get(colorMeta);
	}
	*/
}
