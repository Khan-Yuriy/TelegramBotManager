package com.telegramqbot.qbotsbot.cache;

import com.telegramqbot.qbotsbot.botapi.BotState;
import com.telegramqbot.qbotsbot.dto.User;

import java.util.Locale;

public interface DataCache {
    void setUsersCurrentBotState(Long userId, BotState botState);

    BotState getUsersCurrentBotState(Long userId);

    User getUserProfileData(Long userId);

    void saveUserProfileData(Long userId, User userProfileData);

    void setUsersLanguage(Long userId, Locale locale);

    Locale getUsersLanguage(Long userId);
}
