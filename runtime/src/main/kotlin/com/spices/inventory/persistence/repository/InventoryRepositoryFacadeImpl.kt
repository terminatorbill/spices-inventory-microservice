package com.spices.inventory.persistence.repository

import com.spices.inventory.domain.Stock
import com.spices.inventory.persistence.projection.StockProjection
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.inject.Inject

@Repository
class InventoryRepositoryFacadeImpl @Inject constructor(val stockEntityRepository: StockEntityRepository) : InventoryRepositoryFacade {

    @Transactional
    override fun retrieveStock(productIds: List<Long>): List<Stock> {
        val stock : List<StockProjection> = stockEntityRepository.retrieveStockForProducts(productIds)

        return stock.asSequence()
                .map { s -> Stock(s.productId, s.currentStock) }
                .toList()
    }
}