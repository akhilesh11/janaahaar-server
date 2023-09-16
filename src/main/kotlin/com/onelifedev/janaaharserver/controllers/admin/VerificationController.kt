package com.onelifedev.janaaharserver.controllers.admin

import com.onelifedev.janaaharserver.models.admin.EntityVerification
import com.onelifedev.janaaharserver.services.admin.VerificationService
import com.onelifedev.janaaharserver.utilities.VerificationStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception
import java.lang.IllegalArgumentException


@RestController("/admin/verification")
class VerificationController(val verificationService : VerificationService) {

    @GetMapping("/fetchAllVerifications")
    fun fetchAllVerifications() : ResponseEntity<List<EntityVerification>>?{
        return ResponseEntity.ok(verificationService.fetchAllVerifications())
    }

    @GetMapping("/fetchVerificationsHavingStatus")
    fun fetchAllVerificationsHavingStatusIn(@RequestParam(required = true) status : List<String>){
        var errorMessage: String = ""
        try {
            status.forEach {
                if (VerificationStatus.valueOf(it).name != it) {
                    errorMessage += "The status must be exactly one of ${VerificationStatus.values()}"
                }
            }
            // If no error found then call the service
            if(errorMessage == ""){
                verificationService.fetchVerificationsByStatus(status)
            }

        }catch (wrongStatusError : IllegalArgumentException){
            errorMessage += "The status must be exactly one of ${VerificationStatus.values()}"
        }
        catch(otherError : Exception){
            errorMessage += "Following error while fetching status" + otherError.message
        }
    }
}