package astrotibs.villagenames.handler;

import com.emoniph.witchery.entity.EntityVillageGuard;
import cpw.mods.fml.common.Optional;
import net.minecraft.entity.Entity;

import static astrotibs.villagenames.VillageNames.isWitcheryLoaded;

public class WitcheryHelper {
    public static boolean isWitcheryGuard(Entity entity){
        if (!isWitcheryLoaded) return false;
        return instanceOfWitcheryGuard(entity);
    }

    @Optional.Method(modid = "witchery")
    private static boolean instanceOfWitcheryGuard(Entity entity){
        return entity instanceof EntityVillageGuard;
    }
}
