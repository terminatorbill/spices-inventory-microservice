package com.spices.inventory.service

import com.spices.inventory.domain.Stock
import com.spices.inventory.persistence.repository.InventoryRepositoryFacade
import com.spices.inventory.service.exception.InventoryServiceException
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

object InventoryServiceImplSpec : Spek({

    val inventoryDaoFacade = Mockito.mock(InventoryRepositoryFacade::class.java)
    val inventoryService = InventoryServiceImpl(inventoryDaoFacade)

    given("We want to retrieve the stock for each passed productId") {
        val productIds = listOf(1L, 2L, 3L)

        on("Retrieving the stock for each passed productId") {
            val expectedStock = listOf(
                Stock(1L, 100L),
                Stock(2L, 0L),
                Stock(3L, 30L)
            )

            Mockito.`when`(inventoryDaoFacade.retrieveStock(productIds)).thenReturn(expectedStock)

            val actualStock = inventoryService.retrieveStock(productIds.map { it.toString() })
            it("Should return the expected stock") {
                assertThat(actualStock.size, `is`(expectedStock.size))
                assertThat(actualStock[0].productId, `is`(expectedStock[0].productId))
                assertThat(actualStock[0].currentStock, `is`(expectedStock[0].currentStock))
                assertThat(actualStock[1].productId, `is`(expectedStock[1].productId))
                assertThat(actualStock[1].currentStock, `is`(expectedStock[1].currentStock))
                assertThat(actualStock[2].productId, `is`(expectedStock[2].productId))
                assertThat(actualStock[2].currentStock, `is`(expectedStock[2].currentStock))
            }
        }

        on("Retrieving stock for less products than expected due to some products that do not exist") {
            val lessStock = listOf(
                Stock(1L, 100L),
                Stock(2L, 0L)
            )

            Mockito.`when`(inventoryDaoFacade.retrieveStock(productIds)).thenReturn(lessStock)

            it("Should throw InventoryServiceException with code LESS_PRODUCTS_RETRIEVED_THAN_EXPECTED") {
                val ex: InventoryServiceException =
                    assertThrows { inventoryService.retrieveStock(productIds.map { it.toString() }) }

                assertThat(ex.type, `is`(InventoryServiceException.Type.LESS_PRODUCTS_RETRIEVED_THAN_EXPECTED))
            }
        }
    }

    given("We want to add missing products to the inventory") {
        val productsToCreate = listOf("1", "2", "3")

        on("Creating the products") {
            inventoryService.createProducts(productsToCreate)

            it("Should create the products") {
                verify(inventoryDaoFacade, times(1)).createProducts(productsToCreate)
            }
        }
    }
})