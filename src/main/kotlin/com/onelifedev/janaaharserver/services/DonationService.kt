package com.onelifedev.janaaharserver.services

import com.onelifedev.janaaharserver.models.Donation
import com.onelifedev.janaaharserver.models.HexMap
import com.onelifedev.janaaharserver.models.LatLng
import com.onelifedev.janaaharserver.repositories.DonationRepository
import com.onelifedev.janaaharserver.utilities.JanAahaarCommonUtils
import com.uber.h3core.H3Core
import org.springframework.stereotype.Service
import java.util.*


@Service
class DonationService(val donationRepository: DonationRepository,val matchingService: MatcherService) {

    fun getDonation(donationId : String) : Optional<Donation> {
        return donationRepository.findById(donationId)
    }

    fun createDonation(availablePackets : Int,latLng: LatLng,expiryDate: Date) : Donation{

        //Creating the Donation hexAddress list at each level of resolution
        // the number of hexagons increases with each resolution.
        val donationAddressList : MutableList<HexMap> = mutableListOf()

        for(resolution in 0 .. 15){
            val h3 = H3Core.newInstance()
            val donationHexAddress = h3.latLngToCellAddress(latLng.latitude, latLng.longitude, resolution)
            donationAddressList.add(HexMap(resolution,donationHexAddress))
        }

        val donation = Donation(
            id = "Donation_${Calendar.getInstance().time.toInstant().toEpochMilli()}",
            userId = "Doner_1234",
            coordinatesOfLocation = latLng,
            numberOfPackets = availablePackets,
            bestBeforeDate = expiryDate,
            creationDate = Calendar.getInstance().time,
            hexGridAddressAtEachResolution = donationAddressList,
            lastUpdateDate = Calendar.getInstance().time
        )

        val createdDonation = donationRepository.save(donation)
        matchingService.afterSavingDonationFindSpotsNearby(createdDonation.coordinatesOfLocation,createdDonation.numberOfPackets)
        return donationRepository.save(donation)

    }

    fun deleteDonation(donationId: String){

    }

    fun updateDonation(donation: Donation){

    }

    fun completeDonation(donationId: String){

    }

    //Based on the choice of search the range radius of the requested latitude and longitude.
    fun searchDonationsWithinRange(requestLatLng: LatLng, resolution: Int) : List<Donation>?{

        if(JanAahaarCommonUtils.checkResolutionInRange(resolution)){
            val h3 = H3Core.newInstance()
            val requestHexAddress = h3.latLngToCellAddress(requestLatLng.latitude, requestLatLng.longitude, resolution)
            return donationRepository.hexGridAddressAtEachResolutionContains(requestHexAddress)
        }
        return null
    }
}