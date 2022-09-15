package sparta.seed.s3;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class S3Dto {
    String fileName;
    String uploadImageUrl;

    @QueryProjection
    public S3Dto(String uploadImageUrl) {
        this.uploadImageUrl = uploadImageUrl;
    }
}