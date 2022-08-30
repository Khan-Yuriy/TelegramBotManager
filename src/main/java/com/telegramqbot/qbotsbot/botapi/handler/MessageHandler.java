package com.telegramqbot.qbotsbot.botapi.handler;

import com.telegramqbot.qbotsbot.botapi.BotState;
import com.telegramqbot.qbotsbot.cache.UserDataCache;
import com.telegramqbot.qbotsbot.dto.User;
import com.telegramqbot.qbotsbot.service.UserService;
import com.telegramqbot.qbotsbot.util.Emoji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class MessageHandler{
    @Autowired
    private UserDataCache userDataCache;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserService userService;

    public SendMessage handle(BotState currentBotState, Message message) {
        String userAnswer = message.getText();
        Long userId = message.getFrom().getId();
        Long chatId = message.getChatId();

        User user = userDataCache.getUserProfileData(userId);
        Locale locale = userDataCache.getUsersLanguage(userId);

        SendMessage replyToUser = new SendMessage(chatId.toString(),
                messageSource.getMessage("reply.mainMenu", new Emoji[]{Emoji.POINT_DOWN}, locale));

        if(currentBotState.equals(BotState.ASK_LANGUAGE)){
            replyToUser = new SendMessage(chatId.toString(),
                    messageSource.getMessage("reply.askLang", new Emoji[]{Emoji.BOT}, locale));
            replyToUser.setReplyMarkup(getInlineMessageButtons("Language", locale));
            userDataCache.setUsersCurrentBotState(userId,BotState.MAIN_MENU);
        }
        if(currentBotState.equals(BotState.ABOUT_COMPANY)){
            replyToUser = new SendMessage(chatId.toString(),
                    messageSource.getMessage("reply.choose", new Emoji[]{Emoji.POINT_DOWN}, locale));
            replyToUser.setReplyMarkup(getInlineMessageButtons("AboutCompany", locale));
        }
        if(currentBotState.equals(BotState.ABOUT_BOTS)){
            replyToUser = new SendMessage(chatId.toString(),
                    messageSource.getMessage("reply.aboutBots",
                            new Emoji[]{Emoji.PHONE_RECEIVER, Emoji.CLIPBOARD, Emoji.BOOKMARK_TABS, Emoji.CREDIT_CARD, Emoji.DESKTOP},
                            locale));
        }
        if(currentBotState.equals(BotState.ASK_NAME)){
            replyToUser = new SendMessage(chatId.toString(),
                    messageSource.getMessage("reply.orderCall", new Emoji[]{Emoji.PEN}, locale));
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_NUMBER);

        }
        if(currentBotState.equals(BotState.ASK_NUMBER)){
            user.setName(userAnswer);
            replyToUser = new SendMessage(chatId.toString(),
                    messageSource.getMessage("reply.askPhone", new Emoji[]{Emoji.PHONE}, locale));
            userDataCache.setUsersCurrentBotState(userId, BotState.FILLING_PROFILE);

        }
        if(currentBotState.equals(BotState.FILLING_PROFILE)){
            if(userAnswer.matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")){
                user.setPhone(userAnswer);
                this.userService.addUser(user);
                replyToUser = new SendMessage(chatId.toString(),
                        messageSource.getMessage("reply.filled", new Emoji[]{Emoji.CHECK_MARK}, locale));
                userDataCache.setUsersCurrentBotState(userId, BotState.MAIN_MENU);
            }
            else{
                replyToUser = new SendMessage(chatId.toString(),
                        messageSource.getMessage("reply.phoneValidation",  new Emoji[]{Emoji.WARNING}, locale) + "\n\n" +
                                messageSource.getMessage("reply.askPhone",  new Emoji[]{Emoji.PHONE}, locale));
            }
        }
        if(currentBotState.equals(BotState.MAIN_MENU)){
            replyToUser = new SendMessage(chatId.toString(),
                    messageSource.getMessage("reply.mainMenu", new Emoji[]{Emoji.POINT_DOWN}, locale));
        }

        return replyToUser;
    }

    private InlineKeyboardMarkup getInlineMessageButtons(String variant, Locale locale) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        if(variant.equals("Language")){
            button1.setText("Қазақ тілінде " + Emoji.KZ);
            button2.setText("На русском языке " + Emoji.RU);

            button1.setCallbackData("buttonKz");
            button2.setCallbackData("buttonRu");

            List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
            keyboardButtonsRow1.add(button1);
            keyboardButtonsRow1.add(button2);

            rowList.add(keyboardButtonsRow1);
        }
        if(variant.equals("AboutCompany")){
            button1.setText(messageSource.getMessage("button.aboutUs", null, locale));
            button2.setText(messageSource.getMessage("button.news", null, locale));
            button2.setUrl("https://www.qbots.kz/");

            button1.setCallbackData("buttonAboutUs");
            button2.setCallbackData("buttonNews");

            List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
            keyboardButtonsRow1.add(button1);
            List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
            keyboardButtonsRow2.add(button2);

            rowList.add(keyboardButtonsRow1);
            rowList.add(keyboardButtonsRow2);
        }

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

}
