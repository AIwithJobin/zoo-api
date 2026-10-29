package dev.jobinjose.zoo.animal

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PastOrPresent
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.time.LocalTime

/** Payload for creating or replacing an animal. Validated before it reaches the service. */
data class AnimalRequest(
    @field:NotBlank @field:Size(max = 60) val name: String,
    @field:NotBlank @field:Size(max = 60) val species: String,
    @field:PositiveOrZero @field:Max(200) val age: Int,
    val sex: Sex,
    @field:NotBlank val habitat: String,
    @field:NotBlank val enclosureId: String,
    val diet: Diet,
    @field:Positive val weightKg: Double,
    @field:Positive val heightCm: Double,
    val healthStatus: HealthStatus,
    val conservationStatus: ConservationStatus,
    @field:PastOrPresent val arrivalDate: LocalDate,
    val feedingTime: LocalTime,
    @field:NotBlank val caretaker: String,
)

data class AnimalResponse(
    val id: Long,
    val name: String,
    val species: String,
    val age: Int,
    val sex: Sex,
    val habitat: String,
    val enclosureId: String,
    val diet: Diet,
    val weightKg: Double,
    val heightCm: Double,
    val healthStatus: HealthStatus,
    val conservationStatus: ConservationStatus,
    val arrivalDate: LocalDate,
    val feedingTime: LocalTime,
    val caretaker: String,
)

fun AnimalRequest.toEntity() = Animal(
    name = name, species = species, age = age, sex = sex, habitat = habitat, enclosureId = enclosureId,
    diet = diet, weightKg = weightKg, heightCm = heightCm, healthStatus = healthStatus,
    conservationStatus = conservationStatus, arrivalDate = arrivalDate, feedingTime = feedingTime,
    caretaker = caretaker,
)

fun Animal.applyFrom(request: AnimalRequest) {
    name = request.name
    species = request.species
    age = request.age
    sex = request.sex
    habitat = request.habitat
    enclosureId = request.enclosureId
    diet = request.diet
    weightKg = request.weightKg
    heightCm = request.heightCm
    healthStatus = request.healthStatus
    conservationStatus = request.conservationStatus
    arrivalDate = request.arrivalDate
    feedingTime = request.feedingTime
    caretaker = request.caretaker
}

fun Animal.toResponse() = AnimalResponse(
    id = requireNotNull(id) { "Animal must be persisted before it is returned" },
    name = name, species = species, age = age, sex = sex, habitat = habitat, enclosureId = enclosureId,
    diet = diet, weightKg = weightKg, heightCm = heightCm, healthStatus = healthStatus,
    conservationStatus = conservationStatus, arrivalDate = arrivalDate, feedingTime = feedingTime,
    caretaker = caretaker,
)
