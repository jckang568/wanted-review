package kr.co.wanted.common;

import lombok.Getter;

@Getter
public enum ErrorType {
    UNKNOWN_ERROR(800, "알 수 없는 에러입니다."),
    REQUEST_ERROR(801, "잘못된 요청입니다."),
    MENU_ERROR(901, "메뉴 처리 에러."),
    CATEGORY_ERROR(902, "카테고리 처리 에러.");

    private final int number;
    private final String message;

    ErrorType(int number, String message) {
        this.number = number;
        this.message = message;
    }

}
