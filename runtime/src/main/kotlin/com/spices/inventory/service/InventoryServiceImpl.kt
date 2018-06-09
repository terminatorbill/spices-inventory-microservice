package com.spices.inventory.service

import com.spices.inventory.domain.Stock
import com.spices.inventory.persistence.repository.InventoryRepositoryFacade
import org.springframework.stereotype.Component
import javax.inject.Inject

@Component
class InventoryServiceImpl @Inject constructor(val inventoryRepositoryFacade: InventoryRepositoryFacade) : InventoryService {

    override fun retrieveStock(productIds: List<String>): List<Stock> {
        return inventoryRepositoryFacade.retrieveStock(productIds.map { it.toLong() })
    }
}