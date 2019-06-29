package astrotibs.villagenames.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.ieep.ExtendedVillager;
import astrotibs.villagenames.ieep.ExtendedZombieVillager;
import astrotibs.villagenames.prismarine.minecraft.Vec3i;
import astrotibs.villagenames.utility.LogHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;


/**
 * Adapted from Villager Tweaks by sidben:
 * https://github.com/sidben/VillagerTweaks/blob/master/src/main/java/sidben/villagertweaks/tracker/ServerInfoTracker.java
 * @author AstroTibs
 *
 * Helper class to store certain server-side information until they are needed or expire.
 * 
 * Used to integrate actions in multiple classes or provide the functionality of non-existing Forge Hooks.
 * 
 */

/*
 * ----------------------------------------------------------------------------------------
 * Causal sequence - sidben
 * ----------------------------------------------------------------------------------------
 * 
 * VILLAGER -> ZOMBIE VILLAGER
 * 
 * 1) Villager is killed and the source of damage is EntityZombie;
 * 2) I intercept [onLivingDeath] and add some info of the villager to this tracker - add(EntityVillager villager);
 * 3) World spawns a zombie villager (firing the [EntityJoinWorldEvent] event) at the exact position where the
 * villager died, ignoring adult/child status (bug?);
 * 4) Players in that region starts tracking the new entity;
 * 
 * Here I keep track of all villagers killed by zombies and add them to a list for a certain time.
 * 
 * When a zombie villager spawns, I check if their spawn coordinates matches any villager that just died and, if so,
 * copy the information I stored on the EventTracker (mainly profession and custom name).
 * 
 * After a zombie spawn, the [PlayerEvent.StartTracking] is fired and I use it to notify the clients about the
 * extended properties that were just copied.
 * 
 * 
 * 
 * ZOMBIE VILLAGER -> VILLAGER
 * 
 * 1) Zombie villager starts to be cured (player used potion and golden apple);
 * 2) EntityZombie fires the [startConversion] inner method, setting the conversion counter to something
 * between 3600~6000 ticks;
 * 3) On every entity update, the counter is decreased by the amount defined in [getConversionTimeBoost];
 * 4) When that counter reaches zero, EntityZombie fires the [convertToVillager] method, creating a new
 * villager on the same location, preserving adult/child status;
 * 5) After creating the new villager, the zombie is removed with [world.removeEntity] and the villager
 * is added with [world.spawnEntityInWorld];
 * 
 * Here I listen to the [onLivingUpdateEvent], looking for zombie villagers about to convert. I try to
 * predict the next value of the counter, but since it has a rare random factor (iron bars and beds can give
 * a boost), it's not 100% reliable.
 * 
 * When I detect the zombie is about to convert on the next tick update, I add it to the tracker list.
 * 
 * I also listen to the [onEntityJoinWorld] and every time a villager joins the world, I check if a zombie
 * villager just converted on that spot so I can copy the info.
 */

public class ServerInfoTracker
{

    public static enum EventType {
    	
        ZOMBIE,         // Zombie was about to be cured, used to transfer info to the new villager
        VILLAGER,       // Villager was killed by zombie, used to transfer info in case he is zombified
    	GUARD;			// Used for Witchery town guards keeping their names
    	
        private List<EventTracker>        tracker                 = new ArrayList<EventTracker>();
        public List<EventTracker> getTracker() {
            return tracker;
        }
    }


    private static HashMap<Integer, Integer> CuredZombies                 = new HashMap<Integer, Integer>();    // Tracks which players cured zombies (used to reward achievements)
    private static int                       CuredZombiesListLastChange   = 0;                              // Tracks when the list of cured zombies was last updated
    private static HashMap<Integer, Integer> CuredVillagers               = new HashMap<Integer, Integer>();    // Tracks a villager just cured by a player
    private static int                       CuredVillagersListLastChange = 0;                              // Tracks when the list of cured villagers was last updated
    private static final int                 ListExpiration               = 12000;                          // Number of ticks the list will stay alive after the last update, 12000 = 10 minutes

    private static boolean                   canStartTracking             = false;



    /**
     * Adds villager information that should be tracked on the server.
     * Used to track villager that were killed by zombies.
     */
    public static void add(EntityVillager villager)
    {
    	ExtendedVillager ieep = ExtendedVillager.get(villager);
        ServerInfoTracker.add(EventType.VILLAGER, new EventTracker(villager, ieep));
    }

