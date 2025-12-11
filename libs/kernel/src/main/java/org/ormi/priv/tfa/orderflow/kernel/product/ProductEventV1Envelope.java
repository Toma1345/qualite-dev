package org.ormi.priv.tfa.orderflow.kernel.product;

import java.time.Instant;

import org.ormi.priv.tfa.orderflow.cqrs.EventEnvelope;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1.ProductDescriptionUpdated;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1.ProductNameUpdated;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1.ProductRegistered;
import org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1.ProductRetired;

/**
 * Classe abstraite pour un événement {@link org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1} de version 1 enveloppé avec des métadonnées de séquencement et d'horodatage.
 * Fournit des classes concrètes spécifiques pour chaque type d'événement afin de prendre en charge la correspondance de motifs (pattern matching) dans la logique de projection.
 */

public abstract class ProductEventV1Envelope<E extends ProductEventV1> extends EventEnvelope<E> {

    public ProductEventV1Envelope(E event, Long sequence, Instant timestamp) {
		super(event, sequence, timestamp);
	}

	public static class ProductRegisteredEnvelope extends ProductEventV1Envelope<ProductRegistered> {
        public ProductRegisteredEnvelope(ProductRegistered event, Long sequence, Instant timestamp) {
            super(event, sequence, timestamp);
        }
    }

    public static class ProductRetiredEnvelope extends ProductEventV1Envelope<ProductRetired> {
        public ProductRetiredEnvelope(ProductRetired event, Long sequence, Instant timestamp) {
            super(event, sequence, timestamp);
        }
    }

    public static class ProductNameUpdatedEnvelope extends ProductEventV1Envelope<ProductNameUpdated> {
        public ProductNameUpdatedEnvelope(ProductNameUpdated event, Long sequence, Instant timestamp) {
            super(event, sequence, timestamp);
        }
    }

    public static class ProductDescriptionUpdatedEnvelope extends ProductEventV1Envelope<ProductDescriptionUpdated> {
        public ProductDescriptionUpdatedEnvelope(ProductDescriptionUpdated event, Long sequence, Instant timestamp) {
            super(event, sequence, timestamp);
        }
    }
}
