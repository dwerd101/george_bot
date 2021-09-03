package ru.mephi.config.html;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class HtmlMephi {

    @Value("${mephi.url}")
    private String baseUrl;
    @Value("${mephi.tomorrowUrl}")
    private String tomorrowUrl;
    /*@Value("${mephi.week}")
    private String weekUrl;*/

    @Value("${mephi.userAgent}")
    private String userAgent;

    @SneakyThrows
    public Document getPageTodayFromMephi() {
        log.info("Идет получение расписания на сегодня");
        Connection con = Jsoup.connect(baseUrl).userAgent(userAgent);
        Document doc = con.get();
        return doc;
    }

  /*  @SneakyThrows
    public Document getWeekTodayFromMephi() {
        log.info("Идет подключение");
        Connection con = Jsoup.connect(weekUrl).userAgent(userAgent);
        Document doc = con.get();
        return doc;
    }*/

    @SneakyThrows
    public Document getPageTomorrowFromMephi() {
        log.info("Идет получение расписания на завтра");
        LocalDate localDate = LocalDate.now().plusDays(1);
        log.info(LocalDateTime.now().toString());
        String europeanDatePattern = "yyyy-MM-dd";
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
        Connection con = Jsoup.connect(tomorrowUrl + europeanDateFormatter.format(localDate)).userAgent(userAgent);
        Document doc = con.get();
        return doc;
    }


    public List<String> getTimes(Document doc) {

        Elements timeLesson = doc.select("div.lesson-time");
        List<String> times = new ArrayList<>();
        for (Element element : timeLesson) {
            TextNode element2 = (TextNode) element.childNode(0);
            times.add(element2.text());
        }
        return times;
    }

    public List<String> getNameLessons(Document doc) {

        Elements nameLessons = doc.select("div.lesson");
        List<String> nameLessonList = new ArrayList<>();
        for (Element element : nameLessons) {
            nameLessonList.add(element.ownText());
        }
        return nameLessonList;
    }

    public List<String> getLocations(Document doc) {

        Elements location = doc.select("a.text-nowrap");
        List<String> nameLessonList = new ArrayList<>();
        for (Element element : location) {
            Attributes attributes = element.attributes();
            String text = ((TextNode) element.childNode(0)).text();
            String rooms = attributes.get("href");
            if (rooms.contains("rooms")) {
                nameLessonList.add(text);
            }
        }
        return nameLessonList;
    }

    public List<String> getNamesTeachers(Document doc) {

        Elements location = doc.select("a.text-nowrap");
        List<String> nameLessonList = new ArrayList<>();
        for (Element element : location) {
            Attributes attributes = element.attributes();
            String text = ((TextNode) element.childNode(0)).text();
            String rooms = attributes.get("href");
            if (rooms.contains("tutors")) {
                nameLessonList.add(text);
            }
        }
        return nameLessonList;
    }

    public List<String> getTypeOfLesson(Document doc) {

        Elements typeOfLesson = doc.select("div.label-lesson");
        List<String> nameLessonList = new ArrayList<>();
        for (Element element : typeOfLesson) {
            String text = ((TextNode) element.childNode(0)).text();
            nameLessonList.add(text);
        }
        return nameLessonList;
    }
}
