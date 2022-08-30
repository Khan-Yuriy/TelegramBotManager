package com.telegramqbot.qbotsbot.botapi;

import com.telegramqbot.qbotsbot.botapi.handler.CallbackQueryHandler;
import com.telegramqbot.qbotsbot.botapi.handler.MessageHandler;
import com.telegramqbot.qbotsbot.cache.UserDataCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramFacade {
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private CallbackQueryHandler callbackQueryHandler;
    @Autowired
    private UserDataCache userDataCache;

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyMessage = null;

        if(update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackQueryHandler.handleCallbackQuery(callbackQuery);
        }

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            replyMessage = handleInputMessage(message);
            System.out.println(message);
        }

        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMessage = message.getText();
        Long userId = message.getFrom().getId();
        BotState botState;
        SendMessage replyMessage;

        switch (inputMessage) {
            case "/start":
                botState = BotState.ASK_LANGUAGE;
                break;
            case "О компании":
            case "Компания туралы":
                botState = BotState.ABOUT_COMPANY;
                break;
            case "О чат-ботах":
            case "Чат-боттар туралы":
                botState = BotState.ABOUT_BOTS;
                break;
            case "Заказать обратный звонок":
            case "Қайта қоңырау шалуды сұраңыз":
                botState = BotState.ASK_NAME;
                break;
            default:
                botState = userDataCache.getUsersCurrentBotState(userId);
                break;
        }

        userDataCache.setUsersCurrentBotState(userId, botState);
        replyMessage = messageHandler.handle(botState, message);

        return replyMessage;
    }
}
