package com.alexnesterov

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.subject.SubjectSpek
import org.springframework.http.MediaType.*
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.test.StepVerifier

object OhmypwdTests: SubjectSpek<WebTestClient>({

    subject { WebTestClient.bindToRouterFunction(routes).build() }

    val passwordHtmlPattern = "\\w+<span>\\d\\d</span>\\w+"
    val passwordPattern = "^\\w+\\d\\d\\w+$"

    describe("when getting a page by / url") {
        it("returns 200 response with type text/html") {
            subject.get().uri("/").accept(TEXT_HTML)
                .exchange()
                    .expectStatus()
                    .is2xxSuccessful
        }

        it("returns html with password") {
            val responseBody = subject.get().uri("/").accept(TEXT_HTML)
                .exchange()
                .expectStatus().isOk
                .expectBody(String::class.java)
                .returnResult()
                .responseBody

            assertThat(responseBody).containsPattern(passwordHtmlPattern)
        }
    }

    describe("when getting a password via API") {
        it("returns stream of strings") {
            val result = subject.get().uri("/api/password/stream")
                    .accept(TEXT_EVENT_STREAM)
                    .exchange()
                    .expectStatus().isOk
                    .expectHeader().contentType(TEXT_EVENT_STREAM)
                    .returnResult(String::class.java)

            StepVerifier.create(result.responseBody)
                    .expectNextCount(3)
                    .expectNextMatches({ it.matches(Regex(passwordPattern)) })
                    .thenCancel()
                    .verify()
        }

        it("returns a single string") {
            val result = subject.get().uri("/api/password/single")
                    .accept(APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk
                    .expectHeader().contentType(APPLICATION_JSON)
                    .returnResult(Password::class.java)

            StepVerifier.create(result.responseBody)
                    .expectNextMatches({ it.toString().matches(Regex(passwordPattern)) })
                    .expectComplete()
                    .verify()
        }
    }
})