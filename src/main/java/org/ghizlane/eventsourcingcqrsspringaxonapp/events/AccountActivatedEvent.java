
package org.ghizlane.eventsourcingcqrsspringaxonapp.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ghizlane.eventsourcingcqrsspringaxonapp.enums.AccountStatus;

@Getter
@AllArgsConstructor
public class AccountActivatedEvent {

    private final String accountId;
    private final AccountStatus status;
}
