package com.onelifedev.janaaharserver.services.admin

import com.onelifedev.janaaharserver.models.admin.EntityVerification
import com.onelifedev.janaaharserver.repositories.admin.VerificationRepository
import com.onelifedev.janaaharserver.utilities.VerificationStatus
import com.onelifedev.janaaharserver.utilities.VerificationType
import org.springframework.stereotype.Service
import java.util.*

@Service
class VerificationService(val verificationRepo : VerificationRepository) {
    fun fetchAllVerifications() : List<EntityVerification>{
        return verificationRepo.findAll()
    }

    fun fetchVerificationsByStatus(statuses : List<String>) : List<EntityVerification>{
        return verificationRepo.findByStatusIn(statuses)
    }

    fun raiseVerificationRequest(id : String,type : VerificationType) : EntityVerification{
        val newVerification = EntityVerification(
            id = "VERIFICATION_${Calendar.getInstance().time.toInstant().toEpochMilli()}",
            type = type.name,
            status = VerificationStatus.PENDING_INITIATION.name,
            lastUpdateDate = Calendar.getInstance().time,
            creationDate = Calendar.getInstance().time,
            verificationEntityId = id
        )

        return verificationRepo.save(newVerification)
    }
}