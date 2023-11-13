package com.onelifedev.janaaharserver.services

import com.onelifedev.janaaharserver.models.*
import com.onelifedev.janaaharserver.repositories.SpotRepository
import com.onelifedev.janaaharserver.services.admin.VerificationService
import com.onelifedev.janaaharserver.utilities.JanAahaarCommonUtils
import com.onelifedev.janaaharserver.utilities.VerificationType
import com.uber.h3core.H3Core
import org.springframework.stereotype.Service
import java.lang.Exception
import java.util.Calendar

@Service
class SpotService(val spotRepository: SpotRepository,val matchingService: MatcherService,val verificationService: VerificationService) {

    fun getSpot() : Spot?{
        return null
    }

    fun updateSpot(spot : Spot) : Spot{
        return spotRepository.save(spot)
    }

    fun createSpot(latLng: LatLng, requiredNumberOfPackets : Int, additionalDescription : String) : Spot?{

        //Creating the Spot hexAddress list at each level of resolution
        // the number of hexagons increases with each resolution.
        val spotAddressList : MutableList<HexMap> = mutableListOf()

        for(resolution in 0 .. 15){
            val h3 = H3Core.newInstance()
            val spotHexAddress = h3.latLngToCellAddress(latLng.latitude, latLng.longitude, resolution)
            spotAddressList.add(HexMap(resolution,spotHexAddress))
        }

        val spot = Spot(
            id = "Spot_${Calendar.getInstance().time.toInstant().toEpochMilli()}",
            userId = "User_3214",
            additionalDescription = additionalDescription,
            coordinatesOfLocation = latLng,
            numberOfPacketsNeeded = requiredNumberOfPackets,
            verified = false,
            status = Status.IN_PROGRESS,
            creationDate = Calendar.getInstance().time,
            lastUpdateDate = Calendar.getInstance().time,
            hexGridAddressAtEachResolution = spotAddressList
            )

        return try {
            val createdSpot = spotRepository.insert(spot)
            matchingService.afterSavingSpotFindDonationNearby()
            verificationService.raiseVerificationRequest(createdSpot.id,VerificationType.SPOT)
            createdSpot
        } catch (exception : Exception){
            println("Exception in Spot Service : ${exception.message}")
            null
        }

    }

    fun deleteSpot(spotId : String){
        return spotRepository.deleteById(spotId)
    }

    //Based on the choice of search the range radius of the requested latitude and longitude.
    fun searchSpotsWithinRange(requestLatLng: LatLng,resolution : Int) : List<Spot>?{

        if(JanAahaarCommonUtils.checkResolutionInRange(resolution)){
            val h3 = H3Core.newInstance()
            val requestLatLngHexAddress = h3.latLngToCellAddress(requestLatLng.latitude, requestLatLng.longitude, resolution)
            println("requestLatLng Hex Address : $requestLatLngHexAddress")
            println(requestLatLng)
            return spotRepository.findSpotWhichContains(requestLatLngHexAddress)
        }

        /**"SELECT * FROM SPOTS " +
                "   WHERE (latitude BETWEEN S{latLng.latitude - findRange} AND S{latLng.latitude + findRange} )" +
                "   AND (longitude BETWEEN S{latLng.longitude - findRange} AND S{latLng.longitude + findRange})"**/
        return null
    }

    fun fetchSpotsForUserWithId(userId : String) : List<Spot>{
        return spotRepository.findSpotWhereUserId(userId)
    }
}