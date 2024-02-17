package com.pay.tracker.commons.model;

public enum ReactionTypeEnum {
    INFO((byte) 1),
    WARNING((byte) 2),
    ERROR((byte) 3);
    private final byte value;

    ReactionTypeEnum(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
