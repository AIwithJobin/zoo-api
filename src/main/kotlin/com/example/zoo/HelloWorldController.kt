package com.example.zoo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/zoo")
class HelloWorldController {

        @GetMapping
        fun hello() : String="Welcome to the hannover zoo"

    }
