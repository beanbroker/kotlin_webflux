package com.example.corot.handlers

import com.example.corot.helpers.getLogger
import com.example.corot.helpers.withBody
import com.example.corot.model.ErrorResponse
import org.learning.by.example.reactive.kotlin.microservices.KotlinReactiveMS.exceptions.PathNotFoundException
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.status
import reactor.core.publisher.Mono

internal class ErrorHandler {

    companion object {
        private val logger = getLogger<ErrorHandler>()
        private val NOT_FOUND = "not found"
        private val ERROR_RAISED = "error raised"
    }

    @Suppress("UNUSED_PARAMETER")
    fun notFound(request: ServerRequest) =
            Mono.just(PathNotFoundException(NOT_FOUND)).transform(this::getResponse)!!


    fun throwableError(throwable: Throwable): Mono<ServerResponse> {
        logger.error(ERROR_RAISED, throwable)
        return Mono.just(throwable).transform(this::getResponse)
    }

    private fun <T : Throwable> getResponse(monoError: Mono<T>): Mono<ServerResponse> =
            monoError.transform(ThrowableTranslator.Translate::throwable).flatMap {
                status(it.httpStatus) withBody ErrorResponse(it.code, it.message)
            }

}
