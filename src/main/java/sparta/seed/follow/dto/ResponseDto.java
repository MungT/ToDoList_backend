//package sparta.seed.follow.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//public class ResponseDto<T> {
//
//    private boolean success;
//    private T data;
//    private Status status;
//
//    public static <T> ResponseDto<T> success(T data) {
//        return new ResponseDto<>(true, data, new Status("200,","정상적으로 처리 되었습니다."));
//
//    }
//
//    public static <T> ResponseDto<T> fail(String code, String message) {
//        return new ResponseDto<>(false, null, new Status(code, message));
//    }
//
//    @Getter
//    @AllArgsConstructor
//    static class Status {
//
//        private String code;
//        private String message;
//
//    }
//
//
//
//}
