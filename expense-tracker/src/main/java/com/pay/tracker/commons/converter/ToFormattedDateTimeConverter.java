package com.pay.tracker.commons.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class ToFormattedDateTimeConverter extends JsonSerializer<LocalDateTime> implements Converter<LocalDateTime, Long> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        try {
            jsonGenerator.writeNumber(convert(dateTime));
        }
        catch (Exception e){
            log.error("error when formatting localDateTime");
            jsonGenerator.writeNull();
        }
    }

    @Override
    public Long convert(LocalDateTime source) {
        return Long.valueOf(source.format(formatter));
    }
}
