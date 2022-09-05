//package sparta.seed.todo.repository;
//
//import com.querydsl.core.types.Expression;
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.support.PageableExecutionUtils;
//import sparta.seed.todo.domain.QTodo;
//import sparta.seed.todo.dto.QTodoResponseDto;
//import sparta.seed.todo.dto.TodoResponseDto;
//import sparta.seed.todo.dto.TodoSearchCondition;
//
//
//import javax.persistence.EntityManager;
//
//import java.util.List;
//
//import static org.springframework.util.ObjectUtils.isEmpty;
//import static sparta.seed.todo.domain.QTodo.*;
//
//
//public class TodoRepositoryImpl implements TodoRepositoryCustom {
//    private final JPAQueryFactory queryFactory;
//
//    public TodoRepositoryImpl(EntityManager em) {
//        this.queryFactory = new JPAQueryFactory(em);
//    }
//
//    //회원명, 팀명, 나이(ageGoe, ageLoe)
////    @Override
//    public List<TodoResponseDto> search(TodoSearchCondition condition) {
//        return queryFactory
//                .select(new QTodoResponseDto(todo.id, todo.content,todo.isComplete))
//                .from(todo)
//                .where(todo.createdAt.between
//                .where(todo.createdAt.between(condition.getSearchStartDate(), condition.getSearchEndDate()))
//                .fetch();
//    }
//}
