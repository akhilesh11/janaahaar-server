package com.onelifedev.janaaharserver.services

import com.onelifedev.janaaharserver.models.Delivery
import com.onelifedev.janaaharserver.models.LatLng
import org.springframework.stereotype.Service

@Service
class DeliveryService(
    val donationService : DonationService,
    val spotService : SpotService
) {

    fun getDeliveriesAround(latLng : LatLng) : List<Delivery>{

        for(resolution in 15 downTo 4) {
            val spots = spotService.searchSpotsWithinRange(latLng, resolution)
            println("Spots Around with resolution $resolution : $spots")
            val donations = donationService.searchDonationsWithinRange(latLng, resolution)

            //TODO("Spot Donation Matching Logic")
        }
        return listOf()
    }

    private fun matchSpotToDonation(){

    }

    fun acceptDelivery(deliveryId : String) : String{
        return ""
    }

    fun updateDelivery(deliveryId : String){
        return
    }

}