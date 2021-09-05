package ru.mephi.bot.api.weather.yandex;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class YandexWeatherApi {

    @Value("${yandex.weather.moscow.url}")
    private String yandexApiMoscowUrl;
    @Value("${yandex.weather.header.value}")
    private String yandexApiHeaderValue;
    @Value("${yandex.weather.header.key}")
    private String yandexApiHeaderKey;
}
