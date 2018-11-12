package br.com.octo.events.api.v1.request

import javax.validation.constraints.NotNull

/**
 * User Request
 */
data class UserRequest(

    /**
     * Login
     */
    @field:NotNull val login: String?,

    /**
     * Id
     */
    @field:NotNull val id: Long?,

    /**
     * Url
     */
    @field:NotNull val url: String?
)