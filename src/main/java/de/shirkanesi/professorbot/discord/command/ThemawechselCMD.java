package de.shirkanesi.professorbot.discord.command;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import de.shirkanesi.professorbot.ButterBrot;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.Color;
import java.util.List;

public class ThemawechselCMD extends Command implements GuildExecuteHandler {
    @Override
    public String[] getAlias() {
        return new String[]{"themawechsel", "tw"};
    }

    @Override
    public String getDescription() {
        return "Lässt Tesi (aka Teresatesasesa) einen Themawechsel vorschlagen.";
    }

    @Override
    public void onExecute(GuildMessageReceivedEvent e, String[] args) {
        e.getMessage().delete().queue();

        WebhookMessageBuilder builder = new WebhookMessageBuilder();
        builder.setUsername("Tesi");
        builder.setAvatarUrl("https://professors-laboratory.com/rsc/dc/Theresa.PNG");

        WebhookEmbedBuilder embedBuilder = new WebhookEmbedBuilder();
        embedBuilder.setImageUrl("https://professors-laboratory.com/rsc/dc/Themawechsel.png");
        embedBuilder.setColor(Color.ORANGE.getRGB());
        builder.addEmbeds(embedBuilder.build());

        try {
            Webhook webhook;
            List<Webhook> channelWebhooks = e.getChannel().retrieveWebhooks().complete();
            if (channelWebhooks.size() > 0) {
                webhook = channelWebhooks.stream().findAny().get();
            } else {
                webhook = e.getChannel().createWebhook("Tesi").complete();
            }
            if (webhook.getToken() != null) {
                WebhookClient client = WebhookClient.withId(webhook.getIdLong(), webhook.getToken());
                client.send(builder.build());
            } else {
                ButterBrot.LOGGER.warn("Webhook could not be created!");
            }
        } catch (AssertionError e1) {
            ButterBrot.LOGGER.warn(String.format("Error while sending WebHookMessage [%s]", e1.getMessage()));
        }
    }
}
