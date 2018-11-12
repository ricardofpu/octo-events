package br.com.octo.events.api.v1.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

/**
 * Event Request
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class EventRequest(

    /**
     * The action that was performed
     */
    @field:Pattern(regexp = "assigned|unassigned|opened|edited|closed|reopened") val action: String,

    /**
     * The issue request
     */
    @field:[NotNull Valid] val issue: IssueRequest?
)