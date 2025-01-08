package astrotibs.villagenames.item;

import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.prismarine.item.PrismarineCrystals;
import astrotibs.villagenames.prismarine.item.PrismarineShard;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {

    // Codex
    public static final ItemCodex codex = new ItemCodex();

    // Books
    public static final ItemVillageBook villageBook = new ItemVillageBook("villagebook");
    public static final ItemVillageBook mineshaftBook = new ItemVillageBook("mineshaftbook");
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

    // Prismarine
    public static final Item prismarine_shard = new PrismarineShard();
    public static final Item prismarine_crystals = new PrismarineCrystals();

    public static void init() {
        // Codex
        GameRegistry.registerItem(codex, "codex");

        // Books
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

        // Prismarine
        if (GeneralConfig.addPrismarine) {
            GameRegistry.registerItem(prismarine_shard, "prismarine_shard");
            GameRegistry.registerItem(prismarine_crystals, "prismarine_crystals");

            // Oredict registries
            OreDictionary.registerOre("gemPrismarine", prismarine_shard);
            OreDictionary.registerOre("dustPrismarine", prismarine_crystals);
        }
    }
}
