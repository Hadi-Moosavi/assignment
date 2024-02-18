package com.pay.tracker.category.api;

import com.pay.tracker.category.service.CategoryService;
import com.pay.tracker.commons.api.AbstractController;
import com.pay.tracker.commons.api.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/category")
@Tag(name = "CategoryController", description = "Category Endpoints")
public class CategoryController extends AbstractController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the record", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Invalid token", content = @Content),
            @ApiResponse(responseCode = "406", description = "Business exception happened",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
    })
    @GetMapping(path = "/{id}")
    public ResponseDTO<CategoryResponseDTO> getCategory(@PathVariable Long id) {
        return new ResponseDTO<>(categoryService.getCategory(id, getUser()));
    }

    @Operation(summary = "Get all of user's categories by transaction type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Invalid token", content = @Content),
    })
    @GetMapping
    public ResponseDTO<List<CategoryResponseDTO>> getCategories(@RequestParam byte transactionType) {
        return new ResponseDTO<>(categoryService.getUserCategories(transactionType, getUser()));
    }

    @Operation(summary = "Save new or update existing category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Invalid token", content = @Content),
            @ApiResponse(responseCode = "406", description = "Business exception happened",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
    })
    @PostMapping
    public ResponseDTO<CategoryResponseDTO> saveCategory(@RequestBody @Validated CategoryDTO dto) {
        return new ResponseDTO<>(categoryService.saveCategory(dto, getUser()));
    }
}
