package com.pay.tracker.commons.api;


import com.pay.tracker.commons.model.ErrorException;
import com.pay.tracker.commons.model.ReactionTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<ResponseDTO<?>> handle(ErrorException ex) {
        log.info("ErrorException exception happened: {}", ex.getMessage());
        var dto = new ResponseDTO<>(ex.getMessage(), ReactionTypeEnum.ERROR.getValue());
        return new ResponseEntity<>(dto, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handle(NullPointerException ex) {
        ex.printStackTrace();
        return unhandledException(ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception ex) {
        return unhandledException(ex);
    }

    private static ResponseEntity<ResponseDTO<Object>> unhandledException(Exception ex) {
        var trackingNo = RandomUtils.nextInt(1000, 9999);
        log.error("trackingNo:{}, {} exception occur.", trackingNo, ex.getClass().getSimpleName(), ex);
        return new ResponseEntity<>(new ResponseDTO<>("Unhandled exception. TrackingNo: " + trackingNo, ReactionTypeEnum.ERROR.getValue()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
