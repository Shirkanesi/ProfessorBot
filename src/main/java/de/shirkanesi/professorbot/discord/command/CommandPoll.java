package de.shirkanesi.professorbot.discord.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class CommandPoll extends Command implements GuildExecuteHandler {

    private final String[] emotes = {"1️⃣", "2️⃣", "3️⃣", "4️⃣", "5️⃣", "6️⃣", "7️⃣", "8️⃣",
            "9️⃣", "0️⃣"};

    @Override
    public String[] getAlias() {
        return new String[]{"umfrage", "poll"};
    }

    @Override
    public String getDescription() {
        return "Erstellt eine Umfrage";
    }

    @Override
    public void onExecute(GuildMessageReceivedEvent e, String[] args) {
        List<String> options = Arrays.asList(args).subList(1, args.length);
        EmbedBuilder eb = new EmbedBuilder();
        StringBuilder text = new StringBuilder();
        String question = args[0];
        for(int i = 0; i<options.size(); i++){
            if(options.get(i).length() > 0){
                text.append(emotes[i]).append(": ").append(options.get(i)).append("\n");
            }
        }
        eb.addField("Abstimmung von " + e.getMember().getEffectiveName(), question, false);
        eb.addField("Antwortmöglichkeiten:", text.toString(), false);
        e.getChannel().sendMessage(eb.build()).queue(msg ->{
            for(int i = 0; i<options.size(); i++){
                if(options.get(i).length() > 0){
                    msg.addReaction(emotes[i]).queue();
                }
            }
        });
    }

}
