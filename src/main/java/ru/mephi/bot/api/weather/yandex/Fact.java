package ru.mephi.bot.api.weather.yandex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Fact {
    @JsonProperty("temp")
    private Long temp;
    @JsonProperty("feels_like")
    private Long feelsLike;
    @JsonProperty("condition")
    private String condition;
    @JsonProperty("pressure_mm")
    private Long pressureMm;
    @JsonProperty("humidity")
    private Long humidity;
}
