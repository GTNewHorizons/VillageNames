package astrotibs.villagenames.prismarine.guardian.entity.pathfinding;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;

import astrotibs.villagenames.prismarine.guardian.entity.monster.EntityGuardian;
import astrotibs.villagenames.prismarine.guardian.util.MathHelper1122;
import astrotibs.villagenames.utility.BlockPos;

public class PathNavigateSwimmer {

    protected EntityGuardian theEntity;
    protected World worldObj;
    /** The PathEntity being followed. */
    protected PathEntitySwimmer currentPath;
    protected double speed;
    /** The number of blocks (extra) +/- in each axis that get pulled out as cache for the pathfinder's search space */
    private final IAttributeInstance pathSearchRange;
    /** Time, in number of ticks, following the current path */
    private int totalTicks;
    /** The time when the last position check was done (to detect successful movement) */
    private int ticksAtLastPos;
    /** Coordinates of the entity's position last time a check was done (part of monitoring getting 'stuck') */
    private Vec3 lastPosCheck = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
    private float heightRequirement = 1.0F;
    private final PathFinderSwimmer pathFinder;

    public PathNavigateSwimmer(EntityGuardian entitylivingIn, World worldIn) {
        this.theEntity = entitylivingIn;
        this.worldObj = worldIn;
        this.pathSearchRange = entitylivingIn.getEntityAttribute(SharedMonsterAttributes.followRange);
        this.pathFinder = this.getPathFinder();
    }

    protected PathFinderSwimmer getPathFinder() {
        return new PathFinderSwimmer(new SwimNodeProcessor());
    }

    /**
     * Sets the speed
     */
    public void setSpeed(double speedIn) {
        this.speed = speedIn;
    }

    /**
     * Gets the maximum distance that the path finding will search in.
     */
    public float getPathSearchRange() {
        return (float) this.pathSearchRange.getAttributeValue();
    }

    /**
     * Returns the path to the given coordinates. Args : x, y, z
     */
    public final PathEntitySwimmer getPathToXYZ(double x, double y, double z) {
        return this.getPathToPos(new BlockPos(MathHelper1122.floor_double(x), (int) y, MathHelper1122.floor_double(z)));
    }

    /**
     * Returns path to given BlockPos
     */
    public PathEntitySwimmer getPathToPos(BlockPos pos) {
        if (!this.canNavigate()) {
            return null;
        } else {
            float f = this.getPathSearchRange();
            this.worldObj.theProfiler.startSection("pathfind");
            BlockPos blockpos = new BlockPos(this.theEntity);
            int i = (int) (f + 8.0F);
            ChunkCache chunkcache = new ChunkCache(
                this.worldObj,
                blockpos.getX() - i,
                blockpos.getY() - i,
                blockpos.getZ() - i,
                blockpos.getX() + i,
                blockpos.getY() + i,
                blockpos.getZ() + i,
                0);
            PathEntitySwimmer pathentity = this.pathFinder.createEntityPathTo(chunkcache, this.theEntity, pos, f);
            this.worldObj.theProfiler.endSection();
            return pathentity;
        }
    }

    /**
     * Try to find and set a path to XYZ. Returns true if successful. Args : x, y, z, speed
     */
    public boolean tryMoveToXYZ(double x, double y, double z, double speedIn) {
        PathEntitySwimmer pathentity = this.getPathToXYZ(
            (double) MathHelper1122.floor_double(x),
            (double) ((int) y),
            (double) MathHelper1122.floor_double(z));
        return this.setPath(pathentity, speedIn);
    }

    /**
     * Sets vertical space requirement for path
     */
    public void setHeightRequirement(float jumpHeight) {
        this.heightRequirement = jumpHeight;
    }

    /**
     * Returns the path to the given EntityLiving. Args : entity
     */
    public PathEntitySwimmer getPathToEntityLiving(Entity entityIn) {
        if (!this.canNavigate()) {
            return null;
        } else {
            float f = this.getPathSearchRange();
            this.worldObj.theProfiler.startSection("pathfind");
            BlockPos blockpos = (new BlockPos(this.theEntity)).up();
            int i = (int) (f + 16.0F);
            ChunkCache chunkcache = new ChunkCache(
                this.worldObj,
                blockpos.getX() - i,
                blockpos.getY() - i,
                blockpos.getZ() - i,
                blockpos.getX() + i,
                blockpos.getY() + i,
                blockpos.getZ() + i,
                0);
            PathEntitySwimmer pathentity = this.pathFinder.createEntityPathTo(chunkcache, this.theEntity, entityIn, f);
            this.worldObj.theProfiler.endSection();
            return pathentity;
        }
    }

    /**
     * Try to find and set a path to EntityLiving. Returns true if successful. Args : entity, speed
     */
    public boolean tryMoveToEntityLiving(Entity entityIn, double speedIn) {
        PathEntitySwimmer pathentity = this.getPathToEntityLiving(entityIn);
        return pathentity != null ? this.setPath(pathentity, speedIn) : false;
    }

    /**
     * Sets a new path. If it's diferent from the old path. Checks to adjust path for sun avoiding, and stores start
     * coords. Args : path, speed
     */
    public boolean setPath(PathEntitySwimmer pathentityIn, double speedIn) {
        if (pathentityIn == null) {
            this.currentPath = null;
            return false;
        } else {
            if (!pathentityIn.isSamePath(this.currentPath)) {
                this.currentPath = pathentityIn;
            }

            this.removeSunnyPath();

            if (this.currentPath.getCurrentPathLength() == 0) {
                return false;
            } else {
                this.speed = speedIn;
                Vec3 vec3 = this.getEntityPosition();
                this.ticksAtLastPos = this.totalTicks;
                this.lastPosCheck = vec3;
                return true;
            }
        }
    }

