package astrotibs.villagenames.integration.antiqueatlas;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.config.village.VillageGeneratorConfigHandler;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.Reference;
import astrotibs.villagenames.village.StructureVillageVN;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import hunternif.mc.atlas.AntiqueAtlasMod;
import hunternif.mc.atlas.api.AtlasAPI;
import hunternif.mc.atlas.api.TileAPI;
import hunternif.mc.atlas.ext.ExtTileIdMap;
import hunternif.mc.atlas.marker.Marker;
import hunternif.mc.atlas.marker.MarkersData;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureData;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

/*
 * Adapted from Antique Atlas by Hunternif et al.
 * https://github.com/AntiqueAtlasTeam/AntiqueAtlas/blob/1.7.10/src/main/java/hunternif/mc/atlas/ext/ExtTileIdMap.java
 */
public class VillageWatcherAA
{
	// Register custom texture
	public static final String
	TILE_VILLAGE_FENCE = "npcVillageFence",
	TILE_VILLAGE_TREE = "npcVillageTree",
	TILE_VILLAGE_HOUSE = "npcVillageHouse";
	
	@SideOnly(Side.CLIENT)
	public static void registerTextures()
	{
		setCustomTileTexture(TILE_VILLAGE_FENCE, "Village Fence", "fence");
		setCustomTileTexture(TILE_VILLAGE_TREE, "Village Central Tree", "forest");
		setCustomTileTexture(TILE_VILLAGE_HOUSE, "Village House", "house");
	}
	
	public static void setCustomTileTexture(String tileName, String printedName, String aaTileFileRoot)
	{
		TileAPI tileAPI = AtlasAPI.getTileAPI();
		tileAPI.setCustomTileTexture(tileName, tileAPI.registerTextureSet(printedName, new ResourceLocation(Reference.ANTIQUE_ATLAS_MODID, "textures/gui/tiles/"+aaTileFileRoot+".png")));
	}
	
	public static final String VILLAGE_MARKER = "village";
	
	/** Set of tag names for every village, in the format "[x, y]" */
	//TODO: list of visited villages must be reset when changing worlds!
	// And the same goes for Nether Fortress Watcher
	private final Set<String> visited = new HashSet<String>();
	
	
	// === ANTIQUE ATLAS ICONS === //
	public static final String
	TILE_VILLAGE_LIBRARY = "npcVillageLibrary",
	TILE_VILLAGE_SMITHY = "npcVillageSmithy",
	TILE_VILLAGE_L_HOUSE = "npcVillageLHouse",
	TILE_VILLAGE_FARMLAND_SMALL = "npcVillageFarmlandSmall",
	TILE_VILLAGE_FARMLAND_LARGE = "npcVillageFarmlandLarge",
	TILE_VILLAGE_WELL = "npcVillageWell",
	TILE_VILLAGE_TORCH = "npcVillageTorch",
	//TILE_VILLAGE_PATH_X = "npcVillagePathX",
	//TILE_VILLAGE_PATH_Z = "npcVillagePathZ",
	TILE_VILLAGE_HUT = "npcVillageHut",
	TILE_VILLAGE_SMALL_HOUSE = "npcVillageSmallHouse",
	TILE_VILLAGE_BUTCHERS_SHOP = "npcVillageButchersShop",
	TILE_VILLAGE_CHURCH = "npcVillageChurch";
	
	// === LEGACY VILLAGE ICONS === //
	
//	private static final String LIBRARY= "ViBH"; // Big-ish house with a high slanted roof, large windows; books and couches inside.
//	private static final String SMITHY = "ViS"; // The smithy.
//	private static final String L_HOUSE = "ViTRH"; // Medium-sized, L-shaped houses with slanted roof.
//	private static final String FARMLAND_LARGE = "ViDF"; // Large field, basically 2 times larger than FIELD2
//	private static final String FARMLAND_SMALL = "ViF"; // Smaller field.
//	@SuppressWarnings("unused")
//	private static final String PATH = "ViSR"; // Usually too narrow to be seen between the houses...
//	private static final String TORCH = "ViL";
//	private static final String WELL = "ViW";
//	private static final String START = "ViStart"; // Same as WELL
//	private static final String BUTCHERS = "ViPH"; // Medium house with a low slanted roof and a dirt porch with a fence.
//	private static final String HUT = "ViSmH"; // Tiniest hut with a flat roof.
//	private static final String HOUSE_SMALL = "ViSH"; // Slightly larger than huts, sometimes with a fenced balcony on the roof and a ladder.
//	private static final String CHURCH = "ViST"; // The church.
	
	
	// === NEW VILLAGE ICONS === //
	
	// Village Misc
	private static final String
	
	VN_PATH = "VNPath", // VN Path
	VN_DECOR_TORCH = "VNDecTor", // VN Decor Torch

	// Village Centers
	PLAINS_FOUNTAIN = "VNPlF01", // Plains Fountain
	PLAINS_WELL = "VNPlMP1", // Plains Well
	PLAINS_MARKET = "VNPlMP2", // Plains Market
	PLAINS_CENTRAL_TREE = "VNPlMP3", // Plains Central Tree
	
	DESERT_FOUNTAIN = "VNDeMP1", // Desert Fountain
	DESERT_WELL = "VNDeMP2", // Desert Well
	DESERT_MARKET = "VNDeMP3", // Desert Market
	
	TAIGA_GRASSY_CENTER = "VNTaMP1", // Taiga Grassy Center
	TAIGA_WELL = "VNTaMP2", // Taiga Well
	
	SAVANNA_MARKET = "VNSaMP1", // Savanna Market
	SAVANNA_FOUNTAIN = "VNSaMP2", // Savanna Fountain
	SAVANNA_DOUBLE_WELL = "VNSaMP3", // Savanna Double Well
	SAVANNA_SINGLE_WELL = "VNSaMP4", // Savanna Single Well
	
	SNOWY_ICE_SPIRE = "VNSnMP1", // Snowy Ice Spire
	SNOWY_FOUNTAIN = "VNSnMP2", // Snowy Fountain
	SNOWY_PAVILION = "VNSnMP3", // Snowy Pavilion
	
	JUNGLE_STATUE = "VNJuSta", // Jungle Statue
	JUNGLE_TREE = "VNJuTre", // Jungle Tree
	JUNGLE_GARDEN = "VNJuGar", // Jungle Garden
	JUNGLE_VILLA = "VNJuVil", // Jungle Villa
	
	SWAMP_WILLOW = "VNSwWil", // Swamp Willow
	SWAMP_STATUE = "VNSwSta", // Swamp Statue
	SWAMP_PAVILION = "VNSwPav", // Swamp Pavilion
	SWAMP_MONOLITH = "VNSwMon", // Swamp Monolith
	
