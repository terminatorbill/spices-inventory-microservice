package com.spices.inventory.service

import com.spices.inventory.domain.Stock
import com.spices.inventory.persistence.InventoryDaoFacade
import org.springframework.stereotype.Component
import javax.inject.Inject

@Component
class InventoryServiceImpl @Inject constructor(val inventoryDaoFacade: InventoryDaoFacade) : InventoryService {

    override fun retrieveStock(productIds: List<String>): List<Stock> {
        return inventoryDaoFacade.retrieveStock(productIds.map { it.toLong() })
    }
}