package com.example.restsimple.model

import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "student")
class Student {
    @Column(name = "id", nullable = false)
    var id: @NotNull(message = "id is required") String? = null

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mnr", nullable = false)
    var mnr: String? = null

    @Column(name = "name", nullable = false)
    var name: @Size(
        max = 200,
        message = "Name must not exceed 200 characters"
    ) @NotNull(message = "name is required") String? = null

    @Column(name = "last_name", nullable = false)
    var lastName: @NotNull(message = "lastName is required") String? = null

    @Column(name = "created_on", nullable = false)
    var createdOn: @NotNull(message = "created is required") LocalDateTime? = null

    constructor(id: String?, name: String?, lastName: String?, createdOn: LocalDateTime?) {
        this.id = id
        this.name = name
        this.lastName = lastName
        this.createdOn = createdOn
    }

    constructor()
}
