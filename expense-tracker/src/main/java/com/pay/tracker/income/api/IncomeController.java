package com.pay.tracker.income.api;

import com.pay.tracker.commons.api.AbstractController;
import com.pay.tracker.commons.api.ResponseDTO;
import com.pay.tracker.income.service.IncomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/income")
@Tag(name = "IncomeController", description = "Income Endpoints")
public class IncomeController extends AbstractController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @Operation(summary = "Get income by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the record", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Invalid token", content = @Content),
            @ApiResponse(responseCode = "406", description = "Business exception happened",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
    })
    @GetMapping(path = "/{id}")
    public ResponseDTO<IncomeResponseDTO> getIncome(@PathVariable Long id) {
        return new ResponseDTO<>(incomeService.getIncome(id, getUser()));
    }

    @Operation(summary = "Get user incomes by category and date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Invalid token", content = @Content),
    })
    @GetMapping
    public ResponseDTO<List<IncomeResponseDTO>> getIncomes(@RequestParam(required = false) Long categoryId,
                                                           @RequestParam LocalDateTime dateFrom,
                                                           @RequestParam LocalDateTime dateTo) {
        return new ResponseDTO<>(incomeService.getUserIncomes(categoryId, dateFrom, dateTo, getUser()));
    }

    @Operation(summary = "Save new or update existing income")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Invalid token", content = @Content),
            @ApiResponse(responseCode = "406", description = "Business exception happened",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
    })
    @PostMapping
    public ResponseDTO<IncomeResponseDTO> saveIncome(@RequestBody @Validated IncomeDTO dto) {
        return new ResponseDTO<>(incomeService.saveIncome(dto, getUser()));
    }
}
