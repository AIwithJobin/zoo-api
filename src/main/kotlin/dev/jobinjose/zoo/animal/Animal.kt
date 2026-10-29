package dev.jobinjose.zoo.animal

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalTime

enum class Sex { MALE, FEMALE }

enum class Diet { CARNIVORE, HERBIVORE, OMNIVORE }

enum class HealthStatus { HEALTHY, UNDER_OBSERVATION, RECOVERING, SICK }

/** IUCN Red List categories. */
enum class ConservationStatus { LEAST_CONCERN, NEAR_THREATENED, VULNERABLE, ENDANGERED, CRITICALLY_ENDANGERED }

@Entity
@Table(name = "animals")
class Animal(
    @Column(nullable = false) var name: String,
    @Column(nullable = false) var species: String,
    var age: Int,
    @Enumerated(EnumType.STRING) var sex: Sex,
    @Column(nullable = false) var habitat: String,
    @Column(nullable = false) var enclosureId: String,
    @Enumerated(EnumType.STRING) var diet: Diet,
    var weightKg: Double,
    var heightCm: Double,
    @Enumerated(EnumType.STRING) var healthStatus: HealthStatus,
    @Enumerated(EnumType.STRING) var conservationStatus: ConservationStatus,
    var arrivalDate: LocalDate,
    var feedingTime: LocalTime,
    @Column(nullable = false) var caretaker: String,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
)
