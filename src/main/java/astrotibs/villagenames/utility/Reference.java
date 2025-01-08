package astrotibs.villagenames.utility;

import net.minecraft.util.EnumChatFormatting;

public class Reference {

    // Contains common constants for the mod
    public static final String MOD_ID = "VillageNames";
    public static final String MOD_NAME = "Village Names";
    public static final String MOD_NAME_COLORIZED = EnumChatFormatting.GOLD + MOD_NAME;
    public static final String VERSION = "@VERSION@";
    public static final String URL = "https://modrinth.com/mod/village-names";
    public static final String VERSION_CHECKER_URL = "https://gitgud.io/AstroTibs/VillageNames/-/raw/1.7.10/CURRENT_VERSION";
    public static final String MOD_CHANNEL = "vnChannel";
    public static final String CLIENT_PROXY = "astrotibs.villagenames.proxy.ClientProxy";
    public static final String SERVER_PROXY = "astrotibs.villagenames.proxy.ServerProxy";
    public static final String COMMON_PROXY = "astrotibs.villagenames.proxy.CommonProxy";
    public static final String GUI_FACTORY = "astrotibs.villagenames.config.gui.VNGuiFactory";

    // Mod variables
    public static final String MORE_FUN_QUICKSAND_MOD_MODID = "MFQM", ANTIQUE_ATLAS_MODID = "antiqueatlas",
        NAME_TIBS = "tibs", NAME_ASTROTIBS = "astrotibs", NAME_TIBS_OPENP = "tibs (",
        NAME_ASTROTIBS_OPENP = "astrotibs (";

    // Elder Guardian class path: prior to 1.11, the Elder is just a normal Guardian with an "Elder" flag turned on.
    // This helps distinguish the two via hard-coding a faux class path
    public static final String ELDER_GUARDIAN_CLASS = "astrotibs.villagenames.prismarine.guardian.entity.monster.EntityElderGuardian";
    public static final String VILLAGER_CLASS = "net.minecraft.entity.passive.EntityVillager";
    public static final int STREET_WIDTH = 3;
    public static final double SPAWN_BLOCK_OFFSET = 0.5D; // If you obtained the spawn x,y,z as ints, add this offset to
                                                          // x and z to ensure it's in the center of the block.
    public static final String MOB_GUARDIAN_VN = "Guardian";
    public static final String ELDER_GEN = "ElderGen"; // The old version of Elder generation for when Guardians were
                                                       // first introduced
    public static final String ELDER_GEN_VN4 = "ElderGen_VN4"; // Regenerating Elder Guardians to account for new entity
                                                               // registry in Village Names 4

    // Vanilla village component class paths
    public static final String House4Garden_CLASS = "net.minecraft.world.gen.structure.StructureVillagePieces$House4Garden",
        Church_CLASS = "net.minecraft.world.gen.structure.StructureVillagePieces$Church",
        House1_CLASS = "net.minecraft.world.gen.structure.StructureVillagePieces$House1",
        WoodHut_CLASS = "net.minecraft.world.gen.structure.StructureVillagePieces$WoodHut",
        Hall_CLASS = "net.minecraft.world.gen.structure.StructureVillagePieces$Hall",
        Field1_CLASS = "net.minecraft.world.gen.structure.StructureVillagePieces$Field1",
        Field2_CLASS = "net.minecraft.world.gen.structure.StructureVillagePieces$Field2",
        House2_CLASS = "net.minecraft.world.gen.structure.StructureVillagePieces$House2",
        House3_CLASS = "net.minecraft.world.gen.structure.StructureVillagePieces$House3";

    // Config values
    public static final String FOLDER_NAMEPIECES = "namepieces", FOLDER_NEWVILLAGES = "newvillages",

        // Main config
        CATEGORY_GENERAL = "general", CATEGORY_VILLAGER_PROFESSIONS = "villager professions",
        CATEGORY_WELL_KILL_SWITCH = "well kill switch", CATEGORY_WORLD_OF_COLOR = "world of color",
        CATEGORY_VILLAGER_SKIN_TONES = "villager skin tones", CATEGORY_MISCELLANEOUS = "miscellaneous",
        CATEGORY_NAMING = "naming", CATEGORY_MOD_INTEGRATION = "mod integration",
        CATEGORY_ZOMBIE_CONVERSION = "zombie conversion",

