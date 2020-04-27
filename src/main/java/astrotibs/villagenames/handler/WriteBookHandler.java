package astrotibs.villagenames.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import astrotibs.villagenames.VillageNames;
import astrotibs.villagenames.banner.BannerGenerator;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.item.ModItems;
import astrotibs.villagenames.name.NameGenerator;
import astrotibs.villagenames.nbt.VNWorldData;
import astrotibs.villagenames.nbt.VNWorldDataStructure;
import astrotibs.villagenames.structure.StructureRegistry;
import astrotibs.villagenames.utility.FunctionsVN;
import astrotibs.villagenames.utility.LogHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.village.Village;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.MapGenStructureData;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

public class WriteBookHandler {
	
	//static Random random = new Random();
	
	/**
	 * This method is fired when a player right clicks a regular book onto a Villager or modded village inhabitant.
	 * It consumes the book and returns the new Village Book.
	 * @param bookType - String referring to which type of book to drop. e.g. "village", "MoonVillage", etc
	 * @param author - Either the name of the target that's writing the book, or your own name if using a Codex
	 * @param locX, locY, locZ - The location of a village's sign, or otherwise the midpoint of the feature's bounding box 
	 * @param structureName, dimensionName - The title of the structure and the dimension, to be recorded in the book
	 * @param namePrefix, nameRoot, nameSuffix - The three string components of any name 
	 * @param population, radius - internal stats for the village
	 * @param playerIsInVillage - whether or not you're in a village bounding box. Checked against villageYouAreIn below to trigger Ghost Town achievement.
	 * @param villageYouAreIn - the presumed village that the villager is inside - used to produce second page
	 * @param event - The event invoked that led to this method's call - used to produce second page
	 * @param targetProfession, targetCareer - The integers representing profession and career: used to write a second page
	 * @param targetTradeCount, playerRep - The player's rapport with the target: used to write a second page
	 * @param player, target - used to consume the book and drop the newly-written book
	 */
	public static void targetWriteNewVillageBook (
			String bookType, String author,
			int locX, int locY, int locZ,
			String structureName, String dimensionName,
			String namePrefix, String nameRoot, String nameSuffix,
			boolean playerIsInVillage, Village villageYouAreIn, EntityInteractEvent event,
			int targetProfession, int targetCareer,
			int targetTradeCount, int playerRep,
			EntityPlayer player, EntityLiving target
			) {
		
		// \n is "new line"
		// \u00a7 is the section symbol, which Minecraft uses for text formatting
		// l is bold, r is reset, o is italic, n is underline
		
		int population = -1;
		int radius = -1;
		
		try {
			population = villageYouAreIn.getNumVillagers();
			radius = villageYouAreIn.getVillageRadius();
		}
		catch (Exception e) {
			// Failed to evaluate village, or there is none.
		}
		
		String targetClassPath = "";
		// It's possible to pass a null entity (when using a Codex)
		if (target != null) { targetClassPath = target.getClass().toString().substring(6); }
		
		String bookContents = "\n"; // Start on line 2
		
		bookContents += (structureName.equals("")) ? "" : structureName+":\n"; // Add the name of the structure at the top
		
		bookContents += "\u00a7l"; // The name will be bolded
		
		// Add the next lines with the town's title
		if ( (namePrefix + " " + nameRoot + " " + nameSuffix).trim().length() <=15 ) { // All three pieces fit on the 1st line
			bookContents += (namePrefix + " " + nameRoot + " " + nameSuffix).trim();
		}
		else if ( (namePrefix + " " + nameRoot).trim().length() <=15 ) { // Just the first two pieces fit on the 1st line
			bookContents += (namePrefix + " " + nameRoot).trim();
			bookContents += "\n" + "\u00a7l" + nameSuffix;
		}
		else { // Only the prefix fits on the 1st line!
			bookContents += (namePrefix).trim(); // Line 1
			
			if ( (nameRoot + " " + nameSuffix).trim().length() <=15 ) { // The name root and suffix fit on the 2nd line
				bookContents += "\n" + "\u00a7l" + (nameRoot + " " + nameSuffix).trim(); // Line 2
			}
			else { // Just the root name fits on the second line!
				bookContents += "\n" + "\u00a7l" + (nameRoot).trim(); // Line 2
				bookContents += "\n" + "\u00a7l" + (nameSuffix).trim(); // Line 3
			}
		}
		
		// Next, add the structures coordinates:
		if (GeneralConfig.recordStructureCoords) {
			bookContents +=
					"\n\n" +  // Skip a line before adding the next set of info
					"\u00a7rLocated at:\n" +
					"\u00a7rx = \u00a7l"+locX+"\u00a7r\ny = \u00a7l"+locY+"\u00a7r\nz = \u00a7l"+locZ + "\u00a7r";
		}
		
		bookContents += (dimensionName.equals("")) ? "\u00a7r" : "\n\u00a7r(" + dimensionName + ")";
		
		bookContents += "\n"; // New blank line
		
		// Next, if there are viable population/radius values for this structure, record them:
		if (population > -1) {bookContents += "\n\u00a7rPopulation: \u00a7l" + population;} 
		if (radius > 0)      {bookContents += "\n\u00a7rRadius: \u00a7l" + radius;}
		
		// Now that we have the Page 1 contents, put it together:
		
		ItemStack book;
		
		// Select the book type to produce based on what string was entered
		
		// Vanilla structures
		     if (bookType.toLowerCase().trim().equals("mineshaft")) {book = new ItemStack(ModItems.mineshaftBook);}
		else if (bookType.toLowerCase().trim().equals("temple")) {book = new ItemStack(ModItems.templeBook);}
		else if (bookType.toLowerCase().trim().equals("monument")) {book = new ItemStack(ModItems.monumentBook);}
		else if (bookType.toLowerCase().trim().equals("fortress")) {book = new ItemStack(ModItems.fortressBook);}
		else if (bookType.toLowerCase().trim().equals("stronghold")) {book = new ItemStack(ModItems.strongholdBook);}
		else if (bookType.toLowerCase().trim().equals("endcity")) {book = new ItemStack(ModItems.endcityBook);}
		else if (bookType.toLowerCase().trim().equals("mansion")) {book = new ItemStack(ModItems.mansionBook);}
		
		// Mod structures     
		else if (bookType.toLowerCase().trim().equals("moonvillage")) {book = new ItemStack(ModItems.moonvillageBook);}
		else if (bookType.toLowerCase().trim().equals("fronosvillage")) {book = new ItemStack(ModItems.fronosvillageBook);}
		else if (bookType.toLowerCase().trim().equals("koentusvillage")) {book = new ItemStack(ModItems.koentusvillageBook);}
		else if (bookType.toLowerCase().trim().equals("nibiruvillage")) {book = new ItemStack(ModItems.nibiruvillageBook);}
		else if (bookType.toLowerCase().trim().equals("abandonedbase")) {book = new ItemStack(ModItems.abandonedbasebook);}
		     
		// New temples
		else if (bookType.toLowerCase().trim().equals("jungletemple")) {book = new ItemStack(ModItems.jungletempleBook);}
		else if (bookType.toLowerCase().trim().equals("desertpyramid")) {book = new ItemStack(ModItems.desertpyramidBook);}
		else if (bookType.toLowerCase().trim().equals("swamphut")) {book = new ItemStack(ModItems.swamphutBook);}
		else if (bookType.toLowerCase().trim().equals("igloo")) {book = new ItemStack(ModItems.igloobook);}
		
		else {book = new ItemStack(ModItems.villageBook);} // By default, or if the type was misspelled, use a Village book.

		List<String> pages = new ArrayList<String>();
		
		if (book.stackTagCompound == null) {book.setTagCompound(new NBTTagCompound());} // Priming the book to receive information
		book.stackTagCompound.setString("title", (namePrefix + " " + nameRoot + " " + nameSuffix).trim() ); // Set the title
		// Set the author
		if (author!=null && !author.equals("")) {
			try { book.stackTagCompound.setString("author", author.indexOf("(")!=-1 ? author.substring(0, author.indexOf("(")).trim() : author ); }
			// If the target's name starts with a parenthesis for some reason, this will crash with an index OOB exception. In that case, add no author name.
        	catch (Exception e) {}
        }
		
		// Set the book's contents
        NBTTagList pagesTag = new NBTTagList();

        // Page 1, with the structure information
        pagesTag.appendTag(new NBTTagString(bookContents));
        
        // ----------------------- //
        // ----- Second Page ----- //
        // ----------------------- //
        if (target!=null) {
        			if ((target instanceof EntityVillager 
            				|| target.getClass().toString().substring(6).equals(ModObjects.PMLostMinerClass)
            				|| target.getClass().toString().substring(6).equals(ModObjects.PMTravelingMerchantClass)) 
            		&&
            		((author != "" && author != null) || !GeneralConfig.nameEntities) ) {
            	
            	// All the machinery to make a second page should only work if the villager is named.
            	// Alternatively, do not require a name if you have the "Name Villagers" flag off.
            	
        		// Put second page call into try/catch - v3.2.2
        		try {
        			String structureHintPageText = makeSecondPage(
                			event, targetClassPath, villageYouAreIn,
                			locX, locY, locZ,
                			playerRep, targetProfession, targetCareer, targetTradeCount );
        			if (!structureHintPageText.equals("")) { // Add a second page!
                		pagesTag.appendTag(new NBTTagString(structureHintPageText));
                	}
        		}
        		catch (Exception e) {}
            }
        }
        		
        
        // Add all the pages so far to the book
        book.stackTagCompound.setTag("pages", pagesTag);
        
        // Consume the book held by the player
        if (!player.capabilities.isCreativeMode) {player.inventory.consumeInventoryItem(Items.book);}
        
        // Give the book to the player
        EntityItem eitem = ( (GeneralConfig.villagerDropBook && target != null) ? target : player).entityDropItem(book, 1);
        eitem.delayBeforeCanPickup = 0; //No delay: directly into the inventory!
        
        // Trigger the Ghost Town achievement
        if (target == null // There was no "target" (i.e. Villager) to create the book
        		// All of these values are impossible naturally: they are set by using the Codex.
        		&& targetProfession == -6
        		&& targetCareer == -27
        		&& targetTradeCount == -8
        		&& playerRep == -16
        		&& !player.worldObj.isRemote
        		&& (	// Definitely no population, confirmed
        				population == 0  
        				|| //OR
        		// Population can't be loaded in because this village does not exist in the villages.dat file.
        		// As far as I can tell, villages get removed from this list if they have a population of zero,
        		// which would explain the spotty village lookup behavior.
        		(playerIsInVillage && population == -1))
        		) {
        	// The if condition was inserted so that your achievement stat will cap out at 1
        	try {
        		if (!((EntityPlayerMP) player).func_147099_x().hasAchievementUnlocked(VillageNames.ghosttown)) {
        			player.triggerAchievement(VillageNames.ghosttown);
        			AchievementReward.allFiveAchievements( (EntityPlayerMP)player );
        		}
        	}
        	catch (Exception e) {}
        }
	}
	
