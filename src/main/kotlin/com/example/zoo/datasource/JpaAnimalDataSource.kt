package com.example.zoo.datasource

import com.example.zoo.model.Animal
import com.example.zoo.repository.AnimalRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Repository
@Primary
class JpaAnimalDataSource(
    private val animalRepository: AnimalRepository
) : ZooDataSource {

    override fun retrieveAnimals(): Collection<Animal> {
        return animalRepository.findAll()
    }

    override fun retrieveAnimal(name: String): Animal {
        return animalRepository.findAll()
            .firstOrNull { it.name == name }
            ?: throw NoSuchElementException(
                "Could not find animal with name $name"
            )
    }

    override fun createAnimal(animal: Animal): Animal {
        return animalRepository.save(animal)
    }

    override fun updateAnimal(animal: Animal): Animal {
        return animalRepository.save(animal)
    }

    override fun removeAnimal(animal: Animal): Animal {
        animalRepository.delete(animal)
        return animal
    }
}