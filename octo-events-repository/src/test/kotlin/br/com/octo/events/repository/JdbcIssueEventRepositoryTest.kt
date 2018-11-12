package br.com.octo.events.repository

import br.com.octo.events.domain.dummyIssueEvent
import br.com.octo.events.domain.randomLong
import br.com.octo.events.repository.config.RepositoryBaseTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class JdbcIssueEventRepositoryTest : RepositoryBaseTest() {

    @Test
    fun `should save event and then find by id`() {
        val event = dummyIssueEvent()

        val saved = issueEventRepository.save(event)
        assertEquals(1, saved)

        val find = issueEventRepository.findById(event.id)
        assertNotNull(find)
        assertEquals(event.id, find?.id)
        assertEquals(event.action, find?.action)
        assertEquals(event.url, find?.url)
        assertEquals(event.number, find?.number)
        assertEquals(event.title, find?.title)
        assertEquals(event.user, find?.user)
        assertEquals(event.createdAt, find?.createdAt)
    }

    @Test
    fun `should find event null when not exists`() {
        val find = issueEventRepository.findById(randomLong())
        assertNull(find)
    }

    @Test
    fun `should find all events by number empty when not exists`() {
        val find = issueEventRepository.findAllEventsByNumber(randomLong())
        assertTrue(find.isEmpty())
    }

    @Test
    fun `should find all events by number`() {
        val number = randomLong(4)
        val event1 = dummyIssueEvent(number = number)

        var saved = issueEventRepository.save(event1)
        assertEquals(1, saved)

        //Should list one event
        var list = issueEventRepository.findAllEventsByNumber(number)
        assertTrue(list.isNotEmpty())
        assertTrue(list.contains(event1))

        val event2 = dummyIssueEvent(number = number)
        saved = issueEventRepository.save(event2)
        assertEquals(1, saved)

        //Should list two events
        list = issueEventRepository.findAllEventsByNumber(number)
        assertTrue(list.isNotEmpty())
        assertTrue(list.containsAll(listOf(event1, event2)))

        val event3 = dummyIssueEvent(number = number)
        saved = issueEventRepository.save(event3)
        assertEquals(1, saved)

        //Should list three events
        list = issueEventRepository.findAllEventsByNumber(number)
        assertTrue(list.isNotEmpty())
        assertTrue(list.containsAll(listOf(event1, event2, event3)))
    }

}