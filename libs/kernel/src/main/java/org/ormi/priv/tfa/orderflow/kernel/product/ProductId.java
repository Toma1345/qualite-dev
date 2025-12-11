package org.ormi.priv.tfa.orderflow.kernel.product;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Objet de valeur représentant l'identifiant unique de l'agrégat {@link org.ormi.priv.tfa.orderflow.kernel.Product}.
 */

public record ProductId(@NotNull UUID value) {
    public static ProductId newId() {
        return new ProductId(UUID.randomUUID());
    }
}
