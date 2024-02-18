package com.pay.tracker.config;

import com.pay.tracker.category.api.CategoryDTO;
import com.pay.tracker.category.persistance.Category;
import com.pay.tracker.category.persistance.TransactionTypeEnum;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.createTypeMap(CategoryDTO.class, Category.class).addMappings(mapper -> mapper.map(c -> TransactionTypeEnum.getByCode(c.getTransactionTypeCode()), Category::setType));
        return modelMapper;
    }
}
