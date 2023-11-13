package com.onelifedev.janaaharserver.controllers

import com.onelifedev.janaaharserver.models.LatLng
import com.onelifedev.janaaharserver.models.Spot
import com.onelifedev.janaaharserver.models.SpotRequestBody
import com.onelifedev.janaaharserver.services.SpotService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Exception

@RestController
@RequestMapping("/api/spot")
class SpotController(val spotService: SpotService) {

    @GetMapping("/fetchSpotsAround")
    fun fetchSpotsAround(latLng: LatLng) : ResponseEntity<List<Spot>>{

        //Starting from the maximum proximity of the latitude and longitude
        for(resolution in 1..14) {
            val spotsAround = spotService.searchSpotsWithinRange(latLng,resolution)
            if(!spotsAround.isNullOrEmpty()){
                val header = HttpHeaders()
                header.set("Resolution",resolution.toString())
                return ResponseEntity<List<Spot>>(spotsAround,header,HttpStatus.ACCEPTED)
            }
        }

        //When no spots are found nearby return header with message.
        val header = HttpHeaders()
        header.set("Message","No spots found nearby.")
        return ResponseEntity<List<Spot>>(null,header,HttpStatus.NO_CONTENT)
    }

    @PostMapping("/createSpot")
    fun createSpot(@RequestBody spotRequestBody: SpotRequestBody) : ResponseEntity<Spot>{
        println(spotRequestBody)
        return try {
            val createdSpot = spotService.createSpot(spotRequestBody.latLng,spotRequestBody.numberOfRequiredPackets,spotRequestBody.additionalDescription)
            if(createdSpot!=null){
                ResponseEntity.ok(createdSpot)
            } else{
                val header = HttpHeaders()
                header.set("Message","Error in spot Service while creating spot.")
                ResponseEntity<Spot>(null,header,HttpStatus.NO_CONTENT)
            }
        }catch (e : Exception){
            val header = HttpHeaders()
            header.set("Message","Error while creating spot : " + e.message)
            ResponseEntity<Spot>(null,header,HttpStatus.NO_CONTENT)
        }
    }

    @GetMapping("/fetchSpotForUser")
    fun fetchSpotsWhereUserId(userId : String) : ResponseEntity<List<Spot>>{
        return ResponseEntity.ok(spotService.fetchSpotsForUserWithId(userId))
    }

    @DeleteMapping("deleteSpot")
    fun deleteSpotWithId(spotId : String){
        spotService.deleteSpot(spotId)
    }

}

