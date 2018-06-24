package com.spices.inventory.api

import com.spices.inventory.service.InventoryService
import org.springframework.stereotype.Component
import javax.inject.Inject

@Component
class ProductApiImpl @Inject constructor(val inventoryService: InventoryService) : ProductApi {
    override fun createProducts(productIds: List<String>) {
        inventoryService.createProducts(productIds)
    }
}