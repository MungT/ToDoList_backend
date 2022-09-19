package sparta.seed.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.seed.sercurity.UserDetailsImpl;
import sparta.seed.todo.domain.Category;
import sparta.seed.todo.dto.AchievementResponseDto;
import sparta.seed.todo.dto.CategoryRequestDto;
import sparta.seed.todo.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/api/todo/category")
    public ResponseEntity<List<Category>> getCategory(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return ResponseEntity.ok()
                .body(categoryService.getCategory(userDetailsImpl));
    }
    @PostMapping("/api/todo/category")
    public ResponseEntity<String> addCategory(@RequestBody CategoryRequestDto categoryRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return ResponseEntity.ok()
                .body(categoryService.addCategory(categoryRequestDto, userDetailsImpl));
    }
}
