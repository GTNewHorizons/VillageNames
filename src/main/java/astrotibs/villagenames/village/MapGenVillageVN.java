package astrotibs.villagenames.village;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.config.village.VillageGeneratorConfigHandler;
import astrotibs.villagenames.utility.FunctionsVN;
import astrotibs.villagenames.utility.LogHelper;
import astrotibs.villagenames.utility.Reference;
import astrotibs.villagenames.village.biomestructures.DesertStructures;
import astrotibs.villagenames.village.biomestructures.JungleStructures;
import astrotibs.villagenames.village.biomestructures.PlainsStructures;
import astrotibs.villagenames.village.biomestructures.SavannaStructures;
import astrotibs.villagenames.village.biomestructures.SnowyStructures;
import astrotibs.villagenames.village.biomestructures.SwampStructures;
import astrotibs.villagenames.village.biomestructures.TaigaStructures;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Road;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;

public class MapGenVillageVN extends MapGenVillage
{
	/**
	 * Adapted from RTG 
	 * https://github.com/Team-RTG/Realistic-Terrain-Generation/blob/1.7.10-master/src/main/java/rtg/event/EventManagerRTG.java
	 */
	@SubscribeEvent(priority = EventPriority.LOW)
    public void onInitMapGen(InitMapGenEvent event)
	{
		if (event.type == EventType.VILLAGE && VillageGeneratorConfigHandler.newVillageGenerator)
        {
			// Do a try/catch because in case the Overworld has not yet loaded
	        try 
	        {
	            if (MinecraftServer.getServer().worldServerForDimension(0).getWorldInfo().getTerrainType()==null) {return;}
	        }
	        catch (Exception e) {}
	        
        	event.newGen = new MapGenVillageVN();
        }
	}
	
    private float villageSize; // Actually village "size" integer
    private int field_82665_g; // Maximum distance between villages
    private int field_82666_h; // Minimum distance between villages
    
    public MapGenVillageVN()
    {
    	this.villageSize = 0; // Vanilla is "0" - will be changed on the fly at getStructureStart
    	
    	// Set spacings
    	this.field_82666_h = VillageGeneratorConfigHandler.newVillageSpacingMedian - VillageGeneratorConfigHandler.newVillageSpacingSpread;
    	if (this.field_82666_h<1) {this.field_82666_h=1;}
    	
        this.field_82665_g = VillageGeneratorConfigHandler.newVillageSpacingMedian + VillageGeneratorConfigHandler.newVillageSpacingSpread;
        
    }
    
