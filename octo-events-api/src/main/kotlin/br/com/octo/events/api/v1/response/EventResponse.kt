package br.com.octo.events.api.v1.response

/**
 * Event Response
 */
data class EventResponse(

    /**
     * Action
     */
    val action: String,

    /**
     * The issue response
     */
    val issue: IssueResponse
)