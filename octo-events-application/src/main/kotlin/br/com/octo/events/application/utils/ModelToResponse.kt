package br.com.octo.events.application.utils

import br.com.octo.events.api.v1.response.EventResponse
import br.com.octo.events.api.v1.response.IssueResponse
import br.com.octo.events.api.v1.response.UserResponse
import br.com.octo.events.domain.model.IssueEvent
import br.com.octo.events.domain.model.User

fun List<IssueEvent>.toResponse(): List<EventResponse> =
    this.map { it.toResponse() }

fun IssueEvent.toResponse(): EventResponse =
    EventResponse(
        action = this.action.toString(),
        issue = IssueResponse(
            id = this.id,
            url = this.url,
            number = this.number,
            title = this.title,
            user = this.user.toResponse(),
            createdAt = this.createdAt
        )
    )

fun User.toResponse(): UserResponse =
    UserResponse(
        login = this.login,
        id = this.id,
        url = this.url
    )