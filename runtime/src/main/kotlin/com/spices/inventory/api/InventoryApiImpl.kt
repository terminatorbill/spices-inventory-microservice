package com.spices.inventory.api

import com.spices.inventory.domain.Stock
import com.spices.inventory.dto.ModificationStockDto
import com.spices.inventory.dto.StockDto
import com.spices.inventory.exception.LessProductsRetrievedException
import com.spices.inventory.service.InventoryService
import com.spices.inventory.service.exception.InventoryServiceException
import org.springframework.stereotype.Component
import javax.inject.Inject

@Component
class InventoryApiImpl @Inject constructor(val inventoryService: InventoryService) : InventoryApi {
    override fun retrieveStock(productIds: List<String>): List<StockDto> {
        try {
            val stocks: List<Stock> = inventoryService.retrieveStock(productIds)
            return stocks.asSequence()
                .map { convertToStockDto(it) }
                .toList()
        } catch (e: InventoryServiceException) {
            when (e.type) {
                InventoryServiceException.Type.LESS_PRODUCTS_RETRIEVED_THAN_EXPECTED -> throw LessProductsRetrievedException("Less products retrieved than expected", e)
            }
        }
    }

    override fun modifyStock(modificationStockList: List<ModificationStockDto>) {
        inventoryService.modifyStock(modificationStockList.asSequence().map { convertToStock(it) }.toList())
    }

    companion object {
        fun convertToStockDto(stock: Stock): StockDto {
            return StockDto(stock.productId, stock.currentStock)
        }

        fun convertToStock(modificationStockDto: ModificationStockDto): Stock {
            return Stock(modificationStockDto.productId.toLong(), modificationStockDto.quantity)
        }
    }
}