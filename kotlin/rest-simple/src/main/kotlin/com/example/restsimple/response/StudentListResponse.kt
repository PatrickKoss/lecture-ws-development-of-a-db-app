package com.example.restsimple.response

import com.example.restsimple.model.Student
import javax.validation.constraints.NotNull

class StudentListResponse(students: List<Student?>?) {
    var students: @NotNull(message = "students is required") MutableList<Student?>? = students?.toMutableList()
}
