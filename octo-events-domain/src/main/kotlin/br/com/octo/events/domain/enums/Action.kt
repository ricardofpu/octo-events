package br.com.octo.events.domain.enums

enum class Action {
    ASSIGNED,
    UNASSIGNED,
    OPENED,
    EDITED,
    CLOSED,
    REOPENED;

    override fun toString(): String {
        return this.name.toLowerCase()
    }
}