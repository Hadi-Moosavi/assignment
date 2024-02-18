package com.pay.tracker.category.persistance;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Getter
public enum TransactionTypeEnum {
    INCOME((byte) 1),
    EXPENSE((byte) 2);

    private final byte code;

    private static final Map<Byte, TransactionTypeEnum> _MAP;

    static {
        _MAP = Arrays.stream(values()).collect(toMap(t -> t.code, Function.identity()));
    }

    TransactionTypeEnum(byte code) {
        this.code = code;
    }

    public static TransactionTypeEnum getByCode(Byte code) {
        if (code == null)
            return null;
        return _MAP.get(code);
    }
}
