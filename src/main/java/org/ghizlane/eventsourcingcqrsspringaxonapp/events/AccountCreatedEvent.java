package org.ghizlane.eventsourcingcqrsspringaxonapp.events;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ghizlane.eventsourcingcqrsspringaxonapp.enums.AccountStatus;

@Getter
@AllArgsConstructor
public class AccountCreatedEvent {

    private final String accountId;
    private final double initialBalance;
    private final AccountStatus status;
    private final String currency;
}
