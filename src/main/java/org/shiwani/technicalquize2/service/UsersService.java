package org.shiwani.technicalquize2.service;

import lombok.RequiredArgsConstructor;
import org.shiwani.technicalquize2.dao.ApiKeyDao;
import org.shiwani.technicalquize2.dao.UsersDao;
import org.shiwani.technicalquize2.dto.UserDTO;
import org.shiwani.technicalquize2.pojo.Users;
import org.shiwani.technicalquize2.utility.CustomPasswordEncoder;
import org.shiwani.technicalquize2.utility.KeyAuthenticator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersDao usersDao;
    private final ApiKeyDao apiKeyDao;
    private final CustomPasswordEncoder passwordEncoder;
    private final KeyAuthenticator keyAuthenticator;

    private boolean userExists(String email) {
        return usersDao.findById(email).isPresent();
    }

    public ResponseEntity<String> register(Users user) {

        if (!userExists(user.getEmail())) {
            try {
                String password = user.getPassword();
                String salt = passwordEncoder.generateSalt();
                String encodedPassword = passwordEncoder.hashPassword(password, salt);

                user.setRole("user");
                user.setPassword(encodedPassword);
                user.setSalt(salt);
                usersDao.save(user);

                return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
            } catch (Exception e) {
                String errorMessage = "{\"error\": \"An error occurred while processing your request\"}";
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }
        }
        else{
            String errorMessage = "{\"error\": \"User already exists\"}";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<String> delete(String password,String email) {


            if (userExists(email)) {
                String storedPassword = usersDao.findById(email).get().getPassword();
                String storedSalt = usersDao.findById(email).get().getSalt();
                String encodedPassword = passwordEncoder.hashPassword(password, storedSalt);

                if (encodedPassword.equals(storedPassword)) {
                    try {
                        usersDao.deleteById(email);
                        apiKeyDao.deleteApiKeyByEmail(email);
                        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
                    } catch (Exception e) {
                        String errorMessage = "{\"error\": \"An error occurred while processing your request\"}";
                        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
                    }
                }else{
                    String errorMessage = "{\"error\": \"Invalid password\"}";
                    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
                }
            }
            else{
                String errorMessage = "{\"error\": \"User does not exist\"}";
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }

    }

    public ResponseEntity<String> authenticate(String email, String password) {

        if (userExists(email)) {
            try {
                String storedPassword = usersDao.findById(email).get().getPassword();
                String storedSalt = usersDao.findById(email).get().getSalt();
                String encodedPassword = passwordEncoder.hashPassword(password, storedSalt);

                if(encodedPassword.equals(storedPassword)){;
                    return new ResponseEntity<>("{\"success\": \"User authenticated successfully\"}", HttpStatus.OK);
                }
                else{
                    String errorMessage = "{\"error\": \"Invalid password\"}";
                    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                String errorMessage = "{\"error\": \"An error occurred while processing your request\"}";
                return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else{
            String errorMessage = "{\"error\": \"User does not exist\"}";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> update(UserDTO user) {
        String email = user.getEmail();
        String oldPassword = user.getOldPassword();
        if(userExists(email)){
            try {
                String storedPassword = usersDao.findById(email).get().getPassword();
                String storedSalt = usersDao.findById(email).get().getSalt();
                String encodedPassword = passwordEncoder.hashPassword(oldPassword, storedSalt);

                Users oldUser = usersDao.findById(email).get();

                Users updateUser = new Users();
                updateUser.setEmail(email);

                if(encodedPassword.equals(storedPassword)){;
                    if(user.getName() != null){
                        updateUser.setName(user.getName());
                    }else{
                        updateUser.setName(oldUser.getName());
                    }
                    if(user.getNewPassword() != null){
                        String salt = oldUser.getSalt();
                        String encodedNewPassword = passwordEncoder.hashPassword(user.getNewPassword(), salt);
                        updateUser.setPassword(encodedNewPassword);
                        updateUser.setSalt(salt);
                    }else{
                        updateUser.setPassword(storedPassword);
                        updateUser.setSalt(oldUser.getSalt());
                    }
                    updateUser.setRole(oldUser.getRole());
                    usersDao.save(updateUser);
                    return new ResponseEntity<>("{\"success\": \"User updated successfully\"}", HttpStatus.OK);
                }
                else{
                    String errorMessage = "{\"error\": \"Invalid password\"}";
                    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                e.printStackTrace();
                String errorMessage = "{\"error\": \"An error occurred while processing your request\"}";
                return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else{
            String errorMessage = "{\"error\": \"User does not exist\"}";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }
}
