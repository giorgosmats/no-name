package gr.noname.rest;

import gr.noname.middleware.entities.Person;
import gr.noname.middleware.repositories.PersonRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {
    private final PersonRepository repository;

    PersonController(PersonRepository repository) { this.repository = repository; }


    @GetMapping(value = "/api/people")
    List<Person> GetPeople(
            @RequestParam(name = "emailStartsWith", required = false) String emailStartsWith
    ) {

        if (emailStartsWith != null && emailStartsWith != "") {
            return repository.findByEmailStartingWith(emailStartsWith);
        }
        return repository.findAll();
    }

    @GetMapping("/api/people/{id}")
    Person GetPerson(@PathVariable("id") Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find person with id " + id));
    }

    @PutMapping("/api/people/{id}")
    Person updateCar(@RequestBody Person newPerson, @PathVariable Long id) {

        return repository.findById(id)
                .map(match -> {
                    match.setEmail(newPerson.getEmail());
                    match.setPassword(newPerson.getPassword());
                    match.setFirstName(newPerson.getFirstName());
                    match.setLastName(newPerson.getLastName());
                    match.setGender(newPerson.getGender());
                    match.setAddress(newPerson.getAddress());
                    match.setContactDetails(newPerson.getContactDetails());
                    return repository.save(match);
                })
                .orElseGet(() -> {
                    newPerson.setId(id);
                    return repository.save(newPerson);
                });
    }

    @DeleteMapping("/api/people")
    void deleteAllPeople() {
    repository.deleteAll();
}

    @DeleteMapping("/api/people/{id}")
    void deletePerson(@PathVariable Long id) {
        repository.deleteById(id);
    }


}
