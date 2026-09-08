package dev.shnhs.kotboard

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SampleController {
    @GetMapping("/sample")
    fun sample(): String = "Hello World!"

    @PostMapping("/sample")
    fun sample(
        @RequestParam name: String,
    ): String = "Hello $name!"
}
