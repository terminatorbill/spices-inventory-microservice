package com.spices.inventory.functional

import com.fasterxml.jackson.core.type.TypeReference
import com.spices.inventory.common.FunctionalHelper.convertToObject
import com.spices.inventory.common.FunctionalHelper.convertToString
import com.spices.inventory.common.FunctionalHelper.generateRandomNumber
import com.spices.inventory.dto.StockDto
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.javalite.http.Http
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import javax.ws.rs.core.MediaType

object InventoryFunctionalSpec : Spek({

    given("We want to retrieve the stock for a list of products") {
        val bound: Int = 1_000_000
        val products = listOf(
            generateRandomNumber(bound),
            generateRandomNumber(bound),
            generateRandomNumber(bound)
        )

        val createProductsResponse = Http.post("http://localhost:9090/product", convertToString(products))
            .header("Content-Type", MediaType.APPLICATION_JSON)

        assertThat(createProductsResponse.responseCode(), `is`(204))

        on("Requesting to retrieve the products") {
            val retrieveStockResponse = Http.get("http://localhost:9090/stock?productIds=${products[0]}&productIds=${products[1]}&productIds=${products[2]}")

            it("Should return 200 (OK) with the stock for the requested products") {
                val typeReference = object : TypeReference<List<StockDto>>() {}
                assertThat(retrieveStockResponse.responseCode(), `is`(200))
                val stock: List<StockDto> = convertToObject(retrieveStockResponse.text(), typeReference)

                assertThat(stock.size, `is`(3))
                assertThat(stock[0].productId, `is`(products[0]))
                assertThat(stock[0].currentStock, `is`(0L))
                assertThat(stock[1].productId, `is`(products[1]))
                assertThat(stock[1].currentStock, `is`(0L))
                assertThat(stock[2].productId, `is`(products[2]))
                assertThat(stock[2].currentStock, `is`(0L))
            }
        }
    }
})