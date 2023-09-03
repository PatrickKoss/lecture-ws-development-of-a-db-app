package com.example.restsimple.model

import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "student")
class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mnr", nullable = false)
    var mnr: String? = null

    constructor()
}
