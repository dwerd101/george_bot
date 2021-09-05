package ru.mephi.bot.api.handler.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.mephi.bot.api.handler.WeatherTodayHandler;
import ru.mephi.bot.api.weather.yandex.Condition;
import ru.mephi.bot.api.weather.yandex.Fact;
import ru.mephi.bot.api.weather.yandex.Weather;
import ru.mephi.bot.api.weather.yandex.YandexWeatherApi;
import ru.mephi.config.BotState;
import ru.mephi.service.ReplyMessagesService;

import java.util.Locale;

@Component
@AllArgsConstructor
public class WeatherTodayHandlerImpl implements WeatherTodayHandler {
    private final ReplyMessagesService messagesService;
    private final InlineKeyboardMarkup inlineMessageButtons;
    private final YandexWeatherApi yandexWeatherApi;

    @Override
    @SneakyThrows
    public SendMessage handle(Message message) {
        final long chatId = message.getChatId();
        Weather weather = getWeatherMoscowFromYandexApi();
        Fact fact = weather.getFact();
        String weatherInStringResult = getWeatherMoscowNowFromYandexApiMessage(fact,weather);
        SendMessage replyToUser = messagesService.getReplyMessage(chatId, weatherInStringResult);
        replyToUser.setReplyMarkup(inlineMessageButtons);
        return replyToUser;

    }


    @Override
    public BotState getHandlerName() {
        return BotState.WEATHER_TODAY;
    }

    @SneakyThrows
    @Override
    public SendMessage handle(final long chatId) {
        Weather weather = getWeatherMoscowFromYandexApi();
        Fact fact = weather.getFact();
        String weatherInStringResult  = getWeatherMoscowNowFromYandexApiMessage(fact,weather);
        SendMessage replyToUser = messagesService.getReplyMessage(chatId, weatherInStringResult);
        replyToUser.setReplyMarkup(inlineMessageButtons);
        return replyToUser;
    }
    private Weather getWeatherMoscowFromYandexApi() {
       return WebClient.create().get()
                .uri(yandexWeatherApi.getYandexApiMoscowUrl())
                .header(yandexWeatherApi.getYandexApiHeaderKey(), yandexWeatherApi.getYandexApiHeaderValue())
                .exchange()
                .block()
                .bodyToMono(Weather.class)
                .block();
    }
    private String getWeatherMoscowNowFromYandexApiMessage(Fact fact, Weather weather) {
        StringBuilder weatherStringBuilder = new StringBuilder();
        weatherStringBuilder.append("Погода в Москве сейчас:\n");
        weatherStringBuilder.append("Температура: ").append(fact.getTemp()).append("°C\n");
        weatherStringBuilder.append("Ощущаемая температура: ").append(fact.getFeelsLike()).append("°C\n");
        Condition condition = Condition.valueOf(weather.getFact().getCondition().toUpperCase(Locale.ROOT));
        weatherStringBuilder.append("Погодное описание: ").append(condition.getCondition()).append("\n");
        weatherStringBuilder.append("Давление (в мм рт. ст.): ").append(fact.getPressureMm()).append("\n");
        weatherStringBuilder.append("Влажность воздуха: ").append(fact.getHumidity()).append("%\n");
        return  weatherStringBuilder.toString();
    }
}
