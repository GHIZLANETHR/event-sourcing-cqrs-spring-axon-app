
package org.ghizlane.eventsourcingcqrsspringaxonapp.commands.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
public class AddAccountCommand {

    @TargetAggregateIdentifier
    private final String accountId;

    private final double initialBalance;
    private final String currency;
}