    // Same as vanilla
    public MapGenVillageVN(Map par1Map)
    {
    	this();
    	Iterator iterator = par1Map.entrySet().iterator();
    	
        while (iterator.hasNext())
        {
            Entry entry = (Entry)iterator.next();

            if (((String)entry.getKey()).equals("size"))
            {
                this.villageSize = (float) MathHelper.parseDoubleWithDefaultAndMax((String)entry.getValue(), this.villageSize, 0);
            }
            else if (((String)entry.getKey()).equals("distance"))
            {
                this.field_82665_g = MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.field_82665_g, this.field_82666_h + 1);
            }
        }
    }
    
    @Override
    public String func_143025_a()
    {
        return "Village";
    }
    
    @Override
    protected boolean canSpawnStructureAtCoords(int chunkXin, int chunkZin)
    {
    	// Deny villages less than a config-specified distance away from spawn
    	int noVillagesRadius = VillageGeneratorConfigHandler.noVillagesRadius;
    	if ((chunkXin*chunkXin) + (chunkZin*chunkXin) < (noVillagesRadius*noVillagesRadius)) {return false;}
    	
        int chunkX = chunkXin;
        int chunkZ = chunkZin;
        
        // Handle negative chunk values
        if (chunkXin < 0) {chunkXin -= this.field_82665_g - 1;}
        if (chunkZin < 0) {chunkZin -= this.field_82665_g - 1;}
        
        // The (floor) number of [max Village chunk distance]s this chunk is
        int chunkXModulated = chunkXin / this.field_82665_g;
        int chunkZModulated = chunkZin / this.field_82665_g;
        
        // Set the random seed based on number of X, Z spacings
        Random random = this.worldObj.setRandomSeed(chunkXModulated, chunkZModulated, 10387312); // Idk the significance of this number. May be unique to "Village" structures?
        
        // Get the chunk X and Z, floored by the number of max village spacings
        chunkXModulated *= this.field_82665_g;
        chunkZModulated *= this.field_82665_g;
        
        // Add random offset based on village spacing min and max values
        chunkXModulated += random.nextInt(this.field_82665_g - this.field_82666_h);
        chunkZModulated += random.nextInt(this.field_82665_g - this.field_82666_h);
        
        // Return "true" if this chunk X & Z is flagged for village construction AND the biome is allowed as per the config
        if (chunkX == chunkXModulated && chunkZ == chunkZModulated)
        {
        	BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(chunkX * 16 + 8, chunkZ * 16 + 8); // No need for bit shift here. Villages rarely spawn.
        	
        	if (VillageGeneratorConfigHandler.spawnBiomesNames != null) // Biome list is not empty
    		{
        		Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
        		
    			for (int i = 0; i < mappedBiomes.get("BiomeNames").size(); i++)
    			{
    				if (mappedBiomes.get("BiomeNames").get(i).equals(biome.biomeName))
    				{
    					BiomeManager.addVillageBiome(biome, true); // Set biome to be able to spawn villages
    					
    					return true;
    				}
    			}
    		}
        }
        return false;
    }
    
    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
    	// Get a random size for the village
    	int order = VillageGeneratorConfigHandler.newVillageSizeNormalOrder;
    	float randomizedVillageSize;
    	
    	do
    	{
    		// Obtain a random variate between 0 and 1, with a distribution based on the normal order value specified
    		randomizedVillageSize=0; for (int n=0; n<VillageGeneratorConfigHandler.newVillageSizeNormalOrder; n++) {randomizedVillageSize+=this.rand.nextFloat();} randomizedVillageSize/=VillageGeneratorConfigHandler.newVillageSizeNormalOrder;
        	// Scale and offset the variate based on the maximum, minimum, and mode values wanted
    		double halfrange = MathHelper.abs_max(VillageGeneratorConfigHandler.newVillageSizeMaximum-VillageGeneratorConfigHandler.newVillageSizeMode, VillageGeneratorConfigHandler.newVillageSizeMode-VillageGeneratorConfigHandler.newVillageSizeMinimum);
    		randomizedVillageSize *= (2*halfrange);
        	randomizedVillageSize += (VillageGeneratorConfigHandler.newVillageSizeMode - halfrange);
    	}
    	// Keep drawing until the value is within the range specified
    	while (randomizedVillageSize>VillageGeneratorConfigHandler.newVillageSizeMaximum || randomizedVillageSize<VillageGeneratorConfigHandler.newVillageSizeMinimum);
    	
    	this.villageSize = randomizedVillageSize-1F;
    	
    	// Return the village starter
        return new MapGenVillageVN.Start(this.worldObj, this.rand, chunkX, chunkZ, this.villageSize);
    }
    
    // Copied from vanilla
    public static class Start extends StructureStart
    {
        /** well ... thats what it does */
        private boolean hasMoreThanTwoComponents;
        
        public Start() {}

        public Start(World world, Random random, int chunkX, int chunkZ, float villageSize)
        {
            super(chunkX, chunkZ);
            
            // Choose starter type based on biome
            int posX = (chunkX << 4) + 2;
            int posZ = (chunkZ << 4) + 2;
            WorldChunkManager chunkManager = world.getWorldChunkManager();
            BiomeGenBase biome = chunkManager.getBiomeGenAt(posX, posZ);
			Map<String, ArrayList<String>> mappedBiomes = VillageGeneratorConfigHandler.unpackBiomes(VillageGeneratorConfigHandler.spawnBiomesNames);
			FunctionsVN.VillageType startVillageType;
			
			// Attempt to swap it with the config value
			try {
            	String mappedVillageType = (String) (mappedBiomes.get("VillageTypes")).get(mappedBiomes.get("BiomeNames").indexOf(biome.biomeName));
            	if (mappedVillageType.equals("")) {startVillageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, posX, posZ);}
            	else {startVillageType = FunctionsVN.VillageType.getVillageTypeFromName(mappedVillageType, FunctionsVN.VillageType.PLAINS);}
            	}
			catch (Exception e) {startVillageType = FunctionsVN.VillageType.getVillageTypeFromBiome(chunkManager, posX, posZ);}
			
            
			
            // My modified version, which allows the user to disable each building
            List list = StructureVillageVN.getStructureVillageWeightedPieceList(random, villageSize, startVillageType);
            
            // Print out the list of components for player use
            if (GeneralConfig.debugMessages)
            {
            	Map<String, ArrayList> mappedComponentVillageTypes = VillageGeneratorConfigHandler.unpackComponentVillageTypes(VillageGeneratorConfigHandler.componentVillageTypes);
        		Map<String, ArrayList> mappedComponentVillageTypesNonModDefaults = VillageGeneratorConfigHandler.unpackComponentVillageTypes(VillageGeneratorConfigHandler.MODERN_VANILLA_COMPONENT_VILLAGE_TYPE_DEFAULTS);
        		
            	Iterator iterator = list.iterator();
            	
            	int unmappedComponent=0; // Counts how many structure components try to generate. If none, no text is printed.
            	
            	ArrayList<PieceWeight> unmapped_mod_components = new ArrayList<PieceWeight>();
            	
                while (iterator.hasNext())
                {
                	PieceWeight pw = (StructureVillagePieces.PieceWeight)iterator.next();
                	
                	// This component does not appear in the "Component Village Types" config entry
                	if (!mappedComponentVillageTypes.get("ClassPaths").contains(pw.villagePieceClass.toString().substring(6)))
                	{
                		if (mappedComponentVillageTypesNonModDefaults.get("ClassPaths").contains(pw.villagePieceClass.toString().substring(6)))
                    	{
                			int classPathListIndexNonModDefaults = mappedComponentVillageTypesNonModDefaults.get("ClassPaths").indexOf(pw.villagePieceClass.toString().substring(6));
            				
            				if (classPathListIndexNonModDefaults!=-1) // It is in the "Component Village Types" default values
            				{
            					// This component is a default type
                    			LogHelper.warn("A village queued "+Reference.MOD_NAME+" building component " + pw.villagePieceClass.toString().substring(6) + " which does not appear in your \"Component Village Types\" config entry. Its default biome type of " + ((String) ((mappedComponentVillageTypesNonModDefaults.get("VillageTypes")).get(classPathListIndexNonModDefaults))).trim().toLowerCase() + " will be used.");
            				}
                    	}
                    	else
                    	{
                    		// This component is not a default type
                    		unmapped_mod_components.add(pw);
                    	}
                	}
                }
                
                if (unmapped_mod_components.size()>0)
        		{
        			LogHelper.warn("A village queued the following modded village components which do not appear in your \"Component Village Types\" config entry. They will be registered for all village types until otherwise specified:");
        			
        			Iterator unmapped_mod_components_iterator = unmapped_mod_components.iterator();
        			while (unmapped_mod_components_iterator.hasNext())
                    {
        				PieceWeight pw_unmapped = (StructureVillagePieces.PieceWeight)unmapped_mod_components_iterator.next();
        				LogHelper.warn("Weight " + pw_unmapped.villagePieceWeight + ", Limit " + pw_unmapped.villagePiecesLimit + ": " + pw_unmapped.villagePieceClass.toString().substring(6));
                    }
        		}
            }
            
            // Generate the "start" component and add it to the list
            StructureVillageVN.StartVN start = null;
            
            // Select a starter at random
            
            StructureVillageVN.StartVN[] plainsStarters = new StructureVillageVN.StartVN[]
            {
	        		new PlainsStructures.PlainsFountain01(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Fountain
	        		new PlainsStructures.PlainsMeetingPoint1(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Plains Well
	        		new PlainsStructures.PlainsMeetingPoint2(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Market
	        		new PlainsStructures.PlainsMeetingPoint3(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Tree
            };
            
            StructureVillageVN.StartVN[] desertStarters = new StructureVillageVN.StartVN[]
            {
	            	new DesertStructures.DesertMeetingPoint1(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Fountain with structure
	            	new DesertStructures.DesertMeetingPoint2(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Desert well
	            	new DesertStructures.DesertMeetingPoint3(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Desert market
            };
            
            StructureVillageVN.StartVN[] taigaStarters = new StructureVillageVN.StartVN[]
            {
	            	new TaigaStructures.TaigaMeetingPoint1(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Simple grass plot with two houses
	            	new TaigaStructures.TaigaMeetingPoint2(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Taiga Well
            };
            
            StructureVillageVN.StartVN[] savannaStarters = new StructureVillageVN.StartVN[]
            {
	            	new SavannaStructures.SavannaMeetingPoint1(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Savanna market
	            	new SavannaStructures.SavannaMeetingPoint2(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Savanna fountain
	            	new SavannaStructures.SavannaMeetingPoint3(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Savanna double well
	            	new SavannaStructures.SavannaMeetingPoint4(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Savanna single well
            };
            
            StructureVillageVN.StartVN[] snowyStarters = new StructureVillageVN.StartVN[]
            {
            		new SnowyStructures.SnowyMeetingPoint1(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Ice spire
            		new SnowyStructures.SnowyMeetingPoint2(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Frozen Fountain
            		new SnowyStructures.SnowyMeetingPoint3(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Snowy Pavilion
            };
            
            StructureVillageVN.StartVN[] jungleStarters = new StructureVillageVN.StartVN[]
            {
            		new JungleStructures.JungleStatue(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Jungle Statue
            		new JungleStructures.JungleCocoaTree(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Jungle Cocoa Tree
            		new JungleStructures.JungleGarden(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Jungle Garden
            		new JungleStructures.JungleVilla(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Jungle Villa
            };
            
            StructureVillageVN.StartVN[] swampStarters = new StructureVillageVN.StartVN[]
            {
                	new SwampStructures.SwampWillow(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Swamp Willow
                	new SwampStructures.SwampStatue(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Swamp Statue
                	new SwampStructures.SwampPavilion(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Swamp Pavilion
                	new SwampStructures.SwampMonolith(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize), // Swamp Monolith
            };
            
            double[] townCenterWeightArray;
            StructureVillageVN.StartVN[] townCenterElementArray;
            
            if (startVillageType==FunctionsVN.VillageType.DESERT)
            {
            	townCenterElementArray = desertStarters;
            	townCenterWeightArray = new double[]{
            			VillageGeneratorConfigHandler.componentModernDesertFountain,
            			VillageGeneratorConfigHandler.componentModernDesertWell,
            			VillageGeneratorConfigHandler.componentModernDesertMarket
            			};
            }
            else if (startVillageType==FunctionsVN.VillageType.TAIGA)
            {
            	townCenterElementArray = taigaStarters;
            	townCenterWeightArray = new double[]{
            			VillageGeneratorConfigHandler.componentModernTaigaSquare,
            			VillageGeneratorConfigHandler.componentModernTaigaWell
            			};
            }
            else if (startVillageType==FunctionsVN.VillageType.SAVANNA)
            {
            	townCenterElementArray = savannaStarters;
            	townCenterWeightArray = new double[]{
            			VillageGeneratorConfigHandler.componentModernSavannaMarket,
            			VillageGeneratorConfigHandler.componentModernSavannaFountain,
            			VillageGeneratorConfigHandler.componentModernSavannaDoubleWell,
            			VillageGeneratorConfigHandler.componentModernSavannaWell
            			};
            }
            else if (startVillageType==FunctionsVN.VillageType.SNOWY)
            {
            	townCenterElementArray = snowyStarters;
            	townCenterWeightArray = new double[]{
            			VillageGeneratorConfigHandler.componentModernSnowyIceSpire,
            			VillageGeneratorConfigHandler.componentModernSnowyFountain,
            			VillageGeneratorConfigHandler.componentModernSnowyPavilion
            			};
            }
            else if (startVillageType==FunctionsVN.VillageType.JUNGLE)
            {
            	townCenterElementArray = jungleStarters;
            	townCenterWeightArray = new double[]{
            			VillageGeneratorConfigHandler.componentModernJungleStatue,
            			VillageGeneratorConfigHandler.componentModernJungleCocoaTree,
            			VillageGeneratorConfigHandler.componentModernJungleGarden,
            			VillageGeneratorConfigHandler.componentModernJungleVilla
            			};
            }
            else if (startVillageType==FunctionsVN.VillageType.SWAMP)
            {
            	townCenterElementArray = swampStarters;
            	townCenterWeightArray = new double[]{
            			VillageGeneratorConfigHandler.componentModernSwampWillow,
            			VillageGeneratorConfigHandler.componentModernSwampStatue,
            			VillageGeneratorConfigHandler.componentModernSwampPavilion,
            			VillageGeneratorConfigHandler.componentModernSwampMonolith
            			};
            }
            else // Plains if nothing else matches
            {
            	townCenterElementArray = plainsStarters;
            	townCenterWeightArray = new double[]{
            			VillageGeneratorConfigHandler.componentModernPlainsFountain,
            			VillageGeneratorConfigHandler.componentModernPlainsWell,
            			VillageGeneratorConfigHandler.componentModernPlainsMarket,
            			VillageGeneratorConfigHandler.componentModernPlainsOakTree
            			};
            }
            
        	double totalWeight = 0D; for (int i=0; i<townCenterWeightArray.length; i++) {totalWeight += townCenterWeightArray[i];}
        	if (totalWeight<=0D) {start = plainsStarters[1];} // If total weight is 0, default to Plains well
        	else // Select the starter based off of weighting specified in configs
        	{
        		start = (StructureVillageVN.StartVN) FunctionsVN.weightedRandom(townCenterElementArray, townCenterWeightArray, random);
        	}
        	
        	
            // === FORCE A SPECIFIC STARTER FOR TESTING PURPOSES === //
        	//start = new JungleStructures.JungleCocoaTree(world.getWorldChunkManager(), 0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageSize);
            
        	
        	
            // Add well to the component list
            this.components.add(start);
            
            // Build the town center and get that ball rollin homie
            start.buildComponent(start, this.components, random);
            
            List paths =      start.field_74930_j; // Paths
            List components = start.field_74932_i; // Village Components
            int counter; // Used a couple times
            
            while (!paths.isEmpty() || !components.isEmpty())
            {
                StructureComponent structurecomponent;
                
                if (paths.isEmpty()) // There are components remaining, but no paths
                {
                    counter = random.nextInt(components.size());
                    structurecomponent = (StructureComponent)components.remove(counter);
                    structurecomponent.buildComponent(start, this.components, random);
                }
                else // There are paths remaining, but no components
                {
                    counter = random.nextInt(paths.size());
                    structurecomponent = (StructureComponent)paths.remove(counter);
                    structurecomponent.buildComponent(start, this.components, random);
                }
            }

            this.updateBoundingBox();
            counter = 0;
            Iterator iterator = this.components.iterator();
            
            while (iterator.hasNext())
            {
                StructureComponent structurecomponent_temp = (StructureComponent)iterator.next();

                if (!(structurecomponent_temp instanceof Road)) {++counter;}
            }
            
            this.hasMoreThanTwoComponents = counter > 2;
        }

        /**
         * currently only defined for Villages, returns true if Village has more than 2 non-road components
         */
        @Override
        public boolean isSizeableStructure()
        {
            return this.hasMoreThanTwoComponents;
        }
        
        // Presumably for setting the "Valid" tag
        @Override
        public void func_143022_a(NBTTagCompound p_143022_1_)
        {
            super.func_143022_a(p_143022_1_);
            p_143022_1_.setBoolean("Valid", this.hasMoreThanTwoComponents);
        }
        
        // Presumably for reading the "Valid" tag
        @Override
        public void func_143017_b(NBTTagCompound p_143017_1_)
        {
            super.func_143017_b(p_143017_1_);
            this.hasMoreThanTwoComponents = p_143017_1_.getBoolean("Valid");
        }
    }
}
