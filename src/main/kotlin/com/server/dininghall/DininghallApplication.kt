package com.server.dininghall

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = ["com.server.dininghall.controllers", "com.server.dininghall.services"] )
class DininghallApplication

fun main(args: Array<String>) {
	runApplication<DininghallApplication>(*args)
}
