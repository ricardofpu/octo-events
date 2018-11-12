package br.com.octo.events.repository.extractor

import br.com.octo.events.domain.model.IssueEvent
import br.com.octo.events.domain.model.User
import br.com.octo.events.infrastructure.jsonToObject
import br.com.octo.events.repository.JdbcIssueEventRepository
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class IssueEventRowMapper : RowMapper<IssueEvent> {

    override fun mapRow(rs: ResultSet, rowNum: Int): IssueEvent {
        val id = rs.getLong(JdbcIssueEventRepository.ID_COLUMN)
        val action = rs.getString(JdbcIssueEventRepository.ACTION_COLUMN)
        val title = rs.getString(JdbcIssueEventRepository.TITLE_COLUMN)
        val number = rs.getLong(JdbcIssueEventRepository.NUMBER_COLUMN)
        val url = rs.getString(JdbcIssueEventRepository.URL_COLUMN)
        val user = rs.getString(JdbcIssueEventRepository.USER_COLUMN)
        val createdAt = rs.getTimestamp(JdbcIssueEventRepository.CREATED_AT_COLUMN)

        return IssueEvent(
            id = id,
            action = enumValueOf(action.toUpperCase()),
            title = title,
            number = number,
            url = url,
            user = user.jsonToObject(User::class.java),
            createdAt = createdAt.toLocalDateTime()
        )
    }
}