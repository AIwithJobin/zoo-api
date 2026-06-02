package com.example.zoo.model

import jakarta.persistence.*

@Entity
@Table(name = "animals")
data class Animal(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    val species: String,
    val age: Int,
    val gender: String,
    val habitat: String,
    val enclosureId: String,
    val diet: String,
    val weightKg: Double,
    val heightCm: Double,
    val healthStatus: String,
    val endangeredStatus: String,
    val arrivalDate: String,
    val feedingTime: String,
    val caretakerName: String
)


// fun getVariableName() : VariableName
// fun setVariableName() : VariableName

// in kotlin it already has the logic for getter and setter -- for val only setter
// if var setter and getter logic is inbuilt in it