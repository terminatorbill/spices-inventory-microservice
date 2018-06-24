package com.spices.inventory.persistence.repository

import com.spices.inventory.domain.Stock

interface InventoryRepositoryFacade {
    fun retrieveStock(productIds: List<Long>): List<Stock>
    fun createProducts(productIds: List<String>)
}