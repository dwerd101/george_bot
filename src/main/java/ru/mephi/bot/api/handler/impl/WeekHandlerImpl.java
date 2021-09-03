package ru.mephi.bot.api.handler.impl;/*
package ru.mephi.bot.api.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.mephi.config.bot.BotState;
import ru.mephi.config.html.HtmlMephi;
import ru.mephi.model.TimeTable;
import ru.mephi.service.ReplyMessagesService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class WeekHandlerImpl implements WeekHandler{
    private ReplyMessagesService messagesService;
    private final HtmlMephi htmlMephi;
    private final InlineKeyboardMarkup inlineMessageButtons;
    @Override
    public SendMessage handle(Message message) {
        long chatId = message.getChatId();
        String listTimeTableOfString = getListTimeTableOfString(getTimeTableWeek());
        SendMessage replyToUser = messagesService.getReplyMessage(chatId, listTimeTableOfString);
        replyToUser.setReplyMarkup(inlineMessageButtons);
        return replyToUser;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.WEEK;
    }

    @Override
    public SendMessage handle(long chatId) {
        String listTimeTableOfString =  getListTimeTableOfString(getTimeTableWeek());
        SendMessage replyToUser = messagesService.getReplyMessage(chatId, listTimeTableOfString);
        replyToUser.setReplyMarkup(inlineMessageButtons);
        return replyToUser;
    }

    private String getListTimeTableOfString(List<TimeTable> timeTableList) {
        StringBuilder listTimeTableOfString = new StringBuilder();
        for (TimeTable timeTable : timeTableList) {
                if (!timeTable.getTeacherList().isEmpty()) {
                    listTimeTableOfString.append(timeTable.getRangeOfTimes()).append(" ").append(timeTable.getTypeOfLesson()).append(" ").append(timeTable.getNameLesson()).append(" ").append(timeTable.getCabinet()).append(" ").append(timeTable.getTeacherList().get(0)).append("\n");
                } else
                    listTimeTableOfString.append(timeTable.getRangeOfTimes()).append(" ").append(timeTable.getTypeOfLesson()).append(" ").append(timeTable.getNameLesson()).append(" ").append(timeTable.getCabinet()).append("\n");
            }
        return listTimeTableOfString.toString();
    }
    public List<TimeTable> getTimeTableWeek() {
        Document document = htmlMephi.getWeekTodayFromMephi();
        List<String> locationsList = htmlMephi.getLocations(document);
        List<String> nameLessonsList = htmlMephi.getNameLessons(document);
        List<String> timesList = htmlMephi.getTimes(document);
        List<String> namesTeachersList = htmlMephi.getNamesTeachers(document)
                .stream()
                .filter(s -> !s.contains("Зайцев"))
                .collect(Collectors.toList());
        List<String> typeOfLesson = htmlMephi.getTypeOfLesson(document);
        List<TimeTable> timeTableList = new ArrayList<>();

        int countOfTeachers = 0;

        //Военная кафедра
        if(namesTeachersList.isEmpty()) {
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

        else if (timesList.size() < namesTeachersList.size()) {
            for (int i = 0; i < namesTeachersList.size(); i++) {

                // 3 препродоваткля
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
        } else {
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
    }
}
*/
