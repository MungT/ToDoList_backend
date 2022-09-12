package sparta.seed.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import sparta.seed.todo.dto.QTodoResponseDto;
import sparta.seed.todo.dto.TodoResponseDto;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static sparta.seed.todo.domain.QTodo.todo;

public class AchievementRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public AchievementRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<TodoResponseDto> getRecentlyRate(LocalDate yesterDay) {
        return queryFactory
                .select(new QTodoResponseDto(todo.nickname, todo.isComplete, todo.addDate, todo.count()))
                .from(todo)
//                .where(todo.addDate.eq(yesterDay)) //실 서비스에서는 하루 단위로 스케쥴러
                .where(todo.addDate.between(yesterDay.minusDays(30), yesterDay))
                .groupBy( todo.nickname,todo.addDate, todo.isComplete)
                .fetch();
    }
}
