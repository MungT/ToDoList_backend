package sparta.seed.school.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import sparta.seed.school.domain.School;
import sparta.seed.school.dto.SchoolRequestDto;

import java.util.List;

public interface SchoolRepositoryCustom {
    Slice<School> getSchoolListPage(SchoolRequestDto schoolRequestDto, Pageable pageable);
}

