package astrotibs.villagenames.prismarine.guardian.entity.ai;

import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

import astrotibs.villagenames.prismarine.guardian.entity.monster.EntityGuardian;

public class EntityAIWanderExtender extends EntityAIWander {

    private EntityGuardian entity;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private double speed;
    private int executionChance;
    private boolean mustUpdate;

    public EntityAIWanderExtender(EntityGuardian creatureIn, double speedIn) {
        this(creatureIn, speedIn, 120); // The default 1.7 version
    }

    public EntityAIWanderExtender(EntityGuardian creatureIn, double speedIn, int chance) {
        super(creatureIn, speedIn);
        this.entity = creatureIn;
        this.speed = speedIn;
        this.executionChance = chance;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() // Fires as long as the thing is alive?
    {
        if (!this.mustUpdate) {
            if (this.entity.getAge() >= 100) {
                return false;
            }

            if (this.entity.getRNG()
                .nextInt(this.executionChance) != 0) {
                return false;
            }
        }

        Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);

        if (vec3 == null) {
            return false;
        } else {
            this.xPosition = vec3.xCoord;
            this.yPosition = vec3.yCoord;
            this.zPosition = vec3.zCoord;
            this.mustUpdate = false;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting() // Fires as long as the thing is in mid-wander
    {
        return !this.entity.navigator.noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() // Fires when the Guardian starts wandering
    {
        this.entity.navigator.tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }

    /**
     * Makes task to bypass chance
     */
    public void makeUpdate() // Fires when Guardian is attacked or when it completes its beam
    {
        this.mustUpdate = true;
    }

    /**
     * Changes task random possibility for execution
     */
    public void setExecutionChance(int newchance) {
        this.executionChance = newchance;
    }
}
