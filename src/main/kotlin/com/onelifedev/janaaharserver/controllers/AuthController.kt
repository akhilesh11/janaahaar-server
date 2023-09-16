package com.onelifedev.janaaharserver.controllers

import com.onelifedev.janaaharserver.models.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/api/auth")
class AuthController {

    @PostMapping("/email/login")
    fun login(authObject: AuthObject) : ResponseEntity<User>?{

        return null; // TODO ResponseEntity.ok()
    }

    @PostMapping("/email/signup")
    fun signup(authObject: AuthObject) : ResponseEntity<User>?{

        return null// Todo Return User created
    }


}

data class AuthObject(val email : String?,val passHash: String?)