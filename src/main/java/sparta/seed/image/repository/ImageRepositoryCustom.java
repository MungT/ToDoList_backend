package sparta.seed.image.repository;

import sparta.seed.s3.S3Dto;

import java.util.List;

public interface ImageRepositoryCustom {
    List<String> getBoastImage(Long memberId);
}
