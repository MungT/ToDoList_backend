package sparta.seed.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import sparta.seed.login.domain.Member;
import sparta.seed.todo.dto.QTodoResponseDto;
import sparta.seed.todo.dto.TodoResponseDto;

import javax.persistence.EntityManager;
import java.util.List;

import static sparta.seed.todo.domain.QTodo.todo;


public class TodoRepositoryImpl implements TodoRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public TodoRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<TodoResponseDto> findAllbyAddDateAndMember(String addDate, Member member){
        return queryFactory
                .select(new QTodoResponseDto(todo.id, todo.content, todo.isComplete, todo.addDate))
                .from(todo)
                .where(todo.member.eq(member),
                        todo.addDate.eq(addDate))
                .fetch();
    }

    public List<TodoResponseDto> getAchievementRateByDate(String addDate, Member member){
        return queryFactory
                .select(new QTodoResponseDto(todo.isComplete, todo.addDate, todo.count()))
                .from(todo)
                .where(todo.member.eq(member),
                        todo.addDate.eq(addDate))
                .groupBy(todo.isComplete)
                .fetch();
    }

    //회원명, 팀명, 나이(ageGoe, ageLoe)
//    @Override
//    public List<TodoResponseDto> search(TodoSearchCondition condition) {
//        return queryFactory
//                .select(new QTodoResponseDto(todo.id, todo.content,todo.isComplete))
//                .from(todo)
//                .where(todo.createdAt.between
//                .where(todo.createdAt.between(condition.getSearchStartDate(), condition.getSearchEndDate()))
//                .fetch();
//    }

}
