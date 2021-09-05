package ru.mephi.bot.api.handler.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.mephi.bot.api.handler.WeatherTodayAndTomorrowHandler;
import ru.mephi.bot.api.weather.yandex.Condition;
import ru.mephi.bot.api.weather.yandex.Forecast;
import ru.mephi.bot.api.weather.yandex.Weather;
import ru.mephi.bot.api.weather.yandex.YandexWeatherApi;
import ru.mephi.config.BotState;
import ru.mephi.service.ReplyMessagesService;

import java.util.List;
import java.util.Locale;

@Component
@AllArgsConstructor
public class WeatherTodayAndTomorrowHandlerImpl implements WeatherTodayAndTomorrowHandler {
    private final ReplyMessagesService messagesService;
    private final InlineKeyboardMarkup inlineMessageButtons;
    private final YandexWeatherApi yandexWeatherApi;
    @SneakyThrows
    @Override
    public SendMessage handle(Message message) {
       final long chatId = message.getChatId();
        Weather weather = getWeatherMoscowFromYandexApi();
        List<Forecast> forecastList = weather.getForecast();
        String weatherMoscowFromYandexInString = getWeatherMoscowFromYandexApiTodayAndTomorrowMessage(forecastList);
        SendMessage replyToUser = messagesService.getReplyMessage(chatId, weatherMoscowFromYandexInString);
        replyToUser.setReplyMarkup(inlineMessageButtons);
        return replyToUser;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.WEATHER_TODAY_AND_TOMORROW;
    }

    @SneakyThrows
    @Override
    public SendMessage handle(final long chatId) {
        Weather weather = getWeatherMoscowFromYandexApi();
        List<Forecast> forecastList = weather.getForecast();
        String weatherMoscowFromYandexInString = getWeatherMoscowFromYandexApiTodayAndTomorrowMessage(forecastList);
        SendMessage replyToUser = messagesService.getReplyMessage(chatId, weatherMoscowFromYandexInString);
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

    private String getWeatherMoscowFromYandexApiTodayAndTomorrowMessage(List<Forecast> forecastList) {
        StringBuilder weatherMoscowStringBuilder = new StringBuilder("Погода в Москве на сегодня и на завтра :\n");
        int weatherInMoscowTodayAndTomorrow = 2;
        for (int i=0; i<weatherInMoscowTodayAndTomorrow; i++) {
            Forecast forecast = forecastList.get(i);
            getTextWeatherMoscow(forecast, weatherMoscowStringBuilder);
        }
        return weatherMoscowStringBuilder.toString();
    }
    private void getTextWeatherMoscow(Forecast forecast, StringBuilder weatherMoscowStringBuilder ) {

        weatherMoscowStringBuilder.append("Дата: ").append(forecast.getDate()).append("\n");
        weatherMoscowStringBuilder.append("Время восхода Солнца: ").append(forecast.getSunrise()).append("\n");
        weatherMoscowStringBuilder.append("Время заката Солнца: ").append(forecast.getSunset()).append("\n\n");

        weatherMoscowStringBuilder.append("                                                  Ночь" + "\n\n");
        weatherMoscowStringBuilder.append("Минимальная температура для времени суток: ").append(forecast.getParts().getNight().getTempMin()).append("°C").append("\n");
        weatherMoscowStringBuilder.append("Средняя температура для времени суток: ").append(forecast.getParts().getNight().getTempAvg()).append("°C").append("\n");
        weatherMoscowStringBuilder.append("Максимальная температура для времени суток: ").append(forecast.getParts().getNight().getTempMax()).append("°C").append("\n");
        weatherMoscowStringBuilder.append("Ощущаемая температура: ").append(forecast.getParts().getNight().getFeelsLike()).append("°C").append("\n");
        weatherMoscowStringBuilder.append("Давление (в мм рт. ст.): ").append(forecast.getParts().getNight().getPressureMm()).append("\n");
        weatherMoscowStringBuilder.append("Погодное описание: ").append(Condition.valueOf(forecast.getParts().getNight().getCondition().toUpperCase(Locale.ROOT)).getCondition()).append("\n\n");

        weatherMoscowStringBuilder.append("                                                  Утро" + "\n\n");
        weatherMoscowStringBuilder.append("Минимальная температура для времени суток: ").append(forecast.getParts().getMorning().getTempMin()).append("°C").append("\n");
        weatherMoscowStringBuilder.append("Средняя температура для времени суток: ").append(forecast.getParts().getMorning().getTempAvg()).append("°C").append("\n");
        weatherMoscowStringBuilder.append("Максимальная температура для времени суток: ").append(forecast.getParts().getMorning().getTempMax()).append("°C").append("\n");
        weatherMoscowStringBuilder.append("Ощущаемая температура: ").append(forecast.getParts().getMorning().getFeelsLike()).append("°C").append("\n");
        weatherMoscowStringBuilder.append("Давление (в мм рт. ст.): ").append(forecast.getParts().getMorning().getPressureMm()).append("\n");
        weatherMoscowStringBuilder.append("Погодное описание: ").append(Condition.valueOf(forecast.getParts().getMorning().getCondition().toUpperCase(Locale.ROOT)).getCondition()).append("\n\n");

        weatherMoscowStringBuilder.append("                                                  День" + "\n\n");
        weatherMoscowStringBuilder.append("Минимальная температура для времени суток: ").append(forecast.getParts().getDay().getTempMin()).append("°C").append("\n");
        weatherMoscowStringBuilder.append("Средняя температура для времени суток: ").append(forecast.getParts().getDay().getTempAvg()).append("°C").append("\n");
        weatherMoscowStringBuilder.append("Максимальная температура для времени суток: ").append(forecast.getParts().getDay().getTempMax()).append("°C").append("\n");
        weatherMoscowStringBuilder.append("Ощущаемая температура: ").append(forecast.getParts().getDay().getFeelsLike()).append("°C").append("\n");
        weatherMoscowStringBuilder.append("Давление (в мм рт. ст.): ").append(forecast.getParts().getDay().getPressureMm()).append("\n");
        weatherMoscowStringBuilder.append("Погодное описание: ").append(Condition.valueOf(forecast.getParts().getDay().getCondition().toUpperCase(Locale.ROOT)).getCondition()).append("\n\n");

        weatherMoscowStringBuilder.append("                                                  Вечер" + "\n\n");
        weatherMoscowStringBuilder.append("Минимальная температура для времени суток: ").append(forecast.getParts().getEvening().getTempMin()).append("°C").append("\n");
        weatherMoscowStringBuilder.append("Средняя температура для времени суток: ").append(forecast.getParts().getEvening().getTempAvg()).append("°C").append("\n");
        weatherMoscowStringBuilder.append("Максимальная температура для времени суток: ").append(forecast.getParts().getEvening().getTempMax()).append("°C").append("\n");
        weatherMoscowStringBuilder.append("Ощущаемая температура: ").append(forecast.getParts().getEvening().getFeelsLike()).append("°C").append("\n");
        weatherMoscowStringBuilder.append("Давление (в мм рт. ст.): ").append(forecast.getParts().getEvening().getPressureMm()).append("\n");
        weatherMoscowStringBuilder.append("Погодное описание: ").append(Condition.valueOf(forecast.getParts().getEvening().getCondition().toUpperCase(Locale.ROOT)).getCondition()).append("\n\n");

        weatherMoscowStringBuilder.append("\n\n");
    }
}
