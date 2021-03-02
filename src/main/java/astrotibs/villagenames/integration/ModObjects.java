package astrotibs.villagenames.integration;

import astrotibs.villagenames.VillageNames;
import astrotibs.villagenames.block.ModBlocksVN;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.utility.FunctionsVN;
import astrotibs.villagenames.village.StructureVillageVN;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * A holder for string names for various mod items/blocks/etc for easy access
 */
public class ModObjects {
	
	// Constantly referenced domain names
	public static final String DOM_BIOMESOPLENTY = "BiomesOPlenty";
	public static final String DOM_BOTANIA = "Botania";
	public static final String DOM_CHISEL = "chisel";
	public static final String DOM_DQR3 = "DQMIIINext";
	public static final String DOM_ETFUTURUM = "etfuturum";
	public static final String DOM_GANYSSURFACE = "ganyssurface";
	public static final String DOM_HARVESTCRAFT = "harvestcraft";
	public static final String DOM_MALISISDOORS = "malisisdoors";
	public static final String DOM_UPTODATE = "uptodate";
	public static final String DOM_WOODSTUFF = "woodstuff";
	public static final String MFQM_CLASS_ROOT = "MoreFunQuicksandMod.main";
	
    // ---------------- //
	// --- Entities --- //
    // ---------------- //
    
	//public static final String GCAlienVillagerClass = "micdoodle8.mods.galacticraft.core.entities.EntityAlienVillager";
	public static final String MPKoentusVillagerClass = "stevekung.mods.moreplanets.moons.koentus.entities.EntityKoentusianVillager";
	public static final String MPKoentusVillagerClassModern = "stevekung.mods.moreplanets.module.moons.koentus.entities.EntityKoentusianVillager";
	public static final String MPFronosVillagerClass = "stevekung.mods.moreplanets.planets.fronos.entities.EntityFronosVillager";
	public static final String MPFronosVillagerClassModern = "stevekung.mods.moreplanets.module.planets.fronos.entities.EntityFronosVillager";
	
	// Nibiru villager
	public static final String MPNibiruVillagerClass = "stevekung.mods.moreplanets.planets.nibiru.entity.EntityNibiruVillager";
	public static final String MPNibiruVillagerClassModern = "stevekung.mods.moreplanets.module.planets.nibiru.entity.EntityNibiruVillager";
	
	public static final String WitcheryGuardClass = "com.emoniph.witchery.entity.EntityVillageGuard";
	//public static final String WitcheryVampireClass = "com.emoniph.witchery.entity.EntityVampire";
	//public static final String WitcheryHobgoblinClass = "com.emoniph.witchery.entity.EntityGoblin";
	//public static final String WitcheryHunterClass = "com.emoniph.witchery.entity.EntityWitchHunter";
	
	// Primitive Mobs class paths
	public static final String PMTravelingMerchantClass = "net.daveyx0.primitivemobs.entity.passive.EntityTravelingMerchant"; //Counts as a Villager
	public static final String PMLostMinerClass = "net.daveyx0.primitivemobs.entity.passive.EntityLostMiner"; //Counts as a Villager
	public static final String PMSheepmanClass = "net.daveyx0.primitivemobs.entity.passive.EntitySheepman"; //Counts as a Villager
	public static final String PMSheepmanSmithClass = "net.daveyx0.primitivemobs.entity.passive.EntitySheepmanSmith";
	// Primitive Mobs unlocalized names
	public static final String PMTravelingMerchantUnlocalized = "entity.primitivemobs.TravelingMerchant.name";
	public static final String PMTravelingMerchantUnlocalizedModern = "entity.primitivemobs.traveling_merchant.name";
	public static final String PMLostMinerUnlocalized = "entity.primitivemobs.LostMiner.name";
	public static final String PMLostMinerUnlocalizedModern = "entity.primitivemobs.lost_miner.name";
	public static final String PMSheepmanUnlocalized = "entity.primitivemobs.Sheepman.name";
	public static final String PMSheepmanUnlocalizedModern = "entity.primitivemobs.sheepman.name";
	public static final String PMSheepmanSmithUnlocalized = "entity.primitivemobs.SheepmanSmith.name";
	
	
	// ------------------------------------------------------------ //
	// --- Blocks and items reference for trades and generation --- //
	// ------------------------------------------------------------ //
	
	// --- Blocks --- //
	
	// Banner
	public static final String bannerEF = DOM_ETFUTURUM + ":banner";
	public static final String bannerGS = DOM_GANYSSURFACE + ":banner";
	
	// Bark
	public static final String barkEF = DOM_ETFUTURUM + ":bark";
	public static final String bark2EF = DOM_ETFUTURUM + ":bark2";
	
	// Barrel
	public static final String barrelUTD = DOM_UPTODATE + ":barrel";
	public static final String barrelEF = DOM_ETFUTURUM + ":barrel";

	// Blast Furnace
	public static final String blastFurnace_EF = DOM_ETFUTURUM + ":blast_furnace";
	
	// Blue Ice
	public static final String blueIce_EF = DOM_ETFUTURUM + ":blue_ice";
	
	// Bookshelf
	public static final String bookshelfGS = DOM_GANYSSURFACE + ":bookshelf";
	public static final String bookshelfOakWS = DOM_WOODSTUFF + ":bookshelf_tile.wood_0";
	public static final String bookshelfSpruceWS = DOM_WOODSTUFF + ":bookshelf_tile.wood_1";
	public static final String bookshelfBirchWS = DOM_WOODSTUFF + ":bookshelf_tile.wood_2";
	public static final String bookshelfJungleWS = DOM_WOODSTUFF + ":bookshelf_tile.wood_3";
	public static final String bookshelfAcaciaWS = DOM_WOODSTUFF + ":bookshelf_tile.wood_4";
	public static final String bookshelfDarkOakWS = DOM_WOODSTUFF + ":bookshelf_tile.wood_5";
	
	// Bountiful rocks
	public static final String andesiteC2 = DOM_CHISEL + ":andesite";
	public static final String dioriteC2 = DOM_CHISEL + ":diorite";
	public static final String graniteC2 = DOM_CHISEL + ":granite";
	public static final String stoneBo = DOM_BOTANIA + ":stone";
	public static final String stoneEF = DOM_ETFUTURUM + ":stone";
	public static final String stoneGS = DOM_GANYSSURFACE + ":18Stones";
	public static final String stoneUTD = DOM_UPTODATE + ":stone";
	public static final String andesiteSlabUTD = DOM_UPTODATE + ":slab_andesite";
	public static final String andesiteStairsUTD = DOM_UPTODATE + ":stairs_andesite";
	public static final String dioriteSlabUTD = DOM_UPTODATE + ":slab_diorite";
	public static final String dioriteStairsUTD = DOM_UPTODATE + ":stairs_diorite";
	public static final String graniteSlabUTD = DOM_UPTODATE + ":slab_granite";
	public static final String graniteStairsUTD = DOM_UPTODATE + ":stairs_granite";
	public static final String andesiteSlabBo = DOM_BOTANIA + ":stone0Slab";
	public static final String andesiteStairsBo = DOM_BOTANIA + ":stone0Stairs";
	public static final String dioriteSlabBo = DOM_BOTANIA + ":stone2Slab";
	public static final String dioriteStairsBo = DOM_BOTANIA + ":stone2Stairs";
	public static final String graniteSlabBo = DOM_BOTANIA + ":stone3Slab";
	public static final String graniteStairsBo = DOM_BOTANIA + ":stone3Stairs";
	
	// Brewing Stand
	public static final String brewingStandEF = DOM_ETFUTURUM + ":brewing_stand";
	
	// Buttons
	public static final String buttonSpruceEF = DOM_ETFUTURUM + ":button_spruce";
	public static final String buttonBirchEF = DOM_ETFUTURUM + ":button_birch";
	public static final String buttonJungleEF = DOM_ETFUTURUM + ":button_jungle";
	public static final String buttonAcaciaEF = DOM_ETFUTURUM + ":button_acacia";
	public static final String buttonDarkOakEF = DOM_ETFUTURUM + ":button_dark_oak";
	
	public static final String buttonSpruceGS = DOM_GANYSSURFACE + ":button1";
	public static final String buttonBirchGS = DOM_GANYSSURFACE + ":button2";
	public static final String buttonJungleGS = DOM_GANYSSURFACE + ":button3";
	public static final String buttonAcaciaGS = DOM_GANYSSURFACE + ":button4";
	public static final String buttonDarkOakGS = DOM_GANYSSURFACE + ":button5";
	
	public static final String buttonSpruceUTD = DOM_UPTODATE + ":button_spruce";
	public static final String buttonBirchUTD = DOM_UPTODATE + ":button_birch";
	public static final String buttonJungleUTD = DOM_UPTODATE + ":button_jungle";
	public static final String buttonAcaciaUTD = DOM_UPTODATE + ":button_acacia";
	public static final String buttonDarkOakUTD = DOM_UPTODATE + ":button_dark_oak";
	
	public static final String buttonOakWS = DOM_WOODSTUFF + ":button_tile.wood_0";
	public static final String buttonSpruceWS = DOM_WOODSTUFF + ":button_tile.wood_1";
	public static final String buttonBirchWS = DOM_WOODSTUFF + ":button_tile.wood_2";
	public static final String buttonJungleWS = DOM_WOODSTUFF + ":button_tile.wood_3";
	public static final String buttonAcaciaWS = DOM_WOODSTUFF + ":button_tile.wood_4";
	public static final String buttonDarkOakWS = DOM_WOODSTUFF + ":button_tile.wood_5";
	
	// Campfire
	public static final String campfirebackport = "campfirebackport:campfire";
	
	// Chests
	public static final String chestOakGS = DOM_GANYSSURFACE + ":chest0";
	public static final String chestSpruceGS = DOM_GANYSSURFACE + ":chest1";
	public static final String chestBirchGS = DOM_GANYSSURFACE + ":chest2";
	public static final String chestJungleGS = DOM_GANYSSURFACE + ":chest3";
	public static final String chestAcaciaGS = DOM_GANYSSURFACE + ":chest4";
	public static final String chestDarkOakGS = DOM_GANYSSURFACE + ":chest5";
	
	public static final String chestOakWS = DOM_WOODSTUFF + ":chest_tile.wood_0";
	public static final String chestSpruceWS = DOM_WOODSTUFF + ":chest_tile.wood_1";
	public static final String chestBirchWS = DOM_WOODSTUFF + ":chest_tile.wood_2";
	public static final String chestJungleWS = DOM_WOODSTUFF + ":chest_tile.wood_3";
	public static final String chestAcaciaWS = DOM_WOODSTUFF + ":chest_tile.wood_4";
	public static final String chestDarkOakWS = DOM_WOODSTUFF + ":chest_tile.wood_5";
	
	// Colored Bed
	public static final String coloredBedBlockBV = "bettervanilla:bettervanilla_colored_bed_block";
	
	// Compost Bin
	public static final String compostBinGC = "GardenCore:compost_bin";
	
	// Concrete
	public static final String concreteUTD = DOM_UPTODATE + ":concrete";
	public static final String concreteWhiteEF = DOM_ETFUTURUM + ":concrete_white";
	public static final String concreteOrangeEF = DOM_ETFUTURUM + ":concrete_orange";
	public static final String concreteMagentaEF = DOM_ETFUTURUM + ":concrete_magenta";
	public static final String concreteLightBlueEF = DOM_ETFUTURUM + ":concrete_lightBlue";
	public static final String concreteYellowEF = DOM_ETFUTURUM + ":concrete_yellow";
	public static final String concreteLimeEF = DOM_ETFUTURUM + ":concrete_lime";
	public static final String concretePinkEF = DOM_ETFUTURUM + ":concrete_pink";
	public static final String concreteGrayEF = DOM_ETFUTURUM + ":concrete_gray";
	public static final String concreteSilverEF = DOM_ETFUTURUM + ":concrete_silver";
	public static final String concreteCyanEF = DOM_ETFUTURUM + ":concrete_cyan";
	public static final String concretePurpleEF = DOM_ETFUTURUM + ":concrete_purple";
	public static final String concreteBlueEF = DOM_ETFUTURUM + ":concrete_blue";
	public static final String concreteBrownEF = DOM_ETFUTURUM + ":concrete_brown";
	public static final String concreteGreenEF = DOM_ETFUTURUM + ":concrete_green";
	public static final String concreteRedEF = DOM_ETFUTURUM + ":concrete_red";
	public static final String concreteBlackEF = DOM_ETFUTURUM + ":concrete_black";
	public static final String concreteEF = DOM_ETFUTURUM + "etfuturum:concrete"; // EF:R
	
	// Crafting Table
	public static final String craftingTableOakWS = DOM_WOODSTUFF + ":crafting_table_tile.wood_0"; 
	public static final String craftingTableSpruceWS = DOM_WOODSTUFF + ":crafting_table_tile.wood_1"; 
	public static final String craftingTableBirchWS = DOM_WOODSTUFF + ":crafting_table_tile.wood_2"; 
	public static final String craftingTableJungleWS = DOM_WOODSTUFF + ":crafting_table_tile.wood_3"; 
	public static final String craftingTableAcaciaWS = DOM_WOODSTUFF + ":crafting_table_tile.wood_4"; 
	public static final String craftingTableDarkOakWS = DOM_WOODSTUFF + ":crafting_table_tile.wood_5"; 
	
	// Crops
	public static final String cropHerbDRQ = DOM_DQR3 + ":blockYakusouSeed1";
	public static final String cropAntidoteHerbDRQ = DOM_DQR3 + ":blockDokukesisouSeed1";
	public static final String cropStrengthSeedDRQ = DOM_DQR3 + ":blockTikaraSeed1";
	public static final String cropDefenceSeedDRQ = DOM_DQR3 + ":blockMamoriSeed1";
	public static final String cropAgilitySeedDRQ = DOM_DQR3 + ":blockSubayasaSeed1";
	
