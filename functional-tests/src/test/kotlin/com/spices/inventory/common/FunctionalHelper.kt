package com.spices.inventory.common

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.IOException
import java.util.concurrent.ThreadLocalRandom

object FunctionalHelper {

    private val objectMapper = ObjectMapper()

    init {
        objectMapper.registerModule(KotlinModule())
    }

    fun <T> convertToString(obj: T): String {
        try {
            return objectMapper.writeValueAsString(obj)
        } catch (e: JsonProcessingException) {
            throw IllegalStateException(e)
        }
    }

    fun <T> convertToObject(content: String, typeReference: TypeReference<T>): T {
        try {
            return objectMapper.readValue(content, typeReference)
        } catch (e: IOException) {
            throw IllegalStateException(e)
        }
    }

    fun generateRandomNumber(bound: Int): Long {
        return (ThreadLocalRandom.current().nextInt(bound) + 1).toLong()
    }
}