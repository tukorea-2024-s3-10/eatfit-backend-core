package tukorea_2024_s3_10.eat_fit.global.dto;

import lombok.Getter;

@Getter // Jackson 라이브러리에서 객체 -> json 직렬화에 필요
public class ApiResponse<T> {
    private final String status;
    private final String message;
    private final T data;

    private ApiResponse(String status, T data, String message) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("success", data, null);
    }

    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>("fail", null, message);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>("error", null, message);
    }
}
