package ru.mephi.bot.api.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface WeatherTodayHandler extends InputMessageHandler{
    SendMessage handle(long chatId);
}
