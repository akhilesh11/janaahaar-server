package com.onelifedev.janaaharserver.repositories

import com.onelifedev.janaaharserver.models.Delivery
import com.onelifedev.janaaharserver.models.PossibleDelivery
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface DeliveryRepository : MongoRepository<Delivery,String> {

}

@Repository
interface PossibleDeliveryRepository : MongoRepository<PossibleDelivery,String> {

}