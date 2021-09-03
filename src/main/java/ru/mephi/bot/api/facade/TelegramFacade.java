package ru.mephi.bot.api.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.mephi.bot.cache.UserDataCache;
import ru.mephi.config.bot.BotState;
import ru.mephi.config.bot.context.BotStateContext;

@Slf4j
@Component
public class TelegramFacade {
    private BotStateContext botStateContext;
    private UserDataCache userDataCache;

    public TelegramFacade(BotStateContext botStateContext, UserDataCache userDataCache) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyMessage = null;

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data1: {}", update.getCallbackQuery().getFrom().getUserName(),
                    callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            return processCallbackQuery(callbackQuery);
        }

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("New message from User:{}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        int userId = message.getFrom().getId().intValue();
        BotState botState;
        SendMessage replyMessage;
        //    LocalDateTime localDateTime = LocalDateTime.now();

        switch (inputMsg) {
            case "/start":
                botState = BotState.NEW_USER;
                break;
            case "Today":
            case "today":
           // case "СЕГОДНЯ":
                botState = BotState.TODAY;
                break;
            case "Tomorrow":
            case "tomorrow":
                botState = BotState.TOMMOROW;
                break;
           /* case "Week":
            case "week":
                botState = BotState.WEEK;
                break;*/
            default:
                //  botState = userDataCache.getUsersCurrentBotState(userId);
               // botState = BotState.ERROR;
                return null;
                //break;
        }

        userDataCache.setUsersCurrentBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }

    private BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final long chatId = buttonQuery.getMessage().getChatId();
        final int userId = buttonQuery.getFrom().getId().intValue();
        SendMessage replyMessage = null;

        if (buttonQuery.getData().equals("buttonToday")) {
            userDataCache.getUsersCurrentBotState(userId);
            replyMessage = botStateContext.processButton(BotState.TODAY, chatId);

        }
        else if(buttonQuery.getData().equals("buttonTomorrow")) {
            userDataCache.getUsersCurrentBotState(userId);
            replyMessage = botStateContext.processButton(BotState.TOMMOROW, chatId);
        }
        return replyMessage;
    }

}
