package com.example.restsimple.request

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

open class StudentCreateRequest(name: String? = null, lastName: String? = null) {
    var name: @NotNull(message = "name is required") @Size(
        max = 200,
        message = "name must not exceed 200 characters"
    ) String? = name
    var lastName: @NotNull(message = "lastName is required") @Size(
        max = 200,
        message = "lastName must not exceed 200 characters"
    ) String? = lastName
}
