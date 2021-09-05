package ru.mephi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InlineKeyboardMarkupConfig {

    @Bean
    public InlineKeyboardMarkup inlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton buttonToday = new InlineKeyboardButton();
        InlineKeyboardButton buttonTomorrow = new InlineKeyboardButton();
        InlineKeyboardButton buttonWeek = new InlineKeyboardButton();
        InlineKeyboardButton buttonWeatherToday = new InlineKeyboardButton();
        InlineKeyboardButton buttonWeatherTodayAndTomorrow = new InlineKeyboardButton();
        buttonToday.setText("Today");
        buttonToday.setCallbackData("buttonToday");
        buttonTomorrow.setText("Tomorrow");
        buttonTomorrow.setCallbackData("buttonTomorrow");
        buttonWeek.setText("Week");
        buttonWeek.setCallbackData("buttonWeek");
        buttonWeek.setText("Week");
        buttonWeek.setCallbackData("buttonWeek");

        buttonWeatherToday.setText("Weather in Moscow Now");
        buttonWeatherToday.setCallbackData("buttonWeatherToday");
        buttonWeatherTodayAndTomorrow.setText("Weather Mos.Today-Tomorrow");
        buttonWeatherTodayAndTomorrow.setCallbackData("buttonWeatherTodayAndTomorrow");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonToday);
        keyboardButtonsRow1.add(buttonTomorrow);
        keyboardButtonsRow1.add(buttonWeek);
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(buttonWeatherToday);
        keyboardButtonsRow2.add(buttonWeatherTodayAndTomorrow);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
