package de.shirkanesi.professorbot.console.commands;

import de.shirkanesi.professorbot.ButterBrot;
import de.shirkanesi.professorbot.console.ConsoleManager;
import de.shirkanesi.professorbot.console.ConsoleCommand;

public class HelpCommand extends ConsoleCommand {

    @Override
    public String[] getAlias() {
        return new String[]{"help", "?"};
    }

    @Override
    public String getDescription() {
        return "Returns a list of all commands";
    }

    @Override
    public void onExecute(String[] args) {
        ButterBrot.LOGGER.info("List of all commands: ");
        for(ConsoleCommand command : ConsoleManager.getCommands()){
            ButterBrot.LOGGER.info(command.getAlias()[0] + " - " + command.getDescription());
        }
    }
}
