package org.shiwani.technicalquize2.controller;


import lombok.RequiredArgsConstructor;
import org.shiwani.technicalquize2.dto.QuizAnswersDTO;
import org.shiwani.technicalquize2.pojo.Question;
import org.shiwani.technicalquize2.pojo.QuizScore;
import org.shiwani.technicalquize2.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    //http://localhost:8080/quiz/getQuiz?key=12345&limit=10
    @GetMapping("/getQuiz")
    public ResponseEntity<List<Question>> getQuiz(
            @RequestParam String key,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String difficulty
    ) {
        if(category != null && difficulty != null) {
            return quizService.getQuizByCategoryAndDifficulty(key, limit, category.toLowerCase(), difficulty.toLowerCase());
        } else if (difficulty != null) {
            return quizService.getQuizByDifficulty(key, limit, difficulty.toLowerCase());
        } else if (category != null) {
            return quizService.getQuizByCategory(key, limit, category.toLowerCase());
        }else{
            return quizService.getQuiz(key, limit);
        }

    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestParam String key,
                                            @RequestBody QuizAnswersDTO quizAnswersDTO) {
        List<Integer> questionIds = quizAnswersDTO.getQuestionIds();
        List<Integer> answers = quizAnswersDTO.getAnswers();
        return quizService.getScore(key, questionIds, answers);
    }

    @GetMapping("getAllScoreByPercentage")
    public ResponseEntity<List<QuizScore>> getAllScoreByPercentage() {
        return quizService.getAllScoreByPercentage();
    }

    @GetMapping("getAllCategory")
    public ResponseEntity<List<String>> getAllCategory(){
        return quizService.getAllCategory();
    }
}
