package astrotibs.villagenames.prismarine.guardian.entity.monster;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import astrotibs.villagenames.VillageNames;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.prismarine.guardian.entity.ai.EntityAIWanderExtender;
import astrotibs.villagenames.prismarine.guardian.entity.pathfinding.PathNavigateSwimmer;
import astrotibs.villagenames.prismarine.guardian.particle.PacketHandlerClient;
import astrotibs.villagenames.prismarine.guardian.particle.SToCMessage;
import astrotibs.villagenames.prismarine.guardian.util.MathHelper1122;
import astrotibs.villagenames.proxy.ClientProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.Unpooled;

public class EntityGuardian extends EntityMob // implements IMob
{

    private float clientSideTailAnimation;
    private float clientSideTailAnimationO;
    private float clientSideTailAnimationSpeed;
    private float clientSideSpikesAnimation;
    private float clientSideSpikesAnimationO;
    private EntityLivingBase targetedEntity;
    private int clientSideAttackTime;
    private boolean clientSideTouchedGround;
    private EntityAIWanderExtender wander = new EntityAIWanderExtender(this, 1.0D, 80); // Called here just to be safe
    public GuardianMoveHelper moveHelper;
    public GuardianLookHelper lookHelper;
    public PathNavigateSwimmer navigator;

    public EntityGuardian(World worldIn) {
        super(worldIn);
        this.experienceValue = 10;
        this.setSize(0.85F, 0.85F);
        this.tasks.addTask(4, new EntityGuardian.AIGuardianAttack(this));
        EntityAIMoveTowardsRestriction entityaimovetowardsrestriction;
        this.tasks.addTask(5, entityaimovetowardsrestriction = new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, this.wander);
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityGuardian.class, 12.0F, 0.01F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.wander.setMutexBits(3);
        entityaimovetowardsrestriction.setMutexBits(3);
        this.targetTasks.addTask(
            1,
            new EntityAINearestAttackableTarget(
                this,
                EntityLivingBase.class,
                10,
                true,
                false,
                new EntityGuardian.GuardianTargetSelector(this)));
        this.moveHelper = new EntityGuardian.GuardianMoveHelper(this);
        this.lookHelper = new EntityGuardian.GuardianLookHelper(this);
        this.navigator = new PathNavigateSwimmer(this, worldIn);
        this.clientSideTailAnimationO = this.clientSideTailAnimation = this.rand.nextFloat();
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
            .setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange)
            .setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(30.0D);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        this.setElder(tagCompund.getBoolean("Elder"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setBoolean("Elder", this.isElder());
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, Integer.valueOf(0)); // 16 is STATUS
        this.dataWatcher.addObject(17, Integer.valueOf(0)); // 17 is TARGET_ENTITY
    }

    /**
     * Returns true if given flag is set
     */
    private boolean isSyncedFlagSet(int flagId) {
        return (this.dataWatcher.getWatchableObjectInt(16) & flagId) != 0;
    }

    /**
     * Sets a flag state "on/off" on both sides (client/server) by using DataWatcher
     */
    private void setSyncedFlag(int flagId, boolean state) {
        int i = this.dataWatcher.getWatchableObjectInt(16);

        if (state) {
            this.dataWatcher.updateObject(16, Integer.valueOf(i | flagId));
        } else {
            this.dataWatcher.updateObject(16, Integer.valueOf(i & ~flagId));
        }
    }

    public boolean isMoving() // used to be func_175472_n()
    {
        return this.isSyncedFlagSet(2);
    }

    private void setMoving(boolean setMoving) // Deobfuscated from 1.8 version
    {
        this.setSyncedFlag(2, setMoving);
    }

    public int getAttackDuration() // Deobfuscated from 1.8 version
    {
        return this.isElder() ? 60 : 80;
    }

    public boolean isElder() {
        return this.isSyncedFlagSet(4);
    }

    /**
     * Sets this Guardian to be an elder or not.
     */
    // FIRES
    public void setElder(boolean elder) {
        this.setSyncedFlag(4, elder);

        if (elder) {
            this.setSize(1.9975F, 1.9975F);
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
                .setBaseValue(0.30000001192092896D);
            this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
                .setBaseValue(8.0D);
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
                .setBaseValue(80.0D);
            this.func_110163_bv(); // Equivalent to EntityLiving.enablePersistence() in 1.8
            this.wander.setExecutionChance(400);
        }
    }

    // summon Guardian ~ ~ ~ {Elder:1}

    @SideOnly(Side.CLIENT)
    public void setElder() {
        this.setElder(true);
        this.clientSideSpikesAnimationO = this.clientSideSpikesAnimation = 1.0F;
    }

    private void setTargetedEntity(int entityId) {
        this.dataWatcher.updateObject(17, Integer.valueOf(entityId));
    }

    public boolean hasTargetedEntity() {
        return this.dataWatcher.getWatchableObjectInt(17) != 0;
    }

    public EntityLivingBase getTargetedEntity() {
        if (!this.hasTargetedEntity()) {
            return null;
        } else if (this.worldObj.isRemote) {
            if (this.targetedEntity != null) {
                return this.targetedEntity;
            } else {
                Entity entity = this.worldObj.getEntityByID(this.dataWatcher.getWatchableObjectInt(17));

                if (entity instanceof EntityLivingBase) {
                    this.targetedEntity = (EntityLivingBase) entity;
                    return this.targetedEntity;
                } else {
                    return null;
                }
            }
        } else {
            return this.getAttackTarget();
        }
    }

