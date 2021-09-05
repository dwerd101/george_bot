package ru.mephi.bot.api.facade;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.mephi.bot.cache.UserDataCache;
import ru.mephi.config.BotState;
import ru.mephi.config.context.BotStateContext;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class TelegramFacade {
    private final BotStateContext botStateContext;
    private final UserDataCache userDataCache;



    public Optional<BotApiMethod<?>> handleUpdate(Update update) {

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
            Optional<SendMessage> optionalSendMessage = handleInputMessage(message);
            if(optionalSendMessage.isPresent()) {
                return Optional.of(optionalSendMessage.get());
            }
        }

        return Optional.empty();
    }

    private Optional<SendMessage> handleInputMessage(Message message) {
        String inputMsg = message.getText();
        final Long userId = message.getFrom().getId();
        BotState botState;
        SendMessage replyMessage;

        switch (inputMsg) {
            case "/start":
                botState = BotState.NEW_USER;
                break;
            case "/Today":
            case "/today":
            case "/today@hse_ebot":
           // case "СЕГОДНЯ":
                botState = BotState.TODAY;
                break;
            case "/Tomorrow":
            case "/tomorrow":
            case "/tomorrow@hse_ebot":
                botState = BotState.TOMMOROW;
                break;
            case "/week":
            case "/week@hse_ebot":
                botState = BotState.WEEK;
                break;
            case "/wmn":
            case "/wmn@hse_ebot":
                botState = BotState.WEATHER_TODAY;
                break;
            case "/wmtodtom":
            case "/wmtodtom@hse_ebot":
                botState = BotState.WEATHER_TODAY_AND_TOMORROW;
                break;
            default:
                return Optional.empty();

        }

        userDataCache.setUsersCurrentBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);

        return Optional.of(replyMessage);
    }

    private Optional<BotApiMethod<?>> processCallbackQuery(CallbackQuery buttonQuery) {
        final long chatId = buttonQuery.getMessage().getChatId();
        final Long userId = buttonQuery.getFrom().getId();

        switch (buttonQuery.getData()) {
            case "buttonToday":
                userDataCache.getUsersCurrentBotState(userId);
                SendMessage replyMessage= botStateContext.processButton(BotState.TODAY, chatId);
                return Optional.of(replyMessage);
            case "buttonTomorrow":
                userDataCache.getUsersCurrentBotState(userId);
                replyMessage = botStateContext.processButton(BotState.TOMMOROW, chatId);
                return Optional.of(replyMessage);
            case "buttonWeek":
                userDataCache.getUsersCurrentBotState(userId);
                replyMessage = botStateContext.processButton(BotState.WEEK, chatId);
                return Optional.of(replyMessage);
            case "buttonWeatherToday":
                userDataCache.getUsersCurrentBotState(userId);
                replyMessage = botStateContext.processButton(BotState.WEATHER_TODAY, chatId);
                return Optional.of(replyMessage);
            case "buttonWeatherTodayAndTomorrow":
                userDataCache.getUsersCurrentBotState(userId);
                replyMessage = botStateContext.processButton(BotState.WEATHER_TODAY_AND_TOMORROW, chatId);
                return Optional.of(replyMessage);
        }
        return Optional.empty();
    }

}
