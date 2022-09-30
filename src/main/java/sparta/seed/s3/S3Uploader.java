package sparta.seed.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Uploader {


    private final AmazonS3Client amazonS3Client;
    private final AmazonS3 amazonS3;


    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    public S3Dto upload(MultipartFile multipartFile) throws IOException {
        String fileName = UUID.randomUUID() + multipartFile.getOriginalFilename();
        String fileFormatName = Objects.requireNonNull(multipartFile.getContentType()).substring(multipartFile.getContentType().lastIndexOf("/") + 1);
        String uploadImageUrl = amazonS3Client.getUrl(bucket, fileName).toString();

        MultipartFile newFile = resizeMainImage(multipartFile, fileName, fileFormatName);

        ObjectMetadata objectMetadata = new ObjectMetadata();

        objectMetadata.setContentLength(newFile.getSize());
        objectMetadata.setContentType(newFile.getContentType());
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, newFile.getInputStream(), objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));

        removeNewFile(new File(Objects.requireNonNull(newFile.getOriginalFilename())));
        return new S3Dto(fileName, uploadImageUrl);
    }

    //프로필 이미지 업로드
//    public String upload1(MultipartFile multipartFile) throws IOException {
//        String fileName = UUID.randomUUID() + multipartFile.getOriginalFilename();
//        String fileFormatName = Objects.requireNonNull(multipartFile.getContentType()).substring(multipartFile.getContentType().lastIndexOf("/") + 1);
//        String uploadImageUrl = amazonS3Client.getUrl(bucket, fileName).toString();
//
//        File resizingFile = resizeMainImage(multipartFile,fileName,fileFormatName,2).orElseThrow(() -> new io.jsonwebtoken.io.IOException("변환실패"));
//
//        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, resizingFile));
////         removeNewFile(resizingFile);
//        return  uploadImageUrl;
//    }

    //게시글/프로필 이미지 리사이즈
    private MultipartFile resizeMainImage(MultipartFile originalImage, String fileName, String fileFormatName) throws IOException {

        //요청 파일로 BufferedImage 객체를 생성 => MultipartFile에서 Buffered로 바꾸어 가공가능하도록
        BufferedImage srcImg = ImageIO.read(originalImage.getInputStream());

        int demandWidth = 330;
        int demandHeight = 250;

        int originWidth = srcImg.getWidth();
        int originHeight = srcImg.getHeight();

        // 원본 너비를 기준으로 하여 이미지의 비율로 높이를 계산
        int newWidth;
        int newHeight;

        // 원본 넓이가 더 작을경우 리사이징 안함.
        if (demandWidth > originWidth && demandHeight > originHeight) {
            newWidth = originWidth;
            newHeight = originHeight;
        } else {
            newWidth = demandWidth;
            newHeight = (demandWidth * originHeight) / originWidth;
            // newHeigh = demandHeight; 차후 속도체크후 결정
        }

        // 이미지 기본 너비 높이 설정값으로 변경
        BufferedImage destImg = Scalr.resize(srcImg, newWidth, newHeight);

        // 이미지 저장
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(destImg, fileFormatName.toLowerCase(), baos);
        baos.flush();
        destImg.flush();
        return new MockMultipartFile(fileName, baos.toByteArray());
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    public void remove(String filename) {
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, filename);
        amazonS3.deleteObject(request);
    }

}