	// Village Structures
	PLAINS_FLOWER_PLANTER = "VNPlAcc1", // Plains Flower Planter
	PLAINS_SMALL_ANIMAL_PEN = "VNPlAnP1", // Plains Small Animal Pen
	PLAINS_LARGE_ANIMAL_PEN = "VNPlAnP2", // Plains Large Animal Pen
	PLAINS_DECORATED_ANIMAL_PEN = "VNPlAnP3", // Plains Decorated Animal Pen
	PLAINS_ARMORER_HOUSE = "VNPlArm1", // Plains Armorer House
	PLAINS_LARGE_HOUSE = "VNPlBiH1", // Plains Large House
	PLAINS_SMALL_BUTCHER_SHOP = "VNPlBut1", // Plains Small Butcher Shop
	PLAINS_LARGE_BUTCHER_SHOP = "VNPlBut2", // Plains Large Butcher Shop
	PLAINS_CARTOGRAPHER_HOUSE = "VNPlCar1", // Plains Cartographer House
	PLAINS_FISHER_COTTAGE = "VNPlFis1", // Plains Fisher Cottage
	PLAINS_FLETCHER_HOUSE = "VNPlFle1", // Plains Fletcher House
	PLAINS_LARGE_FARM = "VNPlLFa1", // Plains Large Farm
	PLAINS_LARGE_LIBRARY = "VNPlLib1", // Plains Large Library
	PLAINS_SMALL_LIBRARY = "VNPlLib2", // Plains Small Library
	PLAINS_MASON_HOUSE = "VNPlMas1", // Plains Mason House
	PLAINS_MEDIUM_HOUSE_1 = "VNPlMeH1", // Plains Medium House 1
	PLAINS_MEDIUM_HOUSE_2 = "VNPlMeH2", // Plains Medium House 2
	PLAINS_LARGE_MARKET = "VNPlMeP4", // Plains Large Market
	PLAINS_SMALL_MARKET = "VNPlMeP5", // Plains Small Market
	PLAINS_SHEPHERDS_HOUSE = "VNPlShe1", // Plains Shepherd's House
	PLAINS_SMALL_FARM = "VNPlSFa1", // Plains Small Farm
	PLAINS_SMALL_HOUSE_1 = "VNPlSmH1", // Plains Small House 1
	PLAINS_SMALL_HOUSE_2 = "VNPlSmH2", // Plains Small House 2
	PLAINS_SMALL_HOUSE_3 = "VNPlSmH3", // Plains Small House 3
	PLAINS_SMALL_HOUSE_4 = "VNPlSmH4", // Plains Small House 4
	PLAINS_SMALL_HOUSE_5 = "VNPlSmH5", // Plains Small House 5
	PLAINS_SMALL_HOUSE_6 = "VNPlSmH6", // Plains Small House 6
	PLAINS_SMALL_HOUSE_7 = "VNPlSmH7", // Plains Small House 7
	PLAINS_SMALL_HOUSE_8 = "VNPlSmH8", // Plains Small House 8
	PLAINS_COBBLESTONE_STABLE = "VNPlSta1", // Plains Cobblestone Stable
	PLAINS_TERRACOTTA_STABLE = "VNPlSta2", // Plains Terracotta Stable
	PLAINS_TANNERY = "VNPlTan2", // Plains Tannery
	PLAINS_TERRACOTTA_TEMPLE = "VNPlTem3", // Plains Terracotta Temple
	PLAINS_COBBLESTONE_TEMPLE = "VNPlTem4", // Plains Cobblestone Temple
	PLAINS_TOOL_SMITHY = "VNPlTSm1", // Plains Tool Smithy
	PLAINS_WEAPON_SMITHY = "VNPlWSm1", // Plains Weapon Smithy
	PLAINS_ROAD_DECOR = "VNPlStD1", // Plains Road Decor

	DESERT_SMALL_ANIMAL_PEN = "VNDeAnP1", // Desert Small Animal Pen
	DESERT_COVERED_ANIMAL_PEN = "VNDeAnP2", // Desert Covered Animal Pen
	DESERT_ARMORER_HOUSE = "VNDeArm1", // Desert Armorer House
	DESERT_BUTCHER_SHOP = "VNDeBut1", // Desert Butcher Shop
	DESERT_CARTOGRAPHER_HOUSE = "VNDeCar1", // Desert Cartographer House
	DESERT_SMALL_FARM = "VNDeFar1", // Desert Small Farm
	DESERT_MEDIUM_FARM = "VNDeFar2", // Desert Medium Farm
	DESERT_FISHER_COTTAGE = "VNDeFis1", // Desert Fisher Cottage
	DESERT_FLETCHER_HOUSE = "VNDeFle1", // Desert Fletcher House
	DESERT_LARGE_FARM = "VNDeLFa1", // Desert Large Farm
	DESERT_LIBRARY = "VNDeLib1", // Desert Library
	DESERT_MASON_HOUSE = "VNDeMas1", // Desert Mason House
	DESERT_MEDIUM_HOUSE = "VNDeMeH1", // Desert Medium House
	DESERT_LARGE_HOUSE = "VNDeMeH2", // Desert Large House
	DESERT_SHEPHERDS_HOUSE = "VNDeShe1", // Desert Shepherd's House
	DESERT_SMALL_HOUSE_1 = "VNDeSmH1", // Desert Small House 1
	DESERT_SMALL_HOUSE_2 = "VNDeSmH2", // Desert Small House 2
	DESERT_SMALL_HOUSE_3 = "VNDeSmH3", // Desert Small House 3
	DESERT_SMALL_HOUSE_4 = "VNDeSmH4", // Desert Small House 4
	DESERT_SMALL_HOUSE_5 = "VNDeSmH5", // Desert Small House 5
	DESERT_SMALL_HOUSE_6 = "VNDeSmH6", // Desert Small House 6
	DESERT_SMALL_HOUSE_7 = "VNDeSmH7", // Desert Small House 7
	DESERT_SMALL_HOUSE_8 = "VNDeSmH8", // Desert Small House 8
	DESERT_TANNERY = "VNDeTan1", // Desert Tannery
	DESERT_TEMPLE_1 = "VNDeTem1", // Desert Temple 1
	DESERT_TEMPLE_2 = "VNDeTem2", // Desert Temple 2
	DESERT_TOOL_SMITHY = "VNDeTSm1", // Desert Tool Smithy
	DESERT_WEAPON_SMITHY = "VNDeWSm1", // Desert Weapon Smithy
	DESERT_ROAD_DECOR = "VNDeStD1", // Desert Road Decor
	DESERT_ROAD_TERRACOTTA_ACCENT_1 = "VNDeStS1", // Desert Road Terracotta Accent 1
	DESERT_ROAD_TERRACOTTA_ACCENT_2 = "VNDeStS2", // Desert Road Terracotta Accent 2
	DESERT_ROAD_DECOR_3 = "VNDeStS3", // Desert Road Decor 3

	TAIGA_ANIMAL_PEN = "VNTaAnP1", // Taiga Animal Pen
	TAIGA_ARMORER_STATION = "VNTaArm2", // Taiga Armorer Station
	TAIGA_ARMORER_HOUSE = "VNTaArm1", // Taiga Armorer House
	TAIGA_BUTCHER_SHOP = "VNTaBut1", // Taiga Butcher Shop
	TAIGA_CARTOGRAPHER_HOUSE = "VNTaCar1", // Taiga Cartographer House
	TAIGA_FISHER_COTTAGE = "VNTaFis1", // Taiga Fisher Cottage
	TAIGA_FLETCHER_HOUSE = "VNTaFle1", // Taiga Fletcher House
	TAIGA_LARGE_FARM = "VNTaLFa1", // Taiga Large Farm
	TAIGA_MEDIUM_FARM = "VNTaLFa2", // Taiga Medium Farm
	TAIGA_LIBRARY = "VNTaLib1", // Taiga Library
	TAIGA_MASON_HOUSE = "VNTaMas1", // Taiga Mason House
	TAIGA_MEDIUM_HOUSE_1 = "VNTaMeH1", // Taiga Medium House 1
	TAIGA_MEDIUM_HOUSE_2 = "VNTaMeH2", // Taiga Medium House 2
	TAIGA_MEDIUM_HOUSE_3 = "VNTaMeH3", // Taiga Medium House 3
	TAIGA_MEDIUM_HOUSE_4 = "VNTaMeH4", // Taiga Medium House 4
	TAIGA_SHEPHERD_HOUSE = "VNTaShe1", // Taiga Shepherd House
	TAIGA_SMALL_FARM = "VNTaSFa1", // Taiga Small Farm
	TAIGA_SMALL_HOUSE_1 = "VNTaSmH1", // Taiga Small House 1
	TAIGA_SMALL_HOUSE_2 = "VNTaSmH2", // Taiga Small House 2
	TAIGA_SMALL_HOUSE_3 = "VNTaSmH3", // Taiga Small House 3
	TAIGA_SMALL_HOUSE_4 = "VNTaSmH4", // Taiga Small House 4
	TAIGA_SMALL_HOUSE_5 = "VNTaSmH5", // Taiga Small House 5
	TAIGA_TANNERY = "VNTaTan1", // Taiga Tannery
	TAIGA_TEMPLE = "VNTaTem1", // Taiga Temple
	TAIGA_TOOL_SMITHY = "VNTaTSm1", // Taiga Tool Smithy
	TAIGA_WEAPON_SMITH_HOUSE = "VNTaWSm1", // Taiga Weapon Smith House
	TAIGA_WEAPON_SMITH_STATION = "VNTaWSm2", // Taiga Weapon Smith Station
	TAIGA_ROAD_DECOR = "VNTaStD1", // Taiga Road Decor

