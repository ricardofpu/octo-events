package br.com.octo.events.api.v1.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDateTime
import javax.validation.Valid
import javax.validation.constraints.NotNull

/**
 * Issue Request
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class IssueRequest(

    /**
     * Url
     */
    @field:NotNull val url: String?,

    /**
     * Id
     */
    @field:NotNull val id: Long?,

    /**
     * Number
     */
    @field:NotNull val number: Long?,

    /**
     * Title
     */
    @field:NotNull val title: String?,

    /**
     * The user request
     */
    @field:[NotNull Valid] val user: UserRequest?,

    /**
     * Creation date
     */
    @field:NotNull val createdAt: LocalDateTime?
)