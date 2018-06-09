package com.spices.inventory.persistence.model

import javax.persistence.*

@Entity
@Table(name = "product_stock")
class StockEntity(
        @Id @Column(name = "stock_id") @GeneratedValue
        val stockId: Long,
        @Column(name = "product_id", nullable = false, unique = true)
        val productId: Long,
        @Column(name = "current_stock", nullable = false)
        val currentStock: Long,
        @Column(name = "minimum_stock", nullable = false)
        val minimumStock: Long,
        @Column(name = "maximum_stock", nullable = true)
        val maximumStock: Long?
) {

        override fun equals(other: Any?): Boolean {
                if (this === other) {
                        return true
                }
                if (other !is StockEntity) {
                        return false
                }

                if (productId != other.productId) {
                        return false
                }

                return true
        }

        override fun hashCode(): Int {
                return productId.hashCode()
        }
}