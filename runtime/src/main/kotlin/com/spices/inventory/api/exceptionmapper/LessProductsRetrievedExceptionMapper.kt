package com.spices.inventory.api.exceptionmapper

import com.spices.inventory.dto.ErrorCodeDto
import com.spices.inventory.dto.ErrorDto
import com.spices.inventory.exception.LessProductsRetrievedException
import org.slf4j.LoggerFactory
import java.util.UUID
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class LessProductsRetrievedExceptionMapper : ExceptionMapper<LessProductsRetrievedException> {
    override fun toResponse(e: LessProductsRetrievedException): Response {

        val uuid = UUID.randomUUID()
        LOG.error("Less stock retrieved. Exception {} with UUID {}", e.message, uuid)
        return Response
            .status(Response.Status.CONFLICT)
            .entity(ErrorDto(ErrorCodeDto.LESS_STOCK_RETRIEVED, "Less stocked retrieved than expected", uuid))
            .build()
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(LessProductsRetrievedExceptionMapper::class.java)
    }
}