package ru.vvpanf.bot;

import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HelloBot extends TelegramWebhookBot {
    private final String botUsername;
    private final String botToken;
    private final String botPath;

    public HelloBot(String botUsername, String botToken, String botPath) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.botPath = botPath;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return botPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("Hello!")
                .build();
    }
}
