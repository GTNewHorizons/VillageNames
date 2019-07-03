package astrotibs.villagenames.command;

import java.util.ArrayList;
import java.util.List;

import astrotibs.villagenames.banner.BannerGenerator;
import astrotibs.villagenames.integration.ModObjects;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;

// Added in v3.1.1
public class CommandBanner extends CommandBase {

	@Override
	public String getCommandName() {
		return "vn_banner";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "§c/"+getCommandName()+" <forced base color OR \"village\"> (optional)";
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		
		if (!sender.getEntityWorld().isRemote && getCommandSenderAsPlayer(sender) instanceof EntityPlayer) {
			
			if (ModObjects.chooseModBannerItem() == null)
			{
				sender.addChatMessage( new ChatComponentText("Banners are not available.") );
				return;
			}
			
			EntityPlayer entity = getCommandSenderAsPlayer(sender);
			
			String argument = ""; // Defaults to "generate new base color" if no argument is provided
			
			if (args.length==1)
			{
				argument = args[0];
			}
			else if (args.length > 1)
			{
				sender.addChatMessage( new ChatComponentText(getCommandUsage(null)) );
				return;
			}
			
			// Player wants to retrieve the banner for the current village
			if (argument.toLowerCase().equals("village") && getCommandSenderAsPlayer(sender) != null)
			{
				Object[] villageBannerData = BannerGenerator.getVillageBannerData(entity);
				NBTTagCompound bannerNBT = new NBTTagCompound();
				String villageNameForBanner = "";
				if (villageBannerData!=null) {
					bannerNBT = (NBTTagCompound) villageBannerData[0];
					villageNameForBanner = (String) villageBannerData[1];}
				
				if (!(villageNameForBanner.equals("")))
				{
					// Successfully generate a village banner and give it to the player
					EntityItem eitem = entity.entityDropItem(BannerGenerator.makeBanner(bannerNBT), 1);
					eitem.delayBeforeCanPickup = 0; // No delay: directly into the inventory!
			        return;
				}
				else
				{
					sender.addChatMessage( new ChatComponentText("Not inside a village.") );
					return;
				}
			}
			
			// Player wants to create a random banner with a specific base color
			if (argument.equals("-1")) {
				sender.addChatMessage( new ChatComponentText("Unknown color: -1") );
				return;
				} 
			else if (argument.toLowerCase().equals("black")) {argument = "0";}
			else if (argument.toLowerCase().equals("red")) {argument = "1";}
			else if (argument.toLowerCase().equals("green")) {argument = "2";}
			else if (argument.toLowerCase().equals("brown")) {argument = "3";}
			else if (argument.toLowerCase().equals("blue")) {argument = "4";}
			else if (argument.toLowerCase().equals("purple")) {argument = "5";}
			else if (argument.toLowerCase().equals("cyan")) {argument = "6";}
			else if (argument.toLowerCase().equals("light_gray")) {argument = "7";}
			else if (argument.toLowerCase().equals("lightgray")) {argument = "7";}
			else if (argument.toLowerCase().equals("silver")) {argument = "7";}
			else if (argument.toLowerCase().equals("gray")) {argument = "8";}
			else if (argument.toLowerCase().equals("pink")) {argument = "9";}
			else if (argument.toLowerCase().equals("lime")) {argument = "10";}
			else if (argument.toLowerCase().equals("yellow")) {argument = "11";}
			else if (argument.toLowerCase().equals("light_blue")) {argument = "12";}
			else if (argument.toLowerCase().equals("lightblue")) {argument = "12";}
			else if (argument.toLowerCase().equals("magenta")) {argument = "13";}
			else if (argument.toLowerCase().equals("orange")) {argument = "14";}
			else if (argument.toLowerCase().equals("white")) {argument = "15";}
			
			// Convert number argument to an actual integer
			int forcedbasemeta = -1;
			try {forcedbasemeta = argument.equals("") ? -1 : Integer.parseInt(argument);}
			catch (Exception e) {
				sender.addChatMessage( new ChatComponentText("Unknown color: " + argument) );
				return;
			}
			
			// User submitted a non-color meta number
			if (forcedbasemeta > 15 || forcedbasemeta < -1)
			{
				sender.addChatMessage( new ChatComponentText("Unknown color: " + forcedbasemeta) );
				return;
			}
			
			// Generate a random banner, perhaps using argument as a forced base color
			if (getCommandSenderAsPlayer(sender) != null && getCommandSenderAsPlayer(sender) instanceof EntityPlayer)
			{
				Object[] newRandomBanner = BannerGenerator.randomBannerArrays(entity.worldObj.rand, forcedbasemeta);
				ArrayList<String> patternArray = (ArrayList<String>) newRandomBanner[0];
				ArrayList<Integer> colorArray = (ArrayList<Integer>) newRandomBanner[1];
				
				ItemStack villagebanner = BannerGenerator.makeBanner(patternArray, colorArray);
				
				// At this stage, you probably have a banner. Give it to the command sender.
				if (villagebanner != null)
				{
					EntityItem eitem = entity.entityDropItem(villagebanner, 1);
					eitem.delayBeforeCanPickup = 0; // No delay: directly into the inventory!
				}
			}
			
		}
		
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}
	
	/**
     * Adds the strings available in this command to the given list of tab completion options.
     */
	@SuppressWarnings("unchecked")
	@Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] strings)
    {
        if (strings.length == 1) {
        	return getListOfStringsMatchingLastWord(strings, new String[]{
        			"village",
        			"black",
        			"red",
        			"green",
        			"brown",
        			"blue",
        			"purple",
        			"cyan",
        			"light_gray",
        			"gray",
        			"pink",
        			"lime",
        			"yellow",
        			"light_blue",
        			"magenta",
        			"orange",
        			"white"
        			});
        }
        else {
        	return new ArrayList<String>();
        }
    }
}