    /**
     * Adds zombie information that should be tracked on the server.
     * Used to track zombies villagers that were about to be cured.
     */
    public static void add(EntityZombie zombie)
    {
        final ExtendedZombieVillager properties = ExtendedZombieVillager.get(zombie);
        ServerInfoTracker.add(EventType.ZOMBIE, new EventTracker(zombie, properties));
    }
    
    
    public static void add(EntityLiving guard)
    {
        ServerInfoTracker.add(EventType.GUARD, new EventTracker(guard));
    }
    
    
    /**
     * Tracks that a certain player started to cure a zombie. Used for achievements.
     */
    public static void startedCuringZombie(int playerID, int zombieID)
    {
        if (zombieID > 0 && playerID > 0) {
            if (GeneralConfig.debugMessages) {
                LogHelper.info("> Player [" + playerID + "] started to cure zombie [" + zombieID + "]");
            }
            ServerInfoTracker.CuredZombies.put(zombieID, playerID);
            ServerInfoTracker.CuredZombiesListLastChange = MinecraftServer.getServer().getTickCounter();
        }
    }
    
        
    
    
    /**
     * Tracks that a villager was successfully cured by a player. Used for achievements.
     * 
     * @param oldZombieID
     *            ID of the zombie that was just cured.
     * @param newVillagerID
     *            ID of the villager that just spawned.
     */
    public static void endedCuringZombie(int oldZombieID, int newVillagerID)
    {
        // checks if the zombie that was cured was one being tracked
        final Integer playerID = ServerInfoTracker.CuredZombies.get(oldZombieID);

        if (newVillagerID > 0 && playerID != null && playerID > 0) {
            if (GeneralConfig.debugMessages) {
                LogHelper.info("> Player [" + playerID + "] cured villager [" + newVillagerID + "], formerly known as zombie [" + oldZombieID + "]");
            }
            ServerInfoTracker.CuredVillagers.put(newVillagerID, playerID);
            ServerInfoTracker.CuredVillagersListLastChange = MinecraftServer.getServer().getTickCounter();
        }
    }



    /**
     * Adds informations that should be tracked on the server.
     */
    private static void add(EventType type, EventTracker event)
    {
        // Don't track anything at tick zero, since that's when the world loads
        if (ServerInfoTracker.ThisTick() == 0) {
            return;
        }
        
        if (event == null) {
            return;
        }
        if (!canStartTracking) {
            return;
        }
        
        /*
        if (GeneralConfigHandler.debugMessages) {
            LogHelper.info("ServerInfoTracker > Tracking a new event of type [" + type + "] - [" + event + "]");
        }
         */
        
        // Adds the "tick of birth" to control when the event will expire
        event.setBirthTick(MinecraftServer.getServer().getTickCounter());

        // Adds the event to the specific list
        type.getTracker().add(event);
        
    }



    /**
     * Seek for a valid event being tracked around a specific position.
     */
    public static EventTracker seek(EventType type, Vec3i position)
    //public static EventTracker seek(EventType type, Vec3 position)
    {
    	/*
        if (GeneralConfigHandler.debugMessages) {
            LogHelper.info("ServerInfoTracker > Seeking one event of type [" + type + "] at [" + position + "]");
        }
		*/
        int rangeLimit;             // how far the code will search for an entity.
        int ageTolerance;           // how old the entry can be considered valid.

        switch (type) {

            case VILLAGER:
                rangeLimit = 1;         // Seek at the exact spot (maybe should be zero?)
                ageTolerance = 5;       // Only 5 ticks tolerance to play safe, since "zombification" happens on the same tick
                break;
            
            case ZOMBIE:
                rangeLimit = 1;         // Seek at the exact spot (maybe should be zero?)
                ageTolerance = 3;       // Only 3 ticks tolerance to play safe, since the cure should happen on the next 1 tick
                break;
            
            case GUARD:                 // Custom type for Witchery
                rangeLimit = 1;         
                ageTolerance = 5;       
                break;
            
                
            default:
                return null;

        }
        
        return seekValueOnList(type.getTracker(), position, rangeLimit, ageTolerance);

    }


    /**
     * Encapsulates the search on a list.
     * 
     * @param list
     *            Target list
     * @param position
     *            Desired coordinates
     * @param rangeLimit
     *            Maximum distance accepted
     * @param ageTolerance
     *            Maximum age of event accepted
     */
    private static EventTracker seekValueOnList(List<EventTracker> list, Vec3i position, int rangeLimit, int ageTolerance)
    //private static EventTracker seekValueOnList(List<EventTracker> list, Vec3 position, int rangeLimit, int ageTolerance)
    {
        final int thisTick = ServerInfoTracker.ThisTick();
        
        for (final EventTracker et : list) {
        	
        	if (et.getBirthTick() > 0 && et.getBirthTick() + ageTolerance >= thisTick
            		&& position.distanceSq(et.getPosition()) <= rangeLimit) {
            		//&& position.squareDistanceTo(et.getPosition()) <= rangeLimit) {
            		
                if (GeneralConfig.debugMessages) {
                    LogHelper.info("ServerInfoTracker > found a valid target [" + et + "]");
                }

                et.expireNow();
                return et;
            }
        }

        return null;
    }


    
    /**
     * Looks for the player that cured the zombie informed and give him/her the achievement.
     * 
     * @param zombieID
     *            Entity ID of the zombie that was cured (NOT villager)
     */
    public static void removeCuredZombiesFromTracker(World world, int zombieID)
    {
        final Integer playerID = ServerInfoTracker.CuredZombies.get(zombieID);
        
        if (
        		playerID != null && playerID > 0
        		//&& zombieID > 0
        		) {
        	
        	// Below was my attempt to add in code to increase your reputation when you heal a villager.
        	// However, it seems that this code already exists in 1.7!
        	
        	/*
            // Attempt to locate the player by the entity ID
            final Entity entity = world.getEntityByID(playerID);
            final Entity transformedZombie = world.getEntityByID(zombieID);
            if (
            		entity != null && entity instanceof EntityPlayer &&
            		transformedZombie != null && transformedZombie instanceof EntityZombie
            		) {
            	
            	// summon Zombie ~ ~ ~ {IsVillager:1}
            	
            	// If found the player, increment their village reputation
            	EntityPlayer player = (EntityPlayer)entity;
            	EntityZombie zombie = (EntityZombie)transformedZombie;
            	
            	// Nearest village to the player
    			Village villageNearZombie = world.villageCollectionObj.findNearestVillage(
    					(int)zombie.posX, (int)zombie.posY, (int)zombie.posZ,
    					EntityInteractHandler.villageRadiusBuffer);
    			
    			// If player is in or near a village,
    			if (villageNearZombie != null) {
    				
    				try {
    					// Get their reputation...
        				int currentRep = villageNearZombie.getReputationForPlayer(player.getDisplayName());
        				// Update their reputation
        				villageNearZombie.setReputationForPlayer(player.getDisplayName(), Math.min(currentRep+5, 10));
    				}
    				catch (Exception e) {}
    				
    			}
    			
            }
        	*/
            // Removes the tracking regardless of finding a player
            ServerInfoTracker.CuredZombies.remove(zombieID);
        }
    }
    
