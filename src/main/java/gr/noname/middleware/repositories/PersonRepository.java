package gr.noname.middleware.repositories;

import gr.noname.middleware.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findAllByEmail(String email);
    List<Person> findByEmailStartingWith(String email);

    @Query("SELECT c FROM Person c WHERE Concat(c.email, c.firstName, c.lastName, c.address, c.contactDetails) LIKE %?1%")
    List<Person> freeTextSearch(String query);

}