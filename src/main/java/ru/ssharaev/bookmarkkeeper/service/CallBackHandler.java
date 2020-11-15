package ru.ssharaev.bookmarkkeeper.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ssharaev.bookmarkkeeper.model.CallBackData;
import ru.ssharaev.bookmarkkeeper.service.bookmark.BookmarkSaveService;

/**
 * @author slawi
 * @since 15.11.2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CallBackHandler {
    private final BookmarkSaveService bookmarkSaveService;
    private final ObjectMapper objectMapper;

    public void handleCallBack(CallbackQuery callbackQuery) {
        try {
            CallBackData callBackData = objectMapper.readValue(callbackQuery.getData(), CallBackData.class);
            bookmarkSaveService.updateBookmarkCategory(callbackQuery.getMessage(), callBackData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
