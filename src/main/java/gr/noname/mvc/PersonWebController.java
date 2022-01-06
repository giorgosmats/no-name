package gr.noname.mvc;

import gr.noname.middleware.entities.Comment;
import gr.noname.middleware.entities.Gender;
import gr.noname.middleware.entities.Person;
import gr.noname.middleware.repositories.CommentRepository;
import gr.noname.middleware.repositories.PersonRepository;
import gr.noname.mvc.models.PersonSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class PersonWebController {
    private final PersonRepository repository;

    PersonWebController(PersonRepository repository, CommentRepository commentRepository) { this.repository = repository; }

    @PostMapping("/people")
    public Object searchPeopleSubmit(
            @ModelAttribute PersonSearch searchModel) {
        return "redirect:/people?searchByEmail=" + searchModel.getEmail();
    }

    @GetMapping("/people")
    public Object showPeople(
            Model model,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String searchByEmail
    ) {
        if (page < 1) {
            return new RedirectView("/people?page=1&size="+ size);
        };

        Page<Person> people = findPaginated(
                !searchByEmail.equals("") ?
                        repository.findByEmailStartingWith(searchByEmail):
                        repository.findAll(),
                PageRequest.of(page - 1, size)
        );

        int totalPages = people.getTotalPages();
        //System.out.println(totalPages);

        if (page > totalPages) {
            //return new RedirectView("/people?size="+ size + "&page=" + totalPages);
        };

        if (totalPages >= 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(Math.max(1, page-2), Math.min(page + 2, totalPages))
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("page", page);
        model.addAttribute("people", people);
        model.addAttribute("searchModel", new PersonSearch(searchByEmail));
        return "people";
    }

    @GetMapping("/people/addperson")
    public String addPerson(Model model) {
        model.addAttribute("gender_value", Gender.values());
        model.addAttribute("person", new Person());
        return "create-person";
    }

    @PostMapping("/people/addperson")
    public String addPerson(@Validated @ModelAttribute("person")Person person, BindingResult result, Model model) {
        if (result.hasErrors() || result.hasErrors()) {
            model.addAttribute("gender_value", Gender.values());
            return "create-person";
        }

        repository.save(person);
        model.addAttribute("person", person);
        return "redirect:/people";
    }


    @GetMapping("/people/update/{id}")
    public String updatePerson(@PathVariable("id") long id, Model model) {
        Person newperson = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id:" + id));

        model.addAttribute("newperson", newperson);
        return "update-person";
    }

    @PostMapping("/people/update/{id}")
    public String updatePerson(@PathVariable("id") long id, @Validated @ModelAttribute("newperson") Person newperson,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            newperson.setId(id);
            return "update-person";
        }

        repository.save(newperson);
        return "redirect:/people";
    }

    @GetMapping("/people/delete/{id}")
    public String deletePerson(@PathVariable("id") long id, Model model) {
        Person oldperson = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id:" + id));
        repository.delete(oldperson);
        return "redirect:/people";
    }

    private Page<Person> findPaginated(List<Person> people, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Person> result;

        if (people.size() < startItem) {
            result = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, people.size());
            result = people.subList(startItem, toIndex);
        }

        Page<Person> personPage = new PageImpl<Person>(result, PageRequest.of(currentPage, pageSize), people.size());

        return personPage;
    }
}
