package com.example.restsimple.response

import com.example.restsimple.model.Student
import javax.validation.constraints.NotNull

open class StudentCreateResponse(var student: @NotNull(message = "student is required") Student?)
