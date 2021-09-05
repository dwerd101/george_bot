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
public class TimeTableInToString {
    public String getListTimeTableOfString(List<TimeTable> timeTableList) {
        StringBuilder listTimeTableOfString = new StringBuilder();
        ZoneId moscow = ZoneId.of("Europe/Moscow");
        LocalDateTime currentTimeLesson = LocalDateTime.now();
        ZonedDateTime zonedDateTimeServer = ZonedDateTime.of(currentTimeLesson,moscow);
        ZonedDateTime zonedDateTimeMoscow = zonedDateTimeServer.plusHours(3);
        log.info(zonedDateTimeMoscow.toLocalTime().toString());
        for (TimeTable timeTable : timeTableList) {
            String[] timeRangeMas = timeTable.getRangeOfTimes().replace(" —","").split(" ");
            LocalTime timeEndLesson = LocalTime.parse(timeRangeMas[1]);
            if(zonedDateTimeMoscow.toLocalTime().isBefore(timeEndLesson)) {
               timeTableAppend(listTimeTableOfString, timeTable);
            }
            else if(timeTableList.get(0).getTeacherList().isEmpty() && timeTableList.get(0).getNameLesson()
                    .equalsIgnoreCase("Военная подготовка")) {
                timeTableAppend(listTimeTableOfString, timeTable);
            }
        }
        if(listTimeTableOfString.toString().isEmpty()) {
            return "На сегодня пар нет";
        }
        else return listTimeTableOfString.toString();
    }
    static void timeTableAppend(StringBuilder weekTimeTable, TimeTable timeTable) {
        appendIfTeacherListIsEmptyOrNot(weekTimeTable, timeTable);
    }

    static void appendIfTeacherListIsEmptyOrNot(StringBuilder weekTimeTable, TimeTable timeTable) {
        if (!timeTable.getTeacherList().isEmpty()) {
            weekTimeTable.append(timeTable.getRangeOfTimes()).append(" ").append(timeTable.getTypeOfLesson()).append(" ")
                    .append(timeTable.getNameLesson()).append(" ").append(timeTable.getCabinet()).append(" ")
                    .append(timeTable.getTeacherList().get(0)).append("\n").append("\n");
        } else
            weekTimeTable.append(timeTable.getRangeOfTimes()).append(" ").append(timeTable.getTypeOfLesson()).append(" ")
                    .append(timeTable.getNameLesson()).append(" ").append(timeTable.getCabinet()).append("\n").append("\n");
    }
}
