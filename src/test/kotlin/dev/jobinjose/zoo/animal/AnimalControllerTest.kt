package dev.jobinjose.zoo.animal

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import java.time.LocalDate
import java.time.LocalTime

/** End-to-end tests through the HTTP layer against an in-memory H2 database. */
@SpringBootTest
@AutoConfigureMockMvc
class AnimalControllerTest {

    @Autowired lateinit var mockMvc: MockMvc

    @Autowired lateinit var repository: AnimalRepository

    private var leoId: Long = 0

    @BeforeEach
    fun seed() {
        repository.deleteAll()
        leoId = repository.save(
            Animal(
                name = "Leo", species = "Lion", age = 8, sex = Sex.MALE, habitat = "Savannah Zone",
                enclosureId = "ENC-101", diet = Diet.CARNIVORE, weightKg = 190.5, heightCm = 120.0,
                healthStatus = HealthStatus.HEALTHY, conservationStatus = ConservationStatus.VULNERABLE,
                arrivalDate = LocalDate.of(2019, 3, 15), feedingTime = LocalTime.of(9, 0), caretaker = "John Smith",
            ),
        ).id!!
    }

    private fun body(name: String = "Oscar", age: Int = 12) = """
        {
          "name": "$name", "species": "Orangutan", "age": $age, "sex": "MALE",
          "habitat": "Rainforest House", "enclosureId": "ENC-402", "diet": "OMNIVORE",
          "weightKg": 85.0, "heightCm": 140.0, "healthStatus": "HEALTHY",
          "conservationStatus": "CRITICALLY_ENDANGERED", "arrivalDate": "2017-03-18",
          "feedingTime": "12:30", "caretaker": "David Lee"
        }
    """.trimIndent()

    @Nested
    inner class Read {
        @Test
        fun `lists all animals`() {
            mockMvc.get("/api/v1/animals").andExpect {
                status { isOk() }
                jsonPath("$[0].name") { value("Leo") }
            }
        }

        @Test
        fun `filters by species`() {
            mockMvc.get("/api/v1/animals?species=tiger").andExpect {
                status { isOk() }
                jsonPath("$.length()") { value(0) }
            }
        }

        @Test
        fun `returns one animal by id`() {
            mockMvc.get("/api/v1/animals/$leoId").andExpect {
                status { isOk() }
                jsonPath("$.species") { value("Lion") }
            }
        }

        @Test
        fun `returns a problem detail for an unknown id`() {
            mockMvc.get("/api/v1/animals/999999").andExpect {
                status { isNotFound() }
                content { contentType(MediaType.APPLICATION_PROBLEM_JSON) }
                jsonPath("$.title") { value("Animal not found") }
            }
        }
    }

    @Nested
    inner class Write {
        @Test
        fun `creates an animal and returns its location`() {
            mockMvc.post("/api/v1/animals") {
                contentType = MediaType.APPLICATION_JSON
                content = body()
            }.andExpect {
                status { isCreated() }
                header { exists("Location") }
                jsonPath("$.name") { value("Oscar") }
            }
        }

        @Test
        fun `rejects invalid input with 400`() {
            mockMvc.post("/api/v1/animals") {
                contentType = MediaType.APPLICATION_JSON
                content = body(name = "", age = -1)
            }.andExpect {
                status { isBadRequest() }
            }
        }

        @Test
        fun `updates an existing animal`() {
            mockMvc.put("/api/v1/animals/$leoId") {
                contentType = MediaType.APPLICATION_JSON
                content = body(name = "Leo", age = 9)
            }.andExpect {
                status { isOk() }
                jsonPath("$.age") { value(9) }
            }
        }

        @Test
        fun `deletes an animal`() {
            mockMvc.delete("/api/v1/animals/$leoId").andExpect { status { isNoContent() } }
            mockMvc.get("/api/v1/animals/$leoId").andExpect { status { isNotFound() } }
        }

        @Test
        fun `delete of an unknown id returns 404`() {
            mockMvc.delete("/api/v1/animals/999999").andExpect { status { isNotFound() } }
        }
    }
}
