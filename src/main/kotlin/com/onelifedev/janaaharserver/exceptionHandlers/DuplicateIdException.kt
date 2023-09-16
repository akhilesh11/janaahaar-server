package com.onelifedev.janaaharserver.exceptionHandlers

class DuplicateIdException(message: String = "The id field must contain a unique value.") : RuntimeException() {
    private val serialVersionUID = 1L

}