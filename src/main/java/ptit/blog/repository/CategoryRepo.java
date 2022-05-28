package ptit.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptit.blog.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    @Query("select c from Category c where (:categoryName is null or c.categoryName like %:categoryName%)")
    Category findByCategoryName(@Param("categoryName") String categoryName);
}
