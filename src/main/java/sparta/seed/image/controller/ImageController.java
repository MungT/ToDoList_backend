package sparta.seed.image.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sparta.seed.image.dto.ImageResponseDto;
import sparta.seed.image.dto.MottoRequestDto;
import sparta.seed.image.repository.ImageRepository;
import sparta.seed.image.service.ImageService;
import sparta.seed.login.dto.MemberResponseDto;
import sparta.seed.sercurity.UserDetailsImpl;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class ImageController {

    private final ImageService imageService;

    //프로필 이미지 등록
    @PostMapping("/profile")
    public ResponseEntity<String> saveProfileImageAndMotto(
            @RequestPart(required = false, value = "dto") MottoRequestDto mottoRequestDto,
            @RequestPart(required = false) MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws IOException {
        return ResponseEntity.ok(imageService.saveProfileImageAndMotto(mottoRequestDto, multipartFile, userDetailsImpl));
    }
    //프로필 이미지 삭제
    @DeleteMapping("/profile")
    public ResponseEntity<String> deleteProfileImage(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return ResponseEntity.ok(imageService.deleteProfileImage(userDetailsImpl));
    }
    //자랑 이미지 조회
    @GetMapping("/boast/{nickname}")
    public ResponseEntity<List<ImageResponseDto>> getBoastImage(@PathVariable String nickname){
        return ResponseEntity.ok(imageService.getBoastImage(nickname));
    }
    //자랑 이미지 저장
    @PostMapping("/boast")
    public ResponseEntity<String> saveBoastImage(@RequestPart MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws IOException {

        return ResponseEntity.ok(imageService.saveBoastImage(multipartFile, userDetailsImpl));
    }
    //자랑 이미지 삭제
    @DeleteMapping("/boast/{boastId}")
    public ResponseEntity<String> deleteBoastImage(@PathVariable Long boastId){
        return ResponseEntity.ok(imageService.deleteBoastImage(boastId));
    }
    @DeleteMapping(value = "/delete")
    public void removeS3Image() {
        imageService.removeS3Image();
    }

}
