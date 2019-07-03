package astrotibs.villagenames.client.renderer.entity;

import java.util.Map;

import com.google.common.collect.Maps;

import astrotibs.villagenames.client.model.ModelVillagerModern;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.ieep.ExtendedVillager;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderVillager;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

/**
 * Adapted from Villager Mantle Fix by MJaroslav
 * https://github.com/MJaroslav/VillagerMantleFix/blob/1.7.10/src/main/java/mjaroslav/mcmods/villagermantlefix/client/renderer/entity/RenderVillagerAlt.java
 * @author AstroTibs
 */

//Added in v3.1
@SideOnly(Side.CLIENT)
public class RenderVillagerModern extends RenderVillager {
	
	// Constructor
	public RenderVillagerModern() {
		this.mainModel = new ModelVillagerModern(0.0F);
		this.villagerModel = (ModelVillagerModern) this.mainModel;
		this.setRenderPassModel(new ModelVillagerModern(0.1F));
	}
	
	// ------------------------------ //
	// --- Skin resource elements --- //
	// ------------------------------ //
	
	// Base skin texture
	private static final ResourceLocation villagerBaseSkin = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/villager.png");
	
	// Biome-based types
	private static final ResourceLocation villagerTypeDesert  = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/type/desert.png");
	private static final ResourceLocation villagerTypeJungle  = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/type/jungle.png");
	private static final ResourceLocation villagerTypePlains  = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/type/plains.png");
	private static final ResourceLocation villagerTypeSavanna = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/type/savanna.png");
	private static final ResourceLocation villagerTypeSnow    = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/type/snow.png");
	private static final ResourceLocation villagerTypeSwamp   = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/type/swamp.png");
	private static final ResourceLocation villagerTypeTaiga   = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/type/taiga.png");
	// Custom biome types
	private static final ResourceLocation villagerTypeForest   = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/type/forest.png");
	private static final ResourceLocation villagerTypeAquatic  = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/type/aquatic.png");
	private static final ResourceLocation villagerTypeHighland = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/type/highland.png");
	private static final ResourceLocation villagerTypeMushroom = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/type/mushroom.png");
	private static final ResourceLocation villagerTypeMagical  = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/type/magical.png");
	private static final ResourceLocation villagerTypeNether   = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/type/nether.png");
	private static final ResourceLocation villagerTypeEnd      = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/type/end.png");
	
	// Profession-based layer
	private static final ResourceLocation villagerProfessionArmorer = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession/armorer.png");
	private static final ResourceLocation villagerProfessionButcher = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession/butcher.png");
	private static final ResourceLocation villagerProfessionCartographer = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession/cartographer.png");
	private static final ResourceLocation villagerProfessionCleric = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession/cleric.png");
	private static final ResourceLocation villagerProfessionFarmer = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession/farmer.png");
	private static final ResourceLocation villagerProfessionFisherman = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession/fisherman.png");
	private static final ResourceLocation villagerProfessionFletcher = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession/fletcher.png");
	private static final ResourceLocation villagerProfessionLeatherworker = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession/leatherworker.png");
	private static final ResourceLocation villagerProfessionLibrarian = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession/librarian.png");
	private static final ResourceLocation villagerProfessionMason = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession/mason.png");
	private static final ResourceLocation villagerProfessionNitwit = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession/nitwit.png");
	private static final ResourceLocation villagerProfessionShepherd = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession/shepherd.png");
	private static final ResourceLocation villagerProfessionToolsmith = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession/toolsmith.png");
	private static final ResourceLocation villagerProfessionWeaponsmith = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession/weaponsmith.png");
	
	// Profession level purses
	private static final ResourceLocation villagerProfessionLevelStone = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession_level/stone.png");
	private static final ResourceLocation villagerProfessionLevelIron = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession_level/iron.png");
	private static final ResourceLocation villagerProfessionLevelGold = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession_level/gold.png");
	private static final ResourceLocation villagerProfessionLevelEmerald = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession_level/emerald.png");
	private static final ResourceLocation villagerProfessionLevelDiamond = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession_level/diamond.png");
	
