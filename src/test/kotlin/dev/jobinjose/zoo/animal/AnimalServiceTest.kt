package dev.jobinjose.zoo.animal

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalTime
import java.util.Optional

class AnimalServiceTest {

    private val repository: AnimalRepository = mockk()
    private val service = AnimalService(repository)

    private val request = AnimalRequest(
        name = "Leo", species = "Lion", age = 8, sex = Sex.MALE, habitat = "Savannah Zone", enclosureId = "ENC-101",
        diet = Diet.CARNIVORE, weightKg = 190.5, heightCm = 120.0, healthStatus = HealthStatus.HEALTHY,
        conservationStatus = ConservationStatus.VULNERABLE, arrivalDate = LocalDate.of(2019, 3, 15),
        feedingTime = LocalTime.of(9, 0), caretaker = "John Smith",
    )

    @Test
    fun `filters by species when a species is given`() {
        every { repository.findAllBySpeciesIgnoreCaseOrderByNameAsc("lion") } returns listOf(request.toEntity().apply { id = 1 })

        val result = service.findAll("lion")

        assertThat(result).extracting("name").containsExactly("Leo")
        verify(exactly = 0) { repository.findAllByOrderByNameAsc() }
    }

    @Test
    fun `throws when the animal does not exist`() {
        every { repository.findById(99) } returns Optional.empty()

        assertThrows<AnimalNotFoundException> { service.findById(99) }
    }

    @Test
    fun `update replaces all fields of the existing animal`() {
        val existing = request.toEntity().apply { id = 1 }
        val saved = slot<Animal>()
        every { repository.findById(1) } returns Optional.of(existing)
        every { repository.save(capture(saved)) } answers { saved.captured }

        val result = service.update(1, request.copy(age = 9, healthStatus = HealthStatus.RECOVERING))

        assertThat(result.age).isEqualTo(9)
        assertThat(result.healthStatus).isEqualTo(HealthStatus.RECOVERING)
        assertThat(saved.captured.id).isEqualTo(1L)
    }

    @Test
    fun `delete fails for an unknown id`() {
        every { repository.existsById(42) } returns false

        assertThrows<AnimalNotFoundException> { service.delete(42) }
        verify(exactly = 0) { repository.deleteById(any()) }
    }
}
