package org.shiwani.technicalquize2.dao;

import org.shiwani.technicalquize2.pojo.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizDao extends JpaRepository<Question, Integer> {

    @Query("SELECT q FROM Question q ORDER BY RANDOM()")
    List<Question> getQuiz(Pageable pageable);

    @Query("SELECT q FROM Question q WHERE q.category = :category AND q.difficultyLevel = :difficulty ORDER BY RANDOM()")
    List<Question> getQuizByCategoryAndDifficulty(String category, String difficulty, Pageable pageable);

    @Query("SELECT q FROM Question q WHERE q.difficultyLevel = :difficulty ORDER BY RANDOM()")
    List<Question> getQuizByDifficulty(String difficulty, Pageable pageable);

    @Query("SELECT q FROM Question q WHERE q.category = :category ORDER BY RANDOM()")
    List<Question> getQuizByCategory(String category, Pageable pageable);

    @Query("SELECT DISTINCT q.category FROM Question q")
    List<String> getAllCategory();

}
