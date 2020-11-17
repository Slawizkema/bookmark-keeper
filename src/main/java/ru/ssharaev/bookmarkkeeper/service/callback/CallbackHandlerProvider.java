package ru.ssharaev.bookmarkkeeper.service.callback;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ssharaev.bookmarkkeeper.model.CallbackType;

import java.util.Map;

/**
 * @author slawi
 * @since 17.11.2020
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CallbackHandlerProvider {
    private final Map<CallbackType, CallbackHandler> callbackHandlerMap;

    public CallbackHandler getCallbackHandler(CallbackType callbackType) {
        return callbackHandlerMap.get(callbackType);
    }
}
