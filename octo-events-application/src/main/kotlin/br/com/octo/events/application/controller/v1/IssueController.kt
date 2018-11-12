package br.com.octo.events.application.controller.v1

import br.com.octo.events.api.v1.IssueApi
import br.com.octo.events.api.v1.request.EventRequest
import br.com.octo.events.api.v1.response.EventResponse
import br.com.octo.events.application.utils.toModel
import br.com.octo.events.application.utils.toResponse
import br.com.octo.events.domain.service.IssueEventService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

/**
 * Issue API
 */
@RestController
class IssueController(
    private val issueEventService: IssueEventService
) : IssueApi {

    /**
     * Create event
     */
    override fun create(@RequestBody @Valid request: EventRequest): EventResponse =
        request.run { this.toModel() }
            .run { issueEventService.save(this) }
            .run { this.toResponse() }

    /**
     * Find all events by number
     * @param number Identifier a number of event
     */
    override fun find(@PathVariable("number") number: Long): List<EventResponse> =
        issueEventService.findAllEventsByNumber(number).toResponse()

}