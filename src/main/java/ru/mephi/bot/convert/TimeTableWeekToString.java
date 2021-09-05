package ru.mephi.bot.convert;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.mephi.config.timetable.week.Week;
import ru.mephi.model.TimeTable;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class TimeTableWeekToString {

    private final Map<Week, List<TimeTable>> timeTableWeekMap;

    public String getTimeTableWeekToString() {
        StringBuilder weekTimeTable = new StringBuilder();
        for (Map.Entry<Week, List<TimeTable>> timeTableEntry : timeTableWeekMap.entrySet()) {
            Week week = timeTableEntry.getKey();
            switch (week) {
                case MONDAY:
                    getTimeTableWeekToString(timeTableEntry.getValue(),weekTimeTable,"                                                  Понедельник");
                    break;
                case TUESDAY:
                    getTimeTableWeekToString(timeTableEntry.getValue(),weekTimeTable,"Вторник");
                    break;
                case WEDNESDAY:
                    getTimeTableWeekToString(timeTableEntry.getValue(),weekTimeTable,"Среда");
                    break;
                case THURSDAY:
                    getTimeTableWeekToString(timeTableEntry.getValue(),weekTimeTable,"Четверг");
                    break;
                case FRIDAY:
                    getTimeTableWeekToString(timeTableEntry.getValue(),weekTimeTable,"Пятница");
                    break;
                case SATURDAY:
                    getTimeTableWeekToString(timeTableEntry.getValue(),weekTimeTable,"Суббота");
                    break;
            }
        }
        return weekTimeTable.toString();
    }

    private static void getTimeTableWeekToString(List<TimeTable> timeTableList, StringBuilder weekOfDay,
                                                 String weekOfDayTOString) {
        weekOfDay.append("                              ").append(weekOfDayTOString).append("\n\n");
        for (TimeTable timeTable : timeTableList) {

            log.info(timeTable+"");
            timeTableAppend(weekOfDay, timeTable);
        }
        weekOfDay.append("\n\n");
    }

    static void timeTableAppend(StringBuilder weekTimeTable, TimeTable timeTable) {
        TimeTableInToString.appendIfTeacherListIsEmptyOrNot(weekTimeTable, timeTable);
    }
}
