package astrotibs.villagenames.client.renderer.entity;

import astrotibs.villagenames.client.model.EFModelZombieVillager;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.ieep.ExtendedZombieVillager;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
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
    private EFModelZombieVillager zombieVillagerModel;
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
        this.zombieVillagerModel = new EFModelZombieVillager(0);
        this.setRenderPassModel(new EFModelZombieVillager(0.1F));
    }
    
    @Override
    protected void func_82421_b()
    {
        this.field_82423_g = new ModelZombie(1.0F, true);
        this.field_82425_h = new ModelZombie(0.5F, true);
        this.field_82437_k = this.field_82423_g;
        this.field_82435_l = this.field_82425_h;
        this.field_82436_m = new EFModelZombieVillager(0);
        this.field_82433_n = new EFModelZombieVillager(0);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityZombie zombie, int passNumber, float progress)
    {
        this.modelSetter(zombie); // Whether or not this will render as a villager-type zombie
        
        if (
        		!(zombie instanceof EntityPigZombie)
        		&& zombie.isVillager()
        		)
        {
        	final ExtendedZombieVillager ezv = ExtendedZombieVillager.get(zombie);
        	
        	if (ezv.getProfession() >= 0 && ezv.getProfession() <= 5)
    		{
    			// Biome type skins
    			if (GeneralConfig.modernVillagerSkins && passNumber == 2)
    			{
    				switch (ezv.getBiomeType())
    				{
    					case 11:
    						this.bindTexture(zombieVillagerTypeSnow); break;
    					case 9:
    						this.bindTexture(zombieVillagerTypeSavanna); break;
    					case 8:
    						this.bindTexture(zombieVillagerTypeDesert); break;
    					case 3:
    						this.bindTexture(zombieVillagerTypeForest); break;
    					case 7:
    						this.bindTexture(zombieVillagerTypeTaiga); break;
    					case 6:
    						this.bindTexture(zombieVillagerTypeSwamp); break;
    					case 5:
    						this.bindTexture(zombieVillagerTypeJungle); break;
    					case 4:
    						this.bindTexture(zombieVillagerTypeAquatic); break;
    					case 2:
    						this.bindTexture(zombieVillagerTypeHighland); break;
    					case 10:
    						this.bindTexture(zombieVillagerTypeMushroom); break;
    					case 1:
    						this.bindTexture(zombieVillagerTypeMagical); break;
    					case 13:
    						this.bindTexture(zombieVillagerTypeNether); break;
    					case 12:
    						this.bindTexture(zombieVillagerTypeEnd); break;
    					default:
    					case 0:
    						this.bindTexture(zombieVillagerTypePlains); break;
    				}
    				return passNumber;
    			}
    			
    			// Profession skins
    			else if (GeneralConfig.modernVillagerSkins && passNumber == 3)
    	        {
    				int career = GeneralConfig.villagerCareers ? ezv.getCareer() : -1;
    				
    			    switch (ezv.getProfession())
    			    {
    		        case 0: // Farmer type
    		        	switch (career)
    		        	{
    		        	default:
    		        	case 1:
    		        		this.bindTexture(zombieVillagerProfessionFarmer); break;
    		        	case 2:
    		        		this.bindTexture(zombieVillagerProfessionFisherman); break;
    		        	case 3:
    		        		this.bindTexture(zombieVillagerProfessionShepherd); break;
    		        	case 4:
    		        		this.bindTexture(zombieVillagerProfessionFletcher); break;
    		        	}
    		        	return passNumber;
    		        	
    		        case 1: // Librarian type
    		        	switch (career)
    		        	{
    		        	default:
    		        	case 1:
    		        		this.bindTexture(zombieVillagerProfessionLibrarian); break;
    		        	case 2:
    		        		this.bindTexture(zombieVillagerProfessionCartographer); break;
    		        	}
    		        	return passNumber;
    		        	
    		        case 2: // Priest type
    		        	switch (career)
    		        	{
    		        	default:
    		        	case 1:
    		        		this.bindTexture(zombieVillagerProfessionCleric); break;
    		        	}
    		        	return passNumber;
    		        	
    		        case 3: // Smith type
    		        	switch (career)
    		        	{
    		        	case 1:
    		        		this.bindTexture(zombieVillagerProfessionArmorer); break;
    		        	case 2:
    		        		this.bindTexture(zombieVillagerProfessionWeaponsmith); break;
    		        	default:
    		        	case 3:
    		        		this.bindTexture(zombieVillagerProfessionToolsmith); break;
    		        	case 4:
    		        		this.bindTexture(zombieVillagerProfessionMason); break;
    		        	}
    		        	return passNumber;
    		        	
    		        case 4: // Butcher type
    		        	switch (career)
    		        	{
    		        	default:
    		        	case 1:
    		        		this.bindTexture(zombieVillagerProfessionButcher); break;
    		        	case 2:
    		        		this.bindTexture(zombieVillagerProfessionLeatherworker); break;
    		        	}
    		        	return passNumber;
    		        	
    		        case 5: // Butcher type
    		        	switch (career)
    		        	{
    		        	default:
    		        	case 1:
    		        		this.bindTexture(zombieVillagerProfessionNitwit); break;
    		        	}
    		        	return passNumber;
    		        default: // No profession skin
    			    }
    	        }
    			/*
    			// Profession levels
    			else if (
        				GeneralConfig.modernVillagerSkins
        				&& passNumber == 4
        				)
        		{
        			switch (ezv.getProfessionLevel())
        			{
        				case 1: this.bindTexture(zombieVillagerProfessionLevelStone); return passNumber;
        				case 2: this.bindTexture(zombieVillagerProfessionLevelIron); return passNumber;
        				case 3: this.bindTexture(zombieVillagerProfessionLevelGold); return passNumber;
        				case 4: this.bindTexture(zombieVillagerProfessionLevelEmerald); return passNumber;
        				case 5: this.bindTexture(zombieVillagerProfessionLevelDiamond); return passNumber;
        			}
        		}*/
    		}
        }
        
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
    		if (GeneralConfig.modernVillagerSkins)
    		{
    			return zombieVillagerBaseSkin;
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
                this.zombieVillagerModel = new EFModelZombieVillager(0);
                this.field_82431_q = this.zombieVillagerModel.func_82897_a();
                this.field_82436_m = new EFModelZombieVillager(0);
                this.field_82433_n = new EFModelZombieVillager(0);
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
}