	/**
	 * This method calls the version above, but with dummy values inserted to guarantee
	 * that there will be no second "hint" page.
	 */
	public static void codexWriteNewVillageBook (
			String bookType, String author,
			int locX, int locY, int locZ,
			String structureName, String dimensionName,
			String namePrefix, String nameRoot, String nameSuffix,
			EntityPlayer player, Village villagePlayerIsIn, boolean playerIsInVillage
			) {
		
		// Consume Codex
		if (!player.capabilities.isCreativeMode) { player.inventory.consumeInventoryItem(ModItems.codex); }
		
		targetWriteNewVillageBook (
				bookType, author,
				locX, locY, locZ,
				structureName, dimensionName,
				namePrefix, nameRoot, nameSuffix,
				playerIsInVillage, villagePlayerIsIn, null,
				-6, -27,
				-8, -16,
				player, null
				);
		
	}
	
	public static String makeSecondPage(EntityInteractEvent event, String targetClassPath, Village villageNearVillager,
			double targetX, double targetY, double targetZ, 
			int playerRep, int villagerProfession, int villagerCareer, int villagerTradeCount
			) {
		
    	double radiusCoef = 64.0f; // Feature search radius is playerRep x tradeCount x radiusCoef
    	double strongholdCoefSquared = 0.5f; // Multiplier to reduce chances of locating a stronghold
    	double nitwitCoef = 2.5f; // Multiplier
    	
    	double nitwitRadius = playerRep * radiusCoef * nitwitCoef;
    	
    	String structureFeature = "";
        double maxStructureDistance = playerRep * Math.sqrt(villagerTradeCount+1) * radiusCoef; // Maximum radius villager is allowed to report a structure
        //maxStructureDistance = Double.MAX_VALUE; // Guarantees search result
        // Page 2 will be information about nearby features
        double dx;
        double dy;
        double dz;

        // v3.2.1 - Initialize these as empty and assign values later as needed. Results in improved performance, especially with Open Terrain Generator.
    	int[] nearestMineshaftXYZ = new int[3];
    	int[] nearestStrongholdXYZ = new int[3];
    	int[] nearestTempleXYZ = new int[3];
    	int[] nearestVillageXYZ = new int[3];
    	int[] nearestMonumentXYZ = new int[3];
    	int[] nearestMansionXYZ = new int[3];
    	
    	List vlist = event.entityPlayer.worldObj.villageCollectionObj.getVillageList();
    	
    	// keys: "Professions", "IDs", "VanillaProfMaps"
    	Map<String, ArrayList> mappedProfessions = GeneralConfig.unpackMappedProfessions(GeneralConfig.modProfessionMapping);
    	
    	// Go through list of villages and pick out the closest one that's not this one.
    	double vmaxr = Double.MAX_VALUE;
    	int radius = villageNearVillager.getVillageRadius();
    	Iterator vitr = vlist.iterator();

    	double rsq = Double.MAX_VALUE;
    	double rsq2 = Double.MAX_VALUE;
    	while (vitr.hasNext()) {
    		Village element = (Village)vitr.next();
    		int vx = element.getCenter().posX;
    		int vy = element.getCenter().posY;
    		int vz = element.getCenter().posZ;
    		dx = vx - villageNearVillager.getCenter().posX;
    		dy = vy - villageNearVillager.getCenter().posY;
    		dz = vz - villageNearVillager.getCenter().posZ;
    		rsq = (dx*dx) + (dy*dy) + (dz*dz) ;
    		if ( rsq < vmaxr && rsq >= ((radius+EntityInteractHandler.villageRadiusBuffer)*(radius+EntityInteractHandler.villageRadiusBuffer)) ) {
    			vmaxr = rsq;
    			nearestVillageXYZ[0] = vx;
    			nearestVillageXYZ[1] = vy;
    			nearestVillageXYZ[2] = vz;
    		}
    	}
    	

    	// Also calculate the second-closest by manually going through the .dat file:

    	
    	// Updated in v3.2.1 to allow for Open Terrain Generation compatibility
		
		MapGenStructureData structureData;
		NBTTagCompound nbttagcompound = null;
		
		try
		{
			structureData = (MapGenStructureData)event.entityPlayer.worldObj.perWorldStorage.loadData(MapGenStructureData.class, "Village");
			nbttagcompound = structureData.func_143041_a();
		}
		catch (Exception e) // Village.dat does not exist
		{
			try
    		{
    			structureData = (MapGenStructureData)event.entityPlayer.worldObj.perWorldStorage.loadData(MapGenStructureData.class, "OTGVillage");
    			nbttagcompound = structureData.func_143041_a();
    		}
    		catch (Exception e1) {} // OTGVillage.dat does not exist
		}
		
		Iterator itr = nbttagcompound.func_150296_c().iterator();

		while (itr.hasNext()) {
			Object element = itr.next();
			
			NBTBase nbtbase = nbttagcompound.getTag(element.toString());
			
			if (nbtbase.getId() == 10) {
				NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbtbase;
			
				try {
					
					int[] boundingBox = nbttagcompound2.getIntArray("BB");
					
					int vx = villageNearVillager.getCenter().posX;
					int vy = villageNearVillager.getCenter().posY;
					int vz = villageNearVillager.getCenter().posZ;
					
					if (
							nbttagcompound2.getBoolean("Valid") && // Was not generated as a junk entry
							!( // If the center of the village this villager is in is not inside the bounding box of the village in question
							   vx >= (boundingBox[0])
							&& vy >= (boundingBox[1])
							&& vz >= (boundingBox[2])
							&& vx <= (boundingBox[3])
							&& vy <= (boundingBox[4])
							&& vz <= (boundingBox[5])
							)) {
						dx = (boundingBox[0]+boundingBox[3])/2 - vx;
			    		dy = (boundingBox[1]+boundingBox[4])/2 - vy;
			    		dz = (boundingBox[2]+boundingBox[5])/2 - vz;
			    		rsq2 = (dx*dx) + (dy*dy) + (dz*dz) ;
			    		if ( rsq2 < vmaxr ) {
			    			vmaxr = rsq2;
			    			nearestVillageXYZ[0] = (boundingBox[0]+boundingBox[3])/2;
			    			nearestVillageXYZ[1] = (boundingBox[1]+boundingBox[4])/2;
			    			nearestVillageXYZ[2] = (boundingBox[2]+boundingBox[5])/2;
			    		}
					}
				}
				catch (Exception e) {}
			}
		}

		
		// v3.2.1 - initialize these as blank and only calculate them as required.
    	double mineshaftDistSq = Double.MAX_VALUE;
        double strongholdDistSq = Double.MAX_VALUE;
        double templeDistSq = Double.MAX_VALUE;
        double villageDistSq = Double.MAX_VALUE;
        double monumentDistSq = Double.MAX_VALUE;
    	double mansionDistSq = Double.MAX_VALUE;
    	
        
		double[] villagerCoords = {event.target.posX, event.target.posY, event.target.posZ};
		double[] villageCoords = {villageNearVillager.getCenter().posX, villageNearVillager.getCenter().posY, villageNearVillager.getCenter().posZ};
		
    	String closestStructure = "";
    	int[] closestCoords = new int[3];
    	
    	// Convert a non-vanilla profession into a vanilla one for the purposes of generating a hint page
    	int villagerMappedProfession = -1; // If the below fails, do none
    	
    	try {
    		villagerMappedProfession =  
    				(Integer) ((villagerProfession >= 0 && villagerProfession <= 5)
    				? villagerProfession : ((mappedProfessions.get("VanillaProfMaps")).get( mappedProfessions.get("IDs").indexOf(villagerProfession) )));
    		}
    	catch (Exception e) {
    		if(!event.entityLiving.worldObj.isRemote) LogHelper.error("Error evaluating mod profession ID. Check your formatting!");
    		}
    	
    	// Primitive Mobs hard coding for career detection
    	if (targetClassPath.equals( ModObjects.PMTravelingMerchantClass ) )
				{villagerMappedProfession = GeneralConfig.PMMerchantProfessionMap;}
		else if (targetClassPath.equals( ModObjects.PMLostMinerClass ) )
				{villagerMappedProfession = GeneralConfig.PMLostMinerProfessionMap;}
		else if (targetClassPath.equals( ModObjects.PMSheepmanSmithClass ) )
				{villagerMappedProfession = 3;}
    	
    	// ------------------------- //
    	// Find Structures by Career //
    	// ------------------------- //
    	
        switch (villagerMappedProfession) {
	        case 0: // Villager is a Farmer
	        	
	        	// v3.2.1 - calculate distances here
	        	nearestMonumentXYZ = nearestStructureLoc("Monument", event);
	        	monumentDistSq =
		    		(nearestMonumentXYZ[0]==0 && nearestMonumentXYZ[1]==0 && nearestMonumentXYZ[2]==0) ? Double.MAX_VALUE :
		    		(nearestMonumentXYZ[0]-targetX)*(nearestMonumentXYZ[0]-targetX) + (nearestMonumentXYZ[1]-targetY)*(nearestMonumentXYZ[1]-targetY) + (nearestMonumentXYZ[2]-targetZ)*(nearestMonumentXYZ[2]-targetZ);
	        	
	        	if (villagerCareer == 2
	        		&& monumentDistSq <= maxStructureDistance*maxStructureDistance) {
	        		// Villager is a fisherman. Find an ocean monument.
        			closestStructure = "Monument";
        			closestCoords = nearestMonumentXYZ;
	        	}
	        	else {

	        		// v3.2.1 - calculate distances here
		        	villageDistSq =
		        		(nearestVillageXYZ[0]==0 && nearestVillageXYZ[1]==0 && nearestVillageXYZ[2]==0) ? Double.MAX_VALUE :
		        		(nearestVillageXYZ[0]-targetX)*(nearestVillageXYZ[0]-targetX) + (nearestVillageXYZ[1]-targetY)*(nearestVillageXYZ[1]-targetY) + (nearestVillageXYZ[2]-targetZ)*(nearestVillageXYZ[2]-targetZ);
		        	
	        		// Villager is another kind of farmer, or a fisherman didn't report a Monument.
	        		if (villageDistSq <= maxStructureDistance*maxStructureDistance) {
	        			// Village found. Write a page about it.
	        			closestStructure = "Village";
            			closestCoords = nearestVillageXYZ;
	        		}
	        	}
	        	
	        	break;
	        	
	        case 1: // Villager is a Librarian. Find a Stronghold or a Woodland Mansion.

	        	// v3.2.1 - calculate distances here
	        	nearestStrongholdXYZ = nearestStructureLoc("Stronghold", event);
	        	strongholdDistSq =
	        		(nearestStrongholdXYZ[0]==0 && nearestStrongholdXYZ[1]==0 && nearestStrongholdXYZ[2]==0) ? Double.MAX_VALUE :
	        		(nearestStrongholdXYZ[0]-targetX)*(nearestStrongholdXYZ[0]-targetX) + (nearestStrongholdXYZ[1]-targetY)*(nearestStrongholdXYZ[1]-targetY) + (nearestStrongholdXYZ[2]-targetZ)*(nearestStrongholdXYZ[2]-targetZ);
	        	
	        	// v3.2.1 - calculate distances here
	        	nearestMansionXYZ = nearestStructureLoc("Mansion", event);
	        	mansionDistSq =
	        			(nearestMansionXYZ[0]==0 && nearestMansionXYZ[1]==0 && nearestMansionXYZ[2]==0) ? Double.MAX_VALUE :
	            		(nearestMansionXYZ[0]-targetX)*(nearestMansionXYZ[0]-targetX) + (nearestMansionXYZ[1]-targetY)*(nearestMansionXYZ[1]-targetY) + (nearestMansionXYZ[2]-targetZ)*(nearestMansionXYZ[2]-targetZ);
	        	
	        	if (strongholdDistSq <= maxStructureDistance*maxStructureDistance*strongholdCoefSquared
	        		&& strongholdDistSq < mansionDistSq) {
					// Only a Stronghold has been legally detected. Report that.
					closestStructure = "Stronghold";
					closestCoords = nearestStrongholdXYZ;
	        	}
	        	else if (mansionDistSq <= maxStructureDistance*maxStructureDistance
        			&& mansionDistSq <= strongholdDistSq) {
        			// Only a Mansion has been legally detected. Report that.
        			closestStructure = "Mansion";
        			closestCoords = nearestMansionXYZ;
        		}
	        	break;
	        	
	        case 2: // Villager is a Priest. Find a temple.

	        	// v3.2.1 - calculate distances here
	        	nearestTempleXYZ = nearestStructureLoc("Temple", event);
	        	templeDistSq =
	        		(nearestTempleXYZ[0]==0 && nearestTempleXYZ[1]==0 && nearestTempleXYZ[2]==0) ? Double.MAX_VALUE :
	        		(nearestTempleXYZ[0]-targetX)*(nearestTempleXYZ[0]-targetX) + (nearestTempleXYZ[1]-targetY)*(nearestTempleXYZ[1]-targetY) + (nearestTempleXYZ[2]-targetZ)*(nearestTempleXYZ[2]-targetZ);

	        	// v3.2.1 - calculate distances here
	        	nearestMonumentXYZ = nearestStructureLoc("Monument", event);
	        	monumentDistSq =
		    		(nearestMonumentXYZ[0]==0 && nearestMonumentXYZ[1]==0 && nearestMonumentXYZ[2]==0) ? Double.MAX_VALUE :
		    		(nearestMonumentXYZ[0]-targetX)*(nearestMonumentXYZ[0]-targetX) + (nearestMonumentXYZ[1]-targetY)*(nearestMonumentXYZ[1]-targetY) + (nearestMonumentXYZ[2]-targetZ)*(nearestMonumentXYZ[2]-targetZ);
	        	
	        	if (villagerCareer == 0 && !GeneralConfig.villagerCareers // If the priest's career is 0--only plausible in 1.7.10--then perhaps find a Monument instead
	        		&& monumentDistSq <= maxStructureDistance*maxStructureDistance
    				&& monumentDistSq <= templeDistSq) {
        			// Only a Monument has been legally detected. Report that.
        			closestStructure = "Monument";
        			closestCoords = nearestMonumentXYZ;
        		}
        		else if (templeDistSq <= maxStructureDistance*maxStructureDistance
            			&& templeDistSq < monumentDistSq) {
        			// Only a Temple has been legally detected. Report that.
        			closestStructure = "Temple";
        			closestCoords = nearestTempleXYZ;
        		}
	        	break;
	        	
	        case 3: // Villager is a Blacksmith. Find a mineshaft.

	        	// v3.2.1 - calculate distances here
	        	nearestMineshaftXYZ = nearestStructureLoc("Mineshaft", event);
	        	mineshaftDistSq = 
	    			(nearestMineshaftXYZ[0]==0 && nearestMineshaftXYZ[1]==0 && nearestMineshaftXYZ[2]==0) ? Double.MAX_VALUE :
	    			(nearestMineshaftXYZ[0]-targetX)*(nearestMineshaftXYZ[0]-targetX) + (nearestMineshaftXYZ[1]-targetY)*(nearestMineshaftXYZ[1]-targetY) + (nearestMineshaftXYZ[2]-targetZ)*(nearestMineshaftXYZ[2]-targetZ);
	        	
	        	if (mineshaftDistSq <= maxStructureDistance*maxStructureDistance) {
        			// Mineshaft found. Write a page about it.
        			closestStructure = "Mineshaft";
        			closestCoords = nearestMineshaftXYZ;
        		}
	        	break;
	        	
	        case 4: // Villager is a Butcher. Find a temple or a village.

	        	// v3.2.1 - calculate distances here
	        	nearestTempleXYZ = nearestStructureLoc("Temple", event);
	        	templeDistSq =
	        		(nearestTempleXYZ[0]==0 && nearestTempleXYZ[1]==0 && nearestTempleXYZ[2]==0) ? Double.MAX_VALUE :
	        		(nearestTempleXYZ[0]-targetX)*(nearestTempleXYZ[0]-targetX) + (nearestTempleXYZ[1]-targetY)*(nearestTempleXYZ[1]-targetY) + (nearestTempleXYZ[2]-targetZ)*(nearestTempleXYZ[2]-targetZ);
	        	
	        	// v3.2.1 - calculate distances here
	        	villageDistSq =
	        		(nearestVillageXYZ[0]==0 && nearestVillageXYZ[1]==0 && nearestVillageXYZ[2]==0) ? Double.MAX_VALUE :
	        		(nearestVillageXYZ[0]-targetX)*(nearestVillageXYZ[0]-targetX) + (nearestVillageXYZ[1]-targetY)*(nearestVillageXYZ[1]-targetY) + (nearestVillageXYZ[2]-targetZ)*(nearestVillageXYZ[2]-targetZ);
	        	
	        	if (villageDistSq <= maxStructureDistance*maxStructureDistance
    			&& villageDistSq < templeDistSq) {
        			// Only a Village has been legally detected. Report that.
        			closestStructure = "Village";
        			closestCoords = nearestVillageXYZ;
        		}
        		else if (templeDistSq <= maxStructureDistance*maxStructureDistance
            			&& templeDistSq <= villageDistSq) {
        			// Only a Temple has been legally detected. Report that.
        			closestStructure = "Temple";
        			closestCoords = nearestTempleXYZ;
        		}
	        	break;
	        	
	        case 5: // Villager is a Nitwit
	        	// No structure planned at this time
	        	//nitwitRadius = Double.MAX_VALUE; // Guarantees search result
	        	double nitwitMax = Double.MAX_VALUE;

	        	// v3.2.1 - calculate distances here
	        	nearestStrongholdXYZ = nearestStructureLoc("Stronghold", event);
	        	strongholdDistSq =
	        		(nearestStrongholdXYZ[0]==0 && nearestStrongholdXYZ[1]==0 && nearestStrongholdXYZ[2]==0) ? Double.MAX_VALUE :
	        		(nearestStrongholdXYZ[0]-targetX)*(nearestStrongholdXYZ[0]-targetX) + (nearestStrongholdXYZ[1]-targetY)*(nearestStrongholdXYZ[1]-targetY) + (nearestStrongholdXYZ[2]-targetZ)*(nearestStrongholdXYZ[2]-targetZ);
		        
	        	// Check Stronghold distance first because of the extra coefficient
        		if (strongholdDistSq <= nitwitRadius*nitwitRadius*strongholdCoefSquared) {
        			// Stronghold may be the closest structure.
        			nitwitMax = strongholdDistSq;
        			closestStructure = "Stronghold";
        			closestCoords = nearestStrongholdXYZ;
        		}
        		
	    		// v3.2.1 - calculate distances here
	        	villageDistSq =
	        		(nearestVillageXYZ[0]==0 && nearestVillageXYZ[1]==0 && nearestVillageXYZ[2]==0) ? Double.MAX_VALUE :
	        		(nearestVillageXYZ[0]-targetX)*(nearestVillageXYZ[0]-targetX) + (nearestVillageXYZ[1]-targetY)*(nearestVillageXYZ[1]-targetY) + (nearestVillageXYZ[2]-targetZ)*(nearestVillageXYZ[2]-targetZ);
	        	
        		if (villageDistSq <= nitwitRadius*nitwitRadius 
        				&& villageDistSq <= nitwitMax) {
        			// Village may be the closest structure.
        			nitwitMax = villageDistSq;
        			closestStructure = "Village";
        			closestCoords = nearestVillageXYZ;
        		}

	    		// v3.2.1 - calculate distances here
	    		nearestMineshaftXYZ = nearestStructureLoc("Mineshaft", event);
	        	mineshaftDistSq = 
	    			(nearestMineshaftXYZ[0]==0 && nearestMineshaftXYZ[1]==0 && nearestMineshaftXYZ[2]==0) ? Double.MAX_VALUE :
	    			(nearestMineshaftXYZ[0]-targetX)*(nearestMineshaftXYZ[0]-targetX) + (nearestMineshaftXYZ[1]-targetY)*(nearestMineshaftXYZ[1]-targetY) + (nearestMineshaftXYZ[2]-targetZ)*(nearestMineshaftXYZ[2]-targetZ);
	        	
        		if (mineshaftDistSq <= nitwitRadius*nitwitRadius 
        				&& mineshaftDistSq <= nitwitMax ) {
        			// Mineshaft may be the closest structure.
        			nitwitMax = mineshaftDistSq;
        			closestStructure = "Mineshaft";
        			closestCoords = nearestMineshaftXYZ;
        		}

	    		// v3.2.1 - calculate distances here
	    		nearestTempleXYZ = nearestStructureLoc("Temple", event);
	        	templeDistSq =
	        		(nearestTempleXYZ[0]==0 && nearestTempleXYZ[1]==0 && nearestTempleXYZ[2]==0) ? Double.MAX_VALUE :
	        		(nearestTempleXYZ[0]-targetX)*(nearestTempleXYZ[0]-targetX) + (nearestTempleXYZ[1]-targetY)*(nearestTempleXYZ[1]-targetY) + (nearestTempleXYZ[2]-targetZ)*(nearestTempleXYZ[2]-targetZ);
	        	
        		if (templeDistSq  <= nitwitRadius*nitwitRadius 
        				&& templeDistSq <= nitwitMax ) {
        			// Temple may be the closest structure.
        			nitwitMax = templeDistSq;
        			closestStructure = "Temple";
        			closestCoords = nearestTempleXYZ;
        		}

	        	// v3.2.1 - calculate distances here
	    		nearestMonumentXYZ = nearestStructureLoc("Monument", event);
	        	monumentDistSq =
		    		(nearestMonumentXYZ[0]==0 && nearestMonumentXYZ[1]==0 && nearestMonumentXYZ[2]==0) ? Double.MAX_VALUE :
		    		(nearestMonumentXYZ[0]-targetX)*(nearestMonumentXYZ[0]-targetX) + (nearestMonumentXYZ[1]-targetY)*(nearestMonumentXYZ[1]-targetY) + (nearestMonumentXYZ[2]-targetZ)*(nearestMonumentXYZ[2]-targetZ);
	        	
        		if (monumentDistSq  <= nitwitRadius*nitwitRadius 
        				&& monumentDistSq <= nitwitMax ) {
        			// Monument may be the closest structure.
        			nitwitMax = monumentDistSq;
        			closestStructure = "Monument";
        			closestCoords = nearestMonumentXYZ;
        		}

	        	// v3.2.1 - calculate distances here
	    		nearestMansionXYZ = nearestStructureLoc("Mansion", event);
	        	mansionDistSq =
	        			(nearestMansionXYZ[0]==0 && nearestMansionXYZ[1]==0 && nearestMansionXYZ[2]==0) ? Double.MAX_VALUE :
	            		(nearestMansionXYZ[0]-targetX)*(nearestMansionXYZ[0]-targetX) + (nearestMansionXYZ[1]-targetY)*(nearestMansionXYZ[1]-targetY) + (nearestMansionXYZ[2]-targetZ)*(nearestMansionXYZ[2]-targetZ);
	        	
        		if (mansionDistSq  <= nitwitRadius*nitwitRadius 
        				&& mansionDistSq <= nitwitMax ) {
        			// Mansion may be the closest structure.
        			nitwitMax = mansionDistSq;
        			closestStructure = "Mansion";
        			closestCoords = nearestMansionXYZ;
        		}
	        	break;
        }
                
        if (!closestStructure.equals("")) {
        	String structureHintPageText = "\n\n" + writeStructureHintPage(closestStructure, closestCoords, villagerProfession, villageCoords, radius, event );
            
            //pagesTag.appendTag(new NBTTagString(structureHintPageText));
        	return structureHintPageText;
        }
        else {
        	return "";
        }
	}
	
	
	/**
	 * Used to find the nearest structure to the target entity by structure name. Returns [0,0,0] if none found.
	 * You can also enter an X and Z position offset for the search.
	 */
	private static int[] nearestStructureLoc(String structureName, EntityInteractEvent event, double xOffset, double zOffset, boolean includeThis) {
        
		int[] structurePos = new int[]{0,0,0};
		
		Map<String, ChunkPosition> nearbyStructures = StructureRegistry.instance.getNearestStructures((WorldServer)event.entityPlayer.worldObj, (int)(event.target.posX + xOffset), (int)event.target.posY, (int)(event.target.posZ + zOffset) );
		
		double max = Double.MAX_VALUE;
		
		int CX = 0;
		//int CY = 0;
		int CZ = 0;
		int radius = 0;
		

		for (Map.Entry<String, ChunkPosition> e : nearbyStructures.entrySet()) {
			ChunkPosition pos = e.getValue();
			
			// First things first. If 
			
			double dx = pos.chunkPosX - event.target.posX;
			double dy = pos.chunkPosY - event.target.posY;
			double dz = pos.chunkPosZ - event.target.posZ;

			double distsq = (dx * dx) + (dy * dy) + (dz * dz);
			
			// Locate nearest component of specified type to specified position
			if (distsq < max && e.getKey().equals(structureName)) {
				// Return the nearest structure of interest
				max = distsq;
				structurePos[0] = pos.chunkPosX;
				structurePos[1] = pos.chunkPosY;
				structurePos[2] = pos.chunkPosZ;
			}
			
			
		}
		return structurePos;
	}
	
