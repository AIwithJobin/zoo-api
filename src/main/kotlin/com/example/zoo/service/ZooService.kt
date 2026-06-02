package com.example.zoo.service

import com.example.zoo.datasource.ZooDataSource
import com.example.zoo.model.Animal
import org.springframework.stereotype.Service

@Service
class ZooService(private val dataSource: ZooDataSource) {
    fun getAnimals(): Collection<Animal> {
        return dataSource.retrieveAnimals()
    }

    fun getAnimal(name: String): Animal {
        return dataSource.retrieveAnimal(name)
    }

    fun addAnimal(animal: Animal): Animal = dataSource.createAnimal(animal)

    fun patchAnimal(animal: Animal):Animal = dataSource.updateAnimal(animal)

    fun deleteAnimal(animal: Animal): Animal = dataSource.removeAnimal(animal)
}