package astrotibs.villagenames.prismarine.guardian.entity.pathfinding;

/*
 * The 1.8 backport of Path
 */
public class PathSwimmer {

    /** Contains the points in this path */
    private PathPointSwimmer[] pathPoints = new PathPointSwimmer[1024];
    /** The number of points in this path */
    private int count;

    /**
     * Adds a point to the path
     */
    public PathPointSwimmer addPoint(PathPointSwimmer point) {
        if (point.index >= 0) {
            throw new IllegalStateException("OW KNOWS!");
        } else {
            if (this.count == this.pathPoints.length) {
                PathPointSwimmer[] apathpoint = new PathPointSwimmer[this.count << 1];
                System.arraycopy(this.pathPoints, 0, apathpoint, 0, this.count);
                this.pathPoints = apathpoint;
            }

            this.pathPoints[this.count] = point;
            point.index = this.count;
            this.sortBack(this.count++);
            return point;
        }
    }

    /**
     * Clears the path
     */
    public void clearPath() {
        this.count = 0;
    }

    /**
     * Returns and removes the first point in the path
     */
    public PathPointSwimmer dequeue() {
        PathPointSwimmer pathpoint = this.pathPoints[0];
        this.pathPoints[0] = this.pathPoints[--this.count];
        this.pathPoints[this.count] = null;

        if (this.count > 0) {
            this.sortForward(0);
        }

        pathpoint.index = -1;
        return pathpoint;
    }

    /**
     * Changes the provided point's distance to target
     */
    public void changeDistance(PathPointSwimmer p_75850_1_, float p_75850_2_) {
        float f = p_75850_1_.distanceToTarget;
        p_75850_1_.distanceToTarget = p_75850_2_;

        if (p_75850_2_ < f) {
            this.sortBack(p_75850_1_.index);
        } else {
            this.sortForward(p_75850_1_.index);
        }
    }

    /**
     * Sorts a point to the left
     */
    private void sortBack(int p_75847_1_) {
        PathPointSwimmer pathpoint = this.pathPoints[p_75847_1_];
        int i;

        for (float f = pathpoint.distanceToTarget; p_75847_1_ > 0; p_75847_1_ = i) {
            i = p_75847_1_ - 1 >> 1;
            PathPointSwimmer pathpoint1 = this.pathPoints[i];

            if (f >= pathpoint1.distanceToTarget) {
                break;
            }

            this.pathPoints[p_75847_1_] = pathpoint1;
            pathpoint1.index = p_75847_1_;
        }

        this.pathPoints[p_75847_1_] = pathpoint;
        pathpoint.index = p_75847_1_;
    }

    /**
     * Sorts a point to the right
     */
    private void sortForward(int p_75846_1_) {
        PathPointSwimmer pathpoint = this.pathPoints[p_75846_1_];
        float f = pathpoint.distanceToTarget;

        while (true) {
            int i = 1 + (p_75846_1_ << 1);
            int j = i + 1;

            if (i >= this.count) {
                break;
            }

            PathPointSwimmer pathpoint1 = this.pathPoints[i];
            float f1 = pathpoint1.distanceToTarget;
            PathPointSwimmer pathpoint2;
            float f2;

            if (j >= this.count) {
                pathpoint2 = null;
                f2 = Float.POSITIVE_INFINITY;
            } else {
                pathpoint2 = this.pathPoints[j];
                f2 = pathpoint2.distanceToTarget;
            }

            if (f1 < f2) {
                if (f1 >= f) {
                    break;
                }

                this.pathPoints[p_75846_1_] = pathpoint1;
                pathpoint1.index = p_75846_1_;
                p_75846_1_ = i;
            } else {
                if (f2 >= f) {
                    break;
                }

                this.pathPoints[p_75846_1_] = pathpoint2;
                pathpoint2.index = p_75846_1_;
                p_75846_1_ = j;
            }
        }

        this.pathPoints[p_75846_1_] = pathpoint;
        pathpoint.index = p_75846_1_;
    }

    /**
     * Returns true if this path contains no points
     */
    public boolean isPathEmpty() {
        return this.count == 0;
    }
}
