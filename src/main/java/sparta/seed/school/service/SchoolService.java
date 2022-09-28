package sparta.seed.school.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.seed.exception.CustomException;
import sparta.seed.school.domain.School;
import sparta.seed.school.dto.SchoolRequestDto;
import sparta.seed.school.repository.SchoolRepository;
import sparta.seed.util.SchoolList;

import java.io.IOException;
import java.util.List;

import static sparta.seed.exception.ErrorCode.SCHOOLNAME_EMPTY;

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

    public List<School> getSchoolList(SchoolRequestDto schoolRequestDto) {
        if (schoolRequestDto.getSearch().isEmpty())
            return null;
        else
            return schoolRepository.getSchoolListPage(schoolRequestDto);
    }
}