	/**
	 * Used to find the nearest structure to the target entity by structure name. Returns [0,0,0] if none found.
	 */
	public static int[] nearestStructureLoc(String structureName, EntityInteractEvent event){
		return nearestStructureLoc(structureName, event, 0.0f, 0.0f, false);
	}
	
	/**
	 * This method generates the additional page about a nearby structure
	 */
	private static String writeStructureHintPage(String nearbyStructure, int[] structureCoords, int villagerProfession, double[] villageCoords, int villageRadius, EntityInteractEvent event ) {
		String structureHintPage = "";
		Random random = event.entity.worldObj.rand;
		
		// Determine the cardinal direction from the coordinates
		double dx = structureCoords[0] - villageCoords[0];
		double dy = structureCoords[1] - villageCoords[1];
		double dz = structureCoords[2] - villageCoords[2];
		// Distances across surface (2D) or absolute (3d)
		double featureDistance2D = Math.sqrt( (dx*dx)+(dz*dz) );
		double featureDistance3D = Math.sqrt( (dx*dx)+(dy*dy)+(dz*dz) );
		
		double thetaPolar;
		thetaPolar = Math.atan2(-dz, dx);
		
		//Convert angle into a cardinal direction
		String directionString;
		double directionDegrees = (thetaPolar*180/Math.PI);///11.25;
		while (directionDegrees<0) {
			directionDegrees += 360d;
		}
		// Flag for whether structure is under village
		boolean isInVillageBounds=false;
		if ( (dx*dx)+(dz*dz) <= villageRadius*villageRadius ) {
			isInVillageBounds=true;
		}
		
		boolean isBelowGround=false;
		if (nearbyStructure.equals("Mineshaft") || nearbyStructure.equals("Stronghold")){
			isBelowGround=true;
		}
		
		if (directionDegrees <= 11.25){
			directionString = "east";
		}
		else if (directionDegrees <= 33.75){
			directionString = "east-northeast";
		}
		else if (directionDegrees <= 56.25){
			directionString = "northeast";
		}
		else if (directionDegrees <= 78.75){
			directionString = "north-northeast";
		}
		else if (directionDegrees <= 101.25){
			directionString = "north";
		}
		else if (directionDegrees <= 123.75){
			directionString = "north-northwest";
		}
		else if (directionDegrees <= 146.25){
			directionString = "northwest";
		}
		else if (directionDegrees <= 168.75){
			directionString = "west-northwest";
		}
		else if (directionDegrees <= 191.25){
			directionString = "west";
		}
		else if (directionDegrees <= 213.75){
			directionString = "west-southwest";
		}
		else if (directionDegrees <= 236.25){
			directionString = "southwest";
		}
		else if (directionDegrees <= 258.75){
			directionString = "south-southwest";
		}
		else if (directionDegrees <= 281.25){
			directionString = "south";
		}
		else if (directionDegrees <= 303.75){
			directionString = "south-southeast";
		}
		else if (directionDegrees <= 326.25){
			directionString = "southeast";
		}
		else if (directionDegrees <= 348.75){
			directionString = "east-southeast";
		}
		else {
			directionString = "east";
		}
		
		/*
		 * Here's where we'll name the structure.
		 */
		String structureName;
		String[] structureNameArray = tryGetStructureName(nearbyStructure, structureCoords, event);
		
		if (structureNameArray[0]==null && structureNameArray[1]==null && structureNameArray[2]==null) {
			//Structure has no name. Generate it here.
			//VNWorldData data=null;
			
			VNWorldDataStructure data = VNWorldDataStructure.forWorld(event.entity.worldObj, "villagenames3_"+nearbyStructure, "NamedStructures");

    		int signX = structureCoords[0];
    		int signY = structureCoords[1];
    		int signZ = structureCoords[2];
    		
			Random deterministic = new Random(); deterministic.setSeed(event.entity.worldObj.getSeed() + FunctionsVN.getUniqueLongForXYZ(signX, signY, signZ));
			structureNameArray = NameGenerator.newRandomName(nearbyStructure, deterministic);
			
			// Gotta copy this thing to each IF condition I think
			String headerTags = structureNameArray[0];
    		String namePrefix = structureNameArray[1];
    		String nameRoot = structureNameArray[2];
    		String nameSuffix = structureNameArray[3];

    		// Added in v3.1banner
    		// Generate banner info specifically to obtain a village color
    		Object[] newRandomBanner = BannerGenerator.randomBannerArrays(deterministic, -1, -1);
			ArrayList<String> patternArray = (ArrayList<String>) newRandomBanner[0];
			ArrayList<Integer> colorArray = (ArrayList<Integer>) newRandomBanner[1];
    		int townColorMeta = 15-colorArray.get(0);
    		int townColorMeta2 = colorArray.size()==1 ? townColorMeta : 15-colorArray.get(1); 
    		
    		// Make the data bundle to save to NBT
    		NBTTagList nbttaglist = new NBTTagList();
    		
    		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound1.setInteger("signX", signX);
            nbttagcompound1.setInteger("signY", signY);
            nbttagcompound1.setInteger("signZ", signZ);
            nbttagcompound1.setInteger("townColor", townColorMeta); //In case we want to make clay, carpet, wool, glass, etc
            nbttagcompound1.setInteger("townColor2", townColorMeta2);
            nbttagcompound1.setString("namePrefix", namePrefix);
            nbttagcompound1.setString("nameRoot", nameRoot);
            nbttagcompound1.setString("nameSuffix", nameSuffix);
            nbttagcompound1.setBoolean("fromEntity", true);
            
            nbttaglist.appendTag(nbttagcompound1);
    		
    		// .getTagList() will return all the entries under the specific village name.
    		NBTTagCompound tagCompound = data.getData();
    		
            data.getData().setTag((namePrefix + " " + nameRoot + " " + nameSuffix).trim() + ", x" + signX + " y" + signY + " z" + signZ, nbttaglist);
            data.markDirty();
			
			structureName = structureNameArray[1]+" "+structureNameArray[2]+" "+structureNameArray[3];
			structureName = structureName.trim();
			
		}
		else {
			//Structure has a name. Unpack it here.
			structureName = structureNameArray[0]+" "+structureNameArray[1]+" "+structureNameArray[2];
			structureName = structureName.trim();
		}
		
		
		
		int approxDist2D = (int)Math.round(featureDistance2D/100)*100; //Approximates the distance to the structure
		
		String structureString="";
		
		if (nearbyStructure.equals("Village")) {
			String[] structureStringArray = new String[]{
					"We trade with " + structureName + ", ",
					"The villagers of this town trade with " + structureName + ", ",
					"Our trading partner, " + structureName + ", is ",
					"There's another settlement named " + structureName + " that we trade with, ",
					"The village of " + structureName + " is ",
					"A village named " + structureName + " is ",
					"There is a village, " + structureName + ", "
				};
			structureString = structureStringArray[random.nextInt(structureStringArray.length)];
		}
		else if (nearbyStructure.equals("Stronghold")) {
			String[] structureStringArray = new String[]{
					"We have records of a stronghold, " + structureName + ", ",
					"An underground fortress, " + structureName + ", is ",
					"Our records list a stronghold, " + structureName + ", ",
					"A mysterious labyrinth, " + structureName + ", is "
				};
			structureString = structureStringArray[random.nextInt(structureStringArray.length)];
		}
		else if (nearbyStructure.equals("Temple")) {
			String[] structureStringArray = new String[]{
					"An abandoned temple called " + structureName + " is ",
					"A former religious ritual site, " + structureName + ", is ",
					"Some bygone religious sect constructed a temple at the " + structureName + " site. It's ",
					"A previous civilization built the temple of " + structureName + ", "
				};
			structureString = structureStringArray[random.nextInt(structureStringArray.length)];
		}
		else if (nearbyStructure.equals("Mineshaft")) {
			String[] structureStringArray = new String[]{
					"An underground mining site, " + structureName + ", is ",
					"Previous settlers built an underground mining site, " + structureName + ", ",
					"There's a long-deserted mine at the old " + structureName + " site ",
					"Back at the old " + structureName + " site, there should be an abandoned mineshaft. It's ",
					"An abandoned mining structure known as " + structureName + " is "
				};
			structureString = structureStringArray[random.nextInt(structureStringArray.length)];
		}
		else if (nearbyStructure.equals("Monument")) {
			String[] structureStringArray = new String[]{
					"Rumors on the wind speak of a sunken temple, " + structureName + ", ",
					"There are rumors of " + structureName + ", under the sea, ",
					"The monument of " + structureName + "was said to have been dragged into the sea long ago. It's rumored to be ",
					"An old fisherman's tale mentions " + structureName + ", "
				};
			structureString = structureStringArray[random.nextInt(structureStringArray.length)];
		}
		else if (nearbyStructure.equals("Mansion")) {
			String[] structureStringArray = new String[]{
					"We have records of a cult mansion, " + structureName + ", ",
					"We have records of a cult operating at " + structureName + ", ",
					"Our records list a cult mansion, " + structureName + ", ",
					"A mysterious cult meets at a mansion called " + structureName + ", "
				};
			structureString = structureStringArray[random.nextInt(structureStringArray.length)];
		}
		else {
			//There's nothing left, man
		}
		
		if (villagerProfession==5) {
			// Special text for nitwit
			
			if (nearbyStructure.equals("Mineshaft")) {
				String[] structureStringArray = new String[]{
						"I hear there used to be a mine called " + structureName + ", ",
						"There used to be a mine over at the old " + structureName + " site, ",
						"If the wind is still over at the " + structureName + 
							" site, you can hear faint sounds coming from deep underground. You can go check it out if you want: it's "
					};
				structureString = structureStringArray[random.nextInt(structureStringArray.length)];
			}
			else if (nearbyStructure.equals("Stronghold")) {
				String[] structureStringArray = new String[]{
						"I've heard rumors about " + structureName + ", an underground stronghold ",
						"I get really weird supernatural sensations over at the old " + structureName + 
							" site. If you want to dig around under there, it's "
					};
				structureString = structureStringArray[random.nextInt(structureStringArray.length)];
			}
			else if (nearbyStructure.equals("Temple")) {
				String[] structureStringArray = new String[]{
						"In my wanderings I've stumbled upon an abandoned site called "	+ structureName + ", ",
						"I hear there's a temple or something like that over at the "	+ structureName + " religious site, "
					};
				structureString = structureStringArray[random.nextInt(structureStringArray.length)];
			}
			else if (nearbyStructure.equals("Village")) {
				String[] structureStringArray = new String[]{
						"There is another village named " + structureName + ", ",
						"Everyone knows about the town of " + structureName + ", ",
						"My buddy once lost a shoe while visiting " + structureName + ", "
					};
				structureString = structureStringArray[random.nextInt(structureStringArray.length)];
			}
			else if (nearbyStructure.equals("Monument")) {
				String[] structureStringArray = new String[]{
						"I've heard fishermen mention a sunken monument called " + structureName + ", ",
						"The darkest sea tales, whispered in hushed tones, mention " + structureName + 
							", a sunken temple filled with treasure. If you dare to look for it, rumor says it's "
					};
				structureString = structureStringArray[random.nextInt(structureStringArray.length)];
			}
			else if (nearbyStructure.equals("Mansion")) {
				String[] structureStringArray = new String[]{
						"I've heard a lot of very bad things about the cult that practices in "	+ structureName + ", ",
						"Others don't like to talk about it, but everyone here knows about the eerie cult that gathers at "
								+ structureName + ", deep in the dark wood. If you're foolish enough to look, it's "
					};
				structureString = structureStringArray[random.nextInt(structureStringArray.length)];
			}
		}
		
		structureHintPage += "" + structureString;
		
		
		
		/*
		 * Location of the feature
		 */
		
		String[] thisVillageArray = new String[]{
				"this village",
				"this very village"
			};
		String thisVillage = thisVillageArray[random.nextInt(thisVillageArray.length)];
		
		if (isInVillageBounds && isBelowGround){
			// Structure is under this very village.
			structureHintPage += "located underneath "+thisVillage+". ";
		}
		else if (isInVillageBounds && !isBelowGround) {
			// It's in this village and should be visible.
			structureHintPage += "located within "+thisVillage+". ";
		}
		else {
			// Is outside of village.
			
			if (approxDist2D>=100){
				
				String[] approxStringArray = new String[]{
						"approximately",
						"roughly",
						"about"
					};
				String approxString = approxStringArray[random.nextInt(approxStringArray.length)];
				
				structureHintPage += "located "+approxString;
				structureHintPage += " "+approxDist2D+" meters ";
			}
			else {
				String[] approxStringArray = new String[]{
						"less than",
						"under",
						"short of"
					};
				String approxString = approxStringArray[random.nextInt(approxStringArray.length)];
				
				structureHintPage += "located "+approxString;
				structureHintPage += " 100 meters ";
			}
			String[] wordArray = new String[]{
					"due",
					"to the"
				};
			String randomWord = wordArray[random.nextInt(wordArray.length)];
			structureHintPage += randomWord + " " + directionString + ". ";
			
		}
		return structureHintPage;
	}
	
