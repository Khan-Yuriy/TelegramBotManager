package com.telegramqbot.qbotsbot.controller;

import com.telegramqbot.qbotsbot.botapi.QBotsTelegramBot;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebhookController {
    private final QBotsTelegramBot telegramBot;

    public WebhookController(QBotsTelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update){
        return telegramBot.onWebhookUpdateReceived(update);
    }
}
