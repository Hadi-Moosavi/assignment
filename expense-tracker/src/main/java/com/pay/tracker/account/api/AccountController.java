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

    @Operation(summary = "Get account by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the record",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Invalid token"),
            @ApiResponse(responseCode = "406", description = "Business exception happened"),

    })
    @GetMapping(path = "/{id}")
    public ResponseDTO<AccountDTO> getAccount(@PathVariable Long id) {
        return new ResponseDTO<>(accountService.getAccount(id, getUser()));
    }

    @Operation(summary = "Get all of user accounts")
    @GetMapping
    public ResponseDTO<List<AccountDTO>> getAccounts() {
        return new ResponseDTO<>(accountService.getUserAccounts(getUser()));
    }

    @Operation(summary = "Save new or update existing account")
    @PostMapping
    public ResponseDTO<AccountDTO> saveAccount(@RequestBody @Validated AccountDTO dto) {
        return new ResponseDTO<>(accountService.saveAccount(dto, getUser()));
    }

    @Operation(summary = "Deactivate account")
    @PutMapping(path = "/{id}")
    public ResponseDTO<Void> deactivateAccount(@PathVariable Long id) {
        accountService.deactivateAccount(id, getUser());
        return new ResponseDTO<>();
    }
}