	/**
	 * This method searches the feature you're interested in to see if a name already exists for it
	 * @return {namePrefix, nameRoot, nameSuffix} if something is found; {null, null, null} otherwise
	 */
	private static String[] tryGetStructureName(String nearbyStructure, int[] structureCoords, EntityInteractEvent event) {
		
		// Load in names data
		VNWorldData data= VNWorldDataStructure.forWorld(event.entity.worldObj, "villagenames3_"+nearbyStructure, "NamedStructures" );
		
		// .getTagList() will return all the entries under the specific village name.
		NBTTagCompound tagCompound = data.getData();
		
		Set tagmapKeyset = tagCompound.func_150296_c(); //Gets the town key list: "x535y80z39"
		
        Iterator itr = tagmapKeyset.iterator();
        String townSignEntry;
        
        String namePrefix=null;
        String nameRoot=null;
        String nameSuffix=null;
        while(itr.hasNext()) {
            Object element = itr.next();
            
            townSignEntry = element.toString(); //Text name of village header (e.g. "x535y80z39")
            //The only index that has data is 0:
            NBTTagCompound tagList = tagCompound.getTagList(townSignEntry, tagCompound.getId()).getCompoundTagAt(0);
            
            // Town's location
            int featureX = tagList.getInteger("signX");
            int featureY = tagList.getInteger("signY");
            int featureZ = tagList.getInteger("signZ");
            
            double sdx = featureX - structureCoords[0];
            double sdy = (featureY==64) ? 0.d : featureY - structureCoords[1]; // A signY of 64 likely means it was detected pre-generation.
            double sdz = featureZ - structureCoords[2];
            
            if ( (sdx*sdx)+(sdy*sdy)+(sdz*sdz) <= 100*100 ) {
            	//This entry has been correctly matched to the structure in question
                namePrefix = tagList.getString("namePrefix"); 
                nameRoot = tagList.getString("nameRoot"); 
                nameSuffix = tagList.getString("nameSuffix"); 
            }
            
        }
		
		return new String[] {namePrefix, nameRoot, nameSuffix};
	}
	
