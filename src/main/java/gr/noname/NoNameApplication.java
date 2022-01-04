package gr.noname;

import gr.noname.middleware.repositories.PersonRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
//@EnableMongoRepositories(basePackageClasses = PersonRepository.class) //gia mongo
public class NoNameApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoNameApplication.class, args);
	}

}
