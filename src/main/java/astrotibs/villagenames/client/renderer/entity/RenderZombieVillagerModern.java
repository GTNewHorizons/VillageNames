package astrotibs.villagenames.client.renderer.entity;

import java.util.Map;

import com.google.common.collect.Maps;

import astrotibs.villagenames.client.model.ModelZombieVillagerModern;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.ieep.ExtendedZombieVillager;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

/**
 * This is essentially the vanilla class, but modified to accommodate the modern layered skins.
 * @author AstroTibs
 */

@SideOnly(Side.CLIENT)
public class RenderZombieVillagerModern extends RenderBiped
{

	// ------------------------------ //
	// --- Skin resource elements --- //
	// ------------------------------ //
	
	// Base skin texture
	private static final ResourceLocation zombieVillagerBaseSkin = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/zombie_villager.png");
	
	// Biome-based types
	private static final ResourceLocation zombieVillagerTypeDesert  = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/type/desert.png");
	private static final ResourceLocation zombieVillagerTypeJungle  = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/type/jungle.png");
	private static final ResourceLocation zombieVillagerTypePlains  = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/type/plains.png");
	private static final ResourceLocation zombieVillagerTypeSavanna = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/type/savanna.png");
	private static final ResourceLocation zombieVillagerTypeSnow    = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/type/snow.png");
	private static final ResourceLocation zombieVillagerTypeSwamp   = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/type/swamp.png");
	private static final ResourceLocation zombieVillagerTypeTaiga   = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/type/taiga.png");
	// Custom biome types
	private static final ResourceLocation zombieVillagerTypeForest   = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/type/forest.png");
	private static final ResourceLocation zombieVillagerTypeAquatic  = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/type/aquatic.png");
	private static final ResourceLocation zombieVillagerTypeHighland = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/type/highland.png");
	private static final ResourceLocation zombieVillagerTypeMushroom = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/type/mushroom.png");
	private static final ResourceLocation zombieVillagerTypeMagical  = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/type/magical.png");
	private static final ResourceLocation zombieVillagerTypeNether   = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/type/nether.png");
	private static final ResourceLocation zombieVillagerTypeEnd      = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/type/end.png");
	
	// Profession-based layer
	private static final ResourceLocation zombieVillagerProfessionArmorer = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession/armorer.png");
	private static final ResourceLocation zombieVillagerProfessionButcher = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession/butcher.png");
	private static final ResourceLocation zombieVillagerProfessionCartographer = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession/cartographer.png");
	private static final ResourceLocation zombieVillagerProfessionCleric = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession/cleric.png");
	private static final ResourceLocation zombieVillagerProfessionFarmer = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession/farmer.png");
	private static final ResourceLocation zombieVillagerProfessionFisherman = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession/fisherman.png");
	private static final ResourceLocation zombieVillagerProfessionFletcher = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession/fletcher.png");
	private static final ResourceLocation zombieVillagerProfessionLeatherworker = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession/leatherworker.png");
	private static final ResourceLocation zombieVillagerProfessionLibrarian = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession/librarian.png");
	private static final ResourceLocation zombieVillagerProfessionMason = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession/mason.png");
	private static final ResourceLocation zombieVillagerProfessionNitwit = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession/nitwit.png");
	private static final ResourceLocation zombieVillagerProfessionShepherd = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession/shepherd.png");
	private static final ResourceLocation zombieVillagerProfessionToolsmith = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession/toolsmith.png");
	private static final ResourceLocation zombieVillagerProfessionWeaponsmith = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession/weaponsmith.png");
	
	// Profession level purses
	private static final ResourceLocation zombieVillagerProfessionLevelStone = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession_level/stone.png");
	private static final ResourceLocation zombieVillagerProfessionLevelIron = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession_level/iron.png");
	private static final ResourceLocation zombieVillagerProfessionLevelGold = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession_level/gold.png");
	private static final ResourceLocation zombieVillagerProfessionLevelEmerald = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession_level/emerald.png");
	private static final ResourceLocation zombieVillagerProfessionLevelDiamond = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession_level/diamond.png");
	
	// Vanilla textures
    private static final ResourceLocation zombiePigmanTextures = new ResourceLocation("textures/entity/zombie_pigman.png");
    private static final ResourceLocation zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
    private static final ResourceLocation zombieVillagerTextures = new ResourceLocation("textures/entity/zombie/zombie_villager.png");
    
    
	
