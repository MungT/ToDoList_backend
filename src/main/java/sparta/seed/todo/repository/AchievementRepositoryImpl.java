package sparta.seed.todo.repository;

import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import sparta.seed.login.domain.Member;
import sparta.seed.todo.domain.QAchievement;
import sparta.seed.todo.dto.AchievementResponseDto;
import sparta.seed.todo.dto.QAchievementResponseDto;
import sparta.seed.todo.dto.QTodoResponseDto;
import sparta.seed.todo.dto.TodoResponseDto;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static sparta.seed.todo.domain.QAchievement.*;
import static sparta.seed.todo.domain.QTodo.*;


public class AchievementRepositoryImpl implements AchievementRepositoryCustom{

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
    public List<TodoResponseDto> getAchievementRateByDate(LocalDate selectedDate, String nickname){
        return queryFactory
                .select(new QTodoResponseDto(todo.isComplete, todo.count()))
                .from(todo)
                .where(todo.nickname.eq(nickname),
                        todo.addDate.eq(selectedDate))
                .groupBy(todo.isComplete)
                .fetch();
    }
    public List<AchievementResponseDto> getThisWeekAchievementRate(LocalDate startDate, LocalDate endDate, String nickname){
        return queryFactory
                .select(new QAchievementResponseDto(achievement.id, achievement.addDate, achievement.score))
                .from(achievement)
                .where(achievement.nickname.eq(nickname),
                       achievement.addDate.between(startDate,endDate))
                .orderBy(achievement.id.asc())
                .fetch();
    }
    public AchievementResponseDto getThisMonthAchievementRate(LocalDate startDate, LocalDate endDate, String nickname){
        return queryFactory
                .select(new QAchievementResponseDto(achievement.score.sum(), achievement.count()))
                .from(achievement)
                .where(achievement.nickname.eq(nickname),
                        achievement.addDate.between(startDate,endDate))
                .groupBy(achievement.nickname)
                .fetchOne();
    }
    public List<AchievementResponseDto> getDaylyAchievementRate(LocalDate stardDate, LocalDate endDate, String nickname) {
        return queryFactory
                .select(new QAchievementResponseDto(achievement.id, achievement.addDate, achievement.score))
                .from(achievement)
                .where(achievement.nickname.eq(nickname),
                        achievement.addDate.between(stardDate,endDate))
                .orderBy(achievement.id.asc())
                .fetch();
    }
    public List<TodoResponseDto> getWeeklyAchievementRate(LocalDate stardDate, LocalDate endDate, Member member) {
        return queryFactory
                .select(new QTodoResponseDto(todo.isComplete, todo.count()))
                .from(todo)
                .where(todo.nickname.eq(member.getNickname()))
                .where(todo.addDate.between(stardDate, endDate))
                .groupBy(todo.isComplete)
                .fetch();
    }
    public AchievementResponseDto getTotalAchievementRate(String nickname) {
        return queryFactory
                .select(new QAchievementResponseDto(MathExpressions.round(achievement.score.sum(), 2), achievement.count()))
                .from(achievement)
                .where(achievement.nickname.eq(nickname)) //가짜데이터라 주석처리
                .groupBy(achievement.nickname)
                .fetchOne();
    }
    public Long getPlannerCnt(Member member){
        return queryFactory
                .select(achievement.count())
                .from(achievement)
                .where(achievement.nickname.eq(member.getNickname()))
                .groupBy(achievement.nickname)
                .fetchOne();
    }
}
