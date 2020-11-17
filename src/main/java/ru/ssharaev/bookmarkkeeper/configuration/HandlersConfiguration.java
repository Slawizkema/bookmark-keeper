package ru.ssharaev.bookmarkkeeper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ssharaev.bookmarkkeeper.model.CallbackType;
import ru.ssharaev.bookmarkkeeper.model.CommandType;
import ru.ssharaev.bookmarkkeeper.service.callback.CallbackHandler;
import ru.ssharaev.bookmarkkeeper.service.command.CommandHandler;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

/**
 * @author slawi
 * @since 27.09.2020
 */
@Configuration
public class HandlersConfiguration {

    @Bean
    public Map<CommandType, CommandHandler> commandHandlerMap(Collection<CommandHandler> commandHandlerCollection) {
        return commandHandlerCollection.stream()
                .collect(toMap(CommandHandler::getType, Function.identity()));
    }

    @Bean
    public Map<CallbackType, CallbackHandler> callbackHandlerMap(Collection<CallbackHandler> callbackHandlerCollection) {
        return callbackHandlerCollection.stream()
                .collect(toMap(CallbackHandler::getCallbackType, Function.identity()));
    }
}