	public static final String cropArtichokeHC = DOM_HARVESTCRAFT + ":pamartichokeCrop";
	public static final String cropAsparagusHC = DOM_HARVESTCRAFT + ":pamasparagusCrop";
	public static final String cropBambooHC = DOM_HARVESTCRAFT + ":pambambooshootCrop";
	public static final String cropBarleyHC = DOM_HARVESTCRAFT + ":pambarleyCrop";
	public static final String cropBeanHC = DOM_HARVESTCRAFT + ":pambeanCrop";
	public static final String cropBeetHC = DOM_HARVESTCRAFT + ":pambeetCrop";
	public static final String cropBellpepperHC = DOM_HARVESTCRAFT + ":pambellpepperCrop";
	public static final String cropBlackberryHC = DOM_HARVESTCRAFT + ":pamblackberryCrop";
	public static final String cropBlueberryHC = DOM_HARVESTCRAFT + ":pamblueberryCrop";
	public static final String cropBroccoliHC = DOM_HARVESTCRAFT + ":pambroccoliCrop";
	public static final String cropBrusselsproutHC = DOM_HARVESTCRAFT + ":pambrusselsproutCrop";
	public static final String cropCabbageHC = DOM_HARVESTCRAFT + ":pamcabbageCrop";
	public static final String cropCactusfruitHC = DOM_HARVESTCRAFT + ":pamcactusfruitCrop"; // Planted on sand
	public static final String cropCandleberryHC = DOM_HARVESTCRAFT + ":pamcandleberryCrop";
	public static final String cropCantaloupeHC = DOM_HARVESTCRAFT + ":pamcantaloupeCrop";
	public static final String cropCauliflowerHC = DOM_HARVESTCRAFT + ":pamcauliflowerCrop";
	public static final String cropCeleryHC = DOM_HARVESTCRAFT + ":pamceleryCrop";
	public static final String cropChilipepperHC = DOM_HARVESTCRAFT + ":pamchilipepperCrop";
	public static final String cropCoffeebeanHC = DOM_HARVESTCRAFT + ":pamcoffeebeanCrop";
	public static final String cropCornHC = DOM_HARVESTCRAFT + ":pamcornCrop";
	public static final String cropCottonHC = DOM_HARVESTCRAFT + ":pamcottonCrop";
	public static final String cropCranberryHC = DOM_HARVESTCRAFT + ":pamcranberryCrop"; // Planted on water
	public static final String cropCucumberHC = DOM_HARVESTCRAFT + ":pamcucumberCrop";
	public static final String cropCurryleafHC = DOM_HARVESTCRAFT + ":pamcurryleafCrop";
	public static final String cropEggplantHC = DOM_HARVESTCRAFT + ":pameggplantCrop";
	public static final String cropGarlicHC = DOM_HARVESTCRAFT + ":pamgarlicCrop";
	public static final String cropGingerHC = DOM_HARVESTCRAFT + ":pamgingerCrop";
	public static final String cropGrapeHC = DOM_HARVESTCRAFT + ":pamgrapeCrop";
	public static final String cropKiwiHC = DOM_HARVESTCRAFT + ":pamkiwiCrop";
	public static final String cropLeekHC = DOM_HARVESTCRAFT + ":pamleekCrop";
	public static final String cropLettuceHC = DOM_HARVESTCRAFT + ":pamlettuceCrop";
	public static final String cropMustardseedHC = DOM_HARVESTCRAFT + ":pammustardseedsCrop";
	public static final String cropOatsHC = DOM_HARVESTCRAFT + ":pamoatsCrop";
	public static final String cropOkraHC = DOM_HARVESTCRAFT + ":pamokraCrop";
	public static final String cropOnionHC = DOM_HARVESTCRAFT + ":pamonionCrop";
	public static final String cropParsnipHC = DOM_HARVESTCRAFT + ":pamparsnipCrop";
	public static final String cropPeanutHC = DOM_HARVESTCRAFT + ":pampeanutCrop";
	public static final String cropPeasHC = DOM_HARVESTCRAFT + ":pampeasCrop";
	public static final String cropPineappleHC = DOM_HARVESTCRAFT + ":pampineappleCrop";
	public static final String cropRadishHC = DOM_HARVESTCRAFT + ":pamradishCrop";
	public static final String cropRaspberryHC = DOM_HARVESTCRAFT + ":pamraspberryCrop";
	public static final String cropRhubarbHC = DOM_HARVESTCRAFT + ":pamrhubarbCrop";
	public static final String cropRiceHC = DOM_HARVESTCRAFT + ":pamriceCrop"; // Planted on water
	public static final String cropRutabegaHC = DOM_HARVESTCRAFT + ":pamrutabagaCrop";
	public static final String cropRyeHC = DOM_HARVESTCRAFT + ":pamryeCrop";
	public static final String cropScallionHC = DOM_HARVESTCRAFT + ":pamscallionCrop";
	public static final String cropSeaweedHC = DOM_HARVESTCRAFT + ":pamseaweedCrop"; // Planted on water
	public static final String cropSesameseedHC = DOM_HARVESTCRAFT + ":pamsesameseedsCrop";
	public static final String cropSoybeanHC = DOM_HARVESTCRAFT + ":pamsoybeanCrop";
	public static final String cropSpiceleafHC = DOM_HARVESTCRAFT + ":pamspiceleafCrop";
	public static final String cropSpinachHC = DOM_HARVESTCRAFT + ":pamspinachCrop";
	public static final String cropStrawberryHC = DOM_HARVESTCRAFT + ":pamstrawberryCrop";
	public static final String cropSweetpotatoHC = DOM_HARVESTCRAFT + ":pamsweetpotatoCrop";
	public static final String cropTealeafHC = DOM_HARVESTCRAFT + ":pamtealeafCrop";
	public static final String cropTomatoHC = DOM_HARVESTCRAFT + ":pamtomatoCrop";
	public static final String cropTurnipHC = DOM_HARVESTCRAFT + ":pamturnipCrop";
	public static final String cropWaterchestnutHC = DOM_HARVESTCRAFT + ":pamwaterchestnutCrop"; // Planted on water
	public static final String cropWhitemushroomHC = DOM_HARVESTCRAFT + ":pamwhitemushroomCrop"; // Planted on log
	public static final String cropWintersquashHC = DOM_HARVESTCRAFT + ":pamwintersquashCrop";
	public static final String cropZucchiniHC = DOM_HARVESTCRAFT + ":pamzucchiniCrop";
	public static final String cropKaleJAFFA = "jaffa:kaleCrop";
	
	// Desk
	public static final String deskBC = "BiblioCraft:BiblioDesk";
	
	// Door
	public static final String doorSpruceGS = DOM_GANYSSURFACE + ":doorSpruce";
	public static final String doorBirchGS = DOM_GANYSSURFACE + ":doorBirch";
	public static final String doorJungleGS = DOM_GANYSSURFACE + ":doorJungle";
	public static final String doorAcaciaGS = DOM_GANYSSURFACE + ":doorAcacia";
	public static final String doorDarkOakGS = DOM_GANYSSURFACE + ":doorDark_oak"; // lol wtf Ganymedes
	public static final String doorSpruceEF = DOM_ETFUTURUM + ":door_spruce";
	public static final String doorBirchEF = DOM_ETFUTURUM + ":door_birch";
	public static final String doorJungleEF = DOM_ETFUTURUM + ":door_jungle";
	public static final String doorAcaciaEF = DOM_ETFUTURUM + ":door_acacia";
	public static final String doorDarkOakEF = DOM_ETFUTURUM + ":door_dark_oak";
	public static final String doorSpruceMD = DOM_MALISISDOORS + ":door_spruce";
	public static final String doorBirchMD = DOM_MALISISDOORS + ":door_birch";
	public static final String doorJungleMD = DOM_MALISISDOORS + ":door_jungle";
	public static final String doorAcaciaMD = DOM_MALISISDOORS + ":door_acacia";
	public static final String doorDarkOakMD = DOM_MALISISDOORS + ":door_dark_oak";
	public static final String doorSpruceUTD = DOM_UPTODATE + ":door_spruce";
	public static final String doorBirchUTD = DOM_UPTODATE + ":door_birch";
	public static final String doorJungleUTD = DOM_UPTODATE + ":door_jungle";
	public static final String doorAcaciaUTD = DOM_UPTODATE + ":door_acacia";
	public static final String doorDarkOakUTD = DOM_UPTODATE + ":door_dark_oak";
	
	// Fence
	public static final String fenceOakEF = DOM_ETFUTURUM + ":fence_oak";
	public static final String fenceSpruceEF = DOM_ETFUTURUM + ":fence_spruce";
	public static final String fenceBirchEF = DOM_ETFUTURUM + ":fence_birch";
	public static final String fenceJungleEF = DOM_ETFUTURUM + ":fence_jungle";
	public static final String fenceAcaciaEF = DOM_ETFUTURUM + ":fence_acacia";
	public static final String fenceDarkOakEF = DOM_ETFUTURUM + ":fence_dark_oak";
	
	public static final String fenceOakGS = DOM_GANYSSURFACE + ":fence_0";
	public static final String fenceSpruceGS = DOM_GANYSSURFACE + ":fence_1";
	public static final String fenceBirchGS = DOM_GANYSSURFACE + ":fence_2";
	public static final String fenceJungleGS = DOM_GANYSSURFACE + ":fence_3";
	public static final String fenceAcaciaGS = DOM_GANYSSURFACE + ":fence_4";
	public static final String fenceDarkOakGS = DOM_GANYSSURFACE + ":fence_5";
	
	public static final String fenceSpruceUTD = DOM_UPTODATE + ":fence_spruce";
	public static final String fenceBirchUTD = DOM_UPTODATE + ":fence_birch";
	public static final String fenceJungleUTD = DOM_UPTODATE + ":fence_jungle";
	public static final String fenceAcaciaUTD = DOM_UPTODATE + ":fence_acacia";
	public static final String fenceDarkOakUTD = DOM_UPTODATE + ":fence_dark_oak";
	
	public static final String fenceOakWS = DOM_WOODSTUFF + ":fence_tile.wood_0";
	public static final String fenceSpruceWS = DOM_WOODSTUFF + ":fence_tile.wood_1";
	public static final String fenceBirchWS = DOM_WOODSTUFF + ":fence_tile.wood_2";
	public static final String fenceJungleWS = DOM_WOODSTUFF + ":fence_tile.wood_3";
	public static final String fenceAcaciaWS = DOM_WOODSTUFF + ":fence_tile.wood_4";
	public static final String fenceDarkOakWS = DOM_WOODSTUFF + ":fence_tile.wood_5";
	
	// Fence Gate
	public static final String fenceGateSpruceEF = DOM_ETFUTURUM + ":fence_gate_spruce";
	public static final String fenceGateBirchEF = DOM_ETFUTURUM + ":fence_gate_birch";
	public static final String fenceGateJungleEF = DOM_ETFUTURUM + ":fence_gate_jungle";
	public static final String fenceGateAcaciaEF = DOM_ETFUTURUM + ":fence_gate_acacia";
	public static final String fenceGateDarkOakEF = DOM_ETFUTURUM + ":fence_gate_dark_oak";
	
	public static final String fenceGateOakGS = DOM_GANYSSURFACE + ":fence_gate_0";
	public static final String fenceGateSpruceGS = DOM_GANYSSURFACE + ":fence_gate_1";
	public static final String fenceGateBirchGS = DOM_GANYSSURFACE + ":fence_gate_2";
	public static final String fenceGateJungleGS = DOM_GANYSSURFACE + ":fence_gate_3";
	public static final String fenceGateAcaciaGS = DOM_GANYSSURFACE + ":fence_gate_4";
	public static final String fenceGateDarkOakGS = DOM_GANYSSURFACE + ":fence_gate_5";
	
	public static final String fenceGateSpruceMD = DOM_MALISISDOORS + ":spruceFenceGate";
	public static final String fenceGateBirchMD = DOM_MALISISDOORS + ":birchFenceGate";
	public static final String fenceGateJungleMD = DOM_MALISISDOORS + ":jungleFenceGate";
	public static final String fenceGateAcaciaMD = DOM_MALISISDOORS + ":acaciaFenceGate";
	public static final String fenceGateDarkOakMD = DOM_MALISISDOORS + ":darkOakFenceGate";
	
	public static final String fenceGateSpruceUTD = DOM_UPTODATE + ":fence_gate_spruce";
	public static final String fenceGateBirchUTD = DOM_UPTODATE + ":fence_gate_birch";
	public static final String fenceGateJungleUTD = DOM_UPTODATE + ":fence_gate_jungle";
	public static final String fenceGateAcaciaUTD = DOM_UPTODATE + ":fence_gate_acacia";
	public static final String fenceGateDarkOakUTD = DOM_UPTODATE + ":fence_gate_dark_oak";
	
	public static final String fenceGateOakWS = DOM_WOODSTUFF + ":fence_gate_tile.wood_0";
	public static final String fenceGateSpruceWS = DOM_WOODSTUFF + ":fence_gate_tile.wood_1";
	public static final String fenceGateBirchWS = DOM_WOODSTUFF + ":fence_gate_tile.wood_2";
	public static final String fenceGateJungleWS = DOM_WOODSTUFF + ":fence_gate_tile.wood_3";
	public static final String fenceGateAcaciaWS = DOM_WOODSTUFF + ":fence_gate_tile.wood_4";
	public static final String fenceGateDarkOakWS = DOM_WOODSTUFF + ":fence_gate_tile.wood_5";
	
	
	// Glazed Terracotta
	// Et Futurum Requiem versions:
	public static final String glazedTerracottaWhiteEF = DOM_ETFUTURUM + ":white_glazed_terracotta";
	public static final String glazedTerracottaOrangeEF = DOM_ETFUTURUM + ":orange_glazed_terracotta";
	public static final String glazedTerracottaMagentaEF = DOM_ETFUTURUM + ":magenta_glazed_terracotta";
	public static final String glazedTerracottaLightBlueEF = DOM_ETFUTURUM + ":light_blue_glazed_terracotta";
	public static final String glazedTerracottaYellowEF = DOM_ETFUTURUM + ":yellow_glazed_terracotta";
	public static final String glazedTerracottaLimeEF = DOM_ETFUTURUM + ":lime_glazed_terracotta";
	public static final String glazedTerracottaPinkEF = DOM_ETFUTURUM + ":pink_glazed_terracotta";
	public static final String glazedTerracottaGrayEF = DOM_ETFUTURUM + ":gray_glazed_terracotta";
	public static final String glazedTerracottaLightGrayEF = DOM_ETFUTURUM + ":light_gray_glazed_terracotta";
	public static final String glazedTerracottaCyanEF = DOM_ETFUTURUM + ":cyan_glazed_terracotta";
	public static final String glazedTerracottaPurpleEF = DOM_ETFUTURUM + ":purple_glazed_terracotta";
	public static final String glazedTerracottaBlueEF = DOM_ETFUTURUM + ":blueglazed_terracotta";
	public static final String glazedTerracottaBrownEF = DOM_ETFUTURUM + ":brown_glazed_terracotta";
	public static final String glazedTerracottaGreenEF = DOM_ETFUTURUM + ":green_glazed_terracotta";
	public static final String glazedTerracottaRedEF = DOM_ETFUTURUM + ":red_glazed_terracotta";
	public static final String glazedTerracottaBlackEF = DOM_ETFUTURUM + ":black_glazed_terracotta";
	
