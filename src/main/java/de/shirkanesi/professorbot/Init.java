package de.shirkanesi.professorbot;

import club.minnced.discord.webhook.WebhookClient;
import de.shirkanesi.professorbot.console.ConsoleManager;
import de.shirkanesi.professorbot.voice.AudioManager;
import de.shirkanesi.professorbot.discord.CommandManager;
import de.shirkanesi.professorbot.discord.DiscordController;
import de.shirkanesi.professorbot.settings.SettingsController;

import static de.shirkanesi.professorbot.ButterBrot.LOGGER;

public class Init {

    static void preInit() throws Exception {
        LOGGER.info("Starting pre-init state");
        new SettingsController(ButterBrot.SETTINGS_FILE);
        LOGGER.info("Passed pre-init state");
    }

    static void init() throws Exception {
        LOGGER.info("Starting init state");
        new DiscordController();
        ButterBrot.WEBHOOK_CLIENT = WebhookClient
                .withId(820952563951861791L, "Gs6s8psx_wOwsuA0s-qhpQmJy2H84DFZ-W5sxnEf1TpcIc04zzOc8SSM5jn27JRW2wCk");
        LOGGER.info("Passed init state");
    }

    static void postInit() throws Exception {
        LOGGER.info("Starting post-init state");
        new CommandManager();
        new AudioManager();
        LOGGER.info("Passed post-init state");
    }

    static void startupComplete() throws Exception {
        new ConsoleManager();
        LOGGER.info("Startup complete");
    }

    static void addShutdownHook() throws Exception {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> stopBot(false)));
    }

    static boolean shutdown = false;
    public static void stopBot(boolean systemExit){
        if(shutdown)
            return;

        DiscordController.getJDA().shutdown();
        shutdown = true;

        LOGGER.info("Good Bye! :c");
        if(systemExit) System.exit(0);
    }

}
