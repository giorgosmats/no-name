package gr.noname.middleware.repositories;

import gr.noname.middleware.entities.Comment;
import gr.noname.middleware.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
//    List<Comment> findAllByPersonId(Long id);
      List<Comment> findByCommentStartingWith(String comment);
}