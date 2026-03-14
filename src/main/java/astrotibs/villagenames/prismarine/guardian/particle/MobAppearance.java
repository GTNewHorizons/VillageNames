package astrotibs.villagenames.prismarine.guardian.particle;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import astrotibs.villagenames.prismarine.guardian.entity.monster.EntityGuardian;
import astrotibs.villagenames.prismarine.guardian.util.MathHelper1122;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MobAppearance extends EntityFX {

    private EntityLivingBase entity;
    private EntityClientPlayerMP targetPlayer;

    public MobAppearance(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn,
        EntityClientPlayerMP entityIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.motionX = this.motionY = this.motionZ = 0.0D;
        this.particleGravity = 0.0F;
        this.particleMaxAge = 30;
        this.targetPlayer = entityIn;
    }

    public int getFXLayer() {
        return 3;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        super.onUpdate();

        if (this.entity == null) {
            EntityGuardian entityguardian = new EntityGuardian(this.worldObj);
            entityguardian.setElder();
            this.entity = entityguardian;
        }
    }

    /**
     * Renders the particle
     * This is the version actually called by EffectRenderer.renderLitParticles and EffectRenderer.renderParticles
     */
    // public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float p_180434_4_,
    // float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_)
    public void renderParticle(Tessellator worldRendererIn, float partialTicks, float p_180434_4_, float p_180434_5_,
        float p_180434_6_, float p_180434_7_, float p_180434_8_) {
        if (this.entity != null) {
            // RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
            RenderManager rendermanager = RenderManager.instance;

            // rendermanager.setRenderPosition(EntityFX.interpPosX, EntityFX.interpPosY, EntityFX.interpPosZ);
            rendermanager.renderPosX = EntityFX.interpPosX;
            rendermanager.renderPosY = EntityFX.interpPosY;
            rendermanager.renderPosZ = EntityFX.interpPosZ;

            float f = 0.42553192F;
            float f1 = ((float) this.particleAge + partialTicks) / (float) this.particleMaxAge;
            GL11.glDepthMask(true);// GlStateManager.depthMask(true);
            GL11.glEnable(GL11.GL_BLEND);// GlStateManager.enableBlend();
            GL11.glEnable(GL11.GL_DEPTH_TEST);// GlStateManager.enableDepth();
            GL11.glBlendFunc(770, 771);// GlStateManager.blendFunc(770, 771);
            float f2 = 240.0F;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f2, f2);
            GL11.glPushMatrix();
            float f3 = 0.05F + 0.5F * MathHelper1122.sin(f1 * (float) Math.PI);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f3);
            // GL11.glTranslatef(0.0F, 1.8F, 0.0F);
            GL11.glTranslatef(0.0F, 0.0F, 0.0F); // Changed to y=0 because in 1.7, y=0 is the player's eyes rather than
                                                 // feet.
            GL11.glRotatef(180.0F - targetPlayer.rotationYaw, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(60.0F - 150.0F * f1 - targetPlayer.rotationPitch, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.4F, -1.5F);
            GL11.glScalef(f, f, f);
            this.entity.rotationYaw = this.entity.prevRotationYaw = 0.0F;
            this.entity.rotationYawHead = this.entity.prevRotationYawHead = 0.0F;
            rendermanager.renderEntityWithPosYaw(this.entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks);
            GL11.glPopMatrix();
            GL11.glEnable(GL11.GL_DEPTH_TEST);// GlStateManager.enableDepth();
        }
    }
    /*
     * @SideOnly(Side.CLIENT)
     * public static class Factory implements IParticleFactory
     * {
     * public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn,
     * double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_)
     * {
     * return new MobAppearance(worldIn, xCoordIn, yCoordIn, zCoordIn);
     * }
     * }
     */
}
