package com.onelifedev.janaaharserver.repositories.admin

import com.onelifedev.janaaharserver.models.admin.EntityVerification
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface VerificationRepository : MongoRepository<EntityVerification,String> {

    fun findByStatusIn(status : List<String>) : List<EntityVerification>
}