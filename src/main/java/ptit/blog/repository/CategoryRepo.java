package ptit.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ptit.blog.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
}
