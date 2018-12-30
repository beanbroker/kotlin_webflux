package com.example.corot.service.test

import com.example.corot.exceptions.KaException
import com.example.corot.exceptions.ReqeustNotValidException
import com.example.corot.exceptions.TestException
import com.example.corot.model.TestModel
import com.example.corot.model.TestResponse
import com.example.corot.repository.TestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.toEntity
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.lang.Exception

internal class TestGetServiceImpl(
    val endPoint: String,
    var webClient: WebClient = WebClient.create(endPoint)

) :
    TestGetService {
    private companion object {


        val RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND" to "id is required"

        const val ERROR_NOT_200 = "response was not 200"
        val USER_NOT_VALID = "USER_NOT_VALID" to "user is an out user"
        val UNKNOW_USER_STATUS = "UNKNOW_USER_STATUS" to "UNKNOW_USER_STATUS"


    }




    override fun getInfoByQueryParam(userId: Mono<String>) = userId
        .transform(this::isExistUser)
        .transform(this::buildUrl)
        .transform(this::get)
        .onErrorResume { it.toMono() }
        .transform(this::createResponse)!!

    internal open fun isExistUser(userId: Mono<String>): Mono<String>

        =  TestRepository().getExistTestUser(userId).toMono()



    internal open fun buildUrl(userId: Mono<String>) =
        userId.flatMap {
            if (it != "") (endPoint + "v1/please?id=$it").toMono()
            else ReqeustNotValidException(RESOURCE_NOT_FOUND.first, RESOURCE_NOT_FOUND.second).toMono()
//            else Exception(RESOURCE_NOT_FOUND.first).toMono()
        }

    internal open fun get(urlMono: Mono<String>) =
        urlMono.flatMap {
            webClient.get()
                .uri(it)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().flatMap { it.toEntity<TestResponse>() }
        }


    internal open fun createResponse(responseMono: Mono<ResponseEntity<TestResponse>>) =
        responseMono.flatMap {
            if (it.statusCode != HttpStatus.OK)
                TestException(ERROR_NOT_200).toMono()
            else with(it.body) {
                when (status /* userStatus*/) {
                    0 -> with(status) { TestModel(id, extId).toMono() }
                    9 -> KaException(400, USER_NOT_VALID.first, USER_NOT_VALID.second).toMono()
                    else -> KaException(400, UNKNOW_USER_STATUS.first, UNKNOW_USER_STATUS.second).toMono()
                }
            }
        }
}
