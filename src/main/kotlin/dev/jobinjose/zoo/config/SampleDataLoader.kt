package dev.jobinjose.zoo.config

import dev.jobinjose.zoo.animal.Animal
import dev.jobinjose.zoo.animal.AnimalRepository
import dev.jobinjose.zoo.animal.ConservationStatus
import dev.jobinjose.zoo.animal.Diet
import dev.jobinjose.zoo.animal.HealthStatus
import dev.jobinjose.zoo.animal.Sex
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalTime

/** Seeds a small demo zoo on first start so the API returns data straight away. */
@Component
class SampleDataLoader(
    private val repository: AnimalRepository,
    @Value("\${zoo.seed-data:true}") private val enabled: Boolean,
) : CommandLineRunner {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun run(vararg args: String) {
        if (!enabled || repository.count() > 0) return
        repository.saveAll(sampleAnimals())
        log.info("Seeded {} sample animals", repository.count())
    }

    private fun sampleAnimals() = listOf(
        animal("Leo", "Lion", 8, Sex.MALE, "Savannah Zone", "ENC-101", Diet.CARNIVORE, 190.5, 120.0, ConservationStatus.VULNERABLE, "2019-03-15", "09:00", "John Smith"),
        animal("Nala", "Lion", 6, Sex.FEMALE, "Savannah Zone", "ENC-101", Diet.CARNIVORE, 135.0, 110.0, ConservationStatus.VULNERABLE, "2020-07-21", "09:00", "John Smith"),
        animal("Dumbo", "Elephant", 15, Sex.MALE, "Elephant Park", "ENC-201", Diet.HERBIVORE, 5200.0, 320.0, ConservationStatus.ENDANGERED, "2015-05-10", "08:00", "Sarah Wilson"),
        animal("Melman", "Giraffe", 9, Sex.MALE, "Savannah Zone", "ENC-102", Diet.HERBIVORE, 900.0, 520.0, ConservationStatus.VULNERABLE, "2018-09-30", "11:00", "Emma Davis", HealthStatus.UNDER_OBSERVATION),
        animal("Koko", "Gorilla", 11, Sex.MALE, "Rainforest House", "ENC-401", Diet.OMNIVORE, 180.0, 175.0, ConservationStatus.CRITICALLY_ENDANGERED, "2016-06-23", "12:00", "David Lee"),
        animal("Pingo", "Penguin", 3, Sex.MALE, "Arctic World", "ENC-501", Diet.CARNIVORE, 28.0, 70.0, ConservationStatus.NEAR_THREATENED, "2023-01-08", "13:00", "Olivia Taylor"),
        animal("Mia", "Tiger", 5, Sex.FEMALE, "Big Cat Zone", "ENC-801", Diet.CARNIVORE, 140.0, 105.0, ConservationStatus.ENDANGERED, "2021-05-13", "15:00", "John Smith"),
        animal("Balu", "Bear", 10, Sex.MALE, "Mountain Habitat", "ENC-701", Diet.OMNIVORE, 450.0, 190.0, ConservationStatus.LEAST_CONCERN, "2018-04-27", "14:00", "Robert White", HealthStatus.RECOVERING),
    )

    @Suppress("LongParameterList")
    private fun animal(
        name: String, species: String, age: Int, sex: Sex, habitat: String, enclosure: String, diet: Diet,
        weight: Double, height: Double, status: ConservationStatus, arrival: String, feeding: String,
        caretaker: String, health: HealthStatus = HealthStatus.HEALTHY,
    ) = Animal(
        name = name, species = species, age = age, sex = sex, habitat = habitat, enclosureId = enclosure,
        diet = diet, weightKg = weight, heightCm = height, healthStatus = health, conservationStatus = status,
        arrivalDate = LocalDate.parse(arrival), feedingTime = LocalTime.parse(feeding), caretaker = caretaker,
    )
}
