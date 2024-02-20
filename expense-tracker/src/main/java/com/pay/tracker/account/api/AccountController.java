package com.pay.tracker.account.api;

import com.pay.tracker.account.service.AccountService;
import com.pay.tracker.commons.api.AbstractController;
import com.pay.tracker.commons.api.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/account")
@Tag(name = "AccountController", description = "Account Endpoints")
public class AccountController extends AbstractController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Get account's current balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the record", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Invalid token", content = @Content),
            @ApiResponse(responseCode = "406", description = "Business exception happened",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
    })
    @GetMapping(path = "/{id}")
    public ResponseDTO<AccountBalanceDTO> getAccountInfo(@PathVariable Long id) {
        return new ResponseDTO<>(accountService.getAccountBalance(id, getUser()));
    }

    @Operation(summary = "Get all of user accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Invalid token", content = @Content),
    })
    @GetMapping
    public ResponseDTO<List<AccountDTO>> getAccounts() {
        return new ResponseDTO<>(accountService.getUserAccounts(getUser()));
    }

    @Operation(summary = "Save new or update existing account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Invalid token", content = @Content),
            @ApiResponse(responseCode = "406", description = "Business exception happened",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
    })
    @PostMapping
    public ResponseDTO<AccountDTO> saveAccount(@RequestBody @Validated AccountDTO dto) {
        return new ResponseDTO<>(accountService.saveAccount(dto, getUser()));
    }

    @Operation(summary = "Deactivate account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid token", content = @Content),
            @ApiResponse(responseCode = "406", description = "Business exception happened",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDTO.class))}),
    })
    @PutMapping(path = "/{id}")
    public ResponseDTO<Void> deactivateAccount(@PathVariable Long id) {
        accountService.deactivateAccount(id, getUser());
        return new ResponseDTO<>();
    }
}
