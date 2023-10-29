package profplan.logic;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Utilities class for Help Command. In charge of getting all commands and their messages.
 */
public class HelperCommandUtil {
    private final String commandPackage = "profplan.logic.commands";
    private final String packageFilePath = "src/main/java/" + commandPackage.replace('.', '/');
    private final String commandUsageFieldName = "MESSAGE_USAGE";
    private final String commandExampleFieldName = "MESSAGE_EXAMPLE";
    private final String commandWordFieldName = "COMMAND_WORD";
    private final String delimitterBetweenCommands = "\n--------------------------------\n";

    private String[] getListOfCommandsClasses() {
        File[] commandFolderContents = new File(packageFilePath).listFiles();
        HashSet<String> rtn = new HashSet<String>();
        for (File f : commandFolderContents) {
            String fileName = f.getName();
            if (fileName.contains("Command.java") && !fileName.equals("Command.java")) {
                rtn.add(fileName.replace(".java", ""));
            }
        }
        return rtn.toArray(new String[rtn.size()]);
    }

    /**
     * Gets the commands descriptions of all commands in the command package.
     * @return A string of the descriptions of all commands with a delimitter.
     */

    public String getAllCommandDescriptions() {
        String[] commands = getListOfCommandsClasses();
        String rtn = "";
        for (String command : commands) {
            try {
                Field usage = Class.forName(String.format("%s.%s", commandPackage, command)
                    ).getDeclaredField(commandUsageFieldName);
                rtn += usage.get(null).toString() + delimitterBetweenCommands;
            } catch (ClassNotFoundException e) {
                //impossible because the list is the list of classes
                continue;
            } catch (NoSuchFieldException e) {
                //impossible unless your command has no command word??
                continue;
            } catch (IllegalAccessException e) {
                //impossible as command word MUST be public
                continue;
            }
        }
        return rtn;
    }

    /**
     * Gets a HashMap of the command words to map the command word to its class name.
     * @return HashMap of command words. Key: command word, value: command class name.
     */

    private HashMap<String, String> getCommandWords() {
        String[] commandNames = getListOfCommandsClasses();
        HashMap<String, String> rtn = new HashMap<>();
        for (String commandName : commandNames) {
            try {
                Field commandWord = Class.forName(String.format("%s.%s", commandPackage, commandName)
                    ).getDeclaredField(commandWordFieldName);
                rtn.put(commandWord.get(null).toString(), commandName);
            } catch (ClassNotFoundException e) {
                //impossible because the list is the list of classes
                continue;
            } catch (NoSuchFieldException e) {
                //impossible unless your command has no command word??
                continue;
            } catch (IllegalAccessException e) {
                //impossible as command word MUST be public
                continue;
            }
        }
        return rtn;
    }

    /**
     * Checks if given command word is a command.
     */
    public boolean isValidCommand(String commandWord) {
        HashMap<String, String> commands = getCommandWords();
        return commands.containsKey(commandWord);
    }

    /**
     * Gets the description of the command corresponding to the commandword.
     * @param commandWord The commandword of the Command.
     * @return A String containing the Command's usage and example.
     */
    public String getOneCommandDescription(String commandWord) {
        HashMap<String, String> commands = getCommandWords();
        String rtn = "";
        try {
            rtn += Class.forName(String.format("%s.%s", commandPackage, commands.get(commandWord))
                ).getDeclaredField(commandUsageFieldName).get(null).toString();
            rtn += "\n";
            rtn += Class.forName(String.format("%s.%s", commandPackage, commands.get(commandWord))
                ).getDeclaredField(commandExampleFieldName).get(null).toString();
        } catch (ClassNotFoundException e) {
            //impossible because the list is the list of classes
        } catch (NoSuchFieldException e) {
            //impossible unless your command has no command word??
        } catch (IllegalAccessException e) {
            //impossible as command word MUST be public
        }
        return rtn;
    }
}
