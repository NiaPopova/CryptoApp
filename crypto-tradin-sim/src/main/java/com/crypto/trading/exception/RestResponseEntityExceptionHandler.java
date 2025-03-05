package com.crypto.trading.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UnauthorizedException.class})
    protected ResponseEntity<Object> handleUnauthorizedException(
        RuntimeException ex, WebRequest request) {

        return handleExceptionInternal(ex, buildResponseBody(ex, request, "Unauthorized", HttpStatus.UNAUTHORIZED),
            new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {AssetNotOwnedException.class, UnsupportedOperationException.class})
    protected ResponseEntity<Object> handleBadRequestExceptionsException(
        RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, buildResponseBody(ex, request, "Bad Request", HttpStatus.BAD_REQUEST),
            new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {InsufficientBalanceException.class})
    protected ResponseEntity<Object> handleInsufficientBalanceException(
        RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,
            buildResponseBody(ex, request, "Payment Required", HttpStatus.PAYMENT_REQUIRED),
            new HttpHeaders(), HttpStatus.PAYMENT_REQUIRED, request);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<Object> handleANotFoundException(
        RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, buildResponseBody(ex, request, "Not Found", HttpStatus.NOT_FOUND),
            new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private Map<String, Object> buildResponseBody(RuntimeException ex, WebRequest request, String error,
                                                  HttpStatus status) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("status", status.value());
        responseBody.put("error", error);
        responseBody.put("message", ex.getMessage());
        responseBody.put("path", request.getDescription(false));
        return responseBody;
    }
}