	public static final String glazedTerracottaWhiteUTD = DOM_UPTODATE + ":glazed_terracotta_white";
	public static final String glazedTerracottaOrangeUTD = DOM_UPTODATE + ":glazed_terracotta_orange";
	public static final String glazedTerracottaMagentaUTD = DOM_UPTODATE + ":glazed_terracotta_magenta";
	public static final String glazedTerracottaLightBlueUTD = DOM_UPTODATE + ":glazed_terracotta_light_blue";
	public static final String glazedTerracottaYellowUTD = DOM_UPTODATE + ":glazed_terracotta_yellow";
	public static final String glazedTerracottaLimeUTD = DOM_UPTODATE + ":glazed_terracotta_lime";
	public static final String glazedTerracottaPinkUTD = DOM_UPTODATE + ":glazed_terracotta_pink";
	public static final String glazedTerracottaGrayUTD = DOM_UPTODATE + ":glazed_terracotta_gray";
	public static final String glazedTerracottaLightGrayUTD = DOM_UPTODATE + ":glazed_terracotta_light_gray";
	public static final String glazedTerracottaCyanUTD = DOM_UPTODATE + ":glazed_terracotta_cyan";
	public static final String glazedTerracottaPurpleUTD = DOM_UPTODATE + ":glazed_terracotta_purple";
	public static final String glazedTerracottaBlueUTD = DOM_UPTODATE + ":glazed_terracotta_blue";
	public static final String glazedTerracottaBrownUTD = DOM_UPTODATE + ":glazed_terracotta_brown";
	public static final String glazedTerracottaGreenUTD = DOM_UPTODATE + ":glazed_terracotta_green";
	public static final String glazedTerracottaRedUTD = DOM_UPTODATE + ":glazed_terracotta_red";
	public static final String glazedTerracottaBlackUTD = DOM_UPTODATE + ":glazed_terracotta_black";
	
	// Grass Path
	public static final String grassPathUTD = DOM_UPTODATE + ":grass_path";
	public static final String grassPathEF = DOM_ETFUTURUM + ":grass_path";
	
	// Iron Nuggets
	// Mariculture nugget is Mariculture:materials:33
	public static final String nuggetRC = "Railcraft:nugget"; // Iron Nugget is 0
	public static final String materialsTC = "TConstruct:materials"; // Iron Nugget is 19 
	public static final String materialsTF = "ThermalFoundation:material"; // Iron Nugget is 8
	public static final String ironNuggetUTD = DOM_UPTODATE + ":iron_nugget";
	public static final String ironNuggetEF = DOM_ETFUTURUM + ":nugget_iron";
	public static final String nuggetNL = "netherlicious:Nugget"; // Iron Nugget is 0
	
	// Ladders
	public static final String ladderSpruceGS = DOM_GANYSSURFACE + ":ladder1";
	public static final String ladderBirchGS = DOM_GANYSSURFACE + ":ladder2";
	public static final String ladderJungleGS = DOM_GANYSSURFACE + ":ladder3";
	public static final String ladderAcaciaGS = DOM_GANYSSURFACE + ":ladder4";
	public static final String ladderDarkOakGS = DOM_GANYSSURFACE + ":ladder5";
	
	// Lanterns / Lamps
	public static final String davyLampEM = "enviromine:davy_lamp";
	public static final String lanternEF = DOM_ETFUTURUM + ":lantern";
	public static final String lanternNL = "netherlicious:Lantern";
	public static final String lanternUTD = DOM_UPTODATE + ":lantern";
	
	// Mossy Cobblestone Stairs
	public static final String mossyCobblestoneStairsUTD = DOM_UPTODATE + ":stairs_mossy_cobblestone";
	
	// Red Sandstone - regular is meta 0, chiseled is 1, cut is 2
	public static final String redSandstoneEF = DOM_ETFUTURUM + ":red_sandstone";
	public static final String redSandstoneGS = DOM_GANYSSURFACE + ":red_sandstone";
	public static final String redSandstoneUTD = DOM_UPTODATE + ":red_sandstone";
	
	// Red Sandstone slab
	public static final String redSandstoneSlabEF = DOM_ETFUTURUM + ":red_sandstone_slab";
	public static final String redSandstoneSlabGS = DOM_GANYSSURFACE + ":red_sandstone_slab";
	public static final String redSandstoneSlabUTD = DOM_UPTODATE + ":slab_red_sandstone";
	public static final String cutRedSandstoneSlabUTD = DOM_UPTODATE + ":slab_cut_red_sandstone";
	
	// Cut Sandstone Slab
	public static final String cutSandstoneSlabUTD = DOM_UPTODATE + ":slab_cut_sandstone";
	
	// Red Sandstone Stairs
	public static final String redSandstoneStairsEF = DOM_ETFUTURUM + ":red_sandstone_stairs";
	public static final String redSandstoneStairsGS = DOM_GANYSSURFACE + ":red_sandstone_stairs";
	public static final String redSandstoneStairsUTD = DOM_UPTODATE + ":stairs_red_sandstone";
	
	// Quicksand
	public static final String mudBOP_classPath = "biomesoplenty.common.blocks.BlockMud";
	
	public static final String softSnowMFQM_classPath = MFQM_CLASS_ROOT + ".blocks.BlockSoftSnow";
	public static final String jungleQuicksandMFQM_classPath = MFQM_CLASS_ROOT + ".liquids.BlockJungleQuicksand";
	public static final String moorMFQM_classPath = MFQM_CLASS_ROOT + ".blocks.BlockMorass";
	public static final String dryQuicksandMFQM_classPath = MFQM_CLASS_ROOT + ".liquids.BlockSand";
	public static final String liquidMireMFQM_classPath = MFQM_CLASS_ROOT + ".liquids.BlockStableLiquidMire";
	public static final String liquidChocolateMFQM_classPath = MFQM_CLASS_ROOT + ".blocks.BlockChocolate";
	public static final String sinkingClayMFQM_classPath = MFQM_CLASS_ROOT + ".blocks.BlockSinkingClay";
	public static final String wetPeatMFQM_classPath = MFQM_CLASS_ROOT + ".blocks.BlockWetPeat";
	public static final String mudMFQM_classPath = MFQM_CLASS_ROOT + ".blocks.BlockMud";
	public static final String softQuicksandMFQM_classPath = MFQM_CLASS_ROOT + ".blocks.BlockSoftQuicksand";
	public static final String bogMFQM_classPath = MFQM_CLASS_ROOT + ".liquids.BlockBog";
	public static final String softGravelMFQM_classPath = MFQM_CLASS_ROOT + ".blocks.BlockSoftGravel";
	public static final String mireMFQM_classPath = MFQM_CLASS_ROOT + ".blocks.BlockMire";
	public static final String sinkingSlimeMFQM_classPath = MFQM_CLASS_ROOT + ".liquids.BlockSlime";
	public static final String slurryMFQM_classPath = MFQM_CLASS_ROOT + ".liquids.BlockSlurry";
	public static final String quicksandMFQM_classPath = MFQM_CLASS_ROOT + ".blocks.BlockQuicksand";
	public static final String brownClayMFQM_classPath = MFQM_CLASS_ROOT + ".blocks.BlockBrownClay";
	public static final String denseWebbingMFQM_classPath = MFQM_CLASS_ROOT + ".blocks.BlockDenseWeb";
	public static final String tarMFQM_classPath = MFQM_CLASS_ROOT + ".liquids.BlockTar";
	
	// Smoker
	public static final String smoker_EF = DOM_ETFUTURUM + ":smoker";
	
	// Smooth Stone
	public static final String smoothStoneUTD = DOM_UPTODATE + ":smooth_stone";
	public static final String smoothStoneEF = DOM_ETFUTURUM + ":smooth_stone";
	
	// Smooth Sandstone
	public static final String smoothSandstoneUTD = DOM_UPTODATE + ":smooth_sandstone";
	public static final String smoothSandstoneEF = DOM_ETFUTURUM + ":smooth_sandstone";
	public static final String smoothRedSandstoneEF = DOM_ETFUTURUM + ":smooth_red_sandstone";
	
	// Smooth Sandstone Stairs
	public static final String smoothSandstoneStairsUTD = DOM_UPTODATE + ":stairs_smooth_sandstone";
	public static final String smoothRedSandstoneStairsUTD = DOM_UPTODATE + ":stairs_smooth_red_sandstone";
	
	// Smooth Sandstone Slab
	public static final String smoothSandstoneSlabUTD = DOM_UPTODATE + ":slab_smooth_sandstone";
	public static final String smoothRedSandstoneSlabUTD = DOM_UPTODATE + ":slab_smooth_red_sandstone";
	
	// Walls
	public static final String sandstoneWallUTD = DOM_UPTODATE + ":wall_sandstone";
	public static final String redSandstoneWallUTD = DOM_UPTODATE + ":wall_red_sandstone";
	public static final String brickWallUTD = DOM_UPTODATE + ":wall_bricks";
	public static final String stonebrickWallUTD = DOM_UPTODATE + ":wall_stone_bricks";
	public static final String mossystonebrickWallUTD = DOM_UPTODATE + ":wall_mossy_stone_bricks";
	public static final String andesiteWallUTD = DOM_UPTODATE + ":wall_andesite";
	public static final String dioriteWallUTD = DOM_UPTODATE + ":wall_diorite";
	public static final String graniteWallUTD = DOM_UPTODATE + ":wall_granite";
	public static final String bountifulWallBo = DOM_BOTANIA + ":stone0Wall"; // Meta 0:Andesite, 2:Diorite, 3:Granite
	public static final String wallRC = "Railcraft:wall.alpha"; // Meta 10:Brick, 11:Sandstone
	
	// Wooden Pressure Plate
	public static final String pressurePlateSpruceGS = DOM_GANYSSURFACE + ":pressure_plate_spruce";
	public static final String pressurePlateBirchGS = DOM_GANYSSURFACE + ":pressure_plate_birch";
	public static final String pressurePlateJungleGS = DOM_GANYSSURFACE + ":pressure_plate_jungle";
	public static final String pressurePlateAcaciaGS = DOM_GANYSSURFACE + ":pressure_plate_acacia";
	public static final String pressurePlateDarkOakGS = DOM_GANYSSURFACE + ":pressure_plate_dark_oak";
	
	public static final String pressurePlateSpruceEF = DOM_ETFUTURUM + ":pressure_plate_spruce";
	public static final String pressurePlateBirchEF = DOM_ETFUTURUM + ":pressure_plate_birch";
	public static final String pressurePlateJungleEF = DOM_ETFUTURUM + ":pressure_plate_jungle";
	public static final String pressurePlateAcaciaEF = DOM_ETFUTURUM + ":pressure_plate_acacia";
	public static final String pressurePlateDarkOakEF = DOM_ETFUTURUM + ":pressure_plate_dark_oak";
	
	public static final String pressurePlateSpruceUTD = DOM_UPTODATE + ":pressurePlate1";
	public static final String pressurePlateBirchUTD = DOM_UPTODATE + ":pressurePlate2";
	public static final String pressurePlateJungleUTD = DOM_UPTODATE + ":pressurePlate3";
	public static final String pressurePlateAcaciaUTD = DOM_UPTODATE + ":pressurePlate4";
	public static final String pressurePlateDarkOakUTD = DOM_UPTODATE + ":pressurePlate5";
	
	public static final String pressurePlateOakWS = DOM_WOODSTUFF + ":pressure_plate_tile.wood_0";
	public static final String pressurePlateSpruceWS = DOM_WOODSTUFF + ":pressure_plate_tile.wood_1";
	public static final String pressurePlateBirchWS = DOM_WOODSTUFF + ":pressure_plate_tile.wood_2";
	public static final String pressurePlateJungleWS = DOM_WOODSTUFF + ":pressure_plate_tile.wood_3";
	public static final String pressurePlateAcaciaWS = DOM_WOODSTUFF + ":pressure_plate_tile.wood_4";
	public static final String pressurePlateDarkOakWS = DOM_WOODSTUFF + ":pressure_plate_tile.wood_5";
	
	// Wooden Sign
	public static final String signSpruceGS = DOM_GANYSSURFACE + ":sign1";
	public static final String signBirchGS = DOM_GANYSSURFACE + ":sign2";
	public static final String signJungleGS = DOM_GANYSSURFACE + ":sign3";
	public static final String signAcaciaGS = DOM_GANYSSURFACE + ":sign4";
	public static final String signDarkOakGS = DOM_GANYSSURFACE + ":sign5";
	public static final String signSpruceEF = DOM_ETFUTURUM + ":sign_spruce";
	public static final String signBirchEF = DOM_ETFUTURUM + ":sign_birch";
	public static final String signJungleEF = DOM_ETFUTURUM + ":sign_jungle";
	public static final String signAcaciaEF = DOM_ETFUTURUM + ":sign_acacia";
	public static final String signDarkOakEF = DOM_ETFUTURUM + ":sign_dark_oak";
	public static final String wallSignSpruceEF = DOM_ETFUTURUM + ":wall_sign_spruce";
	public static final String wallSignBirchEF = DOM_ETFUTURUM + ":wall_sign_birch";
	public static final String wallSignJungleEF = DOM_ETFUTURUM + ":wall_sign_jungle";
	public static final String wallSignAcaciaEF = DOM_ETFUTURUM + ":wall_sign_acacia";
	public static final String wallSignDarkOakEF = DOM_ETFUTURUM + ":wall_sign_dark_oak";
	
