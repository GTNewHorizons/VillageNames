package astrotibs.villagenames.tracker;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.ieep.ExtendedVillageGuard;
import astrotibs.villagenames.ieep.ExtendedVillager;
import astrotibs.villagenames.ieep.ExtendedZombieVillager;
import astrotibs.villagenames.name.NameGenerator;
import astrotibs.villagenames.prismarine.minecraft.Vec3i;
import astrotibs.villagenames.tracker.ServerInfoTracker.EventType;
import astrotibs.villagenames.utility.FunctionsVN;
import astrotibs.villagenames.utility.LogHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

/**
 * Adapted from Villager Tweaks by sidben:
 * https://github.com/sidben/VillagerTweaks/blob/master/src/main/java/sidben/villagertweaks/tracker/EventTracker.java
 * @author AstroTibs
 */
public class EventTracker
{

    //private final Vec3i  _position;
	//private final Vec3   eventPos;
	private final Vec3i  eventPos;
    private final String customName;
    private final Object extraInfo;
    private int          tickWhenAdded;
    private final int    entityID;
    
    static final String PMTMUnloc = "Traveling Merchant";
    static final String PMTMUnlocModern = "Traveling Merchant";
    static final String PMShUnloc = "Sheepman";
    static final String PMShUnlocModern = "Sheepman";
    static final String PMSSUnloc = "Sheepman Smith";
    
    /**
     * Position where the event happened
     * 
     */
    public Vec3i getPosition()
    //public Vec3 getPosition()
    {
        return eventPos;
    }

    /**
     * Does the event involves something with a custom name? Store it here.
     * 
     */
    public String getCustomName()
    {
        return customName;
    }
    
    /**
     * Does the event has extra information? Use this generic object.
     * 
     */
    public Object getExtraInfo()
    {
        return extraInfo;
    }

    /**
     * Returns the entity ID being tracked, if any.
     * 
     */
    public int getEntityID()
    {
        return entityID;
    }

    /**
     * Sets the tick in which this entry was created (Tick of Birth).
     * 
     */
    public void setBirthTick(int tick)
    {
        this.tickWhenAdded = tick;
    }

    /**
     * Gets the tick in which this entry was created (Tick of Birth).
     * 
     */
    public int getBirthTick()
    {
        return tickWhenAdded;
    }

    /**
     * Forces this object to "expire" so it won't be used again.
     * 
     */
    public void expireNow()
    {
        this.tickWhenAdded = -1;
    }



    //private EventTracker(int entityID, Vec3i pos, String customName, Object extraInfo) {
    private EventTracker(int entityID, Vec3i pos, String customName, Object extraInfo) {
        this.entityID = entityID;
        this.customName = customName;
        this.eventPos = pos;
        this.extraInfo = extraInfo;
        this.tickWhenAdded = 0;
    }

    public EventTracker(EntityVillager villager, ExtendedVillager ieep) {
        this(
        		villager.getEntityId(),
        		//villager.getPosition(),
        		//Vec3.createVectorHelper(villager.posX, villager.posY, villager.posZ), // Replaced because of mp server-side crash
        		new Vec3i(villager.posX, villager.posY + 0.5D, villager.posZ),
        		villager.getCustomNameTag(),
        		new Object[] {
        				
        				villager.getProfession(),
        				
        				// Changed in v3.2 to actually use ieep
        				(GeneralConfig.villagerCareers) ? ieep.getCareer() : 0,
						villager.isChild(),
        				(GeneralConfig.modernVillagerSkins) ? ieep.getBiomeType() : -1, // Added in v3.1
        				(GeneralConfig.modernVillagerSkins) ? ieep.getProfessionLevel() : -1, // Added in v3.1
						(GeneralConfig.modernVillagerSkins && GeneralConfig.villagerSkinTones) ? ieep.getSkinTone() : -99 // Added in v3.2
        				/*
        				(GeneralConfig.villagerCareers) ? (ExtendedVillager.get(villager)).getCareer() : 0,
        				villager.isChild(),
        				(GeneralConfig.modernVillagerSkins) ? (ExtendedVillager.get(villager)).getBiomeType() : -1, // Added in v3.1
        				(GeneralConfig.modernVillagerSkins) ? (ExtendedVillager.get(villager)).getProfessionLevel() : -1 // Added in v3.1
        				*/
        				}
        		);
    }
    
