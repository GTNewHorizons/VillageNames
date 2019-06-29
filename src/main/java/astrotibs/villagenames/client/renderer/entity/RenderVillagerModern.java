package astrotibs.villagenames.client.renderer.entity;

import astrotibs.villagenames.client.model.ModelVillagerModern;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.ieep.ExtendedVillager;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderVillager;
import net.minecraft.entity.passive.EntityVillager;
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
			return villagerBaseSkin;
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
	

    /**
     * Queries whether should render the specified pass or not.
     */
	/*
    @Override
    protected int shouldRenderPass(EntityLivingBase entity, int passNumber, float progress)
    {
    	if (entity instanceof EntityVillager)
    	{
    		return this.shouldRenderPass((EntityVillager)entity, passNumber, progress);
    	}
    	else
    	{
    		return super.shouldRenderPass(entity, passNumber, progress);
    	}
    }
    */
	
    /**
     * Queries whether should render the specified pass or not.
     */
	@Override
    protected int shouldRenderPass(EntityVillager villager, int passNumber, float progress)
    {
		if (villager.getProfession() >= 0 && villager.getProfession() <= 5)
		{
			// Biome type skins
			if (GeneralConfig.modernVillagerSkins && passNumber == 1)
			{
				switch ((ExtendedVillager.get(villager)).getBiomeType())
				{
					case 11:
						this.bindTexture(villagerTypeSnow); break;
					case 9:
						this.bindTexture(villagerTypeSavanna); break;
					case 8:
						this.bindTexture(villagerTypeDesert); break;
					case 3:
						this.bindTexture(villagerTypeForest); break;
					case 7:
						this.bindTexture(villagerTypeTaiga); break;
					case 6:
						this.bindTexture(villagerTypeSwamp); break;
					case 5:
						this.bindTexture(villagerTypeJungle); break;
					case 4:
						this.bindTexture(villagerTypeAquatic); break;
					case 2:
						this.bindTexture(villagerTypeHighland); break;
					case 10:
						this.bindTexture(villagerTypeMushroom); break;
					case 1:
						this.bindTexture(villagerTypeMagical); break;
					case 13:
						this.bindTexture(villagerTypeNether); break;
					case 12:
						this.bindTexture(villagerTypeEnd); break;
					default:
					case 0:
						this.bindTexture(villagerTypePlains); break;
				}
				return passNumber;
			}
			
			// Profession skins
			else if (GeneralConfig.modernVillagerSkins && passNumber == 2)
	        {
	        	int career = GeneralConfig.villagerCareers ? (ExtendedVillager.get(villager)).getCareer() : -1;
				
			    switch (villager.getProfession())
			    {
		        case 0: // Farmer type
		        	switch (career)
		        	{
		        	default:
		        	case 1:
		        		this.bindTexture(villagerProfessionFarmer); break;
		        	case 2:
		        		this.bindTexture(villagerProfessionFisherman); break;
		        	case 3:
		        		this.bindTexture(villagerProfessionShepherd); break;
		        	case 4:
		        		this.bindTexture(villagerProfessionFletcher); break;
		        	}
		        	return passNumber;
		        	
		        case 1: // Librarian type
		        	switch (career)
		        	{
		        	default:
		        	case 1:
		        		this.bindTexture(villagerProfessionLibrarian); break;
		        	case 2:
		        		this.bindTexture(villagerProfessionCartographer); break;
		        	}
		        	return passNumber;
		        	
		        case 2: // Priest type
		        	switch (career)
		        	{
		        	default:
		        	case 1:
		        		this.bindTexture(villagerProfessionCleric); break;
		        	}
		        	return passNumber;
		        	
		        case 3: // Smith type
		        	switch (career)
		        	{
		        	case 1:
		        		this.bindTexture(villagerProfessionArmorer); break;
		        	case 2:
		        		this.bindTexture(villagerProfessionWeaponsmith); break;
		        	default:
		        	case 3:
		        		this.bindTexture(villagerProfessionToolsmith); break;
		        	case 4:
		        		this.bindTexture(villagerProfessionMason); break;
		        	}
		        	return passNumber;
		        	
		        case 4: // Butcher type
		        	switch (career)
		        	{
		        	default:
		        	case 1:
		        		this.bindTexture(villagerProfessionButcher); break;
		        	case 2:
		        		this.bindTexture(villagerProfessionLeatherworker); break;
		        	}
		        	return passNumber;
		        	
		        case 5: // Butcher type
		        	switch (career)
		        	{
		        	default:
		        	case 1:
		        		this.bindTexture(villagerProfessionNitwit); break;
		        	}
		        	return passNumber;
		        default: // No profession skin
			    }
	        }
			
			// Profession levels
			else if (GeneralConfig.modernVillagerSkins && passNumber == 3)
			{
				final int profLevel = (ExtendedVillager.get(villager)).getProfessionLevel();
				if (profLevel <= 0) {return -1;}
				else if (profLevel >= 5) {this.bindTexture(villagerProfessionLevelDiamond); return passNumber;}
				switch (profLevel)
				{
					case 1: this.bindTexture(villagerProfessionLevelStone); return passNumber;
					case 2: this.bindTexture(villagerProfessionLevelIron); return passNumber;
					case 3: this.bindTexture(villagerProfessionLevelGold); return passNumber;
					case 4: this.bindTexture(villagerProfessionLevelEmerald); return passNumber;
				}
			}
		}
		
        return -1;
    }
    
}
