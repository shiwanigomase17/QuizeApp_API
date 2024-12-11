package org.shiwani.technicalquize2.dao;

import org.shiwani.technicalquize2.pojo.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "SELECT * FROM question WHERE category = :category ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Question> findRandomByCategory(@Param("category") String category, @Param("limit") int limit);
}
