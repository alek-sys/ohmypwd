package com.alexnesterov

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.subject.SubjectSpek
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.test.web.reactive.server.WebTestClient

@RunWith(JUnitPlatform::class)
object OhmypwdTests: SubjectSpek<WebTestClient>({

    subject { WebTestClient.bindToRouterFunction(routes).build() }

    describe("when getting a page by / url", {
        it("returns 200 response with type text/html", {
            subject.get().uri("/").accept(TEXT_HTML)
                .exchange()
                    .expectStatus()
                    .is2xxSuccessful
        })

        it("returns html with password", {
            subject.get().uri("/").accept(TEXT_HTML)
                .exchange()
                    .expectBody()
                    .consumeAsStringWith {
                        assertThat(it).containsPattern("\\w+<span>\\d\\d</span>\\w+")
                    }
        })
    })
})