	SAVANNA_COVERED_ANIMAL_PEN = "VNSaAnP1", // Savanna Covered Animal Pen
	SAVANNA_LARGE_ANIMAL_PEN = "VNSaAnP2", // Savanna Large Animal Pen
	SAVANNA_MEDIUM_ANIMAL_PEN = "VNSaAnP3", // Savanna Medium Animal Pen
	SAVANNA_ARMORER_HOUSE = "VNSaArm1", // Savanna Armorer House
	SAVANNA_BUTCHER_SHOP_1 = "VNSaBut1", // Savanna Butcher Shop 1
	SAVANNA_BUTCHER_SHOP_2 = "VNSaBut2", // Savanna Butcher Shop 2
	SAVANNA_CARTOGRAPHER = "VNSaCar1", // Savanna Cartographer
	SAVANNA_FISHER_COTTAGE = "VNSaFis1", // Savanna Fisher Cottage
	SAVANNA_FLETCHER_HOUSE = "VNSaFle1", // Savanna Fletcher House
	SAVANNA_METHODICAL_FARM = "VNSaLFa1", // Savanna Methodical Farm
	SAVANNA_HAPHAZARD_FARM = "VNSaLFa2", // Savanna Haphazard Farm
	SAVANNA_LIBRARY = "VNSaLib1", // Savanna Library
	SAVANNA_MASON_HOUSE = "VNSaMas1", // Savanna Mason House
	SAVANNA_MEDIUM_HOUSE_1 = "VNSaMeH1", // Savanna Medium House 1
	SAVANNA_MEDIUM_HOUSE_2 = "VNSaMeH2", // Savanna Medium House 2
	SAVANNA_SHEPHERD_HOUSE = "VNSaShe1", // Savanna Shepherd House
	SAVANNA_SMALL_FARM = "VNSaSmFa", // Savanna Small Farm
	SAVANNA_SMALL_HOUSE_1 = "VNSaSmH1", // Savanna Small House 1
	SAVANNA_SMALL_HOUSE_2 = "VNSaSmH2", // Savanna Small House 2
	SAVANNA_SMALL_HOUSE_3 = "VNSaSmH3", // Savanna Small House 3
	SAVANNA_SMALL_HOUSE_4 = "VNSaSmH4", // Savanna Small House 4
	SAVANNA_SMALL_HOUSE_5 = "VNSaSmH5", // Savanna Small House 5
	SAVANNA_SMALL_HOUSE_6 = "VNSaSmH6", // Savanna Small House 6
	SAVANNA_SMALL_HOUSE_7 = "VNSaSmH7", // Savanna Small House 7
	SAVANNA_SMALL_HOUSE_8 = "VNSaSmH8", // Savanna Small House 8
	SAVANNA_TANNERY = "VNSaTsn1", // Savanna Tannery
	SAVANNA_TEMPLE_1 = "VNSaTem1", // Savanna Temple 1
	SAVANNA_TEMPLE_2 = "VNSaTem2", // Savanna Temple 2
	SAVANNA_TOOL_SMITHY = "VNSaTSm1", // Savanna Tool Smithy
	SAVANNA_SMALL_WEAPON_SMITHY = "VNSaWSm1", // Savanna Small Weapon Smithy
	SAVANNA_LARGE_WEAPON_SMITHY = "VNSaWSm2", // Savanna Large Weapon Smithy
	SAVANNA_ROAD_DECOR = "VNSaStD1", // Savanna Road Decor
	SAVANNA_ROADSIDE_FARM_1 = "VNSaStS1", // Savanna Roadside Farm 1
	SAVANNA_ROADSIDE_FARM_2 = "VNSaStS2", // Savanna Roadside Farm 2
	SAVANNA_ROADSIDE_FARM_3 = "VNSaStS3", // Savanna Roadside Farm 3
	SAVANNA_ROADSIDE_FARM_4 = "VNSaStS4", // Savanna Roadside Farm 4

	SNOWY_ANIMAL_PEN_1 = "VNSnAnP1", // Snowy Animal Pen 1
	SNOWY_ANIMAL_PEN_2 = "VNSnAnP2", // Snowy Animal Pen 2
	SNOWY_ARMORER_HOUSE_1 = "VNSnArH1", // Snowy Armorer House 1
	SNOWY_ARMORER_HOUSE_2 = "VNSnArH2", // Snowy Armorer House 2
	SNOWY_BUTCHER_HOUSE = "VNSnBut1", // Snowy Butcher House
	SNOWY_BUTCHER_IGLOO = "VNSnBut2", // Snowy Butcher Igloo
	SNOWY_CARTOGRAPHER_HOUSE = "VNSnCar1", // Snowy Cartographer House
	SNOWY_SQUARE_FARM = "VNSnFar1", // Snowy Square Farm
	SNOWY_PATCH_FARM = "VNSnFar2", // Snowy Patch Farm
	SNOWY_FISHER_COTTAGE = "VNSnFisC", // Snowy Fisher Cottage
	SNOWY_FLETCHER_HOUSE = "VNSnFle1", // Snowy Fletcher House
	SNOWY_LIBRARY = "VNSnLib1", // Snowy Library
	SNOWY_MASON_HOUSE_1 = "VNSnMas1", // Snowy Mason House 1
	SNOWY_MASON_HOUSE_2 = "VNSnMas2", // Snowy Mason House 2
	SNOWY_MEDIUM_HOUSE_1 = "VNSnMeH1", // Snowy Medium House 1
	SNOWY_MEDIUM_HOUSE_2 = "VNSnMeH2", // Snowy Medium House 2
	SNOWY_MEDIUM_HOUSE_3 = "VNSnMeH3", // Snowy Medium House 3
	SNOWY_SHEPHERD_HOUSE = "VNSnShe1", // Snowy Shepherd House
	SNOWY_SMALL_HOUSE_1 = "VNSnSmH1", // Snowy Small House 1
	SNOWY_SMALL_HOUSE_2 = "VNSnSmH2", // Snowy Small House 2
	SNOWY_SMALL_HOUSE_3 = "VNSnSmH3", // Snowy Small House 3
	SNOWY_SMALL_HOUSE_4 = "VNSnSmH4", // Snowy Small House 4
	SNOWY_SMALL_HOUSE_5 = "VNSnSmH5", // Snowy Small House 5
	SNOWY_SMALL_HOUSE_6 = "VNSnSmH6", // Snowy Small House 6
	SNOWY_SMALL_HOUSE_7 = "VNSnSmH7", // Snowy Small House 7
	SNOWY_SMALL_HOUSE_8 = "VNSnSmH8", // Snowy Small House 8
	SNOWY_TANNERY = "VNSnTan1", // Snowy Tannery
	SNOWY_TEMPLE = "VNSnTem1", // Snowy Temple
	SNOWY_TOOL_SMITHY = "VNSnTSm1", // Snowy Tool Smithy
	SNOWY_WEAPON_SMITHY = "VNSnWSm1", // Snowy Weapon Smithy
	SNOWY_ROAD_DECOR = "VNSnStD1", // Snowy Road Decor
	
	JUNGLE_ARMORER_HOUSE = "VNJuArmH", // Jungle Armorer House
	JUNGLE_BUTCHER_SHOP = "VNJuButS", // Jungle Butcher Shop
	JUNGLE_CARTOGRAPHER_HOUSE_1 = "VNJuCaH1", // Jungle Cartographer House 1
	JUNGLE_CARTOGRAPHER_HOUSE_2 = "VNJuCaH2", // Jungle Cartographer House 2
	JUNGLE_FISHER_COTTAGE = "VNJuFshC", // Jungle Fisher Cottage
	JUNGLE_FLETCHER_HOUSE_1 = "VNJuFlH1", // Jungle Fletcher House 1
	JUNGLE_FLETCHER_HOUSE_2 = "VNJuFlH2", // Jungle Fletcher House 2
	JUNGLE_LARGE_HOUSE = "VNJuLaHo", // Jungle Large House
	JUNGLE_LIBRARY = "VNJuLibr", // Jungle Library
	JUNGLE_MASON_HOUSE = "VNJuMasH", // Jungle Mason House
	JUNGLE_MEDIUM_HOUSE_1 = "VNJuMeH1", // Jungle Medium House 1
	JUNGLE_MEDIUM_HOUSE_2 = "VNJuMeH2", // Jungle Medium House 2
	JUNGLE_MEDIUM_HOUSE_3 = "VNJuMeH3", // Jungle Medium House 3
	JUNGLE_MEDIUM_HOUSE_4 = "VNJuMeH4", // Jungle Medium House 4
	JUNGLE_SHEPHERD_HOUSE = "VNJuShpH", // Jungle Shepherd House
	JUNGLE_SMALL_HOUSE_1 = "VNJuSmH1", // Jungle Small House 1
	JUNGLE_SMALL_HOUSE_2 = "VNJuSmH2", // Jungle Small House 2
	JUNGLE_SMALL_HOUSE_3 = "VNJuSmH3", // Jungle Small House 3
	JUNGLE_SMALL_HOUSE_4 = "VNJuSmH4", // Jungle Small House 4
	JUNGLE_SMALL_HOUSE_5 = "VNJuSmH5", // Jungle Small House 5
	JUNGLE_SMALL_HOUSE_6 = "VNJuSmH6", // Jungle Small House 6
	JUNGLE_SMALL_HOUSE_7 = "VNJuSmH7", // Jungle Small House 7
	JUNGLE_SMALL_HOUSE_8 = "VNJuSmH8", // Jungle Small House 8
	JUNGLE_STABLE = "VNJuStbl", // Jungle Stable
	JUNGLE_STONE_ANIMAL_PEN = "VNJuStAP", // Jungle Stone Animal Pen
	JUNGLE_STEPPED_FARM = "VNJuStFa", // Jungle Stepped Farm
	JUNGLE_TAMED_FARM = "VNJuTaFa", // Jungle Tamed Farm
	JUNGLE_TANNERY_1 = "VNJuTan1", // Jungle Tannery 1
	JUNGLE_TANNERY_2 = "VNJuTan2", // Jungle Tannery 2
	JUNGLE_TEMPLE = "VNJuTemp", // Jungle Temple
	JUNGLE_TOOL_SMITHY_1 = "VNJuTSm1", // Jungle Tool Smithy 1
	JUNGLE_TOOL_SMITHY_2 = "VNJuTSm2", // Jungle Tool Smithy 2
	JUNGLE_WEAPON_SMITHY = "VNJuWpSm", // Jungle Weapon Smithy
	JUNGLE_WILD_FARM = "VNJuWiFa", // Jungle Wild Farm
	JUNGLE_WOOD_ANIMAL_PEN = "VNJuWdAP", // Jungle Wood Animal Pen
	JUNGLE_ROAD_DECOR = "VNJuStDe", // Jungle Road Decor
	JUNGLE_ROAD_ACCENT_1 = "VNJuRdA1", // Jungle Well
	JUNGLE_ROAD_ACCENT_2 = "VNJuRdA2", // Jungle Treehouse
	
