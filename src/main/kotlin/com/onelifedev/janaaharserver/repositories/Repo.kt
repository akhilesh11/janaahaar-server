package com.onelifedev.janaaharserver.repositories

import com.onelifedev.janaaharserver.models.Restaurant
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface Repo : MongoRepository<Restaurant, String> {
    fun findByRestaurantId(restaurantId: String): Restaurant
}