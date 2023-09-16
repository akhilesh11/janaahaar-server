package com.onelifedev.janaaharserver.models

import lombok.Getter
import lombok.Setter
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Getter
@Setter
data class LatLng(var latitude : Double,var longitude : Double)

@Getter
@Setter
@Document("donations")
data class Donation(
    @Id
    val id : String,
    val userId : String,
    val coordinatesOfLocation : LatLng,
    val hexGridAddressAtEachResolution : List<HexMap>,
    val numberOfPackets : Int,
    val bestBeforeDate : Date,
    val creationDate : Date,
    var lastUpdateDate : Date)

@Getter
@Setter
data class DonationRequestBody(val latLng: LatLng,val availablePackets : Int,val bestBeforeDate: Date){

}
