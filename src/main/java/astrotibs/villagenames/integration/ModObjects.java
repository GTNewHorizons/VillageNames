package astrotibs.villagenames.integration;

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
// Added in v3.1trades
public class ModObjects {
	
    
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
	public static final String bannerEF = "etfuturum:banner";
	public static final String bannerGS = "ganyssurface:banner";
	
	// Bark
	public static final String barkEF = "etfuturum:bark";
	public static final String bark2EF = "etfuturum:bark2";
	
	// Barrel
	public static final String barrelUTD = "uptodate:barrel";
	
	// Bountiful rocks
	public static final String andesiteC2 = "chisel:andesite";
	public static final String dioriteC2 = "chisel:diorite";
	public static final String graniteC2 = "chisel:granite";
	public static final String stoneBo = "Botania:stone";
	public static final String stoneEF = "etfuturum:stone";
	public static final String stoneGS = "ganyssurface:18Stones";
	public static final String stoneUTD = "uptodate:stone";

	// Campfire
	public static final String campfirebackport = "campfirebackport:campfire";
	// Colored Bed
	public static final String coloredBedBlockBV = "bettervanilla:bettervanilla_colored_bed_block";
	
	// Concrete
	public static final String concreteUTD = "uptodate:concrete";
	public static final String concreteWhiteEF = "etfuturum:concrete_white";
	public static final String concreteOrangeEF = "etfuturum:concrete_orange";
	public static final String concreteMagentaEF = "etfuturum:concrete_magenta";
	public static final String concreteLightBlueEF = "etfuturum:concrete_lightBlue";
	public static final String concreteYellowEF = "etfuturum:concrete_yellow";
	public static final String concreteLimeEF = "etfuturum:concrete_lime";
	public static final String concretePinkEF = "etfuturum:concrete_pink";
	public static final String concreteGrayEF = "etfuturum:concrete_gray";
	public static final String concreteSilverEF = "etfuturum:concrete_silver";
	public static final String concreteCyanEF = "etfuturum:concrete_cyan";
	public static final String concretePurpleEF = "etfuturum:concrete_purple";
	public static final String concreteBlueEF = "etfuturum:concrete_blue";
	public static final String concreteBrownEF = "etfuturum:concrete_brown";
	public static final String concreteGreenEF = "etfuturum:concrete_green";
	public static final String concreteRedEF = "etfuturum:concrete_red";
	public static final String concreteBlackEF = "etfuturum:concrete_black";
	
	// Door
	public static final String doorSpruceGS = "ganyssurface:doorSpruce";
	public static final String doorBirchGS = "ganyssurface:doorBirch";
	public static final String doorJungleGS = "ganyssurface:doorJungle";
	public static final String doorAcaciaGS = "ganyssurface:doorAcacia";
	public static final String doorDarkOakGS = "ganyssurface:doorDark_oak"; // lol wtf Ganymedes
	public static final String doorSpruceEF = "etfuturum:door_spruce";
	public static final String doorBirchEF = "etfuturum:door_birch";
	public static final String doorJungleEF = "etfuturum:door_jungle";
	public static final String doorAcaciaEF = "etfuturum:door_acacia";
	public static final String doorDarkOakEF = "etfuturum:door_dark_oak";
	public static final String doorSpruceUTD = "uptodate:door_spruce";
	public static final String doorBirchUTD = "uptodate:door_birch";
	public static final String doorJungleUTD = "uptodate:door_jungle";
	public static final String doorAcaciaUTD = "uptodate:door_acacia";
	public static final String doorDarkOakUTD = "uptodate:door_dark_oak";
	
