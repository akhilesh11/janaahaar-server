package com.onelifedev.janaaharserver.models

import lombok.Getter
import lombok.Setter
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Getter
@Setter
@Document("spots")
data class Spot(
    @Id
    val id : String,
    val userId : String,
    var additionalDescription : String,
    val coordinatesOfLocation : LatLng,
    val hexGridAddressAtEachResolution : List<HexMap>,
    val numberOfPacketsNeeded : Int,
    val verified : Boolean,
    val status : Status,
    val creationDate : Date,
    var lastUpdateDate : Date
){

}

@Getter
@Setter
data class SpotRequestBody(val latLng: LatLng, val numberOfRequiredPackets : Int, val additionalDescription : String)

@Getter
@Setter
data class HexMap(val resolution : Int,val hexAddress : String)