package gr.noname.mvc;

import gr.noname.middleware.entities.Comment;
import gr.noname.middleware.repositories.CommentRepository;
import gr.noname.mvc.models.CommentSearch;
import gr.noname.mvc.models.PersonSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CommentWebController {
    private final CommentRepository repository;

    CommentWebController(CommentRepository repository) { this.repository = repository; }

    @PostMapping("/comments")
    public Object searchCommentSubmit(
            @ModelAttribute CommentSearch searchModel) {
        return "redirect:/comments?searchByComment=" + searchModel.getComment();
    }
    
    @GetMapping("/comments")
    public Object showComments(
            Model model,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String searchByComment
    ) {
        if (page < 1) {
            return new RedirectView("/comments?page=1&size="+ size);
        };

        //Page<Comment> comments = findPaginated(repository.findAll(), PageRequest.of(page - 1, size));

        Page<Comment> comments = findPaginated(
                !searchByComment.equals("") ?
                        repository.findByCommentStartingWith(searchByComment):
                        repository.findAll(),
                PageRequest.of(page - 1, size)
        );

        int totalPages = comments.getTotalPages();

        if (page > totalPages) {
            return new RedirectView("/comments?size="+ size + "&page=" + totalPages);
        };

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(Math.max(1, page-2), Math.min(page + 2, totalPages))
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("page", page);
        model.addAttribute("comments", comments);
        model.addAttribute("searchModel", new CommentSearch(searchByComment));
        return "comments";
    }

    @GetMapping("/people/comments/{id}")
    public String personComments(@PathVariable("id") long id, Model model) {
        List<Comment> comments = new ArrayList<>();
        List<Comment> allComments = repository.findAll();
//        System.out.println(allComments[(int)id].getPerson());
        for (Comment comment : allComments) {
            if (comment.getPerson().getId().equals(id)) {
                comments.add(comment);
                System.out.println(id);
            }
        }


        model.addAttribute("comments", comments);
        return "person-comments";
    }




    private Page<Comment> findPaginated(List<Comment> comments, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Comment> result;

        if (comments.size() < startItem) {
            result = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, comments.size());
            result = comments.subList(startItem, toIndex);
        }

        Page<Comment> commentPage = new PageImpl<Comment>(result, PageRequest.of(currentPage, pageSize), comments.size());

        return commentPage;
    }
}