	// Vanilla textures
    private static final ResourceLocation defaultOldNitwit = new ResourceLocation("textures/entity/villager/villager.png");
    private static final ResourceLocation defaultOldFarmer = new ResourceLocation("textures/entity/villager/farmer.png");
    private static final ResourceLocation defaultOldLibrarian = new ResourceLocation("textures/entity/villager/librarian.png");
    private static final ResourceLocation defaultOldPriest = new ResourceLocation("textures/entity/villager/priest.png");
    private static final ResourceLocation defaultOldSmith = new ResourceLocation("textures/entity/villager/smith.png");
    private static final ResourceLocation defaultOldButcher = new ResourceLocation("textures/entity/villager/butcher.png");
	
	
	

    /**
     * Added in v3.1.1: machinery for modular textures, adapted from RenderHorse
     */
    
    //private String[] layeredTextureAddressArray = new String[4]; // Holds the different layer textures as resource address strings
    private static final Map skinComboHashmap = Maps.newHashMap(); // Populates a hash map with various combinations so you don't have to constantly ascertain them on the fly
    //private String skinComboHashKey; // String that will be the hashmap key corresponding to the particular biome/career combination
    
    // Made this 2D so that I can always make sure to add the proper key
    private static final String[][] biomeTypeTextures = new String[][] {
    		{villagerTypePlains.toString(), "pla"},
    		{villagerTypeMagical.toString(), "mag"},
    		{villagerTypeHighland.toString(), "hig"},
    		{villagerTypeForest.toString(), "for"},
    		{villagerTypeAquatic.toString(), "aqu"},
    		{villagerTypeJungle.toString(), "jun"},
    		{villagerTypeSwamp.toString(), "swa"},
    		{villagerTypeTaiga.toString(), "tai"},
    		{villagerTypeDesert.toString(), "des"},
    		{villagerTypeSavanna.toString(), "sav"},
    		{villagerTypeMushroom.toString(), "mus"},
    		{villagerTypeSnow.toString(), "sno"},
    		{villagerTypeEnd.toString(), "end"},
    		{villagerTypeNether.toString(), "net"},
    		};
    
    private static final String[][] careerTextures = new String[][] {
    	{villagerProfessionFarmer.toString(), "far"}, // 0
    	{villagerProfessionFisherman.toString(), "fis"},
    	{villagerProfessionShepherd.toString(), "she"},
    	{villagerProfessionFletcher.toString(), "fle"},
    	{villagerProfessionLibrarian.toString(), "lib"}, // 4
    	{villagerProfessionCartographer.toString(), "car"},
    	{villagerProfessionCleric.toString(), "cle"}, // 6
    	{villagerProfessionArmorer.toString(), "arm"},
    	{villagerProfessionWeaponsmith.toString(), "wea"},
    	{villagerProfessionToolsmith.toString(), "too"}, // 9
    	{villagerProfessionMason.toString(), "mas"},
    	{villagerProfessionButcher.toString(), "but"}, // 11
    	{villagerProfessionLeatherworker.toString(), "lea"},
    	{villagerProfessionNitwit.toString(), "nit"}, //13
    	};
    
    private static final String[][] profLevelTextures = new String[][] {
    	{villagerProfessionLevelStone.toString(), "pl1"},
    	{villagerProfessionLevelIron.toString(), "pl2"},
    	{villagerProfessionLevelGold.toString(), "pl3"},
    	{villagerProfessionLevelEmerald.toString(), "pl4"},
    	{villagerProfessionLevelDiamond.toString(), "pl5"},
    	};
    
	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(EntityVillager villager)
	{
		if (
				GeneralConfig.modernVillagerSkins
				&& villager.getProfession() >= 0
				&& villager.getProfession() <= 5
				)
		{
			return this.getHashmappedSkinCombo(villager);
		}
		else
		{
			// Condition for modern villager skins OFF
		    switch (villager.getProfession())
		    {
		        case 0:
		            return defaultOldFarmer;
		        case 1:
		            return defaultOldLibrarian;
		        case 2:
		            return defaultOldPriest;
		        case 3:
		            return defaultOldSmith;
		        case 4:
		            return defaultOldButcher;
		        default:
		        	return VillagerRegistry.getVillagerSkin(villager.getProfession(), defaultOldNitwit);
		    }
		}
	}
    
