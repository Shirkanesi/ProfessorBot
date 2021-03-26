package de.shirkanesi.professorbot.discord.command;

import java.util.ArrayList;

public interface CommandBucket extends DiscordFunction {

    void register(ArrayList<DiscordFunction> functions);

}
