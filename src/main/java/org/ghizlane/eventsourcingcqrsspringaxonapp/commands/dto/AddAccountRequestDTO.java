
package org.ghizlane.eventsourcingcqrsspringaxonapp.commands.dto;

public record AddAccountRequestDTO(
        double initialBalance,
        String currency
) {
}
