package com.onelifedev.janaaharserver.controllers

import com.onelifedev.janaaharserver.models.Restaurant
import com.onelifedev.janaaharserver.services.SampleService
import lombok.AllArgsConstructor
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/restaurants")
@AllArgsConstructor
class SampleController(var sampleService: SampleService) {

    @GetMapping
    fun getCount(): Int {
        return sampleService.getCount()
    }

    @GetMapping("/{id}")
    fun getRestaurantById(@PathVariable("id") id: String): ResponseEntity<Restaurant?> {
        val restaurant = sampleService.findByRestaurantId(id)
        return if(restaurant!=null) ResponseEntity.ok(restaurant) else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun postRestaurant(): ResponseEntity<Restaurant?> {
        val restaurant = Restaurant().copy(name = "sample", restaurantId = "33332")
        if(sampleService.findByRestaurantId("33332")==null) {
            return ResponseEntity.ok(sampleService.insert(restaurant))
        }
        else{
            val responseHeaders = HttpHeaders()
            responseHeaders.set("ErrorMessage", "Restaurant Id is duplicate")
            return ResponseEntity(null,responseHeaders,HttpStatus.BAD_REQUEST)
        }
    }

    @PatchMapping("/{id}")
    fun updateRestaurant(@PathVariable("id") id: String): ResponseEntity<Restaurant?> {
        return ResponseEntity.ok(sampleService.findByRestaurantId(restaurantId = id)?.let {
            sampleService.save(it.copy(name = "Update"))
        })
    }

    @DeleteMapping("/{id}")
    fun deleteRestaurant(@PathVariable("id") id: String) {
        sampleService.findByRestaurantId(id)?.let {
            sampleService.delete(it)
        }
    }

}