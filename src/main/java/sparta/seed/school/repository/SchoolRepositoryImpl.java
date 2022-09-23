package sparta.seed.school.repository;


import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import sparta.seed.school.domain.School;
import sparta.seed.school.dto.SchoolRequestDto;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static sparta.seed.school.domain.QSchool.school;


public class SchoolRepositoryImpl implements SchoolRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public SchoolRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    public Slice<School> getSchoolListPage(SchoolRequestDto schoolRequestDto, Pageable pageable) {
        List<School> result = queryFactory
                .selectFrom(school)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .where(school.schoolName.contains(schoolRequestDto.getSearch()))
                .fetch();

        List<School> schoolList = new ArrayList<>();
        for (School eachSchool : result) {
            schoolList.add(School.builder()
                    .id(eachSchool.getId())
                    .schoolName(eachSchool.getSchoolName())
                    .build());
        }

        boolean hasNext = false;
        if (schoolList.size() > pageable.getPageSize()) {
            schoolList.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(schoolList, pageable, hasNext);
    }
}
