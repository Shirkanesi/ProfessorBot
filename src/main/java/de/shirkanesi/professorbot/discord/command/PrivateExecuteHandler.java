package de.shirkanesi.professorbot.discord.command;

import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

public interface PrivateExecuteHandler {

    void onPrivateExecute(PrivateMessageReceivedEvent e, String[] args);

}
