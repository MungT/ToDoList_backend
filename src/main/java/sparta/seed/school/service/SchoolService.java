package sparta.seed.school.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import sparta.seed.school.domain.School;
import sparta.seed.school.dto.SchoolRequestDto;
import sparta.seed.school.repository.SchoolRepository;
import sparta.seed.util.SchoolList;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;
    public Boolean saveSchoolList() throws IOException {
        SchoolList schoolList = new SchoolList();
        List<School> schools = schoolList.getSchoolList();
        schoolRepository.saveAll(schools);
        return true;
    }
    public Slice<School> getSchoolList(SchoolRequestDto schoolRequestDto, Pageable pageable){
        return schoolRepository.getSchoolListPage(schoolRequestDto, pageable);
    }
}