package com.spices.inventory.api.exceptionmapper

import com.spices.inventory.dto.ErrorCodeDto
import com.spices.inventory.dto.ErrorDto
import org.slf4j.LoggerFactory
import java.util.UUID
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class GenericExceptionMapper : ExceptionMapper<Exception> {
    override fun toResponse(e: Exception): Response {
        val uuid = UUID.randomUUID()
        GenericExceptionMapper.LOG.error("Exception {} with UUID {}", e.message, uuid)
        return Response
            .status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(ErrorDto(ErrorCodeDto.GENERIC, e.message ?: "", uuid))
            .build()
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(GenericExceptionMapper::class.java)
    }
}