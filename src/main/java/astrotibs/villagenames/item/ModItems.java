package astrotibs.villagenames.item;

import astrotibs.villagenames.reference.Reference;
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
	public static final ItemVillageBook villageBook = new ItemVillageBook();
	public static final ItemFortressBook fortressBook = new ItemFortressBook();
	public static final ItemMineshaftBook mineshaftBook = new ItemMineshaftBook();
	public static final ItemMansionBook mansionBook = new ItemMansionBook();
	public static final ItemMonumentBook monumentBook = new ItemMonumentBook();
	public static final ItemStrongholdBook strongholdBook = new ItemStrongholdBook();
	public static final ItemTempleBook templeBook = new ItemTempleBook();
	public static final ItemEndCityBook endcityBook = new ItemEndCityBook();
	public static final ItemCodex codex = new ItemCodex();
	public static final ItemMoonVillageBook moonvillageBook = new ItemMoonVillageBook();
	public static final ItemKoentusVillageBook koentusvillageBook = new ItemKoentusVillageBook();
	public static final ItemFronosVillageBook fronosvillageBook = new ItemFronosVillageBook();
	public static final ItemNibiruVillageBook nibiruvillageBook = new ItemNibiruVillageBook();
	public static final ItemAbandonedBaseBook abandonedbasebook = new ItemAbandonedBaseBook();
	
	public static void init() {
		GameRegistry.registerItem(villageBook, "villagebook");
		GameRegistry.registerItem(fortressBook, "fortressbook");
		GameRegistry.registerItem(mineshaftBook, "mineshaftbook");
		GameRegistry.registerItem(mansionBook, "mansionbook");
		GameRegistry.registerItem(monumentBook, "monumentbook");
		GameRegistry.registerItem(strongholdBook, "strongholdbook");
		GameRegistry.registerItem(templeBook, "templebook");
		GameRegistry.registerItem(endcityBook, "endcitybook");
		GameRegistry.registerItem(codex, "codex");
		GameRegistry.registerItem(moonvillageBook, "moonvillagebook");
		GameRegistry.registerItem(koentusvillageBook, "koentusvillagebook");
		GameRegistry.registerItem(fronosvillageBook, "fronosvillagebook");
		GameRegistry.registerItem(nibiruvillageBook, "nibiruvillagebook");
		GameRegistry.registerItem(abandonedbasebook, "abandonedbasebook");
	}
}
