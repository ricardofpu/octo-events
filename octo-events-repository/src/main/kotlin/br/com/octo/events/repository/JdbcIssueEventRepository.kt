package br.com.octo.events.repository

import br.com.octo.events.domain.model.IssueEvent
import br.com.octo.events.domain.repository.IssueEventRepository
import br.com.octo.events.infrastructure.objectToJson
import br.com.octo.events.repository.extractor.IssueEventRowMapper
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
open class JdbcIssueEventRepository(
    private val jdbcTemplate: JdbcTemplate
) : IssueEventRepository {

    companion object {
        const val TABLE_NAME = "issue_event"
        const val ID_COLUMN = "id"
        const val ACTION_COLUMN = "action"
        const val URL_COLUMN = "url"
        const val NUMBER_COLUMN = "number"
        const val TITLE_COLUMN = "title"
        const val USER_COLUMN = "username"
        const val CREATED_AT_COLUMN = "created_at"
    }

    override fun save(event: IssueEvent): Int {
        val sql = """
                    insert into $TABLE_NAME($ID_COLUMN, $ACTION_COLUMN, $URL_COLUMN, $NUMBER_COLUMN, $TITLE_COLUMN,
                      $USER_COLUMN, $CREATED_AT_COLUMN)
                    values (?, ?, ?, ?, ?, ?::json, ?)
                """
        return jdbcTemplate.update(
            sql,
            event.id,
            event.action.toString(),
            event.url,
            event.number,
            event.title,
            event.user.objectToJson(),
            event.createdAt
        )
    }

    override fun exists(id: Long): Boolean {
        val sql = """
                    select count($ID_COLUMN) from $TABLE_NAME where $ID_COLUMN = ?
                """
        return jdbcTemplate.queryForObject(sql, Int::class.java, id) > 0
    }

    override fun findById(id: Long): IssueEvent? {
        val sql = """
                    select * from $TABLE_NAME where $ID_COLUMN = ?
                """
        return try {
            jdbcTemplate.queryForObject(sql, IssueEventRowMapper(), id)
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    override fun findAllEventsByNumber(number: Long): List<IssueEvent> {
        val sql = """
                    select * from $TABLE_NAME where $NUMBER_COLUMN = ?
                """
        return jdbcTemplate.query(sql, IssueEventRowMapper(), number)
    }

}