	SWAMP_ANIMAL_PEN_1 = "VNSwAnP1", // Swamp Animal Pen 1
	SWAMP_ANIMAL_PEN_2 = "VNSwAnP2", // Swamp Animal Pen 2
	SWAMP_ARMORER_HOUSE = "VNSwArHo", // Swamp Armorer House
	SWAMP_BUTCHER_SHOP = "VNSwBuSh", // Swamp Butcher Shop
	SWAMP_CARTOGRAPHER_HOUSE = "VNSwCaHo", // Swamp Cartographer House
	SWAMP_FISHER_COTTAGE_1 = "VNSwFiC1", // Swamp Fisher Cottage 1
	SWAMP_FISHER_COTTAGE_2 = "VNSwFiC2", // Swamp Fisher Cottage 2
	SWAMP_FLETCHER_HOUSE = "VNSwFlHo", // Swamp Fletcher House
	SWAMP_HORRIBLE_SECRET = "VNSwHoSe", // Swamp Horrible Secret - NO MAPPING for this because it's supposed to be a secret!
	SWAMP_HUT_FARM = "VNSwHuFa", // Swamp Hut Farm
	SWAMP_LARGE_HOUSE = "VNSwLaHo", // Swamp Large House
	SWAMP_LIBRARY = "VNSwLibr", // Swamp Library
	SWAMP_MASON_HOUSE = "VNSwMaHo", // Swamp Mason House
	SWAMP_MEDIUM_HOUSE_1 = "VNSwMeH1", // Swamp Medium House 1
	SWAMP_MEDIUM_HOUSE_2 = "VNSwMeH2", // Swamp Medium House 2
	SWAMP_SHEPHERD_HOUSE_1 = "VNSwShH1", // Swamp Shepherd House 1
	SWAMP_SHEPHERD_HOUSE_2 = "VNSwShH2", // Swamp Shepherd House 2
	SWAMP_SMALL_HOUSE_1 = "VNSwSmH1", // Swamp Small House 1
	SWAMP_SMALL_HOUSE_2 = "VNSwSmH2", // Swamp Small House 2
	SWAMP_SMALL_HOUSE_3 = "VNSwSmH3", // Swamp Small House 3
	SWAMP_SMALL_HOUSE_4 = "VNSwSmH4", // Swamp Small House 4
	SWAMP_SMALL_HOUSE_5 = "VNSwSmH5", // Swamp Small House 5
	SWAMP_STABLE = "VNSwStb;", // Swamp Stable
	SWAMP_TANNERY = "VNSwTann", // Swamp Tannery
	SWAMP_TEMPLE = "VNSwTemp", // Swamp Temple
	SWAMP_TOOL_SMITHY = "VNSwToSm", // Swamp Tool Smithy
	SWAMP_WEAPON_SMITHY = "VNSwWeSm", // Swamp Weapon Smithy
	SWAMP_WILD_FARM = "VNSwWiFa", // Swamp Wild Farm
	SWAMP_ROAD_DECOR = "VNSwStDe", // Swamp Road Decor
	SWAMP_ROAD_ACCENT = "VNSwRdAc", // Swamp Road Accent
	
