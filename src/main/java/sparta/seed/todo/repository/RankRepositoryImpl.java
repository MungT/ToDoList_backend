package sparta.seed.todo.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import sparta.seed.login.domain.Member;
import sparta.seed.login.domain.QMember;
import sparta.seed.todo.domain.QRank;
import sparta.seed.todo.dto.*;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static sparta.seed.login.domain.QMember.*;
import static sparta.seed.todo.domain.QRank.*;
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

    public List<TodoResponseDto> getDaylyAchievementRate(LocalDate yesterDay) {
        return queryFactory
                .select(new QTodoResponseDto(member.nickname, todo.isComplete, todo.addDate, todo.count()))
                .from(todo)
                .join(todo.member, member)
                .where(todo.addDate.eq(yesterDay))
                .groupBy(todo.addDate, member.nickname, todo.isComplete)
                .fetch();
    }

    public List<AchievementResponseDto> getRankTable(LocalDate stardDate, LocalDate endDate) {
        return queryFactory
                .select(new QAchievementResponseDto(rank.nickname, rank.addDate, rank.score.sum()))
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
