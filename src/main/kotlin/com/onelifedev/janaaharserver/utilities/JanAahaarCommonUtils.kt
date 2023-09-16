package com.onelifedev.janaaharserver.utilities

class JanAahaarCommonUtils {

    companion object {
         fun checkResolutionInRange(res: Int): Boolean {
            return res in 0..15
        }

       const val SPOT_H3_RESOLUTION : Int = 8
    }

}

enum class VerificationStatus {PENDING_INITIATION,INITIATED,IN_PROGRESS,VERIFIED,REJECTED}
enum class VerificationType {USER,SPOT,DONATION}

