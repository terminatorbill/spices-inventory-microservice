package com.spices.inventory.persistence

import com.spices.inventory.domain.Stock

interface InventoryDaoFacade {
    fun retrieveStock(productIds: List<Long>): List<Stock>
}