    /**
     * Looks if the infected villager was cured by a player recently to
     * give him/her the achievement.
     * 
     * @param villagerID
     *            Entity ID of the villager that was cured
     */
    
    public static void removeCuredVillagersFromTracker(World world, int villagerID)
    {
        final Integer playerID = ServerInfoTracker.CuredVillagers.get(villagerID);

        if (playerID != null && playerID > 0) {

            // Removes the tracking regardless of finding a player
            ServerInfoTracker.CuredVillagers.remove(villagerID);
        }
    }
	
    
    


    /**
     * Remove all expired information from the tracked lists.
     */
    public static void cleanExpired()
    {
        if (!ServerInfoTracker.canStartTracking) {
            return;
        }

        final int thisTick = ServerInfoTracker.ThisTick();


        // Clean expired from the events tracker
        final int expireAllUntil = thisTick - 100;     // Maximum amount of time an entry stays at the list

        cleanExpiredList(ServerInfoTracker.EventType.VILLAGER.getTracker(), expireAllUntil);
        cleanExpiredList(ServerInfoTracker.EventType.ZOMBIE.getTracker(), expireAllUntil);
        cleanExpiredList(ServerInfoTracker.EventType.GUARD.getTracker(), expireAllUntil);
        
        /*
        // Debug
        if (GeneralConfigHandler.debugMessages) {
            LogHelper.info("-- ServerInfoTracker Cleanup [" + thisTick + "] --");
        }
         */

        // Clear the cured zombie tracker
        if (CuredZombiesListLastChange + ListExpiration < thisTick
        		&& CuredZombies.size()>0) {
            /*
        	if (GeneralConfigHandler.debugMessages) {
                LogHelper.info("resetting cured zombies list");
            }
            */
            ServerInfoTracker.CuredZombies.clear();
        }

        if (CuredVillagersListLastChange + ListExpiration < thisTick
        		&& CuredVillagers.size()>0) {
        	/*
            if (GeneralConfigHandler.debugMessages) {
                LogHelper.info("resetting cured villagers list");
            }
            */
            ServerInfoTracker.CuredVillagers.clear();
        }
        

    }

    /**
     * Encapsulates the cleaning of a list.
     * 
     * @param list
     *            Target list
     * @param limit
     *            Last accepted tick. Everything before this will be expired.
     */
    private static void cleanExpiredList(List<EventTracker> list, int limit)
    {
        if (list.size() > 0) {
            final Iterator<EventTracker> i = list.iterator();

            while (i.hasNext()) {
                final EventTracker et = i.next();
                if (et.getBirthTick() < 0 || et.getBirthTick() < limit) {
                    if (GeneralConfig.debugMessages) {
                        LogHelper.info("removing: " + et);
                    }
                    i.remove();
                }
            }

        }
    }



    /*
     * NOTE: This exists to avoid using the tracker before it's actually needed.
     * Not essential after the auto-cleaning was finished, may be removed in the future.
     * 
     * e.g. - Tracking player-created iron golem.
     */

    /**
     * Allows the tracker to start working.
     */
    public static void startTracking()
    {
        /*
    	if (GeneralConfigHandler.debugMessages) {
            LogHelper.info("ServerInfoTracker > start tracking");
        }
         */
        canStartTracking = true;

        EventType.ZOMBIE.getTracker().clear();
        EventType.VILLAGER.getTracker().clear();
        EventType.GUARD.getTracker().clear();
        
        CuredZombies.clear();
        CuredVillagers.clear();
    }

    
    /**
     * Encapsulates the access to current server tick.
     * 
     */
    private static int ThisTick() {
        return MinecraftServer.getServer().getTickCounter();
    }
    


}
