package de.shirkanesi.professorbot.console;

import de.shirkanesi.professorbot.console.commands.HelpCommand;
import de.shirkanesi.professorbot.console.commands.StopCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static de.shirkanesi.professorbot.ButterBrot.LOGGER;

/**
 * Handler for internal commands.
 * Console commands
 */
public class ConsoleManager implements Runnable{

    private static ConsoleManager instance;

    private final List<ConsoleCommand> commands = new ArrayList<>();

    public ConsoleManager(){
        instance = this;

        addCommand(new StopCommand());
        addCommand(new HelpCommand());

        new Thread(this).start();
    }

    /**
     * Add a command to the available command bucket
     * @param cmd an object of the ConsoleCommand
     */
    private void addCommand(ConsoleCommand cmd) {
        commands.add(cmd);
    }

    /**
     * Internal scanner for System.in checking for new inputs in the console
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();

            boolean found = false;
            for(ConsoleCommand command : commands){
                if(Arrays.stream(command.getAlias()).anyMatch(s1 -> s1.equalsIgnoreCase(s))){
                    command.onExecute(s.replaceFirst(s.split(" ")[0], "").split(" "));
                    found = true;
                    break;
                }
            }

            if(!found) {
                LOGGER.info("Command not found");
            }
        }
    }

    /**
     * Get all current registered commands
     * @return a List with the ConsoleCommands
     */
    public static List<ConsoleCommand> getCommands() {
        return instance.commands;
    }
}
