package sparta.seed.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import sparta.seed.login.domain.Member;
import sparta.seed.todo.domain.Todo;
import sparta.seed.todo.dto.QTodoResponseDto;
import sparta.seed.todo.dto.TodoResponseDto;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static sparta.seed.todo.domain.QTodo.todo;


public class TodoRepositoryImpl implements TodoRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public TodoRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<TodoResponseDto> findAllbyAddDateAndMember(LocalDate addDate, Member member){
        return queryFactory
                .select(new QTodoResponseDto(todo.id, todo.content, todo.isComplete, todo.addDate))
                .from(todo)
                .where(todo.member.eq(member),
                        todo.addDate.eq(addDate))
                .fetch();
    }

    public List<TodoResponseDto> getAchievementRateByDate(LocalDate addDate, Member member){
        return queryFactory
                .select(new QTodoResponseDto(todo.isComplete, todo.addDate, todo.count()))
                .from(todo)
                .where(todo.member.eq(member),
                        todo.addDate.eq(addDate))
                .groupBy(todo.isComplete)
                .fetch();
    }

    public List<TodoResponseDto> getWeeklyAchievementRate(LocalDate stardDate, LocalDate endDate, Member member){
        return queryFactory
                .select(new QTodoResponseDto(todo.isComplete, todo.addDate, todo.count()))
                .from(todo)
                .where(todo.member.eq(member),
                        todo.addDate.between(stardDate, endDate))
                .groupBy(todo.isComplete)
                .fetch();
    }
    public LocalDate getFirstTodoAddDate(Member member){
        return queryFactory
                .select(todo.addDate.min())
                .from(todo)
                .where(todo.member.eq(member))
                .fetchOne();

    }
}
