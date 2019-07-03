package astrotibs.villagenames.client.model;

import astrotibs.villagenames.config.GeneralConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;

/**
 * Adapted from Villager Mantle Fix by MJaroslav
 * https://github.com/MJaroslav/VillagerMantleFix/blob/1.7.10/src/main/java/mjaroslav/mcmods/villagermantlefix/client/model/ModelVillagerAlt.java
 * @author AstroTibs
 */

// Added in v3.1
@SideOnly(Side.CLIENT)
public class ModelVillagerModern extends ModelVillager
{
	public ModelRenderer villagerHeadwear;
	public ModelRenderer villagerHatRimHigh;
	public ModelRenderer villagerHatRimLow;
	
	public ModelVillagerModern(float headScale)
	{
		this(headScale, 0F, 64, 64); // Texture files are 64 by 64
	}

	public ModelVillagerModern(float headScale, float noseY, int textureFileWidth, int textureFileHeight)
	{
		super(headScale, noseY, textureFileWidth, textureFileHeight);
		
		float headscaleOffset = 0.5F; // How much the mantle layer gets "inflated"
		
		// Main headwear portion
		this.villagerHeadwear = new ModelRenderer(this).setTextureSize(textureFileWidth, textureFileHeight);
		this.villagerHeadwear.setTextureOffset(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, headScale + headscaleOffset);
		this.villagerHeadwear.setRotationPoint(0.0F, 0.0F + noseY, 0.0F);
		
		// Higher Rim -- used for Farmer and Fisherman
		this.villagerHatRimHigh = new ModelRenderer(this).setTextureSize(textureFileWidth, textureFileHeight);
		int rimHighTextureOffsetX = 15;
		int rimHighTextureOffsetY = 48;
		this.villagerHatRimHigh.setTextureOffset(rimHighTextureOffsetX, rimHighTextureOffsetY);
		
		// The two below methods should be equivalent
		//this.villagerHatRimHigh.addBox(-8F, -6F, -8F, 16, 0, 16);//.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, headScale + headscaleOffset);
		//this.villagerHatRimHigh.cubeList.add(new ModelBox(this.villagerHatRimHigh, 15, 48, -8F, -6F, -8F, 16, 0, 16, 0.0F));
		//this.villagerHatRimHigh.cubeList.add(new ModelBox2(this.villagerHatRimHigh, 15, 48, -8F, -6F, -8F, 16, 0, 16, 0.0F));
		// My version, which implements a singly-textured plane:
		this.villagerHatRimHigh.cubeList.add(new ModelPlane(this.villagerHatRimHigh, rimHighTextureOffsetX, rimHighTextureOffsetY, -8F, -6F, -8F, 16, 0, 16, 0.0F));
		this.villagerHatRimHigh.setRotationPoint(0.0F, 0.0F + noseY, 0.0F);
		
		// Lower Rim -- used for Shepherd
		this.villagerHatRimLow = new ModelRenderer(this).setTextureSize(textureFileWidth, textureFileHeight);
		int rimLowTextureOffsetX = 32;
		int rimLowTextureOffsetY = 48;
		this.villagerHatRimLow.setTextureOffset(rimLowTextureOffsetX, rimLowTextureOffsetY);
		this.villagerHatRimLow.cubeList.add(new ModelPlane(this.villagerHatRimLow, rimLowTextureOffsetX, rimLowTextureOffsetY, -8F, -5F, -8F, 16, 0, 16, 0.0F));
		this.villagerHatRimLow.setRotationPoint(0.0F, 0.0F + noseY, 0.0F);
		
		/*
		// Copied over from ModelVillager: used to "puff out" additional layers
        this.villagerHead = (new ModelRenderer(this)).setTextureSize(textureFileWidth, textureFileHeight);
        this.villagerHead.setRotationPoint(0.0F, 0.0F + noseY, 0.0F);
        this.villagerHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, headScale);
        this.villagerNose = (new ModelRenderer(this)).setTextureSize(textureFileWidth, textureFileHeight);
        this.villagerNose.setRotationPoint(0.0F, noseY - 2.0F, 0.0F);
        this.villagerNose.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, headScale);
        this.villagerHead.addChild(this.villagerNose);
        this.villagerBody = (new ModelRenderer(this)).setTextureSize(textureFileWidth, textureFileHeight);
        this.villagerBody.setRotationPoint(0.0F, 0.0F + noseY, 0.0F);
        this.villagerBody.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, headScale);
        this.villagerBody.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, headScale + 0.5F);
        this.villagerArms = (new ModelRenderer(this)).setTextureSize(textureFileWidth, textureFileHeight);
        this.villagerArms.setRotationPoint(0.0F, 0.0F + noseY + 2.0F, 0.0F);
        this.villagerArms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, headScale);
        this.villagerArms.setTextureOffset(44, 22).addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, headScale);
        this.villagerArms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, headScale);
        this.rightVillagerLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(textureFileWidth, textureFileHeight);
        this.rightVillagerLeg.setRotationPoint(-2.0F, 12.0F + noseY, 0.0F);
        this.rightVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, headScale);
        this.leftVillagerLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(textureFileWidth, textureFileHeight);
        this.leftVillagerLeg.mirror = true;
        this.leftVillagerLeg.setRotationPoint(2.0F, 12.0F + noseY, 0.0F);
        this.leftVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, headScale);
		*/
		
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		this.villagerHeadwear.rotateAngleY = this.villagerHead.rotateAngleY;
		this.villagerHeadwear.rotateAngleX = this.villagerHead.rotateAngleX;
		
		this.villagerHatRimHigh.rotateAngleY = this.villagerHead.rotateAngleY;
		this.villagerHatRimHigh.rotateAngleX = this.villagerHead.rotateAngleX;
		
		this.villagerHatRimLow.rotateAngleY = this.villagerHead.rotateAngleY;
		this.villagerHatRimLow.rotateAngleX = this.villagerHead.rotateAngleX;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		
		if (
				entity instanceof EntityVillager
				// Below conditions specify only vanilla villagers
				&& ((EntityVillager)entity).getProfession() >= 0
				&& ( // Added condition on 3.1.1 to allow villager cowls
						((EntityVillager)entity).getProfession() <= 5
						|| GeneralConfig.moddedVillagerHeadwear
					)
			)
		{
			this.villagerHeadwear.render(f5);
			this.villagerHatRimHigh.render(f5);
			this.villagerHatRimLow.render(f5);
		}
	}
}
