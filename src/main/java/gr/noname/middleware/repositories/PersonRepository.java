package gr.noname.middleware.repositories;

import gr.noname.middleware.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findAllByEmail(String email);
    List<Person> findByEmailStartingWith(String email);

}