package com.pay.tracker.category.service;

import com.pay.tracker.category.api.CategoryDTO;
import com.pay.tracker.category.api.CategoryResponseDTO;
import com.pay.tracker.category.persistance.Category;
import com.pay.tracker.category.persistance.CategoryRepository;
import com.pay.tracker.category.persistance.TransactionTypeEnum;
import com.pay.tracker.commons.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        Category entity = new Category();
        entity.setId(1L);
        entity.setName("1L");
        entity.setUserId(2L);
        entity.setType(TransactionTypeEnum.INCOME);
        categoryRepository.save(entity);
    }

    @Test
    void updateCategory() {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(1L);
        dto.setName("2L");
        var h = categoryService.saveCategory(dto, new User(2L, "H"));
        assertEquals("2L", h.getName());
    }
    @Test
    void saveCategory() {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(null);
        dto.setName("3L");
        dto.setTransactionTypeCode(TransactionTypeEnum.EXPENSE.getCode());
        var h = categoryService.saveCategory(dto, new User(2L, "H"));
        assertNotNull(h.getType());
        assertEquals(TransactionTypeEnum.EXPENSE, h.getType());
        assertEquals("3L", h.getName());
    }

    @Test
    void getCategory() {
        CategoryResponseDTO category = categoryService.getCategory(1L, new User(2L, "H"));
        assertEquals(TransactionTypeEnum.INCOME, category.getType());
    }

    @Test
    void getUserCategories() {
        List<CategoryResponseDTO> h = categoryService.getUserCategories(TransactionTypeEnum.INCOME.getCode(), new User(2L, "H"));
        assertEquals(1, h.size());
    }
}