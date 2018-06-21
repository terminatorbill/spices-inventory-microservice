package com.spices.inventory.service.exception

class InventoryServiceException : RuntimeException {
    val type: Type

    enum class Type {
        LESS_PRODUCTS_RETRIEVED_THAN_EXPECTED
    }

    constructor(message: String, type: Type) : super(message) {
        this.type = type
    }

    constructor(message: String, type: Type, cause: Throwable) : super(message, cause) {
        this.type = type
    }


}