package ru.ssharaev.bookmarkkeeper.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ssharaev.bookmarkkeeper.exception.BookmarkKeeperException;
import ru.ssharaev.bookmarkkeeper.model.CommandType;

import java.util.NoSuchElementException;

import static ru.ssharaev.bookmarkkeeper.TelegramMessageUtils.fetchEntityValue;

/**
 * @author slawi
 * @since 27.09.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommandService {
    private final CommandHandlerProvider commandHandlerProvider;

    public void handleCommand(Update update) throws BookmarkKeeperException {
        CommandType command = fetchCommand(update.getMessage());
        log.info("Execute command {}", command);
        commandHandlerProvider.getCommandHandler(command).handleCommand(update);
    }

    private CommandType fetchCommand(Message message) throws BookmarkKeeperException {
        if (!message.hasEntities()) {
            throw new BookmarkKeeperException("В сообщении нет команды!");
        }
        try {
            String botCommand = fetchEntityValue(message, EntityType.BOTCOMMAND)
                    .substring(1)   //Команды начинаются с символа "/", обрезаем его
                    .toUpperCase();
            return CommandType.valueOf(botCommand);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            throw new BookmarkKeeperException("В сообщении нет команды!", e);
        }
    }

}
