package astrotibs.villagenames.item;

import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
/* This ^ is an instance of this object from my mod, and I want this one
 * preserved so that if I ever want to reference it again, I don't need
 * to worry about another mod tinkering with it.
 * 
 * That's a very high-level understanding of what this is. If anyone tries
 * to tinker with my items, they have their version of my items, and I
 * have my own.
 */
public class ModItems {
	
	public static final ItemCodex codex = new ItemCodex();

	public static final ItemVillageBook villageBook = new ItemVillageBook("villagebook");
	public static final ItemVillageBook mineshaftBook = new ItemVillageBook("mineshaftbook");

	// V3 temple types
	public static final ItemVillageBook jungletempleBook = new ItemVillageBook("jungletemplebook");
	public static final ItemVillageBook desertpyramidBook = new ItemVillageBook("desertpyramidbook");
	public static final ItemVillageBook swamphutBook = new ItemVillageBook("swamphutbook");
	public static final ItemVillageBook igloobook = new ItemVillageBook("igloobook");
	
	public static final ItemVillageBook templeBook = new ItemVillageBook("templebook");
	public static final ItemVillageBook strongholdBook = new ItemVillageBook("strongholdbook");
	public static final ItemVillageBook fortressBook = new ItemVillageBook("fortressbook");
	public static final ItemVillageBook monumentBook = new ItemVillageBook("monumentbook");
	public static final ItemVillageBook endcityBook = new ItemVillageBook("endcitybook");
	public static final ItemVillageBook mansionBook = new ItemVillageBook("mansionbook");
	
	// Mod books
	public static final ItemVillageBook moonvillageBook = new ItemVillageBook("moonvillagebook");
	public static final ItemVillageBook koentusvillageBook = new ItemVillageBook("koentusvillagebook");
	public static final ItemVillageBook fronosvillageBook = new ItemVillageBook("fronosvillagebook");
	public static final ItemVillageBook nibiruvillageBook = new ItemVillageBook("nibiruvillagebook");
	public static final ItemVillageBook abandonedbasebook = new ItemVillageBook("abandonedbasebook");
	
	
	public static void init() {
		GameRegistry.registerItem(codex, "codex");
		
		GameRegistry.registerItem(villageBook, "villagebook");
		GameRegistry.registerItem(mineshaftBook, "mineshaftbook");
		GameRegistry.registerItem(jungletempleBook, "jungletemplebook");
		GameRegistry.registerItem(desertpyramidBook, "desertpyramidbook");
		GameRegistry.registerItem(swamphutBook, "swamphutbook");
		GameRegistry.registerItem(igloobook, "igloobook");
		GameRegistry.registerItem(templeBook, "templebook");
		GameRegistry.registerItem(strongholdBook, "strongholdbook");
		
		GameRegistry.registerItem(monumentBook, "monumentbook");
		GameRegistry.registerItem(mansionBook, "mansionbook");
				
		GameRegistry.registerItem(fortressBook, "fortressbook");
		GameRegistry.registerItem(endcityBook, "endcitybook");
		
		// Mod books
		GameRegistry.registerItem(moonvillageBook, "moonvillagebook");
		GameRegistry.registerItem(koentusvillageBook, "koentusvillagebook");
		GameRegistry.registerItem(fronosvillageBook, "fronosvillagebook");
		GameRegistry.registerItem(nibiruvillageBook, "nibiruvillagebook");
		GameRegistry.registerItem(abandonedbasebook, "abandonedbasebook");
		
	}
}
