package com.spices.inventory.api

import com.spices.inventory.dto.Stock
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("stock")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
interface InventoryApi {

    @GET
    fun retrieveStock(@QueryParam("productIds") productIds: List<String>): List<Stock>
}