	// Wooden Trapdoor
	public static final String trapdoorSpruceEF = DOM_ETFUTURUM + ":trapdoor_spruce";
	public static final String trapdoorBirchEF = DOM_ETFUTURUM + ":trapdoor_birch";
	public static final String trapdoorJungleEF = DOM_ETFUTURUM + ":trapdoor_jungle";
	public static final String trapdoorAcaciaEF = DOM_ETFUTURUM + ":trapdoor_acacia";
	public static final String trapdoorDarkOakEF = DOM_ETFUTURUM + ":trapdoor_dark_oak";
	public static final String trapdoorSpruceGS = DOM_GANYSSURFACE + ":trapdoor1";
	public static final String trapdoorBirchGS = DOM_GANYSSURFACE + ":trapdoor2";
	public static final String trapdoorJungleGS = DOM_GANYSSURFACE + ":trapdoor3";
	public static final String trapdoorAcaciaGS = DOM_GANYSSURFACE + ":trapdoor4";
	public static final String trapdoorDarkOakGS = DOM_GANYSSURFACE + ":trapdoor5";
	//public static final String trapdoorSpruceMD = DOM_MALISISDOORS + ":trapdoor_spruce";
	public static final String trapdoorBirchMD = DOM_MALISISDOORS + ":trapdoor_birch";
	public static final String trapdoorJungleMD = DOM_MALISISDOORS + ":trapdoor_jungle";
	public static final String trapdoorAcaciaMD = DOM_MALISISDOORS + ":trapdoor_acacia";
	public static final String trapdoorDarkOakMD = DOM_MALISISDOORS + ":trapdoor_dark_oak";
	public static final String trapdoorSpruceUTD = DOM_UPTODATE + ":trap_door_spruce";
	public static final String trapdoorBirchUTD = DOM_UPTODATE + ":trap_door_birch";
	public static final String trapdoorJungleUTD = DOM_UPTODATE + ":trap_door_jungle";
	public static final String trapdoorAcaciaUTD = DOM_UPTODATE + ":trap_door_acacia";
	public static final String trapdoorDarkOakUTD = DOM_UPTODATE + ":trap_door_dark_oak";
	
	
	
	
	// --- Items --- //
	
	
	// Beetroot
	public static final String beetrootItemEF = DOM_ETFUTURUM + ":beetroot";
	public static final String beetrootItemGS = DOM_GANYSSURFACE + ":beetroot";
	public static final String beetrootCropEF = DOM_ETFUTURUM + ":beetroots";
	public static final String beetrootCropGS = DOM_GANYSSURFACE + ":beetrootBlock";
	
	// Beetroot Seeds
	public static final String beetrootSeedsEF = DOM_ETFUTURUM + ":beetroot_seeds";
	public static final String beetrootSeedsGS = DOM_GANYSSURFACE + ":beetrootSeeds";

	// Beetroot Soup
	public static final String beetrootSoupEF = DOM_ETFUTURUM + ":beetroot_soup";
	public static final String beetrootSoupGS = DOM_GANYSSURFACE + ":beetrootSoup";
	
	// Boats
	public static final String boatBirchUTD = DOM_UPTODATE + ":item_boat_birch";
	public static final String boatSpruceUTD = DOM_UPTODATE + ":item_boat_spruce";
	public static final String boatDarkOakUTD = DOM_UPTODATE + ":item_boat_dark_oak";
	public static final String boatJungleUTD = DOM_UPTODATE + ":item_boat_jungle";
	public static final String boatAcaciaUTD = DOM_UPTODATE + ":item_boat_acacia";
	
	// Colored beds
	public static final String coloredBedItemBV = "bettervanilla:bettervanilla_colored_bed";
	public static final String bedCB = "CarpentersBlocks:itemCarpentersBed";
	
	// Dyes
	public static final String miscBOP = DOM_BIOMESOPLENTY + ":misc"; // Usually used for dyes
	public static final String materialsMC = "Mariculture:materials"; // Usually used for dyes
	public static final String dyeUTD = DOM_UPTODATE + ":dye";
	
	// Flowers
	public static final String flowerUTD = DOM_UPTODATE + ":flower";
	public static final String flowerCornflowerEF = DOM_ETFUTURUM + ":cornflower";
	public static final String flowerLilyOfTheValleyEF = DOM_ETFUTURUM + ":lily_of_the_valley";
	
	// Kelp and Kelp Accessories
	public static final String kelpDriedMC = "Mariculture:plant_static"; // Use meta 1
	public static final String kelpWrapMC = "Mariculture:food"; // Use meta 8
	public static final String kelpDriedBOP = DOM_BIOMESOPLENTY + ":coral1"; // Use meta 11 
	
	// Mutton
	public static final String muttonRawEF = DOM_ETFUTURUM + ":mutton_raw";
	public static final String muttonCookedEF = DOM_ETFUTURUM + ":mutton_cooked";
	public static final String muttonRawGS = DOM_GANYSSURFACE + ":mutton_raw";
	public static final String muttonCookedGS = DOM_GANYSSURFACE + ":mutton_cooked";
	public static final String muttonRawHC = DOM_HARVESTCRAFT + ":muttonrawItem";
	public static final String muttonCookedHC = DOM_HARVESTCRAFT + ":muttoncookedItem";
	public static final String muttonRawUTD = DOM_UPTODATE + ":raw_mutton";
	public static final String muttonCookedUTD = DOM_UPTODATE + ":cooked_mutton";
	
	// Rabbit
	public static final String rabbitHideEF = DOM_ETFUTURUM + ":rabbit_hide";
	public static final String rabbitFootEF = DOM_ETFUTURUM + ":rabbit_foot";
	public static final String rabbitRawEF = DOM_ETFUTURUM + ":rabbit_raw";
	public static final String rabbitCookedEF = DOM_ETFUTURUM + ":rabbit_cooked";
	public static final String rabbitStewEF = DOM_ETFUTURUM + ":rabbit_stew";
	
	// Stripped log
	public static final String strippedLog1EF = DOM_ETFUTURUM + ":log_stripped";
	public static final String strippedLog2EF = DOM_ETFUTURUM + ":log2_stripped";
	public static final String strippedLogOakUTD = DOM_UPTODATE + ":stripped_log_oak";
	public static final String strippedLogSpruceUTD = DOM_UPTODATE + ":stripped_log_spruce";
	public static final String strippedLogBirchUTD = DOM_UPTODATE + ":stripped_log_birch";
	public static final String strippedLogJungleUTD = DOM_UPTODATE + ":stripped_log_jungle";
	public static final String strippedLogAcaciaUTD = DOM_UPTODATE + ":stripped_log_acacia";
	public static final String strippedLogDarkOakUTD = DOM_UPTODATE + ":stripped_log_dark_oak";
	
	// Stripped wood
	public static final String strippedWood1EF = DOM_ETFUTURUM + ":wood_stripped";
	public static final String strippedWood2EF = DOM_ETFUTURUM + ":wood2_stripped";
	// stripped wood for UTD is just log with meta value of 12
	
	// Suspicious Stew
	public static final String suspiciousStewUTD = DOM_UPTODATE + ":suspicious_stew";

	// Sweet Berries
	public static final String sweetBerriesEF = DOM_ETFUTURUM + ":sweet_berries";
	public static final String sweetBerriesUTD = DOM_UPTODATE + ":sweet_berries";
	
	// Tipped arrows
	public static final String tippedArrowEF = DOM_ETFUTURUM + ":tipped_arrow";
	
