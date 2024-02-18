package com.pay.tracker.commons.converter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class FormattedToLocalDateTimeConverter extends JsonDeserializer<LocalDateTime> implements Converter<Long, LocalDateTime> {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        try {
            return this.convert(jsonParser.getValueAsLong());
        } catch (Exception e) {
            log.info("converting datetime failed, String: {}", jsonParser.getCurrentValue());
        }
        return null;
    }

    @Override
    public LocalDateTime convert(Long source) {
        return LocalDateTime.parse(source.toString(), formatter);
    }
}
