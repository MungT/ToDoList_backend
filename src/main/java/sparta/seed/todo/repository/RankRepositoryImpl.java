package sparta.seed.todo.repository;


import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import sparta.seed.todo.dto.AchievementResponseDto;
import sparta.seed.todo.dto.QAchievementResponseDto;
import sparta.seed.todo.dto.QTodoResponseDto;
import sparta.seed.todo.dto.TodoResponseDto;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static sparta.seed.todo.domain.QRank.rank;
import static sparta.seed.todo.domain.QTodo.todo;


public class RankRepositoryImpl implements RankRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public RankRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

//    public List<TodoResponseDto> getAchievementRateByDate(LocalDate selectedDate, Member member){
//        return queryFactory
//                .select(new QTodoResponseDto(todo.isComplete, todo.count()))
//                .from(todo)
//                .where(todo.member.eq(member),
//                        todo.addDate.eq(selectedDate))
//                .groupBy(todo.isComplete)
//                .fetch();
//    }

    public List<TodoResponseDto> saveRankTable(LocalDate yesterDay) {
        return queryFactory
                .select(new QTodoResponseDto(todo.nickname, todo.isComplete, todo.addDate, todo.count()))
                .from(todo)
//                .where(todo.addDate.eq(yesterDay)) //실 서비스에서는 하루 단위로 스케쥴러
                .where(todo.addDate.between(yesterDay.minusDays(30), yesterDay))
                .groupBy( todo.nickname,todo.addDate, todo.isComplete)
                .fetch();
    }

    public List<AchievementResponseDto> getRankTable(LocalDate stardDate, LocalDate endDate) {
        return queryFactory
                .select(new QAchievementResponseDto(rank.nickname, MathExpressions.round(rank.score.sum(),2)))
                .from(rank)
                .where(rank.addDate.between(stardDate,endDate))
                .groupBy(rank.nickname)
                .orderBy(rank.score.sum().desc())
                .fetch();
    }


//    public List<TodoResponseDto> getWeeklyAchievementRate(LocalDate stardDate, LocalDate endDate, Member member) {
//        return queryFactory
//                .select(new QTodoResponseDto(todo.isComplete, todo.count()))
//                .from(todo)
////                .where(todo.member.eq(member), //가짜데이터라 주석처리
//                .where(todo.addDate.between(stardDate, endDate))
//                .groupBy(todo.isComplete)
//                .fetch();
//    }
//
//    public TodoDateResponseDto getFirstandLastTodoAddDate(Member member) {
//        return queryFactory
//                .select(new QTodoDateResponseDto(todo.addDate.min(), todo.addDate.max()))
//                .from(todo)
////                .where(todo.member.eq(member))   //가짜데이터를 사용했기때문에 주석처리
//                .fetchOne();
//    }
}
