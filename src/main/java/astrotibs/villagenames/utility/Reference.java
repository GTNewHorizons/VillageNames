package astrotibs.villagenames.utility;

import cpw.mods.fml.common.Loader;

public class Reference
{
	// Contains common constants for the mod
	public static final String MOD_ID = "VillageNames";
	public static final String MOD_NAME = "Village Names";
	public static final String VERSION = "4.1.1b";
	public static final String URL = "https://www.curseforge.com/minecraft/mc-mods/village-names";
	public static final String VERSION_CHECKER_URL = "https://gitgud.io/AstroTibs/VillageNames/raw/1.7.10/CURRENT_VERSION";
	public static final String MOD_CHANNEL = "vnChannel";
	public static final String CLIENT_PROXY = "astrotibs.villagenames.proxy.ClientProxy";
	public static final String SERVER_PROXY = "astrotibs.villagenames.proxy.ServerProxy";
	public static final String COMMON_PROXY = "astrotibs.villagenames.proxy.CommonProxy";
	public static final String GUI_FACTORY = "astrotibs.villagenames.config.gui.VNGuiFactory";
    
	// Mod variables
	public static boolean isMFQMloaded = Loader.isModLoaded("MFQM");
	
    // Elder Guardian class path: prior to 1.11, the Elder is just a normal Guardian with an "Elder" flag turned on.
    // This helps distinguish the two via hard-coding a faux class path
    public static final String ELDER_GUARDIAN_CLASS = "astrotibs.villagenames.prismarine.guardian.entity.monster.EntityElderGuardian";
    public static final String VILLAGER_CLASS = "net.minecraft.entity.passive.EntityVillager";
    public static final int STREET_WIDTH = 3;
	public static final double SPAWN_BLOCK_OFFSET = 0.5D; // If you obtained the spawn x,y,z as ints, add this offset to x and z to ensure it's in the center of the block.
	public static final String MOB_GUARDIAN_VN = "Guardian";
	public static final String ELDER_GEN = "ElderGen"; // The old version of Elder generation for when Guardians were first introduced
	public static final String ELDER_GEN_VN4 = "ElderGen_VN4"; // Regenerating Elder Guardians to account for new entity registry in Village Names 4
	
	// Vanilla village component class paths
	public static final String House4Garden_CLASS = "net.minecraft.world.gen.structure.StructureVillagePieces$House4Garden";
	public static final String Church_CLASS = "net.minecraft.world.gen.structure.StructureVillagePieces$Church";
	public static final String House1_CLASS = "net.minecraft.world.gen.structure.StructureVillagePieces$House1";
	public static final String WoodHut_CLASS = "net.minecraft.world.gen.structure.StructureVillagePieces$WoodHut";
	public static final String Hall_CLASS = "net.minecraft.world.gen.structure.StructureVillagePieces$Hall";
	public static final String Field1_CLASS = "net.minecraft.world.gen.structure.StructureVillagePieces$Field1";
	public static final String Field2_CLASS = "net.minecraft.world.gen.structure.StructureVillagePieces$Field2";
	public static final String House2_CLASS = "net.minecraft.world.gen.structure.StructureVillagePieces$House2";
	public static final String House3_CLASS = "net.minecraft.world.gen.structure.StructureVillagePieces$House3";
	
    // Config values
    public static final String FOLDER_NAMEPIECES = "namepieces";
    public static final String FOLDER_NEWVILLAGES = "newvillages";
    public static final String CATEGORY_VILLAGE_GENERATOR = "Village Generator";
    public static final String VN_BUILDING_CLASSPATH_STUB = "astrotibs.villagenames.village.biomestructures.";
    public static final String PLAINS_BUILDING_STUB =  "PlainsStructures$Plains";
    public static final String DESERT_BUILDING_STUB =  "DesertStructures$Desert";
    public static final String TAIGA_BUILDING_STUB =   "TaigaStructures$Taiga";
    public static final String SAVANNA_BUILDING_STUB = "SavannaStructures$Savanna";
    public static final String SNOWY_BUILDING_STUB =   "SnowyStructures$Snowy";
}
