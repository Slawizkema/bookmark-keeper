package ru.ssharaev.bookmarkkeeper.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ssharaev.bookmarkkeeper.model.CommandType;
import ru.ssharaev.bookmarkkeeper.service.command.CommandHandler;

import java.util.Map;

/**
 * @author slawi
 * @since 27.09.2020
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommandHandlerConfiguration {
    private final CommandHandler commandHandler;

    @Bean
    public Map<CommandType, CommandHandler> commandHandlerMap() {
        return Map.of(CommandType.ALL, commandHandler);
    }
}
