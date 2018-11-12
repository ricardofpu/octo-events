package br.com.octo.events.exception.handler

import br.com.octo.events.exception.handler.error.ExceptionResponse
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component

@Component
class MessageBuilder(
    private val messageSource: MessageSource
) {

    private val defaultMessageKey = "default.message.error"

    fun buildMessage(ex: BusinessException): ExceptionResponse {
        val locale = LocaleContextHolder.getLocale()
        val key = ex.errorCode?.key
        var message: String

        key?.let {
            message = messageSource.getMessage(key, null, key, locale)
            return ExceptionResponse(message)
        }

        message = messageSource.getMessage(defaultMessageKey, null, defaultMessageKey, locale)

        return ExceptionResponse(message)
    }
}
