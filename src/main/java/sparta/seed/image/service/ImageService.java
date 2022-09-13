package sparta.seed.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.multipart.MultipartFile;
import sparta.seed.image.domain.DeletedUrlPath;
import sparta.seed.image.repository.DeletedUrlPathRepository;
import sparta.seed.login.domain.Member;
import sparta.seed.login.dto.MemberResponseDto;
import sparta.seed.message.Message;
import sparta.seed.s3.S3Dto;
import sparta.seed.s3.S3Uploader;
import sparta.seed.sercurity.UserDetailsImpl;
import sparta.seed.todo.domain.Rank;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final S3Uploader s3Uploader;
    private final DeletedUrlPathRepository deletedUrlPathRepository;

    @Transactional
    public String saveProfileImage(MultipartFile multipartFile, UserDetailsImpl userDetailsImpl) throws IOException {
        S3Dto s3Dto = s3Uploader.upload(multipartFile);
        Member member = userDetailsImpl.getMember();
        member.setProfileImage(s3Dto.getFileName());
        return s3Dto.getUploadImageUrl();
    }
    public String deleteProfileImage(UserDetailsImpl userDetailsImpl) {
        Member member = userDetailsImpl.getMember();
        member.setProfileImage(null); //기본 이미지 만들어지면 해당 주소 넣기
        DeletedUrlPath deletedUrlPath = new DeletedUrlPath();
        deletedUrlPath.setDeletedUrlPath(member.getProfileImage());
        deletedUrlPathRepository.save(deletedUrlPath);
        return Message.PROFILE_IMAGE_DELETE_SUCCESS.getMessage();
    }
    public void removeS3Image() {
        List<DeletedUrlPath> deletedUrlPathList = deletedUrlPathRepository.findAll();
        for (DeletedUrlPath deletedUrlPath : deletedUrlPathList) {
            s3Uploader.remove(deletedUrlPath.getDeletedUrlPath());
        }
        deletedUrlPathRepository.deleteAll();
    }

}
