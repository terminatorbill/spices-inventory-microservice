package com.spices.inventory.persistence.projection

data class StockProjection(val productId: Long, val currentStock: Long)