package com.spices.inventory.api

import com.spices.inventory.domain.Stock
import com.spices.inventory.dto.StockDto
import com.spices.inventory.service.InventoryService
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.mockito.Mockito

object InventoryApiImplSpec : Spek({

    val inventoryService: InventoryService = Mockito.mock(InventoryService::class.java)
    val inventoryApi: InventoryApi = InventoryApiImpl(inventoryService)

    given("A need to retrieve the stock for a list of products") {

        val expectedProductsIds: List<String> = listOf("1", "2", "3")
        val expectedStockForEachProduct: List<Stock> = listOf(
                Stock(1L, 50L),
                Stock(2L, 0L),
                Stock(3L, 30L)
        )
        on("calling the method to retrieve the stock of these products") {
            Mockito.`when`(inventoryService.retrieveStock(expectedProductsIds)).thenReturn(expectedStockForEachProduct)

            val actualStockForEachProduct: List<StockDto> = inventoryApi.retrieveStock(expectedProductsIds)
            it("should return the correct stock for each product") {
                assertThat(actualStockForEachProduct.size, `is`(expectedStockForEachProduct.size))
                assertThat(actualStockForEachProduct[0].productId, `is`(expectedStockForEachProduct[0].productId))
                assertThat(actualStockForEachProduct[0].currentStock, `is`(expectedStockForEachProduct[0].currentStock))
                assertThat(actualStockForEachProduct[1].productId, `is`(expectedStockForEachProduct[1].productId))
                assertThat(actualStockForEachProduct[1].currentStock, `is`(expectedStockForEachProduct[1].currentStock))
                assertThat(actualStockForEachProduct[2].productId, `is`(expectedStockForEachProduct[2].productId))
                assertThat(actualStockForEachProduct[2].currentStock, `is`(expectedStockForEachProduct[2].currentStock))
            }
        }
    }
})