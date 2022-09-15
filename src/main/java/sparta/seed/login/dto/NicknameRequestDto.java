package sparta.seed.login.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class NicknameRequestDto {

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z-0-9]{2,12}$", message = "닉네임은 특수문자를 제외한 2~12자리여야 합니다.")
    private String nickname;
}
