package sparta.seed.s3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDto {

    private String uploadImageUrl;
    private String imageName;


    public ImageDto(String uploadImageUrl, String imageName) {
        this.uploadImageUrl = uploadImageUrl;
        this.imageName = imageName;
    }

}