	PLACEHOLDER = "plcholdr"; // TODO - placeholder while you add other components to the list
	
		
	// Associates village structure components with an AA map icon
	private static final Map<String, String> COMPONENT_TO_TILE_MAP;
	static {
		ImmutableMap.Builder<String, String> builder = new Builder<String, String>();
		
		// === LEGACY VILLAGE ICONS === //
//		builder.put(LIBRARY, ExtTileIdMap.TILE_VILLAGE_LIBRARY);
//		builder.put(SMITHY, ExtTileIdMap.TILE_VILLAGE_SMITHY);
//		builder.put(L_HOUSE, ExtTileIdMap.TILE_VILLAGE_L_HOUSE);
//		builder.put(FARMLAND_LARGE, ExtTileIdMap.TILE_VILLAGE_FARMLAND_LARGE);
//		builder.put(FARMLAND_SMALL, ExtTileIdMap.TILE_VILLAGE_FARMLAND_SMALL);
//		//builder.put(PATH, ExtTileIdMap.TILE_VILLAGE_PATH_X); // Handled separately
//		builder.put(TORCH, ExtTileIdMap.TILE_VILLAGE_TORCH);
//		builder.put(WELL, ExtTileIdMap.TILE_VILLAGE_WELL);
//		builder.put(START, ExtTileIdMap.TILE_VILLAGE_WELL);
//		builder.put(BUTCHERS, ExtTileIdMap.TILE_VILLAGE_BUTCHERS_SHOP);
//		builder.put(HOUSE_SMALL, ExtTileIdMap.TILE_VILLAGE_SMALL_HOUSE);
//		builder.put(HUT, ExtTileIdMap.TILE_VILLAGE_HUT);
//		builder.put(CHURCH, ExtTileIdMap.TILE_VILLAGE_CHURCH);
		
		// === NEW VILLAGE ICONS === //
		// Miscellany
		builder.put(VN_DECOR_TORCH, TILE_VILLAGE_TORCH);
		
		// Centers
		builder.put(PLAINS_FOUNTAIN, TILE_VILLAGE_WELL);
		builder.put(PLAINS_WELL, TILE_VILLAGE_WELL);
		builder.put(PLAINS_MARKET, TILE_VILLAGE_HUT);
		builder.put(PLAINS_CENTRAL_TREE, TILE_VILLAGE_TREE);
		builder.put(DESERT_FOUNTAIN, TILE_VILLAGE_WELL);
		builder.put(DESERT_WELL, TILE_VILLAGE_WELL);
		builder.put(DESERT_MARKET, TILE_VILLAGE_HUT);
		builder.put(TAIGA_GRASSY_CENTER, TILE_VILLAGE_WELL);
		builder.put(TAIGA_WELL, TILE_VILLAGE_WELL);
		builder.put(SAVANNA_MARKET, TILE_VILLAGE_HUT);
		builder.put(SAVANNA_FOUNTAIN, TILE_VILLAGE_WELL);
		builder.put(SAVANNA_DOUBLE_WELL, TILE_VILLAGE_WELL);
		builder.put(SAVANNA_SINGLE_WELL, TILE_VILLAGE_WELL);
		builder.put(SNOWY_ICE_SPIRE, TILE_VILLAGE_WELL);
		builder.put(SNOWY_FOUNTAIN, TILE_VILLAGE_WELL);
		builder.put(SNOWY_PAVILION, TILE_VILLAGE_HUT);
		
		builder.put(JUNGLE_STATUE, TILE_VILLAGE_WELL);
		builder.put(JUNGLE_TREE, TILE_VILLAGE_TREE);
		builder.put(JUNGLE_GARDEN, TILE_VILLAGE_WELL);
		builder.put(JUNGLE_VILLA, TILE_VILLAGE_L_HOUSE);
		builder.put(SWAMP_WILLOW, TILE_VILLAGE_TREE);
		builder.put(SWAMP_STATUE, TILE_VILLAGE_WELL);
		builder.put(SWAMP_PAVILION, TILE_VILLAGE_HUT);
		builder.put(SWAMP_MONOLITH, TILE_VILLAGE_WELL);
		
		// Plains
		builder.put(PLAINS_FLOWER_PLANTER, TILE_VILLAGE_FARMLAND_SMALL);
		builder.put(PLAINS_SMALL_ANIMAL_PEN, TILE_VILLAGE_FENCE);
		builder.put(PLAINS_LARGE_ANIMAL_PEN, TILE_VILLAGE_FENCE);
		builder.put(PLAINS_DECORATED_ANIMAL_PEN, TILE_VILLAGE_FENCE);
		builder.put(PLAINS_ARMORER_HOUSE, TILE_VILLAGE_SMITHY);
		builder.put(PLAINS_LARGE_HOUSE, TILE_VILLAGE_HOUSE);
		builder.put(PLAINS_SMALL_BUTCHER_SHOP, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(PLAINS_LARGE_BUTCHER_SHOP, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(PLAINS_CARTOGRAPHER_HOUSE, TILE_VILLAGE_LIBRARY);
		builder.put(PLAINS_FISHER_COTTAGE, TILE_VILLAGE_HUT);
		builder.put(PLAINS_FLETCHER_HOUSE, TILE_VILLAGE_L_HOUSE);
		builder.put(PLAINS_LARGE_FARM, TILE_VILLAGE_FARMLAND_LARGE);
		builder.put(PLAINS_LARGE_LIBRARY, TILE_VILLAGE_LIBRARY);
		builder.put(PLAINS_SMALL_LIBRARY, TILE_VILLAGE_LIBRARY);
		builder.put(PLAINS_MASON_HOUSE, TILE_VILLAGE_L_HOUSE);
		builder.put(PLAINS_MEDIUM_HOUSE_1, TILE_VILLAGE_L_HOUSE);
		builder.put(PLAINS_MEDIUM_HOUSE_2, TILE_VILLAGE_HOUSE);
		builder.put(PLAINS_LARGE_MARKET, TILE_VILLAGE_HUT);
		builder.put(PLAINS_SMALL_MARKET, TILE_VILLAGE_HUT);
		builder.put(PLAINS_SHEPHERDS_HOUSE, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(PLAINS_SMALL_FARM, TILE_VILLAGE_FARMLAND_SMALL);
		builder.put(PLAINS_SMALL_HOUSE_1, TILE_VILLAGE_HUT);
		builder.put(PLAINS_SMALL_HOUSE_2, TILE_VILLAGE_HUT);
		builder.put(PLAINS_SMALL_HOUSE_3, TILE_VILLAGE_HUT);
		builder.put(PLAINS_SMALL_HOUSE_4, TILE_VILLAGE_HUT);
		builder.put(PLAINS_SMALL_HOUSE_5, TILE_VILLAGE_HOUSE);
		builder.put(PLAINS_SMALL_HOUSE_6, TILE_VILLAGE_HUT);
		builder.put(PLAINS_SMALL_HOUSE_7, TILE_VILLAGE_HOUSE);
		builder.put(PLAINS_SMALL_HOUSE_8, TILE_VILLAGE_HUT);
		builder.put(PLAINS_COBBLESTONE_STABLE, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(PLAINS_TERRACOTTA_STABLE, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(PLAINS_TANNERY, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(PLAINS_TERRACOTTA_TEMPLE, TILE_VILLAGE_CHURCH);
		builder.put(PLAINS_COBBLESTONE_TEMPLE, TILE_VILLAGE_CHURCH);
		builder.put(PLAINS_TOOL_SMITHY, TILE_VILLAGE_SMITHY);
		builder.put(PLAINS_WEAPON_SMITHY, TILE_VILLAGE_SMITHY);
		builder.put(PLAINS_ROAD_DECOR, TILE_VILLAGE_TORCH);
		
		// Desert
		builder.put(DESERT_SMALL_ANIMAL_PEN, TILE_VILLAGE_FENCE);
		builder.put(DESERT_COVERED_ANIMAL_PEN, TILE_VILLAGE_FENCE);
		builder.put(DESERT_ARMORER_HOUSE, TILE_VILLAGE_SMITHY);
		builder.put(DESERT_BUTCHER_SHOP, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(DESERT_CARTOGRAPHER_HOUSE, TILE_VILLAGE_LIBRARY);
		builder.put(DESERT_SMALL_FARM, TILE_VILLAGE_FARMLAND_SMALL);
		builder.put(DESERT_MEDIUM_FARM, TILE_VILLAGE_FARMLAND_SMALL);
		builder.put(DESERT_FISHER_COTTAGE, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(DESERT_FLETCHER_HOUSE, TILE_VILLAGE_CHURCH);
		builder.put(DESERT_LARGE_FARM, TILE_VILLAGE_FARMLAND_LARGE);
		builder.put(DESERT_LIBRARY, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(DESERT_MASON_HOUSE, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(DESERT_MEDIUM_HOUSE, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(DESERT_LARGE_HOUSE, TILE_VILLAGE_HOUSE);
		builder.put(DESERT_SHEPHERDS_HOUSE, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(DESERT_SMALL_HOUSE_1, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(DESERT_SMALL_HOUSE_2, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(DESERT_SMALL_HOUSE_3, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(DESERT_SMALL_HOUSE_4, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(DESERT_SMALL_HOUSE_5, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(DESERT_SMALL_HOUSE_6, TILE_VILLAGE_CHURCH);
		builder.put(DESERT_SMALL_HOUSE_7, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(DESERT_SMALL_HOUSE_8, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(DESERT_TANNERY, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(DESERT_TEMPLE_1, TILE_VILLAGE_CHURCH);
		builder.put(DESERT_TEMPLE_2, TILE_VILLAGE_CHURCH);
		builder.put(DESERT_TOOL_SMITHY, TILE_VILLAGE_SMITHY);
		builder.put(DESERT_WEAPON_SMITHY, TILE_VILLAGE_SMITHY);
		builder.put(DESERT_ROAD_DECOR, TILE_VILLAGE_TORCH);
		builder.put(DESERT_ROAD_TERRACOTTA_ACCENT_1, TILE_VILLAGE_TORCH);
		builder.put(DESERT_ROAD_TERRACOTTA_ACCENT_2, TILE_VILLAGE_TORCH);
		builder.put(DESERT_ROAD_DECOR_3, TILE_VILLAGE_TORCH);
		
		// Taiga
		builder.put(TAIGA_ANIMAL_PEN, TILE_VILLAGE_FENCE);
		builder.put(TAIGA_ARMORER_STATION, TILE_VILLAGE_HUT);
		builder.put(TAIGA_ARMORER_HOUSE, TILE_VILLAGE_SMITHY);
		builder.put(TAIGA_BUTCHER_SHOP, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(TAIGA_CARTOGRAPHER_HOUSE, TILE_VILLAGE_LIBRARY);
		builder.put(TAIGA_FISHER_COTTAGE, TILE_VILLAGE_HUT);
		builder.put(TAIGA_FLETCHER_HOUSE, TILE_VILLAGE_L_HOUSE);
		builder.put(TAIGA_LARGE_FARM, TILE_VILLAGE_FARMLAND_LARGE);
		builder.put(TAIGA_MEDIUM_FARM, TILE_VILLAGE_FARMLAND_SMALL);
		builder.put(TAIGA_LIBRARY, TILE_VILLAGE_LIBRARY);
		builder.put(TAIGA_MASON_HOUSE, TILE_VILLAGE_L_HOUSE);
		builder.put(TAIGA_MEDIUM_HOUSE_1, TILE_VILLAGE_HOUSE);
		builder.put(TAIGA_MEDIUM_HOUSE_2, TILE_VILLAGE_L_HOUSE);
		builder.put(TAIGA_MEDIUM_HOUSE_3, TILE_VILLAGE_L_HOUSE);
		builder.put(TAIGA_MEDIUM_HOUSE_4, TILE_VILLAGE_HOUSE);
		builder.put(TAIGA_SHEPHERD_HOUSE, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(TAIGA_SMALL_FARM, TILE_VILLAGE_FARMLAND_SMALL);
		builder.put(TAIGA_SMALL_HOUSE_1, TILE_VILLAGE_HUT);
		builder.put(TAIGA_SMALL_HOUSE_2, TILE_VILLAGE_HUT);
		builder.put(TAIGA_SMALL_HOUSE_3, TILE_VILLAGE_HUT);
		builder.put(TAIGA_SMALL_HOUSE_4, TILE_VILLAGE_L_HOUSE);
		builder.put(TAIGA_SMALL_HOUSE_5, TILE_VILLAGE_HUT);
		builder.put(TAIGA_TANNERY, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(TAIGA_TEMPLE, TILE_VILLAGE_CHURCH);
		builder.put(TAIGA_TOOL_SMITHY, TILE_VILLAGE_SMITHY);
		builder.put(TAIGA_WEAPON_SMITH_HOUSE, TILE_VILLAGE_SMITHY);
		builder.put(TAIGA_WEAPON_SMITH_STATION, TILE_VILLAGE_HUT);
		builder.put(TAIGA_ROAD_DECOR, TILE_VILLAGE_TORCH);
		
		// Savanna
		builder.put(SAVANNA_COVERED_ANIMAL_PEN, TILE_VILLAGE_FENCE);
		builder.put(SAVANNA_LARGE_ANIMAL_PEN, TILE_VILLAGE_FENCE);
		builder.put(SAVANNA_MEDIUM_ANIMAL_PEN, TILE_VILLAGE_FENCE);
		builder.put(SAVANNA_ARMORER_HOUSE, TILE_VILLAGE_SMITHY);
		builder.put(SAVANNA_BUTCHER_SHOP_1, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(SAVANNA_BUTCHER_SHOP_2, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(SAVANNA_CARTOGRAPHER, TILE_VILLAGE_LIBRARY);
		builder.put(SAVANNA_FISHER_COTTAGE, TILE_VILLAGE_HUT);
		builder.put(SAVANNA_FLETCHER_HOUSE, TILE_VILLAGE_L_HOUSE);
		builder.put(SAVANNA_METHODICAL_FARM, TILE_VILLAGE_FARMLAND_LARGE);
		builder.put(SAVANNA_HAPHAZARD_FARM, TILE_VILLAGE_FARMLAND_SMALL);
		builder.put(SAVANNA_LIBRARY, TILE_VILLAGE_LIBRARY);
		builder.put(SAVANNA_MASON_HOUSE, TILE_VILLAGE_L_HOUSE);
		builder.put(SAVANNA_MEDIUM_HOUSE_1, TILE_VILLAGE_HUT);
		builder.put(SAVANNA_MEDIUM_HOUSE_2, TILE_VILLAGE_L_HOUSE);
		builder.put(SAVANNA_SHEPHERD_HOUSE, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(SAVANNA_SMALL_FARM, TILE_VILLAGE_FARMLAND_SMALL);
		builder.put(SAVANNA_SMALL_HOUSE_1, TILE_VILLAGE_HUT);
		builder.put(SAVANNA_SMALL_HOUSE_2, TILE_VILLAGE_HUT);
		builder.put(SAVANNA_SMALL_HOUSE_3, TILE_VILLAGE_HUT);
		builder.put(SAVANNA_SMALL_HOUSE_4, TILE_VILLAGE_L_HOUSE);
		builder.put(SAVANNA_SMALL_HOUSE_5, TILE_VILLAGE_CHURCH);
		builder.put(SAVANNA_SMALL_HOUSE_6, TILE_VILLAGE_HUT);
		builder.put(SAVANNA_SMALL_HOUSE_7, TILE_VILLAGE_HUT);
		builder.put(SAVANNA_SMALL_HOUSE_8, TILE_VILLAGE_HUT);
		builder.put(SAVANNA_TANNERY, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(SAVANNA_TEMPLE_1, TILE_VILLAGE_CHURCH);
		builder.put(SAVANNA_TEMPLE_2, TILE_VILLAGE_CHURCH);
		builder.put(SAVANNA_TOOL_SMITHY, TILE_VILLAGE_SMITHY);
		builder.put(SAVANNA_SMALL_WEAPON_SMITHY, TILE_VILLAGE_SMITHY);
		builder.put(SAVANNA_LARGE_WEAPON_SMITHY, TILE_VILLAGE_SMITHY);
		builder.put(SAVANNA_ROAD_DECOR, TILE_VILLAGE_TORCH);
		builder.put(SAVANNA_ROADSIDE_FARM_1, TILE_VILLAGE_FARMLAND_SMALL);
		builder.put(SAVANNA_ROADSIDE_FARM_2, TILE_VILLAGE_FARMLAND_LARGE);
		builder.put(SAVANNA_ROADSIDE_FARM_3, TILE_VILLAGE_FARMLAND_SMALL);
		builder.put(SAVANNA_ROADSIDE_FARM_4, TILE_VILLAGE_FARMLAND_LARGE);
		
		// Snowy
		builder.put(SNOWY_ANIMAL_PEN_1, TILE_VILLAGE_FENCE);
		builder.put(SNOWY_ANIMAL_PEN_2, TILE_VILLAGE_FENCE);
		builder.put(SNOWY_ARMORER_HOUSE_1, TILE_VILLAGE_SMITHY);
		builder.put(SNOWY_ARMORER_HOUSE_2, TILE_VILLAGE_SMITHY);
		builder.put(SNOWY_BUTCHER_HOUSE, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(SNOWY_BUTCHER_IGLOO, TILE_VILLAGE_HUT);
		builder.put(SNOWY_CARTOGRAPHER_HOUSE, TILE_VILLAGE_LIBRARY);
		builder.put(SNOWY_SQUARE_FARM, TILE_VILLAGE_FARMLAND_SMALL);
		builder.put(SNOWY_PATCH_FARM, TILE_VILLAGE_FARMLAND_SMALL);
		builder.put(SNOWY_FISHER_COTTAGE, TILE_VILLAGE_HUT);
		builder.put(SNOWY_FLETCHER_HOUSE, TILE_VILLAGE_L_HOUSE);
		builder.put(SNOWY_LIBRARY, TILE_VILLAGE_LIBRARY);
		builder.put(SNOWY_MASON_HOUSE_1, TILE_VILLAGE_L_HOUSE);
		builder.put(SNOWY_MASON_HOUSE_2, TILE_VILLAGE_L_HOUSE);
		builder.put(SNOWY_MEDIUM_HOUSE_1, TILE_VILLAGE_HUT);
		builder.put(SNOWY_MEDIUM_HOUSE_2, TILE_VILLAGE_HOUSE);
		builder.put(SNOWY_MEDIUM_HOUSE_3, TILE_VILLAGE_HUT);
		builder.put(SNOWY_SHEPHERD_HOUSE, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(SNOWY_SMALL_HOUSE_1, TILE_VILLAGE_HUT);
		builder.put(SNOWY_SMALL_HOUSE_2, TILE_VILLAGE_HUT);
		builder.put(SNOWY_SMALL_HOUSE_3, TILE_VILLAGE_HOUSE);
		builder.put(SNOWY_SMALL_HOUSE_4, TILE_VILLAGE_HUT);
		builder.put(SNOWY_SMALL_HOUSE_5, TILE_VILLAGE_HUT);
		builder.put(SNOWY_SMALL_HOUSE_6, TILE_VILLAGE_HUT);
		builder.put(SNOWY_SMALL_HOUSE_7, TILE_VILLAGE_HUT);
		builder.put(SNOWY_SMALL_HOUSE_8, TILE_VILLAGE_HUT);
		builder.put(SNOWY_TANNERY, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(SNOWY_TEMPLE, TILE_VILLAGE_CHURCH);
		builder.put(SNOWY_TOOL_SMITHY, TILE_VILLAGE_SMITHY);
		builder.put(SNOWY_WEAPON_SMITHY, TILE_VILLAGE_SMITHY);
		builder.put(SNOWY_ROAD_DECOR, TILE_VILLAGE_TORCH);
		
		// Jungle
		builder.put(JUNGLE_ARMORER_HOUSE, TILE_VILLAGE_HUT);
		builder.put(JUNGLE_BUTCHER_SHOP, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(JUNGLE_CARTOGRAPHER_HOUSE_1, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(JUNGLE_CARTOGRAPHER_HOUSE_2, TILE_VILLAGE_L_HOUSE);
		builder.put(JUNGLE_FISHER_COTTAGE, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(JUNGLE_FLETCHER_HOUSE_1, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(JUNGLE_FLETCHER_HOUSE_2, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(JUNGLE_MASON_HOUSE, TILE_VILLAGE_SMITHY);
		builder.put(JUNGLE_LARGE_HOUSE, TILE_VILLAGE_HOUSE);
		builder.put(JUNGLE_LIBRARY, TILE_VILLAGE_LIBRARY);
		builder.put(JUNGLE_MEDIUM_HOUSE_1, TILE_VILLAGE_HOUSE);
		builder.put(JUNGLE_MEDIUM_HOUSE_2, TILE_VILLAGE_HOUSE);
		builder.put(JUNGLE_MEDIUM_HOUSE_3, TILE_VILLAGE_HUT);
		builder.put(JUNGLE_MEDIUM_HOUSE_4, TILE_VILLAGE_HOUSE);
		builder.put(JUNGLE_SHEPHERD_HOUSE, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(JUNGLE_SMALL_HOUSE_1, TILE_VILLAGE_L_HOUSE);
		builder.put(JUNGLE_SMALL_HOUSE_2, TILE_VILLAGE_L_HOUSE);
		builder.put(JUNGLE_SMALL_HOUSE_3, TILE_VILLAGE_HUT);
		builder.put(JUNGLE_SMALL_HOUSE_4, TILE_VILLAGE_HUT);
		builder.put(JUNGLE_SMALL_HOUSE_5, TILE_VILLAGE_HUT);
		builder.put(JUNGLE_SMALL_HOUSE_6, TILE_VILLAGE_HUT);
		builder.put(JUNGLE_SMALL_HOUSE_7, TILE_VILLAGE_HUT);
		builder.put(JUNGLE_SMALL_HOUSE_8, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(JUNGLE_STABLE, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(JUNGLE_STEPPED_FARM, TILE_VILLAGE_FARMLAND_LARGE);
		builder.put(JUNGLE_STONE_ANIMAL_PEN, TILE_VILLAGE_FENCE);
		builder.put(JUNGLE_TAMED_FARM, TILE_VILLAGE_FARMLAND_LARGE);
		builder.put(JUNGLE_TANNERY_1, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(JUNGLE_TANNERY_2, TILE_VILLAGE_HUT);
		builder.put(JUNGLE_TEMPLE, TILE_VILLAGE_CHURCH);
		builder.put(JUNGLE_TOOL_SMITHY_1, TILE_VILLAGE_HUT);
		builder.put(JUNGLE_TOOL_SMITHY_2, TILE_VILLAGE_SMITHY);
		builder.put(JUNGLE_WEAPON_SMITHY, TILE_VILLAGE_SMITHY);
		builder.put(JUNGLE_WILD_FARM, TILE_VILLAGE_FARMLAND_LARGE);
		builder.put(JUNGLE_WOOD_ANIMAL_PEN, TILE_VILLAGE_FENCE);
		builder.put(JUNGLE_ROAD_DECOR, TILE_VILLAGE_TORCH);
		builder.put(JUNGLE_ROAD_ACCENT_1, TILE_VILLAGE_WELL);
		builder.put(JUNGLE_ROAD_ACCENT_2, TILE_VILLAGE_TREE);
		
		// Swamp
		builder.put(SWAMP_ANIMAL_PEN_1, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(SWAMP_ANIMAL_PEN_2, TILE_VILLAGE_FENCE);
		builder.put(SWAMP_ARMORER_HOUSE, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(SWAMP_BUTCHER_SHOP, TILE_VILLAGE_HUT);
		builder.put(SWAMP_CARTOGRAPHER_HOUSE, TILE_VILLAGE_LIBRARY);
		builder.put(SWAMP_FISHER_COTTAGE_1, TILE_VILLAGE_HOUSE);
		builder.put(SWAMP_FISHER_COTTAGE_2, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(SWAMP_FLETCHER_HOUSE, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(SWAMP_HUT_FARM, TILE_VILLAGE_FARMLAND_SMALL);
		builder.put(SWAMP_LARGE_HOUSE, TILE_VILLAGE_HOUSE);
		builder.put(SWAMP_LIBRARY, TILE_VILLAGE_LIBRARY);
		builder.put(SWAMP_MASON_HOUSE, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(SWAMP_MEDIUM_HOUSE_1, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(SWAMP_MEDIUM_HOUSE_2, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(SWAMP_SHEPHERD_HOUSE_1, TILE_VILLAGE_HOUSE);
		builder.put(SWAMP_SHEPHERD_HOUSE_2, TILE_VILLAGE_HUT);
		builder.put(SWAMP_SMALL_HOUSE_1, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(SWAMP_SMALL_HOUSE_2, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(SWAMP_SMALL_HOUSE_3, TILE_VILLAGE_HUT);
		builder.put(SWAMP_SMALL_HOUSE_4, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(SWAMP_SMALL_HOUSE_5, TILE_VILLAGE_SMALL_HOUSE);
		builder.put(SWAMP_STABLE, TILE_VILLAGE_BUTCHERS_SHOP);
		builder.put(SWAMP_TANNERY, TILE_VILLAGE_L_HOUSE);
		builder.put(SWAMP_TEMPLE, TILE_VILLAGE_CHURCH);
		builder.put(SWAMP_TOOL_SMITHY, TILE_VILLAGE_HOUSE);
		builder.put(SWAMP_WEAPON_SMITHY, TILE_VILLAGE_SMITHY);
		builder.put(SWAMP_WILD_FARM, TILE_VILLAGE_FARMLAND_LARGE);
		builder.put(SWAMP_ROAD_DECOR, TILE_VILLAGE_TORCH);
		builder.put(SWAMP_ROAD_ACCENT, TILE_VILLAGE_TORCH);
		
		COMPONENT_TO_TILE_MAP = builder.build();
	}
	
	// Associates AA map icon with a priority level
	/** Tiles with the higher priority override tiles with lower priority at the same chunk. */
	private static final Map<String, Integer> TILE_TO_PRIORITY_MAP;
	static {
		ImmutableMap.Builder<String, Integer> builder = new Builder<String, Integer>();
		
		// === LEGACY VILLAGE ICONS === //
//		builder.put(TILE_VILLAGE_LIBRARY, 5);
//		builder.put(TILE_VILLAGE_SMITHY, 6);
//		builder.put(TILE_VILLAGE_L_HOUSE, 5);
//		builder.put(TILE_VILLAGE_FARMLAND_LARGE, 3);
//		builder.put(TILE_VILLAGE_FARMLAND_SMALL, 3);
//		builder.put(TILE_VILLAGE_PATH_X, 0);
//		builder.put(TILE_VILLAGE_PATH_Z, 0);
//		builder.put(TILE_VILLAGE_TORCH, 1);
//		builder.put(TILE_VILLAGE_WELL, 7);
//		builder.put(TILE_VILLAGE_BUTCHERS_SHOP, 4);
//		builder.put(TILE_VILLAGE_SMALL_HOUSE, 4);
//		builder.put(TILE_VILLAGE_HUT, 3);
//		builder.put(TILE_VILLAGE_CHURCH, 6);
		
		// Custom-registered tiles
		builder.put(TILE_VILLAGE_WELL, 7);
		builder.put(TILE_VILLAGE_TREE, 7);
		builder.put(TILE_VILLAGE_CHURCH, 7);
		builder.put(TILE_VILLAGE_HOUSE, 7);
		builder.put(TILE_VILLAGE_L_HOUSE, 6);
		builder.put(TILE_VILLAGE_LIBRARY, 6);
		builder.put(TILE_VILLAGE_SMITHY, 6);
		builder.put(TILE_VILLAGE_BUTCHERS_SHOP, 6);
		builder.put(TILE_VILLAGE_SMALL_HOUSE, 5);
		builder.put(TILE_VILLAGE_HUT, 4);
		builder.put(TILE_VILLAGE_FARMLAND_LARGE, 3);
		builder.put(TILE_VILLAGE_FARMLAND_SMALL, 3);
		builder.put(TILE_VILLAGE_FENCE, 2);
		builder.put(TILE_VILLAGE_TORCH, 1);
		
		TILE_TO_PRIORITY_MAP = builder.build();
	}
	
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void onWorldLoad(WorldEvent.Load event)
	{
		if (!event.world.isRemote && event.world.provider.dimensionId == 0)
		{
			visitAllUnvisitedVillages(event.world);
		}
	}
	
	@SubscribeEvent
	public void onPopulateChunk(PopulateChunkEvent.Post event)
	{
		if (!event.world.isRemote && event.world.provider.dimensionId == 0)
		{
			visitAllUnvisitedVillages(event.world);
		}
	}
	
	public void visitAllUnvisitedVillages(World world)
	{
		MapGenStructureData data = (MapGenStructureData)world.perWorldStorage.loadData(MapGenStructureData.class, "Village");
		
		if (data == null) return;
		
		NBTTagCompound villageNBTData = data.func_143041_a();
		@SuppressWarnings("unchecked")
		Set<String> tagSet = villageNBTData.func_150296_c();
		
		for (String coords : tagSet)
		{
			if (!visited.contains(coords))
			{
				NBTBase tag = villageNBTData.getTag(coords);
				if (tag.getId() == 10)  // is NBTTagCompound
				{
					visitVillage(world, (NBTTagCompound) tag);
					visited.add(coords);
				}
			}
		}
	}
	
	/** Put all child parts of the village on the map as global custom tiles. */
	private void visitVillage(World world, NBTTagCompound tag)
	{
		if (!VillageGeneratorConfigHandler.newVillageGenerator) {return;} // Don't do any of this if you're not using the new village generator
		
		if (!tag.getBoolean("Valid"))
		{
			// The village was not actually generated and should not be mapped.
			// Remove legacy marker and custom tile:
			removeVillage(world, tag);
			return;
		}
		
		int startChunkX = tag.getInteger("ChunkX");
		int startChunkZ = tag.getInteger("ChunkZ");
		
		if (GeneralConfig.debugMessages)
		{
			LogHelper.info(
					"Visiting NPC Village in dimension #"+world.provider.dimensionId+" \""+world.provider.getDimensionName()+
					"\" at chunk ("+startChunkX+", "+startChunkZ+") ~ blocks ("+(startChunkX << 4)+", "+(startChunkZ << 4)+")"
					);
		}
		
		NBTTagList children = tag.getTagList("Children", 10);
		for (int i = 0; i < children.tagCount(); i++)
		{
			NBTTagCompound child = children.getCompoundTagAt(i);
			String childID = child.getString("id");
			
			StructureBoundingBox boundingBox = new StructureBoundingBox(child.getIntArray("BB"));
			int x = boundingBox.getCenterX();
			int y = boundingBox.getCenterY();
			int z = boundingBox.getCenterZ();
			int chunkX = x >> 4;
			int chunkZ = z >> 4;
			
			// Detect town center and generate a marker
			if (isTownCenter(childID))
			{
				// Put marker at Start.
				// Check if the marker already exists:
				boolean foundMarker = false;
				// Legacy support: don't place new marker if there's already one in a wider area.
				for (int j = -1; j <= 1; j++)
				{
					for (int k = -1; k <= 1; k++)
					{
						List<Marker> markers = AntiqueAtlasMod.globalMarkersData.getData().getMarkersAtChunk(world.provider.dimensionId, j + chunkX / MarkersData.CHUNK_STEP, k + chunkZ / MarkersData.CHUNK_STEP);
						
						if (markers != null)
						{
							for (Marker marker : markers)
							{
								if (marker.getType().equals(VILLAGE_MARKER))
								{
									foundMarker = true;
									break;
								}
							}
						}
					}
				}
				if (!foundMarker && AntiqueAtlasMod.settings.autoVillageMarkers)
				{
					String aaMArkerName;
					
					if (GeneralConfig.antiqueAtlasMarkerNames)
					{
						// Get the name of the village
						NBTTagCompound villageNBTtag = StructureVillageVN.getOrMakeVNInfo(world, x, y, z);
						
						String namePrefix = villageNBTtag.getString("namePrefix");
						String nameRoot = villageNBTtag.getString("nameRoot");
						String nameSuffix = villageNBTtag.getString("nameSuffix");
						
						aaMArkerName = (namePrefix + " "+  nameRoot +" "+  nameSuffix).trim();
					}
					else // Use the unlocalized word "Village"
					{
						aaMArkerName = "gui.antiqueatlas.marker.village";
					}
					
					AtlasAPI.markers.putGlobalMarker(world, false, VILLAGE_MARKER, aaMArkerName, x, z);
				}
			}
			
//			String tileName = null;
//			if (PATH.equals(childID)) {
//				int orientation = child.getInteger("O");
//				switch (orientation) {
//				case 0:
//				case 2: tileName = TILE_VILLAGE_PATH_Z; break;
//				case 1:
//				case 3: tileName = TILE_VILLAGE_PATH_X; break;
//				}
//			} else {
//			}
			
			// Add tile to map
			String tileName = COMPONENT_TO_TILE_MAP.get(childID);
			
			if (tileName != null)
			{
				Integer curTilePriority = TILE_TO_PRIORITY_MAP.get(tileName);
				Integer prevTilePriority = TILE_TO_PRIORITY_MAP.get(tileAt(chunkX, chunkZ));
				if (curTilePriority != null && prevTilePriority != null)
				{
					if (curTilePriority >= prevTilePriority)
					{
						//AtlasAPI.tiles.putCustomGlobalTile(world, tileName, chunkX, chunkZ);
						AtlasAPI.getTileAPI().putCustomGlobalTile(world, tileName, chunkX, chunkZ);
					}
				}
				else
				{
					//AtlasAPI.tiles.putCustomGlobalTile(world, tileName, chunkX, chunkZ);
					AtlasAPI.getTileAPI().putCustomGlobalTile(world, tileName, chunkX, chunkZ);
				}
			}
		}
	}
	
	private static String tileAt(int chunkX, int chunkZ) {
		int biomeID = AntiqueAtlasMod.extBiomeData.getData().getBiomeIdAt(0, chunkX, chunkZ);
		return ExtTileIdMap.instance().getPseudoBiomeName(biomeID);
	}
	
	/** Delete the marker and custom tile data about the village. */
	private static void removeVillage(World world, NBTTagCompound tag)
	{
		NBTTagList children = tag.getTagList("Children", 10);
		
		for (int i = 0; i < children.tagCount(); i++)
		{
			NBTTagCompound child = children.getCompoundTagAt(i);
			String childID = child.getString("id");
			
			StructureBoundingBox boundingBox = new StructureBoundingBox(child.getIntArray("BB"));
			int x = boundingBox.getCenterX();
			int z = boundingBox.getCenterZ();
			int chunkX = x >> 4;
			int chunkZ = z >> 4;
			
			if (isTownCenter(childID))
			{
				List<Marker> markers = AntiqueAtlasMod.globalMarkersData.getData().getMarkersAtChunk(world.provider.dimensionId, chunkX / MarkersData.CHUNK_STEP, chunkZ / MarkersData.CHUNK_STEP);
				
				if (markers != null)
				{
					for (Marker marker : markers)
					{
						if (marker.getType().equals(VILLAGE_MARKER))
						{
							AtlasAPI.markers.deleteGlobalMarker(world, marker.getId());
							if (GeneralConfig.debugMessages) {LogHelper.info("Removed faux village marker at x=" + (chunkX<<4) + " z=" + (chunkZ<<4));}
							break;
						}
					}
				}
			}
			
			AtlasAPI.tiles.deleteCustomGlobalTile(world, chunkX, chunkZ);
			if (GeneralConfig.debugMessages) {LogHelper.info("Removed faux village tile at x=" + (chunkX<<4) + " z=" + (chunkZ<<4));}
		}
	}
	
	public static boolean isTownCenter(String childID)
	{
		return
				PLAINS_FOUNTAIN.equals(childID)
				|| PLAINS_WELL.equals(childID)
				|| PLAINS_MARKET.equals(childID)
				|| PLAINS_CENTRAL_TREE.equals(childID)
				|| DESERT_FOUNTAIN.equals(childID)
				|| DESERT_WELL.equals(childID)
				|| DESERT_MARKET.equals(childID)
				|| TAIGA_GRASSY_CENTER.equals(childID)
				|| TAIGA_WELL.equals(childID)
				|| SAVANNA_MARKET.equals(childID)
				|| SAVANNA_FOUNTAIN.equals(childID)
				|| SAVANNA_DOUBLE_WELL.equals(childID)
				|| SAVANNA_SINGLE_WELL.equals(childID)
				|| SNOWY_ICE_SPIRE.equals(childID)
				|| SNOWY_FOUNTAIN.equals(childID)
				|| SNOWY_PAVILION.equals(childID)
				|| JUNGLE_STATUE.equals(childID)
				|| JUNGLE_TREE.equals(childID)
				|| JUNGLE_GARDEN.equals(childID)
				|| JUNGLE_VILLA.equals(childID)
				|| SWAMP_WILLOW.equals(childID)
				|| SWAMP_STATUE.equals(childID)
				|| SWAMP_PAVILION.equals(childID)
				|| SWAMP_MONOLITH.equals(childID)
				;
	}
}
