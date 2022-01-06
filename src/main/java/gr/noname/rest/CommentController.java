package gr.noname.rest;

import gr.noname.middleware.entities.Comment;
import gr.noname.middleware.repositories.CommentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentRepository repository;

    CommentController(CommentRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/api/comments")
    List<Comment> GetComments(
            @RequestParam(name = "commentStartsWith", required = false) String commnentStartsWith
    ) {

        if (commnentStartsWith != null && commnentStartsWith != "") {
            return repository.findByCommentStartingWith(commnentStartsWith);
        }
        return repository.findAll();
    }

    @GetMapping("/api/comments/{id}")
    Comment GetComment(@PathVariable("id") Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find comment with id " + id));
    }

    @PutMapping("/api/comments/{id}")
    Comment updateComment(@RequestBody Comment newComment, @PathVariable Long id) {

        return repository.findById(id)
                .map(match -> {
                    match.setComment(newComment.getComment());
                    match.setPerson(newComment.getPerson());
                    return repository.save(match);
                })
                .orElseGet(() -> {
                    newComment.setId(id);
                    return repository.save(newComment);
                });
    }
    @DeleteMapping("/api/comments")
    void deleteAllCars() {
    repository.deleteAll();
}

    @DeleteMapping("/api/comments/{id}")
    void deleteCar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
