package sparta.seed.image.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sparta.seed.image.service.ImageService;
import sparta.seed.login.dto.MemberResponseDto;
import sparta.seed.sercurity.UserDetailsImpl;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/api/profile-image")
    public ResponseEntity<String> saveProfileImage(@RequestPart MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws IOException {
        return ResponseEntity.ok(imageService.saveProfileImage(multipartFile, userDetailsImpl));
    }
    @DeleteMapping("/api/profile-image")
    public ResponseEntity<String> deleteProfileImage(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return ResponseEntity.ok(imageService.deleteProfileImage(userDetailsImpl));
    }

    @DeleteMapping(value = "/delete")
    public void removeS3Image() {
        imageService.removeS3Image();
    }

}
