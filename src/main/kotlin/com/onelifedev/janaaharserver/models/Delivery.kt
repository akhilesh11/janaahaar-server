package com.onelifedev.janaaharserver.models

import lombok.Getter
import lombok.Setter
import java.util.Calendar
import java.util.Date

enum class Status {NOT_FULFILLED,IN_PROGRESS,FULFILLED}

enum class PossibleDeliveryStatus {ACCEPTED,TIMED_OUT,REJECTED,PENDING_ACCEPTANCE}

@Getter
@Setter
data class Delivery(
    val id : String = "Delivery_${Calendar.getInstance().time.toInstant().toEpochMilli()}",
    val userId : String,
    val donationId : String,
    val spotId : String,
    val status : Status,
    val creationDate : Date = Calendar.getInstance().time,
    var lastUpdateDate : Date,
    ) {
    }

@Getter
@Setter
data class PossibleDelivery(
    val id : String = "PossibleDelivery_${Calendar.getInstance().time.toInstant().toEpochMilli()}",
    val donationId : String,
    val spotId : String,
    val distanceBetweenDeliveryAndDonation : Double,
    val distanceBetweenDonationAndSpot : Double,
    val possibleDeliveryStatus : PossibleDeliveryStatus,
    val creationDate : Date = Calendar.getInstance().time,
    var lastUpdateDate : Date,
) {
}



