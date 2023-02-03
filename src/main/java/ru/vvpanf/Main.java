package ru.vvpanf;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.Webhook;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultWebhook;
import ru.vvpanf.bot.HelloBot;

import java.io.File;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            Config config = ConfigFactory.load("app.conf").resolve().getConfig("hello-bot");

            Webhook webhook = new DefaultWebhook();
            webhook.setInternalUrl(config.getString("webhook.internalUrl"));

            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class, webhook);
            HelloBot helloBot = new HelloBot(
                    config.getString("bot.username"),
                    config.getString("bot.token"),
                    config.getString("bot.path")
            );

            SetWebhook setWebhook = new SetWebhook(config.getString("webhook.url"));
            File cert = new File(config.getString("webhook.cert"));
            if (cert.exists()) {
                log.debug("Cert file found!");
            } else {
                log.debug("Cert file not found!");
            }
            setWebhook.setCertificate(new InputFile(cert));

            telegramBotsApi.registerBot(helloBot, setWebhook);
        } catch (TelegramApiException e) {
            log.info(e.getMessage());
        }
    }
}