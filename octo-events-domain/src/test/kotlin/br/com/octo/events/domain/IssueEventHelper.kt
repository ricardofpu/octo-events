package br.com.octo.events.domain

import br.com.octo.events.domain.enums.Action
import br.com.octo.events.domain.model.IssueEvent
import br.com.octo.events.domain.model.User
import java.time.LocalDateTime

fun dummyIssueEvent(
    id: Long = randomLong(),
    action: Action = Action.OPENED,
    title: String = "Title",
    number: Long = randomLong(4),
    url: String = "http://url.com.br",
    user: User = User(
        login = "login",
        id = randomLong(),
        url = "http://user.url.com.br"
    ),
    createdAt: LocalDateTime = LocalDateTime.now()
): IssueEvent =
    IssueEvent(
        id = id,
        action = action,
        title = title,
        number = number,
        url = url,
        user = user,
        createdAt = createdAt
    )