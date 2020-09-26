package ru.ssharaev.bookmarkkeeper.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import ru.ssharaev.bookmarkkeeper.telegram.Bot;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TelegramBotProvider {
    private final TelegramConfigurationProperty telegramConfigurationProperty;

    @Bean
    public WebhookBot bot() {
        Bot bot = new Bot(telegramConfigurationProperty.getBotName(), telegramConfigurationProperty.getToken(), telegramConfigurationProperty.getBotPath());
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
        return bot;
    }
}
