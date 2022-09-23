package sparta.seed.image.repository;

import sparta.seed.image.dto.ImageResponseDto;
import sparta.seed.s3.S3Dto;

import java.util.List;

public interface ImageRepositoryCustom {
    List<ImageResponseDto> getBoastImage(Long memberId);
}
