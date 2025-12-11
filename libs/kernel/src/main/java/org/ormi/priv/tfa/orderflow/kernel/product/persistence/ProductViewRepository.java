package org.ormi.priv.tfa.orderflow.kernel.product.persistence;

import java.util.List;
import java.util.Optional;

import org.ormi.priv.tfa.orderflow.kernel.product.ProductId;
import org.ormi.priv.tfa.orderflow.kernel.product.SkuId;
import org.ormi.priv.tfa.orderflow.kernel.product.views.ProductView;

/**
 * Interface de dépôt pour la gestion de la persistance et de la récupération du modèle de lecture {@link org.ormi.priv.tfa.orderflow.kernel.product.views.ProductView}.
 */

public interface ProductViewRepository {
    void save(ProductView productView);
    Optional<ProductView> findById(ProductId id);
    Optional<ProductView> findBySkuId(SkuId skuId);
    long countPaginatedViewsBySkuIdPattern(String skuIdPattern);
    List<ProductView> searchPaginatedViewsOrderBySkuId(String skuIdPattern, int page, int size);
}
