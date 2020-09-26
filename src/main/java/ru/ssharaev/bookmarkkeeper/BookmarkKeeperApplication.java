package ru.ssharaev.bookmarkkeeper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.telegram.telegrambots.ApiContextInitializer;
import ru.ssharaev.bookmarkkeeper.configuration.TelegramConfigurationProperty;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(TelegramConfigurationProperty.class)
public class BookmarkKeeperApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(BookmarkKeeperApplication.class, args);
    }

}
