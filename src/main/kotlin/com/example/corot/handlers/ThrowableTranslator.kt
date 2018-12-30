package com.example.corot.handlers


import com.example.corot.exceptions.KaException
import org.learning.by.example.reactive.kotlin.microservices.KotlinReactiveMS.exceptions.InvalidParametersException
import org.learning.by.example.reactive.kotlin.microservices.KotlinReactiveMS.exceptions.PathNotFoundException
import org.springframework.http.HttpStatus
import reactor.core.publisher.Mono

internal class ThrowableTranslator private constructor(throwable: Throwable) {


    val httpStatus: HttpStatus
    val message: String
    val code : String


    init {
        this.httpStatus = getStatus(throwable)
        this.message = throwable.message.toString()
        this.code = when(throwable.cause){
            is KaException ->  (throwable as KaException).code
            else -> UNKNOWN_ERROR
        }
    }

    private fun getStatus(throwable: Throwable): HttpStatus =
            when (throwable) {
                is InvalidParametersException -> HttpStatus.BAD_REQUEST
                is PathNotFoundException -> HttpStatus.NOT_FOUND
                is KaException -> HttpStatus.valueOf(throwable.status)
//                is GeoLocationNotFoundException -> HttpStatus.NOT_FOUND
//                is GetGeoLocationException ->
//                    when (throwable.cause) {
//                        is InvalidParametersException -> HttpStatus.BAD_REQUEST
//                        else -> HttpStatus.INTERNAL_SERVER_ERROR
//                    }
                else -> HttpStatus.INTERNAL_SERVER_ERROR
            }

    companion object Translate {
        const val UNKNOWN_ERROR = "UNKNOWN_ERROR"
        fun <T : Throwable> throwable(throwable: Mono<T>) = throwable.map(::ThrowableTranslator)!!
    }
}
