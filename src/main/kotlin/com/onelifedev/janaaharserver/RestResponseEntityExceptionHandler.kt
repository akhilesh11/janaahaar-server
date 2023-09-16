package com.onelifedev.janaaharserver

import com.onelifedev.janaaharserver.exceptionHandlers.DuplicateIdException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [IllegalArgumentException::class, IllegalStateException::class,RuntimeException::class])
    protected fun handleConflict(
        ex: RuntimeException?, request: WebRequest?
    ): ResponseEntity<Any>? {
        val bodyOfResponse = "An internal error occurred and this has been reported. We should be up again soon!"
        if (ex != null) {
            println(ex.message)
        }

        return handleExceptionInternal(
            ex!!, bodyOfResponse,
            HttpHeaders(), HttpStatus.CONFLICT, request!!
        )
    }

    @ExceptionHandler(DuplicateIdException::class)
    protected fun handleDuplicateId(ex : RuntimeException?,request: WebRequest?) : ResponseEntity<Any>?{
        return ResponseEntity.badRequest().body("Duplicate to another row. Please keep ids unique.")
    }
}