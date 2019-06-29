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

// Added in v3.1

public class MessageModernVillagerSkin implements IMessage
{
	// Constructors
	public MessageModernVillagerSkin() {}
    public MessageModernVillagerSkin(int entityID, int profession, int career, int biomeType, int professionLevel) {
        this.entityID = entityID;
        this.profession = profession;
        this.career = career;
        this.biomeType = biomeType;
        this.professionLevel = professionLevel;
    }
    
    // Fields to be used by this message
    private int entityID;
    private int profession;
    private int career;
    private int biomeType;
    private int professionLevel;
    
    
    // Getters
    public int getProfession() {return this.profession;}
    public int getCareer() {return this.career;}
    public int getEntityID() {return this.entityID;}
    public int getBiomeType() {return this.biomeType;}
    public int getProfessionLevel() {return this.professionLevel;}
    

    // Reads the packet
    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityID = buf.readInt();
        this.profession = buf.readInt();
        this.career = buf.readInt();
        this.biomeType = buf.readInt();
        this.professionLevel = buf.readInt();
        // note - maybe use ByteBufUtils
    }

    
    // Write the packet
    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityID);
        buf.writeInt(this.profession);
        buf.writeInt(this.career);
        buf.writeInt(this.biomeType);
        buf.writeInt(this.professionLevel);
    }

    
    // Builds a string
    @Override 
    public String toString() {
        
    	StringBuilder r = new StringBuilder();
        
        r.append("Entity ID = ");
        r.append(this.getEntityID());
        r.append(", Profession = ");
        r.append(this.getProfession());
        r.append(", Career = ");
        r.append(this.getCareer());
        r.append(", BiomeType = ");
        r.append(this.getBiomeType());
        r.append(", Profession Level = ");
        r.append(this.getProfessionLevel());
        
        return r.toString();
    }
    
}
