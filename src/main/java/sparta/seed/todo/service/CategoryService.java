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
import sparta.seed.todo.dto.CategoryRequestDto;
import sparta.seed.todo.repository.CategoryRepository;
import sparta.seed.todo.repository.TodoRepository;
import sparta.seed.util.TimeCustom;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final TodoRepository todoRepository;
    private final TimeCustom timeCustom;

    public List<Category> getCategory(String nickname) {
        if(!memberRepository.existsByNickname(nickname))
                throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);

        return categoryRepository.findAllByNickname(nickname);

    }

    public String addCategory(CategoryRequestDto categoryRequestDto, UserDetailsImpl userDetailsImpl) {
        Member member = memberRepository.findByUsername(userDetailsImpl.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        categoryRepository.save(
                Category.builder()
                        .nickname(member.getNickname())
                        .title(categoryRequestDto.getTitle()).build());

        return Message.CATEGORY_UPLOAD_SUCCESS.getMessage();
    }
    @Transactional
    public String updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto, UserDetailsImpl userDetailsImpl) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
        Member member = memberRepository.findByUsername(userDetailsImpl.getUsername())
                .orElseThrow( ()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        LocalDate today = timeCustom.currentDate();
        todoRepository.updateTodayTodoOfCategory(member.getNickname(),category.getTitle(), today, categoryRequestDto.getTitle());
        category.setTitle(categoryRequestDto.getTitle());
        categoryRepository.save(category);
        return Message.CATEGORY_UPDATE_SUCCESS.getMessage();
    }

    @Transactional
    public String deleteCategory(Long categoryId, UserDetailsImpl userDetailsImpl) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
        Member member = memberRepository.findByUsername(userDetailsImpl.getUsername())
                        .orElseThrow( ()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        LocalDate today = timeCustom.currentDate();
        todoRepository.deleteTodayTodoOfCategory(member.getNickname(),category.getTitle(), today);
        categoryRepository.deleteById(categoryId);
        return Message.CATEGORY_DELETE_SUCCESS.getMessage();
    }
}
