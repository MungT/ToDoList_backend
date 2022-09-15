package sparta.seed.image.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public ResponseEntity<String> saveProfileImage(@RequestPart MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws IOException {
        return ResponseEntity.ok(imageService.saveProfileImage(multipartFile, userDetailsImpl));
    }
    //프로필 이미지 삭제
    @DeleteMapping("/profile")
    public ResponseEntity<String> deleteProfileImage(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return ResponseEntity.ok(imageService.deleteProfileImage(userDetailsImpl));
    }
    //자랑 이미지
    @GetMapping("/boast")
    public ResponseEntity<List<String>> getBoastImage(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return ResponseEntity.ok(imageService.getBoastImage(userDetailsImpl));
    }
    @PostMapping("/boast")
    public ResponseEntity<String> saveBoastImage(@RequestPart MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws IOException {

        return ResponseEntity.ok(imageService.saveBoastImage(multipartFile, userDetailsImpl));
    }
    @DeleteMapping("/boast/{boastId}")
    public ResponseEntity<String> deleteBoastImage(@PathVariable Long boastId){
        return ResponseEntity.ok(imageService.deleteBoastImage(boastId));
    }
    @DeleteMapping(value = "/delete")
    public void removeS3Image() {
        imageService.removeS3Image();
    }

}
