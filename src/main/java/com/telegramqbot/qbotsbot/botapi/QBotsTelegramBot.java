package com.telegramqbot.qbotsbot.botapi;

import com.telegramqbot.qbotsbot.botapi.TelegramFacade;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Setter
public class QBotsTelegramBot extends TelegramWebhookBot {
    private String webhookPath;
    private String botName;
    private String botToken;

    @Autowired
    private TelegramFacade telegramFacade;

    public QBotsTelegramBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return telegramFacade.handleUpdate(update);
    }

    @Override
    public String getBotPath() {
        return webhookPath;
    }
}
