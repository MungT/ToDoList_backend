package sparta.seed.school.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;
import sparta.seed.login.repository.MemberRepository;
import sparta.seed.school.domain.School;
import sparta.seed.school.dto.SchoolRequestDto;
import sparta.seed.school.service.SchoolService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class SchoolController {

    private final MemberRepository memberRepository;
    private final SchoolService schoolService;


    @PostMapping("/api/school")
    public Boolean saveSchool() throws IOException {
        return schoolService.saveSchoolList();
    }
    @GetMapping("/api/school")
    public List<School> getSchool(@ModelAttribute SchoolRequestDto schoolRequestDto){
        return schoolService.getSchoolList(schoolRequestDto);
    }

}
