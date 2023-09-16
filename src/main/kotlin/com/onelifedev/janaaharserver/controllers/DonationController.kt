package com.onelifedev.janaaharserver.controllers

import com.onelifedev.janaaharserver.models.Donation
import com.onelifedev.janaaharserver.models.DonationRequestBody
import com.onelifedev.janaaharserver.services.DonationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/donations")
class DonationController(val donationService: DonationService) {

    @GetMapping("/deleteDonation")
    fun deleteDonation(donationId : String) : ResponseEntity<String> {
        val response = "Deleted"

        //TODO("Add logic to delete donation")

        return if(response!=null) ResponseEntity.ok(response) else ResponseEntity.notFound().build()
    }

    @PostMapping("/createDonation")
    fun createDonation(@RequestBody donationRequestBody: DonationRequestBody) : ResponseEntity<Donation> {
        val createdDonation = donationService.createDonation(donationRequestBody.availablePackets,donationRequestBody.latLng,donationRequestBody.bestBeforeDate)
        return ResponseEntity.ok(createdDonation)
    }


}