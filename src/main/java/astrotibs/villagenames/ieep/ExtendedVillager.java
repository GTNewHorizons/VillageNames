package astrotibs.villagenames.ieep;

import java.util.Random;

import astrotibs.villagenames.config.GeneralConfig;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/*
 * Villager
 * summon Villager ~ ~ ~ {Profession:3}
 */

public class ExtendedVillager implements IExtendedEntityProperties {
	
	public final static String VN_VILLAGER_TAGS = "VNExtendedVillager";
	public final static String careerKey = "Career";
	public final static String biomeKey = "BiomeType"; // Added in v3.1
	public final static String professionLevelKey = "ProfessionLevel"; // Added in v3.1
	public final static String skinToneKey = "SkinTone"; // Added in v3.2
    protected final static String InitializedKey = "Defined";               // Controls if a villager was assigned a career and career level
    
	private final EntityVillager villager;
	private int career;
	private int biomeType; // Added in v3.1
	private int professionLevel; // Added in v3.1
	private int skinTone; // Added in v3.2
	//private int careerLevel;
    private Boolean hasValidData;      // TODO: Attempt to refactor and get rid of this property
    protected World theWorld;
    
	public ExtendedVillager(EntityVillager villager) {
		this.villager = villager;
		this.career = -1;
		this.biomeType = -1; // Added in v3.1
		this.professionLevel = -1; // Added in v3.1
		this.skinTone = -99; // Added in v3.2
	}
	
	/**
	* Used to register these extended properties for the villager during EntityConstructing event
	* This method is for convenience only; it will make your code look nicer
	*/
	public static final void register(EntityVillager villager)
	{
		villager.registerExtendedProperties(ExtendedVillager.VN_VILLAGER_TAGS, new ExtendedVillager(villager));
	}
	
	/**
	* Returns ExtendedPlayer properties for villager
	* This method is for convenience only; it will make your code look nicer
	*/
	public static final ExtendedVillager get(EntityVillager villager)
	{
		return (ExtendedVillager) villager.getExtendedProperties(VN_VILLAGER_TAGS);
	}
	
	
	public int getCareer() {
		return this.career;
	}
	public void setCareer(int n) {
		this.career=n;
        this.hasValidData = true;
	}
	
	// Added in v3.1
	public int getProfession()
	{
		return villager.getProfession();
	}
	public int getBiomeType()
	{
		return this.biomeType;
	}
	public void setBiomeType(int b)
	{
		this.biomeType = b;
		//this.hasValidData = true;
	}
	public int getProfessionLevel()
	{
		return this.professionLevel;
	}
	public void setProfessionLevel(int pl)
	{
		this.professionLevel = pl;
		//this.hasValidData = true;
	}

	// Added in v3.2
	public int getSkinTone() {return this.skinTone;}
	public void setSkinTone(int pl) {this.skinTone = pl;}
	
	// Save any custom data that needs saving here
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		
        if (this.hasValidData == null) {
            this.career = -1;
            this.biomeType = -1; // Added in v3.1
            this.professionLevel = -1; // Added in v3.1
            this.skinTone = -99; // Added in v3.2
            this.hasValidData = false;
        }
        
        NBTTagCompound properties = new NBTTagCompound();
        properties.setInteger(careerKey, this.career);
        properties.setInteger(biomeKey, this.biomeType); // Added in v3.1
        properties.setInteger(professionLevelKey, this.professionLevel); // Added in v3.1
        properties.setInteger(skinToneKey, this.skinTone); // Added in v3.2
        properties.setBoolean(InitializedKey, this.hasValidData);

        compound.setTag(VN_VILLAGER_TAGS, properties); 
	}
	
	
	
	// Load whatever data you saved
	@Override
	public void loadNBTData(NBTTagCompound compound) {
		
        NBTTagCompound properties = (NBTTagCompound)compound.getTag(VN_VILLAGER_TAGS);

        if (properties == null) {
            hasValidData = false;
            career = -1;
            biomeType = -1;
            professionLevel = -1;
            skinTone = -99;
        } 
        else {
            this.career = properties.getInteger(careerKey);
            this.biomeType = properties.hasKey(biomeKey) ? properties.getInteger(biomeKey) : -1;
            this.professionLevel = properties.getInteger(professionLevelKey);
            this.skinTone = properties.hasKey(skinToneKey) ? properties.getInteger(skinToneKey) : -99;
            this.hasValidData = properties.getBoolean(InitializedKey);
        }
	}
	
	
	
	@Override
	public void init(Entity entity, World world) {
        theWorld = world;
	}
	
	/**
	* Sets the career to a random value based on the input profession
	*/
	public static int pickRandomCareer(Random random, int profession) {
		
		//Random random = this.theWorld.rand;
		
		//if (this.career <= -1 && (this.hasValidData == null || !this.hasValidData)) {
			
			switch(profession) {
			
			case 0: // FARMER
				return 1 + random.nextInt(4);
			
			case 1: // LIBRARIAN
				return 1 + random.nextInt(2);
			
			case 2: // PRIEST
				return 1;// + random.nextInt(1);
			
			case 3: // BLACKSMITH
				return 1 + random.nextInt(GeneralConfig.modernVillagerTrades ? 4 : 3);
			
			case 4: // BUTCHER
				return 1 + random.nextInt(2);
			
			case 5: // NITWIT
				return 1;// + random.nextInt(1);
				
			default:
				return 0;
			}
			
		//}
		//return -1;
		
	}
	
	
	// Added in v3.1
	/**
	 * Set the profession level based on the number of trades
	 */
	public static int determineProfessionLevel(EntityVillager villager)
	{
		// Pull out the recipe list and use its size to determine the profession level of the villager
		MerchantRecipeList buyingList = ReflectionHelper.getPrivateValue( EntityVillager.class, villager, new String[]{"buyingList", "field_70963_i"} );
		int professionLevel = (buyingList == null ? 0 : Math.max(buyingList.size(),0)); // Ensure the PL is not below 0
		
		if (
				villager.getProfession() == 0 && ExtendedVillager.get(villager).getCareer() == 3 // Is a shepherd
				&& (professionLevel >=16 && !GeneralConfig.modernVillagerTrades) // Has unlocked the colored wool trades - modern trades condition
				)
		{professionLevel -= 15;} // Do this to offset the immense additions to the shepherd
		
		// Hard-code to remove pouch if nitwit
		if (
				villager.getProfession() == 5
				&& GeneralConfig.enableNitwit
				)
		{
			professionLevel = 0;
		}
		
		return professionLevel;
	}
	
}
