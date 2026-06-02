package com.example.zoo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ZooApplication

fun main(args: Array<String>) {
	runApplication<ZooApplication>(*args)
}
