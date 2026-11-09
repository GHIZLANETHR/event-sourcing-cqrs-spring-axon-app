
package org.ghizlane.eventsourcingcqrsspringaxonapp.commands.controllers;

import lombok.RequiredArgsConstructor;

import org.axonframework.commandhandling.gateway.CommandGateway;

import org.ghizlane.eventsourcingcqrsspringaxonapp.commands.commands.*;
import org.ghizlane.eventsourcingcqrsspringaxonapp.commands.dto.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands/accounts")
@RequiredArgsConstructor
public class AccountCommandController {

    private final CommandGateway commandGateway;

    // 1. CREER UN COMPTE
    @PostMapping("/add")
    public CompletableFuture<String> addNewAccount(
            @RequestBody AddAccountRequestDTO request) {

        return commandGateway.send(
                new AddAccountCommand(
                        UUID.randomUUID().toString(),
                        request.initialBalance(),
                        request.currency()
                )
        );
    }

    // 2. ACTIVER UN COMPTE
    @PostMapping("/activate")
    public CompletableFuture<String> activateAccount(
            @RequestBody ActivateAccountRequestDTO request) {

        return commandGateway.send(
                new ActivateAccountCommand(
                        request.accountId()
                )
        );
    }

    // 3. CREDITER UN COMPTE
    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(
            @RequestBody CreditAccountRequestDTO request) {

        return commandGateway.send(
                new CreditAccountCommand(
                        request.accountId(),
                        request.amount(),
                        request.currency()
                )
        );
    }

    // 4. DEBITER UN COMPTE
    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(
            @RequestBody DebitAccountRequestDTO request) {

        return commandGateway.send(
                new DebitAccountCommand(
                        request.accountId(),
                        request.amount(),
                        request.currency()
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(
            Exception exception) {

        return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR
        ).body(exception.getMessage());
    }
}
