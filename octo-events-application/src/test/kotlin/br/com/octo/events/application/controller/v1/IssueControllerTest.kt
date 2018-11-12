package br.com.octo.events.application.controller.v1

import br.com.octo.events.api.v1.response.EventResponse
import br.com.octo.events.application.ControllerBaseTest
import br.com.octo.events.domain.randomLong
import br.com.octo.events.infrastructure.jsonToArrayListObject
import br.com.octo.events.infrastructure.jsonToObject
import br.com.octo.events.infrastructure.objectToJson
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class IssueControllerTest : ControllerBaseTest() {

    @Test
    fun `should create event`() {
        val request = dummyEventRequest()

        this.mockMvc.perform(
            post(
                "/v1/issues/events"
            )
                .content(request.objectToJson())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
            .andExpect(status().isCreated)
            .andDo {
                val response = it.response.contentAsString.jsonToObject(EventResponse::class.java)
                assertNotNull(response)
                assertEquals(request.action, response.action)
                assertEquals(request.issue?.id, response.issue.id)
                assertEquals(request.issue?.url, response.issue.url)
                assertEquals(request.issue?.title, response.issue.title)
                assertEquals(request.issue?.number, response.issue.number)
                assertEquals(request.issue?.createdAt, response.issue.createdAt)
                assertEquals(request.issue?.user?.login, response.issue.user.login)
                assertEquals(request.issue?.user?.url, response.issue.user.url)
                assertEquals(request.issue?.user?.id, response.issue.user.id)
            }
    }

    @Test
    fun `shouldn't create event when the event id already exists`() {
        val request = dummyEventRequest()
        requestToCreateIssueEvent(request)

        this.mockMvc.perform(
            post(
                "/v1/issues/events"
            )
                .content(request.objectToJson())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `should find all events by number`() {
        val number = randomLong(5)
        val eventResponse1 = requestToCreateIssueEvent(dummyEventRequest(issue = dummyIssueRequest(number = number)))
        val eventResponse2 = requestToCreateIssueEvent(dummyEventRequest(issue = dummyIssueRequest(number = number)))

        this.mockMvc.perform(
            RestDocumentationRequestBuilders.get(
                "/v1/issues/{number}/events", number
            )
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
            .andExpect(status().isOk)
            .andDo {
                val response = it.response.contentAsString.jsonToArrayListObject(EventResponse::class.java)
                assertTrue(response.isNotEmpty())
                assertTrue(response.containsAll(listOf(eventResponse1, eventResponse2)))
            }
    }

}