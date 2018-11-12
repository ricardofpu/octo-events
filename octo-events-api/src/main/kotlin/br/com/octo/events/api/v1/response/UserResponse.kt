package br.com.octo.events.api.v1.response

/**
 * User Response
 */
data class UserResponse(

    /**
     * Login
     */
    val login: String,

    /**
     * Id
     */
    val id: Long,

    /**
     * Url
     */
    val url: String
)