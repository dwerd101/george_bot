package ru.mephi.bot.messages;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class MessagesForReply {

    @Value("${message.hello}")
    private  String hello;
    @Value("${message.error}")
    private String errorMessage;

}
