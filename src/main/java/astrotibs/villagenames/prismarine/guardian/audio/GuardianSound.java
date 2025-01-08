package astrotibs.villagenames.prismarine.guardian.audio;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.ResourceLocation;

import astrotibs.villagenames.prismarine.guardian.entity.monster.EntityGuardian;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuardianSound extends MovingSound {

    private final EntityGuardian guardian;

    // This constructs as per beth's EntityGuardian.handleHealthUpdate method
    public GuardianSound(EntityGuardian guardian) {
        super(new ResourceLocation("VillageNames:guardian_attack_loop"));
        // super(new ResourceLocation(Reference.MOD_ID, "guardian_attack_loop"));
        this.guardian = guardian;
        this.field_147666_i = ISound.AttenuationType.NONE; // field_147666_i is attenuationType in 1.8
        this.repeat = true;
        this.field_147665_h = 0; // field_147665_h is repeatDelay in 1.8
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    // This fires as per beth's EntityGuardian.handleHealthUpdate method
    public void update() {
        if (!this.guardian.isDead && this.guardian.hasTargetedEntity() && this.guardian.getHealth() > 0 // Added this
                                                                                                        // contingency
                                                                                                        // so that beam
                                                                                                        // sound stops
                                                                                                        // when guardian
                                                                                                        // is killed
        ) {
            this.xPosF = (float) this.guardian.posX;
            this.yPosF = (float) this.guardian.posY;
            this.zPosF = (float) this.guardian.posZ;
            float f = this.guardian.getAttackAnimationScale(0.0F);
            this.volume = 0.0F + 1.0F * f * f;
            this.field_147663_c = 0.7F + 0.5F * f; // field_147663_c is "pitch" in 1.8
        } else {
            this.donePlaying = true;
        }
    }
}
