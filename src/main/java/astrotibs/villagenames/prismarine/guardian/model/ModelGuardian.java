package astrotibs.villagenames.prismarine.guardian.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

import astrotibs.villagenames.prismarine.guardian.entity.monster.EntityGuardian;
import astrotibs.villagenames.prismarine.guardian.util.MathHelper1122;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

// Copied over from 1.8
@SideOnly(Side.CLIENT)
public class ModelGuardian extends ModelBase {

    private ModelRenderer guardianBody;
    private ModelRenderer guardianEye;
    private ModelRenderer[] guardianSpines;
    private ModelRenderer[] guardianTail;

    // Fires at launch
    public ModelGuardian() {
        // LogHelper.info("ModelGuardian fired");
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.guardianSpines = new ModelRenderer[12];
        this.guardianBody = new ModelRenderer(this);
        this.guardianBody.setTextureOffset(0, 0)
            .addBox(-6.0F, 10.0F, -8.0F, 12, 12, 16);
        this.guardianBody.setTextureOffset(0, 28)
            .addBox(-8.0F, 10.0F, -6.0F, 2, 12, 12); // Renders right side of body

        // TODO - fix mirror rendering
        // Renders left side of body addBox(offX, offY, offZ, width, height, depth, mirrored)
        // this.guardianBody.setTextureOffset(0, 28).addBox(6.0F, 10.0F, -6.0F, 2, 12, 12);//, true); This is for
        // mirroring. I'll try to adjust that.
        this.guardianBody.mirror = true;
        this.guardianBody.cubeList.add(new ModelBox(this.guardianBody, 0, 28, 6.0F, 10.0F, -6.0F, 2, 12, 12, 0.0F));
        this.guardianBody.mirror = false;

        // this.guardianBody.mirror=true; // This flips ALL textures on this.guardianBody
        this.guardianBody.setTextureOffset(16, 40)
            .addBox(-6.0F, 8.0F, -6.0F, 12, 2, 12); // Renders bottom of body
        this.guardianBody.setTextureOffset(16, 40)
            .addBox(-6.0F, 22.0F, -6.0F, 12, 2, 12); // Renders top of body

        for (int i = 0; i < this.guardianSpines.length; ++i) {
            this.guardianSpines[i] = new ModelRenderer(this, 0, 0);
            this.guardianSpines[i].addBox(-1.0F, -4.5F, -1.0F, 2, 9, 2);
            this.guardianBody.addChild(this.guardianSpines[i]);
        }

        this.guardianEye = new ModelRenderer(this, 8, 0);
        this.guardianEye.addBox(-1.0F, 15.0F, 0.0F, 2, 2, 1);
        this.guardianBody.addChild(this.guardianEye);
        this.guardianTail = new ModelRenderer[3];
        this.guardianTail[0] = new ModelRenderer(this, 40, 0);
        this.guardianTail[0].addBox(-2.0F, 14.0F, 7.0F, 4, 4, 8);
        this.guardianTail[1] = new ModelRenderer(this, 0, 54);
        this.guardianTail[1].addBox(0.0F, 14.0F, 0.0F, 3, 3, 7);
        this.guardianTail[2] = new ModelRenderer(this);
        this.guardianTail[2].setTextureOffset(41, 32)
            .addBox(0.0F, 14.0F, 0.0F, 2, 2, 6);
        this.guardianTail[2].setTextureOffset(25, 19)
            .addBox(1.0F, 10.5F, 3.0F, 1, 9, 9);
        this.guardianBody.addChild(this.guardianTail[0]);
        this.guardianTail[0].addChild(this.guardianTail[1]);
        this.guardianTail[1].addChild(this.guardianTail[2]);
    }

