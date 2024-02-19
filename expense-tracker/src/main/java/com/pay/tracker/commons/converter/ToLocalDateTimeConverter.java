package com.pay.tracker.commons.converter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class ToLocalDateTimeConverter extends JsonDeserializer<LocalDateTime> implements Converter<String, LocalDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            return this.convert(jsonParser.getValueAsString());
        } catch (Exception e) {
            log.info("converting datetime failed, String: {}", jsonParser.getCurrentValue());
        }
        return null;
    }

    @Override
    public LocalDateTime convert(String source) {
        if(StringUtils.isEmpty(source))
            return null;
        return LocalDateTime.parse(source, formatter);
    }
}
