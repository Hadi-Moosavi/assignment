package com.pay.tracker.commons.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class ResponseDTO<R> implements Serializable {
    private R response;
    @Schema(description = "Info, warning or error text")
    private String text;
    @Schema(description = "Ui reaction code, Info: 1, Warning: 2, Error: 3")
    private Byte reactionCode;

    public ResponseDTO(R response) {
        this.response = response;
    }

    public ResponseDTO(String text, Byte reactionCode) {
        this.text = text;
        this.reactionCode = reactionCode;
    }
}
