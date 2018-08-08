package com.spices.inventory.api.exceptionmapper

import com.spices.inventory.dto.ErrorCodeDto
import com.spices.inventory.dto.ErrorDto
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import javax.ws.rs.core.Response

class GenericExceptionMapperSpec : Spek({
    given("An Exception") {
        val ex = IllegalStateException("foo")

        on("Mapping this error") {
            val mapper = GenericExceptionMapper()

            it("Should return 500 with code GENERIC") {
                val response = mapper.toResponse(ex)

                MatcherAssert.assertThat(response.status, CoreMatchers.`is`(Response.Status.INTERNAL_SERVER_ERROR.statusCode))
                val errorDto = response.entity as ErrorDto

                MatcherAssert.assertThat(errorDto.code, CoreMatchers.`is`(ErrorCodeDto.GENERIC))
                MatcherAssert.assertThat(errorDto.description, CoreMatchers.`is`(CoreMatchers.notNullValue()))
                MatcherAssert.assertThat(errorDto.uuid, CoreMatchers.`is`(CoreMatchers.notNullValue()))
            }
        }
    }
})