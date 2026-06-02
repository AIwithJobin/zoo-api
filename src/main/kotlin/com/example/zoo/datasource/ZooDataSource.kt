package com.example.zoo.datasource

import com.example.zoo.model.Animal

interface ZooDataSource {
    fun retrieveAnimals() : Collection<Animal>
    fun retrieveAnimal(name: String): Animal
    fun createAnimal(animal: com.example.zoo.model.Animal): com.example.zoo.model.Animal
    fun updateAnimal(animal: Animal): Animal
    fun removeAnimal(animal: Animal): Animal
}