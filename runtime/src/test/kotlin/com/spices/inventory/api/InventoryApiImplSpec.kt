package com.spices.inventory.api

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.spices.inventory.domain.Stock
import com.spices.inventory.dto.ModificationStockDto
import com.spices.inventory.dto.StockDto
import com.spices.inventory.exception.LessProductsRetrievedException
import com.spices.inventory.service.InventoryService
import com.spices.inventory.service.exception.InventoryServiceException
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

object InventoryApiImplSpec : Spek({

    val inventoryService: InventoryService = mock()
    val inventoryApi: InventoryApi = InventoryApiImpl(inventoryService)

    given("A need to retrieve the stock for a list of products") {

        val expectedProductsIds: List<String> = listOf("1", "2", "3")
        val expectedStockForEachProduct: List<Stock> = listOf(
                Stock(1L, 50L),
                Stock(2L, 0L),
                Stock(3L, 30L)
        )
        on("calling the method to retrieve the stock of these products") {
            whenever(inventoryService.retrieveStock(expectedProductsIds)).thenReturn(expectedStockForEachProduct)

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

        on("throwing InventoryServiceException with code LESS_PRODUCTS_RETRIEVED_THAN_EXPECTED") {
            whenever(inventoryService.retrieveStock(expectedProductsIds)).thenThrow(InventoryServiceException("foo", InventoryServiceException.Type.LESS_PRODUCTS_RETRIEVED_THAN_EXPECTED))

            it ("should throw LessProductedRetrievedException") {
                assertThrows<LessProductsRetrievedException> { inventoryApi.retrieveStock(expectedProductsIds) }
            }
        }
    }

    given("Some products with their modified quantity") {
        val modifiedProductsStock = listOf(
            ModificationStockDto("1", 10L),
            ModificationStockDto("2", -5L)
        )

        val expectedModifiedProductsStock = listOf(
            Stock(modifiedProductsStock[0].productId.toLong(), modifiedProductsStock[0].quantity),
            Stock(modifiedProductsStock[1].productId.toLong(), modifiedProductsStock[1].quantity)
        )

        on("calling the method to update the provided product quantities") {
            inventoryApi.modifyStock(modifiedProductsStock)

            it("should change the quantity for the provided products") {
                verify(inventoryService, times(1)).modifyStock(expectedModifiedProductsStock)
            }
        }
    }
})