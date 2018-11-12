package br.com.octo.events.domain.service

import br.com.octo.events.domain.model.IssueEvent

interface IssueEventService {

    fun save(event: IssueEvent): IssueEvent

    fun findAllEventsByNumber(number: Long): List<IssueEvent>
}