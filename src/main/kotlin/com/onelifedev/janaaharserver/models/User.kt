package com.onelifedev.janaaharserver.models

import lombok.Getter
import lombok.Setter
import java.util.Date

enum class UserRole {SPOTTER,DELIVERY,DONOR,ADMIN}

@Getter
@Setter
data class User(
    val id             : String,
    val firstName      : String,
    val lastName       : String,
    val email          : String,
    val phone          : String,
    val role           : UserRole,
    val verified       : Boolean,
    val creationDate   : Date,
    val lastUpdateDate : Date) {
}