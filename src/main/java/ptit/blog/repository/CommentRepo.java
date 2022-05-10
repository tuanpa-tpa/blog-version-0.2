package ptit.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.blog.model.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
}
