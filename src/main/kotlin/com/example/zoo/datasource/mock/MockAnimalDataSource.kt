package com.example.zoo.datasource.mock

import com.example.zoo.datasource.ZooDataSource
import com.example.zoo.model.Animal
import org.springframework.stereotype.Repository

// repository stands for mentioning this class is responsible for retrieving and storing data and other such data functionalities
@Repository
class MockAnimalDataSource : ZooDataSource {

    val Animals = mutableListOf(
        Animal(1, "Leo", "Lion", 8, "Male", "Savannah Zone", "ENC-101", "Carnivore", 190.5, 120.0, "Healthy", "Vulnerable", "2019-03-15", "09:00", "John Smith"),
        Animal(2, "Nala", "Lion", 6, "Female", "Savannah Zone", "ENC-101", "Carnivore", 135.0, 110.0, "Healthy", "Vulnerable", "2020-07-21", "09:00", "John Smith"),
        Animal(3, "Dumbo", "Elephant", 15, "Male", "Elephant Park", "ENC-201", "Herbivore", 5200.0, 320.0, "Healthy", "Endangered", "2015-05-10", "08:00", "Sarah Wilson"),
        Animal(4, "Ella", "Elephant", 12, "Female", "Elephant Park", "ENC-201", "Herbivore", 4300.0, 290.0, "Healthy", "Endangered", "2017-11-05", "08:00", "Sarah Wilson"),
        Animal(5, "Stripes", "Zebra", 5, "Male", "Grassland Habitat", "ENC-301", "Herbivore", 380.0, 145.0, "Healthy", "Least Concern", "2021-04-19", "10:00", "Michael Brown"),
        Animal(6, "Zara", "Zebra", 4, "Female", "Grassland Habitat", "ENC-301", "Herbivore", 360.0, 140.0, "Healthy", "Least Concern", "2022-01-11", "10:00", "Michael Brown"),
        Animal(7, "Melman", "Giraffe", 9, "Male", "Savannah Zone", "ENC-102", "Herbivore", 900.0, 520.0, "Under Observation", "Vulnerable", "2018-09-30", "11:00", "Emma Davis"),
        Animal(8, "Gemma", "Giraffe", 7, "Female", "Savannah Zone", "ENC-102", "Herbivore", 820.0, 500.0, "Healthy", "Vulnerable", "2020-02-14", "11:00", "Emma Davis"),
        Animal(9, "Koko", "Gorilla", 11, "Male", "Rainforest House", "ENC-401", "Omnivore", 180.0, 175.0, "Healthy", "Critically Endangered", "2016-06-23", "12:00", "David Lee"),
        Animal(10, "Luna", "Gorilla", 10, "Female", "Rainforest House", "ENC-401", "Omnivore", 145.0, 165.0, "Healthy", "Critically Endangered", "2017-08-17", "12:00", "David Lee"),
        Animal(11, "Pingo", "Penguin", 3, "Male", "Arctic World", "ENC-501", "Carnivore", 28.0, 70.0, "Healthy", "Near Threatened", "2023-01-08", "13:00", "Olivia Taylor"),
        Animal(12, "Penny", "Penguin", 2, "Female", "Arctic World", "ENC-501", "Carnivore", 25.0, 65.0, "Healthy", "Near Threatened", "2023-06-15", "13:00", "Olivia Taylor"),
        Animal(13, "Rocky", "Rhinoceros", 14, "Male", "Rhino Reserve", "ENC-601", "Herbivore", 2300.0, 185.0, "Healthy", "Critically Endangered", "2014-12-04", "08:30", "Sophia Martin"),
        Animal(14, "Ruby", "Rhinoceros", 13, "Female", "Rhino Reserve", "ENC-601", "Herbivore", 2100.0, 175.0, "Healthy", "Critically Endangered", "2016-10-22", "08:30", "Sophia Martin"),
        Animal(15, "Balu", "Bear", 10, "Male", "Mountain Habitat", "ENC-701", "Omnivore", 450.0, 190.0, "Recovering", "Least Concern", "2018-04-27", "14:00", "Robert White"),
        Animal(16, "Mia", "Tiger", 5, "Female", "Big Cat Zone", "ENC-801", "Carnivore", 140.0, 105.0, "Healthy", "Endangered", "2021-05-13", "15:00", "John Smith"),
        Animal(17, "Rajah", "Tiger", 7, "Male", "Big Cat Zone", "ENC-801", "Carnivore", 220.0, 115.0, "Healthy", "Endangered", "2019-08-09", "15:00", "John Smith"),
        Animal(18, "Chirpy", "Parrot", 2, "Female", "Bird Sanctuary", "ENC-901", "Herbivore", 1.2, 30.0, "Healthy", "Least Concern", "2024-01-03", "09:30", "Anna Green"),
        Animal(19, "Sky", "Eagle", 6, "Male", "Bird Sanctuary", "ENC-902", "Carnivore", 6.5, 85.0, "Healthy", "Least Concern", "2020-11-16", "09:30", "Anna Green"),
        Animal(20, "Snap", "Crocodile", 18, "Male", "Reptile House", "ENC-1001", "Carnivore", 700.0, 450.0, "Under Observation", "Least Concern", "2012-07-01", "16:00", "Chris Walker")
    )
    override fun retrieveAnimals(): Collection<Animal> {
        return Animals

    }

    override fun retrieveAnimal(name: String): Animal {
        return Animals.firstOrNull(){it.name == name}
            ?:throw NoSuchElementException("Could not find animal with name $name")
    }

    override fun createAnimal(animal: Animal): Animal {
        if (Animals.any{ it.id == animal.id }) {
            throw IllegalArgumentException("Animal with id ${animal.id} already exists")
        }else{
            Animals.add(animal)
        }

        return animal
    }

    override fun removeAnimal(animal: Animal): Animal {
        if (Animals.none { it.id == animal.id }) {
            throw NoSuchElementException(
                "Animal with id ${animal.id} does not exist"
            )
        }

        Animals.removeIf { it.id == animal.id }

        return animal
    }

    override fun updateAnimal(animal: Animal): Animal {
        if (Animals.none { it.id == animal.id }) {
            throw NoSuchElementException("Could not find animal with id ${animal.id}")
        }else{
            Animals.replaceAll {
                if (it.id == animal.id) animal else it
            }
            return animal

        }









    }



}