    public EventTracker(EntityLiving guard) {
        this(
        		guard.getEntityId(),
        		//villager.getPosition(),
        		//Vec3.createVectorHelper(villager.posX, villager.posY, villager.posZ), // Replaced because of mp server-side crash
        		new Vec3i(guard.posX, guard.posY + 0.5D, guard.posZ),
        		guard.getCustomNameTag(),
        		new Object[] {
        				//guard.getProfession(),
        				//(GeneralConfigHandler.villagerCareers) ? (ExtendedVillager.get(guard)).getCareer() : 0,
        				//guard.isChild()
        				}
        		);
    }
    
    public EventTracker(EntityZombie zombie, ExtendedZombieVillager ieep) {
        //this(zombie.getEntityId(), zombie.getPosition(1.0F), zombie.getCustomNameTag(), properties);
    	this(
    			zombie.getEntityId(),
    			//zombie.getPosition(),
    			//Vec3.createVectorHelper(zombie.posX, zombie.posY, zombie.posZ), // Replaced because of mp server-side crash
    			new Vec3i(zombie.posX, zombie.posY + 0.5D, zombie.posZ),
    			zombie.getCustomNameTag(), 
    			 // Added Object structure in v3.1
    			new Object[] {
    					ieep.getProfession(),
        				(GeneralConfig.villagerCareers) ? (ieep).getCareer() : 0,
        				zombie.isChild(),
        				(GeneralConfig.modernZombieSkins) ? ieep.getBiomeType() : -1, // v3.2.3
        				(GeneralConfig.modernZombieSkins) ? ieep.getProfessionLevel() : -1, // v3.2.3
        				(GeneralConfig.villagerSkinTones) ? ieep.getSkinTone() : -99, // Added in v3.2
        				}
    			);
    }
    
    
    
    /**
     * Updates a zombie entity with the villager info this object is tracking.
     */
    //public void updateZombie(EntityZombie zombie, ExtendedVillagerZombie properties)
    public void updateZombie(EntityJoinWorldEvent event, ExtendedZombieVillager ieep)
    {
    	EntityZombie zombie = (EntityZombie) event.entity;
    	
    	if (GeneralConfig.debugMessages) {LogHelper.info("EventTracker.updateZombie called with this.getCustomName(): " + this.getCustomName() + ", this.getObject(): " + this.getExtraInfo() );}
        
    	// Note: I must trust that this object actually contain a villager info. If not, the cast below will fail.
        final Object[] extraInfo = (Object[]) this.getExtraInfo();
        final int profession = (Integer) extraInfo[0];
        final int career     = (Integer) extraInfo[1];
        final boolean isBaby = (Boolean) extraInfo[2];
        // Added in v3.1
        final int biomeType = (Integer) extraInfo[3];
        final int professionLevel = (Integer) extraInfo[4];
        final int skinTone = (Integer) extraInfo[5]; // Added in v3.2
        
        // Custom name
        //if (this.getCustomName() != "") {
        String customName = this.getCustomName();
        
		// Unfortunately, these are client-side only and will crash the server. So we're going to just have to deal with the English versions.
		/*
		try {
			PMTMUnloc = I18n.format(Reference.PMTravelingMerchantUnlocalized);
			PMTMUnlocModern = I18n.format(Reference.PMTravelingMerchantUnlocalizedModern);
			PMShUnloc = I18n.format(Reference.PMSheepmanUnlocalized);
			PMShUnlocModern = I18n.format(Reference.PMSheepmanUnlocalizedModern);
			PMSSUnloc = I18n.format(Reference.PMSheepmanSmithUnlocalized);
		}
		catch (Exception e) {}
        */
        if ( !customName.equals("") && !customName.equals(null)
        		&& !customName.equals( PMTMUnloc )
        		&& !customName.equals( PMTMUnlocModern )
        		&& !customName.equals( PMShUnloc )
        		&& !customName.equals( PMShUnlocModern )
        		&& !customName.equals( PMSSUnloc )
        		) {
        	zombie.setCustomNameTag(this.getCustomName());
        	zombie.func_110163_bv(); //Equivalent to EntityLiving.enablePersistence() in 1.8
        }
        
        // Adult or child
        zombie.setChild(isBaby);
        
        // Profession
        //if ( customName.equals( I18n.format(Reference.PMSheepmanSmithUnlocalized) ) ) {
        if ( customName.equals( PMSSUnloc ) ) {
        	ieep.setProfession(3); // Hard-wired to be a blacksmith -- only works if the sheep has no name :-/
        }
        else if (profession >= 0) {// Allowing above 4 so that you can have modded professions  //&& profession <= 4) {   // vanilla professions
            ieep.setProfession(profession);
        } else {
            ieep.setProfession(-1);           // vanilla zombie villager
        }
        
        if ( career > 0 ) {
        	ieep.setCareer(career);
        }
        else {
        	ieep.setCareer(0);
        }
        
        // BiomeType
        if (ieep.getBiomeType() <0)
        {
        	ieep.setBiomeType(FunctionsVN.returnBiomeTypeForEntityLocation(zombie));
        }
        else
        {
        	ieep.setBiomeType(biomeType);
        }
        
        // SkinTone
        if (ieep.getSkinTone() == -99) {ieep.setSkinTone(FunctionsVN.returnSkinToneForEntityLocation(zombie));}
        else {ieep.setSkinTone(skinTone);}
        
        
        // ProfessionLevel
        ieep.setProfessionLevel(professionLevel);
        
        
    	// Only strip armor if modern villager skins are on
    	if (GeneralConfig.modernZombieSkins && GeneralConfig.removeMobArmor)
    	{
    		// Turn off gear pickup to prevent goofball rendering
    		if (zombie.canPickUpLoot()) {zombie.setCanPickUpLoot(false);}
    		
	    	// Strip armor
    		for (int slot=1; slot <=4; slot++) {if (zombie.getEquipmentInSlot(slot) != null) {zombie.setCurrentItemOrArmor(slot, null);}}
    	}
    }
    
    
    
