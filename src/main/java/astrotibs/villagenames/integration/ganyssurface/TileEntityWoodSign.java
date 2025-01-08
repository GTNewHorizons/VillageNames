package astrotibs.villagenames.integration.ganyssurface;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntitySign;

public class TileEntityWoodSign extends TileEntitySign {

    public boolean isStanding = false;

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("IsStanding", isStanding);
        nbt.setString("id", "ganyssurface.wood_sign");
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        isStanding = nbt.getBoolean("IsStanding");
    }
}
