package org.ormi.priv.tfa.orderflow.cqrs;

import java.time.Instant;
import java.util.UUID;

/**
 * Conteneur pour un {@link DomainEvent} qui ajoute des métadonnées requises pour le traitement des événements,
 * telles qu'un numéro de séquence unique (pour l'ordonnancement) et un horodatage.
 *
 * @param <E> Le type de l'événement de domaine.
 */

public class EventEnvelope<E extends DomainEvent> {
    private final E event;
    private final Long sequence;
    private final Instant timestamp;

    public EventEnvelope(E event, Long sequence, Instant timestamp) {
        this.event = event;
        this.sequence = sequence;
        this.timestamp = timestamp;
    }

    public UUID aggregateId() {
        return event.aggregateId();
    }
    public String aggregateType() {
        return event.aggregateType();
    }
    public E event() {
        return event;
    }
    public Long sequence() {
        return sequence;
    }
    public Instant timestamp() {
        return timestamp;
    }

    public static <E extends DomainEvent> EventEnvelope<E> with(E event, Long sequence) {
        return new EventEnvelope<>(event, sequence, Instant.now());
    }
}
