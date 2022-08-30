package com.telegramqbot.qbotsbot.util;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Emoji {
    BOT(EmojiParser.parseToUnicode(":robot:")),
    POINT_DOWN(EmojiParser.parseToUnicode(":point_down:")),
    KZ(EmojiParser.parseToUnicode(":kz:")),
    RU(EmojiParser.parseToUnicode(":ru:")),
    PEN(EmojiParser.parseToUnicode(":lower_left_ballpoint_pen:")),
    PHONE(EmojiParser.parseToUnicode(":iphone:")),
    WARNING(EmojiParser.parseToUnicode(":warning:")),
    CHECK_MARK(EmojiParser.parseToUnicode(":white_check_mark:")),
    PHONE_RECEIVER(EmojiParser.parseToUnicode(":telephone_receiver:")),
    CLIPBOARD(EmojiParser.parseToUnicode(":clipboard:")),
    BOOKMARK_TABS(EmojiParser.parseToUnicode(":bookmark_tabs:")),
    CREDIT_CARD(EmojiParser.parseToUnicode(":credit_card:")),
    DESKTOP(EmojiParser.parseToUnicode(":desktop_computer:")),
    OFFICE(EmojiParser.parseToUnicode(":office:")),
    ROAD(EmojiParser.parseToUnicode(":motorway:")),
    EMAIL(EmojiParser.parseToUnicode(":email:")),
    WEBSITE(EmojiParser.parseToUnicode(":globe_with_meridians:")),
    BULB(EmojiParser.parseToUnicode(":bulb:"));


    private String emojiName;

    @Override
    public String toString(){
        return emojiName;
    }
}
