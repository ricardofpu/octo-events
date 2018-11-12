package br.com.octo.events.api.v1.response

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

/**
 * Issue Response
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class IssueResponse(

    /**
     * Url
     */
    val url: String,

    /**
     * Id
     */
    val id: Long,

    /**
     * Number
     */
    val number: Long,

    /**
     * Title
     */
    val title: String? = null,

    /**
     * The user response
     */
    val user: UserResponse,

    /**
     * Creation date
     */
    val createdAt: LocalDateTime
)