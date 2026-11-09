
package org.ghizlane.eventsourcingcqrsspringaxonapp.commands.dto;

public record CreditAccountRequestDTO(
        String accountId,
        double amount,
        String currency
) {
}
