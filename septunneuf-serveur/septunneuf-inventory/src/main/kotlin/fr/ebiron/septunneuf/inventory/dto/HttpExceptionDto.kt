package fr.ebiron.septunneuf.inventory.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class HttpExceptionDto(
    val statusCode: Int,
    val reasonPhrase: String,
    val details: String? = null,
    val additionalData: Any? = null
)