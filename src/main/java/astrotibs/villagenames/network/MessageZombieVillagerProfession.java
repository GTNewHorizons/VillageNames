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
public class MessageZombieVillagerProfession implements IMessage
{
    
	
    // Constructors
	public MessageZombieVillagerProfession() {}
    public MessageZombieVillagerProfession(int entityID, int profession, int career, int biomeType, int professionLevel
    		, int skinTone // v3.2
    		) { // Changed in v3.1
        this.entityID = entityID;
        this.profession = profession;
        this.career = career;
        this.biomeType = biomeType; // Added in v3.1
        this.professionLevel = biomeType; // Added in v3.1
        this.skinTone = skinTone; // v3.2
        
    }
    
 // Fields to be used by this message
    private int entityID;
    private int profession;
    private int career;
    private int biomeType; // Added in v3.1
    private int professionLevel; // Added in v3.1
    private int skinTone; // Added in v3.2
    
	
    // Getters
    public int getProfession() {return this.profession;}
    public int getCareer() {return this.career;}
    public int getEntityID() {return this.entityID;}
    public int getBiomeType() {return this.biomeType;} // Added in v3.1
    public int getProfessionLevel() {return this.professionLevel;} // Added in v3.1
    public int getSkinTone() {return this.skinTone;} // Added in v3.2


    // Reads the packet
    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityID = buf.readInt();
        this.profession = buf.readInt();
        this.career = buf.readInt();
        this.biomeType = buf.readInt(); // Added in v3.1
        this.professionLevel = buf.readInt(); // Added in v3.1
        this.skinTone = buf.readInt(); // Added in v3.2
        
        // note - maybe use ByteBufUtils
    }

    
    // Write the packet
    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityID);
        buf.writeInt(this.profession);
        buf.writeInt(this.career);
        buf.writeInt(this.biomeType); // Added in v3.1
        buf.writeInt(this.professionLevel); // Added in v3.1
        buf.writeInt(this.skinTone); // Added in v3.2
        
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
        // Added in v3.1
        r.append(", Biome = ");
        r.append(this.getBiomeType());
        r.append(", Profession Level = ");
        r.append(this.getProfessionLevel());
        // v3.2
        r.append(", Skin Tone = ");
        r.append(this.getSkinTone());
        
        return r.toString();
    }
    
}
