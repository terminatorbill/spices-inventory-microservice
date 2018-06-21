package com.spices.inventory.exception

class LessProductsRetrievedException : RuntimeException {

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable): super(message, cause)
}