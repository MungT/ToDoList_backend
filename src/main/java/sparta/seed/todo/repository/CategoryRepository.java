package sparta.seed.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.seed.todo.domain.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByNickname(String nickname);
}
