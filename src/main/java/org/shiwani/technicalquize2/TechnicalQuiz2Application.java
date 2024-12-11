package org.shiwani.technicalquize2;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.shiwani.technicalquize2.dao.UsersDao;
import org.shiwani.technicalquize2.pojo.Users;
import org.shiwani.technicalquize2.utility.CustomPasswordEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@SpringBootApplication
public class TechnicalQuiz2Application {

	public static void main(String[] args) {
		SpringApplication.run(TechnicalQuiz2Application.class, args);

	}

	@Component
	@RequiredArgsConstructor
	public static class DatabaseChecker {

		private final UsersDao usersDao;
		private final CustomPasswordEncoder customPasswordEncoder;

		@PostConstruct
		public void checkDatabase() {
			Optional<Users> ad = usersDao.findById("gomasesa@rknec.edu");
			if (ad.isEmpty()) {

				String password = "123456";
				String salt = customPasswordEncoder.generateSalt();
				String encodedPassword = customPasswordEncoder.hashPassword(password, salt);

				Users rootUser = new Users("gomasesa@rknec.edu", "Shiwani", encodedPassword, salt, "admin");
				usersDao.save(rootUser);
				System.out.println("if executed");
			}
			else{
				System.out.println("else executed");
			}

		}
	}


}
