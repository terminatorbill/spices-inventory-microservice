package com.spices.inventory.api

import com.spices.inventory.dto.Stock
import org.springframework.stereotype.Component
import javax.inject.Inject

@Component
class InventoryApiImpl @Inject constructor()  : InventoryApi {

    override fun retrieveStock(productIds: List<String>): List<Stock> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}