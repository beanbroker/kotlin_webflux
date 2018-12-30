package com.example.corot.service

import com.example.corot.model.User
import reactor.core.publisher.Mono

internal interface UserService {
    fun getUserInfo(): Mono<User.UserRes>
    fun registUserInfo() : Mono<User.UserRes>
}
