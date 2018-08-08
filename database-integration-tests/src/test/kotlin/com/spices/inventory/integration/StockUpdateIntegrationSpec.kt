package com.spices.inventory.integration

import com.spices.inventory.IntegrationTestConfiguration
import com.spices.inventory.domain.Stock
import com.spices.inventory.persistence.model.StockEntity
import com.spices.inventory.persistence.repository.InventoryRepositoryFacade
import com.spices.inventory.persistence.repository.StockEntityRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [IntegrationTestConfiguration::class])
class StockUpdateIntegrationSpec {

    @Autowired
    private lateinit var inventoryRepositoryFacade: InventoryRepositoryFacade

    @Autowired
    private lateinit var stockEntityRepository: StockEntityRepository

    @BeforeEach
    fun setup() {
        stockEntityRepository.saveAll(
            listOf(
                StockEntity(null, 1L, 100L, 5L, 100L),
                StockEntity(null, 2L, 0L, 5L, 100L),
                StockEntity(null, 3L, 15L, 10L, null)
            )
        )
    }

    @AfterEach
    fun clear() {
        stockEntityRepository.deleteAll()
    }

    @Test
    @DisplayName("Should update the stock for a list of products")
    fun updateStock() {
        val modifiedStock = listOf(Stock(1L, 200L))

        inventoryRepositoryFacade.modifyStock(modifiedStock)

        val currentStock = inventoryRepositoryFacade.retrieveStock(listOf(1L, 2L, 3L))

        assertThat(currentStock[0].productId, `is`(1L))
        assertThat(currentStock[0].currentStock, `is`(200L))
        assertThat(currentStock[1].productId, `is`(2L))
        assertThat(currentStock[1].currentStock, `is`(0L))
        assertThat(currentStock[2].productId, `is`(3L))
        assertThat(currentStock[2].currentStock, `is`(15L))
    }

}