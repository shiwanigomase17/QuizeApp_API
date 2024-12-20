package org.shiwani.technicalquize2.utility;


import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Component
public class ApiKeyGenerator {

    public String generateApiKey(String email) {
        try {
            UUID uuid = UUID.randomUUID();
            String uniqueId = uuid.toString();

            String combinedString = email + uniqueId;

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(combinedString.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}

