package com.onelifedev.janaaharserver.services

import com.onelifedev.janaaharserver.models.Restaurant
import com.onelifedev.janaaharserver.repositories.Repo
import lombok.AllArgsConstructor
import org.springframework.stereotype.Service


@AllArgsConstructor
@Service
class SampleService( val sampleRepo: Repo) {

    fun findByRestaurantId(restaurantId : String) : Restaurant? {
       return sampleRepo.findByRestaurantId(restaurantId)
    }

    fun getCount() : Int{
        return sampleRepo.findAll().count()
    }

    fun insert(restaurant: Restaurant) : Restaurant{
        return sampleRepo.insert(restaurant)
    }

    fun save(restaurant: Restaurant) : Restaurant{
        return sampleRepo.save(restaurant)
    }

    fun delete(restaurant: Restaurant) {
        sampleRepo.delete(restaurant)
    }


}