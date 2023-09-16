package com.onelifedev.janaaharserver.services

import com.onelifedev.janaaharserver.models.LatLng
import com.onelifedev.janaaharserver.repositories.DonationRepository
import com.onelifedev.janaaharserver.repositories.SpotRepository
import com.uber.h3core.H3Core
import org.springframework.stereotype.Service

@Service
class MatcherService(val donationRepository: DonationRepository,val spotRepository: SpotRepository) {

    val h3 : H3Core by lazy { H3Core.newInstance() }


    fun afterSavingSpotFindDonationNearby(){
        //The most optimal one is to fetch the ones which can fulfill the required packets in minimum trips.

        //Problem Statement
        /*
            Given the location, #ofPACKETS for a donations around.
            [GoogleMapsDistanceAndTimeBetweenSpotAndDonation, NumberOfPackets]

            Problem to Solve : Minimise the total sum of distance and time of the donations that make up to the required packets on Spot.

            Not making much difference Idea :
                The threshold for preferring fewer trips is that the time difference of <= 15min
                i.e. If donation_1 takes 60 min to deliver but can complete while requirement of Spot then it should be preferred over 2 trips that take 45 mins or more.

            Good Ideas :
                The distance between delivery guy and donation pickup location should also be considered in this algo.
                Should also be a limit on how may packets a single delivery person can carry at a time.
                Scheduling option for active hours for spotters,delivery guys, donations services where they will receive notifications and considered for calculations.
                Do NOT choose the nearest option first and then look for options (Greedy Algo). Consider all options until a resolution and then bring some result back.


            Given :
            Array of donations :
                ["donation_id",Distance from delivery guy to Spot with waypoint as donation point,Time,NoOfPackets],
                [["donation_1",26.4,45,60], ["donation_2",16.4,25,30], ["donation_3",10.2,20,25]] // Should return donation_1 as they take same time so fewer trips
                [["donation_1",26.4,45,60], ["donation_2",8.1,15,40], ["donation_3",5.8,10,35]] // Should return donation_2,donation_3 as they take lesser time.
            Spot number of packets :
                Number of required packets : 50
         */

        println("Best Sum Donation Test 1 ${findOptimalDonations(listOf(
            MatcherObject("donation_1",26.4,45,60),
            MatcherObject("donation_2",16.2,25,30),
            MatcherObject("donation_3",10.2,20,25)
        ),50,hashMapOf())}")

        println("Best Sum Donation Test 2 ${findOptimalDonations(listOf(
            MatcherObject("donation_1",26.4,45,60),
            MatcherObject("donation_2",8.1,15,40),
            MatcherObject("donation_3",5.8,10,35)
        ),50, hashMapOf()
        )}")

        println("Best Sum Donation Test 3 ${findOptimalDonations(listOf(
            MatcherObject("donation_1",6.4,15,60),
            MatcherObject("donation_2",16.2,25,30),
            MatcherObject("donation_3",10.2,20,25)
        ),50,hashMapOf())}")
    }

    data class MatcherObject(val donationId : String,
                             val distFromDeliveryToSpotViaDonation : Double,
                             val timeFromDeliveryToSpotViaDonationMin : Int,
                             val numberOfPacketsAvailable : Int){}

    private fun maxOfTime(list: List<MatcherObject>) : Int{
        return list.maxOf {
            it.timeFromDeliveryToSpotViaDonationMin
        }
    }

    private fun minOfDistance(list: List<MatcherObject>) : Double{
        return list.minOf {
            it.distFromDeliveryToSpotViaDonation
        }
    }

    private fun findOptimalDonations(matchableOptionsAroundList : List<MatcherObject>, targetNumberOfPackets : Int, memo : HashMap<Int,List<MatcherObject>?>) : List<MatcherObject>?{
       //TODO(Add logic to implement the best sum)
        if(memo.containsKey(targetNumberOfPackets)) {
            //println(memo)
            return memo[targetNumberOfPackets]
        }
        if(targetNumberOfPackets <= 0)
            return listOf()

        var optimizedMatches : List<MatcherObject>? = null

        for(option in matchableOptionsAroundList){
            val remainder = targetNumberOfPackets - option.numberOfPacketsAvailable
            val currentList : MutableList<MatcherObject> = findOptimalDonations(matchableOptionsAroundList.filter { it.donationId != option.donationId },remainder,memo)!!.toMutableList()
            if(!currentList.contains(option))
                currentList.add(option)
            if(optimizedMatches == null || ( (maxOfTime(currentList) < maxOfTime(optimizedMatches)) || (minOfDistance(currentList) < minOfDistance(optimizedMatches)) || (optimizedMatches.size > currentList.size)) ){
                optimizedMatches = currentList
            }
        }

        memo[targetNumberOfPackets] = optimizedMatches
        //println(memo)
        return optimizedMatches

    }

    fun afterSavingSpotFindNearby(){
        //The most optimal one is to fetch the ones which can fulfill the required packets in minimum trips.
    }

    fun afterSavingDonationFindSpotsNearby(donationLatLng : LatLng, numberOfAvailablePackets : Int){
        for(resolution in 15 downTo 4) {
            val requestLatLngHexAddress =
                h3.latLngToCellAddress(donationLatLng.latitude, donationLatLng.longitude, resolution)
            println(
                "List of Spots around donations at resolution $resolution : ${
                    spotRepository.findSpotWhichContains(
                        requestLatLngHexAddress
                    )
                }"
            )
        }
    }

    fun notifyDeliveryPersonsAroundOfNewDeliveries(){}

}