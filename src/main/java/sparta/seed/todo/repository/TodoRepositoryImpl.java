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

    public List<TodoResponseDto> findAllbyAddDateAndMember(LocalDate addDate, Member member) {
        return queryFactory
                .select(new QTodoResponseDto(todo.id, todo.content, todo.isComplete, todo.addDate))
                .from(todo)
                .where(todo.member.eq(member),
                        todo.addDate.eq(addDate))
                .fetch();
    }

    public List<TodoResponseDto> getAchievementRateByDate(LocalDate selectedDate, Member member){
        return queryFactory
                .select(new QTodoResponseDto(todo.isComplete, todo.count()))
                .from(todo)
                .where(todo.member.eq(member),
                        todo.addDate.eq(selectedDate))
                .groupBy(todo.isComplete)
                .fetch();
    }

    public List<TodoResponseDto> getDaylyAchievementRate(LocalDate stardDate, LocalDate endDate, Member member) {
        return queryFactory
                .select(new QTodoResponseDto(todo.isComplete, todo.addDate, todo.count()))
                .from(todo)
                .where(todo.member.eq(member),
                        todo.addDate.between(stardDate,endDate))

                .groupBy(todo.addDate, todo.isComplete)
                .fetch();
    }


    public List<TodoResponseDto> getWeeklyAchievementRate(LocalDate stardDate, LocalDate endDate, Member member) {
        return queryFactory
                .select(new QTodoResponseDto(todo.isComplete, todo.count()))
                .from(todo)
//                .where(todo.member.eq(member), //가짜데이터라 주석처리
                .where(todo.addDate.between(stardDate, endDate))
                .groupBy(todo.isComplete)
                .fetch();
    }

    public TodoDateResponseDto getFirstandLastTodoAddDate(Member member) {
        return queryFactory
                .select(new QTodoDateResponseDto(todo.addDate.min(), todo.addDate.max()))
                .from(todo)
//                .where(todo.member.eq(member))   //가짜데이터를 사용했기때문에 주석처리
                .fetchOne();
    }
}
