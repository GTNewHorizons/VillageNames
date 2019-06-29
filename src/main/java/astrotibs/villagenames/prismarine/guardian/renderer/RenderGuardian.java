package astrotibs.villagenames.prismarine.guardian.renderer;

import org.lwjgl.opengl.GL11;

import astrotibs.villagenames.prismarine.guardian.entity.monster.EntityGuardian;
import astrotibs.villagenames.prismarine.guardian.model.ModelGuardian;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;


@SideOnly(Side.CLIENT) //Apparently not allowed in 1.7?
public class RenderGuardian extends RenderLiving//Extender//<EntityGuardian>
{
	// Of course, these three resources below are modified
	public static final ResourceLocation GUARDIAN_TEXTURE = new ResourceLocation("minecraft:textures/entity/guardian.png");
	public static final ResourceLocation GUARDIAN_ELDER_TEXTURE = new ResourceLocation("minecraft:textures/entity/guardian_elder.png");
	public static final ResourceLocation GUARDIAN_BEAM_TEXTURE = new ResourceLocation("minecraft:textures/entity/guardian_beam.png");
	private RenderLiving RLExtender;
    int lastModelVersion;
    public RenderManager rManager = RenderManager.instance;
    
    // Fires at launch
    public RenderGuardian()
    {
        super(new ModelGuardian(), 0.5F);
        this.lastModelVersion = ((ModelGuardian)this.mainModel).getModelVersion();
        rManager.getEntityClassRenderObject(EntityGuardian.class);
    }
    
    //1.7: in RenderGlobal.renderEntities(EntityLivingBase p_147589_1_, ICamera p_147589_2_, float p_147589_3_)
    //boolean flag = entity.isInRangeToRender3d(d0, d1, d2) && (entity.ignoreFrustumCheck || p_147589_2_.isBoundingBoxInFrustum(entity.boundingBox) || entity.riddenByEntity == this.mc.thePlayer);
    //1.8: in RenderGlobal.renderEntities(Entity renderViewEntity, ICamera camera, float partialTicks)
    //boolean flag2 = this.renderManager.shouldRender(entity2, camera, d0, d1, d2) || entity2.riddenByEntity == this.mc.thePlayer;
    
    /*
     *  The purpose of this is to keep the guardian rendered even when off-screen if its beam is still on-screen.
     *  Instead, I've slightly replicated the behavior with EntityGuardian.isInRangeToRender3d, however
     *  that disregards the frustum completely when the beam is firing. C'EST LA VIE, MON FRÈRE
     */
    
    /*
    public boolean shouldRender(EntityGuardian livingEntity, ICamera camera, double camX, double camY, double camZ)
    {
        LogHelper.info("shouldRender fired");
        if (this.RLExtender.shouldRender(livingEntity, camera, camX, camY, camZ))//(super.shouldRender(livingEntity, camera, camX, camY, camZ))
        {
            return true;
        }
        else
        {
            if (livingEntity.hasTargetedEntity())
            {
                EntityLivingBase entitylivingbase = livingEntity.getTargetedEntity();

                if (entitylivingbase != null)
                {
                    Vec3 vec3 = this.getPosition(entitylivingbase, (double)entitylivingbase.height * 0.5D, 1.0F);
                    Vec3 vec31 = this.getPosition(livingEntity, (double)livingEntity.getEyeHeight(), 1.0F);

                    //if (camera.isBoundingBoxInFrustum(AxisAlignedBB.fromBounds(vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord, vec3.zCoord)))
                    double d0 = Math.min(vec31.xCoord, vec3.xCoord);
                    double d1 = Math.min(vec31.yCoord, vec3.yCoord);
                    double d2 = Math.min(vec31.zCoord, vec3.zCoord);
                    double d3 = Math.max(vec31.xCoord, vec3.xCoord);
                    double d4 = Math.max(vec31.yCoord, vec3.yCoord);
                    double d5 = Math.max(vec31.zCoord, vec3.zCoord);
                    if ( camera.isBoundingBoxInFrustum(AxisAlignedBB.getBoundingBox(d0, d1, d2, d3, d4, d5)) )
                    {
                        return true;
                    }
                }
            }

            return false;
        }
    }
    */
    
