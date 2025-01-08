package astrotibs.villagenames.prismarine.guardian.particle;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Adapted from Draco18s's Artifacts:
 * https://github.com/Draco18s/Artifacts/blob/master/main/java/com/draco18s/artifacts/network/PacketHandlerClient.java
 * 
 * @author AstroTibs
 *
 */
public class PacketHandlerClient implements IMessageHandler<SToCMessage, IMessage> {

    public static final int MOB_APPEARANCE = 10;

    public PacketHandlerClient() {

    }

    public IMessage onMessage(SToCMessage packet, MessageContext context) {
        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
        World world = player.worldObj;
        ByteArrayInputStream stream = new ByteArrayInputStream(packet.getData());
        DataInputStream dis = new DataInputStream(stream);

        TileEntity te;
        try {
            int effectID = dis.readInt();

            if (effectID == MOB_APPEARANCE) {
                drawParticle(player.worldObj, player.posX, player.posY, player.posZ, "mobappearance", 0, player);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to read packet");
        }

        // Don't return anything.
        return null;
    }

    @SideOnly(Side.CLIENT)
    private static void drawParticle(World worldObj, double srcX, double srcY, double srcZ, String particleName,
        int age, EntityClientPlayerMP target) {
        EntityFX particle = null;
        if (particleName.equals("mobappearance")) {
            // RENDERS THE CURSE ANIMATION
            particle = new MobAppearance(worldObj, srcX, srcY, srcZ, target);
            // PLAYS THE CURSE SOUND
            target.worldObj.playSound(target.posX, target.posY, target.posZ, "VillageNames:curse", 1.0F, 1.0F, false);
        }
        if (particle != null) Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }
}
