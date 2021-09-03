package ru.mephi.config.bot.context;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.mephi.bot.api.handler.InputMessageHandler;
import ru.mephi.bot.api.handler.TodayHandler;
import ru.mephi.bot.api.handler.TomorrowHandler;
import ru.mephi.config.bot.BotState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {
    private Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

    public BotStateContext(List<InputMessageHandler> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    public SendMessage processInputMessage(BotState currentState, Message message) {
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
            return currentMessageHandler.handle(message);
    }
    public SendMessage processButton(BotState currentState, long chatId) {
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        if(currentState.equals(BotState.TODAY)) {
            TodayHandler todayHandler = (TodayHandler) currentMessageHandler;
            return todayHandler.handle(chatId);
        }
        else if(currentState.equals(BotState.TOMMOROW)) {
            TomorrowHandler tomorrowHandler = (TomorrowHandler) currentMessageHandler;
            return tomorrowHandler.handle(chatId);
        }
        else return null;
    }

    private InputMessageHandler findMessageHandler(BotState currentState) {
        if (isUsertTelegramState(currentState)) {
            return messageHandlers.get(BotState.NEW_USER);
        }

        return messageHandlers.get(currentState);
    }

    private boolean isUsertTelegramState(BotState currentState) {
        switch (currentState) {
            case NEW_USER:
                return true;
            default:
                return false;
        }
    }

}
