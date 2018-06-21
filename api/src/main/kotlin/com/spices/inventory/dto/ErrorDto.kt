package com.spices.inventory.dto

import java.util.UUID

data class ErrorDto(val code: ErrorCodeDto, val description: String, val uuid: UUID?)