	// Wood block
	public static final String woodBlockUTD = DOM_UPTODATE + ":wood";
	
	
	// --------------------------- //
	// --- Generator Functions --- //
	// --------------------------- //
	
	
	// Andesite
	public static Object[] chooseModAndesiteBlock()
	{
		String[] modprioritylist = GeneralConfig.modBountifulStone;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("chisel"))
			{
				modblock = Block.getBlockFromName(ModObjects.andesiteC2);
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
			else if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.stoneUTD);
				if (modblock != null) {return new Object[]{modblock, 5};}
			}
			else if (mod.toLowerCase().equals("etfuturum"))
			{
				modblock = Block.getBlockFromName(ModObjects.stoneEF);
				if (modblock != null) {return new Object[]{modblock, 5};}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				modblock = Block.getBlockFromName(ModObjects.stoneGS);
				if (modblock != null) {return new Object[]{modblock, 5};}
			}
			else if (mod.toLowerCase().equals("botania"))
			{
				modblock = Block.getBlockFromName(ModObjects.stoneBo);
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
		}
		return null;
	}
	public static ItemStack chooseModAndesiteItem()
	{
		String[] modprioritylist = GeneralConfig.modBountifulStone;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("chisel"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.andesiteC2));
				if (moditem != null) {return new ItemStack(moditem, 1, 0);}
			}
			else if (mod.toLowerCase().equals("uptodate") & VillageNames.canVillagerTradesDistinguishMeta)
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneUTD));
				if (moditem != null) {return new ItemStack(moditem, 1, 5);}
			}
			else if (mod.toLowerCase().equals("etfuturum") & VillageNames.canVillagerTradesDistinguishMeta)
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneEF));
				if (moditem != null) {return new ItemStack(moditem, 1, 5);}
			}
			else if (mod.toLowerCase().equals("ganyssurface") & VillageNames.canVillagerTradesDistinguishMeta)
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneGS));
				if (moditem != null) {return new ItemStack(moditem, 1, 5);}
			}
			else if (mod.toLowerCase().equals("botania") & VillageNames.canVillagerTradesDistinguishMeta)
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneBo));
				if (moditem != null) {return new ItemStack(moditem, 1, 0);}
			}
		}
		return null;
	}
	public static ItemStack chooseModPolishedAndesiteItem()
	{
		String[] modprioritylist = GeneralConfig.modBountifulStone;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("chisel"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.andesiteC2));
				if (moditem != null) {return new ItemStack(moditem, 1, 1);}
			}
			else if (mod.toLowerCase().equals("uptodate"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneUTD));
				if (moditem != null) {return new ItemStack(moditem, 1, 6);}
			}
			else if (mod.toLowerCase().equals("etfuturum"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneEF));
				if (moditem != null) {return new ItemStack(moditem, 1, 6);}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneGS));
				if (moditem != null) {return new ItemStack(moditem, 1, 6);}
			}
			else if (mod.toLowerCase().equals("botania"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneBo));
				if (moditem != null) {return new ItemStack(moditem, 1, 4);}
			}
		}
		return null;
	}
	public static Object[] chooseModPolishedAndesiteBlock()
	{
		String[] modprioritylist = GeneralConfig.modBountifulStone;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("chisel"))
			{
				modblock = Block.getBlockFromName(ModObjects.andesiteC2);
				if (modblock != null) {return new Object[]{modblock, new Integer(1)};}
			}
			else if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.stoneUTD);
				if (modblock != null) {return new Object[]{modblock, new Integer(6)};}
			}
			else if (mod.toLowerCase().equals("etfuturum"))
			{
				modblock = Block.getBlockFromName(ModObjects.stoneEF);
				if (modblock != null) {return new Object[]{modblock, new Integer(6)};}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				modblock = Block.getBlockFromName(ModObjects.stoneGS);
				if (modblock != null) {return new Object[]{modblock, new Integer(6)};}
			}
			else if (mod.toLowerCase().equals("botania"))
			{
				modblock = Block.getBlockFromName(ModObjects.stoneBo);
				if (modblock != null) {return new Object[]{modblock, new Integer(4)};}
			}
		}
		return null;
	}
	/**
	 * Select an andesite slab block from a mod; returns null otherwise
	 */
	public static Block chooseModAndesiteSlabBlock()
	{
		String[] modprioritylist = GeneralConfig.modBountifulStone;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.andesiteSlabUTD);
				if (modblock != null) {return modblock;}
			}
			else if (mod.toLowerCase().equals("botania"))
			{
				modblock = Block.getBlockFromName(ModObjects.andesiteSlabBo);
				if (modblock != null) {return modblock;}
			}
		}
		return null;
	}
	/**
	 * Select an andesite stairs block from a mod; returns null otherwise
	 */
	public static Block chooseModAndesiteStairsBlock()
	{
		String[] modprioritylist = GeneralConfig.modBountifulStone;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.andesiteStairsUTD);
				if (modblock != null) {return modblock;}
			}
			else if (mod.toLowerCase().equals("botania"))
			{
				modblock = Block.getBlockFromName(ModObjects.andesiteStairsBo);
				if (modblock != null) {return modblock;}
			}
		}
		return null;
	}
	/**
	 * Select an andesite wall from a mod; returns null otherwise
	 */
	public static Object[] chooseModAndesiteWallBlock()
	{
		String[] modprioritylist = GeneralConfig.modBountifulStone;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.andesiteWallUTD);
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
			else if (mod.toLowerCase().equals("botania"))
			{
				modblock = Block.getBlockFromName(ModObjects.bountifulWallBo);
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
		}
		return null;
	}
	
	// Banner
	public static ItemStack chooseModBannerItem()
	{
		String[] modprioritylist = GeneralConfig.modBanner;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("etfuturum"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.bannerEF));
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.bannerGS));
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
		}
		return null;
	}
	
	public static Block chooseModBannerBlock()
	{
		String[] modprioritylist = GeneralConfig.modBanner;
		
		for (String mod : modprioritylist)
		{
			Block moditem=null;
			
			if (mod.toLowerCase().equals("etfuturum"))
			{
				moditem = Block.getBlockFromName(ModObjects.bannerEF);
				if (moditem != null) {return moditem;}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				moditem = Block.getBlockFromName(ModObjects.bannerGS);
				if (moditem != null) {return moditem;}
			}
		}
		return null;
	}
	
	
	// Barrel
	public static ItemStack chooseModBarrelItem()
	{
		String[] modprioritylist = GeneralConfig.modBarrel;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("etfuturum"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.barrelEF));
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
			else if (mod.toLowerCase().equals("uptodate"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.barrelUTD));
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
		}
		return null;
	}
	// Uses furnace metas. 1 is vertical, and horizontal are 2, 3, 4, 5. 0 is inverted
	public static Block chooseModBarrelBlock()
	{
		String[] modprioritylist = GeneralConfig.modBarrel;
		
		for (String mod : modprioritylist)
		{
			Block moditem=null;
			
			if (mod.toLowerCase().equals("etfuturum"))
			{
				moditem = Block.getBlockFromName(ModObjects.barrelEF);
				if (moditem != null) {return moditem;}
			}
			else if (mod.toLowerCase().equals("uptodate"))
			{
				moditem = Block.getBlockFromName(ModObjects.barrelUTD);
				if (moditem != null) {return moditem;}
			}
		}
		return null;
	}
	
	// Bark block
	public static Object[] chooseModBarkBlock(Block block, int meta)
	{
		if (block==Blocks.log)
		{
			Block tryBark = Block.getBlockFromName(ModObjects.barkEF);
			
			if (tryBark != null) // EF bark exists
			{
				return new Object[]{tryBark, meta%4};
			}
			else return new Object[]{block, meta+12};
		}
		else if (block==Blocks.log2)
		{
			Block tryBark = Block.getBlockFromName(ModObjects.bark2EF);
			
			if (tryBark != null) // EF bark exists
			{
				return new Object[]{tryBark, meta%4};
			}
			else return new Object[]{block, meta+12};
		}
		
		return new Object[]{block, meta};
	}
	
	// Bed
	public static void setModBedBlock(World world, int x, int y, int z, int orientationMeta, int colorMeta)
	{
		Block modblock=null;
		boolean setTE = false; // Flagged as true if you need to set a tile entity
		
		modblock = Block.getBlockFromName(ModObjects.coloredBedBlockBV);
		if (modblock != null)
		{
			setTE = true;
		}
		else
		{
			// Vanilla bed only; no nbt value necessary
			modblock = Blocks.bed;
		}
		
		// Set the bed block and metadata here
		world.setBlock(x, y, z, modblock);
		world.setBlockMetadataWithNotify(x, y, z, orientationMeta, 2);
		
		if (setTE)
		{
			// Set the tile entity so that you can assign the color via NBT 
			NBTTagCompound bedNBT = new NBTTagCompound();
        	TileEntity tileentity = world.getTileEntity(x, y, z);
        	tileentity.writeToNBT(bedNBT);
        	bedNBT.setInteger("_color", colorMeta);
        	tileentity.readFromNBT(bedNBT);
        	world.setTileEntity(x, y, z, tileentity);
		}
	}
	
	
	// Beetroot
	public static ItemStack chooseModBeetrootItem()
	{
		String[] modprioritylist = GeneralConfig.modBeetroot;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("etfuturum"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.beetrootItemEF);
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.beetrootItemGS);
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}

		}
		return null;
	}
	public static Block chooseModBeetrootCrop()
	{
		String[] modprioritylist = GeneralConfig.modBeetroot;
		
		for (String mod : modprioritylist)
		{
			Block modblock = null;
			
			if (mod.toLowerCase().equals("etfuturum"))
			{
				modblock = Block.getBlockFromName(ModObjects.beetrootCropEF); 
				if (modblock != null) {return modblock;}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				modblock = Block.getBlockFromName(ModObjects.beetrootCropGS);
				if (modblock != null) {return modblock;}
			}

		}
		return null;
	}
	
	
	
	// Beetroot Seeds
	public static ItemStack chooseModBeetrootSeeds()
	{
		String[] modprioritylist = GeneralConfig.modBeetroot;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("etfuturum"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.beetrootSeedsEF);
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.beetrootSeedsGS);
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}

		}
		return null;
	}
	
	
	
	// Beetroot Soup
	public static ItemStack chooseModBeetrootSoup()
	{
		String[] modprioritylist = GeneralConfig.modBeetroot;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("etfuturum"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.beetrootSoupEF);
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.beetrootSoupGS);
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}

		}
		return null;
	}
	
	
	
	// Blast Furnace
	/**
	 * furnaceOrientation:
	 * 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
	 */
	public static Object[] chooseModBlastFurnaceBlock(int furnaceOrientation, int horizIndex)
	{
		int meta = StructureVillageVN.chooseFurnaceMeta(furnaceOrientation, horizIndex);
		
		Block modblock = Block.getBlockFromName(blastFurnace_EF);
		if (modblock == null) {modblock = Blocks.furnace;}
		
		return new Object[]{modblock, meta};
	}
	
	
	// Blue Ice
    public static Object[] chooseModBlueIceBlock()
    {
    	// None are found, so return ordinary packed ice
    	Block modblock = Block.getBlockFromName(blueIce_EF);
		if (modblock == null) {modblock = Blocks.packed_ice;}
		
    	return new Object[]{modblock, 0};
    }
	
	
	// Bookshelves
	public static Object[] chooseModBookshelf(int materialMeta)
	{
		String[] modprioritylist = GeneralConfig.modBookshelf;
		
		Block modblock=null; int meta=0;
		
		for (String mod : modprioritylist)
		{
			if (mod.toLowerCase().equals("woodstuff"))
			{
				switch (materialMeta)
				{
					case 0: modblock = Block.getBlockFromName(ModObjects.bookshelfOakWS); meta=0; break;
					case 1: modblock = Block.getBlockFromName(ModObjects.bookshelfSpruceWS); meta=0; break;
					case 2: modblock = Block.getBlockFromName(ModObjects.bookshelfBirchWS); meta=0; break;
					case 3: modblock = Block.getBlockFromName(ModObjects.bookshelfJungleWS); meta=0; break;
					case 4: modblock = Block.getBlockFromName(ModObjects.bookshelfAcaciaWS); meta=0; break;
					case 5: modblock = Block.getBlockFromName(ModObjects.bookshelfDarkOakWS); meta=0; break;
				}
				if (modblock!=null) {return new Object[]{modblock, meta};}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				if (materialMeta>0)
				{
					modblock = Block.getBlockFromName(ModObjects.bookshelfGS); meta=materialMeta-1; break;
				}
				if (modblock!=null) {return new Object[]{modblock, meta};}
			}
		}
		
		return new Object[]{Blocks.bookshelf, 0};
	}
	
	
	// Brewing Stand - returns 1.7 vanilla if not found
    public static Block chooseModBrewingStandBlock()
    {
    	Block modblock = Block.getBlockFromName(ModObjects.brewingStandEF);
    	if (modblock == null) {modblock = Blocks.brewing_stand;}
    	
    	return modblock;
    }
	
	
	// Brick Wall
	public static Object[] chooseModBrickWall()
	{
		String[] modprioritylist = GeneralConfig.modWall;
		
		Block modblock=null;
		
		for (String mod : modprioritylist)
		{
			if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.brickWallUTD);
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
			if (mod.toLowerCase().equals("railcraft"))
			{
				modblock = Block.getBlockFromName(ModObjects.wallRC);
				if (modblock != null) {return new Object[]{modblock, 10};}
			}
		}
		
		return null;
	}
	
	
	// Campfire
	/**
     * Give this method the orientation of the campfire and the base mode of the structure it's in,
     * and it'll give you back the required meta value for construction.
     * For relative orientations, use:
     * 
     * HANGING:
     * 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
     *   
     * STANDING:
     * 0=fore-facing (away from you); 4=right-facing; 8=back-facing (toward you); 12=left-facing
     */
	public static Object[] chooseModCampfireBlock(int relativeOrientation, int coordBaseMode)
	{
		Block tryCampfire = Block.getBlockFromName(ModObjects.campfirebackport);
		
		if (tryCampfire!=null)
		{
			return new Object[]{tryCampfire, StructureVillageVN.getSignRotationMeta(relativeOrientation, coordBaseMode, true)};
		}
		
		return new Object[]{Blocks.torch, 0};
	}
	
	
	// Cartography Table
	/**
	 * furnaceOrientation:
	 * 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
	 */
	public static Object[] chooseModCartographyTable(int woodMeta)
	{
		Block modblock = chooseModCraftingTable(woodMeta); int meta = 0;
		
		return new Object[]{modblock, meta};
	}
	
	
	// Chest
	public static Block chooseModChest(int woodMeta)
	{
		String[] modprioritylist = GeneralConfig.modChest;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("ganyssurface"))
			{
				switch (woodMeta)
				{
					case 0: modblock = Block.getBlockFromName(ModObjects.chestOakGS); break;
					case 1: modblock = Block.getBlockFromName(ModObjects.chestSpruceGS); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.chestBirchGS); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.chestJungleGS); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.chestAcaciaGS); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.chestDarkOakGS); break;
				}
				if (modblock != null) {return modblock;}
			}
			else if (mod.toLowerCase().equals("woodstuff"))
			{
				switch (woodMeta)
				{
					case 0: modblock = Block.getBlockFromName(ModObjects.chestOakWS); break;
					case 1: modblock = Block.getBlockFromName(ModObjects.chestSpruceWS); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.chestBirchWS); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.chestJungleWS); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.chestAcaciaWS); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.chestDarkOakWS); break;
				}
				if (modblock != null) {return modblock;}
			}
		}
		
		return Blocks.chest;
	}
	
	
	// Composter
	public static Block chooseModComposterBlock()
	{
		Block modobject=null;
		
		modobject = Block.getBlockFromName(ModObjects.compostBinGC);
		if (modobject != null) {return modobject;}
		
		return null;
	}
	
	
	// Concrete - added in v3.1.2
	public static Object[] chooseModConcrete(int meta)
	{
		String[] modprioritylist = GeneralConfig.modConcrete;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;

			if (mod.toLowerCase().equals("villagenames") && GeneralConfig.addConcrete)
			{
				return new Object[]{ModBlocksVN.blockConcrete, new Integer(meta)};
			}
			else if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.concreteUTD);
				if (modblock != null) {return new Object[]{modblock, new Integer(meta)};}
			}
			else if (mod.toLowerCase().equals("etfuturum"))
			{
				// Try Et Futurum: Requiem entries
				modblock = Block.getBlockFromName(ModObjects.concreteEF);
				if (modblock != null) {return new Object[]{modblock, new Integer(meta)};}
				
				// Try original Et Futurum entries
				switch (meta)
				{
					case 0: modblock = Block.getBlockFromName(ModObjects.concreteWhiteEF); break;
					case 1: modblock = Block.getBlockFromName(ModObjects.concreteOrangeEF); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.concreteMagentaEF); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.concreteLightBlueEF); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.concreteYellowEF); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.concreteLimeEF); break;
					case 6: modblock = Block.getBlockFromName(ModObjects.concretePinkEF); break;
					case 7: modblock = Block.getBlockFromName(ModObjects.concreteGrayEF); break;
					case 8: modblock = Block.getBlockFromName(ModObjects.concreteSilverEF); break;
					case 9: modblock = Block.getBlockFromName(ModObjects.concreteCyanEF); break;
					case 10: modblock = Block.getBlockFromName(ModObjects.concretePurpleEF); break;
					case 11: modblock = Block.getBlockFromName(ModObjects.concreteBlueEF); break;
					case 12: modblock = Block.getBlockFromName(ModObjects.concreteBrownEF); break;
					case 13: modblock = Block.getBlockFromName(ModObjects.concreteGreenEF); break;
					case 14: modblock = Block.getBlockFromName(ModObjects.concreteRedEF); break;
					case 15: modblock = Block.getBlockFromName(ModObjects.concreteBlackEF); break;
				}
				if (modblock != null) {return new Object[]{modblock, new Integer(0)};}
			}
		}
		return null;
	}
	
	
	// Crafting Table
	public static Block chooseModCraftingTable(int materialMeta)
	{
		Block modblock=null; int meta=0;
		
		switch (materialMeta)
		{
			case 0: modblock = Block.getBlockFromName(ModObjects.craftingTableOakWS); break;
			case 1: modblock = Block.getBlockFromName(ModObjects.craftingTableSpruceWS); break;
			case 2: modblock = Block.getBlockFromName(ModObjects.craftingTableBirchWS); break;
			case 3: modblock = Block.getBlockFromName(ModObjects.craftingTableJungleWS); break;
			case 4: modblock = Block.getBlockFromName(ModObjects.craftingTableAcaciaWS); break;
			case 5: modblock = Block.getBlockFromName(ModObjects.craftingTableDarkOakWS); break;
		}
		if (modblock==null) {modblock = Blocks.crafting_table;}
		
		return modblock;
	}
	
	
	/**
	 * Returns a potted cactus
	 */
	public static Object[] chooseGreenCoralOrPottedCactus()
	{
		return new Object[]{Blocks.flower_pot, 9};
	}
	
	
	// Diorite
	public static Object[] chooseModDioriteBlock()
	{
		String[] modprioritylist = GeneralConfig.modBountifulStone;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("chisel"))
			{
				modblock = Block.getBlockFromName(ModObjects.dioriteC2);
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
			else if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.stoneUTD);
				if (modblock != null) {return new Object[]{modblock, 3};}
			}
			else if (mod.toLowerCase().equals("etfuturum"))
			{
				modblock = Block.getBlockFromName(ModObjects.stoneEF);
				if (modblock != null) {return new Object[]{modblock, 3};}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				modblock = Block.getBlockFromName(ModObjects.stoneGS);
				if (modblock != null) {return new Object[]{modblock, 3};}
			}
			else if (mod.toLowerCase().equals("botania"))
			{
				modblock = Block.getBlockFromName(ModObjects.stoneBo);
				if (modblock != null) {return new Object[]{modblock, 2};}
			}
		}
		return null;
	}
	
	public static ItemStack chooseModDioriteItem()
	{
		String[] modprioritylist = GeneralConfig.modBountifulStone;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("chisel"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.dioriteC2));
				if (moditem != null) {return new ItemStack(moditem, 1, 0);}
			}
			else if (mod.toLowerCase().equals("uptodate") & VillageNames.canVillagerTradesDistinguishMeta)
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneUTD));
				if (moditem != null) {return new ItemStack(moditem, 1, 3);}
			}
			else if (mod.toLowerCase().equals("etfuturum") & VillageNames.canVillagerTradesDistinguishMeta)
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneEF));
				if (moditem != null) {return new ItemStack(moditem, 1, 3);}
			}
			else if (mod.toLowerCase().equals("ganyssurface") & VillageNames.canVillagerTradesDistinguishMeta)
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneGS));
				if (moditem != null) {return new ItemStack(moditem, 1, 3);}
			}
			else if (mod.toLowerCase().equals("botania") & VillageNames.canVillagerTradesDistinguishMeta)
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneBo));
				if (moditem != null) {return new ItemStack(moditem, 1, 2);}
			}
		}
		return null;
	}
	
	public static ItemStack chooseModPolishedDioriteItem()
	{
		String[] modprioritylist = GeneralConfig.modBountifulStone;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("chisel"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.dioriteC2));
				if (moditem != null) {return new ItemStack(moditem, 1, 1);}
			}
			else if (mod.toLowerCase().equals("uptodate"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneUTD));
				if (moditem != null) {return new ItemStack(moditem, 1, 4);}
			}
			else if (mod.toLowerCase().equals("etfuturum"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneEF));
				if (moditem != null) {return new ItemStack(moditem, 1, 4);}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneGS));
				if (moditem != null) {return new ItemStack(moditem, 1, 4);}
			}
			else if (mod.toLowerCase().equals("botania"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneBo));
				if (moditem != null) {return new ItemStack(moditem, 1, 6);}
			}
		}
		return null;
	}
	/**
	 * Select a diorite slab block from a mod; returns null otherwise
	 */
	public static Block chooseModDioriteSlabBlock()
	{
		String[] modprioritylist = GeneralConfig.modBountifulStone;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.dioriteSlabUTD);
				if (modblock != null) {return modblock;}
			}
			else if (mod.toLowerCase().equals("botania"))
			{
				modblock = Block.getBlockFromName(ModObjects.dioriteSlabBo);
				if (modblock != null) {return modblock;}
			}
		}
		return null;
	}
	/**
	 * Select a diorite stairs block from a mod; returns null otherwise
	 */
	public static Block chooseModDioriteStairsBlock()
	{
		String[] modprioritylist = GeneralConfig.modBountifulStone;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.dioriteStairsUTD);
				if (modblock != null) {return modblock;}
			}
			else if (mod.toLowerCase().equals("botania"))
			{
				modblock = Block.getBlockFromName(ModObjects.dioriteStairsBo);
				if (modblock != null) {return modblock;}
			}
		}
		return null;
	}
	/**
	 * Select a diorite wall from a mod; returns null otherwise
	 */
	public static Object[] chooseModDioriteWallBlock()
	{
		String[] modprioritylist = GeneralConfig.modBountifulStone;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.dioriteWallUTD);
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
			else if (mod.toLowerCase().equals("botania"))
			{
				modblock = Block.getBlockFromName(ModObjects.bountifulWallBo);
				if (modblock != null) {return new Object[]{modblock, 2};}
			}
		}
		return null;
	}
	
	
	// Dye
	public static ItemStack chooseModBlueDye()
	{
		String[] modprioritylist = GeneralConfig.modDye;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("biomesoplenty"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.miscBOP);
				if (moditem != null) {return new ItemStack(moditem, 1, 5);}
			}
			else if (mod.toLowerCase().equals("mariculture"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.materialsMC);
				if (moditem != null) {return new ItemStack(moditem, 1, 28);}
			}
			else if (mod.toLowerCase().equals("uptodate"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.dyeUTD);
				if (moditem != null) {return new ItemStack(moditem, 1, 0);}
			}
		}
		return null;
	}
	public static ItemStack chooseModBlackDye()
	{
		String[] modprioritylist = GeneralConfig.modDye;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("biomesoplenty"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.miscBOP);
				if (moditem != null) {return new ItemStack(moditem, 1, 9);}
			}
			else if (mod.toLowerCase().equals("uptodate"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.dyeUTD);
				if (moditem != null) {return new ItemStack(moditem, 1, 2);}
			}
		}
		return null;
	}
	public static ItemStack chooseModBrownDye()
	{
		String[] modprioritylist = GeneralConfig.modDye;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("biomesoplenty"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.miscBOP);
				if (moditem != null) {return new ItemStack(moditem, 1, 6);}
			}
			else if (mod.toLowerCase().equals("mariculture"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.materialsMC);
				if (moditem != null) {return new ItemStack(moditem, 1, 32);}
			}
			else if (mod.toLowerCase().equals("uptodate"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.dyeUTD);
				if (moditem != null) {return new ItemStack(moditem, 1, 3);}
			}
		}
		return null;
	}
	public static ItemStack chooseModWhiteDye()
	{
		String[] modprioritylist = GeneralConfig.modDye;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("biomesoplenty"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.miscBOP);
				if (moditem != null) {return new ItemStack(moditem, 1, 8);}
			}
			else if (mod.toLowerCase().equals("mariculture"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.materialsMC);
				if (moditem != null) {return new ItemStack(moditem, 1, 27);}
			}
			else if (mod.toLowerCase().equals("uptodate"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.dyeUTD);
				if (moditem != null) {return new ItemStack(moditem, 1, 1);}
			}
		}
		return null;
	}
	
	
	// Fence
	public static Object[] chooseModFence(int materialMeta)
	{
		String[] modprioritylist = GeneralConfig.modFence;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				switch (materialMeta)
				{
					case 1: modblock = Block.getBlockFromName(ModObjects.fenceSpruceUTD); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.fenceBirchUTD); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.fenceJungleUTD); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.fenceAcaciaUTD); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.fenceDarkOakUTD); break;
				}
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
			else if (mod.toLowerCase().equals("etfuturum"))
			{
				switch (materialMeta)
				{
					case 0: modblock = Block.getBlockFromName(ModObjects.fenceOakEF); break;
					case 1: modblock = Block.getBlockFromName(ModObjects.fenceSpruceEF); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.fenceBirchEF); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.fenceJungleEF); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.fenceAcaciaEF); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.fenceDarkOakEF); break;
				}
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				switch (materialMeta)
				{
					case 0: modblock = Block.getBlockFromName(ModObjects.fenceOakGS); break;
					case 1: modblock = Block.getBlockFromName(ModObjects.fenceSpruceGS); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.fenceBirchGS); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.fenceJungleGS); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.fenceAcaciaGS); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.fenceDarkOakGS); break;
				}
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
			else if (mod.toLowerCase().equals("woodstuff"))
			{
				switch (materialMeta)
				{
					case 0: modblock = Block.getBlockFromName(ModObjects.fenceOakWS); break;
					case 1: modblock = Block.getBlockFromName(ModObjects.fenceSpruceWS); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.fenceBirchWS); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.fenceJungleWS); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.fenceAcaciaWS); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.fenceDarkOakWS); break;
				}
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
		}
		
		return new Object[] {Blocks.fence, 0};
	}
	
	
	// Fence Gate
	public static Block chooseModFenceGate(int materialMeta)
	{
		String[] modprioritylist = GeneralConfig.modFenceGate;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				switch (materialMeta)
				{
					case 1: modblock = Block.getBlockFromName(ModObjects.fenceGateSpruceUTD); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.fenceGateBirchUTD); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.fenceGateJungleUTD); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.fenceGateAcaciaUTD); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.fenceGateDarkOakUTD); break;
				}
				if (modblock != null) {return modblock;}
			}
			else if (mod.toLowerCase().equals("etfuturum"))
			{
				switch (materialMeta)
				{
					case 1: modblock = Block.getBlockFromName(ModObjects.fenceGateSpruceEF); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.fenceGateBirchEF); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.fenceGateJungleEF); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.fenceGateAcaciaEF); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.fenceGateDarkOakEF); break;
				}
				if (modblock != null) {return modblock;}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				switch (materialMeta)
				{
					case 0: modblock = Block.getBlockFromName(ModObjects.fenceGateOakGS); break;
					case 1: modblock = Block.getBlockFromName(ModObjects.fenceGateSpruceGS); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.fenceGateBirchGS); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.fenceGateJungleGS); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.fenceGateAcaciaGS); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.fenceGateDarkOakGS); break;
				}
				if (modblock != null) {return modblock;}
			}
			else if (mod.toLowerCase().equals("malisisdoors"))
			{
				switch (materialMeta)
				{
					case 1: modblock = Block.getBlockFromName(ModObjects.fenceGateSpruceMD); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.fenceGateBirchMD); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.fenceGateJungleMD); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.fenceGateAcaciaMD); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.fenceGateDarkOakMD); break;
				}
				if (modblock != null) {return modblock;}
			}
			else if (mod.toLowerCase().equals("woodstuff"))
			{
				switch (materialMeta)
				{
					case 0: modblock = Block.getBlockFromName(ModObjects.fenceGateOakWS); break;
					case 1: modblock = Block.getBlockFromName(ModObjects.fenceGateSpruceWS); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.fenceGateBirchWS); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.fenceGateJungleWS); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.fenceGateAcaciaWS); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.fenceGateDarkOakWS); break;
				}
				if (modblock != null) {return modblock;}
			}
		}
		// If all else fails, grab the vanilla version
		return Blocks.fence_gate;
	}
	
	
	// Fletching Table
	public static Object[] chooseModFletchingTable(int woodMeta)
	{
		Block modblock = chooseModCraftingTable(woodMeta); int meta = 0;
		
		return new Object[]{modblock, meta};
	}
	
	
	// Flowers
	public static Object[] chooseModCornflower()
	{
		String[] modprioritylist = GeneralConfig.modFlower;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("etfuturum"))
			{
				modblock = Block.getBlockFromName(ModObjects.flowerCornflowerEF);
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
			else if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.flowerUTD);
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
		}
		return null;
	}
	
	public static Object[] chooseModLilyOfTheValley()
	{
		String[] modprioritylist = GeneralConfig.modFlower;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("etfuturum"))
			{
				modblock = Block.getBlockFromName(ModObjects.flowerLilyOfTheValleyEF);
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
			else if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.flowerUTD);
				if (modblock != null) {return new Object[]{modblock, 1};}
			}
		}
		return null;
	}
	
	
	// Glazed Terracotta - added in v3.1.2
	public static Object[] chooseModGlazedTerracotta(int colorMeta, int orientationMeta)
	{
		String[] modprioritylist = GeneralConfig.modGlazedTerracotta;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;

			if (mod.toLowerCase().equals("villagenames") && GeneralConfig.addConcrete)
			{
				switch (colorMeta/4)
				{
					case 0: modblock = ModBlocksVN.glazedTerracotta; break;
					case 1: modblock = ModBlocksVN.glazedTerracotta2; break;
					case 2: modblock = ModBlocksVN.glazedTerracotta3; break;
					case 3: modblock = ModBlocksVN.glazedTerracotta4; break;
				}
				
				// The Village Names schematic is:
				// blockType = colorMeta/4 (as seen above)
				// color metas increment by 1, but cycle every 4
				// Rotations increment by 4
				
				// Thus:
				int convolvedMeta = colorMeta%4 + 4*orientationMeta;
				
				return new Object[]{modblock, new Integer(convolvedMeta)};
			}
			
			if (mod.toLowerCase().equals("etfuturum"))
			{
				// Try original Et Futurum entries
				switch (colorMeta)
				{
					case 0: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaWhiteEF); break;
					case 1: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaOrangeEF); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaMagentaEF); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaLightBlueEF); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaYellowEF); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaLimeEF); break;
					case 6: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaPinkEF); break;
					case 7: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaGrayEF); break;
					case 8: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaLightGrayEF); break;
					case 9: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaCyanEF); break;
					case 10: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaPurpleEF); break;
					case 11: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaBlueEF); break;
					case 12: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaBrownEF); break;
					case 13: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaGreenEF); break;
					case 14: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaRedEF); break;
					case 15: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaBlackEF); break;
				}
				if (modblock != null) {return new Object[]{modblock, new Integer(orientationMeta)};} //TODO - get roadhog's namespace and orientation meta values
			}
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				switch (colorMeta)
				{
					case 0: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaWhiteUTD); break;
					case 1: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaOrangeUTD); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaMagentaUTD); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaLightBlueUTD); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaYellowUTD); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaLimeUTD); break;
					case 6: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaPinkUTD); break;
					case 7: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaGrayUTD); break;
					case 8: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaLightGrayUTD); break;
					case 9: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaCyanUTD); break;
					case 10: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaPurpleUTD); break;
					case 11: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaBlueUTD); break;
					case 12: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaBrownUTD); break;
					case 13: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaGreenUTD); break;
					case 14: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaRedUTD); break;
					case 15: modblock = Block.getBlockFromName(ModObjects.glazedTerracottaBlackUTD); break;
				}
				if (modblock != null) {return new Object[]{modblock, new Integer(orientationMeta)};}
			}
		}
		return null;
	}
	
	
	// Granite
	public static Object[] chooseModGraniteBlock()
	{
		String[] modprioritylist = GeneralConfig.modBountifulStone;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("chisel"))
			{
				modblock = Block.getBlockFromName(ModObjects.graniteC2);
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
			else if (mod.toLowerCase().equals("uptodate") & VillageNames.canVillagerTradesDistinguishMeta)
			{
				modblock = Block.getBlockFromName(ModObjects.stoneUTD);
				if (modblock != null) {return new Object[]{modblock, 1};}
			}
			else if (mod.toLowerCase().equals("etfuturum") & VillageNames.canVillagerTradesDistinguishMeta)
			{
				modblock = Block.getBlockFromName(ModObjects.stoneEF);
				if (modblock != null) {return new Object[]{modblock, 1};}
			}
			else if (mod.toLowerCase().equals("ganyssurface") & VillageNames.canVillagerTradesDistinguishMeta)
			{
				modblock = Block.getBlockFromName(ModObjects.stoneGS);
				if (modblock != null) {return new Object[]{modblock, 1};}
			}
			else if (mod.toLowerCase().equals("botania") & VillageNames.canVillagerTradesDistinguishMeta)
			{
				modblock = Block.getBlockFromName(ModObjects.stoneBo);
				if (modblock != null) {return new Object[]{modblock, 3};}
			}
		}
		return null;
	}
	public static ItemStack chooseModGraniteItem()
	{
		String[] modprioritylist = GeneralConfig.modBountifulStone;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("chisel"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.graniteC2));
				if (moditem != null) {return new ItemStack(moditem, 1, 0);}
			}
			else if (mod.toLowerCase().equals("uptodate"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneUTD));
				if (moditem != null) {return new ItemStack(moditem, 1, 1);}
			}
			else if (mod.toLowerCase().equals("etfuturum"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneEF));
				if (moditem != null) {return new ItemStack(moditem, 1, 1);}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneGS));
				if (moditem != null) {return new ItemStack(moditem, 1, 1);}
			}
			else if (mod.toLowerCase().equals("botania"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneBo));
				if (moditem != null) {return new ItemStack(moditem, 1, 3);}
			}
		}
		return null;
	}
	
	public static ItemStack chooseModPolishedGraniteItem()
	{
		String[] modprioritylist = GeneralConfig.modBountifulStone;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("chisel"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.graniteC2));
				if (moditem != null) {return new ItemStack(moditem, 1, 1);}
			}
			else if (mod.toLowerCase().equals("uptodate"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneUTD));
				if (moditem != null) {return new ItemStack(moditem, 1, 2);}
			}
			else if (mod.toLowerCase().equals("etfuturum"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneEF));
				if (moditem != null) {return new ItemStack(moditem, 1, 2);}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneGS));
				if (moditem != null) {return new ItemStack(moditem, 1, 2);}
			}
			else if (mod.toLowerCase().equals("botania"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneBo));
				if (moditem != null) {return new ItemStack(moditem, 1, 7);}
			}
		}
		return null;
	}
	/**
	 * Select a granite slab block from a mod; returns null otherwise
	 */
	public static Block chooseModGraniteSlabBlock()
	{
		String[] modprioritylist = GeneralConfig.modBountifulStone;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.graniteSlabUTD);
				if (modblock != null) {return modblock;}
			}
			else if (mod.toLowerCase().equals("botania"))
			{
				modblock = Block.getBlockFromName(ModObjects.graniteSlabBo);
				if (modblock != null) {return modblock;}
			}
		}
		return null;
	}
	/**
	 * Select a granite stairs block from a mod; returns null otherwise
	 */
	public static Block chooseModGraniteStairsBlock()
	{
		String[] modprioritylist = GeneralConfig.modBountifulStone;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.graniteStairsUTD);
				if (modblock != null) {return modblock;}
			}
			else if (mod.toLowerCase().equals("botania"))
			{
				modblock = Block.getBlockFromName(ModObjects.graniteStairsBo);
				if (modblock != null) {return modblock;}
			}
		}
		return null;
	}
	/**
	 * Select a granite wall from a mod; returns null otherwise
	 */
	public static Object[] chooseModGraniteWallBlock()
	{
		String[] modprioritylist = GeneralConfig.modBountifulStone;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.graniteWallUTD);
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
			else if (mod.toLowerCase().equals("botania"))
			{
				modblock = Block.getBlockFromName(ModObjects.bountifulWallBo);
				if (modblock != null) {return new Object[]{modblock, 3};}
			}
		}
		return null;
	}
	
	
	
	// Selects a modded Grass Path block if able; returns Gravel otherwise.
	public static Block chooseModPathBlock()
	{
		String[] modprioritylist = GeneralConfig.modGrassPath;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.grassPathUTD);
				if (modblock != null) {return modblock;}
			}
			else if (mod.toLowerCase().equals("etfuturum"))
			{
				modblock = Block.getBlockFromName(ModObjects.grassPathEF);
				if (modblock != null) {return modblock;}
			}
		}
		return Blocks.gravel;
	}
	
	
	// Grindstone
	public static Object[] chooseModGrindstone(int orientation, int horizIndex)
	{
		Block modblock = Blocks.anvil;
		int meta = StructureVillageVN.chooseAnvilMeta(orientation, horizIndex);
		
		return new Object[]{modblock, meta};
	}
	
	
	// Iron Nugget
	public static ItemStack chooseModIronNugget()
	{
		String[] modprioritylist = GeneralConfig.modIronNugget;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("etfuturum"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.ironNuggetEF);
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
			if (mod.toLowerCase().equals("uptodate"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.ironNuggetUTD);
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
			else if (mod.toLowerCase().equals("tinkersconstruct"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.materialsTC);
				if (moditem != null) {return new ItemStack(moditem, 1, 19);}
			}
			else if (mod.toLowerCase().equals("thermalfoundation"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.materialsTF);
				if (moditem != null) {return new ItemStack(moditem, 1, 8);}
			}
			else if (mod.toLowerCase().equals("railcraft"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.nuggetRC);
				if (moditem != null) {return new ItemStack(moditem, 1, 0);}
			}
			else if (mod.toLowerCase().equals("mariculture"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.materialsMC);
				if (moditem != null) {return new ItemStack(moditem, 1, 33);}
			}
			else if (mod.toLowerCase().equals("netherlicious"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.nuggetNL);
				if (moditem != null) {return new ItemStack(moditem, 1, 0);}
			}
		}
		return null;
	}
	
	
	// Lantern
    public static Object[] chooseModLanternBlock(boolean isHanging)
    {
    	String[] modprioritylist = GeneralConfig.modLantern;
    	
		for (String mod : modprioritylist)
		{
			if (mod.toLowerCase().equals("uptodate"))
			{
				Block tryLantern = Block.getBlockFromName(ModObjects.lanternUTD);
		    	if (tryLantern!=null) {return new Object[]{tryLantern, isHanging? 1:0};} // 1 is hanging, 0 is sitting
			}
			if (mod.toLowerCase().equals("etfuturum"))
			{
				Block tryLantern = Block.getBlockFromName(ModObjects.lanternEF);
		    	if (tryLantern!=null) {return new Object[]{tryLantern, isHanging? 1:0};} // 1 is hanging, 0 is sitting
			}
			if (mod.toLowerCase().equals("netherlicious"))
			{
				Block tryLantern = Block.getBlockFromName(ModObjects.lanternNL);
		    	if (tryLantern!=null) {return new Object[]{tryLantern, isHanging? 1:0};} // 1 is hanging, 0 is sitting
			}
			if (mod.toLowerCase().equals("enviromine"))
			{
				Block tryLantern = Block.getBlockFromName(ModObjects.davyLampEM);
		    	if (tryLantern!=null) {return new Object[]{tryLantern, 1};} // 1 is lit
			}
		}
		
    	// None are found, so return ordinary glowstone
    	return new Object[]{Blocks.glowstone, 0};
    }
	public static ItemStack chooseModLanternItem()
	{
		String[] modprioritylist = GeneralConfig.modLantern;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.lanternUTD));
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
			else if (mod.toLowerCase().equals("enviromine"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.davyLampEM));
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
		}
		return null;
	}
	
	
	// Lectern
	
	// Array of meta values for furnaces indexed by [orientation][horizIndex]
	// 1: north-facing
	// 2: east-facing
	// 3: south-facing
	// 0: west-facing
	public static final int[][] BIBLIOCRAFT_DESK_META_ARRAY = new int[][]{
		{3,0,1,2}, // fore-facing (away from you)
		{2,3,2,3}, // right-facing
		{1,2,3,0}, // back-facing (toward you)
		{0,1,0,1}, // left-facing
	};
	/**
	 * orientation:
	 * 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
	 */
	public static int chooseBibliocraftDeskMeta(int orientation, int horizIndex)
	{
		return (StructureVillageVN.ANVIL_META_ARRAY[orientation][horizIndex]+2)%4;
	}
	
	public static void setModLecternBlock(World world, int x, int y, int z, int orientation, int horizIndex, int woodMeta)
	{
		Block modblock=null; int meta=0;
		boolean setTE = false; // Flagged as true if you need to set a tile entity
		
		modblock = Block.getBlockFromName(ModObjects.deskBC);
		
		if (modblock != null) {setTE = true;}
		else
		{
			Object modobject[] = ModObjects.chooseModBookshelf(woodMeta); modblock=(Block)modobject[0]; meta=(Integer)modobject[1];
		}
		
		world.setBlock(x, y, z, modblock, meta, 2);
		
		if (setTE)
		{
			world.setBlockMetadataWithNotify(x, y, z, woodMeta, 2);
			
			// Set the tile entity so that you can assign the orientation via NBT 
			NBTTagCompound nbtCompound = new NBTTagCompound();
        	TileEntity tileentity = world.getTileEntity(x, y, z);
        	tileentity.writeToNBT(nbtCompound);
        	nbtCompound.setInteger("deskAngle", chooseBibliocraftDeskMeta(orientation, horizIndex));
        	tileentity.readFromNBT(nbtCompound);
        	world.setTileEntity(x, y, z, tileentity);
		}
	}
	
	
	// Loom
	public static Object[] chooseModLoom(int woodMeta)
	{
		Block modblock = chooseModCraftingTable(woodMeta); int meta = 0;
		
		return new Object[]{modblock, meta};
	}
	
	
	// Ladder
	/**
	 * Tries to convert to different wood ladder types.
	 * On a failure, return default vanilla ladder.
	 */
	public static Block chooseModLadderBlock(int materialMeta)
	{
		Block modblock=null;
		
		switch (materialMeta)
		{
		case 0: modblock = Blocks.ladder; break;
		case 1: modblock = Block.getBlockFromName(ModObjects.ladderSpruceGS); break;
		case 2: modblock = Block.getBlockFromName(ModObjects.ladderBirchGS); break;
		case 3: modblock = Block.getBlockFromName(ModObjects.ladderJungleGS); break;
		case 4: modblock = Block.getBlockFromName(ModObjects.ladderAcaciaGS); break;
		case 5: modblock = Block.getBlockFromName(ModObjects.ladderDarkOakGS); break;
		}
		if (modblock != null) {return modblock;}
		else {return Blocks.ladder;}
	}
	
	
	// Sign
	public static ItemStack chooseModWoodenSignItem(int materialMeta)
	{
		Item moditem=null;
		
		String[] modprioritylist = GeneralConfig.modSign;
		
		for (String mod : modprioritylist)
		{
			moditem=null;
			
			if (mod.toLowerCase().equals("etfuturum"))
			{
				switch (materialMeta)
				{
					case 1: moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.signSpruceEF)); break;
					case 2: moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.signBirchEF)); break;
					case 3: moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.signJungleEF)); break;
					case 4: moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.signAcaciaEF)); break;
					case 5: moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.signDarkOakEF)); break;
				}
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				switch (materialMeta)
				{
					case 1: moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.signSpruceGS)); break;
					case 2: moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.signBirchGS)); break;
					case 3: moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.signJungleGS)); break;
					case 4: moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.signAcaciaGS)); break;
					case 5: moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.signDarkOakGS)); break;
				}
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
		}
		
		// If all else fails, grab the vanilla version
		return new ItemStack(Items.sign, 1);
	}
	
	public static Block chooseModWoodenSignBlock(int materialMeta, boolean standing)
	{
		if (materialMeta==0) {return standing? Blocks.standing_sign : Blocks.wall_sign;} // Oak is just vanilla default
		
		Block modblock=null;
		
		switch (materialMeta)
		{
			case 1: modblock = Block.getBlockFromName(standing? ModObjects.signSpruceEF : ModObjects.wallSignSpruceEF); break;
			case 2: modblock = Block.getBlockFromName(standing? ModObjects.signBirchEF : ModObjects.wallSignSpruceEF); break;
			case 3: modblock = Block.getBlockFromName(standing? ModObjects.signJungleEF : ModObjects.wallSignSpruceEF); break;
			case 4: modblock = Block.getBlockFromName(standing? ModObjects.signAcaciaEF : ModObjects.wallSignSpruceEF); break;
			case 5: modblock = Block.getBlockFromName(standing? ModObjects.signDarkOakEF : ModObjects.wallSignSpruceEF); break;
			
			// Gany's Surface uniquely uses boolean tile.isStanding in its tileentity which does not have a vanilla parallel.
			// GS standing signs won't work.
/*			case 1: modblock = Block.getBlockFromName(ModObjects.signSpruceGS); break;
			case 2: modblock = Block.getBlockFromName(ModObjects.signBirchGS); break;
			case 3: modblock = Block.getBlockFromName(ModObjects.signJungleGS); break;
			case 4: modblock = Block.getBlockFromName(ModObjects.signAcaciaGS); break;
			case 5: modblock = Block.getBlockFromName(ModObjects.signDarkOakGS); break;*/
		}
		if (modblock != null) {return modblock;}
		
		// If all else fails, grab the vanilla version
		return standing? Blocks.standing_sign : Blocks.wall_sign;
	}
	
	
	
	// Smithing Table
	public static Object[] chooseModSmithingTable(int woodMeta)
	{
		Block modblock = chooseModCraftingTable(woodMeta); int meta = 0;
		
		return new Object[]{modblock, meta};
	}
	
	
	// Smoker
	/**
	 * furnaceOrientation:
	 * 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
	 */
	public static Object[] chooseModSmokerBlock(int furnaceOrientation, int horizIndex)
	{
		int meta = StructureVillageVN.chooseFurnaceMeta(furnaceOrientation, horizIndex);
		
		Block modblock = Block.getBlockFromName(smoker_EF);
		if (modblock == null) {modblock = Blocks.furnace;}
		
		return new Object[]{modblock, meta};
	}
	
	
	// Red Sandstone
	public static Block chooseModRedSandstone()
	{
		String[] modprioritylist = GeneralConfig.modRedSandstone;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.redSandstoneUTD);
				if (modblock != null) {return modblock;}
			}
			if (mod.toLowerCase().equals("etfuturum"))
			{
				modblock = Block.getBlockFromName(ModObjects.redSandstoneEF);
				if (modblock != null) {return modblock;}
			}
			if (mod.toLowerCase().equals("ganyssurface"))
			{
				modblock = Block.getBlockFromName(ModObjects.redSandstoneGS);
				if (modblock != null) {return modblock;}
			}
		}
		
		return null;
	}
	
	
	// Red Sandstone Slab
	public static Object[] chooseModRedSandstoneSlab(boolean upper)
	{
		String[] modprioritylist = GeneralConfig.modRedSandstone;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.redSandstoneSlabUTD);
				if (modblock != null) {return new Object[]{modblock, upper?8:0};}
			}
			if (mod.toLowerCase().equals("etfuturum"))
			{
				modblock = Block.getBlockFromName(ModObjects.redSandstoneSlabEF);
				if (modblock != null) {return new Object[]{modblock, upper?1:0};}
			}
			if (mod.toLowerCase().equals("ganyssurface"))
			{
				modblock = Block.getBlockFromName(ModObjects.redSandstoneSlabGS);
				if (modblock != null) {return new Object[]{modblock, upper?1:0};}
			}
		}
		
		return null;
	}
	
	
	// Red Sandstone Double Slab
	public static Object[] chooseModRedSandstoneDoubleSlab()
	{
		String[] modprioritylist = GeneralConfig.modRedSandstone;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.redSandstoneSlabUTD);
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
			if (mod.toLowerCase().equals("etfuturum"))
			{
				modblock = Block.getBlockFromName(ModObjects.redSandstoneSlabEF);
				if (modblock != null) {return new Object[]{modblock, 2};}
			}
			if (mod.toLowerCase().equals("ganyssurface"))
			{
				modblock = Block.getBlockFromName(ModObjects.redSandstoneSlabGS);
				if (modblock != null) {return new Object[]{modblock, 2};}
			}
		}
		
		return null;
	}
	
	
	// Cut Red Sandstone Slab
	public static Object[] chooseModCutRedSandstoneSlab(boolean upper)
	{
		Block modblock = Block.getBlockFromName(ModObjects.cutRedSandstoneSlabUTD);
		if (modblock != null) {return new Object[]{modblock, upper?8:0};}
		
		return null;
	}
	
	
	// Red Sandstone Stairs
	public static Block chooseModRedSandstoneStairs()
	{
		String[] modprioritylist = GeneralConfig.modRedSandstone;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.redSandstoneStairsUTD);
				if (modblock != null) {return modblock;}
			}
			if (mod.toLowerCase().equals("etfuturum"))
			{
				modblock = Block.getBlockFromName(ModObjects.redSandstoneStairsEF);
				if (modblock != null) {return modblock;}
			}
			if (mod.toLowerCase().equals("ganyssurface"))
			{
				modblock = Block.getBlockFromName(ModObjects.redSandstoneStairsGS);
				if (modblock != null) {return modblock;}
			}
		}
		
		return null;
	}
	
	
	/**
	 * Returns non-smooth stair versions on failure; white sandstone stairs if red does not exist
	 */
	public static Block chooseModSmoothSandstoneStairs(boolean isRed)
	{
		Block modblock;
		
		if (isRed)
		{
			modblock = Block.getBlockFromName(ModObjects.smoothRedSandstoneStairsUTD);
			if (modblock != null) {return modblock;}
			else
			{
				modblock = chooseModRedSandstoneStairs();
				if (modblock != null) {return modblock;}
				else {return Blocks.sandstone_stairs;}
			}
		}
		else
		{
			modblock = Block.getBlockFromName(ModObjects.smoothSandstoneStairsUTD);
			if (modblock != null) {return modblock;}
			else {return Blocks.sandstone_stairs;}
		}
	}
	
	
	// Sandstone Wall
	public static Object[] chooseModSandstoneWall(boolean isRed)
	{
		String[] modprioritylist = GeneralConfig.modWall;
		
		Block modblock=null;
		
		if (isRed)
		{
			for (String mod : modprioritylist)
			{
				if (mod.toLowerCase().equals("uptodate"))
				{
					modblock = Block.getBlockFromName(ModObjects.redSandstoneWallUTD);
					if (modblock != null) {return new Object[]{modblock, 0};}
				}
			}
		}
		else
		{
			for (String mod : modprioritylist)
			{
				if (mod.toLowerCase().equals("uptodate"))
				{
					modblock = Block.getBlockFromName(ModObjects.sandstoneWallUTD);
					if (modblock != null) {return new Object[]{modblock, 0};}
				}
				if (mod.toLowerCase().equals("railcraft"))
				{
					modblock = Block.getBlockFromName(ModObjects.wallRC);
					if (modblock != null) {return new Object[]{modblock, 11};}
				}
			}
		}
		
		return null;
	}
	
	
	// Cut Sandstone Slab
	public static Object[] chooseModCutSandstoneSlab(boolean upper)
	{
		Block modblock = Block.getBlockFromName(ModObjects.cutSandstoneSlabUTD);
		if (modblock != null) {return new Object[]{modblock, upper?8:0};}
		
		return null;
	}
	
	
	// Smooth Sandstone
	public static Object[] chooseModSmoothSandstoneBlock(boolean isRed)
	{
		String[] modprioritylist = GeneralConfig.modSmoothSandstone;
		
		Block modblock=null;
		
		for (String mod : modprioritylist)
		{
			if (mod.toLowerCase().equals("etfuturum"))
			{
				modblock = Block.getBlockFromName(isRed ? ModObjects.smoothRedSandstoneEF : ModObjects.smoothSandstoneEF);
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
			if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.smoothSandstoneUTD);
				if (modblock != null) {return new Object[]{modblock, isRed?3:0};}
			}
		}
		
		// Return vanilla white sandstone with cap on all sides
		return new Object[]{Blocks.double_stone_slab, 9};
	}
	
	
	// Smooth Sandstone Slab
	/**
	 * Returns regular sandstone slab on a failure
	 */
	public static Object[] chooseModSmoothSandstoneSlab(boolean upper, boolean isred)
	{
		Block modblock;
		
		if (isred)
		{
			modblock = Block.getBlockFromName(ModObjects.smoothRedSandstoneSlabUTD);
			if (modblock != null)
			{
				return new Object[]{modblock, upper?8:0};
			}
			else
			{
				Object[] modobject = chooseModRedSandstoneSlab(upper);
				if (modobject != null)
				{
					return modobject;
				}
				else
				{
					return new Object[]{Blocks.stone_slab, upper?9:1};
				}
			}
		}
		else
		{
			modblock = Block.getBlockFromName(ModObjects.smoothSandstoneSlabUTD);
			if (modblock != null)
			{
				return new Object[]{modblock, upper?8:0};
			}
			else
			{
				return new Object[]{Blocks.stone_slab, upper?9:1};
			}
		}
	}
	
	
	// Smooth Stone
	public static Object[] chooseModSmoothStoneBlock()
	{
		String[] modprioritylist = GeneralConfig.modSmoothStone;
		
		Block modblock=null;
		
		for (String mod : modprioritylist)
		{
			if (mod.toLowerCase().equals("etfuturum"))
			{
				modblock = Block.getBlockFromName(ModObjects.smoothStoneEF);
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
			if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.smoothStoneUTD);
				if (modblock != null) {return new Object[]{modblock, 0};}
			}
		}
		
		// Return vanilla polished stone slab with top face on all sides
		return new Object[]{Blocks.double_stone_slab, 8};
	}
	
	
	// Stripped log
	/**
	 * Materials are: 0=oak, 1=spruce, 2=birch, 3=jungle, 4=acacia, 5=darkoak
	 * Orientations are: 0=vertical, 1=east-west, 2=north-south
	 */
	public static Object[] chooseModStrippedLog(int materialMeta, int orientation)
	{
		String[] modprioritylist = GeneralConfig.modStrippedLog;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("etfuturum"))
			{
				int addMeta=0;
				
				switch (materialMeta)
				{
				case 0: modblock = Block.getBlockFromName(ModObjects.strippedLog1EF); addMeta=0; break;
				case 1: modblock = Block.getBlockFromName(ModObjects.strippedLog1EF); addMeta=1; break;
				case 2: modblock = Block.getBlockFromName(ModObjects.strippedLog1EF); addMeta=2; break;
				case 3: modblock = Block.getBlockFromName(ModObjects.strippedLog1EF); addMeta=3; break;
				case 4: modblock = Block.getBlockFromName(ModObjects.strippedLog2EF); addMeta=0; break;
				case 5: modblock = Block.getBlockFromName(ModObjects.strippedLog2EF); addMeta=1; break;
				}
				if (modblock != null) {return new Object[]{modblock, orientation*4+addMeta};}
			}
			else if (mod.toLowerCase().equals("uptodate"))
			{
				switch (materialMeta)
				{
				case 0: modblock = Block.getBlockFromName(ModObjects.strippedLogOakUTD); break;
				case 1: modblock = Block.getBlockFromName(ModObjects.strippedLogSpruceUTD); break;
				case 2: modblock = Block.getBlockFromName(ModObjects.strippedLogBirchUTD); break;
				case 3: modblock = Block.getBlockFromName(ModObjects.strippedLogJungleUTD); break;
				case 4: modblock = Block.getBlockFromName(ModObjects.strippedLogAcaciaUTD); break;
				case 5: modblock = Block.getBlockFromName(ModObjects.strippedLogDarkOakUTD); break;
				}
				if (modblock != null) {return new Object[]{modblock, orientation*4};}
			}
		}
		// If all else fails, grab the vanilla version
		return new Object[]{materialMeta <4 ? Blocks.log : Blocks.log2, orientation*4+materialMeta%4};
	}
	
	public static Object[] chooseModStrippedWood(int materialMeta)
	{
		String[] modprioritylist = GeneralConfig.modStrippedLog;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("etfuturum"))
			{
				int meta=0;
				
				switch (materialMeta)
				{
					case 0: modblock = Block.getBlockFromName(ModObjects.strippedWood1EF); meta=0; break;
					case 1: modblock = Block.getBlockFromName(ModObjects.strippedWood1EF); meta=1; break;
					case 2: modblock = Block.getBlockFromName(ModObjects.strippedWood1EF); meta=2; break;
					case 3: modblock = Block.getBlockFromName(ModObjects.strippedWood1EF); meta=3; break;
					case 4: modblock = Block.getBlockFromName(ModObjects.strippedWood2EF); meta=0; break;
					case 5: modblock = Block.getBlockFromName(ModObjects.strippedWood2EF); meta=1; break;
				}
				if (modblock != null) {return new Object[]{modblock, meta};}
			}
			else if (mod.toLowerCase().equals("uptodate"))
			{
				switch (materialMeta)
				{
					case 0: modblock = Block.getBlockFromName(ModObjects.strippedLogOakUTD); break;
					case 1: modblock = Block.getBlockFromName(ModObjects.strippedLogSpruceUTD); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.strippedLogBirchUTD); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.strippedLogJungleUTD); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.strippedLogAcaciaUTD); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.strippedLogDarkOakUTD); break;
				}
				if (modblock != null) {return new Object[]{modblock, 12};}
			}
		}
		
		// On a failure, return vanilla log with bark on all sides
		return new Object[]{materialMeta<4 ? Blocks.log : Blocks.log2, 12+materialMeta%4};
	}

	// Stonecutter
	/**
	 * Orientation:
	 * 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
	 */
	public static Object[] chooseModStonecutter(int orientation, int woodMeta)
	{
		Block modblock = chooseModCraftingTable(woodMeta); int meta = 0;
		
		return new Object[]{modblock, meta};
	}
	
	// Trap door
	public static Block chooseModWoodenTrapdoor(int materialMeta)
	{
		if (materialMeta==0) {return Blocks.trapdoor;}
		
		String[] modprioritylist = GeneralConfig.modWoodenTrapdoor;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				switch (materialMeta)
				{
					case 1: modblock = Block.getBlockFromName(ModObjects.trapdoorSpruceUTD); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.trapdoorBirchUTD); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.trapdoorJungleUTD); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.trapdoorAcaciaUTD); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.trapdoorDarkOakUTD); break;
				}
				if (modblock != null) {return modblock;}
			}
			else if (mod.toLowerCase().equals("etfuturum"))
			{
				switch (materialMeta)
				{
					case 1: modblock = Block.getBlockFromName(ModObjects.trapdoorSpruceEF); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.trapdoorBirchEF); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.trapdoorJungleEF); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.trapdoorAcaciaEF); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.trapdoorDarkOakEF); break;
				}
				if (modblock != null) {return modblock;}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				switch (materialMeta)
				{
					case 1: modblock = Block.getBlockFromName(ModObjects.trapdoorSpruceGS); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.trapdoorBirchGS); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.trapdoorJungleGS); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.trapdoorAcaciaGS); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.trapdoorDarkOakGS); break;
				}
				if (modblock != null) {return modblock;}
			}
			/*else if (mod.toLowerCase().equals("malisisdoors"))
			{
				switch (materialMeta)
				{
					case 1: modblock = Block.getBlockFromName(ModObjects.trapdoorSpruceMD); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.trapdoorBirchMD); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.trapdoorJungleMD); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.trapdoorAcaciaMD); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.trapdoorDarkOakMD); break;
				}
				if (modblock != null) {return modblock;}
			}*/
		}
		// If all else fails, grab the vanilla version
		return Blocks.trapdoor;
	}
	
	// Kelp
	public static ItemStack chooseModKelpBlock()
	{
		String[] modprioritylist = GeneralConfig.modKelp;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("mariculture") & VillageNames.canVillagerTradesDistinguishMeta)
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.kelpWrapMC);
				if (moditem != null) {return new ItemStack(moditem, 1, 8);}
			}
			else if (mod.toLowerCase().equals("biomesoplenty") & VillageNames.canVillagerTradesDistinguishMeta)
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.kelpDriedBOP);
				if (moditem != null) {return new ItemStack(moditem, 9, 11);} // Use nine because BoP doesn't have a way to consolidate them
			}
		}
		return null;
	}
	
	// Raw Mutton
	public static ItemStack chooseModRawMutton()
	{
		String[] modprioritylist = GeneralConfig.modMutton;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.muttonRawUTD);
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
			else if (mod.toLowerCase().equals("etfuturum"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.muttonRawEF);
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.muttonRawGS);
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
			else if (mod.toLowerCase().equals("harvestcraft"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.muttonRawHC);
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
		}
		return null;
	}
	
	// Cooked Mutton
	public static ItemStack chooseModCookedMutton()
	{
		String[] modprioritylist = GeneralConfig.modMutton;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.muttonCookedUTD);
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
			else if (mod.toLowerCase().equals("etfuturum"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.muttonCookedEF);
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.muttonCookedGS);
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
			else if (mod.toLowerCase().equals("harvestcraft"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.muttonCookedHC);
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
		}
		return null;
	}

	// Sweet Berries
	public static ItemStack chooseModSweetBerriesItem()
	{
		String[] modprioritylist = GeneralConfig.modSweetBerries;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("etfuturum"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.sweetBerriesEF);
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
			else if (mod.toLowerCase().equals("uptodate"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.sweetBerriesUTD);
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
		}
		return null;
	}
	
	// Raw Rabbit
	
	// Cooked Rabbit
	
	// Rabbit Stew
	
	// Rabbit Hide
	
	// Rabbit Foot
	
	// Suspicious Stew
	
	
	// Wooden Pressure Plate
	public static Block chooseModPressurePlate(int materialMeta)
	{
		String[] modprioritylist = GeneralConfig.modPressurePlate;
		
		for (String mod : modprioritylist)
		{
			Block modblock=null;
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				switch (materialMeta)
				{
					case 1: modblock = Block.getBlockFromName(ModObjects.pressurePlateSpruceUTD); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.pressurePlateBirchUTD); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.pressurePlateJungleUTD); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.pressurePlateAcaciaUTD); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.pressurePlateDarkOakUTD); break;
				}
				if (modblock != null) {return modblock;}
			}
			else if (mod.toLowerCase().equals("etfuturum"))
			{
				switch (materialMeta)
				{
					case 1: modblock = Block.getBlockFromName(ModObjects.pressurePlateSpruceEF); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.pressurePlateBirchEF); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.pressurePlateJungleEF); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.pressurePlateAcaciaEF); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.pressurePlateDarkOakEF); break;
				}
				if (modblock != null) {return modblock;}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				switch (materialMeta)
				{
					case 1: modblock = Block.getBlockFromName(ModObjects.pressurePlateSpruceGS); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.pressurePlateBirchGS); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.pressurePlateJungleGS); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.pressurePlateAcaciaGS); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.pressurePlateDarkOakGS); break;
				}
				if (modblock != null) {return modblock;}
			}
			else if (mod.toLowerCase().equals("woodstuff"))
			{
				switch (materialMeta)
				{
					case 0: modblock = Block.getBlockFromName(ModObjects.pressurePlateOakWS); break;
					case 1: modblock = Block.getBlockFromName(ModObjects.pressurePlateSpruceWS); break;
					case 2: modblock = Block.getBlockFromName(ModObjects.pressurePlateBirchWS); break;
					case 3: modblock = Block.getBlockFromName(ModObjects.pressurePlateJungleWS); break;
					case 4: modblock = Block.getBlockFromName(ModObjects.pressurePlateAcaciaWS); break;
					case 5: modblock = Block.getBlockFromName(ModObjects.pressurePlateDarkOakWS); break;
				}
				if (modblock != null) {return modblock;}
			}
		}
		// If all else fails, grab the vanilla version
		return Blocks.wooden_pressure_plate;
	}
	
	// Wood block (has bark on all surfaces)
	public static Object[] chooseModWoodBlock(Block block, int meta)
	{
		// Pass the original block if it's not a vanilla log
		if (block!=Blocks.log && block!=Blocks.log2) {return new Object[]{block, meta};}
		
		// Specifically UpToDate stuff
		Block logBlock = Block.getBlockFromName(ModObjects.woodBlockUTD);
		
		if (logBlock!=null) // Use the UTD block
		{
			return new Object[]{logBlock, meta+(block==Blocks.log2?4:0)};
		}
		else // Use vanilla wood with bark on all sides
		{
			return chooseModBarkBlock(block, meta);
		}
	}
	
	
}
