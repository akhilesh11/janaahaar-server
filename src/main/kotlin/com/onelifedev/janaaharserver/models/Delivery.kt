package com.onelifedev.janaaharserver.models

import lombok.Getter
import lombok.Setter
import java.util.Calendar
import java.util.Date

enum class Status {NOT_FULFILLED,IN_PROGRESS,FULFILLED}

@Getter
@Setter
data class Delivery(
    val id : String,
    val userId : String,
    val donationId : String,
    val spotId : String,
    val status : Status,
    val creationDate : Date,
    var lastUpdateDate : Date,
    ) {
    }


