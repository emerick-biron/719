package fr.ebiron.septunneuf.incubators

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class SeptunneufIncubatorApplication

fun main(args: Array<String>) {
	runApplication<SeptunneufIncubatorApplication>(*args)
}
