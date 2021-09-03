package ru.mephi.bot.convert;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import ru.mephi.config.html.HtmlMephi;
import ru.mephi.model.TimeTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class TimeTableTomorrow {
    private final HtmlMephi htmlMephi;
    public List<TimeTable> getTimeTomorrowTable() {
        Document document = htmlMephi.getPageTomorrowFromMephi();
        log.info("Получен html с завтрашним расписанием");
        List<String> locationsList = htmlMephi.getLocations(document);
        List<String> nameLessonsList = htmlMephi.getNameLessons(document);
        List<String> timesList = htmlMephi.getTimes(document);
        List<String> namesTeachersList = htmlMephi.getNamesTeachers(document)
                .stream()
                .filter(s -> !s.contains("Зайцев"))
                .collect(Collectors.toList());
        List<String> typeOfLesson = htmlMephi.getTypeOfLesson(document);
        List<TimeTable> timeTableList = new ArrayList<>();

        return algorithmGetTimeTodayTable(locationsList,nameLessonsList,timesList,typeOfLesson,timeTableList,
                namesTeachersList);

    }

    private List<TimeTable> algorithmGetTimeTodayTable(List<String> locationsList,
                                                       List<String> nameLessonsList,
                                                       List<String> timesList,
                                                       List<String> typeOfLesson,
                                                       List<TimeTable> timeTableList,
                                                       List<String> namesTeachersList) {

        if(namesTeachersList.isEmpty()) {
            return getMilitaryDepartmentFromMondayTimeTableList(locationsList, nameLessonsList, timesList,
                    typeOfLesson, timeTableList);
        }
        else if (timesList.size() < namesTeachersList.size()) {
            return getThursdayTimeTableList(locationsList,nameLessonsList,timesList,typeOfLesson
                    ,timeTableList,namesTeachersList);
        } else {
            return getWednesdayTimeTableList(locationsList, nameLessonsList, timesList
                    ,typeOfLesson, timeTableList,namesTeachersList);
        }
    }

    private List<TimeTable> getWednesdayTimeTableList(List<String> locationsList,
                                                      List<String> nameLessonsList,
                                                      List<String> timesList,
                                                      List<String> typeOfLesson,
                                                      List<TimeTable> timeTableList,
                                                      List<String> namesTeachersList
    ) {
        int countOfTeachers = 0;
        for (int i = 0; i < timesList.size(); i++) {

            //в ауд отсуствует препод
            if (typeOfLesson.get(i).contains("Ауд")) {
                TimeTable timeTable = TimeTable.builder()
                        .cabinet(locationsList.get(i))
                        .rangeOfTimes(timesList.get(i))
                        .nameLesson(nameLessonsList.get(i))
                        .teacherList(new ArrayList<>())
                        .typeOfLesson(typeOfLesson.get(i))
                        .build();
                ++countOfTeachers;
                timeTableList.add(timeTable);
            } else {
                int countOfTeachers1 = i - countOfTeachers;
                TimeTable timeTable = TimeTable.builder()
                        .cabinet(locationsList.get(i))
                        .rangeOfTimes(timesList.get(i))
                        .nameLesson(nameLessonsList.get(i))
                        .teacherList(Collections.singletonList(namesTeachersList.get(countOfTeachers1)))
                        .typeOfLesson(typeOfLesson.get(i))
                        .build();
                timeTableList.add(timeTable);
            }
        }
        return timeTableList;
    }



    private List<TimeTable> getMilitaryDepartmentFromMondayTimeTableList(List<String> locationsList,
                                                                         List<String> nameLessonsList,
                                                                         List<String> timesList,
                                                                         List<String> typeOfLesson,
                                                                         List<TimeTable> timeTableList) {

        for (int i = 0; i < timesList.size(); i++) {
            TimeTable timeTable = TimeTable.builder()
                    .cabinet(locationsList.get(i))
                    .rangeOfTimes(timesList.get(i))
                    .nameLesson(nameLessonsList.get(i))
                    .teacherList(new ArrayList<>())
                    .typeOfLesson(typeOfLesson.get(i))
                    .build();
            timeTableList.add(timeTable);
        }
        return timeTableList;
    }
    private List<TimeTable> getThursdayTimeTableList(List<String> locationsList,
                                                     List<String> nameLessonsList,
                                                     List<String> timesList,
                                                     List<String> typeOfLesson,
                                                     List<TimeTable> timeTableList,
                                                     List<String> namesTeachersList
    ) {
        for (int i = 0; i < namesTeachersList.size(); i++) {
            // 3 преподавателя
            if(timesList.size() >= i && namesTeachersList.size()==3) {
                if (i == 2) {
                    TimeTable timeTable = TimeTable.builder()
                            .cabinet(locationsList.get(i))
                            .rangeOfTimes(timesList.get(i - 1))
                            .nameLesson(nameLessonsList.get(i))
                            .teacherList(Collections.singletonList(namesTeachersList.get(i)))
                            .typeOfLesson(typeOfLesson.get(i))
                            .build();
                    timeTableList.add(timeTable);
                } else {
                    TimeTable timeTable = TimeTable.builder()
                            .cabinet(locationsList.get(i))
                            .rangeOfTimes(timesList.get(i))
                            .nameLesson(nameLessonsList.get(i))
                            .teacherList(Collections.singletonList(namesTeachersList.get(i)))
                            .typeOfLesson(typeOfLesson.get(i))
                            .build();
                    timeTableList.add(timeTable);
                }
            }
            else
            {
                TimeTable timeTable = TimeTable.builder()
                        .cabinet(locationsList.get(i))
                        .rangeOfTimes(timesList.get(i))
                        .nameLesson(nameLessonsList.get(i))
                        .teacherList(Collections.singletonList(namesTeachersList.get(i)))
                        .typeOfLesson(typeOfLesson.get(i))
                        .build();
                timeTableList.add(timeTable);
            }
        }
        return timeTableList;
    }
}
