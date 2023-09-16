package com.onelifedev.janaaharserver.controllers

import com.onelifedev.janaaharserver.models.User
import jakarta.validation.constraints.Email
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/api/user")
class UserController {

    @PostMapping("/createNewUser")
    fun createUser(userObject: UserObject) : ResponseEntity<User>?{
        return null
    }
}

data class UserObject(val name : String, val email: Email,val phone : String){}