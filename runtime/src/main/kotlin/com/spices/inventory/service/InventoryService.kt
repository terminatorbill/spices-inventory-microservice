package com.spices.inventory.service

import com.spices.inventory.domain.Stock

interface InventoryService {
    fun retrieveStock(productIds: List<String>): List<Stock>
}