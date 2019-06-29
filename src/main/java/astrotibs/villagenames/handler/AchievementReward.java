package astrotibs.villagenames.handler;

import java.util.Random;

import astrotibs.villagenames.VillageNames;
import astrotibs.villagenames.utility.Reference;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

/**
 * This method rewards the player for completing all five achievements.
 * It will be called each time an advancement is triggered.
 * @author AstroTibs
 */

public class AchievementReward {
	
	public static void allFiveAchievements(EntityPlayerMP playerMP) {
		
		if (
			      playerMP.func_147099_x().hasAchievementUnlocked(VillageNames.archaeologist)
			   && playerMP.func_147099_x().hasAchievementUnlocked(VillageNames.ghosttown)
			   && playerMP.func_147099_x().hasAchievementUnlocked(VillageNames.laputa)
			   && playerMP.func_147099_x().hasAchievementUnlocked(VillageNames.maxrep)
			   && playerMP.func_147099_x().hasAchievementUnlocked(VillageNames.minrep)
				) {
			
			// All five are done
			
			// Send a congratulation message as two parts
			String[] congratsStart = new String[]{
					"Nicely done!",
					"Congratulations!",
					"Thanks for using "+Reference.MOD_NAME+"!",
					"I'm glad you enjoy "+Reference.MOD_NAME+"!",
					"Great job!"
				};
			String[] congratsEnd = new String[]{
					"Here's a little something on me.",
					"Here's something as thanks.",
					"Take this as a memento.",
					"Have a keepsake.",
					"Here's a souvenir."
				};
			
			// achievement give villagenames.achievement.ghosttown
			// achievement take villagenames.achievement.ghosttown
			
			playerMP.addChatComponentMessage(new ChatComponentText(""));
			playerMP.addChatComponentMessage(new ChatComponentText(
					EnumChatFormatting.GOLD + 
					congratsStart[new Random().nextInt(congratsStart.length)]+" "+congratsEnd[new Random().nextInt(congratsEnd.length)]
							+EnumChatFormatting.RESET
					) );
			playerMP.addChatComponentMessage(new ChatComponentText(
					EnumChatFormatting.GOLD + 
					"It's a replica of a sword I used while testing "+Reference.MOD_NAME+" V3."
					+EnumChatFormatting.RESET+" --AstroTibs"
					) );
			
			ItemStack uninstantiator = new ItemStack(Items.golden_sword, 1);
			uninstantiator.addEnchantment(Enchantment.unbreaking, 3);
			uninstantiator.addEnchantment(Enchantment.sharpness, 5);
			//uninstantiator.addEnchantment(Enchantment.looting, 3);
			uninstantiator.setStackDisplayName("Un-Instantiator");
			
			EntityItem eitem = playerMP.entityDropItem(uninstantiator, 1);
	        eitem.delayBeforeCanPickup = 0; //No delay: directly into the inventory!
			
		}
		
	}
	
}
