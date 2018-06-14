package com.spices.inventory.api

import com.spices.inventory.domain.Stock
import com.spices.inventory.dto.StockDto
import com.spices.inventory.service.InventoryService
import org.springframework.stereotype.Component
import javax.inject.Inject

@Component
class InventoryApiImpl @Inject constructor(val inventoryService: InventoryService) : InventoryApi {

    override fun retrieveStock(productIds: List<String>): List<StockDto> {
        val stocks: List<Stock> = inventoryService.retrieveStock(productIds)
        return stocks.asSequence()
            .map { stock -> stock.convertToStockDto() }
            .toList()
    }
}

fun Stock.convertToStockDto(): StockDto {
    return StockDto(this.productId, this.currentStock)
}