package com.example.restsimple.repository

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class UpdateStudent {
    var name: @NotNull(message = "name is required") @Size(
        max = 200,
        message = "name must not exceed 200 characters"
    ) String? = null
    var lastName: @NotNull(message = "lastName is required") @Size(
        max = 200,
        message = "lastName must not exceed 200 characters"
    ) String? = null

    constructor()
    constructor(name: String?, lastName: String?) {
        this.name = name
        this.lastName = lastName
    }
}
