package com.example.restsimple.exception

import com.example.restsimple.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@ControllerAdvice
@RestController
class GlobalExceptionHandler {
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        MethodArgumentNotValidException::class
    )
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): Map<String, String> {
        val message = ex.bindingResult
            .allErrors.stream()
            .map { obj: ObjectError -> obj.defaultMessage }
            .collect(Collectors.joining(", "))
        val responseBody: MutableMap<String, String> = HashMap()
        responseBody["message"] = message
        return responseBody
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(ex.message)
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }
}
