package com.example.corot.routers

import com.example.corot.handlers.TestHandler
import com.example.corot.handlers.ErrorHandler
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router


internal class TestRouter(val handler: TestHandler, val errorHandler: ErrorHandler) {

    private companion object {
        const private val API_PATH = "/test"
        const val LOCATION_PATH = "/location"
        const val ADDRESS_ARG = "/{address}"
        const val ANY_PATH = "/**"
        const val LOCATION_WITH_ADDRESS_PATH = "$LOCATION_PATH$ADDRESS_ARG"
    }

    fun doRoute() = router {
        (accept(MediaType.APPLICATION_JSON_UTF8) and API_PATH).nest {
            GET("/")(handler::getUserInfo)
//            POST(LOCATION_PATH)(handler::postLocation)
            path(ANY_PATH)(errorHandler::notFound)
        }
    }
}