	// Fence
	public static final String fenceOakGS = "ganyssurface:fence_0";
	public static final String fenceSpruceGS = "ganyssurface:fence_1";
	public static final String fenceBirchGS = "ganyssurface:fence_2";
	public static final String fenceJungleGS = "ganyssurface:fence_3";
	public static final String fenceAcaciaGS = "ganyssurface:fence_4";
	public static final String fenceDarkOakGS = "ganyssurface:fence_5";
	public static final String fenceOakEF = "etfuturum:fence_oak";
	public static final String fenceSpruceEF = "etfuturum:fence_spruce";
	public static final String fenceBirchEF = "etfuturum:fence_birch";
	public static final String fenceJungleEF = "etfuturum:fence_jungle";
	public static final String fenceAcaciaEF = "etfuturum:fence_acacia";
	public static final String fenceDarkOakEF = "etfuturum:fence_dark_oak";
	public static final String fenceSpruceUTD = "uptodate:fence_spruce";
	public static final String fenceBirchUTD = "uptodate:fence_birch";
	public static final String fenceJungleUTD = "uptodate:fence_jungle";
	public static final String fenceAcaciaUTD = "uptodate:fence_acacia";
	public static final String fenceDarkOakUTD = "uptodate:fence_dark_oak";
	
	// Fence Gate
	public static final String fenceGateOakGS = "ganyssurface:fence_gate_0";
	public static final String fenceGateSpruceGS = "ganyssurface:fence_gate_1";
	public static final String fenceGateBirchGS = "ganyssurface:fence_gate_2";
	public static final String fenceGateJungleGS = "ganyssurface:fence_gate_3";
	public static final String fenceGateAcaciaGS = "ganyssurface:fence_gate_4";
	public static final String fenceGateDarkOakGS = "ganyssurface:fence_gate_5";
	public static final String fenceGateSpruceEF = "etfuturum:fence_gate_spruce";
	public static final String fenceGateBirchEF = "etfuturum:fence_gate_birch";
	public static final String fenceGateJungleEF = "etfuturum:fence_gate_jungle";
	public static final String fenceGateAcaciaEF = "etfuturum:fence_gate_acacia";
	public static final String fenceGateDarkOakEF = "etfuturum:fence_gate_dark_oak";
	public static final String fenceGateSpruceUTD = "uptodate:fence_gate_spruce";
	public static final String fenceGateBirchUTD = "uptodate:fence_gate_birch";
	public static final String fenceGateJungleUTD = "uptodate:fence_gate_jungle";
	public static final String fenceGateAcaciaUTD = "uptodate:fence_gate_acacia";
	public static final String fenceGateDarkOakUTD = "uptodate:fence_gate_dark_oak";
	
	// Glazed Terracotta
	public static final String glazedTerracottaWhiteUTD = "uptodate:glazed_terracotta_white";
	public static final String glazedTerracottaOrangeUTD = "uptodate:glazed_terracotta_orange";
	public static final String glazedTerracottaMagentaUTD = "uptodate:glazed_terracotta_magenta";
	public static final String glazedTerracottaLightBlueUTD = "uptodate:glazed_terracotta_light_blue";
	public static final String glazedTerracottaYellowUTD = "uptodate:glazed_terracotta_yellow";
	public static final String glazedTerracottaLimeUTD = "uptodate:glazed_terracotta_lime";
	public static final String glazedTerracottaPinkUTD = "uptodate:glazed_terracotta_pink";
	public static final String glazedTerracottaGrayUTD = "uptodate:glazed_terracotta_gray";
	public static final String glazedTerracottaLightGrayUTD = "uptodate:glazed_terracotta_light_gray";
	public static final String glazedTerracottaCyanUTD = "uptodate:glazed_terracotta_cyan";
	public static final String glazedTerracottaPurpleUTD = "uptodate:glazed_terracotta_purple";
	public static final String glazedTerracottaBlueUTD = "uptodate:glazed_terracotta_blue";
	public static final String glazedTerracottaBrownUTD = "uptodate:glazed_terracotta_brown";
	public static final String glazedTerracottaGreenUTD = "uptodate:glazed_terracotta_green";
	public static final String glazedTerracottaRedUTD = "uptodate:glazed_terracotta_red";
	public static final String glazedTerracottaBlackUTD = "uptodate:glazed_terracotta_black";
	
	// Grass Path
	public static final String grassPathUTD = "uptodate:grass_path";
	public static final String grassPathEF = "etfuturum:grass_path";
	
