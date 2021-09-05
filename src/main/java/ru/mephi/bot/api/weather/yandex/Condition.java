package ru.mephi.bot.api.weather.yandex;

public enum Condition {
    CLEAR("ясно"),
    PARTLY_CLOUDY("малооблачно"),
    CLOUDY("облачно с прояснениями"),
    OVERCAST("пасмурно"),
    DRIZZLE("морось"),
    LIGHT_RAIN("небольшой дождь"),
    RAIN("дождь"),
    MODERATE_RAIN("умеренно сильный дождь"),
    HEAVY_RAIN("сильный дождь"),
    CONTINUOUS_HEAVY_RAIN("длительный сильный дождь"),
    SHOWERS("ливень"),
    WET_SNOW("дождь со снегом"),
    LIGHT_SNOW("небольшой снег"),
    SNOW("снег"),
    SNOW_SHOWERS(" снегопад"),
    HAIL("град"),
    THUNDERSTORM("гроза"),
    THUNDERSTORM_WITH_RAIN("дождь с грозой"),
    THUNDERSTORM_WITH_HAIL("гроза с градом");
    private final String condition;

    Condition(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }
}
