package br.com.octo.events.infrastructure.exception

import br.com.octo.events.exception.handler.error.ErrorCode

private class Error(code: String, key: String) : ErrorCode(code, key)

object OctoEventsErrorCode {

    val CREATE_ISSUE_EVENT_ERROR: ErrorCode =
        Error("CREATE_ISSUE_EVENT_ERROR", "create.issue.event.error")

    val ISSUE_EVENT_ALREADY_EXISTS: ErrorCode =
        Error("ISSUE_EVENT_ALREADY_EXISTS", "issue.event.already.exists")

}