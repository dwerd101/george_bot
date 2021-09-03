package ru.mephi.bot.api.handler.impl;/*
package ru.mephi.bot.api.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.mephi.config.bot.BotState;
import ru.mephi.bot.api.handler.InputMessageHandler;
import ru.mephi.bot.messages.MessagesForReply;
import ru.mephi.bot.cache.UserDataCache;
import ru.mephi.service.ReplyMessagesService;

@Component
@AllArgsConstructor
public class ErrorHandler implements InputMessageHandler {
    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;
    private MessagesForReply messagesForReply;
    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ERROR;
    }
    private SendMessage processUsersInput(Message inputMsg) {
        int userId = inputMsg.getFrom().getId().intValue();
        long chatId = inputMsg.getChatId();
        SendMessage replyToUser = messagesService.getReplyMessage(chatId, messagesForReply.getErrorMessage());
        userDataCache.setUsersCurrentBotState(userId,BotState.NEW_USER);

        return replyToUser;
    }
}
*/
