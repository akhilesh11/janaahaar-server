package com.onelifedev.janaaharserver.repositories

import com.onelifedev.janaaharserver.models.Donation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface DonationRepository : MongoRepository<Donation,String> {

    fun findDonationById(donationId : String) : Donation?

    @Query(value="{ \"hexGridAddressAtEachResolution.hexAddress\" : ?0 }")
    fun hexGridAddressAtEachResolutionContains(hexAddress : String) : List<Donation>
}