	// Iron Nuggets
	// Mariculture nugget is Mariculture:materials:33
	public static final String nuggetRC = "Railcraft:nugget"; // Iron Nugget is 0
	public static final String materialsTC = "TConstruct:materials"; // Iron Nugget is 19 
	public static final String materialsTF = "ThermalFoundation:material"; // Iron Nugget is 8
	public static final String ironNuggetUTD = "uptodate:iron_nugget";
	
	// Lanterns / Lamps
	public static final String davyLampEM = "enviromine:davy_lamp";
	public static final String lanternUTD = "uptodate:lantern";
	
	// Mossy Cobblestone Stairs
	public static final String mossyCobblestoneStairsUTD = "uptodate:stairs_mossy_cobblestone";
	
	// Smooth Stone
	public static final String smoothStoneUTD = "uptodate:smooth_stone";
	
	// Walls
	public static final String sandstoneWallUTD = "uptodate:wall_sandstone";
	public static final String brickWallUTD = "uptodate:wall_bricks";
	public static final String stonebrickWallUTD = "uptodate:wall_stone_bricks";
	public static final String mossystonebrickWallUTD = "uptodate:wall_mossy_stone_bricks";

	// Wooden Pressure Plate
	public static final String pressurePlateSpruceGS = "ganyssurface:pressure_plate_spruce";
	public static final String pressurePlateBirchGS = "ganyssurface:pressure_plate_birch";
	public static final String pressurePlateJungleGS = "ganyssurface:pressure_plate_jungle";
	public static final String pressurePlateAcaciaGS = "ganyssurface:pressure_plate_acacia";
	public static final String pressurePlateDarkOakGS = "ganyssurface:pressure_plate_dark_oak";
	
	public static final String pressurePlateSpruceEF = "etfuturum:pressure_plate_spruce";
	public static final String pressurePlateBirchEF = "etfuturum:pressure_plate_birch";
	public static final String pressurePlateJungleEF = "etfuturum:pressure_plate_jungle";
	public static final String pressurePlateAcaciaEF = "etfuturum:pressure_plate_acacia";
	public static final String pressurePlateDarkOakEF = "etfuturum:pressure_plate_dark_oak";
	
	public static final String pressurePlateSpruceUTD = "uptodate:pressurePlate1";
	public static final String pressurePlateBirchUTD = "uptodate:pressurePlate2";
	public static final String pressurePlateJungleUTD = "uptodate:pressurePlate3";
	public static final String pressurePlateAcaciaUTD = "uptodate:pressurePlate4";
	public static final String pressurePlateDarkOakUTD = "uptodate:pressurePlate5";
	
	// Wooden Sign
	public static final String signSpruceGS = "ganyssurface:sign1";
	public static final String signBirchGS = "ganyssurface:sign2";
	public static final String signJungleGS = "ganyssurface:sign3";
	public static final String signAcaciaGS = "ganyssurface:sign4";
	public static final String signDarkOakGS = "ganyssurface:sign5";
	
	// Wooden Trapdoor
	public static final String trapdoorSpruceGS = "ganyssurface:trapdoor1";
	public static final String trapdoorBirchGS = "ganyssurface:trapdoor2";
	public static final String trapdoorJungleGS = "ganyssurface:trapdoor3";
	public static final String trapdoorAcaciaGS = "ganyssurface:trapdoor4";
	public static final String trapdoorDarkOakGS = "ganyssurface:trapdoor5";
	public static final String trapdoorSpruceUTD = "uptodate:trap_door_spruce";
	public static final String trapdoorBirchUTD = "uptodate:trap_door_birch";
	public static final String trapdoorJungleUTD = "uptodate:trap_door_jungle";
	public static final String trapdoorAcaciaUTD = "uptodate:trap_door_acacia";
	public static final String trapdoorDarkOakUTD = "uptodate:trap_door_dark_oak";
	
	
	
	
	// --- Items --- //
	
	
	// Beetroot
	public static final String beetrootEF = "etfuturum:beetroot";
	public static final String beetrootGS = "ganyssurface:beetroot";
	
	// Beetroot Seeds
	public static final String beetrootSeedsEF = "etfuturum:beetroot_seeds";
	public static final String beetrootSeedsGS = "ganyssurface:beetrootSeeds";

