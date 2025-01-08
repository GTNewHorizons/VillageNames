package astrotibs.villagenames.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;

import org.lwjgl.opengl.GL11;

import astrotibs.villagenames.config.GeneralConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Adapted from Villager Mantle Fix by MJaroslav
 * https://github.com/MJaroslav/VillagerMantleFix/blob/1.7.10/src/main/java/mjaroslav/mcmods/villagermantlefix/client/model/ModelVillagerAlt.java
 * 
 * @author AstroTibs
 */

@SideOnly(Side.CLIENT)
public class ModelVillagerModern extends ModelVillager {

    public ModelRenderer villagerHeadwear;
    public ModelRenderer villagerHatRimHigh;
    public ModelRenderer villagerHatRimLow;

    public ModelVillagerModern(float headScale) {
        this(headScale, 0F, 64, 64); // Texture files are 64 by 64
    }

    public ModelVillagerModern(float headScale, float noseY, int textureFileWidth, int textureFileHeight) {
        super(headScale, noseY, textureFileWidth, textureFileHeight);

        float headscaleOffset = 0.5F; // How much the mantle layer gets "inflated"

        // Main headwear portion
        this.villagerHeadwear = new ModelRenderer(this).setTextureSize(textureFileWidth, textureFileHeight);
        this.villagerHeadwear.setTextureOffset(32, 0)
                .addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, headScale + headscaleOffset);
        this.villagerHeadwear.setRotationPoint(0.0F, 0.0F + noseY, 0.0F);

        // Higher Rim -- used for Farmer and Fisherman
        this.villagerHatRimHigh = new ModelRenderer(this).setTextureSize(textureFileWidth, textureFileHeight);
        int rimHighTextureOffsetX = 15;
        int rimHighTextureOffsetY = 48;
        this.villagerHatRimHigh.setTextureOffset(rimHighTextureOffsetX, rimHighTextureOffsetY);

        // The two below methods should be equivalent
        // this.villagerHatRimHigh.addBox(-8F, -6F, -8F, 16, 0, 16);//.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, headScale
        // + headscaleOffset);
        // this.villagerHatRimHigh.cubeList.add(new ModelBox(this.villagerHatRimHigh, 15, 48, -8F, -6F, -8F, 16, 0, 16,
        // 0.0F));
        // this.villagerHatRimHigh.cubeList.add(new ModelBox2(this.villagerHatRimHigh, 15, 48, -8F, -6F, -8F, 16, 0, 16,
        // 0.0F));
        // My version, which implements a singly-textured plane:
        this.villagerHatRimHigh.cubeList.add(
                new ModelPlane(
                        this.villagerHatRimHigh,
                        rimHighTextureOffsetX,
                        rimHighTextureOffsetY,
                        -8F,
                        -6F,
                        -8F,
                        16,
                        0,
                        16,
                        0.0F));
        this.villagerHatRimHigh.setRotationPoint(0.0F, 0.0F + noseY, 0.0F);

        // Lower Rim -- used for Shepherd
        this.villagerHatRimLow = new ModelRenderer(this).setTextureSize(textureFileWidth, textureFileHeight);
        int rimLowTextureOffsetX = 32;
        int rimLowTextureOffsetY = 48;
        this.villagerHatRimLow.setTextureOffset(rimLowTextureOffsetX, rimLowTextureOffsetY);
        this.villagerHatRimLow.cubeList.add(
                new ModelPlane(
                        this.villagerHatRimLow,
                        rimLowTextureOffsetX,
                        rimLowTextureOffsetY,
                        -8F,
                        -5F,
                        -8F,
                        16,
                        0,
                        16,
                        0.0F));
        this.villagerHatRimLow.setRotationPoint(0.0F, 0.0F + noseY, 0.0F);

    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        this.villagerHeadwear.rotateAngleY = this.villagerHead.rotateAngleY;
        this.villagerHeadwear.rotateAngleX = this.villagerHead.rotateAngleX;

        this.villagerHatRimHigh.rotateAngleY = this.villagerHead.rotateAngleY;
        this.villagerHatRimHigh.rotateAngleX = this.villagerHead.rotateAngleX;

        this.villagerHatRimLow.rotateAngleY = this.villagerHead.rotateAngleY;
        this.villagerHatRimLow.rotateAngleX = this.villagerHead.rotateAngleX;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);

        EntityVillager villager = (EntityVillager) entity;

        int prof = villager.getProfession();

        if (prof > 5 // This is a non-vanilla villager profession
                & !villager.isChild() // and is not a child
                & !GeneralConfig.moddedVillagerHeadwearWhitelist.contains(prof) // and is not whitelisted
                & // and...
                (GeneralConfig.moddedVillagerHeadwearBlacklist.contains(prof) // is blacklisted,
                        | !GeneralConfig.moddedVillagerHeadwear // OR headwear is disabled
                )) {
            return;
        }

        // You reach this point if the villager needs its head examined lol gottem

        if (villager.isChild()) {
            // Re-upscale baby head lmao
            GL11.glPushMatrix();
            GL11.glScalef(1.5F, 1.5F, 1.5F);
            this.villagerHead.render(f5);
            this.villagerHeadwear.render(f5);
            this.villagerHatRimHigh.render(f5);
            this.villagerHatRimLow.render(f5);
            GL11.glPopMatrix();
        } else {
            this.villagerHeadwear.render(f5);
            this.villagerHatRimHigh.render(f5);
            this.villagerHatRimLow.render(f5);
        }
    }

}
