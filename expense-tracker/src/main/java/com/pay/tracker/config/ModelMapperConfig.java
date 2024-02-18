package com.pay.tracker.config;

import com.pay.tracker.category.api.CategoryDTO;
import com.pay.tracker.category.persistance.Category;
import com.pay.tracker.category.persistance.TransactionTypeEnum;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        Converter<Byte, TransactionTypeEnum> transactionTypeConverter = c -> TransactionTypeEnum.getByCode(c.getSource());
        modelMapper.createTypeMap(CategoryDTO.class, Category.class).addMappings(mapper ->
                mapper.using(transactionTypeConverter).map(CategoryDTO::getTransactionTypeCode, Category::setType));
        return modelMapper;
    }
}
