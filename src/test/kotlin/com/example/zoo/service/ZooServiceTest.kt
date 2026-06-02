package com.example.zoo.service

import com.example.zoo.datasource.ZooDataSource
import io.mockk.every
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import io.mockk.mockk
import io.mockk.verify
class ZooServiceTest {

    private val dataSource : ZooDataSource= mockk()

    private val zooService = ZooService(dataSource)
    @Test
    fun `should call its data source to retrieve data  `() {
        // given

        every { dataSource.retrieveAnimals() } returns  emptyList()

        // when
        val animals = zooService.getAnimals()

        // then
        verify(exactly = 1) {dataSource.retrieveAnimals()}

    }

}