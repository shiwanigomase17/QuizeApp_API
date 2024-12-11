package org.shiwani.technicalquize2.dao;

import org.shiwani.technicalquize2.pojo.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogsDao extends JpaRepository<Logs, Long> {
}
