package com.pay.tracker.transaction.api;

import com.pay.tracker.commons.api.AbstractController;
import com.pay.tracker.commons.api.ResponseDTO;
import com.pay.tracker.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping(path = "/api/v1/transaction")
@Tag(name = "TransactionController", description = "Transaction Endpoints")
public class TransactionController extends AbstractController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Get transaction by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the record", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Invalid token", content = @Content),
            @ApiResponse(responseCode = "406", description = "Business exception happened",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
    })
    @GetMapping(path = "/{id}")
    public ResponseDTO<TransactionResponseDTO> getTransaction(@PathVariable Long id) {
        return new ResponseDTO<>(transactionService.getTransaction(id, getUser()));
    }

    @Operation(summary = "Get user transactions by category and date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Invalid token", content = @Content),
    })
    @GetMapping
    public ResponseDTO<List<TransactionResponseDTO>> filter(
            @Parameter(description = "date from (yyyy-MM-dd HH:mm:SS)") @RequestParam LocalDateTime from,
            @Parameter(description = "date to (yyyy-MM-dd HH:mm:SS)") @RequestParam LocalDateTime to,
            @Parameter(description = "category id") @RequestParam(required = false) Long categoryId,
            @Parameter(description = "transaction type code") @RequestParam(required = false) Byte type) {
        return new ResponseDTO<>(transactionService.filter(from, to, type, categoryId, getUser()));
    }

    @Operation(summary = "Save new or update existing transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Invalid token", content = @Content),
            @ApiResponse(responseCode = "406", description = "Business exception happened",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
    })
    @PostMapping
    public ResponseDTO<TransactionResponseDTO> saveTransaction(@RequestBody @Validated TransactionDTO dto) {
        return new ResponseDTO<>(transactionService.saveTransaction(dto, getUser()));
    }
}
