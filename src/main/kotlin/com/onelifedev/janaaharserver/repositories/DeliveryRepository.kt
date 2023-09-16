package com.onelifedev.janaaharserver.repositories

import com.onelifedev.janaaharserver.models.Delivery
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface DeliveryRepository : MongoRepository<Delivery,String> {

}