	// Beetroot Soup
	public static final String beetrootSoupEF = "etfuturum:beetroot_soup";
	public static final String beetrootSoupGS = "ganyssurface:beetrootSoup";
	
	// Boats
	public static final String boatBirchUTD = "uptodate:item_boat_birch";
	public static final String boatSpruceUTD = "uptodate:item_boat_spruce";
	public static final String boatDarkOakUTD = "uptodate:item_boat_dark_oak";
	public static final String boatJungleUTD = "uptodate:item_boat_jungle";
	public static final String boatAcaciaUTD = "uptodate:item_boat_acacia";
	
	// Colored beds
	public static final String coloredBedItemBV = "bettervanilla:bettervanilla_colored_bed";
	public static final String bedCB = "CarpentersBlocks:itemCarpentersBed";
	
	// Dyes
	public static final String miscBOP = "BiomesOPlenty:misc"; // Usually used for dyes
	public static final String materialsMC = "Mariculture:materials"; // Usually used for dyes
	public static final String dyeUTD = "uptodate:dye";
	
	// Flowers
	public static final String flowerUTD = "uptodate:flower";
	
	// Kelp and Kelp Accessories
	public static final String kelpDriedMC = "Mariculture:plant_static"; // Use meta 1
	public static final String kelpWrapMC = "Mariculture:food"; // Use meta 8
	public static final String kelpDriedBOP = "BiomesOPlenty:coral1"; // Use meta 11 
	
	// Mutton
	public static final String muttonRawEF = "etfuturum:mutton_raw";
	public static final String muttonCookedEF = "etfuturum:mutton_cooked";
	public static final String muttonRawGS = "ganyssurface:mutton_raw";
	public static final String muttonCookedGS = "ganyssurface:mutton_cooked";
	public static final String muttonRawHC = "harvestcraft:muttonrawItem";
	public static final String muttonCookedHC = "harvestcraft:muttoncookedItem";
	public static final String muttonRawUTD = "uptodate:raw_mutton";
	public static final String muttonCookedUTD = "uptodate:cooked_mutton";
	
	// Rabbit
	public static final String rabbitHideEF = "etfuturum:rabbit_hide";
	public static final String rabbitFootEF = "etfuturum:rabbit_foot";
	public static final String rabbitRawEF = "etfuturum:rabbit_raw";
	public static final String rabbitCookedEF = "etfuturum:rabbit_cooked";
	public static final String rabbitStewEF = "etfuturum:rabbit_stew";
	
	// Stripped log
	public static final String strippedLog1EF = "etfuturum:log_stripped";
	public static final String strippedLog2EF = "etfuturum:log2_stripped";
	public static final String strippedLogOakUTD = "uptodate:stripped_log_oak";
	public static final String strippedLogSpruceUTD = "uptodate:stripped_log_spruce";
	public static final String strippedLogBirchUTD = "uptodate:stripped_log_birch";
	public static final String strippedLogJungleUTD = "uptodate:stripped_log_jungle";
	public static final String strippedLogAcaciaUTD = "uptodate:stripped_log_acacia";
	public static final String strippedLogDarkOakUTD = "uptodate:stripped_log_dark_oak";
	// stripped wood for UTD is just log with meta value of 12
	
	// Suspicious Stew
	public static final String suspiciousStewUTD = "uptodate:suspicious_stew";

	// Sweet Berries
	public static final String sweetBerriesUTD = "uptodate:sweet_berries";
	
	// Tipped arrows
	public static final String tippedArrowEF = "etfuturum:tipped_arrow";
	
	
	
	// --------------------------- //
	// --- Generator Functions --- //
	// --------------------------- //
	
	// Barrel
	public static ItemStack chooseModSweetBerriesItem()
	{
		Item moditem=null;
		
		moditem = FunctionsVN.getItemFromName(ModObjects.sweetBerriesUTD);
		if (moditem != null) {return new ItemStack(moditem, 1);}
		
		return null;
	}
	
