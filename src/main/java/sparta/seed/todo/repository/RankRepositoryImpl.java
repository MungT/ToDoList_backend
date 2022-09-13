package sparta.seed.todo.repository;


import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import sparta.seed.todo.domain.QAchievement;
import sparta.seed.todo.domain.QRank;
import sparta.seed.todo.domain.Rank;
import sparta.seed.todo.dto.AchievementResponseDto;
import sparta.seed.todo.dto.QAchievementResponseDto;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static sparta.seed.todo.domain.QAchievement.*;
import static sparta.seed.todo.domain.QRank.*;


public class RankRepositoryImpl implements RankRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public RankRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AchievementResponseDto> saveRank(LocalDate stardDate, LocalDate endDate) {
        return queryFactory
                .select(new QAchievementResponseDto(achievement.nickname, MathExpressions.round(achievement.score.sum(), 2)))
                .from(achievement)
                .where(achievement.addDate.between(stardDate, endDate))
                .groupBy(achievement.nickname)
                .orderBy(achievement.score.sum().desc())
                .fetch();
    }

    public Slice<AchievementResponseDto> getWeeklyPage(Pageable pageable) {
        QueryResults<Rank> result = queryFactory
                .selectFrom(rank)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .where(rank.category.eq("주간"))
                .orderBy(rank.ranking.asc(), rank.id.asc())
                .fetchResults();

        List<AchievementResponseDto> achievementResponseDtoList = new ArrayList<>();
        for (Rank eachRank : result.getResults()) {
            achievementResponseDtoList.add(AchievementResponseDto.builder()
                    .id(eachRank.getId())
                    .achievementRate(eachRank.getScore())
                    .nickname(eachRank.getNickname())
                    .rank(eachRank.getRanking())
                    .build());
        }

        boolean hasNext = false;
        if (achievementResponseDtoList.size() > pageable.getPageSize()) {
            achievementResponseDtoList.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(achievementResponseDtoList, pageable, hasNext);
    }
}
