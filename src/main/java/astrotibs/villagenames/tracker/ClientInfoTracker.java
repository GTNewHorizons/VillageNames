package astrotibs.villagenames.tracker;


import java.util.HashMap;

import astrotibs.villagenames.ieep.ExtendedVillageGuard;
import astrotibs.villagenames.ieep.ExtendedVillager;
import astrotibs.villagenames.ieep.ExtendedZombieVillager;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.network.MessageModernVillagerSkin;
import astrotibs.villagenames.network.MessageVillageGuard;
import astrotibs.villagenames.network.MessageZombieVillagerProfession;
import astrotibs.villagenames.utility.FunctionsVN;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;

/**
 * Adapted from Villager Tweaks by sidben:
 * https://github.com/sidben/VillagerTweaks/blob/master/src/main/java/sidben/villagertweaks/tracker/ClientInfoTracker.java
 * 
 * @author AstroTibs
 *
 * Helper class to manage client-side information.
 *
 */
public class ClientInfoTracker
{
    /*
     * This map holds packets sent from Server to Client indicating which mob
     * has the extended properties, so the client can load the correct skin / overlay / particles.
     * 
     * The info comes from [MetworkHelper.Hanlder] methods and must be loaded
     * when the client executes [EntityJoinWorldEvent].
     * 
     * I can't use the info before that because when the client receives the packet,
     * the local world still haven't loaded all entities (at least on the first load of the world).
     *  
     */
    @SideOnly(Side.CLIENT)
    private static HashMap<Integer, MessageZombieVillagerProfession> LoadedZombies = new HashMap<Integer, MessageZombieVillagerProfession>();
    
    @SideOnly(Side.CLIENT)
    public static void addZombieMessage(MessageZombieVillagerProfession message) {
        ClientInfoTracker.LoadedZombies.put(message.getEntityID(), message);
    }
    
    @SideOnly(Side.CLIENT)
    public static MessageZombieVillagerProfession getZombieMessage(int entityID) {
        MessageZombieVillagerProfession msg = ClientInfoTracker.LoadedZombies.get(entityID);
        ClientInfoTracker.LoadedZombies.remove(entityID);         // removes from the list, it's not needed anymore
        return msg;
    }
    
    
    
    
    
    
    
        
    // Village Guard part
    
    @SideOnly(Side.CLIENT)
    private static HashMap<Integer, MessageVillageGuard> LoadedGuards = new HashMap<Integer, MessageVillageGuard>();
    
    
    @SideOnly(Side.CLIENT)
    public static void addGuardMessage(MessageVillageGuard message) {
        ClientInfoTracker.LoadedGuards.put(message.getEntityID(), message);
    }
    
    @SideOnly(Side.CLIENT)
    public static MessageVillageGuard getGuardMessage(int entityID) {
    	MessageVillageGuard msg = ClientInfoTracker.LoadedGuards.get(entityID);
        ClientInfoTracker.LoadedGuards.remove(entityID);         // removes from the list, it's not needed anymore
        return msg;
    }
    
        
    
    
    
