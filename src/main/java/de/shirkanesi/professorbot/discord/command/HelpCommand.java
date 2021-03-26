package de.shirkanesi.professorbot.discord.command;

import de.shirkanesi.professorbot.util.EmbedUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import de.shirkanesi.professorbot.discord.CommandManager;

public class HelpCommand extends LegacyCommand {
    @Override
    public String[] getAlias() {
        return new String[]{"help", "list"};
    }

    @Override
    public void onExecute(GuildMessageReceivedEvent e, String[] args) {
        EmbedBuilder b = EmbedUtil.createSuccessEmbed();
        b.setDescription("Folgende Commands sind implementiert und nach einem Prefix ('pbot') " +
                "geschrieben, aufrufbar");
        for (Command command : CommandManager.getCommands()) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < command.getAlias().length; i++) {
                builder.append(command.getAlias()[i]);
                if (i != command.getAlias().length-1) builder.append(" | ");
            }
            b.addField(builder.toString(), (command.getDescription() == null ? "" : command.getDescription()), false);
        }
        e.getChannel().sendMessage(b.build()).queue();
    }

    @Override
    public String getDescription() {
        return "Listet alle implementierten Commands auf";
    }
}
