package org.shiwani.technicalquize2.utility;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.shiwani.technicalquize2.pojo.UserApiKey;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JoinUsersAndApiKey {

    private final EntityManager entityManager;

    public List<UserApiKey> getEmailRoleAndApiKeyByApiKey() {
        String sqlQuery = "SELECT u.email, u.role, k.api_key " +
                "FROM users u " +
                "JOIN api_key k ON u.email = k.email " +
                "WHERE u.role = 'admin'";

        Query query = entityManager.createNativeQuery(sqlQuery);

        List<Object[]> results = query.getResultList();
        List<UserApiKey> userApiKeys = new ArrayList<>();

        for (Object[] result : results) {
            UserApiKey userApiKey = new UserApiKey();
            userApiKey.setEmail((String) result[0]);
            userApiKey.setRole((String) result[1]);
            userApiKey.setApiKey((String) result[2]);
            userApiKeys.add(userApiKey);
        }

        return userApiKeys;
    }
}
