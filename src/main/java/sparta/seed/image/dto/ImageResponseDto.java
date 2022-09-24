package sparta.seed.image.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.List;

@Getter
public class ImageResponseDto {
    private final Long id;
    private final String ImageUrl;


    @QueryProjection
    public ImageResponseDto(Long id, String imageUrl) {
        this.id = id;
        ImageUrl = imageUrl;
    }
}
