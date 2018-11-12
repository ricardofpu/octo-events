package br.com.octo.events.exception.handler.handler

import br.com.octo.events.exception.handler.BusinessException
import br.com.octo.events.exception.handler.MessageBuilder
import br.com.octo.events.exception.handler.NotFoundException
import br.com.octo.events.exception.handler.error.ExceptionResponse
import br.com.octo.events.exception.handler.error.ResourceValueResponse
import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.nio.file.AccessDeniedException

@ControllerAdvice
@ResponseBody
open class GlobalExceptionHandler(
    private val messageBuilder: MessageBuilder
) {

    private val LOG = LogManager.getLogger(this.javaClass)

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException): ExceptionResponse {
        return ExceptionResponse()
    }

    @ExceptionHandler(AccessDeniedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleAccessDeniedException(ex: Exception): ExceptionResponse {
        return ExceptionResponse("Access denied error")
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected fun handleNotFound(ex: NotFoundException): ResourceValueResponse? {
        return if (ex.resourceValue != null) ResourceValueResponse(ex.resourceValue!!) else null
    }

    @ExceptionHandler(BusinessException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected fun handleBusiness(ex: BusinessException): ExceptionResponse {
        return messageBuilder.buildMessage(ex)
    }

    @ExceptionHandler(NullPointerException::class, IllegalArgumentException::class, IllegalStateException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleInternalException(ex: RuntimeException): ExceptionResponse {
        LOG.error("500 Status Code", ex)
        return ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.message)
    }
}