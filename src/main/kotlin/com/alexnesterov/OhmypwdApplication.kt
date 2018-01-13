package com.alexnesterov

import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType.*
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.BodyInserters.fromPublisher
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.ipc.netty.NettyContext
import reactor.ipc.netty.http.server.HttpServer

fun render(template: () -> String): Mono<ServerResponse>
        = ok().contentType(TEXT_HTML).body(Mono.just(template()), String::class.java)

fun getPasswordsStream(): Flux<String> = Flux.generate {
    it.next(PasswordGenerator.generate().toString())
}

val routes = router {
    GET("/", {
        render {
            indexPage(PasswordGenerator.generate())
        }
    })

    path("/api").nest {
        GET("/password/stream", {
            ok()
                    .contentType(TEXT_EVENT_STREAM)
                    .body(fromPublisher(getPasswordsStream(), String::class.java))
        })

        GET("/password/single", {
            ok()
                    .contentType(APPLICATION_JSON)
                    .body(fromObject(PasswordGenerator.generate()))
        })
    }

    resources("/**", ClassPathResource("static/"))
}

fun startApp(port: Int): NettyContext? {
    val handler = ReactorHttpHandlerAdapter(RouterFunctions.toHttpHandler(routes))
    return HttpServer.create(port).newHandler(handler).block()
}

fun main(args: Array<String>) {
    startApp(8080)
    Thread.currentThread().join()
}