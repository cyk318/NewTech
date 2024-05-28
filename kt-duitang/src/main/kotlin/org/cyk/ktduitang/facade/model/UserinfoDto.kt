package org.cyk.ktduitang.facade.model

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class LoginDto (
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val password: String,
)

data class RegDto (
    var id: String? = null,
    @field:NotBlank
    @field:Length(min = 1, max = 20)
    val username: String,
    @field:NotBlank
    @field:Length(min = 4, max = 20)
    val password: String,
)