    private ModelBiped field_82434_o;
    private ModelZombieVillagerModern zombieVillagerModel;
    protected ModelBiped field_82437_k;
    protected ModelBiped field_82435_l;
    protected ModelBiped field_82436_m;
    protected ModelBiped field_82433_n;
    private int field_82431_q = 1;
    private static final String __OBFID = "CL_00001037";
    
    public RenderZombieVillagerModern()
    {
        super(new ModelZombie(), 0.5F, 1.0F);
        this.field_82434_o = this.modelBipedMain;
        this.zombieVillagerModel = new ModelZombieVillagerModern(0);
        this.setRenderPassModel(new ModelZombieVillagerModern(0.1F));
    }
    
    @Override
    protected void func_82421_b()
    {
        this.field_82423_g = new ModelZombie(1.0F, true);
        this.field_82425_h = new ModelZombie(0.5F, true);
        this.field_82437_k = this.field_82423_g;
        this.field_82435_l = this.field_82425_h;
        this.field_82436_m = new ModelZombieVillagerModern(0);
        this.field_82433_n = new ModelZombieVillagerModern(0);
    }
    
    /**
     * Queries whether should render the specified pass or not.
     */
    // Reset to vanilla style - v3.1.1
    protected int shouldRenderPass(EntityZombie zombie, int passNumber, float progress)
    {
        this.modelSetter(zombie); // Whether or not this will render as a villager-type zombie
        return super.shouldRenderPass((EntityLiving)zombie, passNumber, progress);
    }
	
    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityZombie zombie, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.modelSetter(zombie);
        super.doRender((EntityLiving)zombie, x, y, z, entityYaw, partialTicks);
    }
    
    

    protected void renderEquippedItems(EntityZombie p_77029_1_, float p_77029_2_)
    {
        this.modelSetter(p_77029_1_);
        super.renderEquippedItems((EntityLiving)p_77029_1_, p_77029_2_);
    }

    private void modelSetter(EntityZombie zombie)
    {
        if (zombie.isVillager())
        {
            if (this.field_82431_q != this.zombieVillagerModel.func_82897_a())
            {
                this.zombieVillagerModel = new ModelZombieVillagerModern(0);
                this.field_82431_q = this.zombieVillagerModel.func_82897_a();
                this.field_82436_m = new ModelZombieVillagerModern(0);
                this.field_82433_n = new ModelZombieVillagerModern(0);
            }

            this.mainModel = this.zombieVillagerModel;
            this.field_82423_g = this.field_82436_m;
            this.field_82425_h = this.field_82433_n;
        }
        else
        {
            this.mainModel = this.field_82434_o;
            this.field_82423_g = this.field_82437_k;
            this.field_82425_h = this.field_82435_l;
        }

        this.modelBipedMain = (ModelBiped)this.mainModel;
    }

    protected void rotateCorpse(EntityZombie p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
    {
        if (p_77043_1_.isConverting())
        {
            p_77043_3_ += (float)(Math.cos((double)p_77043_1_.ticksExisted * 3.25D) * Math.PI * 0.25D);
        }

        super.rotateCorpse(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    @Override
    protected void renderEquippedItems(EntityLiving p_77029_1_, float p_77029_2_)
    {
        this.renderEquippedItems((EntityZombie)p_77029_1_, p_77029_2_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(EntityLiving p_110775_1_)
    {
        return this.getEntityTexture((EntityZombie)p_110775_1_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    @Override
    public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((EntityZombie)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    @Override
    protected int shouldRenderPass(EntityLiving p_77032_1_, int p_77032_2_, float p_77032_3_)
    {
        return this.shouldRenderPass((EntityZombie)p_77032_1_, p_77032_2_, p_77032_3_);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    @Override
    protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
    {
        return this.shouldRenderPass((EntityZombie)p_77032_1_, p_77032_2_, p_77032_3_);
    }
    
    @Override
    protected void renderEquippedItems(EntityLivingBase p_77029_1_, float p_77029_2_)
    {
        this.renderEquippedItems((EntityZombie)p_77029_1_, p_77029_2_);
    }
    
    @Override
    protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
    {
        this.rotateCorpse((EntityZombie)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    @Override
    public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((EntityZombie)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture((EntityZombie)p_110775_1_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    @Override
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((EntityZombie)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    
    
    /**
     * Added in v3.1.1: machinery for modular textures, adapted from RenderHorse
     */
    
    // summon Zombie ~ ~ ~ {IsVillager:1}
    
    //private String[] layeredTextureAddressArray = new String[4]; // Holds the different layer textures as resource address strings
    private static final Map skinComboHashmap = Maps.newHashMap(); // Populates a hash map with various combinations so you don't have to constantly ascertain them on the fly
    //private String skinComboHashKey; // String that will be the hashmap key corresponding to the particular biome/career combination
    
    // Made this 2D so that I can always make sure to add the proper key
    private static final String[][] biomeTypeTextures = new String[][] {
    		{zombieVillagerTypePlains.toString(), "pla"},
    		{zombieVillagerTypeMagical.toString(), "mag"},
    		{zombieVillagerTypeHighland.toString(), "hig"},
    		{zombieVillagerTypeForest.toString(), "for"},
    		{zombieVillagerTypeAquatic.toString(), "aqu"},
    		{zombieVillagerTypeJungle.toString(), "jun"},
    		{zombieVillagerTypeSwamp.toString(), "swa"},
    		{zombieVillagerTypeTaiga.toString(), "tai"},
    		{zombieVillagerTypeDesert.toString(), "des"},
    		{zombieVillagerTypeSavanna.toString(), "sav"},
    		{zombieVillagerTypeMushroom.toString(), "mus"},
    		{zombieVillagerTypeSnow.toString(), "sno"},
    		{zombieVillagerTypeEnd.toString(), "end"},
    		{zombieVillagerTypeNether.toString(), "net"},
    		};
    
    private static final String[][] careerTextures = new String[][] {
    	{zombieVillagerProfessionFarmer.toString(), "far"}, // 0
    	{zombieVillagerProfessionFisherman.toString(), "fis"},
    	{zombieVillagerProfessionShepherd.toString(), "she"},
    	{zombieVillagerProfessionFletcher.toString(), "fle"},
    	{zombieVillagerProfessionLibrarian.toString(), "lib"}, // 4
    	{zombieVillagerProfessionCartographer.toString(), "car"},
    	{zombieVillagerProfessionCleric.toString(), "cle"}, // 6
    	{zombieVillagerProfessionArmorer.toString(), "arm"},
    	{zombieVillagerProfessionWeaponsmith.toString(), "wea"},
    	{zombieVillagerProfessionToolsmith.toString(), "too"}, // 9
    	{zombieVillagerProfessionMason.toString(), "mas"},
    	{zombieVillagerProfessionButcher.toString(), "but"}, // 11
    	{zombieVillagerProfessionLeatherworker.toString(), "lea"},
    	{zombieVillagerProfessionNitwit.toString(), "nit"}, //13
    	};
    
    private static final String[][] profLevelTextures = new String[][] {
    	{zombieVillagerProfessionLevelStone.toString(), "pl1"},
    	{zombieVillagerProfessionLevelIron.toString(), "pl2"},
    	{zombieVillagerProfessionLevelGold.toString(), "pl3"},
    	{zombieVillagerProfessionLevelEmerald.toString(), "pl4"},
    	{zombieVillagerProfessionLevelDiamond.toString(), "pl5"},
    	};
    
    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityZombie zombie)
    {
    	if (zombie instanceof EntityPigZombie) { // Is a zombie pigman
    		return zombiePigmanTextures;
    	}
    	else if ( zombie.isVillager() ) // Is a zombie villager
    	{
    		if (GeneralConfig.modernZombieSkins) // v3.2.3
    		{
    			return this.getHashmappedSkinCombo(zombie);
    		}
    		else
    		{
    			return zombieVillagerTextures;
    		}
        } 
        else { // Is an ordinary zombie
            return zombieTextures; // The default zombie skin
        }
    }
    
    private ResourceLocation getHashmappedSkinCombo(EntityZombie zombievillager)
    {
        String s = this.getModularZombieVillagerTexture(zombievillager);
        ResourceLocation resourcelocation = (ResourceLocation)skinComboHashmap.get(s);
        
        if (resourcelocation == null)
        {
            resourcelocation = new ResourceLocation(s);
            Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, new LayeredTexture(this.getVariantTexturePaths(zombievillager)));
            skinComboHashmap.put(s, resourcelocation);
        }

        return resourcelocation;
    }
    
    /**
     * Index 0 will be the string array providing the rendering layers for the skin.
     * Index 1 will be the rendering hash key.
     */
    @SideOnly(Side.CLIENT)
    private Object[] setModularZombieVillagerTexturePaths(EntityZombie zombievillager)
    {
    	final ExtendedZombieVillager ezv = ExtendedZombieVillager.get(zombievillager);
    	
        String skinComboHashKey = "zombievillager/";
        
        String[] layeredTextureAddressArray = new String[4]; 
        
        // Reset the layer array
        layeredTextureAddressArray[0] = zombieVillagerBaseSkin.toString();
        layeredTextureAddressArray[1] = null;
        layeredTextureAddressArray[2] = null;
        layeredTextureAddressArray[3] = null;
        
        // Set the indexing values, and clamp them just in case
        int biometype = MathHelper.clamp_int(ezv.getBiomeType(), 0, biomeTypeTextures.length-1);
        int career = GeneralConfig.villagerCareers ? ezv.getCareer() : -1;
        int proflevel = MathHelper.clamp_int(ezv.getProfessionLevel(), 0, profLevelTextures.length-1);
        
        
        // Re-arranged in v3.2
        
        // ---------------------------- //
        // --- PART 0: set the skin --- //
        // ---------------------------- //
        
        // TODO - Maybe I'll add differing zombie skin tones in the future.
        
        
        // ---------------------------------- //
        // --- PART 1: set the biome type --- //
        // ---------------------------------- //
        
        layeredTextureAddressArray[1] = biomeTypeTextures[biometype][0];
        skinComboHashKey = skinComboHashKey + biomeTypeTextures[biometype][1];
        
        
        // ----------------------------- //
        // --- PART 2: set the career--- //
        // ----------------------------- //
        
        int indexofmodprof = GeneralConfig.professionID_a.indexOf(ezv.getProfession());
        
        if (!zombievillager.isChild())
        {
            if ( 
            		ezv.getProfession() > 5 // Is non-vanilla
            		&& indexofmodprof > -1 // Has a skin asset mapping
            		//&& !((String) (moddedVillagerCareerSkins.get("zombieCareerAsset")).get(indexofmodprof)).equals("") ) // That mapping isn't blank
            		)
            {
            	// This is a modded profession ID with a supported skin -- possibly.
            	
            	// If there is no zombie or non-zombie version, just default the asset to the Nitwit style.
            	ResourceLocation modCareerSkin = zombieVillagerProfessionNitwit;
            	String profRootName = "nit"; 
            	
            	// First check if there is an explicit zombie profession texture.
            	profRootName = ((String) (GeneralConfig.zombieCareerAsset_a).get(indexofmodprof));
            	
            	if (profRootName.equals(""))
            	{
            		// There is none, so check the non-zombie version
            		profRootName = ((String) (GeneralConfig.careerAsset_a).get(indexofmodprof));
    	        	
            		if (!profRootName.equals(""))
            		{
            			// A non-zombie texture was found.
                    	modCareerSkin = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/villager/profession/"+profRootName+".png");
            		}
            	}
            	else
            	{
            		// A zombie texture was found.
            		modCareerSkin = new ResourceLocation((Reference.MOD_ID).toLowerCase(), "textures/entity/zombie_villager/profession/"+profRootName+".png");
            	}
            	
                layeredTextureAddressArray[2] = modCareerSkin.toString();
                skinComboHashKey = skinComboHashKey + "_" + profRootName;
            }
            else // This is vanilla skin or is otherwise unsupported
            {
            	// Use the profession and career values to zero in on the value stored in the careerTextures array
                int careerIndex = 0;
                switch (ezv.getProfession())
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
                
                // Set the career
                layeredTextureAddressArray[2] = careerTextures[careerIndex][0];
                skinComboHashKey = skinComboHashKey + "_" + careerTextures[careerIndex][1];
            }
        }
        

        
        // ---------------------------------------- //
        // --- PART 3: set the profession level --- //
        // ---------------------------------------- //
        
        layeredTextureAddressArray[3] = null;//profLevelTextures[proflevel][0]; // Left off for now: no need to see prof level
        skinComboHashKey = skinComboHashKey + "_" + profLevelTextures[proflevel][1];
        
        //return skinComboHashKey;
        return new Object[] {layeredTextureAddressArray, skinComboHashKey};
    }
    
    @SideOnly(Side.CLIENT)
    public String getModularZombieVillagerTexture(EntityZombie zombievillager)
    {
    	return (String) (setModularZombieVillagerTexturePaths(zombievillager))[1];
    }
    
    @SideOnly(Side.CLIENT)
    public String[] getVariantTexturePaths(EntityZombie zombievillager)
    {
    	return (String[]) (this.setModularZombieVillagerTexturePaths(zombievillager))[0];
    }
    
}