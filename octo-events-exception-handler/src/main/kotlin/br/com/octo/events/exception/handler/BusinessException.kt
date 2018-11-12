package br.com.octo.events.exception.handler

import br.com.octo.events.exception.handler.error.ErrorCode
import com.fasterxml.jackson.databind.ObjectMapper

class BusinessException : RuntimeException {

    companion object {
        fun tryBuildLogMessageAsJson(`object`: Any): String? =
            try {
                ObjectMapper().writeValueAsString(`object`)
            } catch (var2: Exception) {
                null
            }
    }

    var errorCode: ErrorCode? = null

    constructor() {}

    @Throws(RuntimeException::class)
    constructor(error: ErrorCode, cause: Throwable) : super(tryBuildLogMessageAsJson(error), cause) {
        this.errorCode = error
    }

    @Throws(RuntimeException::class)
    constructor(error: ErrorCode) : super(tryBuildLogMessageAsJson(error)) {
        this.errorCode = error
    }

    @Throws(RuntimeException::class)
    constructor(cause: Throwable) : super(cause) {
    }

}