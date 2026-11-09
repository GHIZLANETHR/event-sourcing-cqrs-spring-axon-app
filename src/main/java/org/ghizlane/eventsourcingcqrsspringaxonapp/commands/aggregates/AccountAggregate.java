package org.ghizlane.eventsourcingcqrsspringaxonapp.commands.aggregates;


import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import org.ghizlane.eventsourcingcqrsspringaxonapp.commands.commands.AddAccountCommand;
import org.ghizlane.eventsourcingcqrsspringaxonapp.enums.AccountStatus;
import org.ghizlane.eventsourcingcqrsspringaxonapp.events.AccountCreatedEvent;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Slf4j
public class AccountAggregate {

    @AggregateIdentifier
    private String accountId;

    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {

        log.info("AddAccountCommand received: {}", command.getAccountId());

        if (command.getInitialBalance() <= 0) {
            throw new IllegalArgumentException(
                    "Initial balance must be positive"
            );
        }

        apply(new AccountCreatedEvent(
                command.getAccountId(),
                command.getInitialBalance(),
                AccountStatus.CREATED,
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {

        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.currency = event.getCurrency();
        this.status = event.getStatus();

        log.info("AccountCreatedEvent handled: {}", event.getAccountId());
    }
}
