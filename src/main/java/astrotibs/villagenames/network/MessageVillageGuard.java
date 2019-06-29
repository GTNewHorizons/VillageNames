package astrotibs.villagenames.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;


/**
 * Adapted from Villager Tweaks by sidben:
 * https://github.com/sidben/VillagerTweaks/blob/master/src/main/java/sidben/villagertweaks/network/MessageZombieVillagerProfession.java
 * @author AstroTibs
 * 
 * Used to notify the client of the zombie villager profession, 
 * so it can render the correct skin.
 *
 */
public class MessageVillageGuard implements IMessage
{
    
	
    public MessageVillageGuard() {}
    public MessageVillageGuard(int entityID) {//, int profession, int career) {
        this.entityID = entityID;
        //this.profession = profession;
        //this.career = career;
    }

    
    
    private int entityID;
    //private int profession;
    //private int career;
    
    /*
    public int getProfession() {
        return this.profession;
    }
    
    public int getCareer() {
        return this.career;
    }
    */
    
    public int getEntityID() {
        return this.entityID;
    }
    

    // Reads the packet
    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityID = buf.readInt();
        //this.profession = buf.readInt();
        //this.career = buf.readInt();
        // note - maybe use ByteBufUtils
    }

    
    // Write the packet
    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityID);
        //buf.writeInt(this.profession);
        //buf.writeInt(this.career);
    }

    
    
    @Override 
    public String toString() {
        StringBuilder r = new StringBuilder();
        
        r.append("Entity ID = ");
        r.append(this.getEntityID());
        /*
        r.append(", Profession = ");
        r.append(this.getProfession());
        r.append(", Career = ");
        r.append(this.getCareer());
        */
        
        return r.toString();
    }
    
}
