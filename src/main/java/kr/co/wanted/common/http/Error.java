package kr.co.wanted.common.http;

import lombok.Builder;

public class Error {

    private final Exception e;

    @Builder
    public Error(Exception e) {
        this.e = e;
    }

    public static Error empty() {
        return Error.builder().build();
    }

}

