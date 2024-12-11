package org.shiwani.technicalquize2.dao;

import org.shiwani.technicalquize2.pojo.QuizScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuizScoreDao extends JpaRepository<QuizScore, Integer> {

}
