package astrotibs.villagenames.handler;

import astrotibs.villagenames.config.GeneralConfig;
import astrotibs.villagenames.name.NameGenerator;
import astrotibs.villagenames.prismarine.guardian.entity.monster.EntityGuardian;
import astrotibs.villagenames.utility.Reference;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import java.util.Random;

public class SpawnNamingHandler {

	@SubscribeEvent
	public void onPopulating(EntityJoinWorldEvent event) {

		if (event.entity instanceof EntityLiving && !event.entity.worldObj.isRemote) { // Only looks at living entities in order to reduce grind

			EntityLiving entity = (EntityLiving) (event.entity); // Makes things easier to manage
			int targetAge = 0;
			if (entity instanceof EntityAgeable) {
				targetAge = ((EntityAgeable) entity).getGrowingAge();
			}

			String entityClassPath = entity.getClass().toString().substring(6);

			// Have a specific string for Elder Guardian, so that it can be accessed independently of ordinary Guardians.
			if (event.entity instanceof EntityGuardian) {
				if ( ((EntityGuardian) event.entity).isElder() ) { // Reference "Elder" guardians using the below string. Reference ordinary guardians as EntityGuardian
					entityClassPath = Reference.ELDER_GUARDIAN_CLASS;
				}
			}
			
			// keys: "NameTypes", "Professions", "ClassPaths", "AddOrRemove"
			if (GeneralConfig.modNameMappingAutomatic_map.get("ClassPaths").contains(entityClassPath) ) {
				// "true" means "add"
				final int classIndex = GeneralConfig.modNameMappingAutomatic_map.get("ClassPaths").indexOf(entityClassPath);
				String addOrRemove = (String) ((GeneralConfig.modNameMappingAutomatic_map.get("AddOrRemove")).get(classIndex));

				// The spawning entity matches the list. Now check to see if it should GAIN or LOSE its tag.

				// Entity does not have a tag and should...
				final String entityNametag = entity.getCustomNameTag().trim();
				if (addOrRemove.trim().equals("add") && GeneralConfig.nameEntities) { // Add a new name?
					// Contingency in there specifically for PM's Traveling Merchants
					if (entityNametag.isEmpty() || "net.daveyx0.primitivemobs.entity.passive.EntityTravelingMerchant".equals(entityClassPath) && entityNametag.equals("Traveling Merchant")) {

						String nameType = (String) ((GeneralConfig.modNameMappingAutomatic_map.get("NameTypes")).get(classIndex));

						String[] newNameA = NameGenerator.newRandomName(nameType, new Random());
						String newName = (newNameA[1]+" "+newNameA[2]+" "+newNameA[3]).trim();
						// Generate profession tag
						if (GeneralConfig.addJobToName && (!(entity instanceof EntityVillager) || targetAge >= 0)) {
							String careerTag = (String) ((GeneralConfig.modNameMappingAutomatic_map.get("Professions")).get(classIndex));
							newName += careerTag.trim().isEmpty() ? "" : " (" + careerTag + ")";
						}
						// Apply the name
						entity.setCustomNameTag( newName );
					}
					// Clickable Entity already has a name. You may want to add (or remove) a career tag.
					else if (!entityNametag.contains("(") && GeneralConfig.addJobToName && (!(entity instanceof EntityVillager) || targetAge >= 0)) { // Target is named but does not have job tag: add one!
						String careerTag = (String) ((GeneralConfig.modNameMappingAutomatic_map.get("Professions")).get(classIndex));
						String newCustomName = entityNametag + (careerTag.trim().isEmpty() ? "" : " (" + careerTag + ")");
						// Apply the name
						entity.setCustomNameTag(newCustomName.trim());
					} else if (entityNametag.contains("(") && !GeneralConfig.addJobToName) { // Target has a job tag: remove it...
						entity.setCustomNameTag(entityNametag.substring(0, entityNametag.indexOf("(")).trim());
					}
				} else if (addOrRemove.trim().equals("remove")) { // Remove an assigned name?
					if (!entityNametag.isEmpty()) {
						entity.setCustomNameTag("");
					}
				}
			}
		}
	}
}
