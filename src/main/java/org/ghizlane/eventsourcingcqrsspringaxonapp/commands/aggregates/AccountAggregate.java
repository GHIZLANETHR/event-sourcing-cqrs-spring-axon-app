
package org.ghizlane.eventsourcingcqrsspringaxonapp.commands.aggregates;

import lombok.extern.slf4j.Slf4j;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import org.ghizlane.eventsourcingcqrsspringaxonapp.commands.commands.*;
import org.ghizlane.eventsourcingcqrsspringaxonapp.events.*;
import org.ghizlane.eventsourcingcqrsspringaxonapp.enums.AccountStatus;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Slf4j
public class AccountAggregate {

    @AggregateIdentifier
    private String accountId;

    private double balance;
    private String currency;
    private AccountStatus status;

    // Obligatoire pour Axon
    public AccountAggregate() {
    }

    // 1. CREATION DU COMPTE

    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {

        log.info("AddAccountCommand received: {}",
                command.getAccountId());

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

        log.info("Account created: {}", accountId);
    }

    // 2. ACTIVATION DU COMPTE

    @CommandHandler
    public void handle(ActivateAccountCommand command) {

        log.info("ActivateAccountCommand received: {}",
                command.getAccountId());

        if (this.status != AccountStatus.CREATED) {
            throw new IllegalStateException(
                    "Only CREATED accounts can be activated"
            );
        }

        apply(new AccountActivatedEvent(
                command.getAccountId(),
                AccountStatus.ACTIVATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event) {

        this.status = event.getStatus();

        log.info("Account activated: {}", accountId);
    }

    // 3. CREDIT DU COMPTE

    @CommandHandler
    public void handle(CreditAccountCommand command) {

        log.info("CreditAccountCommand received: {}",
                command.getAccountId());

        if (this.status != AccountStatus.ACTIVATED) {
            throw new IllegalStateException(
                    "Account must be activated"
            );
        }

        if (command.getAmount() <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be positive"
            );
        }

        if (!this.currency.equals(command.getCurrency())) {
            throw new IllegalArgumentException(
                    "Invalid account currency"
            );
        }

        apply(new AccountCreditedEvent(
                command.getAccountId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {

        this.balance += event.getAmount();

        log.info("Account credited: {}, new balance: {}",
                accountId, balance);
    }

    // 4. DEBIT DU COMPTE

    @CommandHandler
    public void handle(DebitAccountCommand command) {

        log.info("DebitAccountCommand received: {}",
                command.getAccountId());

        if (this.status != AccountStatus.ACTIVATED) {
            throw new IllegalStateException(
                    "Account must be activated"
            );
        }

        if (command.getAmount() <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be positive"
            );
        }

        if (!this.currency.equals(command.getCurrency())) {
            throw new IllegalArgumentException(
                    "Invalid account currency"
            );
        }

        if (this.balance < command.getAmount()) {
            throw new IllegalStateException(
                    "Insufficient balance"
            );
        }

        apply(new AccountDebitedEvent(
                command.getAccountId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {

        this.balance -= event.getAmount();

        log.info("Account debited: {}, new balance: {}",
                accountId, balance);
    }
}
