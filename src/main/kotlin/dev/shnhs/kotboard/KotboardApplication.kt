package dev.shnhs.kotboard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotboardApplication

fun main(args: Array<String>) {
    runApplication<KotboardApplication>(*args)
}
