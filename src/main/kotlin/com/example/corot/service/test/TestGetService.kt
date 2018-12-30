package com.example.corot.service.test

import com.example.corot.model.TestModel
import reactor.core.publisher.Mono

internal interface TestGetService {
    fun getInfoByQueryParam(userId : Mono<String>): Mono<TestModel>

}
