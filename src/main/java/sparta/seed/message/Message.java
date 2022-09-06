package sparta.seed.message;

public enum Message {

    //멤버 관련
    AVAILABLE_NICK("사용 가능한 닉네임 입니다."),
    //투두 관련
    TODO_UPDATE_SUCCESS("투두 수정 완료");

    private final String message;

    public String getMessage() {
        return message;
    }

    Message(String message){
        this.message = message;
    }
}
