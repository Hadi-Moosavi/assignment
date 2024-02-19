package com.pay.tracker.commons.api;


import com.pay.tracker.commons.model.BusinessException;
import com.pay.tracker.commons.model.ReactionTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.pay.tracker.commons.model.ReactionTypeEnum.ERROR;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    private final MessageSource messageSource;

    public ExceptionControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseDTO<?>> handle(BusinessException ex) {
        var message = messageSource.getMessage(ex.getMessage(), ex.getArgs(), LocaleContextHolder.getLocale());
        log.info("Error exception happened: {}", message);
        return new ResponseEntity<>(new ResponseDTO<>(message, ERROR.getValue()), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handle(NullPointerException ex) {
        log.error(ex.getMessage(), ex);
        return unhandledException(ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception ex) {
        return unhandledException(ex);
    }

    private static ResponseEntity<ResponseDTO<Object>> unhandledException(Exception ex) {
        var trackingNo = RandomUtils.nextInt(1000, 9999);
        log.error("trackingNo:{}, {} exception occur.", trackingNo, ex.getClass().getSimpleName(), ex);
        return new ResponseEntity<>(new ResponseDTO<>("Unhandled exception. TrackingNo: " + trackingNo, ERROR.getValue()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
