package ru.mephi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimeTable {
    private String rangeOfTimes;
    private String nameLesson;
    private String typeOfLesson;
    private String cabinet;
    private List<String> teacherList;
}
