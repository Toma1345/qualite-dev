package org.ormi.priv.tfa.orderflow.cqrs.infra.persistence;

import org.ormi.priv.tfa.orderflow.cqrs.EventEnvelope;
import org.ormi.priv.tfa.orderflow.cqrs.infra.jpa.EventLogEntity;

/**
 * Interface de dépôt pour la gestion du journal des événements (Event Log).
 * Responsable de la persistance des {@link org.ormi.priv.tfa.orderflow.cqrs.EventEnvelope}s.
 */

public interface EventLogRepository {
    EventLogEntity append(EventEnvelope<?> eventLog);
}
