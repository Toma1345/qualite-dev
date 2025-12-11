package org.ormi.priv.tfa.orderflow.contracts.productregistry.v1.read;

import java.time.Instant;

/**
 * DTO représentant un élément dans un flux d'événements de produit, généralement utilisé pour les Server-Sent Events (SSE).
 * Il contient des informations minimales sur l'événement (type, ID du produit et horodatage).
 */

public record ProductStreamElementDto(
    String type,
    String productId,
    Instant occuredAt
) {
}