    /**
     * Updates a village guard entity with the villager info this object is tracking.
     * 
     */
    //public void updateZombie(EntityZombie zombie, ExtendedVillagerZombie properties)
    public void updateGuard(LivingUpdateEvent event, ExtendedVillageGuard properties)
    {
    	EntityLiving guard = (EntityLiving) event.entity;
    	NBTTagCompound compound = new NBTTagCompound();
    	guard.writeEntityToNBT(compound);
		int targetAge = compound.getInteger("Age");
    	
    	if (GeneralConfig.debugMessages) {LogHelper.info("EventTracker.updateGuard called with this.getCustomName(): " + this.getCustomName() + ", this.getObject(): " + this.getExtraInfo() );}
        
    	// Note: I must trust that this object actually contain a villager info. If not, the cast below will fail.
        final Object[] extraInfo = (Object[]) this.getExtraInfo();
        
        String customName = this.getCustomName();
        
        
        if ( !customName.equals("") && !customName.equals(null)
        		) {
        	
        	
        	// Go through the list and eliminate all entries with a matching name
            for (int i = (EventType.GUARD).getTracker().size()-1; i >=0; i-- ) {
            	if ( ((EventType.GUARD).getTracker().get(i)).customName == customName) {(EventType.GUARD).getTracker().remove(i);}
            }
        	
        	
        	// Then account for profession flag
        	
        	if ( // Remove any tag that already exists
        			customName.indexOf("(")!=-1
        			) { // Target has a job tag: remove it...
				customName = customName.substring(0, customName.indexOf("(")).trim();
			}
        	if ( // Add a profession tag if the flag is set
        			(
        					GeneralConfig.addJobToName
        					&& ( !(guard instanceof EntityVillager) || targetAge>=0 )
        					)
        			) { // Target is named but does not have job tag: add one!
        		customName = customName + " " + NameGenerator.getCareerTag(guard.getClass().toString().substring(6), 0, 0);
        		customName = customName.trim();
			}
        	
        	
        	guard.setCustomNameTag(customName);
        	guard.func_110163_bv(); //Equivalent to EntityLiving.enablePersistence() in 1.8
        }
        
    }
    
    

