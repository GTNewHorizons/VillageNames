package astrotibs.villagenames.ieep;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;


/**
 * Adapted from Villager Tweaks by sidben:
 * https://github.com/sidben/VillagerTweaks/blob/master/src/main/java/sidben/villagertweaks/common/ExtendedVillagerZombie.java
 * @author AstroTibs
 * 
 * Adds extra NBT info to zombie villagers, so I can track info 
 * like the villager original profession. 
 */
public class ExtendedVillageGuard implements IExtendedEntityProperties
{

    public    final static String Identifier = "VillagerInfo";
    //protected final static String ProfessionKey = "Profession";             // Controls village guard profession
    //protected final static String CareerKey = "Career";             // Controls village guard profession
    protected final static String InitializedKey = "Defined";               // Controls if a village guard was assigned a profession
    
    //@SuppressWarnings("unused")
    private final EntityLiving guard;
    protected World myWorld;

    
    
    //---------------------------------------------------------
    // Properties
    //---------------------------------------------------------
    //private int profession;
    //private int career;
    private Boolean hasValidData;      // TODO: Attempt to refactor and get rid of this property
    
    /*
    public int getProfession()
    {
        return this.profession;
    }
    
    public int getCareer()
    {
        return this.career;
    }
    
    public void setProfession(int profession)
    {
        this.profession = profession >= 0 ? profession : -1;
        this.hasValidData = true;
    }
    
    public void setCareer(int career)
    {
        this.career = career >= 0 ? career : -1;
        this.hasValidData = true;
    }
    
    public void pickRandomProfession()
    {
        if (this.profession <= -1 && (this.hasValidData == null || !this.hasValidData)) {
            int p = this.myWorld.rand.nextInt(6); // Uniformly distributed between 0 and 5
            this.setProfession(p);
        }
    }
    
    public void pickRandomProfessionAndCareer() {
        if (this.profession <= -1 && this.career <= -1 && (this.hasValidData == null || !this.hasValidData)) {
            int p = this.myWorld.rand.nextInt(6); // Uniformly distributed between 0 and 5
            int c = -1;
            
				switch(p) {
				
				case 0: // FARMER
					c = 1 + this.myWorld.rand.nextInt(4);
				
				case 1: // LIBRARIAN
					c = 1 + this.myWorld.rand.nextInt(2);
				
				case 2: // PRIEST
					c = 1;// + this.myWorld.rand.nextInt(1);
				
				case 3: // BLACKSMITH
					c = 1 + this.myWorld.rand.nextInt(3);
				
				case 4: // BUTCHER
					c = 1 + this.myWorld.rand.nextInt(2);
				
				case 5: // NITWIT
					c = 1;// + this.myWorld.rand.nextInt(1);
			}
				
	    	this.setProfession(p);
	        this.setCareer(c);
        }
    }
    */
    
    
    //---------------------------------------------------------
    // Constructor
    //---------------------------------------------------------
    public ExtendedVillageGuard(EntityLiving guard)
    {
        this.guard = guard;
        //this.profession = -1;
        //this.career = -1;
        this.hasValidData = false;
    }
    
    
    
    //---------------------------------------------------------
    // Methods
    //---------------------------------------------------------
   
    public static final void register(EntityLiving guard)
    {
        guard.registerExtendedProperties(ExtendedVillageGuard.Identifier, new ExtendedVillageGuard(guard));
    }
    
    public static final ExtendedVillageGuard get(EntityLiving guard) {
        return (ExtendedVillageGuard)guard.getExtendedProperties(ExtendedVillageGuard.Identifier);
    }
    
    
    @Override
    public void saveNBTData(NBTTagCompound compound) {
    	
        if (this.hasValidData == null) {
            //this.profession = -1;
            //this.career = -1;
            this.hasValidData = false;
        }
        
        NBTTagCompound properties = new NBTTagCompound();
        //properties.setInteger(ProfessionKey, this.profession);
        //properties.setInteger(CareerKey, this.career);
        properties.setBoolean(InitializedKey, this.hasValidData);

        compound.setTag(Identifier, properties); 
    }

    
    @Override
    public void loadNBTData(NBTTagCompound compound) {
    	
        NBTTagCompound properties = (NBTTagCompound)compound.getTag(Identifier);

        if (properties == null) {
            hasValidData = false;
            //profession = -1;
            //career = -1;
        } 
        else {
            //this.profession = properties.getInteger(ProfessionKey);
            //this.career = properties.getInteger(CareerKey);
            this.hasValidData = properties.getBoolean(InitializedKey);
        }

    }

    
    @Override
    public void init(Entity entity, World world) {
    	
        myWorld = world;
    }

}
