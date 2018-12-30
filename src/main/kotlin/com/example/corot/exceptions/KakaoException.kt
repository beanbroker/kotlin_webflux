package com.example.corot.exceptions


open class KaException(val status: Int, val code: String, message: String?) : Throwable(message) {
//    fun toResult(defaultMessage: String = "") = ErrorResult(this.javaClass.simpleName, code, message ?: defaultMessage)
}

/**
 * 요청한 값이 부족하거나 올바르지 않을 경우
 */
class ReqeustNotValidException(code: String = "", message: String = "") : KaException(400, code, message) {
    constructor(pair: Pair<String, String>) : this(pair.first, pair.second)
}

/**
 * 요청한 객체가 존재하지 않을 경우
 */
class ResourceNotFoundException(code: String = "", message: String = "") : KaException(404, code, message) {
    constructor(pair: Pair<String, String>) : this(pair.first, pair.second)

}



