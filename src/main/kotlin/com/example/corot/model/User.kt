package com.example.corot.model

import javax.validation.constraints.AssertTrue
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class User {
    open class UserReq {
        @NotBlank(message = "user name can't be null")
        var name: String? = null
        @NotBlank(message = "phone number can't be null")
        var phone_number: String = ""
        @NotBlank(message = "Rrn can't be null")
        var rrn: String? = null
        @NotBlank(message = "CI code can't be null")
        var ci: String? = null
        @NotBlank(message = "email can't be null")
        val email: String = ""
        @NotBlank(message = "User Id can't be null")
        var user_id: String? = null
        @NotBlank(message = "bank code can't be null")
        var bank_code: String? = null
        @NotBlank(message = "bank account can't be null")
        var bank_account_number: String? = null
        @NotNull(message = "You Must Aggree.")
        var agreements: List<Agreement>? = null
    }

    open class UserRes {
        @NotBlank(message = "bank account can't be null")
        var deposit_bank_account_number: String? = null
        @NotBlank(message = "bank code can't be null")
        var deposit_bank_code: String? = null
        @NotBlank(message = "tera id can't be null")
        var partner_user_id: String? = null
        @NotBlank(message = "KakaoPay Id can't be null")
        var user_id: String? = null
    }

    class Agreement {
        @AssertTrue(message =  "aggrement must be true")
        var agreed: Boolean = false
        @Min(value = 1, message ="agreement id must above 0")
        var agreement_id: Int = 0
        @Min(value = 1, message ="terms id must above 0")
        var terms_id: Int = 0
    }

    open class UserOut{
        @NotBlank(message = "tara id needed")
        var partner_user_id: String? = null
        @NotBlank(message = "kakao pay id needed")
        var user_id: String? = null
    }
}