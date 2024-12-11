package org.shiwani.technicalquize2.controller;

import lombok.RequiredArgsConstructor;
import org.shiwani.technicalquize2.pojo.Question;
import org.shiwani.technicalquize2.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;


    //http://localhost:8080/question/addQuestion?key=yourKey
    @PostMapping("/addQuestion")
    public ResponseEntity<String> addQuestion(@RequestParam String key, @RequestBody Question question) {
        return questionService.addQuestion(key,question);
    }

    //http://localhost:8080/question/addQuestions?key=yourKey
    @PostMapping("/addQuestions")
    public ResponseEntity<String> addQuestions(@RequestParam String key ,@RequestBody List<Question> questions) {
        return questionService.addQuestions(key,questions);
    }

    //http://localhost:8080/question/category/postman
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category.toLowerCase());
    }

    //http://localhost:8080/question/all
    @GetMapping("/all")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    //http://localhost:8080/question/categoryRandom/postman?limit=5
    @GetMapping("/categoryRandom/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category,
                                                                 @RequestParam(required = false, defaultValue = "10") int limit) {
        return questionService.getRandomQuestionsByCategory(category.toLowerCase(), limit);
    }

}