package com.pay.tracker.commons.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class ResponseDTO<R> implements Serializable {
    private R response;
    private String text;
    private Byte reactionCode;

    public ResponseDTO(R response) {
        this.response = response;
    }

    public ResponseDTO(String text, Byte reactionCode) {
        this.text = text;
        this.reactionCode = reactionCode;
    }
}
