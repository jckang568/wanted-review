package kr.co.wanted.common.http;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;


@Data
public class ApiResult<T> {

    private boolean result;
    private final T data;
    private Error error;

    @Builder(access = AccessLevel.PRIVATE)
    public ApiResult(boolean result, T data, Error error) {
        this.result = result;
        this.data = data;
        this.error = error;
    }

    public static <T> ApiResult<T> ok(T data) {
        return ApiResult.<T>builder()
                .result(true)
                .data(data)
                .build();
    }

    public static <T> ApiResult<T> error(Exception e) {
        return ApiResult.<T>builder()
                .result(false)
                .error(new Error(e)) // Error.create(e) 대신 new Error(e) 사용
                .build();
    }
}
