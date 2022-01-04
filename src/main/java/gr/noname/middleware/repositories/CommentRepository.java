package gr.noname.middleware.repositories;

import gr.noname.middleware.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}