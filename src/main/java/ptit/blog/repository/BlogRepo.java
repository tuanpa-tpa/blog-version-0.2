package ptit.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.blog.model.Blog;

@Repository
public interface BlogRepo extends JpaRepository<Blog, Long> {
}