    // Fires at launch
    public int getModelVersion() {
        // LogHelper.info("func_178706_a fired");
        return 54;
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    // Fires
    public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_,
        float p_78088_6_, float scale) {
        // LogHelper.info("render fired");
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
        this.guardianBody.render(scale);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    // Fires
    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_,
        float p_78087_5_, float p_78087_6_, Entity entityIn) {
        // LogHelper.info("setRotationAngles fired");
        EntityGuardian entityguardian = (EntityGuardian) entityIn;
        float f = p_78087_3_ - (float) entityguardian.ticksExisted;
        this.guardianBody.rotateAngleY = p_78087_4_ / (180F / (float) Math.PI);
        this.guardianBody.rotateAngleX = p_78087_5_ / (180F / (float) Math.PI);
        float[] afloat = new float[] { 1.75F, 0.25F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 0.5F, 1.25F, 0.75F, 0.0F, 0.0F };
        float[] afloat1 = new float[] { 0.0F, 0.0F, 0.0F, 0.0F, 0.25F, 1.75F, 1.25F, 0.75F, 0.0F, 0.0F, 0.0F, 0.0F };
        float[] afloat2 = new float[] { 0.0F, 0.0F, 0.25F, 1.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.75F, 1.25F };
        float[] afloat3 = new float[] { 0.0F, 0.0F, 8.0F, -8.0F, -8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F, 8.0F, -8.0F };
        float[] afloat4 = new float[] { -8.0F, -8.0F, -8.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 8.0F };
        float[] afloat5 = new float[] { 8.0F, -8.0F, 0.0F, 0.0F, -8.0F, -8.0F, 8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F };
        float f1 = (1.0F - entityguardian.getSpikesAnimation(f)) * 0.55F;

        for (int i = 0; i < 12; ++i) {
            this.guardianSpines[i].rotateAngleX = (float) Math.PI * afloat[i];
            this.guardianSpines[i].rotateAngleY = (float) Math.PI * afloat1[i];
            this.guardianSpines[i].rotateAngleZ = (float) Math.PI * afloat2[i];
            this.guardianSpines[i].rotationPointX = afloat3[i]
                * (1.0F + MathHelper1122.cos(p_78087_3_ * 1.5F + (float) i) * 0.01F - f1);
            this.guardianSpines[i].rotationPointY = 16.0F
                + afloat4[i] * (1.0F + MathHelper1122.cos(p_78087_3_ * 1.5F + (float) i) * 0.01F - f1);
            this.guardianSpines[i].rotationPointZ = afloat5[i]
                * (1.0F + MathHelper1122.cos(p_78087_3_ * 1.5F + (float) i) * 0.01F - f1);
        }

        this.guardianEye.rotationPointZ = -8.25F;
        // Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
        Entity entity = Minecraft.getMinecraft().renderViewEntity; // Hope this works

        if (entityguardian.hasTargetedEntity()) {
            entity = entityguardian.getTargetedEntity();
        }

        if (entity != null) {

            // Entity.getPositionEyes() is only available in 1.8+
            // Vec3 vec3 = entity.getPositionEyes(0.0F);
            // Vec3 vec31 = entityIn.getPositionEyes(0.0F);
            Vec3 vec3 = getPositionEyes(entity, 0.0F);
            Vec3 vec31 = getPositionEyes(entityIn, 0.0F);

            double d0 = vec3.yCoord - vec31.yCoord;

            if (d0 > 0.0D) {
                this.guardianEye.rotationPointY = 0.0F;
            } else {
                this.guardianEye.rotationPointY = 1.0F;
            }

            // Vec3 vec32 = entityIn.getLook(0.0F);
            Vec3 vec32 = getLook(entityIn, 0.0F);
            vec32 = Vec3.createVectorHelper(vec32.xCoord, 0.0D, vec32.zCoord);
            // Vec3 vec33 = (Vec3.createVectorHelper(vec31.xCoord - vec3.xCoord, 0.0D, vec31.zCoord -
            // vec3.zCoord)).normalize().rotateYaw(((float)Math.PI / 2F));
            Vec3 vec33 = (Vec3.createVectorHelper(vec31.xCoord - vec3.xCoord, 0.0D, vec31.zCoord - vec3.zCoord))
                .normalize();
            vec33 = rotateYaw(vec33, ((float) Math.PI / 2F)); // created a RotateYaw below to achieve this
            double d1 = vec32.dotProduct(vec33);
            this.guardianEye.rotationPointX = MathHelper1122.sqrt_float((float) Math.abs(d1)) * 2.0F
                * (float) Math.signum(d1);
        }

        this.guardianEye.showModel = true;
        float f2 = entityguardian.getTailAnimation(f);
        this.guardianTail[0].rotateAngleY = MathHelper1122.sin(f2) * (float) Math.PI * 0.05F;
        this.guardianTail[1].rotateAngleY = MathHelper1122.sin(f2) * (float) Math.PI * 0.1F;
        this.guardianTail[1].rotationPointX = -1.5F;
        this.guardianTail[1].rotationPointY = 0.5F;
        this.guardianTail[1].rotationPointZ = 14.0F;
        this.guardianTail[2].rotateAngleY = MathHelper1122.sin(f2) * (float) Math.PI * 0.15F;
        this.guardianTail[2].rotationPointX = 0.5F;
        this.guardianTail[2].rotationPointY = 0.5F;
        this.guardianTail[2].rotationPointZ = 6.0F;
    }

    /*
     * Below methods copied out of 1.8
     */
    // Fires
    Vec3 getPositionEyes(Entity entity, float partialTicks) {
        // LogHelper.info("getPositionEyes fired");
        if (partialTicks == 1.0F) {
            return Vec3.createVectorHelper(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ);
        } else {
            double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double) partialTicks;
            double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double) partialTicks
                + (double) this.getEyeHeight(entity);
            double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double) partialTicks;
            return Vec3.createVectorHelper(d0, d1, d2);
        }
    }

    // Fires
    public float getEyeHeight(Entity entity) {
        // LogHelper.info("getEyeHeight fired");
        return entity.height * 0.85F;
    }

    /**
     * interpolated look vector
     */
    // Fires
    public Vec3 getLook(Entity entity, float partialTicks) {
        // LogHelper.info("getLook fired");
        if (partialTicks == 1.0F) {
            return getVectorForRotation(entity.rotationPitch, entity.rotationYaw);
        } else {
            float f = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            float f1 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;
            return getVectorForRotation(f, f1);
        }
    }

    /**
     * Creates a Vec3 using the pitch and yaw of the entities rotation.
     */
    // Fires
    protected final Vec3 getVectorForRotation(float pitch, float yaw) {
        // LogHelper.info("getVectorForRotation fired");
        float f = MathHelper1122.cos(-yaw * 0.017453292F - (float) Math.PI);
        float f1 = MathHelper1122.sin(-yaw * 0.017453292F - (float) Math.PI);
        float f2 = -MathHelper1122.cos(-pitch * 0.017453292F);
        float f3 = MathHelper1122.sin(-pitch * 0.017453292F);
        return Vec3.createVectorHelper((double) (f1 * f2), (double) f3, (double) (f * f2));
    }

    // Fires
    public Vec3 rotateYaw(Vec3 vec3, float yaw) {
        // LogHelper.info("rotateYaw fired");
        float f = MathHelper1122.cos(yaw);
        float f1 = MathHelper1122.sin(yaw);
        double d0 = vec3.xCoord * (double) f + vec3.zCoord * (double) f1;
        double d1 = vec3.yCoord;
        double d2 = vec3.zCoord * (double) f - vec3.xCoord * (double) f1;
        return Vec3.createVectorHelper(d0, d1, d2);
    }
}
