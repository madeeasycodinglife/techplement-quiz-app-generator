package com.madeeasy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {

    private Integer difficultyLevel;
    private String question;
    private String correctAnswer;
    private String explanation;
    private List<String> options;
    private Integer points;
}
