package com.telegramqbot.qbotsbot.botapi.handler;

import com.telegramqbot.qbotsbot.botapi.BotState;
import com.telegramqbot.qbotsbot.cache.UserDataCache;
import com.telegramqbot.qbotsbot.dto.User;
import com.telegramqbot.qbotsbot.service.MainMenuService;
import com.telegramqbot.qbotsbot.util.Emoji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Locale;

@Component
public class CallbackQueryHandler {
    @Autowired
    private UserDataCache userDataCache;
    @Autowired
    private MainMenuService mainMenuService;
    @Autowired
    private MessageSource messageSource;

    public BotApiMethod<?> handleCallbackQuery(CallbackQuery buttonQuery) {
        final Long chatId = buttonQuery.getMessage().getChatId();
        final Long userId = buttonQuery.getFrom().getId();

        User user = userDataCache.getUserProfileData(userId);

        Locale locale = Locale.forLanguageTag("ru-RU");
        BotApiMethod<?> callBackAnswer = new SendMessage(chatId.toString(),
                messageSource.getMessage("reply.askLang", null, locale));

        if (buttonQuery.getData().equals("buttonKz")) {
            userDataCache.setUsersCurrentBotState(userId, BotState.MAIN_MENU);
            userDataCache.saveUserProfileData(userId, user);
            userDataCache.setUsersLanguage(userId, Locale.forLanguageTag("kz-KZ"));

            callBackAnswer = mainMenuService.getMainMenuMessage(chatId, Locale.forLanguageTag("kz-KZ"),
                    messageSource.getMessage("reply.mainMenu", new Emoji[]{Emoji.POINT_DOWN}, Locale.forLanguageTag("kz-KZ")));

        } else if (buttonQuery.getData().equals("buttonRu")) {
            userDataCache.setUsersCurrentBotState(userId, BotState.MAIN_MENU);
            userDataCache.saveUserProfileData(userId, user);
            userDataCache.setUsersLanguage(userId, Locale.forLanguageTag("ru-RU"));

            callBackAnswer = mainMenuService.getMainMenuMessage(chatId, Locale.forLanguageTag("ru-RU"),
                    messageSource.getMessage("reply.mainMenu", new Emoji[]{Emoji.POINT_DOWN}, Locale.forLanguageTag("ru-RU")));

        } else if(buttonQuery.getData().equals("buttonAboutUs")){
            userDataCache.setUsersCurrentBotState(userId, BotState.MAIN_MENU);
            callBackAnswer = new SendMessage(chatId.toString(),
                    messageSource.getMessage("reply.aboutCompany",
                            new Emoji[]{Emoji.OFFICE, Emoji.BULB, Emoji.ROAD, Emoji.CLIPBOARD,
                                    Emoji.EMAIL, Emoji.WEBSITE, Emoji.PHONE_RECEIVER},
                            userDataCache.getUsersLanguage(userId)));

        }
        else {
            userDataCache.setUsersCurrentBotState(userId, BotState.MAIN_MENU);
        }

        return callBackAnswer;
    }
}
