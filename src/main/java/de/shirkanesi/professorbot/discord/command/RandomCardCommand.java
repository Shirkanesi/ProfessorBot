package de.shirkanesi.professorbot.discord.command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomCardCommand extends Command implements GuildExecuteHandler, PrivateExecuteHandler {

    List<Character> symbols = Arrays.asList('\u2660', '\u2666', '\u2663', '\u2665');
    List<String> values = Arrays.asList("Ass", "Eins", "Zwei", "Drei", "Vier", "Fünf", "Sechs", "Sieben", "Acht", "Neun", "Zehn",
            "Bube", "Dame", "König");

    @Override
    public String[] getAlias() {
        return new String[]{"randomcard", "rc"};
    }

    @Override
    public String getDescription() {
        return "Gibt eine zufällige Karte aus.";
    }

    @Override
    public void onExecute(GuildMessageReceivedEvent e, String[] args) {
        e.getMessage().delete().queue();
        answer(e.getChannel());
    }

    @Override
    public void onPrivateExecute(PrivateMessageReceivedEvent e, String[] args) {
        answer(e.getChannel());
    }

    private void answer(MessageChannel channel) {
        EmbedBuilder b = new EmbedBuilder();
        b.addField("Zufällige Karte:", randomCard(), true);
        channel.sendMessage(b.build()).queue();
    }

    private String randomCard() {
        Random r = new Random();
        Collections.shuffle(symbols);
        Collections.shuffle(values);
        char symbol = symbols.get(r.nextInt(symbols.size()));
        String value = values.get(r.nextInt(values.size()));
        return symbol + " " + value;
    }

}
