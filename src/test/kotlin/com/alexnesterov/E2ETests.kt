package com.alexnesterov

import com.alexnesterov.pages.PasswordPage
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.springframework.util.SocketUtils

object E2Spec : Spek({

    val port = SocketUtils.findAvailableTcpPort()
    val app = startApp(port)
    val page = PasswordPage()
    page.init()

    describe("main page") {
        on("clicking refresh button") {
            it("should generate the new password") {
                page.navigateTo("http://localhost:$port/")
                page.clickRefresh()
                page.assertPasswordChanged()
            }
        }
    }

    afterGroup {
        page.quit()
        app?.dispose()
    }
})
