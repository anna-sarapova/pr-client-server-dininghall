package com.server.dininghall

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class DininghallApplication

fun main(args: Array<String>) {
	runApplication<DininghallApplication>(*args)
}
