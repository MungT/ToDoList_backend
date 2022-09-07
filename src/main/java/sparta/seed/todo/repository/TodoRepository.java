package sparta.seed.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.seed.todo.domain.Todo;

public interface TodoRepository extends JpaRepository<Todo,Long>, TodoRepositoryCustom {
}
