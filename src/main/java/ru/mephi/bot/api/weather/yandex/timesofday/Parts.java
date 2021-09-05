package ru.mephi.bot.api.weather.yandex.timesofday;

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
public class Parts {
    @JsonProperty("night")
    private Night night;
    @JsonProperty("morning")
    private Morning morning;
    @JsonProperty("day")
    private Day day;
    @JsonProperty("evening")
    private Evening evening;

}
