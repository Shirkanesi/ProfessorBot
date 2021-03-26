package de.shirkanesi.professorbot.discord.command;

import de.shirkanesi.professorbot.datafile.DataManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class QuoteCommand extends Command implements GuildExecuteHandler {

    DataManager quoteData = new DataManager("zitate.txt");

    @Override
    public String[] getAlias() {
        return new String[]{"quote", "zitat"};
    }

    @Override
    public String getDescription() {
        return "Liefert ein schönes Zitat von Miles.";
    }

    @Override
    public void onExecute(GuildMessageReceivedEvent e, String[] args) {
        e.getMessage().delete().queue();
        if (args.length == 0) {
            String q = randomQuote();
            EmbedBuilder b = new EmbedBuilder();
            // b.setAuthor("Miles", "https://www.professorofmagic.de//", "https://cdn.discordapp.com/avatars/691634477138444298/df6abcc1310ffb4e07b81ee5309525de.webp?size=256");
            b.setThumbnail(
                    "https://cdn.discordapp.com/avatars/691634477138444298/df6abcc1310ffb4e07b81ee5309525de.webp?size=512");

            b.addField("Miles sagt:", q, true);
            b.setColor(Color.ORANGE);
            e.getChannel().sendMessage(b.build()).queue(msg -> {
                msg.addReaction("U+1F44D").queue();
                msg.addReaction("U+1F44E").queue();
            });
        } else {
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "add":
                    if (args.length >= 2) {
                        StringBuilder newQuote = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            newQuote.append(args[i]).append(" ");
                        }
                        quoteData.add(newQuote.toString().trim());
                    }
                    break;
                case "rl":
                    quoteData.read();
                    break;
                default:
                    break;
            }
        }
    }

    private String randomQuote() {
        List<String> quotes = this.quoteData.getData();
        int rand = new Random().nextInt(quotes.size());
        Collections.shuffle(quotes);
        return quotes.get(rand);
    }


}


