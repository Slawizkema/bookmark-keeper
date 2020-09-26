package ru.ssharaev.bookmarkkeeper.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ssharaev.bookmarkkeeper.model.CommandType;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommandHandlerProvider {
    private final Map<CommandType, Update> handlerMap = new HashMap<>();
}
