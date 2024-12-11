package org.shiwani.technicalquize2.dao;

import jakarta.transaction.Transactional;
import org.shiwani.technicalquize2.pojo.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiKeyDao extends JpaRepository<ApiKey,Integer> {

    Optional<List<ApiKey>> findByEmail(String email);

    @Query(value = "SELECT COUNT(*) FROM api_key a WHERE a.email = ?1", nativeQuery = true)
    long countByEmail(String email);

    Optional<ApiKey> findByApiKey(String apiKey);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM api_key a WHERE a.email = ?1", nativeQuery = true)
    void deleteApiKeyByEmail(String email);

}
