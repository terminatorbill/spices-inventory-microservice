package com.spices.inventory.api

import com.spices.inventory.dto.StockDto
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

@Path("stock")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
interface InventoryApi {

    @GET
    fun retrieveStock(@QueryParam("productIds") productIds: List<String>): List<StockDto>
}