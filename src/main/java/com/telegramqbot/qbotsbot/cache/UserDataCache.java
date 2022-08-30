package com.telegramqbot.qbotsbot.cache;

import com.telegramqbot.qbotsbot.botapi.BotState;
import com.telegramqbot.qbotsbot.dto.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class UserDataCache implements DataCache {
    private Map<Long, BotState> usersBotStates = new HashMap<>();
    private Map<Long, User> usersProfileData = new HashMap<>();
    private Map<Long, Locale> usersLanguage = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(Long userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUsersCurrentBotState(Long userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null) {
            botState = BotState.ASK_LANGUAGE;
        }

        return botState;
    }

    @Override
    public User getUserProfileData(Long userId) {
        User userProfileData = usersProfileData.get(userId);
        if (userProfileData == null) {
            userProfileData = new User();
        }
        return userProfileData;
    }

    @Override
    public void saveUserProfileData(Long userId, User userProfileData) {
        usersProfileData.put(userId, userProfileData);
    }

    @Override
    public void setUsersLanguage(Long userId, Locale locale) {
        usersLanguage.put(userId, locale);
    }

    @Override
    public Locale getUsersLanguage(Long userId) {
        Locale locale = usersLanguage.get(userId);
        if (locale == null) {
            locale = Locale.forLanguageTag("ru-RU");
        }

        return locale;
    }
}