	// Barrel
	public static ItemStack chooseModBarrelItem()
	{
		Block moditem=null;
		
		moditem = Block.getBlockFromName(ModObjects.barrelUTD);
		if (moditem != null) {return new ItemStack(moditem);}
		
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
		}
		else if (block==Blocks.log2)
		{
			Block tryBark = Block.getBlockFromName(ModObjects.bark2EF);
			
			if (tryBark != null) // EF bark exists
			{
				return new Object[]{tryBark, meta%4};
			}
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
	public static ItemStack chooseModBeetroot()
	{
		String[] modprioritylist = GeneralConfig.modBeetroot;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
			if (mod.toLowerCase().equals("etfuturum"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.beetrootEF);
				if (moditem != null) {return new ItemStack(moditem, 1);}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.beetrootGS);
				if (moditem != null) {return new ItemStack(moditem, 1);}
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
		Block modblock = Blocks.furnace;
		int meta = StructureVillageVN.chooseFurnaceMeta(furnaceOrientation, horizIndex);
		
		return new Object[]{modblock, meta};
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
			return new Object[]{tryCampfire, StructureVillageVN.getSignRotationMeta(relativeOrientation, coordBaseMode, false)};
		}
		
		return new Object[]{Blocks.torch, 0};
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
			
			if (mod.toLowerCase().equals("uptodate"))
			{
				modblock = Block.getBlockFromName(ModObjects.concreteUTD);
				if (modblock != null) {return new Object[]{modblock, new Integer(meta)};}
			}
			else if (mod.toLowerCase().equals("etfuturum"))
			{
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
	public static Block chooseModFence(int materialMeta)
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
				if (modblock != null) {return modblock;}
			}
			if (mod.toLowerCase().equals("etfuturum"))
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
				if (modblock != null) {return modblock;}
			}
			if (mod.toLowerCase().equals("ganyssurface"))
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
				if (modblock != null) {return modblock;}
			}
		}
		// If all else fails, grab the vanilla version
		return Blocks.fence;
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
			if (mod.toLowerCase().equals("etfuturum"))
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
			if (mod.toLowerCase().equals("ganyssurface"))
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
		}
		// If all else fails, grab the vanilla version
		return Blocks.fence_gate;
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
	
	// Iron Nugget
	public static ItemStack chooseModIronNugget()
	{
		String[] modprioritylist = GeneralConfig.modIronNugget;
		
		for (String mod : modprioritylist)
		{
			Item moditem=null;
			
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
	
	
	
	// Andesite
	public static ItemStack chooseModAndesite()
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
			else if (mod.toLowerCase().equals("uptodate"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneUTD));
				if (moditem != null) {return new ItemStack(moditem, 1, 5);}
			}
			else if (mod.toLowerCase().equals("etfuturum"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneEF));
				if (moditem != null) {return new ItemStack(moditem, 1, 5);}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneGS));
				if (moditem != null) {return new ItemStack(moditem, 1, 5);}
			}
			else if (mod.toLowerCase().equals("botania"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneBo));
				if (moditem != null) {return new ItemStack(moditem, 1, 0);}
			}
		}
		return null;
	}
	
	public static ItemStack chooseModPolishedAndesite()
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
	
	// Diorite
	public static ItemStack chooseModDiorite()
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
			else if (mod.toLowerCase().equals("uptodate"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneUTD));
				if (moditem != null) {return new ItemStack(moditem, 1, 3);}
			}
			else if (mod.toLowerCase().equals("etfuturum"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneEF));
				if (moditem != null) {return new ItemStack(moditem, 1, 3);}
			}
			else if (mod.toLowerCase().equals("ganyssurface"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneGS));
				if (moditem != null) {return new ItemStack(moditem, 1, 3);}
			}
			else if (mod.toLowerCase().equals("botania"))
			{
				moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.stoneBo));
				if (moditem != null) {return new ItemStack(moditem, 1, 2);}
			}
		}
		return null;
	}
	
	public static ItemStack chooseModPolishedDiorite()
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
	
	// Granite
	public static ItemStack chooseModGranite()
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
	
	public static ItemStack chooseModPolishedGranite()
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
	
	
	// Sign
	public static ItemStack chooseModWoodenSignItem(int materialMeta)
	{
		Item moditem=null;

		switch (materialMeta)
		{
			case 1: moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.signSpruceGS)); break;
			case 2: moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.signBirchGS)); break;
			case 3: moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.signJungleGS)); break;
			case 4: moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.signAcaciaGS)); break;
			case 5: moditem = Item.getItemFromBlock(Block.getBlockFromName(ModObjects.signDarkOakGS)); break;
		}
		if (moditem != null) {return new ItemStack(moditem, 1);}
		
		// If all else fails, grab the vanilla version
		return new ItemStack(Items.sign, 1);
	}
	/*
	public static Block chooseModWoodenSign(int materialMeta, boolean standing)
	{
		if (materialMeta==0) {return standing? Blocks.standing_sign : Blocks.wall_sign;}
		
		Block modblock=null;
		
		switch (materialMeta)
		{
			case 1: modblock = Block.getBlockFromName(ModObjects.signSpruceGS); break;
			case 2: modblock = Block.getBlockFromName(ModObjects.signBirchGS); break;
			case 3: modblock = Block.getBlockFromName(ModObjects.signJungleGS); break;
			case 4: modblock = Block.getBlockFromName(ModObjects.signAcaciaGS); break;
			case 5: modblock = Block.getBlockFromName(ModObjects.signDarkOakGS); break;
		}
		if (modblock != null) {return modblock;}
		
		// If all else fails, grab the vanilla version
		return standing? Blocks.standing_sign : Blocks.wall_sign;
	}
	*/
	
	
	// Smoker
	/**
	 * furnaceOrientation:
	 * 0=fore-facing (away from you); 1=right-facing; 2=back-facing (toward you); 3=left-facing
	 */
	public static Object[] chooseModSmokerBlock(int furnaceOrientation, int horizIndex)
	{
		Block modblock = Blocks.furnace;
		int meta = StructureVillageVN.chooseFurnaceMeta(furnaceOrientation, horizIndex);
		
		return new Object[]{modblock, meta};
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
	
	
	// Smooth Stone
	public static Object[] chooseModSmoothStoneBlock()
	{
		Block modblock=null; int meta = 0;
		modblock = Block.getBlockFromName(ModObjects.smoothStoneUTD);
		if (modblock == null) {modblock = Blocks.double_stone_slab; meta=8;}
		
		return new Object[]{modblock, meta};
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
			
			if (mod.toLowerCase().equals("uptodate"))
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
		}
		// If all else fails, grab the vanilla version
		return new Object[]{materialMeta <4 ? Blocks.log : Blocks.log2, orientation*4+materialMeta%4};
	}
	
	public static Object[] chooseModStrippedWood(int materialMeta)
	{
		Block modblock=null;
		
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
		else {return new Object[]{materialMeta<4 ? Blocks.log : Blocks.log2, 12+materialMeta%4};}
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
			if (mod.toLowerCase().equals("ganyssurface"))
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
			
			if (mod.toLowerCase().equals("mariculture"))
			{
				moditem = FunctionsVN.getItemFromName(ModObjects.kelpWrapMC);
				if (moditem != null) {return new ItemStack(moditem, 1, 8);}
			}
			else if (mod.toLowerCase().equals("biomesoplenty"))
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
	
	// Raw Rabbit
	
	// Cooked Rabbit
	
	// Rabbit Stew
	
	// Rabbit Hide
	
	// Rabbit Foot
	
	// Suspicious Stew
	
	
	// Wooden Pressure Plate
	// Fence
	public static Block chooseModPressurePlate(int materialMeta)
	{
		String[] modprioritylist = GeneralConfig.modFence;
		
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
			if (mod.toLowerCase().equals("etfuturum"))
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
			if (mod.toLowerCase().equals("ganyssurface"))
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
		}
		// If all else fails, grab the vanilla version
		return Blocks.fence;
	}
	
	/**
	 * These functions check the priority order provided by the config file, and return a block or object if one exists.
	 */
	
	
	
}