    // Fires
    private Vec3 getPosition(EntityLivingBase entityLivingBaseIn, double yOffset, float vectorScale)
    {
        double d0 = entityLivingBaseIn.lastTickPosX + (entityLivingBaseIn.posX - entityLivingBaseIn.lastTickPosX) * (double)vectorScale;
        double d1 = yOffset + entityLivingBaseIn.lastTickPosY + (entityLivingBaseIn.posY - entityLivingBaseIn.lastTickPosY) * (double)vectorScale;
        double d2 = entityLivingBaseIn.lastTickPosZ + (entityLivingBaseIn.posZ - entityLivingBaseIn.lastTickPosZ) * (double)vectorScale;
        return Vec3.createVectorHelper(d0, d1, d2);
    }
    
    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
    	this.doRender((EntityGuardian)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    public void doRender(EntityGuardian entityGuardian, double x, double y, double z, float entityYaw, float partialTicks) // Renamed from 1.8's doRender()
    {
        //EntityGuardian entity = (EntityGuardian) entityIn;
        if (this.lastModelVersion != ((ModelGuardian)this.mainModel).getModelVersion())
        {
            this.mainModel = new ModelGuardian();
            this.lastModelVersion = ((ModelGuardian)this.mainModel).getModelVersion();
        }

        super.doRender(entityGuardian, x, y, z, entityYaw, partialTicks);
        EntityLivingBase targetedEntity = entityGuardian.getTargetedEntity();
        
        if (targetedEntity != null && entityGuardian.getHealth()>0) //Added this so that the beam vanishes immediately when killing the Guardian
        {
            float f = entityGuardian.getAttackAnimationScale(partialTicks);
            // These two are my best guesses.
            //TessellatorExtender tessellator = (TessellatorExtender) TessellatorExtender.getInstance();
            Tessellator tessellator = Tessellator.instance;
            //WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            //WorldRendererExtender worldrenderer = (WorldRendererExtender) tessellator.getWorldRenderer();
            
            this.bindTexture(GUARDIAN_BEAM_TEXTURE);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
            GL11.glDisable(GL11.GL_LIGHTING); //GlStateManager.disableLighting();
            GL11.glDisable(GL11.GL_CULL_FACE); //GlStateManager.disableCull();
            GL11.glDisable(GL11.GL_BLEND); //GlStateManager.disableBlend();
            GL11.glDepthMask(true); //GlStateManager.depthMask(true);
            
            float f1 = 240.0F;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f1, f1);
            OpenGlHelper.glBlendFunc(770, 1, 1, 0);  // GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
            float f2 = (float)entityGuardian.worldObj.getTotalWorldTime() + partialTicks;
            float f3 = f2 * 0.5F % 1.0F;
            float f4 = entityGuardian.getEyeHeight(); // Eye position of the Guardian
            
            GL11.glPushMatrix(); //GlStateManager.pushMatrix();
            GL11.glTranslatef( (float)x, (float)y + f4, (float)z ); //Centers one end of beam on the Guardian's eye
            //Vec3 vec3 = this.getPosition(entitylivingbase, (double)entitylivingbase.height * 0.5D, partialTicks);
            // The beam doesn't quite render properly so I changed the target positioning to be negative when the target is a player
            Vec3 vec3 = this.getPosition(targetedEntity, (double)targetedEntity.height*(targetedEntity instanceof EntityPlayer ? -0.5D : 0.5D), partialTicks);
            Vec3 vec31 = this.getPosition(entityGuardian, (double)f4, partialTicks);
            Vec3 vec32 = vec31.subtract(vec3);//vec3.subtract(vec31); // I think 1.7 and 1.8 use opposite subtract order
            
            double d0 = vec32.lengthVector() + 1.0D;
            vec32 = vec32.normalize();
            float f5 = (float)Math.acos(vec32.yCoord);
            float f6 = (float)Math.atan2(vec32.zCoord, vec32.xCoord);
            
            GL11.glRotatef( (((float)Math.PI / 2F) + -f6) * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F ); //Something to do with the axial rotation of the beam; takes in degrees
            GL11.glRotatef( f5 * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F ); // Points the beam from Guardian to its target; takes in degrees
            
            int i = 1;
            double d1 = (double)f2 * 0.05D * (1.0D - (double)(i & 1) * 2.5D);
            
            //worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR); //1.8 version
            tessellator.startDrawing(7);//, DefaultVertexFormats.POSITION_TEX_COLOR);
            
            float f7 = f * f;
            int j = 64 + (int)(f7 * 240.0F);
            int k = 32 + (int)(f7 * 192.0F);
            int l = 128 - (int)(f7 * 64.0F);
            
            double d2 = (double)i * 0.2D;
            double d3 = d2 * 1.41D;
            double d4 = 0.0D + Math.cos(d1 + 2.356194490192345D) * d3;
            double d5 = 0.0D + Math.sin(d1 + 2.356194490192345D) * d3;
            double d6 = 0.0D + Math.cos(d1 + (Math.PI / 4D)) * d3;
            double d7 = 0.0D + Math.sin(d1 + (Math.PI / 4D)) * d3;
            double d8 = 0.0D + Math.cos(d1 + 3.9269908169872414D) * d3;
            double d9 = 0.0D + Math.sin(d1 + 3.9269908169872414D) * d3;
            double d10 = 0.0D + Math.cos(d1 + 5.497787143782138D) * d3;
            double d11 = 0.0D + Math.sin(d1 + 5.497787143782138D) * d3;
            double d12 = 0.0D + Math.cos(d1 + Math.PI) * d2;
            double d13 = 0.0D + Math.sin(d1 + Math.PI) * d2;
            double d14 = 0.0D + Math.cos(d1 + 0.0D) * d2;
            double d15 = 0.0D + Math.sin(d1 + 0.0D) * d2;
            double d16 = 0.0D + Math.cos(d1 + (Math.PI / 2D)) * d2;
            double d17 = 0.0D + Math.sin(d1 + (Math.PI / 2D)) * d2;
            double d18 = 0.0D + Math.cos(d1 + (Math.PI * 3D / 2D)) * d2;
            double d19 = 0.0D + Math.sin(d1 + (Math.PI * 3D / 2D)) * d2;
            double d20 = 0.0D;
            double d21 = 0.4999D;
            double d22 = (double)(-1.0F + f3);
            double d23 = d0 * (0.5D / d2) + d22;
            
            //My tessellator versions
            //LogHelper.info("tessellator" + tessellator.instance.);
            tessellator.setColorOpaque(j, k, l);
            tessellator.addVertexWithUV(d12, d0, d13, 0.4999D, d23);
            tessellator.setColorOpaque(j, k, l);
            tessellator.addVertexWithUV(d12, 0.0D, d13, 0.4999D, d22);
            tessellator.setColorOpaque(j, k, l);
            tessellator.addVertexWithUV(d14, 0.0D, d15, 0.0D, d22);
            tessellator.setColorOpaque(j, k, l);
            tessellator.addVertexWithUV(d14, d0, d15, 0.0D, d23);
            tessellator.setColorOpaque(j, k, l);
            tessellator.addVertexWithUV(d16, d0, d17, 0.4999D, d23);
            tessellator.setColorOpaque(j, k, l);
            tessellator.addVertexWithUV(d16, 0.0D, d17, 0.4999D, d22);
            tessellator.setColorOpaque(j, k, l);
            tessellator.addVertexWithUV(d18, 0.0D, d19, 0.0D, d22);
            tessellator.setColorOpaque(j, k, l);
            tessellator.addVertexWithUV(d18, d0, d19, 0.0D, d23);
            //tessellator.setColorRGBA(j, k, l, 255);
            
            double d24 = 0.0D;

            if (entityGuardian.ticksExisted % 2 == 0)
            {
                d24 = 0.5D;
            }
            
            //My tessellator versions
            tessellator.setColorOpaque(j, k, l);
            tessellator.addVertexWithUV(d4, d0, d5, 0.5D, d24 + 0.5D);
            tessellator.setColorOpaque(j, k, l);
            tessellator.addVertexWithUV(d6, d0, d7, 1.0D, d24 + 0.5D);
            tessellator.setColorOpaque(j, k, l);
            tessellator.addVertexWithUV(d10, d0, d11, 1.0D, d24);
            tessellator.setColorOpaque(j, k, l);
            tessellator.addVertexWithUV(d8, d0, d9, 0.5D, d24);
            //tessellator.setColorRGBA(j, k, l, 255);
            tessellator.draw();
            
            // As per Darian Stephens's recommendation, I've un-done the disabling at the beginning, before the pop matrix:
            GL11.glEnable(GL11.GL_LIGHTING);
            //GL11.glEnable(GL11.GL_CULL_FACE);
            //GL11.glEnable(GL11.GL_BLEND);
            //GL11.glDepthMask(false); 
            
            GL11.glPopMatrix(); //GlStateManager.popMatrix();
        }
    }
    
    
    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    // Fires
    protected void preRenderCallback(EntityGuardian entitylivingbaseIn, float partialTickTime)
    {
        //LogHelper.info("preRenderCallback(EntityGuardian, float) fired");
        if (entitylivingbaseIn.isElder())
        {
        	GL11.glScalef(2.35F, 2.35F, 2.35F); //GlStateManager.scale(2.35F, 2.35F, 2.35F);
        }
    }
    
    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    //protected void preRenderCallback(EntityGiantZombie p_77041_1_, float p_77041_2_)
    //{
    //    GL11.glScalef(this.scale, this.scale, this.scale);
    //}
    
    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    // I copied this version from Giant Zombie
    // Fires
    protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
    {
        //LogHelper.info("preRenderCallback(EntityLivingBase, float) fired");
        this.preRenderCallback((EntityGuardian)p_77041_1_, p_77041_2_);
    }

    
    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    /*
    protected ResourceLocation getEntityTexture(EntityGuardian entity)
    {
        LogHelper.info("getEntityTexture(EntityGuardian) fired");
    	return getEntityTexture((Entity)entity);
    }
	*/
    // Fires
    @Override //The 1.8 version uses the entityguardian input
	protected ResourceLocation getEntityTexture(Entity entity) {
        return ((EntityGuardian)entity).isElder() ? GUARDIAN_ELDER_TEXTURE : GUARDIAN_TEXTURE;
	}
}