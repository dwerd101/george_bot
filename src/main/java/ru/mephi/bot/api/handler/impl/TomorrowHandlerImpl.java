package ru.mephi.bot.api.handler.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.mephi.bot.api.handler.TomorrowHandler;
import ru.mephi.bot.convert.TimeTableInToString;
import ru.mephi.bot.convert.TimeTableTomorrow;
import ru.mephi.config.BotState;
import ru.mephi.service.ReplyMessagesService;

@Component
@AllArgsConstructor
public class TomorrowHandlerImpl implements TomorrowHandler {
    private final ReplyMessagesService messagesService;
    private final TimeTableTomorrow timeTableTomorrow;
    private final InlineKeyboardMarkup inlineMessageButtons;
    private final TimeTableInToString timeTableInString;


    @Override
    public SendMessage handle(Message message) {
        final long chatId = message.getChatId();
        String listTimeTableOfString = timeTableInString.getListTimeTableOfString(timeTableTomorrow.getTimeTomorrowTable());
        SendMessage replyToUser = messagesService.getReplyMessage(chatId, listTimeTableOfString);
        replyToUser.setReplyMarkup(inlineMessageButtons);
        return replyToUser;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.TOMMOROW;
    }

    @Override
    public SendMessage handle(final long chatId) {
        String listTimeTableOfString = timeTableInString.getListTimeTableOfString(timeTableTomorrow.getTimeTomorrowTable());
        SendMessage replyToUser = messagesService.getReplyMessage(chatId, listTimeTableOfString);
        replyToUser.setReplyMarkup(inlineMessageButtons);
        return replyToUser;
    }


}
