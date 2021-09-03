package ru.mephi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    private String lastname;
    private String name;
    private String middleName;
}
