package sparta.seed.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.seed.exception.CustomException;
import sparta.seed.exception.ErrorCode;
import sparta.seed.login.domain.Member;
import sparta.seed.login.repository.MemberRepository;
import sparta.seed.message.Message;
import sparta.seed.sercurity.UserDetailsImpl;
import sparta.seed.todo.domain.Category;
import sparta.seed.todo.dto.CategoryRequestDto;
import sparta.seed.todo.repository.CategoryRepository;

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
    public String updateCategory(Long categoryId, CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
        category.setTitle(categoryRequestDto.getTitle());
        categoryRepository.save(category);
        return Message.CATEGORY_UPDATE_SUCCESS.getMessage();
    }
    public String deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return Message.CATEGORY_DELETE_SUCCESS.getMessage();
    }
}
