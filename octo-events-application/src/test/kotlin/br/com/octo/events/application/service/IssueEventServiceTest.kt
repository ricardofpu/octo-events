package br.com.octo.events.application.service

import br.com.octo.events.application.ControllerBaseTest
import br.com.octo.events.domain.dummyIssueEvent
import br.com.octo.events.domain.model.IssueEvent
import br.com.octo.events.domain.randomLong
import br.com.octo.events.domain.service.IssueEventService
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class IssueEventServiceTest : ControllerBaseTest() {

    @Autowired
    private lateinit var issueEventService: IssueEventService

    @Test
    fun `should create event`() {
        val event = dummyIssueEvent()

        val saved = issueEventService.save(event)
        assertNotNull(saved)
        assertEquals(event.id, saved.id)
        assertEquals(event.action, saved.action)
        assertEquals(event.url, saved.url)
        assertEquals(event.number, saved.number)
        assertEquals(event.title, saved.title)
        assertEquals(event.user, saved.user)
        assertEquals(event.createdAt, saved.createdAt)
    }

    @Test
    fun `should find all events by number`() {
        val number = randomLong(5)
        val event1 = createEvent(dummyIssueEvent(number = number))
        val event2 = createEvent(dummyIssueEvent(number = number))

        val list = issueEventService.findAllEventsByNumber(number)
        assertTrue(list.isNotEmpty())
        assertTrue(list.containsAll(listOf(event1, event2)))
    }

    private fun createEvent(event: IssueEvent = dummyIssueEvent()): IssueEvent {
        val saved = issueEventService.save(event)
        assertNotNull(saved)
        return saved
    }

}