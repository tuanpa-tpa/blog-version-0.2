package ptit.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ptit.blog.model.Image;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
	Optional<Image> findByName(String name);
}
