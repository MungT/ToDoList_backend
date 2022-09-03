package sparta.seed.message;

public enum Message {

    AVAILABLE_NICK("사용 가능한 닉네임 입니다.");

    private final String message;

    public String getMessage() {
        return message;
    }

    Message(String message){
        this.message = message;
    }
}