    /**
     * gets the actively used PathEntity
     */
    public PathEntitySwimmer getPath() {
        return this.currentPath;
    }

    public void onUpdateNavigation() {
        ++this.totalTicks;

        if (!this.noPath()) {
            if (this.canNavigate()) {
                this.pathFollow();
            } else if (this.currentPath != null
                && this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength()) {
                    Vec3 vec3 = this.getEntityPosition();
                    Vec3 vec31 = this.currentPath
                        .getVectorFromIndex(this.theEntity, this.currentPath.getCurrentPathIndex());

                    if (vec3.yCoord > vec31.yCoord && !this.theEntity.onGround
                        && MathHelper1122.floor_double(vec3.xCoord) == MathHelper1122.floor_double(vec31.xCoord)
                        && MathHelper1122.floor_double(vec3.zCoord) == MathHelper1122.floor_double(vec31.zCoord)) {
                        this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
                    }
                }

            if (!this.noPath()) {
                Vec3 vec32 = this.currentPath.getPosition(this.theEntity);

                if (vec32 != null) {
                    AxisAlignedBB axisalignedbb1 = (AxisAlignedBB.getBoundingBox(
                        vec32.xCoord,
                        vec32.yCoord,
                        vec32.zCoord,
                        vec32.xCoord,
                        vec32.yCoord,
                        vec32.zCoord)).expand(0.5D, 0.5D, 0.5D);
                    List<AxisAlignedBB> list = this.worldObj
                        .getCollidingBoundingBoxes(this.theEntity, axisalignedbb1.addCoord(0.0D, -1.0D, 0.0D));
                    double d0 = -1.0D;
                    axisalignedbb1 = axisalignedbb1.offset(0.0D, 1.0D, 0.0D);

                    for (AxisAlignedBB axisalignedbb : list) {
                        d0 = axisalignedbb.calculateYOffset(axisalignedbb1, d0);
                    }

                    this.theEntity.moveHelper.setMoveTo(vec32.xCoord, vec32.yCoord + d0, vec32.zCoord, this.speed);
                }
            }
        }
    }

    protected void pathFollow() {
        Vec3 vec3 = this.getEntityPosition();
        float f = this.theEntity.width * this.theEntity.width;
        int i = 6;

        if (vec3.squareDistanceTo(
            this.currentPath.getVectorFromIndex(this.theEntity, this.currentPath.getCurrentPathIndex())) < (double) f) {
            this.currentPath.incrementPathIndex();
        }

        for (int j = Math
            .min(this.currentPath.getCurrentPathIndex() + i, this.currentPath.getCurrentPathLength() - 1); j
                > this.currentPath.getCurrentPathIndex(); --j) {
            Vec3 vec31 = this.currentPath.getVectorFromIndex(this.theEntity, j);

            if (vec31.squareDistanceTo(vec3) <= 36.0D && this.isDirectPathBetweenPoints(vec3, vec31, 0, 0, 0)) {
                this.currentPath.setCurrentPathIndex(j);
                break;
            }
        }

        this.checkForStuck(vec3);
    }

    /**
     * Checks if entity haven't been moved when last checked and if so, clears current {@link
     * net.minecraft.pathfinding.PathEntity}
     */
    protected void checkForStuck(Vec3 positionVec3) {
        if (this.totalTicks - this.ticksAtLastPos > 100) {
            if (positionVec3.squareDistanceTo(this.lastPosCheck) < 2.25D) {
                this.clearPathEntity();
            }

            this.ticksAtLastPos = this.totalTicks;
            this.lastPosCheck = positionVec3;
        }
    }

    /**
     * If null path or reached the end
     */
    public boolean noPath() {
        return this.currentPath == null || this.currentPath.isFinished();
    }

    /**
     * sets active PathEntity to null
     */
    public void clearPathEntity() {
        this.currentPath = null;
    }

    protected Vec3 getEntityPosition() {
        return Vec3.createVectorHelper(
            this.theEntity.posX,
            this.theEntity.posY + (double) this.theEntity.height * 0.5D,
            this.theEntity.posZ);
    }

    /**
     * If on ground or swimming and can swim
     */
    protected boolean canNavigate() {
        return this.isInLiquid();
    }

    /**
     * Returns true if the entity is in water or lava, false otherwise
     */
    protected boolean isInLiquid() {
        return this.theEntity.isInWater() || this.theEntity.handleLavaMovement();
    }

    /**
     * Trims path data from the end to the first sun covered block
     */
    protected void removeSunnyPath() {}

    /**
     * Returns true when an entity of specified size could safely walk in a straight line between the two points. Args:
     * pos1, pos2, entityXSize, entityYSize, entityZSize
     */
    protected boolean isDirectPathBetweenPoints(Vec3 posVec31, Vec3 posVec32, int sizeX, int sizeY, int sizeZ) {
        MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(
            posVec31,
            Vec3.createVectorHelper(
                posVec32.xCoord,
                posVec32.yCoord + (double) this.theEntity.height * 0.5D,
                posVec32.zCoord),
            false,
            true,
            false);
        return movingobjectposition == null
            || movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.MISS;
    }
}
