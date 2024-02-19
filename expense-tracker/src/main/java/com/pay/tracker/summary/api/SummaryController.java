package com.pay.tracker.summary.api;

import com.pay.tracker.account.api.AccountDTO;
import com.pay.tracker.category.api.CategoryResponseDTO;
import com.pay.tracker.category.persistance.Category;
import com.pay.tracker.commons.api.AbstractController;
import com.pay.tracker.commons.api.ResponseDTO;
import com.pay.tracker.summary.service.SummaryService;
import com.pay.tracker.transaction.api.TransactionResponseDTO;
import com.pay.tracker.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/summary")
@Tag(name = "SummaryController", description = "Summary Endpoints")
public class SummaryController extends AbstractController {

    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @Operation(summary = "Get user transaction's summary by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Invalid token", content = @Content),
            @ApiResponse(responseCode = "406", description = "Business exception happened",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
    })
    @GetMapping(path = "/by-category")
    public ResponseDTO<SummaryDTO<CategoryResponseDTO>> summaryByCategory(
            @Parameter(description = "date from (yyyy-MM-dd HH:mm:SS)") @RequestParam LocalDateTime from,
            @Parameter(description = "date to (yyyy-MM-dd HH:mm:SS)") @RequestParam LocalDateTime to,
            @Parameter(description = "transaction type code") @RequestParam(required = false) Byte type,
            @Parameter(description = "account id") @RequestParam(required = false) Long accountId) {
        return new ResponseDTO<>(summaryService.getSummaryByCategory(from, to, type, accountId, getUser()));
    }

    @Operation(summary = "Get user transaction's summary by account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Invalid token", content = @Content),
            @ApiResponse(responseCode = "406", description = "Business exception happened",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
    })
    @GetMapping(path = "/by-account")
    public ResponseDTO<SummaryDTO<AccountDTO>> summaryByAccount(
            @Parameter(description = "date from (yyyy-MM-dd HH:mm:SS)") @RequestParam LocalDateTime from,
            @Parameter(description = "date to (yyyy-MM-dd HH:mm:SS)") @RequestParam LocalDateTime to,
            @Parameter(description = "transaction type code") @RequestParam(required = false) Byte type,
            @Parameter(description = "category id") @RequestParam(required = false) Long categoryId) {
        return new ResponseDTO<>(summaryService.getSummaryByAccount(from, to, type, categoryId, getUser()));
    }

    @Operation(summary = "Get user transaction's summary by transaction type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Invalid token", content = @Content),
            @ApiResponse(responseCode = "406", description = "Business exception happened",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
    })
    @GetMapping(path = "/by-type")
    public ResponseDTO<SummaryDTO<String>> summaryByType(
            @Parameter(description = "date from (yyyy-MM-dd HH:mm:SS)") @RequestParam LocalDateTime from,
            @Parameter(description = "date to (yyyy-MM-dd HH:mm:SS)") @RequestParam LocalDateTime to,
            @Parameter(description = "category id") @RequestParam(required = false) Long categoryId,
            @Parameter(description = "account id") @RequestParam(required = false) Long accountId) {
        return new ResponseDTO<>(summaryService.getSummaryByType(from, to, categoryId, accountId, getUser()));
    }
}
