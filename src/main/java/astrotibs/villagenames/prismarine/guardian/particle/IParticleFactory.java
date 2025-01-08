package astrotibs.villagenames.prismarine.guardian.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IParticleFactory {

    EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn,
        double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_);
}
