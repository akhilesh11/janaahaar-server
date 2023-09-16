package com.onelifedev.janaaharserver.models.admin

import com.onelifedev.janaaharserver.utilities.VerificationStatus
import com.onelifedev.janaaharserver.utilities.VerificationType
import lombok.Getter
import lombok.Setter
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Getter
@Setter
@Document("Verifications")
data class EntityVerification(
    @Id
    val id : String,
    val type : String,
    val status: String,
    val lastUpdateDate : Date,
    val creationDate: Date,
    val verificationEntityId : String, //ID of the user/spot/donation
    )