    private ResourceLocation getHashmappedSkinCombo(EntityVillager villager)
    {
        String s = this.getModularVillagerTexture(villager);
        ResourceLocation resourcelocation = (ResourceLocation)skinComboHashmap.get(s);
        
        if (resourcelocation == null)
        {
            resourcelocation = new ResourceLocation(s);
            Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, new LayeredTexture(this.getVariantTexturePaths(villager)));
            skinComboHashmap.put(s, resourcelocation);
        }

        return resourcelocation;
    }

    /**
     * Index 0 will be the string array providing the rendering layers for the skin.
     * Index 1 will be the rendering hash key.
     */
    @SideOnly(Side.CLIENT)
    private Object[] setModularVillagerTexturePaths(EntityVillager villager)
    {
    	final ExtendedVillager ev = ExtendedVillager.get(villager);
    	
        String skinComboHashKey = "villager/";
        
        String[] layeredTextureAddressArray = new String[4]; 
        
        // Reset the layer array
        layeredTextureAddressArray[0] = villagerBaseSkin.toString();
        layeredTextureAddressArray[1] = null;
        layeredTextureAddressArray[2] = null;
        layeredTextureAddressArray[3] = null;
        
        // Set the indexing values, and clamp them just in case
        int biometype = MathHelper.clamp_int(ev.getBiomeType(), 0, biomeTypeTextures.length-1);
        int career = GeneralConfig.villagerCareers ? ev.getCareer() : -1;
        int proflevel = MathHelper.clamp_int(ev.getProfessionLevel(), 0, profLevelTextures.length-1);
        
        // Use the profession and career values to zero in on the value stored in the careerTextures array
        int careerIndex = 0;
        switch (ev.getProfession())
        {
	    	case 0: // Farmer type
	    		switch (career)
	    		{
		    		default:
		    		case 1: careerIndex = 0; break;
		    		case 2: careerIndex = 1; break;
		    		case 3: careerIndex = 2; break;
		    		case 4: careerIndex = 3; break;
	    		}
	    		break;
	    		
	    	case 1: // Librarian type
	    		switch (career)
	    		{
		    		default:
		    		case 1: careerIndex = 4; break;
		    		case 2: careerIndex = 5; break;
	    		}
	    		break;
	    		
	    	case 2: // Priest type
	    		switch (career)
	    		{
		    		default:
		    		case 1: careerIndex = 6; break;
	    		}
	    		break;
	    		
	    	case 3: // Smith type
	    		switch (career)
	    		{
		    		case 1: careerIndex = 7; break;
		    		case 2: careerIndex = 8; break;
		    		default:
		    		case 3: careerIndex = 9; break;
		    		case 4: careerIndex = 10; break;
	    		}
	    		break;
	    		
	    	case 4: // Butcher type
	    		switch (career)
	    		{
		    		default:
		    		case 1: careerIndex = 11; break;
		    		case 2: careerIndex = 12; break;
	    		}
	    		break;
	    		
	    	case 5: // Nitwit type
	    		switch (career)
	    		{
		    		default:
		    		case 1: careerIndex = 13; break;
	    		}
	    		break;
	    		
	    	default: // No profession skin
        }
        
        // Set the biome type
        layeredTextureAddressArray[1] = biomeTypeTextures[biometype][0];
        skinComboHashKey = skinComboHashKey + biomeTypeTextures[biometype][1];
        
        // Set the career
        layeredTextureAddressArray[2] = careerTextures[careerIndex][0];
        skinComboHashKey = skinComboHashKey + "_" + careerTextures[careerIndex][1];
        
        // Set the profession level
        layeredTextureAddressArray[3] = profLevelTextures[proflevel][0];
        skinComboHashKey = skinComboHashKey + "_" + profLevelTextures[proflevel][1];
        
        //return skinComboHashKey;
        return new Object[] {layeredTextureAddressArray, skinComboHashKey};
    }
    
    @SideOnly(Side.CLIENT)
    public String getModularVillagerTexture(EntityVillager villager)
    {
    	return (String) (setModularVillagerTexturePaths(villager))[1];
    }
    
    @SideOnly(Side.CLIENT)
    public String[] getVariantTexturePaths(EntityVillager villager)
    {
    	return (String[]) (this.setModularVillagerTexturePaths(villager))[0];
    }
    
}
