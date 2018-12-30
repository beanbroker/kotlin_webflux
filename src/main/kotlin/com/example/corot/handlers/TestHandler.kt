package com.example.corot.handlers

import com.example.corot.helpers.withBody
import com.example.corot.service.test.TestGetService
import org.learning.by.example.reactive.kotlin.microservices.KotlinReactiveMS.exceptions.InvalidParametersException
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

internal class TestHandler(val testGetService: TestGetService,
                           val errorHandler: ErrorHandler
) {

    private companion object {
        const val ID = "id"

    }

    internal fun getUserInfo(request: ServerRequest) =
        request.queryParam(ID).get().toMono()
//        request.pathVariable(ID).toMono()
            .transform(this::buildResponse)
//                    .transform(this::serverResponse)
            .onErrorResume(errorHandler::throwableError)

//    internal fun postLocation(request: ServerRequest) =
//            request.extract<LocationRequest>()
//                    .map(LocationRequest::address)
//                    .transform(this::buildResponse)
//                    .transform(this::serverResponse)
//                    .onErrorResume(errorHandler::throwableError)!!

    internal fun buildResponse(id: Mono<String>) =
            id.transform(testGetService::getInfoByQueryParam).flatMap {
                ok() withBody it
            }

//    internal fun sunriseSunset(geographicCoordinates: GeographicCoordinates) =
//            geographicCoordinates.toMono().transform(sunriseSunsetService::fromGeographicCoordinates)
//
//    internal fun serverResponse(locationResponseMono: Mono<LocationResponse>): Mono<ServerResponse> =
//            locationResponseMono.flatMap { ok() withBody it }
}
