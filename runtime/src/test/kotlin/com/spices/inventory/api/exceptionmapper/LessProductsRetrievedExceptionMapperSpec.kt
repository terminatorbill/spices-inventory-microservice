package com.spices.inventory.api.exceptionmapper

import com.spices.inventory.dto.ErrorCodeDto
import com.spices.inventory.dto.ErrorDto
import com.spices.inventory.exception.LessProductsRetrievedException
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import javax.ws.rs.core.Response

class LessProductsRetrievedExceptionMapperSpec : Spek({

    given("A LessProductsRetrievedException") {
        val ex = LessProductsRetrievedException("foo")

        on("Mapping this error") {
            val mapper = LessProductsRetrievedExceptionMapper()

            it("Should return 500 with code LESS_STOCK_RETRIEVED") {
                val response = mapper.toResponse(ex)

                assertThat(response.status, `is`(Response.Status.INTERNAL_SERVER_ERROR.statusCode))
                val errorDto = response.entity as ErrorDto

                assertThat(errorDto.code, `is`(ErrorCodeDto.LESS_STOCK_RETRIEVED))
                assertThat(errorDto.description, `is`(notNullValue()))
                assertThat(errorDto.uuid, `is`(notNullValue()))
            }
        }
    }
})