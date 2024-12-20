package org.shiwani.technicalquize2.service;


import lombok.RequiredArgsConstructor;
import org.shiwani.technicalquize2.dao.*;
import org.shiwani.technicalquize2.pojo.ApiKey;
import org.shiwani.technicalquize2.pojo.Logs;
import org.shiwani.technicalquize2.pojo.Question;
import org.shiwani.technicalquize2.pojo.QuizScore;
import org.shiwani.technicalquize2.utility.KeyAuthenticator;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizDao quizDao;
    private final ApiKeyDao apiKeyDao;
    private final QuestionDao questionDao;
    private final QuizScoreDao quizScoreDao;
    private final LogsDao logsDao;
    private final KeyAuthenticator keyAuthenticator;

    private void saveLog(String email, String method) {
        Logs logs = new Logs();
        logs.setEmail(email);
        logs.setMethod(method);
        logs.setDateTime(LocalDateTime.now());
        logsDao.save(logs);
    }


    public ResponseEntity<List<Question>> getQuiz(String key, int limit) {
        keyAuthenticator.setKey(key);
        if (keyAuthenticator.isKeyValid()) {
            Optional<ApiKey> apiKey = apiKeyDao.findByApiKey(key);
            String email = apiKey.get().getEmail();
            saveLog(email, "getQuiz");


            List<Question> quizList = quizDao.getQuiz(PageRequest.of(0, limit));
            if (!quizList.isEmpty()) {
                return ResponseEntity.ok(quizList);
            }
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<List<Question>> getQuizByCategory(String key, int limit, String category) {
        keyAuthenticator.setKey(key);

        if (keyAuthenticator.isKeyValid()) {
            Optional<ApiKey> apiKey = apiKeyDao.findByApiKey(key);
            String email = apiKey.get().getEmail();
            saveLog(email, "getQuizByCategory");


            List<Question> quizList = quizDao.getQuizByCategory(category, PageRequest.of(0, limit));
            if (!quizList.isEmpty()) {
                return ResponseEntity.ok(quizList);
            }
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<List<Question>> getQuizByDifficulty(String key, int limit, String difficulty) {
        keyAuthenticator.setKey(key);

        if (keyAuthenticator.isKeyValid()) {
            Optional<ApiKey> apiKey = apiKeyDao.findByApiKey(key);
            String email = apiKey.get().getEmail();
            saveLog(email, "getQuizByDifficulty");


            List<Question> quizList = quizDao.getQuizByDifficulty(difficulty, PageRequest.of(0, limit));
            if (!quizList.isEmpty()) {
                return ResponseEntity.ok(quizList);
            }
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<List<Question>> getQuizByCategoryAndDifficulty(String key, int limit, String category, String difficulty) {
        keyAuthenticator.setKey(key);

        if (keyAuthenticator.isKeyValid()) {
            Optional<ApiKey> apiKey = apiKeyDao.findByApiKey(key);
            String email = apiKey.get().getEmail();
            saveLog(email, "getQuizByCategoryAndDifficulty");

            List<Question> quizList = quizDao.getQuizByCategoryAndDifficulty(category, difficulty, PageRequest.of(0, limit));
            if (!quizList.isEmpty()) {
                return ResponseEntity.ok(quizList);
            }
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<List<String>> getAllCategory() {
        List<String> categoryList = quizDao.getAllCategory();
        return ResponseEntity.ok(categoryList);
    }

    public ResponseEntity<Integer> getScore(String key, List<Integer> questionIds, List<Integer> answers) {
        keyAuthenticator.setKey(key);
        if (keyAuthenticator.isKeyValid()) {
            Optional<ApiKey> apiKey = apiKeyDao.findByApiKey(key);
            String email = apiKey.get().getEmail();
            saveLog(email, "getScore");

            int scoreCount = 0;
            System.out.println(questionIds);
            System.out.println(answers);
            if(questionIds.size() == answers.size()) {
                for(int i = 0; i < questionIds.size(); i++) {
                    Optional<Question> question = questionDao.findById(questionIds.get(i));
                    switch (answers.get(i)) {
                        case 1:
                            if(Objects.equals(question.get().getOption1(), question.get().getRightAnswer())){
                                scoreCount++;
                            }
                            break;
                        case 2:
                            if(Objects.equals(question.get().getOption2(), question.get().getRightAnswer())){
                                scoreCount++;
                            }
                            break;
                        case 3:
                            if(Objects.equals(question.get().getOption3(), question.get().getRightAnswer())){
                                scoreCount++;
                            }
                            break;
                        case 4:
                            if(Objects.equals(question.get().getOption4(), question.get().getRightAnswer())){
                                scoreCount++;
                            }
                            break;
                    }
                }
                QuizScore quizScore = new QuizScore();
                quizScore.setEmail(email);
                quizScore.setScored((double) scoreCount);
                quizScore.setOutOf((double) questionIds.size());
                quizScore.setPercentage((double) (scoreCount * 100) / questionIds.size());
                quizScoreDao.save(quizScore);
            }
            else{
                return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(scoreCount, HttpStatus.OK);
        }
        return new ResponseEntity<>(0, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<List<QuizScore>> getAllScoreByPercentage() {
        List<QuizScore> quizScores = quizScoreDao.findAll();
        quizScores.sort(Comparator.comparingDouble(QuizScore::getPercentage).reversed());
        return new ResponseEntity<>(quizScores, HttpStatus.OK);
    }
}
