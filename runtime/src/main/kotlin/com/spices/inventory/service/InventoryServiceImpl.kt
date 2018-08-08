package com.spices.inventory.service

import com.spices.inventory.domain.Stock
import com.spices.inventory.persistence.repository.InventoryRepositoryFacade
import com.spices.inventory.service.exception.InventoryServiceException
import org.springframework.stereotype.Component
import javax.inject.Inject

@Component
class InventoryServiceImpl @Inject constructor(val inventoryRepositoryFacade: InventoryRepositoryFacade) : InventoryService {
    override fun retrieveStock(productIds: List<String>): List<Stock> {
        val stock: List<Stock> = inventoryRepositoryFacade.retrieveStock(productIds.map { it.toLong() })

        if (!areAllProductsPresent(productIds, stock)) {
            throw InventoryServiceException("Less products retrieved that expected", InventoryServiceException.Type.LESS_PRODUCTS_RETRIEVED_THAN_EXPECTED)
        }

        return stock
    }

    override fun createProducts(productIds: List<String>) {
        inventoryRepositoryFacade.createProducts(productIds)
    }

    override fun modifyStock(modificationStockList: List<Stock>) {
        val currentStock: Map<Long, Stock> = inventoryRepositoryFacade.retrieveStock(modificationStockList.map { it.productId })
            .associate { it.productId to it }

        if (!areAllProductsPresent(currentStock.keys.toList(), modificationStockList)) {
            throw IllegalStateException("Some products do not exist (anymore) in the system")
        }

        val currentProductIds = currentStock.keys.toList()
        val modifiedStockList: List<Stock> = modificationStockList
            .filter { it -> currentProductIds.contains(it.productId) }
            .map { it -> Stock(it.productId, modifyQuantity(it, currentStock)) }

        inventoryRepositoryFacade.modifyStock(modifiedStockList)
    }

    private fun modifyQuantity(stockForModification: Stock, currentStock: Map<Long, Stock>) =
        currentStock[stockForModification.productId]!!.currentStock + stockForModification.currentStock

    private fun areAllProductsPresent(expectedProducts: List<*>, actualProducts: List<*>): Boolean {
        return expectedProducts.size == actualProducts.size
    }
}