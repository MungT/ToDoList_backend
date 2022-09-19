package sparta.seed.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.seed.exception.CustomException;
import sparta.seed.exception.ErrorCode;
import sparta.seed.login.domain.Member;
import sparta.seed.login.repository.MemberRepository;
import sparta.seed.message.Message;
import sparta.seed.sercurity.UserDetailsImpl;
import sparta.seed.todo.domain.Category;
import sparta.seed.todo.domain.Todo;
import sparta.seed.todo.dto.CategoryRequestDto;
import sparta.seed.todo.dto.TodoRequestDto;
import sparta.seed.todo.dto.TodoResponseDto;
import sparta.seed.todo.repository.CategoryRepository;
import sparta.seed.todo.repository.TodoRepository;
import sparta.seed.util.TimeCustom;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    public List<Category> getCategory(UserDetailsImpl userDetailsImpl) {
        Member member = memberRepository.findById(userDetailsImpl.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return categoryRepository.findAllByNickname(member.getNickname());

    }

    public String addCategory(CategoryRequestDto categoryRequestDto, UserDetailsImpl userDetailsImpl) {
        Member member = memberRepository.findById(userDetailsImpl.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        categoryRepository.save(Category.builder()
                .nickname(member.getNickname())
                .title(categoryRequestDto.getTitle()).build());

        return Message.CATEGORY_UPLOAD_SUCCESS.getMessage();
    }
}
