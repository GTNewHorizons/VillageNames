package astrotibs.villagenames.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import astrotibs.villagenames.name.NameGenerator;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class CommandName extends CommandBase {
	
	// Different specific structures
	private static final String NAME_VILLAGE = "Village";
	private static final String NAME_VILLAGER = "Villager";
	private static final String NAME_MINESHAFT = "Mineshaft";
	private static final String NAME_STRONGHOLD = "Stronghold";
	private static final String NAME_TEMPLE = "Temple";
	private static final String NAME_FORTRESS = "Fortress";
	private static final String NAME_MONUMENT = "Monument";
	private static final String NAME_ENDCITY = "EndCity";
	private static final String NAME_MANSION = "Mansion";
	private static final String NAME_DEMON = "Demon";
	private static final String NAME_ANGEL = "Angel";
	private static final String NAME_DRAGON = "Dragon";
	private static final String NAME_GOLEM = "Golem";
	private static final String NAME_ALIEN = "Alien";
	private static final String NAME_ALIENVILLAGE = "AlienVillage";
	private static final String NAME_GOBLIN = "Goblin";
	private static final String NAME_PET = "Pet";
	private static final String NAME_CUSTOM = "Custom"; // Added in v3.1.1
	private static final int maxLoops = 10; // Maximum number of outputs from the name generator command
	private static final String[] nameChoices = {
			NAME_ALIENVILLAGE,
			NAME_ALIEN,
			NAME_ANGEL,
			NAME_CUSTOM, // Added in v3.1.1
			NAME_DEMON,
			NAME_DRAGON,
			NAME_ENDCITY,
			NAME_FORTRESS,
			NAME_GOBLIN,
			NAME_GOLEM,
			NAME_MANSION,
			NAME_MINESHAFT,
			NAME_MONUMENT,
			NAME_PET,
			NAME_STRONGHOLD,
			NAME_TEMPLE,
			NAME_VILLAGE,
			NAME_VILLAGER
			};
	
	
	@Override
	public String getCommandName() {
		return "vn_name";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "§c/"+getCommandName()+" <structureType> [int, max "+maxLoops+"]";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] strings) {
		
		if (strings.length < 1 || strings.length > 2 ) {
			sender.addChatMessage( new ChatComponentText(getCommandUsage(null)) );
			return;
			}
		
		String nameType = strings[0];
		int numberToMake;
		try {
			numberToMake = Math.min(Integer.parseInt(strings[1]), maxLoops);
		}
		catch (Exception e) {
			numberToMake = 1;
		}
		
		String[] arrayName;
		String stringName;
		String outputString = ""; // This will be either one name, or many names
		
		World world = sender.getEntityWorld();
		
		if (!world.isRemote) {
			
			for(int i = 0; i < numberToMake; i++) {
				
				// Add comma separation
				if (i > 0) {outputString += ", ";}
				
				String[] sa = NameGenerator.newRandomName(nameType, new Random());
				stringName = (sa[1] + " " + sa[2] + " " + sa[3]).trim();
				outputString += stringName;
			}
			sender.addChatMessage( new ChatComponentText("") );
			sender.addChatMessage( new ChatComponentText(nameType+" name" + (numberToMake==1 ? "" : "s" ) + ":") );
			sender.addChatMessage( new ChatComponentText(outputString) );
        } 
		
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 1;
	}
	
	/**
     * Adds the strings available in this command to the given list of tab completion options.
     */
	@SuppressWarnings("unchecked")
	@Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] strings)
    {
        if (strings.length == 1) {
        	return getListOfStringsMatchingLastWord(strings, nameChoices);
        }
        else if (strings.length >= 2) {
        	return getListOfStringsMatchingLastWord(strings, new String[]{"1"});
        }
        else {
        	return new ArrayList<String>();
        }
    }
	
}