package de.shirkanesi.professorbot.discord.command;

public abstract class Command implements DiscordFunction {

    public abstract String[] getAlias();
    public abstract String getDescription();

}
