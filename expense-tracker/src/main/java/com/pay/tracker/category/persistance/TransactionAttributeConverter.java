package com.pay.tracker.category.persistance;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TransactionAttributeConverter implements AttributeConverter<TransactionTypeEnum, Byte> {
    @Override
    public Byte convertToDatabaseColumn(TransactionTypeEnum transactionTypeEnum) {
        if (transactionTypeEnum == null)
            return null;
        return transactionTypeEnum.getCode();
    }

    @Override
    public TransactionTypeEnum convertToEntityAttribute(Byte code) {
        if (code == null)
            return null;
        return TransactionTypeEnum.getByCode(code);
    }
}
