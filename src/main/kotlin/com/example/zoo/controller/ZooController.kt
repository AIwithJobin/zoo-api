package com.example.zoo.controller

import com.example.zoo.model.Animal
import com.example.zoo.service.ZooService
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/animals")
class ZooController(private val service: ZooService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun badRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    fun getAnimals(): Collection<Animal> = service.getAnimals()

    @GetMapping("/{name}")
    fun getAnimal(@PathVariable name : String )= service.getAnimal(name)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addAnimal(@RequestBody animal : Animal) = service.addAnimal(animal)

    @PatchMapping
    fun patchAnimal(@RequestBody animal : Animal) = service.patchAnimal(animal)

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAnimal(@RequestBody animal : Animal) = service.deleteAnimal(animal)
}