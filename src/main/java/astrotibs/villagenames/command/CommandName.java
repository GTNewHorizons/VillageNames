package astrotibs.villagenames.command;

import java.util.ArrayList;
import java.util.List;

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
	private static final String NAME_ALIENVILLAGER = "AlienVillager";
	private static final String NAME_ALIENVILLAGE = "AlienVillage";
	private static final String NAME_HOBGOBLIN = "Hobgoblin";
	private static final int maxLoops = 10; // Maximum number of outputs from the name generator command
	private static final String[] nameChoices = {
			NAME_VILLAGE,
			NAME_VILLAGER,
			NAME_MINESHAFT,
			NAME_STRONGHOLD,
			NAME_TEMPLE,
			NAME_FORTRESS,
			NAME_MONUMENT,
			NAME_ENDCITY,
			NAME_MANSION,
			NAME_ALIENVILLAGER,
			NAME_ALIENVILLAGE,
			NAME_HOBGOBLIN};
	
	
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
				
				if (nameType.equalsIgnoreCase(NAME_VILLAGE)) {
					arrayName = NameGenerator.newVillageName();
					stringName = (arrayName[1]+" "+arrayName[2]+" "+arrayName[3]).trim();
					outputString += stringName;
					continue;
				}
				else if (nameType.equalsIgnoreCase(NAME_VILLAGER)) {
					stringName = NameGenerator.newVillagerName();
					outputString += stringName;
					continue;
				}
				else if (nameType.equalsIgnoreCase(NAME_MINESHAFT)) {
					arrayName = NameGenerator.newMineshaftName();
					stringName = (arrayName[1]+" "+arrayName[2]+" "+arrayName[3]).trim();
					outputString += stringName;
					continue;
				}
				else if (nameType.equalsIgnoreCase(NAME_STRONGHOLD)) {
					arrayName = NameGenerator.newStrongholdName();
					stringName = (arrayName[1]+" "+arrayName[2]+" "+arrayName[3]).trim();
					outputString += stringName;
					continue;
				}
				else if (nameType.equalsIgnoreCase(NAME_TEMPLE)) {
					arrayName = NameGenerator.newTempleName();
					stringName = (arrayName[1]+" "+arrayName[2]+" "+arrayName[3]).trim();
					outputString += stringName;
					continue;
				}
				else if (nameType.equalsIgnoreCase(NAME_FORTRESS)) {
					arrayName = NameGenerator.newFortressName();
					stringName = (arrayName[1]+" "+arrayName[2]+" "+arrayName[3]).trim();
					outputString += stringName;
					continue;
				}
				
				else if (nameType.equalsIgnoreCase(NAME_MONUMENT)) {
					arrayName = NameGenerator.newMonumentName();
					stringName = (arrayName[1]+" "+arrayName[2]+" "+arrayName[3]).trim();
					outputString += stringName;
					continue;
				}
				else if (nameType.equalsIgnoreCase(NAME_ENDCITY)) {
					arrayName = NameGenerator.newEndCityName();
					stringName = (arrayName[1]+" "+arrayName[2]+" "+arrayName[3]).trim();
					outputString += stringName;
					continue;
				}
				else if (nameType.equalsIgnoreCase(NAME_MANSION)) {
					arrayName = NameGenerator.newMansionName();
					stringName = (arrayName[1]+" "+arrayName[2]+" "+arrayName[3]).trim();
					outputString += stringName;
					continue;
				}
				else if (nameType.equalsIgnoreCase(NAME_ALIENVILLAGER)) {
					stringName = NameGenerator.newAlienVillagerName();
					outputString += stringName;
					continue;
				}
				else if (nameType.equalsIgnoreCase(NAME_ALIENVILLAGE)) {
					arrayName = NameGenerator.newAlienVillageName();
					stringName = (arrayName[1]+" "+arrayName[2]+" "+arrayName[3]).trim();
					outputString += stringName;
					continue;
				}
				else if (nameType.equalsIgnoreCase(NAME_HOBGOBLIN)) {
					stringName = NameGenerator.newHobgoblinName();
					outputString += stringName;
					continue;
				}
				// Not a valid command choice
				else {
					sender.addChatMessage( new ChatComponentText("§c"+getCommandUsage(null) ) );
					break;
				}
				
			}
			sender.addChatMessage( new ChatComponentText("") );
			sender.addChatMessage( new ChatComponentText(nameType+":") );
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