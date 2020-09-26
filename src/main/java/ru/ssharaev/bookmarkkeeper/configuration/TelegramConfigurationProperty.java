package ru.ssharaev.bookmarkkeeper.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "telegram")
public class TelegramConfigurationProperty {
    String botName;
    String token;
    String botPath;
}
