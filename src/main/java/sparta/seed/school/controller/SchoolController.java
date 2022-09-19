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
    public Slice<School> getSchool(@ModelAttribute SchoolRequestDto schoolRequestDto, Pageable pageable){
        return schoolService.getSchoolList(schoolRequestDto,pageable);
    }

}
