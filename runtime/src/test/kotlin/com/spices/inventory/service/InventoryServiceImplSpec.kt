package com.spices.inventory.service

import com.spices.inventory.domain.Stock
import com.spices.inventory.persistence.repository.InventoryRepositoryFacade
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.mockito.Mockito

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
    }

})