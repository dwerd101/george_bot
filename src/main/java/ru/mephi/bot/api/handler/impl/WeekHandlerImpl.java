package ru.mephi.bot.api.handler.impl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.mephi.bot.api.handler.WeekHandler;
import ru.mephi.bot.convert.TimeTableWeekToString;
import ru.mephi.config.BotState;
import ru.mephi.service.ReplyMessagesService;

@Component
@AllArgsConstructor
@Slf4j
public class WeekHandlerImpl implements WeekHandler {
    private final ReplyMessagesService messagesService;
    private final TimeTableWeekToString timeTableWeekToString;
    private final InlineKeyboardMarkup inlineMessageButtons;
    @Override
    public SendMessage handle(Message message) {
        long chatId = message.getChatId();
        String listTimeTableOfString = timeTableWeekToString.getTimeTableWeekToString();
        SendMessage replyToUser = messagesService.getReplyMessage(chatId, listTimeTableOfString);
        replyToUser.setReplyMarkup(inlineMessageButtons);
        return replyToUser;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.WEEK;
    }

    @Override
    public SendMessage handle(long chatId) {
        String listTimeTableOfString =  timeTableWeekToString.getTimeTableWeekToString();
        SendMessage replyToUser = messagesService.getReplyMessage(chatId, listTimeTableOfString);
        replyToUser.setReplyMarkup(inlineMessageButtons);
        return replyToUser;
    }
}

