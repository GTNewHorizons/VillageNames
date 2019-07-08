package astrotibs.villagenames.client.model;

import org.lwjgl.opengl.GL11;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.ieep.ExtendedZombieVillager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;

/**
 * Copied from Et Futurum
 * https://github.com/ganymedes01/Et-Futurum/blob/master/src/main/java/ganymedes01/etfuturum/client/model/ModelVillagerZombie.java
 * @author ganymedes01
 */

@SideOnly(Side.CLIENT)
public class ModelZombieVillagerModern extends ModelZombie {
	
	// Added in v3.1
	public ModelRenderer zombieVillagerHeadwear;
	public ModelRenderer zombieVillagerHatRimHigh;
	public ModelRenderer zombieVillagerHatRimLow;
	
	// Changed in v3.1
	public ModelZombieVillagerModern(float headScale) {
		this(headScale, 0F, 64, 64);
	}
	
    public int func_82897_a()
    {
        return 10;
    }	
	
	// Changed in v3.1
	public ModelZombieVillagerModern(float headScale, float noseY, int textureFileWidth, int textureFileHeight) {
		
		// Changed in v3.1
		super(headScale, noseY, textureFileWidth, textureFileHeight);
		
		
		// Added in v3.1
		float headscaleOffset = 0.5F; // How much the mantle layer gets "inflated"
		
		// Main headwear portion
		this.zombieVillagerHeadwear = new ModelRenderer(this).setTextureSize(textureFileWidth, textureFileHeight);
		this.zombieVillagerHeadwear.setTextureOffset(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, headScale + headscaleOffset); // Changed in v3.1
		this.zombieVillagerHeadwear.setRotationPoint(0.0F, 0.0F + noseY, 0.0F);

		// Higher Rim -- used for Farmer and Fisherman
		this.zombieVillagerHatRimHigh = new ModelRenderer(this).setTextureSize(textureFileWidth, textureFileHeight);
		int rimHighTextureOffsetX = 15;
		int rimHighTextureOffsetY = 48;
		this.zombieVillagerHatRimHigh.setTextureOffset(rimHighTextureOffsetX, rimHighTextureOffsetY);
		
		this.zombieVillagerHatRimHigh.cubeList.add(new ModelPlane(this.zombieVillagerHatRimHigh, rimHighTextureOffsetX, rimHighTextureOffsetY, -8F, -6F, -8F, 16, 0, 16, 0.0F));
		this.zombieVillagerHatRimHigh.setRotationPoint(0.0F, 0.0F + noseY, 0.0F);
		
		// Lower Rim -- used for Shepherd
		this.zombieVillagerHatRimLow = new ModelRenderer(this).setTextureSize(textureFileWidth, textureFileHeight);
		int rimLowTextureOffsetX = 32;
		int rimLowTextureOffsetY = 48;
		this.zombieVillagerHatRimLow.setTextureOffset(rimLowTextureOffsetX, rimLowTextureOffsetY);
		this.zombieVillagerHatRimLow.cubeList.add(new ModelPlane(this.zombieVillagerHatRimLow, rimLowTextureOffsetX, rimLowTextureOffsetY, -8F, -5F, -8F, 16, 0, 16, 0.0F));
		this.zombieVillagerHatRimLow.setRotationPoint(0.0F, 0.0F + noseY, 0.0F);
		
		
		
		bipedHeadwear.isHidden = true;
		
		bipedHead = new ModelRenderer(this).setTextureSize(textureFileWidth, textureFileHeight);
		bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, headScale);
		
		ModelRenderer nose = new ModelRenderer(this).setTextureSize(textureFileWidth, textureFileHeight);
		nose.setRotationPoint(0.0F, -2.0F, 0.0F);
		nose.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, headScale);
		bipedHead.addChild(nose);
		
		bipedBody = new ModelRenderer(this).setTextureSize(textureFileWidth, textureFileHeight);
		bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		bipedBody.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, headScale);
		bipedBody.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, headScale + headscaleOffset); // Changed in v3.1
		
		bipedRightArm = new ModelRenderer(this, 44, 22).setTextureSize(textureFileWidth, textureFileHeight); // Changed in v3.1
		bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, headScale);
		bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		
		bipedLeftArm = new ModelRenderer(this, 44, 22).setTextureSize(textureFileWidth, textureFileHeight); // Changed in v3.1
		bipedLeftArm.mirror = true;
		bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, headScale);
		bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		
		bipedRightLeg = new ModelRenderer(this, 0, 22).setTextureSize(textureFileWidth, textureFileHeight);
		bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, headScale);
		
		bipedLeftLeg = new ModelRenderer(this, 0, 22).setTextureSize(textureFileWidth, textureFileHeight);
		bipedLeftLeg.mirror = true;
		bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, headScale);
	}
	
	// Added in v3.1
	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		this.zombieVillagerHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
		this.zombieVillagerHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
		
		this.zombieVillagerHatRimHigh.rotateAngleY = this.bipedHead.rotateAngleY;
		this.zombieVillagerHatRimHigh.rotateAngleX = this.bipedHead.rotateAngleX;
		
		this.zombieVillagerHatRimLow.rotateAngleY = this.bipedHead.rotateAngleY;
		this.zombieVillagerHatRimLow.rotateAngleX = this.bipedHead.rotateAngleX;
	}
	
	// Added in v3.1
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		
		if (entity instanceof EntityZombie && ((EntityZombie)entity).isVillager())
		{
			// Changed in v3.2 to accommodate config-specifiable professions
			final ExtendedZombieVillager ezv = ExtendedZombieVillager.get(((EntityZombie)entity));
			int prof = ezv.getProfession();
			
			if (prof > 5 && !GeneralConfig.moddedVillagerHeadwearWhitelist.contains(prof)) // This is a non-vanilla villager profession and is not whitelisted
			{
				// Is in the blacklist, or headwear is turned off at large
				if (GeneralConfig.moddedVillagerHeadwearBlacklist.contains(prof) || !GeneralConfig.moddedVillagerHeadwear) {return;}
			}
			
			// Fixed in v3.1.1 - Child hats and rims render properly
			// summon Zombie ~ ~ ~ {IsVillager:1, IsBaby:1}
	        if (this.isChild)
	        {
	            float f6 = 1.4F; // Scaledown factor
	            
	            GL11.glPushMatrix();
	            GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
	            GL11.glTranslatef(0.0F, 16.0F * f5, 0.0F);
	            
	            this.zombieVillagerHeadwear.render(f5);
	    		this.zombieVillagerHatRimHigh.render(f5);
	    		this.zombieVillagerHatRimLow.render(f5);
	            
	            GL11.glPopMatrix();
	        }
	        else
	        {
	            this.zombieVillagerHeadwear.render(f5);
	    		this.zombieVillagerHatRimHigh.render(f5);
	    		this.zombieVillagerHatRimLow.render(f5);
	        }
		}
		
	}
}