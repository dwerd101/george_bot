package ru.mephi.config.timetable.week;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mephi.model.TimeTable;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

@Configuration
public class WeekTimeTableConfig {

    @Bean
    public Map<Week, List<TimeTable>> timeTableWeekMap() {
        Map<Week, List<TimeTable>> map = new ConcurrentSkipListMap<>();
        TimeTable monday = TimeTable.builder()
                .cabinet("каф.20")
                .rangeOfTimes("08:30 — 10:05")
                .typeOfLesson("ЛЕК")
                .nameLesson("Военная подготовка")
                .teacherList(new ArrayList<>())
                .build();
        TimeTable monday1 = TimeTable.builder()
                .cabinet("каф.20")
                .rangeOfTimes("10:15 — 16:05")
                .typeOfLesson("ЛЕК")
                .nameLesson("Военная подготовка")
                .teacherList(new ArrayList<>())
                .build();
        map.put(Week.MONDAY, Arrays.asList(monday,monday1));
        TimeTable tuesday = TimeTable.builder()
                .cabinet("64-505а")
                .rangeOfTimes("10:15 — 11:50")
                .nameLesson("Нейронные сети и искусственный интеллект. Машинное глубокое обучение")
                .typeOfLesson("ЛЕК")
                .teacherList(Arrays.asList("Дунаев М.Е.","Зайцев К.С."))
                .build();
        TimeTable tuesday1 = TimeTable.builder()
                .cabinet("64-505а")
                .rangeOfTimes("11:55 — 13:30")
                .nameLesson("Нейронные сети и искусственный интеллект. Машинное глубокое обучение")
                .typeOfLesson("ПР")
                .teacherList(Arrays.asList("Дунаев М.Е.","Зайцев К.С."))
                .build();
        map.put(Week.TUESDAY,Arrays.asList(tuesday,tuesday1));
        TimeTable wednesday = TimeTable.builder()
                .cabinet("АСЭ")
                .rangeOfTimes("09:20 — 11:50")
                .nameLesson("Архитектура единого информационного пространства на всем жизненном цикле АЭС")
                .typeOfLesson("ЛЕК")
                .teacherList(Collections.singletonList("Шаманин А.Ю."))
                .build();
        TimeTable wednesday1 = TimeTable.builder()
                .cabinet("АСЭ")
                .rangeOfTimes("09:20 — 11:50")
                .nameLesson("Архитектура единого информационного пространства на всем жизненном цикле АЭС")
                .typeOfLesson("ПР")
                .teacherList(Collections.singletonList("Шаманин А.Ю."))
                .build();
        TimeTable wednesday2 = TimeTable.builder()
                .cabinet("АСЭ")
                .rangeOfTimes("11:55 — 12:40")
                .nameLesson("Архитектура единого информационного пространства на всем жизненном цикле АЭС")
                .typeOfLesson("ЛЕК")
                .teacherList(Collections.singletonList("Шаманин А.Ю."))
                .build();
        TimeTable wednesday3 = TimeTable.builder()
                .cabinet("АСЭ")
                .rangeOfTimes("11:55 — 12:40")
                .nameLesson("Архитектура единого информационного пространства на всем жизненном цикле АЭС")
                .typeOfLesson("ПР")
                .teacherList(Collections.singletonList("Шаманин А.Ю."))
                .build();
        TimeTable wednesday4 = TimeTable.builder()
                .cabinet("АСЭ")
                .rangeOfTimes("13:35 — 15:15")
                .nameLesson("Учебная практика (ознакомительная)")
                .typeOfLesson("АУД")
                .teacherList(new ArrayList<>())
                .build();
        TimeTable wednesday5 = TimeTable.builder()
                .cabinet("АСЭ")
                .rangeOfTimes("15:20 — 17:00")
                .nameLesson("Визуализация и презентация проектов")
                .typeOfLesson("ЛЕК")
                .teacherList(Collections.singletonList("Бойко О.В."))
                .build();
        TimeTable wednesday6 = TimeTable.builder()
                .cabinet("АСЭ")
                .rangeOfTimes("15:20 — 17:00")
                .nameLesson("Визуализация и презентация проектов")
                .typeOfLesson("ПР")
                .teacherList(Collections.singletonList("Бойко О.В."))
                .build();
        map.put(Week.WEDNESDAY, Arrays.asList(wednesday, wednesday1, wednesday2, wednesday3, wednesday4, wednesday5, wednesday6));
         TimeTable thursday = TimeTable.builder()
                 .cabinet("325")
                 .rangeOfTimes("16:15 — 17:50")
                 .nameLesson("Иностранный язык (Foreign Language)")
                 .typeOfLesson("ПР")
                 .teacherList(Collections.singletonList("Курнаев А.А."))
                 .build();
        TimeTable thursday1 = TimeTable.builder()
                .cabinet("В-115")
                .rangeOfTimes("17:55 — 19:30")
                .nameLesson("Системы хранения данных")
                .typeOfLesson("ЛЕК")
                .teacherList(Collections.singletonList("Евсеев В.В."))
                .build();
        TimeTable thursday2 = TimeTable.builder()
                .cabinet("64-505б")
                .rangeOfTimes("17:55 — 19:30")
                .nameLesson("Цифровые технологии работы с технической нормативной документацией")
                .typeOfLesson("ЛЕК")
                .teacherList(Collections.singletonList("Мельников В.Е."))
                .build();
        TimeTable thursday3 = TimeTable.builder()
                .cabinet("В-115")
                .rangeOfTimes("17:55 — 19:30")
                .nameLesson("Системы хранения данных")
                .typeOfLesson("ПР")
                .teacherList(Collections.singletonList("Евсеев В.В."))
                .build();
        TimeTable thursday4 = TimeTable.builder()
                .cabinet("64-505б")
                .rangeOfTimes("17:55 — 19:30")
                .nameLesson("Цифровые технологии работы с технической нормативной документацией")
                .typeOfLesson("ПР")
                .teacherList(Collections.singletonList("Мельников В.Е."))
                .build();
        map.put(Week.THURSDAY,Arrays.asList(thursday,thursday1, thursday2, thursday3,thursday4));
        TimeTable friday = TimeTable.builder()
                .cabinet("64-505a")
                .rangeOfTimes("09:20 — 10:05")
                .nameLesson("Теория игр и исследование операций")
                .typeOfLesson("ПР")
                .teacherList(Collections.singletonList("Колобашкина Л.В"))
                .build();
        TimeTable friday1 = TimeTable.builder()
                .cabinet("64-505a")
                .rangeOfTimes("10:15 — 11:50")
                .nameLesson("Теория игр и исследование операций")
                .typeOfLesson("ЛЕК")
                .teacherList(Collections.singletonList("Колобашкина Л.В"))
                .build();
        TimeTable friday2 = TimeTable.builder()
                .cabinet("64-505a")
                .rangeOfTimes("11:55 — 15:15")
                .nameLesson("Основы технологии промышленного интернета вещей")
                .typeOfLesson("ЛЕК")
                .teacherList(Collections.singletonList("Жабицкий М.Г."))
                .build();
        TimeTable friday3 = TimeTable.builder()
                .cabinet("64-505a")
                .rangeOfTimes("11:55 — 15:15")
                .nameLesson("Основы технологии промышленного интернета вещей")
                .typeOfLesson("ПР")
                .teacherList(Collections.singletonList("Жабицкий М.Г."))
                .build();
        TimeTable friday4 = TimeTable.builder()
                .cabinet("Б-219")
                .rangeOfTimes("16:15 — 17:50")
                .nameLesson("Системный анализ и системная инженерия. " +
                        "Управление проектами и методология проектной деятельности.")
                .typeOfLesson("ЛЕК")
                .teacherList(Collections.singletonList("Королев А.С."))
                .build();
        TimeTable friday5 = TimeTable.builder()
                .cabinet("Б-219")
                .rangeOfTimes("17:55 — 19:30")
                .nameLesson("Системный анализ и системная инженерия. " +
                        "Управление проектами и методология проектной деятельности.")
                .typeOfLesson("ПР")
                .teacherList(Collections.singletonList("Королев А.С."))
                .build();
        map.put(Week.FRIDAY, Arrays.asList(friday,friday1,friday2,friday3,friday4,friday5));
        TimeTable saturday = TimeTable.builder()
                .cabinet("В-118")
                .rangeOfTimes("09:20 — 12:40")
                .nameLesson("Языки программирования. Технологии разработки програмно-информационных систем.")
                .typeOfLesson("ПР")
                .teacherList(Collections.singletonList("Андриенко Ю.А."))
                .build();
        map.put(Week.SATURDAY, Collections.singletonList(saturday));
        return map;
    }


}
