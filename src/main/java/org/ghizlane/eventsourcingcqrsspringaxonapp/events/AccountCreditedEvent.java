
package org.ghizlane.eventsourcingcqrsspringaxonapp.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountCreditedEvent {

    private final String accountId;
    private final double amount;
    private final String currency;
}
