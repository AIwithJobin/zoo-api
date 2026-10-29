package dev.jobinjose.zoo.animal

class AnimalNotFoundException(id: Long) : RuntimeException("Animal with id $id was not found")
