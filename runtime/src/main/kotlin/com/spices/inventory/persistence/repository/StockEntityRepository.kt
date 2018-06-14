package com.spices.inventory.persistence.repository

import com.spices.inventory.persistence.model.StockEntity
import com.spices.inventory.persistence.projection.StockProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface StockEntityRepository : JpaRepository<StockEntity, Long> {

    @Query(
        "SELECT new com.spices.inventory.persistence.projection.StockProjection(s.productId, s.currentStock) " +
            "FROM StockEntity s WHERE s.productId IN :productIds"
    )
    fun retrieveStockForProducts(@Param("productIds") productIds: List<Long>): List<StockProjection>
}