
package org.ghizlane.eventsourcingcqrsspringaxonapp.commands.dto;

public record DebitAccountRequestDTO(
        String accountId,
        double amount,
        String currency
) {
}
