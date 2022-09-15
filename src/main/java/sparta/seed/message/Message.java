package sparta.seed.message;

public enum Message {

    //멤버 관련
    AVAILABLE_NICK("사용 가능한 닉네임 입니다."),
    //투두 관련
    TODO_UPDATE_SUCCESS("투두 수정 완료"),
    TODO_DELETE_SUCCESS("투두 삭제 완료"),
    //이미지 관련
    IMAGE_DELETE_SUCCESS("이미지 삭제 완료"),
    IMAGE_UPLOAD_SUCCESS("이미지 업로드 완료"),
    MYMOTTO_UPDATE_SUCCESS("좌우명 등록 완료");
    private final String message;

    public String getMessage() {
        return message;
    }

    Message(String message){
        this.message = message;
    }
}
