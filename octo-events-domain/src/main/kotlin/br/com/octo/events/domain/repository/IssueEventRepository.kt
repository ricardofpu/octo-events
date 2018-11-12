package br.com.octo.events.domain.repository

import br.com.octo.events.domain.model.IssueEvent

interface IssueEventRepository {

    fun save(event: IssueEvent): Int

    fun exists(id: Long): Boolean

    fun findById(id: Long): IssueEvent?

    fun findAllEventsByNumber(number: Long): List<IssueEvent>
}