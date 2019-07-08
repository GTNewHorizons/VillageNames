package astrotibs.villagenames.network;

import astrotibs.villagenames.VillageNames;
import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.ieep.ExtendedVillageGuard;
import astrotibs.villagenames.ieep.ExtendedVillager;
import astrotibs.villagenames.ieep.ExtendedZombieVillager;
import astrotibs.villagenames.tracker.ClientInfoTracker;
import astrotibs.villagenames.utility.LogHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;


/**
 * Adapted from Villager Tweaks by sidben:
 * https://github.com/sidben/VillagerTweaks/blob/master/src/main/java/sidben/villagertweaks/network/NetworkHelper.java
 * @author AstroTibs
 * 
 * Responsible for sending and receiving packets / messages between client and server. 
 *
 */

public class NetworkHelper
{
	
	
    //---------------------------------------------------------------------
    //      Modern Villager
    //---------------------------------------------------------------------
	
	/**
	 * Used to update client to a villager's career value
	 * Server -> Client 
	 */
	// Deployment
    public static void sendModernVillagerSkinMessage(int villagerID, ExtendedVillager ev, EntityPlayer target) {
        if (
				GeneralConfig.villagerCareers
				&& (
						villagerID > 0
						&& ev != null
						&& ev.getCareer() > 0)
        		) 
        {
            MessageModernVillagerSkin message = new MessageModernVillagerSkin(
            		villagerID,
            		ev.getProfession(),
            		ev.getCareer(),
            		ev.getBiomeType(), // Updated in v3.1
            		ev.getProfessionLevel(), // Updated in v3.1
            		ev.getSkinTone()
            		);
            
            // Sends a message to the player, with the zombie extra info
            VillageNames.VNNetworkWrapper.sendTo(message, (EntityPlayerMP)target);
        }
    }
	
    // Reception
    public static class ModernVillagerSkinHandler implements IMessageHandler<MessageModernVillagerSkin, IMessage> {

        @Override
        public IMessage onMessage(MessageModernVillagerSkin message, MessageContext ctx) {
            
            if ( // Changed if condition in v3.1
    				//GeneralConfig.villagerCareers
    				//&& (
					message.getEntityID() > 0
					&& message.getProfession() >= 0
					//&& message.getProfession() <= 5 // v3.2 - Deactivated to allow modded modular villager skins
					//&& message.getCareer() > 0 // Deactivated to allow Nitwit syncing
    				//		)
            		) { 
                // Saves the info to be used later, when the entity actually loads
                ClientInfoTracker.addModernVillagerMessage(message);
                ClientInfoTracker.syncModernVillagerMessage(message.getEntityID());
            }
            
            return null;
        }
        
    }
    
    
	
	
    //---------------------------------------------------------------------
    //      Zombie Villager Message Deployment
    //---------------------------------------------------------------------
    
    /**
     * Send custom info from zombies villagers when the player starts to track them.
     * Mainly used to notify the client of the zombie villager profession, so it
     * can render the correct skin.
     *  
     * Server -> Client 
     */
    public static void sendZombieVillagerProfessionMessage(int zombieId, ExtendedZombieVillager properties, EntityPlayer target) {
        if (
        		( GeneralConfig.villagerCareers && (zombieId > 0 && properties != null && properties.getProfession() >= 0 && properties.getCareer() >= 0) ) // Career 0 condition v3.2 to allow non-standard profs
        		|| ( zombieId > 0 && properties != null && properties.getProfession() >= 0 )
        		) 
        {
            MessageZombieVillagerProfession message = new MessageZombieVillagerProfession(
            		zombieId,
            		properties.getProfession(),
            		properties.getCareer(),
            		properties.getBiomeType(), // Updated in v3.1
            		properties.getProfessionLevel(), // Updated in v3.1
            		properties.getSkinTone() // v3.2
            		);
            if (GeneralConfig.debugMessages) { // Debug
            	//LogHelper.info("** Sending Message: Zombie Profession **");
                LogHelper.info("NetworkHelper > target: " + target);
                LogHelper.info("NetworkHelper > " + message.toString());
            }
            
            // Sends a message to the player, with the zombie extra info
            VillageNames.VNNetworkWrapper.sendTo(message, (EntityPlayerMP)target);
        }
    }
    
    
    
    

    //---------------------------------------------------------------------
    //      Zombie Villager Message Reception
    //---------------------------------------------------------------------

    public static class ZombieVillagerProfessionHandler implements IMessageHandler<MessageZombieVillagerProfession, IMessage> {

        @Override
        public IMessage onMessage(MessageZombieVillagerProfession message, MessageContext ctx) {
            
            if (
            		( GeneralConfig.villagerCareers && (message.getEntityID() > 0 && message.getProfession() >= 0 && message.getCareer() > 0) )
            		|| ( message.getEntityID() > 0 && message.getProfession() >= 0 )
            		) { 
                // Saves the info to be used later, when the entity actually loads
                ClientInfoTracker.addZombieMessage(message);
                
                // Attempts to sync the entity. Most of the times the entity won't be found 
                // when this code execute, but on some cases the entity can join the world
                // before the packet is received (e.g. villager gets zombified).
                ClientInfoTracker.SyncZombieMessage(message.getEntityID());
            }
            
            return null;
        }
        
    }
    
    
    
    
    //---------------------------------------------------------------------
    //      Village Guard Message Deployment
    //---------------------------------------------------------------------
    
    public static void sendVillageGuardMessage(int guardId, ExtendedVillageGuard properties, EntityPlayer target) {
        if (
        		guardId > 0 && properties != null
        		//( GeneralConfigHandler.villagerCareers && (guardId > 0 && properties != null && properties.getProfession() >= 0 && properties.getCareer() > 0) )
        		//|| ( guardId > 0 && properties != null && properties.getProfession() >= 0 )
        		) 
        {
            MessageVillageGuard message = new MessageVillageGuard(guardId);//, properties.getProfession(), properties.getCareer());
            if (GeneralConfig.debugMessages) { // Debug
            	//LogHelper.info("** Sending Message: Zombie Profession **");
                LogHelper.info("NetworkHelper > target: " + target);
                LogHelper.info("NetworkHelper > " + message.toString());
            }
            
            // Sends a message to the player, with the zombie extra info
            VillageNames.VNNetworkWrapper.sendTo(message, (EntityPlayerMP)target);
        }
    }
    
    
    
    //---------------------------------------------------------------------
    //      Village Guard Message Reception
    //---------------------------------------------------------------------

    public static class VillageGuardHandler implements IMessageHandler<MessageVillageGuard, IMessage> {

        @Override
        public IMessage onMessage(MessageVillageGuard message, MessageContext ctx) {
            
            if (
            		message.getEntityID() > 0
            		//( GeneralConfigHandler.villagerCareers && (message.getEntityID() > 0 && message.getProfession() >= 0 && message.getCareer() > 0) )
            		//|| ( message.getEntityID() > 0 && message.getProfession() >= 0 )
            		) { 
                // Saves the info to be used later, when the entity actually loads
                ClientInfoTracker.addGuardMessage(message);
                
                // Attempts to sync the entity. Most of the times the entity won't be found 
                // when this code execute, but on some cases the entity can join the world
                // before the packet is received (e.g. villager gets zombified).
                ClientInfoTracker.SyncGuardMessage(message.getEntityID());
            }
            
            return null;
        }
        
    }
    
    
}
