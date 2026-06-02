package com.example.zoo.datasource.mock

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat


class MockAnimalDataSourceTest {

     private val animalDataSource = MockAnimalDataSource()
    @Test
    fun `should provide a collection of animals `() {
        // given
        
    
        // when

        val animals = animalDataSource.retrieveAnimals()
    
        // then
        assertThat(animals).isNotEmpty
        
    }

    @Test
    fun `should provide some mock data of animals  `() {
        // given


        // when

        val animals = animalDataSource.retrieveAnimals()
        // then
        assertThat(animals).allMatch{ it.name.isNotEmpty() }

    }

}