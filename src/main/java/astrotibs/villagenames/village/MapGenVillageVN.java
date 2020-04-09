package astrotibs.villagenames.village;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.village.biomestructures.DesertStructures;
import astrotibs.villagenames.village.biomestructures.PlainsStructures;
import astrotibs.villagenames.village.biomestructures.SavannaStructures;
import astrotibs.villagenames.village.biomestructures.TaigaStructures;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
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
		if (event.type == EventType.VILLAGE && GeneralConfig.newVillageGenerator)
        {
			// Do a try/catch because in case the Overworld has not yet loaded
			WorldType worldtype;
			
	        try 
	        {
	            if (MinecraftServer.getServer().worldServerForDimension(0).getWorldInfo().getTerrainType()==null) {return;}
	        }
	        catch (Exception e) {}
	        
        	event.newGen = new MapGenVillageVN();
        }
	}
	
    private int terrainType;
    private int field_82665_g; // Maximum distance between villages
    private int field_82666_h; // Minimum distance between villages
    
    public MapGenVillageVN()
    {
    	this.terrainType = GeneralConfig.newVillageSize-1; // Because vanilla is "0" and default provided value is 1
    	
    	// Set spacings
    	this.field_82666_h = GeneralConfig.newVillageSpacingMedian - GeneralConfig.newVillageSpacingSpread;
    	if (this.field_82666_h<1) {this.field_82666_h=1;}
    	
        this.field_82665_g = GeneralConfig.newVillageSpacingMedian + GeneralConfig.newVillageSpacingSpread;
        
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
                this.terrainType = MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.terrainType, 0);
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
        	BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(chunkX * 16 + 8, chunkZ * 16 + 8); // No need for bit shift here. Villages rarely spawn
        	
        	if (GeneralConfig.spawnBiomesNames != null) // Biome list is not empty
    		{
        		//int dimension = this.worldObj.provider.dimensionId;
    			
    			for (int i = 0; i < GeneralConfig.spawnBiomesNames.length; i++)
    			{
    				if (GeneralConfig.spawnBiomesNames[i].equals(biome.biomeName))
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
        return new MapGenVillageVN.Start(this.worldObj, this.rand, chunkX, chunkZ, this.terrainType);
    }
    
    // Copied from vanilla
    public static class Start extends StructureStart
    {
        /** well ... thats what it does */
        private boolean hasMoreThanTwoComponents;
        
        public Start() {}

        public Start(World world, Random random, int chunkX, int chunkZ, int villageSize)
        {
            super(chunkX, chunkZ);
            
            // My modified version, which allows the user to disable each building
            List list = StructureVillageVN.getStructureVillageWeightedPieceList(random, villageSize);
            
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
            
            start = savannaStarters[random.nextInt(savannaStarters.length)];
            
            
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
