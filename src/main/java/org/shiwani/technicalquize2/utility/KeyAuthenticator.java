package org.shiwani.technicalquize2.utility;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.shiwani.technicalquize2.dao.ApiKeyDao;
import org.shiwani.technicalquize2.pojo.ApiKey;
import org.shiwani.technicalquize2.pojo.UserApiKey;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Data
@RequiredArgsConstructor
public class KeyAuthenticator {

    private final JoinUsersAndApiKey joinUsersAndApiKey;
    private final ApiKeyDao apiKeyDao;
    private String key;
    Optional<ApiKey> apiKeyObj;

    public boolean isKeyValid(){
        apiKeyObj = apiKeyDao.findByApiKey(key);
        return apiKeyObj.isPresent();
    }

    public boolean isKeyValidAndAdmin(){
        List<UserApiKey> userApiKeys = joinUsersAndApiKey.getEmailRoleAndApiKeyByApiKey();
        return !userApiKeys.isEmpty();
    }

}
