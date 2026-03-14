package astrotibs.villagenames.prismarine.guardian.entity.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockAccess;

import astrotibs.villagenames.prismarine.guardian.util.IntHashMap1122;
import astrotibs.villagenames.prismarine.guardian.util.MathHelper1122;

public abstract class NodeProcessor {

    protected IBlockAccess blockaccess;
    protected IntHashMap1122<PathPointSwimmer> pointMap = new IntHashMap1122();
    protected int entitySizeX;
    protected int entitySizeY;
    protected int entitySizeZ;

    public void initProcessor(IBlockAccess iblockaccessIn, Entity entityIn) {
        this.blockaccess = iblockaccessIn;
        this.pointMap.clearMap();
        this.entitySizeX = MathHelper1122.floor_float(entityIn.width + 1.0F);
        this.entitySizeY = MathHelper1122.floor_float(entityIn.height + 1.0F);
        this.entitySizeZ = MathHelper1122.floor_float(entityIn.width + 1.0F);
    }

    /**
     * This method is called when all nodes have been processed and PathEntity is created.
     * {@link net.minecraft.world.pathfinder.WalkNodeProcessor WalkNodeProcessor} uses this to change its field {@link
     * net.minecraft.world.pathfinder.WalkNodeProcessor#avoidsWater avoidsWater}
     */
    public void postProcess() {}

    /**
     * Returns a mapped point or creates and adds one
     */
    protected PathPointSwimmer openPoint(int x, int y, int z) {
        int i = PathPointSwimmer.makeHash(x, y, z);
        PathPointSwimmer pathpoint = (PathPointSwimmer) this.pointMap.lookup(i);

        if (pathpoint == null) {
            pathpoint = new PathPointSwimmer(x, y, z);
            this.pointMap.addKey(i, pathpoint);
        }

        return pathpoint;
    }

    /**
     * Returns given entity's position as PathPoint
     */
    public abstract PathPointSwimmer getPathPointTo(Entity entityIn);

    /**
     * Returns PathPoint for given coordinates
     */
    public abstract PathPointSwimmer getPathPointToCoords(Entity entityIn, double x, double y, double target);

    public abstract int findPathOptions(PathPointSwimmer[] pathOptions, Entity entityIn, PathPointSwimmer currentPoint,
        PathPointSwimmer targetPoint, float maxDistance);
}
