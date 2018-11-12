package br.com.octo.events.application

import br.com.octo.events.api.v1.request.EventRequest
import br.com.octo.events.api.v1.request.IssueRequest
import br.com.octo.events.api.v1.request.UserRequest
import br.com.octo.events.api.v1.response.EventResponse
import br.com.octo.events.application.config.ApplicationConfigTest
import br.com.octo.events.domain.enums.Action
import br.com.octo.events.domain.randomLong
import br.com.octo.events.infrastructure.jsonToObject
import br.com.octo.events.infrastructure.objectToJson
import capital.scalable.restdocs.AutoDocumentation
import capital.scalable.restdocs.SnippetRegistry
import capital.scalable.restdocs.jackson.JacksonResultHandlers
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors
import capital.scalable.restdocs.section.SectionSnippet
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.MessageSource
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.http.MediaType
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.restdocs.cli.CliDocumentation
import org.springframework.restdocs.http.HttpDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDateTime
import java.util.*
import javax.annotation.PostConstruct

@WebAppConfiguration
@SpringBootTest
@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [(ApplicationConfigTest::class)])
@Sql(
    scripts = ["classpath:sqlScripts/clear_tables.sql"],
    config = SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
abstract class ControllerBaseTest {

    @Autowired
    private lateinit var context: WebApplicationContext

    protected lateinit var mockMvc: MockMvc

    @get:Rule
    var restDocumentation = JUnitRestDocumentation()

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private val messageSource: MessageSource? = null

    private var accessor: MessageSourceAccessor? = null

    @PostConstruct
    private fun init() {
        accessor = MessageSourceAccessor(messageSource, Locale.US)
    }

    fun get(code: String): List<String> = listOf(accessor!!.getMessage(code))

    @Before
    fun setUp() {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(this.context)
            .alwaysDo<DefaultMockMvcBuilder>(JacksonResultHandlers.prepareJackson(objectMapper))
            .alwaysDo<DefaultMockMvcBuilder>(commonDocumentation())

            .apply<DefaultMockMvcBuilder>(
                MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                    .uris()
                    .and().snippets()
                    .withDefaults(
                        CliDocumentation.curlRequest(),
                        HttpDocumentation.httpRequest(),
                        HttpDocumentation.httpResponse(),
                        AutoDocumentation.requestFields(),
                        AutoDocumentation.responseFields(),
                        AutoDocumentation.pathParameters(),
                        AutoDocumentation.requestParameters(),
                        AutoDocumentation.description(),
                        AutoDocumentation.methodAndPath(),
                        buildSection()
                    )
            )
            .build()
    }

    private fun commonDocumentation(): RestDocumentationResultHandler {
        return MockMvcRestDocumentation.document(
            "{class-name}/{method-name}",
            Preprocessors.preprocessRequest(
                ResponseModifyingPreprocessors.replaceBinaryContent(),
                ResponseModifyingPreprocessors.limitJsonArrayLength(objectMapper),
                Preprocessors.prettyPrint()
            ),
            Preprocessors.preprocessResponse(
                ResponseModifyingPreprocessors.replaceBinaryContent(),
                ResponseModifyingPreprocessors.limitJsonArrayLength(objectMapper),
                Preprocessors.prettyPrint()
            )
        )
    }

    private fun buildSection(): SectionSnippet {
        return AutoDocumentation.sectionBuilder()
            .snippetNames(
                SnippetRegistry.PATH_PARAMETERS,
                SnippetRegistry.HTTP_REQUEST,
                SnippetRegistry.REQUEST_PARAMETERS,
                SnippetRegistry.REQUEST_FIELDS,
                SnippetRegistry.HTTP_RESPONSE,
                SnippetRegistry.RESPONSE_FIELDS
            )
            .skipEmpty(true)
            .build()
    }

    protected fun requestToCreateIssueEvent(
        request: EventRequest = dummyEventRequest()
    ): EventResponse {
        val response = this.mockMvc.perform(
            RestDocumentationRequestBuilders.post(
                "/v1/issues/events"
            )
                .content(request.objectToJson())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
            .andExpect(status().isCreated)
            .andReturn()

        return response.response.contentAsString.jsonToObject(EventResponse::class.java)
    }

    protected fun dummyEventRequest(
        action: String = Action.OPENED.toString(),
        issue: IssueRequest = dummyIssueRequest()
    ): EventRequest =
        EventRequest(
            action = action,
            issue = issue
        )

    protected fun dummyIssueRequest(
        id: Long = randomLong(),
        title: String = "Title",
        number: Long = randomLong(4),
        url: String = "http://url.com.br",
        user: UserRequest = dummyUserRequest(),
        createdAt: LocalDateTime = LocalDateTime.now()
    ): IssueRequest =
        IssueRequest(
            id = id,
            url = url,
            number = number,
            title = title,
            user = user,
            createdAt = createdAt
        )

    protected fun dummyUserRequest(): UserRequest =
        UserRequest(
            login = "login",
            id = randomLong(),
            url = "http://user.url.com.br"
        )
}