package com.onelifedev.janaaharserver.controllers

import com.onelifedev.janaaharserver.models.Delivery
import com.onelifedev.janaaharserver.models.LatLng
import com.onelifedev.janaaharserver.services.DeliveryService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/delivery")
class DeliveryController(val deliveryService: DeliveryService) {

    @PostMapping("/fetchDeliveriesAround")
    fun getDeliveriesAround(@RequestBody latLng : LatLng) : List<Delivery>{
        return deliveryService.getDeliveriesAround(latLng)
    }
}