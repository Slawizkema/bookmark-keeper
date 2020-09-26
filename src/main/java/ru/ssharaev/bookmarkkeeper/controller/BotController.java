package ru.ssharaev.bookmarkkeeper.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ssharaev.bookmarkkeeper.service.TelegramUpdateHandler;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BotController {
    private final TelegramUpdateHandler updateHandler;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void onUpdate(@RequestBody Update update) {
        log.info("Получили новое сообщение!\n{}", update);
        updateHandler.handleUpdate(update);
    }

}
