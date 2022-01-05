package gr.noname.mvc;

import gr.noname.middleware.entities.Comment;
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

        if (page > totalPages) {
            return new RedirectView("/people?size="+ size + "&page=" + totalPages);
        };

        if (totalPages > 0) {
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


    @GetMapping("/people/update/{id}")
    public String updatePerson(@PathVariable("id") long id, Model model) {
        Person person = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id:" + id));

        model.addAttribute("person", person);
        return "update-person";
    }

    @PostMapping("/people/update/{id}")
    public String updatePerson(@PathVariable("id") long id, @Validated Person person,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            person.setId(id);
            return "update-person";
        }

        repository.save(person);
        return "redirect:/people";
    }

    @GetMapping("/people/delete/{id}")
    public String deletePerson(@PathVariable("id") long id, Model model) {
        Person person = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id:" + id));
        repository.delete(person);
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
