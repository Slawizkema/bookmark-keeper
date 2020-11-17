package ru.ssharaev.bookmarkkeeper.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ssharaev.bookmarkkeeper.model.CommandType;

import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommandHandlerProvider {
    private final Map<CommandType, CommandHandler> commandHandlerMap;

    public CommandHandler getCommandHandler(CommandType commandType) {
        return commandHandlerMap.get(commandType);
    }
}
