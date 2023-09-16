package com.onelifedev.janaaharserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories(basePackages = ["com.onelifedev.janaaharserver"])
class JanaaharServerApplication

	fun main(args: Array<String>) {
		runApplication<JanaaharServerApplication>(*args)
	}