    /**
     * Attempts to locate the entity by its ID and apply the 
     * extended properties.
     */
    @SideOnly(Side.CLIENT)
    public static void SyncZombieMessage(int entityID) {

        // Seeks if the entity ID is loaded
        WorldClient world = Minecraft.getMinecraft().theWorld;
        if (world == null) return;
        Entity entity = world.getEntityByID(entityID);

        // If found the entity, attempt to sync with info sent by the server 
        if (FunctionsVN.isVanillaZombie(entity) ) {
            ClientInfoTracker.SyncZombieMessage((EntityZombie)entity);
        }
        
    }
    
    
    /**
     * Attempts to locate a message with Extended Properties and apply to the zombie. 
     */
    @SideOnly(Side.CLIENT)
    public static void SyncZombieMessage(EntityZombie zombie) {
        
        // Try to locate messages sent by the server, containing special zombie info
        MessageZombieVillagerProfession msg = ClientInfoTracker.getZombieMessage(zombie.getEntityId());
        
        // If a message was found, update the local zombie with that info
        if (msg != null) {
            ExtendedZombieVillager ezv = ExtendedZombieVillager.get(zombie);
            ezv.setProfession(msg.getProfession());
            ezv.setCareer(msg.getCareer());
            ezv.setBiomeType(msg.getBiomeType()); // Added in v3.1
            ezv.setProfessionLevel(msg.getProfessionLevel()); // Added in v3.1
            ezv.setSkinTone(msg.getSkinTone()); // Added in v3.2
        } 
    }
    
    
    
    
    /**
     * Attempts to locate the entity by its ID and apply the 
     * extended properties.
     */
    @SideOnly(Side.CLIENT)
    public static void SyncGuardMessage(int entityID) {

        // Seeks if the entity ID is loaded
        WorldClient world = Minecraft.getMinecraft().theWorld;
        if (world == null) return;
        Entity entity = world.getEntityByID(entityID);

        // If found the entity, attempt to sync with info sent by the server 
        if ( entity.getClass().toString().substring(6).equals(ModObjects.WitcheryGuardClass) ) {
            ClientInfoTracker.SyncGuardMessage((EntityLiving)entity);
        }
        
    }
    
    
    /**
     * Attempts to locate a message with Extended Properties and apply to the guard. 
     */
    @SideOnly(Side.CLIENT)
    public static void SyncGuardMessage(EntityLiving guard) {
        
        // Try to locate messages sent by the server, containing special zombie info
        MessageVillageGuard msg = ClientInfoTracker.getGuardMessage(guard.getEntityId());
        
        // If a message was found, update the local zombie with that info
        if (msg != null) {
        	ExtendedVillageGuard properties = ExtendedVillageGuard.get(guard);
            //properties.setProfession(msg.getProfession());
            //properties.setCareer(msg.getCareer());
        } 
    }
    
    
    
    
    
    
    // Added in v3.1
    
    // --------------------------------- //
    // --- Modern Villager Skin part --- //
    // --------------------------------- //
    
    @SideOnly(Side.CLIENT)
    private static HashMap<Integer, MessageModernVillagerSkin> loadedVillagers = new HashMap<Integer, MessageModernVillagerSkin>();
    
    @SideOnly(Side.CLIENT)
    public static void addModernVillagerMessage(MessageModernVillagerSkin message) {
        ClientInfoTracker.loadedVillagers.put(message.getEntityID(), message);
    }
    @SideOnly(Side.CLIENT)
    public static MessageModernVillagerSkin getModernVillagerMessage(int entityID) {
    	MessageModernVillagerSkin msg = ClientInfoTracker.loadedVillagers.get(entityID);
        ClientInfoTracker.loadedVillagers.remove(entityID);         // removes from the list, it's not needed anymore
        return msg;
    }

    /**
     * Attempts to locate the entity by its ID and apply the 
     * extended properties.
     */
    @SideOnly(Side.CLIENT)
    public static void syncModernVillagerMessage(int entityID) {

        // Seeks if the entity ID is loaded
        WorldClient world = Minecraft.getMinecraft().theWorld;
        if (world == null) return;
        Entity entity = world.getEntityByID(entityID);

        // If found the entity, attempt to sync with info sent by the server 
        if (
        		entity instanceof EntityVillager
        		&& ((EntityVillager)entity).getProfession() >=0
        		//&& ((EntityVillager)entity).getProfession() <=5 Removed in v3.2 to ensure syncing
        		) {
            ClientInfoTracker.syncModernVillagerMessage((EntityVillager)entity);
        }
        
    }
    
    /**
     * Attempts to locate a message with Extended Properties and apply to the villager. 
     */
    @SideOnly(Side.CLIENT)
    public static void syncModernVillagerMessage(EntityVillager villager) {
        
        // Try to locate messages sent by the server, containing special villager info
        MessageModernVillagerSkin msg = ClientInfoTracker.getModernVillagerMessage(villager.getEntityId());
        
        // If a message was found, update the local villager with that info
        if (msg != null) {
            ExtendedVillager properties = ExtendedVillager.get(villager);
            villager.setProfession(msg.getProfession());
            properties.setCareer(msg.getCareer());
            properties.setBiomeType(msg.getBiomeType());
            properties.setProfessionLevel(msg.getProfessionLevel());
            properties.setSkinTone(msg.getSkinTone()); // v3.2
        }
    }
    
    
    
    
}
