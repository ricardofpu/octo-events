package br.com.octo.events.api.v1

import br.com.octo.events.api.v1.request.EventRequest
import br.com.octo.events.api.v1.response.EventResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.validation.Valid

/**
 * Issue API
 */
@RequestMapping("/v1/issues", produces = [MediaType.APPLICATION_JSON_VALUE])
interface IssueApi {

    /**
     * Create event
     */
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping("/events")
    fun create(@RequestBody @Valid request: EventRequest): EventResponse

    /**
     * Find all events by number
     * @param number Identifier a number of event
     */
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/{number}/events")
    fun find(@PathVariable("number") number: Long): List<EventResponse>
}