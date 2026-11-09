package org.ghizlane.eventsourcingcqrsspringaxonapp.commands.controllers;

import org.ghizlane.commands.commands.AddAccountCommand;
import org.ghizlane.commands.dto.AddAccountRequestDTO;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands")
public class AccountCommandController {

    private final CommandGateway commandGateway;

    public AccountCommandController(
            CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/accounts/add")
    public CompletableFuture<String> addNewAccount(
            @RequestBody AddAccountRequestDTO request) {

        String accountId = UUID.randomUUID().toString();

        AddAccountCommand command = new AddAccountCommand(
                accountId,
                request.initialBalance(),
                request.currency()
        );

        return commandGateway.send(command);
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception exception) {
        return exception.getMessage();
    }
}
