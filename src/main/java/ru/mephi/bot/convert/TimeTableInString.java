package ru.mephi.bot.convert;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.mephi.model.TimeTable;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Component
@Slf4j
public class TimeTableInString {
    public String getListTimeTableOfString(List<TimeTable> timeTableList) {
        StringBuilder listTimeTableOfString = new StringBuilder();
        ZoneId moscow = ZoneId.of("Europe/Moscow");
        LocalDateTime currentTimeLesson = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(currentTimeLesson,moscow);
        zonedDateTime.plusHours(3);
        log.info(zonedDateTime.toLocalTime().toString());
        for (TimeTable timeTable : timeTableList) {
            String[] timeRangeMas = timeTable.getRangeOfTimes().replace(" —","").split(" ");
            LocalTime timeEndLesson = LocalTime.parse(timeRangeMas[1]);
            if(zonedDateTime.toLocalTime().isBefore(timeEndLesson)) {
                if (!timeTable.getTeacherList().isEmpty()) {
                    listTimeTableOfString.append(timeTable.getRangeOfTimes()).append(" ").append(timeTable.getTypeOfLesson()).append(" ").append(timeTable.getNameLesson()).append(" ").append(timeTable.getCabinet()).append(" ").append(timeTable.getTeacherList().get(0)).append("\n");
                } else
                    listTimeTableOfString.append(timeTable.getRangeOfTimes()).append(" ").append(timeTable.getTypeOfLesson()).append(" ").append(timeTable.getNameLesson()).append(" ").append(timeTable.getCabinet()).append("\n");
            }
        }
        if(listTimeTableOfString.toString().isEmpty()) {
            return "На сегодня пар нет";
        }
        else return listTimeTableOfString.toString();
    }

}
