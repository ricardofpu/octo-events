package br.com.octo.events.application.service

import br.com.octo.events.domain.model.IssueEvent
import br.com.octo.events.domain.repository.IssueEventRepository
import br.com.octo.events.domain.service.IssueEventService
import br.com.octo.events.exception.handler.BusinessException
import br.com.octo.events.infrastructure.exception.OctoEventsErrorCode
import org.springframework.stereotype.Service

@Service
class IssueEventServiceImpl(
    private val issueEventRepository: IssueEventRepository
) : IssueEventService {

    override fun save(event: IssueEvent): IssueEvent {
        this.validateToCreate(event)

        when (issueEventRepository.save(event)) {
            1 -> return event
            else -> throw BusinessException(OctoEventsErrorCode.CREATE_ISSUE_EVENT_ERROR)
        }
    }

    override fun findAllEventsByNumber(number: Long): List<IssueEvent> {
        return issueEventRepository.findAllEventsByNumber(number = number)
    }

    private fun validateToCreate(event: IssueEvent) {
        if (issueEventRepository.exists(event.id)) {
            throw BusinessException(OctoEventsErrorCode.ISSUE_EVENT_ALREADY_EXISTS)
        }
    }

}