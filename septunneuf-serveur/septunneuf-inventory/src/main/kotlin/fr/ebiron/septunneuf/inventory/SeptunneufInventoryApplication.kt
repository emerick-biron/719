package fr.ebiron.septunneuf.inventory

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class SeptunneufInventoryApplication

fun main(args: Array<String>) {
    runApplication<SeptunneufInventoryApplication>(*args)
}
