package com.example.corot.service.test

import com.example.corot.model.TestModel
import com.example.corot.model.TestResponse
import reactor.core.publisher.Mono

internal interface TestPostService{

    fun postUser(testModel: Mono<TestModel>) : Mono<TestResponse>
}