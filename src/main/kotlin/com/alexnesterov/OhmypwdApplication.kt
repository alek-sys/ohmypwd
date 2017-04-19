package com.alexnesterov

import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType.TEXT_EVENT_STREAM
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.reactive.function.fromPublisher
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.ipc.netty.http.server.HttpServer
import java.time.OffsetDateTime
import java.util.*

data class Password(val first: String, val nums: String, val second: String) {
    override fun toString() = "$first$nums$second"
}

object PasswordGenerator {
    val lines = ClassPathResource("words").inputStream.bufferedReader().readLines()
    val random = Random(OffsetDateTime.now().toEpochSecond())

    fun generate() = Password(lines.random(), randomNum(2), lines.random())

    private fun randomNum(total: Int) = (1..total).fold("", { acc, _ -> acc + random.nextInt(9) })

    private fun List<String>.random(): String = this[random.nextInt(this.size)].toLowerCase()
}


fun indexPage(pwd: Password) = buildString {
    appendHTML().html {
        head {
            link("https://fonts.googleapis.com/css?family=Fredericka+the+Great", "stylesheet")
            link("main.css", "stylesheet")
        }
        body {
            h1 {
                + pwd.first
                span { + pwd.nums }
                + pwd.second
            }
        }
    }
}

fun render(template: () -> String) = ok().contentType(TEXT_HTML).body(Mono.just(template()), String::class.java)

fun getPasswordsStream(): Flux<String> {
    return Flux.generate {
        it.next(PasswordGenerator.generate().toString())
    }
}

val routes = router {
    GET("/", {
        render {
            indexPage(PasswordGenerator.generate())
        }
    })

    path("/api").nest {
        GET("/password", { ok().contentType(TEXT_EVENT_STREAM).body(fromPublisher(getPasswordsStream())) })
    }

    resources("/**", ClassPathResource("static/"))
}

fun main(args: Array<String>) {
    val handler = ReactorHttpHandlerAdapter(RouterFunctions.toHttpHandler(routes))
    HttpServer.create(8080).newHandler(handler).block()

    Thread.currentThread().join()
}
