package astrotibs.villagenames.utility;

public class Reference {
	// Contains common constants for the mod
	public static final String MOD_ID = "VillageNames";
	public static final String MOD_NAME = "Village Names";
	public static final String VERSION = "3.1";
	public static final String URL = "minecraft.curseforge.com/projects/village-names";
	public static final String VERSION_CHECKER_URL = "https://www.dropbox.com/s/ww32gqcftjgnl7n/latestversion_1.7.10.txt?dl=1";
	public static final String MOD_CHANNEL = "vnChannel";
	public static final String CLIENT_PROXY = "astrotibs.villagenames.proxy.ClientProxy";
	public static final String SERVER_PROXY = "astrotibs.villagenames.proxy.ServerProxy";
	public static final String COMMON_PROXY = "astrotibs.villagenames.proxy.CommonProxy";
	public static final String GUI_FACTORY = "astrotibs.villagenames.gui.VNGuiFactory";
    public static final String CONFIG_PATH = "config/VillageNames/";
    
    // Elder Guardian class path: prior to 1.11, the Elder is just a normal Guardian with an "Elder" flag turned on.
    // This helps distinguish the two via hard-coding a faux class path
    public static final String elderGuardianClass = "astrotibs.villagenames.prismarine.guardian.entity.monster.EntityElderGuardian";
    
}
