package ptit.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ptit.blog.model.Blog;
import ptit.blog.model.user.User;

import java.util.Date;

@Repository
public interface BlogRepo extends JpaRepository<Blog, Long> {

    @Query("select b from Blog b WHERE (:contains is null or (b.title LIKE %:contains% ))" +
            "AND (:fromDate is null or :fromDate <= b.updatedAt)" +
            "AND (:toDate is null or :toDate >= b.updatedAt)")
    Page<Blog> search(@Param("contains") String contains,
                      @Param("fromDate") Date fromDate,
                      @Param("toDate") Date toDate,
                      Pageable pageable);
}