    /**
     * Updates a villager entity with the zombie info this object is tracking.
     * 
     * @param villager
     */
    public void updateVillager(EntityVillager villager, ExtendedVillager ieep)
    {
    	if (GeneralConfig.debugMessages) {LogHelper.info("EventTracker.updateVillager called with this.getCustomName(): " + this.getCustomName());}
    	
        // Note: I must trust that this object actually contains zombie info. If not, the cast below will fail.
        //final ExtendedZombieVillager ieep = (ExtendedZombieVillager) this.getExtraInfo();
        final Object[] extraInfo = (Object[]) this.getExtraInfo();
        final int profession = (Integer) extraInfo[0];
        final int career     = (Integer) extraInfo[1];
        final boolean isBaby = (Boolean) extraInfo[2];
        // Added in v3.1
        final int biomeType = (Integer) extraInfo[3];
        final int professionLevel = (Integer) extraInfo[4];
        final int skinTone = (Integer) extraInfo[5]; // Added in v3.2

        String customName = this.getCustomName();
        
        // Renovated in v3.1
        // Custom name
        if ( !customName.equals("") && !customName.equals(null)
        		&& !customName.equals( PMTMUnloc )
        		&& !customName.equals( PMTMUnlocModern )
        		&& !customName.equals( PMShUnloc )
        		&& !customName.equals( PMShUnlocModern )
        		&& !customName.equals( PMSSUnloc )
        		) {
        	villager.setCustomNameTag(this.getCustomName());
        }
        
        // Adult or child
        villager.setGrowingAge(isBaby ? -24000 : 0);
        
        // Profession
        //if ( customName.equals( I18n.format(Reference.PMSheepmanSmithUnlocalized) ) ) {
        if ( customName.equals( PMSSUnloc ) )
        {
        	villager.setProfession(3); // Hard-wired to be a blacksmith -- only works if the sheep has no name :-/
        }
        else
        {// Allowing above 4 so that you can have modded professions  //&& profession <= 4) {   // vanilla professions
        	villager.setProfession(profession);
        }
        
        // Career
        if ( career > 0 ) {
        	ieep.setCareer(career);
        }
        else {
        	ieep.setCareer(0);
        }
        
        // Added in v3.1
        // BiomeType
        if (ieep.getBiomeType() <0)
        {
        	ieep.setBiomeType(FunctionsVN.returnBiomeTypeForEntityLocation(villager));
        }
        else
        {
        	ieep.setBiomeType(biomeType);
        }
        
        // Added in v3.2
        // SkinTone
        if (ieep.getSkinTone() == -99) {ieep.setSkinTone(FunctionsVN.returnSkinToneForEntityLocation(villager));}
        else {ieep.setSkinTone(skinTone);}
        
        
        // Only strip armor if modern villager skins are on
    	if (GeneralConfig.modernVillagerSkins && GeneralConfig.removeMobArmor)
    	{
    		// Turn off gear pickup to prevent goofball rendering
    		if (villager.canPickUpLoot()) {villager.setCanPickUpLoot(false);}
    		
	    	// Strip armor
    		for (int slot=1; slot <=4; slot++) {if (villager.getEquipmentInSlot(slot) != null) {villager.setCurrentItemOrArmor(slot, null);}}
    	}
    }
    

    @Override
    public String toString()
    {
        final StringBuilder r = new StringBuilder();

        r.append("Entity ID = ");
        r.append(this.getEntityID());
        r.append(", Position = ");
        if (this.getPosition() == null) {
            r.append("NULL");
        } else {
            r.append(this.getPosition().toString());
        }
        r.append(", Tick of Birth = ");
        r.append(this.getBirthTick());
        r.append(", Custom Name = ");
        r.append(this.getCustomName());
        r.append(", Extra Info = ");
        if (this.getExtraInfo() == null) {
            r.append("NULL");
        } else {
            r.append(this.getExtraInfo().getClass().getName());
            r.append(":");
            r.append(this.getExtraInfo().toString());
        }

        return r.toString();
    }



}