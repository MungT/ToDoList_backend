package sparta.seed.todo.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import sparta.seed.login.domain.Member;

import sparta.seed.todo.dto.QTodoDateResponseDto;
import sparta.seed.todo.dto.QTodoResponseDto;
import sparta.seed.todo.dto.TodoDateResponseDto;
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
    public List<TodoResponseDto> getTodayTodo(LocalDate localDate, Member member) {
        return queryFactory
                .select(new QTodoResponseDto(todo.id, todo.content, todo.isComplete, todo.addDate, todo.category))
                .from(todo)
                .where(todo.nickname.eq(member.getNickname()),
                        todo.addDate.eq(localDate))
                .fetch();
    }
    public List<TodoResponseDto> getTodo(LocalDate addDate, Member member) {
        return queryFactory
                .select(new QTodoResponseDto(todo.id, todo.content, todo.isComplete, todo.addDate, todo.category))
                .from(todo)
                .where(todo.nickname.eq(member.getNickname()),
                        todo.addDate.eq(addDate))
                .fetch();
    }

    public TodoDateResponseDto getFirstandLastTodoAddDate(Member member) {
        return queryFactory
                .select(new QTodoDateResponseDto(todo.addDate.min(), todo.addDate.max()))
                .from(todo)
                .where(todo.nickname.eq(member.getNickname()))
                .fetchOne();
    }

    public TodoResponseDto getTotalCnt(String nickname){
        return queryFactory
                .select(new QTodoResponseDto(todo.count()))
                .from(todo)
                .where(todo.nickname.eq(nickname))
                .groupBy(todo.nickname)
                .fetchOne();
    }
}
