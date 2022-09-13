package sparta.seed.image.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sparta.seed.image.service.ImageService;
import sparta.seed.login.dto.MemberResponseDto;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class ImageController {

    private final ImageService imageService;

//    @PostMapping("/api/profile-image")
//    public ResponseEntity<MemberResponseDto> saveProfileImage(@RequestPart MultipartFile multipartFile){
//        return imageService.saveProfileImage(multipartFile);
//    }

    @DeleteMapping(value = "/delete")
    public void removeS3Image() {
        imageService.removeS3Image();
    }

}
