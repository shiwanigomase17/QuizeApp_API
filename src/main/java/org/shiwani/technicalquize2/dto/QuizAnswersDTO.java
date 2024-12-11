package org.shiwani.technicalquize2.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuizAnswersDTO {
    private List<Integer> questionIds;
    private List<Integer> answers;

}
