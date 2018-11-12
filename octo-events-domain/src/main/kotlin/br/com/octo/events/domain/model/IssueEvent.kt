package br.com.octo.events.domain.model

import br.com.octo.events.domain.enums.Action
import java.time.LocalDateTime

data class IssueEvent(
    val id: Long,
    val action: Action,
    val url: String,
    val number: Long,
    val title: String,
    val user: User,
    val createdAt: LocalDateTime
)
