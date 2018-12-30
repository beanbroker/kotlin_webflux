package com.example.corot.repository

import com.example.corot.exceptions.KaException
import com.example.corot.exceptions.ReqeustNotValidException
import com.example.corot.exceptions.ResourceNotFoundException
import com.example.corot.service.test.TestGetServiceImpl
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Component
class TestRepository {


    companion object {

        val testUser = listOf<String>("A", "B", "C")
        val RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND" to "NONE USER"
    }


    //어차피 디비는 블록이다
    fun getExistTestUser(idMono: Mono<String>) =
        idMono.flatMap {
            if (testUser.contains(it)) (it).toMono()
            else ResourceNotFoundException(RESOURCE_NOT_FOUND.first, RESOURCE_NOT_FOUND.second).toMono()

        }


}