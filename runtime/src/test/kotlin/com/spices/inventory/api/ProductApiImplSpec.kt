package com.spices.inventory.api

import com.nhaarman.mockito_kotlin.mock
import com.spices.inventory.service.InventoryService
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

object ProductApiImplSpec : Spek({
    val inventoryService: InventoryService = mock()
    val productApi = ProductApiImpl(inventoryService)

    given("We need to create some missing products from the inventory") {
        val productsToCreate = listOf("1", "2", "3")

        on("Creating the products") {
            productApi.createProducts(productsToCreate)
            it("Should add the products") {
                verify(inventoryService, times(1)).createProducts(productsToCreate)
            }
        }
    }
})