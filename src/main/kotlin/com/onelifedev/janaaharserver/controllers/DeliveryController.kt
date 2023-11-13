package com.onelifedev.janaaharserver.controllers

import com.onelifedev.janaaharserver.models.*
import com.onelifedev.janaaharserver.repositories.DeliveryRepository
import com.onelifedev.janaaharserver.repositories.PossibleDeliveryRepository
import com.onelifedev.janaaharserver.services.DeliveryService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/delivery")
class DeliveryController(val deliveryService: DeliveryService,val deliveryRepository : DeliveryRepository,val possibleDeliveryRepository: PossibleDeliveryRepository) {

    @PostMapping("/fetchDeliveriesAround")
    fun getDeliveriesAround(@RequestBody latLng : LatLng) : ResponseEntity<List<PossibleDelivery>> {
        val deliveriesAround = deliveryService.getDeliveriesAround(latLng)
        if(deliveriesAround.isNullOrEmpty()) {
            val header = HttpHeaders()
            header.set("Message", "No matching spots and donations found around.")
            return ResponseEntity<List<PossibleDelivery>>(null, header, HttpStatus.NO_CONTENT)
        }
        possibleDeliveryRepository.saveAll(deliveriesAround)
        return ResponseEntity.ok(deliveriesAround)
    }

    @PostMapping("/acceptPossibleDelivery")
    fun acceptPossibleDelivery(userId : String,possibleDeliveryId : String) : ResponseEntity<Delivery> {
        val possibleDelivery = fetchPossibleDelivery(possibleDeliveryId)

        if(possibleDelivery != null) {
            val delivery = Delivery(
                userId = userId,
                donationId = possibleDelivery.donationId,
                spotId = possibleDelivery.spotId,
                status = Status.IN_PROGRESS,
                lastUpdateDate = Calendar.getInstance().time
            )

            deliveryRepository.save(delivery)
            return ResponseEntity.ok(delivery)
        }
        else{
            val header = HttpHeaders()
            header.set("Message","No delivery found for the possible delivery.")
            return ResponseEntity(null,header,HttpStatus.NO_CONTENT)
        }
    }

    @PostMapping("/fetchPossibleDelivery")
    fun fetchPossibleDelivery(possibleDeliveryId : String) : PossibleDelivery?{
        return possibleDeliveryRepository.findById(possibleDeliveryId).orElse(null)
    }
}