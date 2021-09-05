package ru.mephi.bot.api.handler.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.mephi.bot.api.handler.TodayHandler;
import ru.mephi.bot.convert.TimeTableInToString;
import ru.mephi.bot.convert.TimeTableToday;
import ru.mephi.config.BotState;
import ru.mephi.service.ReplyMessagesService;

@Component
@AllArgsConstructor

public class TodayHandlerImpl implements TodayHandler {

    private final ReplyMessagesService messagesService;
    private final TimeTableToday timeTableToday;
    private final InlineKeyboardMarkup inlineMessageButtons;
    private final TimeTableInToString timeTableInString;


    @Override
    public SendMessage handle(Message inputMsg) {
       final long chatId = inputMsg.getChatId();
        String listTimeTableOfString = timeTableInString.getListTimeTableOfString(timeTableToday.getTimeTodayTable());
        SendMessage replyToUser = messagesService.getReplyMessage(chatId, listTimeTableOfString);
        replyToUser.setReplyMarkup(inlineMessageButtons);
        return replyToUser;

    }

    public SendMessage handle(long chatId) {
        String listTimeTableOfString = timeTableInString.getListTimeTableOfString(timeTableToday.getTimeTodayTable());
        SendMessage replyToUser = messagesService.getReplyMessage(chatId, listTimeTableOfString);
        replyToUser.setReplyMarkup(inlineMessageButtons);
        return replyToUser;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.TODAY;
    }


}

