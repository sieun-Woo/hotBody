package com.sparta.hotbody.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomException extends RuntimeException {

    private final ExceptionStatus exceptionStatus;

}