    /**
     * This version returns "true" if either the guardian OR its beam target are in range.
     * It also similarly turns the frustum check requirement on and off.
     */
    @SideOnly(Side.CLIENT)
    @Override
    public boolean isInRangeToRender3d(double playerX, double playerY, double playerZ) {
        double d3 = this.posX - playerX;
        double d4 = this.posY - playerY;
        double d5 = this.posZ - playerZ;
        double d6 = d3 * d3 + d4 * d4 + d5 * d5;

        if (!this.hasTargetedEntity()) { // No target. Flag Guardian as within 3d range as normal
            this.ignoreFrustumCheck = false;
            // return this.isInRangeToRenderDist(d6);
        } else { // Guardian is targeting something with its beam. Flag Guardian as within 3d range whether it or its
                 // target is in range.
            this.ignoreFrustumCheck = true;
            // return this.isInRangeToRenderDist( (this.posX - playerX) )
            // return this.getTargetedEntity().isInRangeToRender3d(playerX, playerY, playerZ);
        }

        return this.isInRangeToRenderDist(d6);
    }

    public void func_145781_i(int dataID) // this is the 1.8 equivalent of onDataWatcherUpdate
    {
        super.func_145781_i(dataID);

        if (dataID == 16) // STATUS: this newly-created guardian is an Elder
        {
            if (this.isElder() && this.width < 1.0F) {
                this.setSize(1.9975F, 1.9975F);
            }
        } else if (dataID == 17) // TARGET_ENTITY: this guardian has selected a beam target
        {
            this.clientSideAttackTime = 0;
            this.targetedEntity = null;
        }
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    public int getTalkInterval() {
        return 160;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound() {
        return (!this.isInWater()) ? "VillageNames:land_idle"
            : (this.isElder() ? "VillageNames:elder_idle" : "VillageNames:guardian_idle");
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound() {
        return (!this.isInWater()) ? "VillageNames:land_hit"
            : (this.isElder() ? "VillageNames:elder_hit" : "VillageNames:guardian_hit");
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound() {
        return (!this.isInWater()) ? "VillageNames:land_death"
            : (this.isElder() ? "VillageNames:elder_death" : "VillageNames:guardian_death");
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking() {
        return false;
    }

    public float getEyeHeight() {
        return this.height * 0.5F;
    }

    public float getBlockPathWeight(int x, int y, int z) // Fires now that I changed the argument from BlockPos
    {
        // return this.worldObj.getBlockState(pos).getBlock().getMaterial() == Material.water ? 10.0F +
        // this.worldObj.getLightBrightness(pos) - 0.5F : super.getBlockPathWeight(pos);
        return this.worldObj.getBlock(x, y, z)
            .getMaterial() == Material.water ? 10.0F + this.worldObj.getLightBrightness(x, y, z) - 0.5F
                : super.getBlockPathWeight(x, y, z);
    }

    /**
     * Checks whether any part of the bounding box is inside a liquid
     */
    // Added by me so that the Guardian doesn't produce land sounds when at water surface or at bottom of ocean
    // Removed again: now that AI is working, this doesn't appear to be necessary!
    /*
     * public boolean anyLiquidInBB()
     * {
     * double scaleDown = 0.2D; // How many blocks to contract the BB for liquid detection
     * return this.worldObj.isAnyLiquid( this.boundingBox.contract( 0.0D, scaleDown, 0.0D ) );
     * }
     */

    /*
     * Added this because the navigator, move helper, and look helper were all manually re-written.
     * Ordinarily, these equivalents would be run during updateAITasks, so I'm calling them here.
     */
    @Override
    protected void updateAITasks() {
        super.updateAITasks(); // This needs to call super so that updateAITasks doesn't get gutted
        this.navigator.onUpdateNavigation();
        this.moveHelper.onUpdateMoveHelper();
        this.lookHelper.onUpdateLook();
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    // Fires constantly
    public void onLivingUpdate() {
        if (this.worldObj.isRemote) {

            this.clientSideTailAnimationO = this.clientSideTailAnimation;
            if (!this.isInWater())// && !this.anyLiquidInBB()) // Adjusted to make sure the thing doesn't flop while at
                                  // the water's surface
            {
                this.clientSideTailAnimationSpeed = 2.0F;

                if (this.motionY > 0.0D && this.clientSideTouchedGround)// &&
                                                                        // (this.dataWatcher.getWatchableObjectByte(4)
                                                                        // == 1) ) // This is isSilent() from 1.8 but it
                                                                        // crashes
                {
                    this.worldObj.playSound(this.posX, this.posY, this.posZ, "VillageNames:flop", 1.0F, 1.0F, false);
                }

                int xDetect = (int) this.posX;// Math.round(this.posX);
                // int yDetect = (int) (Math.round(this.boundingBox.minY/2 - 0.4));
                int yDetect = (int) this.posY;// (Math.round(this.posY));
                int zDetect = (int) this.posZ;// Math.round(this.posZ);
                this.clientSideTouchedGround = this.motionY < 0.0D
                    && (this.worldObj.isBlockNormalCubeDefault(xDetect, yDetect - 1, zDetect, false));
            } else if (this.isMoving()) {
                if (this.clientSideTailAnimationSpeed < 0.5F) {
                    this.clientSideTailAnimationSpeed = 4.0F;
                } else {
                    this.clientSideTailAnimationSpeed += (0.5F - this.clientSideTailAnimationSpeed) * 0.1F;
                }
            } else {
                this.clientSideTailAnimationSpeed += (0.125F - this.clientSideTailAnimationSpeed) * 0.2F;
            }

            this.clientSideTailAnimation += this.clientSideTailAnimationSpeed;
            this.clientSideSpikesAnimationO = this.clientSideSpikesAnimation;

            if (!this.isInWater()) {
                this.clientSideSpikesAnimation = this.rand.nextFloat();
            } else if (this.isMoving()) {
                this.clientSideSpikesAnimation += (0.0F - this.clientSideSpikesAnimation) * 0.25F;
            } else {
                this.clientSideSpikesAnimation += (1.0F - this.clientSideSpikesAnimation) * 0.06F;
            }

            if (this.isMoving() && this.isInWater()) {
                Vec3 vec3 = this.getLook(0.0F);

                for (int i = 0; i < 2; ++i) { // Spawns a bubble when the Guardian swims
                                              // this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX +
                                              // (this.rand.nextDouble() - 0.5D) * (double)this.width - vec3.xCoord *
                                              // 1.5D, this.posY + this.rand.nextDouble() * (double)this.height -
                                              // vec3.yCoord * 1.5D, this.posZ + (this.rand.nextDouble() - 0.5D) *
                                              // (double)this.width - vec3.zCoord * 1.5D, 0.0D, 0.0D, 0.0D, new int[0]);
                    this.worldObj.spawnParticle(
                        "bubble",
                        this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width - vec3.xCoord * 1.5D,
                        this.posY + this.rand.nextDouble() * (double) this.height - vec3.yCoord * 1.5D,
                        this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width - vec3.zCoord * 1.5D,
                        0.0D,
                        0.0D,
                        0.0D); // vX vY vZ
                }
            }

            if (this.hasTargetedEntity()) {
                if (this.clientSideAttackTime < this.getAttackDuration()) {
                    ++this.clientSideAttackTime;
                }

                EntityLivingBase target = this.getTargetedEntity();

                if (target != null) {
                    // this.getLookHelper().setLookPositionWithEntity(entitylivingbase, 90.0F, 90.0F);
                    this.lookHelper.setLookPositionWithEntity(target, 90.0F, 90.0F);
                    // this.getLookHelper().onUpdateLook();
                    this.lookHelper.onUpdateLook();
                    double animationProgress = (double) this.getAttackAnimationScale(0.0F);
                    double dx = target.posX - this.posX;

                    double dy = target.posY
                        + (double) (target.height * (targetedEntity instanceof EntityPlayer ? -0.5F : 0.5F))
                        - (this.posY + (double) this.getEyeHeight());
                    // double dy = target.posY + (double)(target.yOffset * 0.5F) - (this.posY +
                    // (double)this.getEyeHeight());
                    // double dy = target.posY - (this.posY + (double)this.getEyeHeight());

                    double dz = target.posZ - this.posZ;
                    double dr = Math.sqrt(dx * dx + dy * dy + dz * dz);
                    dx = dx / dr;
                    dy = dy / dr;
                    dz = dz / dr;
                    double randomDouble = this.rand.nextDouble();

                    while (randomDouble < dr) { // Spawns bubbles throughout the beam attack
                        randomDouble += 1.8D - animationProgress + this.rand.nextDouble() * (1.7D - animationProgress);
                        // this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + d0 * d4, this.posY +
                        // d1 * d4 + (double)this.getEyeHeight(), this.posZ + d2 * d4, 0.0D, 0.0D, 0.0D, new int[0]);
                        this.worldObj.spawnParticle(
                            "bubble",
                            this.posX + dx * randomDouble,
                            this.posY + dy * randomDouble + (double) this.getEyeHeight(),
                            this.posZ + dz * randomDouble,
                            0.0D,
                            0.0D,
                            0.0D); // vX vY vZ
                    }
                }
            }
        }

        if (this.isInWater()) {
            this.setAir(300);
        } else if (this.onGround) {
            this.motionY += 0.5D;
            this.motionX += (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
            this.motionZ += (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
            this.rotationYaw = this.rand.nextFloat() * 360.0F;
            this.onGround = false;
            this.isAirBorne = true;
        }

        if (this.hasTargetedEntity()) {
            this.rotationYaw = this.rotationYawHead;
        }

        super.onLivingUpdate();
    }

    @SideOnly(Side.CLIENT)
    public float getTailAnimation(float partialTicks) {
        return this.clientSideTailAnimationO
            + (this.clientSideTailAnimation - this.clientSideTailAnimationO) * partialTicks;
    }

    @SideOnly(Side.CLIENT)
    public float getSpikesAnimation(float partialTicks) {
        return this.clientSideSpikesAnimationO
            + (this.clientSideSpikesAnimation - this.clientSideSpikesAnimationO) * partialTicks;
    }

    // The percent of the way through the beam attack you are. Used for color, volume, pitch of beam.
    public float getAttackAnimationScale(float f) {
        return ((float) this.clientSideAttackTime + f) / (float) this.getAttackDuration();
    }

    // function pasted in from 1.8 World class
    // Fires when Elder searches through list of players to then decide who needs to be spanked with Mining Fatigue
    private <T extends Entity> List<T> getPlayers(Class<? extends T> playerType, Predicate<? super T> filter) {
        List<T> list = Lists.<T>newArrayList();
        // LogHelper.info("getPlayers fired");
        for (Object entity : this.worldObj.playerEntities) {
            if (playerType.isAssignableFrom(entity.getClass()) && filter.apply((T) entity)) {
                list.add((T) entity);
            }
        }
        return list;
    }

    // Made this method to easy my headache--it is not strictly available in ItemInWorldManager
    // Fires when an Elder beam-attacks a non-creative player
    private boolean survivalOrAdventure(EntityPlayerMP player) {
        return player.theItemInWorldManager.getGameType() == GameType.SURVIVAL
            || player.theItemInWorldManager.getGameType() == GameType.ADVENTURE;
    }

    /*
     * In 1.7 EntityLivingBase.onLivingUpdate() calls tasks based on isAIEnabled():
     * TRUE: calls this.updateAITasks() - THIS calls updateAITick(), which allows the Guardian to sink and to
     * beam-attack!
     * FALSE: calls this.updateEntityActionState(), which has no function within 1.8, or explicitly here.
     * In 1.8, EntityLivingBase always fires (the equivalent of) updateAITick() and doesn't check on isAIEnabled().
     * Thus, to get the behavior I want, I might as well leave it "true" for all Guardians, prompting me
     * to add it as the method below.
     */
    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean isAIEnabled() // Setting this to true will call AIGuardianAttack AND updateAITasks
    {
        return true;
    }

    /*
     * In 1.8: This is called updateAITasks(), which is a blank method in EntityLiving.
     * Both animals and monsters use updateAITasks() to do various things. Iron Golems determine if they're in a
     * village.
     * Villagers determine if they're in a village and get a trading list if they don't have one.
     * Bats check whether they're hanging and if there's a player nearby. All versions also call super.updateAITasks().
     * EntityLiving.updateAITasks() is called during EntityLiving.updateEntityActionState().
     * EntityLiving.updateEntityActionState() is called during EntityLivingBase.onLivingUpdate()
     * 1.8 chain of command is:
     * EntityLiving.onLivingUpdate() > EntityLivingBase.onLivingUpdate() > EntityLiving.updateEntityActionState() >
     * EntityGuardian.updateAITasks()
     * In 1.7: There is no equivalent method in EntityLiving, but EntityLiving.updateAITasks() is functionally
     * identical to 1.8's EntityLiving.updateEntityActionState(). HOWEVER, unlike 1.8, there actually is code in 1.7's
     * EntityLiving.updateEntityActionState(). HoweverEVER, 1.8's EntityLiving.updateEntityActionState() block calls
     * this.updateAITasks(),
     * which in 1.7 is EntityLiving.updateAITasks() calling this.updateAITick().
     * 1.7 chain of command is:
     * EntityLiving.onLivingUpdate() > EntityLivingBase.onLivingUpdate() > EntityLiving.updateAITasks() > below
     * If isAIEnabled(), EntityLivingBase calls updateAITasks(). Otherwise, it runs updateEntityActionState().
     */
    protected void updateAITick() // Changed in 1.8 to updateEntityActionState()
    {
        /*
         * FLOATS WHEN:
         * isAIEnabled()==false, this method is updateEntityActionState(), this calls super.updateEntityActionState()
         * isAIEnabled()==false, this method is updateAITick(), this calls super.updateAITick()
         * isAIEnabled()==false, this method is updateAITick(), this calls super.updateEntityActionState()
         * SINKS WHEN:
         * isAIEnabled()==true, this method is updateEntityActionState(), this calls super.updateEntityActionState()
         * isAIEnabled()==true, this method is updateEntityActionState(), this calls super.updateAITick()
         * isAIEnabled()==false, this method is updateEntityActionState(), this calls super.updateAITick()
         * isAIEnabled()==true, this method is updateAITick(), this calls super.updateAITick() <-----------------THIS
         * ONE!
         * isAIEnabled()==true, this method is updateAITick(), this calls super.updateEntityActionState()
         */
        super.updateAITick(); // This is a blank method in EntityLivingBase, similar to how updateAITasks() is a blank
                              // EntityLiving method in 1.8

        if (this.isElder()) {
            /*
             * The variables below aren't used by Minecraft (the values instead are set directly).
             */
            int mfInflict = 1200;// 1200; // When an Elder Guardian first decides to inflict Mining Fatigue
            int mfRenew = 1200;// 1200; // Remaining time on a Mining Fatigue before an Elder Guardian decides to renew
                               // it
            int mfDuration = 6000; // How long the Mining Fatigue is cast onto you
            int mfLevel = 2; // Mining Fatigue level (minus 1)

            if ((this.ticksExisted + this.getEntityId()) % mfInflict == 0) {
                Potion potion = Potion.digSlowdown;

                // Beth reformatted the following nuts for me, xoxo
                Predicate<EntityPlayerMP> filter = new Predicate<EntityPlayerMP>() {

                    public boolean apply(@Nullable EntityPlayerMP player) {
                        return EntityGuardian.this.getDistanceSqToEntity(player) < 2500.0D
                            // && p_apply_1_.theItemInWorldManager.survivalOrAdventure();
                            && survivalOrAdventure(player);
                    }
                };

                // List<EntityPlayerMP> allPlayers = this.getPlayers(EntityPlayerMP.class, filter);
                List<EntityPlayerMP> allPlayers = this.getPlayers(EntityPlayerMP.class, filter);
                // List<EntityPlayerMP> cursePlayers = Lists.<EntityPlayerMP>newArrayList();

                for (EntityPlayerMP entityplayermp : allPlayers) {
                    if (!entityplayermp.isPotionActive(potion) || entityplayermp.getActivePotionEffect(potion)
                        .getAmplifier() < mfLevel
                        || entityplayermp.getActivePotionEffect(potion)
                            .getDuration() < mfRenew) {

                        // This is what plays the Elder Curse sound and animation.
                        // entityplayermp.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(10, 0.0F));

                        // The curse sound ripped right out of 1.8:
                        // this.clientWorldController.playSound(entityplayer.posX, entityplayer.posY, entityplayer.posZ,
                        // "mob.guardian.curse", 1.0F, 1.0F, false);
                        // Minecraft.getMinecraft().getSoundHandler().playSound(new GuardianCurseSound(entityplayermp,
                        // this)); //This works, but I doubt it's client-only

                        PacketBuffer out = new PacketBuffer(Unpooled.buffer());

                        out.writeInt(PacketHandlerClient.MOB_APPEARANCE);
                        SToCMessage packet = new SToCMessage(out);
                        // LogHelper.info("out: " + out + ", packet: " + packet);
                        VillageNames.VNNetworkWrapper.sendTo(packet, entityplayermp);

                        // This applies the curse effects.
                        entityplayermp.addPotionEffect(new PotionEffect(potion.id, mfDuration, mfLevel));

                    }
                }
            }

            if (!this.hasHome()) {
                // this.setHomePosAndDistance(new BlockPos(this), 16);
                this.setHomeArea((int) this.posX, (int) this.posY, (int) this.posZ, 16); // x, y, z, maximumHomeDistance
            }
        }
    }

    /**
     * Beth helped me with this because idk wut me doing
     * This mimicks the insertion of this.gameController.getSoundHandler().playSound(new
     * GuardianSound((EntityGuardian)entity))
     * into NetPlayHandleClient.handleEntityStatus() in 1.8
     */
    @Override
    public void handleHealthUpdate(byte opCode) {
        super.handleHealthUpdate(opCode);
        if (opCode == 21) {
            ClientProxy.handleHealthUpdate(this);
            // Minecraft.getMinecraft().getSoundHandler().playSound(new GuardianBeamSound(this));
            // this.gameController.getSoundHandler().playSound(new GuardianSound((EntityGuardian)entity));
        }
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean recentlyHit, int looting) {
        // Drop Prismarine shards
        int num_prismarine_shards = this.rand.nextInt(3) + this.rand.nextInt(looting + 1);
        if (num_prismarine_shards > 0) {
            ItemStack prismarineItem = ModObjects.chooseModPrismarineShardItemStack();
            if (prismarineItem != null) {
                this.entityDropItem(
                    new ItemStack(prismarineItem.getItem(), num_prismarine_shards, prismarineItem.getItemDamage()),
                    1.0F);
            }
        }

        // Additionally, drop some cod
        if (this.rand.nextInt(3 + looting) > 1) {
            this.entityDropItem(new ItemStack(Items.fish, 1), 1.0F);
        }
        // or some Prismarine crystals
        else if (this.rand.nextInt(3 + looting) > 1) {
            ItemStack prismarineCrystalsItem = ModObjects.chooseModPrismarineCrystalsItemStack();

            if (prismarineCrystalsItem != null) {
                this.entityDropItem(
                    new ItemStack(prismarineCrystalsItem.getItem(), 1, prismarineCrystalsItem.getItemDamage()),
                    1.0F);
            }
        }

        // Elders drop sponge
        if (recentlyHit && this.isElder()) {
            Object[] spongeObject = ModObjects.chooseModSpongeBlockObject(true);
            if (spongeObject == null) {
                spongeObject = new Object[] { Blocks.sponge, 0 };
            }

            this.entityDropItem(new ItemStack((Block) spongeObject[0], 1, (Integer) spongeObject[1]), 1.0F);
        }
    }

    /**
     * Causes this Entity to drop a random item.
     */
    // This is the explicitly-posted code section that replicates 1.8's EntityLivingBase.addRandomDrop()
    public static List<WeightedRandomFishable> func_174855_j() {
        return EntityFishHook.field_146036_f; // Actual fish, as opposed to junk or loot
    }

    protected void dropRareDrop(int i)// addRandomDrop()
    {
        ItemStack itemstack = ((WeightedRandomFishable) WeightedRandom.getRandomItem(this.rand, func_174855_j()))
            .func_150708_a(this.rand);
        this.entityDropItem(itemstack, 1.0F);
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    // Fires when there's a SPAWNER trying to spawn a mob
    protected boolean isValidLightLevel() {
        return true;
    }

    /**
     * Checks that the entity is not colliding with any blocks / liquids
     */
    /*
     * public boolean isNotColliding() //Crashes for some reason, so I commented it out.
     * {
     * return this.worldObj.checkNoEntityCollision(this.getBoundingBox(), this) &&
     * this.worldObj.getCollidingBoundingBoxes(this, this.getBoundingBox()).isEmpty();
     * }
     */

    /*
     * In 1.8, the Guardian has both isNotColliding() AND getCanSpawnHere().
     * In 1.7, getCanSpawnHere() does exactly what isNotColliding() does in 1.8.
     * However, MobSpawnerBaseLogic now checks for BOTH, instead of just ONE in 1.7.
     * My solution to this is to rename isNotColliding() to getCanSpawnHere(), but also
     * to call super AND manually do the stuff in 1.8's getCanSpawnHere(), which
     * adds an additional random element to the spawning.
     */
    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    /*
     * /setblock ~ ~ ~ mob_spawner 0 destroy {EntityId:Guardian}
     */
    public boolean getCanSpawnHere() {
        World world = this.worldObj;
        int spawnX = (int) this.posX;
        int spawnY = (int) this.posY;
        int spawnZ = (int) this.posZ;

        boolean canSpawnHere = // Added requirements that the spawning block be water.
            (world.getBlock(spawnX, spawnY, spawnZ)
                .getMaterial() == Material.water && // Added in so that spawners don't randomly spawn Guardians in the
                                                    // air
                (this.rand.nextInt(20) == 0 || // Overrides the below conditions if true; taken from 1.8 Guardian's
                                               // getCanSpawnHere()
                    !this.canBlockSeeSky((int) this.posX, (int) this.posY, (int) this.posZ) // taken from 1.8 Guardian's
                                                                                            // getCanSpawnHere()
                        // super.getCanSpawnHere() //Can't use this because it ultimately requires no liquids to be
                        // present
                        && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && this.isValidLightLevel() // part
                                                                                                                   // of
                                                                                                                   // EntityMob.getCanSpawnHere()
                        // part of EntityCreature.getCanSpawnHere():
                            && this.getBlockPathWeight(
                                MathHelper1122.floor_double(this.posX),
                                MathHelper1122.floor_double(this.boundingBox.minY),
                                MathHelper1122.floor_double(this.posZ)) >= 0.0F
                        // && true // 1.8 EntityLiving.getCanSpawnHere() concludes with this, rather than:
                        // && this.isNotColliding()
                        // this.worldObj.checkNoEntityCollision(this.boundingBox) &&
                        // this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() &&
                        // !this.worldObj.isAnyLiquid(this.boundingBox)
                        )));
        return canSpawnHere;
    }

    /**
     * Manually porting World.canBlockSeeSky from 1.8
     * Returns true if the block is above sea level and this.canSeeSky, which in 1.7 is canBlockSeeTheSky
     * Returns false if the block is above sea level and !this.canSeeSky
     * Returns false if the block is below sea level, and the sea-level block above it !canSeeSky
     * Returns false if the block is below sea level, and the sea-level block above it canSeeSky, and any blocks between
     * them both have opacity > 0 and aren't liquid
     * Returns true if the block is below sea level, and the sea-level block above it canSeeSky, and no blocks between
     * them both have opacity > 0 and aren't liquid
     */
    public boolean canBlockSeeSky(int spawnX, int spawnY, int spawnZ) {
        if (spawnY >= 63) // Sea level is 63 in 1.7
        {
            return this.worldObj.canBlockSeeTheSky(spawnX, spawnY, spawnZ); // Block is above sea level and either can
                                                                            // or can't see the sky
        } else {
            if (!this.worldObj.canBlockSeeTheSky(spawnX, 63, spawnZ)) {
                return false; // Block is below sea level, and the sea-level block above it can't see the sky
            } else {
                for (int y = 62; y > spawnY; y--) // Checks all blocks from sea level down to the potential spawning
                                                  // position
                {
                    Block block = this.worldObj.getBlock(spawnX, y, spawnZ);

                    if (block.getLightOpacity() > 0 && !block.getMaterial()
                        .isLiquid()) {
                        return false; // One of the blocks in the column is non-liquid and isn't completely transparent.
                        // Air has opacity 0, Water and Ice 3, Leaves 1, Snow layer and carpet 0
                    }
                }
                return true; // Each column blocks is either liquid or completely light-transparent
            }
        }
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!this.isMoving() && !source.isMagicDamage()
            && source.getSourceOfDamage() instanceof EntityLivingBase
            && (this.getHealth() > 0) // Added this condition so that you can't multi-pound a Guardian corpse and fire
                                      // the Thorn effect repeatedly, you sicko
        ) {
            EntityLivingBase entitylivingbase = (EntityLivingBase) source.getSourceOfDamage();

            if (!source.isExplosion()) {
                entitylivingbase.attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0F);
                // entitylivingbase.playSound("VillageNames:enchant.thorns", GeneralConfigHandler.thornsSound ? 0.5F :
                // 0.0F, 1.0F);
            }
        }

        if (this.wander != null) { // Added "null" check from 1.12
            this.wander.makeUpdate(); // This forces EntityAIWander.shouldExecute to bypass the RNG-based wander
                                      // activation
        }

        return super.attackEntityFrom(source, amount);
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int getVerticalFaceSpeed() {
        return 180;
    }

    /**
     * Moves the entity based on the specified heading. Args: strafe, forward
     */
    public void moveEntityWithHeading(float strafe, float forward) { // is getWatchableObjectByte(15) a valid
                                                                     // substitution for isAIDisabled()? - No, it
                                                                     // crashes.
        if (!this.worldObj.isRemote)// && this.dataWatcher.getWatchableObjectByte(15) == 0) //Equivalent to: if
                                    // (this.isServerWorld())
        {
            if (this.isInWater()) {
                this.moveFlying(strafe, forward, 0.1F);
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.8999999761581421D;
                this.motionY *= 0.8999999761581421D;
                this.motionZ *= 0.8999999761581421D;

                if (!this.isMoving() && this.getAttackTarget() == null) {
                    this.motionY -= 0.005D; // This causes the guardian to slowly sink when still. If you set this to
                                            // zero, it gets twitchy, like Lycanite water mobs.
                }
            } else {
                super.moveEntityWithHeading(strafe, forward);
            }
        } else {
            super.moveEntityWithHeading(strafe, forward);
        }
    }

    static class AIGuardianAttack extends EntityAIBase {

        private EntityGuardian theEntity;
        private int tickCounter;

        public AIGuardianAttack(EntityGuardian entity) {
            this.theEntity = entity;
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            EntityLivingBase entitylivingbase = this.theEntity.getAttackTarget();
            return entitylivingbase != null && entitylivingbase.isEntityAlive();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        // Fires while Guardian is targeting a mofo
        public boolean continueExecuting() {
            return super.continueExecuting() && (this.theEntity.isElder()
                || this.theEntity.getDistanceSqToEntity(this.theEntity.getAttackTarget()) > 9.0D);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.tickCounter = -10; // Attack counter starts at -10 -- this is a 10-tick "warm up"
            this.theEntity.navigator.clearPathEntity();
            // this.theEntity.getLookHelper().setLookPositionWithEntity(this.theEntity.getAttackTarget(), 90.0F, 90.0F);
            this.theEntity.lookHelper.setLookPositionWithEntity(this.theEntity.getAttackTarget(), 90.0F, 90.0F);
            this.theEntity.isAirBorne = true;
        }

        /**
         * Resets the task
         */
        // Fires right after guardian completes its beam attack
        public void resetTask() {
            this.theEntity.setTargetedEntity(0);
            this.theEntity.setAttackTarget((EntityLivingBase) null);
            this.theEntity.wander.makeUpdate();
        }

        /**
         * Updates the task
         */
        // Fires as long as Guardian is using a beam
        public void updateTask() {
            EntityLivingBase entitylivingbase = this.theEntity.getAttackTarget();
            this.theEntity.navigator.clearPathEntity();
            // this.theEntity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 90.0F, 90.0F);
            this.theEntity.lookHelper.setLookPositionWithEntity(entitylivingbase, 90.0F, 90.0F);

            if (!this.theEntity.canEntityBeSeen(entitylivingbase)) {
                this.theEntity.setAttackTarget((EntityLivingBase) null);
            } else {
                ++this.tickCounter;

                if (this.tickCounter == 0) // When ticks count up to zero, a target is chosen and engaged with the beam
                {
                    this.theEntity.setTargetedEntity(
                        this.theEntity.getAttackTarget()
                            .getEntityId());
                    this.theEntity.worldObj.setEntityState(this.theEntity, (byte) 21);
                } else if (this.tickCounter >= this.theEntity.getAttackDuration()) // When ticks reach the limit (60 or
                                                                                   // 80), stop the beam and deal damage
                                                                                   // to the target
                {
                    float f = 1.0F;

                    // if (this.theEntity.worldObj.getDifficulty() == EnumDifficulty.HARD)
                    if (this.theEntity.worldObj.difficultySetting == EnumDifficulty.HARD) {
                        f += 2.0F;
                    }

                    if (this.theEntity.isElder()) {
                        f += 2.0F;
                    }

                    entitylivingbase
                        .attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.theEntity, this.theEntity), f);
                    entitylivingbase.attackEntityFrom(
                        DamageSource.causeMobDamage(this.theEntity),
                        (float) this.theEntity.getEntityAttribute(SharedMonsterAttributes.attackDamage)
                            .getAttributeValue());
                    this.theEntity.setAttackTarget((EntityLivingBase) null);
                }
                // This used to do something every 20 ticks. Probably re-start attack sound?
                /*
                 * else if (this.tickCounter >= 60 && this.tickCounter % 20 == 0)
                 * {
                 * ;
                 * }
                 */
                super.updateTask();
            }
        }
    }

    public static class GuardianMoveHelper// extends EntityMoveHelper //I'm no longer extending anything, because I want
                                          // to manipulate the moveHelper on my own terms.
    {

        /** The EntityLiving that is being moved */
        private EntityGuardian entityGuardian;
        private double posX;
        private double posY;
        private double posZ;
        /** The speed at which the entity should move */
        private double speed;
        private boolean update;

        public GuardianMoveHelper(EntityGuardian entityIn) {
            this.entityGuardian = entityIn;
            this.posX = entityIn.posX;
            this.posY = entityIn.posY;
            this.posZ = entityIn.posZ;
        }

        public boolean isUpdating() {
            return this.update;
        }

        public double getSpeed() {
            return this.speed;
        }

        /**
         * Sets the speed and location to move to
         */
        public void setMoveTo(double x, double y, double z, double speedIn) {
            this.posX = x;
            this.posY = y;
            this.posZ = z;
            this.speed = speedIn;
            this.update = true;
        }

        public void onUpdateMoveHelper() {
            if (this.update && !this.entityGuardian.navigator.noPath()) {
                double d0 = this.posX - this.entityGuardian.posX;
                double d1 = this.posY - this.entityGuardian.posY;
                double d2 = this.posZ - this.entityGuardian.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                d3 = (double) MathHelper1122.sqrt_double(d3);
                d1 = d1 / d3;
                float f = (float) (MathHelper1122.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
                this.entityGuardian.rotationYaw = this.limitAngle(this.entityGuardian.rotationYaw, f, 30.0F);
                this.entityGuardian.renderYawOffset = this.entityGuardian.rotationYaw;
                float f1 = (float) (this.speed
                    * this.entityGuardian.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
                        .getAttributeValue());
                this.entityGuardian.setAIMoveSpeed(
                    this.entityGuardian.getAIMoveSpeed() + (f1 - this.entityGuardian.getAIMoveSpeed()) * 0.125F);
                double d4 = Math.sin(
                    (double) (this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.5D) * 0.05D;
                double d5 = Math.cos((double) (this.entityGuardian.rotationYaw * (float) Math.PI / 180.0F));
                double d6 = Math.sin((double) (this.entityGuardian.rotationYaw * (float) Math.PI / 180.0F));
                this.entityGuardian.motionX += d4 * d5;
                this.entityGuardian.motionZ += d4 * d6;
                d4 = Math.sin((double) (this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.75D)
                    * 0.05D;
                this.entityGuardian.motionY += d4 * (d6 + d5) * 0.25D;
                this.entityGuardian.motionY += (double) this.entityGuardian.getAIMoveSpeed() * d1 * 0.1D;
                // EntityLookHelper entitylookhelper = this.entityGuardian.getLookHelper();
                GuardianLookHelper entitylookhelper = this.entityGuardian.lookHelper;
                double d7 = this.entityGuardian.posX + d0 / d3 * 2.0D;
                double d8 = (double) this.entityGuardian.getEyeHeight() + this.entityGuardian.posY + d1 / d3 * 1.0D;
                double d9 = this.entityGuardian.posZ + d2 / d3 * 2.0D;
                double d10 = entitylookhelper.getLookPosX();
                double d11 = entitylookhelper.getLookPosY();
                double d12 = entitylookhelper.getLookPosZ();

                if (!entitylookhelper.getIsLooking()) {
                    d10 = d7;
                    d11 = d8;
                    d12 = d9;
                }

                // this.entityGuardian.getLookHelper().setLookPosition(d10 + (d7 - d10) * 0.125D, d11 + (d8 - d11) *
                // 0.125D, d12 + (d9 - d12) * 0.125D, 10.0F, 40.0F);
                this.entityGuardian.lookHelper.setLookPosition(
                    d10 + (d7 - d10) * 0.125D,
                    d11 + (d8 - d11) * 0.125D,
                    d12 + (d9 - d12) * 0.125D,
                    10.0F,
                    40.0F);
                this.entityGuardian.setMoving(true);
            } else {
                this.entityGuardian.setAIMoveSpeed(0.0F);
                this.entityGuardian.setMoving(false);
            }
        }

        /**
         * Limits the given angle to a upper and lower limit.
         */
        private float limitAngle(float angleIn, float angleTo, float limitAngle) {
            float f3 = MathHelper1122.wrapAngleTo180_float(angleTo - angleIn);

            if (f3 > limitAngle) {
                f3 = limitAngle;
            }

            if (f3 < -limitAngle) {
                f3 = -limitAngle;
            }

            float f1 = angleIn + f3;

            if (f1 < 0.0F) {
                f1 += 360.0F;
            } else if (f1 > 360.0F) {
                f1 -= 360.0F;
            }

            return f1;
        }

        public double getX() {
            return this.posX;
        }

        public double getY() {
            return this.posY;
        }

        public double getZ() {
            return this.posZ;
        }

        public GuardianMoveHelper getMoveHelper() {
            return this.entityGuardian.moveHelper;
        }

    }

    static class GuardianTargetSelector implements IEntitySelector// implements Predicate<EntityLivingBase>
    {

        private EntityGuardian parentEntity;

        public GuardianTargetSelector(EntityGuardian guardian) {
            this.parentEntity = guardian;
        }

        @Override
        // Renamed this from apply(EntityLivingBase)
        public boolean isEntityApplicable(Entity potentialTarget) {
            return (potentialTarget instanceof EntityPlayer || potentialTarget instanceof EntitySquid)
                && potentialTarget.getDistanceSqToEntity(this.parentEntity) > 9.0D;
        }
    }

    /*
     * Added by me, similar to GuardianLookHelper
     * 1.8 EntityLookHelper is IDENTICAL to 1.7, except they added isLooking and posX getters.
     */
    static class GuardianLookHelper {

        private EntityGuardian entityGuardian;
        /** The amount of change that is made each update for an entity facing a direction. */
        private float deltaLookYaw;
        /** The amount of change that is made each update for an entity facing a direction. */
        private float deltaLookPitch;
        /** Whether or not the entity is trying to look at something. */
        private boolean isLooking;
        private double posX;
        private double posY;
        private double posZ;

        public GuardianLookHelper(EntityGuardian entitylivingIn) {
            this.entityGuardian = entitylivingIn;
        }

        /**
         * Sets position to look at using entity
         */
        public void setLookPositionWithEntity(Entity entityIn, float deltaYaw, float deltaPitch) {
            this.posX = entityIn.posX;

            if (entityIn instanceof EntityLivingBase) {
                this.posY = entityIn.posY + (double) entityIn.getEyeHeight();
            } else {
                this.posY = (entityIn.boundingBox.minY + entityIn.boundingBox.maxY) / 2.0D;
            }

            this.posZ = entityIn.posZ;
            this.deltaLookYaw = deltaYaw;
            this.deltaLookPitch = deltaPitch;
            this.isLooking = true;
        }

        /**
         * Sets position to look at
         */
        public void setLookPosition(double x, double y, double z, float deltaYaw, float deltaPitch) {
            this.posX = x;
            this.posY = y;
            this.posZ = z;
            this.deltaLookYaw = deltaYaw;
            this.deltaLookPitch = deltaPitch;
            this.isLooking = true;
        }

        /**
         * Updates look
         */
        // Fires when Guardian uses beam attack
        public void onUpdateLook() {
            this.entityGuardian.rotationPitch = 0.0F;

            if (this.isLooking) {
                this.isLooking = false;
                double d0 = this.posX - this.entityGuardian.posX;
                double d1 = this.posY - (this.entityGuardian.posY + (double) this.entityGuardian.getEyeHeight());
                double d2 = this.posZ - this.entityGuardian.posZ;
                double d3 = (double) MathHelper1122.sqrt_double(d0 * d0 + d2 * d2);
                float f = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
                float f1 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
                this.entityGuardian.rotationPitch = this
                    .updateRotation(this.entityGuardian.rotationPitch, f1, this.deltaLookPitch);
                this.entityGuardian.rotationYawHead = this
                    .updateRotation(this.entityGuardian.rotationYawHead, f, this.deltaLookYaw);
            } else {
                this.entityGuardian.rotationYawHead = this
                    .updateRotation(this.entityGuardian.rotationYawHead, this.entityGuardian.renderYawOffset, 10.0F);
            }

            float f2 = MathHelper1122
                .wrapAngleTo180_float(this.entityGuardian.rotationYawHead - this.entityGuardian.renderYawOffset);

            if (!this.entityGuardian.navigator.noPath()) {
                if (f2 < -75.0F) {
                    this.entityGuardian.rotationYawHead = this.entityGuardian.renderYawOffset - 75.0F;
                }

                if (f2 > 75.0F) {
                    this.entityGuardian.rotationYawHead = this.entityGuardian.renderYawOffset + 75.0F;
                }
            }
        }

        private float updateRotation(float angleIn, float angleTo, float limitAngle) {
            float f3 = MathHelper1122.wrapAngleTo180_float(angleTo - angleIn);

            if (f3 > limitAngle) {
                f3 = limitAngle;
            }

            if (f3 < -limitAngle) {
                f3 = -limitAngle;
            }

            return angleIn + f3;
        }

        public boolean getIsLooking() {
            return this.isLooking;
        }

        public double getLookPosX() {
            return this.posX;
        }

        public double getLookPosY() {
            return this.posY;
        }

        public double getLookPosZ() {
            return this.posZ;
        }

        public GuardianLookHelper getLookHelper() {
            return this.entityGuardian.lookHelper;
        }
    }

}
