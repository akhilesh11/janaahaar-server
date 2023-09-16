package com.onelifedev.janaaharserver.repositories

import com.onelifedev.janaaharserver.models.Spot
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SpotRepository : MongoRepository<Spot,String> {

    fun findSpotById(spotId : String) : Spot

    @Query(value="{ \"hexGridAddressAtEachResolution.hexAddress\" : ?0 }")
    fun findSpotWhichContains(hexAddress : String) : List<Spot>

    //@Query(value="{ \"hexGridAddressAtEachResolution.hexAddress\" : ?0 }")
    //fun findSpotContainsAndCanBeFulfilled(hexAddress: String,numberOfPackets : Int) : List<Spot>

    @Query(value="{ \"userId\" : ?0 }")
    fun findSpotWhereUserId(userId : String) : List<Spot>
}