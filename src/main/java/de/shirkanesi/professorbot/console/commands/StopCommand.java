package de.shirkanesi.professorbot.console.commands;

import de.shirkanesi.professorbot.ButterBrot;
import de.shirkanesi.professorbot.Init;
import de.shirkanesi.professorbot.console.ConsoleCommand;

public class StopCommand extends ConsoleCommand {

    @Override
    public String[] getAlias() {
        return new String[]{"stop"};
    }

    @Override
    public String getDescription() {
        return "Stop the bot";
    }

    @Override
    public void onExecute(String[] args) {
        ButterBrot.LOGGER.info("Stopping bot...");
        Init.stopBot(true);
    }

}
