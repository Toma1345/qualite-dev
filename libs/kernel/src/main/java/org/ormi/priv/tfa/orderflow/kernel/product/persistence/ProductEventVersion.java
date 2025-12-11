package org.ormi.priv.tfa.orderflow.kernel.product.persistence;

import org.ormi.priv.tfa.orderflow.kernel.product.ProductEventV1;

/**
 * Énumération des versions prises en charge pour les événements de domaine liés au produit.
 */

public enum ProductEventVersion {
    V1(ProductEventV1.EVENT_VERSION);

    private final int value;

    ProductEventVersion(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