    /**
     * This method searches the feature you're interested in to see if a name already exists for it
     * @return {namePrefix, nameRoot, nameSuffix} if something is found; {null, null, null} otherwise
     */
    public static String[] tryGetStructureInfo(String structureType, int[] structureBB, World world) {
    	
    	// Load in names data
    	VNWorldData data=null;
    	
    	data = VNWorldDataStructure.forWorld(world, "villagenames3_"+structureType, "NamedStructures");
    	
    	// .getTagList() will return all the entries under the specific village name.
    	NBTTagCompound tagCompound = data.getData();
    	
    	Set tagmapKeyset = tagCompound.func_150296_c(); //Gets the town key list: "coordinates"
    	
    	Iterator itr = tagmapKeyset.iterator();
    	String featureSignLoc;
    	
    	String namePrefix=null;
    	String nameRoot=null;
    	String nameSuffix=null;
    	String signX=null;
    	String signY=null;
    	String signZ=null;
    	
    	while(itr.hasNext()) {
    		Object element = itr.next();
    		
    		featureSignLoc = element.toString(); //Text name of feature header (e.g. "x535y80z39")
    		//The only index that has data is 0:
    		NBTTagCompound tagList = tagCompound.getTagList(featureSignLoc, tagCompound.getId()).getCompoundTagAt(0);
    		
    		int[] structureCoords = new int[] {tagList.getInteger("signX"), tagList.getInteger("signY"), tagList.getInteger("signZ")};
    		
    		// A signY of 64 likely means it was detected pre-generation.
    		// The below code detects if you're in the 3D bounding box if and only if signY is not 64.
    		// If signY is 64, the code detects if you're in the 2D (x vs z) bounding box.
    		
    		if (
    			    structureCoords[0] >= structureBB[0]
				 && structureCoords[2] >= structureBB[2]
				 && structureCoords[0] <= structureBB[3]
				 && structureCoords[2] <= structureBB[5]
    			 && ( structureCoords[1] == 64  ||  (structureCoords[1] >= structureBB[1] && structureCoords[1] <= structureBB[4] ) )
				) {
    			//This entry has been correctly matched to the structure in question
    			namePrefix = tagList.getString("namePrefix"); 
    			nameRoot = tagList.getString("nameRoot"); 
    			nameSuffix = tagList.getString("nameSuffix"); 
    			signX = ""+structureCoords[0];
    			signY = ""+structureCoords[1];
    			signZ = ""+structureCoords[2];
    		}
    		
    	}
    	
    	return new String[] {namePrefix, nameRoot, nameSuffix, signX, signY, signZ};
    }
	
}
