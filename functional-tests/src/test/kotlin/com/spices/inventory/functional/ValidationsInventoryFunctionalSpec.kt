package com.spices.inventory.functional

import com.fasterxml.jackson.core.type.TypeReference
import com.spices.inventory.common.FunctionalHelper.convertToObject
import com.spices.inventory.common.FunctionalHelper.convertToString
import com.spices.inventory.common.FunctionalHelper.generateRandomNumber
import com.spices.inventory.dto.ErrorCodeDto
import com.spices.inventory.dto.ErrorDto
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.javalite.http.Http
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import javax.ws.rs.core.MediaType

object ValidationsInventoryFunctionalSpec : Spek({

    given("We want to retrieve the stock for a list of products") {
        val bound = 1_000_000
        val products = listOf(generateRandomNumber(bound))

        val createProductsResponse = Http.post("http://localhost:9090/product", convertToString(products))
            .header("Content-Type", MediaType.APPLICATION_JSON)

        assertThat(createProductsResponse.responseCode(), `is`(204))

        on("Requesting to retrieve the products") {
            val retrieveStockResponse = Http.get("http://localhost:9090/stock?productIds=${products[0]}&productIds=-5&productIds=-12")

            it("Should return 409 (CONFLICT) with code LESS_STOCK_RETRIEVED when not all the requested product ids exist") {
                val typeReference = object : TypeReference<ErrorDto>() {}
                assertThat(retrieveStockResponse.responseCode(), `is`(409))

                val errorDto = convertToObject(retrieveStockResponse.text(), typeReference)

                assertThat(errorDto.code, `is`(ErrorCodeDto.LESS_STOCK_RETRIEVED))
                assertThat(errorDto.description, `is`(notNullValue()))
                assertThat(errorDto.uuid, `is`(notNullValue()))
            }
        }
    }
})