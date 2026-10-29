package dev.jobinjose.zoo.animal

import org.springframework.data.jpa.repository.JpaRepository

interface AnimalRepository : JpaRepository<Animal, Long> {
    fun findAllBySpeciesIgnoreCaseOrderByNameAsc(species: String): List<Animal>

    fun findAllByOrderByNameAsc(): List<Animal>
}