        // Syllable pools
        SYL_SUF = " syllable pool", CATEGORY_VILLAGER_SYLLABLE_POOL = "villager" + SYL_SUF,
        CATEGORY_VILLAGE_SYLLABLE_POOL = "village" + SYL_SUF, CATEGORY_TEMPLE_SYLLABLE_POOL = "temple" + SYL_SUF,
        CATEGORY_MINESHAFT_SYLLABLE_POOL = "mineshaft" + SYL_SUF,
        CATEGORY_FORTRESS_SYLLABLE_POOL = "fortress" + SYL_SUF,
        CATEGORY_STRONGHOLD_SYLLABLE_POOL = "stronghold" + SYL_SUF,
        CATEGORY_MONUMENT_SYLLABLE_POOL = "monument" + SYL_SUF, CATEGORY_END_CITY_SYLLABLE_POOL = "end city" + SYL_SUF,
        CATEGORY_MANSION_SYLLABLE_POOL = "mansion" + SYL_SUF, CATEGORY_GOLEM_SYLLABLE_POOL = "golem" + SYL_SUF,
        CATEGORY_PET_SYLLABLE_POOL = "pet" + SYL_SUF, CATEGORY_DRAGON_SYLLABLE_POOL = "dragon" + SYL_SUF,
        CATEGORY_ANGEL_SYLLABLE_POOL = "angel" + SYL_SUF, CATEGORY_DEMON_SYLLABLE_POOL = "demon" + SYL_SUF,
        CATEGORY_GOBLIN_SYLLABLE_POOL = "goblin" + SYL_SUF, CATEGORY_ALIEN_SYLLABLE_POOL = "alien" + SYL_SUF,
        CATEGORY_ALIEN_VILLAGE_SYLLABLE_POOL = "alien village" + SYL_SUF,
        CATEGORY_CUSTOM_SYLLABLE_POOL = "custom" + SYL_SUF,

        NAME_PREFIXES = "Prefixes", NAME_ROOT_INITIAL = "Root: Initial", NAME_ROOT_SYLLABLES = "Root: Syllables",
        NAME_ROOT_TERMINAL = "Root: Terminal", NAME_SUFFIXES = "Suffixes",
        NAME_SYLLABLE_COUNT_WEIGHTING = "Syllable Count Weighting",
        NAME_TERMINAL_BLANK_COUNTS = "Terminal Blank Counts", NAME_PREFIX_CHANCE = "Prefix Chance",
        NAME_SUFFIX_CHANCE = "Suffix Chance",

        DESCRIPTION_PREFIXES = "Prefixes that can occur before the core name.",
        DESCRIPTION_ROOT_INITIAL = "Core names begin with one of these half-syllables. Use _ to represent a space.",
        DESCRIPTION_ROOT_SYLLABLES = "Core names insert zero or more of these elements to build to their target lengths. Use _ to represent a space, and ^ for a blank entry.",
        DESCRIPTION_ROOT_TERMINAL = "Core names end with one of these half-syllables. Use _ to represent a space, and ^ for a blank entry.\nThe raw number of ^ entries will be ignored if \"Syllable Count Weighting\" and \"Terminal Blank Counts\" are formatted correctly.",
        DESCRIPTION_SUFFIXES = "Suffixes that can occur after the core name.",
        DESCRIPTION_SYLLABLE_COUNT_WEIGHTING = "How often core names of various lengths are generated. The number in the Nth row is the weighting for N-syllable names.",
        DESCRIPTION_TERMINAL_BLANK_COUNTS = "The number of names that end on a blank \"Root: Terminal\" character (typically this means ending on a vowel sound). The number in the Nth row is the weighting for N-syllable names. "
            + "If this number is larger than the corresponding row's number in \"Syllable Count Weighting\" or smaller than 0, then this config will be ignored and the \"Root: Terminal\" character "
            + "will be drawn from directly.",
        DESCRIPTION_PREFIX_CHANCE = "The fraction of names that include a prefix.",
        DESCRIPTION_SUFFIX_CHANCE = "The fraction of names that include a suffix.",

        // Village generator
        CATEGORY_VILLAGE_GENERATOR = "village generator",

        VN_BUILDING_CLASSPATH_STUB = "astrotibs.villagenames.village.biomestructures.",
        PLAINS_BUILDING_STUB = "PlainsStructures$Plains", DESERT_BUILDING_STUB = "DesertStructures$Desert",
        TAIGA_BUILDING_STUB = "TaigaStructures$Taiga", SAVANNA_BUILDING_STUB = "SavannaStructures$Savanna",
        SNOWY_BUILDING_STUB = "SnowyStructures$Snowy", JUNGLE_BUILDING_STUB = "JungleStructures$Jungle",
        SWAMP_BUILDING_STUB = "SwampStructures$Swamp",

        // Chest loot
        VN_DESERT_HOUSE = "vn_desert_house", VN_PLAINS_HOUSE = "vn_plains_house", VN_SAVANNA_HOUSE = "vn_savanna_house",
        VN_SNOWY_HOUSE = "vn_snowy_house", VN_TAIGA_HOUSE = "vn_taiga_house", VN_JUNGLE_HOUSE = "vn_jungle_house",
        VN_SWAMP_HOUSE = "vn_swamp_house", VN_ARMORER = "vn_armorer", VN_BUTCHER = "vn_butcher",
        VN_CARTOGRAPHER = "vn_cartographer", VN_FARM = "vn_farm", VN_FISHER = "vn_fisher", VN_FLETCHER = "vn_fletcher",
        VN_LIBRARY = "vn_library", VN_MASON = "vn_mason", VN_SHEPHERD = "vn_shepherd", VN_TANNERY = "vn_tannery",
        VN_TEMPLE = "vn_temple", VN_TOOLSMITH = "vn_toolsmith"
    // VN_WEAPONSMITH = "vn_weaponsmith" // This is just the vanilla blacksmith chest
    ;
}
