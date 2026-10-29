package dev.jobinjose.zoo.common

import dev.jobinjose.zoo.animal.AnimalNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * Domain errors as RFC 9457 Problem Details. Validation and malformed-JSON errors are rendered
 * the same way by Spring because `spring.mvc.problemdetails.enabled=true`.
 */
@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(AnimalNotFoundException::class)
    fun handleNotFound(ex: AnimalNotFoundException): ProblemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.message ?: "Not found").apply {
            title = "Animal not found"
        }
}
