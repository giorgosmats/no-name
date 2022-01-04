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
    List<Person> showPeople(){return repository.findAll();}

//    @GetMapping("/api/people/{id}")
//    Person showPerson(@PathVariable("id") Long id){}
//
//    @PutMapping("/api/people/{id}")
//    Person upadatePerson(@RequestParam Person newPerson, @PathVariable("id") Long id){}
//
//    @DeleteMapping("/api/people/{id}")
//    void deletePerson(@PathVariable("id") Long id){repository.deleteById(id);}

}
