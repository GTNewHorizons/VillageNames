package astrotibs.villagenames.client.renderer.entity;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderVillager;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Maps;

import astrotibs.villagenames.client.model.ModelVillagerModern;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.ieep.ExtendedVillager;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Adapted from Villager Mantle Fix by MJaroslav
 * https://github.com/MJaroslav/VillagerMantleFix/blob/1.7.10/src/main/java/mjaroslav/mcmods/villagermantlefix/client/renderer/entity/RenderVillagerAlt.java
 * 
 * @author AstroTibs
 */

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

    static final String VAD = "textures/entity/villager/"; // Villager address, because it's used so damn much
    static final String MIDLC = (Reference.MOD_ID).toLowerCase(); // Same with Mod ID

    // Base skin texture
    private static final ResourceLocation VILLAGER_BASE_SKIN = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("villager.png")
            .toString());

    // Biome-based types
    private static final ResourceLocation VILLAGER_TYPE_DESERT = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("type/desert.png")
            .toString());
    private static final ResourceLocation VILLAGER_TYPE_JUNGLE = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("type/jungle.png")
            .toString());
    private static final ResourceLocation VILLAGER_TYPE_PLAINS = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("type/plains.png")
            .toString());
    private static final ResourceLocation VILLAGER_TYPE_SAVANNA = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("type/savanna.png")
            .toString());
    private static final ResourceLocation VILLAGER_TYPE_SNOW = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("type/snow.png")
            .toString());
    private static final ResourceLocation VILLAGER_TYPE_SWAMP = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("type/swamp.png")
            .toString());
    private static final ResourceLocation VILLAGER_TYPE_TAIGA = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("type/taiga.png")
            .toString());
    // Custom biome types
    private static final ResourceLocation VILLAGER_TYPE_FOREST = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("type/forest.png")
            .toString());
    private static final ResourceLocation VILLAGER_TYPE_AQUATIC = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("type/aquatic.png")
            .toString());
    private static final ResourceLocation VILLAGER_TYPE_HIGHLAND = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("type/highland.png")
            .toString());
    private static final ResourceLocation VILLAGER_TYPE_MUSHROOM = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("type/mushroom.png")
            .toString());
    private static final ResourceLocation VILLAGER_TYPE_MAGICAL = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("type/magical.png")
            .toString());
    private static final ResourceLocation VILLAGER_TYPE_NETHER = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("type/nether.png")
            .toString());
    private static final ResourceLocation VILLAGER_TYPE_END = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("type/end.png")
            .toString());

    // Profession-based layer
    private static final ResourceLocation VILLAGER_PROFESSION_ARMORER = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("profession/armorer.png")
            .toString());
    private static final ResourceLocation VILLAGER_PROFESSION_BUTCHER = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("profession/butcher.png")
            .toString());
    private static final ResourceLocation VILLAGER_PROFESSION_CARTOGRAPHER = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("profession/cartographer.png")
            .toString());
    private static final ResourceLocation VILLAGER_PROFESSION_CLERIC = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("profession/cleric.png")
            .toString());
    private static final ResourceLocation VILLAGER_PROFESSION_FARMER = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("profession/farmer.png")
            .toString());
    private static final ResourceLocation VILLAGER_PROFESSION_FISHERMAN = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("profession/fisherman.png")
            .toString());
    private static final ResourceLocation VILLAGER_PROFESSION_FLETCHER = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("profession/fletcher.png")
            .toString());
    private static final ResourceLocation VILLAGER_PROFESSION_LEATHERWORKER = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("profession/leatherworker.png")
            .toString());
    private static final ResourceLocation VILLAGER_PROFESSION_LIBRARIAN = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("profession/librarian.png")
            .toString());
    private static final ResourceLocation VILLAGER_PROFESSION_MASON = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("profession/mason.png")
            .toString());
    private static final ResourceLocation VILLAGER_PROFESSION_NITWIT = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("profession/nitwit.png")
            .toString());
    private static final ResourceLocation VILLAGER_PROFESSION_SHEPHERD = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("profession/shepherd.png")
            .toString());
    private static final ResourceLocation VILLAGER_PROFESSION_TOOLSMITH = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("profession/toolsmith.png")
            .toString());
    private static final ResourceLocation VILLAGER_PROFESSION_WEAPONSMITH = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("profession/weaponsmith.png")
            .toString());

    // Profession level purses
    private static final ResourceLocation VILLAGER_PROFESSION_LEVEL_STONE = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("profession_level/stone.png")
            .toString());
    private static final ResourceLocation VILLAGER_PROFESSION_LEVEL_IRON = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("profession_level/iron.png")
            .toString());
    private static final ResourceLocation VILLAGER_PROFESSION_LEVEL_GOLD = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("profession_level/gold.png")
            .toString());
    private static final ResourceLocation VILLAGER_PROFESSION_LEVEL_EMERALD = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("profession_level/emerald.png")
            .toString());
    private static final ResourceLocation VILLAGER_PROFESSION_LEVEL_DIAMOND = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("profession_level/diamond.png")
            .toString());

    // Vanilla textures
    private static final ResourceLocation DEFAULT_OLD_NITWIT = new ResourceLocation(
        (new StringBuilder()).append(VAD)
            .append("villager.png")
            .toString());
    private static final ResourceLocation DEFAULT_OLD_FARMER = new ResourceLocation(
        (new StringBuilder()).append(VAD)
            .append("farmer.png")
            .toString());
    private static final ResourceLocation DEFAULT_OLD_LIBRARIAN = new ResourceLocation(
        (new StringBuilder()).append(VAD)
            .append("librarian.png")
            .toString());
    private static final ResourceLocation DEFAULT_OLD_PRIEST = new ResourceLocation(
        (new StringBuilder()).append(VAD)
            .append("priest.png")
            .toString());
    private static final ResourceLocation DEFAULT_OLD_SMITH = new ResourceLocation(
        (new StringBuilder()).append(VAD)
            .append("smith.png")
            .toString());
    private static final ResourceLocation DEFAULT_OLD_BUTCHER = new ResourceLocation(
        (new StringBuilder()).append(VAD)
            .append("butcher.png")
            .toString());

    // Skin tones, arranged lightest to darkest
    private static final ResourceLocation VILLAGER_SKIN_TONE_LIGHT3 = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("skintone/l3.png")
            .toString());
    private static final ResourceLocation VILLAGER_SKIN_TONE_LIGHT2 = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("skintone/l2.png")
            .toString());
    private static final ResourceLocation VILLAGER_SKIN_TONE_LIGHT1 = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("skintone/l1.png")
            .toString());
    private static final ResourceLocation VILLAGER_SKIN_TONE_MEDIUM = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("skintone/m0.png")
            .toString()); // Identical to default skin
    private static final ResourceLocation VILLAGER_SKIN_TONE_DARK1 = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("skintone/d1.png")
            .toString());
    private static final ResourceLocation VILLAGER_SKIN_TONE_DARK2 = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("skintone/d2.png")
            .toString());
    private static final ResourceLocation VILLAGER_SKIN_TONE_DARK3 = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("skintone/d3.png")
            .toString());
    private static final ResourceLocation VILLAGER_SKIN_TONE_DARK4 = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("skintone/d4.png")
            .toString());
    private static final ResourceLocation VILLAGER_SKIN_TIBS = new ResourceLocation(
        MIDLC,
        (new StringBuilder()).append(VAD)
            .append("skintone/t_v.png")
            .toString());

    /**
     * Machinery for modular textures, adapted from RenderHorse
     */

    // private String[] layeredTextureAddressArray = new String[4]; // Holds the different layer textures as resource
    // address strings
    private static final Map skinComboHashmap = Maps.newHashMap(); // Populates a hash map with various combinations so
                                                                   // you don't have to constantly ascertain them on the
                                                                   // fly
    // private String skinComboHashKey; // String that will be the hashmap key corresponding to the particular
    // biome/career combination

    // Made this 2D so that I can always make sure to add the proper key
    private static final String[][] skintoneTextures = new String[][] { { VILLAGER_SKIN_TONE_DARK4.toString(), "std4" },
        { VILLAGER_SKIN_TONE_DARK3.toString(), "std3" }, { VILLAGER_SKIN_TONE_DARK2.toString(), "std2" },
        { VILLAGER_SKIN_TONE_DARK1.toString(), "std1" }, { VILLAGER_SKIN_TONE_MEDIUM.toString(), "stm0" },
        { VILLAGER_SKIN_TONE_LIGHT1.toString(), "stl1" }, { VILLAGER_SKIN_TONE_LIGHT2.toString(), "stl2" },
        { VILLAGER_SKIN_TONE_LIGHT3.toString(), "stl3" }, };

    // Made this 2D so that I can always make sure to add the proper key
    private static final String[][] biomeTypeTextures = new String[][] { { VILLAGER_TYPE_PLAINS.toString(), "pla" },
        { VILLAGER_TYPE_MAGICAL.toString(), "mag" }, { VILLAGER_TYPE_HIGHLAND.toString(), "hig" },
        { VILLAGER_TYPE_FOREST.toString(), "for" }, { VILLAGER_TYPE_AQUATIC.toString(), "aqu" },
        { VILLAGER_TYPE_JUNGLE.toString(), "jun" }, { VILLAGER_TYPE_SWAMP.toString(), "swa" },
        { VILLAGER_TYPE_TAIGA.toString(), "tai" }, { VILLAGER_TYPE_DESERT.toString(), "des" },
        { VILLAGER_TYPE_SAVANNA.toString(), "sav" }, { VILLAGER_TYPE_MUSHROOM.toString(), "mus" },
        { VILLAGER_TYPE_SNOW.toString(), "sno" }, { VILLAGER_TYPE_END.toString(), "end" },
        { VILLAGER_TYPE_NETHER.toString(), "net" }, };

    private static final String[][] careerTextures = new String[][] { { VILLAGER_PROFESSION_FARMER.toString(), "far" }, // 0
        { VILLAGER_PROFESSION_FISHERMAN.toString(), "fis" }, { VILLAGER_PROFESSION_SHEPHERD.toString(), "she" },
        { VILLAGER_PROFESSION_FLETCHER.toString(), "fle" }, { VILLAGER_PROFESSION_LIBRARIAN.toString(), "lib" }, // 4
        { VILLAGER_PROFESSION_CARTOGRAPHER.toString(), "car" }, { VILLAGER_PROFESSION_CLERIC.toString(), "cle" }, // 6
        { VILLAGER_PROFESSION_ARMORER.toString(), "arm" }, { VILLAGER_PROFESSION_WEAPONSMITH.toString(), "wea" },
        { VILLAGER_PROFESSION_TOOLSMITH.toString(), "too" }, // 9
        { VILLAGER_PROFESSION_MASON.toString(), "mas" }, { VILLAGER_PROFESSION_BUTCHER.toString(), "but" }, // 11
        { VILLAGER_PROFESSION_LEATHERWORKER.toString(), "lea" }, { VILLAGER_PROFESSION_NITWIT.toString(), "nit" }, // 13
    };

    private static final String[][] profLevelTextures = new String[][] { { null, "pl0" }, // Added so that some
                                                                                          // villagers can have no
                                                                                          // badge.
        { VILLAGER_PROFESSION_LEVEL_STONE.toString(), "pl1" }, { VILLAGER_PROFESSION_LEVEL_IRON.toString(), "pl2" },
        { VILLAGER_PROFESSION_LEVEL_GOLD.toString(), "pl3" }, { VILLAGER_PROFESSION_LEVEL_EMERALD.toString(), "pl4" },
        { VILLAGER_PROFESSION_LEVEL_DIAMOND.toString(), "pl5" }, };

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(EntityVillager villager) {
        int profession = villager.getProfession();

        if (GeneralConfig.modernVillagerSkins & profession >= 0) {
            return this.getHashmappedSkinCombo(villager);
        } else {
            // Condition for modern villager skins OFF
            switch (profession) {
                case 0:
                    return DEFAULT_OLD_FARMER;
                case 1:
                    return DEFAULT_OLD_LIBRARIAN;
                case 2:
                    return DEFAULT_OLD_PRIEST;
                case 3:
                    return DEFAULT_OLD_SMITH;
                case 4:
                    return DEFAULT_OLD_BUTCHER;
                default:
                    return VillagerRegistry.getVillagerSkin(profession, DEFAULT_OLD_NITWIT);
            }
        }
    }

    private ResourceLocation getHashmappedSkinCombo(EntityVillager villager) {
        String s = this.getModularVillagerTexture(villager);
        ResourceLocation resourcelocation = (ResourceLocation) skinComboHashmap.get(s);

        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s);
            Minecraft.getMinecraft()
                .getTextureManager()
                .loadTexture(resourcelocation, new LayeredTexture(this.getVariantTexturePaths(villager)));
            skinComboHashmap.put(s, resourcelocation);
        }

        return resourcelocation;
    }

    /**
     * Index 0 will be the string array providing the rendering layers for the skin. Index 1 will be the rendering hash
     * key.
     */
    @SideOnly(Side.CLIENT)
    private Object[] setModularVillagerTexturePaths(EntityVillager villager) {
        final ExtendedVillager ev = ExtendedVillager.get(villager);

        StringBuilder skinComboHashKey = (new StringBuilder()).append("villager/");

        String[] layeredTextureAddressArray = new String[4];

        // Reset the layer array
        layeredTextureAddressArray[0] = VILLAGER_BASE_SKIN.toString(); // Leaving this in by default just in case
                                                                       // something goes wrong
        layeredTextureAddressArray[1] = null;
        layeredTextureAddressArray[2] = null;
        layeredTextureAddressArray[3] = null;

        // Set the indexing values, and clamp them just in case
        int biometype = MathHelper.clamp_int(ev.getBiomeType(), 0, biomeTypeTextures.length - 1);
        int career = GeneralConfig.villagerCareers ? ev.getCareer() : -1;
        int profession = villager.getProfession();
        int extended_profession = ev.getProfession();
        int proflevel = (villager.isChild() | profession == 5) ? 0
            : MathHelper.clamp_int(ev.getProfessionLevel(), 0, profLevelTextures.length - 1);
        int skinTone_i = GeneralConfig.villagerSkinTones ? ev.getSkinTone() : 0;
        MathHelper.clamp_int(skinTone_i = (skinTone_i == -99 ? 0 : skinTone_i) + 4, 0, skintoneTextures.length - 1);

        // ---------------------------- //
        // --- PART 0: set the skin --- //
        // ---------------------------- //

        // give @p minecraft:name_tag 1 0 {display:{Name:"Tibs"}}
        // give @p minecraft:name_tag 1 0 {display:{Name:"AstroTibs"}}

        String trimmed_lc_villager_name = villager.getCustomNameTag()
            .toLowerCase()
            .trim();
        if (!trimmed_lc_villager_name.equals(Reference.NAME_TIBS)
            & !trimmed_lc_villager_name.equals(Reference.NAME_ASTROTIBS)
            & !(trimmed_lc_villager_name.length() >= 11 && trimmed_lc_villager_name.substring(0, 11)
                .equals(Reference.NAME_ASTROTIBS_OPENP))
            & !(trimmed_lc_villager_name.length() >= 6 && trimmed_lc_villager_name.substring(0, 6)
                .equals(Reference.NAME_TIBS_OPENP))) {
            layeredTextureAddressArray[0] = skintoneTextures[skinTone_i][0];
            skinComboHashKey.append(skintoneTextures[skinTone_i][1]);
        } else {
            layeredTextureAddressArray[0] = VILLAGER_SKIN_TIBS.toString();
            skinComboHashKey.append("tibs");
        }

        // ---------------------------------- //
        // --- PART 1: set the biome type --- //
        // ---------------------------------- //

        layeredTextureAddressArray[1] = biomeTypeTextures[biometype][0];
        skinComboHashKey.append(biomeTypeTextures[biometype][1]);

        // ----------------------------- //
        // --- PART 2: set the career--- //
        // ----------------------------- //

        int indexofmodprof = GeneralConfig.professionID_a.indexOf(extended_profession);

        if (!villager.isChild()) {
            if (extended_profession > (GeneralConfig.enableNitwit ? 5 : 4) // Is non-vanilla
                & indexofmodprof > -1 // Has a skin asset mapping
                && !((String) GeneralConfig.careerAsset_a.get(indexofmodprof)).equals("")) // That mapping isn't
                                                                                           // blank
            {
                // This is a modded profession ID with a supported skin

                final String profRootName = (String) (GeneralConfig.careerAsset_a.get(indexofmodprof));
                final ResourceLocation modCareerSkin = new ResourceLocation(
                    MIDLC,
                    (new StringBuilder()).append(VAD)
                        .append("profession/")
                        .append(profRootName)
                        .append(".png")
                        .toString());

                layeredTextureAddressArray[2] = modCareerSkin.toString();
                skinComboHashKey.append("_")
                    .append(profRootName);
            } else if (extended_profession >= 0 & extended_profession <= (GeneralConfig.enableNitwit ? 5 : 4)) // This
                                                                                                               // is
                                                                                                               // vanilla
                                                                                                               // skin
                                                                                                               // or is
                                                                                                               // otherwise
                                                                                                               // unsupported
            {
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
                layeredTextureAddressArray[2] = careerTextures[careerIndex][0];
                skinComboHashKey.append("_")
                    .append(careerTextures[careerIndex][1]);
            } else {
                skinComboHashKey.append("_non");
            }
        }

        // ---------------------------------------- //
        // --- PART 3: set the profession level --- //
        // ---------------------------------------- //

        layeredTextureAddressArray[3] = profLevelTextures[proflevel][0];
        skinComboHashKey.append("_")
            .append(profLevelTextures[proflevel][1]);

        // return skinComboHashKey;
        return new Object[] { layeredTextureAddressArray, skinComboHashKey.toString() };
    }

    @SideOnly(Side.CLIENT)
    public String getModularVillagerTexture(EntityVillager villager) {
        return (String) (setModularVillagerTexturePaths(villager))[1];
    }

    @SideOnly(Side.CLIENT)
    public String[] getVariantTexturePaths(EntityVillager villager) {
        return (String[]) (this.setModularVillagerTexturePaths(villager))[0];
    }

}
