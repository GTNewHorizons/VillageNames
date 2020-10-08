package astrotibs.villagenames.utility;

public class Reference
{
	// Contains common constants for the mod
	public static final String MOD_ID = "VillageNames";
	public static final String MOD_NAME = "Village Names";
	public static final String VERSION = "4.0.0 DEV";
	public static final String URL = "https://www.curseforge.com/minecraft/mc-mods/village-names";
	public static final String VERSION_CHECKER_URL = "https://gitgud.io/AstroTibs/VillageNames/raw/1.7.10/CURRENT_VERSION";
	public static final String MOD_CHANNEL = "vnChannel";
	public static final String CLIENT_PROXY = "astrotibs.villagenames.proxy.ClientProxy";
	public static final String SERVER_PROXY = "astrotibs.villagenames.proxy.ServerProxy";
	public static final String COMMON_PROXY = "astrotibs.villagenames.proxy.CommonProxy";
	public static final String GUI_FACTORY = "astrotibs.villagenames.config.gui.VNGuiFactory";
    
    // Elder Guardian class path: prior to 1.11, the Elder is just a normal Guardian with an "Elder" flag turned on.
    // This helps distinguish the two via hard-coding a faux class path
    public static final String ELDER_GUARDIAN_CLASS = "astrotibs.villagenames.prismarine.guardian.entity.monster.EntityElderGuardian";
    public static final String VILLAGER_CLASS = "net.minecraft.entity.passive.EntityVillager";
    public static final int STREET_WIDTH = 3;
	public static final double SPAWN_BLOCK_OFFSET = 0.5D; // If you obtained the spawn x,y,z as ints, add this offset to x and z to ensure it's in the center of the block.
	public static final String MOB_GUARDIAN_VN = "Guardian";
	public static final String ELDER_GEN = "ElderGen"; // The old version of Elder generation for when Guardians were first introduced
	public static final String ELDER_GEN_VN4 = "ElderGen_VN4"; // Regenerating Elder Guardians to account for new entity registry in Village Names 4
	
	
    // Config values
    public static final String FOLDER_NAMEPIECES = "namepieces";
    public static final String FOLDER_NEWVILLAGES = "newvillages";
    public static final String CATEGORY_VILLAGE_GENERATOR = "Village Generator";
}
