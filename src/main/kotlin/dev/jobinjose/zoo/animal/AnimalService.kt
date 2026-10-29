package dev.jobinjose.zoo.animal

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AnimalService(private val repository: AnimalRepository) {

    fun findAll(species: String? = null): List<AnimalResponse> {
        val animals = if (species.isNullOrBlank()) {
            repository.findAllByOrderByNameAsc()
        } else {
            repository.findAllBySpeciesIgnoreCaseOrderByNameAsc(species)
        }
        return animals.map { it.toResponse() }
    }

    fun findById(id: Long): AnimalResponse = load(id).toResponse()

    @Transactional
    fun create(request: AnimalRequest): AnimalResponse = repository.save(request.toEntity()).toResponse()

    @Transactional
    fun update(id: Long, request: AnimalRequest): AnimalResponse {
        val animal = load(id)
        animal.applyFrom(request)
        return repository.save(animal).toResponse()
    }

    @Transactional
    fun delete(id: Long) {
        if (!repository.existsById(id)) throw AnimalNotFoundException(id)
        repository.deleteById(id)
    }

    private fun load(id: Long): Animal = repository.findById(id).orElseThrow { AnimalNotFoundException(id) }
}
