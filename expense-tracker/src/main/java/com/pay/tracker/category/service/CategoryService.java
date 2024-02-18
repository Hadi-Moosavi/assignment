package com.pay.tracker.category.service;

import com.pay.tracker.category.api.CategoryDTO;
import com.pay.tracker.category.api.CategoryResponseDTO;
import com.pay.tracker.commons.model.User;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO saveCategory(CategoryDTO dto, User user);

    CategoryResponseDTO getCategory(Long id, User user);

    List<CategoryResponseDTO> getUserCategories(byte transactionTypeCode, User user);
}
