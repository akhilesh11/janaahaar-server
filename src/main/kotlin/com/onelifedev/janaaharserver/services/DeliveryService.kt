package com.onelifedev.janaaharserver.services

import com.onelifedev.janaaharserver.models.*
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.HashMap

@Service
class DeliveryService(
    val donationService : DonationService,
    val spotService : SpotService
) {

    fun getDeliveriesAround(deliveryCurrentLatLng : LatLng) : List<PossibleDelivery>?{

        val possibleDeliveries : MutableList<PossibleDelivery> = mutableListOf()

        for(resolution in 15 downTo 4) {
            val spots = spotService.searchSpotsWithinRange(deliveryCurrentLatLng, resolution)
            println("Spots Around with resolution $resolution : $spots")
            val donations = donationService.searchDonationsWithinRange(deliveryCurrentLatLng, resolution)
            println("Donations Around with resolution $resolution : $donations")

            if(!donations.isNullOrEmpty() && !spots.isNullOrEmpty())
            {
                possibleDeliveries.addAll(matchSpotToDonation(deliveryCurrentLatLng,spots,donations))
            }
        }
        return possibleDeliveries
    }

    private fun matchSpotToDonation(deliveryCurrentLatLng : LatLng, spots : List<Spot>, donations : List<Donation>) : List<PossibleDelivery>{
        val spotHashMap : HashMap<Spot,Double> = hashMapOf()
        val donationHashMap : HashMap<Donation,Double> = hashMapOf()

        val possibleDeliveries : MutableList<PossibleDelivery> = mutableListOf()

        for(donation in donations){
            val distanceBetweenDeliveryAndDonation = findDistanceBetweenCoordinates(deliveryCurrentLatLng,donation.coordinatesOfLocation)
            val spotsAround : MutableList<Spot> = mutableListOf()
            //Starting from the maximum proximity of the latitude and longitude
            for(resolution in 1..14) {
                val spotsAroundAtCurrentResolution = spotService.searchSpotsWithinRange(donation.coordinatesOfLocation,resolution)
                if(!spotsAroundAtCurrentResolution.isNullOrEmpty()){
                    for(spotAtCurrentLocation in spotsAroundAtCurrentResolution){

                        //Valid only if the donation place has the number of packets available.
                        if(spotAtCurrentLocation.numberOfPacketsNeeded < donation.numberOfPackets) {
                            var possibleDelivery = PossibleDelivery(
                                donationId = donation.id,
                                spotId = spotAtCurrentLocation.id,
                                distanceBetweenDeliveryAndDonation = distanceBetweenDeliveryAndDonation,
                                distanceBetweenDonationAndSpot = findDistanceBetweenCoordinates(
                                    spotAtCurrentLocation.coordinatesOfLocation,
                                    donation.coordinatesOfLocation
                                ),
                                possibleDeliveryStatus = PossibleDeliveryStatus.PENDING_ACCEPTANCE,
                                lastUpdateDate = Calendar.getInstance().time
                            )

                            possibleDeliveries.add(possibleDelivery)

                            println("Possible Delivery : " + possibleDelivery)
                        }
                    }
                    spotsAround.addAll(spotsAroundAtCurrentResolution) // Must be a sorted list as per the distance from donation location.

                }
            }


            donationHashMap[donation] = distanceBetweenDeliveryAndDonation
        }

return possibleDeliveries

    }

    fun findDistanceBetweenCoordinates(value1 : LatLng, value2 : LatLng) : Double {
        return Math.acos(Math.sin(value1.latitude)*Math.sin(value2.latitude)+Math.cos(value1.latitude)*Math.cos(value2.latitude)*Math.cos(value2.longitude-value1.longitude))*6371.0
    }

    fun acceptDelivery(deliveryId : String) : String{
        return ""
    }

    fun updateDelivery(deliveryId : String){
        return
    }

}