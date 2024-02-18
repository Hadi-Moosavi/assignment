package com.pay.tracker.category.service;

import com.pay.tracker.category.api.CategoryDTO;
import com.pay.tracker.category.api.CategoryResponseDTO;
import com.pay.tracker.category.persistance.Category;
import com.pay.tracker.category.persistance.CategoryRepository;
import com.pay.tracker.category.persistance.TransactionTypeEnum;
import com.pay.tracker.commons.model.BusinessException;
import com.pay.tracker.commons.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    private Category findAndUpdateCategory(CategoryDTO dto, User user) {
        Category category;
        category = getAndCheckCategory(dto.getId(), user);
        category.setName(dto.getName());
        return category;
    }

    @Override
    public Category getAndCheckCategory(Long id, User user) {
        Category category;
        category = categoryRepository.findById(id).orElseThrow(() -> new BusinessException("Entity not found."));
        if (!user.getId().equals(category.getUserId())) {
            throw new BusinessException("Category not belong to user");
        }
        return category;
    }

    @Transactional
    @Override
    public CategoryResponseDTO saveCategory(CategoryDTO dto, User user) {
        Category category;
        if (dto.getId() != null) {
            category = findAndUpdateCategory(dto, user);
        } else {
            if (categoryRepository.existsByUserIdAndName(user.getId(), dto.getName())) {
                throw new BusinessException("Duplicate category name");
            }
            category = modelMapper.map(dto, Category.class);
            category.setUserId(user.getId());
        }
        Category savedModel = categoryRepository.save(category);
        return modelMapper.map(savedModel, CategoryResponseDTO.class);
    }

    @Override
    public CategoryResponseDTO getCategory(Long id, User user) {
        return modelMapper.map(getAndCheckCategory(id, user), CategoryResponseDTO.class);
    }

    @Override
    public List<CategoryResponseDTO> getUserCategories(byte transactionTypeCode, User user) {
        return categoryRepository.getByUserIdAndTypeOrderByName(user.getId(),
                        TransactionTypeEnum.getByCode(transactionTypeCode))
                .stream()
                .map(a -> modelMapper.map(a, CategoryResponseDTO.class))
                .toList();
    }
}
