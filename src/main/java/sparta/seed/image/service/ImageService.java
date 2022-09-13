package sparta.seed.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.multipart.MultipartFile;
import sparta.seed.image.domain.DeletedUrlPath;
import sparta.seed.image.repository.DeletedUrlPathRepository;
import sparta.seed.login.dto.MemberResponseDto;
import sparta.seed.s3.S3Uploader;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final S3Uploader s3Uploader;
    private final DeletedUrlPathRepository deletedUrlPathRepository;

//    public ResponseEntity<MemberResponseDto> saveProfileImage(MultipartFile multipartFile) {
//    }
    public void removeS3Image() {
        List<DeletedUrlPath> deletedUrlPathList = deletedUrlPathRepository.findAll();
        for (DeletedUrlPath deletedUrlPath : deletedUrlPathList) {
            s3Uploader.remove(deletedUrlPath.getDeletedUrlPath());
        }
        deletedUrlPathRepository.deleteAll();
    }

}
