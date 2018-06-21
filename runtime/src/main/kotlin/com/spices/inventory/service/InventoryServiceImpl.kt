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

        verifyThatAllProductsExist(productIds, stock.asSequence().map { it.productId.toString() }.toList())

        return stock
    }

    private fun verifyThatAllProductsExist(expectedProducts: List<String>, actualProducts: List<String>) {
        if (expectedProducts.size != actualProducts.size) {
            throw InventoryServiceException("Less products retrieved that expected", InventoryServiceException.Type.LESS_PRODUCTS_RETRIEVED_THAN_EXPECTED)
        }
    }
}