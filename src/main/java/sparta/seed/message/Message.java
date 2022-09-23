package sparta.seed.message;

public enum Message {

    //멤버 관련
    AVAILABLE_NICK("사용 가능한 닉네임 입니다."),
    //투두 관련
    TODO_UPLOAD_SUCCESS("투두 추가 완료"),
    TODO_UPDATE_SUCCESS("투두 수정 완료"),
    TODO_DELETE_SUCCESS("투두 삭제 완료"),
    //이미지 관련
    IMAGE_DELETE_SUCCESS("이미지 삭제 완료"),
    IMAGE_UPLOAD_SUCCESS("이미지 업로드 완료"),

    //좌우명 관련
    MYMOTTO_UPDATE_SUCCESS("좌우명 등록 완료"),
    //D-DAY 관련
    GOAL_UPDATE_SUCCESS("목표 수정 완료"),
    //카테고리 관련
    CATEGORY_UPLOAD_SUCCESS("카테고리 추가 완료"),
    CATEGORY_UPDATE_SUCCESS("카테고리 수정 완료"),
    CATEGORY_DELETE_SUCCESS("카테고리 삭제 완료");
    
    // 팔로우 관련
    MYFOLLOW_UPDATE_SUCCESS("팔로우 완료"),
    MYFOLLOW_UPDATE_CANCEL("팔로우 취소 완료");
    
    private final String message;

    public String getMessage() {
        return message;
    }

    Message(String message){
        this.message = message;
    }

}
