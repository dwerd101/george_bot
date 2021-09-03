package ru.mephi.bot.api.handler.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.mephi.bot.api.handler.InputMessageHandler;
import ru.mephi.bot.cache.UserDataCache;
import ru.mephi.bot.messages.MessagesForReply;
import ru.mephi.config.bot.BotState;
import ru.mephi.service.ReplyMessagesService;

@Slf4j
@Component
@AllArgsConstructor
public class NewUserHandlerImpl implements InputMessageHandler {
    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;
    private MessagesForReply messagesForReply;
    private final InlineKeyboardMarkup inlineMessageButtons;

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.NEW_USER;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        int userId = inputMsg.getFrom().getId().intValue();
        long chatId = inputMsg.getChatId();

        SendMessage replyToUser = messagesService.getReplyMessage(chatId, messagesForReply.getHello());
        replyToUser.setReplyMarkup(inlineMessageButtons);
        userDataCache.setUsersCurrentBotState(userId,BotState.NEW_USER);

        return replyToUser;
    }


}
