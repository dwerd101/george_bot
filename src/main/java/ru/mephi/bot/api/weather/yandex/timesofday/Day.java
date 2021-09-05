package ru.mephi.bot.api.weather.yandex.timesofday;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@EqualsAndHashCode
public class Day {
    @JsonProperty("temp_min")
    private Long tempMin;
    @JsonProperty("temp_avg")
    private Long tempAvg;
    @JsonProperty("temp_max")
    private Long tempMax;
    @JsonProperty("feels_like")
    private Long feelsLike;
    @JsonProperty("pressure_mm")
    private Long pressureMm;
    @JsonProperty("prec_prob")
    private Long precProb;
    @JsonProperty("condition")
    private String condition;

    public String getCondition() {
        String newCondition ="";
        if(condition.contains("-")) {
            newCondition = condition.replace("-","_");
            return newCondition;
        } else return condition;
    }


    public Long getTempMin() {
        return tempMin;
    }

    public Long getTempAvg() {
        return tempAvg;
    }

    public Long getTempMax() {
        return tempMax;
    }

    public Long getFeelsLike() {
        return feelsLike;
    }

    public Long getPressureMm() {
        return pressureMm;
    }

    public Long getPrecProb() {
        return precProb;
    }
}
