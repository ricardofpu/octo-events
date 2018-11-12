package br.com.octo.events.application.utils

import br.com.octo.events.api.v1.request.EventRequest
import br.com.octo.events.api.v1.request.UserRequest
import br.com.octo.events.domain.model.IssueEvent
import br.com.octo.events.domain.model.User

fun EventRequest.toModel(): IssueEvent =
    IssueEvent(
        id = this.issue?.id!!,
        action = enumValueOf(this.action.toUpperCase()),
        url = this.issue?.url!!,
        number = this.issue?.number!!,
        title = this.issue?.title!!,
        user = this.issue?.user!!.toModel(),
        createdAt = this.issue?.createdAt!!
    )

fun UserRequest.toModel(): User =
    User(
        login = this.login!!,
        id = this.id!!,
        url = this.url!!
    )
