package com.telegramqbot.qbotsbot.config;

import com.telegramqbot.qbotsbot.botapi.QBotsTelegramBot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Getter
@Setter
@Configuration
public class BotConfig {
    @Value("${telegram.webhookPath}")
    private String webhookPath;
    @Value("${telegram.botName}")
    private String botName;
    @Value("${telegram.botToken}")
    private String botToken;

    @Value("${telegram.proxyType}")
    private DefaultBotOptions.ProxyType proxyType;
    @Value("${telegram.proxyHost}")
    private String proxyHost;
    @Value("${telegram.proxyPort}")
    private int proxyPort;

    @Bean
    public QBotsTelegramBot qBotsTelegramBot(){
        DefaultBotOptions options = new DefaultBotOptions();
        options.setProxyType(proxyType);
        options.setProxyHost(proxyHost);
        options.setProxyPort(proxyPort);

        QBotsTelegramBot qBotsTelegramBot = new QBotsTelegramBot(options);
        qBotsTelegramBot.setBotName(botName);
        qBotsTelegramBot.setBotToken(botToken);
        qBotsTelegramBot.setWebhookPath(webhookPath);

        return qBotsTelegramBot;
    }

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }
}
