package astrotibs.villagenames.client.renderer.entity;

import java.util.Map;

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

import com.google.common.collect.Maps;

import astrotibs.villagenames.client.model.ModelZombieVillagerModern;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.ieep.ExtendedZombieVillager;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * This is essentially the vanilla class, but modified to accommodate the modern layered skins.
 * 
 * @author AstroTibs
 */

@SideOnly(Side.CLIENT)
public class RenderZombieVillagerModern extends RenderBiped {
    // ------------------------------ //
    // --- Skin resource elements --- //
    // ------------------------------ //

    static final String ZVAD = "textures/entity/zombie_villager/";

    // Base skin texture
    private static final ResourceLocation ZOMBIE_VILLAGER_BASE_SKIN = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("zombie_villager.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_TIBS_SKIN = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("t_zv.png").toString());

    // Biome-based types
    private static final ResourceLocation ZOMBIE_VILLAGER_TYPE_DESERT = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("type/desert.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_TYPE_JUNGLE = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("type/jungle.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_TYPE_PLAINS = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("type/plains.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_TYPE_SAVANNA = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("type/savanna.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_TYPE_SNOW = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("type/snow.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_TYPE_SWAMP = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("type/swamp.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_TYPE_TAIGA = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("type/taiga.png").toString());
    // Custom biome types
    private static final ResourceLocation ZOMBIE_VILLAGER_TYPE_FOREST = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("type/forest.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_TYPE_AQUATIC = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("type/aquatic.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_TYPE_HIGHLAND = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("type/highland.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_TYPE_MUSHROOM = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("type/mushroom.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_TYPE_MAGICAL = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("type/magical.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_TYPE_NETHER = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("type/nether.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_TYPE_END = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("type/end.png").toString());

    // Profession-based layer
    private static final ResourceLocation ZOMBIE_VILLAGER_PROFESSION_ARMORER = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("profession/armorer.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_PROFESSION_BUTCHER = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("profession/butcher.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_PROFESSION_CARTOGRAPHER = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("profession/cartographer.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_PROFESSION_CLERIC = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("profession/cleric.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_PROFESSION_FARMER = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("profession/farmer.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_PROFESSION_FISHERMAN = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("profession/fisherman.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_PROFESSION_FLETCHER = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("profession/fletcher.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_PROFESSION_LEATHERWORKER = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("profession/leatherworker.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_PROFESSION_LIBRARIAN = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("profession/librarian.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_PROFESSION_MASON = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("profession/mason.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_PROFESSION_NITWIT = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("profession/nitwit.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_PROFESSION_SHEPHERD = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("profession/shepherd.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_PROFESSION_TOOLSMITH = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("profession/toolsmith.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_PROFESSION_WEAPONSMITH = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("profession/weaponsmith.png").toString());

    // Profession level purses
    private static final ResourceLocation ZOMBIE_VILLAGER_PROFESSION_LEVEL_STONE = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("profession_level/stone.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_PROFESSION_LEVEL_IRON = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("profession_level/iron.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_PROFESSION_LEVEL_GOLD = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("profession_level/gold.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_PROFESSION_LEVEL_EMERALD = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("profession_level/emerald.png").toString());
    private static final ResourceLocation ZOMBIE_VILLAGER_PROFESSION_LEVEL_DIAMOND = new ResourceLocation(
            (Reference.MOD_ID).toLowerCase(),
            (new StringBuilder()).append(ZVAD).append("profession_level/diamond.png").toString());

    // Vanilla textures
    private static final ResourceLocation ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation(
            "textures/entity/zombie_pigman.png");
    private static final ResourceLocation ZOMBIE_TEXTURE = new ResourceLocation("textures/entity/zombie/zombie.png");
    private static final ResourceLocation ZOMBIE_VILLAGER_TEXTURE = new ResourceLocation(
            "textures/entity/zombie/zombie_villager.png");

    private ModelBiped field_82434_o;
    private ModelZombieVillagerModern zombieVillagerModel;
    protected ModelBiped field_82437_k;
    protected ModelBiped field_82435_l;
    protected ModelBiped field_82436_m;
    protected ModelBiped field_82433_n;
    private int field_82431_q = 1;
    private static final String __OBFID = "CL_00001037";

    public RenderZombieVillagerModern() {
        super(new ModelZombie(), 0.5F, 1.0F);
        this.field_82434_o = this.modelBipedMain;
        this.zombieVillagerModel = new ModelZombieVillagerModern(0);
        this.setRenderPassModel(new ModelZombieVillagerModern(0.1F));
    }

    @Override
    protected void func_82421_b() {
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
    protected int shouldRenderPass(EntityZombie zombie, int passNumber, float progress) {
        this.modelSetter(zombie); // Whether or not this will render as a villager-type zombie
        return super.shouldRenderPass((EntityLiving) zombie, passNumber, progress);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityZombie zombie, double x, double y, double z, float entityYaw, float partialTicks) {
        this.modelSetter(zombie);
        super.doRender((EntityLiving) zombie, x, y, z, entityYaw, partialTicks);
    }

    protected void renderEquippedItems(EntityZombie p_77029_1_, float p_77029_2_) {
        this.modelSetter(p_77029_1_);
        super.renderEquippedItems((EntityLiving) p_77029_1_, p_77029_2_);
    }

    private void modelSetter(EntityZombie zombie) {
        if (zombie.isVillager()) {
            if (this.field_82431_q != this.zombieVillagerModel.func_82897_a()) {
                this.zombieVillagerModel = new ModelZombieVillagerModern(0);
                this.field_82431_q = this.zombieVillagerModel.func_82897_a();
                this.field_82436_m = new ModelZombieVillagerModern(0);
                this.field_82433_n = new ModelZombieVillagerModern(0);
            }

            this.mainModel = this.zombieVillagerModel;
            this.field_82423_g = this.field_82436_m;
            this.field_82425_h = this.field_82433_n;
        } else {
            this.mainModel = this.field_82434_o;
            this.field_82423_g = this.field_82437_k;
            this.field_82425_h = this.field_82435_l;
        }

        this.modelBipedMain = (ModelBiped) this.mainModel;
    }

    protected void rotateCorpse(EntityZombie entityLiving, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
        if (entityLiving.isConverting()) {
            p_77043_3_ += (float) (Math.cos((double) entityLiving.ticksExisted * 3.25D) * Math.PI * 0.25D);
        }

        super.rotateCorpse(entityLiving, p_77043_2_, p_77043_3_, p_77043_4_);
    }

    @Override
    protected void renderEquippedItems(EntityLiving p_77029_1_, float p_77029_2_) {
        this.renderEquippedItems((EntityZombie) p_77029_1_, p_77029_2_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(EntityLiving p_110775_1_) {
        return this.getEntityTexture((EntityZombie) p_110775_1_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    @Override
    public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_,
            float p_76986_8_, float p_76986_9_) {
        this.doRender((EntityZombie) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    @Override
    protected int shouldRenderPass(EntityLiving p_77032_1_, int p_77032_2_, float p_77032_3_) {
        return this.shouldRenderPass((EntityZombie) p_77032_1_, p_77032_2_, p_77032_3_);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    @Override
    protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
        return this.shouldRenderPass((EntityZombie) p_77032_1_, p_77032_2_, p_77032_3_);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase p_77029_1_, float p_77029_2_) {
        this.renderEquippedItems((EntityZombie) p_77029_1_, p_77029_2_);
    }

    @Override
    protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
        this.rotateCorpse((EntityZombie) p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    @Override
    public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_,
            float p_76986_8_, float p_76986_9_) {
        this.doRender((EntityZombie) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return this.getEntityTexture((EntityZombie) p_110775_1_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    @Override
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
            float p_76986_9_) {
        this.doRender((EntityZombie) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    /**
     * Machinery for modular textures, adapted from RenderHorse
     */

    // summon Zombie ~ ~ ~ {IsVillager:1}

    // private String[] layeredTextureAddressArray = new String[4]; // Holds the different layer textures as resource
    // address strings
    private static final Map skinComboHashmap = Maps.newHashMap(); // Populates a hash map with various combinations so
                                                                   // you don't have to constantly ascertain them on the
                                                                   // fly
    // private String skinComboHashKey; // String that will be the hashmap key corresponding to the particular
    // biome/career combination

    // Made this 2D so that I can always make sure to add the proper key
    private static final String[][] biomeTypeTextures = new String[][] {
            { ZOMBIE_VILLAGER_TYPE_PLAINS.toString(), "pla" }, { ZOMBIE_VILLAGER_TYPE_MAGICAL.toString(), "mag" },
            { ZOMBIE_VILLAGER_TYPE_HIGHLAND.toString(), "hig" }, { ZOMBIE_VILLAGER_TYPE_FOREST.toString(), "for" },
            { ZOMBIE_VILLAGER_TYPE_AQUATIC.toString(), "aqu" }, { ZOMBIE_VILLAGER_TYPE_JUNGLE.toString(), "jun" },
            { ZOMBIE_VILLAGER_TYPE_SWAMP.toString(), "swa" }, { ZOMBIE_VILLAGER_TYPE_TAIGA.toString(), "tai" },
            { ZOMBIE_VILLAGER_TYPE_DESERT.toString(), "des" }, { ZOMBIE_VILLAGER_TYPE_SAVANNA.toString(), "sav" },
            { ZOMBIE_VILLAGER_TYPE_MUSHROOM.toString(), "mus" }, { ZOMBIE_VILLAGER_TYPE_SNOW.toString(), "sno" },
            { ZOMBIE_VILLAGER_TYPE_END.toString(), "end" }, { ZOMBIE_VILLAGER_TYPE_NETHER.toString(), "net" }, };

    private static final String[][] careerTextures = new String[][] {
            { ZOMBIE_VILLAGER_PROFESSION_FARMER.toString(), "far" }, // 0
            { ZOMBIE_VILLAGER_PROFESSION_FISHERMAN.toString(), "fis" },
            { ZOMBIE_VILLAGER_PROFESSION_SHEPHERD.toString(), "she" },
            { ZOMBIE_VILLAGER_PROFESSION_FLETCHER.toString(), "fle" },
            { ZOMBIE_VILLAGER_PROFESSION_LIBRARIAN.toString(), "lib" }, // 4
            { ZOMBIE_VILLAGER_PROFESSION_CARTOGRAPHER.toString(), "car" },
            { ZOMBIE_VILLAGER_PROFESSION_CLERIC.toString(), "cle" }, // 6
            { ZOMBIE_VILLAGER_PROFESSION_ARMORER.toString(), "arm" },
            { ZOMBIE_VILLAGER_PROFESSION_WEAPONSMITH.toString(), "wea" },
            { ZOMBIE_VILLAGER_PROFESSION_TOOLSMITH.toString(), "too" }, // 9
            { ZOMBIE_VILLAGER_PROFESSION_MASON.toString(), "mas" },
            { ZOMBIE_VILLAGER_PROFESSION_BUTCHER.toString(), "but" }, // 11
            { ZOMBIE_VILLAGER_PROFESSION_LEATHERWORKER.toString(), "lea" },
            { ZOMBIE_VILLAGER_PROFESSION_NITWIT.toString(), "nit" }, // 13
    };

    private static final String[][] profLevelTextures = new String[][] {
            { ZOMBIE_VILLAGER_PROFESSION_LEVEL_STONE.toString(), "pl1" },
            { ZOMBIE_VILLAGER_PROFESSION_LEVEL_IRON.toString(), "pl2" },
            { ZOMBIE_VILLAGER_PROFESSION_LEVEL_GOLD.toString(), "pl3" },
            { ZOMBIE_VILLAGER_PROFESSION_LEVEL_EMERALD.toString(), "pl4" },
            { ZOMBIE_VILLAGER_PROFESSION_LEVEL_DIAMOND.toString(), "pl5" }, };

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityZombie zombie) {
        if (zombie instanceof EntityPigZombie) { // Is a zombie pigman
            return ZOMBIE_PIGMAN_TEXTURE;
        } else if (zombie.isVillager()) // Is a zombie villager
        {
            if (GeneralConfig.modernZombieSkins) {
                return this.getHashmappedSkinCombo(zombie);
            } else {
                return ZOMBIE_VILLAGER_TEXTURE;
            }
        } else { // Is an ordinary zombie
            return ZOMBIE_TEXTURE; // The default zombie skin
        }
    }

    private ResourceLocation getHashmappedSkinCombo(EntityZombie zombievillager) {
        String s = this.getModularZombieVillagerTexture(zombievillager);
        ResourceLocation resourcelocation = (ResourceLocation) skinComboHashmap.get(s);

        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s);
            Minecraft.getMinecraft().getTextureManager()
                    .loadTexture(resourcelocation, new LayeredTexture(this.getVariantTexturePaths(zombievillager)));
            skinComboHashmap.put(s, resourcelocation);
        }

        return resourcelocation;
    }

    /**
     * Index 0 will be the string array providing the rendering layers for the skin. Index 1 will be the rendering hash
     * key.
     */
    @SideOnly(Side.CLIENT)
    private Object[] setModularZombieVillagerTexturePaths(EntityZombie zombievillager) {
        final ExtendedZombieVillager ieep = ExtendedZombieVillager.get(zombievillager);

        StringBuilder skinComboHashKey = (new StringBuilder()).append("zombievillager/");

        String[] layeredTextureAddressArray = new String[4];

        // summon Zombie ~ ~ ~ {IsVillager:1}

        // give @p minecraft:name_tag 1 0 {display:{Name:"Tibs"}}
        // give @p minecraft:name_tag 1 0 {display:{Name:"AstroTibs"}}

        // Reset the layer array
        String trimmed_lc_zombievillager_name = zombievillager.getCustomNameTag().toLowerCase().trim();
        if (!trimmed_lc_zombievillager_name.equals(Reference.NAME_TIBS)
                & !trimmed_lc_zombievillager_name.equals(Reference.NAME_ASTROTIBS)
                & !(trimmed_lc_zombievillager_name.length() >= 11
                        && trimmed_lc_zombievillager_name.substring(0, 11).equals(Reference.NAME_ASTROTIBS_OPENP))
                & !(trimmed_lc_zombievillager_name.length() >= 6
                        && trimmed_lc_zombievillager_name.substring(0, 6).equals(Reference.NAME_TIBS_OPENP))) {
            layeredTextureAddressArray[0] = ZOMBIE_VILLAGER_BASE_SKIN.toString();
            skinComboHashKey.append("zomb_");
        } else {
            layeredTextureAddressArray[0] = ZOMBIE_VILLAGER_TIBS_SKIN.toString();
            skinComboHashKey.append("tibs_");
        }
        layeredTextureAddressArray[1] = null;
        layeredTextureAddressArray[2] = null;
        layeredTextureAddressArray[3] = null;

        // Set the indexing values, and clamp them just in case
        int biometype = MathHelper.clamp_int(ieep.getBiomeType(), 0, biomeTypeTextures.length - 1);
        int career = GeneralConfig.villagerCareers ? ieep.getCareer() : -1;
        int proflevel = MathHelper.clamp_int(ieep.getProfessionLevel(), 0, profLevelTextures.length - 1);

        // ---------------------------- //
        // --- PART 0: set the skin --- //
        // ---------------------------- //

        // TODO - Maybe I'll add differing zombie skin tones in the future.

        // ---------------------------------- //
        // --- PART 1: set the biome type --- //
        // ---------------------------------- //

        layeredTextureAddressArray[1] = biomeTypeTextures[biometype][0];
        skinComboHashKey.append(biomeTypeTextures[biometype][1]);

        // ----------------------------- //
        // --- PART 2: set the career--- //
        // ----------------------------- //

        int extended_profession = ieep.getProfession();
        int indexofmodprof = GeneralConfig.professionID_a.indexOf(extended_profession);

        if (!zombievillager.isChild()) {
            if (extended_profession > (GeneralConfig.enableNitwit ? 5 : 4) // Is non-vanilla
                    & indexofmodprof > -1 // Has a skin asset mapping
            // && !((String) (moddedVillagerCareerSkins.get("zombieCareerAsset")).get(indexofmodprof)).equals("") ) //
            // That mapping isn't blank
            ) {
                // This is a modded profession ID with a supported skin -- possibly.

                // If there is no zombie or non-zombie version, just default the asset to the Nitwit style.
                ResourceLocation modCareerSkin = ZOMBIE_VILLAGER_PROFESSION_NITWIT;
                String profRootName = "non";

                // First check if there is an explicit zombie profession texture.
                profRootName = ((String) (GeneralConfig.zombieCareerAsset_a).get(indexofmodprof));

                if (profRootName.equals("")) {
                    // There is none, so check the non-zombie version
                    profRootName = ((String) (GeneralConfig.careerAsset_a).get(indexofmodprof));

                    if (!profRootName.equals("")) {
                        // A non-zombie texture was found.
                        modCareerSkin = new ResourceLocation(
                                (Reference.MOD_ID).toLowerCase(),
                                (new StringBuilder()).append("textures/entity/villager/profession/")
                                        .append(profRootName).append(".png").toString());
                        layeredTextureAddressArray[2] = modCareerSkin.toString();
                    }
                } else {
                    // A zombie texture was found.
                    modCareerSkin = new ResourceLocation(
                            (Reference.MOD_ID).toLowerCase(),
                            (new StringBuilder()).append(ZVAD).append("profession/").append(profRootName).append(".png")
                                    .toString());
                    layeredTextureAddressArray[2] = modCareerSkin.toString();
                }

                skinComboHashKey.append("_").append(profRootName);
            } else if (extended_profession >= 0 & extended_profession <= (GeneralConfig.enableNitwit ? 5 : 4)) // This
                                                                                                               // is
                                                                                                               // vanilla
                                                                                                               // skin
                                                                                                               // or is
                                                                                                               // otherwise
                                                                                                               // unsupported
            {
                // Use the profession and career values to zero in on the value stored in the careerTextures array
                int careerIndex = -1;
                switch (extended_profession) {
                    case 0: // Farmer type
                        switch (career) {
                            default:
                            case 1:
                                careerIndex = 0;
                                break;
                            case 2:
                                careerIndex = 1;
                                break;
                            case 3:
                                careerIndex = 2;
                                break;
                            case 4:
                                careerIndex = 3;
                                break;
                        }
                        break;

                    case 1: // Librarian type
                        switch (career) {
                            default:
                            case 1:
                                careerIndex = 4;
                                break;
                            case 2:
                                careerIndex = 5;
                                break;
                        }
                        break;

                    case 2: // Priest type
                        switch (career) {
                            default:
                            case 1:
                                careerIndex = 6;
                                break;
                        }
                        break;

                    case 3: // Smith type
                        switch (career) {
                            case 1:
                                careerIndex = 7;
                                break;
                            case 2:
                                careerIndex = 8;
                                break;
                            default:
                            case 3:
                                careerIndex = 9;
                                break;
                            case 4:
                                careerIndex = 10;
                                break;
                        }
                        break;

                    case 4: // Butcher type
                        switch (career) {
                            default:
                            case 1:
                                careerIndex = 11;
                                break;
                            case 2:
                                careerIndex = 12;
                                break;
                        }
                        break;

                    case 5: // Nitwit type
                        switch (career) {
                            default:
                            case 1:
                                careerIndex = 13;
                                break;
                        }
                        break;

                    default: // No profession skin
                }

                // Set the career
                layeredTextureAddressArray[2] = careerTextures[careerIndex][0]; // TODO - IOOB exception: careerIndex is
                                                                                // -1?
                skinComboHashKey.append("_").append(careerTextures[careerIndex][1]);
            } else {
                skinComboHashKey.append("_non");
            }
        }

        // ---------------------------------------- //
        // --- PART 3: set the profession level --- //
        // ---------------------------------------- //

        layeredTextureAddressArray[3] = null;// profLevelTextures[proflevel][0]; // Left off for now: no need to see
                                             // prof level
        skinComboHashKey.append("_").append(profLevelTextures[proflevel][1]);

        // return skinComboHashKey;
        return new Object[] { layeredTextureAddressArray, skinComboHashKey.toString() };
    }

    @SideOnly(Side.CLIENT)
    public String getModularZombieVillagerTexture(EntityZombie zombievillager) {
        return (String) (setModularZombieVillagerTexturePaths(zombievillager))[1];
    }

    @SideOnly(Side.CLIENT)
    public String[] getVariantTexturePaths(EntityZombie zombievillager) {
        return (String[]) (this.setModularZombieVillagerTexturePaths(zombievillager))